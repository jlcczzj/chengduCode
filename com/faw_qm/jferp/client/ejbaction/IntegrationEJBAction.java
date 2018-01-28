/**
 * 生成程序IntegrationEJBAction.java   1.0              2007-10-10
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.client.ejbaction;

import java.util.Vector;
import org.apache.log4j.Logger;
import com.faw_qm.jferp.client.event.IntegrationEvent;
import com.faw_qm.jferp.client.event.IntegrationEventResponse;
import com.faw_qm.jferp.ejb.service.IntePackService;
import com.faw_qm.jferp.model.IntePackIfc;
import com.faw_qm.jferp.model.IntePackInfo;
import com.faw_qm.jferp.util.IntePackSourceType;
import com.faw_qm.framework.controller.ejb.action.EJBActionSupport;
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.event.EventException;
import com.faw_qm.framework.event.EventResponse;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title: 集成包ejbaction处理类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 王海军
 * @version 1.0
 */
public class IntegrationEJBAction extends EJBActionSupport
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(IntegrationEJBAction.class);

    private static final long serialVersionUID = 1L;

    /**
     * 默认构造器
     */
    public IntegrationEJBAction()
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
        IntegrationEvent de = (IntegrationEvent) e;
        IntegrationEventResponse eventResponse = null;
        try
        {
            if(de.getActionType() == IntegrationEvent.CREATE)
            {
                Vector dataVector = (Vector) de.getVector();
                String name = (String) dataVector.get(0);
                String type = (String) dataVector.get(1);
                if(type.equals("caiy"))
                {
                    type = "promulgateNotify";
                }
                if(type.equals("notice"))
                {
                    type = "adoptNotice";
                }
                String sourceid = (String) dataVector.get(2);
                IntePackSourceType sourcetype = IntePackSourceType
                        .toIntePackSourceType(type);
                //建立新的集成包值对象。
                IntePackInfo intepack = new IntePackInfo();
                intepack.setName(name);
                intepack.setSourceType(sourcetype);
                intepack.setSource(sourceid);
                //调用服务创建集成包，集成包其他属性在服务中默认给出。
                IntePackService itservice = (IntePackService) EJBServiceHelper
                        .getService("JFIntePackService");
                IntePackIfc intepackifc = itservice.createIntePack(intepack);
                String bsoid = intepackifc.getBsoID();
                eventResponse = new IntegrationEventResponse(bsoid);
            }
            if(de.getActionType() == IntegrationEvent.DELETE)
            {
                Vector dataVector = (Vector) de.getVector();
                String bsoid = (String) dataVector.get(0);
                eventResponse = new IntegrationEventResponse("");
                //调用服务删除集成包。
                try
                {
                    IntePackService itservice = (IntePackService) EJBServiceHelper
                            .getService("JFIntePackService");
                    itservice.deleteIntePack(bsoid);
                    eventResponse.setResult("删除集成包操作成功！");
                }
                catch (QMException ex)
                {
                    logger.error("perform(Event)", ex); //$NON-NLS-1$
                    ex.printStackTrace();
                    eventResponse
                            .setResult("删除操作失败！\n" + ex.getClientMessage());
                }
            }
            if(de.getActionType() == IntegrationEvent.RELEASE)
            {
                Vector dataVector = (Vector) de.getVector();
                String bsoid = (String) dataVector.get(0);
                IntePackService packservice = (IntePackService) EJBServiceHelper
                        .getService("JFIntePackService");
                //调用服务将集成包状态设置为已发布。
                packservice.publishIntePack(bsoid);
                eventResponse = new IntegrationEventResponse("");
            }
            
            if(de.getActionType() == IntegrationEvent.RELEASETECH)
            {
                Vector dataVector = (Vector) de.getVector();
                String bsoid = (String) dataVector.get(0);
                IntePackService packservice = (IntePackService) EJBServiceHelper
                        .getService("JFIntePackService");
                //调用服务将集成包状态设置为已发布。
                //packservice.publishTechByIntePack(bsoid);
                eventResponse = new IntegrationEventResponse("");
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
