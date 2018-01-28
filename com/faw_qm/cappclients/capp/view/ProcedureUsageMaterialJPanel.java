/** 生成程序ProcedureUsageMaterialJPanel.java	1.1  2003/08/28
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 *   CR1 郭晓亮　2009/04/21   原因:工序关联的所有资源不能进行多选删除.
 *                            方案:将资源列表修改为可以选择多行数据,而且修改了删除按钮的
 *                                 逻辑.
 *                            备注:变更记录标记"CRSS-009"
 * 
 * CCBegin by liunan 2011-08-25 打补丁PO35
 * CR2 吕航 2011/06/28 参见TD2408
 * CCEnd by liunan 2011-08-25
 * SS1 增加新功能工步中设备工装材料零件的数量是否累加到序 leixiao 2013-9-23
 * SS2 工步中增加关联“累加到工序”并且不在工序中显示 leixiao 2013-10-14
 * SS3 成都工步关联资源添加工序资源功能 guoxiaoliang 2016-8-2
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.resource.view.ResourceDialog;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.resource.support.model.QMMaterialInfo;


/**
 * <p>Title: 工序使用材料关联面板</p>
 * <p>Description: 维护工序用材料资源 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明 薛静
 * @version 1.0
 * 问题（1）20080709 xucy add 修改原因：为工序工装关联添加预留属性用
 * 问题（2）20081219 徐春英修改  修改原因：工序,工步中“工装”、“简图”等标签页中有多个工装、简图时，用户经常需要调整他们的顺序。
 * 针对这个功能添加上移下移按钮
 */

public class ProcedureUsageMaterialJPanel extends ParentJPanel
{
    /**
     * 关联bean
     */
    private CappAssociationsPanel cappAssociationsPanel = new
            CappAssociationsPanel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**
     * 界面模式
     */
    private String mode = "";


    /**
     * 手工输入的材料编号
     */
    private String number = "";


    /**
     * 列表中选中得行
     */
    int row = -1;


    /**
     * 列表中选中得列
     */
    int col = -1;


    /**父界面是否可以保存*/
    private boolean canSave = true;

    //问题（1）20080709 xucy add 修改原因：为工序工装关联添加预留属性用
    /**
     * 工序种类
     */
    private String stepType;
    
    //CCBegin SS3
    private boolean isProcedure = false;
    
    /**
     * 构造方法
     */
    public ProcedureUsageMaterialJPanel(boolean isProcedure)
    {
      this.isProcedure = isProcedure;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    
     /**
     * 构造方法
     */
    public ProcedureUsageMaterialJPanel(String stepType,boolean isPro)
    {
    	
    	
    	this.isProcedure=isPro;
    	
    	
    	this.stepType = stepType;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //CCEnd SS3
    
    /**
     * 构造方法
     */
    public ProcedureUsageMaterialJPanel()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    //问题（1）20080709 xucy add 修改原因：为工序工装关联添加预留属性用
    /**
     * 构造方法
     */
    public ProcedureUsageMaterialJPanel(String stepType)
    {
    	this.stepType = stepType;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    /**
     * 界面初始化
     * @throws Exception
     * 问题（1）20080709 xucy add 修改原因：为工序工装关联添加预留属性用 
     */
    void jbInit()
            throws Exception
    {
        setLayout(gridBagLayout1);
        // 问题（2）20081219 徐春英修改  修改原因：工序,工步中“工装”、“简图”等标签页中有多个工装、简图时，用户经常需要调整他们的顺序。
        // 针对这个功能添加上移下移按钮  begin
        cappAssociationsPanel.getMultiList().setAllowSorting(false);
        cappAssociationsPanel.setIsUpDown(true, 4, 5);
        //问题（2）end
        cappAssociationsPanel.addActionListener(new java.awt.event.
                                                ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cappAssociationsPanel_actionPerformed(e);
            }
        });
       // cappAssociationsPanel.setMutliSelectedModel(false);    //Begin CR1
        cappAssociationsPanel.setMutliSelectedModel(true);      //End CR1
        add(cappAssociationsPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 0), 0, 0));
        //设置角色名
        cappAssociationsPanel.setRole("procedure");
        //当用户点击Add按钮时，本方法用来设置可选的类列表,确定要选择的对象的类型（这里为Doc）
        //cappAssociationsPanel.setChooserOptions("2");
        //设置列表标题名
//      CCBeginby leixiao 2009-6-24 原因：工序材料面板属性增加
        String args1[] = new String[5];
        args1[0] = "材料编号";
        args1[1] = "材料名称";
        args1[2] = "材料牌号";
        args1[3] = "材料规格";
        args1[4] = "材料标准";
//      CCEndby leixiao 2009-6-24 原因：工序材料面板属性增加
        cappAssociationsPanel.setLabels(args1);

