/** 生成程序CappChooserTL.java    1.0  2003/11/29
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */


package com.faw_qm.cappclients.beans.query;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;


/**
 * <p>Title:CappChooserTL</p>
 * <p>Description:CappChooserTL有从本地数据库中检索业务对象的能力。
 * 它由若干个可选的标签面板组合。QmQuery检索的业务对象是针对具体的类的，
 * 一个CappSchema对象被QmQuery用来定义针对具体的类及其属性的检索方案。标签面板
 * 和检索方案根据CappSchema对象构造。所以使用QmQuery时，定义CappSchema是必须的。
 * 检索结果以多列列表的形式显示。用户可以点击“清除”按钮，默认结果区允许多选，
 * 可以调用setMultipleMode方法限制单选。访问多列列表中被选中对象可以使用
 * getSelectedDetails()(多选)和getSelectedDetail()(单选)。
 * QmChooser用ChooseOptions来配置检索类和属性。实例化QmChooser时，提供的bsoName
 * 参数必须在ChooseOptions中有相应的定义</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: FAW_QM Co Ltd</p>
 * @version 1.0
 * @see com.faw_qm.clients.beans.query.QmQuery
 * SS1 修改搜索窗口的高度  马晓彤 2013/11/29
 */


public class CappChooserTL extends CappQuery
{

    private CappScheme myScheme; //默认方案
    private CappSchemeDB mySchemeDB; //方案数据源
    private JDialog myDialog; //CappChooser容器
    private static String OPTIONS_RESOURCE =
            "com.faw_qm.cappclients.beans.query.CappChooserOptions";
    private static boolean verbose = QM.getVerbose(); //debug标识

