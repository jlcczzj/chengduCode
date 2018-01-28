/**
 * 修改说明
 * CR1  郭晓亮   2009/01/15    原因:对于工艺规程管理器工序或工步编辑界面的FEMA、
 *                                  过程流程图、控制计划的TAB页面下可编辑行支持
 *                                  整行的复制与粘贴功能。
 *                                  
 *                             方案:点击复制时获取选中行的行号，点击粘贴时按表
 *                                  格的列数循环,得到复制行每一列的值，并判断单
 *                                  元格是那种类型(下拉框或特殊字符)，然后将这些
 *                                  值设置到粘贴行的每一列。
 *                             
 *                             备注:变更记录标记"CRSS-002";
 *                             
 *CR2  郭晓亮    2009/06/04    测试域:v4r3FunctionTest;TD号2290
 * 
 * SS1 郭晓亮 2014-5-29 修改服务平台问题 A005-2014-2881；
 * 
 */


package com.faw_qm.cappclients.util;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.jview.chrset.SpeCharaterTextPanel;

/**
 * <p>Title:组件多行列表 </p>
 * <p>Description:继承CappQMMultiList类 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: faw_qm</p>
 * @author 管春元
 * @version 1.0
 */
//(1)20060815薛静修改，修改方法addSpeCharCell，原因打印要求特定字体


public class ComponentMultiList extends CappQMMultiList
{ //implements KeyListener
    private Font dialog11;
    private boolean AllCheckBoxesDisabled;
    private Vector checkboxesdisabled;

    private Vector keylinsteners = new Vector();


    //Begin CR1
    private PopupMenu listPopup = new PopupMenu();
    private MenuItem copy = new MenuItem("复制");
    private MenuItem affix = new MenuItem("粘贴");
    
    //控制是否显示复制粘贴菜单，true显示，false不显示
    private boolean isPopupMenu = false;
    private boolean isCopyState = true;
    private int copyRowIndex = -1;
    /**
     * 控制是否显示复制粘贴右键菜单
     * @param flag
     */
    public void setisShowPopupMenu(boolean flag){
    	isPopupMenu=flag;
    }
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

