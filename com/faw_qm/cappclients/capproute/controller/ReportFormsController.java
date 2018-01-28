/**
 * 生成程序 ReportFormsController.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * Title:输出报表控制类
 * </p>
 * <p>
 * Description:输出报表控制类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 *
 * @author 刘明
 * @version 1.0
 */

public class ReportFormsController extends CappRouteAction implements
        ActionListener {
    /** 界面对象 */
    private ReportFormsJDialog view;

    /** 输出方式 */
    private String reportModel = "Display";

    /** 输出格式 */
    private String reportFormat;

    /** 文件过滤器 */
    private FileFilter filter;

    private File selectedFile;

    //帮助上下文环境
    private QMHelpContext helpContext;

    //帮助系统
    private QMHelpSystem helpSystem;
    
    //CCBegin by leixiao 2008-8-11 原因：解放升级工艺路线
    private ExportExcel excel;
    //CCEnd by leixiao 2008-8-11 原因：解放升级工艺路线

    /**
     * 构造函数
     *
     * @param d
     *            输出报表界面类对象
     */
    public ReportFormsController(ReportFormsJDialog d) {
        view = d;
    }

    /**
     * 设置输出方式
     *
     * @param model
     *            输出方式
     * @roseuid 40316F360381
     */
    public void setReportModel(String model) {
        this.reportModel = model;
    }

    /**
     * 输出报表
     * @see JFileChooser 文件选择器
     * @roseuid 40316FF30382
     */
    private void report() {
        //文件选择器
        JFileChooser chooser = new JFileChooser();
        //设置文件选取模式：文件和路径
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //设置不可多选
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
//      CCBeginby leixiao 2008-11-5 原因：解放升级工艺路线,增加默认导出文件名
//      if (selectedFile != null)
//          chooser.setSelectedFile(selectedFile);
      File f = new File(view.getRouteList().getRouteListNumber());
      chooser.setSelectedFile(f);
//    CCEndby leixiao 2008-11-5 原因：解放升级工艺路线
        chooser.setDialogTitle("输出报表至...");
        chooser.setFileFilter(filter);
        //删除系统自带的AcceptAllFileFilter
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());

        //“打开”模式文件选择器中选择了批准按钮还是取消按钮
        int state = chooser.showSaveDialog(view);// zz 指定了父界面 2006-11
        //获得选择的文件
        selectedFile = chooser.getSelectedFile();
        //如果选择了批准按钮,则获得所选择文件名
        if (selectedFile != null && state == JFileChooser.APPROVE_OPTION) {
            //文件格式转换
            selectedFile = this.translateFile(selectedFile, filter);

            //判断 1 未输入文件名,请输入文件名称 2 指定的路径不存在或不可用 3 文件已存在,请重新指定文件名
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
//          CCBegin　by leixiao 2008-8-4 原因：解放升级工艺路线路线导出           
//            Class[] c = { String.class, String.class, String.class };
//            Object[] objs = { view.getRouteList().getBsoID(),
//                    view.getRouteList().getRouteListLevel(), reportFormat };
//          CCEnd　by leixiao 2008-8-4 原因：解放升级工艺路线路线导出
            try {
//            	CCBegin　by leixiao 2008-8-4 原因：解放升级工艺路线路线导出
//                String returnStr = (String) useServiceMethod(
//                        "TechnicsRouteService", "exportRouteList", c, objs);
//                FileWriter fw = new FileWriter(selectedFile.getPath(), false);
//                fw.write(returnStr);
//                fw.close();
//            	CCEnd　by leixiao 2008-8-4 原因：解放升级工艺路线路线导出
                //CCBegin　by leixiao 2008-8-4 原因：解放升级工艺路线路线导出
                excel = new ExportExcel();
                //CCBegin by liunan 2012-01-30 将result挪到方法里
                //excel.gatherExportData(view.getRouteList().getBsoID(),"true");
                //boolean flag = excel.printFile(selectedFile.getPath());
                Vector result = excel.gatherExportData(view.getRouteList().getBsoID(),"true");
                boolean flag = excel.printFile(selectedFile.getPath(), result);
                //CCEnd by liunan 2012-01-30
                if(flag)
                {
                  JOptionPane.showMessageDialog(view,"导出成功！");
                }
                else
                {
                  JOptionPane.showMessageDialog(view,"导出进程意外终止！");
                }
                //CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,路线导出
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
     * 如果file没有扩展名，则加扩展名
     *
     * @param file
     * 			  文件
     * @param filter
     *            文件过滤器
     * @return 格式转换后的文件
     */
//  CCBeginby leixiao 2008-8-4 原因：解放升级工艺路线,private->public,ExportExcelFrame调用
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
//  CCEndby leixiao 2008-8-4 原因：解放升级工艺路线,private->public,ExportExcelFrame调用

    /**
     * 根据业务规则(PHOS-CAPP-BR206)显示一级路线报表界面或二级路线报表界面
     * 识别此工艺路线表的级别，在生成工艺路线报表时显示其对应级别的界面
     */
//  CCBegin by leixiao 2008-8-4 原因：解放升级工艺路线,增加"按车展开" 从查看艺毕瘦客户生成报表,  
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
          //转到"一级路线报表"页面
//        CCBegin by leixiao 2009-3-30 原因：解放升级工艺路线,艺毕输出
          if(routeList.getRouteListState().equals("艺毕")){
        	  RichToThinUtil.toWebPage("capp/routecomplete_report_001.jsp", hashmap); 
          }
//        CCEnd by leixiao 2009-3-30 原因：解放升级工艺路线          

          //CCBegin by liunan 2011-09-21 艺废输出
          if(routeList.getRouteListState().equals("艺废")){
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
          //转到"二级路线报表"页面
//        CCBegin by leixiao 2009-12-18 原因：解放升级工艺路线,二级路线生成报表图标不显示，解放不用二级路线         
          RichToThinUtil.toWebPage("route_report_002.screen", hashmap);
//        CCEnd by leixiao 2009-12-18 原因：解放升级工艺路线          
        }
        view.setCursor(Cursor.getDefaultCursor());
        view.setVisible(false);
      }
    

//  CCEnd by leixiao 2008-8-4 原因：解放升级工艺路线,增加"按车展开" 

    /**
     * 实现动作监听事件
     *
     * @param e
     *            ActionEvent
     */
    public void actionPerformed(ActionEvent e) {
        view.updateRadioButtonEnable();

        String action = e.getActionCommand();
        //以WEB页型式显示输出结果
        if (action.equals("Display")) {
            reportModel = "Display";
        }
        //输出为用户客户端的文件
        else if (action.equals("ToLocal")) {
            reportModel = "ToLocal";
        }
        //以.CSV的交换文件格式输出
        else if (action.equals("TXT")) {
            reportFormat = ".csv";
        }
        //以Excel数据表的格式输出
        else if (action.equals("Excel")) {
            reportFormat = "Excel";
        }
        //以XML格式输出
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
     * 设置格式
     *
     * @param str
     *            String
     */
    public void setFormat(String str) {
        this.reportFormat = str;
    }
    /**
     * 显示帮助
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
     * 初始化帮助
     */
    private void initializeHelp() {
        try {
            //定义帮助文件所在服务器的主机及端口
            //URL url = new URL("http://pdmlm:80/");
            URL url = null;
            //构造帮助系统。
            /*
             * 参数 helptest 表帮助的子类别（用户界面主要操作内容） url 帮助文件所在服务器的主机及端口 OnlineHelp
             * 浏览器窗口名 ResourceBundle 帮助资源
             */
            helpSystem = new QMHelpSystem(
                    "capproute",
                    url,
                    "OnlineHelp",
                    ResourceBundle
                            .getBundle(
                                    "com.faw_qm.cappclients.capproute.util.RouteHelpRB",
                                    RemoteProperty.getVersionLocale()));
            //定义帮助内容ID，（用户界面的工作模式，如“Update”、“Create”、“View”）
            String s = "ReportRouteList";
            //构造帮助上下文环境
            helpContext = new QMHelpContext(new JFrame(), helpSystem, s);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
