/** 生成程序 EquipmentToolMaintainJDialog.java    1.0    2003/08/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/07/13  刘玉柱   参见DefactID=2556
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.QMCt;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.resource.exception.ResourceException;
import com.faw_qm.resource.support.client.model.CEquipment;
import com.faw_qm.resource.support.model.QMEquipmentIfc;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMToolInfo;


/**
 * <p>Title:维护设备与工装关联的界面类</p>
 * <p>将设备与工装的关联保存到设备库中。设备列表和工装列表中至少有一个列表中是一条</p>
 * <p>数据（设备或工装）。如果设备、工装都选择了多条数据，选择入库操作时，给出提示，要求</p>
 * <p>重新选择。</p>
 * <p>如果设备列表中的设备是一个，则系统将该设备与工装列表中的所有工装建立关联关系。</p>
 * <p>如果设备列表中的设备是多个，则必然工装列表中是一个，则系统分别将每个设备与该工装建立关联关系。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */

public class EquipmentToolMaintainJDialog extends JDialog
{
    private JPanel mainJPanel = new JPanel();
    private JPanel buttonJPanel = new JPanel();
    private JPanel equipmentJPanel = new JPanel();
    private JPanel toolJPanel = new JPanel();
    private TitledBorder titledBorder1;
    private TitledBorder titledBorder2;
    private JLabel statusJLabel = new JLabel();

    private JButton okJButton = new JButton();
    private JButton cancelJButton = new JButton();


    /**设备列表*/
    private ComponentMultiList equipMultiList = new ComponentMultiList();


    /**工装列表*/
    private ComponentMultiList toolMultiList = new ComponentMultiList();


    /**设备集合*/
    private Vector equipmentVector = new Vector();


    /**工装集合,用于缓存提交*/
    private Vector toolVector = new Vector();


    /**用于缓存填入列表中的设备*/
    private HashMap equipmentMap = new HashMap();


    /**用于标记资源文件路径*/
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();


