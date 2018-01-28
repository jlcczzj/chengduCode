/** 
 * ���ɳ��� SelectPartTreeJDialog.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Dimension;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.faw_qm.cappclients.conscapproute.util.PartMasterTreeNode;
import com.faw_qm.cappclients.conscapproute.util.PartMasterTreeObject;
import com.faw_qm.cappclients.conscapproute.util.PartMasterTreePanel;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMExceptionHandler;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.util.RouteListLevelType;

/**
 * <p> �Ӳ�Ʒ�ṹ������㲿��ʱ,���ñ�����,ѡ���㲿�� </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */

public class SelectPartTreeJDialog extends JDialog
{
    private JPanel panel1 = new JPanel();

    private JPanel buttonPanel = new JPanel();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JLabel statusJLabel = new JLabel();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();

    /** ��ǣ��Ƿ�ִ���˱������ */
    public boolean isSave = false;

    /** ҵ�����:·�߱� */
    private TechnicsRouteListIfc myRouteList;

    /** ������ */
    private Frame parentFrame;

    /** ��Դ�ļ�·�� */
    private static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /** �㲿���� */
    private PartMasterTreePanel partTreeJPanel;

    /** ������Ա��� */
    private static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /**
     * ���캯��
     * @param frame ������
     */
    public SelectPartTreeJDialog(Frame frame)
    {
        super(frame, "", true);
        parentFrame = frame;
        try
        {
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * ���캯��
     */
    public SelectPartTreeJDialog()
    {
        this(null);
    }

    /**
     * �����ʼ��
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("ѡ���㲿��");
        this.setSize(650, 500);
        this.addWindowListener(new SelectPartTreeJDialog_this_windowAdapter(this));
        panel1.setLayout(gridBagLayout2);
        buttonPanel.setLayout(gridBagLayout1);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
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
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��(C)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setMaximumSize(new Dimension(41, 22));
        statusJLabel.setMinimumSize(new Dimension(41, 22));
        statusJLabel.setPreferredSize(new Dimension(41, 22));
        statusJLabel.setText("�Ӳ�Ʒ�ṹ��ѡ�����༭·�ߵ��㲿��");

        buttonPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        buttonPanel.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        panel1.add(statusJLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        getContentPane().add(panel1);

        panel1.add(buttonPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(20, 0, 20, 10), 0, 0));
        localize();

    }

    /**
     * ���ػ�
     */
    private void localize()
    {

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

    /**
     * ����·�߱�
     * @param list ·�߱�
     */
    public void setRouteList(TechnicsRouteListIfc list)
    {
        try
        {
            this.myRouteList = list;
            QMPartMasterIfc productInfo = (QMPartMasterIfc)RParentJPanel.refreshInfo(list.getProductMasterID());
            partTreeJPanel = new PartMasterTreePanel(RParentJPanel.getIdentity(productInfo) + "�Ĳ�Ʒ�ṹ");
            panel1.add(partTreeJPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
            this.addProductToTree(productInfo);
        }catch(Exception ex)
        {
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showWarningDialog(this, message);
        }
    }

    /**
     * ���·�߱�
     * @return ·�߱�
     */
    public TechnicsRouteListIfc getRouteList()
    {
        return this.myRouteList;
    }

    /**
     * ��ø�����
     * @return ������
     */
    public Frame getParentFrame()
    {
        return this.parentFrame;
    }

    /**
     * �Ѳ�Ʒ�ṹ�����з����������㲿��������б���
     * @param parts �㲿��ֵ���󼯺�
     */
    private void addProductToTree(QMPartMasterIfc product) throws QMException
    {
        Class[] c = {QMPartMasterIfc.class};
		Object[] obj = {product};
		Collection cc = (Collection)RequestHelper.request("consTechnicsRouteService", "getSubPartMasters", c, obj);
		for(Iterator iter = cc.iterator();iter.hasNext();)
		{
		    PartMasterTreeObject treeobj = new PartMasterTreeObject((QMPartMasterIfc)iter.next());
		    partTreeJPanel.addNode(treeobj);
		}
    }

    /**
     * ȷ�ϲ���
     * @param e ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        isSave = true;
        this.setVisible(false);
    }

    /**
     * ȡ������
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        isSave = false;
        this.setVisible(false);
    }

    /**
     * ����û���ѡ��������㲿��
     * @return �㲿��ֵ���󼯺�
     */
    public Vector getSelectedParts()
    {
        Vector v = new Vector();
        //����������б�ѡ�еĽڵ�
        PartMasterTreeNode[] nodes = partTreeJPanel.getSelectedNodes();
        if(nodes != null && nodes.length > 0)
        {

            for(int i = 0;i < nodes.length;i++)
            {
                if(nodes[i].getObject() != null)
                {
                    QMPartMasterInfo masterInfo = (QMPartMasterInfo)nodes[i].getObject().getObject();
                    v.add(masterInfo);
                }
            }
            //����Ƕ���·�ߣ�����м��
            if(this.myRouteList.getRouteListLevel().equals(RouteListLevelType.SENCONDROUTE.getDisplay()))
            {
                Class[] c = {String.class, String.class, Collection.class};
                Object[] objs = {myRouteList.getRouteListDepartment(), myRouteList.getProductMasterID(), v};
                Object[] array = null;
                try
                {
                    array = (Object[])RequestHelper.request("consTechnicsRouteService", "checkSubParts", c, objs);
                }catch(Exception ex)
                {
                    String message = QMExceptionHandler.handle(ex);
                    DialogFactory.showWarningDialog(this, message);
                    return null;
                }
                if(array != null)
                {
                    Vector v1 = (Vector)array[0]; //�����������㲿������Ϣ
                    Vector v2 = (Vector)array[1]; //�������������㲿������Ϣ
                    if(v2 != null && v2.size() > 0)
                    {
                        String message = "�㲿��";
                        for(int j = 0;j < v2.size();j++)
                        {
                            String partNumber = ((QMPartMasterInfo)v2.elementAt(j)).getPartNumber();
                            if(j < v2.size() - 1)
                                message = message + partNumber + "��";
                            else
                                message = message + partNumber + "�ڵ�ǰ·�߱��в��ܱ༭·�ߡ�";
                        }
                        JOptionPane.showMessageDialog(this, message, "֪ͨ", JOptionPane.INFORMATION_MESSAGE);
                    }
                    return v1;
                }
            }
        }
        return v;
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
        ///SelectPartJDialog d = new SelectPartJDialog();
        //d.setVisible(true);
    }

    void this_windowClosing(WindowEvent e)
    {
        this.isSave = false;
        this.setVisible(false);
    }

}

/**
 * <p>Title:�����¼�������</p> <p>Description: </p>
 */
class SelectPartTreeJDialog_this_windowAdapter extends java.awt.event.WindowAdapter
{
    private SelectPartTreeJDialog adaptee;

    SelectPartTreeJDialog_this_windowAdapter(SelectPartTreeJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void windowClosing(WindowEvent e)
    {
        adaptee.this_windowClosing(e);
    }
}