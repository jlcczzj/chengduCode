/** 生成程序PMTechUseMController.java      1.1  2004/10/18
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 由于材料定额升级，所以获取材料定额方法改变 liuyang 2013-2-26
 */
package com.faw_qm.cappclients.capp.controller.TechUsageMaterial;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;  

import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.cappclients.capp.controller.TechUsageMaterialLinkController;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.util.QMMessage;
//CCBegin by liuzc 2009-11-29 原因：解放系统升级为v4r3。
import com.faw_qm.ration.model.BMaterialRationIfc;
import com.faw_qm.ration.model.MaterialRationIfc;
import com.faw_qm.ration.model.MaterialRationInfo;
import com.faw_qm.ration.model.RationListPartLinkIfc;
//import com.faw_qm.ration.model.MaterialRationInfo;
//import com.faw_qm.ration.model.RationListPartLinkIfc;
//CCEnd by liuzc 2009-11-29 原因：解放系统升级为v4r3。
import com.faw_qm.resource.support.model.QMMaterialIfc;


/**
 * <p>Title: 冲压下料工艺材料关联的控制类</p>
 * <p>Description: 主要功能有计算,获得材料主要标识列,获得材料利用率,处理关联面板</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 一汽启明</p>
 * @author 薛静
 * @version 1.0
 * （1）20060907薛静修改，修改计算方法，将每张可制件数转换成int型
 */

public class PMTechUseMController extends TechUsageMaterialLinkController
{

