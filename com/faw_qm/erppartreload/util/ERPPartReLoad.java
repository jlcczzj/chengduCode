package com.faw_qm.erppartreload.util;
/**
 * ����ERPPartReLoad.java	1.0  2014/05/20
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * ERP���ݻص�Ԥ������
 * @author houhf
 */
public class ERPPartReLoad {

	//PDM�м�����ݿ�����
	private static String pdmoid;
	//PDM�û���
	private static String pdmuid;
	//PDM����
	private static String pdmpwd;
	//log�ļ��Ĵ��·��
//	private static String logfilepath;
	//log�ļ��Ĵ��·��
	private static String logFileName;
	//���δ������ݵ�����
	private static String count;
	//���浱ǰ���ڴ��������
	private static int treatedCount = 0;
	//���浱ǰ���ڴ����������
	private static String treatedNumber;
	//���浱ǰ���ڴ����BOM���
	private static String treatedBOMNumber;
	//IBA�ġ�����Դ�汾��ID
	private static String sourceVersion = "StringDefinition_7646";
	//��־·��
	private static String logpath =(String) RemoteProperty.getProperty("erpImportlogPath", "System");
	private static String partID;
	
	//����ERP���ϱ�ԭʼ���ݣ�����������Ѿ����ϳ�ERPMiddlePart����
	private  static Vector<ERPMiddlePart> originalMiddlePart;
	//��HashMap����ʽ����ERPMiddlePart���󣬱�����ERP��Ų��ҡ�key-ERP��ţ�value-ERPMiddlePart����
	private static HashMap<String, ERPMiddlePart> originalPartTable;
	//����ERP BOM��ԭʼ���ݣ�����������Ѿ����ϳ�ERPMiddleBOMLink����,key-������ţ�value-ERPMiddleBOMLink����
	private static HashMap<String, Vector<ERPMiddleBOMLink>> originalMiddleBOMLink;
	//����ṹ��Ϣ������ǿ��Խ���PDM��
	private static HashMap<String,Vector<ERPMiddleBOMLink>> PDMBOMTable;
//��ȡ������
	private static HashMap<String, String> pen_map;
	//�������ݿ�����
	public static Connection conn = null;
	
	//public static String dyversion = "A";
	
	/**
	 * @param args 0-���ݿ�ID 1-�û��� 2-���� 3-log��ŵ�ַ
	 * @author houhf
	 */
	public static void main(String[] args)
	{
		jInit();
	}
	
	/**
	 * ��ʼ������
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
	 * ��ʼִ��BOM�ص�
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
			// ���������ݿ������
			conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);

			for(int x=0; x<30; x++)
			{ 
				
				 SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
                 String stime =  simple.format(new Date());
                 System.out.println("start times =============mmm"+x+"****data========= "+stime);
				//����ʼ����ȷ��log�ļ�
				//String path = System.getProperty("java.class.path");
				SimpleDateFormat df = 
								new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				//path =  "\\opt\\pdmv4r3\\productfactory\\phosphor\\erpimport2014.log";
			//	path =  "\\pdm\\bea35\\user_projects\\domains\\bin\\erpimport2014.log";
				//logfilepath = path;

//				writeLog(mes);
				//��ʽ��ʼ...
				//��ʼ������
				pen_map = new HashMap<String, String>();
				originalPartTable = new HashMap<String, ERPMiddlePart>();
				originalMiddlePart = new Vector<ERPMiddlePart>();
				originalMiddleBOMLink = 
							new HashMap<String, Vector<ERPMiddleBOMLink>>();
				PDMBOMTable = 
							new HashMap<String, Vector<ERPMiddleBOMLink>>();
	
				//��ô����ERP����
				originalMiddlePart = (Vector<ERPMiddlePart>) 
									getAllMiddelParts(Integer.parseInt(count));
				 System.out.println("originalMiddlePart=="+originalMiddlePart.size());
				 partsl = partsl + originalMiddlePart.size();
				if(originalMiddlePart == null || originalMiddlePart.size() == 0)
					continue;

				//����һ��BOM�ṹ
				//��ʱ���Σ�ֻ��������
				/*
				//originalMiddleBOMLink = getAllMiddelPartsLinks(originalMiddlePart);
				//PDMBOMTable = getSubParts(originalMiddlePart);
				 * */
				 
