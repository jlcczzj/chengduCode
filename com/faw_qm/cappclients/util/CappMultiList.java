/** 程序 MultiList.java  1.0  03/03/2003


 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 * CR1 郭晓亮 2009/1/15   原因:工序中工装设备等维护界面排序功能都要实现单击选定，双击排序。
 * 
 *                        方案:将原代码中MultiList的鼠标按下事件替换为鼠标双击事件
 *                        
 *                        备注:变更记录标记"CRSS-001";
 *                        
 * CR2 2009/04/30 薛凯      原因：内容更新才提示是否保存。
 *                         方案：增加监听检测内容是否更新。
 *                         备注：CRSS-004                      
 * CR3 2009/06/22 徐春英  参见td:DefectID=2160
 * CR4 2009/09/03 徐春英  原因：给工具提示信息加延迟
 *                        方案：调用ToolTipManager的延迟方法
 * CR5 2009/09/03 徐春英  参见功能测试域:V4R3FunctionTest,td:DefectID=2622
 * CR6 2009/12/08 刘玉柱  原因：删除关联设备，工装，辅料中的空行时出现错误
 *                      方案：添加空字符串判断
 * CR7 2010/04/08  薛凯 参见TD3208
 * CR8 2010/04/16  薛凯 参见TD3224
 * CR9 2011/03/23  徐春英  参见TD2518
 * SS1 添加设置列不可编辑 和插入一个空行数据 liuyang 2013.1.31
 * SS2 鼠标放到下拉框时显示 liuyang 2013.2.19
 * SS3 修改列表宽度自动弹回的问题，参见服务平台2740 侯焊锋 2013.11.25
 *SS4 变速箱工艺所见即所得  guoxiaoliang 2014-03-14

 */
package com.faw_qm.cappclients.util;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.ItemSelectable;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeSupport;
import java.beans.VetoableChangeSupport;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.faw_qm.clients.util.SortButtonRenderer;
import com.faw_qm.jview.chrset.DataDisplay;
import com.faw_qm.jview.chrset.SpeCharaterTextPanel;
import com.faw_qm.jview.chrset.DataSource;
import com.faw_qm.jview.chrset.SpecialCharacter;

/**
 * <p>多列列表控件。对多列列表的表头，行，列等属性进行设置并设置监听事件</p>
 * @author 管春元
 */
// 问题（1）2007.11.05 薛凯添加 汇总结果按数值排序不正确

public class CappMultiList extends JPanel implements FocusListener, ActionListener, MouseListener, ItemSelectable, ListSelectionListener, KeyListener
{
	//CCBegin SS3
	//判断一个列表是否是自适应列宽
	private boolean isAdjust=false;
	//CCEnd SS3
	
	//CCBegin SS4
    //begin CR7
	public int editingRow = -1;
	public int editingColumn = -1;
    //end CR7
	//CCEnd SS4
	
    private static int a = 0;
    //begin CR2
  //CCBegin SS4
    //设置是否需要返回更新标识，加监听时自动设置。为真时内容是否更新标识才可用。
    public boolean isNeedNoticeModified = false;
    
    //内容是否更新标识
    public boolean isModified = false;
 
    //正在编辑单元格所在的行集合
    public Vector CellEditorRowNumVec = new Vector();
    //CCEnd SS4

    //线程后重新初始化。因为像设备等列表的初始化数据都是线程添加的，会出现一个假的现象，即为表格数据更新了。
    public void intAfterThread()
    {
        isModified = false;
    }

    //获取内容是否改变标识的内容。
    public boolean isValuesChanged()
    {
        boolean b = isModified;
        isModified = false;
        return b;
    }

    //给表格加监听，同时设置表格是否更新标识可用。如果该表格加过监听，则不在加。
    public void addTableModelListener()
    {
        isNeedNoticeModified = true;
        TableModelListener[] tablelistener = mtModel.getTableModelListeners();
        for(int i = 0;i < tablelistener.length;i++)
        {
            if(tablelistener[i] instanceof MultilistListener)
            {
                return;
            }
        }
        mtModel.addTableModelListener(new MultilistListener());
    }

    //内部表格模型监听类，监听表格的添加删除一行数据的操作。
    public class MultilistListener implements TableModelListener
    {
        public void tableChanged(TableModelEvent tme)
        {
            int t = tme.getType();
            if(t == TableModelEvent.INSERT)
            {
                isModified = true;
            }
            if(t == TableModelEvent.DELETE)
            {
                isModified = true;
            }
        }
    }

    //end CR2

    /**
     * 构建默认列表.
     */
    public CappMultiList()
    {
        this(0, false, Color.white);
    }

    /**
     * 构建指定列数的列表.
     * @param cols 为列数
     */
    public CappMultiList(int cols)
    {
        this(cols, false, Color.white);
    }

    /**
     * 构建一新的含有一定列数的列表 指定是否可以多选行.
     * @param cols 为列数
     * @param multi 如为true 则可以多选,否则不可以
     */
    public CappMultiList(int cols, boolean multi)
    {
        this(cols, multi, Color.white);
    }

    /**
     * 构建一新的含有一定列数的列表, 指定是否可以多选,背景颜色
     * @param cols 为列数
     * @param multi 如为true 则可以多选,否则不可以
     * @param bg 为背景颜色
     */
    public CappMultiList(int cols, boolean multi, Color bg)
    {

        GridBagLayout gridBagLayout;
        gridBagLayout = new GridBagLayout();
        super.setLayout(gridBagLayout);
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 100.0;
        gbc.weighty = 100.0;
        internalCreateColumns(cols);
        colorBg = bg;
        createColumns(cols);
        multiSelect = multi;
        Font defaultFont = new Font("SansSerif", Font.PLAIN, 12);
        JFrame f = new JFrame();
        table = new CappTable(mtModel);//CR3
        table.setAutoResizeMode(0);
        table.setShowHorizontalLines(false);
        //        table.setRowSelectionAllowed(true);
        //        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);

        table.setRowSelectionAllowed(false);

        table.setShowVerticalLines(false);
        table.setDefaultRenderer(CappTextAndImageCell.class, new CellRenderer()); //设置表的编辑模式及着色模式
        table.setDefaultEditor(CappTextAndImageCell.class, new CellEditor(new JTextField()));
        table.getTableHeader().setReorderingAllowed(false);
        table.addMouseListener(this);
        table.addKeyListener(this);
        ToolTipManager tip = ToolTipManager.sharedInstance();//begin CR4
        tip.setDismissDelay(30000);
        table.addMouseListener(tip);//end CR4
        //table.getSelectionModel().addListSelectionListener(this);
        setMultipleMode(multi);
        JTableHeader header = table.getTableHeader();
        SortButtonRenderer headerRenderer = new SortButtonRenderer();
        header.setDefaultRenderer(headerRenderer);
        if(isAllowSorting())
        {

            //Begin CR1
            //header.addMouseListener(new HeaderListener(header, headerRenderer)); //向表头加入排序监听

            header.addMouseListener(new HeadDblclickListener(header, headerRenderer));

            //End CR1

        }

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 100;
        gbc.weighty = 100;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        //((GridBagLayout)getLayout()).setConstraints(table, gbc);
        js = new JScrollPane();
        js.getViewport().add(table);
        js.getViewport().setBackground(bg);
        ((GridBagLayout)getLayout()).setConstraints(js, gbc);
        add(js);
        /*
         * f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.getContentPane().add(js,BorderLayout.CENTER);
         * 
         * f.pack(); f.setVisible(true);
         */

    }

    /**
     * 双击表头排序监听类 CR1
     */

    class HeadDblclickListener extends MouseAdapter
    {
        JTableHeader header;
        AbstractTableModel tableModel;
        SortButtonRenderer renderer;

        HeadDblclickListener(JTableHeader header, SortButtonRenderer headerRenderer)
        {
            this.header = header;
            this.renderer = headerRenderer;
        }

        public void mouseClicked(MouseEvent e)
        {

            if(e.getClickCount() == 2)
            {

                if(isAllowSorting())
                {
                    int col = header.columnAtPoint(e.getPoint());
                    int sortCol = header.getTable().convertColumnIndexToModel(col);
                    //记录按下的列号
                    if(col < 0 || table.getRowCount() <= 0)
                    {
                        return;
                    }
                    renderer.setPressedColumn(col);
                    //某一列排序的状态
                    renderer.setSelectedColumn(col);
                    header.repaint();

                    //如果有正在编辑的单元格，停止编辑方式。
                    if(header.getTable().isEditing())
                    {
                        header.getTable().getCellEditor().stopCellEditing();

                    }
                    //排序
                    boolean isAscent = true;
                    if(SortButtonRenderer.DOWN == renderer.getState(col))
                    {
                        isAscent = true;
                    }else
                    {

                        isAscent = false;

                    }
                    ((CappSortableTableModel)header.getTable().getModel()).sortByColumn(sortCol, isAscent);
                    //重新设置行高
                    initalHeight();
                    int col2 = header.columnAtPoint(e.getPoint());
                    //记录按下的列号 -1 代表clear
                    renderer.setPressedColumn(-1); // clear
                    header.repaint();
                }

            }

        }
    }

    //add by guoxl end (双击表头排序)
    /**
     * 设置背景色
     * @param color Color 背景色
     */
    public void setBackGround(Color color)
    {
        js.getViewport().setBackground(color);
    }

    /**
     * 构建一新的含有一定列数的列表, 指定是否可以多选,背景颜色
     * @param cols 为列数
     * @param multi 如为true 则可以多选,否则不可以
     * @param bg 为背景颜色
     */
    public CappMultiList(int cols, boolean multi, Color bg, boolean isCheckMulti)
    {
        this(cols, multi, bg);
        if(isCheckMulti)
        {
        	System.out.println("设置编辑器2222222222222222222222222222");
            table.setDefaultEditor(CappTextAndImageCell.class, new MultiCellEditor(new JCheckBox()));
            System.out.println("设置编辑器eeeeeee222222222222222222222");
        }
    }

    /**
     * 给指定列数分配空间.
     * @param i 为新的列数
     */
    protected void internalCreateColumns(int i)
    {
        headings = new String[i];
    }

    /**
     * 给定列数初始化列表. 所有的列表数据丢失,行数据保留.
     * @param cols 新的列数
     */
    public void createColumns(int cols)
    {
        internalCreateColumns(cols);
        if(cols > 0)
        {
            for(int i = 0;i < cols;i++)
            {
                createColumn("");
            }
        }
    }

    /**
     * 生成新列
     * @param h 新列名
     */
    private void createColumn(String h)
    {
        mtModel.addColumn(h);
    }

    /**
     * 按每行最高的图片高度设置行高，如果没有图片，默认为 defaulRowHeight
     * @parame row 行数
     * @parame imageCell TextAndImageCell封装数据类对象
     * @see CappTextAndImageCell
     */
    private void initalHeight(int row, CappTextAndImageCell imageCell)
    {
        if(row >= 0 && imageCell != null)
        {
            ImageIcon image = (ImageIcon)(imageCell.getImageIcon());
            //如果图片的高度大于默认高度
            if(image != null && image.getIconHeight() > defaulRowHeight)
            {
                defaulRowHeight = image.getIconHeight();
            }
            table.setRowHeight(row, defaulRowHeight);
        }
    }

    /**
     * 设置默认行高
     */
    private void initalHeight()
    {
        for(int i = 0;i < table.getRowCount();i++)
        {

            table.setRowHeight(i, defaulRowHeight);
        }
    }

    /**
     * 设置CappMultiList的选择方式
     * @param b 是否可以多选行，true为可多选
     */
    public void setMultipleMode(boolean b)
    {
        multiSelect = b;
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        if(!multiSelect)
        {
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        }else
        {
            table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        }
    }

    /**
     * 判断是否是多选方式
     * @return 是否是多选方式
     */
    public boolean isMultipleMode()
    {
        return multiSelect;
    }

