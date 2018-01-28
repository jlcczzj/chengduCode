/** 生成程序 PART_REMOVE_Listener.java    1.0    2003/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 A004-2015-3272 如果零部件已经生成过订单，那么不允许删除。 liunan 2016-3-25
 */

package com.faw_qm.part.ejb.standardService;

import java.util.Collection;
import java.util.Iterator;

import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.ServiceHandleSignalException;
import com.faw_qm.framework.service.Signal;
import com.faw_qm.framework.service.SignalListenerIfc;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.EJBServiceHelper;
//CCBegin SS1
import com.faw_qm.pcfg.doc.model.*;
import com.faw_qm.part.model.QMPartIfc;
//CCEnd SS1


/**
 * 零部件删除信号监听类。
 * @author 吴先超
 * @version 1.0
 */

public class PART_REMOVE_Listener implements SignalListenerIfc
{
    private String RESOURCE = "com.faw_qm.part.util.PartResource";


    /**
     * 处理信号。
     * @param signal Signal
     * @param service BaseServiceImp
     * @throws ServiceHandleSignalException
     */
    public void handleSignal(Signal signal, BaseServiceImp service)
            throws ServiceHandleSignalException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "handleSignal() begin ....");
        //返回被复制的目标对象obj.
        Object obj = signal.getSignalTarget();
        if (obj instanceof BaselineIfc)
        {
            try
            {
                checkConfigSpec((BaselineIfc) obj);
            }
            catch (QMException ex)
            {
                throw new ServiceHandleSignalException("服务名:" +
                        service.getServiceName() + ex);
            }
        }
        //CCBegin SS1
        if(obj instanceof QMPartIfc)
        {
            try
            {
        	PersistService ps = (PersistService) EJBServiceHelper.
                                  getPersistService();
            String gpID = ((QMPartIfc)obj).getBsoID();
                QMQuery query = new QMQuery("VariantSpecVariantLink");
                query.addCondition(new QueryCondition("genericPartID", "=", gpID));
                Collection queryresult = ps.findValueInfo(query);
                String specNum = "";
                if (queryresult != null && queryresult.size() != 0)
                {
                	Iterator iter = queryresult.iterator();
                	while (iter.hasNext())
                	{
                		VariantSpecVariantLinkIfc link = (VariantSpecVariantLinkIfc)iter.next();
                		VariantSpecIfc variantspec = (VariantSpecIfc)ps.refreshInfo(link.getRightBsoID());
                		specNum = variantspec.getDocNum()+","+specNum;
                	}
                }
                if(!specNum.equals(""))
                {
                	throw new PartException("当前零部件（"+((QMPartIfc)obj).getPartNumber()+"）已经创建了订单（"+specNum+"），请删除订单后再删除该零部件。");
                }
            }
            catch (QMException ex)
            {
                throw new ServiceHandleSignalException("服务名:" +
                        service.getServiceName() + ex);
            }
        }
        //CCEnd SS1
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "handleSignal() end....return is void");
    }


    /**
     * 检验当前的筛选条件，如果要删除的基准线被作为当前的筛选条件，则不能被删除。
     * @param obj :Baseline要被删除的基准线。
     * @throws QMException
     */
    protected void checkConfigSpec(BaselineIfc obj)
            throws QMException
    {
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "checkConfigSpec() begin ....");
        QMQuery query = new QMQuery("PartConfigSpec");
        QueryCondition condition = new QueryCondition("baselineID", "=",
                obj.getBsoID());
        query.addCondition(condition);
        PersistService pservice = (PersistService) EJBServiceHelper.
                                  getPersistService();
        Collection collection = pservice.findValueInfo(query);
        if (collection.size() == 1)
        {
            //如果条件成立，抛出PartException异常
            //"该基准线不能被删除，它被作为当前的筛选条件。"
            throw new PartException(RESOURCE, "3", null);
        }
        PartDebug.trace(this, PartDebug.PART_SERVICE,
                        "checkConfigSpec() end....return is void");
    }
}
