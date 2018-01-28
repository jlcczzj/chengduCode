/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 工艺BOM管理器中添加解放设计变更单会带入相应单据的采用和不采用件 xianglx 2014-8-28
 * SS2 自动生成BOM发布单 lishu 2017-5-12
 */

package com.faw_qm.gybomNotice.client.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


import com.faw_qm.bomNotice.client.util.NoticeClientUtil;
import com.faw_qm.bomNotice.client.view.BomAdoptNoticeViewPanel;
import com.faw_qm.bomNotice.client.view.BomNoticeMainJFrame;
import com.faw_qm.bomNotice.client.view.BomSubAdoptNoticeViewPanel;
import com.faw_qm.bomNotice.client.view.SearchBomAdoptNoticeDialog;
import com.faw_qm.bomNotice.model.BomAdoptNoticeIfc;
import com.faw_qm.bomNotice.model.BomAdoptNoticeInfo;
import com.faw_qm.bomNotice.model.BomChangeNoticeIfc;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.clients.beans.folderPanel.FolderPanel;
import com.faw_qm.clients.beans.lifecycle.LifeCyclePanel;
import com.faw_qm.clients.beans.query.QMQueryEvent;
import com.faw_qm.clients.beans.query.QMQueryListener;
import com.faw_qm.clients.beans.query.QmChooser;
import com.faw_qm.clients.beans.query.QmQuery;
import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.doc.model.DocInfo;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RichToThinUtil;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.gybomNotice.client.util.GYBomAdoptNoticeTreeObject;
import com.faw_qm.gybomNotice.client.util.GYBomNoticeTreePanel;
import com.faw_qm.gybomNotice.client.util.GYNoticeClientUtil;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeInfo;
import com.faw_qm.gybomNotice.util.GYNoticeHelper;
import com.faw_qm.lifecycle.model.LifeCycleManagedIfc;
import com.faw_qm.lifecycle.model.LifeCycleTemplateIfc;
import com.faw_qm.lock.model.LockIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterInfo;




/**
 * <p>Title: 车架驾驶室。</p> <p>Description: </p> <p>Copyright: Copyright (c) 2014</p> <p>Company: 启明信息技术股份有限公司</p>
 * @author 文柳
 * @version 1.0
 */

public class CJAdoptNoticeViewPanel extends RightPanel
{
	  /**整车、车架分类*/
	private static String classificationStr = RemoteProperty.getProperty("com.faw_qm.gybomNotice.CarloadAndCarFrameClassification", "J6L;J6M;J6P;J7;MV3;其它");
	  /**驾驶室分类*/
	private static String classificationBodyStr = RemoteProperty.getProperty("com.faw_qm.gybomNotice.CarBodyClassification", "J7凸地板高顶;J7凸地板平顶;J7平地板高顶;J7平地板平顶;J6P大排半-13款高顶;J6P大排半-13款平顶;J6P大排半-11款高顶;J6P大排半-11款平顶;J6P小排半-平顶;J6M大排半-11款高顶;J6M大排半-11款平顶;J6L平顶;J6单排");
	/**车架发布类型*/
	private static String publishTypeFrameStr = RemoteProperty.getProperty("com.faw_qm.gybomNotice.CarFramePublicType", "车架工艺BOM采用单;车架工艺BOM更改单");
	  /**驾驶室发布类型*/
	private static String publishTypeBodyStr = RemoteProperty.getProperty("com.faw_qm.gybomNotice.CarBodyPublicTyp", "驾驶室工艺BOM采用单;驾驶室工艺BOM更改单");
	/**生命周期*/
	private static String lifeCycleTemplateStr = RemoteProperty.getProperty("com.faw_qm.gybomNotice.lifeCycleTemplate2", "车架驾驶室工艺BOM发布单");
	  /**资料夹*/
	private static String folderStr = RemoteProperty.getProperty("com.faw_qm.gybomNotice.BomAdoptNoticeFolder2", "\\Root\\车架驾驶室工艺BOM采用更改单");
	
	
	JTabbedPane jTabbedPane1 = new JTabbedPane();
	
	private GYBomAdoptNoticeIfc myBomAdoptNotice;
    private static final long serialVersionUID = 1L;
    private static final boolean verbose = GYNoticeHelper.VERBOSE;
    //发布类型
    JLabel publishTypeLabel = new JLabel();
    JComboBox publishTypeBox = new JComboBox();
    //分类
    JLabel classificationLabel = new JLabel();
    JComboBox classificationBox = new JComboBox();
    
    //通知单编号
    JLabel adoptnoticenumberLabel = new JLabel();
    JTextField adoptnoticenumberJTF = new JTextField();
    //通知单名称
    JLabel adoptnoticenameLabel = new JLabel();
    JTextField adoptnoticenameJTF = new JTextField();
  
    //资料夹
    JLabel locationLabel = new JLabel();
    JTextField locationJTF = new JTextField();
    //生命周期
    JLabel lifeCycleLabel = new JLabel();
    JTextField lifeCycleJTF = new JTextField();
   
    //整车BOM
    JLabel jfBomNoticeLabel = new JLabel();
    JTextField jfBomNoticeJTF = new JTextField();
    GYBomAdoptNoticeIfc parentNotice ;
//CCBegin SS1
    //解放设计变更单
    private JButton addJFNoticeJButton = new JButton();
    JLabel jfNoticeLabel = new JLabel();
    JTextField jfNoticeJTF = new JTextField();
    BaseValueIfc jfNoticeIfc =  null;
    /**
     * 获取中心采用变更单
     * @return BaseValueIfc 
     */
    public BaseValueIfc getJfNoticeIfc(){
    	return jfNoticeIfc;
    }
    /**
     * 设置setJfNoticeIfc
     * @param BaseValueIfc info
     */
    public void setJfNoticeIfc(BaseValueIfc info){
    	this.jfNoticeIfc = info;
    	if(info!=null){
    		if(info instanceof BomAdoptNoticeIfc){
    			jfNoticeJTF.setText(((BomAdoptNoticeIfc)info).getAdoptnoticenumber());
    		}else if(info instanceof BomChangeNoticeIfc){
    			jfNoticeJTF.setText(((BomChangeNoticeIfc)info).getAdoptnoticenumber());
    		}
    	}
    }
//CCEnd SS1
   
    //车型
    JLabel cxPartLabel = new JLabel();
    JTextField cxPartJTF = new JTextField();
    public static QMPartIfc cxPart = null;
    
    //说明
    JLabel consdescLabel = new JLabel();
    JTextArea consdescJTF = new JTextArea();

