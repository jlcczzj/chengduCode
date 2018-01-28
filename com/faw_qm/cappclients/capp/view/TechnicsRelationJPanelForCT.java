package com.faw_qm.cappclients.capp.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMFawTechnicsMasterInfo;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.beans.query.CappSchema;
import com.faw_qm.cappclients.capp.controller.CheckInOutCappTaskLogic;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.wip.model.WorkableIfc;

public class TechnicsRelationJPanelForCT extends ParentJPanel{
    private ComponentMultiList multiList = new ComponentMultiList();
    private JPanel changeItemJPanel = new JPanel();
    private JButton addJButton = new JButton();
    private JButton deleteJButton = new JButton();
    private JButton upJButton = new JButton();
    private JButton downJButton = new JButton();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();


    /**标记：合并工艺规程界面新建的工艺主信息*/
    private QMFawTechnicsInfo relatedTechnics;


    /**用于缓存工艺卡对象*/
    private HashMap map = new HashMap();


    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private String technicsType;
    /**父窗口*/
    private JFrame parentJFrame;

    /**
     * 构造函数
     */
    public TechnicsRelationJPanelForCT(JFrame parentframe)
    {
        parentJFrame=parentframe;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 界面初始化
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        setLayout(gridBagLayout3);
        changeItemJPanel.setLayout(gridBagLayout1);
        addJButton.setMaximumSize(new Dimension(89, 23));
        addJButton.setMinimumSize(new Dimension(89, 23));
        addJButton.setPreferredSize(new Dimension(89, 23));

        addJButton.setMnemonic('A');
        addJButton.setText("添加...");

        deleteJButton.setMaximumSize(new Dimension(89, 23));
        deleteJButton.setMinimumSize(new Dimension(89, 23));
        deleteJButton.setPreferredSize(new Dimension(89, 23));
        deleteJButton.setMnemonic('R');
        deleteJButton.setText("删除(R)");
        deleteJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteJButton_actionPerformed(e);
            }
        });

        upJButton.setMaximumSize(new Dimension(89, 23));
        upJButton.setMinimumSize(new Dimension(89, 23));
        upJButton.setPreferredSize(new Dimension(89, 23));
        upJButton.setMnemonic('U');
        upJButton.setText("上移");
        upJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                upJButton_actionPerformed(e);
            }
        });
        downJButton.setMaximumSize(new Dimension(89, 23));
        downJButton.setMinimumSize(new Dimension(89, 23));
        downJButton.setPreferredSize(new Dimension(89, 23));
        downJButton.setMnemonic('O');
        downJButton.setText("下移");
        downJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                downJButton_actionPerformed(e);
            }
        });

        multiList.setHeadings(new String[]
                              {"工艺编号", "工艺名称", "版本"});
        //设置列宽
        multiList.setRelColWidth(new int[]
                                 {1, 1, 1});
        multiList.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                multiList_itemStateChanged(e);
            }
        });
        multiList.setCellEditable(false);

        add(multiList, new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(5, 0, 5, 0), 0, 0));
        changeItemJPanel.add(addJButton,
                             new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 0, 5), 0, 0));
        changeItemJPanel.add(deleteJButton,
                             new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 0, 5), 0, 0));

        changeItemJPanel.add(upJButton,
                             new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(5, 5, 0, 5), 0, 0));
        changeItemJPanel.add(downJButton,
                             new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(5, 5, 0, 5), 0, 0));
        add(changeItemJPanel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(5, 8, 0, 0), 0, 0));
        localize();
        addJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addJButton_actionPerformed(e);
            }
        });
    }


    /**
     * 界面信息本地化
     */
    protected void localize()
    {
        addJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "addJButton", null));
        //deleteJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
        //         "deleteJButton", null));
        upJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "upJButton", null));
        downJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "downJButton", null));
        multiList.setHeadings(new String[]
                              {
                              QMMessage.getLocalizedMessage(RESOURCE,
                "technicsNumber", null),
                              QMMessage.getLocalizedMessage(RESOURCE,
                "technicsName", null),
                              QMMessage.getLocalizedMessage(RESOURCE, "version", null)});
    }


    /**
     * 设置被关联的工艺卡
     * @param info 合并工艺规程界面新建的工艺卡
     */
    public void setRelatedTechnics(QMFawTechnicsInfo info)
    {
    	
        relatedTechnics = info;
    }


    /**
     * 设置被关联的工艺卡的工艺种类
     * @param type 被关联的工艺卡的工艺种类
     */
    public void setTechnicsType(String type)
    {
        technicsType = type;
    }


    /**
     * 获得被关联的工艺卡
     * @return 合并工艺规程界面新建的工艺卡
     */
    public QMFawTechnicsInfo getRelatedTechnics()
    {
        return relatedTechnics;
    }


    /**
     * 通过搜索工艺规程添加
     * 工艺列表中添加的工艺卡的种类必须与合并工艺规程界面新建的工艺卡的工艺种类相同，否则提示错误
     * @param e ActionEvent
     */
    void addJButton_actionPerformed(ActionEvent e)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel:addJButton_actionPerformed(e) begin...");
        }
        String SCHEMA = "C:QMFawTechnics; G:搜索条件;A:technicsNumber;A:technicsName;A:technicsType;E:technicsType;F:technicsType," +
                        technicsType + ";";
        String title = "搜索工艺规程";
        CappChooser cappQuery = new CappChooser("QMFawTechnics", title, this);
        //定义查询方案
        CappSchema mySchema = null;
        try
        {
            mySchema = new CappSchema(SCHEMA);
        }
        catch (QMRemoteException ex)
        {
            String extitle = QMMessage.getLocalizedMessage(
                    RESOURCE,
                    "exception", null);
            JOptionPane.showMessageDialog(this, ex.getLocalizedMessage(),
                                          extitle,
                                          JOptionPane.ERROR_MESSAGE);
            return;

        }

        cappQuery.setSchema(mySchema);
        cappQuery.notAcessInPersonalFolder();
        cappQuery.setLastIteration(true);
        cappQuery.addExttrPanel("QMFawTechnics", "technicsType");
        cappQuery.addListener(new CappQueryListener()
        {
            public void queryEvent(CappQueryEvent e)
            {
                qmQuery_queryEvent(e);
            }
        });

        //添加TechnicsSearchJDialog中的mutilist的行的双击监听
        cappQuery.addMultiListActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                myList_actionPerformed(e);
            }
        }
        );
        cappQuery.setVisible(true);

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel:addJButton_actionPerformed(e) end...return is void");
        }
    }

   // private TechnicsSearchJDialog d = null;


    /**
     * 搜索工艺监听事件方法
     * @param e 搜索监听事件
     */
    public void qmQuery_queryEvent(CappQueryEvent e)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRelationJPanel:qmQuery_queryEvent(e) begin...");
        }

        if (e.getType().equals(CappQueryEvent.COMMAND))
        {
            if (e.getCommand().equals(CappQuery.OkCMD))
            {
             //   d.setVisible(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                CappQuery c = (CappQuery) e.getSource();
                BaseValueIfc[] bvi = c.getSelectedDetails();
                actionPerformed(bvi);
                setCursor(Cursor.getDefaultCursor());
            }
            /* if (e.getCommand().equals(CappQuery.QuitCMD))
             {
                 d.setVisible(false);
             }*/
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel:qmQuery_queryEvent(e) end...return is void");
        }
    }

    private void myList_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        CappMultiList c = (CappMultiList) e.getSource();
        Object[] bvi = c.getSelectedObjects();
        actionPerformed(bvi);
        setCursor(Cursor.getDefaultCursor());
    }


    /**
     *把数组中的工艺规程加入列表中，此方法提供给TechnicsSearchJDialog的两个监听事件
     */
    private void actionPerformed(Object[] bvi)
    {
//        try
        {
            if (bvi != null)
            {
                //把从结果域选中的业务对象加入产品结构树中
                for (int i = 0; i < bvi.length; i++)
                {
                    QMFawTechnicsInfo info = (QMFawTechnicsInfo) bvi[i];
//                    if (isTypeSame(info))
                    {
                        if (!isContain(info))
                        {
                            if (!CheckInOutCappTaskLogic.isCheckedOut((
                                    WorkableIfc) info))
                            {
                                technicsVector.addElement(info);
                            }
                            else
                            {
                                String title= QMMessage.getLocalizedMessage(RESOURCE,"information",null);
                                Object[] para =
                                        {getIdentity(info)};

                                String message = QMMessage.getLocalizedMessage(
                                        RESOURCE,
                                        CappLMRB.
                                        CANNOT_COLLECT_CHECKED_OUT_OBJECT, para);
                               JOptionPane.showMessageDialog(parentJFrame,message,title,JOptionPane.INFORMATION_MESSAGE);

                            }
                        }
                        else
                        {
                           String title= QMMessage.getLocalizedMessage(RESOURCE,"information",null);
                            Object[] para =
                                    {getIdentity(info)};
                            String message = QMMessage.getLocalizedMessage(
                                    RESOURCE,
                                    CappLMRB.ADD_TECHNICS_REPEAT, para);
                            JOptionPane.showMessageDialog(parentJFrame,message,title,JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    /*else
                    {
                        Object[] para =
                                {getIdentity(info)};
                        String message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.NOT_SAME_TECHNICE_TYPE, para);
                        addToMultiList(technicsVector);
                        throw new QMRemoteException(message);
                    }*/
                }

                addToMultiList(technicsVector);
            }
            else
            {
                setCursor(Cursor.getDefaultCursor());
                return;
            }
        }
        /*catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "notice", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(), title,
                                          JOptionPane.WARNING_MESSAGE);
        }*/
    }

    private Vector technicsVector = new Vector();


    /**
     * 把选定的工艺卡对象添加到列表中。
     * 通过搜索工艺规程添加（或从工艺树中选取）调用本方法。
     * @param v  选定的工艺卡值对象集合
     */
    public void addToMultiList(Vector v)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRelationJPanel.addToMultiList() begin...");
        }
        String as[] = new String[v.size()];
        for (int i = 0; i < v.size(); i++)
        {
            //获得集合中的工艺卡
            QMFawTechnicsInfo info = (QMFawTechnicsInfo) v.elementAt(i);

            //列表属性数组："工艺卡ID","工艺卡编号","工艺卡名称","版本"
            as[i] = info.getTechnicsNumber() + ";"
                    + info.getTechnicsName() + ";"
                    + info.getVersionValue();
            //把所选项添加到工艺卡对象缓存表中
            map.put(info.getTechnicsNumber(), info);

        } //end for
        multiList.clear();
        //把工艺卡属性添加入列表
        multiList.setListItems(as);
        multiList.setSelectedRow(0);
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel.addToMultiList() end...return is void");
        }
    }


    /**
     * 判断所选工艺与合并工艺规程界面新建的工艺卡的工艺种类是否匹配
     * @param ifc 所选工艺
     * @return 如果工艺种类匹配，则返回true；否则返回false，并提示
     */
    private boolean isTypeSame(QMTechnicsIfc ifc)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRelationJPanel.isTypeSame() begin...");
        }
        if (technicsType == null)
        {
            /*String title = QMMessage.getLocalizedMessage(RESOURCE,"information",null);
                     String message = QMMessage.getLocalizedMessage(
                RESOURCE,CappLMRB.NOT_TECHNICSTYPE_ENTERED,null);
             JOptionPane.showMessageDialog(getParentJFrame(),message,title,
                                          JOptionPane.INFORMATION_MESSAGE);
             */
            return false;
        }
        //所选工艺的工艺种类
        String type1 = ifc.getTechnicsType().getCodeContent();

        if (type1.equals(technicsType))
        {
            if (verbose)
            {
                System.out.println(
                        "cappclients.capp.view.TechnicsRelationJPanel.isTypeSame() end...return: true");
            }
            return true;
        }
        else
        {

            if (verbose)
            {
                System.out.println(
                        "cappclients.capp.view.TechnicsRelationJPanel.isTypeSame() end...return: false");
            }
            return false;
        }
    }


    /**
     * 删除列表中的工艺卡对象
     * @param e ActionEvent
     */
    void deleteJButton_actionPerformed(ActionEvent e)
    {
        int selected_row = multiList.getSelectedRow();
        QMTechnicsInfo info = (QMTechnicsInfo) multiList.getSelectedObject();
        if (selected_row != -1)
        {
            //获得所选项的工艺卡ID
            String id = multiList.getCellText(selected_row, 0);
            //把所选项从列表中删除
            multiList.removeRow(selected_row);
            //把所选项从工艺卡对象缓存表中删除
            map.remove(id);
            technicsVector.remove(selected_row);
            refreshButtonState();
        }
    }


    /**
     * 列表的项目改变监听事件方法
     * @param e ItemEvent
     */
    void multiList_itemStateChanged(ItemEvent e)
    {
        refreshButtonState();
    }


    /**
     * 刷新按钮的显示状态
     * <p>在列表中选中一个工艺卡时，移去按钮有效；否则失效。</p>
     */
    private void refreshButtonState()
    {
        int row_number = multiList.getSelectedRow();
        if (row_number != -1)
        {
            deleteJButton.setEnabled(true);
        }
        else
        {
            deleteJButton.setEnabled(false);
        }
    }


    /**
     * 把所选项向上移动一行
     * @param e ActionEvent
     */
    void upJButton_actionPerformed(ActionEvent e)
    {
        //获得列表中所选中的所有项的索引(因为允许多选)
        int[] selected = multiList.getSelectedRows();

        for (int i = 0; i < selected.length; i++)
        {
            if (selected[i] > 0)
            {
                //将所选项上移一个单位
                multiList.getTableModel().moveRow(selected[i], selected[i],
                                                  selected[i] - 1);
                /**将鼠标选择位置上移一个单位*/
                multiList.setSelectedRow(selected[i] - 1);
            }
        }
    }


    /**
     * 把所选项向下移动一行
     * @param e ActionEvent
     */
    void downJButton_actionPerformed(ActionEvent e)
    {
        //获得输出属性列表中所选中的所有项的索引(因为允许多选)
        int[] selected = multiList.getSelectedRows();

        for (int i = 0; i < selected.length; i++)
        {
            if (selected[i] + 1 <= multiList.totalObjects() - 1)
            {
                //将所选项下移一个单位
                multiList.getTableModel().moveRow(selected[i], selected[i],
                                                  selected[i] + 1);
                /**将鼠标选择位置下移一个单位*/
                multiList.setSelectedRow(selected[i] + 1);
            }
        }
    }


    /**
     * 获得列表中最终存在的工艺卡对象
     * @return 工艺卡对象集合
     */
    public Collection commitAddedTechnics()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRelationJPanel.commitAddedTechnics() begin...");
        }
        Collection c = new ArrayList();
        int size = multiList.getNumberOfRows();
        for (int i = 0; i < size; i++)
        {
            //获得某项的工艺卡ID
            String id = multiList.getCellText(i, 0);
            QMFawTechnicsInfo info = (QMFawTechnicsInfo) map.get(id);
            c.add(info);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel.commitAddedTechnics() end...return: " +
                               c);
        }
        return c;
    }


    /**
     * 获得列表中最终存在的工艺卡对象
     * @return 工艺卡对象的BsoID的集合
     */
    public Vector commitAddedTechnicsBsoID()
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel.commitAddedTechnicsBsoID() begin...");
        }
        Vector v = new Vector();
        Set set = map.keySet();
        Iterator keys = set.iterator();
        while (keys.hasNext())
        {
            QMTechnicsInfo tech = (QMTechnicsInfo) map.get(keys.next());
            v.add(tech.getBsoID());
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel.commitAddedTechnicsBsoID() end...return: " +
                               v);
        }
        return v;
    }

    private boolean isContain(QMTechnicsInfo info)
    {
        boolean flag = false;
        for (int i = 0; i < technicsVector.size(); i++)
        {
            QMTechnicsInfo thisInfo = (QMTechnicsInfo) technicsVector.elementAt(
                    i);
            String a = thisInfo.getBsoID();
            String b = info.getBsoID();
            if (a.equals(b))
            {
                flag = true;
                break;
            }
        }
        return flag;
    }


    /**
     * 给定工艺主信息，获得工艺卡的所有版本中的最新小版本
     * @param masterInfo 工艺主信息
     * @return 工艺卡的所有版本中的最新小版本
     */
    private QMFawTechnicsInfo getLastedVersion(QMFawTechnicsMasterInfo
                                               masterInfo)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRelationJPanel:getLastedIterations() begin...");
        }
        QMFawTechnicsInfo technicsInfo = null;
        //调用服务方法，获得工艺卡的所有小版本（包括不同分枝）
        ServiceRequestInfo info1 = new ServiceRequestInfo();
        Class[] paraClass =
                {MasteredIfc.class};
        Object[] objs =
                {masterInfo};
        Collection collection = null;
        try
        {
            collection = (Collection) TechnicsAction.useServiceMethod(
                    "VersionControlService", "allIterationsOf", paraClass, objs);
        }
        catch (QMRemoteException ex)
        {
            if(verbose)
            ex.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        Iterator iterator = collection.iterator();
        if (iterator.hasNext())
        {
            //获得工艺卡的最新小版本
            technicsInfo = (QMFawTechnicsInfo) iterator.next();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel:getLastedIterations() end...return " +
                               technicsInfo);
        }
        return technicsInfo;
    }


    /**
     * 把指定的工艺卡添加到列表中
     * @param info 指定的工艺卡
     */
    public void addTechReguToTable(QMTechnicsInfo info)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsRelationJPanel:addTechReguToTable() begin...");
        }
        try
        {
            if (isTypeSame(info))
            {
                if (!isContain(info))
                {
                    if (!CheckInOutCappTaskLogic.isCheckedOut((WorkableIfc)
                            info))
                    {
                        technicsVector.addElement(info);
                    }
                    else
                    {
                        Object[] para =
                                {getIdentity(info)};
                        String message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.CANNOT_COLLECT_CHECKED_OUT_OBJECT,
                                para);
                        throw new QMRemoteException(message);
                    }
                }
                else
                {
                    Object[] para =
                            {getIdentity(info)};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.ADD_TECHNICS_REPEAT, para);
                    throw new QMRemoteException(message);
                }
            }
            else
            {
                Object[] para =
                        {getIdentity(info)};
                String message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NOT_SAME_TECHNICE_TYPE, para);
                throw new QMRemoteException(message);
            }
            addToMultiList(technicsVector);
        }
        catch (QMRemoteException e)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          e.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsRelationJPanel:addTechReguToTable() end...return is void");
        }
    }


    /**
     * 清除界面信息
     */
    public void clear()
    {
        multiList.clear();
        map.clear();
        relatedTechnics = null;
        technicsVector.clear();
    }




}
