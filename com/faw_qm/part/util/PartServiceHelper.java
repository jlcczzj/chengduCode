/**
 * 生成程序 PartServiceHelper.java    1.0    2003/02/28
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/04/27 谢斌 原因:逻辑混乱
 *                     方案:重写方法
 *                     备注:解放v3r11-分级物料清单性能优化-谢斌
 * CR2 2009/06/09 李延则 原因：查看广义部件使用结构图标显示不正确(td-2258)
 *                       方案：添加广义部件的图标
 * CR3 20090619 张强 修改原因：进行结构比较后，产品信息管理中配置规范显示为结构比较目标件的配置规范。（TD-2190） 
 *                   解决方案：进行结构比较后，产品信息管理中正确显示的配置规范。
 * CR4 2009/12/25 王亮 修改原因：TD2741，在查看父件的使用结构时，点击一个其不符合配置规范的子件的编号链接，页面错误。
 *                     解决方案： 添加方法getPartNumberAndName用于获取零部件编号和名称，用于组织jsp页面信息。                   
 * SS1 获取所有零部件小版本的参考文档。 liunan 2013-4-1
 * SS2 结构比较生成最终报表出错，有状态为空引起的问题，增加判断。 liunan 2013-6-6
 * SS3 2013-1-21  产品信息管理器中输出物料清单功能中增加输出带有erp属性报表功能，导出的格式文件内容包括：零部件的零件号、零件名、父件数量、 
                  每车数量、计量单位、来源、制造路线、装配路线、（T单总、固定提前期合计、可变提前期），
                  其中后三项括弧中的内容需要从零部件关联的工艺卡中获取
 * SS4 变速箱产品设字添加零部件时不判断权限,直接添加 pante 2013-12-19
 * SS5 获取分子公司物料清单列表 liuyang 2013-12-18
 * SS6 增加颜色件标识 liuyang 2014-6-13
 * SS7 比较零部件基线的版本和生产版本是否相同，不同颜色变红 xianglx 2014-10-12
 * SS8 该零部件关联的最大版本的参考文档 gaoys 2015-4-15
 * SS9 汽研windchill升级10.2，只发布零部件，附件pdf、中性文件和原图都挂在零部件下。 liunan 2015-8-21
 * SS10 A004-2015-3167 见其他零部件图纸的零部件，需要在见某件位置添加链接，链接到指定零部件查看界面。 liunan 2015-9-21
 * SS11 外部系统通过零部件编号和版本查看pdm系统中中性文件。 liunan 2015-9-23
 * SS12 查看零部件的pdf和中性文件，要通过epm的权限来控制，有epm读权限则可以在线查看，有epm下载权限则可以下载。 liunan 2015-10-23
 * SS13 A004-2015-3229 零部件配置文件关联。 liunan 2015-12-7
 * SS14 A004-2016-3286 整车一级件清单 liunan 2016-1-20
 * SS15 A004-2016-3338 判断是否有下载和在线查看权限，首先根据关联epm查看权限（有动态权限），没有关联才查看域的静态权限。 liunan 2016-4-1
 *      中性文件权限只看epm对应权限。 liunan 2017-3-10
 * SS16 A004-2015-3231 修改获取配置文档的方法，零部件是广义部件配置出来或广义部件配置过，都能找到配置文件，增加查看配置文件参数选择 liunan 2016-11-2
 * SS17 A004-2017-3462 查询一级机构报表时数量都会改变。 liunan 2017-2-14
 * SS18 A004-2017-3479 车桥不能看2502018K5HC2图纸，没有epm权限，增加对动态权限字段的判断。 liunan 2017-3-16
 * SS19 汽研发布解放设计的生准件，会覆盖原有件，且没有图纸，这时需要显示原图纸。 liunan 2017-6-6
 * SS20 增加零部件下载权限的判断，增加获取零部件doc附件的方法。 liunan 2017-6-8
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
 * 为瘦客户端提供的方法。每个方法均调用服务中的同名方法。
 * @author 吴先超  李玉芬
 * @author 孙贤民
 * @version 1.0
 * 
 */
//问题(1)20080228 张强修改 修改原因:.判断有效性方案的类型和选择的类型是否一致，如果不一致则弹出提示信息。
//问题(2)20080808 张强修改 修改原因：在产看零部件的被用于产品时，显示不正确。（TD-1794）
//问题(3)20081014 张强修改 修改原因:当刚创建零部件时，工作状态应该显示为“未检入”。（TD-1868）
//问题(4)20081028 张强修改 修改原因:零部件瘦客户查看界面带单位的浮点数类型属性显示单位为属性定义的量纲而非默认单位。（TD-1893）
//问题(5)20081103 张强修改 修改原因:用户没有查看公共资料夹的文档的权限时，参考文档显示为空，应该显示文档主信息。（TD-1906）
public class PartServiceHelper implements Serializable
{
  private static Object lock = new Object();
  static final long serialVersionUID = 1L;
  String resource = "com.faw_qm.part.util.PartResource";

  /**
   * 构造函数。
   */
  public PartServiceHelper()
  {
  }

