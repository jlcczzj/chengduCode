/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.cappclients.conscapproute.view;

import javax.swing.UIManager;

import com.faw_qm.cappclients.capp.controller.*;
import com.faw_qm.framework.remote.*;

/**
 * <p>Title: 工艺路线管理主类</p> <p>Description: 客户端由此开始</p> <p>Copyright: Copyright (c) 2004</p> <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */

public class Application1
{
    private boolean packFrame = false;

    //Construct the application
    public Application1()
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
            //20080617 徐春英添加
            System.setProperty("swing.handleTopLevelPaint", "false");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            String sid = RequestServer.getSessionID("localhost", "80", "huanghui", "11111111");
            // String sid = RequestServer.getSessionID("pdm_gcy", "7001","dongf", "11111111");
            //  String sid = RequestServer.getSessionID("pdm_gcy", "7001",    "guanhy", "11111111");
            RequestServer server = new RequestServer("localhost", "80", sid);
            RequestServerFactory.setRequestServer(server);
            CappRouteListManageJFrame f = new CappRouteListManageJFrame();
            f.setVisible(true);
        }catch(ArrayIndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
