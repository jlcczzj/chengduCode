package com.faw_qm.cappclients.conscapproute.controller;

import org.apache.poi.hssf.usermodel.HSSFCell;
import java.io.InputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import javax.swing.JOptionPane;
import java.net.URL;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.HSSFRow;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;

import java.util.Vector;
import java.io.FileOutputStream;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;

import java.util.*;

import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.cappclients.conscapproute.web.ReportLevelOneUtil;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 * SS1 修改导出excel模版名称对应错误问题 pante 20130730
 * SS2 增加轴齿中心模板 liuyang 2014-1-13
 * SS3 轴齿中心用户增加毛坯状态属性  liuyang 2014-1-14
 * SS4 判断轴齿中心用户  liuyang 2014-1-14
 * SS5 调整轴齿中心模板表格 liuyang 2014-1-14
 * SS6  轴齿中心说明分为四行 liuyang 2014-4-17
 * SS7 轴齿中心导出报表选中第一个sheet页 Liuyang 2014-4-23
 * SS8 显示备注 Liuyang 2014-4-24
 * SS9 艺准模版去掉版本列，第一列增加序号，零件号和版本合并到一列。 liunan 2015-5-5
 * SS10 变速箱艺毕，增加第二列显示“完毕否”为W。 liunan 2016-3-27
 * SS11 升级poi到3.6，注释//cell.setEncoding(cell.ENCODING_UTF_16); liunan 2016-4-11
 */
public class ExportExcel {

  private String makeDate = "";
  private String projectName = "";
  private String routelistNumber = "";
  private Vector result = new Vector();
  private static final boolean verbose = false;
  private String shuoming = "";
  private String shuoming1 = "" ;
  private String pizhunInfo = "";
  private String shenheInfo = "";
  private String jiaoduiInfo = "";
  private String bianzhiInfo = "";
  private String faWang = "";
  //CCBegin by liunan 2012-6-4 工艺路线状态。多了艺废情况，不能再用boolean型。
  //private static boolean isYshizhun = false ;
  private static String routeListState = "" ;
  //CCEnd by liunan 2012-6-4
  //CCBegin SS4
  private String comp="";
  //CCEnd SS4
//CCBegin SS6
  private String shuoming2="";
  private String shuoming3="";
  //CCEnd SS6
  public ExportExcel() {
  }

