/** ���� MultiList.java  1.0  03/03/2003


 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * CR1 ������ 2009/1/15   ԭ��:�����й�װ�豸��ά�����������ܶ�Ҫʵ�ֵ���ѡ����˫������
 * 
 *                        ����:��ԭ������MultiList����갴���¼��滻Ϊ���˫���¼�
 *                        
 *                        ��ע:�����¼���"CRSS-001";
 *                        
 * CR2 2009/04/30 Ѧ��      ԭ�����ݸ��²���ʾ�Ƿ񱣴档
 *                         ���������Ӽ�����������Ƿ���¡�
 *                         ��ע��CRSS-004                      
 * CR3 2009/06/22 �촺Ӣ  �μ�td:DefectID=2160
 * CR4 2009/09/03 �촺Ӣ  ԭ�򣺸�������ʾ��Ϣ���ӳ�
 *                        ����������ToolTipManager���ӳٷ���
 * CR5 2009/09/03 �촺Ӣ  �μ����ܲ�����:V4R3FunctionTest,td:DefectID=2622
 * CR6 2009/12/08 ������  ԭ��ɾ�������豸����װ�������еĿ���ʱ���ִ���
 *                      ��������ӿ��ַ����ж�
 * CR7 2010/04/08  Ѧ�� �μ�TD3208
 * CR8 2010/04/16  Ѧ�� �μ�TD3224
 * CR9 2011/03/23  �촺Ӣ  �μ�TD2518
 * SS1 ��������в��ɱ༭ �Ͳ���һ���������� liuyang 2013.1.31
 * SS2 ���ŵ�������ʱ��ʾ liuyang 2013.2.19
 * SS3 �޸��б����Զ����ص����⣬�μ�����ƽ̨2740 ��� 2013.11.25
 *SS4 �����乤������������  guoxiaoliang 2014-03-14

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
 * <p>�����б�ؼ����Զ����б�ı�ͷ���У��е����Խ������ò����ü����¼�</p>
 * @author �ܴ�Ԫ
 */
// ���⣨1��2007.11.05 Ѧ����� ���ܽ������ֵ������ȷ

public class CappMultiList extends JPanel implements FocusListener, ActionListener, MouseListener, ItemSelectable, ListSelectionListener, KeyListener
{
	//CCBegin SS3
	//�ж�һ���б��Ƿ�������Ӧ�п�
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
    //�����Ƿ���Ҫ���ظ��±�ʶ���Ӽ���ʱ�Զ����á�Ϊ��ʱ�����Ƿ���±�ʶ�ſ��á�
    public boolean isNeedNoticeModified = false;
    
    //�����Ƿ���±�ʶ
    public boolean isModified = false;
 
    //���ڱ༭��Ԫ�����ڵ��м���
    public Vector CellEditorRowNumVec = new Vector();
    //CCEnd SS4

    //�̺߳����³�ʼ������Ϊ���豸���б�ĳ�ʼ�����ݶ����߳���ӵģ������һ���ٵ����󣬼�Ϊ������ݸ����ˡ�
    public void intAfterThread()
    {
        isModified = false;
    }

    //��ȡ�����Ƿ�ı��ʶ�����ݡ�
    public boolean isValuesChanged()
    {
        boolean b = isModified;
        isModified = false;
        return b;
    }

    //�����Ӽ�����ͬʱ���ñ���Ƿ���±�ʶ���á�����ñ��ӹ����������ڼӡ�
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

    //�ڲ����ģ�ͼ����࣬�����������ɾ��һ�����ݵĲ�����
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
     * ����Ĭ���б�.
     */
    public CappMultiList()
    {
        this(0, false, Color.white);
    }

    /**
     * ����ָ���������б�.
     * @param cols Ϊ����
     */
    public CappMultiList(int cols)
    {
        this(cols, false, Color.white);
    }

    /**
     * ����һ�µĺ���һ���������б� ָ���Ƿ���Զ�ѡ��.
     * @param cols Ϊ����
     * @param multi ��Ϊtrue ����Զ�ѡ,���򲻿���
     */
    public CappMultiList(int cols, boolean multi)
    {
        this(cols, multi, Color.white);
    }

