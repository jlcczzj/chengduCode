/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 TD8316 ���� 2014-7-29 
 * SS2 �Զ�����BOM������ lishu 2017-5-12
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
 * <p>Title:����ɢ���嵥���档</p> <p>Description: </p> <p>Copyright: Copyright (c) 2014</p> <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ����
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
    public ComponentMultiList invoiceList = new ComponentMultiList();//ɢ���嵥�б�
 
    private JScrollPane jScrollPane2 = new JScrollPane();
    //���ư�ť
    private JButton saveNoticeButton = new JButton();
    private JButton cancelNoticeButton = new JButton();
    
    private JButton countInvoiceButton = new JButton();

    
    /** ������ʾģʽ������ģʽ����� */
    public final static int UPDATE_MODE = 0;

    /** ������ʾģʽ������ģʽ����� */
    public final static int CREATE_MODE = 1;

    /** ������ʾģʽ���鿴ģʽ����� */
    public final static int VIEW_MODE = 2;

    /** ������ʾģʽ�����Ϊģʽ����� */
    public final static int SAVE_AS_MODE = 3;
    /** ����ģʽ(Ĭ��Ϊ�鿴) */
    public int mode = VIEW_MODE;


    private JFrame parentFrame = null;

    /**
     * �Ƿ�ִ�����˳�����
     */
    public boolean isHaveSave = false;
    
    private boolean firstSelect = false;
    
    /**
     * ���캯��
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
     * ��ʼ������
     */
    void jbInit() throws QMException
    {
        jScrollPane2.setBorder(BorderFactory.createEtchedBorder());

        this.setLayout( new GridBagLayout());
        adoptNoticePanel.setLayout( new GridBagLayout());
        countPanel.setLayout(new GridBagLayout());
        saveNoticeButton.setMinimumSize(new Dimension(110, 23));
        saveNoticeButton.setPreferredSize(new Dimension(110, 23));
        saveNoticeButton.setText("����");
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
        cancelNoticeButton.setText("ȡ��");
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
        countInvoiceButton.setText("����ɢ���嵥");
        countInvoiceButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                countJButton_actionPerformed(e);
            }
        });

        
        //���ܰ�ť
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
     * ���ɢ������ͷ
     */
    private void getQMMultiListTitle()
    {
    	
		String[] subNoticeTitle = new String[10];
    	subNoticeTitle[0]="bsoID";
    	subNoticeTitle[1]="���";
    	subNoticeTitle[2]="����";
    	subNoticeTitle[3]="�汾";
    	subNoticeTitle[4]="����";
    	subNoticeTitle[5]="����·��";
    	subNoticeTitle[6]="װ��·��";
    	subNoticeTitle[7]="��ͼ";
    	subNoticeTitle[8]="��������״̬";
    	subNoticeTitle[9]="����";
   
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
     * ɢ���嵥�б�ֵ
     * @param Vector strs ɢ���嵥����
     * @param String type ��ֵ���ͣ�type=0Ϊͨ����ť��ֵ��type=1Ϊͨ������ֵ
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
     * ��ø�����
     * @return javax.swing.JFrame
     */
    public JFrame getParentJFrame()
    {
        return this.parentFrame;
    }


    /**
     * ���ý���ģʽ�����������»�鿴����
     * @param aMode �½���ģʽ
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
        { // ����ģʽ
            this.setCreateModel();
            break;
        }
        case UPDATE_MODE:
        { // ����ģʽ
            this.setUpdateModel();
            break;
        }
        case VIEW_MODE:
        { // �鿴ģʽ
            this.setViewModel();
            break;
        }
        case SAVE_AS_MODE:
        { // ���Ϊģʽ
            this.setSaveAsModel();
            break;
        }
        }
    }

    /**
     * ��õ�ǰ����ģʽ
     * @return ��ǰ����ģʽ
     */
    public int getViewMode()
    {
        return mode;
    }

    /**
     * ���ý���Ϊ����״̬
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
     * ���ý���Ϊ����״̬
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
     * ���ý���Ϊ���Ϊ״̬
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
     * ���ý���Ϊ�鿴״̬
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
     * ���ð�ť����ʾ״̬����Ч��ʧЧ��
     * @param flag flagΪTrue����ť��Ч������ťʧЧ
     */
    private void setButtonWhenSave(boolean flag)
    {

        saveJButton.setEnabled(flag);
        quitJButton.setEnabled(flag);
        countInvoiceButton.setEnabled(flag);
    }



    /**
     * ����ɢ���嵥
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
     * ����ɢ���嵥
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
	 * ����ɢ���嵥
	 */   
    public void countInvoice() throws  QMException{
//        System.out.println("����ɢ���嵥");
        CJAdoptNoticeViewPanel parentPanel = (CJAdoptNoticeViewPanel)((GYBomNoticeMainJFrame) parentFrame).getTaskPanel();
        QMPartIfc topPart = parentPanel.cxPart;
        if(topPart==null){
        	throw new QMException("��ó�����Ϣ�������������Ϣ��");
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
					if((data[5].contains("��(��)")&&data[6].contains("��"))||(data[5].contains("Ϳ")&&data[6].contains("��"))){
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
	 * ����.����������鿴״̬
	 */
	public void save() {

		try {


			// ��ʾ����
			((GYBomNoticeMainJFrame) parentFrame).startProgress();
			// ��ҳ�����û���棬���ȱ��游ҳ��ĸ�������
			JPanel parentPanel = ((GYBomNoticeMainJFrame) parentFrame).getTaskPanel();
			GYBomAdoptNoticeIfc bomIfc = null;
			CJAdoptNoticeViewPanel zcPanel = (CJAdoptNoticeViewPanel) parentPanel;
			zcPanel.save();
			bomIfc = zcPanel.getBomAdoptNotice();
			if (bomIfc == null) {
				throw new QMException("���泵�ܡ���ʻ�һ�����Ϣ����");
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
	        	// �����������Ժ͹������������Ϣ��װ����
				Vector wrapData = commitAllData();
				// ���÷���,ִ�б���.���ز��õ�ֵ����
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
     * �ύ�������Ա���
     * @throws QMException
     */
    private Vector commitAllData() throws QMException
    {
    	Object[] data = new Object[11];
    	//ɢ�����ݼ���
        Vector invoiceVec = new Vector();
		//��������б�
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
     * ��ò��õ�
     * @return BomAdoptNoticeIfc
     */
    public GYBomAdoptNoticeIfc getBomAdoptNotice()
    {
        return myBomAdoptNotice;
    }
    /**
     * ���÷�����
     * @param GYBomAdoptNoticeIfc bomAdoptIfc ������
     * @param int mode ҳ��ģʽ
     * @throws QMException
     */
    public void setBomAdoptNotice(GYBomAdoptNoticeIfc bomAdoptIfc, int mode) throws QMException
    {
    	this.myBomAdoptNotice = bomAdoptIfc;
    	if(!firstSelect){
    		invoiceList.clear();
    		if(bomAdoptIfc!=null){
    			// ���÷���,ִ�в�ѯ.���ز��õ������������
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
     * ��ʾ�Ƿ񱣴�,���棬���رս���
     * @param e ActionEvent
     */
    void quitJButton_actionPerformed(ActionEvent e)
    {
        quit();
    }


   
   
    /**
     * ��ʾȷ�϶Ի���
     * @param s �ڶԻ�������ʾ����Ϣ
     * @return ����û�ѡ���ˡ�ȷ������ť���򷵻�true;���򷵻�false
     */
    private int confirmAction(String s)
    {
        String title = "ȷ�ϱ���";
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
     * �˳�
     * 0�����ǣ�1�����  2����ر�
     */
    public void quit()
    {
        if(!isHaveSave)
        {
            if(this.getViewMode() != VIEW_MODE)
            {
                String s = "�Ƿ񱣴�ɢ���嵥�����";
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
                                    return; //���û�м��� ֱ�ӷ���
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