  /**
   * 根据已经持久化的零部件的值对象获得该零部件的所有使用关系的值对象集合。
   * @param partID :String持久化零部件的值对象bsoID。
   * @return vector:Vector与参数part关联的PartUsageLinkIfc的集合。
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
   * 根据零部件值对象获得该零部件关联的所有参考文档(DocIfc)最新版本的值对象的集合。
   * @param partID :String指定的零部件的值对象bsoID。
   * @return Vector 零部件参考文档(DocIfc)值对象的集合，
   * 先根据零部件获取所有的参考文档主信息(DocMasterIfc)的集合,再对DocMasterIfc集合
   * 进行过滤，找出每个DocMasterIfc所对应的最新版本DocIfc。最后返回DocIfc的一些
   * 基本属性的集合:返回值Vector的每个元素都是String[]:
   * <br>String[0] : 文档的bsoID;
   * <br>String[1] : 文档的编号；
   * <br>String[2] : 文档的名称；
   * <br>String[3] : 文档的分类类型；
   * <br>String[4] : 文档的大版本号；
   * <br>String[5] : 文档的检入检出状态；
   * <br>String[6] : 文档的数据格式ID(DataFormatID)；
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
  //完成!!!!

  //修改5 20081103 zhangq begin 修改原因：用户没有查看公共资料夹的文档的权限时，参考文档显示为空，应该显示文档主信息。
  /**
   * 根据零部件值对象获得该零部件关联的所有参考文档(DocMasterIfc)的主信息值对象的集合。
   * @param partID :String指定的零部件的值对象bsoID。
   * @return Vector 零部件参考文档的主信息值对象(DocMasterIfc)的集合，
   * 先根据零部件获取所有的参考文档主信息(DocMasterIfc)的集合,再返回DocMasterIfc的一些
   * 基本属性的集合:返回值Vector的每个元素都是String[]:
   * <br>String[0] : 文档Master的bsoID;
   * <br>String[1] : 文档的编号；
   * <br>String[2] : 文档的名称；
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
  //修改5 20081103 zhangq end

  /**
   * 根据指定的零部件的值对象返回该零部件描述文档的值对象集合。
   * 
   * @param partID :String零部件的值对象bsoID；
   * @return vector:Vector零部件描述文档的值对象集合，
   * 返回的Vector中的每个元素都是String[]:
   * <br>String[0] : 文档的BsoID；
   * <br>String[1] : 文档的编号；
   * <br>String[2] : 文档的名称；
   * <br>String[3] : 文档的大版本；
   * <br>String[4] : 文档的生命周期状态。
   * <br>String[5] : 文档的检入检出状态
   * <br>String[6] : 文档的数据格式ID(DataFormatID)。
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
   * 根据指定的零部件的值对象返回该零部件EPM文档的值对象集合,如果当前版本的零部件没有EPM文档，
   * 获取最新一个有文档的零部件包含的EMP文档。
   * @param partID :String零部件的值对象bsoID；
   * @return vector:Vector零部件描述文档的值对象集合，
   * 返回的Vector中的每个元素都是String[]:
   * <br>String[0] : 文档的BsoID；
   * <br>String[1] : 文档的编号；
   * <br>String[2] : 文档的名称；
   * <br>String[3] : 文档的大版本；
   * <br>String[4] : 文档的生命周期状态。
   * <br>String[5] : 文档的检入检出状态
   * <br>String[6] : 文档的数据格式ID(DataFormatID)
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
    //去掉没有当前版本的关联时，去找历史版本的part的方法。
    //没有关联就不用显示关联的part。
    //源代码：
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
   * 获得可以被指定零部件替换的主零部件的值对象的集合。
   * @param partMasterID :String:主零部件的值对象bsoID。
   * @return collection:Collection被该主零部件替换的主零部件的值对象的集合。
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
   * 获得指定主零部件的替换件的值对象的集合。
   * @param partMasterID :String主零部件的值对象bsoID。
   * @return collection:Collection该主零部件替换件的值对象的集合。
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
   * 根据指定的结构获得替换件的集合。
   * @param usageLinkID 指定的一个结构的值对象bsoID。
   * @return collection可以替换的主零部件的值对象QMPartMasterIfc的集合。
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
   * 根据指定的主零部件获得该零部件可以替换的结构的值对象的集合。
   * @param partMasterID :String指定的主零部件值对象bsoID；
   * @return collection:Collection该零部件可以替换的结构值对象的集合。
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
   * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。
   * 
   * @param partID :String 指定的零部件的bsoID.
   * @param attrNames :String 定制要输出的属性集合；如果为空，则按标准版输出。
   * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理；
   * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理；
   * @param effectivityActive 是否是有效性配置规范 是1,否0
   * @param baselineActive 是否是基准线配置规范 是1,否0
   * @param standardActive 是否是标准配置规范 是1,否0
   * @param baselineID 基准线的BsoID
   * @param configItemID 配置规范的BsoID
   * @param viewObjectID 视图对象的bsoID
   * @param effectivityType 有效性类型
   * @param effectivityUnit 有效性单位 需要持久化
   * @param state 状态
   * @param workingIncluded 是否在个人文件夹，默认为0:false,1:true
   * @throws QMException
   * @return Vector 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
   *<br> 1、如果不定制属性：
   * BsoID，号码、名称、是（否）可分（"true", "false"）、数量（转化为字符型）、版本和视图；
   * <br>2、如果定制属性：
   * BsoID，号码、名称、是（否）可分("true", "false")、数量、其他定制属性。
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
    //3月6日:需要先转换partID为part.BsoID，转换configSpec为PartConfigSpecIfc对象。
    //再调用方法:StandardPartService.setBOMList(QMPartIfc partIfc, String[] attrNames, String source,
    //String type, PartConfigSpecIfc configSpecIfc)方法!!!
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
   * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。
   *
   * @param partID :String 指定的零部件的bsoID.
   * @param attrNames :String 定制要输出的属性集合；如果为空，则按标准版输出。
   * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理；
   * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理；
   * @throws QMException
   * @return Vector  在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
   * <br>1、如果不定制属性：
   * BsoID，号码、名称、是（否）可分（"true", "false"）、数量（转化为字符型）、版本和视图；
   *<br> 2、如果定制属性：
   * BsoID，号码、名称、是（否）可分("true", "false")、数量、其他定制属性。
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
   * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的物料清单信息。
   * 
   * @param partID String 指定零部件的bsoID
   * @param attrNames String 定制的属性，可以为空
   * @throws QMException
   * @return Vector 返回结果是vector,其中vector中的每个元素都是一个集合：
   *<br> 0：当前part的BsoID；
   *<br> 1：当前part所在的级别，转化为字符型；
   *<br> 2：当前part的编号；
   * <br>3：当前part的名称；
   * <br>4：当前part被最顶层部件使用的数量，转化为字符型；
   * <br>5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
   * <br>             ：如果定制了属性：按照所有定制的属性加到结果集合中。
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
   * 获得受该基准线管理的所有零部件的值对象的集合。
   * @param baselineBsoID :String基准线的值对象的bsoID。
   * @return Vector 受指定的基准线管理的所有零部件的值对象的集合。
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
   * 根据指定的源部件和目标部件及各自的筛选条件，返回两个部件使用关系结构的不同，
   * 
   * @param sourcePartID :String源部件的bsoID；
   * @param effectivityActive1 :String 是否是有效性配置规范 是1,否0
   * @param baselineActive1 :String 是否是基准线配置规范 是1,否0
   * @param standardActive1 :String 是否是标准配置规范 是1,否0
   * @param baselineID1 :String 基准线的BsoID
   * @param configItemID1 :String 配置规范的BsoID
   * @param viewObjectID1 :String 视图对象的bsoID
   * @param effectivityType1 :String 有效性类型
   * @param effectivityUnit1 :String 有效性单位 需要持久化
   * @param state1 :String 状态
   * @param workingIncluded1 :String 是否在个人文件夹，默认为0:false,1:true
   * @param objectPartID :String目标部件的bsoID；
   * @param effectivityActive2 是否是有效性配置规范 是1,否0
   * @param baselineActive2 是否是基准线配置规范 是1,否0
   * @param standardActive2 是否是标准配置规范 是1,否0
   * @param baselineID2 基准线的BsoID
   * @param configItemID2 配置规范的BsoID
   * @param viewObjectID2 视图对象的bsoID
   * @param effectivityType2 有效性类型
   * @param effectivityUnit2 有效性单位 需要持久化
   * @param state2 状态
   * @param workingIncluded2 是否在个人文件夹，默认为0:false,1:true
   * @return vector Vector 返回值Vector中元素的数据结构String[]：
   *<br> 0：当前差异部件相对于根部件的层次，这里，根部件为0层，根部件的子部件为1层，以此类推；
   *<br> 1：当前差异部件的名称+"("+编号 + ")" ；
   *
   *<br> 2：当前差异部件在源部件中的版本+（视图），如果源部件中没有该部件的话，显示""，
   *    如果源部件中有该差异部件但是没有符合配置规范的版本的话，
   *    显示"没有符合配置规范的版本"；
   *<br> 3：当前差异部件被其上一级零部件所使用的数量+(单位)，如果源部件中没有该部件的话，显示""
   *    如果源部件中有该差异部件但是没有符合配置规范的版本的话，照常显示数量+（单位）；
   *
   *<br> 4：当前差异部件在目标部件中的版本+（视图），如果目标部件中没有该部件的话，显示""，
   *    如果目标部件中有该差异部件但是没有符合配置规范的版本的话，
   *    显示"没有符合配置规范的版本"；
   *<br> 5：当前差异部件被其上一级零部件所使用的数量+(单位)，如果目标部件中没有该部件的话，显示""
   *    如果目标部件中有该差异部件但是没有符合配置规范的版本的话，照常显示数量+（单位）；
   *
   *<br> 6：显示的颜色的标识::如果差异部件在源部件和目标部件中都存在，但数量或者版本不同的话，显示为黑色(black)；
   *    如果差异部件在源部件中存在，但是在目标部件中不存在的话，显示为绿色(green)；
   *    如果差异部件在目标部件中存在，但是在源部件中不存在的话，显示为紫色(purple)；
   *    如果差异部件在源部件和在目标部件中的版本和数量都相同的话，显示为灰色(gray)；
   *    信息"没有符合配置规范的版本"的颜色为红色(red),含有该信息的行的颜色为黑色(black)；
   *
   * <br>7:差异零部件的BsoID，是针对源零部件而言的，即是源零部件使用的该差异零部件的BsoID；
   *
   * <br>8:差异零部件的BsoID, 是针对目标零部件而言的，即是目标零部件使用的的差异零部件的BsoID；
   * 本方法只有企业版本服务提供。
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
   * 根据指定的源部件和目标部件及各自的筛选条件，返回两个部件使用关系结构的不同。
   * 
   * @param sourcePartID :String源部件的bsoID；
   * @param objectPartID :String目标部件的bsoID；
   * @param effectivityActive2 是否是有效性配置规范 是1,否0
   * @param baselineActive2 是否是基准线配置规范 是1,否0
   * @param standardActive2 是否是标准配置规范 是1,否0
   * @param baselineID2 基准线的BsoID
   * @param configItemID2 配置规范的BsoID
   * @param viewObjectID2 视图对象的bsoID
   * @param effectivityType2 有效性类型
   * @param effectivityUnit2 有效性单位 需要持久化
   * @param state2 状态
   * @param workingIncluded2 是否在个人文件夹，默认为0:false,1:true
   * @return vector Vector 返回值Vector中元素的数据结构String[]：
   * <br>0：当前差异部件相对于根部件的层次，这里，根部件为0层，根部件的子部件为1层，以此类推；
   * <br>1：当前差异部件的名称+"("+编号 + ")" ；
   *
   * <br>2：当前差异部件在源部件中的版本+（视图），如果源部件中没有该部件的话，显示""，
   *    如果源部件中有该差异部件但是没有符合配置规范的版本的话，
   *    显示"没有符合配置规范的版本"；
   * <br>3：当前差异部件被其上一级零部件所使用的数量+(单位)，如果源部件中没有该部件的话，显示""
   *    如果源部件中有该差异部件但是没有符合配置规范的版本的话，照常显示数量+（单位）；
   *
   * <br>4：当前差异部件在目标部件中的版本+（视图），如果目标部件中没有该部件的话，显示""，
   *    如果目标部件中有该差异部件但是没有符合配置规范的版本的话，
   *    显示"没有符合配置规范的版本"；
   * <br>5：当前差异部件被其上一级零部件所使用的数量+(单位)，如果目标部件中没有该部件的话，显示""
   *    如果目标部件中有该差异部件但是没有符合配置规范的版本的话，照常显示数量+（单位）；
   *
   * <br>6：显示的颜色的标识::如果差异部件在源部件和目标部件中都存在，但数量或者版本不同的话，显示为黑色(black)；
   *    如果差异部件在源部件中存在，但是在目标部件中不存在的话，显示为绿色(green)；
   *    如果差异部件在目标部件中存在，但是在源部件中不存在的话，显示为紫色(purple)；
   *    如果差异部件在源部件和在目标部件中的版本和数量都相同的话，显示为灰色(gray)；
   *    信息"没有符合配置规范的版本"的颜色为红色(red),含有该信息的行的颜色为黑色(black)。
   *
   * <br>7:差异零部件的BsoID，是针对源零部件而言的，即是源零部件使用的该差异零部件的BsoID；
   *
   * <br>8:差异零部件的BsoID, 是针对目标零部件而言的，即是目标零部件使用的的差异零部件的BsoID；
   * 本方法只有企业版本服务提供。
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
    //CR3 Begin 20090619 zhangq 修改原因：TD-2190。
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
   * 获取结构比较最终结果报表，返回一个集合.
 
   * @param sourcePartID :String源部件的bsoID；
   * @param objectPartID :String目标部件的bsoID；
   * @param effectivityActive2 是否是有效性配置规范 是1,否0
   * @param baselineActive2 是否是基准线配置规范 是1,否0
   * @param standardActive2 是否是标准配置规范 是1,否0
   * @param baselineID2 基准线的BsoID
   * @param configItemID2 配置规范的BsoID
   * @param viewObjectID2 视图对象的bsoID
   * @param effectivityType2 有效性类型
   * @param effectivityUnit2 有效性单位 需要持久化
   * @param state2 状态
   * @param workingIncluded2 是否在个人文件夹，默认为0:false,1:true
   * @param sourcePartNames 源部件名称
   * @param objectPartNames 目标件名称
   * @return 返回值Vector中元素的数据结构String[]：
   * <br>0：当前差异部件相对于根部件的层次，这里，根部件为0层，根部件的子部件为1层，以此类推；
   * <br>1：当前差异部件的编号；
   *
   * <br>2：当前差异部件的名称；
   * <br>3：当前差异部件的版本；
   * <br>4：当前差异部件的数量；
   *<br> 5：当前差异部件的变化，新加、撤销、替换、被替换；
   *<br> 6：说明
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
        //CCBegin by leixiao 2010-2-24增加生命周期状态的显示
          obj1[7] = obj[17];
        //CCEnd by leixiao 2010-2-24增加生命周期状态的显示
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
        //CCBegin by leixiao 2010-2-24增加生命周期状态的显示
          obj1[7] = obj[17];
        //CCEnd by leixiao 2010-2-24增加生命周期状态的显示
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
             obj1[5] = "数量不同";
               obj1[6] = "数量：" + obj[16];
           }
           else
           {
             obj1[5] = "版本不同";
             obj1[6] =  "版本：" + obj[15] ;
           }
           //CCBegin by leixiao 2010-2-24增加生命周期状态的显示
           obj1[7] = obj[17];
         //CCEnd by leixiao 2010-2-24增加生命周期状态的显示
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
                 obj1[5] = "替换";
               obj1[6] = "采用:"+nume+"取消："+num1;
               }
               else
               {
                 obj1[5] = "";
                 obj1[6] = "";
               }
             }
             else {
               obj1[5] = "数量不同";
               obj1[6] = "数量：" + obj[16];
             }
             //CCBegin by leixiao 2010-2-24增加生命周期状态的显示
             //CCBegin SS2
             if(obj1.length==18)
             obj1[7] = obj[17];
             else
             obj1[7] = "";
             //CCEnd SS2
           //CCEnd by leixiao 2010-2-24增加生命周期状态的显示
             result.add(obj1);

           }
      }

      return result;

    }
  /**
   * 根据指定零部件和指定的筛选条件获得零部件在该筛选条件下被哪些部件所使用。使用结果
   * 保存在返回值vector中。
   *
   * @param partID 指定零部件的bsoID
   * @param effectivityActive 是否是有效性配置规范 是1,否0
   * @param baselineActive 是否是基准线配置规范 是1,否0
   * @param standardActive 是否是标准配置规范 是1,否0
   * @param baselineID 基准线的BsoID
   * @param configItemID 配置规范的BsoID
   * @param viewObjectID 视图对象的bsoID
   * @param effectivityType 有效性类型
   * @param effectivityUnit 有效性单位 需要持久化
   * @param state 状态
   * @param workingIncluded 是否在个人文件夹，默认为0:false,1:true
   * @return Vector  vector返回值的数据结构为：vector中的每个元素都是Vector类型的，这里为了叙述方便，起名为subVector.
   * 而subVector的每个元素都是String[]类型的:
   * <br>String[0]:层次号；
   * <br>String[1]:零部件编号(零部件名称)版本(视图);
   * <br>String[2]零部件在此结构中(顶级件中)使用的数量，所以在同一结构下的记录使用数量是相同的。
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
   * 根据指定零部件和指定的筛选条件获得零部件在该筛选条件下被哪些部件所使用。
   * 使用结果保存在返回值vector中。
   * 
   * @param partID 指定零部件的bsoID
   * @return Vector vector返回值的数据结构为：vector中的每个元素都是Vector类型的，这里为了叙述方便，起名为subVector.
   * 而subVector的每个元素都是String[]类型的:
   * <br>String[0]:层次号；
   * <br>String[1]:零部件编号(零部件名称)版本(视图);
   *<br> String[2]零部件在此结构中(顶级件中)使用的数量，所以在同一结构下的记录使用数量是相同的。
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
    System.out.println("被用于产品 用时： "+(t222-t111)/1000+" 秒");
    return vec;
  }

  /**
   * 根据指定的零部件的bsoID和筛选条件获得零部件的产品结构。
   * @param partID :当前零部件的bsoID
   * @param effectivityActive 是否是有效性配置规范 是1,否0
   * @param baselineActive 是否是基准线配置规范 是1,否0
   * @param standardActive 是否是标准配置规范 是1,否0
   * @param baselineID 基准线的BsoID
   * @param configItemID 配置规范的BsoID
   * @param viewObjectID 视图对象的bsoID
   * @param effectivityType 有效性类型
   * @param effectivityUnit 有效性单位
   * @param state 状态
   * @param workingIncluded 是否在个人文件夹，默认为0:false,1:true
   * @return Vector 返回Vector:
   * <br>关键字：
   * 号码+名称+版本+视图+数量+单位
   *<br> 值：包含下一级子件信息的哈西表，关键字和值雷同。如果值为空，该零部件即为树形结构
   * 的叶节点。
   * @throws QMException
   */
  //返回一维的数组
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
    //先生成PartConfigSpecIfc,再调用StandardPartServiceEJB中的同名方法
    PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
    String isHasConfigItem = "1";
    PartConfigSpecIfc partConfigSpecIfc = PartServiceRequest.hashtableToPartConfigSpecIfc(
        effectivityActive, baselineActive, standardActive, baselineID,
        configItemID, viewObjectID, effectivityType, effectivityUnit, state, workingIncluded);
    PartDebug.trace(this, "getProductStructure() end....return is Vector");
    return PartServiceRequest.getProductStructure(partIfc, partConfigSpecIfc);
  }

  //add by 孙贤民
  /**
   * 根据指定的零部件的bsoID,获得零部件的产品结构。
   * @param partID String当前零部件的bsoID
   * @return Vector 一维数组
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
   * 获得配置规范的设置信息。
   * 
   * @throws QMException
   * @return Object[] obj[0] 是否是标准配置规范 是1
   * <br>obj[1] viewObject 视图对象
   * <br>obj[2] 生命周期状态
   * <br>obj[3] 是否是基准线配置规范 是1
   * <br>obj[4] 基准线对象
   * <br>obj[5] 是否是有效性配置规范 是1
   * <br>obj[6] 有效性配置项
   * <br>obj[7] 有效性值
   * <br>obj[8] 有效性类型(中文)
   * <br>obj[9] 有效性类型(英文)
   */
  public Object[] getConfigType()
      throws QMException
  {
    Object[] obj = new Object[11];
    PartConfigSpecInfo configInfo = (PartConfigSpecInfo)PartServiceRequest.findPartConfigSpecIfc();
    if(configInfo != null)
    {
      //如果为true，筛选条件为“有效性”
      boolean flag1 = configInfo.getEffectivityActive();
      //如果为true，筛选条件为“基准线”
      boolean flag2 = configInfo.getBaselineActive();
      //如果为true，筛选条件为“标准”
      boolean flag3 = configInfo.getStandardActive();
      //设置为“有效性”筛选条件时
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
        //转换成中文
        //2003/12/13
        //String chineseEffType =effType.getDisplay();
        String chineseEffType = effType.getDisplay(RemoteProperty.getVersionLocale());
        obj[8] = chineseEffType;
        obj[9] = effType;
      }
      //设置为“基准线”筛选条件时
      if(flag2)
      {
        obj[3] = "1";
        BaselineIfc baseline = configInfo.getBaseline().getBaselineIfc();
        obj[4] = baseline;
      }
      //设置为“标准”筛选条件时
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
   * 通过名称和号码查找主零部件。允许模糊查询。如果name为null，按号码查询；如果number
   * 为null，按名称查询；如果name和numnber都为null，获得所有主零部件的值对象。
   * @param name :String 模糊查询的零部件名称；
   * @param number :String模糊查询的零部件的号码；
   * @return collection:Collection符合查询条件的主零部件的值对象的集合。
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
   * 通过名称和号码查找主零部件。允许模糊查询。如果name为null，按号码查询；如果number
   * 为null，按名称查询；如果name和numnber都为null，获得所有主零部件的值对象。
   * 如果参数nameFlag为true, 查找零部件名称和name不相同的那些零部件，否则，查找零部件
   * 名称和name相同的零部件。对参数numFlag作同样的处理。
   * @param name 待查询的零部件名称
   * @param nameFlag 过滤条件，字符型boolean变量
   * @param number 待查询的零部件编号
   * @param numFlag 过滤条件，字符型boolean变量
   * @return Collection 查询出来的主零部件(QMPartMasterIfc)的集合
   * @throws QMException
   */
  public Collection getAllPartMasters(String name, String nameFlag,
                                      String number,
                                      String numFlag)
      throws QMException
  {
    PartDebug.trace(this, "getAllPartMasters" + "(name, nameFlag, number, numFlag) begin ....");
    //需要首先把nameFlag, 和numFlag转化为boolean类型。
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

  //完成!!!!

  /**
   * 获得指定零部件的所有父部件的主信息值对象集合。用于查看被用于部件WEB页面。根据
   * partID获得零部件的值对象后调用服务中的同名方法。
   * @param partID ：String指定的零部件的bsoID；
   * @return Vector指定零部件的所有父部件（直到根部件）的主信息值对象的集合。
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

  //完成!!!!

  /**
   * 通过bsoID获得对象的值对象。
   * @param bsoID 零部件BsoID
   * @return QMPartIfc 零部件值对象
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
  //完成!!

  /**
   * 通过bsoID获得对象的值对象。
   * @param bsoID 零部件主信息BsoID。
   * @return QMPartMasterIfc 零部件主信息值对象。
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
   * 通过bsoID获得对象的值对象。
   * @param bsoID 基准线BsoID。
   * @return ManagedBaselineIfc 基准线值对象。
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
   * 通过bsoID获得对象的值对象。
   * @param bsoID 持久化对象的BsoID。
   * @return BaseValueIfc 持久化对象值对象。
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
   * 通过bsoID获得符合当前配置规范的零部件的最新小版本。
   * @param bsoID 持久化对象的BsoID。
   * @return QMPartIfc 当前配置规范中该零部件的最新小版本。
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
   * @param solutionID String QMPartMasterIfc的BsoID。
   * @param addValueRange String 新增加的有效性值范围。
   * @throws QMException
   * @return Vector EffGroup对象的集合。
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
   * 获得在当前筛选条件下partMasterIfc的结构下所有子件的有效性范围数据类对象EffGroup
   * 的集合。该方法用于显示按结构添加有效性结果。
   * @param partMasterID :零部件主信息业务业务对象的bsoID。
   * @param configSpecIfc :PartConfigSpecIfc 配置规范值对象。
   * @param configItemID 有效性配置项对象的bsoID。
   * @param value_range 值范围。
   * @return Vector 有效性范围数据类对象EffGroup的集合
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
  //完成!!!!

  /**
   * 判断partIfcVector中的所有元素(都是QMPartIfc类型):的参考文档的差异情况。
   * 
   * @param partIfcVector 受同一个QMPartMaster管理的不同branchID的最新小版本的零部件值对象集合
   * @return Vector 返回值Vector中是String[]的集合:
   * <br>String[0]为partIfcVector中元素(QMPartIfc)包含的参考文档对象的文档编号;
   * <br>String[1]到String[partIfcVector.size()]是参考文档在向量(partIfcVector)中的对象(QMPartIfc)中的存在情况，
   * 存在为"true",不存在为"false"。
   * @throws QMException
   */
  public Vector getDeReferenceByDoc(Vector partIfcVector)
      throws QMException
  {
    PartDebug.trace(this, "getDeReferenceByDoc() begin ....");
    // 方法getDocMastersByPartIfc，给出partIfc，返回partIfc关联的所有参考文档(DocMasterIfc)的集合
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
    //下面实现对partIfcVector中的从1到n-1的元素的处理
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
            //如果tempDocMasterNum和vectorString[0]相同的话，合并，即把mainVector中的第
            //m个元素的值String[j+1]修改，String[j+1] = "true"，flag = true.修改完毕后，跳出本层循环。
            if(tempDocMasterNum.equals(vectorString[0]))
            {
              vectorString[j + 1] = "true";
              mainVector.setElementAt(vectorString, m);
              flag = true;
              break;
            }//end if(tempDocMasterNum == vectorString[0])
            //如果tempDocMasterNum和vectorString[0]不相同的话，继续本层的循环，即：
            //继续for(int m=0; m<mainVector.size(); m++)
          }//end for(int m=0; m<mainVector.size(); m++)
          //如果循环for(int m=0; m<mainVector.size(); m++)完毕，在mainVector中没有找到和
          //本 tempDocMasterNum 相同的元素的话，就把本tempDocMasterNum加到mainVector中。
          //tempDocMasterNum , false, ..., true(对应第j个part的版本), false,...false.
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
    //因为查到的是DocMasterIfc的集合，所以，在判断两个DocMaster是否相同的时候，只需要
    //判断其docNumber是否相同.
    //最后的返回值是Vector:vector vector中的每个元素应该是:
    //docMasterNumber, boolean ,boolean ,... , boolean (n个String的boolean)
    //最后需要判断mainVector中是否有这样的元素:String[1]...String[n]都是"true"
    //需要删除这样的元素!!!!
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
      //过滤
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

  //完成!!!!

  /**
   * 给定partIfc，返回其关联的所有DocMasterIfc的集合。
   * @param partIfc :QMPartIfc 零部件值对象。
   * @return Vector DocMasterIfc的集合。
   * @see QMPartInfo
   * @throws QMException
   */
  //需要查PartReferenceLink表，根据partIfc, 和"referencedBy"
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
  * 获取一个集合中的多个零部件所包含的EPM文档（适合多版本的零部件）
  * 通过QMPart和EPMDocumen的关联类EPMBuildHistory查找EPMDocumen的对象集合。
  * @param partIfcVector 零部件值对象集合
  * @return 返回一个字串数组集合，数组大小取决于零部件对象集合的大小+2，
  *<br> 0.EPM文档id;
  * <br>1.epm文档编号；
  * <br>2.epm文档版本 ，数组第三个元素以后的元素记录epm文档的版本
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
	//把partIfcVector所关联的所有EPMDocumentIfc都放到tempResult中
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
	
	//下面是对partIfcVector中的从1到n-1的元素的处理
	
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
	      //比较tempEPMNumber和mainVector的元素
	      for(int m = 0; m < mainVector.size(); m++)
	      {
	    	String[] vectorString = (String[])mainVector.elementAt(m);	    	
	    	//如果tempEPMNumber和vectorString[1]相同，合并，即把mainVector中的
	    	//第m个元素的值String[j+2]修改，String[j+2] = 版本+版序，flag = true.修改完毕后，跳出本层循环。
	    	if(tempEPMNumber.equals(vectorString[1]))
	    	{
	    	  vectorString[j + 2] = tempEPMIfc.getVersionValue();
	    	  mainVector.setElementAt(vectorString, m);
	    	  flag = true;
              break;
	    	}
	    	 //if(tempEPMNumber.equals(vectorString[1]))
	    	
	        //如果tempEPMNumber和vectorString[1]不相同，
           //继续for(int m=0; m < mainVector.size(); m++)
	      }
	      //end for(int m = 0; m < mainVector.size(); m++)
	      
	      //如果for(int m = 0; m < mainVector.size(); m++)循环结束，在mainVector中没有找到和
	      //tempEPMNumber相同的元素，就把本tempEPMNumber加到mainVector中
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
	
	//下面对mainVector中的元素进行处理，如果tempString = (String[])mainVector.elementAt(i);
	//tempString[2]...tempString[n+1]都不是"false",则按照下面处理：
	//版本+版序都相同的需要删除，但是，VersionValue都不是空，而且有不同的情况的时候，需要保留
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
    	//如果所有的版本+版序都是相同的话，就删除掉;不同的话，就留下
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
  
  //通过QMPart和Doc的关联关系类PartDescribeLink查找Doc的对象集合，
  //两个描述文档是不同的，如果他们的版本和版序都不相同
  /**
   * 判断partIfcVector中的所有元素QMPartIfc的描述文档的差异情况。
   *
   * @param partIfcVector 零部件值对象集合。
   * @return Vector  返回值Vector中每个元素都是String[]:
   * <br>String[0]是描述文档对象(DocIfc)的文档编号(DocNum);
   * <br>String[1]到String[partIfcVector.size()]中记录该描述文档在partIfcVector中的对象(partIfc)中的存在情况，
   * 存在的情况记录该描述文档的版本版序, 不存在为false,版本版序和false为String类型。
   * @throws QMException
   */
  public Vector getDeDescribeByDoc(Vector partIfcVector)
      throws QMException
  {
    PartDebug.trace(this, "getDeDescribeByDoc() begin ....");
    // 方法getDocsByPartIfc，给出partIfc，返回partIfc关联的所有描述文档(DocIfc)的集合
    int n = partIfcVector.size();
    String[] tempString = new String[n + 2];
    Vector mainVector = new Vector();
    Vector tempResult = new Vector();
    //把partIfc集合中的第一个元素partIfc所关联的所有的DocIfc都放到mainVector中
    //mainVector是中间结果，其中的每个元素都是String[]，其中String[0]是DocIfc的BsoID,
    //String[1]是DocIfc的docNum，String[2]是DocIfc的"版本+版序".String[3]...String[n+1]都是
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

    //下面实现对partIfcVector中的从1到n-1的元素(partIfc类型)的处理
    DocIfc[] tempDocArray = null;
    String tempDocNum = "";
    DocIfc tempDocIfc = null;     
    for(int j = 1; j < n; j++)
    {
      //对第j个partIfc进行处理:
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
          //把tempDocBsoID和mainVector中的所有元素进行比较:          
          for(int m = 0; m < mainVector.size(); m++)
          {
            String[] vectorString = (String[])mainVector.elementAt(m);            
            //如果tempDocBsoID和vectorString[0]相同的话，合并，即把mainVector中的第
            //m个元素的值String[j+2]修改，String[j+2] = 版本+版序，flag = true.修改完毕后，跳出本层循环。
            if(tempDocNum.equals(vectorString[1]))
            {
              vectorString[j + 2] = tempDocIfc.getVersionValue();
              mainVector.setElementAt(vectorString, m);
              flag = true;
              break;
            }
            //end if(tempDocBsoID.equals(vectorString[0]))

            //如果tempDocBsoID和vectorString[0]不相同的话，继续本层的循环，即：
            //继续for(int m=0; m<mainVector.size(); m++)
          }
          //end for(int m=0; m<mainVector.size(); m++)

          //如果循环for(int m=0; m<mainVector.size(); m++)完毕，在mainVector中没有找到和
          //本 tempDocBsoID 相同的元素的话，就把本tempDocBsoID加到mainVector中。
          //tempDocBsoID , false, ..., 版本+版序(对应第j个part的版本), false,...false.
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

    //处理完毕后，结果存放在mainVector中,mainVector中的元素是String[]类型的:
    //String[0]为DocIfc的BsoID, String[1]为DocIfc的DocNum，String[2]...String[n+1]是
    //false, 或者"版本+版序"。

    //现在需要:对mainVector中的元素进行处理，如果tempString[] = mainVector.elementAt(i)
    //tempString[2]...tempString[n+1]都不是"false",则按照下面处理：
    //版本+版序都相同的需要删除，但是，VersionValue都不是空，而且有不同的情况的时候，
    //还是需要留下的!!
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

      //过滤掉不含有"false"的元素, 还需要过滤掉tempString[0]!!!!
      //如果account>0，表示了该tempString是需要留下的::一定有不相同的
      //存在。
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
        //需要对tempString[0] tempString[1],之外的所有的"版本+版序"进行比较
        //如果有不相同的，就留下，如果所有的版本+版序都是相同的话，就删除掉。
        String s1 = tempString[2];
        for(int k = 3; k < tempString.length; k++)
        {
          if(!s1.equals(tempString[k]))
          {
            //把tempString[1][..]转移到tempString1[0][..]中
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
  //完成!!!!

  /**
   * 给定partIfc，返回和该partIfc关联的描述文档值对象(DocIfc)的集合。
   * @param partIfc 零部件值对象。
   * @return Collection Collection中的每个元素都是DocIfc类型。
   * @see QMPartInfo
   * @throws QMException
   */
  private Vector getDocsByPartIfc(QMPartIfc partIfc)
      throws QMException
  {
    PartDebug.trace(this, "getDocsByPartIfc() begin ....");
    //需要查表来查找到集合Collection 访问PartDescribeLink
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
      //需要对结果进行合并处理操作:::
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

  //完成!!!!

  //主要是判断不同零部件使用的QMPartMasterIfc的差别，判断其PartMaster的bsoID是否相同
  /**
   * 判断partIfcVector中的所有元素的使用的下一级子部件的使用差异情况。
   * 。
   * @param partIfcVector 零部件值对象集合。
   * @return Vector 返回值Vector中的元素是String[]:
   * <br>String[0]是partIfcVector中partIfc使用的子零件编号;
   * <br>String[1]...String[partIfcVector.size()]是每个partIfc使用同一个子部件的数量
   * 如果没有使用"false", 零件数量和false为String类型
   * @throws QMException
   */
  public Vector getDePart(Vector partIfcVector)
      throws QMException
  {
    PartDebug.trace(this, "getDePart() begin ....");
    //需要一个方法:给定partIfc，返回该partIfc所使用的partMasterIfc和partUsageLinkIfc的集合
    //方法getDocMastersByPartIfc，给出partIfc，返回partIfc关联的所有参考文档(DocMasterIfc)的集合
    int n = partIfcVector.size();
    String[] tempString = new String[n + 1];
    Vector mainVector = new Vector(10, 10);
    Vector tempResult = new Vector(10, 10);
    //对partIfcVector中的第一个元素进行处理，把关联的所有partMasterIfc的partNumber和
    //和使用数量组合在一起放到mainVector中
    tempResult = getPartMastersAndUsageLinks((QMPartIfc)partIfcVector.elementAt(0));
    //该处需要对tempResult进行判断!!!!!!::::
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

    //下面实现对partIfcVector中的从1到n-1的元素的处理
    Vector mainVector2 = null;
    for(int j = 1; j < n; j++)
    {
      mainVector2 = new Vector();
      tempResult = getPartMastersAndUsageLinks((QMPartIfc)partIfcVector.elementAt(j));
      //该处需要对tempResult进行判断!!!!!!::::
      if(tempResult != null && tempResult.size() > 0)
      {
        //先处理同一版本有多个相同子零件的情况
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
                //若子零件相同则合并其使用数量
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
              //如果tempPartMasterNum和vectorString[0]相同的话，合并，即把mainVector中的第
              //m个元素的值String[j+1]修改，String[j+1] = 使用数量，flag = true.修改完毕后，跳出本层循环。
              if(vectorString2[0].equals(vectorString[0]))
              {
                vectorString[j + 1] = vectorString2[1];
                mainVector.setElementAt(vectorString, o);
                flag1 = true;
                break;
              }
              //end if(tempPartMasterNum == vectorString[0])
              //如果tempPartMasterNum和vectorString[0]不相同的话，继续本层的循环，即：
              //继续for(int m=0; m<mainVector.size(); m++)
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
    //因为查到的是DocMasterIfc的集合，所以，在判断两个DocMaster是否相同的时候，只需要
    //判断其docNumber是否相同.
    //最后的返回值是Vector:vector vector中的每个元素应该是:
    //PartMasterNumber, boolean ,boolean ,... , boolean (n个String的boolean)
    //最后需要对mainVector中的元素进行处理，删除同一个BsoID下面的所有使用数量都相同的这个元素。
    Vector resultVector = new Vector();
    boolean flag = false;
    for(int i = 0; i < mainVector.size(); i++)
    {
      flag = false;
      tempString = new String[n + 1];
      tempString = (String[])mainVector.elementAt(i);
      for(int u = 1; u < tempString.length; u++)
      {
        //如果有是false的，加到结果集合中
        if(tempString[u].equals(""))
        {
          resultVector.addElement(tempString);
          flag = true;
          break;
        }//end if (tempString[u] == "false"
      }//end for (int u=1; u<tempString.length; u++)
      //如果对tempString[1]..[n]判断完后都不是"false"的话，就需要判断每个元素
      //转换为float类型后是否是相等的:不相等的话，加到结果集合中。
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
  //完成!!!!

  /**
   * 根据partIfc，获取partIfc使用的partMasterIfc，和关联类partUsageLinkIfc的集合。
   * 
   * @param partIfc 零部件值对象。
   * @return Vector 方法返回Vector 其中Collection的每个元素是个集合Object[]:
   * 这个子集合中的第一个元素是partUsageLinkIfc对象；
   * 第二个元素是partMasterIfc对象。
   * @see QMPartInfo
   * @throws QMException
   */
  private Vector getPartMastersAndUsageLinks(QMPartIfc partIfc)
      throws QMException
  {
    PartDebug.trace(this, "getPartMastersAndUsageLinks() begin ....");
    //没有必要!!!
    //查表就可以了,先调用PersistService中的
    //expandValueInfo(BaseValueIfc info,java.lang.String roleName,java.lang.String linkBsoName,
    //    boolean otherSideOnly) throws QMException

    //先返回的是PartUsageLinkIfc的集合，再根据partUsageLinkIfc.getLeftBsoID获取QMPartMasterIfc的BsoID，
    //然后就把QMPartMasterIfc的BsoID转换为QMPartMasterIfc

    //最后保存的是(PartUsageLinkIfc, QMPartMasterIfc)的集合
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
   * 根据指定的分割符，把输入的字符串截为一系列有业务意义的字符串。
   * 2003.08.14增加新的需求：可以对零部件的附加属性进行定制：
   * 这样，传过来的参数就应该对普通属性和扩展属性进行区分。
   * 做如下的区分"+viewName+partName-扩展属性名1-扩展属性名2"，即普通属性名前为"+"，
   * 扩展属性名前为"-"。
   * 这样，为了区分普通属性和扩展属性，需要修改该方法的返回值，由String[]修改为Vector
   * 
   * @param attrs : String
   * @return Vector Vector中含有两个元素，每个元素都是String[]类型，第一个String[]元素保存所有的普通属性名，
   * 后一个String[]保存扩展属性名。
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
    Vector result = new Vector(); //保存最后的结果：
    Vector temp1 = new Vector(); //保存所有的普通属性名
    Vector temp2 = new Vector(); //保存所有的扩展属性名
    String tempString1 = ""; //记录一个正在组装的普通属性名字符串
    String tempString2 = ""; //记录一个正在组装的扩展属性名字符串
    for(int i = 0; i < attrs.length(); i++)
    {
      String nowChar = attrs.substring(i, i + 1);
      //如果nowChar是+,-的话，让tempString记载从当前的
      if(nowChar.equals(" ") || nowChar.equals("-"))
      {
        //寻找下一个"+", "-"所在的位置
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
   * 根据离散的数据信息构造PartConfigSpecIfc对象,一共十二个参数,
   * 该方法调用StandardPartService中的同名的方法。
   * @param effectivityActive 是否是有效性配置规范 是1,否0
   * @param baselineActive 是否是基准线配置规范 是1,否0
   * @param standardActive 是否是标准配置规范 是1,否0
   * @param baselineID 基准线的BsoID
   * @param configItemID 配置规范的BsoID
   * @param viewObjectID 视图对象的bsoID
   * @param effectivityType 有效性类型
   * @param effectivityUnit 有效性单位(有效性点值)
   * @param state 生命周期状态
   * @param workingIncluded 是否在个人文件夹，默认为0:false,1:true
   * @return PartConfigSpecIfc 封装好的零部件配置规范值对象。
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
   * 根据输入的零部件主信息BsoID，和十个关于零部件配置规范的参数，
   * 返回符合零部件配置规范的，受该QMPartMasterIfc管理的所有零部件的集合。
   * @param partMasterID 零部件主信息BsoID
   * @param effectivityActive 是否是有效性配置规范 是1,否0
   * @param baselineActive 是否是基准线配置规范 是1,否0
   * @param standardActive 是否是标准配置规范 是1,否0
   * @param baselineID 基准线的BsoID
   * @param configItemID 配置规范的BsoID
   * @param viewObjectID 视图对象的bsoID
   * @param effectivityType 有效性类型
   * @param effectivityUnit 有效性单位(有效性点值)
   * @param state 生命周期状态
   * @param workingIncluded 是否在个人文件夹，默认为0:false,1:true
   * @return Vector 过滤出来的零部件值对象的集合，如果没有合格的零部件返回new Vector()。
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
    //第一步:恢复QMPartMasterIfc
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(partMasterID);
    //第二步：恢复零部件配置规范值对象：
    PartConfigSpecIfc configSpecIfc = hashtableToPartConfigSpecIfc(
        effectivityActive, baselineActive, standardActive, baselineID, configItemID,
        viewObjectID, effectivityType, effectivityUnit, state, workingIncluded);
    //第三步：根据两个参数调用StandardPartService中的方法filterdIterationsOf方法
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
   * 根据输入的零部件主信息BsoID，和零部件配置规范,
   * 返回符合零部件配置规范的，受该QMPartMasterIfc管理的所有零部件的集合。
   * @param partMasterID 零部件主信息BsoID。
   * @return Vector 过滤出来的零部件值对象的集合，如果没有合格的零部件返回new Vector()。
   * @exception QMException
   */
  public Vector filterIterations(String partMasterID)
      throws QMException
  {
    PartDebug.trace(this, "filterIterations() begin.....");
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
   * 根据指定的零部件返回下级零部件的最新版本的值对象的集合。
   * @param partID : String 零部件的BsoID；
   * @return collection:Collection partIfc使用的下级子件的最新版本的值对象集合。
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
   * 获取所有直接了使用当前partID对应的零部件的零部件,
   * 方法找到所有的使用该零件的集合。
   * @param partID :被使用的零部件的BsoID。
   * @return Vector 所有直接使用了partID对应的零部件的集合。
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
   * 根据零部件的BsoID获取零部件所对应的QMPartMaster的BsoID。
   * @param partID 零部件BsoID
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
   * 该方法用于显示“按结构添加有效性”的结果,根据输入的参数masterID，和配置规范的
   * 十个参数，先找出该masterID的符合配置规范的最新小版本的QMPartIfc；再根据这个
   * QMPartIfc找出所有该QMPartIfc使用的子零部件的有效性值，按照有效性方案进行合并处理。
   * 
   * @param masterID 零部件主信息BsoID
   * @param effectivityActive 是否是有效性配置规范 是1,否0
   * @param baselineActive 是否是基准线配置规范 是1,否0
   * @param standardActive 是否是标准配置规范 是1,否0
   * @param baselineID 基准线的BsoID
   * @param configItemID 配置规范的BsoID
   * @param viewObjectID 视图对象的bsoID
   * @param effectivityType 有效性类型
   * @param effectivityUnit 有效性单位(有效性点值)
   * @param state 生命周期状态
   * @param workingIncluded 是否在个人文件夹，默认为0:false,1:true
   * @return Vector 返回值Vector的数据结构为:Vector中的每个元素都是String[],String[]中的每个元素为:
   *<br> String[0]:零部件号码；
   *<br> String[1]:零部件名称；
   *<br> String[2]:零部件版本（大版本+小版本）；
   *<br> String[3]:视图名称；
   *<br> String[4]:有效性方案名称；
   *<br> String[5]:有效性类型；
   *<br> String[6]:有效性范围值（对日期有效性值截取到“天”）；
   *<br> String[7]:零部件的bsoID（因为在号码上有超链接，将零部件的bsoID也传过来）；
   *<br> String[8]:零部件的StandardIconName。
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

    //方法第一步:masterID -> QMPartMasterIfc,
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(masterID);

    //方法第二步:十个参数 -> PartConfigSpecIfc,
    PartConfigSpecIfc configSpecIfc = hashtableToPartConfigSpecIfc(
        effectivityActive, baselineActive, standardActive, baselineID, configItemID,
        viewObjectID, effectivityType, effectivityUnit, state, workingIncluded);

    //方法第三步:根据QMPartMasterIfc, PartConfigSpecIfc找到该QMPartMasterIfc的
    //符合配置规范的所有子节点零部件：QMPartIfc EnterprisePartService.getAllSubPartsByConfigSpec()
    QMPartIfc[] partArray = PartServiceRequest.getAllSubPartsByConfigSpec(partMasterIfc, configSpecIfc);
    //方法第四步:调用EnterprisePartService中的方法
    //getEffVector(QMPartIfc)方法，获取单个QMPartIfc的所有EffGroup[],就可以先对
    //所有子零部件QMPartIfc进行循环，该循环中再对EffGroup[]进行循环，组装最后的结果。
    Vector resultVector = new Vector();
    for(int i = 0; i < partArray.length; i++)
    {
      QMPartInfo partInfo = (QMPartInfo)partArray[i];
      Vector effGroups = PartServiceRequest.getEffVector(partInfo);
      //针对一个零部件的所有有效性值(所有的EffGroup对象的集合)进行循环：
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
            //需要按照effGroup对象中的type:String这个字符串来对
            //effType的具体的类型进行指定：
            //这段代码最好可以不写死：!!!!
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
          //日期型有效性:
          //configItemName = "";
          effType = (EffectivityType.DATE).getDisplay();
        }
        //end if and else (effGroup.getEffContext() != null)
        //需要对日期类型的range进行处理:（对日期有效性值截取到“天”）
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
   * 该方法用于显示“按结构添加有效性”的结果,根据输入的参数masterID，和配置规范
   * 先找出该masterID的符合配置规范的最新小版本的QMPartIfc；再根据这个
   * QMPartIfc找出所有该QMPartIfc使用的子零部件的有效性值，按照有效性方案进行合并处理。
   * 
   * @param masterID 零部件主信息BsoID
   * @return Vector 返回值Vector的数据结构为:Vector中的每个元素都是String[],String[]中的每个元素为:
   *<br> String[0]:零部件号码；
   *<br> String[1]:零部件名称；
   *<br> String[2]:零部件版本（大版本+小版本）；
   *<br> String[3]:视图名称；
   *<br> String[4]:有效性方案名称；
   *<br> String[5]:有效性类型；
   *<br> String[6]:有效性范围值（对日期有效性值截取到“天”）；
   *<br> String[7]:零部件的bsoID（因为在号码上有超链接，将零部件的bsoID也传过来）；
   *<br> String[8]:零部件的StandardIconName。
   * @throws QMException
   */
  public Vector getPopulateEffectivities(String masterID,String itemName)
      throws QMException
  {
    PartDebug.trace(this, "getPopulateEffectivities() begin.....");
    EffService effService = (EffService)EJBServiceHelper.getService("EffService");
    //方法第一步:masterID -> QMPartMasterIfc,
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartMasterIfc partMasterIfc = (QMPartMasterIfc)pService.refreshInfo(masterID);


    //方法第二步:PartConfigSpecIfc,
    PartConfigSpecIfc configSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();

    //方法第三步:根据QMPartMasterIfc, PartConfigSpecIfc找到该QMPartMasterIfc的
    //符合配置规范的所有子节点零部件：QMPartIfc EnterprisePartService.getAllSubPartsByConfigSpec()
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

    //方法第四步:调用EnterprisePartService中的方法
    //getEffVector(QMPartIfc)方法，获取单个QMPartIfc的所有EffGroup[],就可以先对
    //所有子零部件QMPartIfc进行循环，该循环中再对EffGroup[]进行循环，组装最后的结果。
    Vector resultVector = new Vector();
    for(int i = 0; i < partArray.length; i++)
    {
      QMPartInfo partInfo = (QMPartInfo)partArray[i];
      Vector effGroups = PartServiceRequest.getEffVector(partInfo,item.getBsoID());
      //针对一个零部件的所有有效性值(所有的EffGroup对象的集合)进行循环：
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
            //需要按照effGroup对象中的type:String这个字符串来对
            //effType的具体的类型进行指定：
            //这段代码最好可以不写死：!!!!
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
          //日期型有效性:
          //configItemName = "";
          effType = (EffectivityType.DATE).getDisplay();
        }
        //end if and else (effGroup.getEffContext() != null)
        //需要对日期类型的range进行处理:（对日期有效性值截取到“天”）
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
   * 获取按结构添加有效性的结果，供"按结构添加有效性结果"页面显示用。
   * 以前该页面调用的是带有一个参数的getPopulateEffectivities方法，
   * 由于需求提出只显示当前添加的有效性方案的记录，而不显示其他已添加过值的有效性方案的记录，
   * 故改为调用此方法。
   * @param masterID String
   * @param itemName String
   * @param type String
   * @throws QMException
   * @return Vector 返回值Vector的数据结构为:Vector中的每个元素都是String[],String[]中的每个元素为:
   *<br> String[0]:零部件号码；
   *<br> String[1]:零部件名称；
   *<br> String[2]:零部件版本（大版本+小版本）；
   *<br> String[3]:视图名称；
   *<br> String[4]:有效性方案名称；
   *<br> String[5]:有效性类型；
   *<br> String[6]:有效性范围值（对日期有效性值截取到“天”）；
   *<br> String[7]:零部件的bsoID（因为在号码上有超链接，将零部件的bsoID也传过来）；
   *<br> String[8]:零部件的StandardIconName。
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
   * 根据零部件BsoID获取该零部件的所有的基本属性集合。
   * 
   * @param partBsoID 零部件BsoID
   * @return String[] 返回值是一个字符串数组:
   * <br>String[0]:零部件编号；
   * <br>String[1]:版本+版序；
   * <br>String[2]:零部件名称；
   * <br>String[3]:视图名称；
   * <br>String[4]:所在文件夹(存放位置)；
   * <br>String[5]:单位；
   * <br>String[6]:生命周期名字；
   * <br>String[7]:来源；
   * <br>String[8]:项目组；
   * <br>String[9]:类型；
   * <br>String[10]:生命周期状态；
   * <br>String[11]:状态(检入，检出状态)；
   * <br>String[12]：创建者；
   * <br>String[13]：更新者；
   * <br>String[14]：创建时间；
   * <br>String[15]：更新时间；
   * <br>String[15]：小版本注释。
   * @throws QMException
   */
  public String[] getChineseAttr(String partBsoID)
      throws QMException
  {
    PartDebug.trace(this, "getChineseAttr(" + partBsoID + "): begin... ");
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partBsoID);
        //CCBegin by liunan 2008-07-24
    //原代码如下
    //String[] result = new String[17];
    String[] result = new String[18];
    //CCEnd by liunan 2008-07-24
    result[0] = partIfc.getPartNumber();
    result[1] = partIfc.getVersionValue();
    result[2] = partIfc.getPartName();
    //CCBegin SS10
    if(partIfc.getPartName().indexOf("（见")>0)
    {
    	String partName = partIfc.getPartName();
    	String otherPartNum = partName.substring(partName.indexOf("（见")+2,partName.length()-1);
    	System.out.println("otherPartNum============"+otherPartNum);
    	String otherPartID = PublishHelper.getPartBsoID(otherPartNum);
    	if(otherPartID!=null)
    	  result[2] = partName.substring(0,partName.indexOf("（见"))+"<a href=\"Part-Other-PartLookOver-001.screen?bsoID="+otherPartID+"\">"+partName.substring(partName.indexOf("（见"),partName.length())+"</a>";
    	System.out.println("result[2]============"+result[2]);   	
    }
    if(partIfc.getPartName().indexOf("(见")>0)
    {
    	String partName = partIfc.getPartName();
    	String otherPartNum = partName.substring(partName.indexOf("(见")+2,partName.length()-1);
    	System.out.println("otherPartNum============"+otherPartNum);
    	String otherPartID = PublishHelper.getPartBsoID(otherPartNum);
    	if(otherPartID!=null)
    	  result[2] = partName.substring(0,partName.indexOf("(见"))+"<a href=\"Part-Other-PartLookOver-001.screen?bsoID="+otherPartID+"\">"+partName.substring(partName.indexOf("(见"),partName.length())+"</a>";
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
    //生命周期名称::根据LifeCycleTemplate类的BsoID，获取其值对象，
    //再获取值对象的生命周期名字。
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
    //需要先把该字符串转化成枚举类型，再获取中文字符串:
    String tempWorkableState = partIfc.getWorkableState();
    if(tempWorkableState != null && tempWorkableState.length() > 0)
    {
        //问题(3)20081014 张强修改 begin 修改原因:当刚创建零部件时，工作状态应该显示为“未检入”。
//      WorkInProgressState wState = WorkInProgressState.toWorkInProgressState(tempWorkableState);
//      result[11] = wState.getDisplay();
      WorkInProgressHelper workInProgressHelper=new WorkInProgressHelper();
      result[11] = workInProgressHelper.getStatus(partIfc);
      //问题(3)20081014 张强修改 end
    }
    else
    {
      result[11] = "";
    }

    //获得创建者
    String creater = partIfc.getIterationCreator();
    result[12] = getUserNameByID(creater);
    //获得更新者
    String modifier = partIfc.getIterationModifier();
    result[13] = getUserNameByID(modifier);
    result[14] = partIfc.getCreateTime().toString();
    result[15] = partIfc.getModifyTime().toString();
    result[16] = partIfc.getIterationNote();
        //CCBegin by liunan 2008-07-24
    if(partIfc.getColorFlag())
    {
      result[17] = "是";
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


//add by 孙珂鑫 2004.7.11
  /**
   * 获取零部件的工作状态信息(己检入/由XX检出/XX的工作副本/在个人文件柜)。
   * @param partInfo QMPartIfc
   * @return String 零部件的工作状态信息。
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
          partInfo); //是否是工作副本
      if (flag)
      {
          try
          {
              WorkableIfc originalcopy = wipService.originalCopyOf( (
                  WorkableIfc) partInfo); //获得原本拷贝
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
   * 由bsoid获得其内容（针对文档）。
   * @param docBsoID String
   * @throws QMException
   * @return Vector 获取的内容(HolderAndContentInfo)集合
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
   * 根据零部件id获取相关的epm文档信息。
   * @param partBsoID 零部件id标识
   * @return  String[] 数组元素：
   *<br> 0.epm文档编号；
   * <br>1.epm文档版本；
   * <br>2.epm文档名称；
   * <br>3.epm文档所在资料夹；
   *<br> 4.epm文档所使用的生命周期模板名称
   *<br> 5.epm文档工作组名称
   *<br> 6.epm文档类型的显示名称
   *<br> 7.epm文档的生命周期状态名称
   *<br> 8.epm文档对象使用情况的状态的显示名称
   *<br> 9.epm文档作者应用程序类型的显示名称
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
    //生命周期名称::根据LifeCycleTemplate类的BsoID，获取其值对象，
    //再获取值对象的生命周期名字。
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
    //需要先把该字符串转化成枚举类型，再获取中文字符串:
    String tempWorkableState = epmDoc.getWorkableState();
    if(tempWorkableState != null && tempWorkableState.length() > 0)
    {
        //问题(3)20081014 张强修改 begin 修改原因:当刚创建零部件时，工作状态应该显示为“未检入”。
//      WorkInProgressState wState = WorkInProgressState.toWorkInProgressState(tempWorkableState);
//      result[8] = wState.getDisplay();
      WorkInProgressHelper workInProgressHelper=new WorkInProgressHelper();
      result[8] = workInProgressHelper.getStatus(epmDoc);
      //问题(3)20081014 张强修改 end
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
   * 根据用户BsoID获取用户名全称。
   * @param userID 用户的BsoID
   * @return String 用户名
   * @throws QMException
   */
  public static String getUserNameByID(String userID)
      throws QMException
  {
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    UserInfo userInfo = (UserInfo)pService.refreshInfo(userID);
    if(userInfo == null)
        return null;
    //改为显示用户全称，liun   2005.06.14
    String userName = userInfo.getUsersDesc();
    return userName;
  }

  /**
   * 根据零部件的BsoID获取该零部件上的所有的有效性方案的名称，类型，有效性值范围。
   * 
   * @param partBsoID 零部件的BsoID。
   * @return Vector 返回的Vector中的每个元素都是String[]：
   * <br>String[0]:有效性方案名称，如果默认日期有效性，可以不指定有效性方案名；
   * <br>String[1]:有效性方案类型，如果没有有效性方案的名称，则默认为"日期有效性"；
   * <br>String[2]:有效性值范围。
   * @throws QMException
   */
  public Vector getLookEff(String partBsoID)
      throws QMException
  {
    return getLookEff(partBsoID, true);
  }

  /**
   * 根据零部件的BsoID获取该零部件上的所有的有效性方案的名称，类型，有效性值范围。
   * 
   * @param partBsoID String 零部件的BsoID。
   * @param isacl boolean
   * @throws QMException
   * @return Vector 返回的Vector中的每个元素都是String[]：
   * <br>String[0]:有效性方案名称，如果默认日期有效性，可以不指定有效性方案名；
   * <br>String[1]:有效性方案类型，如果没有有效性方案的名称，则默认为"日期有效性"；
   * <br>String[2]:有效性值范围。
   */
  public Vector getLookEff(String partBsoID, boolean isacl)
      throws QMException
  {
    PartDebug.trace(this, "getLookEff() begin......");
    //先调用EnterprisePartService中的方法getEffVector(QMPartInfo):Vector
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    EffService effService = (EffService)EJBServiceHelper.getService("EffService");
    EffectivityManageableIfc partInfo = (EffectivityManageableIfc)pService.refreshInfo(partBsoID);
    Vector effGroups = PartServiceRequest.getEffVector((WorkableIfc)partInfo, isacl);
    Vector resultVector = new Vector();
    //对effGroups中的所有的EffGroup对象进行循环，组装结果集合：
    for(int i = 0; i < effGroups.size(); i++)
    {
      EffGroup effGroup = (EffGroup)effGroups.elementAt(i);
      EffContextIfc effContextIfc = effGroup.getEffContext();
      String[] tempString = new String[3];
      //如果effContextIfc为空的话，一定是日期的：
      if(effContextIfc == null)
      {
        tempString[0] = "";
        tempString[1] = EffectivityType.DATE.getDisplay(); //日期有效性方案
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
          //这里的type有可能为空：
          EffectivityType type = configItemIfc.getEffectivityType();
          if(type != null)
          {
            tempString[1] = type.getDisplay();
          }
          else
          {
            //需要根据EffGroup中保存的具体的有效性配置项的类型来对tempString[1]进行赋值：
            //需要按照effGroup对象中的type:String这个字符串来对
            //effType的具体的类型进行指定：
            //这段代码最好可以不写死：!!!!
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
   * “在指定结构中可被下列零部件替换”调用此方法。
   * @return vector:每个元素为一个有十个字符串的数组，顺序记录了
   * 在指定结构中可被替换的零部件主信息(number  name)、
   * 父件（QMPart）信息(number  name version view)及BsoID(masterBsoID partBsoID)
   * 和图标（part和master的StandardIconName）。
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
      //获得part集合中最新小版本的part对象集合，如果有的最新小版本对象处于检出
      //状态，判断是否被当前用户检出：如果是当前用户检出的，返回工作副本；
      //如果是其他用户检出的，返回工作原本
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
        //根据part和master获得关联类对象集合
        Collection col2 = pService.findLinkValueInfo("PartUsageLink", part, "usedBy", master);
        Iterator iterator = col2.iterator();
        //根据part和master只能唯一确定一个PartUsageLinkInfo对象
        if(iterator.hasNext())
        {
          usageLink = (PartUsageLinkInfo)iterator.next();
          usageLinkID = usageLink.getBsoID();
        }
        //根据指定的结构获得替换件的集合

        //根据指定的结构获得替换件的集合
        //应该是根据PartUsageLinkID->PartUsageLinkInfo->根据关联类
        //PartSubstituteLink和一边的对象PartUsageLinkInfo查找另一边的
        //QMPartMasterInfo的集合。
        collection1 = getSubstitutesPartMasters(usageLinkID);
        if(collection1 != null && collection.size() > 0)
        {
          Iterator iterator1 = collection1.iterator();
          QMPartMasterInfo master1 = new QMPartMasterInfo();
          //通过循环将在指定结构中可被替换的零部件主信息(QMPartMaster)及
          //父件（QMPart）信息放入字符串数组a[8]中
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
   * “在指定结构中是下列零部件的替换件”调用此方法。
   * @param masterID 零部件主信息的bsoID。
   * @return vector:每个元素为一个有十个字符串的数组，顺序记录了。
   * 在指定结构中可被替换的零部件主信息(number  name)、
   * 父件（QMPart）信息(number  name version view）及BsoID(masterBsoID partBsoID)
   * 图标(part和master的standardIconName)。
   * @throws QMException
   */
  public static Vector getSubstitutedBy(String masterID)
      throws QMException
  {
    try
    {
      PartDebug.trace("getSubstituteBy() begin...");
      PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
      //根据QMPartMaster的BsoID获取QMPartMasterInfo：
      QMPartMasterInfo valueInfo = (QMPartMasterInfo)pService.refreshInfo(masterID);
      //通过master对象找到PartUsageLinkInfo的对象集合
      Collection collection = pService.navigateValueInfo(valueInfo, "substitutes", "PartSubstituteLink", true);
      if(collection != null && collection.size() > 0)
      {
        Iterator iterator = collection.iterator();
        PartUsageLinkInfo usageLink = null;
        QMPartInfo part = null;
        QMPartMasterInfo master = null;
        Vector vector = new Vector();
        //保存collection中的所有PartUsageLink管理的所有的QMPartInfo：
        Vector partVector = new Vector();
        //保存所有的PartUsageLinkInfo:
        Vector usageLinkVector = new Vector();
        //将part和usageLink分别放在partVector和usageLinkVector中
        while(iterator.hasNext())
        {
          usageLink = (PartUsageLinkInfo)iterator.next();
          part = (QMPartInfo)usageLink.getUsedBy();
          partVector.add(part);
          usageLinkVector.add(usageLink);
        }
        //获得part集合中最新小版本的part对象集合，如果有的最新小版本对象处于检出
        //状态，判断是否被当前用户检出：如果是当前用户检出的，返回工作副本；
        //如果是其他用户检出的，返回工作原本
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
        //最好，对usageLinkVector中的所有的元素进行过滤，对其中的有相同的
        //QMPartMasterID的那些PartUsageLinkInfo，只保留一个：
        for(int i = 0; i < usageLinkVector.size(); i++)
        {
          part = (QMPartInfo)((PartUsageLinkInfo)(usageLinkVector.elementAt(i))).getUsedBy();
          //需要判断part是否在partVector1中：
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
        //需要对vector中的所有元素进行过滤：
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
   * 获得parts集合中最新小版本的part对象，如果最新小版本对象处于检出
   * 状态，判断是否被当前用户检出：如果是当前用户检出的，返回工作副本；
   * 如果是其他用户检出的，返回工作原本。
   * @param parts Vector 零部件值对象集合
   * @return Vector 符合条件并且经过过滤后的零部件(QMPartInfo)集合
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
    //对parts集合中的每个QMPartInfo进行判断，先找出其QMPartMasterInfo，再根据
    //该QMPartMasterInfo获取所有的该QMPartMasterInfo管理的QMPartInfo的集合，
    //最后按照业务逻辑返回QMPartInfo集合中的最新小版本的零部件值对象。
    for(int i = 0; i < parts.size(); i++)
    {
      Object obj = parts.elementAt(i);
      if(obj instanceof QMPartInfo)
      {
        part = (QMPartInfo)obj;
        master = (QMPartMasterInfo)part.getMaster();
        try
        {
          //调用版本控制服务
          VersionControlService vcService = (VersionControlService)EJBServiceHelper.getService("VersionControlService");
          //获得master的所有大版本的对应的最新小版本对象集合
          //需要对该集合中的所有的零部件值对象进行循环判断。
          Collection col = vcService.allVersionsOf(master);
          Iterator iterator1 = col.iterator();
          //对所有的col中的元素进行循环：
          while(iterator1.hasNext())
          {
            //获得最新版本的part对象
            part = (QMPartInfo)iterator1.next();
            //调用会话服务
            SessionService sService = (SessionService)EJBServiceHelper.getService("SessionService");
            //获得当前用户
            UserInfo user = (UserInfo)sService.getCurUserInfo();
            //调用wip服务
            WorkInProgressService wipService = (WorkInProgressService)EJBServiceHelper.getService("WorkInProgressService");
            //判断part是否处于检出状态
            boolean flag = WorkInProgressHelper.isCheckedOut(part);
            //如果被检出
            if(flag)
            {
              //判断part是否被当前用户检出
              boolean flag1 = WorkInProgressHelper.isCheckedOut(part, user);
              //如果是被当前用户检出，继续判断part是否是工作副本
              //如果不是，获得工作副本
              if(flag1)
              {
                boolean flag2 = WorkInProgressHelper.isWorkingCopy(part);
                if(!flag2)
                {
                  //获得part对象的工作副本
                  part = (QMPartInfo)wipService.workingCopyOf(part);
                }
              }
              //如果被其他用户检出，获得工作原本
              else
              {
                  boolean iscopy = WorkInProgressHelper.isWorkingCopy(part);
               if(iscopy)
                part = (QMPartInfo)wipService.originalCopyOf(part);
              }
            }
            //如果resultVector中不包含part，将part放入vector中
            //需要判断part是否在vector中：
            String partName = part.getPartName();
            String partNumber = part.getPartNumber();
            String versionValue = part.getVersionValue();
            //当falgDeside为false的时候，把当前的part加到最后的结果vector中
            //否则，不加
            boolean flagDeside = false;
            for(int m = 0; m < resultVector.size(); m++)
            {
              QMPartInfo tempPartInfo = (QMPartInfo)resultVector.elementAt(m);
              //如果vector已经有了该零部件，不加，否则，加：
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

 
 
  //2003/11/05 web页面应该根据part的具体状态来显示其图标
  /**
   * 根据part的bsoID获得相应状态的图标。
   * @param bsoID String
   * @throws QMException
   * @return String 图标的路径
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
			//原本图标格式
			if (state.equals("c/o")) {
				s = "images/genericPart.gif";				
			}
			//标准图标格式
			if (state.equals("c/i")) {
				s = "images/genericPart.gif";				
			}
			//副本图标格式
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
        //标准图标格式
        String state = workable.getWorkableState();
        //原本图标格式
        if(state.equals("c/o"))
        {
          s = "images/part_originalIcon.gif";
          //标准图标格式
        }
        if(state.equals("c/i"))
        {
          s = "images/part.gif";
          //副本图标格式
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

  //新需求添加  2004/02/04   丛得成



  //(add by 孙贤民)
  /**
   * 获取上一次的配置规范。
   * @return Object[] 配置规范元素信息:
   *<br> 0.是否是标准配置规范“1”表示是；
   *<br> 1.在标准配置规范下的视图名称
   *<br> 2.标准配置规范下生命周期状态
   *<br> 3.标准配置规范下是否包含个人资料夹项“1”表示是；
   *<br> 4.是否是基线配置规范“1”表示是；
   *<br> 5.基线名称
   *<br> 6.是否是有效性配置规范“1”表示是；
   *<br> 7.有效性配置规范下的视图名称；
   *<br> 8.有效性配置规范下的配置项名称
   *<br> 9.有效性配置规范下的有效性类型显示名称；
   *<br> 10.有效性配置规范下的有效性单位
   * @throws QMException
   */
  public Object[] getOldConfigSpec()
      throws QMException
  {
    PartConfigSpecIfc config = getCurrentConfigSpec();
    return setCurrentConfigSpec(config);
  }

  /**
   * 获取当前的配置规范，如果用户是首次登陆系统，则构造默认的“标准”配置规范。
   * @throws QMException 使用ExtendedPartService时会抛出。
   * @return PartConfigSpecIfc 标准配置规范。
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
        ViewObjectIfc viewObjectIfc=vs.getView("工程视图");
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
   * 把配置规范的元素信息设置到结果数组中，以便在瘦客户显示。
   * @param configSpec PartConfigSpecIfc 配置规范。
   * @return Object[] 配置规范元素信息。
   *<br> 0.是否是标准配置规范“1”表示是；
   *<br> 1.在标准配置规范下的视图名称
   *<br> 2.标准配置规范下生命周期状态
   *<br> 3.标准配置规范下是否包含个人资料夹项“1”表示是；
   *<br> 4.是否是基线配置规范“1”表示是；
   *<br> 5.基线名称
   *<br> 6.是否是有效性配置规范“1”表示是；
   *<br> 7.有效性配置规范下的视图名称；
   *<br> 8.有效性配置规范下的配置项名称
   *<br> 9.有效性配置规范下的有效性类型显示名称；
   *<br> 10.有效性配置规范下的有效性单位
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
      //20080401 zhangq begin:当没有有效性方案时，有效性类型为日期类型。
      if(qmConfigurationItem == null) 
          obj[9] =EffectivityType.DATE.getDisplay(Locale.CHINA);
      //20080401 zhangq end
      obj[10] = effectivity.getEffectiveUnit();
    }
    obj[11] = configSpec.getBsoID();
    return obj;
  }

  /**
   * 查找所有的生命周期状态。
   * @return Vector 生命周期状态(String)集合
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
   * 查找所有的有效性类型。
   * @return Collection 有效性类型（String）集合
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
   * 检索符合条件的基准线。
   * @param name 基准线名。
   * @return Collection 符合条件的基准线（ManagedBaselineInfo）集合。
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
   * 检索符合条件的有效性配置项。
   * @param effname 有效性名称。
   * @param efftype 有效性类型。
   * @return 符合条件的有效性(QMConfigurationItemInfo)集合。
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
   * 可以添加更多的字符替换。
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
   * 查找所有的视图名称。
   * @return Collection 视图值对象(ViewObjectInfo)集合
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
  * 根据用户的id获取用户的显示名称。
  * @param userID
  * @return 用户名(String)
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
   * 给定事物特性持有者的id,获得其iba属性值等。
   * @param ibaHolderBsoID String
   * @return Vector 其中的元素为二维字符串数组,第一维存放该iba属性值的属性定义的名称,
   * 第二维存放iba属性的值。
   */
  public Vector getIBANameAndValue(String ibaHolderBsoID)
  {
    IBAHolderIfc ibaHolderIfc = null;
    try {
      PersistService pService = (PersistService) EJBServiceHelper.getService(
          "PersistService");
       ibaHolderIfc = (IBAHolderIfc) pService.refreshInfo(ibaHolderBsoID);
       IBAValueService ibaService = (IBAValueService)EJBServiceHelper.getService("IBAValueService");
       //CCBegin by liunan 2009-01-14 汽研epm的事物特性单独保存，不从关联part中获取。
       //掩码如下:
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
          //修改4 20081028 zhangq begin 修改原因：零部件瘦客户查看界面带单位的浮点数类型属性显示单位为属性定义的量纲而非默认单位
          String unitStr = "";
          if(value instanceof UnitValueDefaultView)
          {
              UnitDefView definition1 = ((UnitValueDefaultView)value).getUnitDefinition();
              QuantityOfMeasureDefaultView quantityofmeasuredefaultview = definition1.getQuantityOfMeasureDefaultView();
              if (measurementSystem != null)
              {
                  //得到属性定义的单位
                  unitStr = definition1.getDisplayUnitString(
                          measurementSystem);
                  //得到计量单位中的显示单位
                  if (unitStr == null)
                  {
                      unitStr = quantityofmeasuredefaultview.
                           getDisplayUnitString(measurementSystem);
                  }
                  //得到计量单位中的量纲
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
              //属性值和单位中间用空格分开
              nameAndValue[1] = ss+"  "+unitStr;
          }
          //修改4 20081028 zhangq end
          else
          {
              nameAndValue[1] = ((AbstractContextualValueDefaultView)
                                 value).
                                getValueAsString();
              //CCBegin SS10
              if(nameAndValue[0].equals("文档信息")&&nameAndValue[1]!=null&&nameAndValue[1].indexOf("见")!=-1)
              {
              	String otherPartNum = nameAndValue[1].substring(nameAndValue[1].indexOf("见")+1,nameAndValue[1].length());
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
              if(nameAndValue[0].equals("文档信息")&&nameAndValue[1]!=null&&nameAndValue[1].indexOf("参见")!=-1)
              {
              	String otherPartNum = nameAndValue[1].substring(nameAndValue[1].indexOf("参见")+2,nameAndValue[1].length());
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
   * 由epm文档获取其关联的零部件。
   * @param doc EPMDocumentIfc epm文档。
   * @throws QMException
   * @return QMPartIfc 零部件对象
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
   *根据id获取epm文档对象
   * @param bsoID String
   * @throws QMException
   * @return EPMDocumentInfo epm文档对象
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
   * 获得另存为历史部件。
   * @param partID String
   * @return Collection 零部件历史部件(QMPartInfo)集合
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
  // add by 孙珂鑫
  /**
   *
   * 获得第一级衍生出零部件。
   * @param partID String
   * @return Collection 零部件对象(QMPartInfo)集合
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
   * 获取生命周期历史记录。
   * @param bsoid String
   * @throws QMException
   * @return Vector 生命周期历史
   * @see LifeCycleServiceHelper#getObjectHistorys(String)
   */
  //xdj 生命周期服务对工作副本不支持
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
   * 属性比较 2005.4.19。
   * @param ids String 一个以“:”分割零部件id组合的字串
   * @throws QMException
   * @return Vector 一个字串数组的集合，包括5类数组元素，每个数组的大小取决于参数中包含id个数。依次为
   * <br>title:0."属性"；1.零部件编号+名称+"V"+版本（后面的元素同此）
   * <br>type:0."类型"；1.零部件类型显示名（后面的元素同此）；
   * <br>source:0."来源"1.来源显示名（后面的元素同此）；
   * <br>state：0.“生命周期状态”1.状态显示名（后面的元素同此）；
   * <br>content：0.iba定义名称1.iba定义对应的值
   * 其中content可能包括多个，取决于iba定义的多少
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
                      value = value + "； "+list.get(p);
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
   * 获取IBA比较结果。
   * @param ids String 一个以":"分隔的零部件id组合字串
   * @throws QMException
   * @return Vector 元素为String[]数组，String[]元素：
   * 0.iba定义名称；其他元素为对应的值
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

        //对每个属性名称进行循环。
        for(int m = 0; m < ibaNames.size(); m++)
        {
            String definitionName = (String)ibaNames.elementAt(m);
            String content[] = new String[length+1];

            //把属性名称放入数组首位，并依次得到比较的各个版本属性值。
            boolean flag1 = false;
            for(int k=0; k<length; k++)
            {
                tempMap = (HashMap)table.get(title[k]);
                ArrayList list = (ArrayList) tempMap.get(definitionName);
                    if (tempMap.containsKey(definitionName))
                    {
                        //list中时属性名称为definitionName的各版本属性值的集合。
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
                                value = value + "； " + list.get(p);
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
 * 获取零部件的标准、原本、副本状态的图标路径。
 * @param obj 零部件值对象
 * @return String 图标路径名
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
			//原本图标格式
			if (state.equals("c/o")) {
				s = "images/genericPart.gif";				
			}
			//标准图标格式
			if (state.equals("c/i")) {
				s = "images/genericPart.gif";				
			}
			//副本图标格式
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
        //标准图标格式
        String state = workable.getWorkableState();
        //原本图标格式
        if(state.equals("c/o"))
        {
          s = "images/part_originalIcon.gif";
          //标准图标格式
        }
        if(state.equals("c/i"))
        {
          s = "images/part.gif";
          //副本图标格式
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
   * 根据资料夹的id获取一个封装信息的集合。
   * @param folderID String 资料夹id
   * @return Collection 如果参数id为空，获取用户的个人资料夹，并封装的数组中；0.资料夹id；1.资料夹名称；2.“Y”；并将数组添加到集合中返回。
   * 如果参数id不为空，集合第一个元素数组为：0.“”；1."\\.."；2."Y"。其后的元素数组是封装了所以子资料夹的信息：0.资料夹id；1.资料夹名称；2.“Y”。
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
   * 是否安装了pcfg组件。
   * @return String “true”，“false”
   */
  public String hasPcfg()
  {
      String hasPcfg = RemoteProperty.getProperty("com.faw_qm.hasPcfg" , "true");
      return hasPcfg;
  }
  /**
   * 获取注册成所给零部件的那个广义部件的bsoID。
   * @param bsoID String 零部件bsoID
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
   * 添加受基准线管理的对象到基准线，根据这个对象的结构筛选条件递归实现。
   * @param partbso :String 待添加的受基准线管理的对象。
   * @param baselinebso :String 基准线值对象。
   * @return Vector QMPartIfc的bsoID字串集合。
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
   * 通过masterid获取最新版本的零部件。
   * @param masterID masterid
   * @return QMPartInfo 最新版本的零部件(QMPart);
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
   * 取得所有有效性方案的名称组成一个以“，”分隔的字串。
   * @return 所有有效性方案的名称组成一个以“，”分隔的字串
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
  
  //问题(1)20080228 zhangq begin:判断有效性方案的类型和选择的类型是否一致，如果不一致则弹出提示信息。
  /**
   * 获取包含所有有效性方案信息的数组。
   * @return String[]  有两个元素;
   * 第一个元素是所有有效性方案的名称组成的以“，”分隔的字串，
   * 第二个元素是所有有效性方案的类型组成的以“，”分隔的字串，
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
    //问题(1)20080228 zhangq end
  
    /**
     * 添加受基准线管理的对象到基准线，根据这个对象的结构筛选条件递归实现。
     * @param partbso :String 待添加的受基准线管理的对象。
     * @param baselinebso :String 基准线值对象。
     * @return Vector QMPartIfc的bsoID字符串集合。
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
     * 在创建/更新文档时，将文档添加为零部件的参考文档。
     * @param parts 零部件BsoID集合。
     * @param docMasterBsoID 文档主信息BsoID。
     * @return String 提示信息。
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
            //零部件已被当前用户检出或在个人资料夹中。
            if(CheckInOutTaskLogic.isCheckedOutByUser(partIfc)
                    || !CheckInOutTaskLogic.isInVault(partIfc))
            {
                linkIfc = new PartReferenceLinkInfo();
                linkIfc.setLeftBsoID(docMasterBsoID);
                linkIfc.setRightBsoID(partBsoID);
                PartServiceRequest.savePartReferenceLink(linkIfc);
                //"成功添加为 * 的参考文档。\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M001", new Object[] {partIfc.getIdentity()});
            }
            //零部件已被其他用户检出。
            else if(CheckInOutTaskLogic.isCheckedOutByOther(partIfc))
            {
                //"* 已被用户 * 检出！\n"
                message = message
                        + QMMessage.getLocalizedMessage(resource, "M003",
                                new Object[]{partIfc.getIdentity(),
                                        getUserNameByID(partIfc.getLocker())});
            }
            //当前用户有修改零部件的权限。检出零部件，创建关联，检入零部件。
            else if(CheckInOutTaskLogic.isCheckoutAllowed(partIfc))
            {
                CheckoutLinkInfo checkoutLinkInfo = CheckInOutTaskLogic
                        .checkOutObject(partIfc, CheckInOutTaskLogic
                                .getCheckoutFolder(), "");
                //工作副本BsoID。
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
                //"成功添加为 * 的参考文档。\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M001", new Object[] {workableIfc.getIdentity()});
            }
            //当前用户没有修改零部件的权限。
            else
            {
                //"您没有修改 * 的权限，不能为此零部件添加参考文档！\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M004", new Object[] {partIfc.getIdentity()});
            }
        }
        return message;
    }

    /**
     * 在创建/更新文档时，将文档添加为零部件的描述文档。
     * @param parts 零部件BsoID集合。
     * @param docBsoID 文档值对象BsoID。
     * @return String 提示信息。
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
            //零部件已被当前用户检出或在个人资料夹中。
            if(CheckInOutTaskLogic.isCheckedOutByUser(partIfc)
                    || !CheckInOutTaskLogic.isInVault(partIfc))
            {
                linkIfc = new PartDescribeLinkInfo();
                linkIfc.setLeftBsoID(partBsoID);
                linkIfc.setRightBsoID(docBsoID);
                PartServiceRequest.savePartDescribeLink(linkIfc);
                //"成功添加为 * 的描述文档。\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M002", new Object[] {partIfc.getIdentity()});
            }
            //零部件已被其他用户检出。
            else if(CheckInOutTaskLogic.isCheckedOutByOther(partIfc))
            {
                //"* 已被用户 * 检出！\n"
                message = message + QMMessage.getLocalizedMessage(resource, "M003",
                        new Object[]{partIfc.getIdentity(),
                        getUserNameByID(partIfc.getLocker())});
            }
            //当前用户有修改零部件的权限。检出零部件，创建关联，检入零部件。
            //CCBegin SS4
            else if(dsh.isBSXGroup()){
                linkIfc = new PartDescribeLinkInfo();
                linkIfc.setLeftBsoID(partIfc.getBsoID());
                linkIfc.setRightBsoID(docBsoID);
                PartServiceRequest.savePartDescribeLink(linkIfc);
                //"成功添加为 * 的描述文档。\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M002", new Object[] {partIfc.getIdentity()});
            }
          //CCEnd SS4
            else if(CheckInOutTaskLogic.isCheckoutAllowed(partIfc))
            {
                CheckoutLinkInfo checkoutLinkInfo = CheckInOutTaskLogic
                        .checkOutObject(partIfc, CheckInOutTaskLogic
                                .getCheckoutFolder(), "");
                //工作副本BsoID。
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
                //"成功添加为 * 的描述文档。\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M002", new Object[] {workableIfc.getIdentity()});
            }
            //当前用户没有修改零部件的权限。
            else
            {
                //"您没有修改 * 的权限，不能为此零部件添加描述文档！\n"
                message = message + QMMessage.getLocalizedMessage(resource,
                        "M005", new Object[] {partIfc.getIdentity()});
            }
        }
        return message;
    }

    /**
     * 在更新文档时，删除文档和零部件的参考文档。
     * @param parts 零部件BsoID集合。
     * @param docMasterBsoID 文档主信息BsoID。
     * @return String 提示信息。
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
            //只含有一个对象。
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
     * 在更新文档时，删除文档和零部件的描述文档。
     * @param parts 零部件BsoID集合。
     * @param docBsoID 文档值对象BsoID。
     * @return String 提示信息。
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
     * 根据名称和编号，获取具体版本零部件的BsoID。
     * @param name 名称。
     * @param number 编号。
     * @return ArrayList 具体版本零部件的BsoID集合。
     * @throws QMException
     */
    public ArrayList getAllPartByMaster(String name, String number)
            throws QMException
    {
        ArrayList partList = new ArrayList();
        Collection masters = PartServiceRequest.getAllPartMasters(name, number);
        PartConfigSpecIfc config = PartServiceRequest.findPartConfigSpecIfc();
        //获得id
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
     * 查找所有生命周期状态。
     * @return Collection 所有生命周期状态（LifeCycleState）集合。
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
     * 查找所有零部件类型。
     * @return Collection 所有零部件(QMPartType)类型的集合。
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
     * 查找所有零部件来源。
     * @return Collection 所有零部件来源(ProducedBy)的集合。
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
     * 检查用户权限，获取所有可见的视图。
     * @return Collection 视图名称(String)集合。
     */
    public static Collection findAllView()
    {
        return ViewHelper.getAllPartViews();
    }
    
    /**
     * 获得所有的项目，只包括可用的。（原来为包括不可用的）
     * @return Collection 项目名称（String）集合
     */
    public static Collection findAllProject()
            throws QMException
    {
        
        return CommonSearchHelper.getAllProjects();
    }
    /**
     * 查找所有零部件。瘦客户端根据零部件的11个属性搜索零部件的功能，支持模糊查询和非查询。
     * 现用于文档反查零部件用例中，也适用于其它需要根据多属性查询零部件的用例。
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
     * @return  Collection 零部件值对象（QMPartInfo）集合。
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
   * 根据零部件的BsoID获取该零部件上的所有的有效性方案的名称，类型，有效性值范围，bsoid。
   * 
   * @param partBsoID String 零部件的BsoID。
   * @param isacl boolean
   * @throws QMException
   * @return Vector 返回的Vector中的每个元素都是String[3]：
   * <br>String[0]:有效性方案名称，如果默认日期有效性，可以不指定有效性方案名；
   * <br>String[1]:有效性方案类型，如果没有有效性方案的名称，则默认为"日期有效性"；
   * <br>String[2]:有效性值范围；
   * <br>String[3]:有效性bsoid。
   */
  public Vector getLookEffectivity(String partBsoID, boolean isacl)
      throws QMException
  {
    PartDebug.trace(this, "getLookEff() begin......");
    //先调用EnterprisePartService中的方法getEffVector(QMPartInfo):Vector
    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
    EffService effService = (EffService)EJBServiceHelper.getService("EffService");
    EffectivityManageableIfc partInfo = (EffectivityManageableIfc)pService.refreshInfo(partBsoID);
    Vector effGroups = PartServiceRequest.getEffVector((WorkableIfc)partInfo, isacl);
    Vector resultVector = new Vector();
    //对effGroups中的所有的EffGroup对象进行循环，组装结果集合：
    for(int i = 0; i < effGroups.size(); i++)
    {
      EffGroup effGroup = (EffGroup)effGroups.elementAt(i);
      EffContextIfc effContextIfc = effGroup.getEffContext();
      String[] tempString = new String[4];
      //如果effContextIfc为空的话，一定是日期的：
      if(effContextIfc == null)
      {
        tempString[0] = "";
        tempString[1] = EffectivityType.DATE.getDisplay(); //日期有效性方案
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
          //这里的type有可能为空：
          EffectivityType type = configItemIfc.getEffectivityType();
          if(type != null)
          {
            tempString[1] = type.getDisplay();
          }
          else
          {
            //需要根据EffGroup中保存的具体的有效性配置项的类型来对tempString[1]进行赋值：
            //需要按照effGroup对象中的type:String这个字符串来对
            //effType的具体的类型进行指定：
            //这段代码最好可以不写死：!!!!
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
   * 获取瘦客户标题标题。
   * @param s 操作对象id
   * @return 由唯一性工厂提供的唯一性表示(String)
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
      
      //CCBegin by leixiao 2010-1-7 打补丁　v4r3_p005_20100104 TD2741      
            /**
       * 获取瘦客户标题。
       * @param s 操作对象id
       * @return 只返回零部件的名称和编号（String）
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
                  s1 = "主信息 "+((QMPartMasterInfo)basevalueifc).getPartNumber()+" "+((QMPartMasterInfo)basevalueifc).getPartName();
              }
              catch(Exception exception)
              {
                  exception.printStackTrace();
              }
              return s1;
          }
          //CCEnd by leixiao 2010-1-7　v4r3_p005_20100104 
     
    //问题(2)20080808 zhangq begin 修改原因：在产看零部件的被用于产品时，显示不正确。（TD-1794）
	/**
	 * 根据指定零部件的BsoId获得零部件在当前用户的配置规范下被该产品所使用的结构。
	 * 使用结果保存在返回值vector中。
	 * @param partID :String 指定的零部件BsoId。
	 * @param productBsoId :String 使用该零部件的产品的BsoId。
	 * @return vector:Vector 被其他部件所使用的信息集合。
	 * vector返回值的数据结构为：vector中的每个元素都是Vector类型的，这里为了叙述方便，起名为subVector.
	 * 而subVector的每个元素都是String[5]类型的。
	 * 这个String[5]分别记录了:<br>
	 * String[0]:层次号；<br>
	 * String[1]:零部件编号(零部件名称)版本(视图);<br>
	 * String[2]:零部件在此结构中(顶级件中)使用的数量，所以在同一结构下的记录使用数量是相同的，零部件下同一子件的使用数量合并。<br>
	 * String[3]:零部件的BsoId。<br>
	 * String[4]:零部件值对象。<br>
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
	//问题(2)20080808 zhangq end
	
//	CCBegin by leix 2009-11-30
	  /**
	   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法。
	   * 获取零部件工程视图和制造视图的bom统计表比较yanqi-20060915
	   * @param partID String 零部件的id
	   * @param attrNames String 需要返回的属性
	   * @param source String 零部件来源
	   * @param type String 零部件类型
	   * @throws QMException
	   * @return Vector 返回集合
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
	    //将需要返回的字符串解析成字符串数组
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
	        getMaster(),getPartConfigSpecByViewName("工程视图"));
	    if (coll != null && coll.size() != 0) {
	      Iterator it = coll.iterator();
	      Object[] obj = (Object[]) it.next();
	      enPartIfc = (QMPartInfo) obj[0];

	    }
	    coll = PartServiceRequest.filteredIterationsOf( (MasteredIfc) (partIfc.getMaster()),
	                                         getPartConfigSpecByViewName("制造视图"));
	    if (coll != null && coll.size() != 0) {
	      Iterator it = coll.iterator();
	      Object[] obj = (Object[]) it.next();
	      manuPartIfc = (QMPartInfo) obj[0];
	    }
	    //获取工程视图下的bom清单
	    Vector en_vec = PartServiceRequest.setBOMList(enPartIfc, attrNames1,
	                                         affixAttrNames,
	                                         routeNames,
	                                         source, type,
	                                         getPartConfigSpecByViewName("工程视图"));
	    //获取制造视图下的bom清单
	    Vector manu_vec = PartServiceRequest.setBOMList(manuPartIfc, attrNames1,
	                                           affixAttrNames,
	                                           routeNames,
	                                           source, type,
	                                           getPartConfigSpecByViewName("制造视图"));
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
	   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法。
	   * @param en Vector
	   * @param manu Vector
	   * @return Vector
	   * @author liunan 2008-08-01
	   */
	  private Vector compareBom(Vector en, Vector manu) {
	    Vector result = new Vector(); //返回的结果集
	    try {
	    Vector onlyEn = new Vector(); //用来暂时存放只在工程视图下被使用的零部件，目的是使结果集的元素顺序为两种视图下都使用的部件－》只在工程视图下使用的部件－》只在制造视图下使用的部件
	    HashMap numArrayMap = new HashMap(manu.size()); //用来存放零部件号－数组array的映射。其中array[0]－零部件id,array[2]－零部件编号在这个数组中的序号,array[3]－该零部件是否可分,array[4]－零部件编号,array[5]－零部件名称,array[6]－零部件数量,array[7]－零部件版本,array[8]－零部件视图。
	    Iterator manuIter = manu.iterator();
	    //跳过第一个元素（表头）
	    if (manuIter.hasNext()) {
	      String[] manuEle = (String[]) manuIter.next();
	    }
	    //将制造视图的元素添加到numArrayMap，以供遍历工程视图时查询
	    while (manuIter.hasNext()) {
	      Object[] manuEle = (Object[]) manuIter.next();
	      numArrayMap.put(manuEle[4], manuEle);
	    }
	    //跳过工程视图集合的第一个元素
	    Iterator enIter = en.iterator();
	    if (enIter.hasNext()) {
	      enIter.next();
	    }
	    //遍历工程视图的集合，
	    while (enIter.hasNext()) {
	      //根据工程视图的零件号去numArrayMap查询，看是否存在同号的制造视图的零部件，如存在，将制造视图数组的后三个元素与工程视图数组进行合并，并将合并后的数组添加到结果集中，然后从numArrayMap中删除该制造视图的映射
	      Object[] compareEle = new Object[13]; //第一个元素是工程视图和制造视图下使用情况是否相同的标识，相同为“true”，不同为“false”，不同包括使用视图的不同和使用数量的不同。
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
	    //处理只在制造视图中存在的零部件
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
	   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法。
	   * 根据视图名称返回零部件配置规范
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
	    //若根据指定的视图名称没有获取到相应的值对象则返回当前配置规范
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
	   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法。
	   * yanqi-20061017
	   * 根据零部件id获取该零部件的一级子件信息列表
	   * @param partID String 零部件id
	   * @throws QMException
	   * @return Vector 对象（Object）数组集合，数组的每个元素都是字符串，这些字符串依次为零部件的编号,名称,版本,视图,数量,使用方式,类型,来源,生命周期状态
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
	   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法。
	   * yanqi-20061017
	   * 根据零部件值对象及配置规范获取该零部件的一级子件信息列表
	   * @param part QMPartIfc 零部件值对象
	   * @param config PartConfigSpecIfc 配置规范
	   * @throws QMException
	   * @return Vector 对象（Object）数组集合，数组的每个元素都是字符串，这些字符串依次为零部件的编号,名称,版本,视图,数量,使用方式,类型,来源,生命周期状态
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
	   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法。
	   * yanqi-20061017
	   * 返回用字符串表示零部件属性的数组集合
	   * @param partAndLink Collection 对象数组集合，数组有两个元素，第一个是零部件使用关联值对象，第二个是零部件值对象或零部件基本信息值对象
	   * @return Vector 对象（Object）数组集合，数组的每个元素都是字符串，这些字符串依次为零部件的编号,名称,版本,视图,数量,使用方式,类型,来源,生命周期状态
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
	   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法。
	   * yanqi-20061017
	   * 返回用字符串表示零部件属性的数组
	   * @param part QMPartIfc 零部件值对象
	   * @param link PartUsageLinkIfc 使用关联值对象
	   * @return Object[] 数组的每个元素都是字符串，这些字符串依次为零部件的编号,名称,版本,视图,数量,使用方式,类型,来源,生命周期状态
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
	   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法。
	   * yanqi-20061017
	   * 对对象数组根据由index指定的数组元素进行排序
	   * @param vec Vector 对象数组集合
	   * @param index int 根据数组的第index个元素进行排序
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
	   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法，
	   * 产品中存在的该方法依然保留，以备使用。
	   * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。
	   * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
	   * 1、如果不定制属性：
	   * BsoID，号码、名称、是（否）可分（"true", "false"）、数量（转化为字符型）、版本和视图；
	   * 2、如果定制属性：
	   * BsoID，号码、名称、是（否）可分("true", "false")、数量、其他定制属性。
	   * @param partID :String 指定的零部件的bsoID.
	   * @param attrNames :String 定制要输出的属性集合；如果为空，则按标准版输出。
	   * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理；
	   * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理；
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
	    //int tt = attrNames.indexOf("装配路线-2,");
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
	          //if (str.equals("装配路线-2")) {

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

		//CCBegin by leix	 2010-12-20  增加逻辑总成数量报表
	  public Vector getMaterialList(String partID, String attrNames) throws
      QMException {
		  return getMaterialList(partID,attrNames,false);
	  }
	  public Vector getMaterialLogicList(String partID, String attrNames) throws
      QMException {
		  return getMaterialList(partID,attrNames,true);
	  }
	//CCEnd by leix	 2010-12-20  增加逻辑总成数量报表
	 //CCBegin SS1
	  /**
	   * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的物料清单信息。
	   * 
	   * @param partID String 指定零部件的bsoID
	   * @param attrNames String 定制的属性，可以为空
	   * @throws QMException
	   * @return Vector 返回结果是vector,其中vector中的每个元素都是一个集合：
	   *<br> 0：当前part的BsoID；
	   *<br> 1：当前part所在的级别，转化为字符型；
	   *<br> 2：当前part的编号；
	   * <br>3：当前part的名称；
	   * <br>4：当前part被最顶层部件使用的数量，转化为字符型；
	   * <br>5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
	   * <br>             ：如果定制了属性：按照所有定制的属性加到结果集合中。
	   */
	  public Vector getMaterialListERP(String partID, String attrNames)
	      throws QMException
	  {
	    PartDebug.trace(this, "getMaterialList() begin ....");

	    //源代码
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
	   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法，
	   * 产品中存在的该方法依然保留，以备使用。
	   * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的物料清单信息。
	   * 返回结果是vector,其中vector中的每个元素都是一个集合：
	   * 0：当前part的BsoID；
	   * 1：当前part所在的级别，转化为字符型；
	   * 2：当前part的编号；
	   * 3：当前part的名称；
	   * 4：当前part被最顶层部件使用的数量，转化为字符型；
	   * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
	   *              ：如果定制了属性：按照所有定制的属性加到结果集合中。
	   * @param partID String 指定零部件的bsoID
	   * @param attrNames String 定制的属性，可以为空
	   * @throws QMException
	   * @return Vector
	   * @author liunan 2008-08-01
	   * leixiao 2010-12-21 增加参数islogic,用来处理 逻辑总成数量报表
	   */

	  public Vector getMaterialList(String partID, String attrNames,boolean islogic) throws
	      QMException {
	    attrNames = attrNames.trim();
	    if (attrNames == null || attrNames.length() == 0) {
	      Vector result = new Vector();
	      return result;
	    }

	    int tt = attrNames.indexOf("装配路线-2,");
	    int t = attrNames.indexOf("制造路线-2,");
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
	          /*if (str.equals("装配路线-2")) {
	            attrNames1[fff] = "装配路线-2";
	            fff++;
	            attrNames1[fff] = "装配路线合件号-2";
	            fff++;
	            attrNames1[fff] = "装入合件数量-2";
	            fff++;
	          }
	          else if (tt < 0 && str.equals("制造路线-2")) {
	            attrNames1[fff] = "制造路线-2";
	            fff++;
	            attrNames1[fff] = "装配路线合件号-2";
	            fff++;
	            attrNames1[fff] = "装入合件数量-2";
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
	  //CCBegin by leix	 2010-12-20  增加逻辑总成数量报表,增加参数islogic
	    Vector v =PartServiceRequest.setMaterialList(partIfc, attrNames1, affixAttrNames1,
	                                     routeNames, partConfigSpecIfc,islogic);
	  //CCEnd by leix	 2010-12-20  增加逻辑总成数量报表
	    return v;
	  }

	  /**
	   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法。
	   * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的物料清单信息。
	   * 返回结果是vector,其中vector中的每个元素都是一个集合：
	   * 0：当前part的BsoID；
	   * 1：当前part所在的级别，转化为字符型；
	   * 2：当前part的编号；
	   * 3：当前part的名称；
	   * 4：当前part被最顶层部件使用的数量，转化为字符型；
	   * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
	   *              ：如果定制了属性：按照所有定制的属性加到结果集合中。
	   * @param partID String 指定零部件的bsoID
	   * @param attrNames String 定制的属性，可以为空
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

	    int tt = attrNames.indexOf("装配路线-2,");
	    int t = attrNames.indexOf("制造路线-2,");
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
	          if (str.equals("装配路线-2")) {
	            attrNames1[fff] = "装配路线-2";
	            fff++;
	            attrNames1[fff] = "装配路线合件号-2";
	            fff++;
	            attrNames1[fff] = "装入合件数量-2";
	            fff++;
	          }
	          else if (tt < 0 && str.equals("制造路线-2")) {
	            attrNames1[fff] = "制造路线-2";
	            fff++;
	            attrNames1[fff] = "装配路线合件号-2";
	            fff++;
	            attrNames1[fff] = "装入合件数量-2";
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
     * 得到指定工艺内容容器中指定的数据项
     * @param priInfo QMEquipmentInfo 内容容器
     * @return Vector ApplicationDataInfo 内容项
     * @throws RationException 
     * @author liunan 2009-12-23 根据解放要求，为零部件添加附件的查看。
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
    
    //CCBegin by liunan 2011-08-08 新增零部件一级子件报表，新增生命周期和路线属性。
   /**
	   * 根据零部件id获取该零部件的一级子件信息列表
	   * @param partID String 零部件id
	   * @throws QMException
	   * @return Vector 对象（Object）数组集合，数组的每个元素都是字符串，这些字符串依次为零部件的编号,名称,版本,视图,数量,使用方式,生命周期状态,制造路线,装配路线
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
	   * 根据零部件值对象及配置规范获取该零部件的一级子件信息列表
	   * @param part QMPartIfc 零部件值对象
	   * @param config PartConfigSpecIfc 配置规范
	   * @throws QMException
	   * @return Vector 对象（Object）数组集合，数组的每个元素都是字符串，这些字符串依次为零部件的编号,名称,版本,视图,数量,使用方式,生命周期状态,制造路线,装配路线
	   */
	  private Vector getFirstLevelStruct(QMPartIfc part, PartConfigSpecIfc config) throws
	      QMException {
	    Collection coll = PartServiceRequest.getUsesPartIfcs(part, config);
	    Vector result = getPartsAttributeArrays1(part, coll);
	    return sortStringArrays(result, 0);
	  }

	  /**
	   * 返回用字符串表示零部件属性的数组集合
	   * @param partAndLink Collection 对象数组集合，数组有两个元素，第一个是零部件使用关联值对象，第二个是零部件值对象或零部件基本信息值对象
	   * @return Vector 对象（Object）数组集合，数组的每个元素都是字符串，这些字符串依次为零部件的编号,名称,版本,视图,数量,使用方式,类型,来源,生命周期状态
	   */
	  private Vector getPartsAttributeArrays1(QMPartIfc part, Collection partAndLink) {
	  	
	    String makeRoute = "";
	    try
	    {
	    	TechnicsRouteService trService = (TechnicsRouteService) EJBServiceHelper.getService("TechnicsRouteService");
	    	Vector routeVec = trService.getListAndBrances(null, part, new String[]{"制造路线",}, "");
	    	if (routeVec != null && routeVec.size() > 0)
	    	{
          HashMap map = (HashMap) routeVec.elementAt(0);
          makeRoute = (String) map.get("制造路线");
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
	   * 返回用字符串表示零部件属性的数组
	   * @param part QMPartIfc 零部件值对象
	   * @param link PartUsageLinkIfc 使用关联值对象
	   * @return Vector 对象（Object）数组集合，数组的每个元素都是字符串，这些字符串依次为零部件的编号,名称,版本,视图,数量,使用方式,生命周期状态,制造路线,装配路线
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
	    	Vector routeVec = trService.getListAndBrances(parentpart, part, new String[]{"制造路线","装配路线"}, "");
	    	if (routeVec != null && routeVec.size() > 0)
	    	{
          HashMap map = (HashMap) routeVec.elementAt(0);
          makeRoute = (String) map.get("制造路线");
          assRoute = (String) map.get("装配路线");
          //CCbegin SS6
          colorFlag=(String)map.get("颜色件标识");
          //CCend SS6
        }

        //制造路线有“用”，则不用取得父件 制造路线作为装配路线。
        if(assRoute.equals("")&&makeRoute.indexOf("用")<0)
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
   * 根据零部件值对象获得该零部件关联的所有参考文档(DocMasterIfc)的主信息值对象的集合。
   * @param partID :String指定的零部件的值对象bsoID。
   * @return Vector 零部件参考文档的主信息值对象(DocMasterIfc)的集合，
   * 先根据零部件获取所有的参考文档主信息(DocMasterIfc)的集合,再返回DocMasterIfc的一些
   * 基本属性的集合:返回值Vector的每个元素都是String[]:
   * <br>String[0] : 文档Master的bsoID;
   * <br>String[1] : 文档的编号；
   * <br>String[2] : 文档的名称；
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
		// 集合中对象以版本排序,所以找到第一个符合条件的即可
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
			  //只有制造路线单位
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
			  //只有装配路线单位
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
			  //既有制造路线单位又有装配路线单位
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
			  //不需要路线单位过滤
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
    QueryCondition qc= new QueryCondition("baselineName",  QueryCondition.LIKE, "发布基线");
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
    QueryCondition qc= new QueryCondition("baselineName",  QueryCondition.LIKE, "发布基线");
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
   * 得到零部件的pdf附件
   * @param id String 零部件bsoid
   * @return Vector ApplicationDataInfo 内容项
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
   * 得到零部件的edz附件
   * @param id String 零部件bsoid
   * @return Vector ApplicationDataInfo 内容项
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
    	//从零部件没有找到中性文件，那么要从epm上找。
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
    			//如果更新者是技术中心，创建者不是技术中心，也不是Administrator。
    			if(partIfc.getIterationModifier().equals("User_10634")&&!partIfc.getIterationCreator().equals("User_113")&&!partIfc.getIterationCreator().equals("User_10634"))
    			{
    				//如果有源版本
    				String sv = PublishHelper.getIBAValue(partIfc,"sourceVersion");
    				System.out.println("用户符合要求！ sourceVersion="+sv);
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
    		// 集合中对象以版本排序,所以找到第一个符合条件的即可
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
    				//有epm，取epm的中性文件
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
    							str[1] = "零部件"+partnum+"的epm文档没有中性文件！";
    							str[2] = ifc.getBsoID();
    							return str;
    						}
    						else
    						{
    							str[0] = "error";
    							str[1] = "零部件"+partnum+"的epm文档没有中性文件！";
    							str[2] = ifc.getBsoID();
    							return str;
    						}
    					}
    				}
    				//没有epm，取零部件的中性文件。
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
    						str[1] = "零部件"+partnum+"没有中性文件！";
    						str[2] = ifc.getBsoID();
    						return str;
    					}
    					else
    					{
    						str[0] = "error";
    						str[1] = "零部件"+partnum+"没有中性文件！";
    						str[2] = ifc.getBsoID();
    						return str;
    					}
    				}
    				break;
    			}
    		}
    		str[0] = "error";
    		str[1] = "系统有编号为"+partnum+"的零部件，但没有"+partversion+"版本！";
    	}
    	else
    	{
    		str[0] = "error";
    		str[1] = "系统没有编号为"+partnum+"的零部件！";
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
   * 判断当前用户是否针对零部件的域、生命周期状态，对epm有查看权限
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
   * 判断当前用户是否针对零部件的域、生命周期状态，对epm有下载权限
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
	 * 获得指定零部件的变型文档
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
   * 整车一级件清单，调用逻辑总成报表方法得到结果后处理返回。   * 零件提取规则
   * 1、层级由低向高逐级提取装配路线为“总”的零部件，不包含装置图、逻辑总成；
   * 2、装配图零件号：第5-7为001，2，3，5等；
   * 3、逻辑总成零件号：第5位为“G”，制造路线包含“总”；
   * 4、当零件号第5位为“G”，制造路线包含“总”时，取下一层级装配路线包含”总“的子件；
   * 5、制造路线不含“总”，装配路线含“总”时，不提取子件，提取同一层级装配路线含“总”的件；
   * 6、制造路线包含”总“，装配路线也包含“总”时，不提取子件，提取同一层级装配路线含“总”的件；
   * 7、子组号取逻辑总成前4位；
   * 第一次调试补充规则
   * 8、增加对“物料描述”包含1000410的零件提取，并直接将子组号赋值为“1000”；
   * 9、对1000410子件中父逻辑总成制造路线含“总”，子件装配路线含“总”的零部件进行提取；
   * 10、增加"物料描述"包含"车轮螺母",装配路线包含"总"的子件；
   * 11、增加导出表中表头参数输出顺序，即：物料号、物料描述、子组号、数量、制造路线、装配路线、生命周期状态、艺准编号、上一级父件编号；
   * 第二次调试补充规则
   * 12、父件逻辑总成制造路线含“总”，子件装配路线含“总”的件提取I（不考虑该零件号第5位是否为“G”）；
   * 13、父件逻辑总成中制造路线不含“总”时，不提取子件；
   * 14、增加对“物料描述”包含“弹簧制动缸逻辑总成”的零件提取，并直接将子组号赋值为“2500”；
   * 15、增加对“物料描述”包含“点烟器头总成、烟灰盒总成、随车工具灯、三角警告牌总成”等零件的提取，并直接将子组号赋值为“3900”；
   * 16、增加对“物料号”包含“5000010”的零件提取，并直接将子组号赋值为“5000”。
   * 第三次调试补充规则
   * 17、2400010、2500010、3722024、3902G00直接提取
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

	    int tt = attrNames.indexOf("装配路线-2,");
	    int t = attrNames.indexOf("制造路线-2,");
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
	    //调用逻辑总成物料清单的方法。然后对结果进行过滤
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
        
        //处理广义部件生成的 J001类零部件，没有编辑路线情况下，获取原广义部件的路线。
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
        			System.out.println("处理后，得到zzroute=="+zzroute+"  and  zproute=="+zproute);
        		}
        	}
        }
        
        if(checkSpecialPart(temp))
        {
        	vec.add(addZiZuElement(temp,false));
        	System.out.println("符合条件00");
        	//5000010特殊处理，只带本身，不带子件
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
        		//逻辑总成 制造路线 含有“总”，
        		if(isGpart(num)&&zzroute.indexOf("总")!=-1)
        		{
        			levesubl1000410 = curlevel;
        		}
        	}
        	else if(levesubl1000410==curlevel)
        	{
        		levesubl1000410 = -1;
        		//逻辑总成 制造路线 含有“总”，
        		if(isGpart(num)&&zzroute.indexOf("总")!=-1)
        		{
        			levesubl1000410 = curlevel;
        		}
        	}
        	else if(levesubl1000410==(curlevel-1)&&zproute.indexOf("总")!=-1)
        	{
        		vec.add(addZiZuElement(temp,false));
        		System.out.println("符合条件11");
        	}
        	else if(levesubl1000410==(curlevel-1)&&isGpart(num)&&zzroute.indexOf("总")!=-1)
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
        		System.out.println("符合条件 22");
        		is1000410 = true;
        		level1000410 = curlevel;
        		continue;
        	}
        			
        	if(templevel==-1)
        	{
        		if(checkIsAddPart(temp))
        		{
        			vec.add(addZiZuElement(temp,false));
        			System.out.println("符合条件 33");
        			templevel = curlevel;
        		}
        		
        		//Gpart，制造不包含“总”，则不取子件，
        		if(isGpart(num)&&zzroute.indexOf("总")==-1)
        		{
        			templevel = curlevel;
        			System.out.println("templevel 11=="+templevel);
        			continue;
        		}
        	}
        	else if(templevel>=curlevel)
        	{
        		templevel = -1;
        		
        		//Gpart，制造不包含“总”，则不取子件，
        		if(isGpart(num)&&zzroute.indexOf("总")==-1)
        		{
        			templevel = curlevel;
        			System.out.println("templevel 22=="+templevel);
        			if(checkIsAddPart(temp))
        			{
        				vec.add(addZiZuElement(temp,false));
        				System.out.println("符合条件 33");
        			}
        			continue;
        		}
        		
        		if(checkIsAddPart(temp))
        		{
        			vec.add(addZiZuElement(temp,false));
        			System.out.println("符合条件 44");
        			templevel = curlevel;
        		}
        	}
        	/*else if(templevel>curlevel)
        	{
        		templevel = -1;
        		
        		//Gpart，制造不包含“总”，则不取子件，
        		if(isGpart(num)&&zzroute.indexOf("总")==-1)
        		{
        			templevel = curlevel;
        			System.out.println("templevel 22=="+templevel);
        			if(checkIsAddPart(temp))
        			{
        				vec.add(addZiZuElement(temp,false));
        				System.out.println("符合条件 55");
        			}
        			continue;
        		}
        		
        		if(checkIsAddPart(temp))
        		{
        			vec.add(addZiZuElement(temp,false));
        			System.out.println("符合条件 66");
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
	 * 根据数组内容，判断该件是否符合条件，保留下来。
	 * 不是广义部件，不是装置图，装配路线含有“总”
	 * 第4位是层级，第5位是编号，第6位是名称，第10位是装配路线
	 */
	private boolean checkIsAddPart(Object temp[]) throws QMException
	{
		boolean flag = false;
		String zproute = temp[8]==null?"":temp[8].toString();
		//if(isGpart(temp[4].toString())&&temp[5].toString().indexOf("装置图")==-1&&zproute.indexOf("总")!=-1)
		if(zproute.indexOf("总")!=-1)
		{
			flag = true;
		}
		//没有父件，或父件是标准件的，不保留
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
		if(name.indexOf("车轮螺母")!=-1||name.indexOf("点烟器头总成")!=-1||name.indexOf("烟灰盒总成")!=-1||name.indexOf("随车工具灯")!=-1||name.indexOf("三角警告牌总成")!=-1||name.indexOf("弹簧制动缸逻辑总成")!=-1)
		{
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 处理逻辑总成数量报表的一行数组，在第7位插入子组。
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
					str[i] = "子组号";
				}
				else
				{
					if(obj[5].toString().indexOf("点烟器头总成")!=-1||obj[5].toString().indexOf("烟灰盒总成")!=-1||obj[5].toString().indexOf("随车工具灯")!=-1)
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
			str[4] = "物料号";
			str[5] = "物料描述";
		}
		return str;
	}
	//CCEnd SS14
	
	//CCBegin SS16
	/**
	 * 根据配置文档的bsoid获取《变型配置.xml》文件。
	 * 从文件中提取用户生成订单时选择的参数，只提取了手工选择的参数，引擎自动带出的参数不提取。
	 */
	public Collection getVariantSpecValue(String id)
	{
		Vector result = new Vector();
		System.out.println("getVariantSpecValue="+id);
		SAXReader sax=new SAXReader();//创建一个SAXReader对象  
		//File xmlFile=new File("E:\\test\\变型配置.xml");//根据指定的路径创建file对象  
		Document document=null;
		try
		{
			//document = sax.read(xmlFile);//获取document对象,如果文档无节点，则会抛出Exception提前结束
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
					//获取流ID
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

		Element root=document.getRootElement();//获取根节点  
		//System.out.println(root.elements().size());
		//循环一级节点
		for(Iterator i = root.elementIterator(); i.hasNext();)
		{
			Element employee = (Element) i.next();
			//循环二级节点，找到 parameter 的节点
			for(Iterator j = employee.elementIterator(); j.hasNext();)
			{
				Element node=(Element) j.next();
				//如果节点是手工输入，并且不是引擎驱动得到的参数，则是需要显示的。
				if(node.getQualifiedName().equals("parameter")&&node.attribute("isinput").getText().equals("yes")&&node.attribute("isdriven").getText().equals("no"))
				{
					//System.out.print(node.attribute("name").getText()+":");
					String s[] = new String[2];
					//参数名
					s[0] = node.attribute("name").getText();
					for(Iterator k = node.elementIterator(); k.hasNext();)
					{
						Element sub=(Element) k.next();
						if(sub.getQualifiedName().equals("value"))
						{
							//System.out.println(sub.attribute("value").getText());
							//参数值
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
   * 判断当前用户是否针对零部件的域、生命周期状态，对part有下载权限
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
   * 得到零部件的doc附件
   * @param id String 零部件bsoid
   * @return Vector ApplicationDataInfo 内容项
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
