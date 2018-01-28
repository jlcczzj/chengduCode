/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 修改TD8290 文柳 2014-7-17
 * SS2 修改TD8457 文柳 2014-7-18
 * SS3 增加计算数量按钮 文柳 2014-7-30
 * SS4 修改在"调整BOM"Tab页中,在“采用”列表中无新添加零件按钮 xianglx 2014-8-18
 * SS5 工艺BOM管理器中采用件列表中缺少计算数量按钮 xianglx 2014-8-28
 * SS6 工艺BOM管理器中添加解放设计变更单会带入相应单据的采用和不采用件 xianglx 2014-8-28
 * SS7 增加添加父件和子组功能 刘家坤 2017-3-3
 */

package com.faw_qm.gybomNotice.client.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.faw_qm.cappclients.util.ComponentMultiList;

import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.gybomNotice.client.util.GYNoticeClientUtil;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticePartLinkIfc;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticePartLinkInfo;
import com.faw_qm.gybomNotice.util.GYNoticeHelper;
import com.faw_qm.lock.model.LockIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;


/**
 * <p>Title:调整BOM主界面。</p> <p>Description: </p> <p>Copyright: Copyright (c) 2014</p> <p>Company: 启明信息技术股份有限公司</p>
 * @author 文柳
 * @version 1.0
 */

public class ZCAdoptNoticeAdjustPanel extends RightPanel
{
	
	private GYBomAdoptNoticeIfc myBomAdoptNotice;
    private static final long serialVersionUID = 1L;
    private static final boolean verbose = GYNoticeHelper.VERBOSE;

  


    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel6 = new JPanel();//button页面
    JPanel adoptNoticePanel = new JPanel();//采用页面
    JPanel noNoticePanel = new JPanel();//采用页面
    JPanel adoptNoticeButtonPanel = new JPanel();//采用按钮页面
    JPanel noNoticeButtonPanel = new JPanel();//不采用按钮页面

    JButton saveJButton = new JButton();
    JButton quitJButton = new JButton();
    public ComponentMultiList adoptList = new ComponentMultiList();//采用列表
    public ComponentMultiList noList = new ComponentMultiList();//采用列表
    private JScrollPane jScrollPane2 = new JScrollPane();
    
    //CCBegin SS3
    private JButton countPartsButton = new JButton();
    //CCEnd SS3

    //不采用按钮
    private JButton addPartButton = new JButton();
    private JButton delPartButton = new JButton();
    private JButton replaceNoticeButton = new JButton();
    //采用按钮

        //CCBegin SS5
    private JButton countAdoptPartButton = new JButton();
        //CCEnd SS5
        //CCBegin SS4
    private JButton addAdoptPartButton = new JButton();
        //CCEnd SS4
    private JButton delAdoptPartButton = new JButton();
    //CCBegin SS7
    private JButton addPJButton = new JButton();//采用
    private JButton addPNAJButton = new JButton();//不采用
    int index = 0;
    int ipair  = 0;//替换对，确定零件的唯一一对替换关系
        //CCEnd SS7
    
    //控制按钮
    private JButton saveNoticeButton = new JButton();
    private JButton cancelNoticeButton = new JButton();
    //private JButton viewNoticeButton = new JButton();
    //private JButton viewSubNoticeButton = new JButton();
    
    /** 界面显示模式（更新模式）标记 */
    public final static int UPDATE_MODE = 0;

    /** 界面显示模式（创建模式）标记 */
    public final static int CREATE_MODE = 1;

    /** 界面显示模式（查看模式）标记 */
    public final static int VIEW_MODE = 2;

    /** 界面显示模式（另存为模式）标记 */
    public final static int SAVE_AS_MODE = 3;
    /** 界面模式(默认为查看) */
    public int mode = VIEW_MODE;


    private JFrame parentFrame = null;

    /**
     * 是否执行了退出操作
     */
    public boolean isHaveSave = false;
    
    private boolean firstSelect = false;
    
    /**
     * 构造函数
     */
    public ZCAdoptNoticeAdjustPanel(GYBomNoticeMainJFrame parent)
    {

        try
        {
            this.parentFrame = parent;
            jbInit();
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    /**
     * 初始化方法
     */
    void jbInit() throws QMException
    {
        jScrollPane2.setBorder(BorderFactory.createEtchedBorder());

        this.setLayout( new GridBagLayout());
        adoptNoticePanel.setLayout( new GridBagLayout());
        noNoticePanel.setLayout( new GridBagLayout());
        adoptNoticeButtonPanel.setLayout( new GridBagLayout());
        adoptNoticeButtonPanel.setBackground(Color.lightGray);
        noNoticeButtonPanel.setLayout( new GridBagLayout());
        noNoticeButtonPanel.setBackground(Color.lightGray);
        
        //CCBegin SS3
        countPartsButton.setMaximumSize(new Dimension(110, 23));
        countPartsButton.setMinimumSize(new Dimension(110, 23));
        countPartsButton.setPreferredSize(new Dimension(110, 23));
        countPartsButton.setText("计算数量"); 
        countPartsButton.addActionListener(new ZCAdoptNoticeAdjustPanel_countPartsButton_actionAdapter(this));
        //CCEnd SS3
//      CCBegin SS7
        countPartsButton.setVisible(false);

	    addPJButton.setMaximumSize(new Dimension(100, 23));
	    addPJButton.setMinimumSize(new Dimension(100, 23));
	    addPJButton.setPreferredSize(new Dimension(100, 23));
	    addPJButton.setToolTipText("addParent");
	    addPJButton.setText("添加父件");
	    addPJButton.addActionListener(new
	    		ZCAdoptNoticeAdjustPanel_addPJButtonJButton_actionAdapter(this));
	    
	    addPNAJButton.setMaximumSize(new Dimension(100, 23));
	    addPNAJButton.setMinimumSize(new Dimension(100, 23));
	    addPNAJButton.setPreferredSize(new Dimension(100, 23));
	    addPNAJButton.setToolTipText("addParent");
	    addPNAJButton.setText("添加父件");
	    addPNAJButton.addActionListener(new
	    		ZCAdoptNoticeAdjustPanel_addPNAJButtonJButton_actionAdapter(this));
//	  CCEnd SS7
        //不采用按钮
        addPartButton.setMaximumSize(new Dimension(110, 23));
        addPartButton.setMinimumSize(new Dimension(110, 23));
        addPartButton.setPreferredSize(new Dimension(110, 23));
        addPartButton.setText("添加零件"); 
        addPartButton.addActionListener(new ZCAdoptNoticeAdjustPanel_addPartButton_actionAdapter(this));
        
        delPartButton.setMaximumSize(new Dimension(110, 23));
        delPartButton.setMinimumSize(new Dimension(110, 23));
        delPartButton.setPreferredSize(new Dimension(110, 23));
        delPartButton.setText("删除零件"); 
        delPartButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	deletNoAdoptPartJButton_actionPerformed(e);
            }
        }); 
        
        replaceNoticeButton.setMaximumSize(new Dimension(110, 23));
        replaceNoticeButton.setMinimumSize(new Dimension(110, 23));
        replaceNoticeButton.setPreferredSize(new Dimension(110, 23));
        replaceNoticeButton.setText("替换为"); 
        replaceNoticeButton.addActionListener(new ZCAdoptNoticeAdjustPanel_addReplacePartButton_actionAdapter(this));

