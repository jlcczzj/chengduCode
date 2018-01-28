/**
 * CR1  2009/07/06 薛凯  TD2522
 * CR2 2012/04/12 高义升 参见TD2512
 * */
package com.faw_qm.cappclients.summary.controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.faw_qm.cappclients.capp.util.CappClientHelper;
import com.faw_qm.cappclients.summary.view.EditPanel;
import com.faw_qm.cappclients.summary.view.SummaryMainJFrame;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.lock.model.LockIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.summary.model.TotalAttrSchemaIfc;
import com.faw_qm.summary.model.TotalResultIfc;
import com.faw_qm.summary.model.TotalSchemaItemIfc;
import com.faw_qm.summary.util.TotalInfo;


/**
 * <p>Title:编辑汇总控制类 </p>
 * <p>Description: 对查看、更新等操作控制</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:一汽启明</p>
 * @author 李磊
 * @version 1.0
 * 问题(1)2006.07.10徐春英修改  修改原因:汇总更新时,点击保存出现提示框,关闭提示框出现异常.
 * 问题(2)2006.08.23 徐春英修改 修改原因:重新汇总保存时,没有保存改变后的资料夹
 * 问题(3) 2006.08.31 徐春英修改  修改原因:更新保存之后要删除放在hashMap里的加锁对象
 * 问题(4) 2006.09.15 徐春英修改  修改原因:导出后有导出成功的提示
 * 问题(5) 2006.09.19 徐春英修改  修改原因:更新时点退出按钮要删除放在hashMap里的加锁对象
 * 问题(6)2006.11.15 徐春英修改  修改原因:如果是退出另存为界面
 * 则summaryObject对象还没有持久化,解锁的话会出错
 * 问题(7)2006.11.16 徐春英修改  修改原因:另存为时输入不存在的资料夹后，点击保存按钮
 * 提示资料夹路径无效，点击确定后，程序仍继续执行并保存成功。
 * 保存后汇总的位置为源汇总所在的资料夹。
 * 在用户点击确定后，应中止执行并允许用户重新录入正确的资料夹路径。
 * 问题(8)2007.01.30 徐春英修改  修改原因:重新汇总之后更新资料夹
 * 问题（9）2007.05.24 徐春英注释  保存更新的时候没必要弹出提示框
 * SS1 2013-2-20   刘家坤 增加记录模板类型
 */

public class SummaryEditController extends SummaryController
{
    private SummaryMainJFrame mainFrame;
    private EditPanel editPanel;
    private TotalSchemaItemIfc summaryObject;
    private TotalInfo totalInfo;
    private TotalResultIfc total = null;


    /**
     * 构造器
     * @param state String
     * @param mainFrame SummaryMainJFrame
     * @param summaryObject TotalSchemaItemIfc
     */
    public SummaryEditController(String state, SummaryMainJFrame mainFrame,
                                 TotalSchemaItemIfc summaryObject)
    {
        this.mainFrame = mainFrame;
        editPanel = new EditPanel(this, state, mainFrame, summaryObject);
    }


    /**
     * 汇总约束模型解锁
     * @param summaryObject TotalSchemaItemIfc
     */
    public void unLock(TotalSchemaItemIfc summaryObject)
    {
        Class[] theClass1 =
                {
                LockIfc.class};
        Object[] obj1 =
                {
                summaryObject};
        try
        {
            getServiceObject(
                    "ResourceService",
                    "unlock", theClass1, obj1);
            //vector.removeAllElements();
        }
        catch (QMException ex)
        {
            String s1 = QMMessage.getLocalizedMessage(RESOURCE,
                    "warning", null);
            JOptionPane.showMessageDialog(null, ex.getClientMessage(),
                                          s1,
                                          JOptionPane.WARNING_MESSAGE);
        }

    }


