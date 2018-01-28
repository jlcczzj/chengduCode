/** 生成程序PartUsageTechLinkController.java	1.1  2004/8/6
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 轴齿工艺自动编号 2014-1-13 文柳
 * SS2 轴齿工艺产品平台属性 2014-2-18 文柳
 * SS3 按轴齿用户要求，修改自动编号规则："部门-零件号-工艺种类-流水号“ pante 2014-09-04
 * SS4 轴齿工艺添加主要零件时，如果零件有材料牌号就自动添加到其他相关信息中 pante 2014-10-23
 * SS5 长特工艺自动编号出错 2014-10-20 文柳
 * SS6 成都TS16949自动编号 guoxiaoliang 2016-7-12 
 * SS7 成都TD12047 成都工艺选择主要零件时工艺编号中有null。　guoxiaoliang 2016-11-8
 */

package com.faw_qm.cappclients.capp.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JRadioButton;

import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.cappclients.beans.cappassociationspanel.ObjectFilter;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
import com.faw_qm.cappclients.capp.view.PartUsageTechLinkJPanel;
import com.faw_qm.cappclients.capp.view.TechUsageMaterialLinkJPanel;
import com.faw_qm.cappclients.capp.view.TechnicsMasterJPanel;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.extend.util.ExtendAttModel;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.part.model.QMPartInfo;


/**
 * <p>Title: 工艺使用零件关联面板的控制类</p>
 * <p>Description: 此类是所有具体关联控制类的父类,子类中个性化的地方可以覆盖此类中的方法
 * 覆盖的方法有: setRadionButtonSelected(), calculate(), handelCappAssociationsPanel(String type),
 * public BaseValueIfc[] doFillter(BaseValueIfc[] vec) </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company:一汽启明 </p>
 * @author 薛静
 * @version 1.0
 */

public abstract class PartUsageTechLinkController implements ObjectFilter
{
    /***/
    protected static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**用于标记资源文件路径*/
    public static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**列表中选中得行*/
    int row = -1;


    /**列表中选中得列*/
    int col = -1;
    public PartUsageTechLinkJPanel panel;
    public CappAssociationsPanel cappAssociationsPanel;


    /**监听者*/
    public ArrayList listeners;
    public String technicsType;


    /**材料关联面板*/
    public TechUsageMaterialLinkJPanel materialPanel;

    public Component parent;


    /**
     * 构造方法
     * @param type String 工艺种类
     */
    public PartUsageTechLinkController(String type)
    {
        technicsType = type;
    }


    /**
     * 设置关联面板,并对关联面板进行处理
     * @param linkpanel PartUsageTechLinkJPanel 关联面板
     */
    public void setLinkPanel(PartUsageTechLinkJPanel linkpanel)
    {
        panel = linkpanel;
        cappAssociationsPanel = panel.getCappAssociationsPanel();
        handelCappAssociationsPanel();
    }

    
//  CCBeginby leixiao 2009-6-24 原因：零件面板添加零件时，工艺编号自动生成
    public void setLinkPanel(PartUsageTechLinkJPanel linkpanel,Component parentpanel)
    {
    	parent=parentpanel;
        panel = linkpanel;
        cappAssociationsPanel = panel.getCappAssociationsPanel();
        handelCappAssociationsPanel();
    }
//  CCEndby leixiao 2009-6-24 原因：零件面板添加零件时，工艺编号自动生成
    

