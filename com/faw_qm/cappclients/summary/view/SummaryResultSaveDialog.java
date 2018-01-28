package com.faw_qm.cappclients.summary.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.faw_qm.cappclients.summary.controller.SummaryMainController;
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.clients.beans.lifecycle.LifeCycleInfo;
import com.faw_qm.clients.beans.lifecycle.LifeCyclePanel;
import com.faw_qm.clients.beans.lifecycle.ProjectPanel;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.summary.model.TotalResultIfc;
import com.faw_qm.summary.model.TotalSchemaIfc;
import com.faw_qm.summary.model.TotalSchemaItemIfc;
import com.faw_qm.summary.model.TotalSchemaItemInfo;
import com.faw_qm.cappclients.util.CappTextField;


/**
 * <p>Title:汇总结果保存对话框 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 李磊
 * @version 1.0
 * SS1 2013-2-20   刘家坤 增加记录模板类型
 */

public class SummaryResultSaveDialog extends JDialog
{
    private JPanel panel1 = new JPanel();
    private JPanel jPanel1 = new JPanel();
    private FolderPanel folderPanel = new FolderPanel();


    //2007.05.30 xucy 修改
    private CappTextField sumnameTextField;
    private CappTextField sumnumberTextField;
    private JTextField sumtypeTextField = new JTextField();
    private JLabel sumtypeLabel = new JLabel();
    private JLabel sumnumberLabel = new JLabel();
    private JLabel sumnameLabel = new JLabel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel jPanel2 = new JPanel();
    private JLabel jLabel4 = new JLabel();
    private JButton saveButton = new JButton();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JButton cancelButton = new JButton();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private TotalSchemaIfc schema;
    private SummaryMainController mainControl;
    private CodingIfc totalType;
    private TotalResultIfc totalResult;
    private LifeCyclePanel lifeCycle = new LifeCyclePanel();
    private ProjectPanel project = new ProjectPanel();
    private static ResourceBundle res =
            ResourceBundle.getBundle(
            "com.faw_qm.cappclients.summary.util.SummaryResource",
            RemoteProperty.getVersionLocale());
    private static String RESOURCE =
            "com.faw_qm.cappclients.summary.util.SummaryResource";
    //CCBegin SS1
    private String sumType = "";
    //CCEnd SS1

