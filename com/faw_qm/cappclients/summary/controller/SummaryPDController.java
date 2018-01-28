/**
 * CR1 2012/04/12 ������ �μ�TD2512
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
 * <p>Title:������ܿ����� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author ����
 * @version 1.0
 * ����(2) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
 * ����(3) 2006.08.30 �촺Ӣ�޸�  �޸�ԭ��:�жϻ��ܽ��
 * ����(3) 2007.06.05 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
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


    /**���ڱ����Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.summary.util.SummaryResource";


    /*
     ** ���캯��
     */
    public SummaryPDController(SummaryMainJFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        pdPanel = new PDPanel(this);
        this.mainControl = mainFrame.mainControl;
    }


    /**
     *�ڲ��ࣺ�߳�
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

                    //�����㲿��
                    searchQM();
                    break;
                case SUM:
                    mainFrame.setCursor(Cursor.WAIT_CURSOR);
                    mainFrame.setSumStatus(res.getString("wait"));
                    //���水ť����ʾ
                    pdPanel.setSaveButtonstate(false);
                    //���ݻ����������л���
                    pdPanel.setTotalResult(summary());
                    //���ػ��ܽ������Multilist����ʾ
                    pdPanel.setToMultilist();
                    //���水ť��ʾ
                    pdPanel.setSaveButtonstate(true);
                    //����״̬����ֵ
                    mainFrame.setSumStatus(pdPanel.getSumInfo());
                    mainFrame.setCursor(Cursor.getDefaultCursor());
                    break;
            }
        }
    }


    /*
     ** �����㲿��
     */
    private void searchQM()
    {
        qmchooser = new CappChooser("QMPart", "�㲿������", mainFrame);

        try
        {
        	//Ѧ�� 20081225 start
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
     ** ����ʵ��
     * ����(2) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
     */
    public void actionPerformed(ActionEvent e)
    {
        //���⣨4��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
        if (e.getActionCommand().equals("SearchTechnics"))
        {
            try
            {
                TechnicsSearchJDialog tsd = new TechnicsSearchJDialog(mainFrame);
                //��ֹѡ����
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
        //���⣨4��  ��ӽ���

        if (e.getActionCommand().equals("CUSTO"))
        {
            totalType = (CodingIfc) SummaryMainController.sorts.get("�������");
            //ecDialog = new ExportCustomizeDialog(mainFrame, initial(),
             //                                    totalType);
             //Ѧ�� �޸�  20080222 �޸�ԭ���ڹ��ջ��ܽ���ġ�������ơ�����ѡ���Դ�������ʾĬ�ϵĸ����ͻ��ܵ��б�ǩ�
            String[] output = getExportCustomizeDialogDefaultOutput();
            ecDialog = new ExportCustomizeDialog(mainFrame, initial(),
                                                 totalType ,output);
            //Ѧ�� �޸Ľ���
            ecDialog.setControll(this);
            ecDialog.setLocation(170, 170);
            ecDialog.setSize(650, 500);
            ecDialog.setVisible(true);
        }
        //���ܰ�ť����
        if (e.getActionCommand().equals("SUM"))
        {
            sumThread1 = new WorkThread(SUM);
            sumThread1.start();
        }
        //�жϰ�ť����
        if (e.getActionCommand().equals("INTERRUPT"))
        {
            interruptThread();
            pdPanel.setSaveButtonstate(true);
            mainFrame.setSumStatus(pdPanel.getSumInfo());
            pdPanel.getMultilist().clear();
        }
        //���水ť����
        if (e.getActionCommand().equals("SAVE"))
        {
            if (totalType == null)
            {
                totalType = (CodingIfc) SummaryMainController.sorts.get("�������");
            }
            resultSaveDialog = new SummaryResultSaveDialog(totalType,
                    mainControl);
            mainControl.setDialog(resultSaveDialog);
            resultSaveDialog.setResult(totalResult);
            resultSaveDialog.setAttrSchema(schema);
            mainFrame.removePanel();
            resultSaveDialog.setVisible(true);
        }
        //�˳���ť����
        if (e.getActionCommand().equals("EXIT"))
        {
            mainFrame.removePanel();
        }
        //������ť����
        if (e.getActionCommand().equals("SEARCH"))
        {
            sumThread = new WorkThread(SEARCH);
            sumThread.start();
        }
        //������ť����
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
                //����(2) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
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
     ** ���û��ܷ���
     */
    public TotalResultIfc summary()
    {
        try
        {
            Class[] theClass = {TotalSchemaIfc.class, Boolean.TYPE};
            schema = getTotalSchemaIfc();
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("�������");
            schema.setTotalType(code);
            schema.setSubTotalType(1);
            boolean flag = getFlag();
            Object[] theObj ={schema, new Boolean(flag)};
            //����TotalService�����totalBySchema����
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
     ** ���÷��񣬷���BsoMetaInfo[]����
     */
    public BsoMetaInfo[] initial()
    {
        try
        {
            Class[] theClass =
                    {
                    CodingIfc.class, Integer.TYPE};
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("�������");
            Object[] myObj =
                    {
                    code, new Integer(TotalConstant.ISGENERAL)};
            //����TotalService�����getBsoMetaInfos����
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
     * ��ֹ�߳�
     * ����(3) 2006.08.30 �촺Ӣ�޸�  �޸�ԭ��:�жϻ��ܽ��
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
     * ʵ�ֹ��������ķ�װ,����FilterQuery����
     * @return FilterQuery
     * ����(3) 2007.06.05 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
     */
    public FilterQuery getFilterQuery()
    {
        pdFilterQuery = new FilterQuery(false);
        if (totalType == null)
        {
            totalType = (CodingIfc) SummaryMainController.sorts.get("�������");
        }
        //���û�������
        pdFilterQuery.setType(totalType);
        try
        {
            //ͨ��part���ù淶�õ�����part��SQL���ʽ
            pdFilterQuery = getPartSQLExpression(pdPanel.getPartNum(),
                                                 pdFilterQuery);
            //�õ���������
            if (pdPanel.getTechType() != null)
            {
                //�ǹ������ิѡ���Ƿ�ѡȡ
                if (pdPanel.isSelectTech())
                {
                    if (pdFilterQuery.getCount() != 0)
                    {
                        pdFilterQuery.addAND();
                    }
                    //�õ�����������QueryCondition]
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
                    //�õ�����������QueryCondition
                    techTypeCondition = new QueryCondition("technicsType", "=",
                            pdPanel.getTechType());
                }
                //�õ�����������techTypeCondition
                techTypeFilterCondition = new FilterCondition("QMFawTechnics",
                        techTypeCondition);
                pdFilterQuery.addCondition(techTypeFilterCondition);
            }
            //�õ���������
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
                        //�����ݿ�õ�����������QueryCondition
                        procedureTypeCondition = new QueryCondition(
                                "stepClassification",
                                QueryCondition.NOT_EQUAL, pdType.getBsoID());
                    }
                    else
                    {
                        //����FilterQuery�����з�װ��FilterCondition��Ŀ�������Ŀ��NULL
                        if (pdFilterQuery.getCount() != 0)
                        {
                            pdFilterQuery.addAND();
                        }
                        //�����ݿ�õ�����������QueryCondition
                        procedureTypeCondition = new QueryCondition(
                                "stepClassification",
                                "=", pdType.getBsoID());
                    }
                    //�õ�����������procedureTypeCondition
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
                        //�����ݿ�õ�����������QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                QueryCondition.NOT_EQUAL, depart.getBsoID());
                    }
                    else
                    {
                        if (pdFilterQuery.getCount() != 0)
                        {
                            pdFilterQuery.addAND();
                        }
                        //�����ݿ�õ�����������QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                "=",
                                depart.getBsoID());
                    }
                    //�õ�����������departmentTypeCondition
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
//                            //�õ�����������QueryCondition
//                            departmentTypeCondition = new QueryCondition(
//                                    "workShop",
//                                    QueryCondition.IS_NULL);
//                            //�õ�����������departmentTypeCondition
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
                        //�����ݿ�õ�����������QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                QueryCondition.NOT_EQUAL, depart.getBsoID());
                    }
                    else
                    {
                        if (pdFilterQuery.getCount() != 0)
                        {
                            pdFilterQuery.addAND();
                        }
                        //�����ݿ�õ�����������QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                "=",
                                depart.getBsoID());
                    }
                    //�õ�����������departmentTypeCondition
                    departmentTypeFilterCondition = new FilterCondition(
                            "QMTechnics",
                            departmentTypeCondition);
                    pdFilterQuery.addCondition(departmentTypeFilterCondition);
                }
            }
            //����(3) 2007.06.05 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
//            pdFilterQuery = getAssociateSQLExpression(pdPanel.getTechNum(),
//                    pdFilterQuery);
            boolean bool = pdPanel.isSelectTechnicsBso();
            pdFilterQuery = getAssociateSQLExpression(pdPanel.getTechNum(),
                    pdFilterQuery, bool);
            //���⣨4��Ѧ�� 20080414 �޸Ľ���


        }
        catch (QMException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.pdPanel, e.getClientMessage());
        }
        return pdFilterQuery;
    }


    /**
     * �õ����ܵ�Լ��
     * @return TotalSchemaIfc
     */
    public TotalSchemaIfc getTotalSchemaIfc()
    {
        TotalSchemaIfc totalSchema = new TotalSchemaInfo();
        PartConfigSpecIfc configSpec = mainControl.getConfigSpec().
                                       getConfigSpecInfo();
        this.setPartConfig(null);
        //�ṹ��ѡ���Ƿ�ѡȡ
        if (pdPanel.isSelectPart())
        {
            //�ṹ��ѡ���Ƿ�ѡȡ
            totalSchema.setTotalStructure(true);
            //����������ù淶
            totalSchema.setConfigSpec(configSpec);
            //�������ù淶
            this.setPartConfig(configSpec);
        }
        //��������ģ����Ϣ������������Լ��ģ��ֵ����
        totalSchema.setAttrSchemas(getBsoNameAtt());
        //ʵ�ֹ��������ķ�װ,����FilterQuery����
        totalSchema.setFilterQuery(getFilterQuery());
        //������ʾ�������ʽ
        totalSchema.setDisplayQuery(pdPanel.getSumCondition());
        String partNumber = pdPanel.getPartNum();
        if (partNumber != null)
        {
        	//Ѧ�� �޸� 20081016 ׼���û�ѡ�����㲿�����л���
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
        	//Ѧ���޸Ľ��� 20081016
            if (bsoID != null)
            {
                totalSchema.setPartID(bsoID);
            }
        }
        return totalSchema;
    }


    /*
     ** �õ������������ʾ��
     */
    public String[] getAttrName()
    {
        return ecDialog.getAttrDisplayname();
    }


    /*
     ** ���ö������ʱ�ı�ͷ
     */
    public void setMultiList()
    {
        pdPanel.setAttributes(getAttrName());
        pdPanel.updateHeadings();
        pdPanel.setMultilistRow();
        pdPanel.newPaginatePanel();
    }


    /*
     ** ���ò���������Լ��ģ��ֵ����
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
     * ���÷���,����TotalATtrSchemaIfc[]����
     * @return TotalAttrSchemaIfc[]
     */
    public TotalAttrSchemaIfc[] initSchema()
    {
        try
        {
        	//20081128 Ѧ�� �޸� �޸�ԭ���ڹ��ջ��ܽ���ġ�������ơ�����ѡ���Դ�������ʾĬ�ϵĸ����ͻ��ܵ��б�ǩ�
        	CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("�������");
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
        //����TotalService�����getDefaultOutputSchema������
        totalAttrHeads = (TotalAttrSchemaIfc[]) getServiceObject(
                "TotalService",
                "getDefaultOutputSchema", theClass, myObj);
        	}
            //Ѧ�� 20081128 �޸Ľ��� 
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
     **������Ա�ͷ
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
     ** �õ���ͷ�ĳ���
     */
    public int getHeadsCount()
    {
        return len;
    }


    /*
     ** �õ����Ա�ͷ�ĸ���
     */
    public int getAttrCount()
    {
        return ecDialog.getAttrCount();
    }


/**
    * Ѧ�� ��� 20080222
    * ���ԭ���ڹ��ջ��ܽ���ġ�������ơ�����ѡ���Դ�������ʾĬ�ϵĸ����ͻ��ܵ��б�ǩ�
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
    //Ѧ�� ��ӽ���
}
