/** ���ɳ���ProcedureUsageMaterialJPanel.java	1.1  2003/08/28
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
 * SS1 �����¹��ܹ������豸��װ��������������Ƿ��ۼӵ��� leixiao 2013-9-23
 * SS2 ���������ӹ������ۼӵ����򡱲��Ҳ��ڹ�������ʾ leixiao 2013-10-14
 * SS3 �ɶ�����������Դ��ӹ�����Դ���� guoxiaoliang 2016-8-2
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
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.resource.support.model.QMMaterialInfo;


/**
 * <p>Title: ����ʹ�ò��Ϲ������</p>
 * <p>Description: ά�������ò�����Դ </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 * @version 1.0
 * ���⣨1��20080709 xucy add �޸�ԭ��Ϊ����װ�������Ԥ��������
 * ���⣨2��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
 * ��������������������ư�ť
 */

public class ProcedureUsageMaterialJPanel extends ParentJPanel
{
    /**
     * ����bean
     */
    private CappAssociationsPanel cappAssociationsPanel = new
            CappAssociationsPanel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**
     * ����ģʽ
     */
    private String mode = "";


    /**
     * �ֹ�����Ĳ��ϱ��
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

    //���⣨1��20080709 xucy add �޸�ԭ��Ϊ����װ�������Ԥ��������
    /**
     * ��������
     */
    private String stepType;
    
    //CCBegin SS3
    private boolean isProcedure = false;
    
    /**
     * ���췽��
     */
    public ProcedureUsageMaterialJPanel(boolean isProcedure)
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
    
    
     /**
     * ���췽��
     */
    public ProcedureUsageMaterialJPanel(String stepType,boolean isPro)
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
    //CCEnd SS3
    
    /**
     * ���췽��
     */
    public ProcedureUsageMaterialJPanel()
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


    //���⣨1��20080709 xucy add �޸�ԭ��Ϊ����װ�������Ԥ��������
    /**
     * ���췽��
     */
    public ProcedureUsageMaterialJPanel(String stepType)
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
     * ���⣨1��20080709 xucy add �޸�ԭ��Ϊ����װ�������Ԥ�������� 
     */
    void jbInit()
            throws Exception
    {
        setLayout(gridBagLayout1);
        // ���⣨2��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
        // ��������������������ư�ť  begin
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
       // cappAssociationsPanel.setMutliSelectedModel(false);    //Begin CR1
        cappAssociationsPanel.setMutliSelectedModel(true);      //End CR1
        add(cappAssociationsPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 0), 0, 0));
        //���ý�ɫ��
        cappAssociationsPanel.setRole("procedure");
        //���û����Add��ťʱ���������������ÿ�ѡ�����б�,ȷ��Ҫѡ��Ķ�������ͣ�����ΪDoc��
        //cappAssociationsPanel.setChooserOptions("2");
        //�����б������
//      CCBeginby leixiao 2009-6-24 ԭ�򣺹�����������������
        String args1[] = new String[5];
        args1[0] = "���ϱ��";
        args1[1] = "��������";
        args1[2] = "�����ƺ�";
        args1[3] = "���Ϲ��";
        args1[4] = "���ϱ�׼";
//      CCEndby leixiao 2009-6-24 ԭ�򣺹�����������������
        cappAssociationsPanel.setLabels(args1);

        //���ù�������
        cappAssociationsPanel.setLinkClassName(
                "com.faw_qm.capp.model.QMProcedureQMMaterialLinkInfo");
//      CCBeginby leixiao 2009-6-24 ԭ�򣺹�����������������
        String as1[] = new String[5];
        as1[0] = "materialNumber";
        as1[1] = "materialName";
        as1[2] = "materialState";
        as1[3] = "materialSpecial";
        as1[4] = "materialCrision";
