package com.faw_qm.part.client.design.view;
//SS1 ����BOM��������� liuyang 2014-6-20
//SS2 ���������汾 xianglx 2014-8-12
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;

import com.faw_qm.auth.RequestHelper;
import com.faw_qm.clients.beans.AssociationsPanel;
import com.faw_qm.clients.beans.explorer.Explorable;
import com.faw_qm.clients.beans.explorer.QMExplorer;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.client.main.view.QMPartList;
import com.faw_qm.part.client.main.view.QMProductManagerJFrame;
import com.faw_qm.part.model.PartAttrSetInfo;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartDescriptionList;
import com.faw_qm.part.util.PartReferenceList;
import com.faw_qm.part.util.PartServiceRequest;
import com.faw_qm.part.util.PartUsageList;
import com.faw_qm.part.util.TechnicsRegulateionList;
import com.faw_qm.part.util.TechnicsSummaryList;
import com.faw_qm.part.util.TechnicsRouteList;

/**
 * <p>Title:tabҳ��ʾ�������öԻ���</p>
 * <p>Description:�˽������tabҳ���ƣ���ʾ����б���
 * �ɸ�����Ҫʵ�����ԵĶ��ƺ�ˢ����ʾ��</p>
 * @author ������������
 *
 */
public class PartAttrSetJDialog extends JDialog
{
	/** ���л�ID */
	static final long serialVersionUID = 1L;
	/** ����� */
	private JPanel mainJPanel = new JPanel();

	/** ��ѡ�����Թ������ */
	private JScrollPane mayOutPutJScrollPane = new JScrollPane();

	/** �������Թ������ */
	private JScrollPane outPutJScrollPane = new JScrollPane();

	/** ����ѡ�����ԡ���ǩ */
	private JLabel mayOutputJLabel = new JLabel();

	/** ���������ԡ���ǩ */
	private JLabel outPutJLabel = new JLabel();

	/**���ɾ����ť��� */
	private JPanel add_deleteJPanel = new JPanel();
	
	/** ������԰�ť�����ƣ� */
	private JButton addAttributeJButton = new JButton();

	/** ȫ����Ӱ�ť�����ƣ� */
	private JButton addAllJButton = new JButton();

	/** ɾ�����԰�ť�����ƣ� */
	private JButton deleteAttriJButton = new JButton();

	/** ɾ��ȫ����ť�����ƣ� */
	private JButton deleteAllJButton = new JButton();

	/** ��ѡ�������б� */
	private JList mayOutPutJList = new JList();

	/** ���������б� */
	private JList outPutJList = new JList();
	
	/**������λ��ť��� */
	private JPanel up_downJPanel = new JPanel();

	/** �������ư�ť */
	private JButton upMoveJButton = new JButton();

	/** �������ư�ť */
	private JButton downMoveJButton = new JButton();
	
	/**�㲿�����*/
    private String number = PartUsageList.toPartUsageList("number").getDisplay();
    
	/**�㲿����ͼ����*/
    private String viewName = PartUsageList.toPartUsageList("viewName").getDisplay();
    
    /**�㲿���汾*/
    private String version = PartUsageList.toPartUsageList("iterationID").getDisplay();
    
    /**�㲿������*/
    private String quantity = PartUsageList.toPartUsageList("quantityString").getDisplay();
    
    /**�㲿����λ*/
    private String unit = PartUsageList.toPartUsageList("unitName").getDisplay();
    
    /**�����ĵ����*/
    private String descNum = PartDescriptionList.toPartDescriptionList("docNum").getDisplay();
    
    /**�����ĵ��汾*/
    private String descVersion = PartDescriptionList.toPartDescriptionList("versionID").getDisplay();
    
	/**�ο��ĵ����*/
    private String refNum = PartReferenceList.toPartReferenceList("number").getDisplay();
    
    /**����·�߱��*/
    private String routeNum = TechnicsRouteList.toTechnicsRouteList("routeListNumber").getDisplay();
    
    /**���ջ��ܱ��*/
    private String summaryNum = TechnicsSummaryList.toTechnicsSummaryList("tecTotalNumber").getDisplay();
    
    /**���չ�̱��*/
    private String regulationNum = TechnicsRegulateionList.toTechnicsRegulateionList("technicsNumber").getDisplay();
    //CCBegin SS1
    /**�㲿�������*/
    private String subUnitNumber = PartUsageList.toPartUsageList("subUnitNumber").getDisplay();
    
    /**BOM����*/
    private String bomItem = PartUsageList.toPartUsageList("bomItem").getDisplay();
    //CCEnd SS1
    //CCBegin SS2
    private String proVersion = PartUsageList.toPartUsageList("proVersion").getDisplay();
    //CCEnd SS2
	/**���ư�ť���*/
    private JPanel buttonJPanel = new JPanel();
    
    /**ȷ����ť*/
    private JButton okJButton = new JButton();

    /**ȡ����ť*/
    private JButton cancelJButton = new JButton();
    
    /**���ֹ�����*/
    private GridBagLayout gridBagLayout = new GridBagLayout();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();    
    
    /**��Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.part.client.design.util.PartDesignViewRB";
    /**ö����*/
	private EnumeratedType[] partTabList ;	
	
    /**����Tabҳ������*/
    private String TabName;
    /**��ѡ�����Ե�����*/
    private String[] mayOutPutAttriName;
    
    /**�������Ե�Ĭ��ֵ����ţ���ͼ���汾��������*/

	private String[] outPutAttriName ={number,viewName,version,quantity,unit,subUnitNumber,bomItem};


	/**��ѡ���������ƵĻ���*/
    private Vector leftName=new Vector();
	/**�����������ƵĻ���*/
    private Vector rightName=new Vector();
	/**�����*/
    protected QMExplorer myExplorer = null;
	/**ʹ�ýṹ�б�*/
    private QMPartList usageList = null;
    /**�����ĵ��Ͳο��ĵ��б�*/
    private PartMultiList refList = null;
    private AssociationsPanel descPanel =null;
    /**��������б�*/
    private PartForCappList cappList = null;
    
    
    /**
     * Ĭ�Ϲ��캯��
     */
	public PartAttrSetJDialog()
	{
		this("",null);
	}
  
