/**
 * ���ɳ���PublicProcessEJBAction.java	1.0              2007-11-5
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.client.ejbaction;

import org.apache.log4j.Logger;
import com.faw_qm.cderp.client.event.PublicTechnicsEvent;
import com.faw_qm.cderp.client.event.PublicTechnicsEventResponse;
import com.faw_qm.cderp.util.RequestHelper;
import com.faw_qm.framework.controller.ejb.action.EJBActionSupport;
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.event.EventException;
import com.faw_qm.framework.event.EventResponse;
import com.faw_qm.framework.exceptions.QMException;

/**
 * <p>Title:��������ejbaction������ ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class PublicTechnicsEJBAction extends EJBActionSupport
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(PublicTechnicsEJBAction.class);

    private static final long serialVersionUID = 1L;

    /**
     * Ĭ�Ϲ�����
     */
    public PublicTechnicsEJBAction()
    {
    }

    /**
     * @param e - ִ������
     * @return com.faw_qm.framework.event.EventResponse
     */
    public EventResponse perform(Event e) throws EventException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("perform(Event) - start"); //$NON-NLS-1$
        }
        PublicTechnicsEvent de = (PublicTechnicsEvent) e;
        PublicTechnicsEventResponse eventResponse = null;
        logger.debug("de.getActionType() is " + de.getActionType()); //$NON-NLS-1$
        try
        {
            //��ó־û�����
            if(de.getActionType() == PublicTechnicsEvent.REALEASE)
            {
                //��ȡ�������չ�̵�id�ִ���
                String processIDs = de.getProcessIDs();
                RequestHelper.request("CDMaterialSplitService", "publicTechnics",
                        new Class[]{String.class}, new Object[]{processIDs});
                eventResponse = new PublicTechnicsEventResponse("");
            }
        }
        catch (QMException ue)
        {
            logger.error("perform(Event)", ue); //$NON-NLS-1$
            Object[] objs = {de.getEventName()};
            throw new EventException(ue, "22", objs);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("perform(Event) - ==>return res:" + eventResponse); //$NON-NLS-1$
        }
        return eventResponse;
    }
}