    /**
     * 重新设置列数.
     * @param i 列数
     */
    public void setNumberOfCols(int i)
    {
        Integer oldValue = new Integer(getNumberOfCols());
        Integer newValue = new Integer(i);
        if(!oldValue.equals(newValue))
        {
            removeAllColumns();
            createColumns(i);
        }
    }

    /**
     * 又一种重新设置列数，调用上一种方法
     * @param i 列数
     */
    public void setColumns(int i)
    {
        setNumberOfCols(i);
    }

    /**
     * 得到列数
     * @return 当前列数.
     */
    public int getNumberOfCols()
    {
        return table.getColumnCount();
    }

    /**
     * 删除某一列
     * @param i 第i列
     */
    public void removeColumn(int i)
    {
        int colNum = mtModel.getColumnCount() - 1;
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn tableColumn = columnModel.getColumn(i);
        table.getColumnModel().removeColumn(tableColumn);
        mtModel.setColumnCount(colNum);
    }

    /**
     * 删除所有列
     */
    public void removeAllColumns()
    {
        TableColumnModel columnModel = table.getColumnModel();
        int num = mtModel.getColumnCount();
        for(int i = 0;i < num;i++)
        {
            removeColumn(0);
        }
        mtModel.setColumnCount(0);
    }

    /**
     * 得到行数.
     * @return 当前行数.
     */
    public int getNumberOfRows()
    {
        return table.getRowCount();
    }

    /**
     * 设置表头是否可见.
     * @param b 表头是否可见
     */
    public void setHeadingVisible(boolean b)
    {
        table.getTableHeader().setVisible(b);
    }

    /*
     * private void deselectEvent(int newSelection, int[] selRows) { int numberSelected = selRows.length;
     * 
     * if ((numberSelected>1) || ((numberSelected==1) && (selectedRow!=newSelection))) { } }
     */

    /**
     * 选择某一行
     * @param row 第row行
     */
    public void selectRow(int row)
    {
        if(!multiSelect)
        {
            int selectedRow = getSelectedRow();
            if(selectedRow != -1 && selectedRow != row)
            {
                deselectRow(selectedRow);
            }
        }

        table.addRowSelectionInterval(row, row);

    }

    /**
     * 返回列表表头是否可见.
     * @return 列表表头是否可见
     */
    public boolean isHeadingVisible()
    {
        return headingVisible;
    }

    /**
     * 得到i列的表头
     * @param i 列索引
     * @return String 得到i列的表头
     */
    public String getHeading(int i)
    {
        return table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString();
    }

    /**
     * 修改第i列的表头文本
     * @param h 新表头文本
     * @param i 列索引
     */
    public void setHeading(String h, int i)
    {
        internalCreateColumns(i);
        adjustCols(i);
        table.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(h);
    }

    /**
     * 设置第i列的表头内容和宽度
     * @param h 表头内容
     * @param i 第i列
     * @param pixels 象素
     */
    public void setHeading(String h, int i, int pixels)
    {
        internalCreateColumns(i);
        adjustCols(i);
        setHeading(h, i);
        table.getTableHeader().getColumnModel().getColumn(i).setPreferredWidth(pixels);
    }

    /**
     *得到表头字符的数组形式
     * @return String[] 表头内容的数组形式
     */
    public String[] getHeadings()
    {
        int headingNums = table.getColumnCount();
        headings = new String[headingNums];
        for(int i = 0;i < headingNums;i++)
        {
            headings[i] = table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString();
        }
        return headings;
    }

    /**
     *给定的字符数组更新表头
     * @param list 表头的数组形式
     */
    public void updateHeadings(String[] list)
    {
        list = tokenizeStringArrayIfNeeded(list);
        removeAllColumns();
        calcHeadings(list);
    }

    /**
     *给定的字符数组初始化表头.
     *@param list 表头的数组形式
     */
    public void setHeadings(String[] list)
    {
        list = tokenizeStringArrayIfNeeded(list);
        if(list.length == 0)
        {
            internalCreateColumns(0);
        }else
        {
            calcHeadings(list);
        }
    }

    /**
     * 给定的数组初始化表头.
     * @param list 初始化表头数组
     */
    protected void calcHeadings(String[] list)
    {
        headings = new String[list.length];
        adjustCols(list.length - 1);
        for(int i = 0;i < list.length;++i)
        {
            setHeading(list[i], i);
        }
    }

    /**
     * 处理用户用分号处理的item,
     * @param list 处理用户用分号的数组
     * @return String[] 处理用户用分号处理的item
     */
    protected String[] tokenizeStringArrayIfNeeded(String[] list)
    {
        if(list != null)
        {
            if(list.length == 1 && list[0].indexOf(";") != -1)
            {
                java.util.StringTokenizer tokenizer = new java.util.StringTokenizer(list[0], ";");
                int numColumns = tokenizer.countTokens();
                String[] convertedList = new String[numColumns];
                int currCol = 0;
                while(tokenizer.hasMoreTokens())
                {
                    String columnHeader = tokenizer.nextToken();
                    convertedList[currCol] = columnHeader;
                    currCol++;
                }
                list = convertedList;
            }
        }

        return list;
    }

    /**
     * 得到表头的字体.
     * @return Font 表头字体
     */
    public Font getHeadingFont()
    {
        headingFont = table.getTableHeader().getFont();
        return headingFont;
    }

    /**
     * 设置表头的字体
     * @param newFont 新字体
     */
    public void setHeadingFont(Font newFont)
    {
        if(!table.getTableHeader().getFont().equals(newFont))
        {
            Font oldValue = getHeadingFont();
            headingFont = newFont;
            if(headingVisible)
            {
                table.getTableHeader().setFont(headingFont);
                table.repaint();
            }
        }
    }

    /**
     * 得到单元格的所有字体.
     * @return Font 单元格字体
     */
    public Font getCellFont()
    {
        cellFont = table.getFont();
        return cellFont;
    }

    /**
     * 设置单元格的字体.
     * @param f 新字体
     */
    public void setCellFont(Font f)
    {
        if(!(table.getFont().equals(f)))
        {
            cellFont = f;
            table.setFont(cellFont);
        }
    }

    /**
     * 设置表头的前景和背景颜色
     * @param fg 前景颜色
     * @param bg 背景颜色
     */
    public void setHeadingColors(Color fg, Color bg)
    {
        setHeadingFg(fg);
        setHeadingBg(bg);
    }

    /**
     * 设置表头的前景颜色.
     * @param c 颜色
     */
    public void setHeadingFg(Color c)
    {
        if(!table.getTableHeader().getForeground().equals(c))
        {
            headingFg = c;
            table.getTableHeader().setForeground(headingFg);
            table.repaint();
        }
    }

    /**
     * 得到表头的前景颜色
     * @return Color 表头前景颜色
     */
    public Color getHeadingFg()
    {
        return headingFg;
    }

    /**
     * 得到表头的背景颜色
     * @return Color 表头颜色
     */
    public Color getHeadingBg()
    {
        return headingBg;
    }

    /**
     * 设置表头的背景颜色
     * @param c 颜色
     */
    public void setHeadingBg(Color c)
    {
        if(!(table.getTableHeader().getBackground().equals(c)))
        {
            headingBg = c;
            table.getTableHeader().setBackground(headingBg);
        }
    }

    /**
     * 设置单元格的前景和背景颜色.
     * @param fg 前景颜色
     * @param bg 背景颜色
     */
    public void setCellColors(Color fg, Color bg)
    {

        setCellFg(fg);
        setCellBg(bg);
    }

    /**
     * 得到背景颜色
     * @return Color 背景颜色
     */
    public Color getCellFg()
    {
        return colorFg;
    }

    /**
     * 设置单元格的前景
     * @param c 单元格的前景
     */
    public void setCellFg(Color c)
    {
        if(!(table.getForeground().equals(c)))
        {

            colorFg = c;
            table.setForeground(colorFg);
        }
    }

    /**
     * 设置单元格的 背景颜色
     * @param c 颜色
     */
    public void setCellBg(Color c)
    {
        if(!(table.getBackground().equals(c)))
        {

            colorBg = c;
            table.setBackground(colorBg);
            table.repaint();
        }
    }

    /**
     *得到单元格的背景颜色.
     *@return Color 单元格背景颜色
     */
    public Color getCellBg()
    {
        return colorBg;
    }

    /**
     * 设置每列的对齐方式
     * @param list 对齐方式的数组形式
     */
    public void setColumnAlignments(String[] list)
    {
        if(list != null && list.length == 0)
        {
            list = null;
        }
        list = tokenizeStringArrayIfNeeded(list);
        String[] oldValue = getColumnAlignments();
        if(list != null)
        {
            columnAlignments = new int[list.length];

            for(int i = 0;i < list.length;i++)
            {
                String listElem = list[i];
                int alignment = defaultColumnAlignment;
                if(listElem != null)
                {
                    if(listElem.equalsIgnoreCase("Left"))
                    {
                        alignment = LEFT;
                    }else if(listElem.equalsIgnoreCase("Center"))
                    {
                        alignment = CENTER;
                    }else if(listElem.equalsIgnoreCase("Right"))
                    {
                        alignment = RIGHT;
                    }
                    table.getTableHeader().setAlignmentX(alignment);

                }

                columnAlignments[i] = alignment;
            }
        }else
        {
            columnAlignments = null;

        }
    }

    /**
     * 根据列值得到列的排列方式(left ,right and so on)n.
     * @param column 第几列
     * @return int 第column列的对齐方式
     */
    public int getColumnAlignment(int column)
    {
        if(columnAlignments != null && column < columnAlignments.length)
        {
            int columnAlignment = columnAlignments[column];
            if(columnAlignment != -1)
            {
                return columnAlignments[column];
            }
        }

        return getDefaultColumnAlignment();
    }

    /**
     *默认对齐方式.
     * @return int 返回默认列对齐方式
     */
    public int getDefaultColumnAlignment()
    {
        return defaultColumnAlignment;
    }

    /**
     * 得到排列方式的字符串形式.
     * @return String[] 默认对齐方式(数组形式)
     */
    public String[] getColumnAlignments()
    {
        if(columnAlignments == null)
        {
            return null;
        }
        String[] list = new String[columnAlignments.length];
        for(int i = 0;i < list.length;i++)
        {
            String alignString;
            if(getColumnAlignment(i) == LEFT)
            {
                alignString = "Left";
            }else if(getColumnAlignment(i) == CENTER)
            {
                alignString = "Center";
            }else
            {
                alignString = "Right";
            }
            list[i] = alignString;
        }

        return list;
    }

    /**
     * 设置某一列的文本排列方式.
     * @param column 第column列
     * @param alignment 对齐方式
     */
    public void setColumnAlignment(int column, int alignment)
    {
        if(columnAlignments == null)
        {
            columnAlignments = new int[headings.length];
            for(int i = 0;i < headings.length;i++)
            {
                columnAlignments[i] = LEFT;
            }
        }
        this.columnAlignments[column] = alignment;
    }

    /**
     * 设置默认对齐方式.
     * @param newDefaultColumnAlignment 新的默认对齐方式
     */
    public void setDefaultColumnAlignment(int newDefaultColumnAlignment)
    {
        if(defaultColumnAlignment != newDefaultColumnAlignment)
        {
            defaultColumnAlignment = newDefaultColumnAlignment;
        }
    }

    /**
     * 设置列的尺寸(只是列宽).
     * @param list 列的尺寸数组
     */
    public void setColumnSizes(String[] list)
    {

        if(list != null && list.length > 0)
        {
            columnSizes = new int[list.length];
            int total = 0;
            for(int i = 0;i < list.length;i++)
            {
                columnSizes[i] = 0;
                int stringValue = 100;
                String listElem = list[i];
                stringValue = Integer.parseInt(listElem);
                if(stringValue == 0)
                {
                    table.getColumnModel().getColumn(i).setMaxWidth(stringValue);
                    table.getColumnModel().getColumn(i).setPreferredWidth(stringValue);
                    table.getTableHeader().getColumnModel().getColumn(i).setMinWidth(stringValue);
                }
                columnSizes[i] = stringValue;
            }
        }
    }

