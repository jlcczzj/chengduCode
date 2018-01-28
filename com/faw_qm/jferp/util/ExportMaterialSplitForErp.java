/**
 * 生成程序QMXMLMaterialSplit.java	1.0              2007-9-27
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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

import com.faw_qm.domain.util.DomainHelper;
import com.faw_qm.jferp.ejb.service.MaterialSplitServiceEJB;
import com.faw_qm.jferp.exception.ERPException;
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
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.iba.definition.model.StringDefinitionIfc;
import com.faw_qm.iba.value.model.StringValueIfc;


import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
/**
 * <p>Title: 历史的零部件的物料处理。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 
 * @version 1.0
 */
public class ExportMaterialSplitForErp
{
	private static final long serialVersionUID = 1L;



	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
	.getLogger(MaterialSplitServiceEJB.class);



	/**
	 * 破折号分隔符。用于分隔路线代码。
	 */
	private String dashDelimiter = "-";

	/**
	 * 根据属性文件得到顶级物料号的构成方式，true：零件号+路线代码；false：零件号。
	 */
	private static boolean addRouteCode = RemoteProperty.getProperty(
			"addRouteCode", "false").equalsIgnoreCase("true");


	/**
	 * 物料拆分的管理域。
	 */
	private static String mSplitDefaultDomainName = (String) RemoteProperty
	.getProperty("materialSplitDefaultDomain", "System");

	private static final String RESOURCE = "com.faw_qm.jferp.util.ERPResource";

	// 20080103 begin
	/**
	 * 拆分物料时针对特殊的路线代码有特殊的物料编码规则，如：散热器的“采购”代码，拆分含有这个代码的物料时，“采购”代码对应的物料编号使用零件号，其它物料编号使用：零件号+“-”+路线代码。
	 */
	private static final String specialRouteCode = RemoteProperty.getProperty(
			"specialRouteCode", "采购");
	private HashMap partList=new HashMap();
	
	/**
	 * 在工序中有子件的工艺，以“，”作为分隔符。发布该工艺时，工序关联的物料的部门代码是上一个部门的代码。
	 */
	// private static final String hasSubpartTech = RemoteProperty.getProperty(
	// "hasSubpartTech", "01");
	// 20080103 begin
	/**
	 * 以“，”作为分隔符，拆分物料时需要进行如果有多个路线代码且该路线代码不是在第一个，则在拆分时没有该路线代码的物料，
	 * 即在路线中去掉该路线代码后再拆分，如：散热器的“采购”代码，拆分含有这个代码的零部件时，如果路线为SX-DH-CG，
	 * 则当作路线SX-DH处理。如果路线为SX-CG-DH，则当作路线SX-DH处理。如果路线为CG-SX-DH，
	 * 则仍然当作路线CG-SX-DH处理。如果路线只是CG，则仍当作路线CG处理。
	 */
	private static final String deleteWhenSplitRouteCode = RemoteProperty
	.getProperty("deleteWhenSplitRouteCode", "采购");

	/**
	 * deleteWhenSplitRouteCode的集合。
	 */
	private static HashMap specRCHashMap = null;


	/**
	 * 逗号分隔符。用于分隔useProcessPartRouteCode。
	 */
	private String delimiter = "，";
	private String delimiter1 = ",";
	/**
     * 存放发布的物料XMLMaterialSplitIfc。
     */
    private ArrayList xmlMatSplitList = new ArrayList();

    /**
     * 存放发布的物料结构XMLMaterialStructureIfc。
     */
    private ArrayList xmlMatStructList = new ArrayList();
    private int fileNumber=1;
    private HashMap ibaDefinitionMap=new HashMap();
    private HashMap ibaValueMap=new HashMap();
	/**
	 * 主导出方法
	 */
	public static void mainExport() throws QMException {
		try{
			System.out.println("进入了导出历史数据的方法了！！！！！！！！！");
//		String driverName = "oracle.jdbc.driver.OracleDriver";
//		Class.forName(driverName).newInstance();
//		String URL = "jdbc:oracle:thin:@10.28.68.53:1521:qingqi";
//		System.out.println("URLURLURLURL========"+URL);
		ExportMaterialSplitForErp erp = new ExportMaterialSplitForErp();
		Connection conn = erp.getConnection();
		ResultSet result = null;
		
		Statement select = conn.createStatement();
		result = select.executeQuery("select * from PDMPART0619");
		//System.out.println("1111111111========"+result);

		ArrayList AllParts=new ArrayList();
		String[] partData=null;
        while (result.next()) {
        	partData=new String[10];
        	//零件编号
        	String partNumber = result.getString("partNumber");
        	//erp编号
        	String erpNumber = result.getString("erpNumber");
        	//版本
    		String versionValue = result.getString("partversion");
    		//制造路线
    		String zzRoute = result.getString("manuroute");
    		//装配路线
    		String zpRoute = result.getString("assroute");
    		//虚拟件
    		String virtualFlag = result.getString("dummyPart");
    		//颜色件标识
    		String colorFlag = result.getString("colorFlag");
    		//来源
    		String produceBy = result.getString("producedBy");
    		//类型
    		String partType = result.getString("partType");

    		System.out.println("jieguo  ========"+partNumber);
    		System.out.println("jieguo  ========"+versionValue);
    		System.out.println("erpNumber  ========"+erpNumber);
    		System.out.println("zzRoute  ========"+zzRoute);
    		System.out.println("zpRoute  ========"+zpRoute);
    		partData[0]=partNumber;
    		partData[1]=versionValue;
    		//有;号即多条路线
    		partData[2]=erpNumber; 
    		partData[3]=zzRoute; 
    		partData[4]=zpRoute; 
    		partData[5]=virtualFlag; 
    		partData[6]=colorFlag; 
    		partData[7]=produceBy; 
    		partData[8]=partType; 
    		AllParts.add(partData);
		}
        System.out.println("333333333333333========"+AllParts);
        int a=(int)AllParts.size()/3000;
		   int b=AllParts.size()%3000;
		   if(b>0)
		   {
		    a=a+1;
		   }
		   for(int j=0;j<a;j++)
		   {
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
		    erp.forErpSplit(listzh);
		   }
		conn.commit();
		conn.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new QMException(e);
		}
		
		
	}
	/**
	 * 需求： 1、数据库服务器10.43.4.103，sid=orcl，用户名pm_pdm，密码jfgspmpdm。
	 * 2、该用户具有对10.43.4.103上的用户faw的 pm_pdm_info 表具有insert、select、update权限。
	 */
	private Connection getConnection() throws SQLException 
	{
		Connection conn = null;
		String url = (String) RemoteProperty.getProperty("url");
		String user = (String) RemoteProperty.getProperty("user");
		String password = (String) RemoteProperty.getProperty("password");
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = java.sql.DriverManager.getConnection(url, user,password);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			//CCBegin SS1
			System.out.println("不能获取连接");
			try
			{
				Thread.sleep(60000);
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
			
			return getConnection();
			//throw new SQLException("不能获取连接");
			//CCEnd SS1
		}
		return conn;
	}


