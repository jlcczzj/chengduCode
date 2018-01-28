/** 生成程序PartHTMLAction.java	  1.0  2003/05/10
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
    /**序列化ID*/
    static final long serialVersionUID = 1L;

    /**
     * 执行。
     * @param request HttpServletRequest
     * @throws HTMLActionException
     * @return Event
     */
    public Event perform(HttpServletRequest request)
            throws HTMLActionException
    {
        //  webaction和ejbaction之间的过渡类
        PartEvent partEvent = null;
        //  获得action动作
        String actionType = (String) request.getParameter("action1");
        System.out.println("actionType===="+actionType);
        //  如果action动作为空则返回空DeleteAlterPart
        if (actionType == null)
        {
            return null;
        }
        //  如果为“添加替换件”动作
        if (actionType.equals("addAlter"))
        {
            Vector vector = gainAddAlterMessage(request);
            partEvent = new PartEvent(PartEvent.ADDAlter, vector);
        }
        //  如果为“添加结构替换件”动作
        if (actionType.equals("addStruAlter"))
        {
            Vector vector = gainAddStruAlterMessage(request);
            partEvent = new PartEvent(PartEvent.ADDStruAlter, vector);
        }
        //如果为“删除替换件”动作
        if (actionType.equals("DeleteAlterPart"))
        {
            Vector vector = gainDeleteAlterPartMessage(request);
            partEvent = new PartEvent(PartEvent.DELETEAlterPart, vector);
        }
        //如果为“删除结构替换件”动作
        if (actionType.equals("delStruAlterPart"))
        {
            Vector vector = gainDelStruAlterPartMessage(request);
            partEvent = new PartEvent(PartEvent.DELETEStruAlterPart, vector);
        }
        //如果为“添加被替换件”动作
        if (actionType.equals("addBeAlteredPart"))
        {
            Vector vector = gainAddBeAlteredPartMessage(request);
            partEvent = new PartEvent(PartEvent.ADDBeAlteredPart, vector);
        }
        //如果为“保存配置规范”动作
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
        //如果为“查看使用结构”动作
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
      //如果为“查看试制结构”动作
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
     * 刷新查看属性界面中“使用结构”的树。
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
     * 获取新配置规则中的BaselineID。
     * @param baselineName String 基准线名称。
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
     * 获取新配置规则中的ConfigItemID。
     * @param configItemName String 配置项名称。
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
     * 获取新配置规则中的viewObjectID。
     * @param viewName String 视图名称。
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
     * 获取保存配置规则的信息。
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

        //获取baselineIDd
        String baselineName = (String) request.getParameter("baselinename");
        String baselineID = getBaselineID(baselineName);
        vector.addElement(baselineID);
        //获取ConfigItemID
        String effname = (String) request.getParameter("effname");
        String configItemID = getConfigItemID(effname);
        vector.addElement(configItemID);
        //获取viewObjectID
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
     * 生命周期状态转换。
     * @param s String 英文信息。
     * @return String 本地化后的信息。
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
     * 转换有效性类型。
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
        //20080401 zhangq begin:当没有选择有效性类型时，设置为日期类型。
        if(type == null) {
            type=EffectivityType.DATE.toString();
        }
        //20080401 zhangq end
        return type;
    }


    //end add by 孙贤民

    /**
     * 获取添加替换件信息。
     * @param request HttpServletRequest
     * @return Vector
     */
    public Vector gainAddAlterMessage(HttpServletRequest request)
    {
        //新建一个Vector对象
        Vector vector1 = new Vector();
        //获得界面上复选框的个数
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
        //获取源零部件的bsoID
        String sourcepart = (String) request.getParameter("bsoID");
        //将他加入到vector中
        vector1.add(0, sourcepart);
        //对各个复选框做一个循环，获得被选中的复选框的值，在jsp页面中已经将该条记录的值赋给了复选框，并将它加入vector中
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
     * 获取添加结构替换件信息。
     * @param  request HttpServletRequest
     * @return Vector
     */
    public Vector gainAddStruAlterMessage(HttpServletRequest request)
    {
        //新建一个Vector对象
        Vector vector1 = new Vector();
        //获得源零部件的bsoID
        String partBsoID = request.getParameter("childID");
        //获得结构替换件的bsoID
        String structPartBsoID = request.getParameter("parentID");
        String usageLinkID = request.getParameter("usageLinkID");
        //获得界面上复选框的个数
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
        //对各个复选框做一个循环，获得被选中的复选框的值，在jsp页面中已经将该条记录的bsoID值赋给了复选框，并将它加入vector中
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
     * 获取删除替换件信息。
     * @param  request HttpServletRequest
     * @return Vector
     */
    public Vector gainDeleteAlterPartMessage(HttpServletRequest request)
    {

        //新建一个Vector对象
        Vector vector1 = new Vector();
        //获得源零部件的bsoID
        String partBsoID = request.getParameter("bsoID");

        //将partBsoID放做vector的第一个元素中
        vector1.add(0, partBsoID);
        //获得界面上复选框的个数

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
        //对各个复选框做循环，获得所对应的各项的bsoID
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
     * 获取删除的结构替换件信息。
     * @param request HttpServletRequest
     * @return Vector
     */
    public Vector gainDelStruAlterPartMessage(HttpServletRequest request)
    {
        //新建一个Vector对象
        Vector vector1 = new Vector();
        //获得源零部件的bsoID
        String partBsoID = request.getParameter("childID");
        //获得子件和父件间的使用关联的bsoID
        String usageLinkID = request.getParameter("usageLinkID");
        //将原零部件和结构零部件的bsoID分别放入数组中的第一和第二个元素中
        vector1.add(0, partBsoID);
        vector1.add(1, usageLinkID);
        //获得界面上复选框的个数
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
        //对截面中的复选框进行循环，获得选中的每个复选框对应的对象的bsoID
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
     * 获取添加的被替换件信息。
     * @param request HttpServletRequest
     * @return Vector
     */
    public Vector gainAddBeAlteredPartMessage(HttpServletRequest request)
    {
        //新建一个Vector对象
        Vector vector1 = new Vector();
        //获得源零部件的bsoID
        String partBsoID = (String) request.getParameter("bsoID");
        //将该bsoID值赋值给vector1的第一个元素
        vector1.add(0, partBsoID);
        //获得界面上复选框的个数
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
        //对截面中的复选框进行循环，获得选中的每个复选框对应的对象的bsoID
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