    /**
     * 返回数组列宽
     * @return String[] 返回所有列的列尺寸
     */
    public String[] getColumnSizes()
    {
        return intArrayToStringArray(columnSizes);
    }

    /**
     * 得到某一列的尺寸.
     * @param i 第i列
     * @return int 第i列的尺寸
     */
    public int getColumnSize(int i)
    {
        return table.getColumnModel().getColumn(i).getPreferredWidth();
    }

    /**
     * 将数值型数组变为字符型数组
     * @param intArray 数值型数组
     * @return String[] 字符型数组
     */
    protected String[] intArrayToStringArray(int[] intArray)
    {
        if(intArray == null)
        {
            return null;
        }
        String[] list = new String[intArray.length];
        for(int i = 0;i < intArray.length;i++)
        {
            String intString = "";
            intString = String.valueOf(intArray[i]);
            list[i] = intString;
        }

        return list;
    }

    /**
     * 设置特定行为选择行.
     * @param row 行索引
     */
    public void setSelectedRow(int row)
    {

        if(row < 0 || row > table.getRowCount() - 1)
        {
            return;
        }
        table.addRowSelectionInterval(row, row);

    }

    /**
     * 不选某一行.
     * @param row 行索引
     */
    public void deselectRow(int row)
    {
        if(row < 0 || row > table.getRowCount() - 1)
        {
            return;
        }
        table.removeRowSelectionInterval(row, row);
    }

    /**
     * 得到选择行的索引(第i行,i从0开始)
     * @return int 所选行号
     */
    public int getSelectedRow()
    {
        return table.getSelectedRow();
    }

    /**
     * 得到选择列的索引(第i行,i从0开始)
     * @return int 所选行号
     */
    public int getSelectedCol()
    {
        return table.getSelectedColumn();
    }

    /**
     * 选择所有行.
     */
    public void selectAll()
    {
        if(!multiSelect)
        {
            return;
        }
        table.selectAll();
    }

    /**
     * 不选所有行.
     */
    public void deselectAll()
    {
        table.clearSelection();
    }

    /**
     * 得到所有所选行的索引.
     * @return int[] 所有所选行的索引.
     */
    public int[] getSelectedRows()
    {
        return table.getSelectedRows();
    }

    /**
     * 得到 所选行的对象方式(Integer).
     * @return Object[] 所选行的对象方式(Integer).
     */
    public Object[] getSelectedObjects()
    {
        int[] temp = getSelectedRows();
        int length = temp.length;

        Integer[] newArray = new Integer[length];

        for(int i = 0;i < length;i++)
        {
            newArray[i] = new Integer(temp[i]);
        }

        return newArray;
    }

    /**
     * 获得Table
     * @return JTable
     */
    public JTable getTable()
    {
        return table;
    }

    /**
     * 获得默认模式
     * @return DefaultTableModel 默认模式
     */
    public DefaultTableModel getTableModel()
    {
        return mtModel;
    }

    /**
     * 设置列的最小宽度.
     * @param size 尺寸
     */
    public void setMinColumnWidth(int size)
    {
        if(minColumnWidth != size && size > 0)
        {
            minColumnWidth = size;
            for(int i = 0;i < getNumberOfCols();i++)
            {
                table.getColumnModel().getColumn(i).setMinWidth(minColumnWidth);
            }
        }
    }

    /**
     * 根据item数组选项添加单元格
     *@param items 为用;分隔的列数据项数组
     */
    public void setListItems(String[] items)
    {
        try
        {
            clear();
            for(int row = 0;row < items.length;row++)
            {
                data = new Vector();
                String s = items[row];
                if(s == null)
                {
                    s = "";
                }
                int len = s.length();
                int start, end, col;
                for(end = col = start = 0;end <= len;++end)
                {
                    if(end == len || s.charAt(end) == ';')
                    {
                        data.add(new CappTextAndImageCell(s.substring(start, end)));
                        start = end + 1;
                    }
                }
                mtModel.addRow(data);

            }

        }finally
        {
            table.repaint();
        }
    }

    /**
     * 得到行值的字符形式,一行中用;分隔
     * @return String[] 行值的字符数组形式
     */
    public String[] getListItems()
    {
        Vector v = new Vector();
        String s;
        boolean lastNull = false;
        int row, col;
        String rowString;
        String[] items = new String[getNumberOfRows()];
        for(row = 0;row < getNumberOfRows();row++)
        {
            rowString = "";
            Vector dataVector = mtModel.getDataVector();
            Vector rowVector = (Vector)dataVector.elementAt(row);
            for(col = 0;col < getNumberOfCols();col++)
            {
                s = (rowVector.elementAt(col)).toString();
                if(col != 0)
                {
                    rowString += ";";
                }
                rowString += (s != null) ? s : "";
            }

            items[row] = rowString;
        }

        return items;
    }

    /**
     * 设置是否按列排序
     * @param b 是否可以排序
     */
    public void setAllowSorting(boolean b)
    {
        if(allowSorting != b)
        {
            allowSorting = b;
        }
    }

    /**
     * 得到是否排序
     * @return boolean 是否排序
     */
    public boolean isAllowSorting()
    {
        return allowSorting;
    }

    /**
     * 设置是否可以调整列宽.
     * @param allowResizing 是否可以调整列宽
     */
    public void setAllowResizingOfColumns(boolean allowResizing)
    {
        if(allowResizingOfColumns != allowResizing)
        {
            allowResizingOfColumns = allowResizing;
            if(!allowResizingOfColumns)
            {
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            }else
            {
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            }
        }
    }

    /**
     * 得到是否可以调整列宽.
     * @return boolean 是否可以调整列宽
     */
    public boolean isAllowResizingOfColumns()
    {
        return allowResizingOfColumns;
    }

    /**
     * 得到最小许可列宽
     * @return int 最小列宽
     */
    public int getMinColumnWidth()
    {
        return minColumnWidth;
    }

    /**
     * 删除CappMultiList中的所有行.
     */

    public void clear()
    {
        int num = table.getRowCount();
        if(num != 0)
        {
            for(int i = 0;i < num;i++)
            {
                mtModel.removeRow(0);
            }
            mtModel.setRowCount(0);
        }
        if(table.getCellEditor() != null && table.isEditing())
            table.getCellEditor().cancelCellEditing();

    }

    /*
     * 删除某一行
     * 
     * @param row 列索引
     */
    public void removeRow(int row)
    {
        mtModel.removeRow(row);
    }

    /**
     *调整表格列数
     * @param cols 表格列数
     */
    private void adjustCols(int cols)
    {
        if(cols > getNumberOfCols() - 1)
        {
            insertCols(cols + 1 - getNumberOfCols());
        }
    }

    /**
     * 调整表格行数，列数
     * @param rows 表格行数
     * @param cols 表格列数
     */
    public void adjustRowsAndCols(int rows, int cols)
    {
        adjustCols(cols);
        if(rows > getNumberOfRows() - 1)
        {
            insertRows(rows + 1 - getNumberOfRows());
        }

    }

    /**
     * 插入表格行数
     * @param rows 表格行数
     */
    private void insertRows(int rows)
    {
        int colNum = getNumberOfCols();
        String tt[] = new String[colNum];
        for(int i = 0;i < tt.length;i++)
        {
            tt[i] = new String("");
        }
        for(int i = 0;i < rows;i++)
        {
            insertRow(tt);
        }
    }

    private void insertRow(Object[] obj)
    {
        mtModel.addRow(obj);
    }

    private void insertCols(int cols)
    {
        String initHeadings[] = getHeadings();
        CappTextAndImageCell tt = new CappTextAndImageCell();
        for(int i = 0;i < cols;i++)
        {
            mtModel.addColumn("");
        }

        // for (int len = 0; len <initHeadings.length ; len++)
        // setHeading(initHeadings[len],len);

    }

    /**
     * 设置某一指定行,列单元格中的文本内容.
     * @param r 列索引
     * @param c 行索引
     * @param s 文本内容
     */

    public void addTextCell(int r, int c, String s)
    {
        //adjustRowsAndCols(r,c);
        if(s == null)
        {
            s = new String("");
        }
        CappTextAndImageCell cell = new CappTextAndImageCell(s);
        addCellImpl(r, c, cell);
        if(getColumnSizes() != null)
        {
            setColumnSizes(getColumnSizes());
        }
    }

    /**
     * 设置某一指定行,列单元格中的文本内容.
     * @param r 列索引
     * @param c 行索引
     * @param s 文本内容
     */
    // 问题（1）2007.11.05 薛凯添加 汇总结果按数值排序不正确

    public void addNumberTextCell(int r, int c, Object s)
    {
        if(s == null)
        {
            s = new String("");
        }
        CappTextAndImageCell cell = new CappTextAndImageCell(s.toString(), s);
        addCellImpl(r, c, cell);
        if(getColumnSizes() != null)
        {
            setColumnSizes(getColumnSizes());
        }
    }

    /**
     * 设置某一指定行,列单元格中的图片.
     * @param r 列索引
     * @param c 行索引
     * @param i 图片
     */
    public void addImageCell(int r, int c, Image i)
    {
        //adjustRowsAndCols(r,c);
        ImageIcon j = new ImageIcon(i);
        CappTextAndImageCell cell = new CappTextAndImageCell(j);
        addCellImpl(r, c, cell);
        initalHeight(r, cell);
        if(getColumnSizes() != null)
        {
            setColumnSizes(getColumnSizes());
        }
    }

    /**
     * 设置某一指定行,列单元格中的文本和图片.
     * @param r 列索引
     * @param c 行索引
     * @param s 文本内容
     * @param i 图片
     */
    public void addCell(int r, int c, String s, Image i)
    {
        //adjustRowsAndCols(r,c);
        ImageIcon j = new ImageIcon(i);
        CappTextAndImageCell cell = new CappTextAndImageCell(s, j);
        addCellImpl(r, c, cell);
        initalHeight(r, cell);
        if(getColumnSizes() != null)
        {
            setColumnSizes(getColumnSizes());
        }
    }

    /**
     * 设置某一指定行,列单元格.
     * @param row 列索引
     * @param column 行索引
     * @param cell 单元格
     * @see CappTextAndImageCell
     */
    public void addCell(int row, int column, CappTextAndImageCell cell)
    {
        // adjustRowsAndCols(row,column);
        addCellImpl(row, column, cell);
        initalHeight(row, cell);
        if(getColumnSizes() != null)
        {
            setColumnSizes(getColumnSizes());
        }
    }

    /**
     * 将单元格中内容加入到列表中.
     * @param row 列索引
     * @param column 行索引
     * @param cell 单元格
     * @see CappTextAndImageCell
     */
    protected void addCellImpl(int row, int column, CappTextAndImageCell cell)
    {
        adjustRowsAndCols(row, column);
        table.setValueAt(cell, row, column);  
        initalHeight(row, cell);
        if(getColumnSizes() != null)
        {
            setColumnSizes(getColumnSizes());
        }
        
        
        
    }

    //zz
    /**
     * 将单元格中特殊符号控件加入到列表中.
     * @param row int 列索引
     * @param column int 行索引
     * @param cell CappTextAndImageCell 单元格
     * @see CappTextAndImageCell
     */
    protected void addCellImplforSpeChar(int row, int column, CappTextAndImageCell cell)
    {
    	
        adjustRowsAndCols(row, column);
        table.setValueAt(cell, row, column);
        setDefaultRowHeight(30);

        addCell(row, column, cell);

    }

    /**
     * 返回指定单元格的文本.
     * @param r 列索引
     * @param c 行索引
     * @return 指定单元格的文本
     */
    public String getCellText(int r, int c)
    {
        //Begin CR6
        Object obj = table.getValueAt(r, c);
        if(obj == null || obj.equals(""))
        {
            return null;
        }
        return ((CappTextAndImageCell)(obj)).getStringValue();//End CR6

    }

