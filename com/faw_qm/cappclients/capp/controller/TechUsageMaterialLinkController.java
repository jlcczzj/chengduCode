/** ���ɳ���TechUsageMaterialLinkController.java	1.1  2004/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ���ڲ��϶������������Ļ�ȡ���������Ϣ�ķ��� liuyang 2013-2-26
 */
package com.faw_qm.cappclients.capp.controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.cappclients.capp.util.CappLMRB;
import com.faw_qm.cappclients.capp.view.TechUsageMaterialLinkJPanel;
import com.faw_qm.cappclients.resource.view.ResourceDialog;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.resource.support.model.QMMaterialInfo;


/**
 * <p>Title: ����ʹ�ò��Ϲ������Ŀ�����</p>
 * <p>Description: �����൱��Ĭ�ϵĲ��Ϲ���������,
 * �����и��Ի��ĵط����Ը��Ǵ����еķ���
 * ���ǵķ�����: setRadionButtonSelected(), calculate(),
 * handelCappAssociationsPanel(String type),
 * public BaseValueIfc[] doFillter(BaseValueIfc[] vec)
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Ѧ��
 * @version 1.0
 */
public abstract class TechUsageMaterialLinkController //implements ObjectFilter
{
    /**
     * ��������ϵĹ������
     */
    public TechUsageMaterialLinkJPanel panel;


    /**
     * ����bean
     */
    public CappAssociationsPanel cappAssociationsPanel;


    /**�б���ѡ�е���*/
    public int row = -1;


    /**�б���ѡ�е���*/
    public int col = -1;


    /**
     * ����Ĳ��ϱ��
     */
    private String inputNumber = "";


    /**
     * ��������
     */
    public String technicsType = null;


    /**������Ա���*/
    protected static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**���ڱ����Դ�ļ�·��*/
    public static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**
     * ���췽��
     * @param type String ��������
     */
    public TechUsageMaterialLinkController(String type)
    {
        technicsType = type;
    }


    /**
     * ���ò��Ϲ������,�����ù���bean
     * @param linkpanel TechUsageMaterialLinkJPanel ���Ϲ������
     */
    public void setLinkPanel(TechUsageMaterialLinkJPanel linkpanel)
    {
        panel = linkpanel;
        cappAssociationsPanel = panel.getCappAssociationsPanel();
        handelCappAssociationsPanel();
    }


