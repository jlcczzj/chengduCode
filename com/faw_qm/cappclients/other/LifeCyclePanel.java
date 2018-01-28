/**
 * ����ResourceSelectDialog.java 1.0 12/8/2003
 * ��Ȩ��һ��������˾����
 * ����������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/06/19 л�� ԭ�򣺻������Խ��桰״̬����û�ж��롣TD-2233
 *                     �����������������ں���״̬ʹ�õ���һ����壬����Ԫ�ر仯ʱ�޷�����ͳһ��Ӧ���ָ�Ϊ״̬Ϊ������壬����ͳһ��Ӧ����仯��
 * CR2 2009/07/03 ��� ԭ��TD2515 �����Ʒ��Ϣ������������ĸ��ڵ��Ʒ�ṹ,�ұ߻�������ҳ���ϼд���ʾ��\Root\Administrator"��
 *                          ���㲿���ϰѽ����ƻظ����㣬�������������������Ϣ��
 *                     �����������ڸ����ʱ����ջ�������������Ϣ           
 * CR3 2009/10/28 ����  �޸�ԭ��ͳһ�ͻ��˻򹤾�����÷���ķ�ʽ��         
 * CR4 2011/05/16 �ֺ��� �޸�ԭ��Ϊ����������ʾ��ʶ��ǩ�������������
 */
package  com.faw_qm.cappclients.other;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.util.RefreshService;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMExceptionHandler;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.model.LifeCycleTemplateMasterIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateMasterInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.persist.util.QueryCondition;

/**
 * <p> Title: ��������JavaBean </p>
 * @author �����
 * @version 1.0
 */
public class LifeCyclePanel extends JPanel
{
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private String name = "";

    private JComboBox createChoice = new JComboBox();

    private Object lock = new Object();

    /**
     * CREATE_MODEģʽ <p> Create Mode allows the user to assign a Life Cycle template to a LifeCycleManaged object, a Life Cycle label is displayed to the left of the choice box. </P>
     */
    public static final int CREATE_MODE = 0;

    /**
     * VIEW_MODEģʽ <p> No Label Create Mode allows the user to assign a Life Cycle template to a LifeCycleManaged object. </P>
     */
    public static final int VIEW_MODE = 1;

    /**
     * NO_LABEL_CREATE_MODEģʽ <p> No Label Update Mode allows the user to assign a Life Cycle template to a LifeCycleManaged object </p>
     */
    public static final int NO_LABEL_CREATE_MODE = 2;

    /**
     * NO_LABEL_VIEW_MODEģʽ <p> No Label View Mode displays the name of the Life Cycle template and Life Cycle state associated with a LifeCycleManaged object </p>
     */
    public static final int NO_LABEL_VIEW_MODE = 3;

    /**
     * UPDATE_MODEģʽ <p> Update Mode allows the user to assign a Life Cycle template to a LifeCycleManaged object. </p>
     */
    public static final int UPDATE_MODE = 4;

    /**
     * NO_LABEL_UPDATE_MODEģʽ <p> View Mode displays the name of the Life Cycle template and Life Cycle state associated with a LifeCycleManaged objec </p>
     */
    public static final int NO_LABEL_UPDATE_MODE = 5;

    static final int PROCESS_BROWSE = 0;

    static final int INIT_CHOICE_LIST = 1;

    private boolean notifyDone;

    private boolean initReady;

    private String VIEW_CARD;

    private String CREATE_CARD;

    // ģʽ
    private int viewMode;

    private boolean cardLayoutInitialized;

    // ��Դ�ļ�
    private static final String RESOURCE = "com.faw_qm.clients.beans.lifecycle.BeansLifeCycleRB";

    // ���Ա�־
    private boolean verbose = (RemoteProperty.getProperty("com.faw_qm.clients.beans.verbose", "true")).equals("true");

    // ��ʱ������������
    private Hashtable lifecycles;

    // ���ֹ�����
    private LayoutManager layout;

    // BsoName
    private String aBsoName;

