/**
 * ���ɳ���IntegrationHTMLAction.java   1.0              2007-10-10
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.client.webaction;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.faw_qm.cderp.client.event.IntegrationEvent;
import com.faw_qm.cderp.client.event.IntegrationEventResponse;
import com.faw_qm.framework.controller.web.action.HTMLActionException;
import com.faw_qm.framework.controller.web.action.HTMLActionSupport;
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.event.EventResponse;
import com.faw_qm.framework.exceptions.QMException;

/**
 * <p>Title: ���ɰ�HTMLAction�ࡣ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public final class IntegrationHTMLAction extends HTMLActionSupport
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(SplitMaterielHTMLAction.class);

    private static final long serialVersionUID = 1L;

    /**
     * �÷���ִ���ĵ�����
     */
    public Event perform(HttpServletRequest request)
            throws HTMLActionException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("perform(HttpServletRequest) - start"); //$NON-NLS-1$
        }
        //webaction��ejbaction֮��Ĺ����ࡣ
        IntegrationEvent integrationEvent = null;
        //action������
        String actionType = "";
        actionType = (String) request.getParameter("action1");
        logger.debug("actionType==" + actionType);
        // ���action����Ϊ���򷵻ؿա�
        if(actionType == null || actionType.equals(""))
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("perform(HttpServletRequest) - end"); //$NON-NLS-1$
            }
            return null;
        }
        //���Ϊ��������������
        if(actionType.equals("create"))
        {
            integrationEvent = new IntegrationEvent(IntegrationEvent.CREATE);
            getCreateMessage(request, integrationEvent);
        }
        //���Ϊ��������������
        if(actionType.equals("release"))
        {
            integrationEvent = new IntegrationEvent(IntegrationEvent.RELEASE);
            getReleaseMessage(request, integrationEvent);
        }
        //���Ϊ���������ա�������
        if(actionType.equals("releaseTech"))
        {
            integrationEvent = new IntegrationEvent(IntegrationEvent.RELEASETECH);
            getReleaseMessage(request, integrationEvent);
        }
        //���Ϊ��ɾ����������
        if(actionType.equals("delete"))
        {
            integrationEvent = new IntegrationEvent(IntegrationEvent.DELETE);
            getDeleteMessage(request, integrationEvent);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("perform(HttpServletRequest) - end"); //$NON-NLS-1$
        }
        return integrationEvent;
    }

    /**
     * �÷������ĵ������л�ȡ���ݡ�
     * @param evevt
     * @return com.faw_qm.doc.util.DocFormData
     */
    public Vector getCreateMessage(HttpServletRequest request,
            IntegrationEvent event) throws HTMLActionException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getCreateMessage(HttpServletRequest, IntegrationEvent) - start"); //$NON-NLS-1$
        }
        Vector data = new Vector();
        String name = request.getParameter("topic");
        String type = request.getParameter("radioType");
        String sourceid = request.getParameter("sourceid");
        data.add(0, name);
        data.add(1, type);
        data.add(2, sourceid);
        event.setVector(data);
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getCreateMessage(HttpServletRequest, IntegrationEvent) - end"); //$NON-NLS-1$
        }
        return data;
    }

    /**
     * �÷������ĵ������л�ȡ���ݡ�
     * @param evevt
     * @return com.faw_qm.doc.util.DocFormData
     */
    public Vector getReleaseMessage(HttpServletRequest request,
            IntegrationEvent event) throws HTMLActionException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getReleaseMessage(HttpServletRequest, IntegrationEvent) - start"); //$NON-NLS-1$
        }
        Vector data = new Vector();
        String inteid = request.getParameter("integrationid");
        data.add(0, inteid);
        event.setVector(data);
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getReleaseMessage(HttpServletRequest, IntegrationEvent) - end"); //$NON-NLS-1$
        }
        return data;
    }

    public Vector getDeleteMessage(HttpServletRequest request,
            IntegrationEvent event)
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getDeleteMessage(HttpServletRequest, IntegrationEvent) - start"); //$NON-NLS-1$
        }
        Vector vector1 = new Vector();
        try
        {
            String bsoid = request.getParameter("integrationid");
            vector1.add(0, bsoid);
            event.setVector(vector1);
        }
        catch (Exception e)
        {
            logger.error("deleteGainMessage(HttpServletRequest)", e); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteGainMessage(HttpServletRequest) - end"); //$NON-NLS-1$
        }
        return vector1;
    }

    /**
     * ������ĺ���
     * @param request
     * @param eventResponse
     */
    public void doEnd(HttpServletRequest request, EventResponse eventResponse)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("doEnd(HttpServletRequest, EventResponse) - start"); //$NON-NLS-1$
        }
        if(eventResponse != null)
        {
            if(eventResponse instanceof IntegrationEventResponse)
            {
                IntegrationEventResponse response = (IntegrationEventResponse) eventResponse;
                String id = response.getBsoID();
                request.setAttribute("integrationid", id);
                String message = response.getResult();
                if(message != null)
                {
                    request.setAttribute("resultmessage", message);
                }
                if(logger.isDebugEnabled())
                {
                    logger
                            .debug("doEnd(HttpServletRequest, EventResponse) - here set bsoid:" + id); //$NON-NLS-1$
                }
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("doEnd(HttpServletRequest, EventResponse) - end"); //$NON-NLS-1$
        }
    }
}
