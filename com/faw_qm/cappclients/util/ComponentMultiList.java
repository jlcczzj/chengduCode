/**
 * �޸�˵��
 * CR1  ������   2009/01/15    ԭ��:���ڹ��չ�̹���������򹤲��༭�����FEMA��
 *                                  ��������ͼ�����Ƽƻ���TABҳ���¿ɱ༭��֧��
 *                                  ���еĸ�����ճ�����ܡ�
 *                                  
 *                             ����:�������ʱ��ȡѡ���е��кţ����ճ��ʱ����
 *                                  �������ѭ��,�õ�������ÿһ�е�ֵ�����жϵ�
 *                                  Ԫ������������(������������ַ�)��Ȼ����Щ
 *                                  ֵ���õ�ճ���е�ÿһ�С�
 *                             
 *                             ��ע:�����¼���"CRSS-002";
 *                             
 *CR2  ������    2009/06/04    ������:v4r3FunctionTest;TD��2290
 * 
 * SS1 ������ 2014-5-29 �޸ķ���ƽ̨���� A005-2014-2881��
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
 * <p>Title:��������б� </p>
 * <p>Description:�̳�CappQMMultiList�� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: faw_qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
//(1)20060815Ѧ���޸ģ��޸ķ���addSpeCharCell��ԭ���ӡҪ���ض�����


public class ComponentMultiList extends CappQMMultiList
{ //implements KeyListener
    private Font dialog11;
    private boolean AllCheckBoxesDisabled;
    private Vector checkboxesdisabled;

    private Vector keylinsteners = new Vector();


    //Begin CR1
    private PopupMenu listPopup = new PopupMenu();
    private MenuItem copy = new MenuItem("����");
    private MenuItem affix = new MenuItem("ճ��");
    
    //�����Ƿ���ʾ����ճ���˵���true��ʾ��false����ʾ
    private boolean isPopupMenu = false;
    private boolean isCopyState = true;
    private int copyRowIndex = -1;
    /**
     * �����Ƿ���ʾ����ճ���Ҽ��˵�
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
     * ��ѡ��һ,��Ҫ���������Ƹñ�ĳ�ʼ״̬
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
            add(listPopup);//��������Ӳ˵�
            affix.setEnabled(false);
            
            //���˵�����ӵ��˵���
            listPopup.add(copy);
            listPopup.add(affix);
            
            //Ϊ�˵�����������
            copy.setFont(new java.awt.Font("Dialog", 0, 12));
            affix.setFont(new java.awt.Font("Dialog", 0, 12));
            
            //Ϊ�˵�����Ӽ���
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

      int selectRow = getSelectedRow();//ճ������

      public void actionPerformed(ActionEvent e) {

        if (e.getSource() == copy) {
          isCopyState = false;
          copyRowIndex = getSelectedRow();//������

        }
        else if (e.getSource() == affix) {

          isCopyState = false;
          int selectRow = getSelectedRow();//ճ������
          int colCount = getNumberOfCols();//����

          try {
            if (copyRowIndex <= getNumberOfRows()) {
              for (int i = 0; i < colCount; i++) {

                String value = getStringValue(copyRowIndex, i);

                if (getComboBoxObject(copyRowIndex, i) != null) {
                	//����������ѡ����
                  String comboxSelectedStr = (String) getComboBoxObject(
                      copyRowIndex, i);
                  //���������л�õ�ѡ��������п�ѡ����ӵ�ָ����Ԫ����
                  setComboBoxSelected(selectRow, i, comboxSelectedStr,
                                      getCellAt(copyRowIndex, i).
                                      getSelectableValues());
                  continue;

                }
                // ����ճ�������ַ�
                else if (getCellAt(copyRowIndex, i).getType() == 5) {

                  Vector v = new Vector();
                  //���ָ����Ԫ�������ַ���ֵ
                  String str = getCellAt(copyRowIndex, i).getSpecialCharacter().save();
                  //�жϻ�õ������ַ�ֲ�Ƿ�Ϊ��
                  if(null!=str&&(!str.trim().equals("")))
                    v.add(str);
                    
                  if (v.size() > 0) {
                    //��յ�Ԫ����ԭ�е������ַ�
                    getCellAt(selectRow, i).getSpecialCharacter().clearAll();
                    //�޸�ԭ��Ԫ�񣬽���õĵ������ַ�ֵ�ӵ�ָ���ĵ�Ԫ����
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
                null, "���Ƶ�����������!");
          }

        }

      }
    }

  //End CR1


//public void setSpeChar
    /**
     * ����CheckBox��Ԫ���Ƿ�ɱ༭
     * @param flag boolean trueΪ�ɱ༭
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
     * �������б��е�Checkbox��ѡ��Ĳ�ѡ״̬
     * @param flag ���Ϊ����Ϊ��ѡ
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
     * �������еı���е��ı���Ϊ���ɱ༭״̬
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
     * �������еı���е��ı���Ϊ���ɱ༭״̬
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
     * ����ָ����
     * @param i int����
     */
    public void moveUp(int i)
    {
        this.getTableModel().moveRow(i, i, i - 1);
    }
    /**
     * ����ָ����
     * @param i int����
     */
    public void moveDown(int i)
    {
        this.getTableModel().moveRow(i, i, i + 1);
    }


    /**
     * ���ز�ѡ״̬��ѡ�����
     * @return ��ѡ״̬��ѡ�����(Vector��)
     */
    public Vector getCheckBoxesDisabled()
    {
        return checkboxesdisabled;
    }


    /**
     * �õ���i�е�j�еĵ��ַ�ֵ
     * @param i ��
     * @param j ��
     * @return String �ַ�ֵ
     */
    public String getStringValue(int i, int j)
    {
        return this.getCellAt(i, j).getStringValue();
    }


    /**
     * ���õ�i�е�j�еĶ���
     * @param i �к�
     * @param j �к�
     * @param type ��Ԫ������
     * @param s ��Ԫ���ַ�
     * @param value ��Ԫ������
     * @param values  ��ѡ��Ŀ�ѡ���ݣ�������Ǹ�ѡ���봫��null
     */
    public void setCellObject(int i, int j, int type, String s, Object value,
                              Vector values)
    {
        this.addCell(i, j, s, type, value, values);
    }


    /**
     * ���Ƹ�ѡ����µ�ѡ��״̬
     * @param i �к�
     * @param j �к�
     * @param flag ѡ��״̬
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
     * ���Ƶ�ѡ����µ�ѡ��״̬
     * @param i �к�
     * @param j �к�
     * @param flag ѡ��״̬
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
     * ����������ѡ�еĶ���
     * @param i �к�
     * @param j �к�
     * @param value ѡ�ж���
     * @param values Vector ��ѡ�Ķ��󼯺�
     */
    public void setComboBoxSelected(int i, int j, Object value, Vector values)
    {
        this.addComboBoxCell(i, j, value, values);
    }


    /**
     * �õ���ǰ���ѡ�еĶ���
     * @param i ��ǰ����
     * @param j ��ǰ����
     * @return Object ѡ�еĶ���
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
     * ���ص�i ��,��j �� ��ѡ���ѡ��״̬
     * @param i �к�
     * @param j �к�
     * @return boolean ��ѡ���ѡ��״̬,trueΪѡ��
     */
    public boolean isCheckboxSelected(int i, int j)
    {
        return ((Boolean) getCellAt(i, j).getValue()).booleanValue();
    }


    /**
     * ���ص�i ��,��j �� ��ѡ���ѡ��״̬
     * @param i �к�
     * @param j �к�
     * @return boolean ��ѡ���ѡ��״̬,trueΪѡ��
     */
    public boolean isRadioButtonSelected(int i, int j)
    {
        return ((Boolean) getCellAt(i, j).getValue()).booleanValue();
    }


    /**
     * ���ص�i ��,��j �� �������ѡ�����
     * @param i �к�
     * @param j �к�
     * @return Object �������ѡ�����
     */
    public Object getComboBoxObject(int i, int j)
    {
        return getCellAt(i, j).getValue();
    }


    /**
     * ���ص�i ��,��j �� ��������ַ�
     * @param i �к�
     * @param j �к�
     * @return String ��������ַ�
     */
    public String getComboBoxString(int i, int j)
    {
        return getCellAt(i, j).getStringValue();
    }


    /**
     * �ڵ�i��j����ӵ�Ԫ
     * @param i �к�
     * @param j �к�
     * @param s �ı���ʾ
     * @param type ��Ԫ���� ��4�֣�
     * CappTextAndImageCell.TEXT,�ı�����
     * CappTextAndImageCell.CHECKBOX,   ��ѡ��
     * CappTextAndImageCell.COMBOBOX,  ������
     * CappTextAndImageCell.RADIOBUTTON ��ѡ��ť
     * @param value ��Ԫ��ֵ
     * @param vs  ������Ŀ�ѡֵ����
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
     * ��ָ���к�������������ַ���Ԫ
     * @param i int �к�
     * @param j int �к�
     * @param vevtor Vector ���������ַ�����Դ��ʸ������
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

        //20060815Ѧ���޸ģ�ԭ���ӡҪ��
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
     * �ڵ�i��,��j �� ����ı�
     * @param i ��i��
     * @param j ��j��
     * @param s ����ӵ��ı�
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
     * �ڵ�i��,��j����Ӹ�ѡ��
     * @param i ��i��
     * @param j ��j��
     * @param flag �Ƿ�Ϊѡ�и�ѡ��
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
     * �ڵ�i��,��j����Ӵ����ı��ĸ�ѡ��
     * @param  i ��i��
     * @param  j ��j��
     * @param  s �ı�
     * @param  flag �Ƿ�Ϊѡ���ı�
     */
    public void addCheckBoxCell(int i, int j, String s, boolean flag)
    {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(s,
                CappTextAndImageCell.CHECKBOX, new Boolean(flag), null);
        addCellImpl(i, j, checkboxcell);
    }


    /**
     * �ڵ�i��,��j����ӵ�ѡ��
     * @param i ��i��
     * @param j ��j��
     * @param flag �Ƿ�Ϊѡ�е�ѡ��
     */
    public void addRadioButtonCell(int i, int j, boolean flag)
    {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(
                CappTextAndImageCell.RADIOBUTTON, new Boolean(flag), null);
        addCellImpl(i, j, checkboxcell);
    }


    /**
     * �ڵ�i��,��j����Ӵ����ı��ĵ�ѡ��
     * @param  i ��i��
     * @param  j ��j��
     * @param  s �ı�
     * @param  flag �Ƿ�Ϊѡ���ı�
     */
    public void addRadioButtonCell(int i, int j, String s, boolean flag)
    {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(s,
                CappTextAndImageCell.RADIOBUTTON, new Boolean(flag), null);
        addCellImpl(i, j, checkboxcell);
    }


    /**
     * �ڵ�i��,��j�����������
     * @param i ��i��
     * @param j ��j��
     * @param value ��������ʾ�Ķ���
     * @param values Vector ���������ʾ�Ķ��󼯺�
     */
    public void addComboBoxCell(int i, int j, Object value, Vector values)
    {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(
                CappTextAndImageCell.COMBOBOX, value, values);
        addCellImpl(i, j, checkboxcell);
    }


    /**
     * �ڵ�i��,��j�����������
     * @param i ��i��
     * @param j ��j��
     * @param value ��������ʾ�Ķ���
     * @param values Vector ���������ʾ�Ķ��󼯺�
     */
    public void addComboBoxCell(int i, int j, String s, Object value,
                                Vector values)
    {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(s,
                CappTextAndImageCell.COMBOBOX, value, values);
        addCellImpl(i, j, checkboxcell);
    }


    /**
     * �ڵ�i��,��j����Ӵ����ı��ĵ�ѡ��
     * @param  i ��i��
     * @param  j ��j��
     * @param  s �ı�
     * @param value ��������ʾ�Ķ���
     * @param values Vector ���������ʾ�Ķ��󼯺�
     */
    /**  public void addComboBoxCell(int i, int j, String s, Object value,Vector values)
      {
        CappTextAndImageCell checkboxcell = new CappTextAndImageCell(s,CappTextAndImageCell.COMBOBOX,value,values);
        addCellImpl(i, j, checkboxcell);
      }*/

    /**
     * �õ�ѡ�е�����
     * @return int ѡ�е�����
     */
    public int getSelectedColumn()
    {
        return this.table.getSelectedColumn();
    }


    /**
     * ���õ�Ԫ�������.
     * @param f ������
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
    
  //��ָ��ֵ�����п�ע�Ͳ����ǰ����ݵ����п�
	public void FitTableColumns(int[] columnsWidth) {  
		JTable myTable=this.getTable();
		//�O��table���Ќ��S�����{��
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
