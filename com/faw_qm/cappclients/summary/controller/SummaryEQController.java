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
import com.faw_qm.cappclients.summary.view.EquipPanel;
import com.faw_qm.cappclients.summary.view.ExportCustomizeDialog;
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
 * <p>Title:设备汇总控制类 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author 李磊
 * @version 1.0
 * 问题(1) 2006.08.16 徐春英修改  修改原因:导出后有导出成功的提示
 * 问题(2) 2006.08.30 徐春英修改  修改原因:中断汇总结果
 * 问题(3)2007.06.04 xucy add 通过工艺编号得到工艺及其下面工序的关联工艺的sql
 * 问题（4）薛凯 20080414  添加原因:工艺编号添加浏览按钮和非复选框
 * SS1 2013-2-20   刘家坤 增加记录模板类型
 * SS2 2013-2-20   刘家坤 汇总界面增加汇总条件 assetNumberField生产线  eqManuField制造厂家
 * SS3 成都设备汇总添加设备编号条件 guoxiaoliang 2016-8-4
 */
public class SummaryEQController extends SummaryController
{
    private SummaryMainJFrame mainFrame;
    private SummaryMainController mainControl;
    EquipPanel eqPanel;
    private ExportCustomizeDialog ecDialog;
    private SummaryResultSaveDialog resultSaveDialog;
    private TotalResultIfc totalResult;
    private TotalSchemaIfc schema;
    private QueryCondition techTypeCondition;
    private QueryCondition equipmentTypeCondition;
    private QueryCondition departmentTypeCondition;
    private FilterCondition techTypeFilterCondition;
    private FilterCondition equipmentTypeFilterCondition;
    private FilterCondition departmentTypeFilterCondition;
    private FilterQuery eqFilterQuery;
    private CodingIfc totalType;
    private TotalAttrSchemaIfc[] totalAttrHeads;
    private TotalAttrSchemaIfc[] totalAttributes;
    private int len;
    private WorkThread sumThread;
    private WorkThread sumThread1;
    private CappChooser qmchooser;
    //CCBegin SS2
    private QueryCondition assetNumberCondition;
    private QueryCondition eqManuCondition;
    private FilterCondition assetNumberFilterCondition;
    private FilterCondition eqManuFilterCondition;
    //CCEnd SS2
    
    //CCBegin SS3
    private QueryCondition eqNumCondition;
    private FilterCondition eqNumFilterCondition;
    //CCEnd SS3
    
    
    //问题(1) 2006.08.16 徐春英修改  修改原因:导出后有导出成功的提示
    /**用于标记资源文件路径*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.summary.util.SummaryResource";


    /**
     * 构造器
     * @param mainFrame SummaryMainJFrame
     */
    public SummaryEQController(SummaryMainJFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        eqPanel = new EquipPanel(this);
        this.mainControl = mainFrame.mainControl;
    }


    /**
     *  内部类：线程
     */
    class WorkThread extends Thread
    {
        int action;
        public WorkThread(int action)
        {
            this.action = action;
        }

