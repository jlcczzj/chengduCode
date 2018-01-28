/**
 * CR1  2009/07/06 Ѧ��  TD2522
 * CR2 2012/04/12 ������ �μ�TD2512
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
 * <p>Title:�༭���ܿ����� </p>
 * <p>Description: �Բ鿴�����µȲ�������</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company:һ������</p>
 * @author ����
 * @version 1.0
 * ����(1)2006.07.10�촺Ӣ�޸�  �޸�ԭ��:���ܸ���ʱ,������������ʾ��,�ر���ʾ������쳣.
 * ����(2)2006.08.23 �촺Ӣ�޸� �޸�ԭ��:���»��ܱ���ʱ,û�б���ı������ϼ�
 * ����(3) 2006.08.31 �촺Ӣ�޸�  �޸�ԭ��:���±���֮��Ҫɾ������hashMap��ļ�������
 * ����(4) 2006.09.15 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
 * ����(5) 2006.09.19 �촺Ӣ�޸�  �޸�ԭ��:����ʱ���˳���ťҪɾ������hashMap��ļ�������
 * ����(6)2006.11.15 �촺Ӣ�޸�  �޸�ԭ��:������˳����Ϊ����
 * ��summaryObject����û�г־û�,�����Ļ������
 * ����(7)2006.11.16 �촺Ӣ�޸�  �޸�ԭ��:���Ϊʱ���벻���ڵ����ϼк󣬵�����水ť
 * ��ʾ���ϼ�·����Ч�����ȷ���󣬳����Լ���ִ�в�����ɹ���
 * �������ܵ�λ��ΪԴ�������ڵ����ϼС�
 * ���û����ȷ����Ӧ��ִֹ�в������û�����¼����ȷ�����ϼ�·����
 * ����(8)2007.01.30 �촺Ӣ�޸�  �޸�ԭ��:���»���֮��������ϼ�
 * ���⣨9��2007.05.24 �촺Ӣע��  ������µ�ʱ��û��Ҫ������ʾ��
 * SS1 2013-2-20   ������ ���Ӽ�¼ģ������
 */

public class SummaryEditController extends SummaryController
{
    private SummaryMainJFrame mainFrame;
    private EditPanel editPanel;
    private TotalSchemaItemIfc summaryObject;
    private TotalInfo totalInfo;
    private TotalResultIfc total = null;


    /**
     * ������
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
     * ����Լ��ģ�ͽ���
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
     * ����ʵ��
     * @param e ActionEvent
     * ����(4) 2006.09.15 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
     * ����(5) 2006.09.19 �촺Ӣ�޸�  �޸�ԭ��:����ʱ���˳���ťҪɾ������hashMap��ļ�������
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
                // ��ʾȷ�϶Ի���
                int reply = JOptionPane.showConfirmDialog(this.editPanel,
                        "��Ϣû�б����棬�Ƿ����",
                        res.getString("exitwindow"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (reply == JOptionPane.YES_OPTION)
                {
                    mainFrame.removePanel();
                    //����(6)2006.11.15 �촺Ӣ�޸�  �޸�ԭ��:������˳����Ϊ����
                    //��summaryObject����û�г־û�,�����Ļ������
                    if (summaryObject.getBsoID() != null)
                    {
                        this.unLock(summaryObject);
                    }
                    //����(5) 2006.09.19 �촺Ӣ�޸�  �޸�ԭ��:����ʱ���˳���ťҪɾ������hashMap��ļ�������
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
         * ������ťʵ��
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
                	if(summaryObject.getName().equals("�豸�嵥"))
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
                		if (ss.indexOf("�㲿����") != -1)
                		{
                			partnum = ss.substring(ss.indexOf("�㲿����") + 5);
                			//System.out.println("partnum========="+partnum);
                			//����㲿������
                			QMPartMasterIfc part = this.getPartIfc(partnum);
                			if (part != null)
                			{
                				partname = part.getPartName();
                				//System.out.println("partname========="+partname);
                				//����㲿�����ó��ͣ����ڳ�ѹ���乤����ϸ��
                				//  chexing = this.getFirstCarType(part.getBsoID());
                			}
                		}
                		if (ss.indexOf("����") != -1)
                		{
                			department = ss.substring(ss.indexOf("����") + 3);
                		  //System.out.println("department========="+department);
                		}
                	}
                	EquipmentQDController sbqd = new EquipmentQDController();
                	vector.remove(0); //ȥ��������
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
                  		if (ss.indexOf("�㲿����") != -1)
                  		{
                  			partnum = ss.substring(ss.indexOf("�㲿����") + 5);
                  			//System.out.println("partnum========="+partnum);
                  			//����㲿������
                  			QMPartMasterIfc part = this.getPartIfc(partnum);
                  			if (part != null)
                  			{
                  				partname = part.getPartName();
                  				//System.out.println("partname========="+partname);
                  				//����㲿�����ó��ͣ����ڳ�ѹ���乤����ϸ��
                  				//  chexing = this.getFirstCarType(part.getBsoID());
                  			}
                  		}
                  		if (ss.indexOf("��װ����") != -1)
                  		{
                  			toolType = ss.substring(ss.indexOf("��װ����") + 5);
                  			//System.out.println("toolType========="+toolType);
                  		}
                  		if (ss.indexOf("����") != -1)
                  		{
                  			department = ss.substring(ss.indexOf("����") + 3);
                  			//System.out.println("department========="+department);
                  		}
                  	}
                  	if (!filename.endsWith(".xls"))
                  	{
                  		filename += ".xls";
                  	}
                  	//System.out.println("filename========="+filename);
                  	if (summaryObject.getName().equals("����һ����"))
                  	{
                  		ToolYLBDController mxb = new ToolYLBDController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("ĥ��һ����"))
                  	{
                  		ToolYLBMController mxb = new ToolYLBMController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("���ܹ����嵥"))
                  	{
                  		ToolWNQDController mxb = new ToolWNQDController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("�о���ϸ��"))
                  	{
                  		ToolMXBController mxb = new ToolMXBController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("������������"))
                  	{
                  		ToolZBWNGJController mxb = new ToolZBWNGJController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("�������"))
                  	{
                  		ToolZBJUController mxb = new ToolZBJUController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("����ר������"))
                  	{
                  		ToolZBZYLZController mxb = new ToolZBZYLZController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("װ�乤��һ����"))
                  	{
                  		ToolZPGJYLBController mxb = new ToolZPGJYLBController();
                  		vector.remove(0); 
                  		mxb.printFile(editPanel, vector, department, filename ,partnum ,partname);
                  	}
                  	else if (summaryObject.getName().equals("�и���һ����"))
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
                //����(4) 2006.09.15 �촺Ӣ�޸�  �޸�ԭ��:�������е����ɹ�����ʾ
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
     * ���ر༭����״̬
     * @return String
     */
    public String getState()
    {
        return editPanel.getState();
    }