				//������ڿɽ���PDM PART�����ݣ��������һ����PDM�м�����
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
				//ɾ�����������BOM��Ϣ
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
				//�ύ���
				conn.commit();
			}
			System.out.println("�������㲿��======="+partsl);
			conn.close();
			conn = null;
		}
		catch (Exception e)
		{
			String mes = "\n" + "----------�����ķָ���----------" + "\n";
			mes += "BOM�ص�Ԥ�������" + "\n" + "������Ϣ���£�" + "\n";
			mes += "���ݿ���Ϣ:"+"\n"+"IP��"+pdmoid+"\n"+"name:"+pdmuid+"\n";
			mes += "���δ�������:"+count+"\n"+"���ϱ��Ѿ����������:"+treatedCount+"\n";
			mes += "���������ڴ�����㲿����:"+treatedNumber+"\n";;
			mes += "���������ڴ����BOM��:"+treatedBOMNumber+"\n";
			mes += "�׳����쳣��:"+"\n"+e.getMessage();
			writeLog(mes);
			e.printStackTrace();
			return false;
		}
		System.out.println("all time =========" + (System.currentTimeMillis()-aaa));
		return true;
	}
	
	/**
	 * �õ����е�ERP PART �м�����ݡ����������Ʒ����ɫ����ԭ���ϡ�ͨ��sql���û�ȡ���ݡ�
	 * ���ڲ�ͨ��sql��ȡ�ˣ�ֱ�Ӷ���������ݡ�
	 * @return ����ERPMiddlePart�ļ��ϣ�����Ķ���ʱ���������ܹ����浽PDMPART�ġ�
	 * @author houhf
	 * @throws Exception 
	 */
	public static Collection<ERPMiddlePart> getAllMiddelParts(int reloadCount)
															throws Exception
	{
//		long a = System.currentTimeMillis();
		//�������ݿ�����
//		Connection conn = null;
		//�������ض��󼯺�
		Collection<ERPMiddlePart> coll = new Vector<ERPMiddlePart>();
		//System.out.println("11111111111111=====" );
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// ���������ݿ������
//			conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);
			
			//����ERP�����м��
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
						//�����0����erp�汾��pdm�汾һ�£��������ֻ�޸�·��
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
					//���ж��Ƿ��ǰ��Ʒ����ɫ����ԭ������������ټ���
					if(isHalfPart(temp.getERPNumber().trim()) 
						|| isColorPart(temp.getERPNumber().trim())
							|| temp.getDefaultUnit().equalsIgnoreCase("KG"))
					{
						//System.out.println("���Ʒ��========="+treatedNumber);
						setPartTreated(temp.getERPNumber());
//						if(pstmt!=null){
//							pstmt.close();
//							pstmt = null;
//							rs.close();
//							rs = null;
//						}
						continue;
					}
					
					//������㲿������ݾ���Ĺ��򴴽��ʺ�PDMPART��ERPMiddlePart����
					//��������Ű汾
					String str[] = splitPartNumber(temp.getERPNumber());
				
					temp.setPartNumber(str[0]);
					temp.setPartVersion(str[1]);
					
					
					
					coll.add(temp);
				}
				//��ʱ
