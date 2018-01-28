/**
 * CR1   2009/06/30  Ѧ��  ����û�й��������Ļ����������
 * CR2 2012/04/12 ������ �μ�TD2512
 * SS1 2013-2-20   ������ ���ܽ������ӻ������� assetNumberField������  eqManuField���쳧��
 * */
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
import com.faw_qm.cappclients.summary.view.SummaryMainJFrame;
import com.faw_qm.cappclients.summary.view.SummaryResultSaveDialog;
import com.faw_qm.cappclients.summary.view.mainMTPanel;
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
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.cappclients.capp.view.TechnicsSearchJDialog;


/**
 * <p>Title: ��Ҫ���ϻ��ܿ�����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author ����
 * @version 1.0
 * ����(1) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
 * ����(2) 2006.08.30 �촺Ӣ�޸�  �޸�ԭ��:�жϻ��ܽ��
 * ����(3) 2007.06.05 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
 */

public class SummaryMainMTController extends SummaryController
{
    public SummaryMainJFrame mainFrame;
    mainMTPanel mainMtPanel;
    private ExportCustomizeDialog ecDialog;
    private SummaryResultSaveDialog resultSaveDialog;
    private TotalAttrSchemaIfc[] totalAttrHeads;
    private TotalAttrSchemaIfc[] totalAttributes;
    private QueryCondition techTypeCondition;
    private QueryCondition materialTypeCondition;
    private QueryCondition departmentTypeCondition;
    private FilterCondition techTypeFilterCondition;
    private FilterCondition materialTypeFilterCondition;
    private FilterCondition departmentTypeFilterCondition;
    private FilterQuery mtFilterQuery;
    private TotalSchemaIfc schema;
    private CodingIfc totalType;
    private int len;
    private TotalResultIfc totalResult;
    private SummaryMainController mainControl;
    private WorkThread sumThread;
    private WorkThread sumThread1;
    private CappChooser qmchooser;
    //����(1) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
    /**���ڱ����Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.summary.util.SummaryResource";

 //CCBegin SS1
    private QueryCondition assetNumberCondition;
    private FilterCondition assetNumberFilterCondition;
    private FilterCondition materialNameFilterCondition;
    private QueryCondition materialNameCondition;
    //CCEnd SS1
    /*
     ** ���캯��
     */
    public SummaryMainMTController(SummaryMainJFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        mainMtPanel = new mainMTPanel(this);
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
                    mainMtPanel.setSaveButtonstate(false);
                    //���ݻ����������л���
                    mainMtPanel.setTotalResult(summary());
                    //���ػ��ܽ������Multilist����ʾ
                    mainMtPanel.setToMultilist();
                    //���水ť��ʾ
                    mainMtPanel.setSaveButtonstate(true);
                    //����״̬����ֵ
                    mainFrame.setSumStatus(mainMtPanel.getSumInfo());
                    mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                    break;
            }
        }
    }


    /**
     * �����㲿��
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
                            mainMtPanel.setPart((QMPartInfo) equip);
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
     * ����ʵ��
     * @param e ActionEvent
     * ����(1) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
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
                            mainMtPanel.setTechniceNumber(ti.getTechnicsNumber());
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
            totalType = (CodingIfc) SummaryMainController.sorts.get("��Ҫ���ϻ���");
            //ecDialog = new ExportCustomizeDialog(mainFrame, initial(),
            //                                     totalType);
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
            mainMtPanel.setSaveButtonstate(true);
            mainFrame.setSumStatus(mainMtPanel.getSumInfo());
            mainMtPanel.getMultilist().clear();
        }
        //���水ť����
        if (e.getActionCommand().equals("SAVE"))
        {
            if (totalType == null)
            {
                totalType = (CodingIfc) SummaryMainController.sorts.get(
                        "��Ҫ���ϻ���");
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
        	//begin CR2
        	Vector vector1 = mainMtPanel.exportResult();
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
                    vector = mainMtPanel.exportResult();
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
                //����(1) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
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
     * ��ֹ�߳�
     * ����(2) 2006.08.30 �촺Ӣ�޸�  �޸�ԭ��:�жϻ��ܽ��
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
     * ���û��ܷ���
     * @return TotalResultIfc
     */
    public TotalResultIfc summary()
    {
        try
        {
            Class[] theClass ={TotalSchemaIfc.class, Boolean.TYPE};
            schema = getTotalSchemaIfc();
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("��Ҫ���ϻ���");
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
            JOptionPane.showMessageDialog(this.mainMtPanel, e.getClientMessage());
            return null;
        }
    }


    /**
     * ���÷��񣬷���BsoMetaInfo[]����
     * @return BsoMetaInfo[]
     */
    public BsoMetaInfo[] initial()
    {
        try
        {
            Class[] theClass =
                               {
                               CodingIfc.class, Integer.TYPE};
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get(
                    "��Ҫ���ϻ���");
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
            JOptionPane.showMessageDialog(this.mainMtPanel, e.getClientMessage());
            return null;
        }
    }


    /**
     * ʵ�ֹ��������ķ�װ,����FilterQuery����
     * @return FilterQuery
     * ����(3) 2007.06.05 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
     */
    public FilterQuery getFilterQuery()
    {
        mtFilterQuery = new FilterQuery(false);
        if (totalType == null)
        {
            totalType = (CodingIfc) SummaryMainController.sorts.get("��Ҫ���ϻ���");
        }
        //���û�������
        mtFilterQuery.setType(totalType);
        try
        {
            //ͨ��part���ù淶�õ�����part��SQL���ʽ
        	//begin CR1
//            mtFilterQuery = getPartSQLExpression(mainMtPanel.getPartNum(),
//                                                 mtFilterQuery);
            mtFilterQuery = getPartSQLExpressionForMainMT(mainMtPanel.getPartNum(),
                    mtFilterQuery);
            //end CR1
            //�õ���������
            if (mainMtPanel.getTechType() != null)
            {
                //�ǹ������ิѡ���Ƿ�ѡȡ
                if (mainMtPanel.isSelectTech())
                {
                    if (mtFilterQuery.getCount() != 0)
                    {
                        mtFilterQuery.addAND();
                    }
                    //�õ�����������QueryCondition
                    techTypeCondition = new QueryCondition("technicsType",
                            QueryCondition.NOT_EQUAL,
                            mainMtPanel.getTechType());
                }
                else
                {
                    if (mtFilterQuery.getCount() != 0)
                    {
                        mtFilterQuery.addAND();
                    }
                    //�����ݿ�õ�����������QueryCondition
                    techTypeCondition = new QueryCondition("technicsType", "=",
                            mainMtPanel.getTechType());
                }
                //�õ�����������techTypeCondition
                techTypeFilterCondition = new FilterCondition("QMFawTechnics",
                        techTypeCondition);
                mtFilterQuery.addCondition(techTypeFilterCondition);
            }
            //�õ���Ҫ��������
            if (mainMtPanel.getMtpType() != null)
            {
                BaseValueIfc mtType = mainMtPanel.getMtpType();
                if (mtType instanceof CodingIfc)
                {
                    if (mainMtPanel.isSelectEquipment())
                    {
                        if (mtFilterQuery.getCount() != 0)
                        {
                            mtFilterQuery.addAND();
                        }
                        //�����ݿ�õ�����������QueryCondition
                        materialTypeCondition = new QueryCondition("materialCf",
                                QueryCondition.NOT_EQUAL, mtType.getBsoID());
                    }
                    else
                    {
                        //����FilterQuery�����з�װ��FilterCondition��Ŀ�������Ŀ��NULL
                        if (mtFilterQuery.getCount() != 0)
                        {
                            mtFilterQuery.addAND();
                        }
                        //�����ݿ�õ�����������QueryCondition
                        materialTypeCondition = new QueryCondition("materialCf",
                                "=",
                                mtType.getBsoID());
                    }
                    //�õ�����������toolTypeCondition
                    materialTypeFilterCondition = new FilterCondition(
                            "QMMaterial",
                            materialTypeCondition);
                    mtFilterQuery.addCondition(materialTypeFilterCondition);
                }
                else
                if (mtType instanceof CodingClassificationIfc)
                {
                    Collection col = CappTreeHelper.getOnlyCoding((
                            CodingClassificationIfc) mtType);
                    if (col != null && col.size() > 0)
                    {
                        if (mtFilterQuery.getCount() != 0)
                        {
                            mtFilterQuery.addAND();
                        }
                        Object[] codings = col.toArray();
                        int codSize = codings.length;
                        String[] strs = new String[codSize];
                        for (int i = 0; i < codSize; i++)
                        {
                            strs[i] = ((CodingIfc) codings[i]).getBsoID();
                        }
                        if (mainMtPanel.isSelectEquipment())
                        {
                            materialTypeCondition = new QueryCondition(
                                    "materialCf",
                                    "NOT IN", strs);
                        }
                        else
                        {
                            materialTypeCondition = new QueryCondition(
                                    "materialCf", "IN",
                                    strs);
                        }
                        materialTypeFilterCondition = new FilterCondition(
                                "QMMaterial",
                                materialTypeCondition);
                        mtFilterQuery.addCondition(materialTypeFilterCondition);
                    }
                }
            }
            // �õ����ղ���
            if (mainMtPanel.getProcedureDepart() != null)
            {
                BaseValueIfc depart = mainMtPanel.getProcedureDepart();
                if (mtFilterQuery.getCount() != 0)
                {
                    mtFilterQuery.addAND();
                }
                if (mainMtPanel.isSelectDepartment())
                {
                    //�����ݿ�õ�����������QueryCondition
                    departmentTypeCondition = new QueryCondition("workShop",
                            QueryCondition.NOT_EQUAL, depart.getBsoID());
                }
                else
                {
                    //�����ݿ�õ�����������QueryCondition
                    departmentTypeCondition = new QueryCondition("workShop",
                            "=",
                            depart.getBsoID());
                }
                //�õ�����������departmentTypeCondition
                departmentTypeFilterCondition = new FilterCondition(
                        "QMTechnics",
                        departmentTypeCondition);
                mtFilterQuery.addCondition(departmentTypeFilterCondition);

                if (depart instanceof CodingIfc)
                {
                    if (mtFilterQuery.getCount() != 0)
                    {
                        mtFilterQuery.addAND();
                    }
                    if (mainMtPanel.isSelectDepartment())
                    {
                        //�����ݿ�õ�����������QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                QueryCondition.NOT_EQUAL, depart.getBsoID());
                    }
                    else
                    {
                        //�����ݿ�õ�����������QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                "=",
                                depart.getBsoID());
                    }
                    //�õ�����������departmentTypeCondition
                    departmentTypeFilterCondition = new FilterCondition(
                            "QMTechnics",
                            departmentTypeCondition);
                    mtFilterQuery.addCondition(departmentTypeFilterCondition);
                }

                else
                if (depart instanceof CodingClassificationIfc)
                {
//                    Collection col = CappTreeHelper.getOnlyCoding((
//                            CodingClassificationIfc) depart);
//                    if (col != null && col.size() > 0)
//                    {
//                        if (mtFilterQuery.getCount() != 0)
//                        {
//                            mtFilterQuery.addAND();
//                        }
//                        Object[] codings = col.toArray();
//                        int codSize = codings.length;
//                        String[] strs = new String[codSize];
//                        for (int i = 0; i < codSize; i++)
//                        {
//                            strs[i] = ((CodingIfc) codings[i]).getBsoID();
//                        }
//                        if (mainMtPanel.isSelectDepartment())
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
//                                "QMTechnics",
//                                departmentTypeCondition);
//                        mtFilterQuery.addCondition(
//                                departmentTypeFilterCondition);
//                    }
//                    if (col != null && col.size() == 0)
//                    {
//                        if (!mainMtPanel.isSelectDepartment())
//                        {
//                            if (mtFilterQuery.getCount() != 0)
//                            {
//                                mtFilterQuery.addAND();
//                            }
//                            //�õ�����������QueryCondition
//                            departmentTypeCondition = new QueryCondition(
//                                    "workShop",
//                                    QueryCondition.IS_NULL);
//                            //�õ�����������departmentTypeCondition
//                            departmentTypeFilterCondition = new FilterCondition(
//                                    "QMTechnics",
//                                    departmentTypeCondition);
//                            mtFilterQuery.addCondition(
//                                    departmentTypeFilterCondition);
//                        }
//                    }
                    if (mtFilterQuery.getCount() != 0)
                    {
                        mtFilterQuery.addAND();
                    }
                    if (mainMtPanel.isSelectDepartment())
                    {
                        //�����ݿ�õ�����������QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                QueryCondition.NOT_EQUAL, depart.getBsoID());
                    }
                    else
                    {
                        //�����ݿ�õ�����������QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                "=",
                                depart.getBsoID());
                    }
                    //�õ�����������departmentTypeCondition
                    departmentTypeFilterCondition = new FilterCondition(
                            "QMTechnics",
                            departmentTypeCondition);
                    mtFilterQuery.addCondition(departmentTypeFilterCondition);
                }
            }
            //CCBegin SS1
           if (mainMtPanel.getAssetNumber() != null && !mainMtPanel.getAssetNumber().trim().equals(""))
       {
        String assetNumber = mainMtPanel.getAssetNumber();
        if (mtFilterQuery.getCount() != 0)
        {
        mtFilterQuery.addAND();
        }
        assetNumberCondition = new QueryCondition("taskInstructNum", "=",
        assetNumber);
        assetNumberFilterCondition = new FilterCondition(
        "QMTechnics",
        assetNumberCondition);
        mtFilterQuery.addCondition(assetNumberFilterCondition);
           }
           //�õ���������
            if (mainMtPanel.getMaterialName() != null && !mainMtPanel.getMaterialName().trim().equals(""))
        {
         String materialName = mainMtPanel.getMaterialName();
         if (mtFilterQuery.getCount() != 0)
         {
         mtFilterQuery.addAND();
         }
         //CCBegin by liunan 2011-5-4 ���ܹ����޸ģ��������ƴ��벻�ԡ�
         //materialNameCondition = new QueryCondition("taskInstructNum", "=",
         materialNameCondition = new QueryCondition("materialName", "=",
         //CCEnd by liunan 2011-5-4
         materialName);
         materialNameFilterCondition = new FilterCondition(
         "QMMaterial",
         materialNameCondition);
         mtFilterQuery.addCondition(materialNameFilterCondition);
            }

        //CCEnd SS1
            //Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
           //����(3) 2007.06.05 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