        //设置关联类名
        cappAssociationsPanel.setLinkClassName(
                "com.faw_qm.capp.model.QMProcedureQMMaterialLinkInfo");
//      CCBeginby leixiao 2009-6-24 原因：工序材料面板属性增加
        String as1[] = new String[5];
        as1[0] = "materialNumber";
        as1[1] = "materialName";
        as1[2] = "materialState";
        as1[3] = "materialSpecial";
        as1[4] = "materialCrision";
//      CCEndby leixiao 2009-6-24 原因：工序材料面板属性增加
        //设置将显示在多列列表中关联的业务对象的属性集
        cappAssociationsPanel.setOtherSideAttributes(as1);
        //设置关联业务对象类名
        try
        {
            cappAssociationsPanel.setOtherSideClassName(
                    "com.faw_qm.resource.support.model.QMMaterialInfo");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        //设置是否只保存更改过的
        cappAssociationsPanel.setSaveUpdatesOnly(true);
        //将AssociationsPanel下方的AttributeForm部分删除
        cappAssociationsPanel.removeAttributeForm();
        
        //问题（1）20080709 xucy add 修改原因：为工序工装关联添加预留属性用 begin
        String allProperties = RemoteProperty.getProperty(
                "com.faw_qm.cappclients.material.view" + stepType);
        if (allProperties == null || allProperties.trim().equals("null") || allProperties.equals(""))
        {
            String args2[] = new String[2];
            args2[0] = "usageCount";
            args2[1] = "measureUnit";
            //设置关联属性（这里为“使用数量、计量单位）
            cappAssociationsPanel.setMultiListLinkAttributes(args2);
            int[] editCols = {1};
	        cappAssociationsPanel.setColsEnabled(editCols, false);
        }
        else
        {
//        	StringTokenizer stringToken = new StringTokenizer(allProperties, ";");
//        	String linkProperties = stringToken.nextToken();
        	StringTokenizer linkToken = new StringTokenizer(allProperties, ",");
		    int linkSize = linkToken.countTokens();
			String args3[] = new String[linkSize+2];
			args3[0] = "usageCount";
			args3[1] = "measureUnit";
			for(int i = 2; i < linkSize+2; i++)
			{
			    args3[i] = linkToken.nextToken();
			}
		        
			//设置关联属性（这里为“使用数量”）
			cappAssociationsPanel.setMultiListLinkAttributes(args3);
//			//得到可编辑的列数
//	        String editProperties = stringToken.nextToken();
//	        StringTokenizer editToken = new StringTokenizer(editProperties, ",");
//	        int editL = editToken.countTokens();
//	        int[] editCols = new int[editL+2];
//	        editCols[0] = 0;
//	        editCols[1] = 2;
//	        
//	        for (int i = 2; i < editL+2; i++)
//	        {
//	            editCols[i] = Integer.parseInt(editToken.nextToken())+4;
//	        }
			int[] is = {1};
	        cappAssociationsPanel.setColsEnabled(is, false);
        }
        //问题（1）20080709 xucy add 修改原因：为工序工装关联添加预留属性用 end
//      CCBeginby leixiao 2009-6-24 原因：工序材料面板属性增加
        //CCBegin by liunan 2010-11-12 发动机新需求，新增“更换频次”属性，也需要可编辑
        //CCBegin SS1
         
//        
//        int[] is =
//               {0, 5, 6, 7};
//            cappAssociationsPanel.setColsEnabled(is, true);
//        }
//        else{
            int[] is =
                {0, 5, 6, 7, 8};
            cappAssociationsPanel.setColsEnabled(is, true);
//        }


        //CCEnd SS1
        //CCEnd by liunan 2010-11-12        
        //设置第0,2,３,4,5列可以编辑
//      CCEndby leixiao 2009-6-24 原因：工序材料面板属性增加

        //设置添加按钮显示，即可以添加空白行
               //CCBegin SS3
//      cappAssociationsPanel.setIsInsert(true, 3);
      
        if(isProcedure)
            cappAssociationsPanel.setIsInsert(true, 3);
          else{
            cappAssociationsPanel.setIsInsert(true, 3);
            cappAssociationsPanel.setIsGetResouceByProcedure(true,7);
          }
      //CCEnd SS3
        cappAssociationsPanel.setObject(null);
        //助记符
        cappAssociationsPanel.setBrowseButtonMnemonic('F');
        cappAssociationsPanel.setRemoveButtonMnemonic('D');
        cappAssociationsPanel.setViewButtonMnemonic('V');
        cappAssociationsPanel.setInsertButtonMnemonic('I');
        // 问题（2）20081219 徐春英修改  修改原因：工序,工步中“工装”、“简图”等标签页中有多个工装、简图时，用户经常需要调整他们的顺序。
        // 针对这个功能添加上移下移按钮  
        cappAssociationsPanel.setUpButtonMnemonic('U');
        cappAssociationsPanel.setDownButtonMnemonic('N');
        //20080709 xucy 注释
        //localize();
    }


