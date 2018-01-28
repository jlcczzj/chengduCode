/** 生成程序 EnterprisePartService.java    1.0    2003/02/28
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 成都结构搜索工艺规程 guoxiaoliang 
 */
package com.faw_qm.part.ejb.enterpriseService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import com.faw_qm.eff.model.EffConfigurationItemIfc;
import com.faw_qm.eff.model.EffManagedVersionIfc;
import com.faw_qm.eff.util.EffGroup;
import com.faw_qm.effectivity.model.QMConfigurationItemIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.wip.model.WorkableIfc;


/**
 * 企业级服务，包括产品结构比较、有效性管理等功能的方法。
 * @author 吴先超
 * @version 1.0
 */
public interface EnterprisePartService extends BaseService
{
    /**
     * 根据指定的源部件和目标部件及各自的筛选条件，返回两个部件使用关系的不同。
     * 本方法调用了递归方法：difference(QMPartIfc sourcePartIfc, PartConfigSpecIfc sourceConfigSpecIfc,
     * QMPartIfc objectPartIfc, PartConfigSpecIfc objectConfigSpecIfc)。
     * 返回值Vector中元素的数据结构String[]：
     * 0：当前差异部件相对于根部件的层次，这里，根部件为0层，根部件的子部件为1层，以此类推；
     * 1：当前差异部件的名称+"("+编号+")" ；
     *
     * 2：当前差异部件在源部件中的版本+（视图），如果源部件中没有该部件的话，显示""，
     *    如果源部件中有该差异部件但是没有符合配置规范的版本的话，
     *    显示"没有符合配置规范的版本"；
     * 3：当前差异部件被其上一级零部件所使用的数量+(单位)，如果源部件中没有该部件的话，显示""
     *    如果源部件中有该差异部件但是没有符合配置规范的版本的话，照常显示数量+（单位）；
     *
     * 4：当前差异部件在目标部件中的版本+（视图），如果目标部件中没有该部件的话，显示""，
     *    如果目标部件中有该差异部件但是没有符合配置规范的版本的话，
     *    显示"没有符合配置规范的版本"；
     * 5：当前差异部件被其上一级零部件所使用的数量+(单位)，如果目标部件中没有该部件的话，显示""
     *    如果目标部件中有该差异部件但是没有符合配置规范的版本的话，照常显示数量+（单位）；
     *
     * 6：显示的颜色的标识::如果差异部件在源部件和目标部件中都存在，但数量或者版本不同的话，显示为黑色(black)；
     *    如果差异部件在源部件中存在，但是在目标部件中不存在的话，显示为绿色(green)；
     *    如果差异部件在目标部件中存在，但是在源部件中不存在的话，显示为紫色(purple)；
     *    如果差异部件在源部件和在目标部件中的版本和数量都相同的话，显示为灰色(gray)；
     *    信息"没有符合配置规范的版本"的颜色为红色(red),含有该信息的行的颜色为黑色(black)。

     * 7:差异零部件的BsoID，是针对源零部件而言的，即是源零部件使用的该差异零部件的BsoID；
     *
     * 8:差异零部件的BsoID, 是针对目标零部件而言的，即是目标零部件使用的的差异零部件的BsoID；

     * @param sourcePartIfc :QMPartIfc源部件的值对象。
     * @param objectPartIfc :QMPartIfc目标部件的值对象。
     * @param sourceConfigSpecIfc :PartConfigSpecIfc源部件的筛选条件。
     * @param objectConfigSpecIfc :PartConfigSpecIfc目标部件的筛选条件。
     * @return vector:Vector
     * @throws QMException
     */
    public Vector getBOMDifferences(QMPartIfc sourcePartIfc,
                                    PartConfigSpecIfc sourceConfigSpecIfc,
                                    QMPartIfc objectPartIfc,
                                    PartConfigSpecIfc objectConfigSpecIfc,HashMap map)
            throws QMException;


    /**
     * 将参数collection中的元素上溯造型为PartUsageLinkIfc对象以rightBsoID(零部件的主信息的bsoID)为键字保存在哈西表中并返回哈西表。
     * @param collection :CollectionPartUsageLinkIfc对象集合。
     * @return hashtable:Hashtable哈西表。
     */
    public Hashtable consolidateUsageLink(Collection collection);


    /**
     * 获得part关联的所有PartUsageLinkIfc对象的集合。
     * @param partIfc :QMPartIfc对象。
     * @return collection:Colllection与part关联的所有PartUsageLinkifc对象集合。
     * @throws QMException
     */
    public Collection getConsolidatedBOM(QMPartIfc partIfc)
            throws QMException;


    /**
     * 新建和更新有效性方案。
     * @param effConfigItemIfc EffConfigurationItemIfc 有效性方案。
     * @throws QMException
     * @return EffConfigurationItemIfc 有效性方案。
     */
    public EffConfigurationItemIfc saveConfigItemIfc(EffConfigurationItemIfc
            effConfigItemIfc)
            throws QMException;