    JPanel jPanel1 = new JPanel();//基础信息
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();//说明
    JPanel jPanel5 = new JPanel();//整体页面
    JPanel jPanel6 = new JPanel();//button页面
    JPanel subNoticePanel = new JPanel();//子单页面
    JPanel jPanel7 = new JPanel();//文档关联
    public static String publicType = "";
    //调整BOM页面
    public ZCAdoptNoticeAdjustPanel adustPanle = null;
    //散发清单页面
    public CJCountInvoicePanel invoicePanel = null;
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JButton addJFDocJButton = new JButton();
    private JButton searchPartButton = new JButton();
    private JButton viewNoticeButton = new JButton();
    private JButton saveNoticeButton = new JButton();
    private JButton cancelNoticeButton = new JButton();
    /** 界面显示模式（更新模式）标记 */
    public final static int UPDATE_MODE = 0;

    /** 界面显示模式（创建模式）标记 */
    public final static int CREATE_MODE = 1;

    /** 界面显示模式（查看模式）标记 */
    public final static int VIEW_MODE = 2;
    /** 界面显示模式（另存为模式）标记 */
    public final static int SAVE_AS_MODE = 3;
    /** 界面模式(默认为查看) */
    private int mode = VIEW_MODE;
    
    //CCBegin SS2
    /** 界面显示模式（自动生成发布单）标记 */
    public final static int AUTOCREATE_MODE = 4;
    //CCEnd SS2

    private JFrame parentFrame = null;

    /**
     * 是否执行了退出操作
     */
    public static boolean isHaveSave = false;

    /**
     * 获取父通知单
     * @return BaseValueIfc 
     */
    public GYBomAdoptNoticeIfc getParentNotice(){
    	return this.parentNotice;
    }
    /**
     * 设置父通知单
     * @param BaseValueIfc info
     */
    public void setParentNotice(GYBomAdoptNoticeIfc ifc){
    	this.parentNotice = ifc;
    	if(ifc!=null){
    		jfBomNoticeJTF.setText(ifc.getAdoptnoticenumber());
    	}
    }
    
//CCBegin SS1
    public BaseValueIfc getJfNotice(){
    	return this.jfNoticeIfc;
    }
    public void setJfNotice(BaseValueIfc info){
    	this.jfNoticeIfc = info;
    	if(info!=null){
    		if(info instanceof BomAdoptNoticeIfc){
    			jfNoticeJTF.setText(((BomAdoptNoticeIfc)info).getAdoptnoticenumber());
    		}else if(info instanceof BomChangeNoticeIfc){
    			jfNoticeJTF.setText(((BomChangeNoticeIfc)info).getAdoptnoticenumber());
    		}
    	}
    }
//CCEnd SS1
  
    /**
     * 构造函数
     */
    public CJAdoptNoticeViewPanel(JFrame parent,String type)
    {

        try
        {
            this.parentFrame = parent;
            this.publicType = type;
            jbInit();
           // repaint();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    void jbInit() throws QMException
    {
        jScrollPane2.setBorder(BorderFactory.createEtchedBorder());

        this.setLayout( new GridBagLayout());
        jPanel1.setLayout( new GridBagLayout());
        jPanel2.setLayout( new GridBagLayout());
        jPanel3.setLayout( new GridBagLayout());
        jPanel4.setLayout( new GridBagLayout());
        jPanel5.setLayout( new GridBagLayout());
        jPanel7.setLayout( new GridBagLayout());
        subNoticePanel.setLayout( new GridBagLayout());
        publishTypeLabel.setText("发布类型");
        publishTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        classificationLabel.setText("*分类");
        classificationLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        
        //通知单编号
        adoptnoticenumberLabel.setText("发布单编号");
        adoptnoticenumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        adoptnoticenumberJTF.setMaximumSize(new Dimension(100, 22));
        adoptnoticenumberJTF.setMinimumSize(new Dimension(100, 22));
        adoptnoticenumberJTF.setPreferredSize(new Dimension(100, 22));

        //通知单名称
        adoptnoticenameLabel.setText("*发布单名称");
        adoptnoticenameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        adoptnoticenameJTF.setText("");
        adoptnoticenameJTF.setMaximumSize(new Dimension(110, 22));
        adoptnoticenameJTF.setMinimumSize(new Dimension(110, 22));
        adoptnoticenameJTF.setPreferredSize(new Dimension(110, 22));
        //生命周期
        lifeCycleLabel.setText("*生命周期");
        lifeCycleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lifeCycleJTF.setMaximumSize(new Dimension(110, 22));
        lifeCycleJTF.setMinimumSize(new Dimension(110, 22));
        lifeCycleJTF.setPreferredSize(new Dimension(110, 22));
        //lifeCycleJTF.setEnabled(false);
        
        //资料夹
        locationLabel.setText("*资料夹");
        locationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        locationJTF.setMaximumSize(new Dimension(110, 22));
        locationJTF.setMinimumSize(new Dimension(110, 22));
        locationJTF.setPreferredSize(new Dimension(110, 22));
        //解放设计变更单
        jfBomNoticeLabel.setText("整车发布单");
        jfBomNoticeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        jfBomNoticeJTF.setText("");
        jfBomNoticeJTF.setMaximumSize(new Dimension(100, 22));
        jfBomNoticeJTF.setMinimumSize(new Dimension(100, 22));
        jfBomNoticeJTF.setPreferredSize(new Dimension(100, 22));
        //jfBomNoticeJTF.setEnabled(false);
        addJFDocJButton.setMaximumSize(new Dimension(91, 23));
        addJFDocJButton.setMinimumSize(new Dimension(91, 23));
        addJFDocJButton.setPreferredSize(new Dimension(91, 23));
        addJFDocJButton.setText("浏览..");
        addJFDocJButton.addActionListener(new CJAdoptNoticeViewPanel_addJFDocJButton_actionAdapter(this));
//CCBegin SS1
        //解放设计变更单
        jfNoticeLabel.setText("解放设计变更单");
        jfNoticeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        jfNoticeJTF.setText("");
        jfNoticeJTF.setMaximumSize(new Dimension(100, 22));
        jfNoticeJTF.setMinimumSize(new Dimension(100, 22));
        jfNoticeJTF.setPreferredSize(new Dimension(100, 22));
        //jfNoticeJTF.setEnabled(false);
        addJFNoticeJButton.setMaximumSize(new Dimension(91, 23));
        addJFNoticeJButton.setMinimumSize(new Dimension(91, 23));
        addJFNoticeJButton.setPreferredSize(new Dimension(91, 23));
        addJFNoticeJButton.setText("浏览..");
        addJFNoticeJButton.addActionListener(new ZCAdoptNoticeViewPanel_addJFDocJButton_actionAdapter(this));
//CCEnd SS1
        cxPartLabel.setText("*车型");
        cxPartLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cxPartJTF.setText("");
        cxPartJTF.setMaximumSize(new Dimension(100, 22));
        cxPartJTF.setMinimumSize(new Dimension(100, 22));
        cxPartJTF.setPreferredSize(new Dimension(100, 22));
        //cxPartJTF.setEnabled(false);
        searchPartButton.setMaximumSize(new Dimension(91, 23));
        searchPartButton.setMinimumSize(new Dimension(91, 23));
        searchPartButton.setPreferredSize(new Dimension(91, 23));
        searchPartButton.setText("浏览..");
        searchPartButton.addActionListener(new CJAdoptNoticeViewPanel_addCxPartJButton_actionAdapter(this));
        //说明
        consdescLabel.setText("说明");
        consdescLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        consdescJTF.setText("");
        consdescJTF.setMaximumSize(new Dimension(100, 22));
        consdescJTF.setMinimumSize(new Dimension(100, 22));
        consdescJTF.setPreferredSize(new Dimension(100, 22));
       

        viewNoticeButton.setMaximumSize(new Dimension(110, 23));
        viewNoticeButton.setMinimumSize(new Dimension(110, 23));
        viewNoticeButton.setPreferredSize(new Dimension(110, 23));
        viewNoticeButton.setText("查看发布单");
        viewNoticeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
               viewNoticeJButton_actionPerformed(e);
            }
        });
       
