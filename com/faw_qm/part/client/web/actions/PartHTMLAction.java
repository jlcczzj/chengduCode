/** ���ɳ���PartHTMLAction.java	  1.0  2003/05/10
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.part.client.web.actions;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 */
import java.util.Collection;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.effectivity.model.QMConfigurationItemIfc;
import com.faw_qm.effectivity.util.EffectivityType;
import com.faw_qm.framework.controller.web.action.HTMLActionException;
import com.faw_qm.framework.controller.web.action.HTMLActionSupport;
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.framework.event.EventResponse;


/**
 * Implementation of HTMLAction that processes a
 * user change in language.
 */
public final class PartHTMLAction extends HTMLActionSupport
{
    /**���л�ID*/
    static final long serialVersionUID = 1L;

    /**
     * ִ�С�
     * @param request HttpServletRequest
     * @throws HTMLActionException
     * @return Event
     */
    public Event perform(HttpServletRequest request)
            throws HTMLActionException
    {
        //  webaction��ejbaction֮��Ĺ�����
        PartEvent partEvent = null;
        //  ���action����
        String actionType = (String) request.getParameter("action1");
        System.out.println("actionType===="+actionType);
        //  ���action����Ϊ���򷵻ؿ�DeleteAlterPart
        if (actionType == null)
        {
            return null;
        }
        //  ���Ϊ������滻��������
        if (actionType.equals("addAlter"))
        {
            Vector vector = gainAddAlterMessage(request);
            partEvent = new PartEvent(PartEvent.ADDAlter, vector);
        }
        //  ���Ϊ����ӽṹ�滻��������
        if (actionType.equals("addStruAlter"))
        {
            Vector vector = gainAddStruAlterMessage(request);
            partEvent = new PartEvent(PartEvent.ADDStruAlter, vector);
        }
        //���Ϊ��ɾ���滻��������
        if (actionType.equals("DeleteAlterPart"))
        {
            Vector vector = gainDeleteAlterPartMessage(request);
            partEvent = new PartEvent(PartEvent.DELETEAlterPart, vector);
        }
        //���Ϊ��ɾ���ṹ�滻��������
        if (actionType.equals("delStruAlterPart"))
        {
            Vector vector = gainDelStruAlterPartMessage(request);
            partEvent = new PartEvent(PartEvent.DELETEStruAlterPart, vector);
        }
        //���Ϊ����ӱ��滻��������
        if (actionType.equals("addBeAlteredPart"))
        {
            Vector vector = gainAddBeAlteredPartMessage(request);
            partEvent = new PartEvent(PartEvent.ADDBeAlteredPart, vector);
        }
        //���Ϊ���������ù淶������
        if (actionType.equalsIgnoreCase("SavePartConfigSpec"))
        {
            try
            {
                Vector vector = gainSavePartConfigSpecMessage(request);
                partEvent = new PartEvent(PartEvent.SavePartConfigSpec, vector);
            }
            catch (QMException e)
            {
                throw new HTMLActionException(e.getMessage());
            }
        }
        //���Ϊ���鿴ʹ�ýṹ������
        if (actionType.equalsIgnoreCase("part_structure"))
        {
            try
            {
                Vector vector = gainViewStructureMessage(request);
                partEvent = new PartEvent(PartEvent.ViewStructure, vector);
            }
            catch (QMException e)
            {
                throw new HTMLActionException(e.getMessage());
            }
        }
        
      //shizhi add by lis begin
      //���Ϊ���鿴���ƽṹ������
        if (actionType.equalsIgnoreCase("shizhipart_structure"))
        {
            try
            {
                Vector vector = gainViewStructureMessage(request);
                System.out.println("vector===="+vector);
                partEvent = new PartEvent(PartEvent.ShizhiStructure, vector);
                System.out.println("partEvent===="+partEvent);
            }
            catch (QMException e)
            {
                throw new HTMLActionException(e.getMessage());
            }
        }
      //shizhi add by lis end
        return partEvent;
    }


