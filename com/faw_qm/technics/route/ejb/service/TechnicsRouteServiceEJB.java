/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * CR1 2009/02/26  郭晓亮  原因：优化检出工艺路线表功能
 *  
 *                         方案：1.不调用duplicate()方法去复制原本关联对象，直接将关联对象的
 *                                 bsoid,createTime,modifyTime属性设为null；
 *                                 
 *                               2.将getRouteListLinkParts()方法合并到copyRouteList()方法中，
 *                                 减少对同一个集合的循环次数，并将持久化服务的
 *                                 查询和保存方法替换为不发信号.
 *                               
 *                               3.在显示被检出的路线表时将查找关联逻辑在
 *                                 检出操作中分离出去,只有选择"零部件"TAB页时才去查关联.
 *                         
 *                         备注: 性能测试用例名称"检出工艺路线表".  
 *                            
 *                                 
 *CR2 2009/02/18  郭晓亮   原因：优化删除工艺路线表功能。
 * 
 *                         方案：在删除路线信息时不再对修改后的关联对象进行保存,
 *                               删除关联对象换成在持久化里操作.
 *                       
 *                         备注: 性能测试用例名称"删除工艺路线表". 
 *                         
 *                         
 *CR3 2009/04/10  郭晓亮   原因: 优化创建工艺路线表功能
 *                         
 *                         方案:1.隔离掉不属于创建的逻辑.
 *                               
 *                         备注: 性能测试用例名称"创建工艺路线表".    
 *                         
 *CR4 2009/04/22 郭晓亮    原因:重命名路线表时编号不是唯一时的提示信息不正确。
 *                         方案：自己处理异常。
 *                         
 *CR5 2009/06/3  郭晓亮    参见:测试域:v4r3FunctionTest;TD号2260
 *
 *
 *CR6 2009/06/16 郭晓亮    参见：测试域:v4r3FunctionTest;TD号2413
 *
 * *CR7 2010/06/29 郭晓亮    参见: 域:product;TD号2275
 *
 * CCBegin by liunan 2012-04-25 打补丁v4r3_p044
 * CR8 2012/03/21 吕航 原因 统一重名权限
 * CCEnd by liunan 2012-04-25
 * SS1 复制自，粘贴的路线，如果说明中没有 源版本信息，则获取零部件源版本后保存到说明中，规则与创建路线时一致。 liunan 2012-06-14
 * SS2 报毕报废表 routecompletepart 新增列 unit单位（解放 D31）,partunitversion零部件版本，此处新增获取零部件版本的代码。 liunan 2013-1-6
 * SS3 添加路线是否自动获取最新路线的复选框标识，默认选中，即自动添加最新路线，然后在路线编辑界面由用户决定是否修改。 liunan 2013-4-17
 * SS4 按照采用或更改号搜索零件时，获取最新版零件，避免有些前准、生准都有采用号情况搜到两个同号零件的问题。 liunan 2013-4-8
 * SS5 获取最新零部件的路线时，不取“取消”状态的路线。 liunan 2013-6-3
 * SS6 按列表顺序批量搜索返回零部件结果。 liunan 2013-11-25
 * SS7 新增“轴”路线时，对“轴齿中心接收组”和“轴齿中心权限组”的授权。 liunan 2014-6-23
 * SS8 新增改(锡)和岛(沪)路线，对“无锡改装车接收组”和“无锡改装车权限组”还有“上海改装车接收组”和“上海改装车权限组”进行授权。 liunan 2014-8-14
 * SS9 通过更改通知单查找零部件 liuyang 2014-6-6
 * SS10 存储用于产品零部件BSOID liuyang 2014-6-6 
 * SS11 路线关联零部件增加颜色标识 liuyang 2014-6-9
 * SS12 添加另存为路线复选框 liuyang 2014-6-10
 * SS13 查找零部件另存为之前的零部件 liuyang 2014-6-10
 * SS14 路线显示卡车厂路线单位在前 liuyang 2014-6-12
 * SS15 一级结构报表增加“颜色件标识” liuyang 2014-6-13
 * SS16 新增“轴”路线时，对“轴齿中心接收组”和“轴齿中心权限组”的授权。 liunan 2014-6-23
 * SS17 路线类别为“工艺合件”时，自动编号 liuyang 2014-6-29 
 * SS18 按照解放采用/变更单添加零部件时，只需要采用列表中的零部件 徐德政 2014-08-29
 * SS19 前准和艺准通知书关联零部件类型为工艺合成设置生命周期状态  liuyang 2014-6-3
 * SS20 通过采用单号查找关联零部件 liuyang 2014-6-5
 * SS21 获得路线串，判断是否包含卡车厂路线 徐德政 2014-10-15
 * SS22 艺准按照变更添加零部件，带回来已取消零部件。 liunan 2014-12-24
 * SS23 处理回导零部件的废弃状态设置。 liunan 2015-1-12
 * SS24 新增成都的“川”路线，对“成都接收组”和“成都权限组”进行授权。 liunan 2015-2-11
 * SS25 重命名，对各小版本中艺准编号名称赋值时不再判断权限，此前重命名有权限，走到这就是说明有权限了。 liunan 2015-2-27
 * SS26 变更通知单中不显示变化方式是“结构变更”的通知单  高义升 2015-04-28
 * SS27 变速箱批量导入过一批路线，解放编路线时自动获取了该路线，里面路线单位都是变速箱单位的名称，轴显示是“轴齿车间”，因此授权不了轴齿。 liunan 2015-6-1
 * SS28 添加附件。 liunan 2015-6-18
 * SS29 A004-2015-3163 如果用于产品没有读权限，在添加时是找的master不走权限，但保存时会判断，此处增加提示，无法得到该件。 liunan 2015-7-6
 * SS30 SS29补充修改，如果零部件是带回最新路线，则状态内容对应不上，partindex里是“无”，而路线里会有状态。改为保存时获取一下已有路线的状态，与后面一致。 liunan 2015-8-5
 * SS31 艺准按路线授权失败，因为中间有直接返回分支。 liunan 2015-10-28
 * SS32 计算数量，如果获取不到零部件也要给数量0。 liunan 2015-12-23
 * SS33 回导路线，如果没有源版本，就去本身版本。 liunan 2016-3-22
 * SS34 艺准保存时，如果零部件不符合配置规范则出现空指针问题。 liunan 2016-4-7
 * SS35 按路线授权，历史数据存在保存的节点是coding的情况。 liunan 2016-7-28
 * SS36 根据零部件获取最新路线串 liunan 2016-8-4
 * SS37 保存生产准备项目 liuyuzhu 2016-05-23
 * SS38 A004-2016-3415 实现PDM电控数据及分类属性参数自动发布到EOL系统 liunan 2016-9-7 属性发中间表 2016-11-14
 * SS39 A004-2016-3433 新增路线提醒功能，用于获取子件的装配路线集合。 liunan 2016-10-28
 * SS40 自动带出产品属性 guoxiaoliang  2017-04-14
 * SS41 查找相同的零件如果相同在界面上标记相同颜色  guoxiaoliang  2017-04-14
 * SS42 设置生准零件变更号和采用号   guoxiaoliang  2017-05-10
 * SS43 解放生准查找其他关联  guoxiaoliang 2017-07-12
 * SS44 按路线授权，增加对零部件的下载权限授予。 liunan 2017-6-8
 * SS45 A004-2017-3575 路线“专”对应“长特权限组”和“长特接收组” liunan 2017-6-21
 * SS46 获取试制任务单中发给解放的试制零部件。 liunan 2017-6-28
 * SS47 如果是汽研试制件，则默认加上制造路线“研”。 liunan 2017-7-3
 * SS48 解放生准新需求  guoxiaoliang 2017-07-12
 * SS49 EOL参数约定零部件规则调整。 liunan 2017-7-24
 * SS50 生准修改的问题 guoxiaoliang 2017-09-6
 * SS51 向EOL系统新增发布2个属性 传感器每转脉冲数 传动轴与车速传感器速比 liunan 2017-9-25
 * SS52 设置零件生命周期状态为生准 guoxiaoliang 2017-09-13
 * SS53 修改生准零件重名问题 guoxiaoliang 20171106
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

//CCBegin by liunan 2012-04-25 打补丁v4r3_p044
import com.faw_qm.enterprise.ejb.service.EnterpriseService;
//CCEnd by liunan 2012-04-25

//CCBegin by liunan 2012-05-29 获得该件的EPM文档，并设置状态。
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

// * 参考文档：
// * Phos-REQ-CAPP-BR02(工艺路线业务规则)V2.0.doc
// * PHOS-REQ-CAPP-SRS-002(制造业企业基础数据管理需求规格说明-工艺路线) V2.0.doc
// * "Phos-CAPP-UC301--Phos-CAPP-UC322"共19个用例。
// *  (问题零) 200605 zz 周茁修改 修改原因:查询零部件件关联的路线,应该是被采用的路线
// * (问题一)20060609 zz 周茁修改 修改原因:优化查看路线的关联零部件,修改为两表连查,减少中间对象的生成
// * (问题二)20060701 zz 周茁修改 修改原因:优化按单位搜索路线,将多次配置规范的过滤改为整体一次过滤.
// * (问题三)20060629 zz 周茁修改 修改原因:工艺路线生成报表操作速度慢,同时修改了生成报表的jsp
// * （问题四）2006 07 12  zz 周茁添加 性能优化 ，同时修改了CompareHandler
// * （问题五）2006 08 03  zz 周茁添加 性能优化 ，在工艺路线串增加了一个属性保存路线串字符串
// * 瘦客户端显示工艺路线串的时候可以直接取出次字符串显示
// * （问题六） 2006 08 04  zz 周茁修改 经过这个方法算法。增加了重复元素bug
// * （问题七） 2006 09 04 zz 周茁修改 修订后新版路线表里的关联零部件的路线状态原为采用的变为取消了，
// * 根据需求应该复制前一版本的状态
// * (问题八)20061110 zz 周茁添加,为二级路线 "添加"按钮 添加根据所选单位过滤功能
// * (问题九)周茁添加 zz 添加集体调用方法，减少客户端调用次数
// * （问题十）增加工艺路线的重命名功能 周茁添加 zz 20061214

/**
 * 提供对工艺路线表的 创建，更新，删除，查看的服务
 * @author  赵立彬
 * @version 1.0
 */
public class TechnicsRouteServiceEJB
    extends BaseServiceImp { /////////////////有的多表联查可能不需要distinct.
  public final static boolean VERBOSE = Boolean.valueOf(RemoteProperty.
      getProperty(
      "com.faw_qm.technics.route.verbose", "true")).booleanValue();

  //public static String TIME = "< " + DateFormat.getInstance().format(new java.util.Date()) + " > ";
  public static String TIME = "< " + new java.util.Date().toString() + " > ";

  //关联状态。
  public static final int INHERIT = 0;
  public static final int ROUTEALTER = 1;
  public static final int PARTDELETE = 2;

  public static final String LEFTID = "leftBsoID";
  public static final String RIGHTID = "rightBsoID";
  public static final String VERSION_SERVICE = "VersionControlService";
  public static final String CONFIG_SERVICE = "ConfigService";
  public static final String PART_SERVICE = "StandardPartService";
  //多此一举
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

//////////////////////////////////以下为测试方法/////////////////////////////////////
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

//////////////////////////////////测试方法结束////////////////////////////////////
  /**
   * getServiceName
   *
   * @return String
   */
  /**
   * 获得服务名
   * @return TechnicsRouteService
   */
  public String getServiceName() {
    return "TechnicsRouteService";
  }


  /**
   * 保存工艺路线列表。非空检查在值对象中已做。唯一性检查在数据库中设置。
   * @param routeListInfo TechnicsRouteListIfc 指定的路线表值对象
   * @throws QMException
   * @return TechnicsRouteListIfc 工艺路线表的值对象
   * @see TechnicsRouteListInfo 指定的路线表值对象
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
            "routeService's storeRouteList : routeListInfo不应被持久化。");
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
        	throw new TechnicsRouteException("无法得到用于产品的零部件，请检查该件的状态是否发布！");
        }
        //CCEnd SS29
        routeListInfo.setProductID(partInfo.getBsoID());
        //CCEnd SS10
        //CCBegin SS17
        if(routeListInfo.getRouteListState().equals("工艺合件")){
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
          //判断唯一性。
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
          System.out.println(TIME + "开始保存路线表和产品的关联。");
          //此时routeListInfo已经被保存。建立路线表和产品的关联。此关联只能创建和删除。删除由持久化维护。
        }
        RouteListProductLinkInfo listProductInfo = new
            RouteListProductLinkInfo();
        //if(routeListInfo1.getMaster() == null || routeListInfo1.getMaster().getBsoID() == null)
        //throw new TechnicsRouteException("routeService's storeRouteList 路线表的master没有被建立。");
        listProductInfo.setRouteListMasterID(routeListInfo1.getMasterBsoID());
        if (routeListInfo1.getProductMasterID() == null) {
          throw new TechnicsRouteException(
              "routeService's storeRouteList 产品的masterID没有被设置。");
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
   * 更新工艺路线表。只进行简单存储。
   * @param routeListInfo TechnicsRouteListIfc 路线表值对象
   * @throws QMException
   * @return TechnicsRouteListIfc  工艺路线表值对象
   */
  private TechnicsRouteListIfc updateRouteList(TechnicsRouteListIfc
                                               routeListInfo) throws
      QMException {
    TechnicsRouteListIfc routeListInfo1 = null;
    if (!PersistHelper.isPersistent(routeListInfo)) {
      throw new TechnicsRouteException(
          "routeService's storeRouteList : routeListInfo没有被持久化。");
    }
    //更新时不能更改唯一属性，不需要做SQLException封装。
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    routeListInfo1 = (TechnicsRouteListIfc) pservice.updateValueInfo(
        routeListInfo);
    return routeListInfo1;
  }


  /**
   * 删除工艺路线表，整个大版本内的小版本全部删除。
   * @param routeListInfo TechnicsRouteListIfc  工艺路线表值对象 根据指定的值对象删除路线表
   * @throws QMException
   * @see TechnicsRouteListInfo 工艺路线表值对象
   */
  public void deleteRouteList(TechnicsRouteListIfc routeListInfo) throws
      QMException {
    ///////////////有版本删除整个分支  2004.9.10 赵立彬
    ///////////////可以调用不发信号的删除,自己处理相关删除. 效率更高.
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    pservice.deleteValueInfo(routeListInfo);
    /*
     //找到routeListID对应的所有小版本。
     VersionControlService vservice = (VersionControlService)EJBServiceHelper.getService(VERSION_SERVICE);
     Collection vec1 = vservice.iteratiosaveRoutensOf(routeListInfo);
     if(VERBOSE)
       System.out.println(TIME+"deleteRouteListener 所有的小版本："+vec1);
     //删除在此版本路线表中建立的路线。
     //利用监听删除路线。虽效率低，此处暂不处理。
     /////////////？？？？？此时还有一个好处：可以恢复到删除前的状态。但此状态是否恢复。
     //遍历删除。
     PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
     Iterator iter1 = vec1.iterator();
     if(iter1.hasNext())
       pservice.deleteValueInfo((BaseValueIfc)iter1.next());*/
  }

  /**
   * 编辑零部件表,执行者在更新工艺路线表时，编辑要编制工艺路线的零部件表，可以添加零部件、删除零部件。
   * 添加零部件时，可以根据路线表中的"用于产品"的零部件，按其最新结构中提供备选零部件表，用户可以从中
   * 选择要添加到工艺路线表中的零部件。如果用户正在编辑的是二级路线表，则备选零部件表不仅应按用于产品
   * 的结构，还应按此产品所有零部件中包含本单位路线的一级路线进行进一步筛选，从而获得一份备选零部件表。
   * @param codeID String 路线表单位，根据单位名称的代码ID和路线级别获得可选择的零部件。
   * @param level String 路线级别显示名。例：一级路线或二级路线
   * 如果当前编辑的工艺路线表是一级工艺路线表，则系统应列出产品结构中的所有零部件；
   * 如果当前编辑的工艺路线表是二级工艺路线表，则系统应根据路线表的单位属性，列出产品结构中所有一级路线有对应单位的零部件，作为备选零部件。
   * @param productMasterID String 用于产品MasterID
   * @throws QMException
   * @return Collection  存放的是vector:<br>
   * vector中包含刷新后的partMasterID <br>
   * partMasterID是过滤后产品子件的master值对象。
   */
  public Collection getOptionalParts(String codeID, String level,
                                     String productMasterID) throws QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getOptionalParts : level = " +
                         level);
    }
    //如果是一级路线。
    Collection result = new Vector();
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
           //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
        QMPartIfc productInfo = (QMPartIfc) getLastedPartByConfig(productMasterID,getCurrentUserConfigSpec());
   // QMPartIfc productInfo = (QMPartIfc) getLatestVesion(productMasterID);
       //CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
    if (level.equalsIgnoreCase(RouteListLevelType.FIRSTROUTE.getDisplay())) {
      //获得子件。
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
      //进行过滤，获得给定产品的子件。
      for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
        ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
            next();
        String partMasterID = listLinkInfo.getPartMasterID();
        //进行子件过滤。
        boolean flag = isChildPart(productInfo, partMasterID);
        if (flag) {
          result.add(pservice.refreshInfo(partMasterID));
        }
      }
    }
    else {
      throw new TechnicsRouteException("传入路线等级不正确");
    }
    if (VERBOSE) {
      System.out.println(TIME +
                         " RouteService getOptionalParts's result = " +
                         result);
    }
    return result;
  }
/**
   * 通过路线表编号获得路线表值对象
   * @param routeListNum 路线表编号
   * @return 路线表值对象
   * @author 郭晓亮
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
   * 对给定的零部件进行检查，检查这些零部件是否符合添加到二级路线的零部件表
   * 如果当前编辑的工艺路线表是二级工艺路线表，则系统应根据路线表的单位属性，
   * 列出产品结构中所有一级路线有对应单位的零部件，作为备选零部件。
   * @param codeID String 二级路线表的单位
   * @param productMasterID String 二级路线表用于的产品
   * @param subPartMasters Collection 需进行检查的零部件集合（要求元素为零部件主信息值对象）
   * @throws QMException
   * @return Object[] 数组中包含二个元素:<br>Object[0]:第一个元素是符合条件的零部件主信息集合;<br>
   * Object[1]:第二个元素是不符合条件的零部件主信息集合
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
    //进行过滤，获得给定产品的子件。
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      String partMasterID = listLinkInfo.getPartMasterID();
      //进行子件过滤。
      boolean flag = isChildPart(productInfo, partMasterID);
      if (flag && !masteridVector.contains(partMasterID)) {
        masteridVector.add(partMasterID);
      }
    } //至此，从数据库中找到了所有符合条件的零部件

    Vector v1 = new Vector(); //用于装载符合条件的零部件
    Vector v2 = new Vector(); //用于装载不符合条件的零部件
    //下面进行对比，
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
   * 获得指定零部件（主信息）的下一级零部件（主信息）的集合
   * @param partMasterInfo QMPartMasterIfc 指定零部件（主信息）值对象
   * @throws QMException
   * @return Collection 存放的是HashMap:<br>
   * key:partMasterBsoID <br>value:Master
   * 下一级零部件（主信息：partInfo.getMaster()）的集合
   * @see QMPartMasterInfo
   */
  public Collection getSubPartMasters(QMPartMasterIfc partMasterInfo) throws
      QMException {
    if (VERBOSE) {
      System.out.println("进入TechnicsRouteService: getSubPartMasters 参数：" +
                         partMasterInfo.getBsoID());
    }
    HashMap map = new HashMap();
    QMPartIfc productInfo = (QMPartIfc) getLatestVesion(partMasterInfo);
    if (VERBOSE) {
      System.out.println("产品最新版本：" + productInfo.getBsoID());
    }
    StandardPartService partService = (StandardPartService)
        EJBServiceHelper.
        getService(PART_SERVICE);
    Collection c = partService.getSubParts(productInfo);
    if (VERBOSE) {
      System.out.println("Part服务结束！！");
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

  //获得给定产品的所有子件。不包括自身。
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
    //因为返回值包括产品本身，去掉。
    for (int i = 0; i < result.length; i++) {
      QMPartIfc partInfo = (QMPartIfc) result[i];
      if (!partInfo.getBsoID().equals(productInfo.getBsoID())) {
        subs.add(partInfo);
      }
    }
    return subs;
  }

  //判断给定partMasterID是否是产品的子件。
  private boolean isChildPart(QMPartIfc productInfo, String partMasterID) throws
      QMException {
    boolean flag = false;
    Collection result = getAllSubParts(productInfo);
    //遍历，比较。
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
   * 获得最新版本的值对象。
   * @param masterInfo MasterIfc Master值对象
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
//CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线     	//
 Object obj =  iter1.next();
      if(obj instanceof Object[])
      {
        info = (BaseValueIfc) ((Object[])obj)[0];
      }
      else
      {
        info = (BaseValueIfc) obj;
      }
//CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线
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
   * 获得最新版本值对象的集合
   * @param c Collection  master对象集合
   * @throws QMException
   * @return Collection 存放obj[]数组：<br>obj[0]:最新版本值对象
   */
  //(问题三)20060629 周茁修改
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
   * 获得最新版本值对象的集合
   * @param c Collection master对象集合
   * @throws QMException
   * @return HashMap key:零件编号 value：最新版本值对象
   */
  //   *add by guoxl on 20080307(查看一级路线表报表时，序号排序错误)
  //   *所以添加方法getLatestsVersion1
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
   * 更新工艺路线表:保存或删除路线表和零件的关联。
   * 保存工艺路线表与零件的关联。
   * 由客户端传入关联类集合，做统一处理。
   * @param saveCollection HashMap 所有需要保存的零件masterID.
   * @param deleteCollection HashMap 所有需要删除的零件masterID.
   * @param routeListInfo1 TechnicsRouteListIfc  工艺路线表ID.
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
    //更新路线表
    TechnicsRouteListIfc routeListInfo = updateRouteList(routeListInfo1);
    if (VERBOSE) {
      if (routeListInfo1.getBsoID() == routeListInfo.getBsoID()) {
        System.out.println("更新后所获得的对象和以前的对象的bsoid一样");
      }
      else {
        System.out.println("更新后所获得的对象和以前的对象的bsoid不一样");
      }
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    try {
      //Set  set =  saveCollection.keySet();
      //遍历要保存的零件集合，保存新建关联。
      //while(set.iterator())
    	//Begin CR3
		QMQuery myQuery = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
		QueryCondition linkCond = new QueryCondition(LEFTID,
		        QueryCondition.EQUAL, routeListInfo.getBsoID());
		myQuery.addCondition(linkCond);

		Collection linkColl = pservice.findValueInfo(myQuery); 
		//End CR3
    	
      for (Iterator iter = saveCollection.keySet().iterator(); iter.hasNext(); ) {
        //st skybird 2005.3.4 保存父件所做出的修改
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
        	//查找是否有删除状态的关联。删掉。
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
              "saveListRoutePartLink'saveCollection：同一个零件在一个路线表版本中不能有两个关联，请重新设置关联。");
        }
        Iterator iter1 = coll.iterator();
        if (iter1.hasNext()) {
          ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)
              iter1.next();
          //删除关联。
          pservice.deleteValueInfo(listLinkInfo);
        }
       }
        ListRoutePartLinkInfo listLinkInfo = ListRoutePartLinkInfo.
            newListRoutePartLinkInfo(routeListInfo,
                                     partMasterID);
        //st skybird 2005.3.4 保存父件号
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
      //遍历要删除的零件集合，做删除关联标记。
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

        //升序
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
                             "routeSevice's saveListRoutePartLink 原有关联集合： coll = " +
                             coll);
          //因为有可能新添加零件。所以是2
        }
        if (coll.size() > 2) {
          if (VERBOSE) {
            System.out.println(TIME + coll);
          }
          throw new TechnicsRouteException(
              "saveListRoutePartLink's deleteCollection：不能多于两个关联，请重新选择零件。");
        }
        //做删除关联标记或删除关联。
        Iterator iter1 = coll.iterator();
        if (iter1.hasNext()) {
          ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)
              iter1.next();
          if (listLinkInfo.getAlterStatus() == INHERIT) {
            //skybird这段代码完全有问题
            //设置以前的版本为取消状态。
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
            //////////////////排除自己, 否则有可能造成时间戳问题 2004.9.11 赵立彬
            query1.addAND();
            QueryCondition cond5 = new QueryCondition(LEFTID,
                QueryCondition.NOT_EQUAL,
                routeListInfo.getBsoID());
            query1.addCondition(cond5);
            query1.addAND();
            ////////////////////////    应保证在同一个分支内        2004.9.11 赵立彬  versionID=A 不是A.1 A.2
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
                  "saveListRoutePartLink：不能有两个采用状态");
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
              //skybird感觉这个顺序不对
              deleteRoute(listLinkInfo);
              listLinkInfo.setRouteID(null);
            }
          }
          else {
            throw new TechnicsRouteException(
                "saveListRoutePartLink：处于删除状态的零件不应出现。");
          }
          //没有新建关联，设置删除标记。
          if (coll.size() == 1) {
            if (VERBOSE) {
              System.out.println(TIME +
                                 "if(coll.size() == 1) 没有新建关联，设置删除标记。");
            }
            Collection preLinks = this.searchPreLink(routeListInfo,
                partMasterID, panrentID);
            if (preLinks != null && preLinks.size() > 0) {
              if (VERBOSE) {
                System.out.println(TIME + "以前版本中有此关联，所以设置为已经删除状态");
              }
              listLinkInfo.setAlterStatus(PARTDELETE);
              //被删除后，是否采用状态设置为取消。
              listLinkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.
                                          getDisplay());
              pservice.saveValueInfo(listLinkInfo);
            }
            else {
              if (VERBOSE) {
                System.out.println(TIME + "以前版本中没有此关联，所以删除次关联");
              }
              pservice.deleteValueInfo(listLinkInfo);
            }
          }
          //已有新建关联。//删除此关联。
          else if (coll.size() == 2) {
            if (VERBOSE) {
              System.out.println(TIME +
                                 "else if(coll.size() == 2) 已有新建关联。//删除此关联。");
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
  
  //CCBegin by leixiao 2008-8-5 原因：解放升级工艺路线
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
    //更新路线表
  
    TechnicsRouteListIfc routeListInfo = updateRouteList(routeListInfo1);
    if (VERBOSE) {
      if (routeListInfo1.getBsoID() == routeListInfo.getBsoID()) {
        System.out.println("更新后所获得的对象和以前的对象的bsoid一样");
      }
      else {
        System.out.println("更新后所获得的对象和以前的对象的bsoid不一样");
      }
    }

    //从数据库直接取出关联零件的数量
    HashMap countMap = new HashMap();
    //CCBegin SS11
    HashMap color = new HashMap();
    //CCEnd SS11
    Vector indexVector = routeListInfo.getPartIndex();
    
    //校验传来零部件是否与partindex中个数一致
    int picount = 0;//indexVector里的数量
    int acount = 0;//新增的数量
    int ucount = 0;//更新的数量
    int dcount = 0;//删除的数量
    int ycount = 0;//路线原数量
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
    System.out.println("保存艺准"+routeListInfo.getRouteListNumber()+"("+routeListInfo.getBsoID()+")时各数量为："+picount+"=="+acount+"=="+ucount+"=="+dcount+"=="+ycount);
    if(picount!=(acount+ycount-dcount))
    {
    	System.out.println("数量不对，可能零部件列表已经丢件！！！");
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
        countMap.put(key, ids[2]); //关联零件有重复的可能，需要加以区别
        //CCBegin SS11
        color.put(key, ids[4]); 
        //CCEnd 11
      }
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    try {


      //遍历要删除的零件集合，做删除关联标记。
       for (Iterator iter = deleteCollection.keySet().iterator(); iter.hasNext(); )
       {
         Object[] tmp = (Object[]) deleteCollection.get(iter.next());
         String partMasterID = (String) tmp[0];
         QMPartIfc parentPart = (QMPartIfc) tmp[1];
         String routeID = (String) tmp[2];
//	      CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
         String partID= (String) tmp[3];
//         System.out.println("partid="+partID);
//	      CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
         String panrentID = null;
         if (parentPart != null) {
           panrentID = parentPart.getBsoID();
         }
//         System.out.println("进入删除： PartMasterID = "+partMasterID);
//         System.out.println("       ： routeID = "+routeID);
//         System.out.println("       ： panrentID = "+panrentID);
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
//	      CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id 
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
//	      CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id  
         /**20070912 注释掉 因为此条件无用 liuming
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
           //System.out.println("删除： Part = "+listLinkInfo.getPartMasterInfo().getPartNumber());
           //System.out.println("删除前： link = "+listLinkInfo.getBsoID());
           pservice.deleteValueInfo(listLinkInfo);
//           System.out.println("删除后： link = "+listLinkInfo);
         }
       }



      for(Iterator iter = updateLinksList.iterator();iter.hasNext();)
      {
        String linkID = iter.next().toString();
        ListRoutePartLinkInfo linkInfo = (ListRoutePartLinkInfo)pservice.refreshInfo(linkID,false);
        String partmasterID = linkInfo.getPartMasterID();

        //开始处理关联零件在车型里的的使用数量
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
        //////////////处理数量完毕
        linkInfo.setCount(countInProduct);
       // System.out.println("更新： Part = "+linkInfo.getPartMasterInfo().getPartNumber());
        pservice.updateValueInfo(linkInfo,false);
       // System.out.println("更新后： PartMaster = "+linkInfo.getPartMasterInfo().getPartNumber()+"    count = "+linkInfo.getCount());
      }

      for (Iterator iter = saveCollection.keySet().iterator(); iter.hasNext(); )
      {
    	
        Object[] tmp = (Object[]) saveCollection.get(iter.next());

        String partMasterID = (String) tmp[0];
        QMPartIfc parentPart = (QMPartIfc) tmp[1];
//      CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id
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
//      CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线，记录当前零件id  
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
        //在关联类中不保存数量了
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

        //System.out.println("新建： PartMaster = "+partMasterID+"    count = "+countInProduct);
       listLinkInfo.setCount(countInProduct);*/
    //////处理数量结束


        listLinkInfo =(ListRoutePartLinkInfo) pservice.saveValueInfo(listLinkInfo);
        //复制路线
        //CCBegin SS3
        //if(iscomplete.equals("y")){
        if(iscomplete.equals("y")||addRouteFlag){
        //CCEnd SS3
       //System.out.println("------艺毕,获得复制源 零件="+partMasterID+" "+partid+" routeid="+routeid);
        //按零件添加的情况	
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
            
            //System.out.println("获取零部件已有路线SQL："+query1.getDebugSQL());
            //CCEnd SS5
            
           Collection c=(Collection) pservice.findValueInfo(query1, false);
           Iterator i=c.iterator();
           if(i.hasNext()){
        	   ListRoutePartLinkInfo link=(ListRoutePartLinkInfo) i.next();        	   
        	   routeid= link.getRouteID();
        	  // System.out.println("----得到的link="+link+" routeid="+routeid);
           }
           if(routeid!=null&&!routeid.trim().equals(""))
           {
        	   //  //CCBegin by leixiao 2011-1-12 原因:解放路线新需求 
        	   //按零部件报毕时，更改属性默认采用“C”，
        	   listLinkInfo=(ListRoutePartLinkInfo)copyRoute(routeid,listLinkInfo);
//               RouteIfc route=pservice.findValueInfo(listLinkInfo.getRouteID());
             //  System.out.println(listLinkInfo+"   "+listLinkInfo.getRouteID()+""+modefyidentycoding);
               
            //CCBegin SS3
            if(iscomplete.equals("y"))
            {
            //CCEnd SS3
               TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) pservice.
               refreshInfo(listLinkInfo.getRouteID());
               
               if(routeInfo.getModefyIdenty().getCodeContent().equals("采用")){  
            	   modefyidentycoding=routeInfo.getModefyIdenty();
               }
               else{            	   
               if(modefyidentycoding==null){
            	 CodingManageService codingservice = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
            	 modefyidentycoding=codingservice.findCodingByContent("采用",
                            "更改标记",
                            "工艺路线");             	
               }
               routeInfo.setModefyIdenty(modefyidentycoding);//设成采用
               routeInfo = (TechnicsRouteIfc) pservice.saveValueInfo(
            	          routeInfo);
               }
               
            }
           }
           //CCEnd by leixiao 2011-1-12 原因:解放路线新需求
          //CCBegin SS47
          else
          {
          	QMPartIfc linkpart=(QMPartIfc)pservice.refreshInfo(listLinkInfo.getPartBranchID());
          	System.out.println("状态："+linkpart.getLifeCycleState().getDisplay());
          	System.out.println("说明："+linkpart.getIterationNote());
          	if(linkpart.getLifeCycleState().getDisplay().equals("试制")&&linkpart.getIterationNote()!=null&&linkpart.getIterationNote().equals("汽研试制"))
          	{
          		copyRoute("TechnicsRoute_171044558",listLinkInfo);
          	}
          }
          //CCEnd SS47
          }
        
        //按艺准添加的情况
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
          //  删除关联附件
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
//CCEnd by leixiao 2008-8-5 原因：解放升级工艺路线

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
    //////////////////排除自己
    query1.addAND();
    QueryCondition cond5 = new QueryCondition(LEFTID,
                                              QueryCondition.NOT_EQUAL,
                                              routeListInfo.getBsoID());
    query1.addCondition(cond5);
    query1.addAND();
    ////////////////////////    应保证在同一个分支内    versionID=A 不是A.1 A.2
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
   * 更新父件编号用
   * @param PartsToChange Collection  要更新的父件集合
   * @param routeListInfo1 TechnicsRouteListIfc 路线表值对象
   * @throws QMException
   * @see TechnicsRouteListInfo
   */
  // * 废弃（gcy 05.04.29）
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
   * 搜索路线表(管理器)
   * @param param Object[][] 二维数组，5个元素。例：obj[0]=编号，obj[1]=true. true=是，
   * false=非。数组顺序：编号、名称、级别（汉字）、用于产品、版本号。
   * @throws QMException
   * @return Collection 存放obj[]:根据路线表编号排序后的routelist值对象<br>
   * 此时要用ConfigService进行过滤。
   */

  public Collection findRouteLists(Object[][] param) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(ROUTELIST_BSONAME);
    //zz 设置查询时是否忽略大小写
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
 //  CCBegin by leixiao 2009-4-1 原因：解放升级工艺路线,级别换成类别 
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
//  CCEnd by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕 
    String productNumber = (String) param[3][0];
    boolean productNumberFlag = ( (Boolean) param[3][1]).booleanValue();
    if (productNumber != null && productNumber.trim().length() != 0) {
      //如果产品编号输入错误，将有提示，是否需要。
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
    //如果版本恰好为最新版，可能搜出个人资料夹的东西。
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
    //routelist值对象集合。
    Collection result = pservice.findValueInfo(query);
    //根据工作状态进行过滤。原本副本不能同时存在。获得工艺路线表值对象集合。
    filterByWorkState(result);
    //根据路线表编号升序排列。
    Collection sortedVec = RouteHelper.sortedInfos(result,
        "getRouteListNumber", false);
    return sortedVec;
  }

//如果产品编号输入错误，将有提示，是否需要。
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

  //根据零件号获得零件masterID.
  private Collection getProductMasterID(String productNumber) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(PARTMASTER_BSONAME);
    //GenericPartMaster不搜索。
    query.setChildQuery(false);
    QueryCondition cond = RouteHelper.handleWildcard("partNumber",
        productNumber);
    query.addCondition(cond);
    Collection coll = pservice.findValueInfo(query);
    return coll;
  }

  /**
   * 根据工作状态进行过滤。原本副本不能同时存在。获得工艺路线表值对象集合。
   * @param total Collection
   */
  private void filterByWorkState(Collection total) {
    Object[] obj = total.toArray();
    for (int i = 0; i < obj.length; i++) {
      TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) obj[i];
      if (listInfo.getWorkableState().trim().equals(CompareHandler.WRK)) {
        String decessorID = listInfo.getPredecessorID();
        //去掉原本
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
   * 搜索工艺路线表。搜索范围：编号、名称、状态、级别（汉字）、用于产品、说明、存放位置、
   * 创建日期、创建者、最后更新、版本号。
   * @param routelistInfo TechnicsRouteListIfc 路线表值对象
   * @param productNumber String 用于产品编号
   * @param createTime String    创建时间
   * @param modifyTime String    修改时间
   * @throws QMException
   * @return Collection 存放obj[]:根据路线表编号排序后的工艺路线表值对象结果集，最新版本。<br>
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
      throw new TechnicsRouteException("routelistInfo不能为空。");
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
      //////假设routelistInfo.getIterationCreator()代表用户名或者全称。
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
      //如果产品编号输入错误，将有提示，是否需要。
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
    //如果版本恰好为最新版，如有权限，可能搜出个人资料夹的东西。但此种情况居然业务规则允许。
    if (routelistInfo.getVersionID() != null &&
        routelistInfo.getVersionID().trim().length() != 0) {
      //////////////////getVersionValue改成getVersionID 2004.9.11 赵立彬
      QueryCondition cond11 = RouteHelper.handleWildcard("versionID",
          routelistInfo.getVersionID());
      query.addCondition(0, cond11);
      query.addAND();
      //routelist值对象集合。
      if (VERBOSE) {
        System.out.println(TIME +
                           "routeService's findRouteLists if... clause, SQL = " +
                           query.getDebugSQL());
        //进行其他方法进行过滤，暂不进行。
      }
    }
    QueryCondition cond12 = new QueryCondition("iterationIfLatest",
                                               Boolean.TRUE);
    query.addCondition(cond12);
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's findRouteLists else... clause, SQL = " +
                         query.getDebugSQL());
      //routelist值对象集合。
    }
    query.setDisticnt(true);
    //addListOrderBy(query);
    Collection result = pservice.findValueInfo(query);
    if (VERBOSE) {
      System.out.println(TIME + "routeService's findRouteLists result = " +
                         result);
      //根据工作状态进行过滤。原本副本不能同时存在。获得工艺路线表值对象集合。
    }
    filterByWorkState(result);
    //根据路线表编号升序排列。
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
   * 获得工艺路线表。搜索范围：编号、名称、状态、级别、用于产品、说明、存放位置、创建日期、创建者、最后更新、版本号。
   * @return collection 工艺路线表值对象，最新版本。
   * 此时要用ConfigService进行过滤。
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
            {///////////////////////////////？？？？？？？？？？？？？？？？？
                QueryCondition cond10 = new QueryCondition("productMasterID", QueryCondition.EQUAL,
                    ((QMPartMasterIfc)iter.next()).getBsoID());
                query.addCondition(0, cond10);
                query.addOR();
            }
        }
        //如果版本恰好为最新版，可能搜出个人资料夹的东西。
        if(routelistInfo.getVersionValue()!=null&&routelistInfo.getVersionValue().trim().length()!=0)
        {
   QueryCondition cond11 = new QueryCondition("versionValue", QueryCondition.EQUAL,
                routelistInfo.getVersionValue());
            query.addCondition(0, cond11);
            query.setDisticnt(true);
            query.setVisiableResult(1);
            //routelist值对象集合。
            if(VERBOSE)
                System.out.println(TIME + "routeService's findRouteLists if... clause, SQL = " + query.getDebugSQL());
            result = pservice.findValueInfo(query);
            //进行其他方法进行过滤，暂不进行。
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
     //routelistMaster值对象集合。
            Collection coll = pservice.findValueInfo(query);
            for(Iterator iter = coll.iterator(); iter.hasNext(); )
            {
            //过滤后的结果。
                TechnicsRouteListIfc routeListInfo = (TechnicsRouteListIfc)getLatestVesion((TechnicsRouteListMasterIfc)
                    iter.next());
                result.add(routeListInfo);
            }
        }
        return result;
         }
   */

  //用例7.查看工艺路线表
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC307
  //getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)
