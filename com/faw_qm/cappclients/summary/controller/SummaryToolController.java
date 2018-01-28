/**
 * CR1 2012/04/12 ������ �μ�TD2512
 * SS1 2013-2-20   ������ ���ܽ������ӻ������� assetNumberField������  eqManuField���쳧��
 * SS2 2013-2-20   ������ ���Ӽ�¼ģ������
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
 * <p>Title:��װ���ܿ����� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author ����
 * 
 * @version 1.0
 * ����(1) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
 * ����(2)2007.06.04 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
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
    //����(1) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
    /**���ڱ����Դ�ļ�·��*/
    private static String RESOURCE =
            "com.faw_qm.cappclients.summary.util.SummaryResource";


    /*
     ** ���캯��
     */
    public SummaryToolController(SummaryMainJFrame mainFrame)
    {
        this.mainFrame = mainFrame;
        toolPanel = new ToolPanel(this);
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
                    toolPanel.setSaveButtonstate(false);
                    //���ݻ����������л���
                    toolPanel.setTotalResult(summary());
                    //���ػ��ܽ������Multilist����ʾ
                    toolPanel.setToMultilist();
                    //���水ť��ʾ
                    toolPanel.setSaveButtonstate(true);
                    //����״̬����ֵ
                    mainFrame.setSumStatus(toolPanel.getSumInfo());
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
        //���⣨4��  ��ӽ���
        if (e.getActionCommand().equals("CUSTO"))
        {

            totalType = (CodingIfc) SummaryMainController.sorts.get("��װ����");
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
        //CCBegin SS2
         if (e.getActionCommand().equals("gjylbd")) {
            toolPanel.setAttributes(this.getSchemaheadings(2));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setgjylbdButtonState();
            //CCBegin by liunan 2011-9-20 ���ݻ��ܶ���������ӹ������͡��������㼶�䶯
            toolPanel.sortingSelectedPanel2.setCoding(getClassificationByName("����","������ֹ�˾��װ����"));
            //CCEnd by liunan 2011-9-20
          }
        if (e.getActionCommand().equals("gjylbm")) {
            toolPanel.setAttributes(this.getSchemaheadings(3));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setgjylbmButtonState();
            //CCBegin by liunan 2011-9-20 ���ݻ��ܶ���������ӹ������͡�
            toolPanel.sortingSelectedPanel2.setCoding(getClassificationByName("ĥ��","������ֹ�˾��װ����"));
            //CCEnd by liunan 2011-9-20
          }
        if (e.getActionCommand().equals("wngjqd")) {
            toolPanel.setAttributes(this.getSchemaheadings(4));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setwngjqdButtonState();
            //CCBegin by liunan 2011-9-20 ���ݻ��ܶ���������ӹ������͡�
            toolPanel.sortingSelectedPanel2.setCoding(getCodingByName("���ܹ���","���ܹ���","������ֹ�˾��װ����"));
            //CCEnd by liunan 2011-9-20
          }
        if (e.getActionCommand().equals("jjmxb")) {
            toolPanel.setAttributes(this.getSchemaheadings(5));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setjjmxbButtonState();
            //CCBegin by liunan 2011-9-20 ���ݻ��ܶ���������ӹ������͡�
            toolPanel.sortingSelectedPanel2.setCoding(getClassificationByName("�о�","������ֹ�˾��װ����"));
            //CCEnd by liunan 2011-9-20
          }
        
        if (e.getActionCommand().equals("zbwnlj")) {
            toolPanel.setAttributes(this.getSchemaheadings(6));            
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setzbwnljButtonState();
            //CCBegin by liunan 2011-9-20 ���ݻ��ܶ���������ӹ������͡�
            toolPanel.sortingSelectedPanel2.setCoding(getCodingByName("��������","����","������ֹ�˾��װ����"));
            //CCEnd by liunan 2011-9-20
          }
        if (e.getActionCommand().equals("zbju")) {
            toolPanel.setAttributes(this.getSchemaheadings(7));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setzbjuButtonState();
            //CCBegin by liunan 2011-9-20 ���ݻ��ܶ���������ӹ������͡�
            toolPanel.sortingSelectedPanel2.setCoding(getClassificationByName("���","������ֹ�˾��װ����"));
            //CCEnd by liunan 2011-9-20
          }
        if (e.getActionCommand().equals("zbzylj")) {
            toolPanel.setAttributes(this.getSchemaheadings(8));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setzbzyljButtonState();
            //CCBegin by liunan 2011-9-20 ���ݻ��ܶ���������ӹ������͡�
            toolPanel.sortingSelectedPanel2.setCoding(getCodingByName("ר������","����","������ֹ�˾��װ����"));
            //CCEnd by liunan 2011-9-20
          }
        //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
        if (e.getActionCommand().equals("zpgjylb")) {
            toolPanel.setAttributes(this.getSchemaheadings(9));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setzpgjylbButtonState();
            //CCBegin by liunan 2011-9-20 ���ݻ��ܶ���������ӹ������͡�
            toolPanel.sortingSelectedPanel2.setCoding(getCodingByName("װ�乤��","װ�乤��","������ֹ�˾��װ����"));
            //CCEnd by liunan 2011-9-20
          }
        if (e.getActionCommand().equals("jfjylb")) {
            toolPanel.setAttributes(this.getSchemaheadings(10));
            toolPanel.updateHeadings();
            toolPanel.setAveRow();
            toolPanel.clearMultiList();
            toolPanel.setjfjylbButtonState();
            //CCBegin by liunan 2011-9-20 ���ݻ��ܶ���������ӹ������͡�
            toolPanel.sortingSelectedPanel2.setCoding(getCodingByName("�и���","�и���","������ֹ�˾��װ����"));
            //CCEnd by liunan 2011-9-20
          }
        //CCEnd SS2
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
            toolPanel.setSaveButtonstate(true);
            mainFrame.setSumStatus(toolPanel.getSumInfo());
            toolPanel.getMultilist().clear();
        }
        //���水ť����
        if (e.getActionCommand().equals("SAVE"))
        {
            if (totalType == null)
            {
                totalType = (CodingIfc) SummaryMainController.sorts.get("��װ����");
            }
            //CCBegin SS2��
            if(toolPanel.getSummaryMode() == 1||toolPanel.getSummaryMode() == 2||toolPanel.getSummaryMode() == 3
               ||toolPanel.getSummaryMode() == 4||toolPanel.getSummaryMode() == 5||toolPanel.getSummaryMode() == 6
               ||toolPanel.getSummaryMode() == 7||toolPanel.getSummaryMode() == 8||toolPanel.getSummaryMode() == 9)
            {
            	String typename = "";
            	if(toolPanel.getSummaryMode() == 1)
            	{
            		typename = "����һ����";
            	}
            	else if(toolPanel.getSummaryMode() == 2)
            	{
            		typename = "ĥ��һ����";
            	}
            	else if(toolPanel.getSummaryMode() == 3)
            	{
            		typename = "���ܹ����嵥";
            	}
            	else if(toolPanel.getSummaryMode() == 4)
            	{
            		typename = "�о���ϸ��";
            	}
            	else if(toolPanel.getSummaryMode() == 5)
            	{
            		typename = "������������";
            	}
            	else if(toolPanel.getSummaryMode() == 6)
            	{
            		typename = "�������";
            	}
            	else if(toolPanel.getSummaryMode() == 7)
            	{
            		typename = "����ר������";
            	}
            	else if(toolPanel.getSummaryMode() == 8)
            	{
            		typename = "װ�乤��һ����";
            	}
            	else if(toolPanel.getSummaryMode() == 9)
            	{
            		typename = "�и���һ����";
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
                  		//CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
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
                  //����(1) 2006.08.16 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
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
     *��ֹ�߳�
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
            Class[] theClass =
                    {
                    TotalSchemaIfc.class,Boolean.TYPE};
            schema = getTotalSchemaIfc();
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("��װ����");
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
            JOptionPane.showMessageDialog(this.toolPanel, e.getClientMessage());
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
            CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("��װ����");
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
            JOptionPane.showMessageDialog(this.toolPanel, e.getClientMessage());
            return null;
        }

    }


    /**
     * ʵ�ֹ��������ķ�װ,����FilterQuery����
     * @return FilterQuery
     * ����(2)2007.06.04 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
     */
    public FilterQuery getFilterQuery()
    {
        toolFilterQuery = new FilterQuery(false);
        if (totalType == null)
        {
            totalType = (CodingIfc) SummaryMainController.sorts.get("��װ����");
        }
        //���û�������
        toolFilterQuery.setType(totalType);
        try
        {
            //ͨ��part���ù淶�õ�����part��SQL���ʽ
            toolFilterQuery = getPartSQLExpression(toolPanel.getPartNum(),
                    toolFilterQuery);
            //�õ���������
            if (toolPanel.getTechType() != null)
            {
                //�ǹ������ิѡ���Ƿ�ѡȡ
                if (toolPanel.isSelectTech())
                {
                    if (toolFilterQuery.getCount() != 0)
                    {
                        toolFilterQuery.addAND();
                    }
                    //�õ�����������QueryCondition
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
                    //�õ�����������QueryCondition
                    techTypeCondition = new QueryCondition("technicsType", "=",
                            toolPanel.getTechType());
                }
                //�õ�����������techTypeCondition
                techTypeFilterCondition = new FilterCondition("QMFawTechnics",
                        techTypeCondition);
                toolFilterQuery.addCondition(techTypeFilterCondition);
            }
            //�õ���װ����
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
                        //�����ݿ�õ�����������QueryCondition
                        toolTypeCondition = new QueryCondition("toolCf",
                                QueryCondition.NOT_EQUAL, toolType.getBsoID());

                    }
                    else
                    {
                        //����FilterQuery�����з�װ��FilterCondition��Ŀ�������Ŀ��NULL
                        if (toolFilterQuery.getCount() != 0)
                        {
                            toolFilterQuery.addAND();
                        }
                        //�����ݿ�õ�����������QueryCondition
                        toolTypeCondition = new QueryCondition("toolCf", "=",
                                toolType.getBsoID());
                    }
                    //�õ�����������toolTypeCondition
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
            // �õ�������
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
                            //�õ�����������FilterCondition
                            departmentTypeCondition = new QueryCondition(
                                    "workShop",
                                    QueryCondition.IS_NULL);
                            //�õ�����������departmentTypeCondition
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

         ////CCBegin by chudaming 2008-12-31 ���ӻ�������
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
            //����(2)2007.06.04 xucy add ͨ�����ձ�ŵõ����ռ������湤��Ĺ������յ�sql
//            toolFilterQuery = getAssociateSQLExpression(toolPanel.getTechNum(),
//                    toolFilterQuery);
            boolean bool = toolPanel.isSelectTechnicsBso();
            toolFilterQuery = getAssociateSQLExpression(toolPanel.getTechNum(),
                    toolFilterQuery, bool);
            //���⣨4��Ѧ�� 20080414 �޸Ľ���

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
        if (toolPanel.isSelectPart())
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
        totalSchema.setDisplayQuery(toolPanel.getSumCondition());
        String partNumber = toolPanel.getPartNum();
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
        toolPanel.setAttributes(getAttrName());
        toolPanel.updateHeadings();
        toolPanel.setMultilistRow();
        toolPanel.newPaginatePanel();
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
        	CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("��װ����");
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
            JOptionPane.showMessageDialog(this.toolPanel, e.getClientMessage());
            return null;
        }

    }
    
    public TotalAttrSchemaIfc[] initSchematobsx()
    {
        try
        {
        	//20081128 Ѧ�� �޸� �޸�ԭ���ڹ��ջ��ܽ���ġ�������ơ�����ѡ���Դ�������ʾĬ�ϵĸ����ͻ��ܵ��б�ǩ�
        	CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("��װ����");
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
        //����TotalService�����getDefaultOutputSchema������
        AttrHeads = (TotalAttrSchemaIfc[]) getServiceObject(
                "TotalService",
                "getDefaultOutputSchema", theClass, myObj);
        	}
            //Ѧ�� 20081128 �޸Ľ���
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
    //Ѧ�� ��ӽ���
    //CCBegin SS1
    /*
     **������Ա�ͷ
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
     * ���÷���,����TotalATtrSchemaIfc[]����
     * @return TotalAttrSchemaIfc[]
     */

    public TotalAttrSchemaIfc[] initSchema(int i) {
      try {
        Class[] theClass = {
            CodingIfc.class, Integer.TYPE};
        CodingIfc code = (CodingIfc) SummaryMainController.sorts.get("��װ����");
        Object[] myObj = {
            code, new Integer(i)};
        //����TotalService�����getDefaultOutputSchema������
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
 * ��ȡ��������е�ֵ.
 * @param name String ��������е�ǰ����ڵ������.
 * @param name String ��������е�ǰ���������.
 * @param parent String ��������е�ǰ����ڵ�ĸ��ڵ�����.
 * @return String[] ������ֵ����.
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
 * ��ȡ��������е�ֵ.
 * @param name String ��������е�ǰ����ڵ������.
 * @param parent String ��������е�ǰ����ڵ�ĸ��ڵ�����.
 * @return String[] ������ֵ����.
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
                        "�ڴ�Ŀ¼���д��ļ����Ƿ񸲸�", "��ʾ",
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
   if (ss.indexOf("�㲿����") != -1) {
   partnum = ss.substring(ss.indexOf("�㲿����") + 5);
  //����㲿������
   QMPartMasterIfc part = this.getPartIfc(partnum);
   if (part != null) {
   partname = part.getPartName();
   //����㲿�����ó��ͣ����ڳ�ѹ���乤����ϸ��
    //  chexing = this.getFirstCarType(part.getBsoID());
     }
     }
    if (ss.indexOf("��װ����") != -1) {
    toolType = ss.substring(ss.indexOf("��װ����") + 5);
    }
    if (ss.indexOf("����") != -1) {
    department = ss.substring(ss.indexOf("����") + 3);
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
   //CCBegin by liunan 2012-2-7 ���� ��װ�乤��һ���� ���и���һ����
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
 * �����㲿�����񣬸����㲿����Ż���㲿��ֵ����Ϊ��ȡ�㲿����������Ϣ��׼��
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
