/**
 * 生成程序IntePackHelper.java	1.0              2007-11-30
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

//import com.faw_qm.adoptnotice.util.AdoptNoticeServiceHelper;
import com.faw_qm.baseline.ejb.service.BaselineService;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.erp.ejb.service.MaterialSplitService;
import com.faw_qm.erp.model.IntePackInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;

/**
 * @author Administrator
 * 
 */
public class IntePackHelper {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(PromulgateNotifyHelper.class);
	
    /**
     * 根据基线得到受基线管理的零部件集合
     * @param baseline 基线
     * @return List 受基线管理的零部件的集合
     * @throws QMException
     */
    private Collection getpartsByBaseline(String baselineid) throws QMException
    {
    	List partList = new ArrayList(0);
    	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
    	BaselineService bservice=(BaselineService)EJBServiceHelper.getService("BaselineService");
    	ManagedBaselineIfc baseline=(ManagedBaselineIfc)pservice.refreshInfo(baselineid); 
    	Collection coll=bservice.getBaselineItems(baseline);
    	return coll;
	}
	
	public Vector getProductsByIntePack(String intePackID) {
		if (logger.isDebugEnabled()) {
			logger.debug("getProductsByIntePack(String) - start"); //$NON-NLS-1$
		}
		Vector parts = new Vector();
		IntePackInfo intePackInfo = null;
		try {
			// 获得持久化服务
			PersistService persistService = (PersistService) EJBServiceHelper
					.getService("PersistService");
			MaterialSplitService msservcie = (MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
			intePackInfo = (IntePackInfo) persistService.refreshInfo(
					intePackID, false);
			if (intePackInfo != null) {
				IntePackSourceType intePackType = intePackInfo.getSourceType();
				if (intePackType.equals(IntePackSourceType.PROMULGATENOTIFY)) {
					parts = PromulgateNotifyHelper
							.getProductsByProID(intePackInfo.getSource());
				} /*else if (intePackType.equals(IntePackSourceType.ADOPTNOTICE)) {
					Collection coll = AdoptNoticeServiceHelper
							.findAllPartByReference(intePackInfo.getSource());
					Iterator iter = coll.iterator();
					while (iter.hasNext()) {
						parts.add(iter.next());
					}
				}*/
				else if(intePackType.equals(IntePackSourceType.BASELINE))
				{
					Collection coll=getpartsByBaseline(intePackInfo.getSource());
					Iterator iter=coll.iterator();
					while(iter.hasNext())
					{
						parts.add(iter.next());
					}
				}
				else if(intePackType.equals(IntePackSourceType.technicsRouteList))
                {
                    TechnicsRouteListIfc listifc = (TechnicsRouteListIfc)persistService.refreshInfo(intePackInfo.getSource());
                    Collection coll = msservcie.getPartByRouteList(listifc);
                    for(Iterator iter = coll.iterator(); iter.hasNext(); parts.add(iter.next()));
                }
			}
		} catch (Exception ex) {
			logger.error("getProductsByIntePack(String)", ex); //$NON-NLS-1$
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getProductsByIntePack(String) - end"); //$NON-NLS-1$
		}
		return parts;
	}

}
