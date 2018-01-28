/** 生成程序 StandardPartService.java    1.0    2003/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/04/27 谢斌 原因:逻辑混乱
 *                     方案:重写服务端逻辑。
 *                     备注:解放v3r11-分级物料清单性能优化-谢斌
 * CR2 20090619 张强 修改原因：进行结构比较后，产品信息管理中配置规范显示为结构比较目标件的配置规范。（TD-2190） 
 *                   解决方案：进行结构比较后，产品信息管理中正确显示的配置规范。       
 * SS1 2013-1-21 产品信息管理器中输出物料清单功能中增加输出带有erp属性报表功能         
 * SS2 通过masterid获取最新版本的零部件。 liunan 2013-11-25
 * SS3 增加产品信息管理器零件树定位搜索零件功能 pante 2015-01-14
 * SS4 A004-2016-3286 整车一级件清单 liunan 2016-1-20
 * SS5  成都工序添加获得子件 guoxiaoliang 2016-7-28
 */
package com.faw_qm.part.ejb.standardService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartDescribeLinkIfc;
import com.faw_qm.part.model.PartReferenceLinkIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;

//CCBegin by liunan 2008-08-05
import com.faw_qm.version.model.MasteredIfc;
//CCBegin by liunan 2008-08-05
import com.faw_qm.part.model.PartAttrSetIfc;

//CCBegin SS2
import com.faw_qm.part.model.QMPartInfo;
//CCEnd SS2

/**
 * 标准版服务，包括零部件的基本维护功能（创建、更新、删除、重命名、另存为）；
 * 零部件基本信息查询（使用、参考、描述等关联关系）；
 * 对零部件的版本管理（检入、检出、撤消检出、修订、查看版本和版序、发布等功能）；
 * 零部件的统计汇总功能等服务方法。
 * @author 吴先超
 * @version 1.0
 */
public interface StandardPartService extends BaseService
{
    /**
     * 通过零部件的名字和号码查找零部件的主信息。返回的集合中只有一个QMPartMasterIfc对象。
     * @param partName :String 零部件的名称。
     * @param partNumber :String 零部件的号码。
     * @return collection :查找到的QMPartMasterIfc零部件主信息对象的集合，只有一个元素。
     * @throws QMException
     */
    public Collection findPartMaster(String partName, String partNumber)
            throws QMException;

    /**
     * 通过一个零部件主信息找到该零部件所有版本值对象的集合。
     * @param partMasterIfc :QMPartMasterIfc对象。
     * @return collection :Collection 所有对应于partMasterIfc的零部件对象(QMPartIfc)的集合。
     * @throws QMException
     */
    public Collection findPart(QMPartMasterIfc partMasterIfc)
            throws QMException;

    /**
     * 删除参考文档与零部件的关联关系。
     * @param linkIfc PartReferenceLinkIfc
     * @throws PartException
     */
    public void deletePartReferenceLink(PartReferenceLinkIfc linkIfc)
            throws PartException;

    /**
     * 保存参考文档与零部件的关联关系。
     * @param linkIfc PartReferenceLinkIfc
     * @throws PartException
     * @return PartReferenceLinkIfc
     */
    public PartReferenceLinkIfc savePartReferenceLink(
            PartReferenceLinkIfc linkIfc) throws PartException;

    /**
     * 保存零部件与零部件主信息的关联关系。
     * @param linkIfc PartUsageLinkIfc
     * @return PartUsageLinkIfc
     * @throws PartException
     */
    public PartUsageLinkIfc savePartUsageLink(PartUsageLinkIfc linkIfc)
            throws PartException;

    /**
     * 删除零部件与零部件主信息的关联关系。
     * @param linkIfc PartUsageLinkIfc
     * @throws PartException
     */
    public void deletePartUsageLink(PartUsageLinkIfc linkIfc)
            throws PartException;

    /**
     * 保存描述文档与零部件的关联关系。
     * @param linkIfc PartDescribeLinkIfc
     * @throws PartException
     * @return PartDescribeLinkIfc
     */
    public PartDescribeLinkIfc savePartDescribeLink(PartDescribeLinkIfc linkIfc)
            throws PartException;

    /**
     * 删除描述文档与零部件的关联关系。
     * @param linkIfc PartDescribeLinkIfc
     * @throws PartException
     */
    public void deletePartDescribeLink(PartDescribeLinkIfc linkIfc)
            throws PartException;

    /**
     * 修订零部件。重新生成一个新的大版本。
     * @param partIfc :QMPartIfc 修订前的零部件的值对象，必须为最新的迭代版本。
     * @return partIfc:QMPartIfc 修订后的零部件的值对象。
     * @throws QMException
     */
    public QMPartIfc revisePart(QMPartIfc partIfc) throws QMException;

