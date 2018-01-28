package com.faw_qm.cappclients.capp.view;
//SS1 ��ݹ����Զ���� 2014-1-13 ����
//SS2 ���ع����Զ���� 2014-10-20 ����
//SS3 �ɶ�TS16949�Զ���� guoxiaoliang 2016-7-12 
//SS4 ���ճɶ��������޸�TS16949�Զ���� guoxiaoliang 2016-11-30
//SS5 �޸�FMEA���ȱ�١�-������ liuyuzhu 2017-06-09

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.cappclients.util.CappTextField;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.cappclients.util.CappTextField;;

/**
 * <p>Title: ���տ�ά������е�TS16949�������</p>
 * <p>Description: ά��TS16949���ԡ�</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author  Ѧ��
 * @version 1.0
 */
public class MasterTS16949Panel extends ParentJPanel
{

    /**
     * ��������ͼ���
     */
    JLabel processFlowNumjLabel = new JLabel();
    //JTextField processFlowNumjTextField = new JTextField();
    private CappTextField processFlowNumjTextField;
    
    JLabel processFlowNumDispJLabel = new JLabel();
    /**
     * ���Ƽƻ����
     */
    JLabel controlPlanNumjLabel = new JLabel();

    //JTextField controlPlanNumjTextField = new JTextField();
    private CappTextField controlPlanNumjTextField;
     JLabel controlPlanNumDispJLabel = new JLabel();
    /**
     *  ����FMEA���
     */
    JLabel femaNumjLabel = new JLabel();
    private CappTextField femaNumjTextField;
    //JTextField femaNumjTextField = new JTextField();
    JLabel femaNumDispJLabel = new JLabel();
    /**
     * ��ҵָ�����
     */
     JLabel taskInstructNumjLabel = new JLabel();
     private CappTextField taskInstructNumjTextField;
    //JTextField taskInstructNumjTextField = new JTextField();
    JLabel taskInstructNumDispJLabel = new JLabel();
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    
    //add by wangh on 20070510
    private JFrame parentJFrame;
    