    /**用于标记资源*/
    protected static ResourceBundle resource = null;


    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**
     * 构造函数
     */
    public EquipmentToolMaintainJDialog(JFrame frame)
    {
        super(frame, "", true);
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * 界面初始化
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        setModal(true);
        equipMultiList.setHeadings(new String[]
                                   {"ID", "设备编号", "设备名称", "设备型号", "平面图号", "必要"});
        equipMultiList.setRelColWidth(new int[]
                                      {0, 2, 2, 2, 2, 1});
        equipMultiList.setColsEnabled(new int[]
                                      {5}
                                      , true);
        equipMultiList.setCellEditable(false);

        toolMultiList.setHeadings(new String[]
                                  {"ID", "工装编号", "工装名称"});
        toolMultiList.setRelColWidth(new int[]
                                     {0, 2, 2});
        toolMultiList.setCellEditable(false);

        titledBorder1 = new TitledBorder(
                QMMessage.getLocalizedMessage(RESOURCE, "selectedEquipment", null));
        titledBorder2 = new TitledBorder(
                QMMessage.getLocalizedMessage(RESOURCE, "selectedTool", null));
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setMnemonic('Y');
        okJButton.setText("确定");
        okJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                okJButton_actionPerformed(e);
            }
        });
        buttonJPanel.setLayout(gridBagLayout1);
        mainJPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        mainJPanel.setLayout(gridBagLayout4);
        getContentPane().setLayout(gridBagLayout5);
        setSize(650, 500);
        setTitle(QMMessage.getLocalizedMessage(RESOURCE,
                                               "maintainToolEquipmentTitle", null));

        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });
        equipmentJPanel.setBorder(titledBorder1);
        equipmentJPanel.setLayout(gridBagLayout2);
        toolJPanel.setBorder(titledBorder2);
        toolJPanel.setLayout(gridBagLayout3);
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        buttonJPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 32, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton,
                         new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 8, 0, 0), 0, 0));
        mainJPanel.add(toolJPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 20, 10, 20), 0, 0));
        toolJPanel.add(toolMultiList,
                       new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(10, 10, 10, 10), 0, 0));
        mainJPanel.add(equipmentJPanel,
                       new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(10, 20, 5, 20), 0, 0));
        equipmentJPanel.add(equipMultiList,
                            new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(10, 10, 10, 10), 0, 0));
        getContentPane().add(buttonJPanel,
                             new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(10, 0, 10, 15), 0, 0));
        getContentPane().add(mainJPanel,
                             new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(15, 15, 0, 15), 0, 0));
        getContentPane().add(statusJLabel,
                             new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 14));
        localize();
    }


    /**
     * 界面信息本地化
     */
    private void localize()
    {
        initResources();

        try
        {
            equipMultiList.setHeadings(new String[]
                                       {"ID",
                                       QMMessage.getLocalizedMessage(RESOURCE,
                    "equipmentNumber", null),
                                       QMMessage.getLocalizedMessage(RESOURCE,
                    "equipmentName", null),
                                       QMMessage.getLocalizedMessage(RESOURCE,
                    "equipmentType", null),
                                       QMMessage.getLocalizedMessage(RESOURCE,
                    "planeNumber", null),
                                       QMMessage.getLocalizedMessage(RESOURCE,
                    "necessary", null)});
            toolMultiList.setHeadings(new String[]
                                      {"ID",
                                      QMMessage.getLocalizedMessage(RESOURCE,
                    "toolNumber", null),
                                      QMMessage.getLocalizedMessage(RESOURCE,
                    "toolName", null)});
            okJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "OkJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "CancelJButton", null));
        }
        catch (Exception ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.MISSING_RESOURCER, null);
            JOptionPane.showMessageDialog(this, message, title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }


    /**
     *初始化所使用的资源绑定信息类
     */
    protected void initResources()
    {
        try
        {
            if (resource == null)
            {
                resource = ResourceBundle.getBundle(RESOURCE,
                        QMCt.getContext().getLocale());

            }
        }
        catch (MissingResourceException mre)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(null,
                                          CappLMRB.MISSING_RESOURCER,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }


    /**
     * 设置界面是否可见
     * @param flag boolean 界面是否可见
     */
    public void setVisible(boolean flag)
    {
        setViewLocation();
        super.setVisible(flag);
    }


    /**
     * 设置界面的显示位置
     */
    private void setViewLocation()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2,
                    (screenSize.height - frameSize.height) / 2);
    }


    /**
     * 将选择的设备显示在列表中
     * @param v 选择的设备集合
     */
    public void populateEquipments(Vector v)
    {
        equipmentVector = v;
        // 将设备关联显示在设备列表中
        equipmentsToMultiList(equipmentVector);
    }


    /**
     * 将选择的工装显示在列表中
     * @param v 选择的工装集合
     */
    public void populateTools(Vector v)
    {
        toolVector = v;
        toolsToMultiList(toolVector);
    }


    /**
     * 将设备显示在设备列表中
     * @param 设备集合
     */
    private void equipmentsToMultiList(Vector equipmentsVector)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.equipmentsToMultiList() begin...");
        }
        equipMultiList.clear();
        for (int i = 0; i < equipmentsVector.size(); i++)
        {
            QMEquipmentInfo relationEquipmentInfo
                    = (QMEquipmentInfo) equipmentsVector.elementAt(i);
            //列表属性数组："设备BsoID","设备编号","设备名称","设备型号","平面图号"
            equipMultiList.addTextCell(i, 0, relationEquipmentInfo.getBsoID());
            equipMultiList.addTextCell(i, 1, relationEquipmentInfo.getEqNum());
            equipMultiList.addTextCell(i, 2, relationEquipmentInfo.getEqName());
            equipMultiList.addTextCell(i, 3,
                                       relationEquipmentInfo.getEqType().
                                       getCodeContent());
            equipMultiList.addTextCell(i, 4, relationEquipmentInfo.getPlaneNum());
            //在列表中第5列添加选择框，且设置第5列不选中
            //CCBeginby leixiao 2010-11-26 设备与工装关联必要关联默认选中
            equipMultiList.addCheckboxCell(i, 5, true);
          //CCEndby leixiao 2010-11-26 设备与工装关联必要关联默认选中
            //把设备放入用于缓存的哈西表
            equipmentMap.put(relationEquipmentInfo.getBsoID(),
                             relationEquipmentInfo);

        } //end for

        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.equipmentsToMultiList() end...return is void");
        }
    }


    /**
     * 将工装显示在列表中
     * @param 工装集合
     */
    private void toolsToMultiList(Vector toolsVector)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.EquipmentToolMaintainJDialog.toolsToMultiList() begin...");
        }
        String as[] = new String[toolsVector.size()];
        for (int i = 0; i < toolsVector.size(); i++)
        {
            QMToolInfo relationToolInfo = (QMToolInfo) toolsVector.elementAt(i);
            //列表属性数组："工装ID","工装编号","工装名称"
            as[i] = relationToolInfo.getBsoID() + ";"
                    + relationToolInfo.getToolNum() + ";"
                    + relationToolInfo.getToolName();
        } //end for

        //把工装属性添加入列表
        toolMultiList.setListItems(as);
        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.toolsToMultiList() end...return is void");
        }
    }


    /**
     * 获得所有选择了“必要”的设备
     * @return 选择了“必要”的设备的集合
     */
    public Vector getNecessaryRelationEquipment()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.getNecessaryRelationEquipment() begin...");
        }
        Vector v = new Vector();

        for (int i = 0; i < equipmentVector.size(); i++)
        {
            //如果某设备是“必要”的
            if (equipMultiList.isCheckboxSelected(i, 5))
            {
                //获得“必要”设备的BsoID
                String id = equipMultiList.getCellText(i, 0);
                //获得“必要”设备值对象
                QMEquipmentInfo relationEquipmentInfo = (QMEquipmentInfo)
                        equipmentMap.get(id);
                v.add(relationEquipmentInfo);
            } //end if
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.getNecessaryRelationEquipment() end...return: " +
                               v);
        }
        return v;
    }


    /**
     * 获得所有未选择“必要”的设备
     * @return 选择了“必要”的设备的集合
     */
    public Vector getUnnecessaryRelationEquipment()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.getUnnecessaryRelationEquipment() begin...");
        }
        Vector v = new Vector();

        for (int i = 0; i < equipmentVector.size(); i++)
        {
            //如果某设备不是“必要”的
            if (!equipMultiList.isCheckboxSelected(i, 5))
            {
                //获得“必要”设备的BsoID
                String id = equipMultiList.getCellText(i, 0);
                //获得“必要”设备值对象
                QMEquipmentInfo relationEquipmentInfo = (QMEquipmentInfo)
                        equipmentMap.get(id);
                v.add(relationEquipmentInfo);
            } //end if
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.EquipmentToolMaintainJDialog.getUnnecessaryRelationEquipment() end...return: " +
                               v);
        }
        return v;
    }


    /**
     * 调用资源服务，将设备与工装的关联添加保存到工装资源中
     */
    private void save()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.EquipmentToolMaintainJDialog.save() begin...");
            //获得选择了“必要”的设备
        }
        Vector equipVector = getNecessaryRelationEquipment();
        QMEquipmentIfc equipmentInfo;
        for (int i = 0; i < equipVector.size(); i++)
        {
            //获得设备对象
            equipmentInfo = (QMEquipmentIfc) equipVector.
                            elementAt(i);
            try
            {
                CEquipment cequipment = new CEquipment(equipmentInfo);
                QMToolInfo toolInfo;
                //Begin CR1
                if(cequipment.getTools().size() > 0)
                {
                	cequipment.getTools().clear();
                }
                //End CR1
                //将所有工装对象加入当前设备对象中
                for (int j = 0; j < toolVector.size(); j++)
                {
                    toolInfo = (QMToolInfo) toolVector.elementAt(j);
                    cequipment.addTool(toolInfo, true);
                }
                //保存当前设备对象
                Class[] paraclass =
                        {CEquipment.class,Boolean.TYPE};
                Object[] obj =
                        {cequipment,Boolean.FALSE};
                try
                {
                    TechnicsAction.useServiceMethod("ResourceService",
                            "saveEquipment", paraclass, obj);
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(null, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    return;
                }
            }
            catch (ResourceException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(null, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        //获得未选择“必要”的设备
        Vector equipVector2 = getUnnecessaryRelationEquipment();
        for (int i = 0; i < equipVector2.size(); i++)
        {
            //获得设备对象
            equipmentInfo = (QMEquipmentIfc) equipVector2.
                            elementAt(i);
            try
            {
                CEquipment cequipment = new CEquipment(equipmentInfo);
                //将所有工装对象加入当前设备对象中
                QMToolInfo toolInfo;
                //Begin CR1
                if(cequipment.getTools().size() > 0)
                {
                	cequipment.getTools().clear();
                }
                //End CR1
                for (int j = 0; j < toolVector.size(); j++)
                {
                    toolInfo = (QMToolInfo) toolVector.elementAt(j);
                    cequipment.addTool(toolInfo, false);
                }
                //保存当前设备对象 
                Class[] paraclass =
                        {CEquipment.class,Boolean.TYPE};
                Object[] obj =
                        {cequipment,Boolean.FALSE};
                try
                {
                    TechnicsAction.useServiceMethod("ResourceService",
                            "saveEquipment", paraclass, obj);
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(null, ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                    return;
                }
            }
            catch (ResourceException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "information", null);
                JOptionPane.showMessageDialog(null, ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.EquipmentToolMaintainJDialog.save() end...return is void");
        }
    }


    /**
     * 执行确定操作
     * @param e
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        save();
        setVisible(false);
        setCursor(Cursor.getDefaultCursor());
    }


    /**
     * 执行取消操作
     * @param e
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        setVisible(false);
    }


    /**
     * 设置界面为可编辑
     */
    public void setEditMode()
    {
        equipMultiList.setColsEnabled(new int[]
                                      {5}
                                      , true);
        okJButton.setVisible(true);
        cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "CancelJButton", null));
    }


    /**
     * 设置界面查看模式
     */
    public void setViewMode()
    {
        equipMultiList.setColsEnabled(new int[]
                                      {5}
                                      , false);
        okJButton.setVisible(false);
        cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "QuitJButton", null));
    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        EquipmentToolMaintainJDialog equipmentToolMaintainJFrame = new
                EquipmentToolMaintainJDialog(null);
        equipmentToolMaintainJFrame.setVisible(true);
    }


}
