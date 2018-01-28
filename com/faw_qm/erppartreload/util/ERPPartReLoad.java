package com.faw_qm.erppartreload.util;
/**
 * 程序ERPPartReLoad.java	1.0  2014/05/20
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */


import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;


import com.faw_qm.cderp.exception.QMXMLException;
import com.faw_qm.erppartreload.ERPMiddleBOMLink;
import com.faw_qm.erppartreload.ERPMiddlePart;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.part.model.QMPartIfc;

/**
 * ERP数据回导预处理类
 * @author houhf
 */
public class ERPPartReLoad {

	//PDM中间表数据库连接
	private static String pdmoid;
	//PDM用户名
	private static String pdmuid;
	//PDM密码
	private static String pdmpwd;
	//log文件的存放路径
//	private static String logfilepath;
	//log文件的存放路径
	private static String logFileName;
	//本次处理数据的数量
	private static String count;
	//缓存当前正在处理的行数
	private static int treatedCount = 0;
	//缓存当前正在处理的零件编号
	private static String treatedNumber;
	//缓存当前正在处理的BOM编号
	private static String treatedBOMNumber;
	//IBA的”发布源版本“ID
	private static String sourceVersion = "StringDefinition_7646";
	//日志路径
	private static String logpath =(String) RemoteProperty.getProperty("erpImportlogPath", "System");
	private static String partID;
	
	//缓存ERP物料表原始数据，这里的数据已经整合成ERPMiddlePart对象
	private  static Vector<ERPMiddlePart> originalMiddlePart;
	//用HashMap的形式缓存ERPMiddlePart对象，便于用ERP编号查找。key-ERP编号，value-ERPMiddlePart对象
	private static HashMap<String, ERPMiddlePart> originalPartTable;
	//缓存ERP BOM表原始数据，这里的数据已经整合成ERPMiddleBOMLink对象,key-父件编号，value-ERPMiddleBOMLink对象
	private static HashMap<String, Vector<ERPMiddleBOMLink>> originalMiddleBOMLink;
	//缓存结构信息，这个是可以进入PDM的
	private static HashMap<String,Vector<ERPMiddleBOMLink>> PDMBOMTable;
//获取盆数据
	private static HashMap<String, String> pen_map;
	//创建数据库连接
	public static Connection conn = null;
	
	//public static String dyversion = "A";
	
	/**
	 * @param args 0-数据库ID 1-用户名 2-密码 3-log存放地址
	 * @author houhf
	 */
	public static void main(String[] args)
	{
		jInit();
	}
	
	/**
	 * 初始化界面
	 * @author houhf
	 */
	private static void jInit()
	{
		
					pdmoid = (String) RemoteProperty.getProperty("pdmoidkunge", "jdbc:oracle:thin:@10.7.2.5:1521:jfpdm1");

					pdmuid = (String) RemoteProperty.getProperty("pdmuidkunge", "jfpdmdb");
				
					pdmpwd =(String) RemoteProperty.getProperty("pdmpwdkunge", "jfpdmdb");
			
					count = (String) RemoteProperty.getProperty("countkunge", "1000");
//	          	pdmoid = (String) RemoteProperty.getProperty("pdmoidkunge", "jdbc:oracle:thin:@10.43.4.123:1521:pdmtest");
//		
//				pdmuid = (String) RemoteProperty.getProperty("pdmuidkunge", "jfpdmdb");
//			
//				pdmpwd =(String) RemoteProperty.getProperty("pdmpwdkunge", "jfpdmdb");
//		
//				count = (String) RemoteProperty.getProperty("countkunge", "1000");
				
					logFileName = "preErp.log";
				System.out.println("pdmoid==="+pdmoid);
				System.out.println("pdmuid==="+pdmuid);
				System.out.println("pdmpwd==="+pdmpwd);
			
				if(startBOM())
				{
					System.out.println("import successs");
				}
				else
				{
					System.out.println("import faliure");
				}
			
	}
	
	/**
	 * 开始执行BOM回倒
	 * @author houhf
	 */
	@SuppressWarnings("unchecked")
	private static boolean startBOM()
	{
		long aaa = System.currentTimeMillis();
		long bbb = System.currentTimeMillis();
		int partsl = 0;
		try
		{
			Class.forName("oracle.jdbc.OracleDriver").newInstance();
			// 建立到数据库的连接
			conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);

			for(int x=0; x<30; x++)
			{ 
				
				 SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
                 String stime =  simple.format(new Date());
                 System.out.println("start times =============mmm"+x+"****data========= "+stime);
				//程序开始首先确定log文件
				//String path = System.getProperty("java.class.path");
				SimpleDateFormat df = 
								new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				//path =  "\\opt\\pdmv4r3\\productfactory\\phosphor\\erpimport2014.log";
			//	path =  "\\pdm\\bea35\\user_projects\\domains\\bin\\erpimport2014.log";
				//logfilepath = path;

//				writeLog(mes);
				//正式开始...
				//初始化参数
				pen_map = new HashMap<String, String>();
				originalPartTable = new HashMap<String, ERPMiddlePart>();
				originalMiddlePart = new Vector<ERPMiddlePart>();
				originalMiddleBOMLink = 
							new HashMap<String, Vector<ERPMiddleBOMLink>>();
				PDMBOMTable = 
							new HashMap<String, Vector<ERPMiddleBOMLink>>();
	
				//获得处理的ERP物料
				originalMiddlePart = (Vector<ERPMiddlePart>) 
									getAllMiddelParts(Integer.parseInt(count));
				 System.out.println("originalMiddlePart=="+originalMiddlePart.size());
				 partsl = partsl + originalMiddlePart.size();
				if(originalMiddlePart == null || originalMiddlePart.size() == 0)
					continue;

				//缓存一下BOM结构
				//暂时屏蔽，只导入物料
				/*
				//originalMiddleBOMLink = getAllMiddelPartsLinks(originalMiddlePart);
				//PDMBOMTable = getSubParts(originalMiddlePart);
				 * */
				 
				//如果存在可进入PDM PART的数据，则进行下一步往PDM中间表里进
				//System.out.println("E originalMiddlePart**********"+originalMiddlePart);
				if(originalMiddlePart.size()>0)
				{
					for(int i=0; i<originalMiddlePart.size(); i++)
					{
						ERPMiddlePart part = originalMiddlePart.get(i);
						
						part.setManuRoute(getPartManuRoute(part));
						saveMiddelPartToOracle(part);
					}
				}
				treatedCount = 0;
				originalMiddlePart.clear();
				originalMiddlePart = null;
				originalPartTable.clear();
				originalPartTable = null;
				originalMiddleBOMLink.clear();
				originalMiddleBOMLink = null;
				System.gc();
				System.out.println
						("single sum time =========" + (System.currentTimeMillis()-bbb));
				// stime =  simple.format(new Date());
                // System.out.println("E time**********"+stime);
				bbb = System.currentTimeMillis();
				//删除掉处理过的BOM信息
				/*Iterator iter = originalMiddleBOMLink.entrySet().iterator();
			while (iter.hasNext())
			{
				Map.Entry entry = (Map.Entry) iter.next();
				Vector<ERPMiddleBOMLink> deleteLinkVec = 
								(Vector<ERPMiddleBOMLink>) entry.getValue();
				for(int i=0;i<deleteLinkVec.size();i++)
				{
					ERPMiddleBOMLink temp = deleteLinkVec.get(i);
					deleteBOMByNumber(temp.getParentNumber(),
														temp.getChildNumber());
				}
			}*/
				//提交变更
				conn.commit();
			}
			System.out.println("共处理零部件======="+partsl);
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			String mes = "\n" + "----------华丽的分隔符----------" + "\n";
			mes += "BOM回导预处理出错！" + "\n" + "处理信息如下：" + "\n";
			mes += "数据库信息:"+"\n"+"IP："+pdmoid+"\n"+"name:"+pdmuid+"\n";
			mes += "本次处理数量:"+count+"\n"+"物料表已经处理的数量:"+treatedCount+"\n";
			mes += "出错期正在处理的零部件是:"+treatedNumber+"\n";;
			mes += "出错期正在处理的BOM是:"+treatedBOMNumber+"\n";
			mes += "抛出的异常是:"+"\n"+e.getMessage();
			writeLog(mes);
			e.printStackTrace();
			return false;
		}
		System.out.println("all time =========" + (System.currentTimeMillis()-aaa));
		return true;
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
//		long a = System.currentTimeMillis();
		//创建数据库连接
//		Connection conn = null;
		//创建返回对象集合
		Collection<ERPMiddlePart> coll = new Vector<ERPMiddlePart>();
		//System.out.println("11111111111111=====" );
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// 建立到数据库的连接
//			conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);
			