    /**
     * ˢ�²鿴���Խ����С�ʹ�ýṹ��������
     * @param request HttpServletRequest
     * @throws HTMLActionException
     * @return Vector
     */
    private Vector gainViewStructureMessage(HttpServletRequest request)throws HTMLActionException
    {
        Vector vec = new Vector();
        //String node_count = (String)request.getParameter("node_count");
        String rootID = (String)request.getParameter("bsoID");
        String currentNode = (String)request.getParameter("currentNode");
        String flag = (String)request.getParameter("flag");

        //vec.add(0,node_count);
        vec.add(0,rootID);
        vec.add(1,currentNode);
        vec.add(2,flag);

        //int nodeNum = Integer.parseInt(node_count);
        for(int i=0;;i++)
        {
            String node = "Node";
            node = node+Integer.toString(i);
            String partAndLinkID = (String)request.getParameter(node);
            if(partAndLinkID==null||partAndLinkID.equals(""))
            {
            	break;
            }
            else
            {
                vec.add(i+3,partAndLinkID);
            }

        }

        return vec;
    }
    /**
     * ��ȡ�����ù����е�BaselineID��
     * @param baselineName String ��׼�����ơ�
     * @return String
     * @throws QMException
     */
    private String getBaselineID(String baselineName)
            throws QMException
    {

        if (baselineName == null)
        {
            return null;
        }
        QMQuery query = new QMQuery("ManagedBaseline");
        QueryCondition qc = new QueryCondition("baselineName",
                                               QueryCondition.EQUAL,
                                               baselineName);
        query.addCondition(qc);
        PersistService ps = (PersistService) EJBServiceHelper.getService(
                "PersistService");
        Collection baseline = (Collection) ps.findValueInfo(query);
        ManagedBaselineIfc baselineIfc = null;
        if (baseline.size() > 0)
        {
            baselineIfc = (ManagedBaselineIfc) baseline.iterator().next();
            return baselineIfc.getBsoID();
        }
        return null;
    }


    /**
     * ��ȡ�����ù����е�ConfigItemID��
     * @param configItemName String ���������ơ�
     * @return String
     * @throws QMException
     */
    private String getConfigItemID(String configItemName)
            throws QMException
    {
        if (configItemName == null)
        {
            return null;
        }
        QMQuery query = new QMQuery("QMConfigurationItem");
        QueryCondition qc = new QueryCondition("configItemName",
                                               QueryCondition.EQUAL,
                                               configItemName);
        query.addCondition(qc);
        PersistService ps = (PersistService) EJBServiceHelper.getService(
                "PersistService");
        Collection collection = (Collection) ps.findValueInfo(query);
        if (collection.size() > 0)
        {
            QMConfigurationItemIfc Ifc = (QMConfigurationItemIfc) collection.
                                           iterator().next();
            return Ifc.getBsoID();
        }
        return null;
    }


    /**
     * ��ȡ�����ù����е�viewObjectID��
     * @param viewName String ��ͼ���ơ�
     * @return String
     * @throws QMException
     */
    private String getViewObjectID(String viewName)
            throws QMException
    {
        if (viewName == null)
        {
            return null;
        }
        QMQuery query = new QMQuery("ViewObject");
        QueryCondition qc = new QueryCondition("viewName", QueryCondition.EQUAL,
                                               viewName);
        query.addCondition(qc);
        PersistService ps = (PersistService) EJBServiceHelper.getService(
                "PersistService");
        Collection collection = (Collection) ps.findValueInfo(query);
        if (collection.size() > 0)
        {
            ViewObjectIfc Ifc = (ViewObjectIfc) collection.iterator().next();
            return Ifc.getBsoID();
        }
        return null;
    }


    /**
     * ��ȡ�������ù������Ϣ��
     * @param request HttpServletRequest
     * @return Vector
     * @throws QMException
     */
    public Vector gainSavePartConfigSpecMessage(HttpServletRequest request)
            throws QMException
    {
        Vector vector = new Vector();
        String configActive = (String) request.getParameter("configActive");
        String effActive = "0";
        String baselineActive = "0";
        String standardActive = "0";
        if (configActive != null)
        {
            if (configActive.equals("standardActive"))
            {
                standardActive = "1";
            }
            else if (configActive.equals("baselineActive"))
            {
                baselineActive = "1";
            }
            else
            {
                effActive = "1";
            }
        }
        vector.addElement(effActive);
        vector.addElement(baselineActive);
        vector.addElement(standardActive);

        //��ȡbaselineIDd
        String baselineName = (String) request.getParameter("baselinename");
        String baselineID = getBaselineID(baselineName);
        vector.addElement(baselineID);
        //��ȡConfigItemID
        String effname = (String) request.getParameter("effname");
        String configItemID = getConfigItemID(effname);
        vector.addElement(configItemID);
        //��ȡviewObjectID
        String standardView = (String) request.getParameter("standardView");
        String effview = (String) request.getParameter("effview");
        String viewObjectID = null;
        if (effActive == "1")
        {
            viewObjectID = getViewObjectID(effview);
        }
        else if (standardActive == "1")
        {
            viewObjectID = getViewObjectID(standardView);
        }
        vector.addElement(viewObjectID);
        String effectivityType = (String) request.getParameter("effType");
        effectivityType = toEffectivityType(effectivityType);
        vector.addElement(effectivityType);
        String effectivityUnit = (String) request.getParameter("effValue");
        vector.addElement(effectivityUnit);
        String state = (String) request.getParameter("state");
        state = toState(state);
        vector.addElement(state);
        String workingIncluded = (String) request.getParameter(
                "workingIncluded");
        if (workingIncluded != null && workingIncluded.equals("on"))
        {
            workingIncluded = "0";
        }
        else
        {
            workingIncluded = "1";
        }
        vector.add(workingIncluded);
        String bsoID = (String) request.getParameter("bsoID");
        vector.add(bsoID);
        return vector;
    }

