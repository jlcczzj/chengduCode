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
 *CR7 2010/06/29 郭晓亮    参见: 域:product;TD号2275
 *CR8 2011/12/15 徐春英   原因：修改检入、检出、撤销检出、修订的方式
 *CR9 2011/12/20 吕航  原因：统一重命名操作
 *CR10 2011/12/21 吕 航  原因：增加批量添加方法
 *CR11 2011/1222  吕航  原因：通过零件ID查找是否存在有效的路线
 *CR12 2011/12/23 吕航 原因：增加通过从采用变更添加零件
 *CR13 2011/12/26 吕航 原因：增加添加零件新功能（包含路线串搜索 结构搜索等）
 *CR14 2012/01/04 徐春英   原因：增加保存艺准零件关联及其路线信息的方法
 *CR15 2012/01/06 徐春英   原因：增加发布路线功能
 *CR16 2012/01/06 徐春英  原因：增加查看父件功能
 *CR17 2012/01/11 吕航 原因：增加检入路线表时对零件是否有路线的判断
 *CR18 2012/01/11 吕航 原因：增加复制自查询功能
 *CR19 2012/01/18 xucy  原因：增加快捷路线功能
 *CR20 2012/03/28 吕航 原因参见TD5903
 *CR21 2012/03/28 吕航 原因参见TD5940
 *CR22 2012/03/29 吕航 原因参见TD5958
 *CR23 2012/03/29 吕航原因参见TD5975
 *CR24 2012/03/29 吕航原因参见TD5969
 *CR25 2012/04/01 吕航原因参见TD6012
 *CR26 2012/04/05 吕航原因参见TD6009
 *CR27 2012/04/06 吕航原因参见TD5938
 *CR28 2012/04/06 吕航原因参见TD5963
 *CR29 2012/04/09 吕航原因参见TD6019
 *CR30 2012/04/11 吕航原因参见TD6030
 *SS1 添加瘦客户界面的报表导出 刘家坤 2013-03-01
 *SS2 路线按更改通知书添加零件方法修改 刘家坤 2013-03-01
 *SS3 变速箱工艺路线搜索零件时显示零件多种信息 pante 2013-11-02
 *SS4 变速箱工艺路线重复添加同一零件不同版本 pante 2013-11-16
 *SS5 编辑工艺路线界面零部件表中显示的版本值修改为发布源版本  马晓彤 2013-12-16
 *SS6 搜索解放艺准 liunan 2013-12-23
 *SS7 根据解放艺准和路线搜素零部件 liunan 2013-12-23
 *SS8 修改报表中有零件显示不出来的问题 pante 2013-12-27
 *SS9 轴齿中心增加采购标识 liuyang 2014-1-13
 *SS10 把搜索解放艺准的条件“开始时间”和“结束时间”修改为“创建时间”和“更新时间” liuyang 2014-2-24
 *SS11 储存零部件版本 liuyang 2014-2-25
 *SS12 轴齿中心历史版本设为空 liuyang 2014-2-25
 *SS13 轴齿中心二级路线编号流水号 Liuyang 2014-2-27
 *SS14 修改变速箱二级路线中有零件显示不出来的问题，因为conslistpartlink表中rightbsoid里不一定都是qmpartmasterid了，新的是qmpartid了。 liunan 2014-1-23
 *SS15 按解放艺准搜索支持模糊搜索 liunan 2014-2-17
 *SS16 轴齿中心二级路线编号流水号在客户端进行处理 Liuyang 2014-4-8
 *SS17 二级路线按零部件号排序 Liuyang 2014-4-18
 *SS18 获取零部件结构，传递零部件集合和返回零部件集合都要改成QMPartIfc，而不再用 QMPartMasterIfc 了，因此客户端添加零部件都已改成记录QMPartIfc。 liunan 2014-3-17
 *SS19 添加零部件，按路线搜索时返回QMPartMasterIfc改为QMPartIfc。 liunan 2014-5-13
 *SS20 计算数量QMPartMasterIfc改为QMPartIfc。 liunan 2014-5-21
 *SS21 根据路线单位搜索工艺路线，QMPartMasterIfc改为QMPartIfc liunan 2014-6-19
 *SS22 按轴齿用户要求,艺毕保存时,将关联的零件已有的路线信息保存上 pante 2014-06-26
 *SS23 修改轴齿按路线单位搜索无结果问题 pante 2014-09-04
 *SS24 轴齿零件查看二级路线中看不到艺毕路线 pante 2014-10-09
 *SS25 长特路线关联零部件增加供应商 liuyang 2014-8-20
 *SS26 长特搜索长特一级路线  liuyang 2014-8-25
 *SS27 长特二级路线报表 liuyang 2014-9-3
 *SS28 长特零件根据一级、二级路线设置成生产准备状态 文柳 2014-11-19
 *SS29 A005-2014-3019变速箱，修改零部件二级路线表，显示该件所有版本的零部件路线。 liunan 2014-12-24
 *SS30 变速箱二级工艺路线批准后设置状态时，支持QMPartMasterIfc和QMPartIfc两种情况。 liunan 2015-2-6
 *SS31 变速箱二级工艺路线批准后设置状态时出错，历史是取branchid，获取最新版设置状态，但现在没有branch的记录，改为取rightbsoid设置状态。 liunan 2015-2-25
 *SS32 长特一级路线修改零件状态，需要排除解放生产准备零件  文柳  2015-3-9
 *SS33 补充SS29，显示该件所有小版本的路线，正常应该是大版本，但有些历史件编辑路线的件不是在大版本的最新小版本上。 liunan 2015-3-24
 *SS34 轴齿更新采购标识不好使，不需要判断条件来设置该属性。 liunan 2015-4-29
 *SS35 长特一级路线报表，不按照零件编号排序  文柳  2015-5-29
 *SS36 零部件列表显示顺序问题 liuzhicheng 2015-6-17 
 *SS37 A005-2014-2957 变速箱新需求：添加零部件，带上最新版路线，先去二级路线，没有的话取解放一级路线。 liunan 2015-7-16
 *SS38 A005-2015-3112 编辑工艺路线 广义部件 时无法编辑，无法保存 liunan 2015-11-12
 *SS39 A005-2015-3110  去掉alterStatus的判断，实际保存的内容有误 liunan 2015-11-13
 *SS40 成都工艺路线整合，自动编号 liunan 2016-8-3
 *SS41 成都工艺路线整合，典型路线报表 liunan 2016-8-15
 *SS42 成都添加通过艺准添加零件 guoxiaoliang 2016-07-18
 *SS43 成都工序添加获得子件 guoxiaoliang 2016-7-28
 *SS44 轴齿用户访问变速箱用户时候因为没有权限，再进行refresh时候出错 刘家坤 2016-09-01
 *SS45 A032-2016-0115 成都按路线单位搜索的不好使。 liunan 2016-9-12
 *SS46 A032-2016-0116 成都搜索零部件典型路线不好使。 liunan 2016-9-12
 *SS47 修改成都批量搜索零部件为精确查找 liuyuzhu 2016-11-10
 *SS48 查找相同名称路线表 liuyuzhu 2016-10-12
 *SS49 成都典型路线通过零部件编号进行判断。 liunan 2016-10-14
 *SS50 成都增加颜色件标识colorFlag属性。 liunan 2016-10-25
 *SS51 二级路线在获取解放艺准时候，不是获取解放编辑路线的版本，而是最新小版本 刘家坤 2016-10-28
 *SS52 查看路线时，对于没有权限的二级路线过滤掉，显示有权看的。 liunan 2016-12-7
 *SS53 调整SS5，先取发布源版本，再取部件版本。没有则为空字符串。 liunan 2017-4-11
 *SS54 成都工艺路线特殊处理件标识，为成都的零部件进行版本转换 徐德政 2018-01-11
 *SS55 成都查看二级路线报表，要求只看当前版本零部件的二级路线。 liunan 2018-1-17
 *SS56 成都工艺路线显示发布源版本 徐德政 2018-01-21
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

// * 参考文档：
// * Phos-REQ-CAPP-BR02(工艺路线业务规则)V2.0.doc
// * PHOS-REQ-CAPP-SRS-002(制造业企业基础数据管理需求规格说明-工艺路线) V2.0.doc
// * "Phos-CAPP-UC301--Phos-CAPP-UC322"共19个用例。
// *  (问题零) 200605 zz 周茁修改 修改原因:查询零部件件关联的路线,应该是被采用的路线
// * (问题一)20060609 zz 周茁修改 修改原因:优化查看路线的关联零部件,修改为两表连查,减少中间对象的生成
// * (问题二)20060701 zz 周茁修改 修改原因:优化按单位搜索路线,将多次配置规范的过滤改为整体一次过滤.
// * (问题三)20060629 zz 周茁修改 修改原因:工艺路线生成报表操作速度慢,同时修改了生成报表的jsp
// * （问题四）2006 07 12  zz 周茁添加 性能优化 ，同时修改了ComcreateLinksAndRoutespareHandler
// * （问题五）2006 08 03  zz 周茁添加 性能优化 ，在工艺路线串增加了一个属性保存路线串字符串
// * 瘦客户端显示工艺路线串的时候可以直接取出次字符串显示
// * （问题六） 2006 08 04  zz 周茁修改 经过这个方法算法。增加了重复元素bug
// * （问题七） 2006 09 04 zz 周茁修改 修订后新版路线表里的关联零部件的路线状态原为采用的变为取消了
// * 根据需求应该复制前一版本的状态
// * (问题八)20061110 zz 周茁添加,为二级路线 "添加"按钮 添加根据所选单位过滤功能
// * (问题九)周茁添加 zz 添加集体调用方法，减少客户端调用次数
// * （问题十）增加工艺路线的重命名功能 周茁添加 zz 20061214

/**
 * 提供对工艺路线表的 创建，更新，删除，查看的服务
 * @author 赵立彬
 * @version 1.0
 */
public class TechnicsRouteServiceEJB extends BaseServiceImp
{ /////////////////有的多表联查可能不需要distinct.
    public final static boolean VERBOSE = Boolean.valueOf(RemoteProperty.getProperty("com.faw_qm.technics.consroute.verbose", "true")).booleanValue();

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

    //////////////////////////////////以下为测试方法/////////////////////////////////////
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

    //////////////////////////////////测试方法结束////////////////////////////////////
    /**
     * getServiceName
     * @return String
     */
    /**
     * 获得服务名
     * @return TechnicsRouteService
     */
    public String getServiceName()
    {
        return "TechnicsRouteService";
    }

