/**
 * ���ɳ��� CappRouteApplication.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �ݿͻ��鿴BOm xianglx 2014-8-28
 */
package com.faw_qm.cappclients.capproute.view;

import javax.swing.UIManager;

import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RequestServer;

//CCBegin SS1
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.capproute.util.RouteListTreeObject;
import java.util.Vector;
//CCEnd SS1
/**
 * <p>
 * Title:����·�߱������������Ӧ�ó���
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @version 1.0
 */

public class CappRouteApplication {
  private boolean packFrame = false;
 
  /**
   * Construct the application
   */
  public CappRouteApplication() {
    CappRouteListManageJFrame frame = new CappRouteListManageJFrame();
    frame.setVisible(true);
  }
//CCBegin SS1
  public CappRouteApplication(String bsoID) {
    CappRouteListManageJFrame frame = new CappRouteListManageJFrame();
    try {
	 		TechnicsRouteListInfo info = (TechnicsRouteListInfo)CappTreeHelper.refreshInfo(bsoID);
	  	RouteListTreeObject treeObject = new RouteListTreeObject(info);
	    Vector v = new Vector();
	    v.addElement(treeObject);
	 	 	frame.getTreePanel().addNodes(v);   
	  	frame.getTreePanel().setNodeSelected((RouteListTreeObject)v.elementAt(0)); 
   	}
    catch (Exception e) {

      e.printStackTrace();
    }
    frame.setVisible(true);
  }
//CCEnd SS1

  //Main method
  public static void main(String[] args) {

    try {
      System.setProperty("swing.useSystemFontSettings", "0");
      //20080617 �촺Ӣ���
      System.setProperty("swing.handleTopLevelPaint", "false");
      System.out.println(System.getProperty("swing.useSystemFontSettings"));
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      if (args.length == 0) {
//        String sid = RequestServer.getSessionID("192.168.0.124", "7001",
//                                                "Administrator",
//                                                "administrator");
//        RequestServer server = new RequestServer("192.168.0.124", "7001",
//                                                 sid) {};

        // 8888888888888888888888888888888
        String sid = RequestServer.getSessionID("localhost", "7001",
                                                      "Administrator",
                                                      "administrator");
              RequestServer server = new RequestServer("localhost", "7001",
                                                       sid) {};
//        String sid = RequestServer.getSessionID("localhost", "7001",
//                                                             "zz3",
//                                                             "11111111");
//                     RequestServer server = new RequestServer("localhost", "7001",
//                                                              sid) {};
//CCBegin SS1

    		new CappRouteApplication();

      }
      else if(args.length == 3) {
        RequestServer server = new RequestServer(args[0], args[1],
                                                 args[2]) {};
		    new CappRouteApplication();
      }else if(args.length == 4){//�ݿͻ��鿴BOm
        RequestServer server = new RequestServer(args[0], args[1],
                                                 args[2]) {};
      	String bsoID = args[3];
 		    new CappRouteApplication(bsoID);
      }
   }
//CCEnd SS1
    catch (Exception e) {

      e.printStackTrace();
    }
//    String pdmHome = "e:/v3r9/product/productfactory/phosphor/cpdm";
//    int lastleft =  pdmHome.lastIndexOf("/");
//   pdmHome =   pdmHome.substring(0,lastleft);
//   System.out.println("let me see " + pdmHome);
//CCBegin SS1
// 		    new CappRouteApplication(bsoID);
//CCEnd SS1
  }
}
