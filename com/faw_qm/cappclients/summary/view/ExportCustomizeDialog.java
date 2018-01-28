/**
 * CR1  2009/05/18  薛凯  原因：判断JList是否有选择项的逻辑不合理
 *                       方案: 当发现有选择项时直接跳出
 * CR2  2009/06/04  薛凯  TD = 2300
 * 
 * CR3  2009/06/18  薛凯  TD=2275
 * SS1 汇总定制输出项界面，设置“上移”和“下移”按钮 2013-12-23 文柳
 * SS2 汇总定制输出项界面，输出定制的已选属性，选择轴齿的定制属性 2014-2-12 文柳
 **/
package com.faw_qm.cappclients.summary.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.faw_qm.cappclients.summary.controller.SummaryController;
import com.faw_qm.cappclients.summary.controller.SummaryEQController;
import com.faw_qm.cappclients.summary.controller.SummaryExportController;
import com.faw_qm.cappclients.summary.controller.SummaryMHController;
import com.faw_qm.cappclients.summary.controller.SummaryMTController;
import com.faw_qm.cappclients.summary.controller.SummaryMainController;
import com.faw_qm.cappclients.summary.controller.SummaryMainMTController;
import com.faw_qm.cappclients.summary.controller.SummaryPDController;
import com.faw_qm.cappclients.summary.controller.SummaryToolController;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.summary.model.TotalAttrSchemaIfc;
import com.faw_qm.summary.model.TotalAttrSchemaInfo;
import com.faw_qm.summary.util.BsoMetaInfo;
import com.faw_qm.summary.util.TotalConstant;
import com.faw_qm.util.BsoDescript;
import com.faw_qm.util.JNDIUtil;
import com.faw_qm.util.PropertyDescript;


/**
 * <p>Title:输出定制对话框 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 李磊
 * @version 1.0
 * 问题(1) 2006.06.09  徐春英修改 修改原因：性能优化
 */

public class ExportCustomizeDialog extends JDialog
{
    private JPanel panel1 = new JPanel();
    private JPanel jPanel1 = new JPanel();
    private JLabel objectLabel = new JLabel();
    private JComboBox objectComboBox = new JComboBox();
    private JLabel attrLabel = new JLabel();
    private JList mayattrList = new JList();
    private JButton addButton = new JButton();
    //CCBegin SS1
    private JButton upButton = new JButton("上  移");
    private JButton downButton = new JButton("下  移");
    //CCEnd SS1
    private JButton addAllButton = new JButton();
    private JButton deleteButton = new JButton();
    private JButton clearButton = new JButton();
    private JLabel attredLabel = new JLabel();
    private JList outattrList = new JList();
    private JPanel jPanel2 = new JPanel();
    private JButton confirmButton = new JButton();
    private JButton cancelButton = new JButton();
    private PropertyDescript[] objDescript;
    //问题(1) 2006.06.09  徐春英修改
    private HashMap hashtable = new HashMap();
    private TotalAttrSchemaIfc[] attrSchema;
    private CodingIfc totalType;
    private String[] attrDisplayname;
    private BsoMetaInfo[] objs;
    private boolean isflag = false;
    public static int flag;
    private SummaryController controll;
    private static ResourceBundle res =
            ResourceBundle.getBundle(
            "com.faw_qm.cappclients.summary.util.SummaryResource",
            RemoteProperty.getVersionLocale());
    private JPanel jPanel4 = new JPanel();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JPanel jPanel5 = new JPanel();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private JPanel jPanel6 = new JPanel();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private GridBagLayout gridBagLayout6 = new GridBagLayout();
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JScrollPane jScrollPane2 = new JScrollPane();
    private SummaryExportController exportController = new
            SummaryExportController();
    private int aa;
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();

