/** ���ɳ���MaterialBillJFrame.java	1.1  2003/05/20
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.part.client.other.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Observable;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import com.faw_qm.clients.beans.explorer.QM;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.effectivity.controller.EffAction;
import com.faw_qm.part.client.effectivity.view.EffClientViewIfc;
import com.faw_qm.part.client.main.util.QMProductManagerRB;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;


/**
 * <p>Title: ���������嵥�����ࡣ</p>
 * <p>�û�������Ҫ��ѡ����Ʒ�򲿼�����BOM���ݺ������ʽ�����BOM��<p>
 * <p>���������嵥ʱ������ǰ��Ʒ�ṹ�����������ù淶ȷ���㲿��ʹ�ýṹ��<p>
 * <p>�Ӽ�ʹ�õİ汾������Ӽ���û�з��ϵ�ǰ���ù淶�İ汾����ü�������<p>
 * <p>�������嵥�С�</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 2.0
 */

public class MaterialBillJFrame extends JFrame implements EffClientViewIfc
{
    /**���л�ID*/
    static final long serialVersionUID = 1L;


    /**�����*/
    private JPanel contentPane;

    /**������ʾ����������*/
    private JPanel exportJPanel = new JPanel();

    /**�������*/
    private JPanel actionJPanel = new JPanel();

    /**����ѡ�����*/
    private JPanel selectJPanel = new JPanel();
    private JPanel selectsubJPanel= new JPanel();
    
    private JPanel classeJPanel = new JPanel();

    /**���ư�ť���*/
    private JPanel buttonJPanel = new JPanel();

    /**��ȫ����ѡ��ť*/
    private JRadioButton allJRadioButton = new JRadioButton();

    /**���ּ���ѡ��ť*/
    private JRadioButton gradeJRadioButton = new JRadioButton();

    /**��ͳ�Ʊ�ѡ��ť*/
    private JRadioButton statisticsJRadioButton = new JRadioButton();

    /**�����ࡱѡ��ť*/
    private JRadioButton classifyJRadioButton = new JRadioButton();

    /**����Դ��ѡ���*/
    private JComboBox sourceJComboBox = new JComboBox();

    /**�����͡�ѡ���*/
    private JComboBox typeJomboBox = new JComboBox();

    /**��Դ��ǩ*/
    private JLabel sourceJLabel = new JLabel();

    /**���ͱ�ǩ*/
    private JLabel typeJLabel = new JLabel();

    /**״̬��*/
    private JLabel statusJLabel = new JLabel();

    /**ȷ����ť*/
    private JButton okJButton = new JButton();

    /**ȡ����ť*/
    private JButton cancelJButton = new JButton();

    private TitledBorder titledBorder1;

    /**����������*/
    private ExportAttributesJPanel exportAttributeJPanel;

    /**������*/
    private EffAction controller;

    /**"ȫ��"�Ƿ�ѡ��*/
    private boolean all_flag;

    /**��ȫ�����Ƿ�ѡ�е��ַ�����ʾ*/
    private String all_String;

    /**���ּ����Ƿ�ѡ��*/
    private boolean grade_flag;
    private String grade_String;

    /**��ͳ�Ʊ��Ƿ�ѡ��*/
    private boolean statistics_flag;
    private String statistics_String;

    /**�����ࡱ�Ƿ�ѡ��*/
    private boolean classify_flag;
    private String classify_String;

    /**����Դ���Ƿ�ѡ��*/
    private boolean source_flag;
    private String source_String;

    /**�����͡��Ƿ�ѡ��*/
    private boolean type_flag;
    private String type_String;

    /**��ͳ�Ʊ��Ƿ񱻼���*/
    private String statistics_Enable;
    private boolean enable_statistics;

    /**��Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.part.client.other.util.OtherRB";

    /**�㲿������ӿ�*/
    private QMPartIfc partIfc;

