package com.faw_qm.cappclients.summary.controller;

/**
 * SS1 �ɶ������㲿��һ������ܣ��������һ�������  guoxiaoliang 2016-8-4    
 */

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.faw_qm.cappclients.beans.query.CappChooser;
import com.faw_qm.cappclients.beans.query.CappQueryEvent;
import com.faw_qm.cappclients.beans.query.CappQueryListener;
import com.faw_qm.cappclients.beans.resourcetreepanel.ReportTreeObject;
import com.faw_qm.cappclients.beans.resourcetreepanel.ResourceTreePanel;
import com.faw_qm.cappclients.summary.view.HelpDialog;
import com.faw_qm.cappclients.summary.view.SummaryMainJFrame;
import com.faw_qm.cappclients.summary.view.SummaryRenameDialog;
import com.faw_qm.cappclients.summary.view.SummaryResultSaveDialog;
import com.faw_qm.cappclients.util.CappTreeHelper;
import com.faw_qm.cappclients.util.CappTreeObject;
import com.faw_qm.clients.beans.explorer.ProgressDialog;
import com.faw_qm.clients.util.MessageDialog;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.help.QMHelpSystem;
import com.faw_qm.lock.exception.LockException;
import com.faw_qm.lock.model.LockIfc;
import com.faw_qm.part.client.main.model.ConfigSpecItem;
import com.faw_qm.part.client.main.util.PartHelper;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.resource.exception.ResourceException;
import com.faw_qm.resource.util.ResourceHelper;
import com.faw_qm.summary.model.TotalResultIfc;
import com.faw_qm.summary.model.TotalSchemaIfc;
import com.faw_qm.summary.model.TotalSchemaItemIfc;
import com.faw_qm.summary.model.TotalSchemaItemInfo;
import com.faw_qm.summary.util.TotalInfo;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.clients.util.IntroduceDialog;


/**
 * <p>Title:�������� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 * ����(1) 2006.08.09 �촺Ӣ�޸�  �޸�ԭ��: ��ӽ����һ��״̬����һ��״̬֮ǰ����Ϣ��ʾ
 * ȥ���������
 * ����(2)2006.08.17 �촺Ӣ�޸�  �޸�ԭ��:�����ڿͻ����ж��Ƿ����
 * ����(3)2006.08.17 �촺Ӣ�޸�  �޸�ԭ��:���������õ��������
 */

public class SummaryMainController extends SummaryController
{
    private SummaryMainJFrame mainFrame;
    private ResourceTreePanel summaryTree;
    private TotalSchemaItemIfc summaryObj;
    private SummaryRenameDialog renameDialog;
    private SummaryResultSaveDialog resultSaveDialog;
    private ConfigSpecItem configSpecItem = null;
    private SummaryEditController editControl;
    private boolean flag = true;
    private TotalInfo totalInfo;
    private final static int EQ = 4;
    private final static int TOOL = 5;
    private final static int MT = 6;
    private final static int PD = 7;
    private final static int MH = 8;
    private final static int CS = 9;
    private final static int VIEW = 10;
    private final static int UPDATE = 11;
    private final static int DELETE = 12;
    private final static int RESUM = 13;
    private final static int SAVEAS = 14;
    private final static int RENAME = 15;
    private final static int RESULTSAVE = 17;
    private final static int RESULTSEARCH = 18;
    private final static int CONFIGSPEC = 29;
    private final static int MAINMT = 30;
    private QMHelpSystem helpSystem;
    public static Hashtable sorts = initType();
    private ProgressDialog progress ;
    
    //CCBegin SS1
    private final static int MPT = 31;
    private final static int RPT = 32;
    //CCEnd SS1


    /**���ڴ�ż���ֵ�����Vector*/
    private HashMap hashMap = new HashMap();


