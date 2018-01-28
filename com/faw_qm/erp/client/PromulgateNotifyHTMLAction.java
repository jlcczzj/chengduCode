/**
 * 生成程序PromulgateNotifyHTMLAction.java   1.0              2006-11-6
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
import javax.servlet.http.HttpServletRequest;
import com.faw_qm.erp.model.PromulgateNotifyIfc;
import com.faw_qm.erp.model.PromulgateNotifyInfo;
import com.faw_qm.erp.util.PromulgateNotifyFlag;
import com.faw_qm.framework.controller.web.action.HTMLActionException;
import com.faw_qm.framework.controller.web.action.HTMLActionSupport;
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.event.EventResponse;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title: 采用通知HTMLAction类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 蔡丽娜
 * @version 1.0
 */
public final class PromulgateNotifyHTMLAction extends HTMLActionSupport
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(PromulgateNotifyHTMLAction.class);

    private static final long serialVersionUID = 1L;

    /**
     * 该方法执行文档请求。
     */
    public Event perform(HttpServletRequest request) throws HTMLActionException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("perform(HttpServletRequest) - start"); //$NON-NLS-1$
        }
        //  webaction和ejbaction之间的过渡类
        PromulgateNotifyEvent PromulgateNotifyEvent = null;
        Vector data = new Vector();
        // action动作
        String actionType = "";
        actionType = (String) request.getParameter("action");
        logger.debug("actionType==" + actionType);
        //  如果action动作为空则返回空
        if(actionType == null || actionType.equals(""))
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("perform(HttpServletRequest) - end"); //$NON-NLS-1$
            }
            return null;
        }
        if(actionType.equals("add") || actionType.equals("update"))
        {
            String PromulgateNotifyName = (String) request
                    .getParameter("proName");
            if(PromulgateNotifyName == null
                    || PromulgateNotifyName.trim().length() == 0
                    || (PromulgateNotifyName.replace('　', ' ')).trim().length() == 0)
            {
                throw new HTMLActionException("采用通知书名称不能为空或全部为空格");
            }
            String PromulgateNotifyNum = (String) request
                    .getParameter("proNum");
            if(PromulgateNotifyNum == null
                    || PromulgateNotifyNum.trim().length() == 0
                    || (PromulgateNotifyNum.replace('　', ' ')).trim().length() == 0)
            {
                throw new HTMLActionException("采用通知书编号不能为空或全部为空格");
            }
            String[] refselectedPartFiles = (String[]) request
                    .getParameterValues("checkboxpartbsoID");
            if(refselectedPartFiles == null || refselectedPartFiles.length <= 0)
            {
                throw new HTMLActionException("采用通知书必须添加要采用的零部件");
            }
        }
        //  如果为“新建”动作
        if(actionType.equals("add"))
        {
            data = gainMessage(request);
            if(logger.isDebugEnabled())
            {
                logger
                        .debug("perform(HttpServletRequest) - in html action  data " + data); //$NON-NLS-1$
            }
            PromulgateNotifyEvent = new PromulgateNotifyEvent(
                    com.faw_qm.erp.client.PromulgateNotifyEvent.ADD, data);
        }
        //  如果为“更新”动作
        if(actionType.equals("update"))
        {
            try
            {
                data = updateGainMessage(request);
            }
            catch (QMException ex)
            {
                logger.error("perform(HttpServletRequest)", ex); //$NON-NLS-1$
                throw new HTMLActionException(ex);
            }
            PromulgateNotifyEvent = new PromulgateNotifyEvent(
                    com.faw_qm.erp.client.PromulgateNotifyEvent.UPDATE, data);
        }
        //  如果为“删除”动作
        if(actionType.equals("delete"))
        {
            data = deleteGainMessage(request);
            PromulgateNotifyEvent = new PromulgateNotifyEvent(
                    com.faw_qm.erp.client.PromulgateNotifyEvent.DELETE, data);
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("perform(HttpServletRequest) - pro.perform() end..... return " + PromulgateNotifyEvent); //$NON-NLS-1$
        }
        return PromulgateNotifyEvent;
    }

    /**
     * 该方法从文档请求中获取数据。
     * @param evevt
     * @return com.faw_qm.doc.util.DocFormData
     */
    public Vector gainMessage(HttpServletRequest request)
            throws HTMLActionException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("gainMessage(HttpServletRequest) -  gainMessage()  begin..... "); //$NON-NLS-1$
        }
        Vector data = new Vector();
        PromulgateNotifyInfo PromulgateNotify = new PromulgateNotifyInfo();
        try
        {
            String PromulgateNotifyName = (String) request
                    .getParameter("proName");
            if(PromulgateNotifyName == null
                    || PromulgateNotifyName.trim().length() == 0
                    || (PromulgateNotifyName.replace('　', ' ')).trim().length() == 0)
            {
                throw new HTMLActionException("采用通知书名称不能为空或全部为空格");
            }
            PromulgateNotify.setPromulgateNotifyName(PromulgateNotifyName);
            String PromulgateNotifyNum = (String) request
                    .getParameter("proNum");
            if(PromulgateNotifyNum == null
                    || PromulgateNotifyNum.trim().length() == 0
                    || (PromulgateNotifyNum.replace('　', ' ')).trim().length() == 0)
            {
                throw new HTMLActionException("采用通知书编号不能为空或全部为空格");
            }
            PromulgateNotify.setPromulgateNotifyNumber(PromulgateNotifyNum);
            String flag = (String) request
                    .getParameter("selectpromulgateNotifyFlag");
            PromulgateNotify.setPromulgateNotifyFlag(PromulgateNotifyFlag
                    .toPromulgateNotifyFlag(flag).toString());
            String PromulgateNotifyDes = (String) request
                    .getParameter("proDes");
            PromulgateNotify
                    .setPromulgateNotifyDescription(PromulgateNotifyDes);
            PromulgateNotify.setHasPromulgate("0");
            //特别设置,预留字段，准备以后扩展
            PromulgateNotify.setPromulgateNotifyType("PromulgateNotify");
            ArrayList refPartList = new ArrayList();
            String[] refselectedPartFiles = (String[]) request
                    .getParameterValues("checkboxpartbsoID");
            if(refselectedPartFiles == null || refselectedPartFiles.length <= 0)
            {
                throw new HTMLActionException("采用通知书必须添加要采用的零部件");
            }
            //将参考文档添加到ArrayList中 如果refselectedFiles为空说明没有选择参考文档，则不做处理。
            if(refselectedPartFiles != null)
            {
                //refselectedFiles.length为选择的参考文档的个数
                for (int i = 0; i < refselectedPartFiles.length; i++)
                {
                    refPartList.add(refselectedPartFiles[i]);
                }
            }
            ArrayList refDocList = new ArrayList();
            String[] refselectedFiles = (String[]) request
                    .getParameterValues("checkboxrefbsoID");
            //将参考文档添加到ArrayList中 如果refselectedFiles为空说明没有选择参考文档，则不做处理。
            if(refselectedFiles != null)
            {
                //refselectedFiles.length为选择的参考文档的个数
                for (int i = 0; i < refselectedFiles.length; i++)
                {
                    refDocList.add(refselectedFiles[i]);
                }
            }
            //设置DocFormData 的dependencyList属性（即形成参考文档的bsoID的数组）
            data.add(0, PromulgateNotify);
            data.add(1, refPartList);
            data.add(2, refDocList);
        }
        catch (QMException e)
        {
            logger.error("gainMessage(HttpServletRequest)", e); //$NON-NLS-1$
        }
        //  返回对象
        if(logger.isDebugEnabled())
        {
            logger.debug("gainMessage(HttpServletRequest) - end"); //$NON-NLS-1$
        }
        return data;
    }

    public Vector deleteGainMessage(HttpServletRequest request)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteGainMessage(HttpServletRequest) - start"); //$NON-NLS-1$
        }
        Vector vector1 = new Vector();
        try
        {
            String bsoid = request.getParameter("bsoID");
            vector1.add(0, bsoid);
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

    public Vector updateGainMessage(HttpServletRequest request)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("updateGainMessage(HttpServletRequest) - start"); //$NON-NLS-1$
        }
        Vector updateata = new Vector();
        try
        {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
            String bsoid = (String) request.getParameter("bsoID");
            PromulgateNotifyIfc info = (PromulgateNotifyIfc) pService
                    .refreshInfo(bsoid);
            info.setPromulgateNotifyName(request.getParameter("proName"));
            info.setPromulgateNotifyDescription(request.getParameter("proDes"));
            pService.saveValueInfo(info);
            //处理Product集合
            ArrayList refPartList = new ArrayList();
            String[] refselectedPartFiles = (String[]) request
                    .getParameterValues("checkboxpartbsoID");
            if(refselectedPartFiles == null || refselectedPartFiles.length <= 0)
            {
                throw new HTMLActionException("采用通知书必须添加要采用的零部件");
            }
            //将参考文档添加到ArrayList中 如果refselectedFiles为空说明没有选择参考文档，则不做处理。
            if(refselectedPartFiles != null)
            {
                //refselectedFiles.length为选择的参考文档的个数
                for (int i = 0; i < refselectedPartFiles.length; i++)
                {
                    refPartList.add(refselectedPartFiles[i]);
                }
            }
            ArrayList refDocList = new ArrayList();
            String[] refselectedFiles = (String[]) request
                    .getParameterValues("checkboxrefbsoID");
            //将参考文档添加到ArrayList中 如果refselectedFiles为空说明没有选择参考文档，则不做处理。
            if(refselectedFiles != null)
            {
                for (int i = 0; i < refselectedFiles.length; i++)
                {
                    refDocList.add(refselectedFiles[i]);
                }
            }
            //part
            ArrayList partList = new ArrayList();
            String[] selectedpart = (String[]) request
                    .getParameterValues("checkboxlinkpartbsoID");
            if(selectedpart != null)
            {
                for (int i = 0; i < selectedpart.length; i++)
                {
                    partList.add(selectedpart[i]);
                }
            }
            updateata.add(0, bsoid);
            updateata.add(1, refPartList);
            updateata.add(2, refDocList);
            updateata.add(3, partList);
        }
        catch (QMException e)
        {
            logger.error("updateGainMessage(HttpServletRequest)", e); //$NON-NLS-1$
            throw e;
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("updateGainMessage(HttpServletRequest) - end"); //$NON-NLS-1$
        }
        return updateata;
    }

    public Vector updateadddocGainMessage(HttpServletRequest request)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("updateadddocGainMessage(HttpServletRequest) - start"); //$NON-NLS-1$
        }
        Vector vector1 = new Vector();
        Vector vector2 = new Vector();
        String bsoid = (String) request.getParameter("bsoID");
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("updateadddocGainMessage(HttpServletRequest) - here in the updateadddoc method ,and the bsoid is " + bsoid); //$NON-NLS-1$
        }
        int count;
        try
        {
            count = (Integer.valueOf((String) request.getParameter("count")))
                    .intValue();
        }
        catch (Exception e)
        {
            logger.error("updateadddocGainMessage(HttpServletRequest)", e); //$NON-NLS-1$
            return vector1;
        }
        for (int i = 0; i < count; i++)
        {
            String aa = request.getParameter("checkboxDocName"
                    + String.valueOf(i));
            if(aa != null)
            {
                vector2.addElement(aa);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("updateadddocGainMessage(HttpServletRequest) - here in the updateadddoc method ,borrowhtmlaction,and get the number of the vector is " + vector2.size()); //$NON-NLS-1$
        }
        vector1.add(0, bsoid);
        vector1.add(1, vector2);
        if(logger.isDebugEnabled())
        {
            logger.debug("updateadddocGainMessage(HttpServletRequest) - end"); //$NON-NLS-1$
        }
        return vector1;
    }

    /**
     * 对文档请求的后处理。
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
            if(eventResponse instanceof PromulgateNotifyEventResponse)
            {
                PromulgateNotifyEventResponse ber = (PromulgateNotifyEventResponse) eventResponse;
                String id = ber.getPromulgateNotifyBsoID();
                request.setAttribute("bsoID", id);
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