    /**
     * ���»��ܽ��֮ǰ����ʾ
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
     * ���»��ܻ��ܽ��֮ǰ����ʾ
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
     * ���Ϊ���ܽ��֮ǰ����ʾ
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
     * �˳�����Ŀ��Ʒ���
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
     * ���»��ܽ���ж���������
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
     * ����Լ��
     * @param info TotalSchemaItemIfc
     */
    public void setTotalSchema(TotalSchemaItemIfc info)
    {
        summaryObject = info;
    }


    /**
     * �õ��༭���
     * @return EditPanel
     */
    public EditPanel getPanel()
    {
        return editPanel;
    }


    /**
     * ���û���Լ���Ļ�����Ϣ
     * ����(7)2006.11.16 �촺Ӣ�޸�  �޸�ԭ��:���Ϊʱ���벻���ڵ����ϼк󣬵�����水ť
     * ��ʾ���ϼ�·����Ч�����ȷ���󣬳����Լ���ִ�в�����ɹ���
     * �������ܵ�λ��ΪԴ�������ڵ����ϼС�
     * ���û����ȷ����Ӧ��ִֹ�в������û�����¼����ȷ�����ϼ�·����
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
     * ���水ť����
     * ����(1)2006.07.10�촺Ӣ�޸�  �޸�ԭ��:���ܸ���ʱ,������������ʾ��,�ر���ʾ������쳣.
     * ����(2)2006.08.23 �촺Ӣ�޸� �޸�ԭ��:���»��ܱ���ʱ,û�б���ı������ϼ�
     * ����(3) 2006.08.31 �촺Ӣ�޸�  �޸�ԭ��:���±���֮��Ҫɾ������hashMap��ļ�������
     * ����(8)2007.01.30 �촺Ӣ�޸�  �޸�ԭ��:���»���֮��������ϼ�
     * ���⣨9��2007.05.24 �촺Ӣע��  ������µ�ʱ��û��Ҫ������ʾ��
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
            //2006.08.24 �촺Ӣ�޸�
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
            //Ѧ�� 20080226 ��� ���ԭ���޸Ļ��ܽ�������ϼ�·�������´˴β����޷�����
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
            //Ѧ�� ��ӽ���
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
            //���⣨9��2007.05.240�촺Ӣע��  ������µ�ʱ��û��Ҫ������ʾ��
//            int result = JOptionPane.showConfirmDialog(mainFrame,
//                    "�Ƿ�������»��ܽ��", "��ʾ",
//                    JOptionPane.YES_NO_OPTION);
            //switch (result)
            //{
//                case JOptionPane.YES_OPTION:
//                    editPanel.setTotalResult(total);
//                    mainFrame.setCursor(Cursor.DEFAULT_CURSOR);
//                    break;
//                    //����(1)2006.07.10�촺Ӣ�޸�  �޸�ԭ��:���ܸ���ʱ,����������
//                    //��ʾ��,�ر���ʾ������쳣.
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
                        //����TotalService�����saveTotal����
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
                    //����(3) 2006.08.31 �촺Ӣ�޸�  �޸�ԭ��:���±���֮��Ҫɾ������hashMap��ļ�������
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
            //����(7)2006.11.16 �촺Ӣ�޸�  �޸�ԭ��:���Ϊʱ���벻���ڵ����ϼк󣬵�����水ť
            //��ʾ���ϼ�·����Ч�����ȷ���󣬳����Լ���ִ�в�����ɹ���
            //�������ܵ�λ��ΪԴ�������ڵ����ϼС�
            //���û����ȷ����Ӧ��ִֹ�в������û�����¼����ȷ�����ϼ�·����
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
                //����TotalService�����saveTotal����
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
                //����TotalService�����saveTotalResult����
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
                //����(8)2007.01.30 �촺Ӣ�޸�  �޸�ԭ��:���»���֮��������ϼ�
                setTotalBasicInfo();
                totalInfo = new TotalInfo();
                totalInfo.setResult(resultSum);
                totalInfo.setSchema(summaryObject);
                //���÷���
                Class[] theClasses =
                        {
                        TotalInfo.class};
                Object[] myObjs =
                        {
                        totalInfo};
                //����TotalService�����saveTotal����
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
    //CCEnd SS1
}
