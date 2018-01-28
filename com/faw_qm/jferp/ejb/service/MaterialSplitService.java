/**
 * 生成程序MaterialSplitService.java	1.0              2007-10-7
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.ejb.service;

import java.util.Collection;
import java.util.Vector;
import java.util.HashMap;
import java.util.List;
import com.faw_qm.jferp.exception.QMXMLException;
import com.faw_qm.jferp.model.MaterialSplitIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;

/**
 * <p>Title: 物料拆分服务接口。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 * SS1 添加BOM调整变更采用单过滤条件 刘家坤 2013-06-12
 * SS2 修改发布时写XML数据错误的问题 侯焊锋 2014-02-19
 */
public interface MaterialSplitService extends BaseService
{
	
	/**
	 * 拆分物料
	 * @param coll 零部件的集合
	 * @param baselineiD 零部件存放的基线
	 * @param lx 发布类型，“1”工艺bom发布，“2”路线发布
	 * @param routeID 发布的路线
	 * @throws QMException
	 */
	 public void publishPartsToERPlc(Collection coll,String routeID)
        throws Exception;
	 /**
		 * 发布零部件
		 * @param coll 零部件的集合
		 * @param baselineiD 零部件存放的基线
		 * @param lx 发布类型，“1”工艺bom发布，“2”路线发布
		 * @param routeID 发布的路线
		 * @throws QMException
		 */
		 public void publishPartsToERP(Collection coll ,String routeID,HashMap scbbMap,Collection vec);
	 /**
     * 拆分物料，青汽ERP集成专用
     * @param coll 零部件的集合
     * @param baselineiD 零部件存放的基线
     * @param isPublishRoute 是否由路线发布
     * @param routeID 发布的路线
     * @throws QMException
     */

	public Vector split(Collection coll, String lx, String routeID)throws QMException;
/*
     * 拆分物料。
     * @param partBsoIDs 以“;”为分隔符的零部件BsoID集合。
     * @param routes 需要拆分的路线。
     * @param doSplit 为处理“若零件旧版本已拆分为物料，本次拆分是否重新拆分”的情况准备的boolean变量。true：重新拆分；false：不重新拆分。
     * @throws QMException
     */
//    public void split(String partBsoIDs, String routes, boolean doSplit)
//            throws QMException;
    //20080103 end

//    /**
//     * 根据零件号获取物料。为瘦客户显示使用。
//     * @param partNumberList 零件号集合。
//     * @return 物料Map。键：一组物料共有信息数组；值：物料信息数组集合。
//     * @throws QMException
//     */
//    public HashMap getAllMaterial(List partNumberList) throws QMException;

    /**
     * 根据零件号集合的字符串形式创建临时物料记录。
     * @param partNumberString 零件号集合的字符串形式。p01;p02
     * @throws QMException
     */
    public void createInterimMaterial(String partNumberString)
            throws QMException;

    /**
     * 根据零件号获取物料。为瘦客户显示使用。
     * @param partNumberList 零件号集合。
     * @return 物料Map。键：一组物料共有信息数组；值：物料信息数组集合。
     * @throws QMException
     */
    public HashMap getAllInterimMaterial(String partNumber) throws QMException;

    /**
     * 只删结构关系。将更新标识设置为“D”。
     * @param parentPartNumber 父件号。
     * @param parentNumber 父物料号。
     * @param childBsoID 被删除的临时表记录的bsoID。
     * @throws QMException 
     */
    public void delete(String parentPartNumber, String parentNumber,
            String childBsoID) throws QMException;

    /**
     * 只改结构关系中的子物料号。层级、层级状态都不变。需要将替换的新物料的信息添加到临时表中。
     * 需要父件号、父物料号，临时表子物料bsoID，替换物料的bsoID。
     * xieb同级有多个相同子件时如何办？？？？？？
     * @param parentPartNumber 父件号。
     * @param parentNumber 父物料号。
     * @param childBsoID 临时表子物料bsoID。
     * @param replaceBsoID 替换物料的bsoID。
     * @throws QMException 
     */
    public void replace(String parentPartNumber, String parentNumber,
            String childBsoID, String replaceBsoID) throws QMException;

    /**
     * 正式执行更新。需要校验物料号的唯一性。
     * @param updateMap 键：临时表bsoID。值：修改的物料号。
     * @throws QMException
     */
    public void updateMaterial(HashMap updateMap) throws QMException;

    /**
     * 删除所有临时表中的数据。
     * @throws QMException
     */
    public void deleteAllInterimData() throws QMException;

    /**
     * 根据零件号获取物料。
     * @param partNumber 零件号。 
     * @return 物料集合。
     * @throws QMException
     */
    public List getAllInterimMSplit(String partNumber) throws QMException;