    /**
     * �õ������������
     * @return Hashtable
     */
    private static Hashtable initType()
    {
        try
        {
            Hashtable t = new Hashtable();
            Hashtable[] hashTables = (Hashtable[]) CappTreeHelper.getCodingTree(
                    "��������", "����");
            Hashtable hashTable = hashTables[1];
            Enumeration e = hashTable.elements();
            while (e.hasMoreElements())
            {
                CodingIfc info = (CodingIfc) e.nextElement();
                t.put(info.getCodeContent(), info);
            }
            return t;
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * ��ô�ż��������map
     * @return HashMap
     */
    public HashMap getHashMap()
    {
        return hashMap;
    }


    /**
     * ���캯��
     */
    public SummaryMainController()
    {
        mainFrame = new SummaryMainJFrame(this);
        progress = new ProgressDialog(mainFrame);
        configInit();
        summaryTree = mainFrame.getSummaryTree();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = mainFrame.getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        Dimension scmSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(scmSize.width, scmSize.height);
        mainFrame.setVisible(true);
    }


    /**
     * ���캯��
     * @param flag boolean
     */
    public SummaryMainController(boolean flag)
    {
        this.flag = flag;
        mainFrame = new SummaryMainJFrame(this);
        configInit();
        summaryTree = mainFrame.getSummaryTree();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = mainFrame.getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        Dimension scmSize = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setSize(scmSize.width, scmSize.height);
        mainFrame.setVisible(true);
    }


    /**
     * �ڲ��ࣺ�߳�
     * ����(1) 2006.08.09 �촺Ӣ�޸�  �޸�ԭ��: ��ӽ����һ��״̬����һ��״̬֮ǰ����Ϣ��ʾ
     * ȥ���������
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
                //�豸����
                case EQ:
                    if(preprocess())
                    {
                        openEquipment();
                    }
                    break;
                //��װ����
                case TOOL:
                    if(preprocess())
                    {
                        openTool();
                    }
                    break;
                //�������ϻ���
                case MT:
                    if(preprocess())
                    {
                        openMT();
                    }
                    break;
                // �������
                case PD:
                    if(preprocess())
                    {
                        openPD();
                    }
                    break;
                    
                    //CCBegin SS1
                    
                    // ���һ�������
                case MPT:
                  if (preprocess()) {
                    openMPT();
                  }
                  break;

                case RPT:
                  if (preprocess()) {
                    openRPT();
                  }
                  break;
                    //CCEnd SS1
                    
                // ��ʱ����
                case MH:
                    if(preprocess())
                    {
                        openMH();
                    }
                    break;
                //��Ҫ���ϻ���
                case MAINMT:
                    if(preprocess())
                    {
                        openMainMT();
                    }
                    break;
                //���ƻ���
                case CS:
                    if(preprocess())
                    {
                        openCS();
                    }
                    break;
                //�༭����:�鿴
                case VIEW:
                    if (preprocess())
                    {
                        openVIEW();
                    }
                    break;
                //�༭����:����
                case UPDATE:
                    if (preprocess())
                    {
                        openUPDATE();
                    }
                    break;
                //�༭����:ɾ��
                case DELETE:
                    if (preprocess())
                    {
                        openDELETE();
                    }
                    break;
                //�༭����:���»���
                case RESUM:
                    if (preprocess())
                    {
                        openRESUM();
                    }
                    break;
                //�༭����:���Ϊ
                case SAVEAS:
                    if (preprocess())
                    {
                        openSAVEAS();
                    }
                    break;
                //�༭����:������
                case RENAME:
                    if (preprocess())
                    {
                        openRENAME();
                    }
                    break;
                //�༭����:�������Ի����еı���
                case RESULTSAVE:
                    if (resultSaveDialog.checkValue())
                    {
                        progress.startProcess();
                        if (resultSave())
                        {
                            addTreeCode();
                            resultSaveDialog.dispose();
                            openVIEW();
                        }
                        progress.stopProcess();
                    }
                    break;
                //�������ܽ��
                case RESULTSEARCH:
                    resultSearch();
                    break;
                //���ù淶
                case CONFIGSPEC:
                    openConfigSpec();
                    break;
            }
        }
    }


    /**
     * ����¼�����
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e)
    {
        //�豸����
        if (e.getActionCommand().equals("EQ"))
        {
            WorkThread thread = new WorkThread(EQ);
            thread.start();
        }
        //��װ����
        if (e.getActionCommand().equals("TOOL"))
        {
            WorkThread thread = new WorkThread(TOOL);
            thread.start();
        }
        //�������ϻ���
        if (e.getActionCommand().equals("MT"))
        {
            WorkThread thread = new WorkThread(MT);
            thread.start();
        }
        //��Ҫ���ϻ���
        if (e.getActionCommand().equals("MAINMT"))
        {
            WorkThread thread = new WorkThread(MAINMT);
            thread.start();
        }
        //�������
        if (e.getActionCommand().equals("PD"))
        {
            WorkThread thread = new WorkThread(PD);
            thread.start();
        }
        //��ʱ����
        if (e.getActionCommand().equals("MH"))
        {
            WorkThread thread = new WorkThread(MH);
            thread.start();
        }
        
        //CCBegin SS1
        
        //�������һ�������
        if (e.getActionCommand().equals("MPT")) {
          WorkThread thread = new WorkThread(MPT);
          thread.start();
        }
        //�������һ�������
        if (e.getActionCommand().equals("RPT")) {
          WorkThread thread = new WorkThread(RPT);
          thread.start();
        }
        
        //CCEnd SS1
        
        
        //���ƻ���
        if (e.getActionCommand().equals("CS"))
        {
            WorkThread thread = new WorkThread(CS);
            thread.start();
        }
        //�༭����:�鿴 2006.08.09 �촺Ӣ�޸�  ����������ť�Ͳ˵���ͬ������һ����
        if (e.getActionCommand().equals("VIEW") || e.getActionCommand().equals("total_view"))
        {
            WorkThread thread = new WorkThread(VIEW);
            thread.start();
        }
        //�༭����:���� 2006.08.09 �촺Ӣ�޸�  ����������ť�Ͳ˵���ͬ������һ����
        if (e.getActionCommand().equals("UPDATE")||e.getActionCommand().equals("total_update"))
        {
            WorkThread thread = new WorkThread(UPDATE);
            thread.start();
        }
        //�༭����:ɾ�� 2006.08.09 �촺Ӣ�޸�  ����������ť�Ͳ˵���ͬ������һ����
        if (e.getActionCommand().equals("DELETE")||e.getActionCommand().equals("total_delete"))
        {
            WorkThread thread = new WorkThread(DELETE);
            thread.start();
        }
        //�༭����:����
        if (e.getActionCommand().equals("RESUM"))
        {
            WorkThread thread = new WorkThread(RESUM);
            thread.start();
        }
        //�༭����:���Ϊ
        if (e.getActionCommand().equals("SAVEAS"))
        {
            WorkThread thread = new WorkThread(SAVEAS);
            thread.start();
        }
        //�༭����:������
        if (e.getActionCommand().equals("RENAME"))
        {
            WorkThread thread = new WorkThread(RENAME);
            thread.start();
        }
        // �༭����:�������Ի����еı���
        if (e.getActionCommand().equals("RENAMESAVE"))
        {
            //�����Ż�����Ϊ�գ��򷵻�
            if (renameDialog != null && renameDialog.validity())
            {
                return;
            }
            openRENAMESAVE();
        }
        //���ܽ�������еı���
        if (e.getActionCommand().equals("RESULTSAVE"))
        {
            WorkThread thread = new WorkThread(RESULTSAVE);
            thread.start();
        }
        //�༭���ܣ����� 2006.08.09 �촺Ӣ�޸�  ����������ť�Ͳ˵���ͬ������һ����
        if (e.getActionCommand().equals("RESULTSEARCH")||e.getActionCommand().equals("public_search"))
        {
            WorkThread thread = new WorkThread(RESULTSEARCH);
            thread.start();
        }

        //���ù淶
        if (e.getActionCommand().equals("CONFIGSPEC"))
        {
            WorkThread thread = new WorkThread(CONFIGSPEC);
            thread.start();
        }
        //����
        if (e.getActionCommand().equals("HELP"))
        {
            if (helpSystem == null)
            {
                try
                {
                    helpSystem = new QMHelpSystem("Summary", null, "OnlineHelp",
                                                  ResourceBundle.getBundle(
                            "com.faw_qm.cappclients.summary.util.SummaryResource",
                            RemoteProperty.getVersionLocale()));
                }
                catch (Exception exception)
                {
                    (new MessageDialog(this.getMainFrame(), true,
                                       exception.getLocalizedMessage())).
                            setVisible(true);
                }
            }
            helpSystem.showHelp("QMSummary");
        }
        //����
        if (e.getActionCommand().equals("ABOUT"))
        {
            String s1=QMMessage.getLocalizedMessage(RESOURCE,"summaryAbout", null);
            IntroduceDialog aboutDialog=new IntroduceDialog(mainFrame,s1);
            aboutDialog.setVisible();
        }
    }


    /**
     * ����"�ṹɸѡ����"����
     */
    public void setConfigSpec(Object obj)
    {
        this.configSpecItem = (ConfigSpecItem) obj;
    }


    /**
     * ����ɸѡ����
     */
    public ConfigSpecItem getConfigSpec()
    {
        return configSpecItem;
    }


    /**
     * �������ܽ��
     */
    private void resultSearch()
    {
        CappChooser cappchooser = new CappChooser("TotalSchemaItem", "�������ܽ��",
                                                  this.mainFrame);
        cappchooser.removeViewButton();

        try
        {
            cappchooser.setMultipleMode(true);
            cappchooser.addListener(new CappQueryListener()
            {
                public void queryEvent(CappQueryEvent qmqueryevent)
                {
                    if (qmqueryevent.getType().equals("Command") &&
                        qmqueryevent.getCommand().equals("OK"))
                    {
                        CappChooser qmchooser1 = (CappChooser) qmqueryevent.
                                                 getSource();
                        BaseValueIfc[] lequips = qmchooser1.getSelectedDetails();
                        Vector vec = new Vector();
                        for (int i = 0; i < lequips.length; i++)
                        {
                            TotalSchemaItemInfo totalSchema = (
                                    TotalSchemaItemInfo) lequips[i];
                            vec.add(new ReportTreeObject(totalSchema));
                        }
                        summaryTree.addResources(vec);
                        mainFrame.removePanel();
                    }
                }
            });
            cappchooser.setVisible(true);

        }

        catch (Exception ee)
        {
            ee.printStackTrace();
        }
    }


    /**
     * �豸����
     */
     //Ѧ�� 20081104 �޸� ���������Ҽ��˵�
    public void openEquipment()
    {
        mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        SummaryEQController eqControl = new SummaryEQController(mainFrame);
        mainFrame.removePanel();
        mainFrame.setSumLabel(res.getString("equipment_summary"));
        mainFrame.addSummaryPanel(eqControl.eqPanel);
        mainFrame.show();
        mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    /**
     * ��װ����
     */
     //Ѧ�� 20081104 �޸� ���������Ҽ��˵�
    public void openTool()
    {
        mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        SummaryToolController toolControl = new SummaryToolController(mainFrame);
        mainFrame.removePanel();
        mainFrame.setSumLabel(res.getString("tool_summary"));
        mainFrame.addSummaryPanel(toolControl.toolPanel);
        mainFrame.show();
        mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    /**
     * �������ϻ���
     */
     //Ѧ�� 20081104 �޸� ���������Ҽ��˵�
    public void openMT()
    {
        mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        SummaryMTController mtControl = new SummaryMTController(mainFrame);
        mainFrame.removePanel();
        mainFrame.setSumLabel(res.getString("material_summary"));
        mainFrame.addSummaryPanel(mtControl.mtPanel);
        mainFrame.show();
        mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    /**
     * ��Ҫ���ϻ���
     */
     //Ѧ�� 20081104 �޸� ���������Ҽ��˵�
    public void openMainMT()
    {
        mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        SummaryMainMTController mainmtControl = new SummaryMainMTController(
                mainFrame);
        mainFrame.removePanel();
        mainFrame.setSumLabel(res.getString("mainmaterial_summary"));
        mainFrame.addSummaryPanel(mainmtControl.mainMtPanel);
        mainFrame.show();
        mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    /**
     * �������
     */
     //Ѧ�� 20081104 �޸� ���������Ҽ��˵�
    public void openPD()
    {
        mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        SummaryPDController pdControl = new SummaryPDController(mainFrame);
        mainFrame.removePanel();
        mainFrame.setSumLabel(res.getString("prodedure_summary"));
        mainFrame.addSummaryPanel(pdControl.pdPanel);
        mainFrame.show();
        mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }


    /**
     * ��ʱ����
     */
     //Ѧ�� 20081104 �޸� ���������Ҽ��˵�
    public void openMH()
    {
        mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        SummaryMHController mhControl = new SummaryMHController(mainFrame);
        mainFrame.removePanel();
        mainFrame.setSumLabel(res.getString("manhour_summary"));
        mainFrame.addSummaryPanel(mhControl.mhPanel);
        mainFrame.show();
        mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    //CCBegin SS1
    
    /**
     * tangshutao 2008.01.17
     * ���һ�������
     */
    private void openMPT() {
      mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
      SummaryMPTController pdControl = new SummaryMPTController(mainFrame);
      mainFrame.removePanel();
      mainFrame.setSumLabel("�������һ�������");
      mainFrame.addSummaryPanel(pdControl.ptPanel);
      mainFrame.show();
      mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    private void openRPT() {
      mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
      SummaryRPTController rptControl = new SummaryRPTController(mainFrame);
      mainFrame.removePanel();
      mainFrame.setSumLabel("�������һ�������");
      mainFrame.addSummaryPanel(rptControl.ptPanel);
      mainFrame.show();
      mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    //CCEnd SS1


    /**
     * ���ƻ���
     */
    private void openCS()
    {
        new SummaryCustomizeController(mainFrame);
    }


    /**
     * ��õ�ǰѡ��Ķ���(����ǩ�ڵ�)��
     * ���û��ѡ����󷵻�null,���׳���ʾ��Ϣ��
     * @return ��ǰѡ��Ķ���
     * @throws QMRemoteException
     */
    public CappTreeObject getSelectedObject()
            throws QMRemoteException
    {
        if (summaryTree.getSelectedObject() == null ||
            summaryTree.getSelectedObject().getType() ==
            summaryTree.getSelectedObject().LABELOBJECT)
        {
            String message = res.getString("NOT_SELECT_OBJECT");
            throw new QMRemoteException(message);
        }
        else
        {
            return summaryTree.getSelectedObject();
        }
    }


    /**
     * �༭����:�鿴
     */
    public void openVIEW()
    {
        try
        {
            CappTreeObject obj = this.getSelectedObject();
            if (obj != null && obj.getType() == obj.BUSINESSOBJECT)
            {
                summaryObj = (TotalSchemaItemIfc) obj.getObject();

                summaryObj = (TotalSchemaItemIfc) CappTreeHelper.
                             refreshInfo(summaryObj.getBsoID());
                editControl = new SummaryEditController("VIEW",
                        mainFrame, summaryObj);
                mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                mainFrame.removePanel();
                mainFrame.setSumLabel(res.getString("edit_summary"));
                mainFrame.addSummaryPanel(editControl.getPanel());
                mainFrame.show();
                mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
        catch (QMException e)
        {
            progress.stopProcess();
            JOptionPane.showMessageDialog(mainFrame, e.getClientMessage(),
                                          res.getString("information"),
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }


    /**
     * �༭����:���»���
     */
    public void openRESUM()
    {
        TotalResultIfc totalResult;
        Object obj = summaryTree.getSelectedObject().getObject();
        TotalSchemaItemIfc temp = (TotalSchemaItemIfc) obj;
        try
        {
            temp = (TotalSchemaItemIfc) CappTreeHelper.refreshInfo(temp.
                    getBsoID());
        }
        catch (QMRemoteException ex)
        {
            JOptionPane.showMessageDialog(mainFrame, ex.getClientMessage(),
                                          res.getString("information"),
                                          JOptionPane.INFORMATION_MESSAGE);
            mainFrame.removePanel();
            //summaryTree.removeResource(summaryTree.getSelectedObject());
            return;
        }
        int result = JOptionPane.showConfirmDialog(mainFrame,
                res.getString("resum"),
                res.getString("information"),
                JOptionPane.YES_NO_OPTION);
        switch (result)
        {
            case JOptionPane.YES_OPTION:
            {
                mainFrame.setCursor(Cursor.WAIT_CURSOR);
                // Object obj = summaryTree.getSelectedObject().getObject();
                summaryObj = (TotalSchemaItemIfc) temp;
                //}
                //���÷������»���
                try
                {
                    Class[] theClass =
                            {
                            TotalSchemaIfc.class};
                    Object[] myObj =
                            {
                            summaryObj};
                    totalResult = (TotalResultIfc) getServiceObject(
                            "TotalService",
                            "totalBySchema", theClass, myObj);

                    summaryObj.setResult(totalResult);
                }
                catch (QMRemoteException e)
                {
                    mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this.mainFrame,
                                                  e.getClientMessage());
                }
                catch (QMException e)
                {
                    mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    e.printStackTrace();
                }
                editControl = new SummaryEditController("RESUM",
                        mainFrame, summaryObj);
                //�Ƴ����������棬����豸���ܽ���
                mainFrame.removePanel();
                mainFrame.setSumLabel(res.getString("edit_summary"));
                mainFrame.addSummaryPanel(editControl.getPanel());
                mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                mainFrame.show();
                break;
            }
            case JOptionPane.NO_OPTION:
                break;
        }
    }


    /**
     * �༭����:���Ϊ
     */
    public void openSAVEAS()
    {
        Object obj = summaryTree.getSelectedObject().getObject();
        TotalSchemaItemIfc temp = (TotalSchemaItemIfc) obj;
        try
        {
            temp = (TotalSchemaItemIfc) CappTreeHelper.refreshInfo(temp.
                    getBsoID());
        }
        catch (QMRemoteException ex)
        {
            JOptionPane.showMessageDialog(mainFrame, ex.getClientMessage(),
                                          res.getString("information"),
                                          JOptionPane.INFORMATION_MESSAGE);
            mainFrame.removePanel();
            summaryTree.removeResource(summaryTree.getSelectedObject());
            return;
        }
        mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        summaryObj = (TotalSchemaItemIfc) summaryTree.getSelectedObject().
                     getObject();
        editControl = new SummaryEditController("SAVEAS",
                                                mainFrame, summaryObj);
        mainFrame.removePanel();
        mainFrame.setSumLabel(res.getString("edit_summary"));
        mainFrame.addSummaryPanel(editControl.getPanel());
        mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        mainFrame.show();
    }


    /**
     * �༭����:����
     */
    public void openUPDATE()
    {
        try
        {
            CappTreeObject obj = this.getSelectedObject();
            if (obj != null && obj.getType() == obj.BUSINESSOBJECT)
            {
                mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                summaryObj = (TotalSchemaItemIfc) obj.getObject();
                if (summaryObj == null)
                {
                    return;
                }
                summaryObj = (TotalSchemaItemIfc) CappTreeHelper.refreshInfo(
                        summaryObj.getBsoID());
                summaryObj = lock(summaryObj);
                hashMap.put(summaryObj.getBsoID(), summaryObj);
                editControl = new SummaryEditController("UPDATE",
                        mainFrame, summaryObj);
                mainFrame.removePanel();
                mainFrame.setSumLabel(res.getString("edit_summary"));
                mainFrame.addSummaryPanel(editControl.getPanel());
                mainFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                mainFrame.show();
            }
        }
        catch (QMException e)
        {
            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
            JOptionPane.showMessageDialog(mainFrame, e.getClientMessage(),
                                          res.getString("information"),
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }


    /**
     * �༭����:ɾ��
     */
    public void openDELETE()
    {
        String str2 = QMMessage.getLocalizedMessage(RESOURCE,
                "warning", null);
        try
        {
            CappTreeObject objobj = this.getSelectedObject();
            int result = JOptionPane.showConfirmDialog(mainFrame,
                    "ɾ���������޷��ָ����Ƿ������", "��ʾ",
                    JOptionPane.YES_NO_OPTION);
            if (objobj != null && objobj.getType() == objobj.BUSINESSOBJECT)
            {
                switch (result)
                {
                    case JOptionPane.YES_OPTION:
                        summaryObj = (TotalSchemaItemIfc) objobj.getObject();
                        summaryObj = (TotalSchemaItemIfc) refreshNode(
                                summaryObj);
                        if (summaryObj == null)
                        {
                            return;
                        }
                        mainFrame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
                        String strID = summaryObj.getBsoID();
                        Class[] sumLock =
                                {
                                LockIfc.class};
                        Object[] summaryLock =
                                {
                                summaryObj};
                        try
                        {
                            Object[] object = (Object[]) ResourceHelper.
                                              requestServer(
                                    "ResourceService", "isLock", sumLock,
                                    summaryLock);
                            if (((Boolean) object[1]).booleanValue())
                            {
                                Object[] obj =
                                        {(String) object[0]};
                                String str1 = QMMessage.getLocalizedMessage(
                                        RESOURCE,
                                        "locker", obj);
                                JOptionPane.showMessageDialog(mainFrame, str1,
                                        str2,
                                        JOptionPane.WARNING_MESSAGE);
                                mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                                return;
                            }
                        }
                        catch (ResourceException e)
                        {
                            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                            if (ResourceHelper.VERBOSE)
                            {
                                System.out.println(
                                        "�쳣------------------------------" +
                                        e.getClientMessage());
                            }
                            JOptionPane.showMessageDialog(mainFrame,
                                    e.getClientMessage(), str2,
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        //���÷���ɾ������
                        try
                        {
                            ReportTreeObject obj = new ReportTreeObject((
                                    TotalSchemaItemInfo)
                                    summaryObj);

                            Class[] theClass =
                                    {
                                    String.class};
                            Object[] myObj =
                                    {
                                    strID};
                            getServiceObject("TotalService",
                                             "deleteTotalBySchema", theClass,
                                             myObj);
                            summaryTree.removeResource(obj);
                            mainFrame.removePanel(); //�Ƴ����
                        }
                        catch (QMRemoteException e)
                        {
                            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(this.mainFrame,
                                    e.getClientMessage());
                        }

                        //ɾ���ڵ㣬��ѡ�нڵ㣬ѡ�в˵���Ϊ������
                        mainFrame.setState(false);
                        mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                        break;
                    case JOptionPane.NO_OPTION:
                        break;
                }
            }
        }
        catch (QMException e)
        {
            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
            JOptionPane.showMessageDialog(mainFrame, e.getClientMessage(),
                                          res.getString("information"),
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * ��û���Լ��ģ��
     * @return TotalSchemaItemIfc
     * 2006.08.31  �촺Ӣע��
     */
    public TotalSchemaItemIfc getTotalSchema()
    {
        return this.summaryObj;
    }
    /**
     * ������Լ��ģ�ͼ���
     * @param summaryObj TotalSchemaItemIfc
     * @return TotalSchemaItemIfc
     * 2006.08.31  �촺Ӣע��
     */
    public TotalSchemaItemIfc lock(TotalSchemaItemIfc summaryObj)
    {
        Class[] eqLock =
                {
                LockIfc.class};
        Object[] equipLock =
                {
                summaryObj};
        try
        {
            summaryObj = (TotalSchemaItemIfc) ResourceHelper.requestServer(
                    "ResourceService", "lock", eqLock, equipLock);
        }
        catch (ResourceException e)
        {
            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
            if (ResourceHelper.VERBOSE)
            {
                System.out.println(
                        "�쳣------------------------------" +
                        e.getClientMessage());
            }
            JOptionPane.showMessageDialog(mainFrame,
                                          e.getClientMessage());
            return null;
        }
        return summaryObj;
    }


    /**
     * �༭����:������
     * ����(2)2006.08.17 �촺Ӣ�޸�  �޸�ԭ��:�����ڿͻ����ж��Ƿ����
     * ����(4)2006.10.20  �촺Ӣ�޸�  �޸�ԭ�򣺽������֮��Ҫ������hashMap�еļ����������
     */
    public void openRENAME()
    {
        String warning = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
        summaryObj = (TotalSchemaItemIfc) summaryTree.getSelectedObject().
                     getObject();
        try
        {
            summaryObj = (TotalSchemaItemIfc) CappTreeHelper.refreshInfo(
                    summaryObj.
                    getBsoID());
        }
        catch (QMRemoteException ex)
        {
            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
            if (ResourceHelper.VERBOSE)
            {
                System.out.println(
                        "�쳣------------------------------" +
                        ex.getClientMessage());
            }
            JOptionPane.showMessageDialog(mainFrame,
                                          ex.getClientMessage());
            return;
        }
        //����(3) 2006.10.18 �촺Ӣ�޸�  �޸�ԭ��:������ʱ��Ҫ����
        Class[] summaryClass =
                {
                LockIfc.class};
        Object[] summaryLock =
                {
                summaryObj};
        try
        {
            summaryObj = (TotalSchemaItemIfc) ResourceHelper.requestServer(
                    "ResourceService", "lock", summaryClass, summaryLock);
            hashMap.put(summaryObj.getBsoID(), summaryObj);
        }
        catch (ResourceException e)
        {
            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
            JOptionPane.showMessageDialog(mainFrame, e.getClientMessage(),
                                          warning,
                                          JOptionPane.WARNING_MESSAGE);
            return;
        }

        //����(2)2006.08.17 �촺Ӣ�޸�  �޸�ԭ��:����Ҫ�ڿͻ����ж��Ƿ����
        /*Class[] sumLock =
                {
                LockIfc.class};
        Object[] summaryLock =
                {
                summaryObj};
        try
        {
            Object[] object = (Object[]) ResourceHelper.
                              requestServer(
                    "ResourceService", "isLock", sumLock,
                    summaryLock);
            if (((Boolean) object[1]).booleanValue())
            {
                Object[] obj1 =
                        {(String) object[0]};
                String str1 = QMMessage.getLocalizedMessage(
                        RESOURCE,
                        "locker", obj1);
                JOptionPane.showMessageDialog(mainFrame, str1,
                                              str2,
                                              JOptionPane.WARNING_MESSAGE);
                mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
                return;
            }
        }
        catch (ResourceException e)
        {
            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
            if (ResourceHelper.VERBOSE)
            {
                System.out.println(
                        "�쳣------------------------------" +
                        e.getClientMessage());
            }
            JOptionPane.showMessageDialog(mainFrame,
                                          e.getClientMessage(), str2,
                                          JOptionPane.WARNING_MESSAGE);
            return;
        }*/

        //if (!lock(summaryObj))
        //{
        //return;
        //}
        mainFrame.removePanel();
        renameDialog = new SummaryRenameDialog(this);
        //����(4)2006.10.20  �촺Ӣ�޸�  �޸�ԭ�򣺽������֮��Ҫ������hashMap�еļ����������
        hashMap.remove(summaryObj.getBsoID());
        renameDialog.setVisible(true);
    }


    /**
     * ������ת����
     * @return boolean
     */
    public boolean preprocess()
    {
        if (editControl != null && editControl.getState().equals("UPDATE") &&
            mainFrame.isVisiable())
        {
            return editControl.preUpdate();
        }
        if (editControl != null && editControl.getState().equals("RESUM") &&
            mainFrame.isVisiable())
        {
            return editControl.preResum();
        }
        if (editControl != null && editControl.getState().equals("SAVEAS") &&
            mainFrame.isVisiable())
        {
            return editControl.preSaveAs();
        }
        return true;
    }


    /**
     * ���ù淶
     */
    protected void openConfigSpec()
    {
        new ConfigSpecController(configSpecItem, this);
    }


    /**
     * �༭����:����������
     * ����(3)2006.08.17 �촺Ӣ�޸�  �޸�ԭ��:���������õ��������
     */
    protected void openRENAMESAVE()
    {
        totalInfo = new TotalInfo();
        boolean flag = false;
        try
        {
            summaryObj = (TotalSchemaItemIfc) CappTreeHelper.refreshInfo(
                    summaryObj.
                    getBsoID());
        }
        catch (QMRemoteException ex)
        {
            mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
            if (ResourceHelper.VERBOSE)
            {
                System.out.println(
                        "�쳣------------------------------" +
                        ex.getClientMessage());
            }
            JOptionPane.showMessageDialog(mainFrame,
                                          ex.getClientMessage());
            return;
        }
        TotalSchemaItemIfc schema = ((TotalSchemaItemInfo) summaryObj).
                                    duplicated();
        schema.setBsoID(summaryObj.getBsoID());
        schema.setCreateTime(summaryObj.getCreateTime());
        schema.setModifyTime(summaryObj.getModifyTime());
        try
        {
            schema.setLocker(summaryObj.getLocker());
        }
        catch (LockException ex1)
        {
            ex1.printStackTrace();
        }
        schema.setNote(summaryObj.getNote());
        String num = schema.getTecTotalNumber();
        String name1 = schema.getTecTotalNumber();
        String num1 = renameDialog.getSumNum();
        String name = renameDialog.getSumName();
        if (!num.equals(num1))
        {
            flag = true;
        }
        schema.setTecTotalNumber(num1);
        schema.setTecTotalName(name);
        Class[] theclass =
                {
                TotalSchemaItemIfc.class, Boolean.TYPE};
        Object[] Obj =
                {
                schema, new Boolean(flag)};
        try
        {
            summaryObj = (TotalSchemaItemIfc) getServiceObject("TotalService",
                    "rename", theclass, Obj);
        }
        catch (QMRemoteException ex)
        {
            JOptionPane.showMessageDialog(mainFrame, ex.getClientMessage(),
                                          res.getString("warning"),
                                          JOptionPane.WARNING_MESSAGE);
            return;
        }
        summaryObj.setTecTotalName(name);
        totalInfo.setSchema(summaryObj);
        //����(3)2006.08.17 �촺Ӣ�޸�  �޸�ԭ��:���������õ��������
        /*try
        {
            totalInfo.setResult(summaryObj.getResult());
            Class[] theClass =
                    {
                    TotalInfo.class};
            Object[] myObj =
                    {
                    totalInfo};
            totalInfo = (TotalInfo) getServiceObject("TotalService",
                    "saveTotal",
                    theClass, myObj);
        }
        catch (QMException e)
        {
            JOptionPane.showMessageDialog(null, e.getClientMessage(),
                                          res.getString("warning"),
                                          JOptionPane.WARNING_MESSAGE);
            return;
        }*/
        if (totalInfo != null)
        {
            //���������
            this.updateTreeCode();
            //�����Ժ󣬹رնԻ���
            renameDialog.dispose();
        }
    }


    /**
     * ���ͻ��ܽ������Ի���ʵ��
     * @param resultSaveDialog SummaryResultSaveDialog
     */
    public void setDialog(SummaryResultSaveDialog resultSaveDialog)
    {
        this.resultSaveDialog = resultSaveDialog;
    }


    /**
     * ���ݻ�����Ϣ
     * @param totalInfo TotalInfo
     */
    public void setTotalInfo(TotalInfo totalInfo)
    {
        this.totalInfo = totalInfo;
    }


    /**
     * ���ܽ������
     * return true ����ɹ�
     * return false ����ʧ��
     */
    protected boolean resultSave()
    {
        totalInfo = new TotalInfo();
        if (resultSaveDialog.madeSum() == null)
        {
            progress.stopProcess();
            return false;
        }
        TotalSchemaItemIfc info = resultSaveDialog.madeSum();
        TotalResultIfc result = resultSaveDialog.getResult();
        totalInfo.setResult(result);
        info.setResult(result);
        totalInfo.setSchema(info);
        //���÷���
        try
        {
            Class[] theClass =
                    {
                    TotalInfo.class};
            Object[] myObj =
                    {
                    totalInfo};
            //����TotalService�����saveTotal����
            totalInfo = (TotalInfo) getServiceObject("TotalService",
                    "saveTotal",
                    theClass, myObj);
        }
        catch (QMRemoteException e)
        {
            progress.stopProcess();
            // resultSaveDialog.setVisible(false);
            JOptionPane.showMessageDialog(this.resultSaveDialog,
                                          e.getClientMessage(),
                                          res.getString("warning"),
                                          JOptionPane.WARNING_MESSAGE);
            // resultSaveDialog.setVisible(true);
            return false;

        }
        catch (Exception e)
        {
            progress.stopProcess();
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * �������ϼз���
     * @param entry FolderEntryIfc
     * @param location String
     * @throws QMRemoteException
     * @return FolderEntryIfc
     */
    public FolderEntryIfc assignFolder(FolderEntryIfc entry, String location)
            throws
            QMRemoteException
    {
        try
        {
            Class[] theClass =
                    {
                    FolderEntryIfc.class, String.class};
            Object[] theObj =
                    {
                    entry, location};
            return (FolderEntryIfc)this.getServiceObject("FolderService",
                    "assignFolder", theClass, theObj);
        }
        catch (QMRemoteException e)
        {
            throw new QMRemoteException(e);
        }
    }


    /**
     ** �����ϼ���Ӧ�Ľ��
     ** ��������Ӧ����
     */
    protected void addTreeCode()
    {
        TotalSchemaItemInfo totalItem = (TotalSchemaItemInfo) totalInfo.
                                        getSchema();
        ReportTreeObject obj = new ReportTreeObject(totalItem);
        summaryTree.addResource(obj);
        summaryTree.setNodeSelected(obj);
    }


    /**
     * �����ϸ�����Ӧ�Ľ��
     */
    protected void updateTreeCode()
    {
        TotalSchemaItemInfo totalItem = (TotalSchemaItemInfo) totalInfo.
                                        getSchema();
        ReportTreeObject obj = new ReportTreeObject(totalItem);
        summaryTree.updateResource(obj);
    }


    /**
     * �ӻ���Լ����Ϣ
     * @param info TotalSchemaItemInfo
     */
    public void addTotalToTree(TotalSchemaItemInfo info)
    {
        mainFrame.addTotalToTree(info);
    }


    /**
     * �õ�������
     * @return SummaryMainJFrame
     */
    public SummaryMainJFrame getMainFrame()
    {
        return mainFrame;
    }


    /**
     * �����������״̬
     * @return boolean
     */
    public boolean getViewState()
    {
        return flag;
    }


    /**
     * ����״̬����ֵ
     * @param s String
     */
    public void setStatus(String s)
    {
        mainFrame.setSumLabel(s);
    }


    /**
     * ��ʼ����Ʒ�ṹ�����������û���ǰ��ɸѡ����
     * ����û���ǰ��ɸѡ����Ϊ��������Ĭ��ֵ����׼
     * ��ͼΪ�գ���������Ϊ�գ������������ϼ�
     */
    private void configInit()
    {
        try
        {
            //��ȡ�㲿�����ù淶
            PartConfigSpecInfo configSpecInfo = (PartConfigSpecInfo) PartHelper.
                                                getConfigSpec();
            ViewObjectIfc vo = null;
            //���û�����ù淶������Ĭ�ϵġ���׼�����ù淶��
            if (configSpecInfo == null)
            {
                Vector dd = new Vector();
                ServiceRequestInfo info1 = new ServiceRequestInfo();
                info1.setServiceName("ViewService");
                info1.setMethodName("getAllViewInfos");
                try
                {
                    dd = (Vector) RequestServerFactory.getRequestServer().
                         request(info1);
                }
                catch (QMRemoteException e)
                {
                    JOptionPane.showMessageDialog(mainFrame, e.getClientMessage());
                }
                if(dd != null)
                {
                    for (int i = 0, j = dd.size(); i < j; i++)
                    {
                        if (((ViewObjectIfc) dd.elementAt(i)).getViewName().
                            equals(
                                "������ͼ"))
                        {
                            vo = (ViewObjectIfc) dd.elementAt(i);
                        }
                    }
                }
                configSpecInfo = new PartConfigSpecInfo();
                configSpecInfo.setStandardActive(true);
                //��׼������
                PartStandardConfigSpec partStandardConfigSpec = new
                        PartStandardConfigSpec();
                //���ñ�׼ʱ����ͼΪ��
                partStandardConfigSpec.setViewObjectIfc(vo);
                configSpecInfo.setStandard(partStandardConfigSpec);
                //���÷������úõ�ɸѡ�������浽���ݿ���
                RequestServer server = RequestServerFactory.getRequestServer();
                ServiceRequestInfo info = new ServiceRequestInfo();
                info.setServiceName("ExtendedPartService");
                info.setMethodName("savePartConfigSpec");
                Class[] paramClass =
                        {
                        PartConfigSpecIfc.class};
                info.setParaClasses(paramClass);
                Object[] paramValue =
                        {
                        configSpecInfo};
                info.setParaValues(paramValue);
                try
                {
                    configSpecInfo = (PartConfigSpecInfo) server.request(info);
                }
                catch (QMRemoteException ex)
                {
                    ex.printStackTrace();
                    return;
                }
                ConfigSpecItem config = new ConfigSpecItem(configSpecInfo);
                this.configSpecItem = config;
            }
            else
            {
                this.configSpecItem = new ConfigSpecItem(configSpecInfo);
            }
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
        }
    }

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
            JOptionPane.showMessageDialog(mainFrame, ex.getClientMessage(),
                                          s1,
                                          JOptionPane.WARNING_MESSAGE);
        }

    }


    /**
     * ˢ�½ڵ㣬ȷ�����ϵĽڵ�Ϊ���½ڵ�
     * @param resource BaseValueIfc
     * @return BaseValueIfc
     */
    public BaseValueIfc refreshNode(BaseValueIfc node)
    {
        String warning = QMMessage.getLocalizedMessage(RESOURCE, "warning", null);
        if (node == null)
        {
            return null;
        }
        Class[] aa =
                {
                String.class};
        Object[] oo =
                {
                node.getBsoID()};
        try
        {
            node = (BaseValueIfc) ResourceHelper.requestServer(
                    "PersistService", "refreshInfo", aa, oo);
            return node;
        }
        catch (ResourceException ex)
        {
            //setCursor(Cursor.DEFAULT_CURSOR);
            JOptionPane.showMessageDialog(mainFrame, ex.getClientMessage(),
                                          warning,
                                          JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }


}