    /**
     * ����һ�µĺ���һ���������б�, ָ���Ƿ���Զ�ѡ,������ɫ
     * @param cols Ϊ����
     * @param multi ��Ϊtrue ����Զ�ѡ,���򲻿���
     * @param bg Ϊ������ɫ
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
        table.setDefaultRenderer(CappTextAndImageCell.class, new CellRenderer()); //���ñ�ı༭ģʽ����ɫģʽ
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
            //header.addMouseListener(new HeaderListener(header, headerRenderer)); //���ͷ�����������

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
     * ˫����ͷ��������� CR1
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
                    //��¼���µ��к�
                    if(col < 0 || table.getRowCount() <= 0)
                    {
                        return;
                    }
                    renderer.setPressedColumn(col);
                    //ĳһ�������״̬
                    renderer.setSelectedColumn(col);
                    header.repaint();

                    //��������ڱ༭�ĵ�Ԫ��ֹͣ�༭��ʽ��
                    if(header.getTable().isEditing())
                    {
                        header.getTable().getCellEditor().stopCellEditing();

                    }
                    //����
                    boolean isAscent = true;
                    if(SortButtonRenderer.DOWN == renderer.getState(col))
                    {
                        isAscent = true;
                    }else
                    {

                        isAscent = false;

                    }
                    ((CappSortableTableModel)header.getTable().getModel()).sortByColumn(sortCol, isAscent);
                    //���������и�
                    initalHeight();
                    int col2 = header.columnAtPoint(e.getPoint());
                    //��¼���µ��к� -1 ����clear
                    renderer.setPressedColumn(-1); // clear
                    header.repaint();
                }

            }

        }
    }

    //add by guoxl end (˫����ͷ����)
    /**
     * ���ñ���ɫ
     * @param color Color ����ɫ
     */
    public void setBackGround(Color color)
    {
        js.getViewport().setBackground(color);
    }

    /**
     * ����һ�µĺ���һ���������б�, ָ���Ƿ���Զ�ѡ,������ɫ
     * @param cols Ϊ����
     * @param multi ��Ϊtrue ����Զ�ѡ,���򲻿���
     * @param bg Ϊ������ɫ
     */
    public CappMultiList(int cols, boolean multi, Color bg, boolean isCheckMulti)
    {
        this(cols, multi, bg);
        if(isCheckMulti)
        {
        	System.out.println("���ñ༭��2222222222222222222222222222");
            table.setDefaultEditor(CappTextAndImageCell.class, new MultiCellEditor(new JCheckBox()));
            System.out.println("���ñ༭��eeeeeee222222222222222222222");
        }
    }

    /**
     * ��ָ����������ռ�.
     * @param i Ϊ�µ�����
     */
    protected void internalCreateColumns(int i)
    {
        headings = new String[i];
    }

    /**
     * ����������ʼ���б�. ���е��б����ݶ�ʧ,�����ݱ���.
     * @param cols �µ�����
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
     * ��������
     * @param h ������
     */
    private void createColumn(String h)
    {
        mtModel.addColumn(h);
    }

    /**
     * ��ÿ����ߵ�ͼƬ�߶������иߣ����û��ͼƬ��Ĭ��Ϊ defaulRowHeight
     * @parame row ����
     * @parame imageCell TextAndImageCell��װ���������
     * @see CappTextAndImageCell
     */
    private void initalHeight(int row, CappTextAndImageCell imageCell)
    {
        if(row >= 0 && imageCell != null)
        {
            ImageIcon image = (ImageIcon)(imageCell.getImageIcon());
            //���ͼƬ�ĸ߶ȴ���Ĭ�ϸ߶�
            if(image != null && image.getIconHeight() > defaulRowHeight)
            {
                defaulRowHeight = image.getIconHeight();
            }
            table.setRowHeight(row, defaulRowHeight);
        }
    }

    /**
     * ����Ĭ���и�
     */
    private void initalHeight()
    {
        for(int i = 0;i < table.getRowCount();i++)
        {

            table.setRowHeight(i, defaulRowHeight);
        }
    }

    /**
     * ����CappMultiList��ѡ��ʽ
     * @param b �Ƿ���Զ�ѡ�У�trueΪ�ɶ�ѡ
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
     * �ж��Ƿ��Ƕ�ѡ��ʽ
     * @return �Ƿ��Ƕ�ѡ��ʽ
     */
    public boolean isMultipleMode()
    {
        return multiSelect;
    }

