package com.faw_qm.cappclients.summary.view;

import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;


import com.faw_qm.cappclients.resource.view.SortingSelectedPanel;
import com.faw_qm.cappclients.summary.controller.SummaryEQController;
import com.faw_qm.cappclients.util.CappMultiList;
import com.faw_qm.clients.util.PaginateEvent;
import com.faw_qm.clients.util.PaginateListener;
import com.faw_qm.clients.util.PaginatePanel;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.summary.model.TotalResultIfc;
import java.awt.*;
import javax.swing.*;


/**
 * <p>Title:�豸���ܽ��� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 * ���⣨1��2006.08.11 �촺Ӣ�޸� ��շ�ҳ��ʾ�Ļ�����
 * ���⣨2��2006.08.11  �촺Ӣ�޸�  ���������ɺ�multilist���кܶ���У�Ӧ�����
 * ����(3)20070625 �촺Ӣ�޸� �޸�ԭ��:���������б�û�����"���ձ��"�������
 * ����(4)2007.07.19 �촺Ӣ�޸� �޸�ԭ��:������ձ��Ϊ���ַ����򷵻�null
 * ����(5)2007.11.05 Ѧ���޸� �޸�ԭ��:���ܽ������ֵ������ȷ
 * ���⣨6��20080227 Ѧ�� �޸� �޸�ԭ�򣺹��ջ���������ʾ����
 * ���⣨7��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
 * SS1 2013-2-20   ������ ���ܽ������ӻ������� assetNumberField������  eqManuField���쳧��
 * SS2 2013-2-20   ������ �豸�嵥�������
 * SS3 ������Ļ��ܣ�Ĭ�ϲ���  2013-12-26 ���� 
 * SS4 �ɶ��豸��������豸������� guoxiaoliang 2016-8-4
 */

public class EquipPanel extends JPanel
{
    private JPanel jPanel1 = new JPanel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel jPanel2 = new JPanel();
    private JButton summaryButton = new JButton();
    private JButton interruptButton = new JButton();
    private JButton customizeButton = new JButton();
    private JPanel jPanel4 = new JPanel();
    private JLabel partLabel = new JLabel();
//  CCBegin SS2
    private JButton sbqdButton = new JButton();
    //CCEnd SS2
    //CCBegin SS3
    RequestServer server ;
    //CCEnd SS3
    private JLabel techcategoryLabel = new JLabel();
    private JLabel departLabel = new JLabel();
    private JTextField partTextField = new JTextField();
    private JButton searchButton = new JButton();
    private JCheckBox partCheckBox = new JCheckBox();
    private JCheckBox nottechCheckBox = new JCheckBox();
    private JCheckBox notequipCheckBox = new JCheckBox();
    private JCheckBox notdepartmentCheckBox = new JCheckBox();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JPanel jPanel3 = new JPanel();
    private JButton saveButton = new JButton();
    private JButton exportButton = new JButton();
    private JButton exitButton = new JButton();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private JLabel equiptypeLabel = new JLabel();
    private Vector vector;
    private SummaryEQController eqControl;
    private CappMultiList multiList = new CappMultiList();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private TotalResultIfc totalResult = null;
    private String[] attrDisplayname;
    private String part;
    private int size;
    private static ResourceBundle res =
            ResourceBundle.getBundle(
            "com.faw_qm.cappclients.summary.util.SummaryResource",
            RemoteProperty.getVersionLocale());
    private SortingSelectedPanel sortingSelectedPanel1;


    //private JLabel jLabel3 = new JLabel();
    private SortingSelectedPanel sortingSelectedPanel2;
    private SortingSelectedPanel sortingSelectedPanel3;


    //private static boolean verbose = (RemoteProperty.getProperty(
    //"com.faw_qm.summary.verbose", "true")).equals("true");
    private PaginatePanel paginatePanel1 = new PaginatePanel();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();
    private JLabel jLabel1 = new JLabel();


    //2007.04.03
    private JLabel numberLabel = new JLabel();


    //2007.04.03 �촺Ӣ���
    private JTextField technicField = new JTextField();

    //���⣨7��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
    JCheckBox notTechnicsSelected = new JCheckBox();
    JButton searchTechnics = new JButton();