    //薛凯 添加 20080222 添加原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
    private String[] defaultOut = null;
    //薛凯 添加结束

 
    //20081128
    private JButton defaultButton = new JButton("恢复默认");
    /**
     * 构造器
     * @param frame Frame
     * @param title String
     * @param modal boolean
     */
    public ExportCustomizeDialog()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    //CCBegin SS2
    /**
     * 返回用户所属公司
     * @return Sting 用户所属公司
     * @author wenl
     */
    public String getUserFromCompany() throws QMException
    {
    	String returnStr = "";
    	RequestServer server = RequestServerFactory.getRequestServer();
         StaticMethodRequestInfo info = new StaticMethodRequestInfo();
         info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
         info.setMethodName("getUserFromCompany");
         Class[] paraClass ={};
         info.setParaClasses(paraClass);
         Object[] obj ={};
         info.setParaValues(obj);
         boolean flag = false;
         try
         {
        	 returnStr = ((String) server.request(info));
         }
         catch (QMRemoteException e)
         {
               throw new QMException(e);
         }
         return returnStr;
    }
    /**
     * 轴齿中心设置输出默认属性
     * @author wenl
     */ 
    private void setDefaultOutputAttr()
    {
    	String[] output = null;
    	try
    	{
    		Class[] theClass ={CodingIfc.class, Integer.TYPE};
    		Object[] myObj  = {totalType,new Integer(11)};
              //调用TotalService服务的getDefaultOutputSchema方法
             TotalAttrSchemaIfc[] attrs = (TotalAttrSchemaIfc[]) controll.getServiceObject(
            "TotalService","getDefaultOutputSchema", theClass, myObj);
             if (attrs.length > 0)
             {
                 output = new String[attrs.length];
                 for (int i = 0; i < attrs.length; i++)
                 {
                     String s = null;
                     BsoDescript bd = JNDIUtil.getBsoDescript(attrs[i].getAttrBsoName());
                     s = bd.getFeature(TotalConstant.DISPLAYNAME).trim();
                     if (s != null)
                     {
                     	s = s + "." + attrs[i].getAttrDisplayName().trim();
                         output[i] = s.trim();
                     }
                 }
             } 
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	DefaultListModel expModel = (DefaultListModel) outattrList.getModel();
    	expModel.clear();
    	if(output!=null)
    	{
    		for(int i=0;i<output.length;i++)
    		{
    			expModel.addElement(output[i]);
    		}
    	}
    }
    //CCEnd SS2
    /**
     * 构造器
     * @param objs BsoMetaInfo[]
     * @param totalType CodingIfc
     */
    public ExportCustomizeDialog(Frame mainFrame, BsoMetaInfo[] objs,
                                 CodingIfc totalType)
    {
        super(mainFrame, "", true);
        try
        {
            this.objs = objs;
            this.totalType = totalType;
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


/**
     * 构造器
     * @param objs BsoMetaInfo[]
     * @param totalType CodingIfc
     * 薛凯 添加 20080222
     * 添加原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
     */
    public ExportCustomizeDialog(Frame mainFrame, BsoMetaInfo[] objs,
                                 CodingIfc totalType , String[] defaultOut)
    {
        super(mainFrame, "", true);
        
        try
        {
            this.objs = objs;     
            this.defaultOut = defaultOut;
            this.totalType = totalType;
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //薛凯 添加结束
    
    

    /**
     * 初始化函数
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        attrLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        objectLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        attredLabel.setMaximumSize(new Dimension(44, 22));
        attredLabel.setMinimumSize(new Dimension(44, 22));
        attredLabel.setPreferredSize(new Dimension(44, 22));
        attredLabel.setHorizontalAlignment(SwingConstants.LEFT);
        panel1.setLayout(gridBagLayout5);
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setLayout(gridBagLayout1);
        
        //20081128  薛凯  添加  添加原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
        defaultButton.setMaximumSize(new Dimension(82, 23));
        defaultButton.setMargin(new Insets(2, 14, 2, 14));
        defaultButton.setMinimumSize(new Dimension(82, 23));
        defaultButton.setPreferredSize(new Dimension(82, 23));
        defaultButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	defaultButton_actionPerformed(e);
            }
        });
        //薛凯 添加结束
        //CCBegin SS1
        if(getUserFromCompany().equals("zczx")){
            upButton.setMaximumSize(new Dimension(82, 23));
            upButton.setMargin(new Insets(2, 14, 2, 14));
            upButton.setMinimumSize(new Dimension(82, 23));
            upButton.setPreferredSize(new Dimension(82, 23));
            upButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                	upButton_actionPerformed(e);
                }
            });
            
            downButton.setMaximumSize(new Dimension(82, 23));
            downButton.setMargin(new Insets(2, 14, 2, 14));
            downButton.setMinimumSize(new Dimension(82, 23));
            downButton.setPreferredSize(new Dimension(82, 23));
            downButton.addActionListener(new java.awt.event.ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                	downButton_actionPerformed(e);
                }
            });	
        }
        //CCEnd SS1
        addButton.setBounds(new Rectangle(0, 16, 82, 23));
        addButton.setMaximumSize(new Dimension(82, 23));
        addButton.setMinimumSize(new Dimension(82, 23));
        addButton.setPreferredSize(new Dimension(82, 23));

        addButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addButton_actionPerformed(e);
            }
        });
        addAllButton.setPreferredSize(new Dimension(82, 23));
        addAllButton.setMinimumSize(new Dimension(82, 23));
        addAllButton.setBounds(new Rectangle(2, 49, 82, 23));
        addAllButton.setMaximumSize(new Dimension(82, 23));
        addAllButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addAllButton_actionPerformed(e);
            }
        });
        deleteButton.setPreferredSize(new Dimension(82, 23));
        deleteButton.setMinimumSize(new Dimension(82, 23));
        deleteButton.setBounds(new Rectangle( -1, 87, 82, 23));
        deleteButton.setMaximumSize(new Dimension(82, 23));
        deleteButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteButton_actionPerformed(e);
            }
        });
        clearButton.setPreferredSize(new Dimension(82, 23));
        clearButton.setMinimumSize(new Dimension(82, 23));
        clearButton.setBounds(new Rectangle(1, 123, 82, 23));
        clearButton.setMaximumSize(new Dimension(82, 23));
        clearButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                clearButton_actionPerformed(e);
            }
        });
        jPanel2.setLayout(gridBagLayout6);
        confirmButton.setBounds(new Rectangle(252, 19, 65, 23));
        confirmButton.setMaximumSize(new Dimension(75, 23));
        confirmButton.setMinimumSize(new Dimension(75, 23));
        confirmButton.setPreferredSize(new Dimension(75, 23));
        //confirmButton.setEnabled(false);
        //薛凯 修改 20080222 修改原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项 
        confirmButton.setEnabled(true);
        //薛凯 修改结束
        confirmButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                confirmButton_actionPerformed(e);
            }
        });
        confirmButton.setMnemonic('Y');
        cancelButton.setBounds(new Rectangle(325, 19, 65, 23));
        cancelButton.setMaximumSize(new Dimension(75, 23));
        cancelButton.setMinimumSize(new Dimension(75, 23));
        cancelButton.setPreferredSize(new Dimension(75, 23));
        cancelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
        cancelButton.setMnemonic('C');
        jPanel4.setLayout(gridBagLayout2);
        jPanel5.setLayout(gridBagLayout3);
        jPanel6.setLayout(gridBagLayout4);
        jPanel4.setBorder(null);
        jScrollPane1.setMinimumSize(new Dimension(50, 24));
        jScrollPane1.setPreferredSize(new Dimension(260, 132));
        getContentPane().add(panel1);
        jPanel2.add(cancelButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel2.add(confirmButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        panel1.add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(8, 8, 0, 8), 0, 0));
        jPanel4.add(objectLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        jPanel4.add(objectComboBox, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 5, 0), 0, 0));
        jPanel4.add(attrLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                ,
                GridBagConstraints.NORTHEAST,
                GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        jPanel4.add(jScrollPane1, new GridBagConstraints(1, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 8, 0, 0), 0, 0));
        jScrollPane1.getViewport().add(mayattrList, null);
        jPanel1.add(jPanel5, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(8, 0, 8, 8), 0, 0));
        jPanel1.add(jPanel4, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(8, 8, 8, 0), 0, 0));
        jPanel5.add(attredLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        jPanel5.add(jScrollPane2, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 0, 0, 0), 0, 0));
        jScrollPane2.getViewport().add(outattrList, null);
        jPanel1.add(jPanel6, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(0, 8, 0, 8), 0, 0));
        jPanel6.add(clearButton, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(8, 0, 0, 0), 0, 0));
        jPanel6.add(deleteButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(8, 0, 0, 0), 0, 0));
        jPanel6.add(addAllButton, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(8, 0, 0, 0), 0, 0));
        jPanel6.add(addButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
      //20081128 薛凯 修改 修改原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
        jPanel6.add(defaultButton, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(8, 0, 0, 0), 0, 0));
        //薛凯 20081128 结束
        //CCBegin SS1
        if(getUserFromCompany().equals("zczx")){
            jPanel6.add(upButton, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
                    , GridBagConstraints.CENTER,
                    GridBagConstraints.NONE,
                    new Insets(8, 0, 0, 0), 0, 0));
            jPanel6.add(downButton, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
                    , GridBagConstraints.CENTER,
                    GridBagConstraints.NONE,
                    new Insets(8, 0, 0, 0), 0, 0));	
        }
        //CCEnd SS1
        panel1.add(jPanel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST,
                GridBagConstraints.NONE,
                new Insets(8, 0, 8, 8), 0, 0));
        localize();
        initialize();
        refresh();
    }


    /**
     * 本地化
     */
    private void localize()
    {
        this.setTitle(res.getString("customize_export"));
        objectLabel.setText(res.getString("object"));
        attrLabel.setText(res.getString("attribute"));
        addButton.setText(res.getString("add"));
        addAllButton.setText(res.getString("all"));
        deleteButton.setText(res.getString("delete_right"));
        cancelButton.setText(res.getString("cancel1"));
        clearButton.setText(res.getString("clear_all"));
        attredLabel.setText(res.getString("atributed"));
        confirmButton.setText(res.getString("confirm1"));
    }


    /**
     * 对话框基于父组件居中显示
     * @param flag 是否显示
     */
    public void setVisible(boolean flag)
    {

        if (flag)
        {
            Dimension screenSize = Toolkit.getDefaultToolkit().
                                   getScreenSize();
            Dimension frameSize = this.getSize();
            if (frameSize.height > screenSize.height)
            {
                frameSize.height = screenSize.height;
            }
            if (frameSize.width > screenSize.width)
            {
                frameSize.width = screenSize.width;
            }
            this.setLocation((screenSize.width - frameSize.width) / 2,
                             (screenSize.height - frameSize.height) / 2);

        }
        super.setVisible(flag);
    }


    /**
     * 组合框和列表的初始化方法
     */
    public void initialize()
    {
        //类对象组合框模型
        DefaultComboBoxModel objectModel = new DefaultComboBoxModel();
        //向类对象组合框模型加入所有类的显示名
        //2006.09.18
        if(objs != null)
        {
            for (int i = 0; i < objs.length; i++)
            {
                objectModel.addElement(objs[i].getDisplayName());
                //将类的显示名和类名放入哈希表表中
                hashtable.put(objs[i].getDisplayName(), objs[i].getBsoName());
                objDescript = objs[i].getAttrInfo();
                if (objDescript.length != 0)
                {
                    for (int j = 0; j < objDescript.length; j++)
                    {
                        if (objs[i].getBsoName().equals("Doc"))
                        {
                            hashtable.put(objs[i].getDisplayName() +
                                          objDescript[j].getDisplayName(),
                                          objDescript[j].getPropertyName());
                        }
                        else
                        {
                            hashtable.put(objDescript[j].getDisplayName(),
                                          objDescript[j].getPropertyName());
                        }
                    }
                }
                String[] strs = exportController.getExtendAttr(objs[i].getBsoName());
                if(strs!=null)
                {
                    hashtable.putAll(exportController.getTotalAttr());
                }
            }
        }
        //设置类对象组合框模型
        objectComboBox.setMinimumSize(new Dimension(20, 22));
        objectComboBox.setModel(objectModel);
        objectComboBox.setSelectedItem(objs[0].getDisplayName());

        //给类对象组合框添加项目选取监听
        objectComboBox.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(ItemEvent event)
            {
                mayattrListChange();
            }
        });
        //可输出属性列表模型
        DefaultListModel mayExpModel = new DefaultListModel();

        //设置可输出属性列表数据模型
        mayattrList.setModel(mayExpModel);

        //向可输出属性列表模型添加属性
        objDescript = objs[0].getAttrInfo();
        for (int y = 0; y < objDescript.length; y++)
        {
            mayExpModel.addElement(objDescript[y].getDisplayName());
        }
        //begin  CR3
        mayattrList.setSelectedIndex(0);
        //end CR3
        String[] strs = exportController.getExtendAttr(objs[0].getBsoName());
        hashtable.putAll(exportController.getTotalAttr());
        if (strs != null)
        {
            for (int i = 0; i < strs.length; i++)
            {
                mayExpModel.addElement(strs[i]);
            }
        }
        //给可输出属性列表添加项目选择监听
        mayattrList.addListSelectionListener(new ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                mayPutList_valueChanged(e);
            }
        });

        //输出属性列表模型
        DefaultListModel expModel = new DefaultListModel();
        //设置输出属性列表的数据模型
        outattrList.setBorder(null);
        outattrList.setModel(expModel);
        
        //薛凯 添加 20080222 添加原因 在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
        if(this.defaultOut != null)
        {
            for(int i = 0;i<defaultOut.length;i++)
            {
                expModel.addElement(defaultOut[i]);
            }
            //begin  CR3
            outattrList.setSelectedIndex(0);
            //end CR3
        }
        //薛凯 添加结束

        mayattrList.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                mayattrList_keyPressed(e);
            }
        });
        mayattrList.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                mayattrList_mouseClicked(e);
            }
        });
        //给输出属性列表添加鼠标单击监听
        outattrList.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                outattrList_mouseClicked(e);
            }
        });
        //给可输出属性列表添加属性改变监听
        mayattrList.addPropertyChangeListener(new java.beans.
                                              PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                mayattrList_propertyChange(e);
            }
        });
    }


    /**
     * 该监听事件实现了类对象组合框中选项改变时,可输出属性列表的值也跟着变化
     */
    public void mayattrListChange()
    {
        DefaultListModel mayExpModel = (DefaultListModel) mayattrList.getModel();
        int j = objectComboBox.getSelectedIndex();
        if (j == -1)
        {
            return;
        }
        PropertyDescript[] objDescript = null;
        objDescript = objs[j].getAttrInfo();
        String[] strs = exportController.getExtendAttr(objs[j].getBsoName());
        hashtable.putAll(exportController.getTotalAttr());
        mayExpModel.clear();
        for (int x = 0; x < objDescript.length; x++)
        {
            mayExpModel.addElement(objDescript[x].getDisplayName());
        }
        if (strs != null)
        {
            for (int i = 0; i < strs.length; i++)
            {
                mayExpModel.addElement(strs[i]);
            }
        }
        //begin CR3
        mayattrList.setSelectedIndex(0);
        //end CR3
    }


    /**
     * 按界面使用规则刷新界面
     */
    public void refresh()
    {
        //获得两列表的数据模型
        DefaultListModel mayExpModel = (DefaultListModel) mayattrList.getModel();
        DefaultListModel expModel = (DefaultListModel) outattrList.getModel();
        //begin CR3
        if(mayExpModel.size()>0)
        {
        	mayattrList.setSelectedIndex(0);
        }
        if(expModel.size()>0)
        {
        	outattrList.setSelectedIndex(0);
        }
        //end CR3
        /**可输出属性列表中有可选项时,全部添加按钮有效，否则无效
         *可输出属性列表中无可选项时,添加按钮无效
         */
        if (mayExpModel.isEmpty() == true)
        {
            addAllButton.setEnabled(false);
            addButton.setEnabled(false);
        }
        else
        {
            addButton.setEnabled(true);
            addAllButton.setEnabled(true);
        }
        /**可输出属性列表中有选项被选中时,添加按钮有效，否则无效*/
        
        for (int i = 0; i < mayExpModel.size(); i++)
        {
            if
                    (mayattrList.isSelectedIndex(i) == true)
            {
                addButton.setEnabled(true);
                //begin CR1
                break;
                //end CR1
            }
            else
            {
                addButton.setEnabled(false);
            }
        }
        /**输出属性列表中有选项被选中时,删除按钮和全部删除按钮有效，否则无效*/
        //begin CR3
//        for (int i = 0; i < expModel.size(); i++)
//        {
        //end CR3
            if (expModel.isEmpty() != true)
            {
                deleteButton.setEnabled(true);
                clearButton.setEnabled(true);
                //CCBegin SS1
                upButton.setEnabled(true);
                downButton.setEnabled(true);
                //CCEnd SS1
            }
            else
            {
                //CCBegin SS1
                upButton.setEnabled(false);
                downButton.setEnabled(false);
                //CCEnd SS1
                deleteButton.setEnabled(false);
                clearButton.setEnabled(false);
            }
        //begin CR3
//        }
        //end CR3

        /**输出属性列表中有选项被选中时,删除按钮有效，否则无效*/
        for (int i = 0; i < expModel.size(); i++)
        {
            if (outattrList.isSelectedIndex(i) == true)
            {
                //CCBegin SS1
                upButton.setEnabled(true);
                downButton.setEnabled(true);
                //CCEnd SS1
                deleteButton.setEnabled(true);
                //begin CR1
                break;
               //end CR1
            }
            else
            {
                deleteButton.setEnabled(false);
                //CCBegin SS1
                upButton.setEnabled(false);
                downButton.setEnabled(false);
                //CCEnd SS1
            }
        }
        
    }


    /**
     * 该监听事件实现了可输出属性列表中有选项被选中时,添加按钮有效，否则无效
     * @param e 可输出属性列表值改变事件
     */
    private void mayPutList_valueChanged(ListSelectionEvent e)
    {
        if (mayattrList.isSelectionEmpty() == true)
        {
            addButton.setEnabled(false);
        }
        else
        {
            addButton.setEnabled(true);
        }
    }


    /**
     * “全部添加”按钮的行为事件
     * @param e 事件
     */
    private void addAllButton_actionPerformed(ActionEvent e)
    {
        //获得类对象组合框模型
        DefaultComboBoxModel objectModel = (DefaultComboBoxModel)
                                           objectComboBox.
                                           getModel();
        //获得可输出属性列表数据模型
        DefaultListModel mayExpModel = (DefaultListModel) mayattrList.getModel();
        //获得输出属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outattrList.getModel();
        //把所有属性添加入输出属性列表
        for (int i = 0; i < mayExpModel.getSize(); i++)
        {
            String a = objectModel.getSelectedItem() + "." +
                       mayExpModel.getElementAt(i);
            if (expModel.contains(a))
            {
                expModel.removeElement(a);
            }
            expModel.addElement(a);
        }
        confirmButton.setEnabled(true);
        //刷新界面
        refresh();
    }


    //20081128 薛凯 添加 添加原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
    private void defaultButton_actionPerformed(ActionEvent e)
    {
    	String[] output = null;
    	try
    	{
    		Class[] theClass ={CodingIfc.class, Integer.TYPE};
    		//CCBegin SS2
    		Object[] myObj  = new Object[2];
    		if(getUserFromCompany().equals("zczx")){
    			 myObj[0] = totalType;
    			 myObj[1] = new Integer(11);
    		}else{
	   			 myObj[0] = totalType;
				 myObj[1] = new Integer(TotalConstant.ISGENERAL);
    		}
             //CCEnd SS2
              //调用TotalService服务的getDefaultOutputSchema方法
             TotalAttrSchemaIfc[] attrs = (TotalAttrSchemaIfc[]) controll.getServiceObject(
            "TotalService","getDefaultOutputSchema", theClass, myObj);
             if (attrs.length > 0)
             {
                 output = new String[attrs.length];
                 for (int i = 0; i < attrs.length; i++)
                 {
                     String s = null;
                     BsoDescript bd = JNDIUtil.getBsoDescript(attrs[i].getAttrBsoName());
                     s = bd.getFeature(TotalConstant.DISPLAYNAME).trim();
                     if (s != null)
                     {
                     	s = s + "." + attrs[i].getAttrDisplayName().trim();
                         output[i] = s.trim();
                     }
                 }
             } 
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    	}
    	DefaultListModel expModel = (DefaultListModel) outattrList.getModel();
    	expModel.clear();
    	if(output!=null)
    	{
    		for(int i=0;i<output.length;i++)
    		{
    			expModel.addElement(output[i]);
    		}
    	}
    }
    //薛凯 添加结束 20081128
    
    
    /**
     * 添加按钮的行为事件
     * @param e 事件
     */
    private void addButton_actionPerformed(ActionEvent e)
    {
        //获得类对象组合框模型
        DefaultComboBoxModel objectModel = (DefaultComboBoxModel)
                                           objectComboBox.
                                           getModel();
        //获得可输出属性列表中所选中的所有项的索引(因为允许多选)
        int[] selected = mayattrList.getSelectedIndices();
        //获得可输出属性列表数据模型
        DefaultListModel mayExpModel = (DefaultListModel) mayattrList.getModel();
        //获得输出属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outattrList.getModel();
        String a = "";
        //向输出属性列表添加所选中的属性
        for (int i = 0; i < selected.length; i++)
        {
            a = mayExpModel.getElementAt(selected[i]).toString();
            if (expModel.contains(objectModel.getSelectedItem() + "." + a))
            {
                return;
            }
            expModel.addElement(objectModel.getSelectedItem() + "." + a);
        }
        if (!expModel.isEmpty())
        {
            confirmButton.setEnabled(true);
            //刷新界面
        }
        refresh();
    }


    /**
     * 删除按钮的行为事件
     * @param e 事件
     */
    private void deleteButton_actionPerformed(ActionEvent e)
    {
        //获得输出属性列表中所选中的所有项的索引(因为允许多选)
        int[] selected = outattrList.getSelectedIndices();
        //获得输出属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outattrList.getModel();
        //向可输出属性列表添加所选中的属性，同时将该属性从输出属性列表中删除
        for (int i = 0; i < selected.length; i++)
        {
            expModel.removeElementAt(selected[i] - i);
        }
        if (expModel.isEmpty())
        {
            confirmButton.setEnabled(false);
        }
        refresh();
    }

  //CCBegin SS1
    /**
     * 上移按钮的行为事件
     * @param e 事件
     */
    private void upButton_actionPerformed(ActionEvent e)
    {
        //获得输出属性列表中所选中的所有项的索引(因为允许多选)
        int[] selected = outattrList.getSelectedIndices();
        //判断不能选择间断是属性上移
        if(selected.length>1){
        	JOptionPane.showMessageDialog(this.getParent(),"只能单选上移属性！");
		    setCursor(Cursor.getDefaultCursor());
		    return ;
        }
        
        //获得输出属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outattrList.getModel();
        Object[] modelList = expModel.toArray();
        //可输出属性列表上移属性
        if(modelList.length>1&&selected[0]>0){
    		for(int j = 0; j < selected.length; j++){
    			expModel.remove(selected[j]-1);
    			expModel.add(selected[j]-1, modelList[selected[j]]);
    			if(j==0){
    				expModel.remove(selected[j]+selected.length-1);
    				expModel.add(selected[j]+selected.length-1, modelList[selected[0]-1]);
    			}
    		}
        	
        }else{
        	return;
        }
        //刷新选择
        refresh();
        //选择上移完成的属性
        int[] afterUpselected = new int[selected.length];
        for(int i = 0 ; i< selected.length;i++){
        	afterUpselected[i] = selected[i]-1;
        }
        outattrList.setSelectedIndices(afterUpselected);
    }
    /**
     * 下移按钮的行为事件
     * @param e 事件
     */
    private void downButton_actionPerformed(ActionEvent e)
    {
    	 //获得输出属性列表中所选中的所有项的索引(因为允许多选)
        int[] selected = outattrList.getSelectedIndices();
        //判断不能选择间断是属性上移
        if(selected.length>1){
        	JOptionPane.showMessageDialog(this.getParent(),"只能单选下移属性！");
		    setCursor(Cursor.getDefaultCursor());
		    return ;
        }
        
        //获得输出属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outattrList.getModel();
        Object[] modelList = expModel.toArray();
        
        //可输出属性列表上移属性
        if(modelList.length>1&&(selected[0]+1)!=modelList.length){
    			expModel.remove(selected[0]+1);
    			expModel.add(selected[0]+1, modelList[selected[0]]);
    			expModel.remove(selected[0]);
    			expModel.add(selected[0], modelList[selected[0]+1]);
        	
        }else{
        	return;
        }
        //刷新选择
        refresh();
        //选择上移完成的属性
        int[] afterUpselected = new int[selected.length];
        for(int i = 0 ; i< selected.length;i++){
        	afterUpselected[i] = selected[i]+1;
        }
        outattrList.setSelectedIndices(afterUpselected);
    }
