/**
 * CR1 20120401 吕航 原因参见TD6010
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.faw_qm.cappclients.capp.util.CappClientUtil;
import com.faw_qm.cappclients.conscapproute.util.ParentWrapData;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMExceptionHandler;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;

/**
 * <p>Title: </p> <p>查看父件对话框: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: </p>
 * @author not attributable
 * @version 1.0 20120106 徐春英 add
 */
public class ViewParentJDialog extends JDialog implements ActionListener
{
    private QMPartMasterIfc part;
    //private String listID;
    JPanel panel1 = new JPanel();
    JLabel tipLabel = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    QMMultiList multiList = new QMMultiList();
    JButton quitButton = new JButton();
    GridBagLayout gridBagLayout1 = new GridBagLayout();

    private JButton okJButton = new JButton();
    private JPanel buttonJPanel = new JPanel();
    private JLabel statusLabel = new JLabel();
    //判断是否点击确定按钮
    public static boolean isSave = false;
    //选择的零件
    private QMPartIfc theSelectedParentPart;
    //获得路线的模式
    private static final String routeMode = RemoteProperty.getProperty("routeManagerMode", "partRelative");
    private JFrame parentFrame;
    //存放父件的缓存
    private HashMap parentMap = new HashMap();
    //产品编号
    private String productNum;
    //CR1 begin
    private int mode;

