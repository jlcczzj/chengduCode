/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * CR1 2011/12/15 徐春英    原因：统一检入控制类和界面    参见：Wip专题更改说明1_张强.doc
 * CR2 2011/12/15 徐春英   原因：修改检入、检出、撤销检出、修订的方式
 * CR3 2011/12/20 吕航  原因：统一重命名控制类和界面
 * CR4 2011/12/21 吕航   原因：增加批量添加方法
 * CR5 2011/12/23 吕航   原因：增加采用变更添加方法
 * CR6 2011/12/26 吕航 原因：增加添加零件新功能
 * CR7 2011/12/30 徐春英  原因：增加保存艺准零件关联及其路线信息的方法
 * CR8 2012/01/06 徐春英  原因：增加发布路线功能
 * CR9 2012/01/11 吕航 原因：增加检入路线表时对零件是否有路线的判断
 * CR10 2012/01/11 吕航 原因：增加复制自查询功能
 * CR11 2012/01/18 xucy 原因：增加快捷路线功能
 * CR12 2012/03/29 吕航原因参见TD5975
 * CR13 2012/04/06 吕航 原因参见TD5938
 * SS1 添加瘦客户界面的报表导出 liujiakun 2013-03-01
 * SS2 变速箱工艺路线搜索零件时显示零件多种信息 pante 2013-11-02
 * SS3 编辑工艺路线界面零部件表中显示的版本值修改为发布源版本  马晓彤 2013-12-16
 * SS4 搜索解放艺准 liunan 2013-12-23
 * SS5 根据解放艺准和路线搜素零部件 liunan 2013-12-23
 * SS6 修改变速箱二级路线中有零件显示不出来的问题，因为conslistpartlink表中rightbsoid里不一定都是qmpartmasterid了，新的是qmpartid了。 liunan 2014-1-23
 * SS7 轴齿零件查看二级路线中看不到艺毕路线 pante 2014-10-09
 * SS8 长特搜索长特一级路线 liuyang 2014-8-25
 * SS9 长特二级路线报表 liuyang 2014-9-3
 * SS10 长特零件根据路线修改成生准状态 文柳 2014-11-17
 * SS11 变速箱处理二级路线历史数据，开放public方法。 liunan 2015-3-12
 * SS12 A005-2014-2957 变速箱新需求：添加零部件，带上最新版路线，先去二级路线，没有的话取解放一级路线。 liunan 2015-7-16
 * SS13 成都工艺路线整合，典型路线报表 liunan 2016-8-15
 * SS14 成都添加通过艺准添加零件 guoxiaoliang 2016-07-18
 * SS15 成都工序添加获得子件 guoxiaoliang 2016-7-28
 * SS16 修改成都批量搜索零部件为精确查找 liuyuzhu 2016-11-10
 * SS17 查找相同名称路线表 liuyuzhu 2016-10-12
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
 * <p>Description: 工艺路线服务端类</p>
 * <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: 一汽启明公司</p>
 * @author 赵立彬
 * @mender skybird
 * @version 1.0
 */

/**
 * 参考文档： Phos-REQ-CAPP-BR02(工艺路线业务规则)V2.0.doc PHOS-REQ-CAPP-SRS-002(制造业企业基础数据管理需求规格说明-工艺路线) V2.0.doc "Phos-CAPP-UC301--Phos-CAPP-UC322"共19个用例。
 */
public interface TechnicsRouteService extends BaseService
{
    //////////////////////////////////以下为测试方法/////////////////////////////////////
    public Object processTest(int i) throws QMException;

    //////////////////////////////////测试方法结束////////////////////////////////////

    //用例1.创建工艺路线表
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC301
    //用例2.更新工艺路线表
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC302

    /**
     * @roseuid 402C5F01005A
     * @J2EE_METHOD -- storeRouteList 保存或更新工艺路线列表。非空检查在值对象中已做。唯一性检查在数据库中设置。 参考文档：创建工艺路线表：PHOS-CAPP-UC301，更新工艺路线表：PHOS-CAPP-UC302
     * 系统根据业务规则PHOS-CAPP-BR201检查要求非空的属性是否为空(E1)，根据业务规则PHOS-CAPP-BR202检查工艺路线表编号是否唯一(E2)。 例外 E1： 系统显示消息：属性名称+(CP00001) E2： 系统显示消息：属性名称+(CW00001)
     * @return 工艺路线表值对象。
     */
    public TechnicsRouteListIfc storeRouteList(TechnicsRouteListIfc routeListInfo) throws QMException;

    //用例3.删除工艺路线表
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC303

    /**
     * @roseuid 40305EC40043
     * @J2EE_METHOD -- deleteRouteList 删除工艺路线表，整个大版本内的小版本全部删除。
     */
    public void deleteRouteList(TechnicsRouteListIfc routeListInfo) throws QMException;

    //用例4.编辑零部件表
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC304