    /**
     * 处理关联面板,包括设置业务类名,关联类名,属性,标题,可编辑列数
     */
    private void handelCappAssociationsPanel()
    {
        //设置角色名
        cappAssociationsPanel.setRole("technics");
        cappAssociationsPanel.setObject(null);
        //设置关联类名
        try
        {
            cappAssociationsPanel.setLinkClassName(
                    "com.faw_qm.capp.model.PartUsageQMTechnicsLinkInfo");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        //设置业务类名
        try
        {
            cappAssociationsPanel.setObjectClassName(
                    "com.faw_qm.capp.model.QMFawTechnicsInfo");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        //设置关联业务对象类名
        try
        {
            cappAssociationsPanel.setOtherSideClassName(
                    "com.faw_qm.part.model.QMPartInfo");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        cappAssociationsPanel.setLastIteration(true);
        //根据工艺种类从资源文件得到字符串
        String allProperties = RemoteProperty.getProperty(
                "com.faw_qm.cappclients.capp.view" + technicsType);
        StringTokenizer stringToken = new StringTokenizer(allProperties, ";");

        //得到allProperties中第一个分号前的字符串，为零部件的属性
        String partProperties = stringToken.nextToken();
        //得到allProperties中第一个分号后的字符串，为关联属性
        String linkProperties = stringToken.nextToken();

        //关联是否有扩展属性
        boolean flag = stringToken.nextToken().equals("true");
        //标题
        String labels = stringToken.nextToken();
        //是否计算每车件数
        final boolean flag2 = stringToken.nextToken().equals("true");
        //得到可编辑的列数
        String editProperties = stringToken.nextToken();

        StringTokenizer partToken = new StringTokenizer(partProperties, ",");
        StringTokenizer linkToken = new StringTokenizer(linkProperties, ",");
        if (flag)
        {
            cappAssociationsPanel.setSecondTypeValue(technicsType);
        }
        StringTokenizer LabelToken = new StringTokenizer(labels, ",");

        //把零部件属性放入partProperties[]中
        final int partL = partToken.countTokens();
        String[] part = new String[partL];

        for (int i = 0; i < partL; i++)
        {
            part[i] = partToken.nextToken();
        }
        //把关联属性放入linkProperties[]中
        int linkL = linkToken.countTokens();
        String[] link = new String[linkL];
        for (int i = 0; i < linkL; i++)
        {
            link[i] = linkToken.nextToken();
        }
        //标题
        int labelsL = LabelToken.countTokens();
        String[] label = new String[labelsL];
        for (int i = 0; i < labelsL; i++)
        {
            label[i] = LabelToken.nextToken();
            //设置计算按钮
        }
        if (flag2)
        {
            cappAssociationsPanel.setIsCalculate(true, 3);
            //把可编辑的列数放入linkProperties[]中
        }
        StringTokenizer editToken = new StringTokenizer(editProperties, ",");
        int editL = editToken.countTokens();
        int[] editCols = new int[editL];
        for (int i = 0; i < editL; i++)
        {
            editCols[i] = Integer.parseInt(editToken.nextToken());
        }

        //设置将显示在多列列表中关联的业务对象的属性集
        cappAssociationsPanel.setOtherSideAttributes(part);

        try
        {
            cappAssociationsPanel.setMultiListLinkAttributes(link);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        try
        { //标题
            cappAssociationsPanel.setLabels(label);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }

        //设置是否只保存更改过的
        cappAssociationsPanel.setSaveUpdatesOnly(true);
        //将AssociationsPanel下方的AttributeForm部分删除
        cappAssociationsPanel.removeAttributeForm();

        //设置主要零件标识列显示JRadioButton
        if (getRadioButtonCols() != null)
        {
            cappAssociationsPanel.setRadionButtons(getRadioButtonCols());
        }
        cappAssociationsPanel.setColsEnabled(editCols, true);

        //给关联面板加监听
        cappAssociationsPanel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //加工艺主要标识和零部件主要标识
                cappAssociationsPanel_actionPerformed(e);

            }
        });

        cappAssociationsPanel.setObjectFilter(this);
        //助记符
        cappAssociationsPanel.setBrowseButtonMnemonic('F');
        cappAssociationsPanel.setRemoveButtonMnemonic('D');
        cappAssociationsPanel.setViewButtonMnemonic('V');
        cappAssociationsPanel.setCalculateButtonMnemonic('C');

    }
    //CCBegin SS2
    String mainPartBsoID = "";
    //CCEnd SS2
    /**
     * 关联面板的事件执行方法,如果列表中添加了第一行，则设置零部件主要标识和工艺主要标识为选中.
     * 如果主要零部件列被选中,则通知监听者
     * @param e 事件
     */
    private void cappAssociationsPanel_actionPerformed(ActionEvent e)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.PartUsageTechLinkJPanel.cappAssociationsPanel_actionPerformed begin"
                               );
        }
        String actionCommand = e.getActionCommand();

        int t = actionCommand.indexOf(";");
        if (t != -1)
        {
            //得到行
            String rowString = actionCommand.substring(0, t);
            int t1 = rowString.indexOf(":");
            row = Integer.parseInt(rowString.substring(t1 + 1, rowString.length()));
            //得到列
            String colString = actionCommand.substring(t + 1,
                    actionCommand.length());
            int t2 = colString.indexOf(":");
            col = Integer.parseInt(colString.substring(t2 + 1, colString.length()));

            if (verbose)
            {
                System.out.println("行:" + row + "\n" + "列:" + col);
            }
        }

        //如果列表中添加了第一行，则设置零部件主要标识和工艺主要标识为选中
        if (e.getActionCommand().equals("FirstRow"))
        {
            BinaryLinkIfc link = (BinaryLinkIfc) e.getSource();
            if (link.getBsoID() == null)
            {
                cappAssociationsPanel.setRadionButtonValue(0,
                        getMajorpartMarkColum(), true);
                cappAssociationsPanel.setCheckboxValue(0,
                        getmMajortechnicsMarkColum(), true);

                actionPerformed_whenMajorpartMarkSelected(link.getLeftBsoID(),
                        0);
                //CCBegin SS2
                TechnicsMasterJPanel Parent2=(TechnicsMasterJPanel)parent;
        		String type2=Parent2.technicsType.getCodeContent();
                try {
					if(getUserFromCompany().equals("zczx")&&(type2.equals("轴齿机加工艺")||type2.equals("轴齿热处理工艺"))){
		                mainPartBsoID = link.getLeftBsoID();
		                Parent2.hideMainPartBsoID.setText(mainPartBsoID);	
					}
				} catch (QMException e1) {
					e1.printStackTrace();
				}
                //CCEnd SS2
            }
            
//          CCBeginby leixiao 2009-6-24 原因：设置零件ID,供定额获取用  
            cappAssociationsPanel.setAssociatpart(link.getLeftBsoID());
           // System.out.println("---------------part="+cappAssociationsPanel.getAssociatpart()+"   "+link.getLeftBsoID());
//          CCEndby leixiao 2009-6-24 原因：设置零件ID,供定额获取用    	

        }