    //CCBegin SS1
    private JLabel assetNumberLabel = new JLabel();
    private JLabel eqManuLabel = new JLabel();

    private JTextField assetNumberField = new JTextField();
    private JTextField eqManuField = new JTextField();
    //CCEnd SS1
    
    //CCBegin SS4
    
    private JLabel eqNum = new JLabel();
    private JTextField eqNumField = new JTextField();
    
    
    //CCEnd SS4
    
    /**
     * ���������Ĺ��캯��
     */
    public EquipPanel()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * ��һ�������Ĺ��캯��
     * @param control ActionListener
     */
    public EquipPanel(ActionListener control)
    {
        super();
        try
        {
            this.eqControl = (SummaryEQController) control;
            jbInit();
            localize();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * ��ʼ������
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        //sortingSelectedPanel1 = new SortingSelectedPanel("�豸����", "�豸");
        //�豸����
        String equipType = res.getString("equipment_type");
        sortingSelectedPanel1 = new SortingSelectedPanel(equipType
                , "QMEquipment", "eqType");
        sortingSelectedPanel1.setIsSelectCC(true);
        sortingSelectedPanel1.setDialogTitle(equipType);
        //sortingSelectedPanel2 = new SortingSelectedPanel("��֯����", "�������");
        //����
        String department = res.getString("department");
       //CCBegin SS3
        String temCom = getUserFromCompany();
        if(temCom.equals("zczx")){
            sortingSelectedPanel2 = new SortingSelectedPanel("�������","��֯����");
        }else{
            sortingSelectedPanel2 = new SortingSelectedPanel(department
                    , "QMProcedure", "workShop"); 	
        }
        //CCEnd SS3
        sortingSelectedPanel2.setDialogTitle(department);
        sortingSelectedPanel2.setIsSelectCC(true);
        String technicsType = res.getString("technics_category");
        //sortingSelectedPanel3 = new SortingSelectedPanel("��������", "��������Ϣ");
        sortingSelectedPanel3 = new SortingSelectedPanel(technicsType
                , "QMTechnicsMaster", "technicsType");
        sortingSelectedPanel3.setDialogTitle(technicsType);
        sortingSelectedPanel3.setIsSelectCC(true);
        this.setLayout(gridBagLayout5);
        jPanel1.setLayout(gridBagLayout2);
        summaryButton.setMaximumSize(new Dimension(114, 23));
        summaryButton.setMinimumSize(new Dimension(114, 23));
        summaryButton.setPreferredSize(new Dimension(114, 23));

        interruptButton.setMaximumSize(new Dimension(114, 23));
        interruptButton.setMinimumSize(new Dimension(114, 23));
        interruptButton.setPreferredSize(new Dimension(114, 23));
        interruptButton.setEnabled(false);

        customizeButton.setMaximumSize(new Dimension(114, 23));
        customizeButton.setMinimumSize(new Dimension(114, 23));
        customizeButton.setPreferredSize(new Dimension(114, 23));
        customizeButton.setHorizontalAlignment(SwingConstants.LEFT);
        customizeButton.setHorizontalTextPosition(SwingConstants.LEFT);
        customizeButton.setMnemonic('U');
        //CCBegin SS2
        sbqdButton.setMaximumSize(new Dimension(114, 23));
        sbqdButton.setMinimumSize(new Dimension(114, 23));
        sbqdButton.setPreferredSize(new Dimension(114, 23));
        sbqdButton.setHorizontalAlignment(SwingConstants.LEFT);
        sbqdButton.setHorizontalTextPosition(SwingConstants.LEFT);
        sbqdButton.setMnemonic('B');
        
        //CCEnd SS2
        jPanel2.setLayout(gridBagLayout1);
        jPanel4.setLayout(gridBagLayout3);

        searchButton.setMaximumSize(new Dimension(80, 23));
        searchButton.setMinimumSize(new Dimension(80, 23));
        searchButton.setPreferredSize(new Dimension(80, 23));
        jPanel3.setLayout(gridBagLayout4);

        saveButton.setMaximumSize(new Dimension(97, 23));
        saveButton.setMinimumSize(new Dimension(97, 23));
        saveButton.setPreferredSize(new Dimension(97, 23));
        saveButton.setEnabled(false);

        exportButton.setMaximumSize(new Dimension(97, 23));
        exportButton.setMinimumSize(new Dimension(97, 23));
        exportButton.setPreferredSize(new Dimension(97, 23));
        exportButton.setEnabled(false);

        exitButton.setMaximumSize(new Dimension(97, 23));
        exitButton.setMinimumSize(new Dimension(97, 23));
        exitButton.setPreferredSize(new Dimension(97, 23));

        customizeButton.addActionListener(eqControl);
        //CCBegin SS2
        sbqdButton.addActionListener(eqControl);
        sbqdButton.setActionCommand("SBQD");
//      CCEnd SS2
        exitButton.addActionListener(eqControl);
        summaryButton.addActionListener(eqControl);
        interruptButton.addActionListener(eqControl);
        searchButton.addActionListener(eqControl);
        saveButton.addActionListener(eqControl);
        exportButton.addActionListener(eqControl);
        searchButton.setActionCommand("SEARCH");
        searchButton.setMnemonic('S');
        customizeButton.setActionCommand("CUSTO");
        exitButton.setActionCommand("EXIT");
        exitButton.setMnemonic('X');
        summaryButton.setActionCommand("SUM");
        summaryButton.setMargin(new Insets(2, 14, 2, 14));
        summaryButton.setMnemonic('T');
        saveButton.setActionCommand("SAVE");
        saveButton.setMnemonic('V');
        interruptButton.setActionCommand("INTERRUPT");
        interruptButton.setMnemonic('B');
        exportButton.setActionCommand("EXPORT");
        exportButton.setMnemonic('R');
        sortingSelectedPanel1.setButtonSize(80, 23);
        sortingSelectedPanel2.setButtonSize(80, 23);
        sortingSelectedPanel3.setButtonSize(80, 23);
        sortingSelectedPanel3.setButtonName("���. . .");
        sortingSelectedPanel2.setButtonName("���. . .");
        sortingSelectedPanel1.setButtonName("���. . .");
        //2007.04.03
        technicField.setMaximumSize(new Dimension(80, 22));
        technicField.setMinimumSize(new Dimension(80, 22));
        technicField.setPreferredSize(new Dimension(80, 22));

        //jLabel3.setBorder(BorderFactory.createLoweredBevelBorder());
        paginatePanel1.addListener(new PaginateListener()
        {
            public void paginateEvent(PaginateEvent e)
            {
                paginatePanel_paginateEvent(e);
            }
        });
        //���⣨7��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
        notTechnicsSelected.setText(res.getString("not"));
        searchTechnics.setActionCommand("SearchTechnics");
        searchTechnics.setMaximumSize(new Dimension(80, 23));
        searchTechnics.setMinimumSize(new Dimension(80, 23));
        searchTechnics.setPreferredSize(new Dimension(80, 23));
        searchTechnics.setText(res.getString("search2"));
        searchTechnics.addActionListener(eqControl);
        //���⣨7������
        this.add(jPanel1, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(8, 0, 0, 0), 0, 0));
        //CCBegin SS2
     
        jPanel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 4, 1.0, 0.0
                , GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 10, 0, 0), 0, 0));
        //CCEnd SS2
  
        jTabbedPane1.add(jPanel2, res.getString("summary_condition"));
        jPanel2.add(partLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST,
                GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(techcategoryLabel,
                    new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.EAST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(equiptypeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(departLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(4, 0, 4, 0), 0, 0));
        //2007.04.03
        jPanel2.add(numberLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(4, 0, 4, 0), 0, 0));

        jPanel2.add(searchButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(nottechCheckBox,
                    new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(notequipCheckBox,
                    new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(notdepartmentCheckBox,
                    new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(partCheckBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(sortingSelectedPanel1,
                    new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(partTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(sortingSelectedPanel2,
                    new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
         //CCBegin SS1
        jPanel2.add(assetNumberLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(4, 0, 4, 0), 0, 0));
        jPanel2.add(eqManuLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE,
        new Insets(4, 0, 4, 0), 0, 0));
        jPanel2.add(assetNumberField,
                    new GridBagConstraints(1, 5, 2, 1, 1.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 8, 0, 0), 0, 0));
        jPanel2.add(eqManuField,
                    new GridBagConstraints(1, 6, 2, 1, 1.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 8, 0, 0), 0, 0));

        //CCEnd SS1
        
        //CCBegin SS4
        
        jPanel2.add(eqNum, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(4, 0, 4, 0), 0, 0));
        
        jPanel2.add(eqNumField,
                new GridBagConstraints(1, 7, 2, 1, 1.0, 0.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(0, 8, 0, 0), 0, 0));

        //CCEnd SS4
        
        
        //���⣨7��Ѧ�� 20080414  �޸�ԭ��:���ձ����������ť�ͷǸ�ѡ��
        //2007.04.03
        jPanel2.add(technicField,
                    new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 8, 0, 0), 0, 0));
        //���⣨7���޸Ľ���
        jPanel2.add(sortingSelectedPanel3,
                    new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
        //���⣨7��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
        jPanel2.add(notTechnicsSelected,
                    new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(searchTechnics, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        //���⣨7������
        jPanel1.add(summaryButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                new Insets(24, 8, 0, 10), 0, 0));
        jPanel1.add(interruptButton,
                    new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.NORTHWEST,
                                           GridBagConstraints.NONE,
                                           new Insets(8, 8, 0, 10), 0, 0));
        jPanel1.add(customizeButton,
                    new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.NORTHWEST,
                                           GridBagConstraints.NONE,
                                           new Insets(8, 8, 0, 10), 0, 0));
//      CCBegin SS2
        jPanel1.add(sbqdButton,
                new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.NORTHWEST,
                                       GridBagConstraints.NONE,
                                       new Insets(8, 8, 0, 10), 0, 0));
//      CCEnd SS2
        this.add(jPanel4, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(10, 10, 0, 10), 0,
                                                 0));
        jPanel4.add(multiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0,
                180));
        jPanel3.add(exitButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel3.add(exportButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel3.add(saveButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel3.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        this.add(paginatePanel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 10, 0, 8), 0, 0));
        //this.add(jLabel3, new GridBagConstraints(0, 3, 2, 1, 1.0, 0.0
        //   , GridBagConstraints.WEST,
        // GridBagConstraints.HORIZONTAL,
        //  new Insets(0, 0, 2, 0), 0, 0));
        this.add(jPanel3, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.EAST,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(8, 0, 8, 10), 0, 0));
        multiList.setHeadings(eqControl.getSchemaheadings());
        this.setAveRow();
        multiList.setCellEditable(false);

    }


    /**
     * ���ػ�
     */
    public void localize()
    {
        summaryButton.setText(res.getString("summary"));
        interruptButton.setText(res.getString("interrupt"));
        customizeButton.setText(res.getString("export_customize"));
        partLabel.setText(res.getString("part_number"));
        techcategoryLabel.setText(res.getString("technics_category"));
        departLabel.setText(res.getString("department"));
        //2007.04.03
        numberLabel.setText("���ձ��");
         //CCBegin SS1
        assetNumberLabel.setText("������");
        eqManuLabel.setText("���쳧��");
        //CCEnd SS1
        
        //CCBegin SS4
        eqNum.setText("�豸���");
        //CCEnd SS4
        
//      CCBegin SS2
        sbqdButton.setText("�豸�嵥(B)...");
//      CCEnd SS2
        searchButton.setText(res.getString("search2"));
        partCheckBox.setText(res.getString("structure"));
        nottechCheckBox.setText(res.getString("not"));
        notequipCheckBox.setText(res.getString("not"));
        notdepartmentCheckBox.setText(res.getString("not"));
        saveButton.setText(res.getString("save"));
        exportButton.setText(res.getString("export"));
        exitButton.setText(res.getString("exit"));
        equiptypeLabel.setText(res.getString("equipment_type"));
    }


    /**
     * ��ҳ����ʵ��
     * @param e PaginateEvent
     */
    void paginatePanel_paginateEvent(PaginateEvent e)
    {
        Vector tempVector = paginatePanel1.getCurrentObjects();
        this.addMultiList(tempVector);
    }


    /**
     * �����㲿���ŵ�ֵ
     * @return String
     */
    public String getPartNum()
    {
        part = partTextField.getText().trim();
        if (part.equals(""))
        {
            return null;
        }
        else
        {
            return part;
        }
    }


    /**
     * �ṹ��ѡ���Ƿ�ѡȡ
     * @return boolean
     */
    public boolean isSelectPart()
    {
        return partCheckBox.isSelected();
    }


    /**
     * �ǹ������ิѡ���Ƿ�ѡȡ
     * @return boolean
     */
    public boolean isSelectTech()
    {
        return nottechCheckBox.isSelected();
    }


    /**
     * ���⣨7��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
     * �ǹ��ձ�Ÿ�ѡ���Ƿ�ѡȡ
     * @return boolean
     */
    public boolean isSelectTechnicsBso()
    {
        return notTechnicsSelected.isSelected();
    }


    /**
     * ���豸���ิѡ���Ƿ�ѡȡ
     * @return boolean
     */
    public boolean isSelectEquipment()
    {
        return notequipCheckBox.isSelected();
    }


    /**
     * �ǲ��Ÿ�ѡ���Ƿ�ѡȡ
     * @return boolean
     */
    public boolean isSelectDepartment()
    {
        return notdepartmentCheckBox.isSelected();
    }


    /**
     * �趨������Ϣ
     * @return String
     */
    public String getSumInfo()
    {
        return size + res.getString("have been found");
    }


    /**
     * ���û��ܽ��ֵ����
     * @param totalResult TotalResultIfc
     */
    public void setTotalResult(TotalResultIfc totalResult)
    {
        this.totalResult = totalResult;
    }


    /**
     * �ѻ��ܽ��ת��Ϊ����
     * ���⣨1��2006.08.11 �촺Ӣ�޸� ��շ�ҳ��ʾ�Ļ�����
     * ���⣨5��2007.11.05 Ѧ���޸� ���ܽ������ֵ������ȷ
     */
    public void setToMultilist()
    {
        if (totalResult == null)
        {
            return;
        }
        Collection col = totalResult.getContent().getContent();
        size = col.size();
        this.multiList.clear();
        vector = new Vector();
        Vector vec = new Vector();
        if (size > 0)
        {
            this.setUseState(true);
            vector.add(0, totalResult.getAttrDisplayList());
            Object[] results = col.toArray();
            for (int i = 0; i < results.length; i++)
            {
                //���⣨5��2007.11.05 Ѧ���޸� ���ܽ������ֵ������ȷ
                Object[] objs1 = (Object[]) results[i];
                //String[] strs = new String[objs1.length];
                //for (int j = 0; j < objs1.length; j++)
                //{
                //    if (objs1[j] != null)
                //    {
                //        strs[j] = objs1[j].toString();
                //    }
                //    else
                //    {
                //       strs[j] = "";
                //    }
                //}
                //vec.add(i, strs);
                vec.add(i, objs1);
                //vector.add(i + 1, strs);
                vector.add(i + 1, objs1);
            }
            //���⣨1��2006.08.11 �촺Ӣ�޸� ��շ�ҳ��ʾ�Ļ�����
            paginatePanel1.clearResultCache();
            vec = paginatePanel1.paginate(vec);
            this.addMultiList(vec);

        }
        else
        {
            this.setUseState(false);
            paginatePanel1.paginate(null);
        }
    }


    /**
     * �����ܽ��������������
     * @return Vector
     */
    public Vector exportResult()
    {
        return vector;
    }


    /**
     * ���û���������ʾ��
     * @param s String[]
     */
    public void setAttributes(String[] s)
    {
        this.attrDisplayname = s;
    }


    /**
     * �õ��豸����
     * @return BaseValueIfc
     */
    public BaseValueIfc getEquipType()
    {
        if (sortingSelectedPanel1.getCoding() != null)
        {
            return sortingSelectedPanel1.getCoding();
        }
        else
        {
            return null;
        }
    }


    /**
     * �õ�������
     * @return BaseValueIfc
     */
    public BaseValueIfc getProcedureDepart()
    {
        if (sortingSelectedPanel2.getCoding() != null)
        {
            return sortingSelectedPanel2.getCoding();
        }
        else
        {
            return null;
        }
    }


    /**
     * �õ���������
     * @return String
     */
    public String getTechType()
    {
        if (sortingSelectedPanel3.getCoding() != null)
        {
            return sortingSelectedPanel3.getCoding().getBsoID();
        }
        else
        {
            return null;
        }
    }


    /**
     * �����㲿������Ϣ
     * @param info QMPartInfo
     */
    public void setPart(QMPartInfo info)
    {
    	//Ѧ�� 20081014 �޸�
    	String value = partTextField.getText();
    	if(value.length()>0)
    	{
    		partTextField.setText(value + ";" + info.getPartNumber()); 
    	}
    	else
    	{
    		partTextField.setText(info.getPartNumber());
    	}
    	//Ѧ�� 20081014 �޸Ľ���
    }


    /**
     * �õ���������
     * @return String
     * ����(3)20070625 �촺Ӣ�޸� �޸�ԭ��:���������б�û�����"���ձ��"�������
     * ���⣨6��20080227 Ѧ�� �޸� �޸�ԭ�򣺹��ջ���������ʾ����
     */
    public String getSumCondition()
    {
        StringBuffer buffer = new StringBuffer();
        if (getPartNum() != null)
        {
            String partNum = res.getString("part_number");
            buffer.append(partNum).append(":").append(getPartNum());
        }
        BaseValueIfc coding = sortingSelectedPanel3.getCoding();
        if (coding != null)
        {
            String technics = res.getString("technics_category"); //��������
            buffer.append(";").append(technics).append(":").append(
                    coding.toString());
            //Ѧ�� 20080227 ��� ���ԭ�򣺹��ջ���������ʾ����
            if (isSelectTech() == true)
            {
                buffer.append("(��)");
            }
            //Ѧ����ӽ���
        }
        coding = sortingSelectedPanel2.getCoding();
        if (coding != null)
        {
            String equipment = res.getString("department");
            buffer.append(";").append(equipment).append(":").append(
                    coding.toString());
            //Ѧ�� 20080227 ��� ���ԭ�򣺹��ջ���������ʾ����
            if (isSelectDepartment()) //isSelectDepartment()
            {
                buffer.append("(��)");
            }
            //Ѧ����ӽ���
        }
        coding = sortingSelectedPanel1.getCoding();
        if (coding != null)
        {
            String department = res.getString("equipment_type");
            buffer.append(";").append(department).append(":").append(
                    coding.toString());
            //Ѧ�� 20080227 ��� ���ԭ�򣺹��ջ���������ʾ����
            if (isSelectEquipment())
            {
                buffer.append("(��)");
            }
            //Ѧ����ӽ���
        }
        //����(3)20070625 �촺Ӣ�޸� �޸�ԭ��:���������б�û�����"���ձ��"�������
        if (getTechNum() != null)
        {
            String technicNum = res.getString("technicNum");
            buffer.append(";").append(technicNum).append(":").append(getTechNum());
            //���⣨7��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
            if (this.isSelectTechnicsBso())
            {
                buffer.append("(��)");
            }
            //���⣨7������
        }

        return buffer.toString();
    }


    /**
     * ���»��ܱ�ͷ
     */
    public void updateHeadings()
    {
        multiList.updateHeadings(attrDisplayname);
    }


    /**
     *  ���÷�ҳ��ҳ��Ϊ
     */
    public void newPaginatePanel()
    {
        paginatePanel1.paginate(null);
    }


    /**
     * ʹ��ͷ���ȷֲ�
     * ���⣨2��2006.08.11  �촺Ӣ�޸�  ���������ɺ�multilist���кܶ���У�Ӧ�����
     */
    public void setMultilistRow()
    {
        int a = eqControl.getAttrCount();
        if (a > 10)
        {
            a = 10;
        }
        int[] aa = new int[a];
        for (int s = 0; s < a; s++)
        {
            aa[s] = 1;
        }
        this.multiList.setRelColWidth(aa);
        //���⣨2��2006.08.11  �촺Ӣ�޸�  ���������ɺ�multilist���кܶ���У�Ӧ�����
        this.multiList.clear();
    }


    /**
     * ʹĬ�ϱ�ͷ���ȷֲ�
     */
    public void setAveRow()
    {
        int a = eqControl.getHeadsCount();
        int[] aa = new int[a];
        for (int s = 0; s < a; s++)
        {
            aa[s] = 1;
        }
        this.multiList.setRelColWidth(aa);
    }


    /**
     * ���õ�����ť�ͱ����״̬
     */
    private void setUseState(boolean flag)
    {
        saveButton.setEnabled(flag);
        exportButton.setEnabled(flag);
    }


    /**
     * ���û��ܰ�ť��״̬
     */
    public void setSaveButtonstate(boolean flag)
    {
        summaryButton.setEnabled(flag);
        interruptButton.setEnabled(!flag);
    }


    /**
     * �����ܽ�����뵽�����
     * @param v Vector
     * ���⣨5��2007.11.05 Ѧ���޸� ���ܽ������ֵ������ȷ
     */
    public void addMultiList(Vector v)
    {
        int i = 0;
        this.multiList.clear();
        for (Iterator iterator = v.iterator(); iterator.hasNext(); )
        {
            //String[] objs = (String[]) iterator.next();
            Object[] objs = (Object[]) iterator.next();
            for (int j = 0; j < objs.length; j++)
            {
                //���⣨5��2007.11.05 Ѧ���޸� ���ܽ������ֵ������ȷ
                //if (objs[j] != null && !objs[j].toString().equals(""))
                if (objs[j] != null)
                {
                    //this.multiList.addTextCell(i, j, objs[j].toString());
                    this.multiList.addNumberTextCell(i, j, objs[j]);
                    //�޸Ľ���
                }
                else
                {
                    this.multiList.addTextCell(i, j, "");
                }
            }
            i++;
        }
    }


    //����(4)2007.07.19 �촺Ӣ�޸� �޸�ԭ��:������ձ��Ϊ���ַ����򷵻�null
    public String getTechNum()
    {
        String techNum = technicField.getText().trim();
        if (techNum.equals(""))
        {
            return null;
        }
        else
        {
            return techNum;
        }
    }

    public CappMultiList getMultilist()
    {
        return multiList;
    }


    //���⣨7��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
    public void setTechniceNumber(String technicsnumber)
    {
    	//Ѧ�� 20081014 �޸�
    	String s = technicField.getText();
    	if(s.length()>0)
    	{
    		technicField.setText(s + ";" + technicsnumber);
    	}
    	else
    	{
    		technicField.setText(technicsnumber);
    	}
    	//Ѧ�� 20081014 �޸Ľ���
    }
    //���⣨7������
    //CCBegin SS2
    /**
     * ���ð�ť״̬
     */
    public void setButton1State() {
        this.sbqdButton.setEnabled(false);
      }
    /**
     * chudaming add 2009.4.16  ��ȡ���ܵ����豸�嵥 1������ 0
     * @return int
     */
    public int getSummaryMode() {
      //�豸�嵥
      if (sbqdButton.isEnabled() == false) {
        return 1;
      }
      //����
      return 0;
    }
