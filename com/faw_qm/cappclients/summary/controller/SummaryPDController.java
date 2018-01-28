/**
 * CR1 2012/04/12 高义升 参见TD2512
 */
package com.faw_qm.cappclients.summary.controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.summary.view.ExportCustomizeDialog;
import com.faw_qm.cappclients.summary.view.PDPanel;
import com.faw_qm.cappclients.summary.view.SummaryMainJFrame;
import com.faw_qm.cappclients.summary.view.SummaryResultSaveDialog;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartInfo;
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
import com.faw_qm.cappclients.capp.view.TechnicsSearchJDialog;
import com.faw_qm.capp.model.QMTechnicsIfc;


/**
 * <p>Title:工序汇总控制类 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 李磊
 * @version 1.0
 * 问题(2) 2006.08.16 徐春英修改  修改原因:导出后有导出成功的提示
 * 问题(3) 2006.08.30 徐春英修改  修改原因:中断汇总结果
 * 问题(3) 2007.06.05 xucy add 通过工艺编号得到工艺及其下面工序的关联工艺的sql
 */

public class SummaryPDController extends SummaryController
{
    public SummaryMainJFrame mainFrame;
    PDPanel pdPanel;
    private ExportCustomizeDialog ecDialog;
    private SummaryResultSaveDialog resultSaveDialog;
    private QueryCondition techTypeCondition;
    private QueryCondition procedureTypeCondition;
    private QueryCondition departmentTypeCondition;
    private FilterCondition techTypeFilterCondition;
    private FilterCondition prodedureTypeFilterCondition;
    private FilterCondition departmentTypeFilterCondition;
    private FilterQuery pdFilterQuery;
    private TotalAttrSchemaIfc[] totalAttrHeads;
    private TotalAttrSchemaIfc[] totalAttributes;
    private TotalSchemaIfc schema;
    private CodingIfc totalType;
    private int len;
    private TotalResultIfc totalResult;
    private SummaryMainController mainControl;
    private WorkThread sumThread;
    private WorkThread sumThread1;
    private CappChooser qmchooser;


