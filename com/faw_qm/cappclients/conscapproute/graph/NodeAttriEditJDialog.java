/**
 * ���ɳ��� NodeAttriEditJDialog.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 *
 *CR1  ������  2009/12/23 ԭ��TD���⣬��v4r3FunctionTest,TD�ţ�2537
 *
 */
package com.faw_qm.cappclients.conscapproute.graph;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
import java.awt.Dialog;

import com.faw_qm.framework.util.*;
import com.faw_qm.framework.remote.*;
import com.faw_qm.help.*;
import com.faw_qm.technics.consroute.model.*;
import com.faw_qm.technics.consroute.util.*;
import com.faw_qm.cappclients.util.*;

/**
 * <p> Title:�༭·�߽ڵ�Ľ��� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @mender skybird
 * @version 1.0
 */

public class NodeAttriEditJDialog extends JDialog
{
    //��ѯʱ��
    private CalendarSelectedPanel calendarSelectedValid = new CalendarSelectedPanel();

    private CalendarSelectedPanel calendarSelectedInvalid = new CalendarSelectedPanel();

    //ed
    private JPanel panel1 = new JPanel();

    private JLabel jLabel1 = new JLabel();

    private JLabel jLabel2 = new JLabel();

    private JLabel nameJLabel = new JLabel();

    private JComboBox typeJComboBox = new JComboBox();

    private JPanel jPanel1 = new JPanel();

    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();

    private JButton helpJButton = new JButton();

    /** �ڵ���� */
    private GraphNodeComponent nodeComponent;

    /** �ڵ�ֵ���� */
    private RouteNodeInfo theNodeInfo;

    /** �Ƿ��ѱ��� */
    public boolean isSave = false;

    /** �Ƿ�����༭ */
    private boolean isEditable = true;

    /** ��Դ�ļ� */
    private static final String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.GraphRB";

    //���������Ļ���
    private QMHelpContext helpContext;

    //����ϵͳ
    private QMHelpSystem helpSystem;

    private JFrame parentFrame;

    JPanel jPanel2 = new JPanel();

    JLabel jLabel3 = new JLabel();

    JScrollPane jScrollPane1 = new JScrollPane();

    JTextArea descriJTextArea = new JTextArea();

    JPanel jPanel3 = new JPanel();

    /**
     * ��Чʱ���ʧЧʱ��
     */
    JLabel timeJLabel = new JLabel();

    JLabel timeJLabel1 = new JLabel();

    JList jList1 = new JList();

    JCheckBox jCheckBox1 = new JCheckBox();

    GridBagLayout gridBagLayout1 = new GridBagLayout();

    GridBagLayout gridBagLayout4 = new GridBagLayout();

    GridBagLayout gridBagLayout2 = new GridBagLayout();

    GridBagLayout gridBagLayout3 = new GridBagLayout();

    //ed

    /**
     * ���캯��
     * @param frame
     * @param isEdit �Ƿ�����༭
     */
    /*
     * public NodeAttriEditJDialog(Frame frame, boolean isEdit) { super(frame, "", true); try { parentFrame = (JFrame) frame; isEditable = isEdit; jbInit(); } catch (Exception ex) {
     * ex.printStackTrace(); } }
     */
    public NodeAttriEditJDialog(Dialog prent, Frame frame, boolean isEdit)
    {//CR1
        super((Dialog)prent, "", true);
        try
        {
            parentFrame = (JFrame)frame;
            isEditable = isEdit;
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * ���캯��
     */
    public NodeAttriEditJDialog()
    {
        this(null, null, true);//CR1
    }

    /**
     * �����ʼ��
     * @throws Exception
     */
    private void jbInit()
    {
        this.setResizable(true);
        this.setTitle(QMMessage.getLocalizedMessage(RESOURCE, "editNodeTitle", null));
        this.setSize(new Dimension(452, 324));
        this.getContentPane().setLayout(null);
        panel1.setLayout(gridBagLayout2);
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setText("·�ߵ�λ");
        jLabel2.setMaximumSize(new Dimension(44, 22));
        jLabel2.setMinimumSize(new Dimension(44, 22));
        jLabel2.setPreferredSize(new Dimension(44, 22));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText("·�����");
        nameJLabel.setMaximumSize(new Dimension(48, 22));
        nameJLabel.setMinimumSize(new Dimension(48, 22));
        nameJLabel.setPreferredSize(new Dimension(48, 22));
        nameJLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nameJLabel.setText("");
        jPanel1.setLayout(gridBagLayout3);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
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
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("ȡ��(C)");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        helpJButton.setMaximumSize(new Dimension(78, 23));
        helpJButton.setMinimumSize(new Dimension(78, 23));
        helpJButton.setPreferredSize(new Dimension(78, 23));
        helpJButton.setMnemonic('0');
        helpJButton.setText("����(H)");
        helpJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                helpJButton_actionPerformed(e);
            }
        });
        //radioJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        //radioJLabel.setText("���˵��");
        //ratioJTextArea.setMinimumSize(new Dimension(0, 18));
        //ratioJTextArea.setPreferredSize(new Dimension(0, 18));
        //ratioJTextArea.setCaretPosition(0);
        //ratioJTextArea.setText("");
        jPanel2.setLayout(gridBagLayout4);
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabel3.setText("˵��");
        jLabel3.setVerticalAlignment(SwingConstants.CENTER);
        descriJTextArea.setText("");
        jPanel3.setLayout(gridBagLayout1);
        timeJLabel.setToolTipText("");
        timeJLabel.setVerifyInputWhenFocusTarget(true);
        timeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        timeJLabel.setText("��Чʱ��");