        public WorkThread()
        {}

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
                    eqPanel.setSaveButtonstate(false);
                    //根据汇总条件进行汇总
                    eqPanel.setTotalResult(summary());
                    //返回汇总结果，在Multilist中显示
                    eqPanel.setToMultilist();
                    //保存按钮显示
                    eqPanel.setSaveButtonstate(true);
                    mainFrame.setSumStatus(eqPanel.getSumInfo());
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
            //薛凯 end
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
                            eqPanel.setPart((QMPartInfo) equip);
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
       System.out.println("e.getActionCommand()="+e.getActionCommand());
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
                            eqPanel.setTechniceNumber(ti.getTechnicsNumber());
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
            totalType = (CodingIfc) SummaryMainController.sorts.get("设备汇总");
            //薛凯 修改  20080222 修改原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
            String[] output = getExportCustomizeDialogDefaultOutput();
            ecDialog = new ExportCustomizeDialog(mainFrame, initial(),
                                                 totalType, output);
            //薛凯 修改结束
            //ecDialog = new ExportCustomizeDialog(mainFrame, initial(),
            //                                     totalType);
            ecDialog.setControll(this);
            ecDialog.setLocation(170, 170);
            ecDialog.setSize(650, 500);
            ecDialog.setVisible(true);
        }
        //CCBegin SS1
        if (e.getActionCommand().equals("SBQD")) {
        	System.out.println("SBQD");
            eqPanel.setAttributes(this.getSchemaheadings(2));
            eqPanel.updateHeadings();
            eqPanel.setAveRow();
            eqPanel.clearMultiList();
            eqPanel.setButton1State();
          }
        //CCEnd SS1
        //搜索按钮监听
        if (e.getActionCommand().equals("SEARCH"))
        {
            WorkThread sumThread = new WorkThread(SEARCH);
            sumThread.start();
        }
        //设备汇总监听
        if (e.getActionCommand().equals("SUM"))
        {
        	System.out.println("ddddd");
            sumThread1 = new WorkThread(SUM);
            sumThread1.start();
        }
        //中断按钮监听
        if (e.getActionCommand().equals("INTERRUPT"))
        {
            interruptThread();
            eqPanel.setSaveButtonstate(true);
            mainFrame.setSumStatus(eqPanel.getSumInfo());
            eqPanel.getMultilist().clear();
        }
        //保存按钮监听
        if (e.getActionCommand().equals("SAVE"))
        {
            if (totalType == null)
            {
                totalType = (CodingIfc) SummaryMainController.sorts.get("设备汇总");
            }
              //CCBegin SS1
            if(eqPanel.getSummaryMode() == 1)
            {
            	resultSaveDialog = new SummaryResultSaveDialog(totalType,
                    mainControl, "设备清单");
            }
            else
            {
            	resultSaveDialog = new SummaryResultSaveDialog(totalType,
                    mainControl);
            }
            //CCEnd SS1
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
        //导出按钮监听
        if (e.getActionCommand().equals("EXPORT"))
        {
        	//begin CR1
        	Vector vector1 = eqPanel.exportResult();
        	exportSummaryQMTechnics(vector1);
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
                if (totalResult != null)
                {
                    vector = eqPanel.exportResult();
                }
                Class[] lass =
                        {
                        Vector.class, String.class, Boolean.TYPE};
                Object[] obj =
                        {
                        vector,
                        new StringTokenizer(file.getAbsolutePath(), ".").
                        nextElement(),
                        //new String(file.getAbsolutePath()),
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
                    this.writeFile(str, file.getAbsolutePath(), false);
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
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("设备汇总");
            schema = getTotalSchemaIfc();
            schema.setTotalType(code);
            schema.setSubTotalType(1);
            boolean flag = getFlag();
            Class[] theClass =
                    {
                    TotalSchemaIfc.class, Boolean.TYPE};
            Object[] theObj =
                    {
                    schema, new Boolean(flag)};
            //调用TotalService服务的totalBySchema方法
            totalResult = (TotalResultIfc) getServiceObject("TotalService",
                    "totalBySchema", theClass, theObj);
            return totalResult;
        }
        catch (QMException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.eqPanel, e.getClientMessage());
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
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("设备汇总");
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
            JOptionPane.showMessageDialog(this.eqPanel, e.getClientMessage());
            return null;
        }
    }


