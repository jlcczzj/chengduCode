/**
 * 程序ResourceSelectDialog.java 1.0 12/8/2003
 * 版权归一汽启明公司所有
 * 本程序属于一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/06/19 谢斌 原因：基本属性界面“状态”处没有对齐。TD-2233
 *                     方案：由于生命周期和其状态使用的是一个面板，界面元素变化时无法做出统一响应，现改为状态为单独面板，即可统一响应界面变化。
 * CR2 2009/07/03 马辉 原因：TD2515 进入产品信息管理器点击树的根节点产品结构,右边基本属性页资料夹处显示“\Root\Administrator"，
 *                          从零部件上把焦点移回跟焦点，基本属性面板有遗留信息。
 *                     方案：焦点在根结点时，清空基本属性面板的信息           
 * CR3 2009/10/28 王彪  修改原因：统一客户端或工具类调用服务的方式。         
 * CR4 2011/05/16 贾浩鑫 修改原因：为生命周期显示标识标签添加重命名方法
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
 * <p> Title: 生命周期JavaBean </p>
 * @author 孙清国
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
     * CREATE_MODE模式 <p> Create Mode allows the user to assign a Life Cycle template to a LifeCycleManaged object, a Life Cycle label is displayed to the left of the choice box. </P>
     */
    public static final int CREATE_MODE = 0;

    /**
     * VIEW_MODE模式 <p> No Label Create Mode allows the user to assign a Life Cycle template to a LifeCycleManaged object. </P>
     */
    public static final int VIEW_MODE = 1;

    /**
     * NO_LABEL_CREATE_MODE模式 <p> No Label Update Mode allows the user to assign a Life Cycle template to a LifeCycleManaged object </p>
     */
    public static final int NO_LABEL_CREATE_MODE = 2;

    /**
     * NO_LABEL_VIEW_MODE模式 <p> No Label View Mode displays the name of the Life Cycle template and Life Cycle state associated with a LifeCycleManaged object </p>
     */
    public static final int NO_LABEL_VIEW_MODE = 3;

    /**
     * UPDATE_MODE模式 <p> Update Mode allows the user to assign a Life Cycle template to a LifeCycleManaged object. </p>
     */
    public static final int UPDATE_MODE = 4;

    /**
     * NO_LABEL_UPDATE_MODE模式 <p> View Mode displays the name of the Life Cycle template and Life Cycle state associated with a LifeCycleManaged objec </p>
     */
    public static final int NO_LABEL_UPDATE_MODE = 5;

    static final int PROCESS_BROWSE = 0;

    static final int INIT_CHOICE_LIST = 1;

    private boolean notifyDone;

    private boolean initReady;

    private String VIEW_CARD;

    private String CREATE_CARD;

    // 模式
    private int viewMode;

    private boolean cardLayoutInitialized;

    // 资源文件
    private static final String RESOURCE = "com.faw_qm.clients.beans.lifecycle.BeansLifeCycleRB";

    // 调试标志
    private boolean verbose = (RemoteProperty.getProperty("com.faw_qm.clients.beans.verbose", "true")).equals("true");

    // 临时储存生命周期
    private Hashtable lifecycles;

    // 布局管理器
    private LayoutManager layout;

    // BsoName
    private String aBsoName;

    // 实现LifeCycleManagedIfc的对象
    private LifeCycleManagedIfc anObject;

    // 用于国际化
    private ResourceBundle messagesResource;

    // 声明一个请求服务器变量
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

    // 为了解决在调用getTemplate()时由于权限不够而获得不了模版时能够获得适合的异常信息
    private String exceptionMessage = "";

    /** browseButton监听器 */
    class SymAction implements ActionListener
    {
        /** 实现actionPerformed方法 */
        public void actionPerformed(ActionEvent actionevent)
        {
            Object obj = actionevent.getSource();
            if(obj == browseButton)
            {
                // 调用实际处理的方法
                browseButton_ActionPerformed(actionevent);
            }
        }

        SymAction()
        {}
    }

    /** 测试代码 */
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
     * 默认构造器 <p> 创建一个LifeCyclePanel，默认的模式是CREATE_MODE </p>
     */
    public LifeCyclePanel()
    {
        notifyDone = false;
        initReady = false;
        // panel card
        VIEW_CARD = "viewPanel";
        CREATE_CARD = "createPanel";
        viewMode = 0;
        // 是否布局
        cardLayoutInitialized = false;
        server = RequestServerFactory.getRequestServer();
        try
        {
            jbInit();
        }catch(Exception e)
        {}
        // 局部化
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

    /** 获得组件的首选大小 */
    public Dimension getPreferredSize()
    {
        return new Dimension(100, 25);
    }

    /**
     * 为容器设置布局管理器
     * @param layoutmanager 布局管理器
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

    /** 获得容器的布局管理器 */
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
     * 设置按钮名
     * @param name
     */
    public void setButtonName(String name)
    {
        browseButton.setText(name);
    }

    /**
     * 设置浏览按钮的助记符
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
     * 使组件可用
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

    // 初始化生命周期列表，调用工作线程
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
            // System.out.println("进入生命周期初始化值=====================================");
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
                        // System.out.println("要设置的生命周期名:"+templateInfo.getLifeCycleName());
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
                //20080414 zhangq begin:普通用户查看零部件时，如果此零部件使用的生命周期被他人检出，出现死锁。
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

    // 向生命周期列表中加入一项
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

    // 初始化生命周期列表
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
                    String lifecycleName = RemoteProperty.getProperty("com.faw_qm.lifecycle." + aBsoName + ".defaultLifeCycle", "缺省");
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
                    // ///wbb 2005.6.29为了修改创建状态选中默认生命周期
                    name = lifecycletemplatemasterInfo.getLifeCycleName();
                    createChoice.setSelectedItem(name);
                    // /////////////////////////////////////////////
                }
            }
        }
    }

    /**
     * 设置BsoName，用于获得一个生命周期的列表
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

    /** 返回现在的模式 */
    public int getMode()
    {
        return viewMode;
    }

    /**
     * 设置模式，create 或 view
     * @param i create 或 view
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
                // 将信息传递到服务器端进行处理
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

    // 获得LifeCycleManagedIfc对象的生命周期模板的值对象
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
            // 将信息传递到服务器端进行处理
            LifeCycleTemplateInfo i = null;
            try {
				i = (LifeCycleTemplateInfo)invokeServiceMethod("PersistService", "refreshInfo", paraClass, objs);
			} catch (QMException e) {
				// TODO 自动生成 catch 块
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
            // System.out.println("-------------------------进入生命周期Panel:"+lifecyclemanagedIfc.getLifeCycleTemplate());
            anObject = lifecyclemanagedIfc;
            aBsoName = lifecyclemanagedIfc.getBsoName();
            // aClass = lifecyclemanagedIfc.getClass();
            // if(verbose)
            // System.out.println("aClass = an_object.getClass()" + aClass);
            initChoiceList();
            // System.out.println("进入生命周期Panel初始化======================================");
            initValues();
        }
    }

    /**
     * 设置生命周期 主要用于查看和更新时候使用
     * @param lifecyclemanagedIfc 受生命周期管理的值对象
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
                String lifecycleName = RemoteProperty.getProperty("com.faw_qm.lifecycle.defaultLifeCycle", "缺省");
                Object[] objs = {lifecycleName};
                // 将信息传递到服务器端进行处理
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
                createChoice.setSelectedItem(RemoteProperty.getProperty("com.faw_qm.lifecycle.defaultLifeCycle", "缺省"));
                return;
            }
            createChoice.setSelectedItem(RemoteProperty.getProperty("com.faw_qm.lifecycle." + aBsoName + ".defaultLifeCycle", "缺省"));
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
            System.out.println("获得的生命周期名称为:" + template.getLifeCycleName());
        }
        createChoice.setSelectedItem(template.getLifeCycleName());
        viewLifeCycleValue.setText(template.getLifeCycleName() + "  " + lifecyclemanagedIfc.getLifeCycleState().getDisplay());
        if(verbose)
        {
            System.out.println("显示的生命周期名为:" + createChoice.getSelectedItem());
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
            String message = RemoteProperty.getProperty("com.faw_qm.lifecycle.defaultLifeCycle", "缺省") + "生命周期不存在或权限不够";
            DialogFactory.showWarningDialog(getParent(), message);
            try {
				throw new QMException(message);
			} catch (QMException e) {
				// TODO 自动生成 catch 块
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
                // 将信息传递到服务器端进行处理
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
                    // 将信息传递到服务器端进行处理
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
    // 需测试调试
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
     * 初始化
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
     * 调用服务方法。
     * @param serviceName String 服务名称。
     * @param methodName String 方法名称。
     * @param paraClass Class[] 参数所属类集合。
     * @param objs Object[] 参数集合。
     * @return Object 操作对象。
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
     * 封装服务端请求方法
     * @param serviceName 服务名 
     * @param methodName 方法名  
     * @param paraClass 参数类
     * @param paraObj 参数值
     * @return Object 请求服务返回的结果
     * @throws QMException
     */
    public static Object invokeServiceMethod(String serviceName, String methodName, Class[] paraClass, Object[] paraObj) throws QMException
    {

        RequestServer server = RequestServerFactory.getRequestServer();
        if(server == null)
        {
            throw new QMException( "获取远程RequestServer失败！");
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
     * 获得焦点
     */
    public void grabFocus()
    {
        createChoice.grabFocus();
    }

    /**
     * CR2 清除生命周期面板上的显示信息
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
     * 设置下拉列表的默认值
     * @param s 要选择的默认值
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
