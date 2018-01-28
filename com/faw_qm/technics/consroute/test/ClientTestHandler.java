package com.faw_qm.technics.consroute.test;

import java.io.FileWriter;

import java.util.Collection;
import java.util.Vector;

import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteListLevelType;
import com.faw_qm.wip.model.WorkableIfc;

/**
 * <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2004</p> <p>Company: 启明公司</p>
 * @author 赵立彬
 * @version 1.0
 */

public class ClientTestHandler
{

    private final static String serviceName = "TechnicsRouteService";

    public ClientTestHandler()
    {}

    public static Object callTest(int i) throws Exception
    {
        Object obj = null;
        switch(i)
        {
        case 1:
            obj = c_storeRouteList();
            break;
        case 2:
            obj = c_getOptionalParts();
            break;
        case 3:
            obj = c_saveListRoutePartLink();
            break;
        ///////////////////////完成////////////////////
        case 4:
            obj = c_getAllRouteLists();
            break;
        case 5:
            obj = c_getRouteListLinkParts();
            break;
        case 6:
            obj = c_saveRoute();
            break;
        case 7:
            obj = c_changeFolder();
            break;
        case 8:
            obj = c_checkIn();
            break;
        case 9:
            obj = c_copyRouteList();
            break;
        case 10:
            obj = c_undoCheckout();
            break;
        ///////////////////////完成////////////////////
        case 11:

            //obj = c_deleteRoute();
            break;
        case 12:
            obj = c_deleteRouteList();
            break;
        case 13:
            obj = c_compareIterate();
            break;
        case 14:

            //obj =
            break;
        case 15:
            obj = c_findRouteLists();
            break;
        case 16:
            obj = c_getPartLevelRoutes();
            break;
        case 17:
            obj = c_findRouteLists_1();
            break;
        case 18:

            //obj =
            break;
        case 20:
            obj = c_deleteRouteList1();
            break;
        case 21:
            obj = c_copyRoute();
            break;
        case 22:
            obj = c_exportRouteList_first();
            break;
        case 23:
            obj = c_exportRouteList_second();
            break;
        case 24:
            obj = c_compareIterate();
            break;
        case 30:
            obj = c_load();
            break;
        default:
            break;
        }
        return obj;
    }

    ////////////////////////////////测试方法开始//////////////////////////////////////
    //构造参数。

