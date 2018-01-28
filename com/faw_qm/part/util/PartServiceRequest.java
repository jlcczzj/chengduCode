/** 生成程序 PartServiceRequest.java    1.0    2004/11/02
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/04/27 谢斌 原因:逻辑混乱
 *                     方案:重写方法
 *                     备注:解放v3r11-分级物料清单性能优化-谢斌
 * CR2 20090619 张强 修改原因：进行结构比较后，产品信息管理中配置规范显示为结构比较目标件的配置规范。（TD-2190） 
 *                   解决方案：进行结构比较后，产品信息管理中正确显示的配置规范。
 * SS1 2013-1-21  产品信息管理器中输出物料清单功能中增加输出带有erp属性报表功能，导出的格式文件内容包括：零部件的零件号、零件名、父件数量、 
                  每车数量、计量单位、来源、制造路线、装配路线、（T单总、固定提前期合计、可变提前期），
                  其中后三项括弧中的内容需要从零部件关联的工艺卡中获取
 */
package com.faw_qm.part.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.auth.RequestHelper;
import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.baseline.model.BaselineableIfc;
import com.faw_qm.change.model.ChangeableIfc;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.eff.model.EffConfigurationItemIfc;
import com.faw_qm.eff.model.EffManagedVersionIfc;
import com.faw_qm.eff.util.EffGroup;
import com.faw_qm.effectivity.model.QMConfigurationItemIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartAlternateLinkIfc;
import com.faw_qm.part.model.PartAttrSetInfo;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartDescribeLinkIfc;
import com.faw_qm.part.model.PartDescribeLinkInfo;
import com.faw_qm.part.model.PartReferenceLinkIfc;
import com.faw_qm.part.model.PartReferenceLinkInfo;
import com.faw_qm.part.model.PartSubstituteLinkIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.visitor.Walker;
import com.faw_qm.wip.model.WorkableIfc;


//CCBegin by liunan 2008-08-01
import com.faw_qm.version.model.MasteredIfc;
//CCBegin by liunan 2008-08-01

/**
 *
 *  Title: 提供调用所有PartService的方法的接口，不必区分是客户端调用还是服务端调用。 
 *  Description: 它的方法的签名与三个PartService是一致的，只不过它不真正地实现这些方法的业务逻辑，而是把方法名和参数类型以及参数传递给helper的request方法，由该方法去真正的调用服务。 
 *  Copyright: Copyright (c) 2004 
 *  Company: 一汽启明 
 * @author 谢斌
 * @version 1.0
 */
//问题(1)20080808 张强修改 修改原因：在产看零部件的被用于产品时，显示不正确。（TD-1794）
public class PartServiceRequest implements Serializable
{
  private static RequestHelper helper = new RequestHelper();
  private static String STANDARD = "StandardPartService";
  private static String EXTENDED = "ExtendedPartService";
  private static String ENTERPRISE = "EnterprisePartService";
  static final long serialVersionUID = 1L;

  /**
   * 构造函数。
   */
  PartServiceRequest()
  {
  }

//以下是标准版功能。

  /**
   * 通过零部件的名字和号码查找零部件的主信息。返回的集合中只有一个QMPartMasterIfc对象。
   * @param partName :String 零部件的名称。
   * @param partNumber :String 零部件的号码。
   * @return collection :查找到的QMPartMasterIfc零部件主信息对象的集合，只有一个元素。
   * @throws QMException
   */
  public static Collection findPartMaster(String partName, String partNumber)
      throws QMException
  {
    Class[] paraClass = {String.class, String.class};
    Object[] para = {partName, partNumber};
    Collection collection = (Collection)helper.request(STANDARD, "findPartMaster", paraClass, para);
    return collection;
  }