        //CCBegin SS5
        countAdoptPartButton.setMaximumSize(new Dimension(110, 23));
        countAdoptPartButton.setMinimumSize(new Dimension(110, 23));
        countAdoptPartButton.setPreferredSize(new Dimension(110, 23));
        countAdoptPartButton.setText("计算数量"); 
        countAdoptPartButton.addActionListener(new ZCAdoptNoticeAdjustPanel_countAdoptPartsButton_actionAdapter(this));
//      CCBegin SS7
        countAdoptPartButton.setVisible(false);
//      CCEnd SS7
        //CCEnd SS5
        //CCBegin SS4
        addAdoptPartButton.setMaximumSize(new Dimension(110, 23));
        addAdoptPartButton.setMinimumSize(new Dimension(110, 23));
        addAdoptPartButton.setPreferredSize(new Dimension(110, 23));
        addAdoptPartButton.setText("添加零件"); 
        addAdoptPartButton.addActionListener(new ZCAdoptNoticeAdjustPanel_addAdoptPartButton_actionAdapter(this));
        //CCEnd SS4
        delAdoptPartButton.setMaximumSize(new Dimension(110, 23));
        delAdoptPartButton.setMinimumSize(new Dimension(110, 23));
        delAdoptPartButton.setPreferredSize(new Dimension(110, 23));
        delAdoptPartButton.setText("删除零件"); 
        delAdoptPartButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
            	deletAdoptPartJButton_actionPerformed(e);
            }
        }); 
        
//        viewNoticeButton.setMaximumSize(new Dimension(110, 23));
//        viewNoticeButton.setMinimumSize(new Dimension(110, 23));
//        viewNoticeButton.setPreferredSize(new Dimension(110, 23));
//        viewNoticeButton.setText("查看通知单");
//        viewNoticeButton.addActionListener(new java.awt.event.ActionListener()
//        {
//            public void actionPerformed(ActionEvent e)
//            {
//            //   viewNoticeJButton_actionPerformed(e);
//            }
//        });
        saveNoticeButton.setMinimumSize(new Dimension(110, 23));
        saveNoticeButton.setPreferredSize(new Dimension(110, 23));
        saveNoticeButton.setText("保存");
        saveNoticeButton.addActionListener(new ZCAdoptNoticeAdjustPanel_saveJButton_actionAdapter(this));   	

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

        
        //功能按钮
       
//        jPanel6.add(viewNoticeButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        jPanel6.add(saveNoticeButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        jPanel6.add(cancelNoticeButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        
        noNoticeButtonPanel.add(new JLabel("不采用"), new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 80), 0, 0));
        //CCBegin SS3
        noNoticeButtonPanel.add(countPartsButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 80, 2, 8), 0, 0));
        noNoticeButtonPanel.add(addPartButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 80, 2, 8), 0, 0));
        noNoticeButtonPanel.add(delPartButton, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        noNoticeButtonPanel.add(replaceNoticeButton, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));

       
        //CCEnd SS3
       
        adoptNoticeButtonPanel.add(new JLabel("采用"), new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 80), 0, 0));
        //CCBegin SS4
        adoptNoticeButtonPanel.add(countAdoptPartButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 80, 2, 8), 0, 0));
        adoptNoticeButtonPanel.add(addAdoptPartButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 80, 2, 8), 0, 0));
        adoptNoticeButtonPanel.add(delAdoptPartButton, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        //CCEnd SS4
//      CCBegin SS7
        noNoticeButtonPanel.add(addPNAJButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 80, 2, 8), 0, 0));
        adoptNoticeButtonPanel.add(addPJButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 80, 2, 8), 0, 0));
