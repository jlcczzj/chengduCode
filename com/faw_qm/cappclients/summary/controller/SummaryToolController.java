/**
 * CR1 2012/04/12 高义升 参见TD2512
 * SS1 2013-2-20   刘家坤 汇总界面增加汇总条件 assetNumberField生产线  eqManuField制造厂家
 * SS2 2013-2-20   刘家坤 增加记录模板类型
 */
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
import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.summary.view.ExportCustomizeDialog;
import com.faw_qm.cappclients.summary.view.SummaryMainJFrame;
import com.faw_qm.cappclients.summary.view.SummaryResultSaveDialog;
import com.faw_qm.cappclients.summary.view.ToolPanel;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.summary.model.TotalAttrSchemaIfc;
import com.faw_qm.summary.model.TotalResultIfc;
import com.faw_qm.summary.model.TotalSchemaIfc;
import com.faw_qm.summary.model.TotalSchemaInfo;
import com.faw_qm.summary.util.BsoMetaInfo;
import com.faw_qm.summary.util.FilterCondition;
import com.faw_qm.summary.util.FilterQuery;
import com.faw_qm.summary.util.TotalConstant;
import com.faw_qm.util.BsoDescript;
import com.faw_qm.util.JNDIUtil;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.cappclients.capp.view.TechnicsSearchJDialog;


/**
 * <p>Title:工装汇总控制类 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 李磊
 * 
 * @version 1.0
 * 问题(1) 2006.08.16 徐春英修改  修改原因:导出后有导出成功的提示
 * 问题(2)2007.06.04 xucy add 通过工艺编号得到工艺及其下面工序的关联工艺的sql
 */

public class SummaryToolController extends SummaryController
{
    public SummaryMainJFrame mainFrame;
    ToolPanel toolPanel;
    private ExportCustomizeDialog ecDialog;
    private SummaryResultSaveDialog resultSaveDialog;
    private QueryCondition techTypeCondition;
    private QueryCondition toolTypeCondition;
    private QueryCondition departmentTypeCondition;
    private FilterCondition toolTypeTypeFilterCondition;
    private FilterCondition techTypeFilterCondition;
    private FilterCondition departmentTypeFilterCondition;
    private FilterQuery toolFilterQuery;
    private TotalSchemaIfc schema;
    private CodingIfc totalType;
    private TotalAttrSchemaIfc[] totalAttrHeads;
    private TotalAttrSchemaIfc[] totalAttributes;
    private int len;
    private WorkThread sumThread;
    private WorkThread sumThread1;
    private SummaryMainController mainControl;
    private CappChooser qmchooser;
    private TotalResultIfc totalResult;

    //CCBegin SS1
    private FilterCondition assetNumberFilterCondition;
    private QueryCondition assetNumberCondition;
    private FilterCondition toolNumFilterCondition;
    private QueryCondition toolNumCondition;
    //CCEnd SS1
    //问题(1) 2006.08.16 徐春英修改  修改原因:导出后有导出成功的提示
    /**用于标记资源文件路径*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.summary.util.SummaryResource";


    /*
     ** 构造函数
     */
    public SummaryToolController(SummaryMainJFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        toolPanel = new ToolPanel(this);
        this.mainControl = mainFrame.mainControl;
    }


    /**
     *内部类：线程
     */
    class WorkThread extends Thread
    {
        int action;
        public WorkThread(int action)
        {
            this.action = action;
        }

        public void run()
        {
            switch (action)
            {
                case SEARCH:

                    //搜索零部件
                    searchQM();
                    break;
                case SUM:
                    mainFrame.setCursor(Cursor.WAIT_CURSOR);
                    mainFrame.setSumStatus(res.getString("wait"));
                    //保存按钮不显示
                    toolPanel.setSaveButtonstate(false);
                    //根据汇总条件进行汇总
                    toolPanel.setTotalResult(summary());
                    //返回汇总结果，在Multilist中显示
                    toolPanel.setToMultilist();
                    //保存按钮显示
                    toolPanel.setSaveButtonstate(true);
                    //设置状态栏的值
                    mainFrame.setSumStatus(toolPanel.getSumInfo());
                    mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                    break;
            }
        }
    }


