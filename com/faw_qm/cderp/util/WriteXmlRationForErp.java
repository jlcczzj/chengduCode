package com.faw_qm.cderp.util;

import org.w3c.dom.*;
import org.apache.crimson.tree.XmlDocument;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.util.*;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.ration.model.AssMaterialRationIfc;
import com.faw_qm.ration.model.MaterialRationListIfc;
import com.faw_qm.ration.model.RationListPartLinkIfc;
import com.faw_qm.resource.support.model.QMMaterialIfc;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;

//
import java.math.BigDecimal;
/**
 * <p>Title: 写xml发布到erp系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明公司</p>
 * @author 刘家坤
 * @version 1.0
 * SS1 添加成都erp接口属性 刘家坤 20160823
 */
public class WriteXmlRationForErp {

 
  private static final String exportAssisRationListToErp = RemoteProperty.
  getProperty(
  "com.faw_qm.cderp.util.AssisRationlisttoerp",
  "/phosphor/support/loadFiles/xml/erpFiles");

  //辅助材料标识
  private static final String MATERIAL = "material";
  //材料bsoid
  private static final String MATERIALBSOID = "materialBsoID";
  //xml的属性值
  private static final String PRODUCT1 = "assistrationproduct";
  private static final String PRIMARYPART = "primarypart";

  //定额类型
  private static final String RATIONTYPE = "rationType";

  //部门
  private static final String WORKSHOP = "workShop";

  //零件编号
  private static final String PARTNUM = "partNum";

  //零件名称
  private static final String PARTNAME = "partName";


  //采用状态
  private static final String ADOPTSTATE = "adoptState";

  //材料编号
  private static final String MATERIALNUM = "materialNum";


  //材料规格
  private static final String MATERIALGUIGE = "materialGuiGe";

  //材料牌号
  private static final String MATERIALPAIHAO = "materialPaihao";

  //材料标准
  private static final String MATERIALBIAOZHUN = "assisMaterialBiaoZhun";

 //材料名称
  private static final String MATERIALNAME = "materialOldName";


  //材料定额
  private static final String RATION = "ration";



  //单位
  private static final String UNIT = "unit";
  
  //材料用途 
  private static final String MATERIALYONGTY = "materialYongTu";
  //标识
  private static final String LABEL = "label";
//CCBesin SS1
  //标识
  private static final String VERSION = "version";
  private static final String FILENAME = "fileName";
  private static final String DATETIME = "dataTime";
  private static final String LX = "lx";
  //CCEnd SS1

  /**
   * 默认构造器
   */
  public WriteXmlRationForErp() {
  }

 

