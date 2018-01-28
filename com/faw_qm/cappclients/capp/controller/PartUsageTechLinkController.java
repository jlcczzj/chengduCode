/** ���ɳ���PartUsageTechLinkController.java	1.1  2004/8/6
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ��ݹ����Զ���� 2014-1-13 ����
 * SS2 ��ݹ��ղ�Ʒƽ̨���� 2014-2-18 ����
 * SS3 ������û�Ҫ���޸��Զ���Ź���"����-�����-��������-��ˮ�š� pante 2014-09-04
 * SS4 ��ݹ��������Ҫ���ʱ���������в����ƺž��Զ���ӵ����������Ϣ�� pante 2014-10-23
 * SS5 ���ع����Զ���ų��� 2014-10-20 ����
 * SS6 �ɶ�TS16949�Զ���� guoxiaoliang 2016-7-12 
 * SS7 �ɶ�TD12047 �ɶ�����ѡ����Ҫ���ʱ���ձ������null����guoxiaoliang 2016-11-8
 */

package com.faw_qm.cappclients.capp.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JRadioButton;

import com.faw_qm.cappclients.beans.cappassociationspanel.CappAssociationsPanel;
import com.faw_qm.cappclients.beans.cappassociationspanel.ObjectFilter;
import com.faw_qm.cappclients.beans.cappexattrpanel.CappExAttrPanel;
import com.faw_qm.cappclients.capp.view.PartUsageTechLinkJPanel;
import com.faw_qm.cappclients.capp.view.TechUsageMaterialLinkJPanel;
import com.faw_qm.cappclients.capp.view.TechnicsMasterJPanel;
import com.faw_qm.cappclients.util.ComponentMultiList;
import com.faw_qm.extend.util.ExtendAttModel;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.part.model.QMPartInfo;


/**
 * <p>Title: ����ʹ������������Ŀ�����</p>
 * <p>Description: ���������о������������ĸ���,�����и��Ի��ĵط����Ը��Ǵ����еķ���
 * ���ǵķ�����: setRadionButtonSelected(), calculate(), handelCappAssociationsPanel(String type),
 * public BaseValueIfc[] doFillter(BaseValueIfc[] vec) </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company:һ������ </p>
 * @author Ѧ��
 * @version 1.0
 */

public abstract class PartUsageTechLinkController implements ObjectFilter
{
    /***/
    protected static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**���ڱ����Դ�ļ�·��*/
    public static String RESOURCE
            = "com.faw_qm.cappclients.capp.util.CappLMRB";


    /**�б���ѡ�е���*/
    int row = -1;


    /**�б���ѡ�е���*/
    int col = -1;
    public PartUsageTechLinkJPanel panel;
    public CappAssociationsPanel cappAssociationsPanel;


    /**������*/
    public ArrayList listeners;
    public String technicsType;


    /**���Ϲ������*/
    public TechUsageMaterialLinkJPanel materialPanel;

    public Component parent;


    /**
     * ���췽��
     * @param type String ��������
     */
    public PartUsageTechLinkController(String type)
    {
        technicsType = type;
    }


    /**
     * ���ù������,���Թ��������д���
     * @param linkpanel PartUsageTechLinkJPanel �������
     */
    public void setLinkPanel(PartUsageTechLinkJPanel linkpanel)
    {
        panel = linkpanel;
        cappAssociationsPanel = panel.getCappAssociationsPanel();
        handelCappAssociationsPanel();
    }

    
//  CCBeginby leixiao 2009-6-24 ԭ��������������ʱ�����ձ���Զ�����
    public void setLinkPanel(PartUsageTechLinkJPanel linkpanel,Component parentpanel)
    {
    	parent=parentpanel;
        panel = linkpanel;
        cappAssociationsPanel = panel.getCappAssociationsPanel();
        handelCappAssociationsPanel();
    }
//  CCEndby leixiao 2009-6-24 ԭ��������������ʱ�����ձ���Զ�����
    