    /**
     * ������������.
     * @param i ����
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
     * ��һ����������������������һ�ַ���
     * @param i ����
     */
    public void setColumns(int i)
    {
        setNumberOfCols(i);
    }

    /**
     * �õ�����
     * @return ��ǰ����.
     */
    public int getNumberOfCols()
    {
        return table.getColumnCount();
    }

    /**
     * ɾ��ĳһ��
     * @param i ��i��
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
     * ɾ��������
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
     * �õ�����.
     * @return ��ǰ����.
     */
    public int getNumberOfRows()
    {
        return table.getRowCount();
    }

    /**
     * ���ñ�ͷ�Ƿ�ɼ�.
     * @param b ��ͷ�Ƿ�ɼ�
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
     * ѡ��ĳһ��
     * @param row ��row��
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
     * �����б��ͷ�Ƿ�ɼ�.
     * @return �б��ͷ�Ƿ�ɼ�
     */
    public boolean isHeadingVisible()
    {
        return headingVisible;
    }

    /**
     * �õ�i�еı�ͷ
     * @param i ������
     * @return String �õ�i�еı�ͷ
     */
    public String getHeading(int i)
    {
        return table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString();
    }

    /**
     * �޸ĵ�i�еı�ͷ�ı�
     * @param h �±�ͷ�ı�
     * @param i ������
     */
    public void setHeading(String h, int i)
    {
        internalCreateColumns(i);
        adjustCols(i);
        table.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(h);
    }

    /**
     * ���õ�i�еı�ͷ���ݺͿ��
     * @param h ��ͷ����
     * @param i ��i��
     * @param pixels ����
     */
    public void setHeading(String h, int i, int pixels)
    {
        internalCreateColumns(i);
        adjustCols(i);
        setHeading(h, i);
        table.getTableHeader().getColumnModel().getColumn(i).setPreferredWidth(pixels);
    }

    /**
     *�õ���ͷ�ַ���������ʽ
     * @return String[] ��ͷ���ݵ�������ʽ
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
     *�������ַ�������±�ͷ
     * @param list ��ͷ��������ʽ
     */
    public void updateHeadings(String[] list)
    {
        list = tokenizeStringArrayIfNeeded(list);
        removeAllColumns();
        calcHeadings(list);
    }

    /**
     *�������ַ������ʼ����ͷ.
     *@param list ��ͷ��������ʽ
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
     * �����������ʼ����ͷ.
     * @param list ��ʼ����ͷ����
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
     * �����û��÷ֺŴ����item,
     * @param list �����û��÷ֺŵ�����
     * @return String[] �����û��÷ֺŴ����item
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
     * �õ���ͷ������.
     * @return Font ��ͷ����
     */
    public Font getHeadingFont()
    {
        headingFont = table.getTableHeader().getFont();
        return headingFont;
    }

    /**
     * ���ñ�ͷ������
     * @param newFont ������
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
     * �õ���Ԫ�����������.
     * @return Font ��Ԫ������
     */
    public Font getCellFont()
    {
        cellFont = table.getFont();
        return cellFont;
    }

    /**
     * ���õ�Ԫ�������.
     * @param f ������
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
     * ���ñ�ͷ��ǰ���ͱ�����ɫ
     * @param fg ǰ����ɫ
     * @param bg ������ɫ
     */
    public void setHeadingColors(Color fg, Color bg)
    {
        setHeadingFg(fg);
        setHeadingBg(bg);
    }

    /**
     * ���ñ�ͷ��ǰ����ɫ.
     * @param c ��ɫ
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
     * �õ���ͷ��ǰ����ɫ
     * @return Color ��ͷǰ����ɫ
     */
    public Color getHeadingFg()
    {
        return headingFg;
    }

    /**
     * �õ���ͷ�ı�����ɫ
     * @return Color ��ͷ��ɫ
     */
    public Color getHeadingBg()
    {
        return headingBg;
    }

    /**
     * ���ñ�ͷ�ı�����ɫ
     * @param c ��ɫ
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
     * ���õ�Ԫ���ǰ���ͱ�����ɫ.
     * @param fg ǰ����ɫ
     * @param bg ������ɫ
     */
    public void setCellColors(Color fg, Color bg)
    {

        setCellFg(fg);
        setCellBg(bg);
    }