    /**
     * 构造器
     */
    public SummaryResultSaveDialog()
    {
        try
        {
            jbInit();
            localize();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 构造器
     * @param totalType CodingIfc
     * @param mainControl SummaryMainController
     */
    public SummaryResultSaveDialog(CodingIfc totalType,
                                   SummaryMainController mainControl)
    {
        super(mainControl.getMainFrame(), null, true);
        try
        {
            this.totalType = totalType;
            this.mainControl = mainControl;
            jbInit();
            localize();
            setSumType();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    //CCBegin SS1
    /**
     * 构造器
     * @param totalType CodingIfc
     * @param mainControl SummaryMainController
     */
    public SummaryResultSaveDialog(CodingIfc totalType,
                                   SummaryMainController mainControl, String sumType)
    {
        super(mainControl.getMainFrame(), null, true);
        try
        {
            this.totalType = totalType;
            this.mainControl = mainControl;
            this.sumType = sumType;
            jbInit();
            localize();
            setSumType();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //CCEnd SS1
    /**
     * 初始化函数
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        panel1.setLayout(gridBagLayout3);
        jPanel1.setLayout(gridBagLayout1);
        lifeCycle.setMode(LifeCycleInfo.CREATE_MODE);
        lifeCycle.setBsoName("TotalSchemaItem");
        project.setMode(ProjectPanel.CREATE_MODE);
        jPanel2.setLayout(gridBagLayout2);
        saveButton.setMaximumSize(new Dimension(97, 23));
        saveButton.setMinimumSize(new Dimension(97, 23));
        saveButton.setPreferredSize(new Dimension(97, 23));
        cancelButton.setMaximumSize(new Dimension(97, 23));
        cancelButton.setMinimumSize(new Dimension(97, 23));
        cancelButton.setPreferredSize(new Dimension(97, 23));
        cancelButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        cancelButton.setMnemonic('C');
        //2007.05.30 xucy 添加
        sumnumberTextField = new CappTextField(this, "汇总编号", 20, false);
        sumnameTextField = new CappTextField(this, "汇总名称", 40, false);
        getContentPane().add(panel1, BorderLayout.CENTER);
        jPanel1.add(sumnumberLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(10, 11, 0, 0), 0, 0));
        jPanel1.add(sumnumberTextField,
                    new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.BOTH,
                                           new Insets(10, 8, 0, 10), 0, 0));
        jPanel1.add(sumnameLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(10, 11, 0, 0), 0, 0));
        jPanel1.add(sumnameTextField,
                    new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(10, 8, 0, 10), 0, 0));
        jPanel1.add(sumtypeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(9, 5, 2, 0), 0, 0));
        jPanel1.add(sumtypeTextField,
                    new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(10, 8, 0, 10), 0, 0));
        jPanel1.add(folderPanel, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0
                , GridBagConstraints.SOUTH, GridBagConstraints.HORIZONTAL,
                new Insets(10, 22, 10, 10), 0, 0));
        jPanel1.add(project, new GridBagConstraints(0, 5, 2, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(10, 27, 0, 10), 0, 0));
        jPanel1.add(lifeCycle, new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0
                , GridBagConstraints.WEST,
                GridBagConstraints.BOTH,
                new Insets(0, 10, 0, 10), 0,
                0));
        panel1.add(jPanel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(10, 0, 20, 20), 0, 0));
        jPanel2.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel2.add(saveButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel2.add(jLabel4, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 10, 0, 10), 0, 0));
        folderPanel.setFolderJLabelName(res.getString("file"));
        //2006.09.20 徐春英修改
        folderPanel.setBrowseJButtonName(res.getString("browse1"));
        folderPanel.setButtonSize(89, 23);
        folderPanel.setViewState(true);
        folderPanel.isPersonalFolder = false;
        folderPanel.isPublicFolders = true;
        folderPanel.setPermission("modify");
        saveButton.addActionListener(mainControl);
        saveButton.setActionCommand("RESULTSAVE");
        //2006.09.20 徐春英修改
        saveButton.setMnemonic('V');
        String search = res.getString("search");
        lifeCycle.setBrowseButtonSize(new Dimension(65, 23));
        lifeCycle.setButtonName(search);
        //2006.09.20 徐春英修改
        project.setBrowseButtonSize(new Dimension(89, 23));
        project.setButtonMnemonic('S');
        project.setBrowseButtonText(search);
    }


    /**
     * 本地化
     */
    public void localize()
    {
        this.setTitle(res.getString("summary_result_save"));
        sumtypeLabel.setText(res.getString("summary_type"));
        sumnumberLabel.setText(res.getString("summary_number"));
        sumnameLabel.setText(res.getString("summary_name"));
        saveButton.setText(res.getString("save"));
        cancelButton.setText(res.getString("cancel1"));
    }


    /**
     * 设置汇总类型
     */
    public void setSumType()
    {
        if (totalType != null &&
            totalType.getBsoID().equals(((CodingIfc) SummaryMainController.
                                         sorts.
                                         get("设备汇总")).getBsoID()))
        {
            sumtypeTextField.setText(res.getString("equipment_summary"));
        }
        else
        if (totalType != null &&
            totalType.getBsoID().equals(((CodingIfc) SummaryMainController.
                                         sorts.
                                         get("工序汇总")).getBsoID()))
        {
            sumtypeTextField.setText(res.getString("prodedure_summary"));
        }
        else
        if (totalType != null &&
            totalType.getBsoID().equals(((CodingIfc) SummaryMainController.
                                         sorts.
                                         get("工时汇总")).getBsoID()))
        {
            sumtypeTextField.setText(res.getString("manhour_summary"));
        }
        else
        if (totalType != null &&
            totalType.getBsoID().equals(((CodingIfc) SummaryMainController.
                                         sorts.
                                         get("辅助材料汇总")).getBsoID()))
        {
            sumtypeTextField.setText(res.getString("material_summary"));
        }
        else
        if (totalType != null &&
            totalType.getBsoID().equals(((CodingIfc) SummaryMainController.
                                         sorts.
                                         get("主要材料汇总")).getBsoID()))
        {
            sumtypeTextField.setText(res.getString("mainmaterial_summary"));
        }
        else
        if (totalType != null &&
            totalType.getBsoID().equals(((CodingIfc) SummaryMainController.
                                         sorts.
                                         get("工装汇总")).getBsoID()))
        {
            sumtypeTextField.setText(res.getString("tool_summary"));
        }

        sumtypeTextField.setEnabled(false);
        //sumtypeTextField.setBackground(Color.lightGray);
    }


    /**
     * 获得父窗口
     *
     * @return javax.swing.JFrame
     * @roseuid 402A11F40212
     */
    public JFrame getParentJFrame()
    {
        Component parent = getParent();
        while (!(parent instanceof JFrame))
        {
            parent = parent.getParent();
        }
        return (JFrame) parent;
    }


    /**
     * 判定条件
     * @return boolean
     */
    public boolean checkValue()
    {
        //2007.05.30 xucy 修改
        if (!sumnumberTextField.check())
        {

            return false;
        }

        if (!sumnameTextField.check())
        {
            return false;
        }

        if (folderPanel.getFolderLocation() == null)
        {
            Object[] objs =
                    {
                    res.getString("folder")};
            String s1 = QMMessage.getLocalizedMessage(RESOURCE, "1", objs);
            JOptionPane.showMessageDialog(this, s1, res.getString("warning"),
                                          JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }


    /**
     * 构造汇总条件
     */
    public TotalSchemaItemIfc madeSum()
    {
        TotalSchemaItemIfc totalSchema;
        try
        {
            totalSchema = new TotalSchemaItemInfo();
            totalSchema.setTecTotalNumber(sumnumberTextField.getText().trim());
            totalSchema.setTecTotalName(sumnameTextField.getText().trim());
            //CCBegin SS1
            totalSchema.setName(sumType);
            //CCEnd SS1
            totalSchema.setSchema(schema);
            totalSchema.setLocation(folderPanel.getFolderLocation());
            totalSchema = (TotalSchemaItemIfc) mainControl.assignFolder(
                    totalSchema,
                    folderPanel.getFolderLocation());
            totalSchema = (TotalSchemaItemIfc) lifeCycle.assign(totalSchema);
            totalSchema = (TotalSchemaItemIfc) project.assign(totalSchema);
        }
        catch (QMException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getClientMessage(),
                                          res.getString("warning"),
                                          JOptionPane.WARNING_MESSAGE);
            return null;
        }
        return totalSchema;
    }


    /**
     * 构造汇总结果
     */
    public TotalResultIfc getResult()
    {
        return totalResult;
    }


    /**
     * 设置汇总结果
     */
    public void setResult(TotalResultIfc totalResult)
    {
        this.totalResult = totalResult;
    }


    /**
     * 设置汇总约束
     */
    public void setAttrSchema(TotalSchemaIfc schema)
    {
        this.schema = schema;
    }


    /**
     * 对话框基于父组件居中显示
     * @param flag 是否显示
     */
    public void setVisible(boolean flag)
    {
        setSize(400, 300);
        if (flag)
        {
            Rectangle rectangle = getParent().getBounds();
            Rectangle rectangle1 = getBounds();
            setLocation(rectangle.x + (rectangle.width - rectangle1.width) / 2,
                        rectangle.y +
                        (rectangle.height - rectangle1.height) / 2);
        }
        super.setVisible(flag);
    }

}
