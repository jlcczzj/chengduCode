package com.faw_qm.technics.consroute.test;

import java.util.Hashtable;

import java.util.Vector;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;

/**
 * <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2003</p> <p>Company: QM</p>
 * @author 赵立彬
 * @version 1.0
 */

public class RouteTest
{
    public static RequestServer server;
    public boolean VERBOSE = true;
    private static String sessionID = "AbWx5wUO21Iedcl7bfyVcyIdTM1v4Dbq2ciRlBUnGgH3VLkintG5!-1403960772!1083922161519";
    private static int i = 30;

    static
    {
        if(server == null)
        {
            try {
				server = new TestRequestServer("localhost", "80", sessionID.trim());
			} catch (QMException e) {
				e.printStackTrace();
			}
        }
    }

    public static void main(String[] args) throws Exception
    {
        //Object obj = callClientTest(i);
        Object obj = staticInvoke();
        System.out.println("返回值 = " + obj);
    }

    public static Object staticInvoke()
    {
        Object obj = null;
        try
        {
            System.out.println("enter callServiceTest...................");
            StaticMethodRequestInfo staticInfo = new StaticMethodRequestInfo();
            staticInfo.setClassName("com.faw_qm.technics.consroute.ejb.service.RouteListImportExportHandler");
            staticInfo.setMethodName("saveRouteList");
            Hashtable hashtable = new Hashtable();
            //routeListNumber~routeListName~routeListDescription~routeListLevel~routeListDepartment~productMasterID~location~lifecycleTemplate~projectid
            //zlb_list1000,zlb_list1000,description,一级路线,,QMPartMaster_34304,\Root\System,life1,
            hashtable.put("routeListNumber", "zlb_list1000");
            hashtable.put("routeListName", "zlb_list1000");
            hashtable.put("routeListDescription", "description");
            hashtable.put("routeListLevel", "一级路线");
            //hashtable.put("routeListDepartment","null");
            hashtable.put("productMasterID", "QMPartMaster_34304");
            hashtable.put("location", "\\Root\\System");
            hashtable.put("lifecycleTemplate", "life1");
            hashtable.put("projectid", "null");
            Vector vec = new Vector();
            Class[] classes = {Hashtable.class, Vector.class};
            Object[] objects = {hashtable, vec};
            staticInfo.setParaClasses(classes);
            staticInfo.setParaValues(objects);
            obj = RequestHelper.requestStaticServer(staticInfo);
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

    public static Object callClientTest(int i) throws Exception
    {
        return ClientTestHandler.callTest(i);
        //return callServiceTest(i);
    }

    public static Object callServiceTest(int i) throws Exception
    {
        Object obj = null;
        try
        {
            System.out.println("enter callServiceTest...................");
            ServiceRequestInfo requestInfo = new ServiceRequestInfo();
            requestInfo.setServiceName("TechnicsRouteService");
            requestInfo.setMethodName("processTest");
            Class[] classes = {int.class};
            Object[] objects = {new Integer(i)};
            requestInfo.setParaClasses(classes);
            requestInfo.setParaValues(objects);
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
}