  /**
   * 通过一个零部件主信息找到该零部件所有版本值对象的集合。
   * @param partMasterIfc :QMPartMasterIfc对象。
   * @return collection :Collection 所有对应于partMasterIfc的零部件对象(QMPartIfc)的集合。
   * @throws QMException
   */
  public static Collection findPart(QMPartMasterIfc partMasterIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class};
    Object[] para = {partMasterIfc};
    Collection collection = (Collection)helper.request(STANDARD,"findPart", paraClass, para);
    return collection;
  }

  /**
   * 删除参考文档与零部件的关联关系。
   * @param linkIfc PartReferenceLinkIfc
   * @throws PartException
   * @throws QMException
   * @see PartReferenceLinkInfo
   */
  public static void deletePartReferenceLink(PartReferenceLinkIfc linkIfc)
      throws QMException
  {
    Class[] paraClass = {PartReferenceLinkIfc.class};
    Object[] para = {linkIfc};
    helper.request(STANDARD,"deletePartReferenceLink", paraClass, para);
  }

  /**
   * 保存参考文档与零部件的关联关系。
   * @param linkIfc PartReferenceLinkIfc 
   * @throws PartException
   * @return PartReferenceLinkIfc 参考文档与零部件的关联关系的对象
   * @throws QMException
   * @see PartReferenceLinkInfo
   */
  public static PartReferenceLinkIfc savePartReferenceLink(PartReferenceLinkIfc linkIfc)
      throws QMException
  {
    Class[] paraClass = {PartReferenceLinkIfc.class};
    Object[] para = {linkIfc};
    PartReferenceLinkIfc partReferenceLinkIfc = (PartReferenceLinkIfc)helper.request(STANDARD,"savePartReferenceLink", paraClass, para);
    return partReferenceLinkIfc;
  }

  /**
   * 保存零部件与零部件主信息的关联关系。
   * @param linkIfc PartUsageLinkIfc
   * @return PartUsageLinkIfc 零部件与零部件主信息的关联关系的对象
   * @throws QMException
   * @see PartUsageLinkInfo
   */
  public static PartUsageLinkIfc savePartUsageLink(PartUsageLinkIfc linkIfc)
      throws QMException
  {
    Class[] paraClass = {PartUsageLinkIfc.class};
    Object[] para = {linkIfc};
    PartUsageLinkIfc partUsageLinkIfc = (PartUsageLinkIfc)helper.request(STANDARD,"savePartUsageLink", paraClass, para);
    return partUsageLinkIfc;
  }

  /**
   * 删除零部件与零部件主信息的关联关系。
   * @param linkIfc PartUsageLinkIfc
   * @throws QMException
   *  @see PartUsageLinkInfo
   */
  public static void deletePartUsageLink(PartUsageLinkIfc linkIfc)
      throws QMException
  {
    Class[] paraClass = {PartUsageLinkIfc.class};
    Object[] para = {linkIfc};
    helper.request(STANDARD,"deletePartUsageLink", paraClass, para);
  }

  /**
   * 保存描述文档与零部件的关联关系。
   * @param linkIfc PartDescribeLinkIfc
   * @throws PartException
   * @return PartDescribeLinkIfc 描述文档与零部件的关联关系的对象
   * @throws QMException
   *  @see PartDescribeLinkInfo
   */
  public static PartDescribeLinkIfc savePartDescribeLink(PartDescribeLinkIfc linkIfc)
      throws QMException
  {
    Class[] paraClass = {PartDescribeLinkIfc.class};
    Object[] para = {linkIfc};
    PartDescribeLinkIfc partDescribeLinkIfc = (PartDescribeLinkIfc)helper.request(STANDARD,"savePartDescribeLink", paraClass, para);
    return partDescribeLinkIfc;
  }

  /**
   * 删除描述文档与零部件的关联关系。
   * @param linkIfc PartDescribeLinkIfc
   * @throws QMException
   */
  public static void deletePartDescribeLink(PartDescribeLinkIfc linkIfc)
      throws QMException
  {
    Class[] paraClass = {PartDescribeLinkIfc.class};
    Object[] para = {linkIfc};
    helper.request(STANDARD,"deletePartDescribeLink", paraClass, para);
  }

  /**
   *  根据零部件值对象获得该零部件关联的所有参考文档(DocIfc)最新版本的值对象的集合。 
   *  先根据零部件获取所有的参考文档主信息(DocMasterIfc)的集合,再对DocMasterIfc集合
   * 进行过滤，找出每个DocMasterIfc所对应的最新版本DocIfc。最后返回DocIfc的集合。 
   * @param partIfc :QMPartIfc 指定的零部件的值对象。
   * @return Vector 零部件参考文档(DocIfc)值对象的集合。
   * @see QMPartInfo
   * @throws QMException
   * 
   */
  public static Vector getReferencesDocMasters(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Vector vector = (Vector)helper.request(STANDARD,"getReferencesDocMasters", paraClass, para);
    return vector;
  }

  /**
   *  根据指定的零部件的值对象返回该零部件描述文档的值对象集合。 
   *  如果标志flag为true,返回的结果Vector中是DocIfc的集合； 
   *  如果flag为false，返回的Vector是PartDescribeLinkIfc的集合。 
   * @param partIfc :QMPartIfc零部件的值对象。
   * @param flag : boolean
   * @return vector 如果标志flag为true,返回的结果中是DocIfc的集合； 
   *  如果flag为false，返回的是PartDescribeLinkIfc的集合
   * @throws QMException
   */
  public static Vector getDescribedByDocs(QMPartIfc partIfc, boolean flag)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, boolean.class};
    Object[] para = {partIfc, new Boolean(flag)};
    Vector Vector = (Vector)helper.request(STANDARD,"getDescribedByDocs", paraClass, para);
    return Vector;
  }

  /**
   * 根据主文档的值对象获得被参考的零部件的值对象的集合。
   * @param docMasterIfc :DocMasterIfc 参考的文档的值对象。
   * @return vector :Vector 被文档参考的零部件的值对象的集合。
   * @see DocMasterInfo
   * @throws QMException
   * 
   */
  public static Vector getReferencedByParts(DocMasterIfc docMasterIfc)
      throws QMException
  {
    Class[] paraClass = {DocMasterIfc.class};
    Object[] para = {docMasterIfc};
    Vector Vector = (Vector)helper.request(STANDARD,"getReferencedByParts", paraClass, para);
    return Vector;
  }

  /**
   * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
   * @param partIfc :QMPartIfc 零部件的值对象。
   * @return collection 与partIfc关联的PartUsageLinkIfc的对象集合。
   * @see QMPartInfo
   * @throws QMException
   * 
   */
  public static Collection getUsesPartMasters(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getUsesPartMasters", paraClass, para);
    return collection;
  }

  /**
   * 根据指定的零部件返回下级零部件的最新版本的值对象的集合。
   * @param partIfc :QMPartIfc 零部件的值对象。
   * @return collection  partIfc使用的下级子件的最新版本的值对象(	QMPartInfo)集合。
   * @see QMPartInfo
   * @throws QMException
   */
  public static Collection getSubParts(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getSubParts", paraClass, para);
    return collection;
  }

  /**
   * 根据指定零部件返回所有下级零部件的最新版本的值对象集合。
   * @param partIfc :QMPartIfc 零部件的值对象。
   * @return collection  partIfc使用的下级子件的最新版本的值对象(QMPartInfo)集合。
   *  @see QMPartInfo
   * @throws QMException
   */
  public static Collection getAllSubParts(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getAllSubParts", paraClass, para);
    return collection;
  }

  /**
   * 根据指定的QMPartMasterIfc对象，通过两个表联合查询获得在PartUsageLink表中与QMPartMasterIfc关联的最新版本
   * 的QMPartIfc对象的集合。
   * @param partMasterIfc :QMPartMasterIfc对象。
   * @return collection 和partMasterIfc在PartUsageLink表中关联的最新版本QMPartIfc对象的集合。
   * @see QMPartMasterInfo
   * @throws QMException
   */
  public static Collection getUsedByParts(QMPartMasterIfc partMasterIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class};
    Object[] para = {partMasterIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getUsedByParts", paraClass, para);
    return collection;
  }

  /**
   * 通过指定的筛选条件，将集合collection中的PartUsageLinkIfc对象对应的
   * 符合筛选条件的Iterated部件进行筛选，如果不符合筛选条件则返回对应的Mastered主零部件。
   * @param collection :Collection 是PartUsageLinkIfc对象的集合。
   * @param configSpecIfc :PartConfigSpecIfc 零部件的筛选条件。
   * @return collection 每个元素为一个数组:数组的第一个元素为PartUsageLinkIfc对象，第二个元素为QMPartIfc对象，
   * 如果没有QMPartIfc对象，为关联的QMPartMasterIfc对象。
   * @see PartConfigSpecInfo
   * @throws QMException
   */
  public static Collection getUsesPartsFromLinks(Collection collection, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {Collection.class, PartConfigSpecIfc.class};
    Object[] para = {collection, configSpecIfc};
    Collection collection2 = (Collection)helper.request(STANDARD,"getUsesPartsFromLinks", paraClass, para);
    return collection2;
  }

  /**
   * 保存零部件。
   * @param partIfc :QMPartIfc 要保存的零部件的值对象。
   * @return partIfc:QMPartIfc 保存后的零部件的值对象。
   * @see QMPartInfo
   * @throws QMException
   */
  public static QMPartIfc savePart(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    QMPartIfc qmPartIfc = (QMPartIfc)helper.request(STANDARD,"savePart", paraClass, para);
    return qmPartIfc;
  }

  /**
   * 删除指定的零部件，如果有其他部件使用该部件，则异常"该零部件已经被其他部件使用，不能删除！"。
   * @param partIfc :QMPartIfc 要删除的零部件的值对象。
   * @see QMPartInfo
   * @throws QMException
   */
  public static void deletePart(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    helper.request(STANDARD,"deletePart", paraClass, para);
  }

  /**
   * 判断零部件partIfc2是否是零部件partIfc1的祖先部件或是partIfc1本身。
   * @param partIfc1 :QMPartIfc 指定的零部件的值对象。
   * @param partIfc2 :QMPartIfc 被检验的零部件的值对象。
   * @return flag:boolean:
   * <br>flag==true:零部件part2是零部件part1的祖先部件或是part1本身。
   * <br>flag==false:零部件part2不是零部件part1的祖先部件或part1本身。
   * @see QMPartInfo
   * @throws QMException
   */
  public static boolean isParentPart(QMPartIfc partIfc1, QMPartIfc partIfc2)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, QMPartIfc.class};
    Object[] para = {partIfc1, partIfc2};
    boolean flag = ((Boolean)helper.request(STANDARD,"isParentPart", paraClass, para)).booleanValue();
    return flag;
  }

  /**
   * 获得指定零部件的所有父部件的主信息值对象集合。
   * @param partIfc :QMPartIfc 指定的零部件的值对象。
   * @return vector:Vector 指定零部件的所有父部件(直到根部件)的主信息值对象(QMPartMasterInfo)的集合。
   * @see QMPartInfo
   * @throws QMException
   */
  public static Vector getAllParentParts(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Vector vector = (Vector)helper.request(STANDARD,"getAllParentsByPart", paraClass, para);
    return vector;
  }

  /**
   *  根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。 
   *  本方法调用了bianli()方法实现递归。 
   * 
   * @param partIfc :QMPartIfc 指定的零部件的值对象。
   * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
   * @param affixAttrNames : String[] 定制的要输出的扩展属性集合；可以为空。
   * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理。
   * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理。
   * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的对当前零部件的筛选条件。
   * @throws QMException
   * @return Vector 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
   *<br> 1、如果不定制属性：BsoID，号码、名称、是（否）可分（"true","false"）、数量（转化为字符型）、版本和视图。
   *<br>2、如果定制属性：BsoID，号码、名称、是（否）可分、数量、其他定制属性。
   *@see QMPartInfo
   *@see PartConfigSpecInfo
   */
  public static Vector setBOMList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,
                           String source, String type, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, String[].class, String[].class, String.class, String.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, attrNames, affixAttrNames, source, type, configSpecIfc};
    Vector vector = (Vector)helper.request(STANDARD,"setBOMList", paraClass, para);
    return vector;
  }
  /**
   * 分级物料清单的显示。包括IBA和工艺路线。
   * 返回结果是vector,其中vector中的每个元素都是一个Object[]：
   * 0：当前part的BsoID显示位置，为页面超链接使用；
   * 1：当前part的BsoID；
   * 2：当前part所在的级别；
   * 3-...：排序的输出属性；
   * 最后元素：当前part的父件编号。
   * 本方法调用了递归方法：fenji(String parentPartNum, QMPartIfc partIfc, int level, PartUsageLinkIfc partUsageLinkIfc);
   * 为Part-Other-classifylisting-001.jsp使用。
   * @param partIfc :QMPartIfc 最顶级的部件值对象。
   * @param attrNameArray :String[] 定制的属性，可以为空。
   * @throws QMException
   * @return Vector
   */
  public static Vector setMaterialList(QMPartIfc partIfc, String[] attrNames)//Begin CR1
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, String[].class};
    Object[] para = {partIfc, attrNames};
    Vector vector = (Vector)helper.request(STANDARD,"setMaterialList", paraClass, para);
    return vector;
  }//End CR1
    //CCBegin SS1
  /**
   * <p>分级物料清单的显示。</p>
   * <p>返回结果是vector,其中vector中的每个元素都是一个集合：</p>
   * <p>0：当前part的BsoID。</p>
   * <p>1：当前part所在的级别，转化为字符型。</p>
   * <p>2：当前part的编号。</p>
   * <p>3：当前part的名称。</p>
   * <p>4：当前part被最顶层部件使用的数量，转化为字符型。</p>
   * <p>5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；</p>
   * <p>              如果定制了属性：按照所有定制的属性加到结果集合中。</p>
   * <p>本方法调用了递归方法：</p>
   * <p>fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc, PartUsageLinkIfc partLinkIfc, int parentQuantity)。</p>
   * @param partIfc :QMPartIfc 最顶级的部件值对象。
   * @param attrNames :String[] 定制的属性，可以为空。
   * @param affixAttrNames : String[] 定制的扩展属性名集合，可以为空。
   * @param configSpecIfc :PartConfigSpecIfc 结构筛选条件。
   * @throws QMException
   * @return Vector
   */
  public static Vector setMaterialListERP(QMPartIfc partIfc, String[] attrNames,
                                       String[] affixAttrNames,
                                       PartConfigSpecIfc configSpecIfc) throws
      QMException {
    Class[] paraClass = {
        QMPartIfc.class, String[].class, String[].class, PartConfigSpecIfc.class};
    Object[] para = {
        partIfc, attrNames, affixAttrNames, configSpecIfc};
    Vector vector = (Vector) helper.request(STANDARD, "setMaterialListERP",
                                            paraClass, para);
    return vector;
  }
  //CCEnd SS1
  /**
   *  根据指定零部件和指定的筛选条件获得零部件在该筛选条件下被哪些部件所使用。 
   *  使用结果保存在返回值vector中。
   *
   * @param partIfc :QMPartIfc 指定的零部件值对象。
   * @param configSpecIfc :PartConfigSpecIfc 指定的筛选条件。
   * @return vector:Vector 被其他部件所使用的信息集合。
   *  vector返回值的数据结构为：vector中的每个元素都是Vector类型的，这里为了叙述方便，起名为subVector。
   * 而subVector的每个元素都是String[]类型的:
   * <br> String[0]:层次号。 
   * <br> String[1]:零部件编号(零部件名称)版本(视图)。 
   * <br> String[2]零部件在此结构中(顶级件中)使用的数量，所以在同一结构下的记录使用数量是相同的，零部件下同一子件的使用数量合并。
   * @see QMPartInfo
   * @see PartConfigSpecInfo 
   * @throws QMException
   */
  public static Vector setUsageList(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, configSpecIfc};
    Vector vector = (Vector)helper.request(STANDARD,"setUsageList", paraClass, para);
    return vector;
  }

  /**
   * 通过指定的结构筛选条件，在数据库中寻找使用partIfc所对应的PartMasterIfc的所有部件(QMPartIfc)。
   * @param partIfc 零部件值对象。
   * @param configSpecIfc 零部件结构筛选条件。
   * @return Collection 返回值是以(PartUsageLinkIfc, QMPartIfc)为元素的集合
   * @see QMPartInfo
   * @see PartConfigSpecInfo
   * @throws QMException
   */
  public static Collection getParentPartsByConfigSpec(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, configSpecIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getParentPartsByConfigSpec", paraClass, para);
    return collection;
  }

  /**
   * 根据指定的结构筛选条件为查询空间添加查询条件。
   * @param partConfigSpecIfc 零部件结构筛选条件值对象。
   * @param query QMQuery
   * @throws QMException
   * @throws QueryException
   * @return QMQuery 返回查询表达式
   * @see PartConfigSpecInfo
   * @see QMQuery
   */
  public static QMQuery appendSearchCriteria(PartConfigSpecIfc partConfigSpecIfc, QMQuery query)
      throws QMException, QueryException
  {
    Class[] paraClass = {PartConfigSpecIfc.class, QMQuery.class};
    Object[] para = {partConfigSpecIfc, query};
    QMQuery qmQuery = (QMQuery)helper.request(STANDARD,"appendSearchCriteria", paraClass, para);
    return qmQuery;
  }

  /**
   * 根据结构筛选条件过滤出符合结构筛选条件的collection中所有QMPartMasterIfc的管理的QMPartIfc对象的小版本。
   * @param collection Collection
   * @param partConfigSpecIfc PartConfigSpecIfc
   * @throws QMException
   * @return Collection 所有QMPartMasterIfc的管理的QMPartIfc对象的小版本
   * @see PartConfigSpecInfo
   */
  public static Collection filteredIterationsOf(Collection collection, PartConfigSpecIfc partConfigSpecIfc)
      throws QMException
  {
    Class[] paraClass = {Collection.class, PartConfigSpecIfc.class};
    Object[] para = {collection, partConfigSpecIfc};
    Collection collection2 = (Collection)helper.request(STANDARD,"filteredIterationsOf", paraClass, para);
    return collection2;
  }

  /**
   * 查询当前用户的结构筛选条件。
   * @throws QMException
   * @return PartConfigSpecIfc 配置规范
   *  @see PartConfigSpecInfo
   */
  public static PartConfigSpecIfc findPartConfigSpecIfc()
      throws QMException
  {
    Class[] paraClass = {};
    Object[] para = {};
    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)helper.request(STANDARD,"findPartConfigSpecIfc", paraClass, para);
    return partConfigSpecIfc;
  }

  /**
   *  根据指定结构筛选条件，获得指定部件的使用结构。 
   *   
   * @param partIfc QMPartIfc
   * @param configSpecIfc PartConfigSpecIfc
   * @throws QMException
   * @return Collection 返回集合的每个元素是一个数组对象，第0个元素记录关联对象信息，
   * 第1个元素记录关联对象记录的use角色的mastered对象匹配结构筛选条件的iterated对象，
   * 如果没有匹配对象，记录mastered对象。
   *  @see PartConfigSpecInfo
   *  @see QMPartInfo
   */
  public static Collection getUsesPartIfcs(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, configSpecIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getUsesPartIfcs", paraClass, para);
    return collection;
  }

  /**
   * 根据结构筛选条件中包含的信息过滤结果集合。
   * 
   * @param configSpecIfc PartConfigSpecIfc
   * @param collection Collection 需要过滤的对象集合
   * @throws QMException
   * @throws QueryException
   * @return Collection 过滤后的结果集：
   *<br> 1，如果当前有效性配置有效，调用有效性过滤结果集和子方法；
   * <br>2，如果当前基线配置有效，调用基线过滤结果集和子方法；
   * <br>3，如果当前标准配置有效，调用标准过滤结果集和子方法；
   * @see PartConfigSpecInfo
   */
  public static Collection process(PartConfigSpecIfc configSpecIfc, Collection collection)
      throws QMException, QueryException
  {
    Class[] paraClass = {PartConfigSpecIfc.class, Collection.class};
    Object[] para = {configSpecIfc, collection};
    Collection collection2 = (Collection)helper.request(STANDARD,"process", paraClass, para);
    return collection2;
  }

  /**
   * 保存配置规范。
   *  <br>1，如果新的结构筛选条件没有持久化保存，为其指定所有者； 
   *   <br>2，查询数据库，获得当前用户的旧的结构筛选条件；
   *   <br>3，如果存在旧结构筛选条件，判断新的结构筛选条件是否为空，若是空， 
   *     从数据库中删除旧结构筛选条件，否则，将新结构筛选条件的数据赋值到旧结构筛选条件中，更新数据库； 
   *   <br>4，如果之前不存在旧的结构筛选条件，将新结构筛选条件持久化保存。 
   * @param configSpecIfc PartConfigSpecIfc
   * @throws QMException
   * @return PartConfigSpecIfc 保存后的配置规范
   * @see PartConfigSpecInfo
   */
  public static PartConfigSpecIfc savePartConfigSpecIfc(PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {PartConfigSpecIfc.class};
    Object[] para = {configSpecIfc};
    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)helper.request(STANDARD,"savePartConfigSpecIfc", paraClass, para);
    return partConfigSpecIfc;
  }

  /**
   *  通过名称和号码查找主零部件，允许模糊查询。
   *  如果name为null，按号码查询；如果number为null，按名称查询；
   *  如果name和numnber都为null，获得所有主零部件的值对象。 
   * @param name :String 模糊查询的零部件名称。
   * @param number :String 模糊查询的零部件的号码。
   * @return collection:Collection 符合查询条件的主零部件的值对象（QMPartMasterInfo）的集合。
   * @throws QMException
   */
  public static Collection getAllPartMasters(String name, String number)
      throws QMException
  {
    Class[] paraClass = {String.class, String.class};
    Object[] para = {name, number};
    Collection collection = (Collection)helper.request(STANDARD,"getAllPartMasters", paraClass, para);
    return collection;
  }

  /**
   *  通过名称和号码查找主零部件，允许模糊查询。
   *  如果name为null，按号码查询； 
   *  如果number为null，按名称查询； 
   *  如果name和numnber都为null，获得所有主零部件的值对象；
   *  如果参数nameFlag为true, 查找零部件名称和name不相同的那些零部件， 
   *  否则，查找零部件名称和name相同的零部件，对参数numFlag作同样的处理。 
   * @param name 待查询的零部件名称。
   * @param nameFlag 过滤条件。
   * @param number 待查询的零部件编号。
   * @param numFlag 过滤条件。
   * @return Collection 查询出来的主零部件(QMPartMasterIfc)的集合。
   * @throws QMException
   */
  public static Collection getAllPartMasters(String name, boolean nameFlag, String number, boolean numFlag)
      throws QMException
  {
    Class[] paraClass = {String.class, boolean.class, String.class, boolean.class};
    Object[] para = {name, new Boolean(nameFlag), number, new Boolean(numFlag)};
    Collection collection = (Collection)helper.request(STANDARD,"getAllPartMasters", paraClass, para);
    return collection;
  }

  /**
   * 根据partIfc寻找对应的partMasterIfc,再查找partUsageLink，获取使用了该partMasterIfc的所有零部件。
   * @param partIfc :QMPartIfc 零部件值对象。
   * @exception QMException 持久化服务的异常。
   * @return Collection QMPartIfc的集合。
   * @see QMPartInfo
   */
  public static Collection getPartsByUse(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Collection collection = (Collection)helper.request(STANDARD,"getPartsByUse", paraClass, para);
    return collection;
  }

  /**
   *  根据指定的零部件值对象和筛选条件获得零部件的产品结构。返回Vector。 
   *   
   * @param partIfc :当前零部件值对象。
   * @param configSpecIfc 当前零部件结构筛选条件值对象。
   * @return Vector Vector中存放NormalPart的对象的集合，每个NormalPart对象中的属性包括：
   * 号码+名称+版本+视图+数量+单位。注意，返回值Vector中不包含当前传入的参数partIfc。
   * @see QMPartInfo
   * @see PartConfigSpecInfo
   * @throws QMException
   * 
   */
  public static Vector getProductStructure(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, configSpecIfc};
    Vector vector = (Vector)helper.request(STANDARD,"getProductStructure", paraClass, para);
    return vector;
  }

  /**
   *  根据指定的零部件值对象和筛选条件获得零部件的子。返回Vector。 
   *  
   * @param partIfc :当前零部件值对象。
   * @param configSpecIfc 当前零部件结构筛选条件值对象。
   * @return Vector  Vector中存放NormalPart的对象的集合，每个NormalPart对象中的属性包括：
   * 号码+名称+版本+视图+数量+单位。注意，返回值Vector中不包含当前传入的参数partIfc。
   * @see QMPartInfo
   * @see PartConfigSpecInfo
   * @throws QMException
   */
  public static Vector getSubProductStructure(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, configSpecIfc};
    Vector vector = (Vector)helper.request(STANDARD,"getSubProductStructure", paraClass, para);
    return vector;
  }

  /**
   * 修改零部件（此方法供客户端直接调用）的名称，编号。
   * @param partMasterIfc QMPartMasterIfc值对象。
   * @param flag 是否修改零部件的编号,true为修改,false不修改。
   * @return partMasterIfc 修改后的零部件的值对象。
   * @see QMPartMasterInfo
   * @throws QMException
   */
  public static QMPartMasterIfc renamePartMaster(QMPartMasterIfc partMasterIfc, boolean flag)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class, boolean.class};
    Object[] para = {partMasterIfc, new Boolean(flag)};
    QMPartMasterIfc qmPartMasterIfc = (QMPartMasterIfc)helper.request(STANDARD,"renamePartMaster", paraClass, para);
    return qmPartMasterIfc;
  }

  //CR2 Begin 20090619 zhangq 修改原因：TD-2190。
  /**
   * 根据离散的数据信息构造PartConfigSpecIfc对象，一共十二个参数。
   * @param effectivityActive 是否是有效性结构筛选条件 是1,否0。
   * @param baselineActive 是否是基线结构筛选条件 是1,否0。
   * @param standardActive 是否是标准结构筛选条件 是1,否0。
   * @param baselineID 基线的BsoID。
   * @param configItemID 结构筛选条件的BsoID。
   * @param viewObjectID 视图对象的bsoID。
   * @param effectivityType 有效性类型。
   * @param effectivityUnit 有效性单位 需要持久化。
   * @param state 状态。
   * @param workingIncluded 是否在个人文件夹，默认为0:false,1:true。
   * @return PartConfigSpecIfc 封装好的零部件结构筛选条件值对象。
   * @throws QMException
   */
  public static PartConfigSpecIfc hashtableToPartConfigSpecIfc(
      String effectivityActive, String baselineActive, String standardActive, String baselineID,
      String configItemID, String viewObjectID, String effectivityType, String effectivityUnit,
      String state, String workingIncluded)
      throws QMException
  {
//    Class[] paraClass = {String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
//    Object[] para = {effectivityActive, baselineActive, standardActive, baselineID, configItemID, viewObjectID, effectivityType, effectivityUnit, state, workingIncluded};
//    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)helper.request(STANDARD,"hashtableToPartConfigSpecIfc", paraClass, para);
    return hashtableToPartConfigSpecIfc( "1",
    	       effectivityActive,  baselineActive,  standardActive,  baselineID,
    	       configItemID,  viewObjectID,  effectivityType,  effectivityUnit,
    	       state,  workingIncluded);
  }

  /**
   * 根据离散的数据信息构造PartConfigSpecIfc对象，一共十二个参数。
   * @param isUseCache 是否用缓存的配置规范的标志 是1,否0。
   * @param effectivityActive 是否是有效性结构筛选条件 是1,否0。
   * @param baselineActive 是否是基线结构筛选条件 是1,否0。
   * @param standardActive 是否是标准结构筛选条件 是1,否0。
   * @param baselineID 基线的BsoID。
   * @param configItemID 结构筛选条件的BsoID。
   * @param viewObjectID 视图对象的bsoID。
   * @param effectivityType 有效性类型。
   * @param effectivityUnit 有效性单位 需要持久化。
   * @param state 状态。
   * @param workingIncluded 是否在个人文件夹，默认为0:false,1:true。
   * @return PartConfigSpecIfc 封装好的零部件结构筛选条件值对象。
   * @throws QMException
   */
  public static PartConfigSpecIfc hashtableToPartConfigSpecIfc(String isUseCache,
      String effectivityActive, String baselineActive, String standardActive, String baselineID,
      String configItemID, String viewObjectID, String effectivityType, String effectivityUnit,
      String state, String workingIncluded)
      throws QMException
  {
    Class[] paraClass = {String.class,String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
    Object[] para = {isUseCache,effectivityActive, baselineActive, standardActive, baselineID, configItemID, viewObjectID, effectivityType, effectivityUnit, state, workingIncluded};
    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)helper.request(STANDARD,"hashtableToPartConfigSpecIfc", paraClass, para);
    return partConfigSpecIfc;
  }
  //CR2 End 20090619 zhangq

  /**
   *  根据文档值对象查询数据库，获取所有被该文档所描述的零部件的集合。
   *  
   * @param docIfc DocIfc 文档值对象。
   * @param flag boolean 决定返回值的结构。
   * @return Collection 如果flag = true，表示只返回关联的另一边的零部件(QMPartIfc)的集合， 
   *  如果flag = false，返回关联类(PartDescribeLinkIfc)的集合。 
   *  @see DocInfo
   * @throws QMException
   */
  public static Collection getDescribesQMParts(DocIfc docIfc, boolean flag)
      throws QMException
  {
    Class[] paraClass = {DocIfc.class, boolean.class};
    Object[] para = {docIfc, new Boolean(flag)};
    Collection collection = (Collection)helper.request(STANDARD,"getDescribesQMParts", paraClass, para);
    return collection;
  }

  /**
   *  获取所有使用当前零件的零部件， 
   *  即根据当前的传入的零件QMPartIfc找到对应的PartMaster，
   * 再调用getUsedByParts(QMPartMasterIfc partMasterIfc):Collection方法找到所有的使用该零件的集合。 
   * @param partIfc QMPartIfc
   * @return Vector 所有的使用该零件的(QMPartInfo)集合
   * @see QMPartInfo
   * @throws QMException
   */
  public static Vector getParentParts(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Vector vector = (Vector)helper.request(STANDARD,"getParentParts", paraClass, para);
    return vector;
  }

  /**
   *  用于瘦客户显示另存为历史。
   *  循环地得到指定零部件的另存为历史部件，例如：零部件a由零部件b另存为得来，而b由c另存为得来，
   * 那么给定a的bsoID则可以获得b和c的相应信息返回值是Vector，其中每个元素是String类型的数组，以存放历史部件的信息。 
   * @param partID String
   * @throws QMException
   * @return Collection 其中每个元素是String类型的数组，以存放历史部件的信息:
   *<br> 0.序号；
   *<br> 1.创建者用户名；
   *<br> 2.创建时间；
   *<br> 3.零部件id；
   *<br> 4.零部件版本；
   *<br> 5.零部件视图名称。
   */
  public static Collection findAllPreParts(String partID)
      throws QMException
  {
    Class[] paraClass = {String.class};
    Object[] para = {partID};
    Collection collection2 = (Collection)helper.request(STANDARD,"findAllPreParts", paraClass, para);
    return collection2;
  }

  /**
   *  获得由给定零部件衍生出的零部件。 
   * @param partID String
   * @throws QMException
   * @return Collection 由给定零部件另存为得到的零部件(QMPartInfo)。
   */
  public static Collection findAllSaveAsParts(String partID)
      throws QMException
  {
    Class[] paraClass = {String.class};
    Object[] para = {partID};
    Collection collection = (Collection)helper.request(STANDARD,"findAllSaveAsParts", paraClass, para);
    return collection;
  }

  /**
   * 根据指定的具体零部件获取其所有类型为产品的父级零部件的最新版本。
   * @param qmPartIfc QMPartIfc 具体零部件。
   * @throws QMRemoteException
   * @throws QMException
   * @return Collection 父级零部件的最新版本(QMPartInfo)。
   * @see QMPartInfo
   */
  public static Collection getParentProduct(QMPartIfc qmPartIfc)
          throws QMException
  {
      Class[] paraClass = {QMPartIfc.class};
      Object[] para = {qmPartIfc};
      Collection collection = (Collection)helper.request(STANDARD,"getParentProduct", paraClass, para);
      return collection;
  }


  /**
     * 获取子零部件在父零部件中的使用数量。
     * @param parentPartIfc QMPartIfc 父零部件。
     * @param childPartIfc QMPartIfc 子零部件。
     * @throws QMException
     * @return String 使用数量。
     * @see QMPartInfo
     */
    public static String getPartQuantity(QMPartIfc parentPartIfc,
                                  QMPartIfc childPartIfc)
            throws QMException
    {
        Class[] paraClass = {QMPartIfc.class, QMPartIfc.class};
        Object[] para = {parentPartIfc, childPartIfc};
        String quantity = (String)helper.request(STANDARD,
                "getPartQuantity", paraClass, para);
        return quantity;
    }


    /**
     * 获取子零部件在父零部件中的使用数量。
     * @param parentPartIfc QMPartIfc 父零部件。
     * @param middlePartMasterIfc QMPartMasterIfc 中间零部件主信息。
     * @param childPartIfc QMPartIfc 子零部件。
     * @throws QMException
     * @return String 使用数量。
     * @see QMPartInfo
     * @see QMPartMasterInfo
     */
    public static String getPartQuantity(QMPartIfc parentPartIfc, QMPartMasterIfc middlePartMasterIfc,
                                  QMPartIfc childPartIfc)
            throws QMException
    {
        Class[] paraClass = {QMPartIfc.class, QMPartMasterIfc.class, QMPartIfc.class};
        Object[] para = {parentPartIfc, middlePartMasterIfc, childPartIfc};
        String quantity = (String)helper.request(STANDARD,
                "getPartQuantity", paraClass, para);
        return quantity;
    }


    /**
     * 在指定产品中，获取指定子零部件的所有父零部件。
     * @param parentPartMasterIfc QMPartMasterIfc 产品的主信息。
     * @param childPartMasterIfc QMPartMasterIfc 该产品中的子零部件的主信息。
     * @throws QMException
     * @return HashMap 父零部件集合,键：PartNumber，值：值对象(QMPartInfo)。
     * @see QMPartMasterInfo
     */
    public static HashMap getParentPartsFromProduct(QMPartMasterIfc parentPartMasterIfc,
                        QMPartMasterIfc childPartMasterIfc)
            throws QMException
    {
        Class[] paraClass = {QMPartMasterIfc.class, QMPartMasterIfc.class};
        Object[] para = {parentPartMasterIfc, childPartMasterIfc};
        HashMap hashMap = (HashMap)helper.request(STANDARD, "getParentPartsFromProduct", paraClass, para);
        return hashMap;
    }

    /**
     * 设置生命周期。
     * @param basevalue 业务对象。
     * @return BaseValueIfc 业务对象。
     * @see BaseValueInfo
     * @throws QMException
     */
    public BaseValueIfc setLifeCycle(BaseValueIfc basevalue) throws QMException
    {
        Class[] paraClass = {BaseValueIfc.class};
        Object[] para = {basevalue};
        BaseValueIfc baseValueIfc = (BaseValueIfc)helper.request(STANDARD, "setLifeCycle", paraClass, para);
        return baseValueIfc;
    }

    /**
     * 获取符合配置规范的零部件。
     * @param partIfc 零部件。
     * @param configSpecIfc 配置规范。
     * @throws QMException
     * @return QMPartIfc 通过配置规范过滤的零部件对象
     * @see QMPartMasterInfo
     * @see PartConfigSpecInfo
     */
    public static QMPartIfc getPartByConfigSpec(QMPartMasterIfc partIfc,
            PartConfigSpecIfc configSpecIfc) throws QMException
    {
        Class[] paraClass = {QMPartMasterIfc.class, PartConfigSpecIfc.class};
        Object[] para = {partIfc, configSpecIfc};
        QMPartIfc QMPartIfc = (QMPartIfc) helper.request(STANDARD,
                "getPartByConfigSpec", paraClass, para);
        return QMPartIfc;
    }
    
    /**
     * 查找所有零部件。瘦客户端根据零部件的11个属性搜索零部件的功能，支持模糊查询和非查询。
     * 现用于文档反查零部件用例中，也适用于其它需要根据多属性查询零部件的用例。
     * @param partnumber 零部件编号；
     * @param checkboxNum 是否包含零部件编号“非”；
     * @param partname零部件名称；
     * @param checkboxName 是否包含零部件名称“非”；
     * @param partver 零部件版本；
     * @param checkboxVersion 是否包含零部件版本“非”；
     * @param partview 零部件视图；
     * @param checkboxView 是否包含零部件视图“非”；
     * @param partstate 零部件状态；
     * @param checkboxLifeCState 是否包含零部件状态“非”；
     * @param parttype 零部件类型；
     * @param checkboxPartType 是否包含零部件类型“非”；
     * @param partby 零部件来源；
     * @param checkboxProducedBy 是否包含零部件来源“非”；
     * @param partproject 零部件工作组；
     * @param checkboxProject 是否包含工作组“非”；
     * @param partfolder 零部件所在资料夹；
     * @param checkboxFolder 是否包含资料夹“非”；
     * @param partcreator 零部件创建者；
     * @param checkboxCreator 是否包含创建者“非”；
     * @param partupdatetime 更新时间；
     * @param checkboxModifyTime 是否包含更新时间“非”；
     * @return 零部件值对象(QMPartInfo)集合。
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
        Class[] paraClass = {String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class,
                String.class, String.class, String.class};
        Object[] para = {partnumber, checkboxNum, partname, checkboxName,
                partver, checkboxVersion, partview, checkboxView, partstate,
                checkboxLifeCState, parttype, checkboxPartType, partby,
                checkboxProducedBy, partproject, checkboxProject, partfolder,
                checkboxFolder, partcreator, checkboxCreator, partupdatetime,
                checkboxModifyTime};
        Collection collection = (Collection) helper.request(STANDARD,
                "findAllPartInfo", paraClass, para);
        return collection;
    }


//以下是扩展版功能。

  /**
   * 从数据库中删除一个基线。
   * @param baselineIfc :BaselineIfc 数据库中已经存在的基线。
   * @see BaselineIfc
   * @throws QMException
   */
  public static void deleteBaseline(BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {BaselineIfc.class};
    Object[] para = {baselineIfc};
    helper.request(EXTENDED,"deleteBaseline", paraClass, para);
  }

  /**
   * 获得数据库中的所有基线。
   * @return  collection  数据库中所有基线的值对象(ManagedBaselineInfo)的集合。
   * @throws QMException
   */
  public static Collection getAllBaselines()
      throws QMException
  {
    Class[] paraClass = {};
    Object[] para = {};
    Collection collection = (Collection)helper.request(EXTENDED,"getAllBaselines", paraClass, para);
    return collection;
  }

  /**
   *  将零部件添加到指定的基线中， 
   *  如果基线中不存在该零部件的任何版本，将其加入到基线中； 
   *  如果基线中已经存在该零部件，方法结束返回； 
   *  如果基线中已经存在该零部件的其它不同版本，用传入的参数对象替换原来基线中的对象。
   * @param partIfc :QMPartIfc 要添加到基线中的零部件的值对象。
   * @param baselineIfc :BaselineIfc 要添加part的基线。
   * @return baselineIfc :BaselineIfc 添加了零部件的基线的值对象。
   * @see QMPartInfo
   * @see BaselineInfo
   * @throws QMException
   */
  public static BaselineIfc addToBaseline(QMPartIfc partIfc, BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, BaselineIfc.class};
    Object[] para = {partIfc, baselineIfc};
    BaselineIfc baselineIfc2 = (BaselineIfc)helper.request(EXTENDED,"addToBaseline", paraClass, para);
    return baselineIfc2;
  }

  /**
   *  将多个受基线管理的零部件同时添加到指定的基线中。对集合中每个对象依次做下列操作： 
   *  如果基线中不存在该零部件的任何版本，将其加入到基线中； 
   *  如果基线中已经存在该零部件，方法结束返回； 
   * 如果基线中已经存在该零部件的其它不同版本，用传入的参数对象替换原来基线中的对象。 
   * @param collection :Collection 要添加到基线中的零部件的值对象的集合。
   * @param baselineIfc :BaselineIfc 要添加part的基线。
   * @return baselineIfc :BaselineIfc 添加了零部件的基线的值对象。
   * @see BaselineInfo
   * @throws QMException
   */
  public static BaselineIfc addToBaseline(Collection collection, BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {Collection.class, BaselineIfc.class};
    Object[] para = {collection, baselineIfc};
    BaselineIfc baselineIfc2 = (BaselineIfc)helper.request(EXTENDED,"addToBaseline", paraClass, para);
    return baselineIfc2;
  }

  /**
   * 从基线中移除零部件，返回基线。
   * @param partIfc :QMPartIfc 存在于基线中要被移除的零部件的值对象。
   * @param baselineIfc :BaselineIfc 包含part的基线的值对象。
   * @return BaselineIfc 基线值对象
   * @see BaselineInfo
   * @see QMPartInfo
   * @throws QMException
   */
  public static BaselineIfc removePartFromBaseline(QMPartIfc partIfc, BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, BaselineIfc.class};
    Object[] para = {partIfc, baselineIfc};
    BaselineIfc baselineIfc2 = (BaselineIfc)helper.request(EXTENDED,"removePartFromBaseline", paraClass, para);
    return baselineIfc2;
  }

  /**
   *  如果把removePartFromBaseline改为：removePartsFromBaseline，就更确切。 
   *  从基线中同时移除多个零部件。 
   * @param collection :Collection 存在于基线将被移出的零部件的值对象的集合。
   * @param baselineIfc :BaselineIfc 将要移出零部件的基线的值对象。
   * @return baselineIfc :BaselineIfc 被移除对象的基线的值对象。
   * @see BaselineInfo
   * @throws QMException
   */
  public static BaselineIfc removePartFromBaseline(Collection collection, BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {Collection.class, BaselineIfc.class};
    Object[] para = {collection, baselineIfc};
    BaselineIfc baselineIfc2 = (BaselineIfc)helper.request(EXTENDED,"removePartFromBaseline", paraClass, para);
    return baselineIfc2;
  }

  /**
   * 获得一个零部件被哪些基线管理的基线值对象的集合。
   * @param partIfc :QMPartIfc 受基线管理的零部件。
   * @return collection :Collection 管理partMasterIfc的所有基线的值对象(BaselineInfo)的集合。
   * @see QMPartInfo
   * @throws QMException
   */
  public static Collection getBaselines(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Collection collection = (Collection)helper.request(EXTENDED,"getBaselines", paraClass, para);
    return collection;
  }

  /**
   * 获得受该基线管理的零部件的值对象的集合。
   * @param baselineIfc :BaselineIfc 基线的值对象。
   * @return collection:Collection 受指定的基线管理的所有零部件的值对象(QMPartInfo)的集合。
   * @see BaselineIfc
   * @throws QMException
   */
  public static Collection getBaselineItems(BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {BaselineIfc.class};
    Object[] para = {baselineIfc};
    Collection collection = (Collection)helper.request(EXTENDED,"getBaselineItems", paraClass, para);
    return collection;
  }

  /**
   *  判断受基线管理的零部件的任何迭代版本是否为具体基线的一部分。
   *  若part对应Master的任何迭代(Iterated)是baseline一部分，返回true，否则返回false。 
   * @param partIfc :QMPartIfc 零部件的值对象。
   * @param baselineIfc :BaselineIfc 基线的值对象。
   * @return boolean 若baselineIfc包含partIfc的任何迭代版本，返回true，否则返回false。
   * @see QMPartInfo
   * @see BaselineIfc
   * @throws QMException
   */
  public static boolean isAnyIterationInBaseline(QMPartIfc partIfc, BaselineIfc baselineIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, BaselineIfc.class};
    Object[] para = {partIfc, baselineIfc};
    boolean flag = ((Boolean)helper.request(EXTENDED,"isAnyIterationInBaseline", paraClass, para)).booleanValue();
    return flag;
  }

  /**
   *  判断具体的受基线管理的对象是否为具体基线的一部分，
   *  若方法的a_BaselineableIfc对应的实体EJB是a_BaselineIfc对应的Entity EJB的一部分返回true，否则返回false。 
   * @param a_BaselineableIfc 任意的BaselineableIfc实例
   * @param a_BaselineIfc 任意的BaselineIfc实例
   * @return boolean 若a_BaselineIfc对应的实体EJB包含与a_BaselineableIfc对应的实体EJB，返回true，否则返回false。
   * @see BaselineIfc
   * @exception QMException
   */
  public static boolean isInBaseline(BaselineableIfc a_BaselineableIfc, BaselineIfc a_BaselineIfc)
      throws QMException
  {
    Class[] paraClass = {BaselineableIfc.class, BaselineIfc.class};
    Object[] para = {a_BaselineableIfc, a_BaselineIfc};
    boolean flag = ((Boolean)helper.request(EXTENDED,"isInBaseline", paraClass, para)).booleanValue();
    return flag;
  }

  /**
   *  在数据库中保存基线， 
   *  首先判断该基线是不是已经持久化的，如果不是，就保存；如果是，就更新。 
   * @param baselineIfc :BaselineIfc 未持久化的基线。
   * @return baselineIfc:BaselineIfc 持久化的基线。
   * @see BaselineIfc
   * @throws QMException
   */
  public static BaselineIfc saveBaseline(BaselineIfc baselineIfc)
      throws QMException
  {

    Class[] paraClass = {BaselineIfc.class};
    Object[] para = {baselineIfc};
    BaselineIfc baselineIfc2 = (BaselineIfc)helper.request(EXTENDED,"saveBaseline", paraClass, para);
    return baselineIfc2;
  }

  /**
   * 添加受基线管理的对象到基线，根据这个对象的结构筛选条件递归实现。
   * @param partIfc :QMPartIfc 待添加的受基线管理的对象。
   * @param baselineIfc :BaselineIfc 基线值对象。
   * @param configSpecIfc :ConfigSpec partMasterIfc 的结构筛选条件。
   * @return Vector QMPartIfc的:编号(名称)视图(版本版序号)。
   *  @see BaselineIfc
   *  @see QMPartInfo
   *  @see PartConfigSpecInfo
   * @throws QMException
   */
  public static Vector[] populateBaseline(QMPartIfc partIfc, BaselineIfc baselineIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, BaselineIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, baselineIfc, configSpecIfc};
    Vector[] vector = (Vector[])helper.request(EXTENDED,"populateBaseline", paraClass, para);
    return vector;
  }

  /**
   * 获得被指定主零部件的替换的主零部件的值对象的集合。
   * @param partMasterIfc :QMPartMasterIfc 主零部件的值对象。
   * @return Collection 被该主零部件替换的主零部件的值对象(QMPartMasterInfo)的集合。
   * @throws QMException
   * @see QMPartMasterInfo
   */
  public static Collection getAlternateForPartMasters(QMPartMasterIfc partMasterIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class};
    Object[] para = {partMasterIfc};
    Collection collection = (Collection)helper.request(EXTENDED,"getAlternateForPartMasters", paraClass, para);
    return collection;
  }

  /**
   * 获得指定主零部件的替换件的值对象的集合。
   * @param partMasterIfc :QMPartMasterIfc 主零部件的值对象。
   * @return collection:Collection 该主零部件替换件的值对象(QMPartMasterInfo)的集合。
   * @see QMPartMasterInfo
   * @throws QMException
   */
  public static Collection getAlternatesPartMasters(QMPartMasterIfc partMasterIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class};
    Object[] para = {partMasterIfc};
    Collection collection = (Collection)helper.request(EXTENDED,"getAlternatesPartMasters", paraClass, para);
    return collection;
  }

  /**
   * 根据指定的结构获得替换件的集合。
   * @param usageLinkIfc :PartUsageLinkIfc 指定的一个结构的值对象。
   * @return collection:Collection 可以替换的主零部件的值对象QMPartMasterIfc的集合。
   * @see PartUsageLinkInfo
   * @throws QMException
   */
  public static Collection getSubstitutesPartMasters(PartUsageLinkIfc usageLinkIfc)
      throws QMException
  {
    Class[] paraClass = {PartUsageLinkIfc.class};
    Object[] para = {usageLinkIfc};
    Collection collection = (Collection)helper.request(EXTENDED,"getSubstitutesPartMasters", paraClass, para);
    return collection;
  }

  /**
   * 根据指定的主零部件获得该零部件可以替换的结构的值对象的集合。
   * @param partMasterIfc : QMPartMasterIfc 指定的主零部件值对象。
   * @return collection:Collection 该零部件可以替换的结构值对象(PartSubstituteLinkInfo)的集合。
   * @see QMPartMasterInfo
   * @throws QMException
   */
  public static Collection getSubstituteForPartUsageLinks(QMPartMasterIfc partMasterIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class};
    Object[] para = {partMasterIfc};
    Collection collection = (Collection)helper.request(EXTENDED,"getSubstituteForPartUsageLinks", paraClass, para);
    return collection;
  }

  /**
   * 保存主零部件的替换关系。
   * @param link PartAlternateLinkIfc 保存前的替换关系
   * @return PartAlternamteLinkIfc  保存后的替换关系
   * @see PartAlternateLinkInfo
   * @throws QMException
   */
  public static PartAlternateLinkIfc savePartAlternateLink(PartAlternateLinkIfc link)
      throws QMException
  {
    Class[] paraClass = {PartAlternateLinkIfc.class};
    Object[] para = {link};
    PartAlternateLinkIfc partAlternateLinkIfc = (PartAlternateLinkIfc)helper.request(EXTENDED,"savePartAlternateLink", paraClass, para);
    return partAlternateLinkIfc;
  }

  /**
   * 保存结构替换关系。
   * @param link PartSubstituteLinkIfc 保存前的结构替换关系
   * @return PartSubstituteLinkIfc 保存后的结构替换关系
   * @see PartSubstituteLinkInfo
   * @throws QMException
   */
  public static PartSubstituteLinkIfc savePartSubstituteLink(PartSubstituteLinkIfc link)
      throws QMException
  {
    Class[] paraClass = {PartSubstituteLinkIfc.class};
    Object[] para = {link};
    PartSubstituteLinkIfc partSubstituteLinkIfc = (PartSubstituteLinkIfc)helper.request(EXTENDED,"savePartSubstituteLink", paraClass, para);
    return partSubstituteLinkIfc;
  }

  /**
   * 删除主零部件的替换关系。
   * @param link PartAlternateLinkIfc 替换关系的对象
   * @see PartAlternateLinkInfo
   * @throws QMException
   */
  public static void deletePartAlternateLink(PartAlternateLinkIfc link)
      throws QMException
  {
    Class[] paraClass = {PartAlternateLinkIfc.class};
    Object[] para = {link};
    helper.request(EXTENDED,"deletePartAlternateLink", paraClass, para);
  }

  /**
   * 删除结构替换关系。
   * @param link PartSubstituteLinkIfc 结构替换关系的对象
   *  @see PartSubstituteLinkInfo
   * @throws QMException
   */
  public static void deletePartSubstituteLink(PartSubstituteLinkIfc link)
      throws QMException
  {
    Class[] paraClass = {PartSubstituteLinkIfc.class};
    Object[] para = {link};
    helper.request(EXTENDED,"deletePartSubstituteLink", paraClass, para);
  }

  /**
   *  将指定的零部件发布到下一个视图中并返回新发布的零部件主信息的值对象。 
   *  返回主信息是为了加事物特性后有利于方法的扩展。 
   * @param partIfc :QMPartIfc 要发布的零部件的值对象。
   * @return masterIfc QMPartMasterIfc 发布后新零部件的主信息的值对象。
   * @see QMPartMasterInfo
   * @throws QMException
   */
  public static QMPartMasterIfc publish(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    QMPartMasterIfc qmPartMasterIfc = (QMPartMasterIfc)helper.request(EXTENDED,"publish", paraClass, para);
    return qmPartMasterIfc;
  }

  /**
   * 按结构添加基线。
   * @param a_BaseValueIfc 将要添加到基线中的任何Entity EJB对应的值对象。
   * @param a_BaselineIfc 接受a_BaseValueIfc的基线的值对象。
   * @param a_Walker Walker的一个实例。
   * @return Vector baselineableIfc的:编号(名称)视图(版本版序号)。
   * @see BaselineIfc
   * @see Walker
   * @exception QMException
   */
  public static Vector populateBaseline(BaseValueIfc a_BaseValueIfc, BaselineIfc a_BaselineIfc, Walker a_Walker)
      throws QMException
  {
    Class[] paraClass = {BaseValueIfc.class, BaselineIfc.class, Walker.class};
    Object[] para = {a_BaseValueIfc, a_BaselineIfc, a_Walker};
    Vector vector = (Vector)helper.request(EXTENDED,"populateBaseline", paraClass, para);
    return vector;
  }

  /**
   * 检验用户权限，获得所有可见的视图对象。
   * @return Vector 所有有权限的视图对象(ViewObjectInfo)的集合。
   * @throws QMException
   */
  public static Vector getAllViewInfos()
      throws QMException
  {
    Class[] paraClass = {};
    Object[] para = {};
    Vector vector = (Vector)helper.request(EXTENDED,"getAllViewInfos", paraClass, para);
    return vector;
  }

  /**
   *  通过扩展属性的查询条件获得符合条件的Affixed对象集合。 
   *  
   * @param vector Vector 输入的参数vector:客户端的扩展属性的查询条件，每个元素为字符串的数组String[]:
   * <br> 1、属性定义的bsoID；
   *  <br>2、查询操作符（只有“=”或“!=”两种情况）；
   * <br> 3、客户端输入的要查询的属性值。 
   * @return Collection 符合条件的"文档或者零部件"(统一表示为AffixIfc值对象)的集合。
   * @throws QMException
   */
  public static Collection getValueInfoForExtendAttr(Vector vector)
      throws QMException
  {
    Class[] paraClass = {Vector.class};
    Object[] para = {vector};
    Collection collection = (Collection)helper.request(EXTENDED,"getValueInfoForExtendAttr", paraClass, para);
    return collection;
  }

  /**
   * 获取当前的结构筛选条件，如果用户是首次登陆系统，则构造默认的“标准”结构筛选条件。
   * @throws QMException
   * @return PartConfigSpecIfc 标准结构筛选条件。
   * @see PartConfigSpecInfo
   */
  public static PartConfigSpecIfc getCurrentConfigSpec()
      throws QMException
  {
    Class[] paraClass = {};
    Object[] para = {};
    PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)helper.request(EXTENDED,"getCurrentConfigSpec", paraClass, para);
    return partConfigSpecIfc;
  }

  /**
   * 根据给定的零部件判断其是否是其他零部件的替换件或结构替换件。
   * @param part QMPartIfc 给定的零部件
   * @throws QMException
   * @return boolean true表示该零部件是一个替换件或结构替换件，否则都不是。
   * @see QMPartInfo
   */
  public static boolean isAlterOrSub(QMPartIfc part)
          throws QMException
  {
      Class[] paraClass = {QMPartIfc.class};
      Object[] para = {part};
      Boolean flag  =  (Boolean)helper.request(EXTENDED, "isAlterOrSub", paraClass, para);
      return flag.booleanValue();
  }

    /**
     * 获取在同一基线中的零部件的结构中的所有子件。
     * @param partIfc 零部件。
     * @param baselineIfc 基线。
     * @param configSpec 配置规范。
     * @return Vector 子件(QMPartInfo)集合。
     * @see QMPartInfo
     * @see BaselineIfc
     * @see PartConfigSpecInfo
     * @throws QMException
     */
    public static Vector getPopulateBaselineResult(QMPartIfc partIfc,
            BaselineIfc baselineIfc, PartConfigSpecIfc configSpec) throws QMException
    {
        Class[] paraClass = {QMPartIfc.class, BaselineIfc.class, PartConfigSpecIfc.class};
        Object[] para = {partIfc, baselineIfc, configSpec};
        Vector result = (Vector) helper.request(EXTENDED,
                "getPopulateBaselineResult", paraClass, para);
        return result;
    }