    /**
     * ��������״̬ת����
     * @param s String Ӣ����Ϣ��
     * @return String ���ػ������Ϣ��
     */
    private String toState(String s)
    {
        LifeCycleState[] allState = LifeCycleState.getLifeCycleStateSet();
        Locale locale = RemoteProperty.getVersionLocale();
        String state = null;
        if (s != null)
        {
            for (int i = 0; i < allState.length; i++)
            {
                if (s.equals(allState[i].getLocalizedMessage(locale)))
                {
                    state = allState[i].toString();
                    break;
                }
            }
        }
        return state;
    }

    /**
     * ת����Ч�����͡�
     * @param s String
     * @return String
     */
    private String toEffectivityType(String s)
    {
        EffectivityType[] effectivityType = EffectivityType.
                                            getEffectivityTypeSet();
        Locale locale = RemoteProperty.getVersionLocale();
        String type = null;
        if (s != null)
        {
            for (int i = 0; i < effectivityType.length; i++)
            {
                if (s.equals(effectivityType[i].getLocalizedMessage(locale)))
                {
                    type = effectivityType[i].toString();
                    break;
                }
            }
        }
        //20080401 zhangq begin:��û��ѡ����Ч������ʱ������Ϊ�������͡�
        if(type == null) {
            type=EffectivityType.DATE.toString();
        }
        //20080401 zhangq end
        return type;
    }


    //end add by ������

    /**
     * ��ȡ����滻����Ϣ��
     * @param request HttpServletRequest
     * @return Vector
     */
    public Vector gainAddAlterMessage(HttpServletRequest request)
    {
        //�½�һ��Vector����
        Vector vector1 = new Vector();
        //��ý����ϸ�ѡ��ĸ���
        int count;
        try
        {
            count = (Integer.valueOf((String) request.getParameter("count"))).
                    intValue();
        }
        catch (Exception e)
        {
            return vector1;
        }
        if (count == 0)
        {
            return vector1;
        }
        //��ȡԴ�㲿����bsoID
        String sourcepart = (String) request.getParameter("bsoID");
        //�������뵽vector��
        vector1.add(0, sourcepart);
        //�Ը�����ѡ����һ��ѭ������ñ�ѡ�еĸ�ѡ���ֵ����jspҳ�����Ѿ���������¼��ֵ�����˸�ѡ�򣬲���������vector��
        for (int i = 0; i < count; i++)
        {
            String aa = request.getParameter("checkbox" + String.valueOf(i));
            if (aa != null)
            {
                vector1.addElement(aa);
            }
        }
        return vector1;
    }


    /**
     * ��ȡ��ӽṹ�滻����Ϣ��
     * @param  request HttpServletRequest
     * @return Vector
     */
    public Vector gainAddStruAlterMessage(HttpServletRequest request)
    {
        //�½�һ��Vector����
        Vector vector1 = new Vector();
        //���Դ�㲿����bsoID
        String partBsoID = request.getParameter("childID");
        //��ýṹ�滻����bsoID
        String structPartBsoID = request.getParameter("parentID");
        String usageLinkID = request.getParameter("usageLinkID");
        //��ý����ϸ�ѡ��ĸ���
        vector1.add(0, partBsoID);
        vector1.add(1, structPartBsoID);
        vector1.add(2, usageLinkID);
        int count;
        try
        {
            count = (Integer.valueOf((String) request.getParameter("count"))).
                    intValue();
        }
        catch (Exception e)
        {
            return vector1;
        }
        if (count == 0)
        {
            return vector1;
        }
        //�Ը�����ѡ����һ��ѭ������ñ�ѡ�еĸ�ѡ���ֵ����jspҳ�����Ѿ���������¼��bsoIDֵ�����˸�ѡ�򣬲���������vector��
        for (int i = 0; i < count; i++)
        {
            String aa = request.getParameter("checkbox" + String.valueOf(i));
            if (aa != null)
            {
                vector1.addElement(aa);
            }
        }
        return vector1;
    }