//CCEnd SS1
    /**
     * 全部删除按钮的行为事件
     * @param e 事件
     */
    private void clearButton_actionPerformed(ActionEvent e)
    {
        //获得输出属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outattrList.getModel();
        //删除输出属性列表中的所有属性
        expModel.removeAllElements();
        confirmButton.setEnabled(false);
        refresh();
    }


    /**
     * 设置并返回属性约束模型值对象
     * @return TotalAttrSchemaIfc[]
     */
    public TotalAttrSchemaIfc[] getAttributes()
    {
        //获得输出属性列表数据模型
        DefaultListModel expModel = (DefaultListModel) outattrList.getModel();
        int length = expModel.getSize();
        if (attrSchema == null)
        {
            attrSchema = new TotalAttrSchemaIfc[length];
        }
        for (int i = 0; i < length; i++)
        {
            String str = expModel.getElementAt(i).toString();
            StringTokenizer st = new StringTokenizer(str, ".");
            String bsoName = null;
            String attName = null;
            if (st.hasMoreTokens())
            {
                bsoName = st.nextToken();
            }
            if (st.hasMoreTokens())
            {
                attName = st.nextToken();
            }
            attrSchema[i] = new TotalAttrSchemaInfo();
            if (bsoName.equals("文档"))
            {
                attrSchema[i].setAttrName((String) hashtable.get(bsoName +
                        attName));
            }
            else
            {
                attrSchema[i].setAttrName((String) hashtable.get(attName));
            }
            attrSchema[i].setAttrBsoName((String) hashtable.get(bsoName));
            attrSchema[i].setAttrDisplayName(attName);
        }
        return attrSchema;
    }


    /**
     * 得到属性表头的个数
     * @return int
     */
    public int getAttrCount()
    {
        return aa;
    }


    /**
     * 设置属性表头的个数
     * @param x int
     */
    public void setAttrCount(int x)
    {
        aa = x;
    }


    /**
     * 获取可输出属性的显示名
     * @return 属性显示名数组
     */
    public String[] getAttrDisplayname()
    {
        DefaultListModel expModel = (DefaultListModel) outattrList.getModel();
        String token;
        int j = 0;
        int l = 0;
        String str1 = "";
        //2006.08.10 徐春英修改
        int size = expModel.getSize();
        setAttrCount(size);
        for (int i = 0; i < size; i++)
        {
            String str2 = expModel.getElementAt(i).toString();
            str1 += str2 + ".";
        }
        StringTokenizer st = new StringTokenizer(str1, ".");
        int length = st.countTokens();
        if (attrDisplayname == null)
        {
            attrDisplayname = new String[length / 2];
        }
        while (st.hasMoreTokens())
        {
            token = st.nextToken();
            if (j % 2 == 1)
            {
                attrDisplayname[l] = token;
                l++;
            }
            j = j + 1;

        }
        return attrDisplayname;
    }


    /**
     *  设置状态
     */
    public boolean isFlag()
    {
        return isflag;
    }

    void mayattrList_keyPressed(KeyEvent e)
    {

        if (mayattrList.isSelectionEmpty() == true)
        {
            addButton.setEnabled(false);
        }
        else
        {
            addButton.setEnabled(true);
        }
    }

    void mayattrList_mouseClicked(MouseEvent e)
    {
        if (mayattrList.isSelectionEmpty() == true)
        {
            addButton.setEnabled(false);
        }
        else
        {
            addButton.setEnabled(true);
        }
    }


    /**
     * 输出属性列表监听实现
     * @param e MouseEvent
     */
    private void outattrList_mouseClicked(MouseEvent e)
    {
        //获得输出属性列表的数据模型
        DefaultListModel expModel = (DefaultListModel) outattrList.getModel();
        //如果鼠标点中的行索引不小于零
        if (outattrList.locationToIndex(e.getPoint()) >= 0)
        {
            //获得输出属性的行索引
            int index = outattrList.locationToIndex(e.getPoint());
            //根据属性索引获得属性名
            String indexString = (String) expModel.getElementAt(index);
            //如果选中，则删除按钮有效，否则失效
            if (indexString == null)
            {
                deleteButton.setEnabled(false);
            }
            else
            {
                deleteButton.setEnabled(true);
            }
        }
    }


    /**
     * 可输出属性列表属性改变监听事件方法
     * <p>只有选中列表中某一项，添加按钮才被激活</p>
     * @param e 可输出属性列表属性改变事件
     */
    private void mayattrList_propertyChange(PropertyChangeEvent e)
    {
        if (mayattrList.isSelectionEmpty() == true)
        {
            addButton.setEnabled(false);
        }
        else
        {
            addButton.setEnabled(true);
        }
    }


    /**
     * 设置控制类的状态
     * @param controll SummaryController
     */
    public void setControll(SummaryController controll)
    {
        this.controll = controll;
        //CCBegin SS2
        try {
			if(getUserFromCompany().equals("zczx")){
				setDefaultOutputAttr();//轴齿中心初始化默认属性
			}
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS2
    }


    /**
     * 确定按钮监听
     * @param event ActionEvent
     */
    private void confirmButton_actionPerformed(ActionEvent event)
    {
    	//20081128 薛凯 修改 修改原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
    	saveUserOutput();
    	//薛凯 20081128 结束
        isflag = true;
        flag = 1;
        if (totalType == SummaryMainController.sorts.get("设备汇总"))
        {
            SummaryEQController controller = (SummaryEQController) controll;
            controller.setMultiList();
            this.dispose();
        }
        else
        if (totalType == SummaryMainController.sorts.get("工序汇总"))
        {
            SummaryPDController controller = (SummaryPDController) controll;
            controller.setMultiList();
            this.dispose();
        }
        else
        if (totalType == SummaryMainController.sorts.get("工时汇总"))
        {
            SummaryMHController controller = (SummaryMHController) controll;
            controller.setMultiList();
            this.dispose();
        }
        else
        if (totalType == SummaryMainController.sorts.get("辅助材料汇总"))
        {
            SummaryMTController controller = (SummaryMTController) controll;
            controller.setMultiList();
            this.dispose();
        }
        else
        if (totalType == SummaryMainController.sorts.get("主要材料汇总"))
        {
            SummaryMainMTController controller = (SummaryMainMTController)
                                                 controll;
            controller.setMultiList();
            this.dispose();
        }
        else
        if (totalType == SummaryMainController.sorts.get("工装汇总"))
        {
            SummaryToolController controller = (SummaryToolController) controll;
            controller.setMultiList();
            this.dispose();
        }
    }

    public static void main(String[] arg)
    {
        System.setProperty("swing.useSystemFontSettings", "0");
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException ex)
        {
        }
        catch (IllegalAccessException ex)
        {
        }
        catch (InstantiationException ex)
        {
        }
        catch (ClassNotFoundException ex)
        {
        }
        ExportCustomizeDialog d = new ExportCustomizeDialog();
        d.setVisible(true);
    }
    
  //薛凯 20081128 start 添加原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
    private void saveUserOutput()
    {
    	TotalAttrSchemaIfc[] attrSchemas = getAttributes();
    	String[] userOutput = new String[attrSchemas.length];
    	for(int i=0;i<attrSchemas.length;i++)
    	{
    		//begin CR2
    		userOutput[i] = attrSchemas[i].getAttrBsoName() + "$^~" + attrSchemas[i].getAttrDisplayName()
    		   + "$^~" + attrSchemas[i].getAttrName();
    		//end CR2
    	}
    	
        try {
			controll.getServiceObject("TotalService", "saveUserCustomizeOutput", new Class[]{String[].class,CodingIfc.class}, new Object[]{userOutput,totalType});
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}     
    }
  //薛凯 20081128 end
}