    /**
     * 保存工艺路线列表。非空检查在值对象中已做。唯一性检查在数据库中设置。
     * @param routeListInfo TechnicsRouteListIfc 指定的路线表值对象 @
     * @return TechnicsRouteListIfc 工艺路线表的值对象
     * @see TechnicsRouteListInfo 指定的路线表值对象
     */
    public TechnicsRouteListIfc storeRouteList(TechnicsRouteListIfc routeListInfo)throws QMException
    {
        TechnicsRouteListIfc routeListInfo1 = null;
        if(PersistHelper.isPersistent(routeListInfo))
        {
            throw new QMException("routeService's storeRouteList : routeListInfo不应被持久化。");
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
//                if(routeListInfo.getRouteListState().equals("艺准")){
//                   num=sservice.buildSerialNum(routeListNumber, 3);
//                }
//                if(routeListInfo.getRouteListState().equals("艺试准")){
//                    num=sservice.buildSerialNum(routeListNumber, 3);
//                   }
//                if(routeListInfo.getRouteListState().equals("前准")){
//                    num=sservice.buildSerialNum(routeListNumber, 3);
//                   }
//                if(routeListInfo.getRouteListState().equals("临准")){
//                    num=sservice.buildSerialNum(routeListNumber, 3);
//                   }
//                if(routeListInfo.getRouteListState().equals("艺毕")){
//                    num=sservice.buildSerialNum(routeListNumber, 3);
//                   }
//                if(routeListInfo.getRouteListState().equals("艺废")){
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
            	  throw new QMException("自动编号提取的用于产品信息组装成的编号长度大于200");
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
                //判断唯一性。
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
                System.out.println(TIME + "开始保存路线表和产品的关联。");
                //此时routeListInfo已经被保存。建立路线表和产品的关联。此关联只能创建和删除。删除由持久化维护。
            }
            RouteListProductLinkInfo listProductInfo = new RouteListProductLinkInfo();
            //if(routeListInfo1.getMaster() == null || routeListInfo1.getMaster().getBsoID() == null)
            //throw new TechnicsRouteException("routeService's storeRouteList 路线表的master没有被建立。");
            listProductInfo.setRouteListMasterID(routeListInfo1.getMasterBsoID());
            if(routeListInfo1.getProductMasterID() == null)
            {
                throw new QMException("routeService's storeRouteList 产品的masterID没有被设置。");
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
     * 更新工艺路线表。只进行简单存储。
     * @param routeListInfo TechnicsRouteListIfc 路线表值对象 @
     * @return TechnicsRouteListIfc 工艺路线表值对象
     * @throws QMException 
     */
    private TechnicsRouteListIfc updateRouteList(TechnicsRouteListIfc routeListInfo) throws QMException
    {
        TechnicsRouteListIfc routeListInfo1 = null;
        if(!PersistHelper.isPersistent(routeListInfo))
        {
            throw new QMException("routeService's storeRouteList : routeListInfo没有被持久化。");
        }
        //更新时不能更改唯一属性，不需要做SQLException封装。
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        routeListInfo1 = (TechnicsRouteListIfc)pservice.updateValueInfo(routeListInfo);
        return routeListInfo1;
    }

    /**
     * 删除工艺路线表，整个大版本内的小版本全部删除。
     * @param routeListInfo TechnicsRouteListIfc 工艺路线表值对象 根据指定的值对象删除路线表 @
     * @throws QMException 
     * @see TechnicsRouteListInfo 工艺路线表值对象
     */
    public void deleteRouteList(TechnicsRouteListIfc routeListInfo) throws QMException
    {
        ///////////////有版本删除整个分支  2004.9.10 赵立彬
        ///////////////可以调用不发信号的删除,自己处理相关删除. 效率更高.
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        deleteRouteListListener(routeListInfo);
        pservice.deleteValueInfo(routeListInfo);
        /*
         * //找到routeListID对应的所有小版本。 VersionControlService vservice = (VersionControlService)EJBServiceHelper.getService(VERSION_SERVICE); Collection vec1 =
         * vservice.iteratiosaveRoutensOf(routeListInfo); if(VERBOSE) System.out.println(TIME+"deleteRouteListener 所有的小版本："+vec1); //删除在此版本路线表中建立的路线。 //利用监听删除路线。虽效率低，此处暂不处理。
         * /////////////？？？？？此时还有一个好处：可以恢复到删除前的状态。但此状态是否恢复。 //遍历删除。 PersistService pservice = (PersistService)EJBServiceHelper.getPersistService(); Iterator iter1 = vec1.iterator();
         * if(iter1.hasNext()) pservice.deleteValueInfo((BaseValueIfc)iter1.next());
         */
    }

    /**
     * 编辑零部件表,执行者在更新工艺路线表时，编辑要编制工艺路线的零部件表，可以添加零部件、删除零部件。 添加零部件时，可以根据路线表中的"用于产品"的零部件，按其最新结构中提供备选零部件表，用户可以从中 选择要添加到工艺路线表中的零部件。如果用户正在编辑的是二级路线表，则备选零部件表不仅应按用于产品
     * 的结构，还应按此产品所有零部件中包含本单位路线的一级路线进行进一步筛选，从而获得一份备选零部件表。
     * @param codeID String 路线表单位，根据单位名称的代码ID和路线级别获得可选择的零部件。
     * @param level String 路线级别显示名。例：一级路线或二级路线 如果当前编辑的工艺路线表是一级工艺路线表，则系统应列出产品结构中的所有零部件； 如果当前编辑的工艺路线表是二级工艺路线表，则系统应根据路线表的单位属性，列出产品结构中所有一级路线有对应单位的零部件，作为备选零部件。
     * @param productMasterID String 用于产品MasterID @
     * @return Collection 存放的是vector:<br> vector中包含刷新后的partMasterID <br> partMasterID是过滤后产品子件的master值对象。
     */
    public Collection getOptionalParts(String codeID, String level, String productMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getOptionalParts : level = " + level);
        }
        //如果是一级路线。
        Collection result = new Vector();
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMPartIfc productInfo = (QMPartIfc)getLatestVesion(productMasterID);
        if(level.equalsIgnoreCase(RouteListLevelType.FIRSTROUTE.getDisplay()))
        {
            //获得子件。
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
            //进行过滤，获得给定产品的子件。
            for(Iterator iter = coll.iterator();iter.hasNext();)
            {
                ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
                String partMasterID = listLinkInfo.getPartMasterID();
                //进行子件过滤。
                boolean flag = isChildPart(productInfo, partMasterID);
                if(flag)
                {
                    result.add(pservice.refreshInfo(partMasterID));
                }
            }
        }else
        {
            throw new QMException("传入路线等级不正确");
        }
        if(VERBOSE)
        {
            System.out.println(TIME + " RouteService getOptionalParts's result = " + result);
        }
        return result;
    }

    /**
     * 通过路线表编号获得路线表值对象
     * @param routeListNum 路线表编号
     * @return 路线表值对象
     * @author 郭晓亮
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
     * 对给定的零部件进行检查，检查这些零部件是否符合添加到二级路线的零部件表 如果当前编辑的工艺路线表是二级工艺路线表，则系统应根据路线表的单位属性， 列出产品结构中所有一级路线有对应单位的零部件，作为备选零部件。
     * @param codeID String 二级路线表的单位
     * @param productMasterID String 二级路线表用于的产品
     * @param subPartMasters Collection 需进行检查的零部件集合（要求元素为零部件主信息值对象） @
     * @return Object[] 数组中包含二个元素:<br>Object[0]:第一个元素是符合条件的零部件主信息集合;<br> Object[1]:第二个元素是不符合条件的零部件主信息集合
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
        //进行过滤，获得给定产品的子件。
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            String partMasterID = listLinkInfo.getPartMasterID();
            //进行子件过滤。
            boolean flag = isChildPart(productInfo, partMasterID);
            if(flag && !masteridVector.contains(partMasterID))
            {
                masteridVector.add(partMasterID);
            }
        } //至此，从数据库中找到了所有符合条件的零部件

        Vector v1 = new Vector(); //用于装载符合条件的零部件
        Vector v2 = new Vector(); //用于装载不符合条件的零部件
        //下面进行对比，
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
     * 获得指定零部件（主信息）的下一级零部件（主信息）的集合
     * @param partMasterInfo QMPartMasterIfc 指定零部件（主信息）值对象 @
     * @return Collection 存放的是HashMap:<br> key:partMasterBsoID <br>value:Master 下一级零部件（主信息：partInfo.getMaster()）的集合
     * @see QMPartMasterInfo
     */
    public Collection getSubPartMasters(QMPartMasterIfc partMasterInfo)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("进入TechnicsRouteService: getSubPartMasters 参数：" + partMasterInfo.getBsoID());
        }
        HashMap map = new HashMap();
        QMPartIfc productInfo = (QMPartIfc)getLatestVesion(partMasterInfo);
        if(VERBOSE)
        {
            System.out.println("产品最新版本：" + productInfo.getBsoID());
        }
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        Collection c = partService.getSubParts(productInfo);
        if(VERBOSE)
        {
            System.out.println("Part服务结束！！");
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
    //获得给定产品的所有子件。不包括自身。
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
    //判断给定partMasterID是否是产品的子件。
    private boolean isChildPart(QMPartIfc productInfo, String partMasterID)throws QMException
    {
        boolean flag = false;
        Collection result = getAllSubParts(productInfo);
        //遍历，比较。
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
     * 获得最新版本的值对象。
     * @param masterInfo MasterIfc Master值对象 @
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
     * 获得最新版本值对象的集合
     * @param c Collection master对象集合 @
     * @return Collection 存放obj[]数组：<br>obj[0]:最新版本值对象
     */
    //(问题三)20060629 周茁修改
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
     * 获得最新版本值对象的集合
     * @param c Collection master对象集合 @
     * @return HashMap key:零件编号 value：最新版本值对象
     */
    //   *add by guoxl on 20080307(查看一级路线表报表时，序号排序错误)
    //   *所以添加方法getLatestsVersion1
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
     *创建艺准零件关联和路线信息，参数分别为要保存的艺准零件关联集合，艺准对象，要保存的路线信息
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
     * 更新艺准零件关联和路线信息， 参数分别为要删除的零件信息集合，艺准对象、要创建和要更新的关联信息集合
     */
    public Object[] updateLinksAndRoutes(HashMap deleteCollection, TechnicsRouteListIfc routeListInfo1, ArrayList allLinkCol)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's saveListRoutePartLink......................... saveCollection = " + ", deleteCollection = " + deleteCollection);
        }
        //更新路线表
        TechnicsRouteListIfc routeListInfo = updateRouteList(routeListInfo1);
        if(VERBOSE)
        {
            if(routeListInfo1.getBsoID() == routeListInfo.getBsoID())
            {
                System.out.println("更新后所获得的对象和以前的对象的bsoid一样");
            }else
            {
                System.out.println("更新后所获得的对象和以前的对象的bsoid不一样");
            }
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        try
        {
            Object[] obj = new Object[2];
            //20111230 徐春英  add  更新关联及其路线信息
            ArrayList list = this.updataAllLinkAndRoute(routeListInfo, allLinkCol);
            obj[0] = routeListInfo;
            obj[1] = list;
            //遍历要删除的零件集合，做删除关联标记。
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
                //获得父件信息
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

                //升序
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
                    System.out.println(TIME + "routeSevice's saveListRoutePartLink 原有关联集合： coll = " + coll);
                    //因为有可能新添加零件。所以是2
                }
                if(coll.size() > 2)
                {
                    if(VERBOSE)
                    {
                        System.out.println(TIME + coll);
                    }
                    throw new QMException("saveListRoutePartLink's deleteCollection：不能多于两个关联，请重新选择零件。");
                }
                //做删除关联标记或删除关联。
                Iterator iter1 = coll.iterator();
                if(iter1.hasNext())
                {
                    ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter1.next();
                    if(listLinkInfo.getAlterStatus() == INHERIT)
                    {
                        //skybird这段代码完全有问题
                        //设置以前的版本为取消状态。
                        QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
                        QueryCondition cond2 = new QueryCondition("routeListMasterID", QueryCondition.EQUAL, routeListInfo.getMaster().getBsoID());
                        query1.addCondition(cond2);
                        query1.addAND();
                        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
                        query1.addCondition(cond3);
                        query1.addAND();
                        QueryCondition cond4 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
                        query1.addCondition(cond4);
                        //////////////////排除自己, 否则有可能造成时间戳问题 2004.9.11 赵立彬
                        query1.addAND();
                        QueryCondition cond5 = new QueryCondition(LEFTID, QueryCondition.NOT_EQUAL, routeListInfo.getBsoID());
                        query1.addCondition(cond5);
                        query1.addAND();
                        ////////////////////////    应保证在同一个分支内        2004.9.11 赵立彬  versionID=A 不是A.1 A.2
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
                            throw new QMException("saveListRoutePartLink：不能有两个采用状态");
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
                            //skybird感觉这个顺序不对
                            deleteRoute(listLinkInfo);
                            listLinkInfo.setRouteID(null);
                        }
                    }else
                    {
                        throw new QMException("saveListRoutePartLink：处于删除状态的零件不应出现。");
                    }
                    //没有新建关联，设置删除标记。
                    if(coll.size() == 1)
                    {
                        if(VERBOSE)
                        {
                            System.out.println(TIME + "if(coll.size() == 1) 没有新建关联，设置删除标记。");
                        }
                        Collection preLinks = this.searchPreLink(routeListInfo, partMasterID, productID, parentPartID);
                        if(preLinks != null && preLinks.size() > 0)
                        {
                            if(VERBOSE)
                            {
                                System.out.println(TIME + "以前版本中有此关联，所以设置为已经删除状态");
                            }
                            listLinkInfo.setAlterStatus(PARTDELETE);
                            //被删除后，是否采用状态设置为取消。
                            listLinkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
                            pservice.saveValueInfo(listLinkInfo);
                        }else
                        {
                            if(VERBOSE)
                            {
                                System.out.println(TIME + "以前版本中没有此关联，所以删除次关联");
                            }
                            pservice.deleteValueInfo(listLinkInfo);
                        }
                    }
                    //已有新建关联。//删除此关联。
                    else if(coll.size() == 2)
                    {
                        if(VERBOSE)
                        {
                            System.out.println(TIME + "else if(coll.size() == 2) 已有新建关联。//删除此关联。");
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
     * 保存创建的艺准零件关联和路线信息，供创建和更新艺准时调用
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
            //查找该艺准的所有关联
            QMQuery myQuery = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
            QueryCondition linkCond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
            myQuery.addCondition(linkCond);
            Collection linkColl = pservice.findValueInfo(myQuery);
            ArrayList list = new ArrayList();
            //开始保存关联和路线
            for(int i = 0, j = createRouteCol.size();i < j;i++)
            {
                //数据里存放零件ID、产品信息或父件信息
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
     * 创建关联和路线对象
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
        //和产品有关时获得产品信息
        //                if(routeMode.equals("productRelative") || routeMode.equals("productAndparentRelative"))
        //                {
        QMPartMasterIfc productMaster = routeData.getProduct();
        if(productMaster != null)
        {
            productID = productMaster.getBsoID();
        }
        //                }
        //                //获得父件信息
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
            //查找是否有删除状态的关联。删掉。
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
                throw new QMException("saveListRoutePartLink'saveCollection：同一个零件在一个路线表版本中不能有两个关联，请重新设置关联。");
            }
            Iterator iter1 = coll.iterator();
            if(iter1.hasNext())
            {
                ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter1.next();
                //删除关联。
                pservice.deleteValueInfo(listLinkInfo);
            }
        }
        ListRoutePartLinkInfo listLinkInfo = ListRoutePartLinkInfo.newListRoutePartLinkInfo(routeListInfo, partMasterID);

        //st skybird 2005.3.4 保存父件号
        //  QMPartIfc partifc = (QMPartIfc) tmp[1];
        //和产品有关时设置数量信息
        if(routeMode.equals("partRelative")||routeMode.equals("productRelative") || routeMode.equals("productAndparentRelative"))
        {
            listLinkInfo.setProductCount(routeData.getProductCount());
        }
        //xucy 20111229 add  保存添加件的路线
        //boolean productIdenty = false;

        //ed
        //20120108 xucy add
        //pservice.storeValueInfo(listLinkInfo);
        //20111230xucy add
        //开始保存路线和路线分支信息
        //                if(createRouteCol != null && createRouteCol.size() > 0)
        //                {
        //                    //获得一个零件的路线信息
        //                    RouteWrapData routeWrap = (RouteWrapData)createRouteCol.get(partMasterID);

        //如果是创建的零件，则开始保存关联及其路线信息
      //CCBegin SS22
//        listLinkInfo.setModifyIdenty(routeData.getModifyIdenty());
//        listLinkInfo.setMainStr(RouteHelper.getRouteBranchStr(routeData.getMainStr()));
//        listLinkInfo.setSecondStr(RouteHelper.getRouteBranchStr(routeData.getSecondStr()));
//        listLinkInfo.setRouteDescription(routeData.getDescription());
//        //CCBegin SS9
//        listLinkInfo.setStockID(routeData.getStockID());
//        //CCEnd SS9
      
        String comp=RouteClientUtil.getUserFromCompany();
        if(routeListInfo.getRouteListState().equals("艺毕") && comp.equals("zczx")){
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
        //和产品有关时设置产品信息
        if(parentPart != null && ((routeMode.equals("parentRelative"))||(routeMode.equals("productAndparentRelative"))))
        {
            listLinkInfo.setParentPartID(parentPartID);
            listLinkInfo.setParentPartNum(routeData.getParentNum());
            listLinkInfo.setParentPartName(routeData.getParentName());
        }
        //20120108 xucy add 保存关联
        pservice.storeValueInfo(listLinkInfo);
        //主要路线信息存在则保存路线相关对象
        if(routeData.getMainStr() != null && !routeData.getMainStr().equals(""))
        {
            this.saveRoute(listLinkInfo, routeData.getRouteMap());
        }
        return listLinkInfo;
    }
    //CR28 begin
    /**
     * 保存更新的艺准零件关联及路线信息 xucy 20111230 add
     */
    private ArrayList updataAllLinkAndRoute(TechnicsRouteListIfc routeListInfo, ArrayList updateLinkCol)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //Object[] obj = new Object[2];
        ArrayList list = new ArrayList();
        for(int i = 0, j = updateLinkCol.size();i < j;i++)
        {
            //获得要更新的零件的路线信息
            RouteWrapData wrapData = (RouteWrapData)updateLinkCol.get(i);
            if(wrapData == null)
            {
                throw new QMException("RouteWrapData is null");
            }
            String linkID = wrapData.getLinkID();
            //存在关联则是要更新的对象
            if(linkID != null && !linkID.equals(""))
            {
                ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)pservice.refreshInfo(linkID);
                //如果零件原来有路线，则根据条件更新路线信息
                if(linkInfo.getRouteID() != null && !linkInfo.getRouteID().trim().equals(""))
                {
                    //20120120 xucy add
                    boolean isChangeMain = RouteHelper.compareRouteStr(wrapData.getMainStr(), linkInfo.getMainStr());
                    boolean isChangeSecond = RouteHelper.compareRouteStr(wrapData.getSecondStr(), linkInfo.getSecondStr());
                    if(routeListInfo.getOwner() != null && routeListInfo.getWorkableState().equals("wrk"))
                    {
                        TechnicsRouteListIfc original = (TechnicsRouteListIfc)pservice.refreshInfo(routeListInfo.getPredecessorID());
                        //根据关联中的零件、原本路线表查找原本路线表中有无关联的零件ID。如果相同说明是用的原本路线，则用界面信息新建路线，然后将其设到关联中。
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
                            //如果主要路线串由有变无，则删除路线相关信息，此零件就无路线了
                            if(wrapData.getMainStr() == null || wrapData.getMainStr().equals(""))
                            {
                                //删除节点和分支
                                deleteContainedObjects(linkInfo.getRouteID());
                                //pservice.deleteValueInfo(linkInfo.getRouteID());
                                //20120113 xucy 删除路线
                                deleteRoute(linkInfo);
                                linkInfo.setRouteID("");
                            }
                            //如果主要路线串存在并且发生变化了或次要路线发生了变化，则删除原路线信息、创建新路线信息
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
                    //如果主要路线串由有变无，则删除路线相关信息，此零件就无路线了
                    if(wrapData.getMainStr() == null || wrapData.getMainStr().equals(""))
                    {
                        //删除节点和分支
                        deleteContainedObjects(linkInfo.getRouteID());
                        //pservice.deleteValueInfo(linkInfo.getRouteID());
                        //20120113 xucy 删除路线
                        deleteRoute(linkInfo);
                        linkInfo.setRouteID("");
                    }
                    //如果主要路线串存在并且发生变化了或次要路线发生了变化，则删除原路线信息、创建新路线信息
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
                //如果原来没有路线，则创建路线信息
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
              //if(!routeListInfo.getRouteListState().equals("艺毕") && !comp.equals("zczx")){
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
            //无关联则是要创建的对象
            else
            {
                //查找该艺准的所有关联
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
        //////////////////排除自己
        query1.addAND();
        QueryCondition cond5 = new QueryCondition(LEFTID, QueryCondition.NOT_EQUAL, routeListInfo.getBsoID());
        query1.addCondition(cond5);
        query1.addAND();
        ////////////////////////    应保证在同一个分支内    versionID=A 不是A.1 A.2
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
     * 更新父件编号用
     * @param PartsToChange Collection 要更新的父件集合
     * @param routeListInfo1 TechnicsRouteListIfc 路线表值对象 @
     * @see TechnicsRouteListInfo
     */
    // * 废弃（gcy 05.04.29）
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
     * 搜索路线表(管理器)
     * @param param Object[][] 二维数组，5个元素。例：obj[0]=编号，obj[1]=true. true=是， false=非。数组顺序：编号、名称、级别（汉字）、用于产品、版本号。 @
     * @return Collection 存放obj[]:根据路线表编号排序后的routelist值对象<br> 此时要用ConfigService进行过滤。
     */

    public Collection findRouteLists(Object[][] param)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(ROUTELIST_BSONAME);
        int i= query.appendBso(ROUTELISTMASTER_BSONAME, false);
        query.addCondition(0, i,  new QueryCondition("masterBsoID", "bsoID"));
        query.addAND();
        //zz 设置查询时是否忽略大小写
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
            //如果产品编号输入错误，将有提示，是否需要。
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
        //如果版本恰好为最新版，可能搜出个人资料夹的东西。
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
        //routelist值对象集合。
        System.out.println("sql语句====="+query.getDebugSQL());
        Collection result = pservice.findValueInfo(query);
        //根据工作状态进行过滤。原本副本不能同时存在。获得工艺路线表值对象集合。
        filterByWorkState(result);
        //根据路线表编号升序排列。
        Collection sortedVec = RouteHelper.sortedInfos(result, "getRouteListNumber", false);
        return sortedVec;
    }

    //如果产品编号输入错误，将有提示，是否需要。
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

    //根据零件号获得零件masterID.
    private Collection getProductMasterID(String productNumber)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(PARTMASTER_BSONAME);
        //GenericPartMaster不搜索。
        query.setChildQuery(false);
        QueryCondition cond = RouteHelper.handleWildcard("partNumber", productNumber);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        return coll;
    }

    /**
     * 根据工作状态进行过滤。原本副本不能同时存在。获得工艺路线表值对象集合。
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
                //去掉原本
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
     * 搜索工艺路线表。搜索范围：编号、名称、状态、级别（汉字）、用于产品、说明、存放位置、 创建日期、创建者、最后更新、版本号。
     * @param routelistInfo TechnicsRouteListIfc 路线表值对象
     * @param productNumber String 用于产品编号
     * @param createTime String 创建时间
     * @param modifyTime String 修改时间 @
     * @return Collection 存放obj[]:根据路线表编号排序后的工艺路线表值对象结果集，最新版本。<br>
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
            throw new QMException("routelistInfo不能为空。");
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
            //////假设routelistInfo.getIterationCreator()代表用户名或者全称。
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
            //如果产品编号输入错误，将有提示，是否需要。
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
        //如果版本恰好为最新版，如有权限，可能搜出个人资料夹的东西。但此种情况居然业务规则允许。
        if(routelistInfo.getVersionID() != null && routelistInfo.getVersionID().trim().length() != 0)
        {
            //////////////////getVersionValue改成getVersionID 2004.9.11 赵立彬
            QueryCondition cond11 = RouteHelper.handleWildcard("versionID", routelistInfo.getVersionID());
            query.addCondition(0, cond11);
            query.addAND();
            //routelist值对象集合。
            if(VERBOSE)
            {
                System.out.println(TIME + "routeService's findRouteLists if... clause, SQL = " + query.getDebugSQL());
                //进行其他方法进行过滤，暂不进行。
            }
        }
        QueryCondition cond12 = new QueryCondition("iterationIfLatest", Boolean.TRUE);
        query.addCondition(cond12);
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's findRouteLists else... clause, SQL = " + query.getDebugSQL());
            //routelist值对象集合。
        }
        query.setDisticnt(true);
        //addListOrderBy(query);
        Collection result = pservice.findValueInfo(query);
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's findRouteLists result = " + result);
            //根据工作状态进行过滤。原本副本不能同时存在。获得工艺路线表值对象集合。
        }
        filterByWorkState(result);
        //根据路线表编号升序排列。
        Collection sortedVec = RouteHelper.sortedInfos(result, "getRouteListNumber", false);
        if(VERBOSE)
        {
            System.out.println(TIME + "exit routeService's findRouteLists sortedVec = " + sortedVec);
        }
        return sortedVec;
    }

    /**
     * @roseuid 402CBAF700CA
     * @J2EE_METHOD -- findRouteLists 获得工艺路线表。搜索范围：编号、名称、状态、级别、用于产品、说明、存放位置、创建日期、创建者、最后更新、版本号。
     * @return collection 工艺路线表值对象，最新版本。 此时要用ConfigService进行过滤。 public Collection findRouteLists(TechnicsRouteListIfc routelistInfo, String productNumber) { Collection result = new Vector();
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
     * {///////////////////////////////？？？？？？？？？？？？？？？？？ QueryCondition cond10 = new QueryCondition("productMasterID", QueryCondition.EQUAL, ((QMPartMasterIfc)iter.next()).getBsoID());
     * query.addCondition(0, cond10); query.addOR(); } } //如果版本恰好为最新版，可能搜出个人资料夹的东西。 if(routelistInfo.getVersionValue()!=null&&routelistInfo.getVersionValue().trim().length()!=0) { QueryCondition
     * cond11 = new QueryCondition("versionValue", QueryCondition.EQUAL, routelistInfo.getVersionValue()); query.addCondition(0, cond11); query.setDisticnt(true); query.setVisiableResult(1);
     * //routelist值对象集合。 if(VERBOSE) System.out.println(TIME + "routeService's findRouteLists if... clause, SQL = " + query.getDebugSQL()); result = pservice.findValueInfo(query); //进行其他方法进行过滤，暂不进行。 }
     * else { QueryCondition cond11 = new QueryCondition("iterationIfLatest", Boolean.TRUE); query.addCondition(0, cond11); query.addAND(); QueryCondition cond12 = new QueryCondition("masterBsoID",
     * "bsoID"); query.addCondition(0, 1, cond12); query.setDisticnt(true); query.setVisiableResult(0); if(VERBOSE) System.out.println(TIME + "routeService's findRouteLists else... clause, SQL = " +
     * query.getDebugSQL()); //routelistMaster值对象集合。 Collection coll = pservice.findValueInfo(query); for(Iterator iter = coll.iterator(); iter.hasNext(); ) { //过滤后的结果。 TechnicsRouteListIfc
     * routeListInfo = (TechnicsRouteListIfc)getLatestVesion((TechnicsRouteListMasterIfc) iter.next()); result.add(routeListInfo); } } return result; }
     */

    //用例7.查看工艺路线表
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC307
    //getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)
    ////////////////////////////////////////////////////////////////////////////

    /**
     * 生成报表，导出定制报表。输出方式为本地文件。
     * @param routeListID String 路线表的ID
     * @param exportFormat String 输出格式 @
     * @return String 路线表的名称+编号+的工艺路线报表 20120214 xucy modify
     */
    public String exportRouteList(String routeListID, String exportFormat)throws QMException
    {
        String result = "";
        if(exportFormat.trim().equals(".csv"))
        {
            result = exportRouteList(routeListID);
        }else
        {
            throw new QMException("暂时只支持.csv格式。");
        }
        return result.toString();
    }

    //填加报表头。
    private void appendReportHead(TechnicsRouteListIfc listInfo, StringBuffer result, String level_zh)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("enter appendReportHead : listInfo.bsoid = " + listInfo.getBsoID());
            //添加路线表信息。
        }
        String listNumberVal = listInfo.getRouteListNumber();
        String listNameVal = listInfo.getRouteListName();
        Object[] listObj = null;
        String key = null;
        //二级路线的表头不同。
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
        //添加产品信息。
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
        //添加报表产生日期。
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
     * 生成报表，导出定制报表。输出方式为本地文件。
     * @param routeListID String 路线表的ID
     * @return String 路线表的名称+编号+的工艺路线报表 20120214 xucy modify 瘦客户用
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

        String order = "序号";
        String number = JNDIUtil.getPropertyDescript(new QMPartMasterInfo().getBsoName(), "partNumber").getDisplayName();
        String name = JNDIUtil.getPropertyDescript(new QMPartMasterInfo().getBsoName(), "partName").getDisplayName();
        String version = "版本";
        result.append(order + comma + number + comma + name + comma + version + comma);
        //String manuRoute = RouteCategoryType.MANUFACTUREROUTE.getDisplay();
        //String assemRoute = RouteCategoryType.ASSEMBLYROUTE.getDisplay();
        result.append("主要路线" + comma + "次要路线");
        //CR25 begin
        if(routeMode.equals("productRelative"))
        {
            result.append(comma+"产品编号"+comma+"产品名称");
        }else if(routeMode.equals("parentRelative"))
        {
            result.append(comma+"父件编号"+comma+"父件名称");
        }else if(routeMode.equals("productAndparentRelative"))
        {
            result.append(comma+"产品编号"+comma+"产品名称"+comma+"父件编号"+comma+"父件名称");
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

    //将firstNodes和secondNodes合成一个新集合。并将其写入result中。
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
            out.println("enter inteObj ." + (v + 1) + "次");
            //result = new StringBuffer();

            if(v > 0)
            {
                result.append(four_comma);
            }
            Object[] bigObj = (Object[])inteObj[v];
            //add by guoxl on 2008-07-04(生成二级路线本地报表出现类造型错误)
            if(bigObj[0] instanceof String)
            {
                Object[] first1 = new Object[1];
                first1[0] = bigObj[0].toString();
                appendOnlyOneBranch(first1, result);

            }else
            {
                // add by guoxl end (生成二级路线本地报表出现类造型错误)
                Object[] first = (Object[])bigObj[0];
                appendOnlyOneBranch(first, result);
            }
            //add by guoxl on 2008-07-04(生成二级路线本地报表出现类造型错误)
            if(bigObj[1] instanceof String)
            {
                Object[] second1 = new Object[1];
                second1[0] = bigObj[1].toString();
                appendOnlyOneBranch(second1, result);
            }else
            {
                // add by guoxl end (生成二级路线本地报表出现类造型错误)  
                Object[] second = (Object[])bigObj[1];
                appendOnlyOneBranch(second, result);
            }
            result.append(newline);
            out.println(result);
            out.println("exit inteObj ." + (v + 1) + "次");
        }
    }

    //填加报表头。
    private void appendReportHead(TechnicsRouteListIfc listInfo, StringBuffer result)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("enter appendReportHead : listInfo.bsoid = " + listInfo.getBsoID());
            //添加路线表信息。
        }
        String listNumberVal = listInfo.getRouteListNumber();
        String listNameVal = listInfo.getRouteListName();
        Object[] listObj = null;
        String key = null;
        //二级路线的表头不同。
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
        //添加产品信息。
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

        //添加报表产生日期。
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

    //添加零件信息。
    private void appendPartMsg(ListRoutePartLinkIfc link, StringBuffer result)throws QMException
    {
        result.append(link.getPartMasterInfo().getPartNumber() + comma);
        result.append(link.getPartMasterInfo().getPartName() + comma);
        result.append(((QMPartIfc)getLatestVesion(link.getPartMasterInfo())).getVersionValue() + comma);
    }

    //添加节点信息。
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
     * 添加路线信息。20120214 xucy add
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
     * 根据不同模式添加生成报表的信息内容
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
        //add by guoxl on 2008-07-04(生成二级路线本地报表出现类造型错误)
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
            //add by guoxl end (生成二级路线本地报表出现类造型错误)
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
     * 根据工艺路线表ID获得一级工艺路线报表。
     * @param listInfo TechnicsRouteListIfc 路线表值对象，根据工艺路线表ID获得零件及其工艺路线值对象。 @
     * @return CodeManageTable key:partMasterInfo零件值对象;<br> value：存放的是Map:key:routeBranchInfo 路线分支值对象;<br> value: routeStr 路线串
     * @see TechnicsRouteListInfo
     */
    //obj[0]=制造路线节点值对象集合；obj[1]=装配路线节点值对象。
    public CodeManageTable getFirstLeveRouteListReport(TechnicsRouteListIfc listInfo)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
        if(!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.FIRSTROUTE.getDisplay()))
        {
            throw new QMException("路线表应该是一级路线。");
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
                // 根据工艺路线ID获得工艺路线分枝相关对象。
                //（问题三）生成报表性能优化
                //   Map routeMap = getRouteBranchs(linkInfo.getRouteID()); 原代码
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
	            // 根据工艺路线ID获得工艺路线分枝相关对象。
	            //（问题三）生成报表性能优化
	            //   Map routeMap = getRouteBranchs(linkInfo.getRouteID()); 原代码
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
			System.out.println("排序前 v= " + v + " 排序indexs = " + indexs);
		}
		if (indexs == null || indexs.size() == 0) {
			 System.out.println("排序集合为空");
			return v;
		}
		Vector result = new Vector();
		String partid;
		String partNum;
		String[] index;

		for (int i = 0; i < indexs.size(); i++) {
			index = (String[]) indexs.elementAt(i);
			partid = index[0];
			System.out.println("顺序里的" + partid);
			partNum = index[1];
			ListRoutePartLinkIfc link;
			for (int j = 0; j < v.size(); j++) {
				link = (ListRoutePartLinkIfc) v.elementAt(j);
				System.out.println("关联里的" + link.getRightBsoID());
				if (link.getRightBsoID().equals(partid)) {
					result.add(link);
					v.remove(link);
					break;
				}
			}
		}
		if (VERBOSE) {
			System.out.println("排序后 result= " + result);
		}
		return result;
	}
    //CCEnd SS36
    

