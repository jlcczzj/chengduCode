/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.gybomNotice.ejb.service;


import java.util.Collection;


import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.ServiceHandleSignalException;
import com.faw_qm.framework.service.Signal;
import com.faw_qm.framework.service.SignalListenerIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;
import com.faw_qm.part.model.QMPartInfo;




/**
 * 监听信号GYBOMNOTICE_PRE_DELETE_Listener
 * @author 文柳  修改时间  2014/06/03
 * @version 1.0
 */
public class GYBOMNOTICE_PRE_DELETE_Listener implements SignalListenerIfc
{

    /**
     * 处理信号
     * @param signal
     * @throws ServiceHandleSignalException
     */
    public void handleSignal(Signal signal, BaseServiceImp service)
            throws
            ServiceHandleSignalException
    {
        try
        {
            Object obj = signal.getSignalTarget();                       
            if (obj instanceof GYBomAdoptNoticeInfo)
            {
            	Collection col = ((GYBomNoticeServiceEJB) service).getSubGYBomAdoptNotice((GYBomAdoptNoticeInfo)obj);
            	if(col!=null&&col.size()>0){
            		throw new ServiceHandleSignalException("该发布单被其它车架、车身发布单单应用！不能删除！");
            	}
            	
            } 
            if(obj instanceof QMPartInfo){
            	Collection col = ((GYBomNoticeServiceEJB) service).getPartsFromBomSubAdoptNoticeLink((QMPartInfo)obj);
            	if(col!=null&&col.size()>0){
            		throw new ServiceHandleSignalException("该零件被发布单关联！不能删除！");
            	}
            }

            
            
        } 
        catch (Exception ex)
        {
            throw new ServiceHandleSignalException(ex);
        } 

    } 

} 
