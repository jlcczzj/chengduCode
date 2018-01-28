/** 生成程序LogicBomFrame.java	1.1  2007-2-8
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  */
package com.faw_qm.part.client.other.view;

import javax.swing.border.TitledBorder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.Vector;

import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;

import com.faw_qm.clients.beans.explorer.QM;
import com.faw_qm.part.client.effectivity.controller.EffAction;
import com.faw_qm.part.client.effectivity.view.EffClientViewIfc;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.clients.widgets.IBAUtility;
import java.io.IOException;
import java.io.FileWriter;
import com.faw_qm.part.client.other.controller.MaterialController;
import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * <p>Title: 无合件（逻辑总成）装配表</p>
 * 根据解放公司需求添加功能
 * 输出合件装配表时，将逻辑总成去掉，将逻辑总成的子件提到逻辑总成的位置；输出报表时零部件号
 * 为了与艺准中零部件规则一致，输出形式改为零部件+“/”+版本。
 * <p>用户根据需要对选定产品或部件定制BOM内容和输出方式，输出BOM表。<p>
 * <p>生成物料清单时，按当前产品结构管理器的配置规范确定零部件使用结构中<p>
 * <p>子件使用的版本，如果子件中没有符合当前配置规范的版本，则该件不出现<p>
 * <p>在物料清单中。</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 2.0
 */

public class LogicBomFrame extends JFrame
{

  /**主面板*/
  private JPanel contentPane;
  /**用于显示输出属性面板*/
  private JPanel exportJPanel = new JPanel();
  /**功能面板*/
  private JPanel actionJPanel = new JPanel(); /**控制按钮面板*/
  private JPanel buttonJPanel = new JPanel(); /**状态条*/
  private JLabel statusJLabel = new JLabel();
  /**确定按钮*/
  private JButton okJButton = new JButton();
  /**取消按钮*/
  private JButton cancelJButton = new JButton();

  private TitledBorder titledBorder1;
  /**属性输出面板*/
  private ExportAttributesJPanel exportAttributeJPanel ;
  /**资源文件路径*/
//CCBegin by leixiao 2009-12-10   资源文件方式不对，找不着
//  private static String RESOURCE = "com/faw_qm/part/client/other/util/OtherRB";
  private static String RESOURCE =
          "com.faw_qm.part.client.other.util.OtherRB";
//CCEnd by leixiao 2009-12-10  
  
  /**零部件对象接口*/
  private QMPartIfc partIfc;
  /**放置参数的哈西表*/
  private HashMap hashMap = new HashMap();
  /**用于代码测试*/
  private static boolean verbose=(RemoteProperty.getProperty(
                             "com.faw_qm.part.verbose","true")).equals("true");
  /**界面布局管理器*/
  private GridBagLayout gridBagLayout1 = new GridBagLayout();
  private GridBagLayout gridBagLayout2 = new GridBagLayout();
  private GridBagLayout gridBagLayout3 = new GridBagLayout();
  private GridBagLayout gridBagLayout4 = new GridBagLayout();
  JButton exportButton = new JButton();