//				a=System.currentTimeMillis()-a;
//				System.out.println("������һ��������ʱ"+a);
//				a=System.currentTimeMillis();
			}
			//�ر�����
			
				pstmt.close();
				pstmt = null;
				rs.close();
				rs = null;
			
		
		}
		catch (Exception e)
		{
			String massage = "��ȡ���ϳ�������" + "\n" + "/n";
			writeLog(massage + e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return coll;
	}
	
	/**
	 * �õ����е�ERP BOM �м�����ݡ�ͨ��sql���û�ȡ���ݡ�
	 * @return hashMap key��parentsNumber values��ֱ���ӹ�������Ʒ�����Ʒ��ԭ���ϣ���
	 * @author houhf
	 * @throws Exception 
	 */
	public static HashMap<String, Vector<ERPMiddleBOMLink>> 
		getAllMiddelPartsLinks(Vector<ERPMiddlePart> middelParts) throws Exception
	{
		//���صĽ����
		HashMap<String, Vector<ERPMiddleBOMLink>> resultTable 
							= new HashMap<String, Vector<ERPMiddleBOMLink>>();
		
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// ���������ݿ������
//			conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);
			//ͨ����������Vector�������ݿ�
			for(int i = 0 ;i < middelParts.size() ;i++)
			{
//				long aa = System.currentTimeMillis();
				ERPMiddlePart part = (ERPMiddlePart) middelParts.get(i);
				//����ERP BOM�м��
				String sql = "select t.fatherMtl, t.mtl, t.groupwareNumber, " +
							 "t.SDDocItemNO, t.sortSequence, t.remark, t.BOMCTG " +
							 "from ERPPDMBDMBOMMidTable t " + 
							 "where t.fatherMtl = '" + part.getERPNumber().trim()+
							 "'";
//				System.out.println("���Ҹ�����====" + part.getERPNumber());
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
					//������Ҫ���������������Ʒ����ɫ����ԭ���ϵĽṹ��ϵҲ�ӽ�ȥ
					//�ж��Ƿ��ǰ��Ʒ����ɫ����ԭ����������������������������ƷΪֹ
					if(isHalfPart(temp.getChildNumber().trim()) 
						|| isColorPart(temp.getChildNumber().trim())
							|| temp.getIsMater().equalsIgnoreCase("Y"))
					{
						//���������в�����������ṹ��Ϣ����б�����������
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
//			String massage = "��ȡ�ṹ��������" + "\n" + "/n";
//			writeLog(massage + e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return resultTable;
	}
	
	/**
	 * ���ݶ�ERP PART�м��õ������ݴ�ERP BOM�м���еõ����е�ֱ���Ӽ�
	 * �����������Ʒ��ԭ���Ϻ���ɫ������
	 * ����ζ������������ܹ�����PDM BOM����
	 * @return hashMap�У�key��parentsNumber values��ֱ���ӹ���
	 * @author houhf
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Vector<ERPMiddleBOMLink>> 
				getSubParts(Vector<ERPMiddlePart> middelParts) throws Exception
	{
		//���صĽ����
		HashMap<String, Vector<ERPMiddleBOMLink>> resultTable = 
							new HashMap<String, Vector<ERPMiddleBOMLink>>();
		//ͨ����������Vector�������ݿ�
		for(int i = 0 ;i < middelParts.size() ;i++)
		{
			ERPMiddlePart part = (ERPMiddlePart) middelParts.get(i);
			//������е�BOM Vector
			Vector<ERPMiddleBOMLink> resultLinkVec 
											= new Vector<ERPMiddleBOMLink>();
			//�ж��Ƿ���BOM���棬����л�����ֱ�Ӵӻ�������
			if(originalMiddleBOMLink.size()>0)
			{
				//�жϻ������Ƿ�����Ӧ��BOM��Ϣ
				if(originalMiddleBOMLink.containsKey(part.getERPNumber()))
				{
					Vector<ERPMiddleBOMLink> linkVec = 
						originalMiddleBOMLink.get(part.getERPNumber());
					
					//����BOM������Ӽ����㲿���򽫽ṹ��ϵ���棬���������������±���
					for(int ii=0;ii<linkVec.size();ii++)
					{
						ERPMiddleBOMLink link = linkVec.get(ii);
						String subNumber = link.getChildNumber();
						//�ж��Ƿ��ǰ��Ʒ����ɫ����ԭ������������������
						if(isHalfPart(subNumber.trim()) 
							|| isColorPart(subNumber.trim())
								|| link.getIsMater().equalsIgnoreCase("Y"))
						{
							//������Ҫ��¼����
							int subCount = link.getDefaultUnit();
							//׼����������
							ERPMiddlePart sub = new ERPMiddlePart();
							sub.setERPNumber(subNumber);
							Vector<ERPMiddlePart> vec 
												= new Vector<ERPMiddlePart>();
							vec.add(sub);
							//��ʼ����
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
									//��������ź�ʹ������
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
						else//���ǵĻ���������
						{
							resultLinkVec.add(link);
						}
					}
				}
				else//�����в�������Ҫ����������Ҫ��ѯ���ݿ⣬�������getAllMiddelPartsLinks������ɲ�ѯ
				{
					Vector<ERPMiddlePart> partVec = 
												new Vector<ERPMiddlePart>();
					partVec.add(part);
					HashMap<String, Vector<ERPMiddleBOMLink>> subMap = 
											getAllMiddelPartsLinks(partVec);
					Vector<ERPMiddleBOMLink> linkVec = 
									(Vector)subMap.get(part.getERPNumber());
					//����BOM������Ӽ����㲿���򽫽ṹ��ϵ���棬���������������±���
					for(int ii=0;ii<linkVec.size();ii++)
					{
						ERPMiddleBOMLink link = linkVec.get(ii);
						String subNumber = link.getChildNumber();
						//�ж��Ƿ��ǰ��Ʒ����ɫ����ԭ������������������
						if(isHalfPart(subNumber.trim()) 
							|| isColorPart(subNumber.trim())
								|| link.getIsMater().equalsIgnoreCase("Y"))
						{
							//������Ҫ��¼����
							int subCount = link.getDefaultUnit();
							//׼����������
							ERPMiddlePart sub = new ERPMiddlePart();
							sub.setERPNumber(subNumber);
							Vector<ERPMiddlePart> vec 
								= new Vector<ERPMiddlePart>();
							vec.add(sub);
							//��ʼ����
							HashMap<String, Vector<ERPMiddleBOMLink>> subMap1 =
																getSubParts(vec);
							Vector subVec = (Vector)subMap1.get(sub.getERPNumber());
							//��������ź�ʹ������
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
						else//���ǵĻ���������
						{
							resultLinkVec.add(link);
						}
					}
				}
			}
			else//�����治���ڣ���ֱ�Ӳ�ѯ���ݿ⣬�������getAllMiddelPartsLinks������ɲ�ѯ
			{
				Vector<ERPMiddlePart> partVec = 
					new Vector<ERPMiddlePart>();
				partVec.add(part);
				HashMap<String, Vector<ERPMiddleBOMLink>> subMap = 
					getAllMiddelPartsLinks(partVec);
				Vector<ERPMiddleBOMLink> linkVec = (Vector)subMap.values();
//				����BOM������Ӽ����㲿���򽫽ṹ��ϵ���棬���������������±���
				for(int ii=0;ii<linkVec.size();ii++)
				{
					ERPMiddleBOMLink link = linkVec.get(ii);
					String subNumber = link.getChildNumber();
//					�ж��Ƿ��ǰ��Ʒ����ɫ����ԭ������������������
					if(isHalfPart(subNumber.trim()) 
						|| isColorPart(subNumber.trim())
							|| link.getIsMater().equalsIgnoreCase("Y"))
					{
//						������Ҫ��¼����
						int subCount = link.getDefaultUnit();
//						׼����������
						ERPMiddlePart sub = new ERPMiddlePart();
						sub.setERPNumber(subNumber);
						Vector<ERPMiddlePart> vec 
						= new Vector<ERPMiddlePart>();
						vec.add(sub);
//						��ʼ����
						HashMap<String, Vector<ERPMiddleBOMLink>> subMap1 =
															getSubParts(vec);
						Vector subVec = (Vector)subMap1.get(sub);
//						��������ź�ʹ������
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
					else//���ǵĻ���������
					{
						resultLinkVec.add(link);
					}
				}
			}
			//�����β�ѯ�����������
			resultTable.put(part.getERPNumber(), resultLinkVec);
		}
		return resultTable;
	}

//	/**
//	 * ���ݶ�ERP PART�м��õ������ݴ�ERP BOM�м���еõ����е�ֱ���Ӽ�
//	 * �����������Ʒ��ԭ���Ϻ���ɫ������
//	 * ����ζ������������ܹ�����PDM BOM����
//	 * @return Vector ֱ���ӹ���
//	 * @author houhf
//	 * @throws Exception 
//	 */
//	public static Vector<ERPMiddleBOMLink> 
//				getSubParts(String topNumber,String parentNumber) throws Exception
//	{
//		//������е�BOM Vector
//		Vector<ERPMiddleBOMLink> resultLinkVec = new Vector<ERPMiddleBOMLink>();
//		//�ж��Ƿ���BOM���棬����л�����ֱ�Ӵӻ�������
//		if(originalMiddleBOMLink.size()>0)
//		{
//			//�жϻ������Ƿ�����Ӧ��BOM��Ϣ
//			if(originalMiddleBOMLink.containsKey(parentNumber))
//			{
//				Vector<ERPMiddleBOMLink> linkVec = 
//									originalMiddleBOMLink.get(parentNumber);
//				
//				//����BOM������Ӽ����㲿���򽫽ṹ��ϵ���棬���������������±���
//				for(int ii=0;ii<linkVec.size();ii++)
//				{
//					ERPMiddleBOMLink link = linkVec.get(ii);
//					String subNumber = link.getChildNumber();
//					//�ж��Ƿ��ǰ��Ʒ����ɫ����ԭ������������������
//					if(isHalfPart(subNumber) || isColorPart(subNumber)
//						|| isMater(subNumber))
//					{
//						//������Ҫ��¼����
//						int subCount = link.getDefaultUnit();
//
//						//��ʼ����
//						Vector<ERPMiddleBOMLink> subMap = 
//											getSubParts(topNumber,subNumber);
//						if(subMap != null && subMap.size()>0)
//						{
//							for(int i=0;i<subMap.size();i++)
//							{
//								ERPMiddleBOMLink subLink = subMap.get(i);
//								//��������ź�ʹ������
//								int a = subCount * subLink.getDefaultUnit();
//								subLink.setDefaultUnit(a);
//								resultLinkVec.add(subLink);
//							}
//						}
//					}
//					else//���ǵĻ���������
//					{
//						resultLinkVec.add(link);
//					}
//				}
//			}
//			else//�����в�������Ҫ����������Ҫ��ѯ���ݿ⣬�������getAllMiddelPartsLinks������ɲ�ѯ
//			{
//				Vector<ERPMiddlePart> partVec = 
//											new Vector<ERPMiddlePart>();
//				partVec.add(part);
//				HashMap<String, Vector<ERPMiddleBOMLink>> subMap = 
//										getAllMiddelPartsLinks(partVec);
//				Vector<ERPMiddleBOMLink> linkVec = 
//								(Vector)subMap.get(part.getERPNumber());
//				//����BOM������Ӽ����㲿���򽫽ṹ��ϵ���棬���������������±���
//				for(int ii=0;ii<linkVec.size();ii++)
//				{
//					ERPMiddleBOMLink link = linkVec.get(ii);
//					String subNumber = link.getChildNumber();
//					//�ж��Ƿ��ǰ��Ʒ����ɫ����ԭ������������������
//					if(isHalfPart(subNumber) || isColorPart(subNumber)
//							|| isMater(subNumber))
//					{
//						//������Ҫ��¼����
//						int subCount = link.getDefaultUnit();
//						//׼����������
//						ERPMiddlePart sub = new ERPMiddlePart();
//						sub.setERPNumber(subNumber);
//						Vector<ERPMiddlePart> vec 
//							= new Vector<ERPMiddlePart>();
//						vec.add(sub);
//						//��ʼ����
//						HashMap<String, Vector<ERPMiddleBOMLink>> subMap1 =
//							getSubParts(vec);
//						Vector subVec = (Vector)subMap1.get(sub.getERPNumber());
//						//��������ź�ʹ������
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
//					else//���ǵĻ���������
//					{
//						resultLinkVec.add(link);
//					}
//				}
//			}
//		}
//		return null;
//	}
				
	/**
	 * ���ݱ���ж��Ƿ���Ʒ
	 * @param number �㲿�����
	 * @return true  �ǰ��Ʒ
	 * 	       false ���ǰ��Ʒ
	 * @author houhf
	 */
	public static boolean isHalfPart(String number)
	{
		String enumber = number.replace("��", "-");
		//�������Ʒ��׺�ļ���
		String[] halfPartFlag = 
			{"-T","-M","-P","-F","-B","-B1","-HQ","-W","-Q","-QQ","-H",
				"-C","-M(J)","-HH","-B2"};
		if(enumber.indexOf("-")<0)
		{
			return false;
		}
		//�������������ϱ�ź�׺��ֳ���
		String suffix = 
			enumber.substring(enumber.lastIndexOf("-"), enumber.length());
		if(suffix != null && suffix.length() > 0)
		{
			//�Ժ�׺�����ж�
			for(int i=0;i<halfPartFlag.length;i++)
			{
				if(suffix.trim().equalsIgnoreCase(halfPartFlag[i]))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * ���ݱ���ж��Ƿ���ɫ��
	 * @param number
	 * @return true  ����ɫ��
	 * 	       false ������ɫ��
	 * @author houhf
	 */
	public static boolean isColorPart(String number)
	{
		String enumber = number.replace("��", "-");
		//������ɫ����׺�ļ���
		String[] colorFlag = {"A9","AE","AG","AR","B1","B2","C3","CU",
				"DG","DJ","DK","EH","EQ","ER","EW","EX","G9","GC","GR",
				"H1","H3","H4","H6","HG","HH","HJ","HK","HL","HM",
				"M2","MH","MJ","P1","P2","P5","PQ"};
		if(enumber.indexOf("-")<0)
		{
			return false;
		}
		//�������������ϱ�ź�׺��ֳ���
		String suffix = 
			enumber.substring(enumber.lastIndexOf("-")+1, enumber.length());
		if(suffix != null && suffix.length() > 0)
		{
			//�Ժ�׺�����ж�
			for(int i=0;i<colorFlag.length;i++)
			{
				if(suffix.trim().equalsIgnoreCase(colorFlag[i]))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * ����ʹ�õ�λ�ж��Ƿ�ԭ����
	 * @param partNumber
	 * @return true  ��ԭ����
	 * 	       false ����ԭ����
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
	 * ���ݱ�Ų�ֹ��򽫱�Ų�ֳɱ�źͰ汾������ֵ�����У�0��Ӧ��ţ�1��Ӧ�汾��
	 * ��������Ǿ������˺�����ϱ�ţ�
	 * ���û�������Ʒ����ɫ�������Ϲ��ˣ������ﷵ�صĿ����Ǵ������Ϣ��
	 * @param number
	 * @return String[] 0��Ӧ��ţ�1��Ӧ�汾
	 * @author houhf
	 */
	private static String[] splitPartNumber1(String number)
	{
		//�������ϱ��
		String partNumber = number;
		//���ڷ��ص�String����
		String splitedString[] = new String[2];
		//�ж��Ƿ��а汾������а汾�򷵻����ϰ汾����û���򷵻�A
		if(partNumber.indexOf("/") != -1)
		{
			//ȡ����/��֮����ַ������ڲ�ְ汾
			String temp = 
					partNumber.substring(partNumber.lastIndexOf("/")+1).trim();
//			System.out.println("temp==="+temp);
//			if(temp.equalsIgnoreCase("D0"))
//			{
//				System.out.println("*********************");
//				System.out.println(temp.lastIndexOf("0")+"--------"+temp.length());
//				System.out.println("**********************");
//			}
			//�����м�����������������1�Ļ�ֻ��Ҫ�ж��Ƿ���0�����������2�Ļ����ж��Ƿ���AH��BQ
			//���������3�Ļ����ж��Ƿ���ZBT�������������Ҫ�����汾������в��
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
				//�������İ汾Ҫ��AZ֮��ĺ������ĸ��ǰ�����ĸ��Ҳ������汾
				System.out.println("��һ����ĸ***"+temp.substring(0)+"�ڶ�����ĸ"+temp.substring(1));
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
			
			//������������ַ���β�����
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
			
			//��������Ϊ�����ַ������
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
			
			//����ǰ����Ĺ�ϵ
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
			
			//������ж����-�� ˵�����к�׺��Ҫ����׺ȥ������в��
			if(countString(temp, "-")>1)
			{
				//�����Ϻŵĺ�׺ȥ��
				temp = temp.substring(0, temp.indexOf("-"));
			}
			//���û���˳�����˵���ǰ汾��Ϣ�����в��
			splitedString[0] = 
				partNumber.substring(0,partNumber.indexOf("/")) + 
				partNumber.substring(partNumber.indexOf("/")+temp.length()+1,partNumber.length());
			splitedString[1] = temp;
			
			//���ﻹҪ�ڽ���һ���ж��ų������ϲ�ֹ���ı����/AAAAA�ȡ�
			if(temp.length()>2)
			{
				splitedString[0] = partNumber;
				splitedString[1] = "A";
				return splitedString;
			}
			
			return splitedString;
		}
		else//�������ڰ汾���������ϺŶ�Ϊ���
		{
			splitedString[0] = partNumber;
			splitedString[1] = "A";
		}
		return splitedString;
	}
	
	/**
	 * ���ݱ�Ŵ�ERP BOM �м���еõ����еİ��Ʒ��ԭ���ϡ�
	 * ���������ѯ���ǻ����еĽṹ��Ϣ	
	 * @param number Ҫ���ҵ��㲿�����
	 * @return hashMap key�����Ʒ����ԭ���ϵĲ㼶��value�����Ʒ����ԭ���϶�Ӧ��link
	 * @author houhf
	 */
	private static HashMap<Integer,ERPMiddleBOMLink> getAllHalfParts(String number)
	{
		//�����
		HashMap<Integer,ERPMiddleBOMLink> resultMap = 
			new HashMap<Integer, ERPMiddleBOMLink>();
		//ͨ���������BOM
		Vector<ERPMiddleBOMLink> linkVec = originalMiddleBOMLink.get(number);
//		System.out.println("����getAllHalfParts----linkVec.size=="+linkVec.size());
		if(linkVec != null && linkVec.size()>0)
		{
			for(int i = 0;i<linkVec.size();i++ )
			{
				ERPMiddleBOMLink link = linkVec.get(i);
				//�ж��Ƿ��ǰ��Ʒ����ɫ����ԭ������������������
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
	 * ���ݱ�Ŵ�ERP BOM �м���еõ����еİ��Ʒ��ԭ���ϡ�	
	 * @param number Ҫ���ҵ��㲿�����
	 * @param middelPartslinks �㲿���ṹ��
	 * @return hashMap key�����Ʒ����ԭ���ϵĲ㼶��value�����Ʒ����ԭ���϶�Ӧ��link
	 * @author houhf
	 */
	private static HashMap<Integer,ERPMiddleBOMLink> getAllHalfParts
			(String number, HashMap<Integer,ERPMiddleBOMLink> middelPartslinks)
	{
		//�����
		HashMap<Integer,ERPMiddleBOMLink> resultMap = 
			new HashMap<Integer, ERPMiddleBOMLink>();
		resultMap.putAll(middelPartslinks);
		
		//�㼶
		int level = middelPartslinks.size();
		//ͨ���������BOM
		Vector<ERPMiddleBOMLink> linkVec = originalMiddleBOMLink.get(number);
		if(linkVec != null && linkVec.size()>0)
		{
			for(int i = 0;i<linkVec.size();i++ )
			{
				ERPMiddleBOMLink link = linkVec.get(i);
				//�ж��Ƿ��ǰ��Ʒ����ɫ����ԭ������������������
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
	 * ���м��㲿�����ݣ�����ֱ���Ӽ�������д�뵽PDM PART �м��ֱ���Ӽ�����д��PDM BOM �м����
	 * ���ֻ�������м���д��ڣ���BOM�м���в����ڣ�����·��ȥ�����м���е�����·�ߡ�
	 * @param mpart
	 * @author houhf
	 */
	public static void saveMiddelPartToOracle(ERPMiddlePart mpart) throws Exception 
	{
		//��¼���ݿⱣ������Ϣ
		String res = "";
		boolean flag = true;

			
		//�����㲿��
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
			//����ɹ�
			//��ERP���д���
			String sql1 = "update ERPPDMMMRMtlNoMidTable set ISPRETREATE = 1 where mtlNO = '" +
						  mpart.getERPNumber() + "'";
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
//			System.out.println("sql1====" + sql1);
			int ii = pstmt1.executeUpdate();
			//�ر�����
			pstmt1.close();
			pstmt1 = null;
			//����������������bom
			ii=0;
			if(ii>0)
			{
				//CCBegin SS1
				String istreated = mpart.getIsNew();
//				if(istreated.equals("updateRoute"))
//					return;
				//CCEnd SS1
				//�������ɹ�֮��ʼ����BOM��Ϣ
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
					//�ر�����
					pstmt2.close();
					pstmt2 = null;
					if(iii>0)
					{
						continue;
					}
					else
					{
						res = "��PDM�м��BOM�������ݳ���";
						flag = true;
						break;
					}
				}
				
			}
			else
			{
				res = "��ERP�м����±�ǳ���";
				flag = true;
			}
		}
		else
		{
			//����ʧ��
			res = "��PDM�м��PART�������ݳ���";
			flag = true;
		}
//		if(flag)
//		{
			//�ύ���
			//conn.commit();
			//�ر�����
			pstmt.close();
			pstmt = null;
		//}
//		else
//		{
//			throw(new Exception(res));
//		}
	}
	
	/**
	 * ��ָ����Ŀ¼������LOG�ļ���
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
			System.out.println("д��־�ļ���������");
			e.printStackTrace();
		}
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
	 * ����㲿������·����Ϣ
	 * @param part ERP�м������
	 * @return �㲿������·����Ϣ
	 * @author houhf
	 */
	public static String getPartManuRoute(ERPMiddlePart part)
	{
		//����ԭ���ϵ�·��
		String routeString = "";
		HashMap<Integer,ERPMiddleBOMLink> linkMap = 
										getAllHalfParts(part.getERPNumber());
		//��ʾ�Ƿ���ԭ����
		boolean materFlag = false;
//		System.out.println("��ǰ����·�ߵļ�"+part.getERPNumber()+"linkMap.size==="+linkMap.size());
		if(linkMap != null &&linkMap.size()>0)
		{
			for(int i=linkMap.size();i>0;i--)
			{
				ERPMiddleBOMLink link = linkMap.get(i);
//				System.out.println("���ҵ�linkMap�Ӽ���"+link.getChildNumber());
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
					//�������ɫ�����ϲ�·��
					if(isColorPart(subPart.getERPNumber().trim()))
						continue;
//					System.out.println("subPartERPNumber == "+subPart.getERPNumber() + "-----materFlag===" + materFlag);
					//�ж��Ƿ��Ǽ�����λΪ��KG����ԭ����
					if(subPart.getDefaultUnit() != null 
						&& subPart.getDefaultUnit().length() > 0
							&& subPart.getDefaultUnit().equalsIgnoreCase("KG"))
					{
//						System.out.println("subPartERPNumber == "+subPart.getERPNumber() + "��ԭ����");
						//������������ԭ����·��
						if(materFlag)
						{
							//������������ԭ���ϣ��򱾼�·�߲����кϲ�
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
	 * ͨ����Ų���ERP�������
	 * @param number �㲿�����
	 * @return �㲿������
	 * @author houhf
	 */
	private static ERPMiddlePart getERPPartFromNumber(String number)
	{
		ERPMiddlePart part = new ERPMiddlePart();
//		//�������ݿ�����
//		Connection conn = null;
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// ���������ݿ������
//			conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);
			
			//����ERP�����м��
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
			//�ر�����
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
	 * �����㲿��Ϊ�Ѵ���״̬
	 * @param partERPNumber
	 * @throws Exception
	 * @author houhf
	 */
	public static void setPartTreated(String partERPNumber) throws Exception
	{
//		//�������ݿ�����
//		Connection conn = null;
//		Class.forName("oracle.jdbc.OracleDriver").newInstance();
//		// ���������ݿ������
//		conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);

		String sql1 = 
			"update ERPPDMMMRMtlNoMidTable set ISPRETREATE = 1 where mtlNO = '"
			+ partERPNumber + "'";
		PreparedStatement pstmt1 = conn.prepareStatement(sql1);
		int i = pstmt1.executeUpdate();
		if(i > 0)
		{
			//�ύ���
			//conn.commit();
			//�ر�����
			pstmt1.close();
			pstmt1 = null;
		}
		else
		{
			new Exception("�����㲿��Ϊ�Ѵ���״̬��������");
		}
	}
	
	/**
	 * ����PDM����㲿���Ƿ���Ҫ����
	 * @param ERPNumber
	 * @return 0 ����Ҫ 1 ��Ҫ 2 ���� 3 �½� 
	 * @author houhf
	 */
	public static int isNeedToTreate(ERPMiddlePart part,String penbs)
	{
		//System.out.println("isNeedToTreate start=");
		//���ж��Ƿ��ǰ��Ʒ����ɫ����ԭ������������ټ���
		if(isHalfPart(part.getERPNumber().trim())
			||isColorPart(part.getERPNumber().trim())
				||part.getDefaultUnit().equalsIgnoreCase("KG"))
		{
			return 1;
		}
		String partName = part.getPartName();
		//�����д����ɹ�·�ߣ��ͣ������ߣ����ص�
		if(partName.indexOf("�ɹ�·��")>=0||partName.indexOf("������")>=0)
			return 0;
		//���ݻص����ǿ�����·����������е���
		//��ʼ��ֱ�ţ���ֳ���źͰ汾
		String[] numberAndVersion = splitPartNumber(part.getERPNumber());
		//String penbs = part.getPenbs();
		 String erpNumber = part.getERPNumber();
		//System.out.println("part.getERPNumber()===" + part.getERPNumber());
		String dyversion = "A";
//		//�������ݿ�����
//		Connection conn = null;
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// ���������ݿ������
//			conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);
			
			//������д�汾��Ϣ
			String sql = "select t.BSOID, t.VERSIONID ,t.parttypestr from QMPART t " + 
						 "where t.MASTERBSOID = " + 
						 "(select a.BSOID from QMPARTMASTER a "+
						 "where a.PARTNUMBER = '" + numberAndVersion[0] + "'" +
						 " and a.BSOID not like '%(%' and rownum <2)" +
						 "ORDER BY t.VERSIONVALUE ASC";
			
			//System.out.println("SQL1=====" + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			HashMap partMap =new HashMap();//���capp�汾�����İ汾��Ӧ
			String cappzgb = ""; //capp ��߰汾
			String fbybb = "";//����Դ��߰汾
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
				 //����id�Ͱ汾��ȡ�汾
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
//			��Ҫ�Ƚϵİ汾
			if(fbybb.equals("")){//����Դ�ǿ�ֱ�ӻ�ȡcapp��߰�
				bybb=cappzgb;
			}else{//����Դ��Ϊ�գ����ȡ����Դ�汾�����Һ�capp�汾�Ƚϣ���ȡ�Ƚ����ֵ
				
				int result =cappzgb.compareTo(fbybb);
				if(result>0)
					bybb=cappzgb;
				else
					bybb=fbybb;
			}
//			System.out.println("bybb==="+bybb);
//			System.out.println("penbs==="+penbs);
//			System.out.println("ifNoVersion(part.getERPNumber())==="+ifNoVersion(part.getERPNumber()));
			//��������
			if(penbs.equals("1")){
				//�������ް汾������
				if(ifNoVersion(erpNumber)){
//					��ȡ�����ް汾���϶�Ӧ�İ汾
					
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
					//	--------------------��ȡ��汾����
					//�з���Դ�汾��
					if(fbybb!=null&&!fbybb.equals("")){
						Set newSet=partMap.keySet();
						Iterator newiter=newSet.iterator();
						String newkey="";
						String zxbs = "0";
						//System.out.println("fbybb11111111111==="+fbybb);
						//�����Ƿ��ж�Ӧ�����İ汾
						while(newiter.hasNext())
						{
							newkey=(String)newiter.next();// �ɼ��ϲ������¼������ݣ���ʾ�¼���������
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
						//���û�ж�Ӧ�����İ汾��������㲿���汾��Ӧ�����İ汾
						String zxversion = (String) partMap.get(dyversion);
						if(zxversion!=null&&zxbs.equals("0")){
							//���capp�汾�ж�Ӧ�ķ���Դ�汾�����ҷ���Դ�汾���� erp�ص��İ汾��д��
							  String sql2 = "update ERPPAN set BEFOREMATERIAL = '" +
							   erpNumber + "/"+zxversion + "',isnew ='"+"update"+"' where Aftermaterial='"+erpNumber+"'";
								PreparedStatement pstmt2 = conn.prepareStatement(sql2);
								//System.out.println("sql2====" + sql2);
								int ii = pstmt2.executeUpdate();
								//�ر�����
								pstmt2.close();
								pstmt2 = null;	
								}
					}
				}
				
				
			}else{//��������
				//1���ް汾
				//System.out.println("in2222222222=====1");
				if(ifNoVersion(erpNumber)){
					//�ж�������Ƿ�������capp�ӿ��ް汾��������
					// System.out.println("ifHasVersion(erpNumber,parttype)=="+ifHasVersion(erpNumber,parttype));
					if(!ifHasVersion(erpNumber,parttype)){
//						System.out.println("partMap.size()==="+partMap.size());
						if(partMap.size()>1){
							Set newSet=partMap.keySet();
							Iterator newiter=newSet.iterator();
							String newkey="";
							while(newiter.hasNext())
							{
//								���ް汾���Ҳ�����������ݣ����м�¼
								newkey=(String)newiter.next();// �ɼ��ϲ������¼������ݣ���ʾ�¼���������
								{
									//String zxversion=(String)partMap.get(newkey);
									String sql2 = "insert into ERPVERDIFFERENT  values('" +
								    erpNumber + "/"+newkey + "' , '"+erpNumber+"','','','')";
									PreparedStatement pstmt2 = conn.prepareStatement(sql2);
									//System.out.println("sql222222222222====" + sql2);
									int ii = pstmt2.executeUpdate();
									//�ر�����
									pstmt2.close();
									pstmt2 = null;	
								
										
								}
								
							
							}  
//							����¼����,ֻ��¼A�汾��Ӧ��ϵ
							String sql3 = "insert into ERPPAN values('" +numberAndVersion[0] + "/"+"A" + "','"+numberAndVersion[0]+"','A','','new')";
							PreparedStatement pstmt3 = conn.prepareStatement(sql3);
							//System.out.println("sql3333333333====" + sql3);
							int ii1 = pstmt3.executeUpdate();
							//�ر�����
							pstmt3.close();
							pstmt3 = null;	
						}
						if(partMap.size()==1){
						//	System.out.println("������====partMap.size()==1");
							//���ް汾���Ҳ�����������ݣ����м�¼
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
								//�ر�����
								pstmt2.close();
								pstmt2 = null;	
								//����¼����
								String sql3 = "insert into ERPPAN values('" +numberAndVersion[0] + "/"+cappbb + "','"+numberAndVersion[0]+"','"+cappbb+"','','new')";
								PreparedStatement pstmt3 = conn.prepareStatement(sql3);
								//System.out.println("sql3333333333====" + sql3);
								int ii1 = pstmt3.executeUpdate();
								//�ر�����
								pstmt3.close();
								pstmt3 = null;	
							}
						}
					}
					
					
				}else{
//					�а汾
//					�з���Դ�汾�����û�з���ԭ��ɾͲ���Ҫ�ڲ����ˣ���Ϊ�����ڰ汾����Ӧ���
					if(fbybb!=null&&!fbybb.equals("")){
						Set newSet=partMap.keySet();
						Iterator newiter=newSet.iterator();
						String newkey="";
						String zxbs = "0";
						//System.out.println("fbybb==="+fbybb);
						//�����Ƿ��ж�Ӧ�����İ汾
						while(newiter.hasNext())
						{
							newkey=(String)newiter.next();// �ɼ��ϲ������¼������ݣ���ʾ�¼���������
							{
								String zxversion=(String)partMap.get(newkey);
								if(zxversion.indexOf(".")>0)
									zxversion = 
										zxversion.substring(0, zxversion.indexOf("."));
								if(zxversion.equals(numberAndVersion[1])){//�鿴��û�з������������İ汾
									zxbs="1";
									continue;
								}
									
							}
						}
						//���û�ж�Ӧ�����İ汾����Ѱ汾����capp�汾�ٲ����㲿���汾��Ӧ�����İ汾
//						System.out.println("numberAndVersion[1]====" + numberAndVersion[1]);
//						System.out.println("partMap====" + partMap);
						String zxversion = (String) partMap.get(numberAndVersion[1]);
//						System.out.println("zxversion====" + zxversion);
//						System.out.println("zxbs====" + zxbs);
						if(zxversion!=null&&zxbs.equals("0")){
							//���capp�汾�ж�Ӧ�ķ���Դ�汾�����ҷ���Դ�汾���� erp�ص��İ汾��д��
							String sql3 = "insert into ERPPAN values('" +numberAndVersion[0] + "/"+zxversion + "','"+erpNumber+"','"+zxversion+"','','new')";
								PreparedStatement pstmt2 = conn.prepareStatement(sql3);
							//	System.out.println("sql3====" + sql3);
								int ii = pstmt2.executeUpdate();
								//�ر�����
								pstmt2.close();
								pstmt2 = null;	
						  }
				     }
					
				}

			}
			
			
	
             //bybb�����ڱȽϵİ汾
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
    				if(result>0)//ERP�汾����PDM�汾
    					return 2;
    				if(result==0)//ERP�汾����PDM�汾
    					return 0;
    				if(result<0)//ERP�汾С��PDM�汾,��������
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
	 * ɾ��һ�����ϱ���Ϣ
	 * @param ERPNumber ERP���
	 * @author houhf
	 */
	public static void deleteMLTByNumber(String ERPNumber)
	{
//		//�������ݿ�����
//		Connection conn = null;
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// ���������ݿ������
//			conn = DriverManager
//					.getConnection(pdmoid, pdmuid, pdmpwd);
			String sql = "delete from ERPPDMMMRMtlNoMidTable t " +
			 			 "where t.mtlNO = '"+ERPNumber+"'";
			
			//System.out.println("ɾ������SQL=====" + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int i = pstmt.executeUpdate();
			if(i>0)
			{
				//�ύ���
				//conn.commit();
				//�ر�����
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
	 * ɾ��һ�����ϱ���Ϣ
	 * @param parentERPNumber ����ERP���
	 * @param childERPNumber �Ӽ�ERP���
	 * @author houhf
	 */
	public static void deleteBOMByNumber(String parentERPNumber, String childERPNumber)
	{
//		//�������ݿ�����
//		Connection conn = null;
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// ���������ݿ������
//			conn = DriverManager
//					.getConnection(pdmoid, pdmuid, pdmpwd);
			
			String sql = "delete from ERPPDMBDMBOMMidTable t " +
			 			 "where t.fatherMtl = '"+parentERPNumber+"'" + 
			 			 " and t.mtl = '" + childERPNumber + "'";
			
//			System.out.println("ɾ��BOMSQL=====" + sql);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int i = pstmt.executeUpdate();
			if(i>0)
			{
				//�ύ���
				//conn.commit();
				//�ر�����
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
	 * �õ����е�ERP PART �м�����ݡ����������Ʒ����ɫ����ԭ���ϡ�ͨ��sql���û�ȡ���ݡ�
	 * ���ڲ�ͨ��sql��ȡ�ˣ�ֱ�Ӷ���������ݡ�
	 * @return ����ERPMiddlePart�ļ��ϣ�����Ķ���ʱ���������ܹ����浽PDMPART�ġ�
	 * @author houhf
	 * @throws Exception 
	 */
	public static HashMap getAllPenParts()	throws Exception
	{

		//�������ض��󼯺�
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
			//�ر�����
			
				pstmt.close();
				pstmt = null;
				rs.close();
				rs = null;
			
		
		}
		catch (Exception e)
		{
			String massage = "��ȡ�����ݳ�������" + "\n" + "/n";
			writeLog(massage + e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return MAP;
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
}
