/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 TD8316 文柳 2014-7-29 
 * SS2 自动生成BOM发布单 lishu 2017-5-12
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.faw_qm.cappclients.util.ComponentMultiList;

import com.faw_qm.clients.widgets.DialogFactory;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.gybomNotice.model.GYInvoiceIfc;
import com.faw_qm.gybomNotice.model.GYInvoiceInfo;
import com.faw_qm.gybomNotice.util.GYNoticeHelper;
import com.faw_qm.lock.model.LockIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;


/**
 * <p>Title:计算散发清单界面。</p> <p>Description: </p> <p>Copyright: Copyright (c) 2014</p> <p>Company: 启明信息技术股份有限公司</p>
 * @author 文柳
 * @version 1.0
 */

public class CJCountInvoicePanel extends RightPanel
{
	
	private GYBomAdoptNoticeIfc myBomAdoptNotice;
    private static final long serialVersionUID = 1L;
    private static final boolean verbose = GYNoticeHelper.VERBOSE;

  


    JTabbedPane jTabbedPane1 = new JTabbedPane();
    JPanel jPanel6 = new JPanel();
    JPanel adoptNoticePanel = new JPanel();
    JPanel countPanel = new JPanel();
    JButton saveJButton = new JButton();
    JButton quitJButton = new JButton();
    public ComponentMultiList invoiceList = new ComponentMultiList();//散发清单列表
 
    private JScrollPane jScrollPane2 = new JScrollPane();
    //控制按钮
    private JButton saveNoticeButton = new JButton();
    private JButton cancelNoticeButton = new JButton();
    