    /**
     * 监听实现
     * @param e ActionEvent
     * 问题(4) 2006.09.15 徐春英修改  修改原因:导出后有导出成功的提示
     * 问题(5) 2006.09.19 徐春英修改  修改原因:更新时点退出按钮要删除放在hashMap里的加锁对象
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("SAVE"))
        {
            save();
        }
        if (e.getActionCommand().equals("EXIT"))
        {

            String state = editPanel.getState();
            if (!state.equals("VIEW"))
            {
                // 显示确认对话框
                int reply = JOptionPane.showConfirmDialog(this.editPanel,
                        "信息没有被保存，是否继续",
                        res.getString("exitwindow"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (reply == JOptionPane.YES_OPTION)
                {
                    mainFrame.removePanel();
                    //问题(6)2006.11.15 徐春英修改  修改原因:如果是退出另存为界面
                    //则summaryObject对象还没有持久化,解锁的话会出错
                    if (summaryObject.getBsoID() != null)
                    {
                        this.unLock(summaryObject);
                    }
                    //问题(5) 2006.09.19 徐春英修改  修改原因:更新时点退出按钮要删除放在hashMap里的加锁对象
                    if (mainFrame.mainControl.getHashMap() != null)
                    {
                        mainFrame.mainControl.getHashMap().remove(summaryObject.
                                getBsoID());
                    }
                }
            }
            else
            {
                mainFrame.removePanel();
            }
            /*
                   String state = editPanel.getState();
                   if (state.equals("RESUM")) {
              preResum();
                   }
                   if (state.equals("UPDATE")) {
              preUpdate();
                   }
                   if (state.equals("SAVEAS")) {
              preSaveAs();
                   }
             */
        }
        /**
         * 导出按钮实现
         */
        if (e.getActionCommand().equals("EXPORT"))
        {     
        	//begin CR2
        	Vector vector1 = editPanel.exportResult();
        	  //CCBegin SS1
        	  Vector vector = null;
                if(summaryObject.getName()!=null&&!summaryObject.getName().equals(""))
                {
                	String title = res.getString("export");
            File file = null;
            String str;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(title);
            fileChooser.setApproveButtonText(title);
            int interval = fileChooser.showOpenDialog(mainFrame);
            if (interval == fileChooser.APPROVE_OPTION)
            {
                file = fileChooser.getSelectedFile();
                	if(summaryObject.getName().equals("设备清单"))
                	{
                	String department = "";
                	String partnum = "";
                	String partname = "";
                	String filename = file.getAbsolutePath();
                	
                	if (!filename.endsWith(".xls"))
                	{
                		filename += ".xls";
                	}
                	System.out.println("filename========="+filename);
                	String s = summaryObject.getDisplayQuery();
                	StringTokenizer st = new StringTokenizer(s, ";");
                	for (int i = 0; st.hasMoreTokens(); i++) 
                	{
                		String ss = st.nextToken();
                		if (ss.indexOf("零部件号") != -1)
                		{
                			partnum = ss.substring(ss.indexOf("零部件号") + 5);
                			//System.out.println("partnum========="+partnum);
                			//获得零部件名称
                			QMPartMasterIfc part = this.getPartIfc(partnum);
                			if (part != null)
                			{
                				partname = part.getPartName();
                				//System.out.println("partname========="+partname);
                				//获得零部件首用车型，用于冲压车间工具明细表
                				//  chexing = this.getFirstCarType(part.getBsoID());
                			}
                		}
                		if (ss.indexOf("部门") != -1)
                		{
                			department = ss.substring(ss.indexOf("部门") + 3);
                		  //System.out.println("department========="+department);
                		}
                	}
                	EquipmentQDController sbqd = new EquipmentQDController();
                	vector.remove(0); //去掉表列名
                	sbqd.printFile(editPanel, vector, department, filename, partnum, partname);
                  }
                  else
                  {
                  	String partnum = "";
                  	String toolType = "";
                  	String department = "";
                  	String partname = "";
                  	String filename = file.getAbsolutePath();
                  	String s = summaryObject.getDisplayQuery();
                  	
                  	StringTokenizer st = new StringTokenizer(s, ";");
                  	for (int i = 0; st.hasMoreTokens(); i++)
                  	{
                  		String ss = st.nextToken();
                  		if (ss.indexOf("零部件号") != -1)
                  		{
                  			partnum = ss.substring(ss.indexOf("零部件号") + 5);
                  			//System.out.println("partnum========="+partnum);
                  			//获得零部件名称
                  			QMPartMasterIfc part = this.getPartIfc(partnum);
                  			if (part != null)
                  			{
                  				partname = part.getPartName();
                  				//System.out.println("partname========="+partname);
                  				//获得零部件首用车型，用于冲压车间工具明细表
                  				//  chexing = this.getFirstCarType(part.getBsoID());
                  			}
                  		}
                  		if (ss.indexOf("工装类型") != -1)
                  		{
                  			toolType = ss.substring(ss.indexOf("工装类型") + 5);
                  			//System.out.println("toolType========="+toolType);
                  		}
                  		if (ss.indexOf("部门") != -1)
                  		{
                  			department = ss.substring(ss.indexOf("部门") + 3);
                  			//System.out.println("department========="+department);
                  		}
                  	}
                  	if (!filename.endsWith(".xls"))
                  	{
                  		filename += ".xls";
                  	}
                  	//System.out.println("filename========="+filename);
                  	if (summaryObject.getName().equals("刀具一览表"))
                  	{
                  		ToolYLBDController mxb = new ToolYLBDController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("磨具一览表"))
                  	{
                  		ToolYLBMController mxb = new ToolYLBMController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("万能工具清单"))
                  	{
                  		ToolWNQDController mxb = new ToolWNQDController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("夹具明细表"))
                  	{
                  		ToolMXBController mxb = new ToolMXBController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("质量万能量具"))
                  	{
                  		ToolZBWNGJController mxb = new ToolZBWNGJController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("质量检具"))
                  	{
                  		ToolZBJUController mxb = new ToolZBJUController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("质量专用量具"))
                  	{
                  		ToolZBZYLZController mxb = new ToolZBZYLZController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("装配工具一览表"))
                  	{
                  		ToolZPGJYLBController mxb = new ToolZPGJYLBController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("夹辅具一览表"))
                  	{
                  		ToolJFJYLBController mxb = new ToolJFJYLBController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  }
                }
                }
                else
                {
                //CCEnd SS1
        	exportSummaryQMTechnics(vector1);
        }
        	/*
            String title = res.getString("export");
            File file = null;
            String str;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(title);
            fileChooser.setApproveButtonText(title);
            int interval = fileChooser.showOpenDialog(mainFrame);
            if (interval == fileChooser.APPROVE_OPTION)
            {
                file = fileChooser.getSelectedFile();
                mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                Vector vector = null;
                if (editPanel.getTotalResult() != null)
                {
                    vector = editPanel.exportResult();
                }
                Class[] lass =
                        {
                        Vector.class, String.class, Boolean.TYPE};
                Object[] obj =
                        {
                        vector,
                        new StringTokenizer(file.getAbsolutePath(), ".").
                        nextElement(),
                        new Boolean(false)};
                try
                {
                    str = (String) getServiceObject("TotalService",
                            "exportTotalResult",
                            lass, obj);
                }
                catch (QMRemoteException ex)
                {
                    JOptionPane.showMessageDialog(mainFrame,
                                                  ex.getClientMessage());
                    mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    return;
                }
                try
                {
                     this.writeFile(str,file.getAbsolutePath(),false);
                }
                catch (QMException ex1)
                {
                    JOptionPane.showMessageDialog(mainFrame,
                                                  ex1.getClientMessage());
                    mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    return;
                }
                //问题(4) 2006.09.15 徐春英修改  修改原因:导出后有导出成功的提示
                JOptionPane.showMessageDialog(mainFrame,
                                              QMMessage.getLocalizedMessage(
                        RESOURCE, "export success", null),
                                              QMMessage.getLocalizedMessage(
                        RESOURCE,
                        "hint", null), JOptionPane.INFORMATION_MESSAGE);
                mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            */
        	//end CR2
        	}
    }


    /**
     * 返回编辑面板的状态
     * @return String
     */
    public String getState()
    {
        return editPanel.getState();
    }


    /**
     * 更新汇总结果之前的提示
     * @return boolean
     */
    public boolean preUpdate()
    {
        String str100 = QMMessage.getLocalizedMessage(RESOURCE, "10", null);
        String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
        int i = JOptionPane.showConfirmDialog(mainFrame, str100, title,
                                              JOptionPane.YES_NO_OPTION);
        return validateExit(i);
    }


    /**
     * 重新汇总汇总结果之前的提示
     * @return boolean
     */
    public boolean preResum()
    {
        String str100 = QMMessage.getLocalizedMessage(RESOURCE, "12", null);
        String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
        int i = JOptionPane.showConfirmDialog(mainFrame, str100, title,
                                              JOptionPane.YES_NO_OPTION);
        return validateExit(i);
    }


    /**
     * 另存为汇总结果之前的提示
     * @return boolean
     */
    public boolean preSaveAs()
    {
        String str100 = QMMessage.getLocalizedMessage(RESOURCE, "11", null);
        String title = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
        int i = JOptionPane.showConfirmDialog(mainFrame, str100, title,
                                              JOptionPane.YES_NO_OPTION);
        return validateExit(i);
    }


    /**
     * 退出界面的控制方法
     * @param i int
     * @return boolean
     */
    public boolean validateExit(int i)
    {
        boolean is = true;
        switch (i)
        {
            case JOptionPane.CLOSED_OPTION:
            {
            	editPanel.setState();
                return false;
            }
            case JOptionPane.YES_OPTION:
            {
                is = save();
                if (!is)
                {
                    return is;
                }
                mainFrame.repaint();
                editPanel.setState();
                break;
            }
            case JOptionPane.NO_OPTION:
            {
                mainFrame.removePanel();
                editPanel.setState();
            }
        }
        
        return true;
    }


    /**
     * 更新汇总结果判断属性类型
     * @return String[]
     */
    public String[] jugType()
    {
        String[] strs = null;
        try
        {
            TotalAttrSchemaIfc[] attrSchema = summaryObject.getAttrSchemas();
            strs = new String[attrSchema.length];
            for (int aa = 0; aa < attrSchema.length; aa++)
            {
                strs[aa] = attrSchema[aa].getAttributeType();
            }
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
        return strs;
    }


    /**
     * 设置约束
     * @param info TotalSchemaItemIfc
     */
    public void setTotalSchema(TotalSchemaItemIfc info)
    {
        summaryObject = info;
    }


    /**
     * 得到编辑面板
     * @return EditPanel
     */
    public EditPanel getPanel()
    {
        return editPanel;
    }


    /**
     * 设置汇总约束的基本信息
     * 问题(7)2006.11.16 徐春英修改  修改原因:另存为时输入不存在的资料夹后，点击保存按钮
     * 提示资料夹路径无效，点击确定后，程序仍继续执行并保存成功。
     * 保存后汇总的位置为源汇总所在的资料夹。
     * 在用户点击确定后，应中止执行并允许用户重新录入正确的资料夹路径。
     */
    private void setTotalBasicInfo()
            throws QMException
    {
        try
        {
            summaryObject.setTecTotalNumber(editPanel.getSumNum());
            summaryObject.setTecTotalName(editPanel.getSumName());
            summaryObject = (TotalSchemaItemIfc) mainFrame.mainControl.
                            assignFolder(
                    summaryObject, editPanel.getFolderLocation());
        }
        catch (QMException ex)
        {
            //JOptionPane.showMessageDialog(this.editPanel, ex.getClientMessage(),
            //res.getString("warning"),
            //JOptionPane.WARNING_MESSAGE);
            throw new QMException(ex);
        }
    }


    /**
     * 保存按钮监听
     * 问题(1)2006.07.10徐春英修改  修改原因:汇总更新时,点击保存出现提示框,关闭提示框出现异常.
     * 问题(2)2006.08.23 徐春英修改 修改原因:重新汇总保存时,没有保存改变后的资料夹
     * 问题(3) 2006.08.31 徐春英修改  修改原因:更新保存之后要删除放在hashMap里的加锁对象
     * 问题(8)2007.01.30 徐春英修改  修改原因:重新汇总之后更新资料夹
     * 问题（9）2007.05.24 徐春英注释  保存更新的时候没必要弹出提示框
     */
    private boolean save()  
    {

        if (editPanel.getFolderLocation() == null)
        {
            JOptionPane.showMessageDialog(this.editPanel,
                                          res.getString("null-Folder"),
                                          res.getString("warning"),
                                          JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!editPanel.isFlag())
        {
            return false;
        }
        String state = editPanel.getState();

        mainFrame.setCursor(Cursor.WAIT_CURSOR);
        if (state.equals("UPDATE"))
        {
            //2006.08.24 徐春英修改
//            TotalResultIfc tresult = editPanel.getTotalResult();
//            try
//            {
//                tresult = (TotalResultIfc) CappClientHelper.refresh(tresult.
//                        getBsoID());
//            }
//            catch (QMRemoteException ex)
//            {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(this.mainFrame,
//                                              ex.getClientMessage());
//                mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
//                mainFrame.removePanel();
//                return false;
//            }
            boolean isSummary = editPanel.addTotalResult();
            if (isSummary)
            {
                //mainFrame.setCursor(Cursor.WAIT_CURSOR);
                return false;
            }
            //薛凯 20080226 添加 添加原因：修改汇总结果的资料夹路径错误导致此次操作无法保存
            Class[] theClass1 =
                    {
                    String.class};
            Object[] myObj1 =
                    {
                    editPanel.getTotalResultIfc().getBsoID()};
            try
            {
                TotalResultIfc tri = (TotalResultIfc) getServiceObject(
                        "PersistService",
                        "refreshInfo", theClass1,
                        myObj1);
                  editPanel.setTotalResultIfc(tri);
            }
            catch (QMRemoteException e)
            {
                e.printStackTrace();
            }
            //薛凯 添加结束
            total = editPanel.getTotalResult();
            Class[] theClass =
                    {
                    TotalResultIfc.class};
            Object[] myObj =
                    {
                    total};
            try
            {
                total = (TotalResultIfc) getServiceObject("TotalService",
                        "saveTotalResult", theClass,
                        myObj);
            }
            catch (QMRemoteException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this.mainFrame,
                                              e.getClientMessage());
                mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                return false;
            }
            //问题（9）2007.05.240徐春英注释  保存更新的时候没必要弹出提示框
//            int result = JOptionPane.showConfirmDialog(mainFrame,
//                    "是否继续更新汇总结果", "提示",
//                    JOptionPane.YES_NO_OPTION);
            //switch (result)
            //{
//                case JOptionPane.YES_OPTION:
//                    editPanel.setTotalResult(total);
//                    mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
//                    break;
//                    //问题(1)2006.07.10徐春英修改  修改原因:汇总更新时,点击保存出现
//                    //提示框,关闭提示框出现异常.
//                case JOptionPane.CLOSED_OPTION:
//                    editPanel.setTotalResult(total);
//                    mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
//                    break;
                //case JOptionPane.NO_OPTION:
                    try
                    {
                        this.setTotalBasicInfo();
                        totalInfo = new TotalInfo();
                        total.setSchema(summaryObject);
                        totalInfo.setResult(total);
                        summaryObject.setResult(total);
                        totalInfo.setSchema(summaryObject);
                        Class[] theClasses =
                                {
                                TotalInfo.class};
                        Object[] myObjs =
                                {
                                totalInfo};
                        //调用TotalService服务的saveTotal方法
                        totalInfo = (TotalInfo) getServiceObject("TotalService",
                                "saveTotal", theClasses, myObjs);
                    }
                    catch (QMException e)
                    {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(mainFrame,
                                e.getClientMessage(),
                                res.getString("warning"),
                                JOptionPane.WARNING_MESSAGE);
                        mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                        return false;
                    }
                    mainFrame.mainControl.setTotalInfo(totalInfo);
                    mainFrame.mainControl.updateTreeCode();
                    //问题(3) 2006.08.31 徐春英修改  修改原因:更新保存之后要删除放在hashMap里的加锁对象
                    mainFrame.mainControl.getHashMap().remove(summaryObject.
                            getBsoID());
                    mainFrame.removePanel();
                    mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                   // break;
            }
        //}
        if (state.equals("SAVEAS"))
        {
            //mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            //问题(7)2006.11.16 徐春英修改  修改原因:另存为时输入不存在的资料夹后，点击保存按钮
            //提示资料夹路径无效，点击确定后，程序仍继续执行并保存成功。
            //保存后汇总的位置为源汇总所在的资料夹。
            //在用户点击确定后，应中止执行并允许用户重新录入正确的资料夹路径。
            try
            {
                this.setTotalBasicInfo();
                totalInfo = new TotalInfo();
                totalInfo.setSchema(summaryObject);
                totalInfo.setResult(summaryObject.getResult());
                Class[] theClass =
                        {
                        TotalInfo.class};
                Object[] myObj =
                        {
                        totalInfo};
                //调用TotalService服务的saveTotal方法
                totalInfo = (TotalInfo) getServiceObject("TotalService",
                        "saveTotal",
                        theClass, myObj);
            }
            catch (QMException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame,
                                              e.getClientMessage(),
                                              res.getString("warning"),
                                              JOptionPane.WARNING_MESSAGE);
                mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                return false;
            }
            mainFrame.mainControl.setTotalInfo(totalInfo);
            mainFrame.mainControl.addTreeCode();
            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
            mainFrame.removePanel();
        }
        if (state.equals("RESUM"))
        {
            mainFrame.setCursor(Cursor.WAIT_CURSOR);
            TotalResultIfc resultSum = null;
            try
            {
                resultSum = summaryObject.getResult();
                Class[] theClass =
                        {
                        TotalResultIfc.class};
                Object[] myObj =
                        {
                        resultSum};
                //调用TotalService服务的saveTotalResult方法
                resultSum = (TotalResultIfc) getServiceObject("TotalService",
                        "saveTotalResult", theClass,
                        myObj);
            }
            catch (QMException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame,
                                              e.getClientMessage(),
                                              res.getString("warning"),
                                              JOptionPane.WARNING_MESSAGE);
                mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
            }
            try
            {
                //问题(8)2007.01.30 徐春英修改  修改原因:重新汇总之后更新资料夹
                setTotalBasicInfo();
                totalInfo = new TotalInfo();
                totalInfo.setResult(resultSum);
                totalInfo.setSchema(summaryObject);
                //调用服务
                Class[] theClasses =
                        {
                        TotalInfo.class};
                Object[] myObjs =
                        {
                        totalInfo};
                //调用TotalService服务的saveTotal方法
                totalInfo = (TotalInfo) getServiceObject("TotalService",
                        "saveTotal", theClasses,
                        myObjs);
            }
            catch (QMException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(mainFrame,
                                              e.getClientMessage(),
                                              res.getString("warning"),
                                              JOptionPane.WARNING_MESSAGE);
                mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
            }
            mainFrame.mainControl.setTotalInfo(totalInfo);
            mainFrame.mainControl.updateTreeCode();
            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
            mainFrame.removePanel();
        }


         return true;
    }
    
    
    //begin CR1
    public void saveBeforeChangePage()
    {
        Class[] theClass1 =
                {
                String.class};
        Object[] myObj1 =
                {
                editPanel.getTotalResultIfc().getBsoID()};
        try
        {
            TotalResultIfc tri = (TotalResultIfc) getServiceObject(
                    "PersistService",
                    "refreshInfo", theClass1,
                    myObj1);
              editPanel.setTotalResultIfc(tri);
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
        }
        total = editPanel.getTotalResult();
        Class[] theClass =
                {
                TotalResultIfc.class};
        Object[] myObj =
                {
                total};
        try
        {
            total = (TotalResultIfc) getServiceObject("TotalService",
                    "saveTotalResult", theClass,
                    myObj);
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainFrame,
                                          e.getClientMessage());
            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
        }
                try
                {
                    this.setTotalBasicInfo();
                    totalInfo = new TotalInfo();
                    total.setSchema(summaryObject);
                    totalInfo.setResult(total);
                    summaryObject.setResult(total);
                    totalInfo.setSchema(summaryObject);
                    Class[] theClasses =
                            {
                            TotalInfo.class};
                    Object[] myObjs =
                            {
                            totalInfo};
                    totalInfo = (TotalInfo) getServiceObject("TotalService",
                            "saveTotal", theClasses, myObjs);
                }
                catch (QMException e)
                {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(mainFrame,
                            e.getClientMessage(),
                            res.getString("warning"),
                            JOptionPane.WARNING_MESSAGE);
                    mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                }
                mainFrame.mainControl.setTotalInfo(totalInfo);
                mainFrame.mainControl.updateTreeCode();
                mainFrame.mainControl.getHashMap().remove(summaryObject.
                        getBsoID());
                mainFrame.removePanel();
                mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
    }
    //end CR1
    //CCBegin SS1
   /**
     * 调用零部件服务，根据零部件编号获得零部件值对象，为获取零部件的其他信息做准备
     * @param partnum String
     * @return QMPartIfc
     */
    public QMPartMasterIfc getPartIfc(String partnum) {
      QMPartMasterIfc partmasterifc = null;
      Collection coll = null;

      try{

          QMQuery query = new QMQuery("QMPartMaster");
          QueryCondition condition = new QueryCondition("partNumber", "=",
          		partnum);
          query.addCondition(condition);
          Class[] theClass = {QMQuery.class};
    	      Object[] theObj = {query};
    	      coll = (Collection)getServiceObject("PersistService","findValueInfo", theClass, theObj);
      }catch(Exception exc){
      	exc.printStackTrace();
      }
      if (coll != null) {
        Iterator iter = coll.iterator();
        partmasterifc = (QMPartMasterIfc) iter.next();
      }
      return partmasterifc;
    }
    //CCEnd SS1
}
