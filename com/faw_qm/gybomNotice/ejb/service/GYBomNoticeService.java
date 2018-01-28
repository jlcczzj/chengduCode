/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 TD8290 调整BOM添加零件问题  文柳 2014-7-17
 * SS2 Bom导入 xianglx 2014-9-10
 * SS3 工艺BOM管理器中添加解放设计变更单会带入相应单据的采用和不采用件 xianglx 2014-8-28
 * SS4 查看BOM版本与逻辑不合栏、没子组、不显示保存成功、应支持按单级/多级导出 xianglx 2014-9-28
 * SS5 自动生成BOM发布单 lishu 2017-5-12
 * SS6 检查零部件是否存在，并且是否有成都路线 徐德政 2017-12-30
*/

package com.faw_qm.gybomNotice.ejb.service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

import com.faw_qm.bomNotice.model.BomAdoptNoticeIfc;
import com.faw_qm.bomNotice.model.BomAdoptNoticeInfo;
import com.faw_qm.common.cipjava.booleandict;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;
import com.faw_qm.lock.model.LockIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;



/**
 * <p>Title: 发布单服务类。</p> <p>Description: </p> <p>Copyright: Copyright (c) 2014</p> <p>Company: 启明信息技术股份有限公司</p>
 * @author 文柳
 * @version 1.0
 */

public interface GYBomNoticeService extends BaseService
{


    /**
     * 创建发布单
     * @param Object[] data 数据集合
     * @return Object[]
     */
    public Object[] createGYBomAdoptNotice(Object[] data);

    /**
     * 批量新增零件
     * @param Object[] param
     * @throws QMException
     * @return Collection
     */
   public Collection findMultPartsByNumbers(Object[] param) throws QMException;
	 /**
    * 采用单采用件添加子件
    * @param QMPartInfo[] QMPartInfos
    * @throws QMException
    * @return Object[] 
    */
   public Object[] replaceBomAdoptNoticePart(QMPartInfo[] QMPartInfos,String selectAdoptPart,String[] usageCount) throws QMException;
	  /**
    * 创建子采用单
    * @param Object[] data 数据集合
    * @return Object[]
    */

	public Object[] createSubBomAdoptNotice(Object[] data) throws QMException;

    /**
     * 根据子采用单，获得子采用单
     * @return Collection 采用单集合
     * @param BomAdoptNoticeInfo info 采用单对象
     * @throws QMException
     */
    public Collection getSubGYBomAdoptNotice(GYBomAdoptNoticeInfo info)throws QMException;
    /**
     * 给采用单解锁
     * @param list
     * @param locker
     * @throws QMException
     */
    public void unlock(LockIfc list, String locker) throws QMException;
    /**
     * 给采用单加锁
     * @param list
     * @param locker
     * @throws QMException
     */
    public LockIfc lock(LockIfc list, String locker) throws QMException;
	 /**
     * 删除发布BOM
     * @param BomAdoptNoticeInfo list
     */
    public void deleteGYBomAdoptNotice(GYBomAdoptNoticeInfo notice) throws QMException;
    /**
     * 根据零件获得采用单关联（用于删除信号监听）
     * @return Collection 采用单关联零件集合
     * @param QMPartInfo info 零件对象
     * @throws QMException
     */
    public Collection getPartsFromBomSubAdoptNoticeLink(QMPartInfo info)throws QMException;
	
	  /**
     * 更新子采用单
     * @param Object[] data 数据集合
     * @param BomAdoptNoticeIfc ifc 更新的对象
     * @return Object[]
     */

	public Object[] updateSubBomAdoptNotice(Object[] data,BomAdoptNoticeIfc ifc) throws QMException;
    /**
     * 根据子采用单，获得采用单关联零件
     * @return Collection 采用单关联零件集合
     * @param String bsoID 采用单对象bsoID
     * @throws QMException
     */
    public Collection getPartsFromBomSubAdoptNotice(String bsoID)throws QMException;

	 /**
     * 根据采用单，查询使用零件情况（用于查看BOM）
     * @param BomAdoptNoticeIfc bomIfc
     * @return Collection
     */
 	public Collection getBomAdoptUsagePart(BomAdoptNoticeIfc bomIfc)throws QMException;
	 /**
	    * 解放查看发布BOM（解放查看发布BOM）
	    * @param String bsoID
	    * @return Collection
	    */
	public Collection getReleaseBom(QMPartIfc partIfc)throws QMException ;
	   /**
	    * 根据条件搜索采用单
	    * @param condition 输入检索条件
	    * @return Vector 所需零件
	    * @author wenliu 2014-6-12
	    */
	public Collection searchGYBomAdoptNotice(HashMap condition) throws QMException;
	   /**
	    * 根据条件搜索中心采用、变更单
	    * @param condition 输入检索条件
	    * @return Vector 所需零件
	    * @author wenliu 2014-6-4
	    */
	public Collection searchBomAdoptChangeNotice(HashMap condition) throws QMException;
	//CCBegin SS1
	   /**
	    * 根据零件集合，查询最新生效路线内容
	    * @param QMPartInfo[] parts 零件集合
        * @param QMPartIfc parentPart 顶级父件
	    * @return Vector 所需零件以及路线内容
	    * @author wenliu 2014-6-13
	    */
	public Collection findPartAndRoute(QMPartInfo[] parts,QMPartIfc parentPart) throws QMException;
	//CCEnd SS1
	  /**
     * 创建整车BOM零件列表关联
     * @param Object[] data 数据集合
     * @return Object[]
     */

