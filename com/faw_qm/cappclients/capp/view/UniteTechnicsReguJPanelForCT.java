package com.faw_qm.cappclients.capp.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.faw_qm.capp.model.PDrawingInfo;
import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.capp.model.QMTechnicsQMDocumentLinkInfo;
import com.faw_qm.capp.util.CappWrapData;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
import com.faw_qm.cappclients.beans.drawingpanel.DrawingPanel;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
//CCBeign SS1
import com.faw_qm.cappclients.capp.util.CappClientHelper;
//CCEnd SS1
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.util.HorizontalLine;
import com.faw_qm.cappclients.capp.view.UniteTechnicsReguJPanel.WorkThread;
import com.faw_qm.cappclients.resource.view.ResourcePanel;
import com.faw_qm.cappclients.util.CappCharTextField;
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.clients.beans.lifecycle.LifeCycleInfo;
//CCBeign SS1
import com.faw_qm.codemanage.model.CodingClassificationInfo;
//CCEnd SS1
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.folder.model.SubFolderInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.users.model.UserInfo;
//SS1 长特合并工艺去掉变速箱状态、默认长特生命周期、默认长特部门 文柳 2015-3-26


public class UniteTechnicsReguJPanelForCT extends ParentJPanel{

    /**保存按钮*/
    private JButton saveJButton = new JButton();


    /**取消按钮*/
    private JButton cancelJButton = new JButton();


    /**退出按钮*/
    private JButton quitJButton = new JButton();


    /**工艺种类代码内容表：用于缓存*/
    private JPanel buttonJPanel = new JPanel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    private JPanel masterJPanel = new JPanel();
    private JLabel numberJLabel = new JLabel();
    private JLabel nameJLabel = new JLabel();
    private JLabel typeJLabel = new JLabel();
    public llTextField numberJTextField;
    private CappCharTextField nameJTextField;
    private JLabel remarkJLabel = new JLabel();
    private CappCharTextField remarkJTextField ;
    private FolderPanel folderPanel = new FolderPanel();
    private JLabel technicsTypeDisJLabel = new JLabel();

    /**是否可以退出*/
    private boolean isSave = true;


    /**关联工艺面板*/
    private TechnicsRelationJPanelForCT technicsJPanel ;


    /**要新建的工艺卡值对象*/
    private QMFawTechnicsInfo technicsInfo;


    /**父窗口*/
    private JFrame parentJFrame;

    private JTabbedPane relationsJTabbedPane = new JTabbedPane();


    /**工艺使用文档关联的维护面板*/
    private TechUsageDocLinkJPanel doclinkJPanel = new TechUsageDocLinkJPanel();


    /**工艺使用材料关联的维护面板*/
    private TechUsageMaterialLinkJPanel materialLinkJPanel;


    /**零部件使用工艺卡关联的维护面板*/
    private PartUsageTechLinkJPanel partLinkJPanel;

    private GridBagLayout gridBagLayout2 = new GridBagLayout();
    private JTabbedPane jTabbedPane = new JTabbedPane();
    private JPanel extendJPanel = new JPanel();
    private CappExAttrPanel cappExAttrPanel;
    private GridBagLayout gridBagLayout3 = new GridBagLayout();
    private GridBagLayout gridBagLayout4 = new GridBagLayout();
    private HorizontalLine horizontalLine = new HorizontalLine();


    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    private JScrollPane jScrollPane = new JScrollPane();
    private JPanel jPanel = new JPanel();

    private JLabel workShopJLabel = new JLabel();
    private CappSortingSelectedPanel workShopSortingSelectedPanel = null;

    /**存放扩展属性bean;键:工艺种类 值:扩展属性bean*/
    private Hashtable extendTable = new Hashtable();
    private CodingIfc technicsType;
    private Hashtable materialLinkTable = new Hashtable();
    private Hashtable partLinkTable = new Hashtable();
    private DrawingPanel drawingpanel;
    private JLabel flowDrawingJLabel = new JLabel();
    private LifeCycleInfo lifeCycleInfo = new LifeCycleInfo();
    private JLabel productStateJLabel = new JLabel();
    private JComboBox productStateComboBox = new JComboBox();
    /**
     * 是否实施TS16949属性
     */
    //CCBeign SS1
   // private static boolean ts16949 = (RemoteProperty.getProperty("TS16949", "true")).equals("true");
    private static boolean ts16949 = false;
    //CCEnd SS1
  
    private MasterTS16949Panel masterTS16949Panel;