    private JButton countInvoiceButton = new JButton();

    
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
    public CJCountInvoicePanel(GYBomNoticeMainJFrame parent)
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
        countPanel.setLayout(new GridBagLayout());
        saveNoticeButton.setMinimumSize(new Dimension(110, 23));
        saveNoticeButton.setPreferredSize(new Dimension(110, 23));
        saveNoticeButton.setText("保存");
        saveNoticeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                saveJButton_actionPerformed(e);
            }
        });   	

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
        countInvoiceButton.setMaximumSize(new Dimension(110, 23));
        countInvoiceButton.setMinimumSize(new Dimension(110, 23));
        countInvoiceButton.setPreferredSize(new Dimension(110, 23));
        countInvoiceButton.setText("计算散发清单");
        countInvoiceButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                countJButton_actionPerformed(e);
            }
        });

        
        //功能按钮
        jPanel6.add(saveNoticeButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        jPanel6.add(cancelNoticeButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        countPanel.add(countInvoiceButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 2, 8), 0, 0));
      
        adoptNoticePanel.add(invoiceList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 8, 0, 0), 0, 0));
        this.add(countPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(0, 8, 2, 8), 0, 0));
        this.add(adoptNoticePanel, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 8, 2, 8), 0, 0));
        this.add(jPanel6, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        getQMMultiListTitle();

    }
   
    /**
     * 获得散发单表头
     */
    private void getQMMultiListTitle()
    {
    	
		String[] subNoticeTitle = new String[10];
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
   
    	int[] mutListSize = new int[10];
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
    	invoiceList.setHeadings(subNoticeTitle);
    	invoiceList.setRelColWidth(mutListSize);
    	invoiceList.setCellEditable(false);
    	invoiceList.setMultipleMode(true);
    	invoiceList.setAllowSorting(false);

    }

    /**
     * 散发清单列表赋值
     * @param Vector strs 散发清单集合
     * @param String type 赋值类型：type=0为通过按钮赋值；type=1为通过对象赋值
     * @author wenl
     */
    public void setMultiListData(Vector strs,int type)
    {
    	invoiceList.clear();
    	if(type == 0){
    		if(strs!=null&&strs.size()>0){
    			for(int i = 0 ; i <strs.size();i++){
        			String[] part = (String[])strs.get(i);
        			invoiceList.addTextCell(i, 0, part[0]);
        			invoiceList.addTextCell(i, 1, part[1]);
        			invoiceList.addTextCell(i, 2, part[2]);
                	invoiceList.addTextCell(i, 3, part[3]);
                	invoiceList.addTextCell(i, 4, part[4]);
                	invoiceList.addTextCell(i, 5, part[5]);
                	invoiceList.addTextCell(i, 6, part[6]);
                	invoiceList.addTextCell(i, 7, part[7]);
                	invoiceList.addTextCell(i, 8, part[8]);
                	invoiceList.addTextCell(i, 9, part[9]);
        		}
    		}
    	}else if(type ==1){
        	if(strs!=null&&strs.size()>0){
        		for(int i = 0 ; i<strs.size();i++){
        			GYInvoiceIfc invoice = (GYInvoiceIfc)strs.get(i);
        			invoiceList.addTextCell(i, 0, invoice.getPartID());
        			invoiceList.addTextCell(i, 1, invoice.getPartNumber());
        			invoiceList.addTextCell(i, 2, invoice.getPartName());
                	invoiceList.addTextCell(i, 3, invoice.getVersionValue());
                	invoiceList.addTextCell(i, 4, invoice.getSl());
                	invoiceList.addTextCell(i, 5, invoice.getZzlx());
                	invoiceList.addTextCell(i, 6, invoice.getZplx());
                	invoiceList.addTextCell(i, 7, invoice.getPartView());
                	invoiceList.addTextCell(i, 8, invoice.getLifecyclestate());
                	invoiceList.addTextCell(i, 9, invoice.getVirtualPart());
        		}
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
    	saveNoticeButton.setEnabled(true);
    	cancelNoticeButton.setEnabled(true);
    	countInvoiceButton.setEnabled(true);
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
    	saveNoticeButton.setEnabled(true);
    	cancelNoticeButton.setEnabled(true);
    	countInvoiceButton.setEnabled(true);
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
    	

    	saveNoticeButton.setEnabled(false);
    	cancelNoticeButton.setEnabled(false);
    	countInvoiceButton.setEnabled(false);
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
        countInvoiceButton.setEnabled(flag);
    }



    /**
     * 保存散发清单
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
     * 计算散发清单
     * @param e ActionEvent
     */
    void countJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setButtonWhenSave(false);
        try {
			countInvoice();
		} catch (QMException e1) {
			e1.printStackTrace();
		}
        setButtonWhenSave(true);
        setCursor(Cursor.getDefaultCursor());
    }
    /**
	 * 计算散发清单
	 */   
    public void countInvoice() throws  QMException{
//        System.out.println("计算散发清单");
        CJAdoptNoticeViewPanel parentPanel = (CJAdoptNoticeViewPanel)((GYBomNoticeMainJFrame) parentFrame).getTaskPanel();
        QMPartIfc topPart = parentPanel.cxPart;
        if(topPart==null){
        	throw new QMException("获得车型信息出错，请检查基础信息！");
        }
        invoiceList.clear();
		Class[] paraClass = {QMPartIfc.class};
		Object[] obj = { topPart};
		try {
		  //CCBegin SS2
//			Collection invoices = (Collection) GYNoticeHelper.requestServer("GYBomNoticeService","getReleaseBom", paraClass, obj);
			Collection invoices = (Collection) GYNoticeHelper.requestServer("GYBomNoticeService","getFsqd", paraClass, obj);
			//CCEnd SS2
			if(invoices!=null&&invoices.size()>0){
				Vector invoiceData = new Vector();
				//CCBegin SS1
				for(Iterator ite = invoices.iterator();ite.hasNext();){
					Object[] objs = (Object[])ite.next();
					QMPartIfc part = (QMPartIfc)objs[0];
					String[] routes = (String[])objs[2];
					String count  = (String)objs[5];
					String[] data = new String[10];
					data[0] = part.getBsoID(); 
					data[1] = part.getPartNumber(); 
					data[2] = part.getPartName(); 
					data[3] = part.getVersionID(); 
					data[4] = count; 
					data[5] = routes[0]; 
					data[6] = routes[1]; 
					data[7] = part.getViewName(); 
					data[8] = part.getLifeCycleState().getDisplay(); 
					data[9] = (String)objs[1]; 
//					System.out.println("data[5]   ========  " + data[5]);
//					System.out.println("data[6]   ========  " + data[6]);
					if((data[5].contains("架(漆)")&&data[6].contains("总"))||(data[5].contains("涂")&&data[6].contains("总"))){
						invoiceData.add(data);
					}
					//CCEnd SS1
				}
				setMultiListData(invoiceData,0);
			}
		
		} catch (QMException e1) {
			e1.printStackTrace();
		}
    }
   
    /**
	 * 保存.保存完后进入查看状态
	 */
	public void save() {

		try {


			// 显示进度
			((GYBomNoticeMainJFrame) parentFrame).startProgress();
			// 父页面如果没保存，则先保存父页面的更新内容
			JPanel parentPanel = ((GYBomNoticeMainJFrame) parentFrame).getTaskPanel();
			GYBomAdoptNoticeIfc bomIfc = null;
			CJAdoptNoticeViewPanel zcPanel = (CJAdoptNoticeViewPanel) parentPanel;
			zcPanel.save();
			bomIfc = zcPanel.getBomAdoptNotice();
			if (bomIfc == null) {
				throw new QMException("保存车架、驾驶室基础信息出错！");
			}
		
		} catch (QMException ex) {
			((GYBomNoticeMainJFrame) parentFrame).stopProgress();
			ex.printStackTrace();
			DialogFactory.showInformDialog(this, ex.getClientMessage());

		}

		((GYBomNoticeMainJFrame) parentFrame).stopProgress();

	}
    /**
	 * 
	 */
	public void saveCountInvoice(GYBomAdoptNoticeIfc newInfo) {

		try {
	        if(getViewMode() == CREATE_MODE||getViewMode() == SAVE_AS_MODE||getViewMode() == UPDATE_MODE)
	        {
	        	// 设置所有属性和关联，并获得信息封装对象。
				Vector wrapData = commitAllData();
				// 调用服务,执行保存.返回采用单值对象
				Class[] paraClass = { Vector.class,GYBomAdoptNoticeIfc.class };
				Object[] obj = { wrapData, newInfo };
				Vector linksVec = (Vector) GYNoticeHelper.requestServer("GYBomNoticeService","saveInvoice", paraClass, obj);
				this.isHaveSave = true;
	        }
		
		
		} catch (QMException ex) {
			((GYBomNoticeMainJFrame) parentFrame).stopProgress();
			ex.printStackTrace();
			DialogFactory.showInformDialog(this, ex.getClientMessage());

		}

		((GYBomNoticeMainJFrame) parentFrame).stopProgress();

	}
  
    /**
     * 提交数据用以保存
     * @throws QMException
     */
    private Vector commitAllData() throws QMException
    {
    	Object[] data = new Object[11];
    	//散发数据集合
        Vector invoiceVec = new Vector();
		//处理采用列表
		if(invoiceList.getRowCount()>0){
			for(int i = 0 ; i <invoiceList.getRowCount();i++){
				String partID = invoiceList.getCellText(i, 0);
				String partNumber = invoiceList.getCellText(i, 1);
				String partName = invoiceList.getCellText(i, 2);
				String versionValue = invoiceList.getCellText(i, 3);
				String sl = invoiceList.getCellText(i, 4);
				String zzlx = invoiceList.getCellText(i, 5);
				String zplx = invoiceList.getCellText(i, 6);
				String partView = invoiceList.getCellText(i, 7);
				String lifeCycleState = invoiceList.getCellText(i, 8);
				String isVir = invoiceList.getCellText(i, 9);
	        
				GYInvoiceInfo invoice = new GYInvoiceInfo();
				invoice.setPartID(partID);
				invoice.setPartNumber(partNumber);
				invoice.setPartName(partName);
				invoice.setVersionValue(versionValue);
				invoice.setSl(sl);
				invoice.setZzlx(zzlx);
				invoice.setZplx(zplx);
				invoice.setPartView(partView);
				invoice.setLifecyclestate(lifeCycleState);
				invoice.setVirtualPart(isVir);
				invoiceVec.add(invoice);
			}
		}    

		return invoiceVec;
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
     * 设置发布单
     * @param GYBomAdoptNoticeIfc bomAdoptIfc 发布单
     * @param int mode 页面模式
     * @throws QMException
     */
    public void setBomAdoptNotice(GYBomAdoptNoticeIfc bomAdoptIfc, int mode) throws QMException
    {
    	this.myBomAdoptNotice = bomAdoptIfc;
    	if(!firstSelect){
    		invoiceList.clear();
    		if(bomAdoptIfc!=null){
    			// 调用服务,执行查询.返回采用单零件关联对象
                Class[] paraClass = {String.class};
                Object[] obj = {bomAdoptIfc.getBsoID()};
                Collection obj1 = (Collection)GYNoticeHelper.requestServer("GYBomNoticeService", "getInvoiceByNotice", paraClass, obj);
                if(obj1!=null&&obj1.size()>0){
                    this.setMultiListData((Vector)obj1,1);
                }
    		}
      	  this.firstSelect = true;
    	}   
    	this.setViewMode(mode);
    }
    
  


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
                String s = "是否保存散发清单结果？";
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







}


