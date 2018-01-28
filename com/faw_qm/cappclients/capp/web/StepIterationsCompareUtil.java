/** ���ɳ���StepIterationsCompareUtil.java	1.1  2003/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.capp.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.faw_qm.capp.model.QMProcedureIfc;
import com.faw_qm.capp.util.CappServiceHelper;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.jview.chrset.DataDisplay;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.extend.util.ExtendAttContainer;

/**
 * <p>Title:����İ�����ʷ�Ƚ� </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 * ���⣨1��20080925 �촺Ӣ�޸� �޸�ԭ�򣺱ȽϹ��򸽼���Ϣ�еĳ�������
 */

public class StepIterationsCompareUtil
{
    /**��ѡ������ID*/
    private String[] selectBsoID;


    /**�־û�����*/
    private PersistService service;


    /**ҵ����󣨹���*/
    private QMProcedureIfc myObjectInfo;
    public boolean flag = false;


    /**����İ汾��*/
    public String stepVersionID;


    /**�汾�ż���*/
    public Vector myVersionIDCollection = new Vector();


    /**���󼯺�*/
    public Vector myTechnicsCollection = new Vector();
    //add by wangh on 20070404
    public Vector panelCollection = new Vector();
    
    private CappServiceHelper helper = new CappServiceHelper();

    private String technicsBsoID;
    public ArrayList panelCompare = new ArrayList();
    public ArrayList eachPanel = new ArrayList();

    /**������Ա���*/
    private static boolean verbose = true;//(RemoteProperty.getProperty(
//            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    //add by wangh on 20070426
    private static boolean ts16949 = (RemoteProperty.getProperty(
            "TS16949", "true")).equals("true");

    /**
     * ���캯��
     */
    public StepIterationsCompareUtil()
    {
    }