    /**
     * 获得指定行列的单元格
     * @param i int 行号
     * @param j int 列号
     * @return CappTextAndImageCell 单元格
     */
    public CappTextAndImageCell getCellAt(int i, int j)
    {
        return (CappTextAndImageCell)table.getValueAt(i, j);
    }
    //CCBegin SS4
    /**
     * 获得指定行列的单元格
     * @param i int 行号
     * @param j int 列号
     * @return CappTextAndImageCell 单元格
     */
    public CappTextAndImageCell getCellAtForBSX(int i, int j)
    {
    	
    	 Object obj = table.getValueAt(i, j);
         if(obj == null || obj.equals(""))
         {
             return null;
         }else{
        	 
        	 CappTextAndImageCell cell=(CappTextAndImageCell)obj;
        	 return cell;
         }
    	
    }
    //CCEnd SS4
    

    /**
     * 返回指定单元格的图片.
     * @param r 列索引
     * @param c 行索引
     * @return Image 指定单元格的图片.
     */
    public Image getCellImage(int r, int c)
    {
        if(((CappTextAndImageCell)(table.getValueAt(r, c))).getImageIcon() != null)
        {
            return ((CappTextAndImageCell)(table.getValueAt(r, c))).getImageIcon().getImage();
        }else
        {
            return null;
        }
    }

    /**
     * 得到列表的优选尺寸
     * @return Dimension 列表的优选尺寸
     */
    public synchronized Dimension getPreferredSize()
    {
        return new Dimension(175, 125);
    }

    /**
     * 得到列表的最小尺寸
     * @return Dimension 列表的最小尺寸
     */
    public synchronized Dimension getMinimumSize()
    {
        return new Dimension(50, 50);
    }

    /**
     * 初始化表格
     */
    public void initTable()
    {
        Vector dataVector = new Vector();
        CappTextAndImageCell tt = new CappTextAndImageCell();
        dataVector = mtModel.getDataVector();
        for(int i = 0;i < dataVector.size();i++)
        {
            for(int j = 0;j < ((Vector)(dataVector.elementAt(i))).size();j++)
            {
                if(((Vector)(dataVector.elementAt(i))).elementAt(j) == null)
                {
                    ((Vector)(dataVector.elementAt(i))).setElementAt(tt, j);
                }
            }
        }
        Vector h = new Vector();
        String[] s = getHeadings();
        for(int j = 0;j < s.length;j++)
        {
            h.add(s[j]);
        }
        mtModel.setDataVector(dataVector, h);
        if(getColumnSizes() != null)
        {
            setColumnSizes(getColumnSizes());
        }
    }

    public static void main(String args[])
    {}

    /***************************/
    /**
     * <p>Title:内部封装 单元格显示类 </p> <p>Description:显示单元格 </p> <p>Copyright: Copyright (c) 2008</p> <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    class CellRenderer extends JPanel implements TableCellRenderer
    {

        JLabel label = new JLabel();
        JComponent checkBox;

        public CellRenderer()
        {
            setOpaque(true);
        }

        /**
         *重载单元格的编辑组件
         * @param table JTable 当前Tab页
         * @param value Object 所选单元格内容
         * @param isSelected boolean 是否被选中
         * @param hasFocus 是否有焦点
         * @param row int 所在行数
         * @param column int 所在列数
         * @return Component 该单元格组件
         */
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            //setLayout(new GridLayout());
            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
            label.setHorizontalAlignment(JLabel.LEFT);
            if(isSelected)
            {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
                label.setForeground(table.getSelectionForeground());
                label.setBackground(table.getSelectionBackground());
            }else
            {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
                label.setForeground(table.getForeground());
                setBackground(new Color(233, 233, 233));

            }
            label.setFont(table.getFont());
            if(hasFocus)
            {
                setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
                if(table.isCellEditable(row, column))
                {
                    //                    setForeground(UIManager.getColor(//begin CR5
                    //                            "Table.focusCellForeground"));
                    //                    setBackground(UIManager.getColor(
                    //                            "Table.focusCellBackground"));
                    setForeground(table.getSelectionForeground());
                    setBackground(table.getSelectionBackground());//end CR5
                }
            }else
            {
                setBorder(new EmptyBorder(1, 1, 1, 1));
            }
            if(value == null)
            {
                label.setText("");
                label.setIcon(null);

                this.removeAll();
                add(label);
            }else if(value instanceof CappTextAndImageCell)
            { // 1
                if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.TEXT)
                { // if getType() == CappTextAndImageCell.TEXT

                    label.setIcon((((CappTextAndImageCell)value).getImageIcon() == null) ? null : ((CappTextAndImageCell)value).getImageIcon());
                    label.setText((((CappTextAndImageCell)value).getStringValue() == null) ? "" : ((CappTextAndImageCell)value).getStringValue().toString());
                    // this.remove(checkBox);
                    this.removeAll();

                    add(label);
                    label.addFocusListener(CappMultiList.this);

                }else if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.CHECKBOX)
                {
                    checkBox = new JCheckBox();
                    Boolean ddd = (Boolean)((CappTextAndImageCell)value).getValue();
                    ((JCheckBox)checkBox).setText((((CappTextAndImageCell)value).getStringValue() == null) ? "" : ((CappTextAndImageCell)value).getStringValue().toString());
                    ((JCheckBox)checkBox).setSelected((ddd.booleanValue() == true) ? true : false);
                    setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

                    this.removeAll();
                    checkBox.setBackground(Color.white);
                    add(checkBox);
                    ((JCheckBox)checkBox).addActionListener(CappMultiList.this);
                }else if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.RADIOBUTTON)
                {
                    checkBox = new JRadioButton();
                    Boolean ddd = (Boolean)((CappTextAndImageCell)value).getValue();
                    ((JRadioButton)checkBox).setText((((CappTextAndImageCell)value).getStringValue() == null) ? "" : ((CappTextAndImageCell)value).getStringValue().toString());
                    ((JRadioButton)checkBox).setSelected((ddd.booleanValue() == true) ? true : false);
                    setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    //setAlignment(JPanel.CENTER_ALIGNMENT);
                    //checkBox.setVisible(true);
                    this.removeAll();
                    checkBox.setBackground(Color.white);
                    add(checkBox);
                    ((JRadioButton)checkBox).addActionListener(CappMultiList.this);
                }else if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.COMBOBOX)
                {
                    Vector svs = ((CappTextAndImageCell)value).getSelectableValues();
                    checkBox = new JComboBox(svs);
                    /** 有问题，对象相等的问题 */
                    //((JComboBox)checkBox).setText((((CappTextAndImageCell)value).getStringValue()== null) ? "" : ((CappTextAndImageCell)value).getStringValue().toString());
                    Object obj = ((CappTextAndImageCell)value).getValue();
                    // ((JComboBox)checkBox).setSelectedItem((obj==null)?"":obj);
                    ((JComboBox)checkBox).setSelectedItem(obj);
                    // ((JComboBox)checkBox).addItem((obj== null) ? "" : ((CappTextAndImageCell)value).getObject().toString());
                    setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
                    this.removeAll();
                    checkBox.setBackground(Color.white);
                    if(((CappTextAndImageCell)value).getStringValue() != null)
                    {
                        label.setText((((CappTextAndImageCell)value).getStringValue() == null) ? "" : ((CappTextAndImageCell)value).getStringValue().toString());
                        add(label);
                    }
                    add(checkBox);
                    ((JComboBox)checkBox).addActionListener(CappMultiList.this);

                }
                //zz
                else if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.SPECIALCHARACTER)
                {
                    setLayout(new BorderLayout());
                    this.removeAll();
                    SpeCharaterTextPanel dd = ((CappTextAndImageCell)value).getSpecialCharacter();

                    add(dd, BorderLayout.CENTER);
                    //System.out.println("11111111111111111111111111 Zzzz");
                }

            } // 1 end if value instanceof CappTextAndImageCell
            return this;
        }
    }

    /**
     * 获得正在编辑的单元格所在的行号 guoxl
     */
    public Vector getCellEditorRowNum()
    {
        return this.CellEditorRowNumVec;
    }
//CCBegin SS4
    /**
     * 控制计划成组属性单元格编辑数据监听类
     * @author  guoxiaoliang
     *
     */
//    class myDocmentListener implements DocumentListener{
//
//    	JTable mytable;
//    	Object comvalue;
//    	boolean isSelected;
//    	int row;
//    	int column;
//    	
//    	public   myDocmentListener(JTable table, Object value, boolean isSelected, int row, int column){
//    		
//    		this.mytable=table;
//    		this.comvalue=value;
//    		this.isSelected=isSelected;
//    		this.row=row;
//    		this.column=column;
//    		
//    		System.out.println("监听*********table************"+table);
//    		
//    	}
//		
//    	// 给出属性或属性集发生了更改的通知。
//		public void changedUpdate(DocumentEvent arg0) {
//			
//			System.out.println("更改*********************");
//			
//		}
//
//		//给出对文档执行了插入操作的通知。
//		public void insertUpdate(DocumentEvent arg0) {
//			
//			row=mytable.getEditingRow();
//			column=mytable.getEditingColumn();
//			
//			System.out.println("插入*********************"+row+"=========="+column);
//			
//			
//		}
//
//		//给出移除了一部分文档的通知。
//		public void removeUpdate(DocumentEvent arg0) {
//			System.out.println("删除*********************");
//		}
//		
//		
//		
//		
//		
//    	
//    }
    //CCEnd SS4
    
    /**
     * <p>Title:内部封装 单元格编辑类 </p> <p>Description: </p> <p>Copyright: Copyright (c) 2008</p> <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    class CellEditor extends DefaultCellEditor
    {
        ImageIcon imageT;
        JComponent checkBox;
        Vector values = new Vector();
        //int hasCheckbox;
        int type;
        Object theValue;
        Object strT;
        JTextField textfield;
        JTextField textfield1;
        JLabel jlabel = new JLabel();
        JPanel jp = new JPanel();
        SpeCharaterTextPanel sepChar;
        CappTextAndImageCell cappcell;

        public CellEditor(JTextField checkBox)
        {
            super(checkBox);
            jlabel.addKeyListener(CappMultiList.this);
        }

        //CCBegin SS4
        
//        JTable mytable;
//        Object myvalue;
//        boolean isSelected;
//        int row;
//        int column;
//        
//        myDocmentListener docmentListener =new myDocmentListener(mytable,myvalue,isSelected,row,column);
        //CCEnd SS4
        
        /**
         *重载单元格的编辑组件
         * @param table JTable 当前Tab页
         * @param value Object 所选单元格内容
         * @param isSelected boolean 是否被选中
         * @param row int 所在行数
         * @param column int 所在列数
         * @return Component 该单元格组件
         */
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            //begin CR7
            editingRow = row;
            editingColumn = column;
            //end CR7
            //begin CR2
            
            //CCBegin SS4
