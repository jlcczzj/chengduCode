/**
 * 生成程序SplitMaterielHTMLAction.java   1.0              2007-10-10
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.cderp.client.webaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.faw_qm.cderp.client.event.SplitMaterielEvent;
import com.faw_qm.cderp.client.event.SplitMaterielEventResponse;
import com.faw_qm.framework.controller.web.action.HTMLActionException;
import com.faw_qm.framework.controller.web.action.HTMLActionSupport;
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.event.EventResponse;
import com.faw_qm.framework.exceptions.QMException;

/**
 * <p>Title: 物料拆分HTMLAction类。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 王海军
 * @version 1.0
 */
public final class SplitMaterielHTMLAction extends HTMLActionSupport
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
        SplitMaterielEvent splitmaterielEvent = null;
        //action动作。
        String actionType = "";
        actionType = (String) request.getParameter("action1");
        //如果action动作为空则返回空。
        if(actionType == null || actionType.equals(""))
        {
            if(logger.isDebugEnabled())
            {
                logger.debug("perform(HttpServletRequest) - end"); //$NON-NLS-1$
            }
            return null;
        }
        //如果为“拆分”动作。
        if(actionType.equals("split"))
        {
            splitmaterielEvent = new SplitMaterielEvent(
                    SplitMaterielEvent.SPLIT);
            getSplitMessage(request, splitmaterielEvent);
        }
        // 如果为“替换”动作。
        if(actionType.equals("replace"))
        {
            splitmaterielEvent = new SplitMaterielEvent(
                    SplitMaterielEvent.REPLACE);
            getReplaceMessage(request, splitmaterielEvent);
        }
        // 如果为“更新”动作。
        if(actionType.equals("update"))
        {
            splitmaterielEvent = new SplitMaterielEvent(
                    SplitMaterielEvent.UPDATE);
            getUpdateMessage(request, splitmaterielEvent);
        }
        // 如果为“删除”动作。
        if(actionType.equals("delete"))
        {
            splitmaterielEvent = new SplitMaterielEvent(
                    SplitMaterielEvent.DELETE);
            getDeleteMessage(request, splitmaterielEvent);
        }
        //20080103 begin
        //如果为“选择路线”动作。
        if(actionType.equals("choose"))
        {
            splitmaterielEvent = new SplitMaterielEvent(
                    SplitMaterielEvent.CHOOSE);
            getChooseMessage(request, splitmaterielEvent);
        }
        //20080103 end
        //如果为“取消”动作。
        if(actionType.equals("cancle"))
        {
            splitmaterielEvent = new SplitMaterielEvent(
                    SplitMaterielEvent.CANCLE);
        }
        //创建同料尺
        if(actionType.equals("sameMaterial"))
        {
        	splitmaterielEvent=new SplitMaterielEvent(SplitMaterielEvent.SAMEMATERIAL);
        	getSameMaterialMessage(request,splitmaterielEvent);
        	
        }
        //删除同料尺
        if(actionType.equals("deleteSameMaterial"))
        {
        	splitmaterielEvent=new SplitMaterielEvent(SplitMaterielEvent.DELETESAMEMATERIAL);
        	getDeleteSameMaterialMessage(request,splitmaterielEvent);
        	
        }
        //基线发布数据到ERP
        if(actionType.equals("publishByBaseline"))
        {
        	splitmaterielEvent=new SplitMaterielEvent(SplitMaterielEvent.PUBLISHBYBASELINE);
        	getPublishDataByBaseline(request,splitmaterielEvent);
        }
        //路线发布数据到ERP
        if(actionType.equals("publishByRoute"))
        {
        	splitmaterielEvent=new SplitMaterielEvent(SplitMaterielEvent.PUBLISHBYROUTE);
        	getPublishDataByRoute(request,splitmaterielEvent);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("perform(HttpServletRequest) - end"); //$NON-NLS-1$
        }
        return splitmaterielEvent;
    }
    
    public Vector getPublishDataByBaseline(HttpServletRequest request, SplitMaterielEvent splitmaterielEvent)throws HTMLActionException
    {
    	Vector vec1=new Vector();
    	String baselineid=(String)request.getParameter("baselineID");
    	Collection publishCol=new ArrayList();
    	String[] partids=request.getParameterValues("checkboxrefbsoID");
    	if(partids.length==0)
    	{
    		throw new HTMLActionException("没有选择零部件，不能进行数据发布，请选择要发布的零部件");
    	}
    	for(int i =0;i<partids.length;i++)
    	{
    		String partid=partids[i];
    		publishCol.add(partid);
    	}
    	vec1.add(0,baselineid);
    	vec1.add(1,publishCol);
    	splitmaterielEvent.setVector(vec1);
    	return vec1;
    }
    
    public Vector getPublishDataByRoute(HttpServletRequest request, SplitMaterielEvent splitmaterielEvent)throws HTMLActionException
    {
    	Vector vec1=new Vector();
    	Collection publishCol=new ArrayList();
    	String[] partids=request.getParameterValues("checkboxrefbsoID");
    	if(partids.length==0)
    	{
    		throw new HTMLActionException("没有选择路线，不能进行数据发布，请选择要发布的路线");
    	}
    	if(partids.length>1)
    	{
    		throw new HTMLActionException("每次发布只能选择一条路线，请重新选择要发布的路线");
    	}
    	for(int i =0;i<partids.length;i++)
    	{
    		String partid=partids[i];
    		publishCol.add(partid);
    	}
    	vec1.add(0,publishCol);
    	splitmaterielEvent.setVector(vec1);
    	return vec1;
    }
    
    public Vector getDeleteSameMaterialMessage(HttpServletRequest request, SplitMaterielEvent splitmaterielEvent)
    {
    	Vector vec1=new Vector();
    	String[] sameBsoids=request.getParameterValues("deleteBsoID");
    	if (sameBsoids != null) {
			for (int i = 0; i < sameBsoids.length; i++) {
				vec1.add(sameBsoids[i]);
			}
		}
    	splitmaterielEvent.setVector(vec1);
    	return vec1;
    }
    
    public Vector getSameMaterialMessage(HttpServletRequest request,
            SplitMaterielEvent event)
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getSameMaterialMessage(HttpServletRequest, SplitMaterielEvent) - start"); //$NON-NLS-1$
        }
        Vector vector1 = new Vector();
        try
        {
            String partNumber = request.getParameter("partNumber");
            String routeCode = request.getParameter("routeCode");
            String sameMaterialNumber = request.getParameter("sameMaterialNumber");
            vector1.add(0, partNumber);
            vector1.add(1, routeCode);
            vector1.add(2, sameMaterialNumber);
            event.setVector(vector1);
        }
        catch (Exception e)
        {
            logger.error("getSameMaterialMessage(HttpServletRequest)", e); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getSameMaterialMessage(HttpServletRequest, SplitMaterielEvent) - end"); //$NON-NLS-1$
        }
        return vector1;
    }

    /**
     * 该方法从文档请求中获取数据。
     * @param evevt
     * @return com.faw_qm.doc.util.DocFormData
     */
    public Vector getSplitMessage(HttpServletRequest request,
            SplitMaterielEvent event) throws HTMLActionException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getSplitMessage(HttpServletRequest, SplitMaterielEvent) - start"); //$NON-NLS-1$
        }
        Vector data = new Vector();
        //20080103 begin
        String mutPartIDs = request.getParameter("mutPartIds");
        String mutPartRoutes = request.getParameter("mutPartRoutes");
        String singlePartIDs = request.getParameter("singlePartIds");
        String singlePartRoutes = request.getParameter("singlePartRoutes");
        //是否重新拆分。
        String resplit = request.getParameter("resplit");
        //拆分后是立即显示还是通知。
        String splitresult = request.getParameter("splitresult");
        //数据源的BsoId。
        String sourceId = request.getParameter("sourceId");
        String partIDs = null;
        String routes = null;
        if(mutPartIDs != null && !mutPartIDs.trim().equals(""))
        {
            partIDs = mutPartIDs;
            routes = mutPartRoutes;
        }
        else
        {
            partIDs = "";
            routes = "";
        }
        if(singlePartIDs != null && !singlePartIDs.trim().equals(""))
        {
            if(partIDs.equals(""))
            {
                partIDs = singlePartIDs;
                routes = singlePartRoutes;
            }
            else
            {
                partIDs += ";" + singlePartIDs;
                routes += ";" + singlePartRoutes;
            }
        }
        event.setPartIDs(partIDs);
        event.setRoutes(routes);
        event.setResplit(resplit);
        event.setSplitResult(splitresult);
        event.setSourceId(sourceId);
        //20080103 end
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getSplitMessage(HttpServletRequest, SplitMaterielEvent) - end"); //$NON-NLS-1$
        }
        return data;
    }

    public Vector getDeleteMessage(HttpServletRequest request,
            SplitMaterielEvent event)
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getDeleteMessage(HttpServletRequest, SplitMaterielEvent) - start"); //$NON-NLS-1$
        }
        Vector vector1 = new Vector();
        try
        {
            String bsoid = request.getParameter("mateid");
            String partnum = request.getParameter("partnum");
            String parentmatenum = request.getParameter("parentmatenum");
            vector1.add(0, bsoid);
            vector1.add(1, partnum);
            vector1.add(2, parentmatenum);
            event.setVector(vector1);
        }
        catch (Exception e)
        {
            logger.error("deleteGainMessage(HttpServletRequest)", e); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getDeleteMessage(HttpServletRequest, SplitMaterielEvent) - end"); //$NON-NLS-1$
        }
        return vector1;
    }

    public Vector getUpdateMessage(HttpServletRequest request,
            SplitMaterielEvent event)
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getUpdateMessage(HttpServletRequest, SplitMaterielEvent) - start"); //$NON-NLS-1$
        }
        Vector updateata = new Vector();
        int value_count = 0;
        String count = (String) request.getParameter("count");
        String partnum = (String) request.getParameter("partnum");
        if(count != null && count.trim().length() > 0)
        {
            value_count = (new Integer(count)).intValue();
        }
        String bsoid;
        String textFieldOld;
        String textField;
        HashMap updatMap = new HashMap();
        for (int i = 0; i < value_count; i++)
        {
            bsoid = (String) request.getParameter("splitid" + i);
            textField = (String) request.getParameter("textfield" + i);
            textFieldOld = (String) request.getParameter("oldtext" + i);
            if(!textField.equals(textFieldOld))
            {
                updatMap.put(bsoid, textField);
            }
        }
        updateata.add(0, updatMap);
        event.setVector(updateata);
        event.setPartid(partnum);
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getUpdateMessage(HttpServletRequest, SplitMaterielEvent) - end"); //$NON-NLS-1$
        }
        return updateata;
    }

    public Vector getReplaceMessage(HttpServletRequest request,
            SplitMaterielEvent event)
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getReplaceMessage(HttpServletRequest, SplitMaterielEvent) - start"); //$NON-NLS-1$
        }
        Vector vector1 = new Vector();
        String partnum = request.getParameter("partnum");
        String mateselect = request.getParameter("mateid");
        String mateoldid = request.getParameter("mateoldid");
        String parentmatenum = request.getParameter("parentmatenum");
        String partnumbers = request.getParameter("partnumbers");
        vector1.add(0, partnum);
        vector1.add(1, mateselect);
        vector1.add(2, mateoldid);
        vector1.add(3, parentmatenum);
        vector1.add(4, partnumbers);
        event.setVector(vector1);
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getReplaceMessage(HttpServletRequest, SplitMaterielEvent) - end"); //$NON-NLS-1$
        }
        return vector1;
    }

    //20080103 begin
    public Vector getChooseMessage(HttpServletRequest request,
            SplitMaterielEvent event) throws HTMLActionException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getSplitMessage(HttpServletRequest, SplitMaterielEvent) - start"); //$NON-NLS-1$
        }
        Vector data = new Vector();
        String needsplitparts = request.getParameter("needsplitparts");
        //是否重新拆分。
        String resplit = request.getParameter("resplit");
        //拆分后是立即显示还是通知。
        String splitresult = request.getParameter("splitresult");
        String caiyongid = request.getParameter("caiyid");
        String noticeid  = request.getParameter("noticeid");;
        //数据源的BsoId
        if((noticeid == null || noticeid.length() <= 0)
                && (caiyongid != null && caiyongid.length() > 0)){
        	
        	event.setSourceId(caiyongid);
        }
        else if((caiyongid == null || caiyongid.length() <= 0)
                && (noticeid != null && noticeid.length() > 0))
        {
        	event.setSourceId(noticeid);
        }
        else{
        	event.setSourceId("");
        }
        event.setPartIDs(needsplitparts);
        event.setResplit(resplit);
        event.setSplitResult(splitresult);
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getSplitMessage(HttpServletRequest, SplitMaterielEvent) - end"); //$NON-NLS-1$
        }
        return data;
    }
    //20080103 end
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
        if(eventResponse != null)
        {
            if(eventResponse instanceof SplitMaterielEventResponse)
            {
                SplitMaterielEventResponse ber = (SplitMaterielEventResponse) eventResponse;
                String id = ber.getPartIDs();
                request.setAttribute("partids", id);
                request.setAttribute("partnumbers", id);
                String partid = ber.getPartid();
                if(partid != null)
                {
                    request.setAttribute("partnum", partid);
                }
                //20080103 begin
                String resplit=ber.getResplit();
                if(resplit != null) {
                    request.setAttribute("resplit", resplit);
                }
                String splitResult=ber.getShow();
                if(splitResult != null) {
                    request.setAttribute("splitResult", splitResult);
                }
                //20080103 end
                String sourceid=ber.getSourceId();
                if(sourceid != null) {
                    request.setAttribute("sourceid", sourceid);
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
