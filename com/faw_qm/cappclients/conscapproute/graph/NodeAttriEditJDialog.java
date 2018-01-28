/**
 * 生成程序 NodeAttriEditJDialog.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 *
 *CR1  郭晓亮  2009/12/23 原因：TD问题，域v4r3FunctionTest,TD号：2537
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
 * <p> Title:编辑路线节点的界面 </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: 一汽启明 </p>
 * @author 刘明
 * @mender skybird
 * @version 1.0
 */

public class NodeAttriEditJDialog extends JDialog
{
    //查询时间
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

    /** 节点组件 */
    private GraphNodeComponent nodeComponent;

    /** 节点值对象 */
    private RouteNodeInfo theNodeInfo;

    /** 是否已保存 */
    public boolean isSave = false;

    /** 是否允许编辑 */
    private boolean isEditable = true;

    /** 资源文件 */
    private static final String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.GraphRB";

    //帮助上下文环境
    private QMHelpContext helpContext;

    //帮助系统
    private QMHelpSystem helpSystem;

    private JFrame parentFrame;

    JPanel jPanel2 = new JPanel();

    JLabel jLabel3 = new JLabel();

    JScrollPane jScrollPane1 = new JScrollPane();

    JTextArea descriJTextArea = new JTextArea();

    JPanel jPanel3 = new JPanel();

    /**
     * 生效时间和失效时间
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
     * 构造函数
     * @param frame
     * @param isEdit 是否允许编辑
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
     * 构造函数
     */
    public NodeAttriEditJDialog()
    {
        this(null, null, true);//CR1
    }

    /**
     * 界面初始化
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
        jLabel1.setText("路线单位");
        jLabel2.setMaximumSize(new Dimension(44, 22));
        jLabel2.setMinimumSize(new Dimension(44, 22));
        jLabel2.setPreferredSize(new Dimension(44, 22));
        jLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel2.setText("路线类别");
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
        okJButton.setText("确定(Y)");
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
        cancelJButton.setText("取消(C)");
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
        helpJButton.setText("帮助(H)");
        helpJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                helpJButton_actionPerformed(e);
            }
        });
        //radioJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        //radioJLabel.setText("配比说明");
        //ratioJTextArea.setMinimumSize(new Dimension(0, 18));
        //ratioJTextArea.setPreferredSize(new Dimension(0, 18));
        //ratioJTextArea.setCaretPosition(0);
        //ratioJTextArea.setText("");
        jPanel2.setLayout(gridBagLayout4);
        jLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel3.setHorizontalTextPosition(SwingConstants.LEADING);
        jLabel3.setText("说明");
        jLabel3.setVerticalAlignment(SwingConstants.CENTER);
        descriJTextArea.setText("");
        jPanel3.setLayout(gridBagLayout1);
        timeJLabel.setToolTipText("");
        timeJLabel.setVerifyInputWhenFocusTarget(true);
        timeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        timeJLabel.setText("生效时间");

        //st skybird 2005.2.28
        //ed
        timeJLabel1.setText("失效时间");
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

        jCheckBox1.setText("临时路线");
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
     * 本地化
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
     * 设置界面的显示位置
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
     * 重载父类方法，使界面显示在屏幕中央
     * @param flag 设置Visible
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }

    /**
     * 获得被编辑的节点组件
     * @return 节点组件
     */
    public GraphNodeComponent getNodeComponent()
    {
        return nodeComponent;
    }

    /**
     * 设置编辑指定的节点组件
     * @param nodeComponent 指定的节点组件
     */
    public void setNodeComponent(GraphNodeComponent nodeComponent)
    {
        this.nodeComponent = nodeComponent;
        setDataDisplay();
    }

    /**
     * 设置界面显示节点的原始信息
     */
    private void setDataDisplay()
    {
        //获得节点值对象
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
     * 保存所作的更新
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
                    JOptionPane.showMessageDialog(null, "生效时间不能超过失效时间", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

        isSave = true;
        updatedNodeComponent();
        this.setVisible(false);
    }

    /**
     * 放弃对节点的更新
     * @param e java.awt.event.ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        isSave = false;
        this.setVisible(false);
    }

    /**
     * 获得更新后的节点组件
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
            //根据路线类型更新节点图标
            nodeComponent.getGraphNode().updateImage();
            nodeComponent.updateImage();
        }
    }

    /**
     * 寻求帮助.连接到Web页"编辑路线节点"
     * @param e java.awt.event.ActionEvent
     */
    void helpJButton_actionPerformed(ActionEvent e)
    {
        showHelp();
    }

    /**
     * 显示帮助页
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
     * 初始化帮助
     */
    private void initializeHelp()
    {
        try
        {
            //定义帮助文件所在服务器的主机及端口
            //URL url = new URL("http://pdmlm:80/");
            URL url = null;
            //构造帮助系统。
            /*
             * 参数 helptest 表帮助的子类别（用户界面主要操作内容） url 帮助文件所在服务器的主机及端口 OnlineHelp 浏览器窗口名 ResourceBundle 帮助资源
             */
            helpSystem = new QMHelpSystem("capproute", url, "OnlineHelp", ResourceBundle.getBundle("com.faw_qm.cappclients.conscapproute.util.RouteHelpRB", RemoteProperty.getVersionLocale()));
            //System.out.println(helpSystem.getResources());
            //定义帮助内容ID，（用户界面的工作模式，如“Update”、“Create”、“View”）
            String s = "EditRouteNode";
            //构造帮助上下文环境
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