//           mtFilterQuery = getAssociateSQLExpression(mainMtPanel.getTechNum(),
//                   mtFilterQuery);
           boolean bool = mainMtPanel.isSelectTechnicsBso();
            mtFilterQuery = getAssociateSQLExpression(mainMtPanel.getTechNum(),
                    mtFilterQuery, bool);
            //���⣨4��Ѧ�� 20080414 �޸Ľ���


        }
        catch (QMException e)
        {
            JOptionPane.showMessageDialog(this.mainMtPanel, e.getClientMessage());
        }
        return mtFilterQuery;
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
        if (mainMtPanel.isSelectPart())
        {
            //���û��ܵ�ǰ�㲿���е��Ӽ�����ṹ���ݣ��桢��  Ĭ��Ϊ��
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
        totalSchema.setDisplayQuery(mainMtPanel.getSumCondition());
        String partNumber = mainMtPanel.getPartNum();
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


    /**
     * �õ������������ʾ��
     * @return String[]
     */
    public String[] getAttrName()
    {
        return ecDialog.getAttrDisplayname();
    }


    /**
     * ���ö������ʱ�ı�ͷ
     */

    public void setMultiList()
    {
        mainMtPanel.setAttributes(getAttrName());
        mainMtPanel.updateHeadings();
        mainMtPanel.setMultilistRow();
        mainMtPanel.newPaginatePanel();
    }


    /**
     * ���ò���������Լ��ģ��ֵ����
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
     * ���÷���,����TotalATtrSchemaIfc[]����
     * @return TotalAttrSchemaIfc[]
     */

    public TotalAttrSchemaIfc[] initSchema()
    {
        try
        {
        	//20081128 Ѧ�� �޸� �޸�ԭ���ڹ��ջ��ܽ���ġ�������ơ�����ѡ���Դ�������ʾĬ�ϵĸ����ͻ��ܵ��б�ǩ�
        	CodingIfc code = (CodingIfc) SummaryMainController.sorts.get(
            "��Ҫ���ϻ���");
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
            //Ѧ�� �޸Ľ��� 20081128
            return totalAttrHeads;
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mainMtPanel, e.getClientMessage());
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
     **
     */
    public int getHeadsCount()
    {
        return len;
    }


    /**
     * �õ����Ա�ͷ�ĸ���
     * @return int
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
                s += "." + defaultAttrOutput[i].getAttrDisplayName().trim();
                output[i] = s;
            }
        }
        return output;
    }
    //Ѧ�� ��ӽ���
}
