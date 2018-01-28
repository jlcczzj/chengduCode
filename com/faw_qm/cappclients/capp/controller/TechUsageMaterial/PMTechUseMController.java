/** ���ɳ���PMTechUseMController.java      1.1  2004/10/18
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ���ڲ��϶������������Ի�ȡ���϶�����ı� liuyang 2013-2-26
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
//CCBegin by liuzc 2009-11-29 ԭ�򣺽��ϵͳ����Ϊv4r3��
import com.faw_qm.ration.model.BMaterialRationIfc;
import com.faw_qm.ration.model.MaterialRationIfc;
import com.faw_qm.ration.model.MaterialRationInfo;
import com.faw_qm.ration.model.RationListPartLinkIfc;
//import com.faw_qm.ration.model.MaterialRationInfo;
//import com.faw_qm.ration.model.RationListPartLinkIfc;
//CCEnd by liuzc 2009-11-29 ԭ�򣺽��ϵͳ����Ϊv4r3��
import com.faw_qm.resource.support.model.QMMaterialIfc;


/**
 * <p>Title: ��ѹ���Ϲ��ղ��Ϲ����Ŀ�����</p>
 * <p>Description: ��Ҫ�����м���,��ò�����Ҫ��ʶ��,��ò���������,����������</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: һ������</p>
 * @author Ѧ��
 * @version 1.0
 * ��1��20060907Ѧ���޸ģ��޸ļ��㷽������ÿ�ſ��Ƽ���ת����int��
 */

public class PMTechUseMController extends TechUsageMaterialLinkController
{