  /**
   * 发布定额单到erp系统
   * 首先将数据写入xml
   * @param base BaseValueIfc
   */
  public void publishDataToErp(BaseValueIfc base) {
    System.out.println("开始发布定额单数据" + base);
    MaterialRationListIfc list = (MaterialRationListIfc) base;
    String type = list.getRationType();
    if (type.equals("辅助材料消耗定额")) {
      publishAssisRationListToErp(list);
    }
    System.out.println("结束发布定额单数据" + base);
  }
  /**
   * 发布定额单到erp系统
   * 首先将数据写入xml
   * @param base BaseValueIfc
   */
  public void publishDataToErp1(String baseid) {
	  PersistService pservice;
	try {
		pservice = (PersistService)EJBServiceHelper.getService("PersistService");
		MaterialRationListIfc base =  (MaterialRationListIfc)pservice.refreshInfo(baseid);
		 System.out.println("开始发布定额单数据" + base);
		    MaterialRationListIfc list = (MaterialRationListIfc) base;
		    String type = list.getRationType();
		    if (type.equals("辅助材料消耗定额")) {
		      publishAssisRationListToErp(list);
		    }
		    System.out.println("结束发布定额单数据" + base);
	} catch (QMException e) {
		
    	
		e.printStackTrace();
	}
   
  }
  /**
   * 发布辅助材料定额单
   * @param list MaterialRationListIfc
   * @throws QMException
   */
  private void publishAssisRationListToErp(MaterialRationListIfc list) {
    try {
    	  Timestamp time = new Timestamp(System.currentTimeMillis());
    	    //辅材发布到ERP的XML文件的文件名为 年月日-材料定额编号
      java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
    	  			"yyyyMMdd-");
    	 
      sdf.format(time);
      String assName= sdf.format(time)+list.getListNumber();
      Class c = Class.forName("org.apache.crimson.tree.XmlDocument");
      XmlDocument doc = (XmlDocument) c.newInstance();
      Element product = doc.createElement(PRODUCT1);
      //定额类型
      Element rationType = doc.createElement(RATIONTYPE);
      rationType.appendChild(doc.createTextNode("辅助材料消耗定额"));
      //文件名
      Element filename = doc.createElement(FILENAME);
      filename.appendChild(doc.createTextNode(assName));
      //文件发布时间
      java.text.SimpleDateFormat stime = new java.text.SimpleDateFormat(
		"yyyyMMdd HH:mm:ss");

      String rqsj = stime.format(time);
      Element datetime = doc.createElement(DATETIME);
      datetime.appendChild(doc.createTextNode(rqsj));

      //部门（车间）
      Element workShop = doc.createElement(WORKSHOP);
      if(list.getWorkShop() instanceof CodingIfc){
          CodingIfc ifc = (CodingIfc) list.getWorkShop();

        //这里传路线代码
          workShop.appendChild(doc.createTextNode(ifc.getCodeContent()));
          }else{
        	  CodingClassificationIfc ifc = (CodingClassificationIfc) list.getWorkShop();
        	  workShop.appendChild(doc.createTextNode(ifc.getCodeSort()));
          }
      product.appendChild(rationType);
      product.appendChild(filename);
      product.appendChild(datetime);
     // product.appendChild(lx);
      product.appendChild(workShop);
      Element primarypart = null;
      Collection cc = getPartLinkByAssisRationList(list);
      System.out.println("cc====="+cc);
      for (Iterator iter = cc.iterator(); iter.hasNext(); ) {
    	  RationListPartLinkIfc info = (RationListPartLinkIfc) iter.next();
        primarypart = doc.createElement(PRIMARYPART);
        primarypart = writeErpPrimaryParts_ForAssis(doc, info, primarypart);
        if(primarypart.hasChildNodes())
        product.appendChild(primarypart);
        primarypart = null;
      }
      doc.appendChild(product);
    
      FileOutputStream out=null;
       out = new FileOutputStream(new File(getFileName(
    		  exportAssisRationListToErp,assName )));
     //6 材料定额和bom发送的文件前面的时间变成090903
       Writer writer = new OutputStreamWriter(out, "gb2312");

	      doc.write(writer, "gb2312");

System.out.println("发布成功");
//System.out.println("exportAssisRationListToErp==="+exportAssisRationListToErp);
	out.close();
	writer.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }
  /**
   * 将辅助材料定额的priarypart 的信息写入到xml中
   * @param doc XmlDocument
   * @param info QMPartIfc
   * @param primarypart Element
   * @throws QMException
   * @return Element
   */
  private Element writeErpPrimaryParts_ForAssis(XmlDocument doc,
		                    RationListPartLinkIfc info,
                                             Element primarypart) {
	PartHelper helper = new PartHelper();
    //生成xml标签
    Element primarypartNum = doc.createElement(PARTNUM);
    Element primarypartName = doc.createElement(PARTNAME);
    Element version = doc.createElement(VERSION);
    Element primaryadoptState = doc.createElement(ADOPTSTATE);
//  路线
    Element lx = doc.createElement(LX);
    lx.appendChild(doc.createTextNode("川总"));
    //将值写入
    primarypartNum.appendChild(doc.createTextNode(info.getPartMaster().getPartNumber()));
    primarypartName.appendChild(doc.createTextNode(info.getPartMaster().
        getPartName()));
    QMPartIfc part = null;
	try {
		part = helper.getZZPartInfoByMasterBsoID(info.getPartMaster().getBsoID());
	} catch (QMException e) {
		// TODO 自动生成 catch 块
		e.printStackTrace();
	}
	//版本
    version.appendChild(doc.createTextNode(part.getVersionID()));
    //

    if (info != null) {
      primaryadoptState.appendChild(doc.createTextNode(info.getAdoptState()));

      //将标签追加到上级标签中
      primarypart.appendChild(primarypartNum);
      primarypart.appendChild(primarypartName);
      primarypart.appendChild(primaryadoptState);
      primarypart.appendChild(lx);
      //获得多个材料信息
      Collection assisMaterialIfc = getAssisMaterialByTotal(info);
      for (Iterator iter = assisMaterialIfc.iterator(); iter.hasNext(); ) {
    	  AssMaterialRationIfc assismaterialifc = (
    			  AssMaterialRationIfc)
            iter.next();
        QMMaterialIfc qmmaterialifc = assismaterialifc.getMaterialInfo();
        
        Element material = doc.createElement(MATERIAL);
        Element materialBsoID = doc.createElement(MATERIALBSOID);
        Element materialNum = doc.createElement(MATERIALNUM);
        Element materialName = doc.createElement(MATERIALNAME);
        Element materialGuiGe = doc.createElement(MATERIALGUIGE);
        Element materialPaiHao = doc.createElement(MATERIALPAIHAO);
        Element materialBiaoZhun = doc.createElement(MATERIALBIAOZHUN);
        Element ration = doc.createElement(RATION);
        Element unit = doc.createElement(UNIT);
        Element materialYongtu = doc.createElement(MATERIALYONGTY);
        Element label = doc.createElement(LABEL);


        materialBsoID.appendChild(doc.createTextNode(qmmaterialifc.getBsoID()));
        materialNum.appendChild(doc.createTextNode(qmmaterialifc.getMaterialNumber()));
        materialName.appendChild(doc.createTextNode(qmmaterialifc.getMaterialName()));
        materialGuiGe.appendChild(doc.createTextNode(qmmaterialifc.getMaterialSpecial()));
        materialPaiHao.appendChild(doc.createTextNode(qmmaterialifc.getMaterialState()));
        materialBiaoZhun.appendChild(doc.createTextNode(qmmaterialifc.getMaterialCrision()));
        String mration = String.valueOf(assismaterialifc.getMRation());
        String mrationstr = "0.0";
        if (mration!=null&&!mration.equalsIgnoreCase("0.0")) {
          //处理定额精度问题，不用科学计数法显示
          BigDecimal original = new BigDecimal(mration);
          BigDecimal result1 = original.setScale(15, BigDecimal.ROUND_HALF_DOWN);
          String newstring = getRealString(result1.toString());
          if (newstring.endsWith(".")) {
            newstring = newstring.substring(0, newstring.length() - 1);
          }
          mrationstr = newstring;
        }
        ration.appendChild(doc.createTextNode(mrationstr));

        unit.appendChild(doc.createTextNode(qmmaterialifc.getMeasureUnit().
                                            getCodeContent()));
        //用途
        materialYongtu.appendChild(doc.createTextNode(qmmaterialifc.getPurpose()));
        //标识
        label.appendChild(doc.createTextNode(assismaterialifc.getMatFlag()));
        //将标签追加到上级标签中
        primarypart.appendChild(material);
        material.appendChild(materialBsoID);
        material.appendChild(materialNum);
        material.appendChild(materialName);
        material.appendChild(materialGuiGe);
        material.appendChild(materialPaiHao);
        material.appendChild(materialBiaoZhun);
        material.appendChild(ration);
        material.appendChild(unit);
      }
    }
    primarypartNum = null;
    primarypartName = null;
    primaryadoptState = null;

    return primarypart;
  }
  
 

