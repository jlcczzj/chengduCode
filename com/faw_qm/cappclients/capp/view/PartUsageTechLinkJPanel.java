/** ���ɳ���PartUsageTechLinkJPanel.java	1.1  2003/08/28
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 �ɶ���Ӵӽṹ�͹�����Ӽ�  guoxiaoliang 2016-7-14
 * SS2 A004-2016-3437 ����ͬ�汾���������ֻ������һ��ͬ���͹��յ����� liunan 2017-1-13
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.cappclients.beans.cappassociationspanel.PartListPanel;
import com.faw_qm.cappclients.capp.controller.PartUsageTechLinkController;
import com.faw_qm.cappclients.capp.controller.PartUseTechDelegate;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartIfc;


/**
 * <p>Title:�㲿��ʹ�ù��տ��Ĺ���ά����� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 * @version 1.0
 */

public class PartUsageTechLinkJPanel extends ParentJPanel
{
    /***
     * ����bean
     */
    private CappAssociationsPanel cappAssociationsPanel = new
            CappAssociationsPanel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();
    
    //CCBegin SS2
    private CodingIfc techTypeIfc;
    //CCEnd SS2
    
//CCBegin SS1
    
    private PartListPanel partListPanel = new  PartListPanel();
    String techtype = "";
    /**
     * �б���ѡ�е���
     */
    int row = -1;

    /**
     * �б���ѡ�е���
     */
    int col = -1;
    
//CCEnd SS1
    /**
     * ������
     */
    private PartUsageTechLinkController controller;


    /**
     * ������
     */
    private PartUseTechDelegate delegate;


    /**
     * ���췽��
     * @param technicsType String ��������
     */
    public PartUsageTechLinkJPanel(String technicsType)
    {
    	//CCBegin SS1
    	 techtype = technicsType;
    	 //CCEnd SS1
    	
        delegate = PartUseTechDelegate.getDelegate();
        try
        {
            controller = delegate.getController(technicsType);
        }
        catch (QMException ex1)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex1.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }
        System.out.println("controller=========11111111111=========="+controller);
        