        saveNoticeButton.setMaximumSize(new Dimension(110, 23));
        saveNoticeButton.setMinimumSize(new Dimension(110, 23));
        saveNoticeButton.setPreferredSize(new Dimension(110, 23));
        saveNoticeButton.setText("保存");
        saveNoticeButton.addActionListener(new CJAdoptNoticeViewPanel_saveJButton_actionAdapter(this));   	
        cancelNoticeButton.setMaximumSize(new Dimension(110, 23));
        cancelNoticeButton.setMinimumSize(new Dimension(110, 23));
        cancelNoticeButton.setPreferredSize(new Dimension(110, 23));
        cancelNoticeButton.setText("取消");
        cancelNoticeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                quitJButton_actionPerformed(e);
            }
        });
        jPanel1.add(publishTypeLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel1.add(publishTypeBox, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel1.add(classificationLabel, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel1.add(classificationBox, new GridBagConstraints(3, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 14, 2, 4), 0, 0));
        jPanel1.add(adoptnoticenumberLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel1.add(adoptnoticenumberJTF, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel1.add(adoptnoticenameLabel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 0), 0, 0));
        jPanel1.add(adoptnoticenameJTF, new GridBagConstraints(3, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 14, 2, 4), 0, 0));
        jPanel1.add(lifeCycleLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel1.add(lifeCycleJTF, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel1.add(locationLabel, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 30, 2, 0), 0, 0));
        jPanel1.add(locationJTF, new GridBagConstraints(3, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 14, 2, 4), 0, 0)); 
        
         
        jPanel2.add(jfBomNoticeLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel2.add(jfBomNoticeJTF, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel2.add(addJFDocJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel2.add(cxPartLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 12, 2, 4), 0, 0));
        jPanel2.add(cxPartJTF, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel2.add(searchPartButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
//CCBegin SS1
        jPanel2.add(jfNoticeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel2.add(jfNoticeJTF, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel2.add(addJFNoticeJButton, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
//CCEnd SS1
        
        consdescJTF.setLineWrap(true);
        jPanel4.add(consdescLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 58, 2, 4), 0, 0));
        jPanel4.add(jScrollPane2, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 8, 2, 4), 0, 0));
        jScrollPane2.getViewport().add(consdescJTF, null);
       
       // jPanel6.add(viewBomButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        jPanel6.add(viewNoticeButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        jPanel6.add(saveNoticeButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        jPanel6.add(cancelNoticeButton, new GridBagConstraints(2, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));

        
      
        jPanel3.add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 36, 0, 8), 0, 0));
        jPanel3.add(jPanel2, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 36, 0, 8), 0, 0));
        jPanel3.add(jPanel4, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 20, 2, 8), 0, 0));
        jPanel3.add(jPanel6, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 100, 2, 8), 0, 0));
        adustPanle = new ZCAdoptNoticeAdjustPanel((GYBomNoticeMainJFrame)this.getParentJFrame());
        invoicePanel = new CJCountInvoicePanel((GYBomNoticeMainJFrame)this.getParentJFrame());
        jTabbedPane1.add(jPanel3,"基础信息");