//以下是企业版功能。

  /**
   *  根据指定的源部件和目标部件及各自的筛选条件，返回两个部件使用关系的不同。 
   *  本方法调用了递归方法：difference(QMPartIfc sourcePartIfc, PartConfigSpecIfc sourceConfigSpecIfc,
   * QMPartIfc objectPartIfc, PartConfigSpecIfc objectConfigSpecIfc)。 
   *  
   * @param sourcePartIfc :QMPartIfc源部件的值对象。
   * @param objectPartIfc :QMPartIfc目标部件的值对象。
   * @param sourceConfigSpecIfc :PartConfigSpecIfc源部件的筛选条件。
   * @param objectConfigSpecIfc :PartConfigSpecIfc目标部件的筛选条件。
   * @return vector  返回值Vector中元素的数据结构String[]： 
   *  <br>0：当前差异部件相对于根部件的层次，这里，根部件为0层，根部件的子部件为1层，以此类推；
   *   <br>1：当前差异部件的名称+"("+编号+")" ； 
   *   <br>2：当前差异部件在源部件中的版本+（视图），如果源部件中没有该部件的话，显示""；
   *     如果源部件中有该差异部件但是没有符合结构筛选条件的版本的话，显示"没有符合结构筛选条件的版本"；
   *   <br>3：当前差异部件被其上一级零部件所使用的数量+(单位)，如果源部件中没有该部件的话，显示""； 
   *     如果源部件中有该差异部件但是没有符合结构筛选条件的版本的话，照常显示数量+（单位）； 
   *   <br>4：当前差异部件在目标部件中的版本+（视图），如果目标部件中没有该部件的话，显示""； 
   *     如果目标部件中有该差异部件但是没有符合结构筛选条件的版本的话，显示"没有符合结构筛选条件的版本"；
   *   <br>5：当前差异部件被其上一级零部件所使用的数量+(单位)，如果目标部件中没有该部件的话，显示""；
   *     如果目标部件中有该差异部件但是没有符合结构筛选条件的版本的话，照常显示数量+（单位）；
   *   <br>6：显示的颜色的标识::如果差异部件在源部件和目标部件中都存在，但数量或者版本不同的话，显示为黑色(black)；
   *     如果差异部件在源部件中存在，但是在目标部件中不存在的话，显示为绿色(green)；
   *     如果差异部件在目标部件中存在，但是在源部件中不存在的话，显示为紫色(purple)； 
   *     如果差异部件在源部件和在目标部件中的版本和数量都相同的话，显示为灰色(gray)；
   *     信息"没有符合结构筛选条件的版本"的颜色为红色(red),含有该信息的行的颜色为黑色(black)；
   *   <br>7:差异零部件的BsoID，是针对源零部件而言的，即是源零部件使用的该差异零部件的BsoID；
   *   <br>8:差异零部件的BsoID, 是针对目标零部件而言的，即是目标零部件使用的的差异零部件的BsoID。 
   * @throws QMException
   * @see QMPartInfo
   * @see PartConfigSpecInfo
   */
  public static Vector getBOMDifferences(QMPartIfc sourcePartIfc, PartConfigSpecIfc sourceConfigSpecIfc,
                                  QMPartIfc objectPartIfc, PartConfigSpecIfc objectConfigSpecIfc,HashMap map)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, PartConfigSpecIfc.class, QMPartIfc.class, PartConfigSpecIfc.class,HashMap.class};
    Object[] para = {sourcePartIfc, sourceConfigSpecIfc, objectPartIfc, objectConfigSpecIfc,map};
    Vector vector = (Vector)helper.request(ENTERPRISE,"getBOMDifferences", paraClass, para);
    return vector;
  }

  /**
   * 将参数collection中的元素上溯造型为PartUsageLinkIfc对象以rightBsoID(零部件的主信息的bsoID)为键字保存在哈西表中并返回哈西表。
   * @param collection :CollectionPartUsageLinkIfc对象集合。
   * @throws QMException
   * @return HashMap 键：零部件的主信息的bsoID；值：PartUsageLinkIfc对象。
   */
  public static HashMap consolidateUsageLink(Collection collection)
      throws QMException
  {
    Class[] paraClass = {Collection.class};
    Object[] para = {collection};
    HashMap hashMap = (HashMap)helper.request(ENTERPRISE,"consolidateUsageLink", paraClass, para);
    return hashMap;
  }

  /**
   * 获得part关联的所有PartUsageLinkIfc对象的集合。
   * @param partIfc :QMPartIfc对象。
   * @return collection:Colllection与part关联的所有PartUsageLinkifc对象集合。
   * @throws QMException
   * @see QMPartInfo
   */
  public static Collection getConsolidatedBOM(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Collection collection = (Collection)helper.request(ENTERPRISE,"getConsolidatedBOM", paraClass, para);
    return collection;
  }

  /**
   * 保存新建和更新的有效性方案.
   * @param effConfigItemIfc :EffConfigurationItemIfc 有效性配置项值对象。
   * @throws QMException
   * @return EffConfigurationItemIfc 保存后的有效性配置项值对象
   * @see EffConfigurationItemInfo
   */
  public static EffConfigurationItemIfc saveConfigItemIfc(EffConfigurationItemIfc effConfigItemIfc)
      throws QMException
  {
    Class[] paraClass = {EffConfigurationItemIfc.class};
    Object[] para = {effConfigItemIfc};
    EffConfigurationItemIfc effConfigurationItemIfc = (EffConfigurationItemIfc)helper.request(ENTERPRISE,"saveConfigItemIfc", paraClass, para);
    return effConfigurationItemIfc;
  }

  /**
   * 删除有效性方案。
   * @param configItemIfc EffConfigurationItemIfc
   * @throws QMException
   * @see EffConfigurationItemInfo
   */
  public static void deleteConfigItemIfc(EffConfigurationItemIfc configItemIfc)
      throws QMException
  {
    Class[] paraClass = {EffConfigurationItemIfc.class};
    Object[] para = {configItemIfc};
    helper.request(ENTERPRISE,"deleteConfigItemIfc", paraClass, para);
  }

  /**
   * 创建一个EffGroup对象。
   * @param effectivityType 有效性类型。
   * @param value_range 值范围。
   * @param configItemIfc QMConfigurationItemIfc
   * @param partIfc EffManagedVersionIfc
   * @throws QMException
   * @return EffGroup 创建后的EffGroup对象
   * @see QMConfigurationItemInfo
   * @see EffGroup
   */
  public static EffGroup createEffGroup(String effectivityType, String value_range, QMConfigurationItemIfc configItemIfc, EffManagedVersionIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {String.class, String.class, QMConfigurationItemIfc.class, EffManagedVersionIfc.class};
    Object[] para = {effectivityType, value_range, configItemIfc};
    EffGroup effGroup = (EffGroup)helper.request(ENTERPRISE,"createEffGroup", paraClass, para);
    return effGroup;
  }

  /**
   * 获得在当前筛选条件下partMasterIfc的结构下所有子件。
   * @param partMasterIfc 零部件主信息值对象。
   * @param configSpecIfc 结构筛选条件。
   * @return QMPartIfc[] 所有子部件值对象(QMPartInfo)的集合。
   * @throws QMException
   * @see QMPartMasterInfo
   * @see QMPartInfo
   * @see PartConfigSpecInfo
   */
  public static QMPartIfc[] getAllSubPartsByConfigSpec(QMPartMasterIfc partMasterIfc, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class, PartConfigSpecIfc.class};
    Object[] para = {partMasterIfc, configSpecIfc};
    QMPartIfc[] qmPartIfc = (QMPartIfc[])helper.request(ENTERPRISE,"getAllSubPartsByConfigSpec", paraClass, para);
    return qmPartIfc;
  }

  /**
   * 获得partIfc已经存在的EffGroup对象的集合。
   * @param partIfc QMPartIfc
   * @throws QMException
   * @return Vector EffGroup对象的集合
   * @see QMPartInfo
   */
  public static Vector outputExistingEffGroups(QMPartIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class};
    Object[] para = {partIfc};
    Vector vector = (Vector)helper.request(ENTERPRISE,"outputExistingEffGroups", paraClass, para);
    return vector;
  }

  /**
   * 获得在当前筛选条件下partMasterIfc的结构下所有子件的有效性范围数据类对象EffGroup的集合。该方法用于显示按结构添加有效性结果。
   * @param partMasterIfc :零部件主信息值对象。
   * @param configSpecIfc :PartConfigSpecIfc 结构筛选条件值对象。
   * @param configItemID 有效性配置项bsoID。
   * @param value_range :String 有效性值范围。
   * @return Vector EffGroup的集合
   * @throws QMException
   *  @see QMPartMasterInfo
   * @see PartConfigSpecInfo
   */
  public static Vector getEffGroups(QMPartMasterIfc partMasterIfc, PartConfigSpecIfc configSpecIfc, String configItemID, String value_range)
      throws QMException
  {
    Class[] paraClass = {QMPartMasterIfc.class, PartConfigSpecIfc.class, String.class, String.class};
    Object[] para = {partMasterIfc, configSpecIfc, configItemID, value_range};
    Vector vector = (Vector)helper.request(ENTERPRISE,"getEffGroups", paraClass, para);
    return vector;
  }

  /**
   * 根据零部件值对象获取该零部件的所有有效性值。
   * @param partIfc : QMPartIfc 零部件值对象。
   * @return Vector EffGroup的集合。
   * @throws QMException
   * @see WorkableIfc
   */
  public static Vector getEffVector(WorkableIfc partIfc)
      throws QMException
  {
    Class[] paraClass = {WorkableIfc.class};
    Object[] para = {partIfc};
    Vector vector = (Vector)helper.request(ENTERPRISE,"getEffVector", paraClass, para);
    return vector;
  }
  /**
   * 根据零部件值对象和有效性上下文id获取该零部件的所有有效性值。
   * @param partIfc : WorkableIfc 零部件值对象。
   * @param itemID  有效性上下文id
   * @return Vector EffGroup的集合。
   * @throws QMException
   * @see WorkableIfc
   */
  public static Vector getEffVector(WorkableIfc partIfc,String itemName)
      throws QMException
  {
    Class[] paraClass = {WorkableIfc.class,String.class};
    Object[] para = {partIfc,itemName};
    Vector vector = (Vector)helper.request(ENTERPRISE,"getEffVector", paraClass, para);
    return vector;
  }

  /**
   * 根据零部件值对象获取该零部件的所有有效性值。
   * @param partIfc : QMPartIfc 零部件值对象。
   * @param isacl 是否访问控制。
   * @return Vector EffGroup的集合。
   * @throws QMException
   * @see WorkableIfc
   */
  public static Vector getEffVector(WorkableIfc partIfc, boolean isacl)
      throws QMException
  {
    Class[] paraClass = {WorkableIfc.class, boolean.class};
    Object[] para = {partIfc, new Boolean(isacl)};
    Vector vector = (Vector)helper.request(ENTERPRISE,"getEffVector", paraClass, para);
    return vector;
  }

  /**
   * 删除part对应的有效性值。
   * @param partBsoID 零件的bsoID。
   * @param configItemName 有效性方案名称。
   * @param effectivityType 有效性类型:"DATE","LOT_NUM","SERIAL_NUM"。
   * @throws QMException
   */
  public static void deleteAllEffs(String partBsoID, String configItemName, String effectivityType)
      throws QMException
  {
    Class[] paraClass = {String.class, String.class, String.class};
    Object[] para = {partBsoID, configItemName, effectivityType};
    helper.request(ENTERPRISE,"deleteAllEffs", paraClass, para);
  }

  /**
   * 创建part对应的有效性值。
   * @param partIfc 零件。
   * @param configItemName 有效性方案名称。
   * @param effectivityType 有效性类型不能为空必须是:"DATE","LOT_NUM","SERIAL_NUM"之一。
   * @param effValue 有效性值。
   * @throws QMException
   * @see QMPartInfo
   */
  public static void createEff(QMPartIfc partIfc, String configItemName, String effectivityType, String effValue)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, String.class, String.class, String.class};
    Object[] para = {partIfc, configItemName, effectivityType, effValue};
    helper.request(ENTERPRISE,"createEff", paraClass, para);
  }

  /**
   * 更新part对应的有效性值。
   * @param partIfc QMPartIfc 零件。
   * @param configItemName String 有效性方案名称。
   * @param oldEffectivityType String 有效性类型不能为空必须是:"DATE","LOT_NUM","SERIAL_NUM"之一。
   * @param newEffValue String 新的有效性值范围。
   * @throws QMException
   * @see QMPartInfo
   */
  public static void updateEff(QMPartIfc partIfc, String configItemName,
            String oldEffectivityType, String newEffValue) throws QMException
    {
        Class[] paraClass = {QMPartIfc.class, String.class, String.class,
                String.class};
        Object[] para = {partIfc, configItemName, oldEffectivityType,
                newEffValue};
        helper.request(ENTERPRISE, "updateEff", paraClass, para);
    }
  
  /**
   * 给定事物特性持有者的对象,获得其iba属性值的个数。
   * @param ibaHolderIfc 事物特性持有者值对象
   * @return int 零部件值对象包含有iba属性值的个数
   * @see QMPartInfo
   */
  public static int getIBACount(QMPartIfc ibaHolderIfc)
  throws QMException
  {
	 
	  Class[] paraClass = {IBAHolderIfc.class};
      Object[] para = {ibaHolderIfc};
      ibaHolderIfc=(QMPartIfc) helper.request(EXTENDED, "getIBAContainer", paraClass, para);
    
    DefaultAttributeContainer container = (DefaultAttributeContainer)ibaHolderIfc.getAttributeContainer();
   
    if(container == null)
      container = new DefaultAttributeContainer();
    
    AbstractValueView[] defs=container.getAttributeValues();
 
    return defs.length;
  }
  
  /**
   * 零部件是否可以发布事物特性。
   * @param partIfc QMPartIfc 操作零部件。
   * @throws QMException
   * @return boolean 返回判断结果。
   * @see QMPartInfo
   */
  public static boolean  publishIBA(QMPartIfc partIfc)
      throws QMException
  {
	  try
	  {
	    Class[] paraClass = {QMPartIfc.class};
        Object[] para = {partIfc};
       
       boolean flage=((Boolean)helper.request(EXTENDED, "publishIBA", paraClass, para)).booleanValue();
       return flage;
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
		  throw new QMException(e);
	  }
	  
  }
  
    //问题(1)20080808 zhangq begin 修改原因：在产看零部件的被用于产品时，显示不正确。（TD-1794）
	/**
	 * 根据指定零部件、产品的BsoId和指定的筛选条件获得零部件在该筛选条件下被该产品所使用的结构。
	 * 使用结果保存在返回值vector中。
	 * @param partIfc :QMPartIfc 指定的零部件值对象。
	 * @param productBsoId :String 使用该零部件的产品的BsoId。
	 * @param configSpecIfc :PartConfigSpecIfc 指定的筛选条件。
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
	public static Vector getUsedProductStruct(QMPartIfc partIfc,
			String productBsoId, PartConfigSpecIfc configSpecIfc)
			throws QMException {
		Class[] paraClass = { QMPartIfc.class, String.class,
				PartConfigSpecIfc.class };
		Object[] para = { partIfc, productBsoId, configSpecIfc };
		Vector vector = (Vector) helper.request(STANDARD,
				"getUsedProductStruct", paraClass, para);
		return vector;
	}
	//问题(2)20080808 zhangq end
	
	//add whj 2008/09/03
	 /**
	   * 获取列表标题字串
	   * @param s 根据参数s判断获取值对象哪个属性s:"usage","description","reference","route","summary","regulation"
	   * @return
	   * @throws QMException
	   *@see String
	   */
	  public static String[] getListHead(String s)
	  throws QMException
	  {
		  String[] heads=null;
		  String head="";
		  //根据session服务获取当前用户的ID
		  //String userid=getCurUserID();
		  //根据当前用户的ID查询表PartAttrSet对应的字段owner获取PartAttrSetInfo对象
		  PartAttrSetInfo info=getPartAttrSetInfo();
		  //根据参数s判断获取值对象哪个属性s:"usage","description","reference","route","summary","regulation"
		
		  if(s.equals("usage"))
		  {
			  head=info.getUsageAttr();			
			  StringTokenizer st=new StringTokenizer(head,",");
			  int j=st.countTokens();			  
			  heads=new String[j];
			  for(int i=0;st.hasMoreTokens();i++)
			  {				  
				  String en=st.nextToken();				  
				 heads[i]=PartUsageList.toPartUsageList(en).getDisplay();				 
			  }			 
		  }
		 
		  if(s.equals("description"))
		  {			 
			  head=info.getDescriptionAttr();
			  StringTokenizer st=new StringTokenizer(head,",");
			  int j=st.countTokens();
			  heads=new String[j];
			  for(int i=0;st.hasMoreElements();i++)
			  {				
				  String en=st.nextToken();
				 heads[i]=PartDescriptionList.toPartDescriptionList(en).getDisplay();
			  }
			  
		  }
		
			  if(s.equals("reference"))
			  {				 
				  head=info.getReferenceAttr();
				  StringTokenizer st=new StringTokenizer(head,",");
				  int j=st.countTokens();
				  heads=new String[j];
				  for(int i=0;st.hasMoreElements();i++)
				  {					 
					  String en=st.nextToken();
					 heads[i]=PartReferenceList.toPartReferenceList(en).getDisplay();
				  }
			  }
		
				  if(s.equals("route"))
				  {					 
					  head=info.getCappRoute();
					  StringTokenizer st=new StringTokenizer(head,",");
					  int j=st.countTokens();			  
					  heads=new String[j];
					  for(int i=0;st.hasMoreTokens();i++)
					  {				  
						  String en=st.nextToken();				  
						 heads[i]=TechnicsRouteList.toTechnicsRouteList(en).getDisplay();
						 
					  }
				  }
					  if(s.equals("summary"))
					  {
						  head=info.getCappSummary();
						  StringTokenizer st=new StringTokenizer(head,",");
						  int j=st.countTokens();			  
						  heads=new String[j];
						  for(int i=0;st.hasMoreTokens();i++)
						  {				  
							  String en=st.nextToken();				  
							 heads[i]=TechnicsSummaryList.toTechnicsSummaryList(en).getDisplay();
							 
						  }
					  }
						  if(s.equals("regulation"))
						  {
							  head=info.getCappRegulation();
							  StringTokenizer st=new StringTokenizer(head,",");
							  int j=st.countTokens();			  
							  heads=new String[j];
							  for(int i=0;st.hasMoreTokens();i++)
							  {				  
								  String en=st.nextToken();				  
								 heads[i]=TechnicsRegulateionList.toTechnicsRegulateionList(en).getDisplay();
								 
							  }
						  }							  
		 
		  return heads;
	  }
	  
	  
	  /**
	   * 获取列表标题字串(英文)
	   * @param s 根据参数s判断获取值对象哪个属性s:"usage","description","reference","route","summary","regulation"
	   * @return
	   * @throws QMException
	   *@see String
	   */
	  public static String[] getListHeadEn(String s)
	  throws QMException
	  {
		  String[] heads=null;
		  String head="";
		  //根据session服务获取当前用户的ID
		  //String userid=getCurUserID();
		  //根据当前用户的ID查询表PartAttrSet对应的字段owner获取PartAttrSetInfo对象
		  PartAttrSetInfo info=getPartAttrSetInfo();
		  //根据参数s判断获取值对象哪个属性s:"usage","description","reference","route","summary","regulation"
		
		  if(s.equals("usage"))
		  {
			  head=info.getUsageAttr();			
			  StringTokenizer st=new StringTokenizer(head,",");
			  int j=st.countTokens();			  
			  heads=new String[j];
			  for(int i=0;st.hasMoreTokens();i++)
			  {				  
				  String en=st.nextToken();				  
				 heads[i]=en;				 
			  }			 
		  }
		 
		  if(s.equals("description"))
		  {			 
			  head=info.getDescriptionAttr();
			  StringTokenizer st=new StringTokenizer(head,",");
			  int j=st.countTokens();
			  heads=new String[j];
			  for(int i=0;st.hasMoreElements();i++)
			  {				  
				  String en=st.nextToken();
				 heads[i]=en;
			  }			  
		  }
		
			  if(s.equals("reference"))
			  {				 
				  head=info.getReferenceAttr();
				  StringTokenizer st=new StringTokenizer(head,",");
				  int j=st.countTokens();
				  heads=new String[j];
				  for(int i=0;st.hasMoreElements();i++)
				  {					  
					 String en=st.nextToken();
					 heads[i]=en;
				  }
			  }
		
				  if(s.equals("route"))
				  {					 
					  head=info.getCappRoute();
					  StringTokenizer st=new StringTokenizer(head,",");
					  int j=st.countTokens();
					  heads=new String[j];
					  for(int i=0;st.hasMoreElements();i++)
					  {					  
						 String en=st.nextToken();
						 heads[i]=en;
					  }
				  }
					  if(s.equals("summary"))
					  {
						  head=info.getCappSummary();
						  StringTokenizer st=new StringTokenizer(head,",");
						  int j=st.countTokens();			  
						  heads=new String[j];
						  for(int i=0;st.hasMoreTokens();i++)
						  {				  
							  String en=st.nextToken();				  
							 heads[i]=en;				 
						  }
					  }
						  if(s.equals("regulation"))
						  {
							  head=info.getCappRegulation();
							  StringTokenizer st=new StringTokenizer(head,",");
							  int j=st.countTokens();			  
							  heads=new String[j];
							  for(int i=0;st.hasMoreTokens();i++)
							  {				  
								  String en=st.nextToken();				  
								 heads[i]=en;				 
							  }	
						  }
							  
		  return heads;
	  }
	  
	  /**
	   * 获取列表标题的方法
	   * @param s
	   * @return
	   * @throws QMException
	   */
	  public static String[] getListMethod(String s)
	  throws QMException
	  {
		  String[] headingMethod=null;
		  PartAttrSetInfo info=getPartAttrSetInfo();
		  //根据参数s判断获取值对象哪个属性s:"usage","description","reference","route","summary","regulation"
		  String head="";
		  if(s.equals("usage"))
		  {
			  head=info.getUsageAttr();	
		  }
		  else 
			  if(s.equals("description"))
			  {
				  head=info.getDescriptionAttr();
			  }
			  else 
				  if(s.equals("reference"))
				  {
					  head=info.getReferenceAttr();
				  }
				  else if(s.equals("route"))
				  {
					  head = info.getCappRoute();
				  }
				  else if(s.equals("summary"))
				  {
					  head = info.getCappSummary();
				  }
				  else if(s.equals("regulation"))
				  {
					  head = info.getCappRegulation();
				  }
			  StringTokenizer st=new StringTokenizer(head,",");
			  int j=st.countTokens();			  
			  headingMethod=new String[j];
			  for(int i=0;st.hasMoreTokens();i++)
			  {				  
				  String en=st.nextToken();				  
				  headingMethod[i]="get"+en.substring(0,1).toUpperCase()+en.substring(1);				 
			  }		 
		  
		  return headingMethod;
	  }
	  /**
	   * 获取当前用户的ID
	   * @return
	   * @throws QMException
	   *@see String
	   */
	  public static String getCurUserID()
	  throws QMException
	  {
		  Class[] paraClass = {};
		    Object[] para = {};
		  BaseValueIfc base=(BaseValueIfc)  helper.request("SessionService","getCurUserInfo", paraClass, para);
		  return base.getBsoID();
	  }
	  /**
	   * 得到当前用户的“属性设置”对象
	   * @param s
	   * @return
	   *@see PartAttrSetInfo
	   */
	  public static PartAttrSetInfo getPartAttrSetInfo()
	  throws QMException
	  {
		  Class[] paraClass = {};
		    Object[] para = {};
		    PartAttrSetInfo info=(PartAttrSetInfo)  helper.request(STANDARD,"getCurUserInfo", paraClass, para);
		   
		    if(info==null)
		    {
		    	String cappRegulation=RemoteProperty.getProperty("part.manager.cappRegulation",
		    			"technicsNumber,technicsName,technicsType,productState,versionValue");
		    	String cappRoute=RemoteProperty.getProperty("part.manager.cappRoute",
		    			"routeListNumber,routeListName,routeListLevel,routeListState,versionID");
		    	String cappSummary=RemoteProperty.getProperty("part.manager.cappSummary",
		    			"tecTotalNumber,tecTotalName,totalType,lifeCycleState");
		    	String description=RemoteProperty.getProperty("part.manager.description","docNum,docName,versionID");
		    	String reference=RemoteProperty.getProperty("part.manager.reference","number,name");
		    	String usage=RemoteProperty.getProperty("part.manager.usage","number,name,viewName,state,type,producedBy,iterationID,unitName,quantityString");
		    	
		    	String owner=getCurUserID();
		    	info=new PartAttrSetInfo();
		    	info.setCappRegulation(cappRegulation);
		    	info.setCappRoute(cappRoute);
		    	info.setCappSummary(cappSummary);
		    	info.setDescriptionAttr(description);
		    	info.setOwner(owner);
		    	info.setReferenceAttr(reference);
		    	info.setUsageAttr(usage);
		    	 Class[] paraClass1 = {BaseValueIfc.class};
		 	    Object[] para1 = {info};
		 	 info=(PartAttrSetInfo)  helper.request("PersistService","saveValueInfo", paraClass1, para1);
		 	
		    }
		   
		  return info;
	  }
	
	public static Vector getEPMDocument(BaseValueIfc baseIfc)
	throws QMException
	{
		 Class[] paraClass = {BaseValueIfc.class};
		    Object[] para = {baseIfc};
		  Vector epmdocs=(Vector)  helper.request(STANDARD,"getEMPDocument", paraClass, para);
		  return epmdocs;
	}
	
	 /**
     * 根据业务对象ID信息，查找其与相关的所有更改请求（计划）
     * （要求业务对象必须实现ChangeableIfc接口）。
     * @param bsoID 业务对象ID信息,要求业务对象必须实现ChangeableIfc接口。
     * @return 其与相关的所有更改请求（计划）。
     * @throws QMException
     */
    public static Collection getRelevantChangeRequests(String bsoID)
            throws
            QMException
    {
        Collection coll = null;
        ChangeableIfc chageable = null;
        BaseValueInfo qmobj = null;
        if (bsoID == null || bsoID.trim().length() == 0)
        {
            return null;
        }        
        Class[] paraClass = {String.class};
	    Object[] para = {bsoID};
	    qmobj=(BaseValueInfo)  helper.request("PersistService","refreshInfo", paraClass, para);       
        if (!(qmobj instanceof ChangeableIfc))
        {
            throw new QMException("业务对象必须实现ChangeableIfc接口!");
        }
        else
        {
            chageable = (ChangeableIfc) qmobj;
        }
        
        Class[] paraClass1 = {ChangeableIfc.class};
	    Object[] para1 = {chageable};
	    coll=(Vector)  helper.request( "ChangeService","getRelevantChangeRequests", paraClass1, para1);      
        if (coll == null)
        {
            coll = new Vector(0);
        }
        return coll;
    }
    
    /**
     * 根据业务对象ID信息，查找其与相关的所有问题报告
     * （要求业务对象必须实现ChangeableIfc接口）。
     * @param bsoID 业务对象ID信息,要求业务对象必须实现ChangeableIfc接口。
     * @return 其与相关的所有参考分析活动。
     * @throws QMException
     */
    public static Collection getChangeIssues(String bsoID)
            throws
            QMException
    {
    	Collection coll = null;
        ChangeableIfc chageable = null;
        BaseValueInfo qmobj = null;
        if (bsoID == null || bsoID.trim().length() == 0)
        {
            return null;
        }        
        Class[] paraClass = {String.class};
	    Object[] para = {bsoID};
	    qmobj=(BaseValueInfo)  helper.request("PersistService","refreshInfo", paraClass, para);       
        if (!(qmobj instanceof ChangeableIfc))
        {
            throw new QMException("业务对象必须实现ChangeableIfc接口!");
        }
        else
        {
            chageable = (ChangeableIfc) qmobj;
        }
        
        Class[] paraClass1 = {ChangeableIfc.class};
	    Object[] para1 = {chageable};
	    coll=(Vector)  helper.request( "ChangeService","getChangeIssues", paraClass1, para1);      
        if (coll == null)
        {
            coll = new Vector(0);
        }
        return coll;
    }
    
    /**
     * 根据业务对象ID信息，查找其与相关的尚未完成的所有更改活动
     * （要求业务对象必须实现ChangeableInf接口）。
     * @param bsoID 业务对象ID信息,要求业务对象必须实现ChangeableInf接口。
     * @return 其与相关的尚未完成的所有更改活动。
     * @throws QMException
     */
    public static Collection getAffectingChangeActivities(String bsoID)
            throws
            QMException
    {
    	Collection coll = null;
        ChangeableIfc chageable = null;
        BaseValueInfo qmobj = null;
        if (bsoID == null || bsoID.trim().length() == 0)
        {
            return null;
        }        
        Class[] paraClass = {String.class};
	    Object[] para = {bsoID};
	    qmobj=(BaseValueInfo)  helper.request("PersistService","refreshInfo", paraClass, para);       
        if (!(qmobj instanceof ChangeableIfc))
        {
            throw new QMException("业务对象必须实现ChangeableIfc接口!");
        }
        else
        {
            chageable = (ChangeableIfc) qmobj;
        }
        
        Class[] paraClass1 = {ChangeableIfc.class};
	    Object[] para1 = {chageable};
	    coll=(Vector)  helper.request( "ChangeService","getAffectingChangeActivities", paraClass1, para1);      
        if (coll == null)
        {
            coll = new Vector(0);
        }
        return coll;
    }
    
    /**
     * 根据业务对象ID信息，查找其与相关的已完成的所有更改活动
     * （要求业务对象必须实现ChangeableIfc接口）。
     * @param bsoID 业务对象ID信息,要求业务对象必须实现ChangeableIfc接口。
     * @return 其与相关的已完成的所有更改活动。
     * @throws QMException
     */
    public static Collection getImplementedChangeActivities(String bsoID)
            throws
            QMException
    {
    	Collection coll = null;
        ChangeableIfc chageable = null;
        BaseValueInfo qmobj = null;
        if (bsoID == null || bsoID.trim().length() == 0)
        {
            return null;
        }        
        Class[] paraClass = {String.class};
	    Object[] para = {bsoID};
	    qmobj=(BaseValueInfo)  helper.request("PersistService","refreshInfo", paraClass, para);       
        if (!(qmobj instanceof ChangeableIfc))
        {
            throw new QMException("业务对象必须实现ChangeableIfc接口!");
        }
        else
        {
            chageable = (ChangeableIfc) qmobj;
        }
        
        Class[] paraClass1 = {ChangeableIfc.class};
	    Object[] para1 = {chageable};
	    coll=(Vector)  helper.request( "ChangeService","getImplementedChangeActivities", paraClass1, para1);      
        if (coll == null)
        {
            coll = new Vector(0);
        }
        return coll;
    }

    /**
     * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法，
     * 产品中存在的该方法依然保留，以备使用。
     * 根据结构筛选条件过滤出符合结构筛选条件的masterIfc中所有QMPartMasterIfc的管理的QMPartIfc对象的小版本。
     * @param masterIfc MasteredIfc
     * @param partConfigSpecIfc PartConfigSpecIfc
     * @throws QMException
     * @return Collection
     * @author liunan 2008-08-01
     */
    public static Collection filteredIterationsOf(MasteredIfc masterIfc, PartConfigSpecIfc partConfigSpecIfc)
        throws QMException
    {
      Class[] paraClass = {MasteredIfc.class, PartConfigSpecIfc.class};
      Object[] para = {masterIfc, partConfigSpecIfc};
      Collection collection2 = (Collection)helper.request(STANDARD,"filteredIterationsOf", paraClass, para);
      return collection2;
    }

    /**
     * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法，
     * 产品中存在的该方法依然保留，以备使用。
     * <p>根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。</p>
     * <p>本方法调用了bianli()方法实现递归。</p>
     * <p>在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：</p>
     * <p>1、如果不定制属性：BsoID，号码、名称、是（否）可分（"true","false"）、数量（转化为字符型）、版本和视图。</p>
     * <p>2、如果定制属性：BsoID，号码、名称、是（否）可分、数量、其他定制属性。</p>
     * @param partIfc :QMPartIfc 指定的零部件的值对象。
     * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
     * @param affixAttrNames : String[] 定制的要输出的扩展属性集合；可以为空。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理。
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理。
     * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的对当前零部件的筛选条件。
     * @throws QMException
     * @return Vector
     * @author liunan 2008-08-01
     */
    public static Vector setBOMList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames,String[] routeNames,
                             String source, String type, PartConfigSpecIfc configSpecIfc)
        throws QMException
    {
      Class[] paraClass = {QMPartIfc.class, String[].class, String[].class, String[].class, String.class, String.class, PartConfigSpecIfc.class};
      Object[] para = {partIfc, attrNames, affixAttrNames, routeNames, source, type, configSpecIfc};
      //CCBegin by liunan 2009-02-11
      //Vector vector = (Vector)helper.request(STANDARD,"setBOMList", paraClass, para);
      Vector vector = (Vector)helper.request("JFService","setBOMList", paraClass, para);
      //CCEnd by liunan 2009-02-11
      return vector;
    }

  /**
   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法，
   * 产品中存在的该方法依然保留，以备使用。
   * <p>分级物料清单的显示。</p>
   * <p>返回结果是vector,其中vector中的每个元素都是一个集合：</p>
   * <p>0：当前part的BsoID。</p>
   * <p>1：当前part所在的级别，转化为字符型。</p>
   * <p>2：当前part的编号。</p>
   * <p>3：当前part的名称。</p>
   * <p>4：当前part被最顶层部件使用的数量，转化为字符型。</p>
   * <p>5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；</p>
   * <p>              如果定制了属性：按照所有定制的属性加到结果集合中。</p>
   * <p>本方法调用了递归方法：</p>
   * <p>fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc, PartUsageLinkIfc partLinkIfc, int parentQuantity)。</p>
   * @param partIfc :QMPartIfc 最顶级的部件值对象。
   * @param attrNames :String[] 定制的属性，可以为空。
   * @param affixAttrNames : String[] 定制的扩展属性名集合，可以为空。
   * @param routeNames : String[] 定制的工艺路线名集合，可以为空。
   * @param configSpecIfc :PartConfigSpecIfc 结构筛选条件。
   * @throws QMException
   * @return Vector
   */
  public static Vector setMaterialList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, String[] routeNames, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, String[].class, String[].class, String[].class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, attrNames, affixAttrNames, routeNames, configSpecIfc};
    //CCBegin by liunan 2009-02-11
    //Vector vector = (Vector)helper.request(STANDARD,"setMaterialList", paraClass, para);
    Vector vector = (Vector)helper.request("JFService","setMaterialList", paraClass, para);
    //CCEnd by liunan 2009-02-11
    return vector;
  }
  
