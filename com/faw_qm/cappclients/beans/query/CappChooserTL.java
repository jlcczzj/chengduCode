/** ���ɳ���CappChooserTL.java    1.0  2003/11/29
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Description:CappChooserTL�дӱ������ݿ��м���ҵ������������
 * �������ɸ���ѡ�ı�ǩ�����ϡ�QmQuery������ҵ���������Ծ������ģ�
 * һ��CappSchema����QmQuery����������Ծ�����༰�����Եļ�����������ǩ���
 * �ͼ�����������CappSchema�����졣����ʹ��QmQueryʱ������CappSchema�Ǳ���ġ�
 * ��������Զ����б����ʽ��ʾ���û����Ե�����������ť��Ĭ�Ͻ���������ѡ��
 * ���Ե���setMultipleMode�������Ƶ�ѡ�����ʶ����б��б�ѡ�ж������ʹ��
 * getSelectedDetails()(��ѡ)��getSelectedDetail()(��ѡ)��
 * QmChooser��ChooseOptions�����ü���������ԡ�ʵ����QmChooserʱ���ṩ��bsoName
 * ����������ChooseOptions������Ӧ�Ķ���</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: FAW_QM Co Ltd</p>
 * @version 1.0
 * @see com.faw_qm.clients.beans.query.QmQuery
 * SS1 �޸��������ڵĸ߶�  ����ͮ 2013/11/29
 */


public class CappChooserTL extends CappQuery
{

    private CappScheme myScheme; //Ĭ�Ϸ���
    private CappSchemeDB mySchemeDB; //��������Դ
    private JDialog myDialog; //CappChooser����
    private static String OPTIONS_RESOURCE =
            "com.faw_qm.cappclients.beans.query.CappChooserOptions";
    private static boolean verbose = QM.getVerbose(); //debug��ʶ

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
     * Ĭ�Ϲ��췽��
     */
    public CappChooserTL()
    {
        this((String)null, "", null);
    }


    /**
     * �þ����ҵ�����bsoName�ͱ��⹹��ʵ��
     * @param bsoName ҵ�����bsoName
     * @param title ����
     */
    public CappChooserTL(String bsoName, String title)
    {
        this(bsoName, title, null);
    }


    /**
     * �þ����ҵ�����bsoName�����⣬�����壬�������������͹���ʵ��
     * @param bsoName ҵ�����bsoName
     * @param title ����
     * @param component ���������
     * @param filter_type ��������������
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
     * �þ����Ĭ�Ϸ��������⣬�����壬�������������͹���ʵ��
     * @param CappScheme  Ĭ�Ϸ���
     * @param title ����
     * @param component ���������
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
     * ��ʼ��CappChooser����
     * @param CappScheme ��������
     * @param title ����
     * @param component ������
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
     * ����ת��
     * @param actionevent
     */
    private void myList_ActionPerformed(ActionEvent actionevent)
    {
        processEvent(new CappQueryEvent(this, CappQueryEvent.COMMAND,
                                        CappQuery.OkCMD));
    }


    /**
     * �¼�����
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
     * ���ÿɼ���
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
     * ����������������
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
     * ���þ���bsoName��Ӧ��Ĭ�Ϸ���Ϊ��������
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
