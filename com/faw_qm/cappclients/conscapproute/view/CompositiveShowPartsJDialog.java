/**
 * CR1 吕航 原因 参见TD6005
 */
package com.faw_qm.cappclients.conscapproute.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.faw_qm.cappclients.resource.view.CheckboxMultiList;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestHelper;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.technics.consroute.model.ModelRouteInfo;
import com.faw_qm.technics.consroute.util.RouteWrapData;

public class CompositiveShowPartsJDialog extends JDialog
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private ComponentMultiList qMMultiList = new ComponentMultiList();

    private JPanel jPanel1 = new JPanel();
    private JPanel jPanel2 = new JPanel();
    private JButton okJButton = new JButton();

    private JButton cancelJButton = new JButton();
    private JButton searchJButton = new JButton();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();

    // private JLabel statusJLabel = new JLabel();

    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private HashMap map = new HashMap();

    //    private int[] index;

    /**
     * @roseuid 4031A77200CE
     */
    public CompositiveShowPartsJDialog()
    {
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 构造函数
     * @param parent 父窗口
     * @roseuid 40317EEC01C1
     */
    public CompositiveShowPartsJDialog(JFrame frame)
    {
        //super(panel, "", true);
        super(frame);
        try
        {
            jbInit();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     * @throws Exception
     */
    private void jbInit()
    {
        this.setTitle("查询综合路线表");
        this.setSize(500, 400);
        this.setModal(true);
        this.getContentPane().setLayout(gridBagLayout2);
        jPanel1.setLayout(gridBagLayout1);
        jPanel2.setLayout(gridBagLayout1);
        //okJButton.setEnabled(false);
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setToolTipText("Ok");
        okJButton.setMnemonic('Y');
        okJButton.setText("确定(Y)");
        okJButton.addActionListener(new CompositiveShowPartsJDialog_okJButton_actionAdapter(this));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setToolTipText("Cancel");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消(C)");
        cancelJButton.addActionListener(new CompositiveShowPartsJDialog_cancelJButton_actionAdapter(this));
        searchJButton.setMaximumSize(new Dimension(75, 23));
        searchJButton.setMinimumSize(new Dimension(75, 23));
        searchJButton.setPreferredSize(new Dimension(75, 23));
        searchJButton.setToolTipText("Cancel");
        searchJButton.setMnemonic('C');
        searchJButton.setText("搜索(S)");
        searchJButton.addActionListener(new CompositiveShowPartsJDialog_searchJButton_actionAdapter(this));
        //        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        //        statusJLabel.setText("需要覆盖典型路线的零部件");
        // qMMultiList.addItemListener(new CompositiveShowPartsJDialog_qMMultiList_itemAdapter(this));
        jPanel1.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
        jPanel1.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(searchJButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        // this.getContentPane().add(statusJLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        this.getContentPane().add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 14, 10, 14), 0, 0));
        this.getContentPane().add(jPanel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 14, 10, 14), 0, 0));
        this.getContentPane().add(jPanel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 14, 10, 14), 0, 0));
        qMMultiList.setHeadings(new String[]{"id", "标识", "零部件编号", "零部件名称"});
        qMMultiList.setRelColWidth(new int[]{0, 1, 2, 2});
        int col[] = {1};
        qMMultiList.setColsEnabled(col, true);
        qMMultiList.setMultipleMode(false);

    }

    /**
     * 将零件显示到列表里
     * @param v
     */
    private void showPart(Vector vec)
    {
        int j = qMMultiList.getRowCount();
        Vector partVec=new Vector();
        if(j + vec.size() > 10)
        {
            DialogFactory.showInformDialog(this.getParent(), "最多只能添加10条，请重新选择！");
        }else
        {
            for(int i = 0;i < vec.size();i++)
            {
                QMPartMasterInfo  part = (QMPartMasterInfo)vec.elementAt(i);
                if(map.get(part.getBsoID()) == null)
                {
                    partVec.addElement(part);
                    map.put(part.getBsoID(), part);
                }
            }
            for(int k=0;k<partVec.size();k++)
            {
                QMPartMasterInfo  part1 = (QMPartMasterInfo)partVec.elementAt(k);
                Image image = this.getStandardIcon(part1);
                qMMultiList.addTextCell(k + j, 0, part1.getBsoID());
                qMMultiList.setCheckboxSelected(k + j, 1, true);
                qMMultiList.addCell(k + j, 2, part1.getPartNumber(), image);
                qMMultiList.addTextCell(k + j, 3, part1.getPartName());
            }
        }
       //CR1 
        this.setVisible(true);
    }

    /**
     * 获得图标
     * @param basevalueinfo
     * @return
     */
    public Image getStandardIcon(BaseValueIfc basevalueinfo)
    {
        Image image = null;
        if(basevalueinfo != null)
        {
            try
            {
                image = QM.getStandardIcon(basevalueinfo.getBsoName());
            }catch(Exception _ex)
            {
                image = null;
            }
        }
        return image;
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
        CompositiveShowPartsJDialog d = new CompositiveShowPartsJDialog();
        d.setVisible(true);
    }

    /**
     * 执行确定操作
     * @param e ActionEvent
     */
    void okJButton_actionPerformed(ActionEvent e)
    {
        int index = qMMultiList.getRowCount();
        StringBuffer partMasterID = new StringBuffer();
        HashMap map = new HashMap();
        int count = 0;
        for(int i = 0;i < index;i++)
        {
            if(qMMultiList.isCheckboxSelected(i, 1) == true)
            {

                if(count != 0)
                    partMasterID.append(";");
                partMasterID.append(qMMultiList.getCellText(i, 0));
                count++;
            }
        }

        if(!partMasterID.toString().equals(""))
        {
            map.put("productIDs", partMasterID.toString());
            RichToThinUtil.toWebPage("route_CompositiveRouteList.screen", map);
        }
        this.setVisible(false);
    }

    /**
     * 执行取消操作
     * @param e ActionEvent
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        this.setVisible(false);
    }

    /**
     * 执行搜索操作
     * @param e ActionEvent
     */
    void searchJButton_actionPerformed(ActionEvent e)
    {
        //CR1 
        this.setVisible(false);
        QmChooser chooser = new QmChooser("QMPartMaster", "搜索零件", this.getParent());
        chooser.setChildQuery(false);
        chooser.setRelColWidth(new int[]{1, 1});
        chooser.addListener(new QMQueryListener()
        {
            public void queryEvent(QMQueryEvent e)
            {
                if(e.getType().equals(QMQueryEvent.COMMAND))
                {
                    if(e.getCommand().equals(QmQuery.OkCMD))
                    {
                        Vector vec = new Vector();
                        QmChooser qmChooser = (QmChooser)e.getSource();
                        //获得选择的零件数组。
                        BaseValueIfc[] objs = (BaseValueIfc[])qmChooser.getSelectedDetails();
                        for(int i = 0, j = objs.length;i < j;i++)
                        {
                            QMPartMasterIfc part = (QMPartMasterIfc)objs[i];
                            vec.addElement(part);
                        }
                        showPart(vec);
                    }
                }
            }
        });
        chooser.setVisible(true);
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
     * @param flag
     */
    public void setVisible(boolean flag)
    {
        this.setViewLocation();
        super.setVisible(flag);
    }

    //    /**
    //     * 当选中零部件时，确定按钮有效
    //     * @param e ItemEvent
    //     */
    //    void qMMultiList_itemStateChanged(ItemEvent e)
    //    {
    //        Object[] objs = qMMultiList.getSelectedObjects();
    //        if(objs != null && objs.length > 0)
    //        {
    //            okJButton.setEnabled(true);
    //        }
    //    }

}

