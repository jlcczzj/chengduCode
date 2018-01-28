/** 生成程序ProcedureUsageDrawingPanel.java      1.1  2006/3/7
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2009/04/05 刘志城    原因：优化工序、工步查看，减少查询数据库次数。
 *                         方案：避免刷简图值对象。
 *                         备注：性能测试用例名称："工艺树节点切换（大简图工序、工步查看）"。
 *                               由于该类逻辑几乎全部改变，所以没有具体标注。
 * CR2 2009/04/30 薛凯      原因：内容更新才提示是否保存。
 *                         方案：增加监听检测内容是否更新。
 *                         备注：CRSS-004
 * CR3 2009/05/31 徐春英   参见DefectID=2164 
 * SS1 工序工步关联简图,添加顺序号 liuyang 2014-4-1
 * SS2 由编辑简图引起的丢图问题 liunan 2014-6-12
 * SS3 由编辑、查看简图引起的多图问题 liunan 2014-6-12
 * SS4 编辑简图后引起丢图、多图等问题，改为初始化界面时删除生成的dxf临时文件 xuekai 2014-6-19
 * SS5 保存多图问题  leixiao 2015-3-23 
 * SS6 成都工步关联资源添加工序资源功能 guoxiaoliang 2016-8-2
 */
   
package com.faw_qm.cappclients.capp.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import com.faw_qm.capp.model.PDrawingIfc;
import com.faw_qm.capp.model.PDrawingInfo;
import com.faw_qm.capp.model.QMProcedureDrawingLinkIfc;
import com.faw_qm.capp.model.QMProcedureDrawingLinkInfo;
import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.cappclients.beans.drawingpanel.ToolSelectedDialog;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.resource.support.model.DrawingIfc;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import com.faw_qm.persist.util.*;

//CCBegin SS1
import java.util.Arrays;
import javax.swing.JTextField;
//CCEnd SS1
/**
 * <p>Title:工序使用简图客户端bean</p>
 * <p>Description: 此panel用来维护工序使用简图的关联关系
 * </p> 维护两种简图关系，工序简图和资源简图
 * 现在没有考虑到资源简图变为工序简图的状况
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 一汽启明</p>
 * @author lil
 * @version 1.0
 */