    /**
     * 根据零部件值对象获得该零部件关联的所有参考文档(DocIfc)最新版本的值对象的集合。
     * 先根据零部件获取所有的参考文档主信息(DocMasterIfc)的集合,再对DocMasterIfc集合
     * 进行过滤，找出每个DocMasterIfc所对应的最新版本DocIfc。最后返回DocIfc的集合。
     * @param partIfc :QMPartIfc 指定的零部件的值对象。
     * @return Vector 零部件参考文档(DocIfc)值对象的集合。
     * @throws QMException
     */
    public Vector getReferencesDocMasters(QMPartIfc partIfc) throws QMException;

    /**
     * 根据指定的零部件的值对象返回该零部件描述文档的值对象集合。
     * @param partIfc :QMPartIfc零部件的值对象。
     * @param flag : boolean
     * @return vector:Vector 零部件描述文档的值对象集合。
     * @throws QMException
     */
    public Vector getDescribedByDocs(QMPartIfc partIfc, boolean flag)
            throws QMException;

    /**
     * 根据主文档的值对象获得被参考的零部件的值对象的集合。
     * @param docMasterIfc :DocMasterIfc 参考的文档的值对象。
     * @return vector :Vector 被文档参考的零部件的值对象的集合。
     * @throws QMException
     */
    public Vector getReferencedByParts(DocMasterIfc docMasterIfc)
            throws QMException;

    /**
     * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
     * @param partIfc :QMPartIfc 零部件的值对象。
     * @return collection:Collection 与partIfc关联的PartUsageLinkIfc的对象集合。
     * @throws QMException
     */
    public Collection getUsesPartMasters(QMPartIfc partIfc) throws QMException;

    /**
     * 根据指定的零部件返回下级零部件的最新版本的值对象的集合。
     * @param partIfc :QMPartIfc 零部件的值对象。
     * @return collection:Collection partIfc使用的下级子件的最新版本的值对象集合。
     * @throws QMException
     */
    public Collection getSubParts(QMPartIfc partIfc) throws QMException;

    /**
     * 根据指定零部件返回所有下级零部件的最新版本的值对象集合。
     * @param partIfc :QMPartIfc 零部件的值对象。
     * @return collection:Collection partIfc使用的下级子件的最新版本的值对象集合。
     * @throws QMException
     */
    public Collection getAllSubParts(QMPartIfc partIfc) throws QMException;

    /**
     * 根据指定的QMPartMasterIfc对象，
     * 通过两个表联合查询获得在PartUsageLink表中与QMPartMasterIfc关联的最新版本
     * 的QMPartIfc对象的集合。
     * @param partMasterIfc :QMPartMasterIfc对象。
     * @return collection 和partMasterIfc在PartUsageLink表中关联的最新版本QMPartIfc对象的集合。
     * @throws QMException
     */
    public Collection getUsedByParts(QMPartMasterIfc partMasterIfc)
            throws QMException;

