/**
 * ���ɳ��� ReportFormsController.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.capproute.controller;

import java.awt.event.*;
import com.faw_qm.cappclients.capproute.view.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import com.faw_qm.cappclients.capproute.util.TXTFileFilter;
import com.faw_qm.cappclients.capproute.util.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.framework.remote.*;
import com.faw_qm.technics.route.model.*;
import com.faw_qm.technics.route.util.*;
import java.util.*;
import java.awt.*;
import java.net.*;
import com.faw_qm.help.*;

/**
 * <p>
 * Title:������������
 * </p>
 * <p>
 * Description:������������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @version 1.0
 */

public class ReportFormsController extends CappRouteAction implements
        ActionListener {
    /** ������� */
    private ReportFormsJDialog view;

    /** �����ʽ */
    private String reportModel = "Display";

    /** �����ʽ */
    private String reportFormat;

    /** �ļ������� */
    private FileFilter filter;

    private File selectedFile;

    //���������Ļ���
    private QMHelpContext helpContext;

    //����ϵͳ
    private QMHelpSystem helpSystem;
    
    //CCBegin by leixiao 2008-8-11 ԭ�򣺽����������·��
    private ExportExcel excel;
    //CCEnd by leixiao 2008-8-11 ԭ�򣺽����������·��

    /**
     * ���캯��
     *
     * @param d
     *            ���������������
     */
    public ReportFormsController(ReportFormsJDialog d) {
        view = d;
    }

    /**
     * ���������ʽ
     *
     * @param model
     *            �����ʽ
     * @roseuid 40316F360381
     */
    public void setReportModel(String model) {
        this.reportModel = model;
    }

    /**
     * �������
     * @see JFileChooser �ļ�ѡ����
     * @roseuid 40316FF30382
     */
    private void report() {
        //�ļ�ѡ����
        JFileChooser chooser = new JFileChooser();
        //�����ļ�ѡȡģʽ���ļ���·��
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //���ò��ɶ�ѡ
        chooser.setMultiSelectionEnabled(false);

        if (reportFormat.equals(".csv")) {
            filter = new TXTFileFilter();
            //chooser.setSelectedFile(new File("report.csv"));
        } else if (reportFormat.equals("Excel")) {
            filter = new XLSFileFilter();
            //chooser.setSelectedFile(new File("report.xls"));
        } else if (reportFormat.equals("XML")) {
            filter = new XMLFileFilter();
            //chooser.setSelectedFile(new File("report.xml"));
        }
//      CCBeginby leixiao 2008-11-5 ԭ�򣺽����������·��,����Ĭ�ϵ����ļ���
//      if (selectedFile != null)
//          chooser.setSelectedFile(selectedFile);
      File f = new File(view.getRouteList().getRouteListNumber());
      chooser.setSelectedFile(f);
//    CCEndby leixiao 2008-11-5 ԭ�򣺽����������·��
        chooser.setDialogTitle("���������...");
        chooser.setFileFilter(filter);
        //ɾ��ϵͳ�Դ���AcceptAllFileFilter
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());

        //���򿪡�ģʽ�ļ�ѡ������ѡ������׼��ť����ȡ����ť
        int state = chooser.showSaveDialog(view);// zz ָ���˸����� 2006-11
        //���ѡ����ļ�
        selectedFile = chooser.getSelectedFile();
        //���ѡ������׼��ť,������ѡ���ļ���
        if (selectedFile != null && state == JFileChooser.APPROVE_OPTION) {
            //�ļ���ʽת��
            selectedFile = this.translateFile(selectedFile, filter);

            //�ж� 1 δ�����ļ���,�������ļ����� 2 ָ����·�������ڻ򲻿��� 3 �ļ��Ѵ���,������ָ���ļ���
            if (!filter.accept(selectedFile)) {
                JOptionPane.showMessageDialog(view, QMMessage
                        .getLocalizedMessage(RESOURCE,
                                CappRouteRB.PATH_NOT_EXIST, null), QMMessage
                        .getLocalizedMessage(RESOURCE, "error", null),
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (selectedFile.exists()) {
                JOptionPane
                        .showMessageDialog(view,
                                QMMessage.getLocalizedMessage(RESOURCE,
                                        CappRouteRB.FILE_ALREADY_EXIST, null),
                                QMMessage.getLocalizedMessage(RESOURCE,
                                        "warning", null),
                                JOptionPane.WARNING_MESSAGE);
                return;
            }
//          CCBegin��by leixiao 2008-8-4 ԭ�򣺽����������·��·�ߵ���           
//            Class[] c = { String.class, String.class, String.class };
//            Object[] objs = { view.getRouteList().getBsoID(),
//                    view.getRouteList().getRouteListLevel(), reportFormat };
//          CCEnd��by leixiao 2008-8-4 ԭ�򣺽����������·��·�ߵ���
            try {
//            	CCBegin��by leixiao 2008-8-4 ԭ�򣺽����������·��·�ߵ���
//                String returnStr = (String) useServiceMethod(
//                        "TechnicsRouteService", "exportRouteList", c, objs);
//                FileWriter fw = new FileWriter(selectedFile.getPath(), false);
//                fw.write(returnStr);
//                fw.close();
//            	CCEnd��by leixiao 2008-8-4 ԭ�򣺽����������·��·�ߵ���
                //CCBegin��by leixiao 2008-8-4 ԭ�򣺽����������·��·�ߵ���
                excel = new ExportExcel();
                //CCBegin by liunan 2012-01-30 ��resultŲ��������
                //excel.gatherExportData(view.getRouteList().getBsoID(),"true");
                //boolean flag = excel.printFile(selectedFile.getPath());
                Vector result = excel.gatherExportData(view.getRouteList().getBsoID(),"true");
                boolean flag = excel.printFile(selectedFile.getPath(), result);
                //CCEnd by liunan 2012-01-30
                if(flag)
                {
                  JOptionPane.showMessageDialog(view,"�����ɹ���");
                }
                else
                {
                  JOptionPane.showMessageDialog(view,"��������������ֹ��");
                }
                //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,·�ߵ���
            }
            catch (QMRemoteException ex) {
                JOptionPane.showMessageDialog(view, ex.getClientMessage(),
                        QMMessage.getLocalizedMessage(RESOURCE, "information",
                                null), JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception e) {
              JOptionPane.showMessageDialog(view, e,
                         QMMessage.getLocalizedMessage(RESOURCE, "information",
                                 null), JOptionPane.INFORMATION_MESSAGE);

                e.printStackTrace();
            }
            view.setVisible(false);
        }
    }

    /**
     * ���fileû����չ���������չ��
     *
     * @param file
     * 			  �ļ�
     * @param filter
     *            �ļ�������
     * @return ��ʽת������ļ�
     */
//  CCBeginby leixiao 2008-8-4 ԭ�򣺽����������·��,private->public,ExportExcelFrame����
    public File translateFile(File file, FileFilter filter) {
        String path = file.getPath();
        if (filter instanceof TXTFileFilter) {
            if (!path.endsWith(".csv")) {
                path = path + ".csv";
            }
        } else if (filter instanceof XLSFileFilter) {
            if (!path.endsWith(".xls")) {
                path = path + ".xls";
            }
        } else if (filter instanceof XMLFileFilter) {
            if (!path.endsWith(".xml")) {
                path = path + ".xml";
            }
        }
        return new File(path);
    }
//  CCEndby leixiao 2008-8-4 ԭ�򣺽����������·��,private->public,ExportExcelFrame����

    /**
     * ����ҵ�����(PHOS-CAPP-BR206)��ʾһ��·�߱����������·�߱������
     * ʶ��˹���·�߱�ļ��������ɹ���·�߱���ʱ��ʾ���Ӧ����Ľ���
     */
//  CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��,����"����չ��" �Ӳ鿴�ձ��ݿͻ����ɱ���,  
    private void displayWeb() {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        TechnicsRouteListInfo routeList = view.getRouteList();
        HashMap hashmap = new HashMap();
        if (routeList == null ||
            routeList.getRouteListLevel().equals(RouteListLevelType.FIRSTROUTE.
                                                 getDisplay())) {
          hashmap.put("bsoID",
                      routeList == null ? view.getRouteCompleteOrListId() :
                      routeList.getBsoID());
          //System.out.println("-----------getIsExpandByProduct="+view.getIsExpandByProduct());
          hashmap.put("expandByProduct", view.getIsExpandByProduct() + "");
          //ת��"һ��·�߱���"ҳ��
//        CCBegin by leixiao 2009-3-30 ԭ�򣺽����������·��,�ձ����
          if(routeList.getRouteListState().equals("�ձ�")){
        	  RichToThinUtil.toWebPage("capp/routecomplete_report_001.jsp", hashmap); 
          }
//        CCEnd by leixiao 2009-3-30 ԭ�򣺽����������·��          

          //CCBegin by liunan 2011-09-21 �շ����
          if(routeList.getRouteListState().equals("�շ�")){
        	  RichToThinUtil.toWebPage("capp/routedisaffirm_report_001.jsp", hashmap); 
          }
          //CCEnd by liunan 2011-09-21
          else{
          RichToThinUtil.toWebPage("capp/route_report_001.jsp", hashmap);
          }
          if (routeList == null) {
            System.exit(0);
          }
        }
        else {
          hashmap.put("bsoID", routeList.getBsoID());
          //ת��"����·�߱���"ҳ��
//        CCBegin by leixiao 2009-12-18 ԭ�򣺽����������·��,����·�����ɱ���ͼ�겻��ʾ����Ų��ö���·��         
          RichToThinUtil.toWebPage("route_report_002.screen", hashmap);
//        CCEnd by leixiao 2009-12-18 ԭ�򣺽����������·��          
        }
        view.setCursor(Cursor.getDefaultCursor());
        view.setVisible(false);
      }
    

//  CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��,����"����չ��" 

    /**
     * ʵ�ֶ��������¼�
     *
     * @param e
     *            ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        view.updateRadioButtonEnable();

        String action = e.getActionCommand();
        //��WEBҳ��ʽ��ʾ������
        if (action.equals("Display")) {
            reportModel = "Display";
        }
        //���Ϊ�û��ͻ��˵��ļ�
        else if (action.equals("ToLocal")) {
            reportModel = "ToLocal";
        }
        //��.CSV�Ľ����ļ���ʽ���
        else if (action.equals("TXT")) {
            reportFormat = ".csv";
        }
        //��Excel���ݱ�ĸ�ʽ���
        else if (action.equals("Excel")) {
            reportFormat = "Excel";
        }
        //��XML��ʽ���
        else if (action.equals("XML")) {
            reportFormat = "XML";
        } else if (action.equals("OK")) {
            if (reportModel.equals("Display"))
                displayWeb();
            else if (reportModel.equals("ToLocal"))
                report();
        } else if (action.equals("CANCEL")) {
            view.setVisible(false);
        } else if (action.equals("HELP")) {
            help();
        }
    }

    /**
     * ���ø�ʽ
     *
     * @param str
     *            String
     */
    public void setFormat(String str) {
        this.reportFormat = str;
    }
    /**
     * ��ʾ����
     *
     */
    private void help() {
        if (helpSystem == null) {
            initializeHelp();
        } else {
            helpSystem.showHelp("ReportRouteListHelp");
        }
    }

    /**
     * ��ʼ������
     */
    private void initializeHelp() {
        try {
            //��������ļ����ڷ��������������˿�
            //URL url = new URL("http://pdmlm:80/");
            URL url = null;
            //�������ϵͳ��
            /*
             * ���� helptest �������������û�������Ҫ�������ݣ� url �����ļ����ڷ��������������˿� OnlineHelp
             * ����������� ResourceBundle ������Դ
             */
            helpSystem = new QMHelpSystem(
                    "capproute",
                    url,
                    "OnlineHelp",
                    ResourceBundle
                            .getBundle(
                                    "com.faw_qm.cappclients.capproute.util.RouteHelpRB",
                                    RemoteProperty.getVersionLocale()));
            //�����������ID�����û�����Ĺ���ģʽ���硰Update������Create������View����
            String s = "ReportRouteList";
            //������������Ļ���
            helpContext = new QMHelpContext(new JFrame(), helpSystem, s);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
