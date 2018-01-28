package com.faw_qm.cappclients.summary.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.faw_qm.cappclients.resource.view.SortingSelectedPanel;
import com.faw_qm.cappclients.summary.controller.SummaryMTController;
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


/**
 * <p>Title:���ϻ��ܽ��� </p>
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
* SS1 2013-2-20   ������ ���ܽ������ӻ������� assetNumberField������ materialFunctionField����״̬ materialNameField��������
 * SS2 ������Ļ��ܣ�Ĭ�ϲ���  2013-12-26 ���� 
 */

public class MTPanel extends JPanel
{
    private JPanel jPanel1 = new JPanel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel jPanel2 = new JPanel();
    private JButton summaryButton = new JButton();
    private JButton interruptButton = new JButton();
    private JButton customizeButton = new JButton();
    private JPanel jPanel4 = new JPanel();
    private JLabel partLabel = new JLabel();
    private JLabel techcategoryLabel = new JLabel();
    private JLabel departLabel = new JLabel();
    private JTextField partTextField = new JTextField();
    private JButton searchButton = new JButton();
    private JCheckBox partCheckBox = new JCheckBox();
    private JCheckBox nottechCheckBox = new JCheckBox();
    private JCheckBox notmtCheckBox = new JCheckBox();
    private JCheckBox notdepartmentCheckBox = new JCheckBox();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JPanel jPanel3 = new JPanel();
    private JButton saveButton = new JButton();
    private JButton exportButton = new JButton();
    private JButton exitButton = new JButton();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private JLabel mtcategoryLabel = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private SummaryMTController mtControl;
    private CappMultiList multiList = new CappMultiList();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private TotalResultIfc totalResult;
    private String[] attrDisplayname;
    private Vector vector;
    private String part;
    private int size;
    private static ResourceBundle res =
            ResourceBundle.getBundle(
            "com.faw_qm.cappclients.summary.util.SummaryResource",
            RemoteProperty.getVersionLocale());
    private SortingSelectedPanel sortingSelectedPanel2;
    private GridBagLayout gridBagLayout5 = new GridBagLayout();
    private SortingSelectedPanel sortingSelectedPanel1;
    private SortingSelectedPanel sortingSelectedPanel3;
    private PaginatePanel paginatePanel1 = new PaginatePanel();

    //2007.04.06
    private JLabel numberLabel = new JLabel();
  //CCBegin SS1
     private JTextField materialNameField = new JTextField();
     private JTextField materialFunctionField = new JTextField();
     private JTextField assetNumberField = new JTextField();
     private JLabel materialFunctionLabel = new JLabel();
     private JLabel materialNameLabel = new JLabel();
     private JLabel assetNumberLabel = new JLabel();
     //CCEnd SS1
    //2007.04.06 �촺Ӣ���
    private JTextField technicField = new JTextField();

    //���⣨7��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
    JCheckBox notTechnicsSelected = new JCheckBox();
    JButton searchTechnics = new JButton();