  //CCBegin SS1
    /**
     * 判断用户所属公司
     * @return String 获得用户所属公司
     * @author wenl
     */
    public String getUserFromCompany() throws QMException {
		String returnStr = "";
		RequestServer server = RequestServerFactory.getRequestServer();
		StaticMethodRequestInfo info = new StaticMethodRequestInfo();
		info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
		info.setMethodName("getUserFromCompany");
		Class[] paraClass = {};
		info.setParaClasses(paraClass);
		Object[] obj = {};
		info.setParaValues(obj);
		boolean flag = false;
		try {
			returnStr = ((String) server.request(info));
		} catch (QMRemoteException e) {
			throw new QMException(e);
		}
		return returnStr;
	}
//CCEnd SS1
    /**
     * 构造函数
     * @param parentframe 父窗口
     */
    public UniteTechnicsReguJPanelForCT(JFrame parentframe)
    {
        parentJFrame = parentframe;
        //编号
        String technicsNumdisp = QMMessage.getLocalizedMessage(RESOURCE,
                "technicsNumber", null);
        numberJTextField = new llTextField(parentJFrame, technicsNumdisp, 50, false);
         //名称
        String technicsNamedisp = QMMessage.getLocalizedMessage(RESOURCE,
                "technicsName", null);
        nameJTextField =new CappCharTextField(parentJFrame,
                                               technicsNamedisp, 40, false);
        //备注
        String remarkdisp = QMMessage.getLocalizedMessage(RESOURCE,
                "remarkJLabel", null);
        remarkJTextField= new CappCharTextField(parentJFrame, remarkdisp, 40, true);
        technicsJPanel = new TechnicsRelationJPanelForCT(parentJFrame);
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
     * 界面初始化
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        String title2 = QMMessage.getLocalizedMessage(RESOURCE,
                CappLMRB.WORKSHOP, null);
        //工艺种类
        //部门，只显示代码分类，且代码分类可选（部门只能选择厂和分厂）
        workShopSortingSelectedPanel = new CappSortingSelectedPanel(title2,
                "QMProcedure", "workShop");
        workShopSortingSelectedPanel.setDialogTitle(title2);
        //只显示代码分类
         workShopSortingSelectedPanel.setIsOnlyCC(false);
        //代码分类可选
        workShopSortingSelectedPanel.setButtonSize(89, 23);

        workShopSortingSelectedPanel.setSelectBMnemonic('W');

        try
        {
            setRelatedTechnics();
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(parentJFrame, ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
        setLayout(gridBagLayout4);
        saveJButton.setMaximumSize(new Dimension(75, 23));
        saveJButton.setMinimumSize(new Dimension(75, 23));
        saveJButton.setPreferredSize(new Dimension(75, 23));
        saveJButton.setActionCommand("SAVE");
        saveJButton.setMnemonic('S');
        saveJButton.setText("保存");
        saveJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                saveJButton_actionPerformed(e);
            }
        });

        cancelJButton.setMaximumSize(new Dimension(75, 23));
        cancelJButton.setMinimumSize(new Dimension(75, 23));
        cancelJButton.setPreferredSize(new Dimension(75, 23));
        cancelJButton.setActionCommand("CANCEL");
        cancelJButton.setMnemonic('C');
        cancelJButton.setText("取消");
        cancelJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cancelJButton_actionPerformed(e);
            }
        });

        quitJButton.setMaximumSize(new Dimension(75, 23));
        quitJButton.setMinimumSize(new Dimension(75, 23));
        quitJButton.setPreferredSize(new Dimension(75, 23));
        quitJButton.setActionCommand("QUIT");
        quitJButton.setMnemonic('Q');
        quitJButton.setText("退出");
        quitJButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                quitJButton_actionPerformed(e);
            }
        });

        folderPanel.setIsPersonalFolder(true);
        folderPanel.setIsPublicFolders(false);
        folderPanel.setTextFielEnable(false);
        buttonJPanel.setLayout(gridBagLayout1);
        masterJPanel.setLayout(gridBagLayout2);
        numberJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        numberJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        numberJLabel.setText("*工艺编号");
        nameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        nameJLabel.setText("*工艺名称");
        typeJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        typeJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        typeJLabel.setText("*工艺种类");
        remarkJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        remarkJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        /*if(BSXGroup()){
        	 remarkJLabel.setText("PFEMA编号");   
        }else{ */
        	 remarkJLabel.setText("备注");
