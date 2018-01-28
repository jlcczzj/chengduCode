/**
 * 
 */
package com;

import javax.swing.UIManager;

import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.part.client.main.controller.PartRequestServer;
import com.faw_qm.sysadmin.client.SysMainJFrame;

/**
 * @author Administrator
 *
 */
public class Test {

	/**
	 * 
	 */
	public Test() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		openSystm();
	}
	
	public static void openSystm(){
		try
        {
            //获得系统的风格
            System.setProperty("swing.useSystemFontSettings", "0");
            System.setProperty("swing.handleTopLevelPaint", "false");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //http://10.133.67.92/PhosphorPDM/aa.jsp
          String host = "10.133.67.92";
          String port = "80";
          String username = "Administrator";
          String userpassword = "weblogic";
          String sid = null;
          try
          {
              sid = RequestServer.getSessionID(host, port,
                      username, userpassword);
          }
          catch (Exception ex1)
          {
              ex1.printStackTrace();
          }
          PartRequestServer server = new PartRequestServer(host,
                  port, sid);
//          PartRequestServer server = new PartRequestServer(args[0],
//                  args[1], args[2]);
          

            RequestServerFactory.setRequestServer(server);
            SysMainJFrame sysMainJFrame = new SysMainJFrame(host,
                    port, sid);
            
//            if (args == null || args.length != 3)
//            {
//                throw new Exception(res.getString("invalid_parameter"));
//            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
	}

}