    /**
     * �õ�������ɫ
     * @return Color ������ɫ
     */
    public Color getCellFg()
    {
        return colorFg;
    }

    /**
     * ���õ�Ԫ���ǰ��
     * @param c ��Ԫ���ǰ��
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
     * ���õ�Ԫ��� ������ɫ
     * @param c ��ɫ
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
     *�õ���Ԫ��ı�����ɫ.
     *@return Color ��Ԫ�񱳾���ɫ
     */
    public Color getCellBg()
    {
        return colorBg;
    }

    /**
     * ����ÿ�еĶ��뷽ʽ
     * @param list ���뷽ʽ��������ʽ
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
     * ������ֵ�õ��е����з�ʽ(left ,right and so on)n.
     * @param column �ڼ���
     * @return int ��column�еĶ��뷽ʽ
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
     *Ĭ�϶��뷽ʽ.
     * @return int ����Ĭ���ж��뷽ʽ
     */
    public int getDefaultColumnAlignment()
    {
        return defaultColumnAlignment;
    }

    /**
     * �õ����з�ʽ���ַ�����ʽ.
     * @return String[] Ĭ�϶��뷽ʽ(������ʽ)
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
     * ����ĳһ�е��ı����з�ʽ.
     * @param column ��column��
     * @param alignment ���뷽ʽ
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
     * ����Ĭ�϶��뷽ʽ.
     * @param newDefaultColumnAlignment �µ�Ĭ�϶��뷽ʽ
     */
    public void setDefaultColumnAlignment(int newDefaultColumnAlignment)
    {
        if(defaultColumnAlignment != newDefaultColumnAlignment)
        {
            defaultColumnAlignment = newDefaultColumnAlignment;
        }
    }

    /**
     * �����еĳߴ�(ֻ���п�).
     * @param list �еĳߴ�����
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
     * ���������п�
     * @return String[] ���������е��гߴ�
     */
    public String[] getColumnSizes()
    {
        return intArrayToStringArray(columnSizes);
    }

    /**
     * �õ�ĳһ�еĳߴ�.
     * @param i ��i��
     * @return int ��i�еĳߴ�
     */
    public int getColumnSize(int i)
    {
        return table.getColumnModel().getColumn(i).getPreferredWidth();
    }

    /**
     * ����ֵ�������Ϊ�ַ�������
     * @param intArray ��ֵ������
     * @return String[] �ַ�������
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
     * �����ض���Ϊѡ����.
     * @param row ������
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
     * ��ѡĳһ��.
     * @param row ������
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
     * �õ�ѡ���е�����(��i��,i��0��ʼ)
     * @return int ��ѡ�к�
     */
    public int getSelectedRow()
    {
        return table.getSelectedRow();
    }

    /**
     * �õ�ѡ���е�����(��i��,i��0��ʼ)
     * @return int ��ѡ�к�
     */
    public int getSelectedCol()
    {
        return table.getSelectedColumn();
    }

    /**
     * ѡ��������.
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
     * ��ѡ������.
     */
    public void deselectAll()
    {
        table.clearSelection();
    }

    /**
     * �õ�������ѡ�е�����.
     * @return int[] ������ѡ�е�����.
     */
    public int[] getSelectedRows()
    {
        return table.getSelectedRows();
    }

    /**
     * �õ� ��ѡ�еĶ���ʽ(Integer).
     * @return Object[] ��ѡ�еĶ���ʽ(Integer).
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
     * ���Table
     * @return JTable
     */
    public JTable getTable()
    {
        return table;
    }

    /**
     * ���Ĭ��ģʽ
     * @return DefaultTableModel Ĭ��ģʽ
     */
    public DefaultTableModel getTableModel()
    {
        return mtModel;
    }

    /**
     * �����е���С���.
     * @param size �ߴ�
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
     * ����item����ѡ����ӵ�Ԫ��
     *@param items Ϊ��;�ָ���������������
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
     * �õ���ֵ���ַ���ʽ,һ������;�ָ�
     * @return String[] ��ֵ���ַ�������ʽ
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
     * �����Ƿ�������
     * @param b �Ƿ��������
     */
    public void setAllowSorting(boolean b)
    {
        if(allowSorting != b)
        {
            allowSorting = b;
        }
    }

