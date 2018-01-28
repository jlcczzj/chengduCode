/**
 * ���ɳ���PublicProcessHTMLAction.java	1.0              2007-11-2
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.client.webaction;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.faw_qm.cderp.client.event.PublicTechnicsEvent;
import com.faw_qm.framework.controller.web.action.HTMLActionException;
import com.faw_qm.framework.controller.web.action.HTMLActionSupport;
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.event.EventResponse;
import com.faw_qm.framework.exceptions.QMException;

/**
 * <p>Title: ��������HTMLAction�ࡣ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class PublicTechnicsHTMLAction extends HTMLActionSupport
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(PublicTechnicsHTMLAction.class);

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
        PublicTechnicsEvent splitmaterielEvent = null;
        //action������
        String actionType = "";
        actionType = (String) request.getParameter("action1");
        logger.debug("actionType==" + actionType);
        //���action����Ϊ���򷵻ؿա�
        if(actionType == null || actionType.equals(""))
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("perform(HttpServletRequest) - end"); //$NON-NLS-1$
            }
        }
        //���Ϊ��������������
        else if(actionType.equals("release"))
        {
            splitmaterielEvent = new PublicTechnicsEvent(
                    PublicTechnicsEvent.REALEASE);
            getProcessMessage(request, splitmaterielEvent);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("perform(HttpServletRequest) - end"); //$NON-NLS-1$
        }
        return splitmaterielEvent;
    }

    /**
     * �÷����������л�ȡ���ݡ�
     * @param evevt
     */
    public void getProcessMessage(HttpServletRequest request,
            PublicTechnicsEvent event) throws HTMLActionException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getSplitMessage(HttpServletRequest, SplitMaterielEvent) - start"); //$NON-NLS-1$
        }
        String processIDs = request.getParameter("publicPros");
        event.setProcessIDs(processIDs);
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getSplitMessage(HttpServletRequest, SplitMaterielEvent) - end"); //$NON-NLS-1$
        }
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
        super.doEnd(request, eventResponse);
        if(logger.isDebugEnabled())
        {
            logger.debug("doEnd(HttpServletRequest, EventResponse) - end"); //$NON-NLS-1$
        }
    }
}