    public static Object c_load() throws Exception
    {
        System.out.println("开始测试方法： c_load.....................");
        String fileName = "c:\\routelist.csv";
        String mapName = "c:\\csvmapfile.txt";
        Class[] paramType = {String.class, String.class, String.class, String.class, String.class, String.class};
        Object[] paramValue = {fileName, mapName, null, null, null, null};
        String methodName = "load";
        String serviceName = "StandardLoadService";
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    //TechnicsRouteList_47602, TechnicsRouteList_47802
    public static Object c_compareIterate() throws Exception
    {
        System.out.println("开始测试方法： c_compareIterate.....................");
        TechnicsRouteListIfc listInfo1 = (TechnicsRouteListIfc)refreshInfo("TechnicsRouteList_47602");
        TechnicsRouteListIfc listInfo2 = (TechnicsRouteListIfc)refreshInfo("TechnicsRouteList_47802");
        Collection vec = new Vector();

        vec.add(listInfo2);
        vec.add(listInfo1);
        Class[] paramType = {Collection.class, boolean.class};
        Object[] paramValue = {vec, new Boolean(true)};
        String methodName = "compareIterate";
        String serviceName = null;
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    public static Object c_exportRouteList_second() throws Exception
    {
        System.out.println("开始测试方法： c_copyRoute.....................");
        //String routeListID = "TechnicsRouteList_42202";
        String routeListID = "TechnicsRouteList_47702";
        Class[] paramType = {String.class, String.class, String.class};
        Object[] paramValue = {routeListID, "二级路线", ".csv"};
        String methodName = "exportRouteList";
        String serviceName = null;
        String result = (String)testHandler(serviceName, methodName, paramType, paramValue);
        System.out.println("\n ################################################### result = " + result);
        FileWriter fw = new FileWriter("c:\\second.csv", false);
        fw.write(result);
        fw.close();
        return null;
    }

    public static Object c_exportRouteList_first() throws Exception
    {
        System.out.println("开始测试方法： c_exportRouteList_first.....................");
        String routeListID = "TechnicsRouteList_42202";
        Class[] paramType = {String.class, String.class, String.class};
        Object[] paramValue = {routeListID, "一级路线", ".csv"};
        String methodName = "exportRouteList";
        String serviceName = null;
        String result = (String)testHandler(serviceName, methodName, paramType, paramValue);
        System.out.println("\n ################################################### result = " + result);
        FileWriter fw = new FileWriter("c:\\first.csv", false);
        fw.write(result);
        fw.close();
        return null;
    }

    public static Object c_copyRoute() throws QMException
    {
        System.out.println("开始测试方法： c_copyRoute.....................");
        String linkID = "ListRoutePartLink_45108";
        String routeID = "TechnicsRoute_44909";
        ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)refreshInfo(linkID);
        Class[] paramType = {String.class, ListRoutePartLinkIfc.class};
        Object[] paramValue = {routeID, linkInfo};
        String methodName = "copyRoute";
        String serviceName = null;
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    public static Object c_deleteRouteList1() throws QMException
    {
        System.out.println("开始测试方法： c_deleteRouteList1.....................");
        String listID = "TechnicsRouteList_42401";
        TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)refreshInfo(listID);
        Class[] paramType = {BaseValueIfc.class};
        Object[] paramValue = {listInfo};
        String methodName = "deleteValueInfo";
        String serviceName = "PersistService";
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    //public Collection findRouteLists(Object[][] param) throws QMException

    public static Object c_findRouteLists_1() throws QMException
    {
        System.out.println("开始测试方法： c_findRouteLists.....................");
        Object[][] param = new Object[4][2];

        Class[] paramType = {TechnicsRouteListIfc.class, String.class, String.class, String.class};
        Object[] paramValue = {null, null, null, null};
        String methodName = "findRouteLists";
        String serviceName = null;
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    public static Object c_getPartLevelRoutes() throws QMException
    {
        System.out.println("开始测试方法： c_findRouteLists.....................");
        String partMasterID = "QMPartMaster_405";
        String level_zh = "一级路线";
        Class[] paramType = {String.class, String.class};
        Object[] paramValue = {partMasterID, level_zh};
        String methodName = "getPartLevelRoutes";
        String serviceName = null;
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    public static Object c_findRouteLists() throws QMException
    {
        System.out.println("开始测试方法： c_findRouteLists.....................");
        TechnicsRouteListIfc listInfo1 = new TechnicsRouteListInfo();
        listInfo1.setIterationCreator("list3");
        listInfo1.setRouteListNumber("zlb_list11");
        System.out.println(listInfo1.getIterationCreator());
        //listInfo1.setRouteListLevel("二级路线");
        //listInfo1.setVersionValue("A.1");
        Class[] paramType = {TechnicsRouteListIfc.class, String.class, String.class, String.class};
        Object[] paramValue = {listInfo1, "", "", ""};
        String methodName = "findRouteLists";
        String serviceName = null;
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    //完成
    public static Object c_saveListRoutePartLink()
    {
        System.out.println("开始测试方法： saveRoute.....................");
        Collection coll = (Collection)c_getOptionalParts();
        Vector save = new Vector();
        Vector delete = new Vector();
        /*
         * for(Iterator iter = coll.iterator(); iter.hasNext();) { save.add(((QMPartMasterIfc)iter.next()).getBsoID()); }
         */
        //QMPartMaster_405
        save.add("QMPartMaster_1118");
        //QMPartMaster_1118
        delete.add("QMPartMaster_1118");
        TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)refreshInfo("TechnicsRouteList_2802");
        Class[] paramType = {Collection.class, Collection.class, TechnicsRouteListIfc.class};
        Object[] paramValue = {save, delete, listInfo};
        String methodName = "saveListRoutePartLink";
        return testHandler(null, methodName, paramType, paramValue);
    }

    public static Object c_findRouteLists1() throws QMException
    {
        System.out.println("开始测试方法： c_findRouteLists.....................");
        //TechnicsRouteListIfc listInfo1 = new TechnicsRouteListInfo();

        Class[] paramType = {String.class};
        Object[] paramValue = {"2004/04/07"};
        String methodName = "findRouteLists";
        String serviceName = null;
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    /*
     * public static Object c_compareIterate() throws QMException { System.out.println("开始测试方法： c_compareIterate....................."); TechnicsRouteListIfc listInfo1 =
     * (TechnicsRouteListIfc)refreshInfo("TechnicsRouteList_2108"); TechnicsRouteListIfc listInfo2 = (TechnicsRouteListIfc)refreshInfo("TechnicsRouteList_2123"); Class[] paramType =
     * {TechnicsRouteListIfc.class, TechnicsRouteListIfc.class}; Object[] paramValue = {listInfo1, listInfo2}; String methodName = "compareIterate"; String serviceName = null; return
     * testHandler(serviceName, methodName, paramType, paramValue); }
     */

    public static Object c_deleteRoute() throws QMException
    {
        System.out.println("开始测试方法： c_deleteRoute.....................");
        ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)refreshInfo("ListRoutePartLink_1504");
        Class[] paramType = {ListRoutePartLinkIfc.class};
        Object[] paramValue = {linkInfo};
        String methodName = "deleteRoute";
        String serviceName = null;
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    public static Object c_deleteRouteList() throws QMException
    {
        System.out.println("开始测试方法： c_deleteRouteList.....................");
        TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc)refreshInfo("TechnicsRouteList_2025");
        Class[] paramType = {TechnicsRouteListIfc.class};
        Object[] paramValue = {listInfo};
        String methodName = "deleteRouteList";
        String serviceName = null;
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    public static Object c_changeFolder() throws QMException
    {
        System.out.println("开始测试方法： c_assignFolder.....................");
        TechnicsRouteListIfc foldered = (TechnicsRouteListIfc)refreshInfo("TechnicsRouteList_2108");
        FolderIfc folderInfo = (FolderIfc)refreshInfo("SubFolder_109");
        Class[] paramType = {FolderEntryIfc.class, FolderIfc.class};
        Object[] paramValue = {foldered, folderInfo};
        String methodName = "changeFolder";
        String serviceName = "FolderService";
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    public static Object c_checkIn() throws QMException
    {
        System.out.println("开始测试方法： c_checkIn.....................");
        TechnicsRouteListIfc workable = (TechnicsRouteListIfc)refreshInfo("TechnicsRouteList_2123");
        String note = "dddd";
        Class[] paramType = {WorkableIfc.class, String.class};
        Object[] paramValue = {workable, note};
        String methodName = "checkin";
        String serviceName = "WorkInProgressService";
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    //检出测试。
    public static Object c_copyRouteList() throws QMException
    {
        System.out.println("开始测试方法： c_copyRouteList.....................");
        TechnicsRouteListIfc workable = (TechnicsRouteListIfc)refreshInfo("TechnicsRouteList_2123");
        Class[] paramType = {BaseValueIfc.class};
        Object[] paramValue = {workable};
        String methodName = "checkout";
        String serviceName = "WorkInProgressService";
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    public static Object c_undoCheckout() throws QMException
    {
        System.out.println("开始测试方法： c_undoCheckout.....................");
        //副本
        TechnicsRouteListIfc workable = (TechnicsRouteListIfc)refreshInfo("TechnicsRouteList_2049");
        Class[] paramType = {WorkableIfc.class};
        Object[] paramValue = {workable};
        String methodName = "undoCheckout";
        String serviceName = "WorkInProgressService";
        return testHandler(serviceName, methodName, paramType, paramValue);
    }

    public static Object c_saveRoute() throws QMException
    {
        System.out.println("开始测试方法： saveRoute.....................");
        ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)refreshInfo("ListRoutePartLink_2127");
        TechnicsRouteIfc routeInfo = new TechnicsRouteInfo();
        routeInfo.setRouteDescription("路线qqq");
        Class[] paramType = {ListRoutePartLinkIfc.class, TechnicsRouteIfc.class};
        Object[] paramValue = {linkInfo, routeInfo};
        String methodName = "saveRoute";
        return testHandler(null, methodName, paramType, paramValue);
    }

    public static Object c_getAllRouteLists() throws QMException
    {
        System.out.println("开始测试方法： c_getAllRouteLists.....................");
        Class[] paramType = {};
        Object[] paramValue = {};
        String methodName = "getAllRouteLists";
        return testHandler(null, methodName, paramType, paramValue);
    }

    public static Object c_getRouteListLinkParts() throws QMException
    {
        System.out.println("开始测试方法： c_getRouteListLinkParts.....................");
        Collection coll1 = (Collection)c_getAllRouteLists();
        TechnicsRouteListIfc listInfo = null;
        if(coll1.iterator().hasNext())
        {
            listInfo = (TechnicsRouteListIfc)coll1.iterator().next();
        }
        Class[] paramType = {TechnicsRouteListIfc.class};
        Object[] paramValue = {listInfo};
        String methodName = "getRouteListLinkParts";
        return testHandler(null, methodName, paramType, paramValue);
    }

    //完成
    public static Object c_storeRouteList() throws QMException
    {
        TechnicsRouteListIfc routeListInfo = new TechnicsRouteListInfo();
        routeListInfo.setRouteListName("zlb_1工艺录像表000");
        routeListInfo.setRouteListNumber("000");
        routeListInfo.setRouteListDescription("zlb_1路线表000");
        routeListInfo.setProductMasterID("QMPartMaster_1110");
        routeListInfo.setRouteListLevel(RouteListLevelType.toRouteListLevelType("FIRSTROUTE").getDisplay());
        routeListInfo.setLocation("\\Root\\Administrator");
        routeListInfo.setLifeCycleTemplate("LifeCycleTemplate_204");
        Class[] paramType = {TechnicsRouteListIfc.class};
        Object[] paramValue = {routeListInfo};
        String methodName = "storeRouteList";
        return testHandler(null, methodName, paramType, paramValue);
    }

    //完成，二级路线未测试。
    public static Object c_getOptionalParts()
    {
        System.out.println("开始测试方法： getOptionalParts.....................");
        String codeID = "CodingClassification_305";
        String level = "一级路线";
        String productMasterID = "QMPartMaster_1110";
        Class[] paramType = {String.class, String.class, String.class};
        Object[] paramValue = {codeID, level, productMasterID};
        String methodName = "getOptionalParts";
        return testHandler(null, methodName, paramType, paramValue);
    }

    ///////////////////////////////测试方法结束//////////////////////////////
    public static Object testHandler(String sName, String methodName, Class[] paramType, Object[] paramValue)
    {
        Object obj = null;
        try
        {
            System.out.println("enter 客户端测试调用...................");
            ServiceRequestInfo requestInfo = new ServiceRequestInfo();
            if(sName == null)
            {
                requestInfo.setServiceName(serviceName);
            }else
            {
                requestInfo.setServiceName(sName);
            }
            requestInfo.setMethodName(methodName);
            requestInfo.setParaClasses(paramType);
            requestInfo.setParaValues(paramValue);
            obj = RequestHelper.request(requestInfo);
        }catch(QMException qmre)
        {
            System.out.println(qmre.getClientMessage());
            qmre.printStackTrace();
        }catch(Exception ex)
        {
            //System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
        return obj;
    }

    public static BaseValueIfc refreshInfo(String bsoid)
    {
        String serviceName = "PersistService";
        String methodName = "refreshInfo";
        Class[] paramType = {String.class};
        Object[] paramValue = {bsoid};
        return (BaseValueIfc)testHandler(serviceName, methodName, paramType, paramValue);
    }
}