    /**
     * ���췽��
     * @param type String ��������
     */
    public PMTechUseMController(String type)
    {
        super(type);
    }
//  CCBegin by leixiao 2008-10-06 ԭ�򣺽������,����
    /**  
     * ����
     * ԭ���㹫ʽ�У�����ë������=����ë����*����ë����*��������
     *             ����ë������=����ë������/ÿ�����Ƽ���
     *             ��������=��������*���ϳߴ��*���ϳߴ��(���ֲ���ʡ��)
     *             ÿ�ſ��Ƽ���=ÿ�ſɼ�����*ÿ�����Ƽ���
     *             �������Ķ���=��������/(ÿ�ſɼ�����*ÿ�����Ƽ���)
     */    
    public void calculate()
    {
        double offerDensityD = Double.parseDouble(RemoteProperty.getProperty(
				"Ĭ�ϵĲ����ܶ�", "7.85"));
        double extendlLength0 = Double.parseDouble(RemoteProperty.getProperty(
				"����Ԥ�����", "3"));
        double extendlLength1 = Double.parseDouble(RemoteProperty.getProperty(
				"���Ԥ�����", "20"));
        double lWeight0 = Double.parseDouble(RemoteProperty.getProperty(
				"�������", "7000"));
        double lWeight1 = Double.parseDouble(RemoteProperty.getProperty(
				"������", "23000"));
        double cutextend0 = Double.parseDouble(RemoteProperty.getProperty(
				"������ͷ��β", "2300"));
        double cutextend1 = Double.parseDouble(RemoteProperty.getProperty(
				"�����ͷ��β", "1500"));
        DecimalFormat df2 = new DecimalFormat("0.00"); //ȡ��λС��
        DecimalFormat df4 = new DecimalFormat("0.0000"); //ȡ��λС��
        
        try
        {
            if (cappAssociationsPanel.getLinks().size() == 0)
            {
                return;
            }
            row = 0;
//          ��þ�������
            int type=1;
            String gltype = (String) cappAssociationsPanel.getCellAt(row, 8);
            System.out.println("------�������ͣ�"+gltype);           
            try
            {
            
            type=Integer.parseInt(gltype);
            }
            catch (NumberFormatException ex)
            {
                throw new QMException("\"��������\"����ֵӦΪ0��1,0����\"�Ǳ����\"��1����\"�����\"");
            }
            
            double lWeight =0;//����
            double extendlLength =0;//Ԥ����,3,20
            double cutextend =0;//��ͷ��β��2300,1500
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
            	
//          ��þ��
            double lLength;
            String glLength = (String)cappAssociationsPanel.getCellAt(row, 9);
            System.out.println("------���"+glLength);           
            try
            {
            	lLength = Double.parseDouble(
                		glLength);
            }
            catch (NumberFormatException ex)
            {
                throw new QMException("\"�Ͽ�\"����ֵӦΪDouble��");
            }

            
//          ��ò��Ϻ��
            double lPly;
            String glPly = (String)cappAssociationsPanel.getCellAt(row, 10);
            System.out.println("------���Ϻ�ȣ�"+glPly);            
            try
            {
            	lPly = Double.parseDouble(
            			glPly);
            }
            catch (NumberFormatException ex)
            {
                throw new QMException("\"�Ϻ�\"����ֵӦΪDouble��");
            }
//          ���þ���
            System.out.print("-----����="+lWeight);
            cappAssociationsPanel.setCellTextValue(row, 11,
                    String.valueOf(new Double(lWeight).intValue()));
            
 //���㳤��
            double calculateLength;
            //���㳤��=����/((���+Ԥ����)*�Ϻ�*7.85)*1000000
            calculateLength=lWeight/((lLength+extendlLength)*lPly*offerDensityD)*1000000;

             if(type==1){
//            	���㳤��=����/((���+Ԥ����)*�Ϻ�*7.85)*1000000-��ͷ��β
            	calculateLength=calculateLength-cutextend;	
            }
            System.out.println("----------���㳤�ȣ�"+calculateLength);
//����ë����
            double roughLengthPostCutD;
            String roughLengthPostCut = (String) cappAssociationsPanel.getCellAt(
                    row, 16);

            if (roughLengthPostCut == null ||
                roughLengthPostCut.equals(""))
            {
                return;
                //throw new Exception("\"����ë����\"����Ϊ��!");
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
                    throw new QMException("\"����ë����\"����ֵӦΪDouble��");
                }

            }
            //����ë����
            String roughWidthPostCut = (String) cappAssociationsPanel.getCellAt(
                    row, 17);
            double roughWidthPostCutD;
            if (roughWidthPostCut == null ||
                roughWidthPostCut.equals(""))
            {
                return;
                //throw new Exception("\"����ë����\"����Ϊ��!");
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
                    throw new QMException("\"����ë����\"����ֵӦΪDouble��");
                }

            }
            //�ɼ�����
            double cancut=0;
            if(type==0){
            	//a��(���㳤��-��ͷ��β)/����ë����*�Ͽ�/����ë����
            	double a=(calculateLength-cutextend)/roughLengthPostCutD*lLength/roughWidthPostCutD;
            	System.out.println("---a="+a);
            	int aa=(int)a;
            	System.out.println("----aa="+aa);
            	cancut=aa+(a-aa)*0.8;
            	cancut=new Double(cancut).intValue();
            	System.out.println("----------�ɼ�������"+cancut);
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
            	System.out.println("----------�ɼ�������"+cancut);
            }
            //��ü���ë�����Ƽ���
            String cutableCountPerSheet = (String) cappAssociationsPanel.getCellAt(
                    row, 13);
            double cutableCountPerSheetD;
            if (cutableCountPerSheet == null ||
            		cutableCountPerSheet.equals(""))
            {
                return;
                //throw new Exception("\"����ë����\"����Ϊ��!");
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
                    throw new QMException("\"����ë�����Ƽ���\"����ֵӦΪDouble��");
                }

            }
            
            //���Ƽ���
            double controlableCountPerSheet = cancut *cutableCountPerSheetD;           
            //20060907Ѧ���޸ģ���ÿ�ſ��Ƽ���ת����int��
            cappAssociationsPanel.setCellTextValue(row, 14,
                    String.valueOf(new Double(controlableCountPerSheet).intValue()));
            
