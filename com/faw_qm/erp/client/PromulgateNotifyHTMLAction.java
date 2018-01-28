/**
 * ���ɳ���PromulgateNotifyHTMLAction.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ����֪ͨHTMLAction�ࡣ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
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
     * �÷���ִ���ĵ�����
     */
    public Event perform(HttpServletRequest request) throws HTMLActionException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("perform(HttpServletRequest) - start"); //$NON-NLS-1$
        }
        //  webaction��ejbaction֮��Ĺ�����
        PromulgateNotifyEvent PromulgateNotifyEvent = null;
        Vector data = new Vector();
        // action����
        String actionType = "";
        actionType = (String) request.getParameter("action");
        logger.debug("actionType==" + actionType);
        //  ���action����Ϊ���򷵻ؿ�
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
                    || (PromulgateNotifyName.replace('��', ' ')).trim().length() == 0)
            {
                throw new HTMLActionException("����֪ͨ�����Ʋ���Ϊ�ջ�ȫ��Ϊ�ո�");
            }
            String PromulgateNotifyNum = (String) request
                    .getParameter("proNum");
            if(PromulgateNotifyNum == null
                    || PromulgateNotifyNum.trim().length() == 0
                    || (PromulgateNotifyNum.replace('��', ' ')).trim().length() == 0)
            {
                throw new HTMLActionException("����֪ͨ���Ų���Ϊ�ջ�ȫ��Ϊ�ո�");
            }
            String[] refselectedPartFiles = (String[]) request
                    .getParameterValues("checkboxpartbsoID");
            if(refselectedPartFiles == null || refselectedPartFiles.length <= 0)
            {
                throw new HTMLActionException("����֪ͨ��������Ҫ���õ��㲿��");
            }
        }
        //  ���Ϊ���½�������
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
        //  ���Ϊ�����¡�����
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
        //  ���Ϊ��ɾ��������
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
     * �÷������ĵ������л�ȡ���ݡ�
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
                    || (PromulgateNotifyName.replace('��', ' ')).trim().length() == 0)
            {
                throw new HTMLActionException("����֪ͨ�����Ʋ���Ϊ�ջ�ȫ��Ϊ�ո�");
            }
            PromulgateNotify.setPromulgateNotifyName(PromulgateNotifyName);
            String PromulgateNotifyNum = (String) request
                    .getParameter("proNum");
            if(PromulgateNotifyNum == null
                    || PromulgateNotifyNum.trim().length() == 0
                    || (PromulgateNotifyNum.replace('��', ' ')).trim().length() == 0)
            {
                throw new HTMLActionException("����֪ͨ���Ų���Ϊ�ջ�ȫ��Ϊ�ո�");
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
            //�ر�����,Ԥ���ֶΣ�׼���Ժ���չ
            PromulgateNotify.setPromulgateNotifyType("PromulgateNotify");
            ArrayList refPartList = new ArrayList();
            String[] refselectedPartFiles = (String[]) request
                    .getParameterValues("checkboxpartbsoID");
            if(refselectedPartFiles == null || refselectedPartFiles.length <= 0)
            {
                throw new HTMLActionException("����֪ͨ��������Ҫ���õ��㲿��");
            }
            //���ο��ĵ���ӵ�ArrayList�� ���refselectedFilesΪ��˵��û��ѡ��ο��ĵ�����������
            if(refselectedPartFiles != null)
            {
                //refselectedFiles.lengthΪѡ��Ĳο��ĵ��ĸ���
                for (int i = 0; i < refselectedPartFiles.length; i++)
                {
                    refPartList.add(refselectedPartFiles[i]);
                }
            }
            ArrayList refDocList = new ArrayList();
            String[] refselectedFiles = (String[]) request
                    .getParameterValues("checkboxrefbsoID");
            //���ο��ĵ���ӵ�ArrayList�� ���refselectedFilesΪ��˵��û��ѡ��ο��ĵ�����������
            if(refselectedFiles != null)
            {
                //refselectedFiles.lengthΪѡ��Ĳο��ĵ��ĸ���
                for (int i = 0; i < refselectedFiles.length; i++)
                {
                    refDocList.add(refselectedFiles[i]);
                }
            }
            //����DocFormData ��dependencyList���ԣ����γɲο��ĵ���bsoID�����飩
            data.add(0, PromulgateNotify);
            data.add(1, refPartList);
            data.add(2, refDocList);
        }
        catch (QMException e)
        {
            logger.error("gainMessage(HttpServletRequest)", e); //$NON-NLS-1$
        }
        //  ���ض���
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
            //����Product����
            ArrayList refPartList = new ArrayList();
            String[] refselectedPartFiles = (String[]) request
                    .getParameterValues("checkboxpartbsoID");
            if(refselectedPartFiles == null || refselectedPartFiles.length <= 0)
            {
                throw new HTMLActionException("����֪ͨ��������Ҫ���õ��㲿��");
            }
            //���ο��ĵ���ӵ�ArrayList�� ���refselectedFilesΪ��˵��û��ѡ��ο��ĵ�����������
            if(refselectedPartFiles != null)
            {
                //refselectedFiles.lengthΪѡ��Ĳο��ĵ��ĸ���
                for (int i = 0; i < refselectedPartFiles.length; i++)
                {
                    refPartList.add(refselectedPartFiles[i]);
                }
            }
            ArrayList refDocList = new ArrayList();
            String[] refselectedFiles = (String[]) request
                    .getParameterValues("checkboxrefbsoID");
            //���ο��ĵ���ӵ�ArrayList�� ���refselectedFilesΪ��˵��û��ѡ��ο��ĵ�����������
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
     * ���ĵ�����ĺ���
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
