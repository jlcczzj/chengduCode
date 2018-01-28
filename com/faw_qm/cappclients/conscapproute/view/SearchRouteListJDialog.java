/**
 * ���ɳ���TechnicsSearchJDialog.java	1.1  2003/08/10
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p> Title: �����������������͹��ս��� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2003 </p> <p> Company:һ������ </p>
 * @author ����
 * @version 1.0
 */

public class SearchRouteListJDialog extends JDialog
{
    /** ��ѯ�� */
    private MyQuery cappQuery = new MyQuery();

    /** Ҫ��ѯ��ҵ����� */
    public static String SCHEMA;

    /** ��ѯ���� */
    private CappSchema mySchema;

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    /** ��������������� */
    private CappRouteListManageJFrame managerJFrame;

    /** ���ڱ����Դ�ļ�·�� */
    protected static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /** ���Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /**
     * ���췽��
     * @param frame ���������������
     */
    public SearchRouteListJDialog(CappRouteListManageJFrame frame)
    {
        super(frame, "", true);
        SCHEMA = "C:consTechnicsRouteList; G:��������;A:routeListNumber;A:routeListName;A:routeListLevel;A:versionValue";
        //�����ѯ����
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
     * ��ʼ��
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("��������·�߱�");
        this.setSize(650, 500);
        this.getContentPane().setLayout(gridBagLayout1);
        //���沼�ֹ���
        this.getContentPane().add(cappQuery, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
    }

    /**
     * ���Ĭ�ϲ�ѯ����
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
     * ��Ӳ�ѯ����
     * @param s ��ѯ����
     */
    public void addQueryListener(CappQueryListener s)
    {
        cappQuery.addListener(s);
    }

    /**
     * ɾ����ѯ����
     * @param s ��ѯ����
     */
    public void removeQueryListener(CappQueryListener s)
    {
        cappQuery.removeListener(s);
    }

    /**
     * ���������¼�����
     * @param e ���������¼�
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
                    //�Ѵ����°汾�Ĺ��տ������Ʒ�ṹ����
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
     * �����б�Ϊ��ѡģʽ
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
     * ���ý������ʾλ��
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
     * ���ظ��෽����ʹ������ʾ����Ļ����
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

    /** ��չ�����Ŀ����ʹ��ѯ����б�֧�����˫������ */
    class MyQuery extends CappQuery
    {
        public CappMultiList getResultList()
        {
            return this.getMyList();
        }
    }

    /**
     * MultiList����¼�
     * @param lis
     */
    public void addMultiListActionListener(ActionListener lis)
    {
        this.cappQuery.getResultList().addActionListener(lis);
    }

}