////////////////////////////////////////////////////////////////////////////

  /**
   * 生成报表，导出定制报表。输出方式为本地文件。
   * @param routeListID String 路线表的ID
   * @param level_zh String    路线级别
   * @param exportFormat String 输出格式
   * @throws QMException
   * @return String  路线表的名称+编号+的工艺路线报表
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
        String order = "序号";
        String number = JNDIUtil.getPropertyDescript(new
            QMPartMasterInfo().
            getBsoName(),
            "partNumber").getDisplayName();
        String name = JNDIUtil.getPropertyDescript(new QMPartMasterInfo().
            getBsoName(),
            "partName").getDisplayName();
        String version = "版本";
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
                               "一级路线 exportRouteList nodes = " +
                               nodes);
          }
          appendNodesMsg(nodes, result, four_comma);
          result.append(newline);
        }
      }
      else if (level_zh.trim().equals(RouteListLevelType.SENCONDROUTE.
                                      getDisplay())) {
        String order = "序号";
        String number = JNDIUtil.getPropertyDescript(new
            QMPartMasterInfo().
            getBsoName(),
            "partNumber").getDisplayName();
        String name = JNDIUtil.getPropertyDescript(new QMPartMasterInfo().
            getBsoName(),
            "partName").getDisplayName();
        String version = "版本";
        result.append(order + comma + number + comma + name + comma +
                      version +
                      comma);
        String firstManuRoute = "一级制造";
        String firstAssemRoute = "一级装配";
        String secondManuRoute = "二级制造";
        String secondAssemRoute = "二级装配";
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
                               "一级制造, 一级装配 exportRouteList nodes = " +
                               firstNodes);
          }
          Collection secondNodes = (Collection) branchColl[1];
          if (VERBOSE) {
            System.out.println(TIME +
                               "二级制造, 二级装配 exportRouteList nodes = " +
                               secondNodes);
            //将firstNodes和secondNodes合成一个新集合。并将其写入result中。
          }
          appendNodesMsg_Second(firstNodes, secondNodes, result);
        }
      }
      else {
        throw new TechnicsRouteException("路线级别有错误。");
      }
    }
    else {
      throw new TechnicsRouteException("暂时只支持.csv格式。");
    }
    return result.toString();
  }

//将firstNodes和secondNodes合成一个新集合。并将其写入result中。
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
      out.println("enter inteObj ." + (v + 1) + "次");
      //result = new StringBuffer();
      
      if (v > 0) {
        result.append(four_comma);
      }
      Object[] bigObj = (Object[]) inteObj[v];
      //add by guoxl on 2008-07-04(生成二级路线本地报表出现类造型错误)
      if(bigObj[0] instanceof String){
    	  Object[] first1=new Object[1];
    	   first1[0] = bigObj[0].toString();
          appendOnlyOneBranch(first1, result);
    	  
      }else{
      // add by guoxl end (生成二级路线本地报表出现类造型错误)
      Object[] first = (Object[]) bigObj[0];
      appendOnlyOneBranch(first, result);
      }
      //add by guoxl on 2008-07-04(生成二级路线本地报表出现类造型错误)
      if(bigObj[1] instanceof String){
    	  Object[] second1=new Object[1];
    	  second1[0]=  bigObj[1].toString();
    	  appendOnlyOneBranch(second1, result);
      }else{
    	// add by guoxl end (生成二级路线本地报表出现类造型错误)  
      Object[] second = (Object[]) bigObj[1];
      appendOnlyOneBranch(second, result);
      }
      result.append(newline);
      out.println(result);
      out.println("exit inteObj ." + (v + 1) + "次");
    }
  }

  //填加报表头。
  private void appendReportHead(TechnicsRouteListIfc listInfo,
                                StringBuffer result,
                                String level_zh) throws QMException {
    if (VERBOSE) {
      System.out.println("enter appendReportHead : listInfo.bsoid = " +
                         listInfo.getBsoID());
      //添加路线表信息。
    }
    String listNumberVal = listInfo.getRouteListNumber();
    String listNameVal = listInfo.getRouteListName();
    Object[] listObj = null;
    String key = null;
    //二级路线的表头不同。
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
    //添加产品信息。
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
    //添加报表产生日期。
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

  //添加零件信息。
  private void appendPartMsg(QMPartMasterIfc partMasterInfo,
                             StringBuffer result) throws QMException {
    result.append(partMasterInfo.getPartNumber() + comma);
    result.append(partMasterInfo.getPartName() + comma);
    result.append( ( (QMPartIfc) getLatestVesion(partMasterInfo)).
                  getVersionValue() + comma);
  }

  //添加节点信息。
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
  //add by guoxl on 2008-07-04(生成二级路线本地报表出现类造型错误)
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
   //add by guoxl end (生成二级路线本地报表出现类造型错误)
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
   * 根据工艺路线表ID获得一级工艺路线报表。
   * @param listInfo TechnicsRouteListIfc 路线表值对象，根据工艺路线表ID获得零件及其工艺路线值对象。
   * @throws QMException
   * @return CodeManageTable key:partMasterInfo零件值对象;<br>
   * value：存放的是Map:key:routeBranchInfo 路线分支值对象;<br>
   * value: routeStr 路线串
   * @see TechnicsRouteListInfo
   */
  //obj[0]=制造路线节点值对象集合；obj[1]=装配路线节点值对象。
  public CodeManageTable getFirstLeveRouteListReport(TechnicsRouteListIfc
      listInfo) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
    if (!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.
        FIRSTROUTE.getDisplay())) {
      throw new TechnicsRouteException("路线表应该是一级路线。");
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
      //    CCBegin by leixiao 2009-01-05 原因：解放升级工艺路线
     // QMPartMasterIfc partMasterInfo = linkInfo.getPartMasterInfo();
     //    CCEnd by leixiao 2009-01-05 原因：解放升级工艺路线
      // 根据工艺路线ID获得工艺路线分枝相关对象。
      //（问题三）生成报表性能优化
   //   Map routeMap = getRouteBranchs(linkInfo.getRouteID()); 原代码
      Map routeMap = this.getDirectRouteBranchStrs(linkInfo.getRouteID());
     //    CCBegin by leixiao 2009-01-05 原因：解放升级工艺路线
      firstMap.put(linkInfo, routeMap.values());
      //    CCEnd by leixiao 2009-01-05 原因：解放升级工艺路线
    }
    if (VERBOSE) {
      System.out.println(
          "exit getFirstLeveRouteListReport , firstMap.values = " +
          firstMap.values());
    }
    return firstMap;
  }
  /**
   * 根据工艺路线表ID获得一级工艺路线报表。
   * @param listInfo TechnicsRouteListIfc 路线值对象，根据工艺路线表ID获得零件及其工艺路线值对象。
   * @throws QMException
   * @return Map key:QMPartMasterIfc零件(主信息)值对象;value：存放一个map: key：TechnicsRouteBranchIfc 工艺路线分枝值对象,<br>
   * value：路线节点数组，obj[0]=制造路线节点值对象集合；
   * obj[1]=装配路线节点值对象。
   * @see TechnicsRouteListInfo
   */
  public Map getFirstLeveRouteListReport1(TechnicsRouteListIfc listInfo) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
    if (!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.
        FIRSTROUTE.getDisplay())) {
      throw new TechnicsRouteException("路线表应该是一级路线。");
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
   * 根据工艺路线表ID获得二级工艺路线报表。
   * @param listInfo TechnicsRouteListIfc 路线表值对象
   * @throws QMException
   * @return Map key:QMPartMasterIfc零件(主信息)值对象;<br>
   * value：存放了一个obj[] :<bt>obj[0]:一级工艺路线节点数组象集合,obj[1]:二级工艺路线节点数组象集合<br>
   * obj2[0]： 制造路线节点值对象集合；obj2[1]：装配路线节点值对象。
   * @see TechnicsRouteListInfo
   */
  // 数组中包含两个集合，obj[0]=一级工艺路线节点数组象集合，集合中有数组。 obj1[0]=制造路线节点值对象集合；obj1[1]=装配路线节点值对象。
  // * obj[1] = 二级工艺路线节点数组象集合，集合中有数组。obj2[0]=制造路线节点值对象集合；obj2[1]=装配路线节点值对象。。
  public Map getSecondLeveRouteListReport(TechnicsRouteListIfc listInfo) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
    if (!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.
        SENCONDROUTE.getDisplay())) {
      throw new TechnicsRouteException("路线表应该是二级路线。");
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
      throw new TechnicsRouteException("二级路线必须有单位。");
    }
    Map secondMap = new HashMap();
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      QMPartMasterIfc partMasterInfo = linkInfo.getPartMasterInfo();

      //获得partMasterInfo的二级路线对应的一级路线串。
      //Collection nodes = getFirstNodesBySecondRoute(departmentID, listInfo.getProductMasterID());
      Collection nodes = getFirstNodesBySecondRoute(departmentID,
          linkInfo.getPartMasterID());
      if (nodes != null) { //lm add 20040526
        Object obj[] = new Object[2];
        obj[0] = nodes;
        //获得二级路线包含路线串。
        //Map routeMap = getRouteBranchs(linkInfo.getRouteID());//原代码
       //（问题五）2006 08 03  zz 周茁添加 性能优化
    Map routeMap =  getDirectRouteBranchStrs(linkInfo.getRouteID());//end
        obj[1] = routeMap.values();
        secondMap.put(partMasterInfo, obj);
      }
///////////////////// lm add 20040826
      else { //没有一级路线时，只显示二级路线节点
        Object obj2[] = new Object[2];
        obj2[0] = new Vector();
        //获得二级路线包含路线串。
      //  Map routeMap = getRouteBranchs(linkInfo.getRouteID());原代码
       //（问题五）2006 08 03  zz 周茁添加 性能优化
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

  //获得partMasterInfo的二级路线对应的一级路线串。此规则可能有变化。
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
    //路线修改时间降序排列。
    //query.addOrderBy(i, "modifyTime", true);
    query.setDisticnt(true);
    if (VERBOSE) {
      System.out.println("getFirstNodesBySecondRoute's SQL = " +
                         query.getDebugSQL());
    }
    Collection coll = pservice.findValueInfo(query);
    //for(Iterator iter = coll.iterator(); iter.hasNext();)
    //此异常是否应该抛出。
    if (coll.size() == 0) {
      if (VERBOSE) {
        System.out.println(productMasterID + "没有对应的一级路线。");
      }
      return null; //lm modify 20040526
    }
    //路线修改时间降序排列。
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
    Collection result = null;
    Iterator iter = sortedVec.iterator();
    //取最后修改的路线对应的关联。
    if (iter.hasNext()) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      if (VERBOSE) {
        System.out.println(
            "routeService's getFirstNodesBySecondRoute linkInfo = " +
            linkInfo);
        //获得一级路线包含路线串。
      }
     // Map routeMap = getRouteBranchs(linkInfo.getRouteID());
    //  （问题五）2006 08 03  zz 周茁添加 性能优化 生成报表  直接从branch里取路线穿字符串,不用恢复node对象
    Map routeMap = this.getDirectRouteBranchStrs(linkInfo.getRouteID());
      result = routeMap.values();
    }
    if (VERBOSE) {
      System.out.println("exit getFirstNodesBySecondRoute  result = " +
                         result);
    }
    return result;
  }

