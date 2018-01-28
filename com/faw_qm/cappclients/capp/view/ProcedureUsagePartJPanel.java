/**
 *   �޸ļ�¼
 *   
 *   CR1 ��������2009/04/21   ԭ��:���������������Դ���ܽ��ж�ѡɾ��.
 *                            ����:����Դ�б��޸�Ϊ����ѡ���������,�����޸���ɾ����ť��
 *                                 �߼�.
 *                            ��ע:�����¼���"CRSS-009"
 *                
 * SS1 ������ɱ༭�����Ų�ͬ pante 20130729  
 * SS2 �����¹��ܹ������豸��װ��������������Ƿ��ۼӵ��� leixiao 2013-9-23      
 * SS3 ���������ӹ������ۼӵ����򡱲��Ҳ��ڹ�������ʾ leixiao 2013-10-14      
 * SS4 �ɶ�������ӻ���Ӽ�����  guoxiaoliang 2016-7-28         
 * SS5 �ɶ�����������Դ��ӹ�����Դ���� guoxiaoliang 2016-8-2     
 * SS6 �ɶ���ӵ���������  guoxiaoliang 2016-8-3  
 */
package com.faw_qm.cappclients.capp.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartIfc;


/**
 * <p>Title:����ʹ���㲿��������� </p>
 * <p>Description: ά�����������</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author Ѧ��
 * @version 1.0
 * ���⣨1��20080728  xucy add  ���ÿɱ༭����
 */

public class ProcedureUsagePartJPanel extends ParentJPanel
{
    /**
     * �������
     */
    private CappAssociationsPanel cappAssociationsPanel = new
            CappAssociationsPanel();
    private GridBagLayout gridBagLayout1 = new GridBagLayout();


    /**
     * ��������
     */
    private String stepType;


    /**
     * ����ģʽ
     */
    private String mode = "";


    /**
     * ʹ��������
     */
    private int useCountColum = -1;


    /**
     * �б���ѡ�е���
     */
    int row = -1;

    /**
     * �б���ѡ�е���
     */
    int col = -1;
    
    
    //CCBegin SS4
    private boolean isProcedure = false;
    private BaseValueIfc dikefTechnics;
    //CCEnd SS4

    
    //CCBegin SS5
    
    /**
     * ���췽��
     */
    public ProcedureUsagePartJPanel(boolean isProcedure)
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
    //CCEnd SS5
    
    /**
     * ���췽��
     * @param stepType String ��������
     */
    public ProcedureUsagePartJPanel(String stepType)
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
//CCBegin SS4
    
    /**
     * ���췽��
     * @param stepType String ��������
     */
    public ProcedureUsagePartJPanel(String stepType,BaseValueIfc base)
    {
       this(stepType,true,base);
    }
    
