/** 生成程序UpFilePanel.java	1.1  2009-10-12
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 添加文件服务器上传逻辑 jiahx 2013-12-09
 */
 
package com.faw_qm.cappclients.capp.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.StreamDataInfo;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.resource.util.ResourceHelper;
import javax.swing.JFrame;

/**
 * <p>Title: 工艺卡附件维护面板</p>
 * <p>Description: 工艺卡的创建、更新、查看、另存为操作均在本面板维护。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @version 1.0
 */
public class UpFilePanel extends JComponent implements Serializable
{
    private JButton viewButton;
    private JButton addButton;
    private JButton downLoadButton;
    private JButton deleteButton;
    private JLabel jlabel;
    private JPanel btnPanel = new JPanel();

    private int rowCount = 0;
    String path = "";
    String fileName = "";
    long length = 0;
    String newFileName = "";
    String appDataID = "";
    ArrayList arrayList = new ArrayList();

    //文件选择类  2007.01.17  徐春英注释
    //private JFileChooser jFileChooser = null;
    //private JPanel panel;
    public ComponentMultiList checkboxMultiList = new ComponentMultiList();
    ApplicationDataInfo applicationDataInfo = new ApplicationDataInfo();
    //删除的文件内容项
    private HashMap deleteContentMap = new HashMap();
    //2007.01.17
    private JFrame parentFrame = null;