//        }
        lifeCycleInfo.getProjectPanel().setBrowseButtonSize(new Dimension(89, 23));
        lifeCycleInfo.getProjectPanel().setMnemonicAndText('R', "R");
        lifeCycleInfo.setMaximumSize(new Dimension(20, 60));
        lifeCycleInfo.setPreferredSize(new Dimension(20, 60));
        lifeCycleInfo.setMinimumSize(new Dimension(20, 60));
        lifeCycleInfo.setMode(LifeCycleInfo.CREATE_MODE);

        productStateJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        productStateJLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        productStateJLabel.setText("产品状态");
        
        ResourcePanel rsPanel = new ResourcePanel();
        Collection col = rsPanel.getCoding("QMTechnics", "productState", "SortType");
        if(col == null || col.size() == 0)
        {
            productStateComboBox.addItem("");
        }else
        {
            productStateComboBox.addItem("");
            for(Iterator iter = col.iterator();iter.hasNext();)
            {
                CodingIfc code = (CodingIfc)iter.next();
                
                //CCBegin SS1
                if(getUserFromCompany().equals("ct")){
                	if(!code.getCodeContent().toString().contains("变速箱"))
                		productStateComboBox.addItem(code);
                }else{
                	productStateComboBox.addItem(code);
                }
                //CCEnd SS1
            }
        }
        productStateComboBox.setMaximumSize(new Dimension(80, 22));
        productStateComboBox.setMinimumSize(new Dimension(80, 22));
        productStateComboBox.setPreferredSize(new Dimension(80, 22));
        
        productStateComboBox.setSelectedIndex(2);
        /*if(!BSXGroup()){
          if(ts16949){  
             masterTS16949Panel=new MasterTS16949Panel();
           }
        }*/

        extendJPanel.setLayout(gridBagLayout3);
        relationsJTabbedPane.setMaximumSize(new Dimension(32767, 180));
        relationsJTabbedPane.setMinimumSize(new Dimension(360, 180));
        relationsJTabbedPane.setPreferredSize(new Dimension(310, 200));
        technicsJPanel.setMaximumSize(new Dimension(2147483647, 150));
        technicsJPanel.setMinimumSize(new Dimension(123, 150));
        technicsJPanel.setPreferredSize(new Dimension(248, 200));
        horizontalLine.setFillMode(true);
        horizontalLine.setBevelStyle(0);

        jPanel.setLayout(new BorderLayout());
        masterJPanel.setMaximumSize(new Dimension(370, 2147483647));
        masterJPanel.setMinimumSize(new Dimension(370, 571));
        masterJPanel.setPreferredSize(new Dimension(370, 626));
        jScrollPane.setBorder(BorderFactory.createEtchedBorder());
        jScrollPane.setMaximumSize(new Dimension(394, 32767));
        jScrollPane.setMinimumSize(new Dimension(394, 24));
        jPanel.setMaximumSize(new Dimension(394, 2147483647));
        jTabbedPane.setMaximumSize(new Dimension(399, 32767));
        workShopJLabel.setText("*部门");
        technicsTypeDisJLabel.setMaximumSize(new Dimension(10, 23));
        technicsTypeDisJLabel.setMinimumSize(new Dimension(4, 23));
        technicsTypeDisJLabel.setPreferredSize(new Dimension(4, 23));
        extendJPanel.setBorder(BorderFactory.createEtchedBorder());
        masterJPanel.add(numberJLabel,
                         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 15, 2, 0), 0, 0));
        masterJPanel.add(nameJLabel,
                         new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(5, 15, 5, 0), 0, 0));
        masterJPanel.add(typeJLabel,
                         new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0), 0, 0));
        masterJPanel.add(numberJTextField,
                         new GridBagConstraints(1, 0, 3, 1, 1.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(5, 8, 2, 15), 0, 0));
        masterJPanel.add(nameJTextField,
                         new GridBagConstraints(1, 1, 3, 1, 1.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(2, 8, 2, 15), 0, 0));

        masterJPanel.add(remarkJTextField, new GridBagConstraints(1, 4, 3, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(7, 8, 5, 17), 0, 0));//CR4
        masterJPanel.add(remarkJLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        masterJPanel.add(folderPanel, new GridBagConstraints(0, 7, 4, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 27, 0, 15), 0, 0));//begin CR4
        masterJPanel.add(technicsJPanel, new GridBagConstraints(0, 12, 4, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 15, 0, 15), 0, 0));//end CR4
        masterJPanel.add(relationsJTabbedPane, new GridBagConstraints(0, 3, 4, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 15, 0, 15), 0, 0));
        masterJPanel.add(productStateJLabel, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 2, 0), 0, 0));
        masterJPanel.add(productStateComboBox, new GridBagConstraints(1, 9, 3, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 8, 2, 15), 0, 0));
        /*if(!BSXGroup()){
           if(ts16949)
              masterJPanel.add(masterTS16949Panel, new GridBagConstraints(0, 10, 4, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 4, 7), 0, 0));
        }*/
        masterJPanel.add(horizontalLine, new GridBagConstraints(0, 11, 4, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 0, 10), 0, 1));
        masterJPanel.add(lifeCycleInfo, new GridBagConstraints(0, 5, 4, 2, 1.0, 0.0, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(3, 15, 5, 15), 0, 0));
        masterJPanel.add(technicsTypeDisJLabel,
                         new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.WEST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(5, 8, 5, 8), 0, 0));
        masterJPanel.add(workShopJLabel,
                         new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0), 0, 0));
        masterJPanel.add(workShopSortingSelectedPanel,
                         new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(5, 0, 5, 15), 0, 0));

        relationsJTabbedPane.add(doclinkJPanel,
                                 "文档");

        jPanel.add(jScrollPane, BorderLayout.CENTER);
        jScrollPane.getViewport().add(masterJPanel, null);
        jTabbedPane.add(jPanel, "工艺主信息");
        jTabbedPane.add(extendJPanel, "扩展属性");

        add(buttonJPanel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                                 , GridBagConstraints.EAST,
                                                 GridBagConstraints.NONE,
                                                 new Insets(10, 0, 10, 5), 0, 0));

        buttonJPanel.add(saveJButton,
                         new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 0, 0, 0), 0, 0));
        buttonJPanel.add(quitJButton,
                         new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 8, 0, 0), 0, 0));
        buttonJPanel.add(cancelJButton,
                         new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.NONE,
                                                new Insets(0, 8, 0, 0), 0, 0));
        add(jTabbedPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.BOTH,
                                                new Insets(0, 5, 0, 5), 0, 0));
        masterJPanel.add(flowDrawingJLabel,
                         new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
                                                , GridBagConstraints.EAST,
                                                GridBagConstraints.HORIZONTAL,
                                                new Insets(5, 10, 5, 0), 0, 0));
        masterJPanel.add(flowDrawingJLabel, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(5, 10, 5, 0), 0, 0));//begin CR4
        
        
        lifeCycleInfo.setBsoName("QMFawTechnics");
        folderPanel.setButtonMnemonic('B');//end CR4
        localize();
        numberJTextField.addTextListener(new java.awt.event.TextListener()
        {
        public void textValueChanged(TextEvent e)
        {
                   text_textValueChanged(e);
        }
        });

    }

    private void text_textValueChanged(TextEvent e)  
    {
    	//wenliu
//    	if(!BSXGroup())
          //  masterTS16949Panel.setAttribute(numberJTextField.getText());
    }

    /**
     * 界面信息本地化
     */
    protected void localize()
    {

        folderPanel.setLabelModel(false);
        try
        {
            folderPanel.setLabelText(getPersionalFolder());
        }
        catch (QMRemoteException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        doclinkJPanel.setMode("Edit");
        doclinkJPanel.setTechnics(technicsInfo);

        initResources();

        try
        {
            saveJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "SaveJButton", null));
            cancelJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "CancelJButton", null));
            quitJButton.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "QuitJButton", null));
            numberJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "technicsNumberJLabel", null));
            nameJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "technicsNameJLabel", null));
            typeJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "technicsTypeJLabel", null));
            /*if(BSXGroup()){
           	    remarkJLabel.setText("PFEMA编号");
            }else{*/
                remarkJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "remarkJLabel", null));