    public ProcedureUsagePartJPanel(String stepType,boolean isProcedure)
    {
      this(stepType,isProcedure,null);
    }
    
    
    /**
     * ���췽��
     * @param stepType String ��������
     */
    public ProcedureUsagePartJPanel(String stepType,boolean isProcedure,BaseValueIfc base)
    {
        this.stepType = stepType;
        this.isProcedure= isProcedure;
        this.dikefTechnics=base;
        try
        {
            jbInit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    //CCEnd SS4

    /**
     * �����ʼ��
     * @throws Exception
     * ���⣨1��20080728  xucy add  ���ÿɱ༭����
     */
    void jbInit()
            throws Exception
    {
        setLayout(gridBagLayout1);
        //CCBegin by liuzc 2009.12.12 ԭ����������
        cappAssociationsPanel.getMultiList().setAllowSorting(false);
        cappAssociationsPanel.setIsUpDown(true, 4, 5);
        //CCBegin SS6
        cappAssociationsPanel.setProcedureUsagePartJPanel(this);
        //CCEnd SS6
        
        
        //CCEnd by liuzc 2009.12.12 ԭ����������
        //���ݹ����������Դ�ļ��õ��ַ���
        String allProperties = RemoteProperty.getProperty(
                "com.faw_qm.cappclients.capp.view" + stepType);
        if (allProperties.trim().equals("null") || allProperties.equals(""))
        {
            return;
        }
        add(cappAssociationsPanel, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(5, 5, 5, 0), 0, 0));

        //localize();
        //���ý�ɫ��procedure
        cappAssociationsPanel.setRole("procedure");
        cappAssociationsPanel.setObject(null);
        //���ù�������
        try
        {
            cappAssociationsPanel.setLinkClassName(
                    "com.faw_qm.capp.model.QMProcedureQMPartLinkInfo");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        //���ù���ҵ���������
        try
        {
            cappAssociationsPanel.setOtherSideClassName(
                    "com.faw_qm.part.model.QMPartInfo");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }

        StringTokenizer stringToken = new StringTokenizer(allProperties, ";");
        //�õ�allProperties�е�һ���ֺ�ǰ���ַ�����Ϊ�㲿��������
        String partProperties = stringToken.nextToken();
        //�õ�allProperties�е�һ���ֺź���ַ�����Ϊ��������
        String linkProperties = stringToken.nextToken();
        System.out.println("linkProperties========"+linkProperties);
        //�����Ƿ�����չ����
        boolean flag = stringToken.nextToken().equals("true");
        //����
        String labels = stringToken.nextToken();
        
       
        StringTokenizer partToken = new StringTokenizer(partProperties, ",");
        StringTokenizer linkToken = new StringTokenizer(linkProperties, ",");
        if (flag)
        {
            cappAssociationsPanel.setSecondTypeValue(stepType);
        }
        StringTokenizer LabelToken = new StringTokenizer(labels, ",");
        
        String s = null;
        //���㲿�����Է���partProperties[]��
        int partL = partToken.countTokens();
        String[] part = new String[partL];
        for (int i = 0; i < partL; i++)
        {
            part[i] = partToken.nextToken();
        }
        //�ѹ������Է���linkProperties[]��
        int linkL = linkToken.countTokens();
        String[] link = new String[linkL];
        for (int i = 0; i < linkL; i++)
        {
            link[i] = linkToken.nextToken();
            if (link[i].equals("usageCount"))
            {
                useCountColum = partL + i;
            }
            if (verbose)
            {
                System.out.println("useCount=" + useCountColum);
            }
        }
        
//        System.out.println("link========"+link.length+"    "+link[0]+"      "+link[1]);
        //����
        int labelsL = LabelToken.countTokens();
        String[] label = new String[labelsL];
        for (int i = 0; i < labelsL; i++)
        {
            label[i] = LabelToken.nextToken();

            //���ý���ʾ�ڶ����б��й�����ҵ���������Լ�
        }
        System.out.println("label========"+label.length+"    "+label[0]+"      "+label[1]);
        cappAssociationsPanel.setOtherSideAttributes(part);

        try
        {
            cappAssociationsPanel.setMultiListLinkAttributes(link);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        try
        { //����
            cappAssociationsPanel.setLabels(label);
        }
        catch (PropertyVetoException ex)
        {
            ex.printStackTrace();
        }
        //�����Ƿ�ֻ������Ĺ���
        cappAssociationsPanel.setSaveUpdatesOnly(true);
        //��AssociationsPanel�·���AttributeForm����ɾ��
        cappAssociationsPanel.removeAttributeForm();
        //����ֻ����㲿����ÿ����汾�����°汾
        cappAssociationsPanel.setLastIteration(true);     
        //SSBegin SS1
        if(this.stepType.contains("������")){
        	 //���⣨1��20080728  xucy add  ���ÿɱ༭����  begin
            // Sysem.out.println("stringToken====="+stringToken);
             String editProperties = stringToken.nextToken();
             StringTokenizer editToken = new StringTokenizer(editProperties, ",");
             //System.out.println("editTokeneditToken====="+editToken);
             int editL = editToken.countTokens();
             //System.out.println("editLeditL====="+editL);
             int[] editCols = new int[editL];

             for (int i = 0; i < editL; i++)
             {
                 editCols[i] = Integer.parseInt(editToken.nextToken());
             }
             cappAssociationsPanel.setColsEnabled(editCols, true);
             //���⣨1��20080728  xucy add  ���ÿɱ༭����  endt
        }
        else{
        	cappAssociationsPanel.setMutliSelectedModel(true);//CR1

        	//����ʹ�������п��Ա༭
        	//CCBegin SS2
        	int[] is =
        		{partL,partL+1};
        	cappAssociationsPanel.setColsEnabled(is, true);
        	//CCEnd SS2
        }

      //SSEnd SS1
        //��Ӽ���,Ŀ���Ǽ��װ�乤���ʹ�������Ƿ񳬳�ֵ��
        cappAssociationsPanel.addActionListener(new java.awt.event.
                                                ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                cappAssociationsPanel_actionPerformed(e);
            }
        });
        
        //CCBegin SS4
        
        if(isProcedure)
        {
        	
        	
          cappAssociationsPanel.setIsInsert(true, 3);
          cappAssociationsPanel.setIsStruct(true, 4);
          //added by dikef
          cappAssociationsPanel.setHasSon(true);
          cappAssociationsPanel.setLinkedTechnics(dikefTechnics);
          //added by dikef end
        }
        else{
        
        cappAssociationsPanel.setIsInsert(true, 3);
        cappAssociationsPanel.setIsStruct(true, 7);
        
        cappAssociationsPanel.setIsPartadd(true,8);
        cappAssociationsPanel.setIsGetResouceByProcedure(true,9);
        

        
       } 
        
        
        //CCEnd SS4
        
       
        
        
        cappAssociationsPanel.setBrowseButtonMnemonic('F');
        cappAssociationsPanel.setRemoveButtonMnemonic('D');
        cappAssociationsPanel.setViewButtonMnemonic('V');
//      CCBegin by liuzhicheng 20010.01.12 ԭ��ȥ����Ӱ�ť  �μ�TD=2800
//        cappAssociationsPanel.setInsertButtonMnemonic('I');
//      CCEnd by liuzhicheng 2009.01.12
        cappAssociationsPanel.setUpButtonMnemonic('U');
        cappAssociationsPanel.setDownButtonMnemonic('N');
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
     * ���ػ�
     */
    protected void localize()
    {
        //�����б������
        String args1[] = new String[2];
        args1[0] = QMMessage.getLocalizedMessage(RESOURCE, "partNumber", null);
        args1[1] = QMMessage.getLocalizedMessage(RESOURCE, "partName", null);
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
    //CCBegin SS3
    public void setIsProcedure(boolean flag){
    	if(!this.stepType.contains("������")){
    		cappAssociationsPanel.setIsProcedure(flag);
    	}
    }
//CCEnd SS3
    
    
    /**
     * ������й���
     * @return ���й��������ļ���
     */
    public Vector getAllLinks()
            throws Exception
    {
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

    public void clear()
    {
        cappAssociationsPanel.clear();
        useCountColum = -1;
    }


    /**
     * ����㲿��
     */
    public void addPartToTable(QMPartIfc part)
    {
        try
        {
            cappAssociationsPanel.addSelectedObject(part);
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
     * ���װ�乤���㲿��ʹ������
     * @param e
     */
    public void cappAssociationsPanel_actionPerformed(ActionEvent e)
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

        //���ѡ�е���ʹ��������
        if (col == useCountColum
            && stepType.equals("װ�乤��"))
        {
            if (obj instanceof JTextField)
            {
                JTextField textField = (JTextField) obj;
                String number = textField.getText().trim();
                try
                {
                    int f = Integer.parseInt(number);
                    if (f > 99 || f < 0)
                    {
                        textField.setText("1");
                        cappAssociationsPanel.addToList(row, col, "1");
                        String title = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.USECOUNT_INVALID, null);
                        String message = QMMessage.getLocalizedMessage(RESOURCE,
                                CappLMRB.USECOUNT_TOOLONG, null);
                        JOptionPane.showMessageDialog(getParentJFrame(),
                                message,
                                title, JOptionPane.WARNING_MESSAGE);
                    }
                }
                catch (NumberFormatException ex)
                {
                    cappAssociationsPanel.addToList(row, col, "1");
                    //��ʾʹ���������Ͳ���ȷ
                    String title = QMMessage.getLocalizedMessage(RESOURCE,
                            CappLMRB.USECOUNT_INVALID, null);
                    Object[] obj1 =
                            {"ʹ������", "����"};
                    String message = QMMessage.getLocalizedMessage(RESOURCE,
                            "114", obj1);
                    JOptionPane.showMessageDialog(getParentJFrame(), message,
                                                  title,
                                                  JOptionPane.WARNING_MESSAGE);

                }
            }
        }
    }
    
    //CCBegin SS4
    
    public void setLinkedTechnics(BaseValueIfc base)
    {
      dikefTechnics=base;
    }
    //CCEnd SS4
    
    //CCBegin SS5
    public void setProcedurePace(QMProcedureIfc info){
    	   cappAssociationsPanel.setProcedurePace(info);
    	  }
    //CCEnd SS5
    
    //CCBegin SS6
    
    public void setTextValue(String s){
        tpjPanel.setTechnicsPaceText(s);
      }
    
    private TechnicsPaceJPanel tpjPanel = null;
    public void setTechnicsPaceJPanel(TechnicsPaceJPanel tpjPanel){
      this.tpjPanel = tpjPanel;
    }
    //CCEnd SS6

}
