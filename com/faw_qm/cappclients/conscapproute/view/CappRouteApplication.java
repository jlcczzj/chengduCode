/**
 * ���ɳ��� CappRouteApplication.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.view;

import javax.swing.UIManager;

import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RequestServer;

/**
 * <p> Title:����·�߱������������Ӧ�ó��� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class CappRouteApplication
{
    private boolean packFrame = false;

    /**
     * Construct the application
     */
    public CappRouteApplication()
    {
        CappRouteListManageJFrame frame = new CappRouteListManageJFrame();
        frame.setVisible(true);
    }

    //Main method
    public static void main(String[] args)
    {

        try
        {
            System.setProperty("swing.useSystemFontSettings", "0");
            //20080617 �촺Ӣ���
            System.setProperty("swing.handleTopLevelPaint", "false");
            System.out.println(System.getProperty("swing.useSystemFontSettings"));
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            if(args.length == 0)
            {
                //        String sid = RequestServer.getSessionID("192.168.0.124", "7001",
                //                                                "Administrator",
                //                                                "administrator");
                //        RequestServer server = new RequestServer("192.168.0.124", "7001",
                //                                                 sid) {};

                // 8888888888888888888888888888888
                String sid = RequestServer.getSessionID("localhost", "80", "huanghui", "11111111");
                RequestServer server = new RequestServer("localhost", "80", sid)
                {};
                //        String sid = RequestServer.getSessionID("localhost", "7001",
                //                                                             "zz3",
                //                                                             "11111111");
                //                     RequestServer server = new RequestServer("localhost", "7001",
                //                                                              sid) {};

            }else
            {
                RequestServer server = new RequestServer(args[0], args[1], args[2])
                {};
            }
        }catch(Exception e)
        {

            e.printStackTrace();
        }
        //    String pdmHome = "e:/v3r9/product/productfactory/phosphor/cpdm";
        //    int lastleft =  pdmHome.lastIndexOf("/");
        //   pdmHome =   pdmHome.substring(0,lastleft);
        //   System.out.println("let me see " + pdmHome);
        new CappRouteApplication();
    }
}
