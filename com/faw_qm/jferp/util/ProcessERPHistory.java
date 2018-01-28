/**
 * ���ɳ���PublishERPHistory.java	1.0              2007-9-27
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ��ʷ���㲿�������ϴ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author 
 * @version 1.0
 */
public class ProcessERPHistory 
{
	//�������ݿ�����
	public static Connection conn = null;
	//����ERP���ϱ�ԭʼ���ݣ�����������Ѿ����ϳ�ERPMiddlePart����
	private  static Vector<ERPMiddlePart> originalMiddlePart;
	//��HashMap����ʽ����ERPMiddlePart���󣬱�����ERP��Ų��ҡ�key-ERP��ţ�value-ERPMiddlePart����
	//private static HashMap<String, ERPMiddlePart> originalPartTable;
	//����ERP BOM��ԭʼ���ݣ�����������Ѿ����ϳ�ERPMiddleBOMLink����,key-������ţ�value-ERPMiddleBOMLink����
	private static HashMap<String, Vector<ERPMiddleBOMLink>> originalMiddleBOMLink;
	//����ṹ��Ϣ������ǿ��Խ���PDM��
	private static HashMap<String,Vector<ERPMiddleBOMLink>> PDMBOMTable;
	//���δ������ݵ�����
	private static String count = "1000";
	//���浱ǰ���ڴ��������
	private static int treatedCount = 0;
	//���浱ǰ���ڴ����������
	private static String treatedNumber;
	//���浱ǰ���ڴ����BOM���
	private static String treatedBOMNumber;
	
	private static String  pdmoid = "jdbc:oracle:thin:@10.0.227.1:1521:orcl";

	private static String pdmuid = "etest";

	private static String pdmpwd = "etest";
	//IBA�ġ�����Դ�汾��ID
	private static String sourceVersion = "StringDefinition_7646";
	private static String path =  "\\opt\\pdmv4r3\\jfdomain\\bin\\erpHistory2014.log";
	//log�ļ��Ĵ��·��
	private static String logfilepath;
	//log�ļ��Ĵ��·��
	private static String logFileName;
	//uid
	private static int uid = 0;
	private  static String partnumber = "";
	//	�������ض��󼯺�
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
			// ���������ݿ������
			
			ERPPartReLoad reload = new ERPPartReLoad();
			final SimpleDateFormat simple = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss ");
			String  stime = simple.format(new Date());
			//��ʱ
			long aa = System.currentTimeMillis();
		    System.out.println("start erp interface data========="+stime); 
			for(int x=0; x<6000; x++)
			{
				conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);
				//System.out.println("start times ============="+x+"****data========= "+stime);
				//����ʼ����ȷ��log�ļ�
				//String path = System.getProperty("java.class.path");
				
				//System.out.println("path="+path);
				long bb = System.currentTimeMillis();
//				writeLog(mes);
				//��ʽ��ʼ...
			//	originalPartTable = new HashMap<String, ERPMiddlePart>();
				originalMiddlePart = new Vector<ERPMiddlePart>();
				originalMiddleBOMLink = 
							new HashMap<String, Vector<ERPMiddleBOMLink>>();
				PDMBOMTable = 
							new HashMap<String, Vector<ERPMiddleBOMLink>>();
				aa = System.currentTimeMillis();
				//��ô����ERP����
				originalMiddlePart = (Vector<ERPMiddlePart>) 
									getAllMiddelParts(Integer.parseInt(count));
				//System.out.println("1***** "+ (System.currentTimeMillis()-aa));
				aa = System.currentTimeMillis();
				if(originalMiddlePart == null || originalMiddlePart.size() == 0)
					break;

				//����һ��BOM�ṹ
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
//							����JFMATERIALSTRUCTURE
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
				//������ڿɽ���PDM PART�����ݣ��������һ����PDM�м�����
				if(originalMiddlePart.size()>0)
				{
					for(int i=0; i<originalMiddlePart.size(); i++)
					{
						ERPMiddlePart part = originalMiddlePart.get(i);
							//����JFMATERIALPARTSTRUCTURE
							savePartStruct(part,PDMBOMTable);
							
						
					}
				}
				//System.out.println("5***** "+ (System.currentTimeMillis()-aa));
				