public class ProcedureUsageDrawingPanel
    extends ParentJPanel {
  private ComponentMultiList cappMultiList = new ComponentMultiList();
  private JButton addButton = new JButton();
  private JButton editButton = new JButton();
  private JButton viewButton = new JButton();
  private JButton deleteButton = new JButton();
  private JLabel jLabel1 = new JLabel();

  /**用于标记资源文件路径*/
  protected static String RESOURCE
      = "com.faw_qm.cappclients.capp.util.CappLMRB";
  private JFrame parentJFrame;
  private ResourceBundle rb = ResourceBundle.getBundle(
      "com.faw_qm.cappclients.beans.drawingpanel.DrawingPanelRB",
      RemoteProperty.getVersionLocale());

  //选择简图类型对话框
  private ToolSelectedDialog selectDialog;

  /**bean的工作模式标识*/
  private int model = 2;

  /**查看模式*/
  public static int VIEW = 1;

  /**编辑模式*/
  public static int EDIT = 2;

  //简图，键是行号，值：图，即数据库中保存的向量
  private HashMap drawingDataTable = new HashMap();

  //点击的按钮，0为添加，1为编辑，2为查看，3为删除
  private int selectedButton;

  //存放图页形式，键：代码内容，值：代码项，用于存储在简图对象的字段中
  private HashMap drawingFormatHash;

  //工序（工步)值对象
  private QMProcedureIfc procedure;

  //图页形式的集合，用在表格中的组合框，供用户选择
  private Vector drawingFormatVec;

  //工序或者工步值对象
  private QMProcedureIfc info;

  //为可更新的对象，如果是工序简图，则为简图对象，如果是资源简图，则为关联对象
  private ArrayList updatedLinks;

  //为可删除的对象，如果是工序简图，则为简图对象，如果是资源简图，则为关联对象
  private ArrayList removedLinks;

  //为数据缓存，键是行号，值如果是工序简图，则为简图对象，如果是资源简图，则为关联对象
  private HashMap rowMap;
  
  
  //CCBegin SS6
  boolean isprocedure = false;
  JButton getProDrawButton = new JButton();
  private boolean modifyFlag = false;
  private String newbsoid = "";
  //CCEnd SS6

  /**
   * 构造器
   * @param parentFrame JFrame 主窗口
   */
  public ProcedureUsageDrawingPanel(JFrame parentFrame) {
    this.parentJFrame = parentFrame;
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 构造器
   * @param parentFrame JFrame 主窗口
   * @param isprocedure boolean 是否为工序
   */
  public ProcedureUsageDrawingPanel(JFrame parentFrame, boolean isprocedure) {
	  
	  //CCBegin SS6
	  
	  this.isprocedure = isprocedure;
	  //CCEnd SS6
	  
    parentJFrame = parentFrame;
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * 初始化
   * @throws Exception
   */
  private void jbInit() throws Exception {
    this.setLayout(new GridBagLayout());
    this.add(cappMultiList, new GridBagConstraints(0, 0, 1, 7, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(10, 10, 35, 0), 119, 130));
    this.add(addButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.NONE,
                                               new Insets(8, 8, 0, 8), 0, 0));
    this.add(editButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(8, 8, 0, 8), 0, 0));
    this.add(viewButton, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(8, 8, 0, 8), 0, 0));
    this.add(deleteButton, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.NONE,
                                                  new Insets(8, 8, 0, 8), 0, 0));
    this.add(jLabel1, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
                                             , GridBagConstraints.CENTER,
                                             GridBagConstraints.BOTH,
                                             new Insets(0, 0, 0, 0), 0, 0));
    //CCBegin  SS1  添加顺序号列的监听
    cappMultiList.addActionListener(new java.awt.event.
            ActionListener()
    {
    	public void actionPerformed(ActionEvent e)
    	{
    		cappMultiList_actionPerformed(e);
    	}
    });
	  
    //CCBegin SS6
    if (!isprocedure) {
    	  this.add(getProDrawButton, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
    	      , GridBagConstraints.CENTER, GridBagConstraints.NONE,
    	      new Insets(8, 8, 0, 8), 0, 0));
    	}
    //CCEnd SS6
	  
  //CCEnd  SS1
    localize();
    updatedLinks = new ArrayList();
    removedLinks = new ArrayList();
    rowMap = new HashMap();
  }

  //CCBegin  SS1
  /**
   * 顺序号验证
   */
  public void cappMultiList_actionPerformed(ActionEvent e)
  {
	  int row = 0;
	  int col = 0;
      Object obj = e.getSource();
      String actionCommand = e.getActionCommand();
      int t = actionCommand.indexOf(";");
      if (t != -1)
      {
          //得到行
          String rowString = actionCommand.substring(0, t);
          int t1 = rowString.indexOf(":");
          row = Integer.parseInt(rowString.substring(t1 + 1, rowString.length()));
          //得到列
          String colString = actionCommand.substring(t + 1,
                  actionCommand.length());
          int t2 = colString.indexOf(":");
          col = Integer.parseInt(colString.substring(t2 + 1, colString.length()));

      }


      
      //如果选中的是顺序号列,列中的数据格式不符合要求则自动赋初值"1"
      if (col == 0)
      {
          if (obj instanceof JTextField)
          {
              JTextField textField = (JTextField) obj;
              String number = textField.getText().trim();
              if(number ==null || number.equals(""))
              {
            	  
            	  JOptionPane.showMessageDialog(null, "顺序号不能为空");
            	  cappMultiList.addTextCell(row, col, "1");
              }
              try
              {
                  int f = Integer.parseInt(number);
                  if(f<=0)
                  {
                	  JOptionPane.showMessageDialog(null, "顺序号不能为零或者负数"); 
                	  cappMultiList.addTextCell(row, col, "1");
                  }

              }
              catch (NumberFormatException ex)
              {
            	  cappMultiList.addTextCell(row, col, "1");
            	  JOptionPane.showMessageDialog(null, "顺序号必须是大于零的正整数");

              }
          }
      }
  }
  //CCEnd  SS1
  
  /**
   * 文字信息本地化
   */
  protected void localize() {
    addButton.setMaximumSize(new Dimension(89, 23));
    addButton.setMinimumSize(new Dimension(89, 23));
    addButton.setPreferredSize(new Dimension(89, 23));
    addButton.setText("添加(F)...");
    addButton.addActionListener(new java.awt.event.
                                ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addButton_actionPerformed(e);
      }
    });
    addButton.setMnemonic('F');
    editButton.setMaximumSize(new Dimension(89, 23));
    editButton.setMinimumSize(new Dimension(89, 23));
    editButton.setPreferredSize(new Dimension(89, 23));
    editButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
        "editJButton", null));
    editButton.setText("编辑(J)...");
    editButton.addActionListener(new java.awt.event.
                                 ActionListener() {
      public void actionPerformed(ActionEvent e) {
        editButton_actionPerformed(e);
      }
    });
    editButton.setMnemonic('J');
    viewButton.setMaximumSize(new Dimension(89, 23));
    viewButton.setMinimumSize(new Dimension(89, 23));
    viewButton.setPreferredSize(new Dimension(89, 23));
    viewButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
        "jMenuSelectView", null));
    viewButton.addActionListener(new java.awt.event.
                                 ActionListener() {
      public void actionPerformed(ActionEvent e) {
        viewButton_actionPerformed(e);
      }
    });
    viewButton.setMnemonic('V');
    deleteButton.setMaximumSize(new Dimension(89, 23));
    deleteButton.setMinimumSize(new Dimension(89, 23));
    deleteButton.setPreferredSize(new Dimension(89, 23));
    deleteButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
        "jMenuSelectDelete", null));
    deleteButton.addActionListener(new java.awt.event.
                                   ActionListener() {
      public void actionPerformed(ActionEvent e) {
        deleteButton_actionPerformed(e);
      }
    });
    deleteButton.setMnemonic('D');
    
    //CCBegin SS6
    
    getProDrawButton.setMaximumSize(new Dimension(89, 23));
    getProDrawButton.setMinimumSize(new Dimension(89, 23));
    getProDrawButton.setPreferredSize(new Dimension(89, 23));
    getProDrawButton.setText("工序资源(X)");
