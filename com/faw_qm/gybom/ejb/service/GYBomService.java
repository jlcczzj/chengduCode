/**
 * 生成程序时间 2016-5-16 版本 1.0 作者 刘楠 版权归一汽启明公司所有 本程序属一汽启明公司的私有机要资料 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序 保留所有权利
 * SS1 保存变更记录 liuyuzhu 2017-04-27
 * SS2 修改初始化工艺BOM 刘涛 2017-5-3
 * SS3 保存单独替换件的工艺BOM liuyuzhu 2017-05-19
 * SS4 开放获取当前用户工厂方法。 liunan 2017-5-24
 * SS5 初始化bom之后，部分逻辑总成是成都创建的，但是在解放工艺bom里，也借用了成都的逻辑总成结构。此规则需要去掉。 (服务平台A004-2017-3578)2017-07-03
 * SS6 查询关联的书签 liuyuzhu 2017-07-20
 * SS7 导出BOM方法 liuyuzhu 2017-07-25
 * SS8 修改结构比较方法 liuyuzhu 2017-08-22
 * SS9 界面调用判断是否为生效结构的方法 liuyuzhu 2017-09-26 
 * SS10 替换件方法参数变化 liuyuzhu 2017-11-02
 * SS11 所有对BOM的修改或调整都改变节点颜色 liuyuzhu 2017-11-08
 * SS12 加锁零部件 liuyuzhu 2017-11-13
 * SS13 工艺BOM客户端导入功能 刘家坤 2017-12-8
 * SS14 校验工艺bom是否编辑路线并发布  刘家坤 2017-12-22
 */
package com.faw_qm.gybom.ejb.service;

import java.util.Collection;
import java.util.Vector;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.users.model.UserInfo;

/**
 * 标准版文档服务的EJBLocal接口。
 * 
 * @author 刘楠 修改时间 2016-5-16
 */
public interface GYBomService extends BaseService
{
	/**
	 * 创建设计BOM关联
	 * 
	 * @param Collection
	 *            mtreenode 目标树（工艺BOM）节点集合 保存时要获取当前用户所在单位，然后直接通过数据库创建。
	 */
	public String saveBom(String mtreenode);

	/**
	 * 拖拽参照BOM树上的总成及零部件到工艺BOM树上等操作； 左右拖拽，参照bom树拖拽到目标bom树之后需要变图标处理
	 * 左右拖拽：参照BOM树下的零部件及结构调整到工艺BOM树下，连带bom都需要拖拽到工艺bom树节点之下（拖拽之后需要进行局部刷新并保存）
	 * 上下拖拽：工艺BOM树自身结构的调整，可以拖拽进行自身结构的调整，不需要改变图标 上下拖拽：目标树自身拖拽，再拖拽之后进行提交保存。
	 * 左右拖拽：addArr有值；上下拖拽：updateArr有值；移除：deleteArr有值；部分移动：addArr和updateArr有值
	 * 
	 * @param String
	 *            [] addArr 新增结构数组 父件id,子件id;子件id1;子件id2,数量;数量1;数量2,单位;单位1;单位2
	 * @param String
	 *            [] updateArr 更新结构数组
	 *            linkid;父件id;数量,linkid1;父件id1;数量1,linkid2;父件id2;数量2
	 * @param String
	 *            [] deleteArr 删除结构数组 linkid,linkid1,linkid2
	 * @param String
	 *            carModelCode 车型码
	 * @param String
	 *            dwbs 工厂 返回新增件json
	 */
	public String saveTreeNode(String addstr,String updatestr,String deletestr,String carModelCode,String dwbs,
		String left);

	/**
	 * 删除工艺BOM关联
	 * 
	 * @param Collection
	 *            mtreenode 目标树（工艺BOM）关联集合
	 *            每个元素是一个6维数组，分别是父件bsoID、子件bsoID、数量、车型代码、单位、生效标识。 删除符合条件数据。
	 */
	public void deleteGYBom(Collection mtreenode);