    /**
     * @roseuid 403449360397
     * @J2EE_METHOD -- getOptionalParts
     * @param level 路线级别显示名。例：一级路线。 根据单位名称的代码ID和路线级别获得可选择的零部件。
     * @return Collection 零部件master值对象。 对应规则： 如果当前编辑的工艺路线表是一级工艺路线表，则系统应列出产品结构中的所有零部件；Part: getAllSubParts(QMPartIfc). 如果当前编辑的工艺路线表是二级工艺路线表，则系统应根据路线表的单位属性，列出产品结构中所有一级路线有对应单位的零部件，作为备选零部件。 规则来源：3.4
     * PHOS-CAPP-BR204 从产品结构中添加零部件。 参考文档： 编辑零部件表 版本 <v2.0> 文档编号：PHOS-CAPP-UC304
     * 执行者在更新工艺路线表时，编辑要编制工艺路线的零部件表，可以添加零部件、删除零部件。添加零部件时，可以根据路线表中的"用于产品"的零部件，按其最新结构中提供备选零部件表，用户可以从中选择要添加到工艺路线表中的零部件。如果用户正在编辑的是二级路线表，则备选零部件表不仅应按用于产品的结构，还应按此产品所有零部件中包含本单位路线的一级路线进行进一步筛选，从而获得一份备选零部件表。
     */
    public Collection getOptionalParts(String codeID, String level, String productMasterID) throws QMException;

    //获得最新版本的值对象。
    public BaseValueIfc getLatestVesion(MasterIfc masterInfo) throws QMException;

    /**
     * 获得最新版本值对象的集合
     * @param c master对象集合 @ (问题三)20060629 周茁修改
     */
    public Collection getLatestsVersion(Collection c) throws QMException;

    /**
     * add by guoxl on 20080307 查看一级路线表报表时，序号排序错误 所以添加方法getLatestsVersion1
     */
    public HashMap getLatestsVersion1(Collection c) throws QMException;


    /**
     * 更新父件编号 added by skybird 2005.3.4
     * @param PartsToChange
     * @param routeListInfo1 @
     */
    public void updateListRoutePartLink(Collection PartsToChange, TechnicsRouteListIfc routeListInfo1) throws QMException;

    //用例5.搜索路线表(管理器)
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC305
    //用例6.搜索工艺路线表
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC306

    /**
     * @param param 二维数组，5个元素。例：obj[0]=编号，obj[1]=true. 数组顺序：编号、名称、级别（汉字）、用于产品、版本号。
     * @roseuid 402CBAF700CA
     * @J2EE_METHOD -- findRouteLists 获得工艺路线表。搜索范围：编号、名称、级别、用于产品、版本号。
     * @return collection 工艺路线表值对象，最新版本。 此时要用ConfigService进行过滤。
     */
    public Collection findRouteLists(Object[][] param) throws QMException;

    /**
     * @roseuid 402CBAF700CA
     * @J2EE_METHOD -- findRouteLists 获得工艺路线表。搜索范围：编号、名称、状态、级别（汉字）、用于产品、说明、存放位置、创建日期、创建者、最后更新、版本号。
     * @return collection 工艺路线表值对象，最新版本。 此时要用ConfigService进行过滤。
     */
    public Collection findRouteLists(TechnicsRouteListIfc routelistInfo, String productNumber, String createTime, String modifyTime) throws QMException;

    //用例7.查看工艺路线表
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC307
    //getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)
    ////////////////////////////////////////////////////////////////////////////
    //用例8.生成报表
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC308

    /**
     * 生成报表，导出定制报表。输出方式为本地文件。
     * @param routeListID String 路线表的ID
     * @param exportFormat String 输出格式 @
     * @return String 路线表的名称+编号+的工艺路线报表 20120214 xucy modify
     */
    public String exportRouteList(String routeListID, String exportFormat) throws QMException;

    /**
     * @roseuid 40305F8F0257
     * @J2EE_METHOD -- getFirstLeveRouteListReport 根据工艺路线表ID获得一级工艺路线报表。根据工艺路线表ID获得零件及其工艺路线值对象。 key:零件值对象;value=工艺路线节点数组象集合，obj[0]=制造路线节点值对象集合；obj[1]=装配路线节点值对象。 参考文档：生成报表 版本 <v2.0> 文档编号：PHOS-CAPP-UC308
     * 系统退出界面PHOS-CAPP-UI311，根据业务规则(PHOS-CAPP-BR206)显示一级路线报表界面(PHOS-CAPP-UI313)或二级路线报表界面(PHOS-CAPP-UI314) ，用例结束。
     */
    public CodeManageTable getFirstLeveRouteListReport(TechnicsRouteListIfc listInfo);

    /**
     * @roseuid 4031B65C0364
     * @J2EE_METHOD -- getSecondLeveRouteListReport 根据工艺路线表ID获得二级工艺路线报表。根据工艺路线表ID获得零件及其工艺路线值对象。
     * @return Map key:零件值对象;value=工艺路线值对象数组 数组中包含两个集合，obj[0]=一级工艺路线节点数组象集合，集合中有数组。 obj1[0]=制造路线节点值对象集合；obj1[1]=装配路线节点值对象。 obj[1] = 二级工艺路线节点数组象集合，集合中有数组。obj2[0]=制造路线节点值对象集合；obj2[1]=装配路线节点值对象。。
     * 参考文档：生成报表 版本 <v2.0> 文档编号：PHOS-CAPP-UC308 系统退出界面PHOS-CAPP-UI311，根据业务规则(PHOS-CAPP-BR206)显示一级路线报表界面(PHOS-CAPP-UI313)或二级路线报表界面(PHOS-CAPP-UI314) ，用例结束。
     */
    public Map getSecondLeveRouteListReport(TechnicsRouteListIfc listInfo) throws QMException;

