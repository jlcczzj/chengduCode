/** ���ɳ���ProcedureUsageEquipmentJPanel.java	1.1  2003/08/28
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 *   CR1 ��������2009/04/21   ԭ��:���������������Դ���ܽ��ж�ѡɾ��.
 *                            ����:����Դ�б��޸�Ϊ����ѡ���������,�����޸���ɾ����ť��
 *                                 �߼�.
 *                            ��ע:�����¼���"CRSS-009"
 * 
 * CCBegin by liunan 2011-08-25 �򲹶�PO35
 * CR2 ���� 2011/06/28 �μ�TD2408
 * CCEnd by liunan 2011-08-25
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.resource.view.ResourceDialog;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.resource.exception.ResourceException;
import com.faw_qm.resource.support.client.model.CEquipment;
import com.faw_qm.resource.support.client.model.CTool;
import com.faw_qm.resource.support.model.QMEquipmentInfo;
import com.faw_qm.resource.support.model.QMToolInfo;


/**
 * <p>Title:����ʹ���豸�Ĺ������</p>
 * <p>Description: ά���������豸��Դ </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 * @version 1.0
 * ���⣨1��20080704 �촺Ӣ�޸� �޸�ԭ��Ϊ�����豸����������Ԥ��������
 * ���⣨2��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
 * ���������������������ư�ť  begin
 * SS1 ���������ӹ������ۼӵ����򡱲��Ҳ��ڹ�������ʾ leixiao 2013-10-14
 * SS2 �ɶ�����������Դ���ӹ�����Դ���� guoxiaoliang 2016-8-2
 */

public class ProcedureUsageEquipmentJPanel extends ParentJPanel
{
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**
     * ����bean
     */
    private CappAssociationsPanel cappAssociationsPanel = new
            CappAssociationsPanel();


    /**
     * ����ģʽ
     */
    private String mode = "";


    /**
     * ������豸���
     */
    private String number = "";


    /**
     * �б���ѡ�е���
     */
    int row = -1;


    /**
     * �б���ѡ�е���
     */
    int col = -1;


    /**�������Ƿ���Ա���*/
    private boolean canSave = true;
    
    /**����ʹ�ù�װ�������*/
    private ProcedureUsageToolJPanel procedureUsageToolJPanel;
    
    //���⣨1��20080704 xucy add �޸�ԭ��Ϊ�����豸��������Ԥ��������
    //��������
    private String stepType;
    
     //CCBegin SS2
    private boolean isProcedure = false;
    