////////////////////////////生成报表结束。///////////////////////////////

  /**
   * 获得所有的工艺路线表的最新版本，如果有A,B版，返回B版最新小版本。
   * @throws QMException
   * @return Collection 存放obj[]集合：<br>obj[0]：工艺路线表值对象。
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
      //获得所有的工艺路线表的最新版本，如果有A,B版，返回B版最新小版本。
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
   * 获得与路线表相关的零部件。
   * @param routeListInfo TechnicsRouteListIfc 路线表值对象
   * @throws QMException
   * @return Collection 存储ListRoutePartLinkIfc：关联值对象集合。
   * @see TechnicsRouteListInfo
   */
  public Collection getRouteListLinkParts(TechnicsRouteListIfc routeListInfo) throws
      QMException {
    if (VERBOSE) {
      System.out.println(
          "enter the class:1731行：方法:getRouteListLinkParts()");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                             routeListInfo.getBsoID());
    query.addCondition(cond);
    query.addAND();
    //有可能零件未使用路线。
    QueryCondition cond1 = new QueryCondition("alterStatus",
                                              QueryCondition.NOT_EQUAL,
                                              PARTDELETE);
    query.addCondition(cond1);
    Collection coll = pservice.findValueInfo(query);
    ///////2004.4.28修改//////////
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      if (linkInfo.getRouteID() == null) {
        /**
                 原有代码
                 String status = getStatus(linkInfo.getPartMasterID(),
                                  routeListInfo.getRouteListLevel());
                 linkInfo.setAdoptStatus(status);
         */
        /*
         代码修改 修改者 skybird 2005.2.24
         */
        //begin
        String level = routeListInfo.getRouteListLevel();
        String level2 = new String("二级路线");
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
    *  获得与路线表相关的零部件。
    * 本方法是查看路线表版本历史是调用，所以不用查看其他路线表是否为关联的零部件建立路线，返回关联值对象就可以
    * @param routeListInfo TechnicsRouteListIfc 路线表值对象
    * @throws QMException
    * @return Collection 存放ListRoutePartLinkIfc：关联值对象集合。
    * @see TechnicsRouteListInfo
    */
   //（问题四）zz 周茁添加 性能优化 2006 07 12
   public Collection getRouteListLinkPartsforVersionCompare(TechnicsRouteListIfc routeListInfo) throws
       QMException {

     PersistService pservice = (PersistService) EJBServiceHelper.
         getPersistService();
     QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
     QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL,
                                              routeListInfo.getBsoID());
     query.addCondition(cond);
     query.addAND();
     //有可能零件未使用路线。
     QueryCondition cond1 = new QueryCondition("alterStatus",
                                               QueryCondition.NOT_EQUAL,
                                               PARTDELETE);
     query.addCondition(cond1);
     Collection coll = pservice.findValueInfo(query);
      return coll;
   }


  /**
   * 获得所有路线及节点信息。
   * @param routeID String 路线ID
   * @throws QMException
   * @return Object[]<br>
   * obj[0]：TechnicsRouteIfc 工艺路线值对象。obj[1] ： HashMap,参见getRouteBranchs(String routeID)
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
   * 根据工艺路线ID获得工艺路线分枝相关对象。
   * @param routeID String  路线ID
   * @throws QMException
   * @return Map  <br>key：TechnicsRouteBranchIfc 工艺路线分枝值对象, value：路线节点数组，obj[0]=制造路线节点值对象集合；
   * obj[1]=装配路线节点值对象。
   */
  public Map getRouteBranchs(String routeID) throws QMException {
       //  System.out.println(" 进入getRouteBranchs方法 传过来的 routeID " + routeID);
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);//表TechnicsRouteBranch
    QueryCondition cond = new QueryCondition("routeID",
                                             QueryCondition.EQUAL,
                                             routeID);
    query.addCondition(cond);
    Collection coll = pservice.findValueInfo(query);
   // System.out.println(" 进入getRouteBranchs方法 传过来的 coll " + coll.size());
    Map map = new HashMap();
    //HashMap map = new HashMap();
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      TechnicsRouteBranchIfc routeBranchInfo = (TechnicsRouteBranchIfc)
          iter.
          next();
      //因分枝数据量较少，未采用两表联查方式。
      Collection branchNodes = getBranchRouteNodes(routeBranchInfo);
      //obj[0]=制造路线节点值对象集合；obj[1]=装配路线节点值对象。
      Object[] obj = getNodeType(branchNodes);
      map.put(routeBranchInfo, obj);
    }
    return map;
  }

  /**
   * 瘦客户端显示路线串
   * @param routeID String 路线ID
   * @throws QMException
   * @return Map  key:TechnicsRouteBranchIfc 分支值对象; value:路线串,routeBranchInfo.getRouteStr();
   */
  //（问题五）zz 周茁添加
  public Map getDirectRouteBranchStrs(String routeID) throws QMException{
    PersistService pservice = (PersistService) EJBServiceHelper.
       getPersistService();
   QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);//表TechnicsRouteBranch
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
   * 获得路线分支
   * @param routeID String 路线ID
   * @throws QMException
   * @return Collection 存放pservice.findValueInfo(query)： 该分支中的路线节点的集合
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
   * 根据路线ID获得该路线中按分支进行分类的路线节点
   * @param routeID String 路线ID
   * @return Map <br>key：TechnicsRouteBranchIfc分支值对象，value：getBranchRouteNodes(routeBranchInfo);
   * 该分支中的路线节点的集合
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
      //因分枝数据量较少，未采用两表联查方式。
      Collection branchNodes = getBranchRouteNodes(routeBranchInfo);
      map.put(routeBranchInfo, branchNodes);
    }
    return map;
  }

  /**
   * 据工艺路线分枝ID获得工艺路线节点。
   * @param routeBranchInfo TechnicsRouteBranchIfc 路线值对象
   * @throws QMException
   * @return Collection 存放pservice.findValueInfo(query)：工艺路线节点值对象。
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
//    //按升序排列。
//    query.addOrderBy("bsoID", false);
//    Collection coll = pservice.findValueInfo(query);
//    Collection result = new Vector();
//    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
//      RouteBranchNodeLinkIfc linkInfo = (RouteBranchNodeLinkIfc) iter.
//          next();
//      result.add(linkInfo.getRouteNodeInfo());
//    }
    //(问题一)20060609 周茁修改 zzstart
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
    //(问题一)20060609 周茁修改 end
   return result;
  }

  /**
   * 按路线节点类型分类。装配路线、制造路线。
   * @param branchNodes Collection 路线节点集合
   * @throws QMException
   * @return Object[]  obj[0]：存放了一个vector:此vector存放了RouteNodeIfc路线节点值对象 <br>
   * obj[1]：RouteNodeIfc装配节点值对象
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
        throw new TechnicsRouteException("节点类型不正确" +
                                         nodeInfo.getRouteType());
      }
    }
    if (VERBOSE) {
      if (i > 1) {
        System.out.println(TIME + "一个分支中出现多个装配路线。 " + test);
      }
    }
    obj[0] = vec;
    obj[1] = assemNodeInfo;
    return obj;
  }

  //查看工艺路线
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC316


  /**
   * 根据工艺路线获得相关的节点及其关联。
   * @param routeID String 路线ID
   * @throws QMException
   * @return Object[] <br> obj[0]:RouteNodeInfo路线节点值对象集合；
   * obj[1]:RouteNodeLinkInfo路线节点关联值对象集合。
   */
  public Object[] getRouteNodeAndNodeLink(String routeID) throws QMException {
    Object[] obj = new Object[2];
    obj[0] = getRouteNodes(routeID);
    obj[1] = getNodelink(routeID);
    return obj;
  }

  /**
   * 根据工艺路线获得相关的节点(按路线分支区别)及其关联。
   * @param routeID String
   * @return  Object[] <br> obj[0]:存放了一个Map（key:TechnicsRouteBranchIfc分支值对象，
   * value:参见getBranchRouteNodes(routeBranchInfo);该分支中的路线节点的集合）；<br>
   *  obj[1]：RouteNodeLinkInfo路线节点关联值对象集合。
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
   * 根据工艺路线获得对应的节点。
   * 先获得工艺路线对应的若干起始节点。
   * @return Collection 获得路线节点和链接的值对象集合，参见pservice.findValueInfo(query);。
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
   * 根据工艺路线获得对应的节点关联。
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
   * 保存，更新工艺路线。
   * 系统保存此新建的工艺路线，将界面刷新为查看路线界面
   * 在创建完路线后，当保存RoutePartLink时，应在ListRoutePartLink中保存相应的路线
   * 是否使用状态。保持数据的一致性。
   * @param linkInfo ListRoutePartLinkIfc 路线表与零件关联值对象
   * @param routeInfo TechnicsRouteIfc 路线值对象
   * @throws QMException
   * @return TechnicsRouteIfc 工艺路线值对象
   * @see ListRoutePartLinkInfo
   * @see TechnicsRouteInso
   */
  public TechnicsRouteIfc saveRoute(ListRoutePartLinkIfc linkInfo,
                                    TechnicsRouteIfc routeInfo) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    TechnicsRouteIfc routeInfoSaved = null;
    //判断路线是更新还是新建。
    if (PersistHelper.isPersistent(routeInfo)) {
      if (VERBOSE) {
        System.out.println(TIME +
                           "enter routeService's saveRoute if cause ,开始更新工艺路线。");
        //更新工艺路线。
      }
      routeInfoSaved = (TechnicsRouteIfc) pservice.saveValueInfo(
          routeInfo);
    }
    else {
      if (VERBOSE) {
        System.out.println(TIME +
                           "enter routeService's saveRoute else cause ,开始创建工艺路线。");
        //保存工艺路线。
      }
      //把选择的复制路线对象另存一份，bsoID不同
      routeInfoSaved = (TechnicsRouteIfc) pservice.saveValueInfo(
          routeInfo);
      String routeListID = linkInfo.getRouteListID();
      String partMasterID = linkInfo.getPartMasterID();
      //修改 skybird 2005.1.29
      //begin
      String INITIALUSED = linkInfo.getInitialUsed();
      //end
      //保存ListRoutePartLink？skybird
      if (linkInfo.getAlterStatus() == INHERIT) {
        //关联后处理,设置原来的路线状态为取消。
        //  listPostProcess(linkInfo.getRouteListMasterID(), routeListID,
        //                  partMasterID);
        //begin 把这个listPostProcess()方法增加了个参数
        listPostProcess(linkInfo.getRouteListMasterID(), routeListID,
                        partMasterID, INITIALUSED);
        //end
      }
      TechnicsRouteListIfc routeListInfo = (TechnicsRouteListIfc)
          pservice.
          refreshInfo(routeListID);
      linkInfo.setRouteID(routeInfoSaved.getBsoID());
      //检出时的复制不处理此项。
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
   * 获得具体路线版本的关联。
   * @param routeListID String 路线表ID
   * @param partMasterID String 零部件住信息ID
   * @param partNumber String 零件编号
   * @throws QMException
   * @return ListRoutePartLinkIfc 路线表与零件关联值对象
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
    //有可能零件未使用路线。
    QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(cond1);
    query.addAND();
    //不包括删除状态的关联。
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
      throw new TechnicsRouteException("partMasterID和listID获得的关联不应该超过一个。");
    }
    Iterator iter = coll.iterator();
    ListRoutePartLinkIfc linkInfo = null;
    if (iter.hasNext()) {
      linkInfo = (ListRoutePartLinkIfc) iter.next();
    }
    return linkInfo;
  }

  /*
     关联后处理,设置原来的路线状态为取消。
     被修改 修改者：skybird 2005.1.29
     修改说明：这个方法在系统执行过程中会抛异常，
     因此加个参数arg4，但这个修改导致了程序逻辑的改变，
     不知道是否合乎需求的规定。
   */
  private void listPostProcess(String routeListMasterID, String routeListID,
                               String partMasterID, String arg4) throws
      QMException {
      	//    CCBegin by leixiao 2008-10-09 原因：解放升级工艺路线
	    //quxg add 不再自动将路线设置为取消状态060808
	    if (true) {
	      return;
	    }
//      CCEnd by leixiao 2008-10-09 原因：解放升级工艺路线
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
     代码修改：skybird 时间：2005.1.24
     */
    //begin
    query.addAND();
    QueryCondition cond4 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query.addCondition(cond4);
    //end
    /*
         代码修改：skybird 2005.1.29
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
      //一旦零件采用路线，即使原关联中零件没设置路线，也要被设置为取消。否则getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)要出问题。
    }
//      CCBegin by leixiao 2008-10-09 原因：解放升级工艺路线
    if (coll.size() > 1) {
//      throw new TechnicsRouteException(
//          "routeService's listPostProcess : 获得的关联不应该有多个： " +
//          coll.size());
      throw new TechnicsRouteException(
              "获得的关联不应该有 " +
              coll.size()+"个，请检查是否添加了相同的零件.");
//    CCEnd by leixiao 2008-10-09 原因：解放升级工艺路线
    }
    Iterator iter = coll.iterator();
    if (iter.hasNext()) {
      ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
      if (VERBOSE) {
        System.out.println("设置关联前 " + linkInfo.getBsoID() + "的状态为==" +
                           linkInfo.getAdoptStatus());
      }
      linkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
      pservice.saveValueInfo(linkInfo);
      if (VERBOSE) {
        System.out.println("设置关联后2222222222222 " + linkInfo.getBsoID() +
                           "的状态为==" + linkInfo.getAdoptStatus());

      }
    }
  }

  /**
   * 保存工艺路线编辑图。
   * 路线串构造规则：
   * 工艺路线串的构成为路线单位节点，一个单位可以在一个路线串中出现多次。路线串中包括加工单位和装配单位，所以路线串的构成必须符合下列规则：
   * 1. 装配单位在一个路线串中只能有一个，且只能是最后一个节点；
   * 2. 一个单位如果在一个路线串中出现多次，则必须是不同类型的节点(制造或装配)，否则不能在相邻的位置出现。
   * ￠     * 如果一个路线串中设计了多个装配节点，则显示对应的消息；
   * ￠     * 如果装配节点不是最后节点，则显示对应的消息；
   * ￠     * 如果路线串中存在相邻的同类型节点，则显示对应的消息；
   * 注意：事务回滚。
   * @param listLinkInfo ListRoutePartLinkIfc 路线与零件关联值对象
   * @param routeRelaventObejts HashMap key: TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME<br>
   * value:RouteNodeInfo路线节点值对象或RouteNodeLinkInfo路线节点关联值对象
   * @throws QMException
   * @see ListRoutePartLinkInfo
   */
  // 客户端在进行保存时，应首先判断ListRoutePartLinkIfc的getAlterStatus()，如=0，全部设置成新建状态；=1，正常处理。
  public void saveRoute(ListRoutePartLinkIfc listLinkInfo,
                        HashMap routeRelaventObejts) throws QMException {
    try {
      if (VERBOSE) {
        System.out.println(
            "更新工艺路线：enter the TechnicsRouteServiceEJB:saveRoute():2135");
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

//CCBegin by leixiao 2010-7-12 打补丁v4r3_p018_20100629 见CR7
  /**
   *清除历史路线表关联的路线ID
   *return true:删除历史版本关联的路线;false:不删除
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
//CCEnd by leixiao 2010-7-12 打补丁v4r3_p018_20100629 见CR7
  /**
   * 删除工艺路线。
   * @param linkInfo ListRoutePartLinkIfc 路线与零件关联值对象
   * @throws QMException
   * @see ListRoutePartLinkInfo
   */
//  调用到此方法的地方：
//   * 1.删除零件关联调用了此方法
//   * 2.编辑路线里的删除一个零部件的路线也调用了此方法。

  public void deleteRoute(ListRoutePartLinkIfc linkInfo) throws QMException {
    if (linkInfo.getRouteID() == null ||
        linkInfo.getRouteID().trim().length() == 0) {
      throw new TechnicsRouteException("此零件没有路线，不能删除");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //CCBegin by leixiao 2010-7-12 打补丁v4r3_p018_20100629 见CR7
    boolean flag=isDeleteHistoryRoute(linkInfo);//CR7
    /*
      加这个判断有用，但是做什么用还没有想好？-----2005.1.31-skybird
      我想到删除一个路线的时候只能对大版本的最新小版本进行删除工作。
      三方关联有不同的状态进行设置，所以会有此判断。
     */
    /*
     * int =0 表示是从上一版本继承下来的；int=1，
     * 从本版本生成的。涉及到路线是否重新生成；int=2，
     * 此版本删除的，当路线表检出时，不复制此关联。
     */
    
    if (linkInfo.getAlterStatus() == ROUTEALTER) {//1
    	if(flag){//Begin CR7
      //删除路线包含对象。
      deleteContainedObjects(linkInfo.getRouteID());
      //删除路线本身
      pservice.deleteBso(linkInfo.getRouteID());
      	}//End CR7
    }
  //CCEnd by leixiao 2010-7-12 打补丁v4r3_p018_20100629 见CR7
//    else if (linkInfo.getAlterStatus() == INHERIT) {//0              //Begin CR6
//      //设置上一个采用路线为取消状态。
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
//        代码修改--skybird  2005.1.31
//        修改方式：添加
//        修改目的：此查询语句的条件不符合逻辑，删除路线的操作应该只对本
//        版本的关联状态产生影响，所以加个查询条件。
//       */
//      //begin
//      query.addAND();
//      QueryCondition cond4 = new QueryCondition("initialUsed",
//                                                QueryCondition.EQUAL,
//                                                linkInfo.getInitialUsed());
//      query.addCondition(cond4);
//      //end
//      Collection coll = pservice.findValueInfo(query);
//      //一旦零件采用路线，即使原关联中零件没设置路线，也要被设置为取消。
//      if (coll.size() > 1) {
//        throw new TechnicsRouteException(
//            "routeService's listPostProcess : 获得的关联不应该有多个： " +
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
    
    //设置关联类。
    linkInfo.setRouteID(null);
    linkInfo.setAlterStatus(ROUTEALTER);
    //skybird,2005.1.21,修改
    //原有的代码：linkInfo.setAdoptStatus(RouteAdoptedType.ADOPT.getDisplay());
    //added begin
    linkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
    //added end
    pservice.saveValueInfo(linkInfo);
  }
  
  /**
	 * 在删除路线表时删除路线信息. 此方法在删除路线信息后不再保存修改后的关联对象.
	 * 
	 * @param linkInfo   关联对象
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
			throw new TechnicsRouteException("此零件没有路线，不能删除");
		}
		PersistService pservice = (PersistService) EJBServiceHelper
				.getPersistService();

		if (linkInfo.getAlterStatus() == ROUTEALTER)
		{
			// 删除路线包含对象。
			deleteContainedObjects(linkInfo.getRouteID());
			// 删除路线本身
			pservice.deleteBso(linkInfo.getRouteID());
		}

	}

  /**
   * @roseuid 4030B10E02D9
   * @J2EE_METHOD  --  deleteNode
   * 删除节点ID.关联类可由持久化删除（需测试）。
   */
  private void deleteNode(String routeID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //由持久化删除node_node_link./////////////需测试。
    //自己删除
    QMQuery query2 = new QMQuery(ROUTENODE_LINKBSONAME);
    QueryCondition cond2 = new QueryCondition("routeID",
                                              QueryCondition.EQUAL,
                                              routeID);
    query2.addCondition(cond2);
    Collection coll2 = pservice.findValueInfo(query2);
    //删除所有节点关联。
    for (Iterator iter2 = coll2.iterator(); iter2.hasNext(); ) {
      pservice.deleteValueInfo( (BaseValueIfc) iter2.next());
    }

    QMQuery query = new QMQuery(ROUTENODE_BSONAME);
    QueryCondition cond = new QueryCondition("routeID",
                                             QueryCondition.EQUAL,
                                             routeID);
    query.addCondition(cond);
    Collection coll = pservice.findValueInfo(query);
    //删除所有节点。
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      pservice.deleteValueInfo( (BaseValueIfc) iter.next());
    }

  }


  /**
   * 在删除和每次更新之前进行删除。
   * (此方法循环中有sql查询，效率有待测试)
   * @param routeID String 路线ID
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
    //删除BranchNodeLink和Branch
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc) iter.next();
      QMQuery branchQuery = new QMQuery(BRANCHNODE_LINKBSONAME);
      QueryCondition cond2 = new QueryCondition(LEFTID,
                                                QueryCondition.EQUAL,
                                                branch.getBsoID());
      branchQuery.addCondition(cond2);
      Collection coll2 = pservice.findValueInfo(branchQuery); //找到所有关联
      for (Iterator iter2 = coll2.iterator(); iter2.hasNext(); ) {
        pservice.deleteValueInfo( (BaseValueIfc) iter2.next());
      }
      pservice.deleteValueInfo(branch);
    }

  }

  /**
   * 删除路线时可能有问题，持久化会删除对应的关联，但此时不想删除关联。
   * @roseuid 4030B1D703B4
   * @J2EE_METHOD  --  deleteRouteListener
   * 删除工艺路线节点及其关联。
   */
  protected void deleteContainedObjects(String routeID) throws QMException {
    //删除路线分枝。
    deleteBranchRelavent(routeID);
    //删除路线节点。
    deleteNode(routeID);

  }

  /**
   * 当只更新节点位置时，调用此方法。不进行路线的更新。
   * @param coll Collection RouteNodeIfc路线节点值对象集合
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
      throw new TechnicsRouteException("传入类型必须为RouteNodeIfc。");
    }
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      BaseValueIfc nodeInfo = (BaseValueIfc) iter.next();
      //只能是更新路线节点。
      pservice.updateValueInfo(nodeInfo);
    }
  }

////////////路线及其节点的创建、更新、删除结束。////////////////

  /**
   * 获得用于指定产品的最新工艺路线表
   * @param productMasterID String 用于产品
   * @throws QMException
   * @return TechnicsRouteListInfo 工艺路线表
   */
  public TechnicsRouteListInfo getRouteListByProduct(String productMasterID) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getRouteListByProduct productMasterID = " +
                         productMasterID);
    }
    if (productMasterID == null || productMasterID.trim().length() == 0) {
      throw new TechnicsRouteException("输入参数不对");
    }
    Object[] obj = new Object[3];
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query1 = new QMQuery("RouteListProductLink");
    QueryCondition condition1 = new QueryCondition(RIGHTID,
        QueryCondition.EQUAL, productMasterID);
    query1.addCondition(condition1);
    //返回RouteListProductLink
    Collection coll = pservice.findValueInfo(query1);
    if (coll != null) {
      //降序排列。
      Collection sortedVec = RouteHelper.sortedInfos(coll,
          "getCreateTime", true);
      if (VERBOSE) {
        System.out.println(TIME + "查询产品结构对应的关联： " + coll);
      }
      Iterator iter = sortedVec.iterator();
      //只取最新创建的路线表对应的关联。
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
   * 生成物料清单的零部件路线
   * 如果用户选择输出工艺路线，系统应检查用户当前要生成物料清单的零部件，如果有对应的
   * 工艺路线表(工艺路线表中的“用于产品”属性关联到该零部件)，则使用此零部件的最新工
   * 艺路线表为工艺路线的数据源，对于没在其工艺路线表中列出的零部件，取创建时间戳最新
   * 的路线表中的路线为数据源。如果当前要生成物料清单的零部件没有对应的工艺路线表，所
   * 有零部件全部取创建时间戳最新的路线表中的路线为数据源。如果产品结构中的某些零部件
   * 没有编制过工艺路线，则有关路线的属性输出空值。
   * @param routeList TechnicsRouteListInfo 路线表值对象
   * @param partMasterID String 零件MasterID
   * @param level_zh String 路线级别
   * @throws QMException
   * @return Object[] obj[0]:TechnicsRouteListIfc, <br>obj[1],obj[2]
   * 见getRouteAndBrach(routeID), <br>obj[3]:ListRoutePartLinkIfc。
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
    //查找当前路线表与partMasterID的关联
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
        throw new QMException("不应该查出多个纪录！");
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
    else { //对于没在其工艺路线表中列出的零部件，取创建时间戳最新的路线表中的路线为数据源
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
   * 按产品结构生成路线报表
   * @param partMasterID String
   * @param level_zh String 路线级别
   * @throws QMException
   * @return Object[] obj[0]: TechnicsRouteListIfc,<br> obj[1],
   * obj[2]见getRouteAndBrach(routeID), <br>obj[3]:ListRoutePartLinkIfc。
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
      throw new TechnicsRouteException("输入参数不对");
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = getLinkQuery(partMasterID, level_zh);
    //返回ListRoutePartLinkIfc
    Collection coll = pservice.findValueInfo(query);
    //降序排列。
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
    if (VERBOSE) {
      System.out.println(TIME + "查询船品结构对应的关联： " + coll);
    }
    Object[] obj = new Object[4];
    Iterator iter = sortedVec.iterator();
    //只取最新创建的路线表对应的关联。
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
   * 根据零件masterID和路线级别获得所有是采用状态的关联类的查询条件。
   * @param partMasterID String
   * @param level_zh String  路线级别
   * @throws QMException
   * @return QMQuery
   */
  private QMQuery getLinkQuery(String partMasterID, String level_zh) throws
      QMException {
    if (VERBOSE) {
      System.out.println("skybird-------TRS:QMQuery():参数：level_zh" +
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
    //降序排列。
    //query.addOrderBy(i, "createTime", true);
    ///////////////////////////
    query.setDisticnt(true);
    return query;
  }

  /**
   * 新增加的方法-------skybird 2005.1.24
   * ？这个方法和上一个方法代码大部分重复！！
   * @param partMasterID 零部件的主ID
   * @param level_zh 路线表的级别
   * @param depart 二级工艺路线所对应的路线级别
   */
  private QMQuery getLinkQuery2(String partMasterID, String level_zh,
                                String depart) throws
      QMException {
    if (VERBOSE) {
      System.out.println("skybird-------TRS:QMQuery2():参数：level_zh" +
                         level_zh);
      System.out.println("skybird-------TRS:QMQuery2():参数：depart" +
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
    //降序排列。
    //query.addOrderBy(i, "createTime", true);
    ///////////////////////////
    query.setDisticnt(true);
    return query;
  }

  //22. 查看零部件的工艺路线
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC322

  /**
   * 根据零件ID和工艺路线级别获得对应的工艺路线。
   * @param partMasterID String
   * @param level_zh String 路线级别
   * @throws QMException
   * @return Collection  数组集合。obj[0]: TechnicsRouteListIfc, <br>
   * obj[1],obj[2]见getRouteAndBrach(routeID),<br> obj[3]:linkInfo。
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
      throw new TechnicsRouteException("输入参数不对");
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
    //zz added start (问题零)
   QueryCondition condx = new QueryCondition("adoptStatus",
                                             QueryCondition.EQUAL,
                                             RouteAdoptedType.ADOPT.
                                             toString());
   query.addCondition(0,condx);
   //zz added end (问题零)

    //路线修改时间降序排列。
    //query.addOrderBy(j, "modifyTime", true);
    //SQL不能正常处理。
    query.setDisticnt(true);
    //返回ListRoutePartLinkIfc
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's getPartLevelRoutes SQL = " +
                         query.getDebugSQL());
    }
    Collection coll = pservice.findValueInfo(query);
    //路线修改时间降序排列。
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
    if (VERBOSE) {
      System.out.println(TIME + "查询结果为： coll = " + sortedVec);
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
   *  根据零部件查找路线
   * @param partMasterID String 零部件bsoid
   * @throws QMException
   * @return Collection  数组集合。String[] {partID, routeID, routeState, linkID, state,
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
      throw new TechnicsRouteException("输入参数不对");
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
    //SQL不能正常处理。
    query.setDisticnt(true);
    //返回ListRoutePartLinkIfc
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's getPartLevelRoutes SQL = " +
                         query.getDebugSQL());
    }
    Collection coll = pservice.findValueInfo(query);
    //路线修改时间降序排列。
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
    if (VERBOSE) {
      System.out.println(TIME + "查询结果为： coll = " + sortedVec.size());
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

//////17.复制工艺路线
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC317

  /**
   * 复制工艺路线
   * 复制自。利用其他路线表中的路线进行复制。获得"其它"路线表中与给定partMasterID采用的路线。
   * 注意：此种情况只允许一个零件在不同路线表中路线的复制。不允许多个零件间的复制粘贴。
   * 根据零件ID和工艺路线级别获得对应的工艺路线。
   * 执行者复制一个工艺路线，粘贴到没有工艺路线的零部件中。
   * 复制工艺路线时，可以从当前路线表中的一个零部件的工艺路线复制，也可以从一个零部件的其它路线表编制的工艺路线复制；
   * 粘贴时，可以粘贴到当前选中的零部件，也可以粘贴到本路线表中其它无路线的零部件；如果零部件已有路线，使用"粘贴到"复制路线时，不能复制到这些零部件。
   * @param linkInfo1 ListRoutePartLinkIfc
   * @throws QMException
   * @return Collection 数组集合。obj[0]: TechnicsRouteListIfc, <br>
   * obj[1],obj[2]见getRouteAndBrach(routeID)。
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
    /////////根据路线级别进行过滤///////
    int i = query.appendBso(ROUTELIST_BSONAME, false);
    QueryCondition cond0 = new QueryCondition(LEFTID, "bsoID");
    query.addCondition(0, i, cond0);
    query.addAND();
    QueryCondition cond1 = new QueryCondition("routeListLevel",
                                              QueryCondition.EQUAL, level);
    query.addCondition(i, cond1);
    query.addAND();
    if (listInfo1.getRouteListLevel().equals("二级路线")) {
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
    //降序排列。
    //query.addOrderBy("createTime", true);
    //////////////////
    query.setDisticnt(true);
    //返回ListRoutePartLinkIfc
    Collection coll = pservice.findValueInfo(query);
    if (VERBOSE) {
      System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&" +
                         query.getDebugSQL());
    }
    //降序排列。
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
    Collection result = new Vector();
    for (Iterator iter = sortedVec.iterator(); iter.hasNext(); ) {
        //  CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线
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
        //  CCEnd by leixiao 2008-7-31 原因：解放升级工艺路线
      result.add(obj);
    }

    if (VERBOSE) {
      System.out.println(TIME + "exit... routeService's getAdoptRoutes " +
                         result);
    }
    return result;
  }

  /**
   * 复制工艺路线
   * @param routeID String 路线ID: 利用给定routeID,构造对应的路线信息。
   * @param linkInfo ListRoutePartLinkIfc
   * @throws QMException
   * @return ListRoutePartLinkIfc 路线与零件关联
   * @see ListRoutePartLinkInfo
   */
  public ListRoutePartLinkIfc copyRoute(String routeID,
                                        ListRoutePartLinkIfc linkInfo) throws
      QMException {
    if (linkInfo.getRouteID() != null &&
        linkInfo.getRouteID().trim().length() != 0) {
      throw new TechnicsRouteException("4", null);
    }
    //利用给定routeID,构造对应的路线信息。
    HashMap routeRelaventObejts = getRouteContainer(routeID, null);
    //保存路线
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
    else if(routeInfo.getRouteDescription().equals("BOM回导路线"))
    {
    	QMPartMasterIfc partmaster = (QMPartMasterIfc)pservice.refreshInfo(linkInfo1.getRightBsoID());
    	routeInfo.setRouteDescription("("+getibaPartVersion(partmaster)+")");
    	pservice.saveValueInfo(routeInfo);
    }
    //CCEnd SS33
    //CCEnd SS1
    
    return linkInfo1;
    //另一种方式，不调用saveRoute，自己处理。
  }

  /**
   * 复制路线相关对象。注意保证顺序。
   * @param routeID String
   * @param branchInfos Collection 有变化的分支值对象。
   * @throws QMException
   * @return HashMap <br> key:TECHNICSROUTE_BSONAME，ROUTENODE_LINKBSONAME，BRANCHNODE_LINKBSONAME ，
   * TECHNICSROUTEBRANCH_BSONAME，ROUTENODE_BSONAME  <br>
   * value:TechnicsRouteIfc路线值对象；RouteNodeLinkIfc节点关联，RouteBranchNodeLinkIfc分支与节点关联，
   * branchItemVec分支集合，nodeItemVec节点集合
   */
  public HashMap getRouteContainer(String routeID, Collection branchInfos) throws
      QMException {
    HashMap relaventObejts = new HashMap();
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //1.添加路线到HashMap。
    TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) pservice.refreshInfo(
        routeID);
    setupNewBaseValueIfc(routeInfo);
    RouteItem item = createRouteItem(routeInfo);
    relaventObejts.put(TECHNICSROUTE_BSONAME, item);
    //获得节点及节点关联。
    Object[] obj1 = getRouteNodeAndNodeLink(routeID);
    //节点集合。
    Collection nodes = (Collection) obj1[0];
    //节点关联集合。
    Collection nodeLinks = (Collection) obj1[1];
    //2.添加节点关联集合到HashMap
    //集合中的对象为RouteItem.
    Collection nodeLinkItemVec = new Vector();
    for (Iterator iter1 = nodeLinks.iterator(); iter1.hasNext(); ) {
      RouteNodeLinkIfc nodeLinkInfo = (RouteNodeLinkIfc) iter1.next();
      //设置关联源节点。
      RouteNodeIfc sourceNode = nodeLinkInfo.getSourceNode();
      RouteNodeIfc node1 = (RouteNodeIfc) getTheSameInfo(sourceNode,
          nodes);
      nodeLinkInfo.setSourceNode(node1);
      //设置关联目的节点。
      RouteNodeIfc destNode = nodeLinkInfo.getDestinationNode();
      RouteNodeIfc node2 = (RouteNodeIfc) getTheSameInfo(destNode, nodes);
      nodeLinkInfo.setDestinationNode(node2);
      RouteItem nodeLinkItem = getNodeLinkItem(nodeLinkInfo, routeInfo);
      nodeLinkItemVec.add(nodeLinkItem);
    }
    relaventObejts.put(ROUTENODE_LINKBSONAME, nodeLinkItemVec);
    //3.添加分枝与节点关联集合到HashMap
    //获得所有工艺路线分支和节点关联类值对象。branchNodeLinks有顺序。
    Collection branchNodeLinks = getBranchRouteLinks(routeID);
    Collection branches = getOnlyRouteBranch(routeID);
    Collection branchNodeLinkItemVec = new Vector();
    for (Iterator iter = branchNodeLinks.iterator(); iter.hasNext(); ) {
      RouteBranchNodeLinkIfc branchNodeLinkInfo = (RouteBranchNodeLinkIfc)
          iter.
          next();
      //获得分枝item。
      TechnicsRouteBranchIfc branchInfo = branchNodeLinkInfo.
          getRouteBranchInfo();
      //获得在分枝与节点关联中的分支在branches的对应值。
      TechnicsRouteBranchIfc branch1 = (TechnicsRouteBranchIfc)
          getTheSameInfo(
          branchInfo, branches);
      branchNodeLinkInfo.setRouteBranchInfo(branch1);
      RouteNodeIfc branchNode = branchNodeLinkInfo.getRouteNodeInfo();
      //获得在分枝与节点关联中的节点在nodes的对应值。
      RouteNodeIfc node1 = (RouteNodeIfc) getTheSameInfo(branchNode,
          nodes);
      branchNodeLinkInfo.setRouteNodeInfo(node1);
      RouteItem branchNodeLinkItem = getBranchNodeLinkItem(
          branchNodeLinkInfo);
      branchNodeLinkItemVec.add(branchNodeLinkItem);
    }
    relaventObejts.put(BRANCHNODE_LINKBSONAME, branchNodeLinkItemVec);
    //4.添加分支集合到HashMap
    Collection branchItemVec = new Vector();
    for (Iterator iter11 = branches.iterator(); iter11.hasNext(); ) {
      TechnicsRouteBranchIfc branchInfo = (TechnicsRouteBranchIfc) iter11.
          next();
      if (branchInfos != null) {
        if (branchInfo.getBsoID() == null) {
          throw new TechnicsRouteException("分支值对象被设置为空的时机不对。");
        }
        //根据传入分支值对象集合设置是否主要路线信息。
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
    //5.添加节点集合到HashMap.
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
      //TECHNICSROUTE_BSONAME，ROUTENODE_LINKBSONAME，TECHNICSROUTEBRANCH_BSONAME
      //ROUTENODE_BSONAME，BRANCHNODE_LINKBSONAME
      //1.路线。
      System.out.println(
          "1.route.........................................");
      RouteItem routeItem1 = (RouteItem) relaventObejts.get(
          TECHNICSROUTE_BSONAME);
      TechnicsRouteIfc route = (TechnicsRouteIfc) routeItem1.getObject();
      System.out.println(TIME + "route.... routeID = " + route.getBsoID());
      System.out.println(TIME + "route.... routehashCode = " +
                         route.hashCode());
      //2.节点。
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
      //3.节点关联。
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
      //4.分支
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
      //5.分枝关联。
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

  //获得在分枝与节点关联中的节点在nodes的对应值。
  private BaseValueIfc getTheSameInfo(BaseValueIfc node, Collection nodes) throws
      QMException {
    for (Iterator iter = nodes.iterator(); iter.hasNext(); ) {
      BaseValueIfc nodeInfo = (BaseValueIfc) iter.next();
      if (node.getBsoID().equals(nodeInfo.getBsoID())) {
        return nodeInfo;
      }
    }
    throw new TechnicsRouteException("节点范围出错。");
    //   return null;
  }

  /**
   * @roseuid 4039932300E0
   * @J2EE_METHOD  --  getBranchRouteNodes
   * 根据工艺路线分枝ID获得工艺路线节点。
   * @return Collection 工艺路线分支和节点关联类值对象。
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
    //按升序排列。
    //query.addOrderBy(0, "bsoID", false);
    query.setDisticnt(true);
    Collection coll = pservice.findValueInfo(query);
    //按升序排列。
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

/////////////////////复制结束。////////////////////////////

/////////////////////与项目关联开始////////////////////////////

  /**
   * 根据路线获得单位和零件
   * @param routeListID String 路线表ID  根据路线表ID获得单位
   * @throws QMException
   * @return HashMap key:单位值对象  ; value:零件masterInfo集合
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
   * 通过单位代码ID获得对应的要编工艺的零件。通过零件可获得相关信息。
   * 例：工艺路线等信息。此方法可由工艺部分调用。
   * @param departmentID String 单位ID
   * @throws QMException
   * @return Collection 零件masterInfo集合,参见linkInfo.getPartMasterID();。
   */
 // 注意：单位有结构，查找时，子节点也要遍历。此项暂不处理。
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
   *  根据工艺路线获得其包含的单位。用于任务的分发。
   * @param routeListID String
   * @throws QMException
   * @return Collection  CodingIfc或CodingClassificationIfc
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
   * 根据工艺路线获得其包含的单位ID。
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

/////////////////////与项目关联结束////////////////////////////

  /**
   * 根据routeMaster获得所有的大版本对应的最新小版本。
   * @param listMasterInfo TechnicsRouteListMasterIfc
   * @throws QMException
   * @return Collection 存放的是Object[]：<br>
   * obj[0]:所有的大版本对应的最新小版本。<br>
   * 有config过滤。
   * @see TechnicsRouteListMasterInfo
   */
  //版本提供的方法：allVersionsOf(MasteredIfc mastered).
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
   * 提供版序的比较。
   * @param iteratedVec Collection 不同版本的工艺路线表值对象集合。
   * @throws QMException
   * @return Map key ： partMasterID, value ： Collection:listRoutePartLinkIfc集合。集合顺序，版本号升序排列。
   */
  public Map compareIterate(Collection iteratedVec) throws QMException {
    boolean isCommonSort = Boolean.valueOf(RemoteProperty.getProperty(
        "com.faw_qm.technics.route.isCommonCompare", "true")).
        booleanValue();
    if (VERBOSE) {
      System.out.println(
          "enter the class:TechnicsRouteServiceEJB,method:compareIterate()" +
          isCommonSort);
      System.out.println("方法的参数，iterateVec" + iteratedVec);
    }
    if (isCommonSort) {
      return CompareHandler.commonCompareIterate(iteratedVec);
    }
    else {
      return CompareHandler.compareIterate(iteratedVec);
    }
  }

  ///////////////功能方法。
  /**
   * 客户端进行判断，是否是创建路线。如true,即使是更新，也要将状态设置为创建。
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
        //有可能零件未使用路线。
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
   * 是否有路线。
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

  //routeListInfo为新的大版本。此时要复制关联。并要设置initialUsed为新的大版本。
  /**
   * 获得新版本
   * @param routeListInfo TechnicsRouteListIfc 为新的大版本。此时要复制关联。
   * 并要设置initialUsed为新的大版本。
   * @throws QMException
   * @return TechnicsRouteListIfc 路线表值对象
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
      //获得上一个小版本。
    }
    TechnicsRouteListIfc originalList = (TechnicsRouteListIfc) pservice.
        refreshInfo(decessorID);
    //复制新的关联。
    Collection coll = getRouteListLinkParts(originalList);
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      if (VERBOSE) {
        System.out.println("原关联：listLinkInfo.bsoID = " +
                           listLinkInfo.getBsoID());
      }
      ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkInfo) ( (
          ListRoutePartLinkInfo) listLinkInfo).duplicate();
      if (VERBOSE) {
        System.out.println("新关联：listLinkInfo1.bsoID = " +
                           listLinkInfo1.getBsoID());
      }
      listLinkInfo1.setRouteListID(routeListInfo.getBsoID());
      //将alterStatus设置成INHERIT状态。
     listLinkInfo1.setAlterStatus(INHERIT);//0
      //将adoptStatus设置成CANCEL状态。
      // （问题七） 2006 09 04 zz 周茁修改 红塔提出修订后新版路线表里的关联零部件的路线状态原为采用的变为取消了，
      // 根据需求应该复制前一版本的状态 所以不将状态设为取消
    // listLinkInfo1.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
      //设置initialUsed为新的大版本。
      listLinkInfo1.setInitialUsed(routeListInfo.getVersionID());
      pservice.saveValueInfo(listLinkInfo1);
    }
    if (VERBOSE) {
      System.out.println("exit routeService's newVersionListener");
    }
  }

//////////////监听处理开始//////////////////////////////////////
  /**
   * 不仅仅删除已有数据，还要保证数据恢复到未删除时的状态。此时不用回滚，持久化发出信号，已经设置回滚。
   * @roseuid 403085BA0008
   * @J2EE_METHOD  --  deleteRouteListListener, 实际在撤销检出时用。
   * 因为工艺路线没有版本。此方法负责删除和副本关联的业务对象。包括：工艺路线、路线节点。要考虑master名称和工作原本是否一致。
   */
  protected void deleteRouteListListener(TechnicsRouteListIfc routeListInfo) throws
      QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    //删除在routeListInfo版本中创建的工艺路线。
    QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond3 = new QueryCondition("alterStatus",
                                              QueryCondition.EQUAL,
                                              ROUTEALTER);
    query1.addCondition(cond3);
    query1.addAND();
    //获得新建的工艺路线。
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
    	  
        //保证数据恢复到未删除时的状态。
        // comebackHandle(listLinkInfo1);   
    	//End CR2
      }
      /*else
                   {
       throw new TechnicsRouteException("deleteRouteListListener 路线ID不能为空。");
                   }*/
    }
//Begin CR2
// 此关联持久化会删除。                                                 
//    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);               
//    QueryCondition cond2 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
//                                              routeListInfo.getBsoID());
//    query.addCondition(cond2);
//    Collection coll = pservice.findValueInfo(query);
//    //删除ListRoutePartLink表中的对应的关联。
//    for (Iterator iter3 = coll.iterator(); iter3.hasNext(); ) {
//      pservice.deleteValueInfo( (BaseValueIfc) iter3.next());
//    }
//End CR2
  }

  //恢复数据。
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
    //按降序排列。
    query.addOrderBy("routeListIterationID", true);
    Collection coll = pservice.findValueInfo(query);
    Iterator iter = coll.iterator();
    //只处理第一个。
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

//////////////////////删除零件监听开始///////////////////////////
  /**
   * 当零件被删除时，删除所有的和routeList的关联，并删除相应的路线ID.
   * @param partMasterInfo QMPartMasterIfc
   */
  protected void deletePartMasterListener(QMPartMasterIfc partMasterInfo) throws
      QMException {
    if (VERBOSE) {
      System.out.println(
          "enter the TechnicsRouteServiceEJB:deletePartmasterListener():删除零件关联");
    }
    try {
      String partMasterID = partMasterInfo.getBsoID();
      //如果零件被删除，找到所有建立路线的listRoutePartlink关联类，删除路线。
      deletePartRoutes(partMasterID);
      //删除所有ListRoutePartLink关联。
      deletePartLinks(partMasterID);
      //如果被删除零件是产品，删除相关的路线表等。
      deleteProductRelaventObject(partMasterID);
      //持久化删除产品和路线表的关联。
    }
    catch (Exception e) {
      this.setRollbackOnly();
      throw new TechnicsRouteException(e);
    }
  }

  //删除零件相关路线。
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
    //删除路线。
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      if (listLinkInfo.getRouteID() != null) {
        deleteRoute(listLinkInfo);
      }
    }
  }

  //删除所有与零件有关的关联。
  private void deletePartLinks(String partMasterID) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
    QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL,
                                              partMasterID);
    query.addCondition(cond1);
    Collection coll = pservice.findValueInfo(query);
    //删除关联。
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      //即使没有权限，路线表关联也会被删除。
      pservice.removeValueInfo(listLinkInfo);
    }
  }

  //删除与产品相关的路线表和路线等。//因不需要恢复状态，且为提高效率。手动删除路线及关联。
  private void deleteProductRelaventObject(String productMasterID) throws
      QMException {
    //删除与产品相关的路线。
    deleteProductRoutes(productMasterID);
    //删除与产品相关的listRoutePartLink。
    deleteProductLinks(productMasterID);
    //删除与产品相关的路线表。
    deleteProdcutRouteLists(productMasterID);
  }

  //删除与产品相关的路线。
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
    //删除路线。
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      if (listLinkInfo.getRouteID() != null) {
        deleteRoute(listLinkInfo);
      }
    }
  }

  /**
   * 删除与产品相关的listRoutePartLink。
   * 如果此处不删除关联，在路线表删除监听会做大量数据处理，浪费效率。
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
    //删除所有关联。
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      //即使没有权限，路线表关联也会被删除。
      pservice.removeValueInfo(listLinkInfo);
    }
  }

  //删除与产品相关的路线表。
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
    //删除所有关联。
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) iter.next();
      //即使没有权限，路线表也会被删除。
      pservice.removeValueInfo(listInfo);
    }
  }

  //////////////////////删除零件监听结束。///////////////////////////
  /**
   * @param original 路线表原本
   * @param work 路线表副本
   * @roseuid 4031A3570092
   * @J2EE_METHOD  --  copyRouteList
   * @return 复制后的工艺路线副本。
   * 负责拷贝整个工艺路线表。在产品升级时刻能用到。此时需注意：原来的子件可能不是现在产品的子件，
   * 需在客户端进行检查，给出提示标识，便于用户更改。
   * 拷贝工艺路线表。检出时路线表副本的路线及其节点的复制。
   */
  //Begin CR1
  /*
   原代码:
  protected void copyRouteList(TechnicsRouteListIfc original,                       
                               TechnicsRouteListIfc work) throws QMException {
    if (VERBOSE) {
      System.out.println(TIME + "enter routeService's copyRouteList,原本 " +
                         original + "副本" + work);
    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    Collection coll = getRouteListLinkParts(original);
    if (VERBOSE) {
      System.out.println(TIME + "copyRouteList... 原本的三放关联coll = " + coll);
    }
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
      //原本的零部件关联
      ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter.
          next();
      //获得关联的拷贝
      ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkInfo) ( (
          ListRoutePartLinkInfo) listLinkInfo).duplicate();
      listLinkInfo1.setRouteListID(work.getBsoID());
      //将alterStatus设置成INHERIT状态。
      listLinkInfo1.setAlterStatus(INHERIT);
      //将adoptStatus设置成CANCEL状态。//更改：2004.5.12将adoptStatus设置成ADOPT状态。
      //////////////////因关联会产生多个采用状态,故有此更改. 2004.9.11 赵立彬
      //listLinkInfo1.setAdoptStatus(RouteAdoptedType.ADOPT.getDisplay());
      if (!listLinkInfo1.getAdoptStatus().equals(RouteAdoptedType.ADOPT.
                                                 getDisplay())) {
        listLinkInfo1.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
      }
      if (VERBOSE) {
        System.out.println(TIME + "保存前： listLinkInfo.bsoID = " +
                           listLinkInfo.getBsoID() +
                           ", listLinkInfo1.bsoID = " +
                           listLinkInfo1.getBsoID());
      }
      pservice.saveValueInfo(listLinkInfo1);
      if (VERBOSE) {
        System.out.println(TIME + "保存后： listLinkInfo1.bsoID = " +
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
		// 有可能零件未使用路线。
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
				String level2 = new String("二级路线");
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
			// 重新设置关联对象的属性
			linkInfo.setBsoID(null);
			linkInfo.setCreateTime(null);
			linkInfo.setModifyTime(null);

			linkInfo.setRouteListID(work.getBsoID());

			// 将alterStatus设置成INHERIT状态。
			linkInfo.setAlterStatus(INHERIT);

			// 设置路线状态
			if (!linkInfo.getAdoptStatus().equals(
					RouteAdoptedType.ADOPT.getDisplay()))
			{
				linkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
			}

			pservice.storeBso(linkInfo, false);

		}

	}
          //End CR1
  //设置ListRoutePartLink的setRouteListIterationID
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

//////////////监听处理结束。//////////////////////////////////////

  /**
   *  根据零件masterID获得工艺路线表值对象集合。
   * @param partMasterID String
   * @throws QMException
   * @return Collection TechnicsRouteListInfo工艺路线表值对象集合
   */
  public Collection getListsByPart(String partMasterID) throws QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getListsByPart, partMasterID = " +
                         partMasterID);
    }
    if (partMasterID == null || partMasterID.trim().length() == 0) {
      throw new TechnicsRouteException("partMasterID不能为空。");
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
    //路线表的创建时间降序排列。
    query.addOrderBy(0, "createTime", true);
    //SQL不能正常处理。
    //query.setDisticnt(true);
    //返回ListRoutePartLinkIfc
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's getPartLevelRoutes SQL = " +
                         query.getDebugSQL());
    }
    Collection coll = pservice.findValueInfo(query);
    //路线表的创建时间降序排列。
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
    if (VERBOSE) {
      System.out.println(TIME + "查询结果为： sortedVec = " + sortedVec);
      System.out.println(TIME + "exit... routeService's getListsByPart ");
    }
    return sortedVec;
  }

  /////////////2004.4.27后添加的方法/////////////

  /**
   * 判断给定的零件masterID在其它路线表中是否有路线。
   * @param partMasteInfos Collection QMPartMasterIfc集合
   * @param level_zh String 路线级别
   * @throws QMException
   * @return Map  key：partMasterInfo, value ：确定一个零部件的
   * 的状态值,参见getStatus(partMasterInfo.getBsoID(), level_zh)<br>
   * 零件"不存在"或“无”；
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
   根据给定的partMasterID,level_zh（路线表级别），来动态的确定一个零部件的
   的状态值。
   @param partMasterID
   @param level_zh  路线表级别
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
        System.out.println("TRS:动态获取一个零部件的路线状态" + status);
      }
    }
    else {
      status = RouteAdoptedType.NOTHING.getDisplay();
      if (VERBOSE) {
        System.out.println("TRS:动态获取一个零部件的路线状态" + status);
      }
    }
    return status;
  }

  /**

   * 该方法在工艺路线表执行更新操作的时候所调用的方法，
   * 它针对的是二级工艺路线表的情形。该方法当一个零部
   * 件的路线是空的时候调用，来动态的生成零部件的路线
   * 状态。
   * @param partmasterID 路线表的零部件
   * @param level_zh 路线表的级别
   * @param depart 二级路线表的单位属性值
   */
  // * 新增加的一个方法--------- skybird 2005.2.24
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
        System.out.println("TRS:动态获取一个零部件的路线状态" + status);
      }
    }
    else {
      status = RouteAdoptedType.NOTHING.getDisplay();
      if (VERBOSE) {
        System.out.println("TRS:动态获取一个零部件的路线状态" + status);
      }
    }
    return status;
  }

  /**
   * @param 有变化的分支值对象。alterStatus = 0;
   * @param routeMap Map
   */
  /**
   * 根据分支创建路线
   * @param linkInfo ListRoutePartLinkIfc 路线与零件关联值对象
   * @param route TechnicsRouteIfc 路线值对象
   * @param branchInfos Collection 分支值对象 根据分支创建路线
   * @throws QMException
   * @see ListRoutePartLinkInfo
   * @see TechnicsRouteInfo
   */
  public void createRouteByBranch(ListRoutePartLinkIfc linkInfo,
                                  TechnicsRouteIfc route,
                                  Collection branchInfos) throws QMException {
    if (VERBOSE) {
      System.out.println(
          "TechnicsRouteServiceEJB,由更新路线调用（没点击更新路线，alterstatus 0）");
    }
    //利用给定routeID,构造对应的路线信息。
    HashMap routeRelaventObejts = getRouteContainer2(linkInfo.getRouteID(),
        route, branchInfos);
    //保存路线。
    saveRoute(linkInfo, routeRelaventObejts);
  }

  /**
   * 更新分枝。
   * @param branchs Collection RouteBranchNodeLinkIfc要更新分枝值对象。
   * @throws QMException
   */
  public void updateBranchInfos(Collection branchs) throws QMException {
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
    for (Iterator iter = branchs.iterator(); iter.hasNext(); ) {
      //此时的分支值对象都已经持久化。
      pservice.updateValueInfo( (BaseValueIfc) iter.next());
    }
  }

  /**
   * 根据路线类型和分枝ID获得工艺路线节点。
   * @param routeType String 路线类型
   * @param routeBranchID String 路线分支ID
   * @return Collection null
   */
  //（可考虑在检查起始节点是否有左连接，如有，调用RouteListHandler的后处理方法，重新设置起始节点。根据实际情况做选择。）
  public Collection getRouteNodes(String routeType, String routeBranchID) {
    return null;
  }