    public Map getFirstLeveRouteListReport1(TechnicsRouteListIfc listInfo) throws QMException;

    ////////////////////////////生成报表结束。///////////////////////////////
    //用例11.启动工艺路线管理器
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC311
    /**
     * @roseuid 40359624031D
     * @J2EE_METHOD -- getAllRouteLists 获得所有的工艺路线表的最新版本，如果有A,B版，返回B版最新小版本。 参考文件：删除工艺路线表 版本 <v2.0> 文档编号：PHOS-CAPP-UC303
     * @return 工艺路线表值对象集合。
     */
    public Collection getAllRouteLists() throws QMException;

    /**
     * @roseuid 4031A5A203A3
     * @J2EE_METHOD -- getRouteListLinkParts 获得与路线表相关的零部件。
     * @return Collection 关联值对象集合。 参考文档：启动工艺路线管理器 版本 <v2.0> 文档编号：PHOS-CAPP-UC311 1. 执行者在路线表管理器界面(PHOS-CAPP-UI302)选中一个工艺路线表，执行编辑路线操作； 2. 系统显示"显示零部件表"界面(PHOS-CAPP-UI325)；
     */
    public Collection getRouteListLinkParts(TechnicsRouteListIfc routeListInfo) throws QMException;

    //用例12.重新选择零部件
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC312
    //方法在其它用例中已提供。getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)

    //用例13.创建工艺路线
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC313
    //用例14.更新工艺路线
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC314

    /**
     * 可获得显示PHOS-CAPP-UI320界面的所有路线及节点信息。
     * @param routelistID String
     * @param partMasterID String
     * @return Object[] obj[0]= 工艺路线值对象。obj[1] = HashMap,参见getRouteBranchs(String routeID)
     */
    public Object[] getRouteAndBrach(String routeID) throws QMException;

    /**
     * @roseuid 40399137004C
     * @J2EE_METHOD -- getRouteBranchs 根据工艺路线ID获得工艺路线分枝相关对象。
     * @return HashMap key=工艺路线分枝值对象, value=路线节点数组，obj[0]=制造路线节点值对象集合；obj[1]=装配路线节点值对象。
     */
    public Map getRouteBranchs(String routeID) throws QMException;

    //查看工艺路线
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC316

    /**
     * 根据工艺路线获得相关的节点及其关联。
     * @param routeID String
     * @return Object[] obj[0]=路线节点值对象集合； obj[1]=路线节点关联值对象集合。
     */
    public Object[] getRouteNodeAndNodeLink(String routeID) throws QMException;

    /**
     * @roseuid 4030A78002FA
     * @J2EE_METHOD -- saveRoute 保存工艺路线。 参考文档： 1。创建工艺路线 版本 <v2.0> 文档编号：PHOS-CAPP-UC313 系统保存此新建的工艺路线，将界面刷新为查看路线界面(PHOS-CAPP-UI317)，用例结束。 2。 更新工艺路线 版本 <v2.0> 文档编号：PHOS-CAPP-UC314
     * 在创建完路线后，当保存RoutePartLink时，应在ListRoutePartLink中保存相应的路线是否使用状态。保持数据的一致性。
     */
    public TechnicsRouteIfc saveRoute(ListRoutePartLinkIfc linkInfo, TechnicsRouteIfc routeInfo) throws QMException;

    //获得具体路线版本的关联。
    public ListRoutePartLinkIfc getListRoutePartLink(String routeListID, String partMasterID, String partNumber) throws QMException;

    /**
     * 客户端在进行保存时，应首先判断ListRoutePartLinkIfc的getAlterStatus()，如=0，全部设置成新建状态；=1，正常处理。
     * @roseuid 4030A8350200
     * @J2EE_METHOD -- saveRoute
     * @param routeRelaventObejts, key = bsoName, value = 对应的值对象集合。工艺路线集合：工艺路线值对象、路线ID、零件ID. 保存工艺路线编辑图。
     * @return Object[]:数组第一个元素--工艺路线值对象；数组第二个元素--HashMap 结构同getRouteBranchs(String routeID)。 参考文档： 1。 更新工艺路线 版本 <v2.0> 文档编号：PHOS-CAPP-UC314 2。创建工艺路线 版本 <v2.0> 文档编号：PHOS-CAPP-UC313 3。 PHOS-CAPP-BR211
     * 路线串构造规则 说明：工艺路线串的构成为路线单位节点，一个单位可以在一个路线串中出现多次。路线串中包括加工单位和装配单位，所以路线串的构成必须符合下列规则： 1. 装配单位在一个路线串中只能有一个，且只能是最后一个节点； 2. 一个单位如果在一个路线串中出现多次，则必须是不同类型的节点(制造或装配)，否则不能在相邻的位置出现。 ￠ *
     * 如果一个路线串中设计了多个装配节点，则显示对应的消息； ￠ * 如果装配节点不是最后节点，则显示对应的消息； ￠ * 如果路线串中存在相邻的同类型节点，则显示对应的消息； 注意：事务回滚。
     */
    public TechnicsRouteIfc saveRoute(ListRoutePartLinkIfc listLinkInfo, HashMap routeRelaventObejts) throws QMException;