	/**
     * �жϾ���tabҳ�Ĺ��캯��
     * @param s tab����
     */
	public PartAttrSetJDialog(String s,Component component)
	{
		super((JFrame)component,true);
		myExplorer=((QMProductManagerJFrame)component).getMyExplorer().getExplorer();
		TabName =s ;
		if(TabName.equals("usage"))
		{
			usageList = (QMPartList) myExplorer.getList();
		}
		if(TabName.equals("description"))
		{
			descPanel = myExplorer.getPartTaskJPanel().getDescJPanel().getDescPanel();		
		}
		if(TabName.equals("reference"))
		{
			refList = myExplorer.getPartTaskJPanel().getRefJPanel().getRefDocList();
		}
		if(TabName.equals("route"))
		{
			cappList = myExplorer.getPartTaskJPanel().getRouteJPanel().getRouteList();
		}
		if(TabName.equals("summary"))
		{
			cappList = myExplorer.getPartTaskJPanel().getSummaryJPanel().getSummaryList();
		}
		if(TabName.equals("regulation"))
		{
			cappList = myExplorer.getPartTaskJPanel().getRegulationJPanel().getRegulationList();
		}
		try
		{
			getListCollectAttributes();			
        	jbInit();
		}
		 catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	}
	
    
    /**
     * �����ʾ����������������
     */
    private void getListCollectAttributes()
    throws QMException
    {
    	String[] TabList=null;
    	if(TabName.equals("usage"))
    	{
    		partTabList=PartUsageList.getPartUsageListSet();
    		TabList=PartServiceRequest.getListHeadEn(TabName);
    	}
    	else if(TabName.equals("description"))
    	{
    		partTabList= PartDescriptionList.getPartDescriptionListSet();
			TabList=PartServiceRequest.getListHeadEn(TabName);
    	}
    	else if(TabName.equals("reference"))
    	{
    		partTabList= PartReferenceList.getPartReferenceListSet();
    		TabList = PartServiceRequest.getListHeadEn(TabName);
    	}
    	else if(TabName.equals("route"))
    	{
    		partTabList= TechnicsRouteList.getTechnicsRouteListSet();
    		TabList = PartServiceRequest.getListHeadEn(TabName);
    	}
    	else if(TabName.equals("summary"))
    	{
    		partTabList= TechnicsSummaryList.getTechnicsSummaryListSet();
    		TabList = PartServiceRequest.getListHeadEn(TabName);
    	}
    	else if(TabName.equals("regulation"))
    	{
    		partTabList= TechnicsRegulateionList.getTechnicsRegulateionListSet();
    		TabList = PartServiceRequest.getListHeadEn(TabName);
    	}
    	
    	outPutAttriName=TabList;
    	Vector ls=new Vector();
    	for(int m=0;m<TabList.length;m++)
    	{  
    		rightName.add(m, TabList[m]);
    	}
        int collectSize = partTabList.length;
       for(int a=0;a<collectSize;a++)
       {
    	   ls.addElement(partTabList[a].getValue());
       }
        for (int i = 0; i < collectSize; i++)
        {   
        	for(int j=0;j<TabList.length;j++)
        	{
        		String name=TabList[j];
        		if(partTabList[i].getValue().equals(name))
        			ls.removeElement(name);
        	}        	
        }
        mayOutPutAttriName = new String[ls.size()];
        Iterator ite=ls.iterator();
        for(int n=0;n<ls.size();n++)
        {
        	String s=ite.next().toString();
        	mayOutPutAttriName[n]=s;
        	leftName.add(n, s);
        }
       
    }	
	
	/**
     * ��ʼ��
     * @throws Exception
     */
    private void jbInit()throws Exception
    {
    	mayOutputJLabel.setFont(new java.awt.Font("Dialog", 0, 12));
        outPutJLabel.setFont(new java.awt.Font("Dialog", 0, 12));

        this.setLayout(gridBagLayout);
        this.setSize(600,400);
        mainJPanel.setLayout(gridBagLayout1);
        mayOutputJLabel.setText("mayOutput");
        mayOutputJLabel.setBounds(new Rectangle(20, 10, 75, 19));
        outPutJLabel.setText("outPut");
        outPutJLabel.setBounds(new Rectangle(236, 10, 75, 19));
        addAttributeJButton.setBounds(new Rectangle(180, 29, 50, 29));
        addAttributeJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        addAttributeJButton.setMaximumSize(new Dimension(100, 23));
        addAttributeJButton.setMinimumSize(new Dimension(100, 23));
        addAttributeJButton.setPreferredSize(new Dimension(100, 23));
        addAttributeJButton.setText("");
        addAttributeJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addAttributeJButton_actionPerformed(e);
            }
        });
        addAllJButton.setBounds(new Rectangle(180, 78, 50, 29));
        addAllJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        addAllJButton.setMaximumSize(new Dimension(100, 23));
        addAllJButton.setMinimumSize(new Dimension(100, 23));
        addAllJButton.setPreferredSize(new Dimension(100, 23));
        addAllJButton.setText("");
        addAllJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addAllJButton_actionPerformed(e);
            }
        });
        deleteAttriJButton.setBounds(new Rectangle(180, 127, 50, 29));
        deleteAttriJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        deleteAttriJButton.setMaximumSize(new Dimension(100, 23));
        deleteAttriJButton.setMinimumSize(new Dimension(100, 23));
        deleteAttriJButton.setPreferredSize(new Dimension(100, 23));
        deleteAttriJButton.setMnemonic('0');
        deleteAttriJButton.setText("");
        deleteAttriJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteAttriJButton_actionPerformed(e);
            }
        });
        deleteAllJButton.setBounds(new Rectangle(180, 176, 50, 29));
        deleteAllJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        deleteAllJButton.setMaximumSize(new Dimension(100, 23));
        deleteAllJButton.setMinimumSize(new Dimension(100, 23));
        deleteAllJButton.setPreferredSize(new Dimension(100, 23));
        deleteAllJButton.setText("");
        deleteAllJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteAllJButton_actionPerformed(e);
            }
        });
        upMoveJButton.setBounds(new Rectangle(400, 90, 50, 29));
        upMoveJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        upMoveJButton.setMaximumSize(new Dimension(100, 23));
        upMoveJButton.setMinimumSize(new Dimension(100, 23));
        upMoveJButton.setPreferredSize(new Dimension(100, 23));
        upMoveJButton.setToolTipText("");
        upMoveJButton.setText("up");
        upMoveJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                upMoveJButton_actionPerformed(e);
            }
        });
        downMoveJButton.setBounds(new Rectangle(400, 139, 51, 29));
        downMoveJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        downMoveJButton.setMaximumSize(new Dimension(100, 23));
        downMoveJButton.setMinimumSize(new Dimension(100, 23));
        downMoveJButton.setPreferredSize(new Dimension(100, 23));
        downMoveJButton.setText("down");
        downMoveJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                downMoveJButton_actionPerformed(e);
            }
        });
