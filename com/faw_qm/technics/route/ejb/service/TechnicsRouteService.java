/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * SS1 添加路线是否自动获取最新路线的复选框标识，默认选中，即自动添加最新路线，然后在路线编辑界面由用户决定是否修改。 liunan 2013-4-17
 * SS2 按列表顺序批量搜索返回零部件结果。 liunan 2013-11-25
 * SS3 通过采用单号，搜索零部件 liuyang 2014-6-5
 * SS4 通过更改通知单号搜索零部件 liuyang 2014-6-6
 * SS5 添加另存为路线复选框 liuyang 2014-6-10
 * SS6 获得路线串，判断是否包含卡车厂路线 徐德政 2014-10-15
 * SS7 添加附件。 liunan 2015-6-18
 * SS8 如果零部件是带回最新路线，则状态内容对应不上，partindex里是“无”，而路线里会有状态。改为保存时获取一下已有路线的状态，与后面一致。 liunan 2015-8-5
 * SS9 根据零部件获取最新路线串 liunan 2016-8-4
 * SS10 保存生产准备项目 liuyuzhu 2016-05-23
 * SS11 A004-2016-3415 实现PDM电控数据及分类属性参数自动发布到EOL系统 liunan 2016-9-7
 * SS12 A004-2016-3433 新增路线提醒功能，用于获取子件的装配路线集合。 liunan 2016-10-28
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
 * <p>Description: 工艺路线服务端类</p>
 * <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: 一汽启明公司</p>
 * @author 赵立彬
 * @mender skybird
 * @version 1.0
 */

/**
 * 参考文档：
 * Phos-REQ-CAPP-BR02(工艺路线业务规则)V2.0.doc
 * PHOS-REQ-CAPP-SRS-002(制造业企业基础数据管理需求规格说明-工艺路线) V2.0.doc
 * "Phos-CAPP-UC301--Phos-CAPP-UC322"共19个用例。
 */