    /**
     * 通过路线表编号获得路线表值对象
     * @param routeListNum 路线表编号
     * @return 路线表值对象
     * @author 郭晓亮
     */
    public TechnicsRouteListInfo findRouteListByNum(String routeListNum);

    //用例15.删除工艺路线
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC315

    /**
     * @roseuid 4030B1C90274
     * @J2EE_METHOD -- deleteRoute
     * @param routeListPartLinkID 关联类ID.
     * @param routeID 路线ID. 删除工艺路线。 参考文档：删除工艺路线 版本 <v2.0> 文档编号：PHOS-CAPP-UC315
     */
    public void deleteRoute(ListRoutePartLinkIfc linkInfo) throws QMException;

    /**
     * 批量删除零件路线
     * @param list
     * 20120113 xucy add
     */
    public void deleteRoute(ArrayList list) throws QMException;
    
    /**
     * 在删除和每次更新之前进行删除。
     * @param routeID String @
     */
    public void deleteBranchRelavent(String routeID) throws QMException;

    /**
     * 当只更新节点位置时，调用此方法。不进行路线的更新。
     * @param coll Collection @
     */
    public void saveOnlyNodes(Collection coll) throws QMException;

    ////////////路线及其节点的创建、更新、删除结束。////////////////

    //21.按产品结构生成路线报表
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC321
    /**
     * @param partMasterID
     * @param departmentID 用户所在单位的codeID. 如是一级路线，输入null。
     * @roseuid 4035D79C00B0
     * @J2EE_METHOD -- getPartLevelRoutes 根据零件ID和工艺路线级别获得对应的工艺路线。
     * @return 数组。obj[0]= TechnicsRouteListIfc, obj[1],obj[2]见getRouteAndBrach(routeID), obj[3]=ListRoutePartLinkIfc。
     */
    public Object[] getProductStructRoutes(String partMasterID, String level_zh);

    //22. 查看零部件的工艺路线
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC322

    /**
     * @param partMasterID
     * @param departmentID 用户所在单位的codeID. 如是一级路线，输入null。
     * @roseuid 4035D79C00B0
     * @J2EE_METHOD -- getPartLevelRoutes 根据零件ID和工艺路线级别获得对应的工艺路线。
     * @return Collection 数组集合。obj[0]= TechnicsRouteListIfc, obj[1],obj[2]见getRouteAndBrach(routeID), obj[3]=linkInfo。 参考文档： 查看零部件的工艺路线 版本 <v2.0> 文档编号：PHOS-CAPP-UC322 1.
     * 系统显示查看一级路线界面(PHOS-CAPP-UI332)，(S1)，用例结束。 2. 系统显示查看二级路线界面(PHOS-CAPP-UI333)，(备注1)(S1)，用例结束。
     */
    public Collection getPartLevelRoutes(String partMasterID, String level_zh);
    
    
    //CCBegin SS6
    public Collection getPartLevelRoutes(String partMasterID, String partID, String level_zh);
    //CCEnd SS6

    public Collection getPartRoutes(String partMasterID) throws QMException;

    //////17.复制工艺路线
    //版本 <v2.0>
    //文档编号：PHOS-CAPP-UC317

    /**
     * 复制自。利用其他路线表中的路线进行复制。获得"其它"路线表中与给定partMasterID采用的路线。 注意：此种情况只允许一个零件在不同路线表中路线的复制。不允许多个零件间的复制粘贴。
     * @param partMasterID
     * @param departmentID 用户所在单位的codeID. 如是一级路线，输入null。
     * @roseuid 4035D79C00B0
     * @J2EE_METHOD -- getPartLevelRoutes 根据零件ID和工艺路线级别获得对应的工艺路线。 执行者复制一个工艺路线，粘贴到没有工艺路线的零部件中。 复制工艺路线时，可以从当前路线表中的一个零部件的工艺路线复制，也可以从一个零部件的其它路线表编制的工艺路线复制；
     * 粘贴时，可以粘贴到当前选中的零部件，也可以粘贴到本路线表中其它无路线的零部件；如果零部件已有路线，使用"粘贴到"复制路线时，不能复制到这些零部件。
     * @return Collecion数组集合。obj[0]= TechnicsRouteListIfc, obj[1],obj[2]见getRouteAndBrach(routeID)。
     */
    public Collection getAdoptRoutes(ListRoutePartLinkIfc linkInfo1) throws QMException;

