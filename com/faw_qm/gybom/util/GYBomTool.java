/**
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
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
 * ����BOM����ƽ̨��������
 * @author ������   �޸�ʱ��  2018-1-8
 * @version 1.0
 */
public class GYBomTool
{
	
	/**
	 * �����޸Ĺ���BOM�������޸Ĺ��򼯺ϣ���Ҫ�޸ĵĳ��ͣ����е��� ����������������ṹ�¶�����
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
		   if(adobts.equals("����")){
			   String tempkey=rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+"��"+","+rs.getString(4);
			   String[] aa = tempkey.split(",");
			   addvec.add(aa);
		   }else{
			   String tempkey=rs.getString(1)+","+rs.getString(2)+","+rs.getString(3)+","+rs.getString(4);
			   String[] aa = tempkey.split(",");
			   deletevec.add(aa);
		   }
		}
		} catch (SQLException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
		result.put("add",addvec);
		result.put("delete",deletevec);
		return result;
	}	/**
	 * �����޸����֮����Ҫɾ�������޸���Ϣ��
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
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
		result.put("add",addvec);
		result.put("delete",deletevec);
		return result;
	}
	/**
	 * �������ϺŲ����㲿����Ϣ
	 * @param Vector  ���Ϻż���
	 * @return Vector map����
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
	 * �������ϺŲ����㲿����Ϣ
	 * @param mtlno  ���Ϻ�
	 * @return HashMap
	 *  mtlno  ���Ϻ�
	 *  info �㲿��ֵ����
	 *  version ���ϰ汾
	 *  bs �Ƿ����� 1������ 2��������
	 *  error �Ƿ��д�����ʾ 1���д��� 0��û����
	 *  nopart  û�д����
	 *  noversion û�ж�Ӧ��汾
	 * @throws QMException
	 */
	public static HashMap getPartByMtlno(String mtlno) throws QMException{
		  Connection conn = null;
//		��ʼ��ֱ�ţ���ֳ���źͰ汾
		String[] numberAndVersion = splitPartNumber(mtlno);
		String partnumber = numberAndVersion[0];
		String partVersion = numberAndVersion[1];
		HashMap partMapreturn =new HashMap();//����ֵ
		//����Դ�汾
		String sourceVersion = "StringDefinition_7646";
		try
		{
			conn=PersistUtil.getConnection();
//			��ȡ����Ŷ�Ӧ�����а汾����
			String sql = "select t.BSOID, t.VERSIONID ,t.parttypestr from QMPART t " + 
						 "where t.MASTERBSOID = " + 
						 "(select a.BSOID from QMPARTMASTER a "+
						 "where a.PARTNUMBER = '" + partnumber + "'" +
						 " and a.BSOID not like '%(%' and rownum <2) and t.iterationiflatest='1'" +
						 "ORDER BY t.VERSIONVALUE ASC";
			
			//System.out.println("SQL1=====" + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			HashMap partMap =new HashMap();//���capp�汾�����İ汾��Ӧ
			
			String parttype= "";
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			//��ȡ�������Ӧ�汾��Ӧ�ķ���Դ��������map��
//			System.out.println("mtlno111=====" + mtlno);
			while(rs.next())
				{
					 String partID = 
							rs.getString(1) == null ? "" : rs.getString(1).trim();
					 String cappzgb = 
							rs.getString(2) == null ? "" : rs.getString(2).trim();
					 parttype = rs.getString(3) == null ? "" : rs.getString(3).trim();
		//				System.out.println("partID1111=====" + partID);
						
					 //����id�Ͱ汾��ȡ����Դ�汾
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
						partMap.put(cappzgb, vecbb);//�㲿��bsoid
						
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
					partMapreturn.put("nopart", "ϵͳû�д����"+mtlno+"���봴���ô����"   );
					partMapreturn.put("noversion", "" );
					return partMapreturn;
			  }
				if(ifNoVersion(mtlno)){
//					System.out.println("11111111111=====");
					if(!ifHasVersion(mtlno,parttype)){
//						System.out.println("222222222222=====");
	//				��ȡ�����ް汾���϶�Ӧ�İ汾
					
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
						//�ް汾��������������裬������A�汾����
						if(cappnumber==null||cappnumber.equals("")){
//							System.out.println("3333333333333=====");

							//���������Ϣ
							//QMPartIfc part=(QMPartIfc)ps.refreshInfo(partid);
							partMapreturn.put("mtlno", mtlno);
							partMapreturn.put("info", null);
							partMapreturn.put("version", "");
							partMapreturn.put("bs", "0");
							partMapreturn.put("error", "1");
							partMapreturn.put("nopart", "");
							partMapreturn.put("noversion", "���"+mtlno+"�ް汾����û���������ά����"   );
							return partMapreturn;
						}else{//�ް汾������
//							System.out.println("444444444444=====");
							Set newSet=partMap.keySet();
							Iterator newiter=newSet.iterator();
							String newkey="";
							String zxbs = "0";
							String[] numberAndVersion1 = splitPartNumber(cappnumber);
							cappVersion = numberAndVersion1[1];
						//	System.out.println("cappVersion00==="+cappVersion);
							//�����Ƿ��ж�Ӧ�����İ汾
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
							//���û�����İ汾�������capp�汾
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
					}else{//�����ް汾���򣬻�ȡA�汾���
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
				else{//�а汾
					//�Ȳ����İ汾������û������capp
//					System.out.println("99999999999999=====");
					Set newSet=partMap.keySet();
					Iterator newiter=newSet.iterator();
					String newkey="";
					Vector vecbb = new Vector();
					//���Ȳ����Ƿ��ж�Ӧ�����İ汾
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
					//���û�����İ汾�������capp�汾
					if(partMapreturn.size()==0){
//						System.out.println("13131131313=====");
						//��ȡcapp�汾
						vecbb=(Vector) partMap.get(partVersion);
//						System.out.println("vecbb==="+vecbb);
//						System.out.println("partMap==="+partMap);
						if(vecbb!=null){
							String zxbb = (String) vecbb.get(0);
							String partid = (String) vecbb.get(1);
							//��Ӧ��capp�汾���ǿյ�
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
							}else{//���İ汾��Ϊ��
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
										//�а汾�����Ҳ�����������裬�����ذ汾����
										if(cappnumber==null||cappnumber.equals("")){
//											������
											String sql3 = "insert into ERPPAN values('" +partnumber + "/"+zxbb + "','"+mtlno+"',zxbb,'','new')";
											PreparedStatement pstmt3 = conn.prepareStatement(sql3);
											//System.out.println("sql3333333333====" + sql3);
											int ii1 = pstmt3.executeUpdate();
											//�ر�����
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
				partMapreturn.put("nopart", "ϵͳû�д����"+mtlno+"���봴�������"   );
				partMapreturn.put("noversion", "" );
				
			}
			return partMapreturn;
	}
	
	/**
	 * ���ݱ�Ų�ֹ��򽫱�Ų�ֳɱ�źͰ汾������ֵ�����У�0��Ӧ��ţ�1��Ӧ�汾��
	 * ��������Ǿ������˺�����ϱ�ţ�
	 * ���û�������Ʒ����ɫ�������Ϲ��ˣ������ﷵ�صĿ����Ǵ������Ϣ��
	 * ���������/��������λ���ڣ����Ҷ�����ĸ��Ϊ�汾��������Ϊ��ţ��汾����A
	 * @param number
	 * @return String[] 0��Ӧ��ţ�1��Ӧ�汾
	 * @author houhf
	 */
	public static String[] splitPartNumber(String number)
	{
		//�������ϱ��
		String partNumber = number;
		//���ڷ��ص�String����
		String splitedString[] = new String[2];
		//�ж��Ƿ��а汾������а汾�򷵻����ϰ汾����û���򷵻�A
		//System.out.println("partNumber===="+partNumber);
		if(partNumber.indexOf("/") != -1)
		{
			//ȡ����/��֮����ַ������ڲ�ְ汾
			String temp = 
					partNumber.substring(partNumber.lastIndexOf("/")+1).trim();
			
			//������ж����-�� ˵�����к�׺��Ҫ����׺ȥ������в��
			if(countString(temp, "-")>1)
			{
				//�����Ϻŵĺ�׺ȥ��
				temp = temp.substring(0, temp.indexOf("-"));
			}
			
			//������ȵ���2���ж��Ƿ�����ĸ�������������Ϊ��ţ��汾ΪA
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
			
			//������ȵ���1���ж��Ƿ�����ĸ�������������Ϊ��ţ��汾ΪA
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
			//���Ȳ���1��2�����߲������������˵�����ǰ汾�������в��
			splitedString[0] = partNumber;
			splitedString[1] = "A";
		}
		else//�������ڰ汾���������ϺŶ�Ϊ���
		{
			splitedString[0] = partNumber;
			splitedString[1] = "A";
		}
		return splitedString;
	}
	/**
	 * ����ĳ���ַ����ַ���������˼���
	 * @param source �����ҵ��ַ���
	 * @param target ��Ҫ������ִ������ַ�
	 * @return ���ֵ������������0��û���ֹ�
	 * @author houhf
	 */
	private static int countString(String source,String target)
	{
		int count = 0;
		//Ҫ���ҵ��ַ���
		String str =source;
		//����ַ�������target
		while( str.indexOf(target)!=-1){
			count++;
			//���ַ������ֵ�λ��֮ǰ��ȫ��ȥ��
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
	 * �ж��Ƿ����ް汾������
	 * @param number
	 * @return String[] 0��Ӧ��ţ�1��Ӧ�汾
	 * @author houhf
	 */
	public static boolean ifNoVersion(String number)
	{
		//�������ϱ��
		String partNumber = number;
		//���ڷ��ص�String����
		String splitedString[] = new String[2];
		//�ж��Ƿ��а汾������а汾�򷵻����ϰ汾����û���򷵻�A
		//System.out.println("partNumber===="+partNumber);
		if(partNumber.indexOf("/") != -1)
		{
			//ȡ����/��֮����ַ������ڲ�ְ汾
			String temp = 
					partNumber.substring(partNumber.lastIndexOf("/")+1).trim();
			
			//������ж����-�� ˵�����к�׺��Ҫ����׺ȥ������в��
			if(countString(temp, "-")>1)
			{
				//�����Ϻŵĺ�׺ȥ��
				temp = temp.substring(0, temp.indexOf("-"));
			}
			
			//������ȵ���2���ж��Ƿ�����ĸ�������������Ϊ��ţ��汾ΪA
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
			
			//������ȵ���1���ж��Ƿ�����ĸ�������������Ϊ��ţ��汾ΪA
			if(temp.length() ==1)
			{
				temp = temp.toUpperCase();
				if(temp.charAt(0) >= 'A' && temp.charAt(0) <= 'Z')
				{

					return false;
				}
			}
			//���Ȳ���1��2�����߲������������˵�����ǰ汾�������в��
			return true;
		}
		else//�������ڰ汾���������ϺŶ�Ϊ���
		{
	
			return true;
		}
		
	}
	  /**
 	 *  ������Ƿ���汾
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
        //?	����㲿����������Ϊ��׼���������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        //?	������㲿�����͡�����Ϊ���ͣ������ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        if((partNumber.startsWith("CQ")) || (partNumber.startsWith("Q")) || (partNumber.startsWith("T"))){
        	
        	return true;
        }
        //��ʻ������Ű�����5000990���� ����������Ű�����1000940���������ϺŲ��Ӱ汾�����ϺŲ��Ӱ汾�������㲿����+·�ߵ�λ���
        if(partNumber.indexOf("5000990")>=0||partNumber.indexOf("1000940")>=0||partNumber.indexOf("5000020")>=0||partNumber.indexOf("5000030")>=0){
        	return true;
        }
        //ԭ���ϲ����汾
        if(partNumber.indexOf("*")>=0||partType.equals("Assembly")){

        	return true;
        }
//      �ж�����Ƿ��ǳ���
        long lenNumber = partNumber.length();
        if(partNumber.startsWith("C")&&partType.equals("Model")&&lenNumber==15){
        	return true;
	    }
        //�㲿����ŵ�һ����/����Ϊ��*L01*������0������*0��1��2��3��4����������ZBT������*(L)������AH������*J0*����
        //��*J1*������*-SF������BQ���͡�*-ZC���Ĳ��Ӱ汾�������㲿����+·�ߵ�λ��� ��
        if(partNumber.indexOf("/")>=0){
        	
        	int a = partNumber.indexOf("/");
        	//System.out.println("a="+a);
        	String temp = partNumber.substring(a+1);
        	//System.out.println("temp="+temp);
        	//��ȫƥ����a
        	String[] array1 = {"0","ZBT","AH","BQ"};
        	//���м��͡�a��
        	String[] array2 = {"L01","J0","J1"};
        	//�ڽ�βa��
        	String[] array3 = {"0","1","2","3","4","(L)","-SF","-ZC"};
        	//��ȫƥ����a
        	for (int i1 = 0; i1 < array1.length; i1++){
        		String str = array1[i1];
//        		System.out.println("temp==="+temp);
//        		System.out.println("str==="+str);
        		if(str.equals(temp)){
        			return true;
        		}
        	}
        	//���м��͡�a��
        	for (int i1 = 0; i1 < array2.length; i1++){
        	//	System.out.println("55555555555555==");
        		String str = array2[i1];
        		if(temp.indexOf(str)>=0){
    
        			return true;
        		}
        	}
        	//�ڽ�βa��
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
 	 *  ��ȡ������
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
//				�а汾�����Ҳ�����������裬�����ذ汾����
				if(erpnumber==null||erpnumber.equals("")){
					return cappPartNumber;
				}else{
					return erpnumber;
				}
		} catch (SQLException e) {
			// TODO �Զ����� catch ��
			e.printStackTrace();
		}
	    
			return cappPartNumber;
	}
}
