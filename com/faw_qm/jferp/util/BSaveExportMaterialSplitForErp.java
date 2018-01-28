/**
 * ���ɳ���QMXMLMaterialSplit.java	1.0              2007-9-27
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;


import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.dtd.AttributeDecl;
import org.dom4j.dtd.ElementDecl;
import org.dom4j.io.OutputFormat;

import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultDocumentType;

import com.faw_qm.baseline.ejb.service.BaselineService;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.domain.util.DomainHelper;
import com.faw_qm.jferp.client.ejbaction.SplitMaterielEJBAction;
import com.faw_qm.jferp.ejb.service.IntePackService;
import com.faw_qm.jferp.ejb.service.MaterialSplitService;
import com.faw_qm.jferp.ejb.service.MaterialSplitServiceEJB;
import com.faw_qm.jferp.exception.ERPException;
import com.faw_qm.jferp.model.FilterPartIfc;
import com.faw_qm.jferp.model.FilterPartInfo;
import com.faw_qm.jferp.model.IntePackIfc;
import com.faw_qm.jferp.model.IntePackInfo;
import com.faw_qm.jferp.model.MaterialPartStructureInfo;
import com.faw_qm.jferp.model.MaterialSplitIfc;
import com.faw_qm.jferp.model.MaterialSplitInfo;
import com.faw_qm.jferp.model.MaterialStructureIfc;
import com.faw_qm.jferp.model.MaterialStructureInfo;
import com.faw_qm.jferp.util.Messages;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.iba.definition.model.StringDefinitionIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.load.util.LoadHelper;
import com.faw_qm.jferp.util.PublishData;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
/**  
 * <p>Title: ��ʷ���㲿�������ϴ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author 
 * @version 1.0     
 */
public class BSaveExportMaterialSplitForErp   
{
	private static final long serialVersionUID = 1L;



	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
	.getLogger(MaterialSplitServiceEJB.class);



	/**
	 * ���ۺŷָ��������ڷָ�·�ߴ��롣
	 */
	private String dashDelimiter = "-";

	/**
	 * ���������ļ��õ��������ϺŵĹ��ɷ�ʽ��true�������+·�ߴ��룻false������š�
	 */
	private static boolean addRouteCode = RemoteProperty.getProperty(
			"addRouteCode", "false").equalsIgnoreCase("true");


	/**
	 * ���ϲ�ֵĹ�����
	 */
	private static String mSplitDefaultDomainName = (String) RemoteProperty
	.getProperty("materialSplitDefaultDomain", "System");

	private static final String RESOURCE = "com.faw_qm.jferp.util.ERPResource";

	// 20080103 begin
	/**
	 * �������ʱ��������·�ߴ�������������ϱ�������磺ɢ�����ġ��ɹ������룬��ֺ���������������ʱ�����ɹ��������Ӧ�����ϱ��ʹ������ţ��������ϱ��ʹ�ã������+��-��+·�ߴ��롣
	 */
	private static final String specialRouteCode = RemoteProperty.getProperty(
			"specialRouteCode", "�ɹ�");
	private HashMap partList=new HashMap();
	
	/**
	 * �ڹ��������Ӽ��Ĺ��գ��ԡ�������Ϊ�ָ����������ù���ʱ��������������ϵĲ��Ŵ�������һ�����ŵĴ��롣
	 */
	// private static final String hasSubpartTech = RemoteProperty.getProperty(
	// "hasSubpartTech", "01");
	// 20080103 begin
	/**
	 * �ԡ�������Ϊ�ָ������������ʱ��Ҫ��������ж��·�ߴ����Ҹ�·�ߴ��벻���ڵ�һ�������ڲ��ʱû�и�·�ߴ�������ϣ�
	 * ����·����ȥ����·�ߴ�����ٲ�֣��磺ɢ�����ġ��ɹ������룬��ֺ������������㲿��ʱ�����·��ΪSX-DH-CG��
	 * ����·��SX-DH�������·��ΪSX-CG-DH������·��SX-DH�������·��ΪCG-SX-DH��
	 * ����Ȼ����·��CG-SX-DH�������·��ֻ��CG�����Ե���·��CG����
	 */
	private static final String deleteWhenSplitRouteCode = RemoteProperty
	.getProperty("deleteWhenSplitRouteCode", "�ɹ�");

