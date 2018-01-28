/** ���ɳ���UpFilePanel.java	1.1  2009-10-12
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ����ļ��������ϴ��߼� jiahx 2013-12-09
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
 * <p>Title: ���տ�����ά�����</p>
 * <p>Description: ���տ��Ĵ��������¡��鿴�����Ϊ�������ڱ����ά����</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
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

    //�ļ�ѡ����  2007.01.17  �촺Ӣע��
    //private JFileChooser jFileChooser = null;
    //private JPanel panel;
    public ComponentMultiList checkboxMultiList = new ComponentMultiList();
    ApplicationDataInfo applicationDataInfo = new ApplicationDataInfo();
    //ɾ�����ļ�������
    private HashMap deleteContentMap = new HashMap();
    //2007.01.17
    private JFrame parentFrame = null;

    /**���ڱ����Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.resource.util.ResourceRB";

    //CCBegin SS1
    static boolean fileVaultUsed = (RemoteProperty.getProperty(
            "registryFileVaultStoreMode", "true")).equals("true");
    //CCEnd SS1

    /**
     * ���캯��
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
        checkboxMultiList.setHeadings(new String[]{"�ļ�����", "�ļ�·��", "�ļ���С���ֽڣ�", ""});
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
        viewButton.setLabel("�鿴");
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
        addButton.setLabel("���");
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
        downLoadButton.setLabel("����");
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
        deleteButton.setLabel("ɾ��");
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
        /**��Ӱ�ť����*/
        /**�鿴��ť����*/
        viewButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                viewButton_actionPerformed(e);
            }
        });
        /**ɾ����ť����*/
        /**���ذ�ť����*/

    }


    /**
     * ִ����Ӱ�ťִ�еĲ���
     * @param e ActionEvent
     */
    void addButton_actionPerformed(ActionEvent e)
    {
        //����(1)2007.01.17  �촺Ӣ�޸�  �޸�ԭ��:��JFileChooser����ɾֲ���������Ļ�
        //�ļ����л���.
        JFileChooser jFileChooser = new JFileChooser();
        //����Ĭ�ϴ򿪵�·��
        jFileChooser.setCurrentDirectory(new File(
                "C:/Documents and Settings/Administrator/My Documents"));
        jFileChooser.setApproveButtonText("��");
        int i = jFileChooser.showOpenDialog(parentFrame);
        switch (i)
        {
            case JFileChooser.APPROVE_OPTION:
                jFileChooser_APPROVE(jFileChooser); //��򿪰�ť��Ĳ���
                break;
            case JFileChooser.CANCEL_OPTION:
                jFileChooser_CANCEL(); //��ȡ����ť�Ĳ���
                break;
        }

    }


    /**
     * ����鿴��ťִ�еĲ���
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
     * ���ɾ����ťִ�еĲ���
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
     * ������ذ�ťִ�еĲ���
     * @param e ActionEvent
     * ����(1)2007.01.17  �촺Ӣ�޸�  �޸�ԭ��:��JFileChooser����ɾֲ���������Ļ�
     * �ļ����л���.
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
         
            //����(1)2007.01.17  �촺Ӣ�޸�  �޸�ԭ��:��JFileChooser����ɾֲ���������Ļ�
            //�ļ����л���.
            JFileChooser  jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("����");
            //����Ĭ�ϴ򿪵�·��
            jFileChooser.setCurrentDirectory(new File(
                    "C:/Documents and Settings/Administrator/My Documents"));
            jFileChooser.setApproveButtonText("����");
            //����(3)2007.01.17  �촺Ӣ�޸�  �޸�ԭ��:���������ļ������õ�JFileChooser���ı�����
            jFileChooser.setSelectedFile(file);
            int i = jFileChooser.showOpenDialog(parentFrame);
            switch (i)
            {
                case JFileChooser.APPROVE_OPTION:
                    jFileChooser_SAVE(jFileChooser); //�㱣�水ť��Ĳ���
                    break;
                case JFileChooser.CANCEL_OPTION:
                    jFileChooser_CANCEL(); //��ȡ����ť�Ĳ���
                    break;
            }

        }
    }


    /**
     * ���غ������水ťִ�еĲ���
     * ����(2)2007.01.17  �촺Ӣ�޸�  �޸�ԭ��:���ֲ�����JFileChooser����jFileChooser_APPROVE
     */
    private void jFileChooser_SAVE(JFileChooser jFileChooser)
    {
        //�õ�ѡ���ļ�
        File file = jFileChooser.getSelectedFile();
        //�õ��ļ�ȫ·��
        path = file.getAbsolutePath();
        
        int row = checkboxMultiList.getSelectedRow();
        fileName = (String) checkboxMultiList.getCellText(row, 0);
      
        String sourceFileName = fileName;
        //����(3)2007.01.17 �촺Ӣ�޸�  �޸�ԭ��:���ظ���ʱѡ�񱣴�λ��ʱ��ϵͳ�Զ���ȡ���ļ�����չ����
        //����һ���ļ���Ϊ��123������չ��Ϊ��.txt��
        //�����ļ��ϴ��������ص��������ļ����ͱ���ˡ�123.txt.txt��.
        if(path.indexOf(".") != -1)
        {
            newFileName = path;
        }
        else
        if (sourceFileName.indexOf(".") != -1)
        {
            String sign = "";
            int index = sourceFileName.lastIndexOf(".");
            //��ȡ�ļ���׺
            sign = sourceFileName.substring(index);
            newFileName = path + sign;
        }
        applicationDataInfo = refreshAppInfo(appDataID);
       
        //CCBegin SS1 
        //��ȡ�����ֽ�
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
            //��ȡ��ID
            String streamID = applicationDataInfo.getStreamDataID();
        	content = getStreamInfo(streamID).getDataContent();
        }
        //CCEnd SS1
        try
        {

            FileOutputStream out = new FileOutputStream(new File(newFileName));
            out.write(content);
            out.close();
            JOptionPane.showMessageDialog(parentFrame, "�����ļ��ɹ�!");
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
     * ˢ��ApplicationDataInfoֵ����
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
            //ͨ������������Ĺ������server
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
     * ����ļ���
     * @param str String ��ID
     * @param file byte[] �ļ�������
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
            //ͨ������������Ĺ������server
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
     * (JFileChooser)�����Ĵ��ļ������ȷ����ť����
     * ����(2)2007.01.17  �촺Ӣ�޸�  �޸�ԭ��:���ֲ�����JFileChooser����jFileChooser_APPROVE
     */
    private void jFileChooser_APPROVE(JFileChooser jFileChooser)
    {
        //�õ�ѡ���ļ�
        File file = jFileChooser.getSelectedFile();
        //�õ��ļ�ȫ·��
        path = file.getAbsolutePath();
        //CCBeginby liuzc 2009-12-19 ԭ�������ж��Ƿ���ӹ��������μ�DefectID=2666
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
            JOptionPane.showMessageDialog(parentFrame, "�Ѿ�����ͬ���ļ�", title,
                                          JOptionPane.WARNING_MESSAGE);
            return;
        }
        //CCBeginby liuzc 2009-12-19 ԭ�������ж��Ƿ���ӹ��������μ�DefectID=2666
        if (path.indexOf("\\") != -1)
        {
            int index = path.lastIndexOf("\\");
            //��ȡ�ļ�����
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
     * (JFileChooser)�����Ĵ��ļ������ȡ����ť����
     * Ϊ�յ�ԭ����JFileChooser�ؼ����иò����������ظ�д
     */
    private void jFileChooser_CANCEL()
    {

    }


    /**
     * ����ļ�����
     * @return String
     */
    public String getFileName()
    {
        return fileName;
    }


    /**
     * ����ļ�·��
     * @return String
     */
    public String getPath()
    {
        return path;
    }


    /**
     * ����ļ���С
     * @return long
     */
    public long getFileLength()
    {
        return length;
    }


    /**
     * ���checkboxMultiList
     * @return ComponentMultiList
     */
    public ComponentMultiList getMultiList()
    {
        return checkboxMultiList;
    }


    /**
     * ����������
     * @param application ApplicationDataInfo
     */
    public void setApplication(ApplicationDataInfo application)
    {
        applicationDataInfo = application;
    }


    /**
     * ������Ӱ�ť�Ƿ����
     * @param flag boolean
     */
    public void setAButtonVisable(boolean flag)
    {
        addButton.setVisible(flag);
    }


    /**
     * ���ò鿴��ť�Ƿ����
     * @param flag boolean
     */
    public void setVButtonVisable(boolean flag)
    {
        viewButton.setVisible(flag);
    }


    /**
     * ���������ť�Ƿ����
     * @param flag boolean
     */
    public void setDButtonVisable(boolean flag)
    {
        deleteButton.setVisible(flag);
    }


    /**
     * �������ذ�ť�Ƿ����
     * @param flag boolean
     */
    public void setDLButtonVisable(boolean flag)
    {
        downLoadButton.setVisible(flag);
    }


    /**
     * ������Ӱ�ť�����Ƿ�������
     * @param c char ���Ƿ�
     * @param s String ���Ƿ�����ʾ
     */
    public void setSelectBMnemonic(char c, String s)
    {

        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "tranFile", null);
        addButton.setMnemonic(c);
        addButton.setText(str1 + "(" + s + ")" + ". . .");

    }


    /**
     * �����ذ�ť��������Ƿ�������
     * @param c char ���Ƿ�
     */
    public void setDownLoadBMnemonic(char c)
    {
        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "downLoad", null);
        downLoadButton.setMnemonic(c);
        downLoadButton.setText(str1 + "(" + c + ")" + ". . .");
    }


    /**
     * �������ذ�ť�����Ƿ�������
     * @param c char ���Ƿ�
     * @param s String ���Ƿ�����ʾ
     */
    public void setDownLoadBMnemonic(char c, String s)
    {

        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "downLoad", null);
        downLoadButton.setMnemonic(c);
        downLoadButton.setText(str1 + "(" + s + ")" + ". . .");

    }


    /**
     * ������Ӱ�ť��С
     * @param x ���
     * @param y �߶�
     */
    public void setButtonSize(int x, int y)
    {
        addButton.setPreferredSize(new Dimension(x, y));
        addButton.setMinimumSize(new Dimension(x, y));
        addButton.setMaximumSize(new Dimension(x, y));
    }


    /**
     * ����Ӱ�ť��������ֺ����Ƿ�
     * @param c char ���Ƿ�
     */
    public void setSelectBMnemonic(char c)
    {
        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "addAccessary", null);
        addButton.setMnemonic(c);
        addButton.setText(str1 + "(" + c + ")" + ". . .");
    }


    /**
     * �ڲ鿴��ť��������Ƿ�������
     * @param c char ���Ƿ�
     */
    public void setOpenBMnemonic(char c)
    {
        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "open", null);
        viewButton.setMnemonic(c);
        viewButton.setText(str1 + "(" + c + ")" + ". . .");
    }


    /**
     * �������ť��������Ƿ�������
     * @param c char ���Ƿ�
     */
    public void setClearBMnemonic(char c)
    {
        String str1 = QMMessage.getLocalizedMessage(RESOURCE, "clear", null);
        deleteButton.setMnemonic(c);
//        deleteButton.setText(str1 + "(" + c + ")" + ". . .");
    }


    /**
     * �������ذ�ť��С
     * @param x ���
     * @param y �߶�
     */
    public void setdownLoadButtonSize(int x, int y)
    {
        downLoadButton.setPreferredSize(new Dimension(x, y));
        downLoadButton.setMinimumSize(new Dimension(x, y));
        downLoadButton.setMaximumSize(new Dimension(x, y));
    }


    /**
     * ���Ҫ�����ݿ���ɾ���ĸ���
     * @return HashMap
     */
    public HashMap getDeleteContentMap()
    {
        return deleteContentMap;
    }


    /**
     * ��ø�������
     * @return int
     */
    public int getRow()
    {
        return rowCount;
    }


    /**
     * ���ø�������
     * @param row int
     */
    public void setRow(int row)
    {
        this.rowCount = row;
    }

}