//            }
            flowDrawingJLabel.setText(QMMessage.getLocalizedMessage(RESOURCE,
                    "flowdrawing", null));
        }
        catch (Exception ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            String message = QMMessage.getLocalizedMessage(RESOURCE,
                    CappLMRB.MISSING_RESOURCER, null);
            JOptionPane.showMessageDialog(getParentJFrame(), message,
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

    }


//    /**
//     * 获得属性文件中的工艺种类
//     * @return 工艺种类代码内容(字符串形式)集合
//     */
//    public Vector getTechnicsType()
//    {
//        return findCodingInfo("QMTechnicsMaster", "technicsType");
//    }


    /**
     * 保存新建的工艺卡
     */
    private void save()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.UniteTechnicsReguJPanel.save() begin...");
        }
        setButtonWhenSave(false);
        //用于判断必填区域是否已填
        boolean requiredFieldsFilled;
        //设置鼠标形状为等待状态
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        //检查必填区域是否已填
        requiredFieldsFilled = checkRequiredFields();
        if (!requiredFieldsFilled)
        {
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }

        //设置工艺卡属性(编号、名称、类型、备注、资料夹)
        technicsInfo.setTechnicsNumber(numberJTextField.getText());
        technicsInfo.setTechnicsName(nameJTextField.getText());
        technicsInfo.setTechnicsType(technicsType);
        technicsInfo.setWorkShop((BaseValueInfo)
                                 workShopSortingSelectedPanel.getCoding());
        technicsInfo.setLocation(folderPanel.getFolderLocation());
        if (remarkJTextField.getText() != null &&
            remarkJTextField.getText() != "")
        {
            technicsInfo.setRemark(remarkJTextField.getText());
        }
        ArrayList drawingList =null;
        if (drawingpanel != null && drawingpanel.isVisible())
        {
            if(drawingpanel.getDrawingDate()!=null){
            drawingList = new ArrayList(2);
            PDrawingInfo pdrawing = new PDrawingInfo();
            pdrawing.setDrawingByte((byte[])drawingpanel.getDrawingDate().elementAt(0));
            pdrawing.setDrawingType((String)drawingpanel.getDrawingDate().elementAt(1));
            drawingList.add(pdrawing);
            }
        }
        LifeCycleManagedIfc lcm = null;
        try
        {
            //设置生命周期和项目组
            lcm = lifeCycleInfo.assign((
                    LifeCycleManagedIfc)
                    technicsInfo);
        }
        catch (Exception ex1)
        {
             setButtonWhenSave(true);
             setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
             return;
        }
        technicsInfo = (QMFawTechnicsInfo) lcm;
        if(!productStateComboBox.getSelectedItem().equals(""))
        {
        	technicsInfo.setProductState((CodingIfc)productStateComboBox.getSelectedItem());
        }
       
