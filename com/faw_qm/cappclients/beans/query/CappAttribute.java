/** ���ɳ���QMAttribute.java    1.0  2003/02/06
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �����乫˾������ʾ�� liuyang 2013-3-26
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
 * <p>Description:��ҵ���������PropertyDescript��ʾΪĳһ���������
 * ��װΪQmAttribute����</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author ������
 * @version 1.0
 */

public class CappAttribute implements ItemListener, KeyListener,
        CappQueryListener, ActionListener
{

    /**
     * �����ļ��б�ʶ
     */
    protected static final int PROCESS_BROWSE_CABINET = 0;


    /**
     * �����û���ʶ
     */
    protected static final int PROCESS_BROWSE_USER = 1;


    /**
     * ���Coding��ʶ
     */
    protected static final int PROCESS_BROWSE_CODING = 2;


    /**
     * �����"="
     */
    public static final String EQUAL = "=";


    /**
     * �����"<"
     */
    public static final String LESS = "<";


    /**
     * �����">"
     */
    public static final String GREATER = ">";


    /**
     * �����"BETWEEN"
     */
    public static final String BETWEEN = "BETWEEN";


    /**
     * ���������ʽ
     */
    public static final String DateInputFormat = "dateInputFormat";

    private boolean dontClear = false;


    /**
     * ���ݽ�����ʽ
     */
    public static final String QueryDateInputFormat = "queryDateInputFormat";

    private static final String EMPTY_STRING = ""; //���ַ���ʶ
    private static final String BrowseCMD = "Browse"; //������ʾ��
    private static final String BROWSE_CABINET = "BrowseCabinet"; //�����ļ�����ʾ��
    private static final String BROWSE_USER = "BrowseUser"; //�����û���ʾ��
    private static final String BROWSE_CODIND = "BrowseCoding";
    private static String relationalConstraints[] = null; //����Լ���Ƚϱ�ʶ��
    private static String timeConstraints[] = null; //ʱ��Լ���Ƚ��������ʶ��
    private PropertyDescript myPD; //��������
    private BaseValueIfc myObject; //ѡ�����ö���

    private EnumeratedType[] enumType; //ö�ټ�
    private Vector myValueSet; //ö��
    private JPanel myPanel; //���ϼУ�������Ŀbean
    private JLabel myLabel; //������ʾ����ǩ
    private JTextField myText; //�ַ�����
    private JComboBox myCombo; //ö��ѡ��
    private JComboBox myRange; //������ѡ��
    private JTextField myFrom; //����
    private JLabel myDash; //�ָ��
    private JTextField myThru; //����
    private JPanel timePanel;
    private JCheckBox myNot; //ȡ��
    private JButton myRB; //�����ť
    private QmChooser myRQ; //ѡ��Ի���
    private static ThreadGroup theThreadGroup = Thread.currentThread().
                                                getThreadGroup();
    private Color myBG; //ǰ��ɫ
    private Color myFG; //����ɫ
    private Color myCanvasBG; //����ǰ��ɫ
    private Color myCanvasFG; //��������ɫ
    private static boolean verbose = QM.getVerbose(); //debug��ʶ


    /**�Ƿ��Ƕ�����������*/
    private boolean isSecondClassifi;
    private CappQuery cappquery;


    /**
     * <p>Title: </p>
     * <p>Description:�߳̿����� </p>
     * <p>Copyright: Copyright (c) 2002</p>
     * <p>Company: qm</p>
     * @author ������
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
     * ���ؽӿڷ���
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
     * ���������������ͱ�ʶ������CappAttribute
     * @param propertydescript
     * @param flag ��ʾ��������Ŀɱ༭�ԡ�true-->�ɱ༭��false-->���ɱ༭
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
     * ��ʼ����ɫ
     */
    private void initColor()
    {
        myBG = QM.getBGColor();
        myFG = QM.getFGColor();
        myCanvasBG = QM.getCanvasFGColor();
        myCanvasFG = QM.getCanvasBGColor();
    }


    /**
     * ����������ʾ��
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
   			    disname = "�ʱ����ļ����";
   			   return disname;
   			}
   		   if(attrname.equals("femaNum")){
   			   disname = "��Ʒƽ̨";
   			   return disname;
   			}
   		   if(attrname.equals("taskInstructNum")){
   			   disname = "������";
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
     * �����������͡�����������CappBusinessInfo���Գ�������ʽ����
     * @return
     */
    public String getType()
    {
        return CappBusinessInfo.getAttributeType(myPD);
    }


    /**
     * ����������͵ľ�������
     * @return
     */
    public String getLimitType()
    {
        return CappBusinessInfo.getLimitType(myPD);
    }


    /**
     * ������Լ�
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
     * �ж������Ƿ�ɱȽϵ�
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
     * �������Լ���ıȽ��������ʶ��
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
     * ���ʱ��Լ���ıȽ��������ʶ��
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
     * ������Ŀ״̬�ı���Ӧ�ıȽ��������ʶ
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
     * ���ñȽ��������ʶ
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
     * ��ñȽ��������ʶ
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
     * ��ñȽ����������Ӧ����ĳ���
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
     * �ж������Ƿ�Ϊ��������
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
     * �����°�ť
     * @param name ��ť�ı�
     * @param actType ����ָ��
     * @param w int ��ť��
     * @param h int ��ť��
     * @return ��ť
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
     * ���������ļ��л����û�ʱ���������
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
     *  ���Coding
     */
    private void processBrowseCoding()
    {
        //add by guoxl on 2008.3.28(�������չ���в�Ʒ״̬���͹����������������ť����������ı�����������������Ʋ�ͳһ)
        String str=myPD.getLimitValue(CappBusinessInfo.SortType);
        String title="";
        int index=str.indexOf(":");
        if(null==str){
            title="�������";
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
     * ����ļ��У�����FolderPanel
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
            //����ѡ�����ϼд���
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
     * ����û�������UserSelectorDialog
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
     * ��þ�������Ķ���������
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
     * ���������ʾ�ı�
     * @param text ����ʾ�ı�
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
     * ��������Ŀɱ༭��
     * @param flag true-->�ɱ༭��false-->���ɱ༭
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
     * �ж����Զ�Ӧ����Ƿ�ɱ༭
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
     * ������Զ�Ӧ�����
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
     * �ж������Ƿ�ö�������Ҳ�Ϊ��
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
     * �ж������Ƿ�ö������
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
     * ����myLabel����
     * @return
     */
    public JLabel getLabelComp()
    {
        return myLabel;
    }


    /**
     * ����myPanel����
     * @return
     */
    public JPanel getPanelComp()
    {
        return myPanel;
    }


    /**
     * ����myRange����
     * @return
     */
    public JComboBox getRangeComp()
    {
        return myRange;
    }


    /**
     * ����myFrom����
     * @return
     */
    public JTextField getFromComp()
    {
        return myFrom;
    }


    /**
     * ����myDash����
     * @return
     */
    public JLabel getDashComp()
    {
        return myDash;
    }


    /**
     * ����myThru����
     * @return
     */
    public JTextField getThruComp()
    {
        return myThru;
    }


    /**
     * ����myNot����
     * @return
     */
    public JCheckBox getNotComp()
    {
        return myNot;
    }


    /**
     * ����Object����
     * @param s
     * @return
     */
    public boolean equals(String s)
    {
        String avalue = getValue() == null ? "" : getValue();
        return avalue.equals(s);
    }


    /**
     * ����myRB����
     * @return
     */
    public JButton getRefBComp()
    {
        return myRB;
    }


    /**
     * ����myCombo����
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
     * ����myFG
     * @return
     */
    public Color getForeground()
    {
        return myFG;
    }


    /**
     * ����myFG
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
     * ����myBG
     * @return
     */
    public Color getBackground()
    {
        return myBG;
    }


    /**
     * ����myBG
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
     * ����myCanvasFG
     * @return
     */
    public Color getCanvasForeground()
    {
        return myCanvasFG;
    }


    /**
     * ����myFG
     * @param color
     */
    public void setCanvasForeground(Color color)
    {
        myCanvasFG = color;
        refresh();
    }


    /**
     * ����myCanvasBG
     * @return
     */
    public Color getCanvasBackground()
    {
        return myCanvasBG;
    }


    /**
     * ����myCanvasBG
     * @param color
     */
    public void setCanvasBackground(Color color)
    {
        myCanvasBG = color;
        refresh();
    }


    /**
     * ˢ�½���
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
     * ���ö��ֵ
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
     * ���ѡ�еĶ���,û�з���null
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
     * �ж�ѡ������ֵ�Ƿ�Ϊ��
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
     * �ж��Ƿ�����ַ������Ƿ����ͨ���(*,%)
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
     * ƥ������ؼ���
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
     * ͨ��������ַ����Ľ���ƥ������ؼ���
     * @return
     */
    public String likeClaus()
    {
        return likeClaus(getValue());
    }


    /**
     * �жϼ��������Ƿ������
     * @return
     */
    public boolean isFull()
    {
        return!equals("");
    }


    /**
     * ����������
     * @return
     */
    public String getAttributeName()
    {
        return CappBusinessInfo.getAttributeName(myPD);
    }


    /**
     * ���ʼ�������������
     * @return
     */
    public String getFrom()
    {
        return myFrom.getText().trim();
    }


    /**
     * ���ü�������������
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
     * ���ʼ�������������
     * @return
     */
    public String getThru()
    {
        return myThru.getText().trim();
    }


    /**
     * ���ü�������������
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
     * �ж��Ƿ�ȡ��
     * @return
     */
    public boolean getNot()
    {
        return myNot.isSelected();
    }


    /**
     * ������Լ�������
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
     * �Ƿ��Ƕ�����������
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
