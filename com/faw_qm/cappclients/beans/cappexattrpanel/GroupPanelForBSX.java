/** ���ɳ���ʱ�� 2003/08/11
 * �����ļ����� ExtendResource_en_US.java
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/06/04 �촺Ӣ �μ�DefectID=2177
 * CR2 2009/06/30 �촺Ӣ �μ�DefectID=2472
 * CR3 2009/07/03 ������ �μ�DefectID=2508
 * CCBegin by liunan 2011-03-15 �򲹶�v4r3_p028_20110216
 * CR5 2011/02/10 �촺Ӣ �μ�TD����2372
 * CCEnd by liunan 2011-03-15 
 * 
 * SS1 ������ 2014-05-05 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2841��
 * SS2 ������ 2014-05-05 ȥ��������չ���Ա༭��ť��
 * SS3 ������ 2014-05-17 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2839��
 * SS4 ������ 2014-05-19 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2840��
 * SS5 ������ 2014-05-19 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2842��
 * SS6 ������ 2014-05-22 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2876��
 * SS7 ������ 2014-05-26 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2883
 * SS8 ������ 2014-05-29 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2881
 * SS9 ������ 2014-05-29 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2902
 * SS10 liunan 2014-6-3 �����󣬶ԡ���������/�����������У����ж��и��ơ����С�ճ����ɾ����
 * SS11 ������ 2014-6-10 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2877
 * SS12 ������ 2014-6-13 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2916
 * SS13 ������ 2014-6-19 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2950
 * SS14 ������ 2014-7-2  �޸ķ���ƽ̨����A005-2014-2977
 * SS15 ������ 2014-7-2  �޸ķ���ƽ̨����A005-2014-2976
 * SS16 ������ 2014-7-2  �޸ķ���ƽ̨����A005-2014-2974
 * SS17 ����ֹ�¼�����Դ��Ų����ڣ�����ոñ�ţ��������ֹ�¼�루���Ƶ���������Ŀǰ�޷����ƣ� liunan 2014-8-14
 * SS18 ����/�������� �У�װ����Ҫ¼��������֣�����Ҫ�ж��Ƿ��й�װ�����Ӳ���Ҫ��ʾ�û�¼��ϵͳ�д��ڵĹ�װ�� liunan 2014-10-14
 * SS19 �����޸�SS18��erp����У�������¼�벻�������ݡ� liunan 2014-10-22
 * SS20 ������ϸ�����С����Ӧ��¼������������Ҫ���ӡ���������143��Ϊ215������¼��19�����֡� liunan 2014-12-30
 * SS21 ���Ƽƻ�����Դ�빤������Դ������һ�£��豸ɾ����û��ɾ����������Դ������ liunan 2015-3-2
 * SS22 ���Ƽƻ�����Դ�빤������Դ������һ�£�3�й�װά����14�������ά����û��ͬ�����¹�������Դ������ liunan 2015-3-16
 * SS23 ȥ����װ������û��ȥ���������������� liunan 2015-3-19
 * SS24 ���Ƽƻ������У�������ڲ鿴״̬ʱ�����������Ҳ���������룬Ȼ������޸ģ�������޷����档���ڲ鿴ģʽ��Ϊ���ɱ༭�� liunan 2015-3-25
 */


package com.faw_qm.cappclients.beans.cappexattrpanel;

import java.awt.*;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.text.BadLocationException;

import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.util.CappServiceHelper;
import com.faw_qm.cappclients.util.*;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.extend.util.ExtendAttGroup;
import com.faw_qm.extend.model.ExtendAttriedIfc;
import com.faw_qm.extend.util.ExtendAttModel;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;

import java.util.Locale;
import java.util.Vector;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.Collection;
import java.util.Iterator;

import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.jview.chrset.DataDisplay;
import com.faw_qm.jview.chrset.DataSource;
import com.faw_qm.jview.chrset.SpeCharaterInfo;
import com.faw_qm.jview.chrset.SpeCharaterTextPanel;
import com.faw_qm.jview.chrset.SpecialCharacter;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.print.clients.printModel.multiLine.MultiLineController;
import com.faw_qm.resource.exception.ResourceException;
import com.faw_qm.resource.support.client.model.CEquipment;
import com.faw_qm.resource.support.client.model.CTool;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.resource.support.model.QMToolInfo;

import java.util.Hashtable;
import com.faw_qm.codemanage.model.CodingIfc;
import java.util.ArrayList;

import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQuery;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.capp.view.*;
import com.faw_qm.cappclients.capp.web.ViewTechnicsAttrUtil;
import com.faw_qm.doc.model.DocInfo;

/**
 * <p>Title:�������Է�װ���� </p>
 * <p>Description:�����������,���Ƴ������Ե���ʾ,��ѯ,����,ɾ���ȹ��� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: faw_qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 * ���⣨1��20080822 xucy  �޸�ԭ�򣺽����������̡��е���ͬ�б������ݡ�ȫ�������������б������ݣ������Ƶ������Ƽƻ�����
 */
public class GroupPanelForBSX extends JPanel
{
    private JScrollPane jScrollPane1 = new JScrollPane();
    
    
    private static ResourceBundle resource = null;

    //������ʾ�ı��
    public ComponentMultiList multiList = new ComponentMultiList();
    
    
    
    //public CappExAttrPanel processFlowJPanel;
    //���⣨1��20080822 xucy  �޸�ԭ�򣺽����������̡��е���ͬ�б������ݡ�ȫ�������������б������ݣ������Ƶ������Ƽƻ�����
    //��������multilist
    public  ComponentMultiList flowMultiList = new ComponentMultiList();
    
    

    //��Ӱ�ť
    private JButton addButton = new JButton();

    //ɾ����ť
    private JButton deleteButton = new JButton();
    private Locale local = RemoteProperty.getVersionLocale();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    

    //������
    ExtendAttGroup group;
    //�����������ڵ�����
    ExtendAttContainer container;
    //������Ĵ�С
    int groupSize;

    //�Ƿ��ǿ��Ƽƻ�
    boolean isControlPlan = false;
    //20070321 xuejing add
    /**
     * ����������
     */
    //private ArrayList calculateCols;
    //�����棭����չ�����ܽ���
    CappExAttrPanelForBSX parentPanel;
    //ö���໶��
    HashMap etMaps = new HashMap();
    //�Ƿ���ȱʡ����ֵ
    private boolean hasDefault;
    static boolean VERBOSE = new Boolean(RemoteProperty.getProperty(
            "com.faw_qm.extend.verbose", "true")).booleanValue();
    private int[] rds;
    private static boolean verbose = RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true").equals("true");
    /**
     * 20070321Ѧ����ӣ����㰴ť
     */
    private JButton calculateButton = new JButton(); //debug��ʶ
    
    //CCBegin SS4
    
    private JLabel eqequipLabel = new JLabel(); 
    private JLabel toolLabel=new JLabel();
    private JLabel materialLabel=new JLabel();
    private JButton eqequipDecreaseButton = new JButton(); 
    private JButton toolDecreaseButton = new JButton(); 
    private JButton materialDecreaseButton = new JButton(); 
    //CCEnd SS4
    
    //CCBegin SS10
    private JLabel multiLabel=new JLabel();
    private JButton copyMultiButton = new JButton(); 
    private JButton cutMultiButton = new JButton();
    private JButton pasteMultiButton = new JButton();
    private JButton deleteMultiButton = new JButton();
    private JButton moveupMultiButton = new JButton();
    private String[] copystr = null;
    //CCEnd SS10
    
    //�豸
    private JButton eqequipButton = new JButton(); 
    //��װ
    private JButton toolButton = new JButton(); 
    //����
    private JButton materialButton = new JButton(); 
    
    
    
    
    //add by wangh on 20070531
       // CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ����  �̳а�ť ʵ�ֹ�������,���Ƽƻ�������FMEA�����ݼ̳�
   //��������и��ư�ť
    private JButton inheritButton = new JButton();
    private JButton copyAddButton = new JButton();
    
    private String groupname = "";
  //  CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
    protected static String RESOURCE
    = "com.faw_qm.cappclients.capp.util.CappLMRB";
    
    private static String RESOURCEOther = "com.faw_qm.clients.beans.BeansRB";
    
    /**������*/
    private JFrame parentJFrame;

    /**
     * 20070627wangh���,�༭��ť
     */
    private JButton editButton = new JButton();
    //���⣨1��20080822 xucy  �޸�ԭ�򣺽����������̡��е���ͬ�б������ݡ�ȫ�������������б������ݣ������Ƶ������Ƽƻ�����
    private JButton attrButton = new JButton(); 
    
    public Vector eqDeleteVec=new Vector();
    public Vector  materDeleteVec=new Vector();
    public Vector toolDeleteVec=new Vector();
    
    
    