//CCBegin by leixiao	 2010-12-20  增加逻辑总成数量报表,增加参数islogic
  public static Vector setMaterialList(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, String[] routeNames, PartConfigSpecIfc configSpecIfc,boolean islogic)
  throws QMException
{
Class[] paraClass = {QMPartIfc.class, String[].class, String[].class, String[].class, PartConfigSpecIfc.class,boolean.class};
Object[] para = {partIfc, attrNames, affixAttrNames, routeNames, configSpecIfc,islogic};
Vector vector = (Vector)helper.request("JFService","setMaterialList", paraClass, para);
return vector;
}
//CCEnd by leixiao	 2010-12-20  增加逻辑总成数量报表,增加参数islogic
  
  /**
   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法。
   * <p>分级物料清单的显示。</p>
   * <p>返回结果是vector,其中vector中的每个元素都是一个集合：</p>
   * <p>0：当前part的BsoID。</p>
   * <p>1：当前part所在的级别，转化为字符型。</p>
   * <p>2：当前part的编号。</p>
   * <p>3：当前part的名称。</p>
   * <p>4：当前part被最顶层部件使用的数量，转化为字符型。</p>
   * <p>5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；</p>
   * <p>              如果定制了属性：按照所有定制的属性加到结果集合中。</p>
   * <p>本方法调用了递归方法：</p>
   * <p>fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc, PartUsageLinkIfc partLinkIfc, int parentQuantity)。</p>
   * @param partIfc :QMPartIfc 最顶级的部件值对象。
   * @param attrNames :String[] 定制的属性，可以为空。
   * @param affixAttrNames : String[] 定制的扩展属性名集合，可以为空。
   * @param routeNames : String[] 定制的工艺路线名集合，可以为空。
   * @param configSpecIfc :PartConfigSpecIfc 结构筛选条件。
   * @throws QMException
   * @return Vector
   * @author liunan 2008-08-05
   */
  public static Vector setMaterialList2(QMPartIfc partIfc, String[] attrNames, String[] affixAttrNames, String[] routeNames, PartConfigSpecIfc configSpecIfc)
      throws QMException
  {
    Class[] paraClass = {QMPartIfc.class, String[].class, String[].class, String[].class, PartConfigSpecIfc.class};
    Object[] para = {partIfc, attrNames, affixAttrNames, routeNames, configSpecIfc};
    //CCBegin by liunan 2009-02-11
    //Vector vector = (Vector)helper.request(STANDARD,"setMaterialList2", paraClass, para);
    Vector vector = (Vector)helper.request("JFService","setMaterialList2", paraClass, para);
    //CCEnd by liunan 2009-02-11    
    return vector;
  }
}
