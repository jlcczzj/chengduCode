// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 2009-8-3 12:55:13
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   LoadERP.java

package com.faw_qm.erp.client;

import com.faw_qm.erp.model.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.load.util.LoadHelper;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;
import java.sql.Timestamp;
import java.util.*;

public class LoadERP
{

    public LoadERP()
    {
    }

    public static boolean createMaterialSplit(Hashtable attributes, Vector logs)
        throws QMException
    {
        String partNumber = "";
        String partVersion = "";
        String state = "";
        String materialNumber = "";
        String splited = "";
        String virtureFlag = "";
        String status = "";
        String routeCode = "";
        String route = "";
        String partName = "";
        String defaultUnit = "";
        String producedBy = "";
        String partType = "";
        String partModifyTime = "";
        String optionCode = "";
        String colorFlag = "";
        String rootFlag = "";
        try
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
            partNumber = LoadHelper.getValue("partNumber", attributes, 0);
            partVersion = LoadHelper.getValue("partVersion", attributes, 0);
            state = LoadHelper.getValue("state", attributes, 0);
            materialNumber = LoadHelper.getValue("materialNumber", attributes, 0);
            splited = LoadHelper.getValue("splited", attributes, 0);
            virtureFlag = LoadHelper.getValue("virtureFlag", attributes, 0);
            status = LoadHelper.getValue("status", attributes, 0);
            routeCode = LoadHelper.getValue("routeCode", attributes, 0);
            route = LoadHelper.getValue("route", attributes, 0);
            partName = LoadHelper.getValue("partName", attributes, 0);
            defaultUnit = LoadHelper.getValue("defaultUnit", attributes, 0);
            producedBy = LoadHelper.getValue("producedBy", attributes, 0);
            partType = LoadHelper.getValue("partType", attributes, 0);
            partModifyTime = LoadHelper.getValue("partModifyTime", attributes, 0);
            optionCode = LoadHelper.getValue("optionCode", attributes, 0);
            colorFlag = LoadHelper.getValue("colorFlag", attributes, 0);
            rootFlag = LoadHelper.getValue("rootFlag", attributes, 0);
            MaterialSplitInfo info = new MaterialSplitInfo();
            info.setPartNumber(partNumber);
            info.setPartName(partName);
            info.setPartVersion(partVersion);
            info.setState(state);
            info.setMaterialNumber(materialNumber);
            info.setSplited((new Boolean(splited)).booleanValue());
            info.setVirtualFlag((new Boolean(virtureFlag)).booleanValue());
            info.setStatus((new Integer(status)).intValue());
            info.setRouteCode(routeCode);
            info.setRoute(route);
            info.setPartName(partName);
            info.setDefaultUnit(defaultUnit);
            info.setProducedBy(producedBy);
            info.setPartType(partType);
            StringTokenizer to = new StringTokenizer(partModifyTime, "-");
            int year = 0;
            int month = 0;
            int day = 0;
            try
            {
                while(to.hasMoreTokens()) 
                {
                    year = (new Integer(to.nextToken())).intValue();
                    month = (new Integer(to.nextToken())).intValue();
                    day = (new Integer(to.nextToken())).intValue();
                }
            }
            catch(Exception ex)
            {
                year = 0;
                month = 0;
                day = 0;
            }
            Timestamp time = null;
            if(year != 0 && month != 0 && day != 0)
                time = new Timestamp(year, month, day, 0, 0, 0, 0);
            else
                time = new Timestamp(System.currentTimeMillis());
            info.setPartModifyTime(time);
            info.setOptionCode(optionCode);
            info.setColorFlag((new Boolean(colorFlag)).booleanValue());
            info.setRootFlag((new Boolean(rootFlag)).booleanValue());
        }
        catch(QMException ex)
        {
            LoadHelper.printMessage("-------------\u521B\u5EFA\u7269\u6599\u9519\u8BEF--------------------");
            LoadHelper.printMessage(ex.toString());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean createMaterialStruceture(Hashtable attributes, Vector logs)
        throws QMException
    {
        String parentPartNumber = "";
        String parentPartVersion = "";
        String parentNumber = "";
        String childNumber = "";
        String quantity = "";
        String levelNumber = "";
        String defaultUnit = "";
        String optionFlag = "";
        try
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
            parentPartNumber = LoadHelper.getValue("parentPartNumber", attributes, 0);
            parentPartVersion = LoadHelper.getValue("parentPartVersion", attributes, 0);
            parentNumber = LoadHelper.getValue("parentNumber", attributes, 0);
            childNumber = LoadHelper.getValue("childNumber", attributes, 0);
            quantity = LoadHelper.getValue("quantity", attributes, 0);
            levelNumber = LoadHelper.getValue("levelNumber", attributes, 0);
            defaultUnit = LoadHelper.getValue("defaultUnit", attributes, 0);
            optionFlag = LoadHelper.getValue("optionFlag", attributes, 0);
            MaterialStructureInfo ms = new MaterialStructureInfo();
            ms.setParentPartNumber(parentPartNumber);
            ms.setParentPartVersion(parentPartVersion);
            ms.setParentNumber(parentNumber);
            ms.setChildNumber(childNumber);
            ms.setQuantity((new Float(quantity)).floatValue());
            ms.setLevelNumber(levelNumber);
            ms.setDefaultUnit(defaultUnit);
            ms.setOptionFlag((new Boolean(optionFlag)).booleanValue());
            pservice.saveValueInfo(ms);
        }
        catch(QMException ex)
        {
            LoadHelper.printMessage("-------------\u521B\u5EFA\u7269\u6599\u7ED3\u6784\u9519\u8BEF--------------------");
            LoadHelper.printMessage(ex.toString());
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean createFilterPart(Hashtable attributes, Vector logs)
        throws QMException
    {
        String partNumber = "";
        String route = "";
        String versionValue = "";
        String state = "";
        String noticeNumber = "";
        String noticeType = "";
        try
        {
            PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
            partNumber = LoadHelper.getValue("parentPartNumber", attributes, 0);
            route = LoadHelper.getValue("parentPartVersion", attributes, 0);
            versionValue = LoadHelper.getValue("parentNumber", attributes, 0);
            state = LoadHelper.getValue("childNumber", attributes, 0);
            noticeNumber = LoadHelper.getValue("quantity", attributes, 0);
            noticeType = LoadHelper.getValue("levelNumber", attributes, 0);
            FilterPartInfo filterpart = new FilterPartInfo();
            filterpart.setPartNumber(partNumber);
            filterpart.setRoute(route);
            filterpart.setVersionValue(versionValue);
            filterpart.setState(state);
            filterpart.setNoticeNumber(noticeNumber);
            filterpart.setNoticeType(noticeType);
            pservice.saveValueInfo(filterpart);
        }
        catch(QMException ex)
        {
            LoadHelper.printMessage("-------------\u521B\u5EFA\u96F6\u90E8\u4EF6\u7B5B\u9009\u9519\u8BEF--------------------");
            LoadHelper.printMessage(ex.toString());
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}