    /**
     * 搜索零部件
     */
    private void searchQM()
    {
        qmchooser = new CappChooser("QMPart", "零部件搜索", mainFrame);

        qmchooser.setLastIteration(true);
        try
        {
        	//薛凯 20081225 start
            qmchooser.setMultipleMode(true);
            qmchooser.addListener(new CappQueryListener()
            {

                public void queryEvent(CappQueryEvent qmqueryevent)
                {
                    if (qmqueryevent.getType().equals("Command") &&
                        qmqueryevent.getCommand().equals("OK"))
                    {
                        CappChooser qmchooser1 = (CappChooser) qmqueryevent.
                                                 getSource();
                        BaseValueIfc[] lequips = qmchooser1.getSelectedDetails();
                        for (int d = 0; d < lequips.length; d++)
                        {
                            QMPartInfo equip = (QMPartInfo) lequips[d];
                            toolPanel.setPart((QMPartInfo) equip);
                        }
                    }
                }
            });
            qmchooser.setVisible(true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    /**
     * 监听实现
     * @param e ActionEvent
     * 问题(1) 2006.08.16 徐春英修改  修改原因:导出后有导出成功的提示
     */

    public void actionPerformed(ActionEvent e)
    {
        //问题（4）薛凯 20080414  添加原因:工艺编号添加浏览按钮和非复选框
        if (e.getActionCommand().equals("SearchTechnics"))
        {
            try
            {
                TechnicsSearchJDialog tsd = new TechnicsSearchJDialog(mainFrame);
                //禁止选择多个
                tsd.setSelectMore(false);
                tsd.addDefaultListener();
                tsd.setVisible(true);
                Vector v = TechnicsSearchJDialog.vec;
                if (v.size() > 0)
                {
                	for(int i=0;i<v.size();i++)
                	{
                		Object obj = v.get(i);
                        if (obj instanceof QMTechnicsIfc)
                        {
                            QMTechnicsIfc ti = (QMTechnicsIfc) obj;
                            toolPanel.setTechniceNumber(ti.getTechnicsNumber());
                        }
                	}
                }
            }
            catch (QMRemoteException ex2)
            {
                ex2.printStackTrace();
            }
        }
        //问题（4）  添加结束
        if (e.getActionCommand().equals("CUSTO"))
        {

            totalType = (CodingIfc) SummaryMainController.sorts.get("工装汇总");
            //ecDialog = new ExportCustomizeDialog(mainFrame, initial(),
            //                                     totalType);
            //薛凯 修改  20080222 修改原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
            String[] output = getExportCustomizeDialogDefaultOutput();
            ecDialog = new ExportCustomizeDialog(mainFrame, initial(),
                                                 totalType ,output);
            //薛凯 修改结束
            ecDialog.setControll(this);
            ecDialog.setLocation(170, 170);
            ecDialog.setSize(650, 500);
            ecDialog.setVisible(true);
        }
        //CCBegin SS2
         if (e.getActionCommand().equals("gjylbd")) {
            toolPanel.setAttributes(this.getSchemaheadings(2));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setgjylbdButtonState();
            //CCBegin by liunan 2011-9-20 根据汇总定制类型添加工具类型。代码分类层级变动
            toolPanel.sortingSelectedPanel2.setCoding(getClassificationByName("刀具","变速箱分公司工装类型"));
            //CCEnd by liunan 2011-9-20
          }
        if (e.getActionCommand().equals("gjylbm")) {
            toolPanel.setAttributes(this.getSchemaheadings(3));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setgjylbmButtonState();
            //CCBegin by liunan 2011-9-20 根据汇总定制类型添加工具类型。
            toolPanel.sortingSelectedPanel2.setCoding(getClassificationByName("磨具","变速箱分公司工装类型"));
            //CCEnd by liunan 2011-9-20
          }
        if (e.getActionCommand().equals("wngjqd")) {
            toolPanel.setAttributes(this.getSchemaheadings(4));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setwngjqdButtonState();
            //CCBegin by liunan 2011-9-20 根据汇总定制类型添加工具类型。
            toolPanel.sortingSelectedPanel2.setCoding(getCodingByName("万能工具","万能工具","变速箱分公司工装类型"));
            //CCEnd by liunan 2011-9-20
          }
        if (e.getActionCommand().equals("jjmxb")) {
            toolPanel.setAttributes(this.getSchemaheadings(5));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setjjmxbButtonState();
            //CCBegin by liunan 2011-9-20 根据汇总定制类型添加工具类型。
            toolPanel.sortingSelectedPanel2.setCoding(getClassificationByName("夹具","变速箱分公司工装类型"));
            //CCEnd by liunan 2011-9-20
          }
        
        if (e.getActionCommand().equals("zbwnlj")) {
            toolPanel.setAttributes(this.getSchemaheadings(6));            
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setzbwnljButtonState();
            //CCBegin by liunan 2011-9-20 根据汇总定制类型添加工具类型。
            toolPanel.sortingSelectedPanel2.setCoding(getCodingByName("万能量具","量具","变速箱分公司工装类型"));
            //CCEnd by liunan 2011-9-20
          }
        if (e.getActionCommand().equals("zbju")) {
            toolPanel.setAttributes(this.getSchemaheadings(7));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setzbjuButtonState();
            //CCBegin by liunan 2011-9-20 根据汇总定制类型添加工具类型。
            toolPanel.sortingSelectedPanel2.setCoding(getClassificationByName("检具","变速箱分公司工装类型"));
            //CCEnd by liunan 2011-9-20
          }
        if (e.getActionCommand().equals("zbzylj")) {
            toolPanel.setAttributes(this.getSchemaheadings(8));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setzbzyljButtonState();
            //CCBegin by liunan 2011-9-20 根据汇总定制类型添加工具类型。
            toolPanel.sortingSelectedPanel2.setCoding(getCodingByName("专用量具","量具","变速箱分公司工装类型"));
            //CCEnd by liunan 2011-9-20
          }
        //CCBegin by liunan 2012-2-7 新增 “装配工具一览表” “夹辅具一览表”
        if (e.getActionCommand().equals("zpgjylb")) {
            toolPanel.setAttributes(this.getSchemaheadings(9));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setzpgjylbButtonState();
            //CCBegin by liunan 2011-9-20 根据汇总定制类型添加工具类型。
            toolPanel.sortingSelectedPanel2.setCoding(getCodingByName("装配工具","装配工具","变速箱分公司工装类型"));
            //CCEnd by liunan 2011-9-20
          }
        if (e.getActionCommand().equals("jfjylb")) {
            toolPanel.setAttributes(this.getSchemaheadings(10));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setjfjylbButtonState();
            //CCBegin by liunan 2011-9-20 根据汇总定制类型添加工具类型。
            toolPanel.sortingSelectedPanel2.setCoding(getCodingByName("夹辅具","夹辅具","变速箱分公司工装类型"));
            //CCEnd by liunan 2011-9-20
          }
        //CCEnd SS2
        //汇总按钮监听
        if (e.getActionCommand().equals("SUM"))
        {
            sumThread1 = new WorkThread(SUM);
            sumThread1.start();
        }
        //中断按钮监听
        if (e.getActionCommand().equals("INTERRUPT"))
        {
            interruptThread();
            toolPanel.setSaveButtonstate(true);
            mainFrame.setSumStatus(toolPanel.getSumInfo());
            toolPanel.getMultilist().clear();
        }
        //保存按钮监听
        if (e.getActionCommand().equals("SAVE"))
        {
            if (totalType == null)
            {
                totalType = (CodingIfc) SummaryMainController.sorts.get("工装汇总");
            }
            //CCBegin SS2。
            if(toolPanel.getSummaryMode() == 1||toolPanel.getSummaryMode() == 2||toolPanel.getSummaryMode() == 3
               ||toolPanel.getSummaryMode() == 4||toolPanel.getSummaryMode() == 5||toolPanel.getSummaryMode() == 6
               ||toolPanel.getSummaryMode() == 7||toolPanel.getSummaryMode() == 8||toolPanel.getSummaryMode() == 9)
            {
            	String typename = "";
            	if(toolPanel.getSummaryMode() == 1)
            	{
            		typename = "刀具一览表";
            	}
            	else if(toolPanel.getSummaryMode() == 2)
            	{
            		typename = "磨具一览表";
            	}
            	else if(toolPanel.getSummaryMode() == 3)
            	{
            		typename = "万能工具清单";
            	}
            	else if(toolPanel.getSummaryMode() == 4)
            	{
            		typename = "夹具明细表";
            	}
            	else if(toolPanel.getSummaryMode() == 5)
            	{
            		typename = "质量万能量具";
            	}
            	else if(toolPanel.getSummaryMode() == 6)
            	{
            		typename = "质量检具";
            	}
            	else if(toolPanel.getSummaryMode() == 7)
            	{
            		typename = "质量专用量具";
            	}
            	else if(toolPanel.getSummaryMode() == 8)
            	{
            		typename = "装配工具一览表";
            	}
            	else if(toolPanel.getSummaryMode() == 9)
            	{
            		typename = "夹辅具一览表";
            	}
            	resultSaveDialog = new SummaryResultSaveDialog(totalType,
                    mainControl, typename);
            }
            else
            {
            	resultSaveDialog = new SummaryResultSaveDialog(totalType,
                    mainControl);
            }
            //CCEnd SS2
            resultSaveDialog = new SummaryResultSaveDialog(totalType,
                    mainControl);
            mainControl.setDialog(resultSaveDialog);
            resultSaveDialog.setResult(totalResult);
            resultSaveDialog.setAttrSchema(schema);
            mainFrame.removePanel();
            resultSaveDialog.setVisible(true);
        }
        //退出按钮监听
        if (e.getActionCommand().equals("EXIT"))
        {
            mainFrame.removePanel();
        }
        //搜索按钮监听
        if (e.getActionCommand().equals("SEARCH"))
        {
            sumThread = new WorkThread(SEARCH);
            sumThread.start();
        }
        //导出按钮监听
        if (e.getActionCommand().equals("EXPORT"))
        {
        	//CCBegin SS1
        	//Vector vector1 = toolPanel.exportResult();
        	//exportSummaryQMTechnics(vector1);
        	/*
            String title = res.getString("export");
            String str;
            File file = null;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(title);
            fileChooser.setApproveButtonText(title);
            int interval = fileChooser.showOpenDialog(mainFrame);
            if (interval == fileChooser.APPROVE_OPTION)
            {
                file = fileChooser.getSelectedFile();
                mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                Vector vector = null;
                if (totalResult != null)
                {
                    vector = toolPanel.exportResult();
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
                //问题(1) 2006.08.16 徐春英修改  修改原因:导出后有导出成功的提示
                JOptionPane.showMessageDialog(mainFrame,
                                              QMMessage.getLocalizedMessage(
                        RESOURCE, "export success", null),
                                              QMMessage.getLocalizedMessage(
                        RESOURCE,
                        "hint", null), JOptionPane.INFORMATION_MESSAGE);
                mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            */
        	//end CR1
        	  String title = res.getString("export");
              String str;
              File file = null;
              String s = "";
              JFileChooser fileChooser = new JFileChooser();
              fileChooser.setDialogTitle(title);
              fileChooser.setApproveButtonText(title);
              int interval = fileChooser.showOpenDialog(mainFrame);
              if (interval == fileChooser.APPROVE_OPTION)
              {
                  file = fileChooser.getSelectedFile();
                  mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                  Vector vector = null;
                  if (totalResult != null)
                  {
                      vector = toolPanel.exportResult();
                      s = toolPanel.getSumCondition();
                  }
                  if(toolPanel.getSummaryMode() == 1||toolPanel.getSummaryMode() == 2||toolPanel.getSummaryMode() == 3||toolPanel.getSummaryMode() == 4
                  		||toolPanel.getSummaryMode() == 5||toolPanel.getSummaryMode() == 6||toolPanel.getSummaryMode() == 7
                  		//CCBegin by liunan 2012-2-7 新增 “装配工具一览表” “夹辅具一览表”
                  		||toolPanel.getSummaryMode() == 8||toolPanel.getSummaryMode() == 9){
                  		//
                  	exportSRQSummeryResult(vector, s, file.getAbsolutePath());
                  }else{
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
                  //问题(1) 2006.08.16 徐春英修改  修改原因:导出后有导出成功的提示
                  JOptionPane.showMessageDialog(mainFrame,
                                                QMMessage.getLocalizedMessage(
                          RESOURCE, "export success", null),
                                                QMMessage.getLocalizedMessage(
                          RESOURCE,
                          "hint", null), JOptionPane.INFORMATION_MESSAGE);
                  }
                  mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
              }
        	}
    }


    /**
     *中止线程
     * 问题(2) 2006.08.30 徐春英修改  修改原因:中断汇总结果
     */
    public synchronized void interruptThread()
    {
        if (sumThread1 != null && sumThread1.isAlive())
        {
            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
            sumThread1.stop();
            sumThread1 = null;
        }
    }


    /**
     * 调用汇总服务
     * @return TotalResultIfc
     */
    public TotalResultIfc summary()
    {
        try
        {
            Class[] theClass =
                    {
                    TotalSchemaIfc.class,Boolean.TYPE};
            schema = getTotalSchemaIfc();
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("工装汇总");
            schema.setTotalType(code);
            schema.setSubTotalType(1);
            boolean flag = getFlag();
            Object[] theObj ={schema,new Boolean(flag)};
            //调用TotalService服务的totalBySchema方法
            totalResult = (TotalResultIfc) getServiceObject("TotalService",
                    "totalBySchema", theClass, theObj);
            return totalResult;
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.toolPanel, e.getClientMessage());
            return null;
        }
    }


    /**
     * 调用服务，返回BsoMetaInfo[]数组
     * @return BsoMetaInfo[]
     */
    public BsoMetaInfo[] initial()
    {
        try
        {
            Class[] theClass =
                    {
                    CodingIfc.class, Integer.TYPE};
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("工装汇总");
            Object[] myObj =
                    {
                    code, new Integer(TotalConstant.ISGENERAL)};
            //调用TotalService服务的getBsoMetaInfos方法
            BsoMetaInfo[] bsoObject = (BsoMetaInfo[]) getServiceObject(
                    "TotalService",
                    "getBsoMetaInfos", theClass, myObj);
            return bsoObject;
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.toolPanel, e.getClientMessage());
            return null;
        }

    }


    /**
     * 实现过滤条件的封装,返回FilterQuery对象
     * @return FilterQuery
     * 问题(2)2007.06.04 xucy add 通过工艺编号得到工艺及其下面工序的关联工艺的sql
     */
    public FilterQuery getFilterQuery()
    {
        toolFilterQuery = new FilterQuery(false);
        if (totalType == null)
        {
            totalType = (CodingIfc) SummaryMainController.sorts.get("工装汇总");
        }
        //设置汇总类型
        toolFilterQuery.setType(totalType);
        try
        {
            //通过part配置规范得到关于part的SQL表达式
            toolFilterQuery = getPartSQLExpression(toolPanel.getPartNum(),
                    toolFilterQuery);
            //得到工艺类型
            if (toolPanel.getTechType() != null)
            {
                //非工艺种类复选框是否选取
                if (toolPanel.isSelectTech())
                {
                    if (toolFilterQuery.getCount() != 0)
                    {
                        toolFilterQuery.addAND();
                    }
                    //得到符合条件的QueryCondition
                    techTypeCondition = new QueryCondition("technicsType",
                            QueryCondition.NOT_EQUAL,
                            toolPanel.getTechType());
                }
                else
                {
                    if (toolFilterQuery.getCount() != 0)
                    {
                        toolFilterQuery.addAND();
                    }
                    //得到符合条件的QueryCondition
                    techTypeCondition = new QueryCondition("technicsType", "=",
                            toolPanel.getTechType());
                }
                //得到符合条件的techTypeCondition
                techTypeFilterCondition = new FilterCondition("QMFawTechnics",
                        techTypeCondition);
                toolFilterQuery.addCondition(techTypeFilterCondition);
            }
            //得到工装类型
            if (toolPanel.getToolType() != null)
            {
                BaseValueIfc toolType = toolPanel.getToolType();
                if (toolType instanceof CodingIfc)
                {
                    if (toolPanel.isSelectTool())
                    {
                        if (toolFilterQuery.getCount() != 0)
                        {
                            toolFilterQuery.addAND();
                        }
                        //从数据库得到符合条件的QueryCondition
                        toolTypeCondition = new QueryCondition("toolCf",
                                QueryCondition.NOT_EQUAL, toolType.getBsoID());

                    }
                    else
                    {
                        //访问FilterQuery对象中封装的FilterCondition数目，如果数目！NULL
                        if (toolFilterQuery.getCount() != 0)
                        {
                            toolFilterQuery.addAND();
                        }
                        //从数据库得到符合条件的QueryCondition
                        toolTypeCondition = new QueryCondition("toolCf", "=",
                                toolType.getBsoID());
                    }
                    //得到符合条件的toolTypeCondition
                    toolTypeTypeFilterCondition = new FilterCondition("QMTool",
                            toolTypeCondition);
                    toolFilterQuery.addCondition(toolTypeTypeFilterCondition);
                }
                else
                if (toolType instanceof CodingClassificationIfc)
                {
                    Collection col = CappTreeHelper.getOnlyCoding((
                            CodingClassificationIfc) toolType);
                    if (col != null && col.size() > 0)
                    {
                        if (toolFilterQuery.getCount() != 0)
                        {
                            toolFilterQuery.addAND();
                        }
                        Object[] codings = col.toArray();
                        int codSize = codings.length;
                        String[] strs = new String[codSize];
                        for (int i = 0; i < codSize; i++)
                        {
                            strs[i] = ((CodingIfc) codings[i]).getBsoID();
                        }
                        if (toolPanel.isSelectTool())
                        {
                            toolTypeCondition = new QueryCondition("toolCf",
                                    "NOT IN", strs);
                        }
                        else
                        {
                            toolTypeCondition = new QueryCondition("toolCf",
                                    "IN",
                                    strs);
                        }
                        toolTypeTypeFilterCondition = new FilterCondition(
                                "QMTool",
                                toolTypeCondition);
                        toolFilterQuery.addCondition(
                                toolTypeTypeFilterCondition);
                    }
                }
            }
            // 得到工序部门
            if (toolPanel.getProcedureDepart() != null)
            {
                BaseValueIfc depart = toolPanel.getProcedureDepart();
                if (depart instanceof CodingIfc)
                {
                    if (toolFilterQuery.getCount() != 0)
                    {
                        toolFilterQuery.addAND();
                    }
                    if (toolPanel.isSelectDepartment())
                    {
                        //从数据库得到符合条件的QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                QueryCondition.NOT_EQUAL, depart.getBsoID());
                    }
                    else
                    {
                        //从数据库得到符合条件的QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                "=",
                                depart.getBsoID());
                    }
                    //得到符合条件的departmentTypeCondition
                    departmentTypeFilterCondition = new FilterCondition(
                            "QMProcedure",
                            departmentTypeCondition);
                    toolFilterQuery.addCondition(departmentTypeFilterCondition);
                }
                else
                if (depart instanceof CodingClassificationIfc)
                {

                    Collection col = CappTreeHelper.getOnlyCoding((
                            CodingClassificationIfc) depart);
                    if (col != null && col.size() > 0)
                    {
                        if (toolFilterQuery.getCount() != 0)
                        {
                            toolFilterQuery.addAND();
                        }
                        Object[] codings = col.toArray();
                        int codSize = codings.length;
                        String[] strs = new String[codSize];
                        for (int i = 0; i < codSize; i++)
                        {
                            strs[i] = ((CodingIfc) codings[i]).getBsoID();
                        }
                        if (toolPanel.isSelectDepartment())
                        {
                            departmentTypeCondition = new QueryCondition(
                                    "workShop",
                                    "NOT IN", strs);
                        }
                        else
                        {
                            departmentTypeCondition = new QueryCondition(
                                    "workShop", "IN",
                                    strs);
                        }
                        departmentTypeFilterCondition = new FilterCondition(
                                "QMProcedure",
                                departmentTypeCondition);
                        toolFilterQuery.addCondition(
                                departmentTypeFilterCondition);
                    }
                    if (col != null && col.size() == 0)
                    {
                        if (!toolPanel.isSelectDepartment())
                        {
                            if (toolFilterQuery.getCount() != 0)
                            {
                                toolFilterQuery.addAND();
                            }
                            //得到符合条件的FilterCondition
                            departmentTypeCondition = new QueryCondition(
                                    "workShop",
                                    QueryCondition.IS_NULL);
                            //得到符合条件的departmentTypeCondition
                            departmentTypeFilterCondition = new FilterCondition(
                                    "QMProcedure",
                                    departmentTypeCondition);
                            toolFilterQuery.addCondition(
                                    departmentTypeFilterCondition);
                        }
                    }
                }
            }
               //CCBegin SS1
            if (toolPanel.getAssetNumber() != null && !toolPanel.getAssetNumber().trim().equals(""))
        {
         String assetNumber = toolPanel.getAssetNumber();
         System.out.println("assetNumber="+assetNumber);
         if (toolFilterQuery.getCount() != 0)
         {
         toolFilterQuery.addAND();
         }
         assetNumberCondition = new QueryCondition("taskInstructNum", "=",
         assetNumber);
         assetNumberFilterCondition = new FilterCondition(
         "QMTechnics",
         assetNumberCondition);
         toolFilterQuery.addCondition(assetNumberFilterCondition);
         }

         ////CCBegin by chudaming 2008-12-31 增加汇总条件
         if (toolPanel.getToolNum() != null && !toolPanel.getToolNum().trim().equals(""))
       {
        String toolNum1 = toolPanel.getToolNum();
        if (toolFilterQuery.getCount() != 0)
        {
        toolFilterQuery.addAND();
        }
        toolNumCondition = new QueryCondition("toolNum", "=",
        toolNum1);
        toolNumFilterCondition = new FilterCondition(
        "QMTool",
        toolNumCondition);
        toolFilterQuery.addCondition(toolNumFilterCondition);
        }

         //CCEnd SS1
            //问题(2)2007.06.04 xucy add 通过工艺编号得到工艺及其下面工序的关联工艺的sql
//            toolFilterQuery = getAssociateSQLExpression(toolPanel.getTechNum(),
//                    toolFilterQuery);
            boolean bool = toolPanel.isSelectTechnicsBso();
            toolFilterQuery = getAssociateSQLExpression(toolPanel.getTechNum(),
                    toolFilterQuery, bool);
            //问题（4）薛凯 20080414 修改结束

        }
        catch (QMException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.toolPanel, e.getClientMessage());
        }
        System.out.println("toolFilterQuery="+toolFilterQuery.getDisplayQuery());
        return toolFilterQuery;
    }


    /**
     * 得到汇总的约束
     * @return TotalSchemaIfc
     */
    public TotalSchemaIfc getTotalSchemaIfc()
    {
        TotalSchemaIfc totalSchema = new TotalSchemaInfo();
        PartConfigSpecIfc configSpec = mainControl.getConfigSpec().
                                       getConfigSpecInfo();
        this.setPartConfig(null);
        //结构复选框是否选取
        if (toolPanel.isSelectPart())
        {
            //设置汇总当前零部件中的子件及其结构数据，真、假  默认为假
            totalSchema.setTotalStructure(true);
            //访问零件配置规范
            totalSchema.setConfigSpec(configSpec);
            //设置配置规范
            this.setPartConfig(configSpec);
        }
        //设置属性模型信息集，返回属性约束模型值对象
        totalSchema.setAttrSchemas(getBsoNameAtt());
        //实现过滤条件的封装,返回FilterQuery对象
        totalSchema.setFilterQuery(getFilterQuery());
        //设置显示条件表达式
        totalSchema.setDisplayQuery(toolPanel.getSumCondition());
        String partNumber = toolPanel.getPartNum();
        if (partNumber != null)
        {
        	//薛凯 修改 20081016 准许用户选择多个零部件进行汇总
        	String[] partNums = getMultipleCondition(partNumber);
        	String bsoID = null;
        	for(int i =0;i<partNums.length;i++)
        	{
        		String part = this.getPartBsoID(partNums[i]);
        		if(part!=null)
        		{
        			if(bsoID!=null)
            		{
            			bsoID = bsoID + ";" + part;
            		}
        			else
        			{
        				bsoID = part;
        			}
        		}
        	}
        	//薛凯修改结束 20081016
            if (bsoID != null)
            {
                totalSchema.setPartID(bsoID);
            }
        }
        return totalSchema;
    }


    /**
     * 得到定制输出的显示名
     * @return String[]
     */

    public String[] getAttrName()
    {
        return ecDialog.getAttrDisplayname();
    }


    /**
     * 设置定制输出时的表头
     */

    public void setMultiList()
    {
        toolPanel.setAttributes(getAttrName());
        toolPanel.updateHeadings();
        toolPanel.setMultilistRow();
        toolPanel.newPaginatePanel();
    }


    /**
     * 设置并返回属性约束模型值对象
     * @return TotalAttrSchemaIfc[]
     */

    public TotalAttrSchemaIfc[] getBsoNameAtt()
    {
        if (ecDialog != null)
        {
            if (ecDialog.isFlag())
            {
                totalAttributes = ecDialog.getAttributes();
                return totalAttributes;
            }
            else
            {
                if (ExportCustomizeDialog.flag == 1)
                {
                    return totalAttributes;
                }
                else
                {
                    return totalAttrHeads;
                }
            }
        }
        else
        {
            return totalAttrHeads;
        }
    }


    /**
     * 调用服务,返回TotalATtrSchemaIfc[]数组
     * @return TotalAttrSchemaIfc[]
     */

    public TotalAttrSchemaIfc[] initSchema()
    {
        try
        {
        	//20081128 薛凯 修改 修改原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
        	CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("工装汇总");
        	totalAttrHeads = (TotalAttrSchemaIfc[])getServiceObject("TotalService",
        			"getUserCustomizeOutput",new Class[]{CodingIfc.class},new Object[]{code});
        	if(totalAttrHeads==null)
        	{
        		Class[] theClass =
                {
                CodingIfc.class, Integer.TYPE};
        Object[] myObj =
                {
                code, new Integer(TotalConstant.ISGENERAL)};
        //调用TotalService服务的getDefaultOutputSchema方法。
        totalAttrHeads = (TotalAttrSchemaIfc[]) getServiceObject(
                "TotalService",
                "getDefaultOutputSchema", theClass, myObj);
        	}
            //薛凯 20081128 修改结束
            return totalAttrHeads;
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.toolPanel, e.getClientMessage());
            return null;
        }

    }
    
    public TotalAttrSchemaIfc[] initSchematobsx()
    {
        try
        {
        	//20081128 薛凯 修改 修改原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
        	CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("工装汇总");
        	TotalAttrSchemaIfc[] AttrHeads = (TotalAttrSchemaIfc[])getServiceObject("TotalService",
        			"getUserCustomizeOutput",new Class[]{CodingIfc.class},new Object[]{code});
        	if(AttrHeads==null)
        	{
        		Class[] theClass =
                {
                CodingIfc.class, Integer.TYPE};
        Object[] myObj =
                {
                code, new Integer(TotalConstant.ISGENERAL)};
        //调用TotalService服务的getDefaultOutputSchema方法。
        AttrHeads = (TotalAttrSchemaIfc[]) getServiceObject(
                "TotalService",
                "getDefaultOutputSchema", theClass, myObj);
        	}
            //薛凯 20081128 修改结束
            return AttrHeads;
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.toolPanel, e.getClientMessage());
            return null;
        }

    }


    /*
     **获得属性表头
     */
    public String[] getSchemaheadings()
    {
        TotalAttrSchemaIfc[] totalAttrName = initSchema();
        len = totalAttrName.length;
        String[] heads = new String[len];
        if (totalAttrName == null)
        {
            totalAttrName = new TotalAttrSchemaIfc[len];
        }
        for (int k = 0; k < len; k++)
        {
            heads[k] = totalAttrName[k].getAttrDisplayName();
        }
        return heads;
    }


    /*
     **
     */
    public int getHeadsCount()
    {
        return len;
    }


    /**
     * 得到属性表头的个数
     * @return int
     */

    public int getAttrCount()
    {
        return ecDialog.getAttrCount();
    }


    /**
    * 薛凯 添加 20080222
    * 添加原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
    * @return String[]
    */
    public String[] getExportCustomizeDialogDefaultOutput()
    {
        String[] output = null;
        TotalAttrSchemaIfc[] defaultAttrOutput = initSchematobsx();
//      TotalAttrSchemaIfc[] defaultAttrOutput = getBsoNameAtt();
        if (defaultAttrOutput.length > 0)
        {
            output = new String[defaultAttrOutput.length];
        }
        for (int i = 0; i < defaultAttrOutput.length; i++)
        {
            String s = null;
            BsoDescript bd = JNDIUtil.getBsoDescript(defaultAttrOutput[i].getAttrBsoName());
            s = bd.getFeature(TotalConstant.DISPLAYNAME).trim();
            if (s != null)
            {
                s += "." + defaultAttrOutput[i].getAttrDisplayName();
                output[i] = s;
            }
        }
        return output;
    }
    //薛凯 添加结束
    //CCBegin SS1
    /*
     **获得属性表头
     */
    public String[] getSchemaheadings(int i) {
      TotalAttrSchemaIfc[] totalAttrName = initSchema(i);
      len = totalAttrName.length;
      String[] heads = new String[len];
      if (totalAttrName == null) {
        totalAttrName = new TotalAttrSchemaIfc[len];
      }
      for (int k = 0; k < len; k++) {
        heads[k] = totalAttrName[k].getAttrDisplayName();
        
      }
      //System.out.println("headsheads==="+heads);
      return heads;
    }
    /**
     * 调用服务,返回TotalATtrSchemaIfc[]数组
     * @return TotalAttrSchemaIfc[]
     */

    public TotalAttrSchemaIfc[] initSchema(int i) {
      try {
        Class[] theClass = {
            CodingIfc.class, Integer.TYPE};
        CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("工装汇总");
        Object[] myObj = {
            code, new Integer(i)};
        //调用TotalService服务的getDefaultOutputSchema方法。
        totalAttrHeads = (TotalAttrSchemaIfc[]) getServiceObject(
            "TotalService",
            "getDefaultOutputSchema", theClass, myObj);
        return totalAttrHeads;
      }
      catch (QMRemoteException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this.toolPanel, e.getClientMessage());
        return null;
      }

    }
    //CCBegin by liunan 2011-9-20
    /**
 * 获取代码管理中的值.
 * @param name String 代码管理中当前分类节点的名称.
 * @param name String 代码管理中当前分类的名称.
 * @param parent String 代码管理中当前分类节点的父节点名称.
 * @return String[] 包含的值集合.
 */