//        if(!BSXGroup())
        {
           if(ts16949){	
                masterTS16949Panel=new MasterTS16949Panel();
           }
        }
        //设置扩展属性值
        if (cappExAttrPanel.check())
        {
            technicsInfo.setExtendAttributes(cappExAttrPanel.getExAttr());
        }
        else
        {
            if (verbose)
            {
                System.out.println("扩展属性录入错误！");
            }
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            setButtonWhenSave(true);
            isSave = false;
            return;
        }

        Vector docLinks = new Vector();
        Vector partLinks = new Vector();
        Vector materialLinks = new Vector();
        Vector docMasterLinks = new Vector();//CR2
        //获得所有关联
        try
        {
            docLinks = doclinkJPanel.getAllLinks();
            int size = docLinks.size();
            for (int j = 0; j < size; j++)
            {
                String docId = ((QMTechnicsQMDocumentLinkInfo) docLinks.
                                elementAt(j)).getRightBsoID();
                Class[] paraClass1 =
                        {String.class};
                Object[] objs1 =
                        {docId};
                String masterID = "";
                try
                {
                    BaseValueInfo docInfo = (BaseValueInfo)
                                            useServiceMethod(
                            "PersistService", "refreshInfo", paraClass1,
                            objs1);
                    if (docInfo instanceof DocInfo)
                    {
                        masterID = ((DocInfo) docInfo).getMasterBsoID();
                        QMTechnicsQMDocumentLinkInfo linkInfo = new
                        QMTechnicsQMDocumentLinkInfo();
                        linkInfo.setRightBsoID(masterID);
                        linkInfo.setLeftBsoID(((
                        		QMTechnicsQMDocumentLinkInfo)
                                               docLinks.elementAt(j)).
                                              getLeftBsoID());
                        docMasterLinks.add(linkInfo);
                    }
                    else
                    if (docInfo instanceof DocMasterInfo)
                    {
                        docMasterLinks.add((QMTechnicsQMDocumentLinkInfo)
                                           docLinks.elementAt(j));
                    }
                }
                catch (QMRemoteException ex)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);
                    JOptionPane.showMessageDialog(getParentJFrame(),
                                                  ex.getClientMessage(),
                                                  title,
                                                  JOptionPane.
                                                  INFORMATION_MESSAGE);
                }

            }
            partLinks = partLinkJPanel.getAllLinks();
            materialLinks = materialLinkJPanel.getAllLinks();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        //将文档关联和材料关联合并
        Vector resourceLinks = new Vector();
        for (int i = 0, l = docMasterLinks.size(); i < l; i++)
        {
            resourceLinks.addElement(docMasterLinks.elementAt(i));
        }
        for (int j = 0, l = materialLinks.size(); j < l; j++)
        {
            resourceLinks.addElement(materialLinks.elementAt(j));
        }

        //{{{获得所有被删除的关联
        Vector deleteLinks = new Vector();
        Vector delLinks = doclinkJPanel.getDeletedLinks();
        for (int k = 0, l = delLinks.size(); k < l; k++)
        {
            deleteLinks.addElement(delLinks.elementAt(k));
        }
        delLinks = partLinkJPanel.getDeletedLinks();
        for (int k = 0, l = delLinks.size(); k < l; k++)
        {
            deleteLinks.addElement(delLinks.elementAt(k));
        }
        delLinks = materialLinkJPanel.getDeletedLinks();
        for (int k = 0, l = delLinks.size(); k < l; k++)
        {
            deleteLinks.addElement(delLinks.elementAt(k));
        }
        //}}}

        //封装所有信息
        CappWrapData cappWrapData = new CappWrapData();
        cappWrapData.setQMTehnicsIfc(technicsInfo);
        cappWrapData.setPartUsageQMTechnics(partLinks);
        cappWrapData.setQMTechnicsUsageResource(resourceLinks);
        cappWrapData.setDeleteLinks(deleteLinks);
        cappWrapData.setUpdateDrawing(drawingList);//cr1
        ((TechnicsRegulationsMainJFrame) parentJFrame).startProgress();//CR3
        //调用服务，保存工艺卡,按列表中工艺规程的顺序将它们的工序关联到新创建的工艺主信息中，
        //并按照系统设置中的工序初值和步长重新按顺序排列工序号。
        Class[] paraClass =
                {CappWrapData.class, Collection.class};
        Object[] obj =
                {cappWrapData, technicsJPanel.commitAddedTechnics()};
        QMFawTechnicsInfo returnTechnics;
        try
        {
            returnTechnics = (QMFawTechnicsInfo) useServiceMethod(
                    "StandardCappService", "combinationTechnicsForCT", paraClass,
                    obj);
        }
        catch (QMRemoteException ex)
        {
            ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            setCursor(Cursor.getDefaultCursor());
            setButtonWhenSave(true);
            isSave = false;
            return;
        }
        ((TechnicsRegulationsMainJFrame) parentJFrame).stopProgress();
        //得到被合并的工艺卡
        Collection c = technicsJPanel.commitAddedTechnics();
        if (c != null)
        {
            for (Iterator i = c.iterator(); i.hasNext(); )
            {
                //把被合并的工艺卡从树上删除
                ((TechnicsRegulationsMainJFrame) getParentJFrame()).removeNode(
                        (QMFawTechnicsInfo) i.next());
            }
        }
        setVisible(false);

        TechnicsTreeObject treeObject = new TechnicsTreeObject(returnTechnics);
        //把treeObject挂到工艺树上(根据工艺类型挂在不同的树节点上)
        ((TechnicsRegulationsMainJFrame) getParentJFrame()).getProcessTreePanel().
                addProcess(treeObject);

        setButtonWhenSave(true);
        setCursor(Cursor.getDefaultCursor());
        isSave = true;
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.UniteTechnicsReguJPanel.save() end...return is void");
        }
    }



    /**
     * 执行保存操作
     * @param e ActionEvent
     */
    void saveJButton_actionPerformed(ActionEvent e)
    {
        WorkThread thread = new WorkThread();
        thread.start();
    }


    /**
     * 取消按钮的执行方法
     * <p>将已经录入的数据都置空</p>
     */
    void cancelJButton_actionPerformed(ActionEvent e)
    {
        numberJTextField.setText("");
        nameJTextField.setText("");
        remarkJTextField.setText("");
        workShopSortingSelectedPanel.setCoding(null);
        folderPanel.setTextFieldNull();
        technicsJPanel.clear();
        doclinkJPanel.clear();
        materialLinkJPanel.clear();
        partLinkJPanel.clear();
        lifeCycleInfo.clear();
        lifeCycleInfo.getLifeCyclePanel().setLifeCycle(null);
        lifeCycleInfo.getProjectPanel().setObject(null);

//        if(!BSXGroup())
        {
           if(ts16949){
        		 this.masterTS16949Panel.clear();
        	 }
         }
    }


    /**
     * 执行退出操作
     * @param e ActionEvent
     */
    void quitJButton_actionPerformed(ActionEvent e)
    {
        quit();
    }

    protected boolean quit()
    {
        String s = QMMessage.getLocalizedMessage(RESOURCE,
                                                 CappLMRB.
                                                 COMFIRM_UNITE_TECHNICS, null);
        if (confirmAction(s))
        {
            save();

        }
        else
        {
            setVisible(false);
            isSave = true;
        }
        return isSave;
    }


    /**
     * 显示确认对话框
     * @param s 在对话框中显示的信息
     * @return  如果用户选择了“确定”按钮，则返回true;否则返回false
     */
    private boolean confirmAction(String s)
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        JOptionPane okCancelPane = new JOptionPane();
        return okCancelPane.showConfirmDialog(getParentJFrame(),
                                              s, title,
                                              JOptionPane.YES_NO_OPTION) ==
                JOptionPane.YES_OPTION;
    }


    /**
     * 检验必填区域是否已有有效值
     * @return  如果必填区域已有有效值，则返回为真
     */
    private boolean checkRequiredFields()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.UniteTechnicsReguJPanel.checkRequiredFields() begin...");
        }
        boolean isOK = true;
        String message = null;
        String title = "";
        try
        {
            //检查编号
            isOK = numberJTextField.check();
            //检查名称
            if (isOK)
                isOK = nameJTextField.check();
           //检查备注
            if (isOK)
                isOK = remarkJTextField.check();

            //检验资料夹是否为空
             if (isOK&&checkFolderLocation() == null)
            {
                message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NO_LOCATION_ENTERED,
                        null);
                isOK = false;
                folderPanel.grabFocus();
            }

            //检验部门是否为空
            else if (isOK&&workShopSortingSelectedPanel.getCoding() == null)
            {
                message = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.NO_WORKSHOP_ENTERED,
                        null);
                isOK = false;
                workShopSortingSelectedPanel.getJButton().grabFocus();
            }


            if (!isOK&&message!=null)
            {
                //显示信息：缺少必需的字段
                title = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.REQUIRED_FIELDS_MISSING,
                        null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              message,
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }
        catch (QMRemoteException qre)
        {
            //显示信息：所指定的资料夹不是个人资料夹
            title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          qre.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.UniteTechnicsReguJPanel.checkRequiredFields() end...return: " +
                               isOK);
        }
        return isOK;

    }


    /**
     * 检验是否已指定资料夹
     * @return 如果已指定资料夹路径，则返回资料夹。
     * @throws QMRemoteException
     */
    private SubFolderInfo checkFolderLocation()
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.UniteTechnicsReguJPanel.checkFolderLocation() begin...");
        }
        String location = "";
        SubFolderInfo folderInfo = null;
        //获得资料夹路径
        location = folderPanel.getFolderLocation();

        if (location != null && location.length() != 0)
        {
            //调用资料夹服务，根据获得的资料夹路径获得资料夹
            Class[] paraClass =
                    {String.class};
            Object[] objs =
                    {location};
            try
            {
                folderInfo = (SubFolderInfo) useServiceMethod(
                        "FolderService", "getFolder", paraClass, objs);
            }
            catch (QMRemoteException ex)
            {
                throw ex;
            }

        }

        if (folderInfo != null)
        {
            //调用资料夹服务，判断指定的文件夹是否是个人文件夹
            Class[] paraClass2 =
                    {FolderIfc.class};
            Object[] objs2 =
                    {folderInfo};
            Boolean flag1 = null;
            try
            {
                flag1 = (Boolean) useServiceMethod(
                        "FolderService", "isPersonalFolder", paraClass2, objs2);
            }
            catch (QMRemoteException ex)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.LOCATION_NOT_VALID,
                        null);
                JOptionPane.showMessageDialog(getParentJFrame(),
                                              ex.getClientMessage(),
                                              title,
                                              JOptionPane.INFORMATION_MESSAGE);

            }

            boolean flag = false;
            if (flag1 != null)
            {
                flag = flag1.booleanValue();
            }

            if (!flag)
            {
                //抛出异常信息：所指定的资料夹不是个人文件夹
                throw new QMRemoteException(QMMessage.getLocalizedMessage(
                        RESOURCE,
                        CappLMRB.LOCATION_NOT_PERSONAL_CABINET, null));
            }
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.view.UniteTechnicsReguJPanel.checkFolderLocation() end...return: " +
                               folderInfo);
        }
        return folderInfo;

    }


    /**
     * 设置按钮的显示状态（有效或失效）
     * @param flag  flag为True，按钮有效；否则按钮失效
     */
    private void setButtonWhenSave(boolean flag)
    {
        quitJButton.setEnabled(flag);
        saveJButton.setEnabled(flag);
        cancelJButton.setEnabled(flag);
    }


    public static void main(String[] arg)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        JFrame f = new JFrame();
        UniteTechnicsReguJPanel panel = new UniteTechnicsReguJPanel(null);
        f.getContentPane().add(panel);
        f.getContentPane().setLayout(new GridLayout());
        f.setSize(650, 500);
        f.setVisible(true);
    }


    /**
     * 添加指定的工艺卡到关联列表中
     * @param info 指定的工艺卡
     */
    public void addObjectToTable(BaseValueInfo info)
    {
        if (info instanceof QMTechnicsInfo)
        {

            technicsJPanel.addTechReguToTable((QMTechnicsInfo) info);
        }
        else
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "error", null);
            String message = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.WRONG_TYPE_OBJECT, null);
            JOptionPane.showMessageDialog(getParentJFrame(), message, title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
    }


    /**
     * 获得当前用户的个人资料夹位置
     * @return 当前用户的个人资料夹位置
     * @throws QMRemoteException
     */
    public String getPersionalFolder()
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.UniteTechnicsReguJPanel.getPersionalFolder() begin...");
        }
        Class[] c =
                {};
        Object[] objs =
                {};
        UserInfo user = (UserInfo) useServiceMethod(
                "SessionService", "getCurUserInfo", c, objs);
        Class[] c1 =
                {UserInfo.class};
        Object[] objs1 =
                {user};
        SubFolderInfo folder = (SubFolderInfo) useServiceMethod(
                "FolderService", "getPersonalFolder", c1, objs1);
        if (verbose)
        {
            System.out.println("cappclients.capp.view.UniteTechnicsReguJPanel.getPersionalFolder() end...return: "
                               + folder.getPath());
        }
        return folder.getPath();

    }


    /**
     *工艺面板重新设关联工艺
     *重新设零部件关联面板,材料关联面板,扩展属性面板
     */
    public void refreshObject()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.UniteTechnicsReguJPanel.refreshObject bedin  ");
        }
        technicsJPanel.clear();

        technicsTypeDisJLabel.setText(technicsType.getCodeContent());

        //改变零部件使用工艺卡panel
        newPartLinkPanel(technicsType.getCodeContent());
        newMaterialLinkJPanel(technicsType.getCodeContent());

        //扩展属性
        newCappExAttrPanel(technicsType.getCodeContent());

        technicsInfo.setTechnicsType(technicsType);
        technicsJPanel.setRelatedTechnics(technicsInfo);
        workShopSortingSelectedPanel.setDefaultCoding("部门",
                technicsType.getCodeContent());
        //CCBegin SS1
        lifeCycleInfo.getLifeCyclePanel().setDefaultLifeCycle("长特工艺生命周期");
        try
        {
	    	 if(getUserFromCompany().equals("ct")){
	    		 Class[] paraclass ={String.class,String.class};
	             Object[] paraobj ={"长特分公司","组织机构"};
	             CodingClassificationInfo coding = (CodingClassificationInfo) CappClientHelper.useServiceMethod(
	                     "CodingManageService", "findClassificationByName", paraclass, paraobj);
	             if(coding!=null){
	        		workShopSortingSelectedPanel.setCoding(coding);
	        		workShopSortingSelectedPanel.setVisible(true);
	        		workShopSortingSelectedPanel.setEnabled(true);
	        	} 
	         }
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        //CCEnd SS1
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.UniteTechnicsReguJPanel.refreshObject end");
        }
    }


    /**
     * 实例化材料面板
     * @param technicsType String 工艺种类
     */
    private void newMaterialLinkJPanel(String technicsType)
    {
        relationsJTabbedPane.remove(materialLinkJPanel);
        materialLinkJPanel = (TechUsageMaterialLinkJPanel) materialLinkTable.
                             get(technicsType);
        if (materialLinkJPanel == null)
        {
            materialLinkJPanel = new TechUsageMaterialLinkJPanel(technicsType);
            materialLinkTable.put(technicsType, materialLinkJPanel);
        }
        materialLinkJPanel.clear();
        materialLinkJPanel.setTechnics(technicsInfo);
        materialLinkJPanel.setMode("Edit");
        relationsJTabbedPane.add(materialLinkJPanel,
                                 QMMessage.
                                 getLocalizedMessage(RESOURCE, "materialTitle", null),
                                 2);
    }

    public void newPartLinkPanel(String technicsType)
    {
        relationsJTabbedPane.remove(partLinkJPanel);
        partLinkJPanel = (PartUsageTechLinkJPanel) partLinkTable.get(
                technicsType);
        if (partLinkJPanel == null)
        {
            partLinkJPanel = new PartUsageTechLinkJPanel(technicsType);
            //加此监听的原因：当零部件关联面板加入主要零部件，且工艺种类是冲压工艺,则处理材料关联面板
            partLinkJPanel.addListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    materialLinkJPanel.actionPerformd_whenAddedMajorPark(e);
                }
            });
            partLinkTable.put(technicsType, partLinkJPanel);
        }
        partLinkJPanel.clear();
        partLinkJPanel.setTechnics(technicsInfo);

        partLinkJPanel.setMode("Edit");

        relationsJTabbedPane.add(partLinkJPanel,
                                 QMMessage.getLocalizedMessage(RESOURCE,
                "partTitle", null), 1);

    }

    public void clear()
    {
        numberJTextField.setText("");
        nameJTextField.setText("");
        remarkJTextField.setText("");
        technicsTypeDisJLabel.setText("");
        workShopSortingSelectedPanel.setCoding(null);
        try
        {
            folderPanel.setLabelText(getPersionalFolder());
        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
        }
        doclinkJPanel.clear();

        technicsJPanel.clear();
        lifeCycleInfo.clear();
        lifeCycleInfo.getLifeCyclePanel().setLifeCycle(null);
        lifeCycleInfo.getProjectPanel().setObject(null);
//        if(!BSXGroup())
//        {
//          if(ts16949){
//       		 this.masterTS16949Panel.clear();
//       	 }
//        }
        repaint();
    }

    public void setRelatedTechnics()
            throws QMException
    {
        QMFawTechnicsInfo technics = new QMFawTechnicsInfo();
        technicsInfo = technics;
        technicsJPanel.setRelatedTechnics(technics);
    }


    /**
     *  根据录入的工艺卡种类，创建不同的扩展属性
     */
    private void newCappExAttrPanel(String technicsType)
    {
        try
        {
            if (cappExAttrPanel != null)
            {
                extendJPanel.remove(cappExAttrPanel);
            }
            cappExAttrPanel = new CappExAttrPanel("QMFawTechnics", technicsType,
                                                  1);
            extendJPanel.add(cappExAttrPanel,
                             new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                    , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
            return;
        }

        try
        {
            if (cappExAttrPanel != null)
            {
                extendJPanel.remove(cappExAttrPanel);
            }
              cappExAttrPanel = new CappExAttrPanel("QMFawTechnics",
                      technicsType, 1);
              
            extendJPanel.add(cappExAttrPanel,
                             new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                    , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
            return;
        }

    }


    /**
     * 设置工艺种类
     * @param type CodingIfc 工艺种类
     */
    public void setTechnicsType(CodingIfc type)
    {
        technicsType = type;
        technicsJPanel.setTechnicsType(technicsType.getCodeContent());
        technicsTypeDisJLabel.setText(technicsType.getCodeContent());
        refreshObject();
    }
//wenliu
    /**
     * 设置工艺种类
     * @param type CodingIfc 工艺种类
     */
    public CodingIfc getTechnicsType()
    {
       return technicsType;
    }
    //wenliu
    /**
     * 重新初始化制件流程图面板
     * @param technicsType String 工艺种类
     */
    private void newDrawingpanel(String technicsType)
    {
        String has = RemoteProperty.getProperty("制件流程图" + technicsType, "false").
                     trim();
        if (has.equals("true"))
        {
            if (drawingpanel == null)
            {
                drawingpanel = new DrawingPanel(true);
                masterJPanel.add(drawingpanel,
                                 new GridBagConstraints(1, 8, 3, 1, 1.0,
                        0.0
                        , GridBagConstraints.WEST,
                        GridBagConstraints.HORIZONTAL,
                        new Insets(5, 0, 5, 15), 0, 0));//CR4
            }
            drawingpanel.setVisible(true);
            drawingpanel.setModel(DrawingPanel.EDIT);
            drawingpanel.setDrawingDate(null);
            flowDrawingJLabel.setVisible(true);

        }
        else
        {

            if (drawingpanel != null &&
                drawingpanel.getParent() == masterJPanel)
            {
                drawingpanel.setVisible(false);
            }
            flowDrawingJLabel.setVisible(false);
        }
    }
/*
 * 判断所在公司是否是变速箱
 */
    /*public boolean BSXGroup(){
    	Boolean BSX = false; 	
      	try {
  	    	Class[] paraClass = {};
  	        Object[] objs = {};
  	        String className = "com.faw_qm.doc.util.DocServiceHelper";
  	        String methodName = "isBSXGroup";
  	        StaticMethodRequestInfo in = new StaticMethodRequestInfo();
  	        in.setClassName(className);
  	        in.setMethodName(methodName);
  	        in.setParaClasses(paraClass);
  	        in.setParaValues(objs);
  	        RequestServer server = null;
  	        server = RequestServerFactory.getRequestServer();	
  	        BSX = (Boolean) server.request(in);
  		} catch (QMRemoteException e) {
  			e.printStackTrace();
  		}
    	return BSX;
    }*/
    /**
     * <p>Title:工作线程 </p>
     * <p>Description: 用来保存的线程</p>
     * <p>Copyright: Copyright (c) 2004</p>
     * <p>Company: 一汽启明</p>
     * @author not 薛静
     * @version 1.0
     */
    class WorkThread extends Thread
    {
        public void run()
        {
            save();
        }
    }

}