public interface TechnicsRouteService
    extends BaseService {
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
   * @J2EE_METHOD  --  storeRouteList
   * 保存或更新工艺路线列表。非空检查在值对象中已做。唯一性检查在数据库中设置。
   * 参考文档：创建工艺路线表：PHOS-CAPP-UC301，更新工艺路线表：PHOS-CAPP-UC302
   *  系统根据业务规则PHOS-CAPP-BR201检查要求非空的属性是否为空(E1)，根据业务规则PHOS-CAPP-BR202检查工艺路线表编号是否唯一(E2)。
   * 例外
   * E1：
   * 系统显示消息：属性名称+(CP00001)
   * E2：
   * 系统显示消息：属性名称+(CW00001)
   * @return 工艺路线表值对象。
   */
  public TechnicsRouteListIfc storeRouteList(TechnicsRouteListIfc
                                             //CCBegin SS7
                                             //routeListInfo) throws QMException;
                                             routeListInfo, ArrayList arrayList) throws QMException;
                                             //CCEnd SS7

  //用例3.删除工艺路线表
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC303

  /**
   * @roseuid 40305EC40043
   * @J2EE_METHOD  --  deleteRouteList
   * 删除工艺路线表，整个大版本内的小版本全部删除。
   */
  public void deleteRouteList(TechnicsRouteListIfc routeListInfo) throws
      QMException;

  //用例4.编辑零部件表
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC304

  /**
   * @roseuid 403449360397
   * @J2EE_METHOD  --  getOptionalParts
   * @param level 路线级别显示名。例：一级路线。
   * 根据单位名称的代码ID和路线级别获得可选择的零部件。
   * @return Collection 零部件master值对象。
   * 对应规则：
   * 如果当前编辑的工艺路线表是一级工艺路线表，则系统应列出产品结构中的所有零部件；Part:
   *  getAllSubParts(QMPartIfc).
   * 如果当前编辑的工艺路线表是二级工艺路线表，则系统应根据路线表的单位属性，列出产品结构中所有一级路线有对应单位的零部件，作为备选零部件。
   * 规则来源：3.4 PHOS-CAPP-BR204 从产品结构中添加零部件。
   * 参考文档：
   * 编辑零部件表
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC304
   * 执行者在更新工艺路线表时，编辑要编制工艺路线的零部件表，可以添加零部件、删除零部件。添加零部件时，可以根据路线表中的"用于产品"的零部件，按其最新结构中提供备选零部件表，用户可以从中选择要添加到工艺路线表中的零部件。如果用户正在编辑的是二级路线表，则备选零部件表不仅应按用于产品的结构，还应按此产品所有零部件中包含本单位路线的一级路线进行进一步筛选，从而获得一份备选零部件表。
   */
  public Collection getOptionalParts(String codeID, String level,
                                     String productMasterID) throws QMException;

  //获得最新版本的值对象。
  public BaseValueIfc getLatestVesion(MasterIfc masterInfo) throws QMException;

  /**
   * 获得最新版本值对象的集合
   * @param c master对象集合
   * @throws QMException
   * (问题三)20060629 周茁修改
   */
  public Collection getLatestsVersion(Collection c) throws QMException;

 /**
  * add by guoxl on 20080307
  * 查看一级路线表报表时，序号排序错误
  *所以添加方法getLatestsVersion1
  */
   public HashMap getLatestsVersion1(Collection c) throws QMException;

  /**
   * 1.更新工艺路线表。2.保存或删除路线表和零件的关联。
   * @param saveCollection 所有需要保存的零件masterID.
   * @param deleteCollection 所有需要删除的零件masterID.
   * @param routeListID 工艺路线表ID.
   * @roseuid 40399FB60020
   * @J2EE_METHOD  --  saveListRoutePartLink
   * 保存工艺路线表与零件的关联。
   * 由客户端传入关联类集合，做统一处理。
   * @return Collection 所有的与工艺路线表关联的零件值对象。
   */
  public void saveListRoutePartLink(HashMap saveCollection,
                                    HashMap deleteCollection,
                                    TechnicsRouteListIfc routeListInfo1) throws
      QMException;

  /**
   * 更新父件编号
   * added by skybird 2005.3.4
   * @param PartsToChange
   * @param routeListInfo1
   * @throws QMException
   */
  public void updateListRoutePartLink(Collection PartsToChange,
                                      TechnicsRouteListIfc routeListInfo1) throws
      QMException;

  //用例5.搜索路线表(管理器)
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC305
  //用例6.搜索工艺路线表
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC306

  /**
   * @param param 二维数组，5个元素。例：obj[0]=编号，obj[1]=true. 数组顺序：编号、名称、级别（汉字）、用于产品、版本号。
   * @roseuid 402CBAF700CA
   * @J2EE_METHOD  --  findRouteLists
   * 获得工艺路线表。搜索范围：编号、名称、级别、用于产品、版本号。
   * @return collection 工艺路线表值对象，最新版本。
   * 此时要用ConfigService进行过滤。
   */
  public Collection findRouteLists(Object[][] param) throws QMException;

  /**
   * @roseuid 402CBAF700CA
   * @J2EE_METHOD  --  findRouteLists
   * 获得工艺路线表。搜索范围：编号、名称、状态、级别（汉字）、用于产品、说明、存放位置、创建日期、创建者、最后更新、版本号。
   * @return collection 工艺路线表值对象，最新版本。
   * 此时要用ConfigService进行过滤。
   */
  public Collection findRouteLists(TechnicsRouteListIfc routelistInfo,
                                   String productNumber, String createTime,
                                   String modifyTime) throws QMException;

  //用例7.查看工艺路线表
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC307
  //getRouteListLinkParts(TechnicsRouteListIfc routeListInfo)
////////////////////////////////////////////////////////////////////////////
  //用例8.生成报表
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC308

  /**
   * @roseuid 4030537700ED
   * @J2EE_METHOD  --  exportRouteList
   * 导出定制报表。输出方式为本地文件。
   * 参考文档：生成报表
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC308
   * 系统退出界面PHOS-CAPP-UI311，根据业务规则(PHOS-CAPP-BR206)显示一级路线报表界面(PHOS-CAPP-UI313)或二级路线报表界面(PHOS-CAPP-UI314)
   *  ，用例结束。
   */
  public String exportRouteList(String routeListID, String level_zh,
                                String exportFormat) throws QMException;

  /**
   * @roseuid 40305F8F0257
   * @J2EE_METHOD  --  getFirstLeveRouteListReport
   * 根据工艺路线表ID获得一级工艺路线报表。根据工艺路线表ID获得零件及其工艺路线值对象。
   *
   * key:零件值对象;value=工艺路线节点数组象集合，obj[0]=制造路线节点值对象集合；obj[1]=装配路线节点值对象。
   *
   * 参考文档：生成报表
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC308
   * 系统退出界面PHOS-CAPP-UI311，根据业务规则(PHOS-CAPP-BR206)显示一级路线报表界面(PHOS-CAPP-UI313)或二级路线报表界面(PHOS-CAPP-UI314)
   *  ，用例结束。
   */
  public CodeManageTable getFirstLeveRouteListReport(TechnicsRouteListIfc
      listInfo) throws QMException;

  /**
   * @roseuid 4031B65C0364
   * @J2EE_METHOD  --  getSecondLeveRouteListReport
   * 根据工艺路线表ID获得二级工艺路线报表。根据工艺路线表ID获得零件及其工艺路线值对象。
   * @return Map
   * key:零件值对象;value=工艺路线值对象数组
   * 数组中包含两个集合，obj[0]=一级工艺路线节点数组象集合，集合中有数组。 obj1[0]=制造路线节点值对象集合；obj1[1]=装配路线节点值对象。
   * obj[1] = 二级工艺路线节点数组象集合，集合中有数组。obj2[0]=制造路线节点值对象集合；obj2[1]=装配路线节点值对象。。
   *
   * 参考文档：生成报表
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC308
   * 系统退出界面PHOS-CAPP-UI311，根据业务规则(PHOS-CAPP-BR206)显示一级路线报表界面(PHOS-CAPP-UI313)或二级路线报表界面(PHOS-CAPP-UI314)
   *  ，用例结束。
   */
  public Map getSecondLeveRouteListReport(TechnicsRouteListIfc listInfo) throws
      QMException;

  public Map getFirstLeveRouteListReport1(TechnicsRouteListIfc listInfo) throws
      QMException;

////////////////////////////生成报表结束。///////////////////////////////
  //用例11.启动工艺路线管理器
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC311
  /**
   * @roseuid 40359624031D
   * @J2EE_METHOD  --  getAllRouteLists
   * 获得所有的工艺路线表的最新版本，如果有A,B版，返回B版最新小版本。
   * 参考文件：删除工艺路线表
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC303
   * @return 工艺路线表值对象集合。
   */
  public Collection getAllRouteLists() throws QMException;

  /**
   * @roseuid 4031A5A203A3
   * @J2EE_METHOD  --  getRouteListLinkParts
   * 获得与路线表相关的零部件。
   * @return Collection 关联值对象集合。
   * 参考文档：启动工艺路线管理器
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC311
   * 1. 执行者在路线表管理器界面(PHOS-CAPP-UI302)选中一个工艺路线表，执行编辑路线操作；
   * 2. 系统显示"显示零部件表"界面(PHOS-CAPP-UI325)；
   */
  public Collection getRouteListLinkParts(TechnicsRouteListIfc routeListInfo) throws
      QMException;

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
   * @J2EE_METHOD  --  getRouteBranchs
   * 根据工艺路线ID获得工艺路线分枝相关对象。
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
   * @J2EE_METHOD  --  saveRoute
   * 保存工艺路线。
   * 参考文档：
   * 1。创建工艺路线
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC313
   * 系统保存此新建的工艺路线，将界面刷新为查看路线界面(PHOS-CAPP-UI317)，用例结束。
   * 2。 更新工艺路线
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC314
   * 在创建完路线后，当保存RoutePartLink时，应在ListRoutePartLink中保存相应的路线是否使用状态。保持数据的一致性。
   */
  public TechnicsRouteIfc saveRoute(ListRoutePartLinkIfc linkInfo,
                                    TechnicsRouteIfc routeInfo) throws
      QMException;

  //获得具体路线版本的关联。
  public ListRoutePartLinkIfc getListRoutePartLink(String routeListID,
      String partMasterID, String partNumber) throws QMException;

  /**
   * 客户端在进行保存时，应首先判断ListRoutePartLinkIfc的getAlterStatus()，如=0，全部设置成新建状态；=1，正常处理。
   * @roseuid 4030A8350200
   * @J2EE_METHOD  --  saveRoute
   * @param routeRelaventObejts, key = bsoName, value = 对应的值对象集合。工艺路线集合：工艺路线值对象、路线ID、零件ID.
   * 保存工艺路线编辑图。
   * @return Object[]:数组第一个元素--工艺路线值对象；数组第二个元素--HashMap 结构同getRouteBranchs(String routeID)。
   * 参考文档：
   * 1。 更新工艺路线
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC314
   * 2。创建工艺路线
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC313
   * 3。 PHOS-CAPP-BR211 路线串构造规则
   * 说明：工艺路线串的构成为路线单位节点，一个单位可以在一个路线串中出现多次。路线串中包括加工单位和装配单位，所以路线串的构成必须符合下列规则：
   * 1. 装配单位在一个路线串中只能有一个，且只能是最后一个节点；
   * 2. 一个单位如果在一个路线串中出现多次，则必须是不同类型的节点(制造或装配)，否则不能在相邻的位置出现。
   * ￠     * 如果一个路线串中设计了多个装配节点，则显示对应的消息；
   * ￠     * 如果装配节点不是最后节点，则显示对应的消息；
   * ￠     * 如果路线串中存在相邻的同类型节点，则显示对应的消息；
   * 注意：事务回滚。
   */
  public void saveRoute(ListRoutePartLinkIfc listLinkInfo,
                        HashMap routeRelaventObejts) throws QMException;

  
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
   * @J2EE_METHOD  --  deleteRoute
   * @param routeListPartLinkID 关联类ID.
   * @param routeID 路线ID.
   * 删除工艺路线。
   * 参考文档：删除工艺路线
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC315
   */
  public void deleteRoute(ListRoutePartLinkIfc linkInfo) throws QMException;

  /**
   * 在删除和每次更新之前进行删除。
   * @param routeID String
   * @throws QMException
   */
  public void deleteBranchRelavent(String routeID) throws QMException;

  /**
   * 当只更新节点位置时，调用此方法。不进行路线的更新。
   * @param coll Collection
   * @throws QMException
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
   * @J2EE_METHOD  --  getPartLevelRoutes
   * 根据零件ID和工艺路线级别获得对应的工艺路线。
   * @return 数组。obj[0]= TechnicsRouteListIfc, obj[1],obj[2]见getRouteAndBrach(routeID), obj[3]=ListRoutePartLinkIfc。
   */
  public Object[] getProductStructRoutes(String partMasterID, String level_zh) throws
      QMException;

  //22. 查看零部件的工艺路线
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC322

  /**
   *
   * @param partMasterID
   * @param departmentID 用户所在单位的codeID. 如是一级路线，输入null。
   * @roseuid 4035D79C00B0
   * @J2EE_METHOD  --  getPartLevelRoutes
   * 根据零件ID和工艺路线级别获得对应的工艺路线。
   * @return Collection 数组集合。obj[0]= TechnicsRouteListIfc, obj[1],obj[2]见getRouteAndBrach(routeID), obj[3]=linkInfo。
   * 参考文档：
   * 查看零部件的工艺路线
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC322
   * 1. 系统显示查看一级路线界面(PHOS-CAPP-UI332)，(S1)，用例结束。
   * 2. 系统显示查看二级路线界面(PHOS-CAPP-UI333)，(备注1)(S1)，用例结束。
   */
  public Collection getPartLevelRoutes(String partMasterID, String level_zh) throws
      QMException;

  public Collection getPartRoutes(String partMasterID) throws QMException;

//////17.复制工艺路线
  //版本 <v2.0>
  //文档编号：PHOS-CAPP-UC317

  /**
   * 复制自。利用其他路线表中的路线进行复制。获得"其它"路线表中与给定partMasterID采用的路线。
   * 注意：此种情况只允许一个零件在不同路线表中路线的复制。不允许多个零件间的复制粘贴。
   * @param partMasterID
   * @param departmentID 用户所在单位的codeID. 如是一级路线，输入null。
   * @roseuid 4035D79C00B0
   * @J2EE_METHOD  --  getPartLevelRoutes
   * 根据零件ID和工艺路线级别获得对应的工艺路线。
   * 执行者复制一个工艺路线，粘贴到没有工艺路线的零部件中。
   * 复制工艺路线时，可以从当前路线表中的一个零部件的工艺路线复制，也可以从一个零部件的其它路线表编制的工艺路线复制；
   * 粘贴时，可以粘贴到当前选中的零部件，也可以粘贴到本路线表中其它无路线的零部件；如果零部件已有路线，使用"粘贴到"复制路线时，不能复制到这些零部件。
   * @return Collecion数组集合。obj[0]= TechnicsRouteListIfc, obj[1],obj[2]见getRouteAndBrach(routeID)。
   */
  public Collection getAdoptRoutes(ListRoutePartLinkIfc linkInfo1) throws
      QMException;

  /**
   * @param routeID 工艺路线ID
   * @param linkInfo
   * @roseuid 4030BD76011D
   * @J2EE_METHOD  --  copyRoute
   * 拷贝工艺路线及其包含节点。
   * @return 工艺路线ID.
   */
  public ListRoutePartLinkIfc copyRoute(String routeID,
                                        ListRoutePartLinkIfc linkInfo) throws
      QMException;

/////////////////////复制结束。////////////////////////////

/////////////////////与项目关联开始////////////////////////////
  /**
   * key=单位值对象。CodingIfc或CodingClassificationIfc, value=单位对应的零件master集合。
   * @param routeListID String
   * @throws QMException
   * @return HashMap
   */
  public HashMap getDepartmentAndPartByList(String routeListID) throws
      QMException;

  /**
   * @roseuid 403972CA007E
   * @J2EE_METHOD  --  getParts
   * 通过单位代码ID获得对应的要编工艺的零件。通过零件可获得相关信息。例：工艺路线等信息。此方法可由工艺部分调用。
   * @return 零件masterInfo集合。
   * 注意：单位有结构，查找时，子节点也要遍历。此项暂不处理。
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
   * @param listMasterInfo TechnicsRouteListMasterIfc
   * @throws QMException
   * @return Collection 所有的大版本对应的最新小版本。有config过滤。版本提供的方法：allVersionsOf(MasteredIfc mastered).
   */
  public Collection getAllVersionLists(TechnicsRouteListMasterIfc
                                       listMasterInfo) throws QMException;

  /**
   * 提供版序的比较。
   * @param iteratedVec Collection 不同版本的工艺路线表值对象集合。
   * @param isCommonSort 是否采用正常的比较方式。
   * @throws QMException
   * @return Map key = partMasterID, value = Collection:listRoutePartLinkIfc集合。集合顺序，版本号升序排列。
   */
  public Map compareIterate(Collection iteratedVec) throws QMException;

  //routeListInfo为新的大版本。此时要复制关联。并要设置initialUsed为新的大版本。
  public TechnicsRouteListIfc newVersion(TechnicsRouteListIfc routeListInfo) throws
      QMException;

  /**
   * 根据零件masterID获得工艺路线表值对象集合。
   * @param partMasterID String
   * @throws QMException
   * @return Collection
   */
  public Collection getListsByPart(String partMasterID) throws QMException;

/////////////2004.4.27后添加的方法/////////////
  /**
   * 判断给定的零件masterID在其它路线表中是否有路线。
   * @param partMasteIDs Collection
   * @return Map, key=partMasterInfo, value = "不存在"或“无”；
   */
  public Map getRouteByParts(Collection partMasteInfos, String level_zh) throws
      QMException;

  /**
   * @param 有变化的分支值对象。alterStatus = 0;
   * @param routeMap Map
   */
  public void createRouteByBranch(ListRoutePartLinkIfc linkInfo,
                                  TechnicsRouteIfc route,
                                  Collection branchInfos) throws QMException;

  /**
   * 更新分枝。alterStatus = 1;
   * @param branchs Collection 分支值对象。
   */
  public void updateBranchInfos(Collection branchs) throws QMException;

  /**
   * @roseuid 4039965D0106
   * @J2EE_METHOD  --  getRouteNodes
   * 根据路线类型和分枝ID获得工艺路线节点。
   * @return Collection 工艺路线节点值对象。
   * （可考虑在检查起始节点是否有左连接，如有，调用RouteListHandler的后处理方法，重新设置起始节点。根据实际情况做选择。）
   * 参考：PHOS-CAPP-UI321
   *             编辑路线图
   */
  public Collection getRouteNodes(String routeType, String routeBranchID);

  /**
   * @roseuid 4032F952020C
   * @J2EE_METHOD  --  getListPartRoute
   * 根据零部件ID和工艺路线表ID获得对应的工艺路线。
   * @return 工艺路线值对象。
   *
   * 参考文档：
   * １． PHOS-CAPP-BR208 创建工艺路线检查
   * 说明：一个零部件在一个路线表中只能存在一个工艺路线对象。
   * ￠     * 如果零部件已存在工艺路线，则显示对应消息
   *
   * ２．启动工艺路线管理器
   * 版本 <v2.0>
   * 文档编号：PHOS-CAPP-UC311
   *
   * 4. 系统退出界面PHOS-CAPP-UI325，将执行者选择的路线表和执行者选中的零部件显示在"工艺路线管理器"界面(PHOS-CAPP-UI316)中，用例结束。
         public TechnicsRouteIfc getListPartRoute(String partMasterID, String routeListID)
         {
        return null;
         }*/

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
   * @roseuid 40309EF101A5
   * @J2EE_METHOD  --  getRoute
   * 根据产品ID和零件ID获得工艺路线值对象。
   * @return 工艺路线值对象。此方法待商榷。
   */
  public TechnicsRouteIfc getRoute(String productMasterID,
                                   String partMasterID);

  /**
   * @roseuid 4031A6A70254
   * @J2EE_METHOD  --  getRouteListContent
   * 根据工艺路线表ID获得所有工艺路线及其节点值对象。
   * @return 哈希表。
   * key:bsoname;value=Collection  －－相应值对象集合
   */
  public java.util.HashMap getRouteListContent(String routeListID,
                                               String level_zh);

  /**
   * @roseuid 4031A9080245
   * @J2EE_METHOD  --  getListLevelRoutes
   * 根据工艺路线表及路线级别获得工艺路线。
   * 如果级别为空，不分级别。
   * @return Collection 工艺路线值对象。
   */
  public Collection getListLevelRoutes(String routeListID, String level_zh);

  /**
   * 复制路线相关对象。注意保证顺序。
   * @param routeID String
   * @param 有变化的分支值对象。
   * @return HashMap
   */
  public HashMap getRouteContainer(String routeID, Collection branchInfos) throws
      QMException;

  /**
   * 根据工艺路线获得相关的节点(按路线分支区别)及其关联。
   * @param routeID
   * @return obj[0]=Map（key＝分支值对象，value＝该分支中的路线节点的集合） obj[1]=路线节点关联值对象集合。
   * @throws QMException
   */
  public Object[] getBranchNodeAndNodeLink(String routeID) throws QMException;

  /**
   * 获得用于指定产品的最新工艺路线表
   * @param productMasterID 产品
   * @param level_zh 路线表级别
   * @return 工艺路线表
   */
  public TechnicsRouteListInfo getRouteListByProduct(String productMasterID) throws
      QMException;

  public Object[] getMaterialBillRoutes(TechnicsRouteListInfo routeList,
                                        String partMasterID, String level_zh) throws
      QMException;

  /**
   * 获得指定零部件（主信息）的下一级零部件（主信息）的集合
   * @param partMasterInfo 指定零部件（主信息）
   * @return 下一级零部件（主信息）的集合
   * @throws QMException
   */
  public Collection getSubPartMasters(QMPartMasterIfc partMasterInfo) throws
      QMException;

  /**
   * 对给定的零部件进行检查，检查这些零部件是否符合添加到二级路线的零部件表
   * 如果当前编辑的工艺路线表是二级工艺路线表，则系统应根据路线表的单位属性，列出产品结构中所有一级路线有对应单位的零部件，作为备选零部件。
   * 规则来源：3.4 PHOS-CAPP-BR204 从产品结构中添加零部件。
   * @param codeID 二级路线表的单位
   * @param productMasterID 二级路线表用于的产品
   * @param subPartMasters 需进行检查的零部件集合（要求元素为零部件主信息值对象）
   * @return 数组中包含二个元素，第一个元素是符合条件的零部件主信息集合，第二个元素是不符合条件的零部件主信息集合
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
   *  通过part找出所有符合配制规范的子part，并调getOptionPart（）过滤结果集
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
   * 根据给定的制造单位查询相关的零部件和工艺路线id
   * added by skybird 2005.3.9
   * @param makeDepartment 制造单位
   * @return Vector 组成元素{partMasterID,routebsoID}
   * @throws QMException
   */
  public Vector getAllPartsRoutesM(String makeDepartment) throws QMException;

  /**
   * 根据给定的装配单位查询相关的零部件和工艺路线id
   * added by skybird 2005.3.9
   * @param constructDepartment 装配单位
   * @return Vector 组成元素{partMasterID,routebsoID}
   * @throws QMException
   */
  public Vector getAllPartsRoutesC(String constructDepartment) throws
      QMException;

  /**
   * 根据给定的制造单位和装配单位查询相关的零部件和工艺路线id
   * added by skybird 2005.3.9
   * @param makeDepartment 制造单位
   * @param constructDepartment 装配单位
   * @return Vector 组成元素{partMasterID,routebsoID}
   * @throws QMException
   */
  public Vector getAllPartsRoutesA(String makeDepartment,
                                   String constructDepartment) throws
      QMException;

  public Collection getColligationRoutes(String mDepartment, String cDepartment,
                                         Vector parts, Vector products) throws
      QMException;

  /**
   * 根据给定的制造单位查询相关的零部件和工艺路线id
   * added by gcy 2005.5.09
   * @param mDepartment String 制造单位的id
   * @param cDepartment String  装配单位的id
   * @param productID String  产品的id
   * @param source String  零部件的来源
   * @param type String 零部件的类型
   * @throws QMException
   * @return Collection  结果集合。每个集合项是一个Vector.依次放零部件生命周期，零部件id，零部件编号，零部件名称，
   * 版本，父件号，零部件在产品中数量集合，路线数量，路线集合（其中路线集合每个条目包括制造装配2中路线）
   */
  public Collection getAllPartsRoutes(String mDepartment, String cDepartment,
                                      String productID, String source,
                                      String type) throws QMException;

  /**
   * 根据零部件查找是否有路线
   * @param partMasterID 零部件bsoid
   * @return
   * @throws QMException
   */
  public boolean isHasRoute(String partMasterID) throws
      QMException;

  /**
   * 根据零部件查找是否有路线
   * @param partMasterID 零部件bsoid
   * @return
   * @throws QMException
   */
  public Vector[] isHasRoute(Vector vec) throws
      QMException;

  public HashMap calCountInProduct(Vector parts, QMPartIfc parent,
                               QMPartIfc product) throws QMException;

  /**
  * 计算子件在产品中使用的数量（只计算其父件在产品中的数量）
  * @param part QMPartIfc 子件
  * @param parent QMPartIfc 父件
  * @param product QMPartIfc 产品
  * @throws QMException
  * @return int 数量
  */
 public int calCountInProduct(QMPartIfc part, QMPartIfc parent,
                              QMPartIfc product) throws QMException ;


  /**
   * 用当前用户的配置规范过滤零部件
   * @param master QMPartMasterIfc
   * @throws QMException
   * @return QMPartIfc
   */
  public QMPartIfc filteredIterationsOfByDefault(QMPartMasterIfc master) throws
      QMException;

  /**
   * @roseuid 4039932300E0
   * @J2EE_METHOD  --  getBranchRouteNodes
   * 根据工艺路线分枝ID获得工艺路线节点。
   * @return Collection 工艺路线节点值对象。
   */
  public Collection getBranchRouteNodes(TechnicsRouteBranchIfc
                                        routeBranchInfo) throws
      QMException;

//按路线节点类型分类。--装配路线、制造路线。
  public Object[] getNodeType(Collection branchNodes) throws QMException;
  // zz添加 判断是否是一个部件的子件
public Collection isParentPart (Vector maybeChild, QMPartIfc maybeParent) throws QMException;
  //zz添加
  public Collection filteredIterationsOfByDefault(Collection masterCol) throws
      QMException ;
  //zz 添加
  public Collection getRouteListLinkPartsforVersionCompare(TechnicsRouteListIfc routeListInfo) throws
       QMException ;
// zz 20061110 为二级路线添加零部件　要过滤
   public Collection getOptionPart(String depart, Vector masters) throws
      QMException ;
  //  //zz 添加
   public TechnicsRouteListMasterIfc rename(TechnicsRouteListIfc routelist)throws QMException;
     //zz 添加
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
  
//CCBegin by leixiao 2008-7-31 原因：解放升级工艺路线,增加"采用通知书添加"
  /**
   * 解放系统升级，根据变更单文档号及采用通知书号搜索零件
   * @param noticeValue String
   * @return ArrayList
   * @throws QMException
   */
  
  //CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
  //public ArrayList getNoticeOrChangeRelatedParts(String noticeValue) throws QMException ;
  public ArrayList getNoticeOrChangeRelatedParts(String noticeValue, boolean isManufacturing) throws QMException;
  //CCEnd by liunan 2012-05-22
      

  public HashMap getCounts(String productMasterID, HashMap partMap) throws
  QMException,java.sql.SQLException;
  
  public Collection getSubPartsNoRoute(String productNumber) throws
  QMException;
  /**
   * 获得指定零件在特定配置规范的最新小版本 20061209 liuming add
   * @param partMasterBsoID
   * @param configSpecIfc 如果configSpecIfc=null，则获得当前用户的配置规范
   * @return QMPartIfc 如果当前用户在配置规范下没有查看该零件权限或没有版本，则返回null
   * @throws QMException
   */
  public QMPartIfc getLastedPartByConfig(QMPartMasterIfc master) throws
  QMException;
  public QMPartIfc getLastedPartByConfig(String partMasterBsoID,PartConfigSpecIfc configSpecIfc) throws
      QMException;
//CCBegin by leixiao 2009-1-12 原因：解放升级工艺路线,非生准状态不作艺准
  public QMPartIfc getLastedPartofsz(QMPartMasterIfc master,PartConfigSpecIfc configSpecIfc) throws
  QMException;
//CCEnd by leixiao 2009-1-12 原因：解放升级工艺路线,非生准状态不作艺准     
  public QMPartIfc getLastedPartByConfig(QMPartMasterIfc master,PartConfigSpecIfc configSpecIfc) throws
  QMException;
  
  public Vector getListAndBrances(QMPartIfc product, QMPartIfc part,
          String[] atts) throws QMException ;
  public Vector getListAndBrances(QMPartIfc product, QMPartIfc part,
          String[] atts, String parentFirstMakeRoute) throws QMException ;
  public Collection getRouteInfomationByPartmaster(String partMasterID) throws
  QMException ;
  /**
   * 在艺准通知书批准后，系统自动对路线中包含分子公司简称的零部件对分子公司进行授权。
   * 只对零部件及其关联文档和图档进行授权。
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
//CCEndby leixiao 2008-7-31 原因：解放升级工艺路线
//CCBegin by leixiao 2008-8-11 原因：解放升级工艺路线，导出路线
  /**
   * 收集报表数据 20061209 liuming add
   * @param routeListID String
   * @param IsExpandByProduct 是否按车展开（暂时默认总为真）
   * @throws QMException
   */
  public ArrayList gatherExportData(String routeListID, String IsExpandByProduct)
      throws
      QMException;

//CCEnd by leixiao 2008-8-11 原因：解放升级工艺路线 ，导出路线 
//CCBegin by leixiao 2008-8-11 原因：解放升级工艺路线
  public Vector findParentsAndRoutes(QMPartMasterIfc partmaster, String listID) throws
  QMException;
//CCEnd by leixiao 2008-8-11 原因：解放升级工艺路线
//CCBegin by leixiao 2008-8-11 原因：解放升级工艺路线
  public Vector getAllDeps() throws
  QMException;
//CCEnd by leixiao 2008-8-11 原因：解放升级工艺路线

//CCBegin by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕放艺准里，修改流程
  public void setRouteCompletePartsState(String routeListID)throws QMException;
//CCEnd by leixiao 2009-4-1 原因：解放升级工艺路线,艺毕放艺准里，修改流程         

//CCBegin by liunan 2011-09-21 艺废通知书。
public void setRouteDisaffirmPartsState(String routeListID)throws QMException;
//CCEnd by liunan 2011-09-21
  
  public Collection getNeededCollForReport(Collection partMasterIDColl);
     public Collection getNeededCollForReport(Collection partMasterIDColl,
       TechnicsRouteListInfo routeList) ;
//CCEnd by leixiao 2008-9-11 原因：解放升级
//CCBegin by leixiao 2008-10-07 原因：解放升级,艺准通知书流程   	     
  /**
   * 为指定艺准的所有采用状态的零件添加IBA属性值（艺准通知书号）
   * @param routeListBsoID 艺准通知书BsoID
   * 20070115 liuming add
   */
    public void addIBAvalueForPart(String routeListBsoID) throws QMException ;
//CCEnd by leixiao 2008-10-07 原因：解放升级,艺准通知书
//CCBegin by leixiao 2008-10-07 原因：解放升级
/**
* 获取当前用户的配置规范，如果用户是首次登陆系统，则构造默认的“工程视图标准”配置规范。
* @throws QMException 使用ExtendedPartService时会抛出。
* @return PartConfigSpecIfc 标准配置规范。
*/
public  PartConfigSpecIfc getCurrentUserConfigSpec() throws
QMException;

 /**
* 重命名
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
//CCBegin by leixiao 2009-1-8 原因：解放升级工艺路线,版本取汽研发布版本 
public String  getibaPartVersion(QMPartMasterIfc master) throws
QMException;
public String  getibaPartVersion(QMPartIfc part) throws
QMException;
//CCEnd by leixiao 2009-1-8 原因：解放升级工艺路线,版本取汽研发布版本
//CCEnd by leixiao 2008-10-07 原因：解放升级 
public void handleversion()throws QMException;
public void handleversion1()throws QMException;


//CCBegin by liujiakun 工艺定额
/**
* @param param 二维数组，5个元素。例：obj[0]=编号，obj[1]=true. 数组顺序：编号、名称、级别（汉字）、用于产品、版本号。
* @roseuid 402CBAF700CA
* @J2EE_METHOD  --  findRouteLists
* 获得工艺路线表。搜索范围：编号、名称、级别、用于产品、版本号。
* @return collection 工艺路线表值对象，最新版本。
* 此时要用ConfigService进行过滤。
*/
public Collection findRouteListsnew(Object[][] param) throws QMException;
//CCEnd by liujiakun 工艺定额    
//CCBegin by liujiakun 工艺定额

//tang 20070521
public ListRoutePartLinkInfo getCurrentRouteLinkByPartID(String
   partMasterBsoID) throws QMException;

/**
* tangshutao add for qingqi 2007.09.17
* 根据路线表与零部件关联值对象集合获得每个零部件的路线串
* @param coll Collection 路线表与零部件关联值对象集合
* @throws QMException
* @return Vector  存放数组，每个数组三个元素，{零部件编号，零部件名称，路线串}
*/
public Vector getRouteTextByLinkCollection(Vector coll) throws
   QMException;

/**
* 2007.11.21 用于材料定额的过滤
* @param coll Vector
* @throws QMException
* @return Vector
*/
public Vector getRationRouteTextByLinkCol(Vector coll, String department) throws
   QMException;

/**
* tangshutao add for qingqi 2007.09.17
* 根据路线ID获得路线串
* @param routeid String
* @throws QMException
* @return String
*/
public String getRouteTextByRouteID(String routeid) throws
   QMException;

/**
* 2007.12.1 tangshutao 符合辅助材料定额编制的零件过滤
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
* @return String[] 1,路线状态、2，新路线、3，旧路线
*/
public String[] getRouteTextByMaster(String info) throws
   QMException;

//CCEnd by liujiakun 工艺定额
/**
* 2008.01.15 根据艺准获得关联的零件
* @param routeListInfo TechnicsRouteListIfc
* @throws QMException
* @return BaseValueIfc[]
*/

public BaseValueIfc[] getRouteListParts(TechnicsRouteListIfc routeListInfo) throws
   QMException;
//CCBegin by leixiao 2009-4-14 原因：解放升级
public ArrayList getcompleteroutepart(String routeid)throws QMException;

//CCEnd by leixiao 2009-4-14 原因：解放升级
//CCBegin by leixiao 2010-11-30 原因：增加"按发布令号添加" 
//CCBegin by liunan 2012-05-22 添加艺毕是否添加生产状态的复选框。如选中则可以搜索并添加生产状态零部件。
//public ArrayList getPublishRelatedParts(String noticeValue) throws QMException;
public ArrayList getPublishRelatedParts(String noticeValue, boolean isManufacturing) throws QMException;
//CCEnd by liunan 2012-05-22
//CCEnd by leixiao 2010-11-30 原因：增加"按发布令号添加" 
//CCBegin by liunan 2011-09-20 新增根据零部件获得有路线的艺准信息。
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
   * 获取项目内容并保存
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
