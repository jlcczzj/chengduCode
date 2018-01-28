/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1  �޸�TD8457 ���� 2014-7-18
 * SS2 �޸�TD8458 ���� 2014-7-18
 * SS3 �޸�TD8464 ���� 2014-7-18
 * SS4 ����BOM����������ӽ����Ʊ�����������Ӧ���ݵĲ��úͲ����ü� xianglx 2014-8-28
 * SS5 A032-2016-0129 �ɶ��û�ʹ�óɶ����������ڡ� liunan 2016-10-11
 * SS6 �Զ�����BOM������ lishu 2017-5-12
 * SS7 �޸���ת�����޷���ȡ�������� maxiaotong 2017-09-05
 * SS8 �޸Ĺ���BOM���������ʱ���������ʾ��������ɡ����⡣maxiaotong 2017-09-15
 * SS9 �����Զ����ɡ����������ơ� liuyuzhu 2017-10-19
 * SS10 �޸Ĺ���BOM���������ʱ���������ҳ���ֹ��޸ĺ��޷����档������ 2017-12-23
 */

package com.faw_qm.gybomNotice.client.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
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


import com.faw_qm.bomNotice.model.BomAdoptNoticeIfc;

import com.faw_qm.bomNotice.model.BomChangeNoticeIfc;
import com.faw_qm.cappclients.util.ComponentMultiList;
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
import com.faw_qm.gybomNotice.client.util.GYBomNoticeTree;
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
//CCBegin SS5
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
//CCEnd SS5



/**
 * <p>Title: �����������档</p> <p>Description: </p> <p>Copyright: Copyright (c) 2014</p> <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ����
 * @version 1.0
 */

public class ZCBomAdoptNoticeViewPanel extends RightPanel
{
	  /**�������*/
	private static String classificationStr = RemoteProperty.getProperty("com.faw_qm.gybomNotice.CarloadAndCarFrameClassification", "J6L;J6M;J6P;J7;MV3;����");
	  /**��������*/
	private static String publishTypeStr = RemoteProperty.getProperty("com.faw_qm.gybomNotice.CarloadPublicType", "��������BOM���õ�;��������BOM���ĵ�");
	 /**��������*/
	private static String lifeCycleTemplateStr = RemoteProperty.getProperty("com.faw_qm.gybomNotice.lifeCycleTemplate1", "����BOM��������������");
	  /**���ϼ�*/
	private static String folderStr = RemoteProperty.getProperty("com.faw_qm.gybomNotice.BomAdoptNoticeFolder1", "\\Root\\��������BOM���ø��ĵ�");
	
	//CCBegin SS5
	private String comp="";
	//CCEnd SS5
	
	JTabbedPane jTabbedPane1 = new JTabbedPane();
	
	private GYBomAdoptNoticeIfc myBomAdoptNotice;
	private DocInfo  designDoc = null;
    private static final long serialVersionUID = 1L;
    private static final boolean verbose = GYNoticeHelper.VERBOSE;
    //����BOMҳ��
    public ZCAdoptNoticeAdjustPanel adustPanle = null;
    //��������
    JLabel publishTypeLabel = new JLabel();
    JComboBox publishTypeBox = new JComboBox();
    //����
    JLabel classificationLabel = new JLabel();
    JComboBox classificationBox = new JComboBox();
    
    //֪ͨ�����
    JLabel adoptnoticenumberLabel = new JLabel();
    JTextField adoptnoticenumberJTF = new JTextField();
    //֪ͨ������
    JLabel adoptnoticenameLabel = new JLabel();
    JTextField adoptnoticenameJTF = new JTextField();
  
    //���ϼ�
    JLabel locationLabel = new JLabel();
    JTextField locationJTF = new JTextField();
    //��������
    JLabel lifeCycleLabel = new JLabel();
    JTextField lifeCycleJTF = new JTextField();
    //�����������
    JLabel designDocLabel = new JLabel();
    JTextField designDocJTF = new JTextField();

    //�����Ʊ����
    JLabel jfBomNoticeLabel = new JLabel();
    JTextField jfBomNoticeJTF = new JTextField();
    BaseValueIfc jfBomNoticeIfc =  null;
    //������Ʊ����
    JLabel zxBomNoticeLabel = new JLabel();
    JTextField zxBomNoticeJTF = new JTextField();
    DocInfo zxDoc = null;
    //����
    JLabel cxPartLabel = new JLabel();
    JTextField cxPartJTF = new JTextField();
    QMPartIfc cxPart = null;
    
    
    //˵��
    JLabel consdescLabel = new JLabel();
    JTextArea consdescJTF = new JTextArea();

    JPanel jPanel1 = new JPanel();//������Ϣ
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();//˵��
    JPanel jPanel5 = new JPanel();//����ҳ��
    JPanel jPanel6 = new JPanel();//buttonҳ��
    JPanel subNoticePanel = new JPanel();//�ӵ�ҳ��
    JPanel jPanel7 = new JPanel();//�ĵ�����

    

    public ComponentMultiList qMMultiList = new ComponentMultiList();//���ܡ���ʻ�ҷ������б�
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JButton addDesignDocJButton = new JButton();
    private JButton addJFDocJButton = new JButton();
    private JButton addZXDocJButton = new JButton();
    private JButton viewNoticeButton = new JButton();
    private JButton saveNoticeButton = new JButton();
    private JButton cancelNoticeButton = new JButton();
    private JButton searchPartButton = new JButton();
    /** ������ʾģʽ������ģʽ����� */
    public final static int UPDATE_MODE = 0;

    /** ������ʾģʽ������ģʽ����� */
    public final static int CREATE_MODE = 1;

    /** ������ʾģʽ���鿴ģʽ����� */
    public final static int VIEW_MODE = 2;
    /** ������ʾģʽ�����Ϊ����� */
    public final static int SAVE_AS_MODE = 3;
    /** ����ģʽ(Ĭ��Ϊ�鿴) */
    private int mode = VIEW_MODE;
    
    //CCBegin SS6
    /** ������ʾģʽ���Զ����ɷ���������� */
    public final static int AUTOCREATE_MODE = 4;

    private JFrame parentFrame = null;

    /**
     * �Ƿ�ִ�����˳�����
     */
    public static boolean isHaveSave = false;

    
    /**
     * ��ȡ��Ų��ñ����
     * @return BaseValueIfc 
     */
    public DocInfo getZxDoc(){
    	return zxDoc;
    }
    /**
     * ����setJfBomNoticeIfc
     * @param BaseValueIfc info
     */
    public void setZxDoc(DocInfo info){
    	this.zxDoc = info;
    	if(info!=null){
    		//CCBegin SS2
    		if(info.getDocNum().startsWith("CONFIRMATION-")&&info.getDocNum().length()>13){
    			String docNum = info.getDocNum();
    			String numStr = docNum.substring(13,docNum.length());
    			zxBomNoticeJTF.setText(numStr);
    		}else{
    			zxBomNoticeJTF.setText(info.getDocNum());
    		}
    		//CCEnd SS2
    	
    	}
    }
    