    /**用于标记资源文件路径*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.resource.util.ResourceRB";

    //CCBegin SS1
    static boolean fileVaultUsed = (RemoteProperty.getProperty(
            "registryFileVaultStoreMode", "true")).equals("true");
    //CCEnd SS1

    /**
     * 构造函数
     */
    public UpFilePanel(JFrame frame)
    {
        parentFrame = frame;
        GridBagLayout gridbaglayout = new GridBagLayout();
        setLayout(gridbaglayout);
        setSize(804, 459);
//        GridBagConstraints gridbagconstraints = new GridBagConstraints();
//        gridbagconstraints.gridx = 0;
//        gridbagconstraints.gridy = 1;
//        gridbagconstraints.weightx = 1.0D;
//        gridbagconstraints.weighty = 1.0D;
//        gridbagconstraints.anchor = GridBagConstraints.CENTER;
//        gridbagconstraints.fill = GridBagConstraints.BOTH;
//        gridbagconstraints.insets = new Insets(0, 0, 0, 0);
//        gridbagconstraints.ipady = -4;

        btnPanel = new JPanel();
        gridbaglayout = new GridBagLayout();
        gridbaglayout.rowHeights = new int[] {0,7,7,7,0};
        btnPanel.setLayout(gridbaglayout);
//        btnPanel.setBounds(545, 72, 45, 104);

//        gridbagconstraints = new GridBagConstraints();
//        gridbagconstraints.gridx = 1;
//        gridbagconstraints.gridy = 1;
//        gridbagconstraints.anchor = GridBagConstraints.NORTHEAST;
//        gridbagconstraints.fill = GridBagConstraints.VERTICAL;
//        gridbagconstraints.insets = new Insets(0, 0, 0, 0);
//        ((GridBagLayout) getLayout()).setConstraints(btnPanel,
//                gridbagconstraints);
//        final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
//        gridBagConstraints_4.insets = new Insets(0, 290, 0, 275);
//        gridBagConstraints_4.ipady = 160;
//        gridBagConstraints_4.ipadx = -35;
//        gridBagConstraints_4.gridx = 0;
        final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
        gridBagConstraints_4.weighty = 1;
        gridBagConstraints_4.weightx = 1;
        gridBagConstraints_4.gridy = 1;
        gridBagConstraints_4.gridx = 1;
        gridBagConstraints_4.insets = new Insets(5, 5, 0, 5);
        gridBagConstraints_4.fill = GridBagConstraints.BOTH;
        add(btnPanel, gridBagConstraints_4);
        checkboxMultiList.setHeadings(new String[]{"文件名称", "文件路径", "文件大小（字节）", ""});
        final GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
        btnPanel.add(checkboxMultiList, new GridBagConstraints(0, 0, 1, 4, 1, 1
                ,GridBagConstraints.SOUTH, GridBagConstraints.BOTH, new Insets(0, 5, 5, 0), 415, -10));

        checkboxMultiList.setRelColWidth(new int[]
                                         {2, 0, 1, 0});

        checkboxMultiList.setColsEnabled(new int[]
                                         {0, 1, 2}
                                         , false);
        checkboxMultiList.setCellEditable(false);
        checkboxMultiList.setCheckboxEditable(true);
        checkboxMultiList.getTable().setShowHorizontalLines(true);
        checkboxMultiList.getTable().setShowVerticalLines(true);
//        ((GridBagLayout) getLayout()).setConstraints(checkboxMultiList,
//                gridbagconstraints);

        viewButton = new JButton();
        viewButton.setActionCommand("view");
        viewButton.setLabel("查看");
        viewButton.setBounds(0, 5, 89, 23);

//        gridbagconstraints = new GridBagConstraints();
//        gridbagconstraints.gridx = 0;
//        gridbagconstraints.gridy = 1;
//        gridbagconstraints.anchor = GridBagConstraints.NORTHWEST;
//        gridbagconstraints.fill = GridBagConstraints.HORIZONTAL;
//        gridbagconstraints.insets = new Insets(5, 5, 0, 5);
//        ((GridBagLayout) btnPanel.getLayout()).setConstraints(viewButton,
//                gridbagconstraints);
        btnPanel.add(viewButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));

//        gridbagconstraints = new GridBagConstraints();
//        gridbagconstraints.gridx = 0;
//        gridbagconstraints.gridy = 0;
//        gridbagconstraints.anchor = GridBagConstraints.NORTHWEST;
//        gridbagconstraints.fill = GridBagConstraints.HORIZONTAL;
//        gridbagconstraints.insets = new Insets(5, 5, 0, 5);
//        gridbagconstraints.ipady = -4;
//        gridbagconstraints = new GridBagConstraints();
//        gridbagconstraints.gridx = 0;
//        gridbagconstraints.gridy = 2;
//        gridbagconstraints.anchor = GridBagConstraints.NORTHWEST;
//        gridbagconstraints.fill = GridBagConstraints.HORIZONTAL;
//        gridbagconstraints.insets = new Insets(5, 5, 0, 5);
//        gridbagconstraints.ipady = -4;
//        gridbagconstraints = new GridBagConstraints();
//        gridbagconstraints.gridx = 0;
//        gridbagconstraints.gridy = 3;
//        gridbagconstraints.anchor = GridBagConstraints.NORTHWEST;
//        gridbagconstraints.fill = GridBagConstraints.HORIZONTAL;
//        gridbagconstraints.insets = new Insets(5, 5, 0, 5);
//        gridbagconstraints.ipady = -4;

        addButton = new JButton();
        addButton.setActionCommand("view1");
        addButton.setLabel("添加");
        addButton.setBounds(0, 5, 89, 23); //
//        ((GridBagLayout) btnPanel.getLayout()).setConstraints(
//                addButton,
//                gridbagconstraints);
        final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
        gridBagConstraints_1.ipady = 25;
        gridBagConstraints_1.gridy = 1;
        gridBagConstraints_1.gridx = 2;
        btnPanel.add(addButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
        addButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addButton_actionPerformed(e);
            }
        });

        downLoadButton = new JButton();
        downLoadButton.setActionCommand("view2");
        downLoadButton.setLabel("下载");
        downLoadButton.setBounds(0, 5, 89, 23); //
//        ((GridBagLayout) btnPanel.getLayout()).setConstraints(
//                downLoadButton,
//                gridbagconstraints);
        final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
        gridBagConstraints_2.gridy = 2;
        gridBagConstraints_2.gridx = 2;
        btnPanel.add(downLoadButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                ,GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 5, 0, 0), 0, 0));
        downLoadButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                downLoadButton_actionPerformed(e);
            }
        });

        deleteButton = new JButton();
        deleteButton.setActionCommand("view3");
        deleteButton.setLabel("删除");
        deleteButton.setBounds(0, 5, 89, 23); //