    public MasterTS16949Panel()
    {
        try
        {
            jbInit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    //CCBegin SS1
    /**
     * �ж��û�������˾
     * @return String ����û�������˾
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
    private void jbInit()
            throws Exception
    {
    	//add by wangh on 20070510
    	String procedureNumDisp = QMMessage.getLocalizedMessage(RESOURCE,
                                  "processFlowNum", null);
    	
    	String controlPlanNumDisp = QMMessage.getLocalizedMessage(RESOURCE,
                                  "controlPlanNum", null);
        
        String femaNumDisp = QMMessage.getLocalizedMessage(RESOURCE,
                                  "femaNum", null);
        
        String taskInstructNumDisp = QMMessage.getLocalizedMessage(RESOURCE,
                                  "taskInstructNum", null);
//      CCBegin by leixiao 2009-6-18 ԭ�򣺽��ϵͳ���� ,ts16949��ų���20->50
        processFlowNumjTextField=new CappTextField(parentJFrame, procedureNumDisp, 50, true);
        controlPlanNumjTextField=new CappTextField(parentJFrame, controlPlanNumDisp, 50, true);
        femaNumjTextField=new CappTextField(parentJFrame, femaNumDisp, 50, true);
        taskInstructNumjTextField=new CappTextField(parentJFrame, taskInstructNumDisp, 50, true);
//      CCEnd by leixiao 2009-6-18 ԭ�򣺽��ϵͳ���� ,ts16949��ų���20->50
        processFlowNumjLabel.setMaximumSize(new Dimension(84, 18));
        processFlowNumjLabel.setToolTipText("");
        processFlowNumjLabel.setText("��������ͼ���");
        processFlowNumjTextField.setMinimumSize(new Dimension(6, 23));
        processFlowNumjTextField.setText("");
        controlPlanNumjLabel.setText("���Ƽƻ����");
        this.setLayout(gridBagLayout1);
        femaNumjLabel.setText("����FMEA���");
        taskInstructNumjLabel.setText("��ҵָ�����");
        controlPlanNumjTextField.setText("");
        femaNumjTextField.setText("");
        taskInstructNumjTextField.setText("");
        this.add(processFlowNumjLabel,
                 new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
                                        , GridBagConstraints.EAST,
                                        GridBagConstraints.NONE,
                                        new Insets(8, 8, 4, 8), 0, 0));
        this.add(processFlowNumjTextField,
                  new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                         ,GridBagConstraints.WEST,
                                         GridBagConstraints.HORIZONTAL,
                                         new Insets(5, 0, 0, 8), 0, 0));
        this.add(processFlowNumDispJLabel,
                new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
                                       , GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(8, 8, 4, 8), 0, 0));
        this.add(controlPlanNumjLabel,
                 new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
                                        , GridBagConstraints.EAST,
                                        GridBagConstraints.NONE,
                                        new Insets(8, 8, 4, 8), 0, 0));
        this.add(controlPlanNumjTextField,
                  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                         ,GridBagConstraints.WEST,
                                         GridBagConstraints.HORIZONTAL,
                                         new Insets(5, 0, 0, 8), 116, 0));
        this.add(controlPlanNumDispJLabel,
                  new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
                                         ,GridBagConstraints.WEST,
                                         GridBagConstraints.HORIZONTAL,
                                         new Insets(8, 8, 4, 8), 0, 0));

        this.add(femaNumjLabel,
        		new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
                                       ,GridBagConstraints.EAST,
                                       GridBagConstraints.NONE,
                                       new Insets(8, 8, 4, 8), 0, 0));
        this.add(femaNumjTextField,
        		new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                       ,GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(5, 0, 0, 8), 0, 0));
        this.add(femaNumDispJLabel,
        		new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
                                       ,GridBagConstraints.WEST,
                                       GridBagConstraints.HORIZONTAL,
                                       new Insets(8, 8, 4, 8), 0, 0));
        //CCBegin SS1
      //CCBegin SS2
        if(getUserFromCompany().equals("zczx")){
        	
        }else if(getUserFromCompany().equals("ct")){
        	//����û����ҵָ����
        }else{
            this.add(taskInstructNumjLabel,
                    new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
                                           , GridBagConstraints.EAST,
                                           GridBagConstraints.NONE,
                                           new Insets(8, 8, 4, 8), 0, 0));
        	 this.add(taskInstructNumjTextField,
                     new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                                            , GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(5, 0, 0, 8), 0, 0));
            this.add(taskInstructNumDispJLabel,
                   new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                                          ,GridBagConstraints.WEST,
                                          GridBagConstraints.HORIZONTAL,
                                          new Insets(8, 8, 4, 8), 0, 0));
        }
      //CCEnd SS2
        //CCEnd SS1
     
       
    }

    /**
     * ��ս�����Ϣ
     */
    public void clear()
    {
        processFlowNumjTextField.setText("");
        processFlowNumDispJLabel.setText("");
        controlPlanNumjTextField.setText("");
        controlPlanNumDispJLabel.setText("");
        femaNumjTextField.setText("");
        femaNumDispJLabel.setText("");
        taskInstructNumjTextField.setText("");
        taskInstructNumDispJLabel.setText("");

    }

    /**
     * ��������Ϣ�ĺϷ���
     */
    public boolean check()
    {
//      CCBeginby leixiao 2009-7-30 ԭ��ȥ���Ա�������ַ�������
//    	return processFlowNumjTextField.check()&&controlPlanNumjTextField.check()&&
//    	        femaNumjTextField.check()&&taskInstructNumjTextField.check();
//      CCEndby leixiao 2009-7-30 ԭ��ȥ���Ա�������ַ�������
    	return true;
    }

    /**
     * ���ն�������������
     * @param technics QMTechnicsIfc
     */
    public void commitAttributes(QMTechnicsIfc technics)
    {
        if (this.processFlowNumjTextField.getText() != null)
            technics.setProcessFlowNum(processFlowNumjTextField.getText().trim());
        if (this.controlPlanNumjTextField.getText() != null)
            technics.setControlPlanNum(controlPlanNumjTextField.getText().trim());

        if (this.femaNumjTextField.getText() != null)
            technics.setFemaNum(femaNumjTextField.getText().trim());
        //CCBegin SS1
        try {
        	//CCBegin SS2
			if(getUserFromCompany().equals("zczx")){
		    
			}else if(getUserFromCompany().equals("ct")){
				
			}else{
				 if (this.taskInstructNumjTextField.getText() != null)
			            technics.setTaskInstructNum(taskInstructNumjTextField.getText().
			                                        trim());
			}
			//CCEnd SS2
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS1
     

    }
    
//  CCBegin by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
    public void commitAttributes(QMTechnicsIfc technics,String state)
    {   			
    	
        if (this.processFlowNumjTextField.getText() != null){
        	
            technics.setProcessFlowNum(state+processFlowNumjTextField.getText().trim());
        }
        if (this.controlPlanNumjTextField.getText() != null)
            technics.setControlPlanNum(state+controlPlanNumjTextField.getText().trim());

        if (this.femaNumjTextField.getText() != null)
            technics.setFemaNum(state+femaNumjTextField.getText().trim());
        //CCBegin SS1
        try {
        	//CCBegin SS2
			if(getUserFromCompany().equals("zczx")){
		      
			}else if(getUserFromCompany().equals("ct")){
				
			}else{
				  if (this.taskInstructNumjTextField.getText() != null)
			            technics.setTaskInstructNum(state+taskInstructNumjTextField.getText().
			                                        trim());
			}
			//CCEnd SS2
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS1


    }
//  CCEnd by leixiao 2008-10-27 ԭ�򣺽��ϵͳ���� 
    
    /**
     * ���ò鿴ģʽ
     */
    public void setViewMode(QMTechnicsIfc technics)
    {
        //CCBegin SS1
        processFlowNumDispJLabel.setVisible(true);
        controlPlanNumDispJLabel.setVisible(true);
        femaNumDispJLabel.setVisible(true);
        processFlowNumjTextField.setVisible(false);
        controlPlanNumjTextField.setVisible(false);
        femaNumjTextField.setVisible(false);
        processFlowNumDispJLabel.setText(technics.getProcessFlowNum());
        controlPlanNumDispJLabel.setText(technics.getControlPlanNum());
        femaNumDispJLabel.setText(technics.getFemaNum());
        try {
        	//CCBegin SS2
			if(getUserFromCompany().equals("zczx")){
			
			}else if(getUserFromCompany().equals("ct")){
				
			}else{
		        taskInstructNumDispJLabel.setVisible(true);
		        taskInstructNumjTextField.setVisible(false);
		        taskInstructNumDispJLabel.setText(technics.getTaskInstructNum());
			}
			//CCEnd SS2
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS1

    }

    /**
     * ���ø���ģʽ
     * @param technics QMTechnicsIfc ��������Ϣ����
     */
    public void setUpdateMode(QMTechnicsIfc technics)
    {
    	//CCBegin SS1
        processFlowNumDispJLabel.setVisible(false);
        controlPlanNumDispJLabel.setVisible(false);
        femaNumDispJLabel.setVisible(false);
        processFlowNumjTextField.setVisible(true);
        controlPlanNumjTextField.setVisible(true);
        femaNumjTextField.setVisible(true);
        processFlowNumjTextField.setText(technics.getProcessFlowNum());
        controlPlanNumjTextField.setText(technics.getControlPlanNum());
        femaNumjTextField.setText(technics.getFemaNum());
        try {
        	//CCBegin SS2
			if(getUserFromCompany().equals("zczx")){

			}else if(getUserFromCompany().equals("ct")){
				
			}else{
		        taskInstructNumDispJLabel.setVisible(false);
		        taskInstructNumjTextField.setVisible(true);
		        taskInstructNumjTextField.setText(technics.getTaskInstructNum());
			}
			//CCEnd SS2
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS1

    }

    /**
     * ���ô���ģʽ
     */
    public void setCreateMode()
    {
    	//CCBegin SS1
        processFlowNumDispJLabel.setVisible(false);
        controlPlanNumDispJLabel.setVisible(false);
        femaNumDispJLabel.setVisible(false);

        processFlowNumjTextField.setVisible(true);
        controlPlanNumjTextField.setVisible(true);
        femaNumjTextField.setVisible(true);

        try {
        	//CCBegin��SS2
			if(getUserFromCompany().equals("zczx")){

			}else if(getUserFromCompany().equals("ct")){
				
			}else{
		        taskInstructNumDispJLabel.setVisible(false);
		        taskInstructNumjTextField.setVisible(true);	
			}
			//CCEnd SS2
		} catch (QMException e) {
			e.printStackTrace();
		}
        //CCEnd SS1

    }

    /**
     * �������Ϊģʽ
     * @param technics QMTechnicsIfc
     */
    public void setSaveAsMode(QMTechnicsIfc technics)
    {
        setUpdateMode(technics);
    }

    /**
     * add by lil in 20080903
     * @param techinicsNumber String
     * @throws QMException 
     */
    public void setAttribute(String techinicsNumber)  {
//    	CCBegin by leixiao 2009-7-17 ԭ�򣺽����������,TS16949�ı�Ų����汾
    	if(techinicsNumber.indexOf("_")!=-1){
    		techinicsNumber=techinicsNumber.substring(0,techinicsNumber.lastIndexOf("_"));
    	}
//    	CCEnd by leixiao 2009-7-17 ԭ�򣺽����������
  
    	    processFlowNumjTextField.setText("G-"+techinicsNumber);
    	    controlPlanNumjTextField.setText("K-"+techinicsNumber);
    	    femaNumjTextField.setText("F-"+techinicsNumber);
    	    taskInstructNumjTextField.setText("Z-"+techinicsNumber); 
    	    


    }
    //CCBegin SS1
    /**
     * ��������Զ����
     * @param techinicsNumber String
     */
    public void setAttributeNumber(String techinicsNumber,String type) {
    	if(type.equals("��ݻ��ӹ���")){
    		processFlowNumjTextField.setText("L-"+techinicsNumber);
     	    controlPlanNumjTextField.setText("K-"+techinicsNumber);
     	    femaNumjTextField.setText("P-"+techinicsNumber);
     	   // taskInstructNumjTextField.setText("Z-"+techinicsNumber); 
    		
    	}else if (type.equals("����ȴ�����")){
    		processFlowNumjTextField.setText("LR-"+techinicsNumber);
     	    controlPlanNumjTextField.setText("KR-"+techinicsNumber);
     	    femaNumjTextField.setText("PR-"+techinicsNumber);
     	   // taskInstructNumjTextField.setText("Z-"+techinicsNumber); 
    		
    	}
		
    }
    //CCEnd SS1
    
    //CCBegin SS3
     /**
     * �ɶ������Զ����
     * @param techinicsNumber String
     */
    public void setAttributeCD(String partNum,String technicsType) {
    	 
    	//CCBegin SS4
    	if(partNum==null)
    		partNum="";
    	
    	if(!technicsType.equals("�ɶ������ҵָ���鹤��")){
//    	  if(partNum!=null){
    		  
    		  //CCBegin SS5
    		  if(technicsType.equals("�ɶ�����Ϳװ����")){
    			  
    			    controlPlanNumjTextField.setText("K-TC-"+partNum);
    			    femaNumjTextField.setText("F-TC-"+partNum);
    			    processFlowNumjTextField.setText("G-TC-"+partNum);
    			    taskInstructNumjTextField.setText("ZY-TC-"+partNum); 
    	     	    
    	     	   
    		  }else if(technicsType.equals("�ɶ������ѹ����")){
    			  
  			    controlPlanNumjTextField.setText("K-CY-"+partNum);
  			    femaNumjTextField.setText("F-CY-"+partNum);
  			    processFlowNumjTextField.setText("G-CY-"+partNum);
  			  taskInstructNumjTextField.setText("ZY-CY-"+partNum); 
  	     	    
  	     	   
  		      }else if(technicsType.equals("�ɶ����ܳ�ѹ����")){
    			  
    			    controlPlanNumjTextField.setText("K-CY-"+partNum);
    			    femaNumjTextField.setText("F-CY-"+partNum);
    			    processFlowNumjTextField.setText("G-CY-"+partNum);
    			    taskInstructNumjTextField.setText("ZY-CY-"+partNum); 
    	     	    
    	     	   
    		  }else if(technicsType.equals("�ɶ����⹤��")){
    			  
  			    controlPlanNumjTextField.setText("K-TS-"+partNum);
  			    femaNumjTextField.setText("F-TS-"+partNum);
  			    processFlowNumjTextField.setText("G-TS-"+partNum);
  			  taskInstructNumjTextField.setText("ZY-TS-"+partNum); 
  	     	    
  	     	   
  		      }else if(technicsType.equals("�ɶ���ʻ�Һ�װ����")){
    			  
    			    controlPlanNumjTextField.setText("K-HZ-"+partNum);
    			    femaNumjTextField.setText("F-HZ-"+partNum);
    			    processFlowNumjTextField.setText("G-HZ-"+partNum);
    			    taskInstructNumjTextField.setText("ZY-HZ-"+partNum); 
    	     	   
    		 }else if(technicsType.equals("�ɶ����ӹ���")){
   			  
 			    controlPlanNumjTextField.setText("K-JJ-"+partNum);
 			    femaNumjTextField.setText("F-JJ-"+partNum);
 			    processFlowNumjTextField.setText("G-JJ-"+partNum);
 			   taskInstructNumjTextField.setText("ZY-JJ-"+partNum); 
 	     	   
 		   }else if(technicsType.equals("�ɶ��꺸����")){
    			  
			    controlPlanNumjTextField.setText("K-ZH-"+partNum);
			    femaNumjTextField.setText("F-ZH-"+partNum);
			    processFlowNumjTextField.setText("G-ZH-"+partNum);
			    taskInstructNumjTextField.setText("ZY-ZH-"+partNum); 
	     	   
		   }else if(technicsType.equals("�ɶ�����װ�乤��")){
 			  
			    controlPlanNumjTextField.setText("K-MJ-"+partNum);
			    femaNumjTextField.setText("F-MJ-"+partNum);
			    processFlowNumjTextField.setText("G-MJ-"+partNum);
			    taskInstructNumjTextField.setText("ZY-MJ-"+partNum); 
	     	   
		   }else if(technicsType.equals("�ɶ�����װ�乤��")){
	 			  
			    controlPlanNumjTextField.setText("K-ZC-"+partNum);
			    femaNumjTextField.setText("F-ZC-"+partNum);
			    processFlowNumjTextField.setText("G-ZC-"+partNum);
			    taskInstructNumjTextField.setText("ZY-ZC-"+partNum); 
	     	   
		   }else if(technicsType.equals("�ɶ�����װ�乤��")){
	 			  
			    controlPlanNumjTextField.setText("K-NS-"+partNum);
			    femaNumjTextField.setText("F-NS-"+partNum);
			    processFlowNumjTextField.setText("G-NS-"+partNum);
			    taskInstructNumjTextField.setText("ZY-NS-"+partNum); 
	     	   
		   }else if(technicsType.equals("�ɶ���ʻ��Ϳװ����")){
	 			  
			    controlPlanNumjTextField.setText("K-TJ-"+partNum);
			    femaNumjTextField.setText("F-TJ-"+partNum);
			    processFlowNumjTextField.setText("G-TJ-"+partNum);
			    taskInstructNumjTextField.setText("ZY-TJ-"+partNum); 
	     	   
		   }
    		  //CCEnd SS5
    	
     	   
     	    
     	    
//    	  }
//    	  else{
//    		  
//      	    
//   				processFlowNumjTextField.setText("L/"+technicsType);
//   	     	    controlPlanNumjTextField.setText("K/"+technicsType);
//   	     	    femaNumjTextField.setText("PF/"+technicsType);
//   	     	    taskInstructNumjTextField.setText("Z/"+technicsType); 
//   	     	    
//   		
//    	  }
    	  
 
    	}
    		
    	//CCEnd SS4
    }
    
    //CCEnd SS3
    
    
    
}