    /**
     * ��ȡ���Ĳ��ñ����
     * @return BaseValueIfc 
     */
    public BaseValueIfc getJfBomNoticeIfc(){
    	return jfBomNoticeIfc;
    }
    /**
     * ����setJfBomNoticeIfc
     * @param BaseValueIfc info
     */
    public void setJfBomNoticeIfc(BaseValueIfc info){
    	this.jfBomNoticeIfc = info;
    	if(info!=null){
    		if(info instanceof BomAdoptNoticeIfc){
    			jfBomNoticeJTF.setText(((BomAdoptNoticeIfc)info).getAdoptnoticenumber());
    		}else if(info instanceof BomChangeNoticeIfc){
    			jfBomNoticeJTF.setText(((BomChangeNoticeIfc)info).getAdoptnoticenumber());
    		}
    	}
    }
    /**
     * ���캯��
     */
    public ZCBomAdoptNoticeViewPanel(JFrame parent)
    {

        try
        {
         	//CCBegin SS5
        	RequestServer server = RequestServerFactory.getRequestServer();
        	StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
        	info1.setClassName("com.faw_qm.cappclients.conscapproute.util.RouteClientUtil");
        	info1.setMethodName("getUserFromCompany");
        	Class[] classes = {};
        	info1.setParaClasses(classes);
        	Object[] objs = {};
        	info1.setParaValues(objs);
        	comp=(String)server.request(info1);
        	//CCEnd SS5
            this.parentFrame = parent;
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
        publishTypeLabel.setText("��������");
        publishTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        classificationLabel.setText("*����");
        classificationLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        
        //֪ͨ�����
        adoptnoticenumberLabel.setText("���������");
        adoptnoticenumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        adoptnoticenumberJTF.setMaximumSize(new Dimension(100, 22));
        adoptnoticenumberJTF.setMinimumSize(new Dimension(100, 22));
        adoptnoticenumberJTF.setPreferredSize(new Dimension(100, 22));

        //֪ͨ������
        adoptnoticenameLabel.setText("*����������");
        adoptnoticenameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        adoptnoticenameJTF.setText("");
        adoptnoticenameJTF.setMaximumSize(new Dimension(110, 22));
        adoptnoticenameJTF.setMinimumSize(new Dimension(110, 22));
        adoptnoticenameJTF.setPreferredSize(new Dimension(110, 22));
        //��������
        lifeCycleLabel.setText("*��������");
        lifeCycleLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lifeCycleJTF.setMaximumSize(new Dimension(110, 22));
        lifeCycleJTF.setMinimumSize(new Dimension(110, 22));
        lifeCycleJTF.setPreferredSize(new Dimension(110, 22));
        //lifeCycleJTF.setEnabled(false);
        
        //���ϼ�
        locationLabel.setText("*���ϼ�");
        locationLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        locationJTF.setMaximumSize(new Dimension(110, 22));
        locationJTF.setMinimumSize(new Dimension(110, 22));
        locationJTF.setPreferredSize(new Dimension(110, 22));
        //��������֪ͨ��
        designDocLabel.setText("��������֪ͨ��");
        designDocLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        designDocJTF.setText("");
        designDocJTF.setMaximumSize(new Dimension(100, 22));
        designDocJTF.setMinimumSize(new Dimension(100, 22));
        designDocJTF.setPreferredSize(new Dimension(100, 22));
        addDesignDocJButton.setMaximumSize(new Dimension(91, 23));
        addDesignDocJButton.setMinimumSize(new Dimension(91, 23));
        addDesignDocJButton.setPreferredSize(new Dimension(91, 23));
        addDesignDocJButton.setText("���..");
        addDesignDocJButton.addActionListener(new ZCBomAdoptNoticeViewPanel_addDesignDocJButton_actionAdapter(this));
        //�����Ʊ����
        jfBomNoticeLabel.setText("�����Ʊ����");
        jfBomNoticeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        jfBomNoticeJTF.setText("");
        jfBomNoticeJTF.setMaximumSize(new Dimension(100, 22));
        jfBomNoticeJTF.setMinimumSize(new Dimension(100, 22));
        jfBomNoticeJTF.setPreferredSize(new Dimension(100, 22));
        //jfBomNoticeJTF.setEnabled(false);
        addJFDocJButton.setMaximumSize(new Dimension(91, 23));
        addJFDocJButton.setMinimumSize(new Dimension(91, 23));
        addJFDocJButton.setPreferredSize(new Dimension(91, 23));
        addJFDocJButton.setText("���..");
        addJFDocJButton.addActionListener(new ZCBomAdoptNoticeViewPanel_addJFDocJButton_actionAdapter(this));
       
        //�������ı����
        zxBomNoticeLabel.setText("�������ı����");
        zxBomNoticeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        zxBomNoticeJTF.setText("");
        zxBomNoticeJTF.setMaximumSize(new Dimension(100, 22));
        zxBomNoticeJTF.setMinimumSize(new Dimension(100, 22));
        zxBomNoticeJTF.setPreferredSize(new Dimension(100, 22));
       // zxBomNoticeJTF.setEnabled(false);
        addZXDocJButton.setMaximumSize(new Dimension(91, 23));
        addZXDocJButton.setMinimumSize(new Dimension(91, 23));
        addZXDocJButton.setPreferredSize(new Dimension(91, 23));
        addZXDocJButton.setText("���..");
        addZXDocJButton.addActionListener(new ZCBomAdoptNoticeViewPanel_addZXDocJButton_actionAdapter(this));
        //�������ı����
        cxPartLabel.setText("*����");
        cxPartLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        cxPartJTF.setText("");
        cxPartJTF.setMaximumSize(new Dimension(100, 22));
        cxPartJTF.setMinimumSize(new Dimension(100, 22));
        cxPartJTF.setPreferredSize(new Dimension(100, 22));
        //cxPartJTF.setEnabled(false);
        searchPartButton.setMaximumSize(new Dimension(91, 23));
        searchPartButton.setMinimumSize(new Dimension(91, 23));
        searchPartButton.setPreferredSize(new Dimension(91, 23));
        searchPartButton.setText("���..");
        searchPartButton.addActionListener(new ZCBomAdoptNoticeViewPanel_addCxPartJButton_actionAdapter(this));
        //˵��
        consdescLabel.setText("˵��");
        consdescLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        consdescJTF.setText("");
        consdescJTF.setMaximumSize(new Dimension(100, 22));
        consdescJTF.setMinimumSize(new Dimension(100, 22));
        consdescJTF.setPreferredSize(new Dimension(100, 22));
      
        viewNoticeButton.setMaximumSize(new Dimension(110, 23));
        viewNoticeButton.setMinimumSize(new Dimension(110, 23));
        viewNoticeButton.setPreferredSize(new Dimension(110, 23));
        viewNoticeButton.setText("�鿴������");
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
        saveNoticeButton.setText("����");
        saveNoticeButton.addActionListener(new ZCBomAdoptNoticeViewPanel_saveJButton_actionAdapter(this));   	
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
        
        jPanel2.add(designDocLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 12, 2, 4), 0, 0));
        jPanel2.add(designDocJTF, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel2.add(addDesignDocJButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        
        jPanel2.add(jfBomNoticeLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 12, 2, 4), 0, 0));
        jPanel2.add(jfBomNoticeJTF, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel2.add(addJFDocJButton, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel2.add(zxBomNoticeLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 12, 2, 4), 0, 0));
        jPanel2.add(zxBomNoticeJTF, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel2.add(addZXDocJButton, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        
        jPanel2.add(cxPartLabel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 12, 2, 4), 0, 0));
        jPanel2.add(cxPartJTF, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        jPanel2.add(searchPartButton, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 4), 0, 0));
        
        consdescJTF.setLineWrap(true);
        jPanel4.add(consdescLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(2, 58, 2, 4), 0, 0));
        jPanel4.add(jScrollPane2, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 8, 2, 4), 0, 0));
        jScrollPane2.getViewport().add(consdescJTF, null);
       
      //  jPanel6.add(viewBomButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        jPanel6.add(viewNoticeButton, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        jPanel6.add(saveNoticeButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        jPanel6.add(cancelNoticeButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 2, 8), 0, 0));
        qMMultiList.getTable().addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)            
			{
            	//���˫��
				if (e.getButton() ==  MouseEvent.BUTTON1)
				{
					if (e.getClickCount()==2)
					{
						viewSubNoticeJButton_actionPerformed(e);
					}
				}

			}                                                     
        });
        getQMMultiListTitle(null);
        subNoticePanel.add(qMMultiList, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 8, 0, 0), 0, 0));
        jPanel3.add(jPanel1, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 36, 0, 8), 0, 0));
        jPanel3.add(jPanel2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 8, 0, 8), 0, 0));
        //this.add(jPanel3, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 20, 0, 8), 0, 0));
        jPanel3.add(jPanel4, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(2, 20, 2, 8), 0, 0));
        jPanel3.add(subNoticePanel, new GridBagConstraints(0, 3, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 8, 2, 8), 0, 0));
        jPanel3.add(jPanel6, new GridBagConstraints(0, 4, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2, 100, 2, 8), 0, 0));
        adustPanle = new ZCAdoptNoticeAdjustPanel((GYBomNoticeMainJFrame)parentFrame);
        jTabbedPane1.add(jPanel3,"������Ϣ");
        jTabbedPane1.add(adustPanle,"����BOM");
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				
				tabChange(evt);
			}
		});
        this.add(jTabbedPane1, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 8, 0, 8), 0, 0));
    }
    /**
     * ѡ��Tabҳ�����
     */
    private void tabChange(javax.swing.event.ChangeEvent evt) {
		int selectIndex = jTabbedPane1.getSelectedIndex();
		if (selectIndex == 1) {
			try {
				adustPanle.setBomAdoptNotice(myBomAdoptNotice,this.mode);
			} catch (QMException e) {
				e.printStackTrace();
			}
		} 
	}
    /**
     * ����Ӳ��õ���ͷ
     */
    private void getQMMultiListTitle(Vector bomAdoptNoticeVec)
    {
    	String[] subNoticeTitle = new String[4];
    	subNoticeTitle[0]="bsoID";
    	subNoticeTitle[1]="���ܼ�ʻ�ҷ��������";
    	subNoticeTitle[2]="����";
    	subNoticeTitle[3]="��������";
    	
    	int[] mutListSize = new int[4];
    	mutListSize[0]=0;
    	mutListSize[1]=1;
    	mutListSize[2]=1;
    	mutListSize[3]=1;
    	qMMultiList.setHeadings(subNoticeTitle);
    	qMMultiList.setRelColWidth(mutListSize);
    	qMMultiList.setCellEditable(false);
    	qMMultiList.setMultipleMode(false);
    	qMMultiList.setAllowSorting(false);
    	
    	if(bomAdoptNoticeVec!=null){
    		for(int ii = 0 ;ii<bomAdoptNoticeVec.size();ii++){
    			GYBomAdoptNoticeInfo subNotice = (GYBomAdoptNoticeInfo)bomAdoptNoticeVec.get(ii);
        		qMMultiList.addTextCell(ii, 0, subNotice.getBsoID());
            	qMMultiList.addTextCell(ii, 1, subNotice.getAdoptnoticenumber());
            	qMMultiList.addTextCell(ii, 2, subNotice.getAdoptnoticename());
            	qMMultiList.addTextCell(ii, 3, subNotice.getCreateTime().toString());
    		}
    	}else{
    		qMMultiList.clear();
    	}
    	
    }
    
    /**
     * ��õ�ǰ��������
     */
    public void getBoxData(String type)
    {
    	if(type.equals("����")){
    		String[] classificationVec = classificationStr.split(";");
    		if(classificationVec!=null&&classificationVec.length>0){
    			classificationBox.removeAllItems();
    			for(int i = 0 ; i <classificationVec.length;i++){
    				String temClass = classificationVec[i];
    				classificationBox.addItem(temClass);
    			}
    		}
    		
    	}else if(type.equals("��������")){
    		String[] publicTypeVec = publishTypeStr.split(";");
    		if(publicTypeVec!=null&&publicTypeVec.length>0){
    			publishTypeBox.removeAllItems();
    			for(int i = 0 ; i <publicTypeVec.length;i++){
    				String temType = publicTypeVec[i];
    				publishTypeBox.addItem(temType);	
    			}
    		}
    	}else if(type.equals("��������")){
    		//CCBegin SS5
    		//lifeCycleJTF.setText(lifeCycleTemplateStr);
    		if(comp.equals("cd"))
    		{
    			lifeCycleJTF.setText(RemoteProperty.getProperty("com.faw_qm.gybomNotice.cdlifeCycleTemplate1", "�ɶ�����BOM��������������"));
    		}
    		else
    		{
    			lifeCycleJTF.setText(lifeCycleTemplateStr);
    		}
    		//CCEnd SS5
    	}else if(type.equals("���ϼ�")){
    		locationJTF.setText(folderStr) ;
    	}
    	//CCBegin SS7
    	else if(type.equals("����")){
    		String partnumber="";
    		try {
				partnumber = ((QMPartIfc)GYNoticeClientUtil.refresh(myBomAdoptNotice.getTopPart())).getPartNumber();
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		this.cxPartJTF.setText(partnumber);
    	}
    	//CCEnd SS7
    	//CCBegin SS9
    	else if(type.equals("����������")){
    		String noticeName = "";
    		noticeName = myBomAdoptNotice.getAdoptnoticename();
    		this.adoptnoticenameJTF.setText(noticeName);
    	}
    	//CCEnd SS9
        
    }
  
    /**
     * �鿴���õ�  �����ݿͻ�
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
     * �鿴�Ӳ��õ�  �����ݿͻ�
     * @param e ActionEvent
     */
    void viewSubNoticeJButton_actionPerformed(MouseEvent e)
    {
    	int selectIndx = qMMultiList.getSelectedRow();
    	String bsoID = qMMultiList.getCellText(selectIndx, 0);
        HashMap map = new HashMap();
        map.put("bsoID", bsoID);    
        RichToThinUtil.toWebPage("View_GYBomAdoptNotice.screen", map);
    }
    
    /**
     * Ӧ���ĵ���ͨ����������³����㲿��
     * @param e ActionEvent
     */
    void addNewDocJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //����������
        QmChooser qmChooser = new QmChooser("Doc", "�����ĵ�", this.getParentJFrame());
        qmChooser.setChildQuery(false);

        try
        {
            qmChooser.setMultipleMode(false);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            setCursor(Cursor.getDefaultCursor());
            return;
        }
        //���ո���������ִ������
        qmChooser.addListener(new QMQueryListener()
        {
            public void queryEvent(QMQueryEvent e)
            {
                try
                {
                	qmChooser_queryEventDoc(e);
                }catch(QMException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
        
        qmChooser.setVisible(true);

        setCursor(Cursor.getDefaultCursor());

    }
    
    
    private void setMultiListData()
    {
    	if(getViewMode()==CREATE_MODE){
        	qMMultiList.clear();
    	}

    }

    /**
     * ��ø�����
     * @return javax.swing.JFrame
     * @roseuid 402A11F40212
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
        //CCBegin SS6
        if((aMode == this.AUTOCREATE_MODE) || (aMode == this.UPDATE_MODE) || (aMode == this.CREATE_MODE) || (aMode == this.VIEW_MODE) || (aMode == this.SAVE_AS_MODE) )
        //CCEnd SS6
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
        //CCBegin SS6
        case AUTOCREATE_MODE:
        { // �Զ�����ģʽ
            this.setAutoCreateModel();
            break;
        }
        //CCEnd SS6
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
        { // ���Ϊ
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
     * �������Ϊģʽ
     * @throws QMException
     */
    private void setSaveAsModel() throws QMException
    {
    	//getBoxData("����");
    	getBoxData("��������");
    	publishTypeBox.setEnabled(true);
    	publishTypeBox.setSelectedItem(this.getBomAdoptNotice().getPublishType());
  
    	getBoxData("����");
    	classificationBox.setSelectedItem(this.getBomAdoptNotice().getClassification());

    	adoptnoticenumberJTF.setEditable(false);
    	adoptnoticenumberJTF.setText("�Զ����...");
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
    	
    	
    	
    	designDocJTF.setEditable(false);
    	String designDocBsoID = this.getBomAdoptNotice().getDesignDoc();
    	if(designDocBsoID!=null&&!designDocBsoID.equals("")){
    	    designDoc = (DocInfo)GYNoticeClientUtil.refresh(designDocBsoID);
    		designDocJTF.setText(designDoc.getDocNum());
    	}else{
    		designDoc = null;
    	}
    	
    	jfBomNoticeJTF.setEditable(false);
    	String jfNoticeBsoID = this.getBomAdoptNotice().getJfBomnotice();
    	if(jfNoticeBsoID!=null&&!jfNoticeBsoID.equals("")){
    		jfBomNoticeIfc = (BaseValueIfc)GYNoticeClientUtil.refresh(jfNoticeBsoID);
    		if(jfBomNoticeIfc instanceof BomAdoptNoticeIfc){
    			jfBomNoticeJTF.setText(((BomAdoptNoticeIfc)jfBomNoticeIfc).getAdoptnoticenumber());
    		}else if(jfBomNoticeIfc instanceof BomChangeNoticeIfc){
    			jfBomNoticeJTF.setText(((BomChangeNoticeIfc)jfBomNoticeIfc).getAdoptnoticenumber());
    		}
    	}else{
    		jfBomNoticeIfc = null;
    	}
    
    	zxBomNoticeJTF.setEditable(false);
    	String zxDocBsoID = this.getBomAdoptNotice().getZxAdoptNotice();
    	if(zxDocBsoID!=null&&!zxDocBsoID.equals("")){
    	    zxDoc = (DocInfo)GYNoticeClientUtil.refresh(zxDocBsoID);
    	    //CCBegin SS2
    	    String docNum = zxDoc.getDocNum();
    	    if(docNum.startsWith("CONFIRMATION-")&&docNum.length()>13){
    	    	String numStr = docNum.substring(13,docNum.length());
    	    	zxBomNoticeJTF.setText(numStr);
    	    }else{
    	    	zxBomNoticeJTF.setText(docNum);
    	    }
    	    //CCEnd SS2
 
    	}else{
    		zxDoc = null;
    	}

    	cxPartJTF.setEditable(false);
    	String cxPartBsoID = this.getBomAdoptNotice().getTopPart();
    	if(cxPartBsoID!=null&&!cxPartBsoID.equals("")){
    	    cxPart = (QMPartIfc)GYNoticeClientUtil.refresh(cxPartBsoID);
    		cxPartJTF.setText(cxPart.getPartNumber());
    	}else{
    		cxPart = null;
    	}
    	consdescJTF.setEditable(true);
    	consdescJTF.setText(this.getBomAdoptNotice().getConsdesc());
    	
    	addDesignDocJButton.setEnabled(true);
    	addJFDocJButton.setEnabled(true);
    	addZXDocJButton.setEnabled(true);
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
     * ���ý���Ϊ����״̬
     */
    private void setCreateModel()
    {
    	designDoc = null;
    	jfBomNoticeIfc = null;
    	zxDoc = null;
    	cxPart = null;
    	lifeCycleJTF.setEditable(false);
    	adoptnoticenumberJTF.setEditable(false);
    	designDocJTF.setEditable(false);
    	adoptnoticenumberJTF.setText("�Զ����...");
        locationJTF.setEditable(false);
    	viewNoticeButton.setEnabled(false);
    	cancelNoticeButton.setEnabled(true);
    	//jTabbedPane1.setEnabledAt(1, false);
    	getBoxData("��������");
    	getBoxData("����");
    	getBoxData("��������");
    	getBoxData("���ϼ�");
    	designDocJTF.setEditable(false);
    	jfBomNoticeJTF.setEditable(false);
    	zxBomNoticeJTF.setEditable(false);
    	cxPartJTF.setEditable(false);
    	setMultiListData();
        if(!this.isShowing())
        {
            this.setVisible(true);
        }
        isHaveSave = false;
        repaint();
    }
    
    //CCBegin SS6
    /**
     * ���ý���Ϊ����״̬
     */
    private void setAutoCreateModel()
    {
    	System.out.println("111111111111");
        designDoc = null;
        jfBomNoticeIfc = null;
        zxDoc = null;
        cxPart = null;
        lifeCycleJTF.setEditable(false);
        adoptnoticenumberJTF.setEditable(false);
        designDocJTF.setEditable(false);
        adoptnoticenumberJTF.setText("�Զ����...");
        locationJTF.setEditable(false);
        viewNoticeButton.setEnabled(false);
        cancelNoticeButton.setEnabled(true);
        //jTabbedPane1.setEnabledAt(1, false);
        getBoxData("��������");
        getBoxData("����");
        getBoxData("��������");
        getBoxData("���ϼ�");
        //CCBegin SS7
        getBoxData("����");
        //CCEnd SS7
        //CCBegin SS9
        getBoxData("����������");
        //CCEnd SS9
        designDocJTF.setEditable(false);
        jfBomNoticeJTF.setEditable(false);
        zxBomNoticeJTF.setEditable(false);
        cxPartJTF.setEditable(false);
        setMultiListData();
        if(!this.isShowing())
        {
            this.setVisible(true);
        }
        isHaveSave = false;
        repaint();
    }
    //CCEnd SS6

    /**
     * ���ý���Ϊ����״̬ ֻ�б�źͲ��Ų����޸�
     * @throws QMException
     */
    private void setUpdateModel() throws QMException
    {
    	//getBoxData("����");

    	publishTypeBox.setEnabled(false);
    	publishTypeBox.addItem(this.getBomAdoptNotice().getPublishType());
    	//classificationBox.setEnabled(false);
    	getBoxData("����");
    	//CCBegin SS7
//    	classificationBox.setSelectedIndex(0); 
    	classificationBox.setSelectedItem(this.getBomAdoptNotice().getClassification());
    	//CCEnd SS7

    	adoptnoticenumberJTF.setEditable(false);
    	adoptnoticenumberJTF.setText(this.getBomAdoptNotice().getAdoptnoticenumber());
    	//CCBegin SS3
    	adoptnoticenameJTF.setEditable(false);
    	//CCEnd SS3
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
    	
    	
    	
    	designDocJTF.setEditable(false);
    	String designDocBsoID = this.getBomAdoptNotice().getDesignDoc();
    	if(designDocBsoID!=null&&!designDocBsoID.equals("")){
    	    designDoc = (DocInfo)GYNoticeClientUtil.refresh(designDocBsoID);
    		designDocJTF.setText(designDoc.getDocNum());
    	}else{
    		designDoc = null;
    	}
    	
    	jfBomNoticeJTF.setEditable(false);
    	String jfNoticeBsoID = this.getBomAdoptNotice().getJfBomnotice();
    	if(jfNoticeBsoID!=null&&!jfNoticeBsoID.equals("")){
    		jfBomNoticeIfc = (BaseValueIfc)GYNoticeClientUtil.refresh(jfNoticeBsoID);
    		if(jfBomNoticeIfc instanceof BomAdoptNoticeIfc){
    			jfBomNoticeJTF.setText(((BomAdoptNoticeIfc)jfBomNoticeIfc).getAdoptnoticenumber());
    		}else if(jfBomNoticeIfc instanceof BomChangeNoticeIfc){
    			jfBomNoticeJTF.setText(((BomChangeNoticeIfc)jfBomNoticeIfc).getAdoptnoticenumber());
    		}
    	}else{
    		jfBomNoticeIfc = null;
    	}
    
    	zxBomNoticeJTF.setEditable(false);
    	String zxDocBsoID = this.getBomAdoptNotice().getZxAdoptNotice();
    	if(zxDocBsoID!=null&&!zxDocBsoID.equals("")){
    		zxDoc= (DocInfo)GYNoticeClientUtil.refresh(zxDocBsoID);
    		
    		//CCBegin SS2
    	    String docNum = zxDoc.getDocNum();
    	    if(docNum.startsWith("CONFIRMATION-")&&docNum.length()>13){
    	    	String numStr = docNum.substring(13,docNum.length());
    	    	zxBomNoticeJTF.setText(numStr);
    	    }else{
    	    	zxBomNoticeJTF.setText(docNum);
    	    }
    	    //CCEnd SS2
    	}else{
    		zxDoc = null;
    	}

    	cxPartJTF.setEditable(false);
    	String cxPartBsoID = this.getBomAdoptNotice().getTopPart();
    	if(cxPartBsoID!=null&&!cxPartBsoID.equals("")){
    	    cxPart = (QMPartIfc)GYNoticeClientUtil.refresh(cxPartBsoID);
    		cxPartJTF.setText(cxPart.getPartNumber());
    	}else{
    		cxPart = null;
    	}
    	consdescJTF.setEditable(true);
    	consdescJTF.setText(this.getBomAdoptNotice().getConsdesc());
    	
    	addDesignDocJButton.setEnabled(true);
    	addJFDocJButton.setEnabled(true);
    	addZXDocJButton.setEnabled(true);
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
     * ���ý���Ϊ�鿴״̬
     * @throws QMException
     */
    private void setViewModel() throws QMException
    {
        
        //���õ���BOM��ѡ��
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
    	
    	
    	
    	designDocJTF.setEditable(false);
    	String designDocBsoID = this.getBomAdoptNotice().getDesignDoc();
    	if(designDocBsoID!=null&&!designDocBsoID.equals("")){
    	    designDoc = (DocInfo)GYNoticeClientUtil.refresh(designDocBsoID);
    		designDocJTF.setText(designDoc.getDocNum());
    	}else{
    		designDoc = null;
    	}
    	
    	jfBomNoticeJTF.setEditable(false);
    	String jfNoticeBsoID = this.getBomAdoptNotice().getJfBomnotice();
    	if(jfNoticeBsoID!=null&&!jfNoticeBsoID.equals("")){
    	    jfBomNoticeIfc = (BaseValueIfc)GYNoticeClientUtil.refresh(jfNoticeBsoID);
    		if(jfBomNoticeIfc instanceof BomAdoptNoticeIfc){
    			jfBomNoticeJTF.setText(((BomAdoptNoticeIfc)jfBomNoticeIfc).getAdoptnoticenumber());
    		}else if(jfBomNoticeIfc instanceof BomChangeNoticeIfc){
    			jfBomNoticeJTF.setText(((BomChangeNoticeIfc)jfBomNoticeIfc).getAdoptnoticenumber());
    		}
    	}else{
    		jfBomNoticeIfc = null;
    	}
    
    	zxBomNoticeJTF.setEditable(false);
    	String zxDocBsoID = this.getBomAdoptNotice().getZxAdoptNotice();
    	if(zxDocBsoID!=null&&!zxDocBsoID.equals("")){
    		zxDoc = (DocInfo)GYNoticeClientUtil.refresh(zxDocBsoID);
    		//CCBegin SS2
    	    String docNum = zxDoc.getDocNum();
    	    if(docNum.startsWith("CONFIRMATION-")&&docNum.length()>13){
    	    	String numStr = docNum.substring(13,docNum.length());
    	    	zxBomNoticeJTF.setText(numStr);
    	    }else{
    	    	zxBomNoticeJTF.setText(docNum);
    	    }
    	    //CCEnd SS2
    	}else{
    		zxDoc = null;
    	}

    	cxPartJTF.setEditable(false);
    	String cxPartBsoID = this.getBomAdoptNotice().getTopPart();
    	if(cxPartBsoID!=null&&!cxPartBsoID.equals("")){
    		cxPart = (QMPartIfc)GYNoticeClientUtil.refresh(cxPartBsoID);
    		cxPartJTF.setText(cxPart.getPartNumber());
    	}
    	consdescJTF.setEditable(false);
    	consdescJTF.setText(this.getBomAdoptNotice().getConsdesc());
    	
    	addDesignDocJButton.setEnabled(false);
    	addJFDocJButton.setEnabled(false);
    	addZXDocJButton.setEnabled(false);
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
     * ���ð�ť����ʾ״̬����Ч��ʧЧ��
     * @param flag flagΪTrue����ť��Ч������ťʧЧ
     */
    private void setButtonWhenSave(boolean flag)
    {
    
    	saveNoticeButton.setEnabled(flag);
    	cancelNoticeButton.setEnabled(flag);
    }

    /**
     * ������������Ƿ�������Чֵ
     * @return �����������������Чֵ���򷵻�Ϊ��
     * @throws QMException
     */
    private boolean checkRequiredFields() throws QMException
    {
        if(verbose)
        {
            System.out.println("GYBomAdoptNoticeViewPanel.checkRequiredFields() begin...");
        }
        boolean isOK = true;
        StringBuffer message = new StringBuffer();

        if(getViewMode() == CREATE_MODE||getViewMode()==UPDATE_MODE||getViewMode()==SAVE_AS_MODE)
        {
        	 //֪ͨ����
            if(adoptnoticenameJTF==null||adoptnoticenameJTF.getText().equals("")){
            	message.append("�������Ʋ���Ϊ��!");
                isOK = false;
                adoptnoticenameJTF.grabFocus();
            }
            
            //֪ͨ���Ƴ���
            if(adoptnoticenameJTF!=null&&!adoptnoticenameJTF.getText().equals("")){
          	  if(adoptnoticenameJTF.getText().length()>50){
                    message.append("�������Ƴ��Ȳ��ܳ���50���ַ�����������!");
                    isOK = false;
                    adoptnoticenameJTF.grabFocus();
          	  }
            }
            
	        // ��������Ƿ�Ϊ��
	        if((classificationBox.getSelectedItem()==null)||(classificationBox.getSelectedItem().equals("")))
	        {
	        	message.append("���಻��Ϊ��!");
	            isOK = false;
	            classificationBox.grabFocus();
	
	        }
            // ���Ͳ���Ϊ��
	        if(cxPartJTF==null||cxPartJTF.getText().equals(""))
            {
            	message.append("���Ͳ���Ϊ��!");
                isOK = false;
                cxPartJTF.grabFocus();
            }
            //˵�����ܳ���1300�ַ�
            if(consdescJTF!=null&&consdescJTF.getText().length()>1300){
            	message.append("˵�����ܳ���1300��!");
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
     * ���淢����
     * @param e ActionEvent
     */
    void saveJButton_actionPerformed(ActionEvent e)
    {
//        System.out.println("11111111111="+getViewMode());
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        System.out.println("22222");
        setButtonWhenSave(false);
//        System.out.println("3333");
        save();
//        System.out.println("444444");
        setButtonWhenSave(true);
//        System.out.println("555555555");
        setCursor(Cursor.getDefaultCursor());
//        System.out.println("66666666666");
    }

    /**
     * ����.����������鿴״̬
     */
    public void save()
    {

        if(getViewMode() == CREATE_MODE||getViewMode() == SAVE_AS_MODE)
        {
            saveWhenCreate();
        //CCBegin SS6
        }else if(getViewMode() == UPDATE_MODE||getViewMode() == AUTOCREATE_MODE)
        //CCEnd SS6
        {
            saveWhenUpdate();
        }

        ((GYBomNoticeMainJFrame)parentFrame).stopProgress();

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
     * ���ò��õ�
     * @param BomAdoptNoticeIfc bomAdoptIfc 
     * @param int mode
     * @throws QMException
     */
    public void setBomAdoptNotice(GYBomAdoptNoticeIfc bomAdoptIfc, int mode) throws QMException
    {
    	this.myBomAdoptNotice = bomAdoptIfc;
    	if(mode==this.VIEW_MODE||mode==this.UPDATE_MODE){
        	  // ���÷���,ִ�в�ѯ.���ع������Ӳ��õ�����
            Class[] paraClass = {GYBomAdoptNoticeInfo.class};
            Object[] obj = {bomAdoptIfc};
            Collection obj1 = (Collection)GYNoticeHelper.requestServer("GYBomNoticeService", "getSubGYBomAdoptNotice", paraClass, obj);
            this.getQMMultiListTitle((Vector)obj1);
         //   adustPanle.setBomAdoptNotice(myBomAdoptNotice,mode);
    	}
    	
        setViewMode(mode);
      

    }
    /**
     * �ύ�������Ա���
     * @throws QMException
     */
    private Object[] commitAllData() throws QMException
    {
        Object[] data = new Object[10];
        //��������
        if(publishTypeBox.getSelectedItem()!=null&&!publishTypeBox.getSelectedItem().equals("")){
        	data[0] = publishTypeBox.getSelectedItem();
        }
      //����
        if(classificationBox.getSelectedItem()!=null&&!classificationBox.getSelectedItem().equals("")){
        	data[1] = classificationBox.getSelectedItem();
        }
      
        //֪ͨ����
        String adoptnoticenameStr = "";
        if(adoptnoticenameJTF!=null&&(!adoptnoticenameJTF.getText().equals(""))){
        	adoptnoticenameStr = adoptnoticenameJTF.getText();
        }
        data[2] = adoptnoticenameStr; 

        //��������
        String lifeCycleTemlate = "";
        if(lifeCycleJTF!=null&&(!lifeCycleJTF.getText().equals(""))){
        	lifeCycleTemlate = lifeCycleJTF.getText();
        }
        data[3] = lifeCycleTemlate; 
        
        //���ϼ�
        String folderStr = "";
        if(locationJTF!=null&&(!locationJTF.getText().equals(""))){
        	folderStr = locationJTF.getText();
        }
        data[4] = folderStr; 
        
        
        
      //��������֪ͨ��
        if(designDoc!=null){
        	data[5] = designDoc;
        }else{
        	data[5] = null;
        }
        
        //�����Ʊ����
        if(jfBomNoticeIfc!=null){
        	data[6] = jfBomNoticeIfc;
        }else{
        	data[6] = null;
        }
        
        //������Ʊ����
        if(zxDoc!=null){
        	data[7] = zxDoc;
        }else{
        	data[7] = null;
        }
        
        //����
        if(cxPart!=null){
        	data[8] = cxPart;
        }else{
        	data[8] = null;
        }
        
        //˵��
        String remarkStr = "";
        if(consdescJTF!=null&&(!consdescJTF.getText().equals(""))){
        	remarkStr = consdescJTF.getText();
        }
        data[9] = remarkStr; 

        return data;
    }
    /**
     * ������õ�
     */
    private void saveWhenCreate()
    {
        try
        {

            // �����������Ƿ�����
            boolean requiredFieldsFilled = checkRequiredFields();
            if(!requiredFieldsFilled)
            {
                isHaveSave = true;
                return;
            }
            // �����������Ժ͹������������Ϣ��װ����
            Object[] wrapData = commitAllData();
            // ��ʾ����
            ((GYBomNoticeMainJFrame)parentFrame).startProgress();
            // ���÷���,ִ�б���.���ز��õ�����
            Class[] paraClass = {Object[].class};
            Object[] obj = {wrapData};
            GYBomAdoptNoticeIfc newInfo;
            Object[] obj1 = (Object[])GYNoticeHelper.requestServer("GYBomNoticeService", "createGYBomAdoptNotice", paraClass, obj);
            newInfo = (GYBomAdoptNoticeIfc)obj1[0];
            // ���¶��������
            GYBomAdoptNoticeTreeObject newTObj = new  GYBomAdoptNoticeTreeObject(newInfo);

            GYBomNoticeTreePanel treepanel = ((GYBomNoticeMainJFrame)parentFrame).getBomNoticeListTreePanel();
            treepanel.addNode(newTObj);
            this.setBomAdoptNotice(newInfo, this.VIEW_MODE);
            this.isHaveSave = true;
            //�������BOM 
            adustPanle.save();
            // ���ý���
            treepanel.setNodeSelected(newTObj);
            //CCBegin SS8
            JOptionPane.showMessageDialog(new JButton("����ɹ�"), "����ɹ�","��ʾ",
                    JOptionPane.INFORMATION_MESSAGE);
            //CCEnd SS8
        }catch(QMException ex)
        {
        	((GYBomNoticeMainJFrame)parentFrame).stopProgress();
            ex.printStackTrace();
            DialogFactory.showInformDialog(this, ex.getClientMessage());

        }

    }
    /**
     * ���²��õ�
     */
    private void saveWhenUpdate()
    {
        try
        {

            // �����������Ƿ�����
            boolean requiredFieldsFilled = checkRequiredFields();
            if(!requiredFieldsFilled)
            {
                isHaveSave = true;
                return;
            }
            // �����������Ժ͹������������Ϣ��װ����
            Object[] wrapData = commitAllData();
            // ��ʾ����
            ((GYBomNoticeMainJFrame)parentFrame).startProgress();
            // ���÷���,ִ�б���.���ض��ֵ����
            Class[] paraClass = {Object[].class,GYBomAdoptNoticeIfc.class};
            Object[] obj = {wrapData,this.getBomAdoptNotice()};
            GYBomAdoptNoticeIfc newInfo;
            Object[] obj1 = (Object[])GYNoticeHelper.requestServer("GYBomNoticeService", "updateGYBomAdoptNotice", paraClass, obj);
            newInfo = (GYBomAdoptNoticeIfc)obj1[0];
         // ���������

            GYBomAdoptNoticeTreeObject newTObj = new  GYBomAdoptNoticeTreeObject(newInfo);
            GYBomNoticeTreePanel treepanel = ((GYBomNoticeMainJFrame)parentFrame).getBomNoticeListTreePanel();
            
            treepanel.updateNode(newTObj);
            this.setBomAdoptNotice(newInfo, this.VIEW_MODE);
            //SS1
            this.isHaveSave = true;
            //SS1
            //�������BOM 
//CCBegin SS4
						adustPanle.setIsPaint(false);
//						CCBegin SS10
						adustPanle.setViewMode(0);
//						CCEnd SS10
            adustPanle.save();
						adustPanle.setIsPaint(true);
//CCEnd SS4
            // ���ý���
            treepanel.setNodeSelected(newTObj);
            
            //CCBegin SS8
            JOptionPane.showMessageDialog(new JButton("����ɹ�"), "����ɹ�", "��ʾ",
                    JOptionPane.INFORMATION_MESSAGE);
            //CCEnd SS8
        }catch(QMException ex)
        {
        	((GYBomNoticeMainJFrame)parentFrame).stopProgress();
            ex.printStackTrace();
            DialogFactory.showInformDialog(this, ex.getClientMessage());

        }

    }
    /**
     * Ӧ���㲿����ͨ��������ӻ��������㲿��
     * @param e ActionEvent
     */
    void addZcPartJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       
        //����������
        QmChooser qmChooser = new QmChooser("QMPart", "�����㲿��", this.getParentJFrame());
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
        //���ո���������ִ������
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
     * �����㲿�������¼�����
     * @param e ���������¼�
     * @throws QMException
     */
    private void qmChooser_queryEventZcPart(QMQueryEvent e) throws QMException
    {
        if(e.getType().equals(QMQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(QmQuery.OkCMD))
            {
                //��������������������������㲿��
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
     * Ӧ���ĵ���ͨ����������³����㲿��
     * @param e ActionEvent
     */
    void addDesignDocJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        //����������
        QmChooser qmChooser = new QmChooser("Doc", "�����ĵ�", this.getParentJFrame());
        qmChooser.setChildQuery(false);

        try
        {
            qmChooser.setMultipleMode(false);
        }catch(Exception ex)
        {
            ex.printStackTrace();
            setCursor(Cursor.getDefaultCursor());
            return;
        }
        //���ո���������ִ������
        qmChooser.addListener(new QMQueryListener()
        {
            public void queryEvent(QMQueryEvent e)
            {
                try
                {
                	qmChooser_queryEventDoc(e);
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
     * �����ĵ������¼�����
     * @param e ���������¼�
     * @throws QMException
     */
    private void qmChooser_queryEventDoc(QMQueryEvent e) throws QMException
    {
        if(e.getType().equals(QMQueryEvent.COMMAND))
        {
            if(e.getCommand().equals(QmQuery.OkCMD))
            {
                //��������������������������ĵ�
                QmChooser c = (QmChooser)e.getSource();

                BaseValueIfc[] bvi = c.getSelectedDetails();
                if(bvi != null && bvi.length > 0)
                {
                	BaseValueIfc selectDoc  = (BaseValueIfc)bvi[0];
                	designDoc = (DocInfo)selectDoc;
                	designDocJTF.setText(designDoc.getDocNum());
                   
                }
            }
        }

    }
    /**
     * ��ʾ�Ƿ񱣴�,���棬���رս���
     * @param e ActionEvent
     */
    void quitJButton_actionPerformed(ActionEvent e)
    {
        quit();
    }

    // 0�����ǣ�1�����  2����ر�
    public void quit()
    {
        if(!isHaveSave)
        {
            if(this.getViewMode() != VIEW_MODE)
            {
                String s = "�Ƿ񱣴淢������";
                int k = confirmAction(s);
                if(k == 0)
                {
                    isHaveSave = true;
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
    /**x
     * �������֪ͨ��
     * @param e ActionEvent
     */
    void addJFNoticeJButton_actionPerformed(ActionEvent e)
    {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SearchJFNoticeDialog searchDialog = new SearchJFNoticeDialog((GYBomNoticeMainJFrame)this.parentFrame);

        searchDialog.setVisible(true);
        setCursor(Cursor.getDefaultCursor());

//CCBegin SS4
				if (jfBomNoticeIfc!=null)
				{
					try {
         		Class[] paraClass1 = {BaseValueIfc.class};
          	Object[] obj1 = {jfBomNoticeIfc};
          	Vector vector1 = (Vector)GYNoticeHelper.requestServer("GYBomNoticeService", "getNouseByID", paraClass1, obj1);
          	Vector vector2 = (Vector)GYNoticeHelper.requestServer("GYBomNoticeService", "getUseByID", paraClass1, obj1);

//		    	System.out.println("aaaa"+jfBomNoticeIfc.getBsoID());
//		    	String[] part11={"a1","b1","c1","d1","e11","f2","g44","h","i"};

//		    	vector1.add(part11);
						if (adustPanle.getBomAdoptNotice()==null)
							adustPanle.setBomAdoptNotice1(myBomAdoptNotice,this.mode);
			    	adustPanle.setMultiListData("������",vector1);
			    	adustPanle.setMultiListData("����",vector2);
			      adustPanle.repaint();
					} catch (QMException ex) {
						ex.printStackTrace();
					}
	    }
//CCEnd SS4
    }
    /**
     * �������ı����
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
     * ��ʾȷ�϶Ի���
     * @param s �ڶԻ�������ʾ����Ϣ
     * @return ����û�ѡ���ˡ�ȷ������ť���򷵻�true;���򷵻�false
     */
    private int confirmAction(String s)
    {
        String title = "�˳�";
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
 * <p>Title: ��Ų��á��������ѯ
 * @author ����
 * @version 1.0
 */
class ZCBomAdoptNoticeViewPanel_addJFDocJButton_actionAdapter implements java.awt.event.ActionListener
{
	ZCBomAdoptNoticeViewPanel adaptee;

	ZCBomAdoptNoticeViewPanel_addJFDocJButton_actionAdapter(ZCBomAdoptNoticeViewPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.addJFNoticeJButton_actionPerformed(e);
    }
}
/**
 * <p>Title: ���Ĳ��á��������ѯ
 * @author ����
 * @version 1.0
 */
class ZCBomAdoptNoticeViewPanel_addZXDocJButton_actionAdapter implements java.awt.event.ActionListener
{
	ZCBomAdoptNoticeViewPanel adaptee;

	ZCBomAdoptNoticeViewPanel_addZXDocJButton_actionAdapter(ZCBomAdoptNoticeViewPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.addZXNoticeJButton_actionPerformed(e);
    }
}
class ZCBomAdoptNoticeViewPanel_addDesignDocJButton_actionAdapter implements java.awt.event.ActionListener
{
	ZCBomAdoptNoticeViewPanel adaptee;

	ZCBomAdoptNoticeViewPanel_addDesignDocJButton_actionAdapter(ZCBomAdoptNoticeViewPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.addDesignDocJButton_actionPerformed(e);
    }
}
class ZCBomAdoptNoticeViewPanel_addCxPartJButton_actionAdapter implements java.awt.event.ActionListener
{
	ZCBomAdoptNoticeViewPanel adaptee;

	ZCBomAdoptNoticeViewPanel_addCxPartJButton_actionAdapter(ZCBomAdoptNoticeViewPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.addZcPartJButton_actionPerformed(e);
    }
}
class ZCBomAdoptNoticeViewPanel_saveJButton_actionAdapter implements java.awt.event.ActionListener
{
	ZCBomAdoptNoticeViewPanel adaptee;

	ZCBomAdoptNoticeViewPanel_saveJButton_actionAdapter(ZCBomAdoptNoticeViewPanel adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.saveJButton_actionPerformed(e);
    }
}