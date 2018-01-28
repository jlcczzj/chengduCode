/**
 * 生成程序PublicProcessHTMLAction.java	1.0              2007-11-2
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
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
 * <p>Title: 发布工艺HTMLAction类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
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
        PublicTechnicsEvent splitmaterielEvent = null;
        //action动作。
        String actionType = "";
        actionType = (String) request.getParameter("action1");
        logger.debug("actionType==" + actionType);
        //如果action动作为空则返回空。
        if(actionType == null || actionType.equals(""))
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("perform(HttpServletRequest) - end"); //$NON-NLS-1$
            }
        }
        //如果为“发布”动作。
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
     * 该方法从请求中获取数据。
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
        super.doEnd(request, eventResponse);
        if(logger.isDebugEnabled())
        {
            logger.debug("doEnd(HttpServletRequest, EventResponse) - end"); //$NON-NLS-1$
        }
    }
}