    public ViewParentJDialog(JFrame frame, String productNum, String title, boolean modal,int mode)
    {
        super(frame, title, modal);
        parentFrame = frame;
        this.mode=mode;
        //CR1 end
        this.productNum = productNum;
        try
        {
            jbInit();
            pack();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public boolean setPartMaster(QMPartMasterIfc part)
    {
        boolean flag = false;
        this.part = part;
        //this.listID = listID;
        this.setTitle("零部件<" + part.getPartNumber() + "(" + part.getPartName() + ")>的上级部件和路线");
        this.tipLabel.setText("使用零部件<" + part.getPartNumber() + "(" + part.getPartName() + ")>的部件");
        flag = initMultilist();
        return flag;
    }

    //添加父件信息
    private boolean initMultilist()
    {
        Class[] params = {QMPartMasterIfc.class};
        Object[] values = {part};
        Vector result = null;
        //调用服务查找父件
        try
        {
            result = (Vector)RequestHelper.request("consTechnicsRouteService", "findParentsAndRoutes", params, values);
        }catch(Exception ex)
        {
            //输出异常信息：
            //JOptionPane.showMessageDialog(this, ex.getClientMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showInformDialog(parentFrame, message);
            return false;
        }
        if(result == null || result.size() == 0)
        {
            DialogFactory.showInformDialog(parentFrame, "此零件没有父件");
            setCursor(Cursor.getDefaultCursor());

            return false;
        }
        //将父件信息设置到列表中
        if(result != null)
        {
            int size = result.size();
            for(int i = 0;i < size;i++)
            {
                int row = this.multiList.getNumberOfRows();
                Object[] objs = (Object[])result.elementAt(i);
                QMPartInfo parent = (QMPartInfo)objs[0];
                PartUsageLinkIfc link = (PartUsageLinkIfc)objs[1];
                TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)objs[2];
                String branch = (String)objs[3];
                multiList.addTextCell(row, 0, parent.getBsoID());
                multiList.addTextCell(row, 1, "—1");
                multiList.addTextCell(row, 2, parent.getPartNumber());
                multiList.addTextCell(row, 3, parent.getPartName());
                multiList.addTextCell(row, 4, parent.getVersionValue());
                multiList.addTextCell(row, 5, new Float(link.getQuantity()).toString());

                if(routelist != null)
                    multiList.addTextCell(row, 6, routelist.getRouteListNumber() + "(" + routelist.getRouteListName() + ")" + routelist.getVersionValue());
                else
                    multiList.addTextCell(row, 6, "");
                if(branch != null)
                    multiList.addTextCell(row, 7, branch);
                else
                    multiList.addTextCell(row, 7, "");
                //首次添加父件时，默认都未展开过
                multiList.addTextCell(row, 8, "N");
            }
        }
        return true;
    }

    private void jbInit()
    {
        panel1.setLayout(gridBagLayout1);
        tipLabel.setText("jLabel1");
        tipLabel.setFont(new Font("Dialog", 0, 14));
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusLabel.setText("欢迎进入查看装配位置界面");
        quitButton.setMaximumSize(new Dimension(75, 23));
        quitButton.setMinimumSize(new Dimension(75, 23));
        quitButton.setPreferredSize(new Dimension(75, 23));
        quitButton.setToolTipText("quit(Q)");
        quitButton.setMnemonic('Q');
        quitButton.setText("退出(Q)");
        quitButton.addActionListener(new ViewParentJDialog_quiqButton_actionAdapter(this));
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                okJButton_actionPerformed(e);
            }
        });
        if(routeMode.equals("parentRelative") || routeMode.equals("productAndparentRelative"))
            buttonJPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        //CR1 begin
        //查看模式下确定按钮为不可编辑
        if(mode==2)
       {
           okJButton.setEnabled(false);
       }
        //CR1 end
        buttonJPanel.add(quitButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        getContentPane().add(panel1);
        panel1.add(tipLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(4, 8, 4, 8), 366, 0));
        panel1.add(jScrollPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 193, 71));
        panel1.add(buttonJPanel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 16, 0));
        panel1.add(statusLabel, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
        jScrollPane1.getViewport().add(multiList, null);
        //增加隐藏列用来判断是否要展开节点
        multiList.setHeadings(new String[]{"id", "级别", "编号", "名称", "版本", "数量", "路线表", "生效路线", "展开"});
        multiList.setRelColWidth(new int[]{0, 2, 2, 2, 1, 1, 4, 5, 0});
        multiList.setCellEditable(false);
        multiList.setMultipleMode(false);
        if(!routeMode.equals("parentRelative"))
            multiList.setEnabled(false);
        multiList.addActionListener(this);
        multiList.setAllowSorting(false);
        //multiList.setColumnAlignment(4, multiList.CENTER);
    }

    void quiqButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getDefaultCursor());
        this.dispose();
       
    }

    /**
     * 添加父件
     * @param e java.awt.event.ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        try
        {
            confirmOperation();
        }catch(Exception ex)
        {
            setCursor(Cursor.getDefaultCursor());
            //String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            //JOptionPane.showMessageDialog(this, ex.getClientMessage(), title, JOptionPane.INFORMATION_MESSAGE);
            String message = QMExceptionHandler.handle(ex);
            DialogFactory.showInformDialog(this, message);//xucy 20111213
        }
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 点击确定按钮执行的方法
     */
    private void confirmOperation()
    {
        //获得选择的行
        int index = multiList.getSelectedRow();
        if(index != -1)
        {
            String bsoid = multiList.getCellText(index, 0);
            String level = multiList.getCellText(index, 1);
            if("—1".equals(level))
            {
                QMPartIfc parent = (QMPartIfc)RouteClientUtil.refresh(bsoid);
                setSelectedParentPart(parent);
            }else
            {
                DialogFactory.showInformDialog(parentFrame, "所选零件不是直接父件，不能添加");
                return;
            }
        }else
        {
            DialogFactory.showInformDialog(parentFrame, "请选择该零件的父件");
            return;
        }
        setIsSave(true);
        this.setVisible(false);
    }

    public boolean getIsSave()
    {
        return isSave;
    }

    public void setIsSave(boolean arg)
    {
        isSave = arg;
    }

    /**
     * 获得选择的父件
     * @return
     */
    public QMPartIfc getSelectedParentPart()
    {
        return theSelectedParentPart;
    }

    /**
     * 设置选择的父件
     */
    public void setSelectedParentPart(QMPartIfc arg)
    {
        theSelectedParentPart = arg;
    }

    /**
     * 根据层级加"—"
     * @param level
     * @return
     */
    private String constructLine(int level)
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < level;i++)
        {
            sb.append("—");
        }
        return sb.toString();
    }

    /**
     * 实现鼠标监听方法
     */
    public void actionPerformed(ActionEvent e)
    {
        String name = e.getActionCommand();
        //如果是鼠标双击 展开或者关闭零件父件信息
        if(name.startsWith("RowSelected:"))
        {
            int index = multiList.getSelectedRow();
            if(index != -1)
            {
                String partID = this.multiList.getCellText(index, 0);
                String aa = multiList.getCellText(index, 1);
                if(aa.indexOf("—") >= 0)
                {
                    aa = aa.substring(aa.lastIndexOf("—") + 1);
                }
                String partNum = multiList.getCellText(index, 2);
                String expand = multiList.getCellText(index, 8);
                QMPartIfc part = (QMPartIfc)RouteClientUtil.refresh(partID);
                //statusLabel.setForeground(Color.black);
                //如果选择的行未展开，则执行展开操作
                if("N".equals(expand))
                {
                    //展开操作时，存储所选行之下的数据
                    Vector dataVec = new Vector();
                    int level = new Integer(aa).intValue() + 1;
                    //如果路线和产品有关或者路线与产品和结构有关，并且选择展开的节点为产品节点，则不再继续找父件信息
                    if(routeMode.equals("productRelative") || routeMode.equals("productAndparentRelative"))
                    {
                        if(part.getPartNumber().equals(productNum))
                        {
                            statusLabel.setText("所选零部件为产品，不展开。");
                            return;
                        }
                    }
                    Class[] params = {QMPartMasterIfc.class};
                    Object[] values = {part.getMaster()};
                    Vector parentResult = null;
                    try
                    {
                        if(parentMap.get(partNum) != null)
                        {
                            parentResult = (Vector)parentMap.get(partNum);
                        }else
                        {
                            parentResult = (Vector)RequestHelper.request("consTechnicsRouteService", "findParentsAndRoutes", params, values);
                        }
                        statusLabel.setText("当前展开的父件为： " + part.getIdentity());
                    }catch(Exception ex)
                    {
                        //输出异常信息：
                        //JOptionPane.showMessageDialog(this, ex.getClientMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                        String message = QMExceptionHandler.handle(ex);
                        DialogFactory.showInformDialog(parentFrame, message);
                        return;
                    }
                    if(parentResult == null || parentResult.size() == 0)
                    {
                        //DialogFactory.showInformDialog(parentFrame, "此零件没有父件");
                        statusLabel.setText("零件" + part.getIdentity() + "没有父件");
                        //statusLabel.setForeground(Color.red);
                        setCursor(Cursor.getDefaultCursor());
                        return;
                    }
                    if(parentResult != null)
                    {
                        //将展开过节点的信息缓存，下一次在展开是从缓存中取数据
                        parentMap.put(partNum, parentResult);
                        int rowCount = multiList.getNumberOfRows();
                        for(int i = rowCount - 1;i > index;i--)
                        {
                            ParentWrapData parentData = getParentWrapData(i);
                            dataVec.add(parentData);
                            //将选中行之后的数据先从表格中移除
                            multiList.removeRow(i);
                        }
                        int size = parentResult.size();
                        //final List listA = new ArrayList();
                        for(int k = 0;k < size;k++)
                        {
                            int row = index + k + 1;
                            Object[] objs = (Object[])parentResult.elementAt(k);
                            QMPartInfo parent = (QMPartInfo)objs[0];
                            PartUsageLinkIfc link = (PartUsageLinkIfc)objs[1];
                            TechnicsRouteListInfo routelist = (TechnicsRouteListInfo)objs[2];
                            String branch = (String)objs[3];
                            multiList.addTextCell(row, 0, parent.getBsoID());
                            multiList.addTextCell(row, 1, constructLine(level) + level);
                            multiList.addTextCell(row, 2, parent.getPartNumber());
                            multiList.addTextCell(row, 3, parent.getPartName());
                            multiList.addTextCell(row, 4, parent.getVersionValue());
                            multiList.addTextCell(row, 5, new Float(link.getQuantity()).toString());

                            if(routelist != null)
                                multiList.addTextCell(row, 6, routelist.getRouteListNumber() + "(" + routelist.getRouteListName() + ")" + routelist.getVersionValue());
                            else
                                multiList.addTextCell(row, 6, "");
                            if(branch != null)
                                multiList.addTextCell(row, 7, branch);
                            else
                                multiList.addTextCell(row, 7, "");
                            multiList.addTextCell(row, 8, "N");
                        }
                        if(dataVec != null)
                        {
                            int size1 = dataVec.size();
                            int row1 = this.multiList.getNumberOfRows();
                            for(int m = size1 - 1;m >= 0;m--)
                            {
                                int row = row1 + size1 - 1 - m;
                                ParentWrapData parentData = (ParentWrapData)dataVec.elementAt(m);
                                multiList.addTextCell(row, 0, parentData.getParentID());
                                multiList.addTextCell(row, 1, String.valueOf(parentData.getLevel()));

                                multiList.addTextCell(row, 2, parentData.getParentNum());
                                multiList.addTextCell(row, 3, parentData.getParentName());
                                multiList.addTextCell(row, 4, parentData.getVersionValue());
                                multiList.addTextCell(row, 5, new Float(parentData.getCount()).toString());
                                multiList.addTextCell(row, 6, parentData.getRouteListNum());
                                multiList.addTextCell(row, 7, parentData.getRouteStr());
                                multiList.addTextCell(row, 8, parentData.getExpand());
                            }
                        }
                        multiList.addTextCell(index, 8, "Y");
                    }
                }
                //执行关闭操作
                else if("Y".equals(expand))
                {
                    int rowCount = multiList.getNumberOfRows();
                    String currLevel = multiList.getCellText(index, 1);
                    if(currLevel.indexOf("—") >= 0)
                    {
                        currLevel = currLevel.substring(currLevel.lastIndexOf("—") + 1);
                    }
                    //如果是最下面的行
                    if(index == rowCount)
                        return;
                    String currNextLevel = multiList.getCellText(index + 1, 1);
                    if(currNextLevel.indexOf("—") >= 0)
                    {
                        currNextLevel = currNextLevel.substring(currNextLevel.lastIndexOf("—") + 1);
                    }
                    //如果选中行和他下一行，层级相同，返回
                    if(Integer.parseInt(currNextLevel) == Integer.parseInt(currLevel))
                    {
                        return;
                    }

                    for(int i = rowCount - 1;i > index;i--)
                    {
                        ParentWrapData parentData = getParentWrapData(i);
                        String proRowLevel = parentData.getLevel();
                        if(proRowLevel.indexOf("—") >= 0)
                        {
                            proRowLevel = proRowLevel.substring(proRowLevel.lastIndexOf("—") + 1);
                        }
                        //如果要处理的行的层级大于选中行的层级，则将其从表格中移除
                        if(Integer.parseInt(proRowLevel) > Integer.parseInt(currLevel))
                            //将选中行之后的数据先从表格中移除
                            multiList.removeRow(i);
                    }
                    multiList.addTextCell(index, 8, "N");
                    statusLabel.setText("当前合并的父件为： " + part.getIdentity());
                }
            }
        }
    }

    /**
     * 获得封装的父件数据
     * @param row
     * @return
     */
    private ParentWrapData getParentWrapData(int row)
    {
        ParentWrapData parentData = new ParentWrapData();
        parentData.setParentID(multiList.getCellText(row, 0));
        parentData.setLevel(multiList.getCellText(row, 1));
        parentData.setParentNum(multiList.getCellText(row, 2));
        parentData.setParentName(multiList.getCellText(row, 3));
        parentData.setVersionValue(multiList.getCellText(row, 4));
        String num = multiList.getCellText(row, 5);
        float count = Float.valueOf(num).floatValue();
        parentData.setCount(count);
        parentData.setRouteListNum(multiList.getCellText(row, 6));
        parentData.setRouteStr(multiList.getCellText(row, 7));
        parentData.setExpand(multiList.getCellText(row, 8));
        return parentData;
    }

}

class ViewParentJDialog_quiqButton_actionAdapter implements java.awt.event.ActionListener
{
    ViewParentJDialog adaptee;

    ViewParentJDialog_quiqButton_actionAdapter(ViewParentJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.quiqButton_actionPerformed(e);
    }
}