//    getProDrawButton.addActionListener(new
//                                       ProcedureUsageDrawingPanel_getProDrawButton_actionAdapter(this));
    getProDrawButton.setMnemonic('X');
    //CCEnd SS6

    //CCBegin SS1
//    String[] label = new String[7];
    String[] label = new String[8];
    //CCEnd SS1
    label[0] = "简图";
    label[1] = "类型";
    label[2] = "简图资源编号";
    label[3] = "图页形式";
    label[4] = "关联简图资源";
    //此列为bsoid，如果是工序简图则为对象bsoid，如果是资源简图，则是关联bsoid
    label[5] = "bsoid";
    label[6]= "relationbsoid";
    //CCBegin SS1
    label[7]= "顺序号";
 
    //CCEnd SS1
    cappMultiList.setHeadings(label);
    //CCBegin SS1
//    cappMultiList.setRelColWidth(new int[] {1, 1, 1, 1, 1, 0,0});
    cappMultiList.setRelColWidth(new int[] {1, 1, 1, 1, 1, 0,0,1});
    //CCEnd SS1
    cappMultiList.setAllowSorting(false);
    cappMultiList.setColsEnabled(new int[] {0, 1, 2, 4}
                                 , false);
    cappMultiList.setMultipleMode(true);
  }

  /**
   * 添加按钮的执行事件
   * @param e ActionEvent
   */
  private void addButton_actionPerformed(ActionEvent e) {
    selectedButton = 0;
    int result = JOptionPane.showConfirmDialog(parentJFrame,
                                               "您要创建简图么？", rb.getString("2"),
                                               JOptionPane.YES_NO_OPTION,
                                               JOptionPane.INFORMATION_MESSAGE);
    if (result == JOptionPane.YES_OPTION) {
    	//CCBegin SS6
    	this.modifyFlag = true;
    	//CCEnd SS6
      EditThread thread = new EditThread();
      thread.start();
    }
  }

  /**
   * 编辑按钮的执行事件
   * @param e ActionEvent
   */
  private void editButton_actionPerformed(ActionEvent e) {
    if (cappMultiList.getSelectedRow() == -1) {
      return;
    }
    int row = cappMultiList.getSelectedRow();
    //当前选中的行是资源简图，且关联简图资源被选中，则不可编辑
    if (!cappMultiList.getCellText(row, 2).equals("")) {
      if ( ( (Boolean) cappMultiList.getCellAt(row, 4).getValue()).
          booleanValue()) {
        JOptionPane.showMessageDialog(parentJFrame, "资源简图请在资源模块中维护！", "提示",
                                      JOptionPane.INFORMATION_MESSAGE);
        return;
      }
    }
    selectedButton = 1;
    int result = JOptionPane.showConfirmDialog(parentJFrame,
                                               "您要编辑简图么？", rb.getString("2"),
                                               JOptionPane.YES_NO_OPTION,
                                               JOptionPane.INFORMATION_MESSAGE);
    if (result == JOptionPane.YES_OPTION) {
    	//CCBegin SS6
    	this.modifyFlag = true;
    	//CCEnd SS6
      EditThread thread = new EditThread();
      thread.start();
    }
  }

  /**
   * 查看按钮的执行事件
   * @param e ActionEvent
   */
  private void viewButton_actionPerformed(ActionEvent e) {
    int selectedRow = cappMultiList.getSelectedRow();
    if (selectedRow == -1) {
      return;
    }
    selectedButton = 2;
    Vector data = new Vector();
    if(drawingDataTable.containsKey(String.valueOf(selectedRow))){
    	data = (Vector) drawingDataTable.get(String.valueOf(selectedRow));
    }else{
    	BaseValueIfc bvi = null;
    	 try{
    		 bvi=  (BaseValueIfc)CappClientHelper.refresh((String)(cappMultiList.getCellText(selectedRow, 5)));
    	    }
    	 catch(QMRemoteException ee){
    		 ee.printStackTrace();
    	 }
    	 if (bvi instanceof DrawingIfc)
		    {
    		 data .add(0,((DrawingIfc) bvi).getDrawingByte());
    		 data .add(1,((DrawingIfc) bvi).getDrawingStreamType());
		    }
		 else 
		    if (bvi instanceof PDrawingIfc)
		    {
		      data .add(0,((PDrawingIfc) bvi).getDrawingByte());
		      data .add(1,((PDrawingIfc) bvi).getDrawingType());
		    }
    	 drawingDataTable.put(String.valueOf(selectedRow), data);
    	 rowMap.put(String.valueOf(selectedRow), bvi);
    }
     
    if (data != null) {
      if (selectDialog == null) {
        selectDialog = new ToolSelectedDialog(parentJFrame, "", true);
      }
      selectDialog.setDrawingData(data);
      //查看简图
      selectDialog.view();
    }
  }

  /**
   * 删除按钮的执行事件
   * @param e ActionEvent
   */
  private void deleteButton_actionPerformed(ActionEvent e) {
    selectedButton = 3;
    //CCBegin SS6
    this.modifyFlag = true;
    //CCEnd SS6
    int[] rows = cappMultiList.getSelectedRows();
    for (int rownumber = rows.length; rownumber > 0; rownumber--) {
      int row = rows[rownumber - 1];
      if (row != -1) {
        drawingDataTable.remove(String.valueOf(row));
        BaseValueIfc bvi = (BaseValueInfo) rowMap.get(String.valueOf(row));
        if(cappMultiList.getCellText(row, 5).startsWith("Drawing")){
        	removedLinks.add(cappMultiList.getCellText(row, 6));
        }
        else if(cappMultiList.getCellText(row, 5).startsWith("PDrawing")){
        	if(bvi == null){
                try{
               		 bvi=  (BaseValueIfc)CappClientHelper.refresh((String)(cappMultiList.getCellText(row, 5)));
               	    }
               	 catch(Exception ee){
               		 ee.printStackTrace();
               	 }
                }//end == null	
        	if (bvi!=null&&bvi.getBsoID() != null && !bvi.getBsoID().equals("")) {
        		//CCBegin by liunan 2011-01-20 修改定制 无法保存简图类型的问题时，改的引起bvi传来的是link而不是PDrawing。
                if (bvi instanceof PDrawingIfc)
                {
                	removedLinks.add(bvi);
                	}
                //CCEnd by liunan 2011-01-20
                removedLinks.add((String)(cappMultiList.getCellText(row, 6)));
              }
        	  //如果删除的对象没有被持久化,就没有必要放入到可删除的集合中
              if (updatedLinks.contains(bvi)) {
                updatedLinks.remove(bvi);
              }
        } //end pdrawing    
        else{
        	if (updatedLinks.contains(bvi)) {
                updatedLinks.remove(bvi);
              }
        }
        rowMap.remove(String.valueOf(row));
        int rowcount = cappMultiList.getRowCount();
        if ( (rowcount - 1) > row) {
          for (int i = row + 1; i <= (rowcount - 1); i++) {
            Object obj = drawingDataTable.get(String.valueOf(i));
            drawingDataTable.remove(String.valueOf(i));
            drawingDataTable.put(String.valueOf(i - 1), obj);
            cappMultiList.addTextCell(i, 0, "简图" + i);
            BaseValueInfo bv = (BaseValueInfo) rowMap.get(String.valueOf(i));
            rowMap.remove(String.valueOf(i));
            rowMap.put(String.valueOf(i - 1), bv);
          } //for循环结束
        }
        cappMultiList.removeRow(row);
      }
    } //最外层for循环结束
    //log();输出可更新对象，可删除对象，行缓存。
  }

  /**
   * 删除编辑时的本地文件
   */
  private synchronized void deleteEditTempFile() {
    File dxfFile = new File(ToolSelectedDialog.tempDxfPath);
    if (dxfFile.exists()) {
      dxfFile.delete();
    }
  }

  /**
   * 设置图页形式,即为半图和全图
   */
  private void setDrawingFormat() {
    drawingFormatVec = new Vector();
    drawingFormatHash = new HashMap();
    Class[] paraclass = new Class[] {
        String.class, String.class};
    Object[] paraobj = new Object[] {
        "图页形式", "模板"};
    Collection col = null;
    try {
      col = (Collection) useServiceMethod("CodingManageService",
                                          "findDirectClassificationByName",
                                          paraclass, paraobj);
    }
    catch (QMRemoteException ex) {
      ex.printStackTrace();
    }  
    Iterator iterator = col.iterator();
    while (iterator.hasNext()) {
      CodingIfc info1 = (CodingIfc) iterator.next();
      if (info1.getCodeContent().equals("细节页")) {
        continue;
      }
      drawingFormatVec.add(info1.getCodeContent());
      drawingFormatHash.put(info1.getCodeContent(),
                            info1);
    }
  }

  /**
   * 设置工序对象
   * @param ifc QMProcedureIfc
   */
  public void setQMProcedureIfc(QMProcedureIfc ifc) {
    info = ifc;
  }

  /**
   *   内部类，添加线程
   */
  class EditThread
      extends Thread {
    /**
     * 添加资源
     */
    public EditThread() {

    }

    public void run() {
      Dimension screenSize = Toolkit.getDefaultToolkit().
          getScreenSize();
      selectDialog = new ToolSelectedDialog(parentJFrame, "", true);
      selectDialog.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Object ob = e.getSource();
          if (ob != null && ob instanceof Vector) {
            addPDrawing( (Vector) ob);
          }
        }
      });
      //CCBegin SS4
      selectDialog.deleteTempFile();
      //CCEnd SS4
      //编辑按钮选中时，设置简图数据
      if (selectedButton == 1) {
    	  int r = cappMultiList.getSelectedRow();
    	  Vector drawing = new Vector();
    	  if(drawingDataTable.containsKey(String.valueOf(r))){
    		  drawing = (Vector) drawingDataTable.get(String.valueOf(r));
    	    }else{
    	    	BaseValueIfc bvi = null;
    	    	 try{
    	    		 bvi=  (BaseValueIfc)CappClientHelper.refresh((String)(cappMultiList.getCellText(r, 5)));
    	    	    }
    	    	 catch(QMRemoteException ee){
    	    		 ee.printStackTrace();
    	    	 }
    	    	 if (bvi instanceof DrawingIfc)
    			    {
    	    		 drawing .add(0,((DrawingIfc) bvi).getDrawingByte());
    	    		 drawing .add(1,((DrawingIfc) bvi).getDrawingStreamType());
    			    }
    			 else 
    			    if (bvi instanceof PDrawingIfc)
    			    {
    			    	drawing .add(0,((PDrawingIfc) bvi).getDrawingByte());
    			    	drawing .add(1,((PDrawingIfc) bvi).getDrawingType());
    			    }
    	    	 drawingDataTable.put(String.valueOf(r), drawing);
    	    	 rowMap.put(String.valueOf(r), bvi);
    	    }  
        selectDialog.setDrawingData(drawing);
      }
      selectDialog.setLocation( (int) (screenSize.getWidth() -
                                       selectDialog.getWidth()) / 2,
                               (int) (screenSize.getHeight() -
                                      selectDialog.getHeight()) / 2);
      selectDialog.setVisible(true);
    }
  }

  /**
   * 往multiList中添加简图数据,用于创建或编辑简图之后,只限于工序简图
   * @param drawing Vector 简图数据
   */
  private void addPDrawing(Vector drawing) {
    //创建时
    if (selectedButton == 0) {
      String picType = (String) drawing.elementAt(1);
      int row = cappMultiList.getRowCount();
      cappMultiList.addTextCell(row, 0, "简图" + (row + 1));
      cappMultiList.addTextCell(row, 1, picType);
      cappMultiList.addTextCell(row, 2, "");
      //添加图页形式
      if (drawingFormatVec == null) {
        setDrawingFormat();
      }
      //CCBegin by liunan 2011-01-21 改成 “全图页” 为默认
      //String drawingFormat = (String) drawingFormatVec.get(0);
      String drawingFormat = (String) drawingFormatVec.get(1);
      //CCEnd by liunan 2011-01-21
      if (drawingFormatVec != null) {
        cappMultiList.addComboBoxCell(row, 3,
                                      drawingFormat,
                                      drawingFormatVec);
      }
      else {
        JOptionPane.showMessageDialog(parentJFrame,
                                      "代码管理中的图页形式为空！", "提示",
                                      JOptionPane.INFORMATION_MESSAGE);
      }
      cappMultiList.addTextCell(row, 4, "");
      cappMultiList.addTextCell(row, 5, "");
      //CCBegin  SS1
      cappMultiList.addTextCell(row, 7,row +1+ "");
    //CCEnd  SS1
      PDrawingInfo pdrawing = new PDrawingInfo();
      pdrawing.setDrawingFormat( (CodingIfc) drawingFormatHash.get(
          drawingFormat));
      pdrawing.setDrawingByte((byte[])drawing.elementAt(0));
      pdrawing.setDrawingType((String)drawing.elementAt(1));
      //如果是cad图
      if (picType.equals("Autocad")) {
        try {
          String[] strs = getLengthAndWidth(picType);
          pdrawing.setLength(strs[0]);
          pdrawing.setWidth(strs[1]);
          pdrawing.setX(strs[2]);
          pdrawing.setY(strs[3]);
        }
        catch (QMException ex) {
          ex.printStackTrace();
        }
        catch (IOException ee) {
          ee.printStackTrace();
        }
      }
      updatedLinks.add(pdrawing);
      rowMap.put(String.valueOf(row), pdrawing);
      drawingDataTable.put(String.valueOf(row), drawing);
    }
    //编辑时
    else
    if (selectedButton == 1) {
      int row = cappMultiList.getSelectedRow();
      String picType = (String) drawing.elementAt(1);
      drawingDataTable.put(String.valueOf(row), drawing);
      BaseValueInfo bvi = (BaseValueInfo)rowMap.get(String.valueOf(row));
      if (bvi !=null&&bvi.getBsoID()!= null && !bvi.getBsoID().equals("")) {
        //CCBegin SS2
        //removedLinks.add(bvi);
        //CCEnd SS2
        removedLinks.add((String) cappMultiList.getCellText(row, 6));
      }
      else {
        if (updatedLinks.contains(bvi)) {
          updatedLinks.remove(bvi);
        }
      }
      PDrawingInfo pdrawing = new PDrawingInfo();
      String drawingFormatConten = (String) cappMultiList.getCellAt(
          row, 3).getValue();
      pdrawing.setDrawingFormat( (CodingIfc) drawingFormatHash.get(
          drawingFormatConten));
      pdrawing.setDrawingByte((byte[])drawing.elementAt(0));
      pdrawing.setDrawingType((String)drawing.elementAt(1));
      if (picType.equals("Autocad")) {
        try {
          String[] strs = getLengthAndWidth(picType);
          pdrawing.setLength(strs[0]);
          pdrawing.setWidth(strs[1]);
          pdrawing.setX(strs[2]);
          pdrawing.setY(strs[3]);
        }
        catch (QMException ex) {
          ex.printStackTrace();
        }
        catch (IOException ee) {
          ee.printStackTrace();
        }
      }
      updatedLinks.add(pdrawing);
      rowMap.put(String.valueOf(row), pdrawing);
    }
    log();
  }

  /**
   * 添加资源简图，从资源树上添加
   * @param drawingIfc DrawingIfc 资源简图
   */
  public void addDrawingToTable(DrawingIfc drawingIfc) {
    int row = cappMultiList.getRowCount();
    cappMultiList.addTextCell(row, 0, "简图" + (row + 1));
    if (drawingIfc.getDrawingStreamType() == null) {
      cappMultiList.addTextCell(row, 1, "");
    }
    else {
      cappMultiList.addTextCell(row, 1,
                                (String) drawingIfc.getDrawingStreamType());
    }
    cappMultiList.addTextCell(row, 2, drawingIfc.getDrawingNumber());
    //添加图页形式
    if (drawingFormatVec == null) {
      setDrawingFormat();
    }
    String drawingformat = (String) drawingFormatVec.get(0);
    if (drawingFormatVec != null) {
      cappMultiList.addComboBoxCell(row, 3, drawingformat, drawingFormatVec);
    }
    else {
      JOptionPane.showMessageDialog(parentJFrame,
                                    "代码管理中的图页形式为空！", "提示",
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    cappMultiList.addCheckboxCell(row, 4, true);
    cappMultiList.addTextCell(row, 5, drawingIfc.getBsoID());
    cappMultiList.addTextCell(row, 6, "");
    //CCBegin  SS1
    cappMultiList.addTextCell(row, 7, row + 1 + "");
  //CCEnd  SS1
    QMProcedureDrawingLinkInfo link = new
        QMProcedureDrawingLinkInfo();  
    link.setDrawingFormat( (CodingIfc) drawingFormatHash.get(
        drawingformat));
    link.setRightBsoID(drawingIfc.getBsoID());
    if (procedure != null) {
      link.setLeftBsoID(procedure.getBsoID());
    }
    link.setDrawingNumber(drawingIfc.getDrawingNumber());//CR3
    link.setIsDrawingLink(true);
    link.setDrawingType((String) drawingIfc.getDrawingStreamType());
    updatedLinks.add(link);
    rowMap.put(String.valueOf(row), link);
    log();
  }

  /**
   * 设置界面模式
   * @param mode int 界面模式
   */
  public void setModel(int mode) {
    model = mode;
    if (model == VIEW) {
      setEditMode(false);
    }
    else {
      setEditMode(true);
    }
  }

  /**
   * 设置组件状态
   * @param flag boolean 是否可编辑或可见
   */
  private void setEditMode(boolean flag) {
    if (flag) {
      cappMultiList.setColsEnabled(new int[] {0, 1, 2}
                                   , false);
      //CCBegin SS1
//      cappMultiList.setColsEnabled(new int[] {3, 4}
//                                   , true);

      cappMultiList.setColsEnabled(new int[] {3,4,7}
      , true);
      //CCEnd SS1
    }
    else {
      cappMultiList.setColsEnabled(new int[] {0, 1, 2, 3, 4}
                                   , false);
    }
    //CCBegin SS6
    getProDrawButton.setVisible(flag);
    //CCEnd SS6
    addButton.setVisible(flag);
    editButton.setVisible(flag);
    deleteButton.setVisible(flag);
  }
//CCBegin SS6
  void getProDrawButton_actionPerformed(ActionEvent e) {
//	  20090917 徐德政加，更新提示问题
	  this.modifyFlag = true;
//	  end add by mario in 20090917
//    setProcedure1(info);
  }
  //CCEnd SS6
  /**
   * 设置工序值对象
   * @param qmProcedure QMProcedureIfc 工序值对象
   */
  public void setProcedure(QMProcedureIfc qmProcedure) {
    procedure = qmProcedure;
    rowMap.clear();
    drawingDataTable.clear();
    cappMultiList.clear();
    if (qmProcedure.getBsoID() != null) {
    try { 
    	  Collection links= getRelations(procedure);
    	  QMProcedureDrawingLinkIfc procedureDrawLink;  
    	  Iterator i = links.iterator();
    	  //CCBegin  SS1
    	  int k=0;
    	  //CCEnd  SS1
  		  while (i.hasNext()) 
  		 {
  		    procedureDrawLink = (QMProcedureDrawingLinkIfc) i.next();
		    int rowCount = cappMultiList.getRowCount();
            cappMultiList.addTextCell(rowCount, 0,
                                      "简图" + (rowCount + 1));
            cappMultiList.addTextCell(rowCount, 1, procedureDrawLink.getDrawingType());
            cappMultiList.addTextCell(rowCount, 2, procedureDrawLink.getDrawingNumber());
            //添加图页形式
            if (drawingFormatVec == null)
            {
                setDrawingFormat();
            }
            if (drawingFormatVec != null)
            {
            	cappMultiList.addComboBoxCell(rowCount, 3,
            			procedureDrawLink.getDrawingFormat().
                        getCodeContent(),
                        drawingFormatVec);
            }
            else
            {
               JOptionPane.showMessageDialog(parentJFrame,
                          "代码管理中的图页形式为空！", "提示",
                          JOptionPane.INFORMATION_MESSAGE);
            }
            cappMultiList.addComboBoxCell(rowCount, 3,
            		procedureDrawLink.getDrawingFormat().getCodeContent(),
            		drawingFormatVec);  
            if (procedureDrawLink.getIsDrawingLink())
            {
            	cappMultiList.addCheckboxCell(rowCount, 4, procedureDrawLink.getIsDrawingLink());
            }
            else
            {
            	cappMultiList.addTextCell(rowCount, 4, "");
            }
            cappMultiList.addTextCell(rowCount, 5, procedureDrawLink.getRightBsoID());
            cappMultiList.addTextCell(rowCount, 6, procedureDrawLink.getBsoID());
            //CCBegin  SS1
            int seq =  procedureDrawLink.getSeq();
            if(seq==0){
            	k++;
            	seq = k;
            	
            }
            cappMultiList.addTextCell(rowCount, 7, seq+"");
            //CCEnd  SS1
            //CCBegin by liunan 2011-01-18 薛凯帮助修改，无法保存简图类型的问题
            //薛凯  解放定制   开始  2011/01/17
                    System.out.println("rowCount====" + rowCount + "====" + procedureDrawLink.getBsoID());
                    rowMap.put(String.valueOf(rowCount), procedureDrawLink);
                    //薛凯 解放定制 结束
            //CCEnd by liunan 2011-01-18
            log() ;
         } 
      }
      catch (QMException ex) {
        ex.printStackTrace();
      }
      //begin CR2
      finally {
        cappMultiList.intAfterThread();
        //CCBegin SS6
        this.modifyFlag = false;
        //CCEnd SS6
        log();
      }
      //end CR2
    }
  }

  //CCBegin SS6
  
  public boolean getModifyFlag() {
	    return this.modifyFlag;
	  }
  
  public void setModifyFlag(boolean flag) {
    modifyFlag = flag;
  }
  //CCEnd SS6
  
  /**
   * 清除界面信息
   */
  public void clear() {
    cappMultiList.clear();
    if (selectDialog != null) {
      selectDialog.clear();
    }
    procedure = null;
    drawingDataTable.clear();
    deleteEditTempFile();
  }

//CCBegin  SS1
  /**
   * 在保存工序或者工步时,验证顺序号是否重复
   */
  public boolean checkSeqNum() {
	  boolean flag = false;
	  int row = cappMultiList.getNumberOfRows();
	  Vector<Integer> v = new Vector<Integer>();
	  
	  for (int i = 0; i < row; i++) {
		  int seqNum = Integer.valueOf(cappMultiList.getCellText(i, 7));
		  v.addElement(seqNum);
	  }
	  
	  for(int j =0;j<v.size();j++)
	  {
		  int seqCount = v.get(j);
		  v.remove(j);
		  if(v.contains(seqCount))
		  {
			  flag = true;
			  JOptionPane.showMessageDialog(null, "顺序号重复,请修改简图关联中重复的顺序号");
			  break;
		  }
	  }
	  
	  return flag;
  }
 //CCEnd  SS1
  /**
   * 第一个元素是可更新的集合
   * 第二个元素是可删除的集合
   * @return Object[]
   */
  public Object[] getDrawings() {
  	//CCBegin by liunan 2011-01-18 薛凯帮助修改，无法保存简图类型的问题
  	//薛凯  解放定制   开始  2011/01/17
    	checkMutliLIstValue();
    	//薛凯 解放定制 结束
  	//CCEnd by liunan 2011-01-18
    Object[] obj = new Object[2];
	//CCBegin  SS1   
    updatedLinks.clear(); 
	  //将行号与行所对应的顺序号建立HashMap,键是顺序号,值是行号
		int i = cappMultiList.getTable().getModel().getRowCount();	
		HashMap rowSeq = new HashMap();
		int seq[] = new int[i];
			
			for(int j = 0; j< i ; j++)
			{
				//顺序号是第1列
				int seqNum =Integer.valueOf(cappMultiList.getCellText(j, 7)) ;
				seq[j] = seqNum;
		    	rowSeq.put(seqNum, j);
			}
			//重新排序
			Arrays.sort(seq);
			for( int n = 0; n < seq.length; n++)
			{
				int num =(Integer)rowSeq.get(seq[n]) ;
				//拿biosid, 在列表里第8列
				String ID = cappMultiList.getCellText(num, 6);
				if(ID != null && !(ID.equals("")))
				{
					//CCBegin SS3 SS5
					BaseValueIfc bvi = (BaseValueInfo) rowMap.get(String.valueOf(num));
					//如果有简图，并且简图没有bsoid，说明是新修改的，还没有持久化保存过，
					//这时需要将PDrawing加到updatedLinks中。
					if(bvi!=null){
					if((bvi instanceof PDrawingIfc) && bvi.getBsoID()==null)
					{
						updatedLinks.add(rowMap.get(String.valueOf(num)));
					}
					else
					//CCEnd SS3 SS5
					updatedLinks.add(ID);
					}
				}
					
				else{
					//CCBegin SS5
					BaseValueIfc bvi = (BaseValueInfo) rowMap.get(String.valueOf(num));
					if(bvi!=null)
					{
					updatedLinks.add(rowMap.get(String.valueOf(num)));
					}
					//CCEnd SS5
				}	
				
				
			}
//CCEnd  SS1
    obj[0] = updatedLinks;
    obj[1] = removedLinks;
    return obj;
  }

  /**
   * 清空关联
   */
  public void resetArrayList() {
    removedLinks = new ArrayList();
    updatedLinks = new ArrayList();
    //CCBegin SS5
    rowMap.clear();
    //CCEnd SS5
  }

  /**
   * 测试输出
   */
  private void log() {
    System.out.println("可删除的集合是__________" + removedLinks);
    System.out.println("可更新的集合是__________" + updatedLinks);
    System.out.println("行集合是__________" + rowMap);
  }

  /**
   * 通过文件_te_eo%fs$ie@o$%#.info获取简图的基本数据
   * @param type String
   * @throws QMException
   * @throws IOException
   * @throws CappException
   * @return String[] 包含简图的长，宽，x，y坐标
   */
  private String[] getLengthAndWidth(String type) throws QMException,
      IOException {
    String[] lengthandwidth = new String[4];
    String length = "";
    String width = "";
    String x = "";
    String y = "";
    //路径为c:/_te_eo%fs$ie@o$%#.info
    String path = rb.getString("11");
    File file = new File(path);
    if (!file.exists() && type.equals("Autocad")) {
      JOptionPane.showMessageDialog(this, "在C:/下未找到_te_eo%fs$ie@o$%#.info文件",
                                    "提示",
                                    JOptionPane.INFORMATION_MESSAGE);
      return null;
    }
    BufferedReader bufferReader = new BufferedReader(new FileReader(path));
    String temp = bufferReader.readLine();
    String temp1 = bufferReader.readLine();
    StringTokenizer token = new StringTokenizer(temp, ",");
    while (token.hasMoreElements()) {
      length = (String) token.nextElement();
      width = (String) token.nextElement();
    }
    StringTokenizer token1 = new StringTokenizer(temp1, ",");
    while (token1.hasMoreElements()) {
      x = (String) token1.nextElement();
      y = (String) token1.nextElement();
    }
    lengthandwidth[0] = length;
    lengthandwidth[1] = width;
    lengthandwidth[2] = x;
    lengthandwidth[3] = y;
    bufferReader.close();
    return lengthandwidth;
  }
  
  /**
	 * 根据当前对象查询与当前对象关联简图的关联类对象的集合。
	 * @return Collection 当前对象与简图的关联类对象的集合
	 * @throws QMRemoteException
	 * @throws ClassNotFoundException
	 */
	private Collection getRelations(QMProcedureIfc qmProcedure)
	        throws QMRemoteException    
	{
	    Collection queryresult = null;
	    try
	    {
	    	QMQuery query = new QMQuery("QMProcedureDrawingLink");
	    	query.addCondition(new QueryCondition("leftBsoID","=",qmProcedure.getBsoID()));
	    	//CCBegin  SS1 构造查询语句,从数据库中按照顺序号从小到大取数据
	    	query.addOrderBy("seq", false);
	    	//CCEnd  SS1
	        Class[] paraClass =
	               {QMQuery.class,Boolean.TYPE};
	        Object[] objs =
	               {query, new Boolean(false)}; 
	        queryresult = (Collection) useServiceMethod("PersistService",
	                "findValueInfo", paraClass, objs);
	    }
	    catch (QMException ex)
	    {
	        ex.printStackTrace();
	    }
	    return queryresult;
	}
	
	//CCBegin by liunan 2011-01-18 薛凯帮助修改，无法保存简图类型的问题
    //薛凯  解放定制   开始  2011/01/17
    private void checkMutliLIstValue()
    {
    	System.out.println("updatedLinks1=======" + updatedLinks);
    	System.out.println("rowMap1=======" + rowMap);
    	System.out.println("removedLinks1=======" + removedLinks);
    	int row = cappMultiList.getRowCount();
    	System.out.println("row=======" + row);
    	for(int i = 0; i < row; i++)
    	{
    		String df = cappMultiList.getCellAt(i, 3).getValue().toString();
    	System.out.println("df=======" + df);
    		CodingIfc code = (CodingIfc)drawingFormatHash.get(df);
    		BaseValueIfc bvi = (BaseValueIfc)rowMap.get(String.valueOf(i));
    		System.out.println("bvi===="+bvi);
    		if (bvi instanceof PDrawingIfc)
			{
    			PDrawingIfc pd = (PDrawingIfc)bvi;
    			int index = updatedLinks.indexOf(pd);;
    			pd.setDrawingFormat(code);
    			if(index != -1)
    			{
    				updatedLinks.remove(index);
    				updatedLinks.add(index, pd);
    			}
			}
			else if (bvi instanceof QMProcedureDrawingLinkIfc)
			{
				QMProcedureDrawingLinkIfc qmpdlink = (QMProcedureDrawingLinkIfc)bvi;
    			int index = updatedLinks.indexOf(qmpdlink);;
    			qmpdlink.setDrawingFormat(code);
    			if(index != -1)
    			{
    				updatedLinks.remove(index);
    				updatedLinks.add(index, qmpdlink);
    			}
    			else if(index == -1)
    			{
    				updatedLinks.add(qmpdlink);
    			}
			}
    	}
    	System.out.println("updatedLinks2=======" + updatedLinks);
    	System.out.println("rowMap2=======" + rowMap);
    	System.out.println("removedLinks2=======" + removedLinks);
    }
    //薛凯 解放定制 结束	
	//CCEnd by liunan 2011-01-18
    
  
    
    
}