    class SymAction implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            Object obj = actionevent.getSource();
            if (obj == getMyList())
            {
                myList_ActionPerformed(actionevent);
            }
        }

        SymAction()
        {
        }
    }


    class QueryListener implements CappQueryListener
    {
        public void queryEvent(CappQueryEvent qmqueryevent)
        {
            if (qmqueryevent.getType().equalsIgnoreCase(CappQueryEvent.COMMAND))
            {
                if (qmqueryevent.getCommand().equalsIgnoreCase(CappQueryEvent.
                        CLOSE))
                {
                    if (myDialog != null)
                    {
                        paginatePanel.clearResultCache();
                        myDialog.dispose();
                        return;
                    }
                    else
                    {
                        setVisible(false);
                        return;
                    }
                }
                if (qmqueryevent.getCommand().equalsIgnoreCase(CappQuery.OkCMD))
                {
                    /**
                               if (myDialog != null) {
                                 myDialog.dispose();
                                 return;
                               }
                     **/
                    setVisible(false);
                }
            }
        }

        QueryListener()
        {

        }
    }


    /**
     * 默认构造方法
     */
    public CappChooserTL()
    {
        this((String)null, "", null);
    }


    /**
     * 用具体的业务类的bsoName和标题构建实例
     * @param bsoName 业务类的bsoName
     * @param title 标题
     */
    public CappChooserTL(String bsoName, String title)
    {
        this(bsoName, title, null);
    }


    /**
     * 用具体的业务类的bsoName，标题，父窗体，检索过滤器类型构建实例
     * @param bsoName 业务类的bsoName
     * @param title 标题
     * @param component 父窗体组件
     * @param filter_type 检索过滤器类型
     */
    public CappChooserTL(String bsoName, String title, Component component
                       )
    { //, String filter_type
        super(title, true); //filter_type,
        ListResourceBundle listresourcebundle = null;
        try
        {
            listresourcebundle = (ListResourceBundle) ResourceBundle.getBundle(
                    OPTIONS_RESOURCE, Locale.getDefault());
        }
        catch (MissingResourceException missingresourceexception)
        {
            missingresourceexception.printStackTrace();
            QM.showMessageDialog(component,
                                 missingresourceexception.getLocalizedMessage());
            missingresourceexception.printStackTrace();
            return;
        }
        Object aobj[][] = ((CappChooserOptions) listresourcebundle).getContents();
        String s3 = "C:" + bsoName + ";";
        for (int i = 0; i < aobj.length; i++)
        {
            if (((String) aobj[i][1]).startsWith(s3))
            {
                Object aobj1[][] =
                        {
                        {
                        aobj[i][0], aobj[i][1]
                }
                };
                try
                {
                    mySchemeDB = new CappSchemeDB(aobj1);
                }
                catch (Exception ex)
                {
                   ex.printStackTrace();
                    String extitle = QMMessage.getLocalizedMessage(
                            "com.faw_qm.cappclients.capp.util.CappLMRB",
                            "exception", null);
                    JOptionPane.showMessageDialog(myDialog, ex.getLocalizedMessage(),
                                                  extitle,
                                                  JOptionPane.ERROR_MESSAGE);
                    return;
                }
                setup(mySchemeDB.getSchemeByClass(bsoName), title, component);
                return;
            }
        }
    }


    /**
     * 用具体的默认方案，标题，父窗体，检索过滤器类型构建实例
     * @param CappScheme  默认方案
     * @param title 标题
     * @param component 父窗体组件
     */
    public CappChooserTL(CappScheme qmscheme, String title, Component component
                       )
    { //, String filter_type
        super(title, true); // filter_type,
        setup(qmscheme, title, component); //, filter_type
    }

    public void show()
    {
        synchronized (this)
        {
            Container container = myDialog.getParent();
            if (container.isShowing())
            {
                Point point1 = container.getLocationOnScreen();
                Rectangle rectangle2 = container.getBounds();
                Rectangle rectangle3 = getBounds();
                int i = point1.x +
                        (rectangle2.width - rectangle3.width) / 2;
                if (i < 0)
                {
                    i = 0;
                }
                int j = point1.y +
                        (rectangle2.height - rectangle3.height) / 2;
                if (j < 0)
                {
                    j = 0;
                }
                setLocation(i, j);
            }
            else
            {
                setLocation(0, 0);
            }
        }
        super.show();
    }

    /**
     * 初始化CappChooser界面
     * @param CappScheme 检索方案
     * @param title 标题
     * @param component 父窗体
     */
    private void setup(CappScheme qmscheme, String title, Component component
                       )
    { //,String filter_type
        QM.getMessagesResource();
        myScheme = qmscheme;
        if (myScheme != null)
        {
            try
            {
                setSchema(myScheme.getSchema());
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
                String extitle = QMMessage.getLocalizedMessage(
                        "com.faw_qm.cappclients.capp.util.CappLMRB",
                        "exception", null);
                JOptionPane.showMessageDialog(myDialog, ex.getLocalizedMessage(),
                                              extitle,
                                              JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        if (component != null)
        {
            if (component instanceof JDialog) {
                myDialog = new JDialog((JDialog) component, title, true);
            }else
            {
                for(;!(component instanceof Frame);component = component.getParent())
                {
                    ;
                }
                myDialog = new JDialog((Frame) component, title, true);
            }
			this.show();
            myDialog.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent windowevent)
                {
                    if (windowevent.getSource() == myDialog)
                    {
                        myDialog.dispose();
                    }
                }
            });
            GridBagLayout gbl = new GridBagLayout();
            Container c = myDialog.getContentPane();
            c.setLayout(gbl);
            c.add(this, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.BOTH,
                                               new Insets(20, 20, 0, 20), 0, 0));
            myDialog.setResizable(true);
            if (myScheme != null)
            {
            	//SS1 BEGIN
                myDialog.setSize(660, 700);
                //SS1 END
                if (component.isShowing())
                {
                    Point point = component.getLocationOnScreen();
                    Rectangle rectangle = component.getBounds();
                    Rectangle rectangle1 = myDialog.getBounds();
                    myDialog.setLocation(point.x +
                                         (rectangle.width - rectangle1.width) /
                                         2,
                                         point.y +
                                         (rectangle.height - rectangle1.height) /
                                         2);
                }
                else
                {
                    myDialog.setLocation(0, 0);
                }
            }
        }
        PropertyChangeListener propertychangelistener = newHelpListener();
        addListener(new QueryListener());
        SymAction symaction = new SymAction();
        getMyList().addActionListener(symaction);
    }


    /**
     * 监听转换
     * @param actionevent
     */
    private void myList_ActionPerformed(ActionEvent actionevent)
    {
        processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                        CappQuery.OkCMD));
    }


    /**
     * 事件处理
     * @param CappQueryEvent
     */
    private synchronized void processEvent(CappQueryEvent qmqueryevent)
    {
        for (int i = 0; i < getMyListener().size(); i++)
        {
            CappQueryListener qmquerylistener = (CappQueryListener)
                                                getMyListener().
                                                elementAt(i);
            qmquerylistener.queryEvent(qmqueryevent);
        }

    }


    /**
     * 设置可见性
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        if (myDialog != null)
        {
            paginatePanel.clearResultCache();
            myDialog.setVisible(flag);
            return;
        }
        else
        {
        	 paginatePanel.clearResultCache();
            super.setVisible(flag);
            return;
        }
    }


    /**
     * 帮助监听工厂方法
     * @return
     */
    PropertyChangeListener newHelpListener()
    {
        return new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent propertychangeevent)
            {
                if (propertychangeevent.getPropertyName().equals(
                        "HelpContext.toolDescription"))
                {
                    try
                    {
                        setStatus((String) propertychangeevent.getNewValue());
                        return;
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                    return;
                }
                else
                {
                    return;
                }
            }
        };
    }


    /**
     * 设置具体bsoName对应的默认方案为检索方法
     * @param bsoName
     */
    public void setClass(String bsoName)
    throws QMRemoteException
    {
        myScheme = mySchemeDB.getSchemeByClass(bsoName);
        if (myScheme != null)
        {
            setSchema(myScheme.getSchema());
        }
        refresh();
    }
}
