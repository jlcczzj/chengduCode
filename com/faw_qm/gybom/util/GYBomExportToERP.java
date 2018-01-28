/**
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
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
 * 导出ERP-bom辅助类
 * @author 刘家坤   
 * @version 1.0
 */
public class GYBomExportToERP
{

 public static Vector vector = new Vector();
 private static List xmlMatSplitList = new ArrayList();
 public static String dwbs = "";
 public static Vector djbom = null;
  /**
   * 导出BOM 指定车型、工厂的BOM集合。
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
	  	    		System.out.println(part.getPartNumber()+"工艺bom数据为空，无法向erp发布");
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
		System.out.println("over in helper getExportBomList  用时： "+(t2-t1)+" 毫秒");
		
		return vector;
  }
  /**
   * 根据筛选结果表记录保存规则处理零部件。
   * @param partList 待筛选的零部件集合。
   * @throws QMXMLException
   */
  private final static void filterMaterials(final List partList) throws QMException
  {

      //零部件的值对象
      QMPartIfc partIfc;
 
     // System.out.println("这里即将要处理的零部件数量为："+partList.size());
      for (int i = 0; i < partList.size(); i++)
      {
          partIfc = (QMPartIfc) partList.get(i);
         // System.out.println("partIfc1111111111===="+partIfc);
                  setSubMaterial(partIfc);
      }

     
  }
  /**
   *  获取该零部件分解后的物料集合。
   
   * @param partIfc 拆分的零部件。

   * @return Collection 该零部件的物料集合。
   * @throws QMXMLException
   */
  private final static void setSubMaterial(QMPartIfc partIfc) throws QMException
  {
  	PartHelper parthelper =  new PartHelper();
  	String partVersion = "";
	

	 String partnumber ="";
	 //根据串进来的单级bom数据获取物料编号

	  partVersion = parthelper.getPartVersion(partIfc);
	  partnumber =parthelper.getMaterialNumber(partIfc,partVersion);

      MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
      //物料值对象
      MaterialSplitIfc matSplitIfc;
      //物料值对象的XML形式
      QMXMLMaterialSplit xmlMatSplit;
      //零部件拆分的物料的最顶级物料
      Collection rootList=(Collection)msservice.getRootMSplit(partnumber,partVersion);
    //  System.out.println("rootList===="+rootList.size());
      Iterator rootMatSplitIter = rootList.iterator();
    //  HashMap notPublishMat=new HashMap();
      //加上该零部件拆分的物料
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
 * 设置下级物料结构和物料的发布标记。
 *
 * @param rootPartNumber：顶级物料的零部件编号。
 * @param rootPartVersionID：顶级物料的零部件版本。
 * @param matSplitIfc：物料值对象的XML形式
 * @param partMap：存放零部件值对象的HashMap
 * @throws QMException
 */
private static void setSubMatStructPubType(String rootPartNumber
        , QMXMLMaterialSplit xmlMatSplit
        ) throws QMException
{
	//System.out.println("partIfc44444444444===="+xmlMatSplit.getPartIfc());
    // 设置下级物料结构和下级物料的发布标记
 //   0-最底层物料，1-有下级物料，2—此物料下要挂接原零部件的子件
    System.out.println("xmlMatSplit.getMaterialSplit().getStatus() =="+xmlMatSplit.getMaterialSplit().getStatus());
    if(xmlMatSplit.getMaterialSplit().getStatus() == 1)//
    {



             
            //得到该物料的下级物料，这个地方只是获得了顶级物料的下一级物料
            List list = getMatStruct(xmlMatSplit.getPartNumber(), xmlMatSplit
                    .getMaterialNumber());

            //物料结构值对象
            MaterialStructureIfc matStructIfc;
            //物料结构值对象XML形式
          //  QMXMLMaterialStructure xmlMatStruct;
            //下级物料值对象
            MaterialSplitIfc subMatSplitIfc;
            //下级物料值对象XML形式
            QMXMLMaterialSplit subXMlMatSplit;
            //零部件值对象
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
    //设置零部件使用结构的发布标记
    //发布子件结构时，需要发布被删除的结构信息
    else if(xmlMatSplit.getMaterialSplit().getStatus() == 2||xmlMatSplit.getMaterialSplit().getStatus() == 0)
    {
    
    		filterByStructureRule(rootPartNumber,
                    xmlMatSplit);
 
        
    }
    
}
/**
 * 结构处理规则，在零部件的版本变化时使用此规则：
 * 不对结构进行递归处理，只处理第一级结构。
 * @param rootPartNumber 顶级物料的零部件编号
 * @param rootPartVersionID 顶级物料的零部件版本
 * @param xmlMatSplit 要设置零部件使用结构发布标记的物料值对象的XML形式。
 * @throws QMException
 */
public final static void filterByStructureRule(String rootPartNumber
        , QMXMLMaterialSplit xmlMatSplit)
        throws QMException 
{
  
    //发布标记
 //   final String publishType = xmlMatSplit.getPublishType();

   // PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
  
  //  System.out.println("publishTypepublishType====="+publishType+"xmlMatSplitxmlMatSplitxmlMatSplit222"+xmlMatSplit.getMaterialNumber());

    //发布的零部件的使用结构

    Vector newUsageLinkMap = getUsageLinkMap(xmlMatSplit.getPartIfc());

    System.out.println("newUsageLinkMap==="+newUsageLinkMap);
      if(newUsageLinkMap==null||newUsageLinkMap.size()==0)
    	  return;

        
        //0没有下级物料，2有下级子件，1有下级半成品
        if(xmlMatSplit.getMaterialSplit().getStatus()==0)
        {
        	
        }else if(xmlMatSplit.getMaterialSplit().getStatus()==2)
        {
        	
//  
            //零部件值对象
          //  QMPartIfc partIfc = null;
         

            //如果新状态下有子件，则用当前的子件与上次发布内容进行比较，分别设置删除，新增、修改和沿用标记
            //如果新状态下没有子件，则将上次发布的所有子件结构删除（实际上因为当前发布的状态为2，所以不可能没有子件）
        //    System.out.println("newUsageLinkMapnewUsageLinkMapnewUsageLinkMap======="+newUsageLinkMap);
        	for(int i=0;i<newUsageLinkMap.size();i++){
        
        		String[] aa = (String[])newUsageLinkMap.elementAt(i);
        		// 发布新增的物料零件关联
                 setPartStructPubType(xmlMatSplit, aa);
        	}
           
              
            
            
     	
        }
   
  
    
}
/**
 * 设置零部件使用结构发布时的物料结构信息。
 * @param rootPartNumber 最顶级物料的零部件编号
 * @param rootPartVersionID 最顶级物料的零部件版本
 * @param strctPublishType 物料结构发布标识
 * @param xmlMatSplit 父物料的XML形式
 * @param partUsageLinkIfc 零部件使用关系
 * @throws QMException
 */
public static void setPartStructPubType(
        QMXMLMaterialSplit xmlMatSplit, String[] a)
        throws QMException
{
    //物料结构值对象
	String exp[] = new String[4];
   // MaterialStructureIfc matStructIfc = new MaterialStructureInfo();
   // matStructIfc.setParentPartNumber(xmlMatSplit.getPartNumber());
    exp[0]=xmlMatSplit.getMaterialNumber();//父件号
    exp[2]=a[3]; //数量
    exp[3]="个";  //单位
    
  
    //查询顶级子件的顶级物料
    List rootMatNumberList = getRootMatSplit(a, xmlMatSplit.getRouteCode());

    String childNumber = "";
    for (int i = 0; i < rootMatNumberList.size(); i++)
    {
        childNumber = (String) rootMatNumberList.get(i);
        exp[1]=childNumber;//子件号
    }
    System.out.println("exp==="+exp);
    vector.add(exp);
}
/**
 * 获取最顶层物料
 * @param childBsoID：子件的MasterID
 * @param parentRouteCode：父物料的路线代码
 * @return
 * @throws QMException
 */
public static List getRootMatSplit(String[] a, String parentRouteCode)
        throws QMException
{
	PartHelper partHelper =  new PartHelper();
    List rootMatNumberList = new ArrayList();
   
        //查询子件的Master值对象
        try
        {
        	//PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        	MaterialSplitService msservice=(MaterialSplitService)EJBServiceHelper.getService("CDMaterialSplitService");
            //1. 首先查询到子件最新版本
            QMPartMasterIfc childPartIfc = null;

//			 }
            Collection rootList = (Collection)msservice.getRootMSplit(a[0],a[2]);
            Iterator iter = rootList.iterator();
            //是否需要匹配的标志
            boolean isMatching = RemoteProperty.getProperty(
                    "com.faw_qm.cderp.isMatching", "false").equalsIgnoreCase(
                    "true");
            MaterialSplitIfc matSplitIfc;
            while (iter.hasNext())
            {
                matSplitIfc = (MaterialSplitIfc) iter.next();
                //如果需要匹配，则该物料下挂接的子件的路线代码必须与父物料的路线代码一致，
                //如果子物料的所有顶级物料都不满足这个条件，则不挂接该子件
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
            //3 当不需要匹配且子物料的顶级物料（没有拆分子件）时，则子物料号为零部件号
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
 * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
 * 结果存入HashMap集合中，键为PartUsageLinkIfc，值为PartUsageLinkIfc。
 * @param partIfc 零部件。
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
        aa[0]=(String) obj[1]; //编号带版本
        aa[1]=""; //子组
        QMPartIfc part=(QMPartIfc)obj[0]; 
    	aa[2]=part.getVersionID();
    	aa[3]=(String)obj[2]; // 数量
//    	bb=(String[])usageLinkMap.get(aa[0]);
//    	if(bb!=null){
//    		aa[3] = String.valueOf(Integer.parseInt(aa[3])+Integer.parseInt(bb[3]));
//    	}
       
        aa[4]=(String)obj[3]; // 父件编号

//        System.out.println("aa[0]11111111111="+aa[0]);
//        System.out.println("aa[3]11111111111="+aa[3]);
//        System.out.println("aa[4]11111111111="+aa[4]);
//        System.out.println("partIfc222222="+partIfc);
        if(aa[4].equals(partIfc.getPartNumber())){
        	//键值为编号+数量
        	 usageLinkMap.add(aa);
        }
       
    }
  
    return usageLinkMap;
}
/**
 *  获取零部件拆分的物料结构集合。
 * @param parentPartNumber： 父件号。
 * @param parentNumber： 父物料号。
 * @return List 该零部件拆分的物料集合。
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
 * 根据物料号获取物料。
 * @param materialNumber：物料号
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
        //查询该子件的顶级物料集合
    	filterMaterialSplit=(MaterialSplitIfc)msservice.getMSplit(materialNumber);
    }
    catch (QMException e)
    {
    
        throw new QMException(e);
    }
    return filterMaterialSplit;
}
/**
 * 得到物料值对象拆分的零部件信息，从parts中得到或者刷新数据库得到。
 * @param matSplitIfc:物料值对象
 * @param partMap
 * @return 物料值对象拆分的零部件信息
 * @throws QMException
 */
//private static QMPartIfc getPartIfc(MaterialSplitIfc matSplitIfc)
//        throws QMException
//{
//
//    //零部件值对象
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
//                //"查找编号为*，版本为*的最新零部件版序时出错！"
//               
//                throw new QMException(e);
//            }
//        }
//    
//    if(partIfc == null)
//    {
//        //"无法得到物料号为*的物料拆分的零部件的信息!"
//        
//        throw new QMException(Messages.getString("Util.64",
//                new Object[]{matSplitIfc.getMaterialNumber()}));
//    }
//  
//    return partIfc;
//}
/**
 * 根据零部件号和版本获取零部件。
 * @param partNumber：零部件的编号
 * @param partVersionid：零部件的版本
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
 * 获得最新版序值对象
 * @param bsoID
 * @return
 * @throws QMException
 */
private static BaseValueIfc getLatestIteration(IteratedIfc iteratedIfc)
        throws QMException
{

    BaseValueIfc baseIfc = null;
    VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
    // 刷新最新版序
    baseIfc=(BaseValueIfc)vcservice.getLatestIteration(iteratedIfc);

    return baseIfc;
}
}
