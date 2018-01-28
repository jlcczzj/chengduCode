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
import com.faw_qm.cappclients.summary.view.MTPanel;
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
 * <p>Title: �������Ͽ�����</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author ����
 * @version 1.0
 * ����(1) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
 * ����(2) 2006.08.30 �촺Ӣ�޸�  �޸�ԭ��:�жϻ��ܽ��
 * ����(3)2007.06.05 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
 * SS1 2013-2-20   ������ ���ܽ������ӻ������� assetNumberField������  eqManuField���쳧��
 */

public class SummaryMTController extends SummaryController
{
    private SummaryMainJFrame mainFrame;
    MTPanel mtPanel;
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
    private TotalResultIfc totalResult;
    private int len;
    private SummaryMainController mainControl;
    private WorkThread sumThread;
    private WorkThread sumThread1;
    private CappChooser qmchooser;
    //CCBegin by chudaming 2008-9-25 ���ӻ�������
    private FilterCondition assetNumberFilterCondition;
    private FilterCondition materialNameFilterCondition;
    private FilterCondition materialFunctionFilterCondition;
    private QueryCondition assetNumberCondition;
    private QueryCondition materialFunctionCondition;
    private QueryCondition materialNameCondition;
    //CCEnd by chudaming 2008-9-25
    //����(1) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
    /**���ڱ����Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.summary.util.SummaryResource";


    /**
     * ���캯��
     * @param mainFrame SummaryMainJFrame
     */
    public SummaryMTController(SummaryMainJFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        mtPanel = new MTPanel(this);
        this.mainControl = mainFrame.mainControl;
    }