//      CCBeginby leixiao 2009-6-24 原因：零件面板添加零件时，工艺编号自动生成
        if (e.getActionCommand().equals("buildPartNumber"))
        {
    		String partnumber = (String) cappAssociationsPanel
    		.getCellAt(0, 0);
    		
    		String partversion = (String) cappAssociationsPanel
    		.getCellAt(0, 2);
//    		CCBegin by liunan 2009-09-22
    		String partstr="";
    		if(partversion.equals(""))
    		  partstr=partnumber+"_";
    		else
    		  partstr=partnumber+"_"+partversion.substring(0,partversion.indexOf("."));
    		//CCEnd by liunan 2009-09-22
    		//System.out.println("---buildPartNumber-----partstr="+partstr);
    		TechnicsMasterJPanel Parent=(TechnicsMasterJPanel)parent;
    		//System.out.println("--------Parent="+Parent);
    		String type=Parent.technicsType.getCodeContent();
    		String typestr=(String)Parent.typeMap.get(type);
    		//CCBegin SS5
    		String ctPartNumber = "";
    		//CCEnd SS5
    		//CCBegin SS1
    		String zhouchiNumber = "";
    		try {
				if(getUserFromCompany().equals("zczx")&&(type.equals("轴齿机加工艺")||type.equals("轴齿热处理工艺"))){
					BaseValueIfc workShop  = (BaseValueIfc)Parent.workshopSortingSelectedPanel.getCoding();
					String workShopStr = "";
					Parent.hideMainPart.setText(partnumber);
					if(workShop!=null){
						workShopStr = String.valueOf(workShop);
					}
					if(workShopStr.equals("")){
						zhouchiNumber = partnumber+"-"+type;
					}else{
						//CCBegin SS3
//						zhouchiNumber = partnumber+"-"+type+"-"+workShopStr;
						zhouchiNumber = workShopStr+"-"+partnumber+"-"+type;
						//CCEnd SS3
					}
					Parent.numberJTextField.setText(zhouchiNumber);
					Parent.masterTS16949Panel.setAttributeNumber(partnumber,type);
					
//		    		String partname = (String) cappAssociationsPanel.getCellAt(0, 1);
//		    		Parent.nameJTextField.setText(partname); 
					
//		    		CCBegin SS4
		        	RequestServer server = RequestServerFactory.getRequestServer();
		    		ServiceRequestInfo info = new ServiceRequestInfo();
		    		info.setServiceName("StandardCappService");
		    		info.setMethodName("getPartM");
		    		Class[] paraClass = {String.class};
		    		info.setParaClasses(paraClass);
		    		Object[] obj = {mainPartBsoID};
		    		info.setParaValues(obj);
		    		try {
						String returnStr = ((String) server.request(info));
						CappExAttrPanel ex = Parent.getCappExAttrPanel();
						ex.setPartM(returnStr);
					} catch (QMRemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
//		    		CCEnd SS4
				}
			 //CCBegin SS5
				else if(getUserFromCompany().equals("ct")){

					Parent.hideMainPart.setText(partnumber);
					ctPartNumber = partnumber+"-"+type;

					Parent.numberJTextField.setText(ctPartNumber);
		    		String partname = (String) cappAssociationsPanel.getCellAt(0, 1);
					Parent.nameJTextField.setText(partname);  
					Parent.masterTS16949Panel.setAttribute(partnumber);

				}
				//CCEnd SS5
				
				//CCBegin SS6
				else if(getUserFromCompany().equals("cd")||getUserFromCompany().equals("Admin")){
					

					Parent.hideMainPart.setText(partnumber);
					Parent.masterTS16949Panel.setAttributeCD(partnumber,type);
					
				}
				//CCEnd SS6
				
				else{
					//CCBegin SS7
					if(typestr==null)
						typestr="";
					//CCEnd SS7
		    		Parent.numberJTextField.setText(typestr+partstr);   
		        	//  CCBeginby leixiao 2010-4-20 原因：零件面板添加零件时，工艺名称自动生成
		    		String partname = (String) cappAssociationsPanel
		    		.getCellAt(0, 1);
		    		Parent.nameJTextField.setText(partname);  
		    	//  CCEndby leixiao 2010-4-20 原因：零件面板添加零件时，工艺名称自动生成
//		          CCBeginby leixiao 2009-7-2 原因：零件面板添加零件时，工艺编号自动生成   
		    		ComponentMultiList m=(ComponentMultiList)e.getSource();
		    		int r=m.getNumberOfRows();
		    		//System.out.println("---"+r);
		    		for(int i=0;i<r;i++){
		    		String weight = (String) cappAssociationsPanel.getCellAt(i, 3);
//		          CCBeginby leixiao 2010-1-15 原因：涂装零件数量不在第6列 
		    		if(type.indexOf("涂装")!=-1){
		    			cappAssociationsPanel.setCellTextValue(i,9,weight);	
		    		}
		    		else{
//		              CCEnd by leixiao 2010-1-15 
		             cappAssociationsPanel.setCellTextValue(i,6,weight);
		    		}
		    		}
//		           CCEndby leixiao 2009-7-2 原因：零件面板添加零件时，工艺编号自动生成
				}
			} catch (QMException e1) {
				e1.printStackTrace();
			}
    		//CCEnd SS1
			

        }
//      CCEndby leixiao 2009-6-24 原因：零件面板添加零件时，工艺编号自动生成
        //如果主要零部件列被选中,则通知监听者执行actionPerformed(ActionEvent e)
        if (col == getMajorpartMarkColum())
        {
            Object majorpartComp = e.getSource();
            if (majorpartComp instanceof JRadioButton &&
                ((JRadioButton) majorpartComp).isSelected())
            {

                actionPerformed_whenMajorpartMarkSelected(
                        cappAssociationsPanel.getSelectedLink().
                        getLeftBsoID(),
                        row);
//              CCEndby leixiao 2009-7-2 原因：零件面板添加零件时，工艺编号自动生成  添加多个零件时，按主要标识的零件自动编号
        		String partnumber = (String) cappAssociationsPanel
        		.getCellAt(row, 0);
        		String partversion = (String) cappAssociationsPanel
        		.getCellAt(row, 2);
        		//CCBegin by liunan 2009-09-22
        		String partstr="";
        		if(partversion.equals(""))
        		  partstr=partnumber+"_";
        		else
        		  partstr=partnumber+"_"+partversion.substring(0,partversion.indexOf("."));
        		//CCEnd by liunan 2009-09-22        		
        	//	System.out.println("---buildPartNumber-----partstr="+partstr);
        		
        		TechnicsMasterJPanel Parent=(TechnicsMasterJPanel)parent;
        		//System.out.println("--------Parent="+Parent);
        		String type=Parent.technicsType.getCodeContent();
        		String typestr=(String)Parent.typeMap.get(type);
        		//CCBegin SS5
        		String ctPartNumber = "";
        		//CCEnd SS5
        		//CCBegin SS1
        		String zhouchiNumber = "";
        		try {
					if(getUserFromCompany().equals("zczx")&&(type.equals("轴齿机加工艺")||type.equals("轴齿热处理工艺"))){
						BaseValueIfc workShop  = (BaseValueIfc)Parent.workshopSortingSelectedPanel.getCoding();
						String workShopStr = "";
						Parent.hideMainPart.setText(partnumber);
						//CCBegin SS2
		                mainPartBsoID = cappAssociationsPanel.getSelectedLink().getLeftBsoID();
						Parent.hideMainPartBsoID.setText(mainPartBsoID);
						//CCEnd SS2
						if(workShop!=null){
							workShopStr = String.valueOf(workShop);
						}
						if(workShopStr.equals("")){
							zhouchiNumber = partnumber+"-"+type;
						}else{
							//CCBegin SS3
//							zhouchiNumber = partnumber+"-"+type+"-"+workShopStr;
							zhouchiNumber = workShopStr+"-"+partnumber+"-"+type;
							//CCEnd SS3
						}
						Parent.numberJTextField.setText(zhouchiNumber);
						Parent.masterTS16949Panel.setAttributeNumber(partnumber,type);
//						String partname = (String) cappAssociationsPanel.getCellAt(0, 1);
//						Parent.nameJTextField.setText(partname); 
					}
					
					//CCBegin SS5
					else if(getUserFromCompany().equals("ct")){

						Parent.hideMainPart.setText(partnumber);
		                mainPartBsoID = cappAssociationsPanel.getSelectedLink().getLeftBsoID();
						Parent.hideMainPartBsoID.setText(mainPartBsoID);
						ctPartNumber = partnumber+"-"+type;
						Parent.numberJTextField.setText(ctPartNumber);
			    		String partname = (String) cappAssociationsPanel.getCellAt(row, 1);
						Parent.nameJTextField.setText(partname);  
						Parent.masterTS16949Panel.setAttributeNumber(partnumber,type);
					}
					//CCEnd SS5
					else{
						//CCBegin SS7
						if(typestr==null)
							typestr="";
						//CCEnd SS7
		        		Parent.numberJTextField.setText(typestr+partstr);         		 
//		              CCEndby leixiao 2009-7-2 原因：零件面板添加零件时，工艺编号自动生成        
		        	//  CCBeginby leixiao 2010-4-20 原因：零件面板添加零件时，工艺名称自动生成
		        		String partname = (String) cappAssociationsPanel
		        		.getCellAt(row, 1);
		        		Parent.nameJTextField.setText(partname);  
		        	//  CCEndby leixiao 2010-4-20 原因：零件面板添加零件时，工艺名称自动生成
					}
				} catch (QMException e1) {
					e1.printStackTrace();
				}
				//CCEnd SS1


            }
        }
        if (e.getActionCommand().equals("calculate"))
        {
            calculate();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.PartUsageTechLinkJPanel.cappAssociationsPanel_actionPerformed end");
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
    /**
     * 添加监听者,监听关联面板事件
     * @param e ActionListener
     */
    public void addListener(ActionListener e)
    {
        if (listeners == null)
        {
            listeners = new ArrayList();
        }
        listeners.add(e);
    }


    /**
     * 设置材料关联面板
     * @param materialPanel TechUsageMaterialLinkJPanel
     */
    public void setMaterialLinkJPanel(TechUsageMaterialLinkJPanel
                                      panel)
    {
        materialPanel = panel;
    }


    /**
     * 清除,子类覆盖此方法
     */
    public void clear()
    {}


    /**
     * 零件主要标识是否被选中时的执行方法,此方法在相应的子类中覆盖
     * @param partId boolean 零件的bsoID
     * @param row 行数
     */
    public void actionPerformed_whenMajorpartMarkSelected(String partId,
            int row)
    {}


    /**
     * 过滤关联面板添加的对象集合
     * @param parm1 BaseValueIfc[] 过滤前的结果集
     * @return BaseValueIfc[] 过滤后的结果集
     */
    public BaseValueIfc[] doFillter(BaseValueIfc[] parm1)
    {
        return parm1;
    }


    /**
     * 计算,在相应子类中覆盖
     */
    public abstract void calculate();


    /**
     * 得到添单选框的列数
     * @return int[]
     */
    public abstract int[] getRadioButtonCols();


    /**
     * 得到零件主要标识的列数
     * @return int
     */
    public abstract int getMajorpartMarkColum();


    /**
     * 工艺主要标识的列数
     * @return int
     */
    public abstract int getmMajortechnicsMarkColum();
}