	/**
	 * deleteWhenSplitRouteCode�ļ��ϡ�
	 */
	private static HashMap specRCHashMap = null;


	/**
	 * ���ŷָ��������ڷָ�useProcessPartRouteCode��
	 * 
	 */
	private String delimiter = "��";
	private String delimiter1 = ",";
	/**
     * ��ŷ���������XMLMaterialSplitIfc��
     */
    private ArrayList xmlMatSplitList = new ArrayList();

    /**
     * ��ŷ��������ϽṹXMLMaterialStructureIfc��
     */
    private ArrayList xmlMatStructList = new ArrayList();
    private int fileNumber=1;
    private HashMap ibaDefinitionMap=new HashMap();
    private HashMap ibaValueMap=new HashMap();
	/**
	 * ����������
	 */
	public void mainExport() throws QMException {
		try{
			System.out.println("��������ʷ���ݱ���ķ����ˣ�����������������");
		String driverName = "oracle.jdbc.driver.OracleDriver";
		Class.forName(driverName).newInstance();
//		String URL = "jdbc:oracle:thin:@10.151.10.101:1521:qmcappdb";
//		Connection conn = java.sql.DriverManager.getConnection(URL, "capp",
//		"capp");
//		String URL = "jdbc:oracle:thin:@10.151.13.58:1521:capptest";
		//1111111111111111111
		String URL = "jdbc:oracle:thin:@10.151.10.101:1521:qmcappdb";
//		String URL = "jdbc:oracle:thin:@10.28.68.53:1521:qingqi";
		//�������ݿ�20101003�������õ�
//		String URL = "jdbc:oracle:thin:@10.151.15.166:1521:cappdemo";
//		Connection conn = java.sql.DriverManager.getConnection(URL, "capptest",
//				"capp4cs");
		
		
		//�������ݿ�20101003�������õ�
		Connection conn = java.sql.DriverManager.getConnection(URL, "capp", "capp");
//		Connection conn = java.sql.DriverManager.getConnection(URL, "qingqi",
//		"qingqi");
//		Connection conn = java.sql.DriverManager.getConnection(URL, "capptest",
//		"capp4cs");
//		Connection conn = java.sql.DriverManager.getConnection(URL, "capptest",
//		"capp4cs");
		ResultSet result = null;
		Statement select = conn.createStatement();
		//111111111111111111111
		result = select.executeQuery("select * from mesdata_change");
//		result = select.executeQuery("select * from mesdatacsbg");
		
		//ArrayList part=new ArrayList();
//		������е�IBA���Զ���
		PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
		QMQuery definitionQuery=new QMQuery("StringDefinition");
		Collection definCol=pService.findValueInfo(definitionQuery);
		Iterator definIte=definCol.iterator();
		while(definIte.hasNext())
		{
			StringDefinitionIfc sdi=(StringDefinitionIfc)definIte.next();
			this.ibaDefinitionMap.put(sdi.getName(),sdi.getBsoID());
		}
		//��һ������
//		result.next();
		ArrayList AllParts=new ArrayList();
		String[] partData=new String[5];
        while (result.next()) {
        	partData=new String[5];
        	String partNumber = result.getString("partNumber");
        	String baseLineName=result.getString("baseLineName");
    		String versionValue = result.getString("partversion");
    		String mainRoute = result.getString("partroute");
    		String masterid=result.getString("masterbsoid");
    		partData[0]=partNumber;
    		partData[1]=baseLineName;
    		partData[2]=versionValue;
    		//��;�ż�����·��
    		partData[3]=mainRoute; 
    		partData[4]=masterid; 
    		AllParts.add(partData);
		}
        int a=(int)AllParts.size()/3000;
		   int b=AllParts.size()%3000;
		   if(b>0)
		   {
		    a=a+1;
		   }
		   for(int j=0;j<a;j++)
		   {
			   System.out.println("jjjjjjjjjj========="+j);
			   List listzh=new ArrayList();
			   int len=0;
		    if(j!=a-1)
		    {
		    	len=3000;
		    	
		    }
		    else if(j==a-1 && b>0)
		    {
		    	len=b;
		    }
		    else
		    {
		    	len=3000;
		    }
		    for(int k=0;k<len;k++)
		    {
		    	listzh.add(AllParts.get(j*3000+k)); 	
		    }
		    forErpSplit(listzh);
		   }
		conn.commit();
		conn.close();
		System.out.println("fabuwanbi-------");
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new QMException(e);
		}
		
		
	}
	public Vector forErpSplit(List list)throws QMException
	{
		HashMap hamap =new HashMap();
//		boolean flag = false;
		PersistService pService ;
		QMPartIfc partifc=null;
		this.xmlMatSplitList=new ArrayList();
		this.xmlMatStructList=new ArrayList();
		partList=new HashMap();
		Vector filterPartMap=new Vector();
		
        Collection ac1=new Vector();
        Vector filterPartVec = null;
		ManagedBaselineIfc baselineifc=null;
		 BaselineService baseLineEjb = (BaselineService)EJBServiceHelper.getService("BaselineService");
		pService = (PersistService)EJBServiceHelper.getService("PersistService");
		//���ÿһ���㲿�����д���
		 SessionService sessions = null;
		for(int i = 0; i < list.size(); i++)
		{
			Collection ac=new Vector();
//			Collection aczz=new Vector();
	        Collection ac1zz=new Vector();
			String routeAsString = "";
//			ȫ·�ߴ�
			String routeAllCode = "";
//			װ��·��
			String routeAssemStr = "";
			//�������·��
			String routeAsStringQian="";
			String masterbsoid="";
			String partNumber ="";
			String partVersionValue="";
			
			String  baseLineName="";
			String[] partQuan=(String[])list.get(i);
			//�㲿�����
			partNumber=partQuan[0];
			masterbsoid=partQuan[4];
			baseLineName=partQuan[1];
			//�㲿���汾
			partVersionValue=partQuan[2];;
			routeAllCode=partQuan[3];
			
			if(masterbsoid==null||masterbsoid.trim().length()==0)
			{
				System.out.println(partNumber+"��Ӧ��id��ϢΪ�գ�������");
				continue;
			}
			 baselineifc= getBaselineByName(baseLineName);
			if(baseLineName.toString().equals("�����°淢��")){
   			 QMQuery qmquery = new QMQuery("QMPart");
   			 qmquery.addCondition(new QueryCondition("iterationIfLatest",true));
   			 qmquery.addAND();
   			 QueryCondition condition1 = new QueryCondition("versionID", "=",
   					partVersionValue);
   	          qmquery.addCondition(condition1);
   	          qmquery.addAND();
   	          qmquery.addCondition(new QueryCondition("masterBsoID", "=",
   	        		  masterbsoid));
   	          Collection col1 = pService.findValueInfo(qmquery, false);
   	          
   	          System.out.println("cccccccccccccccccccccccc===========aaaaaaa==="+col1);
//   	          System.out.println("Here got the part "+partNumber+",and the result is:"+col1.size()+";and the route is:"+partQuan[2]);
   	          if(col1.size()>0){
   	          Iterator iter = col1.iterator();
   	          if (iter.hasNext()) {
   	        	  partifc=(QMPartIfc)iter.next();
   	          }
   	          }else{
   	        	continue;
   	          }
   	          System.out.println("partifcpartifcpartifc==========="+partifc.getPartNumber());
   	          
//           String partBsoID = getPartIDByNumber(partNumber);
//           System.out.println("partifcpartifcpartifcpartifcpartifcpartifcpartifcdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd==========================================="+partifc.getPartNumber());
           if(!partifc.getBsoID().equals("")){
           	ac1.add(partifc.getBsoID());
           	ac1zz.add(partifc.getBsoID());
           ac.add(partifc);
//           aczz.add(partifc);
           }
//           if(ac.size()==0){
//	        	  throw new QMException("reference object not found "
//                       + partNumber);
//	           }
           if(partifc.getBsoID() != null)
           {
               if(baseLineName == null)
               {
                   throw new QMException("reference object not found "
                           + baseLineName);
               }
//               System.out.println("2222222222222222========="+ac);
//               System.out.println("333333333333333333333333333333333333333333333========="+ac1);
//               System.out.println("444444444444444444444444444444444444444444444444444========="+ac1zz);
               MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("JFMaterialSplitService");
//               BaselineService baseLineEjb = (BaselineService)EJBServiceHelper.getService("BaselineService");
               PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
//               if(baselineifc.getBsoID()!=null){
//               baseline = (ManagedBaselineIfc)pservice.refreshInfo(baselineiD);
               
               if(baselineifc.getBaselineName().toString().equals("�����°淢��")){
               	 
               	sessions = (SessionService) EJBServiceHelper.getService("SessionService");
                   sessions.setAdministrator();
               	try {
               		 
               		baseLineEjb.addToBaseline(ac,baselineifc);
               	}catch(Exception e) {
             	      e.printStackTrace();
              	 }
              	 finally {
              	      try {
              	        sessions.freeAdministrator();
              	      }
              	      catch (QMException e) {
              	        e.printStackTrace();
              	      }
              	    }

               }
               
         //       filterPartVec=msservice.split1(ac1zz, baselineifc.getBsoID(), false, null,partVersionValue,routeAllCode ,masterbsoid);
                System.out.println("filterPartVecfilterPartVec==="+filterPartVec);
//                ac1zz.clear();
//               if(filterPartVec==null||filterPartVec.size()==0)
//                return;
//               flag = true;
               if(baselineifc.getBaselineName().toString().equals("�����°淢��")){
               	 try {
               Collection partss =baseLineEjb.getBaselineItems(baselineifc);
               baseLineEjb.removeFromBaseline(partss,baselineifc);
               	 }catch(Exception e) {
               	      e.printStackTrace();
               	 }
               	 finally {
               	      try {
               	        sessions.freeAdministrator();
               	      }
               	      catch (QMException e) {
               	        e.printStackTrace();
               	      }
               	    }
               }
           }
           }
           else
           {
               String s1 = "\ncreatePartDocReference:û�е�ǰ���ߡ��������ȳɹ������㲿�����ߡ�";
               LoadHelper.printMessage(s1);
           }
//			if(i==list.size()-1){
//			String xmlName = getVirtualPartNumber("���߷���");
//			String packid = createIntePack(xmlName, baselineifc.getBsoID(), false, null);
//	      IntePackService packservice = (IntePackService)EJBServiceHelper.getService("JFIntePackService");
//	      packservice.publishIntePack(packid, xmlName,ac1,filterPartVec);
//			}
		}
		System.out.println("ac1ac1ac1==="+ac1.size());
		if(ac1.size()>0){
		String xmlName = getVirtualPartNumber("���߷���");
String packid = createIntePack(xmlName, baselineifc.getBsoID(), false, null);
      IntePackService packservice = (IntePackService)EJBServiceHelper.getService("JFIntePackService");
     // packservice.publishIntePack(packid, xmlName,ac1,filterPartVec);
	}
      

//		writeDoc();
		if(logger.isDebugEnabled())
			logger.debug("split(String, boolean) - end");
		//CCBegin by dikefeng 20090422��Ϊ��ʹ���ڷ�����ʱ��֪�������·������϶�����Щ����Ҫ������ѹ��˺������嵥����һ��
		return filterPartMap;
		//CCEnd by dikefeng 20090422
	}
	private String createIntePack(String xmlName, String baselineID, boolean flag, String routeID)
    throws QMException
{
    String name = xmlName;
    String sourceid = "";
    IntePackSourceType sourcetype = null;
    if(flag)
    {
        sourceid = routeID;
        sourcetype = IntePackSourceType.technicsRouteList;
    } else
    {
        sourceid = baselineID;
        sourcetype = IntePackSourceType.BASELINE;
    }
    IntePackInfo intepack = new IntePackInfo();
    intepack.setName(name);
    intepack.setSourceType(sourcetype);
    intepack.setSource(sourceid);
    IntePackService itservice = (IntePackService)EJBServiceHelper.getService("JFIntePackService");
    IntePackIfc intepackifc = itservice.createIntePack(intepack);
    String bsoid = intepackifc.getBsoID();
    return bsoid;
}
	private String getVirtualPartNumber(String base) {
        String partNumber = "";
        try {
//          QQChangeService cs = (QQChangeService) EJBServiceHelper.getService(
//              "QQChangeService");
        	PublishData publishData = new PublishData();
          int year = Calendar.getInstance().get(Calendar.YEAR);
          int month = Calendar.getInstance().get(Calendar.MONTH);
          String yearStr = (new Integer(year)).toString();
          String monthStr = "";
          if (month < 9) {
            monthStr = "0" + (new Integer(month + 1)).toString();
          }
          else {
            monthStr = (new Integer(month + 1)).toString();

          }
          int i = publishData.getNextSortNumber(base, yearStr + monthStr, true);
          if (i < 10) {
            partNumber = "000" + (new Integer(i)).toString();
          }
          else if (i < 100) {
            partNumber = "00" + (new Integer(i)).toString();
          }
          else if (i < 1000) {
            partNumber = "0" + (new Integer(i)).toString();
          }
          else {
            partNumber = (new Integer(i)).toString();
          }
          partNumber = yearStr + monthStr + "-" + partNumber;
        }
        catch (QMException e) {
          e.printStackTrace();
        }

        return base+"-" + partNumber;
      }
	/**
     * ͨ�����ߵ����ֵõ�����
     * ���û���򴴽��»���
     * @param name String
     * @param num String
     * @return ManagedBaselineIfc
     */
    private static ManagedBaselineIfc getBaselineByName(String name) {
      try {
    	  PersistService   ps = (PersistService) EJBServiceHelper.getService(
          "PersistService");
        QMQuery query = new QMQuery("ManagedBaseline");
        QueryCondition con = new QueryCondition("baselineName", "=", name);
        query.addCondition(con);
        
        	
       
        Collection col = ps.findValueInfo(query, false);
        
          for (Iterator i = col.iterator(); i.hasNext(); ) {
            ManagedBaselineIfc line = (ManagedBaselineIfc) i.next();
            return line;
          
        }
      }
      catch (QMException e) {
        e.printStackTrace();
      }
      return null;
    }
	/**
	 * added by dikefeng 20100504
	 *���������һ����������㲿�����Ӽ��Լ����Ӽ���Ĺ���
	 * @author �ҿƷ�
	 * @param partIfc �㲿��
	 */
	private final List getSubPartsAndLinks(final QMPartIfc partIfc)
	throws QMException {
		List usersPartList=new ArrayList();
		try{
			PersistService pService=(PersistService)EJBServiceHelper.getService("PersistService");
			QMQuery query=new QMQuery("QMPartMaster");
			int i=query.appendBso("PartUsageLink", true);
			query.addCondition(i,0,new QueryCondition("leftBsoID","bsoID"));
			query.addAND();
			query.addCondition(i,new QueryCondition("rightBsoID","=",partIfc.getBsoID()));
			usersPartList=(List)pService.findValueInfo(query);
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new QMException(e);
		}
		return usersPartList;
	}
	/**
	 * ����������Ҫ���������ԣ������ϲ�ּ��ṹ�޹أ���������ʱ���Ƶ�ÿ����ֺ�����ϼ�¼�С�
	 * 
	 * @param partIfc
	 * @param mSplitIfc
	 * @throws QMException
	 */
	private void setMaterialSplit(QMPartIfc partIfc, MaterialSplitIfc mSplitIfc)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger
			.debug("setMaterialSplit(QMPartIfc, MaterialSplitIfc) - start"); //$NON-NLS-1$
			logger.debug("������partIfc==" + partIfc);
			logger.debug("������mSplitIfc==" + mSplitIfc);
		}
		mSplitIfc.setPartName(partIfc.getPartName());
		mSplitIfc.setDefaultUnit(partIfc.getDefaultUnit().getDisplay());
		mSplitIfc.setProducedBy(partIfc.getProducedBy().getDisplay());
		mSplitIfc.setPartType(partIfc.getPartType().getDisplay());
		mSplitIfc.setPartModifyTime(partIfc.getModifyTime());
		mSplitIfc.setOptionCode(partIfc.getOptionCode());
