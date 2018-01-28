/** ���ɳ���PartEJBAction.java	  1.0  2003/05/10
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
 * @author (correct by ������)
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
    /**���л�ID*/
    static final long serialVersionUID = 1L;

    /**
     * ִ���ݿͻ��˵�.do������
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
            //���¼�������SavePartConfigSpec(add by������)
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
                            //����������ߣ���substitutes��
                            linkIfc.setLeftBsoID((String) vector.get(i));
                            //����substituteFor
                            linkIfc.setRightBsoID(usageLinkIfc.getBsoID());
                            //��PartSubstituteLink����linkIfc�������ݿ���
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
                    //ѭ���������ԭ�㲿���͸����ṹ�滻��֮��Ĺ����󽫸������������ݿ���ɾ��
                    for (int i = 1; i < vector.size(); i++)
                    {
                        //��������е�Ԫ��
                        String aaa = (String) vector.get(i);
                        //����������ѯ
                        QMQuery query = new QMQuery("PartAlternateLink");
                        //�����ѯ����1
                        QueryCondition condition1 = new QueryCondition(
                                "leftBsoID", "=", aaa);
                        //��Ӳ�ѯ����1
                        query.addCondition(condition1);
                        //���and�ַ�
                        query.addAND();
                        //���ò�ѯ����2
                        QueryCondition condition2 = new QueryCondition(
                                "rightBsoID", "=", sourcePartID);
                        //��Ӳ�ѯ����2
                        query.addCondition(condition2);
                        //���ó־û�������в�ѯ����÷���ֵ
                        Collection collection = aa.findValueInfo(query);
                        //������ֵ��ò�����ɾ��
                        Object[] quetydata = collection.toArray();
                        //���ó־û����񽫸ö���ɾ��
                        aa.deleteValueInfo((BaseValueIfc) quetydata[0]);
                    }
                }
            }
            if (de.getActionType() == PartEvent.DELETEStruAlterPart)
            {

                if (vector != null && vector.size() > 0)
                {
                    String sourcePartID = (String) vector.get(0);
                    //��ȡԴ�ṹ�㲿��
                    String sourceStructPartID = (String) vector.get(1);
                    //��ȡ�־û�����
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
                    //��ȡ�־û�����
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
                    //ѭ����vector�е�ÿһ��Ԫ�ض�����ΪԴ�㲿���ı��滻��
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
                            //�½�һ����������
                            PartAlternateLinkIfc linkIfc = new
                                    PartAlternateLinkInfo();
                            //���øö�����滻��
                            linkIfc.setLeftBsoID(sourcePartID);
                            //���øö���ı��滻��
                            linkIfc.setRightBsoID((String) vector.get(i));
                            //���ó־û����񽫸ö���������ݿ���
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
                
                //�ж��Ƿ���չ���ڵ������flagΪ0ʱ�ǽڵ�չ��������flagΪ1ʱ�ǽڵ�رղ�����
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
                            
                            //�����ǰ�ڵ�������ڵ��ʶ��ͬ����˵�����ڵ��㲿��
                            //�Ľṹ���ж����ǰ�ڵ��㲿��������Ҫʹ����ͬ�Ľڵ�
                            //չ�����Ƿ��ǲ����ڵ�ı�ʶ��
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
                            
                            //�����ǰ�ڵ�������ڵ��ʶ��ͬ����˵�����ڵ��㲿��
                            //�Ľṹ���ж����ǰ�ڵ��㲿��������Ҫʹ����ͬ�Ľڵ�
                            //չ�����Ƿ��ǲ����ڵ�ı�ʶ��
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
                //�رսڵ������
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
                            
                            //�����ǰ�ڵ�������ڵ��ʶ��ͬ����˵�����ڵ��㲿��
                            //�Ľṹ���ж����ǰ�ڵ��㲿��������Ҫʹ����ͬ�Ľڵ�
                            //չ�����Ƿ��ǲ����ڵ�ı�ʶ��
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
                            
                            //�����ǰ�ڵ�������ڵ��ʶ��ͬ����˵�����ڵ��㲿��
                            //�Ľṹ���ж����ǰ�ڵ��㲿��������Ҫʹ����ͬ�Ľڵ�
                            //չ�����Ƿ��ǲ����ڵ�ı�ʶ��
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
System.out.println("����ṹ��С===="+result);
            }
            
            //shizhi add by lis begin
            if(de.getActionType() == PartEvent.ShizhiStructure)
            {
//System.out.println("���Ƽ��ṹ--------------------------");
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

                //�ж��Ƿ���չ���ڵ������flagΪ0ʱ�ǽڵ�չ��������flagΪ1ʱ�ǽڵ�رղ�����
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

                                //�����ǰ�ڵ�������ڵ��ʶ��ͬ����˵�����ڵ��㲿��
                                //�Ľṹ���ж����ǰ�ڵ��㲿��������Ҫʹ����ͬ�Ľڵ�
                                //չ�����Ƿ��ǲ����ڵ�ı�ʶ��
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

                                //�����ǰ�ڵ�������ڵ��ʶ��ͬ����˵�����ڵ��㲿��
                                //�Ľṹ���ж����ǰ�ڵ��㲿��������Ҫʹ����ͬ�Ľڵ�
                                //չ�����Ƿ��ǲ����ڵ�ı�ʶ��
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
                //�رսڵ������
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

                                    //�����ǰ�ڵ�������ڵ��ʶ��ͬ����˵�����ڵ��㲿��
                                    //�Ľṹ���ж����ǰ�ڵ��㲿��������Ҫʹ����ͬ�Ľڵ�
                                    //չ�����Ƿ��ǲ����ڵ�ı�ʶ��
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

                                    //�����ǰ�ڵ�������ڵ��ʶ��ͬ����˵�����ڵ��㲿��
                                    //�Ľṹ���ж����ǰ�ڵ��㲿��������Ҫʹ����ͬ�Ľڵ�
                                    //չ�����Ƿ��ǲ����ڵ�ı�ʶ��
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
//                System.out.println("���ؼ��ϴ�С===="+result);
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
     * ��ȡ���Ƽ��ṹ
     * @param partID
     * @return
     */
    private Vector getShizhiStructure(String partID)
    {
        Vector resultVector = new Vector();
        //���¹������Ӽ�id�滻����һ���㲿��id
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
              //�ر�Statement
              stmt.close();
              //�ر����ݿ�����
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