				//�ύ���
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
			String mes = "\n" + "----------�����ķָ���----------" + "\n";
			mes += "BOM�ص�Ԥ�������" + "\n" + "������Ϣ���£�" + "\n";
		//	mes += "���ݿ���Ϣ:"+"\n"+"IP��"+pdmoid+"\n"+"name:"+pdmuid+"\n";
			mes += "���δ�������:"+count+"\n"+"���ϱ��Ѿ����������:"+treatedCount+"\n";
			mes += "���������ڴ�����㲿����:"+treatedNumber+"\n";;
			mes += "���������ڴ����BOM��:"+treatedBOMNumber+"\n";
			mes += "�׳����쳣��:"+"\n"+e.getMessage();
			writeLog(mes);
			e.printStackTrace();
			setCounter();
			return false;
		}
		return true;
	}
	/**
	 * ���м��㲿�����ݣ�����ֱ���Ӽ�������д�뵽PDM PART �м��ֱ���Ӽ�����д��PDM BOM �м����
	 * ���ֻ�������м���д��ڣ���BOM�м���в����ڣ�����·��ȥ�����м���е�����·�ߡ�
	 * @param mpart
	 * @author houhf
	 */
	public static void savePartStruct(ERPMiddlePart mpart,HashMap originalMiddleBOMLink) throws Exception 
	{
		//��¼���ݿⱣ������Ϣ
		String res = "";
		boolean flag = true;
		ERPPartReLoad reload = new ERPPartReLoad();
		long curTime=System.currentTimeMillis();
		Timestamp createTime=new Timestamp(curTime);
   	    Timestamp modifyTime=new Timestamp(curTime);
   	    //��ȡ����·��
   	    String ERPnumber = mpart.getERPNumber();
        Vector<ERPMiddleBOMLink> resultLinkVec = new Vector<ERPMiddleBOMLink>();
        resultLinkVec = (Vector<ERPMiddleBOMLink>) PDMBOMTable.get(ERPnumber);
        if(resultLinkVec.size()==0)
        	return;
//   	    //�������
//   	    String parttype = PublishHistoryHelper.getPartType(mpart);
//   	    //�����
//   	    int xnj = PublishHistoryHelper.getxnj(mpart);
        
        for(int j=0;j<resultLinkVec.size();j++){
        	String bsoid = getUid("JFMATERIALPARTSTRUCTURE");
        	ERPMiddleBOMLink bomlink = resultLinkVec.elementAt(j);
        	//�������ϱ�
    		String sql = "insert into JFMaterialPartStructure values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    		
    		PreparedStatement pstmt = conn.prepareStatement(sql);
    		//�������ţ�������汾
    		String erpParentNumber=bomlink.getParentNumber();
			String erpchildNumber=bomlink.getChildNumber().trim();
//			String str1[] = reload.splitPartNumber(erpParentNumber);
//			String str2[] = reload.splitPartNumber(erpchildNumber);
//		    String parentNumber = str1[0];
//		    String childNumber = str2[0];
		    //��������Ӽ���ͬ������
    		if(erpParentNumber.equals(erpchildNumber))
    			continue;
    		pstmt.setString(1,  bsoid);//BSOID
    		pstmt.setTimestamp(2,  createTime);//CREATETIME
    		pstmt.setString(3,  "0");//LEVELNUMBER
    		pstmt.setInt(4,  0);//OPTIONFLAG
    		pstmt.setString(5, ERPnumber);//PARENTPARTNUMBER
    		pstmt.setString(6,  "��");//DEFAULTUNIT
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
    			//����ʧ��
    			res = "��PDM�м��PART�������ݳ���";
    			flag = true;
    		}
    		if(flag)
    		{
    			//�ύ���
    			//conn.commit();
    			//�ر�����
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
	/* ���м��㲿�����ݣ�����ֱ���Ӽ�������д�뵽PDM PART �м��ֱ���Ӽ�����д��PDM BOM �м����
	 * ���ֻ�������м���д��ڣ���BOM�м���в����ڣ�����·��ȥ�����м���е�����·�ߡ�
	 * @param mpart
	 * @author houhf
	 */
	public static void saveMaterialStruct(ERPMiddlePart mpart,HashMap<String, Vector<ERPMiddleBOMLink>> originalMiddleBOMLink1) throws Exception 
	{
		//��¼���ݿⱣ������Ϣ
		String res = "";
		boolean flag = true;
		ERPPartReLoad reload = new ERPPartReLoad();
		long curTime=System.currentTimeMillis();
		Timestamp createTime=new Timestamp(curTime);
   	    Timestamp modifyTime=new Timestamp(curTime);
   	    //��ȡ����·��
   	    String ERPnumber = mpart.getERPNumber();
        Vector<ERPMiddleBOMLink> resultLinkVec = new Vector<ERPMiddleBOMLink>();
        resultLinkVec = (Vector<ERPMiddleBOMLink>) originalMiddleBOMLink1.get(ERPnumber);
//        System.out.println("ERPnumber="+ERPnumber);
//        System.out.println("partNumber11111="+resultLinkVec.elementAt(0).getPrentPartNumber());
        if(resultLinkVec==null||resultLinkVec.size()==0)
        	return;
//   	    //�������
//   	    String parttype = PublishHistoryHelper.getPartType(mpart);
//   	    //�����
//   	    int xnj = PublishHistoryHelper.getxnj(mpart);
        for(int j=0;j<resultLinkVec.size();j++){
        	String bsoid = getUid("JFMATERIALSTRUCTURE");
        	ERPMiddleBOMLink bomlink = resultLinkVec.elementAt(j);
        	//�������ϱ�
    		String sql = "insert into JFMATERIALSTRUCTURE values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    	 
    		
    		String erpParentNumber=bomlink.getParentNumber();
			String erpchildNumber=bomlink.getChildNumber().trim();
			String partNumber = bomlink.getPrentPartNumber();
//			
//			System.out.println("erpParentNumber1111111="+erpParentNumber);
//			System.out.println("partNumber11111="+partNumber);
//			�ж��Ӽ��Ƿ��ǰ��Ʒ��������ټ���
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
    		pstmt.setString(10,  "��");//DEFAULTUNIT	
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
    			//����ʧ��
    			res = "�����Ͻṹ�������ݳ���";
    			
    		}
    		
    			//�ر�����
    			pstmt.close();
    			pstmt = null;
    		
        }
	
		return ;
	}
	/**
	 * ���м��㲿�����ݣ�����ֱ���Ӽ�������д�뵽PDM PART �м��ֱ���Ӽ�����д��PDM BOM �м����
	 * ���ֻ�������м���д��ڣ���BOM�м���в����ڣ�����·��ȥ�����м���е�����·�ߡ�
	 * @param mpart
	 * @author houhf
	 */
	public static int saveMaterialSplit(ERPMiddlePart mpart,String rcode) throws Exception 
	{
		//��¼���ݿⱣ������Ϣ
		//System.out.println("mpart="+mpart.getPartNumber());
		String res = "";
		boolean flag = true;
		ERPPartReLoad reload = new ERPPartReLoad();
		String bsoid = getUid("JFMATERIALSPLIT");
		long curTime=System.currentTimeMillis();
		Timestamp createTime=new Timestamp(curTime);
   	    Timestamp modifyTime=new Timestamp(curTime);
   	    //��ȡ����·��
   	    String manuRoute = getManuRoute(mpart.getERPNumber());
   	    //�������
   	    String parttype = getPartType(mpart);
   	    //�����
   	    int xnj = getxnj(mpart);
   	    String partnumber = mpart.getERPNumber();
   		if(reload.isHalfPart(partnumber)){
   			partnumber=getsplitPartNumber(partnumber);
   		}
		//�������ϱ�
   	 	String sql = "insert into JFMATERIALSPLIT values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?" +
		",?,?,?,?,?,?,?,?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		/*���ֶ�˳��BSOID��CREATETIME��MODIFYTIME��DOMAIN��PARTNUMBER��PARTVERSION��
		 STATE��MATERIALNUMBER��SPLITED��VIRTUALFLAG��STATUS��ROUTECODE��ROUTE��
		 PARTNAME��DEFAULTUNIT��PRODUCEDBY��PARTTYPE��PARTMODIFYTIME��OPTIONCODE��
		 RCODE��MATERIALSPLITTYPE��COLORFLAG��ROOTFLAG��ISMOREROUTE*/
		
		pstmt.setString(1,  bsoid);//BSOID
		pstmt.setTimestamp(2,  createTime);//CREATETIME
		pstmt.setTimestamp(3,  modifyTime);//MODIFYTIME
		pstmt.setString(4,  "Domain_102");//DOMAIN
		pstmt.setString(5,  partnumber);//PARTNUMBER
		pstmt.setString(6,  mpart.getPartVersion().trim());//PARTVERSION
		pstmt.setString(7,  "����׼��");//STATE
		pstmt.setString(8,  mpart.getERPNumber().trim());//MATERIALNUMBER
		pstmt.setInt(9,  1);//SPLITED
		pstmt.setInt(10, xnj);//VIRTUALFLAG
		pstmt.setInt(11, 2);//STATUS
		pstmt.setString(12, rcode);//ROUTECODE
		pstmt.setString(13, manuRoute);//ROUTE
		pstmt.setString(14, mpart.getPartName());//partname
		pstmt.setString(15,  "��װ");//DefaultUnit	
		pstmt.setString(16, "N");//�ɹ���ʶ
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
			//����ɹ�
			//��ERP���д���
			String sql1 = "update ERPMATERIAL set ISPRETREATE = 1 where mtlNO = '" +
						  mpart.getERPNumber() + "'";
			PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			//System.out.println("sql1====" + sql1);
			int ii = pstmt1.executeUpdate();
			//�ر�����
			pstmt1.close();
			pstmt1 = null;
		}
		else
		{
			//����ʧ��
			res = "��PDM�м��PART�������ݳ���";
			flag = true;
		}
		if(flag)
		{
			//�ύ���
			//conn.commit();
			//�ر�����
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
	 * �������·�ߴ���
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
		//�ر�����
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
	 * �õ����е�ERP PART �м�����ݡ����������Ʒ����ɫ����ԭ���ϡ�ͨ��sql���û�ȡ���ݡ�
	 * ���ڲ�ͨ��sql��ȡ�ˣ�ֱ�Ӷ���������ݡ�
	 * @return ����ERPMiddlePart�ļ��ϣ�����Ķ���ʱ���������ܹ����浽PDMPART�ġ�
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
		//�������ض��󼯺�
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
	
				//System.out.println("ERP�����=====" + temp.getERPNumber());
				if(temp != null)
				{

					treatedNumber = temp.getERPNumber();
					//originalPartTable.put(temp.getERPNumber(), temp);
					
				//	System.out.println("treatedNumber=====" + treatedNumber);
					//�����Ʒ�ͳ�Ʒ���뼯��
					if( reload.isColorPart(temp.getERPNumber().trim())
								|| temp.getDefaultUnit().equalsIgnoreCase("KG"))
						{
							setPartTreated(temp.getERPNumber());
							continue;
						}
					
				
//					������㲿������ݾ���Ĺ��򴴽��ʺ�PDMPART��ERPMiddlePart����
					//��������Ű汾
					String str[] = reload.splitPartNumber(temp.getERPNumber());
					//�޷���ȡ��Ʒ�汾
					temp.setPartNumber(str[0]);
				//	System.out.println("str[0]=====" + str[0]);
					temp.setPartVersion(str[1]);
					colltemp.add(temp);
		
					//���ж��Ƿ��ǰ��Ʒ����ɫ����ԭ������������ټ���
					if(reload.isHalfPart(temp.getERPNumber().trim()))
					{
						setPartTreated(temp.getERPNumber());
						continue;
					}
												
					coll.add(temp);
					partNumberVec.add(str[0]);
					//partMap.put(arg0, str[0]);
					
				
				}
				//��ʱ
				 a=System.currentTimeMillis()-a;
			//	System.out.println("��ѯ��һ��������ʱ"+a);
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
//			String massage = "��ȡ���ϳ�������" + "\n" + "/n";
//			writeLog(massage + e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return coll;
	}
	/**
	 * ����PDM����㲿���Ƿ���Ҫ����
	 * @param ERPNumber
	 * @return 0 ����Ҫ 1 ��Ҫ 2 ���� 3 �½� 
	 * @author houhf
	 */
	public static int isNeedToTreate(ERPMiddlePart part)
	{
		ERPPartReLoad reload = new ERPPartReLoad();
		//���ж��Ƿ��ǰ��Ʒ����ɫ����ԭ������������ټ���
		if(reload.isHalfPart(part.getERPNumber().trim())
			||reload.isColorPart(part.getERPNumber().trim())
				||part.getDefaultUnit().equalsIgnoreCase("KG"))
		{
			return 1;
		}
		//��ʼ��ֱ�ţ���ֳ���źͰ汾
		String[] numberAndVersion = reload.splitPartNumber(part.getERPNumber());
//		//�������ݿ�����
//		Connection conn = null;
		try
		{
//			Class.forName("oracle.jdbc.OracleDriver").newInstance();
//			// ���������ݿ������
//			conn = DriverManager.getConnection(pdmoid, pdmuid, pdmpwd);
			
			//�������С�汾��ID�ʹ�汾��Ϣ
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
				
				//��÷���Դ�汾
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
				//�ر�����
				pstmt1.close();
				pstmt1 = null;
				rs1.close();
				rs1 = null;
				//����Դ�汾��Щ�Ǵ���С�汾��Ϣ�ģ�������Ҫ����һ��
				if(partVersion.indexOf(".")>0)
					partVersion = 
						partVersion.substring(0, partVersion.indexOf("."));
				
				int result =0;
				if(numberAndVersion[1] != null 
					&& numberAndVersion[1].trim().length()>0)
				{
					result = numberAndVersion[1].compareTo(partVersion);
				}
				
				if(result>0)//ERP�汾����PDM�汾
					return 2;
				if(result==0)//ERP�汾����PDM�汾,����·��
					return 2;
				if(result<0)//ERP�汾С��PDM�汾,��������
					return 0;
			}
			//�ر�����
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
	 * ɾ��һ�����ϱ���Ϣ
	 * @param ERPNumber ERP���
	 * @author houhf
	 */
	public static void deleteMLTByNumber(String ERPNumber)
	{

		try
		{

			String sql = "delete from ERPMATERIAL t " +
			 			 "where t.mtlNO = '"+ERPNumber+"'";
			
//			System.out.println("ɾ������SQL=====" + sql);
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
		ERPPartReLoad reload = new ERPPartReLoad();
		
	//	partMap = new HashMap();


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
//				��ó�Ʒ���
			
				String erpnumber = part.getERPNumber().trim();
				if(partNumberVec.contains(part.getPartNumber())){
					//String str[] = reload.splitPartNumber(erpnumber);
					partnumber = erpnumber;
				}
				
				
				
				
				// �Ƚ���Ʒ��Ų��뵽������
				//partMap.put( part.getPartNumber().trim(),part.getERPNumber().trim());
				//����ERP BOM�м��
				String sql = "select t.fatherMtl, t.mtl, t.groupwareNumber, " +
							 "t.SDDocItemNO, t.sortSequence, t.remark, t.BOMCTG " +
							 "from ERPBOM t " + 
							 "where t.fatherMtl = '" + part.getERPNumber().trim()+
							 "'";
				//System.out.println("���Ҹ�����====" + part.getERPNumber());
				

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
					
					//������Ҫ���������������Ʒ����ɫ����ԭ���ϵĽṹ��ϵҲ�ӽ�ȥ
					//�ж��Ƿ��ǰ��Ʒ����ɫ����ԭ����������������������������ƷΪֹ
					if(reload.isHalfPart(temp.getChildNumber().trim()) 
						|| reload.isColorPart(temp.getChildNumber().trim())
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
		ERPPartReLoad reload = new ERPPartReLoad();
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
						if(reload.isHalfPart(subNumber.trim()) 
							|| reload.isColorPart(subNumber.trim())
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
						if(reload.isHalfPart(subNumber.trim()) 
							|| reload.isColorPart(subNumber.trim())
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
					if(reload.isHalfPart(subNumber.trim()) 
						|| reload.isColorPart(subNumber.trim())
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
			"update ERPMATERIAL set ISPRETREATE = 1 where mtlNO = '"
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
	 * ���ݱ�Ŵ�ERP BOM �м���еõ����еİ��Ʒ��ԭ���ϡ�
	 * ���������ѯ���ǻ����еĽṹ��Ϣ	
	 * @param number Ҫ���ҵ��㲿�����
	 * @return hashMap key�����Ʒ����ԭ���ϵĲ㼶��value�����Ʒ����ԭ���϶�Ӧ��link
	 * @author houhf
	 */
	private static HashMap<Integer,ERPMiddleBOMLink> getAllHalfParts(String number)
	{
		ERPPartReLoad reload = new ERPPartReLoad();
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
	 * ����㲿������·����Ϣ
	 * @param part ERP�м������
	 * @return �㲿������·����Ϣ
	 * @author houhf
	 */
	public static String getPartManuRoute(ERPMiddlePart part)
	{
		ERPPartReLoad reload = new ERPPartReLoad();
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
					if(reload.isColorPart(subPart.getERPNumber().trim()))
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
	 * ���ݱ�Ŵ�ERP BOM �м���еõ����еİ��Ʒ��ԭ���ϡ�	
	 * @param number Ҫ���ҵ��㲿�����
	 * @param middelPartslinks �㲿���ṹ��
	 * @return hashMap key�����Ʒ����ԭ���ϵĲ㼶��value�����Ʒ����ԭ���϶�Ӧ��link
	 * @author houhf
	 */
	private static HashMap<Integer,ERPMiddleBOMLink> getAllHalfParts
			(String number, HashMap<Integer,ERPMiddleBOMLink> middelPartslinks)
	{
		ERPPartReLoad reload = new ERPPartReLoad();

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
     * ���bsoid
     * @param partInfo ��Ҫ������ͼ�����
     * @return partInfo ����������
     * @throws QMException
     * @author houhf
	 * @throws SQLException 
     */
//    public  static String getUid(String bso)   
//    {
//    	//˵���ǵ�һ�����г���
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
//		            	//������
//		    			uid1 = rs.getInt("bbsoid");
//	  	
//			    	}
//		    		//�ر�����
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
//		            	//������
//		            	uid2 = rs.getInt("bbsoid");
//	  	
//			    	}
//		    		//�ر�����
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
//			            	//������
//			            	uid3 = rs.getInt("bbsoid");
//		  	
//				    	}
//			    		//�ر�����
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
     * ���bsoid
     * @param bso ��������
     * @return String bsoid
     * @throws QMException
     * @author liuji
	 * @throws SQLException 
     */
    public  static String getUid(String bso)   
    {
    	//˵���ǵ�һ�����г���

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
		            	//������
		    			uid = rs.getInt("bbsoid");
	  	
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
		       
		    }
    		
	    	
	    
	    uid++;
   
    	bsoid = bso + "_" + uid;
    	
    	//System.out.println("bsoid========"+bsoid);
        return bsoid;
    }
    /**
     * ���bsoid��number
     * @throws QMException
     * @author liuji
     */
    public static void setCounter(){
    	String sql = "update jferpcounter set counterid ='"+ uid + "'";    	
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int i = pstmt.executeUpdate();
			if(i<=0){
				
					//����ʧ��
					String res = "��PDM�м��PART�������ݳ���";
					
				
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
			System.out.println("д��־�ļ���������");
			e.printStackTrace();
		}
	}
	public static String getPartType(ERPMiddlePart mPart){
		//�ж��Ƿ��Ǳ�׼��
		String typeString ="���";
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
					if(mPart.getManuRoute().equalsIgnoreCase("Э"))
					{
						
						typeString = "��׼��";
						
					}
				}
			}
		}
		return typeString;
	}
	public static int getxnj(ERPMiddlePart mPart){
		//�ж��Ƿ��Ǳ�׼��
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
	//����ֵ
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
//	�����Ͻ��в�֣�������Ʒ���
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