    /**
     * �ڲ��ࣺ�߳�
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
                    mtPanel.setSaveButtonstate(false);
                    //���ݻ����������л���
                    mtPanel.setTotalResult(summary());
                    //���ػ��ܽ������Multilist����ʾ
                    mtPanel.setToMultilist();
                    //���水ť��ʾ
                    mtPanel.setSaveButtonstate(true);
                    //����״̬����ֵ
                    mainFrame.setSumStatus(mtPanel.getSumInfo());
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

        qmchooser.setLastIteration(true);
        try
        {
        	//Ѧ�� 20081225 start
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
                            mtPanel.setPart((QMPartInfo) equip);
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
     * ������
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
                            mtPanel.setTechniceNumber(ti.getTechnicsNumber());
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
            totalType = (CodingIfc) SummaryMainController.sorts.get("�������ϻ���");
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
            mtPanel.setSaveButtonstate(true);
            mainFrame.setSumStatus(mtPanel.getSumInfo());
            mtPanel.getMultilist().clear();
        }
        //���水ť����
        if (e.getActionCommand().equals("SAVE"))
        {
            if (totalType == null)
            {
                totalType = (CodingIfc) SummaryMainController.sorts.get(
                        "�������ϻ���");
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
                    vector = mtPanel.exportResult();
                }
                Class[] lass =
                               {
                               Vector.class, String.class, Boolean.TYPE};
                Object[] obj =
                               {
                               vector,
                               new StringTokenizer(file.getAbsolutePath(), ".").
                               nextElement(),
                               new Boolean(true)};
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
            Class[] theClass ={TotalSchemaIfc.class,Boolean.TYPE};
            schema = getTotalSchemaIfc();
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get(
                    "�������ϻ���");
            schema.setTotalType(code);
            schema.setSubTotalType(1);
             boolean flag = getFlag();
            Object[] theObj ={schema,new Boolean(flag)};
            //����TotalService�����totalBySchema����
            totalResult = (TotalResultIfc) getServiceObject("TotalService",
                    "totalBySchema", theClass, theObj);
            return totalResult;
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.mtPanel, e.getClientMessage());
            return null;
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
                    "�������ϻ���");
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
            JOptionPane.showMessageDialog(this.mtPanel, e.getClientMessage());
            return null;
        }

    }


    /**
     * ʵ�ֹ��������ķ�װ,����FilterQuery����
     * @return FilterQuery
     * ����(3)2007.06.05 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
     */
    public FilterQuery getFilterQuery()
    {
        mtFilterQuery = new FilterQuery(false);
        if (totalType == null)
        {
            totalType = (CodingIfc) SummaryMainController.sorts.get("�������ϻ���");
        }
        //���û�������
        mtFilterQuery.setType(totalType);
        try
        {
            //ͨ��part���ù淶�õ�����part��SQL���ʽ
            mtFilterQuery = getPartSQLExpression(mtPanel.getPartNum(),
                                                 mtFilterQuery);
            //�õ���������
            if (mtPanel.getTechType() != null)
            {
                //�ǹ������ิѡ���Ƿ�ѡȡ
                if (mtPanel.isSelectTech())
                {
                    if (mtFilterQuery.getCount() != 0)
                    {
                        mtFilterQuery.addAND();
                    }
                    //�õ�����������QueryCondition
                    techTypeCondition = new QueryCondition("technicsType",
                            QueryCondition.NOT_EQUAL,
                            mtPanel.getTechType());
                }
                else
                {
                    if (mtFilterQuery.getCount() != 0)
                    {
                        mtFilterQuery.addAND();
                    }
                    //�õ�����������QueryCondition
                    techTypeCondition = new QueryCondition("technicsType", "=",
                            mtPanel.getTechType());
                }
                //�õ�����������techTypeCondition
                techTypeFilterCondition = new FilterCondition("QMFawTechnics",
                        techTypeCondition);
                mtFilterQuery.addCondition(techTypeFilterCondition);
            }

            if (mtPanel.getMtpType() != null)
            {
                BaseValueIfc mtType = mtPanel.getMtpType();
                if (mtType instanceof CodingIfc)
                {
                    if (mtPanel.isSelectEquipment())
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
                        if (mtFilterQuery.getCount() != 0)
                        {
                            mtFilterQuery.addAND();
                        }
                        //�����ݿ�õ�����������QueryCondition
                        materialTypeCondition = new QueryCondition("materialCf",
                                "=",
                                mtType.getBsoID());

                    }
                    //�õ�����������materialTypeCondition
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
                        if (mtPanel.isSelectEquipment())
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
            // �õ�������
            if (mtPanel.getProcedureDepart() != null)
            {
                BaseValueIfc depart = mtPanel.getProcedureDepart();
                if (depart instanceof CodingIfc)
                {
                    if (mtFilterQuery.getCount() != 0)
                    {
                        mtFilterQuery.addAND();
                    }
                    if (mtPanel.isSelectDepartment())
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
                            "QMProcedure",
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
//                        if (mtPanel.isSelectDepartment())
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
//                        mtFilterQuery.addCondition(
//                                departmentTypeFilterCondition);
//                    }
//                    if (col != null && col.size() == 0)
//                    {
//                        if (!mtPanel.isSelectDepartment())
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
//                                    "QMProcedure",
//                                    departmentTypeCondition);
//                            mtFilterQuery.addCondition(
//                                    departmentTypeFilterCondition);
//                        }
//                    }
                    if (mtFilterQuery.getCount() != 0)
                    {
                        mtFilterQuery.addAND();
                    }
                    if (mtPanel.isSelectDepartment())
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
            if (mtPanel.getAssetNumber() != null && !mtPanel.getAssetNumber().trim().equals(""))
        {
         String assetNumber = mtPanel.getAssetNumber();
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
            //�õ�������;
            if (mtPanel.getMaterialFunction() != null && !mtPanel.getMaterialFunction().trim().equals(""))
        {
         String materialFunction = mtPanel.getMaterialFunction();
         if (mtFilterQuery.getCount() != 0)
         {
         mtFilterQuery.addAND();
         }
         //CCBegin by liunan 2011-5-4 ������;���벻�ԡ�
         //materialFunctionCondition = new QueryCondition("taskInstructNum", "=",
         materialFunctionCondition = new QueryCondition("materialFunction", "=",
         //CCEnd by liunan 2011-5-4
         materialFunction);
         materialFunctionFilterCondition = new FilterCondition(
         "QMMaterial",
         materialFunctionCondition);
         mtFilterQuery.addCondition(materialFunctionFilterCondition);
         }
            //�õ���������
            if (mtPanel.getMaterialName() != null && !mtPanel.getMaterialName().trim().equals(""))
        {
         String materialName = mtPanel.getMaterialName();
         if (mtFilterQuery.getCount() != 0)
         {
         mtFilterQuery.addAND();
         }
         //CCBegin by liunan 2011-5-4 �������ƴ��벻�ԡ�
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
            //����(3)2007.06.05 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
//           mtFilterQuery = getAssociateSQLExpression(mtPanel.getTechNum(),
//                   mtFilterQuery);
            boolean bool = mtPanel.isSelectTechnicsBso();
            mtFilterQuery = getAssociateSQLExpression(mtPanel.getTechNum(),
                    mtFilterQuery, bool);


        }
        catch (QMException e)
        {
            JOptionPane.showMessageDialog(this.mtPanel, e.getClientMessage());
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
        if (mtPanel.isSelectPart())
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
        totalSchema.setDisplayQuery(mtPanel.getSumCondition());
        String partNumber = mtPanel.getPartNum();
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
        mtPanel.setAttributes(getAttrName());
        mtPanel.updateHeadings();
        mtPanel.setMultilistRow();
        mtPanel.newPaginatePanel();
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
            "�������ϻ���");
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
            JOptionPane.showMessageDialog(this.mtPanel, e.getClientMessage());
            return null;
        }
    }


    /**
     * ������Ա�ͷ
     * @return String[]
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
                s += "." + defaultAttrOutput[i].getAttrDisplayName();
                output[i] = s;
            }
        }
        return output;
    }
    //Ѧ�� ��ӽ���
}
