/** ���ɳ���TechnicsContentJPanel.java	1.1  2003/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2010/01/07 �촺Ӣ  ԭ�򣺵�һ�ν���ϲ����ս������
 * SS1 ��������Դ�嵥һ���� zhaoqiuying 2013-01-23
 * SS2 ���ֲ�ͬ��֯���õĹ��򹤲�����  xudezheng 2013-02-17
 * SS3 ������������� guoxiaoliang 2014-03-20
 * SS4 ����������Ĺ��������ļ� pante 2014-02-19
 * SS5 ������鿴���˹����� liunan 2014-5-19
 * SS6 ����û�������,Ҫ��ȡ��ѡ�������ಽ�� pante 2014-06-19
 * SS7 ������һ������ʾ��Ρ� liunan 2014-8-4
 * SS8 ������һ����������ʷ����û��ÿ������������쳣 liunan 2014-8-26
 * SS9 �������ӹ�װ��ϸ���豸�嵥��ģ���嵥�� guoxiaoliang 2014-08-22
 * SS10 ���Ӻϲ����չ��� ����� 2014-12-15
 * SS11 �ɶ������ҵָ���鹤�պ͹������ά������ guoxiaoliang 2016-11-23
 * SS12 �ɶ�����������ȥ������Ϊ�����ҵָ���鹤�� guoxiaoliang 2016-11-13
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyVetoException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.faw_qm.capp.model.PartUsageQMTechnicsLinkInfo;
import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.capp.model.QMTechnicsQMMaterialLinkInfo;
import com.faw_qm.cappclients.beans.processtreepanel.TechnicsTreeObject;
import com.faw_qm.cappclients.beans.resourcetreepanel.ResourceTypeObject;
import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.technics.view.ModelProcedureMasterJPanel;
import com.faw_qm.cappclients.technics.view.ModelTechMasterJPanel;
import com.faw_qm.cappclients.util.CappTreeNode;
import com.faw_qm.cappclients.util.CappTreeObject;
import com.faw_qm.codemanage.model.CodingClassificationInfo;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.codemanage.model.CodingInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.resource.support.model.QMMaterialInfo;
import com.faw_qm.cappclients.capp.util.*;

/** 
 * <p>Title: �����������</p>
 * <p>���ڹ���ά�������档�ڱ�����ڣ����ý������ʾ���ݣ����տ�������򹤲�</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 * @version 1.0
 * ���⣨1��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤��
 */

public class TechnicsContentJPanel extends JPanel
{
	
	//add by guoxl(������)
	public static AddFocusListener addFocusLis=new AddFocusListener();
	//add end 
    /**��������Ϣ���*/
    private TechnicsMasterJPanel technicsMasterJPanel = null;
    //CCBegin SS11
    //�ɶ�����������ά�����
    private CdTechnicsMasterJPanel cdTechnicsMasterJPanel = null;
     //CCEnd SS11

    /**�������*/
    private TechnicsStepJPanel stepJPanel = null;


    /**�������*/
    private TechnicsPaceJPanel paceJPanel = null;


    /**���͹�������Ϣ���*/
    private ModelTechMasterJPanel modelTechJPanel = null;


    /**���͹�������Ϣ���*/
    private ModelProcedureMasterJPanel modelStepJPanel = null;
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**�ϲ����չ�����*/
    private UniteTechnicsReguJPanel uniteTechnicsJPanel = null;
    
//    CCBegin SS10
    /**���غϲ����չ�����*/
    private UniteTechnicsReguJPanelForCT uniteTechnicsJPanelForCT = null;
//    CCEnd SS10


    /**������ʾģʽ������ģʽ�����*/
    public final static int UPDATE_MODE = 0;


    /**������ʾģʽ������ģʽ�����*/
    public final static int CREATE_MODE = 1;


    /**������ʾģʽ���鿴ģʽ�����*/
    public final static int VIEW_MODE = 2;


    /**������ʾģʽ�����Ϊģʽ�����*/
    public final static int SAVEAS_MODE = 3;


    /**��ѡ������ڵ����*/
    private BaseValueInfo selectedObject = null;


    /**������*/
    private JFrame parentJFrame;


    /**��ѡ��Ĺ������ڵ�*/
    private CappTreeNode selectedNode;


    /**��������ѡ���*/
    private SelectTechnicsTypeJDialog technicsSelectDialog;
    private Hashtable stepTypetable = null;
    private CodingIfc technicsType;


    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**���ڱ����Դ�ļ�·��*/
    protected static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";
    private int mode;