    /**
     * ���������Ĺ��캯��
     */
    public MTPanel()
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
    public MTPanel(ActionListener control)
    {
        super();
        try
        {
            this.mtControl = (SummaryMTController) control;
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
        String materialType = res.getString("material_type");
        sortingSelectedPanel2 = new SortingSelectedPanel(materialType,
                "QMMaterial", "materialCf");
        //sortingSelectedPanel2 = new SortingSelectedPanel("���Ϸ���", "����");
        sortingSelectedPanel2.setDialogTitle(materialType);
        sortingSelectedPanel2.setIsSelectCC(true);
        String department = res.getString("department");
       //CCBegin SS2
        String temCom = getUserFromCompany();
        if(temCom.equals("zczx")){
        	sortingSelectedPanel3 = new SortingSelectedPanel("�������","��֯����");
        	sortingSelectedPanel3.setIsSelectCC(true);
        	sortingSelectedPanel3.setDialogTitle(department);
        }else{
        	 sortingSelectedPanel3 = new SortingSelectedPanel(department
                     , "QMProcedure", "workShop");
             //sortingSelectedPanel3 = new SortingSelectedPanel("��֯����", "�������");
             sortingSelectedPanel3.setIsSelectCC(true);
             sortingSelectedPanel3.setDialogTitle(department);
        }

        //CCEnd SS2
        String technicsType = res.getString("technics_category");
        sortingSelectedPanel1 = new SortingSelectedPanel(technicsType
                , "QMTechnicsMaster", "technicsType");
        //sortingSelectedPanel1 = new SortingSelectedPanel("��������", "��������Ϣ");
        sortingSelectedPanel1.setIsSelectCC(true);
        sortingSelectedPanel1.setDialogTitle(technicsType);
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

        sortingSelectedPanel1.setButtonSize(80, 23);
        sortingSelectedPanel2.setButtonSize(80, 23);
        sortingSelectedPanel3.setButtonSize(80, 23);
        sortingSelectedPanel3.setButtonName("���. . .");
        sortingSelectedPanel2.setButtonName("���. . .");
        sortingSelectedPanel1.setButtonName("���. . .");

        //2007.04.06
        technicField.setMaximumSize(new Dimension(80, 22));
        technicField.setMinimumSize(new Dimension(80, 22));
        technicField.setPreferredSize(new Dimension(80, 22));

        //���⣨7��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
        notTechnicsSelected.setText(res.getString("not"));
        searchTechnics.setActionCommand("SearchTechnics");
        searchTechnics.setMaximumSize(new Dimension(80, 23));
        searchTechnics.setMinimumSize(new Dimension(80, 23));
        searchTechnics.setPreferredSize(new Dimension(80, 23));
        searchTechnics.setText(res.getString("search2"));
        searchTechnics.addActionListener(mtControl);
        //���⣨7������


        customizeButton.addActionListener(mtControl);
        exitButton.addActionListener(mtControl);
        summaryButton.addActionListener(mtControl);
        interruptButton.addActionListener(mtControl);
        searchButton.addActionListener(mtControl);
        saveButton.addActionListener(mtControl);
        exportButton.addActionListener(mtControl);
        searchButton.setActionCommand("SEARCH");
        customizeButton.setActionCommand("CUSTO");
        exitButton.setActionCommand("EXIT");
        exitButton.setMnemonic('X');
        summaryButton.setActionCommand("SUM");
        summaryButton.setMnemonic('T');
        interruptButton.setActionCommand("INTERRUPT");
        interruptButton.setMnemonic('B');
        saveButton.setActionCommand("SAVE");
        saveButton.setMnemonic('V');
        exportButton.setActionCommand("EXPORT");
        exportButton.setMnemonic('R');
        paginatePanel1.addListener(new PaginateListener()
        {
            public void paginateEvent(PaginateEvent e)
            {
                paginatePanel_paginateEvent(e);
            }
        });
        this.add(jPanel1, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(8, 0, 0, 0), 0, 0));
        jPanel2.add(partTextField, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(partLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST,
                GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(techcategoryLabel,
                    new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.EAST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(mtcategoryLabel,
                    new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.EAST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(departLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(4, 0, 4, 0), 0, 0));
        //2007.04.06
        jPanel2.add(numberLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(4, 0, 4, 0), 0, 0));

        jPanel2.add(searchButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        //CCBegin SS1
       jPanel2.add(assetNumberLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
       , GridBagConstraints.EAST, GridBagConstraints.NONE,
       new Insets(4, 0, 4, 0), 0, 0));
       jPanel2.add(materialNameLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
       , GridBagConstraints.EAST, GridBagConstraints.NONE,
       new Insets(4, 0, 4, 0), 0, 0));
       jPanel2.add(materialFunctionLabel, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
      , GridBagConstraints.EAST, GridBagConstraints.NONE,
       new Insets(4, 0, 4, 0), 0, 0));

        jPanel2.add(assetNumberField,
                            new GridBagConstraints(1, 5, 2, 1, 1.0, 0.0
                                                   , GridBagConstraints.CENTER,
                                                   GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 8, 0, 0), 0, 0));