//      CCEndby leixiao 2009-6-24 ԭ�򣺹�����������������
        //���ý���ʾ�ڶ����б��й�����ҵ���������Լ�
        cappAssociationsPanel.setOtherSideAttributes(as1);
        //���ù���ҵ���������
        try
        {
            cappAssociationsPanel.setOtherSideClassName(
                    "com.faw_qm.resource.support.model.QMMaterialInfo");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        //�����Ƿ�ֻ������Ĺ���
        cappAssociationsPanel.setSaveUpdatesOnly(true);
        //��AssociationsPanel�·���AttributeForm����ɾ��
        cappAssociationsPanel.removeAttributeForm();
        
        //���⣨1��20080709 xucy add �޸�ԭ��Ϊ����װ�������Ԥ�������� begin
        String allProperties = RemoteProperty.getProperty(
                "com.faw_qm.cappclients.material.view" + stepType);
        if (allProperties == null || allProperties.trim().equals("null") || allProperties.equals(""))
        {
            String args2[] = new String[2];
            args2[0] = "usageCount";
            args2[1] = "measureUnit";
            //���ù������ԣ�����Ϊ��ʹ��������������λ��
            cappAssociationsPanel.setMultiListLinkAttributes(args2);
            int[] editCols = {1};
	        cappAssociationsPanel.setColsEnabled(editCols, false);
        }
        else
        {
//        	StringTokenizer stringToken = new StringTokenizer(allProperties, ";");
//        	String linkProperties = stringToken.nextToken();
        	StringTokenizer linkToken = new StringTokenizer(allProperties, ",");
		    int linkSize = linkToken.countTokens();
			String args3[] = new String[linkSize+2];
			args3[0] = "usageCount";
			args3[1] = "measureUnit";
			for(int i = 2; i < linkSize+2; i++)
			{
			    args3[i] = linkToken.nextToken();
			}
		        
			//���ù������ԣ�����Ϊ��ʹ����������
			cappAssociationsPanel.setMultiListLinkAttributes(args3);
//			//�õ��ɱ༭������
//	        String editProperties = stringToken.nextToken();
//	        StringTokenizer editToken = new StringTokenizer(editProperties, ",");
//	        int editL = editToken.countTokens();
//	        int[] editCols = new int[editL+2];
//	        editCols[0] = 0;
//	        editCols[1] = 2;
//	        
//	        for (int i = 2; i < editL+2; i++)
//	        {
//	            editCols[i] = Integer.parseInt(editToken.nextToken())+4;
//	        }
			int[] is = {1};
	        cappAssociationsPanel.setColsEnabled(is, false);
        }
        //���⣨1��20080709 xucy add �޸�ԭ��Ϊ����װ�������Ԥ�������� end
//      CCBeginby leixiao 2009-6-24 ԭ�򣺹�����������������
        //CCBegin by liunan 2010-11-12 ����������������������Ƶ�Ρ����ԣ�Ҳ��Ҫ�ɱ༭
        //CCBegin SS1
         
//        
//        int[] is =
//               {0, 5, 6, 7};
//            cappAssociationsPanel.setColsEnabled(is, true);
//        }
//        else{
            int[] is =
                {0, 5, 6, 7, 8};
            cappAssociationsPanel.setColsEnabled(is, true);
//        }


        //CCEnd SS1
        //CCEnd by liunan 2010-11-12        
        //���õ�0,2,��,4,5�п��Ա༭
//      CCEndby leixiao 2009-6-24 ԭ�򣺹�����������������

        //������Ӱ�ť��ʾ����������ӿհ���
               //CCBegin SS3
//      cappAssociationsPanel.setIsInsert(true, 3);
      
        if(isProcedure)
            cappAssociationsPanel.setIsInsert(true, 3);
          else{
            cappAssociationsPanel.setIsInsert(true, 3);
            cappAssociationsPanel.setIsGetResouceByProcedure(true,7);
          }
      //CCEnd SS3
        cappAssociationsPanel.setObject(null);
        //���Ƿ�
        cappAssociationsPanel.setBrowseButtonMnemonic('F');
        cappAssociationsPanel.setRemoveButtonMnemonic('D');
        cappAssociationsPanel.setViewButtonMnemonic('V');
        cappAssociationsPanel.setInsertButtonMnemonic('I');
        // ���⣨2��20081219 �촺Ӣ�޸�  �޸�ԭ�򣺹���,�����С���װ��������ͼ���ȱ�ǩҳ���ж����װ����ͼʱ���û�������Ҫ�������ǵ�˳��
        // ��������������������ư�ť  
        cappAssociationsPanel.setUpButtonMnemonic('U');
        cappAssociationsPanel.setDownButtonMnemonic('N');
        //20080709 xucy ע��
        //localize();
    }


    /**
     * ������Ϣ���ػ�
     */
    protected void localize()
    {
//      CCBeginby leixiao 2009-6-24 ԭ�򣺹�����������������
        String args1[] = new String[5];
        args1[0] = "���ϱ��";
        args1[1] = "��������";
        args1[2] = "�����ƺ�";
        args1[3] = "���Ϲ��";
        args1[4] = "���ϱ�׼";
//      CCEndby leixiao 2009-6-24 ԭ�򣺹�����������������
        //�����б������
//        String args1[] = new String[2];
//        args1[0] = QMMessage.getLocalizedMessage(RESOURCE, "materialNumber", null);
//        args1[1] = QMMessage.getLocalizedMessage(RESOURCE, "materialName", null);
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
     * @param info ����ֵ����
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

    //CCBegin SS2
    public void setIsProcedure(boolean flag){
    	cappAssociationsPanel.setIsProcedure(flag);
    }
//CCEnd SS2

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
     * ��ָ���Ĳ�����Դ��ӵ��б���
     * @param material
     */
    public void addMaterialToTable(QMMaterialInfo material)
    {
        try
        {
            cappAssociationsPanel.addSelectedObject(material);
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
     * ִ���ֹ�¼����ϲ���
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

            if (verbose)
            {
                System.out.println("��:" + row + "\n" + "��:" + col);
            }
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
                    handworkAddMaterialNumber(textField);
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
     * �ֹ���Ӳ��ϱ�ŵ�ָ�����ı�����
     * @param textField ��д���ϱ�ŵ��ı���
     */
    private void handworkAddMaterialNumber(JTextField textField)
    {
        number = textField.getText().trim();
        //�������Ĳ��ϱ����ϵͳ�д��ڣ���ϵͳ�Ѹò���������б���
        QMMaterialInfo eq = checkMaterialIsExist(number);
        if (eq != null)
        {
            try
            {
                cappAssociationsPanel.addObjectToRow(eq, row);
                cappAssociationsPanel.setEnabled(true);
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
     * ������Դ����ͨ��ָ���Ĳ��ϱ�������ݿ��в��Ҳ��϶���
     * @param eqNumber ָ���Ĳ��ϱ��
     * @return ���ҵ��Ĳ��϶���
     */
    private QMMaterialInfo checkMaterialIsExist(String eqNumber)
    {
        Class[] c =
                {String.class};
        Object[] obj =
                {eqNumber};
        try
        {
            return (QMMaterialInfo) useServiceMethod(
                    "ResourceService", "findMaterialByNumber", c, obj);
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
            //���ϵͳ�в������б�������Ĳ��ϱ�ţ��򷵻ض�Ӧ����Ϣ��ѯ���Ƿ��½�
            //���ϣ�����ǣ���ʾ�����½����棬���ݷ���Ȩ�޴���������ϣ�����񣬷���ԭλ��.
            String s = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.IS_CREATE_MATERIAL, null);
            if (confirmAction(s))
            {
                cappAssociationsPanel.setEnabled(true);
                getParentJFrame().setCursor(Cursor.getPredefinedCursor(Cursor.
                        WAIT_CURSOR));
                //��ʾ�����½�����
                ResourceDialog d = new ResourceDialog(getParentJFrame(),
                        "material", "CREATE", number);
                d.setSize(650, 500);
                setViewLocation(d);
                d.setTitle("�½�����");
                d.addQuitListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        cappAssociationsPanel.undoCell();
                    }

                });

                d.setVisible(true);
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
     * ��������е�����
     */
    public void clear()
    {
        cappAssociationsPanel.clear();
    }

    
  //CCBegin SS3
    public void setProcedurePace(QMProcedureIfc info){
    	   cappAssociationsPanel.setProcedurePace(info);
    	  }
    //CCEnd SS3
}