    /**
     * ����������,��������ҵ������,��������,����,����,�ɱ༭����
     */
    private void handelCappAssociationsPanel()
    {
        //���ý�ɫ��
        cappAssociationsPanel.setRole("technics");
        cappAssociationsPanel.setObject(null);
        //���ù�������
        try
        {
            cappAssociationsPanel.setLinkClassName(
                    "com.faw_qm.capp.model.PartUsageQMTechnicsLinkInfo");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        //����ҵ������
        try
        {
            cappAssociationsPanel.setObjectClassName(
                    "com.faw_qm.capp.model.QMFawTechnicsInfo");
        }
        catch (ClassNotFoundException ex)
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
        cappAssociationsPanel.setLastIteration(true);
        //���ݹ����������Դ�ļ��õ��ַ���
        String allProperties = RemoteProperty.getProperty(
                "com.faw_qm.cappclients.capp.view" + technicsType);
        StringTokenizer stringToken = new StringTokenizer(allProperties, ";");

        //�õ�allProperties�е�һ���ֺ�ǰ���ַ�����Ϊ�㲿��������
        String partProperties = stringToken.nextToken();
        //�õ�allProperties�е�һ���ֺź���ַ�����Ϊ��������
        String linkProperties = stringToken.nextToken();

        //�����Ƿ�����չ����
        boolean flag = stringToken.nextToken().equals("true");
        //����
        String labels = stringToken.nextToken();
        //�Ƿ����ÿ������
        final boolean flag2 = stringToken.nextToken().equals("true");
        //�õ��ɱ༭������
        String editProperties = stringToken.nextToken();

        StringTokenizer partToken = new StringTokenizer(partProperties, ",");
        StringTokenizer linkToken = new StringTokenizer(linkProperties, ",");
        if (flag)
        {
            cappAssociationsPanel.setSecondTypeValue(technicsType);
        }
        StringTokenizer LabelToken = new StringTokenizer(labels, ",");

        //���㲿�����Է���partProperties[]��
        final int partL = partToken.countTokens();
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
        }
        //����
        int labelsL = LabelToken.countTokens();
        String[] label = new String[labelsL];
        for (int i = 0; i < labelsL; i++)
        {
            label[i] = LabelToken.nextToken();
            //���ü��㰴ť
        }
        if (flag2)
        {
            cappAssociationsPanel.setIsCalculate(true, 3);
            //�ѿɱ༭����������linkProperties[]��
        }
        StringTokenizer editToken = new StringTokenizer(editProperties, ",");
        int editL = editToken.countTokens();
        int[] editCols = new int[editL];
        for (int i = 0; i < editL; i++)
        {
            editCols[i] = Integer.parseInt(editToken.nextToken());
        }

        //���ý���ʾ�ڶ����б��й�����ҵ���������Լ�
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

        //������Ҫ�����ʶ����ʾJRadioButton
        if (getRadioButtonCols() != null)
        {
            cappAssociationsPanel.setRadionButtons(getRadioButtonCols());
        }
        cappAssociationsPanel.setColsEnabled(editCols, true);

        //���������Ӽ���
        cappAssociationsPanel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //�ӹ�����Ҫ��ʶ���㲿����Ҫ��ʶ
                cappAssociationsPanel_actionPerformed(e);

            }
        });

        cappAssociationsPanel.setObjectFilter(this);
        //���Ƿ�
        cappAssociationsPanel.setBrowseButtonMnemonic('F');
        cappAssociationsPanel.setRemoveButtonMnemonic('D');
        cappAssociationsPanel.setViewButtonMnemonic('V');
        cappAssociationsPanel.setCalculateButtonMnemonic('C');

    }
    //CCBegin SS2
    String mainPartBsoID = "";
    //CCEnd SS2
    /**
     * ���������¼�ִ�з���,����б�������˵�һ�У��������㲿����Ҫ��ʶ�͹�����Ҫ��ʶΪѡ��.
     * �����Ҫ�㲿���б�ѡ��,��֪ͨ������
     * @param e �¼�
     */
    private void cappAssociationsPanel_actionPerformed(ActionEvent e)
    {
        if (verbose)
        {
            System.out.println("cappclients.capp.view.PartUsageTechLinkJPanel.cappAssociationsPanel_actionPerformed begin"
                               );
        }
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

        //����б�������˵�һ�У��������㲿����Ҫ��ʶ�͹�����Ҫ��ʶΪѡ��
        if (e.getActionCommand().equals("FirstRow"))
        {
            BinaryLinkIfc link = (BinaryLinkIfc) e.getSource();
            if (link.getBsoID() == null)
            {
                cappAssociationsPanel.setRadionButtonValue(0,
                        getMajorpartMarkColum(), true);
                cappAssociationsPanel.setCheckboxValue(0,
                        getmMajortechnicsMarkColum(), true);

                actionPerformed_whenMajorpartMarkSelected(link.getLeftBsoID(),
                        0);
                //CCBegin SS2
                TechnicsMasterJPanel Parent2=(TechnicsMasterJPanel)parent;
        		String type2=Parent2.technicsType.getCodeContent();
                try {
					if(getUserFromCompany().equals("zczx")&&(type2.equals("��ݻ��ӹ���")||type2.equals("����ȴ�����"))){
		                mainPartBsoID = link.getLeftBsoID();
		                Parent2.hideMainPartBsoID.setText(mainPartBsoID);	
					}
				} catch (QMException e1) {
					e1.printStackTrace();
				}
                //CCEnd SS2
            }
            
//          CCBeginby leixiao 2009-6-24 ԭ���������ID,�������ȡ��  
            cappAssociationsPanel.setAssociatpart(link.getLeftBsoID());
           // System.out.println("---------------part="+cappAssociationsPanel.getAssociatpart()+"   "+link.getLeftBsoID());
//          CCEndby leixiao 2009-6-24 ԭ���������ID,�������ȡ��    	

        }
//      CCBeginby leixiao 2009-6-24 ԭ��������������ʱ�����ձ���Զ�����
        if (e.getActionCommand().equals("buildPartNumber"))
        {
    		String partnumber = (String) cappAssociationsPanel
    		.getCellAt(0, 0);
    		
    		String partversion = (String) cappAssociationsPanel
    		.getCellAt(0, 2);
//    		CCBegin by liunan 2009-09-22
    		String partstr="";
    		if(partversion.equals(""))
    		  partstr=partnumber+"_";
    		else
    		  partstr=partnumber+"_"+partversion.substring(0,partversion.indexOf("."));
    		//CCEnd by liunan 2009-09-22
    		//System.out.println("---buildPartNumber-----partstr="+partstr);
    		TechnicsMasterJPanel Parent=(TechnicsMasterJPanel)parent;
    		//System.out.println("--------Parent="+Parent);
    		String type=Parent.technicsType.getCodeContent();
    		String typestr=(String)Parent.typeMap.get(type);
    		//CCBegin SS5
    		String ctPartNumber = "";
    		//CCEnd SS5
    		//CCBegin SS1
    		String zhouchiNumber = "";
    		try {
				if(getUserFromCompany().equals("zczx")&&(type.equals("��ݻ��ӹ���")||type.equals("����ȴ�����"))){
					BaseValueIfc workShop  = (BaseValueIfc)Parent.workshopSortingSelectedPanel.getCoding();
					String workShopStr = "";
					Parent.hideMainPart.setText(partnumber);
					if(workShop!=null){
						workShopStr = String.valueOf(workShop);
					}
					if(workShopStr.equals("")){
						zhouchiNumber = partnumber+"-"+type;
					}else{
						//CCBegin SS3
//						zhouchiNumber = partnumber+"-"+type+"-"+workShopStr;
						zhouchiNumber = workShopStr+"-"+partnumber+"-"+type;
						//CCEnd SS3
					}
					Parent.numberJTextField.setText(zhouchiNumber);
					Parent.masterTS16949Panel.setAttributeNumber(partnumber,type);
					
//		    		String partname = (String) cappAssociationsPanel.getCellAt(0, 1);
//		    		Parent.nameJTextField.setText(partname); 
					
//		    		CCBegin SS4
		        	RequestServer server = RequestServerFactory.getRequestServer();
		    		ServiceRequestInfo info = new ServiceRequestInfo();
		    		info.setServiceName("StandardCappService");
		    		info.setMethodName("getPartM");
		    		Class[] paraClass = {String.class};
		    		info.setParaClasses(paraClass);
		    		Object[] obj = {mainPartBsoID};
		    		info.setParaValues(obj);
		    		try {
						String returnStr = ((String) server.request(info));
						CappExAttrPanel ex = Parent.getCappExAttrPanel();
						ex.setPartM(returnStr);
					} catch (QMRemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
//		    		CCEnd SS4
				}
			 //CCBegin SS5
				else if(getUserFromCompany().equals("ct")){

					Parent.hideMainPart.setText(partnumber);
					ctPartNumber = partnumber+"-"+type;

					Parent.numberJTextField.setText(ctPartNumber);
		    		String partname = (String) cappAssociationsPanel.getCellAt(0, 1);
					Parent.nameJTextField.setText(partname);  
					Parent.masterTS16949Panel.setAttribute(partnumber);

				}
				//CCEnd SS5
				
				//CCBegin SS6
				else if(getUserFromCompany().equals("cd")||getUserFromCompany().equals("Admin")){
					

					Parent.hideMainPart.setText(partnumber);
					Parent.masterTS16949Panel.setAttributeCD(partnumber,type);
					
				}
				//CCEnd SS6
				
				else{
					//CCBegin SS7
					if(typestr==null)
						typestr="";
					//CCEnd SS7
		    		Parent.numberJTextField.setText(typestr+partstr);   
		        	//  CCBeginby leixiao 2010-4-20 ԭ��������������ʱ�����������Զ�����
		    		String partname = (String) cappAssociationsPanel
		    		.getCellAt(0, 1);
		    		Parent.nameJTextField.setText(partname);  
		    	//  CCEndby leixiao 2010-4-20 ԭ��������������ʱ�����������Զ�����
//		          CCBeginby leixiao 2009-7-2 ԭ��������������ʱ�����ձ���Զ�����   
		    		ComponentMultiList m=(ComponentMultiList)e.getSource();
		    		int r=m.getNumberOfRows();
		    		//System.out.println("---"+r);
		    		for(int i=0;i<r;i++){
		    		String weight = (String) cappAssociationsPanel.getCellAt(i, 3);
//		          CCBeginby leixiao 2010-1-15 ԭ��Ϳװ����������ڵ�6�� 
		    		if(type.indexOf("Ϳװ")!=-1){
		    			cappAssociationsPanel.setCellTextValue(i,9,weight);	
		    		}
		    		else{
//		              CCEnd by leixiao 2010-1-15 
		             cappAssociationsPanel.setCellTextValue(i,6,weight);
		    		}
		    		}
//		           CCEndby leixiao 2009-7-2 ԭ��������������ʱ�����ձ���Զ�����
				}
			} catch (QMException e1) {
				e1.printStackTrace();
			}
    		//CCEnd SS1
			

        }
//      CCEndby leixiao 2009-6-24 ԭ��������������ʱ�����ձ���Զ�����
        //�����Ҫ�㲿���б�ѡ��,��֪ͨ������ִ��actionPerformed(ActionEvent e)
        if (col == getMajorpartMarkColum())
        {
            Object majorpartComp = e.getSource();
            if (majorpartComp instanceof JRadioButton &&
                ((JRadioButton) majorpartComp).isSelected())
            {

                actionPerformed_whenMajorpartMarkSelected(
                        cappAssociationsPanel.getSelectedLink().
                        getLeftBsoID(),
                        row);
//              CCEndby leixiao 2009-7-2 ԭ��������������ʱ�����ձ���Զ�����  ��Ӷ�����ʱ������Ҫ��ʶ������Զ����
        		String partnumber = (String) cappAssociationsPanel
        		.getCellAt(row, 0);
        		String partversion = (String) cappAssociationsPanel
        		.getCellAt(row, 2);
        		//CCBegin by liunan 2009-09-22
        		String partstr="";
        		if(partversion.equals(""))
        		  partstr=partnumber+"_";
        		else
        		  partstr=partnumber+"_"+partversion.substring(0,partversion.indexOf("."));
        		//CCEnd by liunan 2009-09-22        		
        	//	System.out.println("---buildPartNumber-----partstr="+partstr);
        		
        		TechnicsMasterJPanel Parent=(TechnicsMasterJPanel)parent;
        		//System.out.println("--------Parent="+Parent);
        		String type=Parent.technicsType.getCodeContent();
        		String typestr=(String)Parent.typeMap.get(type);
        		//CCBegin SS5
        		String ctPartNumber = "";
        		//CCEnd SS5
        		//CCBegin SS1
        		String zhouchiNumber = "";
        		try {
					if(getUserFromCompany().equals("zczx")&&(type.equals("��ݻ��ӹ���")||type.equals("����ȴ�����"))){
						BaseValueIfc workShop  = (BaseValueIfc)Parent.workshopSortingSelectedPanel.getCoding();
						String workShopStr = "";
						Parent.hideMainPart.setText(partnumber);
						//CCBegin SS2
		                mainPartBsoID = cappAssociationsPanel.getSelectedLink().getLeftBsoID();
						Parent.hideMainPartBsoID.setText(mainPartBsoID);
						//CCEnd SS2
						if(workShop!=null){
							workShopStr = String.valueOf(workShop);
						}
						if(workShopStr.equals("")){
							zhouchiNumber = partnumber+"-"+type;
						}else{
							//CCBegin SS3
//							zhouchiNumber = partnumber+"-"+type+"-"+workShopStr;
							zhouchiNumber = workShopStr+"-"+partnumber+"-"+type;
							//CCEnd SS3
						}
						Parent.numberJTextField.setText(zhouchiNumber);
						Parent.masterTS16949Panel.setAttributeNumber(partnumber,type);
//						String partname = (String) cappAssociationsPanel.getCellAt(0, 1);
//						Parent.nameJTextField.setText(partname); 
					}
					
					//CCBegin SS5
					else if(getUserFromCompany().equals("ct")){

						Parent.hideMainPart.setText(partnumber);
		                mainPartBsoID = cappAssociationsPanel.getSelectedLink().getLeftBsoID();
						Parent.hideMainPartBsoID.setText(mainPartBsoID);
						ctPartNumber = partnumber+"-"+type;
						Parent.numberJTextField.setText(ctPartNumber);
			    		String partname = (String) cappAssociationsPanel.getCellAt(row, 1);
						Parent.nameJTextField.setText(partname);  
						Parent.masterTS16949Panel.setAttributeNumber(partnumber,type);
					}
					//CCEnd SS5
					else{
						//CCBegin SS7
						if(typestr==null)
							typestr="";
						//CCEnd SS7
		        		Parent.numberJTextField.setText(typestr+partstr);         		 
//		              CCEndby leixiao 2009-7-2 ԭ��������������ʱ�����ձ���Զ�����        
		        	//  CCBeginby leixiao 2010-4-20 ԭ��������������ʱ�����������Զ�����
		        		String partname = (String) cappAssociationsPanel
		        		.getCellAt(row, 1);
		        		Parent.nameJTextField.setText(partname);  
		        	//  CCEndby leixiao 2010-4-20 ԭ��������������ʱ�����������Զ�����
					}
				} catch (QMException e1) {
					e1.printStackTrace();
				}
				//CCEnd SS1


            }
        }
        if (e.getActionCommand().equals("calculate"))
        {
            calculate();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.view.PartUsageTechLinkJPanel.cappAssociationsPanel_actionPerformed end");
        }
    }

  //CCBegin SS1
    /**
     * �ж��û�������˾
     * @return String ����û�������˾
     * @author wenl
     */
    public String getUserFromCompany() throws QMException {
		String returnStr = "";
		RequestServer server = RequestServerFactory.getRequestServer();
		StaticMethodRequestInfo info = new StaticMethodRequestInfo();
		info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
		info.setMethodName("getUserFromCompany");
		Class[] paraClass = {};
		info.setParaClasses(paraClass);
		Object[] obj = {};
		info.setParaValues(obj);
		boolean flag = false;
		try {
			returnStr = ((String) server.request(info));
		} catch (QMRemoteException e) {
			throw new QMException(e);
		}
		return returnStr;
	}
//CCEnd SS1
    /**
     * ��Ӽ�����,������������¼�
     * @param e ActionListener
     */
    public void addListener(ActionListener e)
    {
        if (listeners == null)
        {
            listeners = new ArrayList();
        }
        listeners.add(e);
    }


    /**
     * ���ò��Ϲ������
     * @param materialPanel TechUsageMaterialLinkJPanel
     */
    public void setMaterialLinkJPanel(TechUsageMaterialLinkJPanel
                                      panel)
    {
        materialPanel = panel;
    }


    /**
     * ���,���า�Ǵ˷���
     */
    public void clear()
    {}


    /**
     * �����Ҫ��ʶ�Ƿ�ѡ��ʱ��ִ�з���,�˷�������Ӧ�������и���
     * @param partId boolean �����bsoID
     * @param row ����
     */
    public void actionPerformed_whenMajorpartMarkSelected(String partId,
            int row)
    {}


    /**
     * ���˹��������ӵĶ��󼯺�
     * @param parm1 BaseValueIfc[] ����ǰ�Ľ����
     * @return BaseValueIfc[] ���˺�Ľ����
     */
    public BaseValueIfc[] doFillter(BaseValueIfc[] parm1)
    {
        return parm1;
    }


    /**
     * ����,����Ӧ�����и���
     */
    public abstract void calculate();


    /**
     * �õ���ѡ�������
     * @return int[]
     */
    public abstract int[] getRadioButtonCols();


    /**
     * �õ������Ҫ��ʶ������
     * @return int
     */
    public abstract int getMajorpartMarkColum();


    /**
     * ������Ҫ��ʶ������
     * @return int
     */
    public abstract int getmMajortechnicsMarkColum();
}
