/**
 * ���ɳ���PromulgateNotify_POST_DELETE_Listener.java	1.0              2007-10-23
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class PromulgateNotify_POST_DELETE_Listener implements SignalListenerIfc
{
    /**
     * �����ź�
     * @param signal
     * @throws ServiceHandleSignalException
     */
    public void handleSignal(Signal signal, BaseServiceImp service)
            throws ServiceHandleSignalException
    {
        try
        {
            Object obj = signal.getSignalTarget();
            //�����QMPartIfc����ɾ����������
            if(obj instanceof QMPartIfc)
            {
                QMPartIfc temp = (QMPartIfc) obj;
                PromulgateNotifyService pService = (PromulgateNotifyService) EJBServiceHelper
                        .getService("JFPromulgateNotifyService");
                pService.deletePartLink(temp.getBsoID());
            }
            //�����QMPartMasterIfc����ɾ��������Ʒ
            if(obj instanceof QMPartMasterIfc)
            {
                QMPartMasterIfc temp = (QMPartMasterIfc) obj;
                PromulgateNotifyService pService = (PromulgateNotifyService) EJBServiceHelper
                        .getService("JFPromulgateNotifyService");
                pService.deleteProductLink(temp.getBsoID());
            }
            //�����DocIfc����ɾ�������ĵ�
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