  /**
   * 根据辅材定额单获得其关联的零部件
   * @param list MaterialRationListIfc
   * @return Collection
   */
  private Collection getPartLinkByAssisRationList(MaterialRationListIfc list) {
    Collection coll = null;
    try {
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      QMQuery query = new QMQuery("RationListPartLink");
      QueryCondition cond = new QueryCondition("leftBsoID", "=",
                                               list.getBsoID());
      query.addCondition(cond);
      query.addOrderBy("rightBsoID");
      query.addOrderBy("adoptState",true);
     // System.out.println("query111==="+query.getDebugSQL());
      coll = pservice.findValueInfo(query, false);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    return coll;

  }

 

  
  /**
   * 辅材定额：根据AssisMaterialRationTotalIfc获得其关联的辅助材料集合
   * @param list MaterialRationListIfc
   * @return Collection
   */
  private Collection getAssisMaterialByTotal(RationListPartLinkIfc
                                             totalifc) {
    Collection coll = null;
    try {
      PersistService pservice = (PersistService) EJBServiceHelper.
          getPersistService();
      QMQuery query = new QMQuery("AssMaterialRation");
      QueryCondition cond = new QueryCondition("leftBsoID", "=",
                                               totalifc.getBsoID());
      query.addCondition(cond);
      //System.out.println("query22222==="+query.getDebugSQL());
      coll = pservice.findValueInfo(query, false);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    return coll;

  }

  /**
   * 得到文件全路径
   * @param name String
   * @return String
   */
  private String getFileName(String name, String info) {
    StringBuffer bf = new StringBuffer(name);
    bf.append(info).append(
        ".xml");
    return bf.toString();
  }
  /**
   * 去掉字符串后面的0
   * @param s String
   * @return String
   */
  private String getRealString(String s) {
    boolean flag = true;
    while (flag) {
      if (s.substring(s.length() - 1).equals("0")) {
        s = s.substring(0, s.length() - 1);
      }
      else {
        flag = false;
        break;
      }
    }
    return s;
  }

}