    /**
     * ����ѡ������ж����BsoID
     * @param sBsoID �����BsoID������
     */
    public void setSelectBsoID(String[] sBsoID)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.StepIterationsCompareUtil.setSelectBsoID() begin...");
        }
        selectBsoID = sBsoID;
        flag = true;
        if (verbose)
        {
            System.out.println("cappclients.capp.web.StepIterationsCompareUtil.setSelectBsoID() end...return is void");
        }
    }


    /**
     * ����ݿͻ���ͷ������ʾ��Ϣ(�����š����ơ��汾�š�ͼ��)
     * @param partBsoID �����BsoID
     */
    public String[] getHeadInfo(String stepBsoID)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.StepIterationsCompareUtil.getHeadInfo() begin...");
        }
        try
        {
            PersistService service1 = (PersistService) EJBServiceHelper.
                                      getService(
                    "PersistService");
            QMProcedureIfc info = (QMProcedureIfc) service1.refreshInfo(
                    stepBsoID);
            String number = String.valueOf(info.getStepNumber());
            String name = info.getStepName();
            String versionID = info.getVersionID();
            String headIconUrl = helper.getStandardIconName(info);
            String[] myHeadInfo1 =
                    {number, name, versionID, headIconUrl};
            if (verbose)
            {
                System.out.println(
                        "cappclients.capp.web.StepIterationsCompareUtil.getHeadInfo() end...return: "
                        + myHeadInfo1);
            }
            return myHeadInfo1;
        }
        catch (Exception sle)
        {
            sle.printStackTrace();
            return null;
        }
    }


    /**
     * ���ѡ�еĶ���ļ��ϣ�ҳ����ѡ�е���BsoIDͨ���������ת�ɶ���
     * @return  ѡ�еĶ���ļ���
     */
    public Vector getSelectObject()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.StepIterationsCompareUtil.getSelectObject() begin...");
        }
        if (flag)
        {
        	myVersionIDCollection.clear();
        	myTechnicsCollection.clear();
            for (int i = 0; i < selectBsoID.length; i++)
            {
                try
                {
                    service = (PersistService) EJBServiceHelper.getService(
                            "PersistService");
                    myObjectInfo = (QMProcedureIfc) service.refreshInfo(
                            selectBsoID[i].trim());
                }
                catch (Exception sle)
                {
                    sle.printStackTrace();
                }
                stepVersionID = myObjectInfo.getIterationID();
                myVersionIDCollection.add(stepVersionID);
                myTechnicsCollection.add(myObjectInfo);
            }
            if (verbose)
            {
                System.out.println("cappclients.capp.web.StepIterationsCompareUtil.getSelectObject() end...return: "
                                   + myTechnicsCollection);
            }
            return myTechnicsCollection;
        }
        else
        {
            return null;
        }
    }


    /**
     * ������ѡ�񹤲����ڹ��տ���ID
     * @param technicsBsoID
     */
    public void setTechnicsBsoID(String technicsBsoID)
    {
        this.technicsBsoID = technicsBsoID;
    }
   public String getTechnicsBsoID()
   {
	   return technicsBsoID;
   }

    /**
     * �õ�ѡ�еĶ���İ汾�ŵļ���
     * @return Vector
     */
    public Vector getMyVersionIDCollection()
    {
        return myVersionIDCollection;
    }


    /**
     * ���ѡ�еĶ���Ļ������Եļ��ϣ�ҳ����ѡ�е���BsoIDͨ���������ת�ɶ���
     * @return ��Ҫ�����Եļ���
     */
    public Vector getAttVector() throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.StepIterationsCompareUtil.getAttVector() begin...");
        }
        Vector av = new Vector();
        try{
        Vector tav = new Vector();
        String[][] obj = new String[myTechnicsCollection.size() + 1][12];

        obj[0][0] = "�����";
        obj[0][1] = "��������";
        obj[0][2] = "��������"; //ö��
        obj[0][3] = "�������"; //ö��
        obj[0][4] = "�ӹ�����"; //ö��
        obj[0][5] = "����"; //ö��
        obj[0][6] = "���ռ�ͼͼ��";
        obj[0][7] = "��ͼ�����ʽ"; //ö��
        obj[0][8] = "��������";
        obj[0][9] = "�ӹ���ʱ";
        obj[0][10] = "������ʱ";
        obj[0][11] = "������ʱ";

        for (int j = 1; j < myTechnicsCollection.size() + 1; j++)
        {
            int stepNum = ((QMProcedureIfc) (myTechnicsCollection.get(j - 1))).
                          getStepNumber();
            obj[j][0] = String.valueOf(stepNum);

            obj[j][1] = ((QMProcedureIfc) (myTechnicsCollection.get(j - 1))).
                        getStepName();

            obj[j][2] = ((QMProcedureIfc) (myTechnicsCollection.get(j - 1))).
                        getTechnicsType().getCodeContent();

            obj[j][3] = ((QMProcedureIfc) (myTechnicsCollection.get(j - 1))).
                        getStepClassification().getCodeContent();

            obj[j][4] = ((QMProcedureIfc) (myTechnicsCollection.get(j - 1))).
                        getProcessType().getCodeContent();

            BaseValueIfc codeContent=((QMProcedureIfc) (myTechnicsCollection.get(j - 1))).getWorkShop();
            if(codeContent instanceof CodingIfc)
            {
            obj[j][5] = ((CodingIfc)codeContent).getCodeContent();
            }else if(codeContent instanceof CodingClassificationIfc)
            {
            	obj[j][5] = ((CodingClassificationIfc)codeContent).getCodeSort();
            }else
            {
            	obj[j][5] ="";
            }

            /*if (((QMProcedureIfc) (myTechnicsCollection.get(j - 1))).
                getTechGraphics() != null)
            {
                obj[j][6] = "��";
            }
            else*/
            {
                obj[j][6] = "";

            }
           /* if (((QMProcedureIfc) (myTechnicsCollection.get(j - 1))).
                getDrawingFormat() != null)
            {
                obj[j][7] = ((QMProcedureIfc) (myTechnicsCollection.get(j - 1))).
                            getDrawingFormat().getCodeContent();
            }
            else*/
            {
                obj[j][7] = "";

            }
            Vector con = ((QMProcedureIfc) (myTechnicsCollection.get(j - 1))).
                         getContent();
            if (con != null)
            {
                obj[j][8] = DataDisplay.translate(con);
            }
            else
            {
                obj[j][8] = "";
            }
            obj[j][9] = String.valueOf(((QMProcedureIfc) (myTechnicsCollection.
                    get(j - 1))).getMachineHour());
            obj[j][10] = String.valueOf(((QMProcedureIfc) (myTechnicsCollection.
                    get(j - 1))).getAidTime());
            obj[j][11] = String.valueOf(((QMProcedureIfc) (myTechnicsCollection.
                    get(j - 1))).getStepHour());
        }

        boolean flag = false;

        for (int l = 0; l < 12; l++)
        {
            for (int k = 2; k < selectBsoID.length + 1; k++)
            {
                if (!((obj[k][l]).equals((obj[1][l]))))
                {
                    flag = true;
                    break;
                }
            }
            String[] tempArray = new String[selectBsoID.length + 1];
            if (flag)
            {
                for (int m = 0; m < selectBsoID.length + 1; m++)
                {
                    tempArray[m] = obj[m][l];
                }
                av.add(tempArray);
                flag = false;
            }
        }
        }catch(Exception e)
        {
        	e.printStackTrace();
        	throw new QMException(e);
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.StepIterationsCompareUtil.getAttVector() end...return: "
                    + av);
        }
        return av;
    }


    /**
     * ��ù��򣨲����Ĺ����豸�Ĳ������
     * @return Vector ��Ϣ����
     */
    public Vector getdeEquipmentVector()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.StepIterationsCompareUtil.getdeEquipmentVector() begin...");
        }
        Vector v = new Vector();
        try
        {
            v = helper.getDeEquipment(myTechnicsCollection);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.web.StepIterationsCompareUtil.getdeEquipmentVector() end...retrn: "
                               + v);
        }
        return v;
    }


    /**
     * ��ù��򣨲������ĸ������ԵĲ������
     * @return Vector ��Ϣ����
     */
    public Vector getdeAttrVector()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.StepIterationsCompareUtil.getdeAttrVector() begin...");
        }
        Vector v = new Vector();

        try
        {
            v = helper.getDeAffix(myTechnicsCollection);
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }

        if (verbose)
        {
            System.out.println("cappclients.capp.web.StepIterationsCompareUtil.getdeAttrVector() end...return: "
                               + v);
        }
        return v;
    }
    /**
     * ���ts16949������panel��container�ļ���
     * ��return��Vector container����panelCollection
     * add by wangh on 20070403��
     */
	    public Vector getDeAffixByProcedure(String procedureID) throws
	        QMException {
	      if (procedureID == null || procedureID.equals("")||!ts16949) {
	        return null;
	      }
	      try {
	        PersistService persistService = (PersistService) EJBServiceHelper.
	            getPersistService();
	        QMProcedureIfc procedure = (QMProcedureIfc) persistService.refreshInfo(
	            procedureID);
	        panelCollection.add(procedure.
		            getProcessFlow());
	        panelCollection.add(procedure.
		            getFema());
	        panelCollection.add(procedure.
		            getProcessControl());
	      }
	
	      catch (QMException ex) {
	        ex.printStackTrace();
	        throw ex;
	      }
	      return panelCollection;
	    }
	    /**
	     * ��ñȽϺ�������ӵĵ������Եļ���
	     * @return��Vector v
	     * add by wangh on 20070411
	     */
		public Vector getPanelDeAttrVector()
		    {
		        if (verbose)
		        {
		            System.out.println(
		                    "cappclients.capp.web.StepIterationsCompareUtil.getPanelAttrVector() begin...");
		        }
		        Vector v = new Vector();
		
		        try
		        {
		            v = helper.getDeAffix(panelCollection);
		        }
		        catch (QMException e)
		        {
		            e.printStackTrace();
		        }
		
		        if (verbose)
		        {
		            System.out.println("cappclients.capp.web.StepIterationsCompareUtil.getPanelAttrVector() end...return: "
		                               + v);
		        }
		        return v;
		    }
		/**
		 * add by wangh on20070416
		 */
		public ArrayList getColDeAttrVector(int i)
	    {
	        if (verbose)
	        {
	            System.out.println(
	                    "cappclients.capp.web.StepIterationsCompareUtil.getPanelAttrVector() begin...");
	        }
	        ArrayList panelCompare = new ArrayList();
	
	        try
	        {
	        	panelCompare = helper.getColDeAffix(myTechnicsCollection,i);
	        }
	        catch (QMException e)
	        {
	            e.printStackTrace();
	        }
	
	        if (verbose)
	        {
	            System.out.println("cappclients.capp.web.StepIterationsCompareUtil.getPanelAttrVector() end...return: "
	                               + panelCompare);
	        }
	        
	        return panelCompare;
	    }


    /**
     * ��ù��򣨲������Ĺ����ĵ��Ĳ������
     * @return Vector ��Ϣ����
     */
    public Vector getdeRefVector()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.StepIterationsCompareUtil.getdeRefVector() begin...");
        }
        Vector v = new Vector();
        try
        {
            v = helper.getDeDocForProcedure(myTechnicsCollection);
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.StepIterationsCompareUtil.getdeRefVector() end...return: "
                    + v);
        }
        return v;
    }


    /**
     * ��ù��򣨲������Ĳ�����Դ�����Ĳ������
     * @return Vector ��Ϣ����
     */
    public Vector getdeMaterialVector()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.StepIterationsCompareUtil.getdeMaterialVector() begin...");
        }
        Vector v = new Vector();
        try
        {
            v = helper.getDeMaterialForProcedure(myTechnicsCollection);
        }
        catch (QMException e)
        {
            e.printStackTrace();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.web.StepIterationsCompareUtil.getdeMaterialVector() end...return: "
                               + v);
        }
        return v;
    }


    /**
     * ��ù��򣨲������Ĺ�����װ�Ĳ������
     * @return
     */
    public Vector getdeToolVector()
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.StepIterationsCompareUtil.getdeToolVector() begin...");
        }
        Vector v = new Vector();
        try
        {
            v = helper.getDeTool(myTechnicsCollection);
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.web.StepIterationsCompareUtil.getdeToolVector() end...return: "
                               + v);
        }
        return v;
    }


    /**
     * ��ù��򣨲������Ĺ��������Ĳ������
     * @return
     */
    public Vector getdeProcedureVector(String technicsBsoID)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.web.StepIterationsCompareUtil.getdeProcedureVector() begin...");
        }
        Vector v = new Vector();
        try
        {
            v = helper.getDeProcedureByProcedure(myTechnicsCollection, technicsBsoID);
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }
        if (verbose)
        {
            System.out.println("cappclients.capp.web.StepIterationsCompareUtil.getdeProcedureVector() end...return: "
                               + v);
        }
        return v;
    }

    /**
	 * ���򸽼������г������ԵĲ������
	 * 
	 * @return 
	 * ���⣨1��20080925 �촺Ӣ�޸� �޸�ԭ�򣺱ȽϹ��򸽼���Ϣ�еĳ������� 
	 */
	public ArrayList getGroupAttrDeVector() {
		if (verbose) {
			System.out
					.println("cappclients.capp.web.StepIterationsCompareUtil.getPanelAttrVector() begin...");
		}
		// ArrayListgetColDeAttrVector groupAttrCompare = new ArrayList();
		ArrayList groupAttrCompare = new ArrayList();
		try {
			groupAttrCompare = helper.getGroupDeAffix(myTechnicsCollection);
		} catch (QMException e) {
			e.printStackTrace();
		}

		if (verbose) {
			System.out
					.println("cappclients.capp.web.StepIterationsCompareUtil.getPanelAttrVector() end...return: "
							+ groupAttrCompare);
		}

		return groupAttrCompare;
	}

	/**
	 * �õ�������Ϣ�еĳ�������
	 * 
	 * @param procedureID
	 *            ����id
	 * @return ������Ϣ�������Լ���
	 * @throws QMException
	 * ���⣨1��20080925 �촺Ӣ�޸� �޸�ԭ�򣺱ȽϹ��򸽼���Ϣ�еĳ������� 
	 */
	public ArrayList getGroupAttr(String procedureID) throws QMException {
		ArrayList nameAndValues = null;
		try {
			PersistService service = (PersistService) EJBServiceHelper
					.getPersistService();
			QMProcedureIfc procedure = (QMProcedureIfc) service
					.refreshInfo(procedureID);
			ExtendAttContainer container = null;

			container = procedure.getExtendAttributes();

			nameAndValues = helper.getExtendGroupAtt(container);
			// System.out.println("nameAndValues:"+nameAndValues);

		} catch (QMException ex) {
			ex.printStackTrace();
			throw ex;
		}
		return nameAndValues;

	}

}
