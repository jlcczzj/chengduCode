/** 生成程序StepIterationsCompareUtil.java	1.1  2003/08/08
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * <p>Title:工序的版序历史比较 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 * 问题（1）20080925 徐春英修改 修改原因：比较工序附加信息中的成组属性
 */

public class StepIterationsCompareUtil
{
    /**所选择对象的ID*/
    private String[] selectBsoID;


    /**持久化服务*/
    private PersistService service;


    /**业务对象（工序）*/
    private QMProcedureIfc myObjectInfo;
    public boolean flag = false;


    /**工序的版本号*/
    public String stepVersionID;


    /**版本号集合*/
    public Vector myVersionIDCollection = new Vector();


    /**对象集合*/
    public Vector myTechnicsCollection = new Vector();
    //add by wangh on 20070404
    public Vector panelCollection = new Vector();
    
    private CappServiceHelper helper = new CappServiceHelper();

    private String technicsBsoID;
    public ArrayList panelCompare = new ArrayList();
    public ArrayList eachPanel = new ArrayList();

    /**代码测试变量*/
    private static boolean verbose = true;//(RemoteProperty.getProperty(
//            "com.faw_qm.cappclients.verbose", "true")).equals("true");
    //add by wangh on 20070426
    private static boolean ts16949 = (RemoteProperty.getProperty(
            "TS16949", "true")).equals("true");

    /**
     * 构造函数
     */
    public StepIterationsCompareUtil()
    {
    }


    /**
     * 设置选择的所有对象的BsoID
     * @param sBsoID 对象的BsoID的数组
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
     * 获得瘦客户端头部的显示信息(工序编号、名称、版本号、图标)
     * @param partBsoID 对象的BsoID
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
     * 获得选中的对象的集合，页面上选中的是BsoID通过这个方法转成对象
     * @return  选中的对象的集合
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
     * 设置所选择工步所在工艺卡的ID
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
     * 得到选中的对象的版本号的集合
     * @return Vector
     */
    public Vector getMyVersionIDCollection()
    {
        return myVersionIDCollection;
    }


    /**
     * 获得选中的对象的基本属性的集合，页面上选中的是BsoID通过这个方法转成对象
     * @return 必要的属性的集合
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

        obj[0][0] = "工序号";
        obj[0][1] = "工序名称";
        obj[0][2] = "工序种类"; //枚举
        obj[0][3] = "工序类别"; //枚举
        obj[0][4] = "加工类型"; //枚举
        obj[0][5] = "部门"; //枚举
        obj[0][6] = "工艺简图图形";
        obj[0][7] = "简图输出形式"; //枚举
        obj[0][8] = "工艺内容";
        obj[0][9] = "加工工时";
        obj[0][10] = "辅助工时";
        obj[0][11] = "单件工时";

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
                obj[j][6] = "有";
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
     * 获得工序（步）的关联设备的差异情况
     * @return Vector 信息返回
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
     * 获得工序（步）卡的附加属性的差异情况
     * @return Vector 信息返回
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
     * 获得ts16949新增加panel的container的集合
     * ＠return　Vector container集合panelCollection
     * add by wangh on 20070403　
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
	     * 获得比较后的新增加的单个属性的集合
	     * @return　Vector v
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
     * 获得工序（步）卡的关联文档的差异情况
     * @return Vector 信息返回
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
     * 获得工序（步）卡的材料资源关联的差异情况
     * @return Vector 信息返回
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
     * 获得工序（步）卡的关联工装的差异情况
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
     * 获得工序（步）卡的关联工步的差异情况
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
	 * 工序附加属性中成组属性的差异情况
	 * 
	 * @return 
	 * 问题（1）20080925 徐春英修改 修改原因：比较工序附加信息中的成组属性 
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
	 * 得到附加信息中的成组属性
	 * 
	 * @param procedureID
	 *            工序id
	 * @return 附加信息成组属性集合
	 * @throws QMException
	 * 问题（1）20080925 徐春英修改 修改原因：比较工序附加信息中的成组属性 
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
