/**
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  * 
  */
package com.faw_qm.gybom.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import java.util.Vector;

import com.faw_qm.cderp.exception.QMXMLException;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.gybomNotice.model.GYBomAdoptNoticeIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.util.EJBServiceHelper;


/**
 * 工艺BOM管理平台服务辅助类
 * @author 刘家坤   修改时间  2018-1-8
 * @version 1.0
 */
public class GYBomTool
{
	
	/**
	 * 批量修改工艺BOM，根据修改规则集合，把要修改的车型，进行调整 可能造成其他整车结构孤儿件。
	 */


	public HashMap getGYBomChange(GYBomAdoptNoticeIfc ifc,QMPartIfc ypart,String dwbs) throws QMException{
		String partid = ypart.getBsoID();
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		Vector addvec=new Vector();
		Vector deletevec=new Vector();
		HashMap result= new HashMap();
		try {
			conn=PersistUtil.getConnection();
		
		stmt=conn.createStatement();
		
		rs=stmt.executeQuery("select bz2,partid,sl,partnumber,adoptbs from gybomadoptnoticepartlink where noticeid='"+ifc.getBsoID()+"'");
		while(rs.next())
		{
			
		   String adobts = rs.getString(5);
		   if(adobts.equals("采用")){
			   String tempkey=rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+"个"+","+rs.getString(4);
			   String[] aa = tempkey.split(",");
			   addvec.add(aa);
		   }else{
			   String tempkey=rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getString(4);
			   String[] aa = tempkey.split(",");
			   deletevec.add(aa);
		   }
		}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		result.put("add",addvec);
		result.put("delete",deletevec);
		return result;
	}	/**
	 * 批量修改完成之后需要删除批量修改信息。
	 */
	public HashMap deletebatchGYBom(String ypart,String dwbs) throws QMException{
		//String partid = ypart.getBsoID();
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		Vector addvec=new Vector();
		Vector deletevec=new Vector();
		HashMap result= new HashMap();
		try {
			conn=PersistUtil.getConnection();
		
		 stmt=conn.createStatement();
		
		  String  sql="delete from batchupdateCM where yPart='"+ypart+"' and dwbs='" +dwbs+"'";
			stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		result.put("add",addvec);
		result.put("delete",deletevec);
		return result;
	}
	/**
	 * 根据物料号查找零部件信息
	 * @param Vector  物料号集合
	 * @return Vector map集合
	 * @throws QMException
	 */
	public static Vector getPartByMtlVector(Vector vec) throws QMException{
		Vector returnvec = new Vector();
		if(vec!=null&&vec.size()>0){
			for(int i=0;i<vec.size();i++){
				String mtl = (String) vec.elementAt(i);
				HashMap map = getPartByMtlno(mtl);
				if(map!=null)
				returnvec.add(map);
			}
		
		}
		return returnvec;
	}
	/**
	 * 根据物料号查找零部件信息
	 * @param mtlno  物料号
	 * @return HashMap
	 *  mtlno  物料号
	 *  info 零部件值对象
	 *  version 物料版本
	 *  bs 是否找盆 1、找盆 2、不找盆
	 *  error 是否有错误提示 1、有错误 0、没错误
	 *  nopart  没有此零件
	 *  noversion 没有对应盆版本
	 * @throws QMException
	 */
	public static HashMap getPartByMtlno(String mtlno) throws QMException{
		  Connection conn = null;
//		开始拆分编号，拆分出编号和版本
		String[] numberAndVersion = splitPartNumber(mtlno);
		String partnumber = numberAndVersion[0];
		String partVersion = numberAndVersion[1];
		HashMap partMapreturn =new HashMap();//返回值
		//发布源版本
		String sourceVersion = "StringDefinition_7646";
		try
		{
			conn=PersistUtil.getConnection();
//			获取零件号对应的所有版本数据
			String sql = "select t.BSOID, t.VERSIONID ,t.parttypestr from QMPART t " + 
						 "where t.MASTERBSOID = " + 
						 "(select a.BSOID from QMPARTMASTER a "+
						 "where a.PARTNUMBER = '" + partnumber + "'" +
						 " and a.BSOID not like '%(%' and rownum <2) and t.iterationiflatest='1'" +
						 "ORDER BY t.VERSIONVALUE ASC";
			
			//System.out.println("SQL1=====" + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			HashMap partMap =new HashMap();//存放capp版本和中心版本对应
			
			String parttype= "";
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			//获取零件号相应版本对应的发布源，并放入map中
//			System.out.println("mtlno111=====" + mtlno);
			while(rs.next())
				{
					 String partID = 
							rs.getString(1) == null ? "" : rs.getString(1).trim();
					 String cappzgb = 
							rs.getString(2) == null ? "" : rs.getString(2).trim();
					 parttype = rs.getString(3) == null ? "" : rs.getString(3).trim();
		//				System.out.println("partID1111=====" + partID);
						
					 //根据id和版本获取发布源版本
					 String sql1 = "select t.VALUE from STRINGVALUE t " +
					  "where t.DEFINITIONBSOID = '"+sourceVersion+"' "+
					  "and t.ibaholderbsoid = '" + partID + "'";
					   PreparedStatement pstmt1 = conn.prepareStatement(sql1);
						//pstmt1.setFetchSize(10);
						ResultSet rs1 = pstmt1.executeQuery();
						//int bs = 0;
						String fbybb =  "";
						while(rs1.next())
						{
							fbybb =  rs1.getString(1) == null ? "" : rs1.getString(1).trim();
							if(fbybb.indexOf(".")>0)
								fbybb = fbybb.substring(0, fbybb.indexOf("."));
						
							break;
						}
						
						Vector vecbb = new Vector();
						vecbb.add(fbybb);
						vecbb.add(partID);
						partMap.put(cappzgb, vecbb);//零部件bsoid
						
						pstmt1.close();
						pstmt1 = null;
						rs1.close();
						rs1 = null;
				}
//			System.out.println("partMap===="+partMap);
			  if(partMap==null||partMap.size()==0){
				    partMapreturn.put("mtlno", mtlno);
				    partMapreturn.put("info", null);
					partMapreturn.put("version", "");
					partMapreturn.put("bs", "0");
					partMapreturn.put("error", "1");
					partMapreturn.put("nopart", "系统没有此零件"+mtlno+"，请创建该此零件"   );
					partMapreturn.put("noversion", "" );
					return partMapreturn;
			  }
				if(ifNoVersion(mtlno)){
//					System.out.println("11111111111=====");
					if(!ifHasVersion(mtlno,parttype)){
//						System.out.println("222222222222=====");
	//				获取盆里无版本物料对应的版本
					
				      String sql1 = "select t.beforematerial from erppan t " +
					  "where t.aftermaterial = '"+mtlno+"' ";
					   PreparedStatement pstmt1 = conn.prepareStatement(sql1);
						//pstmt1.setFetchSize(10);
						ResultSet rs1 = pstmt1.executeQuery();
						String cappnumber = "";
						String cappVersion = "";
						Vector vecbb = new Vector();
						//System.out.println("sql1==="+sql1);
						while(rs1.next())
						{
							cappnumber =  rs1.getString(1) == null ? "" : rs1.getString(1).trim();
							break;
						}
					
						
						pstmt1.close();
						pstmt1 = null;
						rs1.close();
						rs1 = null;
						//无版本，不再盆里，插入盆，并返回A版本数据
						if(cappnumber==null||cappnumber.equals("")){
//							System.out.println("3333333333333=====");

							//返回零件信息
							//QMPartIfc part=(QMPartIfc)ps.refreshInfo(partid);
							partMapreturn.put("mtlno", mtlno);
							partMapreturn.put("info", null);
							partMapreturn.put("version", "");
							partMapreturn.put("bs", "0");
							partMapreturn.put("error", "1");
							partMapreturn.put("nopart", "");
							partMapreturn.put("noversion", "零件"+mtlno+"无版本并且没有在盆里，请维护盆"   );
							return partMapreturn;
						}else{//无版本在盆里
//							System.out.println("444444444444=====");
							Set newSet=partMap.keySet();
							Iterator newiter=newSet.iterator();
							String newkey="";
							String zxbs = "0";
							String[] numberAndVersion1 = splitPartNumber(cappnumber);
							cappVersion = numberAndVersion1[1];
						//	System.out.println("cappVersion00==="+cappVersion);
							//查找是否有对应的中心版本
							while(newiter.hasNext())
							{
//								System.out.println("5555555555=====");
								newkey=(String)newiter.next();
								vecbb =(Vector)partMap.get(newkey);
								String zxbb = (String) vecbb.get(0);
								String partid = (String) vecbb.get(1);
								if(zxbb!=null){
									if(zxbb.equals(cappVersion)){
//										System.out.println("6666666666666=====");
										QMPartIfc part=(QMPartIfc)ps.refreshInfo(partid);
										partMapreturn.put("mtlno", mtlno);
										partMapreturn.put("info", part);
										partMapreturn.put("version", partVersion);
										partMapreturn.put("bs", "1");
										partMapreturn.put("error", "0");
										partMapreturn.put("nopart", "");
										partMapreturn.put("noversion", "");
										return partMapreturn;
									}
								}
								
								
							}
							//如果没有中心版本，则查找capp版本
							if(partMapreturn.size()==0){
//								System.out.println("77777777777=====");
								vecbb=(Vector) partMap.get(cappVersion);

								String partid = (String) vecbb.get(1);
								QMPartIfc part=(QMPartIfc)ps.refreshInfo(partid);
								partMapreturn.put("mtlno", mtlno);
								partMapreturn.put("info", part);
								partMapreturn.put("version", partVersion);
								partMapreturn.put("bs", "0");
								partMapreturn.put("error", "0");
								partMapreturn.put("nopart", "");
								partMapreturn.put("noversion", "" );
								return partMapreturn;
							}
						}	
					}else{//符合无版本规则，获取A版本零件
//						System.out.println("88888888888888=====");
						Vector vecbb = new Vector();
						vecbb=(Vector) partMap.get("A");

						String partid = (String) vecbb.get(1);
						QMPartIfc part=(QMPartIfc)ps.refreshInfo(partid);
						partMapreturn.put("mtlno", mtlno);
						partMapreturn.put("info", part);
						partMapreturn.put("version", partVersion);
						partMapreturn.put("bs", "0");
						partMapreturn.put("error", "0");
						partMapreturn.put("nopart", "");
						partMapreturn.put("noversion", "" );
						return partMapreturn;
					}
				}
				else{//有版本
					//先查中心版本，中心没有再找capp
//					System.out.println("99999999999999=====");
					Set newSet=partMap.keySet();
					Iterator newiter=newSet.iterator();
					String newkey="";
					Vector vecbb = new Vector();
					//首先查找是否有对应的中心版本
					while(newiter.hasNext())
					{
//						System.out.println("10101010101=====");
						newkey=(String)newiter.next();
						vecbb =(Vector)partMap.get(newkey);
						String zxbb = (String) vecbb.get(0);
						String partid = (String) vecbb.get(1);
						if(zxbb!=null){
							if(zxbb.equals(partVersion)){
//								System.out.println("1212121212=====");
								QMPartIfc part=(QMPartIfc)ps.refreshInfo(partid);
								partMapreturn.put("mtlno", mtlno);
								partMapreturn.put("info", part);
								partMapreturn.put("version", partVersion);
								partMapreturn.put("bs", "0");
								partMapreturn.put("error", "0");
								partMapreturn.put("nopart", "");
								partMapreturn.put("noversion", "" );
								return partMapreturn;
							}
						}

					}
					//如果没有中心版本，则查找capp版本
					if(partMapreturn.size()==0){
//						System.out.println("13131131313=====");
						//获取capp版本
						vecbb=(Vector) partMap.get(partVersion);
//						System.out.println("vecbb==="+vecbb);
//						System.out.println("partMap==="+partMap);
						if(vecbb!=null){
							String zxbb = (String) vecbb.get(0);
							String partid = (String) vecbb.get(1);
							//对应的capp版本还是空的
							if(zxbb.equals("")||zxbb==null){
//								System.out.println("14114141414=====");
								QMPartIfc part=(QMPartIfc)ps.refreshInfo(partid);
								partMapreturn.put("mtlno", mtlno);
								partMapreturn.put("info", part);
								partMapreturn.put("version", partVersion);
								partMapreturn.put("bs", "0");
								partMapreturn.put("error", "0");
								partMapreturn.put("nopart", "");
								partMapreturn.put("noversion", "" );
								return partMapreturn;
							}else{//中心版本不为空
//								System.out.println("1515115115=====");
								   String sql1 = "select t.beforematerial from erppan t " +
									  "where t.aftermaterial = '"+mtlno+"' ";
									   PreparedStatement pstmt1 = conn.prepareStatement(sql1);
										//pstmt1.setFetchSize(10);
										ResultSet rs1 = pstmt1.executeQuery();
										String cappnumber = "";
										String cappVersion = "";
				
										//System.out.println("sql111111==="+sql1);
										while(rs1.next())
										{
											cappnumber =  rs1.getString(1) == null ? "" : rs1.getString(1).trim();
											break;
										}
									
										
										pstmt1.close();
										pstmt1 = null;
										rs1.close();
										rs1 = null;
										//有版本，并且不再盆里，插入盆，并返回版本数据
										if(cappnumber==null||cappnumber.equals("")){
//											插入盆
											String sql3 = "insert into ERPPAN values('" +partnumber + "/"+zxbb + "','"+mtlno+"',zxbb,'','new')";
											PreparedStatement pstmt3 = conn.prepareStatement(sql3);
											//System.out.println("sql3333333333====" + sql3);
											int ii1 = pstmt3.executeUpdate();
											//关闭连接
											pstmt3.close();
											pstmt3 = null;	
										
										}
										QMPartIfc part=(QMPartIfc)ps.refreshInfo(partid);
										partMapreturn.put("mtlno", mtlno);
										partMapreturn.put("info", part);
										partMapreturn.put("version", partVersion);
										partMapreturn.put("bs", "1");
										partMapreturn.put("error", "0");
										partMapreturn.put("nopart", "");
										partMapreturn.put("noversion", "" );
										return partMapreturn;
										
							}
						}
						
					}
					
				}
				
			}
			catch (Exception e)
			{
				e.printStackTrace();
				
			}
			if(partMapreturn.size()==0){
//				System.out.println("161611161616=====");
				partMapreturn.put("mtlno", mtlno);
				partMapreturn.put("info", null);
				partMapreturn.put("version", "");
				partMapreturn.put("bs", "0");
				partMapreturn.put("error", "1");
				partMapreturn.put("nopart", "系统没有此零件"+mtlno+"，请创建该零件"   );
				partMapreturn.put("noversion", "" );
				
			}
			return partMapreturn;
	}
	
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
	 /**
 	 *  获取盆数据
 	 * @param String partNumber
 	 * @param String partType
	 * @throws QMXMLException 
 	 * @throws QMException
 	 */
	public static String getPenNumber(String cappPartNumber) throws QMException{
		Connection conn;
		try {
			   conn = PersistUtil.getConnection();
			   String sql1 = "select t.aftermaterial from erppan t " +
			   "where t.beforematerial = '"+cappPartNumber+"' ";
			   PreparedStatement pstmt1 = conn.prepareStatement(sql1);
				//pstmt1.setFetchSize(10);
				ResultSet rs1 = pstmt1.executeQuery();
				String erpnumber = "";


				//System.out.println("sql111111==="+sql1);
				while(rs1.next())
				{
					erpnumber =  rs1.getString(1) == null ? "" : rs1.getString(1).trim();
					break;
				}
			
				
				pstmt1.close();
				pstmt1 = null;
				rs1.close();
				rs1 = null;
//				有版本，并且不再盆里，插入盆，并返回版本数据
				if(erpnumber==null||erpnumber.equals("")){
					return cappPartNumber;
				}else{
					return erpnumber;
				}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	    
			return cappPartNumber;
	}
}
