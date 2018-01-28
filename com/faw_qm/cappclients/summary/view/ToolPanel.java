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
import com.faw_qm.cappclients.summary.controller.SummaryToolController;
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
 * <p>Title:��װ���ܽ��� </p>
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
* SS1 2013-2-20   ������ ���ܽ������ӻ������� assetNumberField������  toolNumField��װ���
* SS2 2013-2-20   ������ ��װ�������
* SS3 ������Ļ��ܣ�Ĭ�ϲ���  2013-12-26 ���� 
* SS4 ������Ĺ�װ����  2014-1-16 ����
 */

public class ToolPanel extends JPanel
{
    private JPanel jPanel1 = new JPanel();
    private JTabbedPane jTabbedPane1 = new JTabbedPane();
    private JPanel jPanel2 = new JPanel();
    private JButton summaryButton = new JButton();
    private JButton interruptButton = new JButton();
    private JButton customizeButton = new JButton();
    //CCBegin SS2
    private JButton gjylbdButton = new JButton();
    private JButton gjylbmButton = new JButton();
    private JButton zbwnljButton = new JButton();
    private JButton zbjuButton = new JButton();
    private JButton zbzyljButton = new JButton();
    private JButton wngjqdButton = new JButton();
    private JButton jjmxbButton = new JButton();
    private JButton zpgjylbButton = new JButton();
    private JButton jfjylbButton = new JButton();
    //CCEnd SS2
    private JPanel jPanel4 = new JPanel();
    private JLabel partLabel = new JLabel();
    private JLabel techcategoryLabel = new JLabel();
    private JLabel departLabel = new JLabel();
    private JTextField partTextField = new JTextField();
    private JButton searchButton = new JButton();
    private JCheckBox partCheckBox = new JCheckBox();
    private JCheckBox nottechCheckBox = new JCheckBox();
    private JCheckBox nottoolCheckBox = new JCheckBox();
    private JCheckBox notdepartmentCheckBox = new JCheckBox();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JPanel jPanel3 = new JPanel();
    private JButton saveButton = new JButton();
    private JButton exportButton = new JButton();
    private JButton exitButton = new JButton();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private JLabel tooltypeLabel = new JLabel();
    private JLabel jLabel1 = new JLabel();
    private SummaryToolController toolControl;
    private CappMultiList multiList = new CappMultiList();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private TotalResultIfc totalResult;
    private String[] attrDisplayname;
    private String part;
    private Vector vector;
    private int size;
    private static ResourceBundle res =
            ResourceBundle.getBundle(
            "com.faw_qm.cappclients.summary.util.SummaryResource",
            RemoteProperty.getVersionLocale());
    //CCBegin SS2
   // private SortingSelectedPanel sortingSelectedPanel2 = new
           // SortingSelectedPanel();
   
    public  SortingSelectedPanel sortingSelectedPanel2 = new
    SortingSelectedPanel();
    //CCEnd SS2
    private GridBagLayout gridBagLayout5 = new GridBagLayout();
    private SortingSelectedPanel sortingSelectedPanel1;
    private SortingSelectedPanel sortingSelectedPanel3;
    private PaginatePanel paginatePanel1 = new PaginatePanel();


    //2007.04.03
    private JLabel numberLabel = new JLabel();


    //2007.04.03 �촺Ӣ���
    private JTextField technicField = new JTextField();

    //���⣨7��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
    JCheckBox notTechnicsSelected = new JCheckBox();
    JButton searchTechnics = new JButton();

    //CCBegin SS1
    private JLabel assetNumberLabel = new JLabel();
    private JTextField assetNumberField = new JTextField();

    private JLabel toolNumLabel = new JLabel();
    private JTextField toolNumField = new JTextField();

     //CCEnd  SS1

