/**
 * 生成程序IntegrationHTMLAction.java   1.0              2007-10-10
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
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
 * <p>Title: 集成包HTMLAction类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 王海军
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
     * 该方法执行文档请求。
     */
    public Event perform(HttpServletRequest request)
            throws HTMLActionException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("perform(HttpServletRequest) - start"); //$NON-NLS-1$
        }
        //webaction和ejbaction之间的过渡类。
        IntegrationEvent integrationEvent = null;
        //action动作。
        String actionType = "";
        actionType = (String) request.getParameter("action1");
        logger.debug("actionType==" + actionType);
        // 如果action动作为空则返回空。
        if(actionType == null || actionType.equals(""))
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("perform(HttpServletRequest) - end"); //$NON-NLS-1$
            }
            return null;
        }
        //如果为“创建”动作。
        if(actionType.equals("create"))
        {
            integrationEvent = new IntegrationEvent(IntegrationEvent.CREATE);
            getCreateMessage(request, integrationEvent);
        }
        //如果为“发布”动作。
        if(actionType.equals("release"))
        {
            integrationEvent = new IntegrationEvent(IntegrationEvent.RELEASE);
            getReleaseMessage(request, integrationEvent);
        }
        //如果为“发布工艺”动作。
        if(actionType.equals("releaseTech"))
        {
            integrationEvent = new IntegrationEvent(IntegrationEvent.RELEASETECH);
            getReleaseMessage(request, integrationEvent);
        }
        //如果为“删除”动作。
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
     * 该方法从文档请求中获取数据。
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
     * 该方法从文档请求中获取数据。
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
     * 对请求的后处理。
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
