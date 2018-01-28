/**
 * 生成程序PromulgateNotify_POST_DELETE_Listener.java	1.0              2007-10-23
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.ejb.service;

import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.ServiceHandleSignalException;
import com.faw_qm.framework.service.Signal;
import com.faw_qm.framework.service.SignalListenerIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class PromulgateNotify_POST_DELETE_Listener implements SignalListenerIfc
{
    /**
     * 处理信号
     * @param signal
     * @throws ServiceHandleSignalException
     */
    public void handleSignal(Signal signal, BaseServiceImp service)
            throws ServiceHandleSignalException
    {
        try
        {
            Object obj = signal.getSignalTarget();
            //如果是QMPartIfc，则删除关联部件
            if(obj instanceof QMPartIfc)
            {
                QMPartIfc temp = (QMPartIfc) obj;
                PromulgateNotifyService pService = (PromulgateNotifyService) EJBServiceHelper
                        .getService("JFPromulgateNotifyService");
                pService.deletePartLink(temp.getBsoID());
            }
            //如果是QMPartMasterIfc，则删除关联产品
            if(obj instanceof QMPartMasterIfc)
            {
                QMPartMasterIfc temp = (QMPartMasterIfc) obj;
                PromulgateNotifyService pService = (PromulgateNotifyService) EJBServiceHelper
                        .getService("JFPromulgateNotifyService");
                pService.deleteProductLink(temp.getBsoID());
            }
            //如果是DocIfc，则删除关联文档
            if(obj instanceof DocIfc)
            {
                DocIfc temp = (DocIfc) obj;
                PromulgateNotifyService pService = (PromulgateNotifyService) EJBServiceHelper
                        .getService("JFPromulgateNotifyService");
                pService.deleteDocLink(temp.getBsoID());
            }

        }//end try
        catch (Exception ex)
        {
            throw new ServiceHandleSignalException(ex);
        }//end catch
    }//end handleSignal(Signal)
}