			//搜索ERP物料中间表
//			String sql = "select t.mtlNO, t.MTLTYPE, t.MTLDES, " +
//					"t.measureBaseUnit, t.industryStndDes, t.pageFromat, t.isConfFlag, " +
//					"t.procureType, t.specProcureType, t.REMARK" +
//					" from ERPPDMMMRMtlNoMidTable t where t.mtlNO = '2S5000990CSE3'";
			String sql = "select " + "a.mtlNO, a.MTLTYPE, a.MTLDES, a.measureBaseUnit, " +
						 "a.industryStndDes, a.pageFromat, a.isConfFlag, " +
						 "a.procureType, a.specProcureType, a.REMARK,a.Plant,a.penbs" +
			 			 " from " +
			 			 "(select t.* from ERPPDMMMRMtlNoMidTable t " +
			 			 "where t.ISPRETREATE = 0) a where rownum < "+count;
			
		//	System.out.println("SQL=====" + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			//System.out.println("rs=====" + rs.getFetchSize());
			while(rs.next())
			{
				treatedCount++;
				ERPMiddlePart temp = new ERPMiddlePart();
				String plant = "";
				String penbs = "";
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
				plant=rs.getString(11)== null ? "" : rs.getString(11).trim();
				penbs=rs.getString(12)== null ? "" : rs.getString(12).trim();
				
				//System.out.println("ERPnumber=====" + temp.getERPNumber());
				if(temp != null)
				{
					 partID="";
					
					int switch1 = isNeedToTreate(temp,penbs);		
					//System.out.println("switch1========="+switch1);
					switch (switch1) {
					case 0:
						//deleteMLTByNumber(temp.getERPNumber());
						//如果是0代表erp版本和pdm版本一致，这种情况只修改路线
					if(!partID.equals("")){
				
						if(plant.equals("W34")){
							//System.out.println("partID111========="+partID);
							temp.setIsNew(partID);
							break;
						}else{
							//System.out.println("switch122222========="+partID);
							setPartTreated(temp.getERPNumber());
							continue;
						}
						
					}else{
						setPartTreated(temp.getERPNumber());
						continue;
					}
					
					case 1:
						break;
					case 2:
						temp.setIsNew("update");
						break;
					case 3:
						temp.setIsNew("new");
						break;
					}

					treatedNumber = temp.getERPNumber();
					originalPartTable.put(temp.getERPNumber(), temp);
					//System.out.println("originalPartTable========="+originalPartTable);
					//System.out.println("treatedNumber========="+treatedNumber);
					//先判断是否是半成品，颜色件，原材料如果是则不再继续
					if(isHalfPart(temp.getERPNumber().trim()) 
						|| isColorPart(temp.getERPNumber().trim())
							|| temp.getDefaultUnit().equalsIgnoreCase("KG"))
					{
						//System.out.println("半成品是========="+treatedNumber);
						setPartTreated(temp.getERPNumber());
//						if(pstmt!=null){
//							pstmt.close();
//							pstmt = null;
//							rs.close();
//							rs = null;
//						}
						continue;
					}
					
					//如果是零部件则根据具体的规则创建适合PDMPART的ERPMiddlePart对象
					//拆分零件编号版本
					String str[] = splitPartNumber(temp.getERPNumber());
				
					temp.setPartNumber(str[0]);
					temp.setPartVersion(str[1]);
					
					
					
					coll.add(temp);
				}
				//计时
//				a=System.currentTimeMillis()-a;
//				System.out.println("处理完一条数据用时"+a);
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
			String massage = "获取物料出错！！！" + "\n" + "/n";
			writeLog(massage + e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return coll;
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
				//搜索ERP BOM中间表
				String sql = "select t.fatherMtl, t.mtl, t.groupwareNumber, " +
							 "t.SDDocItemNO, t.sortSequence, t.remark, t.BOMCTG " +
							 "from ERPPDMBDMBOMMidTable t " + 
							 "where t.fatherMtl = '" + part.getERPNumber().trim()+
							 "'";
//				System.out.println("查找父件是====" + part.getERPNumber());
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
						
					link.add(temp);
					treatedBOMNumber = temp.getParentNumber();
					//这里需要做个遍历，将半成品，颜色件，原材料的结构关系也加进去
					//判断是否是半成品，颜色件，原材料如果是则继续遍历，遍历到成品为止
					if(isHalfPart(temp.getChildNumber().trim()) 
						|| isColorPart(temp.getChildNumber().trim())
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
					}
				}
				pstmt.close();
				pstmt = null;
				rs.close();
				rs = null;
				resultTable.put(part.getERPNumber(), link);
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
						if(isHalfPart(subNumber.trim()) 
							|| isColorPart(subNumber.trim())
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
						if(isHalfPart(subNumber.trim()) 
							|| isColorPart(subNumber.trim())
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
					if(isHalfPart(subNumber.trim()) 
						|| isColorPart(subNumber.trim())
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

//	/**
//	 * 根据对ERP PART中间表得到的数据从ERP BOM中间表中得到所有的直接子件
//	 * （不包括半成品、原材料和颜色件）。
//	 * 这意味着这里的数据能够存入PDM BOM表了
//	 * @return Vector 直接子关联
//	 * @author houhf
//	 * @throws Exception 
//	 */
//	public static Vector<ERPMiddleBOMLink> 
//				getSubParts(String topNumber,String parentNumber) throws Exception
//	{
//		//结果集中的BOM Vector
//		Vector<ERPMiddleBOMLink> resultLinkVec = new Vector<ERPMiddleBOMLink>();
//		//判断是否有BOM缓存，如果有缓存则直接从缓存里找
//		if(originalMiddleBOMLink.size()>0)
//		{
//			//判断缓存中是否有相应的BOM信息
//			if(originalMiddleBOMLink.containsKey(parentNumber))
//			{
//				Vector<ERPMiddleBOMLink> linkVec = 
//									originalMiddleBOMLink.get(parentNumber);
//				
//				//遍历BOM，如果子件是零部件则将结构关系保存，如果不是则继续向下遍历
//				for(int ii=0;ii<linkVec.size();ii++)
//				{
//					ERPMiddleBOMLink link = linkVec.get(ii);
//					String subNumber = link.getChildNumber();
//					//判断是否是半成品，颜色件，原材料如果是则继续遍历
//					if(isHalfPart(subNumber) || isColorPart(subNumber)
//						|| isMater(subNumber))
//					{
//						//这里需要记录数量
//						int subCount = link.getDefaultUnit();
//
//						//开始遍历
//						Vector<ERPMiddleBOMLink> subMap = 
//											getSubParts(topNumber,subNumber);
//						if(subMap != null && subMap.size()>0)
//						{
//							for(int i=0;i<subMap.size();i++)
//							{
//								ERPMiddleBOMLink subLink = subMap.get(i);
//								//处理父件编号和使用数量
//								int a = subCount * subLink.getDefaultUnit();
//								subLink.setDefaultUnit(a);
//								resultLinkVec.add(subLink);
//							}
//						}
//					}
//					else//不是的话存入结果集
//					{
//						resultLinkVec.add(link);
//					}
//				}
//			}
//			else//缓存中不存在需要的数据则需要查询数据库，这里调用getAllMiddelPartsLinks方法完成查询
//			{
//				Vector<ERPMiddlePart> partVec = 
//											new Vector<ERPMiddlePart>();
//				partVec.add(part);
//				HashMap<String, Vector<ERPMiddleBOMLink>> subMap = 
//										getAllMiddelPartsLinks(partVec);
//				Vector<ERPMiddleBOMLink> linkVec = 
//								(Vector)subMap.get(part.getERPNumber());
//				//遍历BOM，如果子件是零部件则将结构关系保存，如果不是则继续向下遍历
//				for(int ii=0;ii<linkVec.size();ii++)
//				{
//					ERPMiddleBOMLink link = linkVec.get(ii);
//					String subNumber = link.getChildNumber();
//					//判断是否是半成品，颜色件，原材料如果是则继续遍历
//					if(isHalfPart(subNumber) || isColorPart(subNumber)
//							|| isMater(subNumber))
//					{
//						//这里需要记录数量
//						int subCount = link.getDefaultUnit();
//						//准备遍历数据
//						ERPMiddlePart sub = new ERPMiddlePart();
//						sub.setERPNumber(subNumber);
//						Vector<ERPMiddlePart> vec 
//							= new Vector<ERPMiddlePart>();
//						vec.add(sub);
//						//开始遍历
//						HashMap<String, Vector<ERPMiddleBOMLink>> subMap1 =
//							getSubParts(vec);
//						Vector subVec = (Vector)subMap1.get(sub.getERPNumber());
//						//处理父件编号和使用数量
//						for(int iii=0;iii<subVec.size();iii++)
//						{
//							ERPMiddleBOMLink tempLink = 
//								(ERPMiddleBOMLink) subVec.get(iii);
//							tempLink.setParentNumber(part.getERPNumber());
//							int a = subCount * tempLink.getDefaultUnit();
//							tempLink.setDefaultUnit(a);
//							resultLinkVec.add(tempLink);
//						}
//					}
//					else//不是的话存入结果集
//					{
//						resultLinkVec.add(link);
//					}
//				}
//			}
//		}
//		return null;
//	}
				
	/**
	 * 根据编号判断是否半成品
	 * @param number 零部件编号
	 * @return true  是半成品
	 * 	       false 不是半成品
	 * @author houhf
	 */
	public static boolean isHalfPart(String number)
	{
		String enumber = number.replace("—", "-");
		//创建半成品后缀的集合
		String[] halfPartFlag = 
			{"-T","-M","-P","-F","-B","-B1","-HQ","-W","-Q","-QQ","-H",
				"-C","-M(J)","-HH","-B2"};
		if(enumber.indexOf("-")<0)
		{
			return false;
		}
		//将传进来的物料编号后缀拆分出来
		String suffix = 
			enumber.substring(enumber.lastIndexOf("-"), enumber.length());
		if(suffix != null && suffix.length() > 0)
		{
			//对后缀进行判断
			for(int i=0;i<halfPartFlag.length;i++)
			{
				if(suffix.trim().equalsIgnoreCase(halfPartFlag[i]))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据编号判断是否颜色件
	 * @param number
	 * @return true  是颜色件
	 * 	       false 不是颜色件
	 * @author houhf
	 */
	public static boolean isColorPart(String number)
	{
		String enumber = number.replace("—", "-");
		//创建颜色件后缀的集合
		String[] colorFlag = {"A9","AE","AG","AR","B1","B2","C3","CU",
				"DG","DJ","DK","EH","EQ","ER","EW","EX","G9","GC","GR",
				"H1","H3","H4","H6","HG","HH","HJ","HK","HL","HM",
				"M2","MH","MJ","P1","P2","P5","PQ"};
		if(enumber.indexOf("-")<0)
		{
			return false;
		}
		//将传进来的物料编号后缀拆分出来
		String suffix = 
			enumber.substring(enumber.lastIndexOf("-")+1, enumber.length());
		if(suffix != null && suffix.length() > 0)
		{
			//对后缀进行判断
			for(int i=0;i<colorFlag.length;i++)
			{
				if(suffix.trim().equalsIgnoreCase(colorFlag[i]))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据使用单位判断是否原材料
	 * @param partNumber
	 * @return true  是原材料
	 * 	       false 不是原材料
	 * @author houhf
	 */
	/*private static Boolean isMater(String partNumber)
	{
		ERPMiddlePart part = getERPPartFromNumber(partNumber);
		String defaultunit = part.getDefaultUnit();
		if(defaultunit != null && defaultunit.length() > 0
				&& defaultunit.equalsIgnoreCase("KG"))
		{
			return true;
		}
		return false;
	}*/
	
	/**
	 * 根据编号拆分规则将编号拆分成编号和版本。返回值数组中，0对应编号，1对应版本。
	 * 这里必须是经过过滤后的物料编号！
	 * 如果没经过半成品，颜色件，物料过滤，则这里返回的可能是错误的信息！
	 * 规则是最后“/”后是两位以内，并且都是字母的为版本，否则视为编号，版本返回A
	 * @param number
	 * @return String[] 0对应编号，1对应版本
	 * @author houhf
	 */
	public static String[] splitPartNumber(String number)
	{
		//缓存物料编号
		String partNumber = number;
		//用于返回的String数组
		String splitedString[] = new String[2];
		//判断是否有版本，如果有版本则返回物料版本，若没有则返回A
		//System.out.println("partNumber===="+partNumber);
		if(partNumber.indexOf("/") != -1)
		{
			//取出“/”之后的字符串用于拆分版本
			String temp = 
					partNumber.substring(partNumber.lastIndexOf("/")+1).trim();
			
			//如果含有多个“-” 说明含有后缀需要将后缀去掉后进行拆分
			if(countString(temp, "-")>1)
			{
				//将物料号的后缀去除
				temp = temp.substring(0, temp.indexOf("-"));
			}
			
			//如果长度等于2则判断是否是字母，如果不是则视为编号，版本为A
			if(temp.length() == 2)
			{
				temp = temp.toUpperCase();
				if((temp.charAt(0) >= 'A' && temp.charAt(0) <= 'Z') && 
					(temp.charAt(1) >= 'A' && temp.charAt(1) <= 'Z'))
				{
					splitedString[0] = 
						partNumber.substring(0,partNumber.lastIndexOf("/"));
//					System.out.println("1111111");
//					System.out.println("partNumber===="+partNumber);
					splitedString[1] = temp;
					return splitedString;
				}
			}
			
			//如果长度等于1则判断是否是字母，如果不是则视为编号，版本为A
			if(temp.length() ==1)
			{
				temp = temp.toUpperCase();
				if(temp.charAt(0) >= 'A' && temp.charAt(0) <= 'Z')
				{
					splitedString[0] = 
						partNumber.substring(0,partNumber.lastIndexOf("/"));
//					System.out.println("222222222222");
//					System.out.println("partNumber===="+partNumber);
					splitedString[1] = temp;
					return splitedString;
				}
			}
			//长度不是1和2，或者不符合上面规则说明不是版本，不进行拆分
			splitedString[0] = partNumber;
			splitedString[1] = "A";
		}
		else//若不存在版本则整个物料号都为编号
		{
			splitedString[0] = partNumber;
			splitedString[1] = "A";
		}
		return splitedString;
	}
	
	/**
	 * 根据编号拆分规则将编号拆分成编号和版本。返回值数组中，0对应编号，1对应版本。
	 * 这里必须是经过过滤后的物料编号！
	 * 如果没经过半成品，颜色件，物料过滤，则这里返回的可能是错误的信息！
	 * @param number
	 * @return String[] 0对应编号，1对应版本
	 * @author houhf
	 */
	private static String[] splitPartNumber1(String number)
	{
		//缓存物料编号
		String partNumber = number;
		//用于返回的String数组
		String splitedString[] = new String[2];
		//判断是否有版本，如果有版本则返回物料版本，若没有则返回A
		if(partNumber.indexOf("/") != -1)
		{
			//取出“/”之后的字符串用于拆分版本
			String temp = 
					partNumber.substring(partNumber.lastIndexOf("/")+1).trim();
//			System.out.println("temp==="+temp);
//			if(temp.equalsIgnoreCase("D0"))
//			{
//				System.out.println("*********************");
//				System.out.println(temp.lastIndexOf("0")+"--------"+temp.length());
//				System.out.println("**********************");
//			}
			//这里有几种情况，如果长度是1的话只需要判断是否是0，如果长度是2的话则判断是否是AH，BQ
			//如果长度是3的话则判断是否是ZBT。其他情况则需要经过版本规则进行拆分
			if(temp.length() == 3)
			{
				if(temp.equalsIgnoreCase("ZBT"))
				{
					splitedString[0] = partNumber;
					splitedString[1] = "A";
					return splitedString;
				}
			}
			
			if(temp.length() == 2)
			{
				if(temp.equalsIgnoreCase("AH") || temp.equalsIgnoreCase("BQ"))
				{
					splitedString[0] = partNumber;
					splitedString[1] = "A";
					return splitedString;
				}
				//如果拆出的版本要是AZ之类的后面的字母比前面的字母大，也不能算版本
				System.out.println("第一个字母***"+temp.substring(0)+"第二个字母"+temp.substring(1));
				if(temp.substring(0,1)
								.compareToIgnoreCase(temp.substring(1,2))<0)
				{
					splitedString[0] = partNumber;
					splitedString[1] = "A";
					return splitedString;
				}
			}
			
			if(temp.length() == 1)
			{
				if(temp.equalsIgnoreCase("0"))
				{
					splitedString[0] = partNumber;
					splitedString[1] = "A";
					return splitedString;
				}
			}
			
			//这个是以以下字符结尾的情况
			String lastIndex3[] = {"(L)","-SF","-ZC"};
			for(int last3=0;last3<lastIndex3.length;last3++)
			{
				if(temp.lastIndexOf(lastIndex3[last3]) == temp.length()-3
						&&temp.length()>3)
				{
					splitedString[0] = partNumber;
					splitedString[1] = "A";
					return splitedString;
				}
			}
			
			//这个是最后为以下字符的情况
			String lastIndex1[] = {"0","1","2","3","4"};
			for(int last1=0;last1<lastIndex1.length;last1++)
			{
				if(temp.lastIndexOf(lastIndex1[last1]) == temp.length()-1
						&& temp.length()>1)
				{
					splitedString[0] = partNumber;
					splitedString[1] = "A";
					return splitedString;
				}
			}
			
			//最后是包含的关系
			if(temp.length()>4)
			{
				String temp1 = temp.substring(1, temp.length()-1);
				if(temp1.indexOf("L01") != -1)
				{
					splitedString[0] = partNumber;
					splitedString[1] = "A";
					return splitedString;
				}
			}
			if(temp.length()>3)
			{
				String temp1 = temp.substring(1, temp.length()-1);
				if(temp1.indexOf("J0") != -1)
				{
					splitedString[0] = partNumber;
					splitedString[1] = "A";
					return splitedString;
				}
				if(temp1.indexOf("J1") != -1)
				{
					splitedString[0] = partNumber;
					splitedString[1] = "A";
					return splitedString;
				}
			}
			
			//如果含有多个“-” 说明含有后缀需要将后缀去掉后进行拆分
			if(countString(temp, "-")>1)
			{
				//将物料号的后缀去除
				temp = temp.substring(0, temp.indexOf("-"));
			}
			//如果没过滤出来则说明是版本信息，进行拆分
			splitedString[0] = 
				partNumber.substring(0,partNumber.indexOf("/")) + 
				partNumber.substring(partNumber.indexOf("/")+temp.length()+1,partNumber.length());
			splitedString[1] = temp;
			
			//这里还要在进行一次判断排除不符合拆分规则的编号如/AAAAA等。
			if(temp.length()>2)
			{
				splitedString[0] = partNumber;
				splitedString[1] = "A";
				return splitedString;
			}
			
			return splitedString;
		}
		else//若不存在版本则整个物料号都为编号
		{
			splitedString[0] = partNumber;
			splitedString[1] = "A";
		}
		return splitedString;
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
				if(isHalfPart(link.getChildNumber().trim()) 
					|| isColorPart(link.getChildNumber().trim())
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
	 * 根据编号从ERP BOM 中间表中得到所有的半成品和原材料。	
	 * @param number 要查找的零部件编号
	 * @param middelPartslinks 零部件结构表
	 * @return hashMap key：半成品或者原材料的层级；value：半成品或者原材料对应的link
	 * @author houhf
	 */
	private static HashMap<Integer,ERPMiddleBOMLink> getAllHalfParts
			(String number, HashMap<Integer,ERPMiddleBOMLink> middelPartslinks)
	{
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
				if(isHalfPart(link.getChildNumber().trim()) 
					|| isColorPart(link.getChildNumber().trim())
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
	 * 将中间零部件内容（包括直接子件关联）写入到PDM PART 中间表（直接子件关联写入PDM BOM 中间表）。
	 * 如果只在物料中间表中存在，在BOM中间表中不存在，制造路线去物料中间表中的制造路线。
	 * @param mpart
	 * @author houhf
	 */
	public static void saveMiddelPartToOracle(ERPMiddlePart mpart) throws Exception 
	{
		//记录数据库保存结果信息
		String res = "";
		boolean flag = true;

			
		//保存零部件
		String sql = "insert into PDMPART values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1,  mpart.getPartNumber().trim());
		pstmt.setString(2,  mpart.getPartType().trim());
		pstmt.setString(3,  mpart.getPartName().trim());
		pstmt.setString(4,  mpart.getERPNumber().trim());
		pstmt.setString(5,  mpart.getDefaultUnit().trim());
		pstmt.setString(6,  mpart.getPartVersion().trim());
		pstmt.setString(7,  mpart.getManuRoute().trim());
		pstmt.setString(8,  mpart.getAssRoute().trim());
		pstmt.setString(9,  mpart.getColorFlag().trim());
		pstmt.setString(10, mpart.getProducedBy().trim());
		pstmt.setString(11, mpart.getDummyPart().trim());
		pstmt.setString(12, mpart.getDesc().trim());
		pstmt.setInt(13, 0);
		pstmt.setString(14, mpart.getIsNew().trim());
		int i = pstmt.executeUpdate();
		if (i > 0)
		{
			//保存成功
			//在ERP表中打标记
			String sql1 = "update ERPPDMMMRMtlNoMidTable set ISPRETREATE = 1 where mtlNO = '" +
						  mpart.getERPNumber() + "'";
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
//			System.out.println("sql1====" + sql1);
			int ii = pstmt1.executeUpdate();
			//关闭连接
			pstmt1.close();
			pstmt1 = null;
			//刘家坤，测试屏蔽bom
			ii=0;
			if(ii>0)
			{
				//CCBegin SS1
				String istreated = mpart.getIsNew();
//				if(istreated.equals("updateRoute"))
//					return;
				//CCEnd SS1
				//零件保存成功之后开始保存BOM信息
				Vector<ERPMiddleBOMLink> link = PDMBOMTable.get(mpart.getERPNumber());
				
				for(int a=0;a<link.size();a++)
				{
					ERPMiddleBOMLink BOMInfo = link.get(a);
					String sql2 = "insert into PDMBOM values(?,?,?,?,?,?,?)";

					PreparedStatement pstmt2 = conn.prepareStatement(sql2);

					pstmt2.setString(1,  BOMInfo.getParentNumber());
					pstmt2.setString(2,  BOMInfo.getChildNumber());
					pstmt2.setInt(3,  BOMInfo.getDefaultUnit());
					pstmt2.setString(4,  BOMInfo.getBomNumber());
					pstmt2.setString(5,  BOMInfo.getSpecNumber());
					pstmt2.setString(6,  BOMInfo.getDesc());
					pstmt2.setInt(7, 0);

					int iii = pstmt2.executeUpdate();
					//关闭连接
					pstmt2.close();
					pstmt2 = null;
					if(iii>0)
					{
						continue;
					}
					else
					{
						res = "向PDM中间表BOM保存数据出错！";
						flag = true;
						break;
					}
				}
				
			}
			else
			{
				res = "向ERP中间表更新标记出错！";
				flag = true;
			}
		}
		else
		{
			//保存失败
			res = "向PDM中间表PART保存数据出错！";
			flag = true;
		}
//		if(flag)
//		{
			//提交变更
			//conn.commit();
			//关闭连接
			pstmt.close();
			pstmt = null;
		//}
//		else
//		{
//			throw(new Exception(res));
//		}
	}
	
	/**
	 * 在指定的目录下生成LOG文件。
	 * @author houhf
	 */
	private static void writeLog(String message)
	{
		FileWriter fw;
		try
		{
			if(logpath == null ||logpath.length() == 0) 
			{
				String path = System.getProperty("java.class.path");
				SimpleDateFormat df = 
								new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//				path =  "\\pdm\\bea35\\user_projects\\domains\\bin\\erpimport2014.log";
//				logpath = path;
			}
			fw = new FileWriter(logpath, false);
			fw.write(message.toString());
			fw.flush();
		}
		catch (IOException e)
		{
			System.out.println("写日志文件出错！！！");
			e.printStackTrace();
		}
	}

	/**
	 * 查找某个字符在字符串里出现了几次
	 * @param source 被查找的字符串
	 * @param target 需要计算出现次数的字符
	 * @return 出现的数量，如果是0则没出现过
	 * @author houhf
	 */
	private static int countString(String source,String target)
	{
		int count = 0;
		//要查找的字符串
		String str =source;
		//如果字符串中有target
		while( str.indexOf(target)!=-1){
			count++;
			//将字符串出现的位置之前的全部去掉
			if(str != null && str.length()>1)
			{
				str = str.substring(str.indexOf(target)+1);
			}
			else
				break;
		}
		return count;
	}
	
	/**
	 * 获得零部件制造路线信息
	 * @param part ERP中间表数据
	 * @return 零部件制造路线信息
	 * @author houhf
	 */
	public static String getPartManuRoute(ERPMiddlePart part)
	{
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
					if(isColorPart(subPart.getERPNumber().trim()))
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
				if(isHalfPart(subPart.getERPNumber()))
				{
					if(subPart.getDefaultUnit() != null 
						&& subPart.getDefaultUnit().length() > 0
							&& !subPart.getDefaultUnit().equalsIgnoreCase("KG"))
					{
						deleteMLTByNumber(subPart.getERPNumber());
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
					" from ERPPDMMMRMtlNoMidTable t where t.mtlNO = '" + number + "'";
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
			"update ERPPDMMMRMtlNoMidTable set ISPRETREATE = 1 where mtlNO = '"
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
	 * 联合PDM检查零部件是否需要导入
	 * @param ERPNumber
	 * @return 0 不需要 1 需要 2 更新 3 新建 
	 * @author houhf
	 */
	public static int isNeedToTreate(ERPMiddlePart part,String penbs)
	{
		//System.out.println("isNeedToTreate start=");
		//先判断是否是半成品，颜色件，原材料如果是则不再继续
		if(isHalfPart(part.getERPNumber().trim())
			||isColorPart(part.getERPNumber().trim())
				||part.getDefaultUnit().equalsIgnoreCase("KG"))
		{
			return 1;
		}
		String partName = part.getPartName();
		//名称中带（采购路线）和（库存基线）不回导
		if(partName.indexOf("采购路线")>=0||partName.indexOf("库存基线")>=0)
			return 0;
		//数据回导、非卡车厂路线零件不进行导入
		//开始拆分编号，拆分出编号和版本
		String[] numberAndVersion = splitPartNumber(part.getERPNumber());
		//String penbs = part.getPenbs();
		 String erpNumber = part.getERPNumber();
		//System.out.println("part.getERPNumber()===" + part.getERPNumber());
		String dyversion = "A";
//		//创建数据库连接
//		Connection conn = null;
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// 建立到数据库的连接
//			conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);
			
			//获得所有大版本信息
			String sql = "select t.BSOID, t.VERSIONID ,t.parttypestr from QMPART t " + 
						 "where t.MASTERBSOID = " + 
						 "(select a.BSOID from QMPARTMASTER a "+
						 "where a.PARTNUMBER = '" + numberAndVersion[0] + "'" +
						 " and a.BSOID not like '%(%' and rownum <2)" +
						 "ORDER BY t.VERSIONVALUE ASC";
			
			//System.out.println("SQL1=====" + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			HashMap partMap =new HashMap();//存放capp版本和中心版本对应
			String cappzgb = ""; //capp 最高版本
			String fbybb = "";//发布源最高版本
			String parttype= "";
	
			while(rs.next())
			{
				 partID = 
						rs.getString(1) == null ? "" : rs.getString(1).trim();
				 cappzgb = 
						rs.getString(2) == null ? "" : rs.getString(2).trim();
				 parttype = rs.getString(3) == null ? "" : rs.getString(3).trim();
//					System.out.println("partID1111=====" + partID);
//					System.out.println("cappzgb111=====" + cappzgb);
				 //根据id和版本获取版本
				 String sql1 = "select t.VALUE from STRINGVALUE t " +
				  "where t.DEFINITIONBSOID = '"+sourceVersion+"' "+
				  "and t.ibaholderbsoid = '" + partID + "'";
				   PreparedStatement pstmt1 = conn.prepareStatement(sql1);
					//pstmt1.setFetchSize(10);
					ResultSet rs1 = pstmt1.executeQuery();
					//int bs = 0;
					while(rs1.next())
					{
						fbybb =  rs1.getString(1) == null ? "" : rs1.getString(1).trim();
						if(fbybb.indexOf(".")>0)
							fbybb = fbybb.substring(0, fbybb.indexOf("."));
						partMap.put(cappzgb, fbybb);
						break;
					}
					
					
					pstmt1.close();
					pstmt1 = null;
					rs1.close();
					rs1 = null;
				
			}
//			System.out.println("cappzgb==="+cappzgb);
//			System.out.println("fbybb==="+fbybb);
			
			String bybb = "";
//			需要比较的版本
			if(fbybb.equals("")){//发布源是空直接获取capp最高版
				bybb=cappzgb;
			}else{//发布源不为空，则获取发布源版本，并且和capp版本比较，获取比较最大值
				
				int result =cappzgb.compareTo(fbybb);
				if(result>0)
					bybb=cappzgb;
				else
					bybb=fbybb;
			}
//			System.out.println("bybb==="+bybb);
//			System.out.println("penbs==="+penbs);
//			System.out.println("ifNoVersion(part.getERPNumber())==="+ifNoVersion(part.getERPNumber()));
			//如果在盆里，
			if(penbs.equals("1")){
				//并且是无版本的物料
				if(ifNoVersion(erpNumber)){
//					获取盆里无版本物料对应的版本
					
				    String sql1 = "select t.ywversion from erppan t " +
					  "where t.aftermaterial = '"+erpNumber+"' ";
					   PreparedStatement pstmt1 = conn.prepareStatement(sql1);
						//pstmt1.setFetchSize(10);
						ResultSet rs1 = pstmt1.executeQuery();
						//System.out.println("sql1==="+sql1);
						while(rs1.next())
						{
							dyversion =  rs1.getString(1) == null ? "" : rs1.getString(1).trim();
							break;
						}
						//System.out.println("dyversion000==="+dyversion);
						
						pstmt1.close();
						pstmt1 = null;
						rs1.close();
						rs1 = null;
					//	--------------------获取盆版本结束
					//有发布源版本，
					if(fbybb!=null&&!fbybb.equals("")){
						Set newSet=partMap.keySet();
						Iterator newiter=newSet.iterator();
						String newkey="";
						String zxbs = "0";
						//System.out.println("fbybb11111111111==="+fbybb);
						//查找是否有对应的中心版本
						while(newiter.hasNext())
						{
							newkey=(String)newiter.next();// 旧集合不含有新集合内容，表示新集合新增。
							{
								String zxversion=(String)partMap.get(newkey);
								if(zxversion.indexOf(".")>0)
									zxversion = 
										zxversion.substring(0, zxversion.indexOf("."));
								if(zxversion.equals(dyversion)){
									zxbs="1";
									continue;
								}
									
							}
						}
					//	System.out.println("zxbs====" + zxbs);
						//如果没有对应的中心版本，则查找零部件版本对应的中心版本
						String zxversion = (String) partMap.get(dyversion);
						if(zxversion!=null&&zxbs.equals("0")){
							//如果capp版本有对应的发布源版本，并且发布源版本不是 erp回导的版本则反写盆
							  String sql2 = "update ERPPAN set BEFOREMATERIAL = '" +
							   erpNumber + "/"+zxversion + "',isnew ='"+"update"+"' where Aftermaterial='"+erpNumber+"'";
								PreparedStatement pstmt2 = conn.prepareStatement(sql2);
								//System.out.println("sql2====" + sql2);
								int ii = pstmt2.executeUpdate();
								//关闭连接
								pstmt2.close();
								pstmt2 = null;	
								}
					}
				}
				
				
			}else{//不再盆里
				//1、无版本
				//System.out.println("in2222222222=====1");
				if(ifNoVersion(erpNumber)){
					//判断零件号是否是属于capp接口无版本发布规则
					// System.out.println("ifHasVersion(erpNumber,parttype)=="+ifHasVersion(erpNumber,parttype));
					if(!ifHasVersion(erpNumber,parttype)){
//						System.out.println("partMap.size()==="+partMap.size());
						if(partMap.size()>1){
							Set newSet=partMap.keySet();
							Iterator newiter=newSet.iterator();
							String newkey="";
							while(newiter.hasNext())
							{
//								将无版本并且不再盆里的数据，进行记录
								newkey=(String)newiter.next();// 旧集合不含有新集合内容，表示新集合新增。
								{
									//String zxversion=(String)partMap.get(newkey);
									String sql2 = "insert into ERPVERDIFFERENT  values('" +
								    erpNumber + "/"+newkey + "' , '"+erpNumber+"','','','')";
									PreparedStatement pstmt2 = conn.prepareStatement(sql2);
									//System.out.println("sql222222222222====" + sql2);
									int ii = pstmt2.executeUpdate();
									//关闭连接
									pstmt2.close();
									pstmt2 = null;	
								
										
								}
								
							
							}  
//							将记录到盆,只记录A版本对应关系
							String sql3 = "insert into ERPPAN values('" +numberAndVersion[0] + "/"+"A" + "','"+numberAndVersion[0]+"','A','','new')";
							PreparedStatement pstmt3 = conn.prepareStatement(sql3);
							//System.out.println("sql3333333333====" + sql3);
							int ii1 = pstmt3.executeUpdate();
							//关闭连接
							pstmt3.close();
							pstmt3 = null;	
						}
						if(partMap.size()==1){
						//	System.out.println("进来了====partMap.size()==1");
							//将无版本并且不再盆里的数据，进行记录
							Set newSet=partMap.keySet();
							Iterator newiter=newSet.iterator();
							String cappbb="";
							
							while(newiter.hasNext())
							{
								cappbb=(String)newiter.next();
								//String zxversion=(String)partMap.get(cappbb);
								String sql2 = "insert into ERPVERDIFFERENT  values('" +
							    erpNumber + "/"+cappbb + "' , '"+erpNumber+"','','','')";
								PreparedStatement pstmt2 = conn.prepareStatement(sql2);
								//System.out.println("sql222222222222====" + sql2);
								int ii = pstmt2.executeUpdate();
								//关闭连接
								pstmt2.close();
								pstmt2 = null;	
								//将记录到盆
								String sql3 = "insert into ERPPAN values('" +numberAndVersion[0] + "/"+cappbb + "','"+numberAndVersion[0]+"','"+cappbb+"','','new')";
								PreparedStatement pstmt3 = conn.prepareStatement(sql3);
								//System.out.println("sql3333333333====" + sql3);
								int ii1 = pstmt3.executeUpdate();
								//关闭连接
								pstmt3.close();
								pstmt3 = null;	
							}
						}
					}
					
					
				}else{
//					有版本
//					有发布源版本，如果没有发布原版吧就不需要在查找了，因为不存在版本不对应情况
					if(fbybb!=null&&!fbybb.equals("")){
						Set newSet=partMap.keySet();
						Iterator newiter=newSet.iterator();
						String newkey="";
						String zxbs = "0";
						//System.out.println("fbybb==="+fbybb);
						//查找是否有对应的中心版本
						while(newiter.hasNext())
						{
							newkey=(String)newiter.next();// 旧集合不含有新集合内容，表示新集合新增。
							{
								String zxversion=(String)partMap.get(newkey);
								if(zxversion.indexOf(".")>0)
									zxversion = 
										zxversion.substring(0, zxversion.indexOf("."));
								if(zxversion.equals(numberAndVersion[1])){//查看有没有符合条件的中心版本
									zxbs="1";
									continue;
								}
									
							}
						}
						//如果没有对应的中心版本，则把版本当作capp版本再查找零部件版本对应的中心版本
//						System.out.println("numberAndVersion[1]====" + numberAndVersion[1]);
//						System.out.println("partMap====" + partMap);
						String zxversion = (String) partMap.get(numberAndVersion[1]);
//						System.out.println("zxversion====" + zxversion);
//						System.out.println("zxbs====" + zxbs);
						if(zxversion!=null&&zxbs.equals("0")){
							//如果capp版本有对应的发布源版本，并且发布源版本不是 erp回导的版本则反写盆
							String sql3 = "insert into ERPPAN values('" +numberAndVersion[0] + "/"+zxversion + "','"+erpNumber+"','"+zxversion+"','','new')";
								PreparedStatement pstmt2 = conn.prepareStatement(sql3);
							//	System.out.println("sql3====" + sql3);
								int ii = pstmt2.executeUpdate();
								//关闭连接
								pstmt2.close();
								pstmt2 = null;	
						  }
				     }
					
				}

			}
			
			
	
             //bybb是用于比较的版本
		//	System.out.println("partID====" + partID);
            if(partID!=null&&partID!=""){
            	if(cappzgb.indexOf(".")>0)
    				cappzgb = 
    					cappzgb.substring(0, cappzgb.indexOf("."));
                    
     
    				int result =0;
    				if(numberAndVersion[1] != null 
    					&& numberAndVersion[1].trim().length()>0)
    				{
    					result = numberAndVersion[1].compareTo(cappzgb);
    				}
//    				System.out.println("cappzgb====" + cappzgb);
//    				System.out.println("numberAndVersion[1]====" + numberAndVersion[1]);
//    				System.out.println("result====" + result);
    				if(result>0)//ERP版本大于PDM版本
    					return 2;
    				if(result==0)//ERP版本等于PDM版本
    					return 0;
    				if(result<0)//ERP版本小于PDM版本,不作操作
    					return 0;
            }
			
			
		
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
//		//创建数据库连接
//		Connection conn = null;
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// 建立到数据库的连接
//			conn = DriverManager
//					.getConnection(pdmoid, pdmuid, pdmpwd);
			String sql = "delete from ERPPDMMMRMtlNoMidTable t " +
			 			 "where t.mtlNO = '"+ERPNumber+"'";
			
			//System.out.println("删除物料SQL=====" + sql);
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
	 * 删除一个物料表信息
	 * @param parentERPNumber 父件ERP编号
	 * @param childERPNumber 子件ERP编号
	 * @author houhf
	 */
	public static void deleteBOMByNumber(String parentERPNumber, String childERPNumber)
	{
//		//创建数据库连接
//		Connection conn = null;
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// 建立到数据库的连接
//			conn = DriverManager
//					.getConnection(pdmoid, pdmuid, pdmpwd);
			
			String sql = "delete from ERPPDMBDMBOMMidTable t " +
			 			 "where t.fatherMtl = '"+parentERPNumber+"'" + 
			 			 " and t.mtl = '" + childERPNumber + "'";
			
//			System.out.println("删除BOMSQL=====" + sql);
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
	 * 得到所有的ERP PART 中间表数据。不包括半成品，颜色件，原材料。通过sql调用获取数据。
	 * 现在不通过sql获取了，直接读缓存的数据。
	 * @return 返回ERPMiddlePart的集合，这里的对象时经过过滤能够保存到PDMPART的。
	 * @author houhf
	 * @throws Exception 
	 */
	public static HashMap getAllPenParts()	throws Exception
	{

		//创建返回对象集合
		HashMap MAP = new HashMap();
		//System.out.println("11111111111111=====" );
		try
		{


			String sql = "select " + "a.aftermaterial, a.ywversion" +
			 			 " from erppen";
			
			//System.out.println("SQL=====" + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			//System.out.println("rs=====" + rs.getFetchSize());
			while(rs.next())
			{
			
				String aftermaterial=
					(rs.getString(1) == null ? "" : rs.getString(1).trim());
				String ywversion=
					(rs.getString(2)== null ? "" : rs.getString(2).trim());
	            
				MAP.put(aftermaterial, ywversion);
				
			
			}
			//关闭连接
			
				pstmt.close();
				pstmt = null;
				rs.close();
				rs = null;
			
		
		}
		catch (Exception e)
		{
			String massage = "获取盆数据出错！！！" + "\n" + "/n";
			writeLog(massage + e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return MAP;
	}
	/**
	 * 判断是否是无版本的物料
	 * @param number
	 * @return String[] 0对应编号，1对应版本
	 * @author houhf
	 */
	public static boolean ifNoVersion(String number)
	{
		//缓存物料编号
		String partNumber = number;
		//用于返回的String数组
		String splitedString[] = new String[2];
		//判断是否有版本，如果有版本则返回物料版本，若没有则返回A
		//System.out.println("partNumber===="+partNumber);
		if(partNumber.indexOf("/") != -1)
		{
			//取出“/”之后的字符串用于拆分版本
			String temp = 
					partNumber.substring(partNumber.lastIndexOf("/")+1).trim();
			
			//如果含有多个“-” 说明含有后缀需要将后缀去掉后进行拆分
			if(countString(temp, "-")>1)
			{
				//将物料号的后缀去除
				temp = temp.substring(0, temp.indexOf("-"));
			}
			
			//如果长度等于2则判断是否是字母，如果不是则视为编号，版本为A
			if(temp.length() == 2)
			{
				temp = temp.toUpperCase();
				if((temp.charAt(0) >= 'A' && temp.charAt(0) <= 'Z') && 
					(temp.charAt(1) >= 'A' && temp.charAt(1) <= 'Z'))
				{
					splitedString[0] = 
						partNumber.substring(0,partNumber.lastIndexOf("/"));
//					System.out.println("1111111");
//					System.out.println("partNumber===="+partNumber);
					splitedString[1] = temp;
					return false;
				}
			}
			
			//如果长度等于1则判断是否是字母，如果不是则视为编号，版本为A
			if(temp.length() ==1)
			{
				temp = temp.toUpperCase();
				if(temp.charAt(0) >= 'A' && temp.charAt(0) <= 'Z')
				{

					return false;
				}
			}
			//长度不是1和2，或者不符合上面规则说明不是版本，不进行拆分
			return true;
		}
		else//若不存在版本则整个物料号都为编号
		{
	
			return true;
		}
		
	}
	  /**
 	 *  检查编号是否带版本
 	 * @param String partNumber
 	 * @param String partType
	 * @throws QMXMLException 
 	 * @throws QMException
 	 */
	public static boolean ifHasVersion(String partNumber ,String partType) throws QMException{
       // String partNumber = partIfc.getPartNumber()
      //  String partType = partIfc.getPartType().getDisplay().toString();
      //  String materialNumber="";
//       System.out.println("partNumber=="+partNumber);
//       System.out.println("partType=="+partType);
        //?	如果零部件类型属性为标准件，则物料号不加版本，规则：零部件号+路线单位简称
        //?	如果“零部件类型”属性为车型，则物料号不加版本，规则：零部件号+路线单位简称
        if((partNumber.startsWith("CQ")) || (partNumber.startsWith("Q")) || (partNumber.startsWith("T"))){
        	
        	return true;
        }
        //驾驶室零件号包含“5000990”、 发动机零件号包含“1000940”，则物料号不加版本，物料号不加版本，规则：零部件号+路线单位简称
        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0||partNumber.indexOf("5000020")>=0||partNumber.indexOf("5000030")>=0){
        	return true;
        }
        //原材料不带版本
        if(partNumber.indexOf("*")>=0||partType.equals("Assembly")){

        	return true;
        }
//      判断零件是否是车型
        long lenNumber = partNumber.length();
        if(partNumber.startsWith("C")&&partType.equals("Model")&&lenNumber==15){
        	return true;
	    }
        //零部件编号第一个“/”后为“*L01*”、“0”、“*0（1，2，3，4）”、、“ZBT”、“*(L)”、“AH”、“*J0*”、
        //“*J1*”、“*-SF”、“BQ”和“*-ZC”的不加版本，规则：零部件号+路线单位简称 。
        if(partNumber.indexOf("/")>=0){
        	
        	int a = partNumber.indexOf("/");
        	//System.out.println("a="+a);
        	String temp = partNumber.substring(a+1);
        	//System.out.println("temp="+temp);
        	//完全匹配型a
        	String[] array1 = {"0","ZBT","AH","BQ"};
        	//在中间型×a×
        	String[] array2 = {"L01","J0","J1"};
        	//在结尾a×
        	String[] array3 = {"0","1","2","3","4","(L)","-SF","-ZC"};
        	//完全匹配型a
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
//        		System.out.println("temp==="+temp);
//        		System.out.println("str==="+str);
        		if(str.equals(temp)){
        			return true;
        		}
        	}
        	//在中间型×a×
        	for (int i1 = 0; i1 < array2.length; i1++){
        	//	System.out.println("55555555555555==");
        		String str = array2[i1];
        		if(temp.indexOf(str)>=0){
    
        			return true;
        		}
        	}
        	//在结尾a×
        	for (int i1 = 0; i1 < array3.length; i1++){
        		
        		String str = array3[i1];
        		if(temp.endsWith(str)){
        			return true;
        		}
        	}
        }
       

	return false;
	}
}