	/**
	 * 创建关联
	 * 
	 * @param Collection
	 *            ytreenode 源树（设计BOM）关联集合
	 * @param Collection
	 *            mtreenode 目标树（工艺BOM）关联集合
	 * @param Collection
	 *            links 关联集合，每个元素是一个三维数组，分别是父件bsoID、子件bsoID、数量。
	 *            保存时要获取当前用户所在单位，然后直接通过数据库创建。
	 */
	public void saveGYBom(Collection mtreenode, boolean checkexitflag, String dwbs);//SS5

	/**
	 * 创建工艺BOM关联
	 * 
	 * @param String
	 *            [] mt parentPart,childPart,quantity,carModelCode,dwbs,
	 *            effectCurrent,locker,lockDate,bz1
	 * @return String 关联id
	 */
	public String saveGYBom(String[] mt);

	/**
	 * 更新工艺BOM关联
	 * 
	 * @param Collection
	 *            mtreenode 关联集合，每个元素是一个三维数组，分别是linkid、父件bsoID、数量。
	 */
	//CCBegin SS11
//	public void updateGYBom(Collection mtreenode) throws QMException;
	public void updateGYBom(Collection mtreenode,String dwbs,String type) throws QMException;
	//CCEnd SS70

	/**
	 * 生成初始工艺BOM
	 */
	public void initGYBom(String partID,String gyID,String dwbs) throws QMException;

	/**
	 * 返回指定零部件及其一级子件节点集合 用于添加零部件设计bom到源树，根据flag决定从临时表中获取还是获取最新结构。
	 * 
	 * @param id
	 *            零部件bsoID
	 * @param flag
	 *            是否获取设计标签树
	 * @param carModelCode
	 *            车型码
	 * @param dwbs
	 *            单位
	 * @return 零部件及其一级子件的树结构
	 */
	public String getDesignBom(String id,String flag,String carModelCode,String dwbs) throws QMException;

	/**
	 * 返回指定零部件的一级子件节点集合，用于显示设计BOM零部件列表。
	 * 
	 * @param id
	 *            零部件bsoID
	 * @param flag
	 *            是否获取设计标签树
	 * @param carModelCode
	 *            车型码
	 * @param dwbs
	 *            单位
	 * @return 零部件及其一级子件的树结构
	 */
	public String getDesignBomList(String id,String flag,String carModelCode,String dwbs) throws QMException;