//        ((GridBagLayout) btnPanel.getLayout()).setConstraints(
//                deleteButton,
//                gridbagconstraints);
        final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
        gridBagConstraints_3.gridy = 3;
        gridBagConstraints_3.gridx = 2;
        btnPanel.add(deleteButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTHEAST, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
        deleteButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteButton_actionPerformed(e);
            }
        });
//        jlabel = new JLabel();
//        jlabel.setText(" ");
//        btnPanel.add(jlabel,
//                     new GridBagConstraints(0, 7, 1, 1, 0.0, 1.0,
//                                            GridBagConstraints.NORTHWEST,
//                                            GridBagConstraints.VERTICAL,
//                                            new Insets(5, 5, 0, 5), 0, 0));
        setSelectBMnemonic('B');
        setDownLoadBMnemonic('I');
        setOpenBMnemonic('O');
        setClearBMnemonic('R');
        /**添加按钮监听*/
        /**查看按钮监听*/
        viewButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                viewButton_actionPerformed(e);
            }
        });
        /**删除按钮监听*/
        /**下载按钮监听*/

    }


    /**
     * 执行添加按钮执行的操作
     * @param e ActionEvent
     */
    void addButton_actionPerformed(ActionEvent e)
    {
        //问题(1)2007.01.17  徐春英修改  修改原因:将JFileChooser定义成局部变量否则的话
        //文件名有缓存.
        JFileChooser jFileChooser = new JFileChooser();
        //设置默认打开的路径
        jFileChooser.setCurrentDirectory(new File(
                "C:/Documents and Settings/Administrator/My Documents"));
        jFileChooser.setApproveButtonText("打开");
        int i = jFileChooser.showOpenDialog(parentFrame);
        switch (i)
        {
            case JFileChooser.APPROVE_OPTION:
                jFileChooser_APPROVE(jFileChooser); //点打开按钮后的操作
                break;
            case JFileChooser.CANCEL_OPTION:
                jFileChooser_CANCEL(); //点取消按钮的操作
                break;
        }

    }


    /**
     * 点击查看按钮执行的操作
     * @param e ActionEvent
     */
    void viewButton_actionPerformed(ActionEvent e)
    {
        try
        {
            int i = checkboxMultiList.getSelectedRow();
            String appDataID = (String) checkboxMultiList.getCellText(i, 3);
            if (appDataID == null || appDataID.equals(""))
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "warning", null);
                String str4 = QMMessage.getLocalizedMessage(RESOURCE, "unSave", null);
                JOptionPane.showMessageDialog(parentFrame, str4, title,
                                              JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                //String action = "viewSourceFile";
                HashMap hashmap = new HashMap();
                //hashmap.put("action", action);
                hashmap.put("applicationDataID", appDataID);
                RichToThinUtil.toWebPage(
                        "DownloadManager", hashmap);
            }
        }
        catch (Exception ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "warning", null);
            String str4 = QMMessage.getLocalizedMessage(RESOURCE, "setUp", null);

            JOptionPane.showMessageDialog(parentFrame, str4, title,
                                          JOptionPane.WARNING_MESSAGE);
        }

    }


    /**
     * 点击删除按钮执行的操作
     * @param e ActionEvent
     */
    void deleteButton_actionPerformed(ActionEvent e)
    {
        int i = checkboxMultiList.getSelectedRow();
        String appDataID = (String) checkboxMultiList.getCellText(i, 3);
        if (appDataID == null || appDataID.equals(""))
        {
            checkboxMultiList.removeRow(i);
        }
        else
        {
            deleteContentMap.put(appDataID, appDataID);
            checkboxMultiList.removeRow(i);
        }
        rowCount--;
    }


    /**
     * 点击下载按钮执行的操作
     * @param e ActionEvent
     * 问题(1)2007.01.17  徐春英修改  修改原因:将JFileChooser定义成局部变量否则的话
     * 文件名有缓存.
     */
    void downLoadButton_actionPerformed(ActionEvent e)
    {
        int row = checkboxMultiList.getSelectedRow();
        appDataID = (String) checkboxMultiList.getCellText(row, 3);
        
        if (appDataID == null || appDataID.equals(""))
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "warning", null);
            String str4 = QMMessage.getLocalizedMessage(RESOURCE, "unSave", null);
            JOptionPane.showMessageDialog(parentFrame, str4, title,
                                          JOptionPane.WARNING_MESSAGE);
        }
        else
        {
            String fileName = (String) checkboxMultiList.getCellText(row, 0);
          
            File file = new File(fileName);
         
            //问题(1)2007.01.17  徐春英修改  修改原因:将JFileChooser定义成局部变量否则的话
            //文件名有缓存.
            JFileChooser  jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("保存");
            //设置默认打开的路径
            jFileChooser.setCurrentDirectory(new File(
                    "C:/Documents and Settings/Administrator/My Documents"));
            jFileChooser.setApproveButtonText("保存");
            //问题(3)2007.01.17  徐春英修改  修改原因:将附件的文件名设置到JFileChooser的文本框中
            jFileChooser.setSelectedFile(file);
            int i = jFileChooser.showOpenDialog(parentFrame);
            switch (i)
            {
                case JFileChooser.APPROVE_OPTION:
                    jFileChooser_SAVE(jFileChooser); //点保存按钮后的操作
                    break;
                case JFileChooser.CANCEL_OPTION:
                    jFileChooser_CANCEL(); //点取消按钮的操作
                    break;
            }

        }
    }


    /**
     * 下载后点击保存按钮执行的操作
     * 问题(2)2007.01.17  徐春英修改  修改原因:将局部变量JFileChooser传给jFileChooser_APPROVE
     */
    private void jFileChooser_SAVE(JFileChooser jFileChooser)
    {
        //得到选中文件
        File file = jFileChooser.getSelectedFile();
        //得到文件全路径
        path = file.getAbsolutePath();
        
        int row = checkboxMultiList.getSelectedRow();
        fileName = (String) checkboxMultiList.getCellText(row, 0);
      
        String sourceFileName = fileName;
        //问题(3)2007.01.17 徐春英修改  修改原因:下载附件时选择保存位置时，系统自动提取了文件的扩展名，
        //例如一个文件名为“123”，扩展名为“.txt”
        //将该文件上传后再下载到本地其文件名就变成了“123.txt.txt”.
        if(path.indexOf(".") != -1)
        {
            newFileName = path;
        }
        else
        if (sourceFileName.indexOf(".") != -1)
        {
            String sign = "";
            int index = sourceFileName.lastIndexOf(".");
            //截取文件后缀
            sign = sourceFileName.substring(index);
            newFileName = path + sign;
        }
        applicationDataInfo = refreshAppInfo(appDataID);
       
        //CCBegin SS1 
        //获取内容字节
        byte[] content = null;
        if(fileVaultUsed)
        {
        	ContentClientHelper helper = new ContentClientHelper();
        	try 
        	{
				content = helper.requestDownload(applicationDataInfo.getBsoID());
			} catch (QMException e) {
				e.printStackTrace();
			}
        }
        else
        {
            //获取流ID
            String streamID = applicationDataInfo.getStreamDataID();
        	content = getStreamInfo(streamID).getDataContent();
        }
        //CCEnd SS1
        try
        {

            FileOutputStream out = new FileOutputStream(new File(newFileName));
            out.write(content);
            out.close();
            JOptionPane.showMessageDialog(parentFrame, "下载文件成功!");
        }
        catch (FileNotFoundException ex1)
        {
            if (ResourceHelper.VERBOSE)
            {
                ex1.printStackTrace();
            }
        }
        catch (IOException ex1)
        {
        }
    }


    /**
     * 刷新ApplicationDataInfo值对象
     * @param bsoID String
     * @return ApplicationDataInfo
     */
    public ApplicationDataInfo refreshAppInfo(String bsoID)
    {
        try
        {
            Class[] paraClass =
                    {
                    String.class};
            Object[] obj =
                    {
                    bsoID};
            ServiceRequestInfo info = new ServiceRequestInfo();
            info.setServiceName("PersistService");
            info.setMethodName("refreshInfo");
            info.setParaClasses(paraClass);
            info.setParaValues(obj);
            RequestServer server = null;
            //通过请求服务器的工厂获得server
            server = RequestServerFactory.getRequestServer();
            ApplicationDataInfo result = (ApplicationDataInfo) server.request(
                    info);
            return result;
        }
        catch (QMRemoteException ex)
        {
            if (ResourceHelper.VERBOSE)
            {
                ex.printStackTrace();
            }
            return null;
        }

    }


    /**
     * 获得文件流
     * @param str String 流ID
     * @param file byte[] 文件内容流
     * @return boolean
     */
    public StreamDataInfo getStreamInfo(String str)
    {
        try
        {
            String className = "com.faw_qm.content.util.StreamUtil";
            String methodName = "getInfoHasContent";
            Class[] paraClass =
                    {
                    String.class};
            Object[] obj =
                    {
                    str};
            StaticMethodRequestInfo info = new StaticMethodRequestInfo();
            info.setClassName(className);
            info.setMethodName(methodName);
            info.setParaClasses(paraClass);
            info.setParaValues(obj);
            RequestServer server = null;
            //通过请求服务器的工厂获得server
            server = RequestServerFactory.getRequestServer();
            StreamDataInfo result = (StreamDataInfo) server.request(info);
            return result;
        }
        catch (QMRemoteException ex)
        {
            if (ResourceHelper.VERBOSE)
            {
                ex.printStackTrace();
            }
            return null;
        }
    }


    /**
     * (JFileChooser)弹出的打开文件界面的确定按钮操作
     * 问题(2)2007.01.17  徐春英修改  修改原因:将局部变量JFileChooser传给jFileChooser_APPROVE
     */
    private void jFileChooser_APPROVE(JFileChooser jFileChooser)
    {
        //得到选中文件
        File file = jFileChooser.getSelectedFile();
        //得到文件全路径
        path = file.getAbsolutePath();
        //CCBeginby liuzc 2009-12-19 原因：增加判断是否添加过附件，参见DefectID=2666
        boolean samefile = false;
        for(int i = 0; i<checkboxMultiList.getRowCount(); i++)
        {
        	if(path.equals(this.checkboxMultiList.getCellText(i,1)))
        	{
        		samefile = true;
        		break;
        	}
            String fileName = this.checkboxMultiList.getCellText(i,1);
        }
        if(samefile)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "warning", null);
            JOptionPane.showMessageDialog(parentFrame, "已经有相同的文件", title,
                                          JOptionPane.WARNING_MESSAGE);
            return;
        }
        //CCBeginby liuzc 2009-12-19 原因：增加判断是否添加过附件，参见DefectID=2666
        if (path.indexOf("\\") != -1)
        {
            int index = path.lastIndexOf("\\");
            //截取文件名称
            fileName = path.substring(index + 1);
        }
        length = file.length();
        checkboxMultiList.addTextCell(rowCount, 0, fileName);
        checkboxMultiList.addTextCell(rowCount, 1, path);
        checkboxMultiList.addTextCell(rowCount, 2, String.valueOf(length));
        rowCount++;
    }

    public ArrayList getArrayList()
    {
        return arrayList;
    }


    /**
     * (JFileChooser)弹出的打开文件界面的取消按钮操作
     * 为空的原因是JFileChooser控件已有该操作，不需重复写
     */
    private void jFileChooser_CANCEL()
    {

    }


    /**
     * 获得文件名称
     * @return String
     */
    public String getFileName()
    {
        return fileName;
    }


    /**
     * 获得文件路径
     * @return String
     */
    public String getPath()
    {
        return path;
    }


    /**
     * 获得文件大小
     * @return long
     */
    public long getFileLength()
    {
        return length;
    }


    /**
     * 获得checkboxMultiList
     * @return ComponentMultiList
     */
    public ComponentMultiList getMultiList()
    {
        return checkboxMultiList;
    }


    /**
     * 设置内容项
     * @param application ApplicationDataInfo
     */
    public void setApplication(ApplicationDataInfo application)
    {
        applicationDataInfo = application;
    }


    /**
     * 设置添加按钮是否可视
     * @param flag boolean
     */
    public void setAButtonVisable(boolean flag)
    {
        addButton.setVisible(flag);
    }


    /**
     * 设置查看按钮是否可视
     * @param flag boolean
     */
    public void setVButtonVisable(boolean flag)
    {
        viewButton.setVisible(flag);
    }


    /**
     * 设置清除按钮是否可视
     * @param flag boolean
     */
    public void setDButtonVisable(boolean flag)
    {
        deleteButton.setVisible(flag);
    }


    /**
     * 设置下载按钮是否可视
     * @param flag boolean
     */
    public void setDLButtonVisable(boolean flag)
    {
        downLoadButton.setVisible(flag);
    }


    /**
     * 设置添加按钮的助记符和名字
     * @param c char 助记符
     * @param s String 助记符的显示
     */
    public void setSelectBMnemonic(char c, String s)
    {

        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "tranFile", null);
        addButton.setMnemonic(c);
        addButton.setText(str1 + "(" + s + ")" + ". . .");

    }


    /**
     * 在下载按钮上添加助记符和名字
     * @param c char 助记符
     */
    public void setDownLoadBMnemonic(char c)
    {
        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "downLoad", null);
        downLoadButton.setMnemonic(c);
        downLoadButton.setText(str1 + "(" + c + ")" + ". . .");
    }


    /**
     * 设置下载按钮的助记符和名字
     * @param c char 助记符
     * @param s String 助记符的显示
     */
    public void setDownLoadBMnemonic(char c, String s)
    {

        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "downLoad", null);
        downLoadButton.setMnemonic(c);
        downLoadButton.setText(str1 + "(" + s + ")" + ". . .");

    }


    /**
     * 设置添加按钮大小
     * @param x 宽度
     * @param y 高度
     */
    public void setButtonSize(int x, int y)
    {
        addButton.setPreferredSize(new Dimension(x, y));
        addButton.setMinimumSize(new Dimension(x, y));
        addButton.setMaximumSize(new Dimension(x, y));
    }


    /**
     * 在添加按钮上添加名字和助记符
     * @param c char 助记符
     */
    public void setSelectBMnemonic(char c)
    {
        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "addAccessary", null);
        addButton.setMnemonic(c);
        addButton.setText(str1 + "(" + c + ")" + ". . .");
    }


    /**
     * 在查看按钮上添加助记符和名字
     * @param c char 助记符
     */
    public void setOpenBMnemonic(char c)
    {
        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "open", null);
        viewButton.setMnemonic(c);
        viewButton.setText(str1 + "(" + c + ")" + ". . .");
    }


    /**
     * 在清除按钮上添加助记符和名字
     * @param c char 助记符
     */
    public void setClearBMnemonic(char c)
    {
        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "clear", null);
        deleteButton.setMnemonic(c);
//        deleteButton.setText(str1 + "(" + c + ")" + ". . .");
    }


    /**
     * 设置下载按钮大小
     * @param x 宽度
     * @param y 高度
     */
    public void setdownLoadButtonSize(int x, int y)
    {
        downLoadButton.setPreferredSize(new Dimension(x, y));
        downLoadButton.setMinimumSize(new Dimension(x, y));
        downLoadButton.setMaximumSize(new Dimension(x, y));
    }


    /**
     * 获得要从数据库中删除的附件
     * @return HashMap
     */
    public HashMap getDeleteContentMap()
    {
        return deleteContentMap;
    }


    /**
     * 获得附件行数
     * @return int
     */
    public int getRow()
    {
        return rowCount;
    }


    /**
     * 设置附件行数
     * @param row int
     */
    public void setRow(int row)
    {
        this.rowCount = row;
    }

}
