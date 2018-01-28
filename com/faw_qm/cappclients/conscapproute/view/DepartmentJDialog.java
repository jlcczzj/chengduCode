/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * SS1 ��������б�����ԭ���õġ���֯��������Ϊ����֯����-bsx����������༭·��ʱ��ʾ����֯����-bsx�� liunan 2014-6-17
 * SS2 ��������гɶ�ʹ�á���֯����-cd liunan 2016-8-29
 */

package com.faw_qm.cappclients.conscapproute.view;

import java.awt.*;

import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.codemanage.client.view.CodeManageTree;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.remote.RequestHelper;

//CCBegin SS1
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
//CCEnd SS1

/**
 * <p>Title:��֯�������ֽ���</p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: һ��������˾</p>
 * @author skybird
 * @version 1.0
 */

public class DepartmentJDialog extends JDialog
{
    //�߼�Ԫ��
    private CodeManageTree departmentTree;
    /** ������ */
    private JFrame parentFrame;
    /** ���:�Ƿ񱣴����� */
    public boolean isSave = false;
    //��¼��ѡ�еĵ�λ���ƺ�bsoid
    public static Vector result = null;
    //��ѡ�е�λ������
    public static String departmentName = null;
    //���汻ѡ�еĵ�λ��bsoid
    public static String departmentNameid = null;

    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JButton okJButton = new JButton();
    JButton cancelJButton = new JButton();
    JLabel departmentJLabel = new JLabel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    GridBagLayout gridBagLayout2 = new GridBagLayout();
    GridBagLayout gridBagLayout3 = new GridBagLayout();

    //CCBegin SS1
    private String comp="";
    //CCEnd SS1
    
    public DepartmentJDialog(JFrame frame)
    {
        super(frame, "", true);
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
        result = null;
        try
        {
        	//CCBegin SS1
        	RequestServer server = RequestServerFactory.getRequestServer();
        	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
        	info1.setClassName("com.faw_qm.cappclients.conscapproute.util.RouteClientUtil");
            info1.setMethodName("getUserFromCompany");
            Class[] classes = {};
            info1.setParaClasses(classes);
            Object[] objs = {};
            info1.setParaValues(objs);
            comp=(String)server.request(info1);
            //CCEnd SS1
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
        this.getRootPane().setDefaultButton(this.okJButton);
        this.setSize(400, 380);
        this.getContentPane().setLayout(gridBagLayout3);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        jPanel1.setLayout(gridBagLayout2);
        jPanel2.setLayout(gridBagLayout1);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setRequestFocusEnabled(true);
        okJButton.setMnemonic('Y');
        okJButton.setText("ȷ��(Y)");
        okJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                okJButton_actionPerformed(e);
            }
        });

        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setVerifyInputWhenFocusTarget(true);
        cancelJButton.setMargin(new Insets(2, 14, 2, 14));
        cancelJButton.setText("ȡ��(Y)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });

        departmentJLabel.setText("·�ߵ�λ");
        this.getContentPane().add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
        this.getContentPane().add(jPanel2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(9, 225, 14, 10), 4, 0));
        jPanel2.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(9, 4, 20, 3), 16, 0));
        jPanel2.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(9, 3, 20, 2), 16, 0));
        jPanel1.add(jScrollPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(6, 0, 10, 4), 372, 253));
        jPanel1.add(departmentJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 251), 85, 4));
        initTree();
        addDepartmentTree();
    }

    /**
     * ���ػ�����
     */
    private void localize()
    {}

    private void initTree()
    {
        try
        {
            //��ʼ��·�ߵ�λ��
            CodingClassificationIfc cc = null;
            Class[] c = {String.class, String.class};
            System.out.println("��·�� comp=="+comp);
            //CCBegin SS1
            //Object[] obj = {"��֯����", "�������"};
            //cc = (CodingClassificationIfc)RequestHelper.request("CodingManageService", "findClassificationByName", c, obj);
            if(comp.equals("zczx"))
            {
            	Object[] obj = {"��֯����", "�������"};
            	cc = (CodingClassificationIfc)RequestHelper.request("CodingManageService", "findClassificationByName", c, obj);
            }
            //CCBegin SS2
            else if(comp.equals("cd"))
            {
            	Object[] obj = {"��֯����-cd", "�������"};
            	cc = (CodingClassificationIfc)RequestHelper.request("CodingManageService", "findClassificationByName", c, obj);
            }
            //CCEnd SS2
            else
            {
            	Object[] obj = {"��֯����-bsx", "�������"};
            	cc = (CodingClassificationIfc)RequestHelper.request("CodingManageService", "findClassificationByName", c, obj);
            }
            //CCEnd SS1

            if(cc != null)
            {
                departmentTree = new CodeManageTree(cc);
                departmentTree.setShowsRootHandles(false);
            }
        }catch(Exception ex)
        {
            String message = ex.getMessage();
            DialogFactory.showInformDialog(parentFrame, message);
        }
    }

    /**
     * �򱾽������·�ߵ�λ��
     * @param departmentTree
     */
    public void addDepartmentTree()
    {
        if(this.departmentTree != null)
        {
            this.departmentTree = departmentTree;
            jScrollPane1.getViewport().add(this.departmentTree, null);
            this.departmentTree.addMouseListener(new DepartmentJDialog_departmentTree_mouseAdapter(this));
        }
    }

    /**
     * ����֯�ṹ���ϵ�����
     * @param e ����¼�
     */
    void departmentTree_mousePressed(MouseEvent e)
    {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)departmentTree.getLastSelectedPathComponent();
        if(node == null)
        {
            return;
        }
        Object object = node.getUserObject();
        if(object instanceof CodingIfc || (object instanceof CodingClassificationIfc))
        {
            //String departmentName = null;
            if(object instanceof CodingIfc)
            {
                CodingIfc bvi = (CodingIfc)object;
                departmentName = bvi.getCodeContent();
                departmentNameid = bvi.getBsoID();
                //graphPanel.setCurrentRouteDepartment(bvi.getBsoID(),departmentName);
            }else if(object instanceof CodingClassificationIfc)
            {
                CodingClassificationIfc bvi = (CodingClassificationIfc)object;
                departmentName = bvi.getClassSort();
                departmentNameid = bvi.getBsoID();
            }
            this.departmentName = departmentName;
        }
    }

    /**
     * ȷ����ť�����¼�
     * @param e
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        if(departmentName != null)
        {
            isSave = true;
        }
        this.setVisible(false);
    }

    /**
     * ���ִ���ˡ�ȡ�������������о������ύ���档
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

    //Overridden so we can exit when window is closed
    protected void processWindowEvent(WindowEvent e)
    {
        if(e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            cancel();
        }
        super.processWindowEvent(e);
    }

    //Close the dialog
    void cancel()
    {
        dispose();
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
}

/**
 * <p>Title:DepartmentJDialog_departmentTree_mouseAdapter</p> <p>Description: ��������</p> <p>Package:com.faw_qm.cappclients.conscapproute.view</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author
 * @version 1.0
 */
class DepartmentJDialog_departmentTree_mouseAdapter extends java.awt.event.MouseAdapter
{ //��֯�������ֽ���
    private DepartmentJDialog adaptee;

    //���캯��
    DepartmentJDialog_departmentTree_mouseAdapter(DepartmentJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    public void mousePressed(MouseEvent e)
    {
        adaptee.departmentTree_mousePressed(e);
    }
}