        if (controller != null)
        {
            controller.setLinkPanel(this);

            try
            {
                jbInit();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

//  CCBeginby leixiao 2009-6-24 ԭ��������������ʱ�����ձ���Զ����� 
    public PartUsageTechLinkJPanel(Component parent,String technicsType)
    {
        delegate = PartUseTechDelegate.getDelegate();
        try
        {
            controller = delegate.getController(technicsType);
        }  
        catch (QMException ex1)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(getParentJFrame(),
                                          ex1.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);

        }   
        System.out.println("controller=========22222222222=========="+controller);
        
        if (controller != null)
        {
            controller.setLinkPanel(this,parent);

            try
            {
            	 System.out.println("controller=========3333333333333=========="+controller);
                jbInit();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
    	
    }
//  CCEndby leixiao 2009-6-24 ԭ��������������ʱ�����ձ���Զ�����

    /**
     * �����ʼ��
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
    	//CCBegin SS1
    	 

	        
	        //cappAssociationsPanel.setIsStruct(true, 3);
	        //cappAssociationsPanel.setIsRouteList(true, 4);
	        cappAssociationsPanel.setIsInsert(true, 5);
	        
	        partListPanel.setStructButtonMnemonic('S');
	        partListPanel.setRouteListButtonMnemonic('R');
	        partListPanel.addActionListener(new java.awt.event.
	                                                ActionListener() {
	          public void actionPerformed(ActionEvent e) {
	            cappAssociationsPanel_actionPerformed(e);
	          }
	        });
	        
	  //����ʹ�������п��Ա༭
	        if(techtype.equals("�������գ��ü���")||techtype.equals("�������գ�ע�ܡ����ܡ����ݣ�")){
	          int[] is = {
	                0, 3, 4, 5,6};
	          partListPanel.setColsEnabled(is, true);

	        }
	        else{
	          int[] is = {
	              0, 3, 4, 5};
	          partListPanel.setColsEnabled(is, true);
	        }

	      
    	 
    	 
    	 //CCEnd SS1
    	
        setLayout(gridBagLayout1);
        add(cappAssociationsPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 0), 0, 0));

    }
//CCBegin SS1
    
    /**
     * ���ù�������
     * @param info
     */
    public void setCoding(CodingIfc info) {
  	  partListPanel.setTechTypeCodingIfc(info);
  	  //CCBegin SS2
  	  techTypeIfc = info;
  	  //CCEnd SS2
    }
    
//CCEnd SS1

    /**
     * ���ý�����ʾģʽ
     * @param m  ������ʾģʽ(�༭ģʽ���鿴ģʽ)  EDIT_MODE or VIEW_MODE
     */
    public void setMode(String m)
    {
        cappAssociationsPanel.setMode(m);
    }


    /**
     * ���õ�ǰҵ�����
     * @param info ���տ�ֵ����
     */
    public void setTechnics(QMFawTechnicsInfo info)
    {
        cappAssociationsPanel.setObject(info);
        //CCBegin SS2
        cappAssociationsPanel.setTechTypeCodingIfc(techTypeIfc);
        //CCEnd SS2
    }

    public void setLinks(Collection links)
    {
        cappAssociationsPanel.setLinks(links);
    }


    /**
     * ������й���
     * @return ���й��������ļ���   
     */
    public Vector getAllLinks()
            throws Exception
    {
        if (!cappAssociationsPanel.check())
        {
            throw new Exception("���������������");
        }
        else
        {
            return cappAssociationsPanel.getLinks();
        }
    }


    /**
     * ���Ҫ��ɾ���Ĺ���
     * @return ���������ļ���
     */
    public Vector getDeletedLinks()
    {
        Vector delVector = new Vector();
        for (Enumeration e = cappAssociationsPanel.getRemoveLinks();
                             e.hasMoreElements(); )
        {
            delVector.addElement(e.nextElement());
        }
        return delVector;
    }

    public void clear()
    {
//      CCBeginby leixiao 2009-9-14 ԭ�򣺻�ȡ���϶���
        cappAssociationsPanel.setAssociatpart(null);
//      CCEndby leixiao 2009-9-14 ԭ�򣺻�ȡ���϶���
        cappAssociationsPanel.clear();
        controller.clear();
    }


    /**
     * ����㲿��
     */
    public void addPartToTable(QMPartIfc part)
    {
        try
        {
            cappAssociationsPanel.addSelectedObject(part);
        }
        catch (InvocationTargetException ex)
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (InstantiationException ex)
        {
            ex.printStackTrace();
        }
        catch (NoSuchMethodException ex)
        {
            ex.printStackTrace();
        }
        catch (IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
    }
//CCBegin SS1
    
    public void cappAssociationsPanel_actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        String actionCommand = e.getActionCommand();
        int t = actionCommand.indexOf(";");
        if (t != -1) {
          //�õ���
          String rowString = actionCommand.substring(0, t);
          int t1 = rowString.indexOf(":");
          row = Integer.parseInt(rowString.substring(t1 + 1, rowString.length()));
          //�õ���
          String colString = actionCommand.substring(t + 1,
                                                     actionCommand.length());
          int t2 = colString.indexOf(":");
          col = Integer.parseInt(colString.substring(t2 + 1, colString.length()));
        }
        //add by ll in 20080116 ------start
        //if (col == 0 && stepType.equals("װ�乤��"))
        if (col == 0) {
          if (obj instanceof JTextField) {
        	  partListPanel.setEnabled(false);
            JTextField textField = (JTextField) obj;
            if (textField.getText().trim() != null &&
                !textField.getText().trim().equals("")) {
              handworkAddPartNumber(textField);
            }
            else {
            	partListPanel.setEnabled(true);
            }
          }
        }
        //add by ll in 20080116 ------end
      }
    
    
    private void handworkAddPartNumber(JTextField textField) {
        String number = textField.getText().trim();
        QMPartIfc part = checkPartIsExist(number);
        if (part != null) {
          try {
        	  partListPanel.addObjectToRow(part, row);
          }
          catch (InvocationTargetException ex) {
            ex.printStackTrace();
          }
          catch (ClassNotFoundException ex) {
            ex.printStackTrace();
          }
          catch (InstantiationException ex) {
            ex.printStackTrace();
          }
          catch (NoSuchMethodException ex) {
            ex.printStackTrace();
          }
          catch (IllegalAccessException ex) {
            ex.printStackTrace();
          }
          partListPanel.setEnabled(true);
        }
        else {
          JOptionPane.showMessageDialog(getParentJFrame(),
                                        "���㲿�������ڣ�����������",
                                        "��ʾ", JOptionPane.WARNING_MESSAGE);
          partListPanel.setEnabled(true);
          partListPanel.undoCell();
        }
      }
    
    
    private QMPartIfc checkPartIsExist(String partNumber) {
        Class[] c = {
            String.class};
        Object[] obj = {
            partNumber};
        QMPartIfc part = null;
        try {
          part = (QMPartIfc) useServiceMethod(
              "TotalService", "getPartID", c, obj);
        }
        catch (QMRemoteException ex) {
          ex.printStackTrace();
        }
        return part;
      }
    
//CCEnd SS1

    /**
     * ��Ӽ�����
     * @param e ActionListener
     */
    public void addListener(ActionListener e)
    {
        controller.addListener(e);
    }


    /**
     * ���ò��Ϲ������
     * @param materialPanel TechUsageMaterialLinkJPanel ���Ϲ������
     */
    public void setMaterialLinkJPanel(TechUsageMaterialLinkJPanel materialPanel)
    {
        controller.setMaterialLinkJPanel(materialPanel);
    }


    /**
     * ��ù���bean
     * @return CappAssociationsPanel ����bean
     */
    public CappAssociationsPanel getCappAssociationsPanel()
    {
        return cappAssociationsPanel;
    }

    /**
     * ��ù���bean
     * @return CappAssociationsPanel ����bean
     */
    public PartListPanel getPartListPanel() {
      return partListPanel;
    }

}
