/**
 * 生成程序PromulgateNotifyEJBAction.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.client;

import org.apache.log4j.Logger;
import java.util.ArrayList;
import java.util.Vector;
import com.faw_qm.erp.ejb.service.PromulgateNotifyService;
import com.faw_qm.erp.model.PromulgateNotifyIfc;
import com.faw_qm.erp.model.PromulgateNotifyInfo;
import com.faw_qm.framework.controller.ejb.action.EJBActionSupport;
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.event.EventException;
import com.faw_qm.framework.event.EventResponse;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title: 采用通知ejbaction处理类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public class PromulgateNotifyEJBAction extends EJBActionSupport
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(PromulgateNotifyEJBAction.class);

    private static final long serialVersionUID = 1L;

    /**
     * 默认构造器
     */
    public PromulgateNotifyEJBAction()
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
            logger
                    .debug("perform(Event) - 类名" + this.getClass().getName() + "n方法: public EventResponse perform(Event e)n参数: e " + e + "n描述: 执行PromulgateNotifysEJBAction开始"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        }
        PromulgateNotifyEvent de = (PromulgateNotifyEvent) e;
        PromulgateNotifyEventResponse eventResponse = null;
        //文件上载响应事件
        try
        {
            //  获得持久化服务
            PromulgateNotifyService anService;
            if(de.getActionType() == PromulgateNotifyEvent.ADD)
            {
                anService = (PromulgateNotifyService) EJBServiceHelper
                        .getService("PromulgateNotifyService");
                Vector dataVector = (Vector) de.getVector();
                PromulgateNotifyInfo aInfo = (PromulgateNotifyInfo) dataVector
                        .get(0);
                if(logger.isDebugEnabled())
                {
                    logger
                            .debug("perform(Event) -  PromulgateNotify and the PromulgateNotifyName is " + aInfo.getPromulgateNotifyName()); //$NON-NLS-1$
                }
                ArrayList aPartList = (ArrayList) dataVector.get(1);
                ArrayList docList = (ArrayList) dataVector.get(2);
                if(logger.isDebugEnabled())
                {
                    logger
                            .debug("perform(Event) -  cln:here get the part list length is " + aPartList.size()); //$NON-NLS-1$
                }
                PromulgateNotifyIfc PromulgateNotify = anService
                        .createPromulgateNotify(aInfo, aPartList, docList);
                if(logger.isDebugEnabled())
                {
                    logger
                            .debug("perform(Event) - the bsoid:" + PromulgateNotify.getBsoID()); //$NON-NLS-1$
                }
                eventResponse = new PromulgateNotifyEventResponse(
                        PromulgateNotify.getBsoID());
            }
            if(de.getActionType() == PromulgateNotifyEvent.UPDATE)
            {
                Vector dataVector = (Vector) de.getVector();
                String updateBsoid = (String) dataVector.get(0);
                ArrayList aPartList = (ArrayList) dataVector.get(1);
                if(logger.isDebugEnabled())
                {
                    logger
                            .debug("perform(Event) -   list length is " + aPartList.size()); //$NON-NLS-1$
                }
                ArrayList docList = (ArrayList) dataVector.get(2);
                if(logger.isDebugEnabled())
                {
                    logger
                            .debug("perform(Event) -  list length is " + docList.size()); //$NON-NLS-1$
                }
                ArrayList partList = (ArrayList) dataVector.get(3);
                if(logger.isDebugEnabled())
                {
                    logger
                            .debug("perform(Event) -  list length is " + partList.size()); //$NON-NLS-1$
                }
                anService = (PromulgateNotifyService) EJBServiceHelper
                        .getService("PromulgateNotifyService");
                //考虑...
                anService.updatePromulgateNotify(updateBsoid, aPartList,
                        docList, partList);
            }
            if(de.getActionType() == PromulgateNotifyEvent.DELETE)
            {
                Vector dataVector = (Vector) de.getVector();
                String deleteBsoid = (String) dataVector.get(0);
                anService = (PromulgateNotifyService) EJBServiceHelper
                        .getService("PromulgateNotifyService");
                //考虑...
                anService.deletePromulgateNotify(deleteBsoid);
            }
        }
        catch (Exception ue)
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