    /**
     * ���캯��
     * @param container ExtendAttContainer����չ��������
     * @param groupName String����������
     * @param isControlPlan boolean�����Ƿ��ǿ��Ƽƻ�
     * @see ExtendAttContainer
     */
    public GroupPanelForBSX(ExtendAttContainer container, String groupName,
                      boolean isControlPlan)
    {
  	    //CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
        groupname = groupName;
        //CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
        try
        {
            this.container = container;
            this.isControlPlan = isControlPlan;
            //����ǲ�ͬ��
            if (!isControlPlan)
            {
                group = this.container.getAttGroupDes(groupName);
                groupSize = group.getCount();
            }
            //����ǿ��Ƽƻ�
            else
            {
                group = this.container.getPlanGroupDes();
                groupSize = group.getCount();
            }
            // this.multiList.setCheckModel(true);
            String[] heads = new String[groupSize];
            int[] colSize=new int[groupSize];
            for (int i = 0; i < groupSize; i++)
            {
            	
                ExtendAttModel model = group.getAttributeAt(i);

                prepareDatas(model);
                //���ñ���
                heads[i] = model.getAttDisplay();
                  //CCBegin SS8
                
               //CCBegin SS7
            	  if(i<=19){

                      
                      colSize[i] =1;
                }
                else if(i>19&&i<24)
                 colSize[i] =0;
                if(i>=24){
                  colSize[i] =1;
                }

                //CCEnd SS7
                
                //CCEnd SS8
                
            }

            this.multiList.setHeadings(heads);
            multiList.setRelColWidth(colSize);
            
            
            //CCBegin SS10
            multiList.setMultipleMode(true);
            //CCEnd SS10
            
            //add by wangh on 20070518(�������ó��������в��ɱ༭����)
            //int r= multiList.getSelectedRow();
//  		CCBegin by liuzc 2009-12-21 ԭ�򣺽������,FMEA����   �μ�TD��2685
            int n=this.getColByAttname("��ʩ���PRN");
//  		CCEnd by liuzc 2009-12-21 ԭ�򣺽������,FMEA����   �μ�TD��2685
            int m=this.getColByAttname("����˳����(PRN)");
            multiList.setColsEnabled(new int[]{n,m}, false);
//            defaultColumnWidths();
            
            multiList.getTable().getModel().addTableModelListener(new myTableModelListener());
          //CCBegin SS13
            multiList.getTable().putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
          //CCEnd SS13
            multiList.addActionListener(new java.awt.event.
                    ActionListener(){
            	
            	 public void actionPerformed(ActionEvent e)
                 {
                     extend_actionPerformed(e);
                 }
            });
            jbInit();
            
            
  //CCBegin SS8
            //װ��
            if(container.getFirstClassify().equals("consQMAssembleProcedure")){	

            	 //CCBegin SS20
        	     //int[] colwith={330,100,100,80,70,40,0,0,0,40,60,60,60,100,100,45,45,60,65,90,0,0,0,0};
        	     int[] colwith={370,100,100,80,70,40,0,0,0,40,60,60,60,100,100,45,45,60,65,90,0,0,0,0};
        	     //CCEnd SS20
        	     multiList.FitTableColumns(colwith);
        	     multiList.setAllowSorting(false);
        	 
            }
            //����
              if(container.getFirstClassify().equals("consQMMachineProcedure")) {
            	
            	 int[] colwith={200,100,100,80,70,40,30,30,30,40,60,60,60,100,100,45,45,60,65,90,0,0,0,0};
         	     multiList.FitTableColumns(colwith);
         	     multiList.setAllowSorting(false);
            	
              }
            
            
            //CCEnd SS8
            
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * ���ݾ����������֯����
     * @param model ExtendAttModel������һ�����Եķ�װ��
     * @see ExtendAttModel
     */
    private void prepareDatas(ExtendAttModel model)
    {
        HashMap map = model.getFeature();
        if (model.getAttType().equals("EnumeratedType"))
        {
            String classPath = (String) map.get("classpath");
            String newString = (String) map.get("newMethod");
            try
            {
                if (VERBOSE)
                {
                    System.out.println("����·��==  " + classPath);
                }
                Class class1 = Class.forName(classPath);
                Method newMethod = class1.getMethod(newString, null);
                EnumeratedType et1 = (EnumeratedType) newMethod.invoke(class1, null);
                EnumeratedType[] ets = et1.getValueSet();
                this.etMaps.put(classPath, ets);
            }
            catch (Exception e)
            {
                if(verbose)
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
        else
        if (model.getAttType().equals("Coding"))
        {
            try
            {
                String sortType = (String) map.get("SortType");
                StringTokenizer ston = new StringTokenizer(sortType, ";");
                String para1 = ston.nextToken();
                if (VERBOSE)
                {
                    System.out.println("para1== " + para1);
                }
                String para2 = ston.nextToken();
                if (VERBOSE)
                {
                    System.out.println("para2==  " + para2);
                }
                String para3 = ston.nextToken();
                Collection sorts = CappTreeHelper.getCoding(para1, para2, para3);
                this.etMaps.put(sortType, sorts);
            }
            catch (QMRemoteException e)
            {
                if(verbose)
                e.printStackTrace();
                JOptionPane.showMessageDialog(this.getParentJFrame(),
                                              e.getClientMessage());
            }
        }
        else
        if (model.getAttType().equals("String"))
        {
            String refType = (String) map.get("RefType");
            if (refType == null)
            {
                return;
            }
            if (refType.equals("EnumerateType"))
            {
                String classPath = (String) map.get("classpath");
                String newString = (String) map.get("newMethod");
                try
                {
                    Class class1 = Class.forName(classPath);
                    Method newMethod = class1.getMethod(newString, null);
                    EnumeratedType et1 = (EnumeratedType) newMethod.invoke(
                            class1, null);
                    EnumeratedType[] ets = et1.getValueSet();
                    this.etMaps.put(classPath, ets);
                }
                catch (Exception e)
                {
                    if(verbose)
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this.getParent(),
                                                  e.getMessage());
                }
            }
            else
            if (refType.equals("ComboAtts"))
            {
                try
                {
                    String sortType = (String) map.get("SortType");
                    StringTokenizer ston = new StringTokenizer(sortType, ";");
                    String para1 = ston.nextToken();
                    if (VERBOSE)
                    {
                        System.out.println("para1==  " + para1);
                    }
                    String para2 = ston.nextToken();
                    if (VERBOSE)
                    {
                        System.out.println("para2==  " + para2);
                    }
                    String para3 = ston.nextToken();
                    Collection sorts = CappTreeHelper.getCoding(para1, para2,
                            para3);
                    this.etMaps.put(sortType, sorts);

                }
                catch (QMRemoteException e)
                {
                      if(verbose)
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this.getParentJFrame(),
                                                  e.getClientMessage());
                }
            }
        }
    }



    /**
     * �����ʼ��
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        this.setLayout(gridBagLayout1);
        //CCBegin SS4
        addButton.setMaximumSize(new Dimension(145, 23));
        addButton.setMinimumSize(new Dimension(145, 23));
        addButton.setPreferredSize(new Dimension(145, 23));
      //CCEnd SS4
        addButton.setMnemonic('A');
        addButton.setText("���(A)");
        addButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                addButton_actionPerformed(e);
            }
        });
        //CCBegin SS4
        deleteButton.setMaximumSize(new Dimension(145, 23));
        deleteButton.setMinimumSize(new Dimension(145, 23));
        deleteButton.setPreferredSize(new Dimension(145, 23));
        //CCEnd SS4
        deleteButton.setMnemonic('D');
        deleteButton.setText("ɾ��(D)");
        deleteButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                deleteButton_actionPerformed(e);
            }
        });
    calculateButton.setMaximumSize(new Dimension(80, 23));
    calculateButton.setMinimumSize(new Dimension(80, 23));
    calculateButton.setPreferredSize(new Dimension(80, 23));
    calculateButton.setRolloverEnabled(true);
    calculateButton.setText("����");
    calculateButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        calculateButton_actionPerformed(e);
      }
    });
    
    
    //���⣨1��20080822 xucy  �޸�ԭ�򣺽����������̡��е���ͬ�б������ݡ�ȫ�������������б������ݣ������Ƶ������Ƽƻ�����
    //CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
    inheritButton.setMaximumSize(new Dimension(80, 23));
    inheritButton.setMinimumSize(new Dimension(80, 23));
    inheritButton.setPreferredSize(new Dimension(80, 23));
    inheritButton.setRolloverEnabled(true);
    inheritButton.setText("����");
    inheritButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	          inheritButton_actionPerformed(e);
      }
    });
    //CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
    //CCBegin by leixiao 2009-7-2 ԭ����Ӹ��ư�ť
    copyAddButton.setMaximumSize(new Dimension(80, 23));
    copyAddButton.setMinimumSize(new Dimension(80, 23));
    copyAddButton.setPreferredSize(new Dimension(80, 23));
    copyAddButton.setText("����");
    copyAddButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	          copyAddButton_actionPerformed(e);
      }
    });
    //add by wangh on 20070627
    editButton.setMaximumSize(new Dimension(80, 23));
    editButton.setMinimumSize(new Dimension(80, 23));
    editButton.setPreferredSize(new Dimension(80, 23));
    editButton.setRolloverEnabled(true);  
    editButton.setText("�༭");
    editButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	  editButton_actionPerformed(e);
      }
    });  
    //CCEnd by leixiao 2009-7-2
    
    //CCBegin SS4
    
    eqequipLabel.setMaximumSize(new Dimension(25, 23));
    eqequipLabel.setMinimumSize(new Dimension(25, 23));
    eqequipLabel.setPreferredSize(new Dimension(25, 23));
    eqequipLabel.setText("�豸");
    
    eqequipButton.setMaximumSize(new Dimension(40, 23));
    eqequipButton.setMinimumSize(new Dimension(40, 23));
    eqequipButton.setPreferredSize(new Dimension(40, 23));
    eqequipButton.setRolloverEnabled(true);
    eqequipButton.setText("+");
    
    eqequipDecreaseButton.setMaximumSize(new Dimension(60, 23));
    eqequipDecreaseButton.setMinimumSize(new Dimension(60, 23));
    eqequipDecreaseButton.setPreferredSize(new Dimension(60, 23));
    eqequipDecreaseButton.setText("-");
    
    
    eqequipDecreaseButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	eqequipDecreaseButton_actionPerformed(e);
        }
      });
    
  //CCEnd SS4
    
    eqequipButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	  eqequipButton_actionPerformed(e);
      }
    });
    
    //CCBegin SS4
    
    toolLabel.setMaximumSize(new Dimension(25, 23));
    toolLabel.setMinimumSize(new Dimension(25, 23));
    toolLabel.setPreferredSize(new Dimension(25, 23));
    toolLabel.setText("��װ");
    
    toolButton.setMaximumSize(new Dimension(40, 23));
    toolButton.setMinimumSize(new Dimension(40, 23));
    toolButton.setPreferredSize(new Dimension(40, 23));
    toolButton.setText("+");
    
    toolDecreaseButton.setMaximumSize(new Dimension(60, 23));
    toolDecreaseButton.setMinimumSize(new Dimension(60, 23));
    toolDecreaseButton.setPreferredSize(new Dimension(60, 23));
    toolDecreaseButton.setText("����-");
    
    toolDecreaseButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	toolDecreaseButton_actionPerformed(e);
        }
      });
    
    //CCEnd SS4
    toolButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	  toolButton_actionPerformed(e);
      }
    });
    
    
    //CCBegin SS4
    
    materialLabel.setMaximumSize(new Dimension(25, 23));
    materialLabel.setMinimumSize(new Dimension(25, 23));
    materialLabel.setPreferredSize(new Dimension(25, 23));
    materialLabel.setText("����");
    
    materialButton.setMaximumSize(new Dimension(40, 23));
    materialButton.setMinimumSize(new Dimension(40, 23));
    materialButton.setPreferredSize(new Dimension(40, 23));
    materialButton.setRolloverEnabled(true);
    materialButton.setText("+");
    
    materialDecreaseButton.setMaximumSize(new Dimension(60, 23));
    materialDecreaseButton.setMinimumSize(new Dimension(60, 23));
    materialDecreaseButton.setPreferredSize(new Dimension(60, 23));
    materialDecreaseButton.setText("-");
    materialDecreaseButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	materialDecreaseButton_actionPerformed(e);
        }
      });
    
    //CCEnd SS4
    materialButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	  materialButton_actionPerformed(e);
      }
    });
    
    
    //CCBegin SS10
    multiLabel.setMaximumSize(new Dimension(110, 23));
    multiLabel.setMinimumSize(new Dimension(110, 23));
    multiLabel.setPreferredSize(new Dimension(110, 23));
    multiLabel.setText("�������ƴ���");
    
    copyMultiButton.setMaximumSize(new Dimension(110, 23));
    copyMultiButton.setMinimumSize(new Dimension(110, 23));
    copyMultiButton.setPreferredSize(new Dimension(110, 23));
    //copyMultiButton.setRolloverEnabled(true);
    copyMultiButton.setText("���и���");
    
    cutMultiButton.setMaximumSize(new Dimension(110, 23));
    cutMultiButton.setMinimumSize(new Dimension(110, 23));
    cutMultiButton.setPreferredSize(new Dimension(110, 23));
    cutMultiButton.setText("���м���");
    
    pasteMultiButton.setMaximumSize(new Dimension(110, 23));
    pasteMultiButton.setMinimumSize(new Dimension(110, 23));
    pasteMultiButton.setPreferredSize(new Dimension(110, 23));
    pasteMultiButton.setText("����ճ��");
    
    deleteMultiButton.setMaximumSize(new Dimension(110, 23));
    deleteMultiButton.setMinimumSize(new Dimension(110, 23));
    deleteMultiButton.setPreferredSize(new Dimension(110, 23));
    deleteMultiButton.setText("����ɾ��");
    
    moveupMultiButton.setMaximumSize(new Dimension(110, 23));
    moveupMultiButton.setMinimumSize(new Dimension(110, 23));
    moveupMultiButton.setPreferredSize(new Dimension(110, 23));
    moveupMultiButton.setText("ɾ������");
    
    copyMultiButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	copyMultiButton_actionPerformed(e);
        }
      });
    cutMultiButton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	cutMultiButton_actionPerformed(e);
        }
      });
    pasteMultiButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	  pasteMultiButton_actionPerformed(e);
      }
    });
    deleteMultiButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	  deleteMultiButton_actionPerformed(e);
      }
    });
    moveupMultiButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
    	  moveupMultiButton_actionPerformed(e);
      }
    });
  //CCEnd SS10
  
        //CCBegin SS10
        //this.add(jScrollPane1,  new GridBagConstraints(0, 0, 1, 6, 1.0, 1.0
        this.add(jScrollPane1,  new GridBagConstraints(0, 0, 1, 12, 1.0, 1.0
        //CCEnd SS10
            ,GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 113, 156));
        //CCBegin SS2
//        this.add(editButton,      new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
//                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 10, 0, 8), 0, 0));
        //CCEnd SS2
        this.add(deleteButton,   new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
        this.add(addButton,    new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
        
//        this.add(calculateButton,   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
//            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
        // CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
        
        //CCBegin SS4
        this.add(eqequipLabel,   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(4, 0, 0, 100), 0, 0));
        
        this.add(eqequipButton,   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(4, 0, 0, 10), 0, 0));
        
        this.add(eqequipDecreaseButton,   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(4, 100, 0, 10), 0, 0));
        
        
//        this.add(copyAddButton,     new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
//              ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 8), 0, 0));
        
        
        this.add(toolLabel,     new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(4, 0, 0, 100), 0, 0));
        
        this.add(toolButton,     new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
              ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(4, 0, 0, 8), 0, 0));
        
        this.add(toolDecreaseButton,     new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(4, 100, 0, 8), 0, 0));
        
        
//        this.add(inheritButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
//                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 8), 0, 0));
        
        
        this.add(materialLabel, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(4, 0, 0, 100), 0, 0));
        
        
        this.add(materialButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(4, 0, 0, 8), 0, 0));
        
        
        this.add(materialDecreaseButton, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(4, 100, 0, 8), 0, 0));
        
      //CCEnd SS4

       
      //CCBegin SS10
        this.add(multiLabel, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
        
        this.add(copyMultiButton, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
                
        this.add(cutMultiButton, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
        
        this.add(pasteMultiButton, new GridBagConstraints(1, 9, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
                
        this.add(deleteMultiButton, new GridBagConstraints(1, 10, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
                
        this.add(moveupMultiButton, new GridBagConstraints(1, 11, 1, 1, 0.0, 0.0
                ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(8, 10, 0, 7), 0, 0));
        //CCEnd SS10

            
  // CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
        jScrollPane1.getViewport().add(multiList, null);
    }


    /**
     * �õ��������Ե���������
     * @param conditions������չ�����ܽ��洫�ݹ����ļ���
     * @return HashMap �������ڵĹ��ղ���ʹ����ǰ�Ŀ��Ƽƻ�,����ֻ���ܷ�������ͨ��������˵����
     * ����������ɵ��ַ���Ϊ��,���������ļ���Ϊֵ��HashMap
     */
    public HashMap getCondition(HashMap conditions)
    {
        ExtendAttGroup group;
        Vector vec = new Vector();
        for (int i = 0; i < multiList.getNumberOfRows(); i++)
        {
            group = this.group.duplicate();
            ExtendAttModel model;
            HashMap conMap = new HashMap();
            for (int j = 0; j < this.groupSize; j++)
            {
                model = group.getAttributeAt(j);

                String temp = null;
                if (model.getAttType().equalsIgnoreCase("SpecChar"))
                  {
                      conMap.put(model.getAttName(), (Vector)this.multiList.getSelectedObject(i, j));
                  }
               else{
                   if (model.getAttType().equals("EnumeratedType"))
                   {
                       temp = (String)this.multiList.getSelectedObject(i, j);
                   }
                   else
                   if (model.getAttType().equals("Coding"))
                   {
                       temp = (String)this.multiList.getSelectedObject(i, j);
                   }
                   else
                   if (model.getAttType().equalsIgnoreCase("Boolean"))
                   {
                       if (((Boolean) multiList.getSelectedObject(i, j)).
                           booleanValue())
                       {
                           temp = "true";
                       }
                       else
                       {
                           temp = "false";
                       }
                   }
                   else
                   if (model.getAttType().equalsIgnoreCase("String"))
                   {
                       HashMap map = model.getFeature();
                       String refType = (String) map.get("RefType");
                       if (refType == null)
                       {
                           temp = (String)this.multiList.getSelectedObject(i,
                                   j);
                       }
                       else
                       if (refType.equalsIgnoreCase("EnumerateType"))
                       {
                           temp = (String)this.multiList.getSelectedObject(i,
                                   j);
                       }
                       else
                       if (refType.equalsIgnoreCase("ComboAtts"))
                       {
                           temp = multiList.getCellAt(i, j).getStringValue() +
                                  ";" +
                                  (String) multiList.getCellAt(i, j).getValue();
                       }
                   }

                   boolean check = parentPanel.checkValidity(model, temp,
                           model.getAttDisplay());
                   if (check)
                   {
                       if (temp != null)
                       {
                           if (temp.equals(""))
                           {
                               if (hasDefault)
                               {
                                   temp = model.getAttDefault();
                                   hasDefault = false;
                               }
                               else
                               {
                                   temp = null;
                               }
                           }
                       }
                   }
                   else
                   {
                       return null;
                   }

                   if (temp != null && temp.trim().equals(""))
                   {
                       Object value = change(model, temp);

                       conMap.put(model.getAttName(), value);
                   }
               }
                //  conditions.put("",null);
            }
            if (conMap.size() != 0)
            {
                vec.add(conMap);
            }
        }

        if (this.isControlPlan)
        {
            if (vec.size() > 0)
            {
                conditions.put(ExtendAttContainer.CONTROLPLAN, vec);
            }
        }
        else
        {
            if (vec.size() > 0)
            {
                conditions.put(ExtendAttContainer.NORMALGROUP + ";" +
                               this.group.getGroupName(), vec);
            }
        }
        return conditions;
    }


    /**
     * �õ���������
     * @return����չ������ļ���
     */
    public Vector getExAttr()
    {
        Vector groups = new Vector();
        ExtendAttGroup group;
        for (int i = 0; i < multiList.getNumberOfRows(); i++)
        {
            group = this.group.duplicate();
            boolean flag = false;
            for (int j = 0; j < this.groupSize; j++)
            {
                ExtendAttModel model = group.getAttributeAt(j);
                this.setAtts(model, i, j);
                if (model.getAttValue() != null)
                {
                    flag = true;
                }
            }
            if (flag)
            {
                groups.add(group);
            }
        }
        return groups;
    }


    /**
     * ��Ч�Լ��
     * @return boolean ����ֵ��ʾ������Ŀ���Ƿ���Ч,trueΪ��Ч
     */
    public boolean check()
    {
        ExtendAttGroup group;
        for (int i = 0; i < multiList.getNumberOfRows(); i++)
        {
            group = this.group.duplicate();
            for (int j = 0; j < this.groupSize; j++)
            {
                ExtendAttModel model = group.getAttributeAt(j);
                //System.out.println("model.getAttType()="+model.getAttType());
                String temp = null;
                if (model.getAttType().equals("EnumeratedType"))
                {
                    temp = (String)this.multiList.getSelectedObject(i, j);
                }else
                if (model.getAttType().equals("SpecChar"))
               {
                  continue;
               }
               else

                if (model.getAttType().equals("Coding"))
                {
                    temp = (String)this.multiList.getSelectedObject(i, j);
                    if(!model.getAllowNull()&&temp.equals(" "))
                    {
                          this.parentPanel.handleNull(model.getAttDisplay());
                           return false;
                    }
                }
                else
                if (model.getAttType().equalsIgnoreCase("Boolean"))
                {
                    if (((Boolean) multiList.getSelectedObject(i, j)).
                        booleanValue())
                    {
                        temp = "true";
                    }
                    else
                    {
                        temp = "false";
                    }
                }
                else
                if (model.getAttType().equalsIgnoreCase("String"))
                {
                    HashMap map = model.getFeature();
                    String refType = (String) map.get("RefType");
                    if (refType == null)
                    {
                        temp = (String)this.multiList.getSelectedObject(i, j);
                    }
                    else
                    if (refType.equalsIgnoreCase("EnumerateType"))
                    {
                        temp = (String)this.multiList.getSelectedObject(i, j);
                    }else
                    if (refType.equalsIgnoreCase("ComboAtts"))
                    {
                        temp = multiList.getCellAt(i, j).getStringValue() + ";" +
                               (String) multiList.getCellAt(i, j).getValue();
                    }
                }
                else
                {
                    temp = (String)this.multiList.getSelectedObject(i, j);
                }
                boolean check = parentPanel.checkValidity(model, temp,
                        model.getAttDisplay());
                if (!check)
                {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * ���ø�panel
     * @param panel CappExAttrPanel ��������panel
     */
    public void setParentPanel(CappExAttrPanelForBSX panel)
    {
        this.parentPanel = panel;
    }
    /**
     * ���ö�Ӧ�к��еĳ�������ֵ
     * @param model ExtendAttModel ��������ģ��
     * @param i int �к�
     * @param j int �к�
     * @see ExtendAttModel
     */
    private void setAtts(ExtendAttModel model, int i, int j)
    {
        String temp = null;
        if (model.getAttType().equalsIgnoreCase("SpecChar"))
                {

                     model.setAttValue((Vector)this.multiList.getSelectedObject(i, j));
                }
      else
      {
          if (model.getAttType().equals("EnumeratedType"))
          {
              temp = (String)this.multiList.getSelectedObject(i, j);
          }
          else
          if (model.getAttType().equals("Coding"))
          {
              temp = (String)this.multiList.getSelectedObject(i, j);
          }
          else
          if (model.getAttType().equalsIgnoreCase("Boolean"))
          {
              if (((Boolean) multiList.getSelectedObject(i, j)).booleanValue())
              {
                  temp = "true";
              }
              else
              {
                  temp = "false";
              }
          }
          else
          if (model.getAttType().equalsIgnoreCase("String"))
          {
              HashMap map = model.getFeature();
              String refType = (String) map.get("RefType");
              if (refType == null)
              {
                  temp = (String)this.multiList.getSelectedObject(i, j);
              }
              else
              if (refType.equalsIgnoreCase("EnumerateType"))
              {
                  temp = (String)this.multiList.getSelectedObject(i, j);
              }
              else
              if (refType.equalsIgnoreCase("ComboAtts"))
              {

                  temp = multiList.getCellAt(i, j).getStringValue() +";"+
                         (String) multiList.getCellAt(i, j).getValue();
              }

          }
          else
          {
              temp = (String)this.multiList.getSelectedObject(i, j);
          }
          boolean check = parentPanel.checkValidity(model, temp,
                  model.getAttDisplay());
          if (check)
          {
              if (temp != null)
              {
                  if (temp.equals(""))
                  {
                      if (hasDefault)
                      {
                          temp = model.getAttDefault();
                          hasDefault = false;
                      }
                      else
                      {
                          temp = null;
                      }
                  }
              }
          }
          else
          {
              return;
          }
          if(temp!=null)
          {
              Object value = change(model, temp);
              model.setAttValue(value);
          }
      }
    }


    /**
     * ��ʾ��ǰ����
     * @param info ExtendAttriedIfc������չ���Թ����ֵ����
     * @param groupName ��չ��������
     * @see ExtendAttriedInfo
     */
    public void show(ExtendAttriedIfc info, String groupName)
    {
        ExtendAttContainer container1 = info.getExtendAttributes();
        show(container1,groupName);
//        ExtendAttGroup group;
//        int count;
//        if (isControlPlan)
//        {
//            count = container1.getPlanGroupCount();
//            for (int i = 0; i < count; i++)
//            {
//                group = container1.getPlanGroupAt(i);
//                for (int j = 0; j < group.getCount(); j++)
//                {
//                    showAttsToTable(i, j, group.getAttributeAt(j));
//                }
//
//            }
//        }
//        else
//        {
//            Vector vec = container1.getAttGroups(groupName);
//            if (vec == null)
//            {
//                count = 0;
//            }
//            else
//            {
//                count = vec.size();
//            }
//            for (int i = 0; i < count; i++)
//            {
//                group = (ExtendAttGroup) vec.elementAt(i);
//                for (int j = 0; j < group.getCount(); j++)
//                {
//                    showAttsToTable(i, j, group.getAttributeAt(j));
//                }
//
//            }
//        }

    }

    /**
      * ��ʾ��ǰ����
      * @param info container1����չ��������
      * @param groupName ��չ��������
      * @see ExtendAttContainer
      */
    // 20070205Ѧ�����

     public void show(ExtendAttContainer container1, String groupName)
     {

         //CCBegin by liunan 2011-03-15 �򲹶�v4r3_p028_20110216
         //begin CR5
         //Դ��
         /*ExtendAttGroup group;
         int count;*/
         ExtendAttGroup group1 ;
         //����
         int count;
         //ԭ��������������
         int count1 = 0;
         //xml�ļ��б�ԭ�������ж����������
         int a = 0;
         //end CR5
         //CCEnd by liunan 2011-03-15 
         if (isControlPlan)
         {
             count = container1.getPlanGroupCount();
             for (int i = 0; i < count; i++)
             {
                 group = container1.getPlanGroupAt(i);
                 for (int j = 0; j < group.getCount(); j++)
                 {
                     showAttsToTable(i, j, group.getAttributeAt(j));
                 }

             }
         }
         else
         {
             Vector vec = container1.getAttGroups(groupName);
             if (vec == null)
             {
                 count = 0;
             }
             else
             {
                 count = vec.size();
             }
             //CCBegin by liunan 2011-03-15 �򲹶�v4r3_p028_20110216
             //begin CR5
             //Դ��
             /*for (int i = 0; i < count; i++)
             {
                 group = (ExtendAttGroup) vec.elementAt(i);
                 for (int j = 0; j < group.getCount(); j++)
                 {
                     showAttsToTable(i, j, group.getAttributeAt(j));
                 }

             }*/
             ArrayList list = new ArrayList();
             for (int i = 0; i < count; i++)
             {
                 group1 = (ExtendAttGroup) vec.elementAt(i);
                 count1 = group1.getCount();
                 //���xml�е���������ԭ�������չ���ԣ���ֱ�ӽ�ԭ�����е�����ֵ���õ��б���
                 if (groupSize <= count1)
                 {
                 	   for (int j = 0; j < groupSize; j++)
                 	   {
                 	   	   showAttsToTable(i, j, group1.getAttributeAt(j));
                 	   }
                 }
                 else
                 {
                	 for (int j = 0; j < count1; j++)
                     {
                    	 ExtendAttModel model1 = group1.getAttributeAt(j);
                    	 //��ԭ�����е��������õ��б���
                         showAttsToTable(i, j, group1.getAttributeAt(j));
                         //��ԭ�����е���չ������ʾ���ŵ�������
                         list.add(model1.getAttDisplay());     
                     }
                 }
             }
             String head[] = new String[groupSize];
             //��xml�е�������ʾ���ŵ����鼯����
             for (int i = 0; i < groupSize; i++)
             {
             	   head[i] = group.getAttributeAt(i).getAttDisplay();
             }
             //���xml�����õ����Զ���ԭ������չ���ԣ������ڵ�����ֵ�ÿ��ַ�����ʾ
             if(groupSize > count1)
             {
             	   a = groupSize - count1;
             	   for (int i = 0; i < count; i++)
             	   {
             	   	   for (int j = 0, k = groupSize; j < k; j++)
             	   	   {
             	   	   	   //��xml�ж��ڵ�����ֵ�ÿ��ַ�����ʾ
             	   	   	   if (!list.contains(head[j]))
             	   	   	   {
             	   	   	   	   for (int m = 0; m < a; m++)
             	   	   	   	   {
             	   	   	   	   	   multiList.addTextCell(i, count1 + m, "");
             	   	   	   	   }
             	   	   	   }
             	   	   }
             	   }
             }
             //end CR5
             //CCEnd by liunan 2011-03-15 
         }

     }

    /**
     * �Ѷ����һ����������ģ����ʾ��ָ��������
     * @param i ��
     * @param j ��
     * @param model ExtendAttModel ��������ģ��
     * @see ExtendAttModel
     */
    private void showAttsToTable(int i, int j, ExtendAttModel model)
    {
        Object obj1 = model.getAttValue();
        if (model.getAttType().equals("SpecChar"))
         {

             if (obj1 != null)
             {

                 multiList.addSpeCharCell(i, j, (Vector)obj1);
             }
             else
             {
                 multiList.addSpeCharCell(i, j, new Vector());
             }

         }

       else
       if (model.getAttType().equalsIgnoreCase("EnumeratedType")) {
         EnumeratedType et1 = (EnumeratedType) obj1;
         if (et1 == null) {
           HashMap map = model.getFeature();
           String classPath = (String) map.get("classpath");
           String newString = (String) map.get("newMethod");
           try {
             Class class1 = Class.forName(classPath);
             Method newMethod = class1.getMethod(newString, null);
             et1 = (EnumeratedType) newMethod.invoke(class1, null);
           }
           catch (Exception e) {
                 if(verbose)
             e.printStackTrace();
             JOptionPane.showMessageDialog(getParentJFrame(), e.getMessage());
           }
         }
         EnumeratedType[] ets = et1.getValueSet();
         String value = et1.getDisplay(local);
         Vector values = new Vector();
         for (int k = 0; k < ets.length; k++) {
           values.add(ets[k].getDisplay(local));
         }
         this.multiList.addComboBoxCell(i, j, value, values);
       }
       else
       if (model.getAttType().equalsIgnoreCase("Coding")) {
    	 
         String et1 = (String) obj1;
         HashMap map = model.getFeature();
         String sorttype = (String) map.get("SortType");
         Collection sorts = (Collection)this.etMaps.get(sorttype);
         Iterator ite = sorts.iterator();
         Vector vec = new Vector();
          CodingIfc et2 = null;
          String cont = "";
          vec.add(cont);
         while (ite.hasNext()) {
           et2 = (CodingInfo) ite.next();
            if(et2.getCodeContent().equals(et1))
           {
             cont = et2.getCodeContent();
           }
           vec.add( et2.getCodeContent());
         }
//CCBegin SS6
         if (et1 == null) {
//CCEnd SS6
           this.multiList.addComboBoxCell(i, j, "", vec);
         }
         else {
//           this.multiList.addComboBoxCell(i, j,
//                                          ( (CodingIfc) vec.elementAt(0)).
//                                          getCodeContent(), vec);
        	 //CCBegin SS6
//        	  CCBeginby leixiao 2009-7-8 ԭ������������       	 
           this.multiList.addComboBoxCell(i, j,
        		   cont, vec);
//         CCEndby leixiao 2009-7-8 ԭ������������   
           //CCEnd SS6
         }
       }
       else if (model.getAttType().equalsIgnoreCase("String")) {
         HashMap map = model.getFeature();
         String refType = (String) map.get("RefType");
         if (refType == null) {
           if (obj1 != null) {
             multiList.addTextCell(i, j, obj1.toString());
           }
           else {
             multiList.addTextCell(i, j, "");
           }
         }
         else
         if (refType.equals("EnumerateType")) {
           String classPath = (String) map.get("classpath");
           EnumeratedType[] ets = (EnumeratedType[]) etMaps.get(classPath);
           EnumeratedType et;
           Vector vec = new Vector();
           for (int k = 0; k < ets.length; k++) {
             et = (EnumeratedType) ets[k];
             vec.add(et.getDisplay(local));
           }
           if (obj1 != null) {
             this.multiList.addComboBoxCell(i, j, obj1.toString(), vec);
           }
           else {
             this.multiList.addComboBoxCell(i, j, "", vec);
           }
         }
         else
         if (refType.equals("ComboAtts")) {

           String sorttype = (String) map.get("SortType");
           Collection sorts = (Collection)this.etMaps.get(sorttype);
           String valueStr = "";
           String sortStr = "";
           if (obj1 != null) {
             StringTokenizer ston = new StringTokenizer( (String) obj1,
                 ";");
             if (ston.hasMoreTokens()) {
               valueStr = ston.nextToken();
             }
             if (ston.hasMoreTokens()) {
               sortStr = ston.nextToken();
             }
           }
           Iterator ite = sorts.iterator();
           Vector vec = new Vector();
           while (ite.hasNext()) {
             vec.add( ( (CodingInfo) ite.next()).getCodeContent());
           }
           this.multiList.addComboBoxCell(i, j, valueStr, sortStr, vec);
         }

       }
       else
       if (model.getAttType().equalsIgnoreCase("Boolean")) {
         boolean flag = true;
         if (rds != null) {
           for (int len = 0; len < rds.length; len++) {

             if (j == rds[len]) {
               flag = false;

             }
           }
         }
         if (obj1 != null) {
           if (flag) {
             multiList.addCheckboxCell(i, j,
                                       ( (Boolean) obj1).booleanValue());
           }
           else {
             multiList.addRadioButtonCell(i, j,
                                          ( (Boolean) obj1).booleanValue());
           }
         }
         else {
           multiList.addCheckboxCell(i, j, false);
         }
       }
       else {
         if (obj1 != null) {
           multiList.addTextCell(i, j, obj1.toString());
         }
         else {
           multiList.addTextCell(i, j, "");
         }
       }

    }


    /**
     * ������һ���ǵ�ѡ��ť1
     * @param cols��Ҫ���õ��кż���
     */
    public void setRadioButtonCols(int[] cols)
    {
        this.rds = cols;
    }


    /**
     * ���ñ����еľ��Կ��
     */
    public void setMulistAbsWidths()
    {
        int i = multiList.getNumberOfCols();
        String[] cols = new String[i];
        for (int j = 0; j < i; j++)
        {
            cols[j] = "80";
        }
        multiList.setColumnSizes(cols);
    }


    /**
     * Ӧ��Ĭ���п�
     * @throws PropertyVetoException
     */
    protected void defaultColumnWidths() //throws PropertyVetoException
    {
        int i = multiList.getNumberOfCols();
        int j = i - 1;
        int as[] = new int[i];
        for (int k = 0; k < j; k++)
        {
        	
               as[k] = 1;
        }
        as[j] = 1;
        multiList.setRelColWidth(as);

    }


    /**
     * ��Ӱ�ťִ��
     * @param e����ť�¼�
     */
//    void addButton_actionPerformed(ActionEvent e)
//    {  
//        int row = this.multiList.getNumberOfRows();
//        for (int i = 0; i < group.getCount(); i++)
//        {
//            addDefalutAttsToTable(row, i, group.getAttributeAt(i));
//        }
//        this.attrButton.setEnabled(true);
//
//    }
    //CCBegin by liuzhicheng 2010-01-21 ԭ�򣺡���ӡ���ť��ִ�з�ʽ����Ϊѡ��ĳ�����ʱ��˳����ӵ���һ�С�
    void addButton_actionPerformed(ActionEvent e)
    {
        //�޸ġ���ӡ���ť��ִ�з�ʽ����Ϊѡ��ĳ�����ʱ��˳����ӵ���һ�С�
        int row = this.multiList.getNumberOfRows();
        int row1 = this.multiList.getSelectedRow();
        if (row1 < 0)
        {
            for (int i = 0; i < group.getCount(); i++)
            {
                addDefalutAttsToTable(row, i, group.getAttributeAt(i));
                multiList.setSelectedRow(row);
            }
        }
        else
        {
            for (int i = 0; i < group.getCount(); i++)
            {
                addDefalutAttsToTable(row, i, group.getAttributeAt(i));
            }
            for (int i = row - 1; i > row1; i--)
            {
                multiList.moveDown(i);
            }
//            if (groupname.equalsIgnoreCase("���Կ���"))
//            {
//                for (int i = 0; i < multiList.getNumberOfRows(); i++)
//                {
//                	  multiList.addTextCell(i, 0, (new Integer(i + 1)).toString());
//                }
//            }
            multiList.setSelectedRow(row1 + 1);
        }  

    }
    //CCEnd by liuzhicheng 2010-01-21 ԭ�򣺡���ӡ���ť��ִ�з�ʽ����Ϊѡ��ĳ�����ʱ��˳����ӵ���һ�С�

    
    /**  
     * ����Ĭ��ֵ������
     * @param i���к�
     * @param j���к�
     * @param model��ExtendAttModel�����Է�װ��
     * @see ExtendAttModel
     */
    private void addDefalutAttsToTable(int i, int j, ExtendAttModel model)
    {

        Object obj1 = model.getAttDefault();
        if (model.getAttType().equalsIgnoreCase("EnumeratedType"))
        {
            HashMap map = model.getFeature();
            String classPath = (String) map.get("classpath");
            String newString = (String) map.get("newMethod");
            try
            {
                EnumeratedType[] ets = (EnumeratedType[])this.etMaps.get(
                        classPath);
                String value = "";
                Vector values = new Vector();
                for (int k = 0; k < ets.length; k++)
                {
                    values.add(ets[k].getDisplay(local));
                }
                multiList.addComboBoxCell(i, j, value, values);
            }
            catch (Exception e)
            {
                  if(verbose)
                e.printStackTrace();
                JOptionPane.showMessageDialog(getParentJFrame(), e.getLocalizedMessage());
            }
        }
        else if (model.getAttType().equals("SpecChar"))
            {
                if (obj1 != null)
                {

                    multiList.addSpeCharCell(i, j, new Vector());
                }
                else
                {
                    multiList.addSpeCharCell(i, j, new Vector());
                }

            }

        else
        if (model.getAttType().equalsIgnoreCase("Coding"))
        {
            HashMap map = model.getFeature();
            String defaultValue = model.getAttDefault();
            boolean isValid = false;
            String sorttype = (String) map.get("SortType");
            Collection sorts = (Collection)this.etMaps.get(sorttype);
            Iterator ite = sorts.iterator();
            Vector vec = new Vector();
            //CCBegin SS9
            vec.add("");
            //CCEnd SS9
            while (ite.hasNext())
            {
                CodingInfo info = (CodingInfo)ite.next();
                //CCBegin SS9
                //CCBegin by liunan 2009-09-14
//                if(info.getCodeContent().equalsIgnoreCase("C"))
//                  vec.add(0,info.getCodeContent());
//                else
                //CCEnd by liunan
                //CCEnd SS9
                vec.add(info.getCodeContent());
                if(info.getCodeContent().equals(defaultValue))
                  isValid = true;
            }
           if(isValid&&defaultValue!=null&&!defaultValue.equalsIgnoreCase(""))
                this.multiList.addComboBoxCell(i, j,
                                          defaultValue,
                                          vec);
           else
             //20070302xuejing modify
               this.multiList.addComboBoxCell(i, j,
                                           (String) vec.elementAt(0),
                                           vec);
        }
        else
        if (model.getAttType().equalsIgnoreCase("String"))
        {
            HashMap map = model.getFeature();
            String refType = (String) map.get("RefType");
            if (refType == null)
            {
                if (obj1 != null)
                {
                    multiList.addTextCell(i, j, obj1.toString());
                }
                else
                {
                    multiList.addTextCell(i, j, "");
                }
            }
            else
            if (refType.equals("EnumerateType"))
            {
                String classPath = (String) map.get("classpath");
                EnumeratedType[] ets = (EnumeratedType[]) etMaps.get(classPath);
                EnumeratedType et;
                Vector vec = new Vector();
                for (int k = 0; k < ets.length; k++)
                {
                    et = (EnumeratedType) ets[k];
                    vec.add(et.getDisplay(local));
                }
                if (obj1 != null)
                {
                    this.multiList.addComboBoxCell(i, j, obj1.toString(), vec);
                }
                else
                {
                    this.multiList.addComboBoxCell(i, j, "", vec);
                }
            }
            else
            if (refType.equals("ComboAtts"))
            {

                String sorttype = (String) map.get("SortType");
                Collection sorts = (Collection)this.etMaps.get(sorttype);
                String valueStr = "";
                String sortStr = "";
                if (obj1 != null)
                {
                    StringTokenizer ston = new StringTokenizer((String) obj1,
                            ";");
                    
                    if (ston.hasMoreTokens())
                    {
                        valueStr = ston.nextToken();
                    }
                    if (ston.hasMoreTokens())
                    {
                        sortStr = ston.nextToken();
                    }
                }
                Iterator ite = sorts.iterator();
                Vector vec = new Vector();
                while (ite.hasNext())
                {
                    vec.add(((CodingInfo) ite.next()).getCodeContent());
                }
                //begin CR2
                if(sortStr.equals(""))
                {
                	sortStr = (String)vec.elementAt(0);
                }//end CR2
                this.multiList.addComboBoxCell(i, j, valueStr, sortStr, vec);
            }

        }
        else
        if (model.getAttType().equalsIgnoreCase("Boolean"))
        {
            boolean flag = true;
            if (rds != null)
            {
                for (int len = 0; len < rds.length; len++)
                {

                    if (j == rds[len])
                    {
                        flag = false;

                    }
                }
            }
            if (obj1 != null)
            {
                if (flag)
                {
                    multiList.addCheckboxCell(i, j,
                                              (new Boolean(obj1.toString())).booleanValue());
                }
                else
                {
                    multiList.addRadioButtonCell(i, j,
                                                 (new Boolean(obj1.toString())).
                                                 booleanValue());
                }
            }
            else
            {
                multiList.addCheckboxCell(i, j, false);
            }
        }
        else
        {
            if (obj1 != null)
            {
                multiList.addTextCell(i, j, obj1.toString());
            }
            else
            {
                multiList.addTextCell(i, j, "");
            }
        }
    }
    /**
     * ɾ����ťִ���¼�
     * @param e ActionEvent ��ť�����¼�
     */
    void deleteButton_actionPerformed(ActionEvent e)
    {
    	
    	
    	Hashtable eqMap=new Hashtable();
    	Hashtable materMap=new Hashtable();
    	Hashtable toolMap=new Hashtable();
    	
    	
      int row = this.multiList.getSelectedRow();
      if (row != -1){
    	  
    	  String eqID=multiList.getCellText(row, 20);
    	  String  materID=multiList.getCellText(row, 21);
    	  String toolID=multiList.getCellText(row, 22);
    	  String LJtoolID=multiList.getCellText(row, 23);
    	  String count=multiList.getCellText(row, 5);
    	  
    	  
    	  if(eqID!=null&&!eqID.equals("")){
    		  
    		  if(count.indexOf("(")!=-1||count.indexOf("��")!=-1){
        		  
    			  int rCount=Integer.parseInt(count);
    			  
    			  if(!eqMap.containsKey(eqID))
    			     eqMap.put(eqID, rCount);
    			  else{
    				  int oldCount=(Integer)eqMap.get(eqID);
    				  rCount+=oldCount;
    				  eqMap.put(eqID, rCount);
    			  }
        		  
        	  }else{
        		  
        		  eqMap.put(eqID, 1);
        	  }
    		  
    		  eqDeleteVec.add(eqMap);
    		  
    	  }
    	  if(materID!=null&&!materID.equals("")){
    		  
    		  
          if(count.indexOf("(")!=-1||count.indexOf("��")!=-1){
        		  
    			  int rCount=Integer.parseInt(count);
    			  
    			  if(!materMap.containsKey(materID))
    				  materMap.put(eqID, rCount);
    			  else{
    				  int oldCount=(Integer)materMap.get(materID);
    				  rCount+=oldCount;
    				  materMap.put(materID, rCount);
    			  }
        		  
        	  }else{
        		  
        		  materMap.put(materID, 1);
        	  }
    		  
    		  
    		  materDeleteVec.add(materMap);
    		  
    	  }
    	  if(toolID!=null&&!toolID.equals("")){
    		  
    		  //CCBegin SS5
    		  
//             if(count.indexOf("(")!=-1||count.indexOf("��")!=-1){
//        		  
//    			  int rCount=Integer.parseInt(count);
//    			  
//    			  if(!toolMap.containsKey(toolID))
//    				  toolMap.put(toolID, rCount);
//    			  else{
//    				  int oldCount=(Integer)toolMap.get(toolID);
//    				  rCount+=oldCount;
//    				  toolMap.put(toolID, rCount);
//    			  }
//        		  
//        	  }else{
//        		  
//        		  toolMap.put(toolID, 1);
//        	  }
    		  
    		  
    		  if(count.indexOf("(")!=-1||count.indexOf("��")!=-1||count.equals("")){
        		  
	    			 
    			  
    			  toolMap.put(toolID, 0);
        		  
        	  }else{
        		  
                 int rCount=Integer.parseInt(count);
    			  
    			  if(!toolMap.containsKey(toolID))
    				  toolMap.put(toolID, rCount);
    			  else{
    				  int oldCount=(Integer)toolMap.get(toolID);
    				  rCount+=oldCount;
    				  toolMap.put(toolID, rCount);
    			  }
        	  }
    		  //CCEnd SS5
    		  
    		  
    		  toolDeleteVec.add(toolMap);
    	  }
    	  if(LJtoolID!=null&&!LJtoolID.equals("")){
    		  System.out.println("LJtoolID===============**************************==================="+LJtoolID);
    		  toolMap.put(LJtoolID, 1);
    	  }
    	  
    	  
    	  
        multiList.removeRow(row);
        
      }
      if (row > 0) {
        this.multiList.selectRow(row - 1);
      }
      else
      if (this.multiList.getTable().getRowCount() > 0) {
        this.multiList.selectRow(row);
      }
    }


    /**
     * ���ַ�ֵתΪ�����������Ͷ�Ӧ��ֵ
     * @param model�����Է�װ��
     * @param text�����Ե��ַ���ֵ
     * @return Object �ַ�ֵתΪ�ĸ����������Ͷ�Ӧ��ֵ
     * @see ExtendAttModel
     */
    Object change(ExtendAttModel model, String text)
    {
        String rstcType = model.getAttType();
        String defName = model.getAttDisplay();
        HashMap map = model.getFeature();
        if (rstcType.equalsIgnoreCase("EnumeratedType"))
        {
            String classPath = (String) map.get("classpath");
            EnumeratedType[] ets = (EnumeratedType[]) etMaps.get(classPath);
            EnumeratedType et;
            for (int i = 0; i < ets.length; i++)
            {
                et = (EnumeratedType) ets[i];
                if (et.getDisplay(local).equals(text))
                {
                    return et;
                }
            }
        }
        else
        if (rstcType.equalsIgnoreCase("Coding"))
        {
//            String sortType = (String) map.get("SortType");
//            Collection col = (Collection) etMaps.get(sortType);
//            Iterator i = col.iterator();
//            CodingIfc et;
//            while (i.hasNext())
//            {
//                et = (CodingIfc) i.next();
//                if (et.getCodeContent().equals(text))
//                {
//                    return et.getBsoID();
//                }
//            }
          return text;
        }
        else
        if (rstcType.equalsIgnoreCase("int"))
        {
            int textVal = 0;
            textVal = Integer.parseInt(text);
            return new Integer(textVal);
        }
        else
        if (rstcType.equalsIgnoreCase("double"))
        {
            double doubleVal = 0;
            doubleVal = Double.parseDouble(text);
            return new Double(doubleVal);
        }
        else
        if (rstcType.equalsIgnoreCase("float"))
        {
            float floatVal = 0;
            floatVal = Float.parseFloat(text);
            return new Float(floatVal);
        }
        else if (rstcType.equalsIgnoreCase("long"))
        {
            long longVal = 0;
            longVal = Long.parseLong(text);
            return new Long(longVal);
        }
        else if (rstcType.equalsIgnoreCase("string"))
        {
            return text;
        }
        else if (rstcType.equalsIgnoreCase("boolean"))
        {
            if (text.equalsIgnoreCase("true"))
            {
                return new Boolean(true);
            }
            else
            {
                return new Boolean(false);
            }

        }
        return null;
    }


    /**
     * ���ù���ģʽ�������֡����༭���鿴������
     * @param model int
     */
    public void setModel(int model)
    {
        if (model == CappExAttrPanel.VIEW_MODEL)
        {
            this.multiList.setCellEditable(false);
            this.addButton.setEnabled(false);
            this.deleteButton.setEnabled(false);
            this.calculateButton.setEnabled(false);
            this.editButton.setEnabled(false);
//  		CCBegin by liuzc 2009-12-14 ԭ�򣺽������ 
            this.inheritButton.setEnabled(false);
            this.copyAddButton.setEnabled(false);
//  		CCBegin by liuzc 2009-12-14 ԭ�򣺽������    
            
            
            this.eqequipButton.setEnabled(false);
            this.eqequipDecreaseButton.setEnabled(false);
            this.toolButton.setEnabled(false);
            this.toolDecreaseButton.setEnabled(false);
            this.materialButton.setEnabled(false);
            this.materialDecreaseButton.setEnabled(false);
            copyMultiButton.setEnabled(false);
            cutMultiButton.setEnabled(false);
            pasteMultiButton.setEnabled(false);
            deleteMultiButton.setEnabled(false);
            moveupMultiButton.setEnabled(false);
            
            //CCBegin SS24
            multiList.setColsEnabled(new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21}, false);
            //CCEnd SS24
        }
        else
        {
            this.multiList.setCellEditable(true);
            this.addButton.setEnabled(true);
            this.deleteButton.setEnabled(true);
            this.calculateButton.setEnabled(true);   
            this.editButton.setEnabled(true);
//  		CCBegin by liuzc 2009-12-14 ԭ�򣺽������ 
            this.inheritButton.setEnabled(true);
            this.copyAddButton.setEnabled(true);
//  		CCBegin by liuzc 2009-12-14 ԭ�򣺽������ 
            
            
            this.eqequipButton.setEnabled(true);
            this.eqequipDecreaseButton.setEnabled(true);
            this.toolButton.setEnabled(true);
            this.toolDecreaseButton.setEnabled(true);
            this.materialButton.setEnabled(true);
            this.materialDecreaseButton.setEnabled(true);
            copyMultiButton.setEnabled(true);
            cutMultiButton.setEnabled(true);
            pasteMultiButton.setEnabled(true);
            deleteMultiButton.setEnabled(true);
            moveupMultiButton.setEnabled(true);
            
            
    }
    }


    /**
     * ����ϼ�frame
     * @return JFrame �ϼ�frame
     */
    public JFrame getParentJFrame()
    {
    	Component parent = getParent();

        while (!(parent instanceof JFrame))
        {
            parent = parent.getParent();
        }
        return (JFrame) parent;
    }

    /**
     * ������Ӱ�ť����
     * @param i int ��ť����
     */
    public void setAddButtonSize(int i)
     {
        addButton.setMaximumSize(new Dimension(i, 23));
        addButton.setMinimumSize(new Dimension(i, 23));
        addButton.setPreferredSize(new Dimension(i, 23));
       // addButton.setMnemonic('A');
        //addButton.setText("���(A)");
     }

     /**
      * ����ɾ����ť����
      * @param i int ��ť����
      */
     public void setDelButtonSize(int i)
  {
     deleteButton.setMaximumSize(new Dimension(i, 23));
     deleteButton.setMinimumSize(new Dimension(i, 23));
     deleteButton.setPreferredSize(new Dimension(i, 23));
    // addButton.setMnemonic('A');
     //addButton.setText("���(A)");
  }

  /**
   * ������Ӱ�ť�����Ƿ�
   * @param mn char ���Ƿ���
   */
  public void setAddButtonMnemonic(char mn)
  {
      this.addButton.setMnemonic(mn);
      this.addButton.setText("���"+"("+mn+")");
  }

  /**
  * ����ɾ����ť�����Ƿ�
  * @param mn char ���Ƿ���
  */
  public void setDelButtonMnemonic(char mn)
  {
      this.deleteButton.setMnemonic(mn);
      this.deleteButton.setText("ɾ��"+"("+mn+")");
  }


  /**
   * ���ư�ťִ���¼�
   * @param e ActionEvent ��ť�����¼�
   */
  void upButton_actionPerformed(ActionEvent e) {
       if( multiList.getTable().isEditing())
       multiList.getTable().getCellEditor().stopCellEditing();
        int selectRow = multiList.getSelectedRow();
        if(selectRow==0||selectRow==-1)
        {
          return;
        }
        else
        {
            int uprow=selectRow-1;
            int colums=multiList.getTable().getColumnCount();
            //�����һ�е�����
            CappTextAndImageCell tempobj;
            for(int i=0;i<colums;i++)
            {
               //������һ�е�i�е�����
               tempobj=multiList.getCellAt(uprow,i);
               //��ѡ���е�����Ų����һ��

               multiList.addCell(uprow,i,multiList.getCellAt(selectRow,i));
               //����������ݷ���ѡ����
               multiList.addCell(selectRow,i,tempobj);
            }
           multiList.setSelectedRow(uprow);
         }


  }


  /**
   * ���ư�ť�¼�ִ��
   * @param e ActionEvent ��ť�����¼�
   */
  void downButton_actionPerformed(ActionEvent e) {
    if( multiList.getTable().isEditing())
       multiList.getTable().getCellEditor().stopCellEditing();
    int selectRow = multiList.getSelectedRow();
    if (selectRow == (multiList.getRowCount() - 1) || selectRow == -1) {
      return;
    }
    else {
      int downrow = selectRow + 1;
      int colums = multiList.getTable().getColumnCount();
      //�����һ�е�����
      CappTextAndImageCell tempobj;
      for (int i = 0; i < colums; i++) {
        //������һ�е�i�е�����
        tempobj = multiList.getCellAt(downrow, i);
        //��ѡ���е�����Ų����һ��
        multiList.addCell(downrow, i, multiList.getCellAt(selectRow, i));
        //����������ݷ���ѡ����
        multiList.addCell(selectRow, i, tempobj);
      }
      multiList.setSelectedRow(downrow);
    }

  }


  /**
   * ���㰴ť�¼�ִ��
   * @param e ActionEvent ��ť�����¼�
   */
  void calculateButton_actionPerformed(ActionEvent e) {

    calculate();
  }
  /**
   * �豸
   * @param e
   */
  void eqequipButton_actionPerformed(ActionEvent e){
	  
	  try {
		  
		launchChooserDialog("QMEquipment");
		
	} catch (QMRemoteException e1) {
		e1.printStackTrace();
	}
  }
  
  //CCBegin SS4
  /**
   * 
   * ����豸
   */
  void eqequipDecreaseButton_actionPerformed(ActionEvent e){
	  

		  int selectRow=this.multiList.getSelectedRow();
		  int rowCount=this.multiList.getRowCount();
		  //20140616
		  int eqInfoRow=4;
		//20140616 end
		  
		  if(selectRow<0){
			  
			  JOptionPane.showMessageDialog(getParentJFrame(),
						"��ѡ��Ҫ��ղ�ɾ�����豸!", "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);

				return;
			  
		  }else{
			  
			  String eqBsoid=multiList.getCellText(selectRow, 20);
			  
			  
			  
			  //20140616
			  if(eqBsoid!=null&&!eqBsoid.equals("")){
			      
//				  for(int i=selectRow+1;i<rowCount;i++){
//					  
//					  String eqInfo=multiList.getCellText(i, 20);
//					  
//					  String meaBsoid=multiList.getCellText(i, 21);
//					  if((eqInfo==null||eqInfo.equals(""))&&(meaBsoid==null||meaBsoid.equals(""))){
//						  
//						  eqInfoRow++;
//					  }else{
//						  
//						  break;
//					  }
//					  
//					  
//				  }
                //20140616 end
				  
				  if(eqInfoRow!=0){
					  
					  
			           for(int j=0;j<eqInfoRow;j++ ){
				 
			        	   multiList.addTextCell(selectRow+j, 1, "");
			        	   multiList.addTextCell(selectRow+j, 20, "");
			            }
			           
			           Hashtable eqMap=new Hashtable();
			           
						  if(!eqMap.containsKey(eqBsoid))
			    			     eqMap.put(eqBsoid, 1);
						  
					   eqDeleteVec.add(eqMap);
			  }
				  
				  
				  
			  }else{
				  
				  JOptionPane.showMessageDialog(getParentJFrame(),
							"��ѡ���豸�����!", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);

					return;
				  
			  }
			  
			  
		  }
		  
		  
  }
  
  
  /**
   * 
   * ����豸�����
   */
  void equipAndMaterDecrease(int selectrow) {

		String eqBsoid = multiList.getCellText(selectrow, 20);
		String materBsoid = multiList.getCellText(selectrow, 21);
		if (eqBsoid != null || !eqBsoid.equals("")) {

			int rowCount = this.multiList.getRowCount();

			int eqInfoRow = 0;

			for (int i = selectrow + 1; i < rowCount; i++) {

				String eqInfo = multiList.getCellText(i, 20);

				String meaBsoid = multiList.getCellText(i, 21);
				if ((eqInfo == null || eqInfo.equals(""))
						&& (meaBsoid == null || meaBsoid.equals(""))) {

					eqInfoRow++;
				} else {

					break;
				}

			}

			if (eqInfoRow != 0) {

				for (int j = 0; j <= eqInfoRow; j++) {

					multiList.addTextCell(selectrow + j, 1, "");
					multiList.addTextCell(selectrow + j, 20, "");
				}

				Hashtable eqMap = new Hashtable();

				if (!eqMap.containsKey(eqBsoid))
					eqMap.put(eqBsoid, 1);

				eqDeleteVec.add(eqMap);
			}

		} 
		if (materBsoid != null || !materBsoid.equals("")) {

			multiList.addTextCell(selectrow, 1, "");
			multiList.addTextCell(selectrow, 21, "");

			Hashtable materMap = new Hashtable();

			if (!materMap.containsKey(materBsoid))
				materMap.put(materBsoid, 1);

			materDeleteVec.add(materMap);

		}

	}
  
  /**
   * ��չ�װ
   * @param editRow
   * @param editCol
   */
  private void toolDecrease(int editRow, int editCol) {

		if (editCol == 3) {
			
			String toolBsoid = multiList.getCellText(editRow, 22);
			String count = multiList.getCellText(editRow, 5);
			System.out.println("tooldecrease   toolBsoid=="+toolBsoid);
			if(toolBsoid!=null&&!toolBsoid.equals("")){
				
				multiList.addTextCell(editRow, 2, "");
				multiList.addTextCell(editRow, 3, "");
				multiList.addTextCell(editRow, 4, "");
				//CCBegin SS23
				multiList.addTextCell(editRow, 5, "");
				//CCEnd SS23
				multiList.addTextCell(editRow, 22, "");
				
				Hashtable toolMap = new Hashtable();

				if (!toolMap.containsKey(toolBsoid))
					//CCBegin SS15
					toolMap.put(toolBsoid, Integer.parseInt(count));
				//CCEnd SS15

				toolDeleteVec.add(toolMap);
				
			}
			
			

		}
		if (editCol == 14) {

			int toolInfoRow = 2;
			int rowCount = this.multiList.getRowCount();

			String toolBsoid = multiList.getCellText(editRow, 23);

			System.out.println("tooldecrease   toolBsoid=="+toolBsoid);
			if (toolBsoid != null && !toolBsoid.equals("")) {
//201406161
//				for (int i = editRow + 1; i < rowCount; i++) {
//
//					String toolInfo = multiList.getCellText(i, 23);
//
//					if (toolInfo == null || toolInfo.equals("")) {
//
//						toolInfoRow++;
//					} else {
//
//						break;
//					}
//
//				}
			  //201406161
				if (toolInfoRow != 0) {

					for (int j = 0; j <= toolInfoRow; j++) {

						multiList.addTextCell(editRow + j, 14, "");
						multiList.addTextCell(editRow + j, 23, "");
					}

					Hashtable toolMap = new Hashtable();

					if (!toolMap.containsKey(toolBsoid))
						toolMap.put(toolBsoid, 1);

					toolDeleteVec.add(toolMap);
				}

			}

		}

	}
  
  /**
	 * ��չ�װ
	 */
void toolDecreaseButton_actionPerformed(ActionEvent e){
	 
	 
	 int selectRow=this.multiList.getSelectedRow();
	  int rowCount=this.multiList.getRowCount();
	  //20140616
//	  int toolInfoRow=0;
	  int toolInfoRow=3;
	//20140616 end
	  if(selectRow<0){
		  
		  JOptionPane.showMessageDialog(getParentJFrame(),
					"��ѡ������ߡ���װ�����!", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);

			return;
		  
	  }else{
		  
		  String toolBsoid=multiList.getCellText(selectRow, 23);
		  
		  
		  if(toolBsoid!=null&&!toolBsoid.equals("")){
			  
		    //20140616
//			  for(int i=selectRow+1;i<rowCount;i++){
//				  
//				  String toolInfo=multiList.getCellText(i, 23);
//				  
//				  if(toolInfo==null||toolInfo.equals("")){
//					  
//					  toolInfoRow++;
//				  }else{
//					  
//					  break;
//				  }
//				  
//				  
//			  }
		    //20140616 end
			  if(toolInfoRow!=0){
				  
				  
		           for(int j=0;j<toolInfoRow;j++ ){
			 
		        	   multiList.addTextCell(selectRow+j, 14, "");
		        	   multiList.addTextCell(selectRow+j, 23, "");
		            }
		           
		           Hashtable toolMap=new Hashtable();
		           
					  if(!toolMap.containsKey(toolBsoid))
						  toolMap.put(toolBsoid, 1);
					  
					  toolDeleteVec.add(toolMap);
		  }
			  
			  
			  
		  }else{
			  
			  JOptionPane.showMessageDialog(getParentJFrame(),
						"��ѡ������ߡ���װ�����!", "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);

				return;
			  
		  }
		  
		  
	  }
	 
	  
	  
	  
}
  
 /**
  * ��ղ���
  */
 void materialDecreaseButton_actionPerformed(ActionEvent e) {

		int selectRow = this.multiList.getSelectedRow();

		if (selectRow < 0) {

			JOptionPane.showMessageDialog(getParentJFrame(),
					"��ѡ��Ҫ��ղ�ɾ���Ĳ���������!", "��ʾ", JOptionPane.INFORMATION_MESSAGE);

			return;

		} else {

			String materBsoid = multiList.getCellText(selectRow, 21);

			if (materBsoid != null && !materBsoid.equals("")) {

				multiList.addTextCell(selectRow, 1, "");
				multiList.addTextCell(selectRow, 21, "");

				Hashtable materMap = new Hashtable();

				if (!materMap.containsKey(materBsoid))
					materMap.put(materBsoid, 1);

				materDeleteVec.add(materMap);

			} else {

				JOptionPane.showMessageDialog(getParentJFrame(),
						"��ѡ��Ҫ��ղ�ɾ���Ĳ���������!", "��ʾ",
						JOptionPane.INFORMATION_MESSAGE);

				return;

			}

		}

	}
 
 
 
  // CCEnd SS4
  
  
 
// CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ����
  /**
	 * ʵ�ֹ������̵����Ƽƻ������ݴ���
	 * 
	 * @param e
	 *            ActionEvent
	 */
  void inheritButton_actionPerformed(ActionEvent e) {
	    if (groupname.equals("���Կ���")) {
	        QMProcedureInfo info = container.getProIfc();
//          CCBegin by liuzc 2009-12-31 ԭ�򣺹�����չ���Դ��ݹ��ܴ��󣬽����򴫸����� �μ�TD=2758
	    	if(info == null || info.getProcessControl() == null)
	    	{
	    		return;    
	    	}
//          CCEnd by liuzc 2009-12-31 ԭ�򣺹�����չ���Դ��ݹ��ܴ��󣬽����򴫸����� �μ�TD=2758
	        ExtendAttContainer con = info.getProcessControl();

	       // ExtendAttContainer femacon = info.getFema();
	        //���Ƽƻ���Ʒ����
	        Vector productIdentityValue = getExtendAttValue(con,
	            "productAttr");
	        //���Ƽƻ���������
	        Vector productCharacterValue = getExtendAttValue(con, "processAttr");
	        //���Ƽƻ�����
	        Vector sortValue = getExtendAttValue(con, "attrSort");
	        int ii=sortValue.size();
	        int row = this.multiList.getNumberOfRows();
	        if(row==0){
	        for (int i = row; i < row + ii; i++) {
	          for (int j = 0; j < group.getCount(); j++) {
	            addDefalutAttsToTable(i, j, group.getAttributeAt(j));
	          }
	        }
	        //��������      
	        for (int k = row, n = 0; k < row + ii; k++, n++) {

	      	  multiList.addTextCell(k, 0, (String) productIdentityValue.elementAt(n));
	      	  multiList.addTextCell(k, 1, (String) productCharacterValue.elementAt(n));
	      	  multiList.addTextCell(k, 2, (String) sortValue.elementAt(n));
	        }
	    	}
	        else{
	        int srow = this.multiList.getSelectedRow();
	    	if(srow >=0)
	    	{
		      	  multiList.addTextCell(srow, 0, (String) productIdentityValue.elementAt(srow));
		      	  multiList.addTextCell(srow, 1, (String) productCharacterValue.elementAt(srow));
		      	  multiList.addTextCell(srow, 2, (String) sortValue.elementAt(srow));
	    	}	
	        }

	        
	      	
	    }
    if (groupname.equals("���Ƽƻ�")) {
      QMProcedureInfo info = container.getProIfc();
//    CCBegin by liuzc 2009-12-31 ԭ�򣺹�����չ���Դ��ݹ��ܴ��󣬽����򴫸����� �μ�TD=2758
      if(info == null || info.getProcessControl() == null)
      {
    	  return;    
      }
//    CCEnd by liuzc 2009-12-31 ԭ�򣺹�����չ���Դ��ݹ��ܴ��󣬽����򴫸����� �μ�TD=2758
      ExtendAttContainer con = info.getProcessFlow();

      ExtendAttContainer femacon = info.getFema();
      //���Ա��
    //  Vector numberValue = getExtendAttValue(con, "characterNum");
      //��Ʒ����
      Vector productIdentityValue = getExtendAttValue(con,
          "productIdentity");
      //��������
      Vector productCharacterValue = getExtendAttValue(con, "productCharacter");
      //����
      Vector sortValue = getExtendAttValue(con, "sort");
      int ii=sortValue.size();


      //���������
      int row = this.multiList.getNumberOfRows();
      if(row==0){
      for (int i = row; i < row + ii; i++) {
        for (int j = 0; j < group.getCount(); j++) {
          addDefalutAttsToTable(i, j, group.getAttributeAt(j));
        }
      }
      //��������      
      for (int k = row, n = 0; k < row + ii; k++, n++) {

    	  multiList.addTextCell(k, 1, (String) productIdentityValue.elementAt(n));
    	  multiList.addTextCell(k, 2, (String) productCharacterValue.elementAt(n));
    	  multiList.addTextCell(k, 3, (String) sortValue.elementAt(n));
      }
  	}
      else{
    	  int srow = this.multiList.getSelectedRow();
        	if(srow >=0)
        	{
      	      	  multiList.addTextCell(srow, 1, (String) productIdentityValue.elementAt(srow));
      	      	  multiList.addTextCell(srow, 2, (String) productCharacterValue.elementAt(srow));
      	      	  multiList.addTextCell(srow, 3, (String) sortValue.elementAt(srow));
        	}	
      }
    }
    if (groupname.equals("����FMEA")) {
        QMProcedureInfo info = container.getProIfc();
//      CCBegin by liuzc 2009-12-31 ԭ�򣺹�����չ���Դ��ݹ��ܴ��󣬽����򴫸����� �μ�TD=2758
    	if(info == null || info.getProcessControl() == null)
    	{
    		return;    
    	}
//      CCEnd by liuzc 2009-12-31 ԭ�򣺹�����չ���Դ��ݹ��ܴ��󣬽����򴫸����� �μ�TD=2758
        ExtendAttContainer con = info.getProcessControl();

        //����
        Vector sortValue = getExtendAttValue(con, "attrSort");
        if(sortValue==null||sortValue.size()==0){
         ExtendAttContainer con1 = info.getProcessFlow();         
         //����
         sortValue = getExtendAttValue(con1, "sort");
        }
        //���������
        int row = this.multiList.getNumberOfRows();
      //  System.out.println("-----"+row+"   "+sortValue);
        if(row==0){
          for (int i = row; i < row + sortValue.size(); i++) {
            for (int j = 0; j < group.getCount(); j++) {
              addDefalutAttsToTable(i, j, group.getAttributeAt(j));
            }
          }
          for (int k = 0; k < sortValue.size(); k++) {
        	  multiList.addTextCell(k + row, 4, (String) sortValue.elementAt(k));
          }
        }
        else{

        	 int srow = this.multiList.getSelectedRow();
        	 //System.out.println("-----------��Ϊ��"+srow);
        	if(srow >=0)
        	{
      	      	  multiList.addTextCell(srow, 4, (String) sortValue.elementAt(srow));
        	}	
        }

      }
  }

 // CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
  // CCBegin by leixiao 2009-7-6 ԭ�򣺽��ϵͳ���� ,���Ӹ��ư�ť
  /**
   * ������Ը��ƹ��ܡ�
   * @param e ActionEvent
   * @author l 2008-12-26
   */
  void copyAddButton_actionPerformed(ActionEvent e)
  {
    int srow = this.multiList.getSelectedRow();
    	if(srow >=0)
    	{
        int rows = this.multiList.getNumberOfRows();
        int row = this.multiList.getNumberOfRows();
        for (int i = 0; i < group.getCount(); i++)
        {
          addDefalutAttsToTable(row, i, group.getAttributeAt(i));
          multiList.addCell(row,i,multiList.getCellAt(srow,i));
        }
    }
  }
  // CCEnd  by leixiao 2009-7-6 ԭ�򣺽��ϵͳ���� 
  
  /**
   * ��װ
   */
  void toolButton_actionPerformed(ActionEvent e){
	  
	  try {
		  
		launchChooserDialog("QMTool");
		
	} catch (QMRemoteException e1) {
		e1.printStackTrace();
	}
  }
  /**
   * ����
   * @param e
   */
  void materialButton_actionPerformed(ActionEvent e){
	  
	  try {
		  
			launchChooserDialog("QMMaterial");
			
		} catch (QMRemoteException e1) {
			e1.printStackTrace();
		}

  }
  
  /**
   * ���㰴ť�¼�ִ��
   * @param e ActionEvent ��ť�����¼�
   * ���⣨1��20080822 xucy  �޸�ԭ�򣺽����������̡��е���ͬ�б������ݡ�ȫ�������������б������ݣ������Ƶ������Ƽƻ�����
   */
  void attrButton_actionPerformed(ActionEvent e)
  {
      if (container.getSecondClassifyValue().equals("���Ƽƻ�")) 
      {
    	  String[] processFlowHead = flowMultiList.getHeadings();//(String[])TechnicsStepJPanel.list.get(0);
    	  String[]  controlPlanHead = multiList.getHeadings();//(String[])TechnicsStepJPanel.list.get(1);
    	  Vector vec = compareTableHeader(processFlowHead,controlPlanHead);
    	  int  flowRows =flowMultiList.getRowCount();
    	  int planRows = multiList.getRowCount();
    	  if(planRows > 0)
    	  {
    		  if(planRows <= flowRows)
    		  {
    			  for(int d = planRows; d < flowRows; d++)
    		      {
    		          for (int m = 0; m < group.getCount(); m++)
        			  {
        			      addDefalutAttsToTable(d, m, group.getAttributeAt(m));
        			  }
    		      }
    		  }

    		  for(int i = 0; i < vec.size(); i++)
        	  {
        	      int[] str = (int[])vec.elementAt(i);
    		      {
    		          for(int n = 0; n < flowRows; n++)
    			      {
    			          String flowStr = flowMultiList.getCellText(n, str[0]);
    				      String planStr = multiList.getCellText(n, str[1]);
    				      if(planStr == null ||planStr.equals(""))
    				      {
    				          multiList.addTextCell(n, str[1], flowStr);
    				      }
    			      }
    				
    		      }
    	      }
    	  }	
    	  else
    	  {
    	      for(int p = 0; p < flowRows; p++)
    		  {
    		      //int row = this.multiList.getNumberOfRows();
    			  for (int q = 0; q < group.getCount(); q++)
    			  {
    			      addDefalutAttsToTable(p, q, group.getAttributeAt(q));
    			  }
    			  for(int i = 0; i < vec.size(); i++)
    			  {
    			      int[] str = (int[])vec.elementAt(i);
    			      String flowStr = flowMultiList.getCellText(p, str[0]);
    			      String planStr = multiList.getCellText(p, str[1]);
    			      if(planStr == null ||planStr.equals(""))
    			      {
    				      multiList.addTextCell(p, str[1], flowStr);
    			      }
    			  }
    		  }
    	  }
	  } 
  }

  

  //oneHeader��twoHeader�ֱ����������ı�ͷ�ַ������顣
  //����÷������ص�Vector��������Ԫ�أ����磺[3,4]��[7,2]����˵����һ�����ĵ�3�к͵ڶ�������
  //��4�б�ͷ��ͬ����һ�����ĵ�7�к͵ڶ������ĵ�2�б�ͷ��ͬ
  public  Vector compareTableHeader(String[] oneHeader,String[] twoHeader)
  {
      Vector vec=new Vector();
      if(null==oneHeader||null==twoHeader)
          return vec;
      for(int i=0;i<oneHeader.length;i++)
      {
          for(int j=0;j<twoHeader.length;j++)
          {
              if(null!=oneHeader[i]&&null!=twoHeader[j]&&oneHeader[i].equals(twoHeader[j]))
                  vec.addElement(new int[]{i,j});
          }
      }
      return vec;       
  }
  //add by wangh on 20070709 ���ڱ༭��Ť�ļ�������.
  void editButton_actionPerformed(ActionEvent e){
	  ExtendAttGroup group;
	  
	  group = this.group.duplicate();
	  //�õ���ѡ���е��к�;
	  int row = multiList.getSelectedRow();

	  //�õ��ܹ�����;
	  int col = multiList.getNumberOfCols();
	  if(col*row>=0){
	  ArrayList rowObj = new ArrayList();
	  Vector nameVec = new Vector();
	  Vector rowVector = new Vector();
	  Vector contentVec = new Vector();
	  for(int i = 0;i<col;i++){
	  //�õ�ѡ��һ�е�ÿ������ڵľ�������.
	  //begin CR1
	  ExtendAttModel model = group.getAttributeAt(i);
	  String type = model.getAttType();
	  if(type.equalsIgnoreCase("SpecChar"))
	  {
	      Vector vec = (Vector)multiList.getSelectedObject(row, i);
	      contentVec.add(vec);
	  }
	  else if(type.equalsIgnoreCase("String"))
	  {
		  HashMap map = model.getFeature();
          String refType = (String) map.get("RefType");
          String oneRow = "";
          if (refType == null)
          {
        	  oneRow = (String)this.multiList.getSelectedObject(row,
                      i);
          }
          else
          if (refType.equalsIgnoreCase("EnumerateType"))
          {
        	  oneRow = (String)this.multiList.getSelectedObject(row,
                      i);
          }
          else
          if (refType.equalsIgnoreCase("ComboAtts"))
          {
        	  oneRow = multiList.getCellAt(row, i).getStringValue() +
            ";" +
            (String) multiList.getCellAt(row, i).getValue();
          }
		  //String oneRow = multiList.getCellText(row, i);
		  contentVec.add(oneRow);
	  }
	  //Begin CR3
	  else 
	  {
		  String str = (String)this.multiList.getSelectedObject(row, i);
		  contentVec.add(str);
	  }
	  //End CR3
	  //end CR1
	  //�õ����б�ͷ.
	  String head = multiList.getHeading(i);
	  nameVec.addElement(head);

	  CappExAttrPanel cappExAttrPanel = new CappExAttrPanel();
	  //ExtendAttModel model = group.getAttributeAt(i);//CR1
	  //�õ�ÿ������ֵ�Ŀؼ�
	  JComponent component = cappExAttrPanel.getCompoment(model);
	  rowVector.addElement(component);
	  //contentVec.add(oneRow);//CR1
	  }
	  rowObj.add(nameVec);
	  rowObj.add(rowVector);
	  rowObj.add(contentVec);

	  //�����ʡ�ļ��ж�Ӧ��һ���������Ͷ���������
	  String firstClassi = container.getFirstClassify();
	  String secondClassi = container.getSecondClassifyValue();

	  TParamJDialog p = new TParamJDialog(firstClassi,parentJFrame,secondClassi,rowObj);

	  Vector obj=(Vector)p.showDialog();

	  for(int j=0;j<col;j++){
		  ExtendAttModel model = group.getAttributeAt(j);
		  String type = model.getAttType();//CR1
		  //��JDialog���޸ĵ����ݴ���multiList����ʾ��
		  if (type.equalsIgnoreCase("Coding")){
			         HashMap map = model.getFeature();
			         String sorttype = (String) map.get("SortType");
			         Collection sorts = (Collection)this.etMaps.get(sorttype);
			         Iterator ite = sorts.iterator();
			         Vector vec = new Vector();
			          CodingIfc et2 = null;
			         while (ite.hasNext()) {
			           et2 = (CodingInfo) ite.next();

			           vec.add( et2.getCodeContent());
			         }
			         multiList.addComboBoxCell(row, j,  obj.get(j), vec);
			         }
		  //begin CR1
		  else if(type.equalsIgnoreCase("String")){
			  //multiList.addTextCell(row, j, String.valueOf((String)obj.get(j)));
			  HashMap map = model.getFeature();
	            String refType = (String) map.get("RefType");
	            if (refType == null)
	            {
	                if (obj != null)
	                {
	                    multiList.addTextCell(row, j, (String)obj.get(j));
	                }
	                else
	                {
	                    multiList.addTextCell(row, j, "");
	                }
	            }
	            else
	            if (refType.equals("EnumerateType"))
	            {
	                String classPath = (String) map.get("classpath");
	                EnumeratedType[] ets = (EnumeratedType[]) etMaps.get(classPath);
	                EnumeratedType et;
	                Vector vec = new Vector();
	                for (int k = 0; k < ets.length; k++)
	                {
	                    et = (EnumeratedType) ets[k];
	                    vec.add(et.getDisplay(local));
	                }
	                if (obj != null)
	                {
	                    this.multiList.addComboBoxCell(row, j, obj.get(j).toString(), vec);
	                }
	                else
	                {
	                    this.multiList.addComboBoxCell(row, j, "", vec);
	                }
	            }
	            else
	            if (refType.equals("ComboAtts"))
	            {

	                String sorttype = (String) map.get("SortType");
	                Collection sorts = (Collection)this.etMaps.get(sorttype);
//	                String valueStr = "";
//	                String sortStr = "";
//	                if (obj != null)
//	                {
//	                    StringTokenizer ston = new StringTokenizer(String.valueOf((String)obj.get(j)),
//	                            ";");
//	                    if (ston.hasMoreTokens())
//	                    {
//	                        valueStr = ston.nextToken();
//	                    }
//	                    if (ston.hasMoreTokens())
//	                    {
//	                        sortStr = ston.nextToken();
//	                    }
//	                }
	                Iterator ite = sorts.iterator();
	                Vector vec = new Vector();
	                while (ite.hasNext())
	                {
	                    vec.add(((CodingInfo) ite.next()).getCodeContent());
	                }
                    String[] attr = (String[])obj.get(j);
	                this.multiList.addComboBoxCell(row, j, attr[0], attr[1], vec);
	            }

		  }
		  else if(type.equalsIgnoreCase("SpecChar")){
			  if (obj != null)
	             {
				     Vector vec = new Vector();
				     vec.addElement((String)obj.get(j));
	                 multiList.addSpeCharCell(row, j, vec);
	             }
	             else
	             {
	                 multiList.addSpeCharCell(row, j, new Vector());
	             }
			  
		  }//end CR1

	  }
  }
	  return;
  }

  /**
   * ���������������
   * @param s String ������
   * @return int ����������
   */
  public int getColByAttname(String s) {
    String[] heads = this.multiList.getHeadings();

    for (int i = 0; i < heads.length; i++) {
      if (heads[i].equals(s))
        return i;
    }
    return -1;
  }

  /**
   * װ�乤��ļ���
   *
   */
	  private void AssembleCalculate()
	  throws NumberFormatException
	  {

        //�õ�ѡ����
	      int row= multiList.getSelectedRow();
//		  CCBegin by liuzc 2009-12-21 ԭ�򣺽������,FMEA����   �μ�TD��2685
		  int j1=this.getColByAttname("��ʩ���(S)");
	      int j2=this.getColByAttname("��ʩ���(O)");
	      int j3=this.getColByAttname("��ʩ���(D)");
	      int j4=this.getColByAttname("��ʩ���PRN");
//	      CCEnd by liuzc 2008-12-21 ԭ�򣺽������,FMEA����   �μ�TD��2685

//		    CCBegin by leixiao 2009-12-21 ԭ�򣺽������,FMEA����
	      //add by wangh on 20070516
	      int j5=this.getColByAttname("���ض�(s)");
	      int j6=this.getColByAttname("Ƶ����(o)");
	      int j7=this.getColByAttname("̽���(D)");
	      int j8=this.getColByAttname("����˳����(PRN)");
//	    CCEnd by leixiao 2009-12-21 ԭ�򣺽������   

	      String arr55 = (String) multiList.getSelectedObject(row, j5);
          String arr66 = (String) multiList.getSelectedObject(row, j6);
          String arr77 = (String) multiList.getSelectedObject(row, j7);

          if (!arr55.equals("") && !arr66.equals("") && !arr77.equals(""))
          {
        	  int newnum1 = Integer.parseInt(arr55) * Integer.parseInt(arr66) *
              Integer.parseInt(arr77);
			  //���������ŵ���3��
			  multiList.addTextCell(row, j8, String.valueOf(newnum1));
          }
          else
          {
        	  if(arr55.equals("")){
        		  String message = "���ض�(s)Ϊ��";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
        	  }
        	  else if(arr66.equals("")){
        		  String message = "Ƶ����(o)Ϊ��";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
        	  }
        	  else if(arr77.equals("")){
        		  String message = "̽���(D)Ϊ��";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  }
        	  multiList.addTextCell(row, j8, "");
          }

 


	      String arr11 = (String) multiList.getSelectedObject(row, j1);
          //�õ���2�е��ı�
          String arr22 = (String) multiList.getSelectedObject(row, j2);
          String arr33 = (String) multiList.getSelectedObject(row, j3);
//        ����
          if (!arr11.equals("") && !arr22.equals("") && !arr33.equals(""))
          {
        	  int newnum2 = Integer.parseInt(arr11) * Integer.parseInt(arr22) *
              Integer.parseInt(arr33);
			  //���������ŵ���3��
			  multiList.addTextCell(row, j4, String.valueOf(newnum2));
          }
          else
          {
        	  if(arr11.equals("")){
//      		  CCBegin by liuzc 2009-12-21 ԭ�򣺽������,FMEA����   �μ�TD��2685
        		  String message = "��ʩ���(S)Ϊ��";
//        	      CCEnd by liuzc 2009-12-21ԭ�򣺽������,FMEA����   �μ�TD��2685
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
        	  }
        	  else if(arr22.equals("")){
//      		  CCBegin by liuzc 2009-11-21ԭ�򣺽������,FMEA����   �μ�TD��2685
        		  String message = "��ʩ���(O)Ϊ��";
//        	      CCEnd by liuzc 2009-12-21 ԭ�򣺽������,FMEA����   �μ�TD��2685
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
        	  }
        	  else if(arr33.equals("")){
//        	      CCEnd by liuzc 2009-12-21ԭ�򣺽������,FMEA����   �μ�TD��2685
        		  String message = "��ʩ���(D)Ϊ��";
//        	      CCEnd by liuzc 2009-12-21ԭ�򣺽������,FMEA����   �μ�TD��2685
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  }
        	  multiList.addTextCell(row, j4, "");
              
          }

	  }

  /**
   *  ����,�˷���ʵ���˼��㹦��,��ʵʩ��������Ҫ���ݾ�����������Ӧ���޸Ĵ˷���
   */
  private void calculate() {
    String firstClassi = container.getFirstClassify();
    if (firstClassi.equals("QMAssembleProcedure")) {
      if (container.getSecondClassifyValue().equals("����FMEA")) {
    	  try{
    	  AssembleCalculate();
    	  }
    	  catch(NumberFormatException ex){
              String message = "���������Ҫ��������ݲ�������";
              String title = QMMessage.getLocalizedMessage(RESOURCE,
                      "information", null);
              JOptionPane.showMessageDialog(parentJFrame,
                                            message,
                                            title,
                                            JOptionPane.INFORMATION_MESSAGE);
              return;
              }
      }
    }
    else
    if (firstClassi.equals("QMPMProcedure")) {
      if (container.getSecondClassifyValue().equals("����FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "���������Ҫ��������ݲ�������";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }
    else
    if (firstClassi.equals("QMPushProcedure")) {
      if (container.getSecondClassifyValue().equals("����FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "���������Ҫ��������ݲ�������";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }
    else
    if (firstClassi.equals("QMWeldProcedure")) {
      if (container.getSecondClassifyValue().equals("����FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "���������Ҫ��������ݲ�������";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }
    else
    if (firstClassi.equals("QMGelaProcedure")) {
      if (container.getSecondClassifyValue().equals("����FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "���������Ҫ��������ݲ�������";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }
    else
    if (firstClassi.equals("QMVitrWashProcedure")) {
      if (container.getSecondClassifyValue().equals("����FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "���������Ҫ��������ݲ�������";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }
    else
    if (firstClassi.equals("QMDrillProcedure")) {
      if (container.getSecondClassifyValue().equals("����FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "���������Ҫ��������ݲ�������";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }
    else
    if (firstClassi.equals("QMMachineProcedure")) {
      if (container.getSecondClassifyValue().equals("����FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "���������Ҫ��������ݲ�������";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }

    else
    if (firstClassi.equals("QMPaintProcedure")) {
      if (container.getSecondClassifyValue().equals("����FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "���������Ҫ��������ݲ�������";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }

    else
    if (firstClassi.equals("QMCheckProcedure")) {
      if (container.getSecondClassifyValue().equals("����FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "���������Ҫ��������ݲ�������";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }

    else
    if (firstClassi.equals("QMCheckProcedure")) {
      if (container.getSecondClassifyValue().equals("����FMEA")) {
    	  try{
        	  AssembleCalculate();
        	  }
        	  catch(NumberFormatException ex){
                  String message = "���������Ҫ��������ݲ�������";
                  String title = QMMessage.getLocalizedMessage(RESOURCE,
                          "information", null);
                  JOptionPane.showMessageDialog(parentJFrame,
                                                message,
                                                title,
                                                JOptionPane.INFORMATION_MESSAGE);
                  return;
                  }
      }

    }

  }
  //CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ����  
  /**
   *  �������̵����Դ������Ƽƻ���
   * ������չ������,���ҵ��������չ����ֵ
   * @param extendattriedifc ��ʹ����չ���Ե�ҵ�����
   * @param attname ��չ��������
   * @return Object ָ����չ���Ե�����ֵ(���ڳ��������,������Ҫ����ֵ�������ֵ)
   */
  private Vector getExtendAttValue(ExtendAttContainer container,
                                   String attname) {
    Vector obj = null;
    ExtendAttModel model = null;
    //���ҵ��������չ��������
    if (container == null) {
      return null;
    }
    //�������л��ָ��������ģ��
    model = container.findExtendAttModel(attname);
    //����ǳ�������û��
    if (model == null) {
      //�ж������Ƿ��г�������
      if (container.isGroup()) {
        ExtendAttGroup group = null;
        Iterator names = container.getAttGroupNames().iterator();
        Vector vec = new Vector();
        Vector groups;
        while (names.hasNext()) {
          String name = (String) names.next();
          groups = container.getAttGroups(name);
          if (groups != null) {
            for (int i = 0; i < groups.size(); i++) {
              group = (ExtendAttGroup) groups.elementAt(i);
              model = group.findExtendAttModel(attname);
              if (model != null) {
                vec.add(model.getAttValue());
              }
            } //end for
          }
        }
        if (vec.size() > 0) {
          obj = vec;
        }
      } //end if

      return obj;
    } //end if
    return obj;
  }
 // CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
  
  
 //������������������� ��� begin 
  
  
//CCBegin SS11
    int addRowCount = 0;
    Vector dateVec = null;
    boolean turnoff = true;

    // /*
    class myTableModelListener implements TableModelListener
    {

        public void tableChanged(TableModelEvent arg0)
        {
            if(turnoff)
            {
                autoAddRowViewDate();
                turnoff = true;
            }

        }

    }
    private Vector hasRows()
    {

        int editRow = multiList.getTable().getEditingRow();
        int editColumn = multiList.getTable().getEditingColumn();

        if(editRow != -1 && editColumn != -1)
        {

            // �������ݼ���
            Vector valueDownVec = new Vector();
            // ÿ�����ݵ��������
            
            int maxLen=maxLen = new Integer(RemoteProperty.getProperty("bsx.zp.com.faw_qm.extend.columnNum-" + editColumn, "55")).intValue();
            
            int type = multiList.getCellAt(editRow, editColumn).getType();

            // �������
            if(type == 5)
            {
                SpeCharaterTextPanel sepChar = multiList.getCellAt(editRow, editColumn).getSpecialCharacter();
                String editData = sepChar.save();
                MultiLineController mlc = new MultiLineController();
                Vector dataVec = null;
                // �����������
                if(editData.indexOf((char)128) != -1 && editData.indexOf((char)129) != -1)
                {
                    DataSource dataSource = sepChar.resumeData(editData);// ͨ���ַ�����÷�װ����
                    dataVec = mlc.newSplitLine(dataSource, maxLen);

                }else
                {
                    int index = editData.indexOf((char)128);
                    editData = editData.substring(0, index);
                    dataVec = mlc.doChangeLine(editData, maxLen);
                }

                return dataVec;
            }
            // �ı���
            else if(type == 1)
            {

                String editStrData = multiList.getCellAt(editRow, editColumn).getStringValue();
                MultiLineController mlc = new MultiLineController();
                Vector dataVec = mlc.doChangeLine(editStrData, maxLen);

                return dataVec;
            }
        }
        return null;
    }

    String editData = "";
    //20140616
    private void autoAddRowViewDate()
    {
        int editRow = multiList.getTable().getEditingRow();
        int editColumn = multiList.getTable().getEditingColumn();
        if(editRow != -1 && editColumn != -1)
        {
            // �������ݼ���
            Vector valueDownVec = new Vector();
            
            // ÿ�����ݵ��������
            int maxLen =0;
            
            if(container.getFirstClassify().equals("consQMAssembleProcedure")){	

            	
            	maxLen= new Integer(RemoteProperty.getProperty("bsx.zp.com.faw_qm.extend.columnNum-" + editColumn, "55")).intValue();
       	 
           }
           //����
             if(container.getFirstClassify().equals("consQMMachineProcedure")) {
            	 
            	 maxLen= new Integer(RemoteProperty.getProperty("bsx.jijia.com.faw_qm.extend.columnNum-" + editColumn, "55")).intValue();
             }
            
            
            
            
            int type = multiList.getCellAt(editRow, editColumn).getType();

            // �������
            if(type == 5)
            {
                SpeCharaterTextPanel sepChar = multiList.getCellAt(editRow, editColumn).getSpecialCharacter();
                editData = sepChar.save();
                MultiLineController mlc = new MultiLineController();
                Vector dataVec = null;
                // �����������
                if(editData.indexOf((char)128) != -1 && editData.indexOf((char)129) != -1)
                {

                    DataSource dataSource = sepChar.resumeData(editData);// ͨ���ַ�����÷�װ����
                    dataVec = mlc.newSplitLine(dataSource, maxLen);

                }else
                {
                    int index = editData.indexOf((char)128);
                    editData = editData.substring(0, index);
                    dataVec = mlc.doChangeLine(editData, maxLen);
                }
                if(dataVec != null && dataVec.size() > 1)
                {
                    addRowCount = dataVec.size();
                    int allRowCount = multiList.getRowCount();
                    SpeCharaterTextPanel nexRowsepChar;
                    for(int a = editRow + 1;a < allRowCount;a++)
                    {
//                        String nexRowText=multiList.getCellAt(a, editColumn).getStringValue();
                         nexRowsepChar = multiList.getCellAt(a, editColumn).getSpecialCharacter();
                        String nexRowText = nexRowsepChar.save();
                        valueDownVec.add(nexRowText);
                    }

                    turnoff = false;

                    int insertRows =  emptySpeRows(editRow,editColumn,dataVec.size() - 1);
                    // ���б�ĩβ�����������Ҫ����
                    this.addNewRowOnLast(dataVec.size()-insertRows-1);
//                    this.addNewRowOnLast(dataVec.size() - 1);

                    allRowCount = multiList.getRowCount();
                    for(int e = valueDownVec.size();e >0;e--)
                    {
                        String value = (String)valueDownVec.get(e-1);
                        if(value==null)
                            value = "";
                        Vector v = new Vector();
                        v.add(value);
                        multiList.getCellAt(allRowCount +e-valueDownVec.size()-1, editColumn).getSpecialCharacter().clearAll();
                        multiList.getCellAt(allRowCount +e-valueDownVec.size()-1, editColumn).getSpecialCharacter().resumeData(v);
                    }

                    for(int k = 0;k < dataVec.size();k++)
                    {
                        String str = (String)dataVec.get(k);
                        Vector v = new Vector();
                        v.add(str);
                        multiList.getCellAt(editRow + k, editColumn).getSpecialCharacter().clearAll();
                        multiList.getCellAt(editRow + k, editColumn).getSpecialCharacter().resumeData(v);
                    }
                }
            }
            // �ı���
            else if(type == 1)
            {
                String editStrData = multiList.getCellAt(editRow, editColumn).getStringValue();
                MultiLineController mlc = new MultiLineController();
                Vector dataVec = mlc.doChangeLine(editStrData, maxLen);
              
                if(editColumn==1||editColumn==14)
                    return;

                if(dataVec != null && dataVec.size() > 1)
                {
                    addRowCount = dataVec.size();
                    int allRowCount = multiList.getRowCount();
                    for(int a = editRow + 1;a < allRowCount;a++)
                    {
                        String nexRowStrData = multiList.getCellAt(a, editColumn).getStringValue();
                        valueDownVec.add(nexRowStrData);
                    }
                    
                    turnoff = false;
                 
                    int insertRows =  emptyRows(editRow,editColumn,dataVec.size() - 1);
                    // ���б�ĩβ�����������Ҫ����
                    this.addNewRowOnLast(dataVec.size()-insertRows-1);
//                    this.addNewRowOnLast(dataVec.size() - 1);
                    
                    allRowCount = multiList.getRowCount();
                    for(int e = valueDownVec.size();e >0;e--)
                    {
                        String value = (String)valueDownVec.get(e-1);
                        multiList.addTextCell(allRowCount +e-valueDownVec.size()-1, editColumn,value);
                    }
                    
                    for(int k = 0;k < dataVec.size();k++)
                    {
                        String str = (String)dataVec.get(k);
                        multiList.addTextCell(editRow + k, editColumn, str);
                    }
                }
            }
            valueDownVec = null;
        }

    }

    public void addNewRowOnLast(int addRowCout)
    {

        int row1 = multiList.getSelectedRow();
        if(addRowCout != 0)
        {
            for(int a = 0;a < addRowCout;a++)
            {

                int row = multiList.getNumberOfRows();

                if(row1 != -1)
                {
                    for(int i = 0;i < group.getCount();i++)
                    {
                        addDefalutAttsToTable(row, i, group.getAttributeAt(i));
                    }

                }
            }
        }
    }
  
  //CCEnd SS11
  
   
   
  
  /**
   * ��ñ��ػ���ʾ��Ϣ
   * @param s ��Դ�ļ��е�key
   * @param aobj ��������
   * @return String ���ػ���ʾ��Ϣ
   */
  private String display(String s, Object aobj[])
  {
      String s1 = "";
      if (resource == null)
      {
    	  
          initResources();
      }
      s1 = QM.getLocalizedMessage(RESOURCEOther, s, aobj, null);

      return s1;
  }
  /**
   * ��ʼ����Դ
   */
  private void initResources()
  {
      try
      {
          if (resource == null)
          {
              resource = ResourceBundle.getBundle(RESOURCEOther, Locale.getDefault());
              return;
          }
      }
      catch (MissingResourceException _exception)
      {
          _exception.printStackTrace();
      }
  }
 
 //CCBegin SS12
  
  /**
   * ����ѡ����棬�û�����ѡ��ҵ�����ӵ������б��У����༭�����
   * @throws QMRemoteException
   * ����(5)20080602 �촺Ӣ�޸�  �޸�ԭ��:��ӹ�������ĵ�ʱ,��ͨ�û���������ȫ���ĵ�,��Ϊ
   * �������������ĵ�masterû��Ȩ�޿���,����Ҫ�����������ʾ�ĵ�,��������Ȩ�޿�����
   * 
   */
  protected void launchChooserDialog(String bsoName)
  throws QMRemoteException
 {

		String s = display("20", null);
		String s1 = bsoName; //getOtherSideClassName();
		
		CappChooser qmchooser = new CappChooser(s1, s, this
				.getTopLevelAncestor());//CR8

		qmchooser.setLastIteration(false);
		qmchooser.setSize(650, 500);
		String s2 = display("57", null);
		try {
			
			qmchooser.setMultipleMode(false);
			
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		qmchooser.setChildQuery(true);
		qmchooser.addListener(new CappQueryListener() {
			public void queryEvent(CappQueryEvent qmqueryevent) {
				if (qmqueryevent.getType().equals(CappQueryEvent.COMMAND)
						&& qmqueryevent.getCommand().equals(CappQuery.OkCMD)) {
					CappChooser qmchooser1 = (CappChooser) qmqueryevent
							.getSource();
					BaseValueIfc awtobject[] = qmchooser1.getSelectedDetails();
					BaseValueIfc finalawtobject[];
					//����(5) 20080602 �촺Ӣ�޸�
					ArrayList list = new ArrayList();
					try {
						int selectRow=multiList.getSelectedRow();
						
						if (awtobject != null&&multiList.getSelectedRow()!=-1) {
							int length = awtobject.length;
							for (int j = 0; j < length; j++) {
								
								Object obj =awtobject[j];
								
								if (obj instanceof QMEquipmentInfo) {
									
									

									QMEquipmentInfo equiInfo = (QMEquipmentInfo) obj;
									insertEq(equiInfo,selectRow,1);

//									multiList.addTextCell(selectRow, 1, equiInfo.getEqNum());
//									
//									String nextRowText=multiList.getCellText(selectRow+1, 1);
//									
//									if(nextRowText!=null&&!nextRowText.equals(""))
//										addNewRow();
//									
//									if(nextRowText==null)
//										addNewRow();
//									
//									multiList.addTextCell(selectRow+1, 1, equiInfo.getEqName());
//									
//									String nextTowRowText=multiList.getCellText(selectRow+2, 1);
//
//									if(nextTowRowText!=null&&!nextTowRowText.equals(""))
//										addNewRow();
//									
//									if(nextTowRowText==null)
//										addNewRow();
//									
//									multiList.addTextCell(selectRow+2, 1, equiInfo.getEqSpec());
//									multiList.addTextCell(selectRow, 20, equiInfo.getBsoID());
//									
//									
								}else if(obj instanceof QMMaterialInfo){
									
									QMMaterialInfo Materia = (QMMaterialInfo) obj;
									insertMa(Materia,selectRow,1);
//									String materName = Materia.getMaterialName();
//									
//									multiList.addTextCell(multiList
//												.getSelectedRow(), 1, materName);
//									multiList.addTextCell(selectRow, 21, Materia.getBsoID());
									
								}else if(obj instanceof QMToolInfo){
									
									QMToolInfo tool = (QMToolInfo) obj;
									
									String toolName=tool.getToolName();
									
								
									
									System.out.println("tool.getToolCf().getCodeContent()==========="+tool.getToolCf().getCodeContent());
									if(tool.getToolCf().getCodeContent().equals("����")||
											tool.getToolCf().getCodeContent().equals("��������")||
											tool.getToolCf().getCodeContent().equals("ר������")||
											tool.getToolCf().getCodeContent().equals("���")||
											tool.getToolCf().getCodeContent().equals("���鸨��")||
											tool.getToolCf().getCodeContent().equals("����")||
											tool.getToolCf().getCodeContent().equals("����о�")||
											tool.getToolCf().getCodeContent().equals("����������")) 
									{
										
										
									    insertTool(tool,selectRow, 14);
										
										
//										multiList.addTextCell(selectRow, 14, tool.getToolNum());
//										
//										String nextRowText=multiList.getCellText(selectRow+1, 14);
//										
//										if(nextRowText==null){
//											
//											addNewRow();
//										}else if(nextRowText!=null&&!nextRowText.equals("")){
//											
//											addNewRow();
//										}
//										
//										multiList.addTextCell(selectRow+1, 14, tool.getToolSpec());
//										
//										String nextTowRowText=multiList.getCellText(selectRow+2, 14);
//
//										if(nextTowRowText==null){
//											
//											addNewRow();
//										}else if(nextTowRowText!=null&&!nextTowRowText.equals("")){
//											
//											addNewRow();
//										}
//										
//										multiList.addTextCell(selectRow+2, 14, toolName);
//										multiList.addTextCell(selectRow, 22, tool.getBsoID());

									}else{
										
										
										multiList.addTextCell(multiList
												.getSelectedRow(), 2, toolName);
										
										multiList.addTextCell(multiList
												.getSelectedRow(), 3, tool
												.getToolNum());
										//CCBegin SS9
										String count=multiList.getCellText(multiList
												.getSelectedRow(), 5);
										if(count==null&&count.equals(""))
										  multiList.addTextCell(multiList
												.getSelectedRow(), 5,"1");
										//CCEnd SS9
										
										multiList.addTextCell(multiList
												.getSelectedRow(), 4, tool.getToolSpec());
										multiList.addTextCell(selectRow, 23, tool.getBsoID());
									}
									

									
								}
							}
							

						}else{
							 String message = "��ѡ��Ҫ�༭����!";
			                  String title = QMMessage.getLocalizedMessage(RESOURCE,
			                          "information", null);
			                  JOptionPane.showMessageDialog(parentJFrame,
			                                                message,
			                                                title,
			                                                JOptionPane.INFORMATION_MESSAGE);
			                  return;
						}
						int k = multiList.getNumberOfRows() - 1;
						multiList.selectRow(k);
						return;
					} catch (Exception _e) {
						_e.printStackTrace();
						return;
					}
				} else {
					return;
				}
			}
			

		});
		setLocation(qmchooser);
		qmchooser.setVisible(true);
		setEnabled(true);
}
  
  //CCEnd SS12
  
  /**
   * ���������λ��
   * @param comp ���
   */
  private void setLocation(Component comp)
  {
      Dimension compSize = comp.getSize();
      int compH = compSize.height;
      int compW = compSize.width;
      Dimension screenS = Toolkit.getDefaultToolkit().getScreenSize();
      int screenH = screenS.height;
      int screenW = screenS.width;
      comp.setLocation(Math.abs((screenW - compW) / 2),
                       Math.abs((screenH - compH) / 2));
  }
  
 
  /**
   * ִ���ֹ�¼���豸����
   * @param e ActionEvent
   */
  void extend_actionPerformed(ActionEvent e){
	  
	  
//	  System.out.println("ccccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
	  
	  int row=0;
	  int col=0;
	  Object obj = e.getSource();
      String actionCommand = e.getActionCommand();
//      System.out.println("actionCommand========================="+actionCommand);
      int t = actionCommand.indexOf(";");
      if (t != -1)
      {
          //�õ���
          String rowString = actionCommand.substring(0, t);
//          System.out.println("rowString========================="+rowString);
          int t1 = rowString.indexOf(":");
          row = Integer.parseInt(rowString.substring(t1 + 1, rowString.length()));
          //�õ���
          String colString = actionCommand.substring(t + 1,
                  actionCommand.length());
//          System.out.println("colString========================="+colString);
          int t2 = colString.indexOf(":");
          col = Integer.parseInt(colString.substring(t2 + 1, colString.length()));
      }
    //CCBgin SS12
      if(col == 1)
      {
          if(obj instanceof JTextField)
          {
              JTextField textField = (JTextField)obj;
//              if(textField.getText().trim() != null && !textField.getText().trim().equals(""))
//              {
                  handworkAddEquipNumber(textField, row,col);
//              }else
//              {
//                  // CCBegin SS4
//                  if(textField.getText() == null || textField.getText().equals(""))
//                  {
//                      equipAndMaterDecrease(row);
//                  }
//                  // CCEnd SS4
//              }
          }
        //CCEnd SS12
      }else if(col==3||col==14){
    	  
    	  if (obj instanceof JTextField)
          {
              JTextField textField = (JTextField) obj;
              if (textField.getText().trim() != null &&
                  !textField.getText().trim().equals(""))
              {
            	  handworkAddToolNumber(textField, row,col);
              }
              else
              {
            	//CCBegin SS4
//                  multiList.undoCell();
            	  
            	  
            	  toolDecrease(row,col);
            	  
            	//CCEnd SS4
              }
          }
    	  
      }
	  
	  
	  
  }
  //CCBegin SS12
  /**
   * �ֹ�����豸�������Ϣ��ָ�����ı�����
   * ������д�豸������ʱ���鿴20���Ƿ���id�������id����ѯid���豸���ǲ��ϣ��豸-�滻4�У�����-���ĵ�ǰ���ݣ�û��id���豸-����4�У�����-���ĵ�ǰ����
   * @param textField ��д�豸��ŵ��ı���
   * //20140616
   */
  private void handworkAddEquipNumber(JTextField textField,int row,int col)
  {
     String number = textField.getText().trim();
     if(number==null||number.length()==0)
     {

         String id = multiList.getCellText(row, 20);
         if(id != null && id.length() > 0)
            {
                multiList.addTextCell(row, 20, "");
//                multiList.addTextCell(row, 21, "");
                
                if(multiList.getCellText(row + 1, col)!=null)
                multiList.addTextCell(row + 1, col, "");
                if(multiList.getCellText(row + 2, col)!=null)
                multiList.addTextCell(row + 2, col, "");
                if(multiList.getCellText(row + 3, col)!=null)
                multiList.addTextCell(row + 3, col, "");
                
                //CCBegin SS21
                Hashtable eqMap = new Hashtable();
                if (!eqMap.containsKey(id))
                  eqMap.put(id, 1);
                eqDeleteVec.add(eqMap);
                //CCEnd SS21
            }

     }
      //���������豸�����ϵͳ�д��ڣ���ϵͳ�Ѹ��豸������б���
      QMEquipmentInfo eq = checkEquipmentIsExist(number);     
      QMMaterialInfo mater = checkMaterialIsExist(number);
      String no = "--";
      if(eq != null)
        {
          insertEq(eq,row,col);


        }else if(mater != null)
        {
            insertMa(mater,row, col);

        }
    
     if(eq==null&&mater==null&&(number!=null&&number.length()>0))
      {
    	  
    	  
    	  JOptionPane.showMessageDialog(getParentJFrame(),
                  "ϵͳ��û�б��Ϊ��("+number+")���豸�����!",
                  "��ʾ",
                  JOptionPane.INFORMATION_MESSAGE);
    	  
			//CCBegin SS17
			multiList.addTextCell(row, col, "");
			//CCEnd SS17
    	  return;
    	  
      }

  }
  /**
   * ��������
   * @param tool
   * @param row
   * @param col
   * //20140616
   * void
   */
  private void insertTool(QMToolInfo tool,int row,int col)
  {
      String no = "--";
      String id = multiList.getCellText(row, 23);
System.out.println("insertTool ������� id=="+id);
      if(id == null || id.isEmpty())
      {

          int allrow = emptyRows(row,col,2);
          downRow(row, col, 2-allrow,false);
//          String temp = multiList.getCellText(row+1, col);
//          int allrow = multiList.getRowCount();
//          if(temp==null)
//              downRow(row, col, 2,true);
//          else if(!temp.isEmpty())
//              downRow(row, col, 2,true);
//          else if(allrow-row-1<2)
//              downRow(row, col, 2-(allrow-row-1),true);
    
          multiList.addTextCell(row, 23, tool.getBsoID());
          multiList.addTextCell(row, col, tool.getToolNum());
        //CCBegin SS14
          if((tool.getToolStdNum()!=null&&!tool.getToolStdNum().equals(""))&&(tool.getToolSpec() == null || tool.getToolSpec().length() == 0))
         	 multiList.addTextCell(row + 1, col, tool.getToolStdNum());
          
          else if((tool.getToolSpec() != null && tool.getToolSpec().length() != 0)&&(tool.getToolStdNum()==null||tool.getToolStdNum().equals("")))
               multiList.addTextCell(row + 1, col, tool.getToolSpec());
           
          else if((tool.getToolStdNum()!=null&&!tool.getToolStdNum().equals(""))&&(tool.getToolSpec() != null && tool.getToolSpec().length() != 0))
        	  multiList.addTextCell(row + 1, col, tool.getToolSpec());
          else
               multiList.addTextCell(row + 1, col,no );
          //CCEnd SS14
          if(tool.getToolName() == null || tool.getToolName().length() == 0)
                multiList.addTextCell(row + 2, col, tool.getToolName());
            else
                multiList.addTextCell(row + 2, col, tool.getToolName());

      }else if(!id.isEmpty())
      {
          int allrow = multiList.getRowCount();
          if(allrow-row-1<3)
            downRow(row, col, 3-(allrow-row-1),false);
          
          if(multiList.getCellText(row + 1, 23) != null)
          multiList.addTextCell(row+1, 23, "");
          if(multiList.getCellText(row + 2, 23) != null)
          multiList.addTextCell(row+2, 23, "");

          
          multiList.addTextCell(row, 23, tool.getBsoID());
          multiList.addTextCell(row, col, tool.getToolNum());
          //CCBegin SS14
          if((tool.getToolStdNum()!=null&&!tool.getToolStdNum().equals(""))&&(tool.getToolSpec() == null || tool.getToolSpec().length() == 0))
         	 multiList.addTextCell(row + 1, col, tool.getToolStdNum());
          
          else if((tool.getToolSpec() != null && tool.getToolSpec().length() != 0)&&(tool.getToolStdNum()==null||tool.getToolStdNum().equals("")))
               multiList.addTextCell(row + 1, col, tool.getToolSpec());
           
          else if((tool.getToolStdNum()!=null&&!tool.getToolStdNum().equals(""))&&(tool.getToolSpec() != null && tool.getToolSpec().length() != 0))
        	  multiList.addTextCell(row + 1, col, tool.getToolSpec());
          else
               multiList.addTextCell(row + 1, col,no );
          //CCEnd SS14
        if(tool.getToolName() == null || tool.getToolName().length() == 0)
              multiList.addTextCell(row + 2, col, tool.getToolName());
          else
              multiList.addTextCell(row + 2, col, tool.getToolName());
      }
  }
  /**
   * ̽��ָ���еĿ�����
   * @param row
   * @param col
   * @param count
   * @return
   * //20140616 
   * int
   */
  private int emptyRows(int row,int col,int count)
  {
      String temp;
      int rows = 0;
      for(int i=1;i<=count;i++)
      {
          temp = multiList.getCellText(row+i, col);
          if(temp==null)
              return rows;
          if(!temp.isEmpty())
              return rows;
          rows++;
      }
      return rows;
  }
  /**
   * ̽���������ָ���еĿ�����
   * @param row
   * @param col
   * @param count
   * @return
   * //20140616 
   * int
   */
  private int emptySpeRows(int row,int col,int count)
  {
      SpeCharaterTextPanel temp;
      int rows = 0;
      CappTextAndImageCell text;
      for(int i=1;i<=count;i++)
      {
          text = multiList.getCellAt(row+i, col);
          if(text==null)
              return rows;
          temp = text.getSpecialCharacter();
//          String str = temp.save();
//          temp = multiList.getCellText(row+i, col);
          if(temp==null)
              return rows;
          if(temp.save().length()!=1)
              return rows;
          rows++;
      }
      return rows;
  }
  /**
   * �������
   * @param mater
   * @param row
   * @param col
   * 
   * void
   */
  private void insertMa(QMMaterialInfo mater,int row,int col)
  {
      multiList.addTextCell(row, 21, mater.getBsoID());
      multiList.addTextCell(row, col, mater.getMaterialNumber());
  }
  /**
   * �����豸
   * @param eq
   * @param row
   * @param col
   * //20140616
   * void
   */
  private void insertEq(QMEquipmentInfo eq,int row,int col)
  {
      String no = "--";
      String id = multiList.getCellText(row, 20);
      String mid = multiList.getCellText(row, 21);
      if((id == null || id.isEmpty()) && (mid == null || mid.isEmpty()))
      {
          

//          String temp = multiList.getCellText(row+1, col);
//          int allrow = multiList.getRowCount();
          int allrow = emptyRows(row,col,3);
          downRow(row, col, 3-allrow,false);
//          if(temp==null)
//              downRow(row, col, 3,false);
//          else if(!temp.isEmpty())
//              downRow(row, col, 3,false);
//          else if(allrow-row-1<3)
//              downRow(row, col, 3-(allrow-row-1),false);
     
          multiList.addTextCell(row, 20, eq.getBsoID());

          multiList.addTextCell(row, col, eq.getEqNum());
          //CCBegin SS16
          if(eq.getEqModel() == null || eq.getEqModel().length() == 0)
              multiList.addTextCell(row +1, col, no);
          else
              multiList.addTextCell(row + 1, col, eq.getEqModel());
          
          if(eq.getEqName() == null || eq.getEqName().length() == 0)
              multiList.addTextCell(row + 2, col, no);
          else
              multiList.addTextCell(row + 2, col, eq.getEqName());
         //CCEnd SS16
          if(eq.getEqManu() == null || eq.getEqManu().length() == 0)
              multiList.addTextCell(row + 3, col, no);
          else
              multiList.addTextCell(row + 3, col, eq.getEqManu());
          
      }else if(!id.isEmpty())
      {
          int allrow = multiList.getRowCount();
          if(allrow-row-1<3)
            downRow(row, col, 3-(allrow-row-1),false);
          
          multiList.addTextCell(row, 20, eq.getBsoID());
          if(multiList.getCellText(row + 1, 20) != null)
          multiList.addTextCell(row+1, 20, "");
          if(multiList.getCellText(row + 2, 20) != null)
          multiList.addTextCell(row+2, 20, "");
          if(multiList.getCellText(row + 3, 20) != null)
          multiList.addTextCell(row+3, 20, "");
          multiList.addTextCell(row, col, eq.getEqNum());
          //CCBegin SS16
          if(eq.getEqModel() == null || eq.getEqModel().length() == 0)
              multiList.addTextCell(row + 1, col, no);
          else
              multiList.addTextCell(row + 1, col, eq.getEqModel());
          if(eq.getEqName() == null || eq.getEqName().length() == 0)
              multiList.addTextCell(row + 2, col, no);
          else
              multiList.addTextCell(row + 2, col, eq.getEqName());
          //CCEnd SS16
          
          if(eq.getEqManu() == null || eq.getEqManu().length() == 0)
              multiList.addTextCell(row + 3, col, no);
          else
              multiList.addTextCell(row + 3, col, eq.getEqManu());
      }else if(!mid.isEmpty())
      {
          multiList.addTextCell(row, 21, "");

          int allrow = emptyRows(row,col,3);
          downRow(row, col, 3-allrow,false);
//          String temp = multiList.getCellText(row+1, col);
//          int allrow = multiList.getRowCount();
//          if(temp==null)
//              downRow(row, col, 3,false);
//          else if(!temp.isEmpty())
//              downRow(row, col, 3,false);
//          else if(allrow-row-1<3)
//              downRow(row, col, 3-(allrow-row-1),false);

          multiList.addTextCell(row, 20, eq.getBsoID());
          multiList.addTextCell(row, col, eq.getEqNum());
          //CCBeign SS16
          if(eq.getEqModel() == null || eq.getEqModel().length() == 0)
              multiList.addTextCell(row + 1, col, no);
          else
              multiList.addTextCell(row + 1, col, eq.getEqModel());
          if(eq.getEqName() == null || eq.getEqName().length() == 0)
              multiList.addTextCell(row + 2, col, no);
          else
              multiList.addTextCell(row + 2, col, eq.getEqName());
          //CCEnd SS16
         
          if(eq.getEqManu() == null || eq.getEqManu().length() == 0)
              multiList.addTextCell(row + 3, col, no);
          else
              multiList.addTextCell(row + 3, col, eq.getEqManu());
      }

  }
  private void clearRow(int row, int col, int count)
  {
      
  }
  /**
     * �´���
     * @param row
     * @param col void
     * 
     */
    private void downRow(int row, int col, int count,boolean isTool)
    {
        if(count<=0)
            return;
     // �������ݼ���
        Vector valueDownVec = new Vector();
        Vector idVec1 = new Vector();
        Vector idVec2 = new Vector();
        Vector idVec3 = new Vector();
        int allRowCount = multiList.getRowCount();
        for(int a = row + 1;a < allRowCount;a++)
        {
            String nexRowStrData = multiList.getCellAt(a, col).getStringValue();
            valueDownVec.add(nexRowStrData);
        }
        if(!isTool)
        {
            for(int c = row + 1;c < allRowCount;c++)
            {
                String nexRowStrData = multiList.getCellAt(c, 20).getStringValue();
                idVec1.add(nexRowStrData);
                multiList.addTextCell(c, 20, "");
            }
            for(int d = row + 1;d < allRowCount;d++)
            {
                String nexRowStrData = multiList.getCellAt(d, 21).getStringValue();
                idVec2.add(nexRowStrData);
                multiList.addTextCell(d, 21, "");
            }
        }
        if(isTool)
        {
            for(int e = row + 1;e < allRowCount;e++)
            {
                String nexRowStrData = multiList.getCellAt(e, 23).getStringValue();
                idVec3.add(nexRowStrData);
                multiList.addTextCell(e, 23, "");
            }
        }
        // ���б�ĩβ�����������Ҫ����
        this.addNewRowOnLast(count);
        allRowCount = multiList.getRowCount();
        for(int b = valueDownVec.size();b > 0;b--)
        {
            String value = (String)valueDownVec.get(b - 1);
            multiList.addTextCell(allRowCount + b - valueDownVec.size() - 1, col, value);
        }
        if(!isTool)
        {
            for(int f = idVec1.size();f > 0;f--)
            {
                String value = (String)idVec1.get(f - 1);
                multiList.addTextCell(allRowCount + f - idVec1.size() - 1, 20, value);
            }
            for(int g = idVec2.size();g > 0;g--)
            {
                String value = (String)idVec2.get(g - 1);
                multiList.addTextCell(allRowCount + g - idVec2.size() - 1, 21, value);
            }
        }
        if(isTool)
        {
            for(int h = idVec3.size();h > 0;h--)
            {
                String value = (String)idVec3.get(h - 1);
                multiList.addTextCell(allRowCount + h - idVec3.size() - 1, 23, value);
            }
        }
    }
    //CCEnd SS12
  /**
   * �ֹ�����豸�������Ϣ��ָ�����ı�����
   * @param textField ��д�豸��ŵ��ı���
   */
  private void handworkAddEquipNumber(JTextField textField,int row)
  {
     String number = textField.getText().trim();
      //���������豸�����ϵͳ�д��ڣ���ϵͳ�Ѹ��豸������б���
      QMEquipmentInfo eq = checkEquipmentIsExist(number);
      
      QMMaterialInfo mater = checkMaterialIsExist(number);
      
      //CCBegin SS1
//      int selectRow=multiList.getSelectedRow();
      //CCEnd SS1
      
      if (eq != null)
      {
    	  //CCBegin SS1
    	    multiList.addTextCell(row, 20, eq.getBsoID());
    	  
			multiList.addTextCell(row, 1, eq.getEqNum());
			
			String nextRowText=multiList.getCellText(row+1, 1);
			
			 //CCEnd SS1
			
			if(nextRowText==null){
				//CCBegin SS3
				addNewRow(row);
				//CCEnd SS3
			}else if(nextRowText!=null&&!nextRowText.equals("")){
				//CCBegin SS3
				addNewRow(row);
				//CCEnd SS3
			}
			//CCBegin SS1
			multiList.addTextCell(row+1, 1, eq.getEqName());
			
			String nextTowRowText=multiList.getCellText(row+2, 1);
			//CCEnd SS1
			if(nextTowRowText!=null&&!nextTowRowText.equals(""))
				//CCBegin SS3
				addNewRow(row+1);
			//CCEnd SS3
			
			if(nextTowRowText==null)
				//CCBegin SS3
				addNewRow(row+1);
			//CCEnd SS3
			//CCBegin SS1
			multiList.addTextCell(row+2, 1, eq.getEqSpec());
			//CCEnd SS1
         
      }
      if(mater!=null){
    	//CCBegin SS1
    	    multiList.addTextCell(row, 21, mater.getBsoID());
			multiList.addTextCell(row, 1, mater.getMaterialName());
			//CCEnd SS1
      }
      
     if(eq==null&&mater==null)
      {
    	  
    	  
    	  JOptionPane.showMessageDialog(getParentJFrame(),
                  "ϵͳ��û�б��Ϊ��("+number+")���豸�����!",
                  "��ʾ",
                  JOptionPane.INFORMATION_MESSAGE);
    	  
			//CCBegin SS17
			multiList.addTextCell(row, 1, "");
			//CCEnd SS17
    	  return;
    	  
      }

  }
  
 
  /**
     * �´���
     * @param row
     * @param col void
     * 
     */
    private void downRow(int row, int col, int count)
    {
        if(count<=0)
            return;
     // �������ݼ���
        Vector valueDownVec = new Vector();
        Vector idVec1 = new Vector();
        Vector idVec2 = new Vector();
        Vector idVec3 = new Vector();
        int allRowCount = multiList.getRowCount();
        for(int a = row + 1;a < allRowCount;a++)
        {
            String nexRowStrData = multiList.getCellAt(a, col).getStringValue();
            valueDownVec.add(nexRowStrData);
        }
        for(int c = row + 1;c < allRowCount;c++)
        {
            String nexRowStrData = multiList.getCellAt(c, 20).getStringValue();
            idVec1.add(nexRowStrData);
            multiList.addTextCell(c, 20, "");
        }
        for(int d = row + 1;d < allRowCount;d++)
        {
            String nexRowStrData = multiList.getCellAt(d, 21).getStringValue();
            idVec2.add(nexRowStrData);
            multiList.addTextCell(d, 21, "");
        }
        for(int e = row + 1;e < allRowCount;e++)
        {
            String nexRowStrData = multiList.getCellAt(e, 23).getStringValue();
            idVec3.add(nexRowStrData);
            multiList.addTextCell(e, 23, "");
        }
        // ���б�ĩβ�����������Ҫ����
        this.addNewRowOnLast(count);
        allRowCount = multiList.getRowCount();
        for(int b = valueDownVec.size();b > 0;b--)
        {
            String value = (String)valueDownVec.get(b - 1);
            multiList.addTextCell(allRowCount + b - valueDownVec.size() - 1, col, value);
        }
        for(int f = idVec1.size();f > 0;f--)
        {
            String value = (String)idVec1.get(f - 1);
            multiList.addTextCell(allRowCount + f - idVec1.size() - 1, 20, value);
        }
        for(int g = idVec2.size();g > 0;g--)
        {
            String value = (String)idVec2.get(g - 1);
            multiList.addTextCell(allRowCount + g - idVec2.size() - 1, 21, value);
        }
        for(int h = idVec3.size();h > 0;h--)
        {
            String value = (String)idVec3.get(h - 1);
            multiList.addTextCell(allRowCount + h - idVec3.size() - 1, 23, value);
        }
    }
    /**
     * ���ָ���еı����
     * @param row
     * @param col
     * @return
     * 
     * int
     */
    private int checkNumRow(int row,int col)
    {
        String nextRowText =null;
        int allRowCount = multiList.getRowCount();
        for(int i=row;i<allRowCount-1;i++)
        {
            nextRowText = multiList.getCellText(row, col);
            if(nextRowText!=null&&nextRowText.length()>0)
                return i;
        }
        return -1;
        
    }
    /**
     * �ֹ���ӹ�װ��ŵ�ָ�����ı�����
     * @param textField ��д��װ��ŵ��ı���
     * //20140616
     */
  private void handworkAddToolNumber(JTextField textField,int row,int col)
  {
		String number = textField.getText().trim();
		QMToolInfo tool = checkToolIsExist(number);
		if (tool != null) {
			System.out.println("handworkAddToolNumber   111");
			//CCBegin SS1
//			int selectRow = multiList.getSelectedRow();
			//CCEnd SS1
	         

//			System.out.println("tool.getToolCf().getCodeContent()==========="
//					+ tool.getToolCf().getCodeContent());

			if (col == 3) {
				if (tool.getToolCf().getCodeContent().equals("����")
						|| tool.getToolCf().getCodeContent().equals("��������")
						|| tool.getToolCf().getCodeContent().equals("ר������")
						|| tool.getToolCf().getCodeContent().equals("���")
						|| tool.getToolCf().getCodeContent().equals("���鸨��")
						|| tool.getToolCf().getCodeContent().equals("����")
						|| tool.getToolCf().getCodeContent().equals("����о�")
						|| tool.getToolCf().getCodeContent().equals("����������")) {

					JOptionPane.showMessageDialog(getParentJFrame(),
							"����߲�����ӵ��˴�!", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);

			//CCBegin SS17
			multiList.addTextCell(row, col, "");
			//CCEnd SS17
					return;

				} else {

					//CCBegin SS1
					multiList.addTextCell(row, 2, tool
							.getToolName());

					multiList.addTextCell(row, 3, tool
							.getToolNum());
					//CCBegin SS9
					String count=multiList.getCellText(row, 5);
					if(count==null||count.equals(""))
					   multiList.addTextCell(row, 5,"1");
					//CCEnd SS9

					
					  //CCBegin SS14
			          if((tool.getToolStdNum()!=null&&!tool.getToolStdNum().equals(""))&&(tool.getToolSpec() == null || tool.getToolSpec().length() == 0))
			         	 multiList.addTextCell(row , 4, tool.getToolStdNum());
			          
			          else if((tool.getToolSpec() != null && tool.getToolSpec().length() != 0)&&(tool.getToolStdNum()==null||tool.getToolStdNum().equals("")))
			               multiList.addTextCell(row , 4, tool.getToolSpec());
			           
			          else if((tool.getToolStdNum()!=null&&!tool.getToolStdNum().equals(""))&&(tool.getToolSpec() != null && tool.getToolSpec().length() != 0))
			        	  multiList.addTextCell(row , 4, tool.getToolSpec());
			          else
			               multiList.addTextCell(row , 4,"--" );
			          //CCEnd SS14
					System.out.println("handworkAddToolNumber  toolid=="+tool.getBsoID());
					multiList.addTextCell(row, 22, tool
							.getBsoID());
					//CCEnd SS1
				}

			} else if (col == 14) {

				if (tool.getToolCf().getCodeContent().equals("����")
						|| tool.getToolCf().getCodeContent().equals("��������")
						|| tool.getToolCf().getCodeContent().equals("ר������")
						|| tool.getToolCf().getCodeContent().equals("���")
						|| tool.getToolCf().getCodeContent().equals("���鸨��")
						|| tool.getToolCf().getCodeContent().equals("����")
						|| tool.getToolCf().getCodeContent().equals("����о�")
						|| tool.getToolCf().getCodeContent().equals("����������")) {

				    if(number == null || number.length() == 0)
                    {
                        String id = multiList.getCellText(row, 23);
System.out.println("insertTool ������� number is nullʱ id=="+id);
                        if(id != null && id.length() > 0)
                        {
                            multiList.addTextCell(row, 23, "");
                            // multiList.addTextCell(row, 21, "");

                            if(multiList.getCellText(row + 1, col) != null)
                                multiList.addTextCell(row + 1, col, "");
                            if(multiList.getCellText(row + 2, col) != null)
                                multiList.addTextCell(row + 2, col, "");

                        }
                    }

				    insertTool(tool,row, col);
//				    String id = multiList.getCellText(row, 23);
//
//		            if(id == null || id.isEmpty()) 
//		            {
//		                String temp = multiList.getCellText(row+1, col);
//		                int allrow = multiList.getRowCount();
//		                if(temp==null)
//		                    downRow(row, col, 2,true);
//		                else if(!temp.isEmpty())
//		                    downRow(row, col, 2,true);
//		                else if(allrow-row-1<2)
//		                    downRow(row, col, 2-(allrow-row-1),true);
//
//		                multiList.addTextCell(row, 23, tool.getBsoID());
//		                multiList.addTextCell(row, col, tool.getToolNum());
//		                multiList.addTextCell(row + 1, col, tool.getToolSpec());
//		                multiList.addTextCell(row + 2, col, tool.getToolName());
//
//		            }else if(!id.isEmpty())
//		            {
//		                multiList.addTextCell(row, 23, tool.getBsoID());
//                        multiList.addTextCell(row, col, tool.getToolNum());
//                        multiList.addTextCell(row + 1, col, tool.getToolSpec());
//                        multiList.addTextCell(row + 2, col, tool.getToolName());
//		            }
				

				} else {

					JOptionPane.showMessageDialog(getParentJFrame(),
							"������߲�����ӵ��˴�!", "��ʾ",
							JOptionPane.INFORMATION_MESSAGE);

			//CCBegin SS17
			multiList.addTextCell(row, col, "");
			//CCEnd SS17
					return;

				}
			}
		}else {
			System.out.println("handworkAddToolNumber   222");
			//CCBegin SS22
			if(number==null||number.length()==0)
			{
				toolDecrease(row, col);
				return;
			}			
			//CCEnd SS22
			
			//CCBegin SS18
			//װ�� ����ʾҲ���衰��
			if(container.getFirstClassify().equals("consQMAssembleProcedure")){
				//CCBegin SS19
				if(col==14)
				{
				}
				else
				{
					JOptionPane.showMessageDialog(getParentJFrame(), "ϵͳ��û�б��Ϊ��("
					    + number + ")�Ĺ�װ!", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
					multiList.addTextCell(row, col, "");
				}
				//CCEnd SS19
			}
			//���� ��ʾ�� ���á���
			if(container.getFirstClassify().equals("consQMMachineProcedure")) {
			JOptionPane.showMessageDialog(getParentJFrame(), "ϵͳ��û�б��Ϊ��("
					+ number + ")�Ĺ�װ!", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);

			//CCBegin SS17
			multiList.addTextCell(row, col, "");
			}
			//CCEnd SS18
			//CCEnd SS17
			return;
		}

	}
  
  
  
  /**
	 * ������Դ����ͨ��ָ�����豸��������ݿ��в����豸����
	 * 
	 * @param eqNumber
	 *            ָ�����豸���
	 * @return ���ҵ����豸����
	 */
  private QMEquipmentInfo checkEquipmentIsExist(String eqNumber)
  {
      Class[] c =
              {String.class};
      Object[] obj =
              {eqNumber};
      try
      {
          return (QMEquipmentInfo) useServiceMethod(
                  "ResourceService", "findEquipmentByNumber", c, obj);
      }
      catch (QMRemoteException ex)
      {
          //ex.printStackTrace();
          return null;
      }
  }
  
  /**
   * ������Դ����ͨ��ָ���Ĳ��ϱ�������ݿ��в��Ҳ��϶���
   * @param eqNumber ָ���Ĳ��ϱ��
   * @return ���ҵ��Ĳ��϶���
   */
  private QMMaterialInfo checkMaterialIsExist(String eqNumber)
  {
      Class[] c =
              {String.class};
      Object[] obj =
              {eqNumber};
      try
      {
          return (QMMaterialInfo) useServiceMethod(
                  "ResourceService", "findMaterialByNumber", c, obj);
      }
      catch (QMRemoteException ex)
      {
          //ex.printStackTrace();
          return null;
      }
  }
  /**
   * ������Դ����ͨ��ָ���Ĺ�װ��������ݿ��в��ҹ�װ����
   * @param eqNumber ָ���Ĺ�װ���
   * @return ���ҵ��Ĺ�װ����
   */
  private QMToolInfo checkToolIsExist(String toolNumber)
  {
      Class[] c =
              {String.class};
      Object[] obj =
              {toolNumber};
      try
      {
          return (QMToolInfo) useServiceMethod(
                  "ResourceService", "findToolByNumber", c, obj);
      }
      catch (QMRemoteException ex)
      {
          return null;
      }
  }
  
  /**
	 * ��ӿ���
	 */
public void addNewRow(){
	
	 int row = multiList.getNumberOfRows();
      int row1 = multiList.getSelectedRow();
      if (row1 < 0)
      {
          for (int i = 0; i < group.getCount(); i++)
          {
              addDefalutAttsToTable(row, i, group.getAttributeAt(i));
              multiList.setSelectedRow(row);
          }
      }
      else
      {
          for (int i = 0; i < group.getCount(); i++)
          {
              addDefalutAttsToTable(row, i, group.getAttributeAt(i));
          }
          for (int i = row - 1; i > row1; i--)
          {
              multiList.moveDown(i);
          }
          multiList.setSelectedRow(row1 + 1);
      }  
}

//CCBegin SS3
/**
 * ��ӿ���
 */
public void addNewRow(int selectRow){

 int row = multiList.getNumberOfRows();
  int row1 = selectRow;
  if (row1 < 0)
  {
      for (int i = 0; i < group.getCount(); i++)
      {
          addDefalutAttsToTable(row, i, group.getAttributeAt(i));
          multiList.setSelectedRow(row);
      }
  }
  else
  {
      for (int i = 0; i < group.getCount(); i++)
      {
          addDefalutAttsToTable(row, i, group.getAttributeAt(i));
      }
      for (int i = row - 1; i > row1; i--)
      {
          multiList.moveDown(i);
      }

      multiList.setSelectedRow(row1 + 1);
  }  
}
//CCEnd SS3
  
  
  /**
   * ���������ڿͻ���Զ�̵��÷���˷���
   * @param serviceName Ҫ���õķ���������
   * @param methodName Ҫ���õķ��񷽷�����
   * @param paraClass  Ҫ���õķ��񷽷��Ĳ���������
   * @param paraObject Ҫ���õķ��񷽷��Ĳ���ֵ
   * @return
   */
  public Object useServiceMethod(String serviceName, String methodName,
                                 Class[] paraClass, Object[] paraObject)
          throws QMRemoteException
  {
      if (verbose)
      {
          System.out.println(
                  "cappclients.capp.view.ParentJPanel.useServiceMethod() begin...");
      }
      RequestServer server = RequestServerFactory.getRequestServer();
      ServiceRequestInfo info1 = new ServiceRequestInfo();
      info1.setServiceName(serviceName);
      info1.setMethodName(methodName);
      Class[] paraClass1 = paraClass;
      info1.setParaClasses(paraClass1);
      Object[] objs1 = paraObject;
      info1.setParaValues(objs1);
      Object obj = null;
      obj = server.request(info1);
      if (verbose)
      {
          System.out.println(
                  "cappclients.capp.view.ParentJPanel.useServiceMethod() end...return: " +
                  obj);
      }
      return obj;
  }
  
  //������������������� ��� end
  
  //CCBegin SS10
  /**
   * 
   * ���ƶ��еġ���������/����������
   */
  void copyMultiButton_actionPerformed(ActionEvent e)
  {
  	int rows[] = this.multiList.getSelectedRows();
  	copystr = new String[rows.length];
  	for(int i =0;i<rows.length;i++)
  	{
  		System.out.println("rows==============="+rows[i]+"============"+this.multiList.getCellAt(rows[i], 0).getType());
  		String ss = this.multiList.getCellAt(rows[i], 0).getSpecialCharacter().save();
  		System.out.println("ss==============="+ss);
  		copystr[i] = ss;
  	}
  }
  
  /**
   * 
   * ���ж��еġ���������/����������
   */
  void cutMultiButton_actionPerformed(ActionEvent e)
  {
  	int rows[] = this.multiList.getSelectedRows();
  	copystr = new String[rows.length];
  	for(int i =0;i<rows.length;i++)
  	{
  		System.out.println("rows==============="+rows[i]+"============"+this.multiList.getCellAt(rows[i], 0).getType());
  		String str = this.multiList.getCellAt(rows[i], 0).getSpecialCharacter().save();
  		System.out.println("str==============="+str);
  		copystr[i] = str;
  		Vector v = new Vector();
  		if(null!=str&&(!str.trim().equals("")))
  		{
  			v.add(str);
  		}
  		if (v.size() > 0)
  		{
  			//��յ�Ԫ����ԭ�е������ַ�
  			this.multiList.getCellAt(rows[i], 0).getSpecialCharacter().clearAll();
  		}
  		this.multiList.setSelectedRow(rows[i]);
  	}
  }
  
  
  /**
   * 
   * ճ�����еġ���������/����������
   */
  void pasteMultiButton_actionPerformed(ActionEvent e)
  {
  	int srow = this.multiList.getSelectedRow();
  	System.out.println("srow==============="+srow);
  	int allrow = this.multiList.getNumberOfRows();
  	System.out.println("allrow==============="+allrow);
  	
  	//���ճ�����ݵ�����
  	int needcount = copystr.length;
  	System.out.println("needcount==============="+needcount);
  	
  	//�����ж�ѡ����֮���Ƿ���������
  	boolean lastflag = false;
  	
  	//���ճ��λ���������
  	int begincount = 0;
  	for(int j=srow;j<allrow;j++)
  	{
  		String str = this.multiList.getCellAt(j, 0).getSpecialCharacter().save();
  		System.out.println("str===============("+str+")");
  		if(null==str||(str.trim().equals(""))||(str.indexOf((char)128)==0&&str.length()==1))
  		{
  			System.out.println("11111");
  			begincount++;
  		}
  		else
  		{
  			System.out.println("22222");
  			break;
  		}
  		//���һֱ�жϵ����һ�У�˵��ճ��λ������û�д������ݵ��У����ǿ���
  		if(j==(allrow-1))
  		{
  			lastflag = true;
  		}
  	}
  	System.out.println("begincount==============="+begincount);
  	
  	//���ճ��λ����Ŀ����������˸��Ƶ���������ֻ��ֱ���ڿ�����ճ������
  	if(begincount>=needcount)
  	{
  		System.out.println("���й��ã�");
  		for(int i=0;i<needcount;i++)
  		{
  			Vector v = new Vector();
  			v.add(copystr[i]);
  			if (v.size() > 0)
  			{
  				//�޸�ԭ��Ԫ�񣬽���õĵ������ַ�ֵ�ӵ�ָ���ĵ�Ԫ����
  				this.multiList.getCellAt(srow+i, 0).getSpecialCharacter().resumeData(v);
  			}
  			System.out.println((srow+i)+"==============="+copystr[i]);
  		}
  	}
  	//���ѡ���к��ǿ��У���ô������Ҫ������
  	else if(lastflag)
  	{
  		//��Ҫ��������
  		int newrow = needcount-begincount;
  		System.out.println("newrow==============="+newrow);
  		
  		//����Ͷ�������
  		for(int ii=0;ii<newrow;ii++)
  		{
  			int row = this.multiList.getNumberOfRows();
  			for (int iii = 0; iii < group.getCount(); iii++)
  			{
  				addDefalutAttsToTable(row, iii, group.getAttributeAt(iii));
  				multiList.setSelectedRow(row);
  			}
  		}
  		//���Ƶõ��㹻���к�ճ��
  		for(int kk=0;kk<needcount;kk++)
  		{
  			Vector v = new Vector();
  			v.add(copystr[kk]);
  			if (v.size() > 0)
  			{
  				//�޸�ԭ��Ԫ�񣬽���õĵ������ַ�ֵ�ӵ�ָ���ĵ�Ԫ����
  				this.multiList.getCellAt(srow+kk, 0).getSpecialCharacter().resumeData(v);
  			}
  			System.out.println((srow+kk)+"==============="+copystr[kk]);
  		}
  	}
  	//������Ҫ��������ȱ�ٿ�����
  	else
  	{
  		System.out.println("���в�����������");
  		//��Ҫ�ж��Ƿ������е���Ͷ�
  		//������һ�����ϵĿ�����
  		int endcount = 0;
  		for(int jj=(allrow-1);jj>0;jj--)
  		{
  			String str = this.multiList.getCellAt(jj, 0).getSpecialCharacter().save();
  			if(null==str||(str.trim().equals(""))||(str.indexOf((char)128)==0&&str.length()==1))
  			{
  				endcount++;
  			}
  			else
  			{
  				break;
  			}
  		}
  		System.out.println("endcount==============="+endcount);
  		
  		//���ѡ��λ����Ŀ����� ���� ���¶����ϵĿ�����  �����˸��Ƶ�����
  		if((begincount+endcount)>=needcount)
  		{
  			//�������Ƶ�����
  			int changecount = needcount-begincount;
  			System.out.println("�������ƣ�"+(allrow-endcount-1)+"��"+(srow+begincount));
  			for(int k = (allrow-endcount-1); k>=(srow+begincount);k--)
  			{
  				Vector v = new Vector();
  				//���ָ����Ԫ�������ַ���ֵ
  				String str = this.multiList.getCellAt(k, 0).getSpecialCharacter().save();
  				//�жϻ�õ������ַ�ֲ�Ƿ�Ϊ��
  				if(null!=str&&(!str.trim().equals("")))
  				{
  					v.add(str);
  				}
  				if (v.size() > 0)
  				{
  					//��յ�Ԫ����ԭ�е������ַ�
  					this.multiList.getCellAt(k, 0).getSpecialCharacter().clearAll();
  					//�޸�ԭ��Ԫ�񣬽���õĵ������ַ�ֵ�ӵ�ָ���ĵ�Ԫ����
  					this.multiList.getCellAt(k+changecount, 0).getSpecialCharacter().resumeData(v);
  				}
  			}
  			
  			//���Ƶõ��㹻���к�ճ��
  			for(int kk=0;kk<needcount;kk++)
  			{
  				Vector v = new Vector();
  				v.add(copystr[kk]);
  				if (v.size() > 0)
  				{
  					//�޸�ԭ��Ԫ�񣬽���õĵ������ַ�ֵ�ӵ�ָ���ĵ�Ԫ����
  					this.multiList.getCellAt(srow+kk, 0).getSpecialCharacter().resumeData(v);
  				}
  				System.out.println((srow+kk)+"==============="+copystr[kk]);
  			}
  		}
  		//���������¶������У�Ȼ���������Ƶ���Ͷ˺�ճ��
  		else
  		{
  			//��Ҫ��������
  			int newrow = needcount-(begincount+endcount);
  			System.out.println("newrow==============="+newrow);
  			
  			//����Ͷ�������
  			for(int ii=0;ii<newrow;ii++)
  			{
  				int row = this.multiList.getNumberOfRows();
  				for (int iii = 0; iii < group.getCount(); iii++)
  				{
  					addDefalutAttsToTable(row, iii, group.getAttributeAt(iii));
  					multiList.setSelectedRow(row);
  				}
  			}
  			
  			//��������
  			int changecount = endcount+newrow;
  			allrow = this.multiList.getNumberOfRows();
  			System.out.println("new  allrow==============="+allrow);
  			System.out.println("�������ƣ�"+(allrow-changecount-1)+"��"+(srow+begincount));
  			for(int k = (allrow-changecount-1); k>=(srow+begincount);k--)
  			{
  				Vector v = new Vector();
  				//���ָ����Ԫ�������ַ���ֵ
  				String str = this.multiList.getCellAt(k, 0).getSpecialCharacter().save();
  				//�жϻ�õ������ַ�ֲ�Ƿ�Ϊ��
  				if(null!=str&&(!str.trim().equals("")))
  				{
  					v.add(str);
  				}
  				if (v.size() > 0)
  				{
  					//��յ�Ԫ����ԭ�е������ַ�
  					this.multiList.getCellAt(k, 0).getSpecialCharacter().clearAll();
  					//�޸�ԭ��Ԫ�񣬽���õĵ������ַ�ֵ�ӵ�ָ���ĵ�Ԫ����
  					this.multiList.getCellAt(k+changecount, 0).getSpecialCharacter().resumeData(v);
  				}
  			}
  			//���Ƶõ��㹻���к�ճ��
  			for(int kk=0;kk<needcount;kk++)
  			{
  				Vector v = new Vector();
  				v.add(copystr[kk]);
  				if (v.size() > 0)
  				{
  					//�޸�ԭ��Ԫ�񣬽���õĵ������ַ�ֵ�ӵ�ָ���ĵ�Ԫ����
  					this.multiList.getCellAt(srow+kk, 0).getSpecialCharacter().resumeData(v);
  				}
  				System.out.println((srow+kk)+"==============="+copystr[kk]);
  			}
  		}
  	}
  	//Ϊ��ˢ�½��棬������Ҫ�û�ѡ��ĳ�вŻῴ�����
  	this.multiList.setSelectedRow(this.multiList.getNumberOfRows()-1);
  }
  
  
  /**
   * 
   * ɾ�����еġ���������/����������
   */
  void deleteMultiButton_actionPerformed(ActionEvent e)
  {
  	int rows[] = this.multiList.getSelectedRows();
  	for(int i =0;i<rows.length;i++)
  	{
  		System.out.println("rows==============="+rows[i]+"============"+this.multiList.getCellAt(rows[i], 0).getType());
  		String str = this.multiList.getCellAt(rows[i], 0).getSpecialCharacter().save();
  		System.out.println("str==============="+str);
  		
  		Vector v = new Vector();
  		if(null!=str&&(!str.trim().equals("")))
  		{
  			v.add(str);
  		}
  		if (v.size() > 0)
  		{
  			//��յ�Ԫ����ԭ�е������ַ�
  			this.multiList.getCellAt(rows[i], 0).getSpecialCharacter().clearAll();
  		}
  		this.multiList.setSelectedRow(rows[i]);
  	}
  }  
  /**
   * 
   * ɾ�����ƶ��еġ���������/����������
   */
  void moveupMultiButton_actionPerformed(ActionEvent e)
  {
  	int rows[] = this.multiList.getSelectedRows();
  	int temprow = -1;
  	for(int j =0;j<rows.length;j++)
  	{
  		if(temprow==-1)
  		{
  			temprow = rows[j];
  		}
  		else
  		{
  			temprow++;
  		}
  		System.out.println("rows["+j+"]=="+rows[j]+"  and  temprow=="+temprow);
  		
  		if(temprow!=rows[j])
  		{
  			JOptionPane.showMessageDialog(getParentJFrame(), "ɾ�����Ʋ���Ҫ������ѡ�ж��У�", "��ʾ",
					JOptionPane.INFORMATION_MESSAGE);
				return;
  		}
  	}
  	
					
  	for(int i =0;i<rows.length;i++)
  	{
  		System.out.println("rows==============="+rows[i]+"============"+this.multiList.getCellAt(rows[i], 0).getType());
  		String str = this.multiList.getCellAt(rows[i], 0).getSpecialCharacter().save();
  		System.out.println("str==============="+str);
  		
  		Vector v = new Vector();
  		if(null!=str&&(!str.trim().equals("")))
  		{
  			v.add(str);
  		}
  		if (v.size() > 0)
  		{
  			//��յ�Ԫ����ԭ�е������ַ�
  			this.multiList.getCellAt(rows[i], 0).getSpecialCharacter().clearAll();
  		}
  	}
  	
  	//��������
  	int changecount = rows.length;
  	int allrow = this.multiList.getNumberOfRows();
  	System.out.println("new  allrow==============="+allrow);
  	System.out.println("�������ƣ�"+(rows[rows.length-1]+1)+"��"+allrow);
  	for(int k = (rows[rows.length-1]+1); k<allrow;k++)
  	{
  		Vector v = new Vector();
  		//���ָ����Ԫ�������ַ���ֵ
  		String str = this.multiList.getCellAt(k, 0).getSpecialCharacter().save();
  		//�жϻ�õ������ַ�ֲ�Ƿ�Ϊ��
  		if(null!=str&&(!str.trim().equals("")))
  		{
  			v.add(str);
  		}
  		if (v.size() > 0)
  		{
  			//��յ�Ԫ����ԭ�е������ַ�
  			this.multiList.getCellAt(k, 0).getSpecialCharacter().clearAll();
  			//�޸�ԭ��Ԫ�񣬽���õĵ������ַ�ֵ�ӵ�ָ���ĵ�Ԫ����
  			this.multiList.getCellAt(k-changecount, 0).getSpecialCharacter().resumeData(v);
  		}
  		this.multiList.setSelectedRow(k);
  	}
  }
  //CCEnd SS10

}