    /**
     * 构造方法
     * @param type String 工艺种类
     */
    public PMTechUseMController(String type)
    {
        super(type);
    }
//  CCBegin by leixiao 2008-10-06 原因：解放升级,计算
    /**  
     * 计算
     * 原计算公式有：剪后毛坯重量=剪后毛坯长*剪后毛坯宽*理论重量
     *             单间毛坯重量=剪后毛坯重量/每条可制件数
     *             供料重量=理论重量*供料尺寸宽*供料尺寸高(部分参数省略)
     *             每张可制件数=每张可剪条数*每条可制件数
     *             单件消耗定额=供料重量/(每张可剪条数*每条可制件数)
     */    
    public void calculate()
    {
        double offerDensityD = Double.parseDouble(RemoteProperty.getProperty(
				"默认的材料密度", "7.85"));
        double extendlLength0 = Double.parseDouble(RemoteProperty.getProperty(
				"薄板预留卷宽", "3"));
        double extendlLength1 = Double.parseDouble(RemoteProperty.getProperty(
				"厚板预留卷宽", "20"));
        double lWeight0 = Double.parseDouble(RemoteProperty.getProperty(
				"薄板卷重", "7000"));
        double lWeight1 = Double.parseDouble(RemoteProperty.getProperty(
				"厚板卷重", "23000"));
        double cutextend0 = Double.parseDouble(RemoteProperty.getProperty(
				"薄板料头料尾", "2300"));
        double cutextend1 = Double.parseDouble(RemoteProperty.getProperty(
				"厚板料头料尾", "1500"));
        DecimalFormat df2 = new DecimalFormat("0.00"); //取两位小数
        DecimalFormat df4 = new DecimalFormat("0.0000"); //取四位小数
        
        try
        {
            if (cappAssociationsPanel.getLinks().size() == 0)
            {
                return;
            }
            row = 0;
//          获得卷料类型
            int type=1;
            String gltype = (String) cappAssociationsPanel.getCellAt(row, 8);
            System.out.println("------卷料类型＝"+gltype);           
            try
            {
            
            type=Integer.parseInt(gltype);
            }
            catch (NumberFormatException ex)
            {
                throw new QMException("\"卷料类型\"属性值应为0或1,0代表\"非表面件\"，1代表\"表面件\"");
            }
            
            double lWeight =0;//卷重
            double extendlLength =0;//预留宽,3,20
            double cutextend =0;//料头料尾　2300,1500
            if(type==0){
            	lWeight=lWeight0;
            	extendlLength=extendlLength0;
            	cutextend=cutextend0;
            }
            else if(type==1){
            	lWeight=lWeight1;
            	extendlLength=extendlLength1;
            	cutextend=cutextend1;
            }
            	
//          获得卷宽
            double lLength;
            String glLength = (String)cappAssociationsPanel.getCellAt(row, 9);
            System.out.println("------卷宽＝"+glLength);           
            try
            {
            	lLength = Double.parseDouble(
                		glLength);
            }
            catch (NumberFormatException ex)
            {
                throw new QMException("\"料宽\"属性值应为Double型");
            }

            
//          获得材料厚度
            double lPly;
            String glPly = (String)cappAssociationsPanel.getCellAt(row, 10);
            System.out.println("------材料厚度＝"+glPly);            
            try
            {
            	lPly = Double.parseDouble(
            			glPly);
            }
            catch (NumberFormatException ex)
            {
                throw new QMException("\"料厚\"属性值应为Double型");
            }
//          设置卷重
            System.out.print("-----卷重="+lWeight);
            cappAssociationsPanel.setCellTextValue(row, 11,
                    String.valueOf(new Double(lWeight).intValue()));
            
 //计算长度
            double calculateLength;
            //计算长度=卷重/((卷宽+预留宽)*料厚*7.85)*1000000
            calculateLength=lWeight/((lLength+extendlLength)*lPly*offerDensityD)*1000000;

             if(type==1){
//            	计算长度=卷重/((卷宽+预留宽)*料厚*7.85)*1000000-料头料尾
            	calculateLength=calculateLength-cutextend;	
            }
            System.out.println("----------计算长度＝"+calculateLength);
//剪后毛坯长
            double roughLengthPostCutD;
            String roughLengthPostCut = (String) cappAssociationsPanel.getCellAt(
                    row, 16);

            if (roughLengthPostCut == null ||
                roughLengthPostCut.equals(""))
            {
                return;
                //throw new Exception("\"剪后毛坯长\"属性为空!");
            }
            else
            {
                try
                {
                    roughLengthPostCutD = Double.parseDouble(
                            roughLengthPostCut);
                }
                catch (NumberFormatException ex)
                {
                    throw new QMException("\"剪后毛坯长\"属性值应为Double型");
                }

            }
            //剪后毛坯宽
            String roughWidthPostCut = (String) cappAssociationsPanel.getCellAt(
                    row, 17);
            double roughWidthPostCutD;
            if (roughWidthPostCut == null ||
                roughWidthPostCut.equals(""))
            {
                return;
                //throw new Exception("\"剪后毛坯宽\"属性为空!");
            }
            else
            {
                try
                {
                    roughWidthPostCutD = Double.parseDouble(
                            roughWidthPostCut);
                }
                catch (NumberFormatException ex)
                {
                    throw new QMException("\"剪后毛坯宽\"属性值应为Double型");
                }

            }
            //可剪条数
            double cancut=0;
            if(type==0){
            	//a＝(计算长度-料头料尾)/剪后毛坯长*料宽/剪后毛坯宽
            	double a=(calculateLength-cutextend)/roughLengthPostCutD*lLength/roughWidthPostCutD;
            	System.out.println("---a="+a);
            	int aa=(int)a;
            	System.out.println("----aa="+aa);
            	cancut=aa+(a-aa)*0.8;
            	cancut=new Double(cancut).intValue();
            	System.out.println("----------可剪条数＝"+cancut);
            }
            else if(type==1){
            	double a=calculateLength/roughLengthPostCutD;
            	int aa=(int)a;
            	System.out.println("  a="+a+"---aa="+aa);
            	double b=lLength/roughWidthPostCutD;
            	int bb=(int)b; 
            	System.out.println("   b="+b+"---bb="+bb);
            	cancut=aa*bb;
            	cancut=new Double(cancut).intValue();
            	System.out.println("----------可剪条数＝"+cancut);
            }
            //获得剪后毛坯可制件数
            String cutableCountPerSheet = (String) cappAssociationsPanel.getCellAt(
                    row, 13);
            double cutableCountPerSheetD;
            if (cutableCountPerSheet == null ||
            		cutableCountPerSheet.equals(""))
            {
                return;
                //throw new Exception("\"剪后毛坯宽\"属性为空!");
            }
            else
            {
                try
                {
                	cutableCountPerSheetD = Double.parseDouble(
                			cutableCountPerSheet);
                }
                catch (NumberFormatException ex)
                {
                    throw new QMException("\"剪后毛坯可制件数\"属性值应为Double型");
                }

            }
            
            //可制件数
            double controlableCountPerSheet = cancut *cutableCountPerSheetD;           
            //20060907薛静修改，将每张可制件数转换成int型
            cappAssociationsPanel.setCellTextValue(row, 14,
                    String.valueOf(new Double(controlableCountPerSheet).intValue()));
            
//            controlableCountPerSheet = Double.parseDouble(df2.format(controlableCountPerSheet));            
//            cappAssociationsPanel.setCellTextValue(row, 14,
//                    String.valueOf(roundDouble(controlableCountPerSheet)));
            
            //剪后毛坯重量
            double roughWeightPostCutD = lPly *
                                         roughLengthPostCutD *
                                         roughWidthPostCutD *7.85/ 1000000;
            roughWeightPostCutD = Double.parseDouble(df4.format(roughWeightPostCutD));   
            cappAssociationsPanel.setCellTextValue(row, 15,
                    String.valueOf(roundDouble(roughWeightPostCutD)));
            //单件毛坯尺寸
            if(cutableCountPerSheetD>1)
            cappAssociationsPanel.setCellTextValue(row, 18,
            		roughWidthPostCut+"*"+roughLengthPostCut+"/"+cutableCountPerSheet);
            else
                cappAssociationsPanel.setCellTextValue(row, 18,
                		roughWidthPostCut+"*"+roughLengthPostCut);	
            //单件毛坯重量

            double roughWeightPerPieceD = roughWeightPostCutD /cutableCountPerSheetD;
            roughWeightPerPieceD = Double.parseDouble(df4.format(roughWeightPerPieceD));   
            cappAssociationsPanel.setCellTextValue(row, 19,
                    String.valueOf(
                    roundDouble(roughWeightPerPieceD)));
            
            //不成倍数
            //计算长度-料头料尾-可剪条数*剪后毛坯长度/(卷宽/剪后毛坯宽度)
            double bcbs=0;
            if(type==0)
            bcbs=calculateLength-cutextend-controlableCountPerSheet*roughLengthPostCutD/(lLength/roughWidthPostCutD);
            else if(type==1){
            	double a1=calculateLength/roughLengthPostCutD;
            	int aa1=(int)a1;
            	System.out.println("-----a1="+a1+"---aa1"+aa1);
            	bcbs=calculateLength-aa1*roughLengthPostCutD;	
            }
            System.out.println("------不成倍数＝"+bcbs);
            
            
            //消耗定额            

         // 单件消耗/定额=卷料重量÷可制件数 
            double ration;
            if(type==1&&bcbs>=200)
            {
            	//23000*(J7+1500-0.85*S7)/(J7+1500)/L7,23000/L7
            	ration=lWeight*(calculateLength+cutextend-0.85*bcbs)/(calculateLength+cutextend)/controlableCountPerSheet;
            }
            else{
            	ration = lWeight /controlableCountPerSheet;	
            }
            ration = Double.parseDouble(df4.format(ration)); 
            System.out.println("-----消耗定额＝"+ration);
            cappAssociationsPanel.setCellTextValue(row, 7,
                    String.valueOf(roundDouble(ration)));
//          获得零件净重
            double netWeigth1;
            String netWeigth = (String)cappAssociationsPanel.getCellAt(row, 12);
            System.out.println("------零件净重＝"+netWeigth);            
            try
            {
            	netWeigth1 = Double.parseDouble(
            			netWeigth);
            }
            catch (NumberFormatException ex)
            {
                throw new QMException("\"零件净重\"属性值应为Double型");
            }   
            // 利用率=零件净重÷单件消耗/定额＊100    
            double useRate = netWeigth1 /ration*100;
            useRate = Double.parseDouble(df2.format(useRate)); 
            cappAssociationsPanel.setCellTextValue(row, 20,
                    String.valueOf(roundDouble(useRate)));      
            
        }
        catch (Exception ex1)
        {
            if(verbose)
            ex1.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(panel,
                                          ex1.getLocalizedMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }
//  CCEnd by leixiao 2008-10-06 原因：解放升级,计算  
    /**
     * 计算
     * 原计算公式有：剪后毛坯重量=剪后毛坯长*剪后毛坯宽*理论重量
     *             单间毛坯重量=剪后毛坯重量/每条可制件数
     *             供料重量=理论重量*供料尺寸宽*供料尺寸高(部分参数省略)
     *             每张可制件数=每张可剪条数*每条可制件数
     *             单件消耗定额=供料重量/(每张可剪条数*每条可制件数)
     */ 
    public void calculate1()
    {
        try
        {
            if (cappAssociationsPanel.getLinks().size() == 0)
            {
                return;
            }
            row = 0;
            //剪后毛坯长
            String roughLengthPostCut = (String)
                                        cappAssociationsPanel.getCellAt(
                    row, 9);
            double roughLengthPostCutD;
            if (roughLengthPostCut == null ||
                roughLengthPostCut.equals(""))
            {
                return;
                //throw new Exception("\"剪后毛坯长\"属性为空!");

            }
            else
            {
                try
                {
                    roughLengthPostCutD = Double.parseDouble(
                            roughLengthPostCut);
                }
                catch (NumberFormatException ex)
                {
                    throw new QMException("\"剪后毛坯长\"属性值应为Double型");
                }

            }

            String roughWidthPostCut = (String) cappAssociationsPanel.getCellAt(
                    row, 10);
            double roughWidthPostCutD;
            if (roughWidthPostCut == null ||
                roughWidthPostCut.equals(""))
            {
                return;
                //throw new Exception("\"剪后毛坯宽\"属性为空!");
            }
            else
            {
                try
                {
                    roughWidthPostCutD = Double.parseDouble(
                            roughWidthPostCut);
                }
                catch (NumberFormatException ex)
                {
                    throw new QMException("\"剪后毛坯宽\"属性值应为Double型");
                }

            }
            //得到理论重量
            String theoryWeight = (String) cappAssociationsPanel.
                                  getCellAt(row,
                                            3);
            double theoryWeightD;
            if (theoryWeight == null || theoryWeight.equals(""))
            {
                return;
                // throw new Exception("\"理论重量\"属性为空!");

            }
            else
            {
                try
                {
                    theoryWeightD = Double.parseDouble(theoryWeight);
                }
                catch (NumberFormatException ex)
                {
                    throw ex;
                }

            }
            //CCBegin by liuzc 2009-11-29 原因：解放系统升级。
            //计算剪后毛坯重量 modify by lil in 20090904 加上*7.85
            double roughWeightPostCutD = theoryWeightD *
                                         roughLengthPostCutD *
                                         roughWidthPostCutD *7.85/ 1000000;
            //CCEnd by liuzc 2009-11-29 原因：解放系统升级。
            cappAssociationsPanel.setCellTextValue(row, 11,
                    String.valueOf(roundDouble(roughWeightPostCutD)));

            //获得每条可制件数
            String controlableCountPerPiece = (String) cappAssociationsPanel.
                                              getCellAt(row, 7);
            double controlableCountPerPieceD;
            if (controlableCountPerPiece == null ||
                controlableCountPerPiece.equals(""))
            {
                return;
                //throw new Exception("\"每条可制件数\"属性为空!");

            }
            else
            {
                try
                {
                    controlableCountPerPieceD = Double.parseDouble(
                            controlableCountPerPiece);
                }
                catch (NumberFormatException ex)
                {
                    throw new QMException("\"每条可制件数\"属性值应为Double型");
                }
                if (controlableCountPerPieceD == 0)
                {
                    throw new QMException("\"每条可制件数\"属性不能为0");
                }

            }

            //计算单间毛坯重量
            double roughWeightPerPieceD = roughWeightPostCutD /
                                          controlableCountPerPieceD;
            cappAssociationsPanel.setCellTextValue(row, 13,
                    String.valueOf(
                    roundDouble(roughWeightPerPieceD)));
            //得到规格,即供料尺寸的第一位数
            String offerSize = (String) cappAssociationsPanel.
                               getCellAt(row,
                                         2);
            if (offerSize == null || offerSize.trim().equals(""))
            {
                return;
            }
            StringTokenizer offerSizeToken = new StringTokenizer(
                    offerSize, "*");
            if (offerSizeToken.countTokens() != 3)
            {
                return;
            }
            String materialSpecial = offerSizeToken.nextToken();
            double materialSpecialD;
            if (materialSpecial == null || materialSpecial.equals(""))
            {
                return;
            }
            else
            {
                materialSpecialD = Double.parseDouble(materialSpecial);
            }
            double data1;
            double data2;
            if (materialSpecialD > 3)
            {
                data1 = 15;
                data2 = 10;
            }
            else
            {
                data1 = 7.5;
                data2 = 5;
            }
            //供料重量
            double materialWeight = theoryWeightD *
                                    (Double.parseDouble(offerSizeToken.
                    nextToken()) +
                                     data2) *
                                    (Double.parseDouble(offerSizeToken.
                    nextToken()) + data1) / 1000000;
            cappAssociationsPanel.setCellTextValue(row, 15,
                    String.valueOf(roundDouble(materialWeight)));
            //获得每张可剪条数
            String cutableCountPerSheet = (String) cappAssociationsPanel.
                                          getCellAt(row, 6);
            int cutableCountPerSheetD;
            if (cutableCountPerSheet == null ||
                cutableCountPerSheet.equals(""))
            {
                return;
                //throw new Exception("\"每张可剪条数\"属性为空!");

            }
            else
            {
                try
                {
                    cutableCountPerSheetD = Integer.parseInt(
                            cutableCountPerSheet);
                }
                catch (NumberFormatException ex)
                {
                    throw new QMException("\"每张可剪条数\"属性值应为整型");
                }
                if (cutableCountPerSheetD == 0)
                {
                    throw new QMException("\"每张可剪条数\"属性值不能为0");
                }

            }
            //每张可制件数
            double controlableCountPerSheet = cutableCountPerSheetD *
                                              controlableCountPerPieceD;
            //20060907薛静修改，将每张可制件数转换成int型
            cappAssociationsPanel.setCellTextValue(row, 8,
                    String.valueOf(new Double(controlableCountPerSheet).intValue()));
            //单件消耗定额
            double ration = materialWeight /
                            (cutableCountPerSheetD * controlableCountPerPieceD);
            cappAssociationsPanel.setCellTextValue(row, 4,
                    String.valueOf(roundDouble(ration)));
        }
        catch (Exception ex1)
        {
            if(verbose)
            ex1.printStackTrace();
            String title = QMMessage.getLocalizedMessage(RESOURCE,
                    "information", null);
            JOptionPane.showMessageDialog(panel,
                                          ex1.getLocalizedMessage(), title,
                                          JOptionPane.INFORMATION_MESSAGE);
        }

    }

//  CCBeginby leixiao 2009-7-8 原因：工艺与材料面板，增加获取材料定额
    public void   getration(){
        row = 0;
    	String part =cappAssociationsPanel.getAssociatpart();
        if(part==null||part.equals("")||part.equals("null")){
        	throw new NullPointerException("此工艺未添加零件关联。");
        }
        Collection coll =findMainRationByPart(part);
        if(coll.size()==0){
        	throw new NullPointerException("此工艺的零件没有生效的材料定额。");
        }
        //CCBegin SS1
//        MainMaterialRationIfc validRation = (MainMaterialRationIfc)
//        coll.iterator().next();
//    	//System.out.println("----------validRation="+validRation);
//       String materialid=validRation.getRightBsoID();
//       Class[] paraClass ={String.class};
//       Object[] objs ={materialid};
       
        Object[] obj = (Object[]) coll.iterator().next();
        MaterialRationInfo validRation = (MaterialRationInfo)obj[2];
        RationListPartLinkIfc link = (RationListPartLinkIfc)obj[1];
       String materialid=validRation.getRightBsoID();
       Class[] paraClass ={String.class};
       Object[] objs ={validRation.getRightBsoID()};
       //CCEnd SS1
       QMMaterialIfc material=null;

       try {
		 material = (QMMaterialIfc)
		   TechnicsAction.useServiceMethod(
		          "PersistService", "refreshInfo", paraClass, objs);
			cappAssociationsPanel.addSelectedObject(material);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

       cappAssociationsPanel.setCellTextValue(row, 0,
    		   material.getMaterialNumber());
       cappAssociationsPanel.setCellTextValue(row, 1,
    		   material.getMaterialName());
       cappAssociationsPanel.setCellTextValue(row, 2,
    		   material.getMaterialCf().getCodeContent());
       cappAssociationsPanel.setCellTextValue(row, 3,
    		   material.getMaterialCrision());
       cappAssociationsPanel.setCellTextValue(row, 4,
    		   material.getMaterialState());
       cappAssociationsPanel.setCellTextValue(row, 5,
    		   material.getMaterialSpecial());
       
       cappAssociationsPanel.setRadionButtonValue(row, 6,
    		   true);
       cappAssociationsPanel.setCellTextValue(row, 7,
    		   String.valueOf(roundDouble( validRation.getMRation()))
    		   );
      String gg= material.getMaterialSpecial();
      String hd="";
      if(gg.indexOf("*")!=-1)
    	  hd=gg.substring(0,gg.indexOf("*"));
      else if (gg.indexOf("×")!=-1)
    	  hd=gg.substring(0,gg.indexOf("×"));  	   	  
       cappAssociationsPanel.setCellTextValue(row, 8,
              hd); 
       //CCBegin SS1
//       cappAssociationsPanel.setCellTextValue(row, 9,
//    		   String.valueOf(roundDouble(validRation.getMaterialWeight())) );
//       cappAssociationsPanel.setCellTextValue(row, 10,
//    		   String.valueOf(roundDouble(validRation.getPartWeight())));
//       cappAssociationsPanel.setCellTextValue(row, 11,
//    		   String.valueOf(roundDouble( validRation.getMakePartAmount())));
//       String c=validRation.getRoughDimension();
//       String k=String.valueOf(roundDouble( validRation.getRationAmount()));
//       String s=validRation.getSpecialRequest();
//       if(s.equals("1"))
//    	   s="";
//       else
//    	   s="/"+s;
//       cappAssociationsPanel.setCellTextValue(row, 12,
//    		   k+"×"+c+s);
//
//      cappAssociationsPanel.setCellTextValue(row, 13,
//    		   String.valueOf(roundDouble( validRation.getAverageRoughWeight())));
//       cappAssociationsPanel.setCellTextValue(row, 14,
//    		   String.valueOf(roundDouble( validRation.getMaterialUtiliFct())));
       if(validRation instanceof BMaterialRationIfc)
       {
    	   
       cappAssociationsPanel.setCellTextValue(row, 9,
    		   String.valueOf(roundDouble(((BMaterialRationIfc)validRation).getRollWeight())) );
       cappAssociationsPanel.setCellTextValue(row, 10,
    		   String.valueOf(roundDouble(link.getPartWeight())));
       cappAssociationsPanel.setCellTextValue(row, 11,
    		   String.valueOf(roundDouble( ((BMaterialRationIfc)validRation).getMakePartAmount())));
       double roughlength =((BMaterialRationIfc)validRation).getRoughDimension();
       double roughwidth =((BMaterialRationIfc)validRation).getRoughWidth();
       double roughAmount = ((BMaterialRationIfc)validRation).getRoughAmount();
       String c = String.valueOf(roughlength);
       String k= String.valueOf(roughwidth);
       String s= String.valueOf(roughAmount);
       if(s.equals("1"))
    	   s="";
       else
    	   s="/"+s;
       cappAssociationsPanel.setCellTextValue(row, 12,
    		   k+"×"+c+s);

      cappAssociationsPanel.setCellTextValue(row, 13,
    		   String.valueOf(roundDouble( ((BMaterialRationIfc)validRation).getAverageRoughWeight())));
       cappAssociationsPanel.setCellTextValue(row, 14,
    		   ((BMaterialRationIfc)validRation).getMUsePercent());
       
       }
       //CCEnd SS1
        
        
    }
//  CCEndby leixiao 2009-7-8 原因：工艺与材料面板，增加获取材料定额
    

    /**
     * 处理关联面板
     */
    public void handelCappAssociationsPanel()
    {
        super.handelCappAssociationsPanel();
        int[] rds =
                {
                6};//CCEndby liuzc 2009-7-8 原因：工艺与材料面板，增加获取材料定额
        //设置第3列显示JRadioButton
        cappAssociationsPanel.setRadionButtons(rds);
        //20061127薛静修改，将第0列设为可编辑列
        int[] is =
                {
                0,7, 8, 9, 10, 11,12, 13,14};
        //设置列可以编辑
        cappAssociationsPanel.setColsEnabled(is, true);
        //modify by lil in 20080904
      //  cappAssociationsPanel.setIsCalculate(true, 4);
        cappAssociationsPanel.setIsRation(true, 4);
			//CCEndby liuzc 2009-7-8 原因：工艺与材料面板，增加获取材料定额
    }


    /**
     * 获得材料利用率
     * @throws Exception
     * @return double 材料利用率
     */
    public double getMRation()
            throws QMException
    {
        String ration = (String) cappAssociationsPanel.getCellAt(0, 4);

        double rationD = 0.0;
        if (ration != null)
        {
            try
            {
                rationD = Double.parseDouble(ration);
            }
            catch (NumberFormatException ex)
            {
                throw new QMException("材料关联面板中材料消耗定额属性必须是double型");
            }
        }
        return rationD;
    }

    public int getMajorMCol()
    {
        return 6;
    }


    /**
     * 保留小数点后四位
     * @param d double
     * @return double
     */
    private double roundDouble(double d)
    {
        d = Math.round(d * 10000);
        d = d / 10000;
        return d;
    }

}
