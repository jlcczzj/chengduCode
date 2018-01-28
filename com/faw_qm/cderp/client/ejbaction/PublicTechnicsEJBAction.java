/**
 * 生成程序PublicProcessEJBAction.java	1.0              2007-11-5
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
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
 * <p>Title:发布工艺ejbaction处理类 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
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
     * 默认构造器
     */
    public PublicTechnicsEJBAction()
    {
    }

    /**
     * @param e - 执行请求。
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
            //获得持久化服务。
            if(de.getActionType() == PublicTechnicsEvent.REALEASE)
            {
                //获取发布工艺规程的id字串。
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