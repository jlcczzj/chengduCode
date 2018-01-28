package com.faw_qm.cappclients.other;

import java.util.ArrayList;
import java.util.Vector;

import com.buildnum.ejb.service.numService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.util.EJBServiceHelper;

public class resourceUtil
{
  
  
	  public static String buildDocNumber(String cfname)throws QMException
	  {
	  	//文档编号由两部分组成，第一部分是分类码 每二部分是顺序号
	
	  	numService sservice = (numService)EJBServiceHelper.getService("numService");
	  	String docNumber = sservice.buildSerialNum(cfname);
	  	return docNumber;
	  }
	 
	  
}