	public Vector forErpSplit(List list)throws QMException
	{
//		System.out.println("2222222222222========");
		//HashMap hamap =new HashMap();
		
		PersistService pService ;
		
		this.xmlMatSplitList=new ArrayList();
		this.xmlMatStructList=new ArrayList();
		partList=new HashMap();
		Vector filterPartMap=new Vector();
		//QMPartIfc partIfc =null;
		PartHelper partHelper = new PartHelper();
		pService = (PersistService)EJBServiceHelper.getService("PersistService");

		System.out.println("here got the list size="+list.size());
		//针对每一个零部件进行处理
		for(int i = 0; i < list.size(); i++)
		{
			//含有酸的路线
			String routeAsStringQian="";
			String partNumber ="";
			String partVersionValue="";
			String[] partQuan=(String[])list.get(i);
			//零部件编号
			partNumber=partQuan[0];
			//零部件版本
			partVersionValue=partQuan[1];
			//erp编号
			String erpNumber = partQuan[2];
//			制造路线
			String routeAsString = partQuan[3];
//			全路线串
			String routeAllCode = partQuan[3]+";"+partQuan[4];
//			装配路线
			String routeAssemStr = partQuan[4];
			//虚拟件
    		String virtualFlag = partQuan[5];
    		//颜色件标识
    		String colorFlag = partQuan[6];
    		//类型
    		String partType = partQuan[8];
    		//List routeCodeList = getRouteCodeList(routeAsString);
	          this.partList.put(partNumber,partQuan);
	          List usageLinkList = getSubPartsAndLinks(partNumber);
			String erpPartNumber = partNumber + "/" + partVersionValue;
			String materialNumber =  partHelper.getMaterialNumberHistory(partQuan);
//			如果制造路线不为null，并且制造路线为空格，即制造路线为空
			if(routeAsString == null || routeAsString.toString().equals(""))
			{
				logger.debug("路线为空，即不需拆分零部件");
				MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
				mSplitIfc.setRootFlag(true);
				//hamap.put(partIfc.getMasterBsoID(), mSplitIfc);
				mSplitIfc.setPartNumber(erpPartNumber);
				mSplitIfc.setPartVersion(partVersionValue);
				mSplitIfc.setState("生产准备");
				mSplitIfc.setSplited(false);
				mSplitIfc.setRoute(routeAllCode);
				mSplitIfc.setIsMoreRoute(false);
				//虚拟件
				if(virtualFlag.equals("Y"))
					mSplitIfc.setVirtualFlag(true);
				else if(virtualFlag.equals("N"))
					mSplitIfc.setVirtualFlag(false);
				
					//颜色件标识
				if(colorFlag.equals("Y"))
					mSplitIfc.setColorFlag(true);
				else if(colorFlag.equals("N"))
					mSplitIfc.setColorFlag(false);
				
//				如果获得子件列表不为空，则将status设置为2，否则设置为0	
				if(usageLinkList != null && usageLinkList.size() > 0)
					mSplitIfc.setStatus(2);
				else
					mSplitIfc.setStatus(0);
				
				setMaterialSplit(partQuan, mSplitIfc);
				//CCBegin by dikefeng 20100419，ERP实施人员经与客户商谈，更改了虚拟件的设置规则
				//因为当制造路线与装配路线 都为空时，需要判断是不是颜色件，所以将设置虚拟件的位置后移到了颜色件之后
				if(routeAssemStr==null||routeAssemStr.trim().length()==0)
				{

					mSplitIfc.setVirtualFlag(true);
				}
				

				mSplitIfc.setMaterialNumber(materialNumber);

//				这里为什么直接就是N呢？questions
				mSplitIfc.setMaterialSplitType("N");
				//mSplitIfc = (MaterialSplitIfc)pservice.saveValueInfo(mSplitIfc);
				this.xmlMatSplitList.add(mSplitIfc);
				if(usageLinkList!=null&&usageLinkList.size()>0)
				{
					Iterator linkIte=usageLinkList.iterator();
					while(linkIte.hasNext())
					{
						Object[] son=(Object[])linkIte.next();
					//	String parentNumber = (String)son[0];
						String childNumber = (String)son[1];
						String partbom    = (String)son[2];
						MaterialStructureIfc mStructureIfc = new MaterialStructureInfo();
						mStructureIfc.setParentPartNumber(mSplitIfc.getMaterialNumber());
						mStructureIfc.setParentPartVersion(mSplitIfc.getPartVersion());
						mStructureIfc.setParentNumber(erpPartNumber);
						mStructureIfc.setChildNumber(childNumber);
						mStructureIfc.setQuantity(Integer.valueOf(partbom));
								// xieb应该设置为int型。
						mStructureIfc.setLevelNumber("0");
						mStructureIfc.setDefaultUnit("个");
						mStructureIfc.setMaterialStructureType("N");
								// 不设置选装策略码。
								// materialStructureIfc.setOptionFlag(childMSfc.getOptionCode());
								//mStructureIfc = (MaterialStructureIfc) pservice.saveValueInfo(mStructureIfc);
						this.xmlMatStructList.add(mStructureIfc);
					}
				}
			} else
			{
//				需要进行物料拆分的情况下，首先将制造路线分解为一个个路线字符
			
				List routeCodeList = getRouteCodeList(routeAsString);
//				System.out.println("routeCodeListrouteCodeList========="+routeCodeList);
				Vector mSplitList = new Vector();
				Vector co=new Vector();
	
					
				for(int m = routeCodeList.size() - 1; m >= 0; m--)
				{
					String routeCode = (String)routeCodeList.get(m);
					String makeCodeNameStr = RemoteProperty.getProperty("com.faw_qm.jferp.routecode." + routeCode);
					makeCodeNameStr = partHelper.getLXCode(routeCodeList,makeCodeNameStr);
					MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
//					如果是路线串中的最后一个路线单位
					if(m == routeCodeList.size() - 1)
					{

					    materialNumber	= materialNumber;
						
						mSplitIfc.setRootFlag(true);
			
					}

					else{
						materialNumber =  materialNumber + dashDelimiter + makeCodeNameStr;	
					}
					mSplitIfc.setMaterialNumber(materialNumber);
						//虚拟件
						if(virtualFlag.equals("Y"))
							mSplitIfc.setVirtualFlag(true);
						else if(virtualFlag.equals("N"))
							mSplitIfc.setVirtualFlag(false);
						
							//颜色件标识
						if(colorFlag.equals("Y"))
							mSplitIfc.setColorFlag(true);
						else if(colorFlag.equals("N"))
							mSplitIfc.setColorFlag(false);
						mSplitIfc.setPartNumber(erpPartNumber);
						mSplitIfc.setPartVersion(partVersionValue);
						mSplitIfc.setState("生产准备");
						mSplitIfc.setSplited(true);
						if(m == 0)
						{
							if(usageLinkList != null && usageLinkList.size() > 0)
							{
//								System.out.println("1111111111111111111111111111========="+mSplitIfc.getMaterialNumber());
								mSplitIfc.setStatus(2);
								Iterator linkIte=usageLinkList.iterator();
								while(linkIte.hasNext())
								{
									
//									System.out.println("22222222222222222222222========="+mSplitIfc.getMaterialNumber());
									Object[] son=(Object[])linkIte.next();
									String parentNumber = (String)son[0];
									String childNumber = (String)son[1];
									String partbom    = (String)son[2];
					
									MaterialStructureIfc mStructureIfc = new MaterialStructureInfo();
									mStructureIfc.setParentPartNumber(mSplitIfc.getMaterialNumber());
									mStructureIfc.setParentPartVersion(mSplitIfc.getPartVersion());
									mStructureIfc.setParentNumber(mSplitIfc.getMaterialNumber());
									mStructureIfc.setChildNumber(childNumber);
									mStructureIfc.setQuantity(Integer.valueOf(partbom));
											// xieb应该设置为int型。
									mStructureIfc.setLevelNumber("0");
									mStructureIfc.setDefaultUnit("个");
									mStructureIfc.setMaterialStructureType("N");
											// 不设置选装策略码。
											// materialStructureIfc.setOptionFlag(childMSfc.getOptionCode());
											//mStructureIfc = (MaterialStructureIfc) pservice.saveValueInfo(mStructureIfc);
									this.xmlMatStructList.add(mStructureIfc);
								}
							}
							else
								mSplitIfc.setStatus(0);
						} else
						{
							mSplitIfc.setStatus(1);
						}
						mSplitIfc.setRouteCode(routeCode);
						mSplitIfc.setIsMoreRoute(false);
						mSplitIfc.setRoute(routeAllCode.toString());
						//根据零部件设置物料其他的需要发布的属性信息
						setMaterialSplit(partQuan, mSplitIfc);
						mSplitList.add(mSplitIfc);
//					}
				}
				//CCBegin  by chudaming 20100127
				Object[] objs=mSplitList.toArray();
				for(int ii=0;ii<objs.length;ii++)
			    {
//					System.out.println("iiiiiiiiiiiiiiiiiiiiii=========="+ii);
					
					Vector vvv=new Vector();
			     MaterialSplitIfc ifc=(MaterialSplitIfc)objs[ii];
			     String k0 = RemoteProperty.getProperty("com.faw_qm.jferp.routecode." + ifc.getRouteCode());
			     String k1 = partHelper.getLXCode(routeCodeList,k0);
			     if(ii!=objs.length-1)
			     {
			    	 for(int d=1 ;d<objs.length-ii;d++){
//			    		 System.out.println("0000000000000000000000000000000000========"+ii);
//			    		 System.out.println("0000000000000000000000000000000000========"+d);
			    		 MaterialSplitIfc ifcf=(MaterialSplitIfc)objs[ii+d];
			    		 vvv.add(ifcf);
//			    		 System.out.println("ifcf[[[[[[[[[[[["+ifcf.getMaterialNumber());
//			    		 System.out.println("eeeeeeeeee"+ii);
//			    		 System.out.println("fffffffffffffffff"+d);
//			    		 System.out.println("ddddddddddddddddd"+vvv);
			    	 }
			    	 Vector jjj=new Vector();
			    	 Vector jjj1=new Vector();
			    	 Vector jjj2=new Vector();
			    	 int iii=ii;
			    	 boolean boobean=false;
			    	 for(int dd =0 ;dd<vvv.size();dd++){
			    		 
			    		 MaterialSplitIfc ifcdd=(MaterialSplitIfc)vvv.get(dd);
			    		 String kkkkk0=RemoteProperty.getProperty("com.faw_qm.jferp.routecode." + ifcdd.getRouteCode());
			    		 String kkkkk = partHelper.getLXCode(routeCodeList,kkkkk0);
//			    		 System.out.println("wo      -========="+k1+"zhendeme --"+kkkkk);
			    		 
			    		 if(k1!=null&&kkkkk!=null){
//			    			 System.out.println("ifcifcifcifc====="+ifc.getMaterialNumber());
//			    			 System.out.println("ifcifcifcifcddddddddddddddddddddddddddddddddddddd====="+ifcdd.getMaterialNumber());
			    		 if((ifc.getMaterialNumber().equals(materialNumber))&&
		    			  k1.toString().equals(kkkkk.toString())){
			    			 jjj.add(ifcdd.getRouteCode());
//			    			 System.out.println("weishenme======================="+jjj+"-----------------------"+dd);
			    			 String ll="";
			    			 for(int t=0 ;t<jjj.size();t++){
			    				 ll+=jjj.get(jjj.size()-1-t).toString()+"-";
			    				 }
			    			 
//			    			 System.out.println("fengleaaaaaaaaaaaaaaaaaaaaaa00000000"+ll);
//			    			 System.out.println("fenglea aaaa========="+ll+ifc.getRouteCode());
			    			 ifc.setRCode(ll+ifc.getRouteCode());
			    			 
			    			 ifcdd.setMaterialNumber(ifc.getMaterialNumber());
//			    			 System.out.println("ccccccccccccccccccaaaaaaaaaaaaaaaaaaaaaaoooooooooooooooo="+ii);
//			    			 System.out.println("ccccccccccccccccccaaaaaaaaaaaaaaaaaaaaaaoooooooooooooooo="+dd);
			    			 
			    			 co.add(new Integer(iii+dd+1));
			    			 boobean=true;
			    			 ii++;
			    		 }
			    		 else if(k1.toString().equals(kkkkk.toString())&&(
						    	  (ifcdd.getMaterialNumber().substring(0,(ifcdd.getMaterialNumber().length()-2)).equals(ifc.getMaterialNumber().substring(0,(ifc.getMaterialNumber().length()-2))))
						    	  ||ifcdd.getMaterialNumber().equals(ifc.getMaterialNumber().substring(0,(ifc.getMaterialNumber().length()-2))))){
			    			 jjj1.add(ifcdd.getRouteCode());
//			    			 System.out.println("jjj1jjj1jjj1jjj1jjj1jjj1jjj1jjj1==========="+jjj1+"-----------------"+dd);
			    			 String ll1="";
			    			 for(int t=0 ;t<jjj1.size();t++){
			    				 ll1+=jjj1.get(jjj1.size()-1-t).toString()+"-";
			    				 }
//			    			 System.out.println("7777777777777777777777777777"+ll1);
//			    			 System.out.println("7777777777777777777777777777"+ll1+ifc.getRouteCode());
			    			 ifc.setRCode(ll1+ifc.getRouteCode());
			    			 ifc.setMaterialNumber(ifcdd.getMaterialNumber());
			    			 if(ifcdd.getStatus()==2){
//			    		    	   System.out.println("3来没来啊");这个问题狄还没给我，给我直接打开即可
			    		       ifc.setStatus(2);
			    		       }
			    			 co.add(new Integer(iii+dd+1));
			    			 boobean=true;
			    			 ii++;
			    		 }
			    		 else if(k1.toString().equals(kkkkk.toString())&&ifcdd.getMaterialNumber().equals(ifc.getMaterialNumber().substring(0,(ifc.getMaterialNumber().length()-2)))){
			    			 jjj2.add(ifcdd.getRouteCode());
			    			 String ll2="";
			    			 for(int t=0 ;t<jjj2.size();t++){
			    				 ll2+=jjj2.get(jjj2.size()-1-t).toString()+"-";
			    				 }
//			    			 System.out.println("8888888888888888888888888888888888888888"+er);
			    			 ifc.setRCode(ll2+ifc.getRouteCode());
			    			 ifc.setMaterialNumber(ifcdd.getMaterialNumber());
			    			 if(ifcdd.getStatus()==2){
//			    		    	   System.out.println("3来没来啊");这个问题狄还没给我，给我直接打开即可
			    		       ifc.setStatus(2);
			    		       }
			    			 co.add(new Integer(iii+dd+1));
			    			 boobean=true;
			    			 ii++;
			    		 }
//			    		 else if(!k1.toString().equals(kkkkk.toString())){
//			    			 System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv=========="+ifc.getRouteCode());
//			    			 ifc.setRCode(ifc.getRouteCode());
//			    		 }
			    		 else{
//			    			 System.out.println("zzzzzzzzzzzzzzzzzzzz00000000000000000000000"+k1.toString());
//			    			 System.out.println("zzzzzzzzzzzzzzzzzzzz00000000000000000000000"+kkkkk.toString());
//			    			 System.out.println("zzzzzzzzzzzzzzzzzzzz1111111111111111111111111"+!(k1.toString().equals(kkkkk.toString())));
//			    			 System.out.println("zzzzzzzzzzzzzzzzzzzz22222222222222222222222222"+!boobean);
			    			 if(!boobean){
//			    				 System.out.println("ggggggggggggggggggggggggggggggg"+ifc.getRouteCode());
			    			 ifc.setRCode(ifc.getRouteCode());
			    			 break;
			    			 }else{
			    				 break;
			    			 }
			    		 }
//			    		 System.out.println("ererererererhhhhhhhhhhhhhhhhaaaaaaaaaaaaaaa"+ll+ifc.getRouteCode());
			    		 }
			    	 }
			    	 

			     }
			     else
			     {
			      if(objs.length!=1)
			      {
			       MaterialSplitIfc ifc1=(MaterialSplitIfc)objs[ii-1];
			       if(ifc.getMaterialNumber().equals(ifc1.getMaterialNumber()))
			       {
			        
			       }
			       else
			       {
			    	  
			        ifc.setRCode(ifc.getRouteCode());
			        System.out.println("??????????????"+ifc.getRouteCode());
			       }
			      }
			      else
			      {
			       ifc.setRCode(ifc.getRouteCode());
			      }
			     }
			    }
//			    System.out.println("gggggg++++"+mSplitList);
			    //把经过判断后需要去掉的物料从列表中删除
			    for(int k=co.size()-1;k>=0;k--)
			    {
			     Integer ii=(Integer)co.elementAt(k);
			     int i3=ii.intValue();
			     mSplitList.removeElementAt(i3);
			    }
				//CCEnd by chudaming 20100127
				for(Iterator iter1=mSplitList.iterator();iter1.hasNext();)
				{
//					questions，如果零部件由于路线变化需要重新拆分，但是其中某几个物料的内容没有发生变化，那么也将这些物料设置为U标识
					MaterialSplitIfc ifc=(MaterialSplitIfc)iter1.next();
					//CCBegin by dikefeng 20100419,这里保存了本次发布数据的标识
					
						ifc.setMaterialSplitType("N");
					
//					这里进行了所有拆分物料的保存 dikef ?????
//					ifc = (MaterialSplitIfc)pservice.saveValueInfo(ifc);
					this.xmlMatSplitList.add(ifc);
				}
				//CCBegin by dikefeng 20100419，这个地方将原来有，但是现在不再使用的物料打删除标记

				//CCEnd by dikefeng 20100419
				for (int p = 0; p < mSplitList.size(); p++) {
					MaterialSplitIfc parentMSIfc = (MaterialSplitIfc) mSplitList
					.get(p);
					MaterialSplitIfc childMSfc = null;
					if (p != mSplitList.size() - 1) {
						childMSfc = (MaterialSplitIfc) mSplitList.get(p + 1);
					}
					if (parentMSIfc != null) {
						MaterialStructureIfc mStructureIfc = new MaterialStructureInfo();
						// 1-有下级物料。
						if (childMSfc != null && parentMSIfc.getStatus() == 1) {
//							if (!hasSplitedStructure(parentMSIfc, childMSfc)) {
								logger.debug("1-有下级物料。");
								mStructureIfc.setParentPartNumber(parentMSIfc
										.getPartNumber());
								mStructureIfc.setParentPartVersion(parentMSIfc
										.getPartVersion());
								mStructureIfc.setParentNumber(parentMSIfc
										.getMaterialNumber());
								mStructureIfc.setChildNumber(childMSfc
										.getMaterialNumber());
								mStructureIfc.setQuantity(1);
								// xieb应该设置为int型。
								mStructureIfc.setLevelNumber(String.valueOf(p));
								mStructureIfc.setDefaultUnit(childMSfc
										.getDefaultUnit());
								// 不设置选装策略码。
								// materialStructureIfc.setOptionFlag(childMSfc.getOptionCode());
								//mStructureIfc = (MaterialStructureIfc) pservice.saveValueInfo(mStructureIfc);
								this.xmlMatStructList.add(mStructureIfc);
							
						}
						// 2—此物料下要挂接原零部件的子件。
						else if (parentMSIfc.getStatus() == 2) {
							logger.debug("2-最底层物料。");
						}
						// 0-最底层物料。
						else if (parentMSIfc.getStatus() == 0) {
							logger.debug("0-最底层物料。");
						}
					} // if(parentMSIfc != null)
				}
				//CCEnd by dikefeng 20100419				
			}
			//CCEnd by dikefeng 20100419
		}
		writeDoc();
		if(logger.isDebugEnabled())
			logger.debug("split(String, boolean) - end");
		//CCBegin by dikefeng 20090422，为了使得在发布的时候知道本次新发的物料都有哪些，需要在这里把过滤后的零件清单返回一个
		return filterPartMap;
		//CCEnd by dikefeng 20090422
	}
	private void writeDoc() throws QMException
	{
		try{
			Document doc = DocumentHelper.createDocument();
		    OutputFormat format = OutputFormat.createPrettyPrint();
	        //设置XML编码类型。
	        format.setEncoding("gb2312");
	        //指定与XML文件配套使用的样式单的类型及文件名。
	        doc.addProcessingInstruction("xml-stylesheet",
	                "type=\"text/xsl\" href=\"OutPut_Data.xsl\"");
//	        设置根信息
	        DocumentType docType = new DefaultDocumentType();
	        docType.setElementName("bom");
	        docType.setInternalDeclarations(getInternalDeclarations());
	        doc.setDocType(docType);
//	        写文件头信息
	        doc.addElement("bom");
	        Element elem = doc.getRootElement().addElement("description");
            elem.addElement("filenumber").addText("历史数据发布，按MES生产系统版本及路线处理，当前为文件："+fileNumber);
            elem.addElement("type").addText("按生产系统版本处理");
            elem.addElement("date").addText("历史数据时间不进行处理");
            elem.addElement("sourcetag").addText("工艺服务器");
            elem.addElement("notes").addText("共发布"+xmlMatSplitList.size()+"条名称为mpart的数据。共发布"+xmlMatStructList.size()+"条名称为structure的数据。");

//            先写表头
            Element table = doc.getRootElement().addElement("table");
            table.addAttribute("name", "mpart");
            addTablePartColHeader(table);
            PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
            String lastpartnumber="";
//          开始写物料数据
		    for(int i=0;i<xmlMatSplitList.size();i++)
		    {
		    	
		    	Element recordElem = table.addElement("record");
		    	MaterialSplitIfc matSplit=(MaterialSplitIfc)xmlMatSplitList.get(i);
//		    	System.out.println("零件号============"+matSplit.getMaterialNumber());
		    	QMPartIfc part=(QMPartIfc)this.partList.get(matSplit.getPartNumber());
		    	if(!part.getPartNumber().equalsIgnoreCase(lastpartnumber))
		    	{
		    		lastpartnumber=part.getPartNumber();
		    		this.ibaValueMap=new HashMap();
		    		QMQuery query=new QMQuery("StringValue");
		    		query.addCondition(new QueryCondition("iBAHolderBsoID","=",part.getBsoID()));
		    		Collection valueCol=pService.findValueInfo(query);
		    		Iterator valueIte=valueCol.iterator();
		    		while(valueIte.hasNext())
		    		{
		    			
		    			StringValueIfc svi=(StringValueIfc)valueIte.next();
//		    			System.out.println("svisvi========"+svi);
		    			//CCBegin by chudaming 20100809 在XML文件中，有个‘整车配置’字段，CAPP系统在发布历史数据时，没有将该信息发布出来
//		    			this.ibaValueMap.put(svi.getBsoID(), svi.getValue());
		    			this.ibaValueMap.put(svi.getDefinitionBsoID(), svi.getValue());
		    			//CCEnd by chudaming 20100809 在XML文件中，有个‘整车配置’字段，CAPP系统在发布历史数据时，没有将该信息发布出来
//		    			System.out.println("ibaValueMap000000000000000000000========"+ibaValueMap);
		    		}
		    	}
		    	
		    	recordElem.addElement("col").addAttribute("id","1").addText("N");
		    	recordElem.addElement("col").addAttribute("id","2").addText(matSplit.getMaterialNumber());
		    	recordElem.addElement("col").addAttribute("id","3").addText(matSplit.getPartNumber());
		    	if(matSplit.getRCode()==null||matSplit.getRCode().length()==0)
		    	{
		    		recordElem.addElement("col").addAttribute("id","4").addText(matSplit.getPartName());
		    	}else
		    	{
		    		recordElem.addElement("col").addAttribute("id","4").addText(matSplit.getPartName()+"_"+matSplit.getRCode());
		    	}
		    	recordElem.addElement("col").addAttribute("id","5").addText("个");
		    	recordElem.addElement("col").addAttribute("id","6").addText(matSplit.getPartType());
		    	recordElem.addElement("col").addAttribute("id","7").addText(matSplit.getProducedBy());
		    	if(matSplit.getColorFlag())
		    	recordElem.addElement("col").addAttribute("id","8").addText("true");
		    	else
		    		recordElem.addElement("col").addAttribute("id","8").addText("false");
		    	recordElem.addElement("col").addAttribute("id","9").addText(matSplit.getPartVersion());
		    	recordElem.addElement("col").addAttribute("id","10").addText(matSplit.getPartModifyTime().toString());
		    	recordElem.addElement("col").addAttribute("id","11").addText(getSplitedStr(matSplit));
		    	recordElem.addElement("col").addAttribute("id","12").addText(getVirtualFlag(matSplit));
		    	recordElem.addElement("col").addAttribute("id","13").addText((new Integer(matSplit.getStatus())).toString());
		    	if(matSplit.getRCode()==null)		    		
		    	recordElem.addElement("col").addAttribute("id","14").addText("");
		    	else
		    		recordElem.addElement("col").addAttribute("id","14").addText(matSplit.getRCode());
		    	
		    	recordElem.addElement("col").addAttribute("id","15").addText(getManuRoute(matSplit));
		    	if(matSplit.getRouteCode()!=null){
		    	recordElem.addElement("col").addAttribute("id","16").addText(matSplit.getRouteCode()+matSplit.getPartType());
		    	}else{
		    		recordElem.addElement("col").addAttribute("id","16").addText(matSplit.getPartType());
		    	}
		    	recordElem.addElement("col").addAttribute("id","17").addText(getIsMoreRoute(matSplit));
		    	recordElem.addElement("col").addAttribute("id","18").addText("bsoID="+part.getBsoID());
		    	recordElem.addElement("col").addAttribute("id","19").addText("");
		    	recordElem.addElement("col").addAttribute("id","20").addText("");
		    	recordElem.addElement("col").addAttribute("id","21").addText("");
		    	recordElem.addElement("col").addAttribute("id","22").addText("");
		    	recordElem.addElement("col").addAttribute("id","23").addText(getAssemRoute(matSplit));
		    	recordElem.addElement("col").addAttribute("id","24").addText(getSendRoute(matSplit));
		    	recordElem.addElement("col").addAttribute("id","25").addText(getfawtzbh());
		    	recordElem.addElement("col").addAttribute("id","26").addText(getfawtzbb());
		    	recordElem.addElement("col").addAttribute("id","27").addText(getfawzcpz());
		    	recordElem.addElement("col").addAttribute("id","28").addText(getfawsjbz());
		    	recordElem.addElement("col").addAttribute("id","29").addText(getfawfah());
		    	recordElem.addElement("col").addAttribute("id","30").addText(getfawsjxh());
		    	recordElem.addElement("col").addAttribute("id","31").addText(getcollectionid(matSplit));
		    	recordElem.addElement("col").addAttribute("id","32").addText(getpartURL());
		    	recordElem.addElement("col").addAttribute("id","33").addText(matSplit.getState());
		    }
//		    开始写物料结构表头数据
		    Element bomtable = doc.getRootElement().addElement("table");
		    bomtable.addAttribute("name", "structure");
            addTableBomColHeader(bomtable);
//		    开始写物料结构表内容数据
		    for(int i=0;i<xmlMatStructList.size();i++)
		    {
		    	Element recordElem = bomtable.addElement("record");
		    	MaterialStructureIfc matStruct=(MaterialStructureIfc)this.xmlMatStructList.get(i);
		    	recordElem.addElement("col").addAttribute("id","1").addText("N");
//		    	System.out.println("new Integer(matStruct.getLevelNumber()).intValue()========="+new Integer(matStruct.getLevelNumber()).intValue());
//		    	System.out.println("matStruct.getParentPartNumber()========="+matStruct.getParentPartNumber());
//		    	System.out.println("matStruct.getParentNumber()========="+matStruct.getParentNumber());
		    	if(new Integer(matStruct.getLevelNumber()).intValue()>0){
		    	recordElem.addElement("col").addAttribute("id","2").addText(matStruct.getParentNumber());
		    	}else {
		    		recordElem.addElement("col").addAttribute("id","2").addText(matStruct.getParentPartNumber());
		    	}
		    	recordElem.addElement("col").addAttribute("id","3").addText(matStruct.getChildNumber());
		    	recordElem.addElement("col").addAttribute("id","4").addText(""+matStruct.getQuantity());
		    	recordElem.addElement("col").addAttribute("id","5").addText("个");
		    	recordElem.addElement("col").addAttribute("id","6").addText("false");
		    }
//		    开始写文件
		    //测试
//		    String ppString="/cappapp/qmcapp/phosphor/support/loadFiles/xml/erpFiles/bomList/";
		    //生产
//		    String ppString="/qmcappapp/qmcapp/phosphor/support/loadFiles/xml/erpFiles/bomList/";
		    //自己
		    String ppString="G:/qqPDM/qingqi/productfactory/phosphor/support/loadFiles/xml/erpFiles/bomList/";
		    String str1="";
	        String str2="";
	        String str3="";
	        Date now = new Date(); 
	        DateFormat d8 = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
	        String str8 = d8.format(now);
//	        System.out.println("sssssssssssssssssss==="+str8);
	        str1=str8.substring(0,str8.length()-6);
	        str2=str8.substring(str8.length()-5,str8.length()-3);
	        str3=str8.substring(str8.length()-2,str8.length());
//	        System.out.println("sssssssssssssssssss==="+str1+"====22222222222222222=="+str2+"====3333333333333333333======="+str3);
	        str8=str1+str2+str3;
		    String xmlFileName=str8+"历史数据文件"+fileNumber;
		    File path = new File(ppString);
		    
	        if(!path.exists())
	        {
	            path.mkdir();
	        }
	        File file=new File(ppString + xmlFileName+".xml");
	        if(!file.exists()){
	        	//向目标路径写入XML数据文件。
	            XMLWriter writer = new XMLWriter(
	                    new FileWriter(ppString + xmlFileName+".xml"), format);
	            writer.write(doc);
	            writer.flush();
	            writer.close();
	        }
	        else{
	        	System.out.println("存在与文件"+xmlFileName+fileNumber+"同名的文件");
	        	throw new QMException("存在与文件"+xmlFileName+fileNumber+"同名的文件");
	        }
		    
		    fileNumber++;
		}catch(Exception e)
		{
			e.printStackTrace();
			
			throw new QMException(e);
		}
	}
	public void addTableBomColHeader(Element bomtable)throws Exception
	{
		bomtable.addElement("col_header").addAttribute("id","1").addAttribute("type","basic").addText("structure_publish_type");
		bomtable.addElement("col_header").addAttribute("id","2").addAttribute("type","basic").addText("parent_num");
		bomtable.addElement("col_header").addAttribute("id","3").addAttribute("type","basic").addText("child_num");
		bomtable.addElement("col_header").addAttribute("id","4").addAttribute("type","basic").addText("use_quantity");
		bomtable.addElement("col_header").addAttribute("id","5").addAttribute("type","basic").addText("use_unit");
		bomtable.addElement("col_header").addAttribute("id","6").addAttribute("type","basic").addText("option_flag");
	}
	public void addTablePartColHeader(Element table)throws Exception
	{
		table.addElement("col_header").addAttribute("id","1").addAttribute("type","basic").addText("part_publish_type");
		table.addElement("col_header").addAttribute("id","2").addAttribute("type","basic").addText("mat_num");
		table.addElement("col_header").addAttribute("id","3").addAttribute("type","basic").addText("part_number");
		table.addElement("col_header").addAttribute("id","4").addAttribute("type","basic").addText("part_name");
		table.addElement("col_header").addAttribute("id","5").addAttribute("type","basic").addText("part_unit");
		table.addElement("col_header").addAttribute("id","6").addAttribute("type","basic").addText("part_type");
		table.addElement("col_header").addAttribute("id","7").addAttribute("type","basic").addText("part_source");
		table.addElement("col_header").addAttribute("id","8").addAttribute("type","basic").addText("part_colorflag");
		table.addElement("col_header").addAttribute("id","9").addAttribute("type","basic").addText("part_version");
		table.addElement("col_header").addAttribute("id","10").addAttribute("type","basic").addText("part_modifytime");
		table.addElement("col_header").addAttribute("id","11").addAttribute("type","basic").addText("splited");
		table.addElement("col_header").addAttribute("id","12").addAttribute("type","basic").addText("virtualFlag");
		table.addElement("col_header").addAttribute("id","13").addAttribute("type","basic").addText("status");
		table.addElement("col_header").addAttribute("id","14").addAttribute("type","basic").addText("r_code");
		table.addElement("col_header").addAttribute("id","15").addAttribute("type","basic").addText("route");
		table.addElement("col_header").addAttribute("id","16").addAttribute("type","basic").addText("source_type");
		table.addElement("col_header").addAttribute("id","17").addAttribute("type","basic").addText("isMoreRoute");
		table.addElement("col_header").addAttribute("id","18").addAttribute("type","basic").addText("CAPPLinkCharacterBunch");
		table.addElement("col_header").addAttribute("id","19").addAttribute("type","basic").addText("sizecoL");
		table.addElement("col_header").addAttribute("id","20").addAttribute("type","basic").addText("description");
		table.addElement("col_header").addAttribute("id","21").addAttribute("type","basic").addText("designPatternNO");
		table.addElement("col_header").addAttribute("id","22").addAttribute("type","basic").addText("notePatternNO");
		table.addElement("col_header").addAttribute("id","23").addAttribute("type","basic").addText("z_route");
		table.addElement("col_header").addAttribute("id","24").addAttribute("type","basic").addText("s_route");
		table.addElement("col_header").addAttribute("id","25").addAttribute("type","iba").addText("iba_fawtzbh");
		table.addElement("col_header").addAttribute("id","26").addAttribute("type","iba").addText("iba_fawtzbb");
		table.addElement("col_header").addAttribute("id","27").addAttribute("type","iba").addText("iba_zhcpz");
		table.addElement("col_header").addAttribute("id","28").addAttribute("type","iba").addText("iba_rem");
		table.addElement("col_header").addAttribute("id","29").addAttribute("type","iba").addText("iba_fah");
		table.addElement("col_header").addAttribute("id","30").addAttribute("type","iba").addText("iba_sjxh");
		table.addElement("col_header").addAttribute("id","31").addAttribute("type","iba").addText("iba_collectionid");
		table.addElement("col_header").addAttribute("id","32").addAttribute("type","iba").addText("part_iba_partURL");
		table.addElement("col_header").addAttribute("id","33").addAttribute("type","iba").addText("iba_shizhibs");
	}
	public final String getpartURL()
	{
		String tzbhID=(String)ibaDefinitionMap.get("part_iba_partURL");
		if(tzbhID!=null&&tzbhID.trim().length()>0)
		{
			String value=(String)ibaValueMap.get(tzbhID);
			if(value!=null&&value.trim().length()>0)
				return value;
			else return "";
		}
		return "";
	}
	public final String getcollectionid(MaterialSplitIfc ms)
	{
		String tzbhID=(String)ibaDefinitionMap.get("iba_collectionid");
		if(tzbhID!=null&&tzbhID.trim().length()>0)
		{
			String value=(String)ibaValueMap.get(tzbhID);
			//采集件标识只给 顶级件打
			if(value!=null&&value.trim().length()>0&&ms.getRootFlag())
				return value;
			else return "";
		}
		return "";
	}
	public final String getfawsjxh()
	{
		String tzbhID=(String)ibaDefinitionMap.get("iba_sjxh");
		if(tzbhID!=null&&tzbhID.trim().length()>0)
		{
			String value=(String)ibaValueMap.get(tzbhID);
			if(value!=null&&value.trim().length()>0)
				return value;
			else return "";
		}
		return "";
	}
	public final String getfawfah()
	{
		String tzbhID=(String)ibaDefinitionMap.get("iba_fah");
		if(tzbhID!=null&&tzbhID.trim().length()>0)
		{
			String value=(String)ibaValueMap.get(tzbhID);
			if(value!=null&&value.trim().length()>0)
				return value;
			else return "";
		}
		return "";
	}	
	public final String getfawsjbz()
	{
		String tzbhID=(String)ibaDefinitionMap.get("iba_rem");
		if(tzbhID!=null&&tzbhID.trim().length()>0)
		{
			String value=(String)ibaValueMap.get(tzbhID);
			if(value!=null&&value.trim().length()>0)
				return value;
			else return "";
		}
		return "";
	}
	public final String getfawzcpz()
	{
		String tzbhID=(String)ibaDefinitionMap.get("iba_zhcpz");
        System.out.println("ibaDefinitionMapibaDefinitionMap===="+ibaDefinitionMap);
        System.out.println("tzbhIDtzbhIDtzbhIDtzbhIDtzbhID===="+tzbhID);
		if(tzbhID!=null&&tzbhID.trim().length()>0)
		{
			String value=(String)ibaValueMap.get(tzbhID);
			System.out.println("ibaValueMapibaValueMapibaValueMap1111111111===="+ibaValueMap);
	        System.out.println("valuevaluevaluevaluevalue===="+value);

			if(value!=null&&value.trim().length()>0)
				return value;
			else return "";
		}
		return "";
	}
	public final String getfawtzbb()
	{
		String tzbhID=(String)ibaDefinitionMap.get("iba_fawtzbb");
		if(tzbhID!=null&&tzbhID.trim().length()>0)
		{
			String value=(String)ibaValueMap.get(tzbhID);
			if(value!=null&&value.trim().length()>0)
				return value;
			else return "";
		}
		return "";
	}
	public final String getfawtzbh()
	{
		String tzbhID=(String)ibaDefinitionMap.get("iba_fawtzbh");
		if(tzbhID!=null&&tzbhID.trim().length()>0)
		{
			String value=(String)ibaValueMap.get(tzbhID);
			if(value!=null&&value.trim().length()>0)
				return value;
			else return "";
		}
		return "";
	}
	public final String getSendRoute(MaterialSplitIfc ms)
	{
//		String a=getRoute(ms);
//		if(ms.getRCode()!=null){
//		if(a!=null&&a.trim().length()>0&&a.indexOf("=")>0){
//    		if(a.indexOf(ms.getRCode())>=0&&!(a.equals(ms.getRCode()))){
//        int c =a.indexOf(ms.getRCode());
//        int d =c+ms.getRCode().length();
//        int e =d+1;
//    	String b=a.substring(e,a.length());
//    	if(b.indexOf("-")>0)
//    	{
//    		return b.substring(0,b.indexOf("-"));
//    	}else if(b.indexOf("=")>0)
//    	{
//    		
//    		return b.substring(0,b.indexOf("="));
//    	}else{
//    		return b;
//    	}
//    	}else{
//    		return a;
//    	}
//    	}
//		}
    	//CCBegin by chudaming 20100524 3105912-X41，路线为供-酸-机=轮，3105912-X41-CG的装配路线应为‘机’，不应为‘酸’；其中酸不做拆分
    	String aj = getRoute(ms);
        String a = "";
        if(aj.indexOf("酸") > 0 && aj.indexOf("(酸)") < 0)
        {
            String y = aj.substring(0, aj.indexOf("酸") - 1);
            String z = aj.substring(aj.indexOf("酸") + 1, aj.length());
            a = y + z;
        } else
        if(aj.indexOf("酸") == 0){
            a = aj.substring(2, aj.length());
        }
        else{
            a = aj;
        }
    	//CCBegin by chudaming 20100506 为了取得s_route不查询数据库
    	String b;
    	String n;
    	String m;
    	Vector specialRouteCodeVec3=new Vector();
    	Vector specialRouteCodeVec3a=new Vector();
    	Vector specialRouteCodeVec5=new Vector();
    	Vector specialRouteCodeVec6=new Vector();
    	String makeCodeNameStr1 = RemoteProperty.getProperty("com.faw_qm.jferp.routecode." + ms.getRCode());
    	
    	if(a.length()==1){
    		
    		
    		
    		
    		return "";
    	}else if(a.indexOf("=" )<0){
    		if(ms.getRCode()!=null){
    		if(ms.getRCode().indexOf("-")<0){
    			StringTokenizer stringTokena = new StringTokenizer(
    					a, "-");
        		while (stringTokena.hasMoreTokens()) {
        			n=stringTokena.nextToken();
        			
        			specialRouteCodeVec3a.add(n);
        			
        			}
        		for(int kk=0;kk<specialRouteCodeVec3a.size();kk++)
			  	{
        			if(ms.getRCode().equalsIgnoreCase((String)specialRouteCodeVec3a.lastElement())){
        				return "";
        			}
        			else if(ms.getRCode().equalsIgnoreCase((String)specialRouteCodeVec3a.get(kk)))
			  		{
			  			
			  			return specialRouteCodeVec3a.get(kk+1).toString();

			  		}
			  	}
    		}
    		}
    		
    	}
    	else{
    	if(ms.getRCode().indexOf("-")<0){
    		StringTokenizer stringTokena = new StringTokenizer(
					a, "-");
    		while (stringTokena.hasMoreTokens()) {
    			n=stringTokena.nextToken();
    			specialRouteCodeVec3.add(n);
    			if(n.indexOf("=")<0){
    			specialRouteCodeVec3a.add(n);
    			}
    			}
    		String fffff=specialRouteCodeVec3.lastElement().toString();
    		StringTokenizer stringTokenaz = new StringTokenizer(
    				fffff, "=");
    		while (stringTokenaz.hasMoreTokens()) {
    			m=stringTokenaz.nextToken();
    			specialRouteCodeVec3a.add(m);
    			}

//    		修改开始
    		Vector as =new Vector();
    		Vector asz =new Vector();
    		System.out.println("specialRouteCodeVec3a=========0628======="+specialRouteCodeVec3a);
    		for(int u=0 ;u<specialRouteCodeVec3a.size()-1;u++){
    			as.add(specialRouteCodeVec3a.get(u));
    			
    		}
//    		System.out.println("asasasasas=========0628======="+as);
    		for(int x =0;x<as.size()-1;x++){
    			String x1=as.get(x).toString();
    			String x11 = RemoteProperty.getProperty("com.faw_qm.jferp.routecode." + x1);
    			boolean boobean=false;
    			int xx=x;
//    			Vector kkk=new Vector();
    			String xz="";
//    			System.out.println("0709==========00000000000--------------"+x);
//    			System.out.println("0709==========-111111111111111-------------"+xx);
//    			System.out.println("0709==========---22222222222222-----------"+x1);
    			for(int c =0;c<as.size()-xx-1;c++){
    				
    				String xc=as.get(xx+c+1).toString();
//    				System.out.println("sssssssssssssssss==========--------------"+xc);
    				String xc2 = RemoteProperty.getProperty("com.faw_qm.jferp.routecode." + xc);
    				if(x11.toString().equals(xc2.toString())&&!x11.trim().equalsIgnoreCase("S")&&
    						!x11.trim().equalsIgnoreCase("S)")&&
    						!xc2.trim().equalsIgnoreCase("S)")&&!xc2.trim().equalsIgnoreCase("S")){
                         xz+="-"+xc;
                         if(c!=0){
    					asz.remove(asz.get(asz.size()-1));
		    				 }
        				 asz.add(x1+xz);
        				x++;;
        				boobean=true;
//        				System.out.println("0000000000=========0628======="+asz);
        			}else{
        				if(boobean){
        					break;
        				}else{
        				asz.add(x1);
        				break;
        				}
        				
        			}
    				
    			}
    			System.out.println("0709==========-------------zsaaszaszzzzzzzzzzzzzzzzzzzzzzz-========"+asz);
//    			String x2=as.get(x+1).toString();
//    			
//    			
//    			if(x11.toString().equals(x22.toString())&&!x11.trim().equalsIgnoreCase("S")&&!x22.trim().equalsIgnoreCase("S)")){
//    				String xz =x1+"-"+x2;
//    				asz.add(xz);
//    				x=x+1;
////    				System.out.println("0000000000=========0628======="+asz);
//    			}else{
//    				asz.add(x1);
////    				System.out.println("11111111111111111111111111111=========0628======="+asz);
//    			}
    		}
    		asz.add(as.get(as.size()-1));
    		asz.add(specialRouteCodeVec3a.get(specialRouteCodeVec3a.size()-1));
    		int count=0;
    		int location=0;
    		for(int v=0;v<asz.size()-1;v++){
    			if(ms.getRCode().equals(asz.get(v))){
    				count++;
    				location=v;
    			}
    		}
    		if(count==1)
    		{
    			return asz.get(location+1).toString();
    		}else
    		{
    			if(makeCodeNameStr1!=null){
    			if(ms.getMaterialNumber().toString().equalsIgnoreCase(ms.getPartNumber().toString()))
    			{
    				return asz.get(asz.size()-1).toString();
    			}
    			else
    			{
    				String materialNumber=ms.getMaterialNumber();
    				
    				if(materialNumber.endsWith(makeCodeNameStr1))
    				{
    				  	for(int ll=0;ll<asz.size();ll++)
    				  	{
    				  		if(ms.getRCode().equalsIgnoreCase((String)asz.get(ll)))
    				  		{
    				  			return asz.get(ll+1).toString();
//    				  			break;
    				  		}
    				  	}
    				}else
    				{
    					System.out.println("materialNumbermaterialNumbermaterialNumbermaterialNumbermaterialNumber===============+"+materialNumber);
    					System.out.println("makeCodeNameStr1makeCodeNameStr1===============+"+makeCodeNameStr1);
//    					String tempString=materialNumber.substring(materialNumber.indexOf(makeCodeNameStr1));
    					
    					String mCount=materialNumber.substring(materialNumber.length()-1,materialNumber.length());
    					int mmcount=new Integer(mCount).intValue();
    					int dataFlag=0;
    					for(int kk=0;kk<asz.size();kk++)
    				  	{
    				  		if(ms.getRCode().equalsIgnoreCase((String)asz.get(kk)))
    				  		{
    				  			if(dataFlag==mmcount) 
    				  			{
    				  			return asz.get(kk+1).toString();
//    				  			break;
    				  			}
    				  			dataFlag++;
    				  		}
    				  	}
    				}
    			}
    		}else
    		{
    			return asz.get(asz.size()-1).toString();
    		}
    		}
    		
//    	 int k =a.indexOf(getRCode());
//         int d =k+getRCode().length();
//         int e =d+1;
//         System.out.println("kkkkkkkkkkkkkk========="+k);
//         System.out.println("ddddddddddd========="+d);
//         System.out.println("eeeeeeeeeeeeeeeeee========="+e);
//    	String jiehou =a.substring(e,a.length());
//    	if(!jiehou.equals("")&&jiehou!=null){
//    		if(jiehou.indexOf(";")<0&&jiehou.indexOf("-")<0){
//    			col.setValue(jiehou);
//    		}
//    		else if(jiehou.indexOf("-")>0){
//    			
//    			StringTokenizer stringToken3 = new StringTokenizer(
//    					jiehou, "-");
//    			while (stringToken3.hasMoreTokens()) {
//        			b=stringToken3.nextToken();
//        			specialRouteCodeVec3.add(b);
//        			}
//    			col.setValue(specialRouteCodeVec3.get(0).toString());
//    			
//    		}else{
//    			StringTokenizer stringToken4 = new StringTokenizer(
//    					jiehou, ";");
//    			while (stringToken4.hasMoreTokens()) {
//        			b=stringToken4.nextToken();
//        			specialRouteCodeVec4.add(b);
//        			}
//    			col.setValue(specialRouteCodeVec4.get(0).toString());
//    		}
    	
    	}else{
    		String rcode =ms.getRCode();
    		//CCBegin by chudaming 20100524 8507042-Q630，路线为备-生(酸)-生(切)-机(型)-机=驾，8507042-Q630-SC-1的装配路线应为‘机（型）-机’，不应为‘机（型）’；8507042-Q630的装配路线应为‘驾’，不应为‘型）
            String jiehou10 = "";
//            System.out.println("SHIYAN ====" + a.substring(a.indexOf(rcode)));
            jiehou10 = a.substring(a.indexOf(rcode));
            String jiehou1 = jiehou10.substring(rcode.length() + 1, jiehou10.length());
            //CCEnd by chudaming 20100524
//        	System.out.println("jiehou1jiehou1jiehou1jiehou1jiehou1jiehou1===="+jiehou1);
        	if(!jiehou1.equals("")&&jiehou1!=null){
        		if(jiehou1.indexOf("=")<0&&jiehou1.indexOf("-")<0){
        			return jiehou1;
        		}
        		else if(jiehou1.indexOf("-")>0){
        			
        			StringTokenizer stringToken6 = new StringTokenizer(
        					jiehou1, "-");
        			while (stringToken6.hasMoreTokens()) {
            			b=stringToken6.nextToken();
            			specialRouteCodeVec5.add(b);
            			}
        			return specialRouteCodeVec5.get(0).toString();
        			
        		}else{
        			StringTokenizer stringToken7 = new StringTokenizer(
        					jiehou1, "=");
        			while (stringToken7.hasMoreTokens()) {
            			b=stringToken7.nextToken();
            			specialRouteCodeVec6.add(b);
            			}
        			System.out.println("kjdlsajlkdjasldjasldjasld================================="+specialRouteCodeVec6.get(0).toString());
        			return specialRouteCodeVec6.get(0).toString();
        		}
        	}
    		
    	
    	}
    	//CCEnd by chudaming 20100506
//    	
////    	System.out.println("aaaaaaaaaaaaaaaaaaajjjjjjjjjjjjjjj===="+a);
//    	if(!a.equals("")&&a!=null){
//    	StringTokenizer stringToken = new StringTokenizer(
//				a, "-");
//    	Vector specialRouteCodeVec=new Vector();
//    	String b;
//    	String c;
//		while (stringToken.hasMoreTokens()) {
//			b=stringToken.nextToken();
//			specialRouteCodeVec.add(b);
//			}
////		System.out.println("specialRouteCodeVecspecialRouteCodeVec===="+specialRouteCodeVec);
//		if((specialRouteCodeVec.get(specialRouteCodeVec.size()-1).toString()).indexOf(";")>=0){
//		StringTokenizer stringToken1 = new StringTokenizer(
//				specialRouteCodeVec.lastElement().toString(),";");
//		Vector specialRouteCodeVec1=new Vector();
//		while (stringToken1.hasMoreTokens()) {
//			c=stringToken1.nextToken();
//			specialRouteCodeVec1.add(c);
//			}
//		
//		if(getRCode().equals(specialRouteCodeVec1.get(0).toString())||(getRCode().indexOf(specialRouteCodeVec1.get(0).toString())>=0)){
//			
//			col.setValue(specialRouteCodeVec1.get(1).toString());
//		}else{
//			try{
//			PersistService pService = (PersistService) EJBServiceHelper.
//	          getPersistService();
//
//	      Collection resultCol = null;
//	      //获得持久化对象
//	      QMQuery query = new QMQuery("MaterialStructure", "MaterialSplit");
//	      query.addCondition(0,new QueryCondition("childNumber", QueryCondition.EQUAL,
//	    		  getMaterialNumber()));
//	      query.addAND();
//	      query.addCondition(0,new QueryCondition("parentPartVersion", QueryCondition.EQUAL,
//	    		  materialSplit.getPartVersion()));
//	      query.addAND();
//	      query.addCondition(0,1,new QueryCondition("parentNumber",
//	    		  "materialNumber"));
//	      query.addAND();
//	      query.addCondition(0,1,new QueryCondition("parentPartVersion",
//		  "partVersion"));
//	     
//	      resultCol = pService.findValueInfo(query);
//	      Iterator iter = resultCol.iterator();
//	      while (iter.hasNext()) {
//	    	  Object ac[]=(Object[])iter.next();
//	    	  
//	    	  MaterialSplitIfc rule = (MaterialSplitIfc) ac[1];
//	    	  col.setValue(rule.getRouteCode());
//	    	  
//	      }
//			}catch (QMException e) {
//			      e.printStackTrace();
//				
//			}
//		}
//		}
//    	}
    	}
    
    	return "";
	}
	public final String getAssemRoute(MaterialSplitIfc ms)
	{
		String a=getRoute(ms);
		if(a!=null&&a.trim().length()>0){
			//chudaming
    		if(a.indexOf("=")>0){
    	String b=a.substring(a.indexOf("=")+1,a.length());
        return b;
    	}else if(a.indexOf("=")==0){
    		String v=a.substring(a.indexOf(";")+1,a.length());
    		return v;
    	}
    	}
    	return "";
	}
	public final String getIsMoreRoute(MaterialSplitIfc materialSplit)
    {
    	if(String.valueOf(materialSplit.getIsMoreRoute())==null)
    		return "0";
    	if(materialSplit.getIsMoreRoute())
    		return "1";
    	else
    		return "0";
    }
	