//
//   * 根据零部件ID和工艺路线表ID获得对应的工艺路线。
//   * @return 工艺路线值对象。
//   *
//   * 参考文档：
//   * １． PHOS-CAPP-BR208 创建工艺路线检查
//   * 说明：一个零部件在一个路线表中只能存在一个工艺路线对象。
//   * ￠     * 如果零部件已存在工艺路线，则显示对应消息
//   *
//   * ２．启动工艺路线管理器
//   * 版本 <v2.0>
//   * 文档编号：PHOS-CAPP-UC311
//   *
//   * 4. 系统退出界面PHOS-CAPP-UI325，将执行者选择的路线表和执行者选中的零部件显示在"工艺路线管理器"界面(PHOS-CAPP-UI316)中，用例结束。
//         public TechnicsRouteIfc getListPartRoute(String partMasterID, String routeListID)
//         {
//        return null;
//         }*/

  /**
   * @roseuid 40309C1300C3
   * @J2EE_METHOD  --  getRoutes
   * 根据工艺路线表ID获得对应的工艺路线。
   * @return Collection 返回路线表对应的所有工艺路线，路线分枝，路线节点。
   public Collection getRoutes(String routeListID)
   {
           return null;
   }*/

  /**
   * 根据产品ID和零件ID获得工艺路线值对象。
   * @param productMasterID String 产品ID
   * @param partMasterID String零件ID
   * @return TechnicsRouteIfc  null
   */
  public TechnicsRouteIfc getRoute(String productMasterID,
                                   String partMasterID) {
    return null;
  }

  /**
   * 根据工艺路线表ID获得所有工艺路线及其节点值对象。
   * @param routeListID String  路线表ID
   * @param level_zh String 路线表级别
   * @return HashMap null
   */
  public java.util.HashMap getRouteListContent(String routeListID,
                                               String level_zh) {
    return null;
  }


  /**
   * 根据工艺路线表及路线级别获得工艺路线。
   * 如果级别为空，不分级别。
   * @param routeListID String 路线表ID
   * @param level_zh String 路线级别
   * @return Collection null
   */
  public Collection getListLevelRoutes(String routeListID, String level_zh) {
    return null;
  }


  /**
   * 获得选择的零件
   * @param depart String  单位
   * @param master QMPartMasterIfc
   * @throws QMException
   * @return boolean  有零件返回true,否则返回false
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
   // zz (问题八)20061110 周茁添加,为二级路线 "添加"按钮 添加根据所选单位过滤功能

   /**
    * 为二级路线 "添加"按钮 添加根据所选单位过滤功能
    * @param depart String 单位
    * @param masters Vector
    * @throws QMException
    * @return Collection ListRoutePartLinkInfo的RightBsoID <br>
    * 集合经路线单位过滤后的零件集合
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
       //经路线单位过滤后的零件
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
   * 用当前用户的配置规范过滤零部件
   * @param master QMPartMasterIfc
   * @throws QMException
   * @return QMPartIfc 过滤后零件的值对象
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
  
  //CCBegin by leixiao 2008-11-03 原因：解放升级
  /**
   * 用当前用户的配置规范过滤零部件,过滤掉制造视图
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
	if(part.getViewName().equals("工程视图"))
		return part;

}
return null;
}
//CCEnd by leixiao 2008-11-03 原因：解放升级

  /**
   *  通过part找出所有符合配制规范的子part，并调getOptionPart（）过滤结果集
   * @param partMaster QMPartMasterIfc
   * @param configSpecIfc PartConfigSpecIfc
   * @param depart String 单位
   * @throws QMException
   * @return Vector QMPartIfc[] 数组集合:<br>
   * partInfos[i]：过滤后的零件集合
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
   * 复制路线相关对象。注意保证顺序。  //由getRouteContainer而来  lm  add  20040827
   * @param routeID String
   * @param 有变化的分支值对象。
   * @return HashMap
   */
  private HashMap getRouteContainer2(String routeID,
                                     TechnicsRouteIfc oldRoute,
                                     Collection branchInfos) throws QMException {
    HashMap relaventObejts = new HashMap();
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
//1.添加路线到HashMap。
    TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) pservice.refreshInfo(
        routeID);
    setupNewBaseValueIfc(routeInfo);
    routeInfo.setRouteDescription(oldRoute.getRouteDescription());
    RouteItem item = createRouteItem(routeInfo);
    relaventObejts.put(TECHNICSROUTE_BSONAME, item);
    //获得节点及节点关联。
    Object[] obj1 = getRouteNodeAndNodeLink(routeID);
    //节点集合。
    Collection nodes = (Collection) obj1[0];
    //节点关联集合。
    Collection nodeLinks = (Collection) obj1[1];
    //2.添加节点关联集合到HashMap
    //集合中的对象为RouteItem.
    Collection nodeLinkItemVec = new Vector();
    for (Iterator iter1 = nodeLinks.iterator(); iter1.hasNext(); ) {
      RouteNodeLinkIfc nodeLinkInfo = (RouteNodeLinkIfc) iter1.next();
      //设置关联源节点。
      RouteNodeIfc sourceNode = nodeLinkInfo.getSourceNode();
      RouteNodeIfc node1 = (RouteNodeIfc) getTheSameInfo(sourceNode,
          nodes);
      nodeLinkInfo.setSourceNode(node1);
      //设置关联目的节点。
      RouteNodeIfc destNode = nodeLinkInfo.getDestinationNode();
      RouteNodeIfc node2 = (RouteNodeIfc) getTheSameInfo(destNode, nodes);
      nodeLinkInfo.setDestinationNode(node2);
      RouteItem nodeLinkItem = getNodeLinkItem(nodeLinkInfo, routeInfo);
      nodeLinkItemVec.add(nodeLinkItem);
    }
    relaventObejts.put(ROUTENODE_LINKBSONAME, nodeLinkItemVec);
