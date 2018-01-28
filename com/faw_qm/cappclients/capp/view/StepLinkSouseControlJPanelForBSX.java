package com.faw_qm.cappclients.capp.view;
/**
 * SS7 �޸ķ���ƽ̨���⣬�����ţ�A005-2014-2923��������  2014-6-5
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.resource.support.model.DrawingInfo;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.resource.support.model.QMTermInfo;
import com.faw_qm.resource.support.model.QMToolInfo;

public class StepLinkSouseControlJPanelForBSX extends ParentJPanel{
	
	//CCBegin SS1
	/**�������*/
    public JTabbedPane relationsJPanel = new JTabbedPane();
	//CCEnd SS1
	
    
	  /**�ĵ�������ά�����*/
    public ProcedureUsageDocJPanel doclinkJPanel = new ProcedureUsageDocJPanel();
    
    /**��ͼά��*/
    public ProcedureUsageDrawingPanel drawingLinkPanel;
    //���
    public ProcedureUsagePartJPanel partlinkJPanel;
    
    private GridBagLayout gridBagLayout7 = new GridBagLayout();
    
    private CodingIfc stepType;
    
    private JFrame parentFrame;
    
    private EquipmentToolMaintainJDialog f1 = null;
    /**
     * ���տ�
     */
    private QMTechnicsIfc parentTechnics;
    private QMProcedureInfo myProceInfo;
    
    private String existProcessType = "";
    
    /**c�д�Ź����µ�ֱ�ӹ���*/
    private Collection c = null;
    
    public StepLinkSouseControlJPanelForBSX(JFrame pFrame,QMProcedureInfo proceInfo){
    	
    	
    	super();
    	parentFrame=pFrame;
    	myProceInfo=proceInfo;
    	jbInit();
    }
	
    void jbInit(){
    	
    	this.setLayout(gridBagLayout7);
    	this.setMinimumSize(new Dimension(337, 100));
    	this.setPreferredSize(new Dimension(337, 190));
    	
    	 f1 = new EquipmentToolMaintainJDialog(parentFrame);
    	
    	drawingLinkPanel = new ProcedureUsageDrawingPanel(parentFrame);
//    	relationsJPanel.add(doclinkJPanel, "�ĵ�");
//        relationsJPanel.add(drawingLinkPanel, "��ͼ");
//        newPartlinkJPanel(myProceInfo.getProcessType().getCodeContent());
//        
    	setUpdateMode();
    	
        this.add(relationsJPanel,
                new GridBagConstraints(0, 0, 4, 1, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(5, 9, 5, 7), 0, 0));
    	
    	
    }
    
    public void newPartlinkJPanel(String stepType)//anan
    {
        //��̬���ù���ʹ���㲿������
        String link = RemoteProperty.getProperty(
                "com.faw_qm.cappclients.capp.view" + stepType);
        System.out.println("stepType===================="+stepType);
        System.out.println("link===================="+link);
        if (link == null || link.trim().equals("null") || link.equals(""))
        {
            if (partlinkJPanel != null)
            {
                relationsJPanel.remove(partlinkJPanel);
            }
            partlinkJPanel = null;
        }
        else
        {
            if (partlinkJPanel != null)
            {
                relationsJPanel.remove(partlinkJPanel);
            }
            partlinkJPanel = new ProcedureUsagePartJPanel(stepType);
            partlinkJPanel.setIsProcedure(true);
            relationsJPanel.add(partlinkJPanel, "�㲿��");
        }
    }
    
    
    
    /**
     * ���ý���Ϊ����ģʽ����������������ʾ�ڽ�����
     */
    private void setUpdateMode()
    {
        clear();
        System.out.println("myProceInfo========================================="+myProceInfo);
        drawingLinkPanel.setModel(2); 
        if(myProceInfo!=null)
         drawingLinkPanel.setProcedure(myProceInfo);
        
        relationsJPanel.add(drawingLinkPanel, "��ͼ");
        
        doclinkJPanel.setMode("Edit");
        if(myProceInfo!=null)
         doclinkJPanel.setProcedure(myProceInfo);
        
        relationsJPanel.add(doclinkJPanel, "�ĵ�");
        
        
        if(myProceInfo!=null)
         newPartlinkJPanel(myProceInfo.getTechnicsType().
                          getCodeContent());
        
        if (partlinkJPanel != null)
        {
        	 partlinkJPanel.setMode("Edit");
        	 if(myProceInfo!=null)
              partlinkJPanel.setProcedure(myProceInfo);

        }
       
        f1.setEditMode();

        TechnicsContentJPanel.addFocusLis.setCompsFocusListener(this);
    }
    /**
     *�˷��������������ά�������е�����
     */
    public void clear()
    {
      
        drawingLinkPanel.clear();
        doclinkJPanel.clear();
        existProcessType = "";
        if (partlinkJPanel != null)
        {
            relationsJPanel.remove(partlinkJPanel);
            partlinkJPanel = null;
        }

    }
  

    


    

}