    /**
     * ִ���ֹ�¼����ϲ���\�жϲ��϶���
     * @param e ActionEvent
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

        //���ѡ�е��ǵ�0�У����ϱ���У�
        if (col == 0 && obj instanceof JTextField)
        {
            cappAssociationsPanel.setEnabled(false);
            JTextField textField = (JTextField) obj;
            if (textField.getText().trim() == null ||
                textField.getText().trim().equals(""))
            {
                cappAssociationsPanel.setEnabled(true);
            }
            else
            {
                handworkAddMaterialNumber(textField);
            }

        }

        //�б�������˵�һ�У������ò�����Ҫ��ʶΪѡ��
       if (e.getActionCommand().equals("FirstRow"))
        {
            BinaryLinkIfc link = (BinaryLinkIfc) e.getSource();
            if (link.getBsoID() == null)
            {

                actionPerformed_whenFirstRowAdded();
            }
        }
        //����ÿ������
        try
        {
            if (actionCommand.equals("calculate"))
            {
                calculate();
            }
//          CCBeginby leixiao 2009-7-8 ԭ�򣺹����������壬���ӻ�ȡ���϶���
            if (actionCommand.equals("ration"))
            {            	
                getration();
            }
//          CCEndby leixiao 2009-7-8 ԭ�򣺹����������壬���ӻ�ȡ��
        }
        catch (Exception ex1)
        {
            if(verbose)
            ex1.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(panel.getParentJFrame(),
                                          ex1.getLocalizedMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }


    /**
     * �ֹ���Ӳ��ϱ�ŵ�ָ�����ı�����
     * @param textField ��д���ϱ�ŵ��ı���
     */
    private void handworkAddMaterialNumber(JTextField textField)
    {
        String number = textField.getText().trim();
        inputNumber = number;
        //�������Ĳ��ϱ����ϵͳ�д��ڣ���ϵͳ�Ѹò���������б���
        QMMaterialInfo eq = checkMaterialIsExist(number);
        if (eq == null)
        {
            CreateResource t = new CreateResource();
            new Thread(t).start();
        }

        else
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
            return (QMMaterialInfo) TechnicsAction.useServiceMethod(
                    "ResourceService", "findMaterialByNumber", c, obj);
        }
        catch (QMRemoteException ex)
        {
            //ex.printStackTrace();
            return null;
        }
    }

    class CreateResource extends Thread
    {
        public void run()
        {

            //���ϵͳ�в������б�������Ĳ��ϱ�ţ��򷵻ض�Ӧ����Ϣ��ѯ���Ƿ��½�
            //���ϣ�����ǣ���ʾ�����½����棬���ݷ���Ȩ�޴���������ϣ�����񣬷���ԭλ��.
            String s = QMMessage.getLocalizedMessage(
                    RESOURCE, CappLMRB.IS_CREATE_MATERIAL, null);
            if (confirmAction(s))
            {
                cappAssociationsPanel.setEnabled(true);
                panel.getParentJFrame().setCursor(Cursor.getPredefinedCursor(
                        Cursor.WAIT_CURSOR));
                //��ʾ�����½�����
                //��ʾ�����½�����
                ResourceDialog d = new ResourceDialog(panel.getParentJFrame(),
                        "material", "CREATE", inputNumber);
                d.addQuitListener(new ActionListener()
                          {
                              public void actionPerformed(ActionEvent e)
                              {
                                  cappAssociationsPanel.undoCell();
                              }

                          });

                d.setSize(650, 500);
                panel.setViewLocation(d);
                d.setTitle("�½�����");


                d.setVisible(true);
                panel.getParentJFrame().setCursor(Cursor.getDefaultCursor());
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
                      //20061127Ѧ���޸ģ���ѡ��Ķ����ǿ�ʱ�������ϱ�����ÿ�
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
                cappAssociationsPanel.undoCell();
                cappAssociationsPanel.setEnabled(true);
            }

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
        return JOptionPane.showConfirmDialog(panel.getParentJFrame(),
                                             s, title,
                                             JOptionPane.YES_NO_OPTION) ==
                JOptionPane.YES_OPTION;
    }


    /**
     * ����,��Ҫ��������า�Ǵ˷���
     */
    public void calculate()
    {}

    
//  CCBeginby leixiao 2009-7-8 ԭ�򣺹����������壬���ӻ�ȡ���϶���
    public void   getration(){

    }
    
    public Collection findMainRationByPart(String partid){
    	Collection collection=null;

    	try
    	{
    		Class[] paraClass ={String.class};
    		Object[] objs ={partid};

    		QMPartInfo part = (QMPartInfo)
    		TechnicsAction.useServiceMethod(
 	         "PersistService", "refreshInfo", paraClass, objs);
    		Class[] paraClass1 ={String.class};
    		Object[] objs1 ={part.getMasterBsoID()};
    		//CCBegin SS1
//    		collection = (Collection) TechnicsAction.useServiceMethod(
//    				"MaterialRationService", "findMainRationByPart", paraClass1, objs1);
    		//CCEnd SS1 
    		collection = (Collection) TechnicsAction.useServiceMethod(
    				"MaterialRationService", "getValidMainRationByPartMasterID", paraClass1, objs1);
    	}
    	catch (QMRemoteException ex)
    	{
    		ex.printStackTrace();

    	}
    	return collection;
}
//  CCEndby leixiao 2009-7-8 ԭ�򣺹����������壬���ӻ�ȡ���϶���
    

    /**
     * ���ݹ�������õ�����������ʾ����
     * @param type String ��������
     * @return Vector ����Ϊ4 ��һ��Ԫ���ǲ��ϻ�����������,�ڶ���Ԫ���ǹ�����������,������Ԫ���Ǳ�ǩ��
     * ��4��Ԫ���ǹ��������Ƿ������չ����
     */
    public ArrayList getAttrs()
    {
        //���ݹ����������Դ�ļ��õ��ַ���
        String allProperties = RemoteProperty.getProperty(
                "QMTechnicsQMMaterialLink" + technicsType);

        if (allProperties == null)
        {
            return null;
        }
        ArrayList vec = new ArrayList();
        StringTokenizer stringToken = new StringTokenizer(allProperties, ";");
        //�õ����ϵ�����
        String materialProperties = stringToken.nextToken();
        //�õ���������
        String linkProperties = stringToken.nextToken();
        //�õ��������Եı�ǩ��
        String labelProperties = stringToken.nextToken();
        Boolean hasExtendAttr;
        if (stringToken.nextToken().trim().equals("true"))
        {
            hasExtendAttr = Boolean.TRUE;
        }
        else
        {
            hasExtendAttr = Boolean.FALSE;
        }

        //�Ѳ������Է���materialPropertiesArray[]��
        StringTokenizer materialToken = new StringTokenizer(materialProperties,
                ",");
        final int mL = materialToken.countTokens();
        String[] materialPropertiesArray = new String[mL];
        for (int i = 0; i < mL; i++)
        {
            materialPropertiesArray[i] = materialToken.nextToken();
        }
        vec.add(materialPropertiesArray);

        //�ѹ������Է���LinkPropertiesArray[]��
        StringTokenizer linkToken = new StringTokenizer(linkProperties, ",");
        int lL = linkToken.countTokens();
        String[] LinkPropertiesArray = new String[lL];
        for (int i = 0; i < lL; i++)
        {
            LinkPropertiesArray[i] = linkToken.nextToken();
        }
        vec.add(LinkPropertiesArray);

        //�ѱ�ǩ������labelPropertiesArray[]��
        StringTokenizer lableToken = new StringTokenizer(labelProperties, ",");
        int labelL = lableToken.countTokens();
        String[] labelPropertiesArray = new String[labelL];
        for (int i = 0; i < labelL; i++)
        {
            labelPropertiesArray[i] = lableToken.nextToken();
        }
        vec.add(labelPropertiesArray);
        vec.add(hasExtendAttr);

        return vec;
    }


    /**
     * ���ù���bean,�����ڸ��Ǵ˷���ʱҪ�ȵ��ø���Ĵ˷���
     * @param type String��������
     */
    public void handelCappAssociationsPanel()
    {
        try
        {
            //���ý�ɫ��
            cappAssociationsPanel.setRole("technics");
            //���ù�������
            cappAssociationsPanel.setLinkClassName(
                    "com.faw_qm.capp.model.QMTechnicsQMMaterialLinkInfo");
            //����ҵ������
            cappAssociationsPanel.setObjectClassName(
                    "com.faw_qm.capp.model.QMFawTechnicsInfo");
            //���ù���ҵ���������
            cappAssociationsPanel.setOtherSideClassName(
                    "com.faw_qm.resource.support.model.QMMaterialInfo");
            //�����Ƿ�ֻ������Ĺ���
            cappAssociationsPanel.setSaveUpdatesOnly(true);
            //��AssociationsPanel�·���AttributeForm����ɾ��
            cappAssociationsPanel.removeAttributeForm();

            ArrayList attr = getAttrs();
            //�Ƿ������չ����
            boolean flag = ((Boolean) attr.get(3)).booleanValue();
            if (flag)
            {
                cappAssociationsPanel.setSecondTypeValue(technicsType);

                //���ý���ʾ�ڶ����б��й�����ҵ���������Լ�
            }
            String[] as1 = (String[]) attr.get(0);
            cappAssociationsPanel.setOtherSideAttributes(as1);

            //���ù�������
            String[] args2 = (String[]) attr.get(1);
            cappAssociationsPanel.setMultiListLinkAttributes(args2);

            //���ñ�ǩ
            String[] labels = (String[]) attr.get(2);
            cappAssociationsPanel.setLabels(labels);
            // cappAssociationsPanel.setObjectFilter(this);
            //���Ƿ�
            cappAssociationsPanel.setBrowseButtonMnemonic('F');
            cappAssociationsPanel.setRemoveButtonMnemonic('D');
            cappAssociationsPanel.setViewButtonMnemonic('V');
            cappAssociationsPanel.setCalculateButtonMnemonic('C');

        }
        catch (Exception ex1)
        {
            ex1.printStackTrace();
        }

    }


    /**
     * ���˽����
     * @param vec BaseValueIfc[]
     * @return BaseValueIfc[]
     */
    /* public BaseValueIfc[] doFillter(BaseValueIfc[] vec)
     {

         BaseValueIfc[] returnValues = new BaseValueIfc[1];
         if (cappAssociationsPanel.getLinks().size() == 0)
         {
             returnValues[0] = vec[0];
             return returnValues;
         }
         else
         {
             return null;
         }
     }
     */

    /**
     * ��ò���������,��Ӧ���า�Ǵ˷���
     * @throws Exception
     * @return double
     */
    public double getMRation()
            throws Exception
    {
        return 0;
    }


    /**
     * ��������е�һ�����ʱ�Ĵ�������
     */
    public void actionPerformed_whenFirstRowAdded()
    {
        cappAssociationsPanel.setRadionButtonValue(0, getMajorMCol(), true);
    }


    /**
     * �������������е���Ҫ�����ʶѡ��ʱ,���Ϲ������Ĵ����¼�,�������и���
     * @param e ActionEvent
     */
    public void actionPerformd_whenAddedMajorPark(ActionEvent e)
    {
    }


    /**
     * �����Ҫ���ϱ�ʶ��,�����и��Ǵ˷���
     * @return int ��Ҫ���ϱ�ʶ��
     */
    public abstract int getMajorMCol();

}