//        mainJPanel.setMinimumSize(new Dimension(1, 1));
//        mainJPanel.setPreferredSize(new Dimension(1, 1));
        mainJPanel.add(mayOutPutJScrollPane,
                       new GridBagConstraints(0, 1, 1, 4, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(0, 10, 0, 0), 500, 500));
        mainJPanel.add(mayOutputJLabel,
                new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 10, 5, 0), 0, 0));
        
        this.add(mainJPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0,
                0));
        mayOutPutJScrollPane.getViewport().add(mayOutPutJList, null);
        mainJPanel.add(outPutJScrollPane,
                       new GridBagConstraints(2, 1, 1, 4, 1.0, 1.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(0, 0, 0, 8), 500, 500));
        outPutJScrollPane.getViewport().add(outPutJList, null);
        mainJPanel.add(outPutJLabel,
                new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 0, 5, 0), 0, 0));
        mainJPanel.add(add_deleteJPanel,
                new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.NONE,
                                       new Insets(10, 5, 0, 5), 0, 0));
        add_deleteJPanel.setLayout(gridBagLayout2);
        
        add_deleteJPanel.add(addAttributeJButton,
                       new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(8, 10, 0, 10), 0, 0));
        add_deleteJPanel.add(addAllJButton,
                       new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(8, 10, 0, 10), 0, 0));
        add_deleteJPanel.add(deleteAttriJButton,
                       new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(8, 10, 0, 10), 0, 0));
        add_deleteJPanel.add(deleteAllJButton,
                      new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.NORTH,
                                       GridBagConstraints.NONE,
                                       new Insets(8, 10, 0, 10), 0, 0));
        up_downJPanel.setLayout(gridBagLayout3);
        mainJPanel.add(up_downJPanel,
                new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.NONE,
                                       new Insets(0, 0, 0, 0), 0, 0));
        up_downJPanel.add(upMoveJButton,
                       new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(5, 10, 5, 10), 0, 0));
        up_downJPanel.add(downMoveJButton,
                       new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
                                              , GridBagConstraints.CENTER,
                                              GridBagConstraints.NONE,
                                              new Insets(5, 10, 0, 10), 0, 0));
        buttonJPanel.setLayout(gridBagLayout4);
        this.add(buttonJPanel,
        		new GridBagConstraints(0, 2, 1, 1, 1.0,
                        0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE,
                        new Insets(0, 0, 0, 10), 0, 0));
        okJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        okJButton.setMaximumSize(new Dimension(75, 23));
        okJButton.setMinimumSize(new Dimension(75, 23));
        okJButton.setPreferredSize(new Dimension(75, 23));
        okJButton.setActionCommand("OK");
        okJButton.setMnemonic('Y');
        okJButton.setText("OK");
        okJButton.addActionListener(new java.awt.event.ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		okJButton_actionPerformed(e);
        	}
        });
        cancelJButton.setFont(new java.awt.Font("Dialog", 0, 12));
        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setActionCommand("CANCEL");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("Cancel");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
			public void actionPerformed(ActionEvent e)
			{
				cancelJButton_actionPerformed(e);
				
			}
        	
        });
        buttonJPanel.add(okJButton,
        		new GridBagConstraints(0, 0, 1, 1, 0.0,
                        0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(10, 0, 10, 8), 0, 0));
        buttonJPanel.add(cancelJButton,
        		new GridBagConstraints(1, 0, 1, 1, 0.0,
                        0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                        new Insets(10, 0, 10, 10), 0, 0));

        localize();
        //��ʼ����ѡ�������б�Ͷ��������б�
        populateList();
        //������ʹ�ù���ˢ�½���
        refresh();
        
    }
    
    /**
     * ���ػ�
     */
    public void localize()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "localize begin ....");
        mayOutputJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "mayOutput", null));
        outPutJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE, "outPut", null));
        addAttributeJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "addAttribute", null));
        addAllJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "addAll", null));
        deleteAttriJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "deleteAttri", null));
        deleteAllJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "deleteAll", null));
        upMoveJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "upMove", null));
        downMoveJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                "downMove", null));
        okJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "ok", null));
        cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE, "cancel", null));
        String title = QMMessage.getLocalizedMessage(RESOURCE,"AttrSet", null);
        this.setTitle(title);
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "localize end....return is void");		
    }
	
    /**
     * ��ѡ�������б�Ͷ��������б�ĳ�ʼ������
     * @throws QMException 
     */
    public void populateList() throws QMException
    {
    	//��ѡ�������б�ģ��
        DefaultListModel mayExpModel = new DefaultListModel();    	
     
        for(int i = 0;i < mayOutPutAttriName.length;i++)
        {
        	
        	if(TabName.equals("usage"))
        	{
        		mayExpModel.addElement(PartUsageList.toPartUsageList(mayOutPutAttriName[i]).getDisplay()); 
        	}
        	else if(TabName.equals("description"))
        	{
        		mayExpModel.addElement(PartDescriptionList.toPartDescriptionList(mayOutPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("reference"))
        	{
        		mayExpModel.addElement(PartReferenceList.toPartReferenceList(mayOutPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("route"))
        	{
        		mayExpModel.addElement(TechnicsRouteList.toTechnicsRouteList(mayOutPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("summary"))
        	{
        		mayExpModel.addElement(TechnicsSummaryList.toTechnicsSummaryList(mayOutPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("regulation"))
        	{
        		mayExpModel.addElement(TechnicsRegulateionList.toTechnicsRegulateionList(mayOutPutAttriName[i]).getDisplay());
        	}
        	
        }
        //���ÿ�ѡ�������б�����ģ��
        mayOutPutJList.setModel(mayExpModel);
        mayOutPutJList.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                mayOutPutJList_keyPressed(e);
            }
        });
        //��ѡ�������б���굥������
        mayOutPutJList.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                mayOutPutJList_mouseClicked(e);
            }
        });
        //����ѡ�������б�������Ըı����
        mayOutPutJList.addPropertyChangeListener(new java.beans.
                                                 PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                mayOutPutJList_propertyChange(e);
            }
        });
        //�����������б�������Ըı����
        outPutJList.addPropertyChangeListener(new java.beans.
                                                 PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
            	outPutJList_propertyChange(e);
            }
        });
        //����ѡ�������б������Ŀѡ�����
        mayOutPutJList.addListSelectionListener(new javax.swing.event.
                                                ListSelectionListener()
        {
            public void valueChanged(ListSelectionEvent e)
            {
                mayOutPutJList_valueChanged(e);
            }
        });
        //���������б������ģ��
        DefaultListModel expModel = new DefaultListModel();       
        
        for(int i =0;i<outPutAttriName.length;i++)
        {
        	if(TabName.equals("usage"))
        	{
        		expModel.addElement(PartUsageList.toPartUsageList(outPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("description"))
        	{
        		expModel.addElement(PartDescriptionList.toPartDescriptionList(outPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("reference"))
        	{
        		expModel.addElement(PartReferenceList.toPartReferenceList(outPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("route"))
        	{
        		expModel.addElement(TechnicsRouteList.toTechnicsRouteList(outPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("summary"))
        	{
        		expModel.addElement(TechnicsSummaryList.toTechnicsSummaryList(outPutAttriName[i]).getDisplay());
        	}
        	else if(TabName.equals("regulation"))
        	{
        		expModel.addElement(TechnicsRegulateionList.toTechnicsRegulateionList(outPutAttriName[i]).getDisplay());
        	}
        	
        }
        //���ö��������б������ģ��
        outPutJList.setModel(expModel);
        //�����������б������굥������
        outPutJList.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                outPutJList_mouseClicked(e);
            }
        });

        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "populateList() end....return is void");
    }
    
    /**
     * ˢ�½���
     */
    public void refresh()
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT, "refresh() begin ....");
        //������б������ģ��
        DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
        /**��ѡ�������б����п�ѡ��ʱ,ȫ����Ӱ�ť��Ч��������Ч
         *��ѡ�������б����޿�ѡ��ʱ,��Ӱ�ť��Ч
         */
        if (mayExpModel.isEmpty() == true)
        {
            addAllJButton.setEnabled(false);
            addAttributeJButton.setEnabled(false);
        }
        else
        {
            addAttributeJButton.setEnabled(true);
            addAllJButton.setEnabled(true);
        }

        /**��ѡ�������б�����ѡ�ѡ��ʱ,��Ӱ�ť��Ч��������Ч*/
        for (int i = 0; i < mayExpModel.size(); i++)
        {
            if (mayOutPutJList.isSelectedIndex(i) == true)
            {
                addAttributeJButton.setEnabled(true);
            }
            else
            {
                addAttributeJButton.setEnabled(false);
            }
        }

        /**���������б����п�ѡ��(����ţ�����)ʱ,ɾ����ť��ȫ��ɾ����ť��Ч��������Ч
         */ 
        for (int i = 0; i < expModel.size(); i++)
        {
            String a = expModel.getElementAt(i).toString();
            if(TabName.equals("usage"))
            {
            	if (a != viewName && a != number && a != version && a != unit &&
            		a != quantity && expModel.isEmpty() != true)
                {
                    deleteAttriJButton.setEnabled(true);
                }
                else
                {
                    deleteAttriJButton.setEnabled(false);
                }
            }
            
            if(TabName.equals("description"))
            {
            	if (a != descNum && a != descVersion && expModel.isEmpty() != true)
                {
                    deleteAttriJButton.setEnabled(true);
                }
                else
                {
                    deleteAttriJButton.setEnabled(false);
                }
            }
            if(TabName.equals("reference"))
            {
            	if (a != refNum && expModel.isEmpty() != true)
                {
                    deleteAttriJButton.setEnabled(true);
                }
                else
                {
                    deleteAttriJButton.setEnabled(false);
                }            	
            }
            if(TabName.equals("route"))
            {
            	if (a != routeNum && expModel.isEmpty() != true)
                {
                    deleteAttriJButton.setEnabled(true);
                }
                else
                {
                    deleteAttriJButton.setEnabled(false);
                }             	
            }
            if(TabName.equals("summary"))
            {
            	if (a != summaryNum && expModel.isEmpty() != true)
                {
                    deleteAttriJButton.setEnabled(true);
                }
                else
                {
                    deleteAttriJButton.setEnabled(false);
                } 
            }
            if(TabName.equals("regulation"))
            {
            	if (a != regulationNum && expModel.isEmpty() != true)
                {
                    deleteAttriJButton.setEnabled(true);
                }
                else
                {
                    deleteAttriJButton.setEnabled(false);
                }             	
            }
        }

        /**���������б�����ѡ�ѡ��ʱ,ɾ����ť��Ч��������Ч*/
        for (int i = 0; i < expModel.size(); i++)
        {
            if (outPutJList.isSelectedIndex(i) == true)
            {
                deleteAttriJButton.setEnabled(true);
            }
            else
            {
                deleteAttriJButton.setEnabled(false);
            }
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "refresh() end....return is void");
    }
    
    /**
     * ��ѡ�������б����Ըı�����¼�����
     * <p>ֻ��ѡ���б���ĳһ���Ӱ�ť�ű�����</p>
     * @param e ��ѡ�������б����Ըı��¼�
     */
    void mayOutPutJList_propertyChange(PropertyChangeEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_propertyChange() begin ....");        

        if (mayOutPutJList.isSelectionEmpty() == true)
        {
            addAttributeJButton.setEnabled(false);
        }
        else
        {
            addAttributeJButton.setEnabled(true);
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_propertyChange() end....return is void");
    }

    /**
     * ���������б����Ըı�����¼�����
     * <p>ֻ��ѡ���б���ĳһ�ɾ����ť�ű�����</p>
     * @param e ��ѡ�������б����Ըı��¼�
     */
    void outPutJList_propertyChange(PropertyChangeEvent e)
    {
        if (outPutJList.isSelectionEmpty() == true)
        {
        	deleteAttriJButton.setEnabled(false);
        }
        else
        {
        	deleteAttriJButton.setEnabled(true);
        }        
    }

    /**
     * ��ѡ�������б�ֵ�ı�����¼�����
     * <p>�ü����¼�ʵ���˿�ѡ�������б�����ѡ�ѡ��ʱ,��Ӱ�ť��Ч��������Ч</p>
     * @param e �б�ѡȡ�¼�
     */
    void mayOutPutJList_valueChanged(ListSelectionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_valueChanged() begin ....");
        if (mayOutPutJList.isSelectionEmpty() == true)
        {
            addAttributeJButton.setEnabled(false);
        }
        else
        {
            addAttributeJButton.setEnabled(true);
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_valueChanged() end....return is void");
    }


    /**
     * ���������б���굥���¼�����     
     * @param e ��굥���¼�
     */
    void outPutJList_mouseClicked(MouseEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "outPutJList_mouseClicked() begin ....");
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
        Object[] selectedValues = outPutJList.getSelectedValues();
        //���������Ե�������
        int index = outPutJList.locationToIndex(e.getPoint());
        //���������������������
        String indexString = (String) expModel.getElementAt(index);
        //���ѡ�С��㲿����š��͡��㲿�����ơ�����ɾ����ťʧЧ��������Ч
        boolean isDelete = true;
        for(int i=0;i<selectedValues.length;i++)
        {
        	indexString = (String)selectedValues[i];
        	if(TabName.equals("usage"))
            {
            	if (indexString == viewName || indexString == number ||
            		indexString == version || indexString == quantity ||
            		indexString == unit)
                {
            		isDelete = false;
                }
            	deleteAttriJButton.setEnabled(isDelete);
            	
            	if(indexString == number || indexString == quantity || indexString == unit)
            	{
            		upMoveJButton.setEnabled(false);
            		downMoveJButton.setEnabled(false);
            	}
            	else
            	{
            		upMoveJButton.setEnabled(true);
            		downMoveJButton.setEnabled(true);
            	}
            	
            }
            
            if(TabName.equals("description"))
            {
            	if (indexString == descNum || indexString == descVersion)
                {
            		isDelete = false;
                }
            	deleteAttriJButton.setEnabled(isDelete);
            }
            
            if(TabName.equals("reference"))
            {
            	if (indexString == refNum )
                {
            		isDelete = false;
                }
            	deleteAttriJButton.setEnabled(isDelete);
            }
            if(TabName.equals("route"))
            {
            	if (indexString == routeNum )
                {
            		isDelete = false;
                }
            	deleteAttriJButton.setEnabled(isDelete);
            }
            if(TabName.equals("summary"))
            {
            	if (indexString == summaryNum )
                {
            		isDelete = false;
                }
            	deleteAttriJButton.setEnabled(isDelete);
            }
            if(TabName.equals("regulation"))
            {
            	if (indexString == regulationNum )
                {
            		isDelete = false;
                }
            	deleteAttriJButton.setEnabled(isDelete);
            }
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "outPutJList_mouseClicked() end....return is void ");
    }


    /**
     * ��ȫ����ӡ���ť����Ϊ�¼�����
     * @param e ��Ϊ�¼�
     */
    void addAllJButton_actionPerformed(ActionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "addAllJButton_actionPerformed���� begin ....");
        //��ÿ�ѡ�������б�����ģ��
        DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.
                                       getModel();
        //��ö��������б�����ģ��
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
        //��������������붨�������б�
        for (int i = 0; i < mayExpModel.getSize(); i++)
        {
            expModel.addElement(mayExpModel.getElementAt(i));
            
            rightName.addElement(leftName.elementAt(i));
        }
        //���������Դӿ�ѡ�������б�ɾ��
        mayExpModel.removeAllElements();
        leftName.removeAllElements();
        //ˢ�½���
        refresh();
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "addAllJButton_actionPerformed() end ....return is void");
    }


    /**
     * ��Ӱ�ť����Ϊ�¼�����
     * @param e ��Ϊ�¼�
     */
    void addAttributeJButton_actionPerformed(ActionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "addAttributeJButton_actionPerformed() begin ....");
        //��ÿ�ѡ�������б�����ѡ�е������������(��Ϊ�����ѡ)
        int[] selected = mayOutPutJList.getSelectedIndices();
        //��ÿ�ѡ�������б�����ģ��
        DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.getModel();
        //��ö��������б�����ģ��
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
        //���������б������ѡ�е����ԣ�ͬʱ�������Դӿ�ѡ�������б���ɾ��
        for (int i = 0; i < selected.length; i++)
		{
			String temp = mayExpModel.getElementAt(selected[i] - i).toString();

			mayExpModel.removeElementAt(selected[i] - i);			

			expModel.insertElementAt(temp, expModel.size());
			rightName.addElement(leftName.elementAt(selected[i] - i));
			leftName.removeElementAt(selected[i] - i);
		}

		// ˢ�½���
        refresh();
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "addAttributeJButton_actionPerformed() end ....return is void");
    }


    /**
     * ɾ����ť����Ϊ�¼�����
     * @param e ��Ϊ�¼�
     */
    void deleteAttriJButton_actionPerformed(ActionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "deleteAttriJButton_actionPerformed���� begin ....");
        //��ö��������б�����ѡ�е������������(��Ϊ�����ѡ)
        int[] selected = outPutJList.getSelectedIndices();
        //��ÿ�ѡ�������б�����ģ��
        DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.
                                       getModel();
        //��ö��������б�����ģ��
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
        //���ѡ�������б������ѡ�е����ԣ�ͬʱ�������ԴӶ��������б���ɾ��
        for (int i = 0; i < selected.length; i++)
		{
			String temp = expModel.getElementAt(selected[i] - i).toString();
			mayExpModel.insertElementAt(temp, mayExpModel.size());
			expModel.removeElementAt(selected[i] - i);
			leftName.add(leftName.size(), rightName.elementAt(selected[i] - i));
			rightName.removeElementAt(selected[i] - i);
			
		}
        refresh();       
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "deleteAttriJButton_actionPerformed���� end....return is void");
    }


    /**
     * ȫ��ɾ����ť����Ϊ�¼�����
     * @param e ��Ϊ�¼�
     */
    void deleteAllJButton_actionPerformed(ActionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "deleteAllJButton_actionPerformed���� begin ....");
        //��ÿ�ѡ�������б�����ģ��
        DefaultListModel mayExpModel = (DefaultListModel) mayOutPutJList.
                                       getModel();
        //��ö��������б�����ģ��
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();
        //�Ѷ��������б��е��������ԣ���ȥ��ź����ƣ�������ѡ�������б�
        for (int i = 0; i < expModel.size(); i++)
		{
        	String a = expModel.getElementAt(i).toString();
        	if(TabName.equals("usage"))
        	{
        		if (a != viewName && a != number &&
        			a !=version && a != quantity && a!= unit)
                {
                	mayExpModel.addElement(expModel.getElementAt(i));
                	leftName.add(leftName.size(),rightName.elementAt(i));
                }
        	}
            
        	if(TabName.equals("description"))
        	{
        		if (a != descNum && a != descVersion)
                {
                	mayExpModel.addElement(expModel.getElementAt(i));
                	leftName.add(leftName.size(),rightName.elementAt(i));
                }
        	}
        	
        	if(TabName.equals("reference"))
        	{
        		if (a != refNum )
                {
                	mayExpModel.addElement(expModel.getElementAt(i));
                	leftName.add(leftName.size(),rightName.elementAt(i));
                }
        	}
        	if(TabName.equals("route"))
        	{
        		if (a != routeNum )
                {
                	mayExpModel.addElement(expModel.getElementAt(i));
                	leftName.add(leftName.size(),rightName.elementAt(i));
                }
        	}
        	if(TabName.equals("summary"))
        	{
        		if (a != summaryNum )
                {
                	mayExpModel.addElement(expModel.getElementAt(i));
                	leftName.add(leftName.size(),rightName.elementAt(i));
                }
        	}
        	if(TabName.equals("regulation"))
        	{
        		if (a != regulationNum )
                {
                	mayExpModel.addElement(expModel.getElementAt(i));
                	leftName.add(leftName.size(),rightName.elementAt(i));
                }
        	}
		}        
        //ɾ�����������б��е���������
        expModel.removeAllElements();
        rightName.removeAllElements();
        //���¼��ϱ�ź��������ԣ���Ϊ�������Ǳ�ѡ��
        if(TabName.equals("usage"))
        {
        	expModel.addElement(number);            
            expModel.addElement(quantity);
            expModel.addElement(unit);
            expModel.addElement(viewName);
            expModel.addElement(version);
            rightName.addElement("number");            
            rightName.addElement("quantityString");
            rightName.addElement("unitName");
            rightName.addElement("viewName");
            rightName.addElement("iterationID");
        }
        if(TabName.equals("description"))
        {
        	expModel.addElement(descNum);
            expModel.addElement(descVersion);
            rightName.addElement("docNum");
            rightName.addElement("versionID");
        }
        if(TabName.equals("reference"))
        {
        	expModel.addElement(refNum);
        	rightName.addElement("number");
        }
        if(TabName.equals("route"))
        {
        	expModel.addElement(routeNum);
        	rightName.addElement("routeListNumber");
        }
        if(TabName.equals("summary"))
        {
        	expModel.addElement(summaryNum);
        	rightName.addElement("tecTotalNumber");
        }
        if(TabName.equals("regulation"))
        {
        	expModel.addElement(regulationNum);
        	rightName.addElement("technicsNumber");
        }
        
        refresh();
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "deleteAllJButton_actionPerformed���� end....return is void");
    }


    /**
     * �����ƶ���ť����Ϊ�¼�����
     * @param e ��Ϊ�¼�
     */
    void upMoveJButton_actionPerformed(ActionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "upMoveJButton_actionPerformed() begin ....");
        //��ö��������б�����ѡ�е������������(��Ϊ�����ѡ)
        int[] selected = outPutJList.getSelectedIndices();
        //��ö��������б�����ģ��
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();

        for (int i = 0; i < selected.length; i++)
        {
            if (selected[i] > 0)
            {

                /**���Ҫ�ı�λ�õ���������*/
                String a = expModel.getElementAt(selected[i]).toString();
                String b = expModel.getElementAt(selected[i] - 1).toString();
                //a1,b1Ϊ������Ԫ��
                String a1 = rightName.elementAt(selected[i]).toString();
                String b1 = rightName.elementAt(selected[i] - 1).toString();
                if( b != number && b !=quantity && b != unit)
                {
                	/**���ߵ�ֵ�������ݽ���*/
                    String temp;
                    temp = b;
                    b = a;
                    a = temp;
                    String temp1;
                    temp1 = b1;
                    b1 = a1;
                    a1 = temp1;
                    /**���ߵ�ֵ����λ�ý���*/
                    expModel.setElementAt(a, selected[i]);
                    expModel.setElementAt(b, selected[i] - 1);
                    rightName.setElementAt(a1, selected[i]);
                    rightName.setElementAt(b1, selected[i] - 1);
                    /**�����ѡ��λ������һ����λ*/
                    outPutJList.setSelectedIndex(selected[i] - 1);
                }
            }
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "upMoveJButton_actionPerformed() end....return is void");
    }

    /**
     * �����ƶ���ť����Ϊ�¼�����
     * @param e ��Ϊ�¼�
     */
    void downMoveJButton_actionPerformed(ActionEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "downMove_jButton_actionPerformed begin ....");
        //��ö��������б�����ѡ�е������������(��Ϊ�����ѡ)
        int[] selected = outPutJList.getSelectedIndices();
        //��ö��������б�����ģ��
        DefaultListModel expModel = (DefaultListModel) outPutJList.getModel();

        for (int i = 0; i < selected.length; i++)
        {
            if (selected[i] + 1 <= expModel.size() - 1)
            {
                /**���Ҫ�ı�λ�õ���������*/
                String a = expModel.getElementAt(selected[i]).toString();
                String b = expModel.getElementAt(selected[i] + 1).toString();
                //a1,b1Ϊ������Ԫ��
                String a1 = rightName.elementAt(selected[i]).toString();
                String b1 = rightName.elementAt(selected[i] + 1).toString();
                if ( a != number && a != quantity && a != unit)
                {
                	/**���ߵ�ֵ�������ݽ���*/
                    String temp;
                    temp = b;
                    b = a;
                    a = temp;
                    String temp1;
                    temp1 = b1;
                    b1 = a1;
                    a1 = temp1;
                    /**���ߵ�ֵ����λ�ý���*/
                    expModel.setElementAt(a, selected[i]);
                    expModel.setElementAt(b, selected[i] + 1);
                    rightName.setElementAt(a1, selected[i]);
                    rightName.setElementAt(b1, selected[i] + 1);
                    /**�����ѡ��λ������һ����λ*/
                    outPutJList.setSelectedIndex(selected[i] + 1);
                }
            }
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                "downMove_jButton_actionPerformed end....return is void");
    }
    
    /**
     * ȡ����ť����Ϊ�¼�����
     * @param e ��Ϊ�¼�
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
    	dispose();
    }
        
    /**
     * ȷ����ť����Ϊ�¼�����
     * @param e ��Ϊ�¼�
     */   
    void okJButton_actionPerformed(ActionEvent event)
    {	     
        RequestHelper helper = new RequestHelper();
        String STANDARD = "StandardPartService";
    	Class[] paraClass = {};
	    Object[] para = {};
	    try
		{
			PartAttrSetInfo info=(PartAttrSetInfo)  helper.request(STANDARD,"getCurUserInfo", paraClass, para);
			String title=null;			
	        for(Iterator ite=rightName.iterator();ite.hasNext();)
	        {			        	
	        	if(title==null)
	        		title=ite.next().toString();
	        	else
	        		title =title+","+ite.next().toString();		        	
	        }
	        if(TabName.equals("usage"))
	        {
	        	info.setUsageAttr(title);
	        }
	        if(TabName.equals("description"))
	        {
	        	info.setDescriptionAttr(title);
	        }
	        if(TabName.equals("reference"))
	        {
	        	info.setReferenceAttr(title);
	        }
	        if(TabName.equals("route"))
	        {
	        	info.setCappRoute(title);
	        }
	        if(TabName.equals("summary"))
	        {
	        	info.setCappSummary(title);
	        }
	        if(TabName.equals("regulation"))
	        {
	        	info.setCappRegulation(title);
	        }
	        //�־û���������
	    	Class[] paraClass1 = {BaseValueIfc.class};
	 	    Object[] para1 = {info};
	        info=(PartAttrSetInfo)  helper.request("PersistService","saveValueInfo", paraClass1, para1);		        
		} 
	    catch (QMException e)
		{
			e.printStackTrace();
		}	    
	    try
		{
			refreshList();
		} catch (QMException e)
		{
			e.printStackTrace();
		}
	    dispose();
    }
    
    /**
     * ˢ��tabҳ�ķ���,���µ��û�ȡ�б�ķ���
     * @throws QMException
     */
    public void refreshList() throws QMException
    {    	
    	if(TabName.equals("usage"))
    	{
    		String[] listHead=PartServiceRequest.getListHead("usage");
    		String[] headMethod=PartServiceRequest.getListMethod("usage");    		
    		int i = listHead.length;
    		//CCBegin SS1
    		//CCBegin SS2
    	//	int[] in=new int[10];
    		//int[] in=new int[12];
    		int[] in=new int[13];
    		//CCEnd SS2
    		//CCEnd SS1
    		for(int j=0;j<i;j++)    
            {
            	in[j]=4;
            }
    		//CCBegin SS1
    		//CCBegin SS2
          //  for(int k=i;k<10;k++)
            //for(int k=i;k<12;k++)
            for(int k=i;k<13;k++)
            	//CCEnd SS2
            	//CCEnd SS1
            {
            	
           	 in[k]=0;             
            }
            usageList.setRelColWidth(in);	
            usageList.adjustWidth();
            usageList.setHeadings(listHead);
    		try
    		{
    			//myExplorer.setListMethods(headMethod);    		
    			usageList.setHeadingMethods(headMethod);    		
    			Explorable myexplorable[]=usageList.getMyExplorable(); 
    			if(myexplorable!=null)
        		{    
//    				myExplorer.getPartTaskJPanel().setListEditor();
//        			myExplorer.getPartTaskJPanel().setValues();
        			for(int l=0;l<myexplorable.length;l++)    		
        			usageList.removeDetail(myexplorable[l]);    		
        			usageList.addDetail(myexplorable);
        			
        		}    		
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
        	usageList.getTable().getColumnModel().getColumn(i).setMaxWidth(0);
        	usageList.getTable().getColumnModel().getColumn(i).setPreferredWidth(0);
        	usageList.getTable().getTableHeader().getColumnModel().getColumn(i).setMinWidth(0);     
            
    	} 
    	
    	if(TabName.equals("description"))
    	{
    		String[] head=PartServiceRequest.getListHead("description");    		
    		String[] headMethod=PartServiceRequest.getListMethod("description");
    		//epmMethod
    		String[] headingMethodEPM = null;
    		int m = head.length;
            try
			{
				descPanel.setLabels(head);
			} catch (PropertyVetoException e1)
			{
				e1.printStackTrace();
			}
            descPanel.getMultiList().adjustWidth();            

    		try
			{   
    			//��epm�ĵ�����������ȡ���Ӧ����ֵ
    			headingMethodEPM = new String[headMethod.length];
    			for(int i=0;i<headMethod.length;i++)
    	         {
    	        	 if(headMethod[i].equals("getDocNum"))
    	        	 {
    	        		 headingMethodEPM[i]="getDocNumber";
    	        	 }
    	        	 else if(headMethod[i].equals("getDocName")||headMethod[i].equals("getVersionID"))
    	        	 {
    	        		 headingMethodEPM[i]=headMethod[i];
    	        	 }
    	         }
    			descPanel.setHeadingMethodsEPM(headingMethodEPM);
    			//end
    			descPanel.setOtherSideAttributes(headMethod);    			
    			descPanel.refreshDesc();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			
			int size=descPanel.getMultiList().getTable().getTableHeader().getColumnModel().getColumn(0).getWidth();	        
			for(int j=0;j<m;j++)
			{
				descPanel.getMultiList().getTable().getColumnModel().getColumn(j).setMaxWidth(size);
				descPanel.getMultiList().getTable().getColumnModel().getColumn(j).setPreferredWidth(size);
				descPanel.getMultiList().getTable().getTableHeader().getColumnModel().getColumn(j).setMinWidth(size);
			}	
			descPanel.getMultiList().getTable().getColumnModel().getColumn(m).setMaxWidth(0);
			descPanel.getMultiList().getTable().getColumnModel().getColumn(m).setPreferredWidth(0);
			descPanel.getMultiList().getTable().getTableHeader().getColumnModel().getColumn(m).setMinWidth(0);
    	}
    	
    	if(TabName.equals("reference"))
    	{
    		String[] listHead = PartServiceRequest.getListHead("reference");
    		String[] headMethod=PartServiceRequest.getListMethod("reference");
    		int[] colw =new int[3];
            for(int i = 0;i<listHead.length;i++)
            {
            		colw[i]=1;
            }
            colw[listHead.length]=0;
            refList.setRelColWidth(colw);
            refList.adjustWidth();
            refList.setHeadings(listHead);
    		try
    		{   			
    			refList.setHeadingMethods(headMethod);
    			refList.setListItems(refList.getDocumentItems());
    		}
    		catch(PropertyVetoException e)
    		{
    			e.printStackTrace();
    		}
    		int size=refList.getTable().getTableHeader().getColumnModel().getColumn(0).getWidth();
			for(int j=0;j<listHead.length;j++)
			{
				refList.getTable().getColumnModel().getColumn(j).setMaxWidth(size);
				refList.getTable().getColumnModel().getColumn(j).setPreferredWidth(size);
				refList.getTable().getTableHeader().getColumnModel().getColumn(j).setMinWidth(size);					
			}	
    		refList.getTable().getColumnModel().getColumn(listHead.length).setMaxWidth(0);
        	refList.getTable().getColumnModel().getColumn(listHead.length).setPreferredWidth(0);
        	refList.getTable().getTableHeader().getColumnModel().getColumn(listHead.length).setMinWidth(0);
    	}
    	
    	if(TabName.equals("route"))
    	{
    		String[] listHead = PartServiceRequest.getListHead("route");
    		String[] headMethod=PartServiceRequest.getListMethod("route");
    		int[] colw =new int[6];
            for(int i = 0;i<listHead.length;i++)
            {
            		colw[i]=1;
            }
            colw[listHead.length]=0;
            cappList.setRelColWidth(colw);
            cappList.adjustWidth();
            cappList.setHeadings(listHead);
            try
    		{   			
    			cappList.setHeadingMethods(headMethod);
    			cappList.setListItems(cappList.getTechnicsRouteListInfo());
    		}
    		catch(PropertyVetoException e)
    		{
    			e.printStackTrace();
    		}
    		cappList.getTable().getColumnModel().getColumn(listHead.length).setMaxWidth(0);
        	cappList.getTable().getColumnModel().getColumn(listHead.length).setPreferredWidth(0);
        	cappList.getTable().getTableHeader().getColumnModel().getColumn(listHead.length).setMinWidth(0);
    	}
    	
    	if(TabName.equals("summary"))
    	{
    		String[] listHead = PartServiceRequest.getListHead("summary");
    		String[] headMethod = PartServiceRequest.getListMethod("summary");
    		int[] colw =new int[5];
            for(int i = 0;i<listHead.length;i++)
            {
            		colw[i]=1;
            }
            colw[listHead.length]=0;
            cappList.setRelColWidth(colw);
            cappList.adjustWidth();
            cappList.setHeadings(listHead);
            try
    		{   			
    			cappList.setHeadingMethods(headMethod);
    			cappList.setListItems(cappList.getTotalSchemaItemInfo());
    		}
    		catch(PropertyVetoException e)
    		{
    			e.printStackTrace();
    		}	
    		cappList.getTable().getColumnModel().getColumn(listHead.length).setMaxWidth(0);
        	cappList.getTable().getColumnModel().getColumn(listHead.length).setPreferredWidth(0);
        	cappList.getTable().getTableHeader().getColumnModel().getColumn(listHead.length).setMinWidth(0);
    	}
    	
    	if(TabName.equals("regulation"))
    	{
    		String[] listHead = PartServiceRequest.getListHead("regulation");
    		String[] headMethod=PartServiceRequest.getListMethod("regulation");
    		int[] colw =new int[6];
            for(int i = 0;i<listHead.length;i++)
            {
            		colw[i]=1;
            }
            colw[listHead.length]=0;
            cappList.setRelColWidth(colw);
            cappList.adjustWidth();
            cappList.setHeadings(listHead);
            try
    		{   			
    			cappList.setHeadingMethods(headMethod);
    			cappList.setListItems(cappList.getQMTechnicsInfo());
    		}
    		catch(PropertyVetoException e)
    		{
    			e.printStackTrace();
    		}
    	}
    	
    }    
    
    void mayOutPutJList_mouseClicked(MouseEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_mouseClicked() begin ....");
        if (mayOutPutJList.isSelectionEmpty() == true)
        {
            addAttributeJButton.setEnabled(false);
        }
        else
        {
            addAttributeJButton.setEnabled(true);
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_mouseClicked() end....return is void ");
    }

    void mayOutPutJList_keyPressed(KeyEvent e)
    {
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_keyPressed() begin ....");
        if (mayOutPutJList.isSelectionEmpty() == true)
        {
            addAttributeJButton.setEnabled(false);
        }
        else
        {
            addAttributeJButton.setEnabled(true);
        }
        PartDebug.trace(this, PartDebug.PART_CLIENT,
                        "mayOutPutJList_keyPressed() end....return is void ");
    }

}