  /**
   * 收集报表数据
   * @param routeListID String
   * @param IsExpandByProduct 是否按车展开（暂时默认总为真）
   * @throws QMException
   */
  public void gatherExportData(String routeListID) throws
      QMException
  {
      ArrayList allInformationColl = new ArrayList();
      try
      {
          Class[] c =
              {
              String.class};
          Object[] oo =
              {
              routeListID};
          allInformationColl = (ArrayList) CappRouteAction.
              useServiceMethod(
              "consTechnicsRouteService", "gatherExportData", c, oo);
        //CCBegin SS4
          RequestServer server = RequestServerFactory.getRequestServer();
          StaticMethodRequestInfo info1= new StaticMethodRequestInfo();
      	  info1.setClassName("com.faw_qm.cappclients.conscapproute.util.RouteClientUtil");
          info1.setMethodName("getUserFromCompany");
          Class[] classes = {};
          info1.setParaClasses(classes);
          Object[] objs = {};
          info1.setParaValues(objs);
          comp=(String)server.request(info1);
          //CCEnd SS4
          
      }
      catch(Exception e)
      {
          e.printStackTrace();
      }
    String[] header = (String[]) allInformationColl.get(0);
    //CCBegin by wanghonglian 2008-09-05
    //去掉项目中的“用于产品”的属性部分，直接显示艺准的名称
    //源代码如下：
    //projectName = "项目名称:" + header[3]+" "+header[0];
       projectName = "项目名称:" +header[0];
    //CCEnd by wanghonglian 2008-09-05
    //CCBegin by wanghonglian 2008-06-04
    //判断是否为艺试准状态
    boolean isYsz =isYshizhun(routeListID);
    if(isYsz)
    {
    	routelistNumber = header[1];
    }
    else 
    	routelistNumber = header[1];
    //CCEnd by wanghonglian 2008-06-04
    //routelistNumber = "编号："+header[1];
      makeDate = "编制日期:" + header[2];

    //说明
    Collection tail2 = (Collection) allInformationColl.get(3);

    if (tail2 != null && tail2.size() > 0) {
      Object[] des = tail2.toArray();
      shuoming = des[0].toString();
      if(des.length!=1)
      {
       shuoming1 = des[1].toString();
       //CCBegin by wanghonglian 2008-06-05
       //将说明信息框中以“说明：”开始的信息作为路线代码说明信息
       //CCBegin by wanghonglian 2008-09-05
       //将整个说明信息写入到excel文件中,不需要去掉前面的“说明：”
      /* if(shuoming1.indexOf("说明：")!=-1)
       {
    	   shuoming1 = shuoming1.substring(shuoming1.indexOf("说明：")+3);
       }*/
       //CCEnd by wanghonglian 2008-09-05
       //CCEnd by wanghonglian 2008-06-05 
      }
      //CCBegin SS6
      if(comp.equals("zczx")){
    	  shuoming2=des[2].toString();
    	  shuoming3=des[3].toString();
      }
      //CCEnd SS6
      
    }
    ////////////////////添加流程信息  ——刘明 20061213
    Vector wf = (Vector) allInformationColl.get(4);
    if (wf != null && wf.size() > 0) {

      //////////////////审核///////////////////////////
      ArrayList pizhun = (ArrayList) wf.elementAt(0);
      //CCBegin by wanghonglian 2008-06-04
      //增加校对者和批准者的信息显示
      ArrayList shenhe = (ArrayList) wf.elementAt(1);
      ArrayList jiaodui = (ArrayList) wf.elementAt(2);
      StringBuffer pizhunBuffer = new StringBuffer("批准：");
      if (pizhun != null && pizhun.size() != 0) {
        for (int i = 0; i < pizhun.size(); i++) {
          Object p1 = pizhun.get(i);
          if (p1 != null) {
        	  pizhunBuffer.append(p1.toString() + " ");
          }
        }
      }
      pizhunInfo = pizhunBuffer.toString();
      StringBuffer shenheBuffer = new StringBuffer("审核：");
      if (shenhe != null && shenhe.size() != 0) {
        for (int i = 0; i < shenhe.size(); i++) {
          Object p1 = shenhe.get(i);
          if (p1 != null) {
            shenheBuffer.append(p1.toString() + " ");
          }
        }
      }
      shenheInfo = shenheBuffer.toString(); 
      StringBuffer jiaoduiBuffer = new StringBuffer("校对：");
      if (jiaodui != null && jiaodui.size() != 0) {
        for (int i = 0; i < jiaodui.size(); i++) {
          Object p1 = jiaodui.get(i);
          if (p1 != null) {
        	  jiaoduiBuffer.append(p1.toString() + " ");
          }
        }
      }
      jiaoduiInfo = jiaoduiBuffer.toString();
      //CCEnd by wanghonglian 2008-06-04
    
      ////////////////////////////////////////////
      bianzhiInfo = "编制：" + wf.elementAt(3).toString();
    }
    ////////////////////////////////////////////////

    String tail1 = (String) allInformationColl.get(2);
    faWang = "发往单位:" + tail1;
    Collection informationColl = (Collection) allInformationColl.get(1);
    for (Iterator iter = informationColl.iterator(); iter.hasNext(); )
    {
      Object[] arrayObjs = (Object[]) iter.next();
    //  String xuhao = (String) arrayObjs[0];
      String changeSign = (String) arrayObjs[0];//zczx是序号
      String partNum = (String) arrayObjs[1];//zczx是更改标记
      String version = (String) arrayObjs[2];//zczx是编号+版本
      //partNum = partNum + version;
      String partName = (String) arrayObjs[3];
      String countInProduct = (String) arrayObjs[4];
      //if(changeSign.equals("G"))
      //{
        //countInProduct = "";
      //}
      String partMakeRoute = (String) arrayObjs[5];
      String remark ="";
//CCBegin SS8
//      if(arrayObjs.length==9)
//      {
      //CCEnd SS8
         remark = (String) arrayObjs[8];
         //CCBegin SS8
//      }
         //CCEnd SS8
      //CCBegin SS3
      String stockID=(String)arrayObjs[9];
      //CCEnd SS3
      //CCBegin by wanghonglian 2008-06-05
      //此代码封装输出表格的信息。去掉序号，增加版本信息的显示。原代码如下：
/*      String[] array1 = {
              xuhao, changeSign, partNum, partName, countInProduct,
              partMakeRoute, "", (String) arrayObjs[7],
              "",remark};
          result.addElement(array1);*/
   
      //CCBegin by wanghonglian 2008-09-05
      //解决导出的表格中，装配路线被写到备注一栏的问题
      //CCBegin SS3
//      String[] array1 = {
//              changeSign, partNum, version,partName, countInProduct,
//             partMakeRoute, (String) arrayObjs[6],remark};
      if(comp.equals("zczx")){
    	  String[] array1 = {
                  changeSign, partNum, version,partName, countInProduct,stockID,
                 partMakeRoute, (String) arrayObjs[6],remark};
    	  result.addElement(array1);
      }else{
      	//CCBegin SS10
      	if(routeListState.equals("yb"))
      	{
      		String[] array1 = {
                   changeSign, "W", partNum, version,partName, countInProduct,
                  partMakeRoute, (String) arrayObjs[6],remark};
    	    result.addElement(array1);
      	}
      	else
      	{
      	//CCEnd SS10
    	  String[] array1 = {
                   changeSign, partNum, version,partName, countInProduct,
                  partMakeRoute, (String) arrayObjs[6],remark};
    	  result.addElement(array1);
      	//CCBegin SS10
      	}
      	//CCEnd SS10
      }
        
          //CCEnd SS3
             
            //CCEnd by wanghonglian 2008-06-05     
    }
  }
  