    /**
     * �õ��Ƿ�����
     * @return boolean �Ƿ�����
     */
    public boolean isAllowSorting()
    {
        return allowSorting;
    }

    /**
     * �����Ƿ���Ե����п�.
     * @param allowResizing �Ƿ���Ե����п�
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
     * �õ��Ƿ���Ե����п�.
     * @return boolean �Ƿ���Ե����п�
     */
    public boolean isAllowResizingOfColumns()
    {
        return allowResizingOfColumns;
    }

    /**
     * �õ���С����п�
     * @return int ��С�п�
     */
    public int getMinColumnWidth()
    {
        return minColumnWidth;
    }

    /**
     * ɾ��CappMultiList�е�������.
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
     * ɾ��ĳһ��
     * 
     * @param row ������
     */
    public void removeRow(int row)
    {
        mtModel.removeRow(row);
    }

    /**
     *�����������
     * @param cols �������
     */
    private void adjustCols(int cols)
    {
        if(cols > getNumberOfCols() - 1)
        {
            insertCols(cols + 1 - getNumberOfCols());
        }
    }

    /**
     * �����������������
     * @param rows �������
     * @param cols �������
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
     * ����������
     * @param rows �������
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
     * ����ĳһָ����,�е�Ԫ���е��ı�����.
     * @param r ������
     * @param c ������
     * @param s �ı�����
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
     * ����ĳһָ����,�е�Ԫ���е��ı�����.
     * @param r ������
     * @param c ������
     * @param s �ı�����
     */
    // ���⣨1��2007.11.05 Ѧ����� ���ܽ������ֵ������ȷ

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
     * ����ĳһָ����,�е�Ԫ���е�ͼƬ.
     * @param r ������
     * @param c ������
     * @param i ͼƬ
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
     * ����ĳһָ����,�е�Ԫ���е��ı���ͼƬ.
     * @param r ������
     * @param c ������
     * @param s �ı�����
     * @param i ͼƬ
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
     * ����ĳһָ����,�е�Ԫ��.
     * @param row ������
     * @param column ������
     * @param cell ��Ԫ��
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
     * ����Ԫ�������ݼ��뵽�б���.
     * @param row ������
     * @param column ������
     * @param cell ��Ԫ��
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
     * ����Ԫ����������ſؼ����뵽�б���.
     * @param row int ������
     * @param column int ������
     * @param cell CappTextAndImageCell ��Ԫ��
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
     * ����ָ����Ԫ����ı�.
     * @param r ������
     * @param c ������
     * @return ָ����Ԫ����ı�
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
     * ���ָ�����еĵ�Ԫ��
     * @param i int �к�
     * @param j int �к�
     * @return CappTextAndImageCell ��Ԫ��
     */
    public CappTextAndImageCell getCellAt(int i, int j)
    {
        return (CappTextAndImageCell)table.getValueAt(i, j);
    }
    //CCBegin SS4
    /**
     * ���ָ�����еĵ�Ԫ��
     * @param i int �к�
     * @param j int �к�
     * @return CappTextAndImageCell ��Ԫ��
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
     * ����ָ����Ԫ���ͼƬ.
     * @param r ������
     * @param c ������
     * @return Image ָ����Ԫ���ͼƬ.
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
     * �õ��б����ѡ�ߴ�
     * @return Dimension �б����ѡ�ߴ�
     */
    public synchronized Dimension getPreferredSize()
    {
        return new Dimension(175, 125);
    }

    /**
     * �õ��б����С�ߴ�
     * @return Dimension �б����С�ߴ�
     */
    public synchronized Dimension getMinimumSize()
    {
        return new Dimension(50, 50);
    }