//            controlableCountPerSheet = Double.parseDouble(df2.format(controlableCountPerSheet));            
//            cappAssociationsPanel.setCellTextValue(row, 14,
//                    String.valueOf(roundDouble(controlableCountPerSheet)));
            
            //����ë������
            double roughWeightPostCutD = lPly *
                                         roughLengthPostCutD *
                                         roughWidthPostCutD *7.85/ 1000000;
            roughWeightPostCutD = Double.parseDouble(df4.format(roughWeightPostCutD));   
            cappAssociationsPanel.setCellTextValue(row, 15,
                    String.valueOf(roundDouble(roughWeightPostCutD)));
            //����ë���ߴ�
            if(cutableCountPerSheetD>1)
            cappAssociationsPanel.setCellTextValue(row, 18,
            		roughWidthPostCut+"*"+roughLengthPostCut+"/"+cutableCountPerSheet);
            else
                cappAssociationsPanel.setCellTextValue(row, 18,
                		roughWidthPostCut+"*"+roughLengthPostCut);	
            //����ë������

            double roughWeightPerPieceD = roughWeightPostCutD /cutableCountPerSheetD;
            roughWeightPerPieceD = Double.parseDouble(df4.format(roughWeightPerPieceD));   
            cappAssociationsPanel.setCellTextValue(row, 19,
                    String.valueOf(
                    roundDouble(roughWeightPerPieceD)));
            
            //���ɱ���
            //���㳤��-��ͷ��β-�ɼ�����*����ë������/(���/����ë�����)
            double bcbs=0;
            if(type==0)
            bcbs=calculateLength-cutextend-controlableCountPerSheet*roughLengthPostCutD/(lLength/roughWidthPostCutD);
            else if(type==1){
            	double a1=calculateLength/roughLengthPostCutD;
            	int aa1=(int)a1;
            	System.out.println("-----a1="+a1+"---aa1"+aa1);
            	bcbs=calculateLength-aa1*roughLengthPostCutD;	
            }
            System.out.println("------���ɱ�����"+bcbs);
            
            
            //���Ķ���            

         // ��������/����=���������¿��Ƽ��� 
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
            System.out.println("-----���Ķ��"+ration);
            cappAssociationsPanel.setCellTextValue(row, 7,
                    String.valueOf(roundDouble(ration)));
