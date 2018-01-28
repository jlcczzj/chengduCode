package com.faw_qm.cappclients.capp.view;
//SS1 轴齿工艺自动编号 2014-1-13 文柳
//SS2 长特工艺自动编号 2014-10-20 文柳
//SS3 成都TS16949自动编号 guoxiaoliang 2016-7-12 
//SS4 按照成都新需求修改TS16949自动编号 guoxiaoliang 2016-11-30
//SS5 修改FMEA编号缺少“-”问题 liuyuzhu 2017-06-09

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
 * <p>Title: 工艺卡维护面板中的TS16949属性面板</p>
 * <p>Description: 维护TS16949属性。</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author  薛静
 * @version 1.0
 */
public class MasterTS16949Panel extends ParentJPanel
{

    /**
     * 过程流程图编号
     */
    JLabel processFlowNumjLabel = new JLabel();
    //JTextField processFlowNumjTextField = new JTextField();
    private CappTextField processFlowNumjTextField;
    
    JLabel processFlowNumDispJLabel = new JLabel();
    /**
     * 控制计划编号
     */
    JLabel controlPlanNumjLabel = new JLabel();

    //JTextField controlPlanNumjTextField = new JTextField();
    private CappTextField controlPlanNumjTextField;
     JLabel controlPlanNumDispJLabel = new JLabel();
    /**
     *  过程FMEA编号
     */
    JLabel femaNumjLabel = new JLabel();
    private CappTextField femaNumjTextField;
    //JTextField femaNumjTextField = new JTextField();
    JLabel femaNumDispJLabel = new JLabel();
    /**
     * 作业指导编号
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
     * 判断用户所属公司
     * @return String 获得用户所属公司
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
//      CCBegin by leixiao 2009-6-18 原因：解放系统升级 ,ts16949编号长度20->50
        processFlowNumjTextField=new CappTextField(parentJFrame, procedureNumDisp, 50, true);
        controlPlanNumjTextField=new CappTextField(parentJFrame, controlPlanNumDisp, 50, true);
        femaNumjTextField=new CappTextField(parentJFrame, femaNumDisp, 50, true);
        taskInstructNumjTextField=new CappTextField(parentJFrame, taskInstructNumDisp, 50, true);
//      CCEnd by leixiao 2009-6-18 原因：解放系统升级 ,ts16949编号长度20->50
        processFlowNumjLabel.setMaximumSize(new Dimension(84, 18));
        processFlowNumjLabel.setToolTipText("");
        processFlowNumjLabel.setText("过程流程图编号");
        processFlowNumjTextField.setMinimumSize(new Dimension(6, 23));
        processFlowNumjTextField.setText("");
        controlPlanNumjLabel.setText("控制计划编号");
        this.setLayout(gridBagLayout1);
        femaNumjLabel.setText("过程FMEA编号");
        taskInstructNumjLabel.setText("作业指导编号");
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
        	//长特没有作业指导书
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
     * 清空界面信息
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
     * 检察界面信息的合法性
     */
    public boolean check()
    {
//      CCBeginby leixiao 2009-7-30 原因：去掉对编号特殊字符的限制
//    	return processFlowNumjTextField.check()&&controlPlanNumjTextField.check()&&
//    	        femaNumjTextField.check()&&taskInstructNumjTextField.check();
//      CCEndby leixiao 2009-7-30 原因：去掉对编号特殊字符的限制
    	return true;
    }

    /**
     * 向工艺对象中设置属性
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
    
//  CCBegin by leixiao 2008-10-27 原因：解放系统升级 
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
//  CCEnd by leixiao 2008-10-27 原因：解放系统升级 
    
    /**
     * 设置查看模式
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
     * 设置更新模式
     * @param technics QMTechnicsIfc 工艺主信息对象
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
     * 设置创建模式
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
        	//CCBegin　SS2
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
     * 设置另存为模式
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
//    	CCBegin by leixiao 2009-7-17 原因：解放升级工艺,TS16949的编号不带版本
    	if(techinicsNumber.indexOf("_")!=-1){
    		techinicsNumber=techinicsNumber.substring(0,techinicsNumber.lastIndexOf("_"));
    	}
//    	CCEnd by leixiao 2009-7-17 原因：解放升级工艺
  
    	    processFlowNumjTextField.setText("G-"+techinicsNumber);
    	    controlPlanNumjTextField.setText("K-"+techinicsNumber);
    	    femaNumjTextField.setText("F-"+techinicsNumber);
    	    taskInstructNumjTextField.setText("Z-"+techinicsNumber); 
    	    


    }
    //CCBegin SS1
    /**
     * 轴齿设置自动编号
     * @param techinicsNumber String
     */
    public void setAttributeNumber(String techinicsNumber,String type) {
    	if(type.equals("轴齿机加工艺")){
    		processFlowNumjTextField.setText("L-"+techinicsNumber);
     	    controlPlanNumjTextField.setText("K-"+techinicsNumber);
     	    femaNumjTextField.setText("P-"+techinicsNumber);
     	   // taskInstructNumjTextField.setText("Z-"+techinicsNumber); 
    		
    	}else if (type.equals("轴齿热处理工艺")){
    		processFlowNumjTextField.setText("LR-"+techinicsNumber);
     	    controlPlanNumjTextField.setText("KR-"+techinicsNumber);
     	    femaNumjTextField.setText("PR-"+techinicsNumber);
     	   // taskInstructNumjTextField.setText("Z-"+techinicsNumber); 
    		
    	}
		
    }
    //CCEnd SS1
    
