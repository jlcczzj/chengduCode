/**
 * 生成程序TechnicsSearchJDialog.java	1.1  2003/08/10
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.conscapproute.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import com.faw_qm.cappclients.beans.query.*;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.cappclients.conscapproute.util.*;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.technics.consroute.model.*;
import java.beans.*;
import com.faw_qm.framework.util.QMMessage;

/**
 * <p> Title: 按基本属性搜索典型工艺界面 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2003 </p> <p> Company:一汽启明 </p>
 * @author 刘明
 * @version 1.0
 */

public class SearchRouteListJDialog extends JDialog
{
    /** 查询类 */
    private MyQuery cappQuery = new MyQuery();

    /** 要查询的业务对象 */
    public static String SCHEMA;

    /** 查询方案 */
    private CappSchema mySchema;

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    /** 工艺浏览器主界面 */
    private CappRouteListManageJFrame managerJFrame;

    /** 用于标记资源文件路径 */
    protected static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /** 测试变量 */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /**
     * 构造方法
     * @param frame 工艺浏览器主界面
     */
    public SearchRouteListJDialog(CappRouteListManageJFrame frame)
    {
        super(frame, "", true);
        SCHEMA = "C:consTechnicsRouteList; G:搜索条件;A:routeListNumber;A:routeListName;A:routeListLevel;A:versionValue";
        //定义查询方案
        try
        {
            mySchema = new CappSchema(SCHEMA);
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showWarningDialog(this, message);
            return;
        }
        cappQuery.setSchema(mySchema);
        cappQuery.notAcessInPersonalFolder();
        cappQuery.setLastIteration(true);
        managerJFrame = frame;
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 初始化
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("搜索工艺路线表");
        this.setSize(650, 500);
        this.getContentPane().setLayout(gridBagLayout1);
        //界面布局管理
        this.getContentPane().add(cappQuery, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
    }

    /**
     * 添加默认查询监听
     */
    public void addDefaultListener()
    {
        cappQuery.addListener(new CappQueryListener()
        {
            public void queryEvent(CappQueryEvent e)
            {
                cappQuery_queryEvent(e);
            }
        });
    }

    /**
     * 添加查询监听
     * @param s 查询监听
     */
    public void addQueryListener(CappQueryListener s)
    {
        cappQuery.addListener(s);
    }

    /**
     * 删除查询监听
     * @param s 查询监听
     */
    public void removeQueryListener(CappQueryListener s)
    {
        cappQuery.removeListener(s);
    }

    /**
     * 搜索监听事件方法
     * @param e 搜索监听事件
     */
    public void cappQuery_queryEvent(CappQueryEvent e)
    {
        if(verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsSearchJDialog:cappQuery_queryEvent(e) begin...");
        }
        if(e.getType().equals(CappQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(CappQuery.OkCMD))
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                MyQuery c = (MyQuery)e.getSource();
                BaseValueIfc[] bvi = c.getSelectedDetails();
                if(bvi != null)
                {
                    Vector v = new Vector();
                    //把从最新版本的工艺卡加入产品结构树中
                    for(int i = 0;i < bvi.length;i++)
                    {
                        RouteListTreeObject treeObject = new RouteListTreeObject((TechnicsRouteListInfo)bvi[i]);
                        v.addElement(treeObject);
                    }
                    managerJFrame.getTreePanel().addNodes(v);
                    managerJFrame.getTreePanel().setNodeSelected((RouteListTreeObject)v.elementAt(0));
                }
                this.setVisible(false);
                setCursor(Cursor.getDefaultCursor());
            }
            if(e.getCommand().equals(CappQuery.QuitCMD))
            {
                this.setVisible(false);
            }
        }

        if(verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsSearchJDialog:cappQuery_queryEvent(e) end...return is void");
        }
    }

    /**
     * 设置列表为单选模式
     */
    public void setSingleSelectMode()
    {
        try
        {
            cappQuery.setMultipleMode(false);
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 设置界面的显示位置
     */
    private void setViewLocation()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if(frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if(frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);

    }

    /**
     * 重载父类方法，使界面显示在屏幕中央
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        //String sid
        // ="1GBR1uG2JXVV0JwZU25ML8XFBBJRCOJ9dVpyEw2tTz2lcCAed529!960881029!1065763153564".trim();
        //TechnicsClientRequestServer server = new
        // TechnicsClientRequestServer("pdmlm","80",sid);
        //RequestServerFactory.setRequestServer(server);
        SearchRouteListJDialog frame = new SearchRouteListJDialog(null);
        frame.addDefaultListener();
        frame.setVisible(true);
    }

    /** 扩展父类的目的是使查询结果列表支持鼠标双击功能 */
    class MyQuery extends CappQuery
    {
        public CappMultiList getResultList()
        {
            return this.getMyList();
        }
    }

    /**
     * MultiList添加事件
     * @param lis
     */
    public void addMultiListActionListener(ActionListener lis)
    {
        this.cappQuery.getResultList().addActionListener(lis);
    }

}
