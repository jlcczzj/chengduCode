/** ���ɳ���UsesJPanel.java	1.1  2003/02/28
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ��ӽṹ��ʱʹ���µ��������棬������ͼ�������������ĵļ����ǽ�ŵļ��� guoxiaoliang 2016-4-20
 */
package com.faw_qm.part.client.design.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.beans.query.CappSchema;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.prodmgmt.HelperPanel;
import com.faw_qm.clients.prodmgmt.Spinner;
import com.faw_qm.clients.util.EnumeratedChoice;
import com.faw_qm.clients.util.QMMultiList;
import com.faw_qm.clients.widgets.IBAUtility;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingClassificationInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.part.client.design.model.UsageInterfaceItem;
import com.faw_qm.part.client.design.model.UsageItem;
import com.faw_qm.part.client.design.model.UsesInterfaceList;
import com.faw_qm.part.client.design.util.PartDesignViewRB;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.Unit;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.part.client.main.util.PartScreenParameter;
import com.faw_qm.part.client.other.view.BasicSearchJDialog;
import com.faw_qm.persist.util.PersistHelper;


/**
 * <p>Title:�༭ʹ�ýṹ��塣 </p>
 * <p>Description: �㲿��ʹ�ýṹ�����Ŀ��ӻ����档�ڱ������Զ��㲿����</p>
 * <P>ʹ�ù������д��������¡�ɾ�����鿴��ά��������</P>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class UsesJPanel extends HelperPanel
{
    /**���л�ID*/
    static final long serialVersionUID = 1L;

    /**ʹ�ýṹ�б��ID�б��*/
    public static final int ID_COLUMN = 0;


    /**ʹ�ýṹ�б�ı���б��*/
    public static final int NUMBER_COLUMN = 1;


    /**ʹ�ýṹ�б�������б��*/
    public static final int NAME_COUMN = 2;


    /**ʹ�ýṹ�б�������б��*/
    public static final int QTY_COLUMN = 3;


    /**ʹ�ýṹ�б�ĵ�λ�б��*/
    public static final int UNIT_COLUMN = 4;


    /**���ڱ����Դ�ļ�·��*/
    protected static final String RESOURCE
            = "com.faw_qm.part.client.design.util.PartDesignViewRB";


    /**ʹ����Ӻ�ɾ��������*/
    protected UsesInterfaceList usesList = null;


    /**�жϽ���ģʽ�Ĳ�������*/
    protected boolean updateMode = false;


    /**��Ӱ�ť*/
    private JButton addUsageJB = new JButton();


    /**��ȥ��ť*/
    private JButton removeUsageJB = new JButton();


    /**�鿴��ť*/
    private JButton viewUsageJB = new JButton();


    /**�����Ϸ���ʹ���б�����*/
    private JPanel usesPartJP = new JPanel();


    /**�����Ϸ��ð�ť(��ӡ���ȥ���鿴)�����*/
    private JPanel usesButtonJP = new JPanel();


    /**�����Ϸ���usesButtonJPanel��updateUsageJPanel�����*/
    private JPanel actionJP = new JPanel();


    /**�����Ϸ�������΢�����͵�λѡ�������*/
    private JPanel updateUsageJP = new JPanel();


    /**������ǩ*/
    private JLabel quantityJL = new JLabel();


    /**��λ��ǩ*/
    private JLabel unitJL = new JLabel();


    /**ʹ���б�*/
    private QMMultiList usesMultiList = new QMMultiList();


    /**����΢����*/
    private Spinner quantitySpinner = new Spinner();


    /**��������������*/
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();


    /**��ǰ��������µ��㲿��ֵ����*/
    private QMPartIfc partIfc1 = null;


    /**��λѡ���*/
    private EnumeratedChoice unitChoice = new EnumeratedChoice();

    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();
    
    //CCBegin SS1
    private MyQuery cappQuery = new MyQuery();
