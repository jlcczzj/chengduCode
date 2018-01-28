package com.faw_qm.erp.util;

import org.w3c.dom.*;
import org.apache.crimson.tree.XmlDocument;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.persist.util.*;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.ration.model.AssMaterialRationIfc;
import com.faw_qm.ration.model.MaterialRationListIfc;
import com.faw_qm.ration.model.RationListPartLinkIfc;
import com.faw_qm.resource.support.model.QMMaterialIfc;
import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;

//
import java.math.BigDecimal;
/**
 * <p>Title: дxml������erpϵͳ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������˾</p>
 * @author
 * @version 1.0
 * SS1 ���ķ�����ERP��XML�ļ����ļ���Ϊ ������-���϶����� ������ 2014-02-23
 */
public class WriteXmlRationForErp {

 
  private static final String exportAssisRationListToErp = RemoteProperty.
  getProperty(
  "com.faw_qm.erp.util.AssisRationlisttoerp",
  "/phosphor/support/loadFiles/xml/erpFiles");

  //�������ϱ�ʶ
  private static final String MATERIAL = "material";
  //����bsoid
  private static final String MATERIALBSOID = "materialBsoID";
  //xml������ֵ
  private static final String PRODUCT1 = "assistrationproduct";
  private static final String PRIMARYPART = "primarypart";

  //��������
  private static final String RATIONTYPE = "rationType";

  //����
  private static final String WORKSHOP = "workShop";

  //������
  private static final String PARTNUM = "partNum";

  //�������
  private static final String PARTNAME = "partName";


  //����״̬
  private static final String ADOPTSTATE = "adoptState";

  //���ϱ��
  private static final String MATERIALNUM = "materialNum";


  //���Ϲ��
  private static final String MATERIALGUIGE = "materialGuiGe";

  //�����ƺ�
  private static final String MATERIALPAIHAO = "materialPaihao";

  //���ϱ�׼
  private static final String MATERIALBIAOZHUN = "materialBiaoZhun";

 //��������
  private static final String MATERIALNAME = "materialName";


  //���϶���
  private static final String RATION = "ration";



  //��λ
  private static final String UNIT = "unit";
  



  /**
   * Ĭ�Ϲ�����
   */
  public WriteXmlRationForErp() {
  }

 

  /**
   * ���������erpϵͳ
   * ���Ƚ�����д��xml
   * @param base BaseValueIfc
   */
  public void publishDataToErp(BaseValueIfc base) {
    System.out.println("��ʼ�����������" + base);
    MaterialRationListIfc list = (MaterialRationListIfc) base;
    String type = list.getRationType();
    if (type.equals("�����������Ķ���")) {
      publishAssisRationListToErp(list);
    }
    System.out.println("���������������" + base);
  }

  /**
   * �����������϶��
   * @param list MaterialRationListIfc
   * @throws QMException
   */
  private void publishAssisRationListToErp(MaterialRationListIfc list) {
    try {
    	
      Class c = Class.forName("org.apache.crimson.tree.XmlDocument");
      XmlDocument doc = (XmlDocument) c.newInstance();
      Element product = doc.createElement(PRODUCT1);
      //��������
      Element rationType = doc.createElement(RATIONTYPE);
      rationType.appendChild(doc.createTextNode("�����������Ķ���"));
      //���ţ����䣩
      Element workShop = doc.createElement(WORKSHOP);
      if(list.getWorkShop() instanceof CodingIfc){
          CodingIfc ifc = (CodingIfc) list.getWorkShop();

        //���ﴫ·�ߴ���
          workShop.appendChild(doc.createTextNode(ifc.getCodeContent()));
          }else{
        	  CodingClassificationIfc ifc = (CodingClassificationIfc) list.getWorkShop();
        	  workShop.appendChild(doc.createTextNode(ifc.getCodeSort()));
          }
      product.appendChild(rationType);
      product.appendChild(workShop);
      Element primarypart = null;
      Collection cc = getPartLinkByAssisRationList(list);
      for (Iterator iter = cc.iterator(); iter.hasNext(); ) {
    	  RationListPartLinkIfc info = (RationListPartLinkIfc) iter.next();
        primarypart = doc.createElement(PRIMARYPART);
        primarypart = writeErpPrimaryParts_ForAssis(doc, info, primarypart);
        if(primarypart.hasChildNodes())
        product.appendChild(primarypart);
        primarypart = null;
      }
      doc.appendChild(product);
      Timestamp time = new Timestamp(System.currentTimeMillis());
      //CCBegin SS1
  	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
  			"yyyyMMdd-");
  //CCEnd SS1
  	sdf.format(time);
      String assName= sdf.format(time)+list.getListNumber();
      FileOutputStream out=null;
       out = new FileOutputStream(new File(getFileName(
    		  exportAssisRationListToErp,assName )));
     //CCEnd by chudaming 20101016 ���϶����bom���͵��ļ�ǰ���ʱ����090903
       Writer writer = new OutputStreamWriter(out, "gb2312");

	      doc.write(writer, "gb2312");

System.out.println("�����ɹ�");
//System.out.println("exportAssisRationListToErp==="+exportAssisRationListToErp);
	out.close();
	writer.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }
  /**
   * ���������϶����priarypart ����Ϣд�뵽xml��
   * @param doc XmlDocument
   * @param info QMPartIfc
   * @param primarypart Element
   * @throws QMException
   * @return Element
   */
  private Element writeErpPrimaryParts_ForAssis(XmlDocument doc,
		                    RationListPartLinkIfc info,
                                             Element primarypart) {
    //����xml��ǩ
    Element primarypartNum = doc.createElement(PARTNUM);
    Element primarypartName = doc.createElement(PARTNAME);
    Element primaryadoptState = doc.createElement(ADOPTSTATE);
    //��ֵд��
    primarypartNum.appendChild(doc.createTextNode(info.getPartMaster().getPartNumber()));
    primarypartName.appendChild(doc.createTextNode(info.getPartMaster().
        getPartName()));
    //

    if (info != null) {
      primaryadoptState.appendChild(doc.createTextNode(info.getAdoptState()));

      //����ǩ׷�ӵ��ϼ���ǩ��
      primarypart.appendChild(primarypartNum);
      primarypart.appendChild(primarypartName);
      primarypart.appendChild(primaryadoptState);
      //��ö��������Ϣ
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


        materialBsoID.appendChild(doc.createTextNode(qmmaterialifc.getBsoID()));
        materialNum.appendChild(doc.createTextNode(qmmaterialifc.getMaterialNumber()));
        materialName.appendChild(doc.createTextNode(qmmaterialifc.getMaterialName()));
        materialGuiGe.appendChild(doc.createTextNode(qmmaterialifc.getMaterialSpecial()));
        materialPaiHao.appendChild(doc.createTextNode(qmmaterialifc.getMaterialState()));
        materialBiaoZhun.appendChild(doc.createTextNode(qmmaterialifc.getMaterialCrision()));
        String mration = String.valueOf(assismaterialifc.getMRation());
        String mrationstr = "0.0";
        if (mration!=null&&!mration.equalsIgnoreCase("0.0")) {
          //����������⣬���ÿ�ѧ��������ʾ
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

        //����ǩ׷�ӵ��ϼ���ǩ��
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
   * ���ݸ��Ķ�������������㲿��
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
   * ���Ķ������AssisMaterialRationTotalIfc���������ĸ������ϼ���
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
   * �õ��ļ�ȫ·��
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
   * ȥ���ַ��������0
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