    /**用于标记资源文件路径*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.summary.util.SummaryResource";


    /*
     ** 构造函数
     */
    public SummaryPDController(SummaryMainJFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        pdPanel = new PDPanel(this);
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
                    pdPanel.setSaveButtonstate(false);
                    //根据汇总条件进行汇总
                    pdPanel.setTotalResult(summary());
                    //返回汇总结果，在Multilist中显示
                    pdPanel.setToMultilist();
                    //保存按钮显示
                    pdPanel.setSaveButtonstate(true);
                    //设置状态栏的值
                    mainFrame.setSumStatus(pdPanel.getSumInfo());
                    mainFrame.setCursor(Cursor.getDefaultCursor());
                    break;
            }
        }
    }


    /*
     ** 搜索零部件
     */
    private void searchQM()
    {
        qmchooser = new CappChooser("QMPart", "零部件搜索", mainFrame);

        try
        {
        	//薛凯 20081225 start
            qmchooser.setMultipleMode(true);
            qmchooser.setLastIteration(true);
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
                            pdPanel.setPart((QMPartInfo) equip);
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


    /*
     ** 监听实现
     * 问题(2) 2006.08.16 徐春英修改  修改原因:导出后有导出成功的提示
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
                            pdPanel.setTechniceNumber(ti.getTechnicsNumber());
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
            totalType = (CodingIfc) SummaryMainController.sorts.get("工序汇总");
            //ecDialog = new ExportCustomizeDialog(mainFrame, initial(),
             //                                    totalType);
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
            pdPanel.setSaveButtonstate(true);
            mainFrame.setSumStatus(pdPanel.getSumInfo());
            pdPanel.getMultilist().clear();
        }
        //保存按钮监听
        if (e.getActionCommand().equals("SAVE"))
        {
            if (totalType == null)
            {
                totalType = (CodingIfc) SummaryMainController.sorts.get("工序汇总");
            }
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
        	//begin CR1
        	Vector vector1 = pdPanel.exportResult();
        	exportSummaryQMTechnics(vector1);
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
                    vector = pdPanel.exportResult();
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
                //问题(2) 2006.08.16 徐春英修改  修改原因:导出后有导出成功的提示
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
        	}
    }


    /*
     ** 调用汇总服务
     */
    public TotalResultIfc summary()
    {
        try
        {
            Class[] theClass = {TotalSchemaIfc.class, Boolean.TYPE};
            schema = getTotalSchemaIfc();
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("工序汇总");
            schema.setTotalType(code);
            schema.setSubTotalType(1);
            boolean flag = getFlag();
            Object[] theObj ={schema, new Boolean(flag)};
            //调用TotalService服务的totalBySchema方法
            totalResult = (TotalResultIfc) getServiceObject("TotalService",
                    "totalBySchema", theClass, theObj);
            return totalResult;
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.pdPanel, e.getClientMessage());
            return null;
        }

    }


    /*
     ** 调用服务，返回BsoMetaInfo[]数组
     */
    public BsoMetaInfo[] initial()
    {
        try
        {
            Class[] theClass =
                    {
                    CodingIfc.class, Integer.TYPE};
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("工序汇总");
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
            JOptionPane.showMessageDialog(this.pdPanel, e.getClientMessage());
            return null;
        }
    }


    /**
     * 中止线程
     * 问题(3) 2006.08.30 徐春英修改  修改原因:中断汇总结果
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
     * 实现过滤条件的封装,返回FilterQuery对象
     * @return FilterQuery
     * 问题(3) 2007.06.05 xucy add 通过工艺编号得到工艺及其下面工序的关联工艺的sql
     */
    public FilterQuery getFilterQuery()
    {
        pdFilterQuery = new FilterQuery(false);
        if (totalType == null)
        {
            totalType = (CodingIfc) SummaryMainController.sorts.get("工序汇总");
        }
        //设置汇总类型
        pdFilterQuery.setType(totalType);
        try
        {
            //通过part配置规范得到关于part的SQL表达式
            pdFilterQuery = getPartSQLExpression(pdPanel.getPartNum(),
                                                 pdFilterQuery);
            //得到工艺类型
            if (pdPanel.getTechType() != null)
            {
                //非工艺种类复选框是否选取
                if (pdPanel.isSelectTech())
                {
                    if (pdFilterQuery.getCount() != 0)
                    {
                        pdFilterQuery.addAND();
                    }
                    //得到符合条件的QueryCondition]
                    techTypeCondition = new QueryCondition("technicsType",
                            QueryCondition.NOT_EQUAL,
                            pdPanel.getTechType());
                }
                else
                {
                    if (pdFilterQuery.getCount() != 0)
                    {
                        pdFilterQuery.addAND();
                    }
                    //得到符合条件的QueryCondition
                    techTypeCondition = new QueryCondition("technicsType", "=",
                            pdPanel.getTechType());
                }
                //得到符合条件的techTypeCondition
                techTypeFilterCondition = new FilterCondition("QMFawTechnics",
                        techTypeCondition);
                pdFilterQuery.addCondition(techTypeFilterCondition);
            }
            //得到工序类型
            if (pdPanel.getPdType() != null)
            {
                BaseValueIfc pdType = pdPanel.getPdType();
                if (pdType instanceof CodingIfc)
                {
                    if (pdPanel.isSelectProcedure())
                    {
                        if (pdFilterQuery.getCount() != 0)
                        {
                            pdFilterQuery.addAND();
                        }
                        //从数据库得到符合条件的QueryCondition
                        procedureTypeCondition = new QueryCondition(
                                "stepClassification",
                                QueryCondition.NOT_EQUAL, pdType.getBsoID());
                    }
                    else
                    {
                        //访问FilterQuery对象中封装的FilterCondition数目，如果数目！NULL
                        if (pdFilterQuery.getCount() != 0)
                        {
                            pdFilterQuery.addAND();
                        }
                        //从数据库得到符合条件的QueryCondition
                        procedureTypeCondition = new QueryCondition(
                                "stepClassification",
                                "=", pdType.getBsoID());
                    }
                    //得到符合条件的procedureTypeCondition
                    prodedureTypeFilterCondition = new FilterCondition(
                            "QMProcedure",
                            procedureTypeCondition);
                    pdFilterQuery.addCondition(prodedureTypeFilterCondition);
                }
                else
                if (pdType instanceof CodingClassificationIfc)
                {
                    Collection col = CappTreeHelper.getOnlyCoding((
                            CodingClassificationIfc) pdType);
                    if (col != null && col.size() > 0)
                    {
                        if (pdFilterQuery.getCount() != 0)
                        {
                            pdFilterQuery.addAND();
                        }
                        Object[] codings = col.toArray();
                        int codSize = codings.length;
                        String[] strs = new String[codSize];
                        for (int i = 0; i < codSize; i++)
                        {
                            strs[i] = ((CodingIfc) codings[i]).getBsoID();
                        }
                        if (pdPanel.isSelectProcedure())
                        {
                            procedureTypeCondition = new QueryCondition(
                                    "stepClassification",
                                    "NOT IN", strs);
                        }
                        else
                        {
                            procedureTypeCondition = new QueryCondition(
                                    "stepClassification", "IN",
                                    strs);
                        }
                        prodedureTypeFilterCondition = new FilterCondition(
                                "QMProcedure",
                                procedureTypeCondition);
                        pdFilterQuery.addCondition(prodedureTypeFilterCondition);
                    }
                }
            }

            if (pdPanel.getProcedureDepart() != null)
            {
                BaseValueIfc depart = pdPanel.getProcedureDepart();
                if (depart instanceof CodingIfc)
                {
                    if (pdPanel.isSelectDepartment())
                    {
                        if (pdFilterQuery.getCount() != 0)
                        {
                            pdFilterQuery.addAND();
                        }
                        //从数据库得到符合条件的QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                QueryCondition.NOT_EQUAL, depart.getBsoID());
                    }
                    else
                    {
                        if (pdFilterQuery.getCount() != 0)
                        {
                            pdFilterQuery.addAND();
                        }
                        //从数据库得到符合条件的QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                "=",
                                depart.getBsoID());
                    }
                    //得到符合条件的departmentTypeCondition
                    departmentTypeFilterCondition = new FilterCondition(
                            "QMProcedure",
                            departmentTypeCondition);
                    pdFilterQuery.addCondition(departmentTypeFilterCondition);
                }
                else
                if (depart instanceof CodingClassificationIfc)
                {
//                    Collection col = CappTreeHelper.getOnlyCoding((
//                            CodingClassificationIfc) depart);
//                    if (col != null && col.size() > 0)
//                    {
//                        if (pdFilterQuery.getCount() != 0)
//                        {
//                            pdFilterQuery.addAND();
//                        }
//                        Object[] codings = col.toArray();
//                        int codSize = codings.length;
//                        String[] strs = new String[codSize];
//                        for (int i = 0; i < codSize; i++)
//                        {
//                            strs[i] = ((CodingIfc) codings[i]).getBsoID();
//                        }
//                        if (pdPanel.isSelectDepartment())
//                        {
//                            departmentTypeCondition = new QueryCondition(
//                                    "workShop",
//                                    "NOT IN", strs);
//                        }
//                        else
//                        {
//                            departmentTypeCondition = new QueryCondition(
//                                    "workShop", "IN",
//                                    strs);
//                        }
//                        departmentTypeFilterCondition = new FilterCondition(
//                                "QMProcedure",
//                                departmentTypeCondition);
//                        pdFilterQuery.addCondition(
//                                departmentTypeFilterCondition);
//                    }
//                    if (col != null && col.size() == 0)
//                    {
//                        if (!pdPanel.isSelectDepartment())
//                        {
//                            if (pdFilterQuery.getCount() != 0)
//                            {
//                                pdFilterQuery.addAND();
//                            }
//                            //得到符合条件的QueryCondition
//                            departmentTypeCondition = new QueryCondition(
//                                    "workShop",
//                                    QueryCondition.IS_NULL);
//                            //得到符合条件的departmentTypeCondition
//                            departmentTypeFilterCondition = new FilterCondition(
//                                    "QMProcedure",
//                                    departmentTypeCondition);
//                            pdFilterQuery.addCondition(
//                                    departmentTypeFilterCondition);
//                        }
//                    }
                    if (pdPanel.isSelectDepartment())
                    {
                        if (pdFilterQuery.getCount() != 0)
                        {
                            pdFilterQuery.addAND();
                        }
                        //从数据库得到符合条件的QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                QueryCondition.NOT_EQUAL, depart.getBsoID());
                    }
                    else
                    {
                        if (pdFilterQuery.getCount() != 0)
                        {
                            pdFilterQuery.addAND();
                        }
                        //从数据库得到符合条件的QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                "=",
                                depart.getBsoID());
                    }
                    //得到符合条件的departmentTypeCondition
                    departmentTypeFilterCondition = new FilterCondition(
                            "QMTechnics",
                            departmentTypeCondition);
                    pdFilterQuery.addCondition(departmentTypeFilterCondition);
                }
            }
            //问题(3) 2007.06.05 xucy add 通过工艺编号得到工艺及其下面工序的关联工艺的sql
//            pdFilterQuery = getAssociateSQLExpression(pdPanel.getTechNum(),
//                    pdFilterQuery);
            boolean bool = pdPanel.isSelectTechnicsBso();
            pdFilterQuery = getAssociateSQLExpression(pdPanel.getTechNum(),
                    pdFilterQuery, bool);
            //问题（4）薛凯 20080414 修改结束


        }
        catch (QMException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.pdPanel, e.getClientMessage());
        }
        return pdFilterQuery;
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
        if (pdPanel.isSelectPart())
        {
            //结构复选框是否选取
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
        totalSchema.setDisplayQuery(pdPanel.getSumCondition());
        String partNumber = pdPanel.getPartNum();
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


    /*
     ** 得到定制输出的显示名
     */
    public String[] getAttrName()
    {
        return ecDialog.getAttrDisplayname();
    }


    /*
     ** 设置定制输出时的表头
     */
    public void setMultiList()
    {
        pdPanel.setAttributes(getAttrName());
        pdPanel.updateHeadings();
        pdPanel.setMultilistRow();
        pdPanel.newPaginatePanel();
    }


    /*
     ** 设置并返回属性约束模型值对象
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
        	CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("工序汇总");
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
            JOptionPane.showMessageDialog(this.pdPanel, e.getClientMessage());
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
     ** 得到表头的长度
     */
    public int getHeadsCount()
    {
        return len;
    }


    /*
     ** 得到属性表头的个数
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
        TotalAttrSchemaIfc[] defaultAttrOutput = initSchema();
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
}