  /**
   * 构造函数
   * @param c  控制器
   * @param part  零部件对象接口
   */
  public LogicBomFrame(QMPartIfc part,boolean bool)
  {
    try
    {
      this.partIfc = part;
      //实例化属性输出面板
      exportAttributeJPanel = new ExportAttributesJPanel(bool);//add by liun
      jbInit();
      //设置界面动态标题
      if(partIfc!=null)
      {
        this.setTitle("无合件装配表" + partIfc.getPartNumber()+" "+partIfc.getPartName());
        String iconImage = QMMessage.getLocalizedMessage("com.faw_qm.part.client.main.util.QMProductManagerRB", QMProductManagerRB.ICONIMAGE, null);
        setIconImage(new ImageIcon(QM.getIcon(iconImage)).getImage());
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * 初始化
   * @throws Exception
   */
  private void jbInit() throws Exception
  {

    //Button
    cancelJButton.setMaximumSize(new Dimension(65, 23));
    cancelJButton.setMinimumSize(new Dimension(65, 23));
    cancelJButton.setPreferredSize(new Dimension(65, 23));
    okJButton.setMaximumSize(new Dimension(65, 23));
    okJButton.setMinimumSize(new Dimension(65, 23));
    okJButton.setPreferredSize(new Dimension(65, 23));
    okJButton.setText("确定");
    okJButton.addActionListener(new LogicBomFrame_okJButton_actionAdapter(this));
    cancelJButton.setText("取消"); //JLabel
    cancelJButton.addActionListener(new
                                    LogicBomFrame_cancelJButton_actionAdapter(this));
    statusJLabel.setText("  ");
    statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
    statusJLabel.setDoubleBuffered(true);
    contentPane = (JPanel) this.getContentPane();
    titledBorder1 = new TitledBorder(QMMessage.getLocalizedMessage(RESOURCE, "out" , null));
    contentPane.setLayout(gridBagLayout1);
    this.setSize(new Dimension(650, 500));
    actionJPanel.setBorder(titledBorder1);

    //界面布局
    actionJPanel.setLayout(gridBagLayout3);
    buttonJPanel.setLayout(gridBagLayout4);
    exportJPanel.setLayout(gridBagLayout2);
    exportButton.setMaximumSize(new Dimension(65, 23));
    exportButton.setMinimumSize(new Dimension(65, 23));
    exportButton.setPreferredSize(new Dimension(65, 23));
    exportButton.setSelectedIcon(null);
    exportButton.setText("输出");
    exportButton.addActionListener(new LogicBomFrame_exportButton_actionAdapter(this));
    contentPane.add(exportJPanel,     new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    exportJPanel.add(exportAttributeJPanel,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    contentPane.add(actionJPanel,       new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 10, 10), 0, 0));
    actionJPanel.add(buttonJPanel,        new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
    buttonJPanel.add(exportButton,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
    buttonJPanel.add(okJButton,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 8), 0, 0));
    buttonJPanel.add(cancelJButton,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    contentPane.add(statusJLabel,    new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));

  }


  /**
   * 获取可输出的属性的字符串,用来显示
   * @return 返回一个字符串
   */
  public String getAttribute()
  {
    return exportAttributeJPanel.getAttribute()[0];
  }

  /**
   * 获取可输出的属性的字符串，用来调服务
   * @return 返回一个字符串
   */
  public String getAttribute1()
  {
    if(verbose)
      System.out.println(">>>>>>>>>>>>>>>>> END :"+exportAttributeJPanel.getAttribute()[1]);
    return exportAttributeJPanel.getAttribute()[1];
  }

  /**
   * 设置状态栏
   */
  public void setStatusBar()
  {
    String s = exportAttributeJPanel.getAttribute()[1];
    statusJLabel.setText(s);
  }

  /**
   * 导出Excel
   * @param e ActionEvent
   */
  public void exportButton_actionPerformed(ActionEvent e) {
    HashMap map = new HashMap();
    map.put("PartID", partIfc.getBsoID());
    map.put("attributeName", getAttribute());
    map.put("attributeName1", getAttribute1());
    writeClassfyProcess(map);
  }

  //屈晓光添加，以分级形式输出BOM到本地文件
    private void writeClassfyProcess(HashMap map) {
      String path = getSelectedPath();
      if (path == null) {
        return;
      }
      try {
        setCursor(Cursor.WAIT_CURSOR);
        Class[] c = {
            HashMap.class};
        Object[] objs = {
            map};
        String returnStr = (String) IBAUtility.invokeServiceMethod(
            "StandardPartService", "getExportBOMClassfiyString2", c, objs);
        FileWriter fw = new FileWriter(path, false);
        fw.write(returnStr);
        fw.close();
        setCursor(Cursor.getDefaultCursor());
      }
      catch (QMRemoteException ex) {
        setCursor(Cursor.getDefaultCursor());
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this,
                                      ex.getClientMessage(),
                                      QMMessage.getLocalizedMessage(RESOURCE,
            "information", null),
                                      JOptionPane.INFORMATION_MESSAGE);
      }
      catch (IOException ex1) {
        setCursor(Cursor.getDefaultCursor());
        ex1.printStackTrace();
      }
  }

  /**
   * 生成分级物料清单（去掉逻辑总成）
   * @param e ActionEvent
   */
  public void okJButton_actionPerformed(ActionEvent e) {
    HashMap map = new HashMap();
    map.put("PartID", partIfc.getBsoID());
    map.put("attributeName", getAttribute());
    map.put("attributeName1", getAttribute1());
    RichToThinUtil.toWebPage("Part-Other-classifylisting-001-2.screen", map);
  }

  public void cancelJButton_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
  private String getSelectedPath() {
    FileFilter filter;
    File selectedFile = null;
    setCursor(Cursor.WAIT_CURSOR);
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    chooser.setMultiSelectionEnabled(false);
    filter = new TXTFileFilter();
    if (selectedFile != null) {
      chooser.setSelectedFile(selectedFile);
    }
    chooser.setDialogTitle("输出清单至...");
    chooser.setFileFilter(filter);
    //删除系统自带的AcceptAllFileFilter
    chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
    //“打开”模式文件选择器中选择了批准按钮还是取消按钮
    setCursor(Cursor.getDefaultCursor());
    int state = chooser.showSaveDialog(null);
    if (state == chooser.CANCEL_OPTION) {
      this.setVisible(false);
    }
    //获得选择的文件
    selectedFile = chooser.getSelectedFile();
    //如果选择了批准按钮,则获得所选择文件名
    if (selectedFile != null && state == JFileChooser.APPROVE_OPTION) {
      //文件格式转换
      selectedFile = this.translateFile(selectedFile, filter);

      //判断 1 未输入文件名,请输入文件名称  2 指定的路径不存在或不可用 3 文件已存在,请重新指定文件名
      if (!filter.accept(selectedFile)) {
        JOptionPane.showMessageDialog(this,
                                      "路径不存在!",
                                      QMMessage.getLocalizedMessage(RESOURCE,
            "error", null),
                                      JOptionPane.ERROR_MESSAGE);
        return null;
      }
      if (selectedFile.exists()) {
        JOptionPane.showMessageDialog(this, "文件已经存在！",
                                      QMMessage.getLocalizedMessage(RESOURCE,
            "warning", null),
                                      JOptionPane.WARNING_MESSAGE);
        return null;
      }
    }

    return selectedFile.getPath();
  }

  private File translateFile(File file, FileFilter filter) {
    String path = file.getPath();
    if (!path.endsWith(".csv")) {
      path = path + ".csv";
    }
    return new File(path);
  }

  public class TXTFileFilter
      extends FileFilter {

    /**
     * 构造文本文件过滤器
     */
    public TXTFileFilter() {
    }

    /**
     * 判断指定的文件是否被本过滤器接受
     * @param f 文件
     * @return 如果接受，则返回true
     */
    public boolean accept(File f) {
      boolean accept = f.isDirectory();
      if (!accept) {
        String suffix = getSuffix(f);
        if (suffix != null) {
          accept = suffix.equals("csv");
        }
      }
      return accept;
    }

    /**
     * 获得本过滤器的描述信息
     * @return Text Files(*.csv)
     */
    public String getDescription() {
      return "Text Files(*.csv)";
    }

    /**
     * 获得指定文件的后缀
     * @param f File
     * @return 文件的后缀
     */
    private String getSuffix(File f) {
      String s = f.getPath(), suffix = null;
      int i = s.lastIndexOf('.');
      if (i > 0 && i < s.length() - 1) {
        suffix = s.substring(i + 1).toLowerCase();
      }
      return suffix;
    }
  }
}

class LogicBomFrame_cancelJButton_actionAdapter
    implements ActionListener {
  private LogicBomFrame adaptee;
  LogicBomFrame_cancelJButton_actionAdapter(LogicBomFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelJButton_actionPerformed(e);
  }
}

class LogicBomFrame_okJButton_actionAdapter
    implements ActionListener {
  private LogicBomFrame adaptee;
  LogicBomFrame_okJButton_actionAdapter(LogicBomFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okJButton_actionPerformed(e);
  }
}

class LogicBomFrame_exportButton_actionAdapter
    implements ActionListener {
  private LogicBomFrame adaptee;
  LogicBomFrame_exportButton_actionAdapter(LogicBomFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.exportButton_actionPerformed(e);
  }
}