    /**
     * @param routeID 工艺路线ID
     * @param linkInfo
     * @roseuid 4030BD76011D
     * @J2EE_METHOD -- copyRoute 拷贝工艺路线及其包含节点。
     * @return 工艺路线ID.
     */
    public ListRoutePartLinkIfc copyRoute(String routeID, ListRoutePartLinkIfc linkInfo) throws QMException;

    /////////////////////复制结束。////////////////////////////

    /////////////////////与项目关联开始////////////////////////////
    /**
     * key=单位值对象。CodingIfc或CodingClassificationIfc, value=单位对应的零件master集合。
     * @param routeListID String @
     * @return HashMap
     */
    public HashMap getDepartmentAndPartByList(String routeListID) throws QMException;

    /**
     * @roseuid 403972CA007E
     * @J2EE_METHOD -- getParts 通过单位代码ID获得对应的要编工艺的零件。通过零件可获得相关信息。例：工艺路线等信息。此方法可由工艺部分调用。
     * @return 零件masterInfo集合。 注意：单位有结构，查找时，子节点也要遍历。此项暂不处理。
     */
    public Collection getParts(String departmentID) throws QMException;

    /**
     * 根据工艺路线获得其包含的单位。用于任务的分发。
     * @param routeListID String
     * @return Collection CodingIfc或CodingClassificationIfc
     */
    public Collection getDepartments(String routeListID) throws QMException;

    /////////////////////与项目关联结束////////////////////////////
    /**
     * 根据routeMaster获得所有的大版本对应的最新小版本。
     * @param listMasterInfo TechnicsRouteListMasterIfc @
     * @return Collection 所有的大版本对应的最新小版本。有config过滤。版本提供的方法：allVersionsOf(MasteredIfc mastered).
     */
    public Collection getAllVersionLists(TechnicsRouteListMasterIfc listMasterInfo);

    /**
     * 提供版序的比较。
     * @param iteratedVec Collection 不同版本的工艺路线表值对象集合。
     * @param isCommonSort 是否采用正常的比较方式。 @
     * @return Map key = partMasterID, value = Collection:listRoutePartLinkIfc集合。集合顺序，版本号升序排列。
     */
    public Map compareIterate(Collection iteratedVec)  throws QMException;

    //routeListInfo为新的大版本。此时要复制关联。并要设置initialUsed为新的大版本。
    public TechnicsRouteListIfc newVersion(TechnicsRouteListIfc routeListInfo);

    /**
     * 根据零件masterID获得工艺路线表值对象集合。
     * @param partMasterID String @
     * @return Collection
     */
    public Collection getListsByPart(String partMasterID);

    /////////////2004.4.27后添加的方法/////////////
    /**
     * 判断给定的零件masterID在其它路线表中是否有路线。
     * @param partMasteIDs Collection
     * @return Map, key=partMasterInfo, value = "不存在"或“无”；
     */
    public Map getRouteByParts(Collection partMasteInfos, String level_zh);

    /**
     * @param 有变化的分支值对象。alterStatus = 0;
     * @param routeMap Map
     */
    public void createRouteByBranch(ListRoutePartLinkIfc linkInfo, TechnicsRouteIfc route, Collection branchInfos);

    /**
     * 更新分枝。alterStatus = 1;
     * @param branchs Collection 分支值对象。
     */
    public void updateBranchInfos(Collection branchs);

    /**
     * @roseuid 4039965D0106
     * @J2EE_METHOD -- getRouteNodes 根据路线类型和分枝ID获得工艺路线节点。
     * @return Collection 工艺路线节点值对象。 （可考虑在检查起始节点是否有左连接，如有，调用RouteListHandler的后处理方法，重新设置起始节点。根据实际情况做选择。） 参考：PHOS-CAPP-UI321 编辑路线图
     */
    public Collection getRouteNodes(String routeType, String routeBranchID) throws QMException;

    /**
     * @roseuid 4032F952020C
     * @J2EE_METHOD -- getListPartRoute 根据零部件ID和工艺路线表ID获得对应的工艺路线。
     * @return 工艺路线值对象。 参考文档： １． PHOS-CAPP-BR208 创建工艺路线检查 说明：一个零部件在一个路线表中只能存在一个工艺路线对象。 ￠ * 如果零部件已存在工艺路线，则显示对应消息 ２．启动工艺路线管理器 版本 <v2.0> 文档编号：PHOS-CAPP-UC311 4.
     * 系统退出界面PHOS-CAPP-UI325，将执行者选择的路线表和执行者选中的零部件显示在"工艺路线管理器"界面(PHOS-CAPP-UI316)中，用例结束。 public TechnicsRouteIfc getListPartRoute(String partMasterID, String routeListID) { return null; }
     */

    /**
     * @roseuid 40309C1300C3
     * @J2EE_METHOD -- getRoutes 根据工艺路线表ID获得对应的工艺路线。
     * @return Collection 返回路线表对应的所有工艺路线，路线分枝，路线节点。 public Collection getRoutes(String routeListID) { return null; }
     */

