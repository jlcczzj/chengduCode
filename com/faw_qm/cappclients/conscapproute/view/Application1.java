/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

package com.faw_qm.cappclients.conscapproute.view;

import javax.swing.UIManager;

import com.faw_qm.cappclients.capp.controller.*;
import com.faw_qm.framework.remote.*;

/**
 * <p>Title: ����·�߹�������</p> <p>Description: �ͻ����ɴ˿�ʼ</p> <p>Copyright: Copyright (c) 2004</p> <p>Company: һ������</p>
 * @author ����
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
            //20080617 �촺Ӣ���
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