//		�޸�����ط�������ɫ����ʶ����Ϣ dikef
//		CCBegin by dikefeng 20100416,�ൺ��������ɫ����ʶ���㲿����IBA�����У�������Ϊpart_colorflag��������ͨ����ȡ�㲿��IBA���Բ���
//		���������õ���������Ϊ���ϵ���ɫ����ʶ
		PersistService pSerivce=(PersistService)EJBServiceHelper.getPersistService();
		QMQuery query = new QMQuery("StringValue");
	    int j = query.appendBso("StringDefinition", false);
	      QueryCondition qc = new QueryCondition("iBAHolderBsoID", "=",partIfc.getBsoID());
	      query.addCondition(qc);
	      query.addAND();
	      QueryCondition qc1 = new QueryCondition("definitionBsoID", "bsoID");
	      query.addCondition(0, j, qc1);
	      query.addAND();
	      QueryCondition qc2 = new QueryCondition("name", " = ", "part_colorflag");
	      query.addCondition(j, qc2);
	      Collection colorCollection = pSerivce.findValueInfo(query, false);
	      if(colorCollection==null||colorCollection.size()==0)
	    	  mSplitIfc.setColorFlag(false);
	      else{
	    	  StringValueIfc colorValue=(StringValueIfc)colorCollection.iterator().next();
	    	  //ֻ��������ϴ��ʶ
	    	  if(colorValue.getValue().equalsIgnoreCase("true")&&mSplitIfc.getRootFlag())
	    		  mSplitIfc.setColorFlag(true);
	    	  else
	    		  mSplitIfc.setColorFlag(false);
	      }