    /**
     * 通过指定的筛选条件，将集合collection中的PartUsageLinkIfc对象对应的
     * 符合筛选条件的Iterated部件进行筛选，如果不符合筛选条件则返回对应的Mastered主零部件。
     * @param collection :Collection 是PartUsageLinkIfc对象的集合。
     * @param configSpecIfc :PartConfigSpecIfc 零部件的筛选条件。
     * @return collection:Collection 对象，每个元素为一个数组:
     * 数组的第一个元素为PartUsageLinkIfc对象，第二个元素为QMPartIfc对象，
     * 如果没有QMPartIfc对象，为关联的QMPartMasterIfc对象。
     * @throws QMException
     */
    public Collection getUsesPartsFromLinks(Collection collection,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * 保存零部件。
     * @param partIfc :QMPartIfc 要保存的零部件的值对象。
     * @return partIfc:QMPartIfc 保存后的零部件的值对象。
     * @throws QMException
     */
    public QMPartIfc savePart(QMPartIfc partIfc) throws QMException;

    /**
     * 删除指定的零部件，如果有其他部件使用该部件，
     * 则异常"该零部件已经被其他部件使用，不能删除！"。
     * @param partIfc :QMPartIfc 要删除的零部件的值对象。
     * @throws QMException
     */
    public void deletePart(QMPartIfc partIfc) throws QMException;

    /**
     * 判断零部件partIfc2是否是零部件partIfc1的祖先部件或是partIfc1本身。
     * @param partIfc1 :QMPartIfc 指定的零部件的值对象。
     * @param partIfc2 :QMPartIfc 被检验的零部件的值对象。
     * @return flag:boolean:
     * flag==true:零部件part2是零部件part1的祖先部件或是part1本身。
     * flag==false:零部件part2不是零部件part1的祖先部件或part1本身。
     * @throws QMException
     */
    public boolean isParentPart(QMPartIfc partIfc1, QMPartIfc partIfc2)
            throws QMException;

    /**
     * 获得指定零部件的所有父部件的主信息值对象集合。
     * @param partIfc :QMPartIfc 指定的零部件的值对象。
     * @return vector:Vector 指定零部件的所有父部件(直到根部件)的主信息值对象的集合。
     * @throws QMException
     */
    public Vector getAllParentParts(QMPartIfc partIfc) throws QMException;

    /**
     * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。
     * 本方法调用了bianli()方法实现递归。
     * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
     * 1、如果不定制属性：
     * BsoID，号码、名称、是（否）可分（"true","false"）、数量（转化为字符型）、版本和视图；
     * 2、如果定制属性：
     * BsoID，号码、名称、是（否）可分、数量、其他定制属性。

     * @param partIfc :QMPartIfc 指定的零部件的值对象。
     * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
     * @param affixAttrNames : String[] 定制的要输出的扩展属性集合；可以为空。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理。
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理。
     * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的对当前零部件的筛选条件。
     * @throws QMException
     * @return Vector
     */
    public Vector setBOMList(QMPartIfc partIfc, String[] attrNames,
            String[] affixAttrNames, String source, String type,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * 分级物料清单的显示。
     * 返回结果是vector,其中vector中的每个元素都是一个集合：
     * 0：当前part的BsoID；
     * 1：当前part所在的级别，转化为字符型；
     * 2：当前part的编号；
     * 3：当前part的名称；
     * 4：当前part被最顶层部件使用的数量，转化为字符型；
     * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
     *                如果定制了属性：按照所有定制的属性加到结果集合中。
     * 本方法调用了递归方法：
     * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
     * PartUsageLinkIfc partLinkIfc, int parentQuantity);
     * @param partIfc :QMPartIfc 最顶级的部件值对象。
     * @param attrNames :String[] 定制的属性，可以为空。
     * @param affixAttrNames : String[] 定制的扩展属性名集合，可以为空。
     * @param configSpecIfc :PartConfigSpecIfc 配置规范。
     * @throws QMException
     * @return Vector
     */
    public Vector setMaterialList(QMPartIfc partIfc, String[] attrNames,
            String[] affixAttrNames, PartConfigSpecIfc configSpecIfc)
            throws QMException;

    /**
     * 根据指定零部件和指定的筛选条件获得零部件在该筛选条件下被哪些部件所使用。
     * 使用结果保存在返回值vector中。
     * vector返回值的数据结构为：vector中的每个元素都是Vector类型的，这里为了叙述方便，起名为subVector.
     * 而subVector的每个元素都是String[3]类型的。
     * 这个String[3]分别记录了::
     * String[0]:层次号；
     * String[1]:零部件编号(零部件名称)版本(视图);
     * String[2]零部件在此结构中(顶级件中)使用的数量，所以在同一结构下的记录使用数量是相同的。

     * @param partIfc :QMPartIfc 指定的零部件值对象。
     * @param configSpecIfc :PartConfigSpecIfc 指定的筛选条件。
     * @return vector:Vector 被其他部件所使用的信息集合。
     * @throws QMException
     */
    public Vector setUsageList(QMPartIfc partIfc,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * 通过指定的配置规范，在数据库中寻找使用partIfc所对应的PartMasterIfc的所有部件(QMPartIfc)，
     * 返回值是以(PartUsageLinkIfc, QMPartIfc)为元素的集合。
     * @param partIfc 零部件值对象。
     * @param configSpecIfc 零部件配置规范。
     * @return Collection
     * @throws QMException
     */
    public Collection getParentPartsByConfigSpec(QMPartIfc partIfc,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * 根据指定的配置规范为查询空间添加查询条件。
     * @param partConfigSpecIfc 零部件配置规范值对象。
     * @param query QMQuery
     * @throws QMException
     * @throws QueryException
     * @return QMQuery
     */
    public QMQuery appendSearchCriteria(PartConfigSpecIfc partConfigSpecIfc,
            QMQuery query) throws QMException, QueryException;

    /**
     * 根据配置规范过滤出符合配置规范的collection中所有QMPartMasterIfc的管理的QMPartIfc对象的小版本。
     * @param collection Collection
     * @param partConfigSpecIfc PartConfigSpecIfc
     * @throws QMException
     * @return Collection
     */
    public Collection filteredIterationsOf(Collection collection,
            PartConfigSpecIfc partConfigSpecIfc) throws QMException;

    /**
     * 查询当前用户的配置规范。
     * @throws QMException
     * @return PartConfigSpecIfc
     */
    public PartConfigSpecIfc findPartConfigSpecIfc() throws QMException;

    /**
     * 根据指定配置规范，获得指定部件的使用结构：
     * 返回集合的每个元素是一个数组对象，第0个元素记录关联对象信息，
     * 第1个元素记录关联对象记录的use角色的mastered对象匹配配置规范的iterated对象，
     * 如果没有匹配对象，记录mastered对象。
     * @param partIfc QMPartIfc
     * @param configSpecIfc PartConfigSpecIfc
     * @throws QMException
     * @return Collection
     */
    public Collection getUsesPartIfcs(QMPartIfc partIfc,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    
    /**
     * 根据指定配置规范，获得指定部件的使用结构：
     * 返回集合Collection的每个元素是一个数组对象，第0个元素记录关联对象信息，
     * 第1个元素记录关联对象记录的use角色的mastered对象匹配配置规范的iterated对象，
     * 如果没有匹配对象，记录mastered对象。
     * @param partIfc 零部件值对象。
     * @return
     * @throws QMException
     */
    public Collection getUsesPartIfcs(QMPartIfc partIfc) throws QMException;
    
    /**
     * 根据配置规范中包含的信息过滤结果集和。
     * @param configSpecIfc PartConfigSpecIfc
     * @param collection Collection
     * @throws QMException
     * @throws QueryException
     * @return Collection
     */
    public Collection process(PartConfigSpecIfc configSpecIfc,
            Collection collection) throws QMException, QueryException;

    /**
     * 1，如果新的配置规范没有持久化保存，为其指定所有者；
     * 2，查询数据库，获得当前用户的旧的配置规范；
     * 3，如果存在旧配置规范，判断新的配置规范是否为空，若是空，
     *    从数据库中删除旧配置规范，否则，将新配置规范的数据赋值到旧配置规范中，更新数据库。
     * 4，如果之前不存在旧的配置规范，将新配置规范持久化保存。
     * @param configSpecIfc PartConfigSpecIfc
     * @throws QMException
     * @return PartConfigSpecIfc
     */
    public PartConfigSpecIfc savePartConfigSpecIfc(
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * 通过名称和号码查找主零部件。允许模糊查询。
     * 如果name为null，按号码查询；如果number为null，按名称查询；
     * 如果name和numnber都为null，获得所有主零部件的值对象。
     * @param name :String 模糊查询的零部件名称。
     * @param number :String 模糊查询的零部件的号码。
     * @return collection:Collection 符合查询条件的主零部件的值对象的集合。
     * @throws QMException
     */
    public Collection getAllPartMasters(String name, String number)
            throws QMException;

    /**
     * 通过名称和号码查找主零部件。允许模糊查询。如果name为null，按号码查询；如果number
     * 为null，按名称查询；如果name和numnber都为null，获得所有主零部件的值对象。
     * 如果参数nameFlag为true, 查找零部件名称和name不相同的那些零部件，否则，查找零部件
     * 名称和name相同的零部件。对参数numFlag作同样的处理。
     * @param name 待查询的零部件名称。
     * @param nameFlag 过滤条件。
     * @param number 待查询的零部件编号。
     * @param numFlag 过滤条件。
     * @return Collection 查询出来的主零部件(QMPartMasterIfc)的集合。
     * @throws QMException
     */
    public Collection getAllPartMasters(String name, boolean nameFlag,
            String number, boolean numFlag) throws QMException;

    /**
     * 根据partIfc寻找对应的partMasterIfc,再查找partUsageLink，获取使用了该partMasterIfc
     * 的所有零部件。
     * @param partIfc :QMPartIfc 零部件值对象。
     * @exception QMException 持久化服务的异常。
     * @return Collection QMPartIfc的集合。
     */
    public Collection getPartsByUse(QMPartIfc partIfc) throws QMException;

    /**
     * 根据指定的零部件值对象和筛选条件获得零部件的产品结构。返回Vector:
     * Vector中存放NormalPart的对象的集合，每个NormalPart对象中的属性包括：
     * 号码+名称+版本+视图+数量+单位。注意，返回值Vector中不包含当前传入的参数partIfc。
     * @param partIfc :当前零部件值对象。
     * @param configSpecIfc 当前零部件配置规范值对象。
     * @return Vector
     * @throws QMException
     */
    public Vector getProductStructure(QMPartIfc partIfc,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * 根据指定的零部件值对象和筛选条件获得零部件的子。返回Vector:
     * Vector中存放NormalPart的对象的集合，每个NormalPart对象中的属性包括：
     * 号码+名称+版本+视图+数量+单位。注意，返回值Vector中不包含当前传入的参数partIfc。
     * @param partIfc :当前零部件值对象。
     * @param configSpecIfc 当前零部件配置规范值对象。
     * @return Vector
     * @throws QMException
     */
    public Vector getSubProductStructure(QMPartIfc partIfc,
            PartConfigSpecIfc configSpecIfc) throws QMException;

    /**
     * 修改零部件（此方法供客户端直接调用）的名称，编号。
     *
     * @param partMasterIfc QMPartMasterIfc值对象。
     * @param flag 是否修改零部件的编号,true为修改,false不修改。
     * @return partMasterIfc 修改后的零部件的值对象。
     * @throws QMException
     */
    public QMPartMasterIfc renamePartMaster(QMPartMasterIfc partMasterIfc,
            boolean flag) throws QMException;

    //CR2 Begin 20090619 zhangq  修改原因：TD-2190。
    /**
     * 根据离散的数据信息构造PartConfigSpecIfc对象。一共十二个参数。
     * @param effectivityActive 是否是有效性配置规范 是1,否0。
     * @param baselineActive 是否是基准线配置规范 是1,否0。
     * @param standardActive 是否是标准配置规范 是1,否0。
     * @param baselineID 基准线的BsoID。
     * @param configItemID 配置规范的BsoID。
     * @param viewObjectID 视图对象的bsoID。
     * @param effectivityType 有效性类型。
     * @param effectivityUnit 有效性单位 需要持久化。
     * @param state 状态。
     * @param workingIncluded 是否在个人文件夹，默认为0:false,1:true。
     * @return PartConfigSpecIfc 封装好的零部件配置规范值对象。
     * @throws QMException
     */
    public PartConfigSpecIfc hashtableToPartConfigSpecIfc(
            String effectivityActive, String baselineActive,
            String standardActive, String baselineID, String configItemID,
            String viewObjectID, String effectivityType,
            String effectivityUnit, String state, String workingIncluded)
            throws QMException;
    
    /**
     * 根据离散的数据信息构造PartConfigSpecIfc对象。一共十二个参数。
     * @param isUseCache 是否用缓存的配置规范的标志 是1,否0。
     * @param effectivityActive 是否是有效性配置规范 是1,否0。
     * @param baselineActive 是否是基准线配置规范 是1,否0。
     * @param standardActive 是否是标准配置规范 是1,否0。
     * @param baselineID 基准线的BsoID。
     * @param configItemID 配置规范的BsoID。
     * @param viewObjectID 视图对象的bsoID。
     * @param effectivityType 有效性类型。
     * @param effectivityUnit 有效性单位 需要持久化。
     * @param state 状态。
     * @param workingIncluded 是否在个人文件夹，默认为0:false,1:true。
     * @return PartConfigSpecIfc 封装好的零部件配置规范值对象。
     * @throws QMException
     */
    public PartConfigSpecIfc hashtableToPartConfigSpecIfc(String isUseCache,
            String effectivityActive, String baselineActive,
            String standardActive, String baselineID, String configItemID,
            String viewObjectID, String effectivityType,
            String effectivityUnit, String state, String workingIncluded)
            throws QMException;
    //CR2 End 20090619 zhangq

    /**
     * 根据文档值对象查询数据库，获取所有被该文档所描述的零部件的集合。
     * 如果flag = true, 表示只返回关联的另一边的零部件(QMPartIfc)的集合，如果flag = false
     * 返回关联类(PartDescribeLinkIfc)的集合。
     * @param docIfc DocIfc 文档值对象。
     * @param flag boolean 决定返回值的结构。
     * @return Collection
     * @throws QMException
     */
    public Collection getDescribesQMParts(DocIfc docIfc, boolean flag)
            throws QMException;

    /**
     * 获取所有直接使用当前零件的零部件，
     * 即根据当前的传入的零件QMPartIfc找到对应的PartMaster，
     * 再调用getUsedByParts(QMPartMasterIfc partMasterIfc):Collection
     * 方法找到所有的使用该零件的集合。
     * @param partIfc QMPartIfc
     * @return Vector
     * @throws QMException
     */
    public Vector getParentParts(QMPartIfc partIfc) throws QMException;

    /**
     * add by 孙珂鑫 2004.5.24 获得给定零部件的另存为历史部件，即若A另存为得到B，
     * B另存为得到C，则给出c的bsoID则可以得到B和A的信息。
     *
     * @param partID String
     * @return java.util.Collection
     */
    public Collection findAllPreParts(String partID);

    /**
     * add by 孙珂鑫 2004.6.14 获得由给定零部件衍生出的零部件，即由给定零部件另存为得到的零部件。
     *
     * @param partID String
     * @return java.util.Collection
     */
    public Collection findAllSaveAsParts(String partID);

    /**
     * 根据指定的具体零部件获取其所有类型为产品的父级零部件的最新版本。
     * @param qmPartIfc QMPartIfc 具体零部件。
     * @throws QMException
     * @return Collection 父级零部件的最新版本。
     */
    public Collection getParentProduct(QMPartIfc qmPartIfc) throws QMException;

    /**
     * 获取子零部件在父零部件中的使用数量。
     * @param parentPartIfc QMPartIfc 父零部件。
     * @param childPartIfc QMPartIfc 子零部件。
     * @throws QMException
     * @return String 使用数量。
     */
    public String getPartQuantity(QMPartIfc parentPartIfc,
            QMPartIfc childPartIfc) throws QMException;

    /**
     * 获取子零部件在父零部件中的使用数量。
     * @param parentPartIfc QMPartIfc 父零部件。
     * @param middlePartMasterIfc QMPartMasterIfc 中间零部件的主信息。
     * @param childPartIfc QMPartIfc 子零部件。
     * @throws QMException
     * @return String 使用数量。
     */
    public String getPartQuantity(QMPartIfc parentPartIfc,
            QMPartMasterIfc middlePartMasterIfc, QMPartIfc childPartIfc)
            throws QMException;

    /**
     * 在指定产品中，获取指定子零部件的所有父零部件。
     * @param parentPartMasterIfc QMPartMasterIfc 产品的主信息。
     * @param childPartMasterIfc QMPartMasterIfc 该产品中的子零部件的主信息。
     * @throws QMException
     * @return HashMap 父零部件集合,键：PartNumber，值：值对象。
     */
    public HashMap getParentPartsFromProduct(
            QMPartMasterIfc parentPartMasterIfc,
            QMPartMasterIfc childPartMasterIfc) throws QMException;

    /**
     * 为业务对象设置默认生命周期。
     * @param basevalue BaseValueIfc
     * @throws QMException
     * @return BaseValueIfc
     */
    public BaseValueIfc setLifeCycle(BaseValueIfc basevalue) throws QMException;

    /**
     * 获取符合配置规范的零部件。
     * @param partMasterIfc QMPartMasterIfc 零部件主信息。
     * @param partConfigSpecIfc PartConfigSpecIfc 配置规范。
     * @throws QMException
     * @return QMPartIfc
     */
    public QMPartIfc getPartByConfigSpec(QMPartMasterIfc partMasterIfc,
            PartConfigSpecIfc partConfigSpecIfc) throws QMException;

    /**
     * 计算产品下所有子件在产品中使用的数量。
     * @param product
     * @param partConfigSpecIfc
     * @return HashMap 键是子件的bsoid，值是一个数组，第一位放子件的值对象，第2位
     放子件在产品中的使用数量。
     * @throws QMException
     */
    public HashMap getAllSubCounts(QMPartIfc product,
            PartConfigSpecIfc partConfigSpecIfc) throws QMException;

    /**
     * 根据指定的具体零部件集合获取其所有类型为产品的父级零部件的最新版本。
     * @param Collection QMPartIfcs 具体零部件集合。
     * @throws QMRemoteException
     * @throws QMException
     * @return Collection 父级零部件的最新版本集合。
     */
    public Collection getParentProduct(Collection qmPartIfcs)
            throws QMException;

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
     * @return 零部件值对象集合。
     * @throws QMException
     */
    public Collection findAllPartInfo(String partnumber, String checkboxNum,
            String partname, String checkboxName, String partver,
            String checkboxVersion, String partview, String checkboxView,
            String partstate, String checkboxLifeCState, String parttype,
            String checkboxPartType, String partby, String checkboxProducedBy,
            String partproject, String checkboxProject, String partfolder,
            String checkboxFolder, String partcreator, String checkboxCreator,
            String partupdatetime, String checkboxModifyTime)
            throws QMException;
    /**
     * 20070611 add whj for new request moth QMProductManager.isParentPart()
     * 获得指定零部件的所有父部件的主信息值对象集合。
     * @param partIfc :QMPartIfc 指定的零部件的值对象。
     * @return vector:Vector 指定零部件的所有父部件(直到根部件)的主信息值对象的集合。
     * @throws QMException
     */
    public Vector getAllParentsByPart(QMPartIfc partIfc) throws QMException;
    /**
     *  20070611 add whj for new request moth QMProductManager.isParentPart()
     * 获取所有直接使用当前零件的零部件，
     * 即根据当前的传入的零件QMPartIfc找到对应的PartMaster，
     * 再调用getUsedByParts(QMPartMasterIfc partMasterIfc):Collection
     * 方法找到所有的使用该零件的集合。
     * @param partIfc QMPartIfc
     * @throws QMException
     * @return Vector
     */
    public Vector getParentsByPart(QMPartIfc partIfc) throws QMException;
    /**
     * 20070611 add whj for new request moth QMProductManager.isParentPart()
     * 根据指定的QMPartMasterIfc对象，
     * 通过两个表联合查询获得在PartUsageLink表中与QMPartMasterIfc关联的最新版本
     * 的QMPartIfc对象的集合。
     * @param partMasterIfc :QMPartMasterIfc对象。
     * @return collection 和partMasterIfc在PartUsageLink表中关联的最新版本QMPartIfc对象的集合。
     * @throws QMException
     */
    public Collection getUsedByPParts(QMPartMasterIfc partMasterIfc) throws QMException;

    /**
     * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。
     * 本方法调用了bianli()方法实现递归。
     * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
     * 1、如果不定制属性：
     * BsoID，号码、名称、是（否）可分（"true","false"）、数量（转化为字符型）、版本和视图；
     * 2、如果定制属性：
     * BsoID，号码、名称、是（否）可分、数量、其他定制属性。

     * @param partIfc :QMPartIfc 指定的零部件的值对象.
     * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准                                                                                     版输出。
     * @param affixAttrNames : String[] 定制的要输出的扩展属性集合；可以为空。
     * @param routeNames : String[] 定制的工艺路线名集合，可以为空。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理；
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理；
     * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的对当前零部件的筛选条件。
     * @throws QMException
     * @return Vector
     * @author liunan 2008-08-12
     */
    public Vector setBOMList(QMPartIfc partIfc, String attrNames[],
                             String affixAttrNames[], String[] routeNames,
                             String source, String type,
                             PartConfigSpecIfc configSpecIfc) throws QMException ;

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
    public Vector setMaterialList(QMPartIfc partIfc, String attrNames[]) throws//CR1：重写服务端逻辑。
        QMException;

  /**
   * liuming add 20070209
   * 分级物料清单的显示。
   * 返回结果是vector,其中vector中的每个元素都是一个集合：
   * 0：当前part的BsoID；
   * 1：当前part所在的级别，转化为字符型；
   * 2：当前part的编号；
   * 3：当前part的名称；
   * 4：当前part被最顶层部件使用的数量，转化为字符型；
   * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
   *                如果定制了属性：按照所有定制的属性加到结果集合中。
   * 本方法调用了递归方法：
   * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
   * PartUsageLinkIfc partLinkIfc, int parentQuantity);
   * @param partIfc :QMPartIfc 最顶级的部件值对象。
   * @param attrNames :String[] 定制的属性，可以为空。
   * @param affixAttrNames : String[] 定制的扩展属性名集合，可以为空。
   * @param routeNames : String[] 定制的工艺路线名集合，可以为空。
   * @param configSpecIfc :PartConfigSpecIfc 配置规范。
   * @throws QMException
   * @return Vector
   * @author liunan 2008-08-12
   */
  public Vector setMaterialList2(QMPartIfc partIfc, String attrNames[],
                                String affixAttrNames[], String[] routeNames,
                                PartConfigSpecIfc configSpecIfc) throws
      QMException;

    /**
     * 根据配置规范过滤出符合配置规范的collection中所有QMPartMasterIfc的管理的QMPartIfc对象的小版本。
     * @param masterIfc MasteredIfc 待处理的值对象。
     * @param configSpecIfc PartConfigSpecIfc
     * @throws QMException
     * @return Collection
     * @author liunan 2008-08-01
     */
    public Collection filteredIterationsOf(MasteredIfc masterIfc,
        PartConfigSpecIfc configSpecIfc) throws QMException;

  /**
   * 将物料清单输出成本地文件，用于分级报表的输出。
   * 在com.faw_qm.part.client.other.controller.MaterialController调用了此方法。
   * @param map HashMap 整理好的报表属性集合。
   * @throws QMException
   * @return String 按规则整理出全部报表的信息，用于进行本地文件写入。
   * @author liunan 2008-08-01
   */
  public String getExportBOMClassfiyString(HashMap map) throws QMException;

  /**
   * 将物料清单输出成本地文件，用于无合件装配表的输出。
   * 在com.faw_qm.part.client.other.view.LogicBomFrame调用了此方法。
   * @param map HashMap 整理好的报表属性集合。
   * @throws QMException
   * @return String 按规则整理出全部报表的信息，用于进行本地文件写入。
   * @author liunan 2008-08-01
   */
  public String getExportBOMClassfiyString2(HashMap map) throws QMException;

  /**
   * 将物料清单输出成本地文件，用于统计报表的输出。
   * 在com.faw_qm.part.client.other.controller.MaterialController调用了此方法。
   * @param map HashMap 整理好的报表属性集合。
   * @throws QMException
   * @return String 按规则整理出全部报表的信息，用于进行本地文件写入。
   * @author liunan 2008-08-01
   */
  public String getExportBOMStatisticsString(HashMap map) throws QMException;
  
  //CCBeginby leixiao 2008-7-31 原因：解放升级工艺路线,增加"计算数量"  
  /*
   *quxg 添加 ,获得在某个结构下其下的所有子部件的数量,键为子part baoid,值为part数量
   *当某个子任务重复出现多次时,数量叠加. 此方法适用需要计算多个子部件数量,上面的计算数量方法
   *是计算一个子part,计算一个子part需要将所给结构都展开一遍,这样当计算多个子part时,就展开多次
   *此方法将所给结构一次展开,将对应的part和数量缓存到hashmap中,这样计算多个子部件数量时,只从
   *map中取就行了,效率会提高很多
   */
  public HashMap getSonPartsQuantityMap(QMPartIfc parentPartIfc) throws
      QMException;
//CCEndby leixiao 2008-7-31 原因：解放升级工艺路线,增加"计算数量"  

//CCBegin by liunan 2008-10-4 打补丁200816
    //问题(1)20080811 zhangq begin 修改原因：在产看零部件的被用于产品时，显示不正确。（TD-1794）
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
    public Vector getUsedProductStruct(QMPartIfc partIfc,String productBsoId, 
    		PartConfigSpecIfc configSpecIfc) throws QMException;
    //问题(1)20080811 zhangq end
    //CCEnd by liunan 2008-10-4
    //add whj
    /**
     * 获取当前用户的“显示设置”信息
     * @return “显示设置”值对象
     */
    public PartAttrSetIfc getCurUserInfo()
    throws QMException;
    /**
	 * 获取指定对象的EPM文档
	 * @param baseIfc
	 * @return EPM文档值对象集合
	 * @throws QMException
	 *@see Vector
	 */
    public Vector getEMPDocument(BaseValueIfc baseIfc)
    throws QMException;
    
	//CCBegin by leix	 2010-12-20  增加逻辑总成数量报表 
    public String getExportBOMClassfiyLogicString(HashMap map) throws QMException ;
	//CCEnd by leix	 2010-12-20  增加逻辑总成数量报表
    //CCBegin SS1
    /**
     * 分级物料清单的显示。
     * 返回结果是vector,其中vector中的每个元素都是一个集合：
     * 0：当前part的BsoID；
     * 1：当前part所在的级别，转化为字符型；
     * 2：当前part的编号；
     * 3：当前part的名称；
     * 4：当前part被最顶层部件使用的数量，转化为字符型；
     * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
     *                如果定制了属性：按照所有定制的属性加到结果集合中。
     * 本方法调用了递归方法：
     * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
     * PartUsageLinkIfc partLinkIfc, int parentQuantity);
     * @param partIfc :QMPartIfc 最顶级的部件值对象。
     * @param attrNames :String[] 定制的属性，可以为空。
     * @param affixAttrNames : String[] 定制的扩展属性名集合，可以为空。
     * @param configSpecIfc :PartConfigSpecIfc 配置规范。
     * @throws QMException
     * @return Vector
     */
    public Vector setMaterialListERP(QMPartIfc partIfc, String[] attrNames,
            String[] affixAttrNames, PartConfigSpecIfc configSpecIfc)
            throws QMException;
    //CCEnd SS1
    

	//CCBegin SS2
	/**
	 * 通过masterid获取最新版本的零部件。
	 * @param masterID masterid
	 * @return QMPartInfo 最新版本的零部件(QMPart);
	 * @throws QMException
	 * @see QMPartInfo
	 */
	public QMPartInfo getPartByMasterID(String masterID) throws QMException;
	//CCEnd SS2  
//  CCBegin SS3   
    public boolean isSubNode(QMPartIfc part,QMPartIfc subpart);
    
    public QMPartIfc findSubPartMaster(String partNumber) throws QMException;
//    CCEnd SS3
  
  //CCBegin SS4
  public String getExportFirstLeveList(HashMap map) throws QMException ;
  //CCEnd SS4
  
  public Collection sort(Collection collection);
  
  //CCBegin SS5
  public ArrayList setBOMList(QMPartIfc partIfc, String[] attrNames,
          String[] affixAttrNames, String[] routeNames,
          String source, String type,
          PartConfigSpecIfc configSpecIfc,
          String routeDepartment) throws QMException;
  
  //CCEnd SS5
  
  
}