//CCEnd SS1

    /**
     * ���캯����
     */
    public UsesJPanel()
    {
        super();
        try
        {
            this.jbInit();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }


    /**
     * ��ʼ����
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        this.setSize(new Dimension(620, 206));
        this.setLayout(gridBagLayout5);
        //JLabel
        unitJL.setFont(new java.awt.Font("Dialog", 0, 12));
        unitJL.setHorizontalAlignment(SwingConstants.RIGHT);
        unitJL.setHorizontalTextPosition(SwingConstants.RIGHT);
        unitJL.setText("Unit:");
        unitJL.setBounds(new Rectangle(5, 34, 41, 18));
        quantityJL.setFont(new java.awt.Font("Dialog", 0, 12));
        quantityJL.setHorizontalAlignment(SwingConstants.RIGHT);
        quantityJL.setHorizontalTextPosition(SwingConstants.RIGHT);
        quantityJL.setText("Quantity:");
        quantityJL.setBounds(new Rectangle(5, 9, 41, 18));
        //JButton
        addUsageJB.setBounds(new Rectangle(19, 0, 59, 23));
        addUsageJB.setFont(new java.awt.Font("Dialog", 0, 12));
        addUsageJB.setMaximumSize(new Dimension(65, 23));
        addUsageJB.setMinimumSize(new Dimension(65, 23));
        addUsageJB.setPreferredSize(new Dimension(83, 23));
        addUsageJB.setMnemonic('A');
        addUsageJB.setText("Add");
        removeUsageJB.setBounds(new Rectangle(19, 42, 59, 23));
        removeUsageJB.setFont(new java.awt.Font("Dialog", 0, 12));
        removeUsageJB.setMaximumSize(new Dimension(65, 23));
        removeUsageJB.setMinimumSize(new Dimension(65, 23));
        removeUsageJB.setPreferredSize(new Dimension(83, 23));
        removeUsageJB.setMnemonic('R');
        removeUsageJB.setText("Remove");
        viewUsageJB.setBounds(new Rectangle(19, 84, 59, 23));
        viewUsageJB.setFont(new java.awt.Font("Dialog", 0, 12));
        viewUsageJB.setMaximumSize(new Dimension(65, 23));
        viewUsageJB.setMinimumSize(new Dimension(65, 23));
        viewUsageJB.setPreferredSize(new Dimension(83, 23));
        viewUsageJB.setMnemonic('V');
        viewUsageJB.setText("View");

        //��������
        usesMultiList.setNumberOfCols(5);
        usesMultiList.setMultipleMode(true);
        //���÷�Χ
        usesMultiList.setBounds(new Rectangle(0, 0, 493, 192));
        usesMultiList.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                usesMultiList_itemStateChanged(e);
            }
        });
        usesMultiList.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                usesMultiList_actionPerformed(e);
            }
        });
        //���ñ�ͷ
        usesMultiList.setHeadings(new String[]
                                  {"ID", "Number", "Name", "Qty",
                                  "Unit"});
        //�����ж���
        usesMultiList.setColumnAlignments(new String[]
                                          {"left", "left", "left",
                                          "right", "left"});
        //�����п�
        usesMultiList.setRelColWidth(new int[]
                                     {0, 3, 3, 2, 2});
        //����б�ѡ�����
        usesMultiList.getSelectModel().addListSelectionListener(new
                UsesSelection());
        //���õ�Ԫ�񲻿ɱ༭
        usesMultiList.setCellEditable(false);
        //������Сֵ
        quantitySpinner.setMin(0);
        //�������ֵ
        quantitySpinner.setMax(99999);

        quantitySpinner.addMinListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                quantitySpinner_propertyChange(e);
            }
        });
        quantitySpinner.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                quantitySpinner_actionPerformed(e);
            }
        });
        //��������������
        quantitySpinner.setIncrement(1);
        //��Ӽ��̲�������
        quantitySpinner.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(KeyEvent e)
            {
                quantitySpinner_keyReleased(e);
            }
        });
        //��������ع�
        quantitySpinner.setWrappable(false);

        //JPanel
        usesPartJP.setLayout(gridBagLayout4);
        unitChoice.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                unitChoice_itemStateChanged(e);
            }
        });
        usesButtonJP.setLayout(gridBagLayout1);
        actionJP.setLayout(gridBagLayout3);
        updateUsageJP.setLayout(gridBagLayout2);

        usesButtonJP.add(removeUsageJB,
                         new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(8, 8, 0, 8), 15, 0));
        usesButtonJP.add(viewUsageJB,
                         new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(8, 8, 0, 8), 15, 0));
        usesButtonJP.add(addUsageJB,
                         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(2, 8, 0, 8), 15, 0));
        updateUsageJP.add(unitJL, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.5
                , GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(1, 2, 0, 0), 5, 0));
        updateUsageJP.add(quantitySpinner,
                          new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0
                                                 , GridBagConstraints.WEST,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(0, 5, 0, 0), 24, -6));
        updateUsageJP.add(quantityJL,
                          new GridBagConstraints(0, 0, 1, 1, 0.0, 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.NONE,
                                                 new Insets(0, 2, 0, 0), 5, 0));
        updateUsageJP.add(unitChoice,
                          new GridBagConstraints(1, 1, 1, 1, 0.0, 0.5
                                                 , GridBagConstraints.NORTHWEST,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(0, 5, 0, 0), -66,
                                                 -2));
        actionJP.add(usesButtonJP, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.SOUTH, GridBagConstraints.NONE,
                new Insets(3, 6, 0, 32), -14, 0));
        actionJP.add(updateUsageJP, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                new Insets(0, 1, 0, 8), 0, 43));
        this.add(actionJP, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                  , GridBagConstraints.NORTH,
                                                  GridBagConstraints.NONE,
                                                  new Insets(0, 0, 0, 0), 0, 0));
        this.add(usesPartJP, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 6, 5, 0), 0, 0));
        usesPartJP.add(usesMultiList,
                       new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.BOTH,
                                              new Insets(0, 2, 5, 0), 0, 0));

        //}}

        //���õ�λ��ö��ֵ
        unitChoice.setEnumeratedTypeClassName("com.faw_qm.part.util.Unit");

        localize();
        addUsageJB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addUsageJButton_actionPerformed(e);
            }
        });
        removeUsageJB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                removeUsageJButton_actionPerformed(e);
            }
        });
        viewUsageJB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                viewUsageJButton_actionPerformed(e);
            }
        });
       
    }
//CCBegin SS1
    /**��չ�����Ŀ����ʹ��ѯ����б�֧�����˫������*/
    class MyQuery extends CappQuery {
        public CappMultiList getResultList() {
            return this.getMyList();
        }
    }
    
    //CCEnd SS1
    
    /**
     * ʵ�����б�ѡȡ��������ѡ����ʹ�ýṹ�б��е�һ���㲿��ʱ���ɶԸ��㲿�������޸ġ�
     */
    class UsesSelection implements ListSelectionListener
    {
        /**
         * �����ѡ����ʹ�ýṹ�б��е�һ���㲿�����򼤻�����΢��������λѡ��������ȥ
         * ��ť�Ͳ鿴��ť��������΢������ֵ����Ϊʹ�ýṹ�б��е�ǰ��ѡ���㲿��������
         * ֵ������λѡ������ֵѡ��Ϊʹ�ýṹ�б��е�ǰ��ѡ���㲿���ĵ�λֵ��������
         * ��΢��������λѡ��������ȥ��ť�Ͳ鿴��ť����δ����״̬��
         * @param e ListSelectionEvent
         */
        public void valueChanged(ListSelectionEvent e)
        {
            if (e.getValueIsAdjusting())
            {
                PartDebug.debug("valueChanged��event�� begin ...", this,
                                PartDebug.PART_CLIENT);
                int i = usesMultiList.getSelectedRow();
                if (i != -1)
                {
                    if (updateMode)
                    {
                        quantitySpinner.setEnabled(true);
                        quantitySpinner.setEditable(true);
                        unitChoice.setEnabled(true);
                        removeUsageJB.setEnabled(true);
                    }
                    viewUsageJB.setEnabled(true);

                    try
                    {
                        //��ȡ��ǰ��ѡ���㲿��������ֵ��ʮ�������ݸ�ʽֵ
                        Number number = UsageItem.getDecimalFormat().parse(
                                usesMultiList.getCellText(i, QTY_COLUMN));
                        //��õ�ǰ��ѡ���㲿����������˫����ֵ
                        double d = number.doubleValue();
                        //������΢������ֵ����Ϊʹ�ýṹ�б��е�ǰ��ѡ���㲿��������ֵ
                        quantitySpinner.setCurrentDoubleValue(d);
                        Object obj = (Object) usesMultiList.getCellText(i,
                                UNIT_COLUMN);
                        //����λѡ������ֵѡ��Ϊʹ�ýṹ�б��е�ǰ��ѡ���㲿���ĵ�λֵ
                        unitChoice.setSelectedItem(obj);
                        //�����ٴ�ѡ����ѡ�е��㲿��
                        usesMultiList.setSelectedRow(i);
                    }
                    catch (PropertyVetoException pve)
                    {
                        pve.printStackTrace();
                    }
                    catch (java.text.ParseException pe)
                    {
                        pe.printStackTrace();
                    }

                }
                else
                {
                    quantitySpinner.setEnabled(false);
                    quantitySpinner.setEditable(false);
                    unitChoice.setEnabled(false);
                    viewUsageJB.setEnabled(false);
                    removeUsageJB.setEnabled(false);
                }
                PartDebug.debug("valueChanged��event�� end...return is void", this,
                                PartDebug.PART_CLIENT);
            }
        }
    }


    /**
     * �÷������߳�WorkThread�ڲ����ã��Գ�ʼ��������
     */
    public void setPartItem()
    {
        PartDebug.debug("setPartItem() begin....", this, PartDebug.PART_CLIENT);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //��õ�ǰPartItem ʹ�õ��㲿�����б�
        //����ƿ����
        usesList = getPartItem().getUsesInterfaceList();
        //�����Խ��г�ʼ��,��������
        usesList.startFrame();
        //�ѵ�ǰPartItem ʹ�õ������㲿������ʹ�ýṹ�б���
        usesMultiList.setListItems(usesList.toMultiList());
        //����Ĭ��ѡ�е�һ��
        if (usesList.toMultiList() != null &&
            usesList.toMultiList().length != 0)
        {
            usesMultiList.setSelectedRow(0);
            usesMultiList_itemStateChanged(null);
        }
        setCursor(Cursor.getDefaultCursor());
        PartDebug.debug("setPartItem() end....return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * ���������ѷ����ı仯��
     * @return  �������ɹ����򷵻�Ϊ��,���򷵻�false��
     * @exception QMRemoteException
     */
    public boolean save()
            throws QMRemoteException
    {
        PartDebug.debug("save() begin ....", this, PartDebug.PART_CLIENT);
        //����ʹ�ýṹ�б��е������㲿���������͵�λ
        setValues();

        PartDebug.debug("save() end ....return is boolean", this,
                        PartDebug.PART_CLIENT);
        //�������ڵ����ж�����£��������һ�α���������ı䷵��true�����򷵻�false��
        return usesList.saveFrame();

    }


    /**
     * ����ʹ�ýṹ�б��е�ÿ���㲿���������͵�λ��
     */
    protected void setValues()
    {
        PartDebug.debug("setValues() begin ....", this, PartDebug.PART_CLIENT);
        //ʹ���б������
        int size = usesMultiList.getNumberOfRows();
        //����ʹ���б��е������͵�λֵ
        for (int row = 0; row < size; row++)
        {
            //���ݵ�ǰ�㲿����ID��ȡ�����еĶ�Ӧ��ʹ�ù�����UsageInterfaceItem
            UsageInterfaceItem usage =
                    usesList.interfaceElementAt(usesMultiList.getCellText(row,
                    0));

            float gui_quantity = 0; //�����ĸ���ֵ
            //��������ֵ
            try
            {
                //��ȡ��ǰ�㲿��������ֵ��ʮ�������ݸ�ʽֵ
                Number number = UsageItem.getDecimalFormat().parse(
                        usesMultiList.getCellText(row, QTY_COLUMN));
                gui_quantity = number.floatValue();
            }
            catch (java.text.ParseException pe)
            {
                pe.printStackTrace();
            }
            //���ʹ�ù������е����������ڵ�ǰ�㲿��������ֵ
            if (usage.getQuantity() != gui_quantity)
            {
                //��������Ϊ��ǰ�㲿��������
                usage.setQuantity(gui_quantity);
            }

            //���õ�λֵ
            //��ǰ��ѡ�еĵ�λ
            Unit gui_unit = getSelectedUnit(row);
            //ʹ�ù������е�������λ
            Unit saved_unit = usage.getUnits();
            //���ʹ�ù������е�������λΪ��,���߲����ڵ�ǰ�㲿���ĵ�λ��������
            //ʹ�ù������е�������λΪ��ǰ�㲿���ĵ�λ
            if (saved_unit == null)
            {
                //���õ�λΪ��ǰ�㲿���ĵ�λ
                usage.setUnit(gui_unit);
            }
            else if (!saved_unit.equals(gui_unit))
            {
                usage.setUnit(gui_unit);
            }

        }
        PartDebug.debug("setValues() end ....return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * ���ʹ�ýṹ�б���ָ���еĵ�λ��
     * @param row ʹ�ýṹ�б���ָ�����С�
     * @return foundUnit ʹ�ýṹ�б���ָ���еĵ�λ��
     */
    public Unit getSelectedUnit(int row)
    {
        PartDebug.debug("getSelectedUnit(int row) begin ...", this,
                        PartDebug.PART_CLIENT);
        Unit foundUnit = null;
        //ָ���еĵ�λֵ���ַ�����
        String stringUnit = usesMultiList.getCellText(row, UNIT_COLUMN);
        //��õ�λ��������������
        Unit[] types = Unit.getUnitSet();
        //���ָ���㲿���ĵ�λ��ĳ��λ����������������øõ�λ��������
        for (int i = 0; i < types.length; i++)
        {
            if (stringUnit.equals(types[i].getDisplay()))
            {
                foundUnit = types[i];
                break;
            }
        }
        PartDebug.debug("getSelectedUnit(int row) end...return " + foundUnit, this,
                        PartDebug.PART_CLIENT);
        return foundUnit;
    }


    /**
     * ���ػ�������
     */
    protected void localize()
    {
        try
        {
            // labels
            quantityJL.setText(getLabelsRB().getString("quantityJLabel"));
            unitJL.setText(getLabelsRB().getString("unitJLabel"));

            // buttons
            viewUsageJB.setText(getLabelsRB().getString("viewUsageJButton"));
            addUsageJB.setText(getLabelsRB().getString("addUsageJButton"));
            removeUsageJB.setText(getLabelsRB().getString("removeUsageJButton"));

            // headings
            usesMultiList.setHeadings(new String[]
                                      {
                                      getLabelsRB().getString("idHeading"),
                                      getLabelsRB().getString("numberHeading"),
                                      getLabelsRB().getString("nameHeading"),
                                      getLabelsRB().getString("quantityHeading"),
                                      getLabelsRB().getString("unitHeading")});

        }
        catch (MissingResourceException mre)
        {
            mre.printStackTrace();
        }
    }


    /**
     * ��ʼ������ϵͳ��
     */
    public void initializeHelp()
    {
    }


    /**
     * �鿴ʹ�ýṹ�б��б�ѡ�е��
     */
    public void viewSelectedItem()
    {
        PartDebug.debug("viewSelectedItem() begin....", this,
                        PartDebug.PART_CLIENT);
        try
        {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            usesMultiList.setCursor(Cursor.getPredefinedCursor(Cursor.
                    WAIT_CURSOR));
            //ʹ�ýṹ�б��б�ѡ�е���
            int i = usesMultiList.getSelectedRow();

            if (i != -1)
            {
            	//////////////////////begin I//////////////////////////////
            	//add by muyp 20080507
            	//�޸�ԭ�򣺶��㲿������Ϣ������в鿴��ҳ����ʾ����ȷ(TD1754)
            	BaseValueIfc bvi = usesMultiList.getSelectedObject();
            	if(bvi instanceof QMPartMasterIfc)
            	{
            		 QMPartMasterIfc master = (QMPartMasterIfc) bvi;
                     String masterBsoID = master.getBsoID();
                     ///////////////////begin II///////////////
                     //���÷���
                     ServiceRequestInfo info = new ServiceRequestInfo();
                     info.setServiceName("StandardPartService");
                     info.setMethodName("findPart");
                     Class[] theClass = {QMPartMasterIfc.class};
                     info.setParaClasses(theClass);
                     Object[] objs = {master};
                     info.setParaValues(objs);
                     /////////////////end II///////////////
                     RequestServer server = RequestServerFactory.getRequestServer();
                     try {
         				Collection links = (Collection) server.request(info);
         				if(links != null)
         				{
                             Iterator iter = links.iterator();
                             String partID = "";
                             if (iter.hasNext())//�����ֵ����ȡ��ֵ
                             {
                             	QMPartIfc link = (QMPartIfc) iter.next();
                                 partID = link.getBsoID();
                             }
                             HashMap hashmap = new HashMap();
                             hashmap.put("BsoID", masterBsoID);
                             hashmap.put("bsoID", partID);
                             RichToThinUtil.toWebPage("part_version_iterationsViewMain.screen",
                             		hashmap);
                             masterBsoID = null;
                             partID = null;
                         }//end if 1
         			} catch (QMRemoteException e) {
         				
         				e.printStackTrace();
         			}
            	}///end if(bvi instanceof QMPartMasterIfc)
            	else
            	{
            		/////////////////////////end I/////////////////////////////////
//            		����ʹ���б�����ѡ���ID��������ж�Ӧ��ʹ�ù�����
                    UsageInterfaceItem usageInterfaceItem =
                            usesList.interfaceElementAt(usesMultiList.getCellText(i,
                            0));
                    //�����ѡ�㲿��������Ϣ
                    QMPartMasterIfc master =
                            (QMPartMasterIfc) usageInterfaceItem.getUsedBy();
                    //��õ�ǰ��ɸѡ������
                    ConfigSpecItem configSpecItem = getPartItem().getConfigSpecItem();

                    //���ɸѡ������Ϊ�գ���õ�ǰ�ͻ��˵�ɸѡ����
                    if (configSpecItem == null)
                    {
                        PartConfigSpecIfc partConfigSpecIfc = null;

                        // �����������ɸѡ����
                        try
                        {
                            partConfigSpecIfc =
                                    (PartConfigSpecIfc) PartHelper.getConfigSpec();
                        }
                        catch (QMRemoteException qre)
                        {
                            String title = QMMessage.getLocalizedMessage(RESOURCE,
                                    "exception", null);
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    qre.getClientMessage(),
                                    title,
                                    JOptionPane.INFORMATION_MESSAGE);
                        }

                        //���ɸѡ������Ϊ�գ���ɸѡ��������ɸѡ����������½�ɸѡ������
                        if (partConfigSpecIfc != null)
                        {
                            configSpecItem = new ConfigSpecItem(partConfigSpecIfc);
                        }
                        else
                        {
                            configSpecItem = new ConfigSpecItem();
                        }

                    }

                    //�����㲿������Ϣ��ȡ���㲿�������з������ù淶�İ汾
                    Vector partInfoVector = PartHelper.getAllVersions(master,
                            configSpecItem.getConfigSpecInfo());
                    PartDebug.debug("*** ���㲿�������з������ù淶�İ汾:" + partInfoVector,
                                    PartDebug.PART_CLIENT);
                    Object[] qmPartIfc = partInfoVector.toArray();
                    PartDebug.debug("****************" + qmPartIfc, this,
                                    PartDebug.PART_CLIENT);
                    //���Ϊ�գ���ʾ��Ϣ����û�з��������İ汾��
                    if (qmPartIfc == null)
                    {
                        try
                        {
                            Object[] params =
                                              {
                                              ((QMPartMasterInfo) master).
                                              getIdentifyObject().
                                              getIdentity()};
                            String message = getLocalizedMessage(
                                    PartDesignViewRB.NO_QUA_VERSION, params);
                            String title = getLocalizedValue("errorTitle");
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    message,
                                    title,
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        catch (QMException ex)
                        {
                            String title = QMMessage.getLocalizedMessage(RESOURCE,
                                    "exception", null);
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    ex.getClientMessage(),
                                    title,
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else if (qmPartIfc.length == 0)
                    {
                        try
                        {
                            Object[] params =
                                              {
                                              ((QMPartMasterInfo) master).
                                              getIdentifyObject().
                                              getIdentity()};
                            String message = getLocalizedMessage(
                                    PartDesignViewRB.NO_QUA_VERSION, params);
                            String title = getLocalizedValue("errorTitle");
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    message,
                                    title,
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        catch (QMException ex)
                        {
                            String title = QMMessage.getLocalizedMessage(RESOURCE,
                                    "exception", null);
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    ex.getClientMessage(),
                                    title,
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                    //��ȡ��ѡ���㲿�������°汾
                    QMPartIfc part = null;
                    PartDebug.debug("$$$$$$$$ ��ѡ���㲿�������°汾: " + qmPartIfc[0],
                                    PartDebug.PART_CLIENT);

                    if (qmPartIfc[0] instanceof Object[])
                    {
                        Object[] partObj = (Object[]) qmPartIfc[0];
                        part = (QMPartIfc) partObj[0];
                        PartDebug.debug("+++++++ ��ѡ���㲿�������°汾:" + part,
                                        PartDebug.PART_CLIENT);
                    }

                    //�����Ϊ�գ��鿴���㲿�������°汾�������ԣ�ת���鿴�㲿������ҳ�棩
                    if (part != null)
                    {
                        PartDebug.debug("-----------����鿴�㲿������ҳ��------------",
                                        PartDebug.PART_CLIENT);
                        String partID = part.getBsoID();
                        HashMap map = new HashMap();
                        map.put("bsoID", partID);
                        map.put("flag", "0");
                        RichToThinUtil.toWebPage(
                                "Part-Other-PartLookOver-001-0A.do", map);
                        PartDebug.debug("UsesJPanel:RichToThinUtil Successed !",
                                        PartDebug.PART_CLIENT);
                    }
            	}
            }
        }

        catch (QMRemoteException qre)
        {
            qre.printStackTrace();
            String title = getLocalizedValue("informationTitle");
            JOptionPane.showMessageDialog(getParentFrame(),
                                          qre.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        finally
        {
            usesMultiList.setCursor(Cursor.getDefaultCursor());
            setCursor(Cursor.getDefaultCursor());
        }
        PartDebug.debug("viewSelectedItem() end ....return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * ����ˢ��֪ͨ��
     */
    public void addNotify()
    {
        super.addNotify();
    }

   
    /**
     * ��������������Ҫʹ�õ����㲿����
     * @param event the action event
     */
    void addUsageJButton_actionPerformed(ActionEvent event)
    {
    	
    	//CCBegin SS1
    	
//        PartDebug.debug("addUsageJButton_actionPerformed(e) begin...", this,
//                        PartDebug.PART_CLIENT);
//        //��ø�����
//        JFrame frame = getParentFrame();
//        String partBsoName = QMMessage.getLocalizedMessage(RESOURCE,
//                PartDesignViewRB.QMPM_BSONAME, null);
//        String title = QMMessage.getLocalizedMessage(RESOURCE,
//                "findPartTitle", null);
//
//        //����������
//        QmChooser qmChooser = new QmChooser(partBsoName, title, frame);
//        qmChooser.setChildQuery(false);
//        qmChooser.setRelColWidth(new int[]
//                                 {1, 1});
//        PartDebug.debug("************���������㲿���ɹ���������qmChooser = " + qmChooser,
//                        PartDebug.PART_CLIENT);
//        //���ո���������ִ������
//        qmChooser.addListener(new QMQueryListener()
//        {
//
//            public void queryEvent(QMQueryEvent e)
//            {
//                qmChooser_queryEvent(e);
//            }
//        });
//
//        qmChooser.setVisible(true);
//        PartDebug.debug(
//                "addUsageJButton_actionPerformed(e) end...return is void", this,
//                PartDebug.PART_CLIENT);
    	
//    	CappSchema mySchema=null;
//    	
//        String SCHEMA = "C:QMPart; G:��������;A:partNumber;A:partName;A:versionValue;A:viewName";
//        //�����ѯ����
//        try {
//        	 mySchema = new CappSchema(SCHEMA);
//        }
//        catch (QMRemoteException ex) {
//          String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
//                  null);
//          JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
//                  JOptionPane.INFORMATION_MESSAGE);
//         return ;
//        }
//        //���ò�ѯ����
//        cappQuery.setSchema(mySchema);
////        cappQuery.notAcessInPersonalFolder();
//        cappQuery.setLastIteration(true);
//        
//        cappQuery.setVisible(true);

        
    	 AddSearchJDialog searchDialog = new AddSearchJDialog( getParentFrame());
         searchDialog.setSize(700, 650);
         //ʹ��������Ļ������ʾ
         PartScreenParameter.setLocationCenter(searchDialog);
         searchDialog.setVisible(true);

         
         //CCEnd SS1
    	
    	
    }
    
  //CCBegin SS1
    
    /**
     * �����㲿�������¼�������
     * @param e ���������¼���
     */
    public void qmChooser_queryEvent2(CappQueryEvent e)
    {
        PartDebug.debug("qmChooser_queryEvent(e) begin...", this,
                        PartDebug.PART_CLIENT);
        //��ǰ��������µ��㲿��ֵ����
        //2003/12/18
        if (this.getPartItem() != null)
        {
            partIfc1 = this.getPartItem().getPart();
        }
        PartDebug.debug("��ǰ��������µ��㲿��ֵ���� partIfc1 = " + partIfc1,
                        PartDebug.PART_CLIENT);
        if (e.getType().equals(CappQueryEvent.COMMAND))
        {
            if (e.getCommand().equals(CappQuery.OkCMD))
            {
                //��������������������������㲿��
            	 MyQuery c = (MyQuery) e.getSource();

                BaseValueIfc[] bvi = c.getSelectedDetails();
                PartDebug.debug("�����ѡ��������㲿�� bvi = " + bvi,
                                PartDebug.PART_CLIENT);
                if (bvi != null)
                {
                    for (int i = 0; i < bvi.length; i++)
                    {
                        PartDebug.debug("************partIfc1 = " + partIfc1,
                                        PartDebug.PART_CLIENT);
                        //��ѡ���ĳһ�㲿��
                        BaseValueIfc newPart = bvi[i];
                        //����㲿��������Ϣ
//                        QMPartMasterIfc newPartMaster = (QMPartMasterIfc)newPart;
                        //���Ҫ��ӵ��㲿��������С�汾
                        QMPartIfc partIfc2 = (QMPartIfc)newPart;
                        QMPartMasterIfc newPartMaster = (QMPartMasterIfc)partIfc2.getMaster();
                        PartDebug.debug("���Ҫ��ӵ��㲿��������С�汾 partIfc2 = " +
                                        partIfc2, PartDebug.PART_CLIENT);
                        //�����ж�partIfc1��partIfc2�Ƿ�ʹ�ýṹǶ��
                        boolean flag = false;
                        //�����ǰ�Ǹ����㲿�����棬�����partIfc1��partIfc2�Ƿ�ʹ�ýṹǶ��
                        if (partIfc1.getBsoID() != null && partIfc2 != null)
                        {
                            flag = this.isParentPart(partIfc1, partIfc2);
                        }

                        //�������Ϊ�棬��ʾʹ�ýṹǶ�ס����򣬽�һ������Ƿ��ظ����
                        if (flag == true)
                        {
                            //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
                            DisplayIdentity displayidentity = IdentityFactory.
                                    getDisplayIdentity(partIfc2);
                            //��ö������� + ��ʶ
                            String s = displayidentity.getLocalizedMessage(null);
                            Object[] params ={s};
                            String message = getLocalizedMessage(
                                    PartDesignViewRB.
                                    PART_CF_NESTING,
                                    params);
                            String title = getLocalizedValue(
                                    PartDesignViewRB.
                                    USE_CF_NESTING);
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    message, title,
                                    JOptionPane.WARNING_MESSAGE);
                            

                        }
                        else
                        {
                            //�����㲿��ʹ�ù�ϵ��ģ����
                            UsageInterfaceItem usageInterfaceItem =
                                    new UsageInterfaceItem(getPartItem(),
                                    newPartMaster);
                            //Ϊ����ӵ��㲿������Ĭ�ϵ�λ
                            usageInterfaceItem.setUnit(newPartMaster.
                                    getDefaultUnit());
                            //��ͨ�������㲿����������
                            usesList.insertElementAt(usageInterfaceItem, 0);
                        }
                    }
                }
                else
                {
                    return;
                }
                setValues();
                //���ʹ�ýṹ�б�
                usesMultiList.clear();
                //��������õ��㲿�������ʹ�ýṹ�б���
                usesMultiList.setListItems(usesList.toMultiList());
                //����Ĭ��ѡ�е�һ��
                usesMultiList.setSelectedRow(0);
                usesMultiList_itemStateChanged(null);
            }
        }
        PartDebug.debug("qmChooser_queryEvent(e) end...return is void", this,
                        PartDebug.PART_CLIENT);
    }
    //CCEnd SS1

    /**
     * �����㲿��������Ϣ������㲿��������С�汾��
     * @param partMaster  �㲿��������Ϣ��
     * @return  �㲿��������С�汾��
     */
    private QMPartIfc getLastedIterations(QMPartMasterIfc partMaster)
    {
        PartDebug.debug("getLastedIterations() begin...", this,
                        PartDebug.PART_CLIENT);
        QMPartIfc partIfc = null;
        //���÷��񷽷�������㲿��������С�汾
        Class[] paraClass =
                            {
                            MasteredIfc.class};
        Object[] objs =
                        {
                        partMaster};
        Collection collection = null;
        try
        {
            collection = (Collection) IBAUtility.invokeServiceMethod(
                    "VersionControlService", "allIterationsOf", paraClass, objs);
        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            JOptionPane.showMessageDialog(getParentFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
        Iterator iterator = collection.iterator();
        if (iterator.hasNext())
        {
            //����㲿��������С�汾
            partIfc = (QMPartIfc) iterator.next();
        }
        PartDebug.debug("getLastedIterations() end...return " + partIfc, this,
                        PartDebug.PART_CLIENT);
        return partIfc;
    }


    /**
     * �����㲿��part2�Ƿ����㲿��part1�����Ȳ�������part1����
     * @param part1  ָ�����㲿����ֵ����
     * @param part2  ��������㲿����ֵ����
     * @return ����㲿��part2���㲿��part1�����Ȳ�������part1�����򷵻�true�����򷵻�false��
     */
    private boolean isParentPart(QMPartIfc part1, QMPartIfc part2)
    {
        PartDebug.debug("isParentPart() begin...", this, PartDebug.PART_CLIENT);
        Boolean flag1 = new Boolean(false);
        try
        {
            //���÷��񷽷����ж�partIfc1��partIfc2�Ƿ�ʹ�ýṹǶ��
            flag1 = Boolean.valueOf(PartServiceRequest.isParentPart(part1,
                    part2));
            PartDebug.debug("�Ƿ�ʹ�ýṹǶ��(Boolean) flag1 = " + flag1,
                            PartDebug.PART_CLIENT);
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "exception", null);
            JOptionPane.showMessageDialog(getParentFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
        boolean flag = flag1.booleanValue();
        PartDebug.debug("isParentPart() end...return is " + flag, this,
                        PartDebug.PART_CLIENT);
        return flag;

    }


    /**
     * �����㲿�������¼�������
     * @param e ���������¼���
     */
    public void qmChooser_queryEvent(QMQueryEvent e)
    {
        PartDebug.debug("qmChooser_queryEvent(e) begin...", this,
                        PartDebug.PART_CLIENT);
        //��ǰ��������µ��㲿��ֵ����
        //2003/12/18
        if (this.getPartItem() != null)
        {
            partIfc1 = this.getPartItem().getPart();
        }
        PartDebug.debug("��ǰ��������µ��㲿��ֵ���� partIfc1 = " + partIfc1,
                        PartDebug.PART_CLIENT);
        if (e.getType().equals(QMQueryEvent.COMMAND))
        {
            if (e.getCommand().equals(QmQuery.OkCMD))
            {
                //��������������������������㲿��
                QmChooser c = (QmChooser) e.getSource();

                BaseValueIfc[] bvi = c.getSelectedDetails();
                PartDebug.debug("�����ѡ��������㲿�� bvi = " + bvi,
                                PartDebug.PART_CLIENT);
                if (bvi != null)
                {
                    for (int i = 0; i < bvi.length; i++)
                    {
                        PartDebug.debug("************partIfc1 = " + partIfc1,
                                        PartDebug.PART_CLIENT);
                        //��ѡ���ĳһ�㲿��
                        BaseValueIfc newPart = bvi[i];
                        //����㲿��������Ϣ
                        QMPartMasterIfc newPartMaster = (QMPartMasterIfc)
                                newPart;
                        //���Ҫ��ӵ��㲿��������С�汾
                        QMPartIfc partIfc2 = this.getLastedIterations(
                                newPartMaster);
                        PartDebug.debug("���Ҫ��ӵ��㲿��������С�汾 partIfc2 = " +
                                        partIfc2, PartDebug.PART_CLIENT);
                        //�����ж�partIfc1��partIfc2�Ƿ�ʹ�ýṹǶ��
                        boolean flag = false;
                        //�����ǰ�Ǹ����㲿�����棬�����partIfc1��partIfc2�Ƿ�ʹ�ýṹǶ��
                        if (partIfc1.getBsoID() != null && partIfc2 != null)
                        {
                            flag = this.isParentPart(partIfc1, partIfc2);
                        }

                        //�������Ϊ�棬��ʾʹ�ýṹǶ�ס����򣬽�һ������Ƿ��ظ����
                        if (flag == true)
                        {
                            //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
                            DisplayIdentity displayidentity = IdentityFactory.
                                    getDisplayIdentity(partIfc2);
                            //��ö������� + ��ʶ
                            String s = displayidentity.getLocalizedMessage(null);
                            Object[] params =
                                              {s};
                            String message = getLocalizedMessage(
                                    PartDesignViewRB.
                                    PART_CF_NESTING,
                                    params);
                            String title = getLocalizedValue(
                                    PartDesignViewRB.
                                    USE_CF_NESTING);
                            JOptionPane.showMessageDialog(getParentFrame(),
                                    message, title,
                                    JOptionPane.WARNING_MESSAGE);

                        }
                        else
                        {
                            //�����㲿��ʹ�ù�ϵ��ģ����
                            UsageInterfaceItem usageInterfaceItem =
                                    new UsageInterfaceItem(getPartItem(),
                                    newPartMaster);
                            //Ϊ����ӵ��㲿������Ĭ�ϵ�λ
                            usageInterfaceItem.setUnit(newPartMaster.
                                    getDefaultUnit());
                            //��ͨ�������㲿����������
                            usesList.insertElementAt(usageInterfaceItem, 0);
                        }
                    }
                }
                else
                {
                    return;
                }
                setValues();
                //���ʹ�ýṹ�б�
                usesMultiList.clear();
                //��������õ��㲿�������ʹ�ýṹ�б���
                usesMultiList.setListItems(usesList.toMultiList());
                //����Ĭ��ѡ�е�һ��
                usesMultiList.setSelectedRow(0);
                usesMultiList_itemStateChanged(null);
            }
        }
        PartDebug.debug("qmChooser_queryEvent(e) end...return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * ���ý�����ʾģʽ��
     * @param update_mode ���Ϊ�棬����ʾ��ӡ���ȥ���鿴����ť��������������ť
     * ����������λ���������ɼ���
     */
    public void setUpdateMode(boolean update_mode)
    {
        PartDebug.debug("setUpdateMode() begin...updateMode=" + update_mode, this,
                        PartDebug.PART_CLIENT);
        this.updateMode = update_mode;

        if (update_mode)
        {
            updateUsageJP.setVisible(true); //��������λ
            updateUsageJP.setEnabled(false);
            addUsageJB.setVisible(true);
            removeUsageJB.setVisible(true);
            removeUsageJB.setEnabled(false);
            viewUsageJB.setVisible(true);
            viewUsageJB.setEnabled(false);
            unitChoice.setEnabled(false);
            quantitySpinner.setEnabled(false);
        }
        else
        {
            addUsageJB.setEnabled(false);
            removeUsageJB.setEnabled(false);
            updateUsageJP.setEnabled(false);
            updateUsageJP.setVisible(false);
            addUsageJB.setVisible(false);
            removeUsageJB.setVisible(false);
            usesButtonJP.remove(addUsageJB);
            usesButtonJP.remove(removeUsageJB);
        }
        PartDebug.debug("setUpdateMode() end...return is void", this,
                        PartDebug.PART_CLIENT);
    }


    /**
     * ��ý�����ʾģʽ��
     * @return updateMode
     */

    public boolean getUpdateMode()
    {
        return updateMode;
    }


    /**
     * ��õ�λѡ��������ѡ��ĵ�λ��
     * @return ��ѡ��ĵ�λ��
     */
    public Unit getSelectedUnit()
    {
        Unit foundUnit = (Unit) unitChoice.getSelectedEnumeratedType();
        return foundUnit;
    }


    /**
     * �����ȥ��ťʵ�ֵĹ��ܣ�ɾ��ѡ�е��㲿�������û��ѡ�У�����ȥ�Ͳ鿴��ť
     * ʧЧ�������͵�λ����ʧЧ������Ϊ0��
     * @param event ActionEvent
     */
    void removeUsageJButton_actionPerformed(java.awt.event.ActionEvent event)
    {
        PartDebug.debug("removeUsageJButton_actionPerformed��event�� begin ...", this,
                        PartDebug.PART_CLIENT);
        //�����ѡ�е��㲿��
        //modify by skx 2004.9.21 ֧������ɾ��ʹ�ù�ϵ
        int[] i = usesMultiList.getSelectedRows();
        int m = 0;
        for (int j = 0; j < i.length; j++)
        {
            if (i[j] != -1)
            {
                //��ѡ�е��㲿�����������Ƴ�
                usesList.removeElementAt(usesMultiList.getCellText(i[j] - m, 0));
                //��ѡ�е��㲿����ʹ�ýṹ�б���ɾ��
                usesMultiList.removeRow(i[j] - m);
                m++;
                //���û��ѡ���㲿��,��ȥ�Ͳ鿴��ťʧЧ�������͵�λ����ʧЧ������Ϊ0
                if (usesMultiList.getSelectedRow() == -1)
                {
                    if (updateMode)
                    {
                        quantitySpinner.setEnabled(false);
                        quantitySpinner.setEditable(false);
                        unitChoice.setEnabled(false);
                        removeUsageJB.setEnabled(false);

                        try
                        {
                            quantitySpinner.setCurrentDoubleValue(0.0);
                        }
                        catch (PropertyVetoException pve)
                        {
                            pve.printStackTrace();
                        }

                    }

                    viewUsageJB.setEnabled(false);
                }

            }
        }
        PartDebug.debug(
                "removeUsageJButton_actionPerformed��event�� end...return is void", this,
                PartDebug.PART_CLIENT);
    }


    /**
     * �鿴�㲿�����ԡ�
     * @param event ActionEvent
     */
    void viewUsageJButton_actionPerformed(java.awt.event.ActionEvent event)
    {
        viewSelectedItem();
    }


    /**
     * ѡ��ĳһ�㲿���󣬸ı�������ѡ�е��㲿����������֮�ı䡣
     * @param event ActionEvent
     */
    void quantitySpinner_actionPerformed(java.awt.event.ActionEvent event)
    {
        int i = usesMultiList.getSelectedRow(); //��ѡ�е���
        if (i != -1)
        {
            //��ʹ�ýṹ�б��е�����ֵ����΢�����еĵ�ǰֵ��
            usesMultiList.addTextCell(i, QTY_COLUMN,
                                      quantitySpinner.getCurrentText());
            usesMultiList.setSelectedRow(i);
        }
    }


    /**
     * �����ѡ����ʹ�ýṹ�б��е�һ���㲿�����򼤻�����΢��������λѡ��������ȥ
     * ��ť�Ͳ鿴��ť��������΢������ֵ����Ϊʹ�ýṹ�б��е�ǰ��ѡ���㲿��������
     * ֵ������λѡ������ֵѡ��Ϊʹ�ýṹ�б��е�ǰ��ѡ���㲿���ĵ�λֵ��������
     * ��΢��������λѡ��������ȥ��ť�Ͳ鿴��ť����δ����״̬��
     * @param event ItemEvent
     */
    void usesMultiList_itemStateChanged(java.awt.event.ItemEvent event)
    {
        PartDebug.debug("usesMultiList_itemStateChanged��event�� begin ...", this,
                        PartDebug.PART_CLIENT);
        int i = usesMultiList.getSelectedRow();
        if (i != -1)
        {
            if (updateMode)
            {
                quantitySpinner.setEnabled(true);
                quantitySpinner.setEditable(true);
                unitChoice.setEnabled(true);
                removeUsageJB.setEnabled(true);
            }

            viewUsageJB.setEnabled(true);

            try
            {
                Number number = UsageItem.getDecimalFormat().parse(
                        usesMultiList.getCellText(i, QTY_COLUMN));
                double d = number.doubleValue();
                //������΢������ֵ����Ϊʹ�ýṹ�б��е�ǰ��ѡ���㲿��������ֵ
                quantitySpinner.setCurrentDoubleValue(d);
                Object obj = (Object) usesMultiList.getCellText(i, UNIT_COLUMN);
                //����λѡ������ֵѡ��Ϊʹ�ýṹ�б��е�ǰ��ѡ���㲿���ĵ�λֵ
                unitChoice.setSelectedItem(obj);
                usesMultiList.setSelectedRow(i);
            }
            catch (PropertyVetoException pve)
            {
            }
            catch (java.text.ParseException pe)
            {
            }

        }
        else
        {
            quantitySpinner.setEnabled(false);
            quantitySpinner.setEditable(false);
            unitChoice.setEnabled(false);
            viewUsageJB.setEnabled(false);
            removeUsageJB.setEnabled(false);
        }
        PartDebug.debug(
                "usesMultiList_itemStateChanged��event�� end...return is void", this,
                PartDebug.PART_CLIENT);
    }


    /**
     * ����б��е�ĳһ��鿴���
     * @param event  ActionEvent
     */
    void usesMultiList_actionPerformed(java.awt.event.ActionEvent event)
    {
        viewSelectedItem();
    }


    /**
     * ѡ��ĳһ�㲿���󣬸ı䵥λ,ѡ�е��㲿���ĵ�λ��֮�ı䡣
     * @param event ItemEvent
     */
    void unitChoice_itemStateChanged(java.awt.event.ItemEvent event)
    {
        int i = usesMultiList.getSelectedRow();
        if (i != -1)
        {
            //��ʹ�ýṹ�б��еĵ�λֵ���赥λѡ�����еĵ�ǰֵ��
            String s = unitChoice.getSelectedItem().toString();
            usesMultiList.addTextCell(i, UNIT_COLUMN, s);
            usesMultiList.setSelectedRow(i);

        }
    }


    /**
     * ѡ��һ���㲿���󣬸ı�����΢������ֵ����ʹ�ýṹ�б��е�����ֵ��Ӧ�ı䡣
     * @param event PropertyChangeEvent
     */
    void quantitySpinner_propertyChange(java.beans.PropertyChangeEvent event)
    {
        int selected_row = usesMultiList.getSelectedRow();
        if (selected_row != -1)
        {
            //��ʹ�ýṹ�б��е�����ֵ����΢�����еĵ�ǰֵ��
            usesMultiList.addTextCell(selected_row, QTY_COLUMN,
                                      quantitySpinner.getCurrentText());
            usesMultiList.setSelectedRow(selected_row);
        }
    }


    /**
     * ���ֹ���������ʱ����ʹ�ýṹ�б��е�����ֵ����΢�����еĵ�ǰֵ��
     * @param e KeyEvent
     */
    void quantitySpinner_keyReleased(KeyEvent e)
    {
        int selected_row = usesMultiList.getSelectedRow();
        if (selected_row != -1)
        {
            //��ʹ�ýṹ�б��е�����ֵ����΢�����еĵ�ǰֵ��
            usesMultiList.addTextCell(selected_row, QTY_COLUMN,
                                      quantitySpinner.getCurrentText());
            usesMultiList.setSelectedRow(selected_row);
        }

    }


    /**
     * �Ƴ���ť��
     */
    public void removeButtons()
    {
        usesButtonJP.remove(addUsageJB);
        usesButtonJP.remove(removeUsageJB);
        actionJP.remove(updateUsageJP);
    }
    //CCBegin SS1
    class AddSearchJDialog extends JDialog {
        /** ��ѯ�� */
        private MyQuery cappQuery = new MyQuery();

        /** Ҫ��ѯ��ҵ����� */
        public  String SCHEMA;

        /** ��ѯ���� */
        private CappSchema mySchema;

        private GridBagLayout gridBagLayout1 = new GridBagLayout();

        /**��Ʒ�ṹ������������*/
        private JFrame managerJFrame = new JFrame();

        /** ���ڱ����Դ�ļ�·�� */
        protected  String RESOURCE = "com.faw_qm.cappclients.capprouye.util.CappRouteRB";

        /** ���Ա��� */
        private  boolean verbose = (RemoteProperty.getProperty(
                "com.faw_qm.cappclients.verbose", "true")).equals("true");
        
        Hashtable copyCodingClassifications=null;
        
        public AddSearchJDialog(JFrame frame){
        	
        	  super(frame, "", true);
              
              
              SCHEMA = "C:QMPart; G:��������;A:partNumber;A:partName;A:versionValue;A:viewName";
              //�����ѯ����
              try {
                mySchema = new CappSchema(SCHEMA);
              }
              catch (QMRemoteException ex) {
                String title = QMMessage.getLocalizedMessage(RESOURCE, "exception",
                        null);
                JOptionPane.showMessageDialog(this, ex.getClientMessage(), title,
                        JOptionPane.INFORMATION_MESSAGE);
               return ;
              }
              //���ò�ѯ����
              cappQuery.setSchema(mySchema);
//              cappQuery.notAcessInPersonalFolder();
              cappQuery.setLastIteration(true);

              
              
              setSingleSelectMode();
              
              managerJFrame = frame;
              try {
                  jbInit();
                  
                  addDefaultListener();
                  
              } catch (Exception e) {
                  e.printStackTrace();
              }

        	
        }

      

        /**
         * ��ʼ��
         *
         * @throws Exception
         */
        private void jbInit() throws Exception {
            this.setTitle("�������");
            this.setSize(650, 500);
            this.getContentPane().setLayout(gridBagLayout1);
            //���沼�ֹ���
            this.getContentPane().add(
                    cappQuery,
                    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 10, 0, 10), 0, 0));
        }

        /**
         * ���Ĭ�ϲ�ѯ����
         */
        public void addDefaultListener() {
            cappQuery.addListener(new CappQueryListener() {
                public void queryEvent(CappQueryEvent e) {
                    cappQuery_queryEvent(e);
                }
            });
        }

        /**
         * ��Ӳ�ѯ����
         *
         * @param s
         *            ��ѯ����
         */
        public void addQueryListener(CappQueryListener s) {
            cappQuery.addListener(s);
        }

        /**
         * ɾ����ѯ����
         *
         * @param s
         *            ��ѯ����
         */
        public void removeQueryListener(CappQueryListener s) {
            cappQuery.removeListener(s);
        }
  

        /**
         * ���������¼�����
         *
         * @param e
         *            ���������¼�
         */
        public void cappQuery_queryEvent(CappQueryEvent e) {
        	
        	UsesJPanel.this.qmChooser_queryEvent2(e);
          

            
        }
        
       
        

        /**
         * �����б�Ϊ��ѡģʽ
         */
        public void setSingleSelectMode() {
            try {
                cappQuery.setMultipleMode(false);
            } catch (PropertyVetoException ex) {
                ex.printStackTrace();
            }
        }

        /**
         * ���ý������ʾλ��
         */
        private void setViewLocation() {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Dimension frameSize = this.getSize();
            if (frameSize.height > screenSize.height) {
                frameSize.height = screenSize.height;
            }
            if (frameSize.width > screenSize.width) {
                frameSize.width = screenSize.width;
            }
            this.setLocation((screenSize.width - frameSize.width) / 2,
                    (screenSize.height - frameSize.height) / 2);

        }

        /**
         * ���ظ��෽����ʹ������ʾ����Ļ����
         *
         * @param flag
         */
        public void setVisible(boolean flag) {
            this.setViewLocation();
            super.setVisible(flag);
        }



        /**
         * MultiList����¼�
         * @param lis
         */
        public void addMultiListActionListener(ActionListener lis) {
            this.cappQuery.getResultList().addActionListener(lis);
        }

    }
  //CCEnd SS1
    
    
}



