/**
 * ���ɳ���ʱ�� 2016-5-16 �汾 1.0 ���� 
 * ��� ��Ȩ��һ��������˾���� ��������һ��������˾��˽�л�Ҫ���� δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ����� ��������Ȩ�� 
 * SS1 �ɶ����빤��BOMʱ������汾���˴��޸ĵ��ǲ�ѯ�㲿��ʱ�������� liuyuzhu 2016-10-13 
 * SS2 �����㲿���������㲿����������״̬Ϊ������׼���� liuyuzhu 2016-10-21
 * SS3 ����㲿������ȡ����BOM�ṹ���棬���ٻ�ȡ���BOM����ȡ����BOM�ṹ����Ϊ����ǰ������Ч������һ�׽ṹ�� liunan 2016-12-6����Ҫ
 * SS4 A032-2016-0137 1�� ����BOM �ļ�����ʱ�������ļ����������С�/����İ汾��������Ӧ��BOM�ṹ 2��
 * ����㲿�������汾����ṹ�е���A�汾����� 3�� ����ļ��а汾����PDMϵͳ������汾�򲻵��룬��������ʾ 4��
 * �������BOM��ϵͳ���Ѿ����ڣ������Ѿ�������������δ�������������� 5��
 * �������BOM��ϵͳ���Ѿ����ڣ�����δ�������������룬ɾ��δ�����ṹ���� 
 * SS5 �㲿���ȽϽ�����ڴ��� liunan 2016-12-28
 * SS6 ��������¼ liuyuzhu 2017-04-27
 * SS7 ��ʼ������BOM ���� 2017-5-3
 * SS8 �������saveBom(Collection ytreenode)��������Ҫ�����жϲ��ܱ�����ǩ liunan 2017-5-20
 * SS9 ���浥���滻���Ĺ���BOM liuyuzhu 2017-05-19
 * SS10 ���Ż�ȡ��ǰ�û����������� liunan 2017-5-24
 * SS11 �޸��б���������С�������� liuyuzhu 2017-05-26
 * SS12 ��ʼ��BOMʧ������(����ƽ̨A004-2017-3547) lis 2017-06-09
 * SS13 ��ʼ��BOMʧ������(����ƽ̨A004-2017-3566) lis 2017-06-09
 * SS14 ��ʼ��BOMʱ�򣬹���bom�ܳ��޷�չ����һ���� ������ 2017-06-19
 * SS15 �����Ƿ���ṹ�Ѵ��ڵĲ�����������ʱ������Ҫ����δ��Ч�ṹ�� liunan 2017-6-26
 * SS16 �������ʧ�ܺ���ʾ���� (����ƽ̨A004-2017-3561)2017-06-29
 * SS17 A004-2017-3584 ��ȡ·�߲�׼���� liunan 2017-6-29
 * SS18 A004-2017-3588 bom�������BOM�������޸ģ�����ֻ�ĵ�ǰһ��ṹ�� liunan 2017-6-29
 * SS19 A004-2017-3585 ����BOM�������� �㼶����� liunan 2017-6-30
 * SS20 �㲿����� (����ƽ̨A004-2017-3562)2017-07-03
 * SS21 ��ʼ��bom֮�󣬲����߼��ܳ��ǳɶ������ģ������ڽ�Ź���bom�Ҳ�����˳ɶ����߼��ܳɽṹ���˹�����Ҫȥ���� (����ƽ̨A004-2017-3578)2017-07-03
 * SS22 ���������¾ɹ���Աȡ�Ϊ�û���������� lishu 20170707
 * SS23 1000410�ṹ����Ҫ��ȡ1308G02��1313G02�߼��ܳ� lishu 20170707
 * SS24 ���ʱ����JTA��ͷ�ľ�ȥ�����������ĳ�ʼ�� lishu 20170707
 * SS25 3725020-A01��8203010-E06C��7925040-92W��7901012EA01�ŵ�3900G01�£���3103070��ͷ�ķŵ�3100G00�� lishu 20170707
 * SS26 �޸�ԭ������ ����·�߲������ܡ���װ��·�ߺ����ܡ�ʱ������ȡ�Ӽ� ������ 2017-07-10
 * SS27 ԭ�������жϲ���ȷ������Ҫ�ж��Ӽ�·��  ������ 2017-07-11
 * SS28 A004-2017-3594 �������Զ���ֹ��ܲ���ȷ��ϵͳԭ�������Զ����в��940�Զ�����β���� f00�� �㲿�������ڴ˹���û����  lishu 20170713
 * SS29 ��Ӱ汾"version"��Ϣ liuyuzhu 2017-07-13
 * SS30 ��ѯ��������ǩ liuyuzhu 2017-07-20
 * SS31 J6P��1311G01��3406G01����940��  lishu 20170724
 * SS32 3103070��̥��ĸû��ȡ�������� ��������±������ǰ������Ƿŵ�3100G00�µģ����ڸĵ��ŵ����£� lishu 20170724
 * SS33 5000990\2800010�Ĵ�����ʱȥ��
 * SS34 ����BOM���� liuyuzhu 2017-07-25
 * SS35 ��������Ǵ��ݲ����ķָ�����Ϊ��#@�� liuyuzhu 2017-08-07
 * SS36 ���ҹ�����ǩ�ظ����� liuyuzhu 2017-08-07
 * SS37 ������ʼ������BOM�����޸�
 * SS38 ����1000940�ϼ�ʱ������ָ���Ĺ���·��--����·�ߣ��ܣ����͡�װ��·�ߣ��ܣ� liuyuzhu 2017-08-09
 * SS39 ��ʼ�������滻�̶������������ liuyuzhu 2017-08-16
 * SS40 �޸Ľṹ�ȽϷ��� liuyuzhu 2017-08-22
 * SS41 ������ʼ������ lishu 2017-08-30
 * SS42 �޸����а汾��ʾ����Ϊ�������İ汾�� liuyuzhu 2017-08-24
 * SS43 ������ʼ�������޸� lishu 2017-09-05
 * SS44 �޸Ļ�ȡ·�ߵķ�������ȡ���£����ǻ�ȡ����δȡ��·�ߵĺϼ��� liunan 2017-9-5
 * SS45 �߼��ܳ��жϹ�����ԭ����->��Ϊ-- ������ 2017-09-5
 * SS46 �޸����BOM�洢��Ϣ���� liuyuzhu 2017-09-06
 * SS47 �߼��ܳ��жϹ����޸� liuyuzhu 2017-09-07
 * SS48 ��ʼ������ʱ�����ù���û��·�ߵ��߼��ܳɴ�����·�� liuyuzhu 2017-09-07
 * SS49 �޸Ĵ��������������������һ��������Ϊ����·�߱���׼���� liuyuzhu 2017-09-09
 * SS50 ��ʼ��ʱ5001G01-13C���ʻ�����5001G01BA03��ǰ7λ��ͬ����Ҫ����ʻ�����-�ã����߼��ܳɷŵ������� liuyuzhu 2017-09-10
 * SS51 ֻ��δ��Ч����Ч�ṹ�����ڵ�ʱ��ſ����������¼�������� liuyuzhu 2017-09-11
 * SS52 ����BOM�б�ı����¼ liuyuzhu 2017-09-11
 * SS53 �߼��ܳɱ�Ų�Ҫ��ˮ�� liuyuzhu 2017-09-12
 * SS54 �޸Ĵ洢·�ߣ���Ϊ�������岿��·�ߣ� liuyuzhu 2017-09-12
 * SS55 ������ʼ������Ҫ��ź�ӡ�01�� liuyuzhu 2017-09-12
 * SS56 ����ܳɵĵ��� liuyuzhu 2017-09-12
 * SS57 �����ָ������ liuyuzhu 2017-09-13
 * SS58 �������ܳɱ������� liuyuzhu 2017-09-16
 * SS59 �����滻��ʱδ�޸��Ӽ���ţ��������������⣬���޸Ĵ����� liuyuzhu 2017-09-16
 * SS60 3103070��̥��ĸ����ȡ�뱣�� liuyuzhu 2017-09-16
 * SS61 ����BOM�����б�ȥ���汾�У����汾������Ž��кϲ� maxiaotong 2017-09-20
 * SS62 ����һ�������Ϲ����޸� liuyuzhu 2017-09-20
 * SS63 �������ڴ�����-FC����ͬʱҲ��Ҫ����һ����-ZC���ļ� liuyuzhu 2017-09-21
 * SS64 ��ѯ����ָ������ liuyuzhu 2017-09-22
 * SS65 �����ק�ټ����� liuyuzhu 2017-09-25
 * SS66 �ȽϽṹ���� liuyuzhu 2017-10-19
 * SS67 �ֶ�����ܳɵĺ�׺Ϊ��-FC�� 2017-10-19
 * SS68 ����BOM��Чʱ�������ݿ��ġ�bz1���ֶ������ liuyuzhu 2017-10-19
 * SS69 ��ѯ����BOM�ṹʱ���޸���ɫ�ж����� liuyuzhu 2017-10-19
 * SS70 ���ж�BOM���޸Ļ�������ı�ڵ���ɫ liuyuzhu 2017-10-24
 * SS71 �滻�����������仯 liuyuzhu 2017-11-02
 * SS72 �����㲿�� liuyuzhu 2017-11-13
 * SS73 ���ܳ�ʼ�������޸� liuyuzhu 2017-12-11
 * SS74 ��ʻ�ҳ�ʼ�������޸� liuyuzhu 2017-12-13
 * SS75 �ɶ�Ҫ����BOM��ʾ˳���ǵ���˳�� ������ 2017-12-8
 * SS76 ����BOM�ͻ��˵��빦�� ������ 2017-12-8
 * SS77 ����BOM��Чʱ�������ݿ��ġ�bz1���ֶ������'green' ������ 2017-12-17
 * SS78 ��ѯ·��ʱ�����in����ﳬ��һǧ��ֵ���򱨴� ������ 2017-12-17
 * SS79 �����б����б�����޸ģ�����Ϊ������� liuyuzhu 2017-12-20
 * SS80 У�鹤��bom�Ƿ�༭·�߲�����  ������ 2017-12-22
 * SS81 ����BOM���˳�򣬰�id��������  ������ 2017-12-25
 * SS82 ����������Ӱ�����Դ�汾����  ������ 2017-12-27
 * SS83 ����excell����ȥ���汾�İѰ汾��ȥ����  2018-01-03
 * SS84 ����BOM��Ҫ�ж��ް汾����  2018-01-07
 */
package com.faw_qm.gybom.ejb.service;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.faw_qm.cappclients.capproute.web.ViewRoute;
import com.faw_qm.cappclients.capproute.web.ViewRouteLevelOne;
import com.faw_qm.codemanage.ejb.service.CodingManageService;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.doc.util.DocServiceHelper;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.gybom.util.GYBomHelper;
import com.faw_qm.jferp.util.PartHelper;
import com.faw_qm.jfpublish.receive.PublishHelper;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartDebug;
import com.faw_qm.part.util.PartServiceHelper;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.part.util.Unit;
import com.faw_qm.pcfg.family.model.GenericPartIfc;
import com.faw_qm.pcfg.family.model.GenericPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.ration.util.RationHelper;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.route.model.RouteBranchNodeLinkIfc;
import com.faw_qm.technics.route.model.RouteBranchNodeLinkInfo;
import com.faw_qm.technics.route.model.RouteNodeIfc;
import com.faw_qm.technics.route.model.RouteNodeInfo;
import com.faw_qm.technics.route.model.RouteNodeLinkIfc;
import com.faw_qm.technics.route.model.RouteNodeLinkInfo;
import com.faw_qm.technics.route.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.route.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.route.model.TechnicsRouteIfc;
import com.faw_qm.technics.route.model.TechnicsRouteInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.technics.route.model.TechnicsRouteListMasterIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListMasterInfo;
import com.faw_qm.technics.route.util.RouteCategoryType;
import com.faw_qm.technics.route.util.RouteHelper;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.jf.ejb.service.JFService;
import com.jf.ejb.service.JFServiceEJB;
import com.faw_qm.erppartreload.util.ERPPartReLoad;
public class GYBomServiceEJB extends BaseServiceImp
{
	private static final boolean VERBOSE=(RemoteProperty.getProperty("com.faw_qm.gybom.verbose","true")).equals("true");
	private HashMap svmap = null;
	private String ibabs  = null;
	/**
	 * Method��getServiceName
	 * <p>
	 * ��÷�������
	 * 
	 * @return String ��������
	 */
	@Override
	public String getServiceName()
	{
		return "GYBomService";
	}

	/**
	 * �������BOM����
	 * 
	 * @param Collection
	 *            mtreenode Ŀ����������BOM���ڵ㼯�� ����ʱҪ��ȡ��ǰ�û����ڵ�λ��Ȼ��ֱ��ͨ�����ݿⴴ����
	 *            id+";"+pId
	 *            +";"+num+";"+name+";"+pname+";"+quantity+";"+zz+";"+zp
	 *            +";"+dw+";"+color+";"+createUser;
	 */
	public String saveBom(String ytreenode)
	{
//		System.out.println("come in saveBom ytreenode=="+ytreenode);
		String s="";
		Collection ytree=new ArrayList();
		try
		{
			UserInfo user=getCurrentUserInfo();
			String[] partStr=ytreenode.split(",");
			String carModelCode=partStr[0];
			String dwbs=partStr[1];

			// deleteBom(carModelCode, dwbs, user.getBsoID());

			for(int i=2;i<partStr.length;i++)
			{
				String pstr=partStr[i];
				if(pstr!=null&&!pstr.equals(""))
				{
					String[] arrStr=pstr.split(";");
					if(!arrStr[1].equals("0"))
					{
						// parentPart,childPart,quantity,createTime,modifyTime,carModelCode,dwbs,createUser,color,unit,childNumber
						String[] str=new String[9];
						str[0]=arrStr[1];
						str[1]=arrStr[0];
						str[2]=arrStr[5];
						str[3]=carModelCode;
						str[4]=dwbs;
						if(arrStr[10].equals("black"))
						{
							arrStr[10]="";
						}
						if(arrStr[9].equals("red"))// ��ɫ�����û���ק��
						{
							if(arrStr[10].indexOf(user.getBsoID())!=-1)// �������û�������ԭֵ
							{
								str[5]=arrStr[10];
							}
							else
							// ���������û������Ӹ��û�
							{
								str[5]=arrStr[10]+user.getBsoID();
							}
						}
						else
						// û�Ϲ�������ԭֵ
						{
							str[5]=arrStr[10];
						}
						str[6]="";
						str[7]=arrStr[8];
						str[8]=arrStr[2];
						// System.out.println(str[0]+"---"+str[1]+"---"+str[2]);
						if(str[0]==null||str[0].equals("null"))
						{
							continue;
						}
						ytree.add(str);
					}
				}
			}
			saveBom(ytree);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * �������BOM����
	 * 
	 * @param Collection
	 *            ytreenode Դ�������BOM����������
	 * @param Collection
	 *            links �������ϣ�ÿ��Ԫ����һ����ά���飬�ֱ��Ǹ���bsoID���Ӽ�bsoID��������
	 *            ����ʱҪ��ȡ��ǰ�û����ڵ�λ��Ȼ��ֱ��ͨ�����ݿⴴ����
	 */
	public void saveBom(Collection ytreenode)
	{

//		System.out.println("come in saveBom");
		Connection conn=null;
		Statement st=null;
		//CCBegin SS8
		Statement st1=null;
		ResultSet rs=null;
		//CCEnd SS8
		try
		{
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			st1=conn.createStatement();
			String ytsql="INSERT INTO GYBomStructureLable (parentPart,childPart,quantity,createTime,modifyTime,carModelCode,dwbs,createUser,color,unit,childNumber) VALUES ";
			String tempsql="";
			String sendData=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			//CCBegin SS8
			String exitsstr="";
			if(ytreenode!=null)
			{
				Iterator iter=ytreenode.iterator();
				while(iter.hasNext())
				{
					String[] yt=(String[])iter.next();
//					System.out.println("�����С======="+yt.length);
					//CCBegin SS8
					String countIdSql="select count(*) from GYBomStructureLable where parentPart='"+yt[0]+"' and childPart='"+yt[1]+"' and carModelCode='"+yt[3]+"'";
					rs=st.executeQuery(countIdSql);
					rs.next();
					int countList=rs.getInt(1);
//					System.out.println("countIdSql==="+countIdSql);
//					System.out.println("countList==="+countList);
					//1���������Ӽ��������ڵ��������insert����һ������
					if(countList>0)
					{
						exitsstr="("+yt[0]+" "+yt[1]+"),"+exitsstr;
						String countNoUserSql = "select count(*) from GYBomStructureLable where createuser is null and parentPart='"+yt[0]+"' and childPart='"+yt[1]+"' and carModelCode='"+yt[3]+"' and dwbs='"+yt[4]+"'";
						//CCBegin liuyuzhu
//						rs=st1.executeQuery(countNoUserSql);
						rs=st.executeQuery(countNoUserSql);
						//CCEnd liuyuzhu
//						System.out.println("countNoUserSql==="+countNoUserSql);
						rs.next();
						int countList1=rs.getInt(1);
//						System.out.println("countList1==="+countList1);
						//2���������Ӽ����ڣ������û�Ϊ�յ�����£���Ҫ���û���Ϊ��ǰ�û� �Ƿ���Ҫ�ж�carModelCode����Ҫ������carModelCode=yt[3]
						if(countList1>0&&yt[5]!=null)
						{
							UserInfo user=getCurrentUserInfo();
							String updatesql="update GYBomStructureLable set createUser='"+yt[5]+"' where dwbs='"+yt[4]+"' and parentPart='"+yt[0]+"' and childPart='"+yt[1]+"' and carModelCode='"+yt[3]+"'";
//					    System.out.println("updatesql=="+updatesql);
					    //CCBegin liuyuzhu
//					    st1.executeQuery(updatesql);
					    st1.addBatch(updatesql);
					    //CCEnd liuyuzhu
						}
//						continue;
					}else{
					//CCEnd SS8
//						System.out.println("����һ�����ݣ�����");
						//CCBegin SS46
//						tempsql=ytsql+"('"+yt[0]+"', '"+yt[1]+"', '"+yt[2]+"', to_date('"+sendData
//								+"','yyyy-mm-dd hh24:mi:ss'), to_date('"+sendData+"','yyyy-mm-dd hh24:mi:ss'), '"+yt[3]+"', '"
//								+yt[4]+"', '"+yt[5]+"', '"+yt[6]+"', '"+yt[7]+"', '"+yt[8]+"')";
						if(yt.length==9){
							tempsql=ytsql+"('"+yt[0]+"', '"+yt[1]+"', '"+yt[2]+"', to_date('"+sendData
								+"','yyyy-mm-dd hh24:mi:ss'), to_date('"+sendData+"','yyyy-mm-dd hh24:mi:ss'), '"+yt[3]+"', '"
								+yt[4]+"', '"+yt[5]+"', '"+yt[6]+"', '"+yt[7]+"', '"+yt[8]+"')";
						}
						if(yt.length==10){
							tempsql=ytsql+"('"+yt[0]+"', '"+yt[1]+"', '"+yt[2]+"', to_date('"+sendData
									+"','yyyy-mm-dd hh24:mi:ss'), to_date('"+sendData+"','yyyy-mm-dd hh24:mi:ss'), '"+yt[3]+"', '"
									+yt[4]+"', '"+yt[5]+"', '"+yt[6]+"', '"+yt[8]+"', '"+yt[9]+"')";
						}
						//CCEnd SS46
//						System.out.println("tempsql1=="+tempsql);
						//CCBegin liuyuzhu
	//					st.addBatch(tempsql);
						st1.addBatch(tempsql);
					}
				}
//				st.executeBatch();
				st1.executeBatch();
				//CCEnd liuyuzhu
				//CCBegin SS8
//				if(!exitsstr.equals(""))
//				{
//					System.out.println("saveBom �����Ѵ��ڽṹ��"+exitsstr);
//				}
				//CCEnd SS8
			}
			// �ر�Statement
			st.close();
			//CCBegin SS8
			st1.close();
			rs.close();
			//CCEnd SS8
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				st.close();
				//CCBegin SS8
				st1.close();
				rs.close();
				//CCEnd SS8
				conn.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * �������BOM����
	 * 
	 * @param Collection
	 *            mtreenode �������ϣ�ÿ��Ԫ����һ����ά���飬�ֱ��Ǹ���bsoID���Ӽ�bsoID�������롢������
	 */
	public void updateBom(Collection ytreenode,String userID) throws QMException
	{
		Connection conn=null;
		Statement st=null;
		Statement st1=null;
		ResultSet rs=null;
		try
		{
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			st1=conn.createStatement();
			String updatesql="";
			String selectsql="";
			String createUser="";

			if(ytreenode!=null)
			{
				Iterator iter=ytreenode.iterator();
				while(iter.hasNext())
				{
					String[] subs=(String[])iter.next();
					selectsql="select createUser from GYBomStructureLable where carModelCode='"+subs[2]+"' and dwbs='"
						+subs[3]+"' and parentPart='"+subs[0]+"' and childPart='"+subs[1]+"'";

					rs=st.executeQuery(selectsql);
					if(rs.next())
					{
						createUser=rs.getString(1);
					}

					if(createUser==null||createUser.equals(""))
					{
						createUser=userID;
					}
					else
					{
						if(createUser.indexOf(userID)!=-1)// �������û�������ԭֵ
						{
							createUser=createUser;
						}
						else
						// ���������û������Ӹ��û�
						{
							createUser=createUser+userID;
						}
					}
					updatesql="update GYBomStructureLable set createUser='"+createUser+"' where carModelCode='"+subs[2]
						+"' and dwbs='"+subs[3]+"' and parentPart='"+subs[0]+"' and childPart='"+subs[1]+"'";
//					System.out.println("updatesql=="+updatesql);
					st1.addBatch(updatesql);
				}
			}
			st1.executeBatch();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				st.close();
				st1.close();
				conn.close();
				if(rs!=null)
				{
					rs.close();
				}
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * �������BOM��ǩǰ����ո�������BOM
	 */
	private void deleteBom(String carModelCode,String dwbs)
	{
//		System.out.println("come in deleteBom carModelCode=="+carModelCode+"  dwbs=="+dwbs);
		Connection conn=null;
		Statement st=null;
		try
		{
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			String sql="delete GYBomStructureLable where carModelCode='"+carModelCode+"' and dwbs='"+dwbs+"'";
//			System.out.println("deleteBom:"+sql);
			st.executeQuery(sql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				st.close();
				conn.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
//		System.out.println("over in deleteBom");
	}

	/**
	 * ��ק����BOM���ϵ��ܳɼ��㲿��������BOM���ϵȲ����� ������ק������bom����ק��Ŀ��bom��֮����Ҫ��ͼ�괦��
	 * ������ק������BOM���µ��㲿�����ṹ����������BOM���£�����bom����Ҫ��ק������bom���ڵ�֮�£���ק֮����Ҫ���оֲ�ˢ�²����棩
	 * ������ק������BOM������ṹ�ĵ�����������ק��������ṹ�ĵ���������Ҫ�ı�ͼ�� ������ק��Ŀ����������ק������ק֮������ύ���档
	 * ������ק��addArr��ֵ
	 * ��������ק��updateArr��ֵ���Ƴ���deleteArr��ֵ�������ƶ���addArr��updateArr��ֵ��������������ӣ�addArr��ֵ
	 * 
	 * @param String
	 *            [] addArr �����ṹ����
	 *            ԭ����id,�¸���id,�Ӽ�id;�Ӽ�id1;�Ӽ�id2,����;����1;����2,��λ;��λ1;��λ2
	 * @param String
	 *            [] updateArr ���½ṹ����
	 *            linkid;����id;����,linkid1;����id1;����1,linkid2;����id2;����2
	 * @param String
	 *            [] deleteArr ɾ���ṹ���� linkid,linkid1,linkid2
	 * @param String
	 *            carModelCode ������
	 * @param String
	 *            dwbs ���� ����������json
	 */
	public String saveTreeNode(String addstr,String updatestr,String deletestr,String carModelCode,String dwbs,
		String left)
	{
		System.out.println("come in saveTreeNode: addstr=="+addstr+" updatestr=="+updatestr+" deletestr=="+deletestr
			+" carModelCode=="+carModelCode+" dwbs=="+dwbs+" left=="+left);
		System.out.println("left===="+left);
		JSONArray json=new JSONArray();
		JSONObject jo=new JSONObject();
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			long t1=System.currentTimeMillis();

			if(!addstr.equals(""))
			{
				String[] addArr=addstr.split(",");
				Collection ytree=new ArrayList();
				Collection mtree=new ArrayList();

				String[] subs=addArr[2].split(";");
				String[] counts=addArr[3].split(";");
				String[] units=addArr[4].split(";");

				String zhizao="";
				String zhuangpei="";

				Vector linkvec=new Vector();
				for(int i=0;i<subs.length;i++)
				{
					QMPartIfc part=(QMPartIfc)ps.refreshInfo(subs[i]);

					if(checkStructureLoop(addArr[1],subs[i],carModelCode,dwbs))
					{
						return "[{ʧ�ܣ������ṹ����ѭ����}]";
					}

					String[] mt=new String[]{addArr[1],subs[i],counts[i],carModelCode,dwbs,"0","","",units[i],
						//CCBegin SS70
//							part.getPartNumber()};
							part.getPartNumber(),"red"};
					//CCEnd SS70
					// mtree.add(mt);
					String linkid=saveGYBom(mt);
					// ���������ؽڵ��ˣ��ͻ���ͳһ�����ڵ�ˢ�¡�
					/*
					 * HashMap map = new HashMap(); map =
					 * getRoute("'"+part.getMasterBsoID()+"'", map);
					 * if(map.get(part.getMasterBsoID())!=null) { String[]
					 * routestr = (String[])(map.get(part.getMasterBsoID()));
					 * zhizao=routestr[0]; zhuangpei=routestr[1]; }
					 * 
					 * try { jo = new JSONObject();
					 * jo.put("id",part.getBsoID()); jo.put("pId",addArr[1]);
					 * jo.put("num",part.getPartNumber());
					 * jo.put("pname",part.getPartName());
					 * jo.put("name",part.getPartNumber
					 * ()+" "+part.getPartName()+
					 * " "+part.getVersionValue()+" "+part.getViewName());
					 * jo.put("zz",zhizao); jo.put("zp",zhuangpei);
					 * jo.put("quantity",counts[i]); jo.put("dw",units[i]);
					 * jo.put("linkid",linkid); jo.put("open","false");
					 * jo.put("color","black");
					 * jo.put("icon","bom/map/gypart.gif"); json.put(jo); }
					 * catch (Exception ex1) { ex1.printStackTrace(); }
					 */

					// parentPart,childPart,quantity,createTime,modifyTime,carModelCode,dwbs,createUser,color,unit,childNumber
					if(left.equals("treeDemo"))
					{
						String[] str=new String[]{addArr[0],subs[i],carModelCode,dwbs};
						if(!addArr[0].equals("no"))
						{
							ytree.add(str);
						}
					}

					// ����BOM�Ѿ���ʼ���������е������Թ���BOM��Ϊ׼
					if(left.equals("treeDemo"))
					{
						String substr=getGYBomList(part.getBsoID(),carModelCode,dwbs);
						if(substr.equals("")||substr.equals("[]"))
						{
							mtree=getBomLinks(mtree,part,carModelCode,dwbs,linkvec);
						}
					}
					if(left.equals("addPart"))
					{
						// ��ȡcarModelCode�µĽṹ������нṹ˵��֮ǰ��ӹ����������Ӹü���ɾ���ˣ������ظ���ӣ�ʹ��ԭ��¼��
						String substr=getGYBomList(part.getBsoID(),carModelCode,dwbs);
						if(substr.equals("")||substr.equals("[]"))
						{
							mtree=getBomLinks(mtree,part,carModelCode,dwbs,linkvec);
						}
					}
				}
				// saveGYBom(mtree);
				if(left.equals("treeDemo"))
				{
					//CCBegin SS15
					saveGYBom(mtree, true, dwbs);//SS21
					//CCEnd SS15
					UserInfo user=getCurrentUserInfo();
					updateBom(ytree,user.getBsoID());
				}
				if(left.equals("addPart"))
				{
					//CCBegin SS15
					saveGYBom(mtree, true, dwbs);//SS21
					//CCEnd SS15
				}
			}
			if(!updatestr.equals(""))
			{
				String[] updateArr=updatestr.split(",");
				Collection mtree=new ArrayList();
//System.out.println("updateArr.length====="+updateArr.length);
				for(int i=0;i<updateArr.length;i++)
				{
					String[] subs=updateArr[i].split(";");

					if(checkStructureLoop(subs[1],getChildPart(subs[0]),carModelCode,dwbs))
					{
						return "[{ʧ�ܣ������ṹ����ѭ����}]";
					}
					//System.out.println("subs.length====="+subs.length);
					// linkid,parentid,count,yparentid
					//CCBegin SS70
//					String[] mt=new String[]{subs[0],subs[1],subs[2]};
					String[] mt=new String[]{subs[0],subs[1],subs[2],subs[3]};
					//CCEnd SS70
					mtree.add(mt);
				}
				//CCBegin SS52
				saveChangeContentUpdate(carModelCode, dwbs,mtree);//mtree��ÿ�������Ԫ��Ϊlinkid������id���޸ĺ������
				//CCEnd SS52
				//CCBegin SS70
				updateGYBom(mtree,dwbs,left);
				//CCEnd SS70
			}
			if(!deletestr.equals(""))
			{
				String[] deleteArr=deletestr.split(",");
				Collection mdtree=new ArrayList();

				for(int i=0;i<deleteArr.length;i++)
				{
					mdtree.add(deleteArr[i]);
				}
				//CCBegin SS52
				saveChangeContentDelete(carModelCode, dwbs,mdtree);//mdtree��ÿ�������Ԫ��Ϊlinkid
				//CCEnd SS52
				deleteGYBom(mdtree);
			}
			long t2=System.currentTimeMillis();
//			System.out.println("saveTreeNode ��ʱ�� "+(t2-t1)+" ��");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String s=json.toString();
//		System.out.println("saveTreeNode json=="+s);
		return s;
	}

  //CCBegin SS7
	/**
	 * ��������BOM����
	 * mtree:parentPart,childPart,quantity,carModelCode,dwbs,effectCurrent
	 * ,locker,lockDate,unit,childNumber
	 * 
	 * @param Collection
	 *            mtreenode Ŀ����������BOM����������
	 * @param Collection
	 *            links �������ϣ�ÿ��Ԫ����һ����ά���飬�ֱ��Ǹ���bsoID���Ӽ�bsoID��������
	 *            ����ʱҪ��ȡ��ǰ�û����ڵ�λ��Ȼ��ֱ��ͨ�����ݿⴴ����
	 */
	public void saveGYBom(Collection mtreenode, boolean checkexitflag, String dwbs) throws QMException//SS21
	{
		Connection conn=null;
		Statement st=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			stmt=conn.createStatement();
			//CCBegin SS70
//			String mtsql="INSERT INTO GYBomStructure (id,parentPart,childPart,quantity,createTime,modifyTime,carModelCode,dwbs,effectCurrent,locker,lockDate,unit,childNumber,bz2) VALUES ";
			String mtsql="INSERT INTO GYBomStructure (id,parentPart,childPart,quantity,createTime,modifyTime,carModelCode,dwbs,effectCurrent,locker,lockDate,unit,childNumber,bz1) VALUES ";
			//CCEnd SS70
			String tempsql="";
			String sendData=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			String countIdSql="select count(*) from GYBomStructure where dwbs='"+dwbs+"' and parentPart='";//SS20
			String exitsstr="";
			if(mtreenode!=null)
			{
				ArrayList a=new ArrayList();
				Iterator iter=mtreenode.iterator();
				while(iter.hasNext())
				{
					String[] mt=(String[])iter.next();

					rs=stmt.executeQuery(countIdSql+mt[0]+"' and childPart='"+mt[1]+"'");
					rs.next();
					int countList=rs.getInt(1);
					if(countList>0&&checkexitflag)
					{
						exitsstr="("+mt[0]+" "+mt[1]+"),"+exitsstr;
						continue;
					}

					if(mt.length==10)
					{
						tempsql=mtsql+"(GYBOMSTRUCTURE_seq.nextval,'"+mt[0]+"', '"+mt[1]+"', '"+mt[2]+"', to_date('"
							+sendData+"','yyyy-mm-dd hh24:mi:ss'), to_date('"+sendData
							+"','yyyy-mm-dd hh24:mi:ss'), '', '"+mt[4]+"', '"+mt[5]+"', '"+mt[6]+"', '"+mt[7]+"', '"
							+mt[8]+"', '"+mt[9]+"','')";
					}
					else if(mt.length==11)
					{
						tempsql=mtsql+"(GYBOMSTRUCTURE_seq.nextval,'"+mt[0]+"', '"+mt[1]+"', '"+mt[2]+"', to_date('"
							+sendData+"','yyyy-mm-dd hh24:mi:ss'), to_date('"+sendData
							+"','yyyy-mm-dd hh24:mi:ss'), '', '"+mt[4]+"', '"+mt[5]+"', '"+mt[6]+"', '"+mt[7]+"', '"
							+mt[8]+"', '"+mt[9]+"', '"+mt[10]+"')";
					}
					//System.out.println("���======="+tempsql);
					st.addBatch(tempsql);
				}
				st.executeBatch();
//				if(!exitsstr.equals(""))
//				{
//					System.out.println("saveGYBom �����Ѵ��ڽṹ��"+exitsstr);
//				}
			}

			// �ر�Statement
			st.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new QMException(e);
		}
		finally
		{
			try
			{
				st.close();
				conn.close();
			}
			catch(SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * ���ɹ��պϼ� ���������պϼ� ��Ź���1000940+AAAA+J+���
	 * ��AAAA��ȡ�Է����������1000410+AAAA��ȡ��1000410��֮����ַ����� ��J���ǹ̶����ַ�
	 * ���Ϊ01��02��03��ϵͳ�д��ڵ�����������
	 * 
	 * @param bsoID
	 *            1000410���ı��
	 * @return ����1000410���ɵĹ��պϼ���part����
	 */
	public QMPartIfc createGYPart(String num,Collection fdjparts, String dwbs) throws QMException//SS21
	{
//		System.out.println("createGYPart new   num=="+num);
		PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
		StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
		String AAAA=num.substring(7,num.length());
		// ���ݸ����ı�Ų�ѯgybomstructure����ѯ���������û���Ѿ����ڵ��Ӽ�
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		HashMap map=new HashMap();
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			String sq = "select t.childpart,t.parentpart from gybomstructure t,QMPartMaster b,QMPart a where t.parentpart=a.bsoid and a.masterbsoid=b.bsoid and b.partnumber like '"+"1000940"+AAAA+"%' and t.dwbs='"+dwbs+"'";//SS21
//			System.out.println("sq===="+sq);
			rs=stmt.executeQuery(sq);
			while(rs.next())
			{
				String childid=rs.getString(1);
				String parentid=rs.getString(2);
				if(map.get(parentid)==null)
				{
					Collection vec=new ArrayList();
					vec.add(childid);
					map.put(parentid,vec);
				}
				else
				{
					Collection vec=(Collection)map.get(parentid);
					vec.add(childid);
					map.put(parentid,vec);
				}
			}
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new QMException(e);
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}

		String pid=null;
		// �жϸ����ĸ�����ŵ������Ӽ��͸��ݹ������ɵ��Ӽ��Ƿ���ͬ
		for(Iterator it=map.keySet().iterator();it.hasNext();)
		{
			String parentid=(String)it.next();
			Collection childs=(Collection)map.get(parentid);
			Collection vv=new ArrayList();
//			System.out.println("parentid========"+parentid);
//			System.out.println("childs.size()========="+childs.size());
//			System.out.println("fdjparts.size()========="+fdjparts.size());
			if(childs.size()==fdjparts.size())
			{
				for(Iterator oneiter=childs.iterator();oneiter.hasNext();)
				{
					String childid=(String)oneiter.next();
					for(Iterator iter=fdjparts.iterator();iter.hasNext();)
					{
						String[] str1=(String[])iter.next();
						QMPartIfc fdjsp=(QMPartIfc)ps.refreshInfo(str1[0]);
						String fdjid=fdjsp.getBsoID();
						if(childid.equals(fdjid))
						{
							vv.add(childid);
						}
					}
				}
//				System.out.println("vv.size()=========="+vv.size());
				if(vv.size()==childs.size())
				{
					pid=parentid;
				}
			}
		}
//		System.out.println("pid==============="+pid);
		// �����ͬ��������һ������
		if(pid==null)
		{
			UserInfo user=getCurrentUserInfo();
			String lifeCyID=PublishHelper.getLifeCyID("���������㲿����������");

			String snum=getGYPartNums("1000940"+AAAA+"J");
			QMPartIfc part=new QMPartInfo();
			part.setPartNumber(snum);
			part.setPartName("�������ܳ�");
			part.setLifeCycleState(LifeCycleState.toLifeCycleState("PREPARING"));//SS37
			part.setViewName("������ͼ");
			part.setDefaultUnit(Unit.getUnitDefault());
			part.setProducedBy(ProducedBy.getProducedByDefault());
			part.setPartType("Assembly");
			part.setIterationCreator(user.getBsoID());
			part.setIterationModifier(user.getBsoID());
			part.setIterationNote("");
			part.setOwner("");
			part=(QMPartInfo)PublishHelper.assignFolder(part,"\\Root\\���պϼ�");
			part.setLifeCycleTemplate(lifeCyID);
			part=sps.savePart(part);
			//CCBegin SS38
			TechnicsRouteListIfc trIfc = createRouteList(part);
			//
			createListRoutePartLink(trIfc ,part ,null);
			//CCEnd SS38
			return (QMPartIfc)ps.refreshInfo(part.getBsoID());
		}
		// �����ͬ�������Ѿ����ڵĸ���
		else
		{
			return (QMPartIfc)ps.refreshInfo(pid);
		}
	}
	//CCEnd SS7

	/**
	 * ��������BOM����
	 * 
	 * @param String
	 *            [] mt parentPart,childPart,quantity,carModelCode,dwbs,
	 *            effectCurrent,locker,lockDate,bz1
	 * @return String ����id
	 */
	public String saveGYBom(String[] mt)
	{
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		String s="";
		try
		{
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			//CCBegin SS70
//			String mtsql="INSERT INTO GYBomStructure (id,parentPart,childPart,quantity,createTime,modifyTime,carModelCode,dwbs,effectCurrent,locker,lockDate,unit,childNumber) VALUES ";
			String mtsql="INSERT INTO GYBomStructure (id,parentPart,childPart,quantity,createTime,modifyTime,carModelCode,dwbs,effectCurrent,locker,lockDate,unit,childNumber,bz1) VALUES ";
			//CCEnd SS70
			String tempsql="";
			String sendData=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

			rs=st.executeQuery("select GYBomStructure_SEQ.nextval from dual");
			if(rs.next())
			{
				s=rs.getString(1);
			}
			// System.out.println("s=="+s);

			tempsql=mtsql+"('"+s+"','"+mt[0]+"', '"+mt[1]+"', '"+mt[2]+"', to_date('"+sendData
				+"','yyyy-mm-dd hh24:mi:ss'), to_date('"+sendData+"','yyyy-mm-dd hh24:mi:ss'), '', '"+mt[4]+"', '"
				//CCBegin SS70
//				+mt[5]+"', '"+mt[6]+"', '"+mt[7]+"', '"+mt[8]+"', '"+mt[9]+"')";
				+mt[5]+"', '"+mt[6]+"', '"+mt[7]+"', '"+mt[8]+"', '"+mt[9]+"', '"+mt[10]+"')";
			String updatesql = "update GYBomStructure set bz1='red' where (childPart='"+mt[0]+"') and effectCurrent='0'";
			updateNodeColor(updatesql);
			//CCEnd SS70
//			System.out.println("sql���====="+tempsql);
			st.executeQuery(tempsql);

			// �ر�Statement
			st.close();
			// �ر����ݿ�����
			conn.close();
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				st.close();
				conn.close();
				rs.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return s;
	}

	/**
	 * ���¹���BOM����
	 * 
	 * @param Collection
	 *            mtreenode �������ϣ�ÿ��Ԫ����һ����ά���飬�ֱ���linkid������bsoID��������
	 */
	//CCBegin SS70
//	public void updateGYBom(Collection mtreenode) throws QMException
	public void updateGYBom(Collection mtreenode,String dwbs,String type) throws QMException
	//CCEnd SS70
	{
		Connection conn=null;
		Statement st=null;
		try
		{
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			String mtsql="update GYBomStructure set ";
			String updatesql="";
			//CCBegin SS70
			String updatesql1="";
			//CCEnd SS70
			System.out.println("mtreenode===="+mtreenode);
			if(mtreenode!=null)
			{
				Iterator iter=mtreenode.iterator();
				while(iter.hasNext())
				{
					String[] subs=(String[])iter.next();
					//CCBegin SS70 ����ֻ�ı�����ק�Ľڵ���ɫ���ˣ�����Ҫ���ڵ�ԭ�����ڵ��Լ���ק�󸸼��ڵ��ɫ
//					updatesql=mtsql+"parentPart='"+subs[1]+"',quantity='"+subs[2]+"' where id='"+subs[0]+"'";
					boolean flag = true;
					if(type.equals("treeDemo")){
						flag = isSameCount(dwbs,subs[0],subs[1],subs[2]);
					}
					//System.out.println("flag==="+flag);
					if(flag){
						updatesql=mtsql+"parentPart='"+subs[1]+"',quantity='"+subs[2]+"',bz1='red' where id='"+subs[0]+"'";
						updatesql1 = mtsql+"bz1='red' where (childPart='"+subs[1]+"'or childPart='"+subs[3]+"') and effectCurrent='0' and dwbs='"+dwbs+"'";
						//System.out.println("======="+updatesql1);
						st.addBatch(updatesql1);
						//CCEnd SS70
						//System.out.println("updatesql=="+updatesql);
						st.addBatch(updatesql);
					}
				}
			}
			st.executeBatch();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				st.close();
				conn.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	//CCBegin SS70
	/**
	 * ���Ľڵ���ɫ
	 * 
	 * @param updatesql
	 *            sql���
	 */
	private void updateNodeColor(String updatesql) {
		System.out.println("updatesql====="+updatesql);
		Connection conn = null;
		Statement st = null;
		try {
			conn = PersistUtil.getConnection();
			st = conn.createStatement();
			st.addBatch(updatesql);
			st.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//CCEnd SS70

	/**
	 * ɾ������BOM����
	 * 
	 * @param Collection
	 *            mtreenode Ŀ����������BOM���������� ֵ��id�� ɾ�������������ݡ�
	 */
	public void deleteGYBom(Collection mtreenode)
	{
		Connection conn=null;
		Statement st=null;
		try
		{
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			String mtsql="delete GYBomStructure where ";

			String deletesql="";

			if(mtreenode!=null)
			{
				Iterator iter=mtreenode.iterator();
				while(iter.hasNext())
				{
					String id=(String)iter.next();
					//CCBegin SS70
					if(id.contains(";")){
						String[] str = id.split(";");
						deletesql=mtsql+"id='"+str[0]+"'";
						String updatesql1 = "update GYBomStructure set bz1='red' where (childPart='"+str[1]+"') and effectCurrent='0'";
						st.addBatch(updatesql1);
					}else{
						deletesql=mtsql+"id='"+id+"'";
					}
					//CCEnd SS70
					// System.out.println("deletesql=="+deletesql);
					st.addBatch(deletesql);
				}
			}
			st.executeBatch();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				st.close();
				conn.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	//CCBegin SS7
	/**
	 * ���ɳ�ʼ����BOM
	 */
	public void initGYBom(String partID,String gyID,String dwbs) throws QMException
	{
		try
		{
			// �������߼��ܳ�
			String partNumberRuleFDJ=RemoteProperty.getProperty("gybomlistfdj");
			// ����
			String partNumberRuleZC=RemoteProperty.getProperty("gybomlistzc");
			// �����߼��ܳ�
			String partNumberRuleCJ=RemoteProperty.getProperty("gybomlistcj");
			// ��ʻ���߼��ܳ�
			String partNumberRuleNS=RemoteProperty.getProperty("gybomlistns");
			boolean gyPartFlag=false;
			Vector fdjparts=new Vector();
			Vector zcparts=new Vector();
			Vector cjparts=new Vector();
			Vector jssparts=new Vector();
			Collection mtree=new ArrayList();
			Collection ytree=new ArrayList();
			Vector linkvec=new Vector();
			StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
			JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(partID);
			QMPartIfc gypart=(QMPartIfc)ps.refreshInfo(gyID);
			UserInfo user=getCurrentUserInfo();
			deleteGYBomLevel(gyID,dwbs,"0");
			deleteBom(gypart.getPartNumber(),dwbs);
			PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
			Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);
			for(Iterator iter=linkcoll.iterator();iter.hasNext();)
			{
				Object obj[]=(Object[])iter.next();
				PartUsageLinkIfc link=(PartUsageLinkIfc)obj[0];
				if(obj[1] instanceof QMPartIfc)
				{
					QMPartIfc subpart=(QMPartIfc)obj[1];

					// ������
					if(subpart.getPartNumber().length()>=7
						&&partNumberRuleFDJ.indexOf(subpart.getPartNumber().substring(0,7))!=-1)
					{
						if(subpart.getPartNumber().startsWith("1000410"))
						{
							fdjparts.add(
								0,
								new String[]{subpart.getBsoID(),String.valueOf(link.getQuantity()),
									subpart.getPartNumber()});
							gyPartFlag=true;
						}
						else
						{
							fdjparts.add(new String[]{subpart.getBsoID(),String.valueOf(link.getQuantity()),
								subpart.getPartNumber()});
						}

						// parentPart,childPart,quantity,createTime,modifyTime,carModelCode,dwbs,createUser,color,unit,childNumber
						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
							gypart.getPartNumber(),dwbs,user.getBsoID(),"",link.getDefaultUnit().getDisplay(),
							subpart.getPartNumber()};
						ytree.add(mt1);
					}
					// ����
					else if(subpart.getPartNumber().length()>=7
						&&partNumberRuleCJ.indexOf(subpart.getPartNumber().substring(0,7))!=-1)
					{
						cjparts.add(new String[]{subpart.getBsoID(),String.valueOf(link.getQuantity()),
							subpart.getPartNumber()});
						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
							gypart.getPartNumber(),dwbs,user.getBsoID(),"",link.getDefaultUnit().getDisplay(),
							subpart.getPartNumber()};
						ytree.add(mt1);
					}
					// ��ʻ��
					else if(subpart.getPartNumber().length()>=7
						&&partNumberRuleNS.indexOf(subpart.getPartNumber().substring(0,7))!=-1)
					{
						jssparts.add(new String[]{subpart.getBsoID(),String.valueOf(link.getQuantity()),
							subpart.getPartNumber()});
						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
							gypart.getPartNumber(),dwbs,user.getBsoID(),"",link.getDefaultUnit().getDisplay(),
							subpart.getPartNumber()};
						ytree.add(mt1);
					}
					// ����
					else if(subpart.getPartNumber().length()>=7
						&&partNumberRuleZC.indexOf(subpart.getPartNumber().substring(0,7))!=-1)
					{
						zcparts.add(new String[]{subpart.getBsoID(),String.valueOf(link.getQuantity()),
							subpart.getPartNumber()});
						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
							gypart.getPartNumber(),dwbs,user.getBsoID(),"",link.getDefaultUnit().getDisplay(),
							subpart.getPartNumber()};
						ytree.add(mt1);
					}
					else
					{
						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
							gypart.getPartNumber(),dwbs,"","",link.getDefaultUnit().getDisplay(),
							subpart.getPartNumber()};
						ytree.add(mt1);
					}
				}
			}
			ArrayList array = new ArrayList();//SS20
			ArrayList otherParts = new ArrayList();//SS37
			if(gyPartFlag)
			{
				// ������
				Iterator fdjiter1=fdjparts.iterator();
				String[] fdjstr=(String[])fdjiter1.next();
				QMPartIfc gyPartIfc=createGYPart(fdjstr[2]);//����1000940
				// ���������빤�պϼ�����
				String[] mt=new String[]{gyID,gyPartIfc.getBsoID(),"1",gypart.getPartNumber(),dwbs,"0","","","��",
					gyPartIfc.getPartNumber()};
				mtree.add(mt);
				// �������պϼ�
				Iterator iter=fdjparts.iterator();
				QMPartIfc fdjsp=null;
				while(iter.hasNext())
				{
					String[] str1=(String[])iter.next();
					fdjsp=(QMPartIfc)ps.refreshInfo(str1[0]);
					String[] mt1=new String[]{gyPartIfc.getBsoID(),fdjsp.getBsoID(),str1[1],gypart.getPartNumber(),
						dwbs,"0","","","��",str1[2]};
					mtree.add(mt1);
					if(fdjsp.getPartNumber().substring(0,7).equals("1000410"))
					{
						mtree=getBomLinks(gyID,mtree,fdjsp,gypart.getPartNumber(),dwbs,linkvec,"1000410",array,otherParts);//SS20//SS37
					}
					else
					{
						mtree=getBomLinks(gyID,mtree,fdjsp,gypart.getPartNumber(),dwbs,linkvec,"fdj",array,otherParts);//SS20//SS37
					}
				}
				// ����
				Iterator cjiter=cjparts.iterator();
				QMPartIfc cjsp=null;
				while(cjiter.hasNext())
				{
					String[] cjstr1=(String[])cjiter.next();
					cjsp=(QMPartIfc)ps.refreshInfo(cjstr1[0]);
					String[] mt1=new String[]{gyID,cjsp.getBsoID(),cjstr1[1],gypart.getPartNumber(),dwbs,"0","","","��",
						cjstr1[2]};
					mtree.add(mt1);
					mtree=getBomLinks(gyID,mtree,cjsp,cjsp.getPartNumber(),dwbs,linkvec,"cj",array,otherParts);//SS20//SS37
				}
				// ��ʻ��
				Iterator jssiter=jssparts.iterator();
				QMPartIfc jsssp=null;
				while(jssiter.hasNext())
				{
					String[] jssstr1=(String[])jssiter.next();
					jsssp=(QMPartIfc)ps.refreshInfo(jssstr1[0]);
					String[] mt1=new String[]{gyID,jsssp.getBsoID(),jssstr1[1],gypart.getPartNumber(),dwbs,"0","","",
						"��",jssstr1[2]};
					mtree.add(mt1);
					mtree=getBomLinks(gyID,mtree,jsssp,jsssp.getPartNumber(),dwbs,linkvec,"jss",array,otherParts);//SS20//SS37
				}
				// ����
				Iterator zciter=zcparts.iterator();
				QMPartIfc zcsp=null;
				while(zciter.hasNext())
				{
					String[] zcstr1=(String[])zciter.next();
					zcsp=(QMPartIfc)ps.refreshInfo(zcstr1[0]);
					String[] mt1=new String[]{gyID,zcsp.getBsoID(),zcstr1[1],gypart.getPartNumber(),dwbs,"0","","","��",
						zcstr1[2]};
					mtree.add(mt1);
					mtree=getBomLinks(gyID,mtree,zcsp,zcsp.getPartNumber(),dwbs,linkvec,"zc",array,otherParts);//SS20//SS37
				}
			}
			// ���ݹ�����нṹ���
			Collection ntree=chaifengybom(gypart,mtree,dwbs, part,array);//SS20
			// ���ݹ���Է��Ϲ���ļ����и��������Ϊ��
			Collection rtree=renameGyBom(ntree);
			//CCBegin SS15
			saveGYBom(rtree, true, dwbs);//SS21//SS28
			//CCEnd SS15
			// saveGYBom(ntree);
			saveBom(ytree);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	//CCEnd SS7
	/**
	 * ɾ��ָ���㲿����һ���ṹ�� ��ʼ������BOMǰ����ո�������һ��δ��ЧBOM������BOMҲ�����һ��δ��ЧBOM
	 * 
	 * @param parentPart
	 *            ����
	 * @param dwbs
	 *            ����
	 * @param eff
	 *            1 ��Ч�ṹ�� 0δ��Ч�ṹ
	 */
	private void deleteGYBomLevel(String parentPart,String dwbs,String eff)
	{

		Connection conn=null;
		Statement st=null;
		try
		{
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			String sql="delete GYBomStructure where parentPart='"+parentPart+"' and dwbs='"+dwbs
				+"' and effectCurrent='"+eff+"'";
			st.executeQuery(sql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				st.close();
				conn.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���ɹ��պϼ� ���������պϼ� ��Ź���1000940+AAAA+J+���
	 * ��AAAA��ȡ�Է����������1000410+AAAA��ȡ��1000410��֮����ַ����� ��J���ǹ̶����ַ�
	 * ���Ϊ01��02��03��ϵͳ�д��ڵ�����������
	 * 
	 * @param bsoID
	 *            1000410���ı��
	 * @return ����1000410���ɵĹ��պϼ���part����
	 */
	public QMPartIfc createGYPart(String num) throws QMException
	{
//		System.out.println("createGYPart   num=="+num);
		PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
		StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
		UserInfo user=getCurrentUserInfo();
		String lifeCyID=PublishHelper.getLifeCyID("���������㲿����������");
		String AAAA=num.substring(7,num.length());
		String snum=getGYPartNums("1000940"+AAAA+"J");
		QMPartIfc part=new QMPartInfo();
		part.setPartNumber(snum);
		part.setPartName("�������ܳ�");
		part.setLifeCycleState(LifeCycleState.toLifeCycleState("PREPARING"));
		part.setViewName("������ͼ");
		part.setDefaultUnit(Unit.getUnitDefault());
		part.setProducedBy(ProducedBy.getProducedByDefault());
		part.setPartType("Assembly");
		part.setIterationCreator(user.getBsoID());
		part.setIterationModifier(user.getBsoID());
		part.setIterationNote("");
		part.setOwner("");
		part=(QMPartInfo)PublishHelper.assignFolder(part,"\\Root\\���պϼ�");
		part.setLifeCycleTemplate(lifeCyID);
		part=sps.savePart(part);
		return (QMPartIfc)ps.refreshInfo(part.getBsoID());
	}

	/**
	 * ���ݹ��պϼ����ǰ׺����ȡ��ţ����ڼ������պϼ���˳���Ϊ01��02��03...
	 */
	public String getGYPartNums(String num)
	{
//		System.out.println("getGYPartNums begin !!! num=="+num);
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String snum="";
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			String countIdSql="select count(*) from QMPartMaster where partNumber like '"+num+"%'";
			rs=stmt.executeQuery(countIdSql);
			rs.next();
			int countList=rs.getInt(1);
			if(countList==0)
			{
				snum=num+"001";
			}
			else if(0<countList&&countList<9)
			{
				snum=num+"00"+Integer.toString(countList+1);
			}
			else if(8<countList&&countList<99)
			{
				snum=num+"0"+Integer.toString(countList+1);
			}
			else if(98<countList&&countList<999)
			{
				snum=num+Integer.toString(countList+1);
			}

			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
//		System.out.println("getGYPartNums  end !!! snum=="+snum);
		return snum;
	}

	/**
	 * �����߼��ܳɱ��ǰ׺����ȡ��ţ�˳���Ϊ001��002��003...
	 */
	public String getLogicPartNums(String num)
	{
//		System.out.println("getLogicPartNums begin !!! num=="+num);
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String snum="";
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			String countIdSql="select count(*) from QMPartMaster where partNumber like '"+num+"%'";
			rs=stmt.executeQuery(countIdSql);
			rs.next();
			int countList=rs.getInt(1);
			if(countList==0)
			{
				snum=num+"001";
			}
			else if(0<countList&&countList<9)
			{
				snum=num+"00"+Integer.toString(countList+1);
			}
			else if(8<countList&&countList<99)
			{
				snum=num+"0"+Integer.toString(countList+1);
			}
			else if(98<countList&&countList<999)
			{
				snum=num+"0"+Integer.toString(countList+1);
			}

			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
//		System.out.println("getLogicPartNums  end !!! snum=="+snum);
		return snum;
	}

	/**
	 * ����ָ���㲿������һ���Ӽ��ڵ㼯�� ������ӹ���bom��Ŀ������ ��ȡδ��Ч�ṹ��û����ȡ��Ч�ṹ��
	 * 
	 * @param id
	 *            �㲿��bsoID
	 * @param carModelCode
	 *            ������
	 * @param dwbs
	 *            ��λ
	 * @return �㲿������һ���Ӽ������ṹ
	 */
	public String getGYBom(String id,String carModelCode,String dwbs) throws QMException
	{
//		System.out.println("come in getGYBom   id=="+id+"  carModelCode=="+carModelCode+"  dwbs=="+dwbs);
		JSONArray json=new JSONArray();
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(id);
			//CCBegin SS42
			String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",id);
			if(fbyIbaValue==""){
				fbyIbaValue = part.getVersionValue();
			}
			//CCEnd SS42
			// ���ø��ڵ�
			JSONObject jo=new JSONObject();
			jo.put("id",id);
			jo.put("pId","0");
			jo.put("num",part.getPartNumber());
			jo.put("pname",part.getPartName());
			jo.put("name",part.getPartNumber()+" "+part.getPartName()+" "+fbyIbaValue+" "+part.getViewName());
			jo.put("open","true");
			jo.put("icon","bom/map/gyopenpart.gif");
			jo.put("quantity","");
			//CCBegin SS29
			//CCBegin SS42
			//jo.put("version",part.getVersionValue());
			jo.put("version",fbyIbaValue);
			//CCEnd SS42
			//CCEnd SS29
			//CCBegin SS69
			String ss = getEffect(id,carModelCode, dwbs);
			if(ss.equals("2")){
				jo.put("color","green");
			}
			//CCEnd SS69
			// ��ȡ��ǩ
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			try
			{
				conn=PersistUtil.getConnection();
				stmt=conn.createStatement();
				// ���ϵͳ�д���δ��Ч��BOM���δ��Ч��bom
				//CCBegin SS72
//				String sql="select childPart,quantity,id,unit,bz1 from GYBomStructure where effectCurrent='0' and parentPart='"
				String sql="";
				if(dwbs.equals("W34")){
				  sql="select childPart,quantity,id,unit,bz1,locker from GYBomStructure where effectCurrent='0' and parentPart='"
						//CCEnd SS72
					+id+"' and dwbs='"+dwbs+"' order by id";
				  }
				else{
					 sql="select childPart,quantity,id,unit,bz1,locker from GYBomStructure where effectCurrent='0' and parentPart='"
						//CCEnd SS72
					+id+"' and dwbs='"+dwbs+"' order by childNumber";}
//				System.out.println("��ȡδ��Ч�ṹ��"+sql);
				rs=stmt.executeQuery(sql);

				String insql="";
				while(rs.next())
				{
					String childPart=rs.getString(1);
					part=(QMPartIfc)ps.refreshInfo(childPart);
					if(part==null)
					{
						System.out.println("δ�ҵ��Ӽ�id��"+childPart);
						continue;
					}
					if(insql.equals(""))
					{
						if(dwbs.equals("W34"))
						{
							insql="'"+part.getBsoID()+"'";
						}
						else
						{
							insql="'"+part.getMasterBsoID()+"'";
						}
					}
					else
					{
						if(dwbs.equals("W34"))
						{
							insql=insql+",'"+part.getBsoID()+"'";
						}
						else
						{
							insql=insql+",'"+part.getMasterBsoID()+"'";
						}
					}
				}
				// System.out.println("insql 111="+insql);
				HashMap map=new HashMap();
				if(!insql.equals(""))
				{
					if(dwbs.equals("W34"))// �ɶ�ȡ����·��
					{
						map=getSecondRoute(insql,map);
					}
					else
					// ���·��
					{
						map=getRoute(insql,map);
					}
					jo.put("effect","false");
					json.put(jo);
				}

				rs=stmt.executeQuery(sql);
				boolean flag=true;
				Collection route=new ArrayList();
				String zhizao="";
				String zhuangpei="";
				while(rs.next())
				{
					String childPart=rs.getString(1);
					part=(QMPartIfc)ps.refreshInfo(childPart);
					String partnumber = part.getPartNumber();
					String partid = part.getBsoID();
					if(part==null)
					{
						System.out.println("δ�ҵ��Ӽ�id��"+childPart);
						continue;
					}

					if(dwbs.equals("W34"))
					{
						if(map.get(part.getBsoID())!=null)
						{
							String[] routestr=(String[])(map.get(part.getBsoID()));
							zhizao=routestr[0];
							zhuangpei=routestr[1];
						}
					}
					else
					{
						if(map.get(part.getMasterBsoID())!=null)
						{
							String[] routestr=(String[])(map.get(part.getMasterBsoID()));
							zhizao=routestr[0];
							zhuangpei=routestr[1];
						}
					}
					//CCBegin SS60
					if(partnumber.contains("3103070")){
						String sqlstr = "";
						if(sqlstr.equals(""))
						{
							if(dwbs.equals("W34"))
							{
								sqlstr="'"+part.getBsoID()+"'";
							}
							else
							{
								sqlstr="'"+part.getMasterBsoID()+"'";
							}
						}
						else
						{
							if(dwbs.equals("W34"))
							{
								sqlstr=sqlstr+",'"+part.getBsoID()+"'";
							}
							else
							{
								sqlstr=sqlstr+",'"+part.getMasterBsoID()+"'";
							}
						}
						HashMap map11=new HashMap();
						if(!sqlstr.equals(""))
						{
							if(dwbs.equals("W34"))// �ɶ�ȡ����·��
							{
								map11=getSecondRoute(sqlstr,map11);
							}
							else
							// ���·��
							{
								map11=getRoute(sqlstr,map11);
							}
						}
						if(dwbs.equals("W34"))
						{
							if(map11.get(part.getBsoID())!=null)
							{
								String[] routestr=(String[])(map11.get(part.getBsoID()));
								zhizao=routestr[0];
								zhuangpei=routestr[1];
							}
						}
						else
						{
							if(map11.get(part.getMasterBsoID())!=null)
							{
								String[] routestr=(String[])(map11.get(part.getMasterBsoID()));
								zhizao=routestr[0];
								zhuangpei=routestr[1];
							}
						}
					}
					//CCEnd SS60
					jo=new JSONObject();
					//CCBegni SS42
					String fbyIbaValue1 = RationHelper.getIbaValueByName("sourceVersion",childPart);
					if(fbyIbaValue1==""){
						fbyIbaValue1 = part.getVersionValue();
					}
					//CCEnd SS42
					jo.put("id",childPart);
					jo.put("pId",id);
					jo.put("num",part.getPartNumber());
					jo.put("pname",part.getPartName());
					jo.put("linkid",Integer.toString(rs.getInt(3)));
					//CCBegin SS42
//					jo.put("name",
//						part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "+part.getViewName());
					jo.put("name",
							part.getPartNumber()+" "+part.getPartName()+" "+fbyIbaValue1+" "+part.getViewName());
					//CCEnd SS42
					jo.put("open","false");
					jo.put("zz",zhizao);
					jo.put("zp",zhuangpei);
					jo.put("quantity",rs.getString(2));
					jo.put("dw",rs.getString(4));
					//CCBegin SS69
//					if(rs.getString(5)!=null&&rs.getString(5).equals("red"))
//					{
//						jo.put("color","red");
//					}
					if(rs.getString(5)!=null)
					{
						if(rs.getString(5).equals("red")){
							jo.put("color","red");
						}if(rs.getString(5).equals("green")){
							jo.put("color","green");
						}
					}
					//CCEnd SS69
					else
					{
						jo.put("color","black");
					}
					//CCBegin SS72
//					jo.put("icon","bom/map/gypart.gif");
					if(rs.getString(6)!=null){
						jo.put("icon","js/easyui/themes/icons/lock.png");
//						jo.put("islock","true");
					}else{
						jo.put("icon","bom/map/gypart.gif");
//						jo.put("islock","false");
					}
					//�ڵ������������ԣ��Ƿ��˼���
//					boolean lockflag = searchLockUser(childPart,dwbs);
//					if(lockflag){
//						jo.put("isownlock","true");
//					}else{
//						jo.put("isownlock","false");
//					}
					//CCEnd SS72
					jo.put("effect","false");
					//CCBegin SS29
					//CCBegin SS42
//					jo.put("version",part.getVersionValue());
					jo.put("version",fbyIbaValue1);
					//CCEnd SS42
					//CCEnd SS29
					json.put(jo);
					flag=false;
					zhizao="";
					zhuangpei="";
				}
//				System.out.println("�Ƿ�����Ч�ṹ��"+flag);
				// ���ϵͳ�в�����δ��Ч��BOM�����Ч��bom
				if(flag)
				{
					if(dwbs.equals("W34")){
					  sql="select childPart,quantity,id,unit,bz1 from GYBomStructure where effectCurrent='1' and parentPart='"
						+id+"' and dwbs='"+dwbs+"' order by id";
					}
					else
					{
						sql="select childPart,quantity,id,unit,bz1 from GYBomStructure where effectCurrent='1' and parentPart='"
							+id+"' and dwbs='"+dwbs+"' order by childNumber";
					}
//					System.out.println("��ȡ��Ч�ṹ��"+sql);
					rs=stmt.executeQuery(sql);

					insql="";
					while(rs.next())
					{
						String childPart=rs.getString(1);
						part=(QMPartIfc)ps.refreshInfo(childPart);
						if(part==null)
						{
							System.out.println("δ�ҵ��Ӽ�id��"+childPart);
							continue;
						}
						if(insql.equals(""))
						{
							if(dwbs.equals("W34"))
							{
								insql="'"+part.getBsoID()+"'";
							}
							else
							{
								insql="'"+part.getMasterBsoID()+"'";
							}
						}
						else
						{
							if(dwbs.equals("W34"))
							{
								insql=insql+",'"+part.getBsoID()+"'";
							}
							else
							{
								insql=insql+",'"+part.getMasterBsoID()+"'";
							}
						}
					}
//					System.out.println("insql 222="+insql);
					map=new HashMap();
					if(!insql.equals(""))
					{
						if(dwbs.equals("W34"))
						{
							map=getSecondRoute(insql,map);
						}
						else
						{
							map=getRoute(insql,map);
						}
						jo.put("effect","true");
						json.put(jo);
					}
					else
					{
						jo.put("effect","false");
						json.put(jo);
					}

					rs=stmt.executeQuery(sql);
					while(rs.next())
					{
						String childPart=rs.getString(1);
						part=(QMPartIfc)ps.refreshInfo(childPart);
						if(part==null)
						{
							System.out.println("δ�ҵ��Ӽ�id��"+childPart);
							continue;
						}

						if(dwbs.equals("W34"))
						{
							if(map.get(part.getBsoID())!=null)
							{
								String[] routestr=(String[])(map.get(part.getBsoID()));
								zhizao=routestr[0];
								zhuangpei=routestr[1];
							}
						}
						else
						{
							if(map.get(part.getMasterBsoID())!=null)
							{
								String[] routestr=(String[])(map.get(part.getMasterBsoID()));
								zhizao=routestr[0];
								zhuangpei=routestr[1];
							}
						}

						jo=new JSONObject();
						//CCBegni SS42
						String fbyIbaValue1 = RationHelper.getIbaValueByName("sourceVersion",childPart);
						if(fbyIbaValue1==""){
							fbyIbaValue1 = part.getVersionValue();
						}
						//CCEnd SS42
						jo.put("id",childPart);
						jo.put("pId",id);
						jo.put("num",part.getPartNumber());
						jo.put("pname",part.getPartName());
						//CCBegin SS42
//						jo.put(
//							"name",
//							part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "
//								+part.getViewName());
						jo.put(
								"name",
								part.getPartNumber()+" "+part.getPartName()+" "+fbyIbaValue1+" "
									+part.getViewName());
						//CCEnd SS42
						jo.put("open","false");
						jo.put("zz",zhizao);
						jo.put("zp",zhuangpei);
						jo.put("quantity",rs.getString(2));
						jo.put("dw",rs.getString(4));
						jo.put("linkid",Integer.toString(rs.getInt(3)));
						jo.put("icon","bom/map/gypart.gif");
						//CCBegin SS69
//						if(rs.getString(5)!=null&&rs.getString(5).equals("red"))
//						{
//							jo.put("color","red");
//						}
						if(rs.getString(5)!=null)
						{
							if(rs.getString(5).equals("red")){
								jo.put("color","red");
							}if(rs.getString(5).equals("green")){
								jo.put("color","green");
							}
						}
						//CCEnd SS69
						else
						{
							jo.put("color","black");
						}
						jo.put("effect","true");
						//CCBegin SS29
						//CCBegin SS42
//						jo.put("version",part.getVersionValue());
						jo.put("version",fbyIbaValue1);
						//CCEnd SS42
						//CCEnd SS29
						json.put(jo);
						zhizao="";
						zhuangpei="";
					}
				}

				// ��ղ��ر�sql��������
				rs.close();
				// �ر�Statement
				stmt.close();
				// �ر����ݿ�����
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(rs!=null)
					{
						rs.close();
					}
					if(stmt!=null)
					{
						stmt.close();
					}
					if(conn!=null)
					{
						conn.close();
					}
				}
				catch(SQLException ex1)
				{
					ex1.printStackTrace();
				}
			}
//			System.out.println(json.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * ����ָ���㲿����һ���Ӽ��ڵ㼯�ϣ�������ʾ����BOM�㲿���б� ��ȡδ��Ч�ṹ��û����ȡ��Ч�ṹ��
	 * 
	 * @param id
	 *            �㲿��bsoID
	 * @param carModelCode
	 *            ������
	 * @param dwbs
	 *            ��λ
	 * @return �㲿������һ���Ӽ������ṹ
	 */
	public String getGYBomList(String id,String carModelCode,String dwbs) throws QMException
	{
//		System.out.println("come in getGYBomList id=="+id+"  carModelCode=="+carModelCode+"  dwbs=="+dwbs);
		JSONArray json=new JSONArray();
		boolean flag=true;
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(id);
			// ���ø��ڵ�
			JSONObject jo=new JSONObject();

			// ��ȡ��ǩ
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			try
			{
				conn=PersistUtil.getConnection();
				stmt=conn.createStatement();
				Collection route=new ArrayList();
				String zhizao="";
				String zhuangpei="";
				// ���ϵͳ�д���δ��Ч��BOM���δ��Ч��bom
				//CCBegin SS72
//				String sql="select childPart,quantity,unit,id,bz1 from GYBomStructure where effectCurrent='0' and parentPart='"
				String sql="select childPart,quantity,unit,id,bz1,locker from GYBomStructure where effectCurrent='0' and parentPart='"
						//CCEnd SS72
					+id+"' and dwbs='"+dwbs+"' order by childNumber";
				rs=stmt.executeQuery(sql);

				String insql="";
				while(rs.next())
				{
					String childPart=rs.getString(1);
					part=(QMPartIfc)ps.refreshInfo(childPart);
					if(part==null)
					{
						System.out.println("δ�ҵ��Ӽ�id��"+childPart);
						continue;
					}
					if(insql.equals(""))
					{
						if(dwbs.equals("W34"))
						{
							insql="'"+part.getBsoID()+"'";
						}
						else
						{
							insql="'"+part.getMasterBsoID()+"'";
						}
					}
					else
					{
						if(dwbs.equals("W34"))
						{
							insql=insql+",'"+part.getBsoID()+"'";
						}
						else
						{
							insql=insql+",'"+part.getMasterBsoID()+"'";
						}
					}
				}
				HashMap map=new HashMap();
				if(!insql.equals(""))
				{
					if(dwbs.equals("W34"))
					{
						map=getSecondRoute(insql,map);
					}
					else
					{
						map=getRoute(insql,map);
					}
				}

				rs=stmt.executeQuery(sql);
				while(rs.next())
				{
					String childPart=rs.getString(1);
					part=(QMPartIfc)ps.refreshInfo(childPart);
					if(part==null)
					{
						System.out.println("δ�ҵ��Ӽ�id��"+childPart);
						continue;
					}

					if(dwbs.equals("W34"))
					{
						if(map.get(part.getBsoID())!=null)
						{
							String[] routestr=(String[])(map.get(part.getBsoID()));
							zhizao=routestr[0];
							zhuangpei=routestr[1];
						}
					}
					else
					{
						if(map.get(part.getMasterBsoID())!=null)
						{
							String[] routestr=(String[])(map.get(part.getMasterBsoID()));
							zhizao=routestr[0];
							zhuangpei=routestr[1];
						}
					}

					jo=new JSONObject();
					//CCBegni SS42
					String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",childPart);
					if(fbyIbaValue==""){
						fbyIbaValue = part.getVersionValue();
					}
					//CCEnd SS42
					jo.put("id",childPart);
					jo.put("pId",id);
					jo.put("num",part.getPartNumber());
					jo.put("pname",part.getPartName());
					//CCBegin SS42
//					jo.put("name",
//						part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "+part.getViewName());
					jo.put("name",
							part.getPartNumber()+" "+part.getPartName()+" "+fbyIbaValue+" "+part.getViewName());
					//CCEnd SS42
					jo.put("zz",zhizao);
					jo.put("zp",zhuangpei);
					jo.put("quantity",rs.getString(2));
					jo.put("dw",rs.getString(3));
					jo.put("linkid",Integer.toString(rs.getInt(4)));
					jo.put("open","false");
					//CCBegin SS69
//					if(rs.getString(5)!=null&&rs.getString(5).equals("red"))
//					{
//						jo.put("color","red");
//					}
					if(rs.getString(5)!=null)
					{
						if(rs.getString(5).equals("red")){
							jo.put("color","red");
						}if(rs.getString(5).equals("green")){
							jo.put("color","green");
						}
					}
					//CCEnd SS69
					else
					{
						jo.put("color","black");
					}
					//CCBegin SS72
//					jo.put("icon","bom/map/gypart.gif");
					if(rs.getString(6)!=null){
						jo.put("icon","js/easyui/themes/icons/lock.png");
//						jo.put("islock","true");
					}else{
						jo.put("icon","bom/map/gypart.gif");
//						jo.put("islock","false");
					}
					//�ڵ������������ԣ��Ƿ��˼���
//					boolean lockflag = searchLockUser(childPart,dwbs);
//					if(lockflag){
//						jo.put("isownlock","true");
//					}else{
//						jo.put("isownlock","false");
//					}
					//CCEnd SS72
					jo.put("effect","false");
					//CCBegin SS29
					//CCBegin SS42
//					jo.put("version",part.getVersionValue());
					jo.put("version",fbyIbaValue);
					//CCEnd SS42
					//CCEnd SS29
					json.put(jo);
					flag=false;
					zhizao="";
					zhuangpei="";
				}
				// ���ϵͳ�в�����δ��Ч��BOM�����Ч��bom
				if(flag)
				{
					sql="select childPart,quantity,unit,id,bz1 from GYBomStructure where effectCurrent='1' and parentPart='"
						+id+"' and dwbs='"+dwbs+"' order by childNumber";
					rs=stmt.executeQuery(sql);

					insql="";
					while(rs.next())
					{
						String childPart=rs.getString(1);
						part=(QMPartIfc)ps.refreshInfo(childPart);
						if(part==null)
						{
							System.out.println("δ�ҵ��Ӽ�id��"+childPart);
							continue;
						}
						if(insql.equals(""))
						{
							if(dwbs.equals("W34"))
							{
								insql="'"+part.getBsoID()+"'";
							}
							else
							{
								insql="'"+part.getMasterBsoID()+"'";
							}
						}
						else
						{
							if(dwbs.equals("W34"))
							{
								insql=insql+",'"+part.getBsoID()+"'";
							}
							else
							{
								insql=insql+",'"+part.getMasterBsoID()+"'";
							}
						}
					}
					map=new HashMap();
					if(!insql.equals(""))
					{
						if(dwbs.equals("W34"))
						{
							map=getSecondRoute(insql,map);
						}
						else
						{
							map=getRoute(insql,map);
						}
					}

					rs=stmt.executeQuery(sql);
					while(rs.next())
					{
						String childPart=rs.getString(1);
						part=(QMPartIfc)ps.refreshInfo(childPart);
						if(part==null)
						{
							System.out.println("δ�ҵ��Ӽ�id��"+childPart);
							continue;
						}

						if(dwbs.equals("W34"))
						{
							if(map.get(part.getBsoID())!=null)
							{
								String[] routestr=(String[])(map.get(part.getBsoID()));
								zhizao=routestr[0];
								zhuangpei=routestr[1];
							}
						}
						else
						{
							if(map.get(part.getMasterBsoID())!=null)
							{
								String[] routestr=(String[])(map.get(part.getMasterBsoID()));
								zhizao=routestr[0];
								zhuangpei=routestr[1];
							}
						}

						jo=new JSONObject();
						//CCBegni SS42
						String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",childPart);
						if(fbyIbaValue==""){
							fbyIbaValue = part.getVersionValue();
						}
						//CCEnd SS42
						jo.put("id",childPart);
						jo.put("pId",id);
						jo.put("num",part.getPartNumber());
						jo.put("pname",part.getPartName());
						//CCBegin SS42
//						jo.put(
//							"name",
//							part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "
//								+part.getViewName());
						jo.put(
								"name",
								part.getPartNumber()+" "+part.getPartName()+" "+fbyIbaValue+" "
									+part.getViewName());
						//CCEnd SS42
						jo.put("dw",rs.getString(3));
						jo.put("zz",zhizao);
						jo.put("zp",zhuangpei);
						jo.put("quantity",rs.getString(2));
						jo.put("linkid",Integer.toString(rs.getInt(4)));
						jo.put("open","false");
						//CCBegin SS69
//						if(rs.getString(5)!=null&&rs.getString(5).equals("red"))
//						{
//							jo.put("color","red");
//						}
						if(rs.getString(5)!=null)
						{
							if(rs.getString(5).equals("red")){
								jo.put("color","red");
							}if(rs.getString(5).equals("green")){
								jo.put("color","green");
							}
						}
						//CCEnd SS69
						else
						{
							jo.put("color","black");
						}
						jo.put("icon","bom/map/gypart.gif");
						jo.put("effect","true");
						//CCBegin SS29
						//CCBegin SS42
//						jo.put("version",part.getVersionValue());
						jo.put("version",fbyIbaValue);
						//CCEnd SS42
						//CCEnd SS29
						json.put(jo);
						flag=false;
						zhizao="";
						zhuangpei="";
					}
				}
				// �����û�У��򷵻�ʲô�������أ���ʼ��json��ȥ�����ڵ㡣
				if(flag)
				{
					json=new JSONArray();
				}

				// ��ղ��ر�sql��������
				rs.close();
				// �ر�Statement
				stmt.close();
				// �ر����ݿ�����
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(rs!=null)
					{
						rs.close();
					}
					if(stmt!=null)
					{
						stmt.close();
					}
					if(conn!=null)
					{
						conn.close();
					}
				}
				catch(SQLException ex1)
				{
					ex1.printStackTrace();
				}
			}
//			System.out.println(json.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * �������
	 * pid+partNumbe+partName+selectst+selectdw+selectlx+selectly+selectsmzq
	 * +selectzlj+carModelCode+dwbs ������+���+����+��ͼ+��λ+����+��Դ+��������+���ϼ�+������+����
	 */
	public String createPart(String s) throws QMException
	{
		//CCBegin SS35
//		String[] str=s.split(",");
		String[] str=s.split("#@");
		//CCEnd SS35

		PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
		StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
		UserInfo user=getCurrentUserInfo();
		String lifeCyID=PublishHelper.getLifeCyID(str[7]);
		QMPartIfc part=new QMPartInfo();
		//CCBegin SS67
		QMQuery query=new QMQuery("QMPartMaster");
		query.addCondition(new QueryCondition("partNumber","=",str[1]));
		Collection co=ps.findValueInfo(query,false);
		if(co.size()>0)
		{
			QMPartMasterIfc mast=(QMPartMasterIfc)co.toArray()[0];
			part=getPartByMasterID(mast.getBsoID());
			return "[{0}]";
		}else{
		//CCEnd SS67	
			part.setPartNumber(str[1]);
			part.setPartName(str[2]);
			// CCBegin SS2
			part.setLifeCycleState(LifeCycleState.toLifeCycleState("PREPARING"));// ��������׼��״̬
			// CCEnd SS2
			part.setViewName(str[3]);
			part.setDefaultUnit(str[4]);
			part.setPartType(str[5]);// Assembly
			part.setProducedBy(str[6]);
			part.setIterationCreator(user.getBsoID());
			part.setIterationModifier(user.getBsoID());
			part.setIterationNote("");
			part.setOwner("");
			part=(QMPartInfo)PublishHelper.assignFolder(part,str[8]);
			part.setLifeCycleTemplate(lifeCyID);
			part=sps.savePart(part);
		//CCBegin SS67
		}
		//CCEnd SS67

		JSONArray json=new JSONArray();
		JSONObject jo=new JSONObject();
		try
		{
			if(str[0].equals("0"))
			{
				//CCBegni SS42
				String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",part.getBsoID());
				if(fbyIbaValue==""){
					fbyIbaValue = part.getVersionValue();
				}
				//CCEnd SS42
				// ���ø��ڵ�
				jo.put("id",part.getBsoID());
				jo.put("pId","0");
				jo.put("num",part.getPartNumber());
				jo.put("pname",part.getPartName());
				//CCBegin SS42
//				jo.put("name",
//					part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "+part.getViewName());
				jo.put("name",
						part.getPartNumber()+" "+part.getPartName()+" "+fbyIbaValue+" "+part.getViewName());
				//CCEnd SS42
				jo.put("open","true");
				jo.put("icon","bom/map/gyopenpart.gif");
				jo.put("quantity","");
				jo.put("effect","false");
				//CCBegin SS29
				//CCBegin SS42
//				jo.put("version",part.getVersionValue());
				jo.put("version",fbyIbaValue);
				//CCEnd SS42
				//CCEnd SS29
				json.put(jo);
			}
			else
			{
				// parentPart,childPart,quantity,carModelCode,dwbs,effectCurrent,locker,lockDate,unit,childNumber
				//CCBegin SS70
//				String[] mt=new String[]{str[0],part.getBsoID(),"1",str[9],str[10],"0","","","��",part.getPartNumber()};
				String[] mt=new String[]{str[0],part.getBsoID(),"1",str[9],str[10],"0","","","��",part.getPartNumber(),"red"};
				//CCEnd SS70
				String linkid=saveGYBom(mt);
				//CCBegni SS42
				String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",part.getBsoID());
				if(fbyIbaValue==""){
					fbyIbaValue = part.getVersionValue();
				}
				//CCEnd SS42
				jo.put("id",part.getBsoID());
				jo.put("pId",str[0]);
				jo.put("num",part.getPartNumber());
				jo.put("pname",part.getPartName());
				//CCBegin SS42
//				jo.put("name",
//					part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "+part.getViewName());
				jo.put("name",
						part.getPartNumber()+" "+part.getPartName()+" "+fbyIbaValue+" "+part.getViewName());
				//CCEnd SS42
				jo.put("zz","");
				jo.put("zp","");
				jo.put("quantity","1");
				jo.put("dw","��");
				jo.put("linkid",linkid);
				jo.put("open","false");
				//CCBegin SS70
//				jo.put("color","black");
				jo.put("color","red");
				//CCEnd SS70
				jo.put("icon","bom/map/gypart.gif");
				//CCBegin SS29
				//CCBegin SS42
//				jo.put("version",part.getVersionValue());
				jo.put("version",fbyIbaValue);
				//CCEnd SS42
				//CCEnd SS29
				json.put(jo);
				//CCBegin SS70
				String updatesql = "update GYBomStructure set bz1='red' where (childPart='"+str[0]+"') and effectCurrent='0'";
				updateNodeColor(updatesql);
				//CCEnd SS70
			}
		}
		catch(Exception e)
		{
		    //SS14 Begin
//		    System.out.println("e.getLocalizedMessage()  =========  " + e.getLocalizedMessage());
		    if(e instanceof QMException)
		    {
		        QMException qe = (QMException)e;
		        try
	            {
//		            System.out.println("qe.getClientMessage()  =========  " + qe.getClientMessage());
	                jo.put("error",qe.getClientMessage());
	            }catch(JSONException e1)
	            {
	                e1.printStackTrace();
	            }
	            json.put(jo);
		    }
			e.printStackTrace();
			//SS14 End
		}

		return json.toString();
	}

	/**
	 * ���ù���bom��Ч ����ָ���㲿���������ṹ���õ�δ��Ч�ṹ �ĸ������ϡ� ɾ�� ���������� ������Ч�ṹ�� 
	 * �� ��������������δ��Ч�ṹ��Ϊ��Ч��
	 */
	public void setValidBom(String id) throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		Statement stmt1=null;
		try
		{
			String dwbs=getCurrentDWBS();
//			System.out.println("setValidBom id=="+id+"  and dwbs=="+dwbs);
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();

			// �����㲿����δ��Ч�ṹ�ĸ���
			Vector parts=new Vector();
			parts=getUnEffParts(id,dwbs,parts);
//			System.out.println("�õ�δ��Ч������"+parts.size());
			for(int i=0;i<parts.size();i++)
			{
				String str=parts.elementAt(i).toString();
//				System.out.println("δ��Ч������"+str);
				// ɾ����Ч�ṹ��
				// deleteGYBomLevel(str, dwbs, "1");
				stmt.executeQuery("delete GYBomStructure where effectCurrent='1' and parentPart='"+str+"' and dwbs='"
					+dwbs+"'");
				// ��δ��Ч��Ϊ��Ч��
				//CCBegin SS68
//				stmt.executeQuery("update GYBomStructure set effectCurrent='1' where effectCurrent='0' and parentPart='"
//					+str+"' and dwbs='"+dwbs+"'");
//				stmt.executeQuery("update GYBomStructure set effectCurrent='1' where effectCurrent='0' and parentPart='"
//						+str+"' and dwbs='"+dwbs+"' and bz1='green'");
				stmt.executeQuery("update GYBomStructure set effectCurrent='1',bz1='green' where effectCurrent='0' and parentPart='"
						+str+"' and dwbs='"+dwbs+"'");
				//CCEnd SS68
			}
			//CCBegin SS52
			deleteBOMContent(id,dwbs);
			//CCEnd SS52
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
	}

	/**
	 * ����ָ���㲿������һ���Ӽ��ڵ㼯�� ��������㲿�����bom��Դ��������flag��������ʱ���л�ȡ���ǻ�ȡ���½ṹ��
	 * 
	 * @param id
	 *            �㲿��bsoID
	 * @param flag
	 *            �Ƿ��ȡ��Ʊ�ǩ��
	 * @param carModelCode
	 *            ������
	 * @param dwbs
	 *            ��λ
	 * @return �㲿������һ���Ӽ������ṹ
	 */
	public String getDesignBom(String id,String flag,String carModelCode,String dwbs) throws QMException
	{
//		System.out.println("come in getDesignBom   id=="+id+"  flag=="+flag+"  carModelCode=="+carModelCode+"  dwbs=="
//			+dwbs);
		// flag = "true";
		// carModelCode = "B143H26561T9K01";
		// dwbs = "�ɶ�����";
		JSONArray json=new JSONArray();
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			boolean nosub=true;
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(id);
			Collection route=new ArrayList();
			String zhizao="";
			String zhuangpei="";
			String colors="";
			// ���ø��ڵ�
			JSONObject jo=new JSONObject();
			//CCBegni SS42
			String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",id);
			if(fbyIbaValue==""){
				fbyIbaValue = part.getVersionValue();
			}
			//CCEnd SS42
			jo.put("id",id);
			jo.put("pId","0");
			jo.put("num",part.getPartNumber());
			jo.put("pname",part.getPartName());
			//CCBegin SS42
//			jo.put("name",part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "+part.getViewName());
			jo.put("name",part.getPartNumber()+" "+part.getPartName()+" "+fbyIbaValue+" "+part.getViewName());
			//CCEnd SS42
			jo.put("open","true");
			jo.put("icon","bom/map/openpart.gif");
			jo.put("color","");
			jo.put("quantity","");
			//CCBegin SS29
			//CCBegin SS42
//			jo.put("version",part.getVersionValue());
			jo.put("version",fbyIbaValue);
			//CCEnd SS42
			//CCEnd SS29
			json.put(jo);

			// ��ȡ��ǩ
			if(flag.equals("true"))
			{
				Connection conn=null;
				Statement stmt=null;
				ResultSet rs=null;
				try
				{
					UserInfo user=getCurrentUserInfo();
					conn=PersistUtil.getConnection();
					stmt=conn.createStatement();
					String sql="select childPart,quantity,color,unit,createUser from GYBomStructureLable where parentPart='"
						+id+"' and carModelCode='"+carModelCode+"' and dwbs='"+dwbs+"' order by childNumber";
//					System.out.println(sql);
					rs=stmt.executeQuery(sql);

					String insql="";
					while(rs.next())
					{
						String childPart=rs.getString(1);
						part=(QMPartIfc)ps.refreshInfo(childPart);
						if(part==null)
						{
							System.out.println("δ�ҵ��Ӽ�id��"+childPart);
							continue;
						}
						if(insql.equals(""))
						{
							if(dwbs.equals("W34"))
							{
								insql="'"+part.getBsoID()+"'";
							}
							else
							{
								insql="'"+part.getMasterBsoID()+"'";
							}
						}
						else
						{
							if(dwbs.equals("W34"))
							{
								insql=insql+",'"+part.getBsoID()+"'";
							}
							else
							{
								insql=insql+",'"+part.getMasterBsoID()+"'";
							}
						}
						nosub=false;
					}
					HashMap map=new HashMap();
					if(!insql.equals(""))
					{
						if(dwbs.equals("W34"))
						{
							map=getSecondRoute(insql,map);
						}
						else
						{
							map=getRoute(insql,map);
						}
					}

					rs=stmt.executeQuery(sql);
					while(rs.next())
					{
						String childPart=rs.getString(1);
						part=(QMPartIfc)ps.refreshInfo(childPart);
						if(part==null)
						{
							System.out.println("δ�ҵ��Ӽ�id��"+childPart);
							continue;
						}

						if(dwbs.equals("W34"))
						{
							if(map.get(part.getBsoID())!=null)
							{
								String[] routestr=(String[])(map.get(part.getBsoID()));
								zhizao=routestr[0];
								zhuangpei=routestr[1];
							}
						}
						else
						{
							if(map.get(part.getMasterBsoID())!=null)
							{
								String[] routestr=(String[])(map.get(part.getMasterBsoID()));
								zhizao=routestr[0];
								zhuangpei=routestr[1];
							}
						}
//						System.out.println("����3======"+rs.getString(2));
						// System.out.println("rs.getString(1)=="+rs.getString(1));
						jo=new JSONObject();
						//CCBegni SS42
						String fbyIbaValue1 = RationHelper.getIbaValueByName("sourceVersion",childPart);
						if(fbyIbaValue1==""){
							fbyIbaValue1 = part.getVersionValue();
						}
						//CCEnd SS42
						jo.put("id",childPart);
						jo.put("pId",id);
						jo.put("num",part.getPartNumber());
						jo.put("pname",part.getPartName());
						//CCBegin SS42
//						jo.put(
//							"name",
//							part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "
//								+part.getViewName());
						jo.put(
								"name",
								part.getPartNumber()+" "+part.getPartName()+" "+fbyIbaValue+" "
									+part.getViewName());
						//CCEnd SS42
						jo.put("open","false");
						jo.put("icon","bom/map/part.gif");
						jo.put("zz",zhizao);
						jo.put("zp",zhuangpei);
						colors=rs.getString(5);
						// System.out.println("colors=="+colors);
						if(colors==null||colors.equals(""))// û���û���ק��¼����ɫ
						{// System.out.println("111");
							jo.put("color","black");
						}
						else
						{
							if(colors.indexOf(user.getBsoID())!=-1)// ��ǰ�û���ק������ɫ
							{// System.out.println("222");
								jo.put("color","red");
							}
							else
							// ��ǰ�û�û����ק��������������ק������ɫ
							{// System.out.println("333");
								jo.put("color","#FFB90F");
							}
						}
						jo.put("createUser",rs.getString(5));
						jo.put("quantity",rs.getString(2));
						jo.put("dw",rs.getString(4));
						//CCBegin SS29
						//CCBegin SS42
//						jo.put("version",part.getVersionValue());
						jo.put("version",fbyIbaValue1);
						//CCEnd SS42
						//CCEnd SS29
						json.put(jo);

						json=bianliDesignBom(childPart,carModelCode,dwbs,user.getBsoID(),json);
						zhizao="";
						zhuangpei="";
					}
					// ��ղ��ر�sql��������
					rs.close();
					// �ر�Statement
					stmt.close();
					// �ر����ݿ�����
					conn.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						if(rs!=null)
						{
							rs.close();
						}
						if(stmt!=null)
						{
							stmt.close();
						}
						if(conn!=null)
						{
							conn.close();
						}
					}
					catch(SQLException ex1)
					{
						ex1.printStackTrace();
					}
				}
			}
//			System.out.println("nosub=="+nosub);
			if(nosub)// ��ȡ���bom
			{
				StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
				VersionControlService vcservice=(VersionControlService)EJBServiceHelper
					.getService("VersionControlService");
				JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
				PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
				Collection linkcoll=sps.sort(jfs.getUsesPartIfcs(part,configSpecIfc));
				if(linkcoll!=null&&linkcoll.size()>0)
				{
					Iterator iter1=linkcoll.iterator();
					String insql="";
					while (iter1.hasNext()) {
						Object obj[] = (Object[]) iter1.next();
						PartUsageLinkIfc link = (PartUsageLinkIfc) obj[0];
						// CCBegin SS64
						if (obj[1] instanceof QMPartIfc) {
							// CCBegin SS64
							part = (QMPartIfc) obj[1];
							if (insql.equals("")) {
								if (dwbs.equals("W34")) {
									insql = "'" + part.getBsoID() + "'";
								} else {
									insql = "'" + part.getMasterBsoID() + "'";
								}
							} else {
								if (dwbs.equals("W34")) {
									insql = insql + ",'" + part.getBsoID()
											+ "'";
								} else {
									insql = insql + ",'"
											+ part.getMasterBsoID() + "'";
								}
							}
							// CCBegin SS64
						}
						// CCEnd SS64
					}
					HashMap map=new HashMap();
					if(!insql.equals(""))
					{
						if(dwbs.equals("W34"))
						{
							map=getSecondRoute(insql,map);
						}
						else
						{
							map=getRoute(insql,map);
						}
					}

					Iterator iter=linkcoll.iterator();
					QMPartMasterIfc partMaster=null;
					while (iter.hasNext()) {
						Object obj[] = (Object[]) iter.next();
						PartUsageLinkIfc link = (PartUsageLinkIfc) obj[0];
						// CCBegin SS64
						if (obj[1] instanceof QMPartIfc) {
							// CCEnd SS64
							part = (QMPartIfc) obj[1];

							if (dwbs.equals("W34")) {
								if (map.get(part.getBsoID()) != null) {
									String[] routestr = (String[]) (map
											.get(part.getBsoID()));
									zhizao = routestr[0];
									zhuangpei = routestr[1];
								}
							} else {
								if (map.get(part.getMasterBsoID()) != null) {
									String[] routestr = (String[]) (map
											.get(part.getMasterBsoID()));
									zhizao = routestr[0];
									zhuangpei = routestr[1];
								}
							}
							// System.out.println("����4======"+String.valueOf(link.getQuantity()));
							jo = new JSONObject();
							// CCBegni SS42
							String fbyIbaValue2 = RationHelper
									.getIbaValueByName("sourceVersion",
											part.getBsoID());
							if (fbyIbaValue2 == "") {
								fbyIbaValue2 = part.getVersionValue();
							}
							// CCEnd SS42
							jo.put("id", part.getBsoID());
							jo.put("pId", id);
							jo.put("num", part.getPartNumber());
							jo.put("pname", part.getPartName());
							// CCBegin SS42
							// jo.put(
							// "name",
							// part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "
							// +part.getViewName());
							jo.put("name",
									part.getPartNumber() + " "
											+ part.getPartName() + " "
											+ fbyIbaValue2 + " "
											+ part.getViewName());
							// CCEnd SS42
							jo.put("open", "false");
							jo.put("icon", "bom/map/part.gif");
							jo.put("color", "black");
							jo.put("zz", zhizao);
							jo.put("zp", zhuangpei);
							jo.put("createUser", "");
							// CCBegin SS11
							// jo.put("quantity",String.valueOf(link.getQuantity()));
							String qstr = String.valueOf(link.getQuantity());
							String qstr1 = qstr
									.substring(0, qstr.indexOf(".0"));
							jo.put("quantity", qstr1);
							// CCEnd SS11
							jo.put("dw", link.getDefaultUnit().getDisplay());
							// CCBegin SS29
							// CCBegin SS42
							// jo.put("version",part.getVersionValue());
							jo.put("version", fbyIbaValue2);
							// CCEnd SS42
							// CCEnd SS29
							json.put(jo);
							zhizao = "";
							zhuangpei = "";
						}
						// CCBegin SS64
					}
					// CCEnd SS64
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
//		System.out.println(json.toString());
		return json.toString();
	}

	private JSONArray bianliDesignBom(String id,String carModelCode,String dwbs,String userID,JSONArray json)
		throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			JSONObject jo=new JSONObject();
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(id);
			Collection route=new ArrayList();
			String zhizao="";
			String zhuangpei="";
			String colors="";
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			String sql="select childPart,quantity,color,unit,createUser from GYBomStructureLable where parentPart='"+id
				+"' and carModelCode='"+carModelCode+"' and dwbs='"+dwbs+"' order by childNumber";
			rs=stmt.executeQuery(sql);
			String insql="";
			while(rs.next())
			{
				String childPart=rs.getString(1);
				part=(QMPartIfc)ps.refreshInfo(childPart);
				if(part==null)
				{
					System.out.println("δ�ҵ��Ӽ�id��"+childPart);
					continue;
				}
				if(insql.equals(""))
				{
					if(dwbs.equals("W34"))
					{
						insql="'"+part.getBsoID()+"'";
					}
					else
					{
						insql="'"+part.getMasterBsoID()+"'";
					}
				}
				else
				{
					if(dwbs.equals("W34"))
					{
						insql=insql+",'"+part.getBsoID()+"'";
					}
					else
					{
						insql=insql+",'"+part.getMasterBsoID()+"'";
					}
				}
			}
			HashMap map=new HashMap();
			if(!insql.equals(""))
			{
				if(dwbs.equals("W34"))
				{
					map=getSecondRoute(insql,map);
				}
				else
				{
					map=getRoute(insql,map);
				}
			}

			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				String childPart=rs.getString(1);
				part=(QMPartIfc)ps.refreshInfo(childPart);
				if(part==null)
				{
					System.out.println("δ�ҵ��Ӽ�id��"+childPart);
					continue;
				}

				if(dwbs.equals("W34"))
				{
					if(map.get(part.getBsoID())!=null)
					{
						String[] routestr=(String[])(map.get(part.getBsoID()));
						zhizao=routestr[0];
						zhuangpei=routestr[1];
					}
				}
				else
				{
					if(map.get(part.getMasterBsoID())!=null)
					{
						String[] routestr=(String[])(map.get(part.getMasterBsoID()));
						zhizao=routestr[0];
						zhuangpei=routestr[1];
					}
				}

				jo=new JSONObject();
				//CCBegni SS42
				String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",childPart);
				if(fbyIbaValue==""){
					fbyIbaValue = part.getVersionValue();
				}
				//CCEnd SS42
				jo.put("id",childPart);
				jo.put("pId",id);
				jo.put("num",part.getPartNumber());
				jo.put("pname",part.getPartName());
				//CCBegin SS42
//				jo.put("name",
//					part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "+part.getViewName());
				jo.put("name",
						part.getPartNumber()+" "+part.getPartName()+" "+fbyIbaValue+" "+part.getViewName());
				//CCEnd SS42
				jo.put("open","false");
				jo.put("icon","bom/map/part.gif");
				jo.put("zz",zhizao);
				jo.put("zp",zhuangpei);
				// jo.put("color",rs.getString(3));

				colors=rs.getString(5);
				if(colors==null||colors.equals(""))// û���û���ק��¼����ɫ
				{
					jo.put("color","black");
				}
				else
				{
					if(colors.indexOf(userID)!=-1)// ��ǰ�û���ק������ɫ
					{
						jo.put("color","red");
					}
					else
					// ��ǰ�û�û����ק��������������ק������ɫ
					{
						jo.put("color","#FFB90F");
					}
				}

				jo.put("createUser",rs.getString(5));
				jo.put("quantity",rs.getString(2));
				jo.put("dw",rs.getString(4));
				//CCBegin SS29
				//CCBegin SS42
//				jo.put("version",part.getVersionValue());
				jo.put("version",fbyIbaValue);
				//CCEnd SS42
				//CCEnd SS29
				json.put(jo);
				json=bianliDesignBom(childPart,carModelCode,dwbs,userID,json);
				zhizao="";
				zhuangpei="";
			}
			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return json;
	}

	/**
	 * ����ָ���㲿����һ���Ӽ��ڵ㼯�ϣ�������ʾ���BOM�㲿���б�
	 * 
	 * @param id
	 *            �㲿��bsoID
	 * @param flag
	 *            �Ƿ��ȡ��Ʊ�ǩ��
	 * @param carModelCode
	 *            ������
	 * @param dwbs
	 *            ��λ
	 * @return �㲿������һ���Ӽ������ṹ
	 */
	public String getDesignBomList(String id,String flag,String carModelCode,String dwbs) throws QMException
	{
//		System.out.println("come in getDesignBomList   id=="+id+"  flag=="+flag+"  carModelCode=="+carModelCode
//			+"  dwbs=="+dwbs);
		JSONArray json=new JSONArray();
		try
		{
			boolean nosub=true;
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(id);
			// ���ø��ڵ�
			JSONObject jo=new JSONObject();

			Collection ytree=new ArrayList();
			Collection route=new ArrayList();
			String zhizao="";
			String zhuangpei="";
			String colors="";

			// ��ȡ��ǩ
			if(flag.equals("true"))
			{
				Connection conn=null;
				Statement stmt=null;
				ResultSet rs=null;
				try
				{
					UserInfo user=getCurrentUserInfo();
					conn=PersistUtil.getConnection();
					stmt=conn.createStatement();
					String sql="select childPart,quantity,unit,color,createUser from GYBomStructureLable where parentPart='"
						+id+"' and carModelCode='"+carModelCode+"' and dwbs='"+dwbs+"' order by childNumber";
					rs=stmt.executeQuery(sql);

					String insql="";
					while(rs.next())
					{
						String childPart=rs.getString(1);
						part=(QMPartIfc)ps.refreshInfo(childPart);
						if(part==null)
						{
							System.out.println("δ�ҵ��Ӽ�id��"+childPart);
							continue;
						}
						if(insql.equals(""))
						{
							if(dwbs.equals("W34"))
							{
								insql="'"+part.getBsoID()+"'";
							}
							else
							{
								insql="'"+part.getMasterBsoID()+"'";
							}
						}
						else
						{
							if(dwbs.equals("W34"))
							{
								insql=insql+",'"+part.getBsoID()+"'";
							}
							else
							{
								insql=insql+",'"+part.getMasterBsoID()+"'";
							}
						}
						nosub=false;
					}
					HashMap map=new HashMap();
					if(!insql.equals(""))
					{
						if(dwbs.equals("W34"))
						{
							map=getSecondRoute(insql,map);
						}
						else
						{
							map=getRoute(insql,map);
						}
					}

					rs=stmt.executeQuery(sql);
					while(rs.next())
					{
						String childPart=rs.getString(1);
						part=(QMPartIfc)ps.refreshInfo(childPart);
						if(part==null)
						{
							System.out.println("δ�ҵ��Ӽ�id��"+childPart);
							continue;
						}

						if(dwbs.equals("W34"))
						{
							if(map.get(part.getBsoID())!=null)
							{
								String[] routestr=(String[])(map.get(part.getBsoID()));
								zhizao=routestr[0];
								zhuangpei=routestr[1];
							}
						}
						else
						{
							if(map.get(part.getMasterBsoID())!=null)
							{
								String[] routestr=(String[])(map.get(part.getMasterBsoID()));
								zhizao=routestr[0];
								zhuangpei=routestr[1];
							}
						}
//						System.out.println("����1======"+rs.getString(2));
						//CCBegni SS42
						String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",childPart);
						if(fbyIbaValue==""){
							fbyIbaValue = part.getVersionValue();
						}
						//CCEnd SS42
						jo=new JSONObject();
						jo.put("id",childPart);
						jo.put("pId",id);
						jo.put("num",part.getPartNumber());
						//CCBegin SS42
//						jo.put(
//							"name",
//							part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "
//								+part.getViewName());
						jo.put(
								"name",
								part.getPartNumber()+" "+part.getPartName()+" "+fbyIbaValue+" "
									+part.getViewName());
						//CCEnd SS42
						jo.put("pname",part.getPartName());
						jo.put("quantity",rs.getString(2));
						jo.put("zz",zhizao);
						jo.put("zp",zhuangpei);
						jo.put("dw",rs.getString(3));
						// jo.put("color",rs.getString(4));

						colors=rs.getString(4);
						if(colors==null||colors.equals(""))// û���û���ק��¼����ɫ
						{
							jo.put("color","black");
						}
						else
						{
							if(colors.indexOf(user.getBsoID())!=-1)// ��ǰ�û���ק������ɫ
							{
								jo.put("color","red");
							}
							else
							// ��ǰ�û�û����ק��������������ק������ɫ
							{
								jo.put("color","#FFB90F");
							}
						}
						jo.put("createUser",rs.getString(4));
						jo.put("open","false");
						jo.put("icon","bom/map/part.gif");
						//CCBegin SS29
						//CCBegin SS42 
//						jo.put("version",part.getVersionValue());
						jo.put("version",fbyIbaValue);
						//CCEnd SS42
						//CCEnd SS29
						json.put(jo);

						zhizao="";
						zhuangpei="";
					}
					// ��ղ��ر�sql��������
					rs.close();
					// �ر�Statement
					stmt.close();
					// �ر����ݿ�����
					conn.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally
				{
					try
					{
						if(rs!=null)
						{
							rs.close();
						}
						if(stmt!=null)
						{
							stmt.close();
						}
						if(conn!=null)
						{
							conn.close();
						}
					}
					catch(SQLException ex1)
					{
						ex1.printStackTrace();
					}
				}
			}
			if(nosub)// ��ȡ���bom
			{
				StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
				VersionControlService vcservice=(VersionControlService)EJBServiceHelper
					.getService("VersionControlService");
				JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
				PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
				Collection linkcoll=sps.sort(jfs.getUsesPartIfcs(part,configSpecIfc));
				if(linkcoll!=null&&linkcoll.size()>0)
				{
					Iterator iter1=linkcoll.iterator();
					String insql="";
					while(iter1.hasNext())
 {
						Object obj[] = (Object[]) iter1.next();
						PartUsageLinkIfc link = (PartUsageLinkIfc) obj[0];
						// CCBegin SS64
						if (obj[1] instanceof QMPartIfc) {
							// CCEnd SS64
							part = (QMPartIfc) obj[1];
							if (insql.equals("")) {
								if (dwbs.equals("W34")) {
									insql = "'" + part.getBsoID() + "'";
								} else {
									insql = "'" + part.getMasterBsoID() + "'";
								}
							} else {
								if (dwbs.equals("W34")) {
									insql = insql + ",'" + part.getBsoID()
											+ "'";
								} else {
									insql = insql + ",'"
											+ part.getMasterBsoID() + "'";
								}
							}
							// CCBegin SS64
						}
						// CCEnd SS64
					}
					HashMap map=new HashMap();
					if(!insql.equals(""))
					{
						if(dwbs.equals("W34"))
						{
							map=getSecondRoute(insql,map);
						}
						else
						{
							map=getRoute(insql,map);
						}
					}

					Iterator iter=linkcoll.iterator();
					QMPartMasterIfc partMaster=null;
					while (iter.hasNext()) {
						Object obj[] = (Object[]) iter.next();
						PartUsageLinkIfc link = (PartUsageLinkIfc) obj[0];
						// CCBegin SS64
						if (obj[1] instanceof QMPartIfc) {
							// CCEnd SS64
							part = (QMPartIfc) obj[1];

							if (dwbs.equals("W34")) {
								if (map.get(part.getBsoID()) != null) {
									String[] routestr = (String[]) (map
											.get(part.getBsoID()));
									zhizao = routestr[0];
									zhuangpei = routestr[1];
								}
							} else {
								if (map.get(part.getMasterBsoID()) != null) {
									String[] routestr = (String[]) (map
											.get(part.getMasterBsoID()));
									zhizao = routestr[0];
									zhuangpei = routestr[1];
								}
							}
							String partnumber = part.getPartNumber();
							String partid = part.getBsoID();
//							if(partnumber.contains("3103070")){
//								System.out.println("����·��=="+zhizao+"==װ��·��=="+zhuangpei+"==����=="+dwbs);
//							}
							// System.out.println("����2======"+String.valueOf(link.getQuantity()));
							jo = new JSONObject();
							// CCBegni SS42
							String fbyIbaValue = RationHelper
									.getIbaValueByName("sourceVersion",
											part.getBsoID());
							if (fbyIbaValue == "") {
								fbyIbaValue = part.getVersionValue();
							}
							// CCEnd SS42
							jo.put("id", part.getBsoID());
							jo.put("pId", id);
							jo.put("num", part.getPartNumber());
							jo.put("pname", part.getPartName());
							// CCBegin SS42
							// jo.put(
							// "name",
							// part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "
							// +part.getViewName());
							jo.put("name",
									part.getPartNumber() + " "
											+ part.getPartName() + " "
											+ fbyIbaValue + " "
											+ part.getViewName());
							// CCEnd SS42
							jo.put("dw", link.getDefaultUnit().getDisplay());
							// CCBegin SS11
							// jo.put("quantity",String.valueOf(link.getQuantity()));
							String qstr = String.valueOf(link.getQuantity());
							String qstr1 = qstr
									.substring(0, qstr.indexOf(".0"));
							jo.put("quantity", qstr1);
							// CCEnd SS11
							jo.put("zz", zhizao);
							jo.put("zp", zhuangpei);
							jo.put("color", "black");
							jo.put("createUser", "");
							jo.put("open", "false");
							jo.put("icon", "bom/map/part.gif");
							// CCBegin SS29
							// CCBegin SS42
							// jo.put("version",part.getVersionValue());
							jo.put("version", fbyIbaValue);
							// CCEnd SS42
							// CCEnd SS29
							json.put(jo);

							// parentPart,childPart,quantity,createTime,modifyTime,carModelCode,dwbs,createUser,color,unit,childNumber
							String[] str = new String[9];
							str[0] = id;
							str[1] = part.getBsoID();
							// CCBegin SS11
							// str[2]=String.valueOf(link.getQuantity());
							str[2] = String.valueOf(qstr1);
							// CCEnd SS11
							str[3] = carModelCode;
							str[4] = dwbs;
							str[5] = "";
							str[6] = "";
							str[7] = link.getDefaultUnit().getDisplay();
							str[8] = part.getPartNumber();
							ytree.add(str);
							zhizao = "";
							zhuangpei = "";
						}
						// CCBegin SS64
					}
					// CCEnd SS64
				}
			}
			if(ytree.size()>0)
			{
				saveBom(ytree);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

//		System.out.println(json.toString());
		return json.toString();
	}

	/**
	 * �������������bom�� ���ݱ�ź����������㲿��
	 * 
	 * @param name
	 *            �㲿������
	 * @param number1
	 *            �㲿�����
	 * @param ignoreCase
	 *            ���Դ�Сд
	 * @return �����������㲿������
	 * @exception com.faw_qm.framework.exceptions.QMException
	 */
	public String findPart(String number1,String name,String ignoreCase) throws QMException
	{
//		System.out.println("come in findPart name=="+name+"  number1=="+number1+"  ignoreCase=="+ignoreCase);
		// Vector col1=new Vector();
		JSONArray json=new JSONArray();
		try
		{
			StandardPartService spService=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			VersionControlService vcService=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
			Collection col=getAllPartMasters(name.trim(),number1.trim(),(ignoreCase.equals("true")?true:false));
//			System.out.println("col.size=="+col.size());
			Iterator ite=col.iterator();
			while(ite.hasNext())
			{
				QMPartMasterIfc partmaster=(QMPartMasterIfc)ite.next();
				Collection co=vcService.allVersionsOf(partmaster);
				Iterator itea=co.iterator();
				QMPartIfc partIfc=null;
				while(itea.hasNext())
				{
				    // CCBegin SS13
				    Object obj = itea.next();
				    if(obj instanceof GenericPartIfc)
				    {
				        //System.out.println("����");
				        continue;
				    }
					partIfc=(QMPartIfc)obj;
					// CCEnd SS13
					// QMPartIfc
					// partIfc=(QMPartIfc)vcService.getLatestIteration(partIfcAny);
					if(partIfc!=null)
					{
						JSONObject jo=new JSONObject();
						jo.put("id",partIfc.getBsoID());
						jo.put("num",partIfc.getPartNumber());
						jo.put("name",partIfc.getPartName());
						//CCBegni SS42
//						jo.put("version",partIfc.getVersionValue());
						String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",partIfc.getBsoID());
						if(fbyIbaValue==""){
							fbyIbaValue = partIfc.getVersionValue();
						}
						jo.put("version",fbyIbaValue);
						//CCEnd SS42
						json.put(jo);
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
//		System.out.println("findPart json=="+json.toString());
		return json.toString();
	}

	/**
	 * ͨ�����ƺͺ���������㲿��������ģ����ѯ�� ���nameΪnull���������ѯ�����numberΪnull�������Ʋ�ѯ��
	 * ���name��numnber��Ϊnull������������㲿����ֵ����
	 * 
	 * @param name
	 *            :String ģ����ѯ���㲿�����ơ�
	 * @param number
	 *            :String ģ����ѯ���㲿���ĺ��롣
	 * @return collection:Collection ���ϲ�ѯ���������㲿����ֵ����ļ��ϡ�
	 * @throws QMException
	 */
	private Collection getAllPartMasters(String name,String number,boolean ignoreCase) throws QMException
	{
		ignoreCase=true;
		PersistService pservice=(PersistService)EJBServiceHelper.getPersistService();
		QMQuery query=new QMQuery("QMPartMaster");
		boolean flag=false;
		if((name!=null)&&(name.length()>0))
		{
			QueryCondition condition1=null;
			if(name.indexOf("*")!=-1)
			{
				condition1=new QueryCondition("partName","like",name.replace("*","%"));
			}
			else
			{
				condition1=new QueryCondition("partName","=",name);
			}
			query.addCondition(condition1);
			flag=true;
		}
		if((number!=null)&&(number.length()>0))
		{
			if(flag==true)
			{
				query.addAND();
			}
			QueryCondition condition2=null;
			if(number.indexOf("*")!=-1)
			{
				condition2=new QueryCondition("partNumber","like",number.replace("*","%"));
			}
			else
			{
				condition2=new QueryCondition("partNumber","=",number);
			}
			query.addCondition(condition2);
		}
		query.setIgnoreCase(ignoreCase);
		return pservice.findValueInfo(query);
	}

	/**
	 * �ṹ�Ƚ� ��ȡ���BOM�ṹ������BOM�ṹ���Ƚ��㲿��������������
	 */
	public String CompartTreeResult(String desginID,String gyID,String carModelCode,String dwbs) throws QMException
	{
		JSONArray json=new JSONArray();
		JSONObject jo=new JSONObject();
		try
		{
			long t1=System.currentTimeMillis();
			PartServiceHelper psh=new PartServiceHelper();
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			// ��ȡ���BOM�ṹ
			Vector vec=psh.getBOMList(desginID,"partNumber-0,partName-0,quantity-0,","","");
			long t2=System.currentTimeMillis();
//			System.out.println("CompartTreeResult  getBOMList  ��ʱ�� "+(t2-t1)+" ����");
			// System.out.println(vec.size());
			HashMap designMap=new HashMap();
			for(int i=2;i<vec.size();i++)
			{
				Object obj=vec.elementAt(i);
				// System.out.println(obj.getClass());
				if(obj instanceof String[])
				{
					String[] str=(String[])obj;
					// System.out.println(str[4]+"**"+str[6]);
				}
				else if(obj instanceof Object[])
				{
					Object[] str=(Object[])obj;
					designMap.put(str[4],(new String[]{str[0].toString(),str[6].toString()}));
				}
				else
				{
					System.out.println("other");
				}
			}
//			System.out.println("designMap:"+designMap.size());

			long t3=System.currentTimeMillis();
//			System.out.println("CompartTreeResult  designMap  ��ʱ�� "+(t3-t2)+" ����");

			// ��ȡ����BOM
			HashMap gymap=new HashMap();
			gymap=bianli(gyID,carModelCode,dwbs,1L,gymap);

			long t4=System.currentTimeMillis();
//			System.out.println("CompartTreeResult  bianli  ��ʱ�� "+(t4-t3)+" ����");

//			System.out.println("gymap:"+gymap.size());
			Set gySet=gymap.keySet();
			Iterator gyiter=gySet.iterator();
			String gynum="";
			while(gyiter.hasNext())
			{
				gynum=(String)gyiter.next();
				String[] str=(String[])gymap.get(gynum);
				// System.out.println("���:"+str[1]+"    gy������"+str[2]+"    design������"+designMap.get(str[1]));
				if(designMap.containsKey(str[1]))
				{
					String[] destr=(String[])designMap.get(str[1]);
					if(!destr[1].equals(str[2]))
					{
						QMPartIfc part=(QMPartIfc)ps.refreshInfo(str[0]);
						//CCBegni SS42
						String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",str[0]);
						if(fbyIbaValue==""){
							fbyIbaValue = part.getVersionValue();
						}
						//CCEnd SS42
						jo=new JSONObject();
						jo.put("id",str[0]);
						jo.put("num",str[1]);
						jo.put("name",part.getPartName());
						jo.put("dcount",destr[1]);
						jo.put("gycount",str[2]);
						//CCBegin SS29
						//CCBegin SS42
//						jo.put("version",part.getVersionValue());
						jo.put("version",fbyIbaValue);
						//CCEnd SS42
						//CCEnd SS29
						json.put(jo);
					}
					else
					{
						designMap.remove(str[1]);
					}
				}
				else
				{
					QMPartIfc part=(QMPartIfc)ps.refreshInfo(str[0]);
					//CCBegni SS42
					String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",str[0]);
					if(fbyIbaValue==""){
						fbyIbaValue = part.getVersionValue();
					}
					//CCEnd SS42
					jo=new JSONObject();
					jo.put("id",str[0]);
					jo.put("num",str[1]);
					jo.put("name",part.getPartName());
					jo.put("dcount","0");
					jo.put("gycount",str[2]);
					//CCBegin SS29
					//CCBegin SS42
//					jo.put("version",part.getVersionValue());
					jo.put("version",fbyIbaValue);
					//CCEnd SS42
					//CCEnd SS29
					json.put(jo);
				}
			}
			Set idSet=designMap.keySet();
			Iterator iter=idSet.iterator();
			String num="";
			while(iter.hasNext())
			{
				num=(String)iter.next();
				String[] str=(String[])designMap.get(num);
				// System.out.println("design ʣ�ࣺ"+(String)iter.next());
				QMPartIfc part=(QMPartIfc)ps.refreshInfo(str[0]);
				//CCBegni SS42
				String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",str[0]);
				if(fbyIbaValue==""){
					fbyIbaValue = part.getVersionValue();
				}
				//CCEnd SS42
				jo=new JSONObject();
				jo.put("id",str[0]);
				jo.put("num",num);
				jo.put("name",part.getPartName());
				jo.put("dcount",str[1]);
				jo.put("gycount","0");
				//CCBegin SS29
				//CCBegin SS42
//				jo.put("version",part.getVersionValue());
				jo.put("version",fbyIbaValue);
				//CCEnd SS42
				//CCEnd SS29
				json.put(jo);
			}
			long t5=System.currentTimeMillis();
//			System.out.println("CompartTreeResult  json  ��ʱ�� "+(t5-t4)+" ����");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
//		System.out.println(json.length());
//		System.out.println(json.toString());
		return json.toString();
	}

	private HashMap bianli(String gyID,String carModelCode,String dwbs,float parentQuantity,HashMap gymap)
		throws QMException
	{
		// System.out.println("gyID="+gyID+"  parentQuantity="+parentQuantity);
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			String routesql="select childPart,childNumber,quantity from GYBomStructure where parentPart='"+gyID
				+"' and effectCurrent='1' and dwbs='"+dwbs+"'";
			rs=stmt.executeQuery(routesql);

			while(rs.next())
			{
				String childPart="";
				String childNumber="";
				String count="";
				String[] tempArray=new String[3];
//				if(childNumber.equals("5001023-50"))
//				{
//					System.out.println(rs.getString(1)+"***"+rs.getString(2)+"***"+rs.getString(3));
//				}
				childPart=rs.getString(1).toString();
				childNumber=rs.getString(2).toString();
				count=rs.getString(3).toString();

				if(count==null||count.equals(""))
				{
					count="1";
				}

				tempArray[0]=childPart;
				tempArray[1]=childNumber;
				if(gymap.containsKey(childNumber))
				{
//					if(childNumber.equals("5001023-50"))
//					{
//						System.out.println("containsKey  111");
//					}
					String[] a=(String[])gymap.get(childNumber);

					tempArray[2]=(String.valueOf(parentQuantity*(Float.parseFloat(count))+(Float.parseFloat(a[2]))))
						.toString();
				}
				else
				{

//					if(childNumber.equals("5001023-50"))
//					{
//						System.out.println("containsKey  222");
//					}
					tempArray[2]=(String.valueOf(parentQuantity*(Float.parseFloat(count)))).toString();
				}

				if(tempArray[2].endsWith(".0"))
				{
					tempArray[2]=tempArray[2].substring(0,tempArray[2].length()-2);
				}
//				if(childNumber.equals("5001023-50"))
//				{
//					System.out.println("tempArray[2]=="+tempArray[2]);
//				}
				gymap.put(childNumber,tempArray);
				gymap=bianli(childPart,carModelCode,dwbs,(parentQuantity*(Float.parseFloat(count))),gymap);
			}

			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return gymap;
	}

	/**
	 * ��ȡԴ�㲿���ṹ��������
	 */
	public Collection getBomLinks(Collection mtree,QMPartIfc part,String carModelCode,String dwbs,Vector linkvec)
		throws QMException
	{
		try
		{
			if(part!=null)
			{
				// ���part�Ƿ���ڵ�ǰ�����Ľṹ����������Ҫ����ṹ��
				// if(checkParentPartExit(part.getBsoID(), dwbs, "1"))
				if(!getEffect(part.getBsoID(),carModelCode,dwbs).equals("0"))
				{
					return mtree;
				}

				StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
				VersionControlService vcservice=(VersionControlService)EJBServiceHelper
					.getService("VersionControlService");
				JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
				PersistService ps=(PersistService)EJBServiceHelper.getPersistService();

				PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
				Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);
				if(linkcoll!=null&&linkcoll.size()>0)
				{
					Iterator iter=linkcoll.iterator();
					// һ������ parentPart,childPart,quantity,carModelCode,dwbs
					// String[] mt = new String[8];
					QMPartMasterIfc partMaster=null;
					Vector tempResult=new Vector();
					QMPartIfc subpart=null;
					Vector tempvec=new Vector();
					while (iter.hasNext()) {
						Object obj[] = (Object[]) iter.next();
						PartUsageLinkIfc link = (PartUsageLinkIfc) obj[0];
						// CCBegin SS64
						if (obj[1] instanceof QMPartIfc) {
							// CCEnd SS64
							subpart = (QMPartIfc) obj[1];

							if (linkvec.contains(part.getBsoID()
									+ subpart.getBsoID())) {
								if (tempvec.contains(part.getBsoID()
										+ subpart.getBsoID())) {
									System.out.println("��ǰ�ṹ���ظ��������ظ���ӣ�"
											+ part.getPartNumber() + "=="
											+ subpart.getPartNumber());
								} else {
									continue;
								}
							} else {
								linkvec.add(part.getBsoID()
										+ subpart.getBsoID());
							}

							
							tempvec.add(part.getBsoID() + subpart.getBsoID());

							// System.out.println("11:"+part.getPartNumber()+"=="+subpart.getPartNumber()+":"+part.getBsoID()+"=="+subpart.getBsoID());
							//CCBegin SS70
							String[] mt = new String[10];
//							String[] mt = new String[11];
							//CCEnd SS70
							mt[0] = part.getBsoID();
							mt[1] = subpart.getBsoID();
							mt[2] = String.valueOf(link.getQuantity());
							mt[3] = "";
							mt[4] = dwbs;
							mt[5] = "0";
							mt[6] = "";
							mt[7] = "";
							mt[8] = link.getDefaultUnit().getDisplay();
							mt[9] = subpart.getPartNumber();
							//CCBegin SS70
//							mt[10] = "red";
							//CCEnd SS70
							mtree.add(mt);
							getBomLinks(mtree, subpart, carModelCode, dwbs,
									linkvec);
						}
						//CCBegin SS65
//						tempvec = null;
						tempvec = new Vector();
						//CCEnd SS65
					}
					// CCBegin SS64
				}
				// CCEnd SS64
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return mtree;
	}

	/**
	 * ����
	 */
	public String addLock(String parentPart,String dwbs) throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		String s="";
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			UserInfo userinfo=getCurrentUserInfo();
			String sendData=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			stmt.executeQuery("update GYBomStructure set locker='"+userinfo.getBsoID()+"', lockDate=to_date('"+sendData
				+"','yyyy-mm-dd hh24:mi:ss') where parentPart='"+parentPart+"' and dwbs='"+dwbs
				+"' and effectCurrent='0'");
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
			s="�ɹ�";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			s="ʧ��";
		}
		finally
		{
			try
			{
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return s;
	}

	/**
	 * ����
	 */
	public String unLock() throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		String s="";
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			UserInfo userinfo=getCurrentUserInfo();
			String sendData=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			stmt.executeQuery("update GYBomStructure set locker=null,lockDate=null where locker='"+userinfo.getBsoID()
				+"' and effectCurrent='0'");
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
			s="�ɹ�";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			s="ʧ��";
		}
		finally
		{
			try
			{
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return s;
	}

	/**
	 * ���鵱ǰ�������Ƿ�����
	 */
	public String checkLock(String parentPart,String dwbs)
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String s="";
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			String countIdSql="select locker from GYBomStructure where parentPart='"+parentPart+"' and dwbs='"+dwbs
				+"' and locker is not null";
			rs=stmt.executeQuery(countIdSql);
			if(rs.next())
			{
				s=rs.getString(1);
			}

			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return s;
	}

	/**
	 * ���ݳ�����͹������ж��Ƿ���Ч ����0 û��δ��Ч�ṹ û����Ч�ṹ ����1 ��δ��Ч�ṹ û����Ч�ṹ ����2 û��δ��Ч�ṹ ����Ч�ṹ
	 * ����3 ��δ��Ч�ṹ ����Ч�ṹ
	 */
	public String getEffect(String parentPart,String carModelCode,String dwbs) throws QMException
	{
		// System.out.println("getEffect parentPart=="+parentPart+"  and carModelCode=="+carModelCode+"  and dwbs=="+dwbs);
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String result="0";
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();

			boolean flag0=false;// δ��Ч�ṹ���ڱ�ʶ false û��δ��Ч�ṹ true ��δ��Ч�ṹ
			boolean flag1=false;// ��Ч�ṹ���ڱ�ʶ false û����Ч�ṹ true ����Ч�ṹ
			rs=stmt.executeQuery("select count(*) from GYBomStructure where effectCurrent='0' and parentPart='"
				+parentPart+"' and dwbs='"+dwbs+"'");
			rs.next();
			int countList=rs.getInt(1);
			if(countList>0)
			{
				flag0=true;
			}

			rs=stmt.executeQuery("select count(*) from GYBomStructure where effectCurrent='1' and parentPart='"
				+parentPart+"' and dwbs='"+dwbs+"'");
			rs.next();
			int countList1=rs.getInt(1);
			if(countList1>0)
			{
				flag1=true;
			}

			if(flag0&&!flag1)
			{
				result="1";
			}
			if(!flag0&&flag1)
			{
				result="2";
			}
			if(flag0&&flag1)
			{
				result="3";
			}

			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ��ȡ���·��
	 * ͨ��sql��ȡ���� �Ƿ��� ��Ҫ·��
	 */
	private HashMap oldgetRoute(String insql,HashMap map) throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String s="";
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			//CCBegin SS17
			//String routesql="select aa.routestr,bb.rightbsoid from technicsroutebranch aa,(select a.routeid,a.rightbsoid from listroutepartlink a ,(select max(MODIFYTIME) c,rightbsoid from listroutepartlink where rightbsoid in ("
			String routesql="select branch.routestr,link.rightbsoid from technicsroutebranch branch,(select a.leftbsoid,a.routeid,a.rightbsoid from listroutepartlink a ,(select max(aa.MODIFYTIME) maxtime,aa.rightbsoid from (select a.MODIFYTIME,a.routeid,a.rightbsoid from listroutepartlink a,technicsroute b, technicsroutelist c where a.rightbsoid in ("
				+ insql +") and a.routeid=b.bsoid and b.modefyIdenty!='Coding_221664' and a.leftbsoid=c.bsoid and c.owner is null) aa where aa.routeid is not null group by aa.rightbsoid) b where a.rightbsoid=b.rightbsoid and a.MODIFYTIME=b.maxtime) link ,technicsroute route where branch.routeid=link.routeid and route.modefyIdenty!='Coding_221664' and route.bsoid=branch.routeid and branch.mainroute='1'";
				//+") and alterstatus='1' and routeid is not null group by rightbsoid) b where a.rightbsoid=b.rightbsoid and a.MODIFYTIME=b.c) bb where aa.routeid=bb.routeid";
			//CCEnd SS17
			rs=stmt.executeQuery(routesql);
			String[] str=new String[2];
			while(rs.next())
			{
				// System.out.println(rs.getString(1)+"***"+rs.getString(2));
				str=rs.getString(1).toString().split("@");
				if(str[0]==null)
				{
					str[0]="";
				}
				if(str[1]==null)
				{
					str[1]="";
				}
				map.put(rs.getString(2),str);
			}

			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return map;
	}


	//CCBegin SS44
	/**
	 * ��ȡ���·��
	 * ͨ��sql��ȡ���� �Ƿ��� ��Ҫ·��
	 */
	private HashMap getRoute(String insql,HashMap map) throws QMException
	{
		try
		{
			map = getAllRouteValue(insql, map);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}
	//CCEnd SS44
	
	
	/**
	 * ��ȡ����·��·�ߣ��ɶ���
	 */
	private HashMap getSecondRoute(String xsql,HashMap map) throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String s="";
		//CCBegin SS78
		Vector vecsql = new Vector();
		if(xsql.contains(",")){
			String[] aa = xsql.split(",");
			String sqltemp="";
			int lsh = 0;
			//System.out.println("aa.length==="+aa.length);
			for(int i =0;i<aa.length;i++){
				lsh++;
				if(sqltemp.equals(""))
				{
					sqltemp=aa[i];
				}
				else
				{
					sqltemp=sqltemp+","+aa[i];
				}
				if(lsh>500){
					System.out.println("lsh==="+lsh);
					vecsql.add(sqltemp);
					sqltemp="";
					lsh=0;
				}
			}
			
				vecsql.add(sqltemp);
		}
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			for(int i =0;i<vecsql.size();i++){
				String insql = (String) vecsql.elementAt(i);
				String routesql="select mainStr,rightBsoID from consListRoutePartLink where  releaseIdenty='1' and rightBsoID in ("
					+insql+") order by modifytime desc";
				//System.out.println("routesql==="+routesql);
				rs=stmt.executeQuery(routesql);
				String[] str=new String[2];
				while(rs.next())
				{
					// System.out.println(rs.getString(1)+"***"+rs.getString(2));
					
					
					
					if(rs.getString(1)==null)
						continue;
					String route=rs.getString(1).toString();
					if(route.indexOf("=")!=-1)
					{
						str=rs.getString(1).toString().split("=");
					}
					else
					{
						str[0]=route;
					}
					if(str[0]==null)
					{
						str[0]="";
					}
					if(str[1]==null)
					{
						str[1]="";
					}
					map.put(rs.getString(2),str);
				}

				// ��ղ��ر�sql��������
				rs.close();
				
			}
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}//				CCEnd SS78
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return map;
	}

	/**
	 * �������㲿���Ƿ���ڽṹѭ���� ���gybom�Ľṹ����ȡchildID�������Ӽ�id�����Ƿ����parentID��������˵������ѭ����
	 */
	private boolean checkStructureLoop(String parentID,String childID,String carModelCode,String dwbs)
		throws QMException
	{
		try
		{
			Vector vec=getStructures(childID,carModelCode,dwbs,new Vector());
			if(vec.contains(parentID))
			{
				return true;
			}
		}
		catch(Exception ex1)
		{
			ex1.printStackTrace();
		}
		return false;
	}

//CCBegin SS20	
	/**
     * ��ȡ��������
     */
    private String getNewPartNumber(String number) throws QMException
    {
    	//CCBegin SS53
//        Connection conn=null;
//        Statement stmt=null;
//        ResultSet rs=null;
//        String ret = "";
//        try
//        {
//            conn=PersistUtil.getConnection();
//            stmt=conn.createStatement();
//            String countIdSql="select count(*) from QMPartMaster where partNumber like '"+number+"F___'";
//            rs=stmt.executeQuery(countIdSql);
//            rs.next();
//            int countList=rs.getInt(1);
//            // System.out.println(parentID+"==="+dwbs+"==="+eff+"  count:"+countList);
//            if(countList<9)
//            {
//                String temp = String.valueOf(countList+1);
//                ret = number+"F00"+temp;
//            }
//            else if(countList>=9)
//            {
//                String temp = String.valueOf(countList+1);
//                ret = number+"F0"+temp;
//            }
//            else if(countList>=99)
//            {
//                String temp = String.valueOf(countList+1);
//                ret = number+"F"+temp;
//            }
//            
//            // ��ղ��ر�sql��������
//            rs.close();
//            // �ر�Statement
//            stmt.close();
//            // �ر����ݿ�����
//            conn.close();
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            try
//            {
//                if(rs!=null)
//                {
//                    rs.close();
//                }
//                if(stmt!=null)
//                {
//                    stmt.close();
//                }
//                if(conn!=null)
//                {
//                    conn.close();
//                }
//            }
//            catch(SQLException ex1)
//            {
//                ex1.printStackTrace();
//            }
//        }
    	String ret = number+"-FC";
    	//CCEnd SS53
        return ret;
    }
    //CCEnd SS20
    
	/**
	 * ����Ƿ���ڸ����㲿������Ч�ṹ�� ���gybom�Ľṹ����ǰ�����Ƿ������Ч�ĸ�����parentID�Ľṹ��
	 */
	private boolean checkParentPartExit(String parentID,String dwbs,String eff) throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			String countIdSql="select count(*) from GYBomStructure where effectCurrent='"+eff+"' and dwbs='"+dwbs
				+"' and parentPart='"+parentID+"'";
			rs=stmt.executeQuery(countIdSql);
			rs.next();
			int countList=rs.getInt(1);
			// System.out.println(parentID+"==="+dwbs+"==="+eff+"  count:"+countList);
			if(countList>0)
			{
				return true;
			}

			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * �������㲿���Ƿ���ڽṹѭ���� ���gybom�Ľṹ����ȡchildID�������Ӽ�id�����Ƿ����parentID��������˵������ѭ����
	 */
	private Vector getStructures(String parentID,String carModelCode,String dwbs,Vector vec) throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			String childsql="select childPart from GYBomStructure where effectCurrent='0' and parentPart='"+parentID
				+"' and dwbs='"+dwbs+"'";
			rs=stmt.executeQuery(childsql);
			while(rs.next())
			{
				vec.add(rs.getString(1));
				vec=getStructures(rs.getString(1),carModelCode,dwbs,vec);
			}

			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return vec;
	}

	/**
	 * ���ݹ���id��ȡ�Ӽ�id
	 */
	private String getChildPart(String linkid) throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			String childsql="select childPart from GYBomStructure where id='"+linkid+"'";
			rs=stmt.executeQuery(childsql);
			while(rs.next())
			{
				return rs.getString(1);
			}

			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * ���Ϊ����BOM�� String yid ѡ���㲿���� String mid Ŀ���㲿���� String dwbs ѡ���㲿������
	 */
	public String saveAsGYBom(String yid,String mid,String ydwbs) throws QMException
	{
//		System.out.println("come in saveAsGYBom   yid=="+yid+"  mid=="+mid+"  ydwbs=="+ydwbs);
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String str="";
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc ypart=(QMPartIfc)ps.refreshInfo(yid);
			QMPartIfc mpart=(QMPartIfc)ps.refreshInfo(mid);

			String mdwbs=getCurrentDWBS();

			Collection mtree=new ArrayList();
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();

			String countIdSql="select count(*) from GYBomStructure where effectCurrent='0' and dwbs='"+mdwbs
				+"' and parentPart='"+mid+"'";
			rs=stmt.executeQuery(countIdSql);
			rs.next();
			int countList=rs.getInt(1);
			if(countList>0)
			{
				return "[{"+mpart.getPartNumber()+"����δ��Ч����BOM��}]";
			}

			rs=stmt.executeQuery("select count(*) from GYBomStructure where effectCurrent='1' and parentPart='"+yid
				+"' and dwbs='"+ydwbs+"'");
			rs.next();
			if(rs.getInt(1)>0)
			{
//				System.out.println("��ȡ��Ч�ṹ");
				rs=stmt
					.executeQuery("select parentPart,childPart,quantity,unit,childNumber from GYBomStructure where effectCurrent='1' and parentPart='"
						+yid+"' and dwbs='"+ydwbs+"'");
			}
			else
			{
//				System.out.println("��ȡδ��Ч�ṹ");
				rs=stmt
					.executeQuery("select parentPart,childPart,quantity,unit,childNumber from GYBomStructure where effectCurrent='0' and parentPart='"
						+yid+"' and dwbs='"+ydwbs+"'");
			}

			while(rs.next())
			{
				if(yid.equals(rs.getString(1)))
				{
					mtree.add(new String[]{mid,rs.getString(2),rs.getString(3),mpart.getPartNumber(),mdwbs,"0","","",
						rs.getString(4),rs.getString(5)});
				}
				else
				{
					mtree.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),mpart.getPartNumber(),mdwbs,
						"0","","",rs.getString(4),rs.getString(5)});
				}
			}

			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();

			//CCBegin SS15
			saveGYBom(mtree, true, ydwbs);//SS21
			//CCEnd SS15

			str=getGYBom(mid,mpart.getPartNumber(),mdwbs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * ���ñ����Ϊһ����Ч�Ĺ���BOM��¡һ��δ��ЧBOM�ṹ ���ȼ�������Ƿ�����δ��Ч�� ���ָ���ڵ��Ƿ�δ��Ч
	 */
	public String changeGYBom(String id,String carModelCode,String dwbs) throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String str="";
		try
		{
			Collection mtree=new ArrayList();
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();

			// �ȼ��ָ�������Ƿ�����Ч����BOM
			String countIdSql="select count(*) from GYBomStructure where effectCurrent='1' and parentPart='"+id
				+"' and dwbs='"+dwbs+"'";
			rs=stmt.executeQuery(countIdSql);
			rs.next();
			int countList=rs.getInt(1);
			if(countList==0)
			{
				return "[{"+carModelCode+"û����Ч����BOM���޷����б����}]";
			}

			// �ټ��ָ�������Ƿ���δ��Ч����BOM
			countIdSql="select count(*) from GYBomStructure where effectCurrent='0' and parentPart='"+id+"' and dwbs='"
				+dwbs+"'";
			rs=stmt.executeQuery(countIdSql);
			rs.next();
			countList=rs.getInt(1);
			if(countList>0)
			{
				return "[{"+carModelCode+"�Ĺ���BOM��ִ�й������}]";
			}

//			String sql="select parentPart,childPart,quantity,carModelCode,dwbs,unit,childNumber from GYBomStructure where effectCurrent='1' and parentPart='"
//				+id+"' and dwbs='"+dwbs+"'";
			String sql="";
			if(dwbs.equals("W34")){//CCBegin SS85
			  sql="select parentPart,childPart,quantity,carModelCode,dwbs,unit,childNumber from GYBomStructure where effectCurrent='1' and parentPart='"
				+id+"' and dwbs='"+dwbs+"' order by id";}//CCEnd SS85
			  else{
				  sql="select parentPart,childPart,quantity,carModelCode,dwbs,unit,childNumber from GYBomStructure where effectCurrent='1' and parentPart='"
						+id+"' and dwbs='"+dwbs+"' order by id" ;
			  }
			rs=stmt.executeQuery(sql);

			while(rs.next())
			{
				mtree.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),
					"0","","",rs.getString(6),rs.getString(7)});
				//CCBegin SS18
				mtree = changeGYBom(rs.getString(2), mtree, dwbs);
				//CCEnd SS18
			}

			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();

			//CCBegin SS15
			saveGYBom(mtree, false, dwbs);//SS21
			//CCEnd SS15

			str=getGYBom(id,carModelCode,dwbs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return str;
	}
	
	//CCBegin SS18
	/**
	 * �ݹ鴦��Ϊ��Ч�Ĺ���BOM��¡һ��δ��ЧBOM�ṹ
	 */
	private Collection changeGYBom(String id, Collection mtree, String dwbs) throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		String str="";
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();

			// �ȼ��ָ���㲿���Ƿ�����Ч����BOM
			String countIdSql="select count(*) from GYBomStructure where effectCurrent='1' and parentPart='"+id +"' and dwbs='"+dwbs+"'";
			rs=stmt.executeQuery(countIdSql);
			rs.next();
			int countList=rs.getInt(1);
			if(countList==0)
			{
				return mtree;
			}

			// �ټ��ָ���㲿���Ƿ���δ��Ч����BOM
			countIdSql="select count(*) from GYBomStructure where effectCurrent='0' and parentPart='"+id+"' and dwbs='" +dwbs+"'";
			rs=stmt.executeQuery(countIdSql);
			rs.next();
			countList=rs.getInt(1);
			if(countList>0)
			{
				return mtree;
			}

//			String sql="select parentPart,childPart,quantity,carModelCode,dwbs,unit,childNumber from GYBomStructure where effectCurrent='1' and parentPart='"
//				+id+"' and dwbs='"+dwbs+"'";
			String sql="";
			if(dwbs.equals("W34")){
			   sql="select parentPart,childPart,quantity,carModelCode,dwbs,unit,childNumber from GYBomStructure where effectCurrent='1' and parentPart='" +id+"' and dwbs='"+dwbs+"' order by id";
			}
			else{
				 sql="select parentPart,childPart,quantity,carModelCode,dwbs,unit,childNumber from GYBomStructure where effectCurrent='1' and parentPart='" +id+"' and dwbs='"+dwbs+"'";
			}
			rs=stmt.executeQuery(sql);

			while(rs.next())
			{
				mtree.add(new String[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),"0","","",rs.getString(6),rs.getString(7)});
				mtree = changeGYBom(rs.getString(2), mtree, dwbs);
			}
			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return mtree;
	}
	//CCEnd SS18
	

	public void multiChangeGYBom(String ypartid) throws QMException
	{
		PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
		QMPartIfc part=(QMPartIfc)ps.refreshInfo(ypartid);
		multiChangeGYBom(part);
	}

	/**
	 * �����޸Ĺ���BOM�������޸Ĺ��򼯺ϣ���Ҫ�޸ĵĳ��ͣ����е��� ����������������ṹ�¶�����
	 */
	public void multiChangeGYBom(QMPartIfc ypart) throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			String dwbs=getCurrentDWBS();
//			System.out.println("come in multiChangeGYBom carModelCode=="+ypart.getPartNumber()+"  and dwbs=="+dwbs);
			HashMap map=getGYBomDifferent(ypart.getPartNumber(),dwbs);
			if(map==null)
			{
				System.out.println("[{��������һ�β��ã�û����ʷ�ṹ��}]");
				return;
			}
			if(map.size()==0)
			{
				System.out.println("[{������û�бȽϳ��ṹ�仯��}]");
				return;
			}

			QMPartIfc part=null;
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select assisPart from batchupdateCM where yPart='"+ypart.getBsoID()+"' and dwbs='"
				+dwbs+"'");
			while(rs.next())
			{
				part=(QMPartIfc)ps.refreshInfo(rs.getString(1));
				if(part!=null)
				{
					multiChangeGYBom(part.getBsoID(),map,part.getPartNumber(),dwbs);
				}
			}
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
			// ��ղ��ر�sql��������
			rs.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
				if(rs!=null)
				{
					rs.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
	}

	/**
	 * �����޸Ĺ���BOM�������޸Ĺ��򼯺ϣ���Ҫ�޸ĵĳ��ͣ����е��� ֻ��Ҫ�޸�һ�����ṹ
	 * 
	 * @param String
	 *            id �滻���͵�id
	 * @param HashMap
	 *            map �滻���ݼ���
	 * @param String
	 *            carModelCode �滻����
	 * @param String
	 *            dwbs ����
	 */
	public void multiChangeGYBom(String id,HashMap map,String carModelCode,String dwbs) throws QMException
	{
		Vector addvec=(Vector)map.get("add");
		Vector updatevec=(Vector)map.get("update");
		Vector deletevec=(Vector)map.get("delete");

		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		try
		{
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			String sql="";
			for(int i=0;i<updatevec.size();i++)
			{
				String[] str=(String[])updatevec.elementAt(i);
				// CCBegin SS5
				if(str[0].equals(""))
				{
					str[0]=id;
				}
				// CCEnd SS5
				sql="update GYBomStructure set quantity='"+str[2]+"' where effectCurrent='1' and dwbs='"+dwbs
					+"' and parentPart='"+str[0]+"' and childPart='"+str[1];
//				System.out.println(sql);
				st.executeQuery(sql);
			}

			for(int j=0;j<deletevec.size();j++)
			{
				String[] str=(String[])deletevec.elementAt(j);
				// CCBegin SS5
				if(str[0].equals(""))
				{
					str[0]=id;
				}
				// CCEnd SS5
				sql="delete GYBomStructure where effectCurrent='1' and dwbs='"+dwbs+"' and parentPart='"+str[0]
					+"' and childPart='"+str[1]+"' and quantity='"+str[2]+"'";
//				System.out.println(sql);
				st.executeQuery(sql);
			}

			// parentPart,childPart,quantity,carModelCode,dwbs,unit,childNumber
			Collection mtree=new ArrayList();
			String countIdSql="";
			for(int i=0;i<addvec.size();i++)
			{
				String[] str=(String[])addvec.elementAt(i);
				// CCBegin SS5
				if(str[0].equals(""))
				{
					str[0]=id;
				}
				// CCEnd SS5
				// ���
				// parentPart,childPart,quantity,carModelCode,dwbs,unit,childNumber
				countIdSql="select count(*) from GYBomStructure where effectCurrent='1' and parentPart='"+str[0]
					+"' and childPart='"+str[1]+"' and quantity='"+str[2]+"' and dwbs='"+dwbs+"' and unit='"+str[3]
					+"' and childNumber='"+str[4]+"'";
				rs=st.executeQuery(countIdSql);
				rs.next();
				int countList=rs.getInt(1);
//				System.out.println("���advec:"+carModelCode+" and "+str[4]+" 'count="+countList);
				if(countList>0)
				{
					continue;
				}
				String[] str1=new String[]{str[0],str[1],str[2],"",dwbs,"1","","",str[3],str[4]};
//				System.out.println("add:"+Arrays.toString(str1));
				mtree.add(str1);
			}
			//CCBegin SS15
			saveGYBom(mtree, true, dwbs);//SS21
			//CCEnd SS15
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				// CCBegin SS5
				if(st!=null)
				{
					st.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
				if(rs!=null)
				{
					rs.close();
				}
				// CCEnd SS5
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * ���һ������BOM����Ч�ṹ��δ��Ч�ṹ�Ĳ�� �õ�ɾ���Ľṹ���ϡ������Ľṹ���ϡ����µĽṹ����
	 * ����3�������еĸ������Ǵ˴α���漰�������㲿����
	 */
	public HashMap getGYBomDifferent(String carModelCode,String dwbs) throws QMException
	{
		HashMap result=new HashMap();
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;

		QMPartIfc rootpart=findPartLasterByNumber(carModelCode);

		String oldsql="select parentPart,childPart,quantity,unit,childNumber from GYBomStructure where effectCurrent='1' and parentPart='"
			+rootpart.getBsoID()+"' and dwbs='"+dwbs+"'";
		String newsql="select parentPart,childPart,quantity,unit,childNumber from GYBomStructure where effectCurrent='0' and parentPart='"
			+rootpart.getBsoID()+"' and dwbs='"+dwbs+"'";
		HashMap oldmap=new HashMap();
		HashMap newmap=new HashMap();
		try
		{
			// CCBegin SS5
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=null;
			HashMap partmap=new HashMap();
			// CCEnd SS5
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(oldsql);
			String tempkey="";
			while(rs.next())
			{
				tempkey=rs.getString(1)+","+rs.getString(2)+","+rs.getString(4)+","+rs.getString(5);
				// System.out.println("old:"+tempkey);
				if(oldmap.containsKey(tempkey))
				{
					oldmap.put(tempkey,(oldmap.get(tempkey)+","+rs.getString(3)));
				}
				else
				{
					oldmap.put(tempkey,rs.getString(3));
				}
				// CCBegin SS5
				if(rs.getString(1)!=null&&!rs.getString(1).equals(""))
				{
					part=(QMPartIfc)ps.refreshInfo(rs.getString(1));
					if(!partmap.containsKey(rs.getString(1)))
					{
						partmap.put(rs.getString(1),part.getPartNumber());
					}
				}
				// CCEnd SS5
			}
//			System.out.println("oldmap=="+oldmap.size());
			// ��һ�η�����û����ʷ��Ч����
			if(oldmap.size()==0)
			{
				return null;
			}

			rs=stmt.executeQuery(newsql);
			while(rs.next())
			{
				tempkey=rs.getString(1)+","+rs.getString(2)+","+rs.getString(4)+","+rs.getString(5);
				// System.out.println("new:"+tempkey);
				if(newmap.containsKey(tempkey))
				{
					newmap.put(tempkey,(newmap.get(tempkey)+","+rs.getString(3)));
				}
				else
				{
					newmap.put(tempkey,rs.getString(3));
				}

				// CCBegin SS5
				if(rs.getString(1)!=null&&!rs.getString(1).equals(""))
				{
					part=(QMPartIfc)ps.refreshInfo(rs.getString(1));
					if(!partmap.containsKey(rs.getString(1)))
					{
						partmap.put(rs.getString(1),part.getPartNumber());
					}
				}
				// CCEnd SS5
			}
//			System.out.println("newmap=="+newmap.size());

			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();

			// �Ƚ�oldmap��newmap
			Vector addvec=new Vector();
			Vector updatevec=new Vector();
			Vector deletevec=new Vector();
			Vector bgvec=new Vector();
			Set oldSet=oldmap.keySet();
			Iterator olditer=oldSet.iterator();
			String oldkey="";

			// CCBegin SS5
			boolean rootFlag=false;
			// CCEnd SS5

			while(olditer.hasNext())
			{
				oldkey=(String)olditer.next();
				String[] str=oldkey.split(",");

				// CCBegin SS5
				rootFlag=false;
				if(partmap.containsKey(str[0])&&partmap.get(str[0]).equals(carModelCode))
				{
					rootFlag=true;
				}
				// CCEnd SS5

				if(newmap.containsKey(oldkey))
				{
					if(newmap.get(oldkey).equals(oldmap.get(oldkey)))// ������ȣ��Ǳ��ֲ���
					{
						// ��������
					}
					else
					// �������ȣ��Ǹ���
					{
						String[] a=((String)oldmap.get(oldkey)).split(",");
						String[] b=((String)newmap.get(oldkey)).split(",");
						// {"1","2","3"}��{"1","2","1","2"}�Ƚϴ���󣬽������{"","","3"}��{"","","1","2"}
						for(int i=0;i<a.length;i++)
						{
							for(int j=0;j<b.length;j++)
							{
								if(a[i].equals(b[j]))
								{
									a[i]="";
									b[j]="";
									break;
								}
							}
						}

						for(int i=0;i<a.length;i++)
						{
							if(!a[i].equals(""))// ��ֵ����ʾ�ɼ�����ɾ����
							{
								// CCBegin SS5
								/*
								 * deletevec.add(new
								 * String[]{str[0],str[1],a[i]});
								 * if(!bgvec.contains(str[0])+","+str[1]) {
								 * bgvec.add(str[0])+","+str[1]); }
								 */
								if(rootFlag)
								{
									deletevec.add(new String[]{"",str[1],a[i]});
									if(!bgvec.contains("root,"+str[1]))
									{
										bgvec.add("root,"+str[1]);
									}
								}
								else
								{
									deletevec.add(new String[]{str[0],str[1],a[i]});
									if(!bgvec.contains(str[0]+","+str[1]))
									{
										bgvec.add(str[0]+","+str[1]);
									}
								}
								// CCEnd SS5
							}
						}
						for(int i=0;i<b.length;i++)
						{
							if(!b[i].equals(""))// ��ֵ����ʾ�¼���������
							{
								// CCBegin SS5
								/*
								 * addvec.add(new
								 * String[]{str[0],str[1],b[i],str[2],str[3]});
								 * /
								 * /���ӵĲ���Ҫ�����������ӣ���Ϊ��ǰ�����������ӵĽṹ����Ӧ��ͨ���������������ͽṹ�еõ�
								 * �� if(!bgvec.contains(str[0])) {
								 * bgvec.add(str[0]); }
								 */
								if(rootFlag)
								{
									addvec.add(new String[]{"",str[1],b[i],str[2],str[3]});
								}
								else
								{
									addvec.add(new String[]{str[0],str[1],b[i],str[2],str[3]});
								}
								// CCEnd SS5
							}
						}
					}
					newmap.remove(oldkey);
				}
				else
				// �¼��ϲ����оɼ������ݣ���ʾ�ɼ�����ɾ����
				{
					String[] del=((String)oldmap.get(oldkey)).split(",");
					for(int i=0;i<del.length;i++)
					{
						// CCBegin SS5
						/*
						 * deletevec.add(new String[]{str[0],str[1],del[i]});
						 * if(!bgvec.contains(str[0])) { bgvec.add(str[0]); }
						 */
						if(rootFlag)
						{
							deletevec.add(new String[]{"",str[1],del[i]});
							if(!bgvec.contains("root,"+str[1]))
							{
								bgvec.add("root,"+str[1]);
							}
						}
						else
						{
							deletevec.add(new String[]{str[0],str[1],del[i]});
							if(!bgvec.contains(str[0]+","+str[1]))
							{
								bgvec.add(str[0]+","+str[1]);
							}
						}
						// CCEnd SS5
					}
				}
			}

			Set newSet=newmap.keySet();
			Iterator newiter=newSet.iterator();
			String newkey="";
			while(newiter.hasNext())
			{
				newkey=(String)newiter.next();// �ɼ��ϲ������¼������ݣ���ʾ�¼���������
				{
					String[] add=((String)newmap.get(newkey)).split(",");
					String[] str=newkey.split(",");
					for(int i=0;i<add.length;i++)
					{
						// CCBegin SS5
						/*
						 * addvec.add(new
						 * String[]{str[0],str[1],add[i],str[2],str[3]});
						 * if(!bgvec.contains(str[0])) { bgvec.add(str[0]); }
						 */
						if(rootFlag)
						{
							addvec.add(new String[]{"",str[1],add[i],str[2],str[3]});
						}
						else
						{
							addvec.add(new String[]{str[0],str[1],add[i],str[2],str[3]});
						}
						// CCEnd SS5
					}
				}
			}
//			System.out.println("-----------------addvec------------------");
			for(int aa=0;aa<addvec.size();aa++)
			{
				String[] str=(String[])addvec.elementAt(aa);
//				System.out.println(Arrays.toString(str));
			}
//			System.out.println("-----------------updatevec------------------");
			for(int bb=0;bb<updatevec.size();bb++)
			{
				String[] str=(String[])updatevec.elementAt(bb);
//				System.out.println(Arrays.toString(str));
			}
//			System.out.println("-----------------deletevec------------------");
			for(int cc=0;cc<deletevec.size();cc++)
			{
				String[] str=(String[])deletevec.elementAt(cc);
//				System.out.println(Arrays.toString(str));
			}
//			System.out.println("-----------------over------------------");
			// System.out.println("bgvec:"+bgvec);
			result.put("add",addvec);
			result.put("update",updatevec);
			result.put("delete",deletevec);
			result.put("bgvec",bgvec);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return result;
	}

	//CCBegin SS10
	/**
	 * ��ȡ��ǰ�û����ڵĹ�����
	 * @return ����
	 * @throws QMException
	 */
	public String getCurrentDWBS() throws QMException
	//CCEnd SS10
	{
		String dwbs="";
		DocServiceHelper dsh=new DocServiceHelper();
		String com=dsh.getUserFromCompany();
//		System.out.println("com==="+com);
		CodingManageService cmservice=(CodingManageService)EJBServiceHelper.getService("CodingManageService");
		CodingIfc coding=cmservice.findCodingByContent(com,"��������","�������");
		if(coding==null)
		{
			dwbs="";
		}
		else
		{
			dwbs=coding.getShorten();
			if(dwbs==null)
			{
				dwbs="";
			}
		}
		if(com.equals("Admin"))
		{
			dwbs="R06";
		}
//		System.out.println("dwbs==="+dwbs);
		return dwbs;
	}

	/**
	 * ���������滻���ͱ� id ���ͳ���bsoid,������,����,�滻����1,�滻����2,�滻����3...
	 */
	public String saveBatchUpdateCM(String id) throws QMException
	{
//		System.out.println("come in saveBatchUpdateCM");
		Connection conn=null;
		Statement st=null;
		ResultSet rs=null;
		String str="";
		try
		{
			String[] s=id.split(",");
			if(s==null)
			{
				return "[{�������ݴ���}]";
			}
			if(s.length<4)
			{
				return "[{δ�õ��滻���͵ı����Ϣ�����������¼�룡}]";
			}

			String unpart="";

			StandardPartService spService=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			VersionControlService vcService=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();

			conn=PersistUtil.getConnection();
			st=conn.createStatement();

			// ֧�����µ���
			st.executeQuery("delete batchupdateCM where yPart='"+s[0]+"' and dwbs='"+s[2]+"'");

			String ytsql="INSERT INTO batchupdateCM (yPart,assisPart,fbdID,createTime,modifyTime,dwbs) VALUES ";
			String tempsql="";
			String sendData=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

			String insql="";
			Vector numvec=new Vector();
			for(int i=3;i<s.length;i++)
			{
				numvec.add(s[i]);
				if(insql.equals(""))
				{
					insql="'"+s[i]+"'";
				}
				else
				{
					insql=insql+",'"+s[i]+"'";
				}
			}
//			System.out.println("insql=="+insql);
//			System.out.println("sql=="+"select bsoID,partNumber from QMPartMaster where partNumber in ("+insql+")");
			rs=st.executeQuery("select bsoID,partNumber from QMPartMaster where partNumber in ("+insql+")");
			QMPartIfc partIfc=null;

			while(rs.next())
			{
				// System.out.println("rs.getString(2)=="+rs.getString(2));
				if(numvec.contains(rs.getString(2)))
				{
					QMPartMasterIfc partmaster=(QMPartMasterIfc)ps.refreshInfo(rs.getString(1));
					Collection co=vcService.allIterationsOf(partmaster);
					Iterator itea=co.iterator();
					if(itea.hasNext())
					{
						partIfc=(QMPartIfc)itea.next();
					}
					else
					{
//						System.out.println("û���ҵ��㲿�����󣡣�"+rs.getString(1)+"=="+rs.getString(2));
						numvec.remove(rs.getString(2));
						continue;
					}
					tempsql=ytsql+"('"+s[0]+"', '"+partIfc.getBsoID()+"', '', to_date('"+sendData
						+"','yyyy-mm-dd hh24:mi:ss'), to_date('"+sendData+"','yyyy-mm-dd hh24:mi:ss'), '"+s[2]+"')";
					st.addBatch(tempsql);
					numvec.remove(rs.getString(2));
				}
			}
//			System.out.println("numvec=="+numvec);
			if(numvec.size()>0)
			{
				for(int i=0;i<numvec.size();i++)
				{
					if(unpart.equals(""))
					{
						unpart=numvec.elementAt(i).toString();
					}
					else
					{
						unpart=unpart+""+numvec.elementAt(i).toString();
					}
				}
			}

			// ����������������㲿��������ʾ������ӡ�
			if(!unpart.equals(""))
			{
				st.close();
				conn.close();
				rs.close();
				return "[{ϵͳ��δ�ҵ��㲿��"+unpart+"�����������¼�룡}]";
			}

			st.executeBatch();
			// �ر�Statement
			st.close();
			// �ر����ݿ�����
			conn.close();
			str="[{�ɹ�}]";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				st.close();
				conn.close();
				rs.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * ��������ȫ���滻���ͱ� id ����id
	 */
	public String saveAllBatchUpdateCM(String id) throws QMException
	{
//		System.out.println("come in saveAllBatchUpdateCM");
		if(id==null||id.equals(""))
		{
			return "[{�������ݴ���}]";
		}
		Connection conn=null;
		Statement st=null;
		// CCBegin SS5
		Statement st1=null;
		// CCEnd SS5
		ResultSet rs=null;
		String str="";
		String dwbs=getCurrentDWBS();
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(id);
			if(part==null)
			{
				return "[{�������ݴ����޷��ѵ��㲿����}]";
			}
			String carModelCode=part.getPartNumber();

			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			// CCBegin SS5
			st1=conn.createStatement();
			// CCEnd SS5

			// ֧�����µ���
			st.executeQuery("delete batchupdateCM where yPart='"+id+"' and dwbs='"+dwbs+"'");

			String ytsql="INSERT INTO batchupdateCM (yPart,assisPart,fbdID,createTime,modifyTime,dwbs) VALUES ";
			String tempsql="";
			String sendData=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

			String insql="";
			HashMap map=getGYBomDifferent(carModelCode,dwbs);
			if(map==null)
			{
				return "[{��������һ�β��ã�û����ʷ�ṹ��}]";
			}
			if(map.size()==0)
			{
				return "[{������û�бȽϳ��ṹ�仯��}]";
			}
			Vector bgvec=(Vector)map.get("bgvec");
			if(bgvec==null)
			{
				return "[{������û�бȽϳ��ṹ�仯����}]";
			}

			// CCBegin SS5
			/*
			 * for(int i=0;i<bgvec.size();i++) { String carid =
			 * (String)bgvec.elementAt(i); if(insql.equals("")) { insql = "'" +
			 * carid + "'"; } else { insql = insql + ",'"+carid+"'"; } }
			 * //System.out.println("insql=="+insql); QMPartIfc partIfc=null; rs
			 * = st.executeQuery(
			 * "select distinct carmodelcode from gybomstructure where parentpart in ("
			 * +insql+")"); while(rs.next()) { partIfc =
			 * findPartLasterByNumber(rs.getString(1)); if(partIfc==null) {
			 * System.out.println("û���ҵ��㲿������-->"+rs.getString(1)); continue; }
			 * if(id.equals(partIfc.getBsoID())) { continue; } tempsql = ytsql +
			 * "('"+id+"', '"+partIfc.getBsoID()+"', '', to_date('"+sendData+
			 * "','yyyy-mm-dd hh24:mi:ss'), to_date('"
			 * +sendData+"','yyyy-mm-dd hh24:mi:ss'), '"+dwbs+"')";
			 * st.addBatch(tempsql); } st.executeBatch();
			 */
			for(int i=0;i<bgvec.size();i++)
			{
				QMPartIfc partIfc=null;
				String[] linkid=((String)bgvec.elementAt(i)).split(",");
//				System.out.println("linkid=="+Arrays.toString(linkid));
				if(linkid[0].equals("root"))
				{
					rs=st
						.executeQuery("select distinct parentPart from gybomstructure where effectCurrent='1' and childPart='"
							+linkid[1]+"' and dwbs='"+dwbs+"'");
					while(rs.next())
					{
						partIfc=findPartLasterByNumber(rs.getString(1));
						if(partIfc==null)
						{
							System.out.println("û���ҵ��㲿������-->"+rs.getString(1));
							continue;
						}
						// ������س����ǵ�ǰ�������ͣ��򲻴�����SS5������Ӧ�ò����ߵ����
						if(id.equals(partIfc.getBsoID()))
						{
							continue;
						}
						tempsql=ytsql+"('"+id+"', '"+partIfc.getBsoID()+"', '', to_date('"+sendData
							+"','yyyy-mm-dd hh24:mi:ss'), to_date('"+sendData+"','yyyy-mm-dd hh24:mi:ss'), '"+dwbs+"')";
						st1.addBatch(tempsql);
					}
				}
			}
			st1.executeBatch();
			st1.close();
			// CCEnd SS5
			// �ر�Statement
			st.close();
			// �ر����ݿ�����
			conn.close();
			str="[{�ɹ�}]";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				st.close();
				// CCBegin SS5
				if(st1!=null)
				{
					st1.close();
				}
				// CCEnd SS5
				conn.close();
				rs.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * ��ȡ�����滻���ͱ�
	 */
	public String getBatchUpdateCM(String id,String carModelCode,String dwbs) throws QMException
	{
//		System.out.println("come in getBatchUpdateCM   id=="+id+"  carModelCode=="+carModelCode+"  dwbs=="+dwbs);
		JSONArray json=new JSONArray();
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=null;
			// ���ø��ڵ�
			JSONObject jo=new JSONObject();

			// ��ȡ��ǩ
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			try
			{
				conn=PersistUtil.getConnection();
				stmt=conn.createStatement();
				String sql="select assisPart from batchupdateCM where yPart='"+id+"' and dwbs='"+dwbs+"'";
				rs=stmt.executeQuery(sql);
				while(rs.next())
				{
					part=(QMPartIfc)ps.refreshInfo(rs.getString(1));

					jo=new JSONObject();
					jo.put("num",part.getPartNumber());
					json.put(jo);
				}

				// ��ղ��ر�sql��������
				rs.close();
				// �ر�Statement
				stmt.close();
				// �ر����ݿ�����
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(rs!=null)
					{
						rs.close();
					}
					if(stmt!=null)
					{
						stmt.close();
					}
					if(conn!=null)
					{
						conn.close();
					}
				}
				catch(SQLException ex1)
				{
					ex1.printStackTrace();
				}
			}
//			System.out.println(json.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * ���汾 ������bom�Ͻڵ�汾����һ���汾������ԭ���汾�ǡ�D����ѡ�񽵰汾֮���Ϊ��C���汾
	 */
	public String upperVersion(String linkid,String id,String carModelCode,String dwbs) throws QMException
	{
//		System.out.println("come in upperVersion");
		String str="";
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			StandardPartService spService=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			VersionControlService vcService=(VersionControlService)EJBServiceHelper.getService("VersionControlService");

			QMPartIfc part=(QMPartIfc)ps.refreshInfo(id);
			if(part.getVersionID().equals("A"))
			{
				return "[{��ǰ�Ӽ���A�棬���ܽ��н��汾��}]";
			}

			QMPartMasterIfc partmaster=(QMPartMasterIfc)ps.refreshInfo(part.getMasterBsoID());
			Collection co=vcService.allVersionsOf(partmaster);
			Iterator itea=co.iterator();
			QMPartIfc partIfc=null;
			while(itea.hasNext())
			{
				partIfc=(QMPartIfc)itea.next();
				if(part.getBsoID().equals(partIfc.getBsoID()))
				{
					if(itea.hasNext())
					{
						partIfc=(QMPartIfc)itea.next();
					}
					else
					{
						partIfc=null;
					}
					break;
				}
			}

			if(partIfc==null)
			{
				return "[{δ���ҵ���ǰ�Ӽ���һ�汾��}]";
			}

			// ���¹������Ӽ�id�滻����һ���㲿��id
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			try
			{
				conn=PersistUtil.getConnection();
				stmt=conn.createStatement();
				//CCBegin SS70
//				stmt.executeQuery("update GYBomStructure set childPart='"+partIfc.getBsoID()
				stmt.executeQuery("update GYBomStructure set childPart='"+partIfc.getBsoID()+ "',bz1='" + "red"
				//CCEnd SS70
					+"' where effectCurrent='0' and childPart='"+part.getBsoID()+"' and dwbs='"+dwbs+"'");
System.out.println("sql���----update GYBomStructure set childPart='"+partIfc.getBsoID()+ "',bz1='" + "red"
					+"' where effectCurrent='0' and childPart='"+part.getBsoID()+"' and dwbs='"+dwbs+"'");
				// �ر�Statement
				stmt.close();
				// �ر����ݿ�����
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(rs!=null)
					{
						rs.close();
					}
					if(stmt!=null)
					{
						stmt.close();
					}
					if(conn!=null)
					{
						conn.close();
					}
				}
				catch(SQLException ex1)
				{
					ex1.printStackTrace();
				}
			}

			// ɾ����ǰ�汾�Ӽ�����
			Collection ids=new ArrayList();
			ids=bianliGYBomid(ids,part.getBsoID(),carModelCode,dwbs);
			deleteGYBom(ids);

			// ������һ�汾�Ӽ�����
			Collection mtree=new ArrayList();
			Vector linkvec=new Vector();
			mtree=getBomLinks(mtree,partIfc,carModelCode,dwbs,linkvec);
			//CCBegin SS15
			saveGYBom(mtree, true, dwbs);//SS21
			//CCEnd SS15

			// ��ȡ��ǰ�㲿�������Ӽ����½ṹ
			str=getGYBomAll(linkid,partIfc.getBsoID(),carModelCode,dwbs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * ����ָ���㲿������һ���Ӽ��ڵ㼯�� ������ӹ���bom��Ŀ������
	 * 
	 * @param linkid
	 *            id
	 * @param id
	 *            �㲿��bsoID
	 * @param carModelCode
	 *            ������
	 * @param dwbs
	 *            ��λ
	 * @return �㲿������һ���Ӽ������ṹ
	 */
	public String getGYBomAll(String linkid,String id,String carModelCode,String dwbs) throws QMException
	{
//		System.out.println("come in getGYBomAll   linkid=="+linkid+"  id=="+id+"  carModelCode=="+carModelCode
//			+"  dwbs=="+dwbs);
		JSONArray json=new JSONArray();
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(id);

			// ��ȡ��ǩ
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			try
			{
				conn=PersistUtil.getConnection();
				stmt=conn.createStatement();

				Collection route=new ArrayList();
				HashMap map=new HashMap();
				String zhizao="";
				String zhuangpei="";

				if(dwbs.equals("W34"))
				{
					map=getSecondRoute("'"+part.getBsoID()+"'",map);
					if(map.get(part.getBsoID())!=null)
					{
						String[] routestr=(String[])(map.get(part.getBsoID()));
						zhizao=routestr[0];
						zhuangpei=routestr[1];
					}
				}
				else
				{
					map=getRoute("'"+part.getMasterBsoID()+"'",map);
					if(map.get(part.getMasterBsoID())!=null)
					{
						String[] routestr=(String[])(map.get(part.getMasterBsoID()));
						zhizao=routestr[0];
						zhuangpei=routestr[1];
					}
				}

				String selectsql="select parentPart,childPart,quantity,unit,bz1 from GYBomStructure where id='"+linkid
					+"'";
				rs=stmt.executeQuery(selectsql);
				rs.next();
				//CCBegni SS42
				String pfbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",rs.getString(2));
				if(pfbyIbaValue==""){
					pfbyIbaValue = part.getVersionValue();
				}
				//CCEnd SS42
				// ���ø��ڵ�
				JSONObject jo=new JSONObject();
				jo.put("id",rs.getString(2));
				jo.put("pId",rs.getString(1));
				jo.put("num",part.getPartNumber());
				jo.put("pname",part.getPartName());
				jo.put("linkid",linkid);
				//CCBegin SS42
//				jo.put("name",
//					part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "+part.getViewName());
				jo.put("name",
						part.getPartNumber()+" "+part.getPartName()+" "+pfbyIbaValue+" "+part.getViewName());
				//CCEnd SS42
				jo.put("open","true");
				jo.put("zz",zhizao);
				jo.put("zp",zhuangpei);
				jo.put("quantity",rs.getString(3));
				jo.put("dw",rs.getString(4));
				//CCBegin SS69
//				if(rs.getString(5)!=null&&rs.getString(5).equals("red"))
//				{
//					jo.put("color","red");
//				}
				if(rs.getString(5)!=null)
				{
					if(rs.getString(5).equals("red")){
						jo.put("color","red");
					}if(rs.getString(5).equals("green")){
						jo.put("color","green");
					}
				}
				//CCEnd SS69
				else
				{
					jo.put("color","black");
				}
				jo.put("icon","bom/map/gypart.gif");
				jo.put("effect","false");
				//CCBegin SS29
				//CCBegin SS42 
//				jo.put("version",part.getVersionValue());
				jo.put("version",pfbyIbaValue);
				//CCEnd SS42
				//CCEnd SS29
				json.put(jo);

				// ���ϵͳ�д���δ��Ч��BOM���δ��Ч��bom
				String sql="select childPart,quantity,id,unit,bz1 from GYBomStructure where effectCurrent='0' and parentPart='"
					+id+"' and dwbs='"+dwbs+"' order by childNumber";
				rs=stmt.executeQuery(sql);

				String insql="";
				while(rs.next())
				{
					String childPart=rs.getString(1);
					part=(QMPartIfc)ps.refreshInfo(childPart);
					if(part==null)
					{
						System.out.println("δ�ҵ��Ӽ�id��"+childPart);
						continue;
					}
					if(insql.equals(""))
					{
						if(dwbs.equals("W34"))
						{
							insql="'"+part.getBsoID()+"'";
						}
						else
						{
							insql="'"+part.getMasterBsoID()+"'";
						}
					}
					else
					{
						if(dwbs.equals("W34"))
						{
							insql=insql+",'"+part.getBsoID()+"'";
						}
						else
						{
							insql=insql+",'"+part.getMasterBsoID()+"'";
						}
					}
				}
				// System.out.println("insql 111="+insql);
				if(!insql.equals(""))
				{
					if(dwbs.equals("W34"))
					{
						map=getSecondRoute(insql,map);
					}
					else
					{
						map=getRoute(insql,map);
					}
				}

				rs=stmt.executeQuery(sql);
				while(rs.next())
				{
					String childPart=rs.getString(1);
					part=(QMPartIfc)ps.refreshInfo(childPart);
					if(part==null)
					{
						System.out.println("δ�ҵ��Ӽ�id��"+childPart);
						continue;
					}

					if(dwbs.equals("W34"))
					{
						if(map.get(part.getBsoID())!=null)
						{
							String[] routestr=(String[])(map.get(part.getBsoID()));
							zhizao=routestr[0];
							zhuangpei=routestr[1];
						}
					}
					else
					{
						if(map.get(part.getMasterBsoID())!=null)
						{
							String[] routestr=(String[])(map.get(part.getMasterBsoID()));
							zhizao=routestr[0];
							zhuangpei=routestr[1];
						}
					}
					//CCBegni SS42
					String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",childPart);
					if(fbyIbaValue==""){
						fbyIbaValue = part.getVersionValue();
					}
					//CCEnd SS42

					jo=new JSONObject();
					jo.put("id",childPart);
					jo.put("pId",id);
					jo.put("num",part.getPartNumber());
					jo.put("pname",part.getPartName());
					jo.put("linkid",Integer.toString(rs.getInt(3)));
					//CCBegin SS42
//					jo.put("name",
//						part.getPartNumber()+" "+part.getPartName()+" "+part.getVersionValue()+" "+part.getViewName());
					jo.put("name",
							part.getPartNumber()+" "+part.getPartName()+" "+fbyIbaValue+" "+part.getViewName());
					//CCEnd SS42
					jo.put("open","false");
					jo.put("zz",zhizao);
					jo.put("zp",zhuangpei);
					jo.put("quantity",rs.getString(2));
					jo.put("dw",rs.getString(4));
					//CCBegin SS69
//					if(rs.getString(5)!=null&&rs.getString(5).equals("red"))
//					{
//						jo.put("color","red");
//					}
					if(rs.getString(5)!=null)
					{
						if(rs.getString(5).equals("red")){
							jo.put("color","red");
						}if(rs.getString(5).equals("green")){
							jo.put("color","green");
						}
					}
					//CCEnd SS69
					else
					{
						jo.put("color","black");
					}
					jo.put("icon","bom/map/gypart.gif");
					jo.put("effect","false");
					//CCBegin SS29
					//CCBegin SS42
//					jo.put("version",part.getVersionValue());
					jo.put("version",fbyIbaValue);
					//CCEnd SS42
					//CCEnd SS29
					json.put(jo);
					zhizao="";
					zhuangpei="";
				}

				// ��ղ��ر�sql��������
				rs.close();
				// �ر�Statement
				stmt.close();
				// �ر����ݿ�����
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(rs!=null)
					{
						rs.close();
					}
					if(stmt!=null)
					{
						stmt.close();
					}
					if(conn!=null)
					{
						conn.close();
					}
				}
				catch(SQLException ex1)
				{
					ex1.printStackTrace();
				}
			}
//			System.out.println(json.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return json.toString();
	}

	/**
	 * �ݹ��ȡδ��Ч�ṹ��id����
	 */
	private Collection bianliGYBomid(Collection ids,String id,String carModelCode,String dwbs) throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select id,childPart from GYBomStructure where parentPart='"+id
				+"' and effectCurrent='0' and dwbs='"+dwbs+"'");

			while(rs.next())
			{
				ids.add(rs.getString(1));
				ids=bianliGYBomid(ids,rs.getString(2),carModelCode,dwbs);
			}

			// �ر�ResultSet
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return ids;
	}

	/**
	 * �ݹ��ȡ�㲿���ṹ�У�δ��Ч�ṹ�ĸ�������
	 */
	private Vector getUnEffParts(String id,String dwbs,Vector vec) throws QMException
	{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select childPart from GYBomStructure where parentPart='"+id
				+"' and effectCurrent='0' and dwbs='"+dwbs+"'");
			int i=0;
			while(rs.next())
			{
				if(!vec.contains(id))
				{
					vec.add(id);
				}
				vec=getUnEffParts(rs.getString(1),dwbs,vec);
				i++;
			}

			// û��δ��Ч�ṹ�����ȡ��Ч�ṹ �������ҡ�
			if(i==0)
			{
				rs=stmt.executeQuery("select childPart from GYBomStructure where parentPart='"+id
					+"' and effectCurrent='1' and dwbs='"+dwbs+"'");
				while(rs.next())
				{
					vec=getUnEffParts(rs.getString(1),dwbs,vec);
				}
			}

			// �ر�ResultSet
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return vec;
	}

	/**
	 * ��� �����Ҫ��ֵ��߼��ܳɣ���ͬ�����Զ�����һ���߼��ܳɺ� ��Ź���ԭ�߼��ܳɺ�+F+3λ��ˮ��
	 * 
	 * @parm String linkid ����id��linkid��
	 * @parm String carModelCode ������
	 * @parm String dwbs ����
	 */
	public String chaiFenPart(String linkid) throws QMException
	{
		String str="";
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select parentPart,childPart,carModelCode,dwbs from GYBomStructure where id='"+linkid
				+"'");

			if(rs.next())
			{
				String pid=rs.getString(1);
				String id=rs.getString(2);
				QMPartIfc part=(QMPartIfc)ps.refreshInfo(id);
				//CCBegin SS67
//				String partNumber=getLogicPartNums(part.getPartNumber()+"F");
				String partNumber=part.getPartNumber()+"-FC";
				//CCEnd SS67
				String partName=part.getPartName();

				//CCBegin SS58
//				str=pid+","+partNumber+","+partName+","+part.getViewName()+","+part.getDefaultUnit()+","
//					+part.getPartType()+","+part.getProducedBy()+",���������㲿����������,"+part.getLocation()+","+rs.getString(3)
//					+","+rs.getString(4);
				str=pid+"#@"+partNumber+"#@"+partName+"#@"+part.getViewName()+"#@"+part.getDefaultUnit()+"#@"
						+part.getPartType()+"#@"+part.getProducedBy()+"#@���������㲿����������#@"+part.getLocation()+"#@"+rs.getString(3)
						+"#@"+rs.getString(4);
				//CCEnd SS58
			}

			stmt.executeQuery("update GYBomStructure set bz1='red' where id='"+linkid+"'");

			// �ر�ResultSet
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return createPart(str);
	}

	/**
	 * ����һ�����嵥�������߼��ܳɱ������õ���������ء� * �����ȡ����
	 * 1���㼶�ɵ��������ȡװ��·��Ϊ���ܡ����㲿����������װ��ͼ���߼��ܳɣ� 2��װ��ͼ����ţ���5-7Ϊ001��2��3��5�ȣ�
	 * 3���߼��ܳ�����ţ���5λΪ��G��������·�߰������ܡ��� 4��������ŵ�5λΪ��G��������·�߰������ܡ�ʱ��ȡ��һ�㼶װ��·�߰������ܡ����Ӽ���
	 * 5������·�߲������ܡ���װ��·�ߺ����ܡ�ʱ������ȡ�Ӽ�����ȡͬһ�㼶װ��·�ߺ����ܡ��ļ���
	 * 6������·�߰������ܡ���װ��·��Ҳ�������ܡ�ʱ������ȡ�Ӽ�����ȡͬһ�㼶װ��·�ߺ����ܡ��ļ��� 7�������ȡ�߼��ܳ�ǰ4λ�� ��һ�ε��Բ������
	 * 8�����Ӷԡ���������������1000410�������ȡ����ֱ�ӽ�����Ÿ�ֵΪ��1000����
	 * 9����1000410�Ӽ��и��߼��ܳ�����·�ߺ����ܡ����Ӽ�װ��·�ߺ����ܡ����㲿��������ȡ��
	 * 10������"��������"����"������ĸ",װ��·�߰���"��"���Ӽ���
	 * 11�����ӵ������б�ͷ�������˳�򣬼������Ϻš���������������š�����������·�ߡ�װ��·�ߡ���������״̬����׼��š���һ��������ţ�
	 * �ڶ��ε��Բ������ 12�������߼��ܳ�����·�ߺ����ܡ����Ӽ�װ��·�ߺ����ܡ��ļ���ȡI�������Ǹ�����ŵ�5λ�Ƿ�Ϊ��G������
	 * 13�������߼��ܳ�������·�߲������ܡ�ʱ������ȡ�Ӽ���
	 * 14�����Ӷԡ����������������������ƶ����߼��ܳɡ��������ȡ����ֱ�ӽ�����Ÿ�ֵΪ��2500����
	 * 15�����Ӷԡ�����������������������ͷ�ܳɡ��̻Һ��ܳɡ��泵���ߵơ����Ǿ������ܳɡ����������ȡ����ֱ�ӽ�����Ÿ�ֵΪ��3900����
	 * 16�����Ӷԡ����Ϻš�������5000010���������ȡ����ֱ�ӽ�����Ÿ�ֵΪ��5000���� �����ε��Բ������
	 * 17��2400010��2500010��3722024��3902G00ֱ����ȡ
	 */
	public Vector getExportFirstLeveList(String id,String carModelCode,String dwbs) throws QMException
	{
		Vector vec=new Vector();
		HashMap routemap=new HashMap();
		bianliFirstLeveList(vec,id,carModelCode,dwbs,1,"",routemap);
		System.out.println("��ʼ����vec=="+vec.size());
		/*
		 * for(int i=0;i<vec.size();i++) { String[] str =
		 * (String[])vec.elementAt(i);
		 * System.out.println(str[0]+"&&"+str[1]+"&&"
		 * +str[2]+"&&"+str[3]+"&&"+str[4]+"&&"+str[5]+"&&"+str[6]+"&&"+str[7]);
		 * }
		 */
		return vec;
	}

	private void bianliFirstLeveList(Vector vec,String id,String carModelCode,String dwbs,int level,String pzz,
		HashMap routemap) throws QMException
	{
		System.out.println("xin ��������");
		PersistService ps=(PersistService)EJBServiceHelper.getPersistService();

		String effect=getEffect(id,carModelCode,dwbs);
		if(effect.equals("1"))
		{
			effect="0";
		}
		else if(effect.equals("2")||effect.equals("3"))
		{
			effect="1";
		}
		// ��ȡ�Ӽ�id������ findChildPart(String id, String carModelCode, String dwbs,
		// String effect)
		Vector subvec=findChildPart(id,carModelCode,dwbs,effect);
		QMPartIfc parentpart=(QMPartIfc)ps.refreshInfo(id);

		// ��ȡ�Ӽ�·��
		String childPart="";
		QMPartIfc part=null;
		String insql="";
		for(int i=0;i<subvec.size();i++)
		{
			String[] str=subvec.elementAt(i).toString().split(",");
			childPart=str[0];
			part=(QMPartIfc)ps.refreshInfo(childPart);
			if(level==2)
			{
				System.out.println("2��"+part.getPartNumber());
			}
			if(part==null)
			{
				System.out.println("δ�ҵ��Ӽ�id��"+childPart);
				continue;
			}
			if(routemap.containsKey(part.getMasterBsoID()))
			{
				continue;
			}
			if(insql.equals(""))
			{
				if(dwbs.equals("W34"))
				{
					insql="'"+part.getBsoID()+"'";
				}
				else
				{
					insql="'"+part.getMasterBsoID()+"'";
				}
			}
			else
			{
				if(dwbs.equals("W34"))
				{
					insql=insql+",'"+part.getBsoID()+"'";
				}
				else
				{
					insql=insql+",'"+part.getMasterBsoID()+"'";
				}
			}
		}

		if(!insql.equals(""))
		{
			if(dwbs.equals("W34"))
			{
				routemap=getSecondRoute(insql,routemap);
			}
			else
			{
				routemap=getRoute(insql,routemap);
			}
		}

		// �ݹ��ȡ�Ӽ�

		String zhizao="";
		String zhuangpei="";
		for(int i=0;i<subvec.size();i++)
		{
			String[] str=subvec.elementAt(i).toString().split(",");
			childPart=str[0];
			part=(QMPartIfc)ps.refreshInfo(childPart);

			if(dwbs.equals("W34"))
			{
				if(routemap.get(part.getBsoID())!=null)
				{
					String[] routestr=(String[])(routemap.get(part.getBsoID()));
					zhizao=routestr[0];
					zhuangpei=routestr[1];
				}
			}
			else
			{
				if(routemap.get(part.getMasterBsoID())!=null)
				{
					String[] routestr=(String[])(routemap.get(part.getMasterBsoID()));
					zhizao=routestr[0];
					zhuangpei=routestr[1];
				}
			}

			if(zhuangpei.equals(""))
			{
				zhuangpei=pzz;
			}

			// ������߼��ܳɣ���������·�ߺ��С��ܡ��������Ӽ�
			if(isGpart(part.getPartNumber())&&zhizao.indexOf("��")!=-1)
			{
				bianliFirstLeveList(vec,childPart,carModelCode,dwbs,level+1,zhizao,routemap);
			}
			else
			{
				if(part.getPartNumber().startsWith("5000010"))
				{
					vec.add(new String[]{Integer.toString(level),part.getPartNumber(),part.getPartName(),"5000",str[1],
						zhizao,zhuangpei,parentpart.getPartNumber()});
					bianliFirstLeveList(vec,childPart,carModelCode,dwbs,level+1,zhizao,routemap);
				}
				else if(part.getPartNumber().startsWith("1000940"))
				{
					vec.add(new String[]{Integer.toString(level),part.getPartNumber(),part.getPartName(),"5000",str[1],
						zhizao,zhuangpei,parentpart.getPartNumber()});
					bianliFirstLeveList(vec,childPart,carModelCode,dwbs,level+1,zhizao,routemap);
				}
				else if(part.getPartNumber().startsWith("1000410"))
				{
					vec.add(new String[]{Integer.toString(level),part.getPartNumber(),part.getPartName(),"1000",str[1],
						zhizao,zhuangpei,parentpart.getPartNumber()});
					bianliFirstLeveList(vec,childPart,carModelCode,dwbs,level+1,zhizao,routemap);
				}
				else if(part.getPartName().indexOf("������ͷ�ܳ�")!=-1||part.getPartName().indexOf("�̻Һ��ܳ�")!=-1
					||part.getPartName().indexOf("�泵���ߵ�")!=-1)
				{
					vec.add(new String[]{Integer.toString(level),part.getPartNumber(),part.getPartName(),"3900",str[1],
						zhizao,zhuangpei,parentpart.getPartNumber()});
				}
				// ����������߼��ܳɣ����ҵ�ǰ��װ�京�С��ܡ�������뼯��
				else if(isGpart(parentpart.getPartNumber())&&zhuangpei.indexOf("��")!=-1)
				{
					vec.add(new String[]{Integer.toString(level),part.getPartNumber(),part.getPartName(),
						parentpart.getPartNumber().substring(0,4),str[1],zhizao,zhuangpei,parentpart.getPartNumber()});
				}
			}
			zhizao="";
			zhuangpei="";
		}
	}

	/**
	 * ��ȡ�Ӽ�������
	 */
	private Vector findChildPart(String id,String carModelCode,String dwbs,String effect) throws QMException
	{
		Vector vec=new Vector();
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			rs=stmt.executeQuery("select childPart,quantity from GYBomStructure where effectCurrent='"+effect
				+"' and parentPart='"+id+"' and dwbs='"+dwbs+"' order by childNumber");
//			System.out.println("�����¼��Ӽ�==="+"select childPart,quantity from GYBomStructure where effectCurrent='"+effect
//					+"' and parentPart='"+id+"' and dwbs='"+dwbs+"' order by childNumber");
			while(rs.next())
			{
				vec.add(rs.getString(1)+","+rs.getString(2));
			}
			// �ر�ResultSet
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return vec;
	}

	private boolean isGpart(String num) throws QMException
	{
		boolean flag=false;
		if(num.length()>=5&&num.charAt(4)=='G')
		{
			flag=true;
		}
		return flag;
	}

	/**
	 * ��ӱ�Ʒ ��ѡ���㲿���Ӽ���ӵ�ָ���㲿���¡� ����
	 * 
	 * @parm String parentID ָ���㲿��
	 * @parm String beipinID ѡ���㲿��
	 * @parm String carModelCode ������
	 * @parm String dwbs ����
	 */
	public String addBeiPin(String parentID,String beipinID,String carModelCode,String dwbs) throws QMException
	{
//		System.out.println("come in addBeiPin");
		JSONArray json=new JSONArray();
		JSONObject jo=new JSONObject();
		try
		{
			Collection mtree=new ArrayList();
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(beipinID);

			if(part!=null)
			{
				StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
				VersionControlService vcservice=(VersionControlService)EJBServiceHelper
					.getService("VersionControlService");
				JFService jfs=(JFService)EJBServiceHelper.getService("JFService");

				PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
				Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);
				if(linkcoll!=null&&linkcoll.size()>0)
				{
					Vector linkvec=new Vector();
					String zhizao="";
					String zhuangpei="";
					Iterator iter=linkcoll.iterator();
					QMPartMasterIfc partMaster=null;
					QMPartIfc subpart=null;
					while (iter.hasNext()) {
						Object obj[] = (Object[]) iter.next();
						PartUsageLinkIfc link = (PartUsageLinkIfc) obj[0];
						// CCBegin SS64
						if (obj[1] instanceof QMPartIfc) {
							subpart = (QMPartIfc) obj[1];
							linkvec.add(parentID + subpart.getBsoID());

							//CCBegin SS70
//							String[] mt = new String[10];
							String[] mt = new String[11];
							//CCEnd SS70
							mt[0] = parentID;
							mt[1] = subpart.getBsoID();
							mt[2] = String.valueOf(link.getQuantity());
							mt[3] = "";
							mt[4] = dwbs;
							mt[5] = "0";
							mt[6] = "";
							mt[7] = "";
							mt[8] = link.getDefaultUnit().getDisplay();
							mt[9] = subpart.getPartNumber();
							//CCBegin SS70
							mt[10] = "red";
							//CCEnd SS70
							String linkid = saveGYBom(mt);
							HashMap map = new HashMap();

							if (dwbs.equals("W34")) {
								map = getSecondRoute("'" + part.getBsoID()
										+ "'", map);
								if (map.get(part.getBsoID()) != null) {
									String[] routestr = (String[]) (map
											.get(part.getBsoID()));
									zhizao = routestr[0];
									zhuangpei = routestr[1];
								}
							} else {
								map = getRoute("'" + part.getMasterBsoID()
										+ "'", map);
								if (map.get(part.getMasterBsoID()) != null) {
									String[] routestr = (String[]) (map
											.get(part.getMasterBsoID()));
									zhizao = routestr[0];
									zhuangpei = routestr[1];
								}
							}
							// CCBegni SS42
							String fbyIbaValue = RationHelper
									.getIbaValueByName("sourceVersion",
											subpart.getBsoID());
							if (fbyIbaValue == "") {
								fbyIbaValue = part.getVersionValue();
							}
							// CCEnd SS42

							jo = new JSONObject();
							jo.put("id", subpart.getBsoID());
							jo.put("pId", parentID);
							jo.put("num", subpart.getPartNumber());
							jo.put("pname", subpart.getPartName());
							// CCBegin SS42
							// jo.put("name",subpart.getPartNumber()+" "+subpart.getPartName()+" "+subpart.getVersionValue()
							// +" "+subpart.getViewName());
							jo.put("name", subpart.getPartNumber() + " "
									+ subpart.getPartName() + " " + fbyIbaValue
									+ " " + subpart.getViewName());
							// CCEnd SS42
							jo.put("zz", zhizao);
							jo.put("zp", zhuangpei);
							jo.put("quantity",
									String.valueOf(link.getQuantity()));
							jo.put("dw", link.getDefaultUnit().getDisplay());
							jo.put("linkid", linkid);
							jo.put("open", "false");
							//CCBegin SS70
//							jo.put("color", "black");
							jo.put("color", "red");
							//CCEnd SS70
							jo.put("icon", "bom/map/gypart.gif");
							// CCBegin SS29
							// CCBegin SS42
							// jo.put("version",subpart.getVersionValue());
							jo.put("version", fbyIbaValue);
							// CCEnd SS42
							// CCEnd SS29
							json.put(jo);

							getBomLinks(mtree, subpart, carModelCode, dwbs,
									linkvec);
							zhizao = "";
							zhuangpei = "";
						}
						// CCBegin SS64
					}
					// CCEnd SS64
				}
			}
			//CCBegin SS15
			saveGYBom(mtree, true, dwbs);//SS21
			//CCEnd SS15
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String s=json.toString();
//		System.out.println("addBeiPin json=="+s);
		return s;
	}

	/**
	 * ��õ�ǰ�û�
	 * 
	 * @throws QMException
	 */
	public UserInfo getCurrentUserInfo() throws QMException
	{
		SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
		return (UserInfo)sservice.getCurUserInfo();
	}

	/**
	 * ����bom��ʷ���ݵ��뷽�� AddUsageLink C01AM44141BF204 CQ1511065 ea 8 AddUsageLink
	 * C01AM44141BF204 Q1841240F6 ea 8 ��� ���� �Ӽ� ��λ ����
	 */
	public String uploadBomExcel(String isupdate)
	{
//		System.out.println("come in uploadBomExcel  isupdate:"+isupdate);
		long t1=System.currentTimeMillis();
		StringBuffer buffer=new StringBuffer();
		String dwbs="";
		try
		{
			PersistService pservice=(PersistService)EJBServiceHelper.getPersistService();
			VersionControlService vcService=(VersionControlService)EJBServiceHelper.getService("VersionControlService");

			dwbs=getCurrentDWBS();

			String excelPath=RemoteProperty.getProperty("com.faw_qm.loadFiles")+File.separator+"content"+File.separator;// Excel�Ĵ��·��
			logbuffer(buffer,"��ʼ���빤��BOM�ļ���\n");
			File f=new File(excelPath);
			if(f.exists()&&f.isDirectory())
			{
				String[] files=f.list();
				boolean flag=true;
				QMPartIfc ppart=null;
				QMPartIfc spart=null;
				HashMap unitmap=new HashMap();
				HashMap partmap=new HashMap();
				for(int i=0;i<files.length;i++)
				{
					// ������ɾ���ṹ����һ��һ�㡣
					Collection mtree=new ArrayList();
					Vector parentParts=new Vector();
					Vector deleteParts=new Vector();

					File file1=new File(f.getPath()+File.separator+files[i]);
					if(file1.getName().endsWith(".xls"))
					{
						logbuffer(buffer,"��鵼���ļ���"+file1.getName()+"��\n");
						String carModelCode=file1.getName().trim().substring(0,file1.getName().trim().lastIndexOf("."));

						if(!partmap.containsKey(carModelCode))
						{
							partmap.put(carModelCode,findPartByNumber(carModelCode));
						}
						QMPartIfc carpart=(QMPartIfc)partmap.get(carModelCode);
						if(carpart==null)
						{
							logbuffer(buffer,"����"+carModelCode+"ϵͳ�޴˼����޷����롣\n");
							continue;
						}

						FileInputStream inputStream=new FileInputStream(file1);
						POIFSFileSystem fs=new POIFSFileSystem(inputStream);
						HSSFWorkbook workBook=new HSSFWorkbook(fs);
						HSSFSheet sheet=workBook.getSheetAt(0);
						// # ������� �Ӽ���� ��λ ʹ������
						// AddUsageLink C01AM44141BF204 CQ1511065 ea 8
						// ����������������1�вż���
						int lrn=sheet.getLastRowNum();
						if(lrn<2)
						{
							logbuffer(buffer,"��ȡ��"+lrn+"�����ݣ��޷����룬���ټ�����\n");
							continue;
						}
						// ��ȡ��һ�к͵ڶ��е�һ����������У���Ƿ��ǵ����ļ���
						String bj1=String.valueOf(sheet.getRow(0).getCell((short)0)).trim();
						String bj2=String.valueOf(sheet.getRow(1).getCell((short)0)).trim();
						if(bj1.equals("#")&&bj2.equals("AddUsageLink"))
						{
							for(int j=1;j<=lrn;j++)
							{
								HSSFRow row=sheet.getRow(j);
								String pnum=String.valueOf(row.getCell((short)1)).trim();// �������
								String snum=String.valueOf(row.getCell((short)2)).trim();// �Ӽ����
								String unit=String.valueOf(row.getCell((short)3)).trim();// ��λ
								String quantity=String.valueOf(row.getCell((short)4)).trim();// ʹ������
								String subGroup=String.valueOf(row.getCell((short)5)).trim();// ����
								if(subGroup==null)
								{
									subGroup="";
								}
								// ���4�����ԣ�������ǿգ���������Ҳ�����¼��
								if((pnum==null||pnum.equals(""))&&(snum==null||snum.equals(""))
									&&(unit==null||unit.equals(""))&&(quantity==null||quantity.equals("")))
								{
									// System.out.println("��"+j+"��û�е������ݣ�������");
									continue;
								}
								// ���4�������Ƿ�Ϊ�գ����Ϊ�����¼��־���������롣
								if(pnum==null||pnum.equals("")||snum==null||snum.equals("")||unit==null
									||unit.equals("")||quantity==null||quantity.equals(""))
								{
									logbuffer(buffer,"��"+j+"��");
									if(pnum==null||pnum.equals(""))
									{
										logbuffer(buffer," �������");
									}
									if(snum==null||snum.equals(""))
									{
										logbuffer(buffer," �Ӽ����");
									}
									if(unit==null||unit.equals(""))
									{
										logbuffer(buffer," ��λ");
									}
									if(quantity==null||quantity.equals(""))
									{
										logbuffer(buffer," ʹ������");
									}
									logbuffer(buffer," ����û������\n");
									continue;
								}

								// ��������Ѵ�����Ч�ṹ�����������С�
								if(isupdate.equals("true"))
								{
								}
								else
								{
									if(parentParts.contains(pnum))
									{
										continue;
									}
								}

								// ���ݱ�Ż�ȡ�����㲿��
								if(!partmap.containsKey(pnum))
								{
									partmap.put(pnum,findPartByNumber(pnum));
								}
								if(!partmap.containsKey(snum))
								{
									partmap.put(snum,findPartByNumber(snum));
								}
								ppart=null;
								spart=null;
								ppart=(QMPartIfc)partmap.get(pnum);
								spart=(QMPartIfc)partmap.get(snum);
								// ����㲿���Ƿ���ڣ�������������¼��־���������롣
								if(ppart==null||spart==null)
								{
									logbuffer(buffer,"��"+j+"��");
									if(ppart==null)
									{
										logbuffer(buffer," ����"+pnum);
									}
									if(spart==null)
									{
										logbuffer(buffer," �Ӽ�"+snum);
									}
									logbuffer(buffer," ϵͳ�޴˼�\n");
									continue;
								}

								if(!unitmap.containsKey(unit))
								{
									Unit unitT=Unit.toUnit(unit);
									unitmap.put(unit,unitT.getDisplay());
								}

								// ��֯�������ݼ���
								// parentPart,childPart,quantity,carModelCode,dwbs,effectCurrent,locker,lockDate,unit,childNumber
								// String[] mt = new
								// String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,"1","","",(String)unitmap.get(unit),spart.getPartNumber(),subGroup};
								// System.out.println("======----====="+Arrays.toString(mt));
								if(isupdate.equals("true"))
								{
									// ����Ƿ���ڸù���BOM������������¼��־���������롣
									String exitstr=getEffect(ppart.getBsoID(),carModelCode,dwbs);
									// System.out.println(ppart.getPartNumber()+" eff:"+exitstr);
									// ������Ч�ṹ
									if(exitstr.equals("0"))
									{
										String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
											"1","","",(String)unitmap.get(unit),spart.getPartNumber(),subGroup};
										mtree.add(mt);
									}
									else if(exitstr.equals("1"))// ɾ��δ��Ч�ṹ������δ��Ч�ṹ����Ч�ṹ
									{
										// ���������δ��Ч�ṹ�Ѿ�ɾ��������������������ӡ�
										if(!deleteParts.contains(ppart.getBsoID()+",0"))
										{
											deleteParts.add(ppart.getBsoID()+",0");
										}
										String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
											"0","","",(String)unitmap.get(unit),spart.getPartNumber(),subGroup};
										String[] mt1=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
											"1","","",(String)unitmap.get(unit),spart.getPartNumber(),subGroup};
										mtree.add(mt);
										mtree.add(mt1);
									}
									else if(exitstr.equals("2"))// ɾ����Ч�ṹ����������Ч�ṹ
									{
										// ���������δ��Ч�ṹ�Ѿ�ɾ��������������������ӡ�
										if(!deleteParts.contains(ppart.getBsoID()+",1"))
										{
											deleteParts.add(ppart.getBsoID()+",1");
										}
										String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
											"1","","",(String)unitmap.get(unit),spart.getPartNumber(),subGroup};
										// System.out.println("======----====="+Arrays.toString(mt));
										mtree.add(mt);
									}
									else if(exitstr.equals("3"))// ɾ��δ��Ч�ṹ����Ч�ṹ������δ��Ч�ṹ����Ч�ṹ
									{
										// ���������δ��Ч�ṹ�Ѿ�ɾ��������������������ӡ�
										if(!deleteParts.contains(ppart.getBsoID()+",0"))
										{
											deleteParts.add(ppart.getBsoID()+",0");
										}
										// ���������δ��Ч�ṹ�Ѿ�ɾ��������������������ӡ�
										if(!deleteParts.contains(ppart.getBsoID()+",1"))
										{
											deleteParts.add(ppart.getBsoID()+",1");
										}
										String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
											"0","","",(String)unitmap.get(unit),spart.getPartNumber(),subGroup};
										String[] mt1=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
											"1","","",(String)unitmap.get(unit),spart.getPartNumber(),subGroup};
										mtree.add(mt);
										mtree.add(mt1);
									}
								}
								else
								{
									// ������� �Ѵ�����Ч����������Ҫ���е��뱣�档 �����Ѵ��ڼ���
									if(checkParentPartExit(ppart.getBsoID(),dwbs,"1"))
									{
										parentParts.add(pnum);
									}
									else
									{
										String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
											"1","","",(String)unitmap.get(unit),spart.getPartNumber(),subGroup};
										mtree.add(mt);
									}
								}
							}
							flag=false;
						}
						logbuffer(buffer,"�ļ���"+file1.getName()+"�ɹ����롣\n");
						// ���ļ��Ƶ�ͬ������ old �ļ�����
						// System.out.println(file1.getParent()+File.separator+"bom"+File.separator+file1.getName());
						file1.renameTo(new File(file1.getParent()+File.separator+"bom"+File.separator+file1.getName()));
					}

					// ÿ�������ļ�����һ�����ͽ���һ�δ洢��
//					System.out.println("�ļ�:"+file1.getName());
//					System.out.println("deleteParts:"+deleteParts.size());
//					System.out.println("mtree:"+mtree.size());
					if(isupdate.equals("true"))
					{
						for(int j=0;j<deleteParts.size();j++)
						{
							String[] str=deleteParts.elementAt(j).toString().split(",");
							deleteGYBomLevel(str[0],dwbs,str[1]);
						}
					}
					//CCBegin SS15
					saveGYBom(mtree, true, dwbs);//SS21
					//CCEnd SS15
				}

				unitmap=null;
				partmap=null;

				if(flag)
				{
					logbuffer(buffer,"û�пɵ�����ļ���\n");
				}
			}
			else
			{
				logbuffer(buffer,"û���ҵ������ļ�"+f.getPath()+"��\n");
			}
			logbuffer(buffer,"������ϡ�\n");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * ��ȡ����BOM�б��嵥
	 */
	public Vector getBomExcel() throws QMException
	{
		Vector result=new Vector();
		try
		{
			String excelPath=RemoteProperty.getProperty("com.faw_qm.loadFiles")+File.separator+"content"+File.separator;// Excel�Ĵ��·��
			File f=new File(excelPath);
			if(f.exists()&&f.isDirectory())
			{
				String[] files=f.list();
				for(int i=0;i<files.length;i++)
				{
					File file1=new File(f.getPath()+File.separator+files[i]);
					if(file1.getName().endsWith(".xls"))
					{
						result.add(file1.getName());
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	private void logbuffer(StringBuffer buffer,String s)
	{
		// System.out.println(s);
		buffer.append(s);
	}

	/**
	 * �����㲿�������ڵ���ʱ���ݱ�Ų����㲿�����°�
	 * 
	 * @param number
	 *            �㲿�����
	 * @return �����������㲿��
	 * @exception com.faw_qm.framework.exceptions.QMException
	 */
	public QMPartIfc findPartByNumber(String number) throws QMException
	{
		 System.out.println("come in findPartByNumber number=="+number);
		// System.out.println("�����㲿��������");
		// System.out.println("===================================="+number.indexOf("/"));
		// CCBegin SS1
		com.faw_qm.cderp.util.PartHelper helper=new com.faw_qm.cderp.util.PartHelper();
		Connection conn= null;
		ibabs = "0";
		svmap = new HashMap();
		boolean flag=false;
		String ver="";
		if(number.indexOf("/")>-1)
		{
			int l=number.length();
			int i=number.indexOf("/");
			// System.out.println("l===="+l);
			// System.out.println("i===="+i);
			if(l-i==2)
			{
				ver=number.substring(l-1);
				number=number.substring(0,i);
			}
			// System.out.println("number===="+number);

		}

		// CCEnd SS1
		
		QMPartIfc part=null;
		
		try
		{
			// System.out.println("��ʼ�����㲿��");
			StandardPartService spService=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			VersionControlService vcService=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
			Collection col=getAllPartMasters("",number.trim(),true);
			if(col==null||col.size()==0)
			{
				return null;
			}
			

			//------------------------------------------------------
			if(col.size()==1)
			{
				QMPartMasterIfc partmaster=(QMPartMasterIfc)col.iterator().next();
				Collection co=vcService.allVersionsOf(partmaster);
				
				boolean flag1=true;
				String aa[] = new String[2];
				
////			���û�а汾������ҹ��򣬷��Ϲ���ȥA�汾�������Ϲ��򣬲����衣�����еĸ��������ݣ�����û�еĸ�����ʾ
				if(ver.equals("")){
					Object[] vec1=co.toArray();
					QMPartIfc part1=(QMPartIfc)vec1[vec1.length-1];
					boolean ifver = helper.ifHasVersion(number,part1.getPartType().toString());	
					String cappNumber = "";
					if(ifver){
					}else{
					//û�а汾����
						 conn=PersistUtil.getConnection();
				
					   String sql1 = "select t.beforematerial from erppan t " +
					  "where t.aftermaterial = '"+number+"'";
					   PreparedStatement pstmt1 = conn.prepareStatement(sql1);
						//pstmt1.setFetchSize(10);
						ResultSet rs1 = pstmt1.executeQuery();
						//int bs = 0;
						while(rs1.next())
						{
							cappNumber =  rs1.getString(1) == null ? "" : rs1.getString(1).trim();
							break;
						}
						
						
						pstmt1.close();
						pstmt1 = null;
						rs1.close();
						rs1 = null;
						
						//---------����������ҵ��ˣ��������Ҷ�Ӧ�����Ϻ�------------------------------------
						//System.out.println("cappNumber111111111111===="+cappNumber);
						if(!cappNumber.equals("")){
							if(cappNumber.indexOf("/")>-1)
							{
								int l=cappNumber.length();
								int i=cappNumber.indexOf("/");
								// System.out.println("l===="+l);
								// System.out.println("i===="+i);
								if(l-i==2)
								{//���»�ȡ�汾
									ver=cappNumber.substring(l-1);
								}
								// System.out.println("number===="+number);

							}
						}else
						{
							return null;
						}
						
					}
				}
				//System.out.println("ver===="+ver);
				// System.out.println("co.size()1===="+co.size());
				// CCBegin SS1
				// part=(QMPartIfc)co.iterator().next();
				// CCBegin SS4
				
				if(ver.equals(""))
				{   
					
					Object[] vec=co.toArray();
					part=(QMPartIfc)vec[vec.length-1];
					
				}
				else
				{
					//CCBegin SS82
					//���Ȼ�ȡ����Դ�汾
//					String sql1 = "select t.IBAHOLDERBSOID from STRINGVALUE t " +
//									  "where t.DEFINITIONBSOID = 'StringDefinition_7646' and value like '%"+ver+
//									  "%' and t.IBAHOLDERBSOID in (" ;
					String sql1 = "select t.IBAHOLDERBSOID,t.Value from STRINGVALUE t " +
					  "where t.DEFINITIONBSOID = 'StringDefinition_7646' and t.IBAHOLDERBSOID in (" ;
					String sqltemp="";
					for(Iterator iter=co.iterator();iter.hasNext();)
					{
						// System.out.println("           ");
						QMPartIfc part1=(QMPartIfc)iter.next();
						if(sqltemp.equals(""))
						{
							sqltemp="'"+part1.getBsoID()+"'";
						}
						else
						{  
							sqltemp=sqltemp+",'"+part1.getBsoID()+"'";
						}
					}
					sql1 = sql1+sqltemp+")";
					//System.out.println("SQL1=====" + sql1);
					PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
					conn=PersistUtil.getConnection();
					//stmt=conn.createStatement();
					PreparedStatement pstmt1 = conn.prepareStatement(sql1);
					//pstmt1.setFetchSize(10);
					ResultSet rs1 = pstmt1.executeQuery();
					
					while(rs1.next())
					{
						String	bsoid = 
							rs1.getString(1)==null ? "" : rs1.getString(1).trim();
						String version = 
							rs1.getString(2)==null ? "" : rs1.getString(2).trim();
						
						if (version != null && version.length() > 0) {
							int index = version.indexOf(".");
							if(index>=0){
								version = version.substring(0,index);
							}
						}
						svmap.put(bsoid, version);
						if(version.equals(ver)){
							part=(QMPartIfc) ps.refreshInfo(bsoid);
						}
						
						
						//break;
					}
					//�ر�����
					pstmt1.close();
					pstmt1 = null;
					rs1.close();
					rs1 = null;
					//System.out.println("part111=====" + part);
					if(part!=null){
						ibabs="1";
						return part;
					}
					//System.out.println("ibabs111=====" + ibabs);
					//CCEnd SS82
					// CCEnd SS4
					for(Iterator iter=co.iterator();iter.hasNext();)
					{
						// System.out.println("           ");
						QMPartIfc part1=(QMPartIfc)iter.next();
						//System.out.println("������===="+part1.getPartNumber());
						String version=part1.getVersionID();
					//	 System.out.println("����汾===="+version);
						// //����в����汾
						// if(ver==""){
						// part = part1;
						// }else{
						// //�а汾��ͬ��
						// if(ver.equals(version)){
						// part = part1;
						// break;
						// }
						// }
						part=part1;
						
						// ����а汾��ͬ��������ѭ��������ȡ���°�
						if(ver!=""&&ver.equals(version))
						{
							flag1=false;
							break;
						}
					}
					// System.out.println("flag1===="+flag1);
					if(flag1)
					{
						// CCBegin SS4
						// Object[] vec = co.toArray();
						// part = (QMPartIfc)vec[0];
						// ��������汾û�У���ȡ���°棬����null����ʾ�û�������
						part=null;
						// CCEnd SS4
						// System.out.println("������111===="+part.getPartNumber());
						// System.out.println("����汾111===="+part.getVersionID());
					}
					// CCEnd SS1
					// CCBegin SS4
				}
				// CCEnd SS4
				// System.out.println("�汾1==="+part.getVersionID());//�汾
				// System.out.println("�汾2==="+part.getVersionLevel());//����
				// System.out.println("�汾3==="+part.getVersionValue());//�汾+����
			}
			else if(col.size()>1)
			{
				Iterator ite=col.iterator();
				while(ite.hasNext())
				{
					QMPartMasterIfc partmaster=(QMPartMasterIfc)ite.next();
					if(partmaster.getBsoID().indexOf("BSXUP")!=-1)
					{
						continue;
					}
					Collection co=vcService.allVersionsOf(partmaster);
					// System.out.println("co.size()2===="+co.size());
					// CCBegin SS4
					// part=(QMPartIfc)co.iterator().next();
					boolean flag1=true;
					if(ver.equals(""))
					{
						Object[] vec=co.toArray();
						part=(QMPartIfc)vec[vec.length-1];
					}
					else
					{
						for(Iterator iter=co.iterator();iter.hasNext();)
						{
							QMPartIfc part1=(QMPartIfc)iter.next();
							String version=part1.getVersionID();
							part=part1;
							// ����а汾��ͬ��������ѭ��������ȡ���°�
							if(ver!=""&&ver.equals(version))
							{
								flag1=false;
								break;
							}
						}
						if(flag1)
						{
							Object[] vec=co.toArray();
							part=(QMPartIfc)vec[0];
						}
					}
					// CCEnd SS4
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		// System.out.println("part===="+part);
		
		return part;
	}



	/**
	 * �����㲿�����°�
	 * 
	 * @param number
	 *            �㲿�����
	 * @return �����������㲿��
	 * @exception com.faw_qm.framework.exceptions.QMException
	 */
	public QMPartIfc findPartLasterByNumber(String number) throws QMException
	{
		// System.out.println("come in findPartLasterByNumber number=="+number);
		QMPartIfc part=null;
		try
		{
			StandardPartService spService=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			VersionControlService vcService=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
			Collection col=getAllPartMasters("",number.trim(),true);
			if(col==null||col.size()==0)
			{
				return null;
			}
			if(col.size()==1)
			{
				QMPartMasterIfc partmaster=(QMPartMasterIfc)col.iterator().next();
				Collection co=vcService.allVersionsOf(partmaster);
				part=(QMPartIfc)co.iterator().next();
			}
			else if(col.size()>1)
			{
				Iterator ite=col.iterator();
				while(ite.hasNext())
				{
					QMPartMasterIfc partmaster=(QMPartMasterIfc)ite.next();
					if(partmaster.getBsoID().indexOf("BSXUP")!=-1)
					{
						continue;
					}
					Collection co=vcService.allVersionsOf(partmaster);
					part=(QMPartIfc)co.iterator().next();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		// System.out.println("part===="+part);
		return part;
	}

	/**
	 * ɾ���ܳ� �Ҽ���Ӳ˵���ɾ���ܳɡ����˲˵������ǽ��ܳ�ɾ�����������Ӽ��㼶����һ���� ����
	 * ���¹�������ѡ���㲿�����Ӽ���������Ϊ����id���Ӽ��������������ǰ������ѡ���㲿���ж����������Ҫ�������ǣ�����һ�׽ṹ����ǰ������
	 * ɾ��������ɾ��ѡ���㲿���븸��������
	 * 
	 * @parm String parentID ѡ���㲿���ĸ���
	 * @parm String partID ѡ���㲿��
	 * @parm String carModelCode ������
	 * @parm String dwbs ����
	 */
	public String deleteSeparable(String parentID,String partID,String carModelCode,String dwbs) throws QMException
	{
		String str="";
		Connection conn=null;
		Statement stmt=null;
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			// System.out.println("parentID========"+parentID);
			// System.out.println("partID========"+partID);
			// System.out.println("carModelCode========"+carModelCode);
			// System.out.println("dwbs========"+dwbs);
			//CCBegin SS70
//			stmt.executeQuery("update GYBomStructure set parentPart='"+parentID+"' where effectCurrent='0' and dwbs='"
//				+dwbs+"' and parentPart='"+partID+"'");
			stmt.executeQuery("update GYBomStructure set bz1='red', parentPart='"+parentID+"' where effectCurrent='0' and dwbs='"
					+dwbs+"' and parentPart='"+partID+"'");
			stmt.executeQuery("update GYBomStructure set bz1='red' where childPart='"+parentID+"' and effectCurrent='0' and dwbs='"+dwbs+"'");
			//CCEnd SS70
			stmt.executeQuery("delete GYBomStructure where effectCurrent='0' and dwbs='"+dwbs+"' and parentPart='"
				+parentID+"' and childPart='"+partID+"'");

			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "[{ʧ��}]";
		}
		finally
		{
			try
			{
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return "[{�ɹ�}]";
	}

	/**
	 * ����BOM ָ�����͡�������BOM���ϡ� ��ȡ�Ӽ�id�͵�ǰ�û���������Чbom�еĸ����� ���ؼ��� ��� ���� ���� ����·�� װ��·��
	 * �������
	 */
	public Vector getExportBomList(String carModelCode) throws QMException
	{
		System.out.println("getExportBomList��ʼ����:"+carModelCode);
		Vector vec=new Vector();
		HashMap routemap=new HashMap();
		String dwbs=getCurrentDWBS();
		QMPartIfc part=findPartLasterByNumber(carModelCode);
		//CCBegin SS19
		//vec.add(new String[]{part.getPartNumber(),part.getPartName(),"","","","",part.getBsoID(),part.getMasterBsoID()});
		vec.add(new String[]{"0",part.getPartNumber(),part.getPartName(),"","","","",part.getBsoID(),part.getMasterBsoID()});
		//CCEnd SS19
		bianliBomList(vec,part.getBsoID(),carModelCode,dwbs,1,"",routemap);
		System.out.println("vec=="+vec.size());
		/*
		 * for(int i=0;i<vec.size();i++) { String[] str =
		 * (String[])vec.elementAt(i);
		 * System.out.println(str[0]+"&&"+str[1]+"&&"
		 * +str[2]+"&&"+str[3]+"&&"+str[4]+"&&"+str[5]+"&&"+str[6]+"&&"+str[7]);
		 * }
		 */
		return vec;
	}

	/**
	 * ����BOM ָ�����͡�������BOM���ϡ� ��ȡ�Ӽ�id�͵�ǰ�û���������Чbom�еĸ����� ���ؼ��� ��� ���� ���� ����·�� װ��·��
	 * �������
	 */
	private void bianliBomList(Vector vec,String id,String carModelCode,String dwbs,int level,String pzz,
		HashMap routemap) throws QMException
	{
		PersistService ps=(PersistService)EJBServiceHelper.getPersistService();

		String effect=getEffect(id,carModelCode,dwbs);
		if(effect.equals("1"))
		{
			effect="0";
		}
		else if(effect.equals("2")||effect.equals("3"))
		{
			effect="1";
		}
		// ��ȡ�Ӽ�id������
		Vector subvec=findChildPart(id,carModelCode,dwbs,effect);
		QMPartIfc parentpart=(QMPartIfc)ps.refreshInfo(id);

		// ��ȡ�Ӽ�·��
		String childPart="";
		QMPartIfc part=null;
		String insql="";
		for(int i=0;i<subvec.size();i++)
		{
			String[] str=subvec.elementAt(i).toString().split(",");
			childPart=str[0];
			part=(QMPartIfc)ps.refreshInfo(childPart);
			/*if(level==2)
			{
				System.out.println("2��"+part.getPartNumber());
			}*/
			if(part==null)
			{
				System.out.println("δ�ҵ��Ӽ�id��"+childPart);
				continue;
			}
			if(routemap.containsKey(part.getMasterBsoID()))
			{
				continue;
			}
			if(insql.equals(""))
			{
				if(dwbs.equals("W34"))
				{
					insql="'"+part.getBsoID()+"'";
				}
				else
				{
					insql="'"+part.getMasterBsoID()+"'";
				}
			}
			else
			{
				if(dwbs.equals("W34"))
				{
					insql=insql+",'"+part.getBsoID()+"'";
				}
				else
				{
					insql=insql+",'"+part.getMasterBsoID()+"'";
				}
			}
		}
		if(!insql.equals(""))
		{
			if(dwbs.equals("W34"))
			{
				routemap=getSecondRoute(insql,routemap);
			}
			else
			{
				routemap=getRoute(insql,routemap);
			}
		}
		// �ݹ��ȡ�Ӽ�
		String zhizao="";
		String zhuangpei="";

		com.faw_qm.cderp.util.PartHelper helper=new com.faw_qm.cderp.util.PartHelper();

		for(int i=0;i<subvec.size();i++)
		{
			String[] str=subvec.elementAt(i).toString().split(",");
			childPart=str[0];
			part=(QMPartIfc)ps.refreshInfo(childPart);

			if(dwbs.equals("W34"))
			{
				if(routemap.get(part.getBsoID())!=null)
				{
					String[] routestr=(String[])(routemap.get(part.getBsoID()));
					zhizao=routestr[0];
					zhuangpei=routestr[1];
				}
			}
			else
			{
				if(routemap.get(part.getMasterBsoID())!=null)
				{
					String[] routestr=(String[])(routemap.get(part.getMasterBsoID()));
					zhizao=routestr[0];
					zhuangpei=routestr[1];
				}
			}
			if(zhuangpei.equals(""))
			{
				zhuangpei=pzz;
			}
			// Integer.toString(level),
			
			String pnum=helper.getMaterialNumber(part,part.getVersionID());
			System.out.println("pnum11111111111==="+pnum);
			//CCBegin SS19
			//vec.add(new String[]{pnum,part.getPartName(),str[1],zhizao,zhuangpei,parentpart.getPartNumber()});
			vec.add(new String[]{Integer.toString(level),pnum,part.getPartName(),str[1],zhizao,zhuangpei,parentpart.getPartNumber()});
			//CCEnd SS19
			bianliBomList(vec,childPart,carModelCode,dwbs,level+1,zhizao,routemap);

			zhizao="";
			zhuangpei="";
		}
	}

	/**
	 * ��ȡ�㲿�������ڸù����ڵ����и��� ��ȡ�Ӽ�id�͵�ǰ�û���������Чbom�еĸ����� ���ؼ��� �㲿��bsoid ��� ���� ����
	 */
	public Vector getParentFromDwbs(String id) throws QMException
	{
		Vector vec=new Vector();
		String dwbs=getCurrentDWBS();

		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();
			rs=stmt
				.executeQuery("select parentPart,quantity,bz2 from GYBomStructure where effectCurrent='1' and childPart='"
					+id+"' and dwbs='"+dwbs+"'");

			while(rs.next())
			{
				String bsoID=rs.getString(1);
				QMPartIfc part=(QMPartIfc)ps.refreshInfo(bsoID);
				String[] str=new String[]{bsoID,part.getPartNumber(),part.getPartName(),rs.getString(2),rs.getString(3)};
				vec.add(str);
			}
			// �ر�ResultSet
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return vec;
	}

	/**
	 * ��ȡ�㲿�������ڸù����ڵ�����δ��Ч�ĸ��� ��ȡ�Ӽ�id�͵�ǰ�û���������Чbom�еĸ����� ���ؼ��� �㲿��bsoid ��� ���� ����
	 */
	public Vector getParentPart(String id) throws QMException
	{
		Vector vec=new Vector();
		String dwbs=getCurrentDWBS();

		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			conn=PersistUtil.getConnection();
			stmt=conn.createStatement();

			rs=stmt
				.executeQuery("select parentPart,quantity,bz2 from GYBomStructure where effectCurrent='0' and childPart='"
					+id+"' and dwbs='"+dwbs+"'");

			while(rs.next())
			{
				String bsoID=rs.getString(1);
				QMPartIfc part=(QMPartIfc)ps.refreshInfo(bsoID);

				String[] str=new String[]{bsoID,part.getPartNumber(),part.getPartName(),rs.getString(2),
					part.getVersionValue(),rs.getString(3)};

				vec.add(str);
			}
			// �ر�ResultSet
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(rs!=null)
				{
					rs.close();
				}
				if(stmt!=null)
				{
					stmt.close();
				}
				if(conn!=null)
				{
					conn.close();
				}
			}
			catch(SQLException ex1)
			{
				ex1.printStackTrace();
			}
		}
		return vec;
	}

	// CCBegin SS7
//CCBegin SS37
	/**
	 * ��ȡԴ�㲿���ṹ��������
	 */
	private Collection getBomLinks(String gyID,Collection mtree,QMPartIfc part,String carModelCode,String dwbs,
		Vector linkvec,String type, ArrayList arr, ArrayList otherParts) throws QMException//SS20
	{
		try
		{
			if(part!=null)
			{
			    //3103G01-367
//			    if(part.getPartNumber().contains("1303G01"))
//			    {
//			        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa��������������������");
//			    }
//			    System.out.println("�㲿�����-----------------"+part.getPartNumber());
				System.out.println("���ݿ����Ƿ��нṹ==============="+getEffect(part.getBsoID(),carModelCode,dwbs));
				// ���part�Ƿ���ڵ�ǰ�����Ľṹ����������Ҫ����ṹ��
				if(!getEffect(part.getBsoID(),carModelCode,dwbs).equals("0"))
				{
//				    System.out.println("�������㲿�����-----------------"+part.getPartNumber());
					 //CCBegin SS20
				    if(arr == null)
				    {
				        arr = new ArrayList();
				    }
				    arr.add(part.getBsoID());
				    //CCEnd SS20
				    if(otherParts == null)
                    {
				        otherParts = new ArrayList();
                    }
				    otherParts.add(part);
					return mtree;
				}

				StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
				VersionControlService vcservice=(VersionControlService)EJBServiceHelper
					.getService("VersionControlService");
				JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
				PersistService ps=(PersistService)EJBServiceHelper.getPersistService();

				PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
				Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);
//				if(part.getPartNumber().contains("1303G01"))
//                {
                    System.out.println("a��С======"+linkcoll.size());
//                }
				if(linkcoll!=null&&linkcoll.size()>0)
				{
					Iterator iter=linkcoll.iterator();
					QMPartMasterIfc partMaster=null;
					Vector tempResult=new Vector();
					QMPartIfc subpart=null;
					Vector tempvec=new Vector();
					ArrayList array = new ArrayList();//SS20
					while (iter.hasNext()) {
						Object obj[] = (Object[]) iter.next();
						PartUsageLinkIfc link = (PartUsageLinkIfc) obj[0];
						// CCBegin SS14
						// CCBegin SS12
						// System.out.println("obj[1]=================="+obj[1]);
						// if(obj[1] instanceof QMPartIfc)
						// {
						// continue;
						// }
						// CCEnd SS12
						// CCEnd SS14
						// CCBegin SS64
						if (obj[1] instanceof QMPartIfc) {
							// CCEnd SS64
							subpart = (QMPartIfc) obj[1];
							if (linkvec.contains(part.getBsoID()
									+ subpart.getBsoID())) {
								if (tempvec.contains(part.getBsoID()
										+ subpart.getBsoID())) {
									System.out.println("��ǰ�ṹ���ظ��������ظ���ӣ�"
											+ part.getPartNumber() + "=="
											+ subpart.getPartNumber());
								} else {
									continue;
								}
							} else {
								linkvec.add(part.getBsoID()
										+ subpart.getBsoID());
							}
							tempvec.add(part.getBsoID() + subpart.getBsoID());
							String[] mt = new String[10];
							mt[0] = part.getBsoID();
							mt[1] = subpart.getBsoID();
							mt[2] = String.valueOf(link.getQuantity());
							mt[3] = "";
							mt[4] = dwbs;
							mt[5] = "0";
							mt[6] = "";
							mt[7] = "";
							mt[8] = link.getDefaultUnit().getDisplay();
							mt[9] = subpart.getPartNumber();
							// System.out.println("subpart.getPartNumber()   ==== "
							// + subpart.getPartNumber());

							// ���ݹ�����㲿���ṹ���й���
							// ������˹���
							// subpart=glpart(dwbs,subpart);
							if (subpart != null) {
								// // ��������1000410������˹���
								// if(type.equals("1000410"))
								// {
								// subpart=glfdjpart0(dwbs,part,subpart);
								// }
								// // ����������������˹���
								// else if(type.equals("fdj"))
								// {
								// subpart=glfdjpart(dwbs,part,subpart);
								// }
								// // ���ܷ�����˹���
								// else if(type.equals("cj"))
								// {
								// subpart=glcjpart(dwbs,part,subpart);
								// }
								// // ��ʻ�ҷ�����˹���
								// else if(type.equals("jss"))
								// {
								// subpart=gljsspart(dwbs,part,subpart);
								// }
								// // ����������˹���
								// else if(type.equals("zc"))
								// {
								// subpart=glzcpart(dwbs,part,subpart);
								// }
								// // �����򷢶��������ܡ���ʻ�ҡ�������ֱ�ӹ��˵�
								// else
								// {
								// subpart=null;
								// }
								// ����·�߹���
								//CCBegin SS73 ����·�߹���
//								if (!gylxFiltration(dwbs, part, subpart)) {
//									if (otherParts == null) {
//										otherParts = new ArrayList();
//									}
//									otherParts.add(subpart);
//									if (subpart.getPartNumber().contains(
//											"3500G00")) {
//										System.out
//												.println("3500G00-----------------------------------------------");
//									}
//									// mtree.add(mt);
//									continue;
//								}
								//CCEnd SS73
							}
							mtree.add(mt);
							if (subpart != null) {
								getBomLinks(gyID, mtree, subpart, carModelCode,
										dwbs, linkvec, type, array, otherParts);// SS20
							}
						}
						// CCBegin SS64
					}
					// CCEnd SS64
					tempvec=null;
				}
			}
			return mtree;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new QMException(e);
		}
	}
	
		
	//Begin SS41
	/**
	 * ���ݹ���·�߹������
	 * @param dwbs
	 * @param parent
	 * @param sub
	 * @return
	 */
	private boolean gylxFiltration(String dwbs,QMPartIfc parent,QMPartIfc sub) throws QMException
	{
	    //��1��    �����߼��ܳɣ�·������-�ã�������Ӽ�װ��·��Ϊ���ܡ���չ����
	    //��2������·�߰������ܡ���װ��·��Ҳ�������ܡ�ʱ����ȡ�Ӽ�װ��·�ߺ����ܡ��ļ�
	    //��1���ͣ�2������Ҫ�����Ӽ��ģ������Ĳ������Ӽ������ϣ�1���ͣ�2������true
	    boolean ret = false;
	    try
        {
	        
	        HashMap partentmap=new HashMap();
            HashMap submap=new HashMap();
            String[] parentroutestr=new String[2];
            String[] subroutestr=new String[2];
            if(dwbs.equalsIgnoreCase("W34"))// �ɶ�ȡ����·��
            {
                submap=getSecondRoute("'"+sub.getBsoID()+"'",submap);
                if(submap.get(sub.getBsoID())!=null)
                {
                    subroutestr=(String[])(submap.get(sub.getBsoID()));
                }
                partentmap=getSecondRoute("'"+parent.getBsoID()+"'",partentmap);
                if(partentmap.get(parent.getBsoID())!=null)
                {
                    parentroutestr=(String[])(partentmap.get(parent.getBsoID()));
                }
            }
            else
            // ���·��
            {
                submap=getRoute("'"+sub.getMasterBsoID()+"'",submap);
                if(submap.get(sub.getMasterBsoID())!=null)
                {
                    subroutestr=(String[])(submap.get(sub.getMasterBsoID()));
                }
                partentmap=getRoute("'"+parent.getMasterBsoID()+"'",partentmap);
                if(partentmap.get(parent.getMasterBsoID())!=null)
                {
                    parentroutestr=(String[])(partentmap.get(parent.getMasterBsoID()));
                }
            }
            String pzz=parentroutestr[0];
            String pzp=parentroutestr[1];
            String szz=subroutestr[0];
            String szp=subroutestr[1];
            if(pzz==null)
            {
                pzz="";
            }
            if(pzp==null)
            {
                pzp="";
            }
            if(szz==null)
            {
                szz="";
            }
            if(szp==null)
            {
                szp="";
            }
//            if(sub.getPartNumber().contains("1161G02"))
//            {
//                System.out.println("��Ÿ�===== " + parent.getPartNumber());
//                System.out.println("�����===== " + sub.getPartNumber());
//                System.out.println("�߼��ܳɸ�   =========== " + isLogical(parent,pzz,pzp));
//                System.out.println("�߼��ܳ���   =========== " + isLogical(sub,szz,szp));
//                System.out.println("szz   =========== " + szz);
//                System.out.println("szp   =========== " + szp);
//                System.out.println("pzp   =========== " + pzp);
//                System.out.println("pzz   =========== " + pzz);
//            }
            
//            if(sub.getPartNumber().contains("8105G01-13A"))
//            {
//                System.out.println("8105G01-13A��Ÿ�===== " + parent.getPartNumber());
//                System.out.println("8105G01-13A�����===== " + sub.getPartNumber());
//                System.out.println("8105G01-13A�߼��ܳɸ�   =========== " + isLogical(parent,pzz,pzp));
//                System.out.println("8105G01-13A�߼��ܳ���   =========== " + isLogical(sub,szz,szp));
//                System.out.println("8105G01-13Aszz   =========== " + szz);
//                System.out.println("8105G01-13Aszp   =========== " + szp);
//                System.out.println("8105G01-13Apzp   =========== " + pzp);
//                System.out.println("8105G01-13Apzz   =========== " + pzz);
//            }
//            �����ڵĹ���BOM���˺��С===5341
//            ���===== QMPart_136655116
//            �߼��ܳ�   =========== true
//            szz   =========== �ܡ���
//            szp   =========== ��
//            pzp   =========== ��
//            pzz   =========== �ܡ���
            //��1��    �����߼��ܳɣ�·������-�ã�������Ӽ�װ��·��Ϊ���ܡ���չ����
//            if(isLogical(parent,pzz,pzp)&&(szp.contains("��") || isLogical(sub,szz,szp)))
            if(isLogical(parent,pzz,pzp))
            {
                if(isLogical(sub,szz,szp))
                {
//                    if(sub.getPartNumber().contains("1161G02"))
//                    {
//                        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaa=="+sub.getPartNumber());
//                    }
//                    if(sub.getPartNumber().contains("1108G01"))
//                    {
//                        System.out.println("888aaaaaaaaaaaaaaaaaaaaaaaa=="+sub.getPartNumber());
//                    }
                    ret = true;
                }
                else if(szp.contains("��"))
                {
//                    if(sub.getPartNumber().contains("1161G02"))
//                    {
//                        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbb=="+sub.getPartNumber());
//                    }
//                    if(sub.getPartNumber().contains("1108G01"))
//                    {
//                        System.out.println("888bbbbbbbbbbbbbbbbbbbbbbbbbb=="+sub.getPartNumber());
//                    }
                    ret = true;
                }
            }
            //��2������·�߰������ܡ���װ��·��Ҳ�������ܡ�ʱ����ȡ�Ӽ�װ��·�ߺ����ܡ��ļ�
            else if(pzz.contains("��")&&pzp.contains("��"))
            {
                if(isLogical(sub,szz,szp))
                {
//                    if(sub.getPartNumber().contains("1161G02"))
//                    {
//                        System.out.println("cccccccccccccccccccccccccccc=="+sub.getPartNumber());
//                    }
//                    if(sub.getPartNumber().contains("1108G01"))
//                    {
//                        System.out.println("888cccccccccccccccccccccccccccc=="+sub.getPartNumber());
//                    }
                    ret = true;
                }
                else if(szp.contains("��"))
                {
//                    if(sub.getPartNumber().contains("1161G02"))
//                    {
//                        System.out.println("dddddddddddddddddddddddddddddd=="+sub.getPartNumber());
//                    }
//                    if(sub.getPartNumber().contains("1108G01"))
//                    {
//                        System.out.println("888dddddddddddddddddddddddddddddd=="+sub.getPartNumber());
//                    }
                    ret = true;
                }
            }
            else
            {
                ret = false;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new QMException(ex);
        }
	    return ret;
	}
	//End SS41
	
//	/**
//     * �ж��Ƿ����߼��ܳ�
//     * @param dwbs
//     * @param parent
//     * @param sub
//     * @return
//     */
//    private boolean isLjzc(String dwbs,QMPartIfc parent) throws QMException
//    {
//        boolean ret = true;
//        
//        return ret;
//    }
    
    /**
     * �Ƿ����߼��ܳ�
     * ������routeAsString ����·�ߣ�assembStrװ��·��
     * ���ܳ��жϹ��� ��1����ŵ���λ�ǡ�G������1������·�ߺ��á���װ��·�ߣ�2������·�߲����ã���װ��·��
     * @return boolean
     * @throws QMException
     */
     public boolean isLogical(QMPartIfc part,String routeAsString ,String assembStr)throws QMException{
        boolean result=false;
        //Begin SS41
//        System.out.println("part===="+part.getPartNumber());
//        System.out.println("����·��routeAsString===="+routeAsString);
//        System.out.println("װ��·��assembStr===="+assembStr);
//        System.out.println("part.getPartNumber().substring(4, 5)===="+part.getPartNumber().substring(4, 5));
        //��5λΪG����װ��·��Ϊ�ղ�������·�ߺ����á�
        if(part.getPartNumber().length()>5)
        if (part.getPartNumber().substring(4, 5).equals("G"))
        {//CCBegin SS45
        	//CCBegin SS47
//            if(routeAsString.contains("��--��")&&(assembStr.equals("")||assembStr.equals("��"))){
        	if(routeAsString.contains("��")&&routeAsString.contains("��")&&(assembStr.equals("")||assembStr.equals("��"))){
        		//CCEnd SS47
            	//CCEnd SS45
                result=true;
//                System.out.println("���");
            }
//            if(!routeAsString.contains("��")&&!assembStr.equals("")&&!assembStr.equals("��")){
//                result=true;
////                System.out.println("ʵ��");
//            }
        }
//        isLogical(parent,pzz,pzp)
//        �߼��ܳ�   =========== false
//        szz   =========== �ܡ���
//        szp   =========== ��
//        pzp   ===========
//        pzz   ===========
				//End SS41
        return result;
    }
//CCEnd SS37

	/**
	 * ������� Э-Э��
	 * 
	 * @param sub
	 * @return
	 * @throws QMException
	 */
	private QMPartIfc glpart(String dwbs,QMPartIfc sub) throws QMException
	{
		try
		{
			HashMap submap=new HashMap();
			String[] subroutestr=new String[2];
			if(dwbs.equals("W34"))// �ɶ�ȡ����·��
			{
				submap=getSecondRoute("'"+sub.getBsoID()+"'",submap);
				if(submap.get(sub.getBsoID())!=null)
				{
					subroutestr=(String[])(submap.get(sub.getBsoID()));
				}
			}
			else
			{
				submap=getRoute("'"+sub.getMasterBsoID()+"'",submap);
				if(submap.get(sub.getMasterBsoID())!=null)
				{
					subroutestr=(String[])(submap.get(sub.getMasterBsoID()));
				}
			}
			String szz=subroutestr[0];
			String szp=subroutestr[1];
			if(szz==null)
			{
				szz="";
			}
			if(szp==null)
			{
				szp="";
			}
			if(szz.equals("Э")&&szp.equals("Э"))
			{
				return null;
			}
			else
			{
				return sub;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}

	/**
	 * �Է������ܳ���1000410������й��ˣ�1000410������Ĺ��˹���
	 * 
	 * @param dwbs
	 * @param parent
	 * @param sub
	 * @return
	 * @throws QMException
	 */
	private QMPartIfc glfdjpart0(String dwbs,QMPartIfc parent,QMPartIfc sub) throws QMException
	{
		try
		{
			HashMap partentmap=new HashMap();
			HashMap submap=new HashMap();
			String[] parentroutestr=new String[2];
			String[] subroutestr=new String[2];
			if(dwbs.equals("W34"))// �ɶ�ȡ����·��
			{
				submap=getSecondRoute("'"+sub.getBsoID()+"'",submap);
				if(submap.get(sub.getBsoID())!=null)
				{
					subroutestr=(String[])(submap.get(sub.getBsoID()));
				}
				partentmap=getSecondRoute("'"+parent.getBsoID()+"'",partentmap);
				if(partentmap.get(parent.getBsoID())!=null)
				{
					parentroutestr=(String[])(partentmap.get(parent.getBsoID()));
				}
			}
			else
			// ���·��
			{
				submap=getRoute("'"+sub.getMasterBsoID()+"'",submap);
				if(submap.get(sub.getMasterBsoID())!=null)
				{
					subroutestr=(String[])(submap.get(sub.getMasterBsoID()));
				}
				partentmap=getRoute("'"+parent.getMasterBsoID()+"'",partentmap);
				if(partentmap.get(parent.getMasterBsoID())!=null)
				{
					parentroutestr=(String[])(partentmap.get(parent.getMasterBsoID()));
				}
			}
			String pzz=parentroutestr[0];
			String pzp=parentroutestr[1];
			String szz=subroutestr[0];
			String szp=subroutestr[1];
			if(pzz==null)
			{
				pzz="";
			}
			if(pzp==null)
			{
				pzp="";
			}
			if(szz==null)
			{
				szz="";
			}
			if(szp==null)
			{
				szp="";
			}
			if(pzz.indexOf("��")!=-1&&szp.indexOf("��")!=-1)
			{
				return sub;
			}
			else if(pzz.indexOf("��-��")!=-1&&szp.equals("��"))
			{
				return null;
			}
			else if(szp.indexOf("��")!=-1)
			{
				return null;
			}
			else
			{
				return sub;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * �Է������ܳɽ��й���
	 * 
	 * @param dwbs
	 * @param parent
	 * @param sub
	 * @return
	 * @throws QMException
	 */
	private QMPartIfc glfdjpart(String dwbs,QMPartIfc parent,QMPartIfc sub) throws QMException
	{
		try
		{
			HashMap partentmap=new HashMap();
			HashMap submap=new HashMap();
			String[] parentroutestr=new String[2];
			String[] subroutestr=new String[2];
			if(dwbs.equals("W34"))// �ɶ�ȡ����·��
			{
				submap=getSecondRoute("'"+sub.getBsoID()+"'",submap);
				if(submap.get(sub.getBsoID())!=null)
				{
					subroutestr=(String[])(submap.get(sub.getBsoID()));
				}
				partentmap=getSecondRoute("'"+parent.getBsoID()+"'",partentmap);
				if(partentmap.get(parent.getBsoID())!=null)
				{
					parentroutestr=(String[])(partentmap.get(parent.getBsoID()));
				}
			}
			else
			// ���·��
			{
				submap=getRoute("'"+sub.getMasterBsoID()+"'",submap);
				if(submap.get(sub.getMasterBsoID())!=null)
				{
					subroutestr=(String[])(submap.get(sub.getMasterBsoID()));
				}
				partentmap=getRoute("'"+parent.getMasterBsoID()+"'",partentmap);
				if(partentmap.get(parent.getMasterBsoID())!=null)
				{
					parentroutestr=(String[])(partentmap.get(parent.getMasterBsoID()));
				}
			}
			String pzz=parentroutestr[0];
			String pzp=parentroutestr[1];
			String szz=subroutestr[0];
			String szp=subroutestr[1];
			if(pzz==null)
			{
				pzz="";
			}
			if(pzp==null)
			{
				pzp="";
			}
			if(szz==null)
			{
				szz="";
			}
			if(szp==null)
			{
				szp="";
			}
			if(pzz.indexOf("��-��")!=-1&&szp.equals("��"))
			{
				return null;
			}
			else if(szp.indexOf("��")!=-1)
			{
				return null;
			}
			else
			{
				return sub;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * �Գ����ܳɽ��й���
	 * 
	 * @param dwbs
	 * @param parent
	 * @param sub
	 * @return
	 * @throws QMException
	 */
	private QMPartIfc glcjpart(String dwbs,QMPartIfc parent,QMPartIfc sub) throws QMException
	{
		try
		{
			return sub;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * �Լ�ʻ���ܳɽ��й���
	 * 
	 * @param dwbs
	 * @param parent
	 * @param sub
	 * @return
	 * @throws QMException
	 */
	private QMPartIfc gljsspart(String dwbs,QMPartIfc parent,QMPartIfc sub) throws QMException
	{
		try
		{
			return sub;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ֱ�ӹ������ļ����й���
	 * 
	 * @param dwbs
	 * @param parent
	 * @param sub
	 * @return
	 * @throws QMException
	 */
	private QMPartIfc glzcpart(String dwbs,QMPartIfc parent,QMPartIfc sub) throws QMException
	{
		try
		{
			HashMap partentmap=new HashMap();
			HashMap submap=new HashMap();
			String[] parentroutestr=new String[2];
			String[] subroutestr=new String[2];
			if(dwbs.equals("W34"))// �ɶ�ȡ����·��
			{
				submap=getSecondRoute("'"+sub.getBsoID()+"'",submap);
				if(submap.get(sub.getBsoID())!=null)
				{
					subroutestr=(String[])(submap.get(sub.getBsoID()));
				}
				partentmap=getSecondRoute("'"+parent.getBsoID()+"'",partentmap);
				if(partentmap.get(parent.getBsoID())!=null)
				{
					parentroutestr=(String[])(partentmap.get(parent.getBsoID()));
				}
			}
			else
			// ���·��
			{
				submap=getRoute("'"+sub.getMasterBsoID()+"'",submap);
				if(submap.get(sub.getMasterBsoID())!=null)
				{
					subroutestr=(String[])(submap.get(sub.getMasterBsoID()));
				}
				partentmap=getRoute("'"+parent.getMasterBsoID()+"'",partentmap);
				if(partentmap.get(parent.getMasterBsoID())!=null)
				{
					parentroutestr=(String[])(partentmap.get(parent.getMasterBsoID()));
				}
			}
			String pzz=parentroutestr[0];
			String pzp=parentroutestr[1];
			String szz=subroutestr[0];
			String szp=subroutestr[1];
			if(sub.getPartNumber().contains("3513010")){
//				System.out.println("pzz===="+pzz);
//				System.out.println("pzp===="+pzp);
//				System.out.println("szz===="+szz);
//				System.out.println("szp===="+szp);
			}
			
			if(pzz==null)
			{
				pzz="";
			}
			if(pzp==null)
			{
				pzp="";
			}
			if(szz==null)
			{
				szz="";
			}
			if(szp==null)
			{
				szp="";
			}
			if(pzz.indexOf("��-��")!=-1&&szp.equals("��"))
			{
				if(sub.getPartNumber().contains("3513010")){
//				System.out.println("1111111111====");
				}
				return null;
			}
			//CCBegin SS26
			//���˹���
			//1.	���˹���Ϊ�����������߼��ܳ�·��Ϊ����-�á����Ӽ�װ��·��Ϊ���ܡ����Ҹ��Ӽ��¼�������PBOM��
			//2.	����·�߲������ܡ���װ��·�ߺ����ܡ�ʱ������ȡ�Ӽ�����ȡͬһ�㼶װ��·�ߺ����ܡ��ļ���
			//3.	����·�߰������ܡ���װ��·��Ҳ�������ܡ�ʱ����ȡ�Ӽ�����ȡͬһ�㼶װ��·�ߺ����ܡ��ļ���
//			CCBegin SS27
			//else if(pzz.indexOf("��")!=1&&szp.indexOf("��")!=-1)
//			CCEnd SS26
			else if(pzz.indexOf("��")!=1&&pzp.indexOf("��")!=-1)
			{//CCEnd SS27
				
				if(sub.getPartNumber().contains("3513010")){
//				System.out.println("222222222====");
				}
				return null;
			}
			else
			{
				if(sub.getPartNumber().contains("3513010")){
//				System.out.println("333333333====");
				}
				return sub;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	//CCBegin SS20
	/**
	 * ���ݹ���Թ��պϼ����в��
	 * 
	 * @param gyID
	 * @param mtree
	 * @return
	 * @throws QMException
	 */
	private Collection chaifengybom(QMPartIfc gypart, Collection mtree,
			String dwbs, QMPartIfc sjpart, ArrayList arraylist)
			throws QMException
    {
		
        String gyID = gypart.getBsoID();
        //��๤������������ ��Ƴ�������� P66  �� J6P P63�� J6M P62�� J6L.
//System.out.println("gyID============="+gyID);
//System.out.println("getPartName============="+gypart.getPartName());
        String partNumber1000940 = "";
        String partNumber1000410 = "";
        String id3900G01 = ""; //SS25
        String id3100G00 = "";//SS25
        PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
        
        
//        System.out.println("mtree.size()======"+mtree.size());
//		System.out.println("arraylist.size()======"+arraylist.size());
//		for(Iterator iter = mtree.iterator();iter.hasNext();){
//			String[] mt=(String[])iter.next();
//            String subid=mt[1];
//            String parentID = mt[0];
//            QMPartIfc spart=(QMPartIfc)ps.refreshInfo(subid,false);
//            QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(parentID,false);
//            if(parentPart.getPartNumber().contains("1104G01")){
//            	System.out.println("111��������������������������������");
//            	System.out.println("�������===="+parentPart.getPartNumber());
//            	System.out.println("�Ӽ����===="+spart.getPartNumber());
//            }
//            if(spart.getPartNumber().contains("1104G01")){
//            	System.out.println("222��������������������������������");
//            	System.out.println("�������===="+parentPart.getPartNumber());
//            	System.out.println("�Ӽ����===="+spart.getPartNumber());
//            }
//		}
//		for(Iterator iter = arraylist.iterator();iter.hasNext();){
//			String[] mt=(String[])iter.next();
//            String parentID = mt[0];
//            String subid=mt[1];
//            QMPartIfc spart=(QMPartIfc)ps.refreshInfo(subid,false);
//            QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(parentID,false);
//            if(parentPart.getPartNumber().contains("1104G01")){
//            	System.out.println("222��������������������������������");
//            	System.out.println("�������===="+parentPart.getPartNumber());
//            	System.out.println("�Ӽ����===="+spart.getPartNumber());
//            }
//		}
        
        
        ArrayList arr = new ArrayList(mtree);
        HashMap map = new HashMap();//���Ҫ��ּ��Ͷ������ļ�¼
        for(int i=0; i<arr.size(); i++)
        {
            
            String strs[] = (String[])arr.get(i);
            //CCBegin SS24
            //���ʱ����JTA��ͷ�ľ�ȥ�����������ĳ�ʼ��
            if(strs[9].startsWith("JTA"))
            {
//                System.out.println("--------���ʱ����JTA��ͷ�ľ�ȥ�����������ĳ�ʼ��-------");
                mtree.remove(strs);
                continue;
            }
            //CCEnd SS24
            if(strs[9].contains("1000940"))
            {
                partNumber1000940 = strs[1];
            }
            if(strs[9].contains("1000410"))
            {
                partNumber1000410 = strs[1];
            }
            
            if(strs[9].contains("1104G01") || strs[9].contains("1119G01") || 
                    strs[9].contains("1203G01") || strs[9].contains("8116G01") || 
                    strs[9].contains("1303G01") || strs[9].contains("1311G02") || 
                    strs[9].contains("3406G01") || strs[9].contains("3406G02") || 
                    strs[9].contains("1311G01") || strs[9].contains("3406G01") || 
                    strs[9].contains("3506G00") || strs[9].contains("3506G00") ||
                    strs[9].contains("8116G01") || strs[9].contains("3509G01"))
            {
                map.put(strs[9], strs);
            }
            //CCBegin SS25
            if(strs[9].contains("3900G01"))
            {
                id3900G01 = strs[1];
//                System.out.println("id3900G01  ========  " + id3900G01);
            }
            if(strs[9].contains("3100G00"))
            {
                id3100G00 = strs[1];
//                System.out.println("id3100G00  ========  " + id3100G00);
            }
            //CCEnd SS25
        }
//        System.out.println("map =============== " + map);
        Collection col = new ArrayList();
        //J6P���Ͳ��
        if(sjpart.getPartNumber().contains("P66") || sjpart.getPartNumber().contains("p66") || 
                sjpart.getPartNumber().contains("P67") || sjpart.getPartNumber().contains("p67"))//SS22���������¾ɹ���Աȡ�Ϊ�û����������
        {
//            System.out.println("P66------------------P66=="+mtree);
            col = chaifengybomJ6P(gypart, mtree, partNumber1000940, partNumber1000410, map, 
                    dwbs,arraylist,id3900G01,id3100G00);//SS25
//            System.out.println("col========="+col.size());
        }
        //JMP���Ͳ��
        else if(sjpart.getPartNumber().contains("P63") || sjpart.getPartNumber().contains("p63") || 
                sjpart.getPartNumber().contains("P64") || sjpart.getPartNumber().contains("p64"))//SS22���������¾ɹ���Աȡ�Ϊ�û����������
        {
//            System.out.println("P63------------------P63=="+mtree);
            col = chaifengybomJ6M(gypart, mtree, partNumber1000940, partNumber1000410, map, 
                    dwbs,arraylist,id3900G01,id3100G00);//SS25
        }
        //J6L���Ͳ��
        else if(sjpart.getPartNumber().contains("P62") || sjpart.getPartNumber().contains("p62") || 
                sjpart.getPartNumber().contains("P61") || sjpart.getPartNumber().contains("p61"))//SS22���������¾ɹ���Աȡ�Ϊ�û����������
        {
//            System.out.println("P62------------------P62=="+mtree);
            col = chaifengybomJ6L(gypart, mtree, partNumber1000940, partNumber1000410, map, 
                    dwbs,arraylist,id3900G01,id3100G00);//SS25
        }
        //�������Ͳ��
        else
        {
//            for(Iterator iter=mtree.iterator();iter.hasNext();)
//            {
//                String[] mt=(String[])iter.next();
//                String subid=mt[1];
//                String parentID = mt[0];
//                QMPartIfc spart=(QMPartIfc)ps.refreshInfo(subid,false);
//                QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(parentID,false);
//                String partNumber=spart.getPartNumber();
//                
//                if(parentPart.getPartNumber().contains("1000940") 
//                        && spart.getPartNumber().contains("8116G01"))
//                {
//                    System.out.println("����222============="+parentPart.getPartNumber());
//                    System.out.println("�Ӽ�222============="+spart.getPartNumber());
//                }
//            }
            
//            System.out.println("other------------------other=="+mtree);
            col = chaifengybomOther(gypart, mtree, partNumber1000940, partNumber1000410, map, 
                    dwbs,arraylist,id3900G01,id3100G00);//SS25
        }
//        ArrayList array = new ArrayList(col);
//        for(int i=0; i<array.size(); i++)
//        {
//            String strs[] = (String[])array.get(i);
//            System.out.println("strs[0]============="+strs[0]);
//            System.out.println("strs[1]============="+strs[1]);
//            System.out.println("strs[9]============="+strs[9]);
//            System.out.println("\n");
//        }
        return col;
    }
    //CCEnd SS20

	/**
	 * ���ݹ���Թ��պϼ����в��
	 * 
	 * @param gyID
	 * @param mtree
	 * @return
	 * @throws QMException
	 */
	private Collection chaifengybom(String gyID,Collection mtree) throws QMException
	{
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			Collection ntree=new ArrayList();
			HashMap map=new HashMap();
			HashMap nmap=new HashMap();
			for(Iterator iter=mtree.iterator();iter.hasNext();)
			{
				String[] mt=(String[])iter.next();
				//CCBegin SS24
				//���ʱ����JTA��ͷ�ľ�ȥ�����������ĳ�ʼ��
	            if(mt[9].startsWith("JTA"))
	            {
	                mtree.remove(mt);
	                continue;
	            }
	            //CCEnd SS24
				String subid=mt[1];
				QMPartIfc spart=(QMPartIfc)ps.refreshInfo(subid,false);
				String snum=spart.getPartNumber();
				if(snum.length()>7&&snum.substring(0,7).equals("5000990"))
				{
					map.put("5000990",spart.getBsoID());
				}
				if(snum.length()>7&&snum.substring(0,7).equals("3900G01"))
				{
					map.put("3900G01",spart.getBsoID());
				}
				if(snum.length()>7&&snum.substring(0,7).equals("2800010"))
				{
					String nnum=spart.getPartNumber()+"-T";
					QMPartIfc np=createNewTGYPart(spart,nnum,spart.getPartName());
					String[] mtt=new String[]{spart.getBsoID(),np.getBsoID(),"1","",mt[4],"0","","","��",
						np.getPartNumber()};
					nmap.put(spart.getPartNumber(),mtt);
				}
				if(snum.length()>7&&snum.substring(0,7).equals("2800015"))
				{
					String nnum=spart.getPartNumber()+"-T";
					QMPartIfc np=createNewTGYPart(spart,nnum,spart.getPartName());
					String[] mtt=new String[]{spart.getBsoID(),np.getBsoID(),"1","",mt[4],"0","","","��",
						np.getPartNumber()};
					nmap.put(spart.getPartNumber(),mtt);
				}
				if(snum.length()>7&&snum.substring(0,7).equals("2803G01"))
				{
					String nnum=spart.getPartNumber()+"-01";
					String nnum1=spart.getPartNumber()+"-05";
					QMPartIfc np=createNewTGYPart(spart,nnum,spart.getPartName());
					QMPartIfc np1=createNewTGYPart(spart,nnum1,spart.getPartName());
					String[] mtt=new String[]{gyID,np.getBsoID(),"1","",mt[4],"0","","","��",np.getPartNumber()};
					String[] mtt1=new String[]{gyID,np1.getBsoID(),"1","",mt[4],"0","","","��",np1.getPartNumber()};
					Vector vec=new Vector();
					vec.add(mtt);
					vec.add(mtt1);
					vec.add(mt);
					nmap.put(spart.getPartNumber(),vec);
					createGyBomCFHistory(spart.getBsoID(),np.getBsoID(),gyID,"cf");
					createGyBomCFHistory(spart.getBsoID(),np1.getBsoID(),gyID,"cf");
				}
				if(snum.length()>7&&snum.substring(0,7).equals("3722065"))
				{
					map.put("3722G03",spart.getBsoID());
				}
				if(snum.length()>7&&snum.substring(0,7).equals("3722G03"))
				{
					map.put("3722065",spart.getBsoID());
				}
			}
			// ������2800010��2800015���Զ�����һ��
			for(Iterator iter=mtree.iterator();iter.hasNext();)
			{
				String[] mt=(String[])iter.next();
				String parentid=mt[0];
				String subid=mt[1];
				QMPartIfc ppart=(QMPartIfc)ps.refreshInfo(parentid,false);
				QMPartIfc subpart=(QMPartIfc)ps.refreshInfo(subid,false);
				String pnum=ppart.getPartNumber();
				String snum=subpart.getPartNumber();
				// ��ֹ���
				if(pnum.length()>7&&pnum.substring(0,7).equals("1100G01")&&snum.length()>7
					&&snum.substring(0,7).equals("1103010"))
				{
					String npid=(String)map.get("5000990");
					if(npid==null)
					{
						npid=parentid;
					}
					mt[0]=npid;
					if(npid!=null)
					{
						// ���������ʷ����ȡ��
						createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),npid,"tq");
					}
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("2400010")&&snum.length()>7
					&&snum.substring(0,7).equals("3103070"))
				{
					mt[0]=gyID;
					// ���������ʷ����ȡ��
					createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),gyID,"tq");
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("2500010")&&snum.length()>7
					&&snum.substring(0,7).equals("3103070"))
				{
					mt[0]=gyID;
					// ���������ʷ����ȡ��
					createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),gyID,"tq");
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("3000G00")&&snum.length()>7
					&&snum.substring(0,7).equals("3103070"))
				{
					mt[0]=gyID;
					// ���������ʷ����ȡ��
					createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),gyID,"tq");
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("5000010")&&snum.length()>7
					&&snum.substring(0,7).equals("3725020"))
				{
					String nid=(String)map.get("3900G01");
					if(nid==null)
					{
						nid=parentid;
					}
					mt[0]=nid;
					if(nid!=null)
					{
						// ���������ʷ����ȡ��
						createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),nid,"tq");
					}
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("5000010")&&snum.length()>7
					&&snum.substring(0,7).equals("8203010"))
				{
					String nid=(String)map.get("3900G01");
					if(nid==null)
					{
						nid=parentid;
					}
					mt[0]=nid;
					if(nid!=null)
					{
						// ���������ʷ����ȡ��
						createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),nid,"tq");
					}
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("2800010"))
				{
					String[] pt=(String[])nmap.get(pnum);
					mt[0]=pt[1];
					// ���������ʷ����ȡ��
					createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),pt[1],"tq");
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("2800015"))
				{
					String[] pt=(String[])nmap.get(pnum);
					mt[0]=pt[1];
					// ���������ʷ����ȡ��
					createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),pt[1],"tq");
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("2803G01"))
				{
					Vector vec=(Vector)nmap.get(pnum);
					String[] mtt=(String[])vec.elementAt(0);
					String[] mtt1=(String[])vec.elementAt(1);
					// String[] pt=(String[])nmap.get(pnum);
					String subpid=mt[1];
					QMPartIfc sp=(QMPartIfc)ps.refreshInfo(subpid,false);
					String dwbs=mt[4];
					HashMap submap=new HashMap();
					String[] subroutestr=new String[2];
					if(dwbs.equals("W34"))// �ɶ�ȡ����·��
					{
						submap=getSecondRoute("'"+sp.getBsoID()+"'",submap);
						if(submap.get(sp.getBsoID())!=null)
						{
							subroutestr=(String[])(submap.get(sp.getBsoID()));
						}
					}
					else
					{
						submap=getRoute("'"+sp.getMasterBsoID()+"'",submap);
						if(submap.get(sp.getMasterBsoID())!=null)
						{
							subroutestr=(String[])(submap.get(sp.getMasterBsoID()));
						}
					}
					String szz=subroutestr[0];
					String szp=subroutestr[1];
					if(szz==null)
					{
						szz="";
					}
					if(szp==null)
					{
						szp="";
					}
					if(szp.equals("��"))
					{
						mt[0]=mtt[1];
						// ���������ʷ����ȡ��
						createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),mtt[1],"tq");
					}
					else
					{
						mt[0]=mtt1[1];
						// ���������ʷ����ȡ��
						createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),mtt1[1],"tq");
					}
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("3722065"))
				{
					String[] pt=(String[])nmap.get("3722065");
					mt[0]=pt[1];
					// ���������ʷ����ȡ��
					createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),pt[1],"tq");
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("3722G03"))
				{
					String[] pt=(String[])nmap.get("3722G03");
					mt[0]=pt[1];
					// ���������ʷ����ȡ��
					createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),pt[1],"tq");
				}
				else
				{

				}
				ntree.add(mt);
			}
			// ��2800010��2800015��β��Ϊ-T�ļ��ӵ�����BOM���С�
			String[] remm=null;
			for(Iterator it=nmap.keySet().iterator();it.hasNext();)
			{
				String key=(String)it.next();
				Object obj=nmap.get(key);
				if(obj instanceof Vector)
				{
					Vector vec=(Vector)obj;
					String[] mtt=(String[])vec.elementAt(0);
					String[] mtt1=(String[])vec.elementAt(1);
					remm=(String[])vec.elementAt(2);
					ntree.remove(remm);
					ntree.add(mtt);
					ntree.add(mtt1);
				}
				else
				{
					String[] mtt=(String[])nmap.get(key);
					ntree.add(mtt);
				}

			}
			return ntree;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	
	//CCBegin SS20	
	/**
     * �������Ͳ��
     * @param gypart ��
     * @param mtree �Ӽ�
     * @param partNumber1000940 1000940����BSOID
     * @return
     * @throws QMException
     */
    private Collection chaifengybomOther(QMPartIfc gypart,Collection mtree, String partNumber1000940, 
                        String partNumber10004101, HashMap map, String dwbs, ArrayList arrayList,
                        String id3900G01, String id3100G00) throws QMException//SS25
    {
//        System.out.println("�������Ͳ��--------------------------------");
        try
        {
            String gyID = gypart.getBsoID();
            PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
            ArrayList ret = new ArrayList();
            
            for(Iterator iter=mtree.iterator();iter.hasNext();)
            {
                String[] mt=(String[])iter.next();
                String subid=mt[1];
                String parentID = mt[0];
                QMPartIfc spart=(QMPartIfc)ps.refreshInfo(subid,false);
                QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(parentID,false);
                String partNumber=spart.getPartNumber();
                
//                if(parentPart.getPartNumber().contains("1000940") 
//                        && spart.getPartNumber().contains("8116G01"))
//                {
//                    System.out.println("����============="+parentPart.getPartNumber());
//                    System.out.println("�Ӽ�============="+spart.getPartNumber());
//                }
                
                
                //1�������֡����ֳ���ƽ̨��ֱ�ӹҵ��������ܳ��µ��߼��ܳɣ�
                //1000410��1303G02��1503G01��1600G00��1700G00��3523G01��3724G34ֱ�ӷŵ�1000940��
                if(partNumber.contains("1000410") || partNumber.contains("1303G02") || 
                        partNumber.contains("1503G01") || partNumber.contains("1600G00") || 
                        partNumber.contains("1700G00") || partNumber.contains("3523G01") || 
                        partNumber.contains("1308G02") || partNumber.contains("1313G02") || //SS23��������Ҳ�Ƿŵ�1000940��
                        partNumber.contains("3724G34") )
                {
                    mt[0] = partNumber1000940;
                }
                //CCBegin SS23
                /*��������Ҳ�Ƿŵ�1000940��
                //1308G02��1313G02 �ŵ�1000410��
                if(partNumber.contains("1308G02") || partNumber.contains("1313G02") )
                {
                    mt[0] = partNumber10004101;
                }
                */
//                System.out.println("partNumber===="+partNumber);
                //CCEnd SS23
                //CCBegin SS25
              //3725020-A01 8203010-E06C 7925040-92W 7901012EA01 �ŵ�3900G01��
                //if(partNumber.equalsIgnoreCase("3725020-A01") || partNumber.equalsIgnoreCase("8203010-E06C") || 
                //        partNumber.equalsIgnoreCase("7925040-92W") || partNumber.equalsIgnoreCase("7901012EA01"))
                //{
                //    mt[0] = id3900G01;
                //}
                
                //�������ͷ3103070��3100G00��
                if(partNumber.startsWith("3103070"))
                {
//                    mt[0] = id3100G00;
                    mt[0] = gyID;//SS32
                }
                //CCEnd SS25
                
                //CCBegin SS33
                //2800010��5000990�����ṹ���õ�����
//                if(partNumber.contains("2800010") || partNumber.contains("5000990") )
//                {
////                    System.out.println("gyID====gyID===="+gyID);
//                    mt[0] = gyID;
//                    mt[6] = "";
//                }
                //CCEnd SS33
                ret.add(mt);
            }
            
            return ret;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new QMException(ex);
        }
    }
    
    /**
     * ��ֺ�ŵ�����������
     * @param number
     * @return
     */
    private String chaifenPartNumberY(String number)
    {
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        String ret = "";
        try
        {
            conn=PersistUtil.getConnection();
            stmt=conn.createStatement();
            String countIdSql="select count(*) from QMPartMaster where partNumber like '"+number+"Y___'";
            rs=stmt.executeQuery(countIdSql);
            rs.next();
            int countList=rs.getInt(1);
            // System.out.println(parentID+"==="+dwbs+"==="+eff+"  count:"+countList);
            if(countList<9)
            {
                String temp = String.valueOf(countList+1);
                ret = number+"Y00"+temp;
            }
            else if(countList>=9)
            {
                String temp = String.valueOf(countList+1);
                ret = number+"Y0"+temp;
            }
            else if(countList>=99)
            {
                String temp = String.valueOf(countList+1);
                ret = number+"Y"+temp;
            }
            
            // ��ղ��ر�sql��������
            rs.close();
            // �ر�Statement
            stmt.close();
            // �ر����ݿ�����
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(rs!=null)
                {
                    rs.close();
                }
                if(stmt!=null)
                {
                    stmt.close();
                }
                if(conn!=null)
                {
                    conn.close();
                }
            }
            catch(SQLException ex1)
            {
                ex1.printStackTrace();
            }
        }
        return ret;
    }
    
    /**
     * ��һ����ֺ��940��������
     * @param number
     * @return
     */
    private String chaifenPartNumberFBefor(String number)
    {
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        String ret = null;
        try
        {
            conn=PersistUtil.getConnection();
            stmt=conn.createStatement();
            String countIdSql="select count(*) from QMPartMaster where partNumber like '"+number+"F___'";
            rs=stmt.executeQuery(countIdSql);
            rs.next();
            int countList=rs.getInt(1);
            // System.out.println(parentID+"==="+dwbs+"==="+eff+"  count:"+countList);
            if(countList<9)
            {
                String temp = String.valueOf(countList);
                ret = number+"F00"+temp;
            }
            else if(countList>=9)
            {
                String temp = String.valueOf(countList);
                ret = number+"F0"+temp;
            }
            else if(countList>=99)
            {
                String temp = String.valueOf(countList);
                ret = number+"F"+temp;
            }
            
            // ��ղ��ر�sql��������
            rs.close();
            // �ر�Statement
            stmt.close();
            // �ر����ݿ�����
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(rs!=null)
                {
                    rs.close();
                }
                if(stmt!=null)
                {
                    stmt.close();
                }
                if(conn!=null)
                {
                    conn.close();
                }
            }
            catch(SQLException ex1)
            {
                ex1.printStackTrace();
            }
        }
        return ret;
    }
    
    /**
     * ��һ����ֺ�Ÿ���������
     * @param number
     * @return
     */
    private String chaifenPartNumberYBefor(String number)
    {
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        String ret = null;
        try
        {
            conn=PersistUtil.getConnection();
            stmt=conn.createStatement();
            String countIdSql="select count(*) from QMPartMaster where partNumber like '"+number+"Y___'";
            rs=stmt.executeQuery(countIdSql);
            rs.next();
            int countList=rs.getInt(1);
            // System.out.println(parentID+"==="+dwbs+"==="+eff+"  count:"+countList);
            if(countList<9)
            {
                String temp = String.valueOf(countList);
                ret = number+"Y00"+temp;
            }
            else if(countList>=9)
            {
                String temp = String.valueOf(countList);
                ret = number+"Y0"+temp;
            }
            else if(countList>=99)
            {
                String temp = String.valueOf(countList);
                ret = number+"Y"+temp;
            }
            
            // ��ղ��ر�sql��������
            rs.close();
            // �ر�Statement
            stmt.close();
            // �ر����ݿ�����
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(rs!=null)
                {
                    rs.close();
                }
                if(stmt!=null)
                {
                    stmt.close();
                }
                if(conn!=null)
                {
                    conn.close();
                }
            }
            catch(SQLException ex1)
            {
                ex1.printStackTrace();
            }
        }
        return ret;
    }
	
	/**
     * J6P���Ͳ��
     * @param gypart ��
     * @param mtree �Ӽ�
     * @param partNumber1000940 1000940����BSOID
     * @return
     * @throws QMException
     */
	private Collection chaifengybomJ6P(QMPartIfc gypart,Collection mtree, String partNumber1000940, 
            String partNumber1000410, HashMap map, String dwbs, ArrayList arrayList,
            String id3900G01, String id3100G00) throws QMException//SS25
	{
	    try
        {
	        
	        String gyID = gypart.getBsoID();
            PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
            ArrayList ret = new ArrayList();
            
            QMPartIfc newParentPart1119G01=null;
            QMPartIfc newParentPart1303G01=null;
            QMPartIfc newParentPart1104G01=null;
            QMPartIfc newParentPart1203G01=null;
            QMPartIfc newParentPart8116G01=null;
            QMPartIfc newParentPart1311G02=null;
//            QMPartIfc newParentPart1311G01=null;//SS31
//            QMPartIfc newParentPart3406G01=null;//SS31
            QMPartIfc newParentPart3506G00=null;
            //CCBegin SS63
            QMPartIfc ZCnewParentPart1119G01=null;
            QMPartIfc ZCnewParentPart1303G01=null;
            QMPartIfc ZCnewParentPart1104G01=null;
            QMPartIfc ZCnewParentPart1203G01=null;
            QMPartIfc ZCnewParentPart8116G01=null;
            QMPartIfc ZCnewParentPart1311G02=null;
            QMPartIfc ZCnewParentPart3506G00=null;
            //CCEnd SS63
            
            boolean falg1119G01 = true;
            boolean falg1303G01 = true;
            boolean falg1104G01 = true;
            boolean falg1203G01 = true;
            boolean falg8116G01 = true;
            boolean falg1311G02 = true;
//            boolean falg1311G01 = true;
//            boolean falg3406G01 = true;
            boolean falg3506G00 = true;
            
            for(Iterator iter=mtree.iterator();iter.hasNext();)
            {
                String[] mt=(String[])iter.next();
                String subid=mt[1];
                String parentID = mt[0];
                QMPartIfc spart=(QMPartIfc)ps.refreshInfo(subid,false);
                QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(parentID,false);
                String partNumber=spart.getPartNumber();
                
                //1�������֡����ֳ���ƽ̨��ֱ�ӹҵ��������ܳ��µ��߼��ܳɣ�
                //1000410��1303G02��1503G01��1600G00��1700G00��3523G01��3724G34ֱ�ӷŵ�1000940��
                if(partNumber.contains("1000410") || partNumber.contains("1303G02") || 
                        partNumber.contains("1503G01") || partNumber.contains("1600G00") || 
                        partNumber.contains("1700G00") || partNumber.contains("3523G01") || 
                        partNumber.contains("1308G02") || partNumber.contains("1313G02") || //SS23��������Ҳ�Ƿŵ�1000940��
                        partNumber.contains("3724G34") )
                {
                    mt[0] = partNumber1000940;
                }
                //CCBegin SS23
                /*//��������Ҳ�Ƿŵ�1000940��
                //1308G02��1313G02 �ŵ�1000410��
                if(partNumber.contains("1308G02") || partNumber.contains("1313G02") )
                {
                    mt[0] = partNumber1000410;
                }
                */
                //CCEnd SS23
                //CCBegin SS25
                //3725020-A01 8203010-E06C 7925040-92W 7901012EA01 �ŵ�3900G01��
                //if(partNumber.equalsIgnoreCase("3725020-A01") || partNumber.equalsIgnoreCase("8203010-E06C") || 
                //        partNumber.equalsIgnoreCase("7925040-92W") || partNumber.equalsIgnoreCase("7901012EA01"))
                //{
                //    mt[0] = id3900G01;
                //}
                
                //�������ͷ3103070��3100G00��
                if(partNumber.startsWith("3103070"))
                {
//                    mt[0] = id3100G00;
                    mt[0] = gyID;//SS32
                }
                //CCEnd SS25
              //CCBegin SS33
//                if(partNumber.contains("2800010") || partNumber.contains("5000990") )
//                {
//                    System.out.println("gyID====gyID===="+gyID);
//                    mt[0] = gyID;
//                    mt[6] = "";
//                }
              //CCEnd SS33
//                1104G01 ��ֵ�1000940��1104130 1104250 1104210 1145035
                if(mt[9].contains("1104G01"))
                {
//                	System.out.println("33333333333333333");
//                	
//                	String pp = parentPart.getPartNumber();
//                	System.out.println("pp=========="+pp);
//                	System.out.println("mt[9]=========="+mt[9]);
//                	if(pp.contains("1100G01")){
//                		System.out.println("pp=========="+pp);
//                	}
//                    continue;
                	//CCBegin SS56
//                    mt[0]=gypart.getBsoID();
                	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
//                        System.out.println("col.size()================"+col.size());
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1104G01"))
                {
//                	System.out.println("11111111");
//                	System.out.println("partNumber===1====="+partNumber);
                	//CCBegin SS63
                	if(ZCnewParentPart1104G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1104G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1104G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
                            partNumber.contains("1104210") || partNumber.contains("1145035"))
                    {
//                    	System.out.println("partNumber===2====="+partNumber);
//                    	System.out.println("newParentPart1104G01===2====="+newParentPart1104G01);
//                    	System.out.println("22222222222");
                        if(newParentPart1104G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1104G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1104G01)
                        {
//                        	System.out.println("���þ��׾͸�������");
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1104G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1104G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(spart.getBsoID(),newParentPart1104G01.getBsoID(),gyID,"cf");
                            falg1104G01 = false;
                        }
                        
                        if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
                                partNumber.contains("1104210") || partNumber.contains("1145035"))
                        {
                            mt[0] = newParentPart1104G01.getBsoID();
                        }
                    }
                    //End SS28
                }
                
//                1119G01 ��ֵ�1000940��1119046 1119031 1119035 1119053 1119062 1119064 1119125 
//                                        1119127 CQ1461045 CQ34010��1���� T6769006A
//                System.out.println("mt[9]==="+mt[9]);
//                System.out.println("parentPart.getPartNumber()==="+parentPart.getPartNumber());
                if(mt[9].contains("1119G01"))
                {
//                    System.out.println("============mt[9].contains(1119G01)==============");
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
                	//CCEnd SS56
//                    System.out.println("arrayList  ==========  " + arrayList);
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
//                            System.out.println("cfpart.getPartNumber()  ==========  " + cfpart.getPartNumber());
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1119G01"))
                {
//                    System.out.println("============parentPart.getPartNumber().contains(1119G01)==============");
                	//CCBegin SS63
                	if(ZCnewParentPart1119G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1119G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1119G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1119046") || partNumber.contains("1119031") || 
                            partNumber.contains("1119035") || partNumber.contains("1119053") || 
                            partNumber.contains("1119062") || partNumber.contains("1119064") || 
                            partNumber.contains("1119125") || partNumber.contains("1119127") || 
                            partNumber.contains("CQ1461045") || partNumber.contains("T6769006A") ||
                            partNumber.contains("CQ34010"))
                    {
                        if(newParentPart1119G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1119G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1119G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1119G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1119G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1119G01.getBsoID(),gyID,"cf");
                            falg1119G01 = false;
                        }
                        
                        if(partNumber.contains("1119046") || partNumber.contains("1119031") || 
                                partNumber.contains("1119035") || partNumber.contains("1119053") || 
                                partNumber.contains("1119062") || partNumber.contains("1119064") || 
                                partNumber.contains("1119125") || partNumber.contains("1119127") || 
                                partNumber.contains("CQ1461045") || partNumber.contains("T6769006A"))
                        {
                            mt[0] = newParentPart1119G01.getBsoID();
                        }
                        if(partNumber.contains("CQ34010"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart1119G01.getBsoID();
                            temp[2] = "1";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-1)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                    }
                    //End SS28
                    //createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),gyID,"tq");
                }
                
//                1203G01 ��ֵ�1000940��1203040 1203065 1203073 1203071 1203075 1203075 CQ1460816 
//                                        CQ1460825 Q1840830 CQ32608 1203010 1203021 1203071 1203025 
//                                        1203030 1203071 1203510 CQ1501045 CQ1501055 CQ34010 Q1841225 
//                                        Q40110 CQ34012 CQ32612 Q1841240
                if(mt[9].contains("1203G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1203G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1203G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1203G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1203G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1203040") || partNumber.contains("1203065") || 
                            partNumber.contains("1203073") || partNumber.contains("1203071") || 
                            partNumber.contains("1203075") || partNumber.contains("1203075") || 
                            partNumber.contains("CQ1460816") || partNumber.contains("CQ1460825") || 
                            partNumber.contains("Q1840830") || partNumber.contains("CQ32608") || 
                            partNumber.contains("1203010") || partNumber.contains("1203021") || 
                            partNumber.contains("1203071") || partNumber.contains("1203025") || 
                            partNumber.contains("1203030") || partNumber.contains("1203071") || 
                            partNumber.contains("1203510") || partNumber.contains("CQ1501045") || 
                            partNumber.contains("CQ1501055") || partNumber.contains("CQ34010") || 
                            partNumber.contains("Q1841225") || partNumber.contains("Q40110") || 
                            partNumber.contains("CQ34012") || partNumber.contains("CQ32612") ||
                            partNumber.contains("Q1841240"))
                    {
                        if(newParentPart1203G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1203G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1203G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1203G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1203G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1203G01.getBsoID(),gyID,"cf");
                            falg1203G01 = false;
                        }
                        
                        if(partNumber.contains("1203040") || partNumber.contains("1203065") || 
                                partNumber.contains("1203073") || partNumber.contains("1203071") || 
                                partNumber.contains("1203075") || partNumber.contains("1203075") || 
                                partNumber.contains("CQ1460816") || partNumber.contains("CQ1460825") || 
                                partNumber.contains("Q1840830") || partNumber.contains("CQ32608") || 
                                partNumber.contains("1203010") || partNumber.contains("1203021") || 
                                partNumber.contains("1203071") || partNumber.contains("1203025") || 
                                partNumber.contains("1203030") || partNumber.contains("1203071") || 
                                partNumber.contains("1203510") || partNumber.contains("CQ1501045") || 
                                partNumber.contains("CQ1501055") || partNumber.contains("CQ34010") || 
                                partNumber.contains("Q1841225") || partNumber.contains("Q40110") || 
                                partNumber.contains("CQ34012") || partNumber.contains("CQ32612") ||
                                partNumber.contains("Q1841240"))
                        {
                            mt[0] = newParentPart1203G01.getBsoID();
                        }
                    }
                    //End SS28
                }
                
//                8116G01 ��ֵ�1000940��8116061 8116703
                if(mt[9].contains("8116G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
//                        System.out.println("8116G01  mt[1]  == " + mt[1]);
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("8116G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart8116G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart8116G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart8116G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("8116061") || partNumber.contains("8116703"))
                    {
                        if(newParentPart8116G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart8116G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg8116G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart8116G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart8116G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart8116G01.getBsoID(),gyID,"cf");
                            falg8116G01 = false;
                        }
                        
                        if(partNumber.contains("8116061") || partNumber.contains("8116703"))
                        {
                            mt[0] = newParentPart8116G01.getBsoID();
                        }
                    }
                    //End SS28
                }
                
//                1303G01 ��ֵ�1000940��1303011 1303021 CQ67690B��2����
                if(mt[9].contains("1303G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1303G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1303G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1303G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1303G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1303011") || partNumber.contains("1303021") ||
                            partNumber.contains("CQ67690B"))
                    {
                        if(newParentPart1303G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1303G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1303G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1303G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1303G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1303G01.getBsoID(),gyID,"cf");
                            falg1303G01 = false;
                        }
                        
                        if(partNumber.contains("1303011") || partNumber.contains("1303021"))
                        {
                            mt[0] = newParentPart1303G01.getBsoID();
                        }
                        if(partNumber.contains("CQ67690B"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart1303G01.getBsoID();
                            temp[2] = "2";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-2)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-2).toString();
                            }
                        }
                    }
                    //End SS28
                }
                
//                1311G02 ��ֵ�1000940��1311024 1311036
                if(mt[9].contains("1311G02"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1311G02"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1311G02 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1311G02 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1311G02.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1311024") || partNumber.contains("1311036"))
                    {
                        if(newParentPart1311G02 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1311G02 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1311G02)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1311G02.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1311G02.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1311G02.getBsoID(),gyID,"cf");
                            falg1311G02 = false;
                        }
                        
                        if(partNumber.contains("1311024") || partNumber.contains("1311036"))
                        {
                            mt[0] = newParentPart1311G02.getBsoID();
                        }
                    }
                    //End SS28
                }
                
//                3406G01 ��ֵ�1000940��3406010 3406021 3406321
                if(mt[9].contains("3406G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                //CCBegin SS31
//                if(parentPart.getPartNumber().contains("3406G01"))
//                {
//                    //Begin SS28
//                    if(partNumber.contains("3406010") || partNumber.contains("3406021") || 
//                            partNumber.contains("3406321"))
//                    {
//                        if(newParentPart3406G01 == null)
//                        {
//                            String number = getNewPartNumber(parentPart.getPartNumber());
//                            newParentPart3406G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
//                        }
//                        if(falg3406G01)
//                        {
//                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
//
//                            String[] strs = (String[])map.get(parentPart.getPartNumber());
//                            String[] str=new String[10];
//                            str[0]=partNumber1000940;
//                            str[1]=newParentPart3406G01.getBsoID();
//                            str[2]=strs[2];
//                            str[3]=strs[3];
//                            str[4]=dwbs;
//                            str[5]=strs[5];
//                            str[6]=strs[6];
//                            str[7]=strs[7];
//                            str[8]=strs[8];
//                            str[9]=newParentPart3406G01.getPartNumber();
//                            ret.add(str);
//                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart3406G01.getBsoID(),gyID,"cf");
//                            falg3406G01 = false;
//                        }
//                        
//                        if(partNumber.contains("3406010") || partNumber.contains("3406021") || 
//                                partNumber.contains("3406321"))
//                        {
//                            mt[0] = newParentPart3406G01.getBsoID();
//                        }
//                    }
//                    //End SS28
//                }
//                1311G01 ��ֵ�1000940��CQ67622B(1���� CQ67640B��2����
//                if(mt[9].contains("1311G01"))
//                {
////                    continue;
//                    mt[0]=gypart.getBsoID();
//                    if(arrayList.contains(mt[1]))
//                    {
//                        String[] str=new String[10];
//                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
//                        Iterator ite = col.iterator();
//                        String cfID = null;
//                        if(ite.hasNext())
//                        {
//                            String[] temp=(String[])ite.next();
//                            cfID = temp[1];
//                        }
//                        if(cfID != null)
//                        {
//                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
//                            str[0]=partNumber1000940;
//                            str[1]=cfID;
//                            str[2]=mt[2];
//                            str[3]=mt[3];
//                            str[4]=dwbs;
//                            str[5]=mt[5];
//                            str[6]=mt[6];
//                            str[7]=mt[7];
//                            str[8]=mt[8];
//                            str[9]=cfpart.getPartNumber();
//                            ret.add(str);
//                        }
//                    }
//                }
//                if(parentPart.getPartNumber().contains("1311G01"))
//                {
//                    //Begin SS28
//                    if(partNumber.contains("CQ67622B") || partNumber.contains("CQ67640B"))
//                    {
//                        if(newParentPart1303G01 == null)
//                        {
//                            String number = getNewPartNumber(parentPart.getPartNumber());
//                            newParentPart1303G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
//                        }
//                        if(falg1303G01)
//                        {
//                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
//
//                            String[] strs = (String[])map.get(parentPart.getPartNumber());
//                            String[] str=new String[10];
//                            str[0]=partNumber1000940;
//                            str[1]=newParentPart1303G01.getBsoID();
//                            str[2]=strs[2];
//                            str[3]=strs[3];
//                            str[4]=dwbs;
//                            str[5]=strs[5];
//                            str[6]=strs[6];
//                            str[7]=strs[7];
//                            str[8]=strs[8];
//                            str[9]=newParentPart1303G01.getPartNumber();
//                            ret.add(str);
//                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1303G01.getBsoID(),gyID,"cf");
//                            falg1303G01 = false;
//                        }
//                        
//                        if(partNumber.contains("CQ67622B"))
//                        {
//                            String temp[]=new String[10];
//                            String quantity = mt[2];
//                            Float count = Float.valueOf(quantity);
//                            temp[0] = newParentPart1303G01.getBsoID();
//                            temp[2] = "1";
//                            temp[1] = mt[1];
//                            temp[3] = mt[3];
//                            temp[4] =dwbs;
//                            temp[5] = mt[5];
//                            temp[6] = mt[6];
//                            temp[7] = mt[7];
//                            temp[8] = mt[8];
//                            temp[9] = mt[9];
//                            ret.add(temp);
//                            if((count-1)<=0)
//                            {
//                                continue;
//                            }
//                            else
//                            {
//                                mt[2] = Float.valueOf(count-1).toString();
//                            }
//                        }
//                        
//                        if(partNumber.contains("CQ67640B"))
//                        {
//                            String temp[]=new String[10];
//                            String quantity = mt[2];
//                            Float count = Float.valueOf(quantity);
//                            temp[0] = newParentPart1303G01.getBsoID();
//                            temp[2] = "2";
//                            temp[1] = mt[1];
//                            temp[3] = mt[3];
//                            temp[4] = dwbs;
//                            temp[5] = mt[5];
//                            temp[6] = mt[6];
//                            temp[7] = mt[7];
//                            temp[8] = mt[8];
//                            temp[9] = mt[9];
//                            ret.add(temp);
//                            if((count-2)<=0)
//                            {
//                                continue;
//                            }
//                            else
//                            {
//                                mt[2] = Float.valueOf(count-1).toString();
//                            }
//                        }
//                    }
//                    //End SS28
//                }
                //CCEnd SS31

                
//                3506G00 ��ֵ�1000940��3506221 3506230
                if(mt[9].contains("3506G00"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("3506G00"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart3506G00 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart3506G00 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart3506G00.getBsoID();
                    //CCEnd SS63
                    //Begin SS28 
                    if(partNumber.contains("3506221") || partNumber.contains("3506230"))
                    {
                        if(newParentPart3506G00 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart3506G00 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg3506G00)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart3506G00.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart3506G00.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart3506G00.getBsoID(),gyID,"cf");
                            falg3506G00 = false;
                        }
                        
                        if(partNumber.contains("3506221") || partNumber.contains("3506230"))
                        {
                            mt[0] = newParentPart3506G00.getBsoID();
                        }
                    }
                    //End SS28
                }
                
                
                ret.add(mt);
            }
            
            return ret;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new QMException(ex);
        }
	}
	
	/**
     * J6M���Ͳ��
     * @param gypart ��
     * @param mtree �Ӽ�
     * @param partNumber1000940 1000940����BSOID
     * @return
     * @throws QMException
     */
	private Collection chaifengybomJ6M(QMPartIfc gypart,Collection mtree, String partNumber1000940, 
	        String partNumber10004101, HashMap map, String dwbs, ArrayList arrayList,
            String id3900G01, String id3100G00) throws QMException//SS25
    {
        try
        {
            String gyID = gypart.getBsoID();
            PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
            ArrayList ret = new ArrayList();
            
            QMPartIfc newParentPart1104G01=null;
            QMPartIfc newParentPart1119G01=null;
            QMPartIfc newParentPart1203G01=null;
            QMPartIfc newParentPart1303G01=null;
            QMPartIfc newParentPart1311G02=null;
            QMPartIfc newParentPart3406G01=null;
            QMPartIfc newParentPart3406G02=null;
            //CCBegin SS63
            QMPartIfc ZCnewParentPart1104G01=null;
            QMPartIfc ZCnewParentPart1119G01=null;
            QMPartIfc ZCnewParentPart1203G01=null;
            QMPartIfc ZCnewParentPart1303G01=null;
            QMPartIfc ZCnewParentPart1311G02=null;
            QMPartIfc ZCnewParentPart3406G01=null;
            QMPartIfc ZCnewParentPart3406G02=null;
            //CCEnd SS63
            
            boolean falg1104G01 = true;
            boolean falg1119G01 = true;
            boolean falg1203G01 = true;
            boolean falg1303G01 = true;
            boolean falg1311G02 = true;
            boolean falg3406G01 = true;
            boolean falg3406G02 = true;
            
            for(Iterator iter=mtree.iterator();iter.hasNext();)
            {
                String[] mt=(String[])iter.next();
                String subid=mt[1];
                String parentID = mt[0];
                QMPartIfc spart=(QMPartIfc)ps.refreshInfo(subid,false);
                QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(parentID,false);
                String partNumber=spart.getPartNumber();
                
                //1�������֡����ֳ���ƽ̨��ֱ�ӹҵ��������ܳ��µ��߼��ܳɣ�
                //1000410��1303G02��1503G01��1600G00��1700G00��3523G01��3724G34ֱ�ӷŵ�1000940��
                if(partNumber.contains("1000410") || partNumber.contains("1303G02") || 
                        partNumber.contains("1503G01") || partNumber.contains("1600G00") || 
                        partNumber.contains("1700G00") || partNumber.contains("3523G01") || 
                        partNumber.contains("1308G02") || partNumber.contains("1313G02") || //SS23��������Ҳ�Ƿŵ�1000940��
                        partNumber.contains("3724G34") )
                {
                    mt[0] = partNumber1000940;
                }
                //CCBegin SS23
                /*��������Ҳ�Ƿŵ�1000940��
                //1308G02��1313G02 �ŵ�1000410��
                if(partNumber.contains("1308G02") || partNumber.contains("1313G02") )
                {
                    mt[0] = partNumber10004101;
                }
                */
                //CCEnd SS23
                //CCBegin SS25
                //3725020-A01 8203010-E06C 7925040-92W 7901012EA01 �ŵ�3900G01��
                //if(partNumber.equalsIgnoreCase("3725020-A01") || partNumber.equalsIgnoreCase("8203010-E06C") || 
                //        partNumber.equalsIgnoreCase("7925040-92W") || partNumber.equalsIgnoreCase("7901012EA01"))
                //{
                //    mt[0] = id3900G01;
                //}
                
                //�������ͷ3103070��3100G00��
                if(partNumber.startsWith("3103070"))
                {
//                    mt[0] = id3100G00;
                    mt[0] = gyID;//SS32
                }
                //CCEnd SS25
              //CCBegin SS33
//                if(partNumber.contains("2800010") || partNumber.contains("5000990") )
//                {
////                    System.out.println("gyID====gyID===="+gyID);
//                    mt[0] = gyID;
//                    mt[6] = "";
//                }
              //CCEnd SS33
//                1104G01 ��ֵ�1000940��1104130 1104250 1104021 3724015 Q1841225
                if(mt[9].contains("1104G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1104G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1104G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1104G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1104G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
                            partNumber.contains("1104021") || partNumber.contains("3724015") || 
                            partNumber.contains("Q1841225"))
                    {
                        if(newParentPart1104G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1104G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1104G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1104G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1104G01.getPartNumber();
                            ret.add(str);
                            
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1104G01.getBsoID(),gyID,"cf");
                            falg1104G01 = false;
                        }
                        
                        if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
                                partNumber.contains("1104021") || partNumber.contains("3724015") || 
                                partNumber.contains("Q1841225"))
                        {
                            mt[0] = newParentPart1104G01.getBsoID();
                        }
                    }
                    //End SS28
//                    if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
//                            partNumber.contains("1104021") || partNumber.contains("3724015") || 
//                            partNumber.contains("Q1841225"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
                }
                
//                1119G01 ��ֵ�1000940��1119031 1119035 1119038 1119049 1119050 1119081 1119105 
//                                      CQ1461030 T6769006A
                if(mt[9].contains("1119G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1119G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1119G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1119G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1119G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1119031") || partNumber.contains("1119035") || 
                            partNumber.contains("1119038") || partNumber.contains("1119049") || 
                            partNumber.contains("1119050") || partNumber.contains("1119081") || 
                            partNumber.contains("1119105") || partNumber.contains("CQ1461030") || 
                            partNumber.contains("Q1841225"))
                    {
                        if(newParentPart1119G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1119G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1119G01)
                        {
                            //�������߼��ܳɺ�  1119G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1119G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1119G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1119G01.getBsoID(),gyID,"cf");
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            
                            falg1119G01 = false;
                        }
                        
                        if(partNumber.contains("1119031") || partNumber.contains("1119035") || 
                                partNumber.contains("1119038") || partNumber.contains("1119049") || 
                                partNumber.contains("1119050") || partNumber.contains("1119081") || 
                                partNumber.contains("1119105") || partNumber.contains("CQ1461030") || 
                                partNumber.contains("Q1841225"))
                        {
                            mt[0] = newParentPart1119G01.getBsoID();
                        }
                    }
                    //End SS28
//                    if(partNumber.contains("1119031") || partNumber.contains("1119035") || 
//                            partNumber.contains("1119038") || partNumber.contains("1119049") || 
//                            partNumber.contains("1119050") || partNumber.contains("1119081") || 
//                            partNumber.contains("1119105") || partNumber.contains("CQ1461030") || 
//                            partNumber.contains("Q1841225"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
                }
                
//                1203G01 ��ֵ�1000940��1203510 1203030 1203075
                if(mt[9].contains("1203G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1203G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1203G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1203G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1203G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1203510") || partNumber.contains("1203030") || 
                            partNumber.contains("1203075"))
                    {
                        if(newParentPart1203G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1203G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1203G01)
                        {
                            //�������߼��ܳɺ�  1119G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1203G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1203G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1203G01.getBsoID(),gyID,"cf");
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            
                            falg1203G01 = false;
                        }
                        
                        if(partNumber.contains("1203510") || partNumber.contains("1203030") || 
                                partNumber.contains("1203075"))
                        {
                            mt[0] = newParentPart1203G01.getBsoID();
                        }
                    }
                    //End SS28
                    
//                    if(partNumber.contains("1203510") || partNumber.contains("1203030") || 
//                            partNumber.contains("1203075"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
                }
                
//                1303G01 ��ֵ�1000940��1303021 CQ67670B
                if(mt[9].contains("1303G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1303G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1303G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1303G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1303G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1203510") || partNumber.contains("1203030") || 
                            partNumber.contains("1203075"))
                    {
                        if(newParentPart1303G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1303G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1303G01)
                        {
                            //�������߼��ܳɺ�  1119G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1303G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1303G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1303G01.getBsoID(),gyID,"cf");
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            
                            falg1303G01 = false;
                        }
                        
                        if(partNumber.contains("1203510") || partNumber.contains("1203030") || 
                                partNumber.contains("1203075"))
                        {
                            mt[0] = newParentPart1303G01.getBsoID();
                        }
                    }
                    //End SS28
                    
//                    if(partNumber.contains("1303021") || partNumber.contains("CQ67670B"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
                }
                
//                1311G02 ��ֵ�1000940��1311020 1311022 1311027 1311030 1311032 CQ1460820 
//                                      CQ1501430 CQ34014 CQ67616 CQ67630B Q40314
                if(mt[9].contains("1311G02"))
                {
//                    continue;
                	//CCBegin SS56
//                 //CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1311G02"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1311G02 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1311G02 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1311G02.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1311020") || partNumber.contains("1311022") || 
                            partNumber.contains("1311027") || partNumber.contains("1311030") || 
                            partNumber.contains("1311032") || partNumber.contains("CQ1460820") || 
                            partNumber.contains("CQ1501430") || partNumber.contains("CQ34014") || 
                            partNumber.contains("CQ67616") || partNumber.contains("CQ67630B") || 
                            partNumber.contains("Q40314"))
                    {
                        if(newParentPart1311G02 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1311G02 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1311G02)
                        {
                            //�������߼��ܳɺ�  1119G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1311G02.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1311G02.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1311G02.getBsoID(),gyID,"cf");
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            
                            falg1311G02 = false;
                        }
                        
                        if(partNumber.contains("1311020") || partNumber.contains("1311022") || 
                                partNumber.contains("1311027") || partNumber.contains("1311030") || 
                                partNumber.contains("1311032") || partNumber.contains("CQ1460820") || 
                                partNumber.contains("CQ1501430") || partNumber.contains("CQ34014") || 
                                partNumber.contains("CQ67616") || partNumber.contains("CQ67630B") || 
                                partNumber.contains("Q40314"))
                        {
                            mt[0] = newParentPart1311G02.getBsoID();
                        }
                    }
                    //End SS28
                    
//                    if(partNumber.contains("1311020") || partNumber.contains("1311022") || 
//                            partNumber.contains("1311027") || partNumber.contains("1311030") || 
//                            partNumber.contains("1311032") || partNumber.contains("CQ1460820") || 
//                            partNumber.contains("CQ1501430") || partNumber.contains("CQ34014") || 
//                            partNumber.contains("CQ67616") || partNumber.contains("CQ67630B") || 
//                            partNumber.contains("Q40314"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
                }
                
//                3406G01 ��ֵ�1000940��3406011 CQ67635B 3406030 3406424
                if(mt[9].contains("3406G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("3406G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart3406G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart3406G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart3406G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("3406011") || partNumber.contains("CQ67635B") || 
                            partNumber.contains("3406030") || partNumber.contains("3406424"))
                    {
                        if(newParentPart3406G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart3406G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg3406G01)
                        {
                            //�������߼��ܳɺ�  1119G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart3406G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart3406G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart3406G01.getBsoID(),gyID,"cf");
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            
                            falg3406G01 = false;
                        }
                        
                        //if(partNumber.contains("3406011") || partNumber.contains("CQ67635B") || 
                        //        partNumber.contains("3406030") || partNumber.contains("3406424"))
                        //{
                        //    mt[0] = newParentPart3406G01.getBsoID();
                        //}
                    }
                    //End SS28
                    
//                    if(partNumber.contains("3406011") || partNumber.contains("CQ67635B") || 
//                            partNumber.contains("3406030") || partNumber.contains("3406424"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
                }
                
//                3406G02 ��ֵ�1000940��3406011 CQ67635B 3406030 3406424
                if(mt[9].contains("3406G02"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("3406G02"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart3406G02 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart3406G02 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart3406G02.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("3406011") || partNumber.contains("CQ67635B") || 
                            partNumber.contains("3406030") || partNumber.contains("3406424"))
                    {
                        if(newParentPart3406G02 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart3406G02 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg3406G02)
                        {
                            //�������߼��ܳɺ�  1119G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart3406G02.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart3406G02.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart3406G02.getBsoID(),gyID,"cf");
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            
                            falg3406G02 = false;
                        }
                        
                        if(partNumber.contains("3406011") || partNumber.contains("CQ67635B") || 
                                partNumber.contains("3406030") || partNumber.contains("3406424"))
                        {
                            mt[0] = newParentPart3406G02.getBsoID();
                        }
                    }
                    //End SS28
                    
//                    if(partNumber.contains("3406011") || partNumber.contains("CQ67635B") || 
//                            partNumber.contains("3406030") || partNumber.contains("3406424"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
                }
                
                ret.add(mt);
            }
            
            return ret;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new QMException(ex);
        }
    }
	
	/**
     * J6L���Ͳ��
     * @param gypart ��
     * @param mtree �Ӽ�
     * @param partNumber1000940 1000940����BSOID
     * @return
     * @throws QMException
     */
	private Collection chaifengybomJ6L(QMPartIfc gypart,Collection mtree, String partNumber1000940,
	        String partNumber10004101, HashMap map, String dwbs, ArrayList arrayList,
            String id3900G01, String id3100G00) throws QMException//SS25
    {
        try
        {
            String gyID = gypart.getBsoID();
            PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
            ArrayList ret = new ArrayList();
            
            QMPartIfc newParentPart1104G01=null;
            QMPartIfc newParentPart1119G01=null;
            QMPartIfc newParentPart1303G01=null;
            QMPartIfc newParentPart1311G01=null;
            QMPartIfc newParentPart3406G01=null;
            QMPartIfc newParentPart3509G01=null;
            //CCBegin SS63
            QMPartIfc ZCnewParentPart1104G01=null;
            QMPartIfc ZCnewParentPart1119G01=null;
            QMPartIfc ZCnewParentPart1303G01=null;
            QMPartIfc ZCnewParentPart1311G01=null;
            QMPartIfc ZCnewParentPart3406G01=null;
            QMPartIfc ZCnewParentPart3509G01=null;
            //CCEnd SS63
            
            boolean falg1104G01 = true;
            boolean falg1119G01 = true;
            boolean falg1303G01 = true;
            boolean falg1311G01 = true;
            boolean falg3406G01 = true;
            boolean falg3509G01 = true;
            
            for(Iterator iter=mtree.iterator();iter.hasNext();)
            {
                String[] mt=(String[])iter.next();
                String subid=mt[1];
                String parentID = mt[0];
                QMPartIfc spart=(QMPartIfc)ps.refreshInfo(subid,false);
                QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(parentID,false);
                String partNumber=spart.getPartNumber();
                
                //1�������֡����ֳ���ƽ̨��ֱ�ӹҵ��������ܳ��µ��߼��ܳɣ�
                //1000410��1303G02��1503G01��1600G00��1700G00��3523G01��3724G34ֱ�ӷŵ�1000940��
                if(partNumber.contains("1000410") || partNumber.contains("1303G02") || 
                        partNumber.contains("1503G01") || partNumber.contains("1600G00") || 
                        partNumber.contains("1700G00") || partNumber.contains("3523G01") || 
                        partNumber.contains("1308G02") || partNumber.contains("1313G02") || //SS23��������Ҳ�Ƿŵ�1000940��
                        partNumber.contains("3724G34") )
                {
                    mt[0] = partNumber1000940;
                }
                //CCBegin SS23
                /*��������Ҳ�Ƿŵ�1000940��
                //1308G02��1313G02 �ŵ�1000410��
                if(partNumber.contains("1308G02") || partNumber.contains("1313G02") )
                {
                    mt[0] = partNumber10004101;
                }
                */
                //CCEnd SS23
                //CCBegin SS25
                //3725020-A01 8203010-E06C 7925040-92W 7901012EA01 �ŵ�3900G01��
                if(partNumber.equalsIgnoreCase("3725020-A01") || partNumber.equalsIgnoreCase("8203010-E06C") || 
                        partNumber.equalsIgnoreCase("7925040-92W") || partNumber.equalsIgnoreCase("7901012EA01"))
                {
                    mt[0] = id3900G01;
                }
                
                //�������ͷ3103070��3100G00��
                if(partNumber.startsWith("3103070"))
                {
//                    mt[0] = id3100G00;
                    mt[0] = gyID;//SS32
                }
                //CCEnd SS25
                
                 //CCBegin SS33
//                if(partNumber.contains("2800010") || partNumber.contains("5000990") )
//                {
////                    System.out.println("gyID====gyID===="+gyID);
//                    mt[0] = gyID;
//                    mt[6] = "";
//                }
              //CCEnd SS33
                
//                1104G01 ��ֵ�1000940��1104130 1104250 1104071 CQ72316T5
                if(mt[9].contains("1104G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
//                System.out.println("parentPart.getPartNumber()-------------------------"+parentPart.getPartNumber());
                if(parentPart.getPartNumber().contains("1104G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1104G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1104G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1104G01.getBsoID();
                    //CCEnd SS63
//                    System.out.println("1104G01------------------------");
                    //Begin SS28
                    if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
                            partNumber.contains("1104071") || partNumber.contains("CQ72316T5"))
                    {
                        if(newParentPart1104G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1104G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1104G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1104G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1104G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1104G01.getBsoID(),gyID,"cf");
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            falg1104G01 = false;
                        }
                        
                        if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
                                partNumber.contains("1104071") || partNumber.contains("CQ72316T5"))
                        {
                            mt[0] = newParentPart1104G01.getBsoID();
                        }
                    }
                    //End SS28
                    
//                    if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
//                            partNumber.contains("1104071") || partNumber.contains("CQ72316T5"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
                }
                
//                1119G01 ��ֵ�1000940��1119030 1119035 1119038 1119038 1119050 1119053 1119081 1119105 
//                                        1119125 1119127 CQ1461040 Q1840816 T6769006A
                if(mt[9].contains("1119G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1119G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1119G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1119G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1119G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1119030") || partNumber.contains("1119035") || 
                            partNumber.contains("1119038") || partNumber.contains("1119038") || 
                            partNumber.contains("1119050") || partNumber.contains("1119053") || 
                            partNumber.contains("1119081") || partNumber.contains("1119105") || 
                            partNumber.contains("1119125") || partNumber.contains("1119127") || 
                            partNumber.contains("CQ1461040") || partNumber.contains("Q1840816") || 
                            partNumber.contains("T6769006A"))
                    {
                        if(newParentPart1119G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1119G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1119G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1119G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1119G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1119G01.getBsoID(),gyID,"cf");
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            
                            falg1119G01 = false;
                        }
                        
                        if(partNumber.contains("1119030") || partNumber.contains("1119035") || 
                                partNumber.contains("1119038") || partNumber.contains("1119038") || 
                                partNumber.contains("1119050") || partNumber.contains("1119053") || 
                                partNumber.contains("1119081") || partNumber.contains("1119105") || 
                                partNumber.contains("1119125") || partNumber.contains("1119127") || 
                                partNumber.contains("CQ1461040") || partNumber.contains("Q1840816") || 
                                partNumber.contains("T6769006A"))
                        {
                            mt[0] = newParentPart1119G01.getBsoID();
                        }
                    }
                    //End SS28
                    
//                    if(partNumber.contains("1119030") || partNumber.contains("1119035") || 
//                            partNumber.contains("1119038") || partNumber.contains("1119038") || 
//                            partNumber.contains("1119050") || partNumber.contains("1119053") || 
//                            partNumber.contains("1119081") || partNumber.contains("1119105") || 
//                            partNumber.contains("1119125") || partNumber.contains("1119127") || 
//                            partNumber.contains("CQ1461040") || partNumber.contains("Q1840816") || 
//                            partNumber.contains("T6769006A"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
                }
                
//                1303G01 ��ֵ�1000940��1303034 1303036 1303043 1303045 1303048 CQ1460825 CQ67660B Q1841245
                if(mt[9].contains("1303G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1303G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1303G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1303G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1303G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1303034") || partNumber.contains("1303036") || 
                            partNumber.contains("1303043") || partNumber.contains("1303045") || 
                            partNumber.contains("1303048") || partNumber.contains("CQ1460825") || 
                            partNumber.contains("CQ67660B") || partNumber.contains("Q1841245"))
                    {
                        if(newParentPart1303G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1303G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1303G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1303G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1303G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1303G01.getBsoID(),gyID,"cf");
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            
                            falg1303G01 = false;
                        }
                        
                        if(partNumber.contains("1303034") || partNumber.contains("1303036") || 
                                partNumber.contains("1303043") || partNumber.contains("1303045") || 
                                partNumber.contains("1303048") || partNumber.contains("CQ1460825") || 
                                partNumber.contains("CQ67660B") || partNumber.contains("Q1841245"))
                        {
                            mt[0] = newParentPart1303G01.getBsoID();
                        }
                    }
                    //End SS28
                    
//                    if(partNumber.contains("1303034") || partNumber.contains("1303036") || 
//                            partNumber.contains("1303043") || partNumber.contains("1303045") || 
//                            partNumber.contains("1303048") || partNumber.contains("CQ1460825") || 
//                            partNumber.contains("CQ67660B") || partNumber.contains("Q1841245"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
                }
                
//                1311G01 ��ֵ�1000940��1311021 1311021 1311022 1311023 1311031 1311046 1311055
//                                        1311060 CQ1460816(4) CQ1460820(1) CQ39610 CQ67616(3) CQ67630B(2) Q68622
                if(mt[9].contains("1311G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("1311G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1311G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1311G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1311G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("1311021") || partNumber.contains("1311021") || 
                            partNumber.contains("1311022") || partNumber.contains("1311023") || 
                            partNumber.contains("1311031") || partNumber.contains("1311046") || 
                            partNumber.contains("1311055") || partNumber.contains("1311060") || 
                            partNumber.contains("CQ39610") || partNumber.contains("Q68622") ||
                            partNumber.contains("CQ1460816") || partNumber.contains("CQ1460820") ||
                            partNumber.contains("CQ67616") || partNumber.contains("CQ67630B") )
                    {
                        if(newParentPart1311G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1311G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1311G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart1311G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart1311G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1311G01.getBsoID(),gyID,"cf");
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            
                            falg1311G01 = false;
                        }
                        
                        if(partNumber.contains("1311021") || partNumber.contains("1311021") || 
                                partNumber.contains("1311022") || partNumber.contains("1311023") || 
                                partNumber.contains("1311031") || partNumber.contains("1311046") || 
                                partNumber.contains("1311055") || partNumber.contains("1311060") || 
                                partNumber.contains("CQ39610") || partNumber.contains("Q68622"))
                        {
                            mt[0] = newParentPart1311G01.getBsoID();
                        }
                        if(partNumber.contains("CQ1460816"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart1311G01.getBsoID();
                            temp[2] = "4";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-4)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                        if(partNumber.contains("CQ1460820"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart1311G01.getBsoID();
                            temp[2] = "1";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-1)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                        if(partNumber.contains("CQ67616"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart1311G01.getBsoID();
                            temp[2] = "3";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-3)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                        if(partNumber.contains("CQ67630B"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart1311G01.getBsoID();
                            temp[2] = "2";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-2)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                    }
                    //End SS28
                    
//                    if(partNumber.contains("1311021") || partNumber.contains("1311021") || 
//                            partNumber.contains("1311022") || partNumber.contains("1311023") || 
//                            partNumber.contains("1311031") || partNumber.contains("1311046") || 
//                            partNumber.contains("1311055") || partNumber.contains("1311060") || 
//                            partNumber.contains("CQ39610") || partNumber.contains("Q68622"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
//                    
//                    if(partNumber.contains("CQ1460816"))
//                    {
//                        String temp[] = mt;
//                        String quantity = mt[2];
//                        Float count = Float.valueOf(quantity);
//                        temp[2] = "4";
//                        ret.add(temp);
//                        if(!((count-4)>0))
//                        {
//                            continue;
//                        }
//                    }
//                    
//                    if(partNumber.contains("CQ1460820"))
//                    {
//                        String temp[] = mt;
//                        String quantity = mt[2];
//                        Float count = Float.valueOf(quantity);
//                        temp[2] = "1";
//                        ret.add(temp);
//                        if(!((count-1)>0))
//                        {
//                            continue;
//                        }
//                    }
//                    
//                    if(partNumber.contains("CQ67616"))
//                    {
//                        String temp[] = mt;
//                        String quantity = mt[2];
//                        Float count = Float.valueOf(quantity);
//                        temp[2] = "3";
//                        ret.add(temp);
//                        if(!((count-3)>0))
//                        {
//                            continue;
//                        }
//                    }
//                    
//                    if(partNumber.contains("CQ67630B"))
//                    {
//                        String temp[] = mt;
//                        String quantity = mt[2];
//                        Float count = Float.valueOf(quantity);
//                        temp[2] = "2";
//                        ret.add(temp);
//                        if(!((count-2)>0))
//                        {
//                            continue;
//                        }
//                    }
                }
                
//                3406G01 ��ֵ�1000940��3406011 CQ67635B(1��
                if(mt[9].contains("3406G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("3406G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart3406G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart3406G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart3406G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("3406011") || partNumber.contains("CQ67635B") )
                    {
                        if(newParentPart3406G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart3406G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg3406G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart3406G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart3406G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart3406G01.getBsoID(),gyID,"cf");
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            
                            falg3406G01 = false;
                        }
                        
                        if(partNumber.contains("3406011"))
                        {
                            mt[0] = newParentPart3406G01.getBsoID();
                        }
                        if(partNumber.contains("CQ67635B"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart3406G01.getBsoID();
                            temp[2] = "1";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-1)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                    }
                    //End SS28
                    
//                    if(partNumber.contains("3406011"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
//                    
//                    if(partNumber.contains("CQ67635B"))
//                    {
//                        String temp[] = mt;
//                        String quantity = mt[2];
//                        Float count = Float.valueOf(quantity);
//                        temp[2] = "1";
//                        ret.add(temp);
//                        if(!((count-1)>0))
//                        {
//                            continue;
//                        }
//                    }
                }
                
//                3509G01 ��ֵ�1000940��3509284 3509381
                if(mt[9].contains("3509G01"))
                {
//                    continue;
                	//CCBegin SS56
//                  mt[0]=gypart.getBsoID();
              	//CCEnd SS56
                    if(arrayList.contains(mt[1]))
                    {
                        String[] str=new String[10];
                        Collection col = getGyBomCFHistoryBySourceID(mt[1],"cf");
                        Iterator ite = col.iterator();
                        String cfID = null;
                        if(ite.hasNext())
                        {
                            String[] temp=(String[])ite.next();
                            cfID = temp[1];
                        }
                        if(cfID != null)
                        {
                            QMPartIfc cfpart=(QMPartIfc)ps.refreshInfo(subid,false);
                            str[0]=partNumber1000940;
                            str[1]=cfID;
                            str[2]=mt[2];
                            str[3]=mt[3];
                            str[4]=dwbs;
                            str[5]=mt[5];
                            str[6]=mt[6];
                            str[7]=mt[7];
                            str[8]=mt[8];
                            str[9]=cfpart.getPartNumber();
                            ret.add(str);
                        }
                    }
                }
                if(parentPart.getPartNumber().contains("3509G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart3509G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart3509G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart3509G01.getBsoID();
                    //CCEnd SS63
                    //Begin SS28
                    if(partNumber.contains("3509284") || partNumber.contains("3509381"))
                    {
                        if(newParentPart3509G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart3509G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg3509G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            String[] str=new String[10];
                            str[0]=partNumber1000940;
                            str[1]=newParentPart3509G01.getBsoID();
                            str[2]=strs[2];
                            str[3]=strs[3];
                            str[4]=dwbs;
                            str[5]=strs[5];
                            str[6]=strs[6];
                            str[7]=strs[7];
                            str[8]=strs[8];
                            str[9]=newParentPart3509G01.getPartNumber();
                            ret.add(str);
                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart3509G01.getBsoID(),gyID,"cf");
                            //ԭ���߼��ܳɺ�   1104G01-382 �ŵ�һ������
//                            String[] strs1 = (String[])map.get(parentPart.getPartNumber());
//                            String[] str1=new String[10];
//                            str1[0]=gypart.getBsoID();
//                            str1[1]=parentPart.getBsoID();
//                            str1[2]=strs1[2];
//                            str1[3]=strs1[3];
//                            str1[4]=dwbs;
//                            str1[5]=strs1[5];
//                            str1[6]=strs1[6];
//                            str1[7]=strs1[7];
//                            str1[8]=strs1[8];
//                            str1[9]=parentPart.getPartNumber();
//                            ret.add(str1);
                            
                            falg3509G01 = false;
                        }
                        
                        if(partNumber.contains("3509284") || partNumber.contains("3509381"))
                        {
                            mt[0] = newParentPart3509G01.getBsoID();
                        }
                    }
                    //End SS28
                    
//                    if(partNumber.contains("3509284") || partNumber.contains("3509381"))
//                    {
//                        mt[0] = partNumber1000940;
//                    }
                }
                
                ret.add(mt);
            }
            
            return ret;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new QMException(ex);
        }
    }
    //CCEnd SS20
    

	/**
	 * ���ݹ���Է��ϸ����ļ����и�����������ԭ���������Ϊ���������������������ܣ�
	 * 
	 * @param mtree
	 * @return
	 * @throws QMException
	 */
	private Collection renameGyBom(Collection mtree) throws QMException
	{
		try
		{
			Collection co=new ArrayList();
			HashMap map=new HashMap();
			Collection rtree=new ArrayList();
			for(Iterator iter=mtree.iterator();iter.hasNext();)
			{
				String[] mt=(String[])iter.next();
				String parentid=mt[0];
				String childid=mt[1];
//				System.out.println("����id======="+parentid);
//				System.out.println("�Ӽ�id======="+childid);
				if(!co.contains(parentid))
				{
					co.add(parentid);
				}
				if(!co.contains(childid))
				{
					co.add(childid);
				}
			}
			for(Iterator iter=co.iterator();iter.hasNext();)
			{
				String partid=(String)iter.next();
//				System.out.println("���id====="+partid);
				//CCBegin SS37
				if(partid != null && !partid.equals(""))
				{
				    QMPartIfc npart=renameGyParts(partid);
	                map.put(partid,npart.getBsoID());
				}
				//CCEnd SS37
			}
			for(Iterator iter=mtree.iterator();iter.hasNext();)
			{
				String[] mt=(String[])iter.next();
				String pid=mt[0];
				String cid=mt[1];
				String npid=(String)map.get(pid);
				String ncid=(String)map.get(cid);
				if(npid!=null)
				{
					mt[0]=npid;
				}
				if(ncid!=null)
				{
					mt[1]=ncid;
				}
				rtree.add(mt);
			}
			return rtree;

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	
	/**
	 * ���ݹ���Է��ϸ����ļ����и�����������ԭ���������Ϊ������ʻ�ң�
	 * 
	 * @param mtree
	 * @return
	 * @throws QMException
	 */
	private Collection renameGyjssBom(Collection mtree,String newNumber) throws QMException
	{
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getService("PersistService");
			Collection co=new ArrayList();
			HashMap map=new HashMap();
			Collection rtree=new ArrayList();
			for(Iterator iter=mtree.iterator();iter.hasNext();)
			{
				String[] mt=(String[])iter.next();
				String parentid=mt[0];
				String childid=mt[1];
				if(!co.contains(parentid))
				{
					co.add(parentid);
				}
				if(!co.contains(childid))
				{
					co.add(childid);
				}
			}
			for(Iterator iter=co.iterator();iter.hasNext();)
			{
				String partid=(String)iter.next();
				QMPartIfc spart=(QMPartIfc)ps.refreshInfo(partid,false);
				if(spart.getPartNumber().length()>7&&spart.getPartNumber().substring(0,7).equals("5000010"))
				{
					QMPartIfc npart=createNewGYPart(spart,newNumber,spart.getPartName());
					map.put(partid,npart.getBsoID());
				}
				else
				{
					//CCBegin SS37
					if(partid != null && !partid.equals(""))
				    {
				        QMPartIfc npart=renameGyParts(partid);
	              map.put(partid,npart.getBsoID());
				    }
				    //CCEnd SS37
				}
			}
			for(Iterator iter=mtree.iterator();iter.hasNext();)
			{
				String[] mt=(String[])iter.next();
				String pid=mt[0];
				String cid=mt[1];
				String npid=(String)map.get(pid);
				String ncid=(String)map.get(cid);
				if(npid!=null)
				{
					mt[0]=npid;
				}
				if(ncid!=null)
				{
					mt[1]=ncid;
				}
				rtree.add(mt);
			}
			return rtree;

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}

	/**
	 * �㲿�����������Ϊ�¼���
	 * 
	 * @param ppartid
	 * @return
	 * @throws QMException
	 */
	private QMPartIfc renameGyParts(String ppartid) throws QMException
	{
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc ppart=(QMPartIfc)ps.refreshInfo(ppartid,false);
			QMPartIfc npart=null;
			String num=ppart.getPartNumber();
			if(num.length()>7&&num.substring(0,7).equals("1100G01"))
			{
				String newnumber=ppart.getPartNumber()+"-01";
				npart=createNewGYPart(ppart,newnumber,ppart.getPartName());
			}
			if(num.length()>7&&num.substring(0,7).equals("1109G02"))
			{
				String newnumber=ppart.getPartNumber()+"-01";
				npart=createNewGYPart(ppart,newnumber,ppart.getPartName());
			}
			if(num.length()>7&&num.substring(0,7).equals("1311G01"))
			{
				String newnumber=ppart.getPartNumber()+"-02";
				npart=createNewGYPart(ppart,newnumber,ppart.getPartName());
			}
			if(num.length()>7&&num.substring(0,7).equals("1700G00"))
			{
				String newnumber=ppart.getPartNumber()+"-01";
				npart=createNewGYPart(ppart,newnumber,ppart.getPartName());
			}
			if(num.length()>7&&num.substring(0,7).equals("2802G02"))
			{
				String newnumber=ppart.getPartNumber()+"-01";
				npart=createNewGYPart(ppart,newnumber,ppart.getPartName());
			}
			if(num.length()>7&&num.substring(0,7).equals("3600G00"))
			{
				String newnumber=ppart.getPartNumber()+"-01";
				npart=createNewGYPart(ppart,newnumber,ppart.getPartName());
			}
			if(num.length()>7&&num.substring(0,7).equals("8108G01"))
			{
				String newnumber=ppart.getPartNumber()+"-01";
				npart=createNewGYPart(ppart,newnumber,ppart.getPartName());
			}
			if(npart!=null)
			{
				return npart;
			}
			else
			{
				return ppart;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}

	/**
	 * ���Ϊһ���¼��������Ӽ�����
	 * 
	 * @param source
	 * @param num
	 * @param name
	 * @return
	 * @throws QMException
	 */
	private QMPartIfc createNewGYPart(QMPartIfc source,String num,String name) throws QMException
	{
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			QMQuery query=new QMQuery("QMPartMaster");
			query.addCondition(new QueryCondition("partNumber","=",num));
			Collection co=ps.findValueInfo(query,false);
			if(co.size()>0)
			{
				QMPartMasterIfc mast=(QMPartMasterIfc)co.toArray()[0];
				QMPartIfc part=sps.getPartByMasterID(mast.getBsoID());
				// �����㲿��������ʷ
				createGyBomReNameHistory(source.getBsoID(),part.getBsoID());
				return part;
			}
			else
			{
				UserInfo user=getCurrentUserInfo();
				String lifeCyID=PublishHelper.getLifeCyID("���������㲿����������");
				QMPartIfc part=new QMPartInfo();
				part.setPartNumber(num);
				part.setPartName(name);
				part.setLifeCycleState(LifeCycleState.toLifeCycleState("PREPARING"));
				part.setViewName(source.getViewName());
				part.setDefaultUnit(source.getDefaultUnit());
				part.setProducedBy(source.getProducedBy());
				part.setPartType(source.getPartType());
				part.setIterationCreator(user.getBsoID());
				part.setIterationModifier(user.getBsoID());
				part.setIterationNote("");
				part.setOwner("");
				part=(QMPartInfo)PublishHelper.assignFolder(part,"\\Root\\���պϼ�");
				part.setLifeCycleTemplate(lifeCyID);
				part=sps.savePart(part);
				QMPartIfc qp=(QMPartIfc)ps.refreshInfo(part.getBsoID());
				QMQuery q=new QMQuery("PartUsageLink");
				q.addCondition(new QueryCondition("rightBsoID","=",source.getBsoID()));
				Collection coo=ps.findValueInfo(q);
				for(Iterator iter=coo.iterator();iter.hasNext();)
				{
					PartUsageLinkIfc link=(PartUsageLinkIfc)iter.next();
					PartUsageLinkIfc nlink=new PartUsageLinkInfo();
					nlink.setLeftBsoID(link.getLeftBsoID());
					nlink.setRightBsoID(qp.getBsoID());
					nlink.setDefaultUnit(link.getDefaultUnit());
					nlink.setQuantity(link.getQuantity());
					sps.savePartUsageLink(nlink);
				}
				//CCBegin SS38
				//CCBegin SS49
//				TechnicsRouteListIfc trIfc = createRouteList(qp.getMasterBsoID());
				TechnicsRouteListIfc trIfc = createRouteList(qp);
				//CCEnd SS49
				createListRoutePartLink(trIfc ,qp ,source);
				//CCEnd SS38
				// �����㲿��������ʷ
				createGyBomReNameHistory(source.getBsoID(),qp.getBsoID());
				return qp;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}

	/**
	 * ���Ϊһ�����㲿���������Ӽ�
	 * 
	 * @param source
	 * @param num
	 * @param name
	 * @return
	 * @throws QMException
	 */
	private QMPartIfc createNewTGYPart(QMPartIfc source,String num,String name) throws QMException
	{
		try
		{

			if(num.endsWith("-ZC")){
				System.out.println("num===="+num);
			}
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			QMQuery query=new QMQuery("QMPartMaster");
			query.addCondition(new QueryCondition("partNumber","=",num));
			Collection co=ps.findValueInfo(query,false);
			if(co.size()>0)
			{
				QMPartMasterIfc mast=(QMPartMasterIfc)co.toArray()[0];
				QMPartIfc part=getPartByMasterID(mast.getBsoID());
				return part;
			}
			else
			{
				UserInfo user=getCurrentUserInfo();
				String lifeCyID=PublishHelper.getLifeCyID("���������㲿����������");
				QMPartIfc part=new QMPartInfo();
				part.setPartNumber(num);
				part.setPartName(name);
				part.setLifeCycleState(LifeCycleState.toLifeCycleState("PREPARING"));
				part.setViewName(source.getViewName());
				part.setDefaultUnit(source.getDefaultUnit());
				part.setProducedBy(source.getProducedBy());
				part.setPartType(source.getPartType());
				part.setIterationCreator(user.getBsoID());
				part.setIterationModifier(user.getBsoID());
				part.setIterationNote("");
				part.setOwner("");
				part=(QMPartInfo)PublishHelper.assignFolder(part,"\\Root\\���պϼ�");
				part.setLifeCycleTemplate(lifeCyID);
				part=sps.savePart(part);
				QMPartIfc qp=(QMPartIfc)ps.refreshInfo(part.getBsoID());
				System.out.println("qp====="+qp.getPartNumber());
				//CCBegin SS38
				//CCBegin SS49
//				TechnicsRouteListIfc trIfc = createRouteList(qp.getMasterBsoID());
//				TechnicsRouteListIfc trIfc = createRouteList(qp);parentPartMasterID,Vector saveVec
				//CCEnd SS49
//				createListRoutePartLink(trIfc ,qp ,source);
				//CCEnd SS38
				//CCBegin SS63
				createZCORFCTechnics(qp.getBsoID());
				//CCEnd SS63
				return qp;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}

	/**
	 * �����㲿���Ĳ����ʷ
	 * 
	 * @param sourceID
	 * @param cfID
	 * @param type
	 *            ��ֵ����ͣ�cf����һ���ܳɲ�ֳɶ����tq�����㲿����һ���ܳ�����ȡ������ܳ��С�
	 * @param parentID
	 * @throws QMException
	 */
	public void createGyBomCFHistory(String sourceID,String cfID,String parentID,String type) throws QMException
	{

		Connection conn=null;
		Statement st=null;
		try
		{
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			String sql="select sourceID,cfID,parentID from GYBomCFHistory where sourceID='"+sourceID+"' and cfID='"
				+cfID+"' and type='"+type+"' and parentID='"+parentID+"' order by sourceID";

			ResultSet rs=st.executeQuery(sql);
			boolean flag=false;
			while(rs.next())
			{
				flag=true;
			}
			if(!flag)
			{
				String mtsql="INSERT INTO GYBomCFHistory (sourceID,cfID,parentID,type,createTime) VALUES ";
				String tempsql="";
				String sendData=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				tempsql=mtsql+"('"+sourceID+"', '"+cfID+"', '"+parentID+"','"+type+"',to_date('"+sendData
					+"','yyyy-mm-dd hh24:mi:ss'))";
				st.executeQuery(tempsql);

			}
			// �ر�Statement
			st.close();
			// �ر����ݿ�����
			// conn.commit();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new QMException(e);
		}
		finally
		{
			try
			{
				st.close();
				conn.close();
			}
			catch(SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * �����㲿�������Ϊ��ʷ
	 * 
	 * @param sourceID
	 * @param rnID
	 * @throws QMException
	 */
	public void createGyBomReNameHistory(String sourceID,String rnID) throws QMException
	{

		Connection conn=null;
		Statement st=null;
		try
		{
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			String sql="select sourceID,rnID from GYBomRenameHistory where sourceID='"+sourceID+"' and rnID='"+rnID
				+"' order by sourceID";
			ResultSet rs=st.executeQuery(sql);
			boolean flag=false;
			if(rs.next())
			{
				flag=true;
			}
			if(!flag)
			{
				String mtsql="INSERT INTO GYBomRenameHistory (sourceID,rnID,createTime) VALUES ";
				String tempsql="";
				String sendData=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
				tempsql=mtsql+"('"+sourceID+"', '"+rnID+"', to_date('"+sendData+"','yyyy-mm-dd hh24:mi:ss'))";
				st.executeQuery(tempsql);
			}
			// �ر�Statement
			st.close();
			// �ر����ݿ�����
			// conn.commit();
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new QMException(e);
		}
		finally
		{
			try
			{
				st.close();
				conn.close();
			}
			catch(SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * �����㲿��ID�õ��㲿���Ĳ����ʷ
	 * 
	 * @param sourceID
	 * @param type
	 *            ����
	 * @return
	 * @throws QMException
	 */
	public Collection getGyBomCFHistoryBySourceID(String sourceID,String type) throws QMException
	{

		Connection conn=null;
		Statement st=null;
		try
		{
			Collection co=new ArrayList();
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			String sql="";
			if(type!=null&&type.length()>0)
			{
				sql="select sourceID,cfID,parentID from GYBomCFHistory where sourceID='"+sourceID+"'"+"and type='"+type
					+"'"+" order by cfID";
			}
			else
			{
				sql="select sourceID,cfID,parentID from GYBomCFHistory where sourceID='"+sourceID+"'"+" order by cfID";
			}
			ResultSet rs=st.executeQuery(sql);
			while(rs.next())
			{
				String sID=rs.getString(1);
				String cID=rs.getString(2);
				String pID=rs.getString(3);
				String[] mt=new String[3];
				mt[0]=sID;
				mt[1]=cID;
				mt[2]=pID;
				co.add(mt);
			}
			// �ر�Statement
			st.close();
			// �ر����ݿ�����
			conn.close();
			return co;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new QMException(e);
		}
		finally
		{
			try
			{
				st.close();
				conn.close();
			}
			catch(SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * �����㲿����ID�õ��㲿���ĸ�����ʷ
	 * 
	 * @param sourceID
	 * @return
	 * @throws QMException
	 */
	public Collection getGyBomReNameHistoryBySourceID(String sourceID) throws QMException
	{

		Connection conn=null;
		Statement st=null;
		try
		{
			Collection co=new ArrayList();
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			String sql="select sourceID,rnID from GYBomRenameHistory where sourceID='"+sourceID+"' order by rnID";
			ResultSet rs=st.executeQuery(sql);
			if(rs.next())
			{
				String sID=rs.getString(1);
				String rID=rs.getString(2);
				String[] mt=new String[2];
				mt[0]=sID;
				mt[1]=rID;
				co.add(mt);
			}
			// �ر�Statement
			st.close();
			// �ر����ݿ�����
			conn.close();
			return co;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new QMException(e);
		}
		finally
		{
			try
			{
				st.close();
				conn.close();
			}
			catch(SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ���汾 ������bom�Ͻڵ�汾����һ���汾������ԭ���汾�ǡ�C����ѡ�񽵰汾֮���Ϊ��D���汾
	 */
	public String upgradeVersion(String linkid,String id,String carModelCode,String dwbs) throws QMException
	{
//		System.out.println("come in upgradeVersion");
		String str="";
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			StandardPartService spService=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			VersionControlService vcService=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(id);
			QMPartMasterIfc partmaster=(QMPartMasterIfc)ps.refreshInfo(part.getMasterBsoID());
			Collection co=vcService.allVersionsOf(partmaster);
			Iterator itea=co.iterator();
			QMPartIfc npart=null;
			QMPartIfc nnpart=null;
			while(itea.hasNext())
			{
				QMPartIfc partIfc=(QMPartIfc)itea.next();
				if(part.getBsoID().equals(partIfc.getBsoID()))
				{
					nnpart=npart;
				}
				npart=partIfc;
			}

			if(nnpart==null)
			{
				return "[{δ���ҵ���ǰ�Ӽ���һ�汾��}]";
			}

			// ���¹������Ӽ�id�滻����һ���㲿��id
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			try
			{
				conn=PersistUtil.getConnection();
				stmt=conn.createStatement();
				//CCBegin SS70
//				stmt.executeQuery("update GYBomStructure set childPart='"+nnpart.getBsoID()
				stmt.executeQuery("update GYBomStructure set childPart='"+nnpart.getBsoID()+ "',bz1='" + "red"
					//CCEnd SS70
					+"' where effectCurrent='0' and childPart='"+part.getBsoID()+"' and dwbs='"+dwbs+"'");
System.out.println("sql���===update GYBomStructure set childPart='"+nnpart.getBsoID()+ "',bz1='" + "red"
					+"' where effectCurrent='0' and childPart='"+part.getBsoID()+"' and dwbs='"+dwbs+"'");
				// �ر�Statement
				stmt.close();
				// �ر����ݿ�����
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(rs!=null)
					{
						rs.close();
					}
					if(stmt!=null)
					{
						stmt.close();
					}
					if(conn!=null)
					{
						conn.close();
					}
				}
				catch(SQLException ex1)
				{
					ex1.printStackTrace();
				}
			}

			// ɾ����ǰ�汾�Ӽ�����
			Collection ids=new ArrayList();
			ids=bianliGYBomid(ids,part.getBsoID(),carModelCode,dwbs);
			deleteGYBom(ids);

			// ���� ��һ�汾�Ӽ�����
			Collection mtree=new ArrayList();
			Vector linkvec=new Vector();
			mtree=getBomLinks(mtree,nnpart,carModelCode,dwbs,linkvec);
			//CCBegin SS15
			saveGYBom(mtree, true, dwbs);//SS21
			//CCEnd SS15

			// ��ȡ��ǰ�㲿�������Ӽ����½ṹ
			str=getGYBomAll(linkid,nnpart.getBsoID(),carModelCode,dwbs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return str;
	}
	
	//Begin SS41
	
	///////////////////////////////////////////////////////////////
	/**
     * ����������ʼ����BOM
     */
    public void initGYZCBom(String partID,String gyID,String dwbs) throws QMException
    {
    	
//        System.out.println("dwbs   ============ "+dwbs);
        try
        {
        	long t1 = System.currentTimeMillis();
            // �������߼��ܳ�
            String partNumberRuleFDJ=RemoteProperty.getProperty("gybomlistfdj");
            String partNumberRuleFDJs[] = partNumberRuleFDJ.split(",");
            ArrayList ljzcArr = new ArrayList();//�߼��ܳɼ���
            for(int i=0; i<partNumberRuleFDJs.length; i++)
            {
//                System.out.println("partNumberRuleFDJs[i]  ======  " + partNumberRuleFDJs[i]);
                ljzcArr.add(partNumberRuleFDJs[i]);
            }
            // ����
            //CCBegin SS62
//            String partNumberRuleZC=RemoteProperty.getProperty("gybomlistzc");
//            String partNumberRuleZCs[] = partNumberRuleZC.split(",");
//            ArrayList zcArr = new ArrayList();
//            for(int i=0; i<partNumberRuleZCs.length; i++)
//            {
////                System.out.println("partNumberRuleZCs[i]  ======  " + partNumberRuleZCs[i]);
//                zcArr.add(partNumberRuleZCs[i]);
//            }
            //CCEnd SS62
            
            PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
            //CCBegin SS48
            searchAndCreateTechnics(partID);
			//CCEnd SS48
            QMPartIfc part=(QMPartIfc)ps.refreshInfo(partID);
            QMPartIfc gypart=(QMPartIfc)ps.refreshInfo(gyID);
            ArrayList childrenPart = new ArrayList();
            ArrayList existedList = new ArrayList();
            ArrayList ohters = new ArrayList();//���߼��ܳɼ���
            Collection ytree=new ArrayList();//��¼��ǩ�б�ɫ�������
            Vector linkvec = new Vector();//������ϸо�ûʲôʵ����;
            //�Ȼ�ȡ��һ���Ӽ�����Ҫ��ȡ���������д����ͬʱ��һ��ļ��ĸ���������Ϊ����bom�ĸ�
            StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
            VersionControlService vcservice=(VersionControlService)EJBServiceHelper
                .getService("VersionControlService");
            JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
            PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
            Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);//�����Ӽ�
            //CCBegin SS62
            ArrayList zcArr = new ArrayList();
            zcArr = getZCFirstLevelPart(linkcoll,dwbs);//����Ϊ����һ��������
            //CCEnd SS62
            String num410 = "";
            ArrayList childList3900G01 = new ArrayList();
//            System.out.println("ʹ�ýṹ��С======"+linkcoll.size());
            //CCBeign SS60
            //����������������ļ���
            Vector vecall = new Vector();
            int level = 1;
            //CCEnd SS60
            if(linkcoll!=null&&linkcoll.size()>0)
            {
                Iterator iter=linkcoll.iterator();
//                QMPartMasterIfc partMaster=null;
//                Vector tempResult=new Vector();
                QMPartIfc subpart=null;
//                Vector tempvec=new Vector();
//                ArrayList array = new ArrayList();//SS20
                while(iter.hasNext())
                {
                    Object obj[]=(Object[])iter.next();
                    PartUsageLinkIfc link=(PartUsageLinkIfc)obj[0];
                    if(obj[1] instanceof QMPartIfc)
                    	subpart=(QMPartIfc)obj[1];
//                    if(obj[1] instanceof QMPartMasterIfc){
//                    	System.out.println("----========"+((QMPartMasterIfc)obj[1]).getPartNumber());
//	                    QMPartIfc pifc = getPartByMasterID(((QMPartMasterIfc)obj[1]).getBsoID());
//	                    System.out.println("pifc========"+pifc);
//	                    if(pifc!=null)
//	                    System.out.println("bianhaowei========"+pifc.getPartNumber());
//                    }
                    
                    if(subpart != null)
                    {
                    	//CCBegin SS60
                    	//���浥�������ļ���
                    	Vector veceach = new Vector();
                    	//CCEnd SS60
                        if(subpart.getPartNumber().startsWith("1000410"))
                        {
                            num410 = subpart.getPartNumber();
                        }
                        String[] mt=new String[10];
                        mt[0]=gyID;
                        mt[1]=subpart.getBsoID();
                        mt[2]=String.valueOf(link.getQuantity());
                        mt[3]="";
                        mt[4]=dwbs;
                        mt[5]="0";
                        mt[6]="";
                        mt[7]="";
                        mt[8]=link.getDefaultUnit().getDisplay();
                        mt[9]=subpart.getPartNumber();
                        //CCBegin SS60
                        Object[] objlink = new Object[7];
                        objlink[0] = gyID;//����id
                        objlink[1] = gypart.getPartNumber();//�������
                        objlink[2] = subpart.getBsoID();//�Ӽ�id
                        objlink[3] = subpart.getPartNumber();//�Ӽ����
                        objlink[4] = link.getQuantity();//�����֧������ float
                        objlink[5] = link;//������ϵ�����Ի�ȡ������ PartUsageLinkIfc 
                        objlink[6] = level;//�㼶��ϵ int��
                        vecall.add(objlink);
//                        System.out.println(objlink[3]+"======ʹ������===="+objlink[4]);
                        //CCEnd SS60
                        boolean flag = false;
                        //�ж��Ƿ��߼��ܳ�,���Ϊ�߼��ܳɣ���ô�����������
                        for(int i=0; i<ljzcArr.size(); i++)
                        {
                            String str = (String)ljzcArr.get(i);
                            if(subpart.getPartNumber().contains(str))
                            {
                                flag = true;
                                break;
                            }
                        }
                        if(!flag)
                        {
                            for(int i=0; i<zcArr.size(); i++)
                            {
                                String str = (String)zcArr.get(i);
                                if(subpart.getPartNumber().contains(str))
                                {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        //���߼��ܳɺ����������ڼ�����
//                        System.out.println("subpart.getPartNumber() ======= " + subpart.getPartNumber());
                        if(flag)
                        {
//                            System.out.println("1111111111111");
                            ytree.add(mt);
                            childrenPart.add(mt);
                        }
                        else
                        {
//                            System.out.println("2222222222222222");
                            ohters.add(mt);
                        }
                        //��ȡ�Ӽ�
                        //CCBegin SS60
//                        getBomLinks(gyID, childrenPart, subpart, dwbs, linkvec, flag, ljzcArr, zcArr, ohters, childList3900G01);
                        getBomLinks(gyID, childrenPart, subpart, dwbs, linkvec, flag, ljzcArr, zcArr, ohters, childList3900G01,vecall,link.getQuantity(),level);
                        //CCEnd SS60
                    }
                }
            }
            
            //CCBegin SS56
            QMPartIfc part940=createGYPart(num410, childrenPart, dwbs);//����1000940
            // ���������빤�պϼ�����
            String[] mt940=new String[]{gyID,part940.getBsoID(),"1",gypart.getPartNumber(),dwbs,"0","","","��",
            		part940.getPartNumber()};
            childrenPart.add(mt940);
            Collection ntree=chaifengybom(gypart, childrenPart, dwbs, part, ohters);//SS20
//            for(Iterator iit = ntree.iterator();iit.hasNext();){
//            	String[] nn = (String[])iit.next();
//            	if(nn[9].contains("1119G01")){
//            		System.out.println("����id========"+nn[0]);
//            		System.out.println("�Ӽ�id========"+nn[1]);
//            	}
//            }
            //CCEnd SS56
//            System.out.println("childrenPart.size()======"+childrenPart.size());
//    		System.out.println("ohters.size()======"+ohters.size());
//    		for(Iterator iter = childrenPart.iterator();iter.hasNext();){
//    			String[] mt=(String[])iter.next();
//                String subid=mt[1];
//                String parentID = mt[0];
//                String subpartnumber = mt[9];
//                QMPartIfc spart=(QMPartIfc)ps.refreshInfo(subid,false);
//                QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(parentID,false);
//                if(parentPart.getPartNumber().contains("1104G01")){
//                	System.out.println("333��������������������������������");
//                	System.out.println("�������===="+parentPart.getPartNumber());
//                	System.out.println("�Ӽ����===="+spart.getPartNumber());
//                }
//                if(spart.getPartNumber().contains("1104G01")){
//                	System.out.println("444��������������������������������");
//                	System.out.println("�������===="+parentPart.getPartNumber());
//                	System.out.println("�Ӽ����===="+spart.getPartNumber());
//                }
//    		}
//            System.out.println("����ǰ��С==="+childrenPart.size());
//            System.out.println("����ǰ����==="+childrenPart);
            //����Ϊ����·�ߺ��������ϣ�Ҳ�����޹�
            ArrayList list=new ArrayList();
            ArrayList ljzcArrOhter=new ArrayList();
            ArrayList zcArrOhter=new ArrayList();
            //�Ѿ����ڵĹ���BOM�ļ��Ĵ���
            for(int i=0; i<childrenPart.size(); i++)
            {
                String[] mt = (String[])childrenPart.get(i);
                
                
                for(int j=0; j<ljzcArr.size(); j++)
                {
                    String str = (String)ljzcArr.get(j);
                    if(mt[9].contains(str))
                    {
                        mt[0] = gyID;
                        ljzcArrOhter.add(mt);
                    }
                }
                for(int j=0; j<zcArr.size(); j++)
                {
                    String str = (String)zcArr.get(j);
                    if(mt[9].contains(str))
                    {
                        mt[0] = gyID;
                        zcArrOhter.add(mt);
                    }
                }
                
                if(!getEffect(mt[0],"",dwbs).equals("0"))
                {
//                    System.out.println("�Ѿ�����" + mt[9]);
                    ohters.add(mt);
                    childrenPart.remove(mt);
                }
                else
                {
                    list.add(mt);
                }
            }
//            System.out.println("�����ڵĹ���BOM���˺��С==="+list.size());
//            childrenPart = list;
            //���ݹ���·�߹���
            HashMap map = new HashMap();
            HashMap partMap = new HashMap();
            //��Ź���·�߹���ʱ��չ�����Ӽ�
            ArrayList gyList = new ArrayList();
//            System.out.println("�����ڵĹ���BOM���˺�==="+childrenPart);
            for(int i=0; i<list.size(); i++)
            {
                String[] mt1 = (String[])list.get(i);
                ArrayList arr = (ArrayList)partMap.get(mt1[0]);
                if(arr == null)
                {
                    arr = new ArrayList();
                }
                arr.add(mt1);
                partMap.put(mt1[0], arr);
                
                Integer inte = (Integer)map.get(mt1[1]);
                if(inte == null)
                {
                    inte = new Integer(0);
                }
                inte = inte + 1;
                map.put(mt1[1], inte);
                QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(mt1[0],false);
                QMPartIfc subpart=(QMPartIfc)ps.refreshInfo(mt1[1],false);
                if(!parentPart.getBsoID().equals(gyID))
                {
                	//���ݹ���·�߹�������㲿��
                    if(!gylxFiltration(dwbs,parentPart,subpart))
                    {
                        gyList.add(mt1);
                        childrenPart.remove(mt1);
                        continue;//SS43
                    }
                }
                
            }
//            System.out.println("���ղ����ϴ�С==="+gyList.size());
            for(int i=0; i<gyList.size(); i++)
            {
                String[] mt = (String[])gyList.get(i);
                removeParts(childrenPart, mt, map, partMap);
            }
            //�������ڹ��ˣ������ϵ�׷�ݣ�
            for(int i=0; i<childrenPart.size(); i++)
            {
                String[] mt = (String[])childrenPart.get(i);
                QMPartIfc subpart=(QMPartIfc)ps.refreshInfo(mt[1],false);
                
                subpart = filterLifeState(subpart);
                if(subpart == null)
                {
                    childrenPart.remove(mt);
                }
                else
                {
                    mt[1] = subpart.getBsoID();
                }
            }
//            System.out.println("�������ڹ��˺��С==="+childrenPart.size());
            //CCBegin SS56
//            QMPartIfc gyPartIfc=createGYPart(num410, childrenPart, dwbs);//����1000940
            //CCEnd SS56
            //�������ļ��е����ù����ܳ���ӵ�1000940��
            for(int i=0; i<childrenPart.size(); i++)
            {
                String[] mt = (String[])childrenPart.get(i);
                for(int j=0; j<ljzcArr.size(); j++)
                {
                    String str = (String)ljzcArr.get(j);
                    if(mt[9].contains(str))
                    {
                        mt[0] = part940.getBsoID();
                    }
                }
            }
            
            for(int j=0; j<ljzcArrOhter.size(); j++)
            {
                String mt[] = (String[])ljzcArrOhter.get(j);
                mt[0] = part940.getBsoID();
            }
            
            //CCBegin SS56
            // ���������빤�պϼ�����
//            String[] mt940=new String[]{gyID,gyPartIfc.getBsoID(),"1",gypart.getPartNumber(),dwbs,"0","","","��",
//                gyPartIfc.getPartNumber()};
//            childrenPart.add(mt940);
//            System.out.println("-----------------------------���-----------------------");
            //��ֹ���BOM������Ӧ������Ҫ�޸ĵĵط�
//            Collection ntree=chaifengybom(gypart, childrenPart, dwbs, part, ohters);//SS20
            //CCEnd SS56
//            System.out.println("��ֺ��С==="+ntree.size());
            //�ѹ��˵��ļ��ı���ֳ�ȥ�Ĳ��ּӻ���
            ArrayList arr = getOthersCFParts(ohters, part, dwbs);
            for(int i = 0;i < arr.size();i++)
            {
                String[] mt = (String[])arr.get(i);
                QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(mt[0],false);
                QMPartIfc subpart=(QMPartIfc)ps.refreshInfo(mt[1],false);
                if(!parentPart.getBsoID().equals(gyID))
                {
                    if(!gylxFiltration(dwbs,parentPart,subpart))
                    {
//                        gyList.add(mt);
                        arr.remove(mt);
                        continue;
                    }
                }
                
                subpart = filterLifeState(subpart);
                if(subpart == null)
                {
                    arr.remove(mt);
                    continue;
                }
                
                String childID = mt[1];
                String parentID = mt[0];
                QMPartIfc childPart = (QMPartIfc)ps.refreshInfo(childID, false);
                childPart = filterLifeState(childPart);
                if(childPart == null || childPart.equals(""))
                {
                    arr.remove(mt);
                }else
                {
                    mt[1] = childPart.getBsoID();
                }
            }
            ntree.addAll(arr);
            ntree.addAll(ljzcArrOhter);
            ntree.addAll(zcArrOhter);
//            System.out.println("�ѹ��˵��ļ��ı���ֳ�ȥ�Ĳ��ּӻ������С==="+ntree.size());
            // ���ݹ���Է��Ϲ���ļ����и��������Ϊ��
            //CCBegin SS55
//            Collection rtree=renameGyBom(ntree);
            Collection rtree=ntree;
            //CCEnd SS55
//            System.out.println("���湤��BOM��С==="+ntree.size());
//            System.out.println("���湤��BOM��Сrtree==="+rtree.size());
            ArrayList aa = new ArrayList(rtree);
            ArrayList tempList = new ArrayList();
            gyList = new ArrayList();//SS43
            for(int i=0; i<aa.size(); i++)
            {
                String[] mt = (String[])aa.get(i);
                if(tempList.contains(mt[0]+";;;"+mt[1]))
                {
                    rtree.remove(mt);
                }
                else
                {
                    tempList.add(mt[0]+";;;"+mt[1]);
                }

//Begin SS43
 //�ڶ���ִ�й��չ����������Ϊǰ�߹���ʱ��֪��ʲôԭ����Щ�����Ϲ��˹���ļ��ڹ��˵ļ�����û�У����ǹ��˺��ֳ���
                QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(mt[0],false);
                QMPartIfc subpart=(QMPartIfc)ps.refreshInfo(mt[1],false);
                if(!parentPart.getBsoID().equals(gyID))
                {
                    if(!gylxFiltration(dwbs,parentPart,subpart))
                    {
                        gyList.add(mt);
                        if(subpart.getPartNumber().contains("1301G04"))
                        {
//                            System.out.println("==========ɾ��===== " + mt[9]+"  " + mt[1]);
                        }
                        rtree.remove(mt);
//                        continue;
                    }
                }
                //End SS43
            }
            
            //Begin SS43
            for(int i=0; i<gyList.size(); i++)
            {
                String[] mt = (String[])gyList.get(i);
//                System.out.println("���չ���----------------"+mt[9]);
                if(mt[9].contains("1301G04"))
                {
//                    System.out.println("���ղ����ϴ�С    3513G00 mt[9]   ==========  " + mt[9]);
                }
                removeParts(childrenPart, mt, map, partMap);
            }
            //һ�´���childList3900G01���ϵĴ��룬�����޹�
            ArrayList rtreeArr = new ArrayList(rtree);
            ArrayList tempaaa = new ArrayList();
            for(int i=0; i<rtreeArr.size(); i++)
            {
                String strs[] = (String[])rtreeArr.get(i);
                String ss = strs[0]+strs[1];
                tempaaa.add(ss);
            }
            for(int i=0; i<rtreeArr.size(); i++)
            {
                String strs[] = (String[])rtreeArr.get(i);
                if(strs[9].contains("3900G01"))
                {
                    for(int j=0; j<childList3900G01.size(); j++)
                    {
                        String temps[] = (String[])childList3900G01.get(j);
                        if(!tempaaa.contains(strs[1]+temps[1]))
                        {
                            String ss = strs[1]+temps[1];
                            tempaaa.add(ss);
                            temps[0] = strs[1];
                            rtree.add(temps);
                        }
                    }
                }
            }
            //���Ҵ���̫�Ѿ������Դ˴�����һ��ѭ��rtree
            //CCBegin SS50
            //����һ�����ϣ����Ҫ��ɾ�������
            Vector dvec = new Vector();
            for(Iterator iter =rtree.iterator();iter.hasNext();){
            	//�ڶ���Ԫ�������bosid����ʮ��Ԫ����������
            	String[] strs = (String[])iter.next();
	            //�����������ж��Ƿ���5001G01,5001G02,5002G01,5002G02,5103G01,5103G05,5103G07�⼸���ַ�����ͷ������Ƿ����·�ߡ���--�á�
	        	//����һ��boolean�������������������С���--�á�·����ô����true�����򷵻�false
            	
            	QMPartIfc subpart = (QMPartIfc)ps.refreshInfo(strs[1]);
            	String partNumber = strs[9];
            	if (partNumber.startsWith("5001G01")
        				|| partNumber.startsWith("5001G02")
        				|| partNumber.startsWith("5002G01")
        				|| partNumber.startsWith("5002G02")
        				|| partNumber.startsWith("5103G01")
        				|| partNumber.startsWith("5103G05")
        				|| partNumber.startsWith("5103G07")){
		        	boolean rflag = isDeletePart(subpart,dwbs);
		        	if(rflag){
		        		dvec.add(strs);
//		        		rtree.remove(strs);
//		        		continue;
		        	}
            	}
            	//CCBegin SS60
            	//�����������ԣ�������ɾ����
            	if(partNumber.contains("3103070")){
            		dvec.add(strs);
            	}
            	//CCEnd SS60
            }
            //ѭ�����ɾ������ļ���
            for(Iterator iter = dvec.iterator();iter.hasNext();){
            	String[] strs = (String[])iter.next();
            	String partNumber = strs[9];
//            	System.out.println("������------------======="+partNumber);
//            	System.out.println("ɾ��ǰ======"+rtree.contains(strs));
            	rtree.remove(strs);
//            	System.out.println("ɾ����======"+rtree.contains(strs));
            }
        	//CCEnd SS50
            //CCBegin SS39
			//ϵͳ�Զ�ʶ��3724015-240��3724015-240/A��T67437676��T67420376��ֱ���滻������Ҫ�����
			rtree = replacePart(rtree);
			//CCBegin SS60
			//��ɾ������3103070�ӽ���
            for(Iterator iter = vecall.iterator();iter.hasNext();){
            	Object[] obj = (Object[])iter.next();
            	String partnumber = (String)obj[3];
            	
				if (partnumber.contains("3103070")) {
					String quantity = String.valueOf(obj[4]);
					PartUsageLinkIfc link = (PartUsageLinkIfc) obj[5];
//					System.out.println("����=====" + quantity);
					String[] mt = new String[10];
					mt[0] = gyID;// ����id
					mt[1] = (String) obj[2];// �Ӽ�id
					mt[2] = quantity;// ����
					mt[3] = "";
					mt[4] = dwbs;
					mt[5] = "0";
					mt[6] = "";
					mt[7] = "";
					mt[8] = link.getDefaultUnit().getDisplay();
					mt[9] = (String) obj[3];// �Ӽ����
					rtree.add(mt);
            	}
            }
            //CCEnd SS60
			//CCEnd SS39
            //End SS43
            //���湤��BOM
            saveGYBom(rtree, true, dwbs);
//            System.out.println("-----------------------------��¼��ƽṹ�б����սṹʹ�ñ��ı��-----------------------");
            //��¼��ƽṹ�б����սṹʹ�ñ��ı��
            saveBom(ytree);
//            System.out.println("-----------------------------����-----------------------");
            long t2 = System.currentTimeMillis();
    		System.out.println("��ʼ������BOM����ʱ��======"+(t2-t1)+" ����");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new QMException(ex);
        }
    }
    
    private void removeParts(ArrayList childrenPart, String[] mt, HashMap map, HashMap partMap)
    {
        ArrayList arr = (ArrayList)partMap.get(mt[1]);
//        System.out.println("mt[0]  ==== " + mt[0]);
//        System.out.println("mt[1]  ==== " + mt[1]);
//        System.out.println("map  ==== " + map);
//        System.out.println("partMap  ==== " + partMap);
//        if(mt[9].contains("1000410"))
//        {
//            System.out.println("-----------������1000410---------------"+arr);
//        }
        if(arr != null)
        {
            for(int j=0; j<arr.size(); j++)
            {
                String[] str = (String[])arr.get(j);
//                if(mt[9].contains("1000410"))
//                {
//                    System.out.println("-----------������1000410---------------"+str[9]);
//                }
//                System.out.println("str[1]  ==== " + str[1]);
                Integer inte = (Integer)map.get(str[1]);
                if((inte != null) && (inte>1))
                {
//                    if(mt[9].contains("1000410"))
//                    {
//                        System.out.println("1111-----------����---------------"+str[9]);
//                    }
                    inte = inte-1;
                    //map.put(str[1], inte);
                    childrenPart.remove(str);
                }
                else
                {
//                    if(mt[9].contains("1000410"))
//                    {
//                        System.out.println("-----------ɾ��---------------"+str[9]);
//                    }
                    childrenPart.remove(str);
                    map.remove(str[1]);
                    removeParts(childrenPart, str, map, partMap);
                }
            }
        }
    }
    
    /**
     * ��ȡԴ�㲿���ṹ��������
     */
     //Bgein SS43
    //CCBegin SS60
//   private Collection getBomLinks(String gyID, Collection childrenPart, QMPartIfc part, String dwbs,
//        Vector linkvec, boolean isHold, ArrayList ljzcArr, ArrayList zcArr, ArrayList ohters, ArrayList childList3900G01) throws QMException
    private Collection getBomLinks(String gyID, Collection childrenPart, QMPartIfc part, String dwbs,
            Vector linkvec, boolean isHold, ArrayList ljzcArr, ArrayList zcArr, ArrayList ohters, ArrayList childList3900G01,Vector vecall,float quantity,int level) throws QMException
            //CCEnd SS60
    {
        try
        {
            if(part!=null)
            {

                StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
                VersionControlService vcservice=(VersionControlService)EJBServiceHelper
                    .getService("VersionControlService");
                JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
                PersistService ps=(PersistService)EJBServiceHelper.getPersistService();

                PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
                Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);
                if(linkcoll!=null&&linkcoll.size()>0)
                {
                    Iterator iter=linkcoll.iterator();
//                    QMPartMasterIfc partMaster=null;
                    Vector tempResult=new Vector();
                    QMPartIfc subpart=null;
                    Vector tempvec=new Vector();
                    ArrayList array = new ArrayList();//SS20
                    //CCBegin SS60
                    level++;
                    //CCEnd SS60
                    while(iter.hasNext())
                    {
                        Object obj[]=(Object[])iter.next();
                        PartUsageLinkIfc link=(PartUsageLinkIfc)obj[0];
                        if(obj[1] instanceof QMPartIfc)
                        {
                            subpart=(QMPartIfc)obj[1];
                            if(linkvec.contains(part.getBsoID()+subpart.getBsoID()))
                            {
                                if(tempvec.contains(part.getBsoID()+subpart.getBsoID()))
                                {
                                    System.out.println("��ǰ�ṹ���ظ��������ظ���ӣ�"+part.getPartNumber()+"=="+subpart.getPartNumber());
                                }
                                else
                                {
                                    continue;
                                }
                            }
                            else
                            {
                                linkvec.add(part.getBsoID()+subpart.getBsoID());
                            }
                            tempvec.add(part.getBsoID()+subpart.getBsoID());
                            String[] mt=new String[10];
                            mt[0]=part.getBsoID();
                            mt[1]=subpart.getBsoID();
                            mt[2]=String.valueOf(link.getQuantity());
                            mt[3]="";
                            mt[4]=dwbs;
                            mt[5]="0";
                            mt[6]="";
                            mt[7]="";
                            mt[8]=link.getDefaultUnit().getDisplay();
                            mt[9]=subpart.getPartNumber();
                            //CCBegin SS60
                            Object[] objlink = new Object[7];
                            objlink[0] = part.getBsoID();;//����id
                            objlink[1] = part.getPartNumber();//�������
                            objlink[2] = subpart.getBsoID();//�Ӽ�id
                            objlink[3] = subpart.getPartNumber();//�Ӽ����
                            objlink[4] = link.getQuantity()*quantity;//�����֧������ float
                            objlink[5] = link;//������ϵ�����Ի�ȡ������ PartUsageLinkIfc 
                            objlink[6] = level;//�㼶��ϵ int��
                            vecall.add(objlink);
//                            System.out.println(objlink[3]+"=====1ʹ������1===="+objlink[4]);
                            //CCEnd SS60
                            boolean flag = false;
                            for(int i=0; i<ljzcArr.size(); i++)
                            {
                                String str = (String)ljzcArr.get(i);
                                if(subpart.getPartNumber().contains(str))
                                {
                                    flag = true;
                                    isHold = true;
                                    mt[0]=gyID;
                                    break;
                                }
                            }
                            if(!flag)
                            {
                                for(int i=0; i<zcArr.size(); i++)
                                {
                                    String str = (String)zcArr.get(i);
                                    if(subpart.getPartNumber().contains(str))
                                    {
                                        flag = true;
                                        isHold = true;
                                        mt[0]=gyID;
                                        break;
                                    }
                                }
                            }
//                            if(!flag)
//                            {
//                                isHold = false;
//                            }
//                            System.out.println("isHold====="+isHold);
//                            System.out.println("flag====="+flag);
//                            if(subpart.getPartNumber().contains("1104G01"))
//                            {
//                                System.out.println("isHold   ===  " + isHold);
//                                System.out.println("flag   ===  " + flag);
//                            }
                            if(mt[9].equalsIgnoreCase("3725020-A01") || mt[9].equalsIgnoreCase("8203010-E06C") || 
                                    mt[9].equalsIgnoreCase("7925040-92W") || mt[9].equalsIgnoreCase("7901012EA01"))
                            {
                                childList3900G01.add(mt);
                            }
                            else
                            {
                                if(isHold || flag)
                                {
//                                    System.out.println("111111111111111111111111111111111111111");
                                    childrenPart.add(mt);
                                }
                                else
                                {
//                                    System.out.println("222222222222222222222222222222222222222222");
                                    ohters.add(mt);
                                }
                            }
                            
                            if(subpart!=null)
                            {
                            	//CCBegin SS60
//                                getBomLinks(gyID,childrenPart,subpart,dwbs,linkvec, isHold, ljzcArr, zcArr, ohters, childList3900G01);
                            	getBomLinks(gyID,childrenPart,subpart,dwbs,linkvec, isHold, ljzcArr, zcArr, ohters, childList3900G01,vecall,link.getQuantity()*quantity,level);
                            	//CCEnd SS60
                            }
                        }
                        
                    }
                    tempvec=null;
                }
            }
            return childrenPart;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new QMException(e);
        }
    }
    //End SS43
    //End SS41

	/**
	 * ����������ʼ����BOM
	 */
	public void initGYZCBom_old(String partID,String gyID,String dwbs) throws QMException//SS41
	{
		try
		{
			// �������߼��ܳ�
			String partNumberRuleFDJ=RemoteProperty.getProperty("gybomlistfdj");
			// ����
			String partNumberRuleZC=RemoteProperty.getProperty("gybomlistzc");
			boolean gyPartFlag=false;
			Vector fdjparts=new Vector();
			Vector zcparts=new Vector();
			//��������ļ���û�еļ���Ŀ���ǰ���Щ���Ӽ����ϲ�������ļ���ֳ���
			ArrayList otherParts = new ArrayList();
			Collection mtree=new ArrayList();
			Collection ytree=new ArrayList();
			Vector linkvec=new Vector();
			StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
			JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(partID);
			QMPartIfc gypart=(QMPartIfc)ps.refreshInfo(gyID);
			UserInfo user=getCurrentUserInfo();
			//deleteGYBomLevel(gyID,dwbs,"0");
			//deleteBom(gypart.getPartNumber(),dwbs);
			PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
			Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);
			for(Iterator iter=linkcoll.iterator();iter.hasNext();)
			{
				Object obj[]=(Object[])iter.next();
				PartUsageLinkIfc link=(PartUsageLinkIfc)obj[0];
				if(obj[1] instanceof QMPartIfc)
				{
					QMPartIfc subpart=(QMPartIfc)obj[1];
//System.out.println("subpart   ==============   " + subpart);
//System.out.println("subpart.getPartNumber()   ==============   " + subpart.getPartNumber());
//System.out.println("partNumberRuleFDJ   ==============   " + partNumberRuleFDJ);
					// ������
					if(subpart.getPartNumber().length()>=7
						&&partNumberRuleFDJ.indexOf(subpart.getPartNumber().substring(0,7))!=-1)
					{
						if(subpart.getPartNumber().startsWith("1000410"))
						{
							fdjparts.add(
								0,
								new String[]{subpart.getBsoID(),String.valueOf(link.getQuantity()),
									subpart.getPartNumber()});
							gyPartFlag=true;
						}
						else
						{
							fdjparts.add(new String[]{subpart.getBsoID(),String.valueOf(link.getQuantity()),
								subpart.getPartNumber()});
						}

						// parentPart,childPart,quantity,createTime,modifyTime,carModelCode,dwbs,createUser,color,unit,childNumber
						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
							gypart.getPartNumber(),dwbs,user.getBsoID(),"",link.getDefaultUnit().getDisplay(),
							subpart.getPartNumber()};
						ytree.add(mt1);
					}
					// ����
					else if(subpart.getPartNumber().length()>=7
						&&partNumberRuleZC.indexOf(subpart.getPartNumber().substring(0,7))!=-1)
					{
						zcparts.add(new String[]{subpart.getBsoID(),String.valueOf(link.getQuantity()),
							subpart.getPartNumber()});
						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
							gypart.getPartNumber(),dwbs,user.getBsoID(),"",link.getDefaultUnit().getDisplay(),
							subpart.getPartNumber()};
						ytree.add(mt1);
					}
					else
					{
						otherParts.add(subpart);//SS37
						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
							gypart.getPartNumber(),dwbs,"","",link.getDefaultUnit().getDisplay(),
							subpart.getPartNumber()};
						ytree.add(mt1);
					}
				}
			}
			ArrayList array = new ArrayList();//SS20
			String temp  = null;//SS37
			if(gyPartFlag)
			{
//				System.out.println("lyz--��ʼ�����ϼ���");
				// ������
				Iterator fdjiter1=fdjparts.iterator();
				String[] fdjstr=(String[])fdjiter1.next();
				QMPartIfc gyPartIfc=createGYPart(fdjstr[2],fdjparts, dwbs);//SS20//����1000940
				// ���������빤�պϼ�����
				String[] mt=new String[]{gyID,gyPartIfc.getBsoID(),"1",gypart.getPartNumber(),dwbs,"0","","","��",
					gyPartIfc.getPartNumber()};
				mtree.add(mt);
				temp = gyPartIfc.getPartNumber();//SS37
				// �������պϼ�
				Iterator iter=fdjparts.iterator();
				QMPartIfc fdjsp=null;
				while(iter.hasNext())
				{
					String[] str1=(String[])iter.next();
					fdjsp=(QMPartIfc)ps.refreshInfo(str1[0]);
					//CCBegin SS20
					//1000410��1303G02��1308G02������1000410���1313G02 ������1000410���1503G01��1600G00��1700G00��
					//3523G01��3724G34
					if(fdjsp.getPartNumber().contains("1000410") || fdjsp.getPartNumber().contains("1303G02") || 
					        fdjsp.getPartNumber().contains("1308G02") || fdjsp.getPartNumber().contains("1313G02") || 
					        fdjsp.getPartNumber().contains("1503G01") || fdjsp.getPartNumber().contains("1600G00") || 
					        fdjsp.getPartNumber().contains("1700G00") || fdjsp.getPartNumber().contains("3523G01") || 
					        fdjsp.getPartNumber().contains("3724G34") )
					{
					    String[] mt1=new String[]{gyPartIfc.getBsoID(),fdjsp.getBsoID(),str1[1],gypart.getPartNumber(),
					            dwbs,"0","","","��",str1[2]};
//					    System.out.println("����777============="+gyPartIfc.getPartNumber());
//	                    System.out.println("�Ӽ�777============="+fdjsp.getPartNumber());
//	                    System.out.println("gypart777============="+gypart.getPartNumber());
					    mtree.add(mt1);
					}
					else
					{
					    String[] mt1=new String[]{gyID,fdjsp.getBsoID(),str1[1],gypart.getPartNumber(),
		                        dwbs,"0","","","��",str1[2]};
					    mtree.add(mt1);
					}
					//CCEnd SS20
					if(fdjsp.getPartNumber().substring(0,7).equals("1000410"))
					{
						mtree=getBomLinks(gyID,mtree,fdjsp,gypart.getPartNumber(),dwbs,linkvec,"1000410",array,otherParts);//SS20//SS37
					}
					else
					{
						mtree=getBomLinks(gyID,mtree,fdjsp,gypart.getPartNumber(),dwbs,linkvec,"fdj",array,otherParts);//SS20//SS37
					}
				}
				// ����
				Iterator zciter=zcparts.iterator();
				QMPartIfc zcsp=null;
				while(zciter.hasNext())
				{
//					System.out.println("��������ô��������");
					String[] zcstr1=(String[])zciter.next();
					zcsp=(QMPartIfc)ps.refreshInfo(zcstr1[0]);
					String[] mt1=new String[]{gyID,zcsp.getBsoID(),zcstr1[1],gypart.getPartNumber(),dwbs,"0","","","��",
						zcstr1[2]};
					mtree.add(mt1);
					mtree=getBomLinks(gyID,mtree,zcsp,zcsp.getPartNumber(),dwbs,linkvec,"zc",array,otherParts);//SS20//SS37
				}
			}
			// ���ݹ�����нṹ���
			Collection ntree=chaifengybom(gypart,mtree,dwbs, part,array);//SS20
			//�ѹ��˵��ļ��ı���ֳ�ȥ�Ĳ��ּӻ���
			ArrayList arr = getOthersCFParts(otherParts,part,dwbs);
			//CCBegin SS37
			for(int i=0; i<arr.size(); i++)
      {
          String[] mt=(String[])arr.get(i);
          String childID=mt[1];
          String parentID = mt[0];
          QMPartIfc childPart = (QMPartIfc)ps.refreshInfo(childID,false);
          childPart = filterLifeState(childPart);
          if(childPart == null || childPart.equals(""))
          {
              arr.remove(mt);
          }
          else
          {
              mt[1] = childPart.getBsoID();
          }
      }
			ntree.addAll(arr);
			//CCEnd SS37
			// ���ݹ���Է��Ϲ���ļ����и��������Ϊ��
			Collection rtree=renameGyBom(ntree);
			//CCBegin SS39
			//ϵͳ�Զ�ʶ��3724015-240��3724015-240/A��T67437676��T67420376��ֱ���滻������Ҫ�����
			rtree = replacePart(rtree);
			//CCEnd SS39
			//CCBegin SS15
			saveGYBom(rtree, true, dwbs);//SS21//SS28
			//CCEnd SS15
			// saveGYBom(ntree);
			saveBom(ytree);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	
		
		//CCBegin SS37
	/**
     * ������������״�������������ǰ׷��
     * ���������Լ������Ժ��״̬�����Ϊ�������������������ͼ���°汾�㲿��״̬����������
     * ��׷�ݸü�������ͼ֮ǰ���������İ汾��
     * @param vec �㲿������ �� �ṹ������
     * @return
     * 
     * Vector
     * @throws QMException 
     */
    public QMPartIfc filterLifeState(QMPartIfc part1) throws QMException
    {
        String partNumberRuleFDJ=RemoteProperty.getProperty("gybomFilterLifeState");
        
        String[] State=partNumberRuleFDJ.split(";;;");
        QMPartIfc part = null;
        Object[] obj;
        Vector result = new Vector();
        if(part1 instanceof QMPartIfc)
        { 
             part = part1;
               
                String state = part.getLifeCycleState().getDisplay();

                for(int j=0;j<State.length;j++)
                {
                    // System.out.println("state = "+state);
                    if(state.contains(State[j]))
                    {                       
                         return part;
                    }
                }

              // System.out.println("���հ汾 = "+part);
                //�����ǰ����������������׷��ǰһ���汾
               
                    part=getPart(part1,State);
                
      
        }
        return part;
    }
    /**
     * ������������״̬׷�ݵ�ǰ��汾�㲿��
     * @param curPart
     * @return
     * 
     * QMPartIfc
     * @throws QMException 
     */
    public QMPartIfc getPart(QMPartIfc curPart,String[] State) throws QMException
    {
        String preID = curPart.getPredecessorID();
        PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
       
       // System.out.println("curPart.getPredecessorID() = "+curPart.getPredecessorID());
        if(preID==null||preID.length()==0)
        {
            return null;
        }
        QMPartIfc prePart = (QMPartIfc)persistService.refreshInfo(preID, false);
            String state = prePart.getLifeCycleState().getDisplay();
            for(int j = 0;j < State.length;j++)
            {
              //  System.out.println("==================������������״̬׷�ݵ�ǰ��汾�㲿�� state = "+state);                
                if(state.equals(State[j]))
                {
                    return prePart;
                }
            }
           // System.out.println("==================������������״̬׷�ݵ�ǰ��汾�㲿�� prePart = "+prePart); 
            return getPart(prePart,State);
//        }
    }
    
	
	/**
	 * ��ȡû�б����õ��ļ��е��Ӽ�����ֳ�ȥ�ļ��ļ���
	 * @param othersParts
	 * @return
	 * @throws QMException
	 */
	private ArrayList getOthersCFParts(ArrayList othersParts, QMPartIfc root, String dwbs) throws QMException
	{
	    ArrayList ret = new ArrayList();
	    //��ȡothersParts�����м��������Ӽ�
	    ArrayList allParts = new ArrayList();
	    StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
        VersionControlService vcservice=(VersionControlService)EJBServiceHelper
            .getService("VersionControlService");
        JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
        PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
        for(int i=0; i<othersParts.size(); i++)
        {
        	//Begin SS41
            //QMPartIfc part = (QMPartIfc)othersParts.get(i);
            Object object = othersParts.get(i);
            QMPartIfc part = null;
            if(object instanceof QMPartIfc)
            {
                part = (QMPartIfc)object;
            }
            else
            {
                String strs[] = (String[])object;
                part = (QMPartIfc)ps.refreshInfo(strs[1], false);
            }
            //End SS41
            PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
            Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);
            if(linkcoll!=null&&linkcoll.size()>0)
            {
                Iterator iter=linkcoll.iterator();
                QMPartMasterIfc partMaster=null;
                Vector tempResult=new Vector();
                QMPartIfc subpart=null;
                Vector tempvec=new Vector();
                ArrayList array = new ArrayList();
                while(iter.hasNext())
                {
                    Object obj[]=(Object[])iter.next();
                    PartUsageLinkIfc link=(PartUsageLinkIfc)obj[0];
                    //CCBegin SS64
                    if(obj[1] instanceof QMPartIfc){
                    	//CCEnd SS64
	                    subpart=(QMPartIfc)obj[1];
	                    String[] mt=new String[10];
	                    mt[0]=part.getBsoID();
	                    mt[1]=subpart.getBsoID();
	                    mt[2]=String.valueOf(link.getQuantity());
	                    mt[3]="";
	                    mt[4]=dwbs;
	                    mt[5]="0";
	                    mt[6]="";
	                    mt[7]="";
	                    mt[8]=link.getDefaultUnit().getDisplay();
	                    mt[9]=subpart.getPartNumber();
	                    allParts.add(mt);
                    //CCBegin SS64
                    }
                    //CCEnd SS64
                }
            }
        }
	    
        //���ݲ�ֹ����֣�ֻ��������ֳ�ȥ�Ĳ���
        
        String partNumber1000940 = "";
        String partNumber1000410 = "";
        String id3900G01 = "";
        String id3100G00 = "";
        
        HashMap map = new HashMap();//���Ҫ��ּ��Ͷ������ļ�¼
        for(int i=0; i<allParts.size(); i++)
        {
            
            String strs[] = (String[])allParts.get(i);
//            System.out.println("strs[]   ------------    "+strs[9]);
            //CCBegin SS24
            //���ʱ����JTA��ͷ�ľ�ȥ�����������ĳ�ʼ��
            if(strs[9].startsWith("JTA"))
            {
                allParts.remove(strs);
                continue;
            }
            if(strs[9].contains("1000940"))
            {
                partNumber1000940 = strs[1];
            }
            if(strs[9].contains("1000410"))
            {
                partNumber1000410 = strs[1];
            }
            
            if(strs[9].contains("1104G01") || strs[9].contains("1119G01") || 
                    strs[9].contains("1203G01") || strs[9].contains("8116G01") || 
                    strs[9].contains("1303G01") || strs[9].contains("1311G02") || 
                    strs[9].contains("3406G01") || strs[9].contains("3406G02") || 
                    strs[9].contains("1311G01") || strs[9].contains("3406G01") || 
                    strs[9].contains("3506G00") || strs[9].contains("3506G00") ||
                    strs[9].contains("8116G01") || strs[9].contains("3509G01"))
            {
                map.put(strs[9], strs);
            }
            if(strs[9].contains("3900G01"))
            {
                id3900G01 = strs[1];
            }
            if(strs[9].contains("3100G00"))
            {
                id3100G00 = strs[1];
            }
        }
        
        QMPartIfc newParentPart1119G01=null;
        QMPartIfc newParentPart1303G01=null;
        QMPartIfc newParentPart1104G01=null;
        QMPartIfc newParentPart1203G01=null;
        QMPartIfc newParentPart8116G01=null;
        QMPartIfc newParentPart1311G02=null;
        QMPartIfc newParentPart3506G00=null;
        QMPartIfc newParentPart3406G01=null;
        QMPartIfc newParentPart3406G02=null;
        QMPartIfc newParentPart1311G01=null;
        QMPartIfc newParentPart3509G01=null;
        //CCBegin SS63
        QMPartIfc ZCnewParentPart1119G01=null;
        QMPartIfc ZCnewParentPart1303G01=null;
        QMPartIfc ZCnewParentPart1104G01=null;
        QMPartIfc ZCnewParentPart1203G01=null;
        QMPartIfc ZCnewParentPart8116G01=null;
        QMPartIfc ZCnewParentPart1311G02=null;
        QMPartIfc ZCnewParentPart3506G00=null;
        QMPartIfc ZCnewParentPart3406G01=null;
        QMPartIfc ZCnewParentPart3406G02=null;
        QMPartIfc ZCnewParentPart1311G01=null;
        QMPartIfc ZCnewParentPart3509G01=null;
        //CCEnd SS63
        
        boolean falg1104G01 = true;
        boolean falg1119G01 = true;
        boolean falg1303G01 = true;
        boolean falg1311G01 = true;
        boolean falg3406G01 = true;
        boolean falg3509G01 = true;
        boolean falg1203G01 = true;
        boolean falg1311G02 = true;
        boolean falg3406G02 = true;
        boolean falg8116G01 = true;
        boolean falg3506G00 = true;
        
	    for(Iterator iter=allParts.iterator();iter.hasNext();)
        {
            String[] mt=(String[])iter.next();
            String subid=mt[1];
            String parentID = mt[0];
            QMPartIfc spart=(QMPartIfc)ps.refreshInfo(subid,false);
            QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(parentID,false);
            String partNumber=spart.getPartNumber();
            
            //1�������֡����ֳ���ƽ̨��ֱ�ӹҵ��������ܳ��µ��߼��ܳɣ�
            //1000410��1303G02��1503G01��1600G00��1700G00��3523G01��3724G34ֱ�ӷŵ�1000940��
            if(partNumber.contains("1000410") || partNumber.contains("1303G02") || 
                    partNumber.contains("1503G01") || partNumber.contains("1600G00") || 
                    partNumber.contains("1700G00") || partNumber.contains("3523G01") || 
                    partNumber.contains("1308G02") || partNumber.contains("1313G02") || //SS23��������Ҳ�Ƿŵ�1000940��
                    partNumber.contains("3724G34") )
            {
                mt[0] = partNumber1000940;
            }
            
            //3725020-A01 8203010-E06C 7925040-92W 7901012EA01 �ŵ�3900G01��
            //Begin SS43
            //if(partNumber.equalsIgnoreCase("3725020-A01") || partNumber.equalsIgnoreCase("8203010-E06C") || 
            //        partNumber.equalsIgnoreCase("7925040-92W") || partNumber.equalsIgnoreCase("7901012EA01"))
            //{
            //    mt[0] = id3900G01;
            //}
            //End SS43
            
            //�������ͷ3103070��3100G00��
            if(partNumber.startsWith("3103070"))
            {
//                mt[0] = id3100G00;
                mt[0] = root.getBsoID();
            }
            
            //J6P���Ͳ��
            if(root.getPartNumber().contains("P66") || root.getPartNumber().contains("p66") || 
                    root.getPartNumber().contains("P67") || root.getPartNumber().contains("p67"))//SS22���������¾ɹ���Աȡ�Ϊ�û����������
            {
                if(parentPart.getPartNumber().contains("1104G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1104G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1104G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1104G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
                            partNumber.contains("1104210") || partNumber.contains("1145035"))
                    {
                        if(newParentPart1104G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1104G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1104G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
//                          //CCBegin SS57
                            if(strs!=null){
//	                            //CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1104G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1104G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(spart.getBsoID(),newParentPart1104G01.getBsoID(),root.getBsoID(),"cf");
	                            falg1104G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
                                partNumber.contains("1104210") || partNumber.contains("1145035"))
                        {
                            mt[0] = newParentPart1104G01.getBsoID();
                        }
                    }
                }
                
//                1119G01 ��ֵ�1000940��1119046 1119031 1119035 1119053 1119062 1119064 1119125 
//                                        1119127 CQ1461045 CQ34010��1���� T6769006A
                if(parentPart.getPartNumber().contains("1119G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1119G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1119G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1119G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1119046") || partNumber.contains("1119031") || 
                            partNumber.contains("1119035") || partNumber.contains("1119053") || 
                            partNumber.contains("1119062") || partNumber.contains("1119064") || 
                            partNumber.contains("1119125") || partNumber.contains("1119127") || 
                            partNumber.contains("CQ1461045") || partNumber.contains("T6769006A") ||
                            partNumber.contains("CQ34010"))
                    {
                        if(newParentPart1119G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1119G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1119G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1119G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1119G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1119G01.getBsoID(),root.getBsoID(),"cf");
	                            falg1119G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1119046") || partNumber.contains("1119031") || 
                                partNumber.contains("1119035") || partNumber.contains("1119053") || 
                                partNumber.contains("1119062") || partNumber.contains("1119064") || 
                                partNumber.contains("1119125") || partNumber.contains("1119127") || 
                                partNumber.contains("CQ1461045") || partNumber.contains("T6769006A"))
                        {
                            mt[0] = newParentPart1119G01.getBsoID();
                        }
                        if(partNumber.contains("CQ34010"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart1119G01.getBsoID();
                            temp[2] = "1";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-1)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                    }
                }
                
//                1203G01 ��ֵ�1000940��1203040 1203065 1203073 1203071 1203075 1203075 CQ1460816 
//                                        CQ1460825 Q1840830 CQ32608 1203010 1203021 1203071 1203025 
//                                        1203030 1203071 1203510 CQ1501045 CQ1501055 CQ34010 Q1841225 
//                                        Q40110 CQ34012 CQ32612 Q1841240
                if(parentPart.getPartNumber().contains("1203G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1203G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1203G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1203G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1203040") || partNumber.contains("1203065") || 
                            partNumber.contains("1203073") || partNumber.contains("1203071") || 
                            partNumber.contains("1203075") || partNumber.contains("1203075") || 
                            partNumber.contains("CQ1460816") || partNumber.contains("CQ1460825") || 
                            partNumber.contains("Q1840830") || partNumber.contains("CQ32608") || 
                            partNumber.contains("1203010") || partNumber.contains("1203021") || 
                            partNumber.contains("1203071") || partNumber.contains("1203025") || 
                            partNumber.contains("1203030") || partNumber.contains("1203071") || 
                            partNumber.contains("1203510") || partNumber.contains("CQ1501045") || 
                            partNumber.contains("CQ1501055") || partNumber.contains("CQ34010") || 
                            partNumber.contains("Q1841225") || partNumber.contains("Q40110") || 
                            partNumber.contains("CQ34012") || partNumber.contains("CQ32612") ||
                            partNumber.contains("Q1841240"))
                    {
                        if(newParentPart1203G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1203G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1203G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                            //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1203G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1203G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1203G01.getBsoID(),root.getBsoID(),"cf");
	                            falg1203G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1203040") || partNumber.contains("1203065") || 
                                partNumber.contains("1203073") || partNumber.contains("1203071") || 
                                partNumber.contains("1203075") || partNumber.contains("1203075") || 
                                partNumber.contains("CQ1460816") || partNumber.contains("CQ1460825") || 
                                partNumber.contains("Q1840830") || partNumber.contains("CQ32608") || 
                                partNumber.contains("1203010") || partNumber.contains("1203021") || 
                                partNumber.contains("1203071") || partNumber.contains("1203025") || 
                                partNumber.contains("1203030") || partNumber.contains("1203071") || 
                                partNumber.contains("1203510") || partNumber.contains("CQ1501045") || 
                                partNumber.contains("CQ1501055") || partNumber.contains("CQ34010") || 
                                partNumber.contains("Q1841225") || partNumber.contains("Q40110") || 
                                partNumber.contains("CQ34012") || partNumber.contains("CQ32612") ||
                                partNumber.contains("Q1841240"))
                        {
                            mt[0] = newParentPart1203G01.getBsoID();
                        }
                    }
                }
                
//                8116G01 ��ֵ�1000940��8116061 8116703
                if(parentPart.getPartNumber().contains("8116G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart8116G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart8116G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart8116G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("8116061") || partNumber.contains("8116703"))
                    {
                        if(newParentPart8116G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart8116G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg8116G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart8116G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart8116G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart8116G01.getBsoID(),root.getBsoID(),"cf");
	                            falg8116G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("8116061") || partNumber.contains("8116703"))
                        {
                            mt[0] = newParentPart8116G01.getBsoID();
                        }
                    }
                }
                
//                1303G01 ��ֵ�1000940��1303011 1303021 CQ67690B��2����
                if(parentPart.getPartNumber().contains("1303G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1303G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1303G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1303G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1303011") || partNumber.contains("1303021") ||
                            partNumber.contains("CQ67690B"))
                    {
                        if(newParentPart1303G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1303G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1303G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1303G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1303G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1303G01.getBsoID(),root.getBsoID(),"cf");
	                            falg1303G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1303011") || partNumber.contains("1303021"))
                        {
                            mt[0] = newParentPart1303G01.getBsoID();
                        }
                        if(partNumber.contains("CQ67690B"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart1303G01.getBsoID();
                            temp[2] = "2";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-2)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                    }
                }
                
//                1311G02 ��ֵ�1000940��1311024 1311036
                if(parentPart.getPartNumber().contains("1311G02"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1311G02 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1311G02 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1311G02.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1311024") || partNumber.contains("1311036"))
                    {
                        if(newParentPart1311G02 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1311G02 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1311G02)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1311G02.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1311G02.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1311G02.getBsoID(),root.getBsoID(),"cf");
	                            falg1311G02 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1311024") || partNumber.contains("1311036"))
                        {
                            mt[0] = newParentPart1311G02.getBsoID();
                        }
                    }
                }
                
//                3406G01 ��ֵ�1000940��3406010 3406021 3406321
                
//                3506G00 ��ֵ�1000940��3506221 3506230
                if(parentPart.getPartNumber().contains("3506G00"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart3506G00 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart3506G00 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart3506G00.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("3506221") || partNumber.contains("3506230"))
                    {
                        if(newParentPart3506G00 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart3506G00 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg3506G00)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart3506G00.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart3506G00.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart3506G00.getBsoID(),root.getBsoID(),"cf");
	                            falg3506G00 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("3506221") || partNumber.contains("3506230"))
                        {
                            mt[0] = newParentPart3506G00.getBsoID();
                        }
                    }
                }
            }
            //JMP���Ͳ��
            else if(root.getPartNumber().contains("P63") || root.getPartNumber().contains("p63") || 
                    root.getPartNumber().contains("P64") || root.getPartNumber().contains("p64"))//SS22���������¾ɹ���Աȡ�Ϊ�û����������
            {
                if(parentPart.getPartNumber().contains("1104G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1104G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1104G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1104G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
                            partNumber.contains("1104021") || partNumber.contains("3724015") || 
                            partNumber.contains("Q1841225"))
                    {
                        if(newParentPart1104G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1104G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1104G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1104G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1104G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1104G01.getBsoID(),root.getBsoID(),"cf");
	                            falg1104G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
                                partNumber.contains("1104021") || partNumber.contains("3724015") || 
                                partNumber.contains("Q1841225"))
                        {
                            mt[0] = newParentPart1104G01.getBsoID();
                        }
                    }
                }
                
//                1119G01 ��ֵ�1000940��1119031 1119035 1119038 1119049 1119050 1119081 1119105 
//                                      CQ1461030 T6769006A
                if(parentPart.getPartNumber().contains("1119G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1119G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1119G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1119G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1119031") || partNumber.contains("1119035") || 
                            partNumber.contains("1119038") || partNumber.contains("1119049") || 
                            partNumber.contains("1119050") || partNumber.contains("1119081") || 
                            partNumber.contains("1119105") || partNumber.contains("CQ1461030") || 
                            partNumber.contains("Q1841225"))
                    {
                        if(newParentPart1119G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1119G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1119G01)
                        {
                            //�������߼��ܳɺ�  1119G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1119G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1119G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1119G01.getBsoID(),root.getBsoID(),"cf");
	                            falg1119G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1119031") || partNumber.contains("1119035") || 
                                partNumber.contains("1119038") || partNumber.contains("1119049") || 
                                partNumber.contains("1119050") || partNumber.contains("1119081") || 
                                partNumber.contains("1119105") || partNumber.contains("CQ1461030") || 
                                partNumber.contains("Q1841225"))
                        {
                            mt[0] = newParentPart1119G01.getBsoID();
                        }
                    }
                }
                
//                1203G01 ��ֵ�1000940��1203510 1203030 1203075
                if(parentPart.getPartNumber().contains("1203G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1203G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1203G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1203G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1203510") || partNumber.contains("1203030") || 
                            partNumber.contains("1203075"))
                    {
                        if(newParentPart1203G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1203G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1203G01)
                        {
                            //�������߼��ܳɺ�  1119G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1203G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1203G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1203G01.getBsoID(),root.getBsoID(),"cf");
	                            falg1203G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1203510") || partNumber.contains("1203030") || 
                                partNumber.contains("1203075"))
                        {
                            mt[0] = newParentPart1203G01.getBsoID();
                        }
                    }
                }
                
//                1303G01 ��ֵ�1000940��1303021 CQ67670B
                if(parentPart.getPartNumber().contains("1303G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1303G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1303G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1303G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1203510") || partNumber.contains("1203030") || 
                            partNumber.contains("1203075"))
                    {
                        if(newParentPart1303G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1303G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1303G01)
                        {
                            //�������߼��ܳɺ�  1119G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1303G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1303G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1303G01.getBsoID(),root.getBsoID(),"cf");
	                            falg1303G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1203510") || partNumber.contains("1203030") || 
                                partNumber.contains("1203075"))
                        {
                            mt[0] = newParentPart1303G01.getBsoID();
                        }
                    }
                }
                
//                1311G02 ��ֵ�1000940��1311020 1311022 1311027 1311030 1311032 CQ1460820 
//                                      CQ1501430 CQ34014 CQ67616 CQ67630B Q40314
                if(parentPart.getPartNumber().contains("1311G02"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1311G02 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1311G02 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1311G02.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1311020") || partNumber.contains("1311022") || 
                            partNumber.contains("1311027") || partNumber.contains("1311030") || 
                            partNumber.contains("1311032") || partNumber.contains("CQ1460820") || 
                            partNumber.contains("CQ1501430") || partNumber.contains("CQ34014") || 
                            partNumber.contains("CQ67616") || partNumber.contains("CQ67630B") || 
                            partNumber.contains("Q40314"))
                    {
                        if(newParentPart1311G02 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1311G02 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1311G02)
                        {
                            //�������߼��ܳɺ�  1119G01-382F001 �ŵ�940��

                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1311G02.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1311G02.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1311G02.getBsoID(),root.getBsoID(),"cf");
	                            falg1311G02 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1311020") || partNumber.contains("1311022") || 
                                partNumber.contains("1311027") || partNumber.contains("1311030") || 
                                partNumber.contains("1311032") || partNumber.contains("CQ1460820") || 
                                partNumber.contains("CQ1501430") || partNumber.contains("CQ34014") || 
                                partNumber.contains("CQ67616") || partNumber.contains("CQ67630B") || 
                                partNumber.contains("Q40314"))
                        {
                            mt[0] = newParentPart1311G02.getBsoID();
                        }
                    }
                }
                
//                3406G01 ��ֵ�1000940��3406011 CQ67635B 3406030 3406424
                if(parentPart.getPartNumber().contains("3406G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart3406G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart3406G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart3406G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("3406011") || partNumber.contains("CQ67635B") || 
                            partNumber.contains("3406030") || partNumber.contains("3406424"))
                    {
                        if(newParentPart3406G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart3406G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg3406G01)
                        {
                            //�������߼��ܳɺ�  1119G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart3406G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart3406G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart3406G01.getBsoID(),root.getBsoID(),"cf");
	                            falg3406G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("3406011") || partNumber.contains("CQ67635B") || 
                                partNumber.contains("3406030") || partNumber.contains("3406424"))
                        {
                            mt[0] = newParentPart3406G01.getBsoID();
                        }
                    }
                }
                
//                3406G02 ��ֵ�1000940��3406011 CQ67635B 3406030 3406424
                if(parentPart.getPartNumber().contains("3406G02"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart3406G02 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart3406G02 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart3406G02.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("3406011") || partNumber.contains("CQ67635B") || 
                            partNumber.contains("3406030") || partNumber.contains("3406424"))
                    {
                        if(newParentPart3406G02 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart3406G02 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg3406G02)
                        {
                            //�������߼��ܳɺ�  1119G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart3406G02.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart3406G02.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart3406G02.getBsoID(),root.getBsoID(),"cf");
	                            falg3406G02 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("3406011") || partNumber.contains("CQ67635B") || 
                                partNumber.contains("3406030") || partNumber.contains("3406424"))
                        {
                            mt[0] = newParentPart3406G02.getBsoID();
                        }
                    }
                }
            }
            //J6L���Ͳ��
            else if(root.getPartNumber().contains("P62") || root.getPartNumber().contains("p62") || 
                    root.getPartNumber().contains("P61") || root.getPartNumber().contains("p61"))//SS22���������¾ɹ���Աȡ�Ϊ�û����������
            {
                if(parentPart.getPartNumber().contains("1104G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1104G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1104G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1104G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
                            partNumber.contains("1104071") || partNumber.contains("CQ72316T5"))
                    {
                        if(newParentPart1104G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1104G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1104G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1104G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1104G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1104G01.getBsoID(),root.getBsoID(),"cf");
	                            falg1104G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1104130") || partNumber.contains("1104250") || 
                                partNumber.contains("1104071") || partNumber.contains("CQ72316T5"))
                        {
                            mt[0] = newParentPart1104G01.getBsoID();
                        }
                    }
                }
                
//                1119G01 ��ֵ�1000940��1119030 1119035 1119038 1119038 1119050 1119053 1119081 1119105 
//                                        1119125 1119127 CQ1461040 Q1840816 T6769006A
                if(parentPart.getPartNumber().contains("1119G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1119G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1119G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1119G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1119030") || partNumber.contains("1119035") || 
                            partNumber.contains("1119038") || partNumber.contains("1119038") || 
                            partNumber.contains("1119050") || partNumber.contains("1119053") || 
                            partNumber.contains("1119081") || partNumber.contains("1119105") || 
                            partNumber.contains("1119125") || partNumber.contains("1119127") || 
                            partNumber.contains("CQ1461040") || partNumber.contains("Q1840816") || 
                            partNumber.contains("T6769006A"))
                    {
                        if(newParentPart1119G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1119G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1119G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1119G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1119G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1119G01.getBsoID(),root.getBsoID(),"cf");
	                            falg1119G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1119030") || partNumber.contains("1119035") || 
                                partNumber.contains("1119038") || partNumber.contains("1119038") || 
                                partNumber.contains("1119050") || partNumber.contains("1119053") || 
                                partNumber.contains("1119081") || partNumber.contains("1119105") || 
                                partNumber.contains("1119125") || partNumber.contains("1119127") || 
                                partNumber.contains("CQ1461040") || partNumber.contains("Q1840816") || 
                                partNumber.contains("T6769006A"))
                        {
                            mt[0] = newParentPart1119G01.getBsoID();
                        }
                    }
                }
                
//                1303G01 ��ֵ�1000940��1303034 1303036 1303043 1303045 1303048 CQ1460825 CQ67660B Q1841245
                if(parentPart.getPartNumber().contains("1303G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1303G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1303G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1303G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1303034") || partNumber.contains("1303036") || 
                            partNumber.contains("1303043") || partNumber.contains("1303045") || 
                            partNumber.contains("1303048") || partNumber.contains("CQ1460825") || 
                            partNumber.contains("CQ67660B") || partNumber.contains("Q1841245"))
                    {
                        if(newParentPart1303G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1303G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1303G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1303G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1303G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1303G01.getBsoID(),root.getBsoID(),"cf");
	                            falg1303G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1303034") || partNumber.contains("1303036") || 
                                partNumber.contains("1303043") || partNumber.contains("1303045") || 
                                partNumber.contains("1303048") || partNumber.contains("CQ1460825") || 
                                partNumber.contains("CQ67660B") || partNumber.contains("Q1841245"))
                        {
                            mt[0] = newParentPart1303G01.getBsoID();
                        }
                    }
                }
                
//                1311G01 ��ֵ�1000940��1311021 1311021 1311022 1311023 1311031 1311046 1311055
//                                        1311060 CQ1460816(4) CQ1460820(1) CQ39610 CQ67616(3) CQ67630B(2) Q68622
                if(parentPart.getPartNumber().contains("1311G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart1311G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart1311G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart1311G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("1311021") || partNumber.contains("1311021") || 
                            partNumber.contains("1311022") || partNumber.contains("1311023") || 
                            partNumber.contains("1311031") || partNumber.contains("1311046") || 
                            partNumber.contains("1311055") || partNumber.contains("1311060") || 
                            partNumber.contains("CQ39610") || partNumber.contains("Q68622") ||
                            partNumber.contains("CQ1460816") || partNumber.contains("CQ1460820") ||
                            partNumber.contains("CQ67616") || partNumber.contains("CQ67630B") )
                    {
                        if(newParentPart1311G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart1311G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg1311G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart1311G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart1311G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart1311G01.getBsoID(),root.getBsoID(),"cf");
	                            falg1311G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("1311021") || partNumber.contains("1311021") || 
                                partNumber.contains("1311022") || partNumber.contains("1311023") || 
                                partNumber.contains("1311031") || partNumber.contains("1311046") || 
                                partNumber.contains("1311055") || partNumber.contains("1311060") || 
                                partNumber.contains("CQ39610") || partNumber.contains("Q68622"))
                        {
                            mt[0] = newParentPart1311G01.getBsoID();
                        }
                        if(partNumber.contains("CQ1460816"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart1311G01.getBsoID();
                            temp[2] = "4";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-4)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                        if(partNumber.contains("CQ1460820"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart1311G01.getBsoID();
                            temp[2] = "1";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-1)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                        if(partNumber.contains("CQ67616"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart1311G01.getBsoID();
                            temp[2] = "3";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-3)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                        if(partNumber.contains("CQ67630B"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart1311G01.getBsoID();
                            temp[2] = "2";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-2)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                    }
                }
                
//                3406G01 ��ֵ�1000940��3406011 CQ67635B(1��
                if(parentPart.getPartNumber().contains("3406G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart3406G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart3406G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart3406G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("3406011") || partNumber.contains("CQ67635B") )
                    {
                        if(newParentPart3406G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart3406G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg3406G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart3406G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart3406G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart3406G01.getBsoID(),root.getBsoID(),"cf");
	                            falg3406G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("3406011"))
                        {
                            mt[0] = newParentPart3406G01.getBsoID();
                        }
                        if(partNumber.contains("CQ67635B"))
                        {
                            String temp[]=new String[10];
                            String quantity = mt[2];
                            Float count = Float.valueOf(quantity);
                            temp[0] = newParentPart3406G01.getBsoID();
                            temp[2] = "1";
                            temp[1] = mt[1];
                            temp[3] = mt[3];
                            temp[4] = dwbs;
                            temp[5] = mt[5];
                            temp[6] = mt[6];
                            temp[7] = mt[7];
                            temp[8] = mt[8];
                            temp[9] = mt[9];
                            ret.add(temp);
                            if((count-1)<=0)
                            {
                                continue;
                            }
                            else
                            {
                                mt[2] = Float.valueOf(count-1).toString();
                            }
                        }
                    }
                }
                
//                3509G01 ��ֵ�1000940��3509284 3509381
                if(parentPart.getPartNumber().contains("3509G01"))
                {
                	//CCBegin SS63
                	if(ZCnewParentPart3509G01 == null)
                    {
	                    String numberZC = parentPart.getPartNumber()+"-ZC";
	                    ZCnewParentPart3509G01 =createNewTGYPart(parentPart,numberZC,parentPart.getPartName());
                    }
                	//mt[0] = ZCnewParentPart3509G01.getBsoID();
                    //CCEnd SS63
                    if(partNumber.contains("3509284") || partNumber.contains("3509381"))
                    {
                        if(newParentPart3509G01 == null)
                        {
                            String number = getNewPartNumber(parentPart.getPartNumber());
                            newParentPart3509G01 = createNewTGYPart(parentPart,number,parentPart.getPartName());
                        }
                        if(falg3509G01)
                        {
                            //�������߼��ܳɺ�  1104G01-382F001 �ŵ�940��
                            String[] strs = (String[])map.get(parentPart.getPartNumber());
                          //CCBegin SS57
                            if(strs!=null){
                            	//CCEnd SS57
	                            String[] str=new String[10];
	                            str[0]=partNumber1000940;
	                            str[1]=newParentPart3509G01.getBsoID();
	                            str[2]=strs[2];
	                            str[3]=strs[3];
	                            str[4]=dwbs;
	                            str[5]=strs[5];
	                            str[6]=strs[6];
	                            str[7]=strs[7];
	                            str[8]=strs[8];
	                            str[9]=newParentPart3509G01.getPartNumber();
	                            ret.add(str);
	                            createGyBomCFHistory(parentPart.getBsoID(),newParentPart3509G01.getBsoID(),root.getBsoID(),"cf");
	                            falg3509G01 = false;
	                            //CCBegin SS57
                            }
                            //CCEnd SS57
                        }
                        
                        if(partNumber.contains("3509284") || partNumber.contains("3509381"))
                        {
                            mt[0] = newParentPart3509G01.getBsoID();
                        }
                    }
                }
            }
            //�������Ͳ��
            else
            {
                
            }
        }
	    return ret;
	}
	//CCEnd SS37

	/**
	 * ���ɳ��ܳ�ʼ����BOM
	 */
	public void initGYCJBom(String partID,String gyID,String dwbs) throws QMException
	{
		try
		{
			// �����߼��ܳ�
			String partNumberRuleCJ=RemoteProperty.getProperty("gybomlistcj");
			
			// boolean gyPartFlag=false;
			Vector cjparts=new Vector();
			Collection mtree=new ArrayList();
			Collection ytree=new ArrayList();
			Vector linkvec=new Vector();
			StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
			JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(partID);
			QMPartIfc gypart=(QMPartIfc)ps.refreshInfo(gyID);
			UserInfo user=getCurrentUserInfo();
			//deleteGYBomLevel(gyID,dwbs,"0");
			//deleteBom(gypart.getPartNumber(),dwbs);
			PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
			Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);
			//CCBegin SS73
			Collection coall = new Vector();
			getAllUsedLink(coall,part);
			//CCEnd SS73
			ArrayList array = new ArrayList();//SS20
			//CCBegin SS73
//			for(Iterator iter=linkcoll.iterator();iter.hasNext();)
			for(Iterator iter=coall.iterator();iter.hasNext();)
				//CCEnd SS73
			{
				Object obj[]=(Object[])iter.next();
				PartUsageLinkIfc link=(PartUsageLinkIfc)obj[0];
				if(obj[1] instanceof QMPartIfc)
				{
					QMPartIfc subpart=(QMPartIfc)obj[1];
					
					// ����
					if(subpart.getPartNumber().length()>=7
						&&partNumberRuleCJ.indexOf(subpart.getPartNumber().substring(0,7))!=-1)
					{
						cjparts.add(new String[]{subpart.getBsoID(),String.valueOf(link.getQuantity()),
							subpart.getPartNumber()});
						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
							gypart.getPartNumber(),dwbs,user.getBsoID(),"",link.getDefaultUnit().getDisplay(),
							subpart.getPartNumber()};
						ytree.add(mt1);
					}
					else
					{
						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
							gypart.getPartNumber(),dwbs,"","",link.getDefaultUnit().getDisplay(),
							subpart.getPartNumber()};
						ytree.add(mt1);
					}
				}
			}
			// ����
			Iterator cjiter=cjparts.iterator();
			QMPartIfc cjsp=null;
			while(cjiter.hasNext())
			{
				String[] cjstr1=(String[])cjiter.next();
				cjsp=(QMPartIfc)ps.refreshInfo(cjstr1[0]);
				String[] mt1=new String[]{gyID,cjsp.getBsoID(),cjstr1[1],gypart.getPartNumber(),dwbs,"0","","","��",
					cjstr1[2]};
				mtree.add(mt1);
				mtree=getBomLinks(gyID,mtree,cjsp,cjsp.getPartNumber(),dwbs,linkvec,"cj",array, new ArrayList());//SS20//SS37
			}
			//CCBegin SS73
			// ���ݹ�����нṹ���
//			Collection ntree=chaifengybom(gyID,mtree);
			// ���ݹ���Է��Ϲ���ļ����и��������Ϊ��
//			Collection rtree=renameGyBom(ntree);
			//CCBegin SS15
//			saveGYBom(rtree, true, dwbs);//SS21//SS28
			//CCEnd SS15
			saveGYBom(mtree, true, dwbs);//SS21//SS28
			// saveGYBom(ntree);
//			saveBom(ytree);
			//CCEnd SS73
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}

	//CCBegin SS73
	/**������й���
	 * @param coall
	 * @param linkcoll
	 * @throws QMException 
	 * 
	 */
	private Collection getAllUsedLink(Collection coall, QMPartIfc part) throws QMException {
		StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
		PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
		JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
		Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);
		for(Iterator iter = linkcoll.iterator();iter.hasNext();){
			
			Object obj[]=(Object[])iter.next();
			coall.add(obj);
//			System.out.println("obj[1]===="+obj[1]);
			if(obj[1] instanceof QMPartIfc){
				QMPartIfc part1 = (QMPartIfc)obj[1];
				getAllUsedLink(coall, part1);
			}
		}
		return coall;
	}
	//CCEnd SS73

	/**
	 * ���ɼ�ʻ�ҳ�ʼ����BOM
	 */
	public void initGYJSSBom_old(String partID,String gyID,String dwbs,String newnumber) throws QMException
	{
		try
		{
			// ��ʻ���߼��ܳ�
			String partNumberRuleNS=RemoteProperty.getProperty("gybomlistns");
			Vector jssparts=new Vector();
			Collection mtree=new ArrayList();
			Collection ytree=new ArrayList();
			Vector linkvec=new Vector();
			StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
			JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(partID);
			QMPartIfc gypart=(QMPartIfc)ps.refreshInfo(gyID);
			UserInfo user=getCurrentUserInfo();
			//deleteGYBomLevel(gyID,dwbs,"0");
			//deleteBom(gypart.getPartNumber(),dwbs);
			PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
			Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);
			for(Iterator iter=linkcoll.iterator();iter.hasNext();)
			{
				Object obj[]=(Object[])iter.next();
				PartUsageLinkIfc link=(PartUsageLinkIfc)obj[0];
				if(obj[1] instanceof QMPartIfc)
				{
					QMPartIfc subpart=(QMPartIfc)obj[1];
					// ��ʻ��
					if(subpart.getPartNumber().length()>=7
						&&partNumberRuleNS.indexOf(subpart.getPartNumber().substring(0,7))!=-1)
					{
						jssparts.add(new String[]{subpart.getBsoID(),String.valueOf(link.getQuantity()),
							subpart.getPartNumber()});
						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
							gypart.getPartNumber(),dwbs,user.getBsoID(),"",link.getDefaultUnit().getDisplay(),
							subpart.getPartNumber()};
						ytree.add(mt1);
					}
					else
					{
						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
							gypart.getPartNumber(),dwbs,"","",link.getDefaultUnit().getDisplay(),
							subpart.getPartNumber()};
						ytree.add(mt1);
					}
				}
			}
			// ��ʻ��
			Iterator jssiter=jssparts.iterator();
			ArrayList array = new ArrayList();//SS20
			QMPartIfc jsssp=null;
			while(jssiter.hasNext())
			{
				String[] jssstr1=(String[])jssiter.next();
				jsssp=(QMPartIfc)ps.refreshInfo(jssstr1[0]);
				String[] mt1=new String[]{gyID,jsssp.getBsoID(),jssstr1[1],gypart.getPartNumber(),dwbs,"0","","","��",
					jssstr1[2]};
				mtree.add(mt1);
				mtree=getBomLinks(gyID,mtree,jsssp,jsssp.getPartNumber(),dwbs,linkvec,"jss",array, new ArrayList());//SS20//SS37
			}
			// ���ݹ�����нṹ���
			Collection ntree=chaifengybom(gyID,mtree);
			// ���ݹ���Է��Ϲ���ļ����и��������Ϊ��
			Collection rtree=renameGyjssBom(ntree,newnumber);

			//CCBegin SS15
			saveGYBom(rtree, true, dwbs);//SS21//SS28
			//CCEnd SS15
			// saveGYBom(ntree);
			saveBom(ytree);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	// CCEnd SS7
	
	// CCBegin SS6
	/**
	 * ��������¼
	 * 
	 * @param cxh
	 *            ���ͺ�
	 * @param gc
	 *            ����
	 * @param parentid
	 *            ����id
	 * @param subid
	 *            �Ӽ�id
	 * @param tsubid
	 *            �滻��id
	 * @param quantityb
	 *            ���ǰ����
	 * @param quantitya
	 *            ���������
	 * @param sign
	 *            ���ı�ʶ
	 * @return
	 * @throws QMException
	 */
	public String saveChangeContent(String cxh, String gc, String parentid,
			String subid, String tsubid, String quantityb, String quantitya,
			String sign) throws QMException {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String s = "";
		// CCBegin SS51
		// ��¼���ı��ǰҪ�ж��ǲ�������Ч��BOM�����û�У���ô�����м�¼������вż�¼
		// searchChangeContent(cxh,gc);
		String flag = getEffect(parentid, "", gc);
//		System.out.println("��������====="+flag);
		// ��flagΪ3��ʱ����δ��Ч�ṹ�Һ�����Ч�ṹ��������ʱ���ȥ�������������
		if (flag.equals("3")) {
//			System.out.println("����3�Ž�������");
			// CCEnd SS51
			try {
				conn = PersistUtil.getConnection();
				st = conn.createStatement();
				String mtsql = "INSERT INTO BOMChangeContent (id,carModelCode,dwbs,parentPartID,subPartID,tsubPartID,quantity1,quantity2,createTime,modifyTime,sign) VALUES ";
				String tempsql = "";
				String sendData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
				// System.out.println("sendData====="+sendData);
				rs = st.executeQuery("select BOMChangeContent_SEQ.nextval from dual");
				if (rs.next()) {
					s = rs.getString(1);
				}

				tempsql = mtsql + "('" + s + "','" + cxh + "','" + gc + "','"
						+ parentid + "','" + subid + "','" + tsubid + "','"
						+ quantityb + "','" + quantitya + "', to_date('"
						+ sendData + "','yyyy-mm-dd hh24:mi:ss'), to_date('"
						+ sendData + "','yyyy-mm-dd hh24:mi:ss'), '" + sign
						+ "')";
				// System.out.println("tempsql=====" + tempsql);
				st.executeQuery(tempsql);

				// �ر�Statement
				st.close();
				// �ر����ݿ�����
				conn.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					st.close();
					conn.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		// System.out.println("s-=-=-=-=-=" + s);
		return s;
	}

	// CCEnd SS6
	
	
	//CCBegin SS9
	/**�滻��
	 * @param linkid linkid
	 * @param yid ԭ�ڵ�id
	 * @param xid �滻�ڵ�id
	 * @param carModelCode ����
	 * @param dwbs ����
	 * @return
	 * @throws QMException
	 */
	//CCBegin SS71
//	public String changePart(String linkid,String yid,String xid,String carModelCode,String dwbs) throws QMException
	public String changePart(String linkid,String yid,String xid,String carModelCode,String dwbs,String parentid,String changeType) throws QMException
	//CCEnd SS71
	{
		// System.out.println("come in changePart");
		String str = "";
		try {
			// CCBegin SS71
			if (changeType.equals("all")) {
				// CCEnd SS71
				PersistService ps = (PersistService) EJBServiceHelper
						.getPersistService();
				StandardPartService spService = (StandardPartService) EJBServiceHelper
						.getService("StandardPartService");
				// VersionControlService
				// vcService=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
				QMPartIfc part = (QMPartIfc) ps.refreshInfo(yid);
				// QMPartMasterIfc
				// partmaster=(QMPartMasterIfc)ps.refreshInfo(part.getMasterBsoID());
				// Collection co=vcService.allVersionsOf(partmaster);
				// Iterator itea=co.iterator();
				// QMPartIfc npart=null;
				QMPartIfc nnpart = (QMPartIfc) ps.refreshInfo(xid);
				// while(itea.hasNext())
				// {
				// QMPartIfc partIfc=(QMPartIfc)itea.next();
				// if(part.getBsoID().equals(partIfc.getBsoID()))
				// {
				// nnpart=npart;
				// }
				// npart=partIfc;
				// }

				// if(nnpart==null)
				// {
				// return "[{δ���ҵ���ǰ�Ӽ���һ�汾��}]";
				// }

				// ���¹������Ӽ�id�滻����һ���㲿��id
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				//CCBegin SS71
				Statement st = null;
				Statement st1 = null;
//				ResultSet rs1 = null;
				//CCEnd SS71
				try {
					conn = PersistUtil.getConnection();
					//CCBegin SS71
					st=conn.createStatement();
					String sql="select distinct parentPart from GYBomStructure where childPart='"
							+part.getBsoID()+"'";
					rs=st.executeQuery(sql);
					while(rs.next()){
						String parentbsoid = rs.getString(1);
						st1 = conn.createStatement();
						st1.executeQuery("update GYBomStructure set bz1='red' where effectCurrent='0' and childPart='"+parentbsoid+ "'");
						st1.close();
					}
					//CCEnd SS71
					stmt = conn.createStatement();
					// CCBegin SS59
					// stmt.executeQuery("update GYBomStructure set childPart='"+nnpart.getBsoID()
					stmt.executeQuery("update GYBomStructure set childPart='"
							+ nnpart.getBsoID() + "',childNumber='"
							+ nnpart.getPartNumber() + "',bz1='" + "red"
							// CCEnd SS59
							+ "' where effectCurrent='0' and childPart='"
							+ part.getBsoID() + "' and dwbs='" + dwbs + "'");

					st.close();
					// �ر�Statement
					stmt.close();
					// �ر����ݿ�����
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (rs != null) {
							rs.close();
						}
						if (stmt != null) {
							stmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException ex1) {
						ex1.printStackTrace();
					}
				}

				// ɾ����ǰ�Ӽ�����
				Collection ids = new ArrayList();
				ids = bianliGYBomid(ids, part.getBsoID(), carModelCode, dwbs);
				deleteGYBom(ids);

				// �����Ӽ�����
				Collection mtree = new ArrayList();
				Vector linkvec = new Vector();
				mtree = getBomLinks(mtree, nnpart, carModelCode, dwbs, linkvec);
				// CCBegin SS15
				saveGYBom(mtree, true, dwbs);// SS21
				// CCEnd SS15

				// ��ȡ��ǰ�㲿�������Ӽ����½ṹ
				str = getGYBomAll(linkid, nnpart.getBsoID(), carModelCode, dwbs);
				// CCBegin SS71
			} else if (changeType.equals("only")) {
				PersistService ps = (PersistService) EJBServiceHelper
						.getPersistService();
				StandardPartService spService = (StandardPartService) EJBServiceHelper
						.getService("StandardPartService");
				QMPartIfc part = (QMPartIfc) ps.refreshInfo(yid);
				QMPartIfc nnpart = (QMPartIfc) ps.refreshInfo(xid);

				// ���¹������Ӽ�id�滻����һ���㲿��id
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				Statement st1 = null;
				try {
					conn = PersistUtil.getConnection();
					stmt = conn.createStatement();
					stmt.executeQuery("update GYBomStructure set childPart='"
							+ nnpart.getBsoID() + "',childNumber='"
							+ nnpart.getPartNumber() + "',bz1='" + "red"
							+ "' where effectCurrent='0' and childPart='"
							+ part.getBsoID() + "' and parentPart='" + parentid
							+ "' and dwbs='" + dwbs + "'");
					st1 = conn.createStatement();
					st1.executeQuery("update GYBomStructure set bz1='red' where effectCurrent='0' and childPart='"+parentid+ "'");
					// �ر�Statement
					stmt.close();
					st1.close();
					// �ر����ݿ�����
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (rs != null) {
							rs.close();
						}
						if (stmt != null) {
							stmt.close();
						}
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException ex1) {
						ex1.printStackTrace();
					}
				}
				// �����Ӽ�����
				Collection mtree = new ArrayList();
				Vector linkvec = new Vector();
				mtree = getBomLinks(mtree, nnpart, carModelCode, dwbs, linkvec);
				saveGYBom(mtree, true, dwbs);
				// ��ȡ��ǰ�㲿�������Ӽ����½ṹ
				str = getGYBomAll(linkid, nnpart.getBsoID(), carModelCode, dwbs);
			} else if (changeType.equals("part")) {

			}
			// CCEnd SS71
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	//CCEnd SS9
	
//CCBegin SS30
	/**��ѯ������ǩ
	 * @param carModelCode ������
	 * @return 
	 */
	public String searchLinkBook(String carModelCode) throws QMException{
		JSONArray json=new JSONArray();
		try {
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part;
			// ���ø��ڵ�
			JSONObject jo=new JSONObject();
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			//CCBegin SS36
			Statement stmt1=null;
			ResultSet rs1=null;
			Statement stmt2=null;
			ResultSet rs2=null;
			//CCEnd SS36
			try
			{
				conn=PersistUtil.getConnection();
				stmt=conn.createStatement();
				String sql="select distinct parentPart from GYBomStructureLable where carModelCode='"
						+carModelCode+"'";
				rs=stmt.executeQuery(sql);
				while (rs.next()){
					String partid=rs.getString(1);
					//CCBegin SS36
					//�����ж��Ӽ��в������������������ǾͲ�����ǩ
					stmt1=conn.createStatement();
					String sql1 = "select count(*) childPart from GYBomStructure where childPart='"+partid+"'";
					rs1=stmt1.executeQuery(sql1);
					rs1.next();
					int count1=rs1.getInt(1);
					//Ȼ���жϸ����������б�����в�������ǩ������������Ҫ����ſ���ȷ��
					stmt2=conn.createStatement();
					String sql2 = "select count(*) parentPart from GYBomStructure where parentPart='"+partid+"'";
					rs2=stmt2.executeQuery(sql2);
					rs2.next();
					int count2=rs2.getInt(1);
//					System.out.println("����count1======="+count1);
//					System.out.println("����count2======="+count2);
					if(count1==0&&count2==0){
						part=(QMPartIfc)ps.refreshInfo(partid);
						//CCBegin SS42
						String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",partid);
						if(fbyIbaValue==""){
							fbyIbaValue = part.getVersionValue();
						}
						//CCEnd SS42
						jo=new JSONObject();
						//CCEnd SS36
						jo.put("id",partid);
						jo.put("num",part.getPartNumber());
						jo.put("pname",part.getPartName());
						//CCBegin SS42
//						jo.put("version",part.getVersionValue());
						jo.put("version",fbyIbaValue);
						//CCEnd SS42
						json.put(jo);
					}
				}
				//CCBegin SS36
				rs1.close();
				stmt1.close();
				//CCEnd SS36
				// ��ղ��ر�sql��������
				rs.close();

				// �ر�Statement
				stmt.close();
				// �ر����ݿ�����
				conn.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if(rs!=null)
					{
						rs.close();
					}
					if(stmt!=null)
					{
						stmt.close();
					}
					if(conn!=null)
					{
						conn.close();
					}
				}
				catch(SQLException ex1)
				{
					ex1.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json.toString();
	}
	//CCEnd SS30
	
	//CCBegin SS34
	/**����BOM
	 * @param id
	 * @param carModelCode
	 * @param dwbs
	 * @return
	 * @throws QMException
	 */
	public Vector getExportBOMList(String id,String carModelCode,String dwbs) throws QMException
	{
		Vector vec=new Vector();
		HashMap routemap=new HashMap();
		bianliBOMList(vec,id,carModelCode,dwbs,0,"",routemap);
//		String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",id);
//        //�㲿������Դ�汾
//        String sourceVersion = fbyIbaValue.substring(0, fbyIbaValue.indexOf("."));
//		System.out.println("vec=="+vec.size());
		/*
		 * for(int i=0;i<vec.size();i++) { String[] str =
		 * (String[])vec.elementAt(i);
		 * System.out.println(str[0]+"&&"+str[1]+"&&"
		 * +str[2]+"&&"+str[3]+"&&"+str[4]+"&&"+str[5]+"&&"+str[6]+"&&"+str[7]);
		 * }
		 */
		return vec;
	}
	
	/**
	 * ����BOM ָ�����͡�������BOM���ϡ� ��ȡ�Ӽ�id�͵�ǰ�û���������Чbom�еĸ����� 
	 * ���ؼ���:���� ��� �汾 ���� ���� ����·�� װ��·�� �������
	 */
	private void bianliBOMList(Vector vec,String id,String carModelCode,String dwbs,int level,String pzz,
		HashMap routemap) throws QMException
	{
		PersistService ps=(PersistService)EJBServiceHelper.getPersistService();

		String effect=getEffect(id,carModelCode,dwbs);
		if(effect.equals("1"))
		{
			effect="0";
		}
		else if(effect.equals("2")||effect.equals("3"))
		{
			effect="1";
		}
//		System.out.println("effect===="+effect);
		// ��ȡ�Ӽ�id������
		Vector subvec=findChildPart(id,carModelCode,dwbs,effect);
//		System.out.println("subvec.size()===="+subvec.size());
		QMPartIfc parentpart=(QMPartIfc)ps.refreshInfo(id);

		// ��ȡ�Ӽ�·��
		String childPart="";
		QMPartIfc part=null;
		String insql="";
		
//		if(dwbs.equals("W34"))
//		{
//			routemap=getSecondRoute(insql,routemap);
//		}
//		else
//		{
//			routemap=getRoute(insql,routemap);
//		}
		if(level==0){
			String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",id);
			//CCBegin SS61
//		String sourceVersion="";
//		if(fbyIbaValue!=null&&fbyIbaValue!=""){
//				//�㲿������Դ�汾
//				sourceVersion = fbyIbaValue.substring(0, fbyIbaValue.indexOf("."));
//		}
			
			PartHelper parthelper = new PartHelper();
			String partVersion = parthelper.getPartVersion(parentpart);
			String partnumber = parthelper.getMaterialNumber(parentpart,partVersion);	
//			vec.add(new String[]{Integer.toString(level),parentpart.getPartNumber(),sourceVersion,parentpart.getPartName(),"0","","",""});
			vec.add(new String[]{Integer.toString(level),partnumber,parentpart.getPartName(),"0","","",""});
			//CCEnd SS61
		}
			
		for(int i=0;i<subvec.size();i++)
		{
			String[] str=subvec.elementAt(i).toString().split(",");
			childPart=str[0];
			part=(QMPartIfc)ps.refreshInfo(childPart);
			/*if(level==2)
			{
				System.out.println("2��"+part.getPartNumber());
			}*/
			if(part==null)
			{
				System.out.println("δ�ҵ��Ӽ�id��"+childPart);
				continue;
			}
			if(routemap.containsKey(part.getMasterBsoID()))
			{
				continue;
			}
			if(insql.equals(""))
			{
				if(dwbs.equals("W34"))
				{
					insql="'"+part.getBsoID()+"'";
				}
				else
				{
					insql="'"+part.getMasterBsoID()+"'";
				}
			}
			else
			{
				if(dwbs.equals("W34"))
				{
					insql=insql+",'"+part.getBsoID()+"'";
				}
				else
				{
					insql=insql+",'"+part.getMasterBsoID()+"'";
				}
			}
		}
		if(!insql.equals(""))
		{
			if(dwbs.equals("W34"))
			{
				routemap=getSecondRoute(insql,routemap);
			}
			else
			{
				routemap=getRoute(insql,routemap);
			}
		}
		// �ݹ��ȡ�Ӽ�
		String zhizao="";
		String zhuangpei="";

		com.faw_qm.cderp.util.PartHelper helper=new com.faw_qm.cderp.util.PartHelper();

		for(int i=0;i<subvec.size();i++)
		{
			String[] str=subvec.elementAt(i).toString().split(",");
			childPart=str[0];
			part=(QMPartIfc)ps.refreshInfo(childPart);

			if(dwbs.equals("W34"))
			{
				if(routemap.get(part.getBsoID())!=null)
				{
					String[] routestr=(String[])(routemap.get(part.getBsoID()));
					zhizao=routestr[0];
					zhuangpei=routestr[1];
				}
			}
			else
			{
				if(routemap.get(part.getMasterBsoID())!=null)
				{
					String[] routestr=(String[])(routemap.get(part.getMasterBsoID()));
					zhizao=routestr[0];
					zhuangpei=routestr[1];
				}
			}
			if(zhuangpei.equals(""))
			{
				zhuangpei=pzz;
			}
//			String pnum=helper.getMaterialNumber(part,part.getVersionID());
//			
//			String fbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",id);
//			System.out.println("fbyIbaValue======="+fbyIbaValue);
			//CCBegin SS61
//			String sourceVersion="";
//			if(fbyIbaValue!=null&&fbyIbaValue!=""){
//		        //�㲿������Դ�汾
//		        sourceVersion = fbyIbaValue.substring(0, fbyIbaValue.indexOf("."));
//			}
			////CCBegin SS83
			//PartHelper parthelper = new PartHelper();
			com.faw_qm.cderp.util.PartHelper parthelper=new com.faw_qm.cderp.util.PartHelper();
			String partVersion = parthelper.getPartVersion(part);
//			CCEnd SS83
			String partnumber = parthelper.getMaterialNumber(part,partVersion);
	
			//CCEnd SS61
			//CCBegin SS34
			partnumber = getPartNumber(part,partnumber);
			//CCEnd SS34

			//���� ��� �汾 ���� ���� ����·�� װ��·�� �������
//			vec.add(new String[]{Integer.toString(level),pnum,part.getPartName(),str[1],zhizao,zhuangpei,parentpart.getPartNumber()});
			//CCBegin SS61
//			String[] aa = new String[]{Integer.toString(level+1),part.getPartNumber(),fbyIbaValue,part.getPartName(),str[1],zhizao,zhuangpei,parentpart.getPartNumber()};
			String[] aa = new String[]{Integer.toString(level+1),partnumber,part.getPartName(),str[1],zhizao,zhuangpei,parentpart.getPartNumber()};
			//CCEnd SS61
			vec.add(aa);
			//CCEnd SS19
			bianliBOMList(vec,childPart,carModelCode,dwbs,level+1,zhizao,routemap);

			zhizao="";
			zhuangpei="";
		}
	}
	//CCEnd SS34
	//CCBegin SS38
		/**����·�߱�
		 * @param productpart
		 * @return
		 * @throws QMException
		 */
	//CCBegin SS49
//		public TechnicsRouteListIfc createRouteList(String parentPartMasterID) throws QMException
	public TechnicsRouteListIfc createRouteList(QMPartIfc productpart) throws QMException
	//CCEnd SS49
		{
//			System.out.println("parentPartMasterID-=========="+parentPartMasterID);
			long namefix = System.currentTimeMillis();
//			System.out.println("namefix=============="+namefix);
			String listNumber = "intGYBOM" + productpart.getPartNumber();
			try {
				
				PersistService pservice = (PersistService) EJBServiceHelper
						.getPersistService();
				//CCBegin SS49
//				TechnicsRouteListIfc reloadTRLIfc = new TechnicsRouteListInfo();
				TechnicsRouteService trs=(TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
				TechnicsRouteListIfc reloadTRLIfc = trs.findRouteListByNum(listNumber);
				if(reloadTRLIfc!=null){
					return reloadTRLIfc;
				}else{
					reloadTRLIfc = new TechnicsRouteListInfo();
				}
				//CCEnd SS49
				TechnicsRouteListMasterIfc master = new TechnicsRouteListMasterInfo();
				master.setRouteListNumber(listNumber);
				master.setRouteListName(listNumber);
				master.setProductMasterID(productpart.getMasterBsoID());
				master = (TechnicsRouteListMasterIfc) 
				pservice.saveValueInfo(master);

				reloadTRLIfc.setRouteListNumber(listNumber);
				reloadTRLIfc.setRouteListName(listNumber);
				reloadTRLIfc.setRouteListLevel("һ��·��");
				reloadTRLIfc.setRouteListState("��׼");
				reloadTRLIfc.setDefaultDescreption("��ʼ����׼");
				reloadTRLIfc.setProductMasterID(productpart.getMasterBsoID());
				reloadTRLIfc.setProductID(productpart.getBsoID());
				//����setPartIndex
				Vector vec = new Vector();
				String[] restr = new String[6];
				restr[0]=productpart.getMasterBsoID();
				restr[1]=productpart.getPartNumber();
				restr[2]="1";
				restr[3]=productpart.getBsoID();
				restr[4]="0";
				restr[5]="����";
				vec.add(restr);
				reloadTRLIfc.setPartIndex(vec);

				String folderpath = "\\Root\\����׼��֪ͨ��";
				String lifeCycleTemplateName = "ȱʡ";

				FolderService fservice = (FolderService) EJBServiceHelper
				.getService("FolderService");
				LifeCycleService lservice = (LifeCycleService) EJBServiceHelper
				.getService("LifeCycleService");
				fservice.assignFolder(reloadTRLIfc, folderpath);
				LifeCycleTemplateInfo temp = lservice
				.getLifeCycleTemplate(lifeCycleTemplateName);
				reloadTRLIfc = 
					(TechnicsRouteListIfc)lservice.setLifeCycle(reloadTRLIfc,temp);
				reloadTRLIfc.setMaster(master);
				reloadTRLIfc.setMasterBsoID(master.getBsoID());

				reloadTRLIfc = 
					(TechnicsRouteListIfc)pservice.saveValueInfo(reloadTRLIfc);
				LifeCycleState state = LifeCycleState.toLifeCycleState("RELEASED");
				reloadTRLIfc.setLifeCycleState(state);
				reloadTRLIfc = 
					(TechnicsRouteListIfc) pservice.saveValueInfo(reloadTRLIfc);
				return reloadTRLIfc;
			}
			catch (QMException ex)
			{
				ex.printStackTrace();
				throw new QMException(ex);
			}
		}
		
		/**
		 * �����㲿����·�߲����浽���ݿ��С��ڸ÷����У�����·�ߴ���������·�ߵ��롣
		 * @param list ·�߱�
		 * @param part PDM�㲿��
		 * @param source �м���㲿��
		 * @return
		 * @throws QMException 
		 */
		public ListRoutePartLinkIfc createListRoutePartLink
			(TechnicsRouteListIfc list ,QMPartIfc part ,QMPartIfc source) 
																throws QMException
		{
			long a = System.currentTimeMillis();
			
			
			//��ó־û�����
			PersistService pservice = (PersistService) EJBServiceHelper
															.getPersistService();
			ListRoutePartLinkIfc listPart = 
				ListRoutePartLinkInfo.newListRoutePartLinkInfo
					(list, part.getMaster().getBsoID(), part.getBsoID());
//			System.out.println("route0000��ʱ*****" +(System.currentTimeMillis() - a));
//			a = System.currentTimeMillis();
			//����·�߶���
			TechnicsRouteIfc routeInfo = new TechnicsRouteInfo();
			routeInfo.setRouteDescription("��ʼ������·��");
			routeInfo.setModefyIdenty(getCodingIfc("����"));
			//����֮ǰҪ����һ��TechnicsRouteIfc����
			routeInfo = (TechnicsRouteIfc) pservice.saveValueInfo(routeInfo);
			if(routeInfo == null)
			{
				throw new QMException("����·�߶������");
			}
			listPart.setRouteID(routeInfo.getBsoID());
			listPart.setAdoptStatus("����");
			listPart.setAlterStatus(1);
//			listPart.setPartBranchID(part.getBsoID());
//			if(mPart.getColorFlag() != null 
//				&&mPart.getColorFlag().trim().length() > 0 
//					&&mPart.getColorFlag().equalsIgnoreCase("Y"))
//			{
//				listPart.setColorFlag("1");
//			}
//			if(mPart.getColorFlag() != null 
//				&&mPart.getColorFlag().trim().length() > 0 
//					&&mPart.getColorFlag().equalsIgnoreCase("N"))
//			{
				listPart.setColorFlag("0");
//			}
			//CCBegin SS49
			//�����б����Ʒ������ƺ�id
			listPart.setParentPartID(list.getProductID());
			listPart.setParentPartNum(part.getPartNumber());
			listPart.setParentPartName(part.getPartName());
			//CCEnd SS49
			listPart = (ListRoutePartLinkIfc) pservice.saveValueInfo(listPart);
//			System.out.println("route333333333��ʱ*****" +(System.currentTimeMillis() - a));
//			a = System.currentTimeMillis();
			if(listPart == null)
			{
				throw new QMException("�����㲿��"+ part.getPartNumber() + "��·�߱��������");
			}
			boolean flag = false;
			flag = saveRouteBranch(routeInfo, source);
//			System.out.println("route333333333��ʱ*****" +(System.currentTimeMillis() - a));
//			a = System.currentTimeMillis();
			if(flag)
			{
				return listPart;
			}
			else
			{
				return null;
			}
		}
		
		/**
		 * ���ݱ�ǻ��·�߱�Ƕ���
		 * @param ·�߱��
		 * @return ·�߱�Ƕ���
		 * @author houhf
		 */
		private CodingIfc getCodingIfc(String codestr) throws QMException 
		{
			//·�߱��
			CodingIfc codeifc = null;
			if(codeifc == null)
			{
				try
				{
					String codeid = getDepartmentID(codestr);
					PersistService pservice = 
							(PersistService) EJBServiceHelper.getPersistService();
					codeifc = (CodingIfc)pservice.refreshInfo(codeid);
				}
				catch (Exception e) 
				{
					System.out.println("��ȡ·�߱�Ǵ��󣡣���");
					e.printStackTrace();
				}
			}
			
			return codeifc;
		}
		
		/**
		 * ���ݵ�λ��ƻ�õ�λbsoID
		 * @param departmentName ���
		 * @return ��λbsoID
		 * @author houhf
		 */
		private String getDepartmentID(String departmentName)throws QMException
		{
			//����·�ߵ�λID key��λ���� value��λID
			HashMap<String, String> departmentMap = null;
			if(departmentMap == null)
			{
				departmentMap = new HashMap<String, String>();
				
				try
				{
					//�������ݿ�����
					Connection conn=null;
					Statement stmt=null;
					ResultSet rs=null;
					//����CODINGEJB��
					String sql = "select t.shorten, t.BSOID from CODINGEJB t";
					conn=PersistUtil.getConnection();
					stmt=conn.createStatement();
					rs=stmt.executeQuery(sql);
					while(rs.next())
					{
						if(rs.getString(1) != null 
							&& rs.getString(1).trim().length()>0)
						{
							if(!departmentMap.containsKey(rs.getString(1)))
							{
								departmentMap.put(rs.getString(1), rs.getString(2));
							}
						}
					}
					
					//����CODINGEJB��
					String sql1 = 
					  "select t.classSort, t.BSOID from CodingClassificationEJB t";
					Statement stmt1=null;
					stmt1=conn.createStatement();
					ResultSet rs1 = stmt1.executeQuery(sql1);
					while(rs1.next())
					{
						if(rs1.getString(1) != null 
							&& rs1.getString(1).trim().length()>0)
						{
							if(!departmentMap.containsKey(rs1.getString(1)))
							{
								departmentMap
										.put(rs1.getString(1), rs1.getString(2));
							}
						}
					}
//					System.out.println("departmentName=================="+departmentName);
					//�ر�����
					stmt.close();
					stmt1.close();
					//conn.close();
					if(departmentMap.get(departmentName) == null 
						|| departmentMap.get(departmentName).trim().length() == 0)
						return null;
//						System.out.println("departmentMap.get(departmentName)================"+departmentMap.get(departmentName));
					return departmentMap.get(departmentName);
				}
				catch (Exception e) {
					System.out.println("��ȡ·�ߵ�λID��������");
					e.printStackTrace();
					CodingManageService service = 
						(CodingManageService) EJBServiceHelper.
												getService("CodingManageService");
					String str = service.getIDbySort(departmentName);
					return str;
				}
			}
			else
			{
				if(departmentMap.get(departmentName) == null 
					|| departmentMap.get(departmentName).trim().length() == 0)
					return null;
				
				return departmentMap.get(departmentName);
			}
		}
		
		/**
		 * ����·�߷�֧��
		 * @param hashtable ���Ա�
		 * @param vector Ҫ�������־��Ϣ
		 * @return ���������Ƿ���ȷ
		 * @author houhf
		 */
		
		private boolean saveRouteBranch(TechnicsRouteIfc routeInfo,QMPartIfc part)
		{
			try
			{
				//���ÿ��·���м�����֧
				int count = 0;
				PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
				
				count = count + 1;
				//����·��
				String manuRoute = "";
				//װ��·��
				String assRoute="";
				HashMap map=new HashMap();
				
				//����·��
				manuRoute = "��";
				//װ��·��
				assRoute="��";
				//����·�ߴ�
				String routeBranchStr;
				if(manuRoute != null && manuRoute.length()>0)
				{
					routeBranchStr = manuRoute.replaceAll("-","��");
				}
				else
				{
					routeBranchStr = "��";
				}
				if(assRoute != null && assRoute.length()>0)
				{
					routeBranchStr = routeBranchStr + "@" + assRoute;
				}
				else
				{
					routeBranchStr = routeBranchStr + "@��";
				}

				TechnicsRouteBranchIfc routeBranchInfo = new TechnicsRouteBranchInfo();
				routeBranchInfo.setMainRoute(true);

				routeBranchInfo.setRouteStr(routeBranchStr);
				routeBranchInfo.setRouteInfo(routeInfo);
//				System.out.println("*********"+routeBranchInfo.getMainRoute());
//				System.out.println("&&&&&&&&&&&&&&&&&&&&&&"+routeBranchInfo.getRouteStr());
//				System.out.println("/////////////////////"+routeBranchInfo.getRouteInfo().getBsoID());
				
				routeBranchInfo = (TechnicsRouteBranchIfc) pservice.saveValueInfo(routeBranchInfo);
				
				Vector<RouteNodeIfc> nodeVec = new Vector<RouteNodeIfc>();
//				System.out.println("����������·�߼�����======" + manuRoute);
				//����·��Node
				if(manuRoute != null && manuRoute.trim().length() > 0)
				{
					Collection nodeString = new Vector();
					StringTokenizer token = new StringTokenizer(manuRoute, "-");
					while(token.hasMoreTokens())
					{
						nodeString.add(token.nextToken());
					}
					int y = 60 * count;
					int x = 0;
					for(Iterator manuIterator = nodeString.iterator();manuIterator.hasNext();)
					{
						String manu = (String)manuIterator.next();
						if(manu == null || manu.trim().length()==0)
							continue;
						//����·��ID������ʱ��������
						String departmentID = getDepartmentID(manu);
						RouteNodeIfc nodeInfo = new RouteNodeInfo();
						nodeInfo.setRouteType(RouteCategoryType.MANUFACTUREROUTE.getDisplay());
						nodeInfo.setNodeDepartment(departmentID);
						nodeInfo.setRouteInfo(routeInfo);
						x = x + 100;
						nodeInfo.setX(new Long(x).longValue());
						nodeInfo.setY(new Long(y).longValue());
//						System.out.println("node == setRouteType=======" + RouteCategoryType.MANUFACTUREROUTE.getDisplay());
//						System.out.println("node == ��ǰ����·�ߴ���======" + manu);
//						System.out.println("node == setNodeDepartment==" + getDepartmentID(manu));
//						System.out.println("node == setRouteInfo=======" + routeInfo.getBsoID());
						nodeInfo = (RouteNodeIfc) pservice.saveValueInfo(nodeInfo);
						nodeVec.add(nodeInfo);
					}
				}
				//װ��·��
				if(assRoute != null && assRoute.trim().length() > 0)
				{
					//����·��ID������ʱ��������
					String departmentID = getDepartmentID(assRoute);
					if(departmentID != null && departmentID.trim().length()>0)
					{
						int y = 60 * count;
						int x = 0;
						RouteNodeIfc nodeInfo = new RouteNodeInfo();
						nodeInfo.setRouteType
									(RouteCategoryType.ASSEMBLYROUTE.getDisplay());
						nodeInfo.setNodeDepartment(departmentID);
						nodeInfo.setRouteInfo(routeInfo);
						x = x + 100;
						nodeInfo.setX(new Long(x).longValue());
						nodeInfo.setY(new Long(y).longValue());
//						System.out.println("node == setRouteType=======" + RouteCategoryType.MANUFACTUREROUTE.getDisplay());
//						System.out.println("node == ��ǰװ��·�ߴ���======" + assRoute);
//						System.out.println("node == setNodeDepartment==" + getDepartmentID(assRoute));
//						System.out.println("node == setRouteInfo=======" + routeInfo.getBsoID());
						nodeInfo = (RouteNodeIfc) pservice.saveValueInfo(nodeInfo);
						nodeVec.add(nodeInfo);
					}
				}
				
				//���ýڵ����
				for(int i=0;i<nodeVec.size();i++)
				{
					RouteNodeIfc nodeInfo = nodeVec.get(i);
					if(i<nodeVec.size()-1)
					{
						RouteNodeIfc destinationInfo = nodeVec.get(i+1);
						RouteNodeLinkIfc nodeLinkInfo1 = new RouteNodeLinkInfo();
						nodeLinkInfo1.setSourceNode(nodeInfo);
						nodeLinkInfo1.setDestinationNode(destinationInfo);
						nodeLinkInfo1.setRouteInfo(routeInfo);
						pservice.saveValueInfo(nodeLinkInfo1);
					}
				}
				
				//���÷�֧��ڵ����
				for(int i=0;i<nodeVec.size();i++)
				{
					RouteNodeIfc nodeInfo1 = nodeVec.get(i);
					RouteBranchNodeLinkIfc branchNodeLinkInfo = new RouteBranchNodeLinkInfo();
					branchNodeLinkInfo.setRouteBranchInfo(routeBranchInfo);
					branchNodeLinkInfo.setRouteNodeInfo(nodeInfo1);
					pservice.saveValueInfo(branchNodeLinkInfo);
				}
			}
			catch (QMException e)
			{
				e.printStackTrace();
//				String mes = "����·�߲��ֳ���" + "\n" + "�쳣��Ϣ�ǣ�" + "\n";
//				mes += e.getClientMessage();
//				writeLog(mes);
				return false;
			}
			return true;
		}
		//CCEnd SS38
		//CCBegin SS39
		/**ϵͳ�Զ�ʶ��3724015-240��3724015-240/A��T67437676��T67420376��ֱ���滻������Ҫ�����
		 * @param co
		 * @return
		 * @throws QMException 
		 */
		public Collection replacePart(Collection co) throws QMException{
			Collection col = new ArrayList();
			for(Iterator iter = co.iterator();iter.hasNext();){
				String[] strs = (String[])iter.next();
				String str9 = strs[9];//�Ӽ����
				if(str9.trim().equals("T67437676")){
					String partid = getPartBsoID("3723011-81W-C00/AL01");
					if(partid!=null&&!partid.trim().equals("")){
						strs[9] = "3723011-81W-C00/AL01";
						strs[1] = partid;
					}
				}
				if(str9.trim().equals("T67420376")){
					String partid = getPartBsoID("3723011-81W-C00/AL01");
					if(partid!=null&&!partid.trim().equals("")){
						strs[9] = "3723011-81W-C00/AL01";
						strs[1] = partid;
					}
				}
				if(str9.trim().equals("3724015-240")){
					String partid = getPartBsoID("3723012-81W-C00/AL01");
					if(partid!=null&&!partid.trim().equals("")){
						strs[9] = "3723012-81W-C00/AL01";
						strs[1] = partid;
					}
				}
				if(str9.trim().equals("3724015-240/A")){
					String partid = getPartBsoID("3723012-81W-C00/AL01");
					if(partid!=null&&!partid.trim().equals("")){
						strs[9] = "3723012-81W-C00/AL01";
						strs[1] = partid;
					}
				}
				col.add(strs);
			}
			return col;
		}
		/**������bsoid
		 * @param partnum
		 * @return
		 * @throws QMException 
		 */
		public String getPartBsoID(String partnum) throws QMException{
			String partid = null;
			String masterid = findPartMaster(partnum);
			if(masterid!=null&&!masterid.trim().equals("")){
				QMPartInfo part = getPartByMasterID(masterid);
				if(part!=null){
					partid = part.getBsoID();
				}
			}
			return partid;
		}
		/**
	     * ͨ���㲿�������ֺͺ�������㲿��������Ϣ�����صļ�����ֻ��һ��QMPartMasterIfc����
	     * @param partNumber :String �㲿���ĺ��롣
	     * @return collection:���ҵ���QMPartMasterIfc�㲿������Ϣ����ļ��ϣ�ֻ��һ��Ԫ�ء�
	     * @throws QMException
	     */
	    public String findPartMaster(String partNumber)
	        throws QMException
	    {
	        PartDebug.trace(this, PartDebug.PART_SERVICE, "findPartMaster begin ....");
	        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
	        QMQuery query = new QMQuery("QMPartMaster");
	        QueryCondition condition1 = new QueryCondition("partNumber", "=", partNumber);
	        query.addCondition(condition1);
	        PartDebug.trace(this, PartDebug.PART_SERVICE, "findPartMaster end....return is Collection ");
	        Collection col = pservice.findValueInfo(query);
	        QMPartMasterIfc masterIfc = null;
	        String masterid = null;
	        for(Iterator iter = col.iterator();iter.hasNext();){
	        	 masterIfc = (QMPartMasterIfc)iter.next();
	        	 masterid = masterIfc.getBsoID();
	        }
	        return masterid;
	    }
	    /**
		 * ͨ��masterid��ȡ���°汾���㲿����
		 * @param masterID masterid
		 * @return QMPartInfo ���°汾���㲿��(QMPart);
		 * @throws QMException
		 * @see QMPartInfo
		 */
		public QMPartInfo getPartByMasterID(String masterID) throws QMException
		{
	        PartServiceHelper pshelper= new PartServiceHelper();
			QMPartInfo partInfo = pshelper.getPartByMasterID(masterID);
			return partInfo;
		}
	    //CCEnd SS39
		
		
	// CCBegin SS40
	/**
	 * �ṹ�Ƚ� ��ȡ���BOM�ṹ������BOM�ṹ���Ƚ��㲿��������������
	 * @param desginID ���BOM����id
	 * @param gyID ����BOM����id
	 * @param carModelCode ������
	 * @param dwbs ����
	 * @param isRelease �Ƿ񷢲�
	 * @return �������Ԫ�صļ��ϣ���š����BOM�����������BOM�а汾������BOM������������BOM�а汾��
	 * @throws QMException
	 */
	public Vector CompartTreeResult1(String desginID, String gyID,
			String carModelCode, String dwbs, String isRelease)
			throws QMException {
		Vector vector = new Vector();
		try {
//			long t1 = System.currentTimeMillis();
			PartServiceHelper psh = new PartServiceHelper();
			PersistService ps = (PersistService) EJBServiceHelper
					.getPersistService();
			// ��ȡ���BOM�ṹ
			Vector vec = psh.getBOMList(desginID,
					"partNumber-0,partName-0,quantity-0,", "", "");
//			long t2 = System.currentTimeMillis();
//			System.out.println("CompartTreeResult  getBOMList  ��ʱ�� "
//					+ (t2 - t1) + " ����");
			// System.out.println(vec.size());
			HashMap designMap = new HashMap();
			// vec�е�һ��Ԫ���Ǳ�ͷ���ڶ���Ԫ���ǳ��ͣ����Դӵ�����Ԫ�ؿ�ʼ�����BOM�еĽṹ
			for (int i = 2; i < vec.size(); i++) {
				Object obj = vec.elementAt(i);
				// System.out.println(obj.getClass());
				if (obj instanceof String[]) {
					String[] str = (String[]) obj;
					System.out.println(str[4] + "**" + str[6]);
				} else if (obj instanceof Object[]) {
					Object[] str = (Object[]) obj;
					// System.out.println(str[0] + "&&&&&&&&&&&" + str[4]
					// + "*******" + str[6]);
					designMap.put(str[4], (new String[] { str[0].toString(),
							str[6].toString() }));
				} else {
					System.out.println("other");
				}
			}
//			System.out.println("designMap:" + designMap.size());

//			long t3 = System.currentTimeMillis();
//			System.out.println("CompartTreeResult  designMap  ��ʱ�� " + (t3 - t2)
//					+ " ����");

			// ��ȡ����BOM
			HashMap gymap = new HashMap();
			//map��key�������ţ�value��id����š�������һ������
			gymap = bianli(gyID, carModelCode, dwbs, 1L, gymap, isRelease);
//			long t4 = System.currentTimeMillis();
//			System.out.println("CompartTreeResult  bianli  ��ʱ�� " + (t4 - t3)
//					+ " ����");
//			System.out.println("gymap:" + gymap.size());
			Set gySet = gymap.keySet();
			Iterator gyiter = gySet.iterator();
			String gynum = "";
			int i = 0;
			int j = 0;
			int m = 0;
			while (gyiter.hasNext()) {
				gynum = (String) gyiter.next();
				String[] str = (String[]) gymap.get(gynum);
				// System.out.println("���:"+str[1]+"    gy������"+str[2]+"    design������"+designMap.get(str[1]));
				//����BOM���㲿����ֵ����
				//CCBegin SS42
				QMPartIfc part = (QMPartIfc) ps.refreshInfo(str[0]);
				String gyfbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",str[0]);
				if(gyfbyIbaValue==""){
					gyfbyIbaValue = part.getVersionValue();
				}
				//����BOM������
//				String gypartnum = part.getPartNumber();
				//CCEnd SS42
				if (designMap.containsKey(str[1])) {
//					System.out.println("str[1]111111=======" + str[1]);
					//���BOM�а�������BOM���
					String[] destr = (String[]) designMap.get(str[1]);
					//���BOM�����id
					String departid = destr[0];
					//���BOM�����ֵ����
					//CCBegin SS42
					QMPartIfc depart = (QMPartIfc)ps.refreshInfo(departid);
					String defbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",departid);
					if(defbyIbaValue==""){
						defbyIbaValue = depart.getVersionValue();
					}
					//CCEnd SS42
					if (!destr[1].equals(str[2])) {
						i++;
						String[] strs = new String[5];
						strs[0] = str[1];
						strs[1] = destr[1];
						//CCBegin SS42
//						strs[2] = depart.getVersionValue();
						strs[2] = defbyIbaValue;
						strs[3] = str[2];
//						strs[4] = part.getVersionValue();
						strs[4] = gyfbyIbaValue;
						//CCEnd SS42
						vector.add(strs);
						designMap.remove(str[1]);
					} else {
						//CCBegin SS42
//						if(!depart.getVersionValue().equals(part.getVersionValue())){
						if(!defbyIbaValue.equals(gyfbyIbaValue)){
							//CCEnd SS42
							i++;
							String[] strs = new String[5];
							strs[0] = str[1];
							strs[1] = destr[1];
							//CCBegin SS42
//							strs[2] = depart.getVersionValue();
							strs[2] = defbyIbaValue;
							strs[3] = str[2];
//							strs[4] = part.getVersionValue();
							strs[4] = gyfbyIbaValue;
							//CCEnd SS42
							vector.add(strs);
							designMap.remove(str[1]);
						}else{
							j++;
							designMap.remove(str[1]);
						}
					}
				} else {
					//���BOM�в���������BOM���
					i++;
					m++;
					String[] strs = new String[5];
//					System.out.println("str[1]=======" + str[1]);
					strs[0] = str[1];
					strs[1] = "0";
					strs[2] = "��";
					strs[3] = str[2];
					//CCBegin SS42
//					strs[4] = part.getVersionValue();
					strs[4] = gyfbyIbaValue;
					//CCEnd SS42
					vector.add(strs);
				}
			}
//			System.out.println("m=======" + m);
//			System.out.println("��������1====" + i);
//			System.out.println("j=======" + j);
			Set idSet = designMap.keySet();
			Iterator iter = idSet.iterator();
			String num = "";
			while (iter.hasNext()) {
				i++;
				num = (String) iter.next();
				String[] str = (String[]) designMap.get(num);
				// System.out.println("design ʣ�ࣺ"+(String)iter.next());
				//CCBegin SS42
				QMPartIfc depart = (QMPartIfc) ps.refreshInfo(str[0]);
				String gyfbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",str[0]);
				if(gyfbyIbaValue==""){
					gyfbyIbaValue = depart.getVersionValue();
				}
				//CCEnd SS42
				String[] strs = new String[5];
				strs[0] = num;
				strs[1] = str[1];
				//CCBegin SS42
//				strs[2] = depart.getVersionValue();
				strs[2] = gyfbyIbaValue;
				//CCEnd SS42
				strs[3] = "0";
				strs[4] = "��";
				vector.add(strs);
			}
			//CCBeing SS66
			sortTongJiVector(vector,0);
			//CCEnd SS66
//			System.out.println("��������2====" + i);
//			long t5 = System.currentTimeMillis();
//			System.out.println("CompartTreeResult  json  ��ʱ�� " + (t5 - t4)
//					+ " ����");
//			System.out.println("������֯������ʱ�� " + (t5 - t1) + " ����");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return vector;
	}

	/**
	 * �������������Ӽ�����
	 * 
	 * @param gyID
	 * @param carModelCode
	 * @param dwbs
	 * @param parentQuantity
	 * @param gymap
	 * @param isRelease
	 * @return
	 * @throws QMException
	 */
	private HashMap bianli(String gyID, String carModelCode, String dwbs,
			float parentQuantity, HashMap gymap, String isRelease)
			throws QMException {
		// System.out.println("11111111111gyID===" + gyID +
		// "====carModelCode===="
		// + carModelCode + "====dwbs====" + dwbs + "====isRelease===="
		// + isRelease);
		if (isRelease.endsWith("false")) {
			isRelease = "0";
		} else if (isRelease.equals("true")) {
			isRelease = "1";
		}
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = PersistUtil.getConnection();
			stmt = conn.createStatement();
			String routesql = "select childPart,childNumber,quantity from GYBomStructure where parentPart='"
					+ gyID
					+ "' and effectCurrent='"
					+ isRelease
					+ "' and dwbs='" + dwbs + "'";
			rs = stmt.executeQuery(routesql);

			while (rs.next()) {
				String childPart = "";
				String childNumber = "";
				String count = "";
				String[] tempArray = new String[3];
				childPart = rs.getString(1).toString();
				childNumber = rs.getString(2).toString();
				count = rs.getString(3).toString();

				// System.out.println("childPart===" + childPart
				// + "===childNumber====" + childNumber + "====count===="
				// + count);
				if (count == null || count.equals("")) {
					count = "1";
				}

				tempArray[0] = childPart;
				tempArray[1] = childNumber;
				if (gymap.containsKey(childNumber)) {
					String[] a = (String[]) gymap.get(childNumber);

					tempArray[2] = (String.valueOf(parentQuantity
							* (Float.parseFloat(count))
							+ (Float.parseFloat(a[2])))).toString();
				} else {
					tempArray[2] = (String.valueOf(parentQuantity
							* (Float.parseFloat(count)))).toString();
				}

				if (tempArray[2].endsWith(".0")) {
					tempArray[2] = tempArray[2].substring(0,
							tempArray[2].length() - 2);
				}
				gymap.put(childNumber, tempArray);
				gymap = bianli(childPart, carModelCode, dwbs,
						(parentQuantity * (Float.parseFloat(count))), gymap,
						isRelease);
			}

			// ��ղ��ر�sql��������
			rs.close();
			// �ر�Statement
			stmt.close();
			// �ر����ݿ�����
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return gymap;
	}
	// CCEnd SS40
	
	
	//CCBegin SS44
    /**
     * ���·����Ϣ�����崦��in��sql��ʽ��ÿ������20����
     * ��������ڽ�Ų��Ի��϶�β��Խ���ó���Ч����Ѹ�����
     * @param routeTempMap HashMap routeֵ�Ļ��棬keyΪpartMasterBsoID+route���ƣ�ֵ�������part��Ӧroutename��ֵ��
     * @param bomMap HashMap ���б������漰���㲿���ļ��ϡ�keyΪpartBsoID��
     * ֵΪ��ά���飬��һ��Ԫ����partMasterBsoID���ڶ���Ԫ�����丸����partMasterBsoID��
     * @return HashMap ����routeTempMap��
     */
    private HashMap getAllRouteValue(String insql,HashMap routeTempMap)
    {
    	HashMap map = new HashMap();
    	
    	//����sql���õ�ȫ��·�ߡ�����Ҫ����ȥ����ȡ����·�ߡ�
    	// ���TechnicsRouteList��Ϊ��׼��technicsroute����ȡ��״̬��ȥ��alterstatus='1'������
    	//String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid, route.modefyIdenty from technicsroutebranch branch, listroutepartlink link,technicsroute route,technicsroutelist tcl where branch.routeid=link.routeid and route.bsoid=branch.routeid and link.rightbsoid in (";
    	String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid, route.modefyIdenty,link.colorflag from technicsroutebranch branch, listroutepartlink link,technicsroute route,technicsroutelist tcl where branch.routeid=link.routeid and route.bsoid=branch.routeid and link.rightbsoid in (";
    	// ��Ƽ����������ϼе���׼��ȡ��·�ߡ�    	
    	String sql2 = ") and tcl.bsoid=link.leftbsoid and tcl.iterationiflatest=1 and tcl.routeliststate!='��׼' and tcl.owner is null order by tcl.modifytime desc";

    	Connection conn = null;
      Statement stmt =null;
      ResultSet rs=null;
  	  try
  	  {
  	  	PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
  	    //������������׼��masterid����������
    	  conn = PersistUtil.getConnection();
      	stmt = conn.createStatement();
      			rs = stmt.executeQuery(sql1+insql+sql2);
      			while(rs.next())
      			{
      				String keyName = rs.getString(2)+"·��";
    			  			//System.out.println("keyName==="+keyName);
      						if(routeTempMap.containsKey(keyName))
      						{
      							HashMap routemap = (HashMap)routeTempMap.get(keyName);
      							if(routemap.containsKey(rs.getString(3)+rs.getString(4)))
      							{
      								//ͬһ��׼��ͬһ�㲿����·�߿����ж������ظ��Ĳ���ӡ�
      								//����·����ͬʱ���������·�� ��@�� ��@�У���������routemap��ֵ�ǡ���@��, ��@�С�������ȡ���ġ���@�С�����ʱ�͹��˲�����
      								String[] rr = routemap.get(rs.getString(3)+rs.getString(4)).toString().split(",");
      								boolean rflag = false;
      								for(int rii = 0;rii<rr.length;rii++)
      								{
      									if(rr[rii].equals(rs.getString(1)))
      									{
      										rflag = true;
      									}
      								}
      								
      								if(rflag)
      								{
      									continue;
      								}
      								String rstr = routemap.get(rs.getString(3)+rs.getString(4))+","+rs.getString(1);
      								routemap.put(rs.getString(3)+rs.getString(4),rstr);
      							}
      							else
      							{
      								routemap.put(rs.getString(3)+rs.getString(4),rs.getString(1));
      							}
      							
      							
      							routeTempMap.remove(keyName);
      							routeTempMap.put(keyName,routemap);
      						}
      						else
      						{
      							HashMap routemap = new HashMap();
      							routemap.put(rs.getString(3)+rs.getString(4),rs.getString(1));
      							routeTempMap.put(keyName,routemap);
      						}
      					
      				//�õ�����·�߱���
      				if(!rs.getString(4).equals("Coding_221664")&&(!routeTempMap.containsKey(rs.getString(2)+"����·��")||(routeTempMap.containsKey(rs.getString(2)+"����·��")&&routeTempMap.get(rs.getString(2)+"����·��").toString().indexOf(rs.getString(3))!=-1)))
      				{
      					if(routeTempMap.containsKey(rs.getString(2)+"����·��"))
      					{
      						String str1 = routeTempMap.get(rs.getString(2)+"����·��").toString();
      						routeTempMap.put(rs.getString(2)+"����·��",str1+","+rs.getString(1));
      					}
      					else
      					{
      						routeTempMap.put(rs.getString(2)+"����·��",rs.getString(3)+";"+rs.getString(1));
      					}
      				}
      			}
    				//System.out.println("routeTempMap==="+routeTempMap);
    				//��·�ߴ�����
    			  String temp[] = insql.split(",");
    			  for(int ii = 0;ii<temp.length;ii++)
    			  {
    			  	String id = temp[ii].substring(1,temp[ii].length()-1);
    			  	HashMap routemap = (HashMap)routeTempMap.get(id+"·��");
//    			  	System.out.println(id+"ȫ·��Ϊ��"+routemap);
    			  	String zhizaostr = "";
    			  	String zhuangpeistr = "";
    			  	
    			  	if(routemap!=null)
    			  {
    			  	Set routeSet = routemap.keySet();
    			  	Iterator routeiter = routeSet.iterator();
    			  	Vector routevec = new Vector();
    			  	Vector canelvec = new Vector();
    			  	while(routeiter.hasNext())
    			  	{
    			  		String str = (String)routeiter.next();
    			  		if(str.endsWith("Coding_221664"))//ȡ��״̬
    			  		{
    			  			//System.out.println("canelvec  add  ("+routemap.get(str)+")");
    			  			canelvec.add(routemap.get(str));
    			  		}
    			  		else
    			  		{
    			  			//System.out.println("routevec  add  ("+routemap.get(str)+")");
    			  			routevec.add(routemap.get(str));
    			  		}
    			  	}
    			  	
    			  	if(routevec!=null&&routevec.size()>0)
    			  	{
    			  		for(int jj=0;jj<canelvec.size();jj++)
    			  		{
    			  			String cancelstr = canelvec.elementAt(jj).toString();  
    			  			//System.out.println("cancelstr "+jj+" ===  ("+cancelstr+")");  			  		
    			  			while(routevec.contains(cancelstr))
    			  			{
    			  				routevec.remove(cancelstr);
    			  			}
    			  			
    			  			//�����������:
    			  			//routevec======[Э@��,Э@��, ��@��]
    			  			//canelvec======[Э@��,Э@��]
    			  			//Э@��,Э@����Э@��,Э@�ܶ��� һ���������ַ�����
    			  			//Ҫ���������жϺʹ���
    			  			if(cancelstr.indexOf(",")!=-1)
    			  			{
    			  				String cantemp[] = cancelstr.split(",");
    			  				if(cantemp.length==2)
    			  				{
    			  					cancelstr = cantemp[1]+","+cantemp[0];
    			  				}
    			  				else
    			  				{
    			  					boolean flag = false;
    			  					for(int iii=0;iii<routevec.size();iii++)
    			  					{
    			  						String strroute = routevec.elementAt(iii).toString();
    			  						String stemp[] = strroute.split(",");
    			  						if(stemp.length!=cantemp.length)
    			  						{
    			  							continue;
    			  						}
    			  						for(int cani=0;cani<cantemp.length;cani++)
    			  						{
    			  							String str = cantemp[cani];
    			  							if(strroute.indexOf(str)!=-1)
    			  							{
    			  								flag = true;
    			  							}
    			  							else
    			  							{
    			  								flag = false;
    			  								break;
    			  							}
    			  						}
    			  						
    			  						if(flag)
    			  						{
    			  							cancelstr = strroute;
    			  							break;
    			  						}
    			  					}
    			  				}
    			  				
    			  				while(routevec.contains(cancelstr))
    			  				{
    			  					routevec.remove(cancelstr);
    			  				}
    			  			}
    			  		}
    			  		for(int iii=0;iii<routevec.size();iii++)
    			  		{
    			  			String strroute = routevec.elementAt(iii).toString();
    			  			String temproute[] = strroute.split(",");
    			  			for(int iiii=0;iiii<temproute.length;iiii++)
    			  			{
    			  				String str = temproute[iiii];
    			  				if(zhizaostr.equals(""))
    			  				{
    			  					zhizaostr = getZhiZaoRoute(str);
    			  				}
    			  				//else if(zhizaostr.indexOf(getZhiZaoRoute(str))==-1)//anan �����ж����zhizaostr�������getZhiZaoRoute(str)���в�һ������ӣ���Ҫ���� Э--��(��)/Э  ��  �� ���������
    			  				else if(!hasStr(zhizaostr,getZhiZaoRoute(str)))
    			  				{
    			  					zhizaostr = zhizaostr + "/" + getZhiZaoRoute(str);
    			  				}
    			  				if(zhuangpeistr.equals(""))
    			  				{
    			  					zhuangpeistr = getZhuangPeiRoute(str);
    			  				}
    			  				else if(zhuangpeistr.indexOf(getZhuangPeiRoute(str))==-1)
    			  				{
    			  					zhuangpeistr = zhuangpeistr + "/" + getZhuangPeiRoute(str);
    			  				}
    			  				
    			  				//System.out.println("after zhizaostr======"+zhizaostr);
    			  		  }
    			  		}
    			  		
    			  		if((routevec==null||routevec.size()==0)&&zhizaostr.equals("")&&zhizaostr.equals(""))
    			  		{
    			  			String str1 = routeTempMap.get(id+"����·��").toString();
    			  			str1 = str1.substring(str1.indexOf(";")+1,str1.length());
    			  			String temproute[] = str1.split(",");
    			  			for(int iiii=0;iiii<temproute.length;iiii++)
    			  			{
    			  				String str = temproute[iiii];
    			  				if(zhizaostr.equals(""))
    			  				{
    			  					zhizaostr = getZhiZaoRoute(str);
    			  				}
    			  				//else if(zhizaostr.indexOf(getZhiZaoRoute(str))==-1)//anan �����ж����zhizaostr�������getZhiZaoRoute(str)���в�һ������ӣ���Ҫ���� Э--��(��)/Э  ��  �� ���������
    			  				else if(!hasStr(zhizaostr,getZhiZaoRoute(str)))
    			  				{
    			  					zhizaostr = zhizaostr + "/" + getZhiZaoRoute(str);
    			  				}
    			  				if(zhuangpeistr.equals(""))
    			  				{
    			  					zhuangpeistr = getZhuangPeiRoute(str);
    			  				}
    			  				else if(zhuangpeistr.indexOf(getZhuangPeiRoute(str))==-1)
    			  				{
    			  					zhuangpeistr = zhuangpeistr + "/" + getZhuangPeiRoute(str);
    			  				}
    			  				//System.out.println("after zhizaostr======"+zhizaostr);
    			  		  }
    			  		}
    			  	}
    			  }
//    			  System.out.println("����·��======"+zhizaostr);
//    			  System.out.println("װ��·��======"+zhuangpeistr);
    			  	routeTempMap.remove(id+"·��");
    			  	routeTempMap.put(id+"����·��",zhizaostr);
    			  	routeTempMap.put(id+"װ��·��",zhuangpeistr);    			  	
    			  }
      	//��ղ��ر�sql��������
  	    rs.close();
  	    //�ر�Statement
  	    stmt.close();
  	    //�ر����ݿ�����  
        conn.close();
      }
      catch(Exception e)
      {
      	  e.printStackTrace();
      }
      finally
      {
          try
        {
            if (rs != null)
            {
                rs.close();
            }
            if (stmt != null)
            {
                stmt.close();
            }
            if (conn != null)
            {
                conn.close();
            }
        }
        catch (SQLException ex1)
        {
            ex1.printStackTrace();
        }
  	  }
  	  
  	  //ת������
  	  try
  	  {
        if(routeTempMap!=null)
        {
        	String temp[] = insql.split(",");
        	for(int ii = 0;ii<temp.length;ii++)
        	{
        		String id = temp[ii].substring(1,temp[ii].length()-1);
        		String str1 = routeTempMap.get(id+"����·��").toString();
        		String str2 = routeTempMap.get(id+"װ��·��").toString();
        		map.put(id,new String[]{str1, str2});
//        		System.out.println(id+"==="+str1+":"+str2);
        	}
        }
  	  }
      catch(Exception e)
      {
      	  e.printStackTrace();
      }
  	  
    	return map;
    }
    
    /**
     * ����·�ߴ��������·�ߡ�·�ߴ���ʽΪ ����·��@װ��·�� ��
     * @param s String ·�ߴ���
     * @return String ����·�ߡ�
     */
    private String getZhiZaoRoute(String s)
    {
    	String route = s.substring(0,s.indexOf("@"));
    	if(route.equals("��"))
    	{
    		route = "";
    	}
    	return route.replaceAll("��","--");
    }

    /**
     * ����·�ߴ����װ��·�ߡ�·�ߴ���ʽΪ ����·��@װ��·�� ��
     * @param s String ·�ߴ���
     * @return String װ��·�ߡ�
     */
    private String getZhuangPeiRoute(String s)
    {
    	String route = s.substring(s.indexOf("@")+1,s.length());
    	if(route.equals("��"))
    	{
    		//route = "";
    	}
    	return route.replaceAll("��","--");
    }


    /**
     * �ж��ܵ�·���ַ������Ƿ������ǰ·���ַ�����
     * @param s String �ܵ�·�ߴ���
     * @param s1 String ��ǰ·�ߴ���
     * @return boolean �Ƿ��������true������ false��
     * SS2
     */
    private boolean hasStr(String s,String s1)
    {
    	if(s1.equals(""))
    	{
    		return true;
    	}
    	
    	boolean flag = false;
    	
    	String ss[] = s.split("/");    	
    	
    	for(int i=0;i<ss.length;i++)
    	{
    		String str = ss[i];
    		if(str.equals(s1))
    		{
    			flag = true;
    			break;
    		}
    	}
    	return flag;
    }
    //CCEnd SS44
    //CCBegin SS48
	/**
	 * ���ҷ��Ϲ�����㲿����������·��
	 * 
	 * @param partID
	 *            ��Ƴ���ID
	 * @throws QMException
	 */
	public void searchAndCreateTechnics(String partID) throws QMException {
		long t1 = System.currentTimeMillis();
		PersistService ps = (PersistService) EJBServiceHelper
				.getPersistService();

		PartServiceHelper psh = new PartServiceHelper();
		// ��ȡ���BOM�ṹ
		Vector vec = psh.getBOMList(partID,
				"partNumber-0,partName-0,quantity-0,", "", "");
		// �洢Ҫ����·�ߵ�������ϣ����ֵ����·�߼��ϣ�
		Vector saveVec = new Vector();
		// ·�߱�
		TechnicsRouteListIfc tifc = new TechnicsRouteListInfo();
		for (Iterator iter = vec.iterator(); iter.hasNext();) {
			Object[] str = (Object[]) iter.next();
			// Ԫ��0ΪID��Ԫ��4Ϊ���
			String partid = (String) str[0];
			String partnumber = (String) str[4];
			
			//��Ŵ���5λ�����ҵ�������λ��"J"����"j"�������λΪ�����ֵ�ʱ��Ŵ���·�ߣ����򲻹�
			if (partnumber.length() > 5) {
				//��������λ���ַ�
				String final4 = partnumber.substring(partnumber.length()-4, partnumber.length()-3);
				if ((final4.equals("J")||final4.equals("j"))&& partnumber.substring(4, 5).equals("G")) {
					// ���һ���жϣ����-J�������λ���ֲ��Ǵ����֣���ô����ѯ
					boolean isnum = false;
					String finalnum = "";
//					if (partnumber.length() == partnumber.indexOf("-J") + 5) {
//						finalnum = partnumber.substring(partnumber
//								.indexOf("-J") + 2);
//					}
//					if (partnumber.length() > partnumber.indexOf("-J") + 5) {
//						finalnum = partnumber.substring(
//								partnumber.indexOf("-J") + 2,
//								partnumber.indexOf("-J") + 5);
//					}
					finalnum = partnumber.substring(partnumber.length()-3);
//					System.out.println("finalnum======"+finalnum);
					if (finalnum != null && !finalnum.trim().equals("")) {
						isnum = isNumeric(finalnum);
					}
//					System.out.println("isnum======"+isnum);
					if (isnum) {

						QMPartIfc subpart = (QMPartIfc) ps.refreshInfo(partid);
//						 System.out.println("--------"+partid+"========"+partnumber+"----------");

						Collection scoll = ViewRouteLevelOne
								.getRouteInfomationByPartmaster(subpart
										.getMasterBsoID());
						boolean flag = false;
						if (scoll.size() == 0) {
							flag = true;
						}
						// System.out.println("flag======"+flag);
						if (flag) {
							// ��ȡ������ı��
							String jqpartnumber = "";
//							if (partnumber.indexOf("-J") > 0) {
//								jqpartnumber = partnumber.substring(0,
//										partnumber.indexOf("-J"));
//							} else if (partnumber.indexOf("-j") > 0) {
//								jqpartnumber = partnumber.substring(0,
//										partnumber.indexOf("-j"));
//							}
							jqpartnumber = partnumber.substring(0, partnumber.length()-4);
							if(jqpartnumber.endsWith("-")){
								jqpartnumber = jqpartnumber.substring(0, jqpartnumber.length()-1);
							}
							// System.out.println("==========-----------"+jqpartnumber);
							// CCBegin SS54
							// QMQuery query = new QMQuery("QMPartMaster");
							QMQuery query = new QMQuery("GenericPartMaster");
							// CCEnd SS54
							QueryCondition condition1 = new QueryCondition(
									"partNumber", "=", jqpartnumber);
							query.addCondition(condition1);
							Collection col = ps.findValueInfo(query);
							Iterator iterz = col.iterator();
							// CCBegin SS54
							// QMPartMasterIfc pmaster = null;
							GenericPartMasterIfc pmaster = null;
							// CCEnd SS54
							while (iterz.hasNext()) {
								// CCBegin SS54
								// pmaster = (QMPartMasterIfc)iterz.next();
								pmaster = (GenericPartMasterIfc) iterz.next();
								// CCEnd SS54
							}
							// master��Ϊ�յ�ʱ���ȥ����·��
							if (pmaster != null) {
								// System.out.println("���ڴ˼���������");
								// �������µĹ���routelistpartlinkid
								String linkid = searchLinkID(pmaster);
								// System.out.println("linkid-----------------==========================="+linkid);
								if (linkid != "") {
									// ����·�ߣ�ÿ��Ԫ���Ǹ����飬���������ݷֱ�Ϊ������·�ߡ�װ��·�ߡ��Ƿ���Ҫ·�ߣ�
									Collection routecoll = getRoutes(linkid);
									// ��·�ߵ�ʱ���ȥ��ż��ϣ����򲻴棨���ֵ����·�߼��ϣ�
									if (routecoll.size() > 0) {
										Object[] result = new Object[3];
										result[0] = subpart;
										result[1] = routecoll;
										saveVec.add(result);// �浽������
									}
								}
							}
						}
					}
				}
			}
		}

		// �����ϴ���0�Ŵ���·�߱�
		if (saveVec.size() > 0) {
			// ���BOM����ֵ����
			QMPartIfc desPartIfc = (QMPartIfc) ps.refreshInfo(partID);
			String desMasterID = desPartIfc.getMasterBsoID();
			// ·�߱���
			String technicsNumber = "intGYBOM" + desPartIfc.getPartNumber();
			TechnicsRouteService trs = (TechnicsRouteService) EJBServiceHelper
					.getService("TechnicsRouteService");
			tifc = trs.findRouteListByNum(technicsNumber);
			if (tifc == null) {
				tifc = createRouteList(desPartIfc, desMasterID, saveVec);
			}
			// �����㲿������
			StandardPartService sps = (StandardPartService) EJBServiceHelper
					.getService("StandardPartService");

			// ѭ������������ϵ
			for (Iterator iter = saveVec.iterator(); iter.hasNext();) {
				Object[] str = (Object[]) iter.next();// �����ֵ����·�߼��ϣ�
				createListRoutePartLink(tifc, str, desPartIfc.getPartNumber(),
						desPartIfc.getPartName());
				// tifc.getProductID();
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println("��������������ʱ��======" + (t2 - t1) + " ����");
	}
    
    /**����·�߱�(רΪ��ʼ��ʹ��)
	 * @param parentPartMasterID
	 * @return
	 * @throws QMException
	 */
	public TechnicsRouteListIfc createRouteList(QMPartIfc partifc ,String parentPartMasterID,Vector saveVec) throws QMException
	{
//		System.out.println("parentPartMasterID-=========="+parentPartMasterID);
		long namefix = System.currentTimeMillis();
//		System.out.println("namefix=============="+namefix);
		String listNumber = "intGYBOM" + partifc.getPartNumber();
		try {
			
			PersistService pservice = (PersistService) EJBServiceHelper
					.getPersistService();
			TechnicsRouteListIfc reloadTRLIfc = new TechnicsRouteListInfo();
			TechnicsRouteListMasterIfc master = new TechnicsRouteListMasterInfo();
			master.setRouteListNumber(listNumber);
			master.setRouteListName(listNumber);
			master.setProductMasterID(parentPartMasterID);
			master = (TechnicsRouteListMasterIfc) 
			pservice.saveValueInfo(master);

			reloadTRLIfc.setRouteListNumber(listNumber);
			reloadTRLIfc.setRouteListName(listNumber);
			reloadTRLIfc.setRouteListLevel("һ��·��");
			reloadTRLIfc.setRouteListState("��׼");
			reloadTRLIfc.setDefaultDescreption("��ʼ����׼");
			reloadTRLIfc.setProductMasterID(parentPartMasterID);
			reloadTRLIfc.setProductID(partifc.getBsoID());
			//����setPartIndex
			Vector vec = new Vector();
			for(Iterator iter = saveVec.iterator();iter.hasNext();){
				Object[] str = (Object[])iter.next();
				QMPartIfc subpartifc = (QMPartIfc)str[0];
				String[] restr = new String[6];
				restr[0]=subpartifc.getMasterBsoID();
				restr[1]=partifc.getPartNumber();
				restr[2]="1";
				restr[3]=subpartifc.getBsoID();
				restr[4]="0";
				restr[5]="����";
//				System.out.println(restr[0]+"===="+restr[1]+"===="+restr[2]+"===="+restr[3]+"===="+restr[4]+"===="+restr[5]);
				vec.add(restr);
			}
			reloadTRLIfc.setPartIndex(vec);
			
			String folderpath = "\\Root\\����׼��֪ͨ��";
			String lifeCycleTemplateName = "ȱʡ";

			FolderService fservice = (FolderService) EJBServiceHelper
			.getService("FolderService");
			LifeCycleService lservice = (LifeCycleService) EJBServiceHelper
			.getService("LifeCycleService");
			fservice.assignFolder(reloadTRLIfc, folderpath);
			LifeCycleTemplateInfo temp = lservice
			.getLifeCycleTemplate(lifeCycleTemplateName);
			reloadTRLIfc = 
				(TechnicsRouteListIfc)lservice.setLifeCycle(reloadTRLIfc,temp);
			reloadTRLIfc.setMaster(master);
			reloadTRLIfc.setMasterBsoID(master.getBsoID());

			reloadTRLIfc = 
				(TechnicsRouteListIfc)pservice.saveValueInfo(reloadTRLIfc);
			LifeCycleState state = LifeCycleState.toLifeCycleState("RELEASED");
			reloadTRLIfc.setLifeCycleState(state);
			reloadTRLIfc = 
				(TechnicsRouteListIfc) pservice.saveValueInfo(reloadTRLIfc);
			return reloadTRLIfc;
		}
		catch (QMException ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	/**
	 * �����㲿����·�߲����浽���ݿ��С��ڸ÷����У�����·�ߴ���������·�ߵ��롣(רΪ��ʼ��ʹ��)
	 * @param list ·�߱�
	 * @param part PDM�㲿��
	 * @param source �м���㲿��
	 * @return
	 * @throws QMException 
	 */
	public ListRoutePartLinkIfc createListRoutePartLink
		(TechnicsRouteListIfc list ,Object[] str,String productnum,String productname) 
															throws QMException
	{
		QMPartIfc part =(QMPartIfc)str[0];
		long a = System.currentTimeMillis();
		//��ó־û�����
		PersistService pservice = (PersistService) EJBServiceHelper
														.getPersistService();
		ListRoutePartLinkIfc listPart = 
			ListRoutePartLinkInfo.newListRoutePartLinkInfo
				(list, part.getMaster().getBsoID(), part.getBsoID());
		//����·�߶���
		TechnicsRouteIfc routeInfo = new TechnicsRouteInfo();
		routeInfo.setRouteDescription("��ʼ������·��");
		routeInfo.setModefyIdenty(getCodingIfc("����"));
		routeInfo.setIsAdopt(false);
		//����֮ǰҪ����һ��TechnicsRouteIfc����
		routeInfo = (TechnicsRouteIfc) pservice.saveValueInfo(routeInfo);
		if(routeInfo == null)
		{
			throw new QMException("����·�߶������");
		}
		listPart.setRouteID(routeInfo.getBsoID());
		listPart.setAdoptStatus("����");
		listPart.setAlterStatus(1);
		listPart.setColorFlag("0");
		//�����б����Ʒ������ƺ�id
		listPart.setParentPartID(list.getProductID());
		listPart.setParentPartNum(productnum);
		listPart.setParentPartName(productname);
		
		listPart = (ListRoutePartLinkIfc) pservice.saveValueInfo(listPart);
		if(listPart == null)
		{
			throw new QMException("�����㲿��"+ part.getPartNumber() + "��·�߱��������");
		}
		boolean flag = false;
		flag = saveRouteBranch(routeInfo,str);
		if(flag)
		{
			return listPart;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * ����·�߷�֧��(רΪ��ʼ��ʹ��)
	 * @param hashtable ���Ա�
	 * @param vector Ҫ�������־��Ϣ
	 * @return ���������Ƿ���ȷ
	 * @author houhf
	 */
	
	private boolean saveRouteBranch(TechnicsRouteIfc routeInfo,Object[] str)
	{
		try
		{
			//���ÿ��·���м�����֧
			int count1 = 0;
			PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
			count1 = count1 + 1;
			Collection coll = (Collection)str[1];
			//ѭ��������ÿ��Ԫ�ش�һ��
			for(Iterator iter2 = coll.iterator();iter2.hasNext();){
				Object[] resultroutestr = (Object[])iter2.next();
//				for(int kk = 0;kk<resultroutestr.length;kk++){
//					System.out.println("��Ԫ�ص�ֵ======"+kk+"======"+resultroutestr[kk]+"************");
//				}
//				System.out.println("                     ");
				//����·��
				String manuRoute = (String)resultroutestr[1];
				//װ��·��
				String assRoute=(String)resultroutestr[2];
				//�Ƿ���Ҫ·��
				String ismain = (String)resultroutestr[3];
				//����·�ߴ�
				String routeBranchStr;
				if(manuRoute != null && manuRoute.length()>0)
				{
					routeBranchStr = manuRoute.replaceAll("--","��");
				}
				else
				{
					routeBranchStr = "��";
				}
				if(assRoute != null && assRoute.length()>0)
				{
					routeBranchStr = routeBranchStr + "@" + assRoute;
				}
				else
				{
					routeBranchStr = routeBranchStr + "@��";
				}
				TechnicsRouteBranchIfc routeBranchInfo = new TechnicsRouteBranchInfo();
				if(ismain.equals("��")){
					routeBranchInfo.setMainRoute(true);
				}else{
					routeBranchInfo.setMainRoute(false);
				}
				routeBranchInfo.setRouteStr(routeBranchStr);
				routeBranchInfo.setRouteInfo(routeInfo);
				routeBranchInfo = (TechnicsRouteBranchIfc) pservice.saveValueInfo(routeBranchInfo);
				
				//�ڵ㼯��
				Vector<RouteNodeIfc> nodeVec = new Vector<RouteNodeIfc>();
				//����·��Node
				if(manuRoute != null && manuRoute.trim().length() > 0)
				{
					Collection nodeString = new Vector();
					StringTokenizer token = new StringTokenizer(manuRoute, "��");
					while(token.hasMoreTokens())
					{
						String string = token.nextToken();
//						if(string.indexOf("/")>0){
//							StringTokenizer token1 = new StringTokenizer(string, "/");
//							while(token1.hasMoreTokens())
//							{
//								String string1 = token1.nextToken();
//								nodeString.add(string1);
//							}
//						}else{
							nodeString.add(string);
//						}
					}
					int y = 20 * count1;
					int x = 10 * count1;
					for(Iterator manuIterator = nodeString.iterator();manuIterator.hasNext();)
					{
						String manu = (String)manuIterator.next();
						if(manu == null || manu.trim().length()==0)
							continue;
//						System.out.println("manu============"+manu);
						//����·��ID������ʱ��������
						String departmentID = getDepartmentID(manu);
						RouteNodeIfc nodeInfo = new RouteNodeInfo();
						nodeInfo.setRouteType(RouteCategoryType.MANUFACTUREROUTE.getDisplay());
//						System.out.println("departmentID============"+departmentID);
						nodeInfo.setNodeDepartment(departmentID);
						nodeInfo.setRouteInfo(routeInfo);
						x = x + 100;
						nodeInfo.setX(new Long(x).longValue());
						nodeInfo.setY(new Long(y).longValue());
//						System.out.println("nodeInfo============"+nodeInfo);
						nodeInfo = (RouteNodeIfc) pservice.saveValueInfo(nodeInfo);
						nodeVec.add(nodeInfo);
					}
					count1 = count1 + 1;
				}
				
				//װ��·��
				if(assRoute != null && assRoute.trim().length() > 0)
				{
					//����·��ID������ʱ��������
					String departmentID = getDepartmentID(assRoute);
					if(departmentID != null && departmentID.trim().length()>0)
					{
						int y = 20 * count1;
						int x = 10 * count1;
						RouteNodeIfc nodeInfo = new RouteNodeInfo();
						nodeInfo.setRouteType
									(RouteCategoryType.ASSEMBLYROUTE.getDisplay());
						nodeInfo.setNodeDepartment(departmentID);
						nodeInfo.setRouteInfo(routeInfo);
						x = x + 100;
						nodeInfo.setX(new Long(x).longValue());
						nodeInfo.setY(new Long(y).longValue());
						nodeInfo = (RouteNodeIfc) pservice.saveValueInfo(nodeInfo);
						nodeVec.add(nodeInfo);
						count1 = count1 + 1;
					}
				}
				
				//���ýڵ����
				for(int i=0;i<nodeVec.size();i++)
				{
					RouteNodeIfc nodeInfo = nodeVec.get(i);
					if(i<nodeVec.size()-1)
					{
						RouteNodeIfc destinationInfo = nodeVec.get(i+1);
						RouteNodeLinkIfc nodeLinkInfo1 = new RouteNodeLinkInfo();
						nodeLinkInfo1.setSourceNode(nodeInfo);
						nodeLinkInfo1.setDestinationNode(destinationInfo);
						nodeLinkInfo1.setRouteInfo(routeInfo);
						pservice.saveValueInfo(nodeLinkInfo1);
					}
				}
				
				//���÷�֧��ڵ����
				for(int i=0;i<nodeVec.size();i++)
				{
					RouteNodeIfc nodeInfo1 = nodeVec.get(i);
					RouteBranchNodeLinkIfc branchNodeLinkInfo = new RouteBranchNodeLinkInfo();
					branchNodeLinkInfo.setRouteBranchInfo(routeBranchInfo);
					branchNodeLinkInfo.setRouteNodeInfo(nodeInfo1);
					pservice.saveValueInfo(branchNodeLinkInfo);
				}
			}
		}
		catch (QMException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**��������Ĺ���id�����°�ģ�
	 * @return ���µĹ���id
	 * @throws QMException 
	 */
	//CCBegin SS54
//	public String searchLinkID(QMPartMasterIfc partifc) throws QMException{
	public String searchLinkID(GenericPartMasterIfc partifc) throws QMException{
		//CCEnd SS54
		String resultstr = "";
		PersistService pservice = (PersistService) EJBServiceHelper
				.getService("PersistService");
		QMQuery query = new QMQuery("ListRoutePartLink");
		int i = query.appendBso("TechnicsRouteList", false);
		QueryCondition qc = new QueryCondition("leftBsoID", "bsoID");
		query.addCondition(0, i, qc);
		query.addAND();
		QueryCondition qc1 = new QueryCondition("iterationIfLatest", true);
		query.addCondition(i,qc1);
		query.addAND();
		QueryCondition qc2 = new QueryCondition("lifeCycleState",
				QueryCondition.EQUAL, "RELEASED");
		query.addCondition(i, qc2);
		query.addAND();
		QueryCondition qc3 = new QueryCondition("rightBsoID",QueryCondition.EQUAL, partifc.getBsoID());
		query.addCondition(0, qc3);
		// ����(false�����򣬵�һ��Ԫ��ʱ�����硣true�ǽ��򣬵�һ��Ԫ��ʱ������)
		query.addOrderBy("modifyTime", false);
		Collection coll = pservice.findValueInfo(query, false);
		ListRoutePartLinkIfc linkifc = null;
		for(Iterator iter = coll.iterator();iter.hasNext();){
			linkifc = (ListRoutePartLinkIfc)iter.next();
		}
		if(linkifc!=null){
			resultstr = linkifc.getBsoID();
		}
		return resultstr;
	}
	
	/**
	   * ���ָ��·�ߵ�·�߷�֧��Ϣ
	   * @param linkBsoID ·�߱����㲿���Ĺ�������ListRoutePartLink��BsoID
	   * @return ���ؼ��ϵ�Ԫ��Ϊ���飬ÿ�������Ԫ������Ϊ��š�����·�ߴ���װ��·�ߴ�����/����Ҫ·��
	   */
	  public Collection getRoutes(String linkBsoID)
	  {
	    Vector v = new Vector();
	    PersistService pService = null;
	    try
	    {
	      pService = (PersistService)EJBServiceHelper.getService("PersistService");
	      ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)pService.refreshInfo(linkBsoID);
	      TechnicsRouteService routeService =
	          (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
	      Map map = routeService.getRouteBranchs(link.getRouteID());
	      Object[] branchs = RouteHelper.sortedInfos(map.keySet()).toArray();
	      for(int i=0;i<branchs.length;i++)
	      {
	        TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo)branchs[i];
	        String isMainRoute = "��";
	        if(branchinfo.getMainRoute())
	          isMainRoute = "��";
	        else
	          isMainRoute = "��";

	        String makeStr = "";
	        String assemStr = "";
	        Object[] nodes = (Object[])map.get(branchinfo);
	        Vector makeNodes = (Vector)nodes[0];              //����ڵ㼯��
	        RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1];   //װ��ڵ�

	        //{{{Ϊ���ɽڵ����׼��
	        Vector nodeVector = new Vector();
	        if(makeNodes!=null && makeNodes.size()>0)
	          nodeVector.addAll(makeNodes);
	        if(asseNode != null)
	          nodeVector.addElement(asseNode);
	        Object[] indexObjs = new Object[2];
	        indexObjs[0] = String.valueOf(i+1);
	        indexObjs[1] = nodeVector;

	        if(makeNodes != null && makeNodes.size()>0)
	        {
	          for(int m=0;m<makeNodes.size();m++)
	          {
	            RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
	            if(makeStr=="")
	              makeStr = makeStr + node.getNodeDepartmentName();
	            else
	              makeStr = makeStr +"��"+node.getNodeDepartmentName();
	          }
	        }
	        if(asseNode!=null)
	        {
	          assemStr = asseNode.getNodeDepartmentName();
	        }
	        if(makeStr==null ||makeStr.equals(""))
	        {
	          makeStr = "";
	        }
	        if(assemStr==null ||assemStr.equals(""))
	        {
	          assemStr = "";
	        }

	        Object[] array = {String.valueOf(i+1),makeStr,assemStr,isMainRoute};
	        v.addElement(array);
	      }
	    }
	    catch (Exception ex)
	    {
	      ex.printStackTrace();
	    }
	    return v;
	  }
	  
	/**�ж��ַ����ǲ��Ǵ�����
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
    //CCEnd SS48
	  
	// CCBegin SS50
	/**
	 * �����������ж��Ƿ���5001G01,5001G02,5002G01,5002G02,5103G01,5103G05,5103
	 * G07�⼸���ַ�����ͷ������Ƿ����·�ߡ���--�á�
	 * ����һ��boolean�������������������С��Ρ����á����Ҳ��������ܡ�·����ô����true�����򷵻�false
	 * 
	 * @param partifc
	 * @param dwbs
	 * @return
	 * @throws QMException
	 */
	public boolean isDeletePart(QMPartIfc partifc, String dwbs)
			throws QMException {
		boolean flag = false;
		HashMap submap = new HashMap();
		String[] subroutestr = new String[2];
		if (dwbs.equalsIgnoreCase("W34"))// �ɶ�ȡ����·��
		{
			submap = getSecondRoute("'" + partifc.getBsoID() + "'", submap);
			if (submap.get(partifc.getBsoID()) != null) {
				subroutestr = (String[]) (submap.get(partifc.getBsoID()));
			}
		} else {
			submap = getRoute("'" + partifc.getMasterBsoID() + "'", submap);
			if (submap.get(partifc.getMasterBsoID()) != null) {
				subroutestr = (String[]) (submap.get(partifc.getMasterBsoID()));
			}
		}
		String szz = subroutestr[0];
		String szp = subroutestr[1];
		
		if (partifc.getPartNumber().startsWith("5001G01")
				|| partifc.getPartNumber().startsWith("5001G02")
				|| partifc.getPartNumber().startsWith("5002G01")
				|| partifc.getPartNumber().startsWith("5002G02")
				|| partifc.getPartNumber().startsWith("5103G01")
				|| partifc.getPartNumber().startsWith("5103G05")
				|| partifc.getPartNumber().startsWith("5103G07")){
			if(szz!=null){
				if (szz.contains("��") && szz.contains("��") && !szz.contains("��")) {
					flag = true;
				}
			}
//			System.out.println("����ֵ===="+flag);
//			System.out.println("     ");
		}
		return flag;
	}
	// CCEnd SS50
	//CCBegin SS52
	/**
	 * ����������������¼
	 * 
	 * @param carModelCode
	 *            ������
	 * @param dwbs
	 *            ����
	 * @param mtree
	 *            ����//mtree��ÿ�������Ԫ��Ϊlinkid������id���޸ĺ������
	 * @throws QMException 
	 */
	public void saveChangeContentUpdate(String carModelCode, String dwbs,
			Collection mtree) throws QMException {
		//��Ҫ��ѯ�����б仯�����ݣ��������еĶ���
		for (Iterator iter = mtree.iterator(); iter.hasNext();) {
			String[] strs = (String[]) iter.next();
			String lingkid = strs[0];
			String parentid = strs[1];
			String afterquantity = strs[2];
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			String flag = getEffect(parentid, "", dwbs);
			if (flag.equals("3")) {
				try {
					conn = PersistUtil.getConnection();

					st = conn.createStatement();
					String sql = "select childPart,quantity from GYBomStructure where id='"
							+ lingkid + "'";
					rs = st.executeQuery(sql);
					while (rs.next()) {
						String childPart = rs.getString(1);
						String beforquantity = rs.getString(2);
						// ���Ƿ�Ƚ�ǰ����ĵ�����������Ƚ�ʹ��ע�͵��Ĵ��룬���Ƚ�ʹ��δע�ͷ���
						// int after = Integer.valueOf(afterquantity);
						// int befor = Integer.valueOf(beforquantity);
						// String sign = "";
						// if (after > befor) {
						// sign = "A";
						// String f = String.valueOf(after - befor);
						// try {
						// saveChangeContent(carModelCode, dwbs, parentid,
						// childPart, "", "", f, "A");
						// } catch (QMException e) {
						// e.printStackTrace();
						// }
						// } else if (after < befor) {
						// sign = "D";
						// String f = String.valueOf(befor - after);
						// try {
						// saveChangeContent(carModelCode, dwbs, parentid,
						// childPart, "", f, f, "D");
						// } catch (QMException e) {
						// e.printStackTrace();
						// }
						// }
						if (!beforquantity.equals(afterquantity)) {
							try {
								// ������Ƚϣ���ô����Ҫ����������¼��һ��ɾ����һ������
								//CCBegin SS79
//								saveChangeContent(carModelCode, dwbs, parentid,
//										childPart, "", beforquantity,
//										beforquantity, "D");
//								saveChangeContent(carModelCode, dwbs, parentid,
//										childPart, "", "", afterquantity, "A");
								saveChangeContent(carModelCode, dwbs, parentid,
										childPart,childPart,
										beforquantity, afterquantity, "T");
								//CCEnd SS79
							} catch (QMException e) {
								e.printStackTrace();
							}
						}
					}
					// �ر�Statement
					st.close();
					// �ر����ݿ�����
					conn.close();
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						st.close();
						conn.close();
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}
	
	/**����ɾ�������¼
	 * @param carModelCode ������
	 * @param dwbs ����
	 * @param mdtree //mdtree��ÿ�������Ԫ��Ϊlinkid
	 */
	public void saveChangeContentDelete(String carModelCode,String dwbs,Collection mdtree){
		for (Iterator iter = mdtree.iterator(); iter.hasNext();) {
			//CCBegin SS70
//			String lingkid = (String) iter.next();
			String str = (String) iter.next();
			String[] strs = str.split(";");
			String lingkid = strs[0];
			//CCEnd SS70
			Connection conn = null;
			Statement st = null;
			ResultSet rs = null;
			try {
				conn = PersistUtil.getConnection();

				st = conn.createStatement();
				String sql = "select parentPart,childPart,quantity from GYBomStructure where id='"
						+ lingkid + "'";
				rs = st.executeQuery(sql);
				while (rs.next()) {
					String parentPart = rs.getString(1);
					String childPart = rs.getString(2);
					String beforquantity = rs.getString(3);
					String flag = getEffect(parentPart, "", dwbs);
					if (flag.equals("3")) {
						try {
							// ������Ƚϣ���ô����Ҫ����������¼��һ��ɾ����һ������
							saveChangeContent(carModelCode, dwbs, parentPart,
									childPart, "", beforquantity,
									beforquantity, "D");
						} catch (QMException e) {
							e.printStackTrace();
						}
					}
				}
				// �ر�Statement
				st.close();
				// �ر����ݿ�����
				conn.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					st.close();
					conn.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
	}
	/** ɾ�������¼
	 * @param carid ���͵�partid
	 */
	public String deleteBOMContent(String carid,String dwbs){
//		System.out.println("���id===="+carid);
		try {
			PersistService pservice = (PersistService) EJBServiceHelper
					.getService("PersistService");
			QMPartIfc partifc = (QMPartIfc)pservice.refreshInfo(carid);
			String carModelCode = partifc.getPartNumber();
//			System.out.println("���ͱ��===="+carModelCode);
			Connection conn = null;
			Statement st = null;
			conn = PersistUtil.getConnection();
			st = conn.createStatement();
			String sql = "delete BOMChangeContent where carModelCode='"+carModelCode+"' and dwbs='"
					+dwbs+"'";
//			System.out.println("sql===="+sql);
			st.executeQuery(sql);
			// �ر�Statement
			st.close();
			// �ر����ݿ�����
			conn.close();
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		} catch (QMException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return"";
	}
	//CCEnd SS52
	//CCBegin SS62
	/**
	 * ���ҷ��Ϲ����һ��������
	 * 
	 * @param linkcoll
	 *            ���BOMһ��������
	 * @param dwbs
	 *            ����
	 * @return zcArr ���Ϲ����һ��������
	 */
	public ArrayList getZCFirstLevelPart(Collection linkcoll, String dwbs) {
		ArrayList zcArr = new ArrayList();
		try {
			for (Iterator iter = linkcoll.iterator(); iter.hasNext();) {
				Object obj[] = (Object[]) iter.next();
				PartUsageLinkIfc link = (PartUsageLinkIfc) obj[0];
				QMPartIfc sp = null;
				if (obj[1] instanceof QMPartIfc)
					sp = (QMPartIfc) obj[1];
				if (sp != null) {
					// ����·�ߵļ���
					HashMap submap = new HashMap();
					// ·������
					String[] subroutestr = new String[2];
					// �ж��û�����������Ȼ����ȡ��Ӧ·��
					if (dwbs.equals("W34"))// �ɶ�ȡ����·��
					{
						submap = getSecondRoute("'" + sp.getBsoID() + "'",
								submap);
						if (submap.get(sp.getBsoID()) != null) {
							subroutestr = (String[]) (submap.get(sp.getBsoID()));
						}
					} else {
						submap = getRoute("'" + sp.getMasterBsoID() + "'",
								submap);
						if (submap.get(sp.getMasterBsoID()) != null) {
							subroutestr = (String[]) (submap.get(sp
									.getMasterBsoID()));
						}
					}
					String szz = subroutestr[0];
					String szp = subroutestr[1];
					if (szz == null) {
						szz = "";
					}
					if (szp == null) {
						szp = "";
					}
					boolean flag = isLogical(sp, szz, szp);
					if (flag) {
						zcArr.add(sp.getPartNumber());
					}
					boolean flag1 = isEntity(sp, szz, szp);
					if (flag1) {
						zcArr.add(sp.getPartNumber());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return zcArr;
	}
	
	/**
     * �Ƿ���ʵ���
     * ������routeAsString ����·�ߣ�assembStrװ��·��
     * ����λ����G������װ��·�ߺ���
     * @return boolean
     * @throws QMException
     */
     public boolean isEntity(QMPartIfc part,String routeAsString ,String assembStr)throws QMException{
        boolean result=false;
        //��5λΪG����װ��·��Ϊ�ղ�������·�ߺ����á�
        if(part.getPartNumber().length()>5)
        if (!part.getPartNumber().substring(4, 5).equals("G"))
        {
        	if(assembStr.contains("��")){
                result=true;
            }
        }
        return result;
    }
	//CCEnd SS62
     //CCBegin SS63
     /**
 	 * ������"-ZC"��"-FC"��β�����·��
 	 * 
 	 * @param partID
 	 *            ���ID
 	 * @throws QMException
 	 */
 	public void createZCORFCTechnics(String partID) throws QMException {
 		long t1 = System.currentTimeMillis();
 		PersistService ps = (PersistService) EJBServiceHelper
 				.getPersistService();

 		PartServiceHelper psh = new PartServiceHelper();
 		// ��ȡ���BOM�ṹ
 		Vector vec = psh.getBOMList(partID,
 				"partNumber-0,partName-0,quantity-0,", "", "");
 		// �洢Ҫ����·�ߵ�������ϣ����ֵ����·�߼��ϣ�
 		Vector saveVec = new Vector();
 		// ·�߱�
 		TechnicsRouteListIfc tifc = new TechnicsRouteListInfo();
 		for (Iterator iter = vec.iterator(); iter.hasNext();) {
 			Object[] str = (Object[]) iter.next();
 			// Ԫ��0ΪID��Ԫ��4Ϊ���
 			String partid = (String) str[0];
 			String partnumber = (String) str[4];
 			
 			//��Ŵ���5λ�����ҵ�������λ��"J"����"j"�������λΪ�����ֵ�ʱ��Ŵ���·�ߣ����򲻹�
 			if (partnumber.length() > 5) {
 				//��������λ���ַ�
 				String final4 = partnumber.substring(partnumber.length()-4, partnumber.length()-3);
 				if ((final4.equals("J")||final4.equals("j"))&& partnumber.substring(4, 5).equals("G")) {
 					// ���һ���жϣ����-J�������λ���ֲ��Ǵ����֣���ô����ѯ
 					boolean isnum = false;
 					String finalnum = "";
// 					if (partnumber.length() == partnumber.indexOf("-J") + 5) {
// 						finalnum = partnumber.substring(partnumber
// 								.indexOf("-J") + 2);
// 					}
// 					if (partnumber.length() > partnumber.indexOf("-J") + 5) {
// 						finalnum = partnumber.substring(
// 								partnumber.indexOf("-J") + 2,
// 								partnumber.indexOf("-J") + 5);
// 					}
 					finalnum = partnumber.substring(partnumber.length()-3);
// 					System.out.println("finalnum======"+finalnum);
 					if (finalnum != null && !finalnum.trim().equals("")) {
 						isnum = isNumeric(finalnum);
 					}
// 					System.out.println("isnum======"+isnum);
 					if (isnum) {

 						QMPartIfc subpart = (QMPartIfc) ps.refreshInfo(partid);
// 						 System.out.println("--------"+partid+"========"+partnumber+"----------");

 						Collection scoll = ViewRouteLevelOne
 								.getRouteInfomationByPartmaster(subpart
 										.getMasterBsoID());
 						boolean flag = false;
 						if (scoll.size() == 0) {
 							flag = true;
 						}
 						// System.out.println("flag======"+flag);
 						if (flag) {
 							// ��ȡ������ı��
 							String jqpartnumber = "";
// 							if (partnumber.indexOf("-J") > 0) {
// 								jqpartnumber = partnumber.substring(0,
// 										partnumber.indexOf("-J"));
// 							} else if (partnumber.indexOf("-j") > 0) {
// 								jqpartnumber = partnumber.substring(0,
// 										partnumber.indexOf("-j"));
// 							}
 							jqpartnumber = partnumber.substring(0, partnumber.length()-4);
 							if(jqpartnumber.endsWith("-")){
 								jqpartnumber = jqpartnumber.substring(0, jqpartnumber.length()-1);
 							}
 							// System.out.println("==========-----------"+jqpartnumber);
 							// CCBegin SS54
 							// QMQuery query = new QMQuery("QMPartMaster");
 							QMQuery query = new QMQuery("GenericPartMaster");
 							// CCEnd SS54
 							QueryCondition condition1 = new QueryCondition(
 									"partNumber", "=", jqpartnumber);
 							query.addCondition(condition1);
 							Collection col = ps.findValueInfo(query);
 							Iterator iterz = col.iterator();
 							// CCBegin SS54
 							// QMPartMasterIfc pmaster = null;
 							GenericPartMasterIfc pmaster = null;
 							// CCEnd SS54
 							while (iterz.hasNext()) {
 								// CCBegin SS54
 								// pmaster = (QMPartMasterIfc)iterz.next();
 								pmaster = (GenericPartMasterIfc) iterz.next();
 								// CCEnd SS54
 							}
 							// master��Ϊ�յ�ʱ���ȥ����·��
 							if (pmaster != null) {
 								// System.out.println("���ڴ˼���������");
 								// �������µĹ���routelistpartlinkid
 								String linkid = searchLinkID(pmaster);
 								// System.out.println("linkid-----------------==========================="+linkid);
 								if (linkid != "") {
 									// ����·�ߣ�ÿ��Ԫ���Ǹ����飬���������ݷֱ�Ϊ������·�ߡ�װ��·�ߡ��Ƿ���Ҫ·�ߣ�
 									Collection routecoll = getRoutes(linkid);
 									// ��·�ߵ�ʱ���ȥ��ż��ϣ����򲻴棨���ֵ����·�߼��ϣ�
 									if (routecoll.size() > 0) {
 										Object[] result = new Object[3];
 										result[0] = subpart;
 										result[1] = routecoll;
 										saveVec.add(result);// �浽������
 									}
 								}
 							}
 						}
 					}
 				}
 			}
 		}

 		// �����ϴ���0�Ŵ���·�߱�
 		if (saveVec.size() > 0) {
 			// ���BOM����ֵ����
 			QMPartIfc desPartIfc = (QMPartIfc) ps.refreshInfo(partID);
 			String desMasterID = desPartIfc.getMasterBsoID();
 			// ·�߱���
 			String technicsNumber = "intGYBOM" + desPartIfc.getPartNumber();
 			TechnicsRouteService trs = (TechnicsRouteService) EJBServiceHelper
 					.getService("TechnicsRouteService");
 			tifc = trs.findRouteListByNum(technicsNumber);
 			if (tifc == null) {
 				tifc = createRouteList(desPartIfc, desMasterID, saveVec);
 			}
 			// �����㲿������
 			StandardPartService sps = (StandardPartService) EJBServiceHelper
 					.getService("StandardPartService");

 			// ѭ������������ϵ
 			for (Iterator iter = saveVec.iterator(); iter.hasNext();) {
 				Object[] str = (Object[]) iter.next();// �����ֵ����·�߼��ϣ�
 				createListRoutePartLink(tifc, str, desPartIfc.getPartNumber(),
 						desPartIfc.getPartName());
 				// tifc.getProductID();
 			}
 		}
 		long t2 = System.currentTimeMillis();
 		System.out.println("��������������ʱ��======" + (t2 - t1) + " ����");
 	}
 	//CCEnd SS63
 	//CCBegin SS34
 	/**���˵�����Ҫ�汾����������������ɵ�������
 	 * @param part ���ֵ����
 	 * @param partnumber ���汾��������
 	 * @return
 	 */
 	public String getPartNumber(QMPartIfc part,String partnumber){
 		String partnum = part.getPartNumber();//����ı��
 		if(partnum.endsWith("/A")){
 			Connection conn=null;
 			Statement stmt=null;
 			ResultSet rs=null;
 			try {
				conn=PersistUtil.getConnection();
				stmt=conn.createStatement();
				String oldsql="select nowPart from NoVersionPart where  originalPart='"+partnum+"'";
				rs=stmt.executeQuery(oldsql);
				while(rs.next()){
					partnumber = rs.getString(1);
				}
 			} catch (QueryException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
 		}
 		return partnumber;
 	}
 	//CCEnd SS34
 	//CCBegin SS66
 	/**
     * ���򷽷�
     * @param hehe Vector ������Ϣ������ɵ�bom���ϡ�
     * @param numSite int �㲿������������ڵ�λ�á�
     * @return Vector �����bom���ϡ�
     */
    private Vector sortTongJiVector(Vector hehe, int numSite) {

      Object[] mainObject = null;
      int b = hehe.size();
      Object[] aa = null;
      Object[] bb = null;
      String partNum = "";
      String partNum1 = "";
      for (int i = 0; i < b; i++)
      {
        for (int j = i; j < b; )
        {
          aa = (Object[]) hehe.elementAt(i);
          partNum = (String) aa[numSite];
          bb = (Object[]) hehe.elementAt(j);
          partNum1 = (String) bb[numSite];
          if (partNum.compareTo(partNum1) > 0)
            {
              hehe.setElementAt(bb, i);
              hehe.setElementAt(aa, j);
            }
            j++;
        }
      }
      if (mainObject != null) {
        hehe.add(0, mainObject);
      }

      return hehe;
    }//CCEnd SS66
    
    //CCBegin SS71
    /**
     * @param linkid linkid
     * @param yid ԭ�ڵ�id
     * @param xid �滻�ڵ�id
     * @param carModelCode ����
     * @param dwbs ����
     * @param parentid ����id
     * @param changeType 
     * @return
     * @throws QMException
     */
	public String changePartOther(String linkid, String yid, String xid,
			String carModelCode, String dwbs, String parentid, String productid)
			throws QMException {
		System.out.println(parentid+"===="+productid);
		String str = "";
		try {
			PersistService ps = (PersistService) EJBServiceHelper
					.getPersistService();
			StandardPartService spService = (StandardPartService) EJBServiceHelper
					.getService("StandardPartService");
			QMPartIfc part = (QMPartIfc) ps.refreshInfo(yid);
			QMPartIfc nnpart = (QMPartIfc) ps.refreshInfo(xid);

			// ���¹������Ӽ�id�滻����һ���㲿��id
			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			Statement st = null;
			Statement st1 = null;
			try {
				conn = PersistUtil.getConnection();
				String[] strs = productid.split(",");
				for(int i =0;i<strs.length;i++){
					String parentbsoid = strs[i];
					st1 = conn.createStatement();
					st1.executeQuery("update GYBomStructure set childPart='"
							+ nnpart.getBsoID() + "',childNumber='"
							+ nnpart.getPartNumber() + "',bz1='" + "red"
							+ "' where effectCurrent='0' and childPart='"
							+ part.getBsoID() + "' and parentPart='" + parentbsoid+ "' and dwbs='" + dwbs + "'");
					st1.close();
				}
				stmt = conn.createStatement();
				stmt.executeQuery("update GYBomStructure set childPart='"
						+ nnpart.getBsoID() + "',childNumber='"
						+ nnpart.getPartNumber() + "',bz1='" + "red"
						+ "' where effectCurrent='0' and childPart='"
						+ part.getBsoID() + "' and parentPart='" + parentid+ "' and dwbs='" + dwbs + "'");

				// �ر�Statement
				stmt.close();
				// �ر����ݿ�����
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException ex1) {
					ex1.printStackTrace();
				}
			}

			// �����Ӽ�����
			Collection mtree = new ArrayList();
			Vector linkvec = new Vector();
			mtree = getBomLinks(mtree, nnpart, carModelCode, dwbs, linkvec);
			// CCBegin SS15
			saveGYBom(mtree, true, dwbs);// SS21
			// CCEnd SS15

			// ��ȡ��ǰ�㲿�������Ӽ����½ṹ
			str = getGYBomAll(linkid, nnpart.getBsoID(), carModelCode, dwbs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
  	//CCEnd SS71
	// CCBegin SS70
	/**
	 * �ж������Ƿ���ͬ
	 * @param dwbs ����
	 * @param linkid ����id
	 * @param parentid ����id
	 * @param afterquantity ����
	 * @return
	 * @throws QMException
	 */
	public boolean isSameCount(String dwbs, String linkid, String parentid,
			String afterquantity) throws QMException {
		boolean result = false;
		// ��Ҫ��ѯ�����б仯�����ݣ��������еĶ���
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String updatesql1 = "";
		String flag = getEffect(parentid, "", dwbs);
		if (flag.equals("3")) {
			try {
				conn = PersistUtil.getConnection();

				st = conn.createStatement();
				String sql = "select childPart,quantity from GYBomStructure where id='"
						+ linkid + "'";
				rs = st.executeQuery(sql);
				while (rs.next()) {
					String childPart = rs.getString(1);
					String beforquantity = rs.getString(2);
					if (!beforquantity.equals(afterquantity)) {
						result = true;
					}
				}
				// �ر�Statement
				st.close();
				// �ر����ݿ�����
				conn.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					st.close();
					conn.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;

	}
	// CCEnd SS70
	// CCBegin SS72
	/**
	 * �����㲿��
	 * 
	 * @param partid
	 *            ѡ�нڵ����id
	 * @param dwbs
	 *            ����
	 * @return
	 * @throws QMException
	 */
	public String lockPart(String partid, String dwbs) throws QMException {
		String result = "";
		PersistService ps = (PersistService) EJBServiceHelper
				.getPersistService();
		StandardPartService spService = (StandardPartService) EJBServiceHelper
				.getService("StandardPartService");
		SessionService sessionser = (SessionService) EJBServiceHelper
				.getService("SessionService");
		UserIfc user = sessionser.getCurUserInfo();
		String userid = user.getBsoID();
		Connection conn = null;
		Statement st = null;
		try {
			conn = PersistUtil.getConnection();
			st = conn.createStatement();
			// ���µ�ǰ�ڵ����״̬
			String sql = "update GYBomStructure set locker='" + userid
					+ "',bz1='red' where effectCurrent='0' and childPart='"
					+ partid + "' and dwbs = '" + dwbs + "'";
			int i = st.executeUpdate(sql);
			if (i > 0) {
				result = "[{" + "�����ɹ�" + "}]";
			} else {
				result = "[{" + "����ʧ��" + "}]";
			}
			// �ر�Statement
			st.close();
			// �ر����ݿ�����
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * �����㲿��
	 * 
	 * @param partid
	 *            ѡ�нڵ����id
	 * @param dwbs
	 *            ����
	 * @return
	 * @throws QMException
	 */
	public String unLockPart(String partid, String dwbs) throws QMException {
		String result = "";
		PersistService ps = (PersistService) EJBServiceHelper
				.getPersistService();
		SessionService sessionser = (SessionService) EJBServiceHelper
				.getService("SessionService");
		UserIfc user = sessionser.getCurUserInfo();
		String userid = user.getBsoID();
		Connection conn = null;
		Statement st = null;
		String s = searchLockUser(partid, dwbs);
		if (s.equals("2")) {
			try {
				conn = PersistUtil.getConnection();
				st = conn.createStatement();
				// ���µ�ǰ�ڵ����״̬
				String sql = "update GYBomStructure set locker='',bz1='red' where effectCurrent='0' and childPart='"
						+ partid + "' and dwbs = '" + dwbs + "'";
				int i = st.executeUpdate(sql);
				if (i > 0) {
					result = "[{" + "�����ɹ�" + "}]";
				} else {
					result = "[{" + "����ʧ��" + "}]";
				}
				// �ر�Statement
				st.close();
				// �ر����ݿ�����
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					st.close();
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * ��ѯ�����û��Ƿ�Ϊ��ǰ�û�
	 * 
	 * @return false û�е�ǰ�û������Ķ���,true �е�ǰ�û������Ķ���
	 * @throws QMException
	 */
	public String searchLockUser(String partid, String dwbs) throws QMException {
		String s = "";
		SessionService sessionser = (SessionService) EJBServiceHelper
				.getService("SessionService");
		UserIfc user = sessionser.getCurUserInfo();
		String userid = user.getBsoID();
		Connection conn = null;
		Statement st = null;
		
		ResultSet rs = null;
		
		try {
			conn = PersistUtil.getConnection();
			st = conn.createStatement();
			// ���µ�ǰ�ڵ����״̬
			String sql = "select count(*) from GYBomStructure where dwbs='"
					+ dwbs + "' and childPart='" + partid
					+ "' and effectCurrent='0' and locker is null";
			// int i = st.executeUpdate(sql);
			rs = st.executeQuery(sql);
			rs.next();
			int i = rs.getInt(1);
			System.out.println("i===="+i);
			// ���iС��0����ôs����0��֤��û�м�������;
			// ���i����0��֤���м������󣬼���ִ�г������Ϊ��ǰ�û�����������2�����򷵻�1
			if (i == 0) {
				Statement st1 = null;
				ResultSet rs1 = null;
				st1 = conn.createStatement();
				String sql1 = "select count(*) from GYBomStructure where dwbs='"
						+ dwbs
						+ "' and childPart='"
						+ partid
						+ "' and effectCurrent='0' and locker = '"
						+ userid
						+ "'";
				rs1 = st1.executeQuery(sql1);
				rs1.next();
				int m = rs1.getInt(1);
				if (m > 0) {
					s = "2";
				} else {
					s = "1";
				}
				rs1.close();
				st1.close();
			} else {
				s = "0";
			}
			rs.close();
			
			// �ر�Statement
			st.close();
			
			// �ر����ݿ�����
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return s;
	}
	//CCEnd SS72
	//CCBegin SS74
	/**
	 * ���ɼ�ʻ�ҳ�ʼ����BOM
	 */
	public void initGYJSSBom(String partID,String gyID,String dwbs,String newnumber) throws QMException
	{
		try
		{
			// ��ʻ���߼��ܳ�
			String partNumberRuleNS=RemoteProperty.getProperty("gybomlistns");
			Vector jssparts=new Vector();
			Collection mtree=new ArrayList();
			Collection ytree=new ArrayList();
			Vector linkvec=new Vector();
			StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
			JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=(QMPartIfc)ps.refreshInfo(partID);
			QMPartIfc gypart=(QMPartIfc)ps.refreshInfo(gyID);
			UserInfo user=getCurrentUserInfo();
			PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
			Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);
			String endcode = "";
			endcode = getEndCode(part,endcode);
//			System.out.println("endcode===="+endcode);
//			for(Iterator iter=linkcoll.iterator();iter.hasNext();)
//			{
//				Object obj[]=(Object[])iter.next();
//				PartUsageLinkIfc link=(PartUsageLinkIfc)obj[0];
//				if(obj[1] instanceof QMPartIfc)
//				{
//					QMPartIfc subpart=(QMPartIfc)obj[1];
//					// ��ʻ��
//					if(subpart.getPartNumber().length()>=7
//						&&partNumberRuleNS.indexOf(subpart.getPartNumber().substring(0,7))!=-1)
//					{
//						jssparts.add(new String[]{subpart.getBsoID(),String.valueOf(link.getQuantity()),
//							subpart.getPartNumber()});
//						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
//							gypart.getPartNumber(),dwbs,user.getBsoID(),"",link.getDefaultUnit().getDisplay(),
//							subpart.getPartNumber()};
//						ytree.add(mt1);
//					}
//					else
//					{
//						String[] mt1=new String[]{partID,subpart.getBsoID(),String.valueOf(link.getQuantity()),
//							gypart.getPartNumber(),dwbs,"","",link.getDefaultUnit().getDisplay(),
//							subpart.getPartNumber()};
//						ytree.add(mt1);
//					}
//				}
//			}
//			// ��ʻ��
//			Iterator jssiter=jssparts.iterator();
//			ArrayList array = new ArrayList();//SS20
//			QMPartIfc jsssp=null;
//			while(jssiter.hasNext())
//			{
//				String[] jssstr1=(String[])jssiter.next();
//				jsssp=(QMPartIfc)ps.refreshInfo(jssstr1[0]);
//				String[] mt1=new String[]{gyID,jsssp.getBsoID(),jssstr1[1],gypart.getPartNumber(),dwbs,"0","","","��",
//					jssstr1[2]};
//				mtree.add(mt1);
//				mtree=getBomLinks(gyID,mtree,jsssp,jsssp.getPartNumber(),dwbs,linkvec,"jss",array, new ArrayList());//SS20//SS37
//			}
//			// ���ݹ�����нṹ���
//			Collection ntree=chaifengybom(gyID,mtree);
//			// ���ݹ���Է��Ϲ���ļ����и��������Ϊ��
//			Collection rtree=renameGyjssBom(ntree,newnumber);
//
//			saveGYBom(rtree, true, dwbs);
////			saveBom(ytree);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	/**���5000010��5000020��5000030�����׺
	 * @param part
	 * @throws QMException 
	 * 
	 */
	private String getEndCode(QMPartIfc part,String endcode) throws QMException {
//		String resultstr = "";
		StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
		PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
		JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
		Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);
		for(Iterator iter = linkcoll.iterator();iter.hasNext();){
			Object obj[]=(Object[])iter.next();
			if(obj[1] instanceof QMPartIfc){
				QMPartIfc part1 = (QMPartIfc)obj[1];
				String partnumber = part1.getPartNumber();
				if(partnumber.startsWith("5000040")){
//					System.out.println("partnumber======"+partnumber);
					endcode = partnumber.substring(7);
//					System.out.println("endcode======"+endcode);
					return endcode;
				}else{
					endcode = getEndCode(part1,endcode);
				}
			}
		}
		return endcode;
	}
	//CCEnd SS74
	//	CCBegin SS76
	//	CCBegin SS82
    public String importGYBom(String csvData) throws QMException
	{
   	 //�Ƿ��滻
   	 boolean isupdate = true;
		if (csvData == null || csvData.equals("")) {
			return "������ļ�Ϊ�գ�";
		}
		HashMap exitMap = new HashMap();//�Ƿ���ڸ������Ӽ�������������ͬ�ļ�
		HashMap exitMapBOM = new HashMap();//�Ƿ���ڸ��� ���ڵ�bom
		GYBomHelper ghelper = new GYBomHelper();
		QMPartIfc ppart=null;
		QMPartIfc spart=null;
		HashMap partmap=new HashMap();
		StringBuffer buffer=new StringBuffer();
		String dwbs=getCurrentDWBS();
		//System.out.println("dwbs=="+dwbs);
		Vector deleteParts=new Vector();
		Collection mtree=new ArrayList();
		Vector parentParts=new Vector();
		
		PersistService service = (PersistService) EJBServiceHelper
				.getService("PersistService");

		if (csvData != null && !csvData.equals("")) {
			String[] csvRow = csvData.split("\r\n");
			if (csvRow != null && csvRow.length > 0) {
				for (int i = 1; i < csvRow.length; i++) {
					String[] message = new String[2];	
					String row = csvRow[i];
					String[] partInformation = row.split(",");
					String pnum=partInformation[0];// �������
					String snum=partInformation[1];// �Ӽ����
					String unit=partInformation[2];// ��λ
					String quantity=partInformation[3];// ʹ������
					//String subGroup=partInformation[4];;// ����
					
//					System.out.println("parentNumber=="+pnum+","+snum+","+unit+","+quantity);
//					System.out.println("childNumber=="+snum);
//					System.out.println("dw=="+unit);
//					System.out.println("quantity=="+quantity);
                    //�жϸø����Ƿ���ڱ��,��������򲻽��д���
					if(exitMap.containsKey(pnum))
						continue;
					// ���4�����ԣ�������ǿգ���������Ҳ�����¼��
					if((pnum==null||pnum.equals(""))&&(snum==null||snum.equals(""))
						&&(unit==null||unit.equals(""))&&(quantity==null||quantity.equals("")))
					{
						// System.out.println("��"+i+"��û�е������ݣ�������");
						continue;
					}
					// ���4�������Ƿ�Ϊ�գ����Ϊ�����¼��־���������롣
					if(pnum==null||pnum.equals("")||snum==null||snum.equals("")||unit==null
						||unit.equals("")||quantity==null||quantity.equals(""))
					{
						logbuffer(buffer,"��"+(i+1)+"��");
						if(pnum==null||pnum.equals(""))
						{
							logbuffer(buffer," �������");
						}
						if(snum==null||snum.equals(""))
						{
							logbuffer(buffer," �Ӽ����");
						}
						if(unit==null||unit.equals(""))
						{
							logbuffer(buffer," ��λ");
						}
						if(quantity==null||quantity.equals(""))
						{
							logbuffer(buffer," ʹ������");
						}
						logbuffer(buffer," ����û������\n");
						continue;
					}
					
					// ���ݱ�Ż�ȡ�����㲿��
					
					
					ppart=null;
					spart=null;
					Connection conn=null;
					
					if(!partmap.containsKey(pnum))
					{
			
						partmap.put(pnum,findPartByNumber(pnum));
						ppart=(QMPartIfc)partmap.get(pnum);
				        String cappNumber = "";
				        String ver = "";
				        
						if(ibabs=="0"&&ppart!=null){
				
							String version = (String)svmap.get(ppart.getBsoID());
							if(pnum.indexOf("/")>-1)
							{
								int l=snum.length();
								int ii=snum.indexOf("/");
								// System.out.println("l===="+l);
								// System.out.println("i===="+i);
								if(l-ii==2)
								{
									ver=pnum.substring(l-1);
						
								}
								// System.out.println("number===="+number);

							}
							if(version!=null&&!ver.equals("")&&ver!=null){
								//˵���汾��һ��
								//�����Ƿ��������������ﲻ����
							//	System.out.println("����Ϊɶ�������===="+pnum);
								 String sql1 = "select t.beforematerial from erppan t " +
								  "where t.aftermaterial = '"+pnum+"'";
								   PreparedStatement pstmt1;
								try {
									conn=PersistUtil.getConnection();
									pstmt1 = conn.prepareStatement(sql1);
								
									//pstmt1.setFetchSize(10);
									ResultSet rs1 = pstmt1.executeQuery();
									//int bs = 0;
									while(rs1.next())
									{
										cappNumber =  rs1.getString(1) == null ? "" : rs1.getString(1).trim();
										break;
									}									
									
									pstmt1.close();
									pstmt1 = null;
									rs1.close();
									rs1 = null;
								} catch (SQLException e) {
									// TODO �Զ����� catch ��
									e.printStackTrace();
								}
								if(cappNumber.equals("")){
									logbuffer(buffer," ��"+(i+1)+"��");
									logbuffer(buffer," \nϵͳ�и������Ӧ�ķ���Դ�汾��ƥ��"+pnum);
								}
							}
							
							
						}
					}
					//System.out.println("snum=="+snum);
					if(!partmap.containsKey(snum))
					{
						String cappNumber = "";
						partmap.put(snum,findPartByNumber(snum));
						spart=(QMPartIfc)partmap.get(snum);
						String number = "";
						String ver = "";
						if(ibabs=="0"&&spart!=null){
//							System.out.println("spart.getBsoID()=="+spart.getBsoID());
							String version = (String)svmap.get(spart.getBsoID());
//							System.out.println("version=="+version);
							if(snum.indexOf("/")>-1)
							{
								int l=snum.length();
								int ii=snum.indexOf("/");
								// System.out.println("l===="+l);
								// System.out.println("i===="+i);
								if(l-ii==2)
								{
									ver=snum.substring(l-1);
						
								}
								// System.out.println("number===="+number);

							}
							//�汾��Ϊ�յĲŴ���
				
							if(version!=null&&!ver.equals("")&&ver!=null){
								
//								˵���汾��һ��
								//�����Ƿ��������������ﲻ����
								 String sql1 = "select t.beforematerial from erppan t " +
								  "where t.aftermaterial = '"+snum+"'";
								// System.out.println("�Ӽ�Ϊɶ�������===="+sql1);
								   PreparedStatement pstmt1;
								try {
									 conn=PersistUtil.getConnection();
									pstmt1 = conn.prepareStatement(sql1);
								
									//pstmt1.setFetchSize(10);
									ResultSet rs1 = pstmt1.executeQuery();
									//int bs = 0;
									while(rs1.next())
									{
										cappNumber =  rs1.getString(1) == null ? "" : rs1.getString(1).trim();
										break;
									}
	
									pstmt1.close();
									pstmt1 = null;
									rs1.close();
									rs1 = null;
								} catch (SQLException e) {
									// TODO �Զ����� catch ��
									e.printStackTrace();
								}
								if(cappNumber.equals("")){
									logbuffer(buffer," ��"+(i+1)+"��");
									logbuffer(buffer," snum+ \nϵͳ�и������Ӧ�ķ���Դ�汾��ƥ��\n");
								}
								//continue;
							}
						}
					}
					
					
					
					
					
					ppart=(QMPartIfc)partmap.get(pnum);
					spart=(QMPartIfc)partmap.get(snum);
					// ����㲿���Ƿ���ڣ�������������¼��־���������롣
					if(ppart==null||spart==null)
					{
						logbuffer(buffer," ��"+(i+1)+"��");
						if(ppart==null)
						{
							logbuffer(buffer," \n ����"+pnum);
						}
						if(spart==null)
						{
							logbuffer(buffer," \n�Ӽ�"+snum);
						}
						logbuffer(buffer," ϵͳ�޴˼�\n");
						continue;
					}
				
					ppart=(QMPartIfc)partmap.get(pnum);
					spart=(QMPartIfc)partmap.get(snum);

					if(ppart==null||spart==null)
					{
						logbuffer(buffer," ��"+(i+1)+"��");
						if(ppart==null)
						{
							logbuffer(buffer," \n ����"+pnum);
						}
						if(spart==null)
						{
							logbuffer(buffer," \n�Ӽ�"+snum);
						}
						logbuffer(buffer," ϵͳ�޴˼�\n");
						continue;
					}
					
					
				
					

					if(isupdate)
					{
						String exitstr="0";
						// ����Ƿ���ڸù���BOM������������¼��־���������롣
						if(exitMapBOM.containsKey(ppart.getPartNumber())){
							 exitstr="1";
						}else{
							 exitstr=ghelper.getExitBOM(ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs);
						}
					
						
						 //System.out.println(ppart.getPartNumber()+" eff:"+exitstr);
						// ������Ч�ṹ
						if(exitstr.equals("0"))
						{
							
							String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
								"1","","","��",spart.getPartNumber(),""};
							//System.out.println("mt=="+mt);
							mtree.add(mt);
						}
						else // ɾ��δԭ�нṹ�������½ṹ
						{
							// ���������δ��Ч�ṹ�Ѿ�ɾ��������������������ӡ�
//							if(!deleteParts.contains(ppart.getBsoID()+",0"))
//							{
//								deleteParts.add(ppart.getBsoID()+",0");
//							}
//							String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
//	 								"1","","","��",spart.getPartNumber(),""};
//							mtree.add(mt);
							exitMapBOM.put(ppart.getPartNumber(), pnum);
							String exitbom=ghelper.getExitBOMnew(ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs);
							
							if(exitbom.equals("1")){
								exitMap.put(ppart.getPartNumber(), pnum);
							}		
					  }
					}
					else
					{
						// ������� �Ѵ�����Ч����������Ҫ���е��뱣�档 �����Ѵ��ڼ���
						if(checkParentPartExit(ppart.getBsoID(),dwbs,"1"))
						{
							parentParts.add(pnum);
						}
						else
						{
							String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
								"1","","","��",spart.getPartNumber(),""};
							mtree.add(mt);
						}
					}
				
				}
				// ÿ�������ļ�����һ�����ͽ���һ�δ洢��
			//	System.out.println("exitMap=="+exitMap);
				if(isupdate)
				{
					//System.out.println("deleteParts=="+deleteParts);
//					for(int j=0;j<deleteParts.size();j++)
//					{
//						String[] str=deleteParts.elementAt(j).toString().split(",");
//						deleteGYBomLevelAll(str[0],dwbs);
//					}
				}
				
				if(buffer.toString().length()>0)
					return buffer.toString() ;
				//System.out.println("partmap=="+partmap);
				
				//System.out.println("mtree=="+mtree);
				saveGYBom(mtree, true, dwbs);
			}
		}
		System.out.println("exitMap1111=="+exitMap);
       if(exitMap.size()>0){
    	   logbuffer(buffer,"ϵͳ�����Ѵ������±���ṹ"+"\r\n");
    	   for(Iterator it=exitMap.keySet().iterator();it.hasNext();)
   		{
   			String number=(String)it.next();
   			
   			String EXITNUMBER=(String)exitMap.get(number);
    		  // String number = exitMap.keySet().toString();
    		   logbuffer(buffer,EXITNUMBER+"\r\n");
    	   }
       }
		logbuffer(buffer,"������ϡ�\n");
	

	return buffer.toString();
	}
    //CCEnd SS82
    /**
	 * ɾ��ָ���㲿����һ���ṹ�� ��ʼ������BOMǰ����ո�������һ��δ��ЧBOM������BOMҲ�����һ��δ��ЧBOM
	 * 
	 * @param parentPart
	 *            ����
	 * @param dwbs
	 *            ����
	 * @param eff
	 *            1 ��Ч�ṹ�� 0δ��Ч�ṹ
	 */
	private void deleteGYBomLevelAll(String parentPart,String dwbs )
	{

		Connection conn=null;
		Statement st=null;
		try
		{
			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			String sql="delete GYBomStructure where parentPart='"+parentPart+"' and dwbs='"+dwbs
				+"'";
			st.executeQuery(sql);
			System.out.println("deletePartssql=="+sql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				st.close();
				conn.close();
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
    //CCEnd SS76
	  //CCBegin SS80
	/**
	 * У�鹤��bom�Ƿ�༭·�߲�����
	 */
	public Vector checkBom(String partid) throws QMException
	{
//		System.out.println("getExportBomList:"+carModelCode);
		Vector vec=new Vector();
		HashMap routemap=new HashMap();
		String dwbs=getCurrentDWBS();
	//	QMPartIfc part=findPartLasterByNumber(carModelCode);
		//CCBegin SS19
		//vec.add(new String[]{part.getPartNumber(),part.getPartName(),"","","","",part.getBsoID(),part.getMasterBsoID()});
		//vec.add(new String[]{"0",part.getPartNumber(),part.getPartName(),"","","","",part.getBsoID(),part.getMasterBsoID()});
		//CCEnd SS19
		System.out.println("part==="+partid);
		if(dwbs.equals("W34")){
			checkbianliBomList(vec,partid,partid,dwbs,1,"",routemap);
		}
//		System.out.println("vec=="+vec.size());
		/*
		 * for(int i=0;i<vec.size();i++) { String[] str =
		 * (String[])vec.elementAt(i);
		 * System.out.println(str[0]+"&&"+str[1]+"&&"
		 * +str[2]+"&&"+str[3]+"&&"+str[4]+"&&"+str[5]+"&&"+str[6]+"&&"+str[7]);
		 * }
		 */
		return vec;
	}
	/**
	 * У�鹤��bom�Ƿ�༭·�߲�����
	 */
	private void checkbianliBomList(Vector vec,String id,String carModelCode,String dwbs,int level,String pzz,
		HashMap routemap) throws QMException
	{
		PersistService ps=(PersistService)EJBServiceHelper.getPersistService();

		String effect=getEffect(id,carModelCode,dwbs);
		if(effect.equals("1"))
		{
			effect="0";
		}
		else if(effect.equals("2")||effect.equals("3"))
		{
			effect="1";
		}
		// ��ȡ�Ӽ�id������
		Vector subvec=findChildPart(id,carModelCode,dwbs,effect);
		QMPartIfc parentpart=(QMPartIfc)ps.refreshInfo(id);

		// ��ȡ�Ӽ�·��
		String childPart="";
		QMPartIfc part=null;
		String insql="";
		for(int i=0;i<subvec.size();i++)
		{
			String[] str=subvec.elementAt(i).toString().split(",");
			childPart=str[0];
			part=(QMPartIfc)ps.refreshInfo(childPart);

			if(part==null)
			{
				System.out.println("δ�ҵ��Ӽ�id��"+childPart);
				continue;
			}
			if(routemap.containsKey(part.getMasterBsoID()))
			{
				continue;
			}
			if(insql.equals(""))
			{
				if(dwbs.equals("W34"))
				{
					insql="'"+part.getBsoID()+"'";
				}
				else
				{
					insql="'"+part.getMasterBsoID()+"'";
				}
			}
			else
			{
				if(dwbs.equals("W34"))
				{
					insql=insql+",'"+part.getBsoID()+"'";
				}
				else
				{
					insql=insql+",'"+part.getMasterBsoID()+"'";
				}
			}
		}
		if(!insql.equals(""))
		{
			if(dwbs.equals("W34"))
			{
				routemap=getSecondRoute(insql,routemap);
			}
			else
			{
				routemap=getRoute(insql,routemap);
			}
		}

		// �ݹ��ȡ�Ӽ�
		String zhizao="";
		String zhuangpei="";

		com.faw_qm.cderp.util.PartHelper helper=new com.faw_qm.cderp.util.PartHelper();

		for(int i=0;i<subvec.size();i++)
		{
			String[] str=subvec.elementAt(i).toString().split(",");
			childPart=str[0];
			part=(QMPartIfc)ps.refreshInfo(childPart);

			if(dwbs.equals("W34"))
			{
				if(routemap.get(part.getBsoID())!=null)
				{
					String[] routestr=(String[])(routemap.get(part.getBsoID()));
					zhizao=routestr[0];
					zhuangpei=routestr[1];
				}
			}
			else
			{
				if(routemap.get(part.getMasterBsoID())!=null)
				{
					String[] routestr=(String[])(routemap.get(part.getMasterBsoID()));
					zhizao=routestr[0];
					zhuangpei=routestr[1];
				}
			}
//			if(zhuangpei.equals(""))
//			{
//				zhuangpei=pzz;
//			}

			String pnum=helper.getMaterialNumber(part,part.getVersionID());

			//�������·��Ϊ������뵽����
			if(zhizao.equals("")||(zhizao.length()==0))
			vec.add(new String[]{Integer.toString(level),pnum,part.getPartName(),str[1],zhizao,zhuangpei,parentpart.getPartNumber()});
		
			checkbianliBomList(vec,childPart,carModelCode,dwbs,level+1,zhizao,routemap);

			zhizao="";
			zhuangpei="";
		}
	}
	  //CCEnd SS80
}