//  CCEnd SS2
    
    //CCBegin SS4
    public String getEqNum()
    {
        String eqNum = eqNumField.getText().trim();
        if (eqNum.equals(""))
        {
            return null;
        }
        else
        {
            return eqNum;
        }
    }
    
    //CCEnd SS4
    
    
    //CCBegin SS1
    public String getAssetNumber()
   {
       String assetNumber = assetNumberField.getText().trim();
       if (assetNumber.equals(""))
       {
           return null;
       }
       else
       {
           return assetNumber;
       }
   }
   //�õ����쳧��0928
   public String getEqManu()
   {
       String eqManu = eqManuField.getText().trim();
       if (eqManu.equals(""))
       {
           return null;
       }
       else
       {
           return eqManu;
       }
   }
   public void clearMultiList() {
	      this.multiList.clear();
	    }
   //CCEnd SS1
  //CCBegin SS3
    public String getUserFromCompany() throws QMException
    {
    	String returnStr = "";
    	 if (server == null)
         {
             server = RequestServerFactory.getRequestServer();
         }
         StaticMethodRequestInfo info = new StaticMethodRequestInfo();
         info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
         info.setMethodName("getUserFromCompany");
         Class[] paraClass ={};
         info.setParaClasses(paraClass);
         Object[] obj ={};
         info.setParaValues(obj);
         boolean flag = false;
         try
         {
        	 returnStr = ((String) server.request(info));
         }
         catch (QMRemoteException e)
         {
               throw new QMException(e);
         }
         return returnStr;
    }
//CCEnd SS3  
    
}