    /**
     * ���������Ĺ��캯��
     */
    public ToolPanel()
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
    public ToolPanel(ActionListener control)
    {
        super();
        try
        {
            this.toolControl = (SummaryToolController) control;
            jbInit();
            localize();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
//CCBegin SS3
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
    //CCEnd SS3
    /**
     * ��ʼ������
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {
        String toolType = res.getString("tool_type");
        //sortingSelectedPanel2 = new SortingSelectedPanel("��װ����", "��װ");
        sortingSelectedPanel2 = new SortingSelectedPanel(toolType, "QMTool",
                "toolCf");
        sortingSelectedPanel2.setIsSelectCC(true);
        sortingSelectedPanel2.setDialogTitle(toolType);
        String department = res.getString("department");
      //CCBegin SS3
        String temCom = getUserFromCompany();
        if(temCom.equals("zczx")){
	          sortingSelectedPanel1 = new SortingSelectedPanel("�������","��֯����");
	          sortingSelectedPanel1.setIsSelectCC(true);
	          sortingSelectedPanel1.setDialogTitle(department);
        }else{
        	  sortingSelectedPanel1 = new SortingSelectedPanel(department
                      , "QMProcedure", "workShop");
              //sortingSelectedPanel1 = new SortingSelectedPanel("��֯����", "�������");
              sortingSelectedPanel1.setIsSelectCC(true);
              sortingSelectedPanel1.setDialogTitle(department);
        }

        //CCEnd SS3
        String technicsType = res.getString("technics_category");
        //sortingSelectedPanel3 = new SortingSelectedPanel("��������", "��������Ϣ");
        sortingSelectedPanel3 = new SortingSelectedPanel(technicsType
                , "QMTechnicsMaster", "technicsType");
        sortingSelectedPanel3.setIsSelectCC(true);
        sortingSelectedPanel3.setDialogTitle(technicsType);
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
//      CCBegin SS2
        gjylbdButton.setMaximumSize(new Dimension(114, 20));
        gjylbdButton.setMinimumSize(new Dimension(114, 20));
        gjylbdButton.setPreferredSize(new Dimension(114, 20));
        gjylbdButton.setHorizontalAlignment(SwingConstants.LEFT);
        gjylbdButton.setHorizontalTextPosition(SwingConstants.LEFT);
        gjylbdButton.setMnemonic('D');
        
        
        gjylbmButton.setMaximumSize(new Dimension(114, 20));
        gjylbmButton.setMinimumSize(new Dimension(114, 20));
        gjylbmButton.setPreferredSize(new Dimension(114, 20));
        gjylbmButton.setHorizontalAlignment(SwingConstants.LEFT);
        gjylbmButton.setHorizontalTextPosition(SwingConstants.LEFT);
        gjylbmButton.setMnemonic('M');
        
        zbwnljButton.setMaximumSize(new Dimension(114, 20));
        zbwnljButton.setMinimumSize(new Dimension(114, 20));
        zbwnljButton.setPreferredSize(new Dimension(114, 20));
        zbwnljButton.setHorizontalAlignment(SwingConstants.LEFT);
        zbwnljButton.setHorizontalTextPosition(SwingConstants.LEFT);
        
        zbjuButton.setMaximumSize(new Dimension(114, 20));
        zbjuButton.setMinimumSize(new Dimension(114, 20));
        zbjuButton.setPreferredSize(new Dimension(114, 20));
        zbjuButton.setHorizontalAlignment(SwingConstants.LEFT);
        zbjuButton.setHorizontalTextPosition(SwingConstants.LEFT);
        
        zbzyljButton.setMaximumSize(new Dimension(114, 20));
        zbzyljButton.setMinimumSize(new Dimension(114, 20));
        zbzyljButton.setPreferredSize(new Dimension(114, 20));
        zbzyljButton.setHorizontalAlignment(SwingConstants.LEFT);
        zbzyljButton.setHorizontalTextPosition(SwingConstants.LEFT);
        
        wngjqdButton.setMaximumSize(new Dimension(114, 20));
        wngjqdButton.setMinimumSize(new Dimension(114, 20));
        wngjqdButton.setPreferredSize(new Dimension(114, 20));
        wngjqdButton.setHorizontalAlignment(SwingConstants.LEFT);
        wngjqdButton.setHorizontalTextPosition(SwingConstants.LEFT);
        wngjqdButton.setMnemonic('W');
        
        jjmxbButton.setMaximumSize(new Dimension(114, 20));
        jjmxbButton.setMinimumSize(new Dimension(114, 20));
        jjmxbButton.setPreferredSize(new Dimension(114, 20));
        jjmxbButton.setHorizontalAlignment(SwingConstants.LEFT);
        jjmxbButton.setHorizontalTextPosition(SwingConstants.LEFT);
        jjmxbButton.setMnemonic('W');
        
        
        
        zpgjylbButton.setMaximumSize(new Dimension(114, 20));
        zpgjylbButton.setMinimumSize(new Dimension(114, 20));
        zpgjylbButton.setPreferredSize(new Dimension(114, 20));
        zpgjylbButton.setHorizontalAlignment(SwingConstants.LEFT);
        zpgjylbButton.setHorizontalTextPosition(SwingConstants.LEFT);
        
        jfjylbButton.setMaximumSize(new Dimension(114, 20));
        jfjylbButton.setMinimumSize(new Dimension(114, 20));
        jfjylbButton.setPreferredSize(new Dimension(114, 20));
        jfjylbButton.setHorizontalAlignment(SwingConstants.LEFT);
        jfjylbButton.setHorizontalTextPosition(SwingConstants.LEFT);
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

        customizeButton.addActionListener(toolControl);
        //CCBegin SS2
        gjylbdButton.addActionListener(toolControl);
        gjylbmButton.addActionListener(toolControl);
        zbwnljButton.addActionListener(toolControl);
        zbjuButton.addActionListener(toolControl);
        zbzyljButton.addActionListener(toolControl);
        wngjqdButton.addActionListener(toolControl);
        jjmxbButton.addActionListener(toolControl);
        zpgjylbButton.addActionListener(toolControl);
        jfjylbButton.addActionListener(toolControl);
        //CCEnd SS2     
        exitButton.addActionListener(toolControl);
        summaryButton.addActionListener(toolControl);
        interruptButton.addActionListener(toolControl);
        searchButton.addActionListener(toolControl);
        saveButton.addActionListener(toolControl);
        exportButton.addActionListener(toolControl);
        saveButton.setActionCommand("SAVE");
        saveButton.setMnemonic('V');
        searchButton.setActionCommand("SEARCH");
        customizeButton.setActionCommand("CUSTO");
        //CCBegin SS2
        gjylbdButton.setActionCommand("gjylbd");
        gjylbmButton.setActionCommand("gjylbm");
        zbwnljButton.setActionCommand("zbwnlj");
        zbjuButton.setActionCommand("zbju");
        zbzyljButton.setActionCommand("zbzylj");
        wngjqdButton.setActionCommand("wngjqd");
        jjmxbButton.setActionCommand("jjmxb");
        zpgjylbButton.setActionCommand("zpgjylb");
        jfjylbButton.setActionCommand("jfjylb");
//      CCEnd SS2 
        exitButton.setActionCommand("EXIT");
        exitButton.setMnemonic('X');
        summaryButton.setActionCommand("SUM");
        summaryButton.setMnemonic('T');
        interruptButton.setActionCommand("INTERRUPT");
        interruptButton.setMnemonic('B');
        exportButton.setActionCommand("EXPORT");
        exportButton.setMnemonic('R');
        sortingSelectedPanel2.setButtonSize(80, 23);
        sortingSelectedPanel1.setButtonSize(80, 23);
        sortingSelectedPanel3.setButtonSize(80, 23);
        sortingSelectedPanel3.setButtonName("���. . .");
        sortingSelectedPanel2.setButtonName("���. . .");
        sortingSelectedPanel1.setButtonName("���. . .");
        //2007.04.03
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
        searchTechnics.addActionListener(toolControl);
        //���⣨7������


        paginatePanel1.addListener(new PaginateListener()
        {
            public void paginateEvent(PaginateEvent e)
            {
                paginatePanel_paginateEvent(e);
            }
        });
        //CCBegin SS1
       // this.add(jPanel1, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0
       //                                          , GridBagConstraints.CENTER,
       //                                          GridBagConstraints.HORIZONTAL,
        //                                         new Insets(8, 0, 0, 0), 0, 0));
         this.add(jPanel1, new GridBagConstraints(0, 0, 2, 1, 1.0, 0.0
                                                 , GridBagConstraints.CENTER,
                                                 GridBagConstraints.HORIZONTAL,
                                                 new Insets(8, 0, 0, 0), 0, 0));
           
        jPanel2.add(assetNumberLabel, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
                       , GridBagConstraints.EAST, GridBagConstraints.NONE,
                       new Insets(4, 0, 4, 0), 0, 0));
  
        jPanel2.add(toolNumLabel, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
                       , GridBagConstraints.EAST, GridBagConstraints.NONE,
                       new Insets(4, 0, 4, 0), 0, 0));

        jPanel1.add(jTabbedPane1, new GridBagConstraints(0, 0, 1, 10, 1.0, 0.0
                , GridBagConstraints.SOUTHEAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 10, 0, 0), 0, 0));
    //CCEnd SS1
        
       
        jTabbedPane1.add(jPanel2, res.getString("summary_condition"));
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
        jPanel2.add(tooltypeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(4, 8, 4, 0), 0, 0));
        jPanel2.add(departLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
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
        jPanel2.add(nottoolCheckBox,
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
                new Insets(4, 8, 4, 8), 0, 0));
        jPanel2.add(sortingSelectedPanel2,
                    new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0
                                           , GridBagConstraints.WEST,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
        jPanel2.add(sortingSelectedPanel1,
                    new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
        //2007.04.03
        jPanel2.add(numberLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
                , GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(4, 0, 4, 0), 0, 0));

        //���⣨7��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
        //2007.04.03
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


        jPanel2.add(sortingSelectedPanel3,
                    new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.HORIZONTAL,
                                           new Insets(0, 0, 0, 0), 0, 0));
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
        //CCBegin SS1
        jPanel1.add(gjylbdButton,
                new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.NORTHWEST,
                                       GridBagConstraints.NONE,
                                       new Insets(4, 8, 0, 10), 0, 0));
        
        jPanel1.add(gjylbmButton,
                new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.NORTHWEST,
                                       GridBagConstraints.NONE,
                                       new Insets(4, 8, 0, 10), 0, 0));
        jPanel1.add(wngjqdButton,
                new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.NORTHWEST,
                                       GridBagConstraints.NONE,
                                       new Insets(4, 8, 0, 10), 0, 0));
        jPanel1.add(jjmxbButton,
                new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.NORTHWEST,
                                       GridBagConstraints.NONE,
                                       new Insets(4, 8, 0, 10), 0, 0));                
        
        
        jPanel1.add(zbwnljButton,
                new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.NORTHWEST,
                                       GridBagConstraints.NONE,
                                       new Insets(4, 8, 0, 10), 0, 0));
        
        jPanel1.add(zbjuButton,
                new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.NORTHWEST,
                                       GridBagConstraints.NONE,
                                       new Insets(4, 8, 0, 10), 0, 0));
        jPanel1.add(zbzyljButton,
                new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.NORTHWEST,
                                       GridBagConstraints.NONE,
                                       new Insets(4, 8, 0, 10), 0, 0));
        

        
        jPanel1.add(zpgjylbButton,
                new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.NORTHWEST,
                                       GridBagConstraints.NONE,
                                       new Insets(4, 8, 0, 10), 0, 0));
        
        jPanel1.add(jfjylbButton,
                new GridBagConstraints(1, 11, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.NORTHWEST,
                                       GridBagConstraints.NONE,
                                       new Insets(4, 8, 0, 10), 0, 0));                               
        //CCEnd SS2
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
         ////CCBegin SS1
		    jPanel2.add(assetNumberField, new GridBagConstraints(1, 5, 2, 1, 1.0, 0.0
		        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
		        new Insets(0, 8, 0, 0), 0, 0));
		    jPanel2.add(toolNumField, new GridBagConstraints(1, 6, 2, 1, 1.0, 0.0
		        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
		        new Insets(0, 8, 0, 0), 0, 0));

       //CCEnd by SS1
        //CCBegin SS4
        if(temCom.equals("zczx")){
            this.multiList.setHeadings(toolControl.getSchemaheadings(11));	
        }else{
            this.multiList.setHeadings(toolControl.getSchemaheadings());
        }
        //CCEnd SS4
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
        //CCBegin SS2
        gjylbdButton.setText("����һ����");
        gjylbmButton.setText("ĥ��һ����");
        wngjqdButton.setText("���ܹ����嵥");
        jjmxbButton.setText("�о���ϸ��");
        zbwnljButton.setText("�ʱ���������");
        zbjuButton.setText("�ʱ����");
        zbzyljButton.setText("�ʱ�ר������");
        zpgjylbButton.setText("װ�乤��һ����");
        jfjylbButton.setText("�и���һ����");
        //CCEnd SS2
        partLabel.setText(res.getString("part_number"));
        techcategoryLabel.setText(res.getString("technics_category"));
        departLabel.setText(res.getString("department"));
        //2007.04.03
        numberLabel.setText("���ձ��");
        //CCBegin SS1
        assetNumberLabel.setText("������");
        toolNumLabel.setText("��װ���");
        //CCEnd SS1
        searchButton.setText(res.getString("search2"));
        partCheckBox.setText(res.getString("structure"));
        nottechCheckBox.setText(res.getString("not"));
        nottoolCheckBox.setText(res.getString("not"));
        notdepartmentCheckBox.setText(res.getString("not"));
        saveButton.setText(res.getString("save"));
        exportButton.setText(res.getString("export"));
        exitButton.setText(res.getString("exit"));
        tooltypeLabel.setText(res.getString("tool_type"));
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
     * �ǹ�װ���ิѡ���Ƿ�ѡȡ
     * @return boolean
     */
    public boolean isSelectTool()
    {
        return nottoolCheckBox.isSelected();
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
                 //   }
                //}
               // vec.add(i, strs);
               // vector.add(i + 1, strs);
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
     * �õ���װ����
     * @return BaseValueIfc
     */
    public BaseValueIfc getToolType()
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
        if (sortingSelectedPanel3.getCoding() != null)
        {
            String technics = res.getString("technics_category");
            buffer.append(";").append(technics).append(":").append(
                    sortingSelectedPanel3.getCoding().toString());
            //Ѧ�� 20080227 ��� ���ԭ�򣺹��ջ���������ʾ����
            if(isSelectTech()==true)
            {
                buffer.append("(��)");
            }
            //Ѧ����ӽ���
        }
        if (sortingSelectedPanel2.getCoding() != null)
        {
            String tool = res.getString("tool_type");
            buffer.append(";").append(tool).append(":").append(
                    sortingSelectedPanel2.getCoding().toString());
            //Ѧ�� 20080227 ��� ���ԭ�򣺹��ջ���������ʾ����
            if(isSelectTool()==true)
            {
                buffer.append("(��)");
            }
            //Ѧ����ӽ���
        }
        if (sortingSelectedPanel1.getCoding() != null)
        {
            String department = res.getString("department");
            buffer.append(";").append(department).append(":").append(
                    sortingSelectedPanel1.getCoding().toString());
            //Ѧ�� 20080227 ��� ���ԭ�򣺹��ջ���������ʾ����
            if(isSelectDepartment()==true)
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
        int a = toolControl.getAttrCount();
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
        int a = toolControl.getHeadsCount();
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

    public CappMultiList getMultilist()
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
       public String getToolNum()
     {
         String toolNum = toolNumField.getText().trim();
         if (toolNum.equals(""))
         {
             return null;
         }
         else
         {
             return toolNum;
         }


}
       /**
        * chudaming add 2009.4.16
        */
       public void clearMultiList() {
         this.multiList.clear();
       }

       /**
        * chudaming add 2009.4.16
        */
       public void setgjylbdButtonState() {
         this.gjylbdButton.setEnabled(false);
         this.gjylbmButton.setEnabled(true);
         this.wngjqdButton.setEnabled(true);
         this.jjmxbButton.setEnabled(true);
         
         //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
         this.zpgjylbButton.setEnabled(true);
         this.jfjylbButton.setEnabled(true);
         //CCEnd by liunan 2012-2-7
         
         this.zbwnljButton.setEnabled(true);
         this.zbjuButton.setEnabled(true);
         this.zbzyljButton.setEnabled(true);
         
       }

       /**
        * chudaming add 2009.4.16
        */
       public void setgjylbmButtonState() {
    	   this.gjylbdButton.setEnabled(true);
           this.gjylbmButton.setEnabled(false);
           this.wngjqdButton.setEnabled(true);
           this.jjmxbButton.setEnabled(true);
           
           //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
           this.zpgjylbButton.setEnabled(true);
           this.jfjylbButton.setEnabled(true);
           //CCEnd by liunan 2012-2-7
         
           this.zbwnljButton.setEnabled(true);
           this.zbjuButton.setEnabled(true);
           this.zbzyljButton.setEnabled(true);
       }

       /**
        * chudaming add 2009.4.16
        */
       public void setwngjqdButtonState() {
    	   this.gjylbdButton.setEnabled(true);
           this.gjylbmButton.setEnabled(true);
           this.wngjqdButton.setEnabled(false);
           this.jjmxbButton.setEnabled(true);
           //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
           this.zpgjylbButton.setEnabled(true);
           this.jfjylbButton.setEnabled(true);
           //CCEnd by liunan 2012-2-7
           this.zbwnljButton.setEnabled(true);
           this.zbjuButton.setEnabled(true);
           this.zbzyljButton.setEnabled(true);
       }
       /**
        * chudaming add 2009.4.16
        */
       public void setjjmxbButtonState() {
    	   this.gjylbdButton.setEnabled(true);
           this.gjylbmButton.setEnabled(true);
           this.wngjqdButton.setEnabled(true);
           this.jjmxbButton.setEnabled(false);
           //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
           this.zpgjylbButton.setEnabled(true);
           this.jfjylbButton.setEnabled(true);
           //CCEnd by liunan 2012-2-7
           this.zbwnljButton.setEnabled(true);
           this.zbjuButton.setEnabled(true);
           this.zbzyljButton.setEnabled(true);
       }
       public void setzbwnljButtonState() {
           this.gjylbdButton.setEnabled(true);
           this.gjylbmButton.setEnabled(true);
           this.wngjqdButton.setEnabled(true);
           this.jjmxbButton.setEnabled(true);
           //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
           this.zpgjylbButton.setEnabled(true);
           this.jfjylbButton.setEnabled(true);
           //CCEnd by liunan 2012-2-7
           this.zbwnljButton.setEnabled(false);
           this.zbjuButton.setEnabled(true);
           this.zbzyljButton.setEnabled(true);
         }
       public void setzbjuButtonState() {
           this.gjylbdButton.setEnabled(true);
           this.gjylbmButton.setEnabled(true);
           this.wngjqdButton.setEnabled(true);
           this.jjmxbButton.setEnabled(true);
           //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
           this.zpgjylbButton.setEnabled(true);
           this.jfjylbButton.setEnabled(true);
           //CCEnd by liunan 2012-2-7
           this.zbwnljButton.setEnabled(true);
           this.zbjuButton.setEnabled(false);
           this.zbzyljButton.setEnabled(true);
         }
       public void setzbzyljButtonState() {
           this.gjylbdButton.setEnabled(true);
           this.gjylbmButton.setEnabled(true);
           this.wngjqdButton.setEnabled(true);
           this.jjmxbButton.setEnabled(true);
           //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
           this.zpgjylbButton.setEnabled(true);
           this.jfjylbButton.setEnabled(true);
           //CCEnd by liunan 2012-2-7
           this.zbwnljButton.setEnabled(true);
           this.zbjuButton.setEnabled(true);
           this.zbzyljButton.setEnabled(false);
         }
         
       //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
       public void setzpgjylbButtonState() {
           this.gjylbdButton.setEnabled(true);
           this.gjylbmButton.setEnabled(true);
           this.wngjqdButton.setEnabled(true);
           this.jjmxbButton.setEnabled(true);
           //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
           this.zpgjylbButton.setEnabled(false);
           this.jfjylbButton.setEnabled(true);
           //CCEnd by liunan 2012-2-7
           this.zbwnljButton.setEnabled(true);
           this.zbjuButton.setEnabled(true);
           this.zbzyljButton.setEnabled(true);
         }
       public void setjfjylbButtonState() {
           this.gjylbdButton.setEnabled(true);
           this.gjylbmButton.setEnabled(true);
           this.wngjqdButton.setEnabled(true);
           this.jjmxbButton.setEnabled(true);
           //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
           this.zpgjylbButton.setEnabled(true);
           this.jfjylbButton.setEnabled(false);
           //CCEnd by liunan 2012-2-7
           this.zbwnljButton.setEnabled(true);
           this.zbjuButton.setEnabled(true);
           this.zbzyljButton.setEnabled(true);
         }
       //CCEnd by liunan 2012-2-7
       /**
        * chudaming add 2009.4.16  ��ȡ���ܵ� gjylbd 1��gjylbm 2��wngjqd 3  ,jjmxb  4  ,zbwnlj 5  ,zbju  6,zbzylj,7���� 0
        * @return int
        */
       public int getSummaryMode() {
         if (gjylbdButton.isEnabled() == false 
        		 && gjylbmButton.isEnabled() == true
        		 && wngjqdButton.isEnabled() == true
        		 && jjmxbButton.isEnabled() == true
             //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
             && zpgjylbButton.isEnabled() == true
             && jfjylbButton.isEnabled() == true
             //CCEnd by liunan 2012-2-7
        		 && zbwnljButton.isEnabled() == true
        		 && zbjuButton.isEnabled() == true
        		 && zbzyljButton.isEnabled() == true) {
           return 1;
         }
         if (gjylbdButton.isEnabled() == true 
        		 && gjylbmButton.isEnabled() == false
        		 && wngjqdButton.isEnabled() == true
        		 && jjmxbButton.isEnabled() == true
             //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
             && zpgjylbButton.isEnabled() == true
             && jfjylbButton.isEnabled() == true
             //CCEnd by liunan 2012-2-7
        		 && zbwnljButton.isEnabled() == true
        		 && zbjuButton.isEnabled() == true
        		 && zbzyljButton.isEnabled() == true) {
             return 2;
           }
         if (gjylbdButton.isEnabled() == true 
        		 && gjylbmButton.isEnabled() == true
        		 && wngjqdButton.isEnabled() == false
        		 && jjmxbButton.isEnabled() == true
             //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
             && zpgjylbButton.isEnabled() == true
             && jfjylbButton.isEnabled() == true
             //CCEnd by liunan 2012-2-7
        		 && zbwnljButton.isEnabled() == true
        		 && zbjuButton.isEnabled() == true
        		 && zbzyljButton.isEnabled() == true) {
             return 3;
           }
         if (gjylbdButton.isEnabled() == true 
        		 && gjylbmButton.isEnabled() == true
        		 && wngjqdButton.isEnabled() == true
        		 && jjmxbButton.isEnabled() == false
             //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
             && zpgjylbButton.isEnabled() == true
             && jfjylbButton.isEnabled() == true
             //CCEnd by liunan 2012-2-7
        		 && zbwnljButton.isEnabled() == true
        		 && zbjuButton.isEnabled() == true
        		 && zbzyljButton.isEnabled() == true) {
             return 4;
           }
         if (gjylbdButton.isEnabled() == true 
        		 && gjylbmButton.isEnabled() == true
        		 && wngjqdButton.isEnabled() == true
        		 && jjmxbButton.isEnabled() == true
             //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
             && zpgjylbButton.isEnabled() == true
             && jfjylbButton.isEnabled() == true
             //CCEnd by liunan 2012-2-7
        		 && zbwnljButton.isEnabled() == false
        		 && zbjuButton.isEnabled() == true
        		 && zbzyljButton.isEnabled() == true) {
             return 5;
           }
         if (gjylbdButton.isEnabled() == true 
        		 && gjylbmButton.isEnabled() == true
        		 && wngjqdButton.isEnabled() == true
        		 && jjmxbButton.isEnabled() == true
             //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
             && zpgjylbButton.isEnabled() == true
             && jfjylbButton.isEnabled() == true
             //CCEnd by liunan 2012-2-7
        		 && zbwnljButton.isEnabled() == true
        		 && zbjuButton.isEnabled() == false
        		 && zbzyljButton.isEnabled() == true) {
             return 6;
           }
         if (gjylbdButton.isEnabled() == true 
        		 && gjylbmButton.isEnabled() == true
        		 && wngjqdButton.isEnabled() == true
        		 && jjmxbButton.isEnabled() == true
             //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
             && zpgjylbButton.isEnabled() == true
             && jfjylbButton.isEnabled() == true
             //CCEnd by liunan 2012-2-7
        		 && zbwnljButton.isEnabled() == true
        		 && zbjuButton.isEnabled() == true
        		 && zbzyljButton.isEnabled() == false) {
             return 7;
           }
           
         //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
         
         if (gjylbdButton.isEnabled() == true 
        		 && gjylbmButton.isEnabled() == true
        		 && wngjqdButton.isEnabled() == true
        		 && jjmxbButton.isEnabled() == true
             //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
             && zpgjylbButton.isEnabled() == false
             && jfjylbButton.isEnabled() == true
             //CCEnd by liunan 2012-2-7
        		 && zbwnljButton.isEnabled() == true
        		 && zbjuButton.isEnabled() == true
        		 && zbzyljButton.isEnabled() == true) {
             return 8;
           }
           
         if (gjylbdButton.isEnabled() == true 
        		 && gjylbmButton.isEnabled() == true
        		 && wngjqdButton.isEnabled() == true
        		 && jjmxbButton.isEnabled() == true
             //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
             && zpgjylbButton.isEnabled() == true
             && jfjylbButton.isEnabled() == false
             //CCEnd by liunan 2012-2-7
        		 && zbwnljButton.isEnabled() == true
        		 && zbjuButton.isEnabled() == true
        		 && zbzyljButton.isEnabled() == true) {
             return 9;
           }
         //CCEnd by liunan 2012-2-7
         //����
         return 0;
       }
       //CCEnd SS1
}
