/** 生成程序PartEJBAction.java	  1.0  2003/05/10
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
 * @author (correct by 孙贤民)
 * @version 1.0
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.framework.controller.ejb.action.EJBActionSupport;
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.event.EventException;
import com.faw_qm.framework.event.EventResponse;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybom.util.GYBomHelper;
import com.faw_qm.part.model.PartAlternateLinkInfo;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartSubstituteLinkIfc;
import com.faw_qm.part.model.PartSubstituteLinkInfo;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.part.model.PartAlternateLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.NormalPart;
import com.faw_qm.part.util.PartServiceHelper;

public class PartEJBAction extends EJBActionSupport
{
    /**序列化ID*/
    static final long serialVersionUID = 1L;

    /**
     * 执行瘦客户端的.do操作。
     * @param e Event
     * @return EventResponse
     * @throws EventException
     */
    public EventResponse perform(Event e)
            throws EventException
    {
        PartEvent de = (PartEvent) e;
        System.out.println("de.getEventName()===="+de.getEventName());
        Vector vector = de.getVector();
        PartResponse response = new PartResponse(de.getEventName(),
                        vector);
        try
        {
            //当事件类型是SavePartConfigSpec(add by孙贤民)
            if (de.getActionType() == PartEvent.SavePartConfigSpec)
            {

                String[] string = new String[11];
                for (int i = 0; i < 11; i++)
                {
                    string[i] = (String) vector.elementAt(i);
                }
                PartConfigSpecIfc partConfigSpecIfc = PartServiceRequest.
                        hashtableToPartConfigSpecIfc(string[0], string[1],
                        string[2], string[3], string[4], string[5], string[6],
                        string[7], string[8], string[9]);
                PartServiceRequest.savePartConfigSpecIfc(partConfigSpecIfc);
            }
            ///Part start
            if (de.getActionType() == PartEvent.ADDAlter)
            {

                if (vector != null && vector.size() > 0)
                {
                    int len = vector.size();
                    String sourcePartID = (String) vector.get(0);
                    PersistService aa = (PersistService) EJBServiceHelper.
                                        getService("PersistService");
                    QMPartMasterIfc partIfc = (QMPartMasterIfc) aa.refreshInfo(
                            sourcePartID, false);
                    Collection collection = PartServiceRequest.
                                            getAlternatesPartMasters(partIfc);
                    Vector vector1 = new Vector(collection);
                    Vector vector2 = new Vector();
                    for (int y = 0; y < vector1.size(); y++)
                    {
                        QMPartMasterIfc ifc = (QMPartMasterIfc) vector1.get(y);
                        String id = (String) ifc.getBsoID();
                        vector2.add(id);
                    }
                    for (int i = 1; i < len; i++)
                    {
                        boolean flag = true;
                        for (int xx = 0; xx < vector2.size(); xx++)
                        {
                            String yiid = (String) vector2.get(xx);
                            if (yiid.equals(vector.get(i)))
                            {
                                flag = false;
                            }
                        }
                        if (flag && !sourcePartID.equals((String) vector.get(i)))
                        {
                            PartAlternateLinkIfc linkIIfc = new
                                    PartAlternateLinkInfo();
                            linkIIfc.setLeftBsoID((String) vector.get(i));
                            linkIIfc.setRightBsoID((String) sourcePartID);
                            aa.saveBso(linkIIfc);
                        }
                    }
                }
            }
            if (de.getActionType() == PartEvent.ADDStruAlter)
            {

                if (vector != null && vector.size() > 0)
                {
                    String sourcePartID = (String) vector.get(0);
                    String sourceStruPartID = (String) vector.get(1);
                    String usageLinkID = (String) vector.get(2);
                    PersistService aa = (PersistService) EJBServiceHelper.
                                        getService("PersistService");
                    PartUsageLinkIfc usageLinkIfc = (PartUsageLinkIfc) aa.
                            refreshInfo(usageLinkID);
                    Collection coll = PartServiceRequest.
                                      getSubstitutesPartMasters(usageLinkIfc);
                    Vector vec = new Vector(coll);
                    Vector vec2 = new Vector();
                    for (int y = 0; y < vec.size(); y++)
                    {
                        QMPartMasterIfc ifc = (QMPartMasterIfc) vec.get(y);
                        String id = (String) ifc.getBsoID();
                        vec2.add(id);
                    }
                    for (int i = 3; i < vector.size(); i++)
                    {
                        boolean flag = true;
                        for (int xx = 0; xx < vec2.size(); xx++)
                        {
                            String yiid = (String) vec2.get(xx);
                            if (yiid.equals(vector.get(i)))
                            {
                                flag = false;
                            }
                        }
                        if (flag && !sourcePartID.equals((String) vector.get(i)))
                        {
                            PartSubstituteLinkIfc linkIfc = new
                                    PartSubstituteLinkInfo();
                            //设置他的左边（即substitutes）
                            linkIfc.setLeftBsoID((String) vector.get(i));
                            //设置substituteFor
                            linkIfc.setRightBsoID(usageLinkIfc.getBsoID());
                            //将PartSubstituteLink对象linkIfc存入数据库中
                            aa.saveBso(linkIfc);
                        }
                    }
                }
            }
            if (de.getActionType() == PartEvent.DELETEAlterPart)
            {

                if (vector != null && vector.size() > 0)
                {
                    String sourcePartID = (String) vector.get(0);
                    PersistService aa = (PersistService) EJBServiceHelper.
                                        getService("PersistService");
                    //循环操作获得原零部件和各个结构替换件之间的关联后将各个关联从数据库中删除
                    for (int i = 1; i < vector.size(); i++)
                    {
                        //获得叔祖中的元素
                        String aaa = (String) vector.get(i);
                        //构造启明查询
                        QMQuery query = new QMQuery("PartAlternateLink");
                        //构造查询条件1
                        QueryCondition condition1 = new QueryCondition(
                                "leftBsoID", "=", aaa);
                        //添加查询条件1
                        query.addCondition(condition1);
                        //添加and字符
                        query.addAND();
                        //设置查询条件2
                        QueryCondition condition2 = new QueryCondition(
                                "rightBsoID", "=", sourcePartID);
                        //添加查询条件2
                        query.addCondition(condition2);
                        //调用持久化服务进行查询，获得返回值
                        Collection collection = aa.findValueInfo(query);
                        //将返回值获得并将其删除
                        Object[] quetydata = collection.toArray();
                        //调用持久化服务将该对象删除
                        aa.deleteValueInfo((BaseValueIfc) quetydata[0]);
                    }
                }
            }
            if (de.getActionType() == PartEvent.DELETEStruAlterPart)
            {

                if (vector != null && vector.size() > 0)
                {
                    String sourcePartID = (String) vector.get(0);
                    //获取源结构零部件
                    String sourceStructPartID = (String) vector.get(1);
                    //获取持久化服务
                    PersistService aa = (PersistService) EJBServiceHelper.
                                        getService("PersistService");
                    String sublinkID = (String) vector.get(1);
                    for (int i = 2; i < vector.size(); i++)
                    {
                        String partMasterID = (String) vector.get(i);
                        Collection linkresult = (Collection) aa.
                                                findLinkValueInfo(
                                "PartSubstituteLink", partMasterID,
                                "substitutes", sublinkID);
                        Iterator iterator = (Iterator) linkresult.iterator();
                        while (iterator != null && iterator.hasNext())
                        {
                            PartSubstituteLinkIfc sublinkifc = (
                                    PartSubstituteLinkIfc) iterator.next();
                            aa.deleteValueInfo(sublinkifc);
                        }
                    }
                }
            }
            if (de.getActionType() == PartEvent.ADDBeAlteredPart)
            {

                if (vector != null && vector.size() > 0)
                {
                    String sourcePartID = (String) vector.get(0);
                    //获取持久化服务
                    PersistService aa = (PersistService) EJBServiceHelper.
                                        getService("PersistService");
                    QMPartMasterIfc part = (QMPartMasterIfc) aa.refreshInfo(
                            sourcePartID);
                    Collection co = PartServiceRequest.
                                    getAlternateForPartMasters(part);
                    Vector ve = new Vector(co);
                    Vector ve2 = new Vector();
                    for (int y = 0; y < ve.size(); y++)
                    {
                        QMPartMasterIfc ifc = (QMPartMasterIfc) ve.get(y);
                        String id = ifc.getBsoID();
                        ve2.add(id);
                    }
                    //循环对vector中的每一个元素都设置为源零部件的被替换件
                    for (int i = 1; i < vector.size(); i++)
                    {
                        boolean flag = true;
                        for (int xx = 0; xx < ve2.size(); xx++)
                        {
                            String yiid = (String) ve2.get(xx);
                            if (yiid.equals(vector.get(i)))
                            {
                                flag = false;
                            }
                        }
                        if (flag && !sourcePartID.equals((String) vector.get(i)))
                        {
                            //新建一个关联对象
                            PartAlternateLinkIfc linkIfc = new
                                    PartAlternateLinkInfo();
                            //设置该对象的替换件
                            linkIfc.setLeftBsoID(sourcePartID);
                            //设置该对象的被替换件
                            linkIfc.setRightBsoID((String) vector.get(i));
                            //利用持久化服务将该对象存入数据库中
                            aa.saveBso(linkIfc);
                        }
                    }
                }
            }
            if (de.getActionType() == PartEvent.ViewStructure)
            {

                PersistService pservice = (PersistService) EJBServiceHelper.
                                          getPersistService();
                PartServiceHelper pshelper= new PartServiceHelper();

                //String node_count = (String) vector.elementAt(0);
                String rootID = (String) vector.elementAt(0);
                String currentNode = (String) vector.elementAt(1);
                String flag = (String) vector.elementAt(2);

                //String node0 = (String) vector.elementAt(3);


                BaseValueIfc currentPartIfc = pservice.refreshInfo(currentNode);

                //int nodeNum = Integer.parseInt(node_count);
                //node = node+Integer.toString(nodeNum);

                Vector result = new Vector();
                
                //判断是否是展开节点操作。flag为0时是节点展开操作，flag为1时是节点关闭操作。
                if (flag.equals("0"))
                {


                    if(currentNode.equals(rootID))
                    {

                }
                else
                {

                    for (int i = 4;i<vector.size(); i++)
                    {
                        String partAndLinkID = (String) vector.elementAt(i);

                        String partID = partAndLinkID.substring(0,
                                partAndLinkID.indexOf(":"));


                        String linkID = partAndLinkID.substring(partAndLinkID.
                                indexOf(":") + 1, partAndLinkID.indexOf(","));

                        String isHasSub = partAndLinkID.substring(partAndLinkID.
                                indexOf(",") + 1, partAndLinkID.indexOf("+"));
                                
                        String isOpen = partAndLinkID.substring(partAndLinkID.
                                indexOf("+") + 1, partAndLinkID.length());
                                        
                        BaseValueIfc partifc = pservice.refreshInfo(partID);



                        PartUsageLinkIfc linkifc = (PartUsageLinkIfc) pservice.
                                refreshInfo(linkID);

                        //2003/12/16
                        if (partifc instanceof QMPartMasterIfc)
                        {

                            QMPartMasterIfc partIfc1 = (QMPartMasterIfc) partifc;

                            NormalPart normalPart1 = new NormalPart();
                            normalPart1.bsoID = partIfc1.getBsoID();
                            normalPart1.parentBsoID = linkifc.getRightBsoID();
                            normalPart1.partName = partIfc1.getPartName();
                            normalPart1.partNumber = partIfc1.getPartNumber();
                            normalPart1.versionID = "";
                            normalPart1.iterationID = "";
                            normalPart1.versionValue = "";
                            normalPart1.viewName = "";
                            normalPart1.quantity = linkifc.
                                    getQuantity();
                            normalPart1.defaultUnit = linkifc.
                                    getDefaultUnit();
                            normalPart1.partUsageLink = linkifc.
                                    getBsoID();
                            normalPart1.isHasSub = isHasSub;        
                            normalPart1.isOpen = isOpen; 
                            normalPart1.isRuntimeNode = "0"; 
                            
                            //如果当前节点与给定节点标识相同，即说明根节点零部件
                            //的结构下有多个当前节点零部件，他们要使用相同的节点
                            //展开和是否是操作节点的标识。
                            if(currentNode.equals(partIfc1.getBsoID()))
                            {
                            normalPart1.isOpen = "0"; 	
                            normalPart1.isRuntimeNode = "1"; 
                            }
                            normalPart1.partIcon = pshelper.getPartIconByID(partIfc1);
                            result.addElement(normalPart1);
                        }

                        else
                        {

                            QMPartIfc partIfc1 = (QMPartIfc) partifc;

                            NormalPart normalPart1 = new NormalPart();
                            normalPart1.bsoID = partIfc1.getBsoID();
                            normalPart1.parentBsoID = linkifc.getRightBsoID();
                            normalPart1.partName = partIfc1.getPartName();
                            normalPart1.partNumber = partIfc1.getPartNumber();
                            normalPart1.versionID = partIfc1.getVersionID();
                            normalPart1.iterationID = partIfc1.getIterationID();
                            normalPart1.versionValue = partIfc1.getVersionValue();
                            normalPart1.viewName = partIfc1.getViewName();
                            normalPart1.quantity = linkifc.
                                    getQuantity();
                            normalPart1.defaultUnit = linkifc.
                                    getDefaultUnit();
                            normalPart1.partUsageLink = linkifc.
                                    getBsoID();
                            normalPart1.isHasSub =isHasSub;         
                            normalPart1.isOpen = isOpen;        
                            normalPart1.isRuntimeNode = "0"; 
                            
                            //如果当前节点与给定节点标识相同，即说明根节点零部件
                            //的结构下有多个当前节点零部件，他们要使用相同的节点
                            //展开和是否是操作节点的标识。
                            if(currentNode.equals(partIfc1.getBsoID()))
                            {
                            normalPart1.isOpen = "0"; 
                            normalPart1.isRuntimeNode = "1"; 
                            }  
                            normalPart1.partIcon = pshelper.getPartIconByID(partIfc1);
                            result.addElement(normalPart1);
                        }

                    }
                }

                if(currentPartIfc instanceof QMPartMasterIfc)
                {

                }
                else
                {
                    QMPartIfc currentPart = (QMPartIfc)currentPartIfc;
                    PartConfigSpecIfc partConfigSpecIfc = (
                            PartConfigSpecIfc) PartServiceRequest.
                            findPartConfigSpecIfc();
                    Vector addVec = new Vector();

                    addVec = PartServiceRequest.getSubProductStructure(
                            currentPart, partConfigSpecIfc);


                    if(!(addVec.size() == 0) || (addVec == null))
                    {

                    	for(int k = 0; k < addVec.size(); k++)
                    	{
                    	    Object tempObj = (Object)addVec.elementAt(k);
                    	    if(tempObj instanceof NormalPart)
                    	    {
                    	    	result.addElement(tempObj);
                    	    }
                    	}
                    }
                  }

                }
                //关闭节点操作。
                else
                {
                    if(currentNode.equals(rootID))
                    {

                }
                else
                {
                    for (int i = 4;i<vector.size(); i++)
                    {
                        String partAndLinkID = (String) vector.elementAt(i);
                        String partID = partAndLinkID.substring(0,
                                partAndLinkID.indexOf(":"));


                        String linkID = partAndLinkID.substring(partAndLinkID.
                                indexOf(":") + 1, partAndLinkID.indexOf(","));

                        String isHasSub = partAndLinkID.substring(partAndLinkID.
                                indexOf(",") + 1, partAndLinkID.indexOf("+"));
                                
                        String isOpen = partAndLinkID.substring(partAndLinkID.
                                indexOf("+") + 1, partAndLinkID.length());
                                
                 
                        BaseValueIfc partifc = pservice.refreshInfo(partID);
                        PartUsageLinkIfc linkifc = (PartUsageLinkIfc) pservice.
                                refreshInfo(linkID);


                        if (linkifc.getRightBsoID().equals(currentNode))
                        {

                        }
                        else
                        {
                            if (partifc instanceof QMPartMasterIfc)
                        {

                            QMPartMasterIfc partIfc1 = (QMPartMasterIfc) partifc;

                            NormalPart normalPart1 = new NormalPart();
                            normalPart1.bsoID = partIfc1.getBsoID();
                            normalPart1.parentBsoID = linkifc.getRightBsoID();
                            normalPart1.partName = partIfc1.getPartName();
                            normalPart1.partNumber = partIfc1.getPartNumber();
                            normalPart1.versionID = "";
                            normalPart1.iterationID = "";
                            normalPart1.versionValue = "";
                            normalPart1.viewName = "";
                            normalPart1.quantity = linkifc.
                                    getQuantity();
                            normalPart1.defaultUnit = linkifc.
                                    getDefaultUnit();
                            normalPart1.partUsageLink = linkifc.
                                    getBsoID();
                            normalPart1.isHasSub = isHasSub;          
                            normalPart1.isOpen = isOpen;  
                            normalPart1.isRuntimeNode = "0"; 
                            
                            //如果当前节点与给定节点标识相同，即说明根节点零部件
                            //的结构下有多个当前节点零部件，他们要使用相同的节点
                            //展开和是否是操作节点的标识。
                            if(currentNode.equals(partIfc1.getBsoID()))
                            {
                            normalPart1.isOpen = "1"; 
                            normalPart1.isRuntimeNode = "1"; 
                            }      
                            normalPart1.partIcon = pshelper.getPartIconByID(partIfc1);
                            result.addElement(normalPart1);
                        }

                        else
                        {

                            QMPartIfc partIfc1 = (QMPartIfc) partifc;

                            NormalPart normalPart1 = new NormalPart();
                            normalPart1.bsoID = partIfc1.getBsoID();
                            normalPart1.parentBsoID = linkifc.getRightBsoID();
                            normalPart1.partName = partIfc1.getPartName();
                            normalPart1.partNumber = partIfc1.getPartNumber();
                            normalPart1.versionID = partIfc1.getVersionID();
                            normalPart1.iterationID = partIfc1.getIterationID();
                            normalPart1.versionValue = partIfc1.getVersionValue();
                            normalPart1.viewName = partIfc1.getViewName();
                            normalPart1.quantity = linkifc.
                                    getQuantity();
                            normalPart1.defaultUnit = linkifc.
                                    getDefaultUnit();
                            normalPart1.partUsageLink = linkifc.
                                    getBsoID();
                            normalPart1.isHasSub = isHasSub;          
                            normalPart1.isOpen = isOpen;    
                            normalPart1.isRuntimeNode = "0"; 
                            
                            //如果当前节点与给定节点标识相同，即说明根节点零部件
                            //的结构下有多个当前节点零部件，他们要使用相同的节点
                            //展开和是否是操作节点的标识。
                            if(currentNode.equals(partIfc1.getBsoID()))
                            {
                            normalPart1.isOpen = "1"; 
                            normalPart1.isRuntimeNode = "1"; 
                            }   
                            normalPart1.partIcon = pshelper.getPartIconByID(partIfc1);
                            result.addElement(normalPart1);
                        }

                        }
                    }
                }

                }
                response.setVec(result);
System.out.println("零件结构大小===="+result);
            }
            
            //shizhi add by lis begin
            if(de.getActionType() == PartEvent.ShizhiStructure)
            {
//System.out.println("试制件结构--------------------------");
                PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
                PartServiceHelper pshelper = new PartServiceHelper();

                //String node_count = (String) vector.elementAt(0);
                String rootID = (String)vector.elementAt(0);
                String currentNode = (String)vector.elementAt(1);
                String flag = (String)vector.elementAt(2);
//System.out.println("rootID ==== " + rootID);
//System.out.println("currentNode ==== " + currentNode);
//System.out.println("flag ==== " + flag);
//System.out.println("vector ==== " + vector);
//System.out.println("vector size ==== " + vector.size());
                //String node0 = (String) vector.elementAt(3);

                BaseValueIfc currentPartIfc = pservice.refreshInfo(currentNode);

                //int nodeNum = Integer.parseInt(node_count);
                //node = node+Integer.toString(nodeNum);

                Vector result = new Vector();

                //判断是否是展开节点操作。flag为0时是节点展开操作，flag为1时是节点关闭操作。
                if(flag.equals("0"))
                {

                    if(currentNode.equals(rootID))
                    {

                    }else
                    {

                        for(int i = 4;i < vector.size();i++)
                        {
                            String partAndLinkID = (String)vector.elementAt(i);

                            String partID = partAndLinkID.substring(0, partAndLinkID.indexOf(":"));

                            String linkID = partAndLinkID.substring(partAndLinkID.indexOf(":") + 1, partAndLinkID.indexOf(","));
//System.out.println("linkID========================"+linkID);
                            String isHasSub = partAndLinkID.substring(partAndLinkID.indexOf(",") + 1, partAndLinkID.indexOf("+"));

                            String isOpen = partAndLinkID.substring(partAndLinkID.indexOf("+") + 1, partAndLinkID.length());

                            BaseValueIfc partifc = pservice.refreshInfo(partID);
//                            System.out.println("partifc ==== " + partifc);
                            PartUsageLinkIfc linkifc = null;
                            Vector vec = new Vector();
                            if(linkID.startsWith("PartUsageLink"))
                            {
                                linkifc = (PartUsageLinkIfc)pservice.refreshInfo(linkID);
                            }
                            else
                            {
                                vec = getShizhiStructure(linkID);
                            }

                            //2003/12/16
                            if(partifc instanceof QMPartMasterIfc)
                            {

                                QMPartMasterIfc partIfc1 = (QMPartMasterIfc)partifc;

                                NormalPart normalPart1 = new NormalPart();
                                normalPart1.bsoID = partIfc1.getBsoID();
                                
                                normalPart1.partName = partIfc1.getPartName();
                                normalPart1.partNumber = partIfc1.getPartNumber();
                                normalPart1.versionID = "";
                                normalPart1.iterationID = "";
                                normalPart1.versionValue = "";
                                normalPart1.viewName = "";
                                
                                if(linkID.startsWith("PartUsageLink"))
                                {
                                    normalPart1.quantity = linkifc.getQuantity();
                                    normalPart1.parentBsoID = linkifc.getRightBsoID();;
                                }
                                else if(vec.size()>0)
                                {
                                    normalPart1.quantity = Float.valueOf((String)vec.get(2));
                                    normalPart1.parentBsoID = (String)vec.get(1);
                                }
                                
//                                normalPart1.defaultUnit = linkifc.getDefaultUnit();
                                normalPart1.partUsageLink = linkID;
                                normalPart1.isHasSub = isHasSub;
                                normalPart1.isOpen = isOpen;
                                normalPart1.isRuntimeNode = "0";

                                //如果当前节点与给定节点标识相同，即说明根节点零部件
                                //的结构下有多个当前节点零部件，他们要使用相同的节点
                                //展开和是否是操作节点的标识。
                                if(currentNode.equals(partIfc1.getBsoID()))
                                {
                                    normalPart1.isOpen = "0";
                                    normalPart1.isRuntimeNode = "1";
                                }
                                normalPart1.partIcon = pshelper.getPartIconByID(partIfc1);
//                                System.out.println("11111111111111111");
                                result.addElement(normalPart1);
                            }

                            else
                            {

                                QMPartIfc partIfc1 = (QMPartIfc)partifc;

                                NormalPart normalPart1 = new NormalPart();
                                normalPart1.bsoID = partIfc1.getBsoID();
//                                normalPart1.parentBsoID = linkID;
                                normalPart1.partName = partIfc1.getPartName();
                                normalPart1.partNumber = partIfc1.getPartNumber();
                                normalPart1.versionID = partIfc1.getVersionID();
                                normalPart1.iterationID = partIfc1.getIterationID();
                                normalPart1.versionValue = partIfc1.getVersionValue();
                                normalPart1.viewName = partIfc1.getViewName();
                                if(linkID.startsWith("PartUsageLink"))
                                {
                                    normalPart1.quantity = linkifc.getQuantity();
                                    normalPart1.parentBsoID = linkifc.getRightBsoID();;
                                }
                                else if(vec.size()>0)
                                {
                                    normalPart1.quantity = Float.valueOf((String)vec.get(2));
                                    normalPart1.parentBsoID = (String)vec.get(1);
                                }
//                                normalPart1.defaultUnit = linkifc.getDefaultUnit();
                                normalPart1.partUsageLink = linkID;
                                normalPart1.isHasSub = isHasSub;
                                normalPart1.isOpen = isOpen;
                                normalPart1.isRuntimeNode = "0";

                                //如果当前节点与给定节点标识相同，即说明根节点零部件
                                //的结构下有多个当前节点零部件，他们要使用相同的节点
                                //展开和是否是操作节点的标识。
                                if(currentNode.equals(partIfc1.getBsoID()))
                                {
                                    normalPart1.isOpen = "0";
                                    normalPart1.isRuntimeNode = "1";
                                }
                                normalPart1.partIcon = pshelper.getPartIconByID(partIfc1);
//                                System.out.println("22222222222222222");
                                result.addElement(normalPart1);
                            }

                        }
                    }

                    if(currentPartIfc instanceof QMPartMasterIfc)
                    {

                    }else
                    {
                        QMPartIfc currentPart = (QMPartIfc)currentPartIfc;
                        PartConfigSpecIfc partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();
                        Vector addVec = new Vector();

                        
                        addVec = GYBomHelper.getShizhiStructure(currentPart.getBsoID());
                        if(addVec.size() == 0)
                        {
                            addVec = PartServiceRequest.getSubProductStructure(currentPart, partConfigSpecIfc);
                        }
                        if(!(addVec.size() == 0) || (addVec == null))
                        {

                            for(int k = 0;k < addVec.size();k++)
                            {
                                Object tempObj = (Object)addVec.elementAt(k);
                                if(tempObj instanceof NormalPart)
                                {
//                                    System.out.println("33333333333333");
                                    result.addElement(tempObj);
                                }
                            }
                        }
                    }

                }
                //关闭节点操作。
                else
                {
                    if(currentNode.equals(rootID))
                    {

                    }else
                    {
                        for(int i = 4;i < vector.size();i++)
                        {
                            String partAndLinkID = (String)vector.elementAt(i);
                            String partID = partAndLinkID.substring(0, partAndLinkID.indexOf(":"));

                            String linkID = partAndLinkID.substring(partAndLinkID.indexOf(":") + 1, partAndLinkID.indexOf(","));

                            String isHasSub = partAndLinkID.substring(partAndLinkID.indexOf(",") + 1, partAndLinkID.indexOf("+"));

                            String isOpen = partAndLinkID.substring(partAndLinkID.indexOf("+") + 1, partAndLinkID.length());

                            BaseValueIfc partifc = pservice.refreshInfo(partID);
                            PartUsageLinkIfc linkifc = null;
                            Vector vec = new Vector();
                            if(linkID.startsWith("PartUsageLink"))
                            {
                                linkifc = (PartUsageLinkIfc)pservice.refreshInfo(linkID);
                            }
                            else
                            {
                                vec = getShizhiStructure(linkID);
                            }

//                            if(linkifc.getRightBsoID().equals(currentNode))
//                            {
//
//                            }else
                            {
                                if(partifc instanceof QMPartMasterIfc)
                                {

                                    QMPartMasterIfc partIfc1 = (QMPartMasterIfc)partifc;

                                    NormalPart normalPart1 = new NormalPart();
                                    normalPart1.bsoID = partIfc1.getBsoID();
//                                    normalPart1.parentBsoID = linkID;
                                    normalPart1.partName = partIfc1.getPartName();
                                    normalPart1.partNumber = partIfc1.getPartNumber();
                                    normalPart1.versionID = "";
                                    normalPart1.iterationID = "";
                                    normalPart1.versionValue = "";
                                    normalPart1.viewName = "";
                                    if(linkID.startsWith("PartUsageLink"))
                                    {
                                        normalPart1.quantity = linkifc.getQuantity();
                                        normalPart1.parentBsoID = linkifc.getRightBsoID();;
                                    }
                                    else if(vec.size()>0)
                                    {
                                        normalPart1.quantity = Float.valueOf((String)vec.get(2));
                                        normalPart1.parentBsoID = (String)vec.get(1);
                                    }
//                                    normalPart1.defaultUnit = linkifc.getDefaultUnit();
                                    normalPart1.partUsageLink = linkID;
                                    normalPart1.isHasSub = isHasSub;
                                    normalPart1.isOpen = isOpen;
                                    normalPart1.isRuntimeNode = "0";

                                    //如果当前节点与给定节点标识相同，即说明根节点零部件
                                    //的结构下有多个当前节点零部件，他们要使用相同的节点
                                    //展开和是否是操作节点的标识。
                                    if(currentNode.equals(partIfc1.getBsoID()))
                                    {
                                        normalPart1.isOpen = "1";
                                        normalPart1.isRuntimeNode = "1";
                                    }
                                    normalPart1.partIcon = pshelper.getPartIconByID(partIfc1);
//                                    System.out.println("44444444444444444");
                                    result.addElement(normalPart1);
                                }

                                else
                                {

                                    QMPartIfc partIfc1 = (QMPartIfc)partifc;

                                    NormalPart normalPart1 = new NormalPart();
                                    normalPart1.bsoID = partIfc1.getBsoID();
//                                    normalPart1.parentBsoID = linkID;
                                    normalPart1.partName = partIfc1.getPartName();
                                    normalPart1.partNumber = partIfc1.getPartNumber();
                                    normalPart1.versionID = partIfc1.getVersionID();
                                    normalPart1.iterationID = partIfc1.getIterationID();
                                    normalPart1.versionValue = partIfc1.getVersionValue();
                                    normalPart1.viewName = partIfc1.getViewName();
                                    if(linkID.startsWith("PartUsageLink"))
                                    {
                                        normalPart1.quantity = linkifc.getQuantity();
                                        normalPart1.parentBsoID = linkifc.getRightBsoID();;
                                    }
                                    else if(vec.size()>0)
                                    {
                                        normalPart1.quantity = Float.valueOf((String)vec.get(2));
                                        normalPart1.parentBsoID = (String)vec.get(1);
                                    }
//                                    normalPart1.defaultUnit = linkifc.getDefaultUnit();
                                    normalPart1.partUsageLink = linkID;
                                    normalPart1.isHasSub = isHasSub;
                                    normalPart1.isOpen = isOpen;
                                    normalPart1.isRuntimeNode = "0";

                                    //如果当前节点与给定节点标识相同，即说明根节点零部件
                                    //的结构下有多个当前节点零部件，他们要使用相同的节点
                                    //展开和是否是操作节点的标识。
                                    if(currentNode.equals(partIfc1.getBsoID()))
                                    {
                                        normalPart1.isOpen = "1";
                                        normalPart1.isRuntimeNode = "1";
                                    }
                                    normalPart1.partIcon = pshelper.getPartIconByID(partIfc1);
//                                    System.out.println("5555555555555555555");
                                    result.addElement(normalPart1);
                                }

                            }
                        }
                    }

                }
//                for(int i=0; i<result.size(); i++)
//                {
//                    NormalPart normalPart1 = (NormalPart)result.get(i);
//                    System.out.println("parent ==="+normalPart1.parentBsoID);
//                    System.out.println("partNumber ==="+normalPart1.partNumber);
//                    System.out.println("bsoID ==="+normalPart1.bsoID);
//                    System.out.println("---------------------------------------");
//                }
//                System.out.println("返回集合大小===="+result);
                response.setVec(result);
            }
            //shizhi add by lis end
            
        }
        catch (Exception ue)
        {
            ue.printStackTrace();
            Object[] objs =
                            {de.getEventName()};
            throw new EventException(ue, "22", objs);
        }
        return response;
    }
    
    /**
     * 获取试制件结构
     * @param partID
     * @return
     */
    private Vector getShizhiStructure(String partID)
    {
        Vector resultVector = new Vector();
        //更新关联，子件id替换成上一版零部件id
          Connection conn = null;
          Statement stmt =null;
          ResultSet rs=null;
          
          try
          {
              
              conn = PersistUtil.getConnection();
              stmt = conn.createStatement();
              rs = stmt.executeQuery("select * from gybomshizhistructure b " +
                    "where b.id = '" + partID + "'");
              System.out.println("partID-----------------"+partID);
              while(rs.next())
              {
                  String childpart=rs.getString("childpart");
                  String parentPart=rs.getString("parentPart");
                  String quantity=rs.getString("quantity");
                  String id=rs.getString("id");
                  
                  resultVector.add(childpart);
                  resultVector.add(parentPart);
                  resultVector.add(quantity);
                  resultVector.add(id);
              }
              //关闭Statement
              stmt.close();
              //关闭数据库连接
              conn.close();
          }
          catch(Exception e)
          {
              e.printStackTrace();
          }
          finally
          {
              try
              {
                  if (rs != null)
                  {
                      rs.close();
                  }
                  if (stmt != null)
                  {
                      stmt.close();
                  }
                  if (conn != null)
                  {
                      conn.close();
                  }
              }
              catch (SQLException ex1)
              {
                  ex1.printStackTrace();
              }
          }
          return resultVector;
      }
}