  /**
   * 根据给定路线，获得它对应的状态
   * 判断它的状态是否为艺试准状态
   * @author 王红莲
   * @version 1.0
   */
  
  private static boolean isYshizhun(String routeListID)throws
      QMException
{
	boolean isYsz = false ;
	 TechnicsRouteListIfc listInfo = null;
    try
    {
        Class[] c =
            {
            String.class};
        Object[] oo =
            {
            routeListID};
        listInfo = (TechnicsRouteListIfc) CappRouteAction.
            useServiceMethod(
            "PersistService", "refreshInfo", c, oo);
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
	String state = listInfo.getRouteListState();
	System.out.println("state============"+state);
	if(state != null && (state.equalsIgnoreCase("试制")||state.equalsIgnoreCase("艺试准")))
	{
		isYsz = true ;
		//CCBegin by liunan 2012-6-4 工艺路线状态。多了艺废情况，不能再用boolean型。
		routeListState = "sz";
		//CCEnd by liunan 2012-6-4
	}
	//CCBegin by liunan 2012-6-4 工艺路线状态。多了艺废情况，不能再用boolean型。
	//isYshizhun = isYsz ;
	else if(state != null && state.equalsIgnoreCase("艺准"))
	{
		
		routeListState = "yz";
	}
	else if(state != null && (state.equalsIgnoreCase("艺废")||state.equalsIgnoreCase("废弃")))
	{
		routeListState = "yf";
	}
	else if(state != null && state.equalsIgnoreCase("临准"))
	{
		routeListState = "lz";
	}
	else if(state != null && state.equalsIgnoreCase("前准"))
	{
		routeListState = "qz";
	}
	else if(state != null && state.equalsIgnoreCase("艺毕"))
	{
		routeListState = "yb";
	}
	//CCEnd by liunan 2012-6-4
	return isYsz ;
}

  
  /**
   * 输出报表
   *
   * @param path
   * @roseuid 42C363440215
   */
  public boolean printFile(String path) {
	  //CCBegin by wanghonglian 2008-06-04
	  //判断是否为艺试准状态，如果是艺试准状态则选择模板yszrouteListTemplate.xls，否则选择模板routeListTemplate.xls
	  String url = "";
	  //CCBegin by liunan 2012-6-4 工艺路线状态。多了艺废情况，不能再用boolean型。
	  //if(isYshizhun)
	  System.out.println("routeListState=============="+routeListState);
	  if(routeListState.equals("sz"))
	  //CCEnd by liunan 2012-6-4
	  {
		  //CCBegin SS2
			if(comp.equals("zczx")){
				url = "http://" +
		        RemoteProperty.getProperty("server.hostName", "") +
		        RemoteProperty.getProperty("routeListTemplate",
		                                   "/PhosphorPDM/template/") +"zcsz.xls";
			}else{
		    //CCEnd SS2
		    url = "http://" +
	        RemoteProperty.getProperty("server.hostName", "") +
	        RemoteProperty.getProperty("routeListTemplate",
	                                   "/PhosphorPDM/template/") +
	        "sz.xls";
			}
	  }
	  //CCBegin by liunan 2012-6-4 工艺路线状态。多了艺废情况，不能再用boolean型。
	  //else
	  else if(routeListState.equals("yz"))
	  //CCEnd by liunan 2012-6-4
	  //CCEnd by wanghonglian 2008-06-04
    {
		  //CCBegin SS2
		if(comp.equals("zczx")){
			url = "http://" +
	        RemoteProperty.getProperty("server.hostName", "") +
	        RemoteProperty.getProperty("routeListTemplate",
	                                   "/PhosphorPDM/template/") +"zcyz.xls";
		}else{
	    //CCEnd SS2
		url = "http://" +
        RemoteProperty.getProperty("server.hostName", "") +
        RemoteProperty.getProperty("routeListTemplate",
                                   "/PhosphorPDM/template/") +
                                   //SSBegin SS1
        "yz.xls";
		}
		//SSEnd SS1
    }
	  //CCBegin by liunan 2012-6-4 工艺路线状态。多了艺废情况，不能再用boolean型。
	  else if(routeListState.equals("yf"))
 {
			// CCBegin SS2
			if (comp.equals("zczx")) {
				url = "http://"
						+ RemoteProperty.getProperty("server.hostName", "")
						+ RemoteProperty.getProperty("routeListTemplate",
								"/PhosphorPDM/template/") + "zcyf.xls";
			} else {
				// CCEnd SS2
				url = "http://"
						+ RemoteProperty.getProperty("server.hostName", "")
						+ RemoteProperty.getProperty("routeListTemplate",
								"/PhosphorPDM/template/") + "yf.xls";
			}
		}
	  else if(routeListState.equals("yb"))
	    {
		  //CCBegin SS2
			if(comp.equals("zczx")){
				url = "http://" +
		        RemoteProperty.getProperty("server.hostName", "") +
		        RemoteProperty.getProperty("routeListTemplate",
		                                   "/PhosphorPDM/template/") +"zcyb.xls";
			}else{
		    //CCEnd SS2
			url = "http://" +
	        RemoteProperty.getProperty("server.hostName", "") +
	        RemoteProperty.getProperty("routeListTemplate",
	                                   "/PhosphorPDM/template/") +
	        "yb.xls";
			}
	    }
	  else if(routeListState.equals("lz"))
	    {
		  //CCBegin SS2
			if(comp.equals("zczx")){
				url = "http://" +
		        RemoteProperty.getProperty("server.hostName", "") +
		        RemoteProperty.getProperty("routeListTemplate",
		                                   "/PhosphorPDM/template/") +"zclz.xls";
			}else{
		    //CCEnd SS2
			url = "http://" +
	        RemoteProperty.getProperty("server.hostName", "") +
	        RemoteProperty.getProperty("routeListTemplate",
	                                   "/PhosphorPDM/template/") +
	        "lz.xls";
			}
	    }
	  else if(routeListState.equals("qz"))
	    {
		  //CCBegin SS2
			if(comp.equals("zczx")){
				url = "http://" +
		        RemoteProperty.getProperty("server.hostName", "") +
		        RemoteProperty.getProperty("routeListTemplate",
		                                   "/PhosphorPDM/template/") +"zcqz.xls";
			}else{
		  //CCEnd SS2
			url = "http://" +
	        RemoteProperty.getProperty("server.hostName", "") +
	        RemoteProperty.getProperty("routeListTemplate",
	                                   "/PhosphorPDM/template/") +
	        "qz.xls";
			}
	    }
	  //CCEnd by liunan 2012-6-4
    System.out.println("URL = "+url);
    InputStream stream = null;
    try {
      URL aurl = new URL(url);
      stream = aurl.openStream();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }

    POIFSFileSystem fs = null;
    HSSFWorkbook wb = null;
    try {
      fs = new POIFSFileSystem(stream);
      wb = new HSSFWorkbook(fs);
    } 
    catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    	HSSFSheet sheet = wb.getSheetAt(0); //获得第一个页面
  
    	HSSFRow row = sheet.getRow(2);
    	HSSFCell cell = null;
    	//CCBegin SS5
    	if(comp.equals("zczx")){
    		cell = row.getCell( (short) 8); //第2行第9列 编制日期
    	}else{
    	if(!routeListState.equals("yb"))
    		cell = row.getCell( (short) 7); //第2行第8列 编制日期
    	else
    		cell = row.getCell( (short) 8); //第2行第9列 编制日期
    	}
    	//CCEnd SS5
    	//cell.setEncoding(cell.ENCODING_UTF_16);
    	cell.setCellValue(makeDate);

    	row = sheet.getRow(3);
    	cell = row.getCell( (short) 0);
    	//cell.setEncoding(cell.ENCODING_UTF_16);
    	cell.setCellValue(projectName); //第3行0列 项目名称

    	row = sheet.getRow(20);
    	cell = row.getCell( (short) 0);
    	//cell.setEncoding(cell.ENCODING_UTF_16);
    	cell.setCellValue(shuoming); //第21行0列 说明

    	row = sheet.getRow(21);
    	cell = row.getCell( (short) 0);
    	//cell.setEncoding(cell.ENCODING_UTF_16);
    	cell.setCellValue(shuoming1); //第22行0列 说明1,
    	//CCBegin SS6
        if(comp.equals("zczx")){
        	row = sheet.getRow(22);
        	cell = row.getCell( (short) 0);
        	//cell.setEncoding(cell.ENCODING_UTF_16);
        	cell.setCellValue(shuoming2); //第23行说明2
        	
        	row = sheet.getRow(23);
        	cell = row.getCell( (short) 0);
        	//cell.setEncoding(cell.ENCODING_UTF_16);
        	cell.setCellValue(shuoming3); //第24行0列 路线代码说明

        	row = sheet.getRow(24);
        	cell = row.getCell( (short) 0);
        	//cell.setEncoding(cell.ENCODING_UTF_16);
        	for(int n=0;n<13-pizhunInfo.trim().length();n++){
        		pizhunInfo+=" ";
        	}
        	for(int n=0;n<13-shenheInfo.trim().length();n++){
        		shenheInfo+=" ";
        	}
        	for(int n=0;n<13-jiaoduiInfo.trim().length();n++){
        		jiaoduiInfo+=" ";
        	}
        	for(int n=0;n<13-bianzhiInfo.trim().length();n++){
        		bianzhiInfo+=" ";
        	}
        	String str=pizhunInfo+shenheInfo+jiaoduiInfo
        	            +bianzhiInfo+routelistNumber;
        	cell.setCellValue(str);
        	
        	row = sheet.getRow(25);
			cell = row.getCell((short) 0);
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(faWang); // 第26行0列 发往单位

        	
		} else {
			//CCEnd SS6
			row = sheet.getRow(22);
			cell = row.getCell((short) 0);
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(pizhunInfo); // 第23行0列 批准

			// CCBegin by wanghonglian 2008-06-04
			// 调整表格中的信息显示行和列
			cell = row.getCell((short) 2);
			//CCBegin SS10
			if(routeListState.equals("yb"))
			{
				cell = row.getCell((short) 4);
			}
			//CCEnd SS10
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(shenheInfo); // 第23行2列 审核

			cell = row.getCell((short) 4);
			//CCBegin SS10
			if(routeListState.equals("yb"))
			{
				cell = row.getCell((short) 5);
			}
			//CCEnd SS10
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(jiaoduiInfo); // 第23行4列 校对

			// CCEnd by wanghonglian 2008-06-04
			cell = row.getCell((short) 6);
			//CCBegin SS10
			if(routeListState.equals("yb"))
			{
				cell = row.getCell((short) 7);
			}
			//CCEnd SS10
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(bianzhiInfo); // 第23行5列 编制

			cell = row.getCell((short) 7);
			//CCBegin SS10
			if(routeListState.equals("yb"))
			{
				cell = row.getCell((short) 8);
			}
			//CCEnd SS10
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(routelistNumber); // 第26行7列 艺准号

			row = sheet.getRow(23);
			cell = row.getCell((short) 0);
			//cell.setEncoding(cell.ENCODING_UTF_16);
			cell.setCellValue(faWang); // 第27行0列 发往单位

		}
    	System.out.println("result----------------"+result.size());
    	//处理汇总结果
    	if (result != null && result.size() > 0) {
    		System.out.println("ssssssssss----------------");
    		int pageNumber = 0;
    		int psize = result.size();
    		if (psize % this.getRowNumber() == 0) {
    			pageNumber = psize / this.getRowNumber();
    		}
    		else {
    			pageNumber = 1 + (psize / this.getRowNumber());
    		}
    		HSSFSheet sheet1 = null;
    		if(pageNumber>1)
    		{
    			sheet1 = wb.cloneSheet(0); //缓存空页面
    		}
    		int page = 0;
    		for (int i = 0; i < result.size(); i++) {
    			Object[] o = (Object[]) result.elementAt(i);
				if (i % this.getRowNumber() == 0) { // 余数为0,即整除
					page++;
					if (page == 1) {
						
						// CCBegin SS5
						if (comp.equals("zczx")) {
							row = sheet.getRow(26);
							cell = row.getCell((short) 8);
							//cell.setEncoding(cell.ENCODING_UTF_16);
							cell.setCellValue("共" + pageNumber + "页  "
									+ "第1页  ");
						} else {
							row = sheet.getRow(24);
							if (!routeListState.equals("yb"))
								cell = row.getCell((short) 7);
							else
								cell = row.getCell((short) 2);
							//cell.setEncoding(cell.ENCODING_UTF_16);
							cell.setCellValue("共" + pageNumber + "页  "
									 +  "第1页  ");
						}
						// CCEnd SS5
					} else if (page > 1) {
						sheet = sheet1;
						wb.setSheetName(page - 1, "Sheet" + page);
						//CCBegin SS7
						if (comp.equals("zczx")) {
						sheet1.setSelected(false);
						}
						//CCEnd SS7
						if (page < pageNumber) {
							sheet1 = wb.cloneSheet(page - 1);
						}
					
						// CCBegin SS5
						if (comp.equals("zczx")) {
							row = sheet.getRow(26);
							cell = row.getCell((short) 8);
							//cell.setEncoding(cell.ENCODING_UTF_16);
							cell.setCellValue("共" + pageNumber + "页  "
									+ "第"
									+ page + "页");
					
						} else {
							// CCEnd SS5
							row = sheet.getRow(24);
							if (!routeListState.equals("yb"))
								cell = row.getCell((short) 7);
							else
								cell = row.getCell((short) 2);
							//cell.setEncoding(cell.ENCODING_UTF_16);
							cell.setCellValue("共" + pageNumber + "页  " + "第"
									+ page + "页");
						}
						
					}
				
				}
    			if (verbose) {
    				System.out.println("i=" + i + "    page=" + page);
    			}

    			int rowIndex = 5 + i % this.getRowNumber();
    			row = sheet.getRow(rowIndex);
    			if (verbose) {
    				System.out.println("row=" + rowIndex);

    			}

    			for (int j = 0; j < this.getColNumber(); j++) {
    				cell = row.getCell( (short) j);
    				//cell.setEncoding(cell.ENCODING_UTF_16);
    				if (o[j] == null) {
    					cell.setCellValue("");
    				}
    				else {
    					cell.setCellValue(o[j].toString());
    				}
    			}
    		}
    	}
        //CCBegin SS7
		if (comp.equals("zczx")) {
			HSSFSheet sheet1 = wb.getSheetAt(0); // 获得第一个页面
			wb.setSheetName(0, "Sheet1");
			sheet1.setSelected(true);
		}
    	//CCEnd SS7

    try {
      FileOutputStream out = new FileOutputStream(path);
      wb.write(out);
      return true;
    }
    catch (Exception ex1) {
      ex1.printStackTrace();
      return false;
    }

  }

  /**
   * 列表的列数
   *
   * @return int
   * @roseuid 42C3634401EC
   */
  private int getColNumber() {
    return 8;
  }

  /**
   * 每页的零件行数
   *
   * @return int
   * @roseuid 42C3634401E3
   */
  private int getRowNumber() {
    return 15;
  }

}