    // ʵ��LifeCycleManagedIfc�Ķ���
    private LifeCycleManagedIfc anObject;

    // ���ڹ��ʻ�
    private ResourceBundle messagesResource;

    // ����һ���������������
    private RequestServer server = null;

    private CardLayout cardLayout1 = new CardLayout();

    private JPanel createPanel = new JPanel();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JLabel createLabel = new JLabel();

    private JButton browseButton = new JButton();

    private JPanel viewPanel = new JPanel();

    private JLabel viewLifeCycleValue = new JLabel();

    private JLabel viewLabel = new JLabel();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    private static ResourceBundle resourcebundle = null;

    // Ϊ�˽���ڵ���getTemplate()ʱ����Ȩ�޲�������ò���ģ��ʱ�ܹ�����ʺϵ��쳣��Ϣ
    private String exceptionMessage = "";

    /** browseButton������ */
    class SymAction implements ActionListener
    {
        /** ʵ��actionPerformed���� */
        public void actionPerformed(ActionEvent actionevent)
        {
            Object obj = actionevent.getSource();
            if(obj == browseButton)
            {
                // ����ʵ�ʴ���ķ���
                browseButton_ActionPerformed(actionevent);
            }
        }

        SymAction()
        {}
    }

    /** ���Դ��� */
    private static class DriverFrame extends JFrame
    {
        // constructor
        public DriverFrame()
        {
            addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent windowevent)
                {
                    dispose();
                    System.exit(0);
                }
            });
            this.getContentPane().setLayout(new BorderLayout());
            // new LifeCyclePanel
            LifeCyclePanel lifecyclepanel = new LifeCyclePanel();
            setSize(300, 100);
            getContentPane().add(lifecyclepanel);
            // set Mode
            lifecyclepanel.setMode(LifeCyclePanel.VIEW_MODE);
            lifecyclepanel.viewLifeCycleValue.setText("kkkkkkkkkkk" + "  test");
        }
    }

    /**
     * Ĭ�Ϲ����� <p> ����һ��LifeCyclePanel��Ĭ�ϵ�ģʽ��CREATE_MODE </p>
     */
    public LifeCyclePanel()
    {
        notifyDone = false;
        initReady = false;
        // panel card
        VIEW_CARD = "viewPanel";
        CREATE_CARD = "createPanel";
        viewMode = 0;
        // �Ƿ񲼾�
        cardLayoutInitialized = false;
        server = RequestServerFactory.getRequestServer();
        try
        {
            jbInit();
        }catch(Exception e)
        {}
        // �ֲ���
        localize();
        lifecycles = new Hashtable();
        // add ActionListener
        browseButton.addActionListener(new SymAction());
    }

    public synchronized void removeNotify()
    {
        super.removeNotify();
    }

    public void addNotify()
    {
        super.addNotify();
        notifyDone = true;
        if(initReady)
        {
            initChoiceList();
            initValues();
        }
    }

    /** ����������ѡ��С */
    public Dimension getPreferredSize()
    {
        return new Dimension(100, 25);
    }

    /**
     * Ϊ�������ò��ֹ�����
     * @param layoutmanager ���ֹ�����
     */
    public void setLayout(LayoutManager layoutmanager)
    {
        if(cardLayoutInitialized)
        {
            return;
        }else
        {
            super.setLayout(layoutmanager);
            layout = layoutmanager;
            cardLayoutInitialized = true;
            return;
        }
    }

    /** ��������Ĳ��ֹ����� */
    public LayoutManager getLayout()
    {
        if(cardLayoutInitialized)
        {
            return layout;
        }else
        {
            return null;
        }
    }

    /**
     * ���ð�ť��
     * @param name
     */
    public void setButtonName(String name)
    {
        browseButton.setText(name);
    }

    /**
     * ���������ť�����Ƿ�
     * @param i int
     */
    public void setButtonMnemonic(int i)
    {
        browseButton.setText(resourcebundle.getString("0") + "(" + (char)i + ")" + ". . .");
        browseButton.setMnemonic(i);
    }

    private void localize()
    {
        resourcebundle = getMessagesResource();
        String s = resourcebundle.getString("3") + resourcebundle.getString("1");
        viewLabel.setText(s);
        createLabel.setText(s);
        //        viewStateLabel.setText(resourcebundle.getString("2"));
        browseButton.setText(resourcebundle.getString("0") + "(B)" + ". . .");
    }

    // Using a ListResourceBundle
    private ResourceBundle getMessagesResource()
    {
        try
        {
            if(messagesResource == null)
            {
                messagesResource = ResourceBundle.getBundle("com.faw_qm.clients.beans.lifecycle.BeansLifeCycleRB");
            }
        }catch(MissingResourceException missingresourceexception)
        {}
        return messagesResource;
    }

    /**
     * ʹ�������
     * @param flag boolean
     */
    public void setEnabled(boolean flag)
    {
        int i = getMode();
        if(i == 0 || i == 2 || i == 4 || i == 5)
        {
            createChoice.setEnabled(flag);
            browseButton.setEnabled(flag);
        }
    }

    // ��ʼ�����������б����ù����߳�
    private void initChoiceList() 
    {
        try
        {
            setEnabled(false);
            processInitChoiceList();
        }finally
        {
            setEnabled(true);
        }
    }

    private void initValues()
    {
        synchronized(lock)
        {
            // System.out.println("�����������ڳ�ʼ��ֵ=====================================");
            if(anObject == null)
            {
                return;
            }
            try
            {
                name = anObject.getLifeCycleTemplate();
                LifeCycleState s = anObject.getLifeCycleState();
                if(name != null)
                {
                    Class[] paraClass = {String.class,boolean.class};
                    Object[] obj = {name,false};
                    LifeCycleTemplateInfo templateInfo = (LifeCycleTemplateInfo)invokeServiceMethod("PersistService", "refreshInfo", paraClass, obj);
                    try{
                    viewLifeCycleValue.setText(templateInfo.getLifeCycleName() + "  " + s.getDisplay());
                    }catch(Exception ex){
                        // System.out.println("Ҫ���õ�����������:"+templateInfo.getLifeCycleName());
                    }
                    createChoice.setSelectedItem(templateInfo.getLifeCycleName());
                }
                //CR2 Begin zhangq 
                else
                {
                    viewLifeCycleValue.setText("");
                }
                //CR2 End zhangq
                return;
            }catch(Exception lifecycleexception)
            {
                //20080414 zhangq begin:��ͨ�û��鿴�㲿��ʱ��������㲿��ʹ�õ��������ڱ����˼��������������
                //				JOptionPane.showMessageDialog(this.getParent(),
                //						lifecycleexception.getLocalizedMessage(),
                //						resourcebundle.getString("14"),
                //						JOptionPane.ERROR_MESSAGE);
                String message = QMExceptionHandler.handle(lifecycleexception);
                JOptionPane pane = new JOptionPane(message, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION, null, null, null);
                pane.setInitialValue(null);
                Component parentComponent = this.getParent();
                pane.setComponentOrientation(((parentComponent == null) ? JOptionPane.getRootFrame() : parentComponent).getComponentOrientation());
                JDialog dialog = pane.createDialog(parentComponent, resourcebundle.getString("17"));
                dialog.pack();
                dialog.setVisible(true);
                //                dialog.dispose();
                //20080414 zhangq end
                return;
            }
        }
    }

    // �����������б��м���һ��
    private void addTemplate(LifeCycleTemplateMasterInfo lifecycletemplatemasterInfo)
    {
        String s = lifecycletemplatemasterInfo.getLifeCycleName();
        if(verbose)
        {
            System.out.println("---Inside addTemplate :" + s);
        }
        if(!lifecycles.containsKey(s))
        {
            if(verbose)
            {
                System.out.println("LifeCyclePanel: Adding lifecyle " + lifecycletemplatemasterInfo);
            }
            lifecycles.put(s, lifecycletemplatemasterInfo);
            createChoice.addItem(s);
        }
    }

    public void setBrowseButtonSize(Dimension dim)
    {
        browseButton.setPreferredSize(dim);
        browseButton.setMaximumSize(dim);
        browseButton.setMinimumSize(dim);
    }

    // ��ʼ�����������б�
    private void processInitChoiceList() 
    {
        synchronized(lock)
        {
            if(!notifyDone)
            {
                initReady = true;
                return;
            }
            if(verbose)
            {
                System.out.println("LifeCyclePanel: addNotify called = " + notifyDone);
                System.out.println("An object, template = " + anObject + "," + getTemplate(anObject));
            }
            Enumeration enumeration = null;
            Vector vector = null;
            createChoice.removeAllItems();
            lifecycles = new Hashtable();
            try
            { 
                if(anObject != null)
                {
                    Class[] paraClass = {LifeCycleManagedIfc.class};
                    Object[] objs = {anObject};
                    vector = (Vector)invokeServiceMethod("LifeCycleService", "findCandidateMaster", paraClass, objs);
                }else
                {
                    if(aBsoName != null)
                    {
                        Class[] paraClass = {String.class};
                        Object[] objs = {aBsoName};
                        vector = (Vector)invokeServiceMethod("LifeCycleService", "findCandidateMaster", paraClass, objs);
                    }else
                    {
                        return;
                    }
                }
            }catch(Exception exception)
            {
                String message = QMExceptionHandler.handle(exception);
                DialogFactory.showWarningDialog(getParent(), message);
            }
            if(vector != null)
            {
                enumeration = vector.elements();
                LifeCycleTemplateMasterInfo lifecycletemplatemasterInfo = null;
                try
                {
                    if(enumeration != null)
                    {
                        for(;enumeration.hasMoreElements();addTemplate(lifecycletemplatemasterInfo))
                        {
                            lifecycletemplatemasterInfo = (LifeCycleTemplateMasterInfo)enumeration.nextElement();
                        }
                    }
                }catch(Exception exception)
                {
                    String message = QMExceptionHandler.handle(exception);
                    DialogFactory.showWarningDialog(getParent(), message);
                }
                LifeCycleTemplateInfo lifecycletemplateInfo = getTemplate(anObject);
                if(lifecycletemplateInfo == null)
                {
                    Class[] paraClass = {String.class};
                    String lifecycleName = RemoteProperty.getProperty("com.faw_qm.lifecycle." + aBsoName + ".defaultLifeCycle", "ȱʡ");
                    if(lifecycletemplateInfo != null)
                    {
                        lifecycleName = lifecycletemplateInfo.getLifeCycleName();
                    }
                    Object[] objs = {lifecycleName};
                    try
                    {
                        lifecycletemplatemasterInfo = (LifeCycleTemplateMasterInfo)invokeServiceMethod("LifeCycleService", "getLifeCycleTemplateMaster", paraClass, objs);
                    }catch(Exception e)
                    {
                        String message = QMExceptionHandler.handle(e);
                        DialogFactory.showWarningDialog(getParent(), message);
                        return;
                    }
                }
                if(lifecycletemplatemasterInfo != null)
                {
                    addTemplate(lifecycletemplatemasterInfo);
                    // ///wbb 2005.6.29Ϊ���޸Ĵ���״̬ѡ��Ĭ����������
                    name = lifecycletemplatemasterInfo.getLifeCycleName();
                    createChoice.setSelectedItem(name);
                    // /////////////////////////////////////////////
                }
            }
        }
    }

    /**
     * ����BsoName�����ڻ��һ���������ڵ��б�
     * @param BsoName String
     * @ 
     */
    public void setBsoName(String BsoName) 
    {
        aBsoName = BsoName;
        // init ChoiceList
        initChoiceList();
        initValues();
    }

    /** �������ڵ�ģʽ */
    public int getMode()
    {
        return viewMode;
    }

    /**
     * ����ģʽ��create �� view
     * @param i create �� view
     */
    public void setMode(int i)
    {
        viewMode = i;
        if(verbose)
        {
            System.out.println("In  viewMode<" + viewMode + ">");
        }
        switch(i)
        {
        case 0: // '\0'
        case 4: // '\004'
            createLabel.setVisible(true);
            ((CardLayout)layout).show(this, CREATE_CARD);
            return;
        case 1: // '\001'
            viewLabel.setVisible(true);
            ((CardLayout)layout).show(this, VIEW_CARD);
            return;
        case 2: // '\002'
        case 5: // '\005'
            createLabel.setVisible(false);
            ((CardLayout)layout).show(this, CREATE_CARD);
            return;
        case 3: // '\003'
            viewLabel.setVisible(false);
            ((CardLayout)layout).show(this, VIEW_CARD);
            return;
        }
    }

    /**
     * Return the currently selected lifecycle LifeCycleTemplateInfo.
     * @
     */
    public LifeCycleTemplateInfo getSelectedLifeCycleTemplate() 
    {
        String s = (String)createChoice.getSelectedItem();
        name = s;
        LifeCycleTemplateInfo lifecycletemplateInfo = null;
        if(s != null)
        {
            LifeCycleTemplateMasterInfo lifecycletemplatemasterInfo = (LifeCycleTemplateMasterInfo)lifecycles.get(s);
            try
            {
                Class[] paraClass = {String.class,boolean.class};
                Object[] objs = {lifecycletemplatemasterInfo.getLifeCycleName(),false};
                // ����Ϣ���ݵ��������˽��д���
                return (LifeCycleTemplateInfo)invokeServiceMethod("LifeCycleService", "getLifeCycleTemplate", paraClass, objs);
                // lifecycletemplateInfo =
                // ((LifeCycleTemplateMaster)lifecycletemplatemasterInfo.getObject()).getLifeCycleTemplateReference();
            }catch(Exception e)
            {
                String message = QMExceptionHandler.handle(e);
                DialogFactory.showWarningDialog(getParent(), message);
                return null;
            }
        }
        return lifecycletemplateInfo;
    }

    // ���LifeCycleManagedIfc�������������ģ���ֵ����
    private LifeCycleTemplateInfo getTemplate(LifeCycleManagedIfc lifecyclemanagedIfc) 
    {
        if(lifecyclemanagedIfc == null)
        {
            return null;
        }else
        {
            String template = lifecyclemanagedIfc.getLifeCycleTemplate();
            if(template == null || template.equals(""))
            {
                exceptionMessage = QMMessage.getLocalizedMessage(RESOURCE, "16", null);
                return null;
            }
            Class[] paraClass = {String.class,boolean.class};
            Object[] objs = {template,false};
            // ����Ϣ���ݵ��������˽��д���
            LifeCycleTemplateInfo i = null;
            try {
				i = (LifeCycleTemplateInfo)invokeServiceMethod("PersistService", "refreshInfo", paraClass, objs);
			} catch (QMException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}
            return i;
        }
    }

    /**
     * Set the LifeCycleMangedIfc object <p> The object is used to initialize the Life Cycle choice list in create mode. </p> <p> In view mode the object is used to obtain the current values of </p>
     * <p> the Life Cycle template name and Life Cycle state for display </p>
     * @ 
     */
    public void setObject(LifeCycleManagedIfc lifecyclemanagedIfc) 
    {
        synchronized(lock)
        {
            // LifeCycleManaged qmPart
            // System.out.println("-------------------------������������Panel:"+lifecyclemanagedIfc.getLifeCycleTemplate());
            anObject = lifecyclemanagedIfc;
            aBsoName = lifecyclemanagedIfc.getBsoName();
            // aClass = lifecyclemanagedIfc.getClass();
            // if(verbose)
            // System.out.println("aClass = an_object.getClass()" + aClass);
            initChoiceList();
            // System.out.println("������������Panel��ʼ��======================================");
            initValues();
        }
    }

    /**
     * ������������ ��Ҫ���ڲ鿴�͸���ʱ��ʹ��
     * @param lifecyclemanagedIfc ���������ڹ����ֵ����
     * @ 
     */
    public void setLifeCycle(LifeCycleManagedIfc lifecyclemanagedIfc) 
    {
        LifeCycleTemplateMasterInfo lifecycletemplatemasterInfo = null;
        if(lifecyclemanagedIfc == null)
        {
            if(createChoice.getItemCount() == 0)
            {
                Class[] paraClass = {String.class};
                String lifecycleName = RemoteProperty.getProperty("com.faw_qm.lifecycle.defaultLifeCycle", "ȱʡ");
                Object[] objs = {lifecycleName};
                // ����Ϣ���ݵ��������˽��д���
                try
                {
                    lifecycletemplatemasterInfo = (LifeCycleTemplateMasterInfo)invokeServiceMethod("LifeCycleService", "getLifeCycleTemplateMaster", paraClass, objs);
                }catch(Exception ex)
                {
                    String message = QMExceptionHandler.handle(ex);
                    DialogFactory.showWarningDialog(getParent(), message);
                    return;
                }
                addTemplate(lifecycletemplatemasterInfo);
                createChoice.setSelectedItem(RemoteProperty.getProperty("com.faw_qm.lifecycle.defaultLifeCycle", "ȱʡ"));
                return;
            }
            createChoice.setSelectedItem(RemoteProperty.getProperty("com.faw_qm.lifecycle." + aBsoName + ".defaultLifeCycle", "ȱʡ"));
            return;
        }
        // setBsoName(lifecyclemanagedIfc.getBsoName());
        LifeCycleTemplateInfo template = getTemplate(lifecyclemanagedIfc);
        if(template == null)
        {
            String message = "";
            if(exceptionMessage.equals(""))
            {
                message = QMMessage.getLocalizedMessage(RESOURCE, "15", new Object[]{lifecyclemanagedIfc.getLifeCycleTemplate()});
            }else
            {
                message = exceptionMessage;
            }
            DialogFactory.showWarningDialog(getParent(), message);
            return;
        }
        addTemplate((LifeCycleTemplateMasterInfo)template.getMaster());
        if(verbose)
        {
            System.out.println("��õ�������������Ϊ:" + template.getLifeCycleName());
        }
        createChoice.setSelectedItem(template.getLifeCycleName());
        viewLifeCycleValue.setText(template.getLifeCycleName() + "  " + lifecyclemanagedIfc.getLifeCycleState().getDisplay());
        if(verbose)
        {
            System.out.println("��ʾ������������Ϊ:" + createChoice.getSelectedItem());
        }
    }

    /**
     * Save the currently selected Life Cycle choice in the specified LifeCycleManaged object
     * @ 
     */
    public LifeCycleManagedIfc assign(LifeCycleManagedIfc lifecyclemanagedIfc) 
    {
        if(viewMode != 0 && viewMode != 2)
        {
            try
            {
                Class[] paraClass = {BaseValueIfc.class,boolean.class};
                Object[] objs = {lifecyclemanagedIfc,false};
                anObject = (LifeCycleManagedIfc)invokeServiceMethod("PersistService", "refreshInfo", paraClass, objs);
            }catch(Exception e)
            {
                String message = QMExceptionHandler.handle(e);
                DialogFactory.showWarningDialog(getParent(), message);
                return null;
            }
        }else
        {
            anObject = lifecyclemanagedIfc;
        }
        if(anObject == null)
        {
            return null;
        }
        LifeCycleTemplateInfo lifecycletemplateInfo = getTemplate(anObject);
        LifeCycleTemplateInfo lifecycletemplateInfo1 = getSelectedLifeCycleTemplate();
        // wbb tianjia 2005.8.2
        if(lifecycletemplateInfo1 == null)
        {
            String message = RemoteProperty.getProperty("com.faw_qm.lifecycle.defaultLifeCycle", "ȱʡ") + "�������ڲ����ڻ�Ȩ�޲���";
            DialogFactory.showWarningDialog(getParent(), message);
            try {
				throw new QMException(message);
			} catch (QMException e) {
				// TODO �Զ����� catch ��
				e.printStackTrace();
			}
        }
        // wbb tianjia over
        if(viewMode == 0 || viewMode == 2)
        {
            try
            {
                Class[] paraClass = {LifeCycleManagedIfc.class, LifeCycleTemplateIfc.class};
                Object[] objs = {anObject, lifecycletemplateInfo1};
                // ����Ϣ���ݵ��������˽��д���
                anObject = (LifeCycleManagedIfc)invokeServiceMethod("LifeCycleService", "setLifeCycle", paraClass, objs);
            }catch(Exception exception)
            {
                // JOptionPane.showMessageDialog(getParent(),
                // exception.getLocalizedMessage(),
                // resourcebundle.getString("14"), JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }else
        {
            if((viewMode == 4 || viewMode == 5) && lifecycletemplateInfo != null)
            {
                try
                {
                    Class[] paraClass = {LifeCycleManagedIfc.class, LifeCycleTemplateInfo.class};
                    Object[] objs = {anObject, lifecycletemplateInfo1};
                    // ����Ϣ���ݵ��������˽��д���
                    anObject = (LifeCycleManagedIfc)invokeServiceMethod("LifeCycleService", "reassign", paraClass, objs);
                    // anObject = LifeCycleHelper.service.reassign(anObject,
                    // lifecycletemplatereference1);
                }catch(Exception exception)
                {
                    Object aobj1[] = new Object[1];
                    aobj1[0] = exception.getLocalizedMessage();
                    String message = QMExceptionHandler.handle(exception);
                    DialogFactory.showWarningDialog(getParent(), message);
                    return null;
                }
                RefreshService.getRefreshService().dispatchRefresh(this, 1, anObject);
            }
        }
        initValues();
        return anObject;
    }

    /**
     * Save the currently selected life cycle template <P> in the LifeCycleManaged object previously set by setObject. </p>
     * @ 
     */
    public void assign() 
    {
        assign(anObject);
    }

    // browse button
    // ����Ե���
    private void processBrowse()
    {
        if(verbose)
        {
            System.out.println("Enter processBrowse()");
        }
        ResourceBundle resourcebundle = getMessagesResource();
        setEnabled(false);
        QmChooser qmchooser = new QmChooser("LifeCycleTemplateMaster", resourcebundle.getString("10"), this);
        int[] aa = {1, 1};
        qmchooser.setRelColWidth(aa);
        try
        {
            QueryCondition querycondition[] = new QueryCondition[1];
            querycondition[0] = new QueryCondition("enabled", true);
            qmchooser.setAdditionalSearchConditions(querycondition);
            qmchooser.addListener(new QMQueryListener()
            {

                public void queryEvent(QMQueryEvent qmqueryevent)
                {
                    if(qmqueryevent.getType().equals("Command") && qmqueryevent.getCommand().equals("OK"))
                    {
                        QmChooser qmchooser1 = (QmChooser)qmqueryevent.getSource();
                        LifeCycleTemplateMasterIfc lifecycletemplatemasterIfc = (LifeCycleTemplateMasterIfc)qmchooser1.getSelectedDetail();
                        if(lifecycletemplatemasterIfc != null)
                        {
                            try
                            {
                                // ??
                                LifeCycleTemplateMasterInfo lifecycletemplatemasterInfo = (LifeCycleTemplateMasterInfo)lifecycletemplatemasterIfc;
                                // System.out.println("lifecycletemplatemasterInfo:"+lifecycletemplatemasterInfo.getLifeCycleName());
                                addTemplate(lifecycletemplatemasterInfo);
                                createChoice.setSelectedItem(lifecycletemplatemasterInfo.getLifeCycleName());
                                createChoice.setEnabled(true);
                            }finally
                            {
                                setEnabled(true);
                            }
                            return;
                        }
                    }
                }

            });
            qmchooser.setVisible(true);
        }catch(Exception exception)
        {
            String message = QMExceptionHandler.handle(exception);
            DialogFactory.showWarningDialog(getParent(), message);
        }finally
        {
            setEnabled(true);
            if(verbose)
            {
                System.out.println("Exit processBrowse()");
            }
        }
    }

    // browseButton onclick
    void browseButton_ActionPerformed(ActionEvent actionevent)
    {
        processBrowse();
    }

    /**
     * ��ʼ��
     * @
     */
    private void jbInit() 
    {
        this.setLayout(cardLayout1);

        createPanel.setLayout(gridBagLayout1);
        viewPanel.setLayout(gridBagLayout2);
        browseButton.setPreferredSize(new Dimension(89, 23));
        browseButton.setMnemonic(KeyEvent.VK_B);
        createPanel.add(createLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
        createPanel.add(createChoice, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 4), 0, 0));
        this.add(viewPanel, "viewPanel");
        viewPanel.add(viewLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
        viewPanel.add(viewLifeCycleValue, new GridBagConstraints(1, 0, 0, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 4), 0, 0));
        this.add(createPanel, "createPanel");
    }

    /**
     * ���÷��񷽷���
     * @param serviceName String �������ơ�
     * @param methodName String �������ơ�
     * @param paraClass Class[] ���������༯�ϡ�
     * @param objs Object[] �������ϡ�
     * @return Object ��������
     * @
     */
//    private static Object invokeServiceMethod(String serviceName, String methodName, Class[] paraClass, Object[] objs) 
//    {
//        Object object = null;
//        ServiceRequestInfo info1 = new ServiceRequestInfo();
//        info1.setServiceName(serviceName).setMethodName(methodName).setParaClasses(paraClass).setParaValues(objs);
//        object = (Object)RequestHelper.request(info1);
//        return object;
//    }
    /**
     * ��װ��������󷽷�
     * @param serviceName ������ 
     * @param methodName ������  
     * @param paraClass ������
     * @param paraObj ����ֵ
     * @return Object ������񷵻صĽ��
     * @throws QMException
     */
    public static Object invokeServiceMethod(String serviceName, String methodName, Class[] paraClass, Object[] paraObj) throws QMException
    {

        RequestServer server = RequestServerFactory.getRequestServer();
        if(server == null)
        {
            throw new QMException( "��ȡԶ��RequestServerʧ�ܣ�");
        }
        ServiceRequestInfo sInfo = new ServiceRequestInfo();
        sInfo.setServiceName(serviceName);
        sInfo.setMethodName(methodName);
        sInfo.setParaClasses(paraClass);
        sInfo.setParaValues(paraObj);
        Object obj = null;
        try
        {
            obj = server.request(sInfo);
        }catch(QMRemoteException e)
        {
            throw new QMException(e);
        }
        return obj;
    }
    // main
    public static void main(String args[])
    {
        try
        {
            System.setProperty("swing.useSystemFontSettings", "0");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e)
        {}
        RequestServer server = new RequestServer("localhost", "7001", "1DyejdS4UpadsFSdeleV2WdvAdFyj6gGiO0TtGRTteHLfqrZfO3R!-1991015604!1057190430587")
        {};
        DriverFrame driverframe = new DriverFrame();
        driverframe.setTitle("LifeCyclePanel Test");
        driverframe.setVisible(true);
    }

    /**
     * ��ý���
     */
    public void grabFocus()
    {
        createChoice.grabFocus();
    }

    /**
     * CR2 ���������������ϵ���ʾ��Ϣ
     */
    public void clear()
    {

        if(viewLifeCycleValue != null)
        {
            viewLifeCycleValue.setText("");
        }
    }
    //CR2
    /**
     * ���������б��Ĭ��ֵ
     * @param s Ҫѡ���Ĭ��ֵ
     */
    public void setSelectedItem(String s)
    {
        createChoice.setSelectedItem(s);
    }
    
    //Begin CR4
    public void setLifeCycleJLabelName(String name)
    {
        createLabel.setText(name);
    }
    //End CR4
}
