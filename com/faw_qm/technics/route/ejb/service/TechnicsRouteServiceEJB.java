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
 * *CR7 2010/06/29 ������    �μ�: ��:product;TD��2275
 *
 * CCBegin by liunan 2012-04-25 �򲹶�v4r3_p044
 * CR8 2012/03/21 ���� ԭ�� ͳһ����Ȩ��
 * CCEnd by liunan 2012-04-25
 * SS1 �����ԣ�ճ����·�ߣ����˵����û�� Դ�汾��Ϣ�����ȡ�㲿��Դ�汾�󱣴浽˵���У������봴��·��ʱһ�¡� liunan 2012-06-14
 * SS2 ���ϱ��ϱ� routecompletepart ������ unit��λ����� D31��,partunitversion�㲿���汾���˴�������ȡ�㲿���汾�Ĵ��롣 liunan 2013-1-6
 * SS3 ���·���Ƿ��Զ���ȡ����·�ߵĸ�ѡ���ʶ��Ĭ��ѡ�У����Զ��������·�ߣ�Ȼ����·�߱༭�������û������Ƿ��޸ġ� liunan 2013-4-17
 * SS4 ���ղ��û���ĺ��������ʱ����ȡ���°������������Щǰ׼����׼���в��ú�����ѵ�����ͬ����������⡣ liunan 2013-4-8
 * SS5 ��ȡ�����㲿����·��ʱ����ȡ��ȡ����״̬��·�ߡ� liunan 2013-6-3
 * SS6 ���б�˳���������������㲿������� liunan 2013-11-25
 * SS7 �������ᡱ·��ʱ���ԡ�������Ľ����顱�͡��������Ȩ���顱����Ȩ�� liunan 2014-6-23
 * SS8 ������(��)�͵�(��)·�ߣ��ԡ�������װ�������顱�͡�������װ��Ȩ���顱���С��Ϻ���װ�������顱�͡��Ϻ���װ��Ȩ���顱������Ȩ�� liunan 2014-8-14
 * SS9 ͨ������֪ͨ�������㲿�� liuyang 2014-6-6
 * SS10 �洢���ڲ�Ʒ�㲿��BSOID liuyang 2014-6-6 
 * SS11 ·�߹����㲿��������ɫ��ʶ liuyang 2014-6-9
 * SS12 ������Ϊ·�߸�ѡ�� liuyang 2014-6-10
 * SS13 �����㲿�����Ϊ֮ǰ���㲿�� liuyang 2014-6-10
 * SS14 ·����ʾ������·�ߵ�λ��ǰ liuyang 2014-6-12
 * SS15 һ���ṹ�������ӡ���ɫ����ʶ�� liuyang 2014-6-13
 * SS16 �������ᡱ·��ʱ���ԡ�������Ľ����顱�͡��������Ȩ���顱����Ȩ�� liunan 2014-6-23
 * SS17 ·�����Ϊ�����պϼ���ʱ���Զ���� liuyang 2014-6-29 
 * SS18 ���ս�Ų���/���������㲿��ʱ��ֻ��Ҫ�����б��е��㲿�� ����� 2014-08-29
 * SS19 ǰ׼����׼֪ͨ������㲿������Ϊ���պϳ�������������״̬  liuyang 2014-6-3
 * SS20 ͨ�����õ��Ų��ҹ����㲿�� liuyang 2014-6-5
 * SS21 ���·�ߴ����ж��Ƿ����������·�� ����� 2014-10-15
 * SS22 ��׼���ձ������㲿������������ȡ���㲿���� liunan 2014-12-24
 * SS23 ����ص��㲿���ķ���״̬���á� liunan 2015-1-12
 * SS24 �����ɶ��ġ�����·�ߣ��ԡ��ɶ������顱�͡��ɶ�Ȩ���顱������Ȩ�� liunan 2015-2-11
 * SS25 ���������Ը�С�汾����׼������Ƹ�ֵʱ�����ж�Ȩ�ޣ���ǰ��������Ȩ�ޣ��ߵ������˵����Ȩ���ˡ� liunan 2015-2-27
 * SS26 ���֪ͨ���в���ʾ�仯��ʽ�ǡ��ṹ�������֪ͨ��  ������ 2015-04-28
 * SS27 ���������������һ��·�ߣ���ű�·��ʱ�Զ���ȡ�˸�·�ߣ�����·�ߵ�λ���Ǳ����䵥λ�����ƣ�����ʾ�ǡ���ݳ��䡱�������Ȩ������ݡ� liunan 2015-6-1
 * SS28 ��Ӹ����� liunan 2015-6-18
 * SS29 A004-2015-3163 ������ڲ�Ʒû�ж�Ȩ�ޣ������ʱ���ҵ�master����Ȩ�ޣ�������ʱ���жϣ��˴�������ʾ���޷��õ��ü��� liunan 2015-7-6
 * SS30 SS29�����޸ģ�����㲿���Ǵ�������·�ߣ���״̬���ݶ�Ӧ���ϣ�partindex���ǡ��ޡ�����·�������״̬����Ϊ����ʱ��ȡһ������·�ߵ�״̬�������һ�¡� liunan 2015-8-5
 * SS31 ��׼��·����Ȩʧ�ܣ���Ϊ�м���ֱ�ӷ��ط�֧�� liunan 2015-10-28
 * SS32 ���������������ȡ�����㲿��ҲҪ������0�� liunan 2015-12-23
 * SS33 �ص�·�ߣ����û��Դ�汾����ȥ����汾�� liunan 2016-3-22
 * SS34 ��׼����ʱ������㲿�����������ù淶����ֿ�ָ�����⡣ liunan 2016-4-7
 * SS35 ��·����Ȩ����ʷ���ݴ��ڱ���Ľڵ���coding������� liunan 2016-7-28
 * SS36 �����㲿����ȡ����·�ߴ� liunan 2016-8-4
 * SS37 ��������׼����Ŀ liuyuzhu 2016-05-23
 * SS38 A004-2016-3415 ʵ��PDM������ݼ��������Բ����Զ�������EOLϵͳ liunan 2016-9-7 ���Է��м�� 2016-11-14
 * SS39 A004-2016-3433 ����·�����ѹ��ܣ����ڻ�ȡ�Ӽ���װ��·�߼��ϡ� liunan 2016-10-28
 * SS40 �Զ�������Ʒ���� guoxiaoliang  2017-04-14
 * SS41 ������ͬ����������ͬ�ڽ����ϱ����ͬ��ɫ  guoxiaoliang  2017-04-14
 * SS42 ������׼�������źͲ��ú�   guoxiaoliang  2017-05-10
 * SS43 �����׼������������  guoxiaoliang 2017-07-12
 * SS44 ��·����Ȩ�����Ӷ��㲿��������Ȩ�����衣 liunan 2017-6-8
 * SS45 A004-2017-3575 ·�ߡ�ר����Ӧ������Ȩ���顱�͡����ؽ����顱 liunan 2017-6-21
 * SS46 ��ȡ���������з�����ŵ������㲿���� liunan 2017-6-28
 * SS47 ������������Ƽ�����Ĭ�ϼ�������·�ߡ��С��� liunan 2017-7-3
 * SS48 �����׼������  guoxiaoliang 2017-07-12
 * SS49 EOL����Լ���㲿����������� liunan 2017-7-24
 * SS50 ��׼�޸ĵ����� guoxiaoliang 2017-09-6
 * SS51 ��EOLϵͳ��������2������ ������ÿת������ �������복�ٴ������ٱ� liunan 2017-9-25
 * SS52 ���������������״̬Ϊ��׼ guoxiaoliang 2017-09-13
 * SS53 �޸���׼����������� guoxiaoliang 20171106
 */


package com.faw_qm.technics.route.ejb.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.Arrays;
import java.util.ArrayList;

import com.buildnum.ejb.service.numService;
import com.faw_qm.version.util.VersionControlHelper;
import com.faw_qm.acl.ejb.entity.AdHocControlled;
import com.faw_qm.acl.ejb.entity.QMPermission;
import com.faw_qm.acl.ejb.service.AccessControlService;
import com.faw_qm.acl.util.AdHocAclHelper;
import com.faw_qm.acl.util.QMAclEntry;
import com.faw_qm.bomNotice.ejb.service.BomNoticeServiceEJB;
import com.faw_qm.bomNotice.model.BomAdoptNoticeIfc;
import com.faw_qm.bomNotice.model.BomAdoptNoticeInfo;
import com.faw_qm.bomNotice.model.BomAdoptNoticePartLinkInfo;
import com.faw_qm.bomNotice.model.BomChangeNoticeInfo;
import com.faw_qm.bomNotice.model.BomChangeNoticePartLinkInfo;
import com.faw_qm.cappclients.capproute.graph.RouteItem;
import com.faw_qm.cappclients.capproute.web.ReportLevelOneUtil;
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.codemanage.util.CodeManageTable;
import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.config.util.LatestConfigSpec;
import com.faw_qm.config.util.MultipleLatestConfigSpec;
import com.faw_qm.enterprise.model.MakeFromLinkIfc;
import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.epm.build.model.EPMBuildHistoryInfo;
import com.faw_qm.epm.epmdocument.model.EPMDocumentInfo;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.ejb.enterpriseService.EnterprisePartService;
import com.faw_qm.part.ejb.extendedService.ExtendedPartService;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.ration.model.AssisMaterialRationTotalIfc;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.route.exception.TechnicsRouteException;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.route.model.RouteBranchNodeLinkIfc;
import com.faw_qm.technics.route.model.RouteListProductLinkInfo;
import com.faw_qm.technics.route.model.RouteNodeIfc;
import com.faw_qm.technics.route.model.RouteNodeInfo;
import com.faw_qm.technics.route.model.RouteNodeLinkIfc;
import com.faw_qm.technics.route.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.route.model.TechnicsRouteIfc;
import com.faw_qm.technics.route.model.TechnicsRouteInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListMasterIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListMasterInfo;
import com.faw_qm.technics.route.util.RouteAdoptedType;
import com.faw_qm.technics.route.util.RouteCategoryType;
import com.faw_qm.technics.route.util.RouteHelper;
import com.faw_qm.technics.route.util.RouteListLevelType;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.util.JNDIUtil;
import com.faw_qm.version.ejb.service.VersionControlService;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.config.util.LatestConfigSpec;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.doc.util.DocLastConfigSpec;
import com.faw_qm.doc.util.ZXAdoptHelper;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.iba.definition.ejb.service.IBADefinitionObjectsFactory;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.definition.litedefinition.StringDefView;
import com.faw_qm.iba.definition.model.AttributeDefinitionIfc;
import com.faw_qm.iba.definition.model.StringDefinitionIfc;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.StringValueDefaultView;
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.model.StringValueInfo;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;

import com.faw_qm.part.model.PartConfigSpecIfc;

import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;


import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.part.util.Unit;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;

import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.util.VersionControlHelper;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;

import com.faw_qm.technics.route.ejb.entity.ListRoutePartLink;
import com.faw_qm.technics.route.ejb.entity.TechnicsRouteBranch;
import com.faw_qm.technics.route.ejb.entity.TechnicsRouteList;
import com.faw_qm.technics.route.ejb.entity.TechnicsRouteListMaster;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.pcfg.family.model.GenericPartInfo;
import com.faw_qm.jfpublish.receive.PublishHelper;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;

//CCBegin by liunan 2012-04-25 �򲹶�v4r3_p044
import com.faw_qm.enterprise.ejb.service.EnterpriseService;
//CCEnd by liunan 2012-04-25

//CCBegin by liunan 2012-05-29 ��øü���EPM�ĵ���������״̬��
import com.faw_qm.epm.epmdocument.model.EPMDocumentIfc;
import com.faw_qm.epm.build.model.EPMBuildHistoryIfc;
//CCEnd by liunan 2012-05-29

//CCBegin SS28
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentItemIfc;
import com.faw_qm.content.model.StreamDataInfo;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.content.util.StreamUtil;
//CCEnd SS28

//CCBegin SS37
import java.text.DateFormat;
import java.text.ParseException;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.domain.ejb.service.DomainService;
import com.faw_qm.producePreparative.model.ProducePreparativeIfc;
import com.faw_qm.producePreparative.model.ProducePreparativeInfo;
import com.faw_qm.producePreparative.model.ProjectPartIfc;
import com.faw_qm.producePreparative.model.ProjectPartInfo;
import com.faw_qm.workflow.definer.model.WfAssignedActivityTemplateIfc;
import com.faw_qm.workflow.definer.util.WfVariableInfo;
import com.faw_qm.workflow.engine.ejb.entity.ProcessData;
import com.faw_qm.workflow.engine.model.WfExecutionObjectIfc;
import com.faw_qm.workflow.engine.model.WfProcessIfc;
import com.faw_qm.workflow.work.model.WfAssignedActivityIfc;
//CCEnd SS37

//CCBegin SS38
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.units.util.QuantityOfMeasureDefaultView;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.iba.definition.litedefinition.UnitDefView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.content.model.ContentHolderIfc;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//CCEnd SS38
//CCBegin SS40
import com.faw_qm.producePreparative.ejb.service.ProducePreparativeService;
import com.faw_qm.producePreparative.model.ProductAttributesInfo;
//CCEnd SS40
import com.faw_qm.project.util.RolePrincipalTable;
import com.faw_qm.producePreparative.model.CapacityTrackingInfo;
import com.faw_qm.producePreparative.model.CheckReadyStateInfo;
import com.faw_qm.producePreparative.model.CompleteReportInfo;
import com.faw_qm.producePreparative.model.ProcessDebuggingInfo;
import com.faw_qm.producePreparative.model.ProcessDesignPhaseInfo;
import com.faw_qm.producePreparative.model.ProcessPreparationStageInfo;
import com.faw_qm.producePreparative.model.ProducePreparativeIfc;
import com.faw_qm.producePreparative.model.ProducePreparativeInfo;
import com.faw_qm.producePreparative.model.ProductAttributesInfo;
import com.faw_qm.producePreparative.model.ProductDevelopmentStatusInfo;
import com.faw_qm.producePreparative.model.ProjectPartIfc;
import com.faw_qm.producePreparative.model.ProjectPartInfo;
import com.faw_qm.producePreparative.model.ProjectTechnicsNoticeInfo;
import com.faw_qm.producePreparative.model.PrototypeInfo;
import com.faw_qm.producePreparative.model.TryInstallInfo;

// * �ο��ĵ���
// * Phos-REQ-CAPP-BR02(����·��ҵ�����)V2.0.doc
// * PHOS-REQ-CAPP-SRS-002(����ҵ��ҵ�������ݹ���������˵��-����·��) V2.0.doc
// * "Phos-CAPP-UC301--Phos-CAPP-UC322"��19��������
// *  (������) 200605 zz �����޸� �޸�ԭ��:��ѯ�㲿����������·��,Ӧ���Ǳ����õ�·��
// * (����һ)20060609 zz �����޸� �޸�ԭ��:�Ż��鿴·�ߵĹ����㲿��,�޸�Ϊ��������,�����м���������
// * (�����)20060701 zz �����޸� �޸�ԭ��:�Ż�����λ����·��,��������ù淶�Ĺ��˸�Ϊ����һ�ι���.
// * (������)20060629 zz �����޸� �޸�ԭ��:����·�����ɱ�������ٶ���,ͬʱ�޸������ɱ����jsp
// * �������ģ�2006 07 12  zz ������� �����Ż� ��ͬʱ�޸���CompareHandler
// * �������壩2006 08 03  zz ������� �����Ż� ���ڹ���·�ߴ�������һ�����Ա���·�ߴ��ַ���
// * �ݿͻ�����ʾ����·�ߴ���ʱ�����ֱ��ȡ�����ַ�����ʾ
// * ���������� 2006 08 04  zz �����޸� ������������㷨���������ظ�Ԫ��bug
// * �������ߣ� 2006 09 04 zz �����޸� �޶����°�·�߱���Ĺ����㲿����·��״̬ԭΪ���õı�Ϊȡ���ˣ�
// * ��������Ӧ�ø���ǰһ�汾��״̬
// * (�����)20061110 zz �������,Ϊ����·�� "���"��ť ��Ӹ�����ѡ��λ���˹���
// * (�����)������� zz ��Ӽ�����÷��������ٿͻ��˵��ô���
// * ������ʮ�����ӹ���·�ߵ����������� ������� zz 20061214

/**
 * �ṩ�Թ���·�߱�� ���������£�ɾ�����鿴�ķ���
 * @author  ������
 * @version 1.0
 */
public class TechnicsRouteServiceEJB
    extends BaseServiceImp { /////////////////�еĶ��������ܲ���Ҫdistinct.
  public final static boolean VERBOSE = Boolean.valueOf(RemoteProperty.
      getProperty(
      "com.faw_qm.technics.route.verbose", "true")).booleanValue();

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
  public static final String LIST_ROUTE_PART_LINKBSONAME =
      "ListRoutePartLink";
  public static final String PARTMASTER_BSONAME = "QMPartMaster";
  public static final String ROUTELIST_BSONAME = "TechnicsRouteList";
  public static final String ROUTELISTMASTER_BSONAME =
      "TechnicsRouteListMaster";
  public static final String ROUTENODE_BSONAME = "RouteNode";
  public static final String ROUTENODE_LINKBSONAME = "RouteNodeLink";
  public static final String TECHNICSROUTE_BSONAME = "TechnicsRoute";
  public static final String TECHNICSROUTEBRANCH_BSONAME =
      "TechnicsRouteBranch";
  public static final String BRANCHNODE_LINKBSONAME = "RouteBranchNodeLink";
  public static final String BRANCH_ROLENAME = "branch";
  public static final String FIRSTROUTE = "FIRSTROUTE";

  private final String comma = ",";
  private final String four_comma = comma + comma + comma + comma;
  private final String six_comma = comma + comma + comma + comma + comma +
      comma;
  private final String line = "--";
  private final String newline = "\n";
  public static String noRouteStr = "";
  private final static String RESOURCE =
      "com.faw_qm.technics.route.util.RouteResource";
 private String PARTRESOURCE = "com.faw_qm.part.util.PartResource";
//CCBegin by leixiao 2008-8-2
 public static String fbuserid;
//CCEnd by leixiao 2008-8-2
 
  //CCBegin SS28
  static boolean fileVaultUsed = (RemoteProperty.getProperty(
            "registryFileVaultStoreMode", "true")).equals("true");
  //CCEnd SS28
            
 public static CodingIfc modefyidentycoding;

  public TechnicsRouteServiceEJB() {
  }

//////////////////////////////////����Ϊ���Է���/////////////////////////////////////
  public Object processTest(int i) throws QMException {
    Object obj = null;
    ServiceTestHandler handler = new ServiceTestHandler();
    switch (i) {
      case 1: {
        //obj = handler.findRouteLists();
        break;
      }
      default: {
        break;
      }
    }
    return obj;
  }

//////////////////////////////////���Է�������////////////////////////////////////
  /**
   * getServiceName
   *
   * @return String
   */
  /**
   * ��÷�����
   * @return TechnicsRouteService
   */
  public String getServiceName() {
    return "TechnicsRouteService";
  }


  /**
   * ���湤��·���б��ǿռ����ֵ������������Ψһ�Լ�������ݿ������á�
   * @param routeListInfo TechnicsRouteListIfc ָ����·�߱�ֵ����
   * @throws QMException
   * @return TechnicsRouteListIfc ����·�߱��ֵ����
   * @see TechnicsRouteListInfo ָ����·�߱�ֵ����
   */
  public TechnicsRouteListIfc storeRouteList(TechnicsRouteListIfc
                                             //CCBegin SS28
                                             //routeListInfo) throws
                                             routeListInfo, ArrayList arrayList) throws
                                             //CCEnd SS28
      QMException {
      TechnicsRouteListIfc routeListInfo1 = null;
      if (PersistHelper.isPersistent(routeListInfo)) {
        throw new TechnicsRouteException(
            "routeService's storeRouteList : routeListInfo��Ӧ���־û���");
      }
      try {
        FolderService fservice = (FolderService) EJBServiceHelper.
            getService(
            "FolderService");
        fservice.assignFolder(routeListInfo, routeListInfo.getLocation());
        PersistService pservice = (PersistService) EJBServiceHelper.
            getPersistService();
        //CCBegin SS10
        String partMasterID = routeListInfo.getProductMasterID();
        QMPartInfo partInfo=getPartByMasterID(partMasterID);
        //CCBegin SS29
        if(partInfo==null)
        {
        	throw new TechnicsRouteException("�޷��õ����ڲ�Ʒ���㲿��������ü���״̬�Ƿ񷢲���");
        }
        //CCEnd SS29
        routeListInfo.setProductID(partInfo.getBsoID());
        //CCEnd SS10
        //CCBegin SS17
        if(routeListInfo.getRouteListState().equals("���պϼ�")){
			String dath = new SimpleDateFormat("yyyyMMdd",Locale.CHINESE).format(Calendar.getInstance().getTime());	
			numService ns=(numService)EJBServiceHelper.getService("numService");
			String num=ns.buildSerialNum(dath,3);
			routeListInfo.setRouteListNumber(num);
        }
        //CCEnd SS17
        routeListInfo1 = (TechnicsRouteListIfc) pservice.saveValueInfo(
            routeListInfo);
        
        //CCBegin SS28
        createAssisFile(routeListInfo1,arrayList);
        //CCEnd SS28
      }
      catch (Exception e) {
        if (VERBOSE) {
          System.out.println(TIME + "!!!!!e.getMessage = " + e.getMessage() +
                             "\n @@@@@" + e.toString());
        }
        if (e instanceof SQLException) {
          //�ж�Ψһ�ԡ�
          Object[] obj = {
              routeListInfo.getRouteListName(),
              routeListInfo.getRouteListNumber()};
          throw new TechnicsRouteException("3", obj);
        }
        else {
          this.setRollbackOnly();
          throw new TechnicsRouteException(e);
        }
        //throw new TechnicsRouteException(e);
      }
      try {
        if (VERBOSE) {
          System.out.println(TIME + "��ʼ����·�߱�Ͳ�Ʒ�Ĺ�����");
          //��ʱrouteListInfo�Ѿ������档����·�߱�Ͳ�Ʒ�Ĺ������˹���ֻ�ܴ�����ɾ����ɾ���ɳ־û�ά����
        }
        RouteListProductLinkInfo listProductInfo = new
            RouteListProductLinkInfo();
        //if(routeListInfo1.getMaster() == null || routeListInfo1.getMaster().getBsoID() == null)
        //throw new TechnicsRouteException("routeService's storeRouteList ·�߱��masterû�б�������");
        listProductInfo.setRouteListMasterID(routeListInfo1.getMasterBsoID());
        if (routeListInfo1.getProductMasterID() == null) {
          throw new TechnicsRouteException(
              "routeService's storeRouteList ��Ʒ��masterIDû�б����á�");
        }
        listProductInfo.setProductMasterID(routeListInfo1.
                                           getProductMasterID());
        PersistService pservice = (PersistService) EJBServiceHelper.
            getPersistService();
        pservice.saveValueInfo(listProductInfo);
      }
      catch (Exception e) {
        this.setRollbackOnly();
        throw new TechnicsRouteException(e);
      }

    return routeListInfo1;
  }

  /**
   * ���¹���·�߱�ֻ���м򵥴洢��
   * @param routeListInfo TechnicsRouteListIfc ·�߱�ֵ����
   * @throws QMException
   * @return TechnicsRouteListIfc  ����·�߱�ֵ����
   */
  private TechnicsRouteListIfc updateRouteList(TechnicsRouteListIfc
                                               routeListInfo) throws
      QMException {
    TechnicsRouteListIfc routeListInfo1 = null;
    if (!PersistHelper.isPersistent(routeListInfo)) {
      throw new TechnicsRouteException(
          "routeService's storeRouteList : routeListInfoû�б��־û���");
    }
    //����ʱ���ܸ���Ψһ���ԣ�����Ҫ��SQLException��װ��
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    routeListInfo1 = (TechnicsRouteListIfc) pservice.updateValueInfo(
        routeListInfo);
    return routeListInfo1;
  }


  /**
   * ɾ������·�߱�������汾�ڵ�С�汾ȫ��ɾ����
   * @param routeListInfo TechnicsRouteListIfc  ����·�߱�ֵ���� ����ָ����ֵ����ɾ��·�߱�
   * @throws QMException
   * @see TechnicsRouteListInfo ����·�߱�ֵ����
   */
  public void deleteRouteList(TechnicsRouteListIfc routeListInfo) throws
      QMException {
    ///////////////�а汾ɾ��������֧  2004.9.10 ������
    ///////////////���Ե��ò����źŵ�ɾ��,�Լ��������ɾ��. Ч�ʸ���.
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    pservice.deleteValueInfo(routeListInfo);
    /*
     //�ҵ�routeListID��Ӧ������С�汾��
     VersionControlService vservice = (VersionControlService)EJBServiceHelper.getService(VERSION_SERVICE);
     Collection vec1 = vservice.iteratiosaveRoutensOf(routeListInfo);
     if(VERBOSE)
       System.out.println(TIME+"deleteRouteListener ���е�С�汾��"+vec1);
     //ɾ���ڴ˰汾·�߱��н�����·�ߡ�
     //���ü���ɾ��·�ߡ���Ч�ʵͣ��˴��ݲ�����
     /////////////������������ʱ����һ���ô������Իָ���ɾ��ǰ��״̬������״̬�Ƿ�ָ���
     //����ɾ����
     PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
     Iterator iter1 = vec1.iterator();
     if(iter1.hasNext())
       pservice.deleteValueInfo((BaseValueIfc)iter1.next());*/
  }

  /**
   * �༭�㲿����,ִ�����ڸ��¹���·�߱�ʱ���༭Ҫ���ƹ���·�ߵ��㲿������������㲿����ɾ���㲿����
   * ����㲿��ʱ�����Ը���·�߱��е�"���ڲ�Ʒ"���㲿�����������½ṹ���ṩ��ѡ�㲿�����û����Դ���
   * ѡ��Ҫ��ӵ�����·�߱��е��㲿��������û����ڱ༭���Ƕ���·�߱���ѡ�㲿������Ӧ�����ڲ�Ʒ
   * �Ľṹ����Ӧ���˲�Ʒ�����㲿���а�������λ·�ߵ�һ��·�߽��н�һ��ɸѡ���Ӷ����һ�ݱ�ѡ�㲿����
   * @param codeID String ·�߱�λ�����ݵ�λ���ƵĴ���ID��·�߼����ÿ�ѡ����㲿����
   * @param level String ·�߼�����ʾ��������һ��·�߻����·��
   * �����ǰ�༭�Ĺ���·�߱���һ������·�߱���ϵͳӦ�г���Ʒ�ṹ�е������㲿����
   * �����ǰ�༭�Ĺ���·�߱��Ƕ�������·�߱���ϵͳӦ����·�߱�ĵ�λ���ԣ��г���Ʒ�ṹ������һ��·���ж�Ӧ��λ���㲿������Ϊ��ѡ�㲿����
   * @param productMasterID String ���ڲ�ƷMasterID
   * @throws QMException
   * @return Collection  ��ŵ���vector:<br>
   * vector�а���ˢ�º��partMasterID <br>
   * partMasterID�ǹ��˺��Ʒ�Ӽ���masterֵ����
   */
  public Collection getOptionalParts(String codeID, String level,
                                     String productMasterID) throws QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getOptionalParts : level = " +
                         level);
    }
    //�����һ��·�ߡ�
    Collection result = new Vector();
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
           //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
        QMPartIfc productInfo = (QMPartIfc) getLastedPartByConfig(productMasterID,getCurrentUserConfigSpec());
   // QMPartIfc productInfo = (QMPartIfc) getLatestVesion(productMasterID);
       //CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
    if (level.equalsIgnoreCase(RouteListLevelType.FIRSTROUTE.getDisplay())) {
      //����Ӽ���
      Collection coll = getAllSubParts(productInfo);
      for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
        String partMasterID = ( (QMPartIfc) iter.next()).getMaster().
            getBsoID();
        result.add(pservice.refreshInfo(partMasterID));
      }
    }
    else if (level.equalsIgnoreCase(RouteListLevelType.SENCONDROUTE.
                                    getDisplay())) {
      QMQuery query = new QMQuery(ROUTENODE_BSONAME,
                                  LIST_ROUTE_PART_LINKBSONAME);
      QueryCondition cond1 = new QueryCondition("nodeDepartment",
                                                QueryCondition.EQUAL, codeID);
      query.addCondition(0, cond1);
      query.addAND();
      QueryCondition cond2 = new QueryCondition("routeID", "routeID");
      query.addCondition(0, 1, cond2);
      query.addAND();
      QueryCondition cond3 = new QueryCondition("adoptStatus",
                                                QueryCondition.EQUAL,
                                                RouteAdoptedType.ADOPT.toString());
      query.addCondition(1, cond3);
      query.addAND();
      QueryCondition cond4 = new QueryCondition("alterStatus",
                                                QueryCondition.EQUAL,
                                                ROUTEALTER);
      query.addCondition(1, cond4);
      query.setDisticnt(true);
      query.setVisiableResult(0);
      if (VERBOSE) {
        System.out.println(TIME +
                           " RouteService getOptionalParts's SQL = " +
                           query.getDebugSQL());
      }
      Collection coll = pservice.findValueInfo(query);
      //���й��ˣ���ø�����Ʒ���Ӽ���
      for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
        ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
            next();
        String partMasterID = listLinkInfo.getPartMasterID();
        //�����Ӽ����ˡ�
        boolean flag = isChildPart(productInfo, partMasterID);
        if (flag) {
          result.add(pservice.refreshInfo(partMasterID));
        }
      }
    }
    else {
      throw new TechnicsRouteException("����·�ߵȼ�����ȷ");
    }
    if (VERBOSE) {
      System.out.println(TIME +
                         " RouteService getOptionalParts's result = " +
                         result);
    }
    return result;
  }
/**
   * ͨ��·�߱��Ż��·�߱�ֵ����
   * @param routeListNum ·�߱���
   * @return ·�߱�ֵ����
   * @author ������
   */
  public TechnicsRouteListInfo findRouteListByNum(String routeListNum) {
	  
	  TechnicsRouteListInfo routeListInfo=null;
	  Collection col=null;
	  
	  if(routeListNum==null || routeListNum.trim().equals("")) {
		  
		  return null;
	  }
	  
	  try {
	  QMQuery query=new QMQuery("TechnicsRouteList");
	  
	  QueryCondition cond=new QueryCondition("routeListNumber",
			  "=",routeListNum);
	  
	  query.addCondition(cond);
	  
	  PersistService pservice = (PersistService) EJBServiceHelper.
      getPersistService();
	  
	  col=pservice.findValueInfo(query);
	  
	  for(Iterator iter=col.iterator();iter.hasNext();) {
		  
		  routeListInfo=(TechnicsRouteListInfo)iter.next();
		  
	  }
	  
	  }catch(Exception e) {
		  e.printStackTrace();
	  }
	  
	  return routeListInfo;
	  
  }
  /**
   * �Ը������㲿�����м�飬�����Щ�㲿���Ƿ������ӵ�����·�ߵ��㲿����
   * �����ǰ�༭�Ĺ���·�߱��Ƕ�������·�߱���ϵͳӦ����·�߱�ĵ�λ���ԣ�
   * �г���Ʒ�ṹ������һ��·���ж�Ӧ��λ���㲿������Ϊ��ѡ�㲿����
   * @param codeID String ����·�߱�ĵ�λ
   * @param productMasterID String ����·�߱����ڵĲ�Ʒ
   * @param subPartMasters Collection ����м����㲿�����ϣ�Ҫ��Ԫ��Ϊ�㲿������Ϣֵ����
   * @throws QMException
   * @return Object[] �����а�������Ԫ��:<br>Object[0]:��һ��Ԫ���Ƿ����������㲿������Ϣ����;<br>
   * Object[1]:�ڶ���Ԫ���ǲ������������㲿������Ϣ����
   */
  public Object[] checkSubParts(String codeID, String productMasterID,
                                Collection subPartMasters) throws QMException {
    if (subPartMasters == null || subPartMasters.size() == 0) {
      return null;
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMPartIfc productInfo = (QMPartIfc) getLatestVesion(productMasterID);
    QMQuery query = new QMQuery(ROUTENODE_BSONAME,
                                LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond1 = new QueryCondition("nodeDepartment",
                                              QueryCondition.EQUAL, codeID);
    query.addCondition(0, cond1);
    query.addAND();
    QueryCondition cond2 = new QueryCondition("routeID", "routeID");
    query.addCondition(0, 1, cond2);
    query.addAND();
    QueryCondition cond3 = new QueryCondition("adoptStatus",
                                              QueryCondition.EQUAL,
                                              RouteAdoptedType.ADOPT.
                                              toString());
    query.addCondition(1, cond3);
    query.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(1, cond4);
    query.setDisticnt(true);
    query.setVisiableResult(0);
    if (VERBOSE) {
      System.out.println(TIME + " RouteService getOptionalParts's SQL = " +
                         query.getDebugSQL());
    }
    Collection coll = pservice.findValueInfo(query);
    Vector masteridVector = new Vector();
    //���й��ˣ���ø�����Ʒ���Ӽ���
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      String partMasterID = listLinkInfo.getPartMasterID();
      //�����Ӽ����ˡ�
      boolean flag = isChildPart(productInfo, partMasterID);
      if (flag && !masteridVector.contains(partMasterID)) {
        masteridVector.add(partMasterID);
      }
    } //���ˣ������ݿ����ҵ������з����������㲿��

    Vector v1 = new Vector(); //����װ�ط����������㲿��
    Vector v2 = new Vector(); //����װ�ز������������㲿��
    //������жԱȣ�
    if (masteridVector.size() > 0) {
      for (Iterator iter = subPartMasters.iterator(); iter.hasNext(); ) {
        QMPartMasterIfc tempMaster = (QMPartMasterIfc) iter.next();
        if (masteridVector.contains(tempMaster.getBsoID())) {
          v1.add(tempMaster);
        }
        else {
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
   * @param partMasterInfo QMPartMasterIfc ָ���㲿��������Ϣ��ֵ����
   * @throws QMException
   * @return Collection ��ŵ���HashMap:<br>
   * key:partMasterBsoID <br>value:Master
   * ��һ���㲿��������Ϣ��partInfo.getMaster()���ļ���
   * @see QMPartMasterInfo
   */
  public Collection getSubPartMasters(QMPartMasterIfc partMasterInfo) throws
      QMException {
    if (VERBOSE) {
      System.out.println("����TechnicsRouteService: getSubPartMasters ������" +
                         partMasterInfo.getBsoID());
    }
    HashMap map = new HashMap();
    QMPartIfc productInfo = (QMPartIfc) getLatestVesion(partMasterInfo);
    if (VERBOSE) {
      System.out.println("��Ʒ���°汾��" + productInfo.getBsoID());
    }
    StandardPartService partService = (StandardPartService)
        EJBServiceHelper.
        getService(PART_SERVICE);
    Collection c = partService.getSubParts(productInfo);
    if (VERBOSE) {
      System.out.println("Part�����������");
    }
    for (Iterator ite = c.iterator(); ite.hasNext(); ) {
      QMPartIfc partInfo = (QMPartIfc) ite.next();
      String tempMasterID = partInfo.getMasterBsoID();
      if (!map.containsKey(tempMasterID)) {
        QMPartMasterIfc tempMasterInfo = (QMPartMasterIfc) partInfo.
            getMaster();
        map.put(tempMasterID, tempMasterInfo);
      }
    }
    Vector reVector = new Vector();
    if (map.size() > 0) {
      reVector.addAll(map.values());
    }
    return reVector;
  }

  //��ø�����Ʒ�������Ӽ�������������
  private Collection getAllSubParts(QMPartIfc productInfo) throws QMException {
    StandardPartService partService = (StandardPartService)
        EJBServiceHelper.
        getService(PART_SERVICE);
    EnterprisePartService enterprisePartService = (
        EnterprisePartService)
        EJBServiceHelper.getService(
        "EnterprisePartService");
    Vector subs = new Vector();
    PartConfigSpecIfc configSpecIfc = partService.findPartConfigSpecIfc();
    QMPartIfc[] result = (QMPartIfc[]) enterprisePartService.
        getAllSubPartsByConfigSpec( (QMPartMasterIfc) productInfo.getMaster(),
                                   configSpecIfc);
    if (result == null || result.length == 0) {
      return subs;
    }
    //��Ϊ����ֵ������Ʒ����ȥ����
    for (int i = 0; i < result.length; i++) {
      QMPartIfc partInfo = (QMPartIfc) result[i];
      if (!partInfo.getBsoID().equals(productInfo.getBsoID())) {
        subs.add(partInfo);
      }
    }
    return subs;
  }

  //�жϸ���partMasterID�Ƿ��ǲ�Ʒ���Ӽ���
  private boolean isChildPart(QMPartIfc productInfo, String partMasterID) throws
      QMException {
    boolean flag = false;
    Collection result = getAllSubParts(productInfo);
    //�������Ƚϡ�
    for (Iterator iter = result.iterator(); iter.hasNext(); ) {
      QMPartIfc partInfo = (QMPartIfc) iter.next();
      if (partInfo.getMaster().getBsoID().equals(partMasterID)) {
        flag = true;
        break;
      }
    }
    return flag;
  }

  /**
   * ������°汾��ֵ����
   * @param masterInfo MasterIfc Masterֵ����
   * @throws QMException
   * @return BaseValueIfc
   * @see MasterInfo
   */
  public BaseValueIfc getLatestVesion(MasterIfc masterInfo) throws QMException {
    ConfigService cservice = (ConfigService) EJBServiceHelper.getService(
        CONFIG_SERVICE);
    Collection coll1 = cservice.filteredIterationsOf(masterInfo,
        new LatestConfigSpec());
    Iterator iter1 = coll1.iterator();
    BaseValueIfc info = null;
    if (iter1.hasNext()) {
//CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��     	//
 Object obj =  iter1.next();
      if(obj instanceof Object[])
      {
        info = (BaseValueIfc) ((Object[])obj)[0];
      }
      else
      {
        info = (BaseValueIfc) obj;
      }
//CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��
    }
    return info;
  }

  private BaseValueIfc getLatestVesion(String masterID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    MasterIfc masterInfo = (MasterIfc) pservice.refreshInfo(masterID);
    return getLatestVesion(masterInfo);
  }

  /**
   * ������°汾ֵ����ļ���
   * @param c Collection  master���󼯺�
   * @throws QMException
   * @return Collection ���obj[]���飺<br>obj[0]:���°汾ֵ����
   */
  //(������)20060629 �����޸�
  public Collection getLatestsVersion(Collection c) throws QMException
  {
    ConfigService cservice = (ConfigService) EJBServiceHelper.getService(
        CONFIG_SERVICE);
    Collection coll1 = cservice.filteredIterationsOf(c,
        new LatestConfigSpec());
    Iterator iter1 = coll1.iterator();
    BaseValueIfc info = null;
    Vector v = new Vector();
    while(iter1.hasNext())
    {
      Object[] obj = (Object[]) iter1.next();
      info = (BaseValueIfc) obj[0];
      v.add(info);
    }
    return v;
  }
  /**
   * ������°汾ֵ����ļ���
   * @param c Collection master���󼯺�
   * @throws QMException
   * @return HashMap key:������ value�����°汾ֵ����
   */
  //   *add by guoxl on 20080307(�鿴һ��·�߱���ʱ������������)
  //   *������ӷ���getLatestsVersion1
  public HashMap getLatestsVersion1(Collection c)throws QMException
  {
    ConfigService cservice = (ConfigService) EJBServiceHelper.getService(
       CONFIG_SERVICE);
   Collection coll1 = cservice.filteredIterationsOf(c,
       new LatestConfigSpec());
   Iterator iter1 = coll1.iterator();
   BaseValueIfc info = null;

     HashMap hash=new HashMap();

   while(iter1.hasNext())
   {
     Object[] obj = (Object[]) iter1.next();
     info = (BaseValueIfc) obj[0];
     if(info instanceof QMPartIfc)
       hash.put(((QMPartIfc)info).getPartNumber(),info);
   }
     return hash;

  }
  //add end

  /**
   * ���¹���·�߱�:�����ɾ��·�߱������Ĺ�����
   * ���湤��·�߱�������Ĺ�����
   * �ɿͻ��˴�������༯�ϣ���ͳһ����
   * @param saveCollection HashMap ������Ҫ��������masterID.
   * @param deleteCollection HashMap ������Ҫɾ�������masterID.
   * @param routeListInfo1 TechnicsRouteListIfc  ����·�߱�ID.
   * @throws QMException
   * @see TechnicsRouteListInfo
   */
  public void saveListRoutePartLink(HashMap saveCollection,
                                    HashMap deleteCollection,
                                    TechnicsRouteListIfc routeListInfo1) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's saveListRoutePartLink......................... saveCollection = "
                         + saveCollection + ", deleteCollection = " +
                         deleteCollection);
    }
    //����·�߱�
    TechnicsRouteListIfc routeListInfo = updateRouteList(routeListInfo1);
    if (VERBOSE) {
      if (routeListInfo1.getBsoID() == routeListInfo.getBsoID()) {
        System.out.println("���º�����õĶ������ǰ�Ķ����bsoidһ��");
      }
      else {
        System.out.println("���º�����õĶ������ǰ�Ķ����bsoid��һ��");
      }
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    try {
      //Set  set =  saveCollection.keySet();
      //����Ҫ�����������ϣ������½�������
      //while(set.iterator())
    	//Begin CR3
		QMQuery myQuery = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
		QueryCondition linkCond = new QueryCondition(LEFTID,
		        QueryCondition.EQUAL, routeListInfo.getBsoID());
		myQuery.addCondition(linkCond);

		Collection linkColl = pservice.findValueInfo(myQuery); 
		//End CR3
    	
      for (Iterator iter = saveCollection.keySet().iterator(); iter.hasNext(); ) {
        //st skybird 2005.3.4 ���游�����������޸�
        Object[] tmp = (Object[]) saveCollection.get(iter.next());

        String partMasterID = (String) tmp[0];
        QMPartIfc parentPart = (QMPartIfc) tmp[1]; 
        String panrentID = null;
        if (parentPart != null) {
          panrentID = parentPart.getBsoID();

          //ed
          
        }
      //Begin CR3
        if (linkColl != null && linkColl.size() != 0)
        {
       //End CR3
        	//�����Ƿ���ɾ��״̬�Ĺ�����ɾ����
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID,
                                                 QueryCondition.EQUAL,
                                                 routeListInfo.getBsoID());
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition(RIGHTID,
                                                  QueryCondition.EQUAL,
                                                  partMasterID);
        query.addCondition(cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("alterStatus",
                                                  QueryCondition.EQUAL,
                                                  PARTDELETE);
        query.addCondition(cond2);

        query.addAND();
        QueryCondition condP;
        if (panrentID != null && !panrentID.trim().equals("")) {
          condP = new QueryCondition("parentPartID",
                                     QueryCondition.EQUAL,
                                     panrentID);
        }
        else {
          condP = new QueryCondition("parentPartID",
                                     QueryCondition.IS_NULL);
        }
        query.addCondition(condP);

        if (VERBOSE) {
          System.out.println(TIME +
                             "saveListRoutePartLink'saveCollection routeListInfo.bsoID = " +
                             routeListInfo.getBsoID());
          System.out.println(TIME +
                             "saveListRoutePartLink'saveCollection partMasterID = " +
                             partMasterID);
          System.out.println(TIME +
                             "routeService's saveListRoutePartLink'saveCollection SQL = " +
                             query.getDebugSQL());
        }
        Collection coll = pservice.findValueInfo(query);
        if (coll.size() > 1) {
          if (VERBOSE) {
            System.out.println(TIME + coll);
          }
          throw new TechnicsRouteException(
              "saveListRoutePartLink'saveCollection��ͬһ�������һ��·�߱�汾�в������������������������ù�����");
        }
        Iterator iter1 = coll.iterator();
        if (iter1.hasNext()) {
          ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)
              iter1.next();
          //ɾ��������
          pservice.deleteValueInfo(listLinkInfo);
        }
       }
        ListRoutePartLinkInfo listLinkInfo = ListRoutePartLinkInfo.
            newListRoutePartLinkInfo(routeListInfo,
                                     partMasterID);
        //st skybird 2005.3.4 ���游����
        //  QMPartIfc partifc = (QMPartIfc) tmp[1];
        if (tmp[2] != null) {
          int count = ( (Integer) tmp[2]).intValue();
          listLinkInfo.setCount(count);
        }
       if(tmp[3]!=null){
    	   listLinkInfo.setColorFlag((String)tmp[3]);
       }
        if (parentPart != null) {
          listLinkInfo.setParentPartNum(parentPart.getPartNumber());
          listLinkInfo.setParentPartName(parentPart.getPartNumber());
          listLinkInfo.setParentPartID(panrentID);
        }
        //ed

         pservice.storeValueInfo(listLinkInfo);
      }
      //����Ҫɾ����������ϣ���ɾ��������ǡ�
      for (Iterator iter = deleteCollection.keySet().iterator(); iter.hasNext(); ) {
        Object[] tmp = (Object[]) deleteCollection.get(iter.next());
        String partMasterID = (String) tmp[0];
        QMPartIfc parentPart = (QMPartIfc) tmp[1];
        String panrentID = null;
        if (parentPart != null) {
          panrentID = parentPart.getBsoID();
        }
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID,
                                                 QueryCondition.EQUAL,
                                                 routeListInfo.getBsoID());
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition(RIGHTID,
                                                  QueryCondition.EQUAL,
                                                  partMasterID);
        query.addCondition(cond1);

        query.addAND();
        QueryCondition condP;
        if (panrentID != null && !panrentID.trim().equals("")) {
          condP = new QueryCondition("parentPartID",
                                     QueryCondition.EQUAL,
                                     panrentID);
        }
        else {
          condP = new QueryCondition("parentPartID",
                                     QueryCondition.IS_NULL);
        }
        query.addCondition(condP);

        //����
        query.addOrderBy("createTime", false);
        if (VERBOSE) {
          System.out.println(TIME +
                             "....saveListRoutePartLink routeListInfo.bsoID = " +
                             routeListInfo.getBsoID());
          System.out.println(TIME +
                             "saveListRoutePartLink partMasterID = " +
                             partMasterID);
          System.out.println(TIME +
                             "routeService's saveListRoutePartLink SQL = " +
                             query.getDebugSQL());
        }
        //
        Collection coll = pservice.findValueInfo(query);
        if (VERBOSE) {
          System.out.println(TIME +
                             "routeSevice's saveListRoutePartLink ԭ�й������ϣ� coll = " +
                             coll);
          //��Ϊ�п�������������������2
        }
        if (coll.size() > 2) {
          if (VERBOSE) {
            System.out.println(TIME + coll);
          }
          throw new TechnicsRouteException(
              "saveListRoutePartLink's deleteCollection�����ܶ�������������������ѡ�������");
        }
        //��ɾ��������ǻ�ɾ��������
        Iterator iter1 = coll.iterator();
        if (iter1.hasNext()) {
          ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)
              iter1.next();
          if (listLinkInfo.getAlterStatus() == INHERIT) {
            //skybird��δ�����ȫ������
            //������ǰ�İ汾Ϊȡ��״̬��
            QMQuery query1 = new QMQuery(
                LIST_ROUTE_PART_LINKBSONAME);
            QueryCondition cond2 = new QueryCondition(
                "routeListMasterID",
                QueryCondition.EQUAL,
                routeListInfo.getMaster().getBsoID());
            query1.addCondition(cond2);
            query1.addAND();
            QueryCondition cond3 = new QueryCondition(RIGHTID,
                QueryCondition.EQUAL, partMasterID);
            query1.addCondition(cond3);
            query1.addAND();
            QueryCondition cond4 = new QueryCondition("adoptStatus",
                QueryCondition.EQUAL,
                RouteAdoptedType.ADOPT.toString());
            query1.addCondition(cond4);
            //////////////////�ų��Լ�, �����п������ʱ������� 2004.9.11 ������
            query1.addAND();
            QueryCondition cond5 = new QueryCondition(LEFTID,
                QueryCondition.NOT_EQUAL,
                routeListInfo.getBsoID());
            query1.addCondition(cond5);
            query1.addAND();
            ////////////////////////    Ӧ��֤��ͬһ����֧��        2004.9.11 ������  versionID=A ����A.1 A.2
            QueryCondition cond6 = new QueryCondition("initialUsed",
                QueryCondition.EQUAL,
                routeListInfo.getVersionID());
            query1.addCondition(cond6);
            //bengin,mended by skybird,2005,1,19,
            query1.addAND();
            QueryCondition cond7 = new QueryCondition("alterStatus",
                QueryCondition.EQUAL, ROUTEALTER);
            query1.addCondition(cond7);
            //end
            //gcy add  2005/05/19
            query.addAND();
            QueryCondition condPn;
            if (panrentID != null && !panrentID.trim().equals("")) {
              condPn = new QueryCondition("parentPartID",
                                          QueryCondition.EQUAL,
                                          panrentID);
            }
            else {
              condPn = new QueryCondition("parentPartID",
                                          QueryCondition.IS_NULL);
            }
            query.addCondition(condPn);
            //gcy add  2005/05/19 end

            if (VERBOSE) {
              System.out.println(TIME +
                                 "routeSevice's saveListRoutePartLink INHERIT SQL = " +
                                 query1.getDebugSQL());
            }
            Collection coll1 = pservice.findValueInfo(query1);
            if (VERBOSE) {
              System.out.println(TIME +
                                 "routeSevice's saveListRoutePartLink INHERIT coll = " +
                                 coll1);
            }
            if (coll1.size() > 1) {
              throw new TechnicsRouteException(
                  "saveListRoutePartLink����������������״̬");
            }
            Iterator iter2 = coll1.iterator();
            if (iter2.hasNext()) {
              ListRoutePartLinkIfc listLinkInfo1 = (
                  ListRoutePartLinkIfc) iter2.
                  next();
              if (VERBOSE) {
                System.out.println(TIME +
                                   "saveListRoutePartLink deleteCollection INHERIT: " +
                                   coll +
                                   " , linkInfo.BsoID = " +
                                   listLinkInfo1.getBsoID());
              }
              listLinkInfo1.setAdoptStatus(RouteAdoptedType.
                                           CANCEL.getDisplay());
              pservice.saveValueInfo(listLinkInfo1);
            }
          }
          else if (listLinkInfo.getAlterStatus() == ROUTEALTER) {
            if (VERBOSE) {
              System.out.println(TIME +
                                 "saveListRoutePartLink deleteCollection ROUTEALTER: linkBsoID = " +
                                 listLinkInfo.getBsoID());
            }
            if (listLinkInfo.getRouteID() != null) {
              //skybird�о����˳�򲻶�
              deleteRoute(listLinkInfo);
              listLinkInfo.setRouteID(null);
            }
          }
          else {
            throw new TechnicsRouteException(
                "saveListRoutePartLink������ɾ��״̬�������Ӧ���֡�");
          }
          //û���½�����������ɾ����ǡ�
          if (coll.size() == 1) {
            if (VERBOSE) {
              System.out.println(TIME +
                                 "if(coll.size() == 1) û���½�����������ɾ����ǡ�");
            }
            Collection preLinks = this.searchPreLink(routeListInfo,
                partMasterID, panrentID);
            if (preLinks != null && preLinks.size() > 0) {
              if (VERBOSE) {
                System.out.println(TIME + "��ǰ�汾���д˹�������������Ϊ�Ѿ�ɾ��״̬");
              }
              listLinkInfo.setAlterStatus(PARTDELETE);
              //��ɾ�����Ƿ����״̬����Ϊȡ����
              listLinkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.
                                          getDisplay());
              pservice.saveValueInfo(listLinkInfo);
            }
            else {
              if (VERBOSE) {
                System.out.println(TIME + "��ǰ�汾��û�д˹���������ɾ���ι���");
              }
              pservice.deleteValueInfo(listLinkInfo);
            }
          }
          //�����½�������//ɾ���˹�����
          else if (coll.size() == 2) {
            if (VERBOSE) {
              System.out.println(TIME +
                                 "else if(coll.size() == 2) �����½�������//ɾ���˹�����");
            }
            pservice.deleteValueInfo(listLinkInfo);
          }
        }
      }
    }
    catch (Exception e) {
      this.setRollbackOnly();
      throw new TechnicsRouteException(e);
    }
    //Collection result = getRouteListLinkParts(routeListInfo.getBsoID());
    //return result;
  }
  
  //CCBegin by leixiao 2008-8-5 ԭ�򣺽����������·��
  public void saveListRoutePartLink(HashMap saveCollection,
                                    ArrayList updateLinksList,
                                    HashMap deleteCollection,
                                    //CCBegin SS3
                                    //TechnicsRouteListIfc routeListInfo1) throws
                                    TechnicsRouteListIfc routeListInfo1,
                                    //CCBegin SS12
//                                    boolean addRouteFlag) throws
                                    //CCBegin SS28
                                    //boolean addRouteFlag,boolean saveAs) throws
                                    boolean addRouteFlag,boolean saveAs, ArrayList arrayList, Vector vec) throws
                                    //CCEnd SS28
                                    //CCEnd SS12
                                    //CCEnd SS3
      QMException {

    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's saveListRoutePartLink......................... saveCollection = "
                         + saveCollection + ", deleteCollection = " +
                         deleteCollection);
    }
    //����·�߱�
  
    TechnicsRouteListIfc routeListInfo = updateRouteList(routeListInfo1);
    if (VERBOSE) {
      if (routeListInfo1.getBsoID() == routeListInfo.getBsoID()) {
        System.out.println("���º�����õĶ������ǰ�Ķ����bsoidһ��");
      }
      else {
        System.out.println("���º�����õĶ������ǰ�Ķ����bsoid��һ��");
      }
    }

    //�����ݿ�ֱ��ȡ���������������
    HashMap countMap = new HashMap();
    //CCBegin SS11
    HashMap color = new HashMap();
    //CCEnd SS11
    Vector indexVector = routeListInfo.getPartIndex();
    
    //У�鴫���㲿���Ƿ���partindex�и���һ��
    int picount = 0;//indexVector�������
    int acount = 0;//����������
    int ucount = 0;//���µ�����
    int dcount = 0;//ɾ��������
    int ycount = 0;//·��ԭ����
    if(indexVector!=null)
    {
    	picount = indexVector.size();
    }
    if(saveCollection!=null)
    {
    	acount = saveCollection.size();
    }
    if(updateLinksList!=null)
    {
    	ucount = updateLinksList.size();
    }
    if(deleteCollection!=null)
    {
    	dcount = deleteCollection.size();
    }
    Collection linkcoll = getRouteListLinkParts(routeListInfo);
    if(linkcoll!=null)
    {
    	ycount = linkcoll.size();
    }
    System.out.println("������׼"+routeListInfo.getRouteListNumber()+"("+routeListInfo.getBsoID()+")ʱ������Ϊ��"+picount+"=="+acount+"=="+ucount+"=="+dcount+"=="+ycount);
    if(picount!=(acount+ycount-dcount))
    {
    	System.out.println("�������ԣ������㲿���б��Ѿ�����������");
    }
    
    if (indexVector != null && indexVector.size() > 0) {
      int size2 = indexVector.size();
      for (int k = 0; k < size2; k++) {
        String[] ids = (String[]) indexVector.elementAt(k);
        String key = "";
        if (countMap.containsKey(ids[0])) {
          key = ids[0] + "K" + k;
        }
        else {
          key = ids[0];
        }
      //  System.out.println("TechnicsRouteServiceEJB ids[0]="+ids[0]+" ids[1]="+ids[1]+" ids[2]="+ids[2]);
        countMap.put(key, ids[2]); //����������ظ��Ŀ��ܣ���Ҫ��������
        //CCBegin SS11
        color.put(key, ids[4]); 
        //CCEnd 11
      }
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    try {


      //����Ҫɾ����������ϣ���ɾ��������ǡ�
       for (Iterator iter = deleteCollection.keySet().iterator(); iter.hasNext(); )
       {
         Object[] tmp = (Object[]) deleteCollection.get(iter.next());
         String partMasterID = (String) tmp[0];
         QMPartIfc parentPart = (QMPartIfc) tmp[1];
         String routeID = (String) tmp[2];
//	      CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
         String partID= (String) tmp[3];
//         System.out.println("partid="+partID);
//	      CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
         String panrentID = null;
         if (parentPart != null) {
           panrentID = parentPart.getBsoID();
         }
//         System.out.println("����ɾ���� PartMasterID = "+partMasterID);
//         System.out.println("       �� routeID = "+routeID);
//         System.out.println("       �� panrentID = "+panrentID);
         QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
         QueryCondition cond = new QueryCondition(LEFTID,
                                                  QueryCondition.EQUAL,
                                                  routeListInfo.getBsoID());
         query.addCondition(cond);
         query.addAND();
         QueryCondition cond1 = new QueryCondition(RIGHTID,
                                                   QueryCondition.EQUAL,
                                                   partMasterID);
         query.addCondition(cond1);

         query.addAND();
//	      CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id 
         if (partID != null && !partID.trim().equals("")){
        	 query.addLeftParentheses();
             QueryCondition cond21 = new QueryCondition("partBranchID",
            		 QueryCondition.IS_NULL);
             query.addCondition(cond21);
             query.addOR();
         QueryCondition cond2 = new QueryCondition("partBranchID",
                 QueryCondition.EQUAL,
                 partID);
         query.addCondition(cond2);
         query.addRightParentheses();
         query.addAND();
         }
//	      CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id  
         /**20070912 ע�͵� ��Ϊ���������� liuming
         QueryCondition condP;
         if (panrentID != null && !panrentID.trim().equals("")) {
           condP = new QueryCondition("parentPartID",
                                      QueryCondition.EQUAL,
                                      panrentID);
         }
         else {
           condP = new QueryCondition("parentPartID",
                                      QueryCondition.IS_NULL);
         }
         query.addCondition(condP);
         query.addAND();*/


         QueryCondition condP1;
         if (routeID != null && !routeID.trim().equals("")) {
           condP1 = new QueryCondition("routeID",
                                       QueryCondition.EQUAL,
                                       routeID);
         }
         else {
           condP1 = new QueryCondition("routeID",
                                       QueryCondition.IS_NULL);
         }

         query.addCondition(condP1);
        //System.out.print("sql="+ query.getDebugSQL());
         Collection coll = pservice.findValueInfo(query);
         Iterator iter1 = coll.iterator();
         if (iter1.hasNext()) {
           ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)
               iter1.next();
           //System.out.println("ɾ���� Part = "+listLinkInfo.getPartMasterInfo().getPartNumber());
           //System.out.println("ɾ��ǰ�� link = "+listLinkInfo.getBsoID());
           pservice.deleteValueInfo(listLinkInfo);
//           System.out.println("ɾ���� link = "+listLinkInfo);
         }
       }



      for(Iterator iter = updateLinksList.iterator();iter.hasNext();)
      {
        String linkID = iter.next().toString();
        ListRoutePartLinkInfo linkInfo = (ListRoutePartLinkInfo)pservice.refreshInfo(linkID,false);
        String partmasterID = linkInfo.getPartMasterID();

        //��ʼ�����������ڳ�����ĵ�ʹ������
        int countInProduct = 0;
        String countString = "";
        if(countMap.containsKey(partmasterID))
        {
          countString = countMap.get(partmasterID).toString();
          countMap.remove(partmasterID);
        }
        else
        {
            for(Iterator jt= countMap.keySet().iterator();jt.hasNext();)
            {
              String keya = jt.next().toString();
              if(keya.startsWith(partmasterID))
              {
                partmasterID = keya;
                countString = countMap.get(partmasterID).toString();
                countMap.remove(partmasterID);
                break;
              }
            }
        }
        //CCBegin SS11
        String colorFlag="";
        if(color.containsKey(partmasterID)){
        	colorFlag=color.get(partmasterID).toString();
        }else{
        	for(Iterator jt= color.keySet().iterator();jt.hasNext();)
            {
              String keya = jt.next().toString();
              if(keya.startsWith(partmasterID))
              {
                colorFlag = color.get(keya).toString();
                break;
              }
            }
        }
        linkInfo.setColorFlag(colorFlag); 
//CCEnd SS11
        if (countString.trim().equals("")) {
        }
        else {
          countInProduct = Integer.parseInt(countString);
        }
        //////////////�����������
        linkInfo.setCount(countInProduct);
       // System.out.println("���£� Part = "+linkInfo.getPartMasterInfo().getPartNumber());
        pservice.updateValueInfo(linkInfo,false);
       // System.out.println("���º� PartMaster = "+linkInfo.getPartMasterInfo().getPartNumber()+"    count = "+linkInfo.getCount());
      }

      for (Iterator iter = saveCollection.keySet().iterator(); iter.hasNext(); )
      {
    	
        Object[] tmp = (Object[]) saveCollection.get(iter.next());

        String partMasterID = (String) tmp[0];
        QMPartIfc parentPart = (QMPartIfc) tmp[1];
//      CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
        String partid=(String) tmp[2];
        String routeid=(String) tmp[3];
        String iscomplete=(String) tmp[4];
        //CCBegin SS11
        String colorFlag="";
        for(Iterator jt= color.keySet().iterator();jt.hasNext();)
        {
          String keya = jt.next().toString();
          if(keya.startsWith(partMasterID))
          {
        	colorFlag = color.get(keya).toString();
          }
        }
//CCEnd SS11
//        System.out.println("colorFlag=="+tmp[5]);
        // System.out.println("comand partid="+partid);
//      CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id  
        String panrentID = null;
        if (parentPart != null) {
          panrentID = parentPart.getBsoID();
        }
        ListRoutePartLinkInfo listLinkInfo = ListRoutePartLinkInfo.
            newListRoutePartLinkInfo(routeListInfo,
                                     partMasterID,partid);

        if (parentPart != null) {
          listLinkInfo.setParentPartNum(parentPart.getPartNumber());
          listLinkInfo.setParentPartName(parentPart.getPartNumber());
          listLinkInfo.setParentPartID(panrentID);
        }
        //CCBegin SS11
       listLinkInfo.setColorFlag(colorFlag);
       //CCEnd SS11
        //�ڹ������в�����������
      /**  int countInProduct = 0;
        String countString = "";
        if(countMap.containsKey(partMasterID))
        {
          countString = countMap.get(partMasterID).toString();
          countMap.remove(partMasterID);
        }
        else
        {
            for(Iterator jt= countMap.keySet().iterator();jt.hasNext();)
            {
              String keya = jt.next().toString();
              if(keya.startsWith(partMasterID))
              {
                partMasterID = keya;
                countString = countMap.get(partMasterID).toString();
                countMap.remove(partMasterID);
                break;
              }
            }
        }

        if (countString.trim().equals("")) {
        }
        else {
          countInProduct = Integer.parseInt(countString);
        }

        //System.out.println("�½��� PartMaster = "+partMasterID+"    count = "+countInProduct);
       listLinkInfo.setCount(countInProduct);*/
    //////������������


        listLinkInfo =(ListRoutePartLinkInfo) pservice.saveValueInfo(listLinkInfo);
        //����·��
        //CCBegin SS3
        //if(iscomplete.equals("y")){
        if(iscomplete.equals("y")||addRouteFlag){
        //CCEnd SS3
       //System.out.println("------�ձ�,��ø���Դ ���="+partMasterID+" "+partid+" routeid="+routeid);
        //�������ӵ����	
        if(routeid==null||routeid.trim().equals(""))
        {
            //CCBegin SS5
            QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
            int j = query1.appendBso(TECHNICSROUTE_BSONAME, false);
            QueryCondition cond1 = new QueryCondition(RIGHTID,
                                                      QueryCondition.EQUAL,
                                                      partMasterID);
            //query1.addCondition(cond1);
            query1.addCondition(0, cond1);

            query1.addAND();
            QueryCondition cond3 = new QueryCondition("routeID",
                    QueryCondition.NOT_NULL);
            //query1.addCondition(cond3);
            query1.addCondition(0, cond3);
            query1.addAND();

    	      QueryCondition cond4 = new QueryCondition("alterStatus",
    	                                              QueryCondition.EQUAL,
    	                                              ROUTEALTER);
    	      query1.addCondition(0, cond4);
            query1.addAND();
            
            QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
            query1.addCondition(0, j, cond6);
            query1.addAND();
            
            QueryCondition cond7 = new QueryCondition("modefyIdenty", QueryCondition.NOT_EQUAL, "Coding_221664");
            query1.addCondition(j, cond7);
            
            //query1.addOrderBy("modifyTime",true);
            query1.addOrderBy(0, "modifyTime",true);
            
            //System.out.println("��ȡ�㲿������·��SQL��"+query1.getDebugSQL());
            //CCEnd SS5
            
           Collection c=(Collection) pservice.findValueInfo(query1, false);
           Iterator i=c.iterator();
           if(i.hasNext()){
        	   ListRoutePartLinkInfo link=(ListRoutePartLinkInfo) i.next();        	   
        	   routeid= link.getRouteID();
        	  // System.out.println("----�õ���link="+link+" routeid="+routeid);
           }
           if(routeid!=null&&!routeid.trim().equals(""))
           {
        	   //  //CCBegin by leixiao 2011-1-12 ԭ��:���·�������� 
        	   //���㲿������ʱ����������Ĭ�ϲ��á�C����
        	   listLinkInfo=(ListRoutePartLinkInfo)copyRoute(routeid,listLinkInfo);
//               RouteIfc route=pservice.findValueInfo(listLinkInfo.getRouteID());
             //  System.out.println(listLinkInfo+"   "+listLinkInfo.getRouteID()+""+modefyidentycoding);
               
            //CCBegin SS3
            if(iscomplete.equals("y"))
            {
            //CCEnd SS3
               TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) pservice.
               refreshInfo(listLinkInfo.getRouteID());
               
               if(routeInfo.getModefyIdenty().getCodeContent().equals("����")){  
            	   modefyidentycoding=routeInfo.getModefyIdenty();
               }
               else{            	   
               if(modefyidentycoding==null){
            	 CodingManageService codingservice = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
            	 modefyidentycoding=codingservice.findCodingByContent("����",
                            "���ı��",
                            "����·��");             	
               }
               routeInfo.setModefyIdenty(modefyidentycoding);//��ɲ���
               routeInfo = (TechnicsRouteIfc) pservice.saveValueInfo(
            	          routeInfo);
               }
               
            }
           }
           //CCEnd by leixiao 2011-1-12 ԭ��:���·��������
          //CCBegin SS47
          else
          {
          	QMPartIfc linkpart=(QMPartIfc)pservice.refreshInfo(listLinkInfo.getPartBranchID());
          	System.out.println("״̬��"+linkpart.getLifeCycleState().getDisplay());
          	System.out.println("˵����"+linkpart.getIterationNote());
          	if(linkpart.getLifeCycleState().getDisplay().equals("����")&&linkpart.getIterationNote()!=null&&linkpart.getIterationNote().equals("��������"))
          	{
          		copyRoute("TechnicsRoute_171044558",listLinkInfo);
          	}
          }
          //CCEnd SS47
          }
        
        //����׼��ӵ����
        else{
            copyRoute(routeid,listLinkInfo);
        }

      }
       //CCBegin SS12
         if(saveAs){
        	QMPartIfc prePart=findPrePart(listLinkInfo.getPartBranchID());
        	
        	if(prePart!=null){
        		  QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
                  int j = query1.appendBso(TECHNICSROUTE_BSONAME, false);
                  QueryCondition cond1 = new QueryCondition("partBranchID",
                                                            QueryCondition.EQUAL,
                                                            prePart.getBsoID());
                  query1.addCondition(0, cond1);

                  query1.addAND();
                  QueryCondition cond2 = new QueryCondition("routeID",
                          QueryCondition.NOT_NULL);
                  query1.addCondition(0, cond2);
                  query1.addAND();

          	      QueryCondition cond3 = new QueryCondition("alterStatus",
          	                                              QueryCondition.EQUAL,
          	                                              ROUTEALTER);
          	      query1.addCondition(0, cond3);
                  query1.addAND();
                  
                  QueryCondition cond4 = new QueryCondition("routeID", "bsoID");
                  query1.addCondition(0, j, cond4);
                  query1.addAND();
                  
                  QueryCondition cond5 = new QueryCondition("modefyIdenty", QueryCondition.NOT_EQUAL, "Coding_221664");
                  query1.addCondition(j, cond5);
                  
                  query1.addOrderBy(0, "modifyTime",true);
                  
       
                 Collection c=(Collection) pservice.findValueInfo(query1, false);
                 Iterator i=c.iterator();
                 if(i.hasNext()){
              	   ListRoutePartLinkInfo link=(ListRoutePartLinkInfo) i.next();        	   
              	   routeid= link.getRouteID();
              	 
                 }
                 if(routeid!=null&&!routeid.equals("")){
                	listLinkInfo=(ListRoutePartLinkInfo)copyRoute(routeid,listLinkInfo); 
                 }
        	}
        	
        }
        //CCEnd SS12
    }
    
    //CCBegin SS28
          createAssisFile(routeListInfo,arrayList);
          //  ɾ����������
          if (vec != null)
          {
          	Vector appDataVec = refreshAppInfo(vec);
          	deleteApplicationData(routeListInfo, appDataVec);
          }
          //CCEnd SS28
  }
    catch (Exception e) {
      e.printStackTrace();
      this.setRollbackOnly();
      throw new TechnicsRouteException(e);
    }
  }
//CCBegin SS13
  private QMPartIfc findPrePart(String partID)
  {
      try
      {
          PersistService pService = (PersistService) EJBServiceHelper.
              getService("PersistService");
          QMQuery query = new QMQuery("MakeFromLink");
          QueryCondition condition = new QueryCondition("leftBsoID","=",partID);
          query.addCondition(condition);
          Collection link = (Collection)pService.findValueInfo(query);
          if(link != null && (link.iterator()).hasNext())
          {
              Iterator iter = link.iterator();
              MakeFromLinkIfc mlink = (MakeFromLinkIfc)iter.next();
              String partbsoID = mlink.getRightBsoID();
              BaseValueIfc part = pService.refreshInfo(partbsoID);
              return (QMPartIfc)part;
          }

      }catch(QMException e){
          e.printStackTrace();
      }
      return null;
  }
  //CCEnd SS13
//CCEnd by leixiao 2008-8-5 ԭ�򣺽����������·��

  private Collection searchPreLink(TechnicsRouteListIfc routeListInfo,
                                   String partMasterID, String parentID) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query1 = new QMQuery(
        LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond2 = new QueryCondition(
        "routeListMasterID",
        QueryCondition.EQUAL,
        routeListInfo.getMaster().getBsoID());
    query1.addCondition(cond2);
    query1.addAND();
    QueryCondition cond3 = new QueryCondition(RIGHTID,
                                              QueryCondition.EQUAL,
                                              partMasterID);
    query1.addCondition(cond3);
    //////////////////�ų��Լ�
    query1.addAND();
    QueryCondition cond5 = new QueryCondition(LEFTID,
                                              QueryCondition.NOT_EQUAL,
                                              routeListInfo.getBsoID());
    query1.addCondition(cond5);
    query1.addAND();
    ////////////////////////    Ӧ��֤��ͬһ����֧��    versionID=A ����A.1 A.2
    QueryCondition cond6 = new QueryCondition("initialUsed",
                                              QueryCondition.EQUAL,
                                              routeListInfo.getVersionID());
    query1.addCondition(cond6);
    query1.addAND();
    QueryCondition condPn;
    if (parentID != null && !parentID.trim().equals("")) {
      condPn = new QueryCondition("parentPartID",
                                  QueryCondition.EQUAL,
                                  parentID);
    }
    else {
      condPn = new QueryCondition("parentPartID",
                                  QueryCondition.IS_NULL);
    }
    query1.addCondition(condPn);
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeSevice's saveListRoutePartLink INHERIT SQL = " +
                         query1.getDebugSQL());
    }
    Collection coll1 = pservice.findValueInfo(query1);
    return coll1;

  }

  /**
   * ���¸��������
   * @param PartsToChange Collection  Ҫ���µĸ�������
   * @param routeListInfo1 TechnicsRouteListIfc ·�߱�ֵ����
   * @throws QMException
   * @see TechnicsRouteListInfo
   */
  // * ������gcy 05.04.29��
  // * added by skybird 2005.3.4
  public void updateListRoutePartLink(Collection PartsToChange,
                                      TechnicsRouteListIfc routeListInfo1) throws
      QMException {
    TechnicsRouteListIfc routeListInfo = updateRouteList(routeListInfo1);
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    try {
      for (Iterator iter = PartsToChange.iterator(); iter.hasNext(); ) {
        Object[] tmp = (Object[]) (iter.next());
        String partMasterID = (String) tmp[0];
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID,
                                                 QueryCondition.EQUAL,
                                                 routeListInfo.getBsoID());
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition(RIGHTID,
                                                  QueryCondition.EQUAL,
                                                  partMasterID);
        query.addCondition(cond1);
        Collection coll = pservice.findValueInfo(query);
        Iterator iter1 = coll.iterator();
        if (iter1.hasNext()) {
          ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)
              iter1.next();
          listLinkInfo.setParentPartNum( (String) tmp[1]);
          pservice.updateValueInfo(listLinkInfo);
        }
      }
    }
    catch (Exception e) {
      this.setRollbackOnly();
      throw new TechnicsRouteException(e);
    }

  }

  /**
   * ����·�߱�(������)
   * @param param Object[][] ��ά���飬5��Ԫ�ء�����obj[0]=��ţ�obj[1]=true. true=�ǣ�
   * false=�ǡ�����˳�򣺱�š����ơ����𣨺��֣������ڲ�Ʒ���汾�š�
   * @throws QMException
   * @return Collection ���obj[]:����·�߱���������routelistֵ����<br>
   * ��ʱҪ��ConfigService���й��ˡ�
   */

  public Collection findRouteLists(Object[][] param) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(ROUTELIST_BSONAME);
    //zz ���ò�ѯʱ�Ƿ���Դ�Сд
     boolean ignore =   ( (Boolean) param[5][0]).booleanValue();
     query.setIgnoreCase(ignore);
    int n = query.appendBso(PARTMASTER_BSONAME, false);
    query.setChildQuery(false);
    String number = (String) param[0][0];
    boolean numberFlag = ( (Boolean) param[0][1]).booleanValue();
    if (number != null && number.trim().length() != 0) {
      QueryCondition cond = RouteHelper.handleWildcard("routeListNumber",
          number, numberFlag);
      query.addCondition(0, cond);
      query.addAND();
    }
    String name = (String) param[1][0];
    boolean nameFlag = ( (Boolean) param[1][1]).booleanValue();
    if (name != null && name.trim().length() != 0) {
      QueryCondition cond1 = RouteHelper.handleWildcard("routeListName",
          name,
          nameFlag);
      query.addCondition(0, cond1);
      query.addAND();
    }
 //  CCBegin by leixiao 2009-4-1 ԭ�򣺽����������·��,���𻻳���� 
//    String level_zh = (String) param[2][0];
//    if (level_zh != null && level_zh.trim().length() != 0) {
//      String level = RouteHelper.getValue(RouteListLevelType.
//                                          getRouteListLevelTypeSet(),
//                                          level_zh);
//      QueryCondition cond4 = new QueryCondition("routeListLevel",
//                                                QueryCondition.EQUAL,
//                                                level);
//      query.addCondition(cond4);
//      query.addAND();
//    }
    String routeListState = (String) param[2][0];
    if (routeListState != null && routeListState.trim().length() != 0) {

      QueryCondition cond4 = new QueryCondition("routeListState",
                                                QueryCondition.EQUAL,
                                                routeListState);
      query.addCondition(cond4);
      query.addAND();
    }
//  CCEnd by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձ� 
    String productNumber = (String) param[3][0];
    boolean productNumberFlag = ( (Boolean) param[3][1]).booleanValue();
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
    boolean versionFlag = ( (Boolean) param[4][1]).booleanValue();
    //����汾ǡ��Ϊ���°棬�����ѳ��������ϼеĶ�����
    if (version != null && version.trim().length() != 0) {
      QueryCondition cond6 = RouteHelper.handleWildcard("versionID",
          version,
          versionFlag);
      query.addCondition(0, cond6);
      query.addAND();
    }
    QueryCondition cond12 = new QueryCondition("iterationIfLatest",
                                               Boolean.TRUE);
    query.addCondition(cond12);
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's findRouteLists else... clause, SQL = " +
                         query.getDebugSQL());
    }
    query.setDisticnt(true);
    query.setIsSeries(true);
    //addListOrderBy(query);
    //routelistֵ���󼯺ϡ�
    Collection result = pservice.findValueInfo(query);
    //���ݹ���״̬���й��ˡ�ԭ����������ͬʱ���ڡ���ù���·�߱�ֵ���󼯺ϡ�
    filterByWorkState(result);
    //����·�߱����������С�
    Collection sortedVec = RouteHelper.sortedInfos(result,
        "getRouteListNumber", false);
    return sortedVec;
  }

//�����Ʒ���������󣬽�����ʾ���Ƿ���Ҫ��
  private void hasPartNumber(String productNumber) throws QMException {
    Collection productMasterInfo = getProductMasterID(productNumber);
    if (productMasterInfo.size() == 0) {
      Object[] obj = new Object[] {
          productNumber};
      throw new TechnicsRouteException("5", obj);
    }
    if (VERBOSE) {
      System.out.println(TIME + "findRouteLists's productNumber = " +
                         productNumber +
                         ", productMasterInfo = " + productMasterInfo);
    }
  }

  //��������Ż�����masterID.
  private Collection getProductMasterID(String productNumber) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(PARTMASTER_BSONAME);
    //GenericPartMaster��������
    query.setChildQuery(false);
    QueryCondition cond = RouteHelper.handleWildcard("partNumber",
        productNumber);
    query.addCondition(cond);
    Collection coll = pservice.findValueInfo(query);
    return coll;
  }

  /**
   * ���ݹ���״̬���й��ˡ�ԭ����������ͬʱ���ڡ���ù���·�߱�ֵ���󼯺ϡ�
   * @param total Collection
   */
  private void filterByWorkState(Collection total) {
    Object[] obj = total.toArray();
    for (int i = 0; i < obj.length; i++) {
      TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) obj[i];
      if (listInfo.getWorkableState().trim().equals(CompareHandler.WRK)) {
        String decessorID = listInfo.getPredecessorID();
        //ȥ��ԭ��
        for (Iterator iter = total.iterator(); iter.hasNext(); ) {
          TechnicsRouteListIfc originalInfo = (TechnicsRouteListIfc)
              iter.next();
          if (decessorID.equals(originalInfo.getBsoID())) {
            total.remove(originalInfo);
            break;
          }
        }
      }
    }
  }

  private void addListOrderBy(QMQuery query) throws QMException {
    query.addOrderBy(0, "routeListNumber", false);
  }

  /**
   * ��������·�߱�������Χ����š����ơ�״̬�����𣨺��֣������ڲ�Ʒ��˵�������λ�á�
   * �������ڡ������ߡ������¡��汾�š�
   * @param routelistInfo TechnicsRouteListIfc ·�߱�ֵ����
   * @param productNumber String ���ڲ�Ʒ���
   * @param createTime String    ����ʱ��
   * @param modifyTime String    �޸�ʱ��
   * @throws QMException
   * @return Collection ���obj[]:����·�߱��������Ĺ���·�߱�ֵ�������������°汾��<br>
   * @see TechnicsRouteListInfo
   */
  public Collection findRouteLists(TechnicsRouteListIfc routelistInfo,
                                   String productNumber, String createTime,
                                   String modifyTime) throws QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's findRouteLists createTime = " +
                         createTime + ", modifyTime = " +
                         modifyTime);
    }
    if (routelistInfo == null) {
      throw new TechnicsRouteException("routelistInfo����Ϊ�ա�");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(ROUTELIST_BSONAME);
    int m = query.appendBso(new UserInfo().getBsoName(), false);
    int n = query.appendBso(PARTMASTER_BSONAME, false);
    query.setChildQuery(false);
    if (routelistInfo.getRouteListNumber() != null &&
        routelistInfo.getRouteListNumber().trim().length() != 0) {
      QueryCondition cond1 = RouteHelper.handleWildcard("routeListNumber",
          routelistInfo.getRouteListNumber());
      query.addCondition(0, cond1);
      query.addAND();
    }
    if (routelistInfo.getRouteListName() != null &&
        routelistInfo.getRouteListName().trim().length() != 0) {
      QueryCondition cond2 = RouteHelper.handleWildcard("routeListName",
          routelistInfo.getRouteListName());
      query.addCondition(0, cond2);
      query.addAND();
    }
    if (routelistInfo.getLifeCycleState() != null &&
        routelistInfo.getLifeCycleState().toString() != null &&
        routelistInfo.getLifeCycleState().toString().trim().length() != 0) {
      QueryCondition cond3 = new QueryCondition("lifeCycleState",
                                                QueryCondition.EQUAL,
                                                routelistInfo.getLifeCycleState().
                                                toString());
      query.addCondition(0, cond3);
      query.addAND();
    }
    String level_zh = routelistInfo.getRouteListLevel();
    if (level_zh != null && level_zh.trim().length() != 0) {
      String level = RouteHelper.getValue(RouteListLevelType.
                                          getRouteListLevelTypeSet(),
                                          level_zh);
      QueryCondition cond4 = new QueryCondition("routeListLevel",
                                                QueryCondition.EQUAL,
                                                level);
      query.addCondition(0, cond4);
      query.addAND();
    }
    if (routelistInfo.getRouteListDescription() != null &&
        routelistInfo.getRouteListDescription().trim().length() != 0) {
      QueryCondition cond5 = RouteHelper.handleWildcard(
          "routeListDescription",
          routelistInfo.getRouteListDescription());
      query.addCondition(0, cond5);
      query.addAND();
    }
    if (routelistInfo.getLocation() != null &&
        routelistInfo.getLocation().trim().length() != 0) {
      QueryCondition cond6 = new QueryCondition("location",
                                                QueryCondition.EQUAL,
                                                routelistInfo.getLocation());
      query.addCondition(0, cond6);
      query.addAND();
    }
    if (createTime != null && createTime.trim().length() != 0) {
      RouteHelper.handleTimeQuery(query, createTime, "createTime");
    }
    if (routelistInfo.getIterationCreator() != null &&
        routelistInfo.getIterationCreator().trim().length() != 0) {
      //////����routelistInfo.getIterationCreator()�����û�������ȫ�ơ�
      query.addLeftParentheses();
      QueryCondition cond7 = RouteHelper.handleWildcard("usersDesc",
          routelistInfo.getIterationCreator());
      query.addCondition(m, cond7);
      query.addOR();
      QueryCondition cond8 = RouteHelper.handleWildcard("usersName",
          routelistInfo.getIterationCreator());
      query.addCondition(m, cond8);
      query.addRightParentheses();
      query.addAND();
      QueryCondition cond80 = new QueryCondition("iterationCreator",
                                                 "bsoID");
      query.addCondition(0, m, cond80);
      query.addAND();
    }
    if (modifyTime != null && modifyTime.trim().length() != 0) {
      RouteHelper.handleTimeQuery(query, modifyTime, "modifyTime");
    }
    if (productNumber != null && productNumber.trim().length() != 0) {
      //�����Ʒ���������󣬽�����ʾ���Ƿ���Ҫ��
      //hasPartNumber(productNumber);
      QueryCondition cond10 = RouteHelper.handleWildcard("partNumber",
          productNumber);
      query.addCondition(n, cond10);
      query.addAND();
      QueryCondition cond100 = new QueryCondition("productMasterID",
                                                  "bsoID");
      query.addCondition(0, n, cond100);
      query.addAND();
      //if(VERBOSE)
      //System.out.println(TIME + "findRouteLists's for clause end......... SQL = " + query.getDebugSQL());
    }
    //����汾ǡ��Ϊ���°棬����Ȩ�ޣ������ѳ��������ϼеĶ����������������Ȼҵ���������
    if (routelistInfo.getVersionID() != null &&
        routelistInfo.getVersionID().trim().length() != 0) {
      //////////////////getVersionValue�ĳ�getVersionID 2004.9.11 ������
      QueryCondition cond11 = RouteHelper.handleWildcard("versionID",
          routelistInfo.getVersionID());
      query.addCondition(0, cond11);
      query.addAND();
      //routelistֵ���󼯺ϡ�
      if (VERBOSE) {
        System.out.println(TIME +
                           "routeService's findRouteLists if... clause, SQL = " +
                           query.getDebugSQL());
        //���������������й��ˣ��ݲ����С�
      }
    }
    QueryCondition cond12 = new QueryCondition("iterationIfLatest",
                                               Boolean.TRUE);
    query.addCondition(cond12);
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's findRouteLists else... clause, SQL = " +
                         query.getDebugSQL());
      //routelistֵ���󼯺ϡ�
    }
    query.setDisticnt(true);
    //addListOrderBy(query);
    Collection result = pservice.findValueInfo(query);
    if (VERBOSE) {
      System.out.println(TIME + "routeService's findRouteLists result = " +
                         result);
      //���ݹ���״̬���й��ˡ�ԭ����������ͬʱ���ڡ���ù���·�߱�ֵ���󼯺ϡ�
    }
    filterByWorkState(result);
    //����·�߱����������С�
    Collection sortedVec = RouteHelper.sortedInfos(result,
        "getRouteListNumber", false);
    if (VERBOSE) {
      System.out.println(TIME +
                         "exit routeService's findRouteLists sortedVec = " +
                         sortedVec);
    }
    return sortedVec;
  }

  /**
   * @roseuid 402CBAF700CA
   * @J2EE_METHOD  --  findRouteLists
   * ��ù���·�߱�������Χ����š����ơ�״̬���������ڲ�Ʒ��˵�������λ�á��������ڡ������ߡ������¡��汾�š�
   * @return collection ����·�߱�ֵ�������°汾��
   * ��ʱҪ��ConfigService���й��ˡ�
         public Collection findRouteLists(TechnicsRouteListIfc routelistInfo, String productNumber) throws QMException
         {
        Collection result = new Vector();
   PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
   QMQuery query = new QMQuery(ROUTELIST_BSONAME, ROUTELISTMASTER_BSONAME);
        if(routelistInfo.getRouteListNumber()!=null&&routelistInfo.getRouteListNumber().trim().length()!=0)
        {
            QueryCondition cond1 = new QueryCondition("routeListNumber", QueryCondition.EQUAL,
                routelistInfo.getRouteListNumber());
            query.addCondition(0, cond1);
            query.addAND();
        }
        if(routelistInfo.getRouteListName()!=null&&routelistInfo.getRouteListName().trim().length()!=0)
        {
   QueryCondition cond2 = new QueryCondition("routeListName", QueryCondition.EQUAL,
                routelistInfo.getRouteListName());
            query.addCondition(0, cond2);
            query.addAND();
        }
        if(routelistInfo.getLifeCycleState().toString()!=null&&
            routelistInfo.getLifeCycleState().toString().trim().length()!=0)
        {
            QueryCondition cond3 = new QueryCondition("lifeCycleState", QueryCondition.EQUAL,
                routelistInfo.getLifeCycleState().toString());
            query.addCondition(0, cond3);
            query.addAND();
        }
        if(routelistInfo.getRouteListLevel()!=null&&routelistInfo.getRouteListLevel().trim().length()!=0)
        {
            QueryCondition cond4 = new QueryCondition("routeListLevel", QueryCondition.EQUAL,
                routelistInfo.getRouteListLevel());
            query.addCondition(0, cond4);
            query.addAND();
        }
        if(routelistInfo.getRouteListDescription()!=null&&routelistInfo.getRouteListDescription().trim().length()!=0)
        {
            QueryCondition cond5 = new QueryCondition("routeListDescription", QueryCondition.EQUAL,
                routelistInfo.getRouteListDescription());
            query.addCondition(0, cond5);
            query.addAND();
        }
        if(routelistInfo.getLocation()!=null&&routelistInfo.getLocation().trim().length()!=0)
        {
            QueryCondition cond6 = new QueryCondition("location", QueryCondition.EQUAL, routelistInfo.getLocation());
            query.addCondition(0, cond6);
            query.addAND();
        }
        if(routelistInfo.getCreateTime()!=null)
        {
        //QueryCondition cond7 = new QueryCondition("createTime", QueryCondition.EQUAL, routelistInfo.getCreateTime());
        //query.addCondition(0, cond7);
        //query.addAND();
        }
        if(routelistInfo.getCreator()!=null&&routelistInfo.getCreator().trim().length()!=0)
        {
            QueryCondition cond8 = new QueryCondition("creator", QueryCondition.EQUAL, routelistInfo.getCreator());
            query.addCondition(0, cond8);
            query.addAND();
        }
        if(routelistInfo.getModifyTime()!=null)
        {
        //QueryCondition cond9 = new QueryCondition("modifyTime", QueryCondition.EQUAL, routelistInfo.getModifyTime());
        //query.addCondition(0, cond9);
        //query.addAND();
        }
        if(productNumber!=null&&productNumber.trim().length()!=0)
        {
            Collection productMasterInfo = getProductMasterID(productNumber);
           for(Iterator iter = productMasterInfo.iterator(); iter.hasNext(); )
            {///////////////////////////////����������������������������������
                QueryCondition cond10 = new QueryCondition("productMasterID", QueryCondition.EQUAL,
                    ((QMPartMasterIfc)iter.next()).getBsoID());
                query.addCondition(0, cond10);
                query.addOR();
            }
        }
        //����汾ǡ��Ϊ���°棬�����ѳ��������ϼеĶ�����
        if(routelistInfo.getVersionValue()!=null&&routelistInfo.getVersionValue().trim().length()!=0)
        {
   QueryCondition cond11 = new QueryCondition("versionValue", QueryCondition.EQUAL,
                routelistInfo.getVersionValue());
            query.addCondition(0, cond11);
            query.setDisticnt(true);
            query.setVisiableResult(1);
            //routelistֵ���󼯺ϡ�
            if(VERBOSE)
                System.out.println(TIME + "routeService's findRouteLists if... clause, SQL = " + query.getDebugSQL());
            result = pservice.findValueInfo(query);
            //���������������й��ˣ��ݲ����С�
        }
        else
        {
   QueryCondition cond11 = new QueryCondition("iterationIfLatest", Boolean.TRUE);
            query.addCondition(0, cond11);
            query.addAND();
           QueryCondition cond12 = new QueryCondition("masterBsoID", "bsoID");
            query.addCondition(0, 1, cond12);
            query.setDisticnt(true);
            query.setVisiableResult(0);
            if(VERBOSE)
     System.out.println(TIME + "routeService's findRouteLists else... clause, SQL = " + query.getDebugSQL());
     //routelistMasterֵ���󼯺ϡ�
            Collection coll = pservice.findValueInfo(query);
            for(Iterator iter = coll.iterator(); iter.hasNext(); )
            {
            //���˺�Ľ����
                TechnicsRouteListIfc routeListInfo = (TechnicsRouteListIfc)getLatestVesion((TechnicsRouteListMasterIfc)
                    iter.next());
                result.add(routeListInfo);
            }
        }
        return result;
         }
   */

  //����7.�鿴����·�߱�
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC307
  //getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)
////////////////////////////////////////////////////////////////////////////

  /**
   * ���ɱ����������Ʊ��������ʽΪ�����ļ���
   * @param routeListID String ·�߱��ID
   * @param level_zh String    ·�߼���
   * @param exportFormat String �����ʽ
   * @throws QMException
   * @return String  ·�߱������+���+�Ĺ���·�߱���
   */
  public String exportRouteList(String routeListID, String level_zh,
                                String exportFormat) throws QMException {
    StringBuffer result = new StringBuffer();
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) pservice.
        refreshInfo(
        routeListID);
    appendReportHead(listInfo, result, level_zh);
    Map reportMap = null;
    String partName = JNDIUtil.getFeatureValue(new QMPartMasterInfo().
                                               getBsoName(), "DisplayName");
    String listName = JNDIUtil.getFeatureValue(new
                                               TechnicsRouteListMasterInfo().
                                               getBsoName(), "DisplayName");
    if (exportFormat.trim().equals(".csv")) {
      result.append(partName + four_comma);
      result.append(listName);
      result.append(newline);
      if (level_zh.trim().equals(RouteListLevelType.FIRSTROUTE.getDisplay())) {
        String order = "���";
        String number = JNDIUtil.getPropertyDescript(new
            QMPartMasterInfo().
            getBsoName(),
            "partNumber").getDisplayName();
        String name = JNDIUtil.getPropertyDescript(new QMPartMasterInfo().
            getBsoName(),
            "partName").getDisplayName();
        String version = "�汾";
        result.append(order + comma + number + comma + name + comma +
                      version +
                      comma);
        String manuRoute = RouteCategoryType.MANUFACTUREROUTE.
            getDisplay();
        String assemRoute = RouteCategoryType.ASSEMBLYROUTE.getDisplay();
        result.append(manuRoute + comma + assemRoute);
        result.append(newline);
        reportMap = getFirstLeveRouteListReport1(listInfo);
        int i = 0;
        Collection sortedVec = RouteHelper.sortedInfos(reportMap.keySet());
        for (Iterator iter = sortedVec.iterator(); iter.hasNext(); ) {
          i++;
          QMPartMasterIfc partMasterInfo = (QMPartMasterIfc) iter.
              next();
          result.append(i + comma);
          appendPartMsg(partMasterInfo, result);
          Collection nodes = (Collection) reportMap.get(
              partMasterInfo);
          if (VERBOSE) {
            System.out.println(TIME +
                               "һ��·�� exportRouteList nodes = " +
                               nodes);
          }
          appendNodesMsg(nodes, result, four_comma);
          result.append(newline);
        }
      }
      else if (level_zh.trim().equals(RouteListLevelType.SENCONDROUTE.
                                      getDisplay())) {
        String order = "���";
        String number = JNDIUtil.getPropertyDescript(new
            QMPartMasterInfo().
            getBsoName(),
            "partNumber").getDisplayName();
        String name = JNDIUtil.getPropertyDescript(new QMPartMasterInfo().
            getBsoName(),
            "partName").getDisplayName();
        String version = "�汾";
        result.append(order + comma + number + comma + name + comma +
                      version +
                      comma);
        String firstManuRoute = "һ������";
        String firstAssemRoute = "һ��װ��";
        String secondManuRoute = "��������";
        String secondAssemRoute = "����װ��";
        result.append(firstManuRoute + comma + firstAssemRoute + comma +
                      secondManuRoute + comma + secondAssemRoute);
        result.append(newline);
        reportMap = getSecondLeveRouteListReport(listInfo);
        int i = 0;
        Collection sortedVec = RouteHelper.sortedInfos(reportMap.keySet());
        for (Iterator iter = sortedVec.iterator(); iter.hasNext(); ) {
          i++;
          QMPartMasterIfc partMasterInfo = (QMPartMasterIfc) iter.
              next();
          result.append(i + comma);
          appendPartMsg(partMasterInfo, result);
          Object[] branchColl = (Object[]) reportMap.get(
              partMasterInfo);
          Collection firstNodes = (Collection) branchColl[0];
          if (VERBOSE) {
            System.out.println(TIME +
                               "һ������, һ��װ�� exportRouteList nodes = " +
                               firstNodes);
          }
          Collection secondNodes = (Collection) branchColl[1];
          if (VERBOSE) {
            System.out.println(TIME +
                               "��������, ����װ�� exportRouteList nodes = " +
                               secondNodes);
            //��firstNodes��secondNodes�ϳ�һ���¼��ϡ�������д��result�С�
          }
          appendNodesMsg_Second(firstNodes, secondNodes, result);
        }
      }
      else {
        throw new TechnicsRouteException("·�߼����д���");
      }
    }
    else {
      throw new TechnicsRouteException("��ʱֻ֧��.csv��ʽ��");
    }
    return result.toString();
  }

//��firstNodes��secondNodes�ϳ�һ���¼��ϡ�������д��result�С�
  private void appendNodesMsg_Second(Collection firstNodes,
                                     Collection secondNodes,
                                     StringBuffer result) {
    Object[] firstObj = firstNodes.toArray();
    Object[] secondObj = secondNodes.toArray();
    int i = firstObj.length;
    int j = secondObj.length;
    int x = 0;
    if (j > i) {
      x = j;
    }
    else {
      x = i;
    }
    Object[] inteObj = new Object[x];
    for (int k = 0; k < x; k++) {
      Object[] bigObj = new Object[2];
      Object obj1 = null;
      Object obj2 = null;
      if (k < i) {
        obj1 = firstObj[k];
      }
      if (k < j) {
        obj2 = secondObj[k];
      }
      bigObj[0] = obj1;
      bigObj[1] = obj2;
      inteObj[k] = bigObj;
    }
    PrintWriter out = null;
    try {
      out = new PrintWriter(new FileWriter("c:\111.txt", true));
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
    if (x == 0) {
      result.append(comma);
      result.append(comma);
      result.append(comma);
      result.append(comma);
      result.append(newline);
      return;
    }
    for (int v = 0; v < inteObj.length; v++) {
      out.println("enter inteObj ." + (v + 1) + "��");
      //result = new StringBuffer();
      
      if (v > 0) {
        result.append(four_comma);
      }
      Object[] bigObj = (Object[]) inteObj[v];
      //add by guoxl on 2008-07-04(���ɶ���·�߱��ر�����������ʹ���)
      if(bigObj[0] instanceof String){
    	  Object[] first1=new Object[1];
    	   first1[0] = bigObj[0].toString();
          appendOnlyOneBranch(first1, result);
    	  
      }else{
      // add by guoxl end (���ɶ���·�߱��ر�����������ʹ���)
      Object[] first = (Object[]) bigObj[0];
      appendOnlyOneBranch(first, result);
      }
      //add by guoxl on 2008-07-04(���ɶ���·�߱��ر�����������ʹ���)
      if(bigObj[1] instanceof String){
    	  Object[] second1=new Object[1];
    	  second1[0]=  bigObj[1].toString();
    	  appendOnlyOneBranch(second1, result);
      }else{
    	// add by guoxl end (���ɶ���·�߱��ر�����������ʹ���)  
      Object[] second = (Object[]) bigObj[1];
      appendOnlyOneBranch(second, result);
      }
      result.append(newline);
      out.println(result);
      out.println("exit inteObj ." + (v + 1) + "��");
    }
  }

  //��ӱ���ͷ��
  private void appendReportHead(TechnicsRouteListIfc listInfo,
                                StringBuffer result,
                                String level_zh) throws QMException {
    if (VERBOSE) {
      System.out.println("enter appendReportHead : listInfo.bsoid = " +
                         listInfo.getBsoID());
      //���·�߱���Ϣ��
    }
    String listNumberVal = listInfo.getRouteListNumber();
    String listNameVal = listInfo.getRouteListName();
    Object[] listObj = null;
    String key = null;
    //����·�ߵı�ͷ��ͬ��
    if (level_zh.trim().equals(RouteListLevelType.SENCONDROUTE.getDisplay())) {
      String departmentName = listInfo.getDepartmentName();
      listObj = new Object[] {
          departmentName, listNumberVal, listNameVal};
      key = "9";
    }
    else {
      listObj = new Object[] {
          listNumberVal, listNameVal};
      key = "6";
    }
    String list_total = QMMessage.getLocalizedMessage(RESOURCE, key,
        listObj);
    result.append(list_total);
    result.append(newline);
    //��Ӳ�Ʒ��Ϣ��
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMPartMasterIfc productMasterInfo = (QMPartMasterIfc) pservice.
        refreshInfo(
        listInfo.getProductMasterID());
    String partNumberVal = productMasterInfo.getPartNumber();
    String partNamerVal = productMasterInfo.getPartName();
    Object[] productObj = new Object[] {
        partNumberVal, partNamerVal};
    key = "7";
    String product_total = QMMessage.getLocalizedMessage(RESOURCE, key,
        productObj);
    result.append(product_total + four_comma);
    //��ӱ���������ڡ�
    String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    String month = String.valueOf(Calendar.getInstance().get(Calendar.MONTH) +
                                  1);
    String day = String.valueOf(Calendar.getInstance().get(Calendar.DATE));
    Object[] dayObj = new Object[] {
        year, month, day};
    key = "8";
    String date_total = QMMessage.getLocalizedMessage(RESOURCE, key, dayObj);
    result.append(date_total);
    result.append(newline);
    if (VERBOSE) {
      System.out.println("exit appendReportHead : result = " + result);
    }
  }

  //��������Ϣ��
  private void appendPartMsg(QMPartMasterIfc partMasterInfo,
                             StringBuffer result) throws QMException {
    result.append(partMasterInfo.getPartNumber() + comma);
    result.append(partMasterInfo.getPartName() + comma);
    result.append( ( (QMPartIfc) getLatestVesion(partMasterInfo)).
                  getVersionValue() + comma);
  }

  //��ӽڵ���Ϣ��
  private void appendNodesMsg(Collection nodes, StringBuffer result,
                              String comma_num) {
    int i = 0;
    for (Iterator iter1 = nodes.iterator(); iter1.hasNext(); ) {
      i++;
      if (i > 1) {
        result.append(newline);
        result.append(comma_num);
      }
      Object[] obj = (Object[]) iter1.next();
      appendOnlyOneBranch(obj, result);
    }
  }

  private void appendOnlyOneBranch(Object[] obj, StringBuffer result) {
	
    if (obj == null) {
      result.append(comma + comma);
      return;
    }
  //add by guoxl on 2008-07-04(���ɶ���·�߱��ر�����������ʹ���)
    if(obj[0] instanceof String){
    	String branch=obj[0].toString();
    	int index=branch.indexOf("@");
    	if(index!=-1){
    	String sub1=branch.substring(0,index);
    	String sub2=branch.substring(index+1);
    	result.append(sub1+comma+sub2);
    		
    	}else
    	result.append(obj[0]+comma);
    	
    }else{
   //add by guoxl end (���ɶ���·�߱��ر�����������ʹ���)
    Collection manuColl = (Collection) obj[0];
    int k = 0;
    for (Iterator iter2 = manuColl.iterator(); iter2.hasNext(); ) {
      k++;
      RouteNodeIfc manuNode = (RouteNodeIfc) iter2.next();
      if (manuColl.size() == k) {
        result.append(manuNode.getNodeDepartmentName() + comma);
      }
      else {
        result.append(manuNode.getNodeDepartmentName() + line);
      }
    }
    if (manuColl.size() == 0) {
      result.append(comma);
    }
    RouteNodeIfc assemNode = (RouteNodeIfc) obj[1];
    if (assemNode != null) {
      result.append(assemNode.getNodeDepartmentName());
    }
    }
    result.append(comma);
  }

  /**
   * ���ݹ���·�߱�ID���һ������·�߱���
   * @param listInfo TechnicsRouteListIfc ·�߱�ֵ���󣬸��ݹ���·�߱�ID���������乤��·��ֵ����
   * @throws QMException
   * @return CodeManageTable key:partMasterInfo���ֵ����;<br>
   * value����ŵ���Map:key:routeBranchInfo ·�߷�ֵ֧����;<br>
   * value: routeStr ·�ߴ�
   * @see TechnicsRouteListInfo
   */
  //obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj[1]=װ��·�߽ڵ�ֵ����
  public CodeManageTable getFirstLeveRouteListReport(TechnicsRouteListIfc
      listInfo) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
    if (!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.
        FIRSTROUTE.getDisplay())) {
      throw new TechnicsRouteException("·�߱�Ӧ����һ��·�ߡ�");
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
      //    CCBegin by leixiao 2009-01-05 ԭ�򣺽����������·��
     // QMPartMasterIfc partMasterInfo = linkInfo.getPartMasterInfo();
     //    CCEnd by leixiao 2009-01-05 ԭ�򣺽����������·��
      // ���ݹ���·��ID��ù���·�߷�֦��ض���
      //�������������ɱ��������Ż�
   //   Map routeMap = getRouteBranchs(linkInfo.getRouteID()); ԭ����
      Map routeMap = this.getDirectRouteBranchStrs(linkInfo.getRouteID());
     //    CCBegin by leixiao 2009-01-05 ԭ�򣺽����������·��
      firstMap.put(linkInfo, routeMap.values());
      //    CCEnd by leixiao 2009-01-05 ԭ�򣺽����������·��
    }
    if (VERBOSE) {
      System.out.println(
          "exit getFirstLeveRouteListReport , firstMap.values = " +
          firstMap.values());
    }
    return firstMap;
  }
  /**
   * ���ݹ���·�߱�ID���һ������·�߱���
   * @param listInfo TechnicsRouteListIfc ·��ֵ���󣬸��ݹ���·�߱�ID���������乤��·��ֵ����
   * @throws QMException
   * @return Map key:QMPartMasterIfc���(����Ϣ)ֵ����;value�����һ��map: key��TechnicsRouteBranchIfc ����·�߷�ֵ֦����,<br>
   * value��·�߽ڵ����飬obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�
   * obj[1]=װ��·�߽ڵ�ֵ����
   * @see TechnicsRouteListInfo
   */
  public Map getFirstLeveRouteListReport1(TechnicsRouteListIfc listInfo) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
    if (!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.
        FIRSTROUTE.getDisplay())) {
      throw new TechnicsRouteException("·�߱�Ӧ����һ��·�ߡ�");
    }
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond1 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                              listInfo.getBsoID());
    query.addCondition(cond1);
    query.addAND();
    QueryCondition cond2 = new QueryCondition("alterStatus",
                                              QueryCondition.NOT_EQUAL,
                                              PARTDELETE);
    query.addCondition(cond2);
    Collection coll = pservice.findValueInfo(query);
    Map firstMap = new HashMap();
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      QMPartMasterIfc partMasterInfo = linkInfo.getPartMasterInfo();
      Map routeMap = getRouteBranchs(linkInfo.getRouteID());
      firstMap.put(partMasterInfo, routeMap.values());
    }
    if (VERBOSE) {
      System.out.println(
          "exit getFirstLeveRouteListReport , firstMap.values = " +
          firstMap.values());
    }
    return firstMap;
  }

  /**
   * ���ݹ���·�߱�ID��ö�������·�߱���
   * @param listInfo TechnicsRouteListIfc ·�߱�ֵ����
   * @throws QMException
   * @return Map key:QMPartMasterIfc���(����Ϣ)ֵ����;<br>
   * value�������һ��obj[] :<bt>obj[0]:һ������·�߽ڵ������󼯺�,obj[1]:��������·�߽ڵ������󼯺�<br>
   * obj2[0]�� ����·�߽ڵ�ֵ���󼯺ϣ�obj2[1]��װ��·�߽ڵ�ֵ����
   * @see TechnicsRouteListInfo
   */
  // �����а����������ϣ�obj[0]=һ������·�߽ڵ������󼯺ϣ������������顣 obj1[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj1[1]=װ��·�߽ڵ�ֵ����
  // * obj[1] = ��������·�߽ڵ������󼯺ϣ������������顣obj2[0]=����·�߽ڵ�ֵ���󼯺ϣ�obj2[1]=װ��·�߽ڵ�ֵ���󡣡�
  public Map getSecondLeveRouteListReport(TechnicsRouteListIfc listInfo) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
    if (!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.
        SENCONDROUTE.getDisplay())) {
      throw new TechnicsRouteException("·�߱�Ӧ���Ƕ���·�ߡ�");
    }
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond1 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                              listInfo.getBsoID());
    query.addCondition(cond1);
    query.addAND();
    QueryCondition cond2 = new QueryCondition("alterStatus",
                                              QueryCondition.NOT_EQUAL,
                                              PARTDELETE);
    query.addCondition(cond2);
    Collection coll = pservice.findValueInfo(query);
    String departmentID = listInfo.getRouteListDepartment();
    if (departmentID == null || departmentID.trim().length() == 0) {
      throw new TechnicsRouteException("����·�߱����е�λ��");
    }
    Map secondMap = new HashMap();
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      QMPartMasterIfc partMasterInfo = linkInfo.getPartMasterInfo();

      //���partMasterInfo�Ķ���·�߶�Ӧ��һ��·�ߴ���
      //Collection nodes = getFirstNodesBySecondRoute(departmentID, listInfo.getProductMasterID());
      Collection nodes = getFirstNodesBySecondRoute(departmentID,
          linkInfo.getPartMasterID());
      if (nodes != null) { //lm add 20040526
        Object obj[] = new Object[2];
        obj[0] = nodes;
        //��ö���·�߰���·�ߴ���
        //Map routeMap = getRouteBranchs(linkInfo.getRouteID());//ԭ����
       //�������壩2006 08 03  zz ������� �����Ż�
    Map routeMap =  getDirectRouteBranchStrs(linkInfo.getRouteID());//end
        obj[1] = routeMap.values();
        secondMap.put(partMasterInfo, obj);
      }
///////////////////// lm add 20040826
      else { //û��һ��·��ʱ��ֻ��ʾ����·�߽ڵ�
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
    if (VERBOSE) {
      Collection values = secondMap.values();
      for (Iterator iter = values.iterator(); iter.hasNext(); ) {
        Object[] obj1 = (Object[]) iter.next();
        Collection firstColl = (Collection) obj1[0];
        for (Iterator iter1 = firstColl.iterator(); iter1.hasNext(); ) {
          Object[] obj2 = (Object[]) iter1.next();
          if (VERBOSE) {
            System.out.println(
                "getFirstLeveRouteListReport , firstColl -- manuBranch="
                + (Collection) obj2[0] + ", assemblyBranch " +
                (RouteNodeIfc) obj2[1]);
          }
        }
        Collection secondColl = (Collection) obj1[1];
        for (Iterator iter2 = secondColl.iterator(); iter2.hasNext(); ) {
          Object[] obj3 = (Object[]) iter2.next();
          if (VERBOSE) {
            System.out.println(
                "getFirstLeveRouteListReport , firstColl -- manuBranch="
                + (Collection) obj3[0] + ", assemblyBranch " +
                (RouteNodeIfc) obj3[1]);
          }
        }
      }
    }
    if (VERBOSE) {
      System.out.println("exit getSecondLeveRouteListReport...");
    }
    return secondMap;
  }

  //���partMasterInfo�Ķ���·�߶�Ӧ��һ��·�ߴ����˹�������б仯��
  private Collection getFirstNodesBySecondRoute(String departmentID,
                                                String productMasterID) throws
      QMException {
    if (VERBOSE) {
      System.out.println(
          "enter getFirstNodesBySecondRoute, departmentID = " +
          departmentID + ", productMasterID = " +
          productMasterID);
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    int i = query.appendBso(TECHNICSROUTE_BSONAME, false);
    int j = query.appendBso(ROUTENODE_BSONAME, false);
    int k = query.appendBso(ROUTELIST_BSONAME, false);
    QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              productMasterID);
    query.addCondition(0, cond1);
    query.addAND();
    QueryCondition cond2 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(0, cond2);
    query.addAND();
    QueryCondition cond3 = new QueryCondition("adoptStatus",
                                              QueryCondition.EQUAL,
                                              RouteAdoptedType.ADOPT.
                                              toString());
    query.addCondition(0, cond3);
    query.addAND();
    QueryCondition cond4 = new QueryCondition(LEFTID, "bsoID");
    query.addCondition(0, k, cond4);
    query.addAND();
    QueryCondition cond5 = new QueryCondition("routeListLevel",
                                              QueryCondition.EQUAL,
                                              RouteListLevelType.FIRSTROUTE.
                                              toString());
    query.addCondition(k, cond5);
    query.addAND();
    QueryCondition cond6 = new QueryCondition("routeID", "routeID");
    query.addCondition(0, j, cond6);
    query.addAND();
    QueryCondition cond7 = new QueryCondition("nodeDepartment",
                                              QueryCondition.EQUAL,
                                              departmentID);
    query.addCondition(j, cond7);
    query.addAND();
    QueryCondition cond8 = new QueryCondition("routeID", "bsoID");
    query.addCondition(0, i, cond8);
    //·���޸�ʱ�併�����С�
    //query.addOrderBy(i, "modifyTime", true);
    query.setDisticnt(true);
    if (VERBOSE) {
      System.out.println("getFirstNodesBySecondRoute's SQL = " +
                         query.getDebugSQL());
    }
    Collection coll = pservice.findValueInfo(query);
    //for(Iterator iter = coll.iterator(); iter.hasNext();)
    //���쳣�Ƿ�Ӧ���׳���
    if (coll.size() == 0) {
      if (VERBOSE) {
        System.out.println(productMasterID + "û�ж�Ӧ��һ��·�ߡ�");
      }
      return null; //lm modify 20040526
    }
    //·���޸�ʱ�併�����С�
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
    Collection result = null;
    Iterator iter = sortedVec.iterator();
    //ȡ����޸ĵ�·�߶�Ӧ�Ĺ�����
    if (iter.hasNext()) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      if (VERBOSE) {
        System.out.println(
            "routeService's getFirstNodesBySecondRoute linkInfo = " +
            linkInfo);
        //���һ��·�߰���·�ߴ���
      }
     // Map routeMap = getRouteBranchs(linkInfo.getRouteID());
    //  �������壩2006 08 03  zz ������� �����Ż� ���ɱ���  ֱ�Ӵ�branch��ȡ·�ߴ��ַ���,���ûָ�node����
    Map routeMap = this.getDirectRouteBranchStrs(linkInfo.getRouteID());
      result = routeMap.values();
    }
    if (VERBOSE) {
      System.out.println("exit getFirstNodesBySecondRoute  result = " +
                         result);
    }
    return result;
  }

////////////////////////////���ɱ��������///////////////////////////////

  /**
   * ������еĹ���·�߱�����°汾�������A,B�棬����B������С�汾��
   * @throws QMException
   * @return Collection ���obj[]���ϣ�<br>obj[0]������·�߱�ֵ����
   */
  public Collection getAllRouteLists() throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    ConfigService cservice = (ConfigService) EJBServiceHelper.getService(
        CONFIG_SERVICE);
    QMQuery query = new QMQuery(ROUTELISTMASTER_BSONAME);
    Collection coll = pservice.findValueInfo(query);
    Vector listVec = new Vector();
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      TechnicsRouteListMasterIfc listMasterInfo = (
          TechnicsRouteListMasterIfc)
          iter.next();
      //������еĹ���·�߱�����°汾�������A,B�棬����B������С�汾��
      Collection coll1 = cservice.filteredIterationsOf(listMasterInfo,
          new LatestConfigSpec());
      Iterator iter1 = coll1.iterator();
      if (iter1.hasNext()) {
        Object[] obj = (Object[]) iter1.next();
        //if(!(obj[0] instanceof MasteredIfc))
        listVec.addElement(obj[0]);
      }
    }
    return listVec;
  }


  /**
   * �����·�߱���ص��㲿����
   * @param routeListInfo TechnicsRouteListIfc ·�߱�ֵ����
   * @throws QMException
   * @return Collection �洢ListRoutePartLinkIfc������ֵ���󼯺ϡ�
   * @see TechnicsRouteListInfo
   */
  public Collection getRouteListLinkParts(TechnicsRouteListIfc routeListInfo) throws
      QMException {
    if (VERBOSE) {
      System.out.println(
          "enter the class:1731�У�����:getRouteListLinkParts()");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                             routeListInfo.getBsoID());
    query.addCondition(cond);
    query.addAND();
    //�п������δʹ��·�ߡ�
    QueryCondition cond1 = new QueryCondition("alterStatus",
                                              QueryCondition.NOT_EQUAL,
                                              PARTDELETE);
    query.addCondition(cond1);
    Collection coll = pservice.findValueInfo(query);
    ///////2004.4.28�޸�//////////
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      if (linkInfo.getRouteID() == null) {
        /**
                 ԭ�д���
                 String status = getStatus(linkInfo.getPartMasterID(),
                                  routeListInfo.getRouteListLevel());
                 linkInfo.setAdoptStatus(status);
         */
        /*
         �����޸� �޸��� skybird 2005.2.24
         */
        //begin
        String level = routeListInfo.getRouteListLevel();
        String level2 = new String("����·��");
        String status = null;
        if (level.equals(level2)) {
          String departmentBsoid = routeListInfo.
              getRouteListDepartment();
          status = getStatus2(linkInfo.getPartMasterID(),
                              level, departmentBsoid);
        }
        else {
          status = getStatus(linkInfo.getPartMasterID(),
                             routeListInfo.getRouteListLevel());
        }
        linkInfo.setAdoptStatus(status);
        //end
      }
    }

    return coll;
  }
   /**
    *  �����·�߱���ص��㲿����
    * �������ǲ鿴·�߱�汾��ʷ�ǵ��ã����Բ��ò鿴����·�߱��Ƿ�Ϊ�������㲿������·�ߣ����ع���ֵ����Ϳ���
    * @param routeListInfo TechnicsRouteListIfc ·�߱�ֵ����
    * @throws QMException
    * @return Collection ���ListRoutePartLinkIfc������ֵ���󼯺ϡ�
    * @see TechnicsRouteListInfo
    */
   //�������ģ�zz ������� �����Ż� 2006 07 12
   public Collection getRouteListLinkPartsforVersionCompare(TechnicsRouteListIfc routeListInfo) throws
       QMException {

     PersistService pservice = (PersistService) EJBServiceHelper.
         getPersistService();
     QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
     QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                              routeListInfo.getBsoID());
     query.addCondition(cond);
     query.addAND();
     //�п������δʹ��·�ߡ�
     QueryCondition cond1 = new QueryCondition("alterStatus",
                                               QueryCondition.NOT_EQUAL,
                                               PARTDELETE);
     query.addCondition(cond1);
     Collection coll = pservice.findValueInfo(query);
      return coll;
   }


  /**
   * �������·�߼��ڵ���Ϣ��
   * @param routeID String ·��ID
   * @throws QMException
   * @return Object[]<br>
   * obj[0]��TechnicsRouteIfc ����·��ֵ����obj[1] �� HashMap,�μ�getRouteBranchs(String routeID)
   */
  public Object[] getRouteAndBrach(String routeID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    Object[] objs = new Object[2];
    TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) pservice.refreshInfo(
        routeID);
    objs[0] = routeInfo;
    Map map = getRouteBranchs(routeID);
    objs[1] = map;
    return objs;
  }

  /**
   * ���ݹ���·��ID��ù���·�߷�֦��ض���
   * @param routeID String  ·��ID
   * @throws QMException
   * @return Map  <br>key��TechnicsRouteBranchIfc ����·�߷�ֵ֦����, value��·�߽ڵ����飬obj[0]=����·�߽ڵ�ֵ���󼯺ϣ�
   * obj[1]=װ��·�߽ڵ�ֵ����
   */
  public Map getRouteBranchs(String routeID) throws QMException {
       //  System.out.println(" ����getRouteBranchs���� �������� routeID " + routeID);
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);//��TechnicsRouteBranch
    QueryCondition cond = new QueryCondition("routeID",
                                             QueryCondition.EQUAL,
                                             routeID);
    query.addCondition(cond);
    Collection coll = pservice.findValueInfo(query);
   // System.out.println(" ����getRouteBranchs���� �������� coll " + coll.size());
    Map map = new HashMap();
    //HashMap map = new HashMap();
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      TechnicsRouteBranchIfc routeBranchInfo = (TechnicsRouteBranchIfc)
          iter.
          next();
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
   * @param routeID String ·��ID
   * @throws QMException
   * @return Map  key:TechnicsRouteBranchIfc ��ֵ֧����; value:·�ߴ�,routeBranchInfo.getRouteStr();
   */
  //�������壩zz �������
  public Map getDirectRouteBranchStrs(String routeID) throws QMException{
    PersistService pservice = (PersistService) EJBServiceHelper.
       getPersistService();
   QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);//��TechnicsRouteBranch
   QueryCondition cond = new QueryCondition("routeID",
                                            QueryCondition.EQUAL,
                                            routeID);
   query.addCondition(cond);
   Collection coll = pservice.findValueInfo(query);
   Map map = new HashMap();
   for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
     TechnicsRouteBranchIfc routeBranchInfo = (TechnicsRouteBranchIfc)
         iter.
         next();
     String routeStr = routeBranchInfo.getRouteStr();

     map.put(routeBranchInfo, routeStr);
   }
   return map;


  }
  /**
   * ���·�߷�֧
   * @param routeID String ·��ID
   * @throws QMException
   * @return Collection ���pservice.findValueInfo(query)�� �÷�֧�е�·�߽ڵ�ļ���
   */
  public Collection getgetRouteBranchs2(String routeID)throws QMException {

   PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
       QMQuery query = new QMQuery(ROUTENODE_BSONAME);
       query.appendBso(TECHNICSROUTEBRANCH_BSONAME,false);
       query.appendBso(BRANCHNODE_LINKBSONAME,false);
       QueryCondition cond1 = new QueryCondition(LEFTID,"bsoID");
       QueryCondition cond2 = new QueryCondition(RIGHTID,"bsoID");
       QueryCondition cond3 = new QueryCondition("routeID",
                                         QueryCondition.EQUAL,
                                         routeID);
       query.addCondition(1,cond3);
         query.addAND();
       query.addCondition(0,1,cond1);
       query.addAND();
       query.addCondition(0,2,cond2);
        Collection result=pservice.findValueInfo(query);
        return result;
}
  /**
   * ����·��ID��ø�·���а���֧���з����·�߽ڵ�
   * @param routeID String ·��ID
   * @return Map <br>key��TechnicsRouteBranchIfc��ֵ֧����value��getBranchRouteNodes(routeBranchInfo);
   * �÷�֧�е�·�߽ڵ�ļ���
   * @throws QMException
   */
  private Map getBranchNodes(String routeID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);
    QueryCondition cond = new QueryCondition("routeID",
                                             QueryCondition.EQUAL,
                                             routeID);
    query.addCondition(cond);
    Collection coll = pservice.findValueInfo(query);
    Map map = new HashMap();
    //HashMap map = new HashMap();
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      TechnicsRouteBranchIfc routeBranchInfo = (TechnicsRouteBranchIfc)
          iter.
          next();
      //���֦���������٣�δ�����������鷽ʽ��
      Collection branchNodes = getBranchRouteNodes(routeBranchInfo);
      map.put(routeBranchInfo, branchNodes);
    }
    return map;
  }

  /**
   * �ݹ���·�߷�֦ID��ù���·�߽ڵ㡣
   * @param routeBranchInfo TechnicsRouteBranchIfc ·��ֵ����
   * @throws QMException
   * @return Collection ���pservice.findValueInfo(query)������·�߽ڵ�ֵ����
   * @see TechnicsRouteBranchInfo
   */
  public Collection getBranchRouteNodes(TechnicsRouteBranchIfc
                                        routeBranchInfo) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
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
    query.appendBso(BRANCHNODE_LINKBSONAME,false);
    QueryCondition cond1 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                           routeBranchInfo.getBsoID());
      query.addCondition(1,cond1);
      query.addAND();
      QueryCondition cond2 = new QueryCondition(RIGHTID,"bsoID");
       query.addCondition(1,0,cond2);
        query.addOrderBy(1,"bsoID", false);
        Collection result=pservice.findValueInfo(query);
          //Collection result = pservice.findValueInfo(query);
    //(����һ)20060609 �����޸� end
   return result;
  }

  /**
   * ��·�߽ڵ����ͷ��ࡣװ��·�ߡ�����·�ߡ�
   * @param branchNodes Collection ·�߽ڵ㼯��
   * @throws QMException
   * @return Object[]  obj[0]�������һ��vector:��vector�����RouteNodeIfc·�߽ڵ�ֵ���� <br>
   * obj[1]��RouteNodeIfcװ��ڵ�ֵ����
   */
  public Object[] getNodeType(Collection branchNodes) throws QMException {
    Object[] obj = new Object[2];
    Vector vec = new Vector();
    RouteNodeIfc assemNodeInfo = null;
    Vector test = null;
    int i = 0;
    if (VERBOSE) {
      i = 0;
      test = new Vector();
    }
    for (Iterator iter = branchNodes.iterator(); iter.hasNext(); ) {
      RouteNodeIfc nodeInfo = (RouteNodeIfc) iter.next();
      if (VERBOSE) {
        System.out.println(TIME + "routeService getNodeType : type = " +
                           nodeInfo.getRouteType());
      }
      if (nodeInfo.getRouteType().equals(RouteCategoryType.
                                         MANUFACTUREROUTE.
                                         getDisplay())) {
        vec.add(nodeInfo);
      }
      else if (nodeInfo.getRouteType().equals(RouteCategoryType.
                                              ASSEMBLYROUTE.
                                              getDisplay())) {
        if (VERBOSE) {
          i++;
          test.add(nodeInfo);
        }
        assemNodeInfo = nodeInfo;
      }
      else {
        throw new TechnicsRouteException("�ڵ����Ͳ���ȷ" +
                                         nodeInfo.getRouteType());
      }
    }
    if (VERBOSE) {
      if (i > 1) {
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
   * @param routeID String ·��ID
   * @throws QMException
   * @return Object[] <br> obj[0]:RouteNodeInfo·�߽ڵ�ֵ���󼯺ϣ�
   * obj[1]:RouteNodeLinkInfo·�߽ڵ����ֵ���󼯺ϡ�
   */
  public Object[] getRouteNodeAndNodeLink(String routeID) throws QMException {
    Object[] obj = new Object[2];
    obj[0] = getRouteNodes(routeID);
    obj[1] = getNodelink(routeID);
    return obj;
  }

  /**
   * ���ݹ���·�߻����صĽڵ�(��·�߷�֧����)���������
   * @param routeID String
   * @return  Object[] <br> obj[0]:�����һ��Map��key:TechnicsRouteBranchIfc��ֵ֧����
   * value:�μ�getBranchRouteNodes(routeBranchInfo);�÷�֧�е�·�߽ڵ�ļ��ϣ���<br>
   *  obj[1]��RouteNodeLinkInfo·�߽ڵ����ֵ���󼯺ϡ�
   * @throws QMException
   */
  public Object[] getBranchNodeAndNodeLink(String routeID) throws QMException {
    Object[] obj = new Object[2];
    obj[0] = getBranchNodes(routeID);
    obj[1] = getNodelink(routeID);
    return obj;
  }

  /**
   * @roseuid 40309C6D03A8
   * @J2EE_METHOD  --  getRouteNodes
   * ���ݹ���·�߻�ö�Ӧ�Ľڵ㡣
   * �Ȼ�ù���·�߶�Ӧ��������ʼ�ڵ㡣
   * @return Collection ���·�߽ڵ�����ӵ�ֵ���󼯺ϣ��μ�pservice.findValueInfo(query);��
   */
  private Collection getRouteNodes(String routeID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(ROUTENODE_BSONAME);
    QueryCondition cond = new QueryCondition("routeID",
                                             QueryCondition.EQUAL,
                                             routeID);
    query.addCondition(cond);
    Collection coll = pservice.findValueInfo(query);
    return coll;
  }

  /**
   * @roseuid 40309CCD01E3
   * @J2EE_METHOD  --  getNodelink
   * ���ݹ���·�߻�ö�Ӧ�Ľڵ������
   */
  private Collection getNodelink(String routeID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(ROUTENODE_LINKBSONAME);
    QueryCondition cond = new QueryCondition("routeID",
                                             QueryCondition.EQUAL,
                                             routeID);
    query.addCondition(cond);
    Collection coll = pservice.findValueInfo(query);
    return coll;
  }


  /**
   * ���棬���¹���·�ߡ�
   * ϵͳ������½��Ĺ���·�ߣ�������ˢ��Ϊ�鿴·�߽���
   * �ڴ�����·�ߺ󣬵�����RoutePartLinkʱ��Ӧ��ListRoutePartLink�б�����Ӧ��·��
   * �Ƿ�ʹ��״̬���������ݵ�һ���ԡ�
   * @param linkInfo ListRoutePartLinkIfc ·�߱����������ֵ����
   * @param routeInfo TechnicsRouteIfc ·��ֵ����
   * @throws QMException
   * @return TechnicsRouteIfc ����·��ֵ����
   * @see ListRoutePartLinkInfo
   * @see TechnicsRouteInso
   */
  public TechnicsRouteIfc saveRoute(ListRoutePartLinkIfc linkInfo,
                                    TechnicsRouteIfc routeInfo) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    TechnicsRouteIfc routeInfoSaved = null;
    //�ж�·���Ǹ��»����½���
    if (PersistHelper.isPersistent(routeInfo)) {
      if (VERBOSE) {
        System.out.println(TIME +
                           "enter routeService's saveRoute if cause ,��ʼ���¹���·�ߡ�");
        //���¹���·�ߡ�
      }
      routeInfoSaved = (TechnicsRouteIfc) pservice.saveValueInfo(
          routeInfo);
    }
    else {
      if (VERBOSE) {
        System.out.println(TIME +
                           "enter routeService's saveRoute else cause ,��ʼ��������·�ߡ�");
        //���湤��·�ߡ�
      }
      //��ѡ��ĸ���·�߶������һ�ݣ�bsoID��ͬ
      routeInfoSaved = (TechnicsRouteIfc) pservice.saveValueInfo(
          routeInfo);
      String routeListID = linkInfo.getRouteListID();
      String partMasterID = linkInfo.getPartMasterID();
      //�޸� skybird 2005.1.29
      //begin
      String INITIALUSED = linkInfo.getInitialUsed();
      //end
      //����ListRoutePartLink��skybird
      if (linkInfo.getAlterStatus() == INHERIT) {
        //��������,����ԭ����·��״̬Ϊȡ����
        //  listPostProcess(linkInfo.getRouteListMasterID(), routeListID,
        //                  partMasterID);
        //begin �����listPostProcess()���������˸�����
        listPostProcess(linkInfo.getRouteListMasterID(), routeListID,
                        partMasterID, INITIALUSED);
        //end
      }
      TechnicsRouteListIfc routeListInfo = (TechnicsRouteListIfc)
          pservice.
          refreshInfo(routeListID);
      linkInfo.setRouteID(routeInfoSaved.getBsoID());
      //���ʱ�ĸ��Ʋ�������
      //linkInfo.setInitialUsed(routeListInfo.getVersionID());
      linkInfo.setRouteListIterationID(routeListInfo.getVersionValue());
      linkInfo.setAlterStatus(ROUTEALTER);
      linkInfo.setAdoptStatus(RouteAdoptedType.ADOPT.getDisplay());
      pservice.saveValueInfo(linkInfo);
    }
    if (VERBOSE) {
      System.out.println("exit TechnicsRouteServiecEJB:saveRoute() 2031");
    }
    return routeInfoSaved;
  }

  /**
   * ��þ���·�߰汾�Ĺ�����
   * @param routeListID String ·�߱�ID
   * @param partMasterID String �㲿��ס��ϢID
   * @param partNumber String ������
   * @throws QMException
   * @return ListRoutePartLinkIfc ·�߱����������ֵ����
   */
  public ListRoutePartLinkIfc getListRoutePartLink(String routeListID,
      String partMasterID, String partNumber) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                             routeListID);
    query.addCondition(cond);
    query.addAND();
    //�п������δʹ��·�ߡ�
    QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(cond1);
    query.addAND();
    //������ɾ��״̬�Ĺ�����
    QueryCondition cond2 = new QueryCondition("alterStatus",
                                              QueryCondition.NOT_EQUAL,
                                              PARTDELETE);
    query.addCondition(cond2);
    query.addAND();
    QueryCondition cond3;
    if (partNumber != null) {
      cond3 = new QueryCondition("parentPartNum",
                                 QueryCondition.EQUAL,
                                 partNumber);
    }
    else {
      cond3 = new QueryCondition("parentPartNum",
                                 QueryCondition.IS_NULL);
    }
    query.addCondition(cond3);
    Collection coll = pservice.findValueInfo(query);
    if (coll.size() > 1) {
      throw new TechnicsRouteException("partMasterID��listID��õĹ�����Ӧ�ó���һ����");
    }
    Iterator iter = coll.iterator();
    ListRoutePartLinkIfc linkInfo = null;
    if (iter.hasNext()) {
      linkInfo = (ListRoutePartLinkIfc) iter.next();
    }
    return linkInfo;
  }

  /*
     ��������,����ԭ����·��״̬Ϊȡ����
     ���޸� �޸��ߣ�skybird 2005.1.29
     �޸�˵�������������ϵͳִ�й����л����쳣��
     ��˼Ӹ�����arg4��������޸ĵ����˳����߼��ĸı䣬
     ��֪���Ƿ�Ϻ�����Ĺ涨��
   */
  private void listPostProcess(String routeListMasterID, String routeListID,
                               String partMasterID, String arg4) throws
      QMException {
      	//    CCBegin by leixiao 2008-10-09 ԭ�򣺽����������·��
	    //quxg add �����Զ���·������Ϊȡ��״̬060808
	    if (true) {
	      return;
	    }
//      CCEnd by leixiao 2008-10-09 ԭ�򣺽����������·��
    if (VERBOSE) {
      System.out.println(TIME + "enter routeService's listProcess " +
                         routeListMasterID + " " +
                         routeListID + " " +
                         partMasterID);
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);//ListRoutePartLink
    QueryCondition cond = new QueryCondition(LEFTID,
                                             QueryCondition.NOT_EQUAL,
                                             routeListID);
    query.addCondition(cond);
    query.addAND();
    QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(cond1);
    query.addAND();
    QueryCondition cond2 = new QueryCondition("routeListMasterID",
                                              QueryCondition.EQUAL,
                                              routeListMasterID);
    query.addCondition(cond2);
    query.addAND();
    QueryCondition cond3 = new QueryCondition("adoptStatus",
                                              QueryCondition.EQUAL,
                                              RouteAdoptedType.ADOPT.
                                              toString());
    query.addCondition(cond3);
    /**
     �����޸ģ�skybird ʱ�䣺2005.1.24
     */
    //begin
    query.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(cond4);
    //end
    /*
         �����޸ģ�skybird 2005.1.29
     */
    //begin
    query.addAND();
    QueryCondition cond5 = new QueryCondition("initialUsed",
                                              QueryCondition.EQUAL,
                                              arg4);
    query.addCondition(cond5);
    //end
    Collection coll = pservice.findValueInfo(query);
    if (VERBOSE) {
      System.out.println(TIME + "routeService's listProcess coll = " +
                         coll);
      //һ���������·�ߣ���ʹԭ���������û����·�ߣ�ҲҪ������Ϊȡ��������getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)Ҫ�����⡣
    }
//      CCBegin by leixiao 2008-10-09 ԭ�򣺽����������·��
    if (coll.size() > 1) {
//      throw new TechnicsRouteException(
//          "routeService's listPostProcess : ��õĹ�����Ӧ���ж���� " +
//          coll.size());
      throw new TechnicsRouteException(
              "��õĹ�����Ӧ���� " +
              coll.size()+"���������Ƿ��������ͬ�����.");
//    CCEnd by leixiao 2008-10-09 ԭ�򣺽����������·��
    }
    Iterator iter = coll.iterator();
    if (iter.hasNext()) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      if (VERBOSE) {
        System.out.println("���ù���ǰ " + linkInfo.getBsoID() + "��״̬Ϊ==" +
                           linkInfo.getAdoptStatus());
      }
      linkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
      pservice.saveValueInfo(linkInfo);
      if (VERBOSE) {
        System.out.println("���ù�����2222222222222 " + linkInfo.getBsoID() +
                           "��״̬Ϊ==" + linkInfo.getAdoptStatus());

      }
    }
  }

  /**
   * ���湤��·�߱༭ͼ��
   * ·�ߴ��������
   * ����·�ߴ��Ĺ���Ϊ·�ߵ�λ�ڵ㣬һ����λ������һ��·�ߴ��г��ֶ�Ρ�·�ߴ��а����ӹ���λ��װ�䵥λ������·�ߴ��Ĺ��ɱ���������й���
   * 1. װ�䵥λ��һ��·�ߴ���ֻ����һ������ֻ�������һ���ڵ㣻
   * 2. һ����λ�����һ��·�ߴ��г��ֶ�Σ�������ǲ�ͬ���͵Ľڵ�(�����װ��)�������������ڵ�λ�ó��֡�
   * ��     * ���һ��·�ߴ�������˶��װ��ڵ㣬����ʾ��Ӧ����Ϣ��
   * ��     * ���װ��ڵ㲻�����ڵ㣬����ʾ��Ӧ����Ϣ��
   * ��     * ���·�ߴ��д������ڵ�ͬ���ͽڵ㣬����ʾ��Ӧ����Ϣ��
   * ע�⣺����ع���
   * @param listLinkInfo ListRoutePartLinkIfc ·�����������ֵ����
   * @param routeRelaventObejts HashMap key: TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME<br>
   * value:RouteNodeInfo·�߽ڵ�ֵ�����RouteNodeLinkInfo·�߽ڵ����ֵ����
   * @throws QMException
   * @see ListRoutePartLinkInfo
   */
  // �ͻ����ڽ��б���ʱ��Ӧ�����ж�ListRoutePartLinkIfc��getAlterStatus()����=0��ȫ�����ó��½�״̬��=1����������
  public void saveRoute(ListRoutePartLinkIfc listLinkInfo,
                        HashMap routeRelaventObejts) throws QMException {
    try {
      if (VERBOSE) {
        System.out.println(
            "���¹���·�ߣ�enter the TechnicsRouteServiceEJB:saveRoute():2135");
      }
      SaveRouteHandler.doSave(listLinkInfo, routeRelaventObejts);
      if (VERBOSE) {
        System.out.println(
            "exit the TechnicsRouteServiceEJB:saveRoute():2138");
      }
    }
    catch (QMException exception) {
      this.setRollbackOnly();
      throw new TechnicsRouteException(exception);
    }
  }

//CCBegin by leixiao 2010-7-12 �򲹶�v4r3_p018_20100629 ��CR7
  /**
   *�����ʷ·�߱������·��ID
   *return true:ɾ����ʷ�汾������·��;false:��ɾ��
   *CR7
   */
 private boolean isDeleteHistoryRoute(ListRoutePartLinkIfc linkInfo) throws
      QMException{

		boolean flag = false;

		PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();
		QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
		QueryCondition cond = new QueryCondition("routeID",
				QueryCondition.EQUAL, linkInfo.getRouteID());
		query.addCondition(cond);

		Collection coll = pservice.findValueInfo(query);
		if (coll != null && coll.size() > 1) {

			flag = false;
		} else {

			flag = true;

		}
		return flag;

	}
//CCEnd by leixiao 2010-7-12 �򲹶�v4r3_p018_20100629 ��CR7
  /**
   * ɾ������·�ߡ�
   * @param linkInfo ListRoutePartLinkIfc ·�����������ֵ����
   * @throws QMException
   * @see ListRoutePartLinkInfo
   */
//  ���õ��˷����ĵط���
//   * 1.ɾ��������������˴˷���
//   * 2.�༭·�����ɾ��һ���㲿����·��Ҳ�����˴˷�����

  public void deleteRoute(ListRoutePartLinkIfc linkInfo) throws QMException {
    if (linkInfo.getRouteID() == null ||
        linkInfo.getRouteID().trim().length() == 0) {
      throw new TechnicsRouteException("�����û��·�ߣ�����ɾ��");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //CCBegin by leixiao 2010-7-12 �򲹶�v4r3_p018_20100629 ��CR7
    boolean flag=isDeleteHistoryRoute(linkInfo);//CR7
    /*
      ������ж����ã�������ʲô�û�û����ã�-----2005.1.31-skybird
      ���뵽ɾ��һ��·�ߵ�ʱ��ֻ�ܶԴ�汾������С�汾����ɾ��������
      ���������в�ͬ��״̬�������ã����Ի��д��жϡ�
     */
    /*
     * int =0 ��ʾ�Ǵ���һ�汾�̳������ģ�int=1��
     * �ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ�int=2��
     * �˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
     */
    
    if (linkInfo.getAlterStatus() == ROUTEALTER) {//1
    	if(flag){//Begin CR7
      //ɾ��·�߰�������
      deleteContainedObjects(linkInfo.getRouteID());
      //ɾ��·�߱���
      pservice.deleteBso(linkInfo.getRouteID());
      	}//End CR7
    }
  //CCEnd by leixiao 2010-7-12 �򲹶�v4r3_p018_20100629 ��CR7
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
	 * ��ɾ��·�߱�ʱɾ��·����Ϣ. �˷�����ɾ��·����Ϣ���ٱ����޸ĺ�Ĺ�������.
	 * 
	 * @param linkInfo   ��������
	 *           
	 * @throws QMException
	 * CR2
	 */

	private void deleteRouteForDeleteListen(ListRoutePartLinkIfc linkInfo)
			throws QMException
	{
		if (linkInfo.getRouteID() == null
				|| linkInfo.getRouteID().trim().length() == 0)
		{
			throw new TechnicsRouteException("�����û��·�ߣ�����ɾ��");
		}
		PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();

		if (linkInfo.getAlterStatus() == ROUTEALTER)
		{
			// ɾ��·�߰�������
			deleteContainedObjects(linkInfo.getRouteID());
			// ɾ��·�߱���
			pservice.deleteBso(linkInfo.getRouteID());
		}

	}

  /**
   * @roseuid 4030B10E02D9
   * @J2EE_METHOD  --  deleteNode
   * ɾ���ڵ�ID.��������ɳ־û�ɾ��������ԣ���
   */
  private void deleteNode(String routeID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //�ɳ־û�ɾ��node_node_link./////////////����ԡ�
    //�Լ�ɾ��
    QMQuery query2 = new QMQuery(ROUTENODE_LINKBSONAME);
    QueryCondition cond2 = new QueryCondition("routeID",
                                              QueryCondition.EQUAL,
                                              routeID);
    query2.addCondition(cond2);
    Collection coll2 = pservice.findValueInfo(query2);
    //ɾ�����нڵ������
    for (Iterator iter2 = coll2.iterator(); iter2.hasNext(); ) {
      pservice.deleteValueInfo( (BaseValueIfc) iter2.next());
    }

    QMQuery query = new QMQuery(ROUTENODE_BSONAME);
    QueryCondition cond = new QueryCondition("routeID",
                                             QueryCondition.EQUAL,
                                             routeID);
    query.addCondition(cond);
    Collection coll = pservice.findValueInfo(query);
    //ɾ�����нڵ㡣
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      pservice.deleteValueInfo( (BaseValueIfc) iter.next());
    }

  }


  /**
   * ��ɾ����ÿ�θ���֮ǰ����ɾ����
   * (�˷���ѭ������sql��ѯ��Ч���д�����)
   * @param routeID String ·��ID
   * @throws QMException
   */
  public void deleteBranchRelavent(String routeID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);
    QueryCondition cond = new QueryCondition("routeID",
                                             QueryCondition.EQUAL,
                                             routeID);
    query.addCondition(cond);
    Collection coll = pservice.findValueInfo(query);
    //ɾ��BranchNodeLink��Branch
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc) iter.next();
      QMQuery branchQuery = new QMQuery(BRANCHNODE_LINKBSONAME);
      QueryCondition cond2 = new QueryCondition(LEFTID,
                                                QueryCondition.EQUAL,
                                                branch.getBsoID());
      branchQuery.addCondition(cond2);
      Collection coll2 = pservice.findValueInfo(branchQuery); //�ҵ����й���
      for (Iterator iter2 = coll2.iterator(); iter2.hasNext(); ) {
        pservice.deleteValueInfo( (BaseValueIfc) iter2.next());
      }
      pservice.deleteValueInfo(branch);
    }

  }

  /**
   * ɾ��·��ʱ���������⣬�־û���ɾ����Ӧ�Ĺ���������ʱ����ɾ��������
   * @roseuid 4030B1D703B4
   * @J2EE_METHOD  --  deleteRouteListener
   * ɾ������·�߽ڵ㼰�������
   */
  protected void deleteContainedObjects(String routeID) throws QMException {
    //ɾ��·�߷�֦��
    deleteBranchRelavent(routeID);
    //ɾ��·�߽ڵ㡣
    deleteNode(routeID);

  }

  /**
   * ��ֻ���½ڵ�λ��ʱ�����ô˷�����������·�ߵĸ��¡�
   * @param coll Collection RouteNodeIfc·�߽ڵ�ֵ���󼯺�
   * @throws QMException
   */
  public void saveOnlyNodes(Collection coll) throws QMException {
    if (VERBOSE) {
      System.out.println(TIME + "enter routeService's saveOnlyNodes " +
                         coll);
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    if (coll.size() > 0 && ! (coll.iterator().next() instanceof RouteNodeIfc)) {
      throw new TechnicsRouteException("�������ͱ���ΪRouteNodeIfc��");
    }
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      BaseValueIfc nodeInfo = (BaseValueIfc) iter.next();
      //ֻ���Ǹ���·�߽ڵ㡣
      pservice.updateValueInfo(nodeInfo);
    }
  }

////////////·�߼���ڵ�Ĵ��������¡�ɾ��������////////////////

  /**
   * �������ָ����Ʒ�����¹���·�߱�
   * @param productMasterID String ���ڲ�Ʒ
   * @throws QMException
   * @return TechnicsRouteListInfo ����·�߱�
   */
  public TechnicsRouteListInfo getRouteListByProduct(String productMasterID) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getRouteListByProduct productMasterID = " +
                         productMasterID);
    }
    if (productMasterID == null || productMasterID.trim().length() == 0) {
      throw new TechnicsRouteException("�����������");
    }
    Object[] obj = new Object[3];
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query1 = new QMQuery("RouteListProductLink");
    QueryCondition condition1 = new QueryCondition(RIGHTID,
        QueryCondition.EQUAL, productMasterID);
    query1.addCondition(condition1);
    //����RouteListProductLink
    Collection coll = pservice.findValueInfo(query1);
    if (coll != null) {
      //�������С�
      Collection sortedVec = RouteHelper.sortedInfos(coll,
          "getCreateTime", true);
      if (VERBOSE) {
        System.out.println(TIME + "��ѯ��Ʒ�ṹ��Ӧ�Ĺ����� " + coll);
      }
      Iterator iter = sortedVec.iterator();
      //ֻȡ���´�����·�߱��Ӧ�Ĺ�����
      if (iter.hasNext()) {
        RouteListProductLinkInfo linkInfo = (RouteListProductLinkInfo)
            iter.
            next();
        String routeListMasterID = linkInfo.getRouteListMasterID();
        TechnicsRouteListInfo routeList = (TechnicsRouteListInfo)
            getLatestVesion(
            routeListMasterID);
        return routeList;
      }
      else {
        return null;
      }
    }
    else {
      return null;
    }

  }

  /**
   * ���������嵥���㲿��·��
   * ����û�ѡ���������·�ߣ�ϵͳӦ����û���ǰҪ���������嵥���㲿��������ж�Ӧ��
   * ����·�߱�(����·�߱��еġ����ڲ�Ʒ�����Թ��������㲿��)����ʹ�ô��㲿�������¹�
   * ��·�߱�Ϊ����·�ߵ�����Դ������û���乤��·�߱����г����㲿����ȡ����ʱ�������
   * ��·�߱��е�·��Ϊ����Դ�������ǰҪ���������嵥���㲿��û�ж�Ӧ�Ĺ���·�߱���
   * ���㲿��ȫ��ȡ����ʱ������µ�·�߱��е�·��Ϊ����Դ�������Ʒ�ṹ�е�ĳЩ�㲿��
   * û�б��ƹ�����·�ߣ����й�·�ߵ����������ֵ��
   * @param routeList TechnicsRouteListInfo ·�߱�ֵ����
   * @param partMasterID String ���MasterID
   * @param level_zh String ·�߼���
   * @throws QMException
   * @return Object[] obj[0]:TechnicsRouteListIfc, <br>obj[1],obj[2]
   * ��getRouteAndBrach(routeID), <br>obj[3]:ListRoutePartLinkIfc��
   * @see TechnicsRouteListInfo
   */
  public Object[] getMaterialBillRoutes(TechnicsRouteListInfo routeList,
                                        String partMasterID, String level_zh) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getMaterialBillRoutes routeList = " +
                         routeList
                         + "; partMasterID = " + partMasterID + "; level_zh = " +
                         level_zh);
    }
    Object[] obj = new Object[4];
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //���ҵ�ǰ·�߱���partMasterID�Ĺ���
    QMQuery query2 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition condition2 = new QueryCondition(RIGHTID,
        QueryCondition.EQUAL, partMasterID);
    query2.addCondition(condition2);
    query2.addAND();
    QueryCondition condition3 = new QueryCondition(LEFTID,
        QueryCondition.EQUAL,
        routeList.getBsoID());
    query2.addCondition(condition3);
    query2.addAND();
    int i = query2.appendBso(ROUTELIST_BSONAME, false);
    QueryCondition cond1 = new QueryCondition("routeListLevel",
                                              QueryCondition.EQUAL,
                                              level_zh);
    query2.addCondition(i, cond1);
    query2.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query2.addCondition(0, cond4);
    query2.addAND();
    QueryCondition cond5 = new QueryCondition("routeID",
                                              QueryCondition.NOT_NULL);
    query2.addCondition(0, cond5);
    query2.addAND();
    QueryCondition cond6 = new QueryCondition("adoptStatus",
                                              QueryCondition.EQUAL,
                                              RouteAdoptedType.ADOPT.
                                              toString());
    query2.addCondition(0, cond6);
    if (VERBOSE) {
      System.out.println(query2.getDebugSQL());
    }
    Collection col = pservice.findValueInfo(query2);
    if (col != null && col.size() > 0) {
      if (col.size() > 1) {
        throw new QMException("��Ӧ�ò�������¼��");
      }
      ListRoutePartLinkIfc listPartLinkInfo = (ListRoutePartLinkIfc) col.
          toArray()[0];
      String routeID = listPartLinkInfo.getRouteID();
      obj[0] = routeList;
      Object[] route = getRouteAndBrach(routeID);
      obj[1] = route[0];
      obj[2] = route[1];
      obj[3] = listPartLinkInfo;
    }
    else { //����û���乤��·�߱����г����㲿����ȡ����ʱ������µ�·�߱��е�·��Ϊ����Դ
      obj = getProductStructRoutes(partMasterID, level_zh);
    }
    if (VERBOSE) {
      System.out.println(TIME +
                         "exit... routeService's getMaterialBillRoutes " +
                         obj);
    }
    return obj;
  }


  /**
   * ����Ʒ�ṹ����·�߱���
   * @param partMasterID String
   * @param level_zh String ·�߼���
   * @throws QMException
   * @return Object[] obj[0]: TechnicsRouteListIfc,<br> obj[1],
   * obj[2]��getRouteAndBrach(routeID), <br>obj[3]:ListRoutePartLinkIfc��
   */
  public Object[] getProductStructRoutes(String partMasterID, String level_zh) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getProductStructRoutes partMasterID = " +
                         partMasterID +
                         ", level_zh = " + level_zh);
    }
    if (partMasterID == null || partMasterID.trim().length() == 0 ||
        level_zh == null ||
        level_zh.trim().length() == 0) {
      throw new TechnicsRouteException("�����������");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = getLinkQuery(partMasterID, level_zh);
    //����ListRoutePartLinkIfc
    Collection coll = pservice.findValueInfo(query);
    //�������С�
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
    if (VERBOSE) {
      System.out.println(TIME + "��ѯ��Ʒ�ṹ��Ӧ�Ĺ����� " + coll);
    }
    Object[] obj = new Object[4];
    Iterator iter = sortedVec.iterator();
    //ֻȡ���´�����·�߱��Ӧ�Ĺ�����
    if (iter.hasNext()) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      String routeListID = linkInfo.getRouteListID();
      String routeID = linkInfo.getRouteID();
      TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) pservice.
          refreshInfo(routeListID);
      obj[0] = listInfo;
      Object[] route = getRouteAndBrach(routeID);
      obj[1] = route[0];
      obj[2] = route[1];
      obj[3] = linkInfo;
    }
    if (VERBOSE) {
      System.out.println(TIME +
                         "exit... routeService's getProductStructRoutes " +
                         obj);
    }
    return obj;
  }

  /**
   * �������masterID��·�߼����������ǲ���״̬�Ĺ�����Ĳ�ѯ������
   * @param partMasterID String
   * @param level_zh String  ·�߼���
   * @throws QMException
   * @return QMQuery
   */
  private QMQuery getLinkQuery(String partMasterID, String level_zh) throws
      QMException {
    if (VERBOSE) {
      System.out.println("skybird-------TRS:QMQuery():������level_zh" +
                         level_zh);
    }
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    int i = query.appendBso(ROUTELIST_BSONAME, false);
    /*if(departmentID != null && departmentID.trim().length() !=0)
             {
        level = RouteListLevelType.SENCONDROUTE.toString();
        QueryCondition cond1 = new QueryCondition("routeListDepartment", QueryCondition.EQUAL, departmentID);
        query.addCondition(i, cond1);
        query.addAND();
             }
             else
        level = RouteListLevelType.FIRSTROUTE.toString();*/
    String level = RouteHelper.getValue(RouteListLevelType.
                                        getRouteListLevelTypeSet(),
                                        level_zh);
    QueryCondition cond1 = new QueryCondition("routeListLevel",
                                              QueryCondition.EQUAL, level);
    query.addCondition(i, cond1);
    query.addAND();
    QueryCondition cond2 = new QueryCondition(LEFTID, "bsoID");
    query.addCondition(0, i, cond2);
    query.addAND();
    QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(0, cond3);
    query.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(0, cond4);
    query.addAND();
    QueryCondition cond5 = new QueryCondition("routeID",
                                              QueryCondition.NOT_NULL);
    query.addCondition(0, cond5);
    query.addAND();
    QueryCondition cond6 = new QueryCondition("adoptStatus",
                                              QueryCondition.EQUAL,
                                              RouteAdoptedType.ADOPT.
                                              toString());
    query.addCondition(0, cond6);
    //�������С�
    //query.addOrderBy(i, "createTime", true);
    ///////////////////////////
    query.setDisticnt(true);
    return query;
  }

  /**
   * �����ӵķ���-------skybird 2005.1.24
   * �������������һ����������󲿷��ظ�����
   * @param partMasterID �㲿������ID
   * @param level_zh ·�߱�ļ���
   * @param depart ��������·������Ӧ��·�߼���
   */
  private QMQuery getLinkQuery2(String partMasterID, String level_zh,
                                String depart) throws
      QMException {
    if (VERBOSE) {
      System.out.println("skybird-------TRS:QMQuery2():������level_zh" +
                         level_zh);
      System.out.println("skybird-------TRS:QMQuery2():������depart" +
                         depart);
    }
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    int i = query.appendBso(ROUTELIST_BSONAME, false);
    String level = RouteHelper.getValue(RouteListLevelType.
                                        getRouteListLevelTypeSet(),
                                        level_zh);
    QueryCondition cond1 = new QueryCondition("routeListLevel",
                                              QueryCondition.EQUAL, level);
    query.addCondition(i, cond1);
    query.addAND();
    QueryCondition cond7 = new QueryCondition("routeListDepartment",
                                              QueryCondition.EQUAL, depart);
    query.addCondition(i, cond7);
    query.addAND();
    QueryCondition cond2 = new QueryCondition(LEFTID, "bsoID");
    query.addCondition(0, i, cond2);
    query.addAND();
    QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(0, cond3);
    query.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(0, cond4);
    query.addAND();
    QueryCondition cond5 = new QueryCondition("routeID",
                                              QueryCondition.NOT_NULL);
    query.addCondition(0, cond5);
    query.addAND();
    QueryCondition cond6 = new QueryCondition("adoptStatus",
                                              QueryCondition.EQUAL,
                                              RouteAdoptedType.ADOPT.
                                              toString());
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
   * @param level_zh String ·�߼���
   * @throws QMException
   * @return Collection  ���鼯�ϡ�obj[0]: TechnicsRouteListIfc, <br>
   * obj[1],obj[2]��getRouteAndBrach(routeID),<br> obj[3]:linkInfo��
   */
  public Collection getPartLevelRoutes(String partMasterID, String level_zh) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getPartLevelRoutes, partMasterID = " +
                         partMasterID +
                         ", level_zh = " + level_zh);
    }
    if (partMasterID == null || partMasterID.trim().length() == 0 ||
        level_zh == null ||
        level_zh.trim().length() == 0) {
      throw new TechnicsRouteException("�����������");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    int i = query.appendBso(ROUTELIST_BSONAME, false);
    int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
    /*String level = null;
             if(departmentID != null && departmentID.trim().length() != 0)
             {
        level = RouteListLevelType.SENCONDROUTE.toString();
        QueryCondition cond = new QueryCondition("routeListDepartment", QueryCondition.EQUAL, departmentID);
        query.addCondition(i, cond);
        query.addAND();
             }
             else
             {
        level = RouteListLevelType.FIRSTROUTE.toString();
             }*/
    String level = RouteHelper.getValue(RouteListLevelType.
                                        getRouteListLevelTypeSet(),
                                        level_zh);
    QueryCondition cond1 = new QueryCondition("routeListLevel",
                                              QueryCondition.EQUAL, level);
    query.addCondition(i, cond1);
    query.addAND();
    QueryCondition cond2 = new QueryCondition(LEFTID, "bsoID");
    query.addCondition(0, i, cond2);
    query.addAND();
    QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(0, cond3);
    query.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(0, cond4);

    query.addAND();
    QueryCondition cond5 = new QueryCondition("routeID",
                                              QueryCondition.NOT_NULL);
    query.addCondition(0, cond5);
    query.addAND();
    QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
    query.addCondition(0, j, cond6);
    query.addAND();
    //zz added start (������)
   QueryCondition condx = new QueryCondition("adoptStatus",
                                             QueryCondition.EQUAL,
                                             RouteAdoptedType.ADOPT.
                                             toString());
   query.addCondition(0,condx);
   //zz added end (������)

    //·���޸�ʱ�併�����С�
    //query.addOrderBy(j, "modifyTime", true);
    //SQL������������
    query.setDisticnt(true);
    //����ListRoutePartLinkIfc
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's getPartLevelRoutes SQL = " +
                         query.getDebugSQL());
    }
    Collection coll = pservice.findValueInfo(query);
    //·���޸�ʱ�併�����С�
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
    if (VERBOSE) {
      System.out.println(TIME + "��ѯ���Ϊ�� coll = " + sortedVec);
    }
    Collection result = new Vector();
    for (Iterator iter = sortedVec.iterator(); iter.hasNext(); ) {
      Object[] obj = new Object[4];
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      String routeListID = linkInfo.getRouteListID();
      String routeID = linkInfo.getRouteID();
      TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) pservice.
          refreshInfo(routeListID);
      obj[0] = listInfo;
      Object[] route = getRouteAndBrach(routeID);
      obj[1] = route[0];
      obj[2] = route[1];
      obj[3] = linkInfo;
      result.add(obj);
    }
    if (VERBOSE) {
      System.out.println(TIME +
                         "exit... routeService's getPartLevelRoutes " +
                         result);
    }
    return result;
  }

  /**
   *  �����㲿������·��
   * @param partMasterID String �㲿��bsoid
   * @throws QMException
   * @return Collection  ���鼯�ϡ�String[] {partID, routeID, routeState, linkID, state,
                 parentPartNum});
   */
  public Collection getPartRoutes(String partMasterID) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getPartLevelRoutes, partMasterID = " +
                         partMasterID);
    }
    if (partMasterID == null || partMasterID.trim().length() == 0) {
      throw new TechnicsRouteException("�����������");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
    QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(0, cond3);
    query.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(0, cond4);
    query.addAND();
    QueryCondition cond5 = new QueryCondition("routeID",
                                              QueryCondition.NOT_NULL);
    query.addCondition(0, cond5);
    query.addAND();
    QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
    query.addCondition(0, j, cond6);
    //SQL������������
    query.setDisticnt(true);
    //����ListRoutePartLinkIfc
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's getPartLevelRoutes SQL = " +
                         query.getDebugSQL());
    }
    Collection coll = pservice.findValueInfo(query);
    //·���޸�ʱ�併�����С�
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
    if (VERBOSE) {
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
    for (Iterator iter = sortedVec.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      QMPartMasterIfc part = (QMPartMasterIfc) pservice.refreshInfo(
          partMasterID);
      partID = part.getBsoID();
      routelistID = linkInfo.getLeftBsoID();
      TechnicsRouteListIfc techinicsRoute = (TechnicsRouteListIfc) pservice.
          refreshInfo(routelistID);
      state = techinicsRoute.getRouteListState();
      routeID = linkInfo.getRouteID();
      routeState = linkInfo.getAdoptStatus();
      linkID = linkInfo.getBsoID();
      parentPartNum = linkInfo.getParentPartNum();
      result.add(new String[] {partID, routeID, routeState, linkID, state,
                 parentPartNum});
    }
    if (VERBOSE) {
      System.out.println(TIME +
                         "exit... routeService's getPartLevelRoutes " +
                         result);
    }
    return result;
  }

//////17.���ƹ���·��
  //�汾 <v2.0>
  //�ĵ���ţ�PHOS-CAPP-UC317

  /**
   * ���ƹ���·��
   * �����ԡ���������·�߱��е�·�߽��и��ơ����"����"·�߱��������partMasterID���õ�·�ߡ�
   * ע�⣺�������ֻ����һ������ڲ�ͬ·�߱���·�ߵĸ��ơ��������������ĸ���ճ����
   * �������ID�͹���·�߼����ö�Ӧ�Ĺ���·�ߡ�
   * ִ���߸���һ������·�ߣ�ճ����û�й���·�ߵ��㲿���С�
   * ���ƹ���·��ʱ�����Դӵ�ǰ·�߱��е�һ���㲿���Ĺ���·�߸��ƣ�Ҳ���Դ�һ���㲿��������·�߱���ƵĹ���·�߸��ƣ�
   * ճ��ʱ������ճ������ǰѡ�е��㲿����Ҳ����ճ������·�߱���������·�ߵ��㲿��������㲿������·�ߣ�ʹ��"ճ����"����·��ʱ�����ܸ��Ƶ���Щ�㲿����
   * @param linkInfo1 ListRoutePartLinkIfc
   * @throws QMException
   * @return Collection ���鼯�ϡ�obj[0]: TechnicsRouteListIfc, <br>
   * obj[1],obj[2]��getRouteAndBrach(routeID)��
   * @see ListRoutePartLinkInfo
   */
  public Collection getAdoptRoutes(ListRoutePartLinkIfc linkInfo1) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getAdoptRoutes partMasterID = " +
                         linkInfo1.getPartMasterID());
      /*if(linkInfo1.getRouteID() != null && linkInfo1.getRouteID().trim().length() !=0)
          throw new TechnicsRouteException("4", null);*/
    }
    String depart = "";
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    TechnicsRouteListIfc listInfo1 = (TechnicsRouteListIfc) pservice.
        refreshInfo(linkInfo1.getRouteListID());
    String level = RouteHelper.getValue(RouteListLevelType.
                                        getRouteListLevelTypeSet(),
                                        listInfo1.getRouteListLevel());
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    /////////����·�߼�����й���///////
    int i = query.appendBso(ROUTELIST_BSONAME, false);
    QueryCondition cond0 = new QueryCondition(LEFTID, "bsoID");
    query.addCondition(0, i, cond0);
    query.addAND();
    QueryCondition cond1 = new QueryCondition("routeListLevel",
                                              QueryCondition.EQUAL, level);
    query.addCondition(i, cond1);
    query.addAND();
    if (listInfo1.getRouteListLevel().equals("����·��")) {
      depart = listInfo1.getRouteListDepartment();
      QueryCondition cond7 = new QueryCondition("routeListDepartment",
                                                QueryCondition.EQUAL, depart);
      query.addCondition(i, cond7);
      query.addAND();
    }
    QueryCondition cond2 = new QueryCondition("routeListMasterID",
                                              QueryCondition.NOT_EQUAL,
                                              linkInfo1.
                                              getRouteListMasterID());
    query.addCondition(cond2);
    query.addAND();
    if (VERBOSE) {
      System.out.println("linkInfo1.routeListMasterID()" +
                         linkInfo1.getRouteListMasterID());
    }
    QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              linkInfo1.getPartMasterID());
    query.addCondition(cond3);
    query.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(cond4);
    query.addAND();
    QueryCondition cond5 = new QueryCondition("routeID",
                                              QueryCondition.NOT_NULL);
    query.addCondition(cond5);
    query.addAND();
    QueryCondition cond6 = new QueryCondition("adoptStatus",
                                              QueryCondition.EQUAL,
                                              RouteAdoptedType.ADOPT.
                                              toString());
    query.addCondition(cond6);
    if (VERBOSE) {
      System.out.println("RouteAdoptedType.ADOPT.toString()" +
                         RouteAdoptedType.ADOPT.toString());
    }
    //�������С�
    //query.addOrderBy("createTime", true);
    //////////////////
    query.setDisticnt(true);
    //����ListRoutePartLinkIfc
    Collection coll = pservice.findValueInfo(query);
    if (VERBOSE) {
      System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&" +
                         query.getDebugSQL());
    }
    //�������С�
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
    Collection result = new Vector();
    for (Iterator iter = sortedVec.iterator(); iter.hasNext(); ) {
        //  CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��
        Object[] obj = new Object[3];
        ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
        //   String routeListID = linkInfo.getRouteListID();
        String routeID = linkInfo.getRouteID();
        //   TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) pservice.
        //       refreshInfo(routeListID);
        obj[0] = linkInfo;
        Object[] route = getRouteAndBrach(routeID);
        obj[1] = route[0];
        obj[2] = route[1];
        result.add(obj);
        //  CCEnd by leixiao 2008-7-31 ԭ�򣺽����������·��
      result.add(obj);
    }

    if (VERBOSE) {
      System.out.println(TIME + "exit... routeService's getAdoptRoutes " +
                         result);
    }
    return result;
  }

  /**
   * ���ƹ���·��
   * @param routeID String ·��ID: ���ø���routeID,�����Ӧ��·����Ϣ��
   * @param linkInfo ListRoutePartLinkIfc
   * @throws QMException
   * @return ListRoutePartLinkIfc ·�����������
   * @see ListRoutePartLinkInfo
   */
  public ListRoutePartLinkIfc copyRoute(String routeID,
                                        ListRoutePartLinkIfc linkInfo) throws
      QMException {
    if (linkInfo.getRouteID() != null &&
        linkInfo.getRouteID().trim().length() != 0) {
      throw new TechnicsRouteException("4", null);
    }
    //���ø���routeID,�����Ӧ��·����Ϣ��
    HashMap routeRelaventObejts = getRouteContainer(routeID, null);
    //����·��
    saveRoute(linkInfo, routeRelaventObejts);
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    ListRoutePartLinkIfc linkInfo1 = (ListRoutePartLinkIfc) pservice.
        refreshInfo(linkInfo.getBsoID());
    
    //CCBegin SS1
    String routeid = linkInfo1.getRouteID();
    TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) pservice.refreshInfo(routeid);
    if(routeInfo.getRouteDescription()==null||routeInfo.getRouteDescription().equals(""))
    {
    	QMPartMasterIfc partmaster = (QMPartMasterIfc)pservice.refreshInfo(linkInfo1.getRightBsoID());
    	routeInfo.setRouteDescription("("+getibaPartVersion(partmaster)+")");
    	pservice.saveValueInfo(routeInfo);
    }
    //CCBegin SS33
    else if(routeInfo.getRouteDescription().equals("BOM�ص�·��"))
    {
    	QMPartMasterIfc partmaster = (QMPartMasterIfc)pservice.refreshInfo(linkInfo1.getRightBsoID());
    	routeInfo.setRouteDescription("("+getibaPartVersion(partmaster)+")");
    	pservice.saveValueInfo(routeInfo);
    }
    //CCEnd SS33
    //CCEnd SS1
    
    return linkInfo1;
    //��һ�ַ�ʽ��������saveRoute���Լ�����
  }

  /**
   * ����·����ض���ע�Ᵽ֤˳��
   * @param routeID String
   * @param branchInfos Collection �б仯�ķ�ֵ֧����
   * @throws QMException
   * @return HashMap <br> key:TECHNICSROUTE_BSONAME��ROUTENODE_LINKBSONAME��BRANCHNODE_LINKBSONAME ��
   * TECHNICSROUTEBRANCH_BSONAME��ROUTENODE_BSONAME  <br>
   * value:TechnicsRouteIfc·��ֵ����RouteNodeLinkIfc�ڵ������RouteBranchNodeLinkIfc��֧��ڵ������
   * branchItemVec��֧���ϣ�nodeItemVec�ڵ㼯��
   */
  public HashMap getRouteContainer(String routeID, Collection branchInfos) throws
      QMException {
    HashMap relaventObejts = new HashMap();
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //1.���·�ߵ�HashMap��
    TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) pservice.refreshInfo(
        routeID);
    setupNewBaseValueIfc(routeInfo);
    RouteItem item = createRouteItem(routeInfo);
    relaventObejts.put(TECHNICSROUTE_BSONAME, item);
    //��ýڵ㼰�ڵ������
    Object[] obj1 = getRouteNodeAndNodeLink(routeID);
    //�ڵ㼯�ϡ�
    Collection nodes = (Collection) obj1[0];
    //�ڵ�������ϡ�
    Collection nodeLinks = (Collection) obj1[1];
    //2.��ӽڵ�������ϵ�HashMap
    //�����еĶ���ΪRouteItem.
    Collection nodeLinkItemVec = new Vector();
    for (Iterator iter1 = nodeLinks.iterator(); iter1.hasNext(); ) {
      RouteNodeLinkIfc nodeLinkInfo = (RouteNodeLinkIfc) iter1.next();
      //���ù���Դ�ڵ㡣
      RouteNodeIfc sourceNode = nodeLinkInfo.getSourceNode();
      RouteNodeIfc node1 = (RouteNodeIfc) getTheSameInfo(sourceNode,
          nodes);
      nodeLinkInfo.setSourceNode(node1);
      //���ù���Ŀ�Ľڵ㡣
      RouteNodeIfc destNode = nodeLinkInfo.getDestinationNode();
      RouteNodeIfc node2 = (RouteNodeIfc) getTheSameInfo(destNode, nodes);
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
    for (Iterator iter = branchNodeLinks.iterator(); iter.hasNext(); ) {
      RouteBranchNodeLinkIfc branchNodeLinkInfo = (RouteBranchNodeLinkIfc)
          iter.
          next();
      //��÷�֦item��
      TechnicsRouteBranchIfc branchInfo = branchNodeLinkInfo.
          getRouteBranchInfo();
      //����ڷ�֦��ڵ�����еķ�֧��branches�Ķ�Ӧֵ��
      TechnicsRouteBranchIfc branch1 = (TechnicsRouteBranchIfc)
          getTheSameInfo(
          branchInfo, branches);
      branchNodeLinkInfo.setRouteBranchInfo(branch1);
      RouteNodeIfc branchNode = branchNodeLinkInfo.getRouteNodeInfo();
      //����ڷ�֦��ڵ�����еĽڵ���nodes�Ķ�Ӧֵ��
      RouteNodeIfc node1 = (RouteNodeIfc) getTheSameInfo(branchNode,
          nodes);
      branchNodeLinkInfo.setRouteNodeInfo(node1);
      RouteItem branchNodeLinkItem = getBranchNodeLinkItem(
          branchNodeLinkInfo);
      branchNodeLinkItemVec.add(branchNodeLinkItem);
    }
    relaventObejts.put(BRANCHNODE_LINKBSONAME, branchNodeLinkItemVec);
    //4.��ӷ�֧���ϵ�HashMap
    Collection branchItemVec = new Vector();
    for (Iterator iter11 = branches.iterator(); iter11.hasNext(); ) {
      TechnicsRouteBranchIfc branchInfo = (TechnicsRouteBranchIfc) iter11.
          next();
      if (branchInfos != null) {
        if (branchInfo.getBsoID() == null) {
          throw new TechnicsRouteException("��ֵ֧��������Ϊ�յ�ʱ�����ԡ�");
        }
        //���ݴ����ֵ֧���󼯺������Ƿ���Ҫ·����Ϣ��
        for (Iterator branchIter = branchInfos.iterator();
             branchIter.hasNext(); ) {
          TechnicsRouteBranchIfc paraBranch = (TechnicsRouteBranchIfc)
              branchIter.next();
          if (branchInfo.getBsoID().trim().equals(paraBranch.getBsoID().
                                                  trim())) {
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
    for (Iterator iter2 = nodes.iterator(); iter2.hasNext(); ) {
      RouteNodeIfc nodeInfo = (RouteNodeIfc) iter2.next();
      RouteItem nodeItem = getNodeItem(nodeInfo, routeInfo);
      nodeItemVec.add(nodeItem);
    }
    relaventObejts.put(ROUTENODE_BSONAME, nodeItemVec);
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's    VERBOSE begin.....................");
      //TECHNICSROUTE_BSONAME��ROUTENODE_LINKBSONAME��TECHNICSROUTEBRANCH_BSONAME
      //ROUTENODE_BSONAME��BRANCHNODE_LINKBSONAME
      //1.·�ߡ�
      System.out.println(
          "1.route.........................................");
      RouteItem routeItem1 = (RouteItem) relaventObejts.get(
          TECHNICSROUTE_BSONAME);
      TechnicsRouteIfc route = (TechnicsRouteIfc) routeItem1.getObject();
      System.out.println(TIME + "route.... routeID = " + route.getBsoID());
      System.out.println(TIME + "route.... routehashCode = " +
                         route.hashCode());
      //2.�ڵ㡣
      System.out.println(
          "2.node.........................................");
      Collection nodes1 = (Collection) relaventObejts.get(
          ROUTENODE_BSONAME);
      for (Iterator iterator = nodes1.iterator(); iterator.hasNext(); ) {
        RouteItem item1 = (RouteItem) iterator.next();
        RouteNodeIfc node = (RouteNodeIfc) item1.getObject();
        System.out.println(TIME + "node..... nodeID = " + node.getBsoID() +
                           ", routeID = " +
                           node.getRouteInfo().getBsoID());
        System.out.println(TIME + "node..... hashCode = " +
                           node.getBsoID() +
                           ", routehashCode = " +
                           node.getRouteInfo().hashCode());
      }
      System.out.println(
          "3.nodeLink.........................................");
      //3.�ڵ������
      Collection nodelinks1 = (Collection) relaventObejts.get(
          ROUTENODE_LINKBSONAME);
      for (Iterator iterator = nodelinks1.iterator(); iterator.hasNext(); ) {
        RouteItem item1 = (RouteItem) iterator.next();
        RouteNodeLinkIfc nodeLink = (RouteNodeLinkIfc) item1.getObject();
        System.out.println(TIME + "nodeLink..... nodeLinkID = " +
                           nodeLink.getBsoID() +
                           ", routeID = " +
                           nodeLink.getRouteInfo().getBsoID() +
                           ", sourceNodeID = " +
                           nodeLink.getSourceNode().getBsoID() +
                           nodeLink.getDestinationNode().getBsoID());
        System.out.println(TIME + "nodeLink..... nodeLinkHashCode = " +
                           nodeLink.hashCode() +
                           ", routeHashCode = " +
                           nodeLink.getRouteInfo().hashCode() +
                           ", sourceNodeHashCode = " +
                           nodeLink.getSourceNode().hashCode() +
                           nodeLink.getDestinationNode().hashCode());
      }
      //4.��֧
      System.out.println(
          "4.branch.........................................");
      Collection branchs1 = (Collection) relaventObejts.get(
          TECHNICSROUTEBRANCH_BSONAME);
      for (Iterator iterator = branchs1.iterator(); iterator.hasNext(); ) {
        RouteItem item1 = (RouteItem) iterator.next();
        TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc) item1.
            getObject();
        System.out.println(TIME + "branch..... brachID = " +
                           branch.getBsoID() +
                           ", routeID = " +
                           branch.getRouteInfo().getBsoID());
        System.out.println(TIME + "branch..... brachHashCode = " +
                           branch.hashCode() + ", routeHashCode = " +
                           branch.getRouteInfo().hashCode());
      }
      //5.��֦������
      System.out.println(
          "5.branchNodeLink.........................................");
      Collection brancheNodes1 = (Collection) relaventObejts.get(
          BRANCHNODE_LINKBSONAME);
      for (Iterator iterator = brancheNodes1.iterator(); iterator.hasNext(); ) {
        RouteItem item1 = (RouteItem) iterator.next();
        RouteBranchNodeLinkIfc branchNode = (RouteBranchNodeLinkIfc)
            item1.
            getObject();
        System.out.println(TIME + "branchNode..... branchNodeID = " +
                           branchNode.getBsoID() +
                           ", branchID = " +
                           branchNode.getRouteBranchInfo().getBsoID()
                           + ", nodeID = " +
                           branchNode.getRouteNodeInfo().getBsoID());
        System.out.println(TIME +
                           "branchNode..... branchNodeHashCode = " +
                           branchNode.hashCode() +
                           ", branchHashCode = " +
                           branchNode.getRouteBranchInfo().hashCode()
                           + ", nodeHashCode = " +
                           branchNode.getRouteNodeInfo().hashCode());
      }
      System.out.println(TIME +
                         "routeService's getRouteContainer VERBOSE end.....................");
    }
    return relaventObejts;
  }

  private Collection getOnlyRouteBranch(String routeID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);
    QueryCondition cond = new QueryCondition("routeID",
                                             QueryCondition.EQUAL,
                                             routeID);
    query.addCondition(cond);
    Collection coll = pservice.findValueInfo(query);
    return coll;
  }

  //����ڷ�֦��ڵ�����еĽڵ���nodes�Ķ�Ӧֵ��
  private BaseValueIfc getTheSameInfo(BaseValueIfc node, Collection nodes) throws
      QMException {
    for (Iterator iter = nodes.iterator(); iter.hasNext(); ) {
      BaseValueIfc nodeInfo = (BaseValueIfc) iter.next();
      if (node.getBsoID().equals(nodeInfo.getBsoID())) {
        return nodeInfo;
      }
    }
    throw new TechnicsRouteException("�ڵ㷶Χ����");
    //   return null;
  }

  /**
   * @roseuid 4039932300E0
   * @J2EE_METHOD  --  getBranchRouteNodes
   * ���ݹ���·�߷�֦ID��ù���·�߽ڵ㡣
   * @return Collection ����·�߷�֧�ͽڵ������ֵ����
   */
  private Collection getBranchRouteLinks(String routeID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(BRANCHNODE_LINKBSONAME);
    int i = query.appendBso(TECHNICSROUTEBRANCH_BSONAME, false);
    QueryCondition cond = new QueryCondition(LEFTID, "bsoID");
    query.addCondition(0, i, cond);
    query.addAND();
    QueryCondition cond1 = new QueryCondition("routeID",
                                              QueryCondition.EQUAL,
                                              routeID);
    query.addCondition(i, cond1);
    //���������С�
    //query.addOrderBy(0, "bsoID", false);
    query.setDisticnt(true);
    Collection coll = pservice.findValueInfo(query);
    //���������С�
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getBsoID", false);
    return sortedVec;
  }

  private RouteItem getNodeItem(RouteNodeIfc nodeInfo,
                                TechnicsRouteIfc routeInfo) {
    setupNewBaseValueIfc(nodeInfo);
    nodeInfo.setRouteInfo(routeInfo);
    RouteItem item = createRouteItem(nodeInfo);
    return item;
  }

  private RouteItem getNodeLinkItem(RouteNodeLinkIfc nodeLinkInfo,
                                    TechnicsRouteIfc routeInfo) {
    setupNewBaseValueIfc(nodeLinkInfo);
    nodeLinkInfo.setRouteInfo(routeInfo);
    RouteItem item = createRouteItem(nodeLinkInfo);
    return item;
  }

  private RouteItem getBranchItem(TechnicsRouteBranchIfc branchInfo,
                                  TechnicsRouteIfc routeInfo) {
    setupNewBaseValueIfc(branchInfo);
    branchInfo.setRouteInfo(routeInfo);
    RouteItem item = createRouteItem(branchInfo);
    return item;
  }

  private RouteItem getBranchNodeLinkItem(RouteBranchNodeLinkIfc
                                          branchNodeInfo) {
    setupNewBaseValueIfc(branchNodeInfo);
    RouteItem item = createRouteItem(branchNodeInfo);
    return item;
  }

  private void setupNewBaseValueIfc(BaseValueIfc info) {
    info.setBsoID(null);
    info.setCreateTime(null);
    info.setModifyTime(null);
  }

  private RouteItem createRouteItem(BaseValueIfc info) {
    RouteItem item = new RouteItem();
    item.setObject(info);
    item.setState(RouteItem.CREATE);
    return item;
  }

/////////////////////���ƽ�����////////////////////////////

/////////////////////����Ŀ������ʼ////////////////////////////

  /**
   * ����·�߻�õ�λ�����
   * @param routeListID String ·�߱�ID  ����·�߱�ID��õ�λ
   * @throws QMException
   * @return HashMap key:��λֵ����  ; value:���masterInfo����
   */
  public HashMap getDepartmentAndPartByList(String routeListID) throws
      QMException {
    HashMap departmentAndPart = new HashMap();
    Collection departments = getDepartments(routeListID);
    for (Iterator iter = departments.iterator(); iter.hasNext(); ) {
      BaseValueIfc code = (BaseValueIfc) iter.next();
      Collection parts = getParts(code.getBsoID());
      departmentAndPart.put(code, parts);
    }
    return departmentAndPart;
  }


  /**
   * ͨ����λ����ID��ö�Ӧ��Ҫ�๤�յ������ͨ������ɻ�������Ϣ��
   * ��������·�ߵ���Ϣ���˷������ɹ��ղ��ֵ��á�
   * @param departmentID String ��λID
   * @throws QMException
   * @return Collection ���masterInfo����,�μ�linkInfo.getPartMasterID();��
   */
 // ע�⣺��λ�нṹ������ʱ���ӽڵ�ҲҪ�����������ݲ�����
  public Collection getParts(String departmentID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    int i = query.appendBso(ROUTENODE_BSONAME, false);
    QueryCondition cond2 = new QueryCondition("nodeDepartment",
                                              QueryCondition.EQUAL,
                                              departmentID);
    query.addCondition(i, cond2);
    query.addAND();
    QueryCondition cond3 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(0, cond3);
    query.addAND();
    QueryCondition cond5 = new QueryCondition("adoptStatus",
                                              QueryCondition.EQUAL,
                                              RouteAdoptedType.ADOPT.
                                              toString());
    query.addCondition(0, cond5);
    query.addAND();
    QueryCondition cond4 = new QueryCondition("routeID", "routeID");
    query.addCondition(0, i, cond4);
    Collection linkVec = pservice.findValueInfo(query);
    Collection partMasterIDVec = new Vector();
    for (Iterator iter = linkVec.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      String partMasterID = linkInfo.getPartMasterID();
      if (!partMasterIDVec.contains(partMasterID)) {
        partMasterIDVec.add(partMasterID);
      }
    }
    Collection partMasterInfoVec = new Vector();
    for (Iterator iter1 = partMasterIDVec.iterator(); iter1.hasNext(); ) {
      String bsoID = (String) iter1.next();
      BaseValueIfc info = pservice.refreshInfo(bsoID);
      partMasterInfoVec.add(info);
    }
    return partMasterInfoVec;
  }

  /**
   *  ���ݹ���·�߻��������ĵ�λ����������ķַ���
   * @param routeListID String
   * @throws QMException
   * @return Collection  CodingIfc��CodingClassificationIfc
   */
  public Collection getDepartments(String routeListID) throws QMException {
    Collection codeVec = getDepartmentIDByList(routeListID);
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    Collection codeInfoVec = new Vector();
    for (Iterator iter1 = codeVec.iterator(); iter1.hasNext(); ) {
      String bsoID = (String) iter1.next();
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
  private Collection getDepartmentIDByList(String routeListID) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(ROUTENODE_BSONAME);
    int i = query.appendBso(LIST_ROUTE_PART_LINKBSONAME, false);
    //ROUTENODE_BSONAME, TECHNICSROUTE_BSONAME,LIST_ROUTE_PART_LINKBSONAME
    QueryCondition cond2 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                              routeListID);
    query.addCondition(i, cond2);
    query.addAND();
    QueryCondition cond3 = new QueryCondition("alterStatus",
                                              QueryCondition.NOT_EQUAL,
                                              PARTDELETE);
    query.addCondition(i, cond3);
    query.addAND();
    QueryCondition cond4 = new QueryCondition("routeID", "routeID");
    query.addCondition(0, i, cond4);
    Collection nodes = pservice.findValueInfo(query);
    Collection codeVec = new Vector();
    for (Iterator iter = nodes.iterator(); iter.hasNext(); ) {
      RouteNodeIfc nodeInfo = (RouteNodeIfc) iter.next();
      String departmentID = nodeInfo.getNodeDepartment();
      if (!codeVec.contains(departmentID)) {
        codeVec.add(departmentID);
      }
    }
    return codeVec;
  }

/////////////////////����Ŀ��������////////////////////////////

  /**
   * ����routeMaster������еĴ�汾��Ӧ������С�汾��
   * @param listMasterInfo TechnicsRouteListMasterIfc
   * @throws QMException
   * @return Collection ��ŵ���Object[]��<br>
   * obj[0]:���еĴ�汾��Ӧ������С�汾��<br>
   * ��config���ˡ�
   * @see TechnicsRouteListMasterInfo
   */
  //�汾�ṩ�ķ�����allVersionsOf(MasteredIfc mastered).
  public Collection getAllVersionLists(TechnicsRouteListMasterIfc
                                       listMasterInfo) throws QMException {
    //PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
    ConfigService cservice = (ConfigService) EJBServiceHelper.getService(
        CONFIG_SERVICE);
    Vector listVec = new Vector();
    Collection coll1 = cservice.filteredIterationsOf(listMasterInfo,
        new MultipleLatestConfigSpec());
    for (Iterator iter1 = coll1.iterator(); iter1.hasNext(); ) {
      Object[] obj = (Object[]) iter1.next();
      listVec.addElement(obj[0]);
    }
    return listVec;
  }

  /**
   * �ṩ����ıȽϡ�
   * @param iteratedVec Collection ��ͬ�汾�Ĺ���·�߱�ֵ���󼯺ϡ�
   * @throws QMException
   * @return Map key �� partMasterID, value �� Collection:listRoutePartLinkIfc���ϡ�����˳�򣬰汾���������С�
   */
  public Map compareIterate(Collection iteratedVec) throws QMException {
    boolean isCommonSort = Boolean.valueOf(RemoteProperty.getProperty(
        "com.faw_qm.technics.route.isCommonCompare", "true")).
        booleanValue();
    if (VERBOSE) {
      System.out.println(
          "enter the class:TechnicsRouteServiceEJB,method:compareIterate()" +
          isCommonSort);
      System.out.println("�����Ĳ�����iterateVec" + iteratedVec);
    }
    if (isCommonSort) {
      return CompareHandler.commonCompareIterate(iteratedVec);
    }
    else {
      return CompareHandler.compareIterate(iteratedVec);
    }
  }

  ///////////////���ܷ�����
  /**
   * �ͻ��˽����жϣ��Ƿ��Ǵ���·�ߡ���true,��ʹ�Ǹ��£�ҲҪ��״̬����Ϊ������
   * @param RouteListID String
   * @param partMasterID String
   * @throws QMException
   * @return boolean
         public boolean isCreateRoute(String routeListID, String partMasterID) throws QMException
         {
        boolean flag = true;
   PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListID);
        query.addCondition(cond);
        query.addAND();
        //�п������δʹ��·�ߡ�
        QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(cond1);
        query.addAND();
   QueryCondition cond2 = new QueryCondition("rouetID", QueryCondition.NOT_NULL);
        query.addCondition(cond2);
        Collection coll = pservice.findValueInfo(query);
        if(coll.size()==1)
            flag = false;
        return flag;
         }
   */
  /**
   * �Ƿ���·�ߡ�
   * @param partMasterID String
   * @param routeListID String
   * @return boolean
   public boolean isHasRoute(String partMasterID, String routeListID)
   {
           getListRoutePartLink(partMasterID, routeListID);
           if(routeInfo==null)
      return false;
           else
      return true;
   }*/

  //routeListInfoΪ�µĴ�汾����ʱҪ���ƹ�������Ҫ����initialUsedΪ�µĴ�汾��
  /**
   * ����°汾
   * @param routeListInfo TechnicsRouteListIfc Ϊ�µĴ�汾����ʱҪ���ƹ�����
   * ��Ҫ����initialUsedΪ�µĴ�汾��
   * @throws QMException
   * @return TechnicsRouteListIfc ·�߱�ֵ����
   * @see TechnicsRouteListInfo
   */
  public TechnicsRouteListIfc newVersion(TechnicsRouteListIfc routeListInfo) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    TechnicsRouteListIfc updateRouteListInfo = (TechnicsRouteListIfc)
        pservice.
        saveValueInfo(routeListInfo);
    newVersionListener(updateRouteListInfo);
    return updateRouteListInfo;
  }

  protected void newVersionListener(TechnicsRouteListIfc routeListInfo) throws
      QMException {
    if (VERBOSE) {
      System.out.println(
          "enter routeService's newVersionListener : routeListInfo.bsoID = " +
          routeListInfo.getBsoID());
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    String decessorID = routeListInfo.getPredecessorID();
    if (decessorID == null || decessorID.trim().length() == 0) {
      return;
    }
    if (VERBOSE) {
      System.out.println("decessorID = " + decessorID);
      //�����һ��С�汾��
    }
    TechnicsRouteListIfc originalList = (TechnicsRouteListIfc) pservice.
        refreshInfo(decessorID);
    //�����µĹ�����
    Collection coll = getRouteListLinkParts(originalList);
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      if (VERBOSE) {
        System.out.println("ԭ������listLinkInfo.bsoID = " +
                           listLinkInfo.getBsoID());
      }
      ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkInfo) ( (
          ListRoutePartLinkInfo) listLinkInfo).duplicate();
      if (VERBOSE) {
        System.out.println("�¹�����listLinkInfo1.bsoID = " +
                           listLinkInfo1.getBsoID());
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
    if (VERBOSE) {
      System.out.println("exit routeService's newVersionListener");
    }
  }

//////////////��������ʼ//////////////////////////////////////
  /**
   * ������ɾ���������ݣ���Ҫ��֤���ݻָ���δɾ��ʱ��״̬����ʱ���ûع����־û������źţ��Ѿ����ûع���
   * @roseuid 403085BA0008
   * @J2EE_METHOD  --  deleteRouteListListener, ʵ���ڳ������ʱ�á�
   * ��Ϊ����·��û�а汾���˷�������ɾ���͸���������ҵ����󡣰���������·�ߡ�·�߽ڵ㡣Ҫ����master���ƺ͹���ԭ���Ƿ�һ�¡�
   */
  protected void deleteRouteListListener(TechnicsRouteListIfc routeListInfo) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //ɾ����routeListInfo�汾�д����Ĺ���·�ߡ�
    QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond3 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query1.addCondition(cond3);
    query1.addAND();
    //����½��Ĺ���·�ߡ�
    QueryCondition cond4 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                              routeListInfo.getBsoID());
    query1.addCondition(cond4);
    Collection coll1 = pservice.findValueInfo(query1);
    for (Iterator iter5 = coll1.iterator(); iter5.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkIfc) iter5.
          next();
      String routeID = listLinkInfo1.getRouteID();
      if (routeID != null && routeID.trim().length() != 0) {
    	//Begin CR2
        //deleteRoute(listLinkInfo1);         
    	  
    	  deleteRouteForDeleteListen(listLinkInfo1);
    	  
        //��֤���ݻָ���δɾ��ʱ��״̬��
        // comebackHandle(listLinkInfo1);   
    	//End CR2
      }
      /*else
                   {
       throw new TechnicsRouteException("deleteRouteListListener ·��ID����Ϊ�ա�");
                   }*/
    }
//Begin CR2
// �˹����־û���ɾ����                                                 
//    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);               
//    QueryCondition cond2 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
//                                              routeListInfo.getBsoID());
//    query.addCondition(cond2);
//    Collection coll = pservice.findValueInfo(query);
//    //ɾ��ListRoutePartLink���еĶ�Ӧ�Ĺ�����
//    for (Iterator iter3 = coll.iterator(); iter3.hasNext(); ) {
//      pservice.deleteValueInfo( (BaseValueIfc) iter3.next());
//    }
//End CR2
  }

  //�ָ����ݡ�
  private void comebackHandle(ListRoutePartLinkIfc listLinkInfo) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME + "routeService's comebackHandle " +
                         listLinkInfo.getBsoID());
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond = new QueryCondition("alterStatus",
                                             QueryCondition.EQUAL,
                                             ROUTEALTER);
    query.addCondition(cond);
    query.addAND();
    QueryCondition cond1 = new QueryCondition("routeListMasterID",
                                              QueryCondition.EQUAL,
                                              listLinkInfo.
                                              getRouteListMasterID());
    query.addCondition(cond1);
    query.addAND();
    QueryCondition cond2 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              listLinkInfo.getPartMasterID());
    query.addCondition(cond2);
    query.addAND();
    QueryCondition cond3 = new QueryCondition(LEFTID,
                                              QueryCondition.NOT_EQUAL,
                                              listLinkInfo.getRouteListID());
    query.addCondition(cond3);
    //���������С�
    query.addOrderBy("routeListIterationID", true);
    Collection coll = pservice.findValueInfo(query);
    Iterator iter = coll.iterator();
    //ֻ�����һ����
    if (iter.hasNext()) {
      ListRoutePartLinkIfc old_listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      if (VERBOSE) {
        System.out.println(TIME + "routeService's comebackHandle " +
                           old_listLinkInfo.getBsoID());
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
  protected void deletePartMasterListener(QMPartMasterIfc partMasterInfo) throws
      QMException {
    if (VERBOSE) {
      System.out.println(
          "enter the TechnicsRouteServiceEJB:deletePartmasterListener():ɾ���������");
    }
    try {
      String partMasterID = partMasterInfo.getBsoID();
      //��������ɾ�����ҵ����н���·�ߵ�listRoutePartlink�����࣬ɾ��·�ߡ�
      deletePartRoutes(partMasterID);
      //ɾ������ListRoutePartLink������
      deletePartLinks(partMasterID);
      //�����ɾ������ǲ�Ʒ��ɾ����ص�·�߱�ȡ�
      deleteProductRelaventObject(partMasterID);
      //�־û�ɾ����Ʒ��·�߱�Ĺ�����
    }
    catch (Exception e) {
      this.setRollbackOnly();
      throw new TechnicsRouteException(e);
    }
  }

  //ɾ��������·�ߡ�
  private void deletePartRoutes(String partMasterID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond = new QueryCondition("alterStatus",
                                             QueryCondition.EQUAL,
                                             ROUTEALTER);
    query.addCondition(cond);
    query.addAND();
    QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(cond1);
    Collection coll = pservice.findValueInfo(query);
    //ɾ��·�ߡ�
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      if (listLinkInfo.getRouteID() != null) {
        deleteRoute(listLinkInfo);
      }
    }
  }

  //ɾ������������йصĹ�����
  private void deletePartLinks(String partMasterID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(cond1);
    Collection coll = pservice.findValueInfo(query);
    //ɾ��������
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      //��ʹû��Ȩ�ޣ�·�߱����Ҳ�ᱻɾ����
      pservice.removeValueInfo(listLinkInfo);
    }
  }

  //ɾ�����Ʒ��ص�·�߱��·�ߵȡ�//����Ҫ�ָ�״̬����Ϊ���Ч�ʡ��ֶ�ɾ��·�߼�������
  private void deleteProductRelaventObject(String productMasterID) throws
      QMException {
    //ɾ�����Ʒ��ص�·�ߡ�
    deleteProductRoutes(productMasterID);
    //ɾ�����Ʒ��ص�listRoutePartLink��
    deleteProductLinks(productMasterID);
    //ɾ�����Ʒ��ص�·�߱�
    deleteProdcutRouteLists(productMasterID);
  }

  //ɾ�����Ʒ��ص�·�ߡ�
  private void deleteProductRoutes(String productMasterID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    int i = query.appendBso(ROUTELISTMASTER_BSONAME, false);
    QueryCondition cond1 = new QueryCondition("productMasterID",
                                              QueryCondition.EQUAL,
                                              productMasterID);
    query.addCondition(i, cond1);
    query.addAND();
    QueryCondition cond2 = new QueryCondition("routeListMasterID", "bsoID");
    query.addCondition(0, i, cond2);
    query.addAND();
    QueryCondition cond3 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(0, cond3);

    Collection coll = pservice.findValueInfo(query);
    //ɾ��·�ߡ�
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      if (listLinkInfo.getRouteID() != null) {
        deleteRoute(listLinkInfo);
      }
    }
  }

  /**
   * ɾ�����Ʒ��ص�listRoutePartLink��
   * ����˴���ɾ����������·�߱�ɾ�����������������ݴ����˷�Ч�ʡ�
   * @param productMasterID String
   */
  private void deleteProductLinks(String productMasterID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    int i = query.appendBso(ROUTELISTMASTER_BSONAME, false);
    QueryCondition cond1 = new QueryCondition("productMasterID",
                                              QueryCondition.EQUAL,
                                              productMasterID);
    query.addCondition(i, cond1);
    query.addAND();
    QueryCondition cond2 = new QueryCondition("routeListMasterID", "bsoID");
    query.addCondition(0, i, cond2);
    Collection coll = pservice.findValueInfo(query);
    //ɾ�����й�����
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      //��ʹû��Ȩ�ޣ�·�߱����Ҳ�ᱻɾ����
      pservice.removeValueInfo(listLinkInfo);
    }
  }

  //ɾ�����Ʒ��ص�·�߱�
  private void deleteProdcutRouteLists(String productMasterID) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(ROUTELIST_BSONAME);
    int i = query.appendBso(ROUTELISTMASTER_BSONAME, false);
    QueryCondition cond1 = new QueryCondition("productMasterID",
                                              QueryCondition.EQUAL,
                                              productMasterID);
    query.addCondition(i, cond1);
    query.addAND();
    QueryCondition cond2 = new QueryCondition("masterBsoID", "bsoID");
    query.addCondition(0, i, cond2);
    Collection coll = pservice.findValueInfo(query);
    //ɾ�����й�����
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) iter.next();
      //��ʹû��Ȩ�ޣ�·�߱�Ҳ�ᱻɾ����
      pservice.removeValueInfo(listInfo);
    }
  }

  //////////////////////ɾ���������������///////////////////////////
  /**
   * @param original ·�߱�ԭ��
   * @param work ·�߱���
   * @roseuid 4031A3570092
   * @J2EE_METHOD  --  copyRouteList
   * @return ���ƺ�Ĺ���·�߸�����
   * ���𿽱���������·�߱��ڲ�Ʒ����ʱ�����õ�����ʱ��ע�⣺ԭ�����Ӽ����ܲ������ڲ�Ʒ���Ӽ���
   * ���ڿͻ��˽��м�飬������ʾ��ʶ�������û����ġ�
   * ��������·�߱����ʱ·�߱�����·�߼���ڵ�ĸ��ơ�
   */
  //Begin CR1
  /*
   ԭ����:
  protected void copyRouteList(TechnicsRouteListIfc original,                       
                               TechnicsRouteListIfc work) throws QMException {
    if (VERBOSE) {
      System.out.println(TIME + "enter routeService's copyRouteList,ԭ�� " +
                         original + "����" + work);
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    Collection coll = getRouteListLinkParts(original);
    if (VERBOSE) {
      System.out.println(TIME + "copyRouteList... ԭ�������Ź���coll = " + coll);
    }
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      //ԭ�����㲿������
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      //��ù����Ŀ���
      ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkInfo) ( (
          ListRoutePartLinkInfo) listLinkInfo).duplicate();
      listLinkInfo1.setRouteListID(work.getBsoID());
      //��alterStatus���ó�INHERIT״̬��
      listLinkInfo1.setAlterStatus(INHERIT);
      //��adoptStatus���ó�CANCEL״̬��//���ģ�2004.5.12��adoptStatus���ó�ADOPT״̬��
      //////////////////�����������������״̬,���д˸���. 2004.9.11 ������
      //listLinkInfo1.setAdoptStatus(RouteAdoptedType.ADOPT.getDisplay());
      if (!listLinkInfo1.getAdoptStatus().equals(RouteAdoptedType.ADOPT.
                                                 getDisplay())) {
        listLinkInfo1.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
      }
      if (VERBOSE) {
        System.out.println(TIME + "����ǰ�� listLinkInfo.bsoID = " +
                           listLinkInfo.getBsoID() +
                           ", listLinkInfo1.bsoID = " +
                           listLinkInfo1.getBsoID());
      }
      pservice.saveValueInfo(listLinkInfo1);
      if (VERBOSE) {
        System.out.println(TIME + "����� listLinkInfo1.bsoID = " +
                           listLinkInfo1.getBsoID());
      }
    }
    if (VERBOSE) {
      System.out.println(TIME + "exit routeService's copyRouteList... ");
    }
  }*/
  protected void copyRouteList(TechnicsRouteListIfc original,
			TechnicsRouteListIfc work) throws QMException
	{

		PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();

		QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
		QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL,
				original.getBsoID());
		query.addCondition(cond);
		query.addAND();
		// �п������δʹ��·�ߡ�
		QueryCondition cond1 = new QueryCondition("alterStatus",
				QueryCondition.NOT_EQUAL, PARTDELETE);
		query.addCondition(cond1);

		Collection coll = pservice.findValueInfo(query, false);

		for (Iterator iter = coll.iterator(); iter.hasNext();)
		{
			ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
			if (linkInfo.getRouteID() == null)
			{

				String level = original.getRouteListLevel();
				String level2 = new String("����·��");
				String status = null;
				if (level.equals(level2))
				{
					String departmentBsoid = original.getRouteListDepartment();
					status = getStatus2(linkInfo.getPartMasterID(), level,
							departmentBsoid);
				}
				else
				{
					status = getStatus(linkInfo.getPartMasterID(), original
							.getRouteListLevel());
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
			if (!linkInfo.getAdoptStatus().equals(
					RouteAdoptedType.ADOPT.getDisplay()))
			{
				linkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
			}

			pservice.storeBso(linkInfo, false);

		}

	}
          //End CR1
  //����ListRoutePartLink��setRouteListIterationID
  protected void checkinListener(TechnicsRouteListIfc listInfo) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's checkinListener bsoid = " +
                         listInfo.getBsoID());
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond = new QueryCondition("alterStatus",
                                             QueryCondition.EQUAL,
                                             ROUTEALTER);
    query.addCondition(cond);
    query.addAND();
    QueryCondition cond1 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                              listInfo.getBsoID());
    query.addCondition(cond1);
    Collection coll = pservice.findValueInfo(query);
    String routeListIterationID = listInfo.getVersionValue();
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      listLinkInfo.setRouteListIterationID(routeListIterationID);
      if (VERBOSE) {
        System.out.println(TIME +
                           "routeService's checkinListener adoptStatus = " +
                           listLinkInfo.getAdoptStatus());
      }
      pservice.saveValueInfo(listLinkInfo);
    }
  }

//////////////�������������//////////////////////////////////////

  /**
   *  �������masterID��ù���·�߱�ֵ���󼯺ϡ�
   * @param partMasterID String
   * @throws QMException
   * @return Collection TechnicsRouteListInfo����·�߱�ֵ���󼯺�
   */
  public Collection getListsByPart(String partMasterID) throws QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getListsByPart, partMasterID = " +
                         partMasterID);
    }
    if (partMasterID == null || partMasterID.trim().length() == 0) {
      throw new TechnicsRouteException("partMasterID����Ϊ�ա�");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(ROUTELIST_BSONAME);
    int i = query.appendBso(LIST_ROUTE_PART_LINKBSONAME, false);
    //int j = query.appendBso(ROUTELIST_BSONAME, false);
    QueryCondition cond2 = new QueryCondition("bsoID", LEFTID);
    query.addCondition(0, i, cond2);
    query.addAND();
    QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(i, cond3);
    query.addAND();
    QueryCondition cond4 = new QueryCondition("iterationIfLatest",
                                              Boolean.TRUE);
    query.addCondition(0, cond4);
    //·�߱�Ĵ���ʱ�併�����С�
    query.addOrderBy(0, "createTime", true);
    //SQL������������
    //query.setDisticnt(true);
    //����ListRoutePartLinkIfc
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's getPartLevelRoutes SQL = " +
                         query.getDebugSQL());
    }
    Collection coll = pservice.findValueInfo(query);
    //·�߱�Ĵ���ʱ�併�����С�
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
    if (VERBOSE) {
      System.out.println(TIME + "��ѯ���Ϊ�� sortedVec = " + sortedVec);
      System.out.println(TIME + "exit... routeService's getListsByPart ");
    }
    return sortedVec;
  }

  /////////////2004.4.27����ӵķ���/////////////

  /**
   * �жϸ��������masterID������·�߱����Ƿ���·�ߡ�
   * @param partMasteInfos Collection QMPartMasterIfc����
   * @param level_zh String ·�߼���
   * @throws QMException
   * @return Map  key��partMasterInfo, value ��ȷ��һ���㲿����
   * ��״ֵ̬,�μ�getStatus(partMasterInfo.getBsoID(), level_zh)<br>
   * ���"������"���ޡ���
   */
  public Map getRouteByParts(Collection partMasteInfos, String level_zh) throws
      QMException {
    Map partMap = new HashMap();
    for (Iterator iter = partMasteInfos.iterator(); iter.hasNext(); ) {
      QMPartMasterIfc partMasterInfo = (QMPartMasterIfc) iter.next();
      partMap.put(partMasterInfo,
                  getStatus(partMasterInfo.getBsoID(), level_zh));
    }
    return partMap;
  }

  /**
   ���ݸ�����partMasterID,level_zh��·�߱��𣩣�����̬��ȷ��һ���㲿����
   ��״ֵ̬��
   @param partMasterID
   @param level_zh  ·�߱���
   */
  public String getStatus(String partMasterID, String level_zh) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = getLinkQuery(partMasterID, level_zh);
    Collection coll = pservice.findValueInfo(query);
    String status = null;
    if (coll.size() > 0) {
      status = RouteAdoptedType.EXIST.getDisplay();
      if (VERBOSE) {
        System.out.println("TRS:��̬��ȡһ���㲿����·��״̬" + status);
      }
    }
    else {
      status = RouteAdoptedType.NOTHING.getDisplay();
      if (VERBOSE) {
        System.out.println("TRS:��̬��ȡһ���㲿����·��״̬" + status);
      }
    }
    return status;
  }

  /**

   * �÷����ڹ���·�߱�ִ�и��²�����ʱ�������õķ�����
   * ����Ե��Ƕ�������·�߱�����Ρ��÷�����һ���㲿
   * ����·���ǿյ�ʱ����ã�����̬�������㲿����·��
   * ״̬��
   * @param partmasterID ·�߱���㲿��
   * @param level_zh ·�߱�ļ���
   * @param depart ����·�߱�ĵ�λ����ֵ
   */
  // * �����ӵ�һ������--------- skybird 2005.2.24
  public String getStatus2(String partMasterID, String level_zh,
                            String depart) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = getLinkQuery2(partMasterID, level_zh, depart);
    Collection coll = pservice.findValueInfo(query);
    String status = null;
    if (coll.size() > 0) {
      status = RouteAdoptedType.EXIST.getDisplay();
      if (VERBOSE) {
        System.out.println("TRS:��̬��ȡһ���㲿����·��״̬" + status);
      }
    }
    else {
      status = RouteAdoptedType.NOTHING.getDisplay();
      if (VERBOSE) {
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
   * @param branchInfos Collection ��ֵ֧���� ���ݷ�֧����·��
   * @throws QMException
   * @see ListRoutePartLinkInfo
   * @see TechnicsRouteInfo
   */
  public void createRouteByBranch(ListRoutePartLinkIfc linkInfo,
                                  TechnicsRouteIfc route,
                                  Collection branchInfos) throws QMException {
    if (VERBOSE) {
      System.out.println(
          "TechnicsRouteServiceEJB,�ɸ���·�ߵ��ã�û�������·�ߣ�alterstatus 0��");
    }
    //���ø���routeID,�����Ӧ��·����Ϣ��
    HashMap routeRelaventObejts = getRouteContainer2(linkInfo.getRouteID(),
        route, branchInfos);
    //����·�ߡ�
    saveRoute(linkInfo, routeRelaventObejts);
  }

  /**
   * ���·�֦��
   * @param branchs Collection RouteBranchNodeLinkIfcҪ���·�ֵ֦����
   * @throws QMException
   */
  public void updateBranchInfos(Collection branchs) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    for (Iterator iter = branchs.iterator(); iter.hasNext(); ) {
      //��ʱ�ķ�ֵ֧�����Ѿ��־û���
      pservice.updateValueInfo( (BaseValueIfc) iter.next());
    }
  }

  /**
   * ����·�����ͺͷ�֦ID��ù���·�߽ڵ㡣
   * @param routeType String ·������
   * @param routeBranchID String ·�߷�֧ID
   * @return Collection null
   */
  //���ɿ����ڼ����ʼ�ڵ��Ƿ��������ӣ����У�����RouteListHandler�ĺ�����������������ʼ�ڵ㡣����ʵ�������ѡ�񡣣�
  public Collection getRouteNodes(String routeType, String routeBranchID) {
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
   * @J2EE_METHOD  --  getRoutes
   * ���ݹ���·�߱�ID��ö�Ӧ�Ĺ���·�ߡ�
   * @return Collection ����·�߱��Ӧ�����й���·�ߣ�·�߷�֦��·�߽ڵ㡣
   public Collection getRoutes(String routeListID)
   {
           return null;
   }*/

  /**
   * ���ݲ�ƷID�����ID��ù���·��ֵ����
   * @param productMasterID String ��ƷID
   * @param partMasterID String���ID
   * @return TechnicsRouteIfc  null
   */
  public TechnicsRouteIfc getRoute(String productMasterID,
                                   String partMasterID) {
    return null;
  }

  /**
   * ���ݹ���·�߱�ID������й���·�߼���ڵ�ֵ����
   * @param routeListID String  ·�߱�ID
   * @param level_zh String ·�߱���
   * @return HashMap null
   */
  public java.util.HashMap getRouteListContent(String routeListID,
                                               String level_zh) {
    return null;
  }


  /**
   * ���ݹ���·�߱�·�߼����ù���·�ߡ�
   * �������Ϊ�գ����ּ���
   * @param routeListID String ·�߱�ID
   * @param level_zh String ·�߼���
   * @return Collection null
   */
  public Collection getListLevelRoutes(String routeListID, String level_zh) {
    return null;
  }


  /**
   * ���ѡ������
   * @param depart String  ��λ
   * @param master QMPartMasterIfc
   * @throws QMException
   * @return boolean  ���������true,���򷵻�false
   * @see QMPartMasterInfo
   */

  public boolean getOptionPart(String depart, QMPartMasterIfc master) throws
      QMException {
    Collection coll = null;

    try {
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      CodingManageService cservice = (CodingManageService)
          EJBServiceHelper.
          getService("CodingManageService");
      CodingClassificationIfc coding = (CodingClassificationIfc) pservice.
          refreshInfo(depart);
      //Collection col = cservice.getOnlyCoding(coding);
      CodingClassificationIfc code;
      QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
      int n = query.appendBso(ROUTELIST_BSONAME, false);
      int m = query.appendBso(ROUTENODE_BSONAME, false);
      QueryCondition cond = new QueryCondition(RIGHTID,
                                               QueryCondition.EQUAL,
                                               master.getBsoID());
      query.addCondition(cond);
      query.addAND();
      QueryCondition condition1 = new QueryCondition(LEFTID, "bsoID");
      query.addCondition(0, n, condition1);
      query.addAND();
      QueryCondition condition2 = new QueryCondition("routeListLevel",
          QueryCondition.EQUAL,
          FIRSTROUTE);
      query.addCondition(n, condition2);
      query.addAND();
      QueryCondition condition3 = new QueryCondition("routeID", "routeID");
      query.addCondition(0, m, condition3);
      /**
             if (col != null && col.size() > 0) {
        Object[] codings = col.toArray();
        if (codings.length == 1) {
          query.addAND();
          code = (CodingIfc) codings[0];
               QueryCondition condition4 = new QueryCondition("nodeDepartment",
              QueryCondition.EQUAL, code.getBsoID());
          query.addCondition(m, condition4);
        }
        else {
          query.addAND();
          query.addLeftParentheses();
          code = (CodingIfc) codings[0];
               QueryCondition condition4 = new QueryCondition("nodeDepartment",
              QueryCondition.EQUAL, code.getBsoID());
          query.addCondition(m, condition4);
          for (int i = 1; i < codings.length; i++) {
            query.addOR();
            code = (CodingIfc) codings[i];
               QueryCondition condition5 = new QueryCondition("nodeDepartment",
                QueryCondition.EQUAL,
                code.getBsoID());
            query.addCondition(m, condition5);
          }
          query.addRightParentheses();
        }
             }
       **/
      //add begin
      query.addAND();
      code = (CodingClassificationIfc) coding;
      QueryCondition condition4 = new QueryCondition("nodeDepartment",
          QueryCondition.EQUAL, code.getBsoID());
      query.addCondition(m, condition4);
      //i added
      coll = pservice.findValueInfo(query);
      if (VERBOSE) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%"
                           + query.getDebugSQL());
      }
      if (coll != null && coll.size() > 0) {
        return true;
      }
      else {
        return false;
      }
    }
    catch (QMException ex) {
      ex.printStackTrace();
      throw ex;
    }
  }
   // zz (�����)20061110 �������,Ϊ����·�� "���"��ť ��Ӹ�����ѡ��λ���˹���

   /**
    * Ϊ����·�� "���"��ť ��Ӹ�����ѡ��λ���˹���
    * @param depart String ��λ
    * @param masters Vector
    * @throws QMException
    * @return Collection ListRoutePartLinkInfo��RightBsoID <br>
    * ���Ͼ�·�ߵ�λ���˺���������
    */
   public Collection getOptionPart(String depart, Vector masters) throws
      QMException {
    if(masters!=null&&masters.size()>=1){
    String[] array = new String[masters.size()];
    Vector viaDepart = new   Vector();
    for(int i =0 ;i <masters.size();i++){

    QMPartMasterIfc parti = (QMPartMasterIfc)masters.elementAt(i);
     viaDepart.add(parti.getBsoID());

    }

     Object[] obj  = (Object[])viaDepart.toArray();
     for(int i=0;i<obj.length;i++)
     {
        array[i]= (String)obj[i];
     }
    Collection coll = null;
   try {
     PersistService pservice = (PersistService) EJBServiceHelper.
         getPersistService();
     CodingManageService cservice = (CodingManageService)
         EJBServiceHelper.
         getService("CodingManageService");
     CodingClassificationIfc coding = (CodingClassificationIfc) pservice.
         refreshInfo(depart);
      CodingClassificationIfc code;
     QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
     int n = query.appendBso(ROUTELIST_BSONAME, false);
     int m = query.appendBso(ROUTENODE_BSONAME, false);

     QueryCondition cond = new QueryCondition(RIGHTID,
                                             QueryCondition.IN,
                                             array);

     query.addCondition(cond);
     query.addAND();
     QueryCondition condition1 = new QueryCondition(LEFTID, "bsoID");
     query.addCondition(0, n, condition1);
     query.addAND();
     QueryCondition condition2 = new QueryCondition("routeListLevel",
         QueryCondition.EQUAL,
         FIRSTROUTE);
     query.addCondition(n, condition2);
     query.addAND();
     QueryCondition condition3 = new QueryCondition("routeID", "routeID");
     query.addCondition(0, m, condition3);

     query.addAND();
     code = (CodingClassificationIfc) coding;
     QueryCondition condition4 = new QueryCondition("nodeDepartment",
         QueryCondition.EQUAL, code.getBsoID());
     query.addCondition(m, condition4);

     coll = pservice.findValueInfo(query);

     if (coll != null && coll.size() > 0) {
     ;
        Vector vector = new Vector();
       for(   Iterator iter = coll.iterator();iter.hasNext();){
       ListRoutePartLinkInfo behindPart = (ListRoutePartLinkInfo)iter.next();
       //��·�ߵ�λ���˺�����
       String filterPart =(String) behindPart.getRightBsoID();

       vector.add(pservice.refreshInfo(filterPart));

       }

       return vector;
     }
     else {
       return null;
     }
   }
   catch (QMException ex) {
     ex.printStackTrace();
     throw ex;
   }

    }
   else return null;

  }

  private Collection filteredIterationsOf(Collection collection,
                                          PartConfigSpecIfc configSpecIfc) throws
      QMException {
    StandardPartService partService = (StandardPartService) EJBServiceHelper.
        getService(PART_SERVICE);
    return partService.filteredIterationsOf(collection, configSpecIfc);
  }

  /**
   * �õ�ǰ�û������ù淶�����㲿��
   * @param master QMPartMasterIfc
   * @throws QMException
   * @return QMPartIfc ���˺������ֵ����
   * @see QMPartMasterInfo
   */

  public QMPartIfc filteredIterationsOfByDefault(QMPartMasterIfc master) throws
      QMException {
    StandardPartService partService = (StandardPartService) EJBServiceHelper.
        getService(PART_SERVICE);
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
  
  //CCBegin by leixiao 2008-11-03 ԭ�򣺽������
  /**
   * �õ�ǰ�û������ù淶�����㲿��,���˵�������ͼ
   * @param master QMPartMasterIfc
   * @throws QMException
   * @return QMPartIfc
   */
  public QMPartIfc filteredIterationsOfByDefaultConfig(QMPartMasterIfc master) throws
  QMException {
StandardPartService partService = (StandardPartService) EJBServiceHelper.
    getService(PART_SERVICE);
PartConfigSpecIfc configSpecIfc = partService.findPartConfigSpecIfc();
Vector vec = new Vector();
vec.add(master);
Collection col;
col = (Collection) partService.filteredIterationsOf(vec, configSpecIfc);
Iterator i=col.iterator();
while (i.hasNext()){
	QMPartIfc part=(QMPartIfc)i.next();
	System.out.println("part="+part+"part.getViewName()="+part.getViewName());
	if(part.getViewName().equals("������ͼ"))
		return part;

}
return null;
}
//CCEnd by leixiao 2008-11-03 ԭ�򣺽������

  /**
   *  ͨ��part�ҳ����з������ƹ淶����part������getOptionPart�������˽����
   * @param partMaster QMPartMasterIfc
   * @param configSpecIfc PartConfigSpecIfc
   * @param depart String ��λ
   * @throws QMException
   * @return Vector QMPartIfc[] ���鼯��:<br>
   * partInfos[i]�����˺���������
   * @see QMPartMasterInfo
   */
  public Vector getAllSubPart(QMPartMasterIfc partMaster,
                              PartConfigSpecIfc configSpecIfc,
                              String depart) throws QMException {
    Vector vec = new Vector();
    try {
      EnterprisePartService enterprisePartService = (
          EnterprisePartService)
          EJBServiceHelper.getService(
          "EnterprisePartService");
      // Object obj = enterprisePartService.
      //  getAllSubPartsByConfigSpec(partMaster,
      //                     configSpecIfc);

      Object Object = enterprisePartService.
          getAllSubPartsByConfigSpec(partMaster,
                                     configSpecIfc);
      if (VERBOSE) {
        System.out.println("=====getAllSubPart" + Object);
      }
      if (VERBOSE) {
        System.out.println("1111111=====getAllSubPart" + Object.getClass());
      }
      QMPartIfc[] partInfos = (QMPartIfc[]) Object;

      if (getOptionPart(depart, partMaster)) {
        QMPartIfc part = filteredIterationsOfByDefault(partMaster);
        if (part != null) {
          vec.add(part);
        }
      }
      if (partInfos != null) {
        for (int i = 0; i < partInfos.length; i++) {
          if (getOptionPart(depart,
                            (QMPartMasterIfc) partInfos[i].getMaster())) {
            vec.add(partInfos[i]);
          }
        }
      }
    }

    catch (QMException ex) {
      throw ex;
    }
    return vec;
  }

  /**
   * ����·����ض���ע�Ᵽ֤˳��  //��getRouteContainer����  lm  add  20040827
   * @param routeID String
   * @param �б仯�ķ�ֵ֧����
   * @return HashMap
   */
  private HashMap getRouteContainer2(String routeID,
                                     TechnicsRouteIfc oldRoute,
                                     Collection branchInfos) throws QMException {
    HashMap relaventObejts = new HashMap();
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
//1.���·�ߵ�HashMap��
    TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) pservice.refreshInfo(
        routeID);
    setupNewBaseValueIfc(routeInfo);
    routeInfo.setRouteDescription(oldRoute.getRouteDescription());
    RouteItem item = createRouteItem(routeInfo);
    relaventObejts.put(TECHNICSROUTE_BSONAME, item);
    //��ýڵ㼰�ڵ������
    Object[] obj1 = getRouteNodeAndNodeLink(routeID);
    //�ڵ㼯�ϡ�
    Collection nodes = (Collection) obj1[0];
    //�ڵ�������ϡ�
    Collection nodeLinks = (Collection) obj1[1];
    //2.��ӽڵ�������ϵ�HashMap
    //�����еĶ���ΪRouteItem.
    Collection nodeLinkItemVec = new Vector();
    for (Iterator iter1 = nodeLinks.iterator(); iter1.hasNext(); ) {
      RouteNodeLinkIfc nodeLinkInfo = (RouteNodeLinkIfc) iter1.next();
      //���ù���Դ�ڵ㡣
      RouteNodeIfc sourceNode = nodeLinkInfo.getSourceNode();
      RouteNodeIfc node1 = (RouteNodeIfc) getTheSameInfo(sourceNode,
          nodes);
      nodeLinkInfo.setSourceNode(node1);
      //���ù���Ŀ�Ľڵ㡣
      RouteNodeIfc destNode = nodeLinkInfo.getDestinationNode();
      RouteNodeIfc node2 = (RouteNodeIfc) getTheSameInfo(destNode, nodes);
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
    for (Iterator iter = branchNodeLinks.iterator(); iter.hasNext(); ) {
      RouteBranchNodeLinkIfc branchNodeLinkInfo = (RouteBranchNodeLinkIfc)
          iter.
          next();
      //��÷�֦item��
      TechnicsRouteBranchIfc branchInfo = branchNodeLinkInfo.
          getRouteBranchInfo();
      //����ڷ�֦��ڵ�����еķ�֧��branches�Ķ�Ӧֵ��
      TechnicsRouteBranchIfc branch1 = (TechnicsRouteBranchIfc)
          getTheSameInfo(
          branchInfo, branches);
      branchNodeLinkInfo.setRouteBranchInfo(branch1);
      RouteNodeIfc branchNode = branchNodeLinkInfo.getRouteNodeInfo();
      //����ڷ�֦��ڵ�����еĽڵ���nodes�Ķ�Ӧֵ��
      RouteNodeIfc node1 = (RouteNodeIfc) getTheSameInfo(branchNode,
          nodes);
      branchNodeLinkInfo.setRouteNodeInfo(node1);
      RouteItem branchNodeLinkItem = getBranchNodeLinkItem(
          branchNodeLinkInfo);
      branchNodeLinkItemVec.add(branchNodeLinkItem);
    }
    relaventObejts.put(BRANCHNODE_LINKBSONAME, branchNodeLinkItemVec);
     //4.��ӷ�֧���ϵ�HashMap
    Collection branchItemVec = new Vector();
    for (Iterator iter11 = branches.iterator(); iter11.hasNext(); ) {
      TechnicsRouteBranchIfc branchInfo = (TechnicsRouteBranchIfc) iter11.
          next();
      if (branchInfos != null) {
        if (branchInfo.getBsoID() == null) {
          throw new TechnicsRouteException("��ֵ֧��������Ϊ�յ�ʱ�����ԡ�");
        }
        //���ݴ����ֵ֧���󼯺������Ƿ���Ҫ·����Ϣ��
        for (Iterator branchIter = branchInfos.iterator();
             branchIter.hasNext(); ) {
          TechnicsRouteBranchIfc paraBranch = (TechnicsRouteBranchIfc)
              branchIter.next();
          if (branchInfo.getBsoID().trim().equals(paraBranch.getBsoID().
                                                  trim())) {
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
    for (Iterator iter2 = nodes.iterator(); iter2.hasNext(); ) {
      RouteNodeIfc nodeInfo = (RouteNodeIfc) iter2.next();
      RouteItem nodeItem = getNodeItem(nodeInfo, routeInfo);
      nodeItemVec.add(nodeItem);
    }
    relaventObejts.put(ROUTENODE_BSONAME, nodeItemVec);
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's    VERBOSE begin.....................");
      //TECHNICSROUTE_BSONAME��ROUTENODE_LINKBSONAME��TECHNICSROUTEBRANCH_BSONAME
      //ROUTENODE_BSONAME��BRANCHNODE_LINKBSONAME
      //1.·�ߡ�
      System.out.println(
          "1.route.........................................");
      RouteItem routeItem1 = (RouteItem) relaventObejts.get(
          TECHNICSROUTE_BSONAME);
      TechnicsRouteIfc route = (TechnicsRouteIfc) routeItem1.getObject();
      System.out.println(TIME + "route.... routeID = " + route.getBsoID());
      System.out.println(TIME + "route.... routehashCode = " +
                         route.hashCode());
      //2.�ڵ㡣
//      System.out.println(
//          "2.node.........................................");
      Collection nodes1 = (Collection) relaventObejts.get(
          ROUTENODE_BSONAME);
      for (Iterator iterator = nodes1.iterator(); iterator.hasNext(); ) {
        RouteItem item1 = (RouteItem) iterator.next();
        RouteNodeIfc node = (RouteNodeIfc) item1.getObject();
        System.out.println(TIME + "node..... nodeID = " + node.getBsoID() +
                           ", routeID = " +
                           node.getRouteInfo().getBsoID());
        System.out.println(TIME + "node..... hashCode = " +
                           node.getBsoID() +
                           ", routehashCode = " +
                           node.getRouteInfo().hashCode());
      }
      System.out.println(
          "3.nodeLink.........................................");
      //3.�ڵ������
      Collection nodelinks1 = (Collection) relaventObejts.get(
          ROUTENODE_LINKBSONAME);
      for (Iterator iterator = nodelinks1.iterator(); iterator.hasNext(); ) {
        RouteItem item1 = (RouteItem) iterator.next();
        RouteNodeLinkIfc nodeLink = (RouteNodeLinkIfc) item1.getObject();
        System.out.println(TIME + "nodeLink..... nodeLinkID = " +
                           nodeLink.getBsoID() +
                           ", routeID = " +
                           nodeLink.getRouteInfo().getBsoID() +
                           ", sourceNodeID = " +
                           nodeLink.getSourceNode().getBsoID() +
                           nodeLink.getDestinationNode().getBsoID());
        System.out.println(TIME + "nodeLink..... nodeLinkHashCode = " +
                           nodeLink.hashCode() +
                           ", routeHashCode = " +
                           nodeLink.getRouteInfo().hashCode() +
                           ", sourceNodeHashCode = " +
                           nodeLink.getSourceNode().hashCode() +
                           nodeLink.getDestinationNode().hashCode());
      }
      //4.��֧
      System.out.println(
          "4.branch.........................................");
      Collection branchs1 = (Collection) relaventObejts.get(
          TECHNICSROUTEBRANCH_BSONAME);
      for (Iterator iterator = branchs1.iterator(); iterator.hasNext(); ) {
        RouteItem item1 = (RouteItem) iterator.next();
        TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc) item1.
            getObject();
        System.out.println(TIME + "branch..... brachID = " +
                           branch.getBsoID() +
                           ", routeID = " +
                           branch.getRouteInfo().getBsoID());
        System.out.println(TIME + "branch..... brachHashCode = " +
                           branch.hashCode() + ", routeHashCode = " +
                           branch.getRouteInfo().hashCode());
      }
      //5.��֦������
      System.out.println(
          "5.branchNodeLink.........................................");
      Collection brancheNodes1 = (Collection) relaventObejts.get(
          BRANCHNODE_LINKBSONAME);
      for (Iterator iterator = brancheNodes1.iterator(); iterator.hasNext(); ) {
        RouteItem item1 = (RouteItem) iterator.next();
        RouteBranchNodeLinkIfc branchNode = (RouteBranchNodeLinkIfc)
            item1.
            getObject();
        System.out.println(TIME + "branchNode..... branchNodeID = " +
                           branchNode.getBsoID() +
                           ", branchID = " +
                           branchNode.getRouteBranchInfo().getBsoID()
                           + ", nodeID = " +
                           branchNode.getRouteNodeInfo().getBsoID());
        System.out.println(TIME +
                           "branchNode..... branchNodeHashCode = " +
                           branchNode.hashCode() +
                           ", branchHashCode = " +
                           branchNode.getRouteBranchInfo().hashCode()
                           + ", nodeHashCode = " +
                           branchNode.getRouteNodeInfo().hashCode());
      }
      System.out.println(TIME +
                         "routeService's getRouteContainer VERBOSE end.....................");
    }
    return relaventObejts;
  }

  /**
   *  ���ݸ��������쵥λ��ѯ��ص��㲿���͹���·��id
   * @param makeDepartment String ���쵥λ
   * @throws QMException
   * @return Vector ��ŵ��� <br>
   * String[] {listRoutePartLink.getPartMasterID(),
   *                          routeid,
   *                          listRoutePartLink.getAdoptStatus(),
   *                           listRoutePartLink.getBsoID()}
   */
  // * added by skybird 2005.3.9
  public Vector getAllPartsRoutesM(String makeDepartment) throws QMException {
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
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(ROUTENODE_BSONAME);
    QueryCondition cond = new QueryCondition("nodeDepartment",
                                             QueryCondition.EQUAL,
                                             makeDepartment);
    query.addCondition(cond);
    query.addAND();
    QueryCondition cond1 = new QueryCondition("routeType",
                                              QueryCondition.EQUAL,
                                              "MANUFACTUREROUTE");
    query.addCondition(cond1);
    coll = pservice.findValueInfo(query);
    if (coll != null && coll.size() != 0) {
      iterate = coll.iterator();
      while (iterate.hasNext()) {
        RouteNodeIfc tmp = (RouteNodeIfc) iterate.next();
        //�����������ظ���·��
        if (routeidVec.contains(tmp.getRouteInfo())) {
          continue;
        }
        routeidVec.addElement(tmp.getRouteInfo());
        if (VERBOSE) {
          System.out.print("" + tmp.getRouteInfo().getBsoID() + ",");
          System.out.println("4450");
        }
      }
      if (routeidVec.size() != 0) {
        System.out.println("the num of route" + routeidVec.size());
      }
      //�����ǲ�Ѱ�㲿��·�ߺ�·�߱������������
      for (int i = 0; i < routeidVec.size(); i++) {
        technicsRoute = (TechnicsRouteIfc) routeidVec.elementAt(i);
        routeid = technicsRoute.getBsoID();
        if (VERBOSE) {
          System.out.println("4461row routeid" + routeid);
        }
        //���µĲ�ѯ���ܲ����ظ�
        query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        cond = new QueryCondition("routeID", QueryCondition.EQUAL,
                                  routeid);
        query.addCondition(cond);
        coll = pservice.findValueInfo(query);
        if (VERBOSE) {
          System.out.println("�����õ�expected num is 1:" + coll.size());
        }
        if (coll != null && coll.size() != 0) {
          iterate = coll.iterator();
          while (iterate.hasNext()) {
            listRoutePartLink = (ListRoutePartLinkIfc) iterate.next();

            result.addElement(new String[] {listRoutePartLink.getPartMasterID(),
                              routeid,
                              listRoutePartLink.getAdoptStatus(),
                              listRoutePartLink.getBsoID()});
          }
        }
      }
      return result;
    }
    return result;
  }


  /**
   *  ���ݸ�����װ�䵥λ��ѯ��ص��㲿���͹���·��id
   * @param constructDepartment String  װ�䵥λ
   * @throws QMException
   * @return Vector ��ŵ��� String[] {listRoutePartLink.getPartMasterID(),
   *                           routeid,
   *                           listRoutePartLink.getAdoptStatus(),
   *                           listRoutePartLink.getBsoID()}
   */
  // * added by skybird 2005.3.9
  public Vector getAllPartsRoutesC(String constructDepartment) throws
      QMException {
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
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(ROUTENODE_BSONAME);
    QueryCondition cond = new QueryCondition("nodeDepartment",
                                             QueryCondition.EQUAL,
                                             constructDepartment);
    query.addCondition(cond);
    query.addAND();
    QueryCondition cond1 = new QueryCondition("routeType",
                                              QueryCondition.EQUAL,
                                              "ASSEMBLYROUTE");
    query.addCondition(cond1);
    coll = pservice.findValueInfo(query);
    if (coll != null && coll.size() != 0) {
      iterate = coll.iterator();
      while (iterate.hasNext()) {
        RouteNodeIfc tmp = (RouteNodeIfc) iterate.next();
        if (routeidVec.contains(tmp.getRouteInfo())) {
          continue;
        }
        routeidVec.addElement(tmp.getRouteInfo());
      }
      //�����ǲ�Ѱ�㲿��·�ߺ�·�߱������������
      for (int i = 0; i < routeidVec.size(); i++) {
        technicsRoute = (TechnicsRouteIfc) routeidVec.elementAt(i);
        routeid = technicsRoute.getBsoID();
        //���µĲ�ѯ���ܲ����ظ�
        query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        cond = new QueryCondition("routeID", QueryCondition.EQUAL,
                                  routeid);
        query.addCondition(cond);
        coll = pservice.findValueInfo(query);
        if (coll != null && coll.size() != 0) {
          iterate = coll.iterator();
          while (iterate.hasNext()) {
            listRoutePartLink = (ListRoutePartLinkIfc) iterate.next();

            result.addElement(new String[] {listRoutePartLink.getPartMasterID(),
                              routeid,
                              listRoutePartLink.getAdoptStatus(),
                              listRoutePartLink.getBsoID()});
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
   * @param constructDepartment String װ�䵥λ
   * @throws QMException
   * @return Vector  ���������vector:<br>
   * 1.mResult Vector:�������쵥λ��ѯ���صĽ����<br>
   * ��ŵ���String[] {listRoutePartLink.getPartMasterID(),
   *                           routeid,
   *                           listRoutePartLink.getAdoptStatus(),
   *                           listRoutePartLink.getBsoID()}
   * 2.cResult Vector������װ�䵥λ��ѯ���صĽ��;<br>
   * ��ŵ���String[] {listRoutePartLink.getPartMasterID(),
   *                           routeid,
   *                           listRoutePartLink.getAdoptStatus(),
   *                           listRoutePartLink.getBsoID()}
   */
  //* added by skybird 2005.3.9
  public Vector getAllPartsRoutesA(String makeDepartment,
                                   String constructDepartment) throws
      QMException {
    //�洢���صĽ��
    Vector result = new Vector();
    //�������쵥λ��ѯ���صĽ��
    Vector mResult = new Vector();
    //����װ�䵥λ��ѯ���صĽ��
    Vector cResult = new Vector();
    mResult = getAllPartsRoutesM(makeDepartment);
    cResult = getAllPartsRoutesC(makeDepartment);
    if ( (mResult == null || mResult.size() == 0) ||
        (cResult == null || cResult.size() == 0)) {
      return result;
    }
    else {

      for (int i = 0; i < cResult.size(); i++) {
        String[] tmp = (String[]) cResult.elementAt(i);
        for (int j = 0; j < mResult.size(); j++) {
          String[] tmp1 = (String[]) mResult.elementAt(j);
          if (tmp1[1].equals(tmp[1])) {
            if (tmp1[0].equals(tmp[0])) {
              result.addElement(mResult.elementAt(j));
            }
            else {
              result.addElement(mResult.elementAt(j));
              result.addElement(cResult.elementAt(i));
            }
          }
        }
      } //end for
      if (VERBOSE) {
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
  private Collection sortRouteAndLinks(Collection routeAndLinks) {
    QMPartMasterIfc partmaster;
    BaseValueIfc[] bsos;
    Vector result = new Vector();
    Iterator routeIterator1 = routeAndLinks.iterator();
    while (routeIterator1.hasNext()) {

      bsos = (BaseValueIfc[]) routeIterator1.next();
      partmaster = (QMPartMasterIfc) bsos[2];
      QMPartMasterIfc temp;
      if (result.size() == 0) {
    	 
        result.add(bsos);
      }
      else {
    	  
        boolean flag = true;
        for (int i = 0; i < result.size(); i++) {
          temp = (QMPartMasterIfc) ( ( (BaseValueIfc[]) result.elementAt(i))[2]);
          if (partmaster.getPartNumber().compareTo(temp.getPartNumber()) <0) {
            result.add(i, bsos);
            flag=false;
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
   * @param productID String  ��Ʒ��id
   * @param source String  �㲿������Դ
   * @param type String  �㲿��������
   * @throws QMException
   * @return Collection vector����:<br>
   *      part.getLifeCycleState().getDisplay()<br>
   *       part.getBsoID()<br>
   *       partmaster.getPartNumber()<br>
   *       partmaster.getPartName()<br>
   *       part.getVersionValue()<br>
   *       lrpLink.getParentPartNum()<br>
   * Integer(this.calCountInProduct(part,
                  lrpLink.getParentPart(), product, boms, midleBoms))<br>
   *       route.getRouteDescription()<br>
   * ������ϡ�ÿ����������һ��Vector.���η��㲿���������ڣ��㲿��id���㲿����ţ��㲿�����ƣ�
   * �汾�������ţ��㲿���ڲ�Ʒ���������ϣ�·��������·�߼��ϣ�����·�߼���ÿ����Ŀ��������װ��2��·�ߣ�
   */
  /*delete by guoxl on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)
  public Collection getAllPartsRoutes(String mDepartment, String cDepartment,
                                      String productID, String source,
                                      String type) throws QMException {
    if (VERBOSE) {
      System.out.println("����·�߷���getAllPartsRoutes���� :" + "mDepartment = " +
                         mDepartment + "  cDepartment=" + cDepartment +
                         " productID =" + productID);
    }
    try {
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      StandardPartService partService = (StandardPartService) EJBServiceHelper.
          getService(PART_SERVICE);
      VersionControlService vcservice = (VersionControlService)
          EJBServiceHelper.getService
          ("VersionControlService");
      if (productID != null && !productID.trim().equals("")) {
        QMPartIfc product = (QMPartIfc) pservice.refreshInfo(productID);
        //�˷�����part���ṩ
        Vector subs = (Vector) partService.getAllSubParts(product);
        Vector products = new Vector();
        products.add(product);
        return getColligationRoutesDetial(mDepartment, cDepartment, subs,
                                          products, false);
      }
      else {
        PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)
                                         partService.findPartConfigSpecIfc();
        HashMap bomMap = new HashMap();
        Vector mdepartIDs = new Vector();
        Vector cdepartIDs = new Vector();
        if (mDepartment != null && !mDepartment.trim().equals("")) {
          BaseValueIfc base = (BaseValueIfc) pservice.refreshInfo(mDepartment);
          mdepartIDs = this.getAllSubDepartMentID(mdepartIDs, base);
        }
        if (cDepartment != null && !cDepartment.trim().equals("")) {
          BaseValueIfc base = (BaseValueIfc) pservice.refreshInfo(cDepartment);
          cdepartIDs = this.getAllSubDepartMentID(cdepartIDs, base);
        }
        Collection routeAndLinks = this.getRouteByPartAndDep(mdepartIDs,
            cdepartIDs, null);
       // System.out.println("5383�� getRouteByPartAndDep �õ��� routeAndLinks ���ϵĳ���" + routeAndLinks.size());
        if (routeAndLinks != null && routeAndLinks.size() > 0) {
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
        //(�����)20060701 �����޸� begin
        while (routeIterator.hasNext()) {
          bsos = (BaseValueIfc[]) routeIterator.next();
          partmaster = (QMPartMasterIfc) bsos[2];
          // �õ�ǰ�û������ù淶����
          if (!parts.containsKey(partmaster.getBsoID())) {
            //parts.put(partmaster.getBsoID(),
            //          this.filteredIterationsOfByDefault(partmaster));
              parts.put(partmaster.getBsoID(),null);
              partMasters.add(partmaster);
          }
        }
        Collection filteredPartmasters = this.filteredIterationsOfByDefault(partMasters);
        for(Iterator f = filteredPartmasters.iterator();f.hasNext();)
        {
        	Object[] obj = (Object[])f.next();
                parts.put(((QMPartIfc)obj[0]).getMasterBsoID(),((BaseValueIfc)obj[0]));
        }
        //(�����)20060701 �����޸� end
        //(�����)20060725 �����޸� end
        Vector products = this.getAllParentProductOnce(new Vector(parts.values()));
         //(�����)20060725 �����޸� end
        Iterator routeIterator1 = routeAndLinks.iterator();
        Vector content = new Vector();
        Vector result = new Vector();
        Vector productNums = new Vector();
        for (int j = 0; j < products.size(); j++) {
          QMPartIfc tempP = (QMPartIfc) products.elementAt(j);
          productNums.add(tempP.getPartNumber() + "(" + tempP.getVersionValue() +
                          ")");
        }
        result.add(productNums);
        while (routeIterator1.hasNext()) {
          Vector temp = new Vector();
          HashMap map;
          bsos = (BaseValueIfc[]) routeIterator1.next();

          lrpLink = (ListRoutePartLinkIfc) bsos[0];
          route = (TechnicsRouteIfc) bsos[1];
        //  System.out.println("��bsos��ȡ������route " +route.getBsoID() );
          partmaster = (QMPartMasterIfc) bsos[2];
          //ȡ���°汾���е�����
          part = (QMPartIfc) parts.get(partmaster.getBsoID());
          temp.add(part.getLifeCycleState().getDisplay());
          temp.add(part.getBsoID());
          temp.add(partmaster.getPartNumber());
          temp.add(partmaster.getPartName());
          temp.add(part.getVersionValue());
          temp.add(lrpLink.getParentPartNum());
          QMPartIfc product;
          Vector counts = new Vector();
          for (int j = 0; j < products.size(); j++) {
            product = (QMPartIfc) products.elementAt(j);
            List boms = null;
            List midleBoms = null;
            if(lrpLink.getParentPartID()==null)
                counts.add(new Integer(0));
            else{

              if (bomMap.containsKey(product.getBsoID()))
                boms = (List) bomMap.get(product.getBsoID());
              else {
               String[] attrNames = {"quantity"};
                boms = partService.setBOMList(product, attrNames, null, null, null,
                                              partConfigSpecIfc);
                bomMap.put(product.getBsoID(), boms);
              }
              if (bomMap.containsKey(lrpLink.getParentPart().getBsoID()))
                midleBoms = (List) bomMap.get(lrpLink.getParentPart().getBsoID());
              else {
                String[] attrNames = {"quantity"};
                midleBoms = partService.setBOMList(lrpLink.getParentPart(), attrNames, null, null, null,
                    partConfigSpecIfc);
                bomMap.put(lrpLink.getParentPart().getBsoID(), midleBoms);
              }
              //���⣬�������Ϊ0û����.���⣬�����ж��Ƿ��Ʒ����·�߱��Ӧ�ģ�����ǣ�����ֱ����
              counts.add(new Integer(this.calCountInProduct(part,
                  lrpLink.getParentPart(), product, boms, midleBoms)));
            }
          }
          temp.add(counts);
          // �������壩�ݿͻ��鿴ʱ����ֱ�Ӵ�TechnicsRouteBranchIfc�������������·�ߴ��ַ�����ȡ zz start
//         map = (HashMap) getRouteBranchs(route.getBsoID()); //ԭ����
//          assembleBranchStr(temp, map);//ԭ����
          map = (HashMap)   getDirectRouteBranchStrs(route.getBsoID());
             assembleBranchStrForThin(temp, map);
          // �������壩�ݿͻ��鿴ʱ����ֱ�Ӵ�TechnicsRouteBranchIfc�������������·�ߴ��ַ�����ȡ zz end
          //add by guoxl 2008.11.17(���·�߱��ţ���ӵ�������)
             String routeListNum=lrpLink.getLeftBsoID();
             TechnicsRouteListIfc routeListIfc=(TechnicsRouteListIfc) pservice.refreshInfo(routeListNum);
             temp.add(routeListIfc.getRouteListNumber());
             //add end by guoxl
          temp.add(route.getRouteDescription());
          content.add(temp);
        }
        result.add(content);
        return result;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }delete end  by guoxl  end on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)*/
//add by guoxl on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)
  public Collection getAllPartsRoutes(String mDepartment, String cDepartment,
                                      String productID, String source,
                                      String type) throws QMException {
	  
    if (VERBOSE) {
        System.out.println("���뷽��==getAllPartsRoutes=="+"mDepartment==="+mDepartment+"/n"+
        		          "cDepartment======="+cDepartment+"productID====="+productID+"/n"+
        		          "source====="+source+"/n"+"type========="+type);
    }
    try {
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      StandardPartService partService = (StandardPartService) EJBServiceHelper.
          getService(PART_SERVICE);
      VersionControlService vcservice = (VersionControlService)
          EJBServiceHelper.getService
          ("VersionControlService");
      if (productID != null && !productID.trim().equals("")) {
    	 
        QMPartIfc product = (QMPartIfc) pservice.refreshInfo(productID);
       
        Vector subs = new Vector();
        subs.add(product);
        Vector products = new Vector();
       products.add(product);
        
        return getColligationRoutesDetial(mDepartment, cDepartment, subs,
                                          products, false);
      }//ֻ�е�λ
      else {
    	  //�����������ù��
    	
        PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)
                                         partService.findPartConfigSpecIfc();
      
        HashMap bomMap = new HashMap();
        Vector mdepartIDs = new Vector();
        Vector cdepartIDs = new Vector();
        if (mDepartment != null && !mDepartment.trim().equals("")) {
          BaseValueIfc base = (BaseValueIfc) pservice.refreshInfo(mDepartment);

          mdepartIDs.add(base.getBsoID());
          
        }
        if (cDepartment != null && !cDepartment.trim().equals("")) {
          BaseValueIfc base = (BaseValueIfc) pservice.refreshInfo(cDepartment);
          
          cdepartIDs.add(base.getBsoID());
          
        }
        
        //ͨ���������ãӣѣ̲���
        Collection routeAndLinks = this.getRouteByPartAndDep(mdepartIDs,
            cdepartIDs, null);

        if (routeAndLinks != null && routeAndLinks.size() > 0) {
        	
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

        HashMap partParentsMap=new HashMap();
       
        //(�����)20060701 �����޸� begin
        while (routeIterator.hasNext()) {
          bsos = (BaseValueIfc[]) routeIterator.next();
        //add by guoxl on 2008-12-19(���ʵʩ����)
          lrpLink = (ListRoutePartLinkIfc) bsos[0];
          QMPartIfc parentsPartIfc=(QMPartIfc) lrpLink.getParentPart();
          
          if(parentsPartIfc!=null) {
        	  
        	  if(partParentsMap.containsKey(parentsPartIfc.getBsoID())){
        		
        		  continue;
        	  }
        	  else{
        		  partParentsMap.put(parentsPartIfc.getBsoID(), parentsPartIfc);
        		 
        	  }
        	  
          }
          //add by guoxl on 2008-12-19(���ʵʩ����)
          partmaster = (QMPartMasterIfc) bsos[2];
          // �õ�ǰ�û������ù淶����
          if (!parts.containsKey(partmaster.getBsoID())) {
           
              parts.put(partmaster.getBsoID(),null);
              partMasters.add(partmaster);
          }
        }
        //ͨ��partMasters�������ù淶����,Ȼ�󷵻�QMpartIfc����
        Collection filteredPartmasters = this.filteredIterationsOfByDefault(partMasters);
      
        for(Iterator f = filteredPartmasters.iterator();f.hasNext();)
        {
        	Object[] obj = (Object[])f.next();
                parts.put(((QMPartIfc)obj[0]).getMasterBsoID(),((BaseValueIfc)obj[0]));
                
        }
        //(�����)20060701 �����޸� end
        //(�����)20060725 �����޸� end
        
        
        //add by guoxl on 2008-12-19(���ʵʩ����)
        Vector products=new Vector(partParentsMap.values());
        //add by guoxl end on 2008-12-19(���ʵʩ����)
        
         //(�����)20060725 �����޸� end
        Iterator routeIterator1 = routeAndLinks.iterator();
        Vector content = new Vector();
        Vector result = new Vector();
        Vector productNums = new Vector();
        
        for (int j = 0; j < products.size(); j++) {
          QMPartIfc tempP = (QMPartIfc) products.elementAt(j);
         
          productNums.add(tempP.getPartNumber() + "(" + tempP.getVersionValue() +")");
          
        }
       result.add(productNums);

        while (routeIterator1.hasNext()) {
          Vector temp = new Vector();
          HashMap map;
          bsos = (BaseValueIfc[]) routeIterator1.next();
          
          lrpLink = (ListRoutePartLinkIfc) bsos[0];
          
          route = (TechnicsRouteIfc) bsos[1];
        //  System.out.println("��bsos��ȡ������route " +route.getBsoID() );
          partmaster = (QMPartMasterIfc) bsos[2];
          //ȡ���°汾���е�����
          part = (QMPartIfc) parts.get(partmaster.getBsoID());
          temp.add(part.getLifeCycleState().getDisplay());
          temp.add(part.getBsoID());
          temp.add(partmaster.getPartNumber());
          temp.add(partmaster.getPartName());
          temp.add(part.getVersionValue());
          temp.add(lrpLink.getParentPartNum());
          QMPartIfc product;
          Vector counts = new Vector();
          
          int count=lrpLink.getCount();
          String parentsBso=lrpLink.getParentPartID();
          
          for(int j=0;j<products.size();j++){
        	  product = (QMPartIfc) products.elementAt(j);
        	  
        	  if(lrpLink.getParentPartID()==null|| !parentsBso.equals(product.getBsoID())){
        		  
               counts.add(new Integer(0));
               
        	  }else{
           
                 counts.add(new Integer(count));
        	  }
          }
          temp.add(counts);
          
          // �������壩�ݿͻ��鿴ʱ����ֱ�Ӵ�TechnicsRouteBranchIfc�������������·�ߴ��ַ�����ȡ zz start
//         map = (HashMap) getRouteBranchs(route.getBsoID()); //ԭ����
//          assembleBranchStr(temp, map);//ԭ����
          map = (HashMap)   getDirectRouteBranchStrs(route.getBsoID());
             assembleBranchStrForThin(temp, map);
          // �������壩�ݿͻ��鿴ʱ����ֱ�Ӵ�TechnicsRouteBranchIfc�������������·�ߴ��ַ�����ȡ zz end
           //add by guoxl 2008.12.9(���·�߱��ţ���ӵ�������)
             String routeListNum=lrpLink.getLeftBsoID();
             TechnicsRouteListIfc routeListIfc=(TechnicsRouteListIfc) pservice.refreshInfo(routeListNum);

             TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo)routeListIfc.getMaster();
             //������е�����С�汾
             routeListIfc=(TechnicsRouteListIfc)getLatestVesion(masterinfo);
             temp.add(routeListIfc.getRouteListNumber()+"("+routeListIfc.getVersionValue()+")");
             //add end by guoxl
          temp.add(route.getRouteDescription());
          content.add(temp);
        }
        result.add(content);
        return result;
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
//add end by guoxl 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)

  /**
   * ͳ���ۺ�·�߷ֲ�
   * @param mDepartment String ���쵥λid
   * @param cDepartment String װ�䵥λid
   * @param parts Vector Ҫͳ�Ƶ��Ӽ�
   * @param products Vector ��Ʒ�ļ���
   * @throws QMException
   * @return Collection vector���ϣ�
   *  ������ϡ�ÿ����������һ��Vector.���η��㲿���������ڣ��㲿��id���㲿����ţ��㲿�����ƣ�
   * �汾�������ţ��㲿���ڲ�Ʒ���������ϣ�·��������·�߼��ϣ�����·�߼���ÿ����Ŀ��������װ��2��·�ߣ�
   */
  public Collection getColligationRoutes(String mDepartment, String cDepartment,
                                         Vector parts, Vector products) throws
      QMException {
    if (VERBOSE) {
      System.out.println("����·�߷���getColligationRoutes���� :" +
                         "mDepartment = " + mDepartment + "  cDepartment=" +
                         cDepartment + "  parts=" + parts + "  products  =" +
                         products);
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    boolean showAll = false;
    Vector partInfos = new Vector();
    Vector productInfos = new Vector();
    if (parts == null || parts.size() == 0) {
      for (int i = 0; i < products.size(); i++) {
        productInfos.add(pservice.refreshInfo( (String) products.elementAt(i)));
      }
      partInfos = getAllSubParts(productInfos);
      showAll = true;
    }
    if (products == null || products.size() == 0) {
      for (int i = 0; i < parts.size(); i++) {
        this.addToVector(partInfos,
                         (QMPartIfc) pservice.refreshInfo( (String) parts.
            elementAt(i)));
        // partInfos.add(pservice.refreshInfo( (String) parts.elementAt(i)));
      }
      productInfos = this.getAllParentProduct(partInfos);
    }
    if (parts != null && parts.size() != 0 && products != null &&
        products.size() != 0) {
      for (int i = 0; i < parts.size(); i++) {
        this.addToVector(partInfos,
                         (QMPartIfc) pservice.refreshInfo( (String) parts.
            elementAt(i)));
      }
      for (int i = 0; i < products.size(); i++) {
        productInfos.add(pservice.refreshInfo( (String) products.elementAt(i)));
      }
    }
    try{
      //5.25����Ϊû���㲿��Ҳ��ʾ
      return getColligationRoutesDetial(mDepartment, cDepartment, partInfos,
                                        productInfos, true);
    }catch(Exception e)
    {
      e.printStackTrace();
      throw new QMException(e);
    }
  }

  /**
   * �õ���ǰ���ŵ������Ӳ���
   * @param results Vector  �������
   * @param depart BaseValueIfc ��ǰ����
   * @throws QMException
   * @return Vector �������
   */
  private Vector getAllSubDepartMentID(Vector results, BaseValueIfc depart) throws
      QMException {
    CodingManageService cmService = (CodingManageService) EJBServiceHelper.
        getService("CodingManageService");
    results.add(depart.getBsoID());
    if (depart instanceof CodingClassificationIfc) {
      Collection col = cmService.findDirectCodingClassification( (
          CodingClassificationIfc) depart, true);
      
      if (col != null && col.size() > 0) {
        Iterator i = col.iterator();
        while (i.hasNext()) {

       	//��õ�ǰ���ŵ������Ӳ���
          getAllSubDepartMentID(results, (BaseValueIfc) i.next());

        
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
   * @param showAll boolean  �Ƿ���ʾ���в�Ʒ�ṹ��ֻ�е������ۺ�·�߿ͻ���û�����ұߣ��첿������ʱΪtrue
   * @throws QMException
   * @return Collection  ������ϡ�ÿ����������һ��Vector.���η��㲿���������ڣ��㲿��id���㲿����ţ��㲿�����ƣ�
   * �汾�������ţ��㲿���ڲ�Ʒ���������ϣ�·��������·�߼��ϣ�����·�߼���ÿ����Ŀ��������װ��2��·�ߣ�
   */
  private Collection getColligationRoutesDetial(String mDepartment,
                                                String cDepartment,
                                                Vector parts, Vector products,
                                                boolean showAll) throws
      QMException {
    if (VERBOSE) {
      System.out.println("����·�߷���getColligationRoutesDetial���� :" +
                         "mDepartment = " + mDepartment + "  cDepartment=" +
                         cDepartment + "  parts=" + parts + "  products  =" +
                         products);
    }
             

    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    StandardPartService partService = (StandardPartService) EJBServiceHelper.
        getService(PART_SERVICE);
    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)
                                          partService.findPartConfigSpecIfc();
    HashMap bomMap = new HashMap();
    Iterator i = parts.iterator();
    Vector result = new Vector();

    Vector content = new Vector();
    
    QMPartIfc part;
    Vector mdepartIDs = new Vector();
    Vector cdepartIDs = new Vector();
    if (mDepartment != null && !mDepartment.trim().equals("")) {
      BaseValueIfc base = (BaseValueIfc) pservice.refreshInfo(mDepartment);
      mdepartIDs = this.getAllSubDepartMentID(mdepartIDs, base);
    }
    if (cDepartment != null && !cDepartment.trim().equals("")) {
      BaseValueIfc base = (BaseValueIfc) pservice.refreshInfo(cDepartment);
      cdepartIDs = this.getAllSubDepartMentID(cdepartIDs, base);
    }
    while (i.hasNext()) {
      part = (QMPartIfc) i.next();
      Collection routeAndLinks = this.getRouteByPartAndDep(mdepartIDs,
          cdepartIDs, part.getMasterBsoID());
      if (VERBOSE) {
        System.out.println("����·�߷���getRouteByPartAndDep���� :" +
                           "mDepartment = " + mDepartment + "  cDepartment=" +
                           cDepartment + " partMasterID=" + part.getMasterBsoID() +
                           "�õ������" + routeAndLinks);

        //�����Ҫ��ʾ������Ʒ�ṹ����ǰ�첿����û��·�ߣ���ֻ��ʾ��Ʒ�ṹ��Ϣ
      }
      if (showAll && (routeAndLinks == null || routeAndLinks.size() == 0)) {
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
      }
      else {
    	  
        BaseValueIfc[] bsos;
        TechnicsRouteIfc route;
        ListRoutePartLinkIfc lrpLink;
        
        HashMap parentsMap=new HashMap();
        Vector productNums = new Vector();
        
      //add by guoxl
        //��ò��ظ��ĸ�������
        Iterator routeIterator1 = routeAndLinks.iterator();
        while (routeIterator1.hasNext()) {
        	bsos = (BaseValueIfc[]) routeIterator1.next();
        	lrpLink = (ListRoutePartLinkIfc) bsos[0];
        	  
            if(lrpLink.getParentPartID()!=null&&!parentsMap.containsKey(lrpLink.getParentPartID())){
          	  
          	  parentsMap.put(lrpLink.getParentPartID(), lrpLink.getParentPart());
            }
           
            
        }
        products.clear();
        products=new Vector(parentsMap.values());
        
        for (int j = 0; j < products.size(); j++) {
          QMPartIfc tempP = (QMPartIfc) products.elementAt(j);
         
          productNums.add(tempP.getPartNumber() + "(" + tempP.getVersionValue() +
                          ")");
         
        }
        result.add(productNums);
        //add end by guoxl
        Iterator routeIterator = routeAndLinks.iterator();
        while (routeIterator.hasNext()) {
          Vector temp = new Vector();
          HashMap map;
          bsos = (BaseValueIfc[]) routeIterator.next();
          lrpLink = (ListRoutePartLinkIfc) bsos[0];
          route = (TechnicsRouteIfc) bsos[1];
         
          temp.add(part.getLifeCycleState().getDisplay());
          temp.add(part.getBsoID());
          temp.add(part.getPartNumber());
          temp.add(part.getPartName());
          temp.add(part.getVersionValue());
          temp.add(lrpLink.getParentPartNum());
          QMPartIfc product;
          Vector counts = new Vector();
          
          String parentBsoid=lrpLink.getParentPartID();
          int count=lrpLink.getCount();
          for(int j=0;j<products.size();j++){
        	  product = (QMPartIfc) products.elementAt(j);
        	  if(lrpLink.getParentPartID()==null ||!parentBsoid.equals(product.getBsoID())){
                  counts.add(new Integer(0));      
        	}else{
        		 counts.add(new Integer(count));    
        	}
        	  
          }
          //add by guoxl end
          temp.add(counts);
          map = (HashMap) getRouteBranchs(route.getBsoID());
          assembleBranchStr(temp, map);
        //add by guoxl 2008.12.9(���·�߱��ţ���ӵ�������)
          String routeListNum=lrpLink.getLeftBsoID();
          TechnicsRouteListIfc routeListIfc=(TechnicsRouteListIfc) pservice.refreshInfo(routeListNum);

          TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo)routeListIfc.getMaster();
          //������е�����С�汾
          routeListIfc=(TechnicsRouteListIfc)getLatestVesion(masterinfo);
          temp.add(routeListIfc.getRouteListNumber()+"("+routeListIfc.getVersionValue()+")");
          //add end by guoxl--
          temp.add(route.getRouteDescription());
          content.add(temp);
        }
      }
    }
    result.add(content);
    return result;
  }

  /**
   * ��·�ߴ���ӽ������
   * @param result Vector �����
   * @param map HashMap  ·�ߴ�����
   * @return Vector
   */
  private Vector assembleBranchStr(Vector result, HashMap map) {
    if (VERBOSE) {
      System.out.println("����·�߷���assembleBranchStr���� :" +
                         "map = " + map);

    }
    Vector makeBranchs = new Vector();
    if (map == null || map.size() == 0) {
      Vector temp = new Vector();
      temp.add("");
      temp.add("");
      makeBranchs.add(temp);
      result.add(new Integer(1));
      result.add(makeBranchs);
      return result;
    }
    Iterator values = map.values().iterator();
    while (values.hasNext()) {
      Vector temp = new Vector();
      String makeStr = "";
      String assemStr = "";
      Object[] objs = (Object[]) values.next();
      Vector makeNodes = (Vector) objs[0]; //����ڵ�
      RouteNodeIfc asseNode = (RouteNodeIfc) objs[1]; //װ��ڵ�
      if (makeNodes != null && makeNodes.size() > 0) {
        for (int m = 0; m < makeNodes.size(); m++) {
          RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
          if (makeStr == "") {
            makeStr = makeStr + node.getNodeDepartmentName();
          }
          else {
            makeStr = makeStr + "��" + node.getNodeDepartmentName();
          }
        }
      }
      if (asseNode != null) {
        assemStr = asseNode.getNodeDepartmentName();
      }
      if (makeStr == null || makeStr.equals("")) {
        makeStr = "";
      }
      if (assemStr == null || assemStr.equals("")) {
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
    if (VERBOSE) {
      System.out.println("����·�߷���assembleBranchStr���� :" +
                         "makeBranchs = " + makeBranchs
                         );
    }
    return result;
  }
  /**
   * �������壩zz ������ӣ������ݿͻ���ʾ·�ߴ�
   * ��·�ߴ���ӽ������,�ݿͻ����ã�·�ߴ�������branchNodeֱ�Ӵ�branch������ȡ���ַ���
   * @param result Vector �����
   * @param map HashMap  ·�ߴ�����
   * @return Vector
   */
  private Vector assembleBranchStrForThin(Vector result, HashMap map) {
    if (VERBOSE) {
      System.out.println("����·�߷���assembleBranchStr���� :" +
                         "map = " + map);

    }
    Vector makeBranchs = new Vector();
    if (map == null || map.size() == 0) {
      Vector temp = new Vector();
      temp.add("");
      temp.add("");
      makeBranchs.add(temp);
      result.add(new Integer(1));
      result.add(makeBranchs);
      return result;
    }
    Iterator values = map.values().iterator();
    while (values.hasNext()) {
      Vector temp = new Vector();
      String makeStr = "";
      String assemStr = "";
      String unionStr  =(String) values.next();
    //  System.out.println( "unionStr unionStr unionStr " + unionStr);
       if(unionStr!=null){
          StringTokenizer hh = new StringTokenizer(unionStr,"@");
          if(hh.hasMoreTokens()){
          makeStr = hh.nextToken();
          assemStr = hh.nextToken();}}
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
    if (VERBOSE) {
      System.out.println("����·�߷���assembleBranchStr���� :" +
                         "makeBranchs = " + makeBranchs
                         );
    }
    return result;
  }

  private boolean isParentPart(QMPartIfc sub, QMPartIfc parent) throws
      QMException {
    StandardPartService partService = (StandardPartService) EJBServiceHelper.
        getService(PART_SERVICE);
    return partService.isParentPart(sub, parent);
  }

  /**
    * �����Ӽ��ڲ�Ʒ��ʹ�õ�������ֻ�����丸���ڲ�Ʒ�е�������
    * @param parts Vector �Ӽ�
    * @param parent QMPartIfc ����
    * @param product QMPartIfc ��Ʒ
    * @throws QMException
    * @return int �ڲ�Ʒ��ʹ�õ�����
    * @see QMPartInfo
    */
   public HashMap calCountInProduct(Vector parts, QMPartIfc parent,
                                QMPartIfc product) throws QMException {
         if(VERBOSE)
             System.out.println("���� calCountInProduct   parts="+parts+" parent="+parent+" product="+product);
          StandardPartService partService = (StandardPartService) EJBServiceHelper.
              getService(PART_SERVICE);
          PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)
              partService.findPartConfigSpecIfc();
          HashMap map = new HashMap();
          List boms = null;
          List midleBoms = null;
          try {
          	    //long before4 =    System.currentTimeMillis();
                      String[] attrNames = {"quantity"};
            boms = partService.setBOMList(product, attrNames, null, null, null,
                                          partConfigSpecIfc);
          // long after4 =    System.currentTimeMillis();
         //    System.out.println("��ʱcalCountInProduct====="+(after4 - before4));
          }
          catch (Exception e) {

            throw new TechnicsRouteException(e);
          }
          try {
          	//long before5 =    System.currentTimeMillis();
                                        String[] attrNames = {"quantity"};
            midleBoms = partService.setBOMList(parent, attrNames, null, null, null,
                                               partConfigSpecIfc);
              // long after5 =    System.currentTimeMillis();
           //  System.out.println("��ʱcalCountInProduct====="+(after5 - before5));
          }
          catch (Exception ee) {
           throw new TechnicsRouteException(ee);
          }
          for (int i = 0; i < parts.size(); i++) {
            QMPartIfc part = (QMPartIfc) parts.elementAt(i);
            map.put(part.getBsoID(), new Integer(this.calCountInProduct(part,
                parent, product, boms, midleBoms)));
          }
          return map;

   }

   /**
   * �����Ӽ��ڲ�Ʒ��ʹ�õ�������ֻ�����丸���ڲ�Ʒ�е�������
   * @param part QMPartIfc �Ӽ�
   * @param parent QMPartIfc ����
   * @param product QMPartIfc ��Ʒ
   * @throws QMException
   * @return int �ڲ�Ʒ��ʹ������
   * @see QMPartInfo
   */
  public int calCountInProduct(QMPartIfc part, QMPartIfc parent,
                               QMPartIfc product) throws QMException {
    StandardPartService partService = (StandardPartService) EJBServiceHelper.
        getService(PART_SERVICE);
    if (parent == null)
      return 0;
    String count = (String) partService.getPartQuantity(product,(QMPartMasterIfc)parent.getMaster(), part);
   if (VERBOSE)
     System.out.println(part.getBsoID() +part.getPartName() + "��" + parent.getBsoID()+parent.getPartName() +
                        product.getBsoID()+product.getPartName()+
                        "�е�����==" + count);
   if (count != null && !count.trim().equals(""))
     return new Integer(count).intValue();
   else
     return 0;
  }


  /**
   * �����Ӽ��ڲ�Ʒ��ʹ�õ�������ֻ�����丸���ڲ�Ʒ�е�������
   * @param part QMPartIfc �Ӽ�
   * @param parent QMPartIfc ����
   * @param product QMPartIfc ��Ʒ
   * @throws QMException
   * @return int �����Ӽ��ڲ�Ʒ��ʹ�õ���������
   */
  private int calCountInProduct(QMPartIfc part, QMPartIfc parent,
                               QMPartIfc product,List list,List midleList) throws QMException {
    if (parent == null) {
      return 0;
    }
    String count = (String) getPartQuantity(product,
        (QMPartMasterIfc) parent.getMaster(), part,list,midleList);
    if (VERBOSE) {
      System.out.println(part.getBsoID() + part.getPartName() + "��" +
                         parent.getBsoID() + parent.getPartName() +
                         product.getBsoID() + product.getPartName() +
                         "�е�����==" + count);
    }
    if (count != null && !count.trim().equals("")) {
      return new Integer(count).intValue();
    }
    else {
      return 0;
    }
  }

  /**
   * �����Ӽ��ڲ�Ʒ��ʹ�õ�����
   * @param part QMPartIfc �Ӽ�
   * @param parent QMPartIfc ����
   * @throws QMException
   * @return int ����
   */
  private int calCountInProduct(QMPartIfc part, QMPartIfc parent,List list) throws
      QMException {
    if (part.getPartNumber().equals(parent.getPartNumber())) {
      return 1;
    }
    String count = (String) getPartQuantity(parent, part,list);
    if (VERBOSE) {
      System.out.println(part.getBsoID() + part.getPartName() + "��" +
                         parent.getBsoID() + parent.getPartName() +
                         "�е�����==" + count);
    }
    if (count != null && !count.trim().equals("")) {
      return new Integer(count).intValue();
    }
    else {
      return 0;
    }
  }

  /**
   * ͨ�������װ�䵥λ�����쵥λ���·�ߣ�����������ļ���
   * @param mDepartment String ���쵥λ��id
   * @param cDepartment String װ�䵥λ��id
   * @param partMasterID String ��Ʒid
   * @throws QMException
   * @return Collection ������Ϊvector����ʽΪ��·�����㲿��������·�߰����㲿��
   */
  /*delete by guoxl  end on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)
  private Collection getRouteByPartAndDep(Vector mDepartments,
                                          Vector cDepartments,
                                          String partMasterID) throws
      QMException {
    if (VERBOSE) {
      System.out.println("����·�߷���getRouteByPartAndDep���� :" +
                         "mDepartment = " + mDepartments + "  cDepartment=" +
                         cDepartments + " partMasterID=" + partMasterID);

    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();

    QMQuery query = new QMQuery(this.LIST_ROUTE_PART_LINKBSONAME);
    int routeCount = query.appendBso(this.TECHNICSROUTE_BSONAME, true);
    int partCount = query.appendBso("QMPartMaster", true);

    QueryCondition routeToLink = new QueryCondition("routeID", "bsoID");
    query.addCondition(0, routeCount, routeToLink);
    query.addAND();
    QueryCondition partToLink = new QueryCondition("rightBsoID", "bsoID");
    query.addCondition(0, partCount, partToLink);
    query.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              this.ROUTEALTER);
    query.addCondition(0, cond4);
    query.addAND();
    QueryCondition cond3 = new QueryCondition("adoptStatus",
                                              QueryCondition.EQUAL,
                                              RouteAdoptedType.ADOPT.toString());
    query.addCondition(0, cond3);
    if (partMasterID != null) {
      query.addAND();
      QueryCondition partCond = new QueryCondition("bsoID",
          QueryCondition.EQUAL, partMasterID);
      query.addCondition(partCount, partCond);

    }
    int nodeCount = query.appendBso(this.ROUTENODE_BSONAME, false);
    if (cDepartments.size() != 0 || mDepartments.size() != 0) {
      if (mDepartments.size() != 0 && cDepartments.size() == 0) {
        query.addAND();

        query.addLeftParentheses();
        QueryCondition condm = new QueryCondition("nodeDepartment",
                                                  QueryCondition.EQUAL,
                                                  (String) mDepartments.
                                                  elementAt(0));
        query.addCondition(nodeCount, condm);
        for (int i = 1; i < mDepartments.size(); i++) {
          query.addOR();
          QueryCondition condmt = new QueryCondition("nodeDepartment",
              QueryCondition.EQUAL,
              (String) mDepartments.elementAt(i));
          query.addCondition(nodeCount, condmt);
        }
        query.addRightParentheses();

        query.addAND();
        QueryCondition condm1 = new QueryCondition("routeType",
            QueryCondition.EQUAL,
            "MANUFACTUREROUTE");
        query.addCondition(nodeCount, condm1);

      }
      if (cDepartments.size() != 0 && mDepartments.size() == 0) {
        query.addAND();

        query.addLeftParentheses();
        QueryCondition condc = new QueryCondition("nodeDepartment",
                                                  QueryCondition.EQUAL,
                                                  (String) cDepartments.
                                                  elementAt(0));
        query.addCondition(nodeCount, condc);
        for (int i = 1; i < cDepartments.size(); i++) {
          query.addOR();
          QueryCondition condct = new QueryCondition("nodeDepartment",
              QueryCondition.EQUAL,
              (String) cDepartments.elementAt(i));
          query.addCondition(nodeCount, condct);

        }
        query.addRightParentheses();

        query.addAND();
        QueryCondition condc1 = new QueryCondition("routeType",
            QueryCondition.EQUAL,
            "ASSEMBLYROUTE");
        query.addCondition(nodeCount, condc1);
      }
      if (mDepartments.size() != 0 && cDepartments.size() != 0) {
        query.addAND();

        query.addLeftParentheses();
        QueryCondition condc = new QueryCondition("nodeDepartment",
                                                  QueryCondition.EQUAL,
                                                  (String) cDepartments.
                                                  elementAt(0));
        query.addCondition(nodeCount, condc);
        for (int i = 1; i < cDepartments.size(); i++) {
          query.addOR();
          QueryCondition condct = new QueryCondition("nodeDepartment",
              QueryCondition.EQUAL,
              (String) cDepartments.elementAt(i));
          query.addCondition(nodeCount, condct);

        }
        query.addRightParentheses();

        query.addAND();
        QueryCondition condc1 = new QueryCondition("routeType",
            QueryCondition.EQUAL,
            "ASSEMBLYROUTE");
        query.addCondition(nodeCount, condc1);
        int mNodeCount = query.appendBso(this.ROUTENODE_BSONAME, false);
        query.addAND();

        query.addLeftParentheses();
        QueryCondition condm = new QueryCondition("nodeDepartment",
                                                  QueryCondition.EQUAL,
                                                  (String) mDepartments.
                                                  elementAt(0));
        query.addCondition(nodeCount, condm);
        for (int i = 1; i < mDepartments.size(); i++) {
          query.addOR();
          QueryCondition condmt = new QueryCondition("nodeDepartment",
              QueryCondition.EQUAL,
              (String) mDepartments.elementAt(i));
          query.addCondition(mNodeCount, condmt);
        }
        query.addRightParentheses();

        query.addAND();
        QueryCondition condm1 = new QueryCondition("routeType",
            QueryCondition.EQUAL,
            "MANUFACTUREROUTE");
        query.addCondition(mNodeCount, condm1);

      }
    }
    query.addAND();
    QueryCondition nodetoRoute = new QueryCondition("routeID", "bsoID");
    query.addCondition(nodeCount, routeCount, nodetoRoute);
    // query.addOrderBy(partCount, "partNumber");
    query.setDisticnt(true);
    if (VERBOSE) {
      System.out.println("getRouteByPartAndDep ��ѯ����Ϊ�� " + query.getDebugSQL());
    }
    return pservice.findValueInfo(query);

  }delete end by guoxl  end on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)*/
  //add by guoxl on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)
  private Collection getRouteByPartAndDep(Vector mDepartments,
                                          Vector cDepartments,
                                          String partMasterID) throws
      QMException {
    if (VERBOSE) {
      System.out.println("����·�߷���getRouteByPartAndDep���� :" +
                         "mDepartment = " + mDepartments + "  cDepartment=" +
                         cDepartments + " partMasterID=" + partMasterID);

    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
//���������в������ݣ�����·���������������·�߱������������Ϣ
    QMQuery query = new QMQuery(this.LIST_ROUTE_PART_LINKBSONAME);
    int routeCount = query.appendBso(this.TECHNICSROUTE_BSONAME, true);
    int partCount = query.appendBso("QMPartMaster", true);

    QueryCondition routeToLink = new QueryCondition("routeID", "bsoID");
    query.addCondition(0, routeCount, routeToLink);//���һ����͵ڶ�������ӹ�������routeID=bsoID
    query.addAND();
    QueryCondition partToLink = new QueryCondition("rightBsoID", "bsoID");
    query.addCondition(0, partCount, partToLink);//���һ����͵���������ӹ�������rightBsoID=bsoID
    query.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              this.ROUTEALTER);
    query.addCondition(0, cond4);
    query.addAND();
    QueryCondition cond3 = new QueryCondition("adoptStatus",
                                              QueryCondition.EQUAL,
                                              RouteAdoptedType.ADOPT.toString());
    query.addCondition(0, cond3);
    if (partMasterID != null) {
      query.addAND();
      QueryCondition partCond = new QueryCondition("bsoID",
          QueryCondition.EQUAL, partMasterID);
      query.addCondition(partCount, partCond);

    }
    int nodeCount = query.appendBso(this.ROUTENODE_BSONAME, false);
    //add by guoxl on 2008-12-19(���ʵʩ����)
    int mNodeCount = query.appendBso(this.ROUTENODE_BSONAME, false);
    //add by guoxl end on 2008-12-19(���ʵʩ����)
    if (cDepartments.size() != 0 || mDepartments.size() != 0) {
      if (mDepartments.size() != 0 && cDepartments.size() == 0) {
        query.addAND();

        query.addLeftParentheses();
        QueryCondition condm = new QueryCondition("nodeDepartment",
                                                  QueryCondition.EQUAL,
                                                  (String) mDepartments.
                                                  elementAt(0));
        query.addCondition(nodeCount, condm);
        
        query.addRightParentheses();

        query.addAND();
        QueryCondition condm1 = new QueryCondition("routeType",
            QueryCondition.EQUAL,
            "MANUFACTUREROUTE");
        
        query.addCondition(nodeCount, condm1);

      }
      if (cDepartments.size() != 0 && mDepartments.size() == 0) {
        query.addAND();

        query.addLeftParentheses();
        QueryCondition condc = new QueryCondition("nodeDepartment",
                                                  QueryCondition.EQUAL,
                                                  (String) cDepartments.
                                                  elementAt(0));
        query.addCondition(nodeCount, condc);
       
        query.addRightParentheses();

        query.addAND();
        QueryCondition condc1 = new QueryCondition("routeType",
            QueryCondition.EQUAL,
            "ASSEMBLYROUTE");
        query.addCondition(nodeCount, condc1);
      }
      if (mDepartments.size() != 0 && cDepartments.size() != 0) {
        query.addAND();

       query.addLeftParentheses();
        QueryCondition condc = new QueryCondition("nodeDepartment",
                                                  QueryCondition.EQUAL,
                                                  (String) cDepartments.
                                                  elementAt(0));

        	
        //�ڱ�routeNode���������Ϊָ��װ�䵥λ�ļ���
        query.addCondition(nodeCount, condc);
       
        query.addRightParentheses();

        query.addAND();
        QueryCondition condc1 = new QueryCondition("routeType",
            QueryCondition.EQUAL,
            "ASSEMBLYROUTE");
        query.addCondition(nodeCount, condc1);
       
        query.addAND(); 

      query.addLeftParentheses();
        QueryCondition condm = new QueryCondition("nodeDepartment",
                                                  QueryCondition.EQUAL,
                                                  (String) mDepartments.
                                                  elementAt(0));

        	
        query.addCondition(mNodeCount, condm);
       
        query.addRightParentheses();

        query.addAND();
        QueryCondition condm1 = new QueryCondition("routeType",
            QueryCondition.EQUAL,
            "MANUFACTUREROUTE");
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
    if (VERBOSE) {
      System.out.println("getRouteByPartAndDep ��ѯ����Ϊ�� " + query.getDebugSQL());
    }
   
    return pservice.findValueInfo(query);

  }//add end by guoxl on 2008-12-23(���ʵʩ���⣬���ڰ�·�ߵ�λ����)

  /**
   * �õ������㲿���ĸ������ϲ���ͬ��
   * @param subs Vector �Ӽ�����
   * @throws QMException
   * @return Vector  HashMap���ϣ�key:partBsoID,value:QMPartIfc
   * �ϲ���ĸ�������
   */
  private Vector getAllParentProduct(Vector subs) throws QMException {
    StandardPartService partService = (StandardPartService) EJBServiceHelper.
        getService(PART_SERVICE);
    HashMap subMap = new HashMap();
    QMPartIfc part;
    for (int i = 0; i < subs.size(); i++) {
      part = (QMPartIfc) subs.elementAt(i);
      Collection temparts = (Collection) partService.getParentProduct(part);
      QMPartIfc temppart;
      if (temparts == null || temparts.size() == 0) {
        if (VERBOSE) {
          System.out.println("����Ϊ0");
        }
        continue;
      }
      Iterator ite1 = temparts.iterator();
      while (ite1.hasNext()) {
        temppart = (QMPartIfc) ite1.next();
        if (!subMap.containsKey(temppart.getBsoID())) {
          subMap.put(temppart.getBsoID(), temppart);
        }
      }

    }
    return new Vector(subMap.values());
  }
private Vector getAllParentProductOnce(Vector subParts) throws QMException {
  StandardPartService partService = (StandardPartService) EJBServiceHelper.
        getService(PART_SERVICE);
  Collection partColl = (Collection) partService.getParentProduct( subParts);
  HashMap subMap = new HashMap();
  Iterator ite1 = partColl.iterator();
     while (ite1.hasNext()) {
       QMPartIfc temppart = (QMPartIfc) ite1.next();
       if (!subMap.containsKey(temppart.getBsoID())) {
         subMap.put(temppart.getBsoID(), temppart);
       }
     }

  return new Vector(subMap.values());
}
  /**
   * �õ������Ӽ����ϲ���ͬ��
   * @param products Vector ��Ʒ����
   * @throws QMException
   * @return Vector �ϲ�����Ӽ�����
   */
  private Vector getAllSubParts(Vector products) throws QMException {
    StandardPartService partService = (StandardPartService) EJBServiceHelper.
        getService(PART_SERVICE);
    EnterprisePartService enterprisePartService = (
        EnterprisePartService)
        EJBServiceHelper.getService(
        "EnterprisePartService");
    PartConfigSpecIfc configSpecIfc = partService.findPartConfigSpecIfc();
    // HashMap subMap = new HashMap();
    Vector result = new Vector();
    QMPartIfc part;
    for (int i = 0; i < products.size(); i++) {
      part = (QMPartIfc) products.elementAt(i);
      QMPartIfc[] temparts = (QMPartIfc[]) enterprisePartService.
          getAllSubPartsByConfigSpec( (QMPartMasterIfc) part.getMaster(),
                                     configSpecIfc);
      QMPartIfc temppart;
      if (temparts == null || temparts.length == 0) {
        continue;
      }
      for (int j = 0; j < temparts.length; j++) {
        temppart = (QMPartIfc) temparts[j];
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
  private void addToVector(Vector vec, QMPartIfc part) {
    if (vec.size() == 0) {
      vec.add(part);
      return;
    }
    else {
      for (int i = 0; i < vec.size(); i++) {
        QMPartIfc exitPart = (QMPartIfc) vec.elementAt(i);
        if (part.getBsoID().equals(exitPart.getBsoID())) {
          return;
        }
        else
        if (part.getPartNumber().compareTo(exitPart.getPartNumber()) < 0) {
          vec.add(i, part);
          return;
        }
      }
    }
    vec.add(vec.size(), part);

  }


  /**
   * �����㲿�������Ƿ���·��
   * @param vec Vector ���ֵ���󼯺�
   * @throws QMException
   * @return Vector[] ���������vector:<br>
   * 1.successVec vector:��ŵ���QMPartIfc ���ֵ���� <br>
   * 2.failVec    vector::��ŵ���QMPartIfc ���ֵ���� <br>
   * ��·�߻���·������ļ���
   */
  public Vector[] isHasRoute(Vector vec) throws
      QMException {
    Vector successVec = new Vector();
    Vector failVec = new Vector();
    QMPartIfc part;
    for (int j = 0; j < vec.size(); j++) {
      part = (QMPartIfc) vec.elementAt(j);
      if (!isHasRoute(part.getMasterBsoID())) {
        failVec.add(part);
      }
      else {
        successVec.add(part);
      }
    }
    return new Vector[] {
        successVec, failVec};
  }

  /**
   * �����㲿�������Ƿ���·��
   * @param partMasterID String ���ID
   * @throws QMException
   * @return boolean ��·�߷���true ���򷵻�false
   */
  public boolean isHasRoute(String partMasterID) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getPartLevelRoutes, partMasterID = " +
                         partMasterID);
    }
    if (partMasterID == null || partMasterID.trim().length() == 0) {
      throw new TechnicsRouteException("�����������");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
    QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(0, cond3);
    query.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(0, cond4);
    query.addAND();
    QueryCondition cond5 = new QueryCondition("routeID",
                                              QueryCondition.NOT_NULL);
    query.addCondition(0, cond5);
    query.addAND();
    QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
    query.addCondition(0, j, cond6);
    //SQL������������
    query.setDisticnt(true);
    //����ListRoutePartLinkIfc
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's getPartLevelRoutes SQL = " +
                         query.getDebugSQL());
    }
    Collection coll = pservice.findValueInfo(query);
    if (coll != null && coll.size() > 0) {
      return true;
    }
    else {
      return false;
    }

  }

  /**
   * ��ȡ���㲿���ڸ��㲿���е�ʹ��������
   * @param parentPartIfc QMPartIfc ���㲿����
   * @param childPartIfc QMPartIfc ���㲿����
   * @throws QMException
   * @return String ʹ��������
   * @see QMPartInfo
   */
  private String getPartQuantity(QMPartIfc parentPartIfc, QMPartIfc childPartIfc,List childParts)
          throws QMException
  {
      if(parentPartIfc.getPartNumber().equals(childPartIfc.getPartNumber()))
          return "1";
      //��ȡ���㲿��ͳ�Ʊ�
      Object[] childPartsArray = childParts.toArray();
      //��ȡָ�����㲿����ʹ��������
      for (int i = 0; i < childPartsArray.length; i++)
      {
          if (childPartsArray[i] instanceof Object[])
          {
              Object[] childPart = (Object[]) childPartsArray[i];
                  if (childPart[0].equals(childPartIfc.getBsoID()))
                      return childPart[3].toString();
          }
      }
      return null;
  }


  /**
   * ��ȡ���㲿���ڸ��㲿���е�ʹ��������
   * @param parentPartIfc QMPartIfc ���㲿����
   * @param middlePartMasterIfc QMPartMasterIfc �м��㲿��������Ϣ��
   * @param childPartIfc QMPartIfc ���㲿����
   * @throws QMException
   * @return String ʹ��������
   */
  private String getPartQuantity(QMPartIfc parentPartIfc,
                                QMPartMasterIfc middlePartMasterIfc,
                                QMPartIfc childPartIfc,List childParts,List midleList)
          throws QMException
  {
     StandardPartService partService = (StandardPartService) EJBServiceHelper.
        getService(PART_SERVICE);
      //��ȡ��ǰ�û������ù淶��
      PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)
                                           partService.findPartConfigSpecIfc();
      String middleQuantity = null;
      QMPartIfc middlePartIfc = null;
      if(parentPartIfc.getPartNumber().equals(middlePartMasterIfc.getPartNumber()))
      {
          middleQuantity = "1";
          middlePartIfc = parentPartIfc;
      }
      else
      {
          Object[] childPartsArray = childParts.toArray();

          //��ȡ�м��㲿����ʹ��������
          for (int i = 0; i < childPartsArray.length; i++)
          {
              if (childPartsArray[i] instanceof Object[])
              {
                  Object[] childPart = (Object[]) childPartsArray[i];
                      if (childPart[1].equals(middlePartMasterIfc.
                                              getPartNumber()))
                          middleQuantity = childPart[3].toString();
              }
          }
          if (middleQuantity == null || middleQuantity.equals(""))
              return null;
          middlePartIfc = getPartByConfigSpec(middlePartMasterIfc, partConfigSpecIfc);
      }
      String quantity = getPartQuantity(middlePartIfc, childPartIfc,midleList);
      if (quantity == null || quantity.equals(""))
          return null;
      float middleQuantity2 = Float.valueOf(middleQuantity).floatValue();
      float quantity2 = Float.valueOf(quantity).floatValue();
      String tempQuantity = String.valueOf(middleQuantity2 * quantity2);
      if (tempQuantity.endsWith(".0"))
          tempQuantity = tempQuantity.substring(0,
                                                tempQuantity.length() - 2);
      return tempQuantity;
  }

  /**
     * ��ȡ�������ù淶���㲿����
     * @param partMasterIfc QMPartMasterIfc �㲿������Ϣ��
     * @param partConfigSpecIfc PartConfigSpecIfc ���ù淶��
     * @throws QMException
     * @return QMPartIfc
     */
    private QMPartIfc getPartByConfigSpec(QMPartMasterIfc partMasterIfc,
                         PartConfigSpecIfc partConfigSpecIfc)
            throws QMException
    {
        Collection collection = new ArrayList();
        collection.add(partMasterIfc);
        Collection collection2 = filteredIterationsOf(collection,
                partConfigSpecIfc);
        Iterator iterator = collection2.iterator();
        Object[] obj2 = null;
        while (iterator.hasNext())
        {
            Object obj1 = iterator.next();
            if (obj1 instanceof Object[])
            {
                obj2 = (Object[]) obj1;
            }
        }
        if(obj2 == null || obj2.length == 0)
            return null;
        if(!(obj2[0] instanceof QMPartIfc))
            return null;
        return (QMPartIfc)obj2[0];
    }

 /**
  *  �õ�ǰ�û������ù淶�����㲿��
  * @param masterCol Collection
  * @throws QMException
  * @return Collection  �����е����Ϳɲμ� ConfigService ������<br>
  * filteredIterationsOf(Collection collection, ConfigSpec configSpec) ����.<br>
  * ���˺�����ļ���
  */
 public Collection filteredIterationsOfByDefault(Collection masterCol) throws
     QMException {
   StandardPartService partService = (StandardPartService) EJBServiceHelper.
       getService(PART_SERVICE);
   PartConfigSpecIfc configSpecIfc = partService.findPartConfigSpecIfc();
   Collection col;

   col = (Collection) partService.filteredIterationsOf(masterCol, configSpecIfc);

   return col;
 }

    /**
     * �ж��Ƿ��Ǹ���
     * @param maybeChild Vector ���ֵ���󼯺�
     * @param maybeParent QMPartIfc
     * @throws QMException
     * @return Collection vector���ϣ�<br>
     * QMPartIfc�Ǹ������������
     * @see QMPartInfo
     */
    //(�����)������� zz ��Ӽ�����÷��������ٿͻ��˵��ô����������
    public Collection isParentPart (Vector maybeChild, QMPartIfc maybeParent) throws QMException{
  if( maybeChild != null){
    for(int i =0;i< maybeChild.size();i++ ){
      QMPartIfc onePart = (QMPartIfc)maybeChild.elementAt(i);
     boolean be =  isParentPartnotinSubTable(onePart, maybeParent);
     maybeChild.remove(i);
     maybeChild.add(i,new Boolean(be));

    }
    return maybeChild;
  }
  else return null;
}
private boolean isParentPartnotinSubTable(QMPartIfc partIfc1, QMPartIfc partIfc2)throws QMException{
  StandardPartService partService = (StandardPartService) EJBServiceHelper.
        getService(PART_SERVICE);
       QMPartMasterIfc partMasterIfc1 = (QMPartMasterIfc)partIfc1.getMaster();
       QMPartMasterIfc partMasterIfc2 = (QMPartMasterIfc)partIfc2.getMaster();
       if (partMasterIfc1.getBsoID().equals(partMasterIfc2.getBsoID()))
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
       for(int i=0; i<temp.size(); i++)
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
     private Vector getAllParentParts(QMPartIfc partIfc) throws QMException
  {
   //  System.out.println("getAllParentParts========"+partIfc.getMasterBsoID());
      Vector tempresult = getParentParts(partIfc);
      Vector result = new Vector();
      if(tempresult != null)
      {
          for (int i=0; i<tempresult.size(); i++)
          {
              QMPartMasterIfc partMasterIfc1 = (QMPartMasterIfc)((QMPartIfc)tempresult.elementAt(i)).getMaster();
              if(partMasterIfc1 != null)
                  result.addElement(partMasterIfc1);
              Vector temp = getAllParentParts((QMPartIfc)tempresult.elementAt(i));
              for (int j = 0; j<temp.size(); j++)
              {
                  result.addElement(temp.elementAt(j));
              }
          }
      }

      return result;
  }

  private Vector getParentParts(QMPartIfc partIfc) throws QMException
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
               if (obj instanceof QMPartIfc)
               {
                   //�ڲ鿴�����ڽ��棬���һ������ʹ��һ���Ӽ���Σ���ֻ���г�һ����¼������ÿ�ζ��г�
                   QMPartIfc partIfc2 = (QMPartIfc)obj;
                   String string = partIfc2.getPartNumber() + partIfc2.getVersionValue();
                   if(!vector.contains(string))
                   {
                     vector.addElement(string);
                     result.addElement( partIfc2 );
                   }
               }
           }

           return result;
       }
       else
       {

           return null;
       }
   }
   /**
     * ����ָ����QMPartMasterIfc����
     * ͨ�����������ϲ�ѯ�����PartUsageLink������QMPartMasterIfc���������°汾
     * ��QMPartIfc����ļ��ϡ�
     * @param partMasterIfc :QMPartMasterIfc����
     * @return collection ��partMasterIfc��PartUsageLink���й��������°汾QMPartIfc����ļ��ϡ�
     * @throws QMException
     */
    private Collection getUsedByParts(QMPartMasterIfc partMasterIfc) throws QMException
    {

        QMQuery query = new QMQuery("QMPart", "PartUsageLink");
        //�����°汾��ѯ����������cond
        QueryCondition condition1 = VersionControlHelper.getCondForLatest(true);
        //���ݴ������ֵȷ�����λ�ã���������Ӳ�ѯ����,����0��ʾ�ǵ�һ����:�ǶԵ�һ������Ӳ�ѯ����
        query.addCondition(0,condition1);
         query.setChildQuery(false);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();

        //����һ��ֵ����partMasterIfc�ͱ�ֵ�����ڹ������еĹ�����ɫ��"uses", �����������һ�ߵ�bso����
        //query - ��ѯ�Ĺ�������
        Collection outcoll = (Collection)pservice.navigateValueInfo(partMasterIfc, "uses", query);
       // System.out.println("getUsedByParts dejieguo outcoll " + outcoll.size()+"partMasterIfc=="+partMasterIfc.getBsoID());
        return outcoll;
    }

    /**
     * ����·�ߵ�������
     * ·�߱�ֻTechnicsRouteListMaster�����ƺͱ��,TechnicsRouteListҲ�����ƺͱ��
     * @param routelist TechnicsRouteListIfc ·�߱�ֵ����
     * @throws QMException
     * @return TechnicsRouteListMasterIfc ·�߱�ֵ����
     * @see TechnicsRouteListInfo
     */
//    ������ʮ�����ӹ���·�ߵ����������� ������� zz 20061214
//     flag �Ƿ��޸�·�߱���
    public TechnicsRouteListMasterIfc rename(TechnicsRouteListIfc routelist)throws QMException
    {
        if (routelist == null)
        {
            return null; //�����������Ϊ�շ��ؿ�
        }
        PersistService pservice = null;
         pservice = (PersistService) EJBServiceHelper.getService(
                 "PersistService");
   TechnicsRouteListMasterIfc      listMaster = (TechnicsRouteListMasterIfc) pservice.refreshInfo(routelist.getMasterBsoID()); //ˢ��ֵ����

          listMaster.setRouteListNumber(routelist.getRouteListNumber());


        listMaster.setRouteListName(routelist.getRouteListName());
        
        //CCBegin by liunan 2012-04-25 �򲹶�v4r3_p044
        //CR8 Begin
          EnterpriseService enterpriseService = (EnterpriseService) EJBServiceHelper.getService("EnterpriseService");
          boolean isAccess=enterpriseService.hasRenameAccess(listMaster);
          if(isAccess)
          {
          //CCEnd by liunan 2012-04-25
          	
		QMQuery query1 = new QMQuery("TechnicsRouteListMaster");//CR4
		
		QueryCondition condition1 = new QueryCondition("routeListNumber",
		        "=", routelist.getRouteListNumber());
		query1.addCondition(condition1);//CR5
		//CCBeginby leixiao 2010-11-30 ���������ı��ʱ,�޷�������
		query1.addAND();
		QueryCondition condition2 = new QueryCondition("bsoID",
		        "<>", routelist.getMasterBsoID());
		query1.addCondition(condition2);
		//CCEndby leixiao 2010-11-30 		
		boolean isHas = pservice.isHasResult(query1);
		
		if (isHas)
		{
			Object[] obj =
			{ routelist.getRouteListName(), routelist.getRouteListNumber() };

			throw new TechnicsRouteException("3", obj);
		}                                                       //CR4
	
	//CCBegin by liunan 2012-04-25 �򲹶�v4r3_p044
          }
          else
          {
              throw new QMException("��ǰ�û��Ըö���û��������Ȩ�ޣ�");
          }
          //CR8 end
          //CCEnd by liunan 2012-04-25
          
    try {
      listMaster = (TechnicsRouteListMasterIfc) pservice.updateValueInfo(
          listMaster);
    }
    catch (Exception ex) {
      if (ex instanceof SQLException) {
          //�ж�Ψһ�ԡ�
          Object[] obj = {
              routelist.getRouteListName(),
              routelist.getRouteListNumber()};
          throw new TechnicsRouteException("3", obj);
        }
        else {
                 this.setRollbackOnly();
                 throw new TechnicsRouteException(ex);
               }

    }
    QMQuery query = new QMQuery(ROUTELIST_BSONAME);
      QueryCondition condition = new QueryCondition("masterBsoID",
                                                QueryCondition.EQUAL, listMaster.getBsoID());
      query.addCondition(condition);
       Collection coll = pservice.findValueInfo(query);
       if(coll!=null&&coll.size()>0){
          try {
         for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
        TechnicsRouteListIfc routelisti = (TechnicsRouteListIfc)iter.next();

        routelisti.setRouteListNumber(routelist.getRouteListNumber());
           routelisti.setRouteListName(routelist.getRouteListName());

      routelisti = (TechnicsRouteListIfc) pservice.updateValueInfo(
          //CCBegin SS25
          //routelisti);
          routelisti, false);
          //CCEnd SS25
    }

       }
       catch(Exception ex){
         if (ex instanceof SQLException) {
            //�ж�Ψһ�ԡ�
            Object[] obj = {
                routelist.getRouteListName(),
                routelist.getRouteListNumber()};
            throw new TechnicsRouteException("3", obj);
          }
          else {
                   this.setRollbackOnly();
                   throw new TechnicsRouteException(ex);
                 }

           }

       }

      return listMaster;
    }

    /**
     * רΪ���������൥����
     * @param part QMPartIfc ���ֵ����
     * @throws QMException
     * @return String
     * @see QMPartInfo
     */
    //  * lilei add 2006-7-11
    public String getMaterialRoute(QMPartIfc part) throws QMException
    {
    PersistService pService=(PersistService)EJBServiceHelper.getService("PersistService");
    ListRoutePartLinkInfo info = null;
  //  TechnicsRouteListInfo routelist = null;
  //  TechnicsRouteIfc routeIfc = null;//zz
    String routeString = "";//zz
    QMQuery query=new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition qc=new  QueryCondition("rightBsoID","=",part.getMasterBsoID());
    query.addCondition(qc);
    //QueryCondition qc1=new QueryCondition("adoptStatus","=","adopt");
    QueryCondition qc1 = new QueryCondition("adoptStatus",
                                                QueryCondition.EQUAL,
                                                RouteAdoptedType.ADOPT.toString());

    query.addAND();
    query.addCondition(qc1);
    Collection cols=null;

    try{
      cols = pService.findValueInfo(query, false);
      if(cols!=null){
          // System.out.println("��ѯ���Ĺ������� " + cols.size());
      }

    }catch(Exception e)
    {
      e.printStackTrace();
    }
    Iterator ite=cols.iterator();
    if(ite.hasNext())
    {
      info = (ListRoutePartLinkInfo) ite.next();


    }
    if (info != null){
//      routelist = (TechnicsRouteListInfo) pService.refreshInfo(info.
//          getLeftBsoID());
//      routeIfc = (TechnicsRouteIfc) pService.refreshInfo(info.getRouteID());
      if(info.getRouteID()!= null ){
          Map  map = getDirectRouteBranchStrs (info.getRouteID());
           if(!map.isEmpty()){
          Iterator values = map.values().iterator();
           routeString = (String) values.next() ;
          while (values.hasNext())
          {

            String  routeStr =  (String) values.next();
           routeString = routeString  +";"+ routeStr;
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
         * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
         * @throws QMException
         * @return Vector ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
         * 0����ǰpart��BsoID��
         * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
         * 2-...���ɱ�ģ����û�ж������ԣ�2����ǰpart�ı�ţ�3����ǰpart������
         *                              4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
         *                              5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
         *               ������������ԣ��������ж��Ƶ����Լӵ���������С�
         * �����������˵ݹ鷽����
         * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
         * PartUsageLinkIfc partLinkIfc, int parentQuantity);
         * @see  QMPartInfo
         * @see  PartConfigSpecInfo
         */

        public Vector setMaterialList(QMPartIfc partIfc, String[] attrNames,
                                      String[] affixAttrNames,
                                      PartConfigSpecIfc configSpecIfc)
            throws QMException
        {
          System.out.println("������� setMaterialList");
           Vector vector = null;
          try{
//            PartDebug.trace(this, PartDebug.PART_SERVICE,
//                            "setMaterialList begin ....");
            PersistService pService = (PersistService) EJBServiceHelper.getService(
                "PersistService");
            int level = 0;
            PartUsageLinkIfc partLinkIfc = new PartUsageLinkInfo();
            float parentQuantity = 1.0f;

            //��¼�����ͱ�������������ڵ�λ�á�
            int quantitySite = 0;
            int numberSite = 0;
            for (int i = 0; i < attrNames.length; i++) {
              String attr = attrNames[i];
              attr = attr.trim();
              if (attr != null && attr.length() > 0) {
                if (attr.equals("quantity")) {
                  quantitySite = 3 + i;
                }
                if (attr.equals("partNumber")) {
                  numberSite = 3 + i;
                }
              }
            }

            vector = fenji(partIfc, attrNames, affixAttrNames, configSpecIfc,
                           level, partLinkIfc, parentQuantity);
//            PartDebug.trace(this, PartDebug.PART_SERVICE,
//                            "setMaterialList end....return is Vector");
            //�ѽ�������еĵ�һ��Ԫ�ص�ʹ�õ��������""
            if (vector != null && vector.size() > 0) {
              Object[] first = (Object[]) vector.elementAt(0);

              //����������������������������Ϊ�ա�
              if (quantitySite > 0) {
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
            boolean affixAttrNullTrueFlag = affixAttrNames == null ||
                affixAttrNames.length == 0;
            if (attrNullTrueFlag) {
              if (affixAttrNullTrueFlag) {
      //                ssss = QMMessage.getLocalizedMessage(RESOURCE, "versionValue", null);
      //                firstElement.addElement(ssss);
      //                ssss = QMMessage.getLocalizedMessage(RESOURCE, "viewName", null);
      //                firstElement.addElement(ssss);
              }
            }
            else {
              for (int i = 0; i < attrNames.length; i++) {
                String attr = attrNames[i];
                ssss = QMMessage.getLocalizedMessage(PARTRESOURCE, attr, null);
                firstElement.addElement(ssss);
              }
            }
            //�����firstElement�е����е�Ԫ����װ��ϣ�������Ҫ��firstElement ->Object[]
            //����ӵ�vector�еĵ�һ��λ�ã�
            Object[] tempArray = new Object[firstElement.size()];
            for (int i = 0; i < firstElement.size(); i++) {
              tempArray[i] = firstElement.elementAt(i);
            }
            vector.insertElementAt(tempArray, 0);
            //2003.09.12Ϊ�˷�ֹ"null"���뵽����ֵ�У����Զ�vector�е�ÿ��Ԫ���ж�
            //���Ƿ�Ϊnull, �����null����ת��Ϊ""
            for (int i = 0; i < vector.size(); i++) {
              Object[] temp = (Object[]) vector.elementAt(i);
              for (int j = 0; j < temp.length; j++) {
                if (temp[j] == null) {
                  temp[j] = "";
                }
              }
            }
          }catch(Exception e){
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
           private String getAlternates(QMPartIfc part)
           throws QMException
           {
                   String alternates="";

                   ExtendedPartService pservice = (ExtendedPartService)EJBServiceHelper.getService("ExtendedPartService");
                   Collection altes=pservice.getAlternatesPartMasters((QMPartMasterIfc)part.getMaster());
                   Iterator ite=altes.iterator();
                   for(;ite.hasNext();)
                   {
                           QMPartMasterIfc master=(QMPartMasterIfc)ite.next();
                       if(alternates.length()==0)
                       {
                               alternates=master.getPartNumber()+"("+master.getPartName()+")";
                       }
                       else
                               alternates=alternates+";"+master.getPartNumber()+"("+master.getPartName()+")";
                   }
                   return alternates;
           }

           /**
    * ��ȡpart�Ľṹ�滻���ı�ţ����ƣ����ִ���ʽ����
    * @param part �㲿����
    * @return �ṹ�����ı�ţ����ƣ�
    */
   private String getSubstitutes(PartUsageLinkIfc usageLinkIfc)
   throws QMException
   {
           String substitutes="";
           if(usageLinkIfc==null)
                   return substitutes;
           if(!PersistHelper.isPersistent(usageLinkIfc))
                   return substitutes;
           System.out.println("aaaaaaaaaaa the usagelink is "+usageLinkIfc.getBsoID());
           ExtendedPartService pservice = (ExtendedPartService)EJBServiceHelper.getService("ExtendedPartService");
           Collection subst=pservice.getSubstitutesPartMasters(usageLinkIfc);
           Iterator ite=subst.iterator();
           for(;ite.hasNext();)
           {
                   QMPartMasterIfc master=(QMPartMasterIfc)ite.next();
               if(substitutes.length()==0)
               {
                       substitutes=master.getPartNumber()+"("+master.getPartName()+")";
               }
               else
                       substitutes=substitutes+";"+master.getPartNumber()+"("+master.getPartName()+")";
           }
           return substitutes;
   }

            // add by guoxl end

        /**
         * ˽�з�������setMaterialList()�������ã�ʵ�ֶ��Ʒּ������嵥�Ĺ��ܡ�
         * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
         * 0����ǰpart��BsoID��
         * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
         * 2-...���ɱ�ģ����û�ж������ԣ�2����ǰpart�ı�ţ�3����ǰpart������
         *                              4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
         *                              5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
         *               ������������ԣ��������ж��Ƶ����Լӵ���������С�
         * @param partIfc :QMPartIfc ��ǰ�Ĳ�����
         * @param attrNames :String[] ���Ƶ����Լ��ϣ�����Ϊ�ա�
         * @param affixAttrNames :String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
         * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
         * @param level :int ��ǰpart���ڵļ���
         * @param partLinkIfc :PartUsageLinkIfc ��¼�˵�ǰpart�����׽ڵ��ʹ�ù�ϵ��ֵ����
         * @param parentQuantity :int ��ǰpart�ĸ��׽ڵ㱻�������ʹ�õ�������
         * @throws QMException
         * @return Vector
         */

        private Vector fenji(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
            PartConfigSpecIfc configSpecIfc, int level, PartUsageLinkIfc partLinkIfc,
            float parentQuantity) throws QMException
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
                }
                else
                {
                    //���ֻ�ж��Ƶ���չ���Բ�Ϊ�յ�ʱ��
                    tempArray = new Object[3 + affixAttrNames.length];
                }
            }
            else
            {
                if(affixAttrNullTrueFlag)
                {
                    //���ֻ�ж��Ƶ���ͨ���Բ�Ϊ�յ�ʱ��
                    tempArray = new Object[3 + attrNames.length];
                }
                else
                {
                    //����������Ƶ����Լ��϶���Ϊ�յ�ʱ��
                    tempArray = new Object[3 + affixAttrNames.length + attrNames.length];
                }
            }
            //end if and else (attrNames == null || attrNames.length == 0)
            tempArray[0] = partIfc.getBsoID();
            int numberSite = 0;
            for (int i=0; i<attrNames.length; i++)
            {
                String attr = attrNames[i];
                attr = attr.trim();
                if (attr != null && attr.length() > 0)
                {
                    if (attr.equals("partNumber"))
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
           /**if (level == 0)
            {
                parentQuantity = 1f;
                String quan = "1";
                tempArray[4] = new String(quan);
            }
            else
            {
                //�ɲ��������������ٱ���parentBsoID,���Ǳ���PartUsageLinkIfc����ѭ������������
                //��������ʡ���ٲ��ҵĹ��̡�QMPartUsageLinkIfc partLinkIfc
                parentQuantity = partLinkIfc.getQuantity();//parentQuantity*partLinkIfc.getQuantity();
                String tempQuantity = String.valueOf(parentQuantity);
                if (tempQuantity.endsWith(".0"))
                    tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                tempArray[4] = tempQuantity;
            }*/
            //�ж��Ƿ���Ҫ�������Խ��������
            if (attrNullTrueFlag)
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
                for (int j=0; j<attrNames.length; j++)
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
                            tempArray[3 + j]=getAlternates(partIfc);
                   }
                   //��������ǽṹ�滻��
                   else if(attr.equals("substitutes"))
                           {
                                   tempArray[3 + j] = getSubstitutes(partLinkIfc);
                           }
                      //add by guoxl end
                      else  if(attr.equals("defaultUnit")&&!temp.equals("0"))
                        {
                            Unit unit = partLinkIfc.getDefaultUnit();
                            if (unit != null)
                            {
                                tempArray[3 + j] = unit.getDisplay();
                            }
                            else
                            {
                                tempArray[3 + j] = "";
                            }
                        }
                        else if(attr.equals("quantity"))
                        {
                            //���level = 0 ˵��������Ĳ�����
                            if (level == 0)
                            {
                                parentQuantity = 1f;
                                String quan = "1";
                                tempArray[3 + j] = new String(quan);
                            }
                            else
                            {
                                //�ɲ��������������ٱ���parentBsoID,���Ǳ���PartUsageLinkIfc����ѭ������������
                                //��������ʡ���ٲ��ҵĹ��̡�QMPartUsageLinkIfc partLinkIfc
                                parentQuantity = partLinkIfc.getQuantity(); //parentQuantity*partLinkIfc.getQuantity();
                                String tempQuantity = String.valueOf(parentQuantity);
                                if (tempQuantity.endsWith(".0"))
                                    tempQuantity = tempQuantity.substring(0,
                                            tempQuantity.length() - 2);
                                tempArray[3 + j] = tempQuantity;
                            }
                        }
                        //zz start
                  else   if(attr.equals("routeList")){
                    System.out.println("  attr equales routelist ");
                      // tempArray[3+ attrNames.length-1] = "�صص���";
                    TechnicsRouteService technicsrouteservice = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
                     String routeString = technicsrouteservice.getMaterialRoute(partIfc);
                     tempArray[3+ j] = routeString;
                     }
                     //zz end

                        else
                        {
                            attr = (attr.substring(0, 1)).toUpperCase() +
                                attr.substring(1, attr.length());
                            attr = "get" + attr;
                            //���ڵ�attr����"getProducedBy"�ȹ̶����ַ����ˣ�
                            try
                            {
                                Class partClass = Class.forName(
                                    "com.faw_qm.part.model.QMPartInfo");
                                Method method1 = partClass.getMethod(attr, null);
                                Object obj = method1.invoke(partIfc, null);
                                //������Ҫ�ж�obj�Ƿ�Ϊnull, ���Ϊnull, attrNames[i] = "";
                                //���obj��Ϊnull, ������String, attrNames[i] = (String)obj;
                                //���obj��ö�����ͣ�attrNames[i] = (EnumerationType)obj.getDisplay();
                                if (obj == null)
                                {
                                    tempArray[3 + j] = "";
                                }
                                else
                                {
                                    if (obj instanceof String)
                                    {
                                        String tempString = (String) obj;
                                        if (tempString != null &&
                                            tempString.length() > 0)
                                        {
                                            tempArray[3 + j] = tempString;
                                        }
                                        else
                                        {
                                            tempArray[3 + j] = "";
                                        }
                                    }
                                    else
                                    {
                                        if (obj instanceof EnumeratedType)
                                        {
                                            EnumeratedType tempType = (
                                                EnumeratedType)
                                                obj;
                                            if (tempType != null)
                                            {
                                                tempArray[3 +
                                                    j] = tempType.getDisplay();
                                            }
                                            else
                                            {
                                                tempArray[3 + j] = "";
                                            }
                                        }
                                    }
                                }
                                //end if(obj == null)
                            }
                            catch (Exception ex)
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
            if ((collection == null)||(collection.size() == 0))
            {
                //PartDebug.trace(this, PartDebug.PART_SERVICE, "fenji end....return is Vector");
                return resultVector;
            }
            else
            {
                Object[] temp = (Object[])collection.toArray();
                level++;
                for (int k=0; k<temp.length; k++)
                {
                    if(temp[k] instanceof Object[])
                    {
                        Object[] obj = (Object[])temp[k];
                        //ȡtemp�е�Ԫ�ؽ���ѭ����temp[k][0]��PartUsageLinkIfc,
                        //temp[k][1]��QMPartIfc
                        Vector tempResult = new Vector();
                        if(obj[1] instanceof QMPartIfc && obj[0] instanceof PartUsageLinkIfc)
                        {
                            tempResult = fenji( (QMPartIfc) obj[1], attrNames,
                                                affixAttrNames,
                                               configSpecIfc, level,
                                               (PartUsageLinkIfc) obj[0],
                                               parentQuantity);
                        }
                        for (int m=0; m<tempResult.size(); m++)
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
            * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ��
            * ������������bianli()����ʵ�ֵݹ顣
            * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
            * 1��������������ԣ�
            * BsoID���ǣ��񣩿ɷ֣�"true","false"������š����ơ�������ת��Ϊ�ַ��ͣ����汾����ͼ��
            * 2������������ԣ�
            * BsoID���ǣ��񣩿ɷ֡������������ԡ�

            * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
            * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
            * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Լ��ϣ�����Ϊ�ա�
            * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
            * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
            * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õĶԵ�ǰ�㲿����ɸѡ������
            * @throws QMException
            * @return Vector ���Object[] :tempArray[i]  �㲿����ͳ�Ʊ���Ϣ��
            * @see QMPartInfo
            * @see PartConfigSpecInfo
            */
           public Vector setBOMList(QMPartIfc partIfc, String[] attrNames,
                                    String[] affixAttrNames, String source, String type,
                                    PartConfigSpecIfc configSpecIfc)
               throws QMException
           {
               PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
               Vector vector = new Vector();
               float parentQuantity = 1.0f;

               //��¼�����ͱ�������������ڵ�λ�á�
               int quantitySite = 0;
               int numberSite = 0;
               for (int i=0; i<attrNames.length; i++)
               {
                   String attr = attrNames[i];
                   attr = attr.trim();
                   if (attr != null && attr.length() > 0)
                   {
                       if (attr.equals("quantity"))
                       {
                           quantitySite = 3 + i;
                       }
                       if (attr.equals("partNumber"))
                       {
                           numberSite = 3 + i;
                       }

                   }
               }
               PartUsageLinkIfc partLinkIfc = new PartUsageLinkInfo();
               vector = bianli(partIfc, attrNames, affixAttrNames, source, type,
                               configSpecIfc, parentQuantity, partLinkIfc);
               //�����vector�е�Ԫ�ؽ��кϲ������Ĵ���...........
               Vector resultVector = new Vector();
               for (int i=0; i<vector.size(); i++)
               {
                   Object[] temp1 = (Object[])vector.elementAt(i);
                   //2003.09.12Ϊ�˷�ֹ"null"���뵽����ֵ�У����Զ�temp1�е�ÿ��Ԫ���ж�
                   //���Ƿ�Ϊnull, �����null����ת��Ϊ""
                   for(int j=0; j<temp1.length; j++)
                   {
                       if(temp1[j] == null)
                       {
                           temp1[j] = "";
                       }
                   }
                   //�����ǰ���partNumber���кϲ��ģ�����
                   String partNumber1 = (String)temp1[numberSite];
                   boolean flag = false;
                   for (int j=0; j<resultVector.size(); j++)
                   {
                       Object[] temp2 = (Object[])resultVector.elementAt(j);
                       String partNumber2 = (String)temp2[numberSite];
                       if (partNumber1.equals(partNumber2))
                       {
                           flag = true;

                           //���������λ�ô���0��˵���������������������Ȼ����ͬ�㲿��
                           //�������ϲ���
                           if(quantitySite>0)
                           {
                               //��temp2��temp1�е�Ԫ�ؽ��кϲ����ŵ�resultVector��ȥ��:::
                               float float1 = (new Float(temp1[quantitySite].toString())).
                                              floatValue();
                               float float2 = (new Float(temp2[quantitySite].toString())).
                                              floatValue();
                               String tempQuantity = String.valueOf(float1 + float2);
                               if (tempQuantity.endsWith(".0"))
                                   tempQuantity = tempQuantity.substring(0,
                                           tempQuantity.length() - 2);
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
               }
               else
               {
                   flag1 = true;
               }
               if(type != null && type.length() > 0)
               {
                   if(type.equals(type1))
                   {
                       flag2 = true;
                   }
               }
               else
               {
                   flag2 = true;
               }
               if(!flag1 || !flag2)
               {
                   resultVector.removeElementAt(0);
               }
               else
               {
                   //�ѵ�һ��Ԫ�ص������ĳ�""
                   Object[] firstObj = (Object[])resultVector.elementAt(0);

                   //����������������������������Ϊ�ա�
                   if(quantitySite>0)
                   {
                       firstObj[quantitySite] = "";
                   }
                   resultVector.setElementAt(firstObj, 0);
               }
               //����ű���������Ľ����
               Vector result = new Vector();
               //Ȼ�����ﻹ��Ҫ�����ķ���ֵ���ϰ��յ�ǰ��source��type���й��ˣ�
               for(int i=0; i<resultVector.size(); i++)
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
                   }
                   else
                   {
                       flag11 = true;
                   }
                   if(type != null && type.length() > 0)
                   {
                       if(onePart.getPartType().toString().equals(type))
                       {
                           flag22 = true;
                       }
                   }
                   else
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
                   for(int i=0; i<attrNames.length; i++)
                   {
                       String attr = attrNames[i];
                       ssss = QMMessage.getLocalizedMessage(PARTRESOURCE, attr, null);
                       firstElement.addElement(ssss);
                   }
               }
               //�����firstElement�е����е�Ԫ����װ��ϣ�������Ҫ��firstElement ->Object[]
               //����ӵ�vector�еĵ�һ��λ�ã�
               Object[] tempArray = new Object[firstElement.size()];
               for(int i=0; i<firstElement.size(); i++)
               {
                   tempArray[i] = firstElement.elementAt(i);
               }
               result.insertElementAt(tempArray, 0);
               return result;
           }

           /**
            * ��������setBOMList�����ã�ʵ�ֵݹ���õĹ��ܡ�
            * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
            * 1��������������ԣ�
            * BsoID���ǣ��񣩿ɷ֣�"true","false"������š����ơ�������ת��Ϊ�ַ��ͣ����汾����ͼ��
            * 2������������ԣ�
            * BsoID���ǣ��񣩿ɷ֡������������ԡ�

            * @param partIfc :QMPartIfc ָ�����㲿����ֵ����
            * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
            * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Եļ��ϣ�����Ϊ�ա�
            * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
            * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
            * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õ�ɸѡ������
            * @param parentQuantity :float ʹ���˵�ǰ�����Ĳ��������������ʹ�õ�������
            * @param partLinkIfc :PartUsageLinkIfc ���ӵ�ǰ�������丸�����Ĺ�����ϵֵ����
            * @throws QMException
            * @return Vector
            */
           private Vector bianli(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
                                String source, String type, PartConfigSpecIfc configSpecIfc,
                                float parentQuantity,PartUsageLinkIfc partLinkIfc)
              throws QMException
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
                  for (int i=0; i<resultArray.length; i++)
                  {
                      boolean isHasSubParts = true; //false
                      Object obj = resultArray[i];
                      if(obj instanceof Object[])
                      {
                          Object[] obj1 = (Object[])obj;
                          if (obj1[1] instanceof QMPartIfc)
                          {
                              //��һ���൱��������һ���Ե�ǰ�㲿�������ж����㲿���Ĺ�������.
                              if (isHasSubParts == true)
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
              }
              else
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
               for (int i=0; i<attrNames.length; i++)
               {
                   String attr = attrNames[i];
                   attr = attr.trim();
                   if (attr != null && attr.length() > 0)
                   {
                       if (attr.equals("partNumber"))
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
              if (attrNullTrueFlag)
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
                  for (int i=0; i<attrNames.length; i++)
                  {
                      String attr = attrNames[i];
                      attr = attr.trim();
                      if(attr != null && attr.length() > 0)
                      {
                          //modify by liun 2005.3.25 ��Ϊ�ӹ����еõ���λ
                          //modify by guoxl on 2008.03.21(part�������嵥�����õ��˴������޸�)
                          if (attr.equals("alternates")) {
                            tempArray[3 + i] = getAlternates(partIfc);
                          }
                          //��������ǽṹ�滻��
                          else if (attr.equals("substitutes")) {
                            tempArray[3 + i] = getSubstitutes(partLinkIfc);
                          }
                           else
                          //add by guoxl end
                         if(attr.equals("defaultUnit"))
                          {
                              Unit unit = partLinkIfc.getDefaultUnit();
                              if (unit != null)
                              {
                                  tempArray[3 + i] = unit.getDisplay();
                              }
                              else
                              {
                                  tempArray[3 + i] = "";
                              }
                          }
                          else if(attr.equals("quantity"))
                          {
                              //��Ҫ���ж�partLinkIfc�Ƿ��ǳ־û��ģ�������ǣ�parentQuantity = 1.0
                              //�����:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
                              if (partLinkIfc == null ||
                                  !PersistHelper.isPersistent(partLinkIfc))
                              {
                                  parentQuantity = 1.0f;
                              }
                              else
                              {
                                  parentQuantity = parentQuantity *
                                                   partLinkIfc.getQuantity();
                              }
                              String tempQuantity = String.valueOf(parentQuantity);
                              if (tempQuantity.endsWith(".0"))
                                  tempQuantity = tempQuantity.substring(0,
                                          tempQuantity.length() - 2);
                              tempArray[3 + i] = tempQuantity;
                          }
                          //zz start  fff
                          else if(attr.equals("routeList"))
                        {

                          TechnicsRouteService technicsrouteservice = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
                       String routeString = technicsrouteservice.getMaterialRoute(partIfc);
                       tempArray[3+ i] = routeString;

                               }

                          //zz end
                          else
                          {
                              attr = (attr.substring(0, 1)).toUpperCase() +
                                  attr.substring(1, attr.length());
                              attr = "get" + attr;
                              //���ڵ�attr����"getProducedBy"�ȹ̶����ַ����ˣ�
                              try
                              {
                                  Class partClass = Class.forName(
                                      "com.faw_qm.part.model.QMPartInfo");
                                  Method method1 = partClass.getMethod(attr, null);
                                  Object obj = method1.invoke(partIfc, null);
                                  //������Ҫ�ж�obj�Ƿ�Ϊnull, ���Ϊnull, attrNames[i] = "";
                                  //���obj��Ϊnull, ������String, tempArray[i + 5] = (String)obj;
                                  //���obj��ö�����ͣ�tempArray[i + 5] = (EnumerationType)obj.getDisplay();
                                  if (obj == null)
                                  {
                                      tempArray[i + 3] = "";
                                  }
                                  else
                                  {
                                      if (obj instanceof String)
                                      {
                                          String tempString = (String) obj;
                                          if (tempString != null &&
                                              tempString.length() > 0)
                                          {
                                              tempArray[i + 3] = tempString;
                                          }
                                          else
                                          {
                                              tempArray[i + 3] = "";
                                          }
                                      }
                                      else
                                      {
                                          if (obj instanceof EnumeratedType)
                                          {
                                              EnumeratedType tempType = (
                                                  EnumeratedType)
                                                  obj;
                                              if (tempType != null)
                                              {
                                                  tempArray[i +
                                                      3] = tempType.getDisplay();
                                              }
                                              else
                                              {
                                                  tempArray[i + 3] = "";
                                              }
                                          }
                                      }
                                  }
                              }
                              catch (Exception ex)
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
              if (hegeVector != null && hegeVector.size() > 0)
              {
                  for (int j=0; j<hegeVector.size(); j++)
                  {
                      Object obj = hegeVector.elementAt(j);
                      if(obj instanceof Object[])
                      {
                          Object[] obj2 = (Object[])obj;
                          if ((obj2[0] != null)&&(obj2[1] != null))
                          {
                              Vector tempVector = bianli((QMPartIfc)obj2[1], attrNames,
                                  affixAttrNames, source, type, configSpecIfc,
                                  parentQuantity, (PartUsageLinkIfc)obj2[0]);
                              for (int k=0; k<tempVector.size(); k++)
                                  resultVector.addElement(tempVector.elementAt(k));
                          }
                      }
                  }
              }

              return resultVector;
          }

//         CCBegin by leixiao 2008-7-31 ԭ�򣺽����������·��,����"����֪ͨ�����" 
//liunan 2011-04-07 �ĳɸ��ݡ�����֪ͨ��š�ȡ�㲿���� s����ʾ��Ҳ�ɡ�����֪ͨ��š���Ϊ������֪ͨ��š�
           /**
            * ���ϵͳ���������ݱ�����ĵ��ż�����֪ͨ����������
            * @param noticeValue String
            * @return ArrayList
            * @throws QMException
            */

           //CCBegin by liunan 2012-05-22 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
           //public ArrayList getNoticeOrChangeRelatedParts(String noticeValue) throws
           public ArrayList getNoticeOrChangeRelatedParts(String noticeValue, boolean isManufacturing) throws
           //CCEnd by liunan 2012-05-22
           QMException {
    	    if (VERBOSE)
           System.out.println("---------------------TechnicsRouteServiceEJB getNoticeOrChangeRelatedParts() noticeValue="+noticeValue);
           if (noticeValue == null || noticeValue.length() < 1) {
             return new ArrayList();
           }
           PersistService service = (PersistService) EJBServiceHelper.
               getPersistService();
           ArrayList backList = new ArrayList();
           ArrayList templist = new ArrayList();
           Collection v1 = findDocByNumber(noticeValue);
          // System.out.println("------v1="+v1);
           for(Iterator it = v1.iterator();it.hasNext();)
           {
              DocInfo doc = (DocInfo)it.next();
             // System.out.println("---doc="+doc);
              Collection ps = PublishHelper.getPartAfterchange(doc.getBsoID());
              templist.addAll(ps);
              //CCBegin SS22
              Collection ps1 = PublishHelper.getPartCancelChange(doc.getBsoID());
              templist.addAll(ps1);
              //CCEnd SS22
              //CCBegin SS46
              Collection ps2 = PublishHelper.getJFShiZhiPart(doc.getBsoID());
              templist.addAll(ps2);
              //CCEnd SS46
           }
          // System.out.println("----templist="+templist);
           if(templist.size()!=0)
           {
             for(Iterator i=templist.iterator();i.hasNext();)
             {
               Object[] oo = (Object[])i.next();
               String partID1 = oo[0].toString();
               if (partID1.startsWith("QMPart_")) {
                 QMPartIfc part1 = (QMPartIfc) service.refreshInfo(partID1);
                 backList.add(part1);
               }
               else if (partID1.startsWith("GenericPart_")) {
                 GenericPartInfo gp1 = (GenericPartInfo) service.refreshInfo(partID1);
                 gp1.setLogicBase(null);
                 backList.add(gp1);
               }
             }
           }
           else
           {
        	//  System.out.println("-------------����֪ͨ��-");

             BaseValueIfc stringDefine = null;
             String stringDefineID = null;
             ArrayList temp = new ArrayList();//���ڴ����ID,�Թ����ظ����

             QMQuery query = new QMQuery("StringDefinition");
             //CCBegin by liunan 2011-04-07 ������������������������ڣ�һ���ǡ�����֪ͨ��š���һ���ǡ����ñ��-��š�
             //query.addCondition(new QueryCondition("displayName", "=",
                                                   //"����֪ͨ���"));
             query.addCondition(new QueryCondition("name", "=","AdoptionNumber"));                                      
             //CCEnd by liunan 2011-04-07
             
             Collection coll = service.findValueInfo(query);
             Iterator iterator = coll.iterator();

             if (iterator.hasNext()) {
               stringDefine = (BaseValueIfc) iterator.next();
               stringDefineID = stringDefine.getBsoID();
             }
             else {
               QMQuery query9 = new QMQuery("StringDefinition");
               query9.addCondition(new QueryCondition("displayName", "=",
                                                      "���á�����֪ͨ���"));
               Collection coll9 = service.findValueInfo(query9);
               Iterator iterator9 = coll9.iterator();

               if (iterator9.hasNext()) {
                 stringDefine = (BaseValueIfc) iterator9.next();
                 stringDefineID = stringDefine.getBsoID();
               }

             }
             //CCBegin by liunan 2011-04-07 ������������������������ڣ�һ���ǡ�����֪ͨ��š���һ���ǡ����ñ��-��š�
             //CCBegin by leixiao 2010-11-30 ���н����ĺͲ��÷ֿ�,���ӶԲ��úŵ�����
             /*BaseValueIfc adoptstringDefine = null;
             String adoptstringDefineID = null;
             QMQuery adoptquery = new QMQuery("StringDefinition");
             adoptquery.addCondition(new QueryCondition("name", "=",
                                                    "AdoptNumber_jf"));
           //  System.out.println("---------"+adoptquery.getDebugSQL());
             Collection collleix = service.findValueInfo(adoptquery);
             Iterator iteratorleix = collleix.iterator();
             if (iteratorleix.hasNext()) {
            	 adoptstringDefine = (BaseValueIfc) iteratorleix.next();
            	 adoptstringDefineID = adoptstringDefine.getBsoID();
             }*/
            // System.out.println(" stringDefineID="+stringDefineID+" adoptstringDefineID="+adoptstringDefineID);
             
             QMQuery query1 = new QMQuery("StringValue");
             //query1.addLeftParentheses();
             query1.addCondition(new QueryCondition("definitionBsoID", "=",
                                                    stringDefineID));
             //query1.addOR();
             //query1.addCondition(new QueryCondition("definitionBsoID", "=",
            		 //adoptstringDefineID));
             //query1.addRightParentheses();
             //CCEnd by leixiao 2010-11-30
             //CCEnd by liunan 2011-04-07 
             query1.addAND();
             noticeValue = noticeValue.replace('*', '%');
             query1.addCondition(new QueryCondition("value", "like",
                                                    noticeValue));
             Collection coll1 = service.findValueInfo(query1);
             //CCBegin SS4
             HashMap hm = new HashMap();
             //CCEnd SS4
             for (Iterator iterator2 = coll1.iterator(); iterator2.hasNext(); ) {
               StringValueInfo stringValue = (StringValueInfo) iterator2.next();
               String partID = stringValue.getIBAHolderBsoID();
             //  System.out.println("------partID:"+partID+"        stringValue="+stringValue+" backList="+backList);

               if (partID.startsWith("QMPart_")) {
                 QMPartIfc part = (QMPartIfc) service.refreshInfo(partID);  
               //  System.out.println("----"+backList.contains(part));               
                 if(part.getIterationIfLatest()&&!temp.contains(partID)) { 
                 	if(part.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("PREPARING"))||
                 	   part.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("ADVANCEPREPARE"))||
                 	   //CCBegin by liunan 2012-05-22 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
                 	   (part.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("MANUFACTURING"))&&isManufacturing)||
                 	   //CCEnd by liunan 2012-05-22
                 	   part.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("CANCELLED")))//anan
                 	   
                	 //CCBegin SS4
                 //backList.add(part);
                 //temp.add(partID);
                	 //backList.add(part);
                 {
                	 if(hm.containsKey(part.getMasterBsoID()))
                	 {
                		 QMPartIfc part1 = (QMPartIfc)hm.get(part.getMasterBsoID());
                		 //�Ƚϰ汾���µķŵ�����backList�С�
                		 if(getPublishFlag(part.getVersionValue(),part1.getVersionValue()))
                		 {
                    		 hm.put(part.getMasterBsoID(), part);
                    		 backList.remove(part1);
                    		 backList.add(part);
                    		 temp.add(partID);
                		 }
                	 }
                	 else
                	 {
                		 hm.put(part.getMasterBsoID(), part);
                		 backList.add(part);
                		 temp.add(partID);
                	 }                 
                 }
                 //CCEnd SS4
                 }
                 
               }
               else if (partID.startsWith("GenericPart_")) {
                 GenericPartInfo gp = (GenericPartInfo) service.refreshInfo(partID);
                // System.out.println("----"+backList.contains(gp));
                 gp.setLogicBase(null);
                 if(gp.getIterationIfLatest()&&!temp.contains(partID)){
                 	if(gp.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("PREPARING"))||
                 	   gp.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("ADVANCEPREPARE"))||
                 	   //CCBegin by liunan 2012-05-22 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
                 	   (gp.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("MANUFACTURING"))&&isManufacturing)||
                 	   //CCEnd by liunan 2012-05-22
                 	   gp.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("CANCELLED")))//anan
                 	   
                	 //CCBegin SS4
                 //backList.add(gp);
                 //temp.add(partID);
                 {
                	 if(hm.containsKey(gp.getMasterBsoID()))
                	 {
                		 GenericPartInfo gp1 = (GenericPartInfo)hm.get(gp.getMasterBsoID());
                		 //�Ƚϰ汾���µķŵ�����backList�С�
                		 if(getPublishFlag(gp.getVersionValue(),gp1.getVersionValue()))
                		 {
                    		 hm.put(gp.getMasterBsoID(), gp);
                    		 backList.remove(gp1);
                    		 backList.add(gp);
                    		 temp.add(partID);
                		 }
                	 }
                	 else
                	 {
                		 hm.put(gp.getMasterBsoID(), gp);
                		 backList.add(gp);
                		 temp.add(partID);
                	 }                 
                 }
                 //CCEnd SS4
                 }
                 }
             }

           }           

           templist=null;

           return backList;
       }
         //CCBegin by leixiao 2010-11-30 ԭ������"������������"  //liunan 2011-04-07 �ĳɸ��ݡ����ñ��-��š�ȡ�㲿����
           
           //CCBegin by liunan 2012-05-22 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
           //public ArrayList getPublishRelatedParts(String noticeValue) throws
           public ArrayList getPublishRelatedParts(String noticeValue, boolean isManufacturing) throws
           //CCEnd by liunan 2012-05-22
           QMException {
    	    if (VERBOSE)
           System.out.println("---------------------getPublishRelatedParts() noticeValue="+noticeValue);
           if (noticeValue == null || noticeValue.length() < 1) {
             return new ArrayList();
           }
           PersistService service = (PersistService) EJBServiceHelper.
               getPersistService();
           ArrayList backList = new ArrayList();
           ArrayList templist = new ArrayList();
             String stringDefineID = null;

             QMQuery query = new QMQuery("StringDefinition");
             //CCBegin by liunan 2011-04-07 ������������������������ڣ�һ���ǡ�����֪ͨ��š���һ���ǡ����ñ��-��š�
             //query.addCondition(new QueryCondition("displayName", "=",
                                                   //"�������"));
             query.addCondition(new QueryCondition("name", "=","AdoptNumber_jf"));
             //CCEnd by liunan 2011-04-07
             
             Collection coll = service.findValueInfo(query);
             Iterator iterator = coll.iterator();
             if (iterator.hasNext()) {
               stringDefineID = ((BaseValueIfc) iterator.next()).getBsoID();
             } 
          //   System.out.println(" stringDefineID="+stringDefineID);
             
             QMQuery query1 = new QMQuery("StringValue");
             query1.addCondition(new QueryCondition("definitionBsoID", "=",
                                                    stringDefineID));
             query1.addAND();
             noticeValue = noticeValue.replace('*', '%');
             query1.addCondition(new QueryCondition("value", "like",
                                                    noticeValue));
             Collection coll1 = service.findValueInfo(query1);
             //CCBegin SS4
             HashMap hm = new HashMap();
             //CCEnd SS4
             for (Iterator iterator2 = coll1.iterator(); iterator2.hasNext(); ) {
               StringValueInfo stringValue = (StringValueInfo) iterator2.next();
               String partID = stringValue.getIBAHolderBsoID();
           //    System.out.println("------partID:"+partID+"        stringValue="+stringValue);
               if (partID.startsWith("QMPart_")) {
                 QMPartIfc part = (QMPartIfc) service.refreshInfo(partID); 
                 //System.out.println("------partID:"+part.getLifeCycleState().getDisplay());
                 if(part.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("PREPARING"))||
                    part.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("ADVANCEPREPARE"))||
                 	   //CCBegin by liunan 2012-05-22 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
                 	   (part.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("MANUFACTURING"))&&isManufacturing)||
                 	   //CCEnd by liunan 2012-05-22
                    part.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("CANCELLED")))
                	 //CCBegin SS4
                	 //backList.add(part);
                 {
                	 if(hm.containsKey(part.getMasterBsoID()))
                	 {
                		 QMPartIfc part1 = (QMPartIfc)hm.get(part.getMasterBsoID());
                		 //�Ƚϰ汾���µķŵ�����backList�С�
                		 if(getPublishFlag(part.getVersionValue(),part1.getVersionValue()))
                		 {
                    		 hm.put(part.getMasterBsoID(), part);
                    		 backList.remove(part1);
                    		 backList.add(part);
                		 }
                	 }
                	 else
                	 {
                		 hm.put(part.getMasterBsoID(), part);
                		 backList.add(part);
                	 }                 
                 }
                 //CCEnd SS4
               }
               else if (partID.startsWith("GenericPart_")) {
                 GenericPartInfo gp = (GenericPartInfo) service.refreshInfo(partID);
                 gp.setLogicBase(null);   
                 if(gp.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("PREPARING"))||
                    gp.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("ADVANCEPREPARE"))||
                 	   //CCBegin by liunan 2012-05-22 ����ձ��Ƿ��������״̬�ĸ�ѡ����ѡ��������������������״̬�㲿����
                 	   (gp.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("MANUFACTURING"))&&isManufacturing)||
                 	   //CCEnd by liunan 2012-05-22
                    gp.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("CANCELLED")))

                	 //CCBegin SS4
                	 //backList.add(gp);
                 {
                	 if(hm.containsKey(gp.getMasterBsoID()))
                	 {
                		 GenericPartInfo gp1 = (GenericPartInfo)hm.get(gp.getMasterBsoID());
                		 //�Ƚϰ汾���µķŵ�����backList�С�
                		 if(getPublishFlag(gp.getVersionValue(),gp1.getVersionValue()))
                		 {
                    		 hm.put(gp.getMasterBsoID(), gp);
                    		 backList.remove(gp1);
                    		 backList.add(gp);
                		 }
                	 }
                	 else
                	 {
                		 hm.put(gp.getMasterBsoID(), gp);
                		 backList.add(gp);
                	 }                 
                 }
                 //CCEnd SS4                	 
               }
             }

           templist=null;

           return backList;
       }
         //CCEnd by leixiao 2010-11-30 ԭ������"������������"��ť 
           /**
            * ���ϵͳ�����������ĵ���Ż���ĵ�ֵ����
            * @param docNum String
            * @return Collection
            * @throws QMException
            */
           private Vector findDocByNumber(String  docNum) throws QMException
           {

             Vector vec = new Vector();

               //  �����µĲ��Ҷ���query
               QMQuery query = new QMQuery("DocMaster");
               docNum = docNum.replace('*', '%');
               QueryCondition cond = new QueryCondition("docNum", "like", docNum);
               query.addCondition(cond);
               //  ��ó־û�����
               PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
               Iterator iter = persistService.findValueInfo(query).iterator();
               while (iter.hasNext())
               {
                 DocMasterInfo docMasterInfo = ( (DocMasterInfo) iter.next());

                 //  �����µĲ��Ҷ���query
                 QMQuery query1 = new QMQuery("Doc");
                 QueryCondition cond1 = new QueryCondition("masterBsoID", "=",
                                                           docMasterInfo.getBsoID());
                 query1.addCondition(cond1);
                 Iterator iter1 = persistService.findValueInfo(query1).iterator();
                 while (iter1.hasNext())
                 {
                   DocInfo docInfo = (DocInfo) iter1.next();
                   if (VersionControlHelper.isLatestIteration(docInfo)) {
                     vec.addElement(docInfo);
                   }
                 }
               } //end while
             return vec;
           }


//         CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��,����"����֪ͨ�����"

//         CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��,����"��·�����"
           /**
            * ///20061009 liuming add
            * �����㲿����Ż��������δ���ƹ�·�ߵ��Ӽ�
            * @param productNumber �㲿�����
            * @return ���� Ԫ��ΪQMPartIfc
            */
           public Collection getSubPartsNoRoute(String productNumber) throws
               QMException
           {
             //long a = System.currentTimeMillis();
             Vector result = new Vector();
             PersistService pservice = (PersistService) EJBServiceHelper.
                 getPersistService();
             //{{{{{{{{{{{{{{{����������Ϣ
             QMQuery query = new QMQuery("QMPartMaster");
             QueryCondition condition1 = new QueryCondition("partNumber", "=",
                 productNumber);
             query.addCondition(condition1);
             Collection c1 = pservice.findValueInfo(query, false);
             if ( (c1 == null) || (c1.size() == 0)) {
               throw new QMException("�����Ų����ڣ����������룡");
             }
             StandardPartService partService = (StandardPartService)
                 EJBServiceHelper.
                 getService(PART_SERVICE);
             //��õ�ǰ�û������ù淶
             PartConfigSpecIfc configSpecIfc = partService.findPartConfigSpecIfc();
             ConfigService service = (ConfigService) EJBServiceHelper.getService(
                 "ConfigService");

             for (Iterator i = c1.iterator(); i.hasNext(); )
             {
               QMPartMasterIfc productMsater = (QMPartMasterIfc) i.next();
               //}}}}}}}}}}}
               //System.out.println(">>>>>>>>>>>>>>>>>> PartMaster = " +
                //                  productMsater.getBsoID());
               //{{{{{{�����������°汾
               Collection collection = service.filteredIterationsOf(productMsater,
                   new PartConfigSpecAssistant(configSpecIfc));
               if ( (collection == null) || (collection.size() > 1) ||
                   (collection.size() == 0))
              {
                 throw new TechnicsRouteException("�ڲ�����:û���ҵ���ǰ��������������°汾!");
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
               //}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}
               result.addAll(getSubPartsNoRoute(partIfc, configSpecIfc));
             }
             //long b = System.currentTimeMillis() - a;
            // System.out.println(">>>>>>>>>>>>>>>>>���£�"+b);
             return result;

           }

           /**
            * ///20061009 liuming add
            * ���ݵ�ǰɸѡ������ȡQMPartIfc�µ�����û�б��ƹ�·�ߵ��Ӳ�����
            * @param partIfc �㲿��ֵ����
            * @param configSpecIfc �㲿�����ù淶ֵ����
            * @return Vector ������ϣ������е�Ԫ����QMPartIfc
            * @throws QMException
            */
           private Vector getSubPartsNoRoute(QMPartIfc partIfc,
                                             PartConfigSpecIfc configSpecIfc) throws
               QMException
           {
             Vector resultVector = new Vector();
             resultVector.addElement(partIfc);
             Vector tempVector = new Vector();
             StandardPartService spService = (StandardPartService)
                 EJBServiceHelper.getService("StandardPartService");
             Collection collection = spService.getUsesPartIfcs(partIfc, configSpecIfc);
             if((collection == null)||(collection.size() == 0))
             {
                 return resultVector;
             }
             else
             {
                 Object[] tempArray = (Object[])collection.toArray();
                 //��collection�е�Ԫ�ؽ���ѭ�����ݹ���÷���getSubPartsNoRoute(),������ӵ�resultVector��
                 for(int i=0; i<tempArray.length; i++)
                 {
                     if(tempArray[i] instanceof Object[])
                     {
                         Object[] obj = (Object[])tempArray[i];
                         if (obj[1] instanceof QMPartIfc)
                         {
                             tempVector = getSubPartsNoRoute((QMPartIfc)obj[1], configSpecIfc);
                             for (int j=0; j<tempVector.size(); j++)
                                 resultVector.addElement(tempVector.elementAt(j));
                         }
                         //end if(tempArray)
                     }
                     //end if(tempArray[i] instanceof Object[])
                 }
                 //end for(int i)
             }
             //end if(collection) and else

             //��Ҫ�Խ�����Ͻ��кϲ����������ͬ��QMPartIfc�Ļ����ϲ���һ��:
             Vector result = new Vector();
             for(int i=0; i<resultVector.size(); i++)
             {
                 boolean flag1 = false;
                 QMPartIfc partIfc1 = (QMPartIfc)resultVector.elementAt(i);
                 String bsoID1 = partIfc1.getBsoID();
                 for(int j=0; j<result.size(); j++)
                 {
                     QMPartIfc partIfc2 = (QMPartIfc)result.elementAt(j);
                     if(bsoID1.equals(partIfc2.getBsoID()))
                     {
                         flag1 = true;
                         break;
                     }
                 }
                 if(flag1 == false)
                 {
                   //������·�ߵ����ȥ��
                   if(!this.isHasRoute(partIfc1.getMasterBsoID()))
                   {
                     //System.out.println(partIfc1.getPartNumber()+"û��·��");
                     result.addElement(partIfc1);
                   }
                 }
             }
             return result;

           }
//         CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��,����"��·�����"

//         CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��,����"��������"
           /**
            * 20070525  add   liuming
            * ��ȡ��׼�����й�������ڳ����е�ʹ������
            * @param productMasterID ����MasterID
            * @param partMap HashMap key�����ִ�0��ʼ value�����MasterID
            * @throws QMException
            * @return HashMap key�����ִ�0��ʼ value���������
            */
           public HashMap getCounts(String productMasterID, HashMap partMap) throws
               QMException,java.sql.SQLException
           {
           	    if (VERBOSE)
        	   System.out.println("-----getCounts   productMasterID="+productMasterID);

             StandardPartService partService = (StandardPartService)
                 EJBServiceHelper.getService("StandardPartService");

             PartConfigSpecIfc configspec = getCurrentUserConfigSpec();
             if(productMasterID==null || productMasterID.equals(""))
             {
               throw new QMException("Exception: TechnicsRouteService.getCounts()  productMasterID ��null");
             }

             //������Ʒ�����°汾
             QMPartIfc  rootPart = getLastedPartByConfig(productMasterID,configspec);

             int size = partMap.size();
           //  System.out.println("size="+size);
             //������ʶ·�߱�������㲿�������Ƿ����2000������2000ʱ���ò�ͬ�Ĳ��Լ�����ز����ڳ����ϵ�����
             boolean flag = (size > 500);//2017-8-29 ��Ϊ500
             //CCBegin SS32
             //��10����׼�������õ�һ�ַ�ʽ����������
             //��Ϊ5��
             if(!flag)
             {
             	 int cc = 0;
             	 for(int i=0;i<size;i++)
               {
               	 String masterID = partMap.get(String.valueOf(i)).toString();
               	 QMPartIfc part = getLastedPartByConfig(masterID,configspec);
               	 if (part == null)
               	 {
               	 	 continue;
               	 }
               	 if(part.getPartNumber().startsWith("Q")||part.getPartNumber().startsWith("CQ")||part.getPartNumber().startsWith("T"))
               	 {
               	 	 cc++;
               	 }
               	 //if(cc>10)
               	 if(cc>=4)
               	 {
               	 	 flag = true;
               	 	 break;
               	 }
               }
             }
             //CCEnd SS32
             HashMap partIDCountMap = null;
             //������������������2000����ͳ�Ƹó��͵������Ӽ�������
             if (flag)
             {
               //�����ָ�������µ������Ӳ���������,��Ϊ�Ӽ�BSOID,ֵΪ�Ӽ�����
               partIDCountMap = partService.getSonPartsQuantityMap(rootPart);
              // System.out.println("-----partIDCountMap="+partIDCountMap);
             }
             //�㲿��id�����㲿���ĸ������ϣ����ٲ�ѯ�����Ĵ���
             HashMap parentMap = new HashMap();
             //�㲿���ڳ�����ʹ�������Ļ���
             HashMap useQuantityMap = new HashMap();

             HashMap resultMap = new HashMap();

             for(int i=0;i<size;i++)
             {

               String masterID = partMap.get(String.valueOf(i)).toString();
              // System.out.println("-------i"+i+"   masterID="+masterID);
               QMPartIfc part = getLastedPartByConfig(masterID,configspec);
               //System.out.println("part="+part);
               if (part == null) {
               	 //CCBegin SS32
               	 resultMap.put(String.valueOf(i),"0");
               	 //CCEnd SS32
                 continue;
               }


               ////////����ÿ��������////////////////////////////////////////////////////
               String countInProduct = "";
               if(flag)  //������������������2000
               {
                   if (partIDCountMap.get(part.getBsoID()) != null)
                   {
                     countInProduct = (String) partIDCountMap.get(part.getBsoID());
                    // System.out.println("id:"+part.getBsoID()+" countInProduct="+countInProduct);
                   }
                   else {
                     //System.out.println("û�ҵ�����:"+part.getBsoID());
                   }
               }

               else    //�������������2000
               {
                   //�ǲ�Ʒ����
                   if (part.getBsoID().equals(rootPart.getBsoID()))
                   {
                     countInProduct = "1";
                   }
                   else
                   {
                     //��ʱparentMap��useQuantityMap�ж�û������ ѭ�������������
                	//     System.out.println(" ---------���ǲ�Ʒ����-------");
                	     //-----------------leixleix
//                     countInProduct = new Integer(new Float(ReportLevelOneUtil.getUsesCountWholeStruct(part,
//                         rootPart,configspec)).intValue()) +
//                         "";
                     countInProduct = new Integer(new Float(ReportLevelOneUtil.getUseQuantity(part,
                             rootPart,parentMap,useQuantityMap,configspec)).intValue()) +
                             "";
                     //----------------------leixleix
                     /**
                     if (countInProduct.trim().equals("0"))
                     {
                       countInProduct = "";
                     }*/
                   //  System.out.println(" countInProduct="+countInProduct);
                     int cip = Integer.parseInt(countInProduct.trim());
                     if(cip<0)
                     {
                       cip = Math.abs(cip);
                       countInProduct = String.valueOf(cip);
                     }
                    // System.out.println(" count="+countInProduct);
                   }
                 }

               ///////����ÿ�����������
               /////////////////////////////////////////////////////////////

              resultMap.put(String.valueOf(i),countInProduct);

             }

             return resultMap;
           }

           /**
            * 20070116 liuming add
            * ��ȡ��ǰ�û������ù淶������û����״ε�½ϵͳ������Ĭ�ϵġ�������ͼ��׼�����ù淶��
            * @throws QMException ʹ��ExtendedPartServiceʱ���׳���
            * @return PartConfigSpecIfc ��׼���ù淶��
            */
           public  PartConfigSpecIfc getCurrentUserConfigSpec() throws
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
               else if(configSpec.getStandard()==null||configSpec.getStandard().getViewObjectIfc()==null)
               {
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


//         CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·��,����׼״̬������׼
           public QMPartIfc getLastedPartofsz(QMPartMasterIfc master,PartConfigSpecIfc configSpecIfc) throws
           QMException{
        	   UsersService uService ;
  		     VersionControlService service1 = (VersionControlService)
	           EJBServiceHelper.getService("VersionControlService");
        	   QMPartIfc part=  getLastedPartByConfig(master,configSpecIfc);
        	   //CCBegin SS34
        	   if(part==null)
        	   {
        	   	 return null;
        	  }
        	  //CCEnd SS34
               if(fbuserid!=null&&!fbuserid.endsWith("")){
        	   uService = (UsersService) EJBServiceHelper.getService(
                "UsersService");
        	    fbuserid= uService.getUserValueInfo("��������").getBsoID();
               }

        	   //System.out.println(""+fbuserid);
        	   //System.out.println("   ���°汾="+part+"  "+part.getVersionValue()+" "+part.getLifeCycleState()+" "+part.getIterationModifier());
        	   if(!part.getLifeCycleState().equals("PREPARING")&&part.getIterationModifier().equals(fbuserid))
        	   {

        	     Collection  myVersionCollection = (Collection) service1.allVersionsOf( part);
        	     Iterator i = myVersionCollection.iterator();
        	     while(i.hasNext()){
        	    	 QMPartIfc part1=(QMPartIfc)i.next();
        	    	 System.out.println("   part1="+part1);
        	    	 if(part1.getLifeCycleState().equals("PREPARING")||!part.getLifeCycleState().equals(fbuserid))
        	    	 {
        	    		// System.out.println("   ���İ汾��"+part1+" "+part1.getVersionValue());
        	    		 return part1;
        	    	 }

        	     }
        	   }
        	   else{
        		   return part;
        	   }
        	   return null;
           }
//         CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·��,����׼״̬������׼

           /**
            * ���ָ��������ض����ù淶������С�汾 20061209 liuming add
            * @param partMasterBsoID
            * @param configSpecIfc ���configSpecIfc=null�����õ�ǰ�û������ù淶
            * @return QMPartIfc �����ǰ�û������ù淶��û�в鿴�����Ȩ�޻�û�а汾���򷵻�null
            * @throws QMException
            */
           public QMPartIfc getLastedPartByConfig(String partMasterBsoID,PartConfigSpecIfc configSpecIfc) throws
               QMException
           {
             PersistService pservice = (PersistService) EJBServiceHelper.
                 getPersistService();
             QMPartMasterIfc masterInfo = (QMPartMasterIfc) pservice.refreshInfo(partMasterBsoID);
             return getLastedPartByConfig(masterInfo,configSpecIfc);
           }

//       	CCBegin by leixiao 2009-1-8 ԭ�򣺽����������·��,�汾ȡ���з����汾
    public String  getibaPartVersion(QMPartMasterIfc master) throws
           QMException
       {
         String version="";
         QMPartIfc part= getLastedPartofsz(master,null);
        
         if(part!=null){
        	 version= getPartSourceVersion(part.getBsoID());
        	 
         }
       if (version == null || version.equals("")) {
    	   if(part!=null)
          version = part.getVersionValue();
       }
       if(version != null && !version.equals("")) 
         version=version.substring(0,version.indexOf("."));
         return version;

       }

    public String  getibaPartVersion(QMPartIfc part) throws
    QMException
{
  String version="";
  if(part!=null){
 	 version= getPartSourceVersion(part.getBsoID());
  }
if (version == null || version.equals("")) {
	   if(part!=null)
version = part.getVersionValue();
}
if(version != null && !version.equals("")) 
  version=version.substring(0,version.indexOf("."));
  return version;

}

           /**
            * ��IBA���ԣ���ȡ����Ĵ�汾
            * @param ID PartBsoID
            * @return �����IBA�����еİ汾
            * @throws QMException
            */
           private  String getPartSourceVersion(String ID) throws
               QMException {

             PersistService pservice = (PersistService) EJBServiceHelper.
                 getPersistService();
             String defintionID = null;

             QMQuery query11 = new QMQuery("StringDefinition");
             query11.addCondition(new QueryCondition("name", "=", "sourceVersion"));
             Collection collf = pservice.findValueInfo(query11);
             for (Iterator iterator = collf.iterator(); iterator.hasNext(); ) {
               com.faw_qm.iba.definition.model.StringDefinitionInfo dd = (com.faw_qm.
                   iba.
                   definition.model.StringDefinitionInfo) iterator.next();
               defintionID = dd.getBsoID();
             }

             QMQuery query = new QMQuery("StringValue");
             query.addCondition(new QueryCondition("definitionBsoID", "=",
                                                   defintionID));

             query.addAND();
             query.addCondition(new QueryCondition("iBAHolderBsoID", "=", ID));

             Collection collf1 = pservice.findValueInfo(query);
             for (Iterator iterator = collf1.iterator(); iterator.hasNext(); ) {
               StringValueInfo a = (StringValueInfo) iterator.next();
               return a.getValue().trim();
             }
             return "";
           }

//       	CCEnd by leixiao 2009-1-8 ԭ�򣺽����������·��,�汾ȡ���з����汾

           public QMPartIfc getLastedPartByConfig(QMPartMasterIfc master) throws
           QMException
       {

         return getLastedPartByConfig(master,null);
       }

           /**
            * ���ָ��������ض����ù淶������С�汾 20061209 liuming add
            * @param master QMPartMasterIfc
            * @param configSpecIfc ���configSpecIfc=null�����õ�ǰ�û������ù淶
            * @return QMPartIfc �����ǰ�û������ù淶��û�в鿴�����Ȩ�޻�û�а汾���򷵻�null
            * @throws QMException
            */
           public QMPartIfc getLastedPartByConfig(QMPartMasterIfc master,PartConfigSpecIfc configSpecIfc) throws
               QMException {
             if(master ==null)
             {
               throw new TechnicsRouteException("Master Is Null");
             }
             if(configSpecIfc ==null ||configSpecIfc.getStandard()==null||configSpecIfc.getStandard().getViewObjectIfc()==null)
             {
               //��õ�ǰ�û������ù淶
               configSpecIfc = getCurrentUserConfigSpec();
             }
             ConfigService service = (ConfigService) EJBServiceHelper.getService(
                 "ConfigService");

             //{{{{{{�����������°汾
             Collection collection = service.filteredIterationsOf(master,
                 new PartConfigSpecAssistant(configSpecIfc));
             if ( (collection == null)  ||(collection.size() == 0)) {
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
//         CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��,����"��������"

//         CCBeginby leixiao 2008-7-31 ԭ�򣺽����������·��
           /**
           *
           * @param part QMPartIfc
           * @param product QMPartIfc
           * @param atts String[] �������棬����·�ߣ�װ��·�ߣ���š�����
           * @return Vector Vector�е�ÿ��Ԫ��ΪHashMap,HashMap�ĸ�ʽΪ��key=����·�ߣ�װ��·�ߣ���š�������value = �����ֵ
           * gcy Add in 2005-08-08 for jiefang
           */
          public Vector getListAndBrances(QMPartIfc product, QMPartIfc part,
                                          String[] atts) throws
              QMException {
        	 // System.out.println("-------product="+product+"  part="+part);
            Vector result = new Vector();
            Collection cols = getRoutesAndLinks(part);
            if (cols == null || cols.size() == 0) {
              return result;
            }
            Iterator i = cols.iterator();
            ListRoutePartLinkInfo info = null;
            TechnicsRouteListInfo routelist = null;

            //�������ˣ������ǰ·�߱�����·�ߣ���ȡ��ǰ·�߱��еģ����������µġ����⣬��Ҫ��׼��
            while (i.hasNext()) {
              Object[] objss = (Object[]) i.next();
              ListRoutePartLinkInfo info1 = (ListRoutePartLinkInfo) objss[0];
              TechnicsRouteListInfo routelist1 = (TechnicsRouteListInfo) objss[1];
              if (routelist1.getRouteListState().equals("��׼")) {
                continue;
              }
              if (routelist1.getProductMasterID().equals(product.getMasterBsoID())) {
                info = info1;
                routelist = routelist1;
                break;
              }
              else {
                if (info == null) {
                  info = info1;
                  routelist = routelist1;
                }
                else
                if (routelist.getModifyTime().before(routelist.getModifyTime())) {
                  info = info1;
                  routelist = routelist1;
                }
              }
            }
            if (info != null) {
              HashMap temp = new HashMap();
              for (int j = 0; j < atts.length; j++) {
                String att = atts[j];
                if (att.equals("��׼���")) {
                  temp.put("��׼���", routelist.getRouteListNumber());
                }
                else if (att.equals("��ע")) {
                  temp.put("��ע", routelist.getRouteListDescription());
                }
                else if (att.equals("u") || att.equals("װ��·��")) {
                  HashMap map = (HashMap) getRouteBranchs(info.getRouteID());
                  String routeText = "";
                  String assRouteText = "";
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
                    System.out.println("makeStr%%%%%%%%%%%====="+makeStr);
                    
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
                  System.out.println("routeTextrouteText==="+routeText);
                  temp.put("����·��", routeText);
                  temp.put("װ��·��", assRouteText);

                }

              }
              result.add(temp);
            }
            return result;
          }

          /**
           *
           * @param part QMPartIfc-yanqi-20061027-ȡװ��·��ʱ������Լ���װ��·�߾����Լ���װ��·�ߣ����û�о��ã�����������·����Ϊװ��·��
           * @param product QMPartIfc
           * @param atts String[] �������棬����·�ߣ�װ��·�ߣ���š�����
           * @return Vector Vector�е�ÿ��Ԫ��ΪHashMap,HashMap�ĸ�ʽΪ��key=����·�ߣ�װ��·�ߣ���š�������value = �����ֵ
           * gcy Add in 2005-08-08 for jiefang
           */
          public Vector getListAndBrances(QMPartIfc product, QMPartIfc part,
                                          String[] atts, String parentFirstMakeRoute) throws
              QMException {
            PersistService pservice = (PersistService) EJBServiceHelper.
                getPersistService();
            Vector result = new Vector();
            //������߼��ܳ��򷵻��丸��������·�ߺ�װ��·��
            if (part.getPartType().toString().equalsIgnoreCase("Logical") &&
                (parentFirstMakeRoute != null && parentFirstMakeRoute.length() > 0)) {
              HashMap map = new HashMap();
              map.put("����·��", parentFirstMakeRoute);
              map.put("װ��·��", parentFirstMakeRoute);
              //CCBegin SS15
              map.put("��ɫ����ʶ","");
              //CCEnd SS15
              result.add(map);
              return result;
            }
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
              //System.out.println("-----------info1-="+info1+"   routelist1= "+routelist1+"------"+info1.getRouteID());

              if (routelist1.getRouteListState().equals("��׼")) {
                continue;
              }
              if (info1.getRouteID() == null ||
                  ( (com.faw_qm.technics.route.model.TechnicsRouteInfo) pservice.
                   refreshInfo(info1.getRouteID())).getModefyIdenty().getCodeContent().equals("ȡ��")) { //���·�߱��Ϊȡ������Ҫ
                continue;
              }

              //   if (routelist1.getProductMasterID().equals(product.getMasterBsoID())) {
              //     info = info1;
              //     routelist = routelist1;
              //     break;
              //  }
              //  else {
              if (info == null) {
                info = info1;
                routelist = routelist1;
              }
              //CCBegin by liunan 2009-02-25 ��·�ߵĸ���ʱ��Ƚϡ�
              //else if (routelist.getModifyTime().before(routelist1.getModifyTime()))
              else if (info.getModifyTime().before(info1.getModifyTime()))
              //CCEnd by liunan 2009-02-25 
              {
                info = info1;
                routelist = routelist1;
              }
              //    }
            } //

            if (info != null) {
              HashMap temp = new HashMap();
              //����atts���Լ��ϣ�ʵ����ֻ���������������С�����·�ߡ���װ��·�ߡ������صľ���һ�����ĸ���ֵ�Ե�HashMap����׼��ţ���ע������·�ߣ�װ��·�ߣ����������·�߶�û�У����ص���һ����������ֵ�Ե�HashMap����׼��ţ���ע����
              for (int j = 0; j < atts.length; j++) {
                String att = atts[j];
                temp.put("��׼���", routelist.getRouteListNumber());
                temp.put("��ע", routelist.getRouteListDescription());
                if (att.equals("����·��") || att.equals("װ��·��")) {
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
                    }//
                  }
                  //yanqi-20061027-���û��װ��·�ߣ���ȡ�丸��������·��
                  if (assRouteText.trim().length() < 1) {
                    assRouteText = parentFirstMakeRoute;
                  }//
                  routeText = routeText.endsWith("/") ?
                      routeText.substring(0, routeText.length() - 1) :
                      routeText;
                  assRouteText = assRouteText.endsWith("/") ?
                      assRouteText.substring(0, assRouteText.length() - 1) :
                      assRouteText;
                 //CCBegin SS14
                      String route1="";
                      String route2="";
                      if(routeText.indexOf("/")!=-1){
                      	String[] makeRoute=routeText.split("/");
                      	for(int n=0;n<makeRoute.length;n++) {
                             String ss=makeRoute[n];   	 
                             if(ss.trim().startsWith("��")||ss.trim().startsWith("��")||ss.trim().startsWith("��(����)")||
                          	ss.trim().startsWith("��(��)")||ss.trim().startsWith("�񣨺���")||ss.trim().startsWith("��")||
                          	ss.trim().startsWith("Ϳ")||ss.trim().startsWith("�ܣ�װ��")||ss.trim().startsWith("�ܣ��ᣩ")||
                          	ss.trim().startsWith("�ܣ��ݣ�")||ss.trim().startsWith("�ܣ��꣩")||ss.trim().startsWith("�ܣ����ף�")||
                          	ss.trim().startsWith("�ܣ��ᣩ"))
                             {
                          	   if(route1.equals("")){
                          		   route1=makeRoute[n];
                          	   }else{
                          		   route1=route1+"/"+makeRoute[n];
                          	   }   
                          	 }else{
                          		 if(route2.equals("")){
                            		   route2=makeRoute[n];
                            	   }else{
                            		   route2=route2+"/"+makeRoute[n];
                            	   }   
                          	 }
                             } 	
                      }   
                      if(!route1.equals("")){
                      	if(!route2.equals("")){
                      		routeText=route1+"/"+route2;
                      	}else{
                      		routeText=route1;
                      	}
                      } 
                      String route3="";
                      String route4="";                  
                      if(assRouteText.indexOf("/")!=-1){
                      	String[] assRoute1=assRouteText.split("/");
                      	for(int n=0;n<assRoute1.length;n++) {
                             String ss=assRoute1[n];
                             if(ss.trim().startsWith("��")||ss.trim().startsWith("��")||ss.trim().startsWith("��(����)")||
                          	ss.trim().startsWith("��(��)")||ss.trim().startsWith("�񣨺���")||ss.trim().startsWith("��")||
                          	ss.trim().startsWith("Ϳ")||ss.trim().startsWith("�ܣ�װ��")||ss.trim().startsWith("�ܣ��ᣩ")||
                          	ss.trim().startsWith("�ܣ��ݣ�")||ss.trim().startsWith("�ܣ��꣩")||ss.trim().startsWith("�ܣ����ף�")||
                          	ss.trim().startsWith("�ܣ��ᣩ"))
                             {
                          	   if(route3.equals("")){
                          		   route3=assRoute1[n];
                          	   }else{
                          		   route3=route3+"/"+assRoute1[n];
                          	   }   
                          	 }else{
                          		 if(route4.equals("")){
                            		   route4=assRoute1[n];
                            	   }else{
                            		   route4=route4+"/"+assRoute1[n];
                            	   }   
                          	 }
                             } 	
                      }   
                      if(!route3.equals("")){
                      	if(!route4.equals("")){
                      		assRouteText=route3+"/"+route4;
                      	}
                      }      
                 //CCEnd SS14
                  temp.put("����·��", routeText);
                  temp.put("װ��·��", assRouteText);
                 //CCBegin SS15
                  String colorFlag="";
                  if(info.getColorFlag()!=null&&info.getColorFlag().trim().equals("1")){
                	  colorFlag="��";
                  }
                  temp.put("��ɫ����ʶ",colorFlag);
                  //CCEnd SS15
                }
                /* else if (att.equals("װ��·��")) {
                   HashMap map = (HashMap) getRouteBranchs(info.getRouteID());
                   String routeText = "";
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
                       }
                     }
                     if (asseNode != null) {
                       makeStr = makeStr + asseNode.getNodeDepartmentName();
                     }
                     if (isTemp) {
                       makeStr = makeStr + "(��ʱ)";
                     }
                     if (makeStr == null || makeStr.equals("")) {
                       makeStr = "";
                     }
                     routeText = routeText + makeStr;
                   }
                   temp.put("װ��·��", routeText);
                 }*/
              }
              result.add(temp);
            }

            return result;
          }

          private Collection getRoutesAndLinks(QMPartIfc part) throws QMException {
        	    PersistService pservice = (PersistService) EJBServiceHelper.
        	        getPersistService();
        	    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        	    int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
        	    int k = query.appendBso(this.ROUTELIST_BSONAME, true);
        	    QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
        	                                              part.getMasterBsoID());
        	    query.addCondition(0, cond3);
        	    query.addAND();
//              CCBeginby leixiao 2008-11-12 ԭ�򣺽����������·��
        	    QueryCondition cond4 = new QueryCondition("alterStatus",
        	                                              QueryCondition.EQUAL,
        	                                              ROUTEALTER);
        	    query.addCondition(0, cond4);
        	    query.addAND();
//              CCEndby leixiao 2008-11-12 ԭ�򣺽����������·��
        	    QueryCondition cond5 = new QueryCondition("routeID",
        	                                              QueryCondition.NOT_NULL);
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
        	    if (VERBOSE) {
        	      System.out.println(TIME +
        	                         "routeService's getPartLevelRoutes SQL = " +
        	                         query.getDebugSQL());
        	    }
        	    return pservice.findValueInfo(query, false);

        	  }
//        CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
//        CCBeginby leixiao 2008-8-7 ԭ�򣺽����������·��
          /**
           * ��ȡָ���㲿���Ĺ���·����Ϣ(Ϊ �鿴�����·����Ϣ ����)
           * 20070508 liuming add
           * @param partMasterID String
           * @throws QMException
           * @return Collection
           */
          public Collection getRouteInfomationByPartmaster(String partMasterID) throws
                QMException {
              try {
                Collection kkk = new ArrayList();
                PersistService service = (PersistService) EJBServiceHelper.
                    getPersistService();
                QMQuery query1 = new QMQuery("ListRoutePartLink");
                query1.addCondition(new QueryCondition(RIGHTID, "=",
                                                       partMasterID));
                //CCBegin by liunan 2009-04-22
                query1.addAND();
                query1.addCondition(new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER));
                //CCEnd by liunan 2009-04-22
                query1.addOrderBy("modifyTime",true);
                Collection coll1 = service.findValueInfo(query1);

                //Key: masterID  Value: TechnicsRouteListMasterInfo
                //CCBegin by liunan 2009-02-25 
                //HashMap masterMap = new HashMap();                
                ArrayList masterMap = new ArrayList();
                //CCEnd by liunan 2009-02-25
                for (Iterator iterator1 = coll1.iterator(); iterator1.hasNext(); )
                {
                       ListRoutePartLinkInfo link = (ListRoutePartLinkInfo) iterator1.next();
                       String masterID = link.getRouteListMasterID();
                       TechnicsRouteListMasterInfo masterInfo = (TechnicsRouteListMasterInfo) service.
                           refreshInfo(masterID,false);
                       //CCBegin by liunan 2009-02-25 
                       //if(masterMap.containsKey(masterID))
                       if(masterMap.contains(masterID))
                       {
                       }
                       else
                       {
                         //masterMap.put(masterID, masterInfo);
                         masterMap.add(masterID);
                       }
                       //CCEnd by liunan 2009-02-25
               }

                VersionControlService vservice = (VersionControlService)EJBServiceHelper.getService(VERSION_SERVICE);
                WorkInProgressService wip = (WorkInProgressService)EJBServiceHelper.getService("WorkInProgressService");

                //�洢:ListRoutePartLinkInfo
                ArrayList finalList = new ArrayList();
                
                //CCBegin by liunan 2009-02-25
                //for(Iterator masit = masterMap.values().iterator();masit.hasNext();)
                for(int ii =0;ii<masterMap.size();ii++)
                {
                      //TechnicsRouteListMasterInfo masterInfo = (TechnicsRouteListMasterInfo)masit.next();
                      String masterStr = (String)masterMap.get(ii);
                      TechnicsRouteListMasterInfo masterInfo = (TechnicsRouteListMasterInfo) service.
                           refreshInfo(masterStr,false);
                      //CCEnd by liunan 2009-02-25
                      //��ø�Master�ĸ���汾������С�汾
                      Collection cc = vservice.allVersionsOf(masterInfo);
                      HashMap tempMap = new HashMap();
                      for(Iterator infocc = cc.iterator();infocc.hasNext();)
                      {
                         TechnicsRouteListInfo tempList = (TechnicsRouteListInfo)infocc.next();
                          tempMap.put(tempList.getBsoID(),tempList);
                         //������ڸ������ϼ�,�ұ����˼��,����ԭ��
                         if(WorkInProgressHelper.isWorkingCopy(tempList)&& isCheckedOutByOther(tempList))
                         {
                             tempList = (TechnicsRouteListInfo)wip.originalCopyOf(tempList);
                             if(tempMap.containsKey(tempList.getBsoID()))
                             {
                               continue;
                             }
                         }

                         //����ͬһ·�߱��а���ͬһ�㲿�����ܶ��,������Ҫִ�д˲�ѯ������
                         QMQuery query2 = new QMQuery("ListRoutePartLink");
                         query2.addCondition(new QueryCondition(RIGHTID, "=",partMasterID));
                         query2.addAND();
                         query2.addCondition(new QueryCondition(LEFTID, "=",tempList.getBsoID()));
                         query2.addOrderBy("modifyTime",true);
                         Collection coll = service.findValueInfo(query2);
                         for (Iterator iterator3 = coll.iterator(); iterator3.hasNext(); )
                         {
                           ListRoutePartLinkInfo aa = (ListRoutePartLinkInfo) iterator3.next();
                           finalList.add(aa);
                         }

                      }
                      tempMap=null;

                }
                 masterMap=null;

                for (Iterator iterator2 = finalList.iterator(); iterator2.hasNext(); )
                {
                  String make = "";
                  String assRoute = "";//yanqi-20061027
                  String changeSign = "";
                  ListRoutePartLinkInfo link = (ListRoutePartLinkInfo) iterator2.next();
                  String routeID = link.getRouteID();

                  if (routeID == null || routeID.length() < 1) {
                    String routeListID = link.getRouteListID();
                    TechnicsRouteListInfo routeList = (TechnicsRouteListInfo) service.
                        refreshInfo(routeListID);
                    String[] hehe = {
                        make, assRoute, changeSign, routeList.getRouteListNumber(),
                        routeList.getRouteListName(), routeList.getVersionValue(),
                        routeList.getBsoID()};//yanqi-20061027
                    kkk.add(hehe);
                    continue;
                  }

                  String routeListID = link.getRouteListID();
                  HashMap map = (HashMap) getRouteBranchs(routeID);
                  if (map != null && map.size() > 0) {
                    Iterator values = map.values().iterator();
                    String makeStr = "";
                    while (values.hasNext()) {
                      Object[] objs1 = (Object[]) values.next();
                      Vector makeNodes = (Vector) objs1[0]; //����ڵ�
                      RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //װ��ڵ�-yanqi-20061027
                      if (makeNodes != null && makeNodes.size() > 0) {
                        String kk = "";
                        for (int m = 0; m < makeNodes.size(); m++) {
                          RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
                          if (node.getIsTempRoute()) {
                            kk += node.getNodeDepartmentName() + "*" + "--";
                          }
                          else {
                            kk += node.getNodeDepartmentName() + "--";
                          }

                        }
                        kk = kk.endsWith("--") ? kk.substring(0, kk.length() - 2) : kk;
                        makeStr += kk + "/";
                      }
                      //yanqi-20061027
                      if (asseNode != null) {
                        if (!assRoute.trim().equals("")) {
                          assRoute = assRoute + "/" +
                              asseNode.getNodeDepartmentName();
                        }
                        else {
                          assRoute = assRoute +
                              asseNode.getNodeDepartmentName();
                        }
                      }
                    }
                    assRoute = assRoute.endsWith("/") ?
                        assRoute.substring(0, assRoute.length() - 1) :
                        assRoute;//

                    make = makeStr.endsWith("/") ?
                        makeStr.substring(0, makeStr.length() - 1) : makeStr;
                  }
                  TechnicsRouteInfo routeInfo = (TechnicsRouteInfo) service.refreshInfo(
                      routeID);
                  changeSign = routeInfo.getModefyIdenty().getCodeContent();
                  //leixleix************************************************************
                  TechnicsRouteListInfo routeList = (TechnicsRouteListInfo) service.
                      refreshInfo(routeListID,false);
                  //CCBegin by leixiao 2010-1-12 �����޸�ʱ��
                  String modifyTime = routeList.getModifyTime().toString();
                  modifyTime =  modifyTime.substring(0,modifyTime.lastIndexOf("."));
                  String[] hehe = {
                      make, assRoute,changeSign, routeList.getRouteListNumber(),
                      routeList.getRouteListName(), routeList.getVersionValue(),
                      routeList.getBsoID(),modifyTime};//yanqi-20061027
                 // CCEnd by leixiao 2010-1-12 �����޸�ʱ��
                  kkk.add(hehe);
                }
                finalList=null;
                return kkk;

              }
              catch (QMException wx) {
                wx.printStackTrace();
                throw wx;

              }
            }

          /**
           * liuming 20070130 add
           * �ж�ָ���Ķ����Ƿ��Ѿ�������û����
           * @param workable ҵ�����
           * @return ���ָ���Ķ����Ѿ�������û�������򷵻�true
           * @throws QMRemoteException
           */
          private  boolean isCheckedOutByOther(WorkableIfc workable)
                  throws QMException
          {
              boolean flag = false;
              //"WORKING"��"TO_DELETED"�� "CHECKED_OUT"����
              if (WorkInProgressHelper.isCheckedOut(workable))
              {
                SessionService spService = (SessionService) EJBServiceHelper.
                  getService("SessionService");
                  UserIfc sysUser = (UserIfc) spService.getCurUserInfo();
                  flag = !WorkInProgressHelper.isCheckedOut(workable,
                          (UserIfc) sysUser);
              }
              return flag;
            }

          /**
           * ����׼֪ͨ����׼��ϵͳ�Զ���·���а������ӹ�˾��Ƶ��㲿���Է��ӹ�˾������Ȩ��
           * ֻ���㲿����������ĵ���ͼ��������Ȩ��
           * 20071010 add by liuming  for jiefang
           * @param routeListID String
           * @throws QMException
           */
          public void setReadAclForSub(String routeListID) throws QMException
            {
              //System.out.println("TechnicsRouteService.setReadAclForSub.....Begin=="+routeListID);
              PersistService pservice = (PersistService) EJBServiceHelper.
                  getPersistService();
              //���·�߱�������ListRoutePartLink
              QMQuery qq = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
              QueryCondition cond0 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                                        routeListID);
              qq.addCondition(cond0);
              qq.addAND();
              QueryCondition cond1 = new QueryCondition("alterStatus",
                                                        QueryCondition.NOT_EQUAL,
                                                        PARTDELETE);
              qq.addCondition(cond1);
              Collection listPartLinkVector = pservice.findValueInfo(qq);
System.out.println("listPartLinkVector=="+listPartLinkVector.size());

              QMQuery query = new QMQuery(ROUTENODE_BSONAME);
              int i = query.appendBso(LIST_ROUTE_PART_LINKBSONAME, false);
              QueryCondition cond2 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                                        routeListID);
              query.addCondition(i, cond2);
              query.addAND();
              QueryCondition cond3 = new QueryCondition("alterStatus",
                                                        QueryCondition.NOT_EQUAL,
                                                        PARTDELETE);
              query.addCondition(i, cond3);
              query.addAND();
              QueryCondition cond4 = new QueryCondition("routeID", "routeID");
              query.addCondition(0, i, cond4);
              Collection nodes = pservice.findValueInfo(query);



              HashMap map = new HashMap();

              //�Ը�·�ߵ�λ���б���
              for (Iterator iter = nodes.iterator(); iter.hasNext(); )
              {
                RouteNodeInfo nodeInfo = (RouteNodeInfo) iter.next();
                String departmentID = nodeInfo.getNodeDepartment(); //����ID
                BaseValueIfc coding = pservice.refreshInfo(departmentID,false);
                //CCBegin SS35
                //if(coding instanceof CodingClassificationIfc)
                if(coding!=null)
                //CCEnd SS35
                {
                  boolean flag = false;
                  String groupName1 = "";
                  String groupName2 = "";
                  //CCBegin SS35
                  //String classSort = ((CodingClassificationIfc)coding).getClassSort();//·�ߵ�λ���
                  String classSort = "";
                  if(coding instanceof CodingClassificationIfc)
                  {
                  	classSort = ((CodingClassificationIfc)coding).getClassSort();//·�ߵ�λ���
                  }
                  if(coding instanceof CodingIfc)
                  {
                  	classSort = ((CodingIfc)coding).getShorten();//·�ߵ�λ���
                  }
                  //CCEnd SS35
                  if(classSort.equals("��"))
                  {
                     flag =true;
                     groupName1 = "����������";
                     groupName2 = "����Ȩ����";
                  }
                  else if(classSort.equals("��"))
                  {
                     flag =true;
                     groupName1 = "���������";
                     groupName2 = "����Ȩ����";
                  }
                  else if(classSort.equals("��"))
                  {
                     flag =true;
                     groupName1 = "��������";
                     groupName2 = "���Ȩ����";
                  }
                  else if(classSort.equals("��"))
                  {
                     flag =true;
                     groupName1 = "���Ž�����";
                     groupName2 = "����Ȩ����";
                  }
                  else if(classSort.equals("��"))
                  {
                     flag =true;
                     groupName1 = "������������";
                     groupName2 = "������Ȩ����";
                  }
                  else if(classSort.equals("��"))
                  {
                     flag =true;
                     groupName1 = "�����������";
                     groupName2 = "������Ȩ����";
                  }
                  //CCBegin by liunan 2012-02-01 ������ר����λ
                  else if(classSort.equals("ר"))
                  {
                     flag =true;
                     //CCBegin SS45
                     //groupName1 = "ר�ó�������";
                     //groupName2 = "ר�ó�Ȩ����";
                     groupName1 = "���ؽ�����";
                     groupName2 = "����Ȩ����";
                     //CCEnd SS45
                  }
                  //CCEnd by liunan 2012-02-01
                  //CCBegin SS7
                  //CCBegin SS27
                  //else if(classSort.equals("��"))
                  else if(classSort.equals("��")||classSort.equals("��ݳ���"))
                  //CCEnd SS27
                  {
                     flag =true;
                     groupName1 = "������Ľ�����";
                     groupName2 = "�������Ȩ����";
                  }
                  //CCEnd SS7
                  //CCBegin SS8
                  else if(classSort.equals("��(��)"))
                  {
                     flag =true;
                     groupName1 = "������װ��������";
                     groupName2 = "������װ��Ȩ����";
                  }
                  else if(classSort.equals("��(��)"))
                  {
                     flag =true;
                     groupName1 = "�Ϻ���װ��������";
                     groupName2 = "�Ϻ���װ��Ȩ����";
                  }
                  //CCEnd SS8
                  //CCBegin SS24
                  else if(classSort.equals("��"))
                  {
                     flag =true;
                     groupName1 = "�ɶ�������";
                     groupName2 = "�ɶ�Ȩ����";
                  }
                  //CCEnd SS24
                  //CCBegin SS16
//                  else if(classSort.equals("��"))
//                  {
//                     flag =true;
//                     groupName1 = "������Ľ�����";
//                     groupName2 = "�������Ȩ����";
//                  }
                  //CCEnd SS16
                  if(flag)
                  {
                    String partmaster = null;
                    String routeID1 = nodeInfo.getRouteInfo().getBsoID();
                    for(Iterator links = listPartLinkVector.iterator(); links.hasNext();)
                    {
                      ListRoutePartLinkInfo link = (ListRoutePartLinkInfo)links.next();
                      if(routeID1.equals(link.getRouteID()))
                      {
                        partmaster = link.getRightBsoID();
                        String[] array  = new String[2];
                        array[0] = partmaster;
                        array[1] = groupName1;
                        map.put(partmaster+groupName1,array);
                        String[] array2  = new String[2];
                        array2[0] = partmaster;
                        array2[1] = groupName2;
                        map.put(partmaster+groupName2,array2);

                      }
                    }
                  }/////////// end flag=true

                }
                else
                {
                	//CCBegin SS31
                  //return;
                  //CCEnd SS31
                }
              }///////////end for

              QMAclEntry qae = new QMAclEntry();
              qae.setKey("X");
              qae.addPermission(QMPermission.READ);
              PartConfigSpecIfc configspec =  getCurrentUserConfigSpec();
              Collection c = map.values();
              for(Iterator it = c.iterator();it.hasNext();)
              {
                Object[] as = (Object[])it.next();
                String pmID = as[0].toString();
                String group = as[1].toString();
                //System.out.println("��Ȩ���� = "+group);
                ArrayList objs = getAclObj(pmID,configspec);
                for(Iterator it2 = objs.iterator();it2.hasNext();)
                {
                  Object ooo = it2.next();
                  String bviID = ((BaseValueIfc)ooo).getBsoID();
                  AdHocControlled bvi=(AdHocControlled)pservice.refreshBso(bviID,false);

                  qae.setPrincipal(group, false);
                  //CCBegin SS44
                  if(bviID.indexOf("Part")!=-1)
                  {
                  	qae.addPermission(QMPermission.DOWNLOAD);
                  }
                  else
                  {
                  	qae.removePermission(QMPermission.DOWNLOAD);
                  }
                  //CCEnd SS44

                  AdHocAclHelper.addOrReplaceEntry(bvi, qae);
                }
              }
         //System.out.println("TechnicsRouteService.setReadAclForSub.....End");

        }

          /**
           * �õ�Ҫ��Ȩ���ĵ���EPM�ĵ�
           * 20070923 add by liuming for jiefang
           * @param partMasterID String
           * @param config PartConfigSpecIfc
           * @return ArrayList
           * @throws QMException
           */
          private ArrayList getAclObj(String partMasterID,PartConfigSpecIfc config)  throws QMException
         {
           ArrayList result = new ArrayList();
           QMPartIfc lastPart = this.getLastedPartByConfig(partMasterID,config);
           result.add(lastPart);
           result.addAll(getDocAndEPM(lastPart));
           return result;
         }

          /**
           * ��ȡָ����������вο��ĵ��������ĵ���EPM�ĵ�
           * 20070923 add by liuming for jiefang
           * @param partinfo QMPartIfc
           * @return ArrayList
           * @throws QMException
           */
          private ArrayList getDocAndEPM(QMPartIfc partinfo) throws QMException
          {

            ArrayList result = new ArrayList();
            StandardPartService spService = (StandardPartService)EJBServiceHelper.getService(
              "StandardPartService");
            //��ø��㲿�����������вο��ĵ�(DocIfc)���°汾��ֵ����ļ���
            Vector docInfoVector = spService.getReferencesDocMasters(partinfo);
            if(docInfoVector!=null && docInfoVector.size()>0)
            {
              result.addAll(docInfoVector);
            }
            //���㲿�������ĵ���ֵ���󼯺�
            Vector descriDocs = spService.getDescribedByDocs(partinfo,true);
            if(descriDocs!=null && descriDocs.size()>0)
            {
              result.addAll(descriDocs);
            }

            ArrayList epmList = getRelatedEpmDocs(partinfo);
            if(epmList!=null && epmList.size()>0)
            {
              result.addAll(epmList);
            }

            return result;
          }



          /**
           * ��ȡ�����EPM�ĵ�
           * 20070923 add by liuming for jiefang
           * @param part QMPartIfc
           * @return ArrayList ��EPMDocumentInfo��
           * @throws QMException
           */
          private ArrayList getRelatedEpmDocs(QMPartIfc part) throws QMException {

            ArrayList result = new ArrayList();
            PersistService pservice = (PersistService) EJBServiceHelper
                .getService("PersistService");
            // ��ѯ�㲿�����й������ĵ�
            QMQuery query = new QMQuery("EPMBuildHistory");
            QueryCondition condition2 = new QueryCondition("rightBsoID", "=", part
                .getBsoID());
            query.addCondition(condition2);
            Collection epmdocs = (Collection) pservice.findValueInfo(query, false);
            for (Iterator epmIte = epmdocs.iterator(); epmIte.hasNext(); ) {
              EPMBuildHistoryInfo epmdoclink = (EPMBuildHistoryInfo) epmIte
                  .next();
              String epmid = epmdoclink.getLeftBsoID();
              EPMDocumentInfo epmdoc = (EPMDocumentInfo) pservice
                  .refreshInfo(epmid);
              result.add(epmdoc);

            }

            return result;
          }

          /** 20070108 liuming add
           * ���ָ�����������·�ߺ�װ��·��
           * ע�⣺��Ϊ�߼��ܳ�Ҳ�п��ܱ���·�ߣ����Բ��ٿ����߼��ܳɵ����⣬������ͨ���
           * @param part QMPartIfc
           * @return String[] ��һ��Ԫ��Ϊ����·��,�ڶ���Ԫ��Ϊװ��·��
           * @throws QMException
           */
          public String[] getRouteString(QMPartIfc part) throws QMException
          {
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
            while (i.hasNext())
            {
              Object[] objss = (Object[]) i.next();
              ListRoutePartLinkInfo info1 = (ListRoutePartLinkInfo) objss[0];
              TechnicsRouteListInfo routelist1 = (TechnicsRouteListInfo) objss[1];

              if (routelist1.getRouteListState().equals("��׼")) {
                continue;
              }
              if (info1.getRouteID() == null ||
                  ( (com.faw_qm.technics.route.model.TechnicsRouteInfo) pservice.
                   refreshInfo(info1.getRouteID())).getModefyIdenty().getCodeContent().equals("ȡ��")) { //���·�߱��Ϊȡ������Ҫ
                continue;
              }

              if (info == null) {
                info = info1;
                routelist = routelist1;
              }
              else if (routelist.getModifyTime().before(routelist1.getModifyTime()))
              {
                info = info1;
                routelist = routelist1;
              }
            } //

            //����ҵ������µ�·�߱�
            if (info != null)
            {
              String routeText = ""; //����·�ߣ���ͬ��·�ߴ�֮���á�/���ָ���ͬһ·�ߴ�������ڵ�֮���á�--���ָ�
              ArrayList routeList = new ArrayList();
              String assRouteText = ""; //װ��·��
              HashMap map = (HashMap) getRouteBranchs(info.getRouteID());
              //һ���㲿�������ж��·�ߴ���һ��·�ߴ����ֿ����ж������ڵ��һ��װ��ڵ㡣������Ƕ�·�ߴ����еı���
              Iterator values = map.values().iterator();
              while (values.hasNext())
              {
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

          public String getRouteCodeDesc(String routeID) throws QMException {
        	    PersistService pservice = (PersistService) EJBServiceHelper.
        	        getPersistService();
        	    TechnicsRouteIfc route = (TechnicsRouteIfc) pservice.refreshInfo(routeID);
        	    return route.getModefyIdenty().getCodeContent();

        	  }

//        CCEndby leixiao 2008-7-31 ԭ�򣺽����������·��
//        CCBegin by leixiao 2008-8-11 ԭ�򣺽����������·�ߣ�����·��
          /**
           * �ռ��������� 20061209 liuming add
           * @param routeListID String
           * @param IsExpandByProduct �Ƿ񰴳�չ������ʱĬ����Ϊ�棩
           * @throws QMException
           */
          public ArrayList gatherExportData(String routeListID, String IsExpandByProduct)
              throws
              QMException
          {
              return ReportLevelOneUtil.getAllInformation2(routeListID,"true");
          }

          /**
           * �����Ӽ��ڲ�Ʒ��ʹ�õ�����
           * @param part QMPartIfc �Ӽ�
           * @param parent QMPartIfc ����
           * @throws QMException
           * @return int ����
           */
          private int calCountInProduct(QMPartIfc part, QMPartIfc parent) throws
              QMException {

            StandardPartService partService = (StandardPartService) EJBServiceHelper.
                getService(PART_SERVICE);
            if (part.getPartNumber().equals(parent.getPartNumber())) {
              return 1;
            }
            String count = (String) partService.getPartQuantity(parent, part);
           // System.out.println("-----count="+count+"");
            if (VERBOSE) {
              System.out.println(part.getBsoID() + part.getPartName() + "��" +
                                 parent.getBsoID() + parent.getPartName() +
                                 "�е�����==" + count);
            }
            if (count != null && !count.trim().equals("")) {
              return new Integer(count).intValue();
            }
            else {
              return 0;
            }
          }

//        CCEnd by leixiao 2008-8-11 ԭ�򣺽����������·�� ������·��

//        CCBegin by leixiao 2008-8-11 ԭ�򣺽����������·��
          /**
           * �õ��첿�������и����Ѿ�·��������������
           * @param partmaster QMPartMasterIfc
           * @throws QMException
           * @return Vector
           */
          public Vector findParentsAndRoutes(QMPartMasterIfc partmaster, String listID) throws
              QMException {
            Vector result = new Vector();
            StandardPartService standardPartService = (
                StandardPartService)
                EJBServiceHelper.getService(
                "StandardPartService");
            QMPartIfc part = this.filteredIterationsOfByDefault(partmaster);
            PartConfigSpecIfc configSpecIfc = standardPartService.findPartConfigSpecIfc();
            Collection col = standardPartService.getParentPartsByConfigSpec(part,
                configSpecIfc);
        	// CCBeginby leixiao 2009-2-21 ԭ�򣺽���������˴���ָ��
            //int size = col.size();
        	// CCEndby leixiao 2009-2-21 ԭ�򣺽������
            if (col == null || col.size() == 0) {
              return result;
            }
            Iterator i1 = col.iterator();
            while (i1.hasNext()) {
              Object[] temp = new Object[4];
              Object[] objs = (Object[]) i1.next();
              PartUsageLinkIfc linkinfo = (PartUsageLinkIfc) objs[0];
              QMPartInfo parentinfo = (QMPartInfo) objs[1];
              temp[0] = parentinfo;
              temp[1] = linkinfo;
              Collection cols = getRoutesAndLinks(parentinfo);
              if (cols == null || cols.size() == 0) {
                temp[2] = null;
                temp[3] = null;
              }
              Iterator i = cols.iterator();
              ListRoutePartLinkInfo info = null;
              TechnicsRouteListInfo routelist = null;

              //�������ˣ������ǰ·�߱�����·�ߣ���ȡ��ǰ·�߱��еģ����������µġ����⣬��Ҫ��׼��
              while (i.hasNext()) {
                Object[] objss = (Object[]) i.next();
                ListRoutePartLinkInfo info1 = (ListRoutePartLinkInfo) objss[0];
                TechnicsRouteListInfo routelist1 = (TechnicsRouteListInfo) objss[1];
//          if(routelist1.getRouteListState().equals("��׼"))
                //   continue;
                if (routelist1.getBsoID().equals(listID)) {
                  info = info1;
                  routelist = routelist1;
                  break;
                }
                else {
                  if (info == null) {
                    info = info1;
                    routelist = routelist1;
                  }
                  else
                  if (routelist.getModifyTime().before(routelist.getModifyTime())) {
                    info = info1;
                    routelist = routelist1;
                  }
                }
              }
              if (info != null) {
                // Object[] objss = (Object[]) i.next();
                //ListRoutePartLinkInfo info = (ListRoutePartLinkInfo) objss[0];
                //  TechnicsRouteListInfo routelist = (TechnicsRouteListInfo) objss[1];
                temp[2] = routelist;
                HashMap map = (HashMap) getRouteBranchs(info.getRouteID());

                if (map == null || map.size() == 0) {
                  continue;
                }
                String routeText = "";
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
                  routeText = routeText + "," + makeStr;
                }
                temp[3] = routeText;
              }
              result.add(temp);
            }
            return result;
          }
//        CCEnd by leixiao 2008-8-11 ԭ�򣺽����������·�� ������·��

//        CCBegin by leixiao 2008-8-11 ԭ�򣺽����������·��
          /**
           * �õ����еĲ���
           * @throws QMException
           * @return Vector
           * gcy Add in 2005-08-08 for jiefang
           */
          public Vector getAllDeps() throws
              QMException {
            CodingManageService cmService = (CodingManageService) EJBServiceHelper.
                getService("CodingManageService");
            Collection temp = cmService.findDirectClassificationByName("·����֯����", "�������");
            Iterator i = temp.iterator();
            Vector results = new Vector();
            while (i.hasNext()) {
              CodingClassificationIfc depart = (CodingClassificationIfc) i.next();
              results = getAllSubDepartMent(results, depart);
            }
            return results;
          }

          /**
           * �õ���ǰ���ŵ������Ӳ���
           * @param results Vector  �������
           * @param depart BaseValueIfc ��ǰ����
           * @throws QMException
           * @return Vector �������
           */
          private Vector getAllSubDepartMent(Vector results,
                                             CodingClassificationIfc depart) throws
              QMException {
            CodingManageService cmService = (CodingManageService) EJBServiceHelper.
                getService("CodingManageService");
            results.add(depart);
            if (depart instanceof CodingClassificationIfc) {
              Collection col = cmService.findDirectCodingClassification( (
                  CodingClassificationIfc) depart, true);
              if (col != null && col.size() > 0) {
                Iterator i = col.iterator();
                while (i.hasNext()) {
                  BaseValueIfc obj = (BaseValueIfc) i.next();
                  if (obj instanceof CodingClassificationIfc) {
                    getAllSubDepartMent(results, (CodingClassificationIfc) obj);
                  }
                }
              }
            }
            return results;
          }
//        CCEnd by leixiao 2008-8-11 ԭ�򣺽����������·��

//CCBegin by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձϷ���׼��޸�����
 /**
  * Ϊָ����׼�������ձ����͵������Ϊ����״̬����׼֪ͨ��ţ�
  * @param routeListBsoID ��׼֪ͨ��BsoID
  * 20070115 liuming add
  */
 public void setRouteCompletePartsState(String routeListBsoID) throws QMException
 {
   if(routeListBsoID==null || routeListBsoID.equals(""))
   {
     throw new QMException("����TechnicsRouteService����routeListNumberΪ��");
   }
   PersistService ps = (PersistService) EJBServiceHelper.getService(
       "PersistService");
   LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.
   getService("LifeCycleService");

   TechnicsRouteListIfc routelist = (TechnicsRouteListIfc)ps.refreshInfo(routeListBsoID,false);
   HashMap map = new HashMap();
   Collection coll = this.getRouteListLinkParts(routelist);
   for (Iterator iter = coll.iterator(); iter.hasNext(); )
   {
     //�㲿������
     ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
     QMPartIfc  partInfo=listLinkInfo.getPartBranchInfo();
//   CCBegin leixiao 2009-11-27 state->LifeCycleState
     if(partInfo!=null)
     lservice.setLifeCycleState(partInfo, LifeCycleState.toLifeCycleState("MANUFACTURING"));
//   CCEnd leixiao 2009-11-27 state->LifeCycleState
     
     //CCBegin by liunan 2012-05-29 ��øü���EPM�ĵ���������״̬��
     QMQuery query = new QMQuery("EPMBuildHistory");
     QueryCondition condition = new QueryCondition("rightBsoID","=",partInfo.getBsoID());
     query.addCondition(condition);
     Collection coll1 = ps.findValueInfo(query);
     Iterator iter1 = coll1.iterator();
     if(iter1.hasNext())
     {
     	 EPMBuildHistoryIfc link = (EPMBuildHistoryIfc)iter1.next();
     	 EPMDocumentIfc doc = (EPMDocumentIfc)link.getBuiltBy();
     	 if(doc!=null)
     	 {
     	 	 lservice.setLifeCycleState(doc, LifeCycleState.toLifeCycleState("MANUFACTURING"));
     	 }
     }
     //CCEnd by liunan 2012-05-29

   }

 }
//CCEnd by leixiao 2009-4-1 ԭ�򣺽����������·��,�ձϷ���׼��޸�����


//CCBegin by liunan 2011-09-21 �շ�֪ͨ�顣
  /**
   * Ϊָ����׼�������շ����͵������Ϊ�ѷ���״̬����׼֪ͨ��ţ�
   * @param routeListBsoID ��׼֪ͨ��BsoID
   */
  public void setRouteDisaffirmPartsState(String routeListBsoID) throws QMException
  {
    if(routeListBsoID==null || routeListBsoID.equals(""))
    {
      throw new QMException("����TechnicsRouteService����routeListNumberΪ��");
    }
    PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
    LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.getService("LifeCycleService");

    LifeCycleTemplateInfo lct = lservice.getLifeCycleTemplate("���������㲿����������");
    
    //CCBegin SS23
    LifeCycleTemplateInfo huidaolct = lservice.getLifeCycleTemplate("�ص��㲿����������");
    //CCEnd SS23
    
    //CCBegin by liunan 2012-05-29 ��øü���EPM�ĵ���������״̬��
    LifeCycleTemplateInfo lctEpm = lservice.getLifeCycleTemplate("ͼ����������");
    //CCEnd by liunan 2012-05-29
		  
    TechnicsRouteListIfc routelist = (TechnicsRouteListIfc)ps.refreshInfo(routeListBsoID,false);
    HashMap map = new HashMap();
    Collection coll = this.getRouteListLinkParts(routelist);
    System.out.println("coll============="+coll);
    for (Iterator iter = coll.iterator(); iter.hasNext(); )
    {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
      QMPartIfc  partInfo=listLinkInfo.getPartBranchInfo();
      //CCBegin SS23
      partInfo = (QMPartIfc)ps.refreshInfo(partInfo.getBsoID(),false);
      //CCEnd SS23
      System.out.println("partInfo============="+partInfo);
      if(partInfo!=null)
      {
      	//CCBegin SS23
      	//lservice.reassign(partInfo, lct);
      	LifeCycleTemplateInfo tempInfo = (LifeCycleTemplateInfo)ps.refreshInfo(partInfo.getLifeCycleTemplate(), false);
      	if(tempInfo!=null)
      	{
      		String tempName = tempInfo.getLifeCycleName();
      		if(tempName.equals("�ص��㲿����������"))
      		{
      			lservice.reassign(partInfo, huidaolct);
      		}
      		else
      		{
      			lservice.reassign(partInfo, lct);
      		}
      	}
      	//CCEnd SS23
      	
      	lservice.setLifeCycleState(partInfo, LifeCycleState.toLifeCycleState("DISAFFIRM"));    
      }
      
      
     //CCBegin by liunan 2012-05-29 ��øü���EPM�ĵ���������״̬��
     QMQuery query = new QMQuery("EPMBuildHistory");
     QueryCondition condition = new QueryCondition("rightBsoID","=",partInfo.getBsoID());
     query.addCondition(condition);
     Collection coll1 = ps.findValueInfo(query);
     Iterator iter1 = coll1.iterator();
     if(iter1.hasNext())
     {
     	 EPMBuildHistoryIfc link = (EPMBuildHistoryIfc)iter1.next();
     	 EPMDocumentIfc doc = (EPMDocumentIfc)link.getBuiltBy();
     	 if(doc!=null)
     	 {
     	 	lservice.reassign(doc, lctEpm);
     	 	 lservice.setLifeCycleState(doc, LifeCycleState.toLifeCycleState("DISAFFIRM"));
     	 }
     }
     //CCEnd by liunan 2012-05-29
     
    }
  }
//CCEnd by liunan 2011-09-21
 


 public Collection getNeededCollForReport(Collection partMasterIDColl) {
	    return getNeededCollForReport(partMasterIDColl, null);
	  }

	  public Collection getNeededCollForReport(Collection partMasterIDColl,
	                                           TechnicsRouteListInfo routeList) {
	    try {
	      int i = 1;
	      PersistService pservice = (PersistService) EJBServiceHelper.
	          getPersistService();
	      Collection back = new ArrayList();
	      String useforMainProduct = "";
	      if (routeList != null) {
	        QMPartMasterIfc mainProductInfo = (QMPartMasterIfc) pservice.
	            refreshInfo(
	            routeList.getProductMasterID());
	        useforMainProduct = mainProductInfo.getPartNumber();
	      }
	      for (Iterator iter = partMasterIDColl.iterator(); iter.hasNext(); ) {
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
	        QMPartMasterIfc partMasterInfo = (QMPartMasterIfc) pservice.refreshInfo(
	            partmasterID);
	        partNum = partMasterInfo.getPartNumber();
	        partName = partMasterInfo.getPartName();

	        QMQuery query = new QMQuery("ListRoutePartLink");
	        QueryCondition cond1 = new QueryCondition("rightBsoID",
	                                                  QueryCondition.EQUAL,
	                                                  partMasterInfo.getBsoID());
	        query.addCondition(cond1);
	        if (routeList != null) {
	          QueryCondition cond2 = new QueryCondition("leftBsoID",
	              QueryCondition.EQUAL,
	              routeList.getBsoID());
	          query.addAND();
	          query.addCondition(cond2);
	        }

	        Collection coll = pservice.findValueInfo(query);
	        for (Iterator iter1 = coll.iterator(); iter1.hasNext(); ) {
	          ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter1.next();
	          if (linkInfo.getRouteID() == null) {
	            continue;
	          }
	          TechnicsRouteListInfo routeList1 = (TechnicsRouteListInfo) pservice.
	              refreshInfo(linkInfo.getRouteListID());
	          QMPartMasterIfc mainProductInfo = (QMPartMasterIfc) pservice.
	              refreshInfo(routeList1.getProductMasterID());
	          useforMainProduct = mainProductInfo.getPartNumber();
	          Object[] objs = getRouteAndBrach(linkInfo.getRouteID());
	          TechnicsRouteIfc route = (TechnicsRouteIfc) objs[0];
	          HashMap map = (HashMap) objs[1];
	          if (map.size() > 0) {
	            desc = route.getRouteDescription();
	            modifySign = route.getModefyIdenty().getCodeContent();
	            if (route.getDefaultDescreption() != null) {
	              youXiaoQi = route.getDefaultDescreption();
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
	        ArrayList allRoute = getRouteInformation(produceRoute1, setupRoute1,
	                                                 temRoute1);
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
	    }
	    catch (QMException ex) {
	      ex.printStackTrace();
	      return null;
	    }

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
String[] aa = {
produceRoute, setupRoute, temp};
hehe.add(aa);
}
return hehe;
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
//System.out.println("tempRoute " + tempRoute);
//System.out.println("produceRoute " + produceRoute);
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


//              CCEnd by leixiao 2008-9-12 ԭ�򣺽������,�ձ�֪ͨ��
//CCBegin by leixiao 2008-10-07 ԭ�򣺽������,��׼֪ͨ������
/**
 * Ϊָ����׼�����в���״̬��������IBA����ֵ����׼֪ͨ��ţ�
 * @param routeListBsoID ��׼֪ͨ��BsoID
 * 20070115 liuming add
 */
public void addIBAvalueForPart(String routeListBsoID) throws QMException
{
  if(routeListBsoID==null || routeListBsoID.equals(""))
  {
    throw new QMException("����TechnicsRouteService����addIBAvalueForPart����routeListNumberΪ��");
  }
  PartConfigSpecIfc cpnfigspec = this.getCurrentUserConfigSpec();
  PersistService ps = (PersistService) EJBServiceHelper.getService(
      "PersistService");

  TechnicsRouteListIfc routelist = (TechnicsRouteListIfc)ps.refreshInfo(routeListBsoID,false);
  HashMap map = new HashMap();
  Collection coll = this.getRouteListLinkParts(routelist);
  for (Iterator iter = coll.iterator(); iter.hasNext(); )
  {
    //�㲿������
    ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
    String partMasterID = listLinkInfo.getRightBsoID();
    BaseValueIfc partInfo = (BaseValueIfc)getLastedPartByConfig(partMasterID,cpnfigspec);
    if(partInfo instanceof QMPartIfc)
    {
      String status = listLinkInfo.getAdoptStatus();
      if (status.equals(RouteAdoptedType.ADOPT.getDisplay()))
      {
        if(map.containsKey(partMasterID))
        {
          continue;
        }
        else
        {
          map.put(partMasterID,partInfo);
        }
      }
    }

  }

  if(map.size()==0)
  {
    System.out.println("û�ҵ������������㲿��");
    return;
  }

  //����iba���Զ���
  QMQuery query = new QMQuery("StringDefinition");
  QueryCondition cond = new QueryCondition("name", "=", "��׼֪ͨ���");
  query.addCondition(cond);
  Collection queryresult = ps.findValueInfo(query);
  AttributeDefinitionIfc definition = null; //���Զ���
  AttributeDefDefaultView defaultView = null; //������ͼ
  if (queryresult.iterator().hasNext())
  {
    definition = (AttributeDefinitionIfc) queryresult.iterator().next();
    //System.out.println("�ҵ������Զ���:"+definition.getBsoID());
    defaultView = IBADefinitionObjectsFactory.newAttributeDefDefaultView(definition);
  }
  else
  {
    System.out.println("û�ҵ����Զ��壡");
    return;
  }

  //�����ַ����͵�����ֵ
  StringValueDefaultView stringValueView = new StringValueDefaultView(
      (StringDefView) defaultView, routelist.getRouteListNumber());

  AbstractValueIfc abstractvalueIfc = null;
  IBAValueService vs = (IBAValueService) EJBServiceHelper.getService(
      "IBAValueService");

  for (Iterator it = map.values().iterator();it.hasNext();)
  {
    QMPartIfc holder = (QMPartIfc)it.next();
    //System.out.println("���������" + holder.getPartNumber() + " " +
     //                    holder.getBsoID());
    //��ȡ���������ߵ���������
    if (holder.getAttributeContainer() == null) {
      holder = (QMPartIfc) vs.refreshAttributeContainer(holder, null, null,
          null);
    }
    DefaultAttributeContainer attrCont = (DefaultAttributeContainer) holder
        .getAttributeContainer();
    if(attrCont==null)
    {
      continue;
    }
    AbstractValueView[] values = attrCont.getAttributeValues();
    boolean flag = false;
    for (int index = 0; index < values.length; index++) {
      AbstractValueView value = values[index];
      if (value.getDefinition().getName().equals(definition.getName())) {
        String id = value.getBsoID();
        StringValueIfc svalue = (StringValueIfc)ps.refreshInfo(id);
        svalue.setValue(routelist.getRouteListNumber());
        ps.updateValueInfo(svalue, false);
        flag = true;
        break;
      }
    }
    if (!flag) {
      abstractvalueIfc = IBAValueObjectsFactory.newAttributeValue(
          stringValueView, holder);
      abstractvalueIfc = (AbstractValueIfc) ps.storeValueInfo(
          abstractvalueIfc);
      stringValueView.setModifyTime(abstractvalueIfc.getModifyTime());
      stringValueView.setBsoID(abstractvalueIfc.getBsoID());
      attrCont.addAttributeValue(stringValueView); //��������ֵ
      try {
        ps.updateValueInfo(holder, false); //�����㲿��
      }
      catch (Exception e) {
        System.out.println("�޷����������" + holder.getPartNumber() + " " +
                           holder.getBsoID());
      }
    }
  }
  map=null;
}
//CCEnd by leixiao 2008-9-12 ԭ�򣺽������,��׼֪ͨ��
//CCBegin by leixiao 2008-9-12 ԭ�򣺽������,��׼֪ͨ��
/**
 * ������ 20061209 liuming add
 * @param masterInfo TechnicsRouteListMasterIfc
 * @return TechnicsRouteListMasterIfc
 * @throws QMException
 */
public TechnicsRouteListMasterIfc renameRouteListMaster(TechnicsRouteListMasterIfc
        masterInfo)
        throws QMException
{

    //�������Ϊ�գ��׳��쳣
    if (masterInfo == null)
    {
        throw new QMException("������쳣:TechnicsRouteService.renameRouteListMaster ERROR!");
    }
    try
    {
        PersistService persistService = (PersistService) EJBServiceHelper.
                                        getPersistService();
        TechnicsRouteListMaster master = (TechnicsRouteListMaster) persistService.
                                  refreshBso(masterInfo.getBsoID());

        Timestamp timestamp = master.getModifyTime();
        masterInfo.setModifyTime(timestamp);
        masterInfo = (TechnicsRouteListMasterIfc) persistService.
                              updateValueInfo(masterInfo);
        VersionControlService vservice = (VersionControlService)EJBServiceHelper.getService(VERSION_SERVICE);
        //���Ը�ҵ������Ƿ����޸�Ȩ��
         AccessControlService acs=(AccessControlService)EJBServiceHelper.getService("AccessControlService");
        //���÷���VersionControlService�ķ���,����master���õ���������
        //���е�С�汾��������ͬ��֦����Ҳ����master��Ӧ������С�汾��
        Collection versions = vservice.allIterationsOf(masterInfo);
        for(Iterator it = versions.iterator();it.hasNext();)
        {
          TechnicsRouteListInfo info = (TechnicsRouteListInfo)it.next();
          if(acs.checkAccess(info,QMPermission.MODIFY))
          {
            info.setRouteListName(masterInfo.getRouteListName());
            info.setRouteListNumber(masterInfo.getRouteListNumber());
            TechnicsRouteList list = (TechnicsRouteList) persistService.
                refreshBso(info.getBsoID());
            Timestamp time = list.getModifyTime();
            info.setModifyTime(time);
            persistService.updateValueInfo(info,false);
          }
        }
    }
    catch (QMException ex)
    {
        ex.printStackTrace();
        setRollbackOnly();
        throw ex;
    }

    return masterInfo;

  }



public void importSaveRoute(ListRoutePartLinkIfc listLinkInfo,
        HashMap routeRelaventObejts) throws
QMException {

String routeListID = listLinkInfo.getRouteListID();
String parentPartNum = listLinkInfo.getParentPartNum();
String partMasterID = listLinkInfo.getPartMasterID();
ListRoutePartLinkIfc listLinkInfo1 = getListRoutePartLink(routeListID,
partMasterID, parentPartNum);
if (listLinkInfo1 == null) {
SaveRouteHandler.doSave(listLinkInfo, routeRelaventObejts);
}
else {
SaveRouteHandler.impoartDoSave(listLinkInfo1, routeRelaventObejts);
}

}

public TechnicsRouteListInfo getRouteListbyRouteListNum(String routeListNum) throws
QMException {
PersistService service = (PersistService) EJBServiceHelper.
  getPersistService();
QMQuery query = new QMQuery("TechnicsRouteList");
query.addCondition(new QueryCondition("routeListNumber", "=",
                                    routeListNum));
Collection coll = service.findValueInfo(query);
for (Iterator iterator = coll.iterator(); iterator.hasNext(); ) {
TechnicsRouteListInfo routeList = (TechnicsRouteListInfo)
    iterator.next();
return routeList;
}
return null;
}


private String getDate() {
    long aa = System.currentTimeMillis();
    java.sql.Timestamp time = new java.sql.Timestamp(aa);
    String time1 = time.toString().substring(0, 10);
    java.util.StringTokenizer af = new java.util.StringTokenizer(time1, "-");
    String haha = af.nextToken() + "��" + af.nextToken() + "��" + af.nextToken() +
        "��";
    return haha;
  }

private void writeReportMainInformation(Collection routeInformationColl,
        StringBuffer backString) {
for (Iterator iter1 = routeInformationColl.iterator(); iter1.hasNext(); ) {
ArrayList hehe = (ArrayList) iter1.next();
ArrayList route1 = (ArrayList) hehe.get(4);
//    String[] route = (String[]) hehe.get(4);
if (route1.size() < 1) {
String imformation = (String) hehe.get(0) + "," +
(String) hehe.get(1) + "," +
(String) hehe.get(2) + ","
+ (String) hehe.get(3) + "," + "" + "," + "" + "," +
"" + "," + (String) hehe.get(5) + "," +
(String) hehe.get(6) + (String) hehe.get(7) + (String) hehe.get(8) +
"\n";
backString.append(imformation);

}

if (route1.size() == 1) {
String produceRoute = ( (String[]) route1.get(0))[0];
if (produceRoute == null || produceRoute.length() < 1) {
produceRoute = "";
}
String setupRoute = ( (String[]) route1.get(0))[1];
if (setupRoute == null || setupRoute.length() < 1) {
setupRoute = "";
}

String temRoute = ( (String[]) route1.get(0))[2];
if (temRoute == null || temRoute.length() < 1) {
temRoute = "";
}

String imformation = (String) hehe.get(0) + "," +
(String) hehe.get(1) + "," +
(String) hehe.get(2) + ","
+ (String) hehe.get(3) + "," + produceRoute +
"," + setupRoute + "," +
temRoute + "," + (String) hehe.get(5) + "," +
(String) hehe.get(6) + "," + (String) hehe.get(7) + "," +
(String) hehe.get(8) + "\n";
backString.append(imformation);
}
if (route1.size() > 1) {
for (int i = 0; i < route1.size(); i++) {
if (i == 1) {
String produceRoute = ( (String[]) route1.get(1))[0];
String assmRoute = ( (String[]) route1.get(1))[1];
String temRoute = ( (String[]) route1.get(1))[2];
String imformation = (String) hehe.get(0) + "," +
(String) hehe.get(1) + "," +
(String) hehe.get(2) + ","
+ (String) hehe.get(3) + "," + produceRoute +
"," + assmRoute + "," +
temRoute + "," + (String) hehe.get(5) + "," +
(String) hehe.get(6) + "," + (String) hehe.get(7) +
"," +
(String) hehe.get(8) + "\n";
backString.append(imformation);
}
else {
String produceRoute = ( (String[]) route1.get(i))[0];
String assmRoute = ( (String[]) route1.get(i))[1];
String temRoute = ( (String[]) route1.get(i))[2];
String imformation = ",,,," + produceRoute +
"," + assmRoute + "," +
temRoute + "," +
"," + ",," + "\n";
backString.append(imformation);
}

}
}

}
}

public void setRouteListPartsState(String routeListID) {
    try {
      PersistService perService = (PersistService) EJBServiceHelper.
          getPersistService();
      LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.
          getService("LifeCycleService");
      TechnicsRouteListInfo routeList = (TechnicsRouteListInfo)
          perService.refreshInfo(
          routeListID);
      String routeListState = routeList.getRouteListState();
      Collection partsColl = (Collection) getRouteListLinkParts(routeList);
      for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
        ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
            iter.next();
        String partMasterID = routePartLink.getRightBsoID();
        QMPartInfo partInfo = (QMPartInfo) getLastedPartByConfig(partMasterID,getCurrentUserConfigSpec());
        /*
             if (routeListState.equals("��׼") || routeListState.equals("ǰ׼")) {
              // lservice.setLifeCycleState(partInfo, State.toState("SHIZHI"));
         lservice.setLifeCycleState(partInfo, State.toState("MANUFACTURING"));
             }
             if (routeListState.equals("��׼")) {
            //  lservice.setLifeCycleState(partInfo, State.toState("PREPARING"));
         lservice.setLifeCycleState(partInfo, State.toState("MANUFACTURING"));
             }*/
        //CCBegin by leixiao 2009-11-27 ԭ�򣺽����������·��
        partInfo.setLifeCycleState(LifeCycleState.toLifeCycleState("PREPARING"));
        //CCEnd by leixiao 2009-11-27 ԭ�򣺽����������·��
        perService.updateValueInfo(partInfo, false);
      }
    }
    catch (QMException ex) {
      ex.printStackTrace();
    }
}



//CCEnd by leixiao 2008-9-12 ԭ�򣺽������,��׼֪ͨ��
//CCBegin by leixiao 2008-11-10 ԭ�򣺽������,��׼֪ͨ��ѡ�����ʱ����ʾ�ü��������׼
/**
 * �õ��㲿����·�ߴ���Ϣ
 * @param part QMPartInfo
 * @throws QMException
 * @return String
 * gcy Add in 2005-08-08 for jiefang
 */
public String getRouteText(QMPartInfo part) throws QMException {

  Collection coll = getRoutesAndLinks(part);
  if (coll == null || coll.size() == 0) {
    return "N/A";
  }
  Iterator i = coll.iterator();
  String routeText = "";
  while (i.hasNext()) {
    Object[] objs = (Object[]) i.next();
    ListRoutePartLinkInfo info = (ListRoutePartLinkInfo) objs[0];
    TechnicsRouteListInfo routelist = (TechnicsRouteListInfo) objs[1];
    HashMap map = (HashMap) getRouteBranchs(info.getRouteID());

    if (map == null || map.size() == 0) {
      continue;
    }
    routeText = routeText + ";" + routelist.getRouteListNumber();
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
      routeText = routeText + "," + makeStr;
    }
  }
  if (routeText.trim().equals("")) {
    return "N/A";
  }
  return routeText;
}
//CCEnd by leixiao 2008-11-10 ԭ�򣺽������
//CCBegin by leixiao 2009-4-14 ԭ�򣺽������
public ArrayList getcompleteroutepart(String routeid)throws QMException{
	ArrayList completepart=new ArrayList();
	String pversion="";
	String pversionvalue="";
	  PersistService pService = (PersistService) EJBServiceHelper.
	  getPersistService();
		QMQuery query = new QMQuery("StringDefinition");
		QueryCondition qc = new QueryCondition("displayName", " = ", "����Դ�汾");
		query.addCondition(qc);
		Collection col = pService.findValueInfo(query, false);
		Iterator iba = col.iterator();
		
		if (iba.hasNext()) {
			StringDefinitionIfc s = (StringDefinitionIfc) iba.next();
			pversion = s.getBsoID();
		}
	QMQuery query1 =new QMQuery("ListRoutePartLink");
    QueryCondition cond3 = new QueryCondition("leftBsoID","=",
    		routeid);
      query1.addCondition(cond3);
	   Collection coll1 = pService.findValueInfo(query1);
	    Iterator iter = coll1.iterator();
		  while(iter.hasNext()){
			  ListRoutePartLinkIfc link=(ListRoutePartLinkIfc)iter.next();
			  QMPartIfc part=link.getPartBranchInfo();
			  String partnumber=part.getPartNumber();
				QMQuery query2 = new QMQuery("StringValue");
				query2.addCondition(new QueryCondition("iBAHolderBsoID", "=",
						part.getBsoID()));

				query2.addAND();
				query2.addCondition(new QueryCondition("definitionBsoID", "=",
						pversion));
				Collection ibavalue = pService.findValueInfo(query2, false);
				Iterator value = ibavalue.iterator();
				if(value.hasNext()){
		        	StringValueIfc s=(StringValueIfc)value.next();
		        	pversionvalue=s.getValue();
				
				}
				if(!pversionvalue.trim().equals("")&&pversionvalue!=null){
					//CCBegin SS2
				//String [] partvalue={partnumber,pversionvalue};
				String [] partvalue={partnumber,pversionvalue,part.getVersionValue()};
				//CCEnd SS2
				completepart.add(partvalue);
				}
		  }
	return completepart;
}
//CCEnd by leixiao 2009-4-14 ԭ�򣺽������
public void handleversion()throws QMException{

	String beginString="2008-12-30 00";
	String endString="2009-01-09 00";
	 String begin = getValidTime(beginString.replace('/', '-').trim(), true);
	 String end = getValidTime(endString.replace('/', '-').trim(), false);
	 System.out.println(" begin="+begin+" end="+end);

	  PersistService pService = (PersistService) EJBServiceHelper.
	  getPersistService();
	    QMQuery query = new QMQuery(ROUTELIST_BSONAME);
	    Timestamp beginTime = Timestamp.valueOf(begin);
	    Timestamp endTime = Timestamp.valueOf(end);
	    QueryCondition cond1 = new QueryCondition("modifyTime",
	                                              QueryCondition.
	                                              GREATER_THAN_OR_EQUAL, beginTime);
	    query.addCondition(cond1);
	    query.addAND();
	    QueryCondition cond2 = new QueryCondition("modifyTime",
	                                              QueryCondition.LESS_THAN, endTime);
	    query.addCondition(cond2);

    Collection coll = pService.findValueInfo(query);
    Iterator iterator = coll.iterator();
    while(iterator.hasNext()){
    	TechnicsRouteListInfo routelist=(TechnicsRouteListInfo)iterator.next();
    	System.out.println("----routelist="+routelist+"---"+routelist.getRouteListNumber()+""+routelist.getRouteListName());
    	QMQuery query1 =new QMQuery("ListRoutePartLink");
	    QueryCondition cond3 = new QueryCondition("leftBsoID","=",
	    		routelist.getBsoID());
	      query1.addCondition(cond3);
		   Collection coll1 = pService.findValueInfo(query1);
		    Iterator iter = coll1.iterator();
			  while(iter.hasNext()){
				  ListRoutePartLinkIfc link=(ListRoutePartLinkIfc)iter.next();
				 // System.out.println("-----link="+link);
				  String partmasterid=link.getRightBsoID();
				 // System.out.println("-----link="+partmasterid);
				  QMPartMasterInfo master=null;
				  try{
				  if(partmasterid!=null)
				   master=(QMPartMasterInfo)pService.refreshInfo(partmasterid);
				  //System.out.println("---master="+master);
				  String preString="";
				  if(master!=null){
				  String version =getibaPartVersion(master);

				  System.out.println("------����ԭ�汾��"+version);
				   preString="("+version+")";
				  }
				  String routeid=link.getRouteID();
				  //System.out.println("  ·��routeid="+routeid);
				  TechnicsRouteIfc routeifc=null;
				  if(routeid!=null)
				  routeifc=(TechnicsRouteIfc)pService.refreshInfo(routeid);
				  if(routeifc!=null){
				  String describ=routeifc.getRouteDescription();
				 // System.out.println("----------ԭʼ˵����"+describ);
			        if(describ!=null){
			            if(describ.startsWith("(")&&describ.indexOf(")")!=-1){
			            	describ=describ.substring(describ.indexOf(")")+1,describ.length());
			            }
			        }
			        else{
			        	describ="";
			        }
			        String describ1=preString+describ;
			        //System.out.println("---"+describ);
			        if(!describ.equals(describ1)){
			      System.out.println("----------ԭʼ˵����"+describ+"  ��˵��:"+describ1);
				  routeifc.setRouteDescription(describ1);
				   pService.saveValueInfo(routeifc);
				   System.out.println("----���º��˵��"+routeifc.getRouteDescription());
			        }
				  }
				  }
				  catch(Exception ex1){
					  ex1.printStackTrace();
				  }


			  }

    }

}


public void handleversion1()throws QMException{


	String beginString="2008-12-30 00";
	String endString="2009-01-15 00";
	 String begin = getValidTime(beginString.replace('/', '-').trim(), true);
	 String end = getValidTime(endString.replace('/', '-').trim(), false);
	 System.out.println(" begin="+begin+" end="+end);

	  PersistService pService = (PersistService) EJBServiceHelper.
	  getPersistService();
	    QMQuery query = new QMQuery(ROUTELIST_BSONAME);
	    Timestamp beginTime = Timestamp.valueOf(begin);
	    Timestamp endTime = Timestamp.valueOf(end);
	    QueryCondition cond1 = new QueryCondition("modifyTime",
	                                              QueryCondition.
	                                              GREATER_THAN_OR_EQUAL, beginTime);
	    query.addCondition(cond1);
	    query.addAND();
	    QueryCondition cond2 = new QueryCondition("modifyTime",
	                                              QueryCondition.LESS_THAN, endTime);
	    query.addCondition(cond2);

    Collection coll = pService.findValueInfo(query);
    Iterator iterator = coll.iterator();
    while(iterator.hasNext()){
    	TechnicsRouteListInfo routelist=(TechnicsRouteListInfo)iterator.next();
    	System.out.println("----routelist="+routelist+"---"+routelist.getRouteListNumber()+""+routelist.getRouteListName());
    	QMQuery query1 =new QMQuery("ListRoutePartLink");
	    QueryCondition cond3 = new QueryCondition("leftBsoID","=",
	    		routelist.getBsoID());
	      query1.addCondition(cond3);
		   Collection coll1 = pService.findValueInfo(query1);
		    Iterator iter = coll1.iterator();
			  while(iter.hasNext()){
				  ListRoutePartLinkIfc link=(ListRoutePartLinkIfc)iter.next();
				 // System.out.println("-----link="+link);
				  String partmasterid=link.getRightBsoID();
				  System.out.println("-----link="+partmasterid);

				  QMPartMasterInfo master=null;
				  try{
				  if(partmasterid!=null)
				   master=(QMPartMasterInfo)pService.refreshInfo(partmasterid);
				  //System.out.println("---master="+master);
				  String preString="";
				  if(master!=null){
					  QMPartIfc part= getLastedPartofsz(master,null);
					  System.out.println("part="+part);
					  link.setPartBranchID(part.getBsoID());
					  pService.saveValueInfo(link);
				  }

				  }
				  catch(Exception ex1){
					  ex1.printStackTrace();
				  }


			  }

    }

    System.out.println("���ݴ�����ɣ�");
}


private static String getValidTime(String time, boolean flag) throws
QMException {
if (TechnicsRouteServiceEJB.VERBOSE) {
System.out.println(TechnicsRouteServiceEJB.TIME
                   + "enter routeService's getValidTime : time = " + time
                   + ", flag = " + flag);
}
String convert = null;
String day_begin_time = " 00:00:00.000";
String day_time = ":00:00.000";
String day_time_end = ":60:00.000";
String day_end_time = " 24:00:00.000";
StringTokenizer toke = new StringTokenizer(time, "-");
int i = toke.countTokens() - 1;
if (time.indexOf(" ") != -1) {
if (flag) {
  convert = time + day_time;
}
else {
  convert = time + day_time_end;
}
}
else if (i == 2) {
if (flag) {
  convert = time + day_begin_time;
}
else {
  convert = time + day_end_time;
}
}
else if (i == 1) {
String s1 = "-1";
String s2 = null;
if (flag) {
  convert = time + s1 + day_begin_time;
}
else {
  String s3 = time.substring(time.indexOf("-") + 1).trim();
  int k = s3.indexOf("0");
  if (k == 0) {
    s3 = s3.substring(1);
  }
  if (TechnicsRouteServiceEJB.VERBOSE) {
    System.out
        .println("enter routeService's getValidTime �·�s3 = "
                 + s3);
  }
  if (s3.trim().equals("1") || s3.trim().equals("3")
      || s3.trim().equals("5") || s3.trim().equals("7")
      || s3.trim().equals("8") || s3.trim().equals("10")
      || s3.trim().equals("12")) {
    s2 = "-31";
  }
  else if (s3.trim().equals("2")) {
    String year = time.substring(0, time.indexOf("-"));
    int b = Integer.parseInt(year);
    int m = b % 4;
    //�ж����ꡣ
    if (m == 0) {
      s2 = "-29";
    }
    else {
      s2 = "-28";
    }
  }
  else {
    s2 = "-30";
  }
  convert = time + s2 + day_end_time;
}
}
else if (i == 0) {
String s1 = "-1-1";
String s2 = "-12-31";
if (flag) {
  convert = time + s1 + day_begin_time;
}
else {
  convert = time + s2 + day_end_time;
}
}
else {
throw new TechnicsRouteException("����ʱ��������������⡣");
}
return convert;
}
//CCBegin by liujiakun ���ն���
/**
 
 * @param info String
 * @throws QMException
 * @return String[] 1,·��״̬��2����·�ߡ�3����·��
 */
public String[] getRouteTextByMaster(String info) throws
    QMException {
  String[] routetext = new String[3];
  String routestate = "";
  String newroute = "";
  String oldroute = "";
  //System.out.println("info="+info);
  //System.out.println("1=");
  ListRoutePartLinkInfo linkinfo = getCurrentRouteLinkByPartID(info);
  
  //System.out.println("linkinfo="+linkinfo);
  if (linkinfo != null) {
    if (linkinfo.getRouteID() != null && !linkinfo.getRouteID().equals("")) {
      newroute = getRouteTextByRouteID(linkinfo.getRouteID());
      //���·��״̬
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      TechnicsRouteInfo route = (TechnicsRouteInfo) pservice.refreshInfo(
          linkinfo.getRouteID());
      //System.out.println("route="+route);
      //liujiakun ��ԭ����CodingIfc����ת��Ϊ�ַ�������
      routestate = route.getModefyIdenty().toString();
      //���ԭ��·��
     /*
      String lastLinkID = linkinfo.getLastEffRoute();
      if (lastLinkID != null && lastLinkID.length() > 0) {
        ListRoutePartLinkIfc lastlink = (ListRoutePartLinkIfc) pservice.
            refreshInfo(lastLinkID);
        if (lastlink.getRouteID() != null &&
            lastlink.getRouteID().length() > 0) {
          //ԭ��·��
          oldroute = getRouteTextByRouteID(lastlink.getRouteID());
        }
      }
      */
    }
  }
  routetext[0] = routestate;
  routetext[1] = newroute;
  //routetext[2] = oldroute;
  return routetext;
}

/**
 * tangshutao add for qingqi 2007.09.17
 * ����·�߱����㲿������ֵ���󼯺ϻ��ÿ���㲿����·�ߴ�
 * @param coll Collection ·�߱����㲿������ֵ���󼯺�
 * @throws QMException
 * @return Vector  ������飬ÿ����������Ԫ�أ�{�㲿����ţ��㲿�����ƣ�·�ߴ�}
 */
public Vector getRouteTextByLinkCollection(Vector coll) throws
    QMException {
  Vector v = new Vector();
  if (coll == null || coll.size() == 0) {
    return null;
  }
  Iterator i = coll.iterator();
  while (i.hasNext()) {
    ListRoutePartLinkInfo info = (ListRoutePartLinkInfo) i.next();
    String partmasterid = info.getRightBsoID();
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();

    QMPartMasterIfc partmasterInfo = (QMPartMasterIfc) pservice.refreshInfo(
        partmasterid);
    //�������С�汾
    VersionControlService fservice = (VersionControlService) EJBServiceHelper.
        getService(
        "VersionControlService");
    Collection col = (Collection) fservice.allVersionsOf(partmasterInfo);
    QMPartIfc partifc = (QMPartIfc) col.iterator().next();
    //��������ĵ��ĸ�λ��
    String partnumber = partmasterInfo.getPartNumber();
    String partname = partmasterInfo.getPartName();
    String routeid = info.getRouteID();
    String routetext = getRouteTextByRouteID(routeid);
    if (routetext.indexOf("Ϳ") < 0 && routetext.indexOf("��(��)") < 0 &&
        routetext.indexOf("��(��)") < 0) {
      continue;
    }
    Object[] str = new Object[4];
    str[0] = partnumber;
    str[1] = partname;
    str[2] = routetext;
    str[3] = partifc;
    v.add(str);
  }
  return v;
}

//tang 20070521
/**
 * �������õ�ǰ��Ч·�ߡ�
 * @param part QMPartIfc
 * @throws QMException
 * @return ListRoutePartLinkInfo
 */
public ListRoutePartLinkInfo getCurrentRouteLinkByPartID(String
    partMasterBsoID) throws
    QMException {
  ListRoutePartLinkInfo lrpli = null;
  try {
    PersistService pService = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery("ListRoutePartLink");
    QueryCondition qc = new QueryCondition("rightBsoID", "=",
                                           partMasterBsoID);
    query.addCondition(qc);
   // QueryCondition qc1 = new QueryCondition("currentEffctive", true);
    //query.addAND();
    //query.addCondition(qc1);
    Collection col = pService.findValueInfo(query, false);
    if (col == null || col.size() == 0) {
      return null;
    }
    Iterator ite = col.iterator();
    if (ite.hasNext()) {
      lrpli = (ListRoutePartLinkInfo) ite.next();
    }

  }
  catch (QMException e) {
    e.printStackTrace();
    throw new QMException(e);
  }
  return lrpli;

}

//����5.����·�߱�(������)
//�汾 <v2.0>
//�ĵ���ţ�PHOS-CAPP-UC305
//����6.��������·�߱�
//�汾 <v2.0>
//�ĵ���ţ�PHOS-CAPP-UC306
//CCBegin by liujiakun ���ն���
/**
 * @param param ��ά���飬5��Ԫ�ء�����obj[0]=��ţ�obj[1]=true. true=�ǣ�false=�ǡ�����˳�򣺱�š����ơ����𣨺��֣������ڲ�Ʒ���汾�š�
 * @roseuid 402CBAF700CA
 * @J2EE_METHOD  --  findRouteLists
 * ��ù���·�߱�������Χ����š����ơ��������ڲ�Ʒ���汾�š�
 * @return collection ����·�߱�ֵ�������°汾��
 * ��ʱҪ��ConfigService���й��ˡ�
 */
public Collection findRouteListsnew(Object[][] param) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(ROUTELIST_BSONAME);
    //  int n = query.appendBso(PARTMASTER_BSONAME, false);
    query.setChildQuery(false);
    String number = (String) param[0][0];
    boolean numberFlag = ( (Boolean) param[0][1]).booleanValue();
    if (number != null && number.trim().length() != 0) {
      QueryCondition cond = RouteHelper.handleWildcard("routeListNumber",
          number, numberFlag);
      query.addCondition(0, cond);
      query.addAND();
    }
    String name = (String) param[1][0];
    boolean nameFlag = ( (Boolean) param[1][1]).booleanValue();
    if (name != null && name.trim().length() != 0) {
      QueryCondition cond1 = RouteHelper.handleWildcard("routeListName",
          name,
          nameFlag);
      query.addCondition(0, cond1);
      query.addAND();
    }
    /*
         String level_zh = (String) param[2][0];
         if (level_zh != null && level_zh.trim().length() != 0) {
      String level = RouteHelper.getValue(RouteListLevelType.
                                          getRouteListLevelTypeSet(),
                                          level_zh);
      QueryCondition cond4 = new QueryCondition("routeListLevel",
                                                QueryCondition.EQUAL,
                                                level);
      query.addCondition(cond4);
      query.addAND();
         }
         String productNumber = (String) param[3][0];
         boolean productNumberFlag = ( (Boolean) param[3][1]).booleanValue();
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
         boolean versionFlag = ( (Boolean) param[4][1]).booleanValue();
         //����汾ǡ��Ϊ���°棬�����ѳ��������ϼеĶ�����
         if (version != null && version.trim().length() != 0) {
      QueryCondition cond6 = RouteHelper.handleWildcard("versionID",
          version,
          versionFlag);
      query.addCondition(0, cond6);
      query.addAND();
         }
     */
    QueryCondition cond12 = new QueryCondition("iterationIfLatest",
                                               Boolean.TRUE);
    query.addCondition(cond12);
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's findRouteLists else... clause, SQL = " +
                         query.getDebugSQL());
    }
    //added by dikef for search by create time
    String beginTime = (String) param[2][0];
    //System.out.println("beginTime="+beginTime);
    String endTime = (String) param[3][0];
    //System.out.println("endTime="+endTime);
    Timestamp beginTimestamp = null;
    Timestamp endTimestamp = null;
    /*
         String data = "2005/11/17 15:32:05";
         Timestamp ts = new Timestamp(new Date(data).getTime());
         QueryCondition qc = new QueryCondition("createTime", "<=", ts);
     */

    if (beginTime != null && beginTime.trim().length() > 0) {
      //modified by dikef
      //beginTime = beginTime + " 00:00:00";
      StringTokenizer beginST = new StringTokenizer(beginTime, "/");
      int k = beginST.countTokens();
      if (k == 4) {
        int i = beginTime.lastIndexOf("/");
        String hour = beginTime.substring(i + 1);
        beginTime = beginTime.substring(0, i) + " 00:00:00";
        //modified by dikef end
        beginTimestamp = new Timestamp(new Date(beginTime).getTime());
        beginTimestamp.setHours( (new Integer(hour)).intValue());
        QueryCondition beginTimecondition = new QueryCondition("createTime",
            ">=",
            beginTimestamp);
        query.addAND();
        query.addCondition(beginTimecondition);
      }
      else {
        beginTime = beginTime + " 00:00:00";
        beginTimestamp = new Timestamp(new Date(beginTime).getTime());
        QueryCondition beginTimecondition = new QueryCondition("createTime",
            ">=",
            beginTimestamp);
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
        //endTime = endTime + " 24:00:00";
        endTimestamp = new Timestamp(new Date(endTime).getTime());
        endTimestamp.setHours( (new Integer(hour)).intValue());
        QueryCondition endTimecondition = new QueryCondition("createTime", "<=",
            endTimestamp);
        query.addAND();
        query.addCondition(endTimecondition);
      }
      else {
        endTime = endTime + " 22:00:00";
        endTimestamp = new Timestamp(new Date(endTime).getTime());
        QueryCondition endTimecondition = new QueryCondition("createTime", "<=",
            endTimestamp);
        query.addAND();
        query.addCondition(endTimecondition);

      }
    }

    //added by dikef for search by create time
    query.setDisticnt(true);
    //addListOrderBy(query);
    //routelistֵ���󼯺ϡ�
    Collection result = pservice.findValueInfo(query);
    //���ݹ���״̬���й��ˡ�ԭ����������ͬʱ���ڡ���ù���·�߱�ֵ���󼯺ϡ�
    //filterByWorkState(result);
    //����·�߱����������С�
    //modified by dikef 20060824
    //Collection sortedVec = RouteHelper.sortedInfos(result,
    //   "getRouteListNumber", false);
    return result;
  }
  
/**
 * ���ڲ��϶���Ĺ��� 2007.11.21
 * @param coll Vector
 * @throws QMException
 * @return Vector
 */
public Vector getRationRouteTextByLinkCol(Vector coll, String department) throws
    QMException {
  Vector v = new Vector();
  if (coll == null || coll.size() == 0) {
    return null;
  }
  Iterator i = coll.iterator();
  try{
  while (i.hasNext()) {
    ListRoutePartLinkInfo info = (ListRoutePartLinkInfo) i.next();
    String partmasterid = info.getRightBsoID();
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();

    QMPartMasterIfc partmasterInfo = (QMPartMasterIfc) pservice.refreshInfo(
        partmasterid);
    //�������С�汾
    VersionControlService fservice = (VersionControlService) EJBServiceHelper.
        getService(
        "VersionControlService");
    Collection col = (Collection) fservice.allVersionsOf(partmasterInfo);
    QMPartIfc partifc = (QMPartIfc) col.iterator().next();
    //��������ĵ�5��λ��
    String partnumber = partmasterInfo.getPartNumber();
    String partname = partmasterInfo.getPartName();
    String routeid = info.getRouteID();
    String routestate = "";
    String newroute = "";
    //���·��״̬
    if(routeid!=null){
    TechnicsRouteInfo route = (TechnicsRouteInfo) pservice.refreshInfo(
        info.getRouteID());
    routestate = route.getModefyIdenty().toString();
    //���ԭ��·��
    String lastroute = "";
    /*String lastLinkID = info.getLastEffRoute();
    if (lastLinkID != null && lastLinkID.length() > 0) {
      ListRoutePartLinkIfc lastlink = (ListRoutePartLinkIfc) pservice.
          refreshInfo(lastLinkID);
      if (lastlink.getRouteID() != null &&
          lastlink.getRouteID().length() > 0) {
        //ԭ��·��
        lastroute = getRouteTextByRouteID(lastlink.getRouteID());
      }
    }*/

    newroute = getRouteTextByRouteID(routeid);

    //����ط���ʱ���ε�  ��֪��������ܵ���������liujiakun20090318
     /*
     if (!isRouteHasRation(partmasterInfo, newroute, department)) {
      continue;
    }
    */
  }
    Object[] str = new Object[8];
    str[0] = partmasterid;
    str[1] = partnumber;
    str[2] = partname;
    str[3] = routestate;
    str[4] = partifc;
    str[5] = "";
    str[6] = newroute;
    str[7] = hasValidMainRationByPart(partmasterid);
    v.add(str);
  }
  }
  catch (Exception e)
  {
	  e.printStackTrace();
  }
  return v;
}

/**
 * 2007.12.10 tangshutao
 * �жϸ������û����Ч�����Ķ���
 * @param partmasterbsoid String
 * @throws QMException
 * @return boolean
 */
private Collection hasValidMainRationByPart(String partmasterbsoid) throws
    QMException {
  QMQuery query = new QMQuery("MainMaterialRation");
  query.addCondition(new QueryCondition("leftBsoID", "=", partmasterbsoid));
  query.addAND();
  query.addCondition(new QueryCondition("isValid", true));
  PersistService service = (PersistService) EJBServiceHelper.
      getPersistService();
  Collection c = service.findValueInfo(query, false);
  if (c != null && c.size() != 0) {
    return c;
  }
  return null;
}

/**
 * tangshutao 2007.11.22 ���Ķ�����ƵĹ�������
 * ��·�߶�Ӧ�����ļ���������������и�·�ߵ�λ����ô���ڸ�·���м���·���еĵ�һ��·�ߵ�λ���ڳ����Ƿ���������Ķ�����û�����������
 * @param partmasterInfo QMPartMasterIfc
 * @param routetext String
 * @param sourcedepartment String ׼���ඨ��Ĳ��ţ����ݴ˲���ɸѡ�����
 * @return boolean
 */
private boolean isRouteHasRation(QMPartMasterIfc partmasterInfo,
        String routetext, String sourcedepartment) throws
QMException {
boolean flag = false;
String sourceShorten = "-";
//��(����)=��(װ)(��ʱ),��--��--��--Ϳ=��(װ)
//added by dikef to get the shorten incoding to the sourcedepartment
try {
PersistService pservice = (PersistService) EJBServiceHelper.
getPersistService();
QMQuery clasificationquery = new QMQuery("CodingClassification");
QueryCondition clasiCondition = new QueryCondition("codeSort", "=",
sourcedepartment);
clasificationquery.addCondition(clasiCondition);
Collection col = pservice.findValueInfo(clasificationquery);
Iterator ite = col.iterator();
if (ite.hasNext()) {
CodingClassificationIfc codingCla = (CodingClassificationIfc) ite.next();
sourceShorten = sourceShorten + codingCla.getClassSort() + "-";
QMQuery codingQuery = new QMQuery("Coding");
QueryCondition codingCondition = new QueryCondition(
"codingClassification", "=", codingCla.getBsoID());
codingQuery.addCondition(codingCondition);
Collection codingcole = pservice.findValueInfo(codingQuery);
Iterator codingIte = codingcole.iterator();
while (codingIte.hasNext()) {
CodingIfc coding = (CodingIfc) codingIte.next();
sourceShorten = sourceShorten + coding.getShorten() + "-";
}
}

}
catch (Exception e) {
e.printStackTrace();
throw new QMException(e);
}

//added by dikef end

//�ֿ�����·�ߺ�װ��·�ߡ�����·��ֻҪ��һ�����Ͼ���Ҫ���ƶ��
StringTokenizer routesToken = new StringTokenizer(routetext, ";");
while (routesToken.hasMoreTokens()) {
String singleroute = routesToken.nextElement().toString();
int index1 = singleroute.indexOf("=");
String manroute = "";
String asseroute = "";
if (index1 != -1) {
manroute = singleroute.substring(0, index1);
asseroute = singleroute.substring(index1 + 1);
}
else {
manroute = singleroute;
asseroute = "";
}
if (manroute == null) {
manroute = "";
}
if (asseroute == null) {
asseroute = "";
}

//1������·�ߵ�λ�ǡ�Э��������㲿�����������Ķ���
//2������·�ߵ�λ����(����)���������Ķ���.
if (manroute.equalsIgnoreCase("Э") ||
manroute.equalsIgnoreCase("��(����)")) {
flag = false;
continue;
}
//6���㲿��·�ߴ���������·�ߵ�λ����װ��·�ߵ�λ�������Ķ��
//7������·�ߵ�λ���������ϡ������ᡱ���������������������ϣ��ϣ������򲻱������Ķ��
if ( (!manroute.equalsIgnoreCase(""))) {
String tempmanroute = "-" + manroute + "-";
if (tempmanroute.indexOf("-��-") < 0 && tempmanroute.indexOf("-��-") < 0 &&
tempmanroute.indexOf("-��-") < 0
&& tempmanroute.indexOf("-��-") < 0 &&
tempmanroute.indexOf("-��(��)-") < 0) {
flag = false;
continue;
}
}
//����·���޵�λ��ֻ�С��ϡ������ᡱ����������������������(��)������װ��·�ߵ�λ���ơ�
if (manroute.equalsIgnoreCase("")) {
manroute = asseroute;
}
else if (!asseroute.equalsIgnoreCase("")) {
manroute = manroute + "--" + asseroute;
}

StringTokenizer manrouteToken = new StringTokenizer(manroute, "--");

while (manrouteToken.hasMoreTokens()) {
String manroutenode = manrouteToken.nextElement().toString();

//����·�ߵ�λ��λ�ǡ��ϡ������ᡱ���������������������ϣ��ϣ���������һ��·�ߵ�λ���Ʋ��϶��
//�������·�ߵ�λ����������������ͬһ��·�ߴ����������·�ߵ�λ��һ��·�ߵ�λ�������Ķ���
//(�� ���ϳ�� ��ֻ�Գ�ѹ����������Ķ���).
if (manroutenode.equalsIgnoreCase("��") ||
manroutenode.equalsIgnoreCase("��") ||
manroutenode.equalsIgnoreCase("��") ||
manroutenode.equalsIgnoreCase("��") ||
manroutenode.equalsIgnoreCase("��(��)")) {
continue;
}
//�ж��Ƿ�����ѡ���ţ����Ƿ���ƶ��
//���ŷ���
//CodingClassificationIfc department = null;
//QMQuery query = null;
//PersistService pservice = null;
try {
//System.out.println("��ʼ�����ϱ���������·��");
//pservice = (PersistService) EJBServiceHelper.
//getPersistService();
//query = new QMQuery("Coding");
//QueryCondition cond = new QueryCondition("codeContent", "=",
//manroutenode);
//query.addCondition(cond);
//Collection coll = pservice.findValueInfo(query);
//Iterator iter = coll.iterator();
//if (iter.hasNext()) {
//CodingIfc ifc = (CodingIfc) iter.next();
//department = ifc.getCodingClassification();
//}
//������Ǳ���ѡ��Ĳ��ţ��򲻱��ơ�
//if (!department.getCodeSort().equalsIgnoreCase(sourcedepartment)) {
//modified by dikef
if (sourceShorten.indexOf("-" + manroutenode + "-") < 0) {
//modified by dikef end
flag = false;
break;
}
else {
flag = true;
break;
}
}
catch (Exception ex) {
ex.printStackTrace();
}

}
//����ж���·�ߣ�����һ����Ҫ�ඨ��ͷ���true
if (flag == true) {
break;
}
}
return flag;
}
  /**
   * 2007.12.1 tangshutao ���ϸ������϶�����Ƶ��������
   * @param coll Vector
   * @throws QMException
   * @return Vector
   */
  public Vector getAssisRationRouteTextByLinkCol(Vector coll, String department) throws
      QMException {
    Vector v = new Vector();
    if (coll == null || coll.size() == 0) {
      return null;
    }
    Iterator i = coll.iterator();
    while (i.hasNext()) {
      ListRoutePartLinkInfo info = (ListRoutePartLinkInfo) i.next();
      String partmasterid = info.getRightBsoID();
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();

      QMPartMasterIfc partmasterInfo = (QMPartMasterIfc) pservice.refreshInfo(
          partmasterid);
      //�������С�汾
      VersionControlService fservice = (VersionControlService) EJBServiceHelper.
          getService(
          "VersionControlService");
      Collection col = (Collection) fservice.allVersionsOf(partmasterInfo);
      QMPartIfc partifc = (QMPartIfc) col.iterator().next();
      //��������ĵ�5��λ��
      String partnumber = partmasterInfo.getPartNumber();
      String partname = partmasterInfo.getPartName();
      String routeid = info.getRouteID();
      String routetext = getRouteTextByRouteID(routeid);
      //���·��״̬
      TechnicsRouteInfo route = (TechnicsRouteInfo) pservice.refreshInfo(
          info.getRouteID());
      String routestate = route.getModefyIdenty().toString();
      //���ԭ��·��
      String lastroute = "";
      /*String lastLinkID = info.getLastEffRoute();
      if (lastLinkID != null && lastLinkID.length() > 0) {
        ListRoutePartLinkIfc lastlink = (ListRoutePartLinkIfc) pservice.
            refreshInfo(lastLinkID);
        if (lastlink.getRouteID() != null &&
            lastlink.getRouteID().length() > 0) {
          //ԭ��·��
          lastroute = getRouteTextByRouteID(lastlink.getRouteID());
        }
      }*/

      String newroute = getRouteTextByRouteID(routeid);

      //����ط���ʱ���ε�  ��֪��������ܵ���������liujiakun20090318
      // ���ܽ������������Ҳ��һ��
      /*
      if (!isAssisRouteHasRation(partmasterInfo, routetext, department)) {
        continue;
      }
      */
      Object[] str = new Object[8];
      str[0] = partmasterInfo.getBsoID();
      str[1] = partnumber;
      str[2] = partname;
      str[3] = routestate;
      str[4] = partifc;
      str[5] = lastroute;
      str[6] = newroute;
      //�ò���Ψһ��Ч�ĸ��Ķ���
      str[7] = hasValidAssisRationByPart(partmasterid, department);
      v.add(str);
    }
    return v;

  }
  
  /**
   * 2007.12.10 tangshutao
   * �ж������ĳ��������û����Ч�ĸ��Ķ���
   * @param partmasterbsoid String
   * @param department String
   * @throws QMException
   * @return boolean
   */
  private AssisMaterialRationTotalIfc hasValidAssisRationByPart(String
      partmasterbsoid,
      String department) throws
      QMException {
    PersistService service = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query1 = new QMQuery("CodingClassification");
    query1.addCondition(new QueryCondition("codeSort", "=", department));
    Collection c1 = service.findValueInfo(query1, false);
    CodingClassificationIfc cifc = null;
    if (c1 != null) {
      cifc = (CodingClassificationIfc) c1.iterator().
          next();
    }
    QMQuery query = new QMQuery("AssisMaterialRationTotal");
    query.addCondition(new QueryCondition("partID", "=", partmasterbsoid));
    query.addAND();
    query.addCondition(new QueryCondition("isValid", true));
    query.addAND();
    query.addCondition(new QueryCondition("workShop", "=", cifc.getBsoID()));
    Collection c = service.findValueInfo(query, false);
    if (c != null && c.size() != 0) {
      return (AssisMaterialRationTotalIfc) c.iterator().next();
    }
    return null;
  }
  
  /**
   * tangshutao 2007.12.1 ����·�ߴ����˷��ϱ��Ƹ������϶�������
   * ���ÿһ��·�ߵ�λ��Ҫ��ѯ�������ĳ����Ƿ�����˸��Ķ��·�ߵ�λ�ظ��ģ�ֻȡһ��
   * @param partmasterInfo QMPartMasterIfc
   * @param routetext String
   * @return boolean
   */
  private boolean isAssisRouteHasRation(QMPartMasterIfc partmasterInfo,
                                        String routetext,
                                        String sourcedepartment) {
    boolean flag = false;
    //�ֿ�����·�ߺ�װ��·�ߡ�����·��ֻҪ��һ�����Ͼ���Ҫ���ƶ��
    System.out.println("sourcedepartment: " + sourcedepartment);
    StringTokenizer routesToken = new StringTokenizer(routetext, ";");
    while (routesToken.hasMoreTokens()) {
      String singleroute = routesToken.nextElement().toString();
      int index1 = singleroute.indexOf("=");
      String manroute = "";
      String asseroute = "";
      if (index1 != -1) {
        manroute = singleroute.substring(0, index1);
        asseroute = singleroute.substring(index1 + 1);
      }
      else {
        manroute = singleroute;
        asseroute = "";
      }
      if (manroute == null) {
        manroute = "";
      }
      if (asseroute == null) {
        asseroute = "";
      }

      //1������·�ߵ�λ�ǡ�Э��������㲿�������Ƹ��Ķ���
      //2������·�ߵ�λ����(����)�����ศ�Ķ���.
      //3������·�ߵ�λ���������ศ�Ķ���.
      if (manroute.equalsIgnoreCase("Э") ||
          manroute.equalsIgnoreCase("��(����)") ||
          manroute.equalsIgnoreCase("��")) {
        flag = false;
        break;
      }
      //����·���޵�λ��ֻ�С��ϡ������ᡱ����������������������(��)������װ��·�ߵ�λ���ơ�
      if (manroute.equalsIgnoreCase("")) {
        manroute = asseroute;
      }
      else if (!asseroute.equalsIgnoreCase("")) {
        manroute = manroute + "--" + asseroute;
      }

      StringTokenizer manrouteToken = new StringTokenizer(manroute, "--");
      while (manrouteToken.hasMoreTokens()) {
        String manroutenode = manrouteToken.nextElement().toString();

        //����·�ߵ�λ���ǡ��ϡ������ᡱ���������������������ϣ��ϣ������Ե�λ���Ʋ��϶��
        if (manroutenode.equalsIgnoreCase("��") ||
            manroutenode.equalsIgnoreCase("��") ||
            manroutenode.equalsIgnoreCase("��") ||
            manroutenode.equalsIgnoreCase("��") ||
            manroutenode.equalsIgnoreCase("��(��)")) {
          continue;
        }
        //�ж��Ƿ�����ѡ���š�
        //���ŷ���
        CodingClassificationIfc department = null;
        QMQuery query = null;
        PersistService pservice = null;
        try {
          //System.out.println("��ʼ�����ϱ���������·��");
          pservice = (PersistService) EJBServiceHelper.
              getPersistService();

          query = new QMQuery("Coding");
          QueryCondition cond = new QueryCondition("codeContent", "=",
              manroutenode);
          //System.out.println("manroutenode: "+manroutenode);
          query.addCondition(cond);
          Collection coll = pservice.findValueInfo(query);
          if (coll != null && coll.size() > 0) {
            Iterator iter = coll.iterator();
            if (iter.hasNext()) {
              CodingIfc ifc = (CodingIfc) iter.next();
              department = ifc.getCodingClassification();
              //System.out.println("department: " + department);
            }
            //����Ǳ���ѡ��Ĳ��ţ�����ơ�
            if (department.getCodeSort().equalsIgnoreCase(sourcedepartment)) {
              flag = true;
              break;
            }
          }
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }

      }
      //����ж���·�ߣ�����һ����Ҫ�ඨ��ͷ���true
      if (flag == true) {
        break;
      }
    }

    return flag;
  }
  
  /**
   * 2008.01.15 ������׼��ù��������
   * @param routeListInfo TechnicsRouteListIfc
   * @throws QMException
   * @return BaseValueIfc[]
   */
  public BaseValueIfc[] getRouteListParts(TechnicsRouteListIfc routeListInfo) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    Collection coll = pservice.navigateValueInfo(routeListInfo, "routeList",
                                                 "ListRoutePartLink", true);
    BaseValueIfc[] basevalue = new BaseValueIfc[coll.size()];
    if (coll != null && coll.size() > 0) {
      Iterator iter = coll.iterator();
      for (int i = 0; iter.hasNext(); i++) {
        basevalue[i] = (QMPartMasterIfc) iter.next();
      }
    }
    return basevalue;
  }
  
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

//CCEnd by liujiakun ���ն���

//CCBegin by liunan 2011-09-20 �����㲿�����Ƿ���·�ߣ����򷵻�������׼��Ϣ��
  /**
   *  �����㲿������·��
   * @param partMasterID String �㲿��bsoid
   * @throws QMException
   * @return Collection  ���鼯�ϡ�String[] {partID, routeID, routeState, linkID, state,
                 parentPartNum});
   */
  public String getPartRoutesNew(String partMasterID) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getPartLevelRoutes, partMasterID = " +
                         partMasterID);
    }
    if (partMasterID == null || partMasterID.trim().length() == 0) {
      throw new TechnicsRouteException("�����������");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
    QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(0, cond3);
    query.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(0, cond4);
    query.addAND();
    QueryCondition cond5 = new QueryCondition("routeID",
                                              QueryCondition.NOT_NULL);
    query.addCondition(0, cond5);
    query.addAND();
    QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
    query.addCondition(0, j, cond6);
    //SQL������������
    query.setDisticnt(true);
    //����ListRoutePartLinkIfc
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's getPartLevelRoutes SQL = " +
                         query.getDebugSQL());
    }
    Collection coll = pservice.findValueInfo(query);
    //·���޸�ʱ�併�����С�
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
    if (VERBOSE) {
      System.out.println(TIME + "��ѯ���Ϊ�� coll = " + sortedVec.size());
    }
    String result = "";
    String partID = "";
    String routeID = "";
    String routeState = "";
    String linkID = "";
    String routelistID = "";
    String state = "";
    String parentPartNum = "";
    String parentPartName = "";
    for (Iterator iter = sortedVec.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      QMPartMasterIfc part = (QMPartMasterIfc) pservice.refreshInfo(
          partMasterID);
      partID = part.getBsoID();
      routelistID = linkInfo.getLeftBsoID();
      TechnicsRouteListIfc techinicsRoute = (TechnicsRouteListIfc) pservice.
          refreshInfo(routelistID);
      state = techinicsRoute.getRouteListState();
      routeID = linkInfo.getRouteID();
      routeState = linkInfo.getAdoptStatus();
      linkID = linkInfo.getBsoID();
      parentPartNum = linkInfo.getParentPartNum();
      if(result.equals(""))
      {
      	result = techinicsRoute.getRouteListNumber();
      }
      else
      {
      	result = result+"��"+techinicsRoute.getRouteListNumber();
      }
    }
    if (VERBOSE) {
      System.out.println(TIME +
                         "exit... routeService's getPartLevelRoutes " +
                         result);
    }
    return result;
  }
//CCEnd by liunan 2011-09-20
  
  //CCBegin SS4

  /**
   * �Ƚ������汾�Ĵ�С��
   * @param s1 String
   * @param s2 String
   * @return boolean s1��ʱ����true�����򷵻�false��
   * @author liunan 2008-09-02
   */
  private boolean getPublishFlag(String s1, String s2)
  {
    if(s1.indexOf(".")<0)
    {
      s1 = s1 + ".1";
    }
    if (s1.equals(s2)) {
      return false;
    }

    //����StringTokenizer�ָ�st1
    StringTokenizer st1 = new StringTokenizer(s1, ".");
    //����StringTokenizer�ָ�st2
    StringTokenizer st2 = new StringTokenizer(s2, ".");
    int level1 = st1.countTokens();
    int level2 = st2.countTokens();
    //�����汾ֵ���ȴ�Ϊ���µ�
    if (level1 < level2) {
      return false;
    }
    if (level1 > level2) {
      return true;
    }
    String[] sarray1 = new String[level1];
    String[] sarray2 = new String[level2];
    //�ѷָ����ִ��������ַ�������
    for (int i = 0; i < level1; i++) {
      sarray1[i] = st1.nextToken();
      sarray2[i] = st2.nextToken();
    }
    //��ѭ���бȽ������е��ִ�
    for (int k = 0; k < level1; k++) {
      if (sarray1[k].length() > sarray2[k].length()) {
        return true;
      }
      if (sarray1[k].length() < sarray2[k].length()) {
        return false;
      }
      if (sarray1[k].compareTo(sarray2[k]) < 0) {
        return false;
      }
      if (sarray1[k].compareTo(sarray2[k]) > 0) {
        return true;
      }
    }
    return false;
  }
  //CCEnd SS4
  
  //CCBegin SS6
  public Collection findMultPartsByNumbers(Object[] param) throws QMException
  {
  	Collection result = null;
  	try
  	{
  		if(param!=null&&param.length>0)
  		{
  			PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
  			QMQuery query = new QMQuery("QMPartMaster");
  			for (int i = 0; i < param.length; i++)
  			{
  				if (param[i] != null && param[i].toString().trim().length() > 0)
  				{
  					QueryCondition cond = new QueryCondition("partNumber", "=", param[i].toString());
  					if(query.getConditionCount()>0)
  					{
  						query.addOR();
  					}
  					query.addCondition(cond);
  				}
  			}
  			if(query.getConditionCount()>0)
  			{
  				query.addOrderBy("partNumber",false);
  				result = pservice.findValueInfo(query, false);
  			}
  		}
  	}
  	catch (Exception e)
  	{
  		e.printStackTrace();
    }
    return result;
  }
  //CCEnd SS6
  //CCBegin SS19
  public static void setPartState(TechnicsRouteListIfc ifc) throws QMException{
	  String routeState =ifc.getRouteListState();
	  PersistService persistService = (PersistService) EJBServiceHelper
		.getPersistService();
		if (routeState.equals("ǰ׼")) {
			QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
			QueryCondition cond = new QueryCondition(LEFTID,
					QueryCondition.EQUAL, ifc.getBsoID());
			query.addCondition(cond);
			query.addAND();
			// �п������δʹ��·�ߡ�
			QueryCondition cond1 = new QueryCondition("alterStatus",
					QueryCondition.NOT_EQUAL, PARTDELETE);
			query.addCondition(cond1);
			Collection coll = persistService.findValueInfo(query);
			for (Iterator iter = coll.iterator(); iter.hasNext();) {
				ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter
						.next();
				QMPartInfo part = (QMPartInfo)persistService.refreshBso(linkInfo.getPartBranchID());
				part.setLifeCycleState("ADVANCEPREPARE");
			}
		}
		if (routeState.equals("��׼")) {
			QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
			QueryCondition cond = new QueryCondition(LEFTID,
					QueryCondition.EQUAL, ifc.getBsoID());
			query.addCondition(cond);
			query.addAND();
			// �п������δʹ��·�ߡ�
			QueryCondition cond1 = new QueryCondition("alterStatus",
					QueryCondition.NOT_EQUAL, PARTDELETE);
			query.addCondition(cond1);
			Collection coll = persistService.findValueInfo(query);
			for (Iterator iter = coll.iterator(); iter.hasNext();) {
				ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter
						.next();
				QMPartInfo part = (QMPartInfo)persistService.refreshBso(linkInfo.getPartBranchID());
				part.setLifeCycleState("PREPARING");
			}
		}
	  
  }
  //CCEnd SS19
  //CCBegin SS20
 /**
  * ���õ������㲿��
  */
  public ArrayList getAdoptNoticeRelateParts(String adoptNotice,String source,boolean flag)throws QMException{
	 
	  ArrayList list=new ArrayList();
	  if(source.trim().equals("��������")){
	
		  list=(ArrayList)getPublishRelatedParts(adoptNotice,flag);
	  }else{
		  list=(ArrayList)findJFCYPart(adoptNotice);
	  }
	  return list;
  }
//  /**
//   * �������Ĳ��õ������㲿��
//   * @param adoptNotice
//   * @return
//   * @throws QMException
//   */
//  public Collection findZXCYPart(String adoptNotice)throws QMException{
//	  PersistService persistService = (PersistService) EJBServiceHelper
//		.getPersistService();
//	    Collection CYPart= new ArrayList();
//	    QMQuery query1 = new QMQuery("Doc");
//		query1.setChildQuery(false);  
//		adoptNotice="CONFIRMATION-"+adoptNotice.trim();
//		query1 = ZXAdoptHelper.getFindDocInfoByNum(query1, adoptNotice);
//		DocLastConfigSpec doclastconfigspec = new DocLastConfigSpec();
//        doclastconfigspec.appendSearchCriteria(query1);
//        Collection col=persistService.findValueInfo(query1);
//        Iterator iter1 = col.iterator();
//        while (iter1.hasNext()) {
//        	DocInfo docInfo=(DocInfo)iter1.next();
//        	String isCaiYong = PublishHelper.isCaiYong(docInfo);
//			if (isCaiYong.trim().equals("true")) {
//				Collection CYPart1=PublishHelper.getPartCaiYong(docInfo.getBsoID());
//				for(int n=0;n<CYPart1.toArray().length;n++){
//					String[] part =(String[]) CYPart1.toArray()[n];
//					QMPartInfo partInfo=(QMPartInfo)persistService.refreshInfo(part[0]);
//					CYPart.add(partInfo);
//				}
//				
//			}
//        }
//        return CYPart;
//  }
  /**
   * �����Ʋ��õ������㲿��
   */
  public  Collection findJFCYPart(String adoptNotice)throws QMException{
	  PersistService persistService = (PersistService) EJBServiceHelper
		.getPersistService();
	    Collection CYPart= new ArrayList();
	    QMQuery query = new QMQuery("BomAdoptNotice");
        //���õ����
        if(adoptNotice.length()>0){
                query.addAND();
                query.addCondition( new QueryCondition("adoptnoticenumber", QueryCondition.EQUAL, adoptNotice));      
       
        Collection col = (Collection)persistService.findValueInfo(query); 
        Iterator iter = col.iterator();
        while (iter.hasNext()) {
        	BomAdoptNoticeInfo bomInfo = (BomAdoptNoticeInfo) iter.next();
        	BomNoticeServiceEJB service =new BomNoticeServiceEJB();
        		if (bomInfo.getPublishType().equals("�Ӳ���֪ͨ��")) {
				Collection col1 =service. getPartsFromBomSubAdoptNotice(bomInfo.getBsoID());
	        	Iterator iter1=col1.iterator();
	        	while(iter1.hasNext()){
	        		BomAdoptNoticePartLinkInfo partLinkInfo = (BomAdoptNoticePartLinkInfo) iter1
					.next();
//	        		CCBegin SS18
	        		if(partLinkInfo.getAdoptBs().equals("����")){
		        		QMPartInfo part = (QMPartInfo) persistService
						.refreshInfo(partLinkInfo.getPartID());
		        		CYPart.add(part);
	        		}
//	        		CCEnd SS18 
	        	}
			} 
	        	else if (bomInfo.getPublishType().equals("����֪ͨ��")) {
				Collection col2 = service.getParentBomSubAdoptNotice((BomAdoptNoticeInfo)bomInfo);
				if(col2!=null&&col2.size()>0){
					for(Iterator ite = col2.iterator();ite.hasNext();){
						BomAdoptNoticeIfc noticeIfc = (BomAdoptNoticeIfc)ite.next();
						Collection col3 = service.getPartsFromBomSubAdoptNotice(noticeIfc.getBsoID());
			        	Iterator iter3=col3.iterator();
			        	while(iter3.hasNext()){
			        		BomAdoptNoticePartLinkInfo partLinkInfo = (BomAdoptNoticePartLinkInfo) iter3
							.next();
//			        		CCBegin SS18
			        		if(partLinkInfo.getAdoptBs().equals("����")){
				        		QMPartInfo part = (QMPartInfo) persistService
								.refreshInfo(partLinkInfo.getPartID());
				        		CYPart.add(part);
			        		}
//			        		CCEnd SS18 
			        	}
					}
				}
			}
          }
        }  
	    
	  return CYPart;
  }
  
  //CCEnd SS20
  //CCBegin SS9
  public ArrayList getChangeRelateParts(String change,String source,boolean flag)throws QMException{
	  
	  ArrayList list=new ArrayList();
	  if(source.trim().equals("��������")){
		  list=(ArrayList) getNoticeOrChangeRelatedParts(change,flag);
	  }else{
		  list=(ArrayList)findJFBGPart(change);
	  }
	  return list; 
  }
  public  Collection findJFBGPart(String change)throws QMException{
	  PersistService persistService = (PersistService) EJBServiceHelper
		.getPersistService();
	    Collection CYPart= new ArrayList();
	    Collection BGPart= new ArrayList();
	    QMQuery query = new QMQuery("BomChangeNotice");
        //���õ����
        if(change.length()>0){
       
                query.addAND();
                query.addCondition( new QueryCondition("adoptnoticenumber", QueryCondition.EQUAL, change));      
    
        Collection col = (Collection)persistService.findValueInfo(query); 
        Iterator iter = col.iterator();
        while (iter.hasNext()) {
        	BomChangeNoticeInfo bomInfo = (BomChangeNoticeInfo) iter.next();
        	BomNoticeServiceEJB service =new BomNoticeServiceEJB();
        	if (bomInfo.getPublishType().equals("�ӱ��֪ͨ��")) {
				Collection col1 =service. getPartsFromBomSubChangeNotice(bomInfo.getBsoID());
	        	Iterator iter1=col1.iterator();
	        	while(iter1.hasNext()){
	        		BomChangeNoticePartLinkInfo partLinkInfo = (BomChangeNoticePartLinkInfo) iter1
					.next();
//	        		CCBegin SS18
	        		if(partLinkInfo.getAdoptBs().equals("����")){
	        			//CCBegin SS26
						if(!(partLinkInfo.getBz1().equals("�ṹ���")))
						{
                        //CCEnd SS26
		        		CYPart.add(partLinkInfo);
		        		//CCBegin SS26
						}
						//CCEnd SS26
	        		}
//	        		CCEnd SS18 
	        		
	        	}
			} 	else if (bomInfo.getPublishType().equals("���֪ͨ��")) {
				Collection col2 = service.getParentBomSubChangeNotice((BomChangeNoticeInfo)bomInfo);
				if(col2!=null&&col2.size()>0){
					for(Iterator ite = col2.iterator();ite.hasNext();){
						BomChangeNoticeInfo noticeIfc = (BomChangeNoticeInfo)ite.next();
						Collection col3 = service.getPartsFromBomSubChangeNotice(noticeIfc.getBsoID());
			        	Iterator iter3=col3.iterator();
			        	while(iter3.hasNext()){
			        		BomChangeNoticePartLinkInfo partLinkInfo = (BomChangeNoticePartLinkInfo) iter3
							.next();
//			        		CCBegin SS18
			        		if(partLinkInfo.getAdoptBs().equals("����")){
			        			//CCBegin SS26
								if(!(partLinkInfo.getBz1().equals("�ṹ���")))
								{
		                        //CCEnd SS26
				        		CYPart.add(partLinkInfo);
				        		//CCBegin SS26
								}
								//CCEnd SS26
			        		}
//			        		CCEnd SS18 
			        	}
					}
				}
			}
        	HashMap noAdopMap = new HashMap();
			HashMap adopMap = new HashMap();
        	if(CYPart!=null&&CYPart.size()>0){  
				for(Iterator ites = CYPart.iterator();ites.hasNext();){
					BomChangeNoticePartLinkInfo links = (BomChangeNoticePartLinkInfo)ites.next();
					QMPartInfo part = (QMPartInfo) persistService
					.refreshInfo(links.getPartID());
					if(links.getAdoptBs().equals("����")){
						if(links.getLinkPart()!=null&&!links.getLinkPart().equals("")){//���滻��
							Vector vec=(Vector)adopMap.get(links.getLinkPart());
							if(vec==null)
							{
								vec=new Vector();
							}
							vec.add(links);
							adopMap.put(links.getLinkPart(), vec);
						}else{
							String[] list = new String[8];
							list[0] = part.getMasterBsoID();
							list[1] =part.getPartNumber();
							list[2]= part.getPartName();
							list[3]=part.getVersionValue();
							list[4]= part.getViewName();
							list[5]= part.getBsoID();
							list[6]=part.getLifeCycleState()
							.getDisplay();
							list[7]="����";
							BGPart.add(list);
						}

					}else if(links.getAdoptBs().equals("������")){
						noAdopMap.put(links.getPartID(), links);
					}
				}
				//����ͨ���滻Ϊ����������
				if(adopMap!=null&&adopMap.size()>0){
					for(Iterator ite1 = adopMap.keySet().iterator();ite1.hasNext();){
						String key = (String)ite1.next();
						Vector adoptLinks = (Vector)adopMap.get(key);
						BomChangeNoticePartLinkInfo noAdoptLink = (BomChangeNoticePartLinkInfo)noAdopMap.get(key);
						if(adoptLinks!=null&&adoptLinks.size()>0){
							for(int i = 0;i<adoptLinks.size();i++){
								BomChangeNoticePartLinkInfo adoptLink = (BomChangeNoticePartLinkInfo)adoptLinks.get(i);
								QMPartInfo part = (QMPartInfo) persistService
								.refreshInfo(adoptLink.getPartID());
								String[] list = new String[8];
								list[0] = part.getMasterBsoID();
								list[1] =part.getPartNumber();
								list[2]= part.getPartName();
								list[3]=part.getVersionValue();
								list[4]= part.getViewName();
								list[5]= part.getBsoID();
								list[6]=part.getLifeCycleState()
								.getDisplay();
								list[7]="���";
								BGPart.add(list);
							}
							
							//�Ƴ�������滻�Ĳ�����
							noAdopMap.remove(key);
						}
						
					}
				}
				
				//��������ʣ�����ݣ�ֻ�в����ã�
				for(Iterator ite2 = noAdopMap.keySet().iterator();ite2.hasNext();){
					String key2 = (String)ite2.next();
					BomChangeNoticePartLinkInfo adoptLink = (BomChangeNoticePartLinkInfo)noAdopMap.get(key2);
					QMPartInfo part = (QMPartInfo) persistService
					.refreshInfo(adoptLink.getPartID());
					String[] list = new String[8];
					list[0] = part.getMasterBsoID();
					list[1] =part.getPartNumber();
					list[2]= part.getPartName();
					list[3]=part.getVersionValue();
					list[4]= part.getViewName();
					list[5]= part.getBsoID();
					list[6]=part.getLifeCycleState()
					.getDisplay();
					list[7]="ȡ��";
					BGPart.add(list);
					}	
			}
           
        	
        }
        
        }
	  return BGPart;
  }
  //CCEnd SS9
  //CCBegin SS10
  /**
   * ͨ��masterid��ȡ���°汾���㲿����
   * @param masterID masterid
   * @return QMPartInfo ���°汾���㲿��(QMPart);
   * @throws QMException
   * @see QMPartInfo
   */
  public static QMPartInfo getPartByMasterID(String masterID)
    throws QMException
  {
    QMPartInfo part=null;
    Vector ve=filterIterations(masterID);
    Object[] obj={};
    for(Iterator ite=ve.iterator();ite.hasNext();)
    {
      obj=(Object[])ite.next();
      part=(QMPartInfo)obj[0];
    }
    return part;

  }
  /**
   * ����������㲿������ϢBsoID�����㲿�����ù淶,
   * ���ط����㲿�����ù淶�ģ��ܸ�QMPartMasterIfc����������㲿���ļ��ϡ�
   * @param partMasterID �㲿������ϢBsoID��
   * @return Vector ���˳������㲿��ֵ����ļ��ϣ����û�кϸ���㲿������new Vector()��
   * @exception QMException
   */
  public static Vector  filterIterations(String partMasterID)
      throws QMException
  {
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
    
      return resultVector;
    }
    else
    {
      Iterator iterator = result.iterator();
      while(iterator.hasNext())
      {
        resultVector.addElement(iterator.next());
      }
   
      return resultVector;
    }
  }
  //CCEnd SS10
  
//CCBegin SS21
	public boolean getRouteStr(TechnicsRouteListIfc routeList){
		try {
			System.out.println("11111111111111111111==============="+routeList.getBsoID());
			PersistService pservice = (PersistService) EJBServiceHelper
					.getPersistService();
			QMQuery myQuery = new QMQuery("ListRoutePartLink");
			QueryCondition linkCond = new QueryCondition("leftBsoID",
					QueryCondition.EQUAL, routeList.getBsoID());
			myQuery.addCondition(linkCond);

			Collection linkColl = pservice.findValueInfo(myQuery);
			System.out.println("3333333333333==============="+linkColl.size());
			if(linkColl != null && linkColl.size()>0){
				Iterator iter = linkColl.iterator();
				System.out.println("22222222222222222===============");
				while(iter.hasNext()){
					ListRoutePartLinkIfc partLink = (ListRoutePartLinkIfc)iter.next();
					String routeID = partLink.getRouteID();
					myQuery = new QMQuery("TechnicsRouteBranch");
					QueryCondition routeStr = new QueryCondition("routeID",
							QueryCondition.EQUAL, routeID);
					myQuery.addCondition(routeStr);
					Collection branchInfo = pservice.findValueInfo(myQuery);
					System.out.println("4444444444444444==============="+routeID);
					System.out.println("33333333333333333333333==============="+branchInfo.size());
					if(branchInfo != null && branchInfo.size()>0){
						Iterator iter1 = branchInfo.iterator();
						while(iter1.hasNext()){
							TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)iter1.next();
							String str = branch.getRouteStr();
							System.out.println("routestr==============="+str);
							if (str.indexOf("��") >=0|| str.indexOf("��")>=0
									|| str.indexOf("��(��)")>=0
									|| str.indexOf("��(װ)")>=0
									|| str.indexOf("��(��)")>=0
									|| str.indexOf("��(��)")>=0
									|| str.indexOf("��") >=0|| str.indexOf("��")>=0
									|| str.indexOf("Ϳ")>=0 || str.indexOf("��")>=0
									|| str.indexOf("��(��)")>=0
									|| str.indexOf("Э(��)")>=0) {
								return true;
							}
						}
					}
				}
			}
			
		}catch(Exception exc){
			exc.printStackTrace();
		}
		return false;
	}
// CCEnd SS21


  //CCBegin SS28
    /**
     * ���������ļ�����
     * @param priInfo TechnicsRouteListIfc ��������
     * @param arrayList ArrayList ������
     * @return Collection �־û���������
     * ����ApplicationDataInfo����
     * @author liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
     */
    private Collection uploadContent(TechnicsRouteListIfc priIfc, ArrayList arrayList)
    {
        ContentService service = null;
        Collection col = new Vector();
        int size = arrayList.size();
        for (int i = 0; i < size; i++)
        {
            try
            {
               //��arrayList
                //����ApplicationDataInfo����
             //CCBegin SS16
            	ApplicationDataInfo aplInfo = null;
            	if(fileVaultUsed)
            		aplInfo = (ApplicationDataInfo)arrayList.
                            get(i);
            	else	
            		aplInfo = (ApplicationDataInfo) ((Object[])arrayList.
                                              get(i))[0];
            	//CCEnd SS16
                service = (ContentService) EJBServiceHelper.getService(
                        "ContentService");
                ApplicationDataInfo c = (ApplicationDataInfo) service.
                                        uploadContent(priIfc, aplInfo);
                col.add(c);
            }
            catch (QMException ex)
            {
                ex.printStackTrace();
                return null;
            }
        }
        return col;
    }

    
    /**
     * �洢�ļ���,���ֽ�����д�뵽ָ��ID��StreamData��
     * @param arrayList ArrayList ��ID���ļ��������ļ���
     * @return Collection
     * @author liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
     */
    private void storeFile(ArrayList arrayList)
    {
        int size = arrayList.size();
        for (int i = 0; i < size; i++)
        {
            try
            {
                Object[] obj = (Object[]) arrayList.get(i);
                String streamID = (String) obj[0];
                byte[] byteStream = (byte[]) obj[1];
                StreamUtil.writeData(streamID, byteStream);
            }
            catch (QMException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    /**
     * �洢���տ���������Ϣ
     * @param arrayList ArrayList ��ID���ļ��������ļ���
     * @return Collection
     * @author liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
     */
    private void createAssisFile(TechnicsRouteListIfc list,ArrayList
  		  arrayList) throws QMException {
  	  //��������Ϣ
  	    
  	    Collection col = null;
  	    if (arrayList != null)
  	    {
  	        col = uploadContent(list, arrayList);
  	    }
  	    //CCBegin SS16
  	    if(fileVaultUsed)
  	    	return;
  	    //CCEnd SS16
  	    
  	    Iterator iterator = col.iterator();
  	    ArrayList arrayList2 = new ArrayList();
  	    Object[] object = null;
  	    
  	    Object[] object1 = null;
  	  
  	    int i = 0;
  	    while (iterator.hasNext())
  	    {
  	        object1 = (Object[])arrayList.get(i);
  	        i++;
  	        ApplicationDataInfo applicationData = (ApplicationDataInfo)
  	                                              iterator.next();
  	        //����ļ���
  	        byte[] byteStream = (byte[])object1[1];
  	        if (VERBOSE)
  	        {
  	            System.out.println("applicationData====@@@@@" +
  	                               applicationData.getBsoID());


  	        }
  	        String streamID = applicationData.getStreamDataID();
  	        if (VERBOSE)
  	        {
  	            System.out.println("streamID==" + streamID);

  	        }
  	        //byte[] byteStream = getFileByte(applicationData.
  	          //                              getUploadPath());
  	        object = new Object[]
  	                 {streamID, byteStream};
  	        arrayList2.add(object);
  	    }
  	    storeFile(arrayList2);
    }
    
      /**
     * �洢���տ���������Ϣ
     * @param arrayList ArrayList ��ID���ļ��������ļ���
     * @return Collection
     * @author liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
     */
    private void createAddFile(TechnicsRouteListIfc list, Vector vec) throws QMException 
    {
    	try
    	{
    		PersistService pService= (PersistService) EJBServiceHelper.getService("PersistService");
    		ContentService csService = (ContentService) EJBServiceHelper.getService("ContentService");
    		ArrayList arrayList2 = new ArrayList();
    		Iterator iterator = vec.iterator();
    		while (iterator.hasNext())
    		{
    			String appDataID = (String) iterator.next();
    			//CCBegin SS16
    			if(fileVaultUsed)
    			{
    				ContentClientHelper helper = new ContentClientHelper();
    				byte[] byteStream = helper.requestDownload(appDataID);
    				
    				ApplicationDataInfo newAppDataInfo = helper.requestUpload(byteStream);
    				ApplicationDataInfo yuanAppDataInfo = (ApplicationDataInfo) pService.refreshInfo(appDataID);
    				newAppDataInfo.setFileName(yuanAppDataInfo.getFileName());
    				newAppDataInfo.setUploadPath(yuanAppDataInfo.getUploadPath());
    				newAppDataInfo.setFileSize(yuanAppDataInfo.getFileSize());
    				csService.uploadContent(list, newAppDataInfo);
    			}
    			else
    			{
    				ApplicationDataInfo yuanAppDataInfo = (ApplicationDataInfo) pService.refreshInfo(appDataID);
    				ApplicationDataInfo newAppDataInfo = new ApplicationDataInfo();
    				newAppDataInfo.setFileName(yuanAppDataInfo.getFileName());
    				newAppDataInfo.setUploadPath(yuanAppDataInfo.getUploadPath());
    				newAppDataInfo.setFileSize(yuanAppDataInfo.getFileSize());
    				ApplicationDataInfo appDataInfo = (ApplicationDataInfo) csService.uploadContent(list, newAppDataInfo);

    				String yuanStreamID = yuanAppDataInfo.getStreamDataID();
    				StreamDataInfo yuanStreamInfo = (StreamDataInfo)pService.refreshInfo(yuanStreamID);
    				byte[] byteStream = yuanStreamInfo.getDataContent();
    				String streamID = appDataInfo.getStreamDataID();
    				Object[] object = new Object[]{streamID, byteStream};
    				arrayList2.add(object);
    			}
    			//CCEnd SS16
    		}
    		//CCBegin SS16
    		if(!fileVaultUsed)
    			storeFile(arrayList2);
    		//CCEnd SS16
    	}
    	catch (QMException e)
    	{
    		e.printStackTrace();
    	}
    }
      
    /**
     * ˢ��ApplicationDataInfoֵ����
     * @param col Collection
     * @throws ResourceException
     * @return Vector
     * @author liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
     */
    private Vector refreshAppInfo(Collection col)
            throws QMException
    {
        PersistService service = null;
        Vector vector = new Vector();
        Iterator iterator = col.iterator();
        while (iterator.hasNext())
        {
            String appDataID = (String) iterator.next();
            try
            {
                service = (PersistService) EJBServiceHelper.getService(
                        "PersistService");
                ApplicationDataInfo application = (ApplicationDataInfo) service.
                                                  refreshInfo(appDataID); //ˢ��ֵ����
                vector.add(application);
            }
            catch (QMException e)
            {
                e.printStackTrace();
                throw new QMException(e);
            }
        }
        return vector;
    }
    /**
     * ɾ������������ָ����������
     * @param priInfo ResourceManagedInfo ��������
     * @param appDataInfo Vector �������
     * @author liunan 2009-10-12 ���ݽ��Ҫ��Ϊ������Ӹ����Ĺ������档
     */
    private void deleteApplicationData(TechnicsRouteListIfc priIfc, Vector appDataInfo)
            throws QMException
    {
        ContentService service = null;
        int size = appDataInfo.size();
        for (int i = 0; i < size; i++)
        {
            ApplicationDataInfo application = (ApplicationDataInfo) appDataInfo.
                                              get(i);
            try
            {
                service = (ContentService) EJBServiceHelper.getService("ContentService");
                service.deleteApplicationData(priIfc, application);
            }
            catch (QMException ex)
            {
                ex.printStackTrace();
            }
        }
    }
  //CCEnd SS28
  
  //CCBegin SS30
  public String getPartRouteState(String partMasterID)
  {
  	String str = "��";
  	try
  	{
  		PersistService ps = (PersistService) EJBServiceHelper.getPersistService();
            QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
            int j = query1.appendBso(TECHNICSROUTE_BSONAME, false);
            QueryCondition cond1 = new QueryCondition(RIGHTID,
                                                      QueryCondition.EQUAL,
                                                      partMasterID);
            query1.addCondition(0, cond1);

            query1.addAND();
            QueryCondition cond3 = new QueryCondition("routeID",
                    QueryCondition.NOT_NULL);
            query1.addCondition(0, cond3);
            query1.addAND();

    	      QueryCondition cond4 = new QueryCondition("alterStatus",
    	                                              QueryCondition.EQUAL,
    	                                              ROUTEALTER);
    	      query1.addCondition(0, cond4);
            query1.addAND();
            
            QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
            query1.addCondition(0, j, cond6);
            query1.addAND();
            
            QueryCondition cond7 = new QueryCondition("modefyIdenty", QueryCondition.NOT_EQUAL, "Coding_221664");
            query1.addCondition(j, cond7);
            
            query1.addOrderBy(0, "modifyTime",true);
            
            //System.out.println("��ȡ�㲿������·��SQL��"+query1.getDebugSQL());
            
           Collection c=(Collection) ps.findValueInfo(query1, false);
           Iterator i=c.iterator();
           if(i.hasNext())
           {
        	   ListRoutePartLinkInfo link=(ListRoutePartLinkInfo) i.next();
        	   TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) ps.refreshInfo(link.getRouteID());
             str=routeInfo.getModefyIdenty().getCodeContent();
        	   //System.out.println("----�õ���·��״̬��="+str);
           }
    }
    catch(Exception e)
    {
    	e.printStackTrace();
    }
    return str;
  }
  //CCEnd SS30
  
  //CCBegin SS36
  public Vector getPartRouteStrs(String partMasterID)
  {
  	Vector vec = new Vector();
  	try
  	{
  		PersistService ps = (PersistService) EJBServiceHelper.getPersistService();
  		QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
      int j = query1.appendBso(TECHNICSROUTE_BSONAME, false);
      QueryCondition cond1 = new QueryCondition(RIGHTID,QueryCondition.EQUAL,partMasterID);
      query1.addCondition(0, cond1);
      query1.addAND();
      QueryCondition cond3 = new QueryCondition("routeID",QueryCondition.NOT_NULL);
      query1.addCondition(0, cond3);
      query1.addAND();
      QueryCondition cond4 = new QueryCondition("alterStatus",QueryCondition.EQUAL,ROUTEALTER);
      query1.addCondition(0, cond4);
      query1.addAND();
      QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
      query1.addCondition(0, j, cond6);
      query1.addAND();
      QueryCondition cond7 = new QueryCondition("modefyIdenty", QueryCondition.NOT_EQUAL, "Coding_221664");
      query1.addCondition(j, cond7);
      query1.addOrderBy(0, "modifyTime",true);
      Collection c=(Collection) ps.findValueInfo(query1, false);
      Iterator i=c.iterator();
      if(i.hasNext())
      {
      	ListRoutePartLinkInfo link=(ListRoutePartLinkInfo) i.next();
      	QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);//��TechnicsRouteBranch
      	QueryCondition cond = new QueryCondition("routeID",QueryCondition.EQUAL,link.getRouteID());
      	query.addCondition(cond);
      	Collection coll = ps.findValueInfo(query);
      	for (Iterator iter = coll.iterator(); iter.hasNext(); )
      	{
      		TechnicsRouteBranchIfc routeBranchInfo = (TechnicsRouteBranchIfc)iter.next();
      		vec.add(routeBranchInfo.getRouteStr());
      	}
      }
    }
    catch(Exception e)
    {
    	e.printStackTrace();
    }
    return vec;
  }
  //CCEnd SS36
  
  
  //CCBegin SS37
	/**
	 * ��ȡ��Ŀ���ݲ�����
	 * 
	 * @param self
	 * @param primaryBusinessObject
	 * @throws QMException
	 * @throws ParseException
	 */
	public void getWFInfoAndSetProducePreparative(Object self,
			Object primaryBusinessObject) throws QMException, ParseException {
		System.out.println("1111111111111111111111111111111111");
		PersistService ps = (PersistService) EJBServiceHelper
				.getService("PersistService");
		DomainService dService = (DomainService) EJBServiceHelper
				.getService("DomainService");
		FolderService fservice = (FolderService) EJBServiceHelper
				.getService("FolderService");
		LifeCycleService lifeCycle = (LifeCycleService) EJBServiceHelper
				.getService("LifeCycleService");
				
		//CCBegin SS41
		ProducePreparativeService ppService = (ProducePreparativeService) EJBServiceHelper
		.getService("ProducePreparativeService");
		//CCEnd SS41
		
		TechnicsRouteListIfc technicsifc = (TechnicsRouteListIfc) primaryBusinessObject;
		System.out.println("self=============" + self.getClass().toString());
		WfProcessIfc process = (WfProcessIfc) self;
		
		//CCBegin SS42
		
		RolePrincipalTable table1 = (RolePrincipalTable) process
		.getRolePrincipalMap();
		
		//CCEnd SS42
		
		System.out.println("process.getBsoID=========" + process.getBsoID());
		QMQuery query = new QMQuery("WfAssignedActivity");
		QueryCondition cond = new QueryCondition("parentProcessBsoID", "=",
				process.getBsoID());
		query.addCondition(cond);
		Collection collection = ps.findValueInfo(query);
		System.out.println("�������ϴ�С=========" + collection.size());
		Iterator ite = collection.iterator();
		ProcessData processdata = null;
		// ����׼��д�������׼����Ŀ
		String xprocess = RemoteProperty.getProperty(
				"technicsRouteForProducePreparative", "��׼��׼");
		// ��û�ǩ�������
		String huiqianstr = RemoteProperty.getProperty(
				"technicsRouteSignatureStr", "��ǩ���");
		// ��û�ǩ�������
		String huiqian = RemoteProperty.getProperty("technicsRouteSignature",
				"·�߻�ǩ");
		System.out.println("xprocess====================" + xprocess);
		System.out.println("huiqianstr====================" + huiqianstr);
		System.out.println("huiqian====================" + huiqian);
		// ��ǩʱ��
		String huiqiantime = "";
		// ��ǩ����
		String hqstr = "";
		//ת����ʽ���ǩʱ��
		String hqTime="";
		
		
		String xmdh = "";
		String xmmc = "";
		String xmlb = "";
		String xmsjgl = "";
		
		//CCBegin SS48
		String szPlantFinishDate="";
		//CCEnd SS48
		
		while (ite.hasNext()) {
			WfAssignedActivityIfc wfa = (WfAssignedActivityIfc) ite.next();
			String wfaname = wfa.getName();
			System.out.println("�����wfaname=========="+wfaname);
			// ���һ�ǩʱ�估����
			if (wfaname.equalsIgnoreCase(huiqian)) {
				// ������еı�������
				ProcessData processdata1 = wfa.getContext();
				// ��ȡ��ǩ����
				hqstr = (String) processdata1.getValue(huiqianstr);
				// �õ���ǩʱ��
				huiqiantime = DateFormat.getDateInstance().format(
						DateFormat.getDateInstance().parse(
								wfa.getEndTime().toString()));
				String[] hqtime = huiqiantime.split("-");
				hqTime = hqtime[1]+"/"+hqtime[2]+"/"+hqtime[0];
				System.out.println("hqstr====================" + hqstr);
				System.out.println("huiqiantime===================="
						+ huiqiantime);
						
				//CCBegin SS42
				Vector huiqianPeopleVec = (Vector) table1.get("HUIQIANZHE");
				
				String huiqianRen="";
				if (huiqianPeopleVec != null && huiqianPeopleVec.size() > 0) {
					
					for(int i=0;i<huiqianPeopleVec.size();i++){
					
					   String huiqian2 = huiqianPeopleVec.elementAt(i).toString();
					   UserIfc user1 = (UserIfc) ps.refreshInfo(huiqian2);
					   huiqianRen = huiqianRen+user1.getUsersDesc()+";";
					}
				} 
				
				System.out.println("hqr====111111111====================="+huiqianRen);
				
				//CCEnd SS42
				
				xmdh = (String) processdata1.getValue("��Ŀ����");
				xmmc = (String) processdata1.getValue("��Ŀ����");
				xmlb = (String) processdata1.getValue("��Ŀ���");
				xmsjgl = (String) processdata1.getValue("��Ŀ��Ƹ���");
				
				//CCBegin SS48
				szPlantFinishDate= (String) processdata1.getValue("����׼��Ҫ�����ʱ��");
				//CCEnd SS48
				
				System.out.println("��Ŀ����=========" + xmdh);
				System.out.println("��Ŀ����=========" + xmmc);
				System.out.println("��Ŀ���=========" + xmlb);
				System.out.println("��Ŀ��Ƹ���=========" + xmsjgl);
				
				//CCBegin SS42
				ProducePreparativeInfo ppInfo =null;
				
				Vector ppInfoVec=(Vector)ppService.seachPP("sCode", xmdh);
				
				if(ppInfoVec!=null&&ppInfoVec.size()!=0)
					
					ppInfo =(ProducePreparativeInfo)ppInfoVec.get(0);
				
				else
					
					ppInfo=new ProducePreparativeInfo();
				
				//CCEnd SS42
				
				// �������ϼк���
				ppInfo.setLocation("\\Root\\����׼��");
				fservice.assignFolder((FolderEntryIfc) ppInfo, "\\Root\\����׼��");
				ppInfo.setDomain(dService.getDomainID("����׼��"));
				// ������������
				LifeCycleTemplateInfo lifecycle = (LifeCycleTemplateInfo) lifeCycle
						.getLifeCycleTemplate("ȱʡ");
				ppInfo.setLifeCycleTemplate(lifecycle.getBsoID());
				ppInfo.setProjectNum(xmdh);
				ppInfo.setProjectName(xmmc);
				ppInfo.setProjectType(xmlb);
				ppInfo.setProjectDesignProgramme(xmsjgl);
				//CCBegin SS50 �����Զ���������
				ppInfo.setAttr1("Y");
				
				//CCEnd SS50
				ppInfo = (ProducePreparativeInfo) ps.saveValueInfo(ppInfo);
				System.out.println("��ĿID=================" + ppInfo.getBsoID());
				// ��Ŀid
				String projectid = ppInfo.getBsoID();
				
				//CCBegin SS48
				QMPartIfc  linkPartifc=null;
				//CCEnd SS48
				
				// ��ù�����Ϣ
				Collection coll = getLinkByRouteList(technicsifc);
				for (Iterator iter = coll.iterator(); iter.hasNext();) {
					ListRoutePartLinkIfc link = (ListRoutePartLinkInfo) iter
							.next();
					String count = String.valueOf(link.getCount());// ����
					
					//CCBegin SS52
					String partID = link.getPartBranchID();//���ID
					
					QMPartIfc partifc = (QMPartIfc) ps.refreshInfo(partID);// �㲿��ֵ����
					
//					String partmasterid = link.getRightBsoID();// ���masterid
//					
//					QMPartMasterIfc partmasterifc = (QMPartMasterIfc) ps
//							.refreshInfo(partmasterid);// ���masterֵ����
//					
//					QMPartIfc partifc = (QMPartIfc) getLatestVesion(partmasterifc);// �㲿��ֵ����
					
					//CCEnd SS52
					
					//CCBegin SS48
					if(linkPartifc==null)
					    linkPartifc=partifc;
					//CCEnd SS48
					
					// ���ָ�����������·�ߺ�װ��·��,String[] ��һ��Ԫ��Ϊ����·��,�ڶ���Ԫ��Ϊװ��·��
					String[] routestr = getRouteString(partifc);
					//CCBegin SS50  ��·�߹������
					
					String makeRoute=routestr[0];
					String AssembleRoute=routestr[1];
					
					if(makeRoute.contains("��")&&(AssembleRoute.equals("")||AssembleRoute==null)){
						
						continue;
					}
					
					//CCBegin SS52
					partifc.setLifeCycleState(LifeCycleState.toLifeCycleState("PREPARING"));
					
					//CCEnd SS52
					
					//CCEnd SS50
					ProjectPartIfc pPartifc = new ProjectPartInfo();
					pPartifc.setLeftBsoID(projectid);// ������ĿID
					
					//CCBegin SS42
					pPartifc.setRightBsoID(partifc.getBsoID());// �����㲿��ID
					//CCEnd SS42
					
					//CCBegin SS52
					
					//CCBegin SS53
					//pPartifc.setPartNum(linkPartifc.getPartNumber());// �����㲿�����
					//pPartifc.setPartName(linkPartifc.getPartName());// �����㲿������
					
					
					pPartifc.setPartNum(partifc.getPartNumber());// �����㲿�����
					pPartifc.setPartName(partifc.getPartName());// �����㲿������
					
					//CCEnd SS53
					
					//pPartifc.setPartNum(partmasterifc.getPartNumber());// �����㲿�����
					//pPartifc.setPartName(partmasterifc.getPartName());// �����㲿������
					
					//CCEnd SS52
					
					pPartifc.setCount(count);// ��������
					pPartifc.setTechnisNum(technicsifc.getRouteListNumber());// ������׼��
					pPartifc.setContractID(hqTime);// �����ǩʱ��
					pPartifc.setHqContent(hqstr);// �����ǩ����
					
					//CCBegin SS42
					pPartifc.setHqPeople(huiqianRen);// �����ǩ��
					
					//CCEnd SS42
					
					pPartifc.setMakeRoute(routestr[0]);//��������·��
					pPartifc.setAssembleRoute(routestr[1]);//����װ��·��
					
					
					//CCBegin SS41
					pPartifc.setPartVerstionID(partifc.getVersionID());//����汾
					//CCBegin SS42
					//���ò��ñ��
					pPartifc=setCaiYongAndBianGeng(pPartifc,partifc);
					
					//CCEnd SS42
					//CCBegin SS50
					//��Ŀ���
					pPartifc.setProjectType(xmlb);
					//CCEnd SS50
					
					Vector findSamePartVec=ppService.seachProjectSameParts(ppInfo.getBsoID(),partifc.getPartNumber());
					 if(findSamePartVec.size()!=0){
						 for(int a=0;a<findSamePartVec.size();a++){
							 
							 ProjectPartIfc ppOldInfo=(ProjectPartIfc)findSamePartVec.get(a);
							 ppOldInfo = (ProjectPartIfc)ps.refreshInfo(ppOldInfo.getBsoID());
							 ppOldInfo.setIsSamePart("true");
							 ps.saveValueInfo(ppOldInfo);
							 
						 }
						 pPartifc.setIsSamePart("true");
					 }
					//CCEnd SS41
					
					//CCBegin SS50 �����Զ���������
					 pPartifc.setAtt5("Y");
				        //CCEnd SS50
				        
					pPartifc = (ProjectPartInfo) ps.saveValueInfo(pPartifc);// ������Ŀ�㲿������
					
					
					//CCBegin SS40
					//�Զ�������Ʒ������Ϣ
					ProductAttributesInfo paInfo=findPartAttr(partifc);
					paInfo.setLeftBsoID(projectid);
					paInfo.setRightBsoID(pPartifc.getBsoID());
					
					ps.saveValueInfo(paInfo);
					//CCEnd SS40
					
					//CCBegin SS43
					
					//���濪��״̬�����Ϣ
					
					ProductDevelopmentStatusInfo pdsInfo=new ProductDevelopmentStatusInfo();
					pdsInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					pdsInfo.setRightBsoID(pPartifc.getBsoID());
					pdsInfo.setAttr1(partifc.getPartNumber());
					pdsInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(pdsInfo);
					
	                //���湤�����״̬�����Ϣ
					
					ProcessDesignPhaseInfo pdpInfo=new ProcessDesignPhaseInfo();
					pdpInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					pdpInfo.setRightBsoID(pPartifc.getBsoID());
					pdpInfo.setAttr1(partifc.getPartNumber());
					pdpInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(pdpInfo);
					
	             //���湤��׼���׶������Ϣ
					
					ProcessPreparationStageInfo ppsInfo=new ProcessPreparationStageInfo();
					ppsInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					ppsInfo.setRightBsoID(pPartifc.getBsoID());
					ppsInfo.setAttr1(partifc.getPartNumber());
					ppsInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(ppsInfo);
					
	           //���湤�յ���&��װ���������Ϣ
					
					ProcessDebuggingInfo pdgInfo=new ProcessDebuggingInfo();
					pdgInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					pdgInfo.setRightBsoID(pPartifc.getBsoID());
					pdgInfo.setAttr1(partifc.getPartNumber());
					pdgInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(pdgInfo);
					
	             //������װ�����Ϣ
					
					TryInstallInfo tisInfo=new TryInstallInfo();
					tisInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					tisInfo.setRightBsoID(pPartifc.getBsoID());
					tisInfo.setAttr1(partifc.getPartNumber());
					tisInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(tisInfo);
					
	            //������װ�����Ϣ
					
					TryInstallInfo tislInfo=new TryInstallInfo();
					tislInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					tislInfo.setRightBsoID(pPartifc.getBsoID());
					tislInfo.setAttr1(partifc.getPartNumber());
					tislInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(tislInfo);
					
					
	             //���������Ͽ������Ϣ
					
					PrototypeInfo ptyInfo=new PrototypeInfo();
					ptyInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					ptyInfo.setRightBsoID(pPartifc.getBsoID());
					ptyInfo.setAttr1(partifc.getPartNumber());
					ptyInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(ptyInfo);
					
					
	              //��������׼��״̬��������Ϣ
					
					CheckReadyStateInfo crdsInfo=new CheckReadyStateInfo();
					crdsInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					crdsInfo.setRightBsoID(pPartifc.getBsoID());
					crdsInfo.setAttr1(partifc.getPartNumber());
					crdsInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(crdsInfo);
					
	             //��������׼�����������Ϣ
					
					CompleteReportInfo crpInfo=new CompleteReportInfo();
					crpInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					crpInfo.setRightBsoID(pPartifc.getBsoID());
					crpInfo.setAttr1(partifc.getPartNumber());
					crpInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(crpInfo);
					
					
	             //������ܸ��������Ϣ
					
					CapacityTrackingInfo ctkInfo=new CapacityTrackingInfo();
					ctkInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					ctkInfo.setRightBsoID(pPartifc.getBsoID());
					ctkInfo.setAttr1(partifc.getPartNumber());
					ctkInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(ctkInfo);
					
					//CCEnd SS43
				}
				//CCBegin SS48
				
				//������׼��Ϣ
				createTechnicsNoticeDate(linkPartifc,ppInfo,technicsifc,huiqianRen,hqstr,szPlantFinishDate);
				//CCEnd SS48
			}

		}
	}


	//CCBegin SS48
	/**
	 * ������׼��Ϣ
	 * @throws QMException 
	 */
	private void createTechnicsNoticeDate(QMPartIfc partInfo,ProducePreparativeInfo ppInfo,
			TechnicsRouteListIfc tRLInfo,String huiqianR,String huiqianStr,String szPlantFinishDate) throws QMException{
		
		  StandardPartService sPervice = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
		
		  IBAValueService IBAservice = (IBAValueService)EJBServiceHelper.getService("IBAValueService");
		  
		
		
		  
		  PersistService ps = (PersistService) EJBServiceHelper
			.getService("PersistService");
		
		  HashMap ibaMap=IBAservice.getIBADefinitionAndValues(partInfo);
		  
		  ProjectTechnicsNoticeInfo ptnInfo=new ProjectTechnicsNoticeInfo();
		  
		  ptnInfo.setLeftBsoID(ppInfo.getBsoID());
		  
		  ArrayList al=(ArrayList)ibaMap.get("notenumbers");
		  //���ñ������
		  String faLingNum="";
			 if(al!=null)
				 faLingNum= (String)al.get(0);
			 
			//���ò��ñ������
		  ptnInfo.setAttry1(faLingNum);
		  
		  ptnInfo.setProjectName(ppInfo.getProjectName());
		  
		  ArrayList al2=(ArrayList)ibaMap.get("publishdate");
		  //�յ�����
		  String shouDdaoData="";
			 //CCBegin SS50 ����ʱ���ʽ
			 if(al!=null){
				 shouDdaoData= (String)al2.get(0);
				 
				 shouDdaoData=StringPattern(shouDdaoData,"yyyy��MM��dd��","yyyy-MM-dd");
			 }
			 //CCEnd SS50
		  ptnInfo.setReceiveDate(shouDdaoData);
		  
		  String userID=tRLInfo.getCreator();
		  UserInfo crName= (UserInfo)ps.refreshInfo(userID);
		  ptnInfo.setTechnicsNoticeCreatePerson(crName.getUsersDesc());
		  ptnInfo.setTechnicsNoticeNum(tRLInfo.getRouteListNumber());
		    //CCBegin SS50 ����ʱ���ʽ
		  String tNCreateTime=StringPattern(tRLInfo.getCreateTime().toString(),"yyyy-MM-dd HH:mm:ss.S","yyyy-MM-dd HH:mm");
		  
		  ptnInfo.setTechnicsNoticeCreateDate(tNCreateTime);
		//CCEnd SS50
		  
		  SimpleDateFormat sdf=new SimpleDateFormat();
               //CCBegin SS50 ����ʱ���ʽ
                  sdf.applyPattern("yyyy-MM-dd");   
               //CCEnd SS50              	
          String outData = sdf.format(new Date());
		  
		  ptnInfo.setTechnicsNoticeOutDate(outData);
		  ptnInfo.setProjectHQSuggestion(huiqianStr);
		  ptnInfo.setProjectType(ppInfo.getProjectType());
		  ptnInfo.setProductionPerson(huiqianR);
		  ptnInfo.setProductionPlantFinishTime(szPlantFinishDate);
		    //CCBegin SS50  �����Զ���������
		  ptnInfo.setAttry3("Y");
		  //CCEnd SS50
		  ps.saveValueInfo(ptnInfo);
	}
	
	//CCEnd SS48
	
	
	//CCBegin SS50
	
	/**
	 * ���ڸ�ʽ��
	 * @param date
	 * @param oldPattern
	 * @param newPattern
	 * @return
	 */
	 private   String StringPattern(String date, String oldPattern, String newPattern) {   
	        if (date == null || oldPattern == null || newPattern == null)   
	            return "";   
	        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern) ;        // ʵ����ģ�����    
	        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern) ;        // ʵ����ģ�����    
	        Date d = null ;    
	        try{    
	            d = sdf1.parse(date) ;   // ���������ַ����е�������ȡ����    
	        }catch(Exception e){            // ����ṩ���ַ�����ʽ�д���������쳣����    
	            e.printStackTrace() ;       // ��ӡ�쳣��Ϣ    
	        }    
	        System.out.println(sdf2.format(d));
	        return sdf2.format(d);  
	    } 
	
	//CCEnd SS50
	

	//CCBegin SS42
	/**
	 * ���ò��ñ������
	 * @return
	 * @throws QMException 
	 */
	private ProjectPartIfc setCaiYongAndBianGeng(ProjectPartIfc pLinkPartInfo,QMPartIfc partInfo) throws QMException{
		
		 StandardPartService sPervice = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
			
		  IBAValueService IBAservice = (IBAValueService)EJBServiceHelper.getService("IBAValueService");
		  
		  
		  
		  String partNum= partInfo.getPartNumber();
		  String partName=partInfo.getPartName();
		  
		  Vector partMasterInfoVec=(Vector)sPervice.findPartMaster(partName,partNum);
		  QMPartMasterIfc myPartInfo=(QMPartMasterIfc)partMasterInfoVec.get(0);
		  Vector partVec=(Vector)sPervice.findPart(myPartInfo);
		  
		  QMPartIfc lastPart=(QMPartIfc)partVec.get(0);
		  
		  HashMap ibaMap=IBAservice.getIBADefinitionAndValues(lastPart);
		  
		  
		          //CCBegin SS48
		        ArrayList al=(ArrayList)ibaMap.get("notenumbers");
		          //CCEnd SS48
			 String caiyongNum="";
			 if(al!=null)
			   caiyongNum= (String)al.get(0);
			 
			 System.out.println("caiyongNum========11111111==========="+caiyongNum);
			 ArrayList proPartNumList=(ArrayList)ibaMap.get("ProcessPartNumber");
			 String biangengNum="";
			 if(proPartNumList!=null)
			   biangengNum=(String)proPartNumList.get(0);
			 System.out.println("biangengNum========11111111==========="+biangengNum);
			 pLinkPartInfo.setCaiYongNum(caiyongNum);
			 pLinkPartInfo.setBianGengNum(biangengNum);
		 
		 return  pLinkPartInfo;
	}
	
	//CCEnd SS42
	
	
	/**
	 * ͨ��·�߱���ҹ�����
	 * @param routeListInfo
	 * @return
	 * @throws QMException
	 */
	public Collection getLinkByRouteList(TechnicsRouteListIfc routeListInfo)
			throws QMException {
		Collection coll = null;
		PersistService ps = (PersistService) EJBServiceHelper
				.getService("PersistService");
		QMQuery query = new QMQuery("ListRoutePartLink");
		QueryCondition cond = new QueryCondition("leftBsoID", "=",
				routeListInfo.getBsoID());
		query.addCondition(cond);
		coll = ps.findValueInfo(query);
		return coll;
	}
	// CCEnd SS37
	
	//CCBegin SS38
	/**
	 * 1:����׼��׼�󣬽���׼�����ĵ�������Զ�������EOLϵͳ��Ŀ���ļ���
	 * 2:����׼��׼�󣬽���׼�����ĺ����ܳɵķ��������Զ�����һ���ı��ļ������Զ�������EOLϵͳ��Ŀ���ļ��� 
	 * 3:����׼Ŀǰ���⼸�����ݣ�a��3601611XXXX  �Ƿ��������Ƶ�Ԫ�����ļ���b��3611611XXXX  �Ǳ��������Ƶ�Ԫ�����ļ���
	 * c��3601651XXXX�Ƿ�������ʱ���Ƶ�Ԫ�����ļ���d��3610611XXXX ���������Ƶ�Ԫ�����ļ���e��3610612XXXX �������Ļ������ݣ�
	 */
	public void publishDataToEOL(BaseValueIfc primaryBusinessObject) throws QMException
	{
		System.out.println("come in publishDataToEOL:"+primaryBusinessObject);
		try
		{
			if(primaryBusinessObject instanceof TechnicsRouteListIfc)
			{
				TechnicsRouteListIfc route = (TechnicsRouteListIfc)primaryBusinessObject;
				//��ȡ��׼�����㲿��
				PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
				StandardPartService spService = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
				ContentService contentService = (ContentService)EJBServiceHelper.getService("ContentService");
				String eolPublishPath = RemoteProperty.getProperty("eolPublishPath") + route.getRouteListNumber() + File.separator;
				
				QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
				query.addCondition(new QueryCondition(LEFTID, QueryCondition.EQUAL, route.getBsoID()));
				query.addAND();
				//�п������δʹ��·�ߡ�
				query.addCondition(new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE));
				Collection coll = pservice.findValueInfo(query);
				ArrayList partlist = new ArrayList();
				//��ȡ�㲿�����������ĵ����ж��Ƿ����ƺ��С���ص�Ԫ���ݡ�
				for (Iterator iter = coll.iterator(); iter.hasNext();)
				{
					ListRoutePartLinkIfc link = (ListRoutePartLinkInfo) iter.next();
					if(!partlist.contains(link.getRightBsoID()))
					{
						//��ȡ���°��㲿��
						QMPartMasterIfc masterInfo = (QMPartMasterIfc) pservice.refreshInfo(link.getRightBsoID());
						QMPartIfc part = getLastedPartByConfig(masterInfo);
						String partnumber = part.getPartNumber();
						//System.out.println("partnumber=="+partnumber+"  id=="+part);
						//��������2400��ͷ�����ȡ�ٱȺͺ��־�
						//CCBegin SS49
						//if(partnumber.startsWith("2400"))
						//2400010��2500010��ȡ�ٱȣ�3106010��ȡ��̥�����뾶
						if(partnumber.startsWith("2400010")||partnumber.startsWith("2500010")||partnumber.startsWith("3106010"))
						//CCEnd SS49
						{
							File f = new File(eolPublishPath);
							if(!f.exists())
							{
								f.mkdir();
							}
							String subi = getIBAValue((IBAHolderIfc)part, "�ٱ�");
							//String houlunju = getIBAValue((IBAHolderIfc)part, "���־�");
							//String zhongxinju = getIBAValue((IBAHolderIfc)part, "�ְ嵯�����ľ�");
							//String gundongbanjing = getIBAValue((IBAHolderIfc)part, "���ֹ����뾶");
							String gundongbanjing = getIBAValue((IBAHolderIfc)part, "��̥�����뾶");//2017-12-25
							//CCBegin SS51
							String maichongshu = getIBAValue((IBAHolderIfc)part, "������ÿת������");
							if(maichongshu==null)
							{
								maichongshu = "";
							}
							String changganqisubi = getIBAValue((IBAHolderIfc)part, "�������복�ٴ������ٱ�");
							if(changganqisubi==null)
							{
								changganqisubi = "";
							}
							//CCEnd SS51
							//System.out.println("subi=="+subi);
							//System.out.println("gundongbanjing=="+gundongbanjing);
							if(subi==null)
							{
								subi = "";
							}
							if(gundongbanjing==null)
							{
								gundongbanjing = "";
							}
							//���÷����м��ʽ��
							/*if(!subi.equals("")||!gundongbanjing.equals(""))
							{
								StringBuffer buffer = new StringBuffer();
								buffer.append(part.getPartNumber()+"\n");
								buffer.append(subi+"\n");
								buffer.append(gundongbanjing);
								//д�ļ�
								FileWriter writer = new FileWriter(eolPublishPath + part.getPartNumber() + ".txt");
								writer.write(buffer.toString());
								writer.flush();
								writer.close();
							}*/
							String value = "";
							if(!subi.equals(""))
							{
								//CCBegin SS49
								if(subi.indexOf(":")>0)
								{
									subi = subi.trim();
									subi = subi.substring(0,subi.indexOf(":"));
								}
								//CCEnd SS49
								value = "SpeedRatio=" + subi + "|";
							}
							if(!gundongbanjing.equals(""))
							{
								value = value + "RollingRadius=" + gundongbanjing + "|";
							}
							//CCBegin SS51
							if(!maichongshu.equals(""))
							{
								value = value + "SensorPulsePerRev=" + maichongshu + "|";
							}
							if(!changganqisubi.equals(""))
							{
								value = value + "DriveShaftAndSpeedRatio=" + changganqisubi + "|";
							}
							//CCEnd SS51
							if(!value.equals(""))
							{
								//���ݿ���û�и�ֵ�������
								sendEolDataByJDBC(partnumber,value);
							}
						}
						
						//a��3601611XXXX  b��3611611XXXX  c��3601651XXXX d��3610611XXXX e��3610612XXXX
						if(partnumber.startsWith("3601611")||partnumber.startsWith("3611611")||partnumber.startsWith("3601651")||partnumber.startsWith("3610611")||partnumber.startsWith("3610612"))
						{
							File f = new File(eolPublishPath);
							if(!f.exists())
							{
								f.mkdir();
							}
						}
						else
						{
							continue;
						}
						
						System.out.println("���"+partnumber+"������ȡ����ĵ�");
						//���㲿�������ĵ���ֵ���󼯺�
            Vector descriDocs = spService.getDescribedByDocs(part,true);
            if(descriDocs!=null && descriDocs.size()>0)
            {
              for(int i=0;i<descriDocs.size();i++)
              {
              	Object obj = descriDocs.elementAt(i);
              	if(obj instanceof DocIfc)
              	{
              		DocIfc docIfc = (DocIfc)obj;
              		if(docIfc.getDocName().indexOf("��ص�Ԫ����")!=-1||docIfc.getLocation().indexOf("����ĵ�")!=-1)
              		{
              			//��ȡ�ĵ�����
              			Vector c = (Vector)contentService.getContents((ContentHolderIfc)docIfc);
              			if(c!=null)
              			{
              				ContentItemIfc item;
              				ApplicationDataInfo appDataInfo = null;
              				for (Iterator iter1 = c.iterator(); iter1.hasNext();)
              				{
              					item = (ContentItemIfc) iter1.next();
              					if (item instanceof ApplicationDataInfo)
              					{
              						appDataInfo = (ApplicationDataInfo) item;
              						//�����ĵ�����
              						downloadPDF(eolPublishPath + appDataInfo.getFileName(), appDataInfo);
              					}
              				}
              			}
              		}
              		else
              		{
              			System.out.println("�ĵ�"+docIfc.getDocNum()+"���ǵ���ĵ�");
              		}
              	}
              }
            }
            else
            {
            	System.out.println("���"+partnumber+"û�еõ�����ĵ�");
            }
						partlist.add(link.getRightBsoID());
					}
				}
				//�����ļ���Զ�̹����ļ���
				File f = new File(eolPublishPath);
				ftpLoad(f);
			}
		}
		catch (QMException ex)
		{
			ex.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	private void sendEolDataByJDBC(String num, String value)
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try 
		{
			conn = getEolConnection();
			stmt = conn.createStatement();
			{
				String countListSql = "select count(*) from PartProperty where PartNo='" + num + "' and PartProperty='" + value + "'";
				rs = stmt.executeQuery(countListSql);
				rs.next();
				int countList = rs.getInt(1);
				if (countList == 0)
				{
					//�����¼�¼��
					String insDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					String insertSql = "insert into PartProperty (PartNo,PartProperty,DateTime) VALUES ('" + num + "','"+ value + "',getdate())";
					System.out.println("insertSql=================" + insertSql);
					stmt.execute(insertSql);
				}
				else if (countList == 1)
				{
					//��һ���ļ�¼������Ҫ���롣
					System.out.println("����"+num+"->"+value+"�Ѵ��ڡ�");
				}
				else
				{
					System.out.println("���������⣬��Ӧ�ô���2������ź�ֵһ���ļ�¼��");
				}
			}
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try
			{
				if (rs != null)
				{
					rs.close();
				}
				if (stmt != null) 
				{
					stmt.close();
				}
				if (conn != null) 
				{
					conn.close();
				}
			} 
			catch (SQLException ex1)
{
				ex1.printStackTrace();
			}
		}
	}
	
	
	/**
	 * ��ȡEOL���ݿ�����
	 */
	private Connection getEolConnection() throws SQLException 
	{
		String url = RemoteProperty.getProperty("eol.DB.url", "");
		String user = RemoteProperty.getProperty("eol.DB.user", ""); 
		String password = RemoteProperty.getProperty("eol.DB.password", "");
		String driverName = RemoteProperty.getProperty("eol.DB.driverName", "");
		Connection conn = null;
		try
		{
			Class.forName(driverName);
			conn = DriverManager.getConnection(url, user, password);
		}
		catch (Exception e)
		{
			e.printStackTrace();
	  }
		return conn;
	}
	
	
	public void publishDataToEOL(String id) throws QMException
	{
		try
		{
			PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
			TechnicsRouteListIfc ifc = (TechnicsRouteListIfc) pservice.refreshInfo(id);
			publishDataToEOL(ifc);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private String getIBAValue(IBAHolderIfc holder, String def)
	{
		try
		{
			IBAValueService vs = (IBAValueService) EJBServiceHelper.getService("IBAValueService");
			holder = vs.refreshAttributeContainer(holder, null, null,null);
			DefaultAttributeContainer attrCont = (DefaultAttributeContainer) holder.getAttributeContainer();
			AbstractValueView values[] = attrCont.getAttributeValues();
			boolean verOk = false;
			for (int i = 0; i < values.length; i++)
			{
				if (values[i] instanceof StringValueDefaultView && values[i].getDefinition().getName().equals(def))
				{
					StringValueDefaultView strValue = (StringValueDefaultView) values[i];
					StringDefView defView = (StringDefView) strValue.getDefinition();
					String value = strValue.getValue();
					if (value != null)
					{
						return value;
					}
				}
				else if (values[i] instanceof UnitValueDefaultView && values[i].getDefinition().getName().equals(def))
				{
					String unitStr = "";
					MeasurementSystemCache.setCurrentMeasurementSystem("SI");
					String measurementSystem = MeasurementSystemCache.getCurrentMeasurementSystem();
					UnitDefView definition1 = ((UnitValueDefaultView)values[i]).getUnitDefinition();
					QuantityOfMeasureDefaultView quantityofmeasuredefaultview = definition1.getQuantityOfMeasureDefaultView();
					if (measurementSystem != null)
					{
						//�õ����Զ���ĵ�λ
						unitStr = definition1.getDisplayUnitString(measurementSystem);
						//�õ�������λ�е���ʾ��λ
						if (unitStr == null)
						{
							unitStr = quantityofmeasuredefaultview.getDisplayUnitString(measurementSystem);
						}
						//�õ�������λ�е�����
						if (unitStr == null)
						{
							unitStr = quantityofmeasuredefaultview.getDefaultDisplayUnitString(measurementSystem);
						}
					}
					
					DefaultUnitRenderer defaultunitrenderer = new DefaultUnitRenderer();
					String ss = "";
					try
					{
						ss = defaultunitrenderer.renderValue(((UnitValueDefaultView)values[i]).toUnit(), ((UnitValueDefaultView)values[i]).getUnitDisplayInfo(measurementSystem));
					}
					catch (UnitFormatException _ex)
					{
						_ex.printStackTrace();
					}
					catch (IncompatibleUnitsException _ex)
					{
						_ex.printStackTrace();
					}
					//����ֵ�͵�λ
					if(unitStr==null||unitStr.equals(""))
					{
						return ss;
					}
					else
					{
						return ss+"("+unitStr+")";
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		return null;
	}
	
	
	/**
	 * �����ļ���ָ��Ŀ¼��
	 */
	private void downloadPDF(String filePath, ApplicationDataInfo appInfo)throws Exception 
	{
		System.out.println("downloadPDF:"+filePath);
		FileOutputStream fos = null;
		try
		{
			byte[] content = null;
			if(fileVaultUsed)
			{
				ContentClientHelper helper = new ContentClientHelper();
				content = helper.requestDownload(appInfo.getBsoID());
			}
			else
			{
				//��ȡ��ID
				String streamID = appInfo.getStreamDataID();
				StreamDataInfo result = StreamUtil.getInfoHasContent(streamID);
				if(result==null)
				{
					return;
				}
				content = result.getDataContent();
			}
			
			FileOutputStream out = new FileOutputStream(new File(filePath));
			out.write(content);
			out.close();
		}
		catch (FileNotFoundException ex1)
		{
			ex1.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	/**
   * ͨ��ftp��ʽ�ϴ��ļ��ļ���
   * @param file �ϴ����ļ���
   * @throws Exception
   */
  private static void ftpLoad(File f)throws Exception
  {
		System.out.println("ftpLoad  f:"+f.getPath());
  	try
  	{
  		String ftpHost = RemoteProperty.getProperty("eol.ftp.url","");
  		int ftpPort = 21;//RemoteProperty.getProperty("eol.ftp.port","");
  		String ftpUserName = RemoteProperty.getProperty("eol.ftp.user","");
  		String ftpPassword = RemoteProperty.getProperty("eol.ftp.password","");
  		String ftpPath = RemoteProperty.getProperty("eol.ftp.path","");
  		if(f.exists())
  		{
      	FTPClient ftp = new FTPClient();
      	int reply;
      	ftp.setControlEncoding("gb2312");
      	ftp.connect(ftpHost, ftpPort);
      	ftp.login(ftpUserName, ftpPassword);
      	ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
      	reply = ftp.getReplyCode();
      	if (!FTPReply.isPositiveCompletion(reply))
      	{
      		System.out.println("FTPĿ��������޷����ӣ�");
      		ftp.disconnect();
      	}
      	boolean f1 = ftp.changeWorkingDirectory(ftpPath);
		System.out.println("ftpLoad  f1:"+f1);
      	upload(f, ftp);
      	ftp.disconnect();
  		}
  	}
  	catch(Exception e)
  	{
  		System.out.println("�����ļ���"+f.getName()+" ʱ����");
  		//e.printStackTrace();
  		throw new Exception(e);
  	}
  }
  
  private static void upload(File file, FTPClient ftp) throws Exception
  {
  	if(file.isDirectory())
  	{
  		String[] files = file.list();
  		for (int i = 0; i < files.length; i++)
  		{
  			File file1 = new File(file.getPath()+File.separator+files[i] );
  			if(file1.isDirectory())
  			{
  				upload(file1, ftp);
  				ftp.changeToParentDirectory();
  			}
  			else
  			{
  				File file2 = new File(file.getPath()+File.separator+files[i]);
  				//System.out.println("eolftp11: file is "+file2.getPath());
  				FileInputStream input = new FileInputStream(file2);
  				ftp.storeFile(new String(file2.getName().getBytes("GBK"), "iso-8859-1"), input);
  				input.close();
  			}
  		}
  	}
  	else
  	{
  		File file2 = new File(file.getPath());
  		//System.out.println("eolftp22: file is "+file2.getPath());
  		FileInputStream input = new FileInputStream(file2);
  		ftp.storeFile(new String(file2.getName().getBytes("GBK"), "iso-8859-1"), input);
  		input.close();
  	}
  }
	//CCEnd SS38
	
	//CCBegin SS39
	public HashMap getSubPartRoute(QMPartIfc productInfo) throws QMException
	{
    if (VERBOSE)
    {
      System.out.println("����TechnicsRouteService: getSubPartRoute ������" + productInfo.getBsoID());
    }
    HashMap map = new HashMap();
    StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
    Collection c = partService.getSubParts(productInfo);
    for (Iterator ite = c.iterator(); ite.hasNext(); )
    {
      QMPartIfc partInfo = (QMPartIfc) ite.next();
      if (!map.containsKey(partInfo.getPartNumber()))
      {
        Collection route = (Collection)getRouteInfomationByPartmaster(partInfo.getMasterBsoID());
        String[] partroute=new String[2];
        if(route!=null&&route.size()>0)
        {
        	String[] routestr = (String[])(route.iterator().next());
        	partroute[0]=routestr[0];
        	partroute[1]=routestr[1];
        	System.out.println(partInfo.getPartNumber()+"===="+Arrays.toString(partroute));
        }
        map.put(partInfo.getPartNumber(), partroute);
      }
    }
    return map;
  }
	//CCEnd SS39
	
  //CCBegin SS40
  private ProductAttributesInfo findPartAttr(QMPartIfc partInfo) throws QMException{
	  
	  
	  StandardPartService sPervice = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
		
	  IBAValueService IBAservice = (IBAValueService)EJBServiceHelper.getService("IBAValueService");
	  
	  ProductAttributesInfo productAttriInfo=new ProductAttributesInfo();
	  
	  
	  String partNum= partInfo.getPartNumber();
	  String partName=partInfo.getPartName();
	  
	  Vector partMasterInfoVec=(Vector)sPervice.findPartMaster(partName,partNum);
	  QMPartMasterIfc myPartInfo=(QMPartMasterIfc)partMasterInfoVec.get(0);
	  Vector partVec=(Vector)sPervice.findPart(myPartInfo);
	  
	  QMPartIfc lastPart=(QMPartIfc)partVec.get(0);
	  
	  HashMap ibaMap=IBAservice.getIBADefinitionAndValues(lastPart);
	  
	  //CCBegin SS42
	  
	          ArrayList list0=(ArrayList)ibaMap.get("FaceTreatment");
		 String FaceTreatment="";
		 if(list0!=null)
	          FaceTreatment= (String)((ArrayList)ibaMap.get("FaceTreatment")).get(0);
	          
	          System.out.println("FaceTreatment====1111111111============="+FaceTreatment);
		 
		 ArrayList list1=(ArrayList)ibaMap.get("Color");
		 String Color="";
		 if(list1!=null)
		  Color=(String)((ArrayList)ibaMap.get("Color")).get(0);
		 
		 ArrayList list2=(ArrayList)ibaMap.get("ProductValidate");
		 String ProductValidate= "";
		 if(list2!=null)
		   ProductValidate= (String)((ArrayList)ibaMap.get("ProductValidate")).get(0);
		 
		 ArrayList list3=(ArrayList)ibaMap.get("HeatTreatment");
		 String heatTreatment= "";
		 if(list3!=null)
		   heatTreatment=(String)((ArrayList)ibaMap.get("HeatTreatment")).get(0);
		 
		 ArrayList list4=(ArrayList)ibaMap.get("ScrewThread");
		 String ScrewThread= "";
		 if(list4!=null)
		  ScrewThread= (String)((ArrayList)ibaMap.get("ScrewThread")).get(0);
		 
		 //CCEnd SS42
	 
	 productAttriInfo.setSurfaceGuard(FaceTreatment);
	 productAttriInfo.setColour(Color);
	 productAttriInfo.setProductSecondCheck(ProductValidate);
	 productAttriInfo.setHeatTreatment(heatTreatment);
	 productAttriInfo.setArabesquitic(ScrewThread);
	 
	 return  productAttriInfo;
  }
  //CCEnd SS40
}