    /**
     * 文字信息本地化
     */
    protected void localize()
    {
//      CCBeginby leixiao 2009-6-24 原因：工序材料面板属性增加
        String args1[] = new String[5];
        args1[0] = "材料编号";
        args1[1] = "材料名称";
        args1[2] = "材料牌号";
        args1[3] = "材料规格";
        args1[4] = "材料标准";
//      CCEndby leixiao 2009-6-24 原因：工序材料面板属性增加
        //设置列表标题名
//        String args1[] = new String[2];
//        args1[0] = QMMessage.getLocalizedMessage(RESOURCE, "materialNumber", null);
//        args1[1] = QMMessage.getLocalizedMessage(RESOURCE, "materialName", null);
        try
        {  
            cappAssociationsPanel.setLabels(args1);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * 设置界面显示模式
     * @param m  界面显示模式(编辑模式、查看模式)  EDIT_MODE or VIEW_MODE
     */
    public void setMode(String m)
    {
        cappAssociationsPanel.setMode(m);
        mode = m;
    }


    /**
     * 获得界面模式
     * @return 界面显示模式(编辑模式、查看模式)  EDIT_MODE or VIEW_MODE
     */
    public String getMode()
    {
        return mode;
    }


    /**
     * 设置当前业务对象
     * @param info 工序值对象
     */
    public void setProcedure(QMProcedureInfo info)
    {
        if (info.getBsoID() != null)
        {
            String classPath = RemoteProperty.getProperty("instance" +
                    info.getTechnicsType().getCodeContent());

            try
            {
                cappAssociationsPanel.setObjectClassName(classPath);
            }
            catch (ClassNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
        cappAssociationsPanel.setObject(info);
    }

    //CCBegin SS2
    public void setIsProcedure(boolean flag){
    	cappAssociationsPanel.setIsProcedure(flag);
    }
//CCEnd SS2

    /**
     * 获得所有关联
     * @return 所有关联类对象的集合
     */
    public Vector getAllLinks()
            throws Exception
    {
        if (!canSave)
        {
            throw new Exception();
        }
        if (!cappAssociationsPanel.check())
        {
            throw new Exception("关联属性输入错误！");
        }
        else
        {
            return cappAssociationsPanel.getLinks();
        }

    }


    /**
     * 获得要被删除的关联
     * @return 关联类对象的集合
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


    /**
     * 把指定的材料资源添加到列表中
     * @param material
     */
    public void addMaterialToTable(QMMaterialInfo material)
    {
        try
        {
            cappAssociationsPanel.addSelectedObject(material);
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


    /**
     * 执行手工录入材料操作
     * @param e ActionEvent
     */
    void cappAssociationsPanel_actionPerformed(ActionEvent e)
    {
        Object obj = e.getSource();

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
        if (col == 0)
        {
            if (obj instanceof JTextField)
            {
                cappAssociationsPanel.setEnabled(false);
                JTextField textField = (JTextField) obj;
                if (textField.getText().trim() != null &&
                    !textField.getText().trim().equals(""))
                {
                    handworkAddMaterialNumber(textField);
                }
                else
                {
                    cappAssociationsPanel.setEnabled(true);
                    //CCBegin by liunan 2011-08-25 打补丁PO35
                      //CR2
                    cappAssociationsPanel.undoCell();
                    //CCEnd by liunan 2011-08-25
                }
            }
        }

    }


    /**
     * 手工添加材料编号到指定的文本框中
     * @param textField 填写材料编号的文本框
     */
    private void handworkAddMaterialNumber(JTextField textField)
    {
        number = textField.getText().trim();
        //如果输入的材料编号在系统中存在，则系统把该材料添加入列表中
        QMMaterialInfo eq = checkMaterialIsExist(number);
        if (eq != null)
        {
            try
            {
                cappAssociationsPanel.addObjectToRow(eq, row);
                cappAssociationsPanel.setEnabled(true);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        else
        {
            CreateResource t = new CreateResource();
            new Thread(t).start();
        }

    }


    /**
     * 调用资源服务，通过指定的材料编号在数据库中查找材料对象
     * @param eqNumber 指定的材料编号
     * @return 查找到的材料对象
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
     * 显示确认对话框
     * @param s 在对话框中显示的信息
     * @return  如果用户选择了“确定”按钮，则返回true;否则返回false
     */
    private boolean confirmAction(String s)
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        return JOptionPane.showConfirmDialog(getParentJFrame(),
                                             s, title,
                                             JOptionPane.YES_NO_OPTION) ==
                JOptionPane.YES_OPTION;
    }


    class CreateResource extends Thread
    {
        public void run()
        {
            canSave = false;
            //如果系统中不存在列表中输入的材料编号，则返回对应的消息，询问是否新建
            //材料，如果是，显示材料新建界面，根据访问权限创建申请材料；如果否，返回原位置.
            String s = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.IS_CREATE_MATERIAL, null);
            if (confirmAction(s))
            {
                cappAssociationsPanel.setEnabled(true);
                getParentJFrame().setCursor(Cursor.getPredefinedCursor(Cursor.
                        WAIT_CURSOR));
                //显示材料新建界面
                ResourceDialog d = new ResourceDialog(getParentJFrame(),
                        "material", "CREATE", number);
                d.setSize(650, 500);
                setViewLocation(d);
                d.setTitle("新建材料");
                d.addQuitListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        cappAssociationsPanel.undoCell();
                    }

                });

                d.setVisible(true);
                getParentJFrame().setCursor(Cursor.getDefaultCursor());
                try
                {
                    Object obj = d.getObject();
                    if (obj != null)
                    {
                        cappAssociationsPanel.addObjectToRow((BaseValueIfc) obj,
                                row);
                    }
                    else
                    {
                        cappAssociationsPanel.undoCell();
                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                cappAssociationsPanel.setEnabled(true);
                cappAssociationsPanel.undoCell();
            }
            canSave = true;
        }
    }


    /**
     * 设置界面的显示位置
     */
    private void setViewLocation(JDialog d)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //modify by wangh on 20071211
//        Dimension frameSize = getSize();
//        if (frameSize.height > screenSize.height)
//        {
//            frameSize.height = screenSize.height;
//        }
//        if (frameSize.width > screenSize.width)
//        {
//            frameSize.width = screenSize.width;
//        }
        d.setLocation((screenSize.width - d.getWidth()) / 2,
                (screenSize.height - d.getHeight()) / 2);
        //modify end

    }


    /**
     * 清除界面中的内容
     */
    public void clear()
    {
        cappAssociationsPanel.clear();
    }

    
  //CCBegin SS3
    public void setProcedurePace(QMProcedureIfc info){
    	   cappAssociationsPanel.setProcedurePace(info);
    	  }
    //CCEnd SS3
}
