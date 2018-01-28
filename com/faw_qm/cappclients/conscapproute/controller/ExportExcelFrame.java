package com.faw_qm.cappclients.conscapproute.controller;

import java.awt.*;
import javax.swing.*;
import java.awt.BorderLayout;
import java.util.Vector;

import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.cappclients.capp.controller.CappClientRequestServer;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.cappclients.conscapproute.controller.ExportExcel;
import com.faw_qm.cappclients.conscapproute.util.CappRouteRB;
import com.faw_qm.cappclients.conscapproute.util.XLSFileFilter;
import java.io.File;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.technics.consroute.model.TechnicsRouteListInfo;
import com.faw_qm.technics.consroute.util.RouteListLevelType;

import java.awt.Font;
//SS1 长特报表导出 liuyang 2014-9-3
//SS2 成都工艺路线整合，工艺路线5种报表 liunan 2016-8-15
/**
 * <p>Title:专为网页生成报表服务 </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ExportExcelFrame
    extends JFrame {

  public static String RESOURCE
      = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";
  /**用于代码测试*/
  public static boolean verbose = (RemoteProperty.getProperty(
      "com.faw_qm.cappclients.verbose", "true")).equals("true");

  BorderLayout borderLayout1 = new BorderLayout();
  JFileChooser chooser = new JFileChooser();
  ReportFormsController control = new ReportFormsController(null);
  private File selectedFile;
  private ExportExcel excel;
  private JLabel label = new JLabel();
//CCBegin SS1
  private String comp="";
  private CTFirstRouteExportExcel ctfirstroute;
  private CTSecondRouteExportExcel ctsecondroute;
  //CCEnd SS1
  //CCBegin SS2
  private CdExportExcel cdexcel;
  //CCEnd SS2

  public ExportExcelFrame() {
    try {
    	 //CCBegin SS1
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
		//CCEnd SS1
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setSize(400,300);
    this.setResizable(false);
    this.setTitle("输出报表至...");
    getContentPane().setLayout(borderLayout1);
    label.setFont(new java.awt.Font("宋体", Font.BOLD, 30));
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setHorizontalTextPosition(SwingConstants.CENTER);
    label.setText("正在保存数据，请等候...");
    this.getContentPane().add(chooser, java.awt.BorderLayout.CENTER);

  }

  /**
   * 设置界面的显示位置
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
   * 重载父类方法，使界面显示在屏幕中央
   * @param flag
   */
  public void setVisible(boolean flag)
  {
    this.setViewLocation();
    super.setVisible(flag);
  }

  /**
   * 从网页生成Excel报表
   * 20061218 liuming add
   * @param routeListBsoID String
   * @param expandByProduct String
   */
  public void reportExcelFromWeb(String routeListBsoID) {
    try {
      Class[] ccc = {
          String.class};
      String[] o = {
          routeListBsoID};
      TechnicsRouteListInfo listinfo = (TechnicsRouteListInfo) CappRouteAction.
          useServiceMethod("PersistService", "refreshInfo", ccc, o);

      chooser.setDialogTitle("输出报表至...");
      //设置文件选取模式：文件和路径
      chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      //设置不可多选
      chooser.setMultiSelectionEnabled(false);

      File f = new File(listinfo.getRouteListNumber());
      chooser.setSelectedFile(f);

      XLSFileFilter filter = new XLSFileFilter();

      chooser.setFileFilter(filter);

      //删除系统自带的AcceptAllFileFilter
      chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());

      //“打开”模式文件选择器中选择了批准按钮还是取消按钮
      int state = chooser.showSaveDialog(this);
      //获得选择的文件
      selectedFile = chooser.getSelectedFile();

      //如果选择了批准按钮,则获得所选择文件名
      if (selectedFile != null && state == JFileChooser.APPROVE_OPTION)
      {
        this.getContentPane().add(label, java.awt.BorderLayout.CENTER);
        this.setVisible(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //文件格式转换
        selectedFile = control.translateFile(selectedFile, filter);

        //判断 1 未输入文件名,请输入文件名称  2 指定的路径不存在或不可用 3 文件已存在,请重新指定文件名
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
//CCBegin SS1
        if(comp.equals("ct")){
        	if(listinfo.getRouteListLevel().equals( RouteListLevelType.FIRSTROUTE.getDisplay())){
        		ctfirstroute = new CTFirstRouteExportExcel();
        		ctfirstroute.gatherExportData(routeListBsoID);
        	     boolean flag = ctfirstroute.printFile(selectedFile.getPath());
        	     if(flag)
        	      {
        	         JOptionPane.showMessageDialog(this,"导出成功！");
        	      }
        	      else
        	       {
        	         JOptionPane.showMessageDialog(this,"导出进程意外终止！");
        	       }

        	}else{
        		ctsecondroute=new CTSecondRouteExportExcel();
        		ctsecondroute.gatherExportData(routeListBsoID);
       	        boolean flag = ctsecondroute.printFile(selectedFile.getPath());
       	        if(flag)
       	        {
       	           JOptionPane.showMessageDialog(this,"导出成功！");
       	        }
       	        else
       	       {
       	         JOptionPane.showMessageDialog(this,"导出进程意外终止！");
       	       }

        	}
        	
        }
        //CCBegin SS2
        else if(comp.equals("cd"))
        {
        	cdexcel = new CdExportExcel();
        	Vector vec = cdexcel.gatherExportData(routeListBsoID);
        	boolean flag = cdexcel.printFile(selectedFile.getPath(), vec);
        	if(flag)
        	{
        		JOptionPane.showMessageDialog(this,"导出成功！");
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(this,"导出进程意外终止！");
        	}
        }
        //CCEnd SS2
        else{
     //CCEnd SS1
        excel = new ExportExcel();
        excel.gatherExportData(routeListBsoID);
        boolean flag = excel.printFile(selectedFile.getPath());
        if(flag)
        {
          JOptionPane.showMessageDialog(this,"导出成功！");
        }
        else
        {
          JOptionPane.showMessageDialog(this,"导出进程意外终止！");
        }

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
        frame.reportExcelFromWeb(args[3]);

        System.exit(0);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }
}
