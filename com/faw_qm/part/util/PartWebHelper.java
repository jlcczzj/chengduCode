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
	   * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿���������嵥��Ϣ��
	   * 
	   * @param partID String ָ���㲿����bsoID
	   * @param attrNames String ���Ƶ����ԣ�����Ϊ��
	   * @
	   * @return Vector ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
	   *<br> 0����ǰpart��BsoID��
	   *<br> 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
	   *<br> 2����ǰpart�ı�ţ�
	   * <br>3����ǰpart�����ƣ�
	   * <br>4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
	   * <br>5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
	   * <br>             ��������������ԣ��������ж��Ƶ����Լӵ���������С�
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
	   * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ��
	   *
	   * @param partID :String ָ�����㲿����bsoID.
	   * @param attrNames :String ����Ҫ��������Լ��ϣ����Ϊ�գ��򰴱�׼�������
	   * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
	   * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
	   * @
	   * @return Vector  �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
	   * <br>1��������������ԣ�
	   * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true", "false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
	   *<br> 2������������ԣ�
	   * BsoID�����롢���ơ��ǣ��񣩿ɷ�("true", "false")�������������������ԡ�
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