    /**
     * @roseuid 40309EF101A5
     * @J2EE_METHOD -- getRoute 根据产品ID和零件ID获得工艺路线值对象。
     * @return 工艺路线值对象。此方法待商榷。
     */
    public TechnicsRouteIfc getRoute(String productMasterID, String partMasterID);

    /**
     * @roseuid 4031A6A70254
     * @J2EE_METHOD -- getRouteListContent 根据工艺路线表ID获得所有工艺路线及其节点值对象。
     * @return 哈希表。 key:bsoname;value=Collection －－相应值对象集合
     */
    public java.util.HashMap getRouteListContent(String routeListID, String level_zh);

    /**
     * @roseuid 4031A9080245
     * @J2EE_METHOD -- getListLevelRoutes 根据工艺路线表及路线级别获得工艺路线。 如果级别为空，不分级别。
     * @return Collection 工艺路线值对象。
     */
    public Collection getListLevelRoutes(String routeListID, String level_zh);

    /**
     * 复制路线相关对象。注意保证顺序。
     * @param routeID String
     * @param 有变化的分支值对象。
     * @return HashMap
     */
    public HashMap getRouteContainer(String routeID, Collection branchInfos) throws QMException;

    /**
     * 根据工艺路线获得相关的节点(按路线分支区别)及其关联。
     * @param routeID
     * @return obj[0]=Map（key＝分支值对象，value＝该分支中的路线节点的集合） obj[1]=路线节点关联值对象集合。 @
     */
    public Object[] getBranchNodeAndNodeLink(String routeID) throws QMException;

    /**
     * 获得用于指定产品的最新工艺路线表
     * @param productMasterID 产品
     * @param level_zh 路线表级别
     * @return 工艺路线表
     */
    public TechnicsRouteListInfo getRouteListByProduct(String productMasterID) throws QMException;

    public Object[] getMaterialBillRoutes(TechnicsRouteListInfo routeList, String partMasterID, String level_zh) throws QMException;

    /**
     * 获得指定零部件（主信息）的下一级零部件（主信息）的集合
     * @param partMasterInfo 指定零部件（主信息）
     * @return 下一级零部件（主信息）的集合 @
     */
    public Collection getSubPartMasters(QMPartMasterIfc partMasterInfo) throws QMException;

    /**
     * 对给定的零部件进行检查，检查这些零部件是否符合添加到二级路线的零部件表 如果当前编辑的工艺路线表是二级工艺路线表，则系统应根据路线表的单位属性，列出产品结构中所有一级路线有对应单位的零部件，作为备选零部件。 规则来源：3.4 PHOS-CAPP-BR204 从产品结构中添加零部件。
     * @param codeID 二级路线表的单位
     * @param productMasterID 二级路线表用于的产品
     * @param subPartMasters 需进行检查的零部件集合（要求元素为零部件主信息值对象）
     * @return 数组中包含二个元素，第一个元素是符合条件的零部件主信息集合，第二个元素是不符合条件的零部件主信息集合 @
     */
    public Object[] checkSubParts(String codeID, String productMasterID, Collection subPartMasters) throws QMException;

    /**
     * @param depart String
     * @param partInfo QMPartInfo
     * @return Collection
     */
    public boolean getOptionPart(String depart, QMPartMasterIfc master);

    /**
     * 通过part找出所有符合配制规范的子part，并调getOptionPart（）过滤结果集
     * @param partMaster QMPartMasterIfc
     * @param configSpecIfc PartConfigSpecIfc
     * @param depart String @
     * @return Vector
     */
    public Vector getAllSubPart(QMPartMasterIfc partMaster, PartConfigSpecIfc configSpecIfc, String depart);

    /**
     * 根据给定的制造单位查询相关的零部件和工艺路线id added by skybird 2005.3.9
     * @param makeDepartment 制造单位
     * @return Vector 组成元素{partMasterID,routebsoID} @
     */
    public Vector getAllPartsRoutesM(String makeDepartment);

    /**
     * 根据给定的装配单位查询相关的零部件和工艺路线id added by skybird 2005.3.9
     * @param constructDepartment 装配单位
     * @return Vector 组成元素{partMasterID,routebsoID} @
     */
    public Vector getAllPartsRoutesC(String constructDepartment);

    /**
     * 根据给定的制造单位和装配单位查询相关的零部件和工艺路线id added by skybird 2005.3.9
     * @param makeDepartment 制造单位
     * @param constructDepartment 装配单位
     * @return Vector 组成元素{partMasterID,routebsoID} @
     */
    public Vector getAllPartsRoutesA(String makeDepartment, String constructDepartment);

    public Collection getColligationRoutes(String mDepartment, String cDepartment, Vector parts, Vector products);

    /**
     * 根据给定的制造单位查询相关的零部件和工艺路线id added by gcy 2005.5.09
     * @param mDepartment String 制造单位的id
     * @param cDepartment String 装配单位的id
     * @param productID String 产品的id
     * @param source String 零部件的来源
     * @param type String 零部件的类型 @
     * @return Collection 结果集合。每个集合项是一个Vector.依次放零部件生命周期，零部件id，零部件编号，零部件名称， 版本，父件号，零部件在产品中数量集合，路线数量，路线集合（其中路线集合每个条目包括制造装配2中路线）
     */
    public Collection getAllPartsRoutes(String mDepartment, String cDepartment, String productID, String source, String type);