//		mSplitIfc.setColorFlag(partIfc.getColorFlag());
//		CCEnd by dikefeng 20100416
		mSplitIfc.setDomain(DomainHelper.getDomainID(mSplitDefaultDomainName));
		if (logger.isDebugEnabled()) {
			logger.debug("setMaterialSplit(QMPartIfc, MaterialSplitIfc) - end"); //$NON-NLS-1$
		}
	}
	/**
	 * ��·���е�·�ߴ���ת��ΪList,���Ҹ��������ļ�������ȥ��·���е�������롣
	 * 
	 * @param routeStr
	 * @return
	 */
	public List getRouteCodeList(String routeStr) {
		StringTokenizer routeTok = new StringTokenizer(routeStr, dashDelimiter);
		String routeCode = "";
		HashMap specRCHashMap = getDleteWhenSplitRC();
		List routeCodeList = new ArrayList();
		boolean isFirst = true;
		while (routeTok.hasMoreTokens()) {
			routeCode = routeTok.nextToken();
			if (isFirst || specRCHashMap.get(routeCode) == null) {
				routeCodeList.add(routeCode);
				isFirst = false;
			}
		}
		logger.debug("getRouteCodeList end routeCodeList is " + routeCodeList);
		return routeCodeList;
	}
	private HashMap getDleteWhenSplitRC() {
		if (specRCHashMap == null || specRCHashMap.keySet().size() <= 0) {
			specRCHashMap = new HashMap();
			String routeCode = "";
			logger.debug("getDleteWhenSplitRC deleteWhenSplitRouteCode is "
					+ deleteWhenSplitRouteCode);
			StringTokenizer stringToken = new StringTokenizer(
					deleteWhenSplitRouteCode, delimiter);
			while (stringToken.hasMoreTokens()) {
				routeCode = stringToken.nextToken();
				specRCHashMap.put(routeCode, routeCode);
			}
		}
		logger.debug("getDleteWhenSplitRC specRouteCode end is "
				+ specRCHashMap);
		return specRCHashMap;
	}
//	public static void main(String[] args) {
//		try {
//			String  a= "��-��-��=��;��";
//			System.out.println("aaaaaaaaa="+a.substring(0,a.indexOf(";")));
//			//e.mainExport();
//		} catch (Exception exc) {
//			exc.printStackTrace();
//		}
//	}

}