	public Vector createGYBomZCPartLink(Vector linkVec,GYBomAdoptNoticeIfc ifc) throws QMException;
	   /**
	    * 根据发布单ID获得零件BOM
	    * @param QMPartIfc part
	    * @return QMPartIfc
	    * @throws QMException 
	    */
	public Collection getBomPartFromBomAdoptNotice(GYBomAdoptNoticeIfc ifc)throws QMException;
	 /**
     * 更新整车BOM零件列表关联
     * @param Object[] data 数据集合
     * @return Object[]
     */

	public Vector updateGYBomZCPartLink(Vector linkVec,GYBomAdoptNoticeIfc ifc) throws QMException;
	 /**
     * 更新整车发布单
     * @param Object[] data 数据集合
     * @param GYBomAdoptNoticeIfc ifc 发布单对象
     * @return Object[]
     */

	public Object[] updateGYBomAdoptNotice(Object[] data,GYBomAdoptNoticeIfc ifc) throws QMException;
	  /**
     * 创建车架、驾驶室发布单
     * @param Object[] data 数据集合
     * @return Object[]
     */

	public Object[] createFrameAndBodyBomAdoptNotice(Object[] data) throws QMException;
	 /**
     * 更新车架、驾驶室发布单
     * @param Object[] data 数据集合
     * @param GYBomAdoptNoticeIfc ifc 发布单对象
     * @return Object[]
     */

	public Object[] updateFrameAndBodyBomAdoptNotice(Object[] data,GYBomAdoptNoticeIfc ifc) throws QMException;
    /**
     * 废弃发布单
     * @param GYBomAdoptNoticeInfo notice
     */
    public void disuseNotice(GYBomAdoptNoticeInfo notice) throws QMException;
  /**
    * 根据发布单ID散发清单
    * @param String bsoID
    * @return Collection
    * @throws QMException 
    */
	public  Collection getInvoiceByNotice(String bsoID)throws QMException;
	/**
     * 保存散发清单
     * @param Object[] data 数据集合
     * @param GYBomAdoptNoticeIfc ifc 发布单对象
     * @return Object[]
     */

	public Vector saveInvoice(Vector linkVec,GYBomAdoptNoticeIfc ifc) throws QMException;
	/**
     * 导入虚拟件
     * @param String csvData CSV文件内容
     * @return Object[]
     */

	public Vector importVirtualPart(String csvData) throws QMException;
	
	/**
	 * 导出整车下所有工艺BOM
	 * @param ifc 整车发布单对象
	 * @return 导出是否成功
	 * @throws QMException
	 * @author houhf
	 */
	public Vector<Object[]> exportAllBOM(GYBomAdoptNoticeIfc ifc)throws QMException,IOException;
	/**
     * 导入专用件
     * @param String csvData CSV文件内容
     * @return Object[]
     */

	public Vector importSpecPart(String csvData) throws QMException;
	
//CCBegin SS2
	/**
     * 导入Bom
     * @param String csvData CSV文件内容
     * @return Object[]
     */

	public String importBom(String csvData) throws QMException;
	public String importBom1(String csvData) throws QMException;
	public String importGYBom(String csvData) throws QMException;
//CCEnd SS2
//CCBegin SS3
	/**
	根据变更单号或采用单号获得所有的相关采用零部件数组信息的集合
	*/
	public Vector getUseByID(BaseValueIfc ifc) throws QMException;
	/**
	根据变更单号或采用单号获得所有的相关不采用零部件数组信息的集合
	*/
	public Vector getNouseByID(BaseValueIfc ifc) throws QMException;

//CCEnd SS3
//CCBegin SS4
	public boolean isLogical(QMPartIfc ifc) throws QMException;
	public Collection getReleaseBomDJ(QMPartIfc partIfc)throws QMException ;
//CCEnd SS4
	
	//CCBegin SS5
	public Collection getFsqd(QMPartIfc partIfc) throws QMException;
	//CCEnd SS5
	
	//CCBegin SS6
	/**
	 * 根据编号获得零部件集合
	 * @param partNumber
	 * @return
	 * @throws QMException
	 */
	public Collection getPartByNumber(String partNumber)throws QMException;
	/**
	 * 根据指定零部件编号，从盆中获得零部件版本
	 * @param partNumber
	 * @return
	 */
	public String getNumberOfPartFromPen(String partNumber);
	/**
	 * 检查零部件是否存在，并且是否有成都路线
	 * @param csvData
	 * @return
	 * @throws QMException
	 */
	public HashMap checkPartFromExcel(String csvData) throws QMException;
	//CCEnd SS6
}