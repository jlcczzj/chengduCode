/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * �����ź�GYBOMNOTICE_PRE_DELETE_Listener
 * @author ����  �޸�ʱ��  2014/06/03
 * @version 1.0
 */
public class GYBOMNOTICE_PRE_DELETE_Listener implements SignalListenerIfc
{

    /**
     * �����ź�
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
            		throw new ServiceHandleSignalException("�÷��������������ܡ�����������Ӧ�ã�����ɾ����");
            	}
            	
            } 
            if(obj instanceof QMPartInfo){
            	Collection col = ((GYBomNoticeServiceEJB) service).getPartsFromBomSubAdoptNoticeLink((QMPartInfo)obj);
            	if(col!=null&&col.size()>0){
            		throw new ServiceHandleSignalException("�����������������������ɾ����");
            	}
            }

            
            
        } 
        catch (Exception ex)
        {
            throw new ServiceHandleSignalException(ex);
        } 

    } 

} 