    /**
     * ��ʼ�����
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
     * <p>Title:�ڲ���װ ��Ԫ����ʾ�� </p> <p>Description:��ʾ��Ԫ�� </p> <p>Copyright: Copyright (c) 2008</p> <p>Company: </p>
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
         *���ص�Ԫ��ı༭���
         * @param table JTable ��ǰTabҳ
         * @param value Object ��ѡ��Ԫ������
         * @param isSelected boolean �Ƿ�ѡ��
         * @param hasFocus �Ƿ��н���
         * @param row int ��������
         * @param column int ��������
         * @return Component �õ�Ԫ�����
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
                    /** �����⣬������ȵ����� */
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
     * ������ڱ༭�ĵ�Ԫ�����ڵ��к� guoxl
     */
    public Vector getCellEditorRowNum()
    {
        return this.CellEditorRowNumVec;
    }
//CCBegin SS4
    /**
     * ���Ƽƻ��������Ե�Ԫ��༭���ݼ�����
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
//    		System.out.println("����*********table************"+table);
//    		
//    	}
//		
//    	// �������Ի����Լ������˸��ĵ�֪ͨ��
//		public void changedUpdate(DocumentEvent arg0) {
//			
//			System.out.println("����*********************");
//			
//		}
//
//		//�������ĵ�ִ���˲��������֪ͨ��
//		public void insertUpdate(DocumentEvent arg0) {
//			
//			row=mytable.getEditingRow();
//			column=mytable.getEditingColumn();
//			
//			System.out.println("����*********************"+row+"=========="+column);
//			
//			
//		}
//
//		//�����Ƴ���һ�����ĵ���֪ͨ��
//		public void removeUpdate(DocumentEvent arg0) {
//			System.out.println("ɾ��*********************");
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
     * <p>Title:�ڲ���װ ��Ԫ��༭�� </p> <p>Description: </p> <p>Copyright: Copyright (c) 2008</p> <p>Company: </p>
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
         *���ص�Ԫ��ı༭���
         * @param table JTable ��ǰTabҳ
         * @param value Object ��ѡ��Ԫ������
         * @param isSelected boolean �Ƿ�ѡ��
         * @param row int ��������
         * @param column int ��������
         * @return Component �õ�Ԫ�����
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
//            System.out.println("�༭===========mytable==============="+mytable);
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
//                    System.out.println("���ڱ༭***********************************");
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
//                		System.out.println("������ŵ�λ�úʹ�Сloc===="+speArray[i].loc+"  -------  "+speArray[i].iWidth);
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
         *��õ�Ԫ��ı༭ֵ
         * @return Object ��Ԫ��ı༭ֵ
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
     * <p>Title:�ڲ���װ ��Ԫ��༭�� </p> <p>Description: </p> <p>Copyright: Copyright (c) 2008</p> <p>Company: </p>
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
         * ���ص�Ԫ��ı༭���
         * @param table JTable ��ǰTabҳ
         * @param value Object ��ѡ��Ԫ������
         * @param isSelected boolean �Ƿ�ѡ��
         * @param row int ��ǰ����
         * @param column int ��ǰ����
         * @return Component ��Ԫ��༭���
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
         * ��õ�Ԫ��ı༭ֵ
         * @return Object ��Ԫ��ı༭ֵ
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
     * ���CheckBox���
     * @return JComponent CheckBox���
     */
    public JComponent getCheckBox()
    {
        return returnCheckBox;
    }

    /**
     * ��õ�ǰ����
     * @return tableRow ��ǰ����
     */
    public int getRow()
    {
        return tableRow;
    }

    /**
     * ��ñ������
     * @return int �������
     */
    public int getRowCount()
    {
        return table.getRowCount();
    }

    /**
     * ��õ�ǰ����
     * @return tableColumn ��ǰ����
     */
    public int getCol()
    {
        return tableColumn;
    }