    /**
     * ��ȡɾ���滻����Ϣ��
     * @param  request HttpServletRequest
     * @return Vector
     */
    public Vector gainDeleteAlterPartMessage(HttpServletRequest request)
    {

        //�½�һ��Vector����
        Vector vector1 = new Vector();
        //���Դ�㲿����bsoID
        String partBsoID = request.getParameter("bsoID");

        //��partBsoID����vector�ĵ�һ��Ԫ����
        vector1.add(0, partBsoID);
        //��ý����ϸ�ѡ��ĸ���

        int count;
        try
        {
            count = (Integer.valueOf((String) request.getParameter("count"))).
                    intValue();
        }
        catch (Exception e)
        {
            return vector1;
        }
        if (count == 0)
        {
            return vector1;
        }
        //�Ը�����ѡ����ѭ�����������Ӧ�ĸ����bsoID
        for (int i = 0; i < count; i++)
        {
            String aa = request.getParameter("checkboxSeleDel" +
                                             String.valueOf(i));
            if (aa != null)
            {
                vector1.addElement(aa);
            }
        }
        return vector1;
    }

    /**
     * ��ȡɾ���Ľṹ�滻����Ϣ��
     * @param request HttpServletRequest
     * @return Vector
     */
    public Vector gainDelStruAlterPartMessage(HttpServletRequest request)
    {
        //�½�һ��Vector����
        Vector vector1 = new Vector();
        //���Դ�㲿����bsoID
        String partBsoID = request.getParameter("childID");
        //����Ӽ��͸������ʹ�ù�����bsoID
        String usageLinkID = request.getParameter("usageLinkID");
        //��ԭ�㲿���ͽṹ�㲿����bsoID�ֱ���������еĵ�һ�͵ڶ���Ԫ����
        vector1.add(0, partBsoID);
        vector1.add(1, usageLinkID);
        //��ý����ϸ�ѡ��ĸ���
        int count;
        try
        {
            count = (Integer.valueOf((String) request.getParameter("count"))).
                    intValue();
        }
        catch (Exception e)
        {
            return vector1;
        }
        if (count == 0)
        {
            return vector1;
        }
        //�Խ����еĸ�ѡ�����ѭ�������ѡ�е�ÿ����ѡ���Ӧ�Ķ����bsoID
        for (int i = 0; i < count; i++)
        {
            String aa = request.getParameter("checkboxdel" + String.valueOf(i));
            if (aa != null)
            {
                vector1.addElement(aa);
            }
        }
        return vector1;
    }

    /**
     * ��ȡ��ӵı��滻����Ϣ��
     * @param request HttpServletRequest
     * @return Vector
     */
    public Vector gainAddBeAlteredPartMessage(HttpServletRequest request)
    {
        //�½�һ��Vector����
        Vector vector1 = new Vector();
        //���Դ�㲿����bsoID
        String partBsoID = (String) request.getParameter("bsoID");
        //����bsoIDֵ��ֵ��vector1�ĵ�һ��Ԫ��
        vector1.add(0, partBsoID);
        //��ý����ϸ�ѡ��ĸ���
        int count;
        try
        {
            count = (Integer.valueOf((String) request.getParameter("count"))).
                    intValue();
        }
        catch (Exception e)
        {
            return vector1;
        }
        if (count == 0)
        {
            return vector1;
        }
        //�Խ����еĸ�ѡ�����ѭ�������ѡ�е�ÿ����ѡ���Ӧ�Ķ����bsoID
        for (int i = 0; i < count; i++)
        {
            String aa = request.getParameter("checkboxDoub" + String.valueOf(i));
            if (aa != null)
            {
                vector1.addElement(aa);
            }
        }
        return vector1;
    }

    public void doEnd(HttpServletRequest request, EventResponse eventResponse)
    {
    	PartResponse response = (PartResponse)eventResponse;
        String actionType = (String) request.getParameter("action1");

        if((actionType!=null) && (actionType.length()>0))
        {
            if (actionType.equalsIgnoreCase("part_structure"))
            {

                Vector vec = (Vector) response.getVec();

                if (vec != null)
                {

                    request.setAttribute("vec", vec);

                } //endif
            }
            //shizhi add by lis begin
            if (actionType.equalsIgnoreCase("shizhipart_structure"))
            {

                Vector vec = (Vector) response.getVec();

                if (vec != null)
                {

                    request.setAttribute("vec", vec);

                } //endif
            }
            //shizhi add by lis end
        }
    }
}
