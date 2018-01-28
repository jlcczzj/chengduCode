/** 生成程序QMAttribute.java    1.0  2003/02/06
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 变速箱公司属性显示名 liuyang 2013-3-26
 */

package com.faw_qm.cappclients.beans.query;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.faw_qm.acl.ejb.entity.QMPermission;
import com.faw_qm.cappclients.resource.view.ResourceSelectTypeDialog;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.clients.beans.DataFormatChoice;
import com.faw_qm.clients.beans.ViewChoice;
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.clients.beans.folderPanel.SelectFolderDialog;
import com.faw_qm.clients.beans.lifecycle.ProjectPanel;
import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.beans.query.QMBusinessInfo;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.gui.UserSelectorDialog;
import com.faw_qm.clients.util.QMContext;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.content.model.DataFormatInfo;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.users.model.ActorIfc;
import com.faw_qm.util.PropertyDescript;


/**
 *
 * <p>Title: </p>
 * <p>Description:将业务类的属性PropertyDescript表示为某一界面组件，
 * 封装为QmAttribute对象。</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 杨晓辉
 * @version 1.0
 */

public class CappAttribute implements ItemListener, KeyListener,
        CappQueryListener, ActionListener
{

    /**
     * 检索文件夹标识
     */
    protected static final int PROCESS_BROWSE_CABINET = 0;


    /**
     * 检索用户标识
     */
    protected static final int PROCESS_BROWSE_USER = 1;


    /**
     * 浏览Coding标识
     */
    protected static final int PROCESS_BROWSE_CODING = 2;


    /**
     * 运算符"="
     */
    public static final String EQUAL = "=";


    /**
     * 运算符"<"
     */
    public static final String LESS = "<";


    /**
     * 运算符">"
     */
    public static final String GREATER = ">";


    /**
     * 运算符"BETWEEN"
     */
    public static final String BETWEEN = "BETWEEN";


    /**
     * 数据输入格式
     */
    public static final String DateInputFormat = "dateInputFormat";

    private boolean dontClear = false;


    /**
     * 数据解析格式
     */
    public static final String QueryDateInputFormat = "queryDateInputFormat";

    private static final String EMPTY_STRING = ""; //空字符标识
    private static final String BrowseCMD = "Browse"; //检索显示名
    private static final String BROWSE_CABINET = "BrowseCabinet"; //检索文件夹显示名
    private static final String BROWSE_USER = "BrowseUser"; //检索用户显示名
    private static final String BROWSE_CODIND = "BrowseCoding";
    private static String relationalConstraints[] = null; //数字约束比较标识集
    private static String timeConstraints[] = null; //时间约束比较运算符标识集
    private PropertyDescript myPD; //属性描述
    private BaseValueIfc myObject; //选中引用对象

    private EnumeratedType[] enumType; //枚举集
    private Vector myValueSet; //枚举
    private JPanel myPanel; //资料夹，检索项目bean
    private JLabel myLabel; //属性显示名标签
    private JTextField myText; //字符属性
    private JComboBox myCombo; //枚举选择
    private JComboBox myRange; //操作符选择
    private JTextField myFrom; //下限
    private JLabel myDash; //分割符
    private JTextField myThru; //上限
    private JPanel timePanel;
    private JCheckBox myNot; //取反
    private JButton myRB; //浏览按钮
    private QmChooser myRQ; //选择对话框
    private static ThreadGroup theThreadGroup = Thread.currentThread().
                                                getThreadGroup();
    private Color myBG; //前景色
    private Color myFG; //背景色
    private Color myCanvasBG; //容器前景色
    private Color myCanvasFG; //容器背景色
    private static boolean verbose = QM.getVerbose(); //debug标识


    /**是否是二级分类属性*/
    private boolean isSecondClassifi;
    private CappQuery cappquery;


    /**
     * <p>Title: </p>
     * <p>Description:线程控制类 </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: qm</p>
     * @author 杨晓辉
     * @version 1.0
     */
    class WorkThread extends QMThread
    {

        public void run()
        {
            switch (action)
            {
                case PROCESS_BROWSE_CABINET: // '\0'
                    try
                    {
                        processBrowseCabinet();
                        return;
                    }
                    catch (Throwable throwable)
                    {
                        throwable.printStackTrace();
                    }
                    return;
                case PROCESS_BROWSE_USER: // '\001'
                    try
                    {
                        processBrowseUser();
                        return;
                    }
                    catch (Throwable throwable1)
                    {
                        throwable1.printStackTrace();
                    }
                    case PROCESS_BROWSE_CODING:
                        processBrowseCoding();
                        return;
            }
        }

        int action;
        public WorkThread(int i)
        {
            super(theThreadGroup);
            action = i;
        }
    }


    /**
     * 过载接口方法
     * @param qmqueryevent
     */
    public void queryEvent(CappQueryEvent qmqueryevent)
    {
        if (qmqueryevent.getType().equalsIgnoreCase(CappQueryEvent.SELECT))
        {
            myRQ.setVisible(false);
            qmqueryevent.getDetail();
        }
    }


    /**
     * 根据属性描述器和标识来构造CappAttribute
     * @param propertydescript
     * @param flag 标示可视组件的可编辑性。true-->可编辑，false-->不可编辑
     */
    public CappAttribute(PropertyDescript propertydescript, boolean flag,
                         HashMap defalt)
    {
        //initColor();
        myValueSet = new Vector();
        myPD = propertydescript;
        String at = getType();
        ResourceBundle resourcebundle = QM.getMessagesResource();
        ResourceBundle localbundle = new CappChooserOptions();
        myLabel = new JLabel(getName(), //+ resourcebundle.getString("13")
                             JLabel.RIGHT);
        myNot = new JCheckBox(resourcebundle.getString("14"), false);
        if (at.equalsIgnoreCase(QMBusinessInfo.BoolTYPE))
        {
            myCombo = new JComboBox();
            Vector vector = getValueSet();
            for (int i = 0; i < vector.size(); i++)
            {
                myCombo.addItem((String) vector.elementAt(i));
            }
        }
        else
        if (isRangeType())
        {
            myRange = new JComboBox();
            String as[] = getRelationalConstraints();
            if (getType().equalsIgnoreCase(QMBusinessInfo.TimeTYPE))
            {
                as = getTimeConstraints();
                ResourceBundle rb = ResourceBundle.getBundle(
                        "com.faw_qm.query.DateHelperResource",
                        Locale.getDefault());
                String labelText = getName() + "(" +
                                   rb.getString(DateInputFormat) +
                                   ")"; // + resourcebundle.getString("13")
                myLabel.setText(labelText);
            }
            for (int k = 0; k < as.length; k++)
            {
                myRange.addItem(as[k]);
            }
            myRange.addItemListener(this);
            myFrom = new JTextField(getSize());
            myDash = new JLabel(resourcebundle.getString("12"),
                                JLabel.CENTER);
            myThru = new JTextField(getSize());
            timePanel = new JPanel();
            timePanel.setLayout(new GridBagLayout());
            timePanel.add(myRange, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                    , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 3), 0, 0));

            timePanel.add(myFrom, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                    , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 3), 0, 0));
            timePanel.add(myDash, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                    , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(0, 0, 0, 3), 0, 0));
            timePanel.add(myThru, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0
                    , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
        }
        else
        if (at.equalsIgnoreCase(CappBusinessInfo.ViewRefTYPE))
        {
            myCombo = new ViewChoice();
            ((ViewChoice) myCombo).setBlankChoiceAllowed(true);
            //modify by wangh on 20070921
            ((ViewChoice) myCombo).setAllChoiceAllowed(false);
        }
        else
        if (at.equalsIgnoreCase(CappBusinessInfo.DataFormatRefTYPE))
        {
            myCombo = new DataFormatChoice();
            ((DataFormatChoice) myCombo).setBlankChoiceAllowed(true);
        }
        else
        if (isEnumVSType())
        {
            if (verbose)
            {
                System.out.println("the \"" + getName() + " \" is " +
                                   CappBusinessInfo.EnumTYPE);
            }
            myCombo = new JComboBox();
            Vector vector1 = getValueSet();
            for (int j = 0; j < vector1.size(); j++)
            {
                myCombo.addItem((String) vector1.elementAt(j));
            }
        }
        else
        if (isReferenceType())
        {
            if (at.equalsIgnoreCase(CappBusinessInfo.CRefTYPE))
            {

                // myRB = addButton ( resourcebundle.getString ( "15" ) ,
                //                      BROWSE_CABINET ) ;
                FolderPanel folderpanel = new FolderPanel();
                folderpanel.removeLabel();
                folderpanel.setButtonSize(89, 23);
                folderpanel.removeButtonMnemonic();
                //folderpanel.removeButton () ;
                myPanel = folderpanel;
                folderpanel.setIsPersonalFolder(false);
                folderpanel.setIsPublicFolders(false);
                folderpanel.setPermission(QMPermission.READ); //QMPermission.READ
                //folderpanel.setFont(QM.getNormalFont());
                //folderpanel.setBrowseJButtonName(resourcebundle.getString("15"));
            }
            else if (at.equalsIgnoreCase(CappBusinessInfo.CodingTYPE))
            {
                myText = new JTextField(getSize());
                myText.setEnabled(false);
                //myText.setFocusable(false);
                //myText.addKeyListener(this);
                myRB = addButton(localbundle.getString("browsButton") + ". . .",
                                 BROWSE_CODIND, 89, 23);
                String sortType = myPD.getLimitValue(CappBusinessInfo.SortType);
                String queryStr = myPD.getLimitValue("queryDefault");
                boolean queryDefault = false;
                if (queryStr != null)
                {
                    queryDefault = new Boolean(queryStr).booleanValue();
                }
                String defaultValue = (String) defalt.
                                      get(getAttributeName());
                if (defaultValue != null)
                {
                    String name = null;
                    String parent = null;
                    CodingIfc code = null;
                    StringTokenizer a = new StringTokenizer(sortType, ":");
                    if (a.hasMoreTokens())
                    {
                        name = (String) a.nextElement();
                    }
                    if (a.hasMoreTokens())
                    {
                        parent = (String) a.nextElement();
                    }

                    try
                    {
                        code = (CodingIfc) CappTreeHelper.getCodingByContent(
                                name, parent, defaultValue);
                    }
                    catch (QMRemoteException ex)
                    {
                        ex.printStackTrace();
                    }

                    if (code != null)
                    {

                        setValue(code.getCodeContent());
                        myObject = code;
                        dontClear = true;
                    }

                }
                else if (queryDefault)
                {

                    String name = null;
                    String parent = null;
                    String content = null;
                    StringTokenizer a = new StringTokenizer(sortType, ":");
                    if (a.hasMoreTokens())
                    {
                        name = (String) a.nextElement();
                    }
                    if (a.hasMoreTokens())
                    {
                        parent = (String) a.nextElement();
                    }
                    if (a.hasMoreTokens())
                    {
                        content = (String) a.nextElement();
                    }
                    CodingIfc code = null;
                    try
                    {
                        //  System.out.println("kankan ===="+"name"+name+"parent"+parent+"content"+content);
                        code = CappTreeHelper.getCodingByContent(
                                name, parent, content);
                        if (code != null)
                        {
                            setValue(code.getCodeContent());
                            myObject = code;
                            dontClear = true;
                        }
                    }
                    catch (QMRemoteException e)
                    {
                        e.printStackTrace();
                    }
                }

            }
            else
            if (at.equalsIgnoreCase(CappBusinessInfo.PRefTYPE))
            {
                myText = new JTextField(getSize());
                myText.setEnabled(true);
                //myText.setFocusable(false);
                //myText.addKeyListener(this);
                myRB = addButton(localbundle.getString("browsButton") + ". . .",
                                 BROWSE_USER, 89, 23);
            }
            else
            if (at.equalsIgnoreCase(CappBusinessInfo.ProjectRefTYPE))
            {
                myText = new JTextField(getSize());
                myPanel = new ProjectPanel();
                ((ProjectPanel) myPanel).setBrowseButtonText(localbundle.
                        getString("browsButton"));
                ((ProjectPanel) myPanel).setBrowseButtonSize(new Dimension(65,
                        23));
                ((ProjectPanel) myPanel).setMode(ProjectPanel.
                                                 NO_LABEL_CREATE_MODE); //2;
            }
            else
            {
                myText = new JTextField(getSize());
                myRB = addButton(resourcebundle.getString("15"),
                                 BrowseCMD, 89, 23);
            }
        } //end if
        else
        {
            myText = new JTextField(getSize());
        } //end if
        if (myRB != null)
        {
            myRB.setSize(65, 23);
        }
        setEditable(flag);
        if (!dontClear)
        {
            clear();
        }
        setRange(BETWEEN);
        if (verbose)
        {
            System.out.println(
                    "new CappAttribute end.the attribute's name is:::" +
                    getAttributeName());
            System.out.println(
                    "new CappAttribute end.the attribute's type is :::" +
                    getType());
        }
    }


    /**
     * 初始化颜色
     */
    private void initColor()
    {
        myBG = QM.getBGColor();
        myFG = QM.getFGColor();
        myCanvasBG = QM.getCanvasFGColor();
        myCanvasFG = QM.getCanvasBGColor();
    }


    /**
     * 访问属性显示名
     * @return
     */
    public String getName()
    {
    	//CCBegin SS1
    	Boolean yy = false; 	
       	try {
   	    	Class[] paraClass = {};
   	        Object[] objs = {};
   	        String className = "com.faw_qm.doc.util.DocServiceHelper";
   	        String methodName = "isBSXGroup";
   	        StaticMethodRequestInfo in = new StaticMethodRequestInfo();
   	        in.setClassName(className);
   	        in.setMethodName(methodName);
   	        in.setParaClasses(paraClass);
   	        in.setParaValues(objs);
   	        RequestServer server = null;
   	        server = RequestServerFactory.getRequestServer();
   		
   			yy = (Boolean) server.request(in);
   		} catch (QMRemoteException e) {
   			e.printStackTrace();
   		}
   		if(yy){
   			String attrname = CappBusinessInfo.getAttributeName(myPD);
   			String disname = null;
   			if(attrname.equals("controlPlanNum")){
   			    disname = "质保部文件编号";
   			   return disname;
   			}
   		   if(attrname.equals("femaNum")){
   			   disname = "产品平台";
   			   return disname;
   			}
   		   if(attrname.equals("taskInstructNum")){
   			   disname = "生产线";
   			   return disname;
   		   }
   		return CappBusinessInfo.getAttributeDisplayName(myPD);
   		}
   		//CCEnd SS1
   		else{
        return CappBusinessInfo.getAttributeDisplayName(myPD);
   		}
    }


    /**
     * 访问属性类型。具体类型在CappBusinessInfo中以常量的形式定义
     * @return
     */
    public String getType()
    {
        return CappBusinessInfo.getAttributeType(myPD);
    }


    /**
     * 获得引用类型的具体类型
     * @return
     */
    public String getLimitType()
    {
        return CappBusinessInfo.getLimitType(myPD);
    }


    /**
     * 获得属性集
     * @return
     */
    protected Vector getValueSet()
    {
        ResourceBundle resourcebundle = QM.getMessagesResource();
        myValueSet.removeAllElements();
        if (getType().equalsIgnoreCase(CappBusinessInfo.BoolTYPE))
        {
            myValueSet.addElement("");
            myValueSet.addElement(resourcebundle.getString("17"));
            myValueSet.addElement(resourcebundle.getString("16"));
        }
        else
        if (getType().equalsIgnoreCase(CappBusinessInfo.EnumTYPE))
        {
            if (verbose)
            {
                System.out.println(
                        "getValueSet method...the attribute is EnumerateType......");
            }
            if (enumType == null)
            {
                enumType = CappBusinessInfo.getAttributeValueSet(
                        myPD);
            }
            if (enumType != null && enumType.length != 0)
            {
                myValueSet.addElement("");
                for (int i = 0; i < enumType.length; i++)
                {
                    myValueSet.addElement(enumType[i].getDisplay());
                }
            }
        }
        return myValueSet;
    }


    /**
     * 判断属性是否可比较的
     * @return
     */
    public boolean isRangeType()
    {
        String class1 = getType();
        if (class1.equalsIgnoreCase(CappBusinessInfo.IntTYPE))
        {
            return true;
        }
        if (class1.equalsIgnoreCase(CappBusinessInfo.LongTYPE))
        {
            return true;
        }
        if (class1.equalsIgnoreCase(CappBusinessInfo.FltTYPE))
        {
            return true;
        }
        if (class1.equalsIgnoreCase(CappBusinessInfo.DoubTYPE))
        {
            return true;
        }
        return class1.equalsIgnoreCase(CappBusinessInfo.TimeTYPE);
    }


    /**
     * 获得数字约束的比较运算符标识集
     * @return
     */
    private String[] getRelationalConstraints()
    {
        ResourceBundle resourcebundle = QM.getMessagesResource();
        if (relationalConstraints == null)
        {
            relationalConstraints = new String[4];
            relationalConstraints[0] = resourcebundle.getString("47");
            relationalConstraints[1] = resourcebundle.getString("48");
            relationalConstraints[2] = resourcebundle.getString("49");
            relationalConstraints[3] = resourcebundle.getString("11");
        }
        return relationalConstraints;
    }


    /**
     * 获得时间约束的比较运算符标识集
     * @return
     */
    private String[] getTimeConstraints()
    {
        ResourceBundle resourcebundle = QM.getMessagesResource();
        if (timeConstraints == null)
        {
            timeConstraints = new String[4];
            timeConstraints[0] = resourcebundle.getString("8");
            timeConstraints[1] = resourcebundle.getString("9");
            timeConstraints[2] = resourcebundle.getString("10");
            timeConstraints[3] = resourcebundle.getString("11");
        }
        return timeConstraints;
    }


    /**
     * 根据条目状态改变相应的比较运算符标识
     * @param itemevent
     */
    public void itemStateChanged(ItemEvent itemevent)
    {
        if (myRange == itemevent.getSource())
        {
            //setRange(getRange());
            if (verbose)
            {
                System.out.println("the selected Item is:::" + getRange());
            }
        }
    }


    /**
     * 设置比较运算符标识
     * @param s
     */
    public void setRange(String s)
    {
        if (myRange == null)
        {
            return;
        }
        if (getType().equalsIgnoreCase(CappBusinessInfo.TimeTYPE))
        {
            if (s.equalsIgnoreCase(EQUAL))
            {
                s = timeConstraints[0];
            }
            else if (s.equalsIgnoreCase(LESS))
            {
                s = timeConstraints[1];
            }
            else if (s.equalsIgnoreCase(GREATER))
            {
                s = timeConstraints[2];
            }
            else if (s.equalsIgnoreCase(BETWEEN))
            {
                s = timeConstraints[3];
            }
        }
        else
        {
            if (s.equalsIgnoreCase(EQUAL))
            {
                s = relationalConstraints[0];
            }
            else if (s.equalsIgnoreCase(LESS))
            {
                s = relationalConstraints[1];
            }
            else if (s.equalsIgnoreCase(GREATER))
            {
                s = relationalConstraints[2];
            }
            else if (s.equalsIgnoreCase(BETWEEN))
            {
                s = relationalConstraints[3];
            }
        }
        myRange.setSelectedItem(s);
        refresh();
    }


    /**
     * 获得比较运算符标识
     * @return
     */
    public String getRange()
    {
        int i = myRange.getSelectedIndex();
        if (i == 0)
        {
            return EQUAL;
        }
        if (i == 1)
        {
            return LESS;
        }
        if (i == 2)
        {
            return GREATER;
        }
        else
        {
            return BETWEEN;
        }
    }


    /**
     * 获得比较运算符集对应组件的长度
     * @return
     */
    public int getSize()
    {
        byte byte0 = 20;
        String class1 = getType();
        if (class1.equalsIgnoreCase(CappBusinessInfo.BoolTYPE))
        {
            byte0 = 5;
        }
        else if (class1.equalsIgnoreCase(CappBusinessInfo.TimeTYPE))
        {
            byte0 = 12;
        }

        return byte0;
    }


    /**
     * 判断属性是否为引用类型
     * @return
     */
    public boolean isReferenceType()
    {
        String class1 = getLimitType();
        boolean rt = false;
        if (class1 == null)
        {
            ;
        }
        else if (class1.equalsIgnoreCase(CappBusinessInfo.CRefTYPE))
        {
            rt = true;
        }
        else if (class1.equalsIgnoreCase(CappBusinessInfo.PRefTYPE))
        {
            rt = true;
        }
        else if (class1.equalsIgnoreCase(CappBusinessInfo.ProjectRefTYPE))
        {
            rt = true;
        }
        else if (class1.equalsIgnoreCase(CappBusinessInfo.CodingTYPE))
        {
            rt = true;
        }
        return rt;
    }

    public void keyPressed(KeyEvent keyevent)
    {
    }

    public void keyTyped(KeyEvent keyevent)
    {
    }

    public void keyReleased(KeyEvent keyevent)
    {
        myObject = null; //???
    }


    /**
     * 创建新按钮
     * @param name 按钮文本
     * @param actType 作用指令
     * @param w int 按钮宽
     * @param h int 按钮高
     * @return 按钮
     */
    private JButton addButton(String name, String actType, int w, int h)
    {
        JButton button = new JButton(name);
        button.setMaximumSize(new Dimension(w, h));
        button.setMinimumSize(new Dimension(w, h));
        button.setPreferredSize(new Dimension(w, h));
        button.setActionCommand(actType);
        button.addActionListener(this);
        return button;
    }


    /**
     * 当属性是文件夹或是用户时，处理浏览
     * @param actionevent
     */
    public void actionPerformed(ActionEvent actionevent)
    {
        QM.getMessagesResource();
        if (actionevent.getActionCommand() == BROWSE_CABINET)
        {
            try
            {
                WorkThread workthread = new WorkThread(PROCESS_BROWSE_CABINET);
                workthread.start();
            }
            finally
            {
            }
            return;
        }
        else if (actionevent.getActionCommand() == BROWSE_USER)
        {
            try
            {
                WorkThread workthread1 = new WorkThread(PROCESS_BROWSE_USER);
                workthread1.start();
            }
            finally
            {
            }
        }
        else if (actionevent.getActionCommand() == BROWSE_CODIND)
        {
            WorkThread workthread1 = new WorkThread(PROCESS_BROWSE_CODING);
            workthread1.start();
        }
    }


    /**
     *  浏览Coding
     */
    private void processBrowseCoding()
    {
        //add by guoxl on 2008.3.28(搜索工艺规程中产品状态，和工艺种类两个浏览按钮所弹出界面的标题与代码管理类别名称不统一)
        String str=myPD.getLimitValue(CappBusinessInfo.SortType);
        String title="";
        int index=str.indexOf(":");
        if(null==str){
            title="浏览编码";
        }else{

            if(index!=-1 &&index!=0&&index!=str.length() ){

                title=str.substring(0,index);
            }
        }
        //add by guoxl end
        ResourceSelectTypeDialog selectDialog = new ResourceSelectTypeDialog(
                myPD.getLimitValue(CappBusinessInfo.SortType),
                getTopLevelParent(myText), title, true);
        selectDialog.setIsSelectCC(true);
        /* Dimension screenSize = Toolkit.getDefaultToolkit ().getScreenSize () ;
             Dimension frameSize = selectDialog.getSize () ;
             if ( frameSize.height > screenSize.height ) {
                 frameSize.height = screenSize.height ;
             }
             if ( frameSize.width > screenSize.width ) {
                 frameSize.width = screenSize.width ;
             }
         selectDialog.setLocation ( ( screenSize.width - frameSize.width ) /
                                        2 ,
         ( screenSize.height - frameSize.height ) /
                                        2 ) ;
         */
        selectDialog.setVisible(true);
        BaseValueIfc coding = selectDialog.getCoding();
        if (coding != null)
        {
            if (coding instanceof CodingIfc)
            {
                setValue(((CodingIfc) coding).getCodeContent());
                myObject = coding;
                if (isSecondClassifi)
                {
                    cappquery.refreshExttrPanel(((CodingIfc) coding).
                                                getCodeContent());
                }
            }
            else
            if (coding instanceof CodingClassificationIfc)
            {
                setValue(((CodingClassificationIfc) coding).getCodeSort());
                myObject = coding;
            }
        }
        else
        {
            setValue("");
            if (isSecondClassifi)
            {
                cappquery.refreshExttrPanel(null);
            }
        }
    }


    /**
     * 浏览文件夹，调用FolderPanel
     */
    private void processBrowseCabinet()
    {
        FolderPanel folderPanel = (FolderPanel) myPanel;
        if (folderPanel.getPermission() != null &&
            folderPanel.getPermission().length() != 0)
        {
            SelectFolderDialog selectDialog = new SelectFolderDialog(
                    folderPanel,
                    getTopLevelParent(folderPanel), QMContext.getRequestServer());
            //弹出选择资料夹窗口
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = selectDialog.getSize();
            if (frameSize.height > screenSize.height)
            {
                frameSize.height = screenSize.height;
            }
            if (frameSize.width > screenSize.width)
            {
                frameSize.width = screenSize.width;
            }
            selectDialog.setLocation((screenSize.width - frameSize.width) /
                                     2,
                                     (screenSize.height - frameSize.height) /
                                     2);
            selectDialog.show();
        }
        /**
             FolderPanel folderpanel = new FolderPanel();
             folderpanel.setIsPersonalFolder(false);
             folderpanel.setIsPublicFolders(true);
             folderpanel.setPermission("read");//QMPermission.READ
         SubFolderInfo folder = (SubFolderInfo) folderpanel.getSetdFolder();
             if (folder != null) {
          setValue(folder.getName());
             }
             myObject = folder;
         **/
    }


    /**
     * 浏览用户，调用UserSelectorDialog
     */
    private void processBrowseUser()
    {
        UserSelectorDialog userselectordialog = new UserSelectorDialog(
                getTopLevelParent(myText), true);
        userselectordialog.show();
        ActorIfc actorinfo = userselectordialog.getSelectedUser();
        if (actorinfo != null)
        {
            setValue(actorinfo.getUsersName());
            myObject = actorinfo;
        }
        else
        {
            setValue("");
        }
    }


    /**
     * 获得具体组件的顶级父窗体
     * @param component
     * @return
     */
    private JFrame getTopLevelParent(Component component)
    {
        Object obj;
        for (obj = component;
                   !(obj instanceof JFrame) &&
                   ((Component) (obj)).getParent() != null;
                   obj = ((Component) (obj)).getParent())
        {
            ;
        }
        if (obj instanceof JFrame)
        {
            return (JFrame) obj;
        }
        else
        {
            return null;
        }
    }


    /**
     * 设置组件显示文本
     * @param text 欲显示文本
     */
    public void setValue(String text)
    {
        if (myText != null)
        {
            myText.setText(text);
        }
        if (myCombo != null)
        {
            myCombo.setSelectedItem(text);
        }
    }


    /**
     * 设置组件的可编辑性
     * @param flag true-->可编辑，false-->不可编辑
     */
    public void setEditable(boolean flag)
    {
        if (myText != null)
        {
            myText.setEditable(flag);
        }
        if (myFrom != null)
        {
            myFrom.setEditable(flag);
        }
        if (myThru != null)
        {
            myThru.setEditable(flag);
        }
    }


    /**
     * 判断属性对应组件是否可编辑
     * @return
     */
    public boolean isEditable()
    {
        if (myText != null)
        {
            return myText.isEditable();
        }
        if (myThru != null)
        {
            return myThru.isEditable();
        }
        else
        {
            return false;
        }
    }


    /**
     * 清空属性对应的组件
     */
    public void clear()
    {
        if (myText != null)
        {
            myText.setText("");
        }
        if (myCombo != null)
        {
            myCombo.setSelectedItem("");
        }
        if (myFrom != null)
        {
            myFrom.setText("");
        }
        if (myThru != null)
        {
            myThru.setText("");
        }
        if (myNot != null)
        {
            myNot.setSelected(false);
        }
        if (myPanel != null &&
            getLimitType().equalsIgnoreCase(CappBusinessInfo.ProjectRefTYPE))
        {
            ((ProjectPanel) myPanel).clear();
        }
        if (myPanel != null &&
            getLimitType().equalsIgnoreCase(CappBusinessInfo.CRefTYPE))
        {
            ((FolderPanel) myPanel).setLabelText("");
        }

    }


    /**
     * 判断属性是否枚举类型且不为空
     * @return
     */
    public boolean isEnumVSType()
    {
        if (!isEnumType())
        {
            if (verbose)
            {
                System.out.println("this is'nt EnumeratedType......");
            }
            return false;
        }
        if (verbose)
        {
            System.out.println("this is a EnumeratedType......");
        }
        return getValueSet().size() != 0;
    }


    /**
     * 判断属性是否枚举类型
     * @return
     */
    public boolean isEnumType()
    {
        String eType = getLimitType();
        boolean isE = false;
        if (verbose)
        {
            System.out.println(
                    "start isEnumType method...the limit type is:::" +
                    eType);
        }
        if (null != eType)
        {
            isE = (eType.equalsIgnoreCase(CappBusinessInfo.EnumTYPE));
        }
        if (verbose)
        {
            System.out.println("end isEnumType method. the result is:::" +
                               isE);
        }
        return isE;
    }


    /**
     * 访问myLabel属性
     * @return
     */
    public JLabel getLabelComp()
    {
        return myLabel;
    }


    /**
     * 访问myPanel属性
     * @return
     */
    public JPanel getPanelComp()
    {
        return myPanel;
    }


    /**
     * 访问myRange属性
     * @return
     */
    public JComboBox getRangeComp()
    {
        return myRange;
    }


    /**
     * 访问myFrom属性
     * @return
     */
    public JTextField getFromComp()
    {
        return myFrom;
    }


    /**
     * 访问myDash属性
     * @return
     */
    public JLabel getDashComp()
    {
        return myDash;
    }


    /**
     * 访问myThru属性
     * @return
     */
    public JTextField getThruComp()
    {
        return myThru;
    }


    /**
     * 访问myNot属性
     * @return
     */
    public JCheckBox getNotComp()
    {
        return myNot;
    }


    /**
     * 过载Object方法
     * @param s
     * @return
     */
    public boolean equals(String s)
    {
        String avalue = getValue() == null ? "" : getValue();
        return avalue.equals(s);
    }


    /**
     * 访问myRB属性
     * @return
     */
    public JButton getRefBComp()
    {
        return myRB;
    }


    /**
     * 访问myCombo属性
     * @return
     */
    public JComponent getFieldComp()
    {
        if (myCombo != null)
        {
            return myCombo;
        }
        else
        {
            return myText;
        }
    }


    /**
     * 访问myFG
     * @return
     */
    public Color getForeground()
    {
        return myFG;
    }


    /**
     * 设置myFG
     * @param color
     */
    public void setForeground(Color color)
    {
        if (color == null)
        {
            myFG = QM.getFGColor();
        }
        else
        {
            myFG = color;
        }
        refresh();
    }


    /**
     * 访问myBG
     * @return
     */
    public Color getBackground()
    {
        return myBG;
    }


    /**
     * 设置myBG
     * @param color
     */
    public void setBackground(Color color)
    {
        if (color == null)
        {
            myBG = QM.getBGColor();
        }
        else
        {
            myBG = color;
        }
        refresh();
    }


    /**
     * 访问myCanvasFG
     * @return
     */
    public Color getCanvasForeground()
    {
        return myCanvasFG;
    }


    /**
     * 设置myFG
     * @param color
     */
    public void setCanvasForeground(Color color)
    {
        myCanvasFG = color;
        refresh();
    }


    /**
     * 访问myCanvasBG
     * @return
     */
    public Color getCanvasBackground()
    {
        return myCanvasBG;
    }


    /**
     * 设置myCanvasBG
     * @param color
     */
    public void setCanvasBackground(Color color)
    {
        myCanvasBG = color;
        refresh();
    }


    /**
     * 刷新界面
     */
    public void refresh()
    {
        if (isRangeType())
        {
            if (getRange().equalsIgnoreCase(EQUAL) ||
                getRange().equalsIgnoreCase(LESS) ||
                getRange().equalsIgnoreCase(GREATER))
            {
                myFrom.setEnabled(true);
                myThru.setEnabled(false);
                myThru.repaint();
            }
            else
            if (getRange().equalsIgnoreCase(BETWEEN))
            {
                myFrom.setEnabled(true);
                myThru.setEnabled(true);
            }
            myFrom.setText(""); //???
            myFrom.repaint();
            myThru.setText(""); //???
            myThru.repaint();
        }
        else
        if (isReferenceType())
        {
            if (myRB != null)
            {
            }
        }
        else
        {
            getFieldComp().repaint();
        }
        if (myLabel.getParent() != null)
        {
            myLabel.getParent().repaint();
        }
    }


    /**
     * 获得枚举值
     * @return
     */
    public EnumeratedType getEnumValue()
    {
        if (myCombo == null)
        {
            return null;
        }
        else
        {
            if (enumType == null)
            {
                enumType = CappBusinessInfo.getAttributeValueSet(
                        myPD);
            }
            return enumType[myCombo.getSelectedIndex() - 1];
        }
    }


    /**
     * 获得选中的对象,没有返回null
     * @return
     */
    public Object getSelectedDetail()
    {
        if (getType().equalsIgnoreCase(CappBusinessInfo.ProjectRefTYPE))
        {
            return ((ProjectPanel) myPanel).getSelectedProject();
        }
        if (myObject != null && isFull())
        {
            return myObject;
        }
        else
        {
            return null;
        }
    }


    /**
     * 判断选择或填充值是否为空
     * @return
     */
    public boolean isNull()
    {

        if (isRangeType())
        {
            return myFrom.getText().trim().equals("") &&
                    myThru.getText().trim().equals("");
        }
        else if (getType().equalsIgnoreCase(CappBusinessInfo.ProjectRefTYPE))
        {
            return getSelectedDetail() == null;
        }
        else if (getType().equalsIgnoreCase(CappBusinessInfo.ViewRefTYPE))
        {
            if (myCombo.getSelectedItem().toString().trim().equals(""))
            {
                return true;
            }
            else
            {
                return false;
            }

        }
        else
        {
            return equals("");
        }
    }


    /**
     * 判断是否具体字符串中是否包含通配符(*,%)
     * @param s
     * @return
     */
    public static boolean isLike(String s)
    {
        if (s == null)
        {
            return false;
        }
        char ac[] = s.toCharArray();
        boolean isLike = false;
        for (int i = 0; i < s.length(); i++)
        {
            if (ac[i] == '*' || ac[i] == '%' || ac[i] == '?')
            {
                isLike = true;
                break;
            }
        }
        return isLike;
    }


    /**
     * 匹配检索关键字
     * @param s
     * @return
     */
    public static String likeClaus(String s)
    {
        if (isLike(s))
        {
            return QueryCondition.LIKE;
        }
        else
        {
            return QueryCondition.EQUAL;
        }
    }


    /**
     * 通过传入的字符串的解析匹配检索关键字
     * @return
     */
    public String likeClaus()
    {
        return likeClaus(getValue());
    }


    /**
     * 判断检索条件是否已填充
     * @return
     */
    public boolean isFull()
    {
        return!equals("");
    }


    /**
     * 访问属性名
     * @return
     */
    public String getAttributeName()
    {
        return CappBusinessInfo.getAttributeName(myPD);
    }


    /**
     * 访问检索条件的下限
     * @return
     */
    public String getFrom()
    {
        return myFrom.getText().trim();
    }


    /**
     * 设置检索条件的下限
     * @param s
     */
    public void setFrom(String s)
    {
        if (myFrom != null)
        {
            myFrom.setText(s);
        }
    }


    /**
     * 访问检索条件的上限
     * @return
     */
    public String getThru()
    {
        return myThru.getText().trim();
    }


    /**
     * 设置检索条件的下限
     * @param s
     */
    public void setThru(String s)
    {
        if (myThru != null)
        {
            myThru.setText(s);
        }
    }


    /**
     * 判断是否取反
     * @return
     */
    public boolean getNot()
    {
        return myNot.isSelected();
    }


    /**
     * 获得属性检索条件
     * @return
     */
    public String getValue()
    {
        if (verbose)
        {
            System.out.println("start getValue method ...");
        }
        String avalue = null;
        if (myText != null)
        {
            avalue = myText.getText();
        }
        else if (myCombo != null)
        {
            if (myCombo instanceof DataFormatChoice)
            {
                DataFormatInfo dfi = ((DataFormatChoice) myCombo).
                                     getSelectedDataFormat();
                if (dfi != null)
                {
                    avalue = dfi.getBsoID();
                }
            }
            else if (myCombo instanceof ViewChoice)
            {

                if (!myCombo.getSelectedItem().toString().trim().equals("") &&
                    ((ViewChoice) myCombo).getSelectedView() == null)
                {
                    avalue = null; //are you sure ?
                }
                else
                {
                    avalue = myCombo.getSelectedItem().toString();
                }

            }

            else
            {
                avalue = myCombo.getSelectedItem().toString();
            }
        }
        else if (myPanel != null && myPanel instanceof FolderPanel)
        {
            //if(verbose)
            //  System.out.println("the panel is:::"+myPanel);
            avalue = ((FolderPanel) myPanel).getFolderLocation();
        }
        else if (myFrom != null)
        { //&&(getRange().equalsIgnoreCase(EQUAL) ||getRange().equalsIgnoreCase(GREATER) ||getRange().equalsIgnoreCase(LESS))
            avalue = myFrom.getText();
            if (avalue.equals(""))
            {
                avalue = myThru.getText();
            }
        }
        if (avalue != null)
        {
            avalue = avalue.trim();
        }
        /*else
                 {
            avalue = "";
                 }*/
        if (verbose)
        {
            System.out.println("end getValue method the  result is:::" +
                               avalue);
        }
        return avalue;
    }


    /**
     * 是否是二级分类属性
     * @param cappquery CappQuery
     */

    public void setIsSecondClassifi(boolean flag)
    {
        isSecondClassifi = flag;
    }

    public void setCappQuery(CappQuery cappquery)
    {
        this.cappquery = cappquery;
    }

    public JPanel getTimePanel()
    {
        return timePanel;
    }


}