    /**
     * ���췽��
     */
    public ProcedureUsageEquipmentJPanel(boolean isProcedure)
    {
      this.isProcedure = isProcedure;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //CCEnd SS2
    
    //CCBegin SS2
    /**
     * ���췽��
     */
    public ProcedureUsageEquipmentJPanel(String stepType,boolean isPro)
    {
    	
    	this.isProcedure=isPro;
    	
    	this.stepType = stepType;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    //CCEnd SS2
    
    /**
     * ���췽��
     */
    public ProcedureUsageEquipmentJPanel()
    {
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    
    


    //���⣨1��20080704 xucy add �޸�ԭ��Ϊ�����豸��������Ԥ��������
    /**
     * ���췽��
     */
    public ProcedureUsageEquipmentJPanel(String stepType)
    {
    	this.stepType = stepType;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * �����ʼ��
     * @throws Exception
     * ���⣨1��20080704 xucy add  begin
     */
    void jbInit()
            throws Exception
    {
        setLayout(gridBagLayout1);
        // ���⣨2��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
        // ���������������������ư�ť  begin
        cappAssociationsPanel.getMultiList().setAllowSorting(false);
        cappAssociationsPanel.setIsUpDown(true, 4, 5);
        //���⣨2��end
        cappAssociationsPanel.addActionListener(new java.awt.event.
                                                ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cappAssociationsPanel_actionPerformed(e);
            }
        });
       // cappAssociationsPanel.setMutliSelectedModel(false);   //Begin CR1
        cappAssociationsPanel.setMutliSelectedModel(true);     //End CR1
        
        add(cappAssociationsPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 0), 0, 0));

        //���ý�ɫ��
        cappAssociationsPanel.setRole("procedure");
        //���û����Add��ťʱ���������������ÿ�ѡ�����б�,ȷ��Ҫѡ��Ķ�������ͣ�����ΪDoc��
        //cappAssociationsPanel.setChooserOptions("2");
        //�����б�������
        String args1[] = new String[4];
        args1[0] = "�豸���";
        args1[1] = "�豸����";
        args1[2] = "�豸�ͺ�";
        args1[3] = "ƽ��ͼ��";
        cappAssociationsPanel.setLabels(args1);
        //���ù�������
        cappAssociationsPanel.setLinkClassName(
                "com.faw_qm.capp.model.QMProcedureQMEquipmentLinkInfo");

        String as1[] = new String[4];
        as1[0] = "eqNum";
        as1[1] = "eqName";
        as1[2] = "eqModel";
        as1[3] = "planeNum";
        //���ý���ʾ�ڶ����б��й�����ҵ���������Լ�
        cappAssociationsPanel.setOtherSideAttributes(as1);
        //���ù���ҵ���������
        try
        {
            cappAssociationsPanel.setOtherSideClassName(
                    "com.faw_qm.resource.support.model.QMEquipmentInfo");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        //�����Ƿ�ֻ������Ĺ���
        cappAssociationsPanel.setSaveUpdatesOnly(true);
        //��AssociationsPanel�·���AttributeForm����ɾ��
        cappAssociationsPanel.removeAttributeForm();
        //���⣨1��20080704 xucy add  begin
        //���ݹ����������Դ�ļ��õ��ַ���
        String allProperties = RemoteProperty.getProperty(
                "com.faw_qm.cappclients.equipment.view" + stepType);
      
        
        if (allProperties == null || allProperties.trim().equals("null") || allProperties.equals(""))
	    {
	        String args3[] = new String[1];
	        args3[0] = "usageCount";
	        //���ù������ԣ�����Ϊ��ʹ����������
	        cappAssociationsPanel.setMultiListLinkAttributes(args3);
	        int[] editCols = {1,2,3};
	        cappAssociationsPanel.setColsEnabled(editCols, false);
	    }
	    else
	    {
		    StringTokenizer linkToken = new StringTokenizer(allProperties, ",");
		    int linkSize = linkToken.countTokens();
		    String args4[] = new String[linkSize+1];
		    args4[0] = "usageCount";
		    for(int i = 1; i < linkSize+1; i++)
		    {
		        args4[i] = linkToken.nextToken();
		    }
	        
		    //���ù������ԣ�����Ϊ��ʹ����������
		    cappAssociationsPanel.setMultiListLinkAttributes(args4);
		    //�õ��ɱ༭������
	        int[] is = {1,2,3};
	        
//	        for (int i = 2; i < editL+2; i++)
//	        {
//	            editCols[i] = Integer.parseInt(editToken.nextToken())+5;
//	        }
	        cappAssociationsPanel.setColsEnabled(is, false);
	    }
        //���⣨1��20080704 xucy add  end
        
        //���õ�4�п��Ա༭
        //cappAssociationsPanel.setColsEnabled(is, true);
        //�������Ӱ�ť��ʾ�����������ӿհ���
        
        //CCBegin SS2
//        cappAssociationsPanel.setIsInsert(true, 3);
        
        if(isProcedure)
            cappAssociationsPanel.setIsInsert(true, 3);
            else{
              cappAssociationsPanel.setIsInsert(true, 3);
              cappAssociationsPanel.setIsGetResouceByProcedure(true,7);
            }
        //CCEnd SS2
        
        cappAssociationsPanel.setObject(null);
        cappAssociationsPanel.setBrowseButtonMnemonic('F');
        cappAssociationsPanel.setRemoveButtonMnemonic('D');
        cappAssociationsPanel.setViewButtonMnemonic('V');
        cappAssociationsPanel.setInsertButtonMnemonic('I');
        // ���⣨2��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
        // ���������������������ư�ť  
        cappAssociationsPanel.setUpButtonMnemonic('U');
        cappAssociationsPanel.setDownButtonMnemonic('N');
        //20080707 xucy ע��
        //localize();

    }


    /**
     * ������Ϣ���ػ�
     */
    protected void localize()
    {
        //�����б�������
        String args1[] = new String[4];
        args1[0] = QMMessage.getLocalizedMessage(RESOURCE, "equipmentNumber", null);
        args1[1] = QMMessage.getLocalizedMessage(RESOURCE, "equipmentName", null);
        args1[2] = QMMessage.getLocalizedMessage(RESOURCE, "equipmentType", null);
        args1[3] = QMMessage.getLocalizedMessage(RESOURCE, "planeNumber", null);
        try
        {
            cappAssociationsPanel.setLabels(args1);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * ���ý�����ʾģʽ
     * @param m  ������ʾģʽ(�༭ģʽ���鿴ģʽ)  EDIT_MODE or VIEW_MODE
     */
    public void setMode(String m)
    {
        cappAssociationsPanel.setMode(m);
        mode = m;
    }


    /**
     * ��ý���ģʽ
     * @return ������ʾģʽ(�༭ģʽ���鿴ģʽ)  EDIT_MODE or VIEW_MODE
     */
    public String getMode()
    {
        return mode;
    }


    /**
     * ���õ�ǰҵ�����
     * @param info ���տ�ֵ����
     */
    public void setProcedure(QMProcedureInfo info)
    {
        if (info.getBsoID() != null)
        {
            String classPath = RemoteProperty.getProperty("instance" +
                    info.getTechnicsType().getCodeContent());
            try
            {
                cappAssociationsPanel.setObjectClassName(classPath);
            }
            catch (ClassNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
        cappAssociationsPanel.setObject(info);

    }
    
    //CCBegin SS1
    public void setIsProcedure(boolean flag){
    	cappAssociationsPanel.setIsProcedure(flag);
    }
//CCEnd SS1


    /**
     * ������й���
     * @return ���й��������ļ���
     */
    public Vector getAllLinks()
            throws Exception
    {
        if (!canSave)
        {
            throw new Exception();
        }
        if (!cappAssociationsPanel.check())
        {
            throw new Exception("���������������");
        }
        else
        {
            return cappAssociationsPanel.getLinks();
        }
    }


    /**
     * ���Ҫ��ɾ���Ĺ���
     * @return ���������ļ���
     */
    public Vector getDeletedLinks()
    {
        Vector delVector = new Vector();
        for (Enumeration e = cappAssociationsPanel.getRemoveLinks();
                             e.hasMoreElements(); )
        {
            delVector.addElement(e.nextElement());
        }
        return delVector;
    }


    /**
     * ��ָ�����豸��Դ���ӵ��б���
     * @param equipment �豸ֵ����
     */
    public void addEquipmentToTable(QMEquipmentInfo equipment)
    {
        try
        {
            cappAssociationsPanel.addSelectedObject(equipment);
        }
        catch (InvocationTargetException ex)
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (InstantiationException ex)
        {
            ex.printStackTrace();
        }
        catch (NoSuchMethodException ex)
        {
            ex.printStackTrace();
        }
        catch (IllegalAccessException ex)
        {
            ex.printStackTrace();
        }
    }


    /**
     * ִ���ֹ�¼���豸����
     * @param e ActionEvent
     */
    void cappAssociationsPanel_actionPerformed(ActionEvent e)
    {
        Object obj = e.getSource();
        String actionCommand = e.getActionCommand();
        int t = actionCommand.indexOf(";");
        if (t != -1)
        {
            //�õ���
            String rowString = actionCommand.substring(0, t);
            int t1 = rowString.indexOf(":");
            row = Integer.parseInt(rowString.substring(t1 + 1, rowString.length()));
            //�õ���
            String colString = actionCommand.substring(t + 1,
                    actionCommand.length());
            int t2 = colString.indexOf(":");
            col = Integer.parseInt(colString.substring(t2 + 1, colString.length()));
        }
        if (col == 0)
        {
            if (obj instanceof JTextField)
            {
                cappAssociationsPanel.setEnabled(false);
                JTextField textField = (JTextField) obj;
                if (textField.getText().trim() != null &&
                    !textField.getText().trim().equals(""))
                {
                    handworkAddEquipNumber(textField);
                }
                else
                {
                    cappAssociationsPanel.setEnabled(true);
                    //CCBegin by liunan 2011-08-25 �򲹶�PO35
                      //CR2
                    cappAssociationsPanel.undoCell();
                    //CCEnd by liunan 2011-08-25
                }
            }
        }

    }


    /**
     * �ֹ������豸��ŵ�ָ�����ı�����
     * @param textField ��д�豸��ŵ��ı���
     */
    private void handworkAddEquipNumber(JTextField textField)
    {
        number = textField.getText().trim();
        //���������豸�����ϵͳ�д��ڣ���ϵͳ�Ѹ��豸�������б���
        QMEquipmentInfo eq = checkEquipmentIsExist(number);
        if (eq != null)
        {
            try
            {
                cappAssociationsPanel.addObjectToRow(eq, row);
                cappAssociationsPanel.setEnabled(true);
                //������豸�����Ĺ�װ���빤װ
                Vector tools=null;
                try
                {
                   CEquipment  cequip = new CEquipment(eq);
                    //�õ�����豸�����Ĺ�װ
                    tools = cequip.getTools();
                }
                catch (ResourceException ex)
                {
                    if(verbose)
                    ex.printStackTrace();
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            "information", null);

                    JOptionPane.showMessageDialog(getParentJFrame(),
                            ex.getClientMessage(),
                            title,
                            JOptionPane.INFORMATION_MESSAGE);

                }
                if (tools != null && tools.size() != 0)
                {

                    //������װ
                    for (int j = 0; j < tools.size(); j++)
                    {
                        //��������Ǳ�Ҫ��
                        if (((Boolean) ((Object[]) tools.elementAt(j))[1]).
                            booleanValue() == true)
                        {
                           //�ѹ�װ�����б���
                                procedureUsageToolJPanel.addToolToTable((
                                        QMToolInfo) (
                                        (Object[]) tools.elementAt(j))[0]
                                        );


                        }

                    }
                }

            //
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        else
        {
            CreateResource t = new CreateResource();
            new Thread(t).start();
        }

    }


    /**
     * ������Դ����ͨ��ָ�����豸��������ݿ��в����豸����
     * @param eqNumber ָ�����豸���
     * @return ���ҵ����豸����
     */
    private QMEquipmentInfo checkEquipmentIsExist(String eqNumber)
    {
        Class[] c =
                {String.class};
        Object[] obj =
                {eqNumber};
        try
        {
            return (QMEquipmentInfo) useServiceMethod(
                    "ResourceService", "findEquipmentByNumber", c, obj);
        }
        catch (QMRemoteException ex)
        {
            //ex.printStackTrace();
            return null;
        }
    }


    /**
     * ��ʾȷ�϶Ի���
     * @param s �ڶԻ�������ʾ����Ϣ
     * @return  ����û�ѡ���ˡ�ȷ������ť���򷵻�true;���򷵻�false
     */
    private boolean confirmAction(String s)
    {
        String title = QMMessage.getLocalizedMessage(RESOURCE, "information", null);
        return JOptionPane.showConfirmDialog(getParentJFrame(),
                                             s, title,
                                             JOptionPane.YES_NO_OPTION) ==
                JOptionPane.YES_OPTION;
    }


    class CreateResource extends Thread
    {
        public void run()
        {
            canSave = false;
            //���ϵͳ�в������б���������豸��ţ��򷵻ض�Ӧ����Ϣ��ѯ���Ƿ��½�
            //�豸������ǣ���ʾ�豸�½����棬���ݷ���Ȩ�޴��������豸������񣬷���ԭλ��.
            String s = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.IS_CREATE_EQUIPMENT, null);
            if (confirmAction(s))
            {
                cappAssociationsPanel.setEnabled(true);
                getParentJFrame().setCursor(Cursor.getPredefinedCursor(Cursor.
                        WAIT_CURSOR));
                //��ʾ�豸�½�����
                ResourceDialog d = new ResourceDialog(getParentJFrame(),
                        "equipment", "CREATE", number);

                d.setSize(650, 500);
                setViewLocation(d);
                d.setTitle("�½��豸");
                d.addQuitListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        cappAssociationsPanel.undoCell();
                    }

                });
                d.setVisible(true);
                d.setModal(true);
                getParentJFrame().setCursor(Cursor.getDefaultCursor());
                try
                {
                    Object obj = d.getObject();
                    if (obj != null)
                    {
                        cappAssociationsPanel.addObjectToRow((BaseValueIfc) obj,
                                row);
                    }
                    else
                    {
                        cappAssociationsPanel.undoCell();
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
            else
            {
                cappAssociationsPanel.setEnabled(true);
                cappAssociationsPanel.undoCell();
            }
            canSave = true;
        }
    }


    /**
     * ���ý������ʾλ��
     */
    private void setViewLocation(JDialog d)
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //modify by wangh on 20071211
//        Dimension frameSize = getSize();
//        if (frameSize.height > screenSize.height)
//        {
//            frameSize.height = screenSize.height;
//        }
//        if (frameSize.width > screenSize.width)
//        {
//            frameSize.width = screenSize.width;
//        }
        d.setLocation((screenSize.width - d.getWidth()) / 2,
                      (screenSize.height - d.getHeight()) / 2);
        //modify end

    }


    /**
     * ���ѡ�е������豸
     * @return �豸ֵ����ļ���
     */
    public Vector getSelectedObjects()
    {
        Vector v = new Vector();
        Object[] objs = cappAssociationsPanel.getSelectedObjects();
        if (objs != null && objs.length > 0)
        {
            for (int i = 0; i < objs.length; i++)
            {
                v.addElement(objs[i]);
            }
        }
        return v;
    }


    /**
     * ���
     */
    public void clear()
    {
        cappAssociationsPanel.clear();
    }


    /**
     *Ϊ�ⲿ�ṩ�ļ������������б��������Ӷ���ʱ�������¼�
     */
    public void addListener(ActionListener e)
    {
        cappAssociationsPanel.addActionListener(e);
    }


    /**
     * ���e�еõ����ǹ�װ������ù�װ��Ҫ�������豸�����豸�б����˷������ⲿ����
     */
    public void addRelationEquipments(ActionEvent e)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.ProcedureUsageToolJPanel.addRelationEquipments() begin...e=" +
                               e.getSource());
        }
        try
        {
            if (e.getActionCommand().equals("MultiListAdd"))
            {
                if (e.getSource() instanceof QMToolInfo)
                { //��װģ��
                    CTool ctool;
                    //����빤װ�������豸
                    Vector equipments = null;
                    try
                    {
                        ctool = new CTool((QMToolInfo) e.getSource());
                        //�õ���ù�װ�������豸
                        equipments = ctool.getEquips();
                    }
                    catch (ResourceException ex)
                    {
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                "information", null);

                        JOptionPane.showMessageDialog(getParentJFrame(),
                                ex.getClientMessage(),
                                title,
                                JOptionPane.INFORMATION_MESSAGE);

                    }
                    if (equipments != null && equipments.size() != 0)
                    {
                        //������װ
                        for (int j = 0; j < equipments.size(); j++)
                        {
                            //��������Ǳ�Ҫ��
                            if (((Boolean) ((Object[]) equipments.elementAt(j))[
                                 1]).booleanValue() == true)
                            {
                                try
                                { //�ѹ�װ�����б���
                                    cappAssociationsPanel.addSelectedObject((
                                            BaseValueInfo) (
                                            (Object[]) equipments.elementAt(j))[
                                            0]);
                                }
                                catch (InvocationTargetException ex)
                                {throw ex;
                                }
                                catch (ClassNotFoundException ex)
                                {throw ex;
                                }
                                catch (InstantiationException ex)
                                {throw ex;
                                }
                                catch (NoSuchMethodException ex)
                                {throw ex;
                                }
                                catch (IllegalAccessException ex)
                                {throw ex;
                                }
                            }

                        }
                    }
                }
            }
        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.view.ProcedureUsageToolJPanel.addRelationTools() end");
        }
    }

    /**
     * ���ù�װ�������
     * @param toolPanel ProcedureUsageToolJPanel ��װ�������
     */
    public void setToolPanel(ProcedureUsageToolJPanel toolPanel)
    {
        procedureUsageToolJPanel=toolPanel;
    }
    
        //CCBegin SS2
    public void setProcedurePace(QMProcedureIfc info){
    	   cappAssociationsPanel.setProcedurePace(info);
    	  }
    
    //CCEnd SS2

}