        jPanel2.add(materialNameField,
                    new GridBagConstraints(1, 6, 2, 1, 1.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 8, 0, 0), 0, 0));
        jPanel2.add(materialFunctionField,
                    new GridBagConstraints(1, 7, 2, 1, 1.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 8, 0, 0), 0, 0));


       //CCEnd SS1
        jPanel2.add(nottechCheckBox,
                    new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(notmtCheckBox, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(notdepartmentCheckBox,
                    new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.NONE,
                                           new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(partCheckBox, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(sortingSelectedPanel2,
                    new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(sortingSelectedPanel1,
                    new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(sortingSelectedPanel3,
                    new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
        //���⣨7��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
        //2007.04.06
        jPanel2.add(technicField,
                    new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 8, 0, 0), 0, 0));

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
                                           new Insets(10, 8, 0, 10), 0, 0));
        jPanel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 3, 1.0, 0.0
                , GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 10, 0, 0), 0, 0));
        jTabbedPane1.add(jPanel2, res.getString("summary_condition"));
        this.add(jPanel4, new GridBagConstraints(0, 1, 2, 1, 1.0, 1.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.BOTH,
                                                 new Insets(10, 10, 0, 10), 0,
                                                 0));
        jPanel4.add(multiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        this.add(jPanel3, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(8, 0, 8, 10), 0, 0));
        jPanel3.add(exitButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel3.add(exportButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0), 0, 0));
        jPanel3.add(saveButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 8, 0, 0), 0, 0));
        jPanel3.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        this.add(paginatePanel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 10, 0, 8), 0, 0));
        this.multiList.setHeadings(mtControl.getSchemaheadings());
        this.setAveRow();
        multiList.setCellEditable(false);
    }

  //CCBegin SS2
    /**
     * �����û�������˾
     * @return Sting �û�������˾
     * @author wenl
     */
    public String getUserFromCompany() throws QMException
    {
    	String returnStr = "";
    	RequestServer server = RequestServerFactory.getRequestServer();
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
    //CCEnd SS2
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
        //2007.04.06
        numberLabel.setText("���ձ��");
        //CCBegin SS1
        assetNumberLabel.setText("������");
        materialNameLabel.setText("��������");
        materialFunctionLabel.setText("������;");
        //CCEnd SS1
        searchButton.setText(res.getString("search2"));
        partCheckBox.setText(res.getString("structure"));
        nottechCheckBox.setText(res.getString("not"));
        notmtCheckBox.setText(res.getString("not"));
        notdepartmentCheckBox.setText(res.getString("not"));
        saveButton.setText(res.getString("save"));
        exportButton.setText(res.getString("export"));
        exitButton.setText(res.getString("exit"));
        mtcategoryLabel.setText(res.getString("material_type"));
    }


    /**
     * paginatePanel�ļ���ʵ��
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
     * �õ�������
     * @return BaseValueIfc
     */
    public BaseValueIfc getProcedureDepart()
    {
        if (sortingSelectedPanel3.getCoding() != null)
        {
            return sortingSelectedPanel3.getCoding();
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
        if (sortingSelectedPanel1.getCoding() != null)
        {
            return sortingSelectedPanel1.getCoding().getBsoID();
        }
        else
        {
            return null;
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
     * �ǲ������ิѡ���Ƿ�ѡȡ
     * @return boolean
     */
    public boolean isSelectEquipment()
    {
        return notmtCheckBox.isSelected();
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
    } ///////////////////


    /**
     * ���û��ܽ��ֵ����
     * @param totalResult TotalResultIfc
     */
    public void setTotalResult(TotalResultIfc totalResult)
    {
        this.totalResult = totalResult;
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
               // String[] strs = new String[objs1.length];
               // for (int j = 0; j < objs1.length; j++)
               // {
                //    if (objs1[j] != null)
               //     {
               //         strs[j] = objs1[j].toString();
                 //   }
                 //   else
                  //  {
                  //      strs[j] = "";
                  //  }
                //}
                //vec.add(i, strs);
                //vector.add(i + 1, strs);
                vec.add(i, objs1);
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
     * ���û���������ʾ��
     * @param s String[]
     */
    public void setAttributes(String[] s)
    {
        this.attrDisplayname = s;
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
     * @return BaseValueIfc
     */
    public BaseValueIfc getMtpType()
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
        if (sortingSelectedPanel1.getCoding() != null)
        {
            String technics = res.getString("technics_category");
            buffer.append(";").append(technics).append(":").append(
                    sortingSelectedPanel1.getCoding().toString());
            //Ѧ�� 20080227 ��� ���ԭ�򣺹��ջ���������ʾ����
            if(isSelectTech()==true)
            {
                buffer.append("(��)");
            }
            //Ѧ����ӽ���
        }
        if (sortingSelectedPanel2.getCoding() != null)
        {
            String material = res.getString("material_type");
            buffer.append(";").append(material).append(":").append(
                    sortingSelectedPanel2.getCoding().toString());
            //Ѧ�� 20080227 ��� ���ԭ�򣺹��ջ���������ʾ����
            if(isSelectEquipment()==true)
            {
                buffer.append("(��)");
            }
            //Ѧ����ӽ���
        }
        if (sortingSelectedPanel3.getCoding() != null)
        {
            String department = res.getString("department");
            buffer.append(";").append(department).append(":").append(
                    sortingSelectedPanel3.getCoding().toString());
            //Ѧ�� 20080227 ��� ���ԭ�򣺹��ջ���������ʾ����
            if(isSelectDepartment()==true)
            {
                buffer.append("(��)");
            }
            //Ѧ����ӽ���
        }
        //����(3)20070625 �촺Ӣ�޸� �޸�ԭ��:���������б�û�����"���ձ��"�������
        if(getTechNum() != null)
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
     * ���÷�ҳ��ҳ��Ϊ
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
        int a = mtControl.getAttrCount();
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
        int a = mtControl.getHeadsCount();
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
        	//���⣨5��2007.11.05 Ѧ���޸� ���ܽ������ֵ������ȷ
            //String[] objs = (String[]) iterator.next();
            Object[] objs = (Object[]) iterator.next();
            for (int j = 0; j < objs.length; j++)
            {
                if (objs[j] != null)
                {
                    //this.multiList.addTextCell(i, j, objs[j].toString());
                    this.multiList.addNumberTextCell(i, j, objs[j]);
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

    public CappMultiList  getMultilist()
     {
         return multiList;
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
     ////CCBegin SS1
   public String getMaterialName()
     {
         String materialName = materialNameField.getText().trim();
         if (materialName.equals(""))
         {
             return null;
         }
         else
         {
             return materialName;
         }
   }
   //�õ�������0928
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
  //�õ�������;0928
   public String getMaterialFunction()
  {
      String materialFunction = materialFunctionField.getText().trim();
      if (materialFunction.equals(""))
      {
          return null;
      }
      else
      {
          return materialFunction;
      }
  }

  //CCEnd SS1
   //CCEnd SS1
}
