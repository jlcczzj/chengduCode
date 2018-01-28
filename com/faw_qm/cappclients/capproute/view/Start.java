package com.faw_qm.cappclients.capproute.view;

import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import javax.swing.UIManager;
import com.faw_qm.cappclients.capproute.controller.CappRouteAction;

public class Start extends CappRouteAction{

  public Start()
  {

  }
  public void getServer(){

      try {
        Class[] c = {  };
        Object[] objs = { };
        useServiceMethod(
            "TechnicsRouteService", "getAddData", c, objs);

      }catch(Exception e){
        e.printStackTrace();
      }

}

  //Main method
  public static void main1(String[] args)
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
     String sid = RequestServer.getSessionID("localhost", "7001", "Administrator", "weblogic");
      CappClientRequestServer server = new CappClientRequestServer("localhost","7001",sid);
      RequestServerFactory.setRequestServer(server);
      Start s=new Start();
      s.getServer();
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
  

  

}