	/**
	 * 返回指定零部件及其一级子件节点集合 用于添加工艺bom到目标树。
	 * 
	 * @param id
	 *            零部件bsoID
	 * @param carModelCode
	 *            车型码
	 * @param dwbs
	 *            单位
	 * @return 零部件及其一级子件的树结构
	 */
	public String getGYBom(String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * 返回指定零部件的一级子件节点集合，用于显示工艺BOM零部件列表。
	 * 
	 * @param id
	 *            零部件bsoID
	 * @param carModelCode
	 *            车型码
	 * @param dwbs
	 *            单位
	 * @return 零部件及其一级子件的树结构
	 */
	public String getGYBomList(String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * 创建零件
	 */
	public String createPart(String s) throws QMException;

	/**
	 * 设置工艺bom生效
	 */
	public void setValidBom(String id) throws QMException;

	/**
	 * 用例（搜索设计bom） 根据编号和名称搜索零部件
	 * 
	 * @param name
	 *            零部件名称
	 * @param number1
	 *            零部件编号
	 * @param ignoreCase
	 *            忽略大小写
	 * @return 符合条件的零部件集合
	 * @exception com.faw_qm.framework.exceptions.QMException
	 */
	public String findPart(String number1,String name,String ignoreCase) throws QMException;

	/**
	 * 结构比较
	 */
	public String CompartTreeResult(String desginID,String gyID,String carModelCode,String dwbs) throws QMException;

	/**
	 * 加锁
	 */
	public String addLock(String carModelCode,String dwbs) throws QMException;

	/**
	 * 解锁
	 */
	public String unLock() throws QMException;

	/**
	 * 检查是否被锁
	 */
	public String checkLock(String carModelCode,String dwbs) throws QMException;

	/**
	 * 获得当前用户
	 * 
	 * @throws QMException
	 */
	public UserInfo getCurrentUserInfo() throws QMException;

	/**
	 * 另存为工艺BOM。 String yid 选中零部件。 String mid 目标零部件。 String dwbs 选中零部件工厂
	 */
	public String saveAsGYBom(String yid,String mid,String ydwbs) throws QMException;

	/**
	 * 设置变更，为一个生效的工艺BOM克隆一套未生效BOM。
	 */
	public String changeGYBom(String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * 保存批量替换车型表。 id 典型车型bsoid,车型码,工厂,替换车型1,替换车型2,替换车型3...
	 */
	public String saveBatchUpdateCM(String id) throws QMException;

	/**
	 * 保存批量全部替换车型表。 id 车型id
	 */
	public String saveAllBatchUpdateCM(String id) throws QMException;

	/**
	 * 获取批量替换车型表。
	 */
	public String getBatchUpdateCM(String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * 降版本 将工艺bom上节点版本降低一个版本，例如原来版本是“D”，选择降版本之后变为“C”版本
	 */
	public String upperVersion(String linkid,String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * 拆分 点击需要拆分的逻辑总成，在同级下自动生成一个逻辑总成号 编号规则：原逻辑总成号+F+3位流水。
	 * 
	 * @parm String linkid 关联id（linkid）
	 * @parm String carModelCode 车型码
	 * @parm String dwbs 工厂
	 */
	public String chaiFenPart(String linkid) throws QMException;

	public Vector getExportFirstLeveList(String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * 添加备品 将选中零部件子件添加到指定零部件下。 返回
	 * 
	 * @parm String parentID 指定零部件
	 * @parm String beipinID 选中零部件
	 * @parm String carModelCode 车型码
	 * @parm String dwbs 工厂
	 */
	public String addBeiPin(String parentID,String beipinID,String carModelCode,String dwbs) throws QMException;

	/**
	 * 工艺bom历史数据导入方法 AddUsageLink C01AM44141BF204 CQ1511065 ea 8 AddUsageLink
	 * C01AM44141BF204 Q1841240F6 ea 8 标记 父件 子件 单位 数量
	 */
	public String uploadBomExcel(String isupdate) throws QMException;

	/**
	 * 获取导入BOM列表清单
	 */
	public Vector getBomExcel() throws QMException;

	/**
	 * 批量修改工艺BOM，根据修改规则集合，把要修改的车型，进行调整
	 */
	public void multiChangeGYBom(QMPartIfc ypart) throws QMException;

	/**
	 * 删除总成 右键添加菜单“删除总成”，此菜单功能是将总成删除掉，并把子件层级上升一级。 返回
	 * 
	 * @parm String parentID 选中零部件的父件
	 * @parm String partID 选中零部件
	 * @parm String carModelCode 车型码
	 * @parm String dwbs 工厂
	 */
	public String deleteSeparable(String parentID,String partID,String carModelCode,String dwbs) throws QMException;

	/**
	 * 导出BOM 指定车型、工厂的BOM集合。
	 */
	public Vector getExportBomList(String carModelCode) throws QMException;

	/**
	 * 获取零部件父件在该工厂内的所有父件
	 */
	public Vector getParentFromDwbs(String id) throws QMException;

	/**
	 * 获取零部件父件在该工厂内的所有未生效的父件 获取子件id和当前用户工厂的生效bom中的父件。 返回集合 零部件bsoid 编号 名称 数量
	 */
	public Vector getParentPart(String id) throws QMException;

	// SS2 begin 
	public void createGyBomCFHistory(String sourceID,String cfID,String parentID,String type) throws QMException;

	public Collection getGyBomCFHistoryBySourceID(String sourceID,String type) throws QMException;

	public void createGyBomReNameHistory(String sourceID,String cfID) throws QMException;

	public Collection getGyBomReNameHistoryBySourceID(String sourceID) throws QMException;
	
	public String upgradeVersion(String linkid,String id,String carModelCode,String dwbs) throws QMException;

	/**
	 * 生成整车初始工艺BOM
	 */
	public void initGYZCBom(String partID,String gyID,String dwbs) throws QMException;

	/**
	 * 生成车架初始工艺BOM
	 */
	public void initGYCJBom(String partID,String gyID,String dwbs) throws QMException;

	/**
	 * 生成驾驶室初始工艺BOM
	 */
	public void initGYJSSBom(String partID,String gyID,String dwbs,String newnumber) throws QMException;
	// SS2 end

  //CCBegin SS1
  /**保存变更记录
	 * @param pid
	 * @param sid
	 * @return
	 */
	public String saveChangeContent(String cxh, String gc,
			String parentid, String subid,String tsubid, String quantityb, String quantitya,
			String sign) throws QMException;
	//CCEnd SS1
	
	//CCBegin SS3
	/**替换件
	 * @param linkid linkid
	 * @param yid 原节点id
	 * @param xid 替换节点id
	 * @param carModelCode 车型
	 * @param dwbs 工厂
	 * @return
	 * @throws QMException
	 */
	//CCBegin SS10
//	public String changePart(String linkid,String yid,String xid,String carModelCode,String dwbs) throws QMException;
	public String changePart(String linkid,String yid,String xid,String carModelCode,String dwbs,String parentid,String changeType) throws QMException;
	//CCEnd SS10
	//CCEnd SS3
	
	//CCBegin SS4
	/**
	 * 获取当前用户所在的工厂。
	 * @return 工厂
	 * @throws QMException
	 */
	public String getCurrentDWBS() throws QMException;
	//CCEnd SS4
	//CCBegin SS6
	/**查询关联书签
	 * @param carModelCode 车型码
	 * @return 
	 */
	public String searchLinkBook(String carModelCode) throws QMException;
	//CCEnd SS6
	//CCBegin SS7
	/**导出BOM
	 * @param id
	 * @param carModelCode
	 * @param dwbs
	 * @return
	 * @throws QMException
	 */
	public Vector getExportBOMList(String id,String carModelCode,String dwbs) throws QMException;
	//CCEnd SS7
	//CCBegin SS8
	/**
	 * 结构比较
	 */
	public Vector CompartTreeResult1(String desginID,String gyID,String carModelCode,String dwbs,String isRelease) throws QMException;
	//CCEnd SS8
	//CCBegin SS9
	/**
	 * 根据车型码和工厂，判断是否生效 返回0 没有未生效结构 没有生效结构 返回1 有未生效结构 没有生效结构 返回2 没有未生效结构 有生效结构
	 * 返回3 有未生效结构 有生效结构
	 */
	public String getEffect(String parentPart,String carModelCode,String dwbs) throws QMException;
	//CCEnd SS9
	//CCBegin SS10
	/**
     * @param linkid linkid
     * @param yid 原节点id
     * @param xid 替换节点id
     * @param carModelCode 车型
     * @param dwbs 工厂
     * @param parentid 父件id
     * @param changeType 
     * @return
     * @throws QMException
     */
	public String changePartOther(String linkid,String yid,String xid,String carModelCode,String dwbs,String parentid,String productid) throws QMException;
	//CCEnd SS10
	//CCBegin SS12
	/**加锁零部件
	 * @param partid 选中节点零件id
	 * @param dwbs 工厂
	 * @return
	 * @throws QMException
	 */
	public String lockPart(String partid,String dwbs) throws QMException;
	/**解锁零部件
	 * @param partid 选中节点零件id
	 * @param dwbs 工厂
	 * @return
	 * @throws QMException
	 */
	public String unLockPart(String partid,String dwbs) throws QMException;
	/**
	 * 查询加锁用户是否为当前用户
	 * 
	 * @return false 没有当前用户加锁的对象,true 有当前用户加锁的对象
	 * @throws QMException
	 */
	public String searchLockUser(String partid, String dwbs) throws QMException;
	//CCEnd SS12
//	CCBegin SS13
	 public String importGYBom(String csvData) throws QMException;
//	CCEnd SS13
	 /**
		 * 校验工艺bom是否编辑路线并发布
		 */
		public Vector checkBom(String carModelCode) throws QMException;
}