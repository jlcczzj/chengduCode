package com.faw_qm.cappclients.capproute.controller;

import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.cappclients.capproute.util.CappRouteRB;
import com.faw_qm.cappclients.capproute.util.XLSFileFilter;
import java.io.File;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import java.awt.Font;
import java.util.Vector;

/**
 * <p>Title:רΪ��ҳ���ɱ������ </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *  //CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��
 * @author not attributable
 * @version 1.0
 */
public class ExportExcelFrame
    extends JFrame {

  public static String RESOURCE
      = "com.faw_qm.cappclients.capproute.util.CappRouteRB";
  /**���ڴ������*/
  public static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.cappclients.verbose", "true")).equals("true");

  BorderLayout borderLayout1 = new BorderLayout();
  JFileChooser chooser = new JFileChooser();
  ReportFormsController control = new ReportFormsController(null);
  private File selectedFile;
  private ExportExcel excel;
  private JLabel label = new JLabel();


  public ExportExcelFrame() {
    try {
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setSize(400,300);
    this.setResizable(false);
    this.setTitle("���������...");
    getContentPane().setLayout(borderLayout1);
    label.setFont(new java.awt.Font("����", Font.BOLD, 30));
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setHorizontalTextPosition(SwingConstants.CENTER);
    label.setText("���ڱ������ݣ���Ⱥ�...");
    this.getContentPane().add(chooser, java.awt.BorderLayout.CENTER);

  }

  /**
   * ���ý������ʾλ��
   */
  private void setViewLocation()
  {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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

  /**
   * ���ظ��෽����ʹ������ʾ����Ļ����
   * @param flag
   */
  public void setVisible(boolean flag)
  {
    this.setViewLocation();
    super.setVisible(flag);
  }

  /**
   * ����ҳ����Excel����
   * 20061218 liuming add
   * @param routeListBsoID String
   * @param expandByProduct String
   */
  public void reportExcelFromWeb(String routeListBsoID, String expandByProduct) {
    try {
      Class[] ccc = {
          String.class};
      String[] o = {
          routeListBsoID};
      TechnicsRouteListInfo listinfo = (TechnicsRouteListInfo) CappRouteAction.
          useServiceMethod("PersistService", "refreshInfo", ccc, o);
      expandByProduct = "true";

      chooser.setDialogTitle("���������...");
      //�����ļ�ѡȡģʽ���ļ���·��
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      //���ò��ɶ�ѡ
      chooser.setMultiSelectionEnabled(false);

      File f = new File(listinfo.getRouteListNumber());
      chooser.setSelectedFile(f);

      XLSFileFilter filter = new XLSFileFilter();

      chooser.setFileFilter(filter);

      //ɾ��ϵͳ�Դ���AcceptAllFileFilter
      chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());

      //���򿪡�ģʽ�ļ�ѡ������ѡ������׼��ť����ȡ����ť
      int state = chooser.showSaveDialog(this);
      //���ѡ����ļ�
      selectedFile = chooser.getSelectedFile();

      //���ѡ������׼��ť,������ѡ���ļ���
      if (selectedFile != null && state == JFileChooser.APPROVE_OPTION)
      {
        this.getContentPane().add(label, java.awt.BorderLayout.CENTER);
        this.setVisible(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //�ļ���ʽת��
        selectedFile = control.translateFile(selectedFile, filter);

        //�ж� 1 δ�����ļ���,�������ļ�����  2 ָ����·�������ڻ򲻿��� 3 �ļ��Ѵ���,������ָ���ļ���
        if (!filter.accept(selectedFile)) {
          JOptionPane.showMessageDialog(this,
                                        QMMessage.getLocalizedMessage(RESOURCE,
              CappRouteRB.PATH_NOT_EXIST, null),
                                        QMMessage.getLocalizedMessage(RESOURCE,
              "error", null),
                                        JOptionPane.ERROR_MESSAGE);
          setCursor(Cursor.getDefaultCursor());
          return;
        }
        if (selectedFile.exists()) {
          JOptionPane.showMessageDialog(this,
                                        QMMessage.getLocalizedMessage(RESOURCE,
              CappRouteRB.FILE_ALREADY_EXIST, null),
                                        QMMessage.getLocalizedMessage(RESOURCE,
              "warning", null),
                                        JOptionPane.WARNING_MESSAGE);
          setCursor(Cursor.getDefaultCursor());
          return;
        }

        excel = new ExportExcel();
        //CCBegin by liunan 2012-01-30 ��resultŲ��������
        //excel.gatherExportData(routeListBsoID, expandByProduct);
        //boolean flag = excel.printFile(selectedFile.getPath());
        Vector result = excel.gatherExportData(routeListBsoID, expandByProduct);
        boolean flag = excel.printFile(selectedFile.getPath(),result);
        //CCEnd by liunan 2012-01-30
        if(flag)
        {
          JOptionPane.showMessageDialog(this,"�����ɹ���");
        }
        else
        {
          JOptionPane.showMessageDialog(this,"��������������ֹ��");
        }

      }

    }
    catch (QMRemoteException ex) {
      JOptionPane.showMessageDialog(this,
                                    ex.getClientMessage(),
                                    QMMessage.getLocalizedMessage(RESOURCE,
          "information", null),
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    catch (QMException e) {
      JOptionPane.showMessageDialog(this,
                                    e.getClientMessage(),
                                    QMMessage.getLocalizedMessage(RESOURCE,
          "information", null),
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    finally
    {
      setCursor(Cursor.getDefaultCursor());
    }
  }



  public static void main(String[] args) {
    try {
      System.setProperty("swing.useSystemFontSettings", "0");
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      if (args != null && args.length == 4) {
        CappClientRequestServer server = new CappClientRequestServer(args[0],
            args[1], args[2]);
        RequestServerFactory.setRequestServer(server);
        ExportExcelFrame frame = new ExportExcelFrame();
        frame.reportExcelFromWeb(args[3], "true");

        System.exit(0);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }
}