//            this.mytable=table;
//            this.myvalue=value;
//            this.isSelected=isSelected;
//            this.row=row;
//            this.column=column;
//            System.out.println("编辑===========mytable==============="+mytable);
            //CCEnd SS4

            CellEditorRowNumVec.add(editingRow);//guoxl
            if(isNeedNoticeModified)
            {
                isModified = true;

            }
            //end CR2
            if(value == null)
            {

                jp = new JPanel();
                jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
                jlabel = new JLabel();
                jlabel.addKeyListener(CappMultiList.this);
                jlabel.setIcon(null);
                jp.add(jlabel);

            }
            if(value instanceof CappTextAndImageCell)
            {
                if(((CappTextAndImageCell)value).getImageIcon() == null)
                {
                    jlabel.setIcon(null);
                }
                if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.TEXT)
                {
                    imageT = ((CappTextAndImageCell)value).getImageIcon();
                    type = CappTextAndImageCell.TEXT;
                    theValue = null;
                    if(imageT != null)
                    {
                        jlabel.setIcon(imageT);
                    }

                    textfield = new JTextField(((CappTextAndImageCell)value).getStringValue().toString());
                    //  textfield.addKeyListener(CappMultiList.this);
                    textfield.addFocusListener(CappMultiList.this);
                    //textfield.addMouseListener(CappMultiList.this);
                    jp = new JPanel();
                    jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
                    jp.add(jlabel);
                    //jlabel.setIcon(null);
                    jp.add(textfield);
                    // textfield = new JTextField("");
                }else if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.CHECKBOX)
                {
                    type = CappTextAndImageCell.CHECKBOX;
                    jlabel.setIcon(null);
                    jp = new JPanel();

                    checkBox = new JCheckBox();
                    checkBox.addKeyListener(CappMultiList.this);
                    ((JCheckBox)checkBox).addActionListener(CappMultiList.this);
                    ((JCheckBox)checkBox).setText((((CappTextAndImageCell)value).getStringValue() == null) ? "" : ((CappTextAndImageCell)value).getStringValue().toString());
                    Object obj = ((CappTextAndImageCell)value).getValue();
                    if(((Boolean)obj).booleanValue())
                    {
                        theValue = new Boolean(true);
                        ((JCheckBox)checkBox).setSelected(true);
                    }else
                    {
                        theValue = new Boolean(false);
                        ((JCheckBox)checkBox).setSelected(false);
                    }
                    checkBox.addFocusListener(CappMultiList.this);
                    //textfield.addMouseListener(CappMultiList.this);
                    jp.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    jp.add(checkBox);
                }else if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.RADIOBUTTON)
                {
                    type = CappTextAndImageCell.RADIOBUTTON;
                    jlabel.setIcon(null);
                    jp = new JPanel();

                    checkBox = new JRadioButton();
                    checkBox.addKeyListener(CappMultiList.this);
                    ((JRadioButton)checkBox).addActionListener(CappMultiList.this);
                    //           buttonGroup.add((JRadioButton)checkBox);
                    ((JRadioButton)checkBox).setText((((CappTextAndImageCell)value).getStringValue() == null) ? "" : ((CappTextAndImageCell)value).getStringValue().toString());
                    Object obj = ((CappTextAndImageCell)value).getValue();
                    jp.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    jp.add(checkBox);
                    // buttonGroup.add((JRadioButton)checkBox);
                    if(((Boolean)obj).booleanValue())
                    {
                        theValue = new Boolean(true);
                        ((JRadioButton)checkBox).setSelected(true);
                    }else
                    {
                        theValue = new Boolean(false);
                        ((JRadioButton)checkBox).setSelected(false);
                    }

                    checkBox.addFocusListener(CappMultiList.this);
                    //  textfield.addMouseListener(CappMultiList.this);

                }else if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.COMBOBOX)
                {
                    type = CappTextAndImageCell.COMBOBOX;
                    jlabel.setIcon(null);
                    jp = new JPanel();

                    checkBox = new JComboBox(((CappTextAndImageCell)value).getSelectableValues());
                    checkBox.addKeyListener(CappMultiList.this);
                    ((JComboBox)checkBox).addActionListener(CappMultiList.this);
                    theValue = ((CappTextAndImageCell)value).getValue();
                    values = ((CappTextAndImageCell)value).getSelectableValues();
                    ((JComboBox)checkBox).setSelectedItem(((CappTextAndImageCell)value).getValue());
                    jp.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
                    if(((CappTextAndImageCell)value).getStringValue() != null)
                    {
                        textfield1 = new JTextField(((CappTextAndImageCell)value).getStringValue().toString());
                        //  textfield.addKeyListener(CappMultiList.this);
                        textfield1.addFocusListener(CappMultiList.this);
                        //  textfield.addMouseListener(CappMultiList.this);
                        // textfield1.setMinimumSize(new Dimension(30,23));
                        //  jp.add(textfield1);
                        GridBagLayout gridBagLayout1 = new GridBagLayout();
                        jp.setLayout(gridBagLayout1);
                        jp.add(textfield1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0));
                        jp.add(checkBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));
                    }
                    //jp.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
                    else
                    {
                        jp.add(checkBox);
                    }
                    checkBox.addFocusListener(CappMultiList.this);
                    //textfield.addMouseListener(CappMultiList.this);
                }
                //zz
                else if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.SPECIALCHARACTER)
                {
                    cappcell = (CappTextAndImageCell)value;
                    sepChar = ((CappTextAndImageCell)value).getSpecialCharacter();
                    type = ((CappTextAndImageCell)value).getType();
                    jp = new JPanel();
                    jp.setLayout(new BorderLayout());
                    
                    
                    //CCBegin SS4
//                    System.out.println("正在编辑***********************************");
//                    String tsvalue=sepChar.save();
//                    
//                    DataSource dataSource=sepChar.resumeData(tsvalue);
//                    
//                    String allStr=sepChar.resumeData(tsvalue).getTexts();
//                    
//                    System.out.println("tsvalue============================="+tsvalue);
//                    System.out.println("allStr============================="+allStr);
//                    
//                    byte[] len=allStr.getBytes();
//                    
//                    System.out.println("len============================="+len.length);
//                   
//                	SpecialCharacter[] speArray = dataSource.getSpechar();
//                	for(int i =0;speArray[i]!=null;i++){
//                		System.out.println("特殊符号的位置和大小loc===="+speArray[i].loc+"  -------  "+speArray[i].iWidth);
//                	}
//                    
//                	sepChar.speCharaterTextBean.getDocument().removeDocumentListener(docmentListener);
//                    sepChar.speCharaterTextBean.getDocument().addDocumentListener(docmentListener);
                    //CCEnd SS4
                    
                    jp.add(sepChar, BorderLayout.CENTER);
                    //System.out.println("zzzzzz Zzzz");
                }
            }
            return jp;
        } // end getTableCellEditorComponent

        /**
         *获得单元格的编辑值
         * @return Object 单元格的编辑值
         */
        public Object getCellEditorValue()
        {
            //begin CR7
            editingRow = -1;
            editingColumn = -1;
            //end CR7
            if(type == CappTextAndImageCell.CHECKBOX)
            {
                if(checkBox != null)
                {
                    if(((JCheckBox)checkBox).getText() != null)
                    {
                        String text = ((JCheckBox)checkBox).getText();
                        ImageIcon image = imageT;
                        imageT = null;
                        jlabel.setIcon(null);
                        Boolean value1 = new Boolean(((JCheckBox)checkBox).isSelected());
                        checkBox = null;
                        return new CappTextAndImageCell(text, image, type, value1, values);
                    }else
                    {
                        imageT = null;
                        jlabel.setIcon(null);
                        Boolean value1 = new Boolean(((JCheckBox)checkBox).isSelected());
                        checkBox = null;
                        return new CappTextAndImageCell(imageT, type, value1, values);
                    }
                }else
                {
                    imageT = null;
                    jlabel.setIcon(null);
                    return new CappTextAndImageCell("", imageT);
                }
            }else if(type == CappTextAndImageCell.TEXT)
            {
                String text = textfield.getText();
                //  textfield.setText("");
                jlabel.setIcon(null);
                ImageIcon image = imageT;
                return new CappTextAndImageCell(text, image);
            }else if(type == CappTextAndImageCell.RADIOBUTTON)
            {
                if(checkBox != null)
                {
                    if(((JRadioButton)checkBox).getText() != null)
                    {
                        String text = ((JRadioButton)checkBox).getText();
                        ImageIcon image = imageT;
                        imageT = null;
                        jlabel.setIcon(null);
                        Boolean value1 = new Boolean(((JRadioButton)checkBox).isSelected());
                        checkBox = null;
                        return new CappTextAndImageCell(text, image, type, value1, values);
                        // return new CappTextAndImageCell(text,image,type,theValue,values);
                    }else
                    {
                        imageT = null;
                        jlabel.setIcon(null);
                        Boolean value1 = new Boolean(((JRadioButton)checkBox).isSelected());
                        checkBox = null;
                        return new CappTextAndImageCell(imageT, type, value1, values);
                    }
                }else
                {
                    imageT = null;
                    jlabel.setIcon(null);
                    return new CappTextAndImageCell("", imageT);
                }

            }
            //zz
            else if(type == CappTextAndImageCell.SPECIALCHARACTER)
            {
                System.out.println("celleditor");

                // return new CappTextAndImageCell(sepChar,type);
                return cappcell;
            }else if(type == CappTextAndImageCell.COMBOBOX)
            {
                if(checkBox != null)
                {
                    if(textfield1 != null)
                    {
                        String text = textfield1.getText();
                        ImageIcon image = imageT;
                        imageT = null;
                        jlabel.setIcon(null);
                        return new CappTextAndImageCell(text, image, type, ((JComboBox)checkBox).getSelectedItem(), values);
                    }else
                    {
                        imageT = null;
                        jlabel.setIcon(null);
                        // checkBox = null;
                        return new CappTextAndImageCell(imageT, type, ((JComboBox)checkBox).getSelectedItem(), values);
                    }
                }else
                {
                    imageT = null;
                    jlabel.setIcon(null);
                    return new CappTextAndImageCell("", imageT);
                }
            }

            /*
             * else if (type==CappTextAndImageCell.COMBOBOX) { if (checkBox!=null) { imageT = null; jlabel.setIcon(null);
             * 
             * // checkBox = null; return new CappTextAndImageCell(imageT,type,((JComboBox)checkBox).getSelectedItem(),values); } else { imageT = null; jlabel.setIcon(null); return new
             * CappTextAndImageCell("",imageT); } }
             */
            return null;

        }

    }

    /**
     * <p>Title:内部封装 单元格编辑类 </p> <p>Description: </p> <p>Copyright: Copyright (c) 2008</p> <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    class MultiCellEditor extends DefaultCellEditor
    {
        ImageIcon imageT;
        JComponent checkBox;
        int type;

        //zz
        SpeCharaterTextPanel speChar;
        //zz
        CappTextAndImageCell cappcell;
        Object theValue;
        Vector values;
        Object strT;
        JTextField textfield;
        JTextField textfield1;
        JLabel jlabel = new JLabel();
        JPanel jp = new JPanel();

        public MultiCellEditor(JCheckBox checkBox)
        {
            super(checkBox);
            jlabel.addKeyListener(CappMultiList.this);
        }

        /**
         * 重载单元格的编辑组件
         * @param table JTable 当前Tab页
         * @param value Object 所选单元格内容
         * @param isSelected boolean 是否被选中
         * @param row int 当前行数
         * @param column int 当前列数
         * @return Component 单元格编辑组件
         */
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
        {
            //begin CR2
            if(isNeedNoticeModified)
            {
                isModified = true;

            }
            //end CR2
            tableRow = row;
            tableColumn = column;

            if(value == null)
            {
                jp = new JPanel();
                jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
                jlabel = new JLabel();
                jlabel.setIcon(null);
                jp.add(jlabel);

            }
            if(value instanceof CappTextAndImageCell)
            {
                if(((CappTextAndImageCell)value).getImageIcon() == null)
                {
                    jlabel.setIcon(null);
                }
                if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.TEXT)
                {
                    imageT = ((CappTextAndImageCell)value).getImageIcon();
                    type = CappTextAndImageCell.TEXT;
                    theValue = null;
                    if(imageT != null)
                    {
                        jlabel.setIcon(imageT);
                    }
                    textfield = new JTextField(((CappTextAndImageCell)value).getStringValue().toString());

                    textfield.addFocusListener(CappMultiList.this);
                    jp = new JPanel();
                    jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
                    jp.add(jlabel);

                    jp.add(textfield);

                }else if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.CHECKBOX)
                {
                    type = CappTextAndImageCell.CHECKBOX;
                    jlabel.setIcon(null);
                    jp = new JPanel();
                    jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
                    checkBox = new JCheckBox();
                    checkBox.addKeyListener(CappMultiList.this);
                    ((JCheckBox)checkBox).addActionListener(CappMultiList.this);
                    ((JCheckBox)checkBox).setText((((CappTextAndImageCell)value).getStringValue() == null) ? "" : ((CappTextAndImageCell)value).getStringValue().toString());
                    Object obj = ((CappTextAndImageCell)value).getValue();
                    if(((Boolean)obj).booleanValue())
                    {
                        theValue = new Boolean(true);
                        ((JCheckBox)checkBox).setSelected(true);
                    }else
                    {
                        theValue = new Boolean(false);
                        ((JCheckBox)checkBox).setSelected(false);
                    }
                    returnCheckBox = checkBox;

                    jp.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    jp.setBackground(Color.white);
                    checkBox.setBackground(Color.white);

                    jp.add(checkBox);
                    checkBox.addFocusListener(CappMultiList.this);

                }else if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.RADIOBUTTON)
                {
                    type = CappTextAndImageCell.RADIOBUTTON;
                    jlabel.setIcon(null);
                    jp = new JPanel();
                    jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
                    checkBox = new JRadioButton();
                    checkBox.addKeyListener(CappMultiList.this);
                    ((JRadioButton)checkBox).addActionListener(CappMultiList.this);
                    ((JRadioButton)checkBox).setText((((CappTextAndImageCell)value).getStringValue() == null) ? "" : ((CappTextAndImageCell)value).getStringValue().toString());
                    Object obj = ((CappTextAndImageCell)value).getValue();
                    jp.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
                    jp.setBackground(Color.white);
                    checkBox.setBackground(Color.white);
                    jp.add(checkBox);

                    if(((Boolean)obj).booleanValue())
                    {
                        theValue = new Boolean(true);
                        ((JRadioButton)checkBox).setSelected(true);
                    }else
                    {
                        theValue = new Boolean(false);
                        ((JRadioButton)checkBox).setSelected(false);
                    }
                    returnCheckBox = checkBox;
                    checkBox.addFocusListener(CappMultiList.this);

                }else if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.COMBOBOX)
                {
                    type = CappTextAndImageCell.COMBOBOX;
                    jlabel.setIcon(null);
                    jp = new JPanel();
                    jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
                    values = ((CappTextAndImageCell)value).getSelectableValues();
                    checkBox = new JComboBox(values);
                    checkBox.addKeyListener(CappMultiList.this);
                    ((JComboBox)checkBox).addActionListener(CappMultiList.this);
                    theValue = ((CappTextAndImageCell)value).getValue();
                    ((JComboBox)checkBox).setSelectedItem(((CappTextAndImageCell)value).getValue());
                    returnCheckBox = checkBox;

                    jp.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));

                    if(((CappTextAndImageCell)value).getStringValue() != null)
                    {
                        textfield1 = new JTextField(((CappTextAndImageCell)value).getStringValue().toString());
                        textfield1.addFocusListener(CappMultiList.this);
                        GridBagLayout gridBagLayout1 = new GridBagLayout();
                        jp.setLayout(gridBagLayout1);
                        jp.add(textfield1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 5), 0, 0));
                        jp.add(checkBox, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 5), 0, 0));

                    }else
                    {
                        jp.add(checkBox);
                    }
                    checkBox.addFocusListener(CappMultiList.this);

                }
                //zz
                else if(((CappTextAndImageCell)value).getType() == CappTextAndImageCell.SPECIALCHARACTER)
                {
                    cappcell = (CappTextAndImageCell)value;
                    jp = new JPanel();
                    type = ((CappTextAndImageCell)value).getType();
                    jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
                    speChar = ((CappTextAndImageCell)value).getSpecialCharacter();
                    jp.add(speChar);
                }

            }
            return jp;
        } // end getTableCellEditorComponent

        /**
         * 获得单元格的编辑值
         * @return Object 单元格的编辑值
         */
        public Object getCellEditorValue()
        {
            if(type == CappTextAndImageCell.CHECKBOX)
            {
                if(checkBox != null)
                {
                    if(((JCheckBox)checkBox).getText() != null)
                    {
                        String text = ((JCheckBox)checkBox).getText();
                        ImageIcon image = imageT;
                        imageT = null;
                        jlabel.setIcon(null);
                        Boolean value1 = new Boolean(((JCheckBox)checkBox).isSelected());
                        checkBox = null;
                        return new CappTextAndImageCell(text, image, type, value1, values);
                    }else
                    {
                        imageT = null;
                        jlabel.setIcon(null);
                        Boolean value1 = new Boolean(((JCheckBox)checkBox).isSelected());
                        checkBox = null;
                        return new CappTextAndImageCell(imageT, type, value1, values);
                    }
                }else
                {
                    imageT = null;
                    jlabel.setIcon(null);
                    return new CappTextAndImageCell("", imageT);
                }
            }else if(type == CappTextAndImageCell.TEXT)
            {
                String text = textfield.getText();

                jlabel.setIcon(null);
                ImageIcon image = imageT;
                return new CappTextAndImageCell(text, image);
            }
            //zz
            else if(type == CappTextAndImageCell.SPECIALCHARACTER)
            {

                // System.out.println("celleditor2");
                // return new CappTextAndImageCell(speChar, type);
                return cappcell;
            }else if(type == CappTextAndImageCell.RADIOBUTTON)
            {
                if(checkBox != null)
                {
                    if(((JRadioButton)checkBox).getText() != null)
                    {
                        String text = ((JRadioButton)checkBox).getText();
                        ImageIcon image = imageT;
                        imageT = null;
                        jlabel.setIcon(null);
                        Boolean value1 = new Boolean(((JRadioButton)checkBox).isSelected());
                        checkBox = null;
                        return new CappTextAndImageCell(text, image, type, value1, values);

                    }else
                    {
                        imageT = null;
                        jlabel.setIcon(null);
                        Boolean value1 = new Boolean(((JRadioButton)checkBox).isSelected());
                        checkBox = null;
                        return new CappTextAndImageCell(imageT, type, value1, values);
                    }
                }else
                {
                    imageT = null;
                    jlabel.setIcon(null);
                    return new CappTextAndImageCell("", imageT);
                }

            }else if(type == CappTextAndImageCell.COMBOBOX)
            {
                if(checkBox != null)
                {
                    if(textfield1 != null)
                    {
                        String text = textfield1.getText();
                        ImageIcon image = imageT;
                        imageT = null;
                        jlabel.setIcon(null);
                        String value1 = (String)((JComboBox)checkBox).getSelectedItem();
                        checkBox = null;
                        return new CappTextAndImageCell(text, image, type, value1, values);
                    }else
                    {
                        imageT = null;
                        jlabel.setIcon(null);
                        String value1 = (String)((JComboBox)checkBox).getSelectedItem();
                        checkBox = null;
                        return new CappTextAndImageCell(imageT, type, value1, values);
                    }
                }else
                {
                    imageT = null;
                    jlabel.setIcon(null);
                    return new CappTextAndImageCell("", imageT);
                }
            }
            /*
             * else if (type == CappTextAndImageCell.COMBOBOX) { if (checkBox!=null) { imageT = null; jlabel.setIcon(null); // checkBox = null; return new
             * CappTextAndImageCell(imageT,type,((JComboBox)checkBox).getSelectedItem(),values); } else { imageT = null; jlabel.setIcon(null); return new CappTextAndImageCell("",imageT); }
             * 
             * }
             */
            return null;

        }
    }

    /**
     * 获得CheckBox组件
     * @return JComponent CheckBox组件
     */
    public JComponent getCheckBox()
    {
        return returnCheckBox;
    }

    /**
     * 获得当前行数
     * @return tableRow 当前行数
     */
    public int getRow()
    {
        return tableRow;
    }

    /**
     * 获得表格列数
     * @return int 表格列数
     */
    public int getRowCount()
    {
        return table.getRowCount();
    }

    /**
     * 获得当前列数
     * @return tableColumn 当前列数
     */
    public int getCol()
    {
        return tableColumn;
    }

    /**
     * <p>Title:内部封装 表头监听类 </p> <p>Description: </p> <p>Copyright: Copyright (c) 2008</p> <p>Company: </p>
     * @author not attributable
     * @version 1.0
     */
    class HeaderListener extends MouseAdapter
    {
        JTableHeader header;
        AbstractTableModel tableModel;
        SortButtonRenderer renderer;

        HeaderListener(JTableHeader header, SortButtonRenderer headerRenderer)
        {
            this.header = header;
            this.renderer = headerRenderer;
        }

        //按下时
        public void mousePressed(MouseEvent e)
        {
            if(isAllowSorting())
            {

                int col = header.columnAtPoint(e.getPoint());
                int sortCol = header.getTable().convertColumnIndexToModel(col);
                //记录按下的列号
                if(col < 0 || table.getRowCount() <= 0)
                {
                    return;
                }
                renderer.setPressedColumn(col);
                //某一列排序的状态
                renderer.setSelectedColumn(col);
                header.repaint();

                //如果有正在编辑的单元格，停止编辑方式。
                if(header.getTable().isEditing())
                {
                    header.getTable().getCellEditor().stopCellEditing();

                }
                //排序
                boolean isAscent = true;
                if(SortButtonRenderer.DOWN == renderer.getState(col))
                {
                    isAscent = true;
                }else
                {

                    isAscent = false;

                    /**
                     * //获取己选中的行 int[] selectedRows = header.getTable().getSelectedRows(); //获取数据源行号 for(int i=0; i<selectedRows.length; i++) selectedRows[i] =
                     * ((CappSortableTableModel)header.getTable().getModel()).getSelectDataSourceRowNum(selectedRows[i]); //排序
                     * ((CappSortableTableModel)header.getTable().getModel()).sortByColumn(sortCol, isAscent); //恢复选中的行 for (int i=0;i<selectedRows.length; i++) { int temp =
                     * ((CappSortableTableModel)header.getTable().getModel()).getAfeterSortTargetRowNum(selectedRows[i]); header.getTable().addRowSelectionInterval(temp,temp); }
                     */

                }
                ((CappSortableTableModel)header.getTable().getModel()).sortByColumn(sortCol, isAscent);
                //重新设置行高
                initalHeight();
            }

        }

        //松开时
        public void mouseReleased(MouseEvent e)
        {
            if(isAllowSorting())
            {

                int col = header.columnAtPoint(e.getPoint());
                //记录按下的列号 -1 代表clear
                renderer.setPressedColumn(-1); // clear
                header.repaint();
            }
        }
    }

    //定义表模式
    CappSortableTableModel mtModel = new CappSortableTableModel()
    {
        //重载方法
        public boolean isCellEditable(int row, int col)
        {
            boolean myFlagTrue = true;
            boolean myFlagFalse = false;
            if(disCols == null)
            {
                return editFlag;
            }else
            {
                if(disFlag)
                {
                    for(int i = 0;i < disCols.length;i++)
                    {
                        if(col == disCols[i])
                        {
                            myFlagFalse = true;
                            break;
                        }

                    }
                    return myFlagFalse;
                }else
                {
                    for(int i = 0;i < disCols.length;i++)
                    {
                        if(col == disCols[i])
                        {
                            myFlagTrue = false;
                            break;
                        }

                    }
                    return myFlagTrue;

                }

            }
        }

        public Class getColumnClass(int columnIndex)
        {
            return CappTextAndImageCell.class;
        }
    };

    /**
     * 设置默认行高
     * @param rowHeight 默认行高
     */
    public void setDefaultRowHeight(int rowHeight)
    {
        defaulRowHeight = rowHeight;
    }

    /**
     *返回默认行高
     * @return 默认行高
     */
    public int getDefaultRowHeight()
    {
        return defaulRowHeight;
    }

    /**
     *设置单元格是否可编辑
     * @param editFlag 是否可编辑
     */

    public void setCellEditable(boolean editFlag)
    {
        this.editFlag = editFlag;
    }

    /**
     * 设置列不可编辑 　@param cols[] 不可编辑列数组
     */

    public void setColsEnabled(int[] cols, boolean disFlag)
    {
        this.disCols = cols;
        this.disFlag = disFlag;

    }

    /**
     * 得到单元格是否可编辑
     * @return 单元格是否可编辑
     */
    public boolean isCellEditable()
    {
        return editFlag;
    }

    /*
     * class Focus implements FocusListener, java.io.Serializable { public void focusGained(FocusEvent e) { table.requestFocus();
     * 
     * }
     * 
     * public void focusLost(FocusEvent e) {
     * 
     * } }
     */

    public void valueChanged(ListSelectionEvent listEvent)
    {

        notifyItemListeners(new ItemEvent(this, 0, (Object)null, ItemEvent.SELECTED));

    }

    public void focusLost(FocusEvent e)
    {
        Object a = e.getSource();
        int row = this.oldrow;
        int col = this.oldcol;

        if(table.isEditing() && !(a instanceof JComboBox))
        {
            table.getCellEditor().stopCellEditing();
            //table.removeEditor();
        }

        if(a instanceof JTextField)
        {
            notifyActionListeners(new ActionEvent(e.getComponent(), ActionEvent.ACTION_PERFORMED, actionCommand + row + ";" + "ColSelected:" + col));
        }
        if(a instanceof JComboBox)
        {
            //begin CR8
            //            this.getCellAt(row, col).setValue(((JComboBox) a).getSelectedItem());
            if(editingRow != -1 && editingColumn != -1)
                this.getCellAt(editingRow, editingColumn).setValue(((JComboBox)a).getSelectedItem());
            //end CR8
            //table.getCellEditor().stopCellEditing();
        }
        table.repaint();
        // table.validate();

    }

    public void focusGained(FocusEvent e)
    {
        //20081209 徐春英修改   修改原因：采用手工输入编号后回车搜索的方式添加的，
        //依次输入资源A、B、C，但得到的显示结果偶尔会出现A、B、B的现象。
        oldrow = getSelectedRow();
    }

    public void actionPerformed(ActionEvent e)
    {
        Object a = e.getSource();
        if(a instanceof JCheckBox || a instanceof JRadioButton || a instanceof JComboBox)
        {
            if(a instanceof JRadioButton || a instanceof JCheckBox)
            {
                if(((JToggleButton)a).isSelected())
                {
                    int row = this.getSelectedRow();
                    int col = this.getSelectedCol();
                    if(a instanceof JRadioButton)
                    {
                        for(int i = 0;i < this.table.getRowCount();i++)
                        {
                            if(i != row)
                            {
                                getCellAt(i, col).setValue(new Boolean(false));
                            }else
                            {
                                getCellAt(i, col).setValue(new Boolean(true));
                            }
                        }
                        this.repaint();
                    }
                    e = new ActionEvent(e.getSource(), 0, actionCommand + row + ";" + "ColSelected:" + col);
                }
            }
            //begin CR7
            if(a instanceof JComboBox)
            {
                JComboBox box = (JComboBox)a;
                CappTextAndImageCell cat = this.getCellAt(editingRow, editingColumn);
                cat.setValue(box.getSelectedItem());
                //begin CR9
                if(getTable().isEditing())
                {
                    getTable().getCellEditor().stopCellEditing();
                } //end cr9   
            }
            //end CR7
            notifyActionListeners(e);
        }

    }

    /**
     * 实现鼠标单击事件
     * @param e 鼠标事件
     */
    public void mouseClicked(MouseEvent e)
    {
        Component a = e.getComponent();
        if(a instanceof JTable)
        {
            if(e.getClickCount() == 2)
            {
                System.out.println("xxs111222");
                notifyActionListeners(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand + getSelectedRow()));

            }
        }

    }

    public void mousePressed(MouseEvent e)
    {
        this.changeSelectedPosition();
        /*
         * boolean editable= table.isCellEditable(newrow, newcol); if(editable) { java.util.EventObject eventObject = new java.util.EventObject(this); table.editCellAt(newrow, newcol, eventObject);
         * java.awt.Component component = table.getEditorComponent(); System.out.println("可编辑的组建;" + component); if (component instanceof JPanel)
         * 
         * { Component d= ((JPanel)component).getComponent(1); System.out.println("可编辑的组建2;" + d); if (d instanceof javax.swing.JTextField) { JTextField strField = (javax.swing.JTextField) d;
         * System.out.println("可编辑的组建3;" + strField.getText());
         * 
         * strField.grabFocus(); // strField.removeFocusListener(lSymTextFocus); //strField.addFocusListener(lSymTextFocus); } //end if(component instanceof javax.swing.JTextField) } }
         */

    }

    public void mouseEntered(MouseEvent e)
    {

    }

    /**
     * 鼠标释放
     */
    public void mouseReleased(MouseEvent e)
    {
        Vector vector;
        synchronized(actionListeners)
        {
            vector = (Vector)actionListeners.clone();
        }
        for(int i = 0;i < vector.size();i++)
        {
            Object listener = (Object)vector.elementAt(i);
            if(listener instanceof MouseListener)
            {
                ((MouseListener)listener).mouseReleased(e);
            }
        }
    }

    public void mouseExited(MouseEvent e)
    {}

    /**
     * 增加动作事件监听
     * @param actionlisener 动作事件监听
     */
    public synchronized void addActionListener(ActionListener actionlistener)
    {
        if(actionListeners == null)
        {
            actionListeners = new Vector();
        }
        actionListeners.addElement(actionlistener);
    }

    /**
     * 移去动作事件监听
     * @param actionlisener 动作事件监听
     */
    public synchronized void removeActionListener(ActionListener actionlistener)
    {
        if(actionListeners != null)
        {
            actionListeners.removeElement(actionlistener);
        }
    }

    /**
     * 增加项目事件监听
     * @param itemlistener 项目事件监听
     */
    public synchronized void addItemListener(ItemListener itemlistener)
    {
        if(itemListeners == null)
        {
            itemListeners = new Vector();
        }
        itemListeners.addElement(itemlistener);
        table.getSelectionModel().addListSelectionListener(this);
    }

    public void setCheckboxEditable(boolean checkedit)
    {
        checkboxEditable = checkedit;
    }

    /**
     * public void setRadioButtonEditable(boolean radioedit) { this.radioButtonEditable = radioedit; } public void setComboBoxEditable(boolean radioedit) { this.ComboBoxEditable = radioedit; }
     */
    public boolean getCheckboxEditable()
    {
        return checkboxEditable;
    }

    /**
     * 移去项目事件监听
     * @param itemlistener 项目事件监听
     */
    public synchronized void removeItemListener(ItemListener itemlistener)
    {
        if(itemListeners != null)
        {
            itemListeners.removeElement(itemlistener);

        }
    }

    private void notifyItemListeners(AWTEvent awtevent)
    {
        Vector vector;
        if(itemListeners != null)
        {
            synchronized(itemListeners)
            {
                vector = (Vector)itemListeners.clone();
            }
            for(int i = 0;i < vector.size();i++)
            {
                Object listener = (Object)vector.elementAt(i);
                if(listener instanceof ItemListener)
                {
                    //processItemEvent(listener, (ItemEvent)awtevent);
                    ((ItemListener)listener).itemStateChanged((ItemEvent)awtevent);
                }

            }
        }
    }

    public ListSelectionModel getSelectModel()
    {
        return table.getSelectionModel();
    }

    private void notifyActionListeners(AWTEvent awtevent)
    {
        Vector vector;
        synchronized(actionListeners)
        {
            vector = (Vector)actionListeners.clone();
        }
        for(int i = 0;i < vector.size();i++)
        {
            Object listener = (Object)vector.elementAt(i);
            String command = ((ActionEvent)awtevent).getActionCommand();
            //动作监听
            if(listener instanceof ActionListener)
            {
                //processActionEvent(listener, (ActionEvent)awtevent);
                ((ActionListener)listener).actionPerformed((ActionEvent)awtevent);
            }
            //如果鼠标双击选择行
            if(command.startsWith("RowSelected") && command.indexOf("ColSelected") < 0)
                ((MouseListener)listener).mouseClicked(null);
        }

    }

    protected void processItemEvent(Object listener, ItemEvent e)
    {
        if(listener != null)
        {
            ((ItemListener)listener).itemStateChanged(e);
        }
    }

    protected void processActionEvent(Object listener, ActionEvent e)
    {
        if(listener != null)
        {
            ((ActionListener)listener).actionPerformed(e);
        }
    }

    // class CKeyListener implements KeyListener{
    public void keyPressed(KeyEvent e)
    {
        Component a = e.getComponent();
        //System.out.println("xx dd ");
        // if (a instanceof JTable) {
        notifyKeyListeners(e);
        // notifyKeyListeners(new KeyEvent(this, ActionEvent.ACTION_PERFORMED, actionCommand+getSelectedRow()));
        // notifyKeyListeners(new KeyEvent(this,e.getID(),e.getWhen(), e.getModifiers(),e.getKeyCode()));
        // }
    }

    public void keyReleased(KeyEvent e)
    {}

    public void keyTyped(KeyEvent e)
    {

    }

    /**
     * 增加动作事件监听
     * @param actionlisener 动作事件监听
     */
    public synchronized void addKeyListener(KeyListener keylistener)
    {
        if(keylinsteners == null)
        {
            keylinsteners = new Vector();
        }
        keylinsteners.addElement(keylistener);
    }

    /**
     * 移去动作事件监听
     * @param actionlisener 动作事件监听
     */
    public synchronized void removeKeyListener(KeyListener keyListener)
    {
        if(keylinsteners != null)
        {
            keylinsteners.removeElement(keyListener);
        }
    }

    public void notifyKeyListeners(KeyEvent e)
    {

        Vector vector;
        synchronized(keylinsteners)
        {
            vector = (Vector)keylinsteners.clone();
        }

        for(int i = 0;i < vector.size();i++)
        {

            Object listener = (Object)vector.elementAt(i);
            if(listener instanceof KeyListener)
            {
                ((KeyListener)listener).keyPressed((KeyEvent)e);
            }
        }
    }

    public void changeSelectedPosition()
    {
        //zz 20051025
        if(this.getSelectedRow() == -1 || this.getSelectedCol() == -1)
        {
            return;
        }
        this.oldcol = this.newcol;
        this.oldrow = this.newrow;
        this.newrow = this.getSelectedRow();
        this.newcol = this.getSelectedCol();

        oldCell_pre = oldCell_next;
        oldCell_next = this.getCellAt(newrow, newcol);
    }

    //yangxh update --------------------------------------------------
    public void paint(Graphics g)
    {
        //20081208 徐春英修改  修改原因:拉长某属性列的列宽,
        //在该属性框中输入汉字,回车后界面恢复未拉长状态,并且输入的汉字丢失.
        boolean tstflag = this.getTable().isEditing();
        int wid = this.getWidth();
        listwidth = 0; //lilei update
       
        if(wid != listwidth && relColWidth != null)
        {
        	//CCBegin SS3
        	//增加判断是否是自适应
        	if(!tstflag && !isAdjust){
         	   isAdjust = true;
         	   adjustWidth();
            }
        	/*
            if(!tstflag)
            {
                adjustWidth();
            }
            */
            //CCEnd SS3
            listwidth = wid; 
        }
        super.paint(g);
        if(relColWidth != null)
        {
        	//CCBegin SS3
        	if(!tstflag && !isAdjust){
         	   isAdjust = true;
         	   resetWidth();
            }
        	/*
            if(!tstflag)
            {
                resetWidth();
            }
            */
            //CCEnd SS3
        }
    }

    public void setRelColWidth(int[] relWidths)
    {
        relColWidth = relWidths;
        adjustWidth();
    }

    public int[] getRelColWidth()
    {
        return relColWidth;
    }

    /*
     * *调整宽度
     */
    //xuejing modified 列宽为标题的象素长度+50个象素,如果所有列宽的和小于列表的宽度,则按比例分配列宽

    private void adjustWidth()
    {
        /*
         * if(relColWidth != null) { int total = 0; for(int i = 0;i < relColWidth.length;i++) { total = total + relColWidth[i]; } if(total != 0) { int width = getWidth();
         * System.out.println("width==="+width); int[] rel = new int[relColWidth.length]; for(int i = 0;i < relColWidth.length;i++) { rel[i] = width * relColWidth[i] / total; } setColumnSizes(rel); }
         * }
         */

        if(relColWidth != null)
        {
            int n = relColWidth.length;

            int total = 0;
            for(int i = 0;i < n;i++)
            {
                total = total + relColWidth[i];
            }
            if(total != 0)
            {
                int width = getWidth();
                String[] headings = this.getHeadings(); //列宽 
                int[] rel = new int[n];
                int totalLenthOfHeadings = 0;
                if(headings.length > 0)
                {
                    for(int i = 0;i < n;i++)
                    {
                        if(relColWidth[i] != 0)
                        {
                       
                            rel[i] = this.getStringLenth(headings[i]) + 50;
                        	 
                            
                           
                            totalLenthOfHeadings = rel[i] + totalLenthOfHeadings;
                        }else
                        {
                            rel[i] = 0;
                        }
                    }
                }

                if(totalLenthOfHeadings < width)
                {
                    for(int i = 0, l = rel.length;i < l;i++)
                    {
                        rel[i] = width * rel[i] / totalLenthOfHeadings;
                    }
                    setColumnSizes(rel);
                }else
                {
                    setColumnSizes(rel);
                }
            }
        }
        /*int i = 0;
        int j = minColumnWidth;
        for (int k = 0,l=relColWidth.length; k < l; k++)
        {
            i += (new Integer(relColWidth[k])).intValue();

        }
        if (i != 0)
        {
            int l = getSize().width / i;
            if (l > minColumnWidth)
            {
                j = l;
            }
        }
        String list[] = new String[relColWidth.length];
        for (int i1 = 0,m = relColWidth.length; i1 < m; i1++)
        {
            int j1 = j * (new Integer(relColWidth[i1])).intValue();
            list[i1] = (new Integer(j1)).toString();
        }

        //this.setColumnSizes(as);
        if (list != null && list.length > 0)
        {
            columnSizes = new int[list.length];
            for (int m = 0,n=list.length; m < n; m++)
            {
                columnSizes[m] = 0;
                int stringValue = 100;
                try
                {
                    String listElem = list[m];
                    stringValue = Integer.parseInt(listElem);
                    if (stringValue == 0)
                    {
                        table.getColumnModel().getColumn(m).setMaxWidth(
                                stringValue);
                        table.getColumnModel().getColumn(m).setPreferredWidth(
                                stringValue);
                        table.getTableHeader().getColumnModel().getColumn(m).
                                setMinWidth(stringValue);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                columnSizes[m] = stringValue;
            }
        }*/

    }

    /*
     * *设置宽度
     * 
     * @param int[] list 各单元宽度数组集合
     */
    private void setColumnSizes(int[] list)
    {
        if(list != null && list.length > 0)
        {
            for(int i = 0;i < list.length;i++)
            {
                int wid = list[i];
                TableColumn tc = table.getColumnModel().getColumn(i);
                if(wid == 0)
                {
                    tc.setMaxWidth(wid);
                    tc.setPreferredWidth(wid);
                    tc.setMinWidth(wid);
                }else
                {
                    tc.setPreferredWidth(wid);
                    tc.setMinWidth(2);
                    tc.setMaxWidth(Integer.MAX_VALUE);
                    //tc.setMinWidth(wid);

                }
                this.setTableColumn(tc);
            }
            columnSizes = list;
        }
    }

    /**
     *该Bean的构造函数
     *@return JFrame 使用该Bean的窗口
     */
    protected JFrame getParentFrame()
    {
        Component parent = getParent();
        if(parent == null)
        {
            return null;
        }
        while(parent != null && !(parent instanceof JFrame))
        {
            parent = parent.getParent();
        }
        return (JFrame)parent;
    }

    private void setTableColumn(TableColumn tab)
    {

        col = tab;
    }

    public void resetWidth()
    {
        if(relColWidth != null)
        {
            for (int i = 0,j = relColWidth.length; i < j; i++)
            {
                int wid = relColWidth[i];
                TableColumn tc = table.getColumnModel().getColumn(i);
                if (wid != 0)
                {
                    tc.setMinWidth(0);
                    tc.setMaxWidth(Integer.MAX_VALUE);
                }
                else
                {
                    tc.setMaxWidth(wid);
                    tc.setPreferredWidth(wid);
                    tc.setMinWidth(wid);
                }
                tc.sizeWidthToFit();
            }
        }
    }

    /**
     * 对表格添加鼠标监听
     * @param listener MouseListener
     */
    public void addTableMouseListener(MouseListener listener)
    {
        this.table.addMouseListener(listener);
    }

    public void undoCell()
    {
        if(oldCell_pre == null)
        {
            this.addCell(this.oldrow, this.oldcol, this.oldCell_next);
        }else
        {
            this.addCell(this.oldrow, this.oldcol, this.oldCell_pre);
        }
    }

    /**
     * 获得字符串的象素长,宽
     * @param g Graphics2D
     */
    private int getStringLenth(String message)
    {
        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(getFont());
        //获取字符串总长度
        return fm.stringWidth(message);

    }

    /*********************** 变量表 *******************************************/
    /**
     * 列标题.
     */
    protected String headings[];

    /**
     * 是否可以多选行. Default is false.
     */
    protected boolean multiSelect = false;
    protected Vector data = new Vector(); //表格所需数据内容
    protected Vector heading = new Vector(); //表头
    protected JTable table; //生成表格 
    protected Color colorBg = Color.white; //背景颜色
    protected Color colorFg = Color.black; //前景颜色
    protected Color colorHBg = new Color(0, 0, 128); //选择时的背景颜色
    protected Color colorHFg = Color.white; //选择时的前景颜色
    protected Color headingBg = java.awt.SystemColor.control; //表头背景颜色
    protected Color headingFg = java.awt.SystemColor.controlText; //表头前景颜色
    protected boolean allowSorting = true; //是否排序
    protected Font headingFont; //表头字体
    protected Font cellFont; //单元格字体
    protected int minColumnWidth = 10; //最小列宽
    protected boolean allowResizingOfColumns = true; //是否可以调整列
    protected boolean editFlag = true; //单元格是否可编辑
    protected int defaulRowHeight = 20; //默认行高
    protected int preRow = -1; //未选时为-1
    protected Vector itemListeners = new Vector(); //项目事件监听
    protected Vector actionListeners = new Vector(); //动作事件监听

    private int oldrow = -1;
    private int oldcol = -1;
    private int newrow = -1;
    private int newcol = -1;
    private String oldText = "";
    private CappTextAndImageCell oldCell_pre = null;
    private CappTextAndImageCell oldCell_next = null;

    /**
     * 列的对齐方式 即LEFT, CENTER, or RIGHT.
     */
    protected int columnAlignments[];

    /**
     * 默认列表的对齐方式.
     */
    protected int defaultColumnAlignment = 0;

    /**
     * 列宽. 如为null,则分配为默认列宽.
     */
    protected int columnSizes[] = null;

    /*
     * 升序排序
     */
    protected boolean sortAscending = true;

    /**
     * Is the heading visible. Default is true.
     */
    protected boolean headingVisible = true;
    transient protected ResourceBundle errors;
    protected ActionListener actionListener = null;

    /**
     * The action listener to keep track of listeners for our item event.
     */
    protected ItemListener itemListener = null;

    /**
     * The action listener to keep track of listeners for our focus event.
     */
    protected FocusListener focusListener = null;

    protected String actionCommand = "RowSelected:";

    //Event listeners
    // private Focus				focus		= null;
    private VetoableChangeSupport vetos = new VetoableChangeSupport(this);
    private PropertyChangeSupport changes = new PropertyChangeSupport(this);

    /**
     * The heading height in pixels. If the heading is showing, it includes one pixel for the border (they overlap).
     */
    protected int headingHeight = 0;

    /**
     * The most recently selected row.
     */
    protected int selectedRow = -1;

    /**
     * Left-justify column alignment constant.
     */
    public final static int LEFT = JLabel.LEFT;

    /**
     * Center-justify column alignment constant.
     */
    public final static int CENTER = JLabel.CENTER;

    /**
     * Right-justify column alignment constant.
     */
    public final static int RIGHT = JLabel.RIGHT;

    /**
     *
     */
    protected JScrollPane js;
    protected int[] disCols;
    protected boolean disFlag;
    public int tableRow;
    public int tableColumn;
    protected boolean checkboxEditable = false;
    protected boolean radioButtonEditable = false;
    protected boolean ComboBoxEditable = false;
    public JComponent returnCheckBox;

    //yangxh update---------------
    private int[] relColWidth;
    private int listwidth;
    private Vector keylinsteners = new Vector();
    private TableColumn col;
    private Vector mouselinsteners = new Vector();
    //CCBegin SS1
    protected int[] disRows = null;
    //CCEnd SS1
    /**
     * 内部类：重写用作 event 的工具提示的字符串的方法
     * @author xucy
     */
    //begin CR3
    class CappTable extends JTable
    {
        public CappTable(DefaultTableModel model)
        {
            super(model);
        }

        public String getToolTipText(MouseEvent event)
        {
            String str = "";
            String str1 = "";
            Point p = event.getPoint();
            int rowIndex = rowAtPoint(p);
            int colIndex = columnAtPoint(p);
            str = getSelectedTipValue(rowIndex, colIndex);
            if(str != null && !str.equals(""))
            {
                int len = str.length();
                if(len <= 50)
                {
                    return str;
                }else
                {
                    int count = len / 50;
                    for(int i = 0;i < count;i++)
                    {
                        String str2 = "";
                        if(len >= 50 * (i + 1))
                        {
                            str2 = str.substring(50 * i, 50 * (i + 1)) + "<br>";
                        }

                        str1 = str1 + str2;
                    }
                    str1 = str1 + str.substring(50 * count, len);

                }
            }
            return "<html>" + str1 + "</html>";
        }
    }//end CR3

    /**
     * 得到当前表格选中单元格的内容
     * @param i 当前行数
     * @param j 当前列数
     * @return String 选中的对象
     */
    //begin  CR3
    public String getSelectedTipValue(int i, int j)
    {
        CappTextAndImageCell cell = this.getCellAt(i, j);
        if(cell != null)
        {
            if(cell.getType() == CappTextAndImageCell.TEXT)
            {
                return cell.getStringValue();
            }
            if(cell.getType() == CappTextAndImageCell.SPECIALCHARACTER)
            {
                Vector vector = new Vector(1);
                vector.addElement(cell.getSpecialCharacter().save());

                return DataDisplay.translate(vector);
            }
            if(cell.getType() == CappTextAndImageCell.COMBOBOX)
            {
               //CCBegin SS2
            	//String temp = getCellAt(i, j).getStringValue() +
                //";" +(String)getCellAt(i, j).getValue();
                String temp =(String)getCellAt(i, j).getValue();
                //CCEnd SS2

                return temp.replace(";", " ");
            }
            if(cell.getType() == CappTextAndImageCell.RADIOBUTTON || cell.getType() == CappTextAndImageCell.CHECKBOX)
            {
                return null;
            }
            return cell.getValue().toString();
        }else
        {
            return null;
        }
    }//end CR3
//CCBegin SS1
    /**
     * 设置列不可编辑 　@param cols[] 不可编辑列数组
     */

    public void setCellsEnabled(int[] cols, int rows[], boolean disFlag)
    {
        this.disCols = cols;
        this.disRows = rows;
        this.disFlag = disFlag;
        
    }

    /**
     * 插入一个空行数据
     * @param row int 要插入的行号 20120424 lvh add
     */
    public void insertNullRow(int row)
    {
        this.insertRows(1);
        int rowNum = this.getNumberOfRows() - 1;
        int j = rowNum - row;
        for(int i = 0;i < j;i++)
        {
            changeRow(rowNum, rowNum - 1);
            rowNum--;
        }
    }
    //CCEnd SS1
    
    
    //  ButtonGroup buttonGroup = new ButtonGroup();

    //xucy add 
    public void changeRow(int row1, int row2)
    {
        if(table != null)
        {
            int cols = table.getColumnCount();
            for(int i = 0;i < cols;i++)
            {
                Object obj;
                obj = table.getValueAt(row1, i);
                table.setValueAt(table.getValueAt(row2, i), row1, i);
                table.setValueAt(obj, row2, i);
            }
        }
    }
    
    


}