    /**
     * ���캯��
     */
    public TechnicsContentJPanel(JFrame parent)
    {
        try
        {
            parentJFrame = parent;
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * �����ʼ��
     * @throws Exception
     */
    void jbInit()
            throws Exception
    {
        setLayout(gridBagLayout1);
    }


    /**
     * ������ѡ��Ľڵ�
     * @param parent
     */
    public void setSelectedNode(CappTreeNode node)
    {
        selectedNode = node;
    }


    /**
     * ��õ��ñ���ĸ�����
     * @return ������
     */
    public JFrame getParentJFrame()
    {
        return parentJFrame;
    }


    /**
     * ���õ�ǰ������ѡ���ҵ�����
     * @param obj �������ϵĽڵ���󣨹��տ�������򹤲���
     */
    public void setSelectedObject(BaseValueInfo obj)
    {
        selectedObject = obj;
    }


    /**
     * ��õ�ǰ������ѡ���ҵ�����
     * @return �������ϵĽڵ���󣨹��տ�������򹤲���
     */
    protected BaseValueInfo getSelectedObject()
    {
        return selectedObject;
    }


    /**
     * ������ʾ���տ�ά�����
     * @param optionMode  ������ʾģʽ�����������¡��鿴�����Ϊ��
     */
    public void setTechnicsMode(int optionMode)
    {
        //��������
        CodingIfc technicsType = null;
        //���⣨2��20090106 xucy �޸�  �޸�ԭ�򣺱�����¹��򣨹�����֮����汣�ָ���״̬���ٵ���鿴�˽ڵ㣬
    	//���������Ƿ񱣴���µĹ�����ʾ��
        if(this.techStepIsShowing())
    	{
    		this.stepJPanel.setVisible(false);
    	}
    	if(this.techPaceIsShowing())
    	{
    		this.paceJPanel.setVisible(false);
    	}
        //����ģʽ��,���ѡ��Ĺ�������
        if (optionMode == CREATE_MODE)
        {
            //�������ѡ��Ĺ�������
            CappTreeObject obj = ((TechnicsRegulationsMainJFrame)
                                  parentJFrame).
                                 getSelectedObject();
            if (obj != null && obj instanceof ResourceTypeObject)
            {
                technicsType = (CodingIfc) ((ResourceTypeObject) obj).getObject();
                
                
            }
            //����ѡ���
            else
            {
                if (technicsSelectDialog == null)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.TECHNICSTYPE, null);
                    technicsSelectDialog = new SelectTechnicsTypeJDialog(
                            parentJFrame);
                }
                technicsSelectDialog.setVisible(true);
                CodingIfc type = technicsSelectDialog.getCoding();
                if (type == null)
                {
                    return;
                }
                else
                {
                    technicsType = type;
                }
            }
          
        }
        
        //CCBegin SS12

      	if(technicsType!=null&&technicsType.getCodeContent().equals("�ɶ������ҵָ���鹤��"))
        {
      		    
      		    setTechnicsModeForCd(1,technicsType);
          		return;
          		
        }
       else {
    	   //CCEnd SS12
        //ʵ�������
        if (technicsMasterJPanel == null)
        {
        	//CCBegin SS11
        	if(cdTechnicsMasterJPanel!=null){
        		
        		cdTechnicsMasterJPanel.setVisible(false);
        	}
        	//CCEnd SS11
            technicsMasterJPanel = new TechnicsMasterJPanel(parentJFrame);
            add(technicsMasterJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
            
        }
        //CCBegin SS11
        else{
        	
       	 if(cdTechnicsMasterJPanel!=null){
        		
       		   cdTechnicsMasterJPanel.setVisible(false);
        	}
       }
        //CCEnd SS11
        

        mode = optionMode;
        //�˶�Ŀ���Ǳ�������ص�,���Ǻð췢
        if (optionMode == 2)
        {
            setNullMode();
            //���ѡ���˽ڵ㣬��Ϊ���¡��鿴�����Ϊ���ս��棻����Ϊ�½����ս���
        }
        try
        {
            if (optionMode != CREATE_MODE)
            {
                QMTechnicsInfo info = (QMTechnicsInfo) selectedObject;
                technicsMasterJPanel.setTechnics((QMFawTechnicsInfo) info);
                technicsMasterJPanel.setSelectedNode(selectedNode);
            }
            //����ģʽ,���ù�������
            else
            {
                technicsMasterJPanel.setTechnicsType(technicsType);
            }
            technicsMasterJPanel.setViewMode(optionMode);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        //�˶ε�Ŀ���Ǳ�������ص�
        if (optionMode != 1)
        {
            if (selectedObject instanceof QMTechnicsInfo)
            {
                technicsMasterJPanel.setVisible(true);
            }
        }
        else
        {
            technicsMasterJPanel.setVisible(true);
        }
        technicsMasterJPanel.validate();
        technicsMasterJPanel.updateUI();
        technicsMasterJPanel.requestFocus();
        refresh();
    
       
       }
    }
    //CCBegin SS12
    /**
     * ������ʾ���տ�ά�����
     * @param optionMode  ������ʾģʽ�����������¡��鿴�����Ϊ��
     */
    public void setTechnicsModeForCd(int optionMode,CodingIfc technicsType)
    {
    	
        //��������
//        CodingIfc technicsType = null;
        //���⣨2��20090106 xucy �޸�  �޸�ԭ�򣺱�����¹��򣨹�����֮����汣�ָ���״̬���ٵ���鿴�˽ڵ㣬
    	//���������Ƿ񱣴���µĹ�����ʾ��
        if(this.techStepIsShowing())
    	{
    		this.stepJPanel.setVisible(false);
    	}
    	if(this.techPaceIsShowing())
    	{
    		this.paceJPanel.setVisible(false);
    	}
        //����ģʽ��,���ѡ��Ĺ�������
        if (optionMode == CREATE_MODE)
        {
            //�������ѡ��Ĺ�������
//            CappTreeObject obj = ((TechnicsRegulationsMainJFrame)
//                                  parentJFrame).
//                                 getSelectedObject();
//            if (obj != null && obj instanceof ResourceTypeObject)
//            {
//                technicsType = (CodingIfc) ((ResourceTypeObject) obj).getObject();
//            }
            //����ѡ���
//            else
//            {
//                if (technicsSelectDialog == null)
//                {
//                    String title = QMMessage.getLocalizedMessage(RESOURCE,
//                            CappLMRB.TECHNICSTYPE, null);
//                    technicsSelectDialog = new SelectTechnicsTypeJDialog(
//                            parentJFrame);
//                }
//                technicsSelectDialog.setVisible(true);
//                CodingIfc type = technicsSelectDialog.getCoding();
//                if (type == null)
//                {
//                    return;
//                }
//                else
//                {
//                    technicsType = type;
//                }
//            }
            
            
           
        }
        //ʵ�������
        if (cdTechnicsMasterJPanel == null)
        {
            if(technicsMasterJPanel!=null){
        		
            	technicsMasterJPanel.setVisible(false);
        	}
        	cdTechnicsMasterJPanel = new CdTechnicsMasterJPanel(parentJFrame);
            add(cdTechnicsMasterJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
        }else{
        	
        	 if(technicsMasterJPanel!=null){
         		
             	technicsMasterJPanel.setVisible(false);
         	}
        }
        
        

        mode = optionMode;
        //�˶�Ŀ���Ǳ�������ص�,���Ǻð췢
        if (optionMode == 2)
        {
            setNullMode();
            //���ѡ���˽ڵ㣬��Ϊ���¡��鿴�����Ϊ���ս��棻����Ϊ�½����ս���
        }
        try
        {
            if (optionMode != CREATE_MODE)
            {
                QMTechnicsInfo info = (QMTechnicsInfo) selectedObject;
                cdTechnicsMasterJPanel.setTechnics((QMFawTechnicsInfo) info);
                cdTechnicsMasterJPanel.setSelectedNode(selectedNode);
            }
            //����ģʽ,���ù�������
            else
            {
            	cdTechnicsMasterJPanel.setTechnicsType(technicsType);
            }
            cdTechnicsMasterJPanel.setViewMode(optionMode);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        //�˶ε�Ŀ���Ǳ�������ص�
        if (optionMode != 1)
        {
            if (selectedObject instanceof QMTechnicsInfo)
            {
            	cdTechnicsMasterJPanel.setVisible(true);
            }
        }
        else
        {
        	cdTechnicsMasterJPanel.setVisible(true);
        }
        cdTechnicsMasterJPanel.validate();
        cdTechnicsMasterJPanel.updateUI();
        cdTechnicsMasterJPanel.requestFocus();
        refresh();
    }
    
    
    /**
     * ������ʾ���տ�ά�����
     * @param optionMode  ������ʾģʽ�����������¡��鿴�����Ϊ��
     */
    public void setTechnicsModeForCd(int optionMode)
    {
    	
        //��������
        CodingIfc technicsType = null;
        //���⣨2��20090106 xucy �޸�  �޸�ԭ�򣺱�����¹��򣨹�����֮����汣�ָ���״̬���ٵ���鿴�˽ڵ㣬
    	//���������Ƿ񱣴���µĹ�����ʾ��
        if(this.techStepIsShowing())
    	{
    		this.stepJPanel.setVisible(false);
    	}
    	if(this.techPaceIsShowing())
    	{
    		this.paceJPanel.setVisible(false);
    	}
        //����ģʽ��,���ѡ��Ĺ�������
        if (optionMode == CREATE_MODE)
        {
            //�������ѡ��Ĺ�������
            CappTreeObject obj = ((TechnicsRegulationsMainJFrame)
                                  parentJFrame).
                                 getSelectedObject();
            if (obj != null && obj instanceof ResourceTypeObject)
            {
                technicsType = (CodingIfc) ((ResourceTypeObject) obj).getObject();
            }
            //����ѡ���
            else
            {
                if (technicsSelectDialog == null)
                {
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.TECHNICSTYPE, null);
                    technicsSelectDialog = new SelectTechnicsTypeJDialog(
                            parentJFrame);
                }
                technicsSelectDialog.setVisible(true);
                CodingIfc type = technicsSelectDialog.getCoding();
                if (type == null)
                {
                    return;
                }
                else
                {
                    technicsType = type;
                }
            }
            
            
           
        }
        //ʵ�������
        if (cdTechnicsMasterJPanel == null)
        {
            if(technicsMasterJPanel!=null){
        		
            	technicsMasterJPanel.setVisible(false);
        	}
        	cdTechnicsMasterJPanel = new CdTechnicsMasterJPanel(parentJFrame);
            add(cdTechnicsMasterJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
        }else{
        	
        	 if(technicsMasterJPanel!=null){
         		
             	technicsMasterJPanel.setVisible(false);
         	}
        }
        
        

        mode = optionMode;
        //�˶�Ŀ���Ǳ�������ص�,���Ǻð췢
        if (optionMode == 2)
        {
            setNullMode();
            //���ѡ���˽ڵ㣬��Ϊ���¡��鿴�����Ϊ���ս��棻����Ϊ�½����ս���
        }
        try
        {
            if (optionMode != CREATE_MODE)
            {
                QMTechnicsInfo info = (QMTechnicsInfo) selectedObject;
                cdTechnicsMasterJPanel.setTechnics((QMFawTechnicsInfo) info);
                cdTechnicsMasterJPanel.setSelectedNode(selectedNode);
            }
            //����ģʽ,���ù�������
            else
            {
            	cdTechnicsMasterJPanel.setTechnicsType(technicsType);
            }
            cdTechnicsMasterJPanel.setViewMode(optionMode);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        //�˶ε�Ŀ���Ǳ�������ص�
        if (optionMode != 1)
        {
            if (selectedObject instanceof QMTechnicsInfo)
            {
            	cdTechnicsMasterJPanel.setVisible(true);
            }
        }
        else
        {
        	cdTechnicsMasterJPanel.setVisible(true);
        }
        cdTechnicsMasterJPanel.validate();
        cdTechnicsMasterJPanel.updateUI();
        cdTechnicsMasterJPanel.requestFocus();
        refresh();
    }
    
  //CCEnd SS12
    
    
/**
 * ���������ļ�
 * @param type
 * 
 * void
 * @throws Exception 
 */
    public boolean schedule(Vector type) throws Exception
    {
    //CCBegin SS9
    	if(getUserFromCompany().equals("ct")){
    		
    		if((selectedObject != null) && (selectedObject instanceof QMFawTechnicsInfo))
    		{
    			String temp = "";
    			HashMap map = new HashMap();
    			QMFawTechnicsInfo techInfo = (QMFawTechnicsInfo)selectedObject;
    			
    			if(techInfo.getWorkShop() != null)
    			{
    				if(techInfo.getWorkShop() instanceof CodingClassificationInfo)
    					temp = ((CodingClassificationInfo)techInfo.getWorkShop()).getCodeSort();
    				else if(techInfo.getWorkShop() instanceof CodingInfo)
    					temp = ((CodingInfo)techInfo.getWorkShop()).getCodeContent();
    				map.put("����", temp);
    			}else
    			{
    				map.put("����", "");
    			}
    			map.put("���ձ��", techInfo.getTechnicsNumber());
    			
    			temp=techInfo.getTechnicsType().getCodeContent();
    			map.put("����", temp);
    			
    			temp=techInfo.getVersionValue();
    			map.put("���", temp);
    			
    			if(techInfo.getExtendAttributes().findExtendAttModel("teamProductLine").getAttValue()!=null)
    			{
    				temp = techInfo.getExtendAttributes().findExtendAttModel("teamProductLine").getAttValue().toString();
    				map.put("������", temp);
    			}
    			
    			if(technicsMasterJPanel!=null)
    			{
    				PartUsageTechLinkJPanel partLink = technicsMasterJPanel.getPartPanel();
    				Vector vec = partLink.getAllLinks();
    				BinaryLinkIfc link;
    				PartUsageQMTechnicsLinkInfo partLinkInfo;
    				if(vec!=null&&vec.size()>0)
    				{
    					for(int i=0;i<vec.size();i++)
    					{
    						link = (BinaryLinkIfc)vec.get(i);
    						if(link instanceof PartUsageQMTechnicsLinkInfo)
    						{
    							partLinkInfo = (PartUsageQMTechnicsLinkInfo)link;
    							if(partLinkInfo.getMajorpartMark())
    							{
    								String partBsoid = partLinkInfo.getLeftBsoID();
    								QMPartInfo partInfo = (QMPartInfo)CappClientHelper.refresh(partBsoid);
    								if(partInfo!=null)
    								{
    									map.put("������", partInfo.getPartNumber());
    									map.put("�������", partInfo.getPartName());                                   
    								}
    							}
    						}
    					}
    				}
    				
    				TechUsageMaterialLinkJPanel materLink = technicsMasterJPanel.getMaterialPanel();
    				Vector materVec=materLink.getAllLinks();
    				QMTechnicsQMMaterialLinkInfo materialLinkInfo;
    				
    				for(int j=0;j<materVec.size();j++){
    					link = (BinaryLinkIfc)materVec.get(j);
    					
    					if(link instanceof QMTechnicsQMMaterialLinkInfo)
						{
    						materialLinkInfo = (QMTechnicsQMMaterialLinkInfo)link;
    						
    						if(materialLinkInfo.getMmMark()){
    							
    							
    							QMMaterialInfo materInfo = (QMMaterialInfo)CappClientHelper.refresh(materialLinkInfo.getRightBsoID());
    							if(materInfo!=null){
    								
    								map.put("���ϱ��", materInfo.getMaterialNumber());
    								map.put("��������", materInfo.getMaterialName());
    							}
    						}
    						
						}
    				}
    				
    			}
    			
    			
    			Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�
    			int year = c.get(Calendar.YEAR); 
    			int month = c.get(Calendar.MONTH)+1; 
    			int date = c.get(Calendar.DATE); 
    		    temp=String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(date);
    		    map.put("��������",temp);
    		    
    		    int size = type.size();
    			String[] arry = new String[size];
    			for(int i=0;i<size;i++)
    			{
    				arry[i] = (String)type.get(i);
    			}
    			return CappClientHelper.createExcel(techInfo.getBsoID(),arry,map);
    		    
    		}else
    		{
    			JOptionPane.showMessageDialog(null, "��ѡ���գ�", "",
    					JOptionPane.INFORMATION_MESSAGE);
    			return false;
    		}
    		
    	}
    	//CCEnd SS9
    	//CCBegin SS4
    	if(getUserFromCompany().equals("zczx")){
    		if((selectedObject != null) && (selectedObject instanceof QMFawTechnicsInfo))
    		{
    			HashMap map = new HashMap();
    			QMFawTechnicsInfo techInfo = (QMFawTechnicsInfo)selectedObject;
    			String temp = "";
    			temp = techInfo.getTechnicsNumber();
    			map.put("���ձ��", temp);
    			temp = techInfo.getTechnicsName();
    			map.put("��������", temp);
    			if(techInfo.getWorkShop() != null)
    			{
    				if(techInfo.getWorkShop() instanceof CodingClassificationInfo)
    					temp = ((CodingClassificationInfo)techInfo.getWorkShop()).getCodeSort();
    				else if(techInfo.getWorkShop() instanceof CodingInfo)
    					temp = ((CodingInfo)techInfo.getWorkShop()).getCodeContent();
    				map.put("����", temp);
    			}else
    			{
    				map.put("����", "");
    			}
    			if(technicsMasterJPanel!=null)
    			{
    				PartUsageTechLinkJPanel partLink = technicsMasterJPanel.getPartPanel();
    				Vector vec = partLink.getAllLinks();
    				BinaryLinkIfc link;
    				PartUsageQMTechnicsLinkInfo partLinkInfo;
    				if(vec!=null&&vec.size()>0)
    				{
    					for(int i=0;i<vec.size();i++)
    					{
    						link = (BinaryLinkIfc)vec.get(i);
    						if(link instanceof PartUsageQMTechnicsLinkInfo)
    						{
    							partLinkInfo = (PartUsageQMTechnicsLinkInfo)link;
    							if(partLinkInfo.getMajorpartMark())
    							{
    								String partBsoid = partLinkInfo.getLeftBsoID();
    								QMPartInfo partInfo = (QMPartInfo)CappClientHelper.refresh(partBsoid);
    								if(partInfo!=null)
    								{
    									map.put("���ͼ��", partInfo.getPartNumber());
    									map.put("�������", partInfo.getPartName());   
    								}
    							}
    						}
    					}
    				}
    			}
    			if(techInfo.getExtendAttributes().findExtendAttModel("piecePerMobile")!=null)
    			{
    				temp = techInfo.getExtendAttributes().findExtendAttModel("piecePerMobile").getAttValue().toString();
    				map.put("ÿ������", temp);
    			}
    			temp = techInfo.getVersionValue();
    			map.put("���", temp);
    			temp = CappClientHelper.getDate(techInfo.getCreateTime());
    			map.put("��������", temp);
    			temp = techInfo.getSeparableNumber();
    			map.put("�ܳ��ͺ�", temp);
    			int size = type.size();
    			String[] arry = new String[size];
    			for(int i=0;i<size;i++)
    			{
    				arry[i] = (String)type.get(i);
    			}
    			//                    String[] arry = {type};
    			return CappClientHelper.createExcel(techInfo.getBsoID(),arry,map);
    		}
    		else
    		{
    			JOptionPane.showMessageDialog(null, "��ѡ���գ�", "",
    					JOptionPane.INFORMATION_MESSAGE);
    			return false;
    		}
    	}
    	else{
    		//CCEnd SS4
    		if((selectedObject != null) && (selectedObject instanceof QMFawTechnicsInfo))
    		{
    			HashMap map = new HashMap();
    			QMFawTechnicsInfo techInfo = (QMFawTechnicsInfo)selectedObject;
    			String temp = "";
    			temp = techInfo.getFemaNum();
    			map.put("��Ʒƽ̨", temp);
    			if(techInfo.getWorkShop() != null)
    			{
    				if(techInfo.getWorkShop() instanceof CodingClassificationInfo)
    					temp = ((CodingClassificationInfo)techInfo.getWorkShop()).getCodeSort();
    				else if(techInfo.getWorkShop() instanceof CodingInfo)
    					temp = ((CodingInfo)techInfo.getWorkShop()).getCodeContent();
    				map.put("����", temp);
    			}else
    			{
    				map.put("����", "");
    			}
    			//CCBegin SS8
    			//if(techInfo.getExtendAttributes().findExtendAttModel("piecePerMobile")!=null)
    			if(techInfo.getExtendAttributes().findExtendAttModel("piecePerMobile")!=null&&techInfo.getExtendAttributes().findExtendAttModel("piecePerMobile").getAttValue()!=null)
    			//CCEnd SS8
    			{
    				temp = techInfo.getExtendAttributes().findExtendAttModel("piecePerMobile").getAttValue().toString();
    				map.put("ÿ������", temp);
    			}
    			if(technicsMasterJPanel!=null)
    			{
    				PartUsageTechLinkJPanel partLink = technicsMasterJPanel.getPartPanel();
    				Vector vec = partLink.getAllLinks();
    				BinaryLinkIfc link;
    				PartUsageQMTechnicsLinkInfo partLinkInfo;
    				if(vec!=null&&vec.size()>0)
    				{
    					for(int i=0;i<vec.size();i++)
    					{
    						link = (BinaryLinkIfc)vec.get(i);
    						if(link instanceof PartUsageQMTechnicsLinkInfo)
    						{
    							partLinkInfo = (PartUsageQMTechnicsLinkInfo)link;
    							if(partLinkInfo.getMajorpartMark())
    							{
    								String partBsoid = partLinkInfo.getLeftBsoID();
    								QMPartInfo partInfo = (QMPartInfo)CappClientHelper.refresh(partBsoid);
    								if(partInfo!=null)
    								{
    									map.put("�����", partInfo.getPartNumber());
    									map.put("�������", partInfo.getPartName());                                   
    								}
    							}
    						}
    					}
    				}
    			}
    			
    			//CCBegin SS7
    			temp = techInfo.getVersionValue();
    			map.put("���", temp);
    			//CCEnd SS7
    			
    			int size = type.size();
    			String[] arry = new String[size];
    			for(int i=0;i<size;i++)
    			{
    				arry[i] = (String)type.get(i);
    			}
    			//                String[] arry = {type};
    			return CappClientHelper.createExcel(techInfo.getBsoID(),arry,map);
    		}
    		else
    		{
    			JOptionPane.showMessageDialog(null, "��ѡ���գ�", "",
    					JOptionPane.INFORMATION_MESSAGE);
    			return false;
    		}
    	}
    }
  //CCEnd SS1
    /**
     * ������ʾ����ά�����
     * @param optionMode  ������ʾģʽ�����������¡��鿴��
     */
    public void setStepMode(int optionMode)
    {
    	stepJPanel = null;
    	//���⣨2��20081230 xucy �޸�  �޸�ԭ�򣺱�����¹��򣨹�����֮����汣�ָ���״̬���ٵ���鿴�˽ڵ㣬
    	//���������Ƿ񱣴���µĹ�����ʾ��
    	if(this.techStepIsShowing())
    	{
    		this.stepJPanel.setVisible(false);
    	}
    	if(this.techPaceIsShowing())
    	{
    		this.paceJPanel.setVisible(false);
    	}
        String stepType = null;
        if (optionMode == CREATE_MODE)
        {
            if (stepTypetable == null)
            {
                stepTypetable = new Hashtable();
            }
            String type = ((QMFawTechnicsInfo) selectedObject).
                          getTechnicsType().
                          getCodeContent();
            //CCBegin SS6
            if(!type.contains("���")){
            	//CCEnd SS6
            SelectStepTypeJDialog procedureSelectDialog;
            procedureSelectDialog = (SelectStepTypeJDialog) stepTypetable.
                                    get(type);
            if (procedureSelectDialog == null)
            {
                procedureSelectDialog = new SelectStepTypeJDialog(
                        parentJFrame, type);
                stepTypetable.put(type, procedureSelectDialog);
            }
            procedureSelectDialog.setVisible(true);
            stepType = procedureSelectDialog.getCodeContent();
            if (stepType == null)
            {
                return;
            }

            //CCBegin SS6
            }
            else{
            	stepType = "��ݹ���";
            }
          //CCEnd SS6
        }
        if (stepJPanel == null)
        {
//        	CCBegin SS2
        	if (selectedObject != null)
            {
                //���ѡ���˹���ڵ㣬��Ϊ���¡��鿴�������
        		if(stepType == null)
                if (selectedObject instanceof QMProcedureInfo)
                {
                	QMProcedureInfo info = (QMProcedureInfo) selectedObject;
                	stepType = info.getTechnicsType().getCodeContent();
                }else if(selectedObject instanceof QMFawTechnicsInfo){
                	QMFawTechnicsInfo ti = (QMFawTechnicsInfo) selectedObject;
                	stepType = ti.getTechnicsType().getCodeContent();
                }
                	if(stepType.contains("������")){
                		System.out.println("������");
                		stepJPanel = new TechnicsStepJPanelForBSX(parentJFrame);
                	}
                	//CCBegin SS3
                	
                	else if(stepType.contains("���")){
                		System.out.println("���");
                		stepJPanel = new TechnicsStepJPanelForZC(parentJFrame);
                	}
                	
                	//CCEnd SS3
                	
                	//CCBegin SS11
                	else if(stepType.contains("�ɶ������ҵָ���鹤��")){
                		System.out.println("�ɶ������ҵָ���鹤��");
                		stepJPanel = new CdTechnicsStepJPanel(parentJFrame);
                	}
                	//CCEnd SS11
                	
             	
                	
                	else{
                		System.out.println("���");
                		stepJPanel = new TechnicsStepJPanel(parentJFrame);
                	}
//                }
//                //���ѡ���˹��սڵ㣬��Ϊ�½��������
//                else if (selectedObject instanceof QMFawTechnicsInfo)
//                {
//                	
//                }
            }
//           CCEnd SS2
            add(stepJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
        }

        mode = optionMode;
        if (optionMode == 2)
        {
            setNullMode();
        }
        if (selectedObject != null)
        {
            //���ѡ���˹���ڵ㣬��Ϊ���¡��鿴�������
            if (selectedObject instanceof QMProcedureInfo)
            {
            	
                QMProcedureInfo info = (QMProcedureInfo) selectedObject;
                
                if (info.getIsProcedure())
                {
                    stepJPanel.setProcedure(info);
                    stepJPanel.setSelectedNode(selectedNode);
                    try
                    {
                        stepJPanel.setViewMode(optionMode);
                    }
                    catch (PropertyVetoException ex)
                    {
                        ex.printStackTrace();
                    }
                    if (selectedObject instanceof QMProcedureInfo && (
                            (QMProcedureInfo) selectedObject).getIsProcedure())
                    {
                        stepJPanel.setVisible(true);
                    }
                }
            }
            //���ѡ���˹��սڵ㣬��Ϊ�½��������
            else if (selectedObject instanceof QMFawTechnicsInfo)
            {
                stepJPanel.setStepType(stepType);
                stepJPanel.setSelectedNode(selectedNode);
                try
                {
                    stepJPanel.setViewMode(optionMode);
                }
                catch (PropertyVetoException ex)
                {
                    ex.printStackTrace();
                }
                if (selectedObject instanceof QMFawTechnicsInfo)
                {
                    stepJPanel.setVisible(true);

                }
            }

        }
        validate();
        stepJPanel.requestFocus();
        refresh();

    }


    /**
     * ������ʾ����ά�����
     * @param optionMode ������ʾģʽ�����������¡��鿴��
     */
    public void setPaceMode(int optionMode)
    {
    	//���⣨2��20090106 xucy �޸�  �޸�ԭ�򣺱�����¹��򣨹�����֮����汣�ָ���״̬���ٵ���鿴�˽ڵ㣬
    	//���������Ƿ񱣴���µĹ�����ʾ��
    	if(this.techStepIsShowing())
    	{
    		this.stepJPanel.setVisible(false);
    	}
    	if(this.techPaceIsShowing())
    	{
    		this.paceJPanel.setVisible(false);
    	}
        if (paceJPanel == null)
        {
//        CCBegin SS2
        	String type = ((QMProcedureInfo) selectedObject).
            getTechnicsType().
            getCodeContent();
        	if(type.contains("������")){
        		paceJPanel = new TechnicsPaceJPanelForBSX(parentJFrame);
        	}
        	//CCBegin SS3
        	//CCBegin SS5
        	//if(type.contains("���")){
        	else if(type.contains("���")){
        	//CCEnd SS5
        		paceJPanel = new TechnicsPaceJPanelForZC(parentJFrame);
        	}
        	//CCEnd SS3
        	else{
            paceJPanel = new TechnicsPaceJPanel(parentJFrame);
        	}
//        CCEnd	SS2
            add(paceJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));

        }

        mode = optionMode;
        if (optionMode == 2)
        {
            setNullMode();
        }
        if (selectedObject != null)
        {
            QMProcedureInfo info = (QMProcedureInfo) selectedObject;
            if (optionMode != 1)
            {
                paceJPanel.setProcedure(info);
            }
            paceJPanel.setSelectedNode(selectedNode);
            try
            {
                paceJPanel.setViewMode(optionMode);
            }
            catch (PropertyVetoException ex)
            {
                ex.printStackTrace();
                return;
            }
            if (optionMode != 1)
            {
                if (selectedObject instanceof QMProcedureInfo && !
                    ((QMProcedureInfo) selectedObject).getIsProcedure())
                {
                    paceJPanel.setVisible(true);
                }
            }
            else
            {
                paceJPanel.setVisible(true);
            }
        }

        refresh();
        validate();
    }


    /**
     * ������ʾ���͹�������Ϣ���
     * �������������ɵ��͹��ղ���
     */
    public void setModelTechMode()
    {

        if (modelTechJPanel == null)
        {
            modelTechJPanel = new ModelTechMasterJPanel(parentJFrame);
            add(modelTechJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
        }

        if (selectedObject != null)
        {
            //����һ����ѡ�����չ��������ͬ�ĵ��͹��ա��൱�ڶԹ��չ�̵ĸ��Ʋ���
            QMTechnicsInfo info = (QMTechnicsInfo) selectedObject;
            modelTechJPanel.setQMTechnics(info);
            try
            {
                modelTechJPanel.setViewMode(4);
            }
            catch (PropertyVetoException ex)
            {
                ex.printStackTrace();
            }

            modelTechJPanel.validate();
            modelTechJPanel.requestFocus();
            modelTechJPanel.setVisible(true);

        }
        //refresh();
         updateUI();//CR1
    }


    /**
     * ������ʾ���͹�������Ϣ���
     * �������������ɵ��͹������
     */
    public void setModelStepMode()
    {

        if (modelStepJPanel == null)
        {
            modelStepJPanel = new ModelProcedureMasterJPanel(parentJFrame);
            add(modelStepJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));

        }

        if (selectedObject != null)
        {
            QMProcedureInfo info = (QMProcedureInfo) selectedObject;
            modelStepJPanel.setQMProcedure(info);
            modelStepJPanel.setTechNode(selectedNode.getP());
            try
            {
                modelStepJPanel.setViewMode(4);
            }
            catch (PropertyVetoException ex)
            {
                ex.printStackTrace();
            }
            modelStepJPanel.validate();
            modelStepJPanel.requestFocus();
            modelStepJPanel.setVisible(true);
        }
        refresh();
    }


//    CCBegin SS10
    /**
     * ������ʾ���غϲ����չ�����
     */
    public void setCTUniteTechnicsJPanel()
    {
        mode = 1;
    	//this.setTechnicsMode(1);
        CodingIfc technicsType = null;
        QMFawTechnicsInfo tempTechnics = null;
        CappTreeObject obj = ((TechnicsRegulationsMainJFrame) parentJFrame).
                             getSelectedObject();

//        if (obj != null && obj instanceof TechnicsTreeObject)
//        {
//        	
//        	tempTechnics = (QMFawTechnicsInfo) ((TechnicsTreeObject)
//                                        obj).getObject();
//        	technicsType = tempTechnics.getTechnicsType();
//        	System.out.println("AAAAAAAAAA-----1--------select------"+technicsType);
//        }
//        else
//        {
        
        	Class[] paraclass =
                {
                String.class, String.class,String.class};
        Object[] paraobj =
                {
                "27", "��������","��������Ϣ"};
        try{
        	technicsType = (CodingIfc) CappClientHelper.useServiceMethod(
                "CodingManageService", "findCodingByCode", paraclass, paraobj);
        	//System.out.println("AAAAAAAAAAA==========="+technicsType);
        }catch(Exception exc ){
        	exc.printStackTrace();
        }
            
            if (technicsType == null)
            {
                return;
            }

        
        //System.out.println("BBBBBBBBBBBBBBBB============"+uniteTechnicsJPanelForCT);
        
        if (uniteTechnicsJPanelForCT == null)
        {

        	uniteTechnicsJPanelForCT = new UniteTechnicsReguJPanelForCT(parentJFrame);
            add(uniteTechnicsJPanelForCT,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
        }

        uniteTechnicsJPanelForCT.clear();
        uniteTechnicsJPanelForCT.setTechnicsType(technicsType);
        refresh();
        try
        {
        	uniteTechnicsJPanelForCT.setRelatedTechnics();
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(parentJFrame, ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        uniteTechnicsJPanelForCT.validate();
        uniteTechnicsJPanelForCT.requestFocus();
        uniteTechnicsJPanelForCT.setVisible(true);
        refresh();
    }
//CCEnd SS10
    
    /**
     * ������ʾ�ϲ����չ�����
     */
    public void setUniteTechnicsJPanel()
    {
        mode = 1;
        CodingIfc technicsType = null;

        CappTreeObject obj = ((TechnicsRegulationsMainJFrame) parentJFrame).
                             getSelectedObject();

        if (obj != null && obj instanceof ResourceTypeObject)
        {
            technicsType = (CodingIfc) ((ResourceTypeObject)
                                        obj).getObject();
        }
        else
        {
            if (technicsSelectDialog == null)
            {
                String title = QMMessage.getLocalizedMessage(RESOURCE,
                        CappLMRB.TECHNICSTYPE, null);
                technicsSelectDialog = new SelectTechnicsTypeJDialog(
                        parentJFrame);
            }
            technicsSelectDialog.setVisible(true);
            technicsType = technicsSelectDialog.getCoding();
            if (technicsType == null)
            {
                return;
            }

        }
        if (uniteTechnicsJPanel == null)
        {
            uniteTechnicsJPanel = new UniteTechnicsReguJPanel(parentJFrame);
            add(uniteTechnicsJPanel,
                new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER,
                                       GridBagConstraints.REMAINDER, 1.0, 1.0
                                       , GridBagConstraints.CENTER,
                                       GridBagConstraints.BOTH,
                                       new Insets(0, 0, 0, 0), 0, 0));
        }

        uniteTechnicsJPanel.clear();
        uniteTechnicsJPanel.setTechnicsType(technicsType);
        try
        {
            uniteTechnicsJPanel.setRelatedTechnics();
        }
        catch (QMException ex)
        {
            String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
            JOptionPane.showMessageDialog(parentJFrame, ex.getClientMessage(),
                                          title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

        uniteTechnicsJPanel.validate();
        uniteTechnicsJPanel.requestFocus();
        uniteTechnicsJPanel.setVisible(true);
        refresh();
    }
    

    /**
     * ˢ�½���
     */
    private void refresh()
    {
        doLayout();
        repaint();
    }


    /**
     * ���ñ����Ϊ�ա�
     * @return ��ǰ�����Ƿ�ִ���˱�����������ִ���˱��棬�򷵻��档
     */
    public boolean setNullMode()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsContentJPanel.setNullMode() begin...");
        }
        boolean isSave = true;
        if (technicsMasterJPanel != null && technicsMasterJPanel.isShowing())
        {
            isSave = technicsMasterJPanel.quit();
        }
        //CCBegin SS11
        else if(cdTechnicsMasterJPanel != null && cdTechnicsMasterJPanel.isShowing()){
        	
        	 isSave = cdTechnicsMasterJPanel.quit();
        }
        //CCEnd SS11
        else if (uniteTechnicsJPanel != null && uniteTechnicsJPanel.isShowing())
        {
            isSave = uniteTechnicsJPanel.quit();
        }
        else if (stepJPanel != null && stepJPanel.isShowing())
        {
            isSave = stepJPanel.quit();
        }
        else if (paceJPanel != null && paceJPanel.isShowing())
        {
            isSave = paceJPanel.quit();
        }
        else if (modelStepJPanel != null && modelStepJPanel.isShowing())
        {
            isSave = modelStepJPanel.quit();
        }
        else if (modelTechJPanel != null && modelTechJPanel.isShowing())
        {
            isSave = modelTechJPanel.quit();
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsContentJPanel.setNullMode() end...return: " +
                    isSave);
        }
        return isSave;
    }

    
    //���⣨1��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤�� begin
	public void setStepJpanel(boolean flag)
	{
		stepJPanel.setVisible(flag);
	}
	
	public void setPaceJpanel(boolean flag)
	{
		paceJPanel.setVisible(flag);
	}
	//���⣨1��20081226 xucy  �޸�ԭ���Ż�����ʱ���湤�� begin
	
	
   /**
     * ��ָ������Դ������ӵ���Ӧ�����б���
     * @param info ��Դ�����豸����װ�����ϣ����ն���
     */
    public void addObjectToTable(BaseValueInfo[] info)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.TechnicsContentJPanel.addObjectToTable() begin...");
        }
        if (technicsMasterJPanel != null && technicsMasterJPanel.isShowing() &&
            technicsMasterJPanel.getMode() != 2)
        {
            technicsMasterJPanel.addObjectToTable(info);
        }
        //CCBegin SS11
        
        else if (cdTechnicsMasterJPanel != null && cdTechnicsMasterJPanel.isShowing() &&
        		cdTechnicsMasterJPanel.getMode() != 2)
            {
            	cdTechnicsMasterJPanel.addObjectToTable(info);
            }
        //CCEnd SS11
        
        else if (stepJPanel != null && stepJPanel.isShowing() &&
                 stepJPanel.getMode() != 2)
        {
            stepJPanel.addObjectToTable(info);
        }
        else if (paceJPanel != null && paceJPanel.isShowing() &&
                 paceJPanel.getMode() != 2)
        {
            paceJPanel.addObjectToTable(info);
        }
        else if (uniteTechnicsJPanel != null && uniteTechnicsJPanel.isShowing())
        {
            uniteTechnicsJPanel.addObjectToTable(info[0]);
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.TechnicsContentJPanel.addObjectToTable() end...return is void");
        }
    }


    /**
     * ����Ϣ����Ƿ�ɼ�
     * @return boolean ����Ϣ����Ƿ�ɼ�
     */
    public boolean techMasterIsShowing()
    {
        if (technicsMasterJPanel != null && technicsMasterJPanel.isShowing())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    //CCBegin SS11
    /**
     * ����Ϣ����Ƿ�ɼ�
     * @return boolean ����Ϣ����Ƿ�ɼ�
     */
    public boolean techMasterIsShowingForCD()
    {
        if (cdTechnicsMasterJPanel != null && cdTechnicsMasterJPanel.isShowing())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
//CCEnd SS11

    /**
     * ��������Ƿ�ɼ�
     * @return boolean ��������Ƿ�ɼ�
     */
    public boolean techStepIsShowing()
    {
        if (stepJPanel != null && stepJPanel.isShowing())
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * ��������Ƿ�ɼ�
     * @return boolean ��������Ƿ�ɼ�
     */
    public boolean techPaceIsShowing()
    {
        if (paceJPanel != null && paceJPanel.isShowing())
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * �ϲ�����Ƿ���ʾ
     * @return boolean �ϲ�����Ƿ���ʾ
     */
    public boolean uniteTechnicsJPanelIsShowing()
    {
        if (uniteTechnicsJPanel != null && uniteTechnicsJPanel.isShowing())
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * �������Ϣ���
     * @return TechnicsMasterJPanel ����Ϣ���
     */
    public TechnicsMasterJPanel getTechnicsMasterJPanel()
    {
        return technicsMasterJPanel;
    }

    //CCBegin SS11
    /**
     * �������Ϣ���
     * @return TechnicsMasterJPanel ����Ϣ���
     */
    public TechnicsMasterJPanel getTechnicsMasterJPanelForCD()
    {
        return cdTechnicsMasterJPanel;
    }
//CCEnd SS11
    /**
     * ���ù�����ѡ��Ĺ�������
     * @param code CodingIfc ��������
     */
    public void setResourceTypeObject(CodingIfc code)
    {
        technicsType = code;
    }


    /**
     * ���ģʽ(�鿴,����,�½�)
     * @return int ģʽ
     */
    public int getMode()
    {
        return mode;
    }
    
  //CCBegin SS4
    /**
     * �ж��û�������˾
     * @return String ����û�������˾
     * @author wenl
     */
    public static String getUserFromCompany() throws QMException {
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
  //CCEnd SS4
}
