/**
 * ���ɳ��� ReportFormsController.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ����·���ݿͻ����������е�"��ʾ"���ܳ��� liunan 2014-2-7
 * SS2 ���ر��� liuyang 2014-9-3
 * SS3 �ɶ�����·�����ϣ�����·��5�ֱ��� liunan 2016-8-15
 */
package com.faw_qm.cappclients.conscapproute.controller;

import java.awt.event.*;
import com.faw_qm.cappclients.conscapproute.view.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.util.Vector;

import com.faw_qm.cappclients.conscapproute.util.TXTFileFilter;
import com.faw_qm.cappclients.conscapproute.util.*;
import com.faw_qm.framework.util.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.*;
import com.faw_qm.technics.consroute.model.*;
import com.faw_qm.technics.consroute.util.*;
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

    //CCBegin by wanghonglian 2008-07-01
    private ExportExcel excel;
    //CCEnd by wanghonglian 2008-07-01
  //CCBegin SS2
    private String comp="";
    private CTFirstRouteExportExcel ctfirstroute;
    private CTSecondRouteExportExcel ctsecondroute;
    //CCEnd SS2

  //CCBegin SS3
  private CdExportExcel cdexcel;
  //CCEnd SS3
  
    /**
     * ���캯��
     *
     * @param d
     *            ���������������
     */
    public ReportFormsController(ReportFormsJDialog d) {
    	 //CCBegin SS2
    	RequestServer server = RequestServerFactory.getRequestServer();
    	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
    	info1.setClassName("com.faw_qm.doc.util.DocServiceHelper");
        info1.setMethodName("getUserFromCompany");
        Class[] classes = {};
        info1.setParaClasses(classes);
        Object[] objs = {};
        info1.setParaValues(objs);
        try {
			comp=(String)server.request(info1);
		} catch (QMRemoteException e) {
			e.printStackTrace();
		}
		//CCEnd SS2
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
        //CCBegin by wanghonglian 2008-07-01
        File f = new File(view.getRouteList().getRouteListNumber());
        chooser.setSelectedFile(f);
       // if (selectedFile != null)
          //  chooser.setSelectedFile(selectedFile);
        //CCEnd by wanghonglian 2008-07-01

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
            //CCBegin by wanghonglian 2008-07-01
/*            Class[] c = { String.class, String.class, String.class };
            Object[] objs = { view.getRouteList().getBsoID(),
                    view.getRouteList().getRouteListLevel(), reportFormat };
            try {
                String returnStr = (String) useServiceMethod(
                        "TechnicsRouteService", "exportRouteList", c, objs);
                FileWriter fw = new FileWriter(selectedFile.getPath(), false);
                fw.write(returnStr);
                fw.close();
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
            view.setVisible(false);*/

            try
            {
            	//CCBegin SS1
            	 TechnicsRouteListInfo routeList = view.getRouteList();
                if(comp.equals("ct")){
                	if(routeList.getRouteListLevel().equals(RouteListLevelType.FIRSTROUTE.getDisplay())){
                		ctfirstroute = new CTFirstRouteExportExcel();
                		ctfirstroute.gatherExportData(routeList.getBsoID());
                	     boolean flag = ctfirstroute.printFile(selectedFile.getPath());
                	     if(flag)
                	      {
                	         JOptionPane.showMessageDialog(view,"�����ɹ���");
                	      }
                	      else
                	       {
                	         JOptionPane.showMessageDialog(view,"��������������ֹ��");
                	       }

                	}else{
                		ctsecondroute=new CTSecondRouteExportExcel();
                		ctsecondroute.gatherExportData(routeList.getBsoID());
               	        boolean flag = ctsecondroute.printFile(selectedFile.getPath());
               	        if(flag)
               	        {
               	           JOptionPane.showMessageDialog(view,"�����ɹ���");
               	        }
               	        else
               	       {
               	         JOptionPane.showMessageDialog(view,"��������������ֹ��");
               	       }

                	}
                	
                	
                }
                
        //CCBegin SS2
        else if(comp.equals("cd"))
        {
        	cdexcel = new CdExportExcel();
        	Vector vec = cdexcel.gatherExportData(routeList.getBsoID());
        	System.out.println("����������"+vec.size());
        	boolean flag = cdexcel.printFile(selectedFile.getPath(), vec);
        	if(flag)
        	{
        		JOptionPane.showMessageDialog(view,"�����ɹ���");
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(view,"��������������ֹ��");
        	}
        }
        //CCEnd SS2
                else{
             //CCEnd SS1
                excel = new ExportExcel();
                excel.gatherExportData(view.getRouteList().getBsoID());
                boolean flag = excel.printFile(selectedFile.getPath());
                if(flag)
                {
                    JOptionPane.showMessageDialog(view,"�����ɹ���");
                }
                else
                {
                    JOptionPane.showMessageDialog(view,"��������������ֹ��");
                }
                }
            }
            catch (QMException ex)
            {
                JOptionPane.showMessageDialog(view,
                                              ex.getClientMessage(),
                                              QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null),
                                              JOptionPane.INFORMATION_MESSAGE);
            }

            view.setVisible(false);
            if(!view.getRouteCompleteOrListId().equals(""))
            {
              System.exit(0);
            }
            //CCEnd by wanghonglian 2008-07-01
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
    /**
     * ����ҵ�����(PHOS-CAPP-BR206)��ʾһ��·�߱����������·�߱������
     * ʶ��˹���·�߱�ļ��������ɹ���·�߱���ʱ��ʾ���Ӧ����Ľ���
     */
    private void displayWeb() {
        view.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        TechnicsRouteListInfo routeList = view.getRouteList();
        HashMap hashmap = new HashMap();
        if (routeList.getRouteListLevel().equals(
                RouteListLevelType.FIRSTROUTE.getDisplay())) {
        	//CCBegin by wanghonglian 2008-07-01
        	//hashmap.put("bsoID", routeList.getBsoID());
            hashmap.put("bsoID",
                    routeList == null ? view.getRouteCompleteOrListId() :
                    routeList.getBsoID());//liun
            //CCEnd by wanghonglian 2008-07-01
            //ת��"һ��·�߱���"ҳ��
            //CCBegin by wanghonglian 2008-08-04 
            //ʹ�ʿͻ��˵�������ݿͻ��ĵ�����ʽһ��
            //CCBegin SS2
            if(comp.equals("ct")){
            	  RichToThinUtil.toWebPage("consroute/ctroute_report_001.jsp", hashmap);
            }else{
            //CCEnd SS2
            //CCBegin SS1
            //RichToThinUtil.toWebPage("capp/route_report_001.jsp", hashmap);
            RichToThinUtil.toWebPage("consroute/route_report_001.jsp", hashmap);
            }
            //CCEnd SS1
        } else {
            hashmap.put("bsoID", routeList.getBsoID());
            //CCBegin SS2
            if(comp.equals("ct")){
            	  RichToThinUtil.toWebPage("consroute/ctroute_report_002.jsp", hashmap);
            }else{
            //CCEnd SS2
            //ת��"����·�߱���"ҳ��            
            //CCBegin SS1
            //RichToThinUtil.toWebPage("capp/route_report_002.jsp", hashmap);
            RichToThinUtil.toWebPage("consroute/route_report_002.jsp", hashmap);
            }
            //CCEnd SS1
            //CCEnd by wanghonglian 2008-08-04
        }
        view.setCursor(Cursor.getDefaultCursor());
        view.setVisible(false);
    }

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
                                    "com.faw_qm.cappclients.conscapproute.util.RouteHelpRB",
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