//      CCEnd SS7
        adoptNoticePanel.add(adoptList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 8, 0, 0), 0, 0));
        noNoticePanel.add(noList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 8, 0, 0), 0, 0));
        this.add(noNoticeButtonPanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8, 17, 0, 8), 0, 0));
        this.add(noNoticePanel, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 8, 8, 8), 0, 0));
        this.add(adoptNoticeButtonPanel, new GridBagConstraints(0, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(8, 17, 0, 8), 0, 0));
        this.add(adoptNoticePanel, new GridBagConstraints(0, 4, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 8, 2, 8), 0, 0));
        this.add(jPanel6, new GridBagConstraints(0, 5, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        getQMMultiListTitle("采用");
        getQMMultiListTitle("不采用");
    }
   
    /**
     * 获得子采用单表头
     * @param String type 类型
     */
    private void getQMMultiListTitle(String type)
    {
    	if(type.equals("采用")){
//    		CCBegin SS7
    		String[] subNoticeTitle = new String[16];
//        	CCEnd SS7
        	subNoticeTitle[0]="bsoID";
        	subNoticeTitle[1]="编号";
        	subNoticeTitle[2]="名称";
        	subNoticeTitle[3]="版本";
        	subNoticeTitle[4]="数量";
        	subNoticeTitle[5]="制造路线";
        	subNoticeTitle[6]="装配路线";
        	subNoticeTitle[7]="视图";
        	subNoticeTitle[8]="生命周期状态";
        	subNoticeTitle[9]="虚拟";
        	subNoticeTitle[10]="不采用";
        	subNoticeTitle[11]="replacePartBsoID";
        	 //CCBegin SS7
        	subNoticeTitle[12]="父件编号";
        	subNoticeTitle[13]="子组";
        	subNoticeTitle[14]="父件id";
        	subNoticeTitle[15]="替换对";
        	int[] mutListSize = new int[16];
//        	CCEnd SS7
        	mutListSize[0]=0;
        	mutListSize[1]=1;
        	mutListSize[2]=1;
        	mutListSize[3]=1;
        	mutListSize[4]=1;
        	mutListSize[5]=1;
        	mutListSize[6]=1;
        	mutListSize[7]=1;
        	mutListSize[8]=1;
        	mutListSize[9]=1;
        	mutListSize[10]=1;
        	mutListSize[11]=0;
            //	CCBegin SS7
        	mutListSize[12]=1;
        	mutListSize[13]=1;
        	mutListSize[14]=0;
        	mutListSize[15]=0;
//        	
        	int[] editCol = new int[]{0,1,2,3,5,6,7,8,9,10,12,13};
//        	CCEnd SS7
        	adoptList.setColsEnabled(editCol, false);
        	adoptList.setHeadings(subNoticeTitle);
        	adoptList.setRelColWidth(mutListSize);
        	adoptList.setCellEditable(false);
        	adoptList.setMultipleMode(true);
        	adoptList.setAllowSorting(false);
        	adoptList.setBackGround(Color.lightGray);
    	}else if(type.equals("不采用")){
    		String[] noSubNoticeTitle = new String[12];
    		noSubNoticeTitle[0]="bsoID";
    		noSubNoticeTitle[1]="编号";
    		noSubNoticeTitle[2]="名称";
    		noSubNoticeTitle[3]="版本";
    		noSubNoticeTitle[4]="替换前数量";
        	noSubNoticeTitle[5]="制造路线";
        	noSubNoticeTitle[6]="装配路线";
        	noSubNoticeTitle[7]="视图";
        	 //CCBegin SS7
        	noSubNoticeTitle[8]="父件编号";
        	noSubNoticeTitle[9]="子组";
        	noSubNoticeTitle[10]="父件id";
        	noSubNoticeTitle[11]="替换对";
           	int[] mutListSize2 = new int[12];
     
//        	CCEnd SS7
    		
    		//显示列
     
        	mutListSize2[0]=0;
        	mutListSize2[1]=1;
        	mutListSize2[2]=1;
        	mutListSize2[3]=1;
        	mutListSize2[4]=1;
        	mutListSize2[5]=1;
        	mutListSize2[6]=1;
        	mutListSize2[7]=1;
        	 //CCBegin SS7
        	mutListSize2[8]=1;
        	mutListSize2[9]=1;
        	mutListSize2[10]=0;
        	mutListSize2[11]=0;
//        	CCEnd SS7
        	//设置可编辑列【只有数量、互换性是可编辑】
        	int[] editCol = new int[]{0,1,2,3,5,6,7,8,9,10,11};
        	
         	noList.setHeadings(noSubNoticeTitle);
        	noList.setRelColWidth(mutListSize2);
        	noList.setCellEditable(true);
        	noList.setColsEnabled(editCol, false);
        	noList.setMultipleMode(true);
        	noList.setAllowSorting(false);
        	noList.setBackGround(Color.lightGray);
    	}
    
    	
    }

    /**
     * 列表赋值（添加零件时用到）
     * @param String type 类型（采用、不采用）
     * @param QMPartInfo[] partInfo 零件集合
     * @author wenl
     */
    public void setMultiListData(String type,Vector strs)
    {
    	if (type.equals("不采用")){
    		//noList.clear();
    		int ii = noList.getRowCount();
  
    		if(strs!=null&&strs.size()>0){
    			for(int i = 0 ; i <strs.size();i++){
        			String[] part = (String[])strs.get(i);
        			noList.addTextCell(ii+i, 0, part[0]);
                	noList.addTextCell(ii+i, 1, part[1]);
                	noList.addTextCell(ii+i, 2, part[2]);
                	noList.addTextCell(ii+i, 3, part[3]);
                	noList.addTextCell(ii+i, 4, part[6]);
                	noList.addTextCell(ii+i, 5, part[7]);
                	noList.addTextCell(ii+i, 6, part[8]);
                	noList.addTextCell(ii+i, 7, part[5]);
                	noList.addTextCell(ii+i, 8, "");
                	noList.addTextCell(ii+i, 9, "");
                	noList.addTextCell(ii+i, 10, "");
        		}
    		}
        //CCBegin SS4
    	}else if(type.equals("采用")){
    		int ii = adoptList.getRowCount();
    		if(strs!=null&&strs.size()>0){
    			for(int i = 0 ; i <strs.size();i++){
        			String[] part = (String[])strs.get(i);
        			adoptList.addTextCell(ii+i, 0, part[0]);
        			adoptList.addTextCell(ii+i, 1, part[1]);
        			adoptList.addTextCell(ii+i, 2, part[2]);
        			adoptList.addTextCell(ii+i, 3, part[3]);
        			adoptList.addTextCell(ii+i, 4, part[6]);
        			adoptList.addTextCell(ii+i, 5, part[7]);
        			adoptList.addTextCell(ii+i, 6, part[8]);
        			adoptList.addTextCell(ii+i, 7, part[5]);
        			adoptList.addTextCell(ii+i, 8, part[4]);
        			adoptList.addTextCell(ii+i, 9, part[9]);
        			adoptList.addTextCell(ii+i, 10, null);
 //       			adoptList.addTextCell(ii+i, 10, replacePartNumber);
 //       			adoptList.addTextCell(ii+i, 11, replacePartBsoID);
        			 //CCBegin SS7
        			adoptList.addTextCell(ii+i, 12, "");
        			adoptList.addTextCell(ii+i, 13, "");
//                	CCEnd SS7

        		}
    		}
        //CCBegin SS4

    	}else if(type.equals("替换")){
    		ipair++;
    		int ii = adoptList.getRowCount();
    		int[] selectNoListRows = noList.getSelectedRows();
    		String replacePartNumber = noList.getCellText(selectNoListRows[0],1 );
    		String replacePartBsoID = noList.getCellText(selectNoListRows[0], 0);
    		
    		 //CCBegin SS7
    		String parentPartBsoID = noList.getCellText(selectNoListRows[0], 2);
    		String subGroup = noList.getCellText(selectNoListRows[0], 3);
    		//设置替换对
    		noList.addTextCell(selectNoListRows[0], 11, String.valueOf(ipair));
//        	CCEnd SS7 
    		if(strs!=null&&strs.size()>0){
    			for(int i = 0 ; i <strs.size();i++){
        			String[] part = (String[])strs.get(i);
        			adoptList.addTextCell(ii+i, 0, part[0]);
        			adoptList.addTextCell(ii+i, 1, part[1]);
        			adoptList.addTextCell(ii+i, 2, part[2]);
        			adoptList.addTextCell(ii+i, 3, part[3]);
        			adoptList.addTextCell(ii+i, 4, part[6]);
        			adoptList.addTextCell(ii+i, 5, part[7]);
        			adoptList.addTextCell(ii+i, 6, part[8]);
        			adoptList.addTextCell(ii+i, 7, part[5]);
        			adoptList.addTextCell(ii+i, 8, part[4]);
        			adoptList.addTextCell(ii+i, 9, part[9]);
        			adoptList.addTextCell(ii+i, 10, replacePartNumber);
        			adoptList.addTextCell(ii+i, 11, replacePartBsoID);
        			 //CCBegin SS7
        			adoptList.addTextCell(ii+i, 12, "");
        			adoptList.addTextCell(ii+i, 13, "");
        			adoptList.addTextCell(ii+i, 15, String.valueOf(ipair));
        			
//                	CCEnd SS7
    			}
    		}
    		
//    		自动打开”添加父件界面“
     	   setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Vector vecP =  new Vector();
            // index = adoptList.getSelectedRow();
           // System.out.println("ii=="+ii);
            if(ii>=0){
            	String adoptPartBsoID = adoptList.getCellText(ii, 0);
    			Class[] theClass = { String.class};
    			Object[] obj = { adoptPartBsoID };
    			try {
    				 vecP = (Vector) GYNoticeHelper.requestServer("GYBomService","getParentPart", theClass, obj);
    			} catch (QMException e1) {
    				// TODO 自动生成 catch 块
    				e1.printStackTrace();
    			}
    			//adoptList.addTextCell(j, 4, partQuantity);
              
            }
            index = ii;
            GYPartentPartListSearchJDialog searchJDialog = new GYPartentPartListSearchJDialog(this.getParentJFrame(), this,"采用",vecP);
            searchJDialog.setVisible(true);
            setCursor(Cursor.getDefaultCursor());
    		
    	} 

    }
    /**
     * 列表赋值（通过对象获得数据时用到）
     * @param Vector noticePartLinkVec
     * @author wenl
     */
    public void setMultiListData(Vector noticePartLinkVec)
    {
		adoptList.clear();
		noList.clear();
		int j = 0;//采用列表计数器
		int ii = 0;//不采用列表计数器
    	if(noticePartLinkVec!=null&&noticePartLinkVec.size()>0){
    		try {
				for(int i = 0 ; i < noticePartLinkVec.size();i++){
					GYBomAdoptNoticePartLinkIfc link = (GYBomAdoptNoticePartLinkIfc)noticePartLinkVec.get(i);
					
					String replacePartBsoID = link.getLinkPart();
					QMPartInfo replacePart = null;
					if(replacePartBsoID!=null&&!replacePartBsoID.equals("")){
						replacePart = (QMPartInfo)GYNoticeClientUtil.refresh(replacePartBsoID);
					}
			
					if(link.getAdoptBs().equals("不采用")){
							noList.addTextCell(j, 0, link.getPartID());
						
				        	noList.addTextCell(j, 1, link.getPartNumber());
				      
				        	noList.addTextCell(j, 2, link.getPartName());
				        	noList.addTextCell(j, 3, link.getVersionValue());
				    	
				        	noList.addTextCell(j, 4, link.getSl());
				        	noList.addTextCell(j, 5, link.getZzlx());
				        	noList.addTextCell(j, 6, link.getZplx());
				        	noList.addTextCell(j, 7, link.getPartView());
				        
				        	//CCBegin SS7
				        	String pbsoid = link.getBz2();
				        	if(pbsoid!=null&&!pbsoid.equals("")){
//				        		 获取父件编号
					        	  Class[] paraClass = {String.class};
			                       Object[] obj = {pbsoid};
			                       QMPartInfo zcPart = (QMPartInfo)GYNoticeHelper.requestServer("PersistService", "refreshInfo", paraClass, obj);
			                       noList.addTextCell(j, 8, zcPart.getPartNumber());
					
						        	noList.addTextCell(j, 10, zcPart.getBsoID());
				        	}else{
				        	      noList.addTextCell(j, 8, "");
						        
						        	noList.addTextCell(j, 10, "");
				        	}
				        	if(link.getBz3()==null){
				        	noList.addTextCell(j, 9, "");
				        	}else{
					        	noList.addTextCell(j, 9, link.getBz3());
				        	}
				        	String spair = link.getBz4();
							
				        	noList.addTextCell(j, 11, spair);
//				        	CCEnd SS7
							j++;
					
					}else if(link.getAdoptBs().equals("采用")){
						adoptList.addTextCell(ii, 0, link.getPartID());
						adoptList.addTextCell(ii, 1, link.getPartNumber());
						adoptList.addTextCell(ii, 2, link.getPartName());
						adoptList.addTextCell(ii, 3, link.getVersionValue());
						adoptList.addTextCell(ii, 4, link.getSl());
						adoptList.addTextCell(ii, 5, link.getZzlx());
						adoptList.addTextCell(ii, 6, link.getZplx());
						adoptList.addTextCell(ii, 7, link.getPartView());
						adoptList.addTextCell(ii, 8, link.getLifecyclestate());
						adoptList.addTextCell(ii, 9, link.getVirtualPart());
						//CCBegin SS7
						if(replacePart!=null){
							adoptList.addTextCell(ii, 10, replacePart.getPartNumber());
						}else{
							adoptList.addTextCell(ii, 10, "");
						}
						adoptList.addTextCell(ii, 11, link.getLinkPart());
						String pbsoid = link.getBz2();
						if(pbsoid!=null&&!pbsoid.equals("")){
					        	  // 获取父件编号
		                       Class[] paraClass = {String.class};
		                       Object[] obj = {pbsoid};
		                       QMPartInfo zcPart = (QMPartInfo)GYNoticeHelper.requestServer("PersistService", "refreshInfo", paraClass, obj);
		                
								adoptList.addTextCell(ii, 12, zcPart.getPartNumber());
						}else{
							adoptList.addTextCell(ii, 12,"");
						}
						if(link.getBz3()!=null){
							adoptList.addTextCell(ii, 13, link.getBz3());
						}else{
							adoptList.addTextCell(ii, 13, "");
						}
						adoptList.addTextCell(ii, 14, pbsoid);
						String spair = link.getBz4();
						
						adoptList.addTextCell(j, 15, spair);
						int iipair =  0;
						if(spair!=null&&!spair.equals("")){
							iipair =  Integer.parseInt(spair);
						}
						 
						if(iipair>ipair){
							ipair=iipair;
						}
	        			//CCEnd SS7
						ii++;
					}
				
				}
			} catch (QMException e) {
				e.printStackTrace();
			}
    	}
    }
    /**
     * 获得父窗口
     * @return javax.swing.JFrame
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
        if(!this.isShowing())
        {
            this.setVisible(true);
        }
        isHaveSave = false;
        repaint();
    }

    /**
     * 设置界面为更新状态
     * @throws QMException
     */
    private void setUpdateModel() throws QMException
    {
    	
    	
    	
    	//不采用按钮
    	addPartButton.setEnabled(true);
    	delPartButton.setEnabled(true);
    	replaceNoticeButton.setEnabled(true);
    	//采用按钮
//CCBegin SS3
    	countPartsButton.setEnabled(true);
//CCEnd SS3
        //CCBegin SS5
    	countAdoptPartButton.setEnabled(true);
        //CCEnd SS5
        //CCBegin SS4
    	addAdoptPartButton.setEnabled(true);
        //CCEnd SS4
    	delAdoptPartButton.setEnabled(true);
//    	viewNoticeButton.setEnabled(false);
    	saveNoticeButton.setEnabled(true);
    	noList.setEnabled(false);
    	adoptList.setEnabled(false);
        if(!this.isShowing())
        {
            this.setVisible(true);
        }
        isHaveSave = false;
        repaint();
    }
    /**
     * 设置界面为另存为状态
     * @throws QMException
     */
    private void setSaveAsModel() throws QMException
    {
    	
    	
    	
    	//不采用按钮
    	addPartButton.setEnabled(true);
    	delPartButton.setEnabled(true);
    	replaceNoticeButton.setEnabled(true);
    	//采用按钮
    	//CCBegin SS3
    	countPartsButton.setEnabled(true);
        //CCEnd SS3
        //CCBegin SS5
    	countAdoptPartButton.setEnabled(true);
        //CCEnd SS5
        //CCBegin SS4
    	addAdoptPartButton.setEnabled(true);
        //CCEnd SS4
    	delAdoptPartButton.setEnabled(true);
//    	viewNoticeButton.setEnabled(false);
    	saveNoticeButton.setEnabled(true);
    	noList.setEnabled(false);
    	adoptList.setEnabled(false);
        if(!this.isShowing())
        {
            this.setVisible(true);
        }
        isHaveSave = false;
        repaint();
    }

    /**
     * 设置界面为查看状态
     * @throws QMException
     */
    private void setViewModel() throws QMException
    {
    	//CCBegin SS3
    	countPartsButton.setEnabled(false);
        //CCEnd SS3
    	//不采用按钮
    	addPartButton.setEnabled(false);
    	delPartButton.setEnabled(false);
    	replaceNoticeButton.setEnabled(false);
    	//采用按钮

        //CCBegin SS5
    	countAdoptPartButton.setEnabled(false);
        //CCEnd SS5
        //CCBegin SS4
    	addAdoptPartButton.setEnabled(false);
        //CCEnd SS4
    	delAdoptPartButton.setEnabled(false);
//    	viewNoticeButton.setEnabled(true);
    	saveNoticeButton.setEnabled(false);
    	cancelNoticeButton.setEnabled(false);

        
        
      //设置可编辑列[查看时，都不可编辑]
    	int[] editCol = new int[]{0,1,2,3,4,5,6,7,8};
    	adoptList.setColsEnabled(editCol, false);
    	int[] noeditCol = new int[]{0,1,2,3,4,5};
    	noList.setColsEnabled(noeditCol, false);
    	
    	
        if(!this.isShowing())
        {
            this.setVisible(true);
        }
        isHaveSave = false;
        repaint();
    }



    /**
     * 设置按钮的显示状态（有效或失效）
     * @param flag flag为True，按钮有效；否则按钮失效
     */
    private void setButtonWhenSave(boolean flag)
    {

        saveJButton.setEnabled(flag);
        quitJButton.setEnabled(flag);
    }



    /**
     * 保存调整BOM
     * @param e ActionEvent
     */
    void saveJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setButtonWhenSave(false);
        save();
        setButtonWhenSave(true);
        setCursor(Cursor.getDefaultCursor());
    }
    /**
     * 不采用列表增加零件
     * @param e ActionEvent
     */
    void addPartJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        GYNoticePartListSearchJDialog searchJDialog = new GYNoticePartListSearchJDialog(this.getParentJFrame(), this,"不采用");
        searchJDialog.setVisible(true);
        setCursor(Cursor.getDefaultCursor());

    }
        //CCBegin SS4
    void addAdoptPartJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        GYNoticePartListSearchJDialog searchJDialog = new GYNoticePartListSearchJDialog(this.getParentJFrame(), this,"采用");
        searchJDialog.setVisible(true);
        setCursor(Cursor.getDefaultCursor());

    }
        //CCEnd SS4
    //CCBegin SS7
    void addPPartJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Vector vecP =  new Vector();
         index = adoptList.getSelectedRow();
        System.out.println("index=="+index);
        if(index>=0){
        	String adoptPartBsoID = adoptList.getCellText(index, 0);
			Class[] theClass = { String.class};
			Object[] obj = { adoptPartBsoID };
			try {
				 vecP = (Vector) GYNoticeHelper.requestServer("GYBomService","getParentPart", theClass, obj);
			} catch (QMException e1) {
				// TODO 自动生成 catch 块
				e1.printStackTrace();
			}
			//adoptList.addTextCell(j, 4, partQuantity);
          
        }
        System.out.println("vecP==="+vecP);
        GYPartentPartListSearchJDialog searchJDialog = new GYPartentPartListSearchJDialog(this.getParentJFrame(), this,"采用",vecP);
        searchJDialog.setVisible(true);
        setCursor(Cursor.getDefaultCursor());

    }
    void addPNAPartJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Vector vecP =  new Vector();
         index = noList.getSelectedRow();
        System.out.println("index=="+index);
        if(index>=0){
        	String adoptPartBsoID = noList.getCellText(index, 0);
			Class[] theClass = { String.class};
			Object[] obj = { adoptPartBsoID };
			try {
				 vecP = (Vector) GYNoticeHelper.requestServer("GYBomService","getParentFromDwbs", theClass, obj);
			} catch (QMException e1) {
				// TODO 自动生成 catch 块
				e1.printStackTrace();
			}
			//adoptList.addTextCell(j, 4, partQuantity);
          
        }
        GYPartentPartListSearchJDialog searchJDialog = new GYPartentPartListSearchJDialog(this.getParentJFrame(), this,"不采用",vecP);
        searchJDialog.setVisible(true);
        setCursor(Cursor.getDefaultCursor());

    }
    /**
     * 列表赋值（通过对象获得数据时用到）
     * @param Vector noticePartLinkVec
     * @author wenl
     */
    public void setParentMultiListData(String type,String[] part)
    {
    
    	System.out.println("ii=="+index);
    	System.out.println("type=="+type);
    	if (type.equals("不采用")){
    		
    		if(index>=0){
        			noList.addTextCell(index, 10, part[0]);
                	noList.addTextCell(index,8, part[1]);
                	noList.addTextCell(index, 9, part[2]);
    		}
        //CCBegin SS4
    	}else if(type.equals("采用")){
    		if(index>=0){
    			System.out.println("part=="+part[0]);
    			adoptList.addTextCell(index, 14, part[0]);
    			adoptList.addTextCell(index,12, part[1]);
    			adoptList.addTextCell(index, 13, part[2]);
    			adoptList.addTextCell(index, 4, part[3]);
		
    		}
     

    	}
    	
    		
    	
    }
        //CCEnd SS7
   //CCBegin SS5
    /**
     * 计算每车数量
     * @param e ActionEvent
     */
    void countAdoptPartsJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        QMPartIfc topPart = null;
        JPanel parentPanel = ((GYBomNoticeMainJFrame) parentFrame).getTaskPanel();
        if(parentPanel instanceof CJAdoptNoticeViewPanel){
        	topPart = ((CJAdoptNoticeViewPanel)parentPanel).cxPart;
        }else if(parentPanel instanceof ZCBomAdoptNoticeViewPanel){
        	topPart = ((ZCBomAdoptNoticeViewPanel)parentPanel).cxPart;
        }
        if(topPart==null){
       	 JOptionPane.showMessageDialog(this.getParent(),"基础信息中未选择“车型”，不能计算零件使用数量！");
            setCursor(Cursor.getDefaultCursor());
            return;
       }
       int noIndex = noList.getRowCount();
       int adoptIndex = adoptList.getRowCount();
     
    	   
		try {
			
			for (int j = 0; j < adoptIndex; j++) {
				String adoptPartBsoID = adoptList.getCellText(j, 0);
				QMPartIfc adoptPartIfc;
				adoptPartIfc = (QMPartIfc) GYNoticeClientUtil.refresh(adoptPartBsoID);
				Class[] theClass = { QMPartIfc.class, QMPartIfc.class };
				Object[] obj = { topPart, adoptPartIfc };
				String partQuantity = (String) GYNoticeHelper.requestServer("StandardPartService","getPartQuantity", theClass, obj);
				adoptList.addTextCell(j, 4, partQuantity);
			}
		} catch (QMException e1) {
			e1.printStackTrace();
		}
        setCursor(Cursor.getDefaultCursor());

    }
   //CCEnd SS5
   //CCBegin SS3
    /**
     * 计算每车数量
     * @param e ActionEvent
     */
    void countPartsJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        QMPartIfc topPart = null;
        JPanel parentPanel = ((GYBomNoticeMainJFrame) parentFrame).getTaskPanel();
        if(parentPanel instanceof CJAdoptNoticeViewPanel){
        	topPart = ((CJAdoptNoticeViewPanel)parentPanel).cxPart;
        }else if(parentPanel instanceof ZCBomAdoptNoticeViewPanel){
        	topPart = ((ZCBomAdoptNoticeViewPanel)parentPanel).cxPart;
        }
        if(topPart==null){
       	 JOptionPane.showMessageDialog(this.getParent(),"基础信息中未选择“车型”，不能计算零件使用数量！");
            setCursor(Cursor.getDefaultCursor());
            return;
       }
       int noIndex = noList.getRowCount();
       int adoptIndex = adoptList.getRowCount();
     
    	   
		try {
			for (int i = 0; i < noIndex; i++) {
				String noPartBsoID = noList.getCellText(i, 0);
				QMPartIfc noPartIfc;
				noPartIfc = (QMPartIfc) GYNoticeClientUtil.refresh(noPartBsoID);
				Class[] theClass = { QMPartIfc.class, QMPartIfc.class };
				Object[] obj = { topPart, noPartIfc };
				String partQuantity = (String) GYNoticeHelper.requestServer("StandardPartService","getPartQuantity", theClass, obj);
				noList.addTextCell(i, 4, partQuantity);
			}
   //CCBegin SS5
/*			
			for (int j = 0; j < adoptIndex; j++) {
				String adoptPartBsoID = adoptList.getCellText(j, 0);
				QMPartIfc adoptPartIfc;
				adoptPartIfc = (QMPartIfc) GYNoticeClientUtil.refresh(adoptPartBsoID);
				Class[] theClass = { QMPartIfc.class, QMPartIfc.class };
				Object[] obj = { topPart, adoptPartIfc };
				String partQuantity = (String) GYNoticeHelper.requestServer("StandardPartService","getPartQuantity", theClass, obj);
				adoptList.addTextCell(j, 4, partQuantity);
			}
*/
   //CCEnd SS5
		} catch (QMException e1) {
			e1.printStackTrace();
		}
        setCursor(Cursor.getDefaultCursor());

    }
   //CCEnd SS3

    /**
     * 替换为增加零件
     * @param e ActionEvent
     */
    void addReplacePartJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(noList.getSelectedRows()==null||noList.getSelectedRows().length<=0){
            JOptionPane.showMessageDialog(this.getParent(),"选择替换时，需要选择“不采用”零件！");
            setCursor(Cursor.getDefaultCursor());
            return;
        }else if(noList.getSelectedRows().length>1){
            JOptionPane.showMessageDialog(this.getParent(),"选择替换时，只能选择一个“不采用”零件！");
            setCursor(Cursor.getDefaultCursor());
            return;
        }
        int[] selectNoListRows = noList.getSelectedRows();
		String sl = noList.getCellText(selectNoListRows[0],4 );
		System.out.println("sl==="+sl);
		if(sl==null||sl.equals("0")||sl.equals("")){
			JOptionPane.showMessageDialog(this.getParent(),"请维护替换前数量");
            setCursor(Cursor.getDefaultCursor());
            return;
		}
        GYNoticePartListSearchJDialog searchJDialog = new GYNoticePartListSearchJDialog(this.getParentJFrame(), this,"替换");
        searchJDialog.setVisible(true);
        setCursor(Cursor.getDefaultCursor());

    }
    /**
     * 删除不采用列表零件
     * @param e ActionEvent
     */
    void deletNoAdoptPartJButton_actionPerformed(ActionEvent e)
    {
    	
        int[] index = noList.getSelectedRows();
        if(index!=null&&index.length>0){
            for(int i = 0 ; i < index.length;i++){
            	noList.removeRow(index[i]-i);
            }
            //删除后列表内容不为空，则默认选择第一个
         if(noList.getRowCount()>0){
        	 noList.selectRow(0);
         }  	
        }
    }

    /**
     * 删除采用列表零件
     * @param e ActionEvent
     */
    void deletAdoptPartJButton_actionPerformed(ActionEvent e)
    {
    	
        int[] index = adoptList.getSelectedRows();
        if(index!=null&&index.length>0){
            for(int i = 0 ; i < index.length;i++){
            	adoptList.removeRow(index[i]-i);
            }
            //删除后列表内容不为空，则默认选择第一个
         if(adoptList.getRowCount()>0){
        	 adoptList.selectRow(0);
         }  	
        }
    }
   
    /**
     * 保存.保存完后进入查看状态
     */
    public void save()
    {
    	System.out.println("111111111111111=="+getViewMode());

        if(getViewMode() == CREATE_MODE||getViewMode() == SAVE_AS_MODE)
        {
            saveWhenCreate();
        }else if(getViewMode() == UPDATE_MODE)
        {
            saveWhenUpdate();
        }

        ((GYBomNoticeMainJFrame)parentFrame).stopProgress();

    }
    /**
     * 保存发布单调整BOM
     */
    private void saveWhenCreate()
    {
        try
        {

            // 设置所有属性和关联，并获得信息封装对象。
            Vector wrapData = commitAllData();
            // 显示进度
            ((GYBomNoticeMainJFrame)parentFrame).startProgress();
            //父页面如果没保存，则先保存父页面的更新内容
            JPanel parentPanel = ((GYBomNoticeMainJFrame)parentFrame).getTaskPanel();
            GYBomAdoptNoticeIfc bomIfc = null;
            if(parentPanel instanceof ZCBomAdoptNoticeViewPanel){
            	ZCBomAdoptNoticeViewPanel zcPanel  = (ZCBomAdoptNoticeViewPanel)parentPanel;
            	bomIfc = zcPanel.getBomAdoptNotice();
            	 if(bomIfc==null){
                 	zcPanel.save();
                 	bomIfc = zcPanel.getBomAdoptNotice();
                 	
                 	if(bomIfc==null){
                 		  throw new QMException("保存整车基础信息出错！");
                 	}
                 }else{
                  	 //另存为模式，调用前页保存方法
                	 if(this.mode==SAVE_AS_MODE&&!zcPanel.isHaveSave){
                		zcPanel.save();
                      	bomIfc = zcPanel.getBomAdoptNotice();
                      	if(bomIfc==null){
                   		  throw new QMException("保存整车基础信息出错！");
                      	}
                	 }else{
                         // 调用服务,执行保存.返回采用单值对象
                         Class[] paraClass = {Vector.class,GYBomAdoptNoticeIfc.class};
                         Object[] obj = {wrapData,bomIfc};

                         Vector linksVec = (Vector)GYNoticeHelper.requestServer("GYBomNoticeService", "createGYBomZCPartLink", paraClass, obj);
                         this.isHaveSave = true;
                         this.setMultiListData(linksVec);//设置零件列表数据
                         this.setViewModel();
                	 }
                 }
            }else if(parentPanel instanceof CJAdoptNoticeViewPanel){
            	CJAdoptNoticeViewPanel zcPanel  = (CJAdoptNoticeViewPanel)parentPanel;
            	bomIfc = zcPanel.getBomAdoptNotice();
            	 if(bomIfc==null){
                 	zcPanel.save();
                 	bomIfc = zcPanel.getBomAdoptNotice();
                 	if(bomIfc==null){
                 		  throw new QMException("保存车架、驾驶室基础信息出错！");
                 	}
                 }else{
                	 //另存为模式，调用前页保存方法
                	 if(this.mode==SAVE_AS_MODE&&!zcPanel.isHaveSave){
                		zcPanel.save();
                      	bomIfc = zcPanel.getBomAdoptNotice();
                      	if(bomIfc==null){
                   		  throw new QMException("保存车架、驾驶室基础信息出错！");
                      	}
                	 }else{
                		  // 调用服务,执行保存.返回采用单值对象
                         Class[] paraClass = {Vector.class,GYBomAdoptNoticeIfc.class};
                         Object[] obj = {wrapData,bomIfc};

                         Vector linksVec = (Vector)GYNoticeHelper.requestServer("GYBomNoticeService", "createGYBomZCPartLink", paraClass, obj);
                         this.isHaveSave = true;
                         this.setMultiListData(linksVec);//设置零件列表数据
                         this.setViewModel();
                	 }
                	 
                   
                 }
            }     
        }catch(QMException ex)
        {
        	((GYBomNoticeMainJFrame)parentFrame).stopProgress();
            ex.printStackTrace();
            DialogFactory.showInformDialog(this, ex.getClientMessage());

        }

    }
    /**
     * 更新发布单调整BOM
     */
    private void saveWhenUpdate()
    {
        try
        {
        	
        	System.out.println("22222222222222==");

            // 设置所有属性和关联，并获得信息封装对象。
            Vector wrapData = commitAllData();
            // 显示进度
            ((GYBomNoticeMainJFrame)parentFrame).startProgress();
            //父页面如果没保存，则先保存父页面的更新内容
            JPanel parentPanel = ((GYBomNoticeMainJFrame)parentFrame).getTaskPanel();
            GYBomAdoptNoticeIfc bomIfc = null;
            if(parentPanel instanceof ZCBomAdoptNoticeViewPanel){
            	ZCBomAdoptNoticeViewPanel zcPanel  = (ZCBomAdoptNoticeViewPanel)parentPanel;
            	bomIfc = zcPanel.getBomAdoptNotice();
            	System.out.println("bomIfc=="+bomIfc);
            	System.out.println("this.mode=="+this.mode);
            	System.out.println("zcPanel.isHaveSave=="+zcPanel.isHaveSave);
            	 if(bomIfc==null){
                 	zcPanel.save();
                 	bomIfc = zcPanel.getBomAdoptNotice();	
                 	if(bomIfc==null){
                 		  throw new QMException("保存整车基础信息出错！");
                 	}
                 }else{
                	//更新模式，先保存前页
                	 if(this.mode==UPDATE_MODE&&!zcPanel.isHaveSave){
                		zcPanel.save();
                       	bomIfc = zcPanel.getBomAdoptNotice();
                       	if(bomIfc==null){
                    		  throw new QMException("保存车架、驾驶室基础信息出错！");
                    		  
                       	}
                	 }else{
                		 //CCBegin SS2
                		 zcPanel.isHaveSave = false;
                		 System.out.println("wrapData=="+wrapData);
                		 //CCEnd SS2
	                	 // 调用服务,执行保存.返回采用单值对象
	                     Class[] paraClass = {Vector.class,GYBomAdoptNoticeIfc.class};
	                     Object[] obj = {wrapData,this.myBomAdoptNotice};
	                     Vector linksVec = (Vector)GYNoticeHelper.requestServer("GYBomNoticeService", "updateGYBomZCPartLink", paraClass, obj);
	                     this.isHaveSave = true;
	                     this.setMultiListData(linksVec);//设置零件列表数据
         								//CCBegin SS6
        								if (isPaint)
        								{ 
	                     		this.setViewModel();
	                     	}
         								//CCEnd SS6
	                     zcPanel.setViewMode(zcPanel.VIEW_MODE);
                	 }
                 }
            }else if(parentPanel instanceof CJAdoptNoticeViewPanel){
            	CJAdoptNoticeViewPanel zcPanel  = (CJAdoptNoticeViewPanel)parentPanel;
            	bomIfc = zcPanel.getBomAdoptNotice();
            	 if(bomIfc==null){
                 	zcPanel.save();
                 	bomIfc = zcPanel.getBomAdoptNotice();
                 	
                 	if(bomIfc==null){
                 		  throw new QMException("保存车架、驾驶室基础信息出错！");
                 	}
                 }else{
                   	 //更新模式，先保存前页
                	 if(this.mode==UPDATE_MODE&&!zcPanel.isHaveSave){
                		zcPanel.save();
                       	bomIfc = zcPanel.getBomAdoptNotice();
                       	if(bomIfc==null){
                    		  throw new QMException("保存车架、驾驶室基础信息出错！");
                    		  
                       	}
                	 }else{
                    	 // 调用服务,执行保存.返回采用单值对象
                         Class[] paraClass = {Vector.class,GYBomAdoptNoticeIfc.class};
                         Object[] obj = {wrapData,this.myBomAdoptNotice};
                         Vector linksVec = (Vector)GYNoticeHelper.requestServer("GYBomNoticeService", "updateGYBomZCPartLink", paraClass, obj);
                         this.isHaveSave = true;
                         this.setMultiListData(linksVec);//设置零件列表数据
         								//CCBegin SS6
        								if (isPaint)
        								{ 
                         this.setViewModel();
         								}
         								//CCEnd SS6

                	 }

                 }
            }  
        }catch(QMException ex)
        {
        	((GYBomNoticeMainJFrame)parentFrame).stopProgress();
            ex.printStackTrace();
            DialogFactory.showInformDialog(this, ex.getClientMessage());

        }

    }
    /**
     * 提交数据用以保存
     * @throws QMException
     */
    private Vector commitAllData() throws QMException
    {
    	//Object[] data = new Object[11];
    	//采用零件列表
        Vector noticePartLinkVec = new Vector();
		//处理采用列表
		if(adoptList.getRowCount()>0){
			for(int i = 0 ; i <adoptList.getRowCount();i++){
				String adoptPartBsoID = adoptList.getCellText(i, 0);//采用零件bsoID
				String replacePartBsoID = adoptList.getCellText(i, 11);//不采用零件bsoID 
				String partNumber = adoptList.getCellText(i, 1);
				String partName = adoptList.getCellText(i, 2);
				String partVersion = adoptList.getCellText(i, 3);
				String partCount = adoptList.getCellText(i, 4);
				String makeStr = adoptList.getCellText(i, 5);
				String assembleStr = adoptList.getCellText(i, 6);
				String partView = adoptList.getCellText(i, 7);
				String lifeCycleState = adoptList.getCellText(i, 8);
				String isVir = adoptList.getCellText(i, 9);
				String replacePartNum = adoptList.getCellText(i, 10);
				//CCBegin SS7
	            String parentnumber = adoptList.getCellText(i, 14);
	            String subGroup = adoptList.getCellText(i, 13);
	            String spair = adoptList.getCellText(i, 15);
                //CCEnd SS7
				GYBomAdoptNoticePartLinkInfo link = new GYBomAdoptNoticePartLinkInfo();
				link.setAdoptBs("采用");
				link.setPartNumber(partNumber);
				link.setPartName(partName);
				link.setVersionValue(partVersion);
				link.setSl(partCount);
				link.setZzlx(makeStr);
				link.setZplx(assembleStr);
				link.setPartView(partView);
				link.setVirtualPart(isVir);
				link.setLifecyclestate(lifeCycleState);
				link.setLinkPart(replacePartBsoID);
				link.setPartID(adoptPartBsoID);
//				CCBegin SS7
				link.setBz2(parentnumber);
				link.setBz3(subGroup);
				link.setBz4(spair);
				 //CCEnd SS7
				noticePartLinkVec.add(link);
			}
		}    
		//处理不采用列表
		if(noList.getRowCount()>0){
			for(int i = 0 ; i <noList.getRowCount();i++){
				String noPartBsoID = noList.getCellText(i, 0);//不采用采用零件bsoID

				String partNumber = noList.getCellText(i, 1);
				String partName = noList.getCellText(i, 2);
				String partVersion = noList.getCellText(i, 3);
				String partCount = noList.getCellText(i, 4);
				String makeStr = noList.getCellText(i, 5);
				String assembleStr = noList.getCellText(i, 6);
				String partView = noList.getCellText(i, 7);
//				CCBegin SS7
				String parentnumber = noList.getCellText(i, 10);
		        String subGroup = noList.getCellText(i, 9);
		        String spair = noList.getCellText(i, 11);
		        //CCEnd SS7
            	
				GYBomAdoptNoticePartLinkInfo link = new GYBomAdoptNoticePartLinkInfo();
				link.setAdoptBs("不采用");
				link.setPartNumber(partNumber);
				link.setPartName(partName);
				link.setVersionValue(partVersion);
				link.setSl(partCount);
				link.setZzlx(makeStr);
				link.setZplx(assembleStr);
				link.setPartView(partView);
				link.setPartID(noPartBsoID);
//				CCBegin SS7
				link.setBz2(parentnumber);
				link.setBz3(subGroup);
				link.setBz4(spair);
				 //CCEnd SS7
				noticePartLinkVec.add(link);
			}
		}
		return noticePartLinkVec;
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
     * @param rationlist BomAdoptNoticeIfc 
     * @throws QMException
     */
    public void setBomAdoptNotice(GYBomAdoptNoticeIfc bomAdoptIfc, int mode) throws QMException
    {
    	this.myBomAdoptNotice = bomAdoptIfc;
    	if(!firstSelect){
    		noList.clear();
    		adoptList.clear();
      	  // 调用服务,执行查询.返回采用单零件关联对象
            Class[] paraClass = {GYBomAdoptNoticeIfc.class};
            Object[] obj = {bomAdoptIfc};
            Collection obj1 = (Collection)GYNoticeHelper.requestServer("GYBomNoticeService", "getBomPartFromBomAdoptNotice", paraClass, obj);
            if(obj1!=null&&obj1.size()>0){
            	
                this.setMultiListData((Vector)obj1);
            }
            this.firstSelect = true;
    	}   
    	this.setViewMode(mode);
    }
    
         //CCBegin SS6
    private boolean isPaint = true;
    public void setIsPaint(boolean flag)
    {
        isPaint=flag;
    }

    public void setBomAdoptNotice1(GYBomAdoptNoticeIfc bomAdoptIfc, int aMode) throws QMException
    {
    	this.myBomAdoptNotice = bomAdoptIfc;
    	if(!firstSelect){
    		noList.clear();
    		adoptList.clear();
      	  // 调用服务,执行查询.返回采用单零件关联对象
            Class[] paraClass = {GYBomAdoptNoticeIfc.class};
            Object[] obj = {bomAdoptIfc};
            Collection obj1 = (Collection)GYNoticeHelper.requestServer("GYBomNoticeService", "getBomPartFromBomAdoptNotice", paraClass, obj);
            if(obj1!=null&&obj1.size()>0){
                this.setMultiListData((Vector)obj1);
            }
            this.firstSelect = true;
    	}   
         if((aMode == this.UPDATE_MODE) || (aMode == this.CREATE_MODE) || (aMode == this.VIEW_MODE)||(aMode == this.SAVE_AS_MODE) )
        {
            mode = aMode;
        }
        switch(aMode)
        {
        case CREATE_MODE:
        { // 创建模式
        		isHaveSave = false;
            break;
        }
        case UPDATE_MODE:
        { // 更新模式
        		isHaveSave = false;
            break;
        }
      	}
   }
         //CCEnd SS6



    /**
     * 提示是否保存,保存，并关闭界面
     * @param e ActionEvent
     */
    void quitJButton_actionPerformed(ActionEvent e)
    {
        quit();
    }


   
   
    /**
     * 显示确认对话框
     * @param s 在对话框中显示的信息
     * @return 如果用户选择了“确定”按钮，则返回true;否则返回false
     */
    private int confirmAction(String s)
    {
        String title = "确认保存";
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
    /**
     * 退出
     * 0代表是，1代表否  2代表关闭
     */
    public void quit()
    {
        if(!isHaveSave)
        {
            if(this.getViewMode() != VIEW_MODE)
            {
                String s = "是否保存调整BOM结果？";
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






    /**
     * 查看BOM按钮监听
     * @param e ActionEvent
     */
    void viewBomButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       
       
        setCursor(Cursor.getDefaultCursor());

    }
}
/**
 * <p>Title: 查看BOM
 * @author 文柳
 * @version 1.0
 */

class ZCAdoptNoticeAdjustPanel_viewBomButton_actionAdapter implements java.awt.event.ActionListener
{
	ZCAdoptNoticeAdjustPanel adaptee;

	ZCAdoptNoticeAdjustPanel_viewBomButton_actionAdapter(ZCAdoptNoticeAdjustPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.viewBomButton_actionPerformed(e);
    }
}
/**
 * <p>Title: 增加零件
 * @author 文柳
 * @version 1.0
 */

class ZCAdoptNoticeAdjustPanel_addPartButton_actionAdapter implements java.awt.event.ActionListener
{
	ZCAdoptNoticeAdjustPanel adaptee;

	ZCAdoptNoticeAdjustPanel_addPartButton_actionAdapter(ZCAdoptNoticeAdjustPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.addPartJButton_actionPerformed(e);
    }
}
//CCBegin SS7
class ZCAdoptNoticeAdjustPanel_addPJButtonJButton_actionAdapter
	implements java.awt.event.ActionListener {
	private ZCAdoptNoticeAdjustPanel adaptee;
	
	ZCAdoptNoticeAdjustPanel_addPJButtonJButton_actionAdapter(
			ZCAdoptNoticeAdjustPanel adaptee) {
	this.adaptee = adaptee;
	}
	
	public void actionPerformed(ActionEvent e) {
	adaptee.addPPartJButton_actionPerformed(e);
	}
}
class ZCAdoptNoticeAdjustPanel_addPNAJButtonJButton_actionAdapter
	implements java.awt.event.ActionListener {
		private ZCAdoptNoticeAdjustPanel adaptee;
		
		ZCAdoptNoticeAdjustPanel_addPNAJButtonJButton_actionAdapter(
				ZCAdoptNoticeAdjustPanel adaptee) {
				this.adaptee = adaptee;
		}
		
		public void actionPerformed(ActionEvent e) {
		adaptee.addPNAPartJButton_actionPerformed(e);
	}
}
//CCEnd SS7
        //CCBegin SS4
class ZCAdoptNoticeAdjustPanel_addAdoptPartButton_actionAdapter implements java.awt.event.ActionListener
{
	ZCAdoptNoticeAdjustPanel adaptee;

	ZCAdoptNoticeAdjustPanel_addAdoptPartButton_actionAdapter(ZCAdoptNoticeAdjustPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.addAdoptPartJButton_actionPerformed(e);
    }
}
        //CCEnd SS4
//CCBegin SS5
class ZCAdoptNoticeAdjustPanel_countAdoptPartsButton_actionAdapter implements java.awt.event.ActionListener
{
	ZCAdoptNoticeAdjustPanel adaptee;

	ZCAdoptNoticeAdjustPanel_countAdoptPartsButton_actionAdapter(ZCAdoptNoticeAdjustPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.countAdoptPartsJButton_actionPerformed(e);
    }
}
//CCEnd SS5
//CCBegin SS3
/**
 * <p>Title: 计算数量
 * @author 文柳
 * @version 1.0
 */

class ZCAdoptNoticeAdjustPanel_countPartsButton_actionAdapter implements java.awt.event.ActionListener
{
	ZCAdoptNoticeAdjustPanel adaptee;

	ZCAdoptNoticeAdjustPanel_countPartsButton_actionAdapter(ZCAdoptNoticeAdjustPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.countPartsJButton_actionPerformed(e);
    }
}
//CCEnd SS3
/**
 * <p>Title: 采用替换零件
 * @author 文柳
 * @version 1.0
 */

class ZCAdoptNoticeAdjustPanel_addReplacePartButton_actionAdapter implements java.awt.event.ActionListener
{
	ZCAdoptNoticeAdjustPanel adaptee;

	ZCAdoptNoticeAdjustPanel_addReplacePartButton_actionAdapter(ZCAdoptNoticeAdjustPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
    	
        adaptee.addReplacePartJButton_actionPerformed(e);
    }
}
/**
 * <p>Title: 子采用单保存控制类
 * @author 文柳
 * @version 1.0
 */

class ZCAdoptNoticeAdjustPanel_saveJButton_actionAdapter implements java.awt.event.ActionListener
{
    ZCAdoptNoticeAdjustPanel adaptee;

    ZCAdoptNoticeAdjustPanel_saveJButton_actionAdapter(ZCAdoptNoticeAdjustPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.saveJButton_actionPerformed(e);
    }
}