    /**
     * 根据零部件查找是否有路线
     * @param partMasterID 零部件bsoid
     * @return @
     */
    public boolean isHasRoute(String partMasterID);

    /**
     * 根据零部件查找是否有路线
     * @param partMasterID 零部件bsoid
     * @return @
     */
    public Vector[] isHasRoute(Vector vec);

  //  public HashMap calCountInProduct(Vector parts, QMPartIfc parent, QMPartIfc product);

    /**
     * 计算子件在产品中使用的数量（只计算其父件在产品中的数量）
     * @param part QMPartIfc 子件
     * @param parent QMPartIfc 父件
     * @param product QMPartIfc 产品 @
     * @return int 数量
     */
    public int calCountInProduct(QMPartIfc part, QMPartIfc parent, QMPartIfc product);

    /**
     * 用当前用户的配置规范过滤零部件
     * @param master QMPartMasterIfc @
     * @return QMPartIfc
     */
    public QMPartIfc filteredIterationsOfByDefault(QMPartMasterIfc master);

    /**
     * @roseuid 4039932300E0
     * @J2EE_METHOD -- getBranchRouteNodes 根据工艺路线分枝ID获得工艺路线节点。
     * @return Collection 工艺路线节点值对象。
     */
    public Collection getBranchRouteNodes(TechnicsRouteBranchIfc routeBranchInfo) throws QMException;

    //按路线节点类型分类。--装配路线、制造路线。
    public Object[] getNodeType(Collection branchNodes) throws QMException;

    // zz添加 判断是否是一个部件的子件
    public Collection isParentPart(Vector maybeChild, QMPartIfc maybeParent);

    //zz添加
    public Collection filteredIterationsOfByDefault(Collection masterCol);

    //zz 添加
    public Collection getRouteListLinkPartsforVersionCompare(TechnicsRouteListIfc routeListInfo) throws QMException;

    // zz 20061110 为二级路线添加零部件　要过滤
    public Collection getOptionPart(String depart, Vector masters);

    //CR3 begin
    public TechnicsRouteListMasterIfc rename(TechnicsRouteListMasterIfc routelist);

    //CR3 end

    //zz 添加
    public String getMaterialRoute(QMPartIfc part);