    /**ϵͳ����������㲿�����͵ļ���*/
    private QMPartType[] partType = QMPartType.getQMPartTypeSet();

    /**ϵͳ����������㲿����Դ�ļ���*/
    private ProducedBy[] producedBy = ProducedBy.getProducedBySet();

    /**���ò����Ĺ�����*/
    private HashMap hashMap = new HashMap();

    /**��Դ�Ĺ�����*/
    private HashMap produceMap = new HashMap();
    
    private JSeparator horizontalLine;

    /**���沼�ֹ�����*/
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private GridBagLayout gridBagLayout5 = new GridBagLayout();
    private GridBagLayout gridBagLayout6 = new GridBagLayout();
    private GridBagLayout gridBagLayout7 = new GridBagLayout();
    
    //CCBegin by liunan 2008-08-12
    JButton exportButton = new JButton();
    //CCEnd by liunan 2008-08-12

    /**
     * ���캯����
     * @param c  ��������
     * @param part  �㲿������ӿڡ�
     */
    public MaterialBillJFrame(EffAction c, QMPartIfc part)
    {
        try
        {
            controller = c;
            this.partIfc = part;
            //ʵ��������������
            exportAttributeJPanel = new ExportAttributesJPanel();
            jbInit();
            //����ť��Ӽ���
            initView(null);
            //���ý��涯̬����
            if (partIfc != null)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        "MaterialTitle", null);
                this.setTitle(title + partIfc.getPartNumber() + " " +
                              partIfc.getPartName());
                String iconImage = QMMessage.getLocalizedMessage(
                        "com.faw_qm.part.client.main.util.QMProductManagerRB",
                        QMProductManagerRB.ICONIMAGE, null);
                setIconImage(new ImageIcon(QM.getIcon(iconImage)).getImage());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
 //CCBegin by liunan 2008-08-12
    /**
     * ���캯��
     * @param c  ������
     * @param part  �㲿������ӿ�
     * @author 
     */
    public MaterialBillJFrame(EffAction c,QMPartIfc part,boolean bool)
    {
      try
      {
        controller =c;
        this.partIfc = part;
        //ʵ��������������
        exportAttributeJPanel = new ExportAttributesJPanel(bool);//add by liun
        jbInit();
        //����ť��Ӽ���
        initView(null);
        //���ý��涯̬����
        if(partIfc!=null)
        {
          String title = QMMessage.getLocalizedMessage(RESOURCE,"MaterialTitle",null);
          this.setTitle(title + partIfc.getPartNumber()+" "+partIfc.getPartName());
          String iconImage = QMMessage.getLocalizedMessage("com.faw_qm.part.client.main.util.QMProductManagerRB", QMProductManagerRB.ICONIMAGE, null);
          setIconImage(new ImageIcon(QM.getIcon(iconImage)).getImage());
        }
      }
      catch(Exception e)
      {
        e.printStackTrace();
      }
    }
    //CCEnd by liunan 2008-08-12

    /**
     * ��ʼ����
     * @throws Exception
     */
    private void jbInit()
            throws Exception
    {

        //Button
        horizontalLine = new JSeparator();
        cancelJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setActionCommand("CANCEL");
        cancelJButton.setMnemonic('C');
        okJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("OK");
        okJButton.setMnemonic('Y');
        classifyJRadioButton.setFont(new java.awt.Font("Dialog", 0, 12));
        classifyJRadioButton.setActionCommand("CLASSIFY");
        statisticsJRadioButton.setFont(new java.awt.Font("Dialog", 0, 12));
        statisticsJRadioButton.setActionCommand("STATISTICS");
        gradeJRadioButton.setFont(new java.awt.Font("Dialog", 0, 12));
        gradeJRadioButton.setActionCommand("GRADE");
        allJRadioButton.setFont(new java.awt.Font("Dialog", 0, 12));
        allJRadioButton.setActionCommand("ALL");
        okJButton.setText("Ok");
        cancelJButton.setText("Cancel");
        allJRadioButton.setText("All");
        gradeJRadioButton.setText("Grade");
        statisticsJRadioButton.setText("Statistics");
        classifyJRadioButton.setText("Classify");

        //JLabel
        sourceJLabel.setText("Source");
        typeJLabel.setText("Type");
        statusJLabel.setText("  ");
        statusJLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        statusJLabel.setDoubleBuffered(true);
        typeJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        typeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        typeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        sourceJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        sourceJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        sourceJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);

        //this
        contentPane = (JPanel)this.getContentPane();
        titledBorder1 = new TitledBorder(QMMessage.getLocalizedMessage(RESOURCE,
                "out", null));
        contentPane.setLayout(gridBagLayout1);
        this.setSize(new Dimension(650, 500));
        setSelected(true, false, false, false, false, false, true);

        sourceJComboBox.addItem("");
        typeJomboBox.addItem("");
        actionJPanel.setBorder(titledBorder1);
        selectsubJPanel.setBorder(new TitledBorder(""));
        horizontalLine.setBounds(10, 247, 580, 2);

        //���沼��
        actionJPanel.setLayout(gridBagLayout3);
        selectJPanel.setLayout(gridBagLayout5);
        selectsubJPanel.setLayout(gridBagLayout7);
        classeJPanel.setLayout(gridBagLayout6);
        buttonJPanel.setLayout(gridBagLayout4);
        exportJPanel.setLayout(gridBagLayout2);
        
        //CCBegin by liunan 2008-08-12
        exportButton.setMaximumSize(new Dimension(75, 23));
        exportButton.setMinimumSize(new Dimension(75, 23));
        exportButton.setPreferredSize(new Dimension(75, 23));
        exportButton.setActionCommand("EXPORT");
        exportButton.setSelectedIcon(null);
        exportButton.setText("���");
        //CCEnd by liunan 2008-08-12
        contentPane.add(exportJPanel,
                        new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.BOTH,
                                               //CCBegin by liunan 2011-06-20 �����ѡ������������ۡ�
                                               //new Insets(10, 10, 10, 10), 0, 0));
                                               new Insets(10, 10, 3, 10), 0, 0));
                                               //CCEnd by liunan 2011-06-20
        exportJPanel.add(exportAttributeJPanel,
                         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 0), 0, 0));
        contentPane.add(actionJPanel,
                        new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
                                               , GridBagConstraints.CENTER,
                                               GridBagConstraints.HORIZONTAL,
                                               //CCBegin by liunan 2011-06-20 �����ѡ������������ۡ�
                                               //new Insets(8, 10, 7, 10), 0, 41));anan
                                               //CCEnd by liunan 2011-06-20
                                               new Insets(3, 10, 3, 10), 0, 41));
        actionJPanel.add(selectJPanel,
                         new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 0, 0, 104), 0, 0));
        actionJPanel.add(horizontalLine,
                new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0,
                        GridBagConstraints.CENTER,
                        //CCBegin by liunan 2011-06-20 �����ѡ������������ۡ�
                        //GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10,
                        GridBagConstraints.HORIZONTAL, new Insets(3, 10, 3,
                        //CCEnd by liunan 2011-06-20
                                10), 0, 0));
        actionJPanel.add(classeJPanel, new GridBagConstraints(0, 2, 1, 1, 1.0,
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        selectJPanel.add(allJRadioButton, new GridBagConstraints(0, 0, 1, 1,
                0.0, 0.0, GridBagConstraints.SOUTHWEST,
                GridBagConstraints.NONE, new Insets(0, 1, 0, 70), 0, 0));
        selectJPanel.add(selectsubJPanel, new GridBagConstraints(1, 0, 1, 1,
                1.0, 0.0, GridBagConstraints.SOUTHWEST,
                GridBagConstraints.NONE, new Insets(0, -2, 0, 52), 0, 0));
        selectsubJPanel.add(gradeJRadioButton, new GridBagConstraints(0, 0, 1, 1,
                0.0, 0.0, GridBagConstraints.SOUTHWEST,
                GridBagConstraints.NONE, new Insets(0, -2, 0, 52), 0, 0));
        selectsubJPanel.add(statisticsJRadioButton, new GridBagConstraints(1, 0,
                1, 1, 0.0, 0.0, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(0, 50, 0, 116), 0, 0));
        classeJPanel.add(classifyJRadioButton, new GridBagConstraints(0, 0, 1,
                1, 0.0, 0.0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        classeJPanel.add(sourceJLabel, new GridBagConstraints(1, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 10, 0, 0), 0, 0));
        classeJPanel.add(typeJLabel, new GridBagConstraints(3, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 5, 0, 8), 0, 0));
        classeJPanel.add(sourceJComboBox, new GridBagConstraints(2, 0, 1, 1,
                1.0, 0.0, GridBagConstraints.WEST,
                GridBagConstraints.HORIZONTAL, new Insets(0, 8, 0, 0), 0, 0));
        classeJPanel.add(typeJomboBox, new GridBagConstraints(4, 0, 1, 1, 1.0,
                0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
       //CCBegin by liunan 2008-08-12
        //ԭ��������
     /*   buttonJPanel.add(cancelJButton, new GridBagConstraints(1, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 0), 0, 0));
        buttonJPanel.add(okJButton, new GridBagConstraints(0, 0, 1, 1, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 8), 0, 0));*/
       buttonJPanel.add(exportButton,  new GridBagConstraints(0, 0, 1, 1, 0.0,
                0.0 ,GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, 
                new Insets(0, 0, 0, 8), 0, 0));
        buttonJPanel.add(okJButton,      new GridBagConstraints(1, 0, 1, 1, 0.0,
                0.0 ,GridBagConstraints.CENTER, GridBagConstraints.NONE, 
                new Insets(0, 0, 10, 8), 0, 0));
        buttonJPanel.add(cancelJButton,     new GridBagConstraints(2, 0, 1, 1, 0.0,
                0.0 ,GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 0, 10, 0), 0, 0));                
        //CCEnd by liunan 2008-08-12              
        contentPane.add(statusJLabel, new GridBagConstraints(0, 3, 1, 1, 1.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        contentPane.add(buttonJPanel, new GridBagConstraints(0, 2, 1, 1, 1.0,
                0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
                new Insets(0, 0, 0, 10), 0, 0));
        localize();
    }


    /**
     * ���ػ���
     */
    public void localize()
    {
        //��ȡ��Դ�ļ�
        allJRadioButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "all", null));
        gradeJRadioButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "grade", null));
        statisticsJRadioButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "statistics", null));
        classifyJRadioButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "classify", null));
        sourceJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE, "source", null));
        typeJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE, "type", null));
        okJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "ok", null));
        cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "cancel", null));
        //��ȡϵͳ�е������㲿������
        for (int i = 0; i < partType.length; i++)
        {
            hashMap.put(partType[i],
                        partType[i].getDisplay(RemoteProperty.getVersionLocale()));
        }
        //������ѡ�������㲿������
        for (int i = 0; i < hashMap.size(); i++)
        {
            typeJomboBox.addItem(hashMap.get(partType[i]));

        }
        //��ȡϵͳ�е������㲿����Դ
        for (int i = 0; i < producedBy.length; i++)
        {
            produceMap.put(producedBy[i],
                           producedBy[i].getDisplay(RemoteProperty.
                    getVersionLocale()));
        }
        //����Դѡ�������㲿����Դ
        for (int i = 0; i < produceMap.size(); i++)
        {
            sourceJComboBox.addItem(produceMap.get(producedBy[i]));
        }

    }


    /**
     * ���Ƹ�ѡ��ť��ѡ������ʾ״̬��
     * @param s1 ����ȫ����ѡ��ť����ʾ״̬��
     * @param s2 �����ּ���ѡ��ť����ʾ״̬��
     * @param s3 ����ͳ�Ʊ�ѡ��ť����ʾ״̬��
     * @param s4 �������ࡱѡ��ť����ʾ״̬��
     * @param s5 ������Դ��ѡ������ʾ״̬��
     * @param s6 �������͡�ѡ������ʾ״̬��
     * @param s7 ����ͳ�Ʊ��Ƿ񱻼��
     */
    public void setFlag(String s1, String s2, String s3, String s4, String s5,
                        String s6, String s7)
    {

        if (s1.equals("True"))
        {
            all_flag = true;
        }
        if (s1.equals("False"))
        {
            all_flag = false;
        }
        if (s2.equals("True"))
        {
            grade_flag = true;
        }
        if (s2.equals("False"))
        {
            grade_flag = false;
        }
        if (s3.equals("True"))
        {
            statistics_flag = true;
        }
        if (s3.equals("False"))
        {
            statistics_flag = false;
        }
        if (s4.equals("True"))
        {
            classify_flag = true;
        }
        if (s4.equals("False"))
        {
            classify_flag = false;
        }
        if (s5.equals("True"))
        {
            source_flag = true;
        }
        if (s5.equals("False"))
        {
            source_flag = false;
        }
        if (s6.equals("True"))
        {
            type_flag = true;
        }
        if (s6.equals("False"))
        {
            type_flag = false;
        }
        if (s7.equals("True"))
        {
            enable_statistics = true;
        }
        if (s7.equals("False"))
        {
            enable_statistics = false;
        }
    }


    /**
     * ��ȡ����������Ե��ַ���,������ʾ��
     * @return ����һ���ַ�����
     */
    public String getAttribute()
    {
        return exportAttributeJPanel.getAttribute()[0];
    }


    /**
     * ��ȡ����������Ե��ַ���������������
     * @return ����һ���ַ�����
     */
    public String getAttribute1()
    {
        return exportAttributeJPanel.getAttribute()[1];
    }


    /**
     * ����״̬����
     */
    public void setStatusBar()
    {
        String s = exportAttributeJPanel.getAttribute()[0];
        if(s==null||s.length()==0||s.equals(""))
        {
            s = " ";
        }
        statusJLabel.setText(s);
    }


    /**
     * ���������ѡ��״̬��
     * @param s1  "ȫ��"�Ƿ�ѡ�С�
     * @param s2  "�ּ�"�Ƿ�ѡ�С�
     * @param s3  "ͳ�Ʊ�"�Ƿ�ѡ�С�
     * @param s4  "����"�Ƿ�ѡ�С�
     * @param s5  "��Դ"�Ƿ񱻼��
     * @param s6  "����"�Ƿ񱻼��
     * @param s7  "ͳ�Ʊ�"�Ƿ񱻼��
     */
    public void setSelected(boolean s1, boolean s2, boolean s3, boolean s4,
                            boolean s5, boolean s6, boolean s7)
    {
        allJRadioButton.setSelected(s1); //(all_flag);
        gradeJRadioButton.setSelected(s2); //(grade_flag);
        statisticsJRadioButton.setSelected(s3); //(statistics_flag);
        classifyJRadioButton.setSelected(s4); //(classify_flag);
        sourceJComboBox.setEnabled(s5); //(source_flag);
        typeJomboBox.setEnabled(s6); //(type_flag);
        statisticsJRadioButton.setEnabled(s7); //(enable_statistics);
    }


    /**
     * ��ʾ���档
     */
    public void showView()
    {
        setVisible(true);
    }


    /**
     * ���ؽ��档
     */
    public void hideView()
    {
        setVisible(false);
    }


    /**
     * ������档
     */
    public void activateView()
    {
        setEnabled(true);
    }


    /**
     * �䶳���档
     */
    public void passivateView()
    {
        setEnabled(false);
    }


    /**
     * �رս��档
     */
    public void closeView()
    {
        dispose();
    }


    /**
     * ��ý����ϵ�¼����Ϣ��
     * @return ¼����Ϣ�ļ��ϡ�
     */
    public Collection getOutput()
    {
        Vector v = new Vector();
        //�����ѡ�����Դ
        String source = sourceJComboBox.getSelectedItem().toString();
        PartDebug.trace(PartDebug.PART_CLIENT,
                        ">>>>>>>>>>>>!!!!!!!! source = " + source);
        PartDebug.trace(PartDebug.PART_CLIENT,
                        ":::::::::::::::::: size = " + producedBy.length);

        //���ѡ���ǿհ��Ĭ�ϣ������򼯺��м�һ�����ַ��������˳���ѭ����
        //���ѡ����ĳһ��Դ�����򼯺ϼ������Դ
        for (int i = 0; i < producedBy.length; i++)
        {
            if (source.equals(""))
            {
                PartDebug.trace(PartDebug.PART_CLIENT,
                                "------s-----null--------------");
                v.add("");
                break;
            }
            else
            {
                if (source == producedBy[i].getDisplay(Locale.CHINA))
                {
                    PartDebug.trace(PartDebug.PART_CLIENT,
                                    ">>>>>>>>>>>>!!!!!!!! source = " +
                                    producedBy[i]);
                    v.add(producedBy[i]);
                }
            }

        }

        //��õ�ǰѡ�е�����
        String type = typeJomboBox.getSelectedItem().toString();
        PartDebug.trace(PartDebug.PART_CLIENT,
                        ">>>>>>>>>>>>!!!!!!!! type = " + type);

        //���ѡ���ǿհ��Ĭ�ϣ������򼯺��м�һ�����ַ��������˳���ѭ����
        //���ѡ����ĳһ���ͣ����򼯺��м��������
        for (int i = 0; i < partType.length; i++)
        {
            if (type.equals(""))
            {
                PartDebug.trace(PartDebug.PART_CLIENT,
                                "------t-----null--------------");
                v.add("");
                break;
            }
            else
            {
                if (type == partType[i].getDisplay(Locale.CHINA))
                {
                    PartDebug.trace(PartDebug.PART_CLIENT,
                                    ">>>>>>>>>>>>!!!!!!!! type = " + partType[i]);
                    v.add(partType[i]);
                }
            }
        }

        return v;
    }


    /**
     * ���½���(ģ�ͷ����仯ʱ֪ͨ���棬�ú���������)��
     * @param observable ���۲��߶���
     * @param obj ���۲�������Ĳ�����
     */
    public void update(Observable observable, Object obj)
    {
        Vector c = (Vector) obj;
        Iterator i = c.iterator();
        all_String = (String) i.next();
        grade_String = (String) i.next();
        statistics_String = (String) i.next();
        classify_String = (String) i.next();
        source_String = (String) i.next();
        type_String = (String) i.next();
        statistics_Enable = (String) i.next();
        setFlag(all_String, grade_String, statistics_String, classify_String,
                source_String, type_String, statistics_Enable);
        setSelected(all_flag, grade_flag, statistics_flag, classify_flag,
                    source_flag, type_flag, enable_statistics);
    }


    /**
     * �����ʼ��������
     * @param params ��������ļ��ϡ�
     */
    public void initView(Collection params)
    {
        //���ð�ť�ļ�����
        allJRadioButton.addActionListener(controller);
        gradeJRadioButton.addActionListener(controller);
        statisticsJRadioButton.addActionListener(controller);
        classifyJRadioButton.addActionListener(controller);
        okJButton.addActionListener(controller);
        cancelJButton.addActionListener(controller);
        //CCBegin by liunan 2008-08-12
        exportButton.addActionListener(controller);
        //CCEnd by liunan 2008-08-12
    }
}
