/**
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  */
package com.faw_qm.gybom.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.faw_qm.cderp.ejb.service.MaterialSplitService;
import com.faw_qm.cderp.exception.QMXMLException;
import com.faw_qm.cderp.model.MaterialSplitIfc;
import com.faw_qm.cderp.model.MaterialStructureIfc;
import com.faw_qm.cderp.util.Messages;
import com.faw_qm.cderp.util.PartHelper;
import com.faw_qm.cderp.util.QMXMLMaterialSplit;
import com.faw_qm.cderp.util.QMXMLMaterialStructure;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.gybom.ejb.service.GYBomService;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.IteratedIfc;


 /**
 * ����ERP-bom������
 * @author ������   
 * @version 1.0
 */
public class GYBomExportToERP
{

 public static Vector vector = new Vector();
 private static List xmlMatSplitList = new ArrayList();
 public static String dwbs = "";
 public static Vector djbom = null;
  /**
   * ����BOM ָ�����͡�������BOM���ϡ�
   */
  public static Vector getExportERPBomList(QMPartIfc part) throws QMException
  {
	  PartHelper helper = new PartHelper();
		long t1 = System.currentTimeMillis();
  	System.out.println("come in helper getExportBomList carModelCode=="+part.getPartNumber());
  	Vector vec = new Vector();
		try
		{
	  		GYBomService gybs = (GYBomService)EJBServiceHelper.getService("GYBomService");
	  		 dwbs = gybs.getCurrentDWBS();
			 vec = helper.getReleaseBom(part,dwbs);
//			 System.out.println("dwbs===="+dwbs);
//			 System.out.println("doPost  vec22222222222222==="+vec.size());
			 djbom = vec;
			// System.out.println("djbom1111111111===="+djbom);
			 if(vec==null||vec.size()==0){
	  	    		System.out.println(part.getPartNumber()+"����bom����Ϊ�գ��޷���erp����");
	  	    		return null;
	  	    	}
			    Iterator iter = vec.iterator();
	  	    	Vector coll = new Vector();
		    	while(iter.hasNext()){
		    		Object[] obj = new Object[5];
		    		obj = (Object[])iter.next();
		    		QMPartIfc partIfc1 = (QMPartIfc)obj[0];
		    		coll.add(partIfc1);		    		
		    	}
		    	System.out.println("coll===="+coll);
		    	filterMaterials(coll);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
		System.out.println("over in helper getExportBomList  ��ʱ�� "+(t2-t1)+" ����");
		
		return vector;
  }
  /**
   * ����ɸѡ������¼����������㲿����
   * @param partList ��ɸѡ���㲿�����ϡ�
   * @throws QMXMLException
   */
  private final static void filterMaterials(final List partList) throws QMException
  {

      //�㲿����ֵ����
      QMPartIfc partIfc;
 
     // System.out.println("���Ｔ��Ҫ������㲿������Ϊ��"+partList.size());
      for (int i = 0; i < partList.size(); i++)
      {
          partIfc = (QMPartIfc) partList.get(i);
         // System.out.println("partIfc1111111111===="+partIfc);
                  setSubMaterial(partIfc);
      }

     
  }
  /**
   *  ��ȡ���㲿���ֽ������ϼ��ϡ�
   
   * @param partIfc ��ֵ��㲿����

   * @return Collection ���㲿�������ϼ��ϡ�
   * @throws QMXMLException
   */
  private final static void setSubMaterial(QMPartIfc partIfc) throws QMException
  {
  	PartHelper parthelper =  new PartHelper();
  	String partVersion = "";
	

	 String partnumber ="";
	 //���ݴ������ĵ���bom���ݻ�ȡ���ϱ��

	  partVersion = parthelper.getPartVersion(partIfc);
	  partnumber =parthelper.getMaterialNumber(partIfc,partVersion);

      MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
      //����ֵ����
      MaterialSplitIfc matSplitIfc;
      //����ֵ�����XML��ʽ
      QMXMLMaterialSplit xmlMatSplit;
      //�㲿����ֵ����ϵ��������
      Collection rootList=(Collection)msservice.getRootMSplit(partnumber,partVersion);
    //  System.out.println("rootList===="+rootList.size());
      Iterator rootMatSplitIter = rootList.iterator();
    //  HashMap notPublishMat=new HashMap();
      //���ϸ��㲿����ֵ�����
      while (rootMatSplitIter.hasNext())
      {
          matSplitIfc = (MaterialSplitIfc) rootMatSplitIter.next();



              xmlMatSplit = new QMXMLMaterialSplit(matSplitIfc);
              //System.out.println("partIfc333333333===="+partIfc);
              xmlMatSplit.setPartIfc(partIfc);

              setSubMatStructPubType(partnumber,xmlMatSplit);

          }
      

  }
/**
 * �����¼����Ͻṹ�����ϵķ�����ǡ�
 *
 * @param rootPartNumber���������ϵ��㲿����š�
 * @param rootPartVersionID���������ϵ��㲿���汾��
 * @param matSplitIfc������ֵ�����XML��ʽ
 * @param partMap������㲿��ֵ�����HashMap
 * @throws QMException
 */
private static void setSubMatStructPubType(String rootPartNumber
        , QMXMLMaterialSplit xmlMatSplit
        ) throws QMException
{
	//System.out.println("partIfc44444444444===="+xmlMatSplit.getPartIfc());
    // �����¼����Ͻṹ���¼����ϵķ������
 //   0-��ײ����ϣ�1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ�
    System.out.println("xmlMatSplit.getMaterialSplit().getStatus() =="+xmlMatSplit.getMaterialSplit().getStatus());
    if(xmlMatSplit.getMaterialSplit().getStatus() == 1)//
    {



             
            //�õ������ϵ��¼����ϣ�����ط�ֻ�ǻ���˶������ϵ���һ������
            List list = getMatStruct(xmlMatSplit.getPartNumber(), xmlMatSplit
                    .getMaterialNumber());

            //���Ͻṹֵ����
            MaterialStructureIfc matStructIfc;
            //���Ͻṹֵ����XML��ʽ
          //  QMXMLMaterialStructure xmlMatStruct;
            //�¼�����ֵ����
            MaterialSplitIfc subMatSplitIfc;
            //�¼�����ֵ����XML��ʽ
            QMXMLMaterialSplit subXMlMatSplit;
            //�㲿��ֵ����
           // QMPartIfc partIfc = null;
            String partIdentity = "";
            for (int i = 0; i < list.size(); i++)
            {
                matStructIfc = (MaterialStructureIfc) list.get(i);
         
                    if(matStructIfc.getParentPartNumber().equals(
                            xmlMatSplit.getPartNumber()))
                    {
                       // xmlMatStruct.setParentPartVersion(rootPartVersionID);
                    }

                 
                    subMatSplitIfc = getMatSplitIfc(matStructIfc
                            .getChildNumber());

                 
     
                        subXMlMatSplit = new QMXMLMaterialSplit(subMatSplitIfc);

                        subXMlMatSplit.setPartIfc(xmlMatSplit.getPartIfc());
                        xmlMatSplitList.add(subXMlMatSplit);
                        setSubMatStructPubType(rootPartNumber,
                                subXMlMatSplit);
                    
                
            }
           
       
    }
    //�����㲿��ʹ�ýṹ�ķ������
    //�����Ӽ��ṹʱ����Ҫ������ɾ���Ľṹ��Ϣ
    else if(xmlMatSplit.getMaterialSplit().getStatus() == 2||xmlMatSplit.getMaterialSplit().getStatus() == 0)
    {
    
    		filterByStructureRule(rootPartNumber,
                    xmlMatSplit);
 
        
    }
    
}
/**
 * �ṹ����������㲿���İ汾�仯ʱʹ�ô˹���
 * ���Խṹ���еݹ鴦��ֻ�����һ���ṹ��
 * @param rootPartNumber �������ϵ��㲿�����
 * @param rootPartVersionID �������ϵ��㲿���汾
 * @param xmlMatSplit Ҫ�����㲿��ʹ�ýṹ������ǵ�����ֵ�����XML��ʽ��
 * @throws QMException
 */
public final static void filterByStructureRule(String rootPartNumber
        , QMXMLMaterialSplit xmlMatSplit)
        throws QMException 
{
  
    //�������
 //   final String publishType = xmlMatSplit.getPublishType();

   // PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
  
  //  System.out.println("publishTypepublishType====="+publishType+"xmlMatSplitxmlMatSplitxmlMatSplit222"+xmlMatSplit.getMaterialNumber());

    //�������㲿����ʹ�ýṹ

    Vector newUsageLinkMap = getUsageLinkMap(xmlMatSplit.getPartIfc());

    System.out.println("newUsageLinkMap==="+newUsageLinkMap);
      if(newUsageLinkMap==null||newUsageLinkMap.size()==0)
    	  return;

        
        //0û���¼����ϣ�2���¼��Ӽ���1���¼����Ʒ
        if(xmlMatSplit.getMaterialSplit().getStatus()==0)
        {
        	
        }else if(xmlMatSplit.getMaterialSplit().getStatus()==2)
        {
        	
//  
            //�㲿��ֵ����
          //  QMPartIfc partIfc = null;
         

            //�����״̬�����Ӽ������õ�ǰ���Ӽ����ϴη������ݽ��бȽϣ��ֱ�����ɾ�����������޸ĺ����ñ��
            //�����״̬��û���Ӽ������ϴη����������Ӽ��ṹɾ����ʵ������Ϊ��ǰ������״̬Ϊ2�����Բ�����û���Ӽ���
        //    System.out.println("newUsageLinkMapnewUsageLinkMapnewUsageLinkMap======="+newUsageLinkMap);
        	for(int i=0;i<newUsageLinkMap.size();i++){
        
        		String[] aa = (String[])newUsageLinkMap.elementAt(i);
        		// ���������������������
                 setPartStructPubType(xmlMatSplit, aa);
        	}
           
              
            
            
     	
        }
   
  
    
}
/**
 * �����㲿��ʹ�ýṹ����ʱ�����Ͻṹ��Ϣ��
 * @param rootPartNumber ������ϵ��㲿�����
 * @param rootPartVersionID ������ϵ��㲿���汾
 * @param strctPublishType ���Ͻṹ������ʶ
 * @param xmlMatSplit �����ϵ�XML��ʽ
 * @param partUsageLinkIfc �㲿��ʹ�ù�ϵ
 * @throws QMException
 */
public static void setPartStructPubType(
        QMXMLMaterialSplit xmlMatSplit, String[] a)
        throws QMException
{
    //���Ͻṹֵ����
	String exp[] = new String[4];
   // MaterialStructureIfc matStructIfc = new MaterialStructureInfo();
   // matStructIfc.setParentPartNumber(xmlMatSplit.getPartNumber());
    exp[0]=xmlMatSplit.getMaterialNumber();//������
    exp[2]=a[3]; //����
    exp[3]="��";  //��λ
    
  
    //��ѯ�����Ӽ��Ķ�������
    List rootMatNumberList = getRootMatSplit(a, xmlMatSplit.getRouteCode());

    String childNumber = "";
    for (int i = 0; i < rootMatNumberList.size(); i++)
    {
        childNumber = (String) rootMatNumberList.get(i);
        exp[1]=childNumber;//�Ӽ���
    }
    System.out.println("exp==="+exp);
    vector.add(exp);
}
/**
 * ��ȡ�������
 * @param childBsoID���Ӽ���MasterID
 * @param parentRouteCode�������ϵ�·�ߴ���
 * @return
 * @throws QMException
 */
public static List getRootMatSplit(String[] a, String parentRouteCode)
        throws QMException
{
	PartHelper partHelper =  new PartHelper();
    List rootMatNumberList = new ArrayList();
   
        //��ѯ�Ӽ���Masterֵ����
        try
        {
        	//PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
            //1. ���Ȳ�ѯ���Ӽ����°汾
            QMPartMasterIfc childPartIfc = null;

//			 }
            Collection rootList = (Collection)msservice.getRootMSplit(a[0],a[2]);
            Iterator iter = rootList.iterator();
            //�Ƿ���Ҫƥ��ı�־
            boolean isMatching = RemoteProperty.getProperty(
                    "com.faw_qm.cderp.isMatching", "false").equalsIgnoreCase(
                    "true");
            MaterialSplitIfc matSplitIfc;
            while (iter.hasNext())
            {
                matSplitIfc = (MaterialSplitIfc) iter.next();
                //�����Ҫƥ�䣬��������¹ҽӵ��Ӽ���·�ߴ�������븸���ϵ�·�ߴ���һ�£�
                //��������ϵ����ж������϶�����������������򲻹ҽӸ��Ӽ�
                if(isMatching)
                {
                    if(matSplitIfc.getRouteCode().equals(parentRouteCode))
                    {
                        rootMatNumberList.add(matSplitIfc
                                .getMaterialNumber());
                        break;
                    }
                }
                else
                {
                    rootMatNumberList.add(matSplitIfc.getMaterialNumber());
                }
            }
            //3 ������Ҫƥ���������ϵĶ������ϣ�û�в���Ӽ���ʱ���������Ϻ�Ϊ�㲿����
            if(!isMatching && rootMatNumberList.size() <= 0)
            {
            	
           
            	String childNumber =a[0];
                rootMatNumberList.add(childNumber);
            }

        }
        catch (QMException e)
        {
            throw new QMException(e);
        }
 
    return rootMatNumberList;
}
/**
 * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
 * �������HashMap�����У���ΪPartUsageLinkIfc��ֵΪPartUsageLinkIfc��
 * @param partIfc �㲿����
 * @throws QMXMLException
 */
private final static Vector getUsageLinkMap(QMPartIfc partIfc)
        throws QMXMLException
{
	
   
    final Vector usageLinkMap = new Vector();

    if(djbom==null)
    	return usageLinkMap;
    Iterator iter = djbom.iterator();
    PartHelper helper = new PartHelper();
    while (iter.hasNext())
    {
        Object[] obj = new Object[5];
    	obj = (Object[])iter.next();
    	String[] aa = new String[5];
    	//String[] bb = new String[5];
        aa[0]=(String) obj[1]; //��Ŵ��汾
        aa[1]=""; //����
        QMPartIfc part=(QMPartIfc)obj[0]; 
    	aa[2]=part.getVersionID();
    	aa[3]=(String)obj[2]; // ����
//    	bb=(String[])usageLinkMap.get(aa[0]);
//    	if(bb!=null){
//    		aa[3] = String.valueOf(Integer.parseInt(aa[3])+Integer.parseInt(bb[3]));
//    	}
       
        aa[4]=(String)obj[3]; // �������

//        System.out.println("aa[0]11111111111="+aa[0]);
//        System.out.println("aa[3]11111111111="+aa[3]);
//        System.out.println("aa[4]11111111111="+aa[4]);
//        System.out.println("partIfc222222="+partIfc);
        if(aa[4].equals(partIfc.getPartNumber())){
        	//��ֵΪ���+����
        	 usageLinkMap.add(aa);
        }
       
    }
  
    return usageLinkMap;
}
/**
 *  ��ȡ�㲿����ֵ����Ͻṹ���ϡ�
 * @param parentPartNumber�� �����š�
 * @param parentNumber�� �����Ϻš�
 * @return List ���㲿����ֵ����ϼ��ϡ�
 * @throws QMXMLException
 */
private final static List getMatStruct(String parentPartNumber, String parentNumber)
        throws QMXMLException
{
   
    List resultList = new ArrayList();
    try
    {
    	if(dwbs.equals("W34")){
    		MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
        	resultList=(List)msservice.getMStructure(parentPartNumber, parentNumber);
    	}else{
    		MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
        	resultList=(List)msservice.getMStructure(parentPartNumber, parentNumber);
    	}
    }
    catch (QMException e)
    {
       
        throw new QMXMLException(e);
    }
   
    return resultList;
} 
/**
 * �������ϺŻ�ȡ���ϡ�
 * @param materialNumber�����Ϻ�
 * @return
 * @throws QMException
 */
private static MaterialSplitIfc getMatSplitIfc(String materialNumber)
        throws QMException
{
   
    MaterialSplitIfc filterMaterialSplit = null;
    try
    {
    	MaterialSplitService msservice = null;
    	if(dwbs.equals("W34")){
    		 msservice=(MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
        
    	}else{
    		 msservice=(MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");

    	}
        //��ѯ���Ӽ��Ķ������ϼ���
    	filterMaterialSplit=(MaterialSplitIfc)msservice.getMSplit(materialNumber);
    }
    catch (QMException e)
    {
    
        throw new QMException(e);
    }
    return filterMaterialSplit;
}
/**
 * �õ�����ֵ�����ֵ��㲿����Ϣ����parts�еõ�����ˢ�����ݿ�õ���
 * @param matSplitIfc:����ֵ����
 * @param partMap
 * @return ����ֵ�����ֵ��㲿����Ϣ
 * @throws QMException
 */
//private static QMPartIfc getPartIfc(MaterialSplitIfc matSplitIfc)
//        throws QMException
//{
//
//    //�㲿��ֵ����
//    QMPartIfc partIfc = null;
//   
////    	System.out.println("matSplitIfc.getPartNumber()="+matSplitIfc.getPartNumber()); 
////    	System.out.println("matSplitIfc.getPartVersion()="+matSplitIfc.getPartVersion()); 
//    	String[] a = matSplitIfc.getPartNumber().split("/");
//    	String partnumber= a[0];
//    	System.out.println("partnumber="+partnumber); 
//        Collection partCol = getPartCol(partnumber,
//                matSplitIfc.getPartVersion());
//        Iterator partIter = partCol.iterator();
//        if(partIter.hasNext())
//        {
//            try
//            {
//                partIfc = (QMPartIfc) getLatestIteration((QMPartIfc) partIter
//                        .next());
//                System.out.println("partIfc="+partIfc); 
//            }
//            catch (QMException e)
//            {
//                //"���ұ��Ϊ*���汾Ϊ*�������㲿������ʱ����"
//               
//                throw new QMException(e);
//            }
//        }
//    
//    if(partIfc == null)
//    {
//        //"�޷��õ����Ϻ�Ϊ*�����ϲ�ֵ��㲿������Ϣ!"
//        
//        throw new QMException(Messages.getString("Util.64",
//                new Object[]{matSplitIfc.getMaterialNumber()}));
//    }
//  
//    return partIfc;
//}
/**
 * �����㲿���źͰ汾��ȡ�㲿����
 * @param partNumber���㲿���ı��
 * @param partVersionid���㲿���İ汾
 * @return
 * @throws QMException
 */
private static Collection getPartCol(String partNumber, String partVersionid)
        throws QMException
{
	PartHelper helper = new PartHelper();
	partNumber = helper.getPartNumber(partNumber);

    Collection filterMaterialSplitCol = null;
    try
    {
    	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        QMQuery query = new QMQuery("QMPart", "QMPartMaster");
        query
                .addCondition(0, 1, new QueryCondition("masterBsoID",
                        "bsoID"));
        query.addAND();
        query.addCondition(1, new QueryCondition("partNumber",
                QueryCondition.EQUAL, partNumber));
        query.addAND();
        query.addCondition(0, new QueryCondition("versionID",
                QueryCondition.EQUAL, partVersionid));
        query.setVisiableResult(1);
        filterMaterialSplitCol=(Collection)pservice.findValueInfo(query);
    }
    catch (QMException e)
    {
 
        throw new QMException(e);
    }
   
    return filterMaterialSplitCol;
}
/**
 * ������°���ֵ����
 * @param bsoID
 * @return
 * @throws QMException
 */
private static BaseValueIfc getLatestIteration(IteratedIfc iteratedIfc)
        throws QMException
{

    BaseValueIfc baseIfc = null;
    VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
    // ˢ�����°���
    baseIfc=(BaseValueIfc)vcservice.getLatestIteration(iteratedIfc);

    return baseIfc;
}
}