    /**
     * 调用服务,返回TotalAttrSchemaIfc[]数组
     * @return TotalAttrSchemaIfc[]
     */
    public TotalAttrSchemaIfc[] initSchema()
    {
        try
        {
        	//20081128 薛凯 修改 修改原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
        	CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("设备汇总");
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
        //调用TotalService服务的getDefaultOutputSchema方法
        totalAttrHeads = (TotalAttrSchemaIfc[]) getServiceObject(
                "TotalService",
                "getDefaultOutputSchema", theClass, myObj);
        	}
        	//薛凯 修改 结束20081128
            return totalAttrHeads;
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.eqPanel, e.getClientMessage());
            return null;
        }
    }
    
    public TotalAttrSchemaIfc[] initSchematobsx()
    {
        try
        {
        	//20081128 薛凯 修改 修改原因：在工艺汇总界面的“输出定制”的已选属性窗口内显示默认的该类型汇总的列标签项。
        	CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("设备汇总");
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
        //调用TotalService服务的getDefaultOutputSchema方法
        totalAttrHeads = (TotalAttrSchemaIfc[]) getServiceObject(
                "TotalService",
                "getDefaultOutputSchema", theClass, myObj);
        	}
        	//薛凯 修改 结束20081128
            return totalAttrHeads;
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.eqPanel, e.getClientMessage());
            return null;
        }
    }


    /**
     * 获得属性表头
     * @return String[]
     */
    public String[] getSchemaheadings()
    {
        TotalAttrSchemaIfc[] totalAttrSchemaIfc = initSchema();
        String[] heads = null;
        if (totalAttrSchemaIfc != null)
        {
            len = totalAttrSchemaIfc.length;
            heads = new String[len];
            /*if (totalAttrSchemaIfc == null)
                     {
                totalAttrSchemaIfc = new TotalAttrSchemaIfc[len];
                     }*/
            for (int k = 0; k < len; k++)
            {
                heads[k] = totalAttrSchemaIfc[k].getAttrDisplayName();
            }
        }
        return heads;
    }


    /**
     * 得到表头的长度
     * @return int
     */
    public int getHeadsCount()
    {
        return len;
    }


    /**
     * 实现过滤条件的封装,返回FilterQuery对象
     * @return FilterQuery
     * 问题(3)2007.06.04 xucy add 通过工艺编号得到工艺及其下面工序的关联工艺的sql
     */
    public FilterQuery getFilterQuery()
    {
        eqFilterQuery = new FilterQuery(false);
        if (totalType == null)
        {
            totalType = (CodingIfc) SummaryMainController.sorts.get("设备汇总");
        }
        //设置汇总类型
        eqFilterQuery.setType(totalType);
        try
        {
            //通过part配置规范得到关于part的SQL表达式
            eqFilterQuery = getPartSQLExpression(eqPanel.getPartNum(),
                                                 eqFilterQuery);
            //得到工艺类型
            String techType = eqPanel.getTechType();
            if (techType != null)
            {
                if (eqPanel.isSelectTech())
                {
                    //非工艺种类复选框是否选取
                    if (eqFilterQuery.getCount() != 0)
                    {
                        eqFilterQuery.addAND();
                    }
                    //得到符合条件的QueryCondition
                    techTypeCondition = new QueryCondition("technicsType",
                            QueryCondition.NOT_EQUAL,
                            techType);
                }
                else
                {
                    if (eqFilterQuery.getCount() != 0)
                    {
                        eqFilterQuery.addAND();
                    }
                    //从数据库得到符合条件的QueryCondition
                    techTypeCondition = new QueryCondition("technicsType", "=",
                            techType);
                }
                //得到符合条件的techTypeCondition
                techTypeFilterCondition = new FilterCondition("QMFawTechnics",
                        techTypeCondition);
                eqFilterQuery.addCondition(techTypeFilterCondition);
            }
            //得到设备类型
            if (eqPanel.getEquipType() != null)
            {
                BaseValueIfc equipType = eqPanel.getEquipType();
                if (equipType instanceof CodingIfc)
                {
                    if (eqPanel.isSelectEquipment())
                    {
                        if (eqFilterQuery.getCount() != 0)
                        {
                            eqFilterQuery.addAND();
                        }
                        //从数据库得到符合条件的QueryCondition
                        equipmentTypeCondition = new QueryCondition("eqType",
                                QueryCondition.NOT_EQUAL, equipType.getBsoID());
                    }
                    else
                    {
                        //访问FilterQuery对象中封装的FilterCondition数目，如果数目！NULL
                        if (eqFilterQuery.getCount() != 0)
                        {
                            eqFilterQuery.addAND();
                        }
                        //从数据库得到符合条件的QueryCondition
                        equipmentTypeCondition = new QueryCondition("eqType",
                                "=",
                                equipType.getBsoID());
                    }
                    //得到符合条件的equipmentTypeCondition
                    equipmentTypeFilterCondition = new FilterCondition(
                            "QMEquipment",
                            equipmentTypeCondition);
                    eqFilterQuery.addCondition(equipmentTypeFilterCondition);
                }
                else
                if (equipType instanceof CodingClassificationIfc)
                {
                    Collection col = CappTreeHelper.getOnlyCoding((
                            CodingClassificationIfc) equipType);
                    if (col != null && col.size() > 0)
                    {
                        if (eqFilterQuery.getCount() != 0)
                        {
                            eqFilterQuery.addAND();
                        }
                        Object[] codings = col.toArray();
                        int codSize = codings.length;
                        String[] strs = new String[codSize];
                        for (int i = 0; i < codSize; i++)
                        {
                            strs[i] = ((CodingIfc) codings[i]).getBsoID();
                        }
                        if (eqPanel.isSelectEquipment())
                        {
                            equipmentTypeCondition = new QueryCondition(
                                    "eqType",
                                    "NOT IN", strs);
                        }
                        else
                        {
                            equipmentTypeCondition = new QueryCondition(
                                    "eqType", "IN",
                                    strs);
                        }
                        equipmentTypeFilterCondition = new FilterCondition(
                                "QMEquipment",
                                equipmentTypeCondition);
                        eqFilterQuery.addCondition(equipmentTypeFilterCondition);
                    }
                }
            }
            // 得到工序部门
            if (eqPanel.getProcedureDepart() != null)
            {
                BaseValueIfc depart = eqPanel.getProcedureDepart();
                if (depart instanceof CodingIfc)
                {
                    if (eqPanel.isSelectDepartment())
                    {
                        if (eqFilterQuery.getCount() != 0)
                        {
                            eqFilterQuery.addAND();
                        }
                        //从数据库得到符合条件的QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                QueryCondition.NOT_EQUAL, depart.getBsoID());
                    }
                    else
                    {
                        if (eqFilterQuery.getCount() != 0)
                        {
                            eqFilterQuery.addAND();
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
                    eqFilterQuery.addCondition(departmentTypeFilterCondition);
                }
                else
                if (depart instanceof CodingClassificationIfc)
                {
                    Collection col = CappTreeHelper.getOnlyCoding((
                            CodingClassificationIfc) depart);
                    if (col != null && col.size() > 0)
                    {
                        if (eqFilterQuery.getCount() != 0)
                        {
                            eqFilterQuery.addAND();
                        }
                        Object[] codings = col.toArray();
                        int codSize = codings.length;
                        String[] strs = new String[codSize];
                        for (int i = 0; i < codSize; i++)
                        {
                            strs[i] = ((CodingIfc) codings[i]).getBsoID();
                        }
                        if (eqPanel.isSelectDepartment())
                        {
                            equipmentTypeCondition = new QueryCondition(
                                    "workShop",
                                    "NOT IN", strs);
                        }
                        else
                        {
                            equipmentTypeCondition = new QueryCondition(
                                    "workShop", "IN",
                                    strs);
                        }
                        departmentTypeFilterCondition = new FilterCondition(
                                "QMProcedure",
                                equipmentTypeCondition);
                        eqFilterQuery.addCondition(
                                departmentTypeFilterCondition);
                    }
                    if (col != null && col.size() == 0)
                    {
                        if (!eqPanel.isSelectDepartment())
                        {
                            if (eqFilterQuery.getCount() != 0)
                            {
                                eqFilterQuery.addAND();
                            }
                            //得到符合条件的FilterCondition
                            departmentTypeCondition = new QueryCondition(
                                    "workShop",
                                    QueryCondition.IS_NULL);
                            //得到符合条件的departmentTypeCondition
                            departmentTypeFilterCondition = new FilterCondition(
                                    "QMProcedure",
                                    departmentTypeCondition);
                            eqFilterQuery.addCondition(
                                    departmentTypeFilterCondition);
                        }
                    }
                }
            }
            //CCBegin SS2
            if (eqPanel.getAssetNumber() != null && !eqPanel.getAssetNumber().trim().equals(""))
            {
             String assetNumber = eqPanel.getAssetNumber();
             if (eqFilterQuery.getCount() != 0)
             {
             eqFilterQuery.addAND();
             }
             assetNumberCondition = new QueryCondition("taskInstructNum", "=",
             assetNumber);
             assetNumberFilterCondition = new FilterCondition(
             "QMTechnics",
             assetNumberCondition);
             eqFilterQuery.addCondition(assetNumberFilterCondition);
                }
                if (eqPanel.getEqManu() != null && !eqPanel.getEqManu().trim().equals(""))
            {
             String eqManu = eqPanel.getEqManu();
             if (eqFilterQuery.getCount() != 0)
             {
             eqFilterQuery.addAND();
             }
             //CCBegin by liunan 2011-4-26 制造厂家 代码有误。
             //eqManuCondition = new QueryCondition("taskInstructNum", "=",
             eqManuCondition = new QueryCondition("eqManu", "=",
             //CCEnd by liunan 
             eqManu);
             eqManuFilterCondition = new FilterCondition(
             "QMEquipment",
             eqManuCondition);
             eqFilterQuery.addCondition(eqManuFilterCondition);
             }
    
            //CCEnd SS2
                
                
                //CCBegin SS3
                
                if (eqPanel.getEqNum() != null && !eqPanel.getEqNum().trim().equals(""))
                {
                 String eqNum = eqPanel.getEqNum();
                 if (eqFilterQuery.getCount() != 0)
                 {
                 eqFilterQuery.addAND();
                 }
                 //CCBegin by liunan 2011-4-26 制造厂家 代码有误。
                 //eqManuCondition = new QueryCondition("taskInstructNum", "=",
                 eqNumCondition = new QueryCondition("eqNum", "=",
                 //CCEnd by liunan 
                		 eqNum);
                 eqNumFilterCondition = new FilterCondition(
                 "QMEquipment",
                 eqNumCondition);
                 eqFilterQuery.addCondition(eqNumFilterCondition);
                 }
                
                //CCEnd SS3
                
            //问题(3)2007.06.04 xucy add 通过工艺编号得到工艺及其下面工序的关联工艺的sql
            //问题（4）薛凯 20080414  添加原因:工艺编号添加浏览按钮和非复选框
//            eqFilterQuery = getAssociateSQLExpression(eqPanel.getTechNum(),
//                    eqFilterQuery);
            boolean bool = eqPanel.isSelectTechnicsBso();
            eqFilterQuery = getAssociateSQLExpression(eqPanel.getTechNum(),
                    eqFilterQuery, bool);
            //问题（4）薛凯 20080414 修改结束
        }
        catch (QMException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.eqPanel, e.getClientMessage());
        }
        return eqFilterQuery;
    }


    /**
     * 得到汇总的约束条件
     * @return TotalSchemaIfc
     */
    public TotalSchemaIfc getTotalSchemaIfc()
    {
        TotalSchemaIfc totalSchema = new TotalSchemaInfo();
        PartConfigSpecIfc configSpec = mainControl.getConfigSpec().
                                       getConfigSpecInfo();
        this.setPartConfig(null);
        //结构复选框是否选取
        if (eqPanel.isSelectPart())
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
        totalSchema.setDisplayQuery(eqPanel.getSumCondition());
        String partNumber = eqPanel.getPartNum();
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
     * 中止线程
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
        eqPanel.setAttributes(getAttrName());
        eqPanel.updateHeadings();
        eqPanel.setMultilistRow();
        eqPanel.newPaginatePanel();
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
        System.out.println();
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
            	s = s + "." + defaultAttrOutput[i].getAttrDisplayName().trim();
                output[i] = s.trim();
            }
        }
        return output;
    }
    //薛凯 添加结束
    //CCBegin SS1
    /**
     * 获得属性表头
     * @return String[]
     * tangshutao modify 2007.11.06 增加int型参数
     */
    public String[] getSchemaheadings(int i) {
      TotalAttrSchemaIfc[] totalAttrSchemaIfc = initSchema(i);
  	System.out.println("totalAttrSchemaIfc="+totalAttrSchemaIfc);
      len = totalAttrSchemaIfc.length;
      String[] heads = new String[len];
      if (totalAttrSchemaIfc == null) {
        totalAttrSchemaIfc = new TotalAttrSchemaIfc[len];
      }
      for (int k = 0; k < len; k++) {
        heads[k] = totalAttrSchemaIfc[k].getAttrDisplayName();
      }
      return heads;
    }
    /**
     * 调用服务,返回TotalAttrSchemaIfc[]数组
     * @return TotalAttrSchemaIfc[]
     * chudaming modify 2009.4.16 增加int型参数
     */
    public TotalAttrSchemaIfc[] initSchema(int i) {
      try {
        Class[] theClass = {
            CodingIfc.class, Integer.TYPE};
        CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("设备汇总");
        
        Object[] myObj = {
            code, new Integer(i)};
        //调用TotalService服务的getDefaultOutputSchema方法
        totalAttrHeads = (TotalAttrSchemaIfc[]) getServiceObject(
            "TotalService",
            "getDefaultOutputSchema", theClass, myObj);
        return totalAttrHeads;
      }
      catch (QMRemoteException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this.eqPanel, e.getClientMessage());
        return null;
      }
    }
    
    //CCEnd SS1
}