/**
 * <p>Title:确定按钮适配器</p> <p>Description: </p>
 */
class CompositiveShowPartsJDialog_okJButton_actionAdapter implements java.awt.event.ActionListener
{
    private CompositiveShowPartsJDialog adaptee;

    CompositiveShowPartsJDialog_okJButton_actionAdapter(CompositiveShowPartsJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.okJButton_actionPerformed(e);
    }
}

/**
 * <p>Title:取消按钮适配器</p> <p>Description: </p>
 */
class CompositiveShowPartsJDialog_cancelJButton_actionAdapter implements java.awt.event.ActionListener
{
    private CompositiveShowPartsJDialog adaptee;

    CompositiveShowPartsJDialog_cancelJButton_actionAdapter(CompositiveShowPartsJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.cancelJButton_actionPerformed(e);
    }
}

/**
 * <p>Title:搜索按钮适配器</p> <p>Description: </p>
 */
class CompositiveShowPartsJDialog_searchJButton_actionAdapter implements java.awt.event.ActionListener
{
    private CompositiveShowPartsJDialog adaptee;

    CompositiveShowPartsJDialog_searchJButton_actionAdapter(CompositiveShowPartsJDialog adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.searchJButton_actionPerformed(e);
    }
}
///**
// * <p>Title:qMMultiList的ITEM适配器</p> <p>Description: </p>
// */
//class CompositiveShowPartsJDialog_qMMultiList_itemAdapter implements java.awt.event.ItemListener
//{
//    private CompositiveShowPartsJDialog adaptee;
//
//    CompositiveShowPartsJDialog_qMMultiList_itemAdapter(CompositiveShowPartsJDialog adaptee)
//    {
//        this.adaptee = adaptee;
//    }
//
//    public void itemStateChanged(ItemEvent e)
//    {
//        adaptee.qMMultiList_itemStateChanged(e);
//    }
//}