protected BaseValueIfc getCodingByName(String name, String className, String parent)
{
	BaseValueIfc ifc = null;
  try 
  {
    Class[] class1 = {String.class, String.class, String.class};
    Object[] param = {name, className, parent};
    CodingIfc ccifc = (CodingIfc) getServiceObject(
        "CodingManageService", "findCodingByContent", class1, param);
    ifc = (BaseValueIfc)ccifc;
  }
  catch (QMException ex)
  {
    ex.printStackTrace();
  }
  return ifc;
}

      /**
 * 获取代码管理中的值.
 * @param name String 代码管理中当前分类节点的名称.
 * @param parent String 代码管理中当前分类节点的父节点名称.
 * @return String[] 包含的值集合.
 */
protected BaseValueIfc getClassificationByName(String name, String parent)
{
	BaseValueIfc ifc = null;
  try 
  {
    Class[] class1 = {String.class, String.class};
    Object[] param = {name, parent};
    CodingClassificationIfc ccifc = (CodingClassificationIfc) getServiceObject(
        "CodingManageService", "findClassificationByName", class1, param);
    ifc = (BaseValueIfc)ccifc;
  }
  catch (QMException ex)
  {
    ex.printStackTrace();
  }
  return ifc;
}
//CCEnd SS1
//CCBegin SS2
//CCEnd by liunan 2011-9-20    
public void exportSRQSummeryResult(Vector v, String condition,
        String filename) {
if(!filename.endsWith(".xls")){
filename = filename + ".xls";
   }
File file1 = new File(filename);
if(file1.exists()){
 int result1 = JOptionPane.showConfirmDialog(mainFrame,
                        "在此目录下有此文件，是否覆盖", "提示",
                        JOptionPane.YES_NO_OPTION);
   if (result1 == 1)
   return;
   }
   String s = condition;
   String partnum = "";
   String toolType = "";
   String department = "";
   String partname = "";
   StringTokenizer st = new StringTokenizer(s, ";");
   for (int i = 0; st.hasMoreTokens(); i++) {
   String ss = st.nextToken();
   if (ss.indexOf("零部件号") != -1) {
   partnum = ss.substring(ss.indexOf("零部件号") + 5);
  //获得零部件名称
   QMPartMasterIfc part = this.getPartIfc(partnum);
   if (part != null) {
   partname = part.getPartName();
   //获得零部件首用车型，用于冲压车间工具明细表
    //  chexing = this.getFirstCarType(part.getBsoID());
     }
     }
    if (ss.indexOf("工装类型") != -1) {
    toolType = ss.substring(ss.indexOf("工装类型") + 5);
    }
    if (ss.indexOf("部门") != -1) {
    department = ss.substring(ss.indexOf("部门") + 3);
     }
     }
    if (!filename.endsWith(".xls")) {
    filename += ".xls";
    }
   if (toolPanel.getSummaryMode() == 1) {
	   ToolYLBDController mxb = new ToolYLBDController();
       v.remove(0); 
       mxb.printFile(toolPanel, v, department, filename,partnum,partname);
     }
   else if (toolPanel.getSummaryMode() == 2) {
	   ToolYLBMController mxb = new ToolYLBMController();
       v.remove(0); 
       mxb.printFile(toolPanel, v, department, filename,partnum,partname);
      }
   else if (toolPanel.getSummaryMode() == 3) {
	   ToolWNQDController mxb = new ToolWNQDController();
       v.remove(0); 
       mxb.printFile(toolPanel, v, department, filename,partnum,partname);
   }
   else if (toolPanel.getSummaryMode() == 4) {
       ToolMXBController mxb = new ToolMXBController();
        v.remove(0); 
        mxb.printFile(toolPanel, v, department, filename,partnum,partname);
         }
   else if (toolPanel.getSummaryMode() == 5) {
       ToolZBWNGJController mxb = new ToolZBWNGJController();
        v.remove(0); 
        mxb.printFile(toolPanel, v, department, filename,partnum,partname);
         }
   else if (toolPanel.getSummaryMode() == 6) {
       ToolZBJUController mxb = new ToolZBJUController();
        v.remove(0); 
        mxb.printFile(toolPanel, v, department, filename,partnum,partname);
         }
   else if (toolPanel.getSummaryMode() == 7) {
       ToolZBZYLZController mxb = new ToolZBZYLZController();
        v.remove(0); 
        mxb.printFile(toolPanel, v, department, filename,partnum,partname);
         }
   //CCBegin by liunan 2012-2-7 新增 “装配工具一览表” “夹辅具一览表”
   else if (toolPanel.getSummaryMode() == 8) {
       ToolZPGJYLBController mxb = new ToolZPGJYLBController();
        v.remove(0); 
        mxb.printFile(toolPanel, v, department, filename,partnum,partname);
         }
   else if (toolPanel.getSummaryMode() == 9) {
       ToolJFJYLBController mxb = new ToolJFJYLBController();
        v.remove(0); 
        mxb.printFile(toolPanel, v, department, filename,partnum,partname);
         }
   //CCEnd by liunan 2012-2-8     
}
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
//CCEnd SS2
}
