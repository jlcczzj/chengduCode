/** 生成程序NewLogicBomFrame.java	1.1  2007-2-8
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  * SS1 增加艺准说明和艺准状态 liunan 2015-12-3
  */
package com.faw_qm.part.client.other.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;

import com.faw_qm.clients.beans.explorer.QM;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.model.QMPartIfc;

/**
 *
********逻辑总成数量报表************
***************************************
** 上一级件为逻辑总成时,
**自动将零件数量与上一级逻辑总成数量相乘计算出实际装车数量
**leix 2010-12-21
 * @version 2.0
 */

public class NewLogicBomFrame extends JFrame
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
  //CCBegin SS1
  private JCheckBox hasRouteDes; //是否显示艺准说明属性
  //CCEnd SS1

  /**
   * 构造函数
   * @param c  控制器
   * @param part  零部件对象接口
   */
  public NewLogicBomFrame(QMPartIfc part,boolean bool)
  {
    try
    {
      this.partIfc = part;
      jbInit();
      //设置界面动态标题
      if(partIfc!=null)
      {
        this.setTitle("逻辑总成数量报表" + partIfc.getPartNumber()+" "+partIfc.getPartName());
        String iconImage = QMMessage.getLocalizedMessage("com.faw_qm.part.client.main.util.QMProductManagerRB", QMProductManagerRB.ICONIMAGE, null);
        setIconImage(new ImageIcon(QM.getIcon(iconImage)).getImage());
        PartScreenParameter.setLocationCenter(this);
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
	    okJButton.addActionListener(new NewLogicBomFrame_okJButton_actionAdapter(this));
	    cancelJButton.setText("取消"); //JLabel
	    cancelJButton.addActionListener(new
	                                    NewLogicBomFrame_cancelJButton_actionAdapter(this));
	    contentPane = (JPanel) this.getContentPane();
//	    titledBorder1 = new TitledBorder(QMMessage.getLocalizedMessage(RESOURCE, "out" , null));
	    titledBorder1 = new TitledBorder("输出(输出 :输出到本地csv格式  确定:输出到网页格式)");
	    contentPane.setLayout(gridBagLayout1);
	    this.setSize(new Dimension(400, 200));
	    actionJPanel.setBorder(titledBorder1);
	    
	    //CCBegin SS1
	    hasRouteDes = new JCheckBox("显示艺准说明");
	    hasRouteDes.setSelected(false);
	    //CCEnd SS1

	    //界面布局
	    actionJPanel.setLayout(gridBagLayout3);
	    buttonJPanel.setLayout(gridBagLayout4);
	    exportJPanel.setLayout(gridBagLayout2);
	    exportButton.setMaximumSize(new Dimension(65, 23));
	    exportButton.setMinimumSize(new Dimension(65, 23));
	    exportButton.setPreferredSize(new Dimension(65, 23));
	    exportButton.setSelectedIcon(null);
	    exportButton.setText("输出");
	    exportButton.addActionListener(new NewLogicBomFrame_exportButton_actionAdapter(this));
	    //CCBegin SS1
	    contentPane.add(hasRouteDes,       new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 0, 0));
	    contentPane.add(actionJPanel,       new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 0, 0));
	    //CCEnd SS1
	    actionJPanel.add(buttonJPanel,        new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
	            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
	    buttonJPanel.add(exportButton,  new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 8), 0, 0));
	    buttonJPanel.add(okJButton,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 8), 0, 0));
	    buttonJPanel.add(cancelJButton,     new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
	            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
//	    contentPane.add(statusJLabel,    new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
//	            ,GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));


  }

  /**
   * 导出Excel
   * @param e ActionEvent
   */
  public void exportButton_actionPerformed(ActionEvent e) {
    HashMap map = new HashMap();
    map.put("PartID", partIfc.getBsoID());
//    map.put("attributeName", getAttribute());
//    map.put("attributeName1", getAttribute1());
    //CCBegin by liunan 2011-08-08 添加生命周期状态
    //map.put("attributeName", "编号名称数量制造路线装配路线艺准编号");
    //map.put("attributeName1", "partNumber-0,partName-0,quantity-0,制造路线-2,装配路线-2,艺准编号-2,");
    //CCBegin SS1
    if(hasRouteDes.isSelected())
    {
    	map.put("attributeName", "编号名称数量生命周期状态制造路线装配路线艺准编号艺准说明艺准状态");
    	map.put("attributeName1", "partNumber-0,partName-0,quantity-0,lifeCycleState-0,制造路线-2,装配路线-2,艺准编号-2,艺准说明-2,艺准状态-2,");
    }
    else
    {
    	map.put("attributeName", "编号名称数量生命周期状态制造路线装配路线艺准编号");
    	map.put("attributeName1", "partNumber-0,partName-0,quantity-0,lifeCycleState-0,制造路线-2,装配路线-2,艺准编号-2,");
    }
    //CCEnd SS1
    //CCEnd by liunan 2011-08-08
    writeClassfyProcess(map);
    this.setVisible(false);
  }

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
            "StandardPartService", "getExportBOMClassfiyLogicString", c, objs);
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
    //CCBegin by liunan 2011-08-08 添加生命周期状态
    //map.put("attributeName", "编号名称数量制造路线装配路线艺准编号");
    //map.put("attributeName1", "partNumber-0,partName-0,quantity-0,制造路线-2,装配路线-2,艺准编号-2,");
    //CCBegin SS1
    if(hasRouteDes.isSelected())
    {
    	map.put("attributeName", "编号名称数量生命周期状态制造路线装配路线艺准编号艺准说明艺准状态");
    	map.put("attributeName1", "partNumber-0,partName-0,quantity-0,lifeCycleState-0,制造路线-2,装配路线-2,艺准编号-2,艺准说明-2,艺准状态-2,");
    }
    else
    {
    	map.put("attributeName", "编号名称数量生命周期状态制造路线装配路线艺准编号");
    	map.put("attributeName1", "partNumber-0,partName-0,quantity-0,lifeCycleState-0,制造路线-2,装配路线-2,艺准编号-2,");
    }
    //CCEnd SS1
    //CCEnd by liunan 2011-08-08
    RichToThinUtil.toWebPage("Part-Other-classifylisting-logic-001.screen", map);
    this.setVisible(false);
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

class NewLogicBomFrame_cancelJButton_actionAdapter
    implements ActionListener {
  private NewLogicBomFrame adaptee;
  NewLogicBomFrame_cancelJButton_actionAdapter(NewLogicBomFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelJButton_actionPerformed(e);
  }
}

class NewLogicBomFrame_okJButton_actionAdapter
    implements ActionListener {
  private NewLogicBomFrame adaptee;
  NewLogicBomFrame_okJButton_actionAdapter(NewLogicBomFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.okJButton_actionPerformed(e);
  }
}

class NewLogicBomFrame_exportButton_actionAdapter
    implements ActionListener {
  private NewLogicBomFrame adaptee;
  NewLogicBomFrame_exportButton_actionAdapter(NewLogicBomFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.exportButton_actionPerformed(e);
  }
}