        //st skybird 2005.2.28
        //ed
        timeJLabel1.setText("ʧЧʱ��");
        calendarSelectedInvalid.setAlignmentX((float)0.5);
        calendarSelectedInvalid.setMinimumSize(new Dimension(79, 23));
        jPanel3.setOpaque(true);
        jPanel3.setBounds(new Rectangle(12, 195, 412, 56));
        jPanel2.setBounds(new Rectangle(13, 92, 422, 106));
        jPanel1.setMaximumSize(new Dimension(250, 54));
        jPanel1.setMinimumSize(new Dimension(250, 54));
        jPanel1.setPreferredSize(new Dimension(250, 54));
        jPanel1.setToolTipText("");
        jPanel1.setBounds(new Rectangle(177, 246, 266, 54));
        panel1.setBounds(new Rectangle(17, 8, 412, 63));
        //panel1.add(jScrollPane2, new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0
        //        ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(16,
        // 0, 14, 8), 349, 64));
        //jScrollPane2.getViewport().add(ratioJTextArea, null);

        jCheckBox1.setText("��ʱ·��");
        panel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8, 0, 0, 0), 0, 0));
        panel1.add(nameJLabel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8, 0, 0, 0), 0, 0));
        panel1.add(typeJComboBox, new GridBagConstraints(1, 1, 2, 2, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(9, 0, 0, 8), 326, 0));
        panel1.add(jLabel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 0, 4), 7, 0));
        panel1.add(jCheckBox1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8, 0, 0, 0), 0, 0));
        this.getContentPane().add(jPanel3, null);
        //panel1.add(radioJLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
        //      ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(14, 0,
        // 82, 4), 6, 4));
        this.getContentPane().add(jList1, null);
        jPanel2.add(jScrollPane1, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 0, 6, 19), 343, 76));
        jPanel2.add(jLabel3, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(1, 10, 89, 4), 22, 0));
        jScrollPane1.getViewport().add(descriJTextArea, null);
        descriJTextArea.setLineWrap(true);
        //st skybird 2005.2.28
        jPanel3.add(calendarSelectedInvalid, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 8, 9), 68, 25));
        jPanel3.add(timeJLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(13, 0, 27, 4), 8, 0));
        jPanel3.add(calendarSelectedValid, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 8, 0), 77, 25));
        jPanel3.add(timeJLabel1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(16, 0, 24, 0), 4, 0));
        this.getContentPane().add(jPanel1, null);
        this.getContentPane().add(jPanel2, null);
        //ed
        jPanel1.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 4, 26, 4), 0, 0));
        jPanel1.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 26, 4), 5, 0));
        jPanel1.add(helpJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 0, 26, 4), -6, 0));
        this.getContentPane().add(panel1, null);
        localize();
        initializeHelp();
    }

    /**
     * ���ػ�
     */
    private void localize()
    {
        try
        {
            jLabel1.setText(QMMessage.getLocalizedMessage(RESOURCE, "department", null));
            jLabel2.setText(QMMessage.getLocalizedMessage(RESOURCE, "departmentType", null));
            jLabel3.setText(QMMessage.getLocalizedMessage(RESOURCE, "describe", null));
            okJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "okJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "cancelJButton", null));
            helpJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "helpJButton", null));
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }

        typeJComboBox.addItem(RouteCategoryType.MANUFACTUREROUTE.getDisplay());
        typeJComboBox.addItem(RouteCategoryType.ASSEMBLYROUTE.getDisplay());
        typeJComboBox.setSelectedItem(RouteCategoryType.getRouteCategoryTypeDefault().getDisplay());

        if(!isEditable)
        {
            this.setTitle(QMMessage.getLocalizedMessage(RESOURCE, "viewNodeTitle", null));
            okJButton.setVisible(false);
            typeJComboBox.setEnabled(false);
            descriJTextArea.setEditable(false);
            //st skybird 2005.2.28
            calendarSelectedValid.setEnabled(false);
            calendarSelectedInvalid.setEnabled(false);
            //ratioJTextArea.setEditable(false);
            //ed
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
     * @param flag ����Visible
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }

    /**
     * ��ñ��༭�Ľڵ����
     * @return �ڵ����
     */
    public GraphNodeComponent getNodeComponent()
    {
        return nodeComponent;
    }

    /**
     * ���ñ༭ָ���Ľڵ����
     * @param nodeComponent ָ���Ľڵ����
     */
    public void setNodeComponent(GraphNodeComponent nodeComponent)
    {
        this.nodeComponent = nodeComponent;
        setDataDisplay();
    }

    /**
     * ���ý�����ʾ�ڵ��ԭʼ��Ϣ
     */
    private void setDataDisplay()
    {
        //��ýڵ�ֵ����
        theNodeInfo = (RouteNodeInfo)nodeComponent.getGraphNode().getRouteItem().getObject();
        nameJLabel.setText(nodeComponent.getGraphNode().getDepartmentName());
        typeJComboBox.setSelectedItem(theNodeInfo.getRouteType());
        descriJTextArea.setText(theNodeInfo.getNodeDescription());
        //st skybird 2005.2.25
        //ratioJTextArea.setText(theNodeInfo.getNodeRatio());
        this.jCheckBox1.setSelected(theNodeInfo.getIsTempRoute());
        calendarSelectedValid.setDate(theNodeInfo.getNodeValidTime());
        calendarSelectedInvalid.setDate(theNodeInfo.getNodeInvalidTime());
        //ed
    }

    /**
     * ���������ĸ���
     * @param e java.awt.event.ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        String validStr = calendarSelectedValid.getDate();
        String invalidStr = calendarSelectedInvalid.getDate();
        if(validStr != null && !validStr.equals(""))
            if(invalidStr != null && !invalidStr.equals(""))
            {
                Date vaDate = new Date(validStr);
                Date invalDate = new Date(invalidStr);
                if(vaDate.after(invalDate))
                {
                    JOptionPane.showMessageDialog(null, "��Чʱ�䲻�ܳ���ʧЧʱ��", "��ʾ", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

        isSave = true;
        updatedNodeComponent();
        this.setVisible(false);
    }

    /**
     * �����Խڵ�ĸ���
     * @param e java.awt.event.ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        isSave = false;
        this.setVisible(false);
    }

    /**
     * ��ø��º�Ľڵ����
     */
    public void updatedNodeComponent()
    {
        if(isSave)
        {

            theNodeInfo.setRouteType(typeJComboBox.getSelectedItem().toString());
            theNodeInfo.setNodeDescription(descriJTextArea.getText());
            //theNodeInfo.setNodeRatio(ratioJTextArea.getText());

            theNodeInfo.setNodeValidTime(calendarSelectedValid.getDate());
            theNodeInfo.setNodeInvalidTime(calendarSelectedInvalid.getDate());
            theNodeInfo.setIsTempRoute(jCheckBox1.isSelected());
            //ed
            nodeComponent.getGraphNode().getRouteItem().setObject(theNodeInfo);
            nodeComponent.getGraphNode().getRouteItem().setState(RouteItem.UPDATE);
            //����·�����͸��½ڵ�ͼ��
            nodeComponent.getGraphNode().updateImage();
            nodeComponent.updateImage();
        }
    }

    /**
     * Ѱ�����.���ӵ�Webҳ"�༭·�߽ڵ�"
     * @param e java.awt.event.ActionEvent
     */
    void helpJButton_actionPerformed(ActionEvent e)
    {
        showHelp();
    }

    /**
     * ��ʾ����ҳ
     * @roseuid 3DFD6B9403BE
     */
    private void showHelp()
    {
        if(helpSystem == null)
        {
            initializeHelp();
        }else
        {
            helpSystem.showHelp("EditRouteNodeHelp");
        }
    }

    /**
     * ��ʼ������
     */
    private void initializeHelp()
    {
        try
        {
            //��������ļ����ڷ��������������˿�
            //URL url = new URL("http://pdmlm:80/");
            URL url = null;
            //�������ϵͳ��
            /*
             * ���� helptest �������������û�������Ҫ�������ݣ� url �����ļ����ڷ��������������˿� OnlineHelp ����������� ResourceBundle ������Դ
             */
            helpSystem = new QMHelpSystem("capproute", url, "OnlineHelp", ResourceBundle.getBundle("com.faw_qm.cappclients.conscapproute.util.RouteHelpRB", RemoteProperty.getVersionLocale()));
            //System.out.println(helpSystem.getResources());
            //�����������ID�����û�����Ĺ���ģʽ���硰Update������Create������View����
            String s = "EditRouteNode";
            //������������Ļ���
            helpContext = new QMHelpContext(parentFrame, helpSystem, s);
        }catch(Exception exception)
        {
            exception.printStackTrace();
        }
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
        NodeAttriEditJDialog d = new NodeAttriEditJDialog();
        d.setVisible(true);
    }

}