        if (isPopupMenu && e.getButton() == 3) {

          listPopup.show(this.getTable(), e.getX(), e.getY());//CR2
          if (isCopyState) {
            copy.setEnabled(true);
            affix.setEnabled(true);
          }

        }

      }
    
    //End CR1
    /**
     * 构选体一,主要功能是设制该表的初始状态
     */
    public ComponentMultiList()
    {
        //  super(4,false,Color.white,true);
        dialog11 = new Font("Dialog", 0, 11);
        AllCheckBoxesDisabled = false;
        try
        {
            setMultipleMode(false);
            setHeadingFont(dialog11);
            setCellFont(dialog11);
            setCellEditable(true);
            // table.addKeyListener(this);
            
          //Begin CR1
            add(listPopup);//向表格中添加菜单
            affix.setEnabled(false);
            
            //将菜单项添加到菜单中
            listPopup.add(copy);
            listPopup.add(affix);
            
            //为菜单项设置字体
            copy.setFont(new java.awt.Font("Dialog", 0, 12));
            affix.setFont(new java.awt.Font("Dialog", 0, 12));
            
            //为菜单项添加监听
            copy.addActionListener(new MenuActionListen());
            affix.addActionListener(new MenuActionListen());
            //End CR1
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        //addMouseListener(new CheckboxMouseListener());
    }
  //Begin CR1
    class MenuActionListen
        implements ActionListener {

      int selectRow = getSelectedRow();//粘贴的行

      public void actionPerformed(ActionEvent e) {

        if (e.getSource() == copy) {
          isCopyState = false;
          copyRowIndex = getSelectedRow();//复制行

        }
        else if (e.getSource() == affix) {

          isCopyState = false;
          int selectRow = getSelectedRow();//粘贴的行
          int colCount = getNumberOfCols();//列数

          try {
            if (copyRowIndex <= getNumberOfRows()) {
              for (int i = 0; i < colCount; i++) {

                String value = getStringValue(copyRowIndex, i);

                if (getComboBoxObject(copyRowIndex, i) != null) {
                	//获得下拉框的选中项
                  String comboxSelectedStr = (String) getComboBoxObject(
                      copyRowIndex, i);
                  //将下拉框中获得的选中项和所有可选项添加到指定单元格中
                  setComboBoxSelected(selectRow, i, comboxSelectedStr,
                                      getCellAt(copyRowIndex, i).
                                      getSelectableValues());
                  continue;

                }
                // 复制粘贴特殊字符
                else if (getCellAt(copyRowIndex, i).getType() == 5) {

                  Vector v = new Vector();
                  //获得指定单元格特殊字符的值
                  String str = getCellAt(copyRowIndex, i).getSpecialCharacter().save();
                  //判断获得的特殊字符植是否为空
                  if(null!=str&&(!str.trim().equals("")))
                    v.add(str);
                    
                  if (v.size() > 0) {
                    //清空单元格中原有的特殊字符
                    getCellAt(selectRow, i).getSpecialCharacter().clearAll();
                    //修改原单元格，将获得的的特殊字符值加到指定的单元格中
                    getCellAt(selectRow, i).getSpecialCharacter().resumeData(v);
                    // addSpeCharCell(selectRow, i, v);
                  }

                }
                
                else
                addTextCell(selectRow, i, value);
              }

            }
          }
          catch (Exception ex) {
            JOptionPane.showMessageDialog(
                null, "复制的行数不存在!");
          }

        }

      }
    }

  //End CR1


//public void setSpeChar
    /**
     * 设置CheckBox单元格是否可编辑
     * @param flag boolean true为可编辑
     */
    public void setCheckModel(boolean flag)
    {
        if (flag)
        {
            table.setDefaultEditor(CappTextAndImageCell.class,
                                   new MultiCellEditor(new JCheckBox()));
        }
    }


    /**
     * 设置所有表中的Checkbox复选框的不选状态
     * @param flag 如果为真则为不选
     */
    public void setAllCheckBoxesDisabled(boolean flag)
    {
        AllCheckBoxesDisabled = flag;
        if (AllCheckBoxesDisabled)
        {
            if (checkboxesdisabled == null)
            {
                checkboxesdisabled = new Vector();
            }
            else
            {
                checkboxesdisabled.removeAllElements();
            }
            int k1 = getNumberOfRows();
            int k2 = getNumberOfCols();
            for (int l1 = 0; l1 < k1; l1++)
            {
                for (int l2 = 0; l2 < k2; l2++)
                {
                    if ((getCellAt(l1, l2).getType() ==
                         CappTextAndImageCell.CHECKBOX) &&
                        isCheckboxSelected(l1, l2))
                    {
                        String s1 = getCellText(l1, l2);
                        setCheckboxSelected(l1, l2, false);
                        checkboxesdisabled.addElement(new Integer(l1));
                    }
                }
            }
        }
    }


    /**
     * 设置所有的表格中的文本域为不可编辑状态
     */
    public void setAllTextFieldDisabled()
    {
        int k1 = getNumberOfRows();
        int k2 = getNumberOfCols();
        for (int l1 = 0; l1 < k1; l1++)
        {
            for (int l2 = 0; l2 < k2; l2++)
            {
                if ((getCellAt(l1, l2).getType() == CappTextAndImageCell.TEXT))
                {
                    // System.out.println("no");
                }
            }
        }

    }


    /**
     * 设置所有的表格中的文本域为不可编辑状态
     */
    public void setAllRadionButtonDisabled()
    {
        int k1 = getNumberOfRows();
        int k2 = getNumberOfCols();
        for (int l1 = 0; l1 < k1; l1++)
        {
            for (int l2 = 0; l2 < k2; l2++)
            {
                if (!(getCellAt(l1, l2).getType() ==
                      CappTextAndImageCell.CHECKBOX))
                {
                    // System.out.println("no");
                }
            }
        }

    }
    /**
     * 上移指定行
     * @param i int数据
     */
    public void moveUp(int i)
    {
        this.getTableModel().moveRow(i, i, i - 1);
    }
    /**
     * 下移指定行
     * @param i int数据
     */
    public void moveDown(int i)
    {
        this.getTableModel().moveRow(i, i, i + 1);
    }


    /**
     * 返回不选状态复选框的行
     * @return 不选状态复选框的行(Vector表)
     */
    public Vector getCheckBoxesDisabled()
    {
        return checkboxesdisabled;
    }


    /**
     * 得到底i行第j列的的字符值
     * @param i 行
     * @param j 列
     * @return String 字符值
     */
    public String getStringValue(int i, int j)
    {
        return this.getCellAt(i, j).getStringValue();
    }


    /**
     * 设置底i行第j列的对象
     * @param i 行号
     * @param j 列号
     * @param type 单元格类型
     * @param s 单元格字符
     * @param value 单元格内容
     * @param values  复选框的可选内容，如果不是复选框，请传递null
     */
    public void setCellObject(int i, int j, int type, String s, Object value,
                              Vector values)
    {
        this.addCell(i, j, s, type, value, values);
    }


    /**
     * 设制复选框的新的选择状态
     * @param i 行号
     * @param j 列号
     * @param flag 选择状态
     */
    public void setCheckboxSelected(int i, int j, boolean flag)
    {
        String s = getCellText(i, j);
        if (flag)
        {
            addCheckBoxCell(i, j, s, true);
            return;
        }
        else
        {
            addCheckBoxCell(i, j, s, false);
            return;
        }
    }


    /**
     * 设制单选框的新的选择状态
     * @param i 行号
     * @param j 列号
     * @param flag 选择状态
     */
    public void setRadioButtonSelected(int i, int j, boolean flag)
    {
        String s = getCellText(i, j);
        if (flag)
        {
            addCheckBoxCell(i, j, s, true);
            return;
        }
        else
        {
            addCheckBoxCell(i, j, s, false);
            return;
        }
    }


    /**
     * 设置下拉框选中的对象
     * @param i 行号
     * @param j 列号
     * @param value 选中对象
     * @param values Vector 可选的对象集合
     */
    public void setComboBoxSelected(int i, int j, Object value, Vector values)
    {
        this.addComboBoxCell(i, j, value, values);
    }


    /**
     * 得到当前表格选中的对象
     * @param i 当前行数
     * @param j 当前列数
     * @return Object 选中的对象
     */
    public Object getSelectedObject(int i, int j)
    {
        CappTextAndImageCell cell = this.getCellAt(i, j);
        if (cell != null)
        {
            if (cell.getType() == CappTextAndImageCell.TEXT)
            {
                return cell.getStringValue();
            }
            //zz

            if (cell.getType() == CappTextAndImageCell.SPECIALCHARACTER)
            {
                Vector vector = new Vector(1);
                vector.addElement(cell.getSpecialCharacter().save());

                return vector;
            }
            return cell.getValue();
        }
        else
        {
            return null;
        }
    }


    /**
     * 返回第i 行,第j 列 复选框的选择状态
     * @param i 行号
     * @param j 列号
     * @return boolean 复选框的选择状态,true为选中
     */
    public boolean isCheckboxSelected(int i, int j)
    {
        return ((Boolean) getCellAt(i, j).getValue()).booleanValue();
    }


    /**
     * 返回第i 行,第j 列 单选框的选择状态
     * @param i 行号
     * @param j 列号
     * @return boolean 单选框的选择状态,true为选中
     */
    public boolean isRadioButtonSelected(int i, int j)
    {
        return ((Boolean) getCellAt(i, j).getValue()).booleanValue();
    }


    /**
     * 返回第i 行,第j 列 下拉框的选择对象
     * @param i 行号
     * @param j 列号
     * @return Object 下拉框的选择对象
     */
    public Object getComboBoxObject(int i, int j)
    {
        return getCellAt(i, j).getValue();
    }


    /**
     * 返回第i 行,第j 列 下拉框的字符
     * @param i 行号
     * @param j 列号
     * @return String 下拉框的字符
     */
    public String getComboBoxString(int i, int j)
    {
        return getCellAt(i, j).getStringValue();
    }


    /**
     * 在第i行j列添加单元
     * @param i 行号
     * @param j 列号
     * @param s 文本显示
     * @param type 单元类型 有4种：
     * CappTextAndImageCell.TEXT,文本类型
     * CappTextAndImageCell.CHECKBOX,   复选框
     * CappTextAndImageCell.COMBOBOX,  下拉框
     * CappTextAndImageCell.RADIOBUTTON 单选按钮
     * @param value 单元的值
     * @param vs  下拉框的可选值集合
     */
    public void addCell(int i, int j, String s, int type, Object value,
                        Vector vs)
    {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(s, type,
                value, vs);
        addCellImpl(i, j, checkboxcell);
    }


//zz
    /**
     * 在指定行和列中添加特殊字符单元
     * @param i int 行号
     * @param j int 列号
     * @param vevtor Vector 包含特殊字符数据源的矢量数组
     */
    public void addSpeCharCell(int i, int j, Vector vevtor)
    {
    	

        CappTextAndImageCell speChar = null;

        SpeCharaterTextPanel speCharPanel = new SpeCharaterTextPanel(this.
                getParentFrame());

        HashMap speHashmap = CappClientHelper.getSpechar();
        if (speHashmap != null)
        {

            speCharPanel.setDrawInfo(speHashmap);
        }
        speCharPanel.setFilePath(RemoteProperty.getProperty(
                "spechar.image.path"));

        //20060815薛静修改，原因打印要求
        speCharPanel.speCharaterTextBean.setFont(new java.awt.Font("Dialog",0, 18));
       // speCharPanel.speCharaterTextBean.setFont(new java.awt.Font("Dialog",
               // Font.CENTER_BASELINE, 18));
        speCharPanel.speCharaterTextBean.setRows(1);
        speCharPanel.speCharaterTextBean.setLineWrap(false);
        speCharPanel.resumeData(vevtor);
        speChar = new CappTextAndImageCell(speCharPanel,
                                           CappTextAndImageCell.
                                           SPECIALCHARACTER);

        addCellImplforSpeChar(i, j, speChar);

    }


    /**
     * 在第i行,第j 列 添加文本
     * @param i 第i行
     * @param j 第j列
     * @param s 新添加的文本
     */
    public void addTextCell(int i, int j, String s)
    {
        if (s == null)
        {
            s = new String("");
        }
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(s);
        addCellImpl(i, j, checkboxcell);
    }


    /**
     * 在第i行,第j列添加复选框
     * @param i 第i行
     * @param j 第j列
     * @param flag 是否为选中复选框
     */

    public void addCheckboxCell(int i, int j, boolean flag)
    {
        CappTextAndImageCell checkboxcell = null;
        if (flag)
        {
            checkboxcell = new CappTextAndImageCell(CappTextAndImageCell.
                    CHECKBOX, new Boolean(true), null);
        }
        else
        {
            checkboxcell = new CappTextAndImageCell(CappTextAndImageCell.
                    CHECKBOX, new Boolean(false), null);
        }
        addCellImpl(i, j, checkboxcell);
    }


    /**
     * 在第i行,第j列添加带有文本的复选框
     * @param  i 第i行
     * @param  j 第j列
     * @param  s 文本
     * @param  flag 是否为选中文本
     */
    public void addCheckBoxCell(int i, int j, String s, boolean flag)
    {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(s,
                CappTextAndImageCell.CHECKBOX, new Boolean(flag), null);
        addCellImpl(i, j, checkboxcell);
    }


    /**
     * 在第i行,第j列添加单选框
     * @param i 第i行
     * @param j 第j列
     * @param flag 是否为选中单选框
     */
    public void addRadioButtonCell(int i, int j, boolean flag)
    {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(
                CappTextAndImageCell.RADIOBUTTON, new Boolean(flag), null);
        addCellImpl(i, j, checkboxcell);
    }


    /**
     * 在第i行,第j列添加带有文本的单选框
     * @param  i 第i行
     * @param  j 第j列
     * @param  s 文本
     * @param  flag 是否为选中文本
     */
    public void addRadioButtonCell(int i, int j, String s, boolean flag)
    {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(s,
                CappTextAndImageCell.RADIOBUTTON, new Boolean(flag), null);
        addCellImpl(i, j, checkboxcell);
    }


    /**
     * 在第i行,第j列添加下拉框
     * @param i 第i行
     * @param j 第j列
     * @param value 下拉框显示的对象
     * @param values Vector 下拉框可显示的对象集合
     */
    public void addComboBoxCell(int i, int j, Object value, Vector values)
    {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(
                CappTextAndImageCell.COMBOBOX, value, values);
        addCellImpl(i, j, checkboxcell);
    }


    /**
     * 在第i行,第j列添加下拉框
     * @param i 第i行
     * @param j 第j列
     * @param value 下拉框显示的对象
     * @param values Vector 下拉框可显示的对象集合
     */
    public void addComboBoxCell(int i, int j, String s, Object value,
                                Vector values)
    {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(s,
                CappTextAndImageCell.COMBOBOX, value, values);
        addCellImpl(i, j, checkboxcell);
    }


    /**
     * 在第i行,第j列添加带有文本的单选框
     * @param  i 第i行
     * @param  j 第j列
     * @param  s 文本
     * @param value 下拉框显示的对象
     * @param values Vector 下拉框可显示的对象集合
     */
    /**  public void addComboBoxCell(int i, int j, String s, Object value,Vector values)
      {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(s,CappTextAndImageCell.COMBOBOX,value,values);
        addCellImpl(i, j, checkboxcell);
      }*/

    /**
     * 得到选中的列数
     * @return int 选中的列数
     */
    public int getSelectedColumn()
    {
        return this.table.getSelectedColumn();
    }


    /**
     * 设置单元格的字体.
     * @param f 新字体
     */
    public void setCellFont(Font f)
    {
        if (!(getFont().equals(f)))
        {
            Font oldValue = cellFont;
            cellFont = f;
            setFont(cellFont);
        }
    }

    public void sort()
    {

    }

    public static void main(String args[])
    {

        ComponentMultiList myMulti = new ComponentMultiList();
        int[] cols =
                     {
                     0, 1, 2};
        Vector vec1 = new Vector();
        vec1.add("aaa");
        vec1.add("bbb");
        vec1.add("ddd");
        myMulti.addTextCell(2, 4, "adb");
        myMulti.addTextCell(3, 4, "fdf");
        Vector vec = new Vector();
        vec.add("aaaa");
        vec.add("bbbb");
        vec.add("cccc");
        myMulti.addCell(5, 1, "radionb", CappTextAndImageCell.COMBOBOX, "aaaa",
                        vec);
        myMulti.setCellEditable(true);
        myMulti.getSelectModel().addListSelectionListener(new Mylist());

        // myMulti.setCheckModel(true);
        //myMulti.get
        JFrame frame = new JFrame();
        // JButton bb = new JButton();
        ///  bb.setText("122121");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(myMulti);
        //  contentPane.add(bb);
        frame.pack();
        frame.show();
    }
    
    
    //CCBegin SS1
    
  //按指定值设置列宽，注释部分是按内容调整列宽
	public void FitTableColumns(int[] columnsWidth) {  
		JTable myTable=this.getTable();
		//設置table的列寬隨內容調整
	    JTableHeader header = myTable.getTableHeader();
	    int rowCount = myTable.getRowCount();
	    Enumeration columns = myTable.getColumnModel().getColumns();
	    int width = 0;
	    int j=0;
        while (columns.hasMoreElements()) {
	        TableColumn column = (TableColumn) columns.nextElement();
	        int col = header.getColumnModel().getColumnIndex(
	                column.getIdentifier());
	        header.setResizingColumn(column);
	        if(columnsWidth.length>0){
	        		width=(int)columnsWidth[j];
	        		//System.out.println("width=============================================="+width);
	        		 if(width!=0)
	                   column.setWidth(width + myTable.getIntercellSpacing().width);
	                
	        		 else{
	                	column.setMaxWidth(width);
	                	column.setPreferredWidth(width);
	                	column.setMinWidth(width);
	                }
	                
	                j=j+1;
	        }
	    }
	}

    
    //CCEnd SS1
    
    

    static class Mylist implements ListSelectionListener
    {
        public void valueChanged(ListSelectionEvent e)
        {
            //System.out.println(e.getLastIndex());
        }
    }

}