    /**
     * 根据物料号获取物料。
     * @param materialNumber 物料号。
     * @return 物料。
     * @throws QMException
     */
    public MaterialSplitIfc getMSplit(String materialNumber) throws QMException;

    /**
     * 根据零件号获取顶级物料。
     * @param partNumber 零件号。
     * @return 顶级物料集合。
     * @throws QMException
     */
    public List getRootMSplit(String partNumber,String partVersion) throws QMException;

    /**
     * 根据指定的父件号和父物料号条件，找到结构表中所有符合条件的结构信息。
     * @param parentPartNumber 父件号。
     * @param parentNumber 父物料号。
     * @return 结构信息。
     * @throws QMException
     */
    public List getMStructure(String parentPartNumber, String parentNumber)
            throws QMException;

    /**
     * 根据零部件标号和部门代码、是否包括回头件标志获取对应的物料。
     * 
     * @param processRC：工艺规程的部门代码
     * @param partIfc：零部件值对象
     * @param stepNumberList：工序的编号
     * @param stepRCList：工序的与工艺规程部门代码不同的部门代码
     * @return List：注意，最后一个元素是该零部件对应的物料是否包含回头件的标志，类型为Boolean
     * @throws QMException
     */
//    public List getMaterialByPro(String processRC, QMPartIfc partIfc,
//            List stepNumberList, List stepRouteCodeList) throws QMException;

//    /**
//     * 根据工序符合条件的物料。
//     * @param partNumber：零部件编号
//     * @param processRC：工艺规程的部门代码
//     * @return List：第一个元素是HashMap，key是零部件的编号，value是零部件对应的物料
//     *               第二个元素是该零部件对应的物料是否包含回头件的标志，类型为Boolean
//     * @throws QMException
//     */
//    public List getMaterialByStep(String partNumber, String processRC)
//            throws QMException;

    /**
     * 发布工艺数据。
     * @param parts
     * @throws QMException
     */
    public void publicTechnics(String parts) throws Exception;

    /**
     * 根据给定的事物特性持有者,获得特定iba属性值。
     * return String 为该iba属性的值。
     * @throws QMXMLException 
     */
    public String getPartIBA(QMPartIfc partIfc, String ibaDisplayName)
            throws QMXMLException;
    

    //20080103 begin
    /**
     * 根据瘦客户端传递的以“;”作为分隔符的零部件BsoID字符串获取所有的零部件集合。
     * @param partBsoIDs 零部件BsoID字符串。
     * @return 零部件集合。
     * @throws QMException 
     */
    public List getAllPartByBsoID(String partBsoIDs) throws QMException;

    /**
     * 获取路线属性定义。
     * @return 路线属性定义的轻量级对象的集合。
     * @throws QMException 
     */
    public List getRouteDefList() throws QMException;
    //20080103 end
    
    //20080110 begin
//    /**
//     * 根据零部件标号和部门代码、工艺的种类获取对应的物料。
//     * @param partNumber：零部件编号
//     * @param stepRC：工序的部门代码
//     * @param processType：工艺的种类
//     * @return List：注意，最后一个元素是该零部件对应的物料是否包含回头件的标志，类型为Boolean
//     * @throws QMException
//     */
//    public List getMaterialByStep(String partNumber, String stepRC,String processType)
//            throws QMException;
    //20080110 end
    //20080123 begin
    /**
	 * 将部门代码从汉字转换为简称。
	 * @param tempRouteCodes：路线，以dashDelimiter作为分隔符。
	 */
	public String changeRoute(String routeCodes) throws QMException;
	//20080123 end
	
	//20081205 zhangq begin
	/**
	 * 根据零部件的编号和路线创建采用通知书，然后利用这个采用通知书拆分物料，创建集成包并发布。
	 * @param partNumber
	 * @param route
	 * @throws QMException
	 */
	//public void publishMaterial(String partNumber,String route)throws QMException;
	
    /**
     * 获取默认的路线属性定义。
     * @return 路线属性定义的轻量级对象的集合。
     * @throws QMException 
     */
    public List getDefaultRouteDefList() throws QMException;
	//20081205 zhangq end
    
    /**
     * 将路线中的路线代码转化为List,并且根据属性文件的配置去掉路线中的特殊代码。
     * @param routeStr
     * @return
     */
    public List getRouteCodeList(String routeStr);
    
    public Collection getPartByRouteList(TechnicsRouteListIfc list)
        throws QMException;
    
    /**
	 * 发布虚拟件
	 * @param coll 零部件的集合
	 * @param baselineiD 零部件存放的基线
	 * @param lx 发布类型，“1”工艺bom发布，“2”路线发布
	 * @param routeID 发布的路线
	 * @throws QMException
	 */
	 public void publishvirtualPartsToERP(Collection coll )
        throws Exception;
    
}