	public final String getManuRoute(MaterialSplitIfc ms)
	{
		String a=getRoute(ms);
    	if(a!=null&&a.trim().length()>0){
    		if(a.indexOf("=")>0)
    		{
    	String b=a.substring(0,a.indexOf("="));
        return b;
    	}
    		else if(a.indexOf("=")==0){
    			return "";
    		}
    		else{
    		return a;
    	}
    	}
    	return a;
	}
    public  final String getRoute(MaterialSplitIfc ms)
    {
        return ms.getRoute() == null ? "" : ms.getRoute();
    }
    public final String getVirtualFlag(MaterialSplitIfc ms)
    {
    	if(String.valueOf(ms.getVirtualFlag()) == null)
            return "0";
        if(ms.getVirtualFlag())
            return "1";
        else
            return "0";
    }
    private final String getSplitedStr(MaterialSplitIfc ms)
    {
        if(String.valueOf(ms.getSplited()) == null)
        {
            return "0";
        }
        else
        {
            if(ms.getSplited())
            {
                return "1";
            }
            else
            {
                return "0";
            }
        }
    }
    /**
     * 获取DTD描述信息列表。
     * @return List DTD描述信息列表。
     */
    private final List getInternalDeclarations()
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("setInternalDeclarations() - start"); //$NON-NLS-1$
        }
        List decls = new ArrayList();
        decls.add(new ElementDecl("bom", "(description, table+)"));
        decls.add(new ElementDecl("description",
                "(filenumber,type,date,sourcetag,notes)"));
        decls.add(new ElementDecl("filenumber", "(#PCDATA)"));
        decls.add(new ElementDecl("type", "(#PCDATA)"));
        decls.add(new ElementDecl("date", "(#PCDATA)"));
        decls.add(new ElementDecl("sourcetag", "(#PCDATA)"));
        decls.add(new ElementDecl("notes", "(#PCDATA)"));
        decls.add(new ElementDecl("table", "(col_header+, record+)"));
        decls
                .add(new AttributeDecl("table", "name", "CDATA", "#REQUIRED",
                        null));
        decls.add(new ElementDecl("col_header", "(#PCDATA)"));
        decls.add(new AttributeDecl("col_header", "id", "CDATA", "#REQUIRED",
                null));
        decls.add(new AttributeDecl("col_header", "type", "CDATA", "#REQUIRED",
                null));
        decls.add(new ElementDecl("record", "(col+)"));
        decls.add(new ElementDecl("col", "(#PCDATA)"));
        decls.add(new AttributeDecl("col", "id", "CDATA", "#REQUIRED", null));
        if(logger.isDebugEnabled())
        {
            logger.debug("setInternalDeclarations() - end"); //$NON-NLS-1$
        }
        return decls;
    }
	/**
	 * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
	 * 
	 * @param partIfc
	 *            零部件。
	 * @throws QMException
	 *             return PartUsageLinkIfc的对象集合。
	 */
	private final List getUsageLinks(final QMPartIfc partIfc)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUsageLinks(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("参数：partIfc==" + partIfc);
		}
		// 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
		List usesPartList = new ArrayList();
		try {
			StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			usesPartList = (List)spservice.getUsesPartMasters(partIfc);
			
		} catch (QMException e) {
			Object[] aobj = new Object[] { partIfc.getPartNumber() };
			// "获取名为*的零部件结构时出错！"
			logger.error(Messages.getString("Util.17", aobj) + e);
			throw new ERPException(e, RESOURCE, "Util.17", aobj);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("getUsageLinks(QMPartIfc) - end"); //$NON-NLS-1$
		}
		return usesPartList;
	}
	
	/**
	 * added by dikefeng 20100504
	 *在这里添加一个方法获得零部件的子件以及与子件间的关联
	 * @author 狄科峰
	 * @param partIfc 零部件
	 */
	private final ArrayList getSubPartsAndLinks(final String partNumber)
	throws QMException {
		ArrayList AllParts=new ArrayList();
		Connection conn;
		try {
			conn = getConnection();
		
		ResultSet result = null;
		
		Statement select = conn.createStatement();
		result = select.executeQuery("select * from PDMBOM0619 where parentnumber = '"+partNumber);
		System.out.println("1111111111========"+result);

		PersistService pService=(PersistService)EJBServiceHelper.getPersistService();

		
		String[] partData=null;
        while (result.next()) {
        	partData=new String[10];
        	//父件编号
        	String parentNumber = result.getString("parentNumber");
        	//子件编号
        	String childNumber = result.getString("childNumber");
        	//数量
    		String bomNumber = result.getString("bomNumber");
    		
    		System.out.println("jieguo  ========"+parentNumber);
    		System.out.println("jieguo  ========"+childNumber);
    		System.out.println("erpNumber  ========"+bomNumber);

    		partData[0]=parentNumber;
    		partData[1]=childNumber;
    		//有;号即多条路线
    		partData[2]=bomNumber; 
    	
    		AllParts.add(partData);
		}
        System.out.println("333333333333333========"+AllParts);
      
		conn.commit();
		conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return AllParts;
	}
	/**
	 * 设置其它需要发布的属性，与物料拆分及结构无关，在零件拆分时复制到每个拆分后的物料记录行。
	 * 
	 * @param partIfc
	 * @param mSplitIfc
	 * @throws QMException
	 */
	private void setMaterialSplit(String[] partQuan, MaterialSplitIfc mSplitIfc)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger
			.debug("setMaterialSplit(QMPartIfc, MaterialSplitIfc) - start"); //$NON-NLS-1$
			logger.debug("参数：partIfc==" + partQuan);
			logger.debug("参数：mSplitIfc==" + mSplitIfc);
		}
		//零部件编号
		String partNumber=partQuan[0];
		//零部件版本
		String partVersionValue=partQuan[1];
		//erp编号
		String erpNumber = partQuan[2];
