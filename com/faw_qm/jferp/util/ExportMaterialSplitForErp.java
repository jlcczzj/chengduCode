/**
 * ���ɳ���QMXMLMaterialSplit.java	1.0              2007-9-27
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ��ʷ���㲿�������ϴ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
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
	public static void mainExport() throws QMException {
		try{
			System.out.println("�����˵�����ʷ���ݵķ����ˣ�����������������");
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
        	//������
        	String partNumber = result.getString("partNumber");
        	//erp���
        	String erpNumber = result.getString("erpNumber");
        	//�汾
    		String versionValue = result.getString("partversion");
    		//����·��
    		String zzRoute = result.getString("manuroute");
    		//װ��·��
    		String zpRoute = result.getString("assroute");
    		//�����
    		String virtualFlag = result.getString("dummyPart");
    		//��ɫ����ʶ
    		String colorFlag = result.getString("colorFlag");
    		//��Դ
    		String produceBy = result.getString("producedBy");
    		//����
    		String partType = result.getString("partType");

    		System.out.println("jieguo  ========"+partNumber);
    		System.out.println("jieguo  ========"+versionValue);
    		System.out.println("erpNumber  ========"+erpNumber);
    		System.out.println("zzRoute  ========"+zzRoute);
    		System.out.println("zpRoute  ========"+zpRoute);
    		partData[0]=partNumber;
    		partData[1]=versionValue;
    		//��;�ż�����·��
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
	 * ���� 1�����ݿ������10.43.4.103��sid=orcl���û���pm_pdm������jfgspmpdm��
	 * 2�����û����ж�10.43.4.103�ϵ��û�faw�� pm_pdm_info �����insert��select��updateȨ�ޡ�
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
			System.out.println("���ܻ�ȡ����");
			try
			{
				Thread.sleep(60000);
			}
			catch(Exception ee)
			{
				ee.printStackTrace();
			}
			
			return getConnection();
			//throw new SQLException("���ܻ�ȡ����");
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
		//���ÿһ���㲿�����д���
		for(int i = 0; i < list.size(); i++)
		{
			//�������·��
			String routeAsStringQian="";
			String partNumber ="";
			String partVersionValue="";
			String[] partQuan=(String[])list.get(i);
			//�㲿�����
			partNumber=partQuan[0];
			//�㲿���汾
			partVersionValue=partQuan[1];
			//erp���
			String erpNumber = partQuan[2];
//			����·��
			String routeAsString = partQuan[3];
//			ȫ·�ߴ�
			String routeAllCode = partQuan[3]+";"+partQuan[4];
//			װ��·��
			String routeAssemStr = partQuan[4];
			//�����
    		String virtualFlag = partQuan[5];
    		//��ɫ����ʶ
    		String colorFlag = partQuan[6];
    		//����
    		String partType = partQuan[8];
    		//List routeCodeList = getRouteCodeList(routeAsString);
	          this.partList.put(partNumber,partQuan);
	          List usageLinkList = getSubPartsAndLinks(partNumber);
			String erpPartNumber = partNumber + "/" + partVersionValue;
			String materialNumber =  partHelper.getMaterialNumberHistory(partQuan);
//			�������·�߲�Ϊnull����������·��Ϊ�ո񣬼�����·��Ϊ��
			if(routeAsString == null || routeAsString.toString().equals(""))
			{
				logger.debug("·��Ϊ�գ����������㲿��");
				MaterialSplitIfc mSplitIfc = new MaterialSplitInfo();
				mSplitIfc.setRootFlag(true);
				//hamap.put(partIfc.getMasterBsoID(), mSplitIfc);
				mSplitIfc.setPartNumber(erpPartNumber);
				mSplitIfc.setPartVersion(partVersionValue);
				mSplitIfc.setState("����׼��");
				mSplitIfc.setSplited(false);
				mSplitIfc.setRoute(routeAllCode);
				mSplitIfc.setIsMoreRoute(false);
				//�����
				if(virtualFlag.equals("Y"))
					mSplitIfc.setVirtualFlag(true);
				else if(virtualFlag.equals("N"))
					mSplitIfc.setVirtualFlag(false);
				
					//��ɫ����ʶ
				if(colorFlag.equals("Y"))
					mSplitIfc.setColorFlag(true);
				else if(colorFlag.equals("N"))
					mSplitIfc.setColorFlag(false);
				
//				�������Ӽ��б�Ϊ�գ���status����Ϊ2����������Ϊ0	
				if(usageLinkList != null && usageLinkList.size() > 0)
					mSplitIfc.setStatus(2);
				else
					mSplitIfc.setStatus(0);
				
				setMaterialSplit(partQuan, mSplitIfc);
				//CCBegin by dikefeng 20100419��ERPʵʩ��Ա����ͻ���̸������������������ù���
				//��Ϊ������·����װ��·�� ��Ϊ��ʱ����Ҫ�ж��ǲ�����ɫ�������Խ������������λ�ú��Ƶ�����ɫ��֮��
				if(routeAssemStr==null||routeAssemStr.trim().length()==0)
				{

					mSplitIfc.setVirtualFlag(true);
				}
				

				mSplitIfc.setMaterialNumber(materialNumber);

//				����Ϊʲôֱ�Ӿ���N�أ�questions
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
								// xiebӦ������Ϊint�͡�
						mStructureIfc.setLevelNumber("0");
						mStructureIfc.setDefaultUnit("��");
						mStructureIfc.setMaterialStructureType("N");
								// ������ѡװ�����롣
								// materialStructureIfc.setOptionFlag(childMSfc.getOptionCode());
								//mStructureIfc = (MaterialStructureIfc) pservice.saveValueInfo(mStructureIfc);
						this.xmlMatStructList.add(mStructureIfc);
					}
				}
			} else
			{
//				��Ҫ�������ϲ�ֵ�����£����Ƚ�����·�߷ֽ�Ϊһ����·���ַ�
			
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
//					�����·�ߴ��е����һ��·�ߵ�λ
					if(m == routeCodeList.size() - 1)
					{

					    materialNumber	= materialNumber;
						
						mSplitIfc.setRootFlag(true);
			
					}

					else{
						materialNumber =  materialNumber + dashDelimiter + makeCodeNameStr;	
					}
					mSplitIfc.setMaterialNumber(materialNumber);
						//�����
						if(virtualFlag.equals("Y"))
							mSplitIfc.setVirtualFlag(true);
						else if(virtualFlag.equals("N"))
							mSplitIfc.setVirtualFlag(false);
						
							//��ɫ����ʶ
						if(colorFlag.equals("Y"))
							mSplitIfc.setColorFlag(true);
						else if(colorFlag.equals("N"))
							mSplitIfc.setColorFlag(false);
						mSplitIfc.setPartNumber(erpPartNumber);
						mSplitIfc.setPartVersion(partVersionValue);
						mSplitIfc.setState("����׼��");
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
											// xiebӦ������Ϊint�͡�
									mStructureIfc.setLevelNumber("0");
									mStructureIfc.setDefaultUnit("��");
									mStructureIfc.setMaterialStructureType("N");
											// ������ѡװ�����롣
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
						//�����㲿������������������Ҫ������������Ϣ
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
//			    		    	   System.out.println("3��û����");�������һ�û���ң�����ֱ�Ӵ򿪼���
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
//			    		    	   System.out.println("3��û����");�������һ�û���ң�����ֱ�Ӵ򿪼���
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
			    //�Ѿ����жϺ���Ҫȥ�������ϴ��б���ɾ��
			    for(int k=co.size()-1;k>=0;k--)
			    {
			     Integer ii=(Integer)co.elementAt(k);
			     int i3=ii.intValue();
			     mSplitList.removeElementAt(i3);
			    }
				//CCEnd by chudaming 20100127
				for(Iterator iter1=mSplitList.iterator();iter1.hasNext();)
				{
//					questions������㲿������·�߱仯��Ҫ���²�֣���������ĳ�������ϵ�����û�з����仯����ôҲ����Щ��������ΪU��ʶ
					MaterialSplitIfc ifc=(MaterialSplitIfc)iter1.next();
					//CCBegin by dikefeng 20100419,���ﱣ���˱��η������ݵı�ʶ
					
						ifc.setMaterialSplitType("N");
					
//					������������в�����ϵı��� dikef ?????
//					ifc = (MaterialSplitIfc)pservice.saveValueInfo(ifc);
					this.xmlMatSplitList.add(ifc);
				}
				//CCBegin by dikefeng 20100419������ط���ԭ���У��������ڲ���ʹ�õ����ϴ�ɾ�����

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
						// 1-���¼����ϡ�
						if (childMSfc != null && parentMSIfc.getStatus() == 1) {
//							if (!hasSplitedStructure(parentMSIfc, childMSfc)) {
								logger.debug("1-���¼����ϡ�");
								mStructureIfc.setParentPartNumber(parentMSIfc
										.getPartNumber());
								mStructureIfc.setParentPartVersion(parentMSIfc
										.getPartVersion());
								mStructureIfc.setParentNumber(parentMSIfc
										.getMaterialNumber());
								mStructureIfc.setChildNumber(childMSfc
										.getMaterialNumber());
								mStructureIfc.setQuantity(1);
								// xiebӦ������Ϊint�͡�
								mStructureIfc.setLevelNumber(String.valueOf(p));
								mStructureIfc.setDefaultUnit(childMSfc
										.getDefaultUnit());
								// ������ѡװ�����롣
								// materialStructureIfc.setOptionFlag(childMSfc.getOptionCode());
								//mStructureIfc = (MaterialStructureIfc) pservice.saveValueInfo(mStructureIfc);
								this.xmlMatStructList.add(mStructureIfc);
							
						}
						// 2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���
						else if (parentMSIfc.getStatus() == 2) {
							logger.debug("2-��ײ����ϡ�");
						}
						// 0-��ײ����ϡ�
						else if (parentMSIfc.getStatus() == 0) {
							logger.debug("0-��ײ����ϡ�");
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
		//CCBegin by dikefeng 20090422��Ϊ��ʹ���ڷ�����ʱ��֪�������·������϶�����Щ����Ҫ������ѹ��˺������嵥����һ��
		return filterPartMap;
		//CCEnd by dikefeng 20090422
	}
	private void writeDoc() throws QMException
	{
		try{
			Document doc = DocumentHelper.createDocument();
		    OutputFormat format = OutputFormat.createPrettyPrint();
	        //����XML�������͡�
	        format.setEncoding("gb2312");
	        //ָ����XML�ļ�����ʹ�õ���ʽ�������ͼ��ļ�����
	        doc.addProcessingInstruction("xml-stylesheet",
	                "type=\"text/xsl\" href=\"OutPut_Data.xsl\"");
//	        ���ø���Ϣ
	        DocumentType docType = new DefaultDocumentType();
	        docType.setElementName("bom");
	        docType.setInternalDeclarations(getInternalDeclarations());
	        doc.setDocType(docType);
//	        д�ļ�ͷ��Ϣ
	        doc.addElement("bom");
	        Element elem = doc.getRootElement().addElement("description");
            elem.addElement("filenumber").addText("��ʷ���ݷ�������MES����ϵͳ�汾��·�ߴ�����ǰΪ�ļ���"+fileNumber);
            elem.addElement("type").addText("������ϵͳ�汾����");
            elem.addElement("date").addText("��ʷ����ʱ�䲻���д���");
            elem.addElement("sourcetag").addText("���շ�����");
            elem.addElement("notes").addText("������"+xmlMatSplitList.size()+"������Ϊmpart�����ݡ�������"+xmlMatStructList.size()+"������Ϊstructure�����ݡ�");

//            ��д��ͷ
            Element table = doc.getRootElement().addElement("table");
            table.addAttribute("name", "mpart");
            addTablePartColHeader(table);
            PersistService pService=(PersistService)EJBServiceHelper.getPersistService();
            String lastpartnumber="";
//          ��ʼд��������
		    for(int i=0;i<xmlMatSplitList.size();i++)
		    {
		    	
		    	Element recordElem = table.addElement("record");
		    	MaterialSplitIfc matSplit=(MaterialSplitIfc)xmlMatSplitList.get(i);
//		    	System.out.println("�����============"+matSplit.getMaterialNumber());
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
		    			//CCBegin by chudaming 20100809 ��XML�ļ��У��и����������á��ֶΣ�CAPPϵͳ�ڷ�����ʷ����ʱ��û�н�����Ϣ��������
//		    			this.ibaValueMap.put(svi.getBsoID(), svi.getValue());
		    			this.ibaValueMap.put(svi.getDefinitionBsoID(), svi.getValue());
		    			//CCEnd by chudaming 20100809 ��XML�ļ��У��и����������á��ֶΣ�CAPPϵͳ�ڷ�����ʷ����ʱ��û�н�����Ϣ��������
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
		    	recordElem.addElement("col").addAttribute("id","5").addText("��");
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
//		    ��ʼд���Ͻṹ��ͷ����
		    Element bomtable = doc.getRootElement().addElement("table");
		    bomtable.addAttribute("name", "structure");
            addTableBomColHeader(bomtable);
//		    ��ʼд���Ͻṹ����������
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
		    	recordElem.addElement("col").addAttribute("id","5").addText("��");
		    	recordElem.addElement("col").addAttribute("id","6").addText("false");
		    }
//		    ��ʼд�ļ�
		    //����
//		    String ppString="/cappapp/qmcapp/phosphor/support/loadFiles/xml/erpFiles/bomList/";
		    //����
//		    String ppString="/qmcappapp/qmcapp/phosphor/support/loadFiles/xml/erpFiles/bomList/";
		    //�Լ�
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
		    String xmlFileName=str8+"��ʷ�����ļ�"+fileNumber;
		    File path = new File(ppString);
		    
	        if(!path.exists())
	        {
	            path.mkdir();
	        }
	        File file=new File(ppString + xmlFileName+".xml");
	        if(!file.exists()){
	        	//��Ŀ��·��д��XML�����ļ���
	            XMLWriter writer = new XMLWriter(
	                    new FileWriter(ppString + xmlFileName+".xml"), format);
	            writer.write(doc);
	            writer.flush();
	            writer.close();
	        }
	        else{
	        	System.out.println("�������ļ�"+xmlFileName+fileNumber+"ͬ�����ļ�");
	        	throw new QMException("�������ļ�"+xmlFileName+fileNumber+"ͬ�����ļ�");
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
			//�ɼ�����ʶֻ�� ��������
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
    	//CCBegin by chudaming 20100524 3105912-X41��·��Ϊ��-��-��=�֣�3105912-X41-CG��װ��·��ӦΪ����������ӦΪ���ᡯ�������᲻�����
    	String aj = getRoute(ms);
        String a = "";
        if(aj.indexOf("��") > 0 && aj.indexOf("(��)") < 0)
        {
            String y = aj.substring(0, aj.indexOf("��") - 1);
            String z = aj.substring(aj.indexOf("��") + 1, aj.length());
            a = y + z;
        } else
        if(aj.indexOf("��") == 0){
            a = aj.substring(2, aj.length());
        }
        else{
            a = aj;
        }
    	//CCBegin by chudaming 20100506 Ϊ��ȡ��s_route����ѯ���ݿ�
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

//    		�޸Ŀ�ʼ
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
    		//CCBegin by chudaming 20100524 8507042-Q630��·��Ϊ��-��(��)-��(��)-��(��)-��=�ݣ�8507042-Q630-SC-1��װ��·��ӦΪ�������ͣ�-��������ӦΪ�������ͣ�����8507042-Q630��װ��·��ӦΪ���ݡ�����ӦΪ���ͣ�
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
//	      //��ó־û�����
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
     * ��ȡDTD������Ϣ�б�
     * @return List DTD������Ϣ�б�
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
	 * ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
	 * 
	 * @param partIfc
	 *            �㲿����
	 * @throws QMException
	 *             return PartUsageLinkIfc�Ķ��󼯺ϡ�
	 */
	private final List getUsageLinks(final QMPartIfc partIfc)
	throws QMException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUsageLinks(QMPartIfc) - start"); //$NON-NLS-1$
			logger.debug("������partIfc==" + partIfc);
		}
		// ͨ��part��ѯ������PartUsageLink������һ��PartUsageLinkIfc�Ķ��󼯺ϡ�
		List usesPartList = new ArrayList();
		try {
			StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			usesPartList = (List)spservice.getUsesPartMasters(partIfc);
			
		} catch (QMException e) {
			Object[] aobj = new Object[] { partIfc.getPartNumber() };
			// "��ȡ��Ϊ*���㲿���ṹʱ����"
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
	 *���������һ����������㲿�����Ӽ��Լ����Ӽ���Ĺ���
	 * @author �ҿƷ�
	 * @param partIfc �㲿��
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
        	//�������
        	String parentNumber = result.getString("parentNumber");
        	//�Ӽ����
        	String childNumber = result.getString("childNumber");
        	//����
    		String bomNumber = result.getString("bomNumber");
    		
    		System.out.println("jieguo  ========"+parentNumber);
    		System.out.println("jieguo  ========"+childNumber);
    		System.out.println("erpNumber  ========"+bomNumber);

    		partData[0]=parentNumber;
    		partData[1]=childNumber;
    		//��;�ż�����·��
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
	 * ����������Ҫ���������ԣ������ϲ�ּ��ṹ�޹أ���������ʱ���Ƶ�ÿ����ֺ�����ϼ�¼�С�
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
			logger.debug("������partIfc==" + partQuan);
			logger.debug("������mSplitIfc==" + mSplitIfc);
		}
		//�㲿�����
		String partNumber=partQuan[0];
		//�㲿���汾
		String partVersionValue=partQuan[1];
		//erp���
		String erpNumber = partQuan[2];