    public Vector setBOMList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, String source, String type, PartConfigSpecIfc configSpecIfc);

    public Vector setMaterialList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, PartConfigSpecIfc configSpecIfc);

    public String getStatus2(String partMasterID, String level_zh, //gg
            String depart);

    public String getStatus(String partMasterID, String level_zh);//gg

    //begin CR2
    public TechnicsRouteListIfc checkInTechRouteList(WorkableIfc workable, String location);

    /*
     * 检出工艺路线表
     */
    public Collection checkOutTechRouteList(TechnicsRouteListIfc[] checkoutinfo, boolean flag);

    public VersionedIfc reviseTechnicsRouteList(VersionedIfc reviseIfc, String foldLocation, String lifecycleTemName, String projectName);

    //end CR2
    //CR4 begin 批量添加
    public Collection findMultPartsByNumbers(Object[] param);

    //CR4 end

    public boolean getAdoptStatusByPart(String partmasterID);

    //CR5 begin 采用变更添加零件
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
     * 更新艺准零件关联和路线信息， 参数分别为要删除的零件信息集合，艺准对象、要创建和要更新的关联信息集合
     */
    public Object[] updateLinksAndRoutes(HashMap deleteCollection, TechnicsRouteListIfc routeListInfo1, ArrayList allLinkCol) throws QMException;
    
    /**
     * 得到零部件的所有父件以及路线情况
     * @param partmaster QMPartMasterIfc，listID String
     * @throws QMException
     * @return Vector
     */
    public Vector findParentsAndRoutes(QMPartMasterIfc partmaster);
    //CR8
    //CR12 begin
    /**
     * 发布路线方法
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
     * 获得零件生效路线 20120112 徐春英 add
     */
    public Collection findPartEffRoute(String partMasterID);
    
    /**
     * 查找指定零件的生效路线、失效路线
     * 20120110 徐春英 add
     */
    public  Object[] findPartEffAndDisabledRoute(String partMasterID);
    //CR9 begin
    /**
     * 判断要检入的路线表中的零部件是否都有路线
     * @param obj
     * @return
     */
    public boolean haveroutelist(TechnicsRouteListInfo obj);
    //CR9 end
    //CR10 begin
    /**
     * 复制自搜索含有有效路线的零部件
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
     * 创建快捷路线对象
     */
    public void saveShortCutRoute(List list, String userID);
    
    /**
     * 通过用户名查找快捷路线
     */
    public HashMap getShortRouteMap(String userID);
    //end CR11
    
    /**
     * 获得与路线表相关的零部件。
     * @param routeListInfo TechnicsRouteListIfc 路线表值对象 @
     * @return Collection 存储ListRoutePartLinkIfc：关联值对象集合。
     * @see TechnicsRouteListInfo 20120129 xucy add 查看艺准零件时调用此方法
     */
    public Collection getRouteListLinkParts1(TechnicsRouteListIfc routeListInfo) throws QMException;
    public HashMap ApplyModelRoute(ArrayList list);
    /**
     * 通过零件主信息ID获取符合配置规范的零件
     */
    public Object filteredIterationsOf(String masterID);
    public HashMap getRouteInformation(Vector routeVec);
    public Vector ViewModelRoute(Object[] param);
    /**
     * 生成报表，导出定制报表。输出方式为本地文件。
     * @param routeListID String 路线表的ID
     * @return String 路线表的名称+编号+的工艺路线报表 20120214 xucy modify
     * 瘦客户用
     */
    public String exportRouteList(String routeListID) throws QMException;
    
    public Map getProductRelations(String productIDs);
    
    public void setAttrForPart(BaseValueIfc primaryBusinessObject);
    
    public Collection getAllPartsRoutes_new(String mDepartment, String cDepartment, String productID, String partName , String state) throws QMException;
    
    /**
     * 获得零件在产品中的使用数量
     * @param partVec 零件集合
     * @param pruduct 用于产品
     * @return
     */
    public HashMap getPartsCount(Vector partVec,QMPartMasterIfc pruduct);
        //CCBegin SS1
    public QMPartIfc getLastedPartByConfig(QMPartMasterIfc master,PartConfigSpecIfc configSpecIfc) throws
        QMException;

    public ArrayList gatherExportData(String routeListID) throws QMException;
    //CCEnd SS1
    
    /**
     * 获得指定零件在特定配置规范的最新小版本 20061209 liuming add
     * @param partMasterBsoID
     * @param configSpecIfc 如果configSpecIfc=null，则获得当前用户的配置规范
     * @return QMPartIfc 如果当前用户在配置规范下没有查看该零件权限或没有版本，则返回null
     * @throws QMException
     */
    public QMPartIfc getLastedPartByConfig(String partMasterBsoID,
                                           PartConfigSpecIfc configSpecIfc) throws
        QMException;
    
    /**
     * 艺准通知书被批准后，将路线状态为“艺试准”的关联的零部件状态设置为试制状态
     * 当路线状态为其他的状态时，将关联的零部件状态设置为生产准备状态
     * @author 王红莲
     * @version 1.0
     */
    public void setRouteListPreparePartsState(BaseValueIfc primaryBusinessObject);
    
    public ArrayList getRouteCompleteLinkedPartByBsoID(String
  	      routeCompleteBsoID);
    
    /**
     * 根据艺毕通知书的BsoID获取关联的工艺路线
     *@author 王红莲
     *@version 1.0
     * @param routeCompleteBsoID 艺毕通知书的BsoID
     * @return 工艺路线集合
     */
    public ArrayList getCompleteLinkedListByBsoID(String
                                                  routeCompleteBsoID);
    
    
    /**
     * 设置艺毕通知书关联的零部件的生命周期状态
     * 将零部件的生命周期状态设置为生产状态
     * @author 王红莲
     * @version 1.0
     * @param routeListCompleteID 艺毕通知书的BsoID
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
     * 获取艺毕通知书的路线名称和说明信息
     * @author 王红莲
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
     * 获得零部件的发布源版本
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
     * 长特根据路线发布之后设置零件生产准备状态
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
	 *            二维数组，5个元素。例：obj[0]=编号，obj[1]=true.
	 *            true=是，false=非。数组顺序：编号、名称、级别（汉字）、用于产品、版本号。
	 * @roseuid 402CBAF700CA
	 * @J2EE_METHOD -- findRouteLists 获得工艺路线表。搜索范围：编号、名称、级别、用于产品、版本号。
	 * @return collection 工艺路线表值对象，最新版本。 此时要用ConfigService进行过滤。
	 */
	public Collection findRouteListsForCd(Object[][] param) throws QMException;
  //CCEnd SS14
	
	//CCBegin SS15
	/**
	 * 判断该改零件的路线中是否包含有该路线单位
	 * @param part QMPartIfc
	 * @param atts String[]
	 * @throws QMException
	 * @return Vector
	 */
	public boolean isIncludeDepartment(QMPartIfc part, String routeDepartment) throws QMException ;
	  
	/**
	 * 专为导出物料青单所用
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
	  /**成都批量搜索零部件
	 * @param param
	 * @return
	 * @throws QMException
	 */
	public Collection cDfindMultPartsByNumbers(Object[] param)throws QMException;
	//CCEnd SS16
	//CCBegin SS17
	/**
	 * 获得所有的工艺路线表的最新版本，如果有A,B版，返回B版最新小版本。 @
	 * @return Collection 存放obj[]集合：<br>obj[0]：工艺路线表值对象。
	 */
	public boolean searchSameNameList(TechnicsRouteListIfc routeListInfo)throws QMException;
	//CCEnd SS17
}
