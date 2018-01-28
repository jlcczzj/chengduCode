package com.faw_qm.part.util;

import java.util.Vector;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;

public class PartWebHelper {
	
	
	
	 /**
	   * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的物料清单信息。
	   * 
	   * @param partID String 指定零部件的bsoID
	   * @param attrNames String 定制的属性，可以为空
	   * @
	   * @return Vector 返回结果是vector,其中vector中的每个元素都是一个集合：
	   *<br> 0：当前part的BsoID；
	   *<br> 1：当前part所在的级别，转化为字符型；
	   *<br> 2：当前part的编号；
	   * <br>3：当前part的名称；
	   * <br>4：当前part被最顶层部件使用的数量，转化为字符型；
	   * <br>5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
	   * <br>             ：如果定制了属性：按照所有定制的属性加到结果集合中。
	   */
	public static Vector getMaterialList(String partID, String attrNames) 
	   {
	        attrNames = attrNames.trim();
	        if(attrNames == null || attrNames.length() == 0)
	        {
	            Vector result = new Vector();
	            return result;
	        }
	        int newLength = 0;
	        for(int i = 0;i < attrNames.length();i++)
	        {
	            if(attrNames.charAt(i) == ',')
	            {
	                newLength++;
	            }
	        }
	        String[] attrNames1 = new String[newLength];
	        for(int i = 0, j = 0, k = 0;i < attrNames.length();i++)
			{
			    if(attrNames.charAt(i) == ',')
			    {
			        String str = attrNames.substring(j, i);
			        attrNames1[k] = str;
			        k++;
			        j = i + 1;
			    }
			}
	        PersistService pService;
	        Vector v =null;
			try {
				pService = (PersistService)EJBServiceHelper.getPersistService();
			
	        QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partID);
	         v = PartServiceRequest.setMaterialList(partIfc, attrNames1);
			} catch (QMException e) {
				e.printStackTrace();
			}
	        return v;
	   }
	
	/**
	   * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。
	   *
	   * @param partID :String 指定的零部件的bsoID.
	   * @param attrNames :String 定制要输出的属性集合；如果为空，则按标准版输出。
	   * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理；
	   * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理；
	   * @
	   * @return Vector  在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
	   * <br>1、如果不定制属性：
	   * BsoID，号码、名称、是（否）可分（"true", "false"）、数量（转化为字符型）、版本和视图；
	   *<br> 2、如果定制属性：
	   * BsoID，号码、名称、是（否）可分("true", "false")、数量、其他定制属性。
	   */
	  public static Vector getBOMList(String partID, String attrNames, String source, String type)
	      
	  {
//	    Vector allAttrNames = new Vector();
	    String[] attrNames1 =  attrNames.split(",");
	    String[] affixAttrNames = null;
//	    if(attrNames != null && attrNames.length() > 0)
//	    {
//	      allAttrNames = getTableHeader(attrNames);
//	      if(allAttrNames != null && allAttrNames.size() > 0)
//	      {
//	        attrNames1 = (String[])allAttrNames.elementAt(0);
//	        affixAttrNames = (String[])allAttrNames.elementAt(1);
//	      }
//	    }
	    PersistService pService;
	    QMPartIfc partIfc =null;
	    PartConfigSpecIfc partConfigSpecIfc=null;
		try {
			pService = (PersistService)EJBServiceHelper.getPersistService();
		
	     partIfc = (QMPartIfc)pService.refreshInfo(partID);
	     partConfigSpecIfc = (PartConfigSpecIfc)PartServiceRequest.findPartConfigSpecIfc();
		
	   
	    return PartServiceRequest.setBOMList(partIfc, attrNames1, affixAttrNames, source, type, partConfigSpecIfc);
	
		} catch (QMException e) {
			e.printStackTrace();
		}
		return null;
		}
	  
	  

}