    /**
     * 根据工艺路线表ID获得一级工艺路线报表。
     * @param listInfo TechnicsRouteListIfc 路线值对象，根据工艺路线表ID获得零件及其工艺路线值对象。 @
     * @return Map key:QMPartMasterIfc零件(主信息)值对象;value：存放一个map: key：TechnicsRouteBranchIfc 工艺路线分枝值对象,<br> value：路线节点数组，obj[0]=制造路线节点值对象集合； obj[1]=装配路线节点值对象。
     * @see TechnicsRouteListInfo
     */
    public Map getFirstLeveRouteListReport1(TechnicsRouteListIfc listInfo)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
        if(!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.FIRSTROUTE.getDisplay()))
        {
            throw new QMException("路线表应该是一级路线。");
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
     * 根据工艺路线表ID获得二级工艺路线报表。
     * @param listInfo TechnicsRouteListIfc 路线表值对象 @
     * @return Map key:QMPartMasterIfc零件(主信息)值对象;<br> value：存放了一个obj[] :<bt>obj[0]:一级工艺路线节点数组象集合,obj[1]:二级工艺路线节点数组象集合<br> obj2[0]： 制造路线节点值对象集合；obj2[1]：装配路线节点值对象。
     * @see TechnicsRouteListInfo
     */
    // 数组中包含两个集合，obj[0]=一级工艺路线节点数组象集合，集合中有数组。 obj1[0]=制造路线节点值对象集合；obj1[1]=装配路线节点值对象。
    // * obj[1] = 二级工艺路线节点数组象集合，集合中有数组。obj2[0]=制造路线节点值对象集合；obj2[1]=装配路线节点值对象。。
    public Map getSecondLeveRouteListReport(TechnicsRouteListIfc listInfo)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
        if(!listInfo.getRouteListLevel().trim().equals(RouteListLevelType.SENCONDROUTE.getDisplay()))
        {
            throw new QMException("路线表应该是二级路线。");
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
            throw new QMException("二级路线必须有单位。");
        }
        Map secondMap = new HashMap();
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            QMPartMasterIfc partMasterInfo = linkInfo.getPartMasterInfo();

            //获得partMasterInfo的二级路线对应的一级路线串。
            //Collection nodes = getFirstNodesBySecondRoute(departmentID, listInfo.getProductMasterID());
            Collection nodes = getFirstNodesBySecondRoute(departmentID, linkInfo.getPartMasterID());
            if(nodes != null)
            { //lm add 20040526
                Object obj[] = new Object[2];
                obj[0] = nodes;
                //获得二级路线包含路线串。
                //Map routeMap = getRouteBranchs(linkInfo.getRouteID());//原代码
                //（问题五）2006 08 03  zz 周茁添加 性能优化
                Map routeMap = getDirectRouteBranchStrs(linkInfo.getRouteID());//end
                obj[1] = routeMap.values();
                secondMap.put(partMasterInfo, obj);
            }
            ///////////////////// lm add 20040826
            else
            { //没有一级路线时，只显示二级路线节点
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

    //获得partMasterInfo的二级路线对应的一级路线串。此规则可能有变化。
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
        //路线修改时间降序排列。
        //query.addOrderBy(i, "modifyTime", true);
        query.setDisticnt(true);
        if(VERBOSE)
        {
            System.out.println("getFirstNodesBySecondRoute's SQL = " + query.getDebugSQL());
        }
        Collection coll = pservice.findValueInfo(query);
        //for(Iterator iter = coll.iterator(); iter.hasNext();)
        //此异常是否应该抛出。
        if(coll.size() == 0)
        {
            if(VERBOSE)
            {
                System.out.println(productMasterID + "没有对应的一级路线。");
            }
            return null; //lm modify 20040526
        }
        //路线修改时间降序排列。
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
        Collection result = null;
        Iterator iter = sortedVec.iterator();
        //取最后修改的路线对应的关联。
        if(iter.hasNext())
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            if(VERBOSE)
            {
                System.out.println("routeService's getFirstNodesBySecondRoute linkInfo = " + linkInfo);
                //获得一级路线包含路线串。
            }
            // Map routeMap = getRouteBranchs(linkInfo.getRouteID());
            //  （问题五）2006 08 03  zz 周茁添加 性能优化 生成报表  直接从branch里取路线穿字符串,不用恢复node对象
            Map routeMap = this.getDirectRouteBranchStrs(linkInfo.getRouteID());
            result = routeMap.values();
        }
        if(VERBOSE)
        {
            System.out.println("exit getFirstNodesBySecondRoute  result = " + result);
        }
        return result;
    }

    ////////////////////////////生成报表结束。///////////////////////////////

    /**
     * 获得所有的工艺路线表的最新版本，如果有A,B版，返回B版最新小版本。 @
     * @return Collection 存放obj[]集合：<br>obj[0]：工艺路线表值对象。
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
            //获得所有的工艺路线表的最新版本，如果有A,B版，返回B版最新小版本。
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
     * 获得与路线表相关的零部件。
     * @param routeListInfo TechnicsRouteListIfc 路线表值对象 @
     * @return Collection 存储ListRoutePartLinkIfc：关联值对象集合。
     * @see TechnicsRouteListInfo
     */
    public Collection getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("enter the class:1731行：方法:getRouteListLinkParts()");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
        query.addCondition(cond);
        query.addAND();
        //有可能零件未使用路线。
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
        ///////2004.4.28修改//////////
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            if(linkInfo.getRouteID() == null)
            {
                /**
                 * 原有代码 String status = getStatus(linkInfo.getPartMasterID(), routeListInfo.getRouteListLevel()); linkInfo.setAdoptStatus(status);
                 */
                /*
                 * 代码修改 修改者 skybird 2005.2.24
                 */
                //begin
                String level = routeListInfo.getRouteListLevel();
                String level2 = new String("二级路线");
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
     * 获得与路线表相关的零部件。
     * @param routeListInfo TechnicsRouteListIfc 路线表值对象 @
     * @return Collection 存储ListRoutePartLinkIfc：关联值对象集合。
     * @see TechnicsRouteListInfo 20120129 xucy add 查看艺准零件时调用此方法
     */
    public Collection getRouteListLinkParts1(TechnicsRouteListIfc routeListInfo)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("enter the class:1731行：方法:getRouteListLinkParts()");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
        query.addCondition(cond);
        query.addAND();
        //有可能零件未使用路线。
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
        ///////2004.4.28修改//////////
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
//          System.out.println("ddddddd" + linkInfo.getParentPart());
            if(linkInfo.getRouteID() == null)
            {
                //20120113 xucy modify 获得零件的生效路线
                Collection col = findPartEffRoute(linkInfo.getRightBsoID());
                Iterator ii = col.iterator();
                ListRoutePartLinkInfo link = null;
                if(col != null && col.size() > 0)
                {
                    //发布路线永远只有一条
                    if(ii.hasNext())
                    {
                        Object[] objss = (Object[])ii.next();
                        link = (ListRoutePartLinkInfo)objss[0];
                    }
                    if(link != null && link.getBsoID().trim().length() > 0)
                    {
                        System.out.println();
                        linkInfo.setAdoptStatus("已存在");
                    }else
                    {
                        linkInfo.setAdoptStatus("未编制");
                    }
                }else
                {
                    linkInfo.setAdoptStatus("未编制");
                }
            }else
            {
                linkInfo.setAdoptStatus("已编制");
            }
        }

        Vector indexs = routeListInfo.getPartIndex();
        if(indexs == null || indexs.size() == 0)
        {
            // System.out.println("排序集合为空");
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
     * 获得与路线表相关的零部件。 本方法是查看路线表版本历史是调用，所以不用查看其他路线表是否为关联的零部件建立路线，返回关联值对象就可以
     * @param routeListInfo TechnicsRouteListIfc 路线表值对象 @
     * @return Collection 存放ListRoutePartLinkIfc：关联值对象集合。
     * @see TechnicsRouteListInfo
     */
    //（问题四）zz 周茁添加 性能优化 2006 07 12
    public Collection getRouteListLinkPartsforVersionCompare(TechnicsRouteListIfc routeListInfo)throws QMException
    {

        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListInfo.getBsoID());
        query.addCondition(cond);
        query.addAND();
        //有可能零件未使用路线。
        QueryCondition cond1 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query.addCondition(cond1);
        Collection coll = pservice.findValueInfo(query);
        return coll;
    }

    /**
     * 获得所有路线及节点信息。
     * @param routeID String 路线ID @
     * @return Object[]<br> obj[0]：TechnicsRouteIfc 工艺路线值对象。obj[1] ： HashMap,参见getRouteBranchs(String routeID)
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
     * 根据工艺路线ID获得工艺路线分枝相关对象。
     * @param routeID String 路线ID @
     * @return Map <br>key：TechnicsRouteBranchIfc 工艺路线分枝值对象, value：路线节点数组，obj[0]=制造路线节点值对象集合； obj[1]=装配路线节点值对象。
     */
    public Map getRouteBranchs(String routeID)throws QMException
    {
        //  System.out.println(" 进入getRouteBranchs方法 传过来的 routeID " + routeID);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);//表TechnicsRouteBranch
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        // System.out.println(" 进入getRouteBranchs方法 传过来的 coll " + coll.size());
        Map map = new HashMap();
        //HashMap map = new HashMap();
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            TechnicsRouteBranchIfc routeBranchInfo = (TechnicsRouteBranchIfc)iter.next();
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
     * @param routeID String 路线ID @
     * @return Map key:TechnicsRouteBranchIfc 分支值对象; value:路线串,routeBranchInfo.getRouteStr();
     */
    //（问题五）zz 周茁添加
    public Map getDirectRouteBranchStrs(String routeID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);//表TechnicsRouteBranch
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
     * 获得路线分支
     * @param routeID String 路线ID @
     * @return Collection 存放pservice.findValueInfo(query)： 该分支中的路线节点的集合
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
     * 根据路线ID获得该路线中按分支进行分类的路线节点
     * @param routeID String 路线ID
     * @return Map <br>key：TechnicsRouteBranchIfc分支值对象，value：getBranchRouteNodes(routeBranchInfo); 该分支中的路线节点的集合 @
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
            //因分枝数据量较少，未采用两表联查方式。
            Collection branchNodes = getBranchRouteNodes(routeBranchInfo);
            map.put(routeBranchInfo, branchNodes);
        }
        return map;
    }

    /**
     * 据工艺路线分枝ID获得工艺路线节点。
     * @param routeBranchInfo TechnicsRouteBranchIfc 路线值对象 @
     * @return Collection 存放pservice.findValueInfo(query)：工艺路线节点值对象。
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
        query.appendBso(BRANCHNODE_LINKBSONAME, false);
        QueryCondition cond1 = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeBranchInfo.getBsoID());
        query.addCondition(1, cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition(RIGHTID, "bsoID");
        query.addCondition(1, 0, cond2);
        query.addOrderBy(1, "bsoID", false);
        Collection result = pservice.findValueInfo(query);
        //Collection result = pservice.findValueInfo(query);
        //(问题一)20060609 周茁修改 end
        return result;
    }

    /**
     * 按路线节点类型分类。装配路线、制造路线。
     * @param branchNodes Collection 路线节点集合 @
     * @return Object[] obj[0]：存放了一个vector:此vector存放了RouteNodeIfc路线节点值对象 <br> obj[1]：RouteNodeIfc装配节点值对象
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
                throw new QMException("节点类型不正确" + nodeInfo.getRouteType());
            }
        }
        if(VERBOSE)
        {
            if(i > 1)
            {
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
     * @param routeID String 路线ID @
     * @return Object[] <br> obj[0]:RouteNodeInfo路线节点值对象集合； obj[1]:RouteNodeLinkInfo路线节点关联值对象集合。
     */
    public Object[] getRouteNodeAndNodeLink(String routeID)throws QMException
    {
        Object[] obj = new Object[2];
        obj[0] = getRouteNodes(routeID);
        obj[1] = getNodelink(routeID);
        return obj;
    }

    /**
     * 根据工艺路线获得相关的节点(按路线分支区别)及其关联。
     * @param routeID String
     * @return Object[] <br> obj[0]:存放了一个Map（key:TechnicsRouteBranchIfc分支值对象， value:参见getBranchRouteNodes(routeBranchInfo);该分支中的路线节点的集合）；<br> obj[1]：RouteNodeLinkInfo路线节点关联值对象集合。 @
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
     * @J2EE_METHOD -- getRouteNodes 根据工艺路线获得对应的节点。 先获得工艺路线对应的若干起始节点。
     * @return Collection 获得路线节点和链接的值对象集合，参见pservice.findValueInfo(query);。
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
     * @J2EE_METHOD -- getNodelink 根据工艺路线获得对应的节点关联。
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
     * 保存，更新工艺路线。 系统保存此新建的工艺路线，将界面刷新为查看路线界面 在创建完路线后，当保存RoutePartLink时，应在ListRoutePartLink中保存相应的路线 是否使用状态。保持数据的一致性。
     * @param linkInfo ListRoutePartLinkIfc 路线表与零件关联值对象
     * @param routeInfo TechnicsRouteIfc 路线值对象 @
     * @return TechnicsRouteIfc 工艺路线值对象
     * @see ListRoutePartLinkInfo
     * @see TechnicsRouteInso
     */
    public TechnicsRouteIfc saveRoute(ListRoutePartLinkIfc linkInfo, TechnicsRouteIfc routeInfo)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        TechnicsRouteIfc routeInfoSaved = null;
        //判断路线是更新还是新建。
        if(PersistHelper.isPersistent(routeInfo))
        {
            if(VERBOSE)
            {
                System.out.println(TIME + "enter routeService's saveRoute if cause ,开始更新工艺路线。");
                //更新工艺路线。
            }
            routeInfoSaved = (TechnicsRouteIfc)pservice.saveValueInfo(routeInfo);
        }else
        {
            if(VERBOSE)
            {
                System.out.println(TIME + "enter routeService's saveRoute else cause ,开始创建工艺路线。");
                //保存工艺路线。
            }
            //把选择的复制路线对象另存一份，bsoID不同
            System.out.println("更改标记ID====" + routeInfo.getModifyIdenty());
            routeInfoSaved = (TechnicsRouteIfc)pservice.saveValueInfo(routeInfo);
            if(linkInfo != null)
            {
                String routeListID = linkInfo.getRouteListID();
                String partMasterID = linkInfo.getPartMasterID();
                //修改 skybird 2005.1.29
                //begin
                String INITIALUSED = linkInfo.getInitialUsed();
                //end
                //保存ListRoutePartLink？skybird
                if(linkInfo.getAlterStatus() == INHERIT)
                {
                    //关联后处理,设置原来的路线状态为取消。
                    //  listPostProcess(linkInfo.getRouteListMasterID(), routeListID,
                    //                  partMasterID);
                    //begin 把这个listPostProcess()方法增加了个参数
                    listPostProcess(linkInfo.getRouteListMasterID(), routeListID, partMasterID, INITIALUSED);
                    //end
                }
                TechnicsRouteListIfc routeListInfo = (TechnicsRouteListIfc)pservice.refreshInfo(routeListID);
                linkInfo.setRouteID(routeInfoSaved.getBsoID());
                //检出时的复制不处理此项。
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
     * 获得具体路线版本的关联。
     * @param routeListID String 路线表ID
     * @param partMasterID String 零部件住信息ID
     * @param partNumber String 零件编号 @
     * @return ListRoutePartLinkIfc 路线表与零件关联值对象
     */
    public ListRoutePartLinkIfc getListRoutePartLink(String routeListID, String partMasterID, String partNumber)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListID);
        query.addCondition(cond);
        query.addAND();
        //有可能零件未使用路线。
        QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(cond1);
        query.addAND();
        //不包括删除状态的关联。
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
            throw new QMException("partMasterID和listID获得的关联不应该超过一个。");
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
     * 关联后处理,设置原来的路线状态为取消。 被修改 修改者：skybird 2005.1.29 修改说明：这个方法在系统执行过程中会抛异常， 因此加个参数arg4，但这个修改导致了程序逻辑的改变， 不知道是否合乎需求的规定。
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
         * 代码修改：skybird 时间：2005.1.24
         */
        //begin
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(cond4);
        //end
        /*
         * 代码修改：skybird 2005.1.29
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
            //一旦零件采用路线，即使原关联中零件没设置路线，也要被设置为取消。否则getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)要出问题。
        }
        if(coll.size() > 1)
        {
            throw new QMException("routeService's listPostProcess : 获得的关联不应该有多个： " + coll.size());
        }
        Iterator iter = coll.iterator();
        if(iter.hasNext())
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            if(VERBOSE)
            {
                System.out.println("设置关联前 " + linkInfo.getBsoID() + "的状态为==" + linkInfo.getAdoptStatus());
            }
            linkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
            pservice.saveValueInfo(linkInfo);
            if(VERBOSE)
            {
                System.out.println("设置关联后2222222222222 " + linkInfo.getBsoID() + "的状态为==" + linkInfo.getAdoptStatus());

            }
        }
    }

    /**
     * 保存工艺路线编辑图。 路线串构造规则： 工艺路线串的构成为路线单位节点，一个单位可以在一个路线串中出现多次。路线串中包括加工单位和装配单位，所以路线串的构成必须符合下列规则： 1. 装配单位在一个路线串中只能有一个，且只能是最后一个节点； 2. 一个单位如果在一个路线串中出现多次，则必须是不同类型的节点(制造或装配)，否则不能在相邻的位置出现。 ￠ *
     * 如果一个路线串中设计了多个装配节点，则显示对应的消息； ￠ * 如果装配节点不是最后节点，则显示对应的消息； ￠ * 如果路线串中存在相邻的同类型节点，则显示对应的消息； 注意：事务回滚。
     * @param listLinkInfo ListRoutePartLinkIfc 路线与零件关联值对象
     * @param routeRelaventObejts HashMap key: TechnicsRouteServiceEJB.TECHNICSROUTE_BSONAME<br> value:RouteNodeInfo路线节点值对象或RouteNodeLinkInfo路线节点关联值对象 @
     * @see ListRoutePartLinkInfo
     */
    // 客户端在进行保存时，应首先判断ListRoutePartLinkIfc的getAlterStatus()，如=0，全部设置成新建状态；=1，正常处理。
    public TechnicsRouteIfc saveRoute(ListRoutePartLinkIfc listLinkInfo, HashMap routeRelaventObejts)throws QMException
    {
        TechnicsRouteIfc routeinfo = null;
        try
        {
            if(VERBOSE)
            {
                System.out.println("更新工艺路线：enter the TechnicsRouteServiceEJB:saveRoute():2135");
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
     *清除历史路线表关联的路线ID return true:删除历史版本关联的路线;false:不删除 CR7
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
     * 删除工艺路线。
     * @param linkInfo ListRoutePartLinkIfc 路线与零件关联值对象 @
     * @see ListRoutePartLinkInfo
     */
    //  调用到此方法的地方：
    //   * 1.删除零件关联调用了此方法
    //   * 2.编辑路线里的删除一个零部件的路线也调用了此方法。

    public void deleteRoute(ListRoutePartLinkIfc linkInfo)throws QMException
    {
        //        if(linkInfo.getRouteID() == null || linkInfo.getRouteID().trim().length() == 0)
        //        {
        //            throw new QMException("此零件没有路线，不能删除");
        //        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();

        boolean flag = isDeleteHistoryRoute(linkInfo);//CR7

        /*
         * 加这个判断有用，但是做什么用还没有想好？-----2005.1.31-skybird 我想到删除一个路线的时候只能对大版本的最新小版本进行删除工作。 三方关联有不同的状态进行设置，所以会有此判断。
         */
        /*
         * int =0 表示是从上一版本继承下来的；int=1， 从本版本生成的。涉及到路线是否重新生成；int=2， 此版本删除的，当路线表检出时，不复制此关联。
         */

        if(linkInfo.getAlterStatus() == ROUTEALTER)
        {//1
            if(flag)
            {//Begin CR7
                //删除路线包含对象。
                deleteContainedObjects(linkInfo.getRouteID());
                //删除路线本身
                pservice.deleteBso(linkInfo.getRouteID());
            }//End CR7
        }
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
     * 批量删除零件路线
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
     * 在删除路线表时删除路线信息. 此方法在删除路线信息后不再保存修改后的关联对象.
     * @param linkInfo 关联对象 @ CR2
     */

    private void deleteRouteForDeleteListen(ListRoutePartLinkIfc linkInfo)throws QMException
    {
        if(linkInfo.getRouteID() == null || linkInfo.getRouteID().trim().length() == 0)
        {
            throw new QMException("此零件没有路线，不能删除");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();

        if(linkInfo.getAlterStatus() == ROUTEALTER)
        {
            // 删除路线包含对象。
            deleteContainedObjects(linkInfo.getRouteID());
            // 删除路线本身
            pservice.deleteBso(linkInfo.getRouteID());
        }

    }

    /**
     * @roseuid 4030B10E02D9
     * @J2EE_METHOD -- deleteNode 删除节点ID.关联类可由持久化删除（需测试）。
     */
    private void deleteNode(String routeID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //由持久化删除node_node_link./////////////需测试。
        //自己删除
        QMQuery query2 = new QMQuery(ROUTENODE_LINKBSONAME);
        QueryCondition cond2 = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query2.addCondition(cond2);
        Collection coll2 = pservice.findValueInfo(query2);
        //删除所有节点关联。
        for(Iterator iter2 = coll2.iterator();iter2.hasNext();)
        {
            pservice.deleteValueInfo((BaseValueIfc)iter2.next());
        }

        QMQuery query = new QMQuery(ROUTENODE_BSONAME);
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        //删除所有节点。
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            pservice.deleteValueInfo((BaseValueIfc)iter.next());
        }

    }

    /**
     * 在删除和每次更新之前进行删除。 (此方法循环中有sql查询，效率有待测试)
     * @param routeID String 路线ID @
     */
    public void deleteBranchRelavent(String routeID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);
        QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeID);
        query.addCondition(cond);
        Collection coll = pservice.findValueInfo(query);
        //删除BranchNodeLink和Branch
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)iter.next();
            QMQuery branchQuery = new QMQuery(BRANCHNODE_LINKBSONAME);
            QueryCondition cond2 = new QueryCondition(LEFTID, QueryCondition.EQUAL, branch.getBsoID());
            branchQuery.addCondition(cond2);
            Collection coll2 = pservice.findValueInfo(branchQuery); //找到所有关联
            for(Iterator iter2 = coll2.iterator();iter2.hasNext();)
            {
                pservice.deleteValueInfo((BaseValueIfc)iter2.next());
            }
            pservice.deleteValueInfo(branch);
        }

    }

    /**
     * 删除路线时可能有问题，持久化会删除对应的关联，但此时不想删除关联。
     * @roseuid 4030B1D703B4
     * @J2EE_METHOD -- deleteRouteListener 删除工艺路线节点及其关联。
     */
    public void deleteContainedObjects(String routeID)throws QMException
    {
        //删除路线分枝。
        deleteBranchRelavent(routeID);
        //删除路线节点。
        deleteNode(routeID);

    }

    /**
     * 当只更新节点位置时，调用此方法。不进行路线的更新。
     * @param coll Collection RouteNodeIfc路线节点值对象集合 @
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
            throw new QMException("传入类型必须为RouteNodeIfc。");
        }
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            BaseValueIfc nodeInfo = (BaseValueIfc)iter.next();
            //只能是更新路线节点。
            pservice.updateValueInfo(nodeInfo);
        }
    }

    ////////////路线及其节点的创建、更新、删除结束。////////////////

    /**
     * 获得用于指定产品的最新工艺路线表
     * @param productMasterID String 用于产品 @
     * @return TechnicsRouteListInfo 工艺路线表
     */
    public TechnicsRouteListInfo getRouteListByProduct(String productMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getRouteListByProduct productMasterID = " + productMasterID);
        }
        if(productMasterID == null || productMasterID.trim().length() == 0)
        {
            throw new QMException("输入参数不对");
        }
        Object[] obj = new Object[3];
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query1 = new QMQuery("consRouteListProductLink");
        QueryCondition condition1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, productMasterID);
        query1.addCondition(condition1);
        //返回RouteListProductLink
        Collection coll = pservice.findValueInfo(query1);
        if(coll != null)
        {
            //降序排列。
            Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
            if(VERBOSE)
            {
                System.out.println(TIME + "查询产品结构对应的关联： " + coll);
            }
            Iterator iter = sortedVec.iterator();
            //只取最新创建的路线表对应的关联。
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
     * 生成物料清单的零部件路线 如果用户选择输出工艺路线，系统应检查用户当前要生成物料清单的零部件，如果有对应的 工艺路线表(工艺路线表中的“用于产品”属性关联到该零部件)，则使用此零部件的最新工 艺路线表为工艺路线的数据源，对于没在其工艺路线表中列出的零部件，取创建时间戳最新 的路线表中的路线为数据源。如果当前要生成物料清单的零部件没有对应的工艺路线表，所
     * 有零部件全部取创建时间戳最新的路线表中的路线为数据源。如果产品结构中的某些零部件 没有编制过工艺路线，则有关路线的属性输出空值。
     * @param routeList TechnicsRouteListInfo 路线表值对象
     * @param partMasterID String 零件MasterID
     * @param level_zh String 路线级别 @
     * @return Object[] obj[0]:TechnicsRouteListIfc, <br>obj[1],obj[2] 见getRouteAndBrach(routeID), <br>obj[3]:ListRoutePartLinkIfc。
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
        //查找当前路线表与partMasterID的关联
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
                throw new QMException("不应该查出多个纪录！");
            }
            ListRoutePartLinkIfc listPartLinkInfo = (ListRoutePartLinkIfc)col.toArray()[0];
            String routeID = listPartLinkInfo.getRouteID();
            obj[0] = routeList;
            Object[] route = getRouteAndBrach(routeID);
            obj[1] = route[0];
            obj[2] = route[1];
            obj[3] = listPartLinkInfo;
        }else
        { //对于没在其工艺路线表中列出的零部件，取创建时间戳最新的路线表中的路线为数据源
            obj = getProductStructRoutes(partMasterID, level_zh);
        }
        if(VERBOSE)
        {
            System.out.println(TIME + "exit... routeService's getMaterialBillRoutes " + obj);
        }
        return obj;
    }

    /**
     * 按产品结构生成路线报表
     * @param partMasterID String
     * @param level_zh String 路线级别 @
     * @return Object[] obj[0]: TechnicsRouteListIfc,<br> obj[1], obj[2]见getRouteAndBrach(routeID), <br>obj[3]:ListRoutePartLinkIfc。
     */
    public Object[] getProductStructRoutes(String partMasterID, String level_zh)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getProductStructRoutes partMasterID = " + partMasterID + ", level_zh = " + level_zh);
        }
        if(partMasterID == null || partMasterID.trim().length() == 0 || level_zh == null || level_zh.trim().length() == 0)
        {
            throw new QMException("输入参数不对");
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = getLinkQuery(partMasterID, level_zh);
        //返回ListRoutePartLinkIfc
        Collection coll = pservice.findValueInfo(query);
        //降序排列。
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
        if(VERBOSE)
        {
            System.out.println(TIME + "查询船品结构对应的关联： " + coll);
        }
        Object[] obj = new Object[4];
        Iterator iter = sortedVec.iterator();
        //只取最新创建的路线表对应的关联。
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
     * 根据零件masterID和路线级别获得所有是采用状态的关联类的查询条件。
     * @param partMasterID String
     * @param level_zh String 路线级别 @
     * @return QMQuery
     */
    private QMQuery getLinkQuery(String partMasterID, String level_zh)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("skybird-------TRS:QMQuery():参数：level_zh" + level_zh);
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
        //降序排列。
        //query.addOrderBy(i, "createTime", true);
        ///////////////////////////
        query.setDisticnt(true);
        return query;
    }

    /**
     * 新增加的方法-------skybird 2005.1.24 ？这个方法和上一个方法代码大部分重复！！
     * @param partMasterID 零部件的主ID
     * @param level_zh 路线表的级别
     * @param depart 二级工艺路线所对应的路线级别
     */
    private QMQuery getLinkQuery2(String partMasterID, String level_zh, String depart)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("skybird-------TRS:QMQuery2():参数：level_zh" + level_zh);
            System.out.println("skybird-------TRS:QMQuery2():参数：depart" + depart);
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
     * @param level_zh String 路线级别 @
     * @return Collection 数组集合。obj[0]: TechnicsRouteListIfc, <br> obj[1],obj[2]见getRouteAndBrach(routeID),<br> obj[3]:linkInfo。
     */
    public Collection getPartLevelRoutes(String partMasterID, String level_zh)throws QMException
    {
        if(partMasterID == null || partMasterID.trim().length() == 0 || level_zh == null || level_zh.trim().length() == 0)
        {
            throw new QMException("输入参数不对");
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
        //zz added start (问题零)
        QueryCondition condx = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(0, condx);
        //zz added end (问题零)

        //路线修改时间降序排列。
        //query.addOrderBy(j, "modifyTime", true);
        //SQL不能正常处理。
        query.setDisticnt(true);
        //返回ListRoutePartLinkIfc
        Collection coll = pservice.findValueInfo(query);
        //路线修改时间降序排列。
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
     * 根据零部件查找路线
     * @param partMasterID String 零部件bsoid @
     * @return Collection 数组集合。String[] {partID, routeID, routeState, linkID, state, parentPartNum});
     */
    public Collection getPartRoutes(String partMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getPartLevelRoutes, partMasterID = " + partMasterID);
        }
        if(partMasterID == null || partMasterID.trim().length() == 0)
        {
            throw new QMException("输入参数不对");
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
        //SQL不能正常处理。
        query.setDisticnt(true);
        //返回ListRoutePartLinkIfc
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's getPartLevelRoutes SQL = " + query.getDebugSQL());
        }
        Collection coll = pservice.findValueInfo(query);
        //路线修改时间降序排列。
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
        if(VERBOSE)
        {
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

    //////17.复制工艺路线
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC317

    /**
     * 复制工艺路线 复制自。利用其他路线表中的路线进行复制。获得"其它"路线表中与给定partMasterID采用的路线。 注意：此种情况只允许一个零件在不同路线表中路线的复制。不允许多个零件间的复制粘贴。 根据零件ID和工艺路线级别获得对应的工艺路线。 执行者复制一个工艺路线，粘贴到没有工艺路线的零部件中。
     * 复制工艺路线时，可以从当前路线表中的一个零部件的工艺路线复制，也可以从一个零部件的其它路线表编制的工艺路线复制； 粘贴时，可以粘贴到当前选中的零部件，也可以粘贴到本路线表中其它无路线的零部件；如果零部件已有路线，使用"粘贴到"复制路线时，不能复制到这些零部件。
     * @param linkInfo1 ListRoutePartLinkIfc @
     * @return Collection 数组集合。obj[0]: TechnicsRouteListIfc, <br> obj[1],obj[2]见getRouteAndBrach(routeID)。
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
        /////////根据路线级别进行过滤///////
        int i = query.appendBso(ROUTELIST_BSONAME, false);
        QueryCondition cond0 = new QueryCondition(LEFTID, "bsoID");
        query.addCondition(0, i, cond0);
        query.addAND();
        QueryCondition cond1 = new QueryCondition("routeListLevel", QueryCondition.EQUAL, level);
        query.addCondition(i, cond1);
        query.addAND();
        if(listInfo1.getRouteListLevel().equals("二级路线"))
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
        //降序排列。
        //query.addOrderBy("createTime", true);
        //////////////////
        query.setDisticnt(true);
        //返回ListRoutePartLinkIfc
        Collection coll = pservice.findValueInfo(query);
        if(VERBOSE)
        {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&" + query.getDebugSQL());
        }
        //降序排列。
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
     * 复制工艺路线
     * @param routeID String 路线ID: 利用给定routeID,构造对应的路线信息。
     * @param linkInfo ListRoutePartLinkIfc @
     * @return ListRoutePartLinkIfc 路线与零件关联
     * @see ListRoutePartLinkInfo
     */
    public ListRoutePartLinkIfc copyRoute(String routeID, ListRoutePartLinkIfc linkInfo)throws QMException
    {
        if(linkInfo.getRouteID() != null && linkInfo.getRouteID().trim().length() != 0)
        {
            throw new QMException("com.faw_qm.technics.consroute.util.RouteResource", "4", null);
        }
        //利用给定routeID,构造对应的路线信息。
        HashMap routeRelaventObejts = getRouteContainer(routeID, null);
        //保存路线
        saveRoute(linkInfo, routeRelaventObejts);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        ListRoutePartLinkIfc linkInfo1 = (ListRoutePartLinkIfc)pservice.refreshInfo(linkInfo.getBsoID());
        return linkInfo1;
        //另一种方式，不调用saveRoute，自己处理。
    }

    /**
     * 复制路线相关对象。注意保证顺序。
     * @param routeID String
     * @param branchInfos Collection 有变化的分支值对象。 @
     * @return HashMap <br> key:TECHNICSROUTE_BSONAME，ROUTENODE_LINKBSONAME，BRANCHNODE_LINKBSONAME ， TECHNICSROUTEBRANCH_BSONAME，ROUTENODE_BSONAME <br>
     * value:TechnicsRouteIfc路线值对象；RouteNodeLinkIfc节点关联，RouteBranchNodeLinkIfc分支与节点关联， branchItemVec分支集合，nodeItemVec节点集合
     */
    public HashMap getRouteContainer(String routeID, Collection branchInfos)throws QMException
    {
        HashMap relaventObejts = new HashMap();
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //1.添加路线到HashMap。
        TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)pservice.refreshInfo(routeID);
        setupNewBaseValueIfc(routeInfo);
        RouteItem item = createRouteItem(routeInfo);
        relaventObejts.put(TECHNICSROUTE_BSONAME, item);
        //获得节点及节点关联。
        Object[] obj1 = getRouteNodeAndNodeLink(routeID);
        //节点集合。
        Collection nodes = (Collection)obj1[0];
        //节点关联集合。
        Collection nodeLinks = (Collection)obj1[1];
        //2.添加节点关联集合到HashMap
        //集合中的对象为RouteItem.
        Collection nodeLinkItemVec = new Vector();
        for(Iterator iter1 = nodeLinks.iterator();iter1.hasNext();)
        {
            RouteNodeLinkIfc nodeLinkInfo = (RouteNodeLinkIfc)iter1.next();
            //设置关联源节点。
            RouteNodeIfc sourceNode = nodeLinkInfo.getSourceNode();
            RouteNodeIfc node1 = (RouteNodeIfc)getTheSameInfo(sourceNode, nodes);
            nodeLinkInfo.setSourceNode(node1);
            //设置关联目的节点。
            RouteNodeIfc destNode = nodeLinkInfo.getDestinationNode();
            RouteNodeIfc node2 = (RouteNodeIfc)getTheSameInfo(destNode, nodes);
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
        for(Iterator iter = branchNodeLinks.iterator();iter.hasNext();)
        {
            RouteBranchNodeLinkIfc branchNodeLinkInfo = (RouteBranchNodeLinkIfc)iter.next();
            //获得分枝item。
            TechnicsRouteBranchIfc branchInfo = branchNodeLinkInfo.getRouteBranchInfo();
            //获得在分枝与节点关联中的分支在branches的对应值。
            TechnicsRouteBranchIfc branch1 = (TechnicsRouteBranchIfc)getTheSameInfo(branchInfo, branches);
            branchNodeLinkInfo.setRouteBranchInfo(branch1);
            RouteNodeIfc branchNode = branchNodeLinkInfo.getRouteNodeInfo();
            //获得在分枝与节点关联中的节点在nodes的对应值。
            RouteNodeIfc node1 = (RouteNodeIfc)getTheSameInfo(branchNode, nodes);
            branchNodeLinkInfo.setRouteNodeInfo(node1);
            RouteItem branchNodeLinkItem = getBranchNodeLinkItem(branchNodeLinkInfo);
            branchNodeLinkItemVec.add(branchNodeLinkItem);
        }
        relaventObejts.put(BRANCHNODE_LINKBSONAME, branchNodeLinkItemVec);
        //4.添加分支集合到HashMap
        Collection branchItemVec = new Vector();
        for(Iterator iter11 = branches.iterator();iter11.hasNext();)
        {
            TechnicsRouteBranchIfc branchInfo = (TechnicsRouteBranchIfc)iter11.next();
            if(branchInfos != null)
            {
                if(branchInfo.getBsoID() == null)
                {
                    throw new QMException("分支值对象被设置为空的时机不对。");
                }
                //根据传入分支值对象集合设置是否主要路线信息。
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
        //5.添加节点集合到HashMap.
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
            //TECHNICSROUTE_BSONAME，ROUTENODE_LINKBSONAME，TECHNICSROUTEBRANCH_BSONAME
            //ROUTENODE_BSONAME，BRANCHNODE_LINKBSONAME
            //1.路线。
            System.out.println("1.route.........................................");
            RouteItem routeItem1 = (RouteItem)relaventObejts.get(TECHNICSROUTE_BSONAME);
            TechnicsRouteIfc route = (TechnicsRouteIfc)routeItem1.getObject();
            System.out.println(TIME + "route.... routeID = " + route.getBsoID());
            System.out.println(TIME + "route.... routehashCode = " + route.hashCode());
            //2.节点。
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
            //3.节点关联。
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
            //4.分支
            System.out.println("4.branch.........................................");
            Collection branchs1 = (Collection)relaventObejts.get(TECHNICSROUTEBRANCH_BSONAME);
            for(Iterator iterator = branchs1.iterator();iterator.hasNext();)
            {
                RouteItem item1 = (RouteItem)iterator.next();
                TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)item1.getObject();
                System.out.println(TIME + "branch..... brachID = " + branch.getBsoID() + ", routeID = " + branch.getRouteInfo().getBsoID());
                System.out.println(TIME + "branch..... brachHashCode = " + branch.hashCode() + ", routeHashCode = " + branch.getRouteInfo().hashCode());
            }
            //5.分枝关联。
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

    //获得在分枝与节点关联中的节点在nodes的对应值。
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
        throw new QMException("节点范围出错。");
        //   return null;
    }

    /**
     * @roseuid 4039932300E0
     * @J2EE_METHOD -- getBranchRouteNodes 根据工艺路线分枝ID获得工艺路线节点。
     * @return Collection 工艺路线分支和节点关联类值对象。
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
        //按升序排列。
        //query.addOrderBy(0, "bsoID", false);
        query.setDisticnt(true);
        Collection coll = pservice.findValueInfo(query);
        //按升序排列。
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

    /////////////////////复制结束。////////////////////////////

    /////////////////////与项目关联开始////////////////////////////

    /**
     * 根据路线获得单位和零件
     * @param routeListID String 路线表ID 根据路线表ID获得单位 @
     * @return HashMap key:单位值对象 ; value:零件masterInfo集合
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
     * 通过单位代码ID获得对应的要编工艺的零件。通过零件可获得相关信息。 例：工艺路线等信息。此方法可由工艺部分调用。
     * @param departmentID String 单位ID @
     * @return Collection 零件masterInfo集合,参见linkInfo.getPartMasterID();。
     */
    // 注意：单位有结构，查找时，子节点也要遍历。此项暂不处理。
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
     * 根据工艺路线获得其包含的单位。用于任务的分发。
     * @param routeListID String @
     * @return Collection CodingIfc或CodingClassificationIfc
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
     * 根据工艺路线获得其包含的单位ID。
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

    /////////////////////与项目关联结束////////////////////////////

    /**
     * 根据routeMaster获得所有的大版本对应的最新小版本。
     * @param listMasterInfo TechnicsRouteListMasterIfc @
     * @return Collection 存放的是Object[]：<br> obj[0]:所有的大版本对应的最新小版本。<br> 有config过滤。
     * @see TechnicsRouteListMasterInfo
     */
    //版本提供的方法：allVersionsOf(MasteredIfc mastered).
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
     * 提供版序的比较。
     * @param iteratedVec Collection 不同版本的工艺路线表值对象集合。 @
     * @return Map key ： partMasterID, value ： Collection:listRoutePartLinkIfc集合。集合顺序，版本号升序排列。
     */
    public Map compareIterate(Collection iteratedVec)throws QMException
    {
        boolean isCommonSort = Boolean.valueOf(RemoteProperty.getProperty("com.faw_qm.technics.consroute.isCommonCompare", "true")).booleanValue();
        if(VERBOSE)
        {
            System.out.println("enter the class:TechnicsRouteServiceEJB,method:compareIterate()" + isCommonSort);
            System.out.println("方法的参数，iterateVec" + iteratedVec);
        }
        if(isCommonSort)
        {
            return CompareHandler.commonCompareIterate(iteratedVec);
        }else
        {
            return CompareHandler.compareIterate(iteratedVec);
        }
    }

    ///////////////功能方法。
    /**
     * 客户端进行判断，是否是创建路线。如true,即使是更新，也要将状态设置为创建。
     * @param RouteListID String
     * @param partMasterID String @
     * @return boolean public boolean isCreateRoute(String routeListID, String partMasterID) { boolean flag = true; PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
     * QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME); QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, routeListID); query.addCondition(cond); query.addAND();
     * //有可能零件未使用路线。 QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID); query.addCondition(cond1); query.addAND(); QueryCondition cond2 = new
     * QueryCondition("rouetID", QueryCondition.NOT_NULL); query.addCondition(cond2); Collection coll = pservice.findValueInfo(query); if(coll.size()==1) flag = false; return flag; }
     */
    /**
     * 是否有路线。
     * @param partMasterID String
     * @param routeListID String
     * @return boolean public boolean isHasRoute(String partMasterID, String routeListID) { getListRoutePartLink(partMasterID, routeListID); if(routeInfo==null) return false; else return true; }
     */

    //routeListInfo为新的大版本。此时要复制关联。并要设置initialUsed为新的大版本。
    /**
     * 获得新版本
     * @param routeListInfo TechnicsRouteListIfc 为新的大版本。此时要复制关联。 并要设置initialUsed为新的大版本。 @
     * @return TechnicsRouteListIfc 路线表值对象
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
            //获得上一个小版本。
        }
        TechnicsRouteListIfc originalList = (TechnicsRouteListIfc)pservice.refreshInfo(decessorID);
        //复制新的关联。
        Collection coll = getRouteListLinkParts(originalList);
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            if(VERBOSE)
            {
                System.out.println("原关联：listLinkInfo.bsoID = " + listLinkInfo.getBsoID());
            }
            ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkInfo)((ListRoutePartLinkInfo)listLinkInfo).duplicate();
            if(VERBOSE)
            {
                System.out.println("新关联：listLinkInfo1.bsoID = " + listLinkInfo1.getBsoID());
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
        if(VERBOSE)
        {
            System.out.println("exit routeService's newVersionListener");
        }
    }

    //////////////监听处理开始//////////////////////////////////////
    /**
     * 不仅仅删除已有数据，还要保证数据恢复到未删除时的状态。此时不用回滚，持久化发出信号，已经设置回滚。
     * @roseuid 403085BA0008
     * @J2EE_METHOD -- deleteRouteListListener, 实际在撤销检出时用。 因为工艺路线没有版本。此方法负责删除和副本关联的业务对象。包括：工艺路线、路线节点。要考虑master名称和工作原本是否一致。
     */
    protected void deleteRouteListListener(TechnicsRouteListIfc routeListInfo)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //删除在routeListInfo版本中创建的工艺路线。
        QMQuery query1 = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        //        QueryCondition cond3 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        //        query1.addCondition(cond3);
        //        query1.addAND();
        //获得新建的工艺路线。
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
            //先删除艺准零件关联后删除路线
            pservice.deleteValueInfo(listLinkInfo1);
            if(routeID != null && routeID.trim().length() != 0)
            {
                deleteRouteForDeleteListen(listLinkInfo1);
            }
        }
        //Begin CR2
        //此关联持久化会删除。                                                 
        //            QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);               
        //            QueryCondition cond2 = new QueryCondition(LEFTID, QueryCondition.EQUAL,
        //                                                      routeListInfo.getBsoID());
        //            query.addCondition(cond2);
        //            Collection coll = pservice.findValueInfo(query);
        //            //删除ListRoutePartLink表中的对应的关联。
        //            for (Iterator iter3 = coll.iterator(); iter3.hasNext(); ) {
        //              pservice.deleteValueInfo( (BaseValueIfc) iter3.next());
        //            }
        //End CR2
    }

    //恢复数据。
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
        //按降序排列。
        query.addOrderBy("routeListIterationID", true);
        Collection coll = pservice.findValueInfo(query);
        Iterator iter = coll.iterator();
        //只处理第一个。
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

    //////////////////////删除零件监听开始///////////////////////////
    /**
     * 当零件被删除时，删除所有的和routeList的关联，并删除相应的路线ID.
     * @param partMasterInfo QMPartMasterIfc
     */
    protected void deletePartMasterListener(QMPartMasterIfc partMasterInfo)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("enter the TechnicsRouteServiceEJB:deletePartmasterListener():删除零件关联");
        }
        try
        {
            String partMasterID = partMasterInfo.getBsoID();
            //如果零件被删除，找到所有建立路线的listRoutePartlink关联类，删除路线。
            deletePartRoutes(partMasterID);
            //删除所有ListRoutePartLink关联。
            deletePartLinks(partMasterID);
            //如果被删除零件是产品，删除相关的路线表等。
            deleteProductRelaventObject(partMasterID);
            //持久化删除产品和路线表的关联。
        }catch(Exception e)
        {
            this.setRollbackOnly();
            throw new QMException(e);
        }
    }

    //删除零件相关路线。
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
        //删除路线。
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            if(listLinkInfo.getRouteID() != null)
            {
                deleteRoute(listLinkInfo);
            }
        }
    }

    //删除所有与零件有关的关联。
    private void deletePartLinks(String partMasterID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(cond1);
        Collection coll = pservice.findValueInfo(query);
        //删除关联。
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            //即使没有权限，路线表关联也会被删除。
            pservice.removeValueInfo(listLinkInfo);
        }
    }

    //删除与产品相关的路线表和路线等。//因不需要恢复状态，且为提高效率。手动删除路线及关联。
    private void deleteProductRelaventObject(String productMasterID)throws QMException
    {
        //删除与产品相关的路线。
        deleteProductRoutes(productMasterID);
        //删除与产品相关的listRoutePartLink。
        deleteProductLinks(productMasterID);
        //删除与产品相关的路线表。
        deleteProdcutRouteLists(productMasterID);
    }

    //删除与产品相关的路线。
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
        //删除路线。
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
     * 删除与产品相关的listRoutePartLink。 如果此处不删除关联，在路线表删除监听会做大量数据处理，浪费效率。
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
        //删除所有关联。
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            //即使没有权限，路线表关联也会被删除。
            pservice.removeValueInfo(listLinkInfo);
        }
    }

    //删除与产品相关的路线表。
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
        //删除所有关联。
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)iter.next();
            //即使没有权限，路线表也会被删除。
            pservice.removeValueInfo(listInfo);
        }
    }

    //////////////////////删除零件监听结束。///////////////////////////
    /**
     * @param original 路线表原本
     * @param work 路线表副本
     * @roseuid 4031A3570092
     * @J2EE_METHOD -- copyRouteList
     * @return 复制后的工艺路线副本。 负责拷贝整个工艺路线表。在产品升级时刻能用到。此时需注意：原来的子件可能不是现在产品的子件， 需在客户端进行检查，给出提示标识，便于用户更改。 拷贝工艺路线表。检出时路线表副本的路线及其节点的复制。
     */
    //Begin CR1
    /*
     * 原代码: protected void copyRouteList(TechnicsRouteListIfc original, TechnicsRouteListIfc work) { if (VERBOSE) { System.out.println(TIME + "enter routeService's copyRouteList,原本 " + original + "副本"
     * + work); } PersistService pservice = (PersistService) EJBServiceHelper. getPersistService(); Collection coll = getRouteListLinkParts(original); if (VERBOSE) { System.out.println(TIME +
     * "copyRouteList... 原本的三放关联coll = " + coll); } for (Iterator iter = coll.iterator(); iter.hasNext(); ) { //原本的零部件关联 ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc) iter. next();
     * //获得关联的拷贝 ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkInfo) ( ( ListRoutePartLinkInfo) listLinkInfo).duplicate(); listLinkInfo1.setRouteListID(work.getBsoID());
     * //将alterStatus设置成INHERIT状态。 listLinkInfo1.setAlterStatus(INHERIT); //将adoptStatus设置成CANCEL状态。//更改：2004.5.12将adoptStatus设置成ADOPT状态。 //////////////////因关联会产生多个采用状态,故有此更改. 2004.9.11 赵立彬
     * //listLinkInfo1.setAdoptStatus(RouteAdoptedType.ADOPT.getDisplay()); if (!listLinkInfo1.getAdoptStatus().equals(RouteAdoptedType.ADOPT. getDisplay())) {
     * listLinkInfo1.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay()); } if (VERBOSE) { System.out.println(TIME + "保存前： listLinkInfo.bsoID = " + listLinkInfo.getBsoID() +
     * ", listLinkInfo1.bsoID = " + listLinkInfo1.getBsoID()); } pservice.saveValueInfo(listLinkInfo1); if (VERBOSE) { System.out.println(TIME + "保存后： listLinkInfo1.bsoID = " +
     * listLinkInfo1.getBsoID()); } } if (VERBOSE) { System.out.println(TIME + "exit routeService's copyRouteList... "); } }
     */
    protected void copyRouteList(TechnicsRouteListIfc original, TechnicsRouteListIfc work)throws QMException
    {

        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();

        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition(LEFTID, QueryCondition.EQUAL, original.getBsoID());
        query.addCondition(cond);
        query.addAND();
        // 有可能零件未使用路线。
        QueryCondition cond1 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
        query.addCondition(cond1);

        Collection coll = pservice.findValueInfo(query, false);

        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            if(linkInfo.getRouteID() == null)
            {

                String level = original.getRouteListLevel();
                String level2 = new String("二级路线");
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
            // 重新设置关联对象的属性
            linkInfo.setBsoID(null);
            linkInfo.setCreateTime(null);
            linkInfo.setModifyTime(null);

            linkInfo.setRouteListID(work.getBsoID());

            // 将alterStatus设置成INHERIT状态。
            linkInfo.setAlterStatus(INHERIT);

            // 设置路线状态
            if(!linkInfo.getAdoptStatus().equals(RouteAdoptedType.ADOPT.getDisplay()))
            {
                linkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
            }

            pservice.saveValueInfo(linkInfo, false);

        }

    }

    //End CR1
    //设置ListRoutePartLink的setRouteListIterationID
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

    //////////////监听处理结束。//////////////////////////////////////

    /**
     * 根据零件masterID获得工艺路线表值对象集合。
     * @param partMasterID String @
     * @return Collection TechnicsRouteListInfo工艺路线表值对象集合
     */
    public Collection getListsByPart(String partMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getListsByPart, partMasterID = " + partMasterID);
        }
        if(partMasterID == null || partMasterID.trim().length() == 0)
        {
            throw new QMException("partMasterID不能为空。");
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
        //路线表的创建时间降序排列。
        query.addOrderBy(0, "createTime", true);
        //SQL不能正常处理。
        //query.setDisticnt(true);
        //返回ListRoutePartLinkIfc
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's getPartLevelRoutes SQL = " + query.getDebugSQL());
        }
        Collection coll = pservice.findValueInfo(query);
        //路线表的创建时间降序排列。
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getCreateTime", true);
        if(VERBOSE)
        {
            System.out.println(TIME + "查询结果为： sortedVec = " + sortedVec);
            System.out.println(TIME + "exit... routeService's getListsByPart ");
        }
        return sortedVec;
    }

    /////////////2004.4.27后添加的方法/////////////

    /**
     * 判断给定的零件masterID在其它路线表中是否有路线。
     * @param partMasteInfos Collection QMPartMasterIfc集合
     * @param level_zh String 路线级别 @
     * @return Map key：partMasterInfo, value ：确定一个零部件的 的状态值,参见getStatus(partMasterInfo.getBsoID(), level_zh)<br> 零件"不存在"或“无”；
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
     * 根据给定的partMasterID,level_zh（路线表级别），来动态的确定一个零部件的 的状态值。
     * @param partMasterID
     * @param level_zh 路线表级别
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
                System.out.println("TRS:动态获取一个零部件的路线状态" + status);
            }
        }else
        {
            status = RouteAdoptedType.NOTHING.getDisplay();
            if(VERBOSE)
            {
                System.out.println("TRS:动态获取一个零部件的路线状态" + status);
            }
        }
        return status;
    }

    /**
     * 该方法在工艺路线表执行更新操作的时候所调用的方法， 它针对的是二级工艺路线表的情形。该方法当一个零部 件的路线是空的时候调用，来动态的生成零部件的路线 状态。
     * @param partmasterID 路线表的零部件
     * @param level_zh 路线表的级别
     * @param depart 二级路线表的单位属性值
     */
    // * 新增加的一个方法--------- skybird 2005.2.24
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
                System.out.println("TRS:动态获取一个零部件的路线状态" + status);
            }
        }else
        {
            status = RouteAdoptedType.NOTHING.getDisplay();
            if(VERBOSE)
            {
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
     * @param branchInfos Collection 分支值对象 根据分支创建路线 @
     * @see ListRoutePartLinkInfo
     * @see TechnicsRouteInfo
     */
    public void createRouteByBranch(ListRoutePartLinkIfc linkInfo, TechnicsRouteIfc route, Collection branchInfos)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("TechnicsRouteServiceEJB,由更新路线调用（没点击更新路线，alterstatus 0）");
        }
        //利用给定routeID,构造对应的路线信息。
        HashMap routeRelaventObejts = getRouteContainer2(linkInfo.getRouteID(), route, branchInfos);
        //保存路线。
        saveRoute(linkInfo, routeRelaventObejts);
    }

    /**
     * 更新分枝。
     * @param branchs Collection RouteBranchNodeLinkIfc要更新分枝值对象。 @
     */
    public void updateBranchInfos(Collection branchs)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        for(Iterator iter = branchs.iterator();iter.hasNext();)
        {
            //此时的分支值对象都已经持久化。
            pservice.updateValueInfo((BaseValueIfc)iter.next());
        }
    }

    /**
     * 根据路线类型和分枝ID获得工艺路线节点。
     * @param routeType String 路线类型
     * @param routeBranchID String 路线分支ID
     * @return Collection null
     */
    //（可考虑在检查起始节点是否有左连接，如有，调用RouteListHandler的后处理方法，重新设置起始节点。根据实际情况做选择。）
    public Collection getRouteNodes(String routeType, String routeBranchID)
    {
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
     * @J2EE_METHOD -- getRoutes 根据工艺路线表ID获得对应的工艺路线。
     * @return Collection 返回路线表对应的所有工艺路线，路线分枝，路线节点。 public Collection getRoutes(String routeListID) { return null; }
     */

    /**
     * 根据产品ID和零件ID获得工艺路线值对象。
     * @param productMasterID String 产品ID
     * @param partMasterID String零件ID
     * @return TechnicsRouteIfc null
     */
    public TechnicsRouteIfc getRoute(String productMasterID, String partMasterID)
    {
        return null;
    }

    /**
     * 根据工艺路线表ID获得所有工艺路线及其节点值对象。
     * @param routeListID String 路线表ID
     * @param level_zh String 路线表级别
     * @return HashMap null
     */
    public java.util.HashMap getRouteListContent(String routeListID, String level_zh)
    {
        return null;
    }

    /**
     * 根据工艺路线表及路线级别获得工艺路线。 如果级别为空，不分级别。
     * @param routeListID String 路线表ID
     * @param level_zh String 路线级别
     * @return Collection null
     */
    public Collection getListLevelRoutes(String routeListID, String level_zh)
    {
        return null;
    }

    /**
     * 获得选择的零件
     * @param depart String 单位
     * @param master QMPartMasterIfc @
     * @return boolean 有零件返回true,否则返回false
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

    // zz (问题八)20061110 周茁添加,为二级路线 "添加"按钮 添加根据所选单位过滤功能

    /**
     * 为二级路线 "添加"按钮 添加根据所选单位过滤功能
     * @param depart String 单位
     * @param masters Vector @
     * @return Collection ListRoutePartLinkInfo的RightBsoID <br> 集合经路线单位过滤后的零件集合
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
                        //经路线单位过滤后的零件
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
     * 用当前用户的配置规范过滤零部件
     * @param master QMPartMasterIfc @
     * @return QMPartIfc 过滤后零件的值对象
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
     * 通过part找出所有符合配制规范的子part，并调getOptionPart（）过滤结果集
     * @param partMaster QMPartMasterIfc
     * @param configSpecIfc PartConfigSpecIfc
     * @param depart String 单位 @
     * @return Vector QMPartIfc[] 数组集合:<br> partInfos[i]：过滤后的零件集合
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
     * 复制路线相关对象。注意保证顺序。 //由getRouteContainer而来 lm add 20040827
     * @param routeID String
     * @param 有变化的分支值对象。
     * @return HashMap
     */
    private HashMap getRouteContainer2(String routeID, TechnicsRouteIfc oldRoute, Collection branchInfos)throws QMException
    {
        HashMap relaventObejts = new HashMap();
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //1.添加路线到HashMap。
        TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)pservice.refreshInfo(routeID);
        setupNewBaseValueIfc(routeInfo);
        routeInfo.setRouteDescription(oldRoute.getRouteDescription());
        RouteItem item = createRouteItem(routeInfo);
        relaventObejts.put(TECHNICSROUTE_BSONAME, item);
        //获得节点及节点关联。
        Object[] obj1 = getRouteNodeAndNodeLink(routeID);
        //节点集合。
        Collection nodes = (Collection)obj1[0];
        //节点关联集合。
        Collection nodeLinks = (Collection)obj1[1];
        //2.添加节点关联集合到HashMap
        //集合中的对象为RouteItem.
        Collection nodeLinkItemVec = new Vector();
        for(Iterator iter1 = nodeLinks.iterator();iter1.hasNext();)
        {
            RouteNodeLinkIfc nodeLinkInfo = (RouteNodeLinkIfc)iter1.next();
            //设置关联源节点。
            RouteNodeIfc sourceNode = nodeLinkInfo.getSourceNode();
            RouteNodeIfc node1 = (RouteNodeIfc)getTheSameInfo(sourceNode, nodes);
            nodeLinkInfo.setSourceNode(node1);
            //设置关联目的节点。
            RouteNodeIfc destNode = nodeLinkInfo.getDestinationNode();
            RouteNodeIfc node2 = (RouteNodeIfc)getTheSameInfo(destNode, nodes);
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
        for(Iterator iter = branchNodeLinks.iterator();iter.hasNext();)
        {
            RouteBranchNodeLinkIfc branchNodeLinkInfo = (RouteBranchNodeLinkIfc)iter.next();
            //获得分枝item。
            TechnicsRouteBranchIfc branchInfo = branchNodeLinkInfo.getRouteBranchInfo();
            //获得在分枝与节点关联中的分支在branches的对应值。
            TechnicsRouteBranchIfc branch1 = (TechnicsRouteBranchIfc)getTheSameInfo(branchInfo, branches);
            branchNodeLinkInfo.setRouteBranchInfo(branch1);
            RouteNodeIfc branchNode = branchNodeLinkInfo.getRouteNodeInfo();
            //获得在分枝与节点关联中的节点在nodes的对应值。
            RouteNodeIfc node1 = (RouteNodeIfc)getTheSameInfo(branchNode, nodes);
            branchNodeLinkInfo.setRouteNodeInfo(node1);
            RouteItem branchNodeLinkItem = getBranchNodeLinkItem(branchNodeLinkInfo);
            branchNodeLinkItemVec.add(branchNodeLinkItem);
        }
        relaventObejts.put(BRANCHNODE_LINKBSONAME, branchNodeLinkItemVec);
        //4.添加分支集合到HashMap
        Collection branchItemVec = new Vector();
        for(Iterator iter11 = branches.iterator();iter11.hasNext();)
        {
            TechnicsRouteBranchIfc branchInfo = (TechnicsRouteBranchIfc)iter11.next();
            if(branchInfos != null)
            {
                if(branchInfo.getBsoID() == null)
                {
                    throw new QMException("分支值对象被设置为空的时机不对。");
                }
                //根据传入分支值对象集合设置是否主要路线信息。
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
        //5.添加节点集合到HashMap.
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
            //TECHNICSROUTE_BSONAME，ROUTENODE_LINKBSONAME，TECHNICSROUTEBRANCH_BSONAME
            //ROUTENODE_BSONAME，BRANCHNODE_LINKBSONAME
            //1.路线。
            System.out.println("1.route.........................................");
            RouteItem routeItem1 = (RouteItem)relaventObejts.get(TECHNICSROUTE_BSONAME);
            TechnicsRouteIfc route = (TechnicsRouteIfc)routeItem1.getObject();
            System.out.println(TIME + "route.... routeID = " + route.getBsoID());
            System.out.println(TIME + "route.... routehashCode = " + route.hashCode());
            //2.节点。
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
            //3.节点关联。
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
            //4.分支
            System.out.println("4.branch.........................................");
            Collection branchs1 = (Collection)relaventObejts.get(TECHNICSROUTEBRANCH_BSONAME);
            for(Iterator iterator = branchs1.iterator();iterator.hasNext();)
            {
                RouteItem item1 = (RouteItem)iterator.next();
                TechnicsRouteBranchIfc branch = (TechnicsRouteBranchIfc)item1.getObject();
                System.out.println(TIME + "branch..... brachID = " + branch.getBsoID() + ", routeID = " + branch.getRouteInfo().getBsoID());
                System.out.println(TIME + "branch..... brachHashCode = " + branch.hashCode() + ", routeHashCode = " + branch.getRouteInfo().hashCode());
            }
            //5.分枝关联。
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
     * 根据给定的制造单位查询相关的零部件和工艺路线id
     * @param makeDepartment String 制造单位 @
     * @return Vector 存放的是 <br> String[] {listRoutePartLink.getPartMasterID(), routeid, listRoutePartLink.getAdoptStatus(), listRoutePartLink.getBsoID()}
     */
    // * added by skybird 2005.3.9
    public Vector getAllPartsRoutesM(String makeDepartment)throws QMException
    {
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
                //这里可能添加重复的路线
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
            //以下是查寻零部件路线和路线表的三方关联表
            for(int i = 0;i < routeidVec.size();i++)
            {
                technicsRoute = (TechnicsRouteIfc)routeidVec.elementAt(i);
                routeid = technicsRoute.getBsoID();
                if(VERBOSE)
                {
                    System.out.println("4461row routeid" + routeid);
                }
                //以下的查询可能产生重复
                query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
                cond = new QueryCondition("routeID", QueryCondition.EQUAL, routeid);
                query.addCondition(cond);
                coll = pservice.findValueInfo(query);
                if(VERBOSE)
                {
                    System.out.println("测试用的expected num is 1:" + coll.size());
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
     * 根据给定的装配单位查询相关的零部件和工艺路线id
     * @param constructDepartment String 装配单位 @
     * @return Vector 存放的是 String[] {listRoutePartLink.getPartMasterID(), routeid, listRoutePartLink.getAdoptStatus(), listRoutePartLink.getBsoID()}
     */
    // * added by skybird 2005.3.9
    public Vector getAllPartsRoutesC(String constructDepartment)throws QMException
    {
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
            //以下是查寻零部件路线和路线表的三方关联表
            for(int i = 0;i < routeidVec.size();i++)
            {
                technicsRoute = (TechnicsRouteIfc)routeidVec.elementAt(i);
                routeid = technicsRoute.getBsoID();
                //以下的查询可能产生重复
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
     * 根据给定的制造单位和装配单位查询相关的零部件和工艺路线id
     * @param makeDepartment String 制造单位
     * @param constructDepartment String 装配单位 @
     * @return Vector 存放了两个vector:<br> 1.mResult Vector:根据制造单位查询返回的结果；<br> 存放的是String[] {listRoutePartLink.getPartMasterID(), routeid, listRoutePartLink.getAdoptStatus(), listRoutePartLink.getBsoID()}
     * 2.cResult Vector：根据装配单位查询返回的结果;<br> 存放的是String[] {listRoutePartLink.getPartMasterID(), routeid, listRoutePartLink.getAdoptStatus(), listRoutePartLink.getBsoID()}
     */
    //* added by skybird 2005.3.9
    public Vector getAllPartsRoutesA(String makeDepartment, String constructDepartment)throws QMException
    {
        //存储返回的结果
        Vector result = new Vector();
        //根据制造单位查询返回的结果
        Vector mResult = new Vector();
        //根据装配单位查询返回的结果
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
     * 给路线和连接分类
     * @param routeAndLinks Collection 路线和连接集合
     * @return Collection
     */
    //zz 周茁修改（问题六） 2006 08 04
    //这个方法有问题，排序后元素会增多重复元素
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
     * 根据给定的制造单位查询相关的零部件和工艺路线id
     * @param mDepartment String 制造单位的id
     * @param cDepartment String 装配单位的id
     * @param productID String 产品的id
     * @param source String 零部件的来源
     * @param type String 零部件的类型 @
     * @return Collection vector集合:<br> part.getLifeCycleState().getDisplay()<br> part.getBsoID()<br> partmaster.getPartNumber()<br> partmaster.getPartName()<br> part.getVersionValue()<br>
     * lrpLink.getParentPartNum()<br> Integer(this.calCountInProduct(part, lrpLink.getParentPart(), product, boms, midleBoms))<br> route.getRouteDescription()<br>
     * 结果集合。每个集合项是一个Vector.依次放零部件生命周期，零部件id，零部件编号，零部件名称， 版本，父件号，零部件在产品中数量集合，路线数量，路线集合（其中路线集合每个条目包括制造装配2中路线）
     */
    /*
     * delete by guoxl on 2008-12-23(解放实施问题，关于按路线单位搜索) public Collection getAllPartsRoutes(String mDepartment, String cDepartment, String productID, String source, String type) { if (VERBOSE) {
     * System.out.println("进入路线服务getAllPartsRoutes方法 :" + "mDepartment = " + mDepartment + "  cDepartment=" + cDepartment + " productID =" + productID); } try { PersistService pservice =
     * (PersistService) EJBServiceHelper. getPersistService(); StandardPartService partService = (StandardPartService) EJBServiceHelper. getService(PART_SERVICE); VersionControlService vcservice =
     * (VersionControlService) EJBServiceHelper.getService ("VersionControlService"); if (productID != null && !productID.trim().equals("")) { QMPartIfc product = (QMPartIfc)
     * pservice.refreshInfo(productID); //此方法等part组提供 Vector subs = (Vector) partService.getAllSubParts(product); Vector products = new Vector(); products.add(product); return
     * getColligationRoutesDetial(mDepartment, cDepartment, subs, products, false); } else { PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc) partService.findPartConfigSpecIfc(); HashMap
     * bomMap = new HashMap(); Vector mdepartIDs = new Vector(); Vector cdepartIDs = new Vector(); if (mDepartment != null && !mDepartment.trim().equals("")) { BaseValueIfc base = (BaseValueIfc)
     * pservice.refreshInfo(mDepartment); mdepartIDs = this.getAllSubDepartMentID(mdepartIDs, base); } if (cDepartment != null && !cDepartment.trim().equals("")) { BaseValueIfc base = (BaseValueIfc)
     * pservice.refreshInfo(cDepartment); cdepartIDs = this.getAllSubDepartMentID(cdepartIDs, base); } Collection routeAndLinks = this.getRouteByPartAndDep(mdepartIDs, cdepartIDs, null); //
     * System.out.println("5383行 getRouteByPartAndDep 得到的 routeAndLinks 集合的长度" + routeAndLinks.size()); if (routeAndLinks != null && routeAndLinks.size() > 0) { routeAndLinks =
     * sortRouteAndLinks(routeAndLinks); // System.out.println(" sortRouteAndLinks 之后的 routeAndLinks 集合的长度 " +routeAndLinks.size() ); } Iterator routeIterator = routeAndLinks.iterator();
     * BaseValueIfc[] bsos; TechnicsRouteIfc route; HashMap parts = new HashMap(); ArrayList partMasters = new ArrayList(); ListRoutePartLinkIfc lrpLink; QMPartMasterIfc partmaster; QMPartIfc part;
     * //(问题二)20060701 周茁修改 begin while (routeIterator.hasNext()) { bsos = (BaseValueIfc[]) routeIterator.next(); partmaster = (QMPartMasterIfc) bsos[2]; // 用当前用户的配置规范过滤 if
     * (!parts.containsKey(partmaster.getBsoID())) { //parts.put(partmaster.getBsoID(), // this.filteredIterationsOfByDefault(partmaster)); parts.put(partmaster.getBsoID(),null);
     * partMasters.add(partmaster); } } Collection filteredPartmasters = this.filteredIterationsOfByDefault(partMasters); for(Iterator f = filteredPartmasters.iterator();f.hasNext();) { Object[] obj =
     * (Object[])f.next(); parts.put(((QMPartIfc)obj[0]).getMasterBsoID(),((BaseValueIfc)obj[0])); } //(问题二)20060701 周茁修改 end //(问题二)20060725 周茁修改 end Vector products =
     * this.getAllParentProductOnce(new Vector(parts.values())); //(问题二)20060725 周茁修改 end Iterator routeIterator1 = routeAndLinks.iterator(); Vector content = new Vector(); Vector result = new
     * Vector(); Vector productNums = new Vector(); for (int j = 0; j < products.size(); j++) { QMPartIfc tempP = (QMPartIfc) products.elementAt(j); productNums.add(tempP.getPartNumber() + "(" +
     * tempP.getVersionValue() + ")"); } result.add(productNums); while (routeIterator1.hasNext()) { Vector temp = new Vector(); HashMap map; bsos = (BaseValueIfc[]) routeIterator1.next();
     * 
     * lrpLink = (ListRoutePartLinkIfc) bsos[0]; route = (TechnicsRouteIfc) bsos[1]; // System.out.println("重bsos里取出来的route " +route.getBsoID() ); partmaster = (QMPartMasterIfc) bsos[2]; //取最新版本，有点问题
     * part = (QMPartIfc) parts.get(partmaster.getBsoID()); temp.add(part.getLifeCycleState().getDisplay()); temp.add(part.getBsoID()); temp.add(partmaster.getPartNumber());
     * temp.add(partmaster.getPartName()); temp.add(part.getVersionValue()); temp.add(lrpLink.getParentPartNum()); QMPartIfc product; Vector counts = new Vector(); for (int j = 0; j < products.size();
     * j++) { product = (QMPartIfc) products.elementAt(j); List boms = null; List midleBoms = null; if(lrpLink.getParentPartID()==null) counts.add(new Integer(0)); else{
     * 
     * if (bomMap.containsKey(product.getBsoID())) boms = (List) bomMap.get(product.getBsoID()); else { String[] attrNames = {"quantity"}; boms = partService.setBOMList(product, attrNames, null, null,
     * null, partConfigSpecIfc); bomMap.put(product.getBsoID(), boms); } if (bomMap.containsKey(lrpLink.getParentPart().getBsoID())) midleBoms = (List) bomMap.get(lrpLink.getParentPart().getBsoID());
     * else { String[] attrNames = {"quantity"}; midleBoms = partService.setBOMList(lrpLink.getParentPart(), attrNames, null, null, null, partConfigSpecIfc);
     * bomMap.put(lrpLink.getParentPart().getBsoID(), midleBoms); } //问题，如果数量为0没处理.另外，可以判断是否产品就是路线表对应的，如果是，可以直接用 counts.add(new Integer(this.calCountInProduct(part, lrpLink.getParentPart(), product,
     * boms, midleBoms))); } } temp.add(counts); // （问题五）瘦客户查看时可以直接从TechnicsRouteBranchIfc对象的新添属性路线串字符串中取 zz start // map = (HashMap) getRouteBranchs(route.getBsoID()); //原代码 //
     * assembleBranchStr(temp, map);//原代码 map = (HashMap) getDirectRouteBranchStrs(route.getBsoID()); assembleBranchStrForThin(temp, map); // （问题五）瘦客户查看时可以直接从TechnicsRouteBranchIfc对象的新添属性路线串字符串中取 zz
     * end //add by guoxl 2008.11.17(获得路线表编号，添加到集合中) String routeListNum=lrpLink.getLeftBsoID(); TechnicsRouteListIfc routeListIfc=(TechnicsRouteListIfc) pservice.refreshInfo(routeListNum);
     * temp.add(routeListIfc.getRouteListNumber()); //add end by guoxl temp.add(route.getRouteDescription()); content.add(temp); } result.add(content); return result; } } catch (Exception e) {
     * e.printStackTrace(); } return null; }delete end by guoxl end on 2008-12-23(解放实施问题，关于按路线单位搜索)
     */
    //add by guoxl on 2008-12-23(解放实施问题，关于按路线单位搜索)
    public Collection getAllPartsRoutes(String mDepartment, String cDepartment, String productID, String source, String type)throws QMException
    {

        if(VERBOSE)
        {
            System.out.println("进入方法==getAllPartsRoutes==" + "mDepartment===" + mDepartment + "/n" + "cDepartment=======" + cDepartment + "productID=====" + productID + "/n" + "source=====" + source
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
            }//只有单位
            else
            {
                //获得零件的配置规格

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

                //通过部门利用ＳＱＬ查找
                Collection routeAndLinks = this.getRouteByPartAndDep(mdepartIDs, cdepartIDs, null);

                if(routeAndLinks != null && routeAndLinks.size() > 0)
                {

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

                HashMap partParentsMap = new HashMap();

                //(问题二)20060701 周茁修改 begin
                while(routeIterator.hasNext())
                {
                    bsos = (BaseValueIfc[])routeIterator.next();
                    //add by guoxl on 2008-12-19(解放实施问题)
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
                    //add by guoxl on 2008-12-19(解放实施问题)
                    partmaster = (QMPartMasterIfc)bsos[2];
                    // 用当前用户的配置规范过滤
                    if(!parts.containsKey(partmaster.getBsoID()))
                    {

                        parts.put(partmaster.getBsoID(), null);
                        partMasters.add(partmaster);
                    }
                }
                //通过partMasters进行配置规范过滤,然后返回QMpartIfc集合
                Collection filteredPartmasters = this.filteredIterationsOfByDefault(partMasters);

                for(Iterator f = filteredPartmasters.iterator();f.hasNext();)
                {
                    Object[] obj = (Object[])f.next();
                    parts.put(((QMPartIfc)obj[0]).getMasterBsoID(), ((BaseValueIfc)obj[0]));

                }
                //(问题二)20060701 周茁修改 end
                //(问题二)20060725 周茁修改 end

                //add by guoxl on 2008-12-19(解放实施问题)
                Vector products = new Vector(partParentsMap.values());
                //add by guoxl end on 2008-12-19(解放实施问题)

                //(问题二)20060725 周茁修改 end
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
                    //  System.out.println("重bsos里取出来的route " +route.getBsoID() );
                    partmaster = (QMPartMasterIfc)bsos[2];
                    //取最新版本，有点问题
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

                    // （问题五）瘦客户查看时可以直接从TechnicsRouteBranchIfc对象的新添属性路线串字符串中取 zz start
                    //         map = (HashMap) getRouteBranchs(route.getBsoID()); //原代码
                    //          assembleBranchStr(temp, map);//原代码
                    //                    map = (HashMap)getDirectRouteBranchStrs(route.getBsoID());
                    //                    assembleBranchStrForThin(temp, map);
                    temp.add(lrpLink.getMainStr());
                    temp.add(lrpLink.getSecondStr());
                    // （问题五）瘦客户查看时可以直接从TechnicsRouteBranchIfc对象的新添属性路线串字符串中取 zz end
                    //add by guoxl 2008.12.9(获得路线表编号，添加到集合中)
                    String routeListNum = lrpLink.getLeftBsoID();
                    TechnicsRouteListIfc routeListIfc = (TechnicsRouteListIfc)pservice.refreshInfo(routeListNum);

                    TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo)routeListIfc.getMaster();
                    //获得现有的最新小版本
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
     * 通过零部件的名字和号码查找零部件的主信息,返回的集合中只有一个QMPartMasterIfc对象。
     * @param partName :String 零部件的名称。
     * @param partNumber :String 零部件的号码。
     * @return collection:查找到的QMPartMasterIfc零部件主信息对象的集合，只有一个元素。
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
    //add end by guoxl 2008-12-23(解放实施问题，关于按路线单位搜索)
    public Collection getAllPartsRoutes_new(String mDepartment, String cDepartment, String partNum, String partName , String state)throws QMException
    {

        if(VERBOSE)
        {
            System.out.println("进入方法==getAllPartsRoutes==" + "mDepartment===" + mDepartment + "\n" + "cDepartment=======" + cDepartment + "partNum=====" + partNum + "\n" + "partName=====" + partName
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
            }//只有单位
            else
            {
                //获得零件的配置规格

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

                //通过部门利用ＳＱＬ查找
                Collection routeAndLinks = this.getRouteByPartAndDep(mdepartIDs, cdepartIDs, null);

                if(routeAndLinks != null && routeAndLinks.size() > 0)
                {

                    routeAndLinks = sortRouteAndLinks(routeAndLinks);

                    //  System.out.println(" sortRouteAndLinks 之后的 routeAndLinks 集合的长度 " +routeAndLinks.size() );
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

                //(问题二)20060701 周茁修改 begin
                while(routeIterator.hasNext())
                {
                    bsos = (BaseValueIfc[])routeIterator.next();
                    //add by guoxl on 2008-12-19(解放实施问题)
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
                    //add by guoxl on 2008-12-19(解放实施问题)
                    //CCBegin SS21
                    //partmaster = (QMPartMasterIfc)bsos[2];
                    partmaster = (QMPartIfc)bsos[2];
                    //CCEnd SS21
                    // 用当前用户的配置规范过滤
                    if(!parts.containsKey(partmaster.getBsoID()))
                    {

                        //CCBegin SS21
                        //parts.put(partmaster.getBsoID(), null);
                        //partMasters.add(partmaster);
                        parts.put(partmaster.getBsoID(), partmaster);
                        //CCEnd SS21
                    }
                }
                //通过partMasters进行配置规范过滤,然后返回QMpartIfc集合
                //CCBegin SS21
                /*Collection filteredPartmasters = this.filteredIterationsOfByDefault(partMasters);

                for(Iterator f = filteredPartmasters.iterator();f.hasNext();)
                {
                    Object[] obj = (Object[])f.next();
                    parts.put(((QMPartIfc)obj[0]).getMasterBsoID(), ((BaseValueIfc)obj[0]));

                }*/
                //CCEnd SS21
                //(问题二)20060701 周茁修改 end
                //(问题二)20060725 周茁修改 end

                //add by guoxl on 2008-12-19(解放实施问题)
                Vector products = new Vector(partParentsMap.values());
                //add by guoxl end on 2008-12-19(解放实施问题)

                //(问题二)20060725 周茁修改 end
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
							// 获得现有的最新小版本
							routeListIfc = (TechnicsRouteListIfc) getLatestVesion(masterinfo);
							
							route = (TechnicsRouteIfc) bsos[1];
							// System.out.println("重bsos里取出来的route "
							// +route.getBsoID() );
							//CCBegin SS21
							//partmaster = (QMPartMasterIfc) bsos[2];
							partmaster = (QMPartIfc) bsos[2];
							//CCEnd SS21
							// 取最新版本，有点问题
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
									make = "主:" + make + ";" + "次:" + sMake;
									zhuang = "主:" + zhuang + "次:" + sZhuang;
								} else {

									make = "主:" + make + ";" + "次:"+ sRoute;
									if(!zhuang.equals(""))
									   zhuang = "主:" + zhuang;
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
				// 获得现有的最新小版本
				routeListIfc = (TechnicsRouteListIfc) getLatestVesion(masterinfo);
				
				route = (TechnicsRouteIfc) bsos[1];
				// System.out.println("重bsos里取出来的route "
				// +route.getBsoID() );
				//CCBegin SS21
				//partmaster = (QMPartMasterIfc) bsos[2];
				partmaster = (QMPartIfc) bsos[2];
				//CCEnd SS21
				// 取最新版本，有点问题
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
						make = "主:" + make + ";" + "次:" + sMake;
						zhuang = "主:" + zhuang + "次:" + sZhuang;
					} else {

						make = "主:" + make + ";" + "次:"+ sRoute;
						if(!zhuang.equals(""))
						   zhuang = "主:" + zhuang;
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
	 * 统计综合路线分布
	 * 
	 * @param mDepartment
	 *            String 制造单位id
	 * @param cDepartment
	 *            String 装配单位id
	 * @param parts
	 *            Vector 要统计的子件
	 * @param products
	 * Vector 产品的集合 @
	 * @return Collection vector集合：
	 *         结果集合。每个集合项是一个Vector.依次放零部件生命周期，零部件id，零部件编号，零部件名称，
	 *         版本，父件号，零部件在产品中数量集合，路线数量，路线集合（其中路线集合每个条目包括制造装配2中路线）
	 */
    public Collection getColligationRoutes(String mDepartment, String cDepartment, Vector parts, Vector products)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("进入路线服务getColligationRoutes方法 :" + "mDepartment = " + mDepartment + "  cDepartment=" + cDepartment + "  parts=" + parts + "  products  =" + products);
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
            //5.25更改为没有零部件也显示
            return getColligationRoutesDetial(mDepartment, cDepartment, partInfos, productInfos, true);
        }catch(Exception e)
        {
            e.printStackTrace();
            throw new QMException(e);
        }
    }

    /**
     * 得到当前部门的所有子部门
     * @param results Vector 结果集合
     * @param depart BaseValueIfc 当前部门 @
     * @return Vector 结果集合
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

                    //获得当前部门的所有子部门
                    getAllSubDepartMentID(results, (BaseValueIfc)i.next());

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
     * @param showAll boolean 是否显示所有产品结构，只有当生成综合路线客户端没有添（右边）领部件条件时为true @
     * @return Collection 结果集合。每个集合项是一个Vector.依次放零部件生命周期，零部件id，零部件编号，零部件名称， 版本，父件号，零部件在产品中数量集合，路线数量，路线集合（其中路线集合每个条目包括制造装配2中路线）
     */
    private Collection getColligationRoutesDetial(String mDepartment, String cDepartment, Vector parts, Vector products, boolean showAll)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("进入路线服务getColligationRoutesDetial方法 :" + "mDepartment = " + mDepartment + "  cDepartment=" + cDepartment + "  parts=" + parts + "  products  =" + products);
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
                System.out.println("返回路线服务getRouteByPartAndDep方法 :" + "mDepartment = " + mDepartment + "  cDepartment=" + cDepartment + " partMasterID=" + part.getMasterBsoID() + "得到结果树"
                        + routeAndLinks);

                //如果是要显示整个产品结构，当前领部件又没有路线，则，只显示产品结构信息
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
                //获得不重复的父件集合
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
                    //add by guoxl 2008.12.9(获得路线表编号，添加到集合中)
                    String routeListNum = lrpLink.getLeftBsoID();
                    TechnicsRouteListIfc routeListIfc = (TechnicsRouteListIfc)pservice.refreshInfo(routeListNum);

                    TechnicsRouteListMasterInfo masterinfo = (TechnicsRouteListMasterInfo)routeListIfc.getMaster();
                    //获得现有的最新小版本
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
     * 生成综合路线
     * @param mDepartment String 制造单位的id
     * @param cDepartment String 装配单位的id
     * @param parts Vector 子件集合
     * @param products Vector 夫件集合
     * @param showAll boolean 是否显示所有产品结构，只有当生成综合路线客户端没有添（右边）领部件条件时为true @
     * @return Collection 结果集合。每个集合项是一个Vector.依次放零部件生命周期，零部件id，零部件编号，零部件名称， 版本，父件号，零部件在产品中数量集合，路线数量，路线集合（其中路线集合每个条目包括制造装配2中路线）
     */
    private Collection getColligationRoutesDetial(String mDepartment, String cDepartment, Vector parts, Vector products, boolean showAll,String state)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("进入路线服务getColligationRoutesDetial方法 :" + "mDepartment = " + mDepartment + "  cDepartment=" + cDepartment + "  parts=" + parts + "  products  =" + products);
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
                System.out.println("返回路线服务getRouteByPartAndDep方法 :" + "mDepartment = " + mDepartment + "  cDepartment=" + cDepartment + " partMasterID=" + part.getMasterBsoID() + "得到结果树"
                        + routeAndLinks);

                //如果是要显示整个产品结构，当前领部件又没有路线，则，只显示产品结构信息
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
                //获得不重复的父件集合
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
							// 获得现有的最新小版本
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
									make = "主:" + make + ";" + "次:" + sMake;
									zhuang = "主:" + zhuang + "次:" + sZhuang;
								} else {

									make = "主:" + make + ";" + "次:"+ sRoute;
									if(!zhuang.equals(""))
									   zhuang = "主:" + zhuang;
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
						// 获得现有的最新小版本
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
								make = "主:" + make + ";" + "次:" + sMake;
								zhuang = "主:" + zhuang + "次:" + sZhuang;
							} else {

								make = "主:" + mainRoute + ";" + "次:" + sRoute;
								zhuang = "主:" + zhuang;
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
	 * 把路线串添加结果集中
	 * 
	 * @param result
	 *            Vector 结果集
	 * @param map
	 *            HashMap 路线串集合
	 * @return Vector
	 */
    private Vector assembleBranchStr(Vector result, HashMap map)
    {
        if(VERBOSE)
        {
            System.out.println("进入路线服务assembleBranchStr方法 :" + "map = " + map);

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
            Vector makeNodes = (Vector)objs[0]; //制造节点
            RouteNodeIfc asseNode = (RouteNodeIfc)objs[1]; //装配节点
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
                        makeStr = makeStr + "→" + node.getNodeDepartmentName();
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
            System.out.println("返回路线服务assembleBranchStr方法 :" + "makeBranchs = " + makeBranchs);
        }
        return result;
    }

    /**
     * （问题五）zz 周茁添加，用于瘦客户显示路线串 把路线串添加结果集中,瘦客户适用，路线串不经过branchNode直接从branch对象中取出字符串
     * @param result Vector 结果集
     * @param map HashMap 路线串集合
     * @return Vector
     */
    private Vector assembleBranchStrForThin(Vector result, HashMap map)
    {
        if(VERBOSE)
        {
            System.out.println("进入路线服务assembleBranchStr方法 :" + "map = " + map);

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
        if(VERBOSE)
        {
            System.out.println("返回路线服务assembleBranchStr方法 :" + "makeBranchs = " + makeBranchs);
        }
        return result;
    }

    private boolean isParentPart(QMPartIfc sub, QMPartIfc parent)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        return partService.isParentPart(sub, parent);
    }

    /**
     * 计算子件在产品中使用的数量（只计算其父件在产品中的数量）
     * @param parts Vector 子件
     * @param parent QMPartIfc 父件
     * @param product QMPartIfc 产品 @
     * @return int 在产品中使用的数量
     * @see QMPartInfo
     */
    /**
     * delete by guoxl for 实施问题 2010.10.29 public HashMap calCountInProduct(Vector parts, QMPartIfc parent, QMPartIfc product) { if(VERBOSE)
     * System.out.println("进入 calCountInProduct   parts="+parts+" parent="+parent+" product="+product); StandardPartService partService = (StandardPartService) EJBServiceHelper.
     * getService(PART_SERVICE); PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc) partService.findPartConfigSpecIfc(); HashMap map = new HashMap(); List boms = null; List midleBoms = null;
     * try { //long before4 = System.currentTimeMillis(); String[] attrNames = {"quantity"}; boms = partService.setBOMList(product, attrNames, null, null, null, partConfigSpecIfc); // long after4 =
     * System.currentTimeMillis(); // System.out.println("用时calCountInProduct====="+(after4 - before4)); } catch (Exception e) { throw new TechnicsRouteException(e); } try { //long before5 =
     * System.currentTimeMillis(); String[] attrNames = {"quantity"}; midleBoms = partService.setBOMList(parent, attrNames, null, null, null, partConfigSpecIfc); // long after5 =
     * System.currentTimeMillis(); // System.out.println("用时calCountInProduct====="+(after5 - before5)); } catch (Exception ee) { throw new TechnicsRouteException(ee); } for (int i = 0; i <
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
     * 零部件过滤器
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
     * 计算子件在产品中使用的数量（只计算其父件在产品中的数量）
     * @param part QMPartIfc 子件
     * @param parent QMPartIfc 父件
     * @param product QMPartIfc 产品 @
     * @return int 在产品中使用数量
     * @see QMPartInfo
     */
    public int calCountInProduct(QMPartIfc part, QMPartIfc parent, QMPartIfc product)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        if(parent == null)
            return 0;
        String count = (String)partService.getPartQuantity(product, (QMPartMasterIfc)parent.getMaster(), part);
        if(VERBOSE)
            System.out.println(part.getBsoID() + part.getPartName() + "在" + parent.getBsoID() + parent.getPartName() + product.getBsoID() + product.getPartName() + "中的数量==" + count);
        if(count != null && !count.trim().equals(""))
            return new Integer(count).intValue();
        else
            return 0;
    }

    /**
     * 计算子件在产品中使用的数量（只计算其父件在产品中的数量）
     * @param part QMPartIfc 子件
     * @param parent QMPartIfc 父件
     * @param product QMPartIfc 产品 @
     * @return int 计算子件在产品中使用的数量数量
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
            System.out.println(part.getBsoID() + part.getPartName() + "在" + parent.getBsoID() + parent.getPartName() + product.getBsoID() + product.getPartName() + "中的数量==" + count);
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
     * 计算子件在产品中使用的数量
     * @param part QMPartIfc 子件
     * @param parent QMPartIfc 父件 @
     * @return int 数量
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
            System.out.println(part.getBsoID() + part.getPartName() + "在" + parent.getBsoID() + parent.getPartName() + "中的数量==" + count);
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
     * 通过零件，装配单位和制造单位获得路线，零件，关联的集合
     * @param mDepartment String 制造单位的id
     * @param cDepartment String 装配单位的id
     * @param partMasterID String 产品id @
     * @return Collection 集合项为vector，格式为：路线与零部件关联，路线啊，零部件
     */
    /*
     * delete by guoxl end on 2008-12-23(解放实施问题，关于按路线单位搜索) private Collection getRouteByPartAndDep(Vector mDepartments, Vector cDepartments, String partMasterID) { if (VERBOSE) {
     * System.out.println("进入路线服务getRouteByPartAndDep方法 :" + "mDepartment = " + mDepartments + "  cDepartment=" + cDepartments + " partMasterID=" + partMasterID);
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
     * query.setDisticnt(true); if (VERBOSE) { System.out.println("getRouteByPartAndDep 查询条件为： " + query.getDebugSQL()); } return pservice.findValueInfo(query);
     * 
     * }delete end by guoxl end on 2008-12-23(解放实施问题，关于按路线单位搜索)
     */
    //add by guoxl on 2008-12-23(解放实施问题，关于按路线单位搜索)
    private Collection getRouteByPartAndDep(Vector mDepartments, Vector cDepartments, String partMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println("进入路线服务getRouteByPartAndDep方法 :" + "mDepartment = " + mDepartments + "  cDepartment=" + cDepartments + " partMasterID=" + partMasterID);

        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //在三个表中查找数据：１．路线零件关联，２．路线表，３．零件主信息
        QMQuery query = new QMQuery(this.LIST_ROUTE_PART_LINKBSONAME);
        int routeCount = query.appendBso(this.TECHNICSROUTE_BSONAME, true);
        //CCBegin SS21
        //int partCount = query.appendBso("QMPartMaster", true);
        int partCount = query.appendBso("QMPart", true);
        //CCEnd SS21

        QueryCondition routeToLink = new QueryCondition("routeID", "bsoID");
        query.addCondition(0, routeCount, routeToLink);//向第一个表和第二个表添加关联条件routeID=bsoID
        query.addAND();
        QueryCondition partToLink = new QueryCondition("rightBsoID", "bsoID");
        query.addCondition(0, partCount, partToLink);//向第一个表和第三个表添加关联条件rightBsoID=bsoID
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
        //add by guoxl on 2008-12-19(解放实施问题)
        int mNodeCount = query.appendBso(this.ROUTENODE_BSONAME, false);
        //add by guoxl end on 2008-12-19(解放实施问题)
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

                //在表routeNode里查找条件为指定装配单位的集合
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
        //add by guoxl  on 2008-12-19(解放实施问题)
        query.addAND();
        query.addCondition(mNodeCount, routeCount, nodetoRoute);
        //add by guoxl end on 2008-12-19(解放实施问题)
        // query.addOrderBy(partCount, "partNumber");
        query.setDisticnt(true);
        if(VERBOSE)
        {
            System.out.println("getRouteByPartAndDep 查询条件为： " + query.getDebugSQL());
        }

        return pservice.findValueInfo(query);

    }//add end by guoxl on 2008-12-23(解放实施问题，关于按路线单位搜索)

//  CCBegin SS23
  private Collection getRouteByPartAndDepForZC(Vector mDepartments, Vector cDepartments, String partMasterID)throws QMException
  {
      if(VERBOSE)
      {
          System.out.println("进入路线服务getRouteByPartAndDep方法 :" + "mDepartment = " + mDepartments + "  cDepartment=" + cDepartments + " partMasterID=" + partMasterID);

      }
      PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
      //在三个表中查找数据：１．路线零件关联，２．路线表，３．零件主信息
      QMQuery query = new QMQuery(this.LIST_ROUTE_PART_LINKBSONAME);
      int routeCount = query.appendBso(this.TECHNICSROUTE_BSONAME, true);
      int partCount = query.appendBso("QMPart", true);

      QueryCondition routeToLink = new QueryCondition("routeID", "bsoID");
      query.addCondition(0, routeCount, routeToLink);//向第一个表和第二个表添加关联条件routeID=bsoID
      query.addAND();
      QueryCondition partToLink = new QueryCondition("rightBsoID", "bsoID");
      query.addCondition(0, partCount, partToLink);//向第一个表和第三个表添加关联条件rightBsoID=bsoID
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
      //add by guoxl on 2008-12-19(解放实施问题)
      int mNodeCount = query.appendBso(this.ROUTENODE_BSONAME, false);
      //add by guoxl end on 2008-12-19(解放实施问题)
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

              //在表routeNode里查找条件为指定装配单位的集合
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
      //add by guoxl  on 2008-12-19(解放实施问题)
      query.addAND();
      query.addCondition(mNodeCount, routeCount, nodetoRoute);
      //add by guoxl end on 2008-12-19(解放实施问题)
      // query.addOrderBy(partCount, "partNumber");
      query.setDisticnt(true);
      if(VERBOSE)
      {
          System.out.println("getRouteByPartAndDep 查询条件为： " + query.getDebugSQL());
      }
      return pservice.findValueInfo(query);

  }//add end by guoxl on 2008-12-23(解放实施问题，关于按路线单位搜索)
//  CCEnd SS23
    
    /**
     * 得到所有零部件的父件，合并相同的
     * @param subs Vector 子件集合 @
     * @return Vector HashMap集合：key:partBsoID,value:QMPartIfc 合并后的父件集合
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
                    System.out.println("福建为0");
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
     * 得到所有子件，合并相同的
     * @param products Vector 产品集合 @
     * @return Vector 合并后的子件集合
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
     * 排序添加零部件，按编号
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
     * 根据零部件查找是否有路线
     * @param vec Vector 零件值对象集合 @
     * @return Vector[] 存放了两个vector:<br> 1.successVec vector:存放的是QMPartIfc 零件值对象 <br> 2.failVec vector::存放的是QMPartIfc 零件值对象 <br> 有路线或无路线零件的集合
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
     * 根据零部件查找是否有路线
     * @param partMasterID String 零件ID @
     * @return boolean 有路线返回true 否则返回false
     */
    public boolean isHasRoute(String partMasterID)throws QMException
    {
        if(VERBOSE)
        {
            System.out.println(TIME + "enter routeService's getPartLevelRoutes, partMasterID = " + partMasterID);
        }
        if(partMasterID == null || partMasterID.trim().length() == 0)
        {
            throw new QMException("输入参数不对");
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
        //SQL不能正常处理。
        query.setDisticnt(true);
        //返回ListRoutePartLinkIfc
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
     * 获取子零部件在父零部件中的使用数量。
     * @param parentPartIfc QMPartIfc 父零部件。
     * @param childPartIfc QMPartIfc 子零部件。 @
     * @return String 使用数量。
     * @see QMPartInfo
     */
    private String getPartQuantity(QMPartIfc parentPartIfc, QMPartIfc childPartIfc, List childParts)
    {
        if(parentPartIfc.getPartNumber().equals(childPartIfc.getPartNumber()))
            return "1";
        //获取子零部件统计表。
        Object[] childPartsArray = childParts.toArray();
        //获取指定子零部件的使用数量。
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
     * 获取子零部件在父零部件中的使用数量。
     * @param parentPartIfc QMPartIfc 父零部件。
     * @param middlePartMasterIfc QMPartMasterIfc 中间零部件的主信息。
     * @param childPartIfc QMPartIfc 子零部件。 @
     * @return String 使用数量。
     */
    private String getPartQuantity(QMPartIfc parentPartIfc, QMPartMasterIfc middlePartMasterIfc, QMPartIfc childPartIfc, List childParts, List midleList)throws QMException
    {
        StandardPartService partService = (StandardPartService)EJBServiceHelper.getService(PART_SERVICE);
        //获取当前用户的配置规范。
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

            //获取中间零部件的使用数量。
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
     * 获取符合配置规范的零部件。
     * @param partMasterIfc QMPartMasterIfc 零部件主信息。
     * @param partConfigSpecIfc PartConfigSpecIfc 配置规范。 @
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
     * 用当前用户的配置规范过滤零部件
     * @param masterCol Collection @
     * @return Collection 集合中的类型可参见 ConfigService 服务中<br> filteredIterationsOf(Collection collection, ConfigSpec configSpec) 方法.<br> 过滤后零件的集合
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
     * 判断是否是父件
     * @param maybeChild Vector 零件值对象集合
     * @param maybeParent QMPartIfc @
     * @return Collection vector集合：<br> QMPartIfc是父件的零件集合
     * @see QMPartInfo
     */
    //(问题九)周茁添加 zz 添加集体调用方法，减少客户端调用次数提高性能
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
        //如果partIfc1没有父亲节点，说明partIfc2永远不可能是partIfc1的父亲节点，所以方法
        //永远返回false
        if(temp == null || temp.size() == 0)
        {

            return false;
        }
        for(int i = 0;i < temp.size();i++)
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
        //collection中应该是QMPartIfc的集合
        if(collection != null && collection.size() > 0)
        {
            Iterator iterator = collection.iterator();
            while(iterator.hasNext())
            {
                Object obj = iterator.next();
                if(obj instanceof QMPartIfc)
                {
                    //在查看被用于界面，如果一个父件使用一个子件多次，则只需列出一条记录，不必每次都列出
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
     * 根据指定的QMPartMasterIfc对象， 通过两个表联合查询获得在PartUsageLink表中与QMPartMasterIfc关联的最新版本 的QMPartIfc对象的集合。
     * @param partMasterIfc :QMPartMasterIfc对象。
     * @return collection 和partMasterIfc在PartUsageLink表中关联的最新版本QMPartIfc对象的集合。 @
     */
    private Collection getUsedByParts(QMPartMasterIfc partMasterIfc)throws QMException
    {

        QMQuery query = new QMQuery("QMPart", "PartUsageLink");
        //按最新版本查询，返回条件cond
        QueryCondition condition1 = VersionControlHelper.getCondForLatest(true);
        //根据传入的数值确定表的位置，并向其添加查询条件,这里0表示是第一个表:是对第一个表添加查询条件
        query.addCondition(0, condition1);
        query.setChildQuery(false);
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();

        //根据一个值对象partMasterIfc和本值对象在关联类中的关联角色名"uses", 浏览关联的另一边的bso对象
        //query - 查询的过滤条件
        Collection outcoll = (Collection)pservice.navigateValueInfo(partMasterIfc, "uses", query);
        // System.out.println("getUsedByParts dejieguo outcoll " + outcoll.size()+"partMasterIfc=="+partMasterIfc.getBsoID());
        return outcoll;
    }

    /**
     * 工艺路线的重命名 路线表不只TechnicsRouteListMaster记名称和编号,TechnicsRouteList也记名称和编号
     * @param routelist TechnicsRouteListIfc 路线表值对象 @
     * @return TechnicsRouteListMasterIfc 路线表值对象
     * @see TechnicsRouteListInfo
     */
    //    （问题十）增加工艺路线的重命名功能 周茁添加 zz 20061214
    //     flag 是否修改路线表编号
    //CR9 begin
    public TechnicsRouteListMasterIfc rename(TechnicsRouteListMasterIfc routelist)throws QMException
    {
        if(routelist == null)
        {
            return null; //如果所给参数为空返回空
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
                    //判断唯一性。
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
     * 专为导出物料青单所用
     * @param part QMPartIfc 零件值对象 @
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
                // System.out.println("查询到的关联对象 " + cols.size());
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
     * 分级物料清单的显示。
     * @param partIfc :QMPartIfc 最顶级的部件值对象。
     * @param attrNames :String[] 定制的属性，可以为空。
     * @param affixAttrNames : String[] 定制的扩展属性名集合，可以为空。
     * @param configSpecIfc :PartConfigSpecIfc 配置规范。 @
     * @return Vector 返回结果是vector,其中vector中的每个元素都是一个集合： 0：当前part的BsoID； 1：当前part所在的级别，转化为字符型； 2-...：可变的：如果没有定制属性，2：当前part的编号，3：当前part的名称 4：当前part被最顶层部件使用的数量，转化为字符型， 5：当前part的版本号，6：当前part的视图；
     * 如果定制了属性：按照所有定制的属性加到结果集合中。 本方法调用了递归方法： fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc, PartUsageLinkIfc partLinkIfc, int parentQuantity);
     * @see QMPartInfo
     * @see PartConfigSpecInfo
     */

    public Vector setMaterialList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, PartConfigSpecIfc configSpecIfc)throws QMException
    {
        System.out.println("进入服务 setMaterialList");
        Vector vector = null;
        try
        {
            //            PartDebug.trace(this, PartDebug.PART_SERVICE,
            //                            "setMaterialList begin ....");
            PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
            int level = 0;
            PartUsageLinkIfc partLinkIfc = new PartUsageLinkInfo();
            float parentQuantity = 1.0f;

            //记录数量和编号在排序中所在的位置。
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
            //把结果集合中的第一个元素的使用的数量变成""
            if(vector != null && vector.size() > 0)
            {
                Object[] first = (Object[])vector.elementAt(0);

                //如果输出属性有数量，则将数量设置为空。
                if(quantitySite > 0)
                {
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
            //上面对firstElement中的所有的元素组装完毕，下面需要对firstElement ->Object[]
            //再添加到vector中的第一个位置：
            Object[] tempArray = new Object[firstElement.size()];
            for(int i = 0;i < firstElement.size();i++)
            {
                tempArray[i] = firstElement.elementAt(i);
            }
            vector.insertElementAt(tempArray, 0);
            //2003.09.12为了防止"null"进入到返回值中：可以对vector中的每个元素判断
            //其是否为null, 如果是null，就转化为""
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

    //add by guoxl on 2008.03.21(part的物料清单功能调用此服务所以修改此处)
    /**
     * 获取part的替换件的编号（名称）以字串形式返回
     * @param part 零部件的
     * @return 换件的编号（名称）
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
     * 获取part的结构替换件的编号（名称）以字串形式返回
     * @param part 零部件的
     * @return 结构换件的编号（名称）
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
     * 私有方法。被setMaterialList()方法调用，实现定制分级物料清单的功能。 返回结果是vector,其中vector中的每个元素都是一个集合： 0：当前part的BsoID； 1：当前part所在的级别，转化为字符型； 2-...：可变的：如果没有定制属性，2：当前part的编号，3：当前part的名称 4：当前part被最顶层部件使用的数量，转化为字符型，
     * 5：当前part的版本号，6：当前part的视图； 如果定制了属性：按照所有定制的属性加到结果集合中。
     * @param partIfc :QMPartIfc 当前的部件。
     * @param attrNames :String[] 定制的属性集合，可以为空。
     * @param affixAttrNames :String[] 定制的扩展属性名集合，可以为空。
     * @param configSpecIfc :PartConfigSpecIfc 配置规范。
     * @param level :int 当前part所在的级别。
     * @param partLinkIfc :PartUsageLinkIfc 记录了当前part和起父亲节点的使用关系的值对象。
     * @param parentQuantity :int 当前part的父亲节点被最顶级部件使用的数量。 @
     * @return Vector
     */

    private Vector fenji(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, PartConfigSpecIfc configSpecIfc, int level, PartUsageLinkIfc partLinkIfc, float parentQuantity)throws QMException

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
            }else
            {
                //如果只有定制的扩展属性不为空的时候：
                tempArray = new Object[3 + affixAttrNames.length];
            }
        }else
        {
            if(affixAttrNullTrueFlag)
            {
                //如果只有定制的普通属性不为空的时候：
                tempArray = new Object[3 + attrNames.length];
            }else
            {
                //如果两个定制的属性集合都不为空的时候：
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
        tempArray[1] = new Integer(numberSite);//编号属性所在的位。
        tempArray[2] = new Integer(level);//level的初始值为0。
        //        tempArray[2] = partIfc.getPartNumber();
        //        tempArray[3] = partIfc.getPartName();
        //如果level = 0 说明是最顶级的部件。
        /**
         * if (level == 0) { parentQuantity = 1f; String quan = "1"; tempArray[4] = new String(quan); } else { //可不可以这样：不再保存parentBsoID,而是保存PartUsageLinkIfc，到循环参数中来。 //这样可以省略再查找的过程。QMPartUsageLinkIfc
         * partLinkIfc parentQuantity = partLinkIfc.getQuantity();//parentQuantity*partLinkIfc.getQuantity(); String tempQuantity = String.valueOf(parentQuantity); if (tempQuantity.endsWith(".0"))
         * tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2); tempArray[4] = tempQuantity; }
         */
        //判断是否需要定制属性进行输出。
        if(attrNullTrueFlag)
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
            for(int j = 0;j < attrNames.length;j++)
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
                        tempArray[3 + j] = getAlternates(partIfc);
                    }
                    //如果属性是结构替换件
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
                        //如果level = 0 说明是最顶级的部件。
                        if(level == 0)
                        {
                            parentQuantity = 1f;
                            String quan = "1";
                            tempArray[3 + j] = new String(quan);
                        }else
                        {
                            //可不可以这样：不再保存parentBsoID,而是保存PartUsageLinkIfc，到循环参数中来。
                            //这样可以省略再查找的过程。QMPartUsageLinkIfc partLinkIfc
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
                        // tempArray[3+ attrNames.length-1] = "地地道道";
                        TechnicsRouteService technicsrouteservice = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
                        String routeString = technicsrouteservice.getMaterialRoute(partIfc);
                        tempArray[3 + j] = routeString;
                    }
                    //zz end

                    else
                    {
                        attr = (attr.substring(0, 1)).toUpperCase() + attr.substring(1, attr.length());
                        attr = "get" + attr;
                        //现在的attr就是"getProducedBy"等固定的字符串了：
                        try
                        {
                            Class partClass = Class.forName("com.faw_qm.part.model.QMPartInfo");
                            Method method1 = partClass.getMethod(attr, null);
                            Object obj = method1.invoke(partIfc, null);
                            //现在需要判断obj是否为null, 如果为null, attrNames[i] = "";
                            //如果obj不为null, 而且是String, attrNames[i] = (String)obj;
                            //如果obj是枚举类型，attrNames[i] = (EnumerationType)obj.getDisplay();
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
                    //取temp中的元素进行循环，temp[k][0]是PartUsageLinkIfc,
                    //temp[k][1]是QMPartIfc
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
     * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。 本方法调用了bianli()方法实现递归。 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息： 1、如果不定制属性： BsoID，是（否）可分（"true","false"）、编号、名称、数量（转化为字符型）、版本和视图； 2、如果定制属性：
     * BsoID，是（否）可分、其他定制属性。
     * @param partIfc :QMPartIfc 指定的零部件的值对象。
     * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
     * @param affixAttrNames : String[] 定制的要输出的扩展属性集合；可以为空。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理。
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理。
     * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的对当前零部件的筛选条件。 @
     * @return Vector 存放Object[] :tempArray[i] 零部件的统计表信息。
     * @see QMPartInfo
     * @see PartConfigSpecInfo
     */
    public Vector setBOMList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, String source, String type, PartConfigSpecIfc configSpecIfc)throws QMException
    {
        PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
        Vector vector = new Vector();
        float parentQuantity = 1.0f;

        //记录数量和编号在排序中所在的位置。
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
        //下面对vector中的元素进行合并数量的处理。...........
        Vector resultVector = new Vector();
        for(int i = 0;i < vector.size();i++)
        {
            Object[] temp1 = (Object[])vector.elementAt(i);
            //2003.09.12为了防止"null"进入到返回值中：可以对temp1中的每个元素判断
            //其是否为null, 如果是null，就转化为""
            for(int j = 0;j < temp1.length;j++)
            {
                if(temp1[j] == null)
                {
                    temp1[j] = "";
                }
            }
            //需求是按照partNumber进行合并的！！！
            String partNumber1 = (String)temp1[numberSite];
            boolean flag = false;
            for(int j = 0;j < resultVector.size();j++)
            {
                Object[] temp2 = (Object[])resultVector.elementAt(j);
                String partNumber2 = (String)temp2[numberSite];
                if(partNumber1.equals(partNumber2))
                {
                    flag = true;

                    //如果数量的位置大于0，说明输出的属性中有数量，然后将相同零部件
                    //的数量合并。
                    if(quantitySite > 0)
                    {
                        //把temp2和temp1中的元素进行合并，放到resultVector中去。:::
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
            //把第一个元素的数量改成""
            Object[] firstObj = (Object[])resultVector.elementAt(0);

            //如果输出属性有数量，则将数量设置为空。
            if(quantitySite > 0)
            {
                firstObj[quantitySite] = "";
            }
            resultVector.setElementAt(firstObj, 0);
        }
        //这里才保存最后最后的结果：
        Vector result = new Vector();
        //然后，这里还需要对最后的返回值集合按照当前的source和type进行过滤：
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
            for(int i = 0;i < attrNames.length;i++)
            {
                String attr = attrNames[i];
                ssss = QMMessage.getLocalizedMessage(PARTRESOURCE, attr, null);
                firstElement.addElement(ssss);
            }
        }
        //上面对firstElement中的所有的元素组装完毕，下面需要对firstElement ->Object[]
        //再添加到vector中的第一个位置：
        Object[] tempArray = new Object[firstElement.size()];
        for(int i = 0;i < firstElement.size();i++)
        {
            tempArray[i] = firstElement.elementAt(i);
        }
        result.insertElementAt(tempArray, 0);
        return result;
    }

    /**
     * 本方法被setBOMList所调用，实现递归调用的功能。 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息： 1、如果不定制属性： BsoID，是（否）可分（"true","false"）、编号、名称、数量（转化为字符型）、版本和视图； 2、如果定制属性： BsoID，是（否）可分、其他定制属性。
     * @param partIfc :QMPartIfc 指定的零部件的值对象。
     * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
     * @param affixAttrNames : String[] 定制的要输出的扩展属性的集合，可以为空。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理。
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理。
     * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的筛选条件。
     * @param parentQuantity :float 使用了当前部件的部件被最顶级部件所使用的数量。
     * @param partLinkIfc :PartUsageLinkIfc 连接当前部件和其父部件的关联关系值对象。 @
     * @return Vector
     */
    private Vector bianli(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, String source, String type, PartConfigSpecIfc configSpecIfc, float parentQuantity,
            PartUsageLinkIfc partLinkIfc)throws QMException
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
            for(int i = 0;i < resultArray.length;i++)
            {
                boolean isHasSubParts = true; //false
                Object obj = resultArray[i];
                if(obj instanceof Object[])
                {
                    Object[] obj1 = (Object[])obj;
                    if(obj1[1] instanceof QMPartIfc)
                    {
                        //这一步相当于增加了一个对当前零部件的所有儿子零部件的过滤条件.
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
        }else
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
        if(attrNullTrueFlag)
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
            for(int i = 0;i < attrNames.length;i++)
            {
                String attr = attrNames[i];
                attr = attr.trim();
                if(attr != null && attr.length() > 0)
                {
                    //modify by liun 2005.3.25 改为从关联中得到单位
                    //modify by guoxl on 2008.03.21(part的物料清单功能用到此处所以修改)
                    if(attr.equals("alternates"))
                    {
                        tempArray[3 + i] = getAlternates(partIfc);
                    }
                    //如果属性是结构替换件
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
                        //需要先判断partLinkIfc是否是持久化的，如果不是，parentQuantity = 1.0
                        //如果是:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
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
                        //现在的attr就是"getProducedBy"等固定的字符串了：
                        try
                        {
                            Class partClass = Class.forName("com.faw_qm.part.model.QMPartInfo");
                            Method method1 = partClass.getMethod(attr, null);
                            Object obj = method1.invoke(partIfc, null);
                            //现在需要判断obj是否为null, 如果为null, attrNames[i] = "";
                            //如果obj不为null, 而且是String, tempArray[i + 5] = (String)obj;
                            //如果obj是枚举类型，tempArray[i + 5] = (EnumerationType)obj.getDisplay();
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
        //对已经过滤处理的当前输入的零部件的所有子零部件进行递归处理::::
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
     * 检入工艺路线表
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
            //如果检入不成功抛出异常
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
     * 检出工艺路线表
     */
    public Collection checkOutTechRouteList(TechnicsRouteListIfc[] checkoutinfo, boolean flag)throws QMException
    {
        //  PersistService service = (PersistService)EJBServiceHelper.getPersistService();
        // 获得workable服务实例

        WorkInProgressService workservice = (WorkInProgressService)EJBServiceHelper.getService("WorkInProgressService");
        //begin CR31
        // 获得检出文件夹
        //FolderIfc folderIfc = workservice.getCheckoutFolder();
        //add by guo
//        Collection techRouteVec = (Collection)workservice.checkOut(checkoutinfo, flag);
        Collection techRouteVec=new Vector();
       
        for(int i=0;i<checkoutinfo.length;i++){
        	
        	TechnicsRouteListIfc trl=checkoutinfo[i];
            workservice.checkout(trl);//检出
            WorkableIfc work=workservice.workingCopyOf(trl);//获得副本
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
     * 修订工艺路线表
     */
    public VersionedIfc reviseTechnicsRouteList(VersionedIfc reviseIfc, String foldLocation, String lifecycleTemName, String projectName)throws QMException
    {
        // 获得副本对象设置文件夹及生命周期
        // 设置使用资源

        PersistService persistservice = (PersistService)EJBServiceHelper.getPersistService();
        EnterpriseService enService = (EnterpriseService)EJBServiceHelper.getService("EnterpriseService");
        VersionedIfc reviseroute = (VersionedIfc)enService.revise(reviseIfc, foldLocation, lifecycleTemName, projectName, true);
        //复制新的关联。
        Collection coll = getRouteListLinkParts((TechnicsRouteListIfc)reviseIfc);
        for(Iterator iter = coll.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            if(VERBOSE)
            {
                System.out.println("原关联：listLinkInfo.bsoID = " + listLinkInfo.getBsoID());
            }
            ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkInfo)((ListRoutePartLinkInfo)listLinkInfo).duplicate();
            if(VERBOSE)
            {
                System.out.println("新关联：listLinkInfo1.bsoID = " + listLinkInfo1.getBsoID());
            }
            listLinkInfo1.setRouteListID(reviseroute.getBsoID());
            //将alterStatus设置成INHERIT状态。
            listLinkInfo1.setAlterStatus(INHERIT);//0
            //将adoptStatus设置成CANCEL状态。
            // （问题七） 2006 09 04 zz 周茁修改 红塔提出修订后新版路线表里的关联零部件的路线状态原为采用的变为取消了，
            // 根据需求应该复制前一版本的状态 所以不将状态设为取消
            // listLinkInfo1.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
            //设置initialUsed为新的大版本。
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
            //有可能零件未使用路线。
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
     * 根据变更通知书的BsoID获得与其关联的零部件集合
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
     * @param param 二维数组，5个元素。
     * @J2EE_METHOD -- findChangeOrders 获得变更通知书。搜索范围：编号、名称、创建者、创建时间。
     * @return collection 变更通知书值对象集合
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

                // 修改字符串，用来和数据库中的时间字符串进行比较
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
            //System.out.println("添加零件SQL语句：" + query.getDebugSQL());

            result = pservice.findValueInfo(query, false);

        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param param 二维数组，5个元素。
     * @J2EE_METHOD -- findChangeOrders 获得变更通知书。搜索范围：编号、名称、创建者、创建时间。
     * @return collection 变更通知书值对象集合
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
            //向查询条件增加最新版本附加SQL条件
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
     * 根据采用通知书的BsoID获得与其关联的零部件集合
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
        // 编号
        String number = (String)param[0][0];
        boolean numberFlag = ((Boolean)param[0][1]).booleanValue();
        // 名称
        String name = (String)param[1][0];
        boolean nameFlag = ((Boolean)param[1][1]).booleanValue();
        // 制造路线
        String makeroute = (String)param[2][0];
        boolean makerouteFlag = ((Boolean)param[2][1]).booleanValue();
        // 路线的判断条件
        int ifindex = (Integer)param[3][0];
        // 装配路线
        String assembleroute = (String)param[4][0];
        boolean assemblerouteFlag = ((Boolean)param[4][1]).booleanValue();
        // 制造路线和装配路线都为空，只搜零部件
        if(makeroute.equals("") && assembleroute.equals(""))
        {
            result1 = findQMPartByPart(number, name, numberFlag, nameFlag);
        }
        // 编号名称都为空，只根据路线搜零部件
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
     * 根据路线ID获得零部件
     * @param routeIDVec 路线集合
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
                //System.out.println("添加零件SQL语句：" + query.getDebugSQL());
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
        // 结果不重复
        query.setDisticnt(true);
        //System.out.println("添加零件SQL语句：" + query.getDebugSQL());
        result = (Collection)pservice.findValueInfo(query, false);
        Iterator iterator = result.iterator();
        while(iterator.hasNext())
        {
            TechnicsRouteBranchInfo techroutebranch = (TechnicsRouteBranchInfo)iterator.next();
            // 获得路线串
            String routestr = techroutebranch.getRouteStr();
            int index = routestr.indexOf("=");
            //CCBegin SS19
            //String make = routestr.substring(0, index);
            //String assemble = routestr.substring(index + 1, routestr.length());
            String make = "";
            String assemble = "";
            if(index==-1)
            {
            // 获得制造路线
            make = routestr;
          }
          else
          {
            // 获得制造路线
            make = routestr.substring(0, index);
            // 获得装配路线
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
                { // 条件为“与”的时候 需要同时符合
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

                //                    //都选中‘非’
                //                if(!makerouteFlag && !assemblerouteFlag)
                //                {
                //                    //若果装配路线为空并且选中‘非’
                //                    if(!makeroute.equals("") && assembleroute.equals(""))
                //                    {
                //                        if(make.indexOf(makeroute) == -1)
                //                        {
                //                            routeIDVec.add(techroutebranch.getRouteInfo());
                //                        }
                //                    } //若果制造路线为空并且选中‘非’
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
                //               //都没选中‘非’
                //                else if(makerouteFlag && assemblerouteFlag)
                //                {
                //                    if(make.indexOf(makeroute) != -1 && assemble.indexOf(assembleroute) != -1)
                //                    {
                //                        routeIDVec.add(techroutebranch.getRouteInfo());
                //                    }
                //                }
                //                //有一个选中‘非’
                //                else 
                //                {
                //                    //选中装配路线的‘非’
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
            //System.out.println("添加零件SQL语句：" + query.getDebugSQL());
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
     * 搜索零部件的所有子件,按结构添加时使用
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
     * 得到零部件的所有父件以及路线情况
     * @param partmaster QMPartMasterIfc，listID String
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
            //获取生效路线  只有一条
            Collection cols = this.findPartEffRoute(parentinfo.getMasterBsoID());
            if(cols == null || cols.size() == 0)
            {
                temp[2] = null;
                temp[3] = null;
            }
            Iterator i = cols.iterator();
            ListRoutePartLinkInfo info = null;
            TechnicsRouteListInfo routelist = null;

            //遍历过滤，如果当前路线表中有路线，则取当前路线表中的，够则找最新的。另外，不要临准的
            if(i.hasNext())
            {
                Object[] objss = (Object[])i.next();
                info = (ListRoutePartLinkInfo)objss[0];
                routelist = (TechnicsRouteListInfo)objss[1];
                //if(routelist1.getRouteListState().equals("临准"))
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
                    Vector makeNodes = (Vector)objs1[0]; //制造节点
                    RouteNodeIfc asseNode = (RouteNodeIfc)objs1[1]; //装配节点
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
                        makeStr = makeStr + "(临时)";
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
     * 查找路线表和关联对象
     * @param part
     * @return
     */
    private Collection getRoutesAndLinks(QMPartIfc part)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //获得路线表和零件路线的关联
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
        int k = query.appendBso(this.ROUTELIST_BSONAME, true);
        //part的master的id
        QueryCondition cond3 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, part.getMasterBsoID());
        query.addCondition(0, cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        query.addCondition(0, cond4);
        query.addAND();
        //路线不为空
        QueryCondition cond5 = new QueryCondition("routeID", QueryCondition.NOT_NULL);
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
     * 发布路线方法
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
            throw new QMException("此艺准已经发布过路线，不能重新发布路线！");
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
            
                throw new QMException("此艺准中的生命周期模板中没有已发布状态，不能发布路线！");
            }
            for(int i = 0, j = linkCol.size();i < j;i++)
            {
                ListRoutePartLinkIfc linkIfc = (ListRoutePartLinkIfc)linkCol.elementAt(i);
                if(linkIfc.getReleaseIdenty() == -1)
                {
                    //如果该艺准中零件没有发布过路线，则设置发布标识为1，并且设置生效时间
                    linkIfc.setReleaseIdenty(1);
                    linkIfc.setEfficientTime(new Timestamp(System.currentTimeMillis()));
                    pservice.updateValueInfo(linkIfc);

                    //查找零件在其他艺准中有没有生效路线，有则将其置为失效，并且设置失效日期
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
                        //如果真正和产品相关，则取消此件在此产品中的路线
                        if(linkIfc.getProductIdenty() == true)
                        {
                            //添加产品信息
                            query1.addAND();
                            String productID = linkIfc.getProductID();
                            QueryCondition condPn = new QueryCondition("productID", QueryCondition.EQUAL, productID);
                            query1.addCondition(condPn);
                        }
                    }
                    if(routeMode.equals("parentRelative") || routeMode.equals("productAndparentRelative"))
                    {
                        //添加父件信息
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
     * 查找指定零件的生效路线、失效路线 20120110 徐春英 add
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
     * 判断要检入的路线表中的零部件是否都有路线
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
            // DialogFactory.showInformDialog(null, "路线表"+listname+"含有没有路线的零部件，不能检入！");
            throw new QMException("路线表" + listname + "含有没有路线的零部件，不能检入！");
        }else
        {
            flag = true;
        }

        return flag;
    }

    //CR17 end
    //CR18 begin
    /**
     * 复制自搜索含有有效路线的零部件
     * @param param
     * @return
     */
    public Vector copyFromByQMPart(Object[][] param)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        Collection result = null;
        Collection result1 = null;
        Vector linkVec = new Vector();
        // 编号
        String number = (String)param[0][0];
        boolean numberFlag = ((Boolean)param[0][1]).booleanValue();
        // 名称
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
     * 获得零件生效路线 20120112 徐春英 add
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
        //获得路线表和零件路线的关联
        QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
        int j = query.appendBso(TECHNICSROUTE_BSONAME, false);
        int k = query.appendBso(this.ROUTELIST_BSONAME, true);
        //part的master的id
        QueryCondition cond1 = new QueryCondition(RIGHTID, QueryCondition.EQUAL, partMasterID);
        query.addCondition(0, cond1);
        //        query.addAND();
        //        QueryCondition cond2 = new QueryCondition("alterStatus", QueryCondition.EQUAL, ROUTEALTER);
        //        query.addCondition(0, cond2);
        query.addAND();
        //路线不为空
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
        //SQL不能正常处理。
        query.setDisticnt(true);
        //返回ListRoutePartLinkIfc
        if(VERBOSE)
        {
            System.out.println(TIME + "routeService's getPartLevelRoutes SQL = " + query.getDebugSQL());
        }
        return pservice.findValueInfo(query, false);

    }

    /**
     * 保存新的路线（保存典型工艺使用）
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
     * 保存典型工艺
     * @param modelroute
     */
    public void saveModelRoute(ModelRouteInfo modelroute)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        pservice.saveValueInfo(modelroute);
    }

    /**
     * 查找是否存在典型工艺
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
     * 删除路线，路线节点等信息
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
     * 通过路线ID获得主要路线次要路线等
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
            //获得路线表和零件路线的关联
            QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);
            QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, modelRoute.getRightBsoID());
            query.addCondition(cond);
            //升序
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
     * 查询典型路线需要
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
            //获得路线表和零件路线的关联
            QMQuery query = new QMQuery(TECHNICSROUTEBRANCH_BSONAME);
            QueryCondition cond = new QueryCondition("routeID", QueryCondition.EQUAL, modelRoute.getRightBsoID());
            query.addCondition(cond);
            //升序
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
     * 查看典型工艺
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
                	//根据bsoid获取
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
     * 应用典型工艺
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
     * 创建快捷路线对象
     */
    public void saveShortCutRoute(List list, String userID)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        //删除旧的快捷键值
        deleteShortCutRoute(userID);
        //创建新的快捷键值
        for(int i = 0, j = list.size();i < j;i++)
        {
            ShortCutRouteInfo shortCutRoute = (ShortCutRouteInfo)list.get(i);
            pservice.saveValueInfo(shortCutRoute);
        }
    }

    /**
     * 通过用户名查找快捷路线
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
     * 删除旧的快捷键值
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
     * 通过零件主信息ID获取符合配置规范的零件
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
	 * 获得产品中使用零件的信息（包括编号、名称、路线、产品中使用数量信息）
	 * 
	 * @param productIDs
	 *            产品id串
	 * @return
	 */
    public Map getProductRelations(String productIDs)throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        StringTokenizer token = new StringTokenizer(productIDs, ";");
        //key:零件号 value:Map( key:产品id value:String[]{ 零件名，路线，数量} )
        Map mapPart = new HashMap();
        while(token.hasMoreTokens())
        {
            String productID = token.nextToken();
            QMPartMasterIfc product = (QMPartMasterIfc)pservice.refreshInfo(productID);
            //}

            //        for(int i = 0; i < list.size(); i++)
            //        {
            //            //产品
            //            QMPartMasterIfc master = (QMPartMasterIfc)list.get(i);

            QMQuery query = new QMQuery(LIST_ROUTE_PART_LINKBSONAME);
            QueryCondition cond = new QueryCondition("productID", QueryCondition.EQUAL, product.getBsoID());
            query.addCondition(cond);
            query.addAND();
            //有可能零件未使用路线。
            QueryCondition cond1 = new QueryCondition("alterStatus", QueryCondition.NOT_EQUAL, PARTDELETE);
            query.addCondition(cond1);
            query.addOrderBy("rightBsoID", false);
            Collection coll = pservice.findValueInfo(query);
            Iterator it = coll.iterator();
            while(it.hasNext())
            {
                ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)it.next();
                String partNum = link.getPartMasterInfo().getPartNumber();
                //每个零件对应的Map
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
     * 通过原本路线和副本对象中的零件获得原本路线与该零件的关联
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
            //零部件关联
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
    	      throw new QMException("路线表应该是一级路线。");
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
    	      // 根据工艺路线ID获得工艺路线分枝相关对象。
    	      //（问题三）生成报表性能优化
    	      //Map routeMap = getRouteBranchs(linkInfo.getRouteID()); //原代码
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
     * 获得指定零件在特定配置规范的最新小版本 20061209 liuming add
     * @param master QMPartMasterIfc
     * @param configSpecIfc 如果configSpecIfc=null，则获得当前用户的配置规范
     * @return QMPartIfc 如果当前用户在配置规范下没有查看该零件权限或没有版本，则返回null
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
        //获得当前用户的配置规范
        configSpecIfc = getCurrentUserConfigSpec();
      }
      ConfigService service = (ConfigService) EJBServiceHelper.getService(
          "ConfigService");

      //{{{{{{获得零件的最新版本
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
     * 获取当前用户的配置规范，如果用户是首次登陆系统，则构造默认的“工程视图标准”配置规范。
     * @throws QMException 使用ExtendedPartService时会抛出。
     * @return PartConfigSpecIfc 标准配置规范。
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
        ViewObjectIfc view = viewService.getView("工程视图");
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
    /**
     * 收集报表数据 20061209 liuming add
     * @param routeListID String
     * @throws QMException
     */
    public ArrayList gatherExportData(String routeListID) throws
        QMException {
      return ReportLevelOneUtil.getAllInformation2(routeListID);
    }
    //CCEnd SS1
    
     /**
     * 获得零件在产品中的使用数量
     * @param partVec 零件集合
     * @param pruduct 用于产品
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
    
    
    //艺准通知书在批准后设置零部件的状态
    /**
     * 艺准通知书被批准后，将路线状态为“艺试准”的关联的零部件状态设置为试制状态
     * 当路线状态为其他的状态时，将关联的零部件状态设置为生产准备状态
     * @author 王红莲
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
              myRouteList.getRouteListState().equals("艺试准")) {
            for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
              ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
                  iter.next();
                          
              //CCBegin by liunan 2011-12-15 设置零件状态，根据branchid获得零部件设状态。
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
                  myRouteList.getRouteListState().equals("废弃")){
          	for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
                  ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
                      iter.next();
                  
                  //CCBegin by liunan 2011-12-15 设置零件状态，根据branchid获得零部件设状态。
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
              //CCBegin by liunan 2011-12-15 设置零件状态，根据branchid获得零部件设状态。
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
     * 获得指定零件在特定配置规范的最新小版本 20061209 liuming add
     * @param partMasterBsoID
     * @param configSpecIfc 如果configSpecIfc=null，则获得当前用户的配置规范
     * @return QMPartIfc 如果当前用户在配置规范下没有查看该零件权限或没有版本，则返回null
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
     * 对于给定的零部件，获取它的构造规则，该规则关联的EPM文档不能是工作副本.
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
     * 根据艺毕通知书的BsoID获取关联的工艺路线
     *@author 王红莲
     *@version 1.0
     * @param routeCompleteBsoID 艺毕通知书的BsoID
     * @return 工艺路线集合
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
     * 设置艺毕通知书关联的零部件的生命周期状态
     * 将零部件的生命周期状态设置为生产状态
     * @author 王红莲
     * @version 1.0
     * @param routeListCompleteID 艺毕通知书的BsoID
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
            routeListComplete.getCompletType().equals("艺准")) {
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
              //设置零部件关联的CAD文档的生命周期状态
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
            routeListComplete.getCompletType().equals("零件")) {
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
            //设置零部件关联的CAD文档的生命周期状态
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
            //设置零部件关联的CAD文档的生命周期状态
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
              routeListComplete.getCompletType().equals("艺准")) {
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
               partstatez = "已发布";
             }
             if (partstate.toString().equals("BSXTrialProduce")) {
               partstatez = "试制";
             }
             if (partstate.toString().equals("BSXTrialYield")) {
               partstatez = "生产准备";
             }
             if (partstate.toString().equals("BSXYield")) {
               partstatez = "生产";
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
              routeListComplete.getCompletType().equals("零件")) {
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
              partstatez = "已发布";
            }
            if (partstate.toString().equals("BSXTrialProduce")) {
              partstatez = "试制";
            }
            if (partstate.toString().equals("BSXTrialYield")) {
              partstatez = "生产准备";
            }
            if (partstate.toString().equals("BSXYield")) {
              partstatez = "生产";
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
               partstatez = "已发布";
             }
             if (partstate.toString().equals("BSXTrialProduce")) {
               partstatez = "试制";
             }
             if (partstate.toString().equals("BSXTrialYield")) {
               partstatez = "生产准备";
             }
             if (partstate.toString().equals("BSXYield")) {
               partstatez = "生产";
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
          if (completeInfo.getCompletType().equals("艺准")) {
            ArrayList routeListInfos = getCompleteLinkedListByBsoID(completeID);
            for (int i = 0; i < routeListInfos.size(); i++) {
              TechnicsRouteListInfo routeListInfo = (TechnicsRouteListInfo)
                  routeListInfos.get(i);
              QMPartMasterInfo partMasterInfo = (QMPartMasterInfo) pservice.
                  refreshInfo(
                      routeListInfo.getProductMasterID());
              String num_name = "艺准通知书 " + routeListInfo.getRouteListName() +
                  "用于产品 " + partMasterInfo.getPartNumber() + "_" +
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
				// 变速箱 艺毕通知书 增加是否为空的判断
				String masterid = routeList.getProductMasterID();
				QMPartMasterIfc mainProductInfo = null;
				if (masterid != null) {
					mainProductInfo = (QMPartMasterIfc) pservice
							.refreshInfo(masterid);
					useforMainProduct = mainProductInfo.getPartNumber();
				}
				// 原代码如下：
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
					// 变速箱公司 艺毕通知书 增加是否为空的判断
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
					// 原代码如下：
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
      
      //变速箱公司 艺毕通知书增加方法
      /**
       * 获取艺毕通知书的路线名称和说明信息
       * @author 王红莲
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
          nameAndDecs = "艺准通知书 " + name + "说明：" + desc;
          hehe.add(nameAndDecs);
        }
        return hehe;
      }
      /** 20070108 liuming add
       * 获得指定零件的制造路线和装配路线
       * 注意：因为逻辑总成也有可能编制路线，所以不再考虑逻辑总成的问题，视作普通零件
       * @param part QMPartIfc
       * @return String[] 第一个元素为制造路线,第二个元素为装配路线
       * @throws QMException
       */
      public String[] getRouteString(QMPartIfc part) throws QMException {
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
        while (i.hasNext()) {
          Object[] objss = (Object[]) i.next();
          ListRoutePartLinkInfo info1 = (ListRoutePartLinkInfo) objss[0];
          TechnicsRouteListInfo routelist1 = (TechnicsRouteListInfo) objss[1];

          if (routelist1.getRouteListState().equals("临准")) {
            continue;
          }
          if (info1.getRouteID() == null ||
              ( (com.faw_qm.technics.route.model.TechnicsRouteInfo) pservice.
               refreshInfo(info1.getRouteID())).getRouteDescription().substring(3,
              4).equals("Q")) { //如果路线标记为取消，不要
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

        //如果找到了最新的路线表
        if (info != null) {
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
    	      //CCBegin by liunan 2011-12-15 设置零件状态，根据branchid获得零部件设状态。
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
    	            myRouteList.getRouteListState().equals("艺试准")) {
    	          for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
    	            ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
    	                iter.next();
    	            //CCBegin by liunan 2011-12-15 设置零件状态，根据branchid获得零部件设状态。
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
    	              partstatez = "已发布";
    	            }
    	            if (partstate.toString().equals("BSXTrialProduce")) {
    	              partstatez = "试制";
    	            }
    	            if (partstate.toString().equals("BSXTrialYield")) {
    	              partstatez = "生产准备";
    	            }
    	            if (partstate.toString().equals("BSXYield")) {
    	              partstatez = "生产";
    	            }
    	            if (partstate.toString().equals("BSXDisused")) {
    	                partstatez = "废弃";
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
    	                myRouteList.getRouteListState().equals("废弃")){                	
    	        	for (Iterator iter = partsColl.iterator(); iter.hasNext(); ) {
    	                ListRoutePartLinkIfc routePartLink = (ListRoutePartLinkIfc)
    	                    iter.next();
    	                
    	                //CCBegin by liunan 2011-12-15 设置零件状态，根据branchid获得零部件设状态。
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
    	                              partstatez = "已发布";
    	                            }
    	                            if (partstate.toString().equals("BSXTrialProduce")) {
    	                              partstatez = "试制";
    	                            }
    	                            if (partstate.toString().equals("BSXTrialYield")) {
    	                              partstatez = "生产准备";
    	                            }
    	                            if (partstate.toString().equals("BSXYield")) {
    	                              partstatez = "生产";
    	                            }
    	                            if (partstate.toString().equals("BSXDisused")) {
    	                                partstatez = "废弃";
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
    	            
    	            //CCBegin by liunan 2011-12-15 设置零件状态，根据branchid获得零部件设状态。
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
    	                          partstatez = "已发布";
    	                        }
    	                        if (partstate.toString().equals("BSXTrialProduce")) {
    	                        	
    	                          partstatez = "试制";
    	                        }
    	                        if (partstate.toString().equals("BSXTrialYield")) {
    	                          partstatez = "生产准备";
    	                        }
    	                        if (partstate.toString().equals("BSXYield")) {
    	                          partstatez = "生产";
    	                        }
    	                        if (partstate.toString().equals("BSXDisused")) {
    	                            partstatez = "废弃";
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
          //System.out.println("添加零件SQL语句：" + query.getDebugSQL());
          Vector result1 = (Vector)pservice.findValueInfo(query, false);
    	  return result1;
      }
      //CCEnd SS4
      
      // CCBegin SS5
      /**
       * 获得零部件的发布源版本
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
       * @param param 二维数组，5个元素。
       * @J2EE_METHOD -- findChangeOrders 获得变更通知书。搜索范围：编号、名称、创建者、创建时间。
       * @return collection 变更通知书值对象集合
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
              //向查询条件增加最新版本附加SQL条件
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
       * 根据艺准通知书的BsoID获得与其关联的零部件集合
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
                  
                  //System.out.println("添加零件SQL语句：" + query.getDebugSQL());
                  
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
     * 根据零件ID和工艺路线级别获得对应的工艺路线。
     * @param partMasterID String
     * @param level_zh String 路线级别 @
     * @return Collection 数组集合。obj[0]: TechnicsRouteListIfc, <br> obj[1],obj[2]见getRouteAndBrach(routeID),<br> obj[3]:linkInfo。
     */
    public Collection getPartLevelRoutes(String partMasterID, String partID, String level_zh)throws QMException
    {
        if(partMasterID == null || partMasterID.trim().length() == 0 || level_zh == null || level_zh.trim().length() == 0)
        {
            throw new QMException("输入参数不对");
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
        //zz added start (问题零)
        QueryCondition condx = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(0, condx);
        //zz added end (问题零)

        //路线修改时间降序排列。
        //query.addOrderBy(j, "modifyTime", true);
        //SQL不能正常处理。
        query.setDisticnt(true);
        //返回ListRoutePartLinkIfc
        Collection coll = pservice.findValueInfo(query);
        System.out.println("anan coll.size="+coll.size());
        //路线修改时间降序排列。
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
		boolean versionFlag = ((Boolean) param[4][1]).booleanValue();
		//如果版本恰好为最新版，可能搜出个人资料夹的东西。
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
	    QueryCondition cond13 = new QueryCondition("location",QueryCondition.EQUAL,"\\Root\\解放长特分公司\\一级路线");
	    query.addCondition(cond13);
	    query.setDisticnt(true);
		Collection result = pservice.findValueInfo(query);
		//根据工作状态进行过滤。原本副本不能同时存在。获得工艺路线表值对象集合。
		filterByWorkState(result);
		//根据路线表编号升序排列。
		Collection sortedVec = RouteHelper.sortedInfos(result,
				"getRouteListNumber", false);
		return sortedVec;
	}
    /**
     * 根据艺准通知书的BsoID获得与其关联的零部件集合
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
     * 通过零部件ID搜索一级路线
     * @param partID
     * @return
     * @throws QMException
     */
    public String[] findQMPartRouteLevelOne(String partID)throws QMException{
    	 String[] route =new String[2];
    	 PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
         //获得路线表和零件路线的关联
         QMQuery query = new QMQuery("consListRoutePartLink");
         int k = query.appendBso("consTechnicsRouteList",false);
         //part的master的id
         QueryCondition cond3 = new QueryCondition("rightBsoID", QueryCondition.EQUAL, partID);
         query.addCondition(0, cond3);
         query.addAND();
         QueryCondition cond4 = new QueryCondition("alterStatus", QueryCondition.EQUAL, 1);
         query.addCondition(0, cond4);
         query.addAND();
         //路线不为空
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
       * 长特根据路线发布之后设置零件生产准备状态
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
   	             //路线发布之后，零件设置生产准备状态
    	            //只有工作原本才能修改生产准备状态
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
    		throw new QMException("零部件id为空！");
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

        //路线修改时间降序排列。
        query.setDisticnt(true);
        Collection coll = pservice.findValueInfo(query);
        //路线修改时间降序排列。
        Collection sortedVec = RouteHelper.sortedInfos(coll, "getModifyTime", true);
        Collection result = new Vector();
        Iterator iter = sortedVec.iterator();
        //先从二级路线中取
        while(iter.hasNext())
        {
        	System.out.println("取自二级路线！");
        	ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
        	System.out.println("modifyidentity==="+linkInfo.getModifyIdenty());
        	if(linkInfo.getModifyIdenty()!=null&&linkInfo.getModifyIdenty().equals("取消"))
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
        //没有二级路线，就从解放一级路线里取。
        if(sortedVec==null||sortedVec.size()==0)
        {
        	System.out.println("取自解放一级路线！");
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
	 * 自动生成路线表编号
	 * @param routelist
	 * @return 路线表编号
	 * @throws QMException
	 * @author 郭晓亮 for 自动编号
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
			if (routelist.getRouteListState().equalsIgnoreCase("艺准"))
			{
				baseKey = "C准";
			}
			else if (routelist.getRouteListState().equalsIgnoreCase("临准"))
			{
				baseKey = "C临";
			}
			else if (routelist.getRouteListState().equalsIgnoreCase("试制"))
			{
				baseKey = "C试";
			}
			else if (routelist.getRouteListState().equalsIgnoreCase("艺废"))
			{
				baseKey = "C废";
			}
			else if (routelist.getRouteListState().equalsIgnoreCase("前准"))
			{
				baseKey = "C前";
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
  //CCEnd SS41
  
  //CCBegin SS42
  /**
	 * @param param
	 *            二维数组，5个元素。例：obj[0]=编号，obj[1]=true.
	 *            true=是，false=非。数组顺序：编号、名称、级别（汉字）、用于产品、版本号。
	 * @roseuid 402CBAF700CA
	 * @J2EE_METHOD -- findRouteLists 获得工艺路线表。搜索范围：编号、名称、级别、用于产品、版本号。
	 * @return collection 工艺路线表值对象，最新版本。 此时要用ConfigService进行过滤。
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
		// routelist值对象集合。
		Collection result = pservice.findValueInfo(query);
		// 根据工作状态进行过滤。原本副本不能同时存在。获得工艺路线表值对象集合。
		// filterByWorkState(result);
		// 根据路线表编号升序排列。
		// modified by dikef 20060824
		// Collection sortedVec = RouteHelper.sortedInfos(result,
		// "getRouteListNumber", false);
		return result;
	}
  //CCEnd SS42
  
  
  //CCBegin SS43
  /**
   * 判断该改零件的路线中是否包含有该路线单位
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
      //路线串
      Iterator values = map.values().iterator();
      //获得路线分支的方法,返回值是hashmap,值是一个数组,数组的第一个元素是vector
      while (values.hasNext()) {

        Object[] objs1 = (Object[]) values.next();
        Vector makeNodes = (Vector) objs1[0]; //制造节点
        RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //装配节点
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
   * 专为导出物料青单所用
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
          Vector makeNodes = (Vector) objs1[0]; //制造节点
          RouteNodeIfc asseNode = (RouteNodeIfc) objs1[1]; //装配节点
          //首先判断装配结点是不是
          if (asseNode != null && asseNode.getIsTempRoute()) {
            isTemp = true;
          }
          if (makeNodes != null && makeNodes.size() > 0) {
            //对每一个制造节点做
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
          //如果是临时路线,则在制造路线旁边加上临时字样
          if (isTemp) {
            //makeStr = makeStr + "(临时)";
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
          //如果制造路线为空
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
          //获得各个属性名
          String att = atts[j];
          //属性值
          String value = "";
          //如果是艺准编号
          if (att.equals("艺准编号")) {
            value = routelist.getRouteListNumber();
          } //如果是备注
          else if (att.equals("备注")) {
            if (info.getRouteID() != null && info.getRouteID().length() > 0) {
              TechnicsRouteInfo route = (TechnicsRouteInfo) pService.
                  refreshInfo(info.getRouteID(), false);
              value = route.getRouteDescription();
            }
          }
          else if (att.equals("制造路线")) {
            value = routeText;
          }
          else if (att.equals("装配路线")) {
            value = assRouteText;
          }
          else if (att.equals("临时制造路线")) {
            value = temproutetext;
          }
          else if (att.equals("临时装配路线")) {
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
  /**成都批量搜索零部件
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
              System.out.println("输入条件======"+param.length);
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
 * 获得所有的工艺路线表的最新版本，如果有A,B版，返回B版最新小版本。 @
 * @return Collection 存放obj[]集合：<br>obj[0]：工艺路线表值对象。
 */
public boolean searchSameNameList(TechnicsRouteListIfc routeListInfo)throws QMException
{
	boolean flag = false;
    PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
    QMQuery query = new QMQuery(ROUTELISTMASTER_BSONAME);
    Collection coll = pservice.findValueInfo(query);
    //要保存的路线表的名称
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