//          ����������
            double netWeigth1;
            String netWeigth = (String)cappAssociationsPanel.getCellAt(row, 12);
            System.out.println("------������أ�"+netWeigth);            
            try
            {
            	netWeigth1 = Double.parseDouble(
            			netWeigth);
            }
            catch (NumberFormatException ex)
            {
                throw new QMException("\"�������\"����ֵӦΪDouble��");
            }   
            // ������=������ءµ�������/���100    
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
//  CCEnd by leixiao 2008-10-06 ԭ�򣺽������,����  
    /**
     * ����
     * ԭ���㹫ʽ�У�����ë������=����ë����*����ë����*��������
     *             ����ë������=����ë������/ÿ�����Ƽ���
     *             ��������=��������*���ϳߴ��*���ϳߴ��(���ֲ���ʡ��)
     *             ÿ�ſ��Ƽ���=ÿ�ſɼ�����*ÿ�����Ƽ���
     *             �������Ķ���=��������/(ÿ�ſɼ�����*ÿ�����Ƽ���)
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
            //����ë����
            String roughLengthPostCut = (String)
                                        cappAssociationsPanel.getCellAt(
                    row, 9);
            double roughLengthPostCutD;
            if (roughLengthPostCut == null ||
                roughLengthPostCut.equals(""))
            {
                return;
                //throw new Exception("\"����ë����\"����Ϊ��!");

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
                    throw new QMException("\"����ë����\"����ֵӦΪDouble��");
                }

            }

            String roughWidthPostCut = (String) cappAssociationsPanel.getCellAt(
                    row, 10);
            double roughWidthPostCutD;
            if (roughWidthPostCut == null ||
                roughWidthPostCut.equals(""))
            {
                return;
                //throw new Exception("\"����ë����\"����Ϊ��!");
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
                    throw new QMException("\"����ë����\"����ֵӦΪDouble��");
                }

            }
            //�õ���������
            String theoryWeight = (String) cappAssociationsPanel.
                                  getCellAt(row,
                                            3);
            double theoryWeightD;
            if (theoryWeight == null || theoryWeight.equals(""))
            {
                return;
                // throw new Exception("\"��������\"����Ϊ��!");

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
            //CCBegin by liuzc 2009-11-29 ԭ�򣺽��ϵͳ������
            //�������ë������ modify by lil in 20090904 ����*7.85
            double roughWeightPostCutD = theoryWeightD *
                                         roughLengthPostCutD *
                                         roughWidthPostCutD *7.85/ 1000000;
            //CCEnd by liuzc 2009-11-29 ԭ�򣺽��ϵͳ������
            cappAssociationsPanel.setCellTextValue(row, 11,
                    String.valueOf(roundDouble(roughWeightPostCutD)));

            //���ÿ�����Ƽ���
            String controlableCountPerPiece = (String) cappAssociationsPanel.
                                              getCellAt(row, 7);
            double controlableCountPerPieceD;
            if (controlableCountPerPiece == null ||
                controlableCountPerPiece.equals(""))
            {
                return;
                //throw new Exception("\"ÿ�����Ƽ���\"����Ϊ��!");

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
                    throw new QMException("\"ÿ�����Ƽ���\"����ֵӦΪDouble��");
                }
                if (controlableCountPerPieceD == 0)
                {
                    throw new QMException("\"ÿ�����Ƽ���\"���Բ���Ϊ0");
                }

            }

            //���㵥��ë������
            double roughWeightPerPieceD = roughWeightPostCutD /
                                          controlableCountPerPieceD;
            cappAssociationsPanel.setCellTextValue(row, 13,
                    String.valueOf(
                    roundDouble(roughWeightPerPieceD)));
            //�õ����,�����ϳߴ�ĵ�һλ��
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
            //��������
            double materialWeight = theoryWeightD *
                                    (Double.parseDouble(offerSizeToken.
                    nextToken()) +
                                     data2) *
                                    (Double.parseDouble(offerSizeToken.
                    nextToken()) + data1) / 1000000;
            cappAssociationsPanel.setCellTextValue(row, 15,
                    String.valueOf(roundDouble(materialWeight)));
            //���ÿ�ſɼ�����
            String cutableCountPerSheet = (String) cappAssociationsPanel.
                                          getCellAt(row, 6);
            int cutableCountPerSheetD;
            if (cutableCountPerSheet == null ||
                cutableCountPerSheet.equals(""))
            {
                return;
                //throw new Exception("\"ÿ�ſɼ�����\"����Ϊ��!");

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
                    throw new QMException("\"ÿ�ſɼ�����\"����ֵӦΪ����");
                }
                if (cutableCountPerSheetD == 0)
                {
                    throw new QMException("\"ÿ�ſɼ�����\"����ֵ����Ϊ0");
                }

            }
            //ÿ�ſ��Ƽ���
            double controlableCountPerSheet = cutableCountPerSheetD *
                                              controlableCountPerPieceD;
            //20060907Ѧ���޸ģ���ÿ�ſ��Ƽ���ת����int��
            cappAssociationsPanel.setCellTextValue(row, 8,
                    String.valueOf(new Double(controlableCountPerSheet).intValue()));
            //�������Ķ���
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

//  CCBeginby leixiao 2009-7-8 ԭ�򣺹����������壬���ӻ�ȡ���϶���
    public void   getration(){
        row = 0;
    	String part =cappAssociationsPanel.getAssociatpart();
        if(part==null||part.equals("")||part.equals("null")){
        	throw new NullPointerException("�˹���δ������������");
        }
        Collection coll =findMainRationByPart(part);
        if(coll.size()==0){
        	throw new NullPointerException("�˹��յ����û����Ч�Ĳ��϶��");
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
      else if (gg.indexOf("��")!=-1)
    	  hd=gg.substring(0,gg.indexOf("��"));  	   	  
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
//    		   k+"��"+c+s);
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
    		   k+"��"+c+s);

      cappAssociationsPanel.setCellTextValue(row, 13,
    		   String.valueOf(roundDouble( ((BMaterialRationIfc)validRation).getAverageRoughWeight())));
       cappAssociationsPanel.setCellTextValue(row, 14,
    		   ((BMaterialRationIfc)validRation).getMUsePercent());
       
       }
       //CCEnd SS1
        
        
    }
//  CCEndby leixiao 2009-7-8 ԭ�򣺹����������壬���ӻ�ȡ���϶���
    

    /**
     * ����������
     */
    public void handelCappAssociationsPanel()
    {
        super.handelCappAssociationsPanel();
        int[] rds =
                {
                6};//CCEndby liuzc 2009-7-8 ԭ�򣺹����������壬���ӻ�ȡ���϶���
        //���õ�3����ʾJRadioButton
        cappAssociationsPanel.setRadionButtons(rds);
        //20061127Ѧ���޸ģ�����0����Ϊ�ɱ༭��
        int[] is =
                {
                0,7, 8, 9, 10, 11,12, 13,14};
        //�����п��Ա༭
        cappAssociationsPanel.setColsEnabled(is, true);
        //modify by lil in 20080904
      //  cappAssociationsPanel.setIsCalculate(true, 4);
        cappAssociationsPanel.setIsRation(true, 4);
			//CCEndby liuzc 2009-7-8 ԭ�򣺹����������壬���ӻ�ȡ���϶���
    }


    /**
     * ��ò���������
     * @throws Exception
     * @return double ����������
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
                throw new QMException("���Ϲ�������в������Ķ������Ա�����double��");
            }
        }
        return rationD;
    }

    public int getMajorMCol()
    {
        return 6;
    }


    /**
     * ����С�������λ
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