    /**
     * <p>Title:�ڲ���װ ��ͷ������ </p> <p>Description: </p> <p>Copyright: Copyright (c) 2008</p> <p>Company: </p>
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

        //����ʱ
        public void mousePressed(MouseEvent e)
        {
            if(isAllowSorting())
            {

                int col = header.columnAtPoint(e.getPoint());
                int sortCol = header.getTable().convertColumnIndexToModel(col);
                //��¼���µ��к�
                if(col < 0 || table.getRowCount() <= 0)
                {
                    return;
                }
                renderer.setPressedColumn(col);
                //ĳһ�������״̬
                renderer.setSelectedColumn(col);
                header.repaint();

                //��������ڱ༭�ĵ�Ԫ��ֹͣ�༭��ʽ��
                if(header.getTable().isEditing())
                {
                    header.getTable().getCellEditor().stopCellEditing();

                }
                //����
                boolean isAscent = true;
                if(SortButtonRenderer.DOWN == renderer.getState(col))
                {
                    isAscent = true;
                }else
                {

                    isAscent = false;

                    /**
                     * //��ȡ��ѡ�е��� int[] selectedRows = header.getTable().getSelectedRows(); //��ȡ����Դ�к� for(int i=0; i<selectedRows.length; i++) selectedRows[i] =
                     * ((CappSortableTableModel)header.getTable().getModel()).getSelectDataSourceRowNum(selectedRows[i]); //����
                     * ((CappSortableTableModel)header.getTable().getModel()).sortByColumn(sortCol, isAscent); //�ָ�ѡ�е��� for (int i=0;i<selectedRows.length; i++) { int temp =
                     * ((CappSortableTableModel)header.getTable().getModel()).getAfeterSortTargetRowNum(selectedRows[i]); header.getTable().addRowSelectionInterval(temp,temp); }
                     */

                }
                ((CappSortableTableModel)header.getTable().getModel()).sortByColumn(sortCol, isAscent);
                //���������и�
                initalHeight();
            }

        }

        //�ɿ�ʱ
        public void mouseReleased(MouseEvent e)
        {
            if(isAllowSorting())
            {

                int col = header.columnAtPoint(e.getPoint());
                //��¼���µ��к� -1 ����clear
                renderer.setPressedColumn(-1); // clear
                header.repaint();
            }
        }
    }

    //�����ģʽ
    CappSortableTableModel mtModel = new CappSortableTableModel()
    {
        //���ط���
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
     * ����Ĭ���и�
     * @param rowHeight Ĭ���и�
     */
    public void setDefaultRowHeight(int rowHeight)
    {
        defaulRowHeight = rowHeight;
    }

    /**
     *����Ĭ���и�
     * @return Ĭ���и�
     */
    public int getDefaultRowHeight()
    {
        return defaulRowHeight;
    }

    /**
     *���õ�Ԫ���Ƿ�ɱ༭
     * @param editFlag �Ƿ�ɱ༭
     */

    public void setCellEditable(boolean editFlag)
    {
        this.editFlag = editFlag;
    }

    /**
     * �����в��ɱ༭ ��@param cols[] ���ɱ༭������
     */

    public void setColsEnabled(int[] cols, boolean disFlag)
    {
        this.disCols = cols;
        this.disFlag = disFlag;

    }

    /**
     * �õ���Ԫ���Ƿ�ɱ༭
     * @return ��Ԫ���Ƿ�ɱ༭
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
        //20081209 �촺Ӣ�޸�   �޸�ԭ�򣺲����ֹ������ź�س������ķ�ʽ��ӵģ�
        //����������ԴA��B��C�����õ�����ʾ���ż�������A��B��B������
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
     * ʵ����굥���¼�
     * @param e ����¼�
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
         * java.awt.Component component = table.getEditorComponent(); System.out.println("�ɱ༭���齨;" + component); if (component instanceof JPanel)
         * 
         * { Component d= ((JPanel)component).getComponent(1); System.out.println("�ɱ༭���齨2;" + d); if (d instanceof javax.swing.JTextField) { JTextField strField = (javax.swing.JTextField) d;
         * System.out.println("�ɱ༭���齨3;" + strField.getText());
         * 
         * strField.grabFocus(); // strField.removeFocusListener(lSymTextFocus); //strField.addFocusListener(lSymTextFocus); } //end if(component instanceof javax.swing.JTextField) } }
         */

    }

    public void mouseEntered(MouseEvent e)
    {

    }

    /**
     * ����ͷ�
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
     * ���Ӷ����¼�����
     * @param actionlisener �����¼�����
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
     * ��ȥ�����¼�����
     * @param actionlisener �����¼�����
     */
    public synchronized void removeActionListener(ActionListener actionlistener)
    {
        if(actionListeners != null)
        {
            actionListeners.removeElement(actionlistener);
        }
    }

    /**
     * ������Ŀ�¼�����
     * @param itemlistener ��Ŀ�¼�����
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
     * ��ȥ��Ŀ�¼�����
     * @param itemlistener ��Ŀ�¼�����
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
            //��������
            if(listener instanceof ActionListener)
            {
                //processActionEvent(listener, (ActionEvent)awtevent);
                ((ActionListener)listener).actionPerformed((ActionEvent)awtevent);
            }
            //������˫��ѡ����
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
     * ���Ӷ����¼�����
     * @param actionlisener �����¼�����
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
     * ��ȥ�����¼�����
     * @param actionlisener �����¼�����
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
        //20081208 �촺Ӣ�޸�  �޸�ԭ��:����ĳ�����е��п�,
        //�ڸ����Կ������뺺��,�س������ָ�δ����״̬,��������ĺ��ֶ�ʧ.
        boolean tstflag = this.getTable().isEditing();
        int wid = this.getWidth();
        listwidth = 0; //lilei update
       
        if(wid != listwidth && relColWidth != null)
        {
        	//CCBegin SS3
        	//�����ж��Ƿ�������Ӧ
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
     * *�������
     */
    //xuejing modified �п�Ϊ��������س���+50������,��������п�ĺ�С���б�Ŀ��,�򰴱��������п�

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
                String[] headings = this.getHeadings(); //�п� 
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
     * *���ÿ��
     * 
     * @param int[] list ����Ԫ������鼯��
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
     *��Bean�Ĺ��캯��
     *@return JFrame ʹ�ø�Bean�Ĵ���
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
     * �Ա�����������
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
     * ����ַ��������س�,��
     * @param g Graphics2D
     */
    private int getStringLenth(String message)
    {
        FontMetrics fm = Toolkit.getDefaultToolkit().getFontMetrics(getFont());
        //��ȡ�ַ����ܳ���
        return fm.stringWidth(message);

    }

    /*********************** ������ *******************************************/
    /**
     * �б���.
     */
    protected String headings[];

    /**
     * �Ƿ���Զ�ѡ��. Default is false.
     */
    protected boolean multiSelect = false;
    protected Vector data = new Vector(); //���������������
    protected Vector heading = new Vector(); //��ͷ
    protected JTable table; //���ɱ�� 
    protected Color colorBg = Color.white; //������ɫ
    protected Color colorFg = Color.black; //ǰ����ɫ
    protected Color colorHBg = new Color(0, 0, 128); //ѡ��ʱ�ı�����ɫ
    protected Color colorHFg = Color.white; //ѡ��ʱ��ǰ����ɫ
    protected Color headingBg = java.awt.SystemColor.control; //��ͷ������ɫ
    protected Color headingFg = java.awt.SystemColor.controlText; //��ͷǰ����ɫ
    protected boolean allowSorting = true; //�Ƿ�����
    protected Font headingFont; //��ͷ����
    protected Font cellFont; //��Ԫ������
    protected int minColumnWidth = 10; //��С�п�
    protected boolean allowResizingOfColumns = true; //�Ƿ���Ե�����
    protected boolean editFlag = true; //��Ԫ���Ƿ�ɱ༭
    protected int defaulRowHeight = 20; //Ĭ���и�
    protected int preRow = -1; //δѡʱΪ-1
    protected Vector itemListeners = new Vector(); //��Ŀ�¼�����
    protected Vector actionListeners = new Vector(); //�����¼�����

    private int oldrow = -1;
    private int oldcol = -1;
    private int newrow = -1;
    private int newcol = -1;
    private String oldText = "";
    private CappTextAndImageCell oldCell_pre = null;
    private CappTextAndImageCell oldCell_next = null;

    /**
     * �еĶ��뷽ʽ ��LEFT, CENTER, or RIGHT.
     */
    protected int columnAlignments[];

    /**
     * Ĭ���б�Ķ��뷽ʽ.
     */
    protected int defaultColumnAlignment = 0;

    /**
     * �п�. ��Ϊnull,�����ΪĬ���п�.
     */
    protected int columnSizes[] = null;

    /*
     * ��������
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
     * �ڲ��ࣺ��д���� event �Ĺ�����ʾ���ַ����ķ���
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
     * �õ���ǰ���ѡ�е�Ԫ�������
     * @param i ��ǰ����
     * @param j ��ǰ����
     * @return String ѡ�еĶ���
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
     * �����в��ɱ༭ ��@param cols[] ���ɱ༭������
     */

    public void setCellsEnabled(int[] cols, int rows[], boolean disFlag)
    {
        this.disCols = cols;
        this.disRows = rows;
        this.disFlag = disFlag;
        
    }

    /**
     * ����һ����������
     * @param row int Ҫ������к� 20120424 lvh add
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