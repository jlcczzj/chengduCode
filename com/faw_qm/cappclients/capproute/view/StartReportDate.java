package com.faw_qm.cappclients.capproute.view;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.UIManager;

import com.faw_qm.acl.model.AccessSelectorListIfc;
import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.cappclients.capproute.controller.CappRouteAction;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;

public class StartReportDate extends CappRouteAction{

  public StartReportDate()
  {

  }
  public static void mainK(String[] args)
  {

	    try
	    {
	       System.setProperty("swing.useSystemFontSettings","0");
	       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    try
	    {
//	     String sid = RequestServer.getSessionID("10.0.227.3", "80", "Administrator", "jfv4r32010");
//	      CappClientRequestServer server = new CappClientRequestServer("10.0.227.3","80",sid);
	      
//		     String sid = RequestServer.getSessionID("10.0.227.5", "80", "Administrator", "jfv4r3up");
//		      CappClientRequestServer server = new CappClientRequestServer("10.0.227.5","80",sid);
			     String sid = RequestServer.getSessionID("localhost", "7001", "Administrator", "11111111");
			      CappClientRequestServer server = new CappClientRequestServer("localhost","7001",sid);
	      RequestServerFactory.setRequestServer(server);
	      StartReportDate s=new StartReportDate();
        s.quxiao();
       

        
	      System.out.println("-----------------------------over");
	    }
	    catch (ArrayIndexOutOfBoundsException e)
	    {
	      e.printStackTrace();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }

  }
  
  public static void main(String[] args)
  {

	    try
	    {
	       System.setProperty("swing.useSystemFontSettings","0");
	       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    try
	    {
//	     String sid = RequestServer.getSessionID("10.0.227.3", "80", "Administrator", "jfv4r32010");
//	      CappClientRequestServer server = new CappClientRequestServer("10.0.227.3","80",sid);
	      
//		     String sid = RequestServer.getSessionID("10.0.227.5", "80", "Administrator", "jfv4r3up");
//		      CappClientRequestServer server = new CappClientRequestServer("10.0.227.5","80",sid);
			     String sid = RequestServer.getSessionID("localhost", "7001", "Administrator", "11111111");
			      CappClientRequestServer server = new CappClientRequestServer("localhost","7001",sid);
	      RequestServerFactory.setRequestServer(server);
	      StartReportDate s=new StartReportDate();
          s.caiyong();
       

        
	      System.out.println("-----------------------------over");
	    }
	    catch (ArrayIndexOutOfBoundsException e)
	    {
	      e.printStackTrace();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }

  }
  
  
  
  
  public static void mainj(String[] args)
  {
      for (Iterator v = getBallotyesorno();
      v.hasNext(); ) {
    	  
    	  System.out.println("1111"+v.next());
      }

  }
  

  
  
  private static Iterator getBallotyesorno() {
	  System.out.println("22222");
Vector v=new Vector();
	String i[]={"1","2","3","4","5","6"};
	String k="1,2,3";
	for(int j=0;j<i.length;j++){
	v.add(i[j]);
	}
	return v.iterator();
}
public static void mainllll(String[] args)
  {
	    try
	    {
	       System.setProperty("swing.useSystemFontSettings","0");
	       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    try
	    {
	     String sid = RequestServer.getSessionID("10.0.227.3", "80", "Administrator", "jfv4r32010");
	      CappClientRequestServer server = new CappClientRequestServer("10.0.227.3","80",sid);
	      
//		     String sid = RequestServer.getSessionID("10.0.227.5", "80", "Administrator", "weblogic");
//		      CappClientRequestServer server = new CappClientRequestServer("10.0.227.5","80",sid);
	      RequestServerFactory.setRequestServer(server);
	      StartReportDate s=new StartReportDate();
        //  s.routelistpartindex("TechnicsRouteList_17983545");
       //   s.routelistpartindex("TechnicsRouteList_19548863");
//          s.routelistpartindex("TechnicsRouteList_19629723");
         // s.routelistpartindex("TechnicsRouteList_16655902");
//          s.routelistpartindex("TechnicsRouteList_16402429");
//          s.routelistpartindex("TechnicsRouteList_42040888");
          s.routelistpartindex("TechnicsRouteList_46109743");
	      System.out.println("-----------------------------over");
	    }
	    catch (ArrayIndexOutOfBoundsException e)
	    {
	      e.printStackTrace();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
  }
  
  public static void main7(String[] args)
  {
	    try
	    {
	       System.setProperty("swing.useSystemFontSettings","0");
	       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    try
	    {
	     String sid = RequestServer.getSessionID("10.0.227.3", "80", "Administrator", "jfv4r32010");
	      CappClientRequestServer server = new CappClientRequestServer("10.0.227.3","80",sid);
	      
//		     String sid = RequestServer.getSessionID("10.0.227.5", "80", "Administrator", "jfv4r3up");
//		      CappClientRequestServer server = new CappClientRequestServer("10.0.227.5","80",sid);
	      RequestServerFactory.setRequestServer(server);
	      StartReportDate s=new StartReportDate();
//          s.getpermission("Doc_39460099");
//          s.getpermission("Doc_39147059");
//          s.getpermission("Doc_39147109");
//          s.getpermission("Doc_39147498");
          s.getpermission("Doc_39460099");
         

          
	      System.out.println("-----------------------------over");
	    }
	    catch (ArrayIndexOutOfBoundsException e)
	    {
	      e.printStackTrace();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
  }
  
  public static void main4(String[] args)
  {
	    try
	    {
	       System.setProperty("swing.useSystemFontSettings","0");
	       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    try
	    {
//	     String sid = RequestServer.getSessionID("10.0.227.3", "80", "Administrator", "jfv4r32010");
//	      CappClientRequestServer server = new CappClientRequestServer("10.0.227.3","80",sid);
	      
		     String sid = RequestServer.getSessionID("10.0.227.5", "80", "Administrator", "jfv4r3up");
		      CappClientRequestServer server = new CappClientRequestServer("10.0.227.5","80",sid);
	      RequestServerFactory.setRequestServer(server);
	      StartReportDate s=new StartReportDate();
          s.changpermission();
         

          
	      System.out.println("-----------------------------over");
	    }
	    catch (ArrayIndexOutOfBoundsException e)
	    {
	      e.printStackTrace();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
  }
  private void changpermission() {
	  
  }

  private void quxiao() {
	
	    try {
	        Class[] c1 = {};
	        Object[] objs1 = {};
	        useServiceMethod(
	            "JFService", "handleHistoryCapp", c1, objs1);

	       System.out.println("----over");
//	       System.out.println("sssssssssssss" +
//					"+++++++++++++++++++++"+docInfo.getAdHocControlledMap().getAdHocAcl().getEntrySet().getValue());

	      }catch(Exception e){
	        e.printStackTrace();

	      }
}
  
  private void caiyong() {
		
	    try {
	        Class[] c1 = {String.class,String.class,String.class};
	        Object[] objs1 = {"采用","更改标记","工艺路线"};
	        CodingIfc info = (CodingIfc)useServiceMethod(
	            "CodingManageService", "findCodingByContent", c1, objs1);

	       System.out.println("----over"+info.getCodeContent());


	      }catch(Exception e){
	        e.printStackTrace();

	      }
}
  
  private void handleckwd() {
		
	    try {
	        Class[] c1 = {String.class};
	        Object[] objs1 = {"nosave"};
	        useServiceMethod(
	            "JFService", "handleckwd", c1, objs1);

	       System.out.println("----over");

	      }catch(Exception e){
	        e.printStackTrace();

	      }
}


private void getpermission(String docbsoid) {
	    try {
	        Class[] c1 = {String.class  };
	        Object[] objs1 = { docbsoid};
	        BaseValueIfc info = (BaseValueIfc)useServiceMethod(
	            "PersistService", "refreshInfo", c1, objs1);
	       DocIfc docInfo=(DocIfc)info;
	       System.out.println(docInfo.getAdHocAcl().getEntrySet());
//	       System.out.println("sssssssssssss" +
//					"+++++++++++++++++++++"+docInfo.getAdHocControlledMap().getAdHocAcl().getEntrySet().getValue());

	      }catch(Exception e){
	        e.printStackTrace();

	      }
	
}


public void routelistpartindex(String listbsoid) throws
  QMException {
	Vector vect=new Vector();
	HashMap al=new HashMap();
	HashMap kl=new HashMap(); 
	TechnicsRouteListIfc listInfo=null;

    try {
        Class[] c1 = {String.class  };
        Object[] objs1 = { listbsoid};
        BaseValueIfc info = (BaseValueIfc)useServiceMethod(
            "PersistService", "refreshInfo", c1, objs1);
        listInfo=(TechnicsRouteListIfc)info;

      }catch(Exception e){
        e.printStackTrace();

      }



QMQuery query = new QMQuery("ListRoutePartLink",
        "QMPartMaster");
QueryCondition cond1 = new QueryCondition("leftBsoID", QueryCondition.EQUAL,
                      listInfo.getBsoID());
query.addCondition(cond1);
query.addAND();
QueryCondition cond2 = new QueryCondition("alterStatus",
                      QueryCondition.NOT_EQUAL,
                      2);
query.addCondition(cond2);
QueryCondition cond3 = new QueryCondition("rightBsoID", "bsoID");
query.addAND();
query.addCondition(0, 1, cond3);
query.setVisiableResult(1);
query.addOrderBy(1, "partNumber");

try {
    Class[] c2 = {QMQuery.class  };
    Object[] objs2 = { query};
    Collection coll = (Collection)useServiceMethod(
        "PersistService", "findValueInfo", c2, objs2);
int i=0;
    for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
    	  ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
    	  QMPartMasterIfc partMasterInfo = linkInfo.getPartMasterInfo();
    	  al.put(String.valueOf(i),partMasterInfo.getBsoID());    	  
    	  System.out.println("-"+i+"  "+partMasterInfo);
    	  String [] ds={partMasterInfo.getBsoID(),linkInfo.getParentPartNum()};
    	  kl.put(String.valueOf(i),ds);
    	  i++;
    	}
    
  Class[] ccc1 = { String.class,HashMap.class};
  Object[] objsss1 = { listInfo.getProductMasterID(),al};
	 al = (HashMap)this.useServiceMethod("TechnicsRouteService",
          "getCounts", ccc1, objsss1);
    for(int ii=0;ii<al.size();ii++)
    {
    	String shul=al.get(String.valueOf(ii)).toString();
    	String kkk[]=(String[])kl.get(String.valueOf(ii));
    	String partmaster=kkk[0];
    	String pare=kkk[1];
    	System.out.println(partmaster+" "+pare+" "+shul);
    	String [] ids={partmaster,pare,shul};
    	vect.add(ids);
    }
    
  }catch(Exception e){
    e.printStackTrace();

  }
  listInfo.setPartIndex(vect);  
  try {
      Class[] c3 = {BaseValueIfc.class  };
      Object[] objs3 = { listInfo};
      useServiceMethod(
          "PersistService", "saveValueInfo", c3, objs3);


    }catch(Exception e){
      e.printStackTrace();

    }


}

public static void mainselectors(String[] args)
{

	    try
	    {
	       System.setProperty("swing.useSystemFontSettings","0");
	       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    try
	    {
//	     String sid = RequestServer.getSessionID("10.0.227.3", "80", "Administrator", "jfv4r32010");
//	      CappClientRequestServer server = new CappClientRequestServer("10.0.227.3","80",sid);
	      
//		     String sid = RequestServer.getSessionID("10.0.227.5", "80", "Administrator", "jfv4r3up");
//		      CappClientRequestServer server = new CappClientRequestServer("10.0.227.5","80",sid);
			     String sid = RequestServer.getSessionID("localhost", "7001", "Administrator", "11111111");
			      CappClientRequestServer server = new CappClientRequestServer("localhost","7001",sid);
	      RequestServerFactory.setRequestServer(server);
	      StartReportDate s=new StartReportDate();
      s.leix();
     

      
	      System.out.println("-----------------------------over");
	    }
	    catch (ArrayIndexOutOfBoundsException e)
	    {
	      e.printStackTrace();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }

}

public void leix() throws
QMException {


QMQuery query = new QMQuery("AccessSelectorList");

try {
  Class[] c2 = {QMQuery.class  };
  Object[] objs2 = { query};
  Collection coll = (Collection)useServiceMethod(
      "PersistService", "findValueInfo", c2, objs2);

  for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
	  AccessSelectorListIfc i=(AccessSelectorListIfc) iter.next();
	  System.out.println(""+i.getBsoID()+"---"+i.getAccessSelectorListMap().getSelectors());
  	}

  
}catch(Exception e){
  e.printStackTrace();

}


}
  
  public void routelistpartindex1(String listbsoid) throws
  QMException {
	Vector vect=new Vector();
	  
PersistService pservice = (PersistService) EJBServiceHelper.
    getPersistService();

TechnicsRouteListIfc listInfo=(TechnicsRouteListIfc)pservice.refreshInfo(listbsoid);

QMQuery query = new QMQuery("ListRoutePartLink",
        "QMPartMaster");
QueryCondition cond1 = new QueryCondition("leftBsoID", QueryCondition.EQUAL,
                      listInfo.getBsoID());
query.addCondition(cond1);
query.addAND();
QueryCondition cond2 = new QueryCondition("alterStatus",
                      QueryCondition.NOT_EQUAL,
                      2);
query.addCondition(cond2);
QueryCondition cond3 = new QueryCondition("rightBsoID", "bsoID");
query.addAND();
query.addCondition(0, 1, cond3);
query.setVisiableResult(1);
query.addOrderBy(1, "partNumber");
Collection coll = pservice.findValueInfo(query);
for (Iterator iter = coll.iterator(); iter.hasNext(); ) {
  ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc) iter.next();
  QMPartMasterIfc partMasterInfo = linkInfo.getPartMasterInfo();
  System.out.println("--"+partMasterInfo);
  String shuliang="1";
  String[] ids = {partMasterInfo.getBsoID(),linkInfo.getParentPartNum(),shuliang};
  vect.add(ids);

}

listInfo.setPartIndex(vect);

pservice.saveValueInfo(listInfo);

}
 
}
