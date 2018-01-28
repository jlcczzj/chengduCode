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
 * <p>Title:�豸���ܿ����� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author ����
 * @version 1.0
 * ����(1) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
 * ����(2) 2006.08.30 �촺Ӣ�޸�  �޸�ԭ��:�жϻ��ܽ��
 * ����(3)2007.06.04 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
 * ���⣨4��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
 * SS1 2013-2-20   ������ ���Ӽ�¼ģ������
 * SS2 2013-2-20   ������ ���ܽ������ӻ������� assetNumberField������  eqManuField���쳧��
 * SS3 �ɶ��豸��������豸������� guoxiaoliang 2016-8-4
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
    
    
    //����(1) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
    /**���ڱ����Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.summary.util.SummaryResource";


    /**
     * ������
     * @param mainFrame SummaryMainJFrame
     */
    public SummaryEQController(SummaryMainJFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        eqPanel = new EquipPanel(this);
        this.mainControl = mainFrame.mainControl;
    }


    /**
     *  �ڲ��ࣺ�߳�
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

                    //�����㲿��
                    searchQM();
                    break;
                case SUM:
                    mainFrame.setCursor(Cursor.WAIT_CURSOR);
                    mainFrame.setSumStatus(res.getString("wait"));
                    //���水ť����ʾ
                    eqPanel.setSaveButtonstate(false);
                    //���ݻ����������л���
                    eqPanel.setTotalResult(summary());
                    //���ػ��ܽ������Multilist����ʾ
                    eqPanel.setToMultilist();
                    //���水ť��ʾ
                    eqPanel.setSaveButtonstate(true);
                    mainFrame.setSumStatus(eqPanel.getSumInfo());
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
            //Ѧ�� end
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
     * ����ʵ��
     * @param e ActionEvent
     * ����(1) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
     */
    public void actionPerformed(ActionEvent e)
    {
       //���⣨4��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
       System.out.println("e.getActionCommand()="+e.getActionCommand());
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
        //���⣨4��  ��ӽ���
        if (e.getActionCommand().equals("CUSTO"))
        {
            totalType = (CodingIfc) SummaryMainController.sorts.get("�豸����");
            //Ѧ�� �޸�  20080222 �޸�ԭ���ڹ��ջ��ܽ���ġ�������ơ�����ѡ���Դ�������ʾĬ�ϵĸ����ͻ��ܵ��б�ǩ�
            String[] output = getExportCustomizeDialogDefaultOutput();
            ecDialog = new ExportCustomizeDialog(mainFrame, initial(),
                                                 totalType, output);
            //Ѧ�� �޸Ľ���
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
        //������ť����
        if (e.getActionCommand().equals("SEARCH"))
        {
            WorkThread sumThread = new WorkThread(SEARCH);
            sumThread.start();
        }
        //�豸���ܼ���
        if (e.getActionCommand().equals("SUM"))
        {
        	System.out.println("ddddd");
            sumThread1 = new WorkThread(SUM);
            sumThread1.start();
        }
        //�жϰ�ť����
        if (e.getActionCommand().equals("INTERRUPT"))
        {
            interruptThread();
            eqPanel.setSaveButtonstate(true);
            mainFrame.setSumStatus(eqPanel.getSumInfo());
            eqPanel.getMultilist().clear();
        }
        //���水ť����
        if (e.getActionCommand().equals("SAVE"))
        {
            if (totalType == null)
            {
                totalType = (CodingIfc) SummaryMainController.sorts.get("�豸����");
            }
              //CCBegin SS1
            if(eqPanel.getSummaryMode() == 1)
            {
            	resultSaveDialog = new SummaryResultSaveDialog(totalType,
                    mainControl, "�豸�嵥");
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
        //�˳���ť����
        if (e.getActionCommand().equals("EXIT"))
        {
            mainFrame.removePanel();
        }
        //������ť����
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
        	 //end CR1
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
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("�豸����");
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
            //����TotalService�����totalBySchema����
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
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("�豸����");
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
            JOptionPane.showMessageDialog(this.eqPanel, e.getClientMessage());
            return null;
        }
    }


    /**
     * ���÷���,����TotalAttrSchemaIfc[]����
     * @return TotalAttrSchemaIfc[]
     */
    public TotalAttrSchemaIfc[] initSchema()
    {
        try
        {
        	//20081128 Ѧ�� �޸� �޸�ԭ���ڹ��ջ��ܽ���ġ�������ơ�����ѡ���Դ�������ʾĬ�ϵĸ����ͻ��ܵ��б�ǩ�
        	CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("�豸����");
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
        //����TotalService�����getDefaultOutputSchema����
        totalAttrHeads = (TotalAttrSchemaIfc[]) getServiceObject(
                "TotalService",
                "getDefaultOutputSchema", theClass, myObj);
        	}
        	//Ѧ�� �޸� ����20081128
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
        	//20081128 Ѧ�� �޸� �޸�ԭ���ڹ��ջ��ܽ���ġ�������ơ�����ѡ���Դ�������ʾĬ�ϵĸ����ͻ��ܵ��б�ǩ�
        	CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("�豸����");
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
        //����TotalService�����getDefaultOutputSchema����
        totalAttrHeads = (TotalAttrSchemaIfc[]) getServiceObject(
                "TotalService",
                "getDefaultOutputSchema", theClass, myObj);
        	}
        	//Ѧ�� �޸� ����20081128
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
     * ������Ա�ͷ
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
     * �õ���ͷ�ĳ���
     * @return int
     */
    public int getHeadsCount()
    {
        return len;
    }


    /**
     * ʵ�ֹ��������ķ�װ,����FilterQuery����
     * @return FilterQuery
     * ����(3)2007.06.04 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
     */
    public FilterQuery getFilterQuery()
    {
        eqFilterQuery = new FilterQuery(false);
        if (totalType == null)
        {
            totalType = (CodingIfc) SummaryMainController.sorts.get("�豸����");
        }
        //���û�������
        eqFilterQuery.setType(totalType);
        try
        {
            //ͨ��part���ù淶�õ�����part��SQL���ʽ
            eqFilterQuery = getPartSQLExpression(eqPanel.getPartNum(),
                                                 eqFilterQuery);
            //�õ���������
            String techType = eqPanel.getTechType();
            if (techType != null)
            {
                if (eqPanel.isSelectTech())
                {
                    //�ǹ������ิѡ���Ƿ�ѡȡ
                    if (eqFilterQuery.getCount() != 0)
                    {
                        eqFilterQuery.addAND();
                    }
                    //�õ�����������QueryCondition
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
                    //�����ݿ�õ�����������QueryCondition
                    techTypeCondition = new QueryCondition("technicsType", "=",
                            techType);
                }
                //�õ�����������techTypeCondition
                techTypeFilterCondition = new FilterCondition("QMFawTechnics",
                        techTypeCondition);
                eqFilterQuery.addCondition(techTypeFilterCondition);
            }
            //�õ��豸����
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
                        //�����ݿ�õ�����������QueryCondition
                        equipmentTypeCondition = new QueryCondition("eqType",
                                QueryCondition.NOT_EQUAL, equipType.getBsoID());
                    }
                    else
                    {
                        //����FilterQuery�����з�װ��FilterCondition��Ŀ�������Ŀ��NULL
                        if (eqFilterQuery.getCount() != 0)
                        {
                            eqFilterQuery.addAND();
                        }
                        //�����ݿ�õ�����������QueryCondition
                        equipmentTypeCondition = new QueryCondition("eqType",
                                "=",
                                equipType.getBsoID());
                    }
                    //�õ�����������equipmentTypeCondition
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
            // �õ�������
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
                        //�����ݿ�õ�����������QueryCondition
                        departmentTypeCondition = new QueryCondition("workShop",
                                QueryCondition.NOT_EQUAL, depart.getBsoID());
                    }
                    else
                    {
                        if (eqFilterQuery.getCount() != 0)
                        {
                            eqFilterQuery.addAND();
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
                            //�õ�����������FilterCondition
                            departmentTypeCondition = new QueryCondition(
                                    "workShop",
                                    QueryCondition.IS_NULL);
                            //�õ�����������departmentTypeCondition
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
             //CCBegin by liunan 2011-4-26 ���쳧�� ��������
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
                 //CCBegin by liunan 2011-4-26 ���쳧�� ��������
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
                
            //����(3)2007.06.04 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
            //���⣨4��Ѧ�� 20080414  ���ԭ��:���ձ����������ť�ͷǸ�ѡ��
//            eqFilterQuery = getAssociateSQLExpression(eqPanel.getTechNum(),
//                    eqFilterQuery);
            boolean bool = eqPanel.isSelectTechnicsBso();
            eqFilterQuery = getAssociateSQLExpression(eqPanel.getTechNum(),
                    eqFilterQuery, bool);
            //���⣨4��Ѧ�� 20080414 �޸Ľ���
        }
        catch (QMException e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.eqPanel, e.getClientMessage());
        }
        return eqFilterQuery;
    }


    /**
     * �õ����ܵ�Լ������
     * @return TotalSchemaIfc
     */
    public TotalSchemaIfc getTotalSchemaIfc()
    {
        TotalSchemaIfc totalSchema = new TotalSchemaInfo();
        PartConfigSpecIfc configSpec = mainControl.getConfigSpec().
                                       getConfigSpecInfo();
        this.setPartConfig(null);
        //�ṹ��ѡ���Ƿ�ѡȡ
        if (eqPanel.isSelectPart())
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
        totalSchema.setDisplayQuery(eqPanel.getSumCondition());
        String partNumber = eqPanel.getPartNum();
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
        eqPanel.setAttributes(getAttrName());
        eqPanel.updateHeadings();
        eqPanel.setMultilistRow();
        eqPanel.newPaginatePanel();
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
    //Ѧ�� ��ӽ���
    //CCBegin SS1
    /**
     * ������Ա�ͷ
     * @return String[]
     * tangshutao modify 2007.11.06 ����int�Ͳ���
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
     * ���÷���,����TotalAttrSchemaIfc[]����
     * @return TotalAttrSchemaIfc[]
     * chudaming modify 2009.4.16 ����int�Ͳ���
     */
    public TotalAttrSchemaIfc[] initSchema(int i) {
      try {
        Class[] theClass = {
            CodingIfc.class, Integer.TYPE};
        CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("�豸����");
        
        Object[] myObj = {
            code, new Integer(i)};
        //����TotalService�����getDefaultOutputSchema����
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