//		����·��
		String routeAsString = partQuan[3];
//		ȫ·�ߴ�
		String routeAllCode = partQuan[3]+";"+partQuan[4];
//		װ��·��
		String routeAssemStr = partQuan[4];
		//�����
		String virtualFlag = partQuan[5];
		//��ɫ����ʶ
		String colorFlag = partQuan[6];
		//��Դ
		String proceduy = partQuan[7];
		//����
		String partType = partQuan[8];
		String erpPartNumber = partNumber + "/" + partVersionValue;
		mSplitIfc.setPartName(erpPartNumber);
		mSplitIfc.setDefaultUnit("��");
		mSplitIfc.setProducedBy(proceduy);
		mSplitIfc.setPartType(partType);
		StringBuffer buffer = new StringBuffer();
		Timestamp time = new Timestamp(System.currentTimeMillis());
		mSplitIfc.setPartModifyTime(time);
		mSplitIfc.setOptionCode("");
		

	     
	      if(colorFlag==null||colorFlag=="N")
	    	  mSplitIfc.setColorFlag(false);
	      else{
	    	  //ֻ��������ϴ��ʶ
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
	 * ��·���е�·�ߴ���ת��ΪList,���Ҹ��������ļ�������ȥ��·���е�������롣
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
			String  a= "��-��-��=��;��";
			System.out.println("aaaaaaaaa="+a.substring(0,a.indexOf(";")));
			//e.mainExport();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

}