//        System.out.println("11111111111111123333333333s");
    	jTabbedPane1.add(adustPanle,"调整BOM");
    	jTabbedPane1.add(invoicePanel,"散发清单");
    	jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				
				tabChange(evt);
			}
		});
        this.add(jTabbedPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 8, 0, 8), 0, 0));
    }
    /**
     * 选择Tab页面监听
     */
    private void tabChange(javax.swing.event.ChangeEvent evt) {
		int selectIndex = jTabbedPane1.getSelectedIndex();
		if (selectIndex == 1) {
			try {
				adustPanle.setBomAdoptNotice(myBomAdoptNotice,this.mode);
			} catch (QMException e) {
				e.printStackTrace();
			}
		}else if (selectIndex == 2) {
			try {
				invoicePanel.setBomAdoptNotice(myBomAdoptNotice,this.mode);
			} catch (QMException e) {
				e.printStackTrace();
			}
		}
	}
   
    
    /**
     * 获得当前变量内容
     */
    public void getBoxData(String type)
    {
    	if(type.equals("车架")){
    		//发布类型
    		String[] publicTypeVec = publishTypeFrameStr.split(";");
    		if(publicTypeVec!=null&&publicTypeVec.length>0){
    			publishTypeBox.removeAllItems();
    			for(int i = 0 ; i <publicTypeVec.length;i++){
    				String temType = publicTypeVec[i];
    				publishTypeBox.addItem(temType);	
    			}
    		}
    		//分类
    		String[] classificationVec = classificationStr.split(";");
    		if(classificationVec!=null&&classificationVec.length>0){
    			classificationBox.removeAllItems();
    			for(int i = 0 ; i <classificationVec.length;i++){
    				String temClass = classificationVec[i];
    				classificationBox.addItem(temClass);
    			}
    		}
    		
    	}else if(type.equals("驾驶室")){
    		//发布类型
    		String[] publicTypeVec = publishTypeBodyStr.split(";");
    		if(publicTypeVec!=null&&publicTypeVec.length>0){
    			publishTypeBox.removeAllItems();
    			for(int i = 0 ; i <publicTypeVec.length;i++){
    				String temType = publicTypeVec[i];
    				publishTypeBox.addItem(temType);	
    			}
    		}
    		//分类
    		String[] classificationVec = classificationBodyStr.split(";");
    		if(classificationVec!=null&&classificationVec.length>0){
    			classificationBox.removeAllItems();
    			for(int i = 0 ; i <classificationVec.length;i++){
    				String temClass = classificationVec[i];
    				classificationBox.addItem(temClass);
    			}
    		}
    	}else if(type.equals("生命周期")){
    		lifeCycleJTF.setText(lifeCycleTemplateStr);
    	}else if(type.equals("资料夹")){
    		locationJTF.setText(folderStr) ;
    	}
        
    }
   
    
    /**
     * 应用零部件普通搜索，添加新车型零部件
     * @param e ActionEvent
     */
    void addNewPartJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       
        //定义搜索器
        QmChooser qmChooser = new QmChooser("QMPart", "搜索零部件", this.getParentJFrame());
        qmChooser.setChildQuery(false);
        qmChooser.setLastIteration(true);
        try
        {
            qmChooser.setMultipleMode(false);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            setCursor(Cursor.getDefaultCursor());
            return;
        }
        //按照给定条件，执行搜索
        qmChooser.addListener(new QMQueryListener()
        {
            public void queryEvent(QMQueryEvent e)
            {
                try
                {
                    qmChooser_queryEventNew(e);
                }catch(QMException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        
        qmChooser.setVisible(true);

        setCursor(Cursor.getDefaultCursor());

    }
    
    /**
     * 搜索零部件监听事件方法
     * @param e 搜索监听事件
     * @throws QMException
     */
    private void qmChooser_queryEventNew(QMQueryEvent e) throws QMException
    {
        if(e.getType().equals(QMQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(QmQuery.OkCMD))
            {
                //按照所给条件，搜索获得所需零部件
                QmChooser c = (QmChooser)e.getSource();

                BaseValueIfc[] bvi = c.getSelectedDetails();
                if(bvi != null && bvi.length > 0)
                {
                	BaseValueIfc selectPart  = (BaseValueIfc)bvi[0];
                	
                   
                }
            }
        }

    }
    /**
     * 查看发布单  弹出瘦客户
     * @param e ActionEvent
     */
    void viewNoticeJButton_actionPerformed(ActionEvent e)
    {
   	 String bsoID = this.getBomAdoptNotice().getBsoID();
	 HashMap map = new HashMap();
     map.put("bsoID", bsoID);    
     RichToThinUtil.toWebPage("View_GYBomAdoptNotice.screen", map);
    }
    

    


    /**
     * 获得父窗口
     * @return javax.swing.JFrame
     * @roseuid 402A11F40212
     */
    public JFrame getParentJFrame()
    {
        return this.parentFrame;
    }



    



    /**
     * 设置界面模式（创建、更新或查看）。
     * @param aMode 新界面模式
     * @throws QMException
     */
    public void setViewMode(int aMode) throws QMException
    {
        if((aMode == this.UPDATE_MODE) || (aMode == this.CREATE_MODE) || (aMode == this.VIEW_MODE)||(aMode == this.SAVE_AS_MODE) )
        {
            mode = aMode;
        }
        switch(aMode)
        {
        case CREATE_MODE:
        { // 创建模式
            this.setCreateModel();
            break;
        }
        //CCBegin SS2
        case AUTOCREATE_MODE:
        { // 自动创建模式
            this.setAutoCreateModel();
            break;
        }
        //CCEnd SS2
        case UPDATE_MODE:
        { // 更新模式
            this.setUpdateModel();
            break;
        }
        case VIEW_MODE:
        { // 查看模式
            this.setViewModel();
            break;
        }
        case SAVE_AS_MODE:
        { // 另存为模式
            this.setSaveAsModel();
            break;
        }
        }
    }

    /**
     * 获得当前界面模式
     * @return 当前界面模式
     */
    public int getViewMode()
    {
        return mode;
    }

   
    /**
     * 设置界面为创建状态
     */
    private void setCreateModel()
    {

    	adoptnoticenumberJTF.setEditable(false);
    	adoptnoticenumberJTF.setText("自动编号...");
        locationJTF.setEditable(false);
        lifeCycleJTF.setEditable(false);
    	viewNoticeButton.setEnabled(false);
    	//jfBomNoticeJTF.setEditable(false);
    	jfBomNoticeJTF.setEnabled(false);
//CCBegin SS1
    	jfNoticeJTF.setEnabled(false);
//CCEnd SS1
    	cxPartJTF.setEnabled(false);
    	getBoxData(this.publicType);
    	viewNoticeButton.setEnabled(false);
    	//getBoxData("分类");
    	getBoxData("生命周期");
    	getBoxData("资料夹");
    	
        if(!this.isShowing())
        {
            this.setVisible(true);
        }
        isHaveSave = false;
        repaint();
    }
    
    //CCBegin SS2
    /**
     * 设置界面为创建状态
     */
    private void setAutoCreateModel()
    {
        mode = AUTOCREATE_MODE;
        adoptnoticenumberJTF.setEditable(false);
        adoptnoticenumberJTF.setText("自动编号...");
        locationJTF.setEditable(false);
        lifeCycleJTF.setEditable(false);
        viewNoticeButton.setEnabled(false);
        //jfBomNoticeJTF.setEditable(false);
        jfBomNoticeJTF.setEnabled(false);
        jfNoticeJTF.setEnabled(false);
        cxPartJTF.setEnabled(false);
        getBoxData(this.publicType);
        viewNoticeButton.setEnabled(false);
        //getBoxData("分类");
        getBoxData("生命周期");
        getBoxData("资料夹");
        
        if(!this.isShowing())
        {
            this.setVisible(true);
        }
        isHaveSave = false;
        repaint();
        
        String cxPartBsoID = this.getBomAdoptNotice().getTopPart();
        if(cxPartBsoID!=null&&!cxPartBsoID.equals("")){
            try
            {
                cxPart = (QMPartIfc)GYNoticeClientUtil.refresh(cxPartBsoID);
            }catch(QMException e)
            {
                e.printStackTrace();
            }
        }
    }
    //CCEnd SS2

    /**
     * 设置界面为更新状态 只有编号和部门不可修改
     * @throws QMException
     */
    private void setUpdateModel() throws QMException
    {
//        	publishTypeBox.setEnabled(false);
    	publishTypeBox.addItem(this.getBomAdoptNotice().getPublishType());
    	//classificationBox.setEnabled(false);
    	getBoxData(this.publicType);
    	classificationBox.setSelectedItem(this.getBomAdoptNotice().getClassification());
    	adoptnoticenumberJTF.setEditable(false);
    	adoptnoticenumberJTF.setText(this.getBomAdoptNotice().getAdoptnoticenumber());
    	adoptnoticenameJTF.setEditable(false);
    	adoptnoticenameJTF.setText(this.getBomAdoptNotice().getAdoptnoticename());
    	locationJTF.setEditable(false);
    	locationJTF.setText(this.getBomAdoptNotice().getLocation());
    	lifeCycleJTF.setEditable(false);
    	String lifeCycleTemplateBsoID = this.getBomAdoptNotice().getLifeCycleTemplate();
    	if(lifeCycleTemplateBsoID!=null&&!lifeCycleTemplateBsoID.equals("")){
    		LifeCycleTemplateIfc template = (LifeCycleTemplateIfc)GYNoticeClientUtil.refresh(lifeCycleTemplateBsoID);
    		lifeCycleJTF.setText(template.getLifeCycleName());
    	}else{
    		lifeCycleJTF.setText("");
    	}
    	
    	jfBomNoticeJTF.setEditable(false);
    	String jfNoticeBsoID = this.getBomAdoptNotice().getParentNotice();
    	if(jfNoticeBsoID!=null&&!jfNoticeBsoID.equals("")){
    		parentNotice = (GYBomAdoptNoticeIfc)GYNoticeClientUtil.refresh(jfNoticeBsoID);
    		jfBomNoticeJTF.setText(((GYBomAdoptNoticeIfc)parentNotice).getAdoptnoticenumber());
    		
    	}
//CCBegin SS1
    	jfNoticeJTF.setEditable(false);
    	String jfNoticeID = this.getBomAdoptNotice().getJfBomnotice();
    	if(jfNoticeID!=null&&!jfNoticeID.equals("")){
    		jfNoticeIfc = (BaseValueIfc)GYNoticeClientUtil.refresh(jfNoticeID);
    		if(jfNoticeIfc instanceof BomAdoptNoticeIfc){
    			jfNoticeJTF.setText(((BomAdoptNoticeIfc)jfNoticeIfc).getAdoptnoticenumber());
    		}else if(jfNoticeIfc instanceof BomChangeNoticeIfc){
    			jfNoticeJTF.setText(((BomChangeNoticeIfc)jfNoticeIfc).getAdoptnoticenumber());
    		}
    	}else{
    		jfNoticeIfc = null;
    	}
			addJFNoticeJButton.setEnabled(true);
//CCEnd SS1
    
    	cxPartJTF.setEditable(false);
    	String cxPartBsoID = this.getBomAdoptNotice().getTopPart();
    	if(cxPartBsoID!=null&&!cxPartBsoID.equals("")){
    		cxPart = (QMPartIfc)GYNoticeClientUtil.refresh(cxPartBsoID);
    		cxPartJTF.setText(cxPart.getPartNumber());
    	}
    	consdescJTF.setEditable(true);
    	consdescJTF.setText(this.getBomAdoptNotice().getConsdesc());
    		
    	addJFDocJButton.setEnabled(true);
    	searchPartButton.setEnabled(true);
    	saveNoticeButton.setEnabled(true);
    	cancelNoticeButton.setEnabled(true);
        isHaveSave = false;
    	
    	
    	
        if(!this.isShowing())
        {
            this.setVisible(true);
        }

        repaint();
    }
    /**
     * 设置界面为另存为模式
     * @throws QMException
     */
    private void setSaveAsModel() throws QMException
    {
//        	publishTypeBox.setEnabled(false);
    	publishTypeBox.addItem(this.getBomAdoptNotice().getPublishType());
    	//classificationBox.setEnabled(false);
    	getBoxData(this.publicType);
    	classificationBox.setSelectedItem(this.getBomAdoptNotice().getClassification());
    	adoptnoticenumberJTF.setEditable(false);
    	adoptnoticenumberJTF.setText("自动编号...");
    	adoptnoticenameJTF.setEditable(true);
    	adoptnoticenameJTF.setText(this.getBomAdoptNotice().getAdoptnoticename());
    	locationJTF.setEditable(false);
    	locationJTF.setText(this.getBomAdoptNotice().getLocation());
    	lifeCycleJTF.setEditable(false);
    	String lifeCycleTemplateBsoID = this.getBomAdoptNotice().getLifeCycleTemplate();
    	if(lifeCycleTemplateBsoID!=null&&!lifeCycleTemplateBsoID.equals("")){
    		LifeCycleTemplateIfc template = (LifeCycleTemplateIfc)GYNoticeClientUtil.refresh(lifeCycleTemplateBsoID);
    		lifeCycleJTF.setText(template.getLifeCycleName());
    	}else{
    		lifeCycleJTF.setText("");
    	}
    	
    	jfBomNoticeJTF.setEditable(false);
    	String jfNoticeBsoID = this.getBomAdoptNotice().getParentNotice();
    	if(jfNoticeBsoID!=null&&!jfNoticeBsoID.equals("")){
    		parentNotice = (GYBomAdoptNoticeIfc)GYNoticeClientUtil.refresh(jfNoticeBsoID);
    		jfBomNoticeJTF.setText(((GYBomAdoptNoticeIfc)parentNotice).getAdoptnoticenumber());
    		
    	}
//CCBegin SS1
    	jfNoticeJTF.setEditable(false);
    	String jfNoticeID = this.getBomAdoptNotice().getJfBomnotice();
    	if(jfNoticeID!=null&&!jfNoticeID.equals("")){
    		jfNoticeIfc = (BaseValueIfc)GYNoticeClientUtil.refresh(jfNoticeID);
    		if(jfNoticeIfc instanceof BomAdoptNoticeIfc){
    			jfNoticeJTF.setText(((BomAdoptNoticeIfc)jfNoticeIfc).getAdoptnoticenumber());
    		}else if(jfNoticeIfc instanceof BomChangeNoticeIfc){
    			jfNoticeJTF.setText(((BomChangeNoticeIfc)jfNoticeIfc).getAdoptnoticenumber());
    		}
    	}else{
    		jfNoticeIfc = null;
    	}
			addJFNoticeJButton.setEnabled(true);
//CCEnd SS1
   
    	cxPartJTF.setEditable(false);
    	String cxPartBsoID = this.getBomAdoptNotice().getTopPart();
    	if(cxPartBsoID!=null&&!cxPartBsoID.equals("")){
    		cxPart = (QMPartIfc)GYNoticeClientUtil.refresh(cxPartBsoID);
    		cxPartJTF.setText(cxPart.getPartNumber());
    	}
    	consdescJTF.setEditable(true);
    	consdescJTF.setText(this.getBomAdoptNotice().getConsdesc());
    		
    	addJFDocJButton.setEnabled(true);
    	searchPartButton.setEnabled(true);
    	saveNoticeButton.setEnabled(true);
    	cancelNoticeButton.setEnabled(true);
        isHaveSave = false;
        if(!this.isShowing())
        {
            this.setVisible(true);
        }

        repaint();
    }
    /**
     * 设置界面为查看状态
     * @throws QMException
     */
    private void setViewModel() throws QMException
    {
//      //设置调整BOM可选择
       // jTabbedPane1.setEnabledAt(1, true);
    	publishTypeBox.setEnabled(false);
    	publishTypeBox.addItem(this.getBomAdoptNotice().getPublishType());

    	classificationBox.setEnabled(false);
    	classificationBox.addItem(this.getBomAdoptNotice().getClassification());

    	adoptnoticenumberJTF.setEditable(false);
    	adoptnoticenumberJTF.setText(this.getBomAdoptNotice().getAdoptnoticenumber());
    	adoptnoticenameJTF.setEditable(false);
    	adoptnoticenameJTF.setText(this.getBomAdoptNotice().getAdoptnoticename());
    	locationJTF.setEditable(false);
    	locationJTF.setText(this.getBomAdoptNotice().getLocation());
    	lifeCycleJTF.setEditable(false);
    	String lifeCycleTemplateBsoID = this.getBomAdoptNotice().getLifeCycleTemplate();
    	if(lifeCycleTemplateBsoID!=null&&!lifeCycleTemplateBsoID.equals("")){
    		LifeCycleTemplateIfc template = (LifeCycleTemplateIfc)GYNoticeClientUtil.refresh(lifeCycleTemplateBsoID);
    		lifeCycleJTF.setText(template.getLifeCycleName());
    	}else{
    		lifeCycleJTF.setText("");
    	}
    	
    	jfBomNoticeJTF.setEditable(false);
    	String jfNoticeBsoID = this.getBomAdoptNotice().getParentNotice();
    	if(jfNoticeBsoID!=null&&!jfNoticeBsoID.equals("")){
    		parentNotice = (GYBomAdoptNoticeIfc)GYNoticeClientUtil.refresh(jfNoticeBsoID);
    		jfBomNoticeJTF.setText(((GYBomAdoptNoticeIfc)parentNotice).getAdoptnoticenumber());
    	}
//CCBegin SS1
    	jfNoticeJTF.setEditable(false);
    	String jfNoticeID = this.getBomAdoptNotice().getJfBomnotice();
    	if(jfNoticeID!=null&&!jfNoticeID.equals("")){
    	    jfNoticeIfc = (BaseValueIfc)GYNoticeClientUtil.refresh(jfNoticeID);
    		if(jfNoticeIfc instanceof BomAdoptNoticeIfc){
    			jfNoticeJTF.setText(((BomAdoptNoticeIfc)jfNoticeIfc).getAdoptnoticenumber());
    		}else if(jfNoticeIfc instanceof BomChangeNoticeIfc){
    			jfNoticeJTF.setText(((BomChangeNoticeIfc)jfNoticeIfc).getAdoptnoticenumber());
    		}
    	}else{
    		jfNoticeIfc = null;
    	}
			addJFNoticeJButton.setEnabled(false);
//CCEnd SS1
   
    	cxPartJTF.setEditable(false);
    	String cxPartBsoID = this.getBomAdoptNotice().getTopPart();
    	if(cxPartBsoID!=null&&!cxPartBsoID.equals("")){
    		QMPartIfc cxPart = (QMPartIfc)GYNoticeClientUtil.refresh(cxPartBsoID);
    		cxPartJTF.setText(cxPart.getPartNumber());
    	}
    	consdescJTF.setEditable(false);
    	consdescJTF.setText(this.getBomAdoptNotice().getConsdesc());
    	
    	
    	addJFDocJButton.setEnabled(false);

    	searchPartButton.setEnabled(false);
    	saveNoticeButton.setEnabled(false);
    	cancelNoticeButton.setEnabled(false);
    	
        isHaveSave = false;
    	
    	
        if(!this.isShowing())
        {
            this.setVisible(true);
        }
        repaint();
    }




    /**
     * 检验必填区域是否已有有效值
     * @return 如果必填区域已有有效值，则返回为真
     * @throws QMException
     */
    private boolean checkRequiredFields() throws QMException
    {

           boolean isOK = true;
           StringBuffer message = new StringBuffer();

           if(getViewMode() == CREATE_MODE||getViewMode() == UPDATE_MODE||getViewMode() == SAVE_AS_MODE)
           {
           	 //通知名称
               if(adoptnoticenameJTF==null||adoptnoticenameJTF.getText().equals("")){
               	message.append("发布名称不能为空!");
                   isOK = false;
                   adoptnoticenameJTF.grabFocus();
               }
               
               //通知名称长度
               if(adoptnoticenameJTF!=null&&!adoptnoticenameJTF.getText().equals("")){
             	  if(adoptnoticenameJTF.getText().length()>50){
                       message.append("发布名称长度不能超过50个字符！请检查输入!");
                       isOK = false;
                       adoptnoticenameJTF.grabFocus();
             	  }
               }
               
   	        // 检验分类是否为空
   	        if((classificationBox.getSelectedItem()==null)||(classificationBox.getSelectedItem().equals("")))
   	        {
   	        	message.append("分类不能为空!");
   	            isOK = false;
   	            classificationBox.grabFocus();
   	
   	        }
               // 车型不能为空
   	        if(cxPartJTF==null||cxPartJTF.getText().equals(""))
               {
               	message.append("车型不能为空!");
                   isOK = false;
                   cxPartJTF.grabFocus();
               }
               //说明不能超过1300字符
               if(consdescJTF!=null&&consdescJTF.getText().length()>1300){
               	message.append("说明不能超过1300字!");
                   isOK = false;
                   consdescJTF.grabFocus();
               }
           }

           if(!isOK)
           {
               throw new QMException(message.toString());
           }

           if(verbose)
           {
               System.out.println("checkRequiredFields() end...return: " + isOK);
           }
           return isOK;
    }



    /**
     * 保存定额明细单 保存新增的MainRationLink关联 删除欲删除的MainRationLink关联 关闭界面
     * @param e ActionEvent
     */
    void saveJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        save();
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * 保存.保存完后进入查看状态
     */
    public void save()
    {
//            System.out.println("getViewMode() == "+getViewMode());
        if(getViewMode() == CREATE_MODE||getViewMode() == SAVE_AS_MODE)
        {
//            System.out.println("11111111111111111111111111111111111111");
            saveWhenCreate();
        //CCBegin SS2
        }else if(getViewMode() == UPDATE_MODE||getViewMode() == AUTOCREATE_MODE)
        //CCEnd SS2
        {
//            System.out.println("22222222222222222222222222222222222222");
            saveWhenUpdate();
        }

        ((GYBomNoticeMainJFrame)parentFrame).stopProgress();

    }

   
    /**
     * 获得采用单
     * @return BomAdoptNoticeIfc
     */
    public GYBomAdoptNoticeIfc getBomAdoptNotice()
    {
        return myBomAdoptNotice;
    }
    /**
     * 设置采用单
     * @param BomAdoptNoticeIfc bomAdoptIfc 
     * @param int mode
     * @throws QMException
     */
    public void setBomAdoptNotice(GYBomAdoptNoticeIfc bomAdoptIfc, int mode) throws QMException
    {
//        System.out.println("mode   ===========  " + mode);
    	this.myBomAdoptNotice = bomAdoptIfc;
        setViewMode(mode);
//        adustPanle.setBomAdoptNotice(myBomAdoptNotice,this.mode); 
//        invoicePanel.setBomAdoptNotice(myBomAdoptNotice,this.mode); 
    }
    /**
     * 提交数据用以保存
     * @throws QMException
     */
    private Object[] commitAllData() throws QMException
    {
        Object[] data = new Object[9];
        //发布类型
        if(publishTypeBox.getSelectedItem()!=null&&!publishTypeBox.getSelectedItem().equals("")){
        	data[0] = publishTypeBox.getSelectedItem();
        }
      //分类
        if(classificationBox.getSelectedItem()!=null&&!classificationBox.getSelectedItem().equals("")){
        	data[1] = classificationBox.getSelectedItem();
        }
      
        //通知名称
        String adoptnoticenameStr = "";
        if(adoptnoticenameJTF!=null&&(!adoptnoticenameJTF.getText().equals(""))){
        	adoptnoticenameStr = adoptnoticenameJTF.getText();
        }
        data[2] = adoptnoticenameStr; 

        //生命周期
        String lifeCycleTemlate = "";
        if(lifeCycleJTF!=null&&(!lifeCycleJTF.getText().equals(""))){
        	lifeCycleTemlate = lifeCycleJTF.getText();
        }
        data[3] = lifeCycleTemlate; 
        
        //资料夹
        String folderStr = "";
        if(locationJTF!=null&&(!locationJTF.getText().equals(""))){
        	folderStr = locationJTF.getText();
        }
        data[4] = folderStr; 
        
       
        
        //整车发布单
        if(parentNotice!=null){
        	data[5] = parentNotice;
        }else{
        	data[5] = null;
        }
        //车型
        if(cxPart!=null){
        	data[6] = cxPart;
        }else{
        	data[6] = null;
        }
        
        //说明
        String remarkStr = "";
        if(consdescJTF!=null&&(!consdescJTF.getText().equals(""))){
        	remarkStr = consdescJTF.getText();
        }
        data[7] = remarkStr; 

//CCBegin SS1
        //解放设计变更单
        if(jfNoticeIfc!=null){
        	data[8] = jfNoticeIfc;
        }else{
        	data[8] = null;
        }
//CCEnd SS1
        return data;
    }
    /**
     * 保存采用单
     */
    private void saveWhenCreate()
    {
        try
        {

            // 检查必填区域是否已填
            boolean requiredFieldsFilled = checkRequiredFields();
            if(!requiredFieldsFilled)
            {
                isHaveSave = true;
                return;
            }
            // 设置所有属性和关联，并获得信息封装对象。
            Object[] wrapData = commitAllData();
            // 显示进度
            ((GYBomNoticeMainJFrame)parentFrame).startProgress();
            // 调用服务,执行保存.返回采用单对象
            Class[] paraClass = {Object[].class};
            Object[] obj = {wrapData};
            GYBomAdoptNoticeIfc newInfo;
            Object[] obj1 = (Object[])GYNoticeHelper.requestServer("GYBomNoticeService", "createFrameAndBodyBomAdoptNotice", paraClass, obj);
            newInfo = (GYBomAdoptNoticeIfc)obj1[0];
            // 把新对象挂上树
            GYBomAdoptNoticeTreeObject newTObj = new  GYBomAdoptNoticeTreeObject(newInfo);

            GYBomNoticeTreePanel treepanel = ((GYBomNoticeMainJFrame)parentFrame).getBomNoticeListTreePanel();
            
            treepanel.addNode(newTObj);
            this.setBomAdoptNotice(newInfo, this.VIEW_MODE);
            this.isHaveSave = true;
            //保存调整BOM 
            adustPanle.save();
            //散发清单
            invoicePanel.saveCountInvoice(newInfo);
            // 设置焦点
            treepanel.setNodeSelected(newTObj);
        
        }catch(QMException ex)
        {
        	((GYBomNoticeMainJFrame)parentFrame).stopProgress();
            ex.printStackTrace();
            DialogFactory.showInformDialog(this, ex.getClientMessage());

        }

    }
    /**
     * 更新采用单
     */
    private void saveWhenUpdate()
    {
        try
        {
System.out.println("1111更新采用单112");
            // 检查必填区域是否已填
            boolean requiredFieldsFilled = checkRequiredFields();
            if(!requiredFieldsFilled)
            {
                isHaveSave = true;
                return;
            }
            // 设置所有属性和关联，并获得信息封装对象。
            Object[] wrapData = commitAllData();
            // 显示进度
            ((GYBomNoticeMainJFrame)parentFrame).startProgress();
            // 调用服务,执行保存.返回定额单值对象
            Class[] paraClass = {Object[].class,GYBomAdoptNoticeIfc.class};
            Object[] obj = {wrapData,this.getBomAdoptNotice()};
            GYBomAdoptNoticeIfc newInfo;
            Object[] obj1 = (Object[])GYNoticeHelper.requestServer("GYBomNoticeService", "updateFrameAndBodyBomAdoptNotice", paraClass, obj);
            newInfo = (GYBomAdoptNoticeIfc)obj1[0];
         // 更新树结点

            GYBomAdoptNoticeTreeObject newTObj = new  GYBomAdoptNoticeTreeObject(newInfo);
            GYBomNoticeTreePanel treepanel = ((GYBomNoticeMainJFrame)parentFrame).getBomNoticeListTreePanel();

            treepanel.updateNode(newTObj);
            this.setBomAdoptNotice(newInfo, this.VIEW_MODE);
            this.isHaveSave = true;
            //保存调整BOM 
//CCBegin SS1
						adustPanle.setIsPaint(false);
            adustPanle.save();
						adustPanle.setIsPaint(true);
//CCBegin SS1
            //散发清单
            invoicePanel.saveCountInvoice(newInfo);
            // 设置焦点
            treepanel.setNodeSelected(newTObj);
            // 显示进度
            ((GYBomNoticeMainJFrame)parentFrame).stopProgress();
        }catch(QMException ex)
        {
        	((GYBomNoticeMainJFrame)parentFrame).stopProgress();
            ex.printStackTrace();
            DialogFactory.showInformDialog(this, ex.getClientMessage());

        }

    }
   

    /**
     * 提示是否保存,保存，并关闭界面
     * @param e ActionEvent
     */
    void quitJButton_actionPerformed(ActionEvent e)
    {
        quit();
    }

    // 0代表是，1代表否  2代表关闭
    public void quit()
    {
        if(!isHaveSave)
        {
            if(this.getViewMode() != VIEW_MODE)
            {
                String s = "是否保存采用细单？";
                int k = confirmAction(s);
                if(k == 0)
                {
                    //isHaveSave = true;
                    save();

                }else if(k == 1)
                {
                    isHaveSave = true;
                    try
                    {
                        LockIfc lock = null;
                        if(((GYBomNoticeMainJFrame)parentFrame).getController().getHashMap() != null)
                        {
                            Collection col = null;
                            col = ((GYBomNoticeMainJFrame)parentFrame).getController().getHashMap().values();
                            Iterator iterator = col.iterator();
                            while(iterator.hasNext())
                            {
                                lock = (LockIfc)iterator.next();
                                if(lock.getLocker() == null || lock.getLocker().equals(""))
                                {
                                    return; //如果没有加锁 直接返回
                                }

                                Class[] theClass = {LockIfc.class, String.class};
                                Object[] obj = {lock, lock.getLocker()};
                                GYNoticeHelper.requestServer("GYBomNoticeService", "unlock", theClass, obj);
                            }
                        }
                    }catch(QMException ex)
                    {
                        ex.printStackTrace();
                        DialogFactory.showWarningDialog(this, ex.getClientMessage());
                    }
                    this.setVisible(false);
                }
                if(((GYBomNoticeMainJFrame)parentFrame).getController().getHashMap() != null)
                {
                    ((GYBomNoticeMainJFrame)parentFrame).getController().getHashMap().clear();
                }
            }else
            {
                this.setVisible(false);
            }
        }
    }
    /**x
     * 搜索整车发布单
     * @param e ActionEvent
     */
    void addJFNoticeJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SearchGYBomNoticeDialog searchDialog = new SearchGYBomNoticeDialog((GYBomNoticeMainJFrame)this.parentFrame,true);
        searchDialog.setVisible(true);
        setCursor(Cursor.getDefaultCursor());
//CCBegin SS1
				if (parentNotice!=null)
				{
					try {
						jfNoticeIfc=(BaseValueIfc)GYNoticeClientUtil.refresh(parentNotice.getJfBomnotice());
						setJfNoticeIfc(jfNoticeIfc);
						setParts();
					} catch (QMException ex) {
						ex.printStackTrace();
					}
				}
//CCEnd SS1

    }
//CCBegin SS1
    void addZCJFNoticeJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SearchJFNoticeDialog searchDialog = new SearchJFNoticeDialog((GYBomNoticeMainJFrame)this.parentFrame);

        searchDialog.setVisible(true);
        setCursor(Cursor.getDefaultCursor());
				setParts();
    }
    private void setParts(){
				if (jfNoticeIfc!=null)
				{
					try {
         		Class[] paraClass1 = {BaseValueIfc.class};
          	Object[] obj1 = {jfNoticeIfc};
          	Vector vector1 = (Vector)GYNoticeHelper.requestServer("GYBomNoticeService", "getNouseByID", paraClass1, obj1);
          	Vector vector2 = (Vector)GYNoticeHelper.requestServer("GYBomNoticeService", "getUseByID", paraClass1, obj1);

						if (adustPanle.getBomAdoptNotice()==null)
							adustPanle.setBomAdoptNotice1(myBomAdoptNotice,this.mode);
			    	adustPanle.setMultiListData("不采用",vector1);
			    	adustPanle.setMultiListData("采用",vector2);
			      adustPanle.repaint();
					} catch (QMException ex) {
						ex.printStackTrace();
					}
	    }
    }
//CCEnd SS1
    /**
     * 搜索中心变更单
     * @param e ActionEvent
     */
    void addZXNoticeJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SearchZXNoticeDialog searchDialog = new SearchZXNoticeDialog((GYBomNoticeMainJFrame)this.parentFrame);
        searchDialog.setVisible(true);
        setCursor(Cursor.getDefaultCursor());

    }
    /**
     * 应用零部件普通搜索，添加基础车型零部件
     * @param e ActionEvent
     */
    void addZcPartJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       
        //定义搜索器
        QmChooser qmChooser = new QmChooser("QMPart", "搜索零部件", this.getParentJFrame());
        qmChooser.setChildQuery(false);
        qmChooser.setLastIteration(true);
        try
        {
            qmChooser.setMultipleMode(false);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            setCursor(Cursor.getDefaultCursor());
            return;
        }
        //按照给定条件，执行搜索
        qmChooser.addListener(new QMQueryListener()
        {
            public void queryEvent(QMQueryEvent e)
            {
                try
                {
                    qmChooser_queryEventZcPart(e);
                }catch(QMException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        
        qmChooser.setVisible(true);

        setCursor(Cursor.getDefaultCursor());

    }
    
    /**
     * 搜索零部件监听事件方法
     * @param e 搜索监听事件
     * @throws QMException
     */
    private void qmChooser_queryEventZcPart(QMQueryEvent e) throws QMException
    {
        if(e.getType().equals(QMQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(QmQuery.OkCMD))
            {
                //按照所给条件，搜索获得所需零部件
                QmChooser c = (QmChooser)e.getSource();

                BaseValueIfc[] bvi = c.getSelectedDetails();
                if(bvi != null && bvi.length > 0)
                {

                	BaseValueIfc selectPart  = (BaseValueIfc)bvi[0];
                	cxPart = (QMPartInfo)selectPart;
                	cxPartJTF.setText(cxPart.getPartNumber());
                   
                }
            }
        }

    }
    /**
     * 显示确认对话框
     * @param s 在对话框中显示的信息
     * @return 如果用户选择了“确定”按钮，则返回true;否则返回false
     */
    private int confirmAction(String s)
    {
        String title = "退出";
        JOptionPane okCancelPane = new JOptionPane();
        int i = okCancelPane.showConfirmDialog(getParentJFrame(), s, title, JOptionPane.YES_NO_OPTION);
        if(i == JOptionPane.YES_OPTION)
        {
            return 0;
        }
        if(i == JOptionPane.NO_OPTION)
        {

            return 1;
        }
        return 2;
    }
   }
/**
 * <p>Title: 搜索整车发布单
 * @author 文柳
 * @version 1.0
 */
class CJAdoptNoticeViewPanel_addJFDocJButton_actionAdapter implements java.awt.event.ActionListener
{
	CJAdoptNoticeViewPanel adaptee;

	CJAdoptNoticeViewPanel_addJFDocJButton_actionAdapter(CJAdoptNoticeViewPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.addJFNoticeJButton_actionPerformed(e);
    }
}
//CCBegin SS1
class ZCAdoptNoticeViewPanel_addJFDocJButton_actionAdapter implements java.awt.event.ActionListener
{
	CJAdoptNoticeViewPanel adaptee;

	ZCAdoptNoticeViewPanel_addJFDocJButton_actionAdapter(CJAdoptNoticeViewPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.addZCJFNoticeJButton_actionPerformed(e);
    }
}
//CCBegin SS1

class CJAdoptNoticeViewPanel_saveJButton_actionAdapter implements java.awt.event.ActionListener
{
	CJAdoptNoticeViewPanel adaptee;

	CJAdoptNoticeViewPanel_saveJButton_actionAdapter(CJAdoptNoticeViewPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.saveJButton_actionPerformed(e);
    }
}
class CJAdoptNoticeViewPanel_addCxPartJButton_actionAdapter implements java.awt.event.ActionListener
{
	CJAdoptNoticeViewPanel adaptee;

	CJAdoptNoticeViewPanel_addCxPartJButton_actionAdapter(CJAdoptNoticeViewPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.addZcPartJButton_actionPerformed(e);
    }
}