    //CCBegin SS3
     /**
     * 成都设置自动编号
     * @param techinicsNumber String
     */
    public void setAttributeCD(String partNum,String technicsType) {
    	 
    	//CCBegin SS4
    	if(partNum==null)
    		partNum="";
    	
    	if(!technicsType.equals("成都检查作业指导书工艺")){
//    	  if(partNum!=null){
    		  
    		  //CCBegin SS5
    		  if(technicsType.equals("成都车架涂装工艺")){
    			  
    			    controlPlanNumjTextField.setText("K-TC-"+partNum);
    			    femaNumjTextField.setText("F-TC-"+partNum);
    			    processFlowNumjTextField.setText("G-TC-"+partNum);
    			    taskInstructNumjTextField.setText("ZY-TC-"+partNum); 
    	     	    
    	     	   
    		  }else if(technicsType.equals("成都薄板冲压工艺")){
    			  
  			    controlPlanNumjTextField.setText("K-CY-"+partNum);
  			    femaNumjTextField.setText("F-CY-"+partNum);
  			    processFlowNumjTextField.setText("G-CY-"+partNum);
  			  taskInstructNumjTextField.setText("ZY-CY-"+partNum); 
  	     	    
  	     	   
  		      }else if(technicsType.equals("成都车架冲压工艺")){
    			  
    			    controlPlanNumjTextField.setText("K-CY-"+partNum);
    			    femaNumjTextField.setText("F-CY-"+partNum);
    			    processFlowNumjTextField.setText("G-CY-"+partNum);
    			    taskInstructNumjTextField.setText("ZY-CY-"+partNum); 
    	     	    
    	     	   
    		  }else if(technicsType.equals("成都特殊工艺")){
    			  
  			    controlPlanNumjTextField.setText("K-TS-"+partNum);
  			    femaNumjTextField.setText("F-TS-"+partNum);
  			    processFlowNumjTextField.setText("G-TS-"+partNum);
  			  taskInstructNumjTextField.setText("ZY-TS-"+partNum); 
  	     	    
  	     	   
  		      }else if(technicsType.equals("成都驾驶室焊装工艺")){
    			  
    			    controlPlanNumjTextField.setText("K-HZ-"+partNum);
    			    femaNumjTextField.setText("F-HZ-"+partNum);
    			    processFlowNumjTextField.setText("G-HZ-"+partNum);
    			    taskInstructNumjTextField.setText("ZY-HZ-"+partNum); 
    	     	   
    		 }else if(technicsType.equals("成都机加工艺")){
   			  
 			    controlPlanNumjTextField.setText("K-JJ-"+partNum);
 			    femaNumjTextField.setText("F-JJ-"+partNum);
 			    processFlowNumjTextField.setText("G-JJ-"+partNum);
 			   taskInstructNumjTextField.setText("ZY-JJ-"+partNum); 
 	     	   
 		   }else if(technicsType.equals("成都钻焊工艺")){
    			  
			    controlPlanNumjTextField.setText("K-ZH-"+partNum);
			    femaNumjTextField.setText("F-ZH-"+partNum);
			    processFlowNumjTextField.setText("G-ZH-"+partNum);
			    taskInstructNumjTextField.setText("ZY-ZH-"+partNum); 
	     	   
		   }else if(technicsType.equals("成都车架装配工艺")){
 			  
			    controlPlanNumjTextField.setText("K-MJ-"+partNum);
			    femaNumjTextField.setText("F-MJ-"+partNum);
			    processFlowNumjTextField.setText("G-MJ-"+partNum);
			    taskInstructNumjTextField.setText("ZY-MJ-"+partNum); 
	     	   
		   }else if(technicsType.equals("成都整车装配工艺")){
	 			  
			    controlPlanNumjTextField.setText("K-ZC-"+partNum);
			    femaNumjTextField.setText("F-ZC-"+partNum);
			    processFlowNumjTextField.setText("G-ZC-"+partNum);
			    taskInstructNumjTextField.setText("ZY-ZC-"+partNum); 
	     	   
		   }else if(technicsType.equals("成都内饰装配工艺")){
	 			  
			    controlPlanNumjTextField.setText("K-NS-"+partNum);
			    femaNumjTextField.setText("F-NS-"+partNum);
			    processFlowNumjTextField.setText("G-NS-"+partNum);
			    taskInstructNumjTextField.setText("ZY-NS-"+partNum); 
	     	   
		   }else if(technicsType.equals("成都驾驶室涂装工艺")){
	 			  
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