    /**
     * 删除有效性方案。
     * @param configItemIfc EffConfigurationItemIfc 有效性方案。
     * @throws QMException
     */
    public void deleteConfigItemIfc(EffConfigurationItemIfc configItemIfc)
            throws QMException;


    /**
     * 创建一个EffGroup对象。
     * @param effectivityType 有效性类型。
     * @param value_range 值范围。
     * @param configItemIfc QMConfigurationItemIfc 配置规范。
     * @param partIfc EffManagedVersionIfc 零部件。
     * @throws QMException
     * @return EffGroup
     */
    public EffGroup createEffGroup(String effectivityType, String value_range,
                                   QMConfigurationItemIfc configItemIfc,
                                   EffManagedVersionIfc partIfc)
            throws QMException;


    /**
     * 获得在当前筛选条件下partMasterIfc的结构下所有子件。
     * @param partMasterIfc 零部件主信息值对象。
     * @param configSpecIfc 配置规范。
     * @return QMPartInfo[] 所有子部件值对象的集合。
     * @exception QMException
     */
    public QMPartIfc[] getAllSubPartsByConfigSpec(QMPartMasterIfc partMasterIfc,
                                                  PartConfigSpecIfc
                                                  configSpecIfc)
            throws QMException;


    /**
     * 获得partIfc已经存在的EffGroup对象的集合。
     * @param partIfc QMPartIfc 零部件。
     * @throws QMException
     * @return Vector
     */
    public Vector outputExistingEffGroups(QMPartIfc partIfc)
            throws QMException;


    /**
     * 获得在当前筛选条件下partMasterIfc的结构下所有子件的有效性范围数据类对象EffGroup的
     * 集合。该方法用于显示按结构添加有效性结果。
     * @param partMasterIfc :零部件主信息值对象。
     * @param configSpecIfc :PartConfigSpecIfc 配置规范值对象。
     * @param configItemID 有效性配置项bsoID。
     * @param value_range :String 有效性值范围。
     * @return Vector
     * @throws QMException
     */
    public Vector getEffGroups(QMPartMasterIfc partMasterIfc,
                               PartConfigSpecIfc configSpecIfc,
                               String configItemID, String value_range)
            throws QMException;


    /**
     * 根据零部件值对象获取该零部件的所有有效性值。
     * @param partInfo : QMPartInfo 零部件值对象。
     * @return Vector EffGroup的集合。
     * @throws QMException
     */
    public Vector getEffVector(WorkableIfc partInfo)
            throws QMException;


    /**
     * 根据零部件值对象获取该零部件的所有有效性值。
     * @param partInfo : QMPartInfo 零部件值对象。
     * @param isacl 是否访问控制。
     * @return Vector EffGroup的集合。
     * @throws QMException
     */
    public Vector getEffVector(WorkableIfc partInfo, boolean isacl)
            throws QMException;


    /**
     * 删除part对应的有效性值
     *
     * @param partBsoID 零件的bsoID。
     * @param configItemName 有效性方案名称。
     * @param effectivityType 有效性类型:"DATE","LOT_NUM","SERIAL_NUM"。
     * @throws QMException
     */
    public void deleteAllEffs(String partBsoID, String configItemName,
                              String effectivityType)
            throws QMException;


    /**
     * 创建part对应的有效性值。
     *
     * @param partIfc 零件。
     * @param configItemName 有效性方案名称。
     * @param effectivityType 有效性类型不能为空必须是:"DATE","LOT_NUM","SERIAL_NUM"之一。
     * @param effValue 有效性值。
     * @throws QMException
     */
    public void createEff(QMPartIfc partIfc, String configItemName,
                          String effectivityType, String effValue)
            throws QMException;


    /**
     * 更新part对应的有效性值。
     *
     * @param partIfc QMPartIfc 零件。
     * @param configItemName String 有效性方案名称。
     * @param oldEffectivityType String
     *   有效性类型不能为空必须是:"DATE","LOT_NUM","SERIAL_NUM"之一。
     * @param newEffValue String 新的有效性值范围。
     * @throws QMException
     */
    public void updateEff(QMPartIfc partIfc, String configItemName,
                          String oldEffectivityType, String newEffValue)
            throws QMException;

    public Vector getEffVector(WorkableIfc partIfc,String itemName) throws QMException;
    
  //CCBegin by leixiao 2011-1-12 原因:解放路线新需求 
    public Vector getAllSubPartsByConfigSpec(QMPartIfc partIfc,
                                                  PartConfigSpecIfc
                                                  configSpecIfc)
            throws QMException;    
  //CCBegin by leixiao 2011-1-12 原因:解放路线新需求 
    
    //CCBegin SS1
    /**
     * 打印中心结构汇总，返回集合没有重复的件
     * 获得在当前筛选条件下partMasterIfc的结构下所有子件。
     * @param partMasterIfc 零部件主信息值对象。
     * @param configSpecIfc 配置规范。
     * @return Vector 所有子部件值对象的集合。
     * @exception QMException
     */
    public Vector getAllSubPartsByConfigSpecForQQ(QMPartMasterIfc partMasterIfc,
        PartConfigSpecIfc configSpecIfc) throws QMException;
    
    //CCEnd SS1
}