//3.添加分枝与节点关联集合到HashMap
    //获得所有工艺路线分支和节点关联类值对象。branchNodeLinks有顺序。
    Collection branchNodeLinks = getBranchRouteLinks(routeID);
    Collection branches = getOnlyRouteBranch(routeID);
    Collection branchNodeLinkItemVec = new Vector();
    for (Iterator iter = branchNodeLinks.iterator(); iter.hasNext(); ) {
      RouteBranchNodeLinkIfc branchNodeLinkInfo = (RouteBranchNodeLinkIfc)
          iter.
          next();
      //获得分枝item。
      TechnicsRouteBranchIfc branchInfo = branchNodeLinkInfo.
          getRouteBranchInfo();
      //获得在分枝与节点关联中的分支在branches的对应值。
      TechnicsRouteBranchIfc branch1 = (TechnicsRouteBranchIfc)
          getTheSameInfo(
          branchInfo, branches);
      branchNodeLinkInfo.setRouteBranchInfo(branch1);
      RouteNodeIfc branchNode = branchNodeLinkInfo.getRouteNodeInfo();
      //获得在分枝与节点关联中的节点在nodes的对应值。
      RouteNodeIfc node1 = (RouteNodeIfc) getTheSameInfo(branchNode,
          nodes);
      branchNodeLinkInfo.setRouteNodeInfo(node1);
      RouteItem branchNodeLinkItem = getBranchNodeLinkItem(
          branchNodeLinkInfo);
      branchNodeLinkItemVec.add(branchNodeLinkItem);
    }
    relaventObejts.put(BRANCHNODE_LINKBSONAME, branchNodeLinkItemVec);
     //4.添加分支集合到HashMap
    Collection branchItemVec = new Vector();
    for (Iterator iter11 = branches.iterator(); iter11.hasNext(); ) {
      TechnicsRouteBranchIfc branchInfo = (TechnicsRouteBranchIfc) iter11.
          next();
      if (branchInfos != null) {
        if (branchInfo.getBsoID() == null) {
          throw new TechnicsRouteException("分支值对象被设置为空的时机不对。");
        }
        //根据传入分支值对象集合设置是否主要路线信息。
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
//5.添加节点集合到HashMap.
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
      //TECHNICSROUTE_BSONAME，ROUTENODE_LINKBSONAME，TECHNICSROUTEBRANCH_BSONAME
      //ROUTENODE_BSONAME，BRANCHNODE_LINKBSONAME
      //1.路线。
      System.out.println(
          "1.route.........................................");
      RouteItem routeItem1 = (RouteItem) relaventObejts.get(
          TECHNICSROUTE_BSONAME);
      TechnicsRouteIfc route = (TechnicsRouteIfc) routeItem1.getObject();
      System.out.println(TIME + "route.... routeID = " + route.getBsoID());
      System.out.println(TIME + "route.... routehashCode = " +
                         route.hashCode());
      //2.节点。
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
      //3.节点关联。
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
      //4.分支
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
      //5.分枝关联。
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
   *  根据给定的制造单位查询相关的零部件和工艺路线id
   * @param makeDepartment String 制造单位
   * @throws QMException
   * @return Vector 存放的是 <br>
   * String[] {listRoutePartLink.getPartMasterID(),
   *                          routeid,
   *                          listRoutePartLink.getAdoptStatus(),
   *                           listRoutePartLink.getBsoID()}
   */
  // * added by skybird 2005.3.9
  public Vector getAllPartsRoutesM(String makeDepartment) throws QMException {
    Iterator iterate = null;
    //存储返回的结果
    Vector result = new Vector();
    //存储临时查询结果
    Collection coll = null;
    //存储临时的路线id
    Vector routeidVec = new Vector();
    //临时路线id
    String routeid = new String();
    //临时工艺路线
    ListRoutePartLinkIfc listRoutePartLink = null;
    TechnicsRouteIfc technicsRoute = null;
    //找出所有含有此路线单位的路线节点
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
        //这里可能添加重复的路线
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
      //以下是查寻零部件路线和路线表的三方关联表
      for (int i = 0; i < routeidVec.size(); i++) {
        technicsRoute = (TechnicsRouteIfc) routeidVec.elementAt(i);
        routeid = technicsRoute.getBsoID();
        if (VERBOSE) {
          System.out.println("4461row routeid" + routeid);
        }
        //以下的查询可能产生重复
        query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        cond = new QueryCondition("routeID", QueryCondition.EQUAL,
                                  routeid);
        query.addCondition(cond);
        coll = pservice.findValueInfo(query);
        if (VERBOSE) {
          System.out.println("测试用的expected num is 1:" + coll.size());
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
   *  根据给定的装配单位查询相关的零部件和工艺路线id
   * @param constructDepartment String  装配单位
   * @throws QMException
   * @return Vector 存放的是 String[] {listRoutePartLink.getPartMasterID(),
   *                           routeid,
   *                           listRoutePartLink.getAdoptStatus(),
   *                           listRoutePartLink.getBsoID()}
   */
  // * added by skybird 2005.3.9
  public Vector getAllPartsRoutesC(String constructDepartment) throws
      QMException {
    Iterator iterate = null;
    //存储返回的结果
    Vector result = new Vector();
    //存储临时查询结果
    Collection coll = null;
    //存储临时的路线id
    Vector routeidVec = new Vector();
    //临时路线id
    String routeid = new String();
    //临时工艺路线
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
      //以下是查寻零部件路线和路线表的三方关联表
      for (int i = 0; i < routeidVec.size(); i++) {
        technicsRoute = (TechnicsRouteIfc) routeidVec.elementAt(i);
        routeid = technicsRoute.getBsoID();
        //以下的查询可能产生重复
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
   * 根据给定的制造单位和装配单位查询相关的零部件和工艺路线id
   * @param makeDepartment String 制造单位
   * @param constructDepartment String 装配单位
   * @throws QMException
   * @return Vector  存放了两个vector:<br>
   * 1.mResult Vector:根据制造单位查询返回的结果；<br>
   * 存放的是String[] {listRoutePartLink.getPartMasterID(),
   *                           routeid,
   *                           listRoutePartLink.getAdoptStatus(),
   *                           listRoutePartLink.getBsoID()}
   * 2.cResult Vector：根据装配单位查询返回的结果;<br>
   * 存放的是String[] {listRoutePartLink.getPartMasterID(),
   *                           routeid,
   *                           listRoutePartLink.getAdoptStatus(),
   *                           listRoutePartLink.getBsoID()}
   */
  //* added by skybird 2005.3.9
  public Vector getAllPartsRoutesA(String makeDepartment,
                                   String constructDepartment) throws
      QMException {
    //存储返回的结果
    Vector result = new Vector();
    //根据制造单位查询返回的结果
    Vector mResult = new Vector();
    //根据装配单位查询返回的结果
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
   * 给路线和连接分类
   * @param routeAndLinks Collection 路线和连接集合
   * @return Collection
   */
  //zz 周茁修改（问题六） 2006 08 04
  //这个方法有问题，排序后元素会增多重复元素
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
   * 根据给定的制造单位查询相关的零部件和工艺路线id
   * @param mDepartment String 制造单位的id
   * @param cDepartment String 装配单位的id
   * @param productID String  产品的id
   * @param source String  零部件的来源
   * @param type String  零部件的类型
   * @throws QMException
   * @return Collection vector集合:<br>
   *      part.getLifeCycleState().getDisplay()<br>
   *       part.getBsoID()<br>
   *       partmaster.getPartNumber()<br>
   *       partmaster.getPartName()<br>
   *       part.getVersionValue()<br>
   *       lrpLink.getParentPartNum()<br>
   * Integer(this.calCountInProduct(part,
                  lrpLink.getParentPart(), product, boms, midleBoms))<br>
   *       route.getRouteDescription()<br>
   * 结果集合。每个集合项是一个Vector.依次放零部件生命周期，零部件id，零部件编号，零部件名称，
   * 版本，父件号，零部件在产品中数量集合，路线数量，路线集合（其中路线集合每个条目包括制造装配2中路线）
   */
  /*delete by guoxl on 2008-12-23(解放实施问题，关于按路线单位搜索)
  public Collection getAllPartsRoutes(String mDepartment, String cDepartment,
                                      String productID, String source,
                                      String type) throws QMException {
    if (VERBOSE) {
      System.out.println("进入路线服务getAllPartsRoutes方法 :" + "mDepartment = " +
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
        //此方法等part组提供
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
       // System.out.println("5383行 getRouteByPartAndDep 得到的 routeAndLinks 集合的长度" + routeAndLinks.size());
        if (routeAndLinks != null && routeAndLinks.size() > 0) {
          routeAndLinks = sortRouteAndLinks(routeAndLinks);
        //  System.out.println(" sortRouteAndLinks 之后的 routeAndLinks 集合的长度 " +routeAndLinks.size() );
        }
        Iterator routeIterator = routeAndLinks.iterator();
        BaseValueIfc[] bsos;
        TechnicsRouteIfc route;
        HashMap parts = new HashMap();
        ArrayList partMasters = new ArrayList();
        ListRoutePartLinkIfc lrpLink;
        QMPartMasterIfc partmaster;
        QMPartIfc part;
        //(问题二)20060701 周茁修改 begin
        while (routeIterator.hasNext()) {
          bsos = (BaseValueIfc[]) routeIterator.next();
          partmaster = (QMPartMasterIfc) bsos[2];
          // 用当前用户的配置规范过滤
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
        //(问题二)20060701 周茁修改 end
        //(问题二)20060725 周茁修改 end
        Vector products = this.getAllParentProductOnce(new Vector(parts.values()));
         //(问题二)20060725 周茁修改 end
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
        //  System.out.println("重bsos里取出来的route " +route.getBsoID() );
          partmaster = (QMPartMasterIfc) bsos[2];
          //取最新版本，有点问题
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
              //问题，如果数量为0没处理.另外，可以判断是否产品就是路线表对应的，如果是，可以直接用
              counts.add(new Integer(this.calCountInProduct(part,
                  lrpLink.getParentPart(), product, boms, midleBoms)));
            }
          }
          temp.add(counts);
          // （问题五）瘦客户查看时可以直接从TechnicsRouteBranchIfc对象的新添属性路线串字符串中取 zz start
//         map = (HashMap) getRouteBranchs(route.getBsoID()); //原代码
//          assembleBranchStr(temp, map);//原代码
          map = (HashMap)   getDirectRouteBranchStrs(route.getBsoID());
             assembleBranchStrForThin(temp, map);
          // （问题五）瘦客户查看时可以直接从TechnicsRouteBranchIfc对象的新添属性路线串字符串中取 zz end
          //add by guoxl 2008.11.17(获得路线表编号，添加到集合中)
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
  }delete end  by guoxl  end on 2008-12-23(解放实施问题，关于按路线单位搜索)*/
//add by guoxl on 2008-12-23(解放实施问题，关于按路线单位搜索)
  public Collection getAllPartsRoutes(String mDepartment, String cDepartment,
                                      String productID, String source,
                                      String type) throws QMException {
	  
    if (VERBOSE) {
        System.out.println("进入方法==getAllPartsRoutes=="+"mDepartment==="+mDepartment+"/n"+
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
      }//只有单位
      else {
    	  //获得零件的配置规格
    	
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
        
        //通过部门利用ＳＱＬ查找
        Collection routeAndLinks = this.getRouteByPartAndDep(mdepartIDs,
            cdepartIDs, null);

        if (routeAndLinks != null && routeAndLinks.size() > 0) {
        	
          routeAndLinks = sortRouteAndLinks(routeAndLinks);
    
        //  System.out.println(" sortRouteAndLinks 之后的 routeAndLinks 集合的长度 " +routeAndLinks.size() );
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
       
        //(问题二)20060701 周茁修改 begin
        while (routeIterator.hasNext()) {
          bsos = (BaseValueIfc[]) routeIterator.next();
        //add by guoxl on 2008-12-19(解放实施问题)
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
          //add by guoxl on 2008-12-19(解放实施问题)
          partmaster = (QMPartMasterIfc) bsos[2];
          // 用当前用户的配置规范过滤
          if (!parts.containsKey(partmaster.getBsoID())) {
           
              parts.put(partmaster.getBsoID(),null);
              partMasters.add(partmaster);
          }
        }
        //通过partMasters进行配置规范过滤,然后返回QMpartIfc集合
        Collection filteredPartmasters = this.filteredIterationsOfByDefault(partMasters);
      
        for(Iterator f = filteredPartmasters.iterator();f.hasNext();)
        {
        	Object[] obj = (Object[])f.next();
                parts.put(((QMPartIfc)obj[0]).getMasterBsoID(),((BaseValueIfc)obj[0]));
                
        }
        //(问题二)20060701 周茁修改 end
        //(问题二)20060725 周茁修改 end
        
        
        //add by guoxl on 2008-12-19(解放实施问题)
        Vector products=new Vector(partParentsMap.values());
        //add by guoxl end on 2008-12-19(解放实施问题)
        
         //(问题二)20060725 周茁修改 end
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
        //  System.out.println("重bsos里取出来的route " +route.getBsoID() );
          partmaster = (QMPartMasterIfc) bsos[2];
          //取最新版本，有点问题
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
          
          // （问题五）瘦客户查看时可以直接从TechnicsRouteBranchIfc对象的新添属性路线串字符串中取 zz start
//         map = (HashMap) getRouteBranchs(route.getBsoID()); //原代码
//          assembleBranchStr(temp, map);//原代码
          map = (HashMap)   getDirectRouteBranchStrs(route.getBsoID());
             assembleBranchStrForThin(temp, map);
          // （问题五）瘦客户查看时可以直接从TechnicsRouteBranchIfc对象的新添属性路线串字符串中取 zz end
           //add by guoxl 2008.12.9(获得路线表编号，添加到集合中)
             String routeListNum=lrpLink.getLeftBsoID();
             TechnicsRouteListIfc routeListIfc=(TechnicsRouteListIfc) pservice.refreshInfo(routeListNum);

             TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo)routeListIfc.getMaster();
             //获得现有的最新小版本
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
//add end by guoxl 2008-12-23(解放实施问题，关于按路线单位搜索)

  /**
   * 统计综合路线分布
   * @param mDepartment String 制造单位id
   * @param cDepartment String 装配单位id
   * @param parts Vector 要统计的子件
   * @param products Vector 产品的集合
   * @throws QMException
   * @return Collection vector集合：
   *  结果集合。每个集合项是一个Vector.依次放零部件生命周期，零部件id，零部件编号，零部件名称，
   * 版本，父件号，零部件在产品中数量集合，路线数量，路线集合（其中路线集合每个条目包括制造装配2中路线）
   */
  public Collection getColligationRoutes(String mDepartment, String cDepartment,
                                         Vector parts, Vector products) throws
      QMException {
    if (VERBOSE) {
      System.out.println("进入路线服务getColligationRoutes方法 :" +
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
      //5.25更改为没有零部件也显示
      return getColligationRoutesDetial(mDepartment, cDepartment, partInfos,
                                        productInfos, true);
    }catch(Exception e)
    {
      e.printStackTrace();
      throw new QMException(e);
    }
  }

  /**
   * 得到当前部门的所有子部门
   * @param results Vector  结果集合
   * @param depart BaseValueIfc 当前部门
   * @throws QMException
   * @return Vector 结果集合
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

       	//获得当前部门的所有子部门
          getAllSubDepartMentID(results, (BaseValueIfc) i.next());

        
        }
      }
    }
    return results;
  }

  /**
   * 生成综合路线
   * @param mDepartment String 制造单位的id
   * @param cDepartment String 装配单位的id
   * @param parts Vector 子件集合
   * @param products Vector 夫件集合
   * @param showAll boolean  是否显示所有产品结构，只有当生成综合路线客户端没有添（右边）领部件条件时为true
   * @throws QMException
   * @return Collection  结果集合。每个集合项是一个Vector.依次放零部件生命周期，零部件id，零部件编号，零部件名称，
   * 版本，父件号，零部件在产品中数量集合，路线数量，路线集合（其中路线集合每个条目包括制造装配2中路线）
   */
  private Collection getColligationRoutesDetial(String mDepartment,
                                                String cDepartment,
                                                Vector parts, Vector products,
                                                boolean showAll) throws
      QMException {
    if (VERBOSE) {
      System.out.println("进入路线服务getColligationRoutesDetial方法 :" +
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
        System.out.println("返回路线服务getRouteByPartAndDep方法 :" +
                           "mDepartment = " + mDepartment + "  cDepartment=" +
                           cDepartment + " partMasterID=" + part.getMasterBsoID() +
                           "得到结果树" + routeAndLinks);

        //如果是要显示整个产品结构，当前领部件又没有路线，则，只显示产品结构信息
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
        //获得不重复的父件集合
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
        //add by guoxl 2008.12.9(获得路线表编号，添加到集合中)
          String routeListNum=lrpLink.getLeftBsoID();
          TechnicsRouteListIfc routeListIfc=(TechnicsRouteListIfc) pservice.refreshInfo(routeListNum);

          TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo)routeListIfc.getMaster();
          //获得现有的最新小版本
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
   * 把路线串添加结果集中
   * @param result Vector 结果集
   * @param map HashMap  路线串集合
   * @return Vector
   */
  private Vector assembleBranchStr(Vector result, HashMap map) {
    if (VERBOSE) {
      System.out.println("进入路线服务assembleBranchStr方法 :" +
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
      Vector makeNodes = (Vector) objs[0]; //制造节点
      RouteNodeIfc asseNode = (RouteNodeIfc) objs[1]; //装配节点
      if (makeNodes != null && makeNodes.size() > 0) {
        for (int m = 0; m < makeNodes.size(); m++) {
          RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
          if (makeStr == "") {
            makeStr = makeStr + node.getNodeDepartmentName();
          }
          else {
            makeStr = makeStr + "→" + node.getNodeDepartmentName();
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
      System.out.println("返回路线服务assembleBranchStr方法 :" +
                         "makeBranchs = " + makeBranchs
                         );
    }
    return result;
  }
  /**
   * （问题五）zz 周茁添加，用于瘦客户显示路线串
   * 把路线串添加结果集中,瘦客户适用，路线串不经过branchNode直接从branch对象中取出字符串
   * @param result Vector 结果集
   * @param map HashMap  路线串集合
   * @return Vector
   */
  private Vector assembleBranchStrForThin(Vector result, HashMap map) {
    if (VERBOSE) {
      System.out.println("进入路线服务assembleBranchStr方法 :" +
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
//        System.out.println("制造 制造 制造 " +makeStr );
//            System.out.println("装配 装配 装配 " +assemStr );

//      Object[] objs = (Object[]) values.next();
//      Vector makeNodes = (Vector) objs[0]; //制造节点
//      RouteNodeIfc asseNode = (RouteNodeIfc) objs[1]; //装配节点
//      if (makeNodes != null && makeNodes.size() > 0) {
//        for (int m = 0; m < makeNodes.size(); m++) {
//          RouteNodeInfo node = (RouteNodeInfo) makeNodes.elementAt(m);
//          if (makeStr == "") {
//            makeStr = makeStr + node.getNodeDepartmentName();
//          }
//          else {
//            makeStr = makeStr + "→" + node.getNodeDepartmentName();
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
      System.out.println("返回路线服务assembleBranchStr方法 :" +
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
    * 计算子件在产品中使用的数量（只计算其父件在产品中的数量）
    * @param parts Vector 子件
    * @param parent QMPartIfc 父件
    * @param product QMPartIfc 产品
    * @throws QMException
    * @return int 在产品中使用的数量
    * @see QMPartInfo
    */
   public HashMap calCountInProduct(Vector parts, QMPartIfc parent,
                                QMPartIfc product) throws QMException {
         if(VERBOSE)
             System.out.println("进入 calCountInProduct   parts="+parts+" parent="+parent+" product="+product);
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
         //    System.out.println("用时calCountInProduct====="+(after4 - before4));
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
           //  System.out.println("用时calCountInProduct====="+(after5 - before5));
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
   * 计算子件在产品中使用的数量（只计算其父件在产品中的数量）
   * @param part QMPartIfc 子件
   * @param parent QMPartIfc 父件
   * @param product QMPartIfc 产品
   * @throws QMException
   * @return int 在产品中使用数量
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
     System.out.println(part.getBsoID() +part.getPartName() + "在" + parent.getBsoID()+parent.getPartName() +
                        product.getBsoID()+product.getPartName()+
                        "中的数量==" + count);
   if (count != null && !count.trim().equals(""))
     return new Integer(count).intValue();
   else
     return 0;
  }


  /**
   * 计算子件在产品中使用的数量（只计算其父件在产品中的数量）
   * @param part QMPartIfc 子件
   * @param parent QMPartIfc 父件
   * @param product QMPartIfc 产品
   * @throws QMException
   * @return int 计算子件在产品中使用的数量数量
   */
  private int calCountInProduct(QMPartIfc part, QMPartIfc parent,
                               QMPartIfc product,List list,List midleList) throws QMException {
    if (parent == null) {
      return 0;
    }
    String count = (String) getPartQuantity(product,
        (QMPartMasterIfc) parent.getMaster(), part,list,midleList);
    if (VERBOSE) {
      System.out.println(part.getBsoID() + part.getPartName() + "在" +
                         parent.getBsoID() + parent.getPartName() +
                         product.getBsoID() + product.getPartName() +
                         "中的数量==" + count);
    }
    if (count != null && !count.trim().equals("")) {
      return new Integer(count).intValue();
    }
    else {
      return 0;
    }
  }

  /**
   * 计算子件在产品中使用的数量
   * @param part QMPartIfc 子件
   * @param parent QMPartIfc 父件
   * @throws QMException
   * @return int 数量
   */
  private int calCountInProduct(QMPartIfc part, QMPartIfc parent,List list) throws
      QMException {
    if (part.getPartNumber().equals(parent.getPartNumber())) {
      return 1;
    }
    String count = (String) getPartQuantity(parent, part,list);
    if (VERBOSE) {
      System.out.println(part.getBsoID() + part.getPartName() + "在" +
                         parent.getBsoID() + parent.getPartName() +
                         "中的数量==" + count);
    }
    if (count != null && !count.trim().equals("")) {
      return new Integer(count).intValue();
    }
    else {
      return 0;
    }
  }

  /**
   * 通过零件，装配单位和制造单位获得路线，零件，关联的集合
   * @param mDepartment String 制造单位的id
   * @param cDepartment String 装配单位的id
   * @param partMasterID String 产品id
   * @throws QMException
   * @return Collection 集合项为vector，格式为：路线与零部件关联，路线啊，零部件
   */
  /*delete by guoxl  end on 2008-12-23(解放实施问题，关于按路线单位搜索)
  private Collection getRouteByPartAndDep(Vector mDepartments,
                                          Vector cDepartments,
                                          String partMasterID) throws
      QMException {
    if (VERBOSE) {
      System.out.println("进入路线服务getRouteByPartAndDep方法 :" +
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
      System.out.println("getRouteByPartAndDep 查询条件为： " + query.getDebugSQL());
    }
    return pservice.findValueInfo(query);

  }delete end by guoxl  end on 2008-12-23(解放实施问题，关于按路线单位搜索)*/
  //add by guoxl on 2008-12-23(解放实施问题，关于按路线单位搜索)
  private Collection getRouteByPartAndDep(Vector mDepartments,
                                          Vector cDepartments,
                                          String partMasterID) throws
      QMException {
    if (VERBOSE) {
      System.out.println("进入路线服务getRouteByPartAndDep方法 :" +
                         "mDepartment = " + mDepartments + "  cDepartment=" +
                         cDepartments + " partMasterID=" + partMasterID);

    }
    PersistService pservice = (PersistService) EJBServiceHelper.
        getPersistService();
//在三个表中查找数据：１．路线零件关联，２．路线表，３．零件主信息
    QMQuery query = new QMQuery(this.LIST_ROUTE_PART_LINKBSONAME);
    int routeCount = query.appendBso(this.TECHNICSROUTE_BSONAME, true);
    int partCount = query.appendBso("QMPartMaster", true);

    QueryCondition routeToLink = new QueryCondition("routeID", "bsoID");
    query.addCondition(0, routeCount, routeToLink);//向第一个表和第二个表添加关联条件routeID=bsoID
    query.addAND();
    QueryCondition partToLink = new QueryCondition("rightBsoID", "bsoID");
    query.addCondition(0, partCount, partToLink);//向第一个表和第三个表添加关联条件rightBsoID=bsoID
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
    //add by guoxl on 2008-12-19(解放实施问题)
    int mNodeCount = query.appendBso(this.ROUTENODE_BSONAME, false);
    //add by guoxl end on 2008-12-19(解放实施问题)
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

        	
        //在表routeNode里查找条件为指定装配单位的集合
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
    //add by guoxl  on 2008-12-19(解放实施问题)
    query.addAND();
    query.addCondition(mNodeCount, routeCount, nodetoRoute);
    //add by guoxl end on 2008-12-19(解放实施问题)
    // query.addOrderBy(partCount, "partNumber");
    query.setDisticnt(true);
    if (VERBOSE) {
      System.out.println("getRouteByPartAndDep 查询条件为： " + query.getDebugSQL());
    }
   
    return pservice.findValueInfo(query);

  }//add end by guoxl on 2008-12-23(解放实施问题，关于按路线单位搜索)

  /**
   * 得到所有零部件的父件，合并相同的
   * @param subs Vector 子件集合
   * @throws QMException
   * @return Vector  HashMap集合：key:partBsoID,value:QMPartIfc
   * 合并后的父件集合
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
          System.out.println("福建为0");
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
   * 得到所有子件，合并相同的
   * @param products Vector 产品集合
   * @throws QMException
   * @return Vector 合并后的子件集合
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
   * 排序添加零部件，按编号
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
   * 根据零部件查找是否有路线
   * @param vec Vector 零件值对象集合
   * @throws QMException
   * @return Vector[] 存放了两个vector:<br>
   * 1.successVec vector:存放的是QMPartIfc 零件值对象 <br>
   * 2.failVec    vector::存放的是QMPartIfc 零件值对象 <br>
   * 有路线或无路线零件的集合
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
   * 根据零部件查找是否有路线
   * @param partMasterID String 零件ID
   * @throws QMException
   * @return boolean 有路线返回true 否则返回false
   */
  public boolean isHasRoute(String partMasterID) throws
      QMException {
    if (VERBOSE) {
      System.out.println(TIME +
                         "enter routeService's getPartLevelRoutes, partMasterID = " +
                         partMasterID);
    }
    if (partMasterID == null || partMasterID.trim().length() == 0) {
      throw new TechnicsRouteException("输入参数不对");
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
    //SQL不能正常处理。
    query.setDisticnt(true);
    //返回ListRoutePartLinkIfc
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
   * 获取子零部件在父零部件中的使用数量。
   * @param parentPartIfc QMPartIfc 父零部件。
   * @param childPartIfc QMPartIfc 子零部件。
   * @throws QMException
   * @return String 使用数量。
   * @see QMPartInfo
   */
  private String getPartQuantity(QMPartIfc parentPartIfc, QMPartIfc childPartIfc,List childParts)
          throws QMException
  {
      if(parentPartIfc.getPartNumber().equals(childPartIfc.getPartNumber()))
          return "1";
      //获取子零部件统计表。
      Object[] childPartsArray = childParts.toArray();
      //获取指定子零部件的使用数量。
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
   * 获取子零部件在父零部件中的使用数量。
   * @param parentPartIfc QMPartIfc 父零部件。
   * @param middlePartMasterIfc QMPartMasterIfc 中间零部件的主信息。
   * @param childPartIfc QMPartIfc 子零部件。
   * @throws QMException
   * @return String 使用数量。
   */
  private String getPartQuantity(QMPartIfc parentPartIfc,
                                QMPartMasterIfc middlePartMasterIfc,
                                QMPartIfc childPartIfc,List childParts,List midleList)
          throws QMException
  {
     StandardPartService partService = (StandardPartService) EJBServiceHelper.
        getService(PART_SERVICE);
      //获取当前用户的配置规范。
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

          //获取中间零部件的使用数量。
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
     * 获取符合配置规范的零部件。
     * @param partMasterIfc QMPartMasterIfc 零部件主信息。
     * @param partConfigSpecIfc PartConfigSpecIfc 配置规范。
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
  *  用当前用户的配置规范过滤零部件
  * @param masterCol Collection
  * @throws QMException
  * @return Collection  集合中的类型可参见 ConfigService 服务中<br>
  * filteredIterationsOf(Collection collection, ConfigSpec configSpec) 方法.<br>
  * 过滤后零件的集合
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
     * 判断是否是父件
     * @param maybeChild Vector 零件值对象集合
     * @param maybeParent QMPartIfc
     * @throws QMException
     * @return Collection vector集合：<br>
     * QMPartIfc是父件的零件集合
     * @see QMPartInfo
     */
    //(问题九)周茁添加 zz 添加集体调用方法，减少客户端调用次数提高性能
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
       //如果partIfc1没有父亲节点，说明partIfc2永远不可能是partIfc1的父亲节点，所以方法
       //永远返回false
       if(temp == null || temp.size() == 0)
       {

           return false;
       }
       for(int i=0; i<temp.size(); i++)
       {
           String bsoID1 = partMasterIfc2.getBsoID();
           String bsoID2 = ((QMPartMasterIfc)temp.elementAt(i)).getBsoID();
           //如果partMasterIfc2的BsoID和partIfc1的某个父亲节点的BsoID相等，返回true;
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
       //collection中应该是QMPartIfc的集合
       if(collection != null && collection.size() > 0)
       {
           Iterator iterator = collection.iterator();
           while(iterator.hasNext())
           {
               Object obj = iterator.next();
               if (obj instanceof QMPartIfc)
               {
                   //在查看被用于界面，如果一个父件使用一个子件多次，则只需列出一条记录，不必每次都列出
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
     * 根据指定的QMPartMasterIfc对象，
     * 通过两个表联合查询获得在PartUsageLink表中与QMPartMasterIfc关联的最新版本
     * 的QMPartIfc对象的集合。
     * @param partMasterIfc :QMPartMasterIfc对象。
     * @return collection 和partMasterIfc在PartUsageLink表中关联的最新版本QMPartIfc对象的集合。
     * @throws QMException
     */
    private Collection getUsedByParts(QMPartMasterIfc partMasterIfc) throws QMException
    {

        QMQuery query = new QMQuery("QMPart", "PartUsageLink");
        //按最新版本查询，返回条件cond
        QueryCondition condition1 = VersionControlHelper.getCondForLatest(true);
        //根据传入的数值确定表的位置，并向其添加查询条件,这里0表示是第一个表:是对第一个表添加查询条件
        query.addCondition(0,condition1);
         query.setChildQuery(false);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();

        //根据一个值对象partMasterIfc和本值对象在关联类中的关联角色名"uses", 浏览关联的另一边的bso对象
        //query - 查询的过滤条件
        Collection outcoll = (Collection)pservice.navigateValueInfo(partMasterIfc, "uses", query);
       // System.out.println("getUsedByParts dejieguo outcoll " + outcoll.size()+"partMasterIfc=="+partMasterIfc.getBsoID());
        return outcoll;
    }

    /**
     * 工艺路线的重命名
     * 路线表不只TechnicsRouteListMaster记名称和编号,TechnicsRouteList也记名称和编号
     * @param routelist TechnicsRouteListIfc 路线表值对象
     * @throws QMException
     * @return TechnicsRouteListMasterIfc 路线表值对象
     * @see TechnicsRouteListInfo
     */
//    （问题十）增加工艺路线的重命名功能 周茁添加 zz 20061214
//     flag 是否修改路线表编号
    public TechnicsRouteListMasterIfc rename(TechnicsRouteListIfc routelist)throws QMException
    {
        if (routelist == null)
        {
            return null; //如果所给参数为空返回空
        }
        PersistService pservice = null;
         pservice = (PersistService) EJBServiceHelper.getService(
                 "PersistService");
   TechnicsRouteListMasterIfc      listMaster = (TechnicsRouteListMasterIfc) pservice.refreshInfo(routelist.getMasterBsoID()); //刷新值对象

          listMaster.setRouteListNumber(routelist.getRouteListNumber());


        listMaster.setRouteListName(routelist.getRouteListName());
        
        //CCBegin by liunan 2012-04-25 打补丁v4r3_p044
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
		//CCBeginby leixiao 2010-11-30 重命名不改编号时,无法重命名
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
	
	//CCBegin by liunan 2012-04-25 打补丁v4r3_p044
          }
          else
          {
              throw new QMException("当前用户对该对象没有重命名权限！");
          }
          //CR8 end
          //CCEnd by liunan 2012-04-25
          
    try {
      listMaster = (TechnicsRouteListMasterIfc) pservice.updateValueInfo(
          listMaster);
    }
    catch (Exception ex) {
      if (ex instanceof SQLException) {
          //判断唯一性。
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
            //判断唯一性。
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
     * 专为导出物料青单所用
     * @param part QMPartIfc 零件值对象
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
          // System.out.println("查询到的关联对象 " + cols.size());
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
         * 分级物料清单的显示。
         * @param partIfc :QMPartIfc 最顶级的部件值对象。
         * @param attrNames :String[] 定制的属性，可以为空。
         * @param affixAttrNames : String[] 定制的扩展属性名集合，可以为空。
         * @param configSpecIfc :PartConfigSpecIfc 配置规范。
         * @throws QMException
         * @return Vector 返回结果是vector,其中vector中的每个元素都是一个集合：
         * 0：当前part的BsoID；
         * 1：当前part所在的级别，转化为字符型；
         * 2-...：可变的：如果没有定制属性，2：当前part的编号，3：当前part的名称
         *                              4：当前part被最顶层部件使用的数量，转化为字符型，
         *                              5：当前part的版本号，6：当前part的视图；
         *               如果定制了属性：按照所有定制的属性加到结果集合中。
         * 本方法调用了递归方法：
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
          System.out.println("进入服务 setMaterialList");
           Vector vector = null;
          try{
//            PartDebug.trace(this, PartDebug.PART_SERVICE,
//                            "setMaterialList begin ....");
            PersistService pService = (PersistService) EJBServiceHelper.getService(
                "PersistService");
            int level = 0;
            PartUsageLinkIfc partLinkIfc = new PartUsageLinkInfo();
            float parentQuantity = 1.0f;

            //记录数量和编号在排序中所在的位置。
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
            //把结果集合中的第一个元素的使用的数量变成""
            if (vector != null && vector.size() > 0) {
              Object[] first = (Object[]) vector.elementAt(0);

              //如果输出属性有数量，则将数量设置为空。
              if (quantitySite > 0) {
                first[quantitySite] = "";
              }
              vector.setElementAt(first, 0);
            }
            //还需要向该vector中添加一个元素：
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
            //下面需要通过判断来确定firstElement的值:
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
            //上面对firstElement中的所有的元素组装完毕，下面需要对firstElement ->Object[]
            //再添加到vector中的第一个位置：
            Object[] tempArray = new Object[firstElement.size()];
            for (int i = 0; i < firstElement.size(); i++) {
              tempArray[i] = firstElement.elementAt(i);
            }
            vector.insertElementAt(tempArray, 0);
            //2003.09.12为了防止"null"进入到返回值中：可以对vector中的每个元素判断
            //其是否为null, 如果是null，就转化为""
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
        //add by guoxl on 2008.03.21(part的物料清单功能调用此服务所以修改此处)
        /**
            * 获取part的替换件的编号（名称）以字串形式返回
            * @param part 零部件的
            * @return 换件的编号（名称）
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
    * 获取part的结构替换件的编号（名称）以字串形式返回
    * @param part 零部件的
    * @return 结构换件的编号（名称）
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
         * 私有方法。被setMaterialList()方法调用，实现定制分级物料清单的功能。
         * 返回结果是vector,其中vector中的每个元素都是一个集合：
         * 0：当前part的BsoID；
         * 1：当前part所在的级别，转化为字符型；
         * 2-...：可变的：如果没有定制属性，2：当前part的编号，3：当前part的名称
         *                              4：当前part被最顶层部件使用的数量，转化为字符型，
         *                              5：当前part的版本号，6：当前part的视图；
         *               如果定制了属性：按照所有定制的属性加到结果集合中。
         * @param partIfc :QMPartIfc 当前的部件。
         * @param attrNames :String[] 定制的属性集合，可以为空。
         * @param affixAttrNames :String[] 定制的扩展属性名集合，可以为空。
         * @param configSpecIfc :PartConfigSpecIfc 配置规范。
         * @param level :int 当前part所在的级别。
         * @param partLinkIfc :PartUsageLinkIfc 记录了当前part和起父亲节点的使用关系的值对象。
         * @param parentQuantity :int 当前part的父亲节点被最顶级部件使用的数量。
         * @throws QMException
         * @return Vector
         */

        private Vector fenji(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
            PartConfigSpecIfc configSpecIfc, int level, PartUsageLinkIfc partLinkIfc,
            float parentQuantity) throws QMException
        {
         //   PartDebug.trace(this, PartDebug.PART_SERVICE, "fenji begin ....");
            //保存最后结果值。
            Vector resultVector = new Vector();
            Object[] tempArray = null;
            //标识定制的普通属性是否为空，如果为空，该标识为真，否则为假：
            boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
            //标识定制的扩展属性是否为空，如果为空，该标识为真，否则为假：
            boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
            //这个字符串数组中保存的是"头信息":
            if(attrNullTrueFlag)
            {
                if(affixAttrNullTrueFlag)
                {
                    //如果两个的定制的属性集合都为空的话：
                    tempArray = new Object[3];
//                tempArray = new Object[7];
                }
                else
                {
                    //如果只有定制的扩展属性不为空的时候：
                    tempArray = new Object[3 + affixAttrNames.length];
                }
            }
            else
            {
                if(affixAttrNullTrueFlag)
                {
                    //如果只有定制的普通属性不为空的时候：
                    tempArray = new Object[3 + attrNames.length];
                }
                else
                {
                    //如果两个定制的属性集合都不为空的时候：
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
            tempArray[1] = new Integer(numberSite);//编号属性所在的位。
            tempArray[2] = new Integer(level);//level的初始值为0。
//        tempArray[2] = partIfc.getPartNumber();
//        tempArray[3] = partIfc.getPartName();
            //如果level = 0 说明是最顶级的部件。
           /**if (level == 0)
            {
                parentQuantity = 1f;
                String quan = "1";
                tempArray[4] = new String(quan);
            }
            else
            {
                //可不可以这样：不再保存parentBsoID,而是保存PartUsageLinkIfc，到循环参数中来。
                //这样可以省略再查找的过程。QMPartUsageLinkIfc partLinkIfc
                parentQuantity = partLinkIfc.getQuantity();//parentQuantity*partLinkIfc.getQuantity();
                String tempQuantity = String.valueOf(parentQuantity);
                if (tempQuantity.endsWith(".0"))
                    tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
                tempArray[4] = tempQuantity;
            }*/
            //判断是否需要定制属性进行输出。
            if (attrNullTrueFlag)
            {
                //如果两个定制的属性集合都为空的话：
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
            //结束：如果定制的普通属性为空的时候。
            //下面：如果定制的普通属性不为空的时候。
            else
            {
                //对定制的普通属性进行循环：
                for (int j=0; j<attrNames.length; j++)
                {
                    String attr = attrNames[j];
                    attr = attr.trim();
                    if(attr != null && attr.length() > 0)
                    {
                        //modify by liun 2005.3.25 改为从关联中得到单位
                        String temp = tempArray[1].toString();

                  //add by guoxl(part的物料清单功能用此处所以修改)
                        //如果属性是替换件
                   if(attr.equals("alternates"))
                   {
                            tempArray[3 + j]=getAlternates(partIfc);
                   }
                   //如果属性是结构替换件
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
                            //如果level = 0 说明是最顶级的部件。
                            if (level == 0)
                            {
                                parentQuantity = 1f;
                                String quan = "1";
                                tempArray[3 + j] = new String(quan);
                            }
                            else
                            {
                                //可不可以这样：不再保存parentBsoID,而是保存PartUsageLinkIfc，到循环参数中来。
                                //这样可以省略再查找的过程。QMPartUsageLinkIfc partLinkIfc
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
                      // tempArray[3+ attrNames.length-1] = "地地道道";
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
                            //现在的attr就是"getProducedBy"等固定的字符串了：
                            try
                            {
                                Class partClass = Class.forName(
                                    "com.faw_qm.part.model.QMPartInfo");
                                Method method1 = partClass.getMethod(attr, null);
                                Object obj = method1.invoke(partIfc, null);
                                //现在需要判断obj是否为null, 如果为null, attrNames[i] = "";
                                //如果obj不为null, 而且是String, attrNames[i] = (String)obj;
                                //如果obj是枚举类型，attrNames[i] = (EnumerationType)obj.getDisplay();
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
                        //取temp中的元素进行循环，temp[k][0]是PartUsageLinkIfc,
                        //temp[k][1]是QMPartIfc
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
            * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。
            * 本方法调用了bianli()方法实现递归。
            * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
            * 1、如果不定制属性：
            * BsoID，是（否）可分（"true","false"）、编号、名称、数量（转化为字符型）、版本和视图；
            * 2、如果定制属性：
            * BsoID，是（否）可分、其他定制属性。

            * @param partIfc :QMPartIfc 指定的零部件的值对象。
            * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
            * @param affixAttrNames : String[] 定制的要输出的扩展属性集合；可以为空。
            * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理。
            * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理。
            * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的对当前零部件的筛选条件。
            * @throws QMException
            * @return Vector 存放Object[] :tempArray[i]  零部件的统计表信息。
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

               //记录数量和编号在排序中所在的位置。
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
               //下面对vector中的元素进行合并数量的处理。...........
               Vector resultVector = new Vector();
               for (int i=0; i<vector.size(); i++)
               {
                   Object[] temp1 = (Object[])vector.elementAt(i);
                   //2003.09.12为了防止"null"进入到返回值中：可以对temp1中的每个元素判断
                   //其是否为null, 如果是null，就转化为""
                   for(int j=0; j<temp1.length; j++)
                   {
                       if(temp1[j] == null)
                       {
                           temp1[j] = "";
                       }
                   }
                   //需求是按照partNumber进行合并的！！！
                   String partNumber1 = (String)temp1[numberSite];
                   boolean flag = false;
                   for (int j=0; j<resultVector.size(); j++)
                   {
                       Object[] temp2 = (Object[])resultVector.elementAt(j);
                       String partNumber2 = (String)temp2[numberSite];
                       if (partNumber1.equals(partNumber2))
                       {
                           flag = true;

                           //如果数量的位置大于0，说明输出的属性中有数量，然后将相同零部件
                           //的数量合并。
                           if(quantitySite>0)
                           {
                               //把temp2和temp1中的元素进行合并，放到resultVector中去。:::
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

               //需要对第一个元素进行判断，如果其，source，type都和输入的source, type相同的
               //就保留，否则，删除掉。
               //其实是由partIfc决定的:::
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
                   //把第一个元素的数量改成""
                   Object[] firstObj = (Object[])resultVector.elementAt(0);

                   //如果输出属性有数量，则将数量设置为空。
                   if(quantitySite>0)
                   {
                       firstObj[quantitySite] = "";
                   }
                   resultVector.setElementAt(firstObj, 0);
               }
               //这里才保存最后最后的结果：
               Vector result = new Vector();
               //然后，这里还需要对最后的返回值集合按照当前的source和type进行过滤：
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
               //还需要向该vector中添加一个元素：
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
               //下面需要通过判断来确定firstElement的值:
               boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
               boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
               //如果定制的普通属性为空：
               if(attrNullTrueFlag)
               {
                   //如果定制的扩展属性也为空：
                   if(affixAttrNullTrueFlag)
                   {
//                ssss = QMMessage.getLocalizedMessage(RESOURCE, "versionValue", null);
//                firstElement.addElement(ssss);
//                ssss = QMMessage.getLocalizedMessage(RESOURCE, "viewName", null);
//                firstElement.addElement(ssss);
                   }
               }
               //如果定制的普通属性不为空的话：
               else
               {
                   for(int i=0; i<attrNames.length; i++)
                   {
                       String attr = attrNames[i];
                       ssss = QMMessage.getLocalizedMessage(PARTRESOURCE, attr, null);
                       firstElement.addElement(ssss);
                   }
               }
               //上面对firstElement中的所有的元素组装完毕，下面需要对firstElement ->Object[]
               //再添加到vector中的第一个位置：
               Object[] tempArray = new Object[firstElement.size()];
               for(int i=0; i<firstElement.size(); i++)
               {
                   tempArray[i] = firstElement.elementAt(i);
               }
               result.insertElementAt(tempArray, 0);
               return result;
           }

           /**
            * 本方法被setBOMList所调用，实现递归调用的功能。
            * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
            * 1、如果不定制属性：
            * BsoID，是（否）可分（"true","false"）、编号、名称、数量（转化为字符型）、版本和视图；
            * 2、如果定制属性：
            * BsoID，是（否）可分、其他定制属性。

            * @param partIfc :QMPartIfc 指定的零部件的值对象。
            * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
            * @param affixAttrNames : String[] 定制的要输出的扩展属性的集合，可以为空。
            * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理。
            * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理。
            * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的筛选条件。
            * @param parentQuantity :float 使用了当前部件的部件被最顶级部件所使用的数量。
            * @param partLinkIfc :PartUsageLinkIfc 连接当前部件和其父部件的关联关系值对象。
            * @throws QMException
            * @return Vector
            */
           private Vector bianli(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
                                String source, String type, PartConfigSpecIfc configSpecIfc,
                                float parentQuantity,PartUsageLinkIfc partLinkIfc)
              throws QMException
          {
              //本方法的主要实现过程为:::
              //1：判断当前的零部件是否是可分的零部件，以方便在把该零部件放到最后结果集合中的时候，可以确定
              //该零部件的可分标志
            //  PartDebug.trace(this, PartDebug.PART_SERVICE, "bianli begin ....");
              Vector resultVector = new Vector();
              //用来保存当前的零部件的所有合格的子零部件的集合：
              Vector hegeVector = new Vector();
              Collection collection = PartServiceRequest.getUsesPartIfcs(partIfc, configSpecIfc);
              //这个时候就应该先判断collection是否是"null"
              if(collection != null && collection.size() > 0)
              {
                  //需要对collection中的所有元素进行循环，如果有这样的元素
                  //是QMPartIfc而且其来源和类型和输入的参数是一致的，
                  //表明该输入的零部件是可分的.既是根据source, type来对子节点进行过滤:::
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
                              //这一步相当于增加了一个对当前零部件的所有儿子零部件的过滤条件.
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

              //把本part->resultVector中;
              Object[] tempArray = null;
              boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
              boolean affixAttrNullTrueFlag = affixAttrNames == null || affixAttrNames.length == 0;
              if(attrNullTrueFlag)
              {
                  //如果两个定制的属性集合都为空的时候：
                  if(affixAttrNullTrueFlag)
                  {
                      tempArray = new Object[3];
//               tempArray = new Object[7];
                  }
                  //如果定制的普通属性为空，而定制的扩展属性不为空的时候：
                  else
                  {
                      tempArray = new Object[3 + affixAttrNames.length];
                  }
              }
              else
              {
                  //如果定制的普通属性集合不为空，定制的扩展属性集合为空的时候：
                  if(affixAttrNullTrueFlag)
                  {
                      tempArray = new Object[3 + attrNames.length];
                  }
                  //如果两个定制的属性集合都不为空的时候：
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
               tempArray[1] = new Integer(numberSite);//编号属性所在的位。
//       tempArray[1] = partIfc.getPartNumber();
//       tempArray[2] = partIfc.getPartName();
              String isHasSubParts1 = QMMessage.getLocalizedMessage(PARTRESOURCE, "false", null);
              if(hegeVector != null && hegeVector.size() > 0)
              {
                  isHasSubParts1 = QMMessage.getLocalizedMessage(PARTRESOURCE, "true", null);
              }
              tempArray[2] = isHasSubParts1;
              //需要先判断partLinkIfc是否是持久化的，如果不是，parentQuantity = 1.0
              //如果是:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
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
              //下面需要根据两个定制的属性集合来对最后的结果集合进行组织：
              if (attrNullTrueFlag)
              {
                  //当两个定制的属性集合都为空的时候：
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
                  //结束：当定制的普通属性为空，而定制的扩展属性集合不为空的时候：
              }
              //上面结束：当定制的普通属性集合为空的时候。
              //下面开始：当定制的普通属性集合不为空的时候：
              else
              {
                  //先把所有的普通属性的值放到tempArray中：
                  for (int i=0; i<attrNames.length; i++)
                  {
                      String attr = attrNames[i];
                      attr = attr.trim();
                      if(attr != null && attr.length() > 0)
                      {
                          //modify by liun 2005.3.25 改为从关联中得到单位
                          //modify by guoxl on 2008.03.21(part的物料清单功能用到此处所以修改)
                          if (attr.equals("alternates")) {
                            tempArray[3 + i] = getAlternates(partIfc);
                          }
                          //如果属性是结构替换件
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
                              //需要先判断partLinkIfc是否是持久化的，如果不是，parentQuantity = 1.0
                              //如果是:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
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
                              //现在的attr就是"getProducedBy"等固定的字符串了：
                              try
                              {
                                  Class partClass = Class.forName(
                                      "com.faw_qm.part.model.QMPartInfo");
                                  Method method1 = partClass.getMethod(attr, null);
                                  Object obj = method1.invoke(partIfc, null);
                                  //现在需要判断obj是否为null, 如果为null, attrNames[i] = "";
                                  //如果obj不为null, 而且是String, tempArray[i + 5] = (String)obj;
                                  //如果obj是枚举类型，tempArray[i + 5] = (EnumerationType)obj.getDisplay();
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
              //对已经过滤处理的当前输入的零部件的所有子零部件进行递归处理::::
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

//         CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,增加"采用通知书添加" 
//liunan 2011-04-07 改成根据“更改通知书号”取零部件。 s的显示名也由“采用通知书号”改为“更改通知书号”
           /**
            * 解放系统升级，根据变更单文档号及采用通知书号搜索零件
            * @param noticeValue String
            * @return ArrayList
            * @throws QMException
            */

           //CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
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
        	//  System.out.println("-------------采用通知书-");

             BaseValueIfc stringDefine = null;
             String stringDefineID = null;
             ArrayList temp = new ArrayList();//用于存零件ID,以过滤重复零件

             QMQuery query = new QMQuery("StringDefinition");
             //CCBegin by liunan 2011-04-07 根据最新需求，有两个搜索入口，一个是“更改通知书号”，一个是“采用编号-解放”
             //query.addCondition(new QueryCondition("displayName", "=",
                                                   //"采用通知书号"));
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
                                                      "采用、更改通知书号"));
               Collection coll9 = service.findValueInfo(query9);
               Iterator iterator9 = coll9.iterator();

               if (iterator9.hasNext()) {
                 stringDefine = (BaseValueIfc) iterator9.next();
                 stringDefineID = stringDefine.getBsoID();
               }

             }
             //CCBegin by liunan 2011-04-07 根据最新需求，有两个搜索入口，一个是“更改通知书号”，一个是“采用编号-解放”
             //CCBegin by leixiao 2010-11-30 汽研将更改和采用分开,增加对采用号的搜索
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
                 	   //CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
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
                		 //比较版本，新的放到集合backList中。
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
                 	   //CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
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
                		 //比较版本，新的放到集合backList中。
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
         //CCBegin by leixiao 2010-11-30 原因：增加"按发布令号添加"  //liunan 2011-04-07 改成根据“采用编号-解放”取零部件。
           
           //CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
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
             //CCBegin by liunan 2011-04-07 根据最新需求，有两个搜索入口，一个是“更改通知书号”，一个是“采用编号-解放”
             //query.addCondition(new QueryCondition("displayName", "=",
                                                   //"发布令号"));
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
                 	   //CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
                 	   (part.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("MANUFACTURING"))&&isManufacturing)||
                 	   //CCEnd by liunan 2012-05-22
                    part.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("CANCELLED")))
                	 //CCBegin SS4
                	 //backList.add(part);
                 {
                	 if(hm.containsKey(part.getMasterBsoID()))
                	 {
                		 QMPartIfc part1 = (QMPartIfc)hm.get(part.getMasterBsoID());
                		 //比较版本，新的放到集合backList中。
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
                 	   //CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
                 	   (gp.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("MANUFACTURING"))&&isManufacturing)||
                 	   //CCEnd by liunan 2012-05-22
                    gp.getLifeCycleState().equals(LifeCycleState.toLifeCycleState("CANCELLED")))

                	 //CCBegin SS4
                	 //backList.add(gp);
                 {
                	 if(hm.containsKey(gp.getMasterBsoID()))
                	 {
                		 GenericPartInfo gp1 = (GenericPartInfo)hm.get(gp.getMasterBsoID());
                		 //比较版本，新的放到集合backList中。
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
         //CCEnd by leixiao 2010-11-30 原因：增加"按发布令号添加"按钮 
           /**
            * 解放系统升级，根据文档编号获得文档值对象
            * @param docNum String
            * @return Collection
            * @throws QMException
            */
           private Vector findDocByNumber(String  docNum) throws QMException
           {

             Vector vec = new Vector();

               //  创建新的查找对象query
               QMQuery query = new QMQuery("DocMaster");
               docNum = docNum.replace('*', '%');
               QueryCondition cond = new QueryCondition("docNum", "like", docNum);
               query.addCondition(cond);
               //  获得持久化服务
               PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
               Iterator iter = persistService.findValueInfo(query).iterator();
               while (iter.hasNext())
               {
                 DocMasterInfo docMasterInfo = ( (DocMasterInfo) iter.next());

                 //  创建新的查找对象query
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


//         CCEndby leixiao 2008-7-31 原因：解放升级工艺路线,增加"采用通知书添加"

//         CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线,增加"空路线添加"
           /**
            * ///20061009 liuming add
            * 根据零部件编号获得其所有未编制过路线的子件
            * @param productNumber 零部件编号
            * @return 集合 元素为QMPartIfc
            */
           public Collection getSubPartsNoRoute(String productNumber) throws
               QMException
           {
             //long a = System.currentTimeMillis();
             Vector result = new Vector();
             PersistService pservice = (PersistService) EJBServiceHelper.
                 getPersistService();
             //{{{{{{{{{{{{{{{获得零件主信息
             QMQuery query = new QMQuery("QMPartMaster");
             QueryCondition condition1 = new QueryCondition("partNumber", "=",
                 productNumber);
             query.addCondition(condition1);
             Collection c1 = pservice.findValueInfo(query, false);
             if ( (c1 == null) || (c1.size() == 0)) {
               throw new QMException("零件编号不存在，请重新输入！");
             }
             StandardPartService partService = (StandardPartService)
                 EJBServiceHelper.
                 getService(PART_SERVICE);
             //获得当前用户的配置规范
             PartConfigSpecIfc configSpecIfc = partService.findPartConfigSpecIfc();
             ConfigService service = (ConfigService) EJBServiceHelper.getService(
                 "ConfigService");

             for (Iterator i = c1.iterator(); i.hasNext(); )
             {
               QMPartMasterIfc productMsater = (QMPartMasterIfc) i.next();
               //}}}}}}}}}}}
               //System.out.println(">>>>>>>>>>>>>>>>>> PartMaster = " +
                //                  productMsater.getBsoID());
               //{{{{{{获得零件的最新版本
               Collection collection = service.filteredIterationsOf(productMsater,
                   new PartConfigSpecAssistant(configSpecIfc));
               if ( (collection == null) || (collection.size() > 1) ||
                   (collection.size() == 0))
              {
                 throw new TechnicsRouteException("内部错误:没有找到当前所输入零件的最新版本!");
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
            // System.out.println(">>>>>>>>>>>>>>>>>好事："+b);
             return result;

           }

           /**
            * ///20061009 liuming add
            * 根据当前筛选条件获取QMPartIfc下的所有没有编制过路线的子部件。
            * @param partIfc 零部件值对象
            * @param configSpecIfc 零部件配置规范值对象
            * @return Vector 结果集合，集合中的元素是QMPartIfc
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
                 //对collection中的元素进行循环，递归调用方法getSubPartsNoRoute(),结果，加到resultVector中
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

             //需要对结果集合进行合并，如果有相同的QMPartIfc的话，合并到一起:
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
                   //把已有路线的零件去掉
                   if(!this.isHasRoute(partIfc1.getMasterBsoID()))
                   {
                     //System.out.println(partIfc1.getPartNumber()+"没有路线");
                     result.addElement(partIfc1);
                   }
                 }
             }
             return result;

           }
//         CCEndby leixiao 2008-7-31 原因：解放升级工艺路线,增加"空路线添加"

//         CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线,增加"计算数量"
           /**
            * 20070525  add   liuming
            * 获取艺准中所有关联零件在车型中的使用数量
            * @param productMasterID 车型MasterID
            * @param partMap HashMap key：数字从0开始 value：零件MasterID
            * @throws QMException
            * @return HashMap key：数字从0开始 value：零件数量
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
               throw new QMException("Exception: TechnicsRouteService.getCounts()  productMasterID ＝null");
             }

             //整车产品的最新版本
             QMPartIfc  rootPart = getLastedPartByConfig(productMasterID,configspec);

             int size = partMap.size();
           //  System.out.println("size="+size);
             //用来标识路线表关联的零部件数量是否大于2000，大于2000时采用不同的策略计算相关部件在车型上的数量
             boolean flag = (size > 500);//2017-8-29 改为500
             //CCBegin SS32
             //有10个标准件，就用第一种方式计算数量。
             //改为5个
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
             //如果关联零件数量大于2000，则统计该车型的所有子件的数量
             if (flag)
             {
               //获得在指定车型下的所有子部件的数量,键为子件BSOID,值为子件数量
               partIDCountMap = partService.getSonPartsQuantityMap(rootPart);
              // System.out.println("-----partIDCountMap="+partIDCountMap);
             }
             //零部件id－该零部件的父件集合，减少查询父件的次数
             HashMap parentMap = new HashMap();
             //零部件在车型上使用数量的缓存
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


               ////////处理“每车数量”////////////////////////////////////////////////////
               String countInProduct = "";
               if(flag)  //如果关联零件数量大于2000
               {
                   if (partIDCountMap.get(part.getBsoID()) != null)
                   {
                     countInProduct = (String) partIDCountMap.get(part.getBsoID());
                    // System.out.println("id:"+part.getBsoID()+" countInProduct="+countInProduct);
                   }
                   else {
                     //System.out.println("没找到数量:"+part.getBsoID());
                   }
               }

               else    //关联零件不多于2000
               {
                   //是产品本身
                   if (part.getBsoID().equals(rootPart.getBsoID()))
                   {
                     countInProduct = "1";
                   }
                   else
                   {
                     //这时parentMap和useQuantityMap中都没有数据 循环后就有数据了
                	//     System.out.println(" ---------不是产品本身-------");
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

               ///////处理“每车数量”完毕
               /////////////////////////////////////////////////////////////

              resultMap.put(String.valueOf(i),countInProduct);

             }

             return resultMap;
           }

           /**
            * 20070116 liuming add
            * 获取当前用户的配置规范，如果用户是首次登陆系统，则构造默认的“工程视图标准”配置规范。
            * @throws QMException 使用ExtendedPartService时会抛出。
            * @return PartConfigSpecIfc 标准配置规范。
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
                 ViewObjectIfc view = viewService.getView("工程视图");
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
                 ViewObjectIfc view = viewService.getView("工程视图");
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


//         CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线,非生准状态不作艺准
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
        	    fbuserid= uService.getUserValueInfo("技术中心").getBsoID();
               }

        	   //System.out.println(""+fbuserid);
        	   //System.out.println("   最新版本="+part+"  "+part.getVersionValue()+" "+part.getLifeCycleState()+" "+part.getIterationModifier());
        	   if(!part.getLifeCycleState().equals("PREPARING")&&part.getIterationModifier().equals(fbuserid))
        	   {

        	     Collection  myVersionCollection = (Collection) service1.allVersionsOf( part);
        	     Iterator i = myVersionCollection.iterator();
        	     while(i.hasNext()){
        	    	 QMPartIfc part1=(QMPartIfc)i.next();
        	    	 System.out.println("   part1="+part1);
        	    	 if(part1.getLifeCycleState().equals("PREPARING")||!part.getLifeCycleState().equals(fbuserid))
        	    	 {
        	    		// System.out.println("   最后的版本＝"+part1+" "+part1.getVersionValue());
        	    		 return part1;
        	    	 }

        	     }
        	   }
        	   else{
        		   return part;
        	   }
        	   return null;
           }
//         CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线,非生准状态不作艺准

           /**
            * 获得指定零件在特定配置规范的最新小版本 20061209 liuming add
            * @param partMasterBsoID
            * @param configSpecIfc 如果configSpecIfc=null，则获得当前用户的配置规范
            * @return QMPartIfc 如果当前用户在配置规范下没有查看该零件权限或没有版本，则返回null
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

//       	CCBegin by leixiao 2009-1-8 原因：解放升级工艺路线,版本取汽研发布版本
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
            * 从IBA属性，获取零件的大版本
            * @param ID PartBsoID
            * @return 该零件IBA属性中的版本
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

//       	CCEnd by leixiao 2009-1-8 原因：解放升级工艺路线,版本取汽研发布版本

           public QMPartIfc getLastedPartByConfig(QMPartMasterIfc master) throws
           QMException
       {

         return getLastedPartByConfig(master,null);
       }

           /**
            * 获得指定零件在特定配置规范的最新小版本 20061209 liuming add
            * @param master QMPartMasterIfc
            * @param configSpecIfc 如果configSpecIfc=null，则获得当前用户的配置规范
            * @return QMPartIfc 如果当前用户在配置规范下没有查看该零件权限或没有版本，则返回null
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
               //获得当前用户的配置规范
               configSpecIfc = getCurrentUserConfigSpec();
             }
             ConfigService service = (ConfigService) EJBServiceHelper.getService(
                 "ConfigService");

             //{{{{{{获得零件的最新版本
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
//         CCEndby leixiao 2008-7-31 原因：解放升级工艺路线,增加"计算数量"

//         CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线
           /**
           *
           * @param part QMPartIfc
           * @param product QMPartIfc
           * @param atts String[] 属性叔祖，制造路线，装陪路线，编号。。。
           * @return Vector Vector中的每个元书为HashMap,HashMap的格式为：key=制造路线，装陪路线，编号。。。，value = 具体的值
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

            //遍历过滤，如果当前路线表中有路线，则取当前路线表中的，够则找最新的。另外，不要临准的
            while (i.hasNext()) {
              Object[] objss = (Object[]) i.next();
              ListRoutePartLinkInfo info1 = (ListRoutePartLinkInfo) objss[0];
              TechnicsRouteListInfo routelist1 = (TechnicsRouteListInfo) objss[1];
              if (routelist1.getRouteListState().equals("临准")) {
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
                if (att.equals("艺准编号")) {
                  temp.put("艺准编号", routelist.getRouteListNumber());
                }
                else if (att.equals("备注")) {
                  temp.put("备注", routelist.getRouteListDescription());
                }
                else if (att.equals("u") || att.equals("装配路线")) {
                  HashMap map = (HashMap) getRouteBranchs(info.getRouteID());
                  String routeText = "";
                  String assRouteText = "";
                  Iterator values = map.values().iterator();
                  while (values.hasNext()) {
                    boolean isTemp = false;
                    String makeStr = "";
                    Object[] objs1 = (Object[]) values.next();
                    Vector makeNodes = (Vector) objs1[0]; //制造节点
                    RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //装配节点
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
                      makeStr = makeStr + "(临时)";
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
                  temp.put("制造路线", routeText);
                  temp.put("装配路线", assRouteText);

                }

              }
              result.add(temp);
            }
            return result;
          }

          /**
           *
           * @param part QMPartIfc-yanqi-20061027-取装配路线时，如果自己有装配路线就用自己的装配路线；如果没有就用，父件的制造路线作为装配路线
           * @param product QMPartIfc
           * @param atts String[] 属性叔祖，制造路线，装陪路线，编号。。。
           * @return Vector Vector中的每个元书为HashMap,HashMap的格式为：key=制造路线，装陪路线，编号。。。，value = 具体的值
           * gcy Add in 2005-08-08 for jiefang
           */
          public Vector getListAndBrances(QMPartIfc product, QMPartIfc part,
                                          String[] atts, String parentFirstMakeRoute) throws
              QMException {
            PersistService pservice = (PersistService) EJBServiceHelper.
                getPersistService();
            Vector result = new Vector();
            //如果是逻辑总成则返回其父件的制造路线和装配路线
            if (part.getPartType().toString().equalsIgnoreCase("Logical") &&
                (parentFirstMakeRoute != null && parentFirstMakeRoute.length() > 0)) {
              HashMap map = new HashMap();
              map.put("制造路线", parentFirstMakeRoute);
              map.put("装配路线", parentFirstMakeRoute);
              //CCBegin SS15
              map.put("颜色件标识","");
              //CCEnd SS15
              result.add(map);
              return result;
            }
            //获取相关路线表和与路线表的关联
            Collection cols = getRoutesAndLinks(part);

            Iterator i = cols.iterator();
            ListRoutePartLinkInfo info = null;
            TechnicsRouteListInfo routelist = null;

            //获取最新的路线表。不要临准的和状态为取消的
            while (i.hasNext()) {

              Object[] objss = (Object[]) i.next();
              ListRoutePartLinkInfo info1 = (ListRoutePartLinkInfo) objss[0];
              TechnicsRouteListInfo routelist1 = (TechnicsRouteListInfo) objss[1];
              //System.out.println("-----------info1-="+info1+"   routelist1= "+routelist1+"------"+info1.getRouteID());

              if (routelist1.getRouteListState().equals("临准")) {
                continue;
              }
              if (info1.getRouteID() == null ||
                  ( (com.faw_qm.technics.route.model.TechnicsRouteInfo) pservice.
                   refreshInfo(info1.getRouteID())).getModefyIdenty().getCodeContent().equals("取消")) { //如果路线标记为取消，不要
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
              //CCBegin by liunan 2009-02-25 按路线的更新时间比较。
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
              //遍历atts属性集合，实际上只分两种情况：如果有“制造路线”或“装配路线”，返回的就是一个有四个键值对的HashMap（艺准编号，备注，制造路线，装配路线）；如果两种路线都没有，返回的是一个有两个键值对的HashMap（艺准编号，备注）。
              for (int j = 0; j < atts.length; j++) {
                String att = atts[j];
                temp.put("艺准编号", routelist.getRouteListNumber());
                temp.put("备注", routelist.getRouteListDescription());
                if (att.equals("制造路线") || att.equals("装配路线")) {
                  String routeText = ""; //制造路线－不同的路线串之间用“/”分隔，同一路线串的制造节点之间用“--”分隔
                  ArrayList routeList = new ArrayList();
                  String assRouteText = ""; //装配路线
                  HashMap map = (HashMap) getRouteBranchs(info.getRouteID());
                  //一个零部件可以有多个路线串，一个路线串中又可以有多个制造节点和一个装配节点。下面就是对路线串进行的遍历
                  Iterator values = map.values().iterator();
                  while (values.hasNext()) {
                    boolean isTemp = false;
                    String makeStr = "";
                    Object[] objs1 = (Object[]) values.next();
                    Vector makeNodes = (Vector) objs1[0]; //制造节点
                    RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //装配节点
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
                      makeStr = makeStr + "(临时)";
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
                  //yanqi-20061027-如果没有装配路线，则取其父件的制造路线
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
                             if(ss.trim().startsWith("总")||ss.trim().startsWith("薄")||ss.trim().startsWith("厚(试制)")||
                          	ss.trim().startsWith("厚(纵)")||ss.trim().startsWith("厚（焊）")||ss.trim().startsWith("焊")||
                          	ss.trim().startsWith("涂")||ss.trim().startsWith("架（装）")||ss.trim().startsWith("架（漆）")||
                          	ss.trim().startsWith("架（纵）")||ss.trim().startsWith("架（钻）")||ss.trim().startsWith("架（横抛）")||
                          	ss.trim().startsWith("架（横）"))
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
                             if(ss.trim().startsWith("总")||ss.trim().startsWith("薄")||ss.trim().startsWith("厚(试制)")||
                          	ss.trim().startsWith("厚(纵)")||ss.trim().startsWith("厚（焊）")||ss.trim().startsWith("焊")||
                          	ss.trim().startsWith("涂")||ss.trim().startsWith("架（装）")||ss.trim().startsWith("架（漆）")||
                          	ss.trim().startsWith("架（纵）")||ss.trim().startsWith("架（钻）")||ss.trim().startsWith("架（横抛）")||
                          	ss.trim().startsWith("架（横）"))
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
                  temp.put("制造路线", routeText);
                  temp.put("装配路线", assRouteText);
                 //CCBegin SS15
                  String colorFlag="";
                  if(info.getColorFlag()!=null&&info.getColorFlag().trim().equals("1")){
                	  colorFlag="是";
                  }
                  temp.put("颜色件标识",colorFlag);
                  //CCEnd SS15
                }
                /* else if (att.equals("装配路线")) {
                   HashMap map = (HashMap) getRouteBranchs(info.getRouteID());
                   String routeText = "";
                   Iterator values = map.values().iterator();
                   while (values.hasNext()) {
                     boolean isTemp = false;
                     String makeStr = "";
                     Object[] objs1 = (Object[]) values.next();
                     Vector makeNodes = (Vector) objs1[0]; //制造节点
                     RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //装配节点
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
                       makeStr = makeStr + "(临时)";
                     }
                     if (makeStr == null || makeStr.equals("")) {
                       makeStr = "";
                     }
                     routeText = routeText + makeStr;
                   }
                   temp.put("装配路线", routeText);
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
//              CCBeginby leixiao 2008-11-12 原因：解放升级工艺路线
        	    QueryCondition cond4 = new QueryCondition("alterStatus",
        	                                              QueryCondition.EQUAL,
        	                                              ROUTEALTER);
        	    query.addCondition(0, cond4);
        	    query.addAND();
//              CCEndby leixiao 2008-11-12 原因：解放升级工艺路线
        	    QueryCondition cond5 = new QueryCondition("routeID",
        	                                              QueryCondition.NOT_NULL);
        	    query.addCondition(0, cond5);
        	    query.addAND();
        	    QueryCondition cond6 = new QueryCondition("routeID", "bsoID");
        	    query.addCondition(0, j, cond6);
        	    query.addAND();
        	    QueryCondition cond7 = new QueryCondition("leftBsoID", "bsoID");
        	    query.addCondition(0, k, cond7);
        	    //SQL不能正常处理。
        	    query.setDisticnt(true);
        	    //返回ListRoutePartLinkIfc
        	    if (VERBOSE) {
        	      System.out.println(TIME +
        	                         "routeService's getPartLevelRoutes SQL = " +
        	                         query.getDebugSQL());
        	    }
        	    return pservice.findValueInfo(query, false);

        	  }
//        CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
//        CCBeginby leixiao 2008-8-7 原因：解放升级工艺路线
          /**
           * 获取指定零部件的工艺路线信息(为 查看零件的路线信息 服务)
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

                //存储:ListRoutePartLinkInfo
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
                      //获得各Master的各大版本的最新小版本
                      Collection cc = vservice.allVersionsOf(masterInfo);
                      HashMap tempMap = new HashMap();
                      for(Iterator infocc = cc.iterator();infocc.hasNext();)
                      {
                         TechnicsRouteListInfo tempList = (TechnicsRouteListInfo)infocc.next();
                          tempMap.put(tempList.getBsoID(),tempList);
                         //如果是在个人资料夹,且被他人检出,则获得原本
                         if(WorkInProgressHelper.isWorkingCopy(tempList)&& isCheckedOutByOther(tempList))
                         {
                             tempList = (TechnicsRouteListInfo)wip.originalCopyOf(tempList);
                             if(tempMap.containsKey(tempList.getBsoID()))
                             {
                               continue;
                             }
                         }

                         //由于同一路线表中包含同一零部件可能多个,所以需要执行此查询才严密
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
                      Vector makeNodes = (Vector) objs1[0]; //制造节点
                      RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //装配节点-yanqi-20061027
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
                  //CCBegin by leixiao 2010-1-12 增加修改时间
                  String modifyTime = routeList.getModifyTime().toString();
                  modifyTime =  modifyTime.substring(0,modifyTime.lastIndexOf("."));
                  String[] hehe = {
                      make, assRoute,changeSign, routeList.getRouteListNumber(),
                      routeList.getRouteListName(), routeList.getVersionValue(),
                      routeList.getBsoID(),modifyTime};//yanqi-20061027
                 // CCEnd by leixiao 2010-1-12 增加修改时间
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
           * 判断指定的对象是否已经被别的用户检出
           * @param workable 业务对象
           * @return 如果指定的对象已经被别的用户检出，则返回true
           * @throws QMRemoteException
           */
          private  boolean isCheckedOutByOther(WorkableIfc workable)
                  throws QMException
          {
              boolean flag = false;
              //"WORKING"或"TO_DELETED"或 "CHECKED_OUT"是真
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
           * 在艺准通知书批准后，系统自动对路线中包含分子公司简称的零部件对分子公司进行授权。
           * 只对零部件及其关联文档和图档进行授权。
           * 20071010 add by liuming  for jiefang
           * @param routeListID String
           * @throws QMException
           */
          public void setReadAclForSub(String routeListID) throws QMException
            {
              //System.out.println("TechnicsRouteService.setReadAclForSub.....Begin=="+routeListID);
              PersistService pservice = (PersistService) EJBServiceHelper.
                  getPersistService();
              //获得路线表中所有ListRoutePartLink
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

              //对各路线单位进行遍历
              for (Iterator iter = nodes.iterator(); iter.hasNext(); )
              {
                RouteNodeInfo nodeInfo = (RouteNodeInfo) iter.next();
                String departmentID = nodeInfo.getNodeDepartment(); //代码ID
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
                  //String classSort = ((CodingClassificationIfc)coding).getClassSort();//路线单位简称
                  String classSort = "";
                  if(coding instanceof CodingClassificationIfc)
                  {
                  	classSort = ((CodingClassificationIfc)coding).getClassSort();//路线单位简称
                  }
                  if(coding instanceof CodingIfc)
                  {
                  	classSort = ((CodingIfc)coding).getShorten();//路线单位简称
                  }
                  //CCEnd SS35
                  if(classSort.equals("岛"))
                  {
                     flag =true;
                     groupName1 = "青汽接收组";
                     groupName2 = "青汽权限组";
                  }
                  else if(classSort.equals("锡"))
                  {
                     flag =true;
                     groupName1 = "锡柴接收组";
                     groupName2 = "锡柴权限组";
                  }
                  else if(classSort.equals("柴"))
                  {
                     flag =true;
                     groupName1 = "大柴接收组";
                     groupName2 = "大柴权限组";
                  }
                  else if(classSort.equals("底"))
                  {
                     flag =true;
                     groupName1 = "车桥接收组";
                     groupName2 = "车桥权限组";
                  }
                  else if(classSort.equals("发"))
                  {
                     flag =true;
                     groupName1 = "发动机接收组";
                     groupName2 = "发动机权限组";
                  }
                  else if(classSort.equals("变"))
                  {
                     flag =true;
                     groupName1 = "变速箱接收组";
                     groupName2 = "变速箱权限组";
                  }
                  //CCBegin by liunan 2012-02-01 新增“专”单位
                  else if(classSort.equals("专"))
                  {
                     flag =true;
                     //CCBegin SS45
                     //groupName1 = "专用车接收组";
                     //groupName2 = "专用车权限组";
                     groupName1 = "长特接收组";
                     groupName2 = "长特权限组";
                     //CCEnd SS45
                  }
                  //CCEnd by liunan 2012-02-01
                  //CCBegin SS7
                  //CCBegin SS27
                  //else if(classSort.equals("轴"))
                  else if(classSort.equals("轴")||classSort.equals("轴齿车间"))
                  //CCEnd SS27
                  {
                     flag =true;
                     groupName1 = "轴齿中心接收组";
                     groupName2 = "轴齿中心权限组";
                  }
                  //CCEnd SS7
                  //CCBegin SS8
                  else if(classSort.equals("改(锡)"))
                  {
                     flag =true;
                     groupName1 = "无锡改装车接收组";
                     groupName2 = "无锡改装车权限组";
                  }
                  else if(classSort.equals("岛(沪)"))
                  {
                     flag =true;
                     groupName1 = "上海改装车接收组";
                     groupName2 = "上海改装车权限组";
                  }
                  //CCEnd SS8
                  //CCBegin SS24
                  else if(classSort.equals("川"))
                  {
                     flag =true;
                     groupName1 = "成都接收组";
                     groupName2 = "成都权限组";
                  }
                  //CCEnd SS24
                  //CCBegin SS16
//                  else if(classSort.equals("轴"))
//                  {
//                     flag =true;
//                     groupName1 = "轴齿中心接收组";
//                     groupName2 = "轴齿中心权限组";
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
                //System.out.println("授权的组 = "+group);
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
           * 得到要授权的文档和EPM文档
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
           * 获取指定零件的所有参考文档、描述文档和EPM文档
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
            //获得该零部件关联的所有参考文档(DocIfc)最新版本的值对象的集合
            Vector docInfoVector = spService.getReferencesDocMasters(partinfo);
            if(docInfoVector!=null && docInfoVector.size()>0)
            {
              result.addAll(docInfoVector);
            }
            //该零部件描述文档的值对象集合
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
           * 获取零件的EPM文档
           * 20070923 add by liuming for jiefang
           * @param part QMPartIfc
           * @return ArrayList （EPMDocumentInfo）
           * @throws QMException
           */
          private ArrayList getRelatedEpmDocs(QMPartIfc part) throws QMException {

            ArrayList result = new ArrayList();
            PersistService pservice = (PersistService) EJBServiceHelper
                .getService("PersistService");
            // 查询零部件所有关联的文档
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
           * 获得指定零件的制造路线和装配路线
           * 注意：因为逻辑总成也有可能编制路线，所以不再考虑逻辑总成的问题，视作普通零件
           * @param part QMPartIfc
           * @return String[] 第一个元素为制造路线,第二个元素为装配路线
           * @throws QMException
           */
          public String[] getRouteString(QMPartIfc part) throws QMException
          {
            PersistService pservice = (PersistService) EJBServiceHelper.
                getPersistService();
            String[] result = new String[2];
            result[0] = "";
            result[1] = "";

            //获取相关路线表和与路线表的关联
            Collection cols = getRoutesAndLinks(part);

            Iterator i = cols.iterator();
            ListRoutePartLinkInfo info = null;
            TechnicsRouteListInfo routelist = null;

            //获取最新的路线表。不要临准的和状态为取消的
            while (i.hasNext())
            {
              Object[] objss = (Object[]) i.next();
              ListRoutePartLinkInfo info1 = (ListRoutePartLinkInfo) objss[0];
              TechnicsRouteListInfo routelist1 = (TechnicsRouteListInfo) objss[1];

              if (routelist1.getRouteListState().equals("临准")) {
                continue;
              }
              if (info1.getRouteID() == null ||
                  ( (com.faw_qm.technics.route.model.TechnicsRouteInfo) pservice.
                   refreshInfo(info1.getRouteID())).getModefyIdenty().getCodeContent().equals("取消")) { //如果路线标记为取消，不要
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

            //如果找到了最新的路线表
            if (info != null)
            {
              String routeText = ""; //制造路线－不同的路线串之间用“/”分隔，同一路线串的制造节点之间用“--”分隔
              ArrayList routeList = new ArrayList();
              String assRouteText = ""; //装配路线
              HashMap map = (HashMap) getRouteBranchs(info.getRouteID());
              //一个零部件可以有多个路线串，一个路线串中又可以有多个制造节点和一个装配节点。下面就是对路线串进行的遍历
              Iterator values = map.values().iterator();
              while (values.hasNext())
              {
                boolean isTemp = false;
                String makeStr = "";
                Object[] objs1 = (Object[]) values.next();
                Vector makeNodes = (Vector) objs1[0]; //制造节点
                RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //装配节点
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
                  makeStr = makeStr + "(临时)";
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

//        CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
//        CCBegin by leixiao 2008-8-11 原因：解放升级工艺路线，导出路线
          /**
           * 收集报表数据 20061209 liuming add
           * @param routeListID String
           * @param IsExpandByProduct 是否按车展开（暂时默认总为真）
           * @throws QMException
           */
          public ArrayList gatherExportData(String routeListID, String IsExpandByProduct)
              throws
              QMException
          {
              return ReportLevelOneUtil.getAllInformation2(routeListID,"true");
          }

          /**
           * 计算子件在产品中使用的数量
           * @param part QMPartIfc 子件
           * @param parent QMPartIfc 父件
           * @throws QMException
           * @return int 数量
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
              System.out.println(part.getBsoID() + part.getPartName() + "在" +
                                 parent.getBsoID() + parent.getPartName() +
                                 "中的数量==" + count);
            }
            if (count != null && !count.trim().equals("")) {
              return new Integer(count).intValue();
            }
            else {
              return 0;
            }
          }

//        CCEnd by leixiao 2008-8-11 原因：解放升级工艺路线 ，导出路线

//        CCBegin by leixiao 2008-8-11 原因：解放升级工艺路线
          /**
           * 得到领部件的所有父件已经路线情况，解放需求
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
        	// CCBeginby leixiao 2009-2-21 原因：解放升级，此处空指针
            //int size = col.size();
        	// CCEndby leixiao 2009-2-21 原因：解放升级
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

              //遍历过滤，如果当前路线表中有路线，则取当前路线表中的，够则找最新的。另外，不要临准的
              while (i.hasNext()) {
                Object[] objss = (Object[]) i.next();
                ListRoutePartLinkInfo info1 = (ListRoutePartLinkInfo) objss[0];
                TechnicsRouteListInfo routelist1 = (TechnicsRouteListInfo) objss[1];
//          if(routelist1.getRouteListState().equals("临准"))
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
                  Vector makeNodes = (Vector) objs1[0]; //制造节点
                  RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //装配节点
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
                    makeStr = makeStr + "(临时)";
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
//        CCEnd by leixiao 2008-8-11 原因：解放升级工艺路线 ，导出路线

//        CCBegin by leixiao 2008-8-11 原因：解放升级工艺路线
          /**
           * 得到所有的部门
           * @throws QMException
           * @return Vector
           * gcy Add in 2005-08-08 for jiefang
           */
          public Vector getAllDeps() throws
              QMException {
            CodingManageService cmService = (CodingManageService) EJBServiceHelper.
                getService("CodingManageService");
            Collection temp = cmService.findDirectClassificationByName("路线组织机构", "代码分类");
            Iterator i = temp.iterator();
            Vector results = new Vector();
            while (i.hasNext()) {
              CodingClassificationIfc depart = (CodingClassificationIfc) i.next();
              results = getAllSubDepartMent(results, depart);
            }
            return results;
          }

          /**
           * 得到当前部门的所有子部门
           * @param results Vector  结果集合
           * @param depart BaseValueIfc 当前部门
           * @throws QMException
           * @return Vector 结果集合
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
//        CCEnd by leixiao 2008-8-11 原因：解放升级工艺路线

//CCBegin by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕放艺准里，修改流程
 /**
  * 为指定艺准的所有艺毕类型的零件设为生产状态（艺准通知书号）
  * @param routeListBsoID 艺准通知书BsoID
  * 20070115 liuming add
  */
 public void setRouteCompletePartsState(String routeListBsoID) throws QMException
 {
   if(routeListBsoID==null || routeListBsoID.equals(""))
   {
     throw new QMException("调用TechnicsRouteService出错：routeListNumber为空");
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
     //零部件关联
     ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
     QMPartIfc  partInfo=listLinkInfo.getPartBranchInfo();
//   CCBegin leixiao 2009-11-27 state->LifeCycleState
     if(partInfo!=null)
     lservice.setLifeCycleState(partInfo, LifeCycleState.toLifeCycleState("MANUFACTURING"));
//   CCEnd leixiao 2009-11-27 state->LifeCycleState
     
     //CCBegin by liunan 2012-05-29 获得该件的EPM文档，并设置状态。
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
//CCEnd by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕放艺准里，修改流程


//CCBegin by liunan 2011-09-21 艺废通知书。
  /**
   * 为指定艺准的所有艺废类型的零件设为已废弃状态（艺准通知书号）
   * @param routeListBsoID 艺准通知书BsoID
   */
  public void setRouteDisaffirmPartsState(String routeListBsoID) throws QMException
  {
    if(routeListBsoID==null || routeListBsoID.equals(""))
    {
      throw new QMException("调用TechnicsRouteService出错：routeListNumber为空");
    }
    PersistService ps = (PersistService) EJBServiceHelper.getService("PersistService");
    LifeCycleService lservice = (LifeCycleService) EJBServiceHelper.getService("LifeCycleService");

    LifeCycleTemplateInfo lct = lservice.getLifeCycleTemplate("无流程类零部件生命周期");
    
    //CCBegin SS23
    LifeCycleTemplateInfo huidaolct = lservice.getLifeCycleTemplate("回导零部件生命周期");
    //CCEnd SS23
    
    //CCBegin by liunan 2012-05-29 获得该件的EPM文档，并设置状态。
    LifeCycleTemplateInfo lctEpm = lservice.getLifeCycleTemplate("图档生命周期");
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
      		if(tempName.equals("回导零部件生命周期"))
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
      
      
     //CCBegin by liunan 2012-05-29 获得该件的EPM文档，并设置状态。
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
String tempRoute1 = "否";
Vector makeNodes = (Vector) branchs[0]; //制造节点
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
tempRoute1 = "是";
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


//              CCEnd by leixiao 2008-9-12 原因：解放升级,艺毕通知书
//CCBegin by leixiao 2008-10-07 原因：解放升级,艺准通知书流程
/**
 * 为指定艺准的所有采用状态的零件添加IBA属性值（艺准通知书号）
 * @param routeListBsoID 艺准通知书BsoID
 * 20070115 liuming add
 */
public void addIBAvalueForPart(String routeListBsoID) throws QMException
{
  if(routeListBsoID==null || routeListBsoID.equals(""))
  {
    throw new QMException("调用TechnicsRouteService出错：addIBAvalueForPart参数routeListNumber为空");
  }
  PartConfigSpecIfc cpnfigspec = this.getCurrentUserConfigSpec();
  PersistService ps = (PersistService) EJBServiceHelper.getService(
      "PersistService");

  TechnicsRouteListIfc routelist = (TechnicsRouteListIfc)ps.refreshInfo(routeListBsoID,false);
  HashMap map = new HashMap();
  Collection coll = this.getRouteListLinkParts(routelist);
  for (Iterator iter = coll.iterator(); iter.hasNext(); )
  {
    //零部件关联
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
    System.out.println("没找到符合条件的零部件");
    return;
  }

  //查找iba属性定义
  QMQuery query = new QMQuery("StringDefinition");
  QueryCondition cond = new QueryCondition("name", "=", "艺准通知书号");
  query.addCondition(cond);
  Collection queryresult = ps.findValueInfo(query);
  AttributeDefinitionIfc definition = null; //属性定义
  AttributeDefDefaultView defaultView = null; //属性视图
  if (queryresult.iterator().hasNext())
  {
    definition = (AttributeDefinitionIfc) queryresult.iterator().next();
    //System.out.println("找到了属性定义:"+definition.getBsoID());
    defaultView = IBADefinitionObjectsFactory.newAttributeDefDefaultView(definition);
  }
  else
  {
    System.out.println("没找到属性定义！");
    return;
  }

  //创建字符串型的属性值
  StringValueDefaultView stringValueView = new StringValueDefaultView(
      (StringDefView) defaultView, routelist.getRouteListNumber());

  AbstractValueIfc abstractvalueIfc = null;
  IBAValueService vs = (IBAValueService) EJBServiceHelper.getService(
      "IBAValueService");

  for (Iterator it = map.values().iterator();it.hasNext();)
  {
    QMPartIfc holder = (QMPartIfc)it.next();
    //System.out.println("更新零件：" + holder.getPartNumber() + " " +
     //                    holder.getBsoID());
    //获取属性所有者的属性容器
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
      attrCont.addAttributeValue(stringValueView); //设置属性值
      try {
        ps.updateValueInfo(holder, false); //保存零部件
      }
      catch (Exception e) {
        System.out.println("无法更新零件：" + holder.getPartNumber() + " " +
                           holder.getBsoID());
      }
    }
  }
  map=null;
}
//CCEnd by leixiao 2008-9-12 原因：解放升级,艺准通知书
//CCBegin by leixiao 2008-9-12 原因：解放升级,艺准通知书
/**
 * 重命名 20061209 liuming add
 * @param masterInfo TechnicsRouteListMasterIfc
 * @return TechnicsRouteListMasterIfc
 * @throws QMException
 */
public TechnicsRouteListMasterIfc renameRouteListMaster(TechnicsRouteListMasterIfc
        masterInfo)
        throws QMException
{

    //如果对象为空，抛出异常
    if (masterInfo == null)
    {
        throw new QMException("服务端异常:TechnicsRouteService.renameRouteListMaster ERROR!");
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
        //检查对该业务对象是否有修改权限
         AccessControlService acs=(AccessControlService)EJBServiceHelper.getService("AccessControlService");
        //调用服务VersionControlService的方法,给定master，得到这个对象的
        //所有的小版本（包括不同分枝），也就是master对应的所有小版本。
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
    String haha = af.nextToken() + "年" + af.nextToken() + "月" + af.nextToken() +
        "日";
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
             if (routeListState.equals("临准") || routeListState.equals("前准")) {
              // lservice.setLifeCycleState(partInfo, State.toState("SHIZHI"));
         lservice.setLifeCycleState(partInfo, State.toState("MANUFACTURING"));
             }
             if (routeListState.equals("艺准")) {
            //  lservice.setLifeCycleState(partInfo, State.toState("PREPARING"));
         lservice.setLifeCycleState(partInfo, State.toState("MANUFACTURING"));
             }*/
        //CCBegin by leixiao 2009-11-27 原因：解放升级工艺路线
        partInfo.setLifeCycleState(LifeCycleState.toLifeCycleState("PREPARING"));
        //CCEnd by leixiao 2009-11-27 原因：解放升级工艺路线
        perService.updateValueInfo(partInfo, false);
      }
    }
    catch (QMException ex) {
      ex.printStackTrace();
    }
}



//CCEnd by leixiao 2008-9-12 原因：解放升级,艺准通知书
//CCBegin by leixiao 2008-11-10 原因：解放升级,艺准通知书选择零件时，显示该件的相关艺准
/**
 * 得到零部件的路线串信息
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
      Vector makeNodes = (Vector) objs1[0]; //制造节点
      RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //装配节点
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
        makeStr = makeStr + "(临时)";
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
//CCEnd by leixiao 2008-11-10 原因：解放升级
//CCBegin by leixiao 2009-4-14 原因：解放升级
public ArrayList getcompleteroutepart(String routeid)throws QMException{
	ArrayList completepart=new ArrayList();
	String pversion="";
	String pversionvalue="";
	  PersistService pService = (PersistService) EJBServiceHelper.
	  getPersistService();
		QMQuery query = new QMQuery("StringDefinition");
		QueryCondition qc = new QueryCondition("displayName", " = ", "发布源版本");
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
//CCEnd by leixiao 2009-4-14 原因：解放升级
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

				  System.out.println("------发布原版本："+version);
				   preString="("+version+")";
				  }
				  String routeid=link.getRouteID();
				  //System.out.println("  路线routeid="+routeid);
				  TechnicsRouteIfc routeifc=null;
				  if(routeid!=null)
				  routeifc=(TechnicsRouteIfc)pService.refreshInfo(routeid);
				  if(routeifc!=null){
				  String describ=routeifc.getRouteDescription();
				 // System.out.println("----------原始说明："+describ);
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
			      System.out.println("----------原始说明："+describ+"  现说明:"+describ1);
				  routeifc.setRouteDescription(describ1);
				   pService.saveValueInfo(routeifc);
				   System.out.println("----更新后的说明"+routeifc.getRouteDescription());
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

    System.out.println("数据处理完成！");
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
        .println("enter routeService's getValidTime 月份s3 = "
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
    //判断闰年。
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
throw new TechnicsRouteException("输入时间错误或解析有问题。");
}
return convert;
}
//CCBegin by liujiakun 工艺定额
/**
 
 * @param info String
 * @throws QMException
 * @return String[] 1,路线状态、2，新路线、3，旧路线
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
      //获得路线状态
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      TechnicsRouteInfo route = (TechnicsRouteInfo) pservice.refreshInfo(
          linkinfo.getRouteID());
      //System.out.println("route="+route);
      //liujiakun 将原来的CodingIfc类型转换为字符串类型
      routestate = route.getModefyIdenty().toString();
      //获得原来路线
     /*
      String lastLinkID = linkinfo.getLastEffRoute();
      if (lastLinkID != null && lastLinkID.length() > 0) {
        ListRoutePartLinkIfc lastlink = (ListRoutePartLinkIfc) pservice.
            refreshInfo(lastLinkID);
        if (lastlink.getRouteID() != null &&
            lastlink.getRouteID().length() > 0) {
          //原来路线
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
 * 根据路线表与零部件关联值对象集合获得每个零部件的路线串
 * @param coll Collection 路线表与零部件关联值对象集合
 * @throws QMException
 * @return Vector  存放数组，每个数组三个元素，{零部件编号，零部件名称，路线串}
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
    //获得最新小版本
    VersionControlService fservice = (VersionControlService) EJBServiceHelper.
        getService(
        "VersionControlService");
    Collection col = (Collection) fservice.allVersionsOf(partmasterInfo);
    QMPartIfc partifc = (QMPartIfc) col.iterator().next();
    //放在数组的第四个位置
    String partnumber = partmasterInfo.getPartNumber();
    String partname = partmasterInfo.getPartName();
    String routeid = info.getRouteID();
    String routetext = getRouteTextByRouteID(routeid);
    if (routetext.indexOf("涂") < 0 && routetext.indexOf("漆(身)") < 0 &&
        routetext.indexOf("漆(架)") < 0) {
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
 * 根据零获得当前有效路线。
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

//用例5.搜索路线表(管理器)
//版本 <v2.0>
//文档编号：PHOS-CAPP-UC305
//用例6.搜索工艺路线表
//版本 <v2.0>
//文档编号：PHOS-CAPP-UC306
//CCBegin by liujiakun 工艺定额
/**
 * @param param 二维数组，5个元素。例：obj[0]=编号，obj[1]=true. true=是，false=非。数组顺序：编号、名称、级别（汉字）、用于产品、版本号。
 * @roseuid 402CBAF700CA
 * @J2EE_METHOD  --  findRouteLists
 * 获得工艺路线表。搜索范围：编号、名称、级别、用于产品、版本号。
 * @return collection 工艺路线表值对象，最新版本。
 * 此时要用ConfigService进行过滤。
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
      //如果产品编号输入错误，将有提示，是否需要。
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
         //如果版本恰好为最新版，可能搜出个人资料夹的东西。
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
    //routelist值对象集合。
    Collection result = pservice.findValueInfo(query);
    //根据工作状态进行过滤。原本副本不能同时存在。获得工艺路线表值对象集合。
    //filterByWorkState(result);
    //根据路线表编号升序排列。
    //modified by dikef 20060824
    //Collection sortedVec = RouteHelper.sortedInfos(result,
    //   "getRouteListNumber", false);
    return result;
  }
  
/**
 * 用于材料定额的过滤 2007.11.21
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
    //获得最新小版本
    VersionControlService fservice = (VersionControlService) EJBServiceHelper.
        getService(
        "VersionControlService");
    Collection col = (Collection) fservice.allVersionsOf(partmasterInfo);
    QMPartIfc partifc = (QMPartIfc) col.iterator().next();
    //放在数组的第5个位置
    String partnumber = partmasterInfo.getPartNumber();
    String partname = partmasterInfo.getPartName();
    String routeid = info.getRouteID();
    String routestate = "";
    String newroute = "";
    //获得路线状态
    if(routeid!=null){
    TechnicsRouteInfo route = (TechnicsRouteInfo) pservice.refreshInfo(
        info.getRouteID());
    routestate = route.getModefyIdenty().toString();
    //获得原来路线
    String lastroute = "";
    /*String lastLinkID = info.getLastEffRoute();
    if (lastLinkID != null && lastLinkID.length() > 0) {
      ListRoutePartLinkIfc lastlink = (ListRoutePartLinkIfc) pservice.
          refreshInfo(lastLinkID);
      if (lastlink.getRouteID() != null &&
          lastlink.getRouteID().length() > 0) {
        //原来路线
        lastroute = getRouteTextByRouteID(lastlink.getRouteID());
      }
    }*/

    newroute = getRouteTextByRouteID(routeid);

    //这个地方暂时屏蔽掉  不知道这个功能的真正意义liujiakun20090318
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
 * 判断该零件有没有生效的主材定额
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
 * tangshutao 2007.11.22 主材定额编制的过滤条件
 * 在路线对应条件文件中主材列中如果有该路线单位，那么就在该路线中检索路线中的第一个路线单位所在车间是否编制了主材定额，如果没有则符合条件
 * @param partmasterInfo QMPartMasterIfc
 * @param routetext String
 * @param sourcedepartment String 准备编定额的部门，根据此部门筛选零件。
 * @return boolean
 */
private boolean isRouteHasRation(QMPartMasterIfc partmasterInfo,
        String routetext, String sourcedepartment) throws
QMException {
boolean flag = false;
String sourceShorten = "-";
//生(调件)=总(装)(临时),酸--料--冲--涂=总(装)
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

//分开制造路线和装配路线。多条路线只要有一条符合就需要编制定额。
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

//1、制造路线单位是“协”，则该零部件不编制主材定额
//2、制造路线单位是生(调件)，不编主材定额.
if (manroute.equalsIgnoreCase("协") ||
manroute.equalsIgnoreCase("生(调件)")) {
flag = false;
continue;
}
//6、零部件路线串中无制造路线单位，按装配路线单位编制主材定额。
//7、制造路线单位不包含“料”、“酸”、“供”、“备”、“料（废）”，则不编制主材定额。
if ( (!manroute.equalsIgnoreCase(""))) {
String tempmanroute = "-" + manroute + "-";
if (tempmanroute.indexOf("-料-") < 0 && tempmanroute.indexOf("-酸-") < 0 &&
tempmanroute.indexOf("-供-") < 0
&& tempmanroute.indexOf("-备-") < 0 &&
tempmanroute.indexOf("-料(废)-") < 0) {
flag = false;
continue;
}
}
//制造路线无单位或只有“料”、“酸”、“供”、“备”、“料(废)”，按装配路线单位编制。
if (manroute.equalsIgnoreCase("")) {
manroute = asseroute;
}
else if (!asseroute.equalsIgnoreCase("")) {
manroute = manroute + "--" + asseroute;
}

StringTokenizer manrouteToken = new StringTokenizer(manroute, "--");

while (manrouteToken.hasMoreTokens()) {
String manroutenode = manrouteToken.nextElement().toString();

//制造路线单位首位是“料”、“酸”、“供”、“备”、“料（废）”对其下一个路线单位编制材料定额。
//如果上述路线单位中两个或多个出现在同一个路线串中则对上述路线单位下一个路线单位编制主材定额
//(如 酸料冲机 则只对冲压车间编制主材定额).
if (manroutenode.equalsIgnoreCase("料") ||
manroutenode.equalsIgnoreCase("酸") ||
manroutenode.equalsIgnoreCase("供") ||
manroutenode.equalsIgnoreCase("备") ||
manroutenode.equalsIgnoreCase("料(废)")) {
continue;
}
//判断是否是所选部门，且是否编制定额。
//部门分类
//CodingClassificationIfc department = null;
//QMQuery query = null;
//PersistService pservice = null;
try {
//System.out.println("开始检查符合编制条件的路线");
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
//如果不是本次选择的部门，则不编制。
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
//如果有多条路线，则有一条需要编定额就返回true
if (flag == true) {
break;
}
}
return flag;
}
  /**
   * 2007.12.1 tangshutao 符合辅助材料定额编制的零件过滤
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
      //获得最新小版本
      VersionControlService fservice = (VersionControlService) EJBServiceHelper.
          getService(
          "VersionControlService");
      Collection col = (Collection) fservice.allVersionsOf(partmasterInfo);
      QMPartIfc partifc = (QMPartIfc) col.iterator().next();
      //放在数组的第5个位置
      String partnumber = partmasterInfo.getPartNumber();
      String partname = partmasterInfo.getPartName();
      String routeid = info.getRouteID();
      String routetext = getRouteTextByRouteID(routeid);
      //获得路线状态
      TechnicsRouteInfo route = (TechnicsRouteInfo) pservice.refreshInfo(
          info.getRouteID());
      String routestate = route.getModefyIdenty().toString();
      //获得原来路线
      String lastroute = "";
      /*String lastLinkID = info.getLastEffRoute();
      if (lastLinkID != null && lastLinkID.length() > 0) {
        ListRoutePartLinkIfc lastlink = (ListRoutePartLinkIfc) pservice.
            refreshInfo(lastLinkID);
        if (lastlink.getRouteID() != null &&
            lastlink.getRouteID().length() > 0) {
          //原来路线
          lastroute = getRouteTextByRouteID(lastlink.getRouteID());
        }
      }*/

      String newroute = getRouteTextByRouteID(routeid);

      //这个地方暂时屏蔽掉  不知道这个功能的真正意义liujiakun20090318
      // 可能解放条件和这里也不一样
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
      //该部门唯一生效的辅材定额
      str[7] = hasValidAssisRationByPart(partmasterid, department);
      v.add(str);
    }
    return v;

  }
  
  /**
   * 2007.12.10 tangshutao
   * 判断零件在某车间内有没有生效的辅材定额
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
   * tangshutao 2007.12.1 根据路线串过滤符合编制辅助材料定额的零件
   * 针对每一个路线单位都要查询他所属的车间是否编制了辅材定额，路线单位重复的，只取一次
   * @param partmasterInfo QMPartMasterIfc
   * @param routetext String
   * @return boolean
   */
  private boolean isAssisRouteHasRation(QMPartMasterIfc partmasterInfo,
                                        String routetext,
                                        String sourcedepartment) {
    boolean flag = false;
    //分开制造路线和装配路线。多条路线只要有一条符合就需要编制定额。
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

      //1、制造路线单位是“协”，则该零部件不编制辅材定额
      //2、制造路线单位是生(调件)，不编辅材定额.
      //3、制造路线单位是生，不编辅材定额.
      if (manroute.equalsIgnoreCase("协") ||
          manroute.equalsIgnoreCase("生(调件)") ||
          manroute.equalsIgnoreCase("生")) {
        flag = false;
        break;
      }
      //制造路线无单位或只有“料”、“酸”、“供”、“备”、“料(废)”，按装配路线单位编制。
      if (manroute.equalsIgnoreCase("")) {
        manroute = asseroute;
      }
      else if (!asseroute.equalsIgnoreCase("")) {
        manroute = manroute + "--" + asseroute;
      }

      StringTokenizer manrouteToken = new StringTokenizer(manroute, "--");
      while (manrouteToken.hasMoreTokens()) {
        String manroutenode = manrouteToken.nextElement().toString();

        //制造路线单位中是“料”、“酸”、“供”、“备”、“料（废）”不对单位编制材料定额。
        if (manroutenode.equalsIgnoreCase("料") ||
            manroutenode.equalsIgnoreCase("酸") ||
            manroutenode.equalsIgnoreCase("供") ||
            manroutenode.equalsIgnoreCase("备") ||
            manroutenode.equalsIgnoreCase("料(废)")) {
          continue;
        }
        //判断是否是所选部门。
        //部门分类
        CodingClassificationIfc department = null;
        QMQuery query = null;
        PersistService pservice = null;
        try {
          //System.out.println("开始检查符合编制条件的路线");
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
            //如果是本次选择的部门，则编制。
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
      //如果有多条路线，则有一条需要编定额就返回true
      if (flag == true) {
        break;
      }
    }

    return flag;
  }
  
  /**
   * 2008.01.15 根据艺准获得关联的零件
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
 * 根据路线ID获得路线串
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
    Vector makeNodes = (Vector) objs1[0]; //制造节点
    RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //装配节点
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
      makeStr = makeStr + "(临时)";
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
  //System.out.println("路线串routeText: " + routeText);
  return routeText;
}

//CCEnd by liujiakun 工艺定额

//CCBegin by liunan 2011-09-20 根据零部件找是否有路线，有则返回所在艺准信息。
  /**
   *  根据零部件查找路线
   * @param partMasterID String 零部件bsoid
   * @throws QMException
   * @return Collection  数组集合。String[] {partID, routeID, routeState, linkID, state,
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
      throw new TechnicsRouteException("输入参数不对");
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
    //SQL不能正常处理。
    query.setDisticnt(true);
    //返回ListRoutePartLinkIfc
    if (VERBOSE) {
      System.out.println(TIME +
                         "routeService's getPartLevelRoutes SQL = " +
                         query.getDebugSQL());
    }
    Collection coll = pservice.findValueInfo(query);
    //路线修改时间降序排列。
    Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
    if (VERBOSE) {
      System.out.println(TIME + "查询结果为： coll = " + sortedVec.size());
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
      	result = result+"、"+techinicsRoute.getRouteListNumber();
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
   * 比较两个版本的大小。
   * @param s1 String
   * @param s2 String
   * @return boolean s1新时返回true，否则返回false。
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

    //利用StringTokenizer分割st1
    StringTokenizer st1 = new StringTokenizer(s1, ".");
    //利用StringTokenizer分割st2
    StringTokenizer st2 = new StringTokenizer(s2, ".");
    int level1 = st1.countTokens();
    int level2 = st2.countTokens();
    //两个版本值长度大为较新的
    if (level1 < level2) {
      return false;
    }
    if (level1 > level2) {
      return true;
    }
    String[] sarray1 = new String[level1];
    String[] sarray2 = new String[level2];
    //把分割后的字串，加入字符串数组
    for (int i = 0; i < level1; i++) {
      sarray1[i] = st1.nextToken();
      sarray2[i] = st2.nextToken();
    }
    //在循环中比较数组中的字串
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
		if (routeState.equals("前准")) {
			QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
			QueryCondition cond = new QueryCondition(LEFTID,
					QueryCondition.EQUAL, ifc.getBsoID());
			query.addCondition(cond);
			query.addAND();
			// 有可能零件未使用路线。
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
		if (routeState.equals("艺准")) {
			QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
			QueryCondition cond = new QueryCondition(LEFTID,
					QueryCondition.EQUAL, ifc.getBsoID());
			query.addCondition(cond);
			query.addAND();
			// 有可能零件未使用路线。
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
  * 采用单关联零部件
  */
  public ArrayList getAdoptNoticeRelateParts(String adoptNotice,String source,boolean flag)throws QMException{
	 
	  ArrayList list=new ArrayList();
	  if(source.trim().equals("技术中心")){
	
		  list=(ArrayList)getPublishRelatedParts(adoptNotice,flag);
	  }else{
		  list=(ArrayList)findJFCYPart(adoptNotice);
	  }
	  return list;
  }
//  /**
//   * 技术中心采用单关联零部件
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
   * 解放设计采用单关联零部件
   */
  public  Collection findJFCYPart(String adoptNotice)throws QMException{
	  PersistService persistService = (PersistService) EJBServiceHelper
		.getPersistService();
	    Collection CYPart= new ArrayList();
	    QMQuery query = new QMQuery("BomAdoptNotice");
        //采用单编号
        if(adoptNotice.length()>0){
                query.addAND();
                query.addCondition( new QueryCondition("adoptnoticenumber", QueryCondition.EQUAL, adoptNotice));      
       
        Collection col = (Collection)persistService.findValueInfo(query); 
        Iterator iter = col.iterator();
        while (iter.hasNext()) {
        	BomAdoptNoticeInfo bomInfo = (BomAdoptNoticeInfo) iter.next();
        	BomNoticeServiceEJB service =new BomNoticeServiceEJB();
        		if (bomInfo.getPublishType().equals("子采用通知单")) {
				Collection col1 =service. getPartsFromBomSubAdoptNotice(bomInfo.getBsoID());
	        	Iterator iter1=col1.iterator();
	        	while(iter1.hasNext()){
	        		BomAdoptNoticePartLinkInfo partLinkInfo = (BomAdoptNoticePartLinkInfo) iter1
					.next();
//	        		CCBegin SS18
	        		if(partLinkInfo.getAdoptBs().equals("采用")){
		        		QMPartInfo part = (QMPartInfo) persistService
						.refreshInfo(partLinkInfo.getPartID());
		        		CYPart.add(part);
	        		}
//	        		CCEnd SS18 
	        	}
			} 
	        	else if (bomInfo.getPublishType().equals("采用通知单")) {
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
			        		if(partLinkInfo.getAdoptBs().equals("采用")){
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
	  if(source.trim().equals("技术中心")){
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
        //采用单编号
        if(change.length()>0){
       
                query.addAND();
                query.addCondition( new QueryCondition("adoptnoticenumber", QueryCondition.EQUAL, change));      
    
        Collection col = (Collection)persistService.findValueInfo(query); 
        Iterator iter = col.iterator();
        while (iter.hasNext()) {
        	BomChangeNoticeInfo bomInfo = (BomChangeNoticeInfo) iter.next();
        	BomNoticeServiceEJB service =new BomNoticeServiceEJB();
        	if (bomInfo.getPublishType().equals("子变更通知单")) {
				Collection col1 =service. getPartsFromBomSubChangeNotice(bomInfo.getBsoID());
	        	Iterator iter1=col1.iterator();
	        	while(iter1.hasNext()){
	        		BomChangeNoticePartLinkInfo partLinkInfo = (BomChangeNoticePartLinkInfo) iter1
					.next();
//	        		CCBegin SS18
	        		if(partLinkInfo.getAdoptBs().equals("采用")){
	        			//CCBegin SS26
						if(!(partLinkInfo.getBz1().equals("结构变更")))
						{
                        //CCEnd SS26
		        		CYPart.add(partLinkInfo);
		        		//CCBegin SS26
						}
						//CCEnd SS26
	        		}
//	        		CCEnd SS18 
	        		
	        	}
			} 	else if (bomInfo.getPublishType().equals("变更通知单")) {
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
			        		if(partLinkInfo.getAdoptBs().equals("采用")){
			        			//CCBegin SS26
								if(!(partLinkInfo.getBz1().equals("结构变更")))
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
					if(links.getAdoptBs().equals("采用")){
						if(links.getLinkPart()!=null&&!links.getLinkPart().equals("")){//有替换件
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
							list[7]="采用";
							BGPart.add(list);
						}

					}else if(links.getAdoptBs().equals("不采用")){
						noAdopMap.put(links.getPartID(), links);
					}
				}
				//处理通过替换为产生的数据
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
								list[7]="变更";
								BGPart.add(list);
							}
							
							//移除这个被替换的不采用
							noAdopMap.remove(key);
						}
						
					}
				}
				
				//处理不采用剩余数据（只有不采用）
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
					list[7]="取消";
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
   * 通过masterid获取最新版本的零部件。
   * @param masterID masterid
   * @return QMPartInfo 最新版本的零部件(QMPart);
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
   * 根据输入的零部件主信息BsoID，和零部件配置规范,
   * 返回符合零部件配置规范的，受该QMPartMasterIfc管理的所有零部件的集合。
   * @param partMasterID 零部件主信息BsoID。
   * @return Vector 过滤出来的零部件值对象的集合，如果没有合格的零部件返回new Vector()。
   * @exception QMException
   */
  public static Vector  filterIterations(String partMasterID)
      throws QMException
  {
    //第一步:恢复QMPartMasterIfc
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(partMasterID);
    //第二步：零部件配置规范值对象：
    PartConfigSpecIfc configSpecIfc = PartServiceRequest.findPartConfigSpecIfc();
    //第三步：根据两个参数调用StandardPartService中的方法filterdIterationsOf方法
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
							if (str.indexOf("总") >=0|| str.indexOf("饰")>=0
									|| str.indexOf("架(漆)")>=0
									|| str.indexOf("架(装)")>=0
									|| str.indexOf("架(纵)")>=0
									|| str.indexOf("架(钻)")>=0
									|| str.indexOf("薄") >=0|| str.indexOf("焊")>=0
									|| str.indexOf("涂")>=0 || str.indexOf("厚")>=0
									|| str.indexOf("厚(焊)")>=0
									|| str.indexOf("协(抛)")>=0) {
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
     * 上载数据文件内容
     * @param priInfo TechnicsRouteListIfc 内容容器
     * @param arrayList ArrayList 内容项
     * @return Collection 持久化的内容项
     * 里获得ApplicationDataInfo对象
     * @author liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
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
               //从arrayList
                //里获得ApplicationDataInfo对象
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
     * 存储文件流,将字节数组写入到指定ID的StreamData中
     * @param arrayList ArrayList 流ID和文件内容流的集合
     * @return Collection
     * @author liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
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
     * 存储工艺卡单附件信息
     * @param arrayList ArrayList 流ID和文件内容流的集合
     * @return Collection
     * @author liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
     */
    private void createAssisFile(TechnicsRouteListIfc list,ArrayList
  		  arrayList) throws QMException {
  	  //处理附件信息
  	    
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
  	        //获得文件流
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
     * 存储工艺卡单附件信息
     * @param arrayList ArrayList 流ID和文件内容流的集合
     * @return Collection
     * @author liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
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
     * 刷新ApplicationDataInfo值对象
     * @param col Collection
     * @throws ResourceException
     * @return Vector
     * @author liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
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
                                                  refreshInfo(appDataID); //刷新值对象
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
     * 删除内容容器中指定的数据项
     * @param priInfo ResourceManagedInfo 内容容器
     * @param appDataInfo Vector 内容项集合
     * @author liunan 2009-10-12 根据解放要求，为工艺添加附件的关联保存。
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
  	String str = "无";
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
            
            //System.out.println("获取零部件已有路线SQL："+query1.getDebugSQL());
            
           Collection c=(Collection) ps.findValueInfo(query1, false);
           Iterator i=c.iterator();
           if(i.hasNext())
           {
        	   ListRoutePartLinkInfo link=(ListRoutePartLinkInfo) i.next();
        	   TechnicsRouteIfc routeInfo = (TechnicsRouteIfc) ps.refreshInfo(link.getRouteID());
             str=routeInfo.getModefyIdenty().getCodeContent();
        	   //System.out.println("----得到的路线状态是="+str);
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
      	QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);//表TechnicsRouteBranch
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
	 * 获取项目内容并保存
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
		System.out.println("搜索集合大小=========" + collection.size());
		Iterator ite = collection.iterator();
		ProcessData processdata = null;
		// 在艺准活动中创建生产准备项目
		String xprocess = RemoteProperty.getProperty(
				"technicsRouteForProducePreparative", "批准艺准");
		// 获得会签意见名称
		String huiqianstr = RemoteProperty.getProperty(
				"technicsRouteSignatureStr", "会签意见");
		// 获得会签意见名称
		String huiqian = RemoteProperty.getProperty("technicsRouteSignature",
				"路线会签");
		System.out.println("xprocess====================" + xprocess);
		System.out.println("huiqianstr====================" + huiqianstr);
		System.out.println("huiqian====================" + huiqian);
		// 会签时间
		String huiqiantime = "";
		// 会签内容
		String hqstr = "";
		//转换格式后会签时间
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
			System.out.println("活动名称wfaname=========="+wfaname);
			// 查找会签时间及内容
			if (wfaname.equalsIgnoreCase(huiqian)) {
				// 获得流中的变量集合
				ProcessData processdata1 = wfa.getContext();
				// 获取会签内容
				hqstr = (String) processdata1.getValue(huiqianstr);
				// 得到会签时间
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
				
				xmdh = (String) processdata1.getValue("项目代号");
				xmmc = (String) processdata1.getValue("项目名称");
				xmlb = (String) processdata1.getValue("项目类别");
				xmsjgl = (String) processdata1.getValue("项目设计纲领");
				
				//CCBegin SS48
				szPlantFinishDate= (String) processdata1.getValue("生产准备要求完成时间");
				//CCEnd SS48
				
				System.out.println("项目代号=========" + xmdh);
				System.out.println("项目名称=========" + xmmc);
				System.out.println("项目类别=========" + xmlb);
				System.out.println("项目设计纲领=========" + xmsjgl);
				
				//CCBegin SS42
				ProducePreparativeInfo ppInfo =null;
				
				Vector ppInfoVec=(Vector)ppService.seachPP("sCode", xmdh);
				
				if(ppInfoVec!=null&&ppInfoVec.size()!=0)
					
					ppInfo =(ProducePreparativeInfo)ppInfoVec.get(0);
				
				else
					
					ppInfo=new ProducePreparativeInfo();
				
				//CCEnd SS42
				
				// 设置资料夹和域
				ppInfo.setLocation("\\Root\\生产准备");
				fservice.assignFolder((FolderEntryIfc) ppInfo, "\\Root\\生产准备");
				ppInfo.setDomain(dService.getDomainID("生产准备"));
				// 设置生命周期
				LifeCycleTemplateInfo lifecycle = (LifeCycleTemplateInfo) lifeCycle
						.getLifeCycleTemplate("缺省");
				ppInfo.setLifeCycleTemplate(lifecycle.getBsoID());
				ppInfo.setProjectNum(xmdh);
				ppInfo.setProjectName(xmmc);
				ppInfo.setProjectType(xmlb);
				ppInfo.setProjectDesignProgramme(xmsjgl);
				//CCBegin SS50 设置自动生成属性
				ppInfo.setAttr1("Y");
				
				//CCEnd SS50
				ppInfo = (ProducePreparativeInfo) ps.saveValueInfo(ppInfo);
				System.out.println("项目ID=================" + ppInfo.getBsoID());
				// 项目id
				String projectid = ppInfo.getBsoID();
				
				//CCBegin SS48
				QMPartIfc  linkPartifc=null;
				//CCEnd SS48
				
				// 获得关联信息
				Collection coll = getLinkByRouteList(technicsifc);
				for (Iterator iter = coll.iterator(); iter.hasNext();) {
					ListRoutePartLinkIfc link = (ListRoutePartLinkInfo) iter
							.next();
					String count = String.valueOf(link.getCount());// 数量
					
					//CCBegin SS52
					String partID = link.getPartBranchID();//零件ID
					
					QMPartIfc partifc = (QMPartIfc) ps.refreshInfo(partID);// 零部件值对象
					
//					String partmasterid = link.getRightBsoID();// 零件masterid
//					
//					QMPartMasterIfc partmasterifc = (QMPartMasterIfc) ps
//							.refreshInfo(partmasterid);// 零件master值对象
//					
//					QMPartIfc partifc = (QMPartIfc) getLatestVesion(partmasterifc);// 零部件值对象
					
					//CCEnd SS52
					
					//CCBegin SS48
					if(linkPartifc==null)
					    linkPartifc=partifc;
					//CCEnd SS48
					
					// 获得指定零件的制造路线和装配路线,String[] 第一个元素为制造路线,第二个元素为装配路线
					String[] routestr = getRouteString(partifc);
					//CCBegin SS50  按路线过滤零件
					
					String makeRoute=routestr[0];
					String AssembleRoute=routestr[1];
					
					if(makeRoute.contains("用")&&(AssembleRoute.equals("")||AssembleRoute==null)){
						
						continue;
					}
					
					//CCBegin SS52
					partifc.setLifeCycleState(LifeCycleState.toLifeCycleState("PREPARING"));
					
					//CCEnd SS52
					
					//CCEnd SS50
					ProjectPartIfc pPartifc = new ProjectPartInfo();
					pPartifc.setLeftBsoID(projectid);// 保存项目ID
					
					//CCBegin SS42
					pPartifc.setRightBsoID(partifc.getBsoID());// 保存零部件ID
					//CCEnd SS42
					
					//CCBegin SS52
					
					//CCBegin SS53
					//pPartifc.setPartNum(linkPartifc.getPartNumber());// 保存零部件编号
					//pPartifc.setPartName(linkPartifc.getPartName());// 保存零部件名称
					
					
					pPartifc.setPartNum(partifc.getPartNumber());// 保存零部件编号
					pPartifc.setPartName(partifc.getPartName());// 保存零部件名称
					
					//CCEnd SS53
					
					//pPartifc.setPartNum(partmasterifc.getPartNumber());// 保存零部件编号
					//pPartifc.setPartName(partmasterifc.getPartName());// 保存零部件名称
					
					//CCEnd SS52
					
					pPartifc.setCount(count);// 保存数量
					pPartifc.setTechnisNum(technicsifc.getRouteListNumber());// 保存艺准号
					pPartifc.setContractID(hqTime);// 保存会签时间
					pPartifc.setHqContent(hqstr);// 保存会签内容
					
					//CCBegin SS42
					pPartifc.setHqPeople(huiqianRen);// 保存会签者
					
					//CCEnd SS42
					
					pPartifc.setMakeRoute(routestr[0]);//保存制造路线
					pPartifc.setAssembleRoute(routestr[1]);//保存装配路线
					
					
					//CCBegin SS41
					pPartifc.setPartVerstionID(partifc.getVersionID());//零件版本
					//CCBegin SS42
					//设置采用变更
					pPartifc=setCaiYongAndBianGeng(pPartifc,partifc);
					
					//CCEnd SS42
					//CCBegin SS50
					//项目类别
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
					
					//CCBegin SS50 设置自动生成属性
					 pPartifc.setAtt5("Y");
				        //CCEnd SS50
				        
					pPartifc = (ProjectPartInfo) ps.saveValueInfo(pPartifc);// 保存项目零部件关联
					
					
					//CCBegin SS40
					//自动带出产品属性信息
					ProductAttributesInfo paInfo=findPartAttr(partifc);
					paInfo.setLeftBsoID(projectid);
					paInfo.setRightBsoID(pPartifc.getBsoID());
					
					ps.saveValueInfo(paInfo);
					//CCEnd SS40
					
					//CCBegin SS43
					
					//保存开发状态零件信息
					
					ProductDevelopmentStatusInfo pdsInfo=new ProductDevelopmentStatusInfo();
					pdsInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					pdsInfo.setRightBsoID(pPartifc.getBsoID());
					pdsInfo.setAttr1(partifc.getPartNumber());
					pdsInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(pdsInfo);
					
	                //保存工艺设计状态零件信息
					
					ProcessDesignPhaseInfo pdpInfo=new ProcessDesignPhaseInfo();
					pdpInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					pdpInfo.setRightBsoID(pPartifc.getBsoID());
					pdpInfo.setAttr1(partifc.getPartNumber());
					pdpInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(pdpInfo);
					
	             //保存工艺准备阶段零件信息
					
					ProcessPreparationStageInfo ppsInfo=new ProcessPreparationStageInfo();
					ppsInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					ppsInfo.setRightBsoID(pPartifc.getBsoID());
					ppsInfo.setAttr1(partifc.getPartNumber());
					ppsInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(ppsInfo);
					
	           //保存工艺调试&工装样件零件信息
					
					ProcessDebuggingInfo pdgInfo=new ProcessDebuggingInfo();
					pdgInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					pdgInfo.setRightBsoID(pPartifc.getBsoID());
					pdgInfo.setAttr1(partifc.getPartNumber());
					pdgInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(pdgInfo);
					
	             //保存试装零件信息
					
					TryInstallInfo tisInfo=new TryInstallInfo();
					tisInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					tisInfo.setRightBsoID(pPartifc.getBsoID());
					tisInfo.setAttr1(partifc.getPartNumber());
					tisInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(tisInfo);
					
	            //保存试装零件信息
					
					TryInstallInfo tislInfo=new TryInstallInfo();
					tislInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					tislInfo.setRightBsoID(pPartifc.getBsoID());
					tislInfo.setAttr1(partifc.getPartNumber());
					tislInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(tislInfo);
					
					
	             //保存样件认可零件信息
					
					PrototypeInfo ptyInfo=new PrototypeInfo();
					ptyInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					ptyInfo.setRightBsoID(pPartifc.getBsoID());
					ptyInfo.setAttr1(partifc.getPartNumber());
					ptyInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(ptyInfo);
					
					
	              //保存生产准备状态检查零件信息
					
					CheckReadyStateInfo crdsInfo=new CheckReadyStateInfo();
					crdsInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					crdsInfo.setRightBsoID(pPartifc.getBsoID());
					crdsInfo.setAttr1(partifc.getPartNumber());
					crdsInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(crdsInfo);
					
	             //保存生产准备报毕零件信息
					
					CompleteReportInfo crpInfo=new CompleteReportInfo();
					crpInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					crpInfo.setRightBsoID(pPartifc.getBsoID());
					crpInfo.setAttr1(partifc.getPartNumber());
					crpInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(crpInfo);
					
					
	             //保存产能跟踪零件信息
					
					CapacityTrackingInfo ctkInfo=new CapacityTrackingInfo();
					ctkInfo.setLeftBsoID(pPartifc.getLeftBsoID());
					ctkInfo.setRightBsoID(pPartifc.getBsoID());
					ctkInfo.setAttr1(partifc.getPartNumber());
					ctkInfo.setAttr2(partifc.getPartName());
					ps.saveValueInfo(ctkInfo);
					
					//CCEnd SS43
				}
				//CCBegin SS48
				
				//创建艺准信息
				createTechnicsNoticeDate(linkPartifc,ppInfo,technicsifc,huiqianRen,hqstr,szPlantFinishDate);
				//CCEnd SS48
			}

		}
	}


	//CCBegin SS48
	/**
	 * 创建艺准信息
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
		  //采用变更单号
		  String faLingNum="";
			 if(al!=null)
				 faLingNum= (String)al.get(0);
			 
			//设置采用变更单号
		  ptnInfo.setAttry1(faLingNum);
		  
		  ptnInfo.setProjectName(ppInfo.getProjectName());
		  
		  ArrayList al2=(ArrayList)ibaMap.get("publishdate");
		  //收到日期
		  String shouDdaoData="";
			 //CCBegin SS50 设置时间格式
			 if(al!=null){
				 shouDdaoData= (String)al2.get(0);
				 
				 shouDdaoData=StringPattern(shouDdaoData,"yyyy年MM月dd日","yyyy-MM-dd");
			 }
			 //CCEnd SS50
		  ptnInfo.setReceiveDate(shouDdaoData);
		  
		  String userID=tRLInfo.getCreator();
		  UserInfo crName= (UserInfo)ps.refreshInfo(userID);
		  ptnInfo.setTechnicsNoticeCreatePerson(crName.getUsersDesc());
		  ptnInfo.setTechnicsNoticeNum(tRLInfo.getRouteListNumber());
		    //CCBegin SS50 设置时间格式
		  String tNCreateTime=StringPattern(tRLInfo.getCreateTime().toString(),"yyyy-MM-dd HH:mm:ss.S","yyyy-MM-dd HH:mm");
		  
		  ptnInfo.setTechnicsNoticeCreateDate(tNCreateTime);
		//CCEnd SS50
		  
		  SimpleDateFormat sdf=new SimpleDateFormat();
               //CCBegin SS50 设置时间格式
                  sdf.applyPattern("yyyy-MM-dd");   
               //CCEnd SS50              	
          String outData = sdf.format(new Date());
		  
		  ptnInfo.setTechnicsNoticeOutDate(outData);
		  ptnInfo.setProjectHQSuggestion(huiqianStr);
		  ptnInfo.setProjectType(ppInfo.getProjectType());
		  ptnInfo.setProductionPerson(huiqianR);
		  ptnInfo.setProductionPlantFinishTime(szPlantFinishDate);
		    //CCBegin SS50  设置自动生成属性
		  ptnInfo.setAttry3("Y");
		  //CCEnd SS50
		  ps.saveValueInfo(ptnInfo);
	}
	
	//CCEnd SS48
	
	
	//CCBegin SS50
	
	/**
	 * 日期格式化
	 * @param date
	 * @param oldPattern
	 * @param newPattern
	 * @return
	 */
	 private   String StringPattern(String date, String oldPattern, String newPattern) {   
	        if (date == null || oldPattern == null || newPattern == null)   
	            return "";   
	        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern) ;        // 实例化模板对象    
	        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern) ;        // 实例化模板对象    
	        Date d = null ;    
	        try{    
	            d = sdf1.parse(date) ;   // 将给定的字符串中的日期提取出来    
	        }catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理    
	            e.printStackTrace() ;       // 打印异常信息    
	        }    
	        System.out.println(sdf2.format(d));
	        return sdf2.format(d);  
	    } 
	
	//CCEnd SS50
	

	//CCBegin SS42
	/**
	 * 设置采用变更单号
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
	 * 通过路线表查找关联表
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
	 * 1:在艺准批准后，将艺准关联的电控数据自动发布到EOL系统的目标文件夹
	 * 2:在艺准批准后，将艺准关联的后桥总成的分类属性自动生成一个文本文件，并自动发布到EOL系统的目标文件夹 
	 * 3:发艺准目前有这几种数据：a、3601611XXXX  是发动机控制单元生产文件；b、3611611XXXX  是变速器控制单元生产文件；
	 * c、3601651XXXX是发动机临时控制单元生产文件；d、3610611XXXX 是整车控制单元生产文件；e、3610612XXXX 是整车的基础数据；
	 */
	public void publishDataToEOL(BaseValueIfc primaryBusinessObject) throws QMException
	{
		System.out.println("come in publishDataToEOL:"+primaryBusinessObject);
		try
		{
			if(primaryBusinessObject instanceof TechnicsRouteListIfc)
			{
				TechnicsRouteListIfc route = (TechnicsRouteListIfc)primaryBusinessObject;
				//获取艺准关联零部件
				PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
				StandardPartService spService = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
				ContentService contentService = (ContentService)EJBServiceHelper.getService("ContentService");
				String eolPublishPath = RemoteProperty.getProperty("eolPublishPath") + route.getRouteListNumber() + File.separator;
				
				QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
				query.addCondition(new QueryCondition(LEFTID, QueryCondition.EQUAL, route.getBsoID()));
				query.addAND();
				//有可能零件未使用路线。
				query.addCondition(new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE));
				Collection coll = pservice.findValueInfo(query);
				ArrayList partlist = new ArrayList();
				//获取零部件关联描述文档，判断是否名称含有“电控单元数据”
				for (Iterator iter = coll.iterator(); iter.hasNext();)
				{
					ListRoutePartLinkIfc link = (ListRoutePartLinkInfo) iter.next();
					if(!partlist.contains(link.getRightBsoID()))
					{
						//获取最新版零部件
						QMPartMasterIfc masterInfo = (QMPartMasterIfc) pservice.refreshInfo(link.getRightBsoID());
						QMPartIfc part = getLastedPartByConfig(masterInfo);
						String partnumber = part.getPartNumber();
						//System.out.println("partnumber=="+partnumber+"  id=="+part);
						//如果编号以2400开头，则获取速比和后轮距
						//CCBegin SS49
						//if(partnumber.startsWith("2400"))
						//2400010、2500010获取速比，3106010获取轮胎滚动半径
						if(partnumber.startsWith("2400010")||partnumber.startsWith("2500010")||partnumber.startsWith("3106010"))
						//CCEnd SS49
						{
							File f = new File(eolPublishPath);
							if(!f.exists())
							{
								f.mkdir();
							}
							String subi = getIBAValue((IBAHolderIfc)part, "速比");
							//String houlunju = getIBAValue((IBAHolderIfc)part, "后轮距");
							//String zhongxinju = getIBAValue((IBAHolderIfc)part, "钢板弹簧中心距");
							//String gundongbanjing = getIBAValue((IBAHolderIfc)part, "车轮滚动半径");
							String gundongbanjing = getIBAValue((IBAHolderIfc)part, "轮胎滚动半径");//2017-12-25
							//CCBegin SS51
							String maichongshu = getIBAValue((IBAHolderIfc)part, "传感器每转脉冲数");
							if(maichongshu==null)
							{
								maichongshu = "";
							}
							String changganqisubi = getIBAValue((IBAHolderIfc)part, "传动轴与车速传感器速比");
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
							//改用发布中间表方式。
							/*if(!subi.equals("")||!gundongbanjing.equals(""))
							{
								StringBuffer buffer = new StringBuffer();
								buffer.append(part.getPartNumber()+"\n");
								buffer.append(subi+"\n");
								buffer.append(gundongbanjing);
								//写文件
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
								//数据库中没有该值，则插入
								sendEolDataByJDBC(partnumber,value);
							}
						}
						
						//a、3601611XXXX  b、3611611XXXX  c、3601651XXXX d、3610611XXXX e、3610612XXXX
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
						
						System.out.println("零件"+partnumber+"即将获取电控文档");
						//该零部件描述文档的值对象集合
            Vector descriDocs = spService.getDescribedByDocs(part,true);
            if(descriDocs!=null && descriDocs.size()>0)
            {
              for(int i=0;i<descriDocs.size();i++)
              {
              	Object obj = descriDocs.elementAt(i);
              	if(obj instanceof DocIfc)
              	{
              		DocIfc docIfc = (DocIfc)obj;
              		if(docIfc.getDocName().indexOf("电控单元数据")!=-1||docIfc.getLocation().indexOf("电控文档")!=-1)
              		{
              			//获取文档附件
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
              						//下载文档附件
              						downloadPDF(eolPublishPath + appDataInfo.getFileName(), appDataInfo);
              					}
              				}
              			}
              		}
              		else
              		{
              			System.out.println("文档"+docIfc.getDocNum()+"不是电控文档");
              		}
              	}
              }
            }
            else
            {
            	System.out.println("零件"+partnumber+"没有得到电控文档");
            }
						partlist.add(link.getRightBsoID());
					}
				}
				//发布文件到远程共享文件夹
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
					//插入新纪录。
					String insDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
					String insertSql = "insert into PartProperty (PartNo,PartProperty,DateTime) VALUES ('" + num + "','"+ value + "',getdate())";
					System.out.println("insertSql=================" + insertSql);
					stmt.execute(insertSql);
				}
				else if (countList == 1)
				{
					//有一样的记录，不需要插入。
					System.out.println("数据"+num+"->"+value+"已存在。");
				}
				else
				{
					System.out.println("数据有问题，不应该存在2条零件号和值一样的记录。");
				}
			}
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 获取EOL数据库连接
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
						//得到属性定义的单位
						unitStr = definition1.getDisplayUnitString(measurementSystem);
						//得到计量单位中的显示单位
						if (unitStr == null)
						{
							unitStr = quantityofmeasuredefaultview.getDisplayUnitString(measurementSystem);
						}
						//得到计量单位中的量纲
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
					//属性值和单位
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
	 * 下载文件到指定目录。
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
				//获取流ID
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
   * 通过ftp方式上传文件文件夹
   * @param file 上传的文件夹
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
      		System.out.println("FTP目标服务器无法连接！");
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
  		System.out.println("传输文件："+f.getName()+" 时出错！");
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
      System.out.println("进入TechnicsRouteService: getSubPartRoute 参数：" + productInfo.getBsoID());
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