//		制造路线
		String routeAsString = partQuan[3];
//		全路线串
		String routeAllCode = partQuan[3]+";"+partQuan[4];
//		装配路线
		String routeAssemStr = partQuan[4];
		//虚拟件
		String virtualFlag = partQuan[5];
		//颜色件标识
		String colorFlag = partQuan[6];
		//来源
		String proceduy = partQuan[7];
		//类型
		String partType = partQuan[8];
		String erpPartNumber = partNumber + "/" + partVersionValue;
		mSplitIfc.setPartName(erpPartNumber);
		mSplitIfc.setDefaultUnit("个");
		mSplitIfc.setProducedBy(proceduy);
		mSplitIfc.setPartType(partType);
		StringBuffer buffer = new StringBuffer();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		mSplitIfc.setPartModifyTime(time);
		mSplitIfc.setOptionCode("");
		

	     
	      if(colorFlag==null||colorFlag=="N")
	    	  mSplitIfc.setColorFlag(false);
	      else{
	    	  //只给最顶级物料打标识
	    	  if(colorFlag.equalsIgnoreCase("Y")&&mSplitIfc.getRootFlag())
	    		  mSplitIfc.setColorFlag(true);
	    	  else
	    		  mSplitIfc.setColorFlag(false);
	      }
		mSplitIfc.setDomain(DomainHelper.getDomainID(mSplitDefaultDomainName));
		if (logger.isDebugEnabled()) {
			logger.debug("setMaterialSplit(QMPartIfc, MaterialSplitIfc) - end"); //$NON-NLS-1$
		}
	}
	/**
	 * 将路线中的路线代码转化为List,并且根据属性文件的配置去掉路线中的特殊代码。
	 * 
	 * @param routeStr
	 * @return
	 */
	public List getRouteCodeList(String routeStr) {
		StringTokenizer routeTok = new StringTokenizer(routeStr, dashDelimiter);
		String routeCode = "";
		//HashMap specRCHashMap = getDleteWhenSplitRC();
		List routeCodeList = new ArrayList();
		boolean isFirst = true;
		while (routeTok.hasMoreTokens()) {
			routeCode = routeTok.nextToken();
			if (isFirst) {
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
	public static void main(String[] args) {
		try {
			String  a= "料-酸-冲=总;总";
			System.out.println("aaaaaaaaa="+a.substring(0,a.indexOf(";")));
			//e.mainExport();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
