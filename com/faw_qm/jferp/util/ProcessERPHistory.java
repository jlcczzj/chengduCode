/**
 * 生成程序PublishERPHistory.java	1.0              2007-9-27
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.*;

import com.faw_qm.erppartreload.ERPMiddleBOMLink;
import com.faw_qm.erppartreload.ERPMiddlePart;
import com.faw_qm.erppartreload.util.ERPPartReLoad;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.uidgenservice.UIDHelper;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <p>Title: 历史的零部件的物料处理。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 
 * @version 1.0
 */
public class ProcessERPHistory 
{
	//创建数据库连接
	public static Connection conn = null;
	//缓存ERP物料表原始数据，这里的数据已经整合成ERPMiddlePart对象
	private  static Vector<ERPMiddlePart> originalMiddlePart;
	//用HashMap的形式缓存ERPMiddlePart对象，便于用ERP编号查找。key-ERP编号，value-ERPMiddlePart对象
	//private static HashMap<String, ERPMiddlePart> originalPartTable;
	//缓存ERP BOM表原始数据，这里的数据已经整合成ERPMiddleBOMLink对象,key-父件编号，value-ERPMiddleBOMLink对象
	private static HashMap<String, Vector<ERPMiddleBOMLink>> originalMiddleBOMLink;
	//缓存结构信息，这个是可以进入PDM的
	private static HashMap<String,Vector<ERPMiddleBOMLink>> PDMBOMTable;
	//本次处理数据的数量
	private static String count = "1000";
	//缓存当前正在处理的行数
	private static int treatedCount = 0;
	//缓存当前正在处理的零件编号
	private static String treatedNumber;
	//缓存当前正在处理的BOM编号
	private static String treatedBOMNumber;
	
	private static String  pdmoid = "jdbc:oracle:thin:@10.0.227.1:1521:orcl";

	private static String pdmuid = "etest";

	private static String pdmpwd = "etest";
	//IBA的”发布源版本“ID
	private static String sourceVersion = "StringDefinition_7646";
	private static String path =  "\\opt\\pdmv4r3\\jfdomain\\bin\\erpHistory2014.log";
	//log文件的存放路径
	private static String logfilepath;
	//log文件的存放路径
	private static String logFileName;
	//uid
	private static int uid = 0;
	private  static String partnumber = "";
	//	创建返回对象集合
	private static Vector<ERPMiddlePart> colltemp = null;
	private static HashMap partMap = null;
	private static Vector partNumberVec = null ;
	public static void main(String[] args)
	{
		startBOM1();
	}
	public static boolean startBOM1()
	{
	
		try
		{

			
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			// 建立到数据库的连接
			
			ERPPartReLoad reload = new ERPPartReLoad();
			final SimpleDateFormat simple = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss ");
			String  stime = simple.format(new Date());
			//计时
			long aa = System.currentTimeMillis();
		    System.out.println("start erp interface data========="+stime); 
			for(int x=0; x<6000; x++)
			{
				conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);
				//System.out.println("start times ============="+x+"****data========= "+stime);
				//程序开始首先确定log文件
				//String path = System.getProperty("java.class.path");
				
				//System.out.println("path="+path);
				long bb = System.currentTimeMillis();
//				writeLog(mes);
				//正式开始...
			//	originalPartTable = new HashMap<String, ERPMiddlePart>();
				originalMiddlePart = new Vector<ERPMiddlePart>();
				originalMiddleBOMLink = 
							new HashMap<String, Vector<ERPMiddleBOMLink>>();
				PDMBOMTable = 
							new HashMap<String, Vector<ERPMiddleBOMLink>>();
				aa = System.currentTimeMillis();
				//获得处理的ERP物料
				originalMiddlePart = (Vector<ERPMiddlePart>) 
									getAllMiddelParts(Integer.parseInt(count));
				//System.out.println("1***** "+ (System.currentTimeMillis()-aa));
				aa = System.currentTimeMillis();
				if(originalMiddlePart == null || originalMiddlePart.size() == 0)
					break;

				//缓存一下BOM结构
			//	System.out.println("middelParts111111.xize==="+originalMiddlePart.size());
				
				originalMiddleBOMLink = getAllMiddelPartsLinks(originalMiddlePart);
				//System.out.println("2***** "+ (System.currentTimeMillis()-aa));
			
		
				aa = System.currentTimeMillis();
				HashMap<String, Vector<ERPMiddleBOMLink>> originalMiddleBOMLink1 = new HashMap();
			
				if(colltemp.size()>0){
					partnumber = "";
					for(int i=0; i<colltemp.size(); i++)
					{
						ERPMiddlePart part = colltemp.elementAt(i);
						String rcode = part.getManuRoute();
						part.setManuRoute(getPartManuRoute(part));
						int a =saveMaterialSplit(part,rcode);				
						if(a>0){
//							保存JFMATERIALSTRUCTURE
							saveMaterialStruct(part,originalMiddleBOMLink);
						}
						
					}
				}
		
				//System.out.println("3***** "+ (System.currentTimeMillis()-aa));
			
				aa = System.currentTimeMillis();
				PDMBOMTable = getSubParts(originalMiddlePart);	
				//System.out.println("4***** "+ (System.currentTimeMillis()-aa));
				aa = System.currentTimeMillis();
				//System.out.println("PDMBOMTable==="+PDMBOMTable);
				//如果存在可进入PDM PART的数据，则进行下一步往PDM中间表里进
				if(originalMiddlePart.size()>0)
				{
					for(int i=0; i<originalMiddlePart.size(); i++)
					{
						ERPMiddlePart part = originalMiddlePart.get(i);
							//保存JFMATERIALPARTSTRUCTURE
							savePartStruct(part,PDMBOMTable);
							
						
					}
				}
				//System.out.println("5***** "+ (System.currentTimeMillis()-aa));
				
				//提交变更
				setCounter();
				conn.commit();
				treatedCount = 0;
				originalMiddlePart.clear();
				originalMiddlePart = null;
//				originalPartTable.clear();
//				originalPartTable = null;
				originalMiddleBOMLink.clear();
				originalMiddleBOMLink = null;
			//	System.gc();			
		
			
			 stime = simple.format(new Date());
			 System.out.println((x) +"***** "+ (System.currentTimeMillis()-bb)+"*****"+stime);
			 conn.close();
			 conn = null;
			}
			
			 System.out.println("import success");
		}
		catch (Exception e)
		{
			String mes = "\n" + "----------华丽的分隔符----------" + "\n";
			mes += "BOM回导预处理出错！" + "\n" + "处理信息如下：" + "\n";
		//	mes += "数据库信息:"+"\n"+"IP："+pdmoid+"\n"+"name:"+pdmuid+"\n";
			mes += "本次处理数量:"+count+"\n"+"物料表已经处理的数量:"+treatedCount+"\n";
			mes += "出错期正在处理的零部件是:"+treatedNumber+"\n";;
			mes += "出错期正在处理的BOM是:"+treatedBOMNumber+"\n";
			mes += "抛出的异常是:"+"\n"+e.getMessage();
			writeLog(mes);
			e.printStackTrace();
			setCounter();
			return false;
		}
		return true;
	}
	/**
	 * 将中间零部件内容（包括直接子件关联）写入到PDM PART 中间表（直接子件关联写入PDM BOM 中间表）。
	 * 如果只在物料中间表中存在，在BOM中间表中不存在，制造路线去物料中间表中的制造路线。
	 * @param mpart
	 * @author houhf
	 */
	public static void savePartStruct(ERPMiddlePart mpart,HashMap originalMiddleBOMLink) throws Exception 
	{
		//记录数据库保存结果信息
		String res = "";
		boolean flag = true;
		ERPPartReLoad reload = new ERPPartReLoad();
		long curTime=System.currentTimeMillis();
		Timestamp createTime=new Timestamp(curTime);
   	    Timestamp modifyTime=new Timestamp(curTime);
   	    //获取制造路线
   	    String ERPnumber = mpart.getERPNumber();
        Vector<ERPMiddleBOMLink> resultLinkVec = new Vector<ERPMiddleBOMLink>();
        resultLinkVec = (Vector<ERPMiddleBOMLink>) PDMBOMTable.get(ERPnumber);
        if(resultLinkVec.size()==0)
        	return;
//   	    //零件类型
//   	    String parttype = PublishHistoryHelper.getPartType(mpart);
//   	    //虚拟件
//   	    int xnj = PublishHistoryHelper.getxnj(mpart);
        
        for(int j=0;j<resultLinkVec.size();j++){
        	String bsoid = getUid("JFMATERIALPARTSTRUCTURE");
        	ERPMiddleBOMLink bomlink = resultLinkVec.elementAt(j);
        	//保存物料表
    		String sql = "insert into JFMaterialPartStructure values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    		
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		//拆分零件号，分离出版本
    		String erpParentNumber=bomlink.getParentNumber();
			String erpchildNumber=bomlink.getChildNumber().trim();
//			String str1[] = reload.splitPartNumber(erpParentNumber);
//			String str2[] = reload.splitPartNumber(erpchildNumber);
//		    String parentNumber = str1[0];
//		    String childNumber = str2[0];
		    //如果父件子件相同不保存
    		if(erpParentNumber.equals(erpchildNumber))
    			continue;
    		pstmt.setString(1,  bsoid);//BSOID
    		pstmt.setTimestamp(2,  createTime);//CREATETIME
    		pstmt.setString(3,  "0");//LEVELNUMBER
    		pstmt.setInt(4,  0);//OPTIONFLAG
    		pstmt.setString(5, ERPnumber);//PARENTPARTNUMBER
    		pstmt.setString(6,  "个");//DEFAULTUNIT
    		pstmt.setInt(7,  1);//QUANTITY
    		pstmt.setString(8,  "N");//MATERIALSTRUCTURETYPE
    		pstmt.setString(9,  erpParentNumber);//PARENTNUMBER
    		pstmt.setTimestamp(10,  modifyTime);//MODIFYTIME
    		pstmt.setString(11, erpchildNumber);//CHILDNUMBER
    		pstmt.setString(12, mpart.getPartVersion().trim());//PARENTPARTVERSION
    		pstmt.setString(13, "");//WLS_TEMP

    		int i = pstmt.executeUpdate();

        
    		if (i > 0)
    		{
    			setPartTreated(mpart.getERPNumber());
    		
    		}
    		else
    		{
    			//保存失败
    			res = "向PDM中间表PART保存数据出错！";
    			flag = true;
    		}
    		if(flag)
    		{
    			//提交变更
    			//conn.commit();
    			//关闭连接
    			pstmt.close();
    			pstmt = null;
    		}
    		else
    		{
    			throw(new Exception(res));
    		}
        }
    		
		return ;
	}
	/* 将中间零部件内容（包括直接子件关联）写入到PDM PART 中间表（直接子件关联写入PDM BOM 中间表）。
	 * 如果只在物料中间表中存在，在BOM中间表中不存在，制造路线去物料中间表中的制造路线。
	 * @param mpart
	 * @author houhf
	 */
	public static void saveMaterialStruct(ERPMiddlePart mpart,HashMap<String, Vector<ERPMiddleBOMLink>> originalMiddleBOMLink1) throws Exception 
	{
		//记录数据库保存结果信息
		String res = "";
		boolean flag = true;
		ERPPartReLoad reload = new ERPPartReLoad();
		long curTime=System.currentTimeMillis();
		Timestamp createTime=new Timestamp(curTime);
   	    Timestamp modifyTime=new Timestamp(curTime);
   	    //获取制造路线
   	    String ERPnumber = mpart.getERPNumber();
        Vector<ERPMiddleBOMLink> resultLinkVec = new Vector<ERPMiddleBOMLink>();
        resultLinkVec = (Vector<ERPMiddleBOMLink>) originalMiddleBOMLink1.get(ERPnumber);
//        System.out.println("ERPnumber="+ERPnumber);
//        System.out.println("partNumber11111="+resultLinkVec.elementAt(0).getPrentPartNumber());
        if(resultLinkVec==null||resultLinkVec.size()==0)
        	return;
//   	    //零件类型
//   	    String parttype = PublishHistoryHelper.getPartType(mpart);
//   	    //虚拟件
//   	    int xnj = PublishHistoryHelper.getxnj(mpart);
        for(int j=0;j<resultLinkVec.size();j++){
        	String bsoid = getUid("JFMATERIALSTRUCTURE");
        	ERPMiddleBOMLink bomlink = resultLinkVec.elementAt(j);
        	//保存物料表
    		String sql = "insert into JFMATERIALSTRUCTURE values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	 
    		
    		String erpParentNumber=bomlink.getParentNumber();
			String erpchildNumber=bomlink.getChildNumber().trim();
			String partNumber = bomlink.getPrentPartNumber();
//			
//			System.out.println("erpParentNumber1111111="+erpParentNumber);
//			System.out.println("partNumber11111="+partNumber);
//			判断子件是否是半成品，如果是再继续
			if(!reload.isHalfPart(erpchildNumber))
			{
				continue;
			}
			PreparedStatement pstmt = conn.prepareStatement(sql);  

    		pstmt.setString(1,  bsoid);//BSOID
    		pstmt.setTimestamp(2,  createTime);//CREATETIME
    		pstmt.setTimestamp(3,  modifyTime);//MODIFYTIME
    		pstmt.setString(4,  partNumber);//PARENTPARTNUMBER
    		pstmt.setString(5,  mpart.getPartVersion().trim());//PARENTPARTVERSION
    		pstmt.setString(6, erpParentNumber);//PARENTNUMBER
    		pstmt.setString(7,  erpchildNumber);//CHILDNUMBER
    		pstmt.setInt(8,  1);//QUANTITY
    		pstmt.setInt(9,  0);//LEVELNUMBER
    		pstmt.setString(10,  "个");//DEFAULTUNIT	
    		pstmt.setString(11,  "N");//MATERIALSTRUCTURETYPE
    		pstmt.setString(12,  "");//OPTIONFLAG
    		pstmt.setString(13,  bomlink.getSpecNumber());//SUBGROUP
    		pstmt.setString(14, bomlink.getBomNumber());//BOMLINE  	
    		int i = pstmt.executeUpdate();
    		if (i > 0)
    		{
    			
    		}
    		else
    		{
    			//保存失败
    			res = "向物料结构表保存数据出错！";
    			
    		}
    		
    			//关闭连接
    			pstmt.close();
    			pstmt = null;
    		
        }
	
		return ;
	}
	/**
	 * 将中间零部件内容（包括直接子件关联）写入到PDM PART 中间表（直接子件关联写入PDM BOM 中间表）。
	 * 如果只在物料中间表中存在，在BOM中间表中不存在，制造路线去物料中间表中的制造路线。
	 * @param mpart
	 * @author houhf
	 */
	public static int saveMaterialSplit(ERPMiddlePart mpart,String rcode) throws Exception 
	{
		//记录数据库保存结果信息
		//System.out.println("mpart="+mpart.getPartNumber());
		String res = "";
		boolean flag = true;
		ERPPartReLoad reload = new ERPPartReLoad();
		String bsoid = getUid("JFMATERIALSPLIT");
		long curTime=System.currentTimeMillis();
		Timestamp createTime=new Timestamp(curTime);
   	    Timestamp modifyTime=new Timestamp(curTime);
   	    //获取制造路线
   	    String manuRoute = getManuRoute(mpart.getERPNumber());
   	    //零件类型
   	    String parttype = getPartType(mpart);
   	    //虚拟件
   	    int xnj = getxnj(mpart);
   	    String partnumber = mpart.getERPNumber();
   		if(reload.isHalfPart(partnumber)){
   			partnumber=getsplitPartNumber(partnumber);
   		}
		//保存物料表
   	 	String sql = "insert into JFMATERIALSPLIT values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
		",?,?,?,?,?,?,?,?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		/*表字段顺序BSOID、CREATETIME、MODIFYTIME、DOMAIN、PARTNUMBER、PARTVERSION、
		 STATE、MATERIALNUMBER、SPLITED、VIRTUALFLAG、STATUS、ROUTECODE、ROUTE、
		 PARTNAME、DEFAULTUNIT、PRODUCEDBY、PARTTYPE、PARTMODIFYTIME、OPTIONCODE、
		 RCODE、MATERIALSPLITTYPE、COLORFLAG、ROOTFLAG、ISMOREROUTE*/
		
		pstmt.setString(1,  bsoid);//BSOID
		pstmt.setTimestamp(2,  createTime);//CREATETIME
		pstmt.setTimestamp(3,  modifyTime);//MODIFYTIME
		pstmt.setString(4,  "Domain_102");//DOMAIN
		pstmt.setString(5,  partnumber);//PARTNUMBER
		pstmt.setString(6,  mpart.getPartVersion().trim());//PARTVERSION
		pstmt.setString(7,  "生产准备");//STATE
		pstmt.setString(8,  mpart.getERPNumber().trim());//MATERIALNUMBER
		pstmt.setInt(9,  1);//SPLITED
		pstmt.setInt(10, xnj);//VIRTUALFLAG
		pstmt.setInt(11, 2);//STATUS
		pstmt.setString(12, rcode);//ROUTECODE
		pstmt.setString(13, manuRoute);//ROUTE
		pstmt.setString(14, mpart.getPartName());//partname
		pstmt.setString(15,  "必装");//DefaultUnit	
		pstmt.setString(16, "N");//采购标识
		pstmt.setString(17, parttype);
		pstmt.setTimestamp(18,  modifyTime);//partMODIFYTIME
		pstmt.setString(19,  "");//OPTIONCODE
		pstmt.setString(20,  rcode);//RCODE
		pstmt.setString(21,  "N");//MATERIALSPLITTYPE
	
		pstmt.setInt(22, 0);//COLORFLAG
		pstmt.setInt(23, 0);//ROOTFLAG
		pstmt.setInt(24, 0);//ISMOREROUTE
		int i = pstmt.executeUpdate();
		if (i > 0)
		{
			//保存成功
			//在ERP表中打标记
			String sql1 = "update ERPMATERIAL set ISPRETREATE = 1 where mtlNO = '" +
						  mpart.getERPNumber() + "'";
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			//System.out.println("sql1====" + sql1);
			int ii = pstmt1.executeUpdate();
			//关闭连接
			pstmt1.close();
			pstmt1 = null;
		}
		else
		{
			//保存失败
			res = "向PDM中间表PART保存数据出错！";
			flag = true;
		}
		if(flag)
		{
			//提交变更
			//conn.commit();
			//关闭连接
			pstmt.close();
			pstmt = null;
		}
		else
		{
			throw(new Exception(res));
		}
		return i;
	}
	/**
	 * 获得制造路线串。
	 * @throws SQLException 
	 * @parm houhf
	 */
	public static String getManuRoute(String erpnumber) throws SQLException{
		String manuRoute = "";
		String ASSRoute = "";
		String sql1 = "select  manuroute,ASSROUTE from PDMPART  where erpnumber = '" + erpnumber + "'";
		PreparedStatement pstmt1 = conn.prepareStatement(sql1);
		ResultSet rs1 = pstmt1.executeQuery();
		while(rs1.next())
		{
			manuRoute = 
				rs1.getString(1)==null ? "" : rs1.getString(1).trim();
			ASSRoute = 
				rs1.getString(2)==null ? "" : rs1.getString(2).trim();
			break;
		}
		//关闭连接
		pstmt1.close();
		pstmt1 = null;
		rs1.close();
		rs1 = null;
		String route="";
		if(manuRoute.length()==0){
			if(ASSRoute.length()!=0){
				route = ASSRoute;
			}else{
				route =  "";
			}
		}else{
			if(ASSRoute.length()!=0){
				route =  manuRoute+";"+ASSRoute;
			}else{
				route =  manuRoute;
			}
		}
		
		return manuRoute+";"+ASSRoute;
	}
	/**
	 * 得到所有的ERP PART 中间表数据。不包括半成品，颜色件，原材料。通过sql调用获取数据。
	 * 现在不通过sql获取了，直接读缓存的数据。
	 * @return 返回ERPMiddlePart的集合，这里的对象时经过过滤能够保存到PDMPART的。
	 * @author houhf
	 * @throws Exception 
	 */
	public static Collection<ERPMiddlePart> getAllMiddelParts(int reloadCount)
															throws Exception
	{
		long a = System.currentTimeMillis();
		colltemp=new Vector<ERPMiddlePart>();
		partMap = new HashMap();
		partNumberVec = new Vector();
	//	String partNumber = "";
		ERPPartReLoad reload = new ERPPartReLoad();
		//创建返回对象集合
		Collection<ERPMiddlePart> coll = new Vector<ERPMiddlePart>();
		try
		{

			String sql = "select " + "a.mtlNO, a.MTLTYPE, a.MTLDES, a.measureBaseUnit, " +
						 "a.industryStndDes, a.pageFromat, a.isConfFlag, " +
						 "a.procureType, a.specProcureType, a.REMARK" +
			 			 " from " +
			 			 "(select t.* from ERPMATERIAL t " +
			 			 "where t.ISPRETREATE = 0) a where rownum < "+count;
			
		
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				treatedCount++;
		
				ERPMiddlePart temp = new ERPMiddlePart();
				temp.setERPNumber
					(rs.getString(1) == null ? "" : rs.getString(1).trim());
				temp.setPartType
					(rs.getString(2)== null ? "" : rs.getString(2).trim());
				temp.setPartName
					(rs.getString(3)== null ? "" : rs.getString(3).trim());
				temp.setDefaultUnit
					(rs.getString(4)== null ? "" : rs.getString(4).trim());
				temp.setManuRoute
					(rs.getString(5)== null ? "" : rs.getString(5).trim());
				temp.setAssRoute
					(rs.getString(6)== null ? "" : rs.getString(6).trim());
				temp.setColorFlag
					(rs.getString(7)== null ? "" : rs.getString(7).trim());
				temp.setProducedBy
					(rs.getString(8)== null ? "" : rs.getString(8).trim());
				temp.setDummyPart
					(rs.getString(9)== null ? "" : rs.getString(9).trim());
				temp.setDesc
					(rs.getString(10)== null ? "" : rs.getString(10).trim());
	
				//System.out.println("ERP编号是=====" + temp.getERPNumber());
				if(temp != null)
				{

					treatedNumber = temp.getERPNumber();
					//originalPartTable.put(temp.getERPNumber(), temp);
					
				//	System.out.println("treatedNumber=====" + treatedNumber);
					//将半成品和成品加入集合
					if( reload.isColorPart(temp.getERPNumber().trim())
								|| temp.getDefaultUnit().equalsIgnoreCase("KG"))
						{
							setPartTreated(temp.getERPNumber());
							continue;
						}
					
				
//					如果是零部件则根据具体的规则创建适合PDMPART的ERPMiddlePart对象
					//拆分零件编号版本
					String str[] = reload.splitPartNumber(temp.getERPNumber());
					//无法获取成品版本
					temp.setPartNumber(str[0]);
				//	System.out.println("str[0]=====" + str[0]);
					temp.setPartVersion(str[1]);
					colltemp.add(temp);
		
					//先判断是否是半成品，颜色件，原材料如果是则不再继续
					if(reload.isHalfPart(temp.getERPNumber().trim()))
					{
						setPartTreated(temp.getERPNumber());
						continue;
					}
												
					coll.add(temp);
					partNumberVec.add(str[0]);
					//partMap.put(arg0, str[0]);
					
				
				}
				//计时
				 a=System.currentTimeMillis()-a;
			//	System.out.println("查询完一条数据用时"+a);
//				a=System.currentTimeMillis();
			}
			//关闭连接
			pstmt.close();
			pstmt = null;
			rs.close();
			rs = null;
		}
		catch (Exception e)
		{
//			String massage = "获取物料出错！！！" + "\n" + "/n";
//			writeLog(massage + e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return coll;
	}
	/**
	 * 联合PDM检查零部件是否需要导入
	 * @param ERPNumber
	 * @return 0 不需要 1 需要 2 更新 3 新建 
	 * @author houhf
	 */
	public static int isNeedToTreate(ERPMiddlePart part)
	{
		ERPPartReLoad reload = new ERPPartReLoad();
		//先判断是否是半成品，颜色件，原材料如果是则不再继续
		if(reload.isHalfPart(part.getERPNumber().trim())
			||reload.isColorPart(part.getERPNumber().trim())
				||part.getDefaultUnit().equalsIgnoreCase("KG"))
		{
			return 1;
		}
		//开始拆分编号，拆分出编号和版本
		String[] numberAndVersion = reload.splitPartNumber(part.getERPNumber());
//		//创建数据库连接
//		Connection conn = null;
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// 建立到数据库的连接
//			conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);
			
			//获得最新小版本的ID和大版本信息
			String sql = "select t.BSOID, t.VERSIONID from QMPART t " + 
						 "where t.MASTERBSOID = " + 
						 "(select a.BSOID from QMPARTMASTER a "+
						 "where a.PARTNUMBER = '" + numberAndVersion[0] + "'" +
						 " and a.BSOID not like '%(%' and rownum <2)" +
						 "ORDER BY t.VERSIONVALUE DESC";
			
//			System.out.println("SQL=====" + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				String partID = 
					rs.getString(1) == null ? "" : rs.getString(1).trim();
				String partVersion = 
					rs.getString(2) == null ? "" : rs.getString(2).trim();
				
				//获得发布源版本
				String sql1 = "select t.VALUE from STRINGVALUE t " +
							  "where t.DEFINITIONBSOID = '"+sourceVersion+"' "+
							  "and t.IBAHOLDERBSOID = '" + partID + "'";
//				System.out.println("SQL1=====" + sql1);
				PreparedStatement pstmt1 = conn.prepareStatement(sql1);
				ResultSet rs1 = pstmt1.executeQuery();
				while(rs1.next())
				{
					partVersion = 
						rs1.getString(1)==null ? "" : rs1.getString(1).trim();
					break;
				}
				//关闭连接
				pstmt1.close();
				pstmt1 = null;
				rs1.close();
				rs1 = null;
				//发布源版本有些是带有小版本信息的，这里需要处理一下
				if(partVersion.indexOf(".")>0)
					partVersion = 
						partVersion.substring(0, partVersion.indexOf("."));
				
				int result =0;
				if(numberAndVersion[1] != null 
					&& numberAndVersion[1].trim().length()>0)
				{
					result = numberAndVersion[1].compareTo(partVersion);
				}
				
				if(result>0)//ERP版本大于PDM版本
					return 2;
				if(result==0)//ERP版本等于PDM版本,导入路线
					return 2;
				if(result<0)//ERP版本小于PDM版本,不作操作
					return 0;
			}
			//关闭连接
			pstmt.close();
			pstmt = null;
			rs.close();
			rs = null;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return 1;
		}
		return 3;
	}
	/**
	 * 删除一个物料表信息
	 * @param ERPNumber ERP编号
	 * @author houhf
	 */
	public static void deleteMLTByNumber(String ERPNumber)
	{

		try
		{

			String sql = "delete from ERPMATERIAL t " +
			 			 "where t.mtlNO = '"+ERPNumber+"'";
			
//			System.out.println("删除物料SQL=====" + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int i = pstmt.executeUpdate();
			if(i>0)
			{
				//提交变更
				//conn.commit();
				//关闭连接
				pstmt.close();
				pstmt = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * 得到所有的ERP BOM 中间表数据。通过sql调用获取数据。
	 * @return hashMap key：parentsNumber values：直接子关联（成品，半成品，原材料）。
	 * @author houhf
	 * @throws Exception 
	 */
	public static HashMap<String, Vector<ERPMiddleBOMLink>> 
		getAllMiddelPartsLinks(Vector<ERPMiddlePart> middelParts) throws Exception
	{
		//返回的结果集
		HashMap<String, Vector<ERPMiddleBOMLink>> resultTable 
							= new HashMap<String, Vector<ERPMiddleBOMLink>>();
		ERPPartReLoad reload = new ERPPartReLoad();
		
	//	partMap = new HashMap();


		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// 建立到数据库的连接
//			conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);
			//通过传进来的Vector查找数据库
			for(int i = 0 ;i < middelParts.size() ;i++)
			{
			
//				long aa = System.currentTimeMillis();
				ERPMiddlePart part = (ERPMiddlePart) middelParts.get(i);
//				获得成品编号
			
				String erpnumber = part.getERPNumber().trim();
				if(partNumberVec.contains(part.getPartNumber())){
					//String str[] = reload.splitPartNumber(erpnumber);
					partnumber = erpnumber;
				}
				
				
				
				
				// 先将成品编号插入到集合中
				//partMap.put( part.getPartNumber().trim(),part.getERPNumber().trim());
				//搜索ERP BOM中间表
				String sql = "select t.fatherMtl, t.mtl, t.groupwareNumber, " +
							 "t.SDDocItemNO, t.sortSequence, t.remark, t.BOMCTG " +
							 "from ERPBOM t " + 
							 "where t.fatherMtl = '" + part.getERPNumber().trim()+
							 "'";
				//System.out.println("查找父件是====" + part.getERPNumber());
				

				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				Vector<ERPMiddleBOMLink> link = new Vector<ERPMiddleBOMLink>();
				while(rs.next())
				{
					ERPMiddleBOMLink temp = new ERPMiddleBOMLink();
				
					temp.setParentNumber
						(rs.getString(1)== null ? "" : rs.getString(1).trim());
					temp.setChildNumber
						(rs.getString(2)== null ? "" : rs.getString(2).trim());
					temp.setDefaultUnit(rs.getInt(3));
					temp.setBomNumber
						(rs.getString(4)== null ? "" : rs.getString(4).trim());
					temp.setSpecNumber
						(rs.getString(5)== null ? "" : rs.getString(5).trim());
					temp.setDesc
						(rs.getString(6)== null ? "" : rs.getString(6).trim());
					temp.setIsMater
						(rs.getString(7)== null ? "" : rs.getString(7).trim());
					
					temp.setPrentPartNumber(partnumber);
					link.add(temp);
					treatedBOMNumber = temp.getParentNumber();
					
					//这里需要做个遍历，将半成品，颜色件，原材料的结构关系也加进去
					//判断是否是半成品，颜色件，原材料如果是则继续遍历，遍历到成品为止
					if(reload.isHalfPart(temp.getChildNumber().trim()) 
						|| reload.isColorPart(temp.getChildNumber().trim())
							|| temp.getIsMater().equalsIgnoreCase("Y"))
					{
						
						//如果结果集中不包含该零件结构信息则进行遍历加入结果集
						if(!resultTable.containsKey(temp.getChildNumber()))
						{
							ERPMiddlePart a = new ERPMiddlePart();
							Vector<ERPMiddlePart> b = new Vector<ERPMiddlePart>();
							a.setERPNumber(temp.getChildNumber());
							b.add(a);
							HashMap<String, Vector<ERPMiddleBOMLink>> c =
								getAllMiddelPartsLinks(b);
							resultTable.putAll(c);
						}
						//System.out.println("resultTable====" + resultTable);
					}
				}
				pstmt.close();
				pstmt = null;				
				rs.close();
				rs = null;
				
				resultTable.put(part.getERPNumber(), link);
//				System.out.println("part.getERPNumber()====" + part.getERPNumber());
//				Vector<ERPMiddleBOMLink> dd =resultTable.get(part.getERPNumber());
//				System.out.println("partnumber====" + dd.elementAt(0).getPrentPartNumber());
			}
		}
		catch (Exception e)
		{
//			String massage = "获取结构出错！！！" + "\n" + "/n";
//			writeLog(massage + e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return resultTable;
	}
	/**
	 * 根据对ERP PART中间表得到的数据从ERP BOM中间表中得到所有的直接子件
	 * （不包括半成品、原材料和颜色件）。
	 * 这意味着这里的数据能够存入PDM BOM表了
	 * @return hashMap中，key：parentsNumber values：直接子关联
	 * @author houhf
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Vector<ERPMiddleBOMLink>> 
				getSubParts(Vector<ERPMiddlePart> middelParts) throws Exception
	{
		ERPPartReLoad reload = new ERPPartReLoad();
		//返回的结果集
		HashMap<String, Vector<ERPMiddleBOMLink>> resultTable = 
							new HashMap<String, Vector<ERPMiddleBOMLink>>();
		//通过传进来的Vector查找数据库
		for(int i = 0 ;i < middelParts.size() ;i++)
		{
			ERPMiddlePart part = (ERPMiddlePart) middelParts.get(i);
			//结果集中的BOM Vector
			Vector<ERPMiddleBOMLink> resultLinkVec 
											= new Vector<ERPMiddleBOMLink>();
			//判断是否有BOM缓存，如果有缓存则直接从缓存里找
			if(originalMiddleBOMLink.size()>0)
			{
				//判断缓存中是否有相应的BOM信息
				if(originalMiddleBOMLink.containsKey(part.getERPNumber()))
				{
					Vector<ERPMiddleBOMLink> linkVec = 
						originalMiddleBOMLink.get(part.getERPNumber());
					
					//遍历BOM，如果子件是零部件则将结构关系保存，如果不是则继续向下遍历
					for(int ii=0;ii<linkVec.size();ii++)
					{
						ERPMiddleBOMLink link = linkVec.get(ii);
						String subNumber = link.getChildNumber();
						//判断是否是半成品，颜色件，原材料如果是则继续遍历
						if(reload.isHalfPart(subNumber.trim()) 
							|| reload.isColorPart(subNumber.trim())
								|| link.getIsMater().equalsIgnoreCase("Y"))
						{
							//这里需要记录数量
							int subCount = link.getDefaultUnit();
							//准备遍历数据
							ERPMiddlePart sub = new ERPMiddlePart();
							sub.setERPNumber(subNumber);
							Vector<ERPMiddlePart> vec 
												= new Vector<ERPMiddlePart>();
							vec.add(sub);
							//开始遍历
							HashMap<String, Vector<ERPMiddleBOMLink>> subMap =
															getSubParts(vec);
							if(subMap.size()>0)
							{
								Iterator<String> iterator = 
													subMap.keySet().iterator();
								while(iterator.hasNext())
								{
									Vector<ERPMiddleBOMLink> subVec = 
												subMap.get(iterator.next());
									//处理父件编号和使用数量
									for(int iii=0;iii<subVec.size();iii++)
									{
										ERPMiddleBOMLink tempLink = 
											(ERPMiddleBOMLink) subVec.get(iii);
										tempLink.setParentNumber(part.getERPNumber());
										int a = subCount * tempLink.getDefaultUnit();
										tempLink.setDefaultUnit(a);
										resultLinkVec.add(tempLink);
									}
								}
							}
						}
						else//不是的话存入结果集
						{
							resultLinkVec.add(link);
						}
					}
				}
				else//缓存中不存在需要的数据则需要查询数据库，这里调用getAllMiddelPartsLinks方法完成查询
				{
					Vector<ERPMiddlePart> partVec = 
												new Vector<ERPMiddlePart>();
					partVec.add(part);
					HashMap<String, Vector<ERPMiddleBOMLink>> subMap = 
											getAllMiddelPartsLinks(partVec);
					Vector<ERPMiddleBOMLink> linkVec = 
									(Vector)subMap.get(part.getERPNumber());
					//遍历BOM，如果子件是零部件则将结构关系保存，如果不是则继续向下遍历
					for(int ii=0;ii<linkVec.size();ii++)
					{
						ERPMiddleBOMLink link = linkVec.get(ii);
						String subNumber = link.getChildNumber();
						//判断是否是半成品，颜色件，原材料如果是则继续遍历
						if(reload.isHalfPart(subNumber.trim()) 
							|| reload.isColorPart(subNumber.trim())
								|| link.getIsMater().equalsIgnoreCase("Y"))
						{
							//这里需要记录数量
							int subCount = link.getDefaultUnit();
							//准备遍历数据
							ERPMiddlePart sub = new ERPMiddlePart();
							sub.setERPNumber(subNumber);
							Vector<ERPMiddlePart> vec 
								= new Vector<ERPMiddlePart>();
							vec.add(sub);
							//开始遍历
							HashMap<String, Vector<ERPMiddleBOMLink>> subMap1 =
																getSubParts(vec);
							Vector subVec = (Vector)subMap1.get(sub.getERPNumber());
							//处理父件编号和使用数量
							for(int iii=0;iii<subVec.size();iii++)
							{
								ERPMiddleBOMLink tempLink = 
											(ERPMiddleBOMLink) subVec.get(iii);
								tempLink.setParentNumber(part.getERPNumber());
								int a = subCount * tempLink.getDefaultUnit();
								tempLink.setDefaultUnit(a);
								resultLinkVec.add(tempLink);
							}
						}
						else//不是的话存入结果集
						{
							resultLinkVec.add(link);
						}
					}
				}
			}
			else//若缓存不存在，则直接查询数据库，这里调用getAllMiddelPartsLinks方法完成查询
			{
				Vector<ERPMiddlePart> partVec = 
					new Vector<ERPMiddlePart>();
				partVec.add(part);
				HashMap<String, Vector<ERPMiddleBOMLink>> subMap = 
					getAllMiddelPartsLinks(partVec);
				Vector<ERPMiddleBOMLink> linkVec = (Vector)subMap.values();
//				遍历BOM，如果子件是零部件则将结构关系保存，如果不是则继续向下遍历
				for(int ii=0;ii<linkVec.size();ii++)
				{
					ERPMiddleBOMLink link = linkVec.get(ii);
					String subNumber = link.getChildNumber();
//					判断是否是半成品，颜色件，原材料如果是则继续遍历
					if(reload.isHalfPart(subNumber.trim()) 
						|| reload.isColorPart(subNumber.trim())
							|| link.getIsMater().equalsIgnoreCase("Y"))
					{
//						这里需要记录数量
						int subCount = link.getDefaultUnit();
//						准备遍历数据
						ERPMiddlePart sub = new ERPMiddlePart();
						sub.setERPNumber(subNumber);
						Vector<ERPMiddlePart> vec 
						= new Vector<ERPMiddlePart>();
						vec.add(sub);
//						开始遍历
						HashMap<String, Vector<ERPMiddleBOMLink>> subMap1 =
															getSubParts(vec);
						Vector subVec = (Vector)subMap1.get(sub);
//						处理父件编号和使用数量
						for(int iii=0;iii<subVec.size();iii++)
						{
							ERPMiddleBOMLink tempLink = 
								(ERPMiddleBOMLink) subVec.get(iii);
							tempLink.setParentNumber(part.getERPNumber());
							int a = subCount * tempLink.getDefaultUnit();
							tempLink.setDefaultUnit(a);
							resultLinkVec.add(tempLink);
						}
					}
					else//不是的话存入结果集
					{
						resultLinkVec.add(link);
					}
				}
			}
			//将本次查询结果存入结果集
			resultTable.put(part.getERPNumber(), resultLinkVec);
		}
		return resultTable;
	}
	/**
	 * 设置零部件为已处理状态
	 * @param partERPNumber
	 * @throws Exception
	 * @author houhf
	 */
	public static void setPartTreated(String partERPNumber) throws Exception
	{
//		//创建数据库连接
//		Connection conn = null;
//		Class.forName("oracle.jdbc.OracleDriver").newInstance();
//		// 建立到数据库的连接
//		conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);

		String sql1 = 
			"update ERPMATERIAL set ISPRETREATE = 1 where mtlNO = '"
			+ partERPNumber + "'";
		PreparedStatement pstmt1 = conn.prepareStatement(sql1);
		int i = pstmt1.executeUpdate();
		if(i > 0)
		{
			//提交变更
			//conn.commit();
			//关闭连接
			pstmt1.close();
			pstmt1 = null;
		}
		else
		{
			new Exception("设置零部件为已处理状态出错！！！");
		}
	}
	/**
	 * 根据编号从ERP BOM 中间表中得到所有的半成品和原材料。
	 * 这个方法查询的是缓存中的结构信息	
	 * @param number 要查找的零部件编号
	 * @return hashMap key：半成品或者原材料的层级；value：半成品或者原材料对应的link
	 * @author houhf
	 */
	private static HashMap<Integer,ERPMiddleBOMLink> getAllHalfParts(String number)
	{
		ERPPartReLoad reload = new ERPPartReLoad();
		//结果集
		HashMap<Integer,ERPMiddleBOMLink> resultMap = 
			new HashMap<Integer, ERPMiddleBOMLink>();
		//通过缓存查找BOM
		Vector<ERPMiddleBOMLink> linkVec = originalMiddleBOMLink.get(number);
//		System.out.println("方法getAllHalfParts----linkVec.size=="+linkVec.size());
		if(linkVec != null && linkVec.size()>0)
		{
			for(int i = 0;i<linkVec.size();i++ )
			{
				ERPMiddleBOMLink link = linkVec.get(i);
				//判断是否是半成品，颜色件，原材料如果是则继续遍历
				if(reload.isHalfPart(link.getChildNumber().trim()) 
					|| reload.isColorPart(link.getChildNumber().trim())
						|| link.getIsMater().equalsIgnoreCase("Y"))
				{
					resultMap.put(1, link);
					resultMap = getAllHalfParts(link.getChildNumber(), resultMap);
				}
			}
		}
		return resultMap;
	}
	/**
	 * 获得零部件制造路线信息
	 * @param part ERP中间表数据
	 * @return 零部件制造路线信息
	 * @author houhf
	 */
	public static String getPartManuRoute(ERPMiddlePart part)
	{
		ERPPartReLoad reload = new ERPPartReLoad();
		//缓存原材料的路线
		String routeString = "";
		HashMap<Integer,ERPMiddleBOMLink> linkMap = 
										getAllHalfParts(part.getERPNumber());
		//标示是否是原材料
		boolean materFlag = false;
//		System.out.println("当前处理路线的件"+part.getERPNumber()+"linkMap.size==="+linkMap.size());
		if(linkMap != null &&linkMap.size()>0)
		{
			for(int i=linkMap.size();i>0;i--)
			{
				ERPMiddleBOMLink link = linkMap.get(i);
//				System.out.println("查找的linkMap子件是"+link.getChildNumber());
				ERPMiddlePart subPart = 
								getERPPartFromNumber(link.getChildNumber());
				if(subPart == null || subPart.getERPNumber() == null
					|| subPart.getERPNumber().length() < 1 
						|| subPart.getERPNumber().equalsIgnoreCase(""))
				{
					continue;
				}
				
				if(subPart != null)
				{
					//如果是颜色件不合并路线
					if(reload.isColorPart(subPart.getERPNumber().trim()))
						continue;
//					System.out.println("subPartERPNumber == "+subPart.getERPNumber() + "-----materFlag===" + materFlag);
					//判断是否是计量单位为”KG“的原材料
					if(subPart.getDefaultUnit() != null 
						&& subPart.getDefaultUnit().length() > 0
							&& subPart.getDefaultUnit().equalsIgnoreCase("KG"))
					{
//						System.out.println("subPartERPNumber == "+subPart.getERPNumber() + "是原材料");
						//按规则处理两级原材料路线
						if(materFlag)
						{
							//连续两级都是原材料，则本级路线不进行合并
							materFlag = false;
							continue;
						}
						materFlag = true;
						if(routeString.equalsIgnoreCase(""))
						{
							routeString = subPart.getManuRoute();
						}
						else
						{
							routeString = routeString + "-" 
													+ subPart.getManuRoute();
						}
						continue;
					}
					
					if(routeString.equalsIgnoreCase(""))
					{
						routeString = subPart.getManuRoute();
					}
					else
					{
						routeString = routeString + "-" + subPart.getManuRoute();
					}
				}
				if(reload.isHalfPart(subPart.getERPNumber()))
				{
					if(subPart.getDefaultUnit() != null 
						&& subPart.getDefaultUnit().length() > 0
							&& !subPart.getDefaultUnit().equalsIgnoreCase("KG"))
					{
						//deleteMLTByNumber(subPart.getERPNumber());
					}
				}
			}
		}
		if(routeString.equalsIgnoreCase(""))
		{
			routeString = part.getManuRoute();
		}
		else
		{
			routeString = routeString + "-" + part.getManuRoute();
		}
		return routeString;
	}
	/**
	 * 通过编号查找ERP零件对象
	 * @param number 零部件编号
	 * @return 零部件对象
	 * @author houhf
	 */
	private static ERPMiddlePart getERPPartFromNumber(String number)
	{
		ERPMiddlePart part = new ERPMiddlePart();
//		//创建数据库连接
//		Connection conn = null;
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// 建立到数据库的连接
//			conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);
			
			//搜索ERP物料中间表
			String sql = "select t.mtlNO, t.MTLTYPE, t.MTLDES, " +
					"t.measureBaseUnit, t.industryStndDes, t.pageFromat, t.isConfFlag," +
					"t.procureType, t.specProcureType, t.REMARK " +
					" from ERPMATERIAL t where t.mtlNO = '" + number + "'";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				part.setERPNumber(rs.getString(1) == null ? "" : rs.getString(1).trim());
				part.setPartType(rs.getString(2) == null ? "" : rs.getString(2).trim());
				part.setPartName(rs.getString(3) == null ? "" : rs.getString(3).trim());
				part.setDefaultUnit(rs.getString(4) == null ? "" : rs.getString(4).trim());
				part.setManuRoute(rs.getString(5) == null ? "" : rs.getString(5).trim());
				part.setAssRoute(rs.getString(6) == null ? "" : rs.getString(6).trim());
				part.setColorFlag(rs.getString(7) == null ? "" : rs.getString(7).trim());
				part.setProducedBy(rs.getString(8) == null ? "" : rs.getString(8).trim());
				part.setDummyPart(rs.getString(9) == null ? "" : rs.getString(9).trim());
				part.setDesc(rs.getString(10) == null ? "" : rs.getString(10).trim());
			}
			//关闭连接
			pstmt.close();
			pstmt = null;
			rs.close();
			rs = null;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return part;
	}
	/**
	 * 根据编号从ERP BOM 中间表中得到所有的半成品和原材料。	
	 * @param number 要查找的零部件编号
	 * @param middelPartslinks 零部件结构表
	 * @return hashMap key：半成品或者原材料的层级；value：半成品或者原材料对应的link
	 * @author houhf
	 */
	private static HashMap<Integer,ERPMiddleBOMLink> getAllHalfParts
			(String number, HashMap<Integer,ERPMiddleBOMLink> middelPartslinks)
	{
		ERPPartReLoad reload = new ERPPartReLoad();

		//结果集
		HashMap<Integer,ERPMiddleBOMLink> resultMap = 
			new HashMap<Integer, ERPMiddleBOMLink>();
		resultMap.putAll(middelPartslinks);
		
		//层级
		int level = middelPartslinks.size();
		//通过缓存查找BOM
		Vector<ERPMiddleBOMLink> linkVec = originalMiddleBOMLink.get(number);
		if(linkVec != null && linkVec.size()>0)
		{
			for(int i = 0;i<linkVec.size();i++ )
			{
				ERPMiddleBOMLink link = linkVec.get(i);
				//判断是否是半成品，颜色件，原材料如果是则继续遍历
				if(reload.isHalfPart(link.getChildNumber().trim()) 
					|| reload.isColorPart(link.getChildNumber().trim())
						|| link.getIsMater().equalsIgnoreCase("Y"))
				{
					resultMap.put(level+1, link);
					resultMap = getAllHalfParts(link.getChildNumber(), resultMap);
				}
			}
		}

		return resultMap;
	}
	/**
     * 获得bsoid
     * @param partInfo 需要发布视图的零件
     * @return partInfo 发布后的零件
     * @throws QMException
     * @author houhf
	 * @throws SQLException 
     */
//    public  static String getUid(String bso)   
//    {
//    	//说明是第一次运行程序
//    	int uid1 = 0;
//    	int uid2 = 0;
//    	int uid3 = 0;
//    	String bsoid = "";
//    
//    	//System.out.println("bs************************************========"+bs);
//    	System.out.println("bso========"+bso);
//    	System.out.println("uid00000000========"+uid);
//    	
//	    	if(uid==0){
//		    	try
//		    		{
//		    		String sql="select count(*) as bbsoid from JFMATERIALSPLIT";
//		    		System.out.println("sql111========"+sql);
//		    		PreparedStatement pstmt = conn.prepareStatement(sql);
//					ResultSet rs = pstmt.executeQuery();
//		    		
//		
//		    		ArrayList AllParts=new ArrayList();
//		    		String[] partData=null;
//		    		while(rs.next())
//					{
//		            	//零件编号
//		    			uid1 = rs.getInt("bbsoid");
//	  	
//			    	}
//		    		//关闭连接
//					pstmt.close();
//					pstmt = null;
//					rs.close();
//					rs = null;
//					
//					 sql="select count(*) as bbsoid from JFMATERIALPARTSTRUCTURE";
//		    		System.out.println("sql222========"+sql);
//		    	    pstmt = conn.prepareStatement(sql);
//					 rs = pstmt.executeQuery();
//		    		
//		
//		    		AllParts=new ArrayList();
//		    		partData=null;
//		    		while(rs.next())
//					{
//		            	//零件编号
//		            	uid2 = rs.getInt("bbsoid");
//	  	
//			    	}
//		    		//关闭连接
//					pstmt.close();
//					pstmt = null;
//					rs.close();
//					rs = null;
//					
//					 sql="select count(*) as bbsoid from JFMATERIALSTRUCTURE";
//			    		System.out.println("sql333========"+sql);
//			    	    pstmt = conn.prepareStatement(sql);
//						 rs = pstmt.executeQuery();
//			    		
//			
//			    		AllParts=new ArrayList();
//			    		partData=null;
//			    		while(rs.next())
//						{
//			            	//零件编号
//			            	uid3 = rs.getInt("bbsoid");
//		  	
//				    	}
//			    		//关闭连接
//						pstmt.close();
//						pstmt = null;
//						rs.close();
//						rs = null;
//				}
//				catch (Exception e) {
//					e.printStackTrace();
//				}
//				uid=uid1+uid2+uid3;
//		       
//		    }
//    		
//	    	
//	    
//	    	
//	  
//    	System.out.println("uid1111111========"+uid);
//    	bsoid = bso + "_" + uid;
//    	uid++;
//    	System.out.println("bsoid========"+bsoid);
//        return bsoid;
//    }
    /**
     * 获得bsoid
     * @param bso 对象类型
     * @return String bsoid
     * @throws QMException
     * @author liuji
	 * @throws SQLException 
     */
    public  static String getUid(String bso)   
    {
    	//说明是第一次运行程序

    	String bsoid = "";
    
    

    	
	    	if(uid==0){
		    	try
		    		{
		    		String sql="select COUNTERID as bbsoid from jferpcounter";
		    		//System.out.println("sql111========"+sql);
		    		PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery();
		    		
		    		while(rs.next())
					{
		            	//零件编号
		    			uid = rs.getInt("bbsoid");
	  	
			    	}
		    		//关闭连接
					pstmt.close();
					pstmt = null;
					rs.close();
					rs = null;
					
					 
				}
				catch (Exception e) {
					e.printStackTrace();
				}
		       
		    }
    		
	    	
	    
	    uid++;
   
    	bsoid = bso + "_" + uid;
    	
    	//System.out.println("bsoid========"+bsoid);
        return bsoid;
    }
    /**
     * 获得bsoid的number
     * @throws QMException
     * @author liuji
     */
    public static void setCounter(){
    	String sql = "update jferpcounter set counterid ='"+ uid + "'";    	
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int i = pstmt.executeUpdate();
			if(i<=0){
				
					//保存失败
					String res = "向PDM中间表PART保存数据出错！";
					
				
			}
			pstmt.close();
			pstmt = null;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    public static void writeLog(String message)
	{
		FileWriter fw;
		try
		{
			if(logfilepath == null ||logfilepath.length() == 0)
			{
				//String path = System.getProperty("java.class.path");
			
				SimpleDateFormat df = 
								new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				//System.out.println("path1111111="+path);
//				path = path.substring(0,path.indexOf(";")) + 
//					   "\\" + logFileName + df.format(new Date()) + ".log";
				//System.out.println("path1111111="+path);
				logfilepath = path;
			}
			fw = new FileWriter(logfilepath, false);
			fw.write(message.toString());
			fw.flush();
		}
		catch (IOException e)
		{
			System.out.println("写日志文件出错！！！");
			e.printStackTrace();
		}
	}
	public static String getPartType(ERPMiddlePart mPart){
		//判断是否是标准件
		String typeString ="零件";
		if(mPart.getPartType()!=null && mPart.getPartType().trim().length()>0)
		{
			if(mPart.getPartType().startsWith("C") ||
					mPart.getPartType().startsWith("CQ") ||
						mPart.getPartType().startsWith("Q")||
							mPart.getPartType().startsWith("T"))
			{
				if(mPart.getManuRoute() != null && 
						mPart.getManuRoute().trim().length()>0)
				{
					if(mPart.getManuRoute().equalsIgnoreCase("协"))
					{
						
						typeString = "标准件";
						
					}
				}
			}
		}
		return typeString;
	}
	public static int getxnj(ERPMiddlePart mPart){
		//判断是否是标准件
		int typeString =0;
		if(mPart.getDummyPart() != null && 
				mPart.getDummyPart().trim().length()>0)
		{
			if(mPart.getDummyPart().equalsIgnoreCase("50") ||
					mPart.getDummyPart().equalsIgnoreCase("51"))
			{
				typeString=1;
			}
		}
		return typeString;
	}
	//对象赋值
	public static HashMap putMap(HashMap map1){
		HashMap map2 = new HashMap();
		
		if(map1.size()>0)
		{
			Iterator<String> iterator = map1.keySet().iterator();
			while(iterator.hasNext())
			{
				Vector vec = new Vector();
				String key=(String)iterator.next();
				Vector<ERPMiddleBOMLink> subVec = (Vector<ERPMiddleBOMLink>) map1.get(key);
			//	System.out.println("key===="+ ((ERPMiddleBOMLink)subVec.elementAt(0)).getPrentPartNumber());
				if(subVec.size()>0){
					for(int i=0;i<subVec.size();i++){
						ERPMiddleBOMLink aa = subVec.elementAt(i);
						vec.add(aa);
					}
				}
			
				map2.put(key, vec);
			}
		}
		System.out.println("map2="+map2);
		return map2;
	}
//	对物料进行拆分，获得其成品编号
	public static String  getsplitPartNumber(String partnumber){
		if(partnumber.endsWith("-M")){
			partnumber=partnumber.substring(0,partnumber.length()-2);
		}
		else if(partnumber.endsWith("-T")){
			partnumber=partnumber.substring(0,partnumber.length()-2);
		}
		else if(partnumber.endsWith("-B")){
			partnumber=partnumber.substring(0,partnumber.length()-2);
		}
		else if(partnumber.endsWith("-B")){
			partnumber=partnumber.substring(0,partnumber.length()-2);
		}
		else if(partnumber.endsWith("-P")){
			partnumber=partnumber.substring(0,partnumber.length()-2);
		}
		else if(partnumber.endsWith("-B1")){
			partnumber=partnumber.substring(0,partnumber.length()-2);
		}
		else if
		(partnumber.endsWith("-H")){
			partnumber=partnumber.substring(0,partnumber.length()-2);
		}
		return partnumber;
	}
	
}
