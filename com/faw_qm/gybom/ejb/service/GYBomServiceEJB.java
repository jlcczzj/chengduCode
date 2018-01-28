/**
 * 生成程序时间 2016-5-16 版本 1.0 作者 
 * 刘楠 版权归一汽启明公司所有 本程序属一汽启明公司的私有机要资料 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序 保留所有权利 
 * SS1 成都导入工艺BOM时代零件版本，此处修改的是查询零部件时拆分零件号 liuyuzhu 2016-10-13 
 * SS2 创建零部件后，设置零部件生命周期状态为“生产准备” liuyuzhu 2016-10-21
 * SS3 添加零部件，获取工艺BOM结构保存，不再获取设计BOM。获取工艺BOM结构规则为，当前工厂生效的任意一套结构。 liunan 2016-12-6不需要
 * SS4 A032-2016-0137 1、 工艺BOM 文件导入时，根据文件中零件编号中”/“后的版本，生成相应的BOM结构 2、
 * 如果零部件不带版本，则结构中导入A版本的零件 3、 如果文件中版本高于PDM系统中零件版本则不导入，并给出提示 4、
 * 如果车型BOM在系统中已经存在，并且已经发布，不存在未发布，则不允许导入 5、
 * 如果车型BOM在系统中已经存在，并且未发布，则允许导入，删除未发布结构后导入 
 * SS5 零部件比较结果存在错误。 liunan 2016-12-28
 * SS6 保存变更记录 liuyuzhu 2017-04-27
 * SS7 初始化工艺BOM 刘涛 2017-5-3
 * SS8 服务里的saveBom(Collection ytreenode)方法，需要有俩判断才能保存书签 liunan 2017-5-20
 * SS9 保存单独替换件的工艺BOM liuyuzhu 2017-05-19
 * SS10 开放获取当前用户工厂方法。 liunan 2017-5-24
 * SS11 修改列表数量带有小数点问题 liuyuzhu 2017-05-26
 * SS12 初始化BOM失败问题(服务平台A004-2017-3547) lis 2017-06-09
 * SS13 初始化BOM失败问题(服务平台A004-2017-3566) lis 2017-06-09
 * SS14 初始化BOM时候，工艺bom总成无法展开下一级件 刘家坤 2017-06-19
 * SS15 增加是否检查结构已存在的参数。做更改时，就是要生成未生效结构。 liunan 2017-6-26
 * SS16 创建零件失败后提示问题 (服务平台A004-2017-3561)2017-06-29
 * SS17 A004-2017-3584 获取路线不准问题 liunan 2017-6-29
 * SS18 A004-2017-3588 bom变更整个BOM都可以修改，不再只改当前一层结构。 liunan 2017-6-29
 * SS19 A004-2017-3585 导出BOM功能增加 层级和序号 liunan 2017-6-30
 * SS20 零部件拆分 (服务平台A004-2017-3562)2017-07-03
 * SS21 初始化bom之后，部分逻辑总成是成都创建的，但是在解放工艺bom里，也借用了成都的逻辑总成结构。此规则需要去掉。 (服务平台A004-2017-3578)2017-07-03
 * SS22 附件中有新旧规则对比。为用户提出新需求 lishu 20170707
 * SS23 1000410结构下需要提取1308G02，1313G02逻辑总成 lishu 20170707
 * SS24 拆分时遇到JTA开头的就去掉，不管在哪初始化 lishu 20170707
 * SS25 3725020-A01，8203010-E06C，7925040-92W，7901012EA01放到3900G01下；以3103070开头的放到3100G00下 lishu 20170707
 * SS26 修改原来规则 制造路线不含“总”，装配路线含“总”时，不提取子件 刘家坤 2017-07-10
 * SS27 原规则中判断不正确，不需要判断子件路线  刘家坤 2017-07-11
 * SS28 A004-2017-3594 发动机自动拆分功能不正确，系统原来可以自动进行拆分940自动生成尾号是 f00的 零部件，现在此功能没有了  lishu 20170713
 * SS29 添加版本"version"信息 liuyuzhu 2017-07-13
 * SS30 查询关联的书签 liuyuzhu 2017-07-20
 * SS31 J6P中1311G01，3406G01不放940里  lishu 20170724
 * SS32 3103070轮胎螺母没提取到整车里 （这个是新变更，以前这个件是放到3100G00下的，现在改到放到根下） lishu 20170724
 * SS33 5000990\2800010的处理暂时去掉
 * SS34 导出BOM方法 liuyuzhu 2017-07-25
 * SS35 创建零件是传递参数的分隔符改为“#@” liuyuzhu 2017-08-07
 * SS36 查找关联书签重复问题 liuyuzhu 2017-08-07
 * SS37 整车初始化工艺BOM问题修改
 * SS38 生成1000940合件时，创建指定的工艺路线--制造路线（总）”和“装配路线（总） liuyuzhu 2017-08-09
 * SS39 初始化整车替换固定规则的两个件 liuyuzhu 2017-08-16
 * SS40 修改结构比较方法 liuyuzhu 2017-08-22
 * SS41 整车初始化重做 lishu 2017-08-30
 * SS42 修改所有版本显示（改为技术中心版本） liuyuzhu 2017-08-24
 * SS43 整车初始化问题修改 lishu 2017-09-05
 * SS44 修改获取路线的方法，不取最新，而是获取所有未取消路线的合集。 liunan 2017-9-5
 * SS45 逻辑总成判断规则由原来的->改为-- 刘家坤 2017-09-5
 * SS46 修改设计BOM存储信息问题 liuyuzhu 2017-09-06
 * SS47 逻辑总成判断规则修改 liuyuzhu 2017-09-07
 * SS48 初始化整车时，利用规则将没有路线的逻辑总成创建上路线 liuyuzhu 2017-09-07
 * SS49 修改创建工艺零件方法（增加一个参数，为创建路线表做准备） liuyuzhu 2017-09-09
 * SS50 初始化时5001G01-13C与驾驶室里的5001G01BA03的前7位相同，不要将驾驶室里（饰-用）的逻辑总成放到整车里 liuyuzhu 2017-09-10
 * SS51 只有未生效与生效结构都存在的时候才可以做变更记录，否则不做 liuyuzhu 2017-09-11
 * SS52 工艺BOM列表的变更记录 liuyuzhu 2017-09-11
 * SS53 逻辑总成编号不要流水号 liuyuzhu 2017-09-12
 * SS54 修改存储路线（改为搜索广义部件路线） liuyuzhu 2017-09-12
 * SS55 整车初始化不需要编号后加“01” liuyuzhu 2017-09-12
 * SS56 拆分总成的调用 liuyuzhu 2017-09-12
 * SS57 解决空指针问题 liuyuzhu 2017-09-13
 * SS58 解决拆分总成报错问题 liuyuzhu 2017-09-16
 * SS59 保存替换件时未修改子件编号，导致排序有问题，现修改此问题 liuyuzhu 2017-09-16
 * SS60 3103070轮胎螺母的提取与保存 liuyuzhu 2017-09-16
 * SS61 工艺BOM导出列表去掉版本列，将版本和零件号进行合并 maxiaotong 2017-09-20
 * SS62 整车一级件集合规则修改 liuyuzhu 2017-09-20
 * SS63 整车件在创建“-FC”的同时也需要创建一个“-ZC”的件 liuyuzhu 2017-09-21
 * SS64 查询报空指针问题 liuyuzhu 2017-09-22
 * SS65 解决拖拽少件问题 liuyuzhu 2017-09-25
 * SS66 比较结构排序 liuyuzhu 2017-10-19
 * SS67 手动拆分总成的后缀为“-FC” 2017-10-19
 * SS68 设置BOM生效时，将数据库表的“bz1”字段添加上 liuyuzhu 2017-10-19
 * SS69 查询工艺BOM结构时，修改颜色判断条件 liuyuzhu 2017-10-19
 * SS70 所有对BOM的修改或调整都改变节点颜色 liuyuzhu 2017-10-24
 * SS71 替换件方法参数变化 liuyuzhu 2017-11-02
 * SS72 加锁零部件 liuyuzhu 2017-11-13
 * SS73 车架初始化规则修改 liuyuzhu 2017-12-11
 * SS74 驾驶室初始化规则修改 liuyuzhu 2017-12-13
 * SS75 成都要求工艺BOM显示顺序是导入顺序 刘家坤 2017-12-8
 * SS76 工艺BOM客户端导入功能 刘家坤 2017-12-8
 * SS77 设置BOM生效时，将数据库表的“bz1”字段添加上'green' 刘家坤 2017-12-17
 * SS78 查询路线时候，如果in语句里超过一千个值，则报错 刘家坤 2017-12-17
 * SS79 界面中保存列表操作修改，保存为变更数据 liuyuzhu 2017-12-20
 * SS80 校验工艺bom是否编辑路线并发布  刘家坤 2017-12-22
 * SS81 增加BOM变更顺序，按id进行排序  刘家坤 2017-12-25
 * SS82 导入程序，增加按发布源版本导入  刘家坤 2017-12-27
 * SS83 导出excell不该去掉版本的把版本给去掉了  2018-01-03
 * SS84 导入BOM需要判断无版本规则  2018-01-07
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
	 * Method：getServiceName
	 * <p>
	 * 获得服务名称
	 * 
	 * @return String 服务名称
	 */
	@Override
	public String getServiceName()
	{
		return "GYBomService";
	}

	/**
	 * 创建设计BOM关联
	 * 
	 * @param Collection
	 *            mtreenode 目标树（工艺BOM）节点集合 保存时要获取当前用户所在单位，然后直接通过数据库创建。
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
						if(arrStr[9].equals("red"))// 红色，该用户拖拽过
						{
							if(arrStr[10].indexOf(user.getBsoID())!=-1)// 包含该用户，保持原值
							{
								str[5]=arrStr[10];
							}
							else
							// 不包含该用户，增加该用户
							{
								str[5]=arrStr[10]+user.getBsoID();
							}
						}
						else
						// 没拖过，保持原值
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
	 * 创建设计BOM关联
	 * 
	 * @param Collection
	 *            ytreenode 源树（设计BOM）关联集合
	 * @param Collection
	 *            links 关联集合，每个元素是一个三维数组，分别是父件bsoID、子件bsoID、数量。
	 *            保存时要获取当前用户所在单位，然后直接通过数据库创建。
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
//					System.out.println("数组大小======="+yt.length);
					//CCBegin SS8
					String countIdSql="select count(*) from GYBomStructureLable where parentPart='"+yt[0]+"' and childPart='"+yt[1]+"' and carModelCode='"+yt[3]+"'";
					rs=st.executeQuery(countIdSql);
					rs.next();
					int countList=rs.getInt(1);
//					System.out.println("countIdSql==="+countIdSql);
//					System.out.println("countList==="+countList);
					//1、父件和子件都不存在的情况下用insert插入一条数据
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
						//2、父件和子件存在，但是用户为空的情况下，需要把用户改为当前用户 是否需要判断carModelCode，需要则增加carModelCode=yt[3]
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
//						System.out.println("插入一条数据！！！");
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
//					System.out.println("saveBom 跳过已存在结构："+exitsstr);
//				}
				//CCEnd SS8
			}
			// 关闭Statement
			st.close();
			//CCBegin SS8
			st1.close();
			rs.close();
			//CCEnd SS8
			// 关闭数据库连接
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
	 * 更新设计BOM关联
	 * 
	 * @param Collection
	 *            mtreenode 关联集合，每个元素是一个四维数组，分别是父件bsoID、子件bsoID、车型码、工厂。
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
						if(createUser.indexOf(userID)!=-1)// 包含该用户，保持原值
						{
							createUser=createUser;
						}
						else
						// 不包含该用户，增加该用户
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
	 * 保存设计BOM标签前先清空该整车的BOM
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
	 * 拖拽参照BOM树上的总成及零部件到工艺BOM树上等操作； 左右拖拽，参照bom树拖拽到目标bom树之后需要变图标处理
	 * 左右拖拽：参照BOM树下的零部件及结构调整到工艺BOM树下，连带bom都需要拖拽到工艺bom树节点之下（拖拽之后需要进行局部刷新并保存）
	 * 上下拖拽：工艺BOM树自身结构的调整，可以拖拽进行自身结构的调整，不需要改变图标 上下拖拽：目标树自身拖拽，再拖拽之后进行提交保存。
	 * 左右拖拽：addArr有值
	 * ；上下拖拽：updateArr有值；移除：deleteArr有值；部分移动：addArr和updateArr有值；创建和搜索添加：addArr有值
	 * 
	 * @param String
	 *            [] addArr 新增结构数组
	 *            原父件id,新父件id,子件id;子件id1;子件id2,数量;数量1;数量2,单位;单位1;单位2
	 * @param String
	 *            [] updateArr 更新结构数组
	 *            linkid;父件id;数量,linkid1;父件id1;数量1,linkid2;父件id2;数量2
	 * @param String
	 *            [] deleteArr 删除结构数组 linkid,linkid1,linkid2
	 * @param String
	 *            carModelCode 车型码
	 * @param String
	 *            dwbs 工厂 返回新增件json
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
						return "[{失败！父件结构出现循环！}]";
					}

					String[] mt=new String[]{addArr[1],subs[i],counts[i],carModelCode,dwbs,"0","","",units[i],
						//CCBegin SS70
//							part.getPartNumber()};
							part.getPartNumber(),"red"};
					//CCEnd SS70
					// mtree.add(mt);
					String linkid=saveGYBom(mt);
					// 创建不返回节点了，客户端统一做父节点刷新。
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

					// 工艺BOM已经初始化过，所有调整都以工艺BOM的为准
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
						// 获取carModelCode下的结构，如果有结构说明之前添加过结果，或添加该件又删除了，不再重复添加，使用原记录。
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
						return "[{失败！父件结构出现循环！}]";
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
				saveChangeContentUpdate(carModelCode, dwbs,mtree);//mtree中每个数组的元素为linkid、父件id、修改后的数量
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
				saveChangeContentDelete(carModelCode, dwbs,mdtree);//mdtree中每个数组的元素为linkid
				//CCEnd SS52
				deleteGYBom(mdtree);
			}
			long t2=System.currentTimeMillis();
//			System.out.println("saveTreeNode 用时： "+(t2-t1)+" 秒");
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
	 * 创建工艺BOM关联
	 * mtree:parentPart,childPart,quantity,carModelCode,dwbs,effectCurrent
	 * ,locker,lockDate,unit,childNumber
	 * 
	 * @param Collection
	 *            mtreenode 目标树（工艺BOM）关联集合
	 * @param Collection
	 *            links 关联集合，每个元素是一个三维数组，分别是父件bsoID、子件bsoID、数量。
	 *            保存时要获取当前用户所在单位，然后直接通过数据库创建。
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
					//System.out.println("语句======="+tempsql);
					st.addBatch(tempsql);
				}
				st.executeBatch();
//				if(!exitsstr.equals(""))
//				{
//					System.out.println("saveGYBom 跳过已存在结构："+exitsstr);
//				}
			}

			// 关闭Statement
			st.close();
			// 关闭数据库连接
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
	 * 生成工艺合件 发动机工艺合件 编号规则：1000940+AAAA+J+序号
	 * “AAAA”取自发动机零件号1000410+AAAA截取“1000410”之后的字符串。 “J”是固定的字符
	 * 序号为01、02、03按系统中存在的零件编号排序。
	 * 
	 * @param bsoID
	 *            1000410件的编号
	 * @return 根据1000410生成的工艺合件的part对象
	 */
	public QMPartIfc createGYPart(String num,Collection fdjparts, String dwbs) throws QMException//SS21
	{
//		System.out.println("createGYPart new   num=="+num);
		PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
		StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
		String AAAA=num.substring(7,num.length());
		// 根据给定的编号查询gybomstructure表，查询父件编号有没有已经存在的子件
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
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
		// 判断给定的父件编号的所有子件和根据规则生成的子件是否相同
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
		// 如果不同，新生成一个父件
		if(pid==null)
		{
			UserInfo user=getCurrentUserInfo();
			String lifeCyID=PublishHelper.getLifeCyID("无流程类零部件生命周期");

			String snum=getGYPartNums("1000940"+AAAA+"J");
			QMPartIfc part=new QMPartInfo();
			part.setPartNumber(snum);
			part.setPartName("发动机总成");
			part.setLifeCycleState(LifeCycleState.toLifeCycleState("PREPARING"));//SS37
			part.setViewName("工艺视图");
			part.setDefaultUnit(Unit.getUnitDefault());
			part.setProducedBy(ProducedBy.getProducedByDefault());
			part.setPartType("Assembly");
			part.setIterationCreator(user.getBsoID());
			part.setIterationModifier(user.getBsoID());
			part.setIterationNote("");
			part.setOwner("");
			part=(QMPartInfo)PublishHelper.assignFolder(part,"\\Root\\工艺合件");
			part.setLifeCycleTemplate(lifeCyID);
			part=sps.savePart(part);
			//CCBegin SS38
			TechnicsRouteListIfc trIfc = createRouteList(part);
			//
			createListRoutePartLink(trIfc ,part ,null);
			//CCEnd SS38
			return (QMPartIfc)ps.refreshInfo(part.getBsoID());
		}
		// 如果相同，返回已经存在的父件
		else
		{
			return (QMPartIfc)ps.refreshInfo(pid);
		}
	}
	//CCEnd SS7

	/**
	 * 创建工艺BOM关联
	 * 
	 * @param String
	 *            [] mt parentPart,childPart,quantity,carModelCode,dwbs,
	 *            effectCurrent,locker,lockDate,bz1
	 * @return String 关联id
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
//			System.out.println("sql语句====="+tempsql);
			st.executeQuery(tempsql);

			// 关闭Statement
			st.close();
			// 关闭数据库连接
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
	 * 更新工艺BOM关联
	 * 
	 * @param Collection
	 *            mtreenode 关联集合，每个元素是一个三维数组，分别是linkid、父件bsoID、数量。
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
					//CCBegin SS70 这里只改变了拖拽的节点颜色变了，还需要将节点原父件节点以及拖拽后父件节点变色
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
	 * 更改节点颜色
	 * 
	 * @param updatesql
	 *            sql语句
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
	 * 删除工艺BOM关联
	 * 
	 * @param Collection
	 *            mtreenode 目标树（工艺BOM）关联集合 值是id。 删除符合条件数据。
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
	 * 生成初始工艺BOM
	 */
	public void initGYBom(String partID,String gyID,String dwbs) throws QMException
	{
		try
		{
			// 发动机逻辑总成
			String partNumberRuleFDJ=RemoteProperty.getProperty("gybomlistfdj");
			// 整车
			String partNumberRuleZC=RemoteProperty.getProperty("gybomlistzc");
			// 车架逻辑总成
			String partNumberRuleCJ=RemoteProperty.getProperty("gybomlistcj");
			// 驾驶室逻辑总成
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

					// 发动机
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
					// 车架
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
					// 驾驶室
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
					// 整车
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
				// 发动机
				Iterator fdjiter1=fdjparts.iterator();
				String[] fdjstr=(String[])fdjiter1.next();
				QMPartIfc gyPartIfc=createGYPart(fdjstr[2]);//创建1000940
				// 创建整车与工艺合件关联
				String[] mt=new String[]{gyID,gyPartIfc.getBsoID(),"1",gypart.getPartNumber(),dwbs,"0","","","个",
					gyPartIfc.getPartNumber()};
				mtree.add(mt);
				// 创建工艺合件
				Iterator iter=fdjparts.iterator();
				QMPartIfc fdjsp=null;
				while(iter.hasNext())
				{
					String[] str1=(String[])iter.next();
					fdjsp=(QMPartIfc)ps.refreshInfo(str1[0]);
					String[] mt1=new String[]{gyPartIfc.getBsoID(),fdjsp.getBsoID(),str1[1],gypart.getPartNumber(),
						dwbs,"0","","","个",str1[2]};
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
				// 车架
				Iterator cjiter=cjparts.iterator();
				QMPartIfc cjsp=null;
				while(cjiter.hasNext())
				{
					String[] cjstr1=(String[])cjiter.next();
					cjsp=(QMPartIfc)ps.refreshInfo(cjstr1[0]);
					String[] mt1=new String[]{gyID,cjsp.getBsoID(),cjstr1[1],gypart.getPartNumber(),dwbs,"0","","","个",
						cjstr1[2]};
					mtree.add(mt1);
					mtree=getBomLinks(gyID,mtree,cjsp,cjsp.getPartNumber(),dwbs,linkvec,"cj",array,otherParts);//SS20//SS37
				}
				// 驾驶室
				Iterator jssiter=jssparts.iterator();
				QMPartIfc jsssp=null;
				while(jssiter.hasNext())
				{
					String[] jssstr1=(String[])jssiter.next();
					jsssp=(QMPartIfc)ps.refreshInfo(jssstr1[0]);
					String[] mt1=new String[]{gyID,jsssp.getBsoID(),jssstr1[1],gypart.getPartNumber(),dwbs,"0","","",
						"个",jssstr1[2]};
					mtree.add(mt1);
					mtree=getBomLinks(gyID,mtree,jsssp,jsssp.getPartNumber(),dwbs,linkvec,"jss",array,otherParts);//SS20//SS37
				}
				// 整车
				Iterator zciter=zcparts.iterator();
				QMPartIfc zcsp=null;
				while(zciter.hasNext())
				{
					String[] zcstr1=(String[])zciter.next();
					zcsp=(QMPartIfc)ps.refreshInfo(zcstr1[0]);
					String[] mt1=new String[]{gyID,zcsp.getBsoID(),zcstr1[1],gypart.getPartNumber(),dwbs,"0","","","个",
						zcstr1[2]};
					mtree.add(mt1);
					mtree=getBomLinks(gyID,mtree,zcsp,zcsp.getPartNumber(),dwbs,linkvec,"zc",array,otherParts);//SS20//SS37
				}
			}
			// 根据规则进行结构拆分
			Collection ntree=chaifengybom(gypart,mtree,dwbs, part,array);//SS20
			// 根据规则对符合规则的件进行改名（另存为）
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
	 * 删除指定零部件的一级结构。 初始化工艺BOM前先清空该整车的一级未生效BOM，导入BOM也先清除一级未生效BOM
	 * 
	 * @param parentPart
	 *            父件
	 * @param dwbs
	 *            工厂
	 * @param eff
	 *            1 生效结构， 0未生效结构
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
	 * 生成工艺合件 发动机工艺合件 编号规则：1000940+AAAA+J+序号
	 * “AAAA”取自发动机零件号1000410+AAAA截取“1000410”之后的字符串。 “J”是固定的字符
	 * 序号为01、02、03按系统中存在的零件编号排序。
	 * 
	 * @param bsoID
	 *            1000410件的编号
	 * @return 根据1000410生成的工艺合件的part对象
	 */
	public QMPartIfc createGYPart(String num) throws QMException
	{
//		System.out.println("createGYPart   num=="+num);
		PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
		StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
		UserInfo user=getCurrentUserInfo();
		String lifeCyID=PublishHelper.getLifeCyID("无流程类零部件生命周期");
		String AAAA=num.substring(7,num.length());
		String snum=getGYPartNums("1000940"+AAAA+"J");
		QMPartIfc part=new QMPartInfo();
		part.setPartNumber(snum);
		part.setPartName("发动机总成");
		part.setLifeCycleState(LifeCycleState.toLifeCycleState("PREPARING"));
		part.setViewName("工艺视图");
		part.setDefaultUnit(Unit.getUnitDefault());
		part.setProducedBy(ProducedBy.getProducedByDefault());
		part.setPartType("Assembly");
		part.setIterationCreator(user.getBsoID());
		part.setIterationModifier(user.getBsoID());
		part.setIterationNote("");
		part.setOwner("");
		part=(QMPartInfo)PublishHelper.assignFolder(part,"\\Root\\工艺合件");
		part.setLifeCycleTemplate(lifeCyID);
		part=sps.savePart(part);
		return (QMPartIfc)ps.refreshInfo(part.getBsoID());
	}

	/**
	 * 根据工艺合件编号前缀，获取序号，即第几个工艺合件，顺序号为01、02、03...
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

			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 根据逻辑总成编号前缀，获取序号，顺序号为001、002、003...
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

			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 返回指定零部件及其一级子件节点集合 用于添加工艺bom到目标树。 先取未生效结构，没有则取生效结构。
	 * 
	 * @param id
	 *            零部件bsoID
	 * @param carModelCode
	 *            车型码
	 * @param dwbs
	 *            单位
	 * @return 零部件及其一级子件的树结构
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
			// 设置根节点
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
			// 获取标签
			Connection conn=null;
			Statement stmt=null;
			ResultSet rs=null;
			try
			{
				conn=PersistUtil.getConnection();
				stmt=conn.createStatement();
				// 如果系统中存在未生效的BOM则打开未生效的bom
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
//				System.out.println("获取未生效结构："+sql);
				rs=stmt.executeQuery(sql);

				String insql="";
				while(rs.next())
				{
					String childPart=rs.getString(1);
					part=(QMPartIfc)ps.refreshInfo(childPart);
					if(part==null)
					{
						System.out.println("未找到子件id："+childPart);
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
					if(dwbs.equals("W34"))// 成都取二级路线
					{
						map=getSecondRoute(insql,map);
					}
					else
					// 解放路线
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
						System.out.println("未找到子件id："+childPart);
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
							if(dwbs.equals("W34"))// 成都取二级路线
							{
								map11=getSecondRoute(sqlstr,map11);
							}
							else
							// 解放路线
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
					//节点新增两个属性，是否本人加锁
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
//				System.out.println("是否有生效结构："+flag);
				// 如果系统中不存在未生效的BOM则打开生效的bom
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
//					System.out.println("获取生效结构："+sql);
					rs=stmt.executeQuery(sql);

					insql="";
					while(rs.next())
					{
						String childPart=rs.getString(1);
						part=(QMPartIfc)ps.refreshInfo(childPart);
						if(part==null)
						{
							System.out.println("未找到子件id："+childPart);
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
							System.out.println("未找到子件id："+childPart);
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

				// 清空并关闭sql返回数据
				rs.close();
				// 关闭Statement
				stmt.close();
				// 关闭数据库连接
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
	 * 返回指定零部件的一级子件节点集合，用于显示工艺BOM零部件列表。 先取未生效结构，没有则取生效结构。
	 * 
	 * @param id
	 *            零部件bsoID
	 * @param carModelCode
	 *            车型码
	 * @param dwbs
	 *            单位
	 * @return 零部件及其一级子件的树结构
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
			// 设置根节点
			JSONObject jo=new JSONObject();

			// 获取标签
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
				// 如果系统中存在未生效的BOM则打开未生效的bom
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
						System.out.println("未找到子件id："+childPart);
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
						System.out.println("未找到子件id："+childPart);
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
					//节点新增两个属性，是否本人加锁
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
				// 如果系统中不存在未生效的BOM则打开生效的bom
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
							System.out.println("未找到子件id："+childPart);
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
							System.out.println("未找到子件id："+childPart);
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
				// 如果都没有，则返回什么都不返回，初始化json，去掉根节点。
				if(flag)
				{
					json=new JSONArray();
				}

				// 清空并关闭sql返回数据
				rs.close();
				// 关闭Statement
				stmt.close();
				// 关闭数据库连接
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
	 * 创建零件
	 * pid+partNumbe+partName+selectst+selectdw+selectlx+selectly+selectsmzq
	 * +selectzlj+carModelCode+dwbs 父件号+编号+名称+视图+单位+类型+来源+生命周期+资料夹+车型码+工厂
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
			part.setLifeCycleState(LifeCycleState.toLifeCycleState("PREPARING"));// 设置生产准备状态
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
				// 设置根节点
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
//				String[] mt=new String[]{str[0],part.getBsoID(),"1",str[9],str[10],"0","","","个",part.getPartNumber()};
				String[] mt=new String[]{str[0],part.getBsoID(),"1",str[9],str[10],"0","","","个",part.getPartNumber(),"red"};
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
				jo.put("dw","个");
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
	 * 设置工艺bom生效 搜索指定零部件的整个结构，得到未生效结构 的父件集合。 删除 父件集合中 所有生效结构。 
	 * 将 父件集合中所有未生效结构改为生效。
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

			// 先搜零部件下未生效结构的父件
			Vector parts=new Vector();
			parts=getUnEffParts(id,dwbs,parts);
//			System.out.println("得到未生效父件："+parts.size());
			for(int i=0;i<parts.size();i++)
			{
				String str=parts.elementAt(i).toString();
//				System.out.println("未生效父件："+str);
				// 删除生效结构。
				// deleteGYBomLevel(str, dwbs, "1");
				stmt.executeQuery("delete GYBomStructure where effectCurrent='1' and parentPart='"+str+"' and dwbs='"
					+dwbs+"'");
				// 将未生效改为生效。
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
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 返回指定零部件及其一级子件节点集合 用于添加零部件设计bom到源树，根据flag决定从临时表中获取还是获取最新结构。
	 * 
	 * @param id
	 *            零部件bsoID
	 * @param flag
	 *            是否获取设计标签树
	 * @param carModelCode
	 *            车型码
	 * @param dwbs
	 *            单位
	 * @return 零部件及其一级子件的树结构
	 */
	public String getDesignBom(String id,String flag,String carModelCode,String dwbs) throws QMException
	{
//		System.out.println("come in getDesignBom   id=="+id+"  flag=="+flag+"  carModelCode=="+carModelCode+"  dwbs=="
//			+dwbs);
		// flag = "true";
		// carModelCode = "B143H26561T9K01";
		// dwbs = "成都工厂";
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
			// 设置根节点
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

			// 获取标签
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
							System.out.println("未找到子件id："+childPart);
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
							System.out.println("未找到子件id："+childPart);
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
//						System.out.println("数量3======"+rs.getString(2));
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
						if(colors==null||colors.equals(""))// 没有用户拖拽记录，黑色
						{// System.out.println("111");
							jo.put("color","black");
						}
						else
						{
							if(colors.indexOf(user.getBsoID())!=-1)// 当前用户拖拽过，红色
							{// System.out.println("222");
								jo.put("color","red");
							}
							else
							// 当前用户没有拖拽过，而其他人拖拽过，黄色
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
					// 清空并关闭sql返回数据
					rs.close();
					// 关闭Statement
					stmt.close();
					// 关闭数据库连接
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
			if(nosub)// 获取设计bom
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
							// System.out.println("数量4======"+String.valueOf(link.getQuantity()));
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
					System.out.println("未找到子件id："+childPart);
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
					System.out.println("未找到子件id："+childPart);
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
				if(colors==null||colors.equals(""))// 没有用户拖拽记录，黑色
				{
					jo.put("color","black");
				}
				else
				{
					if(colors.indexOf(userID)!=-1)// 当前用户拖拽过，红色
					{
						jo.put("color","red");
					}
					else
					// 当前用户没有拖拽过，而其他人拖拽过，黄色
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
			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 返回指定零部件的一级子件节点集合，用于显示设计BOM零部件列表。
	 * 
	 * @param id
	 *            零部件bsoID
	 * @param flag
	 *            是否获取设计标签树
	 * @param carModelCode
	 *            车型码
	 * @param dwbs
	 *            单位
	 * @return 零部件及其一级子件的树结构
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
			// 设置根节点
			JSONObject jo=new JSONObject();

			Collection ytree=new ArrayList();
			Collection route=new ArrayList();
			String zhizao="";
			String zhuangpei="";
			String colors="";

			// 获取标签
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
							System.out.println("未找到子件id："+childPart);
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
							System.out.println("未找到子件id："+childPart);
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
//						System.out.println("数量1======"+rs.getString(2));
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
						if(colors==null||colors.equals(""))// 没有用户拖拽记录，黑色
						{
							jo.put("color","black");
						}
						else
						{
							if(colors.indexOf(user.getBsoID())!=-1)// 当前用户拖拽过，红色
							{
								jo.put("color","red");
							}
							else
							// 当前用户没有拖拽过，而其他人拖拽过，黄色
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
					// 清空并关闭sql返回数据
					rs.close();
					// 关闭Statement
					stmt.close();
					// 关闭数据库连接
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
			if(nosub)// 获取设计bom
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
//								System.out.println("制造路线=="+zhizao+"==装配路线=="+zhuangpei+"==工厂=="+dwbs);
//							}
							// System.out.println("数量2======"+String.valueOf(link.getQuantity()));
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
	 * 用例（搜索设计bom） 根据编号和名称搜索零部件
	 * 
	 * @param name
	 *            零部件名称
	 * @param number1
	 *            零部件编号
	 * @param ignoreCase
	 *            忽略大小写
	 * @return 符合条件的零部件集合
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
				        //System.out.println("跳过");
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
	 * 通过名称和号码查找主零部件。允许模糊查询。 如果name为null，按号码查询；如果number为null，按名称查询；
	 * 如果name和numnber都为null，获得所有主零部件的值对象。
	 * 
	 * @param name
	 *            :String 模糊查询的零部件名称。
	 * @param number
	 *            :String 模糊查询的零部件的号码。
	 * @return collection:Collection 符合查询条件的主零部件的值对象的集合。
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
	 * 结构比较 获取设计BOM结构，工艺BOM结构，比较零部件区别，数量区别。
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
			// 获取设计BOM结构
			Vector vec=psh.getBOMList(desginID,"partNumber-0,partName-0,quantity-0,","","");
			long t2=System.currentTimeMillis();
//			System.out.println("CompartTreeResult  getBOMList  用时： "+(t2-t1)+" 毫秒");
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
//			System.out.println("CompartTreeResult  designMap  用时： "+(t3-t2)+" 毫秒");

			// 获取工艺BOM
			HashMap gymap=new HashMap();
			gymap=bianli(gyID,carModelCode,dwbs,1L,gymap);

			long t4=System.currentTimeMillis();
//			System.out.println("CompartTreeResult  bianli  用时： "+(t4-t3)+" 毫秒");

//			System.out.println("gymap:"+gymap.size());
			Set gySet=gymap.keySet();
			Iterator gyiter=gySet.iterator();
			String gynum="";
			while(gyiter.hasNext())
			{
				gynum=(String)gyiter.next();
				String[] str=(String[])gymap.get(gynum);
				// System.out.println("编号:"+str[1]+"    gy数量："+str[2]+"    design数量："+designMap.get(str[1]));
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
				// System.out.println("design 剩余："+(String)iter.next());
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
//			System.out.println("CompartTreeResult  json  用时： "+(t5-t4)+" 毫秒");

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

			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 获取源零部件结构关联集合
	 */
	public Collection getBomLinks(Collection mtree,QMPartIfc part,String carModelCode,String dwbs,Vector linkvec)
		throws QMException
	{
		try
		{
			if(part!=null)
			{
				// 检查part是否存在当前工厂的结构，存在则不需要处理结构。
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
					// 一个关联 parentPart,childPart,quantity,carModelCode,dwbs
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
									System.out.println("当前结构中重复，可以重复添加："
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
	 * 加锁
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
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
			conn.close();
			s="成功";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			s="失败";
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
	 * 解锁
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
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
			conn.close();
			s="成功";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			s="失败";
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
	 * 检验当前车型码是否锁定
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

			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 根据车型码和工厂，判断是否生效 返回0 没有未生效结构 没有生效结构 返回1 有未生效结构 没有生效结构 返回2 没有未生效结构 有生效结构
	 * 返回3 有未生效结构 有生效结构
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

			boolean flag0=false;// 未生效结构存在标识 false 没有未生效结构 true 有未生效结构
			boolean flag1=false;// 生效结构存在标识 false 没有生效结构 true 有生效结构
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
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 获取解放路线
	 * 通过sql获取最新 非废弃 主要路线
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

			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 获取解放路线
	 * 通过sql获取最新 非废弃 主要路线
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
	 * 获取二级路线路线（成都）
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

				// 清空并关闭sql返回数据
				rs.close();
				
			}
			stmt.close();
			// 关闭数据库连接
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
	 * 检查添加零部件是否存在结构循环。 检查gybom的结构，获取childID的所有子件id，看是否包含parentID，包含则说明存在循环。
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
     * 获取新零件编号
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
//            // 清空并关闭sql返回数据
//            rs.close();
//            // 关闭Statement
//            stmt.close();
//            // 关闭数据库连接
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
	 * 检查是否存在给定零部件的生效结构。 检查gybom的结构，当前厂家是否存在生效的父件是parentID的结构。
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

			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 检查添加零部件是否存在结构循环。 检查gybom的结构，获取childID的所有子件id，看是否包含parentID，包含则说明存在循环。
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

			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 根据关联id获取子件id
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

			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 另存为工艺BOM。 String yid 选中零部件。 String mid 目标零部件。 String dwbs 选中零部件工厂
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
				return "[{"+mpart.getPartNumber()+"已有未生效工艺BOM！}]";
			}

			rs=stmt.executeQuery("select count(*) from GYBomStructure where effectCurrent='1' and parentPart='"+yid
				+"' and dwbs='"+ydwbs+"'");
			rs.next();
			if(rs.getInt(1)>0)
			{
//				System.out.println("获取生效结构");
				rs=stmt
					.executeQuery("select parentPart,childPart,quantity,unit,childNumber from GYBomStructure where effectCurrent='1' and parentPart='"
						+yid+"' and dwbs='"+ydwbs+"'");
			}
			else
			{
//				System.out.println("获取未生效结构");
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

			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 设置变更，为一个生效的工艺BOM克隆一套未生效BOM结构 首先检查整车是否设置未生效。 检查指定节点是否未生效
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

			// 先检查指定车型是否有生效工艺BOM
			String countIdSql="select count(*) from GYBomStructure where effectCurrent='1' and parentPart='"+id
				+"' and dwbs='"+dwbs+"'";
			rs=stmt.executeQuery(countIdSql);
			rs.next();
			int countList=rs.getInt(1);
			if(countList==0)
			{
				return "[{"+carModelCode+"没有生效工艺BOM，无法进行变更！}]";
			}

			// 再检查指定车型是否有未生效工艺BOM
			countIdSql="select count(*) from GYBomStructure where effectCurrent='0' and parentPart='"+id+"' and dwbs='"
				+dwbs+"'";
			rs=stmt.executeQuery(countIdSql);
			rs.next();
			countList=rs.getInt(1);
			if(countList>0)
			{
				return "[{"+carModelCode+"的工艺BOM已执行过变更！}]";
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

			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 递归处理，为生效的工艺BOM克隆一套未生效BOM结构
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

			// 先检查指定零部件是否有生效工艺BOM
			String countIdSql="select count(*) from GYBomStructure where effectCurrent='1' and parentPart='"+id +"' and dwbs='"+dwbs+"'";
			rs=stmt.executeQuery(countIdSql);
			rs.next();
			int countList=rs.getInt(1);
			if(countList==0)
			{
				return mtree;
			}

			// 再检查指定零部件是否有未生效工艺BOM
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
			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 批量修改工艺BOM，根据修改规则集合，把要修改的车型，进行调整 可能造成其他整车结构孤儿件。
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
				System.out.println("[{该整车第一次采用，没有历史结构！}]");
				return;
			}
			if(map.size()==0)
			{
				System.out.println("[{该整车没有比较出结构变化！}]");
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
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
			conn.close();
			// 清空并关闭sql返回数据
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
	 * 批量修改工艺BOM，根据修改规则集合，把要修改的车型，进行调整 只需要修改一级件结构
	 * 
	 * @param String
	 *            id 替换车型的id
	 * @param HashMap
	 *            map 替换内容集合
	 * @param String
	 *            carModelCode 替换车型
	 * @param String
	 *            dwbs 工厂
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
				// 检查
				// parentPart,childPart,quantity,carModelCode,dwbs,unit,childNumber
				countIdSql="select count(*) from GYBomStructure where effectCurrent='1' and parentPart='"+str[0]
					+"' and childPart='"+str[1]+"' and quantity='"+str[2]+"' and dwbs='"+dwbs+"' and unit='"+str[3]
					+"' and childNumber='"+str[4]+"'";
				rs=st.executeQuery(countIdSql);
				rs.next();
				int countList=rs.getInt(1);
//				System.out.println("检查advec:"+carModelCode+" and "+str[4]+" 'count="+countList);
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
	 * 检查一个工艺BOM的生效结构和未生效结构的差别。 得到删除的结构集合、新增的结构集合、更新的结构集合
	 * 上述3个集合中的父件就是此次变更涉及的所有零部件。
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
			// 第一次发布，没有历史生效内容
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

			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
			conn.close();

			// 比较oldmap和newmap
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
					if(newmap.get(oldkey).equals(oldmap.get(oldkey)))// 数量相等，是保持不变
					{
						// 不做处理
					}
					else
					// 数量不等，是更新
					{
						String[] a=((String)oldmap.get(oldkey)).split(",");
						String[] b=((String)newmap.get(oldkey)).split(",");
						// {"1","2","3"}和{"1","2","1","2"}比较处理后，结果就是{"","","3"}和{"","","1","2"}
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
							if(!a[i].equals(""))// 有值，表示旧集合已删除。
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
							if(!b[i].equals(""))// 有值，表示新集合新增。
							{
								// CCBegin SS5
								/*
								 * addvec.add(new
								 * String[]{str[0],str[1],b[i],str[2],str[3]});
								 * /
								 * /增加的不需要往变更集合里加，因为当前基础车型增加的结构，不应该通过搜索在其他车型结构中得到
								 * 。 if(!bgvec.contains(str[0])) {
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
				// 新集合不含有旧集合内容，表示旧集合已删除。
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
				newkey=(String)newiter.next();// 旧集合不含有新集合内容，表示新集合新增。
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
	 * 获取当前用户所在的工厂。
	 * @return 工厂
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
		CodingIfc coding=cmservice.findCodingByContent(com,"工厂代码","代码分类");
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
	 * 保存批量替换车型表。 id 典型车型bsoid,车型码,工厂,替换车型1,替换车型2,替换车型3...
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
				return "[{参数传递错误！}]";
			}
			if(s.length<4)
			{
				return "[{未得到替换车型的编号信息，请检查后重新录入！}]";
			}

			String unpart="";

			StandardPartService spService=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
			VersionControlService vcService=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();

			conn=PersistUtil.getConnection();
			st=conn.createStatement();

			// 支持重新导入
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
//						System.out.println("没有找到零部件对象！！"+rs.getString(1)+"=="+rs.getString(2));
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

			// 如果有搜索不到的零部件，则提示重新添加。
			if(!unpart.equals(""))
			{
				st.close();
				conn.close();
				rs.close();
				return "[{系统中未找到零部件"+unpart+"，请检查后重新录入！}]";
			}

			st.executeBatch();
			// 关闭Statement
			st.close();
			// 关闭数据库连接
			conn.close();
			str="[{成功}]";
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
	 * 保存批量全部替换车型表。 id 车型id
	 */
	public String saveAllBatchUpdateCM(String id) throws QMException
	{
//		System.out.println("come in saveAllBatchUpdateCM");
		if(id==null||id.equals(""))
		{
			return "[{参数传递错误！}]";
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
				return "[{参数传递错误，无法搜到零部件！}]";
			}
			String carModelCode=part.getPartNumber();

			conn=PersistUtil.getConnection();
			st=conn.createStatement();
			// CCBegin SS5
			st1=conn.createStatement();
			// CCEnd SS5

			// 支持重新导入
			st.executeQuery("delete batchupdateCM where yPart='"+id+"' and dwbs='"+dwbs+"'");

			String ytsql="INSERT INTO batchupdateCM (yPart,assisPart,fbdID,createTime,modifyTime,dwbs) VALUES ";
			String tempsql="";
			String sendData=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

			String insql="";
			HashMap map=getGYBomDifferent(carModelCode,dwbs);
			if(map==null)
			{
				return "[{该整车第一次采用，没有历史结构！}]";
			}
			if(map.size()==0)
			{
				return "[{该整车没有比较出结构变化！}]";
			}
			Vector bgvec=(Vector)map.get("bgvec");
			if(bgvec==null)
			{
				return "[{该整车没有比较出结构变化！！}]";
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
			 * System.out.println("没有找到零部件对象-->"+rs.getString(1)); continue; }
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
							System.out.println("没有找到零部件对象-->"+rs.getString(1));
							continue;
						}
						// 如果返回车型是当前基本车型，则不处理。（SS5调整后应该不会走到这里）
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
			// 关闭Statement
			st.close();
			// 关闭数据库连接
			conn.close();
			str="[{成功}]";
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
	 * 获取批量替换车型表。
	 */
	public String getBatchUpdateCM(String id,String carModelCode,String dwbs) throws QMException
	{
//		System.out.println("come in getBatchUpdateCM   id=="+id+"  carModelCode=="+carModelCode+"  dwbs=="+dwbs);
		JSONArray json=new JSONArray();
		try
		{
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part=null;
			// 设置根节点
			JSONObject jo=new JSONObject();

			// 获取标签
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

				// 清空并关闭sql返回数据
				rs.close();
				// 关闭Statement
				stmt.close();
				// 关闭数据库连接
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
	 * 降版本 将工艺bom上节点版本降低一个版本，例如原来版本是“D”，选择降版本之后变为“C”版本
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
				return "[{当前子件是A版，不能进行降版本！}]";
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
				return "[{未能找到当前子件上一版本！}]";
			}

			// 更新关联，子件id替换成上一版零部件id
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
System.out.println("sql语句----update GYBomStructure set childPart='"+partIfc.getBsoID()+ "',bz1='" + "red"
					+"' where effectCurrent='0' and childPart='"+part.getBsoID()+"' and dwbs='"+dwbs+"'");
				// 关闭Statement
				stmt.close();
				// 关闭数据库连接
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

			// 删除当前版本子件关联
			Collection ids=new ArrayList();
			ids=bianliGYBomid(ids,part.getBsoID(),carModelCode,dwbs);
			deleteGYBom(ids);

			// 新增上一版本子件关联
			Collection mtree=new ArrayList();
			Vector linkvec=new Vector();
			mtree=getBomLinks(mtree,partIfc,carModelCode,dwbs,linkvec);
			//CCBegin SS15
			saveGYBom(mtree, true, dwbs);//SS21
			//CCEnd SS15

			// 获取当前零部件及其子件最新结构
			str=getGYBomAll(linkid,partIfc.getBsoID(),carModelCode,dwbs);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 返回指定零部件及其一级子件节点集合 用于添加工艺bom到目标树。
	 * 
	 * @param linkid
	 *            id
	 * @param id
	 *            零部件bsoID
	 * @param carModelCode
	 *            车型码
	 * @param dwbs
	 *            单位
	 * @return 零部件及其一级子件的树结构
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

			// 获取标签
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
				// 设置根节点
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

				// 如果系统中存在未生效的BOM则打开未生效的bom
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
						System.out.println("未找到子件id："+childPart);
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
						System.out.println("未找到子件id："+childPart);
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

				// 清空并关闭sql返回数据
				rs.close();
				// 关闭Statement
				stmt.close();
				// 关闭数据库连接
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
	 * 递归获取未生效结构的id集合
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

			// 关闭ResultSet
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 递归获取零部件结构中，未生效结构的父件集合
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

			// 没有未生效结构，则获取生效结构 继续查找。
			if(i==0)
			{
				rs=stmt.executeQuery("select childPart from GYBomStructure where parentPart='"+id
					+"' and effectCurrent='1' and dwbs='"+dwbs+"'");
				while(rs.next())
				{
					vec=getUnEffParts(rs.getString(1),dwbs,vec);
				}
			}

			// 关闭ResultSet
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 拆分 点击需要拆分的逻辑总成，在同级下自动生成一个逻辑总成号 编号规则：原逻辑总成号+F+3位流水。
	 * 
	 * @parm String linkid 关联id（linkid）
	 * @parm String carModelCode 车型码
	 * @parm String dwbs 工厂
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
//					+part.getPartType()+","+part.getProducedBy()+",无流程类零部件生命周期,"+part.getLocation()+","+rs.getString(3)
//					+","+rs.getString(4);
				str=pid+"#@"+partNumber+"#@"+partName+"#@"+part.getViewName()+"#@"+part.getDefaultUnit()+"#@"
						+part.getPartType()+"#@"+part.getProducedBy()+"#@无流程类零部件生命周期#@"+part.getLocation()+"#@"+rs.getString(3)
						+"#@"+rs.getString(4);
				//CCEnd SS58
			}

			stmt.executeQuery("update GYBomStructure set bz1='red' where id='"+linkid+"'");

			// 关闭ResultSet
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 整车一级件清单，调用逻辑总成报表方法得到结果后处理返回。 * 零件提取规则
	 * 1、层级由低向高逐级提取装配路线为“总”的零部件，不包含装置图、逻辑总成； 2、装配图零件号：第5-7为001，2，3，5等；
	 * 3、逻辑总成零件号：第5位为“G”，制造路线包含“总”； 4、当零件号第5位为“G”，制造路线包含“总”时，取下一层级装配路线包含”总“的子件；
	 * 5、制造路线不含“总”，装配路线含“总”时，不提取子件，提取同一层级装配路线含“总”的件；
	 * 6、制造路线包含”总“，装配路线也包含“总”时，不提取子件，提取同一层级装配路线含“总”的件； 7、子组号取逻辑总成前4位； 第一次调试补充规则
	 * 8、增加对“物料描述”包含1000410的零件提取，并直接将子组号赋值为“1000”；
	 * 9、对1000410子件中父逻辑总成制造路线含“总”，子件装配路线含“总”的零部件进行提取；
	 * 10、增加"物料描述"包含"车轮螺母",装配路线包含"总"的子件；
	 * 11、增加导出表中表头参数输出顺序，即：物料号、物料描述、子组号、数量、制造路线、装配路线、生命周期状态、艺准编号、上一级父件编号；
	 * 第二次调试补充规则 12、父件逻辑总成制造路线含“总”，子件装配路线含“总”的件提取I（不考虑该零件号第5位是否为“G”）；
	 * 13、父件逻辑总成中制造路线不含“总”时，不提取子件；
	 * 14、增加对“物料描述”包含“弹簧制动缸逻辑总成”的零件提取，并直接将子组号赋值为“2500”；
	 * 15、增加对“物料描述”包含“点烟器头总成、烟灰盒总成、随车工具灯、三角警告牌总成”等零件的提取，并直接将子组号赋值为“3900”；
	 * 16、增加对“物料号”包含“5000010”的零件提取，并直接将子组号赋值为“5000”。 第三次调试补充规则
	 * 17、2400010、2500010、3722024、3902G00直接提取
	 */
	public Vector getExportFirstLeveList(String id,String carModelCode,String dwbs) throws QMException
	{
		Vector vec=new Vector();
		HashMap routemap=new HashMap();
		bianliFirstLeveList(vec,id,carModelCode,dwbs,1,"",routemap);
		System.out.println("开始导出vec=="+vec.size());
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
		System.out.println("xin 导出程序");
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
		// 获取子件id和数量 findChildPart(String id, String carModelCode, String dwbs,
		// String effect)
		Vector subvec=findChildPart(id,carModelCode,dwbs,effect);
		QMPartIfc parentpart=(QMPartIfc)ps.refreshInfo(id);

		// 获取子件路线
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
				System.out.println("2层"+part.getPartNumber());
			}
			if(part==null)
			{
				System.out.println("未找到子件id："+childPart);
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

		// 递归获取子件

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

			// 如果是逻辑总成，并且制造路线含有“总”，则找子件
			if(isGpart(part.getPartNumber())&&zhizao.indexOf("总")!=-1)
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
				else if(part.getPartName().indexOf("点烟器头总成")!=-1||part.getPartName().indexOf("烟灰盒总成")!=-1
					||part.getPartName().indexOf("随车工具灯")!=-1)
				{
					vec.add(new String[]{Integer.toString(level),part.getPartNumber(),part.getPartName(),"3900",str[1],
						zhizao,zhuangpei,parentpart.getPartNumber()});
				}
				// 如果父件是逻辑总成，并且当前件装配含有“总”，则加入集合
				else if(isGpart(parentpart.getPartNumber())&&zhuangpei.indexOf("总")!=-1)
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
	 * 获取子件和数量
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
//			System.out.println("查找下级子件==="+"select childPart,quantity from GYBomStructure where effectCurrent='"+effect
//					+"' and parentPart='"+id+"' and dwbs='"+dwbs+"' order by childNumber");
			while(rs.next())
			{
				vec.add(rs.getString(1)+","+rs.getString(2));
			}
			// 关闭ResultSet
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 添加备品 将选中零部件子件添加到指定零部件下。 返回
	 * 
	 * @parm String parentID 指定零部件
	 * @parm String beipinID 选中零部件
	 * @parm String carModelCode 车型码
	 * @parm String dwbs 工厂
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
	 * 获得当前用户
	 * 
	 * @throws QMException
	 */
	public UserInfo getCurrentUserInfo() throws QMException
	{
		SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
		return (UserInfo)sservice.getCurUserInfo();
	}

	/**
	 * 工艺bom历史数据导入方法 AddUsageLink C01AM44141BF204 CQ1511065 ea 8 AddUsageLink
	 * C01AM44141BF204 Q1841240F6 ea 8 标记 父件 子件 单位 数量
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

			String excelPath=RemoteProperty.getProperty("com.faw_qm.loadFiles")+File.separator+"content"+File.separator;// Excel的存放路径
			logbuffer(buffer,"开始导入工艺BOM文件。\n");
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
					// 父件、删除结构都是一车一算。
					Collection mtree=new ArrayList();
					Vector parentParts=new Vector();
					Vector deleteParts=new Vector();

					File file1=new File(f.getPath()+File.separator+files[i]);
					if(file1.getName().endsWith(".xls"))
					{
						logbuffer(buffer,"检查导入文件："+file1.getName()+"。\n");
						String carModelCode=file1.getName().trim().substring(0,file1.getName().trim().lastIndexOf("."));

						if(!partmap.containsKey(carModelCode))
						{
							partmap.put(carModelCode,findPartByNumber(carModelCode));
						}
						QMPartIfc carpart=(QMPartIfc)partmap.get(carModelCode);
						if(carpart==null)
						{
							logbuffer(buffer,"整车"+carModelCode+"系统无此件，无法导入。\n");
							continue;
						}

						FileInputStream inputStream=new FileInputStream(file1);
						POIFSFileSystem fs=new POIFSFileSystem(inputStream);
						HSSFWorkbook workBook=new HSSFWorkbook(fs);
						HSSFSheet sheet=workBook.getSheetAt(0);
						// # 父件编号 子件编号 单位 使用数量
						// AddUsageLink C01AM44141BF204 CQ1511065 ea 8
						// 检查行数，如果大于1行才继续
						int lrn=sheet.getLastRowNum();
						if(lrn<2)
						{
							logbuffer(buffer,"获取到"+lrn+"行数据，无法导入，不再继续。\n");
							continue;
						}
						// 获取第一行和第二行第一列内容用于校验是否是导入文件。
						String bj1=String.valueOf(sheet.getRow(0).getCell((short)0)).trim();
						String bj2=String.valueOf(sheet.getRow(1).getCell((short)0)).trim();
						if(bj1.equals("#")&&bj2.equals("AddUsageLink"))
						{
							for(int j=1;j<=lrn;j++)
							{
								HSSFRow row=sheet.getRow(j);
								String pnum=String.valueOf(row.getCell((short)1)).trim();// 父件编号
								String snum=String.valueOf(row.getCell((short)2)).trim();// 子件编号
								String unit=String.valueOf(row.getCell((short)3)).trim();// 单位
								String quantity=String.valueOf(row.getCell((short)4)).trim();// 使用数量
								String subGroup=String.valueOf(row.getCell((short)5)).trim();// 子组
								if(subGroup==null)
								{
									subGroup="";
								}
								// 检查4个属性，如果都是空，则不做导入也无需记录。
								if((pnum==null||pnum.equals(""))&&(snum==null||snum.equals(""))
									&&(unit==null||unit.equals(""))&&(quantity==null||quantity.equals("")))
								{
									// System.out.println("第"+j+"行没有导入数据，跳过！");
									continue;
								}
								// 检查4个属性是否为空，如果为空则记录日志，不做导入。
								if(pnum==null||pnum.equals("")||snum==null||snum.equals("")||unit==null
									||unit.equals("")||quantity==null||quantity.equals(""))
								{
									logbuffer(buffer,"第"+j+"行");
									if(pnum==null||pnum.equals(""))
									{
										logbuffer(buffer," 父件编号");
									}
									if(snum==null||snum.equals(""))
									{
										logbuffer(buffer," 子件编号");
									}
									if(unit==null||unit.equals(""))
									{
										logbuffer(buffer," 单位");
									}
									if(quantity==null||quantity.equals(""))
									{
										logbuffer(buffer," 使用数量");
									}
									logbuffer(buffer," 属性没有整理\n");
									continue;
								}

								// 如果父件已存在生效结构，则跳过此行。
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

								// 根据编号获取父子零部件
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
								// 检查零部件是否存在，如果不存在则记录日志，不做导入。
								if(ppart==null||spart==null)
								{
									logbuffer(buffer,"第"+j+"行");
									if(ppart==null)
									{
										logbuffer(buffer," 父件"+pnum);
									}
									if(spart==null)
									{
										logbuffer(buffer," 子件"+snum);
									}
									logbuffer(buffer," 系统无此件\n");
									continue;
								}

								if(!unitmap.containsKey(unit))
								{
									Unit unitT=Unit.toUnit(unit);
									unitmap.put(unit,unitT.getDisplay());
								}

								// 组织导入数据集合
								// parentPart,childPart,quantity,carModelCode,dwbs,effectCurrent,locker,lockDate,unit,childNumber
								// String[] mt = new
								// String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,"1","","",(String)unitmap.get(unit),spart.getPartNumber(),subGroup};
								// System.out.println("======----====="+Arrays.toString(mt));
								if(isupdate.equals("true"))
								{
									// 检查是否存在该工艺BOM，如果存在则记录日志，不做导入。
									String exitstr=getEffect(ppart.getBsoID(),carModelCode,dwbs);
									// System.out.println(ppart.getPartNumber()+" eff:"+exitstr);
									// 创建生效结构
									if(exitstr.equals("0"))
									{
										String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
											"1","","",(String)unitmap.get(unit),spart.getPartNumber(),subGroup};
										mtree.add(mt);
									}
									else if(exitstr.equals("1"))// 删除未生效结构，创建未生效结构、生效结构
									{
										// 如果父件的未生效结构已经删除过，则跳过，否则添加。
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
									else if(exitstr.equals("2"))// 删除生效结构，创建、生效结构
									{
										// 如果父件的未生效结构已经删除过，则跳过，否则添加。
										if(!deleteParts.contains(ppart.getBsoID()+",1"))
										{
											deleteParts.add(ppart.getBsoID()+",1");
										}
										String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
											"1","","",(String)unitmap.get(unit),spart.getPartNumber(),subGroup};
										// System.out.println("======----====="+Arrays.toString(mt));
										mtree.add(mt);
									}
									else if(exitstr.equals("3"))// 删除未生效结构、生效结构，创建未生效结构、生效结构
									{
										// 如果父件的未生效结构已经删除过，则跳过，否则添加。
										if(!deleteParts.contains(ppart.getBsoID()+",0"))
										{
											deleteParts.add(ppart.getBsoID()+",0");
										}
										// 如果父件的未生效结构已经删除过，则跳过，否则添加。
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
									// 如果父件 已存在生效关联，则不需要进行导入保存。 父件已存在集合
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
						logbuffer(buffer,"文件："+file1.getName()+"成功导入。\n");
						// 将文件移到同级备份 old 文件夹中
						// System.out.println(file1.getParent()+File.separator+"bom"+File.separator+file1.getName());
						file1.renameTo(new File(file1.getParent()+File.separator+"bom"+File.separator+file1.getName()));
					}

					// 每个导入文件，即一个车型进行一次存储。
//					System.out.println("文件:"+file1.getName());
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
					logbuffer(buffer,"没有可导入的文件。\n");
				}
			}
			else
			{
				logbuffer(buffer,"没有找到导入文件"+f.getPath()+"。\n");
			}
			logbuffer(buffer,"导入完毕。\n");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * 获取导入BOM列表清单
	 */
	public Vector getBomExcel() throws QMException
	{
		Vector result=new Vector();
		try
		{
			String excelPath=RemoteProperty.getProperty("com.faw_qm.loadFiles")+File.separator+"content"+File.separator;// Excel的存放路径
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
	 * 搜索零部件，用于导入时根据编号查找零部件最新版
	 * 
	 * @param number
	 *            零部件编号
	 * @return 符合条件的零部件
	 * @exception com.faw_qm.framework.exceptions.QMException
	 */
	public QMPartIfc findPartByNumber(String number) throws QMException
	{
		 System.out.println("come in findPartByNumber number=="+number);
		// System.out.println("查找零部件！！！");
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
			// System.out.println("开始查找零部件");
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
				
////			如果没有版本，则查找规则，符合规则，去A版本，不符合规则，查找盆。盆里有的给出盆数据，盆里没有的给出提示
				if(ver.equals("")){
					Object[] vec1=co.toArray();
					QMPartIfc part1=(QMPartIfc)vec1[vec1.length-1];
					boolean ifver = helper.ifHasVersion(number,part1.getPartType().toString());	
					String cappNumber = "";
					if(ifver){
					}else{
					//没有版本找盆
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
						
						//---------如果在盆里找到了，继续查找对应的物料号------------------------------------
						//System.out.println("cappNumber111111111111===="+cappNumber);
						if(!cappNumber.equals("")){
							if(cappNumber.indexOf("/")>-1)
							{
								int l=cappNumber.length();
								int i=cappNumber.indexOf("/");
								// System.out.println("l===="+l);
								// System.out.println("i===="+i);
								if(l-i==2)
								{//重新获取版本
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
					//首先获取发布源版本
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
					//关闭连接
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
						//System.out.println("零件编号===="+part1.getPartNumber());
						String version=part1.getVersionID();
					//	 System.out.println("零件版本===="+version);
						// //编号中不带版本
						// if(ver==""){
						// part = part1;
						// }else{
						// //有版本相同的
						// if(ver.equals(version)){
						// part = part1;
						// break;
						// }
						// }
						part=part1;
						
						// 如果有版本相同的则跳出循环，否则取最新版
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
						// 如果给定版本没有，不取最新版，返回null，提示用户不存在
						part=null;
						// CCEnd SS4
						// System.out.println("零件编号111===="+part.getPartNumber());
						// System.out.println("零件版本111===="+part.getVersionID());
					}
					// CCEnd SS1
					// CCBegin SS4
				}
				// CCEnd SS4
				// System.out.println("版本1==="+part.getVersionID());//版本
				// System.out.println("版本2==="+part.getVersionLevel());//版序
				// System.out.println("版本3==="+part.getVersionValue());//版本+版序
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
							// 如果有版本相同的则跳出循环，否则取最新版
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
	 * 搜索零部件最新版
	 * 
	 * @param number
	 *            零部件编号
	 * @return 符合条件的零部件
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
	 * 删除总成 右键添加菜单“删除总成”，此菜单功能是将总成删除掉，并把子件层级上升一级。 返回
	 * 更新关联，将选中零部件与子件关联，改为父件id与子件关联。（如果当前车型下选中零部件有多个父件，需要再做考虑，复制一套结构给当前父件）
	 * 删除关联，删除选中零部件与父件关联。
	 * 
	 * @parm String parentID 选中零部件的父件
	 * @parm String partID 选中零部件
	 * @parm String carModelCode 车型码
	 * @parm String dwbs 工厂
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

			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
			conn.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "[{失败}]";
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
		return "[{成功}]";
	}

	/**
	 * 导出BOM 指定车型、工厂的BOM集合。 获取子件id和当前用户工厂的生效bom中的父件。 返回集合 编号 名称 数量 制造路线 装配路线
	 * 父件编号
	 */
	public Vector getExportBomList(String carModelCode) throws QMException
	{
		System.out.println("getExportBomList开始导出:"+carModelCode);
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
	 * 导出BOM 指定车型、工厂的BOM集合。 获取子件id和当前用户工厂的生效bom中的父件。 返回集合 编号 名称 数量 制造路线 装配路线
	 * 父件编号
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
		// 获取子件id和数量
		Vector subvec=findChildPart(id,carModelCode,dwbs,effect);
		QMPartIfc parentpart=(QMPartIfc)ps.refreshInfo(id);

		// 获取子件路线
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
				System.out.println("2层"+part.getPartNumber());
			}*/
			if(part==null)
			{
				System.out.println("未找到子件id："+childPart);
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
		// 递归获取子件
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
	 * 获取零部件父件在该工厂内的所有父件 获取子件id和当前用户工厂的生效bom中的父件。 返回集合 零部件bsoid 编号 名称 数量
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
			// 关闭ResultSet
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 获取零部件父件在该工厂内的所有未生效的父件 获取子件id和当前用户工厂的生效bom中的父件。 返回集合 零部件bsoid 编号 名称 数量
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
			// 关闭ResultSet
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
	 * 获取源零部件结构关联集合
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
//			        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa啊啊啊啊啊啊啊啊啊啊");
//			    }
//			    System.out.println("零部件编号-----------------"+part.getPartNumber());
				System.out.println("数据库中是否有结构==============="+getEffect(part.getBsoID(),carModelCode,dwbs));
				// 检查part是否存在当前工厂的结构，存在则不需要处理结构。
				if(!getEffect(part.getBsoID(),carModelCode,dwbs).equals("0"))
				{
//				    System.out.println("不处理零部件编号-----------------"+part.getPartNumber());
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
                    System.out.println("a大小======"+linkcoll.size());
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
									System.out.println("当前结构中重复，可以重复添加："
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

							// 根据规则对零部件结构进行过滤
							// 总体过滤规则
							// subpart=glpart(dwbs,subpart);
							if (subpart != null) {
								// // 发动机下1000410分组过滤规则
								// if(type.equals("1000410"))
								// {
								// subpart=glfdjpart0(dwbs,part,subpart);
								// }
								// // 发动机其它分组过滤规则
								// else if(type.equals("fdj"))
								// {
								// subpart=glfdjpart(dwbs,part,subpart);
								// }
								// // 车架分组过滤规则
								// else if(type.equals("cj"))
								// {
								// subpart=glcjpart(dwbs,part,subpart);
								// }
								// // 驾驶室分组过滤规则
								// else if(type.equals("jss"))
								// {
								// subpart=gljsspart(dwbs,part,subpart);
								// }
								// // 整车分组过滤规则
								// else if(type.equals("zc"))
								// {
								// subpart=glzcpart(dwbs,part,subpart);
								// }
								// // 部署域发动机、车架、驾驶室、整车的直接过滤掉
								// else
								// {
								// subpart=null;
								// }
								// 工艺路线过滤
								//CCBegin SS73 不用路线过滤
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
	 * 根据工艺路线规则过滤
	 * @param dwbs
	 * @param parent
	 * @param sub
	 * @return
	 */
	private boolean gylxFiltration(String dwbs,QMPartIfc parent,QMPartIfc sub) throws QMException
	{
	    //（1）    父件逻辑总成（路线是总-用），如果子件装配路线为“总”则展开，
	    //（2）制造路线包含”总“，装配路线也包含“总”时，提取子件装配路线含“总”的件
	    //（1）和（2）是需要查找子件的，其他的不查找子件，符合（1）和（2）返回true
	    boolean ret = false;
	    try
        {
	        
	        HashMap partentmap=new HashMap();
            HashMap submap=new HashMap();
            String[] parentroutestr=new String[2];
            String[] subroutestr=new String[2];
            if(dwbs.equalsIgnoreCase("W34"))// 成都取二级路线
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
            // 解放路线
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
//                System.out.println("编号父===== " + parent.getPartNumber());
//                System.out.println("编号子===== " + sub.getPartNumber());
//                System.out.println("逻辑总成父   =========== " + isLogical(parent,pzz,pzp));
//                System.out.println("逻辑总成子   =========== " + isLogical(sub,szz,szp));
//                System.out.println("szz   =========== " + szz);
//                System.out.println("szp   =========== " + szp);
//                System.out.println("pzp   =========== " + pzp);
//                System.out.println("pzz   =========== " + pzz);
//            }
            
//            if(sub.getPartNumber().contains("8105G01-13A"))
//            {
//                System.out.println("8105G01-13A编号父===== " + parent.getPartNumber());
//                System.out.println("8105G01-13A编号子===== " + sub.getPartNumber());
//                System.out.println("8105G01-13A逻辑总成父   =========== " + isLogical(parent,pzz,pzp));
//                System.out.println("8105G01-13A逻辑总成子   =========== " + isLogical(sub,szz,szp));
//                System.out.println("8105G01-13Aszz   =========== " + szz);
//                System.out.println("8105G01-13Aszp   =========== " + szp);
//                System.out.println("8105G01-13Apzp   =========== " + pzp);
//                System.out.println("8105G01-13Apzz   =========== " + pzz);
//            }
//            经存在的工艺BOM过滤后大小===5341
//            编号===== QMPart_136655116
//            逻辑总成   =========== true
//            szz   =========== 总→用
//            szp   =========== 无
//            pzp   =========== 无
//            pzz   =========== 总→用
            //（1）    父件逻辑总成（路线是总-用），如果子件装配路线为“总”则展开，
//            if(isLogical(parent,pzz,pzp)&&(szp.contains("总") || isLogical(sub,szz,szp)))
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
                else if(szp.contains("总"))
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
            //（2）制造路线包含”总“，装配路线也包含“总”时，提取子件装配路线含“总”的件
            else if(pzz.contains("总")&&pzp.contains("总"))
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
                else if(szp.contains("总"))
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
//     * 判断是否是逻辑总成
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
     * 是否是逻辑总成
     * 参数，routeAsString 制造路线，assembStr装配路线
     * 辑总成判断规则 （1）编号第五位是“G“，（1）制造路线含用、无装配路线（2）制造路线不含用，有装配路线
     * @return boolean
     * @throws QMException
     */
     public boolean isLogical(QMPartIfc part,String routeAsString ,String assembStr)throws QMException{
        boolean result=false;
        //Begin SS41
//        System.out.println("part===="+part.getPartNumber());
//        System.out.println("制造路线routeAsString===="+routeAsString);
//        System.out.println("装配路线assembStr===="+assembStr);
//        System.out.println("part.getPartNumber().substring(4, 5)===="+part.getPartNumber().substring(4, 5));
        //第5位为G并且装配路线为空并且制造路线含“用”
        if(part.getPartNumber().length()>5)
        if (part.getPartNumber().substring(4, 5).equals("G"))
        {//CCBegin SS45
        	//CCBegin SS47
//            if(routeAsString.contains("总--用")&&(assembStr.equals("")||assembStr.equals("无"))){
        	if(routeAsString.contains("总")&&routeAsString.contains("用")&&(assembStr.equals("")||assembStr.equals("无"))){
        		//CCEnd SS47
            	//CCEnd SS45
                result=true;
//                System.out.println("虚件");
            }
//            if(!routeAsString.contains("用")&&!assembStr.equals("")&&!assembStr.equals("无")){
//                result=true;
////                System.out.println("实件");
//            }
        }
//        isLogical(parent,pzz,pzp)
//        逻辑总成   =========== false
//        szz   =========== 总→用
//        szp   =========== 无
//        pzp   ===========
//        pzz   ===========
				//End SS41
        return result;
    }
//CCEnd SS37

	/**
	 * 整体过滤 协-协件
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
			if(dwbs.equals("W34"))// 成都取二级路线
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
			if(szz.equals("协")&&szp.equals("协"))
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
	 * 对发动机总成下1000410分组进行过滤（1000410有特殊的过滤规则）
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
			if(dwbs.equals("W34"))// 成都取二级路线
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
			// 解放路线
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
			if(pzz.indexOf("总")!=-1&&szp.indexOf("总")!=-1)
			{
				return sub;
			}
			else if(pzz.indexOf("总-用")!=-1&&szp.equals("总"))
			{
				return null;
			}
			else if(szp.indexOf("总")!=-1)
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
	 * 对发动机总成进行过滤
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
			if(dwbs.equals("W34"))// 成都取二级路线
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
			// 解放路线
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
			if(pzz.indexOf("总-用")!=-1&&szp.equals("总"))
			{
				return null;
			}
			else if(szp.indexOf("总")!=-1)
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
	 * 对车架总成进行过滤
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
	 * 对驾驶室总成进行过滤
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
	 * 对直接挂整车的件进行过滤
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
			if(dwbs.equals("W34"))// 成都取二级路线
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
			// 解放路线
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
			if(pzz.indexOf("总-用")!=-1&&szp.equals("总"))
			{
				if(sub.getPartNumber().contains("3513010")){
//				System.out.println("1111111111====");
				}
				return null;
			}
			//CCBegin SS26
			//过滤规则
			//1.	过滤规则为，自上向下逻辑总成路线为“总-用”，子件装配路线为“总”，且该子件下级件不在PBOM中
			//2.	制造路线不含“总”，装配路线含“总”时，不提取子件，提取同一层级装配路线含“总”的件；
			//3.	制造路线包含”总“，装配路线也包含“总”时，提取子件，提取同一层级装配路线含“总”的件；
//			CCBegin SS27
			//else if(pzz.indexOf("总")!=1&&szp.indexOf("总")!=-1)
//			CCEnd SS26
			else if(pzz.indexOf("总")!=1&&pzp.indexOf("总")!=-1)
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
	 * 根据规则对工艺合件进行拆分
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
        //左侧工艺树，顶级件 设计车型码包含 P66  是 J6P P63是 J6M P62是 J6L.
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
//            	System.out.println("111父件！！！！！！！！！！！！！！");
//            	System.out.println("父件编号===="+parentPart.getPartNumber());
//            	System.out.println("子件编号===="+spart.getPartNumber());
//            }
//            if(spart.getPartNumber().contains("1104G01")){
//            	System.out.println("222父件！！！！！！！！！！！！！！");
//            	System.out.println("父件编号===="+parentPart.getPartNumber());
//            	System.out.println("子件编号===="+spart.getPartNumber());
//            }
//		}
//		for(Iterator iter = arraylist.iterator();iter.hasNext();){
//			String[] mt=(String[])iter.next();
//            String parentID = mt[0];
//            String subid=mt[1];
//            QMPartIfc spart=(QMPartIfc)ps.refreshInfo(subid,false);
//            QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(parentID,false);
//            if(parentPart.getPartNumber().contains("1104G01")){
//            	System.out.println("222父件！！！！！！！！！！！！！！");
//            	System.out.println("父件编号===="+parentPart.getPartNumber());
//            	System.out.println("子件编号===="+spart.getPartNumber());
//            }
//		}
        
        
        ArrayList arr = new ArrayList(mtree);
        HashMap map = new HashMap();//存放要拆分件和顶级件的记录
        for(int i=0; i<arr.size(); i++)
        {
            
            String strs[] = (String[])arr.get(i);
            //CCBegin SS24
            //拆分时遇到JTA开头的就去掉，不管在哪初始化
            if(strs[9].startsWith("JTA"))
            {
//                System.out.println("--------拆分时遇到JTA开头的就去掉，不管在哪初始化-------");
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
        //J6P车型拆分
        if(sjpart.getPartNumber().contains("P66") || sjpart.getPartNumber().contains("p66") || 
                sjpart.getPartNumber().contains("P67") || sjpart.getPartNumber().contains("p67"))//SS22附件中有新旧规则对比。为用户提出新需求
        {
//            System.out.println("P66------------------P66=="+mtree);
            col = chaifengybomJ6P(gypart, mtree, partNumber1000940, partNumber1000410, map, 
                    dwbs,arraylist,id3900G01,id3100G00);//SS25
//            System.out.println("col========="+col.size());
        }
        //JMP车型拆分
        else if(sjpart.getPartNumber().contains("P63") || sjpart.getPartNumber().contains("p63") || 
                sjpart.getPartNumber().contains("P64") || sjpart.getPartNumber().contains("p64"))//SS22附件中有新旧规则对比。为用户提出新需求
        {
//            System.out.println("P63------------------P63=="+mtree);
            col = chaifengybomJ6M(gypart, mtree, partNumber1000940, partNumber1000410, map, 
                    dwbs,arraylist,id3900G01,id3100G00);//SS25
        }
        //J6L车型拆分
        else if(sjpart.getPartNumber().contains("P62") || sjpart.getPartNumber().contains("p62") || 
                sjpart.getPartNumber().contains("P61") || sjpart.getPartNumber().contains("p61"))//SS22附件中有新旧规则对比。为用户提出新需求
        {
//            System.out.println("P62------------------P62=="+mtree);
            col = chaifengybomJ6L(gypart, mtree, partNumber1000940, partNumber1000410, map, 
                    dwbs,arraylist,id3900G01,id3100G00);//SS25
        }
        //其他车型拆分
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
//                    System.out.println("父件222============="+parentPart.getPartNumber());
//                    System.out.println("子件222============="+spart.getPartNumber());
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
	 * 根据规则对工艺合件进行拆分
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
				//拆分时遇到JTA开头的就去掉，不管在哪初始化
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
					String[] mtt=new String[]{spart.getBsoID(),np.getBsoID(),"1","",mt[4],"0","","","个",
						np.getPartNumber()};
					nmap.put(spart.getPartNumber(),mtt);
				}
				if(snum.length()>7&&snum.substring(0,7).equals("2800015"))
				{
					String nnum=spart.getPartNumber()+"-T";
					QMPartIfc np=createNewTGYPart(spart,nnum,spart.getPartName());
					String[] mtt=new String[]{spart.getBsoID(),np.getBsoID(),"1","",mt[4],"0","","","个",
						np.getPartNumber()};
					nmap.put(spart.getPartNumber(),mtt);
				}
				if(snum.length()>7&&snum.substring(0,7).equals("2803G01"))
				{
					String nnum=spart.getPartNumber()+"-01";
					String nnum1=spart.getPartNumber()+"-05";
					QMPartIfc np=createNewTGYPart(spart,nnum,spart.getPartName());
					QMPartIfc np1=createNewTGYPart(spart,nnum1,spart.getPartName());
					String[] mtt=new String[]{gyID,np.getBsoID(),"1","",mt[4],"0","","","个",np.getPartNumber()};
					String[] mtt1=new String[]{gyID,np1.getBsoID(),"1","",mt[4],"0","","","个",np1.getPartNumber()};
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
			// 车架中2800010和2800015下自动增加一级
			for(Iterator iter=mtree.iterator();iter.hasNext();)
			{
				String[] mt=(String[])iter.next();
				String parentid=mt[0];
				String subid=mt[1];
				QMPartIfc ppart=(QMPartIfc)ps.refreshInfo(parentid,false);
				QMPartIfc subpart=(QMPartIfc)ps.refreshInfo(subid,false);
				String pnum=ppart.getPartNumber();
				String snum=subpart.getPartNumber();
				// 拆分规则
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
						// 创建拆分历史（提取）
						createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),npid,"tq");
					}
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("2400010")&&snum.length()>7
					&&snum.substring(0,7).equals("3103070"))
				{
					mt[0]=gyID;
					// 创建拆分历史（提取）
					createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),gyID,"tq");
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("2500010")&&snum.length()>7
					&&snum.substring(0,7).equals("3103070"))
				{
					mt[0]=gyID;
					// 创建拆分历史（提取）
					createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),gyID,"tq");
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("3000G00")&&snum.length()>7
					&&snum.substring(0,7).equals("3103070"))
				{
					mt[0]=gyID;
					// 创建拆分历史（提取）
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
						// 创建拆分历史（提取）
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
						// 创建拆分历史（提取）
						createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),nid,"tq");
					}
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("2800010"))
				{
					String[] pt=(String[])nmap.get(pnum);
					mt[0]=pt[1];
					// 创建拆分历史（提取）
					createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),pt[1],"tq");
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("2800015"))
				{
					String[] pt=(String[])nmap.get(pnum);
					mt[0]=pt[1];
					// 创建拆分历史（提取）
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
					if(dwbs.equals("W34"))// 成都取二级路线
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
					if(szp.equals("总"))
					{
						mt[0]=mtt[1];
						// 创建拆分历史（提取）
						createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),mtt[1],"tq");
					}
					else
					{
						mt[0]=mtt1[1];
						// 创建拆分历史（提取）
						createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),mtt1[1],"tq");
					}
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("3722065"))
				{
					String[] pt=(String[])nmap.get("3722065");
					mt[0]=pt[1];
					// 创建拆分历史（提取）
					createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),pt[1],"tq");
				}
				else if(pnum.length()>7&&pnum.substring(0,7).equals("3722G03"))
				{
					String[] pt=(String[])nmap.get("3722G03");
					mt[0]=pt[1];
					// 创建拆分历史（提取）
					createGyBomCFHistory(ppart.getBsoID(),subpart.getBsoID(),pt[1],"tq");
				}
				else
				{

				}
				ntree.add(mt);
			}
			// 将2800010和2800015下尾号为-T的件加到工艺BOM树中。
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
     * 其他车型拆分
     * @param gypart 根
     * @param mtree 子件
     * @param partNumber1000940 1000940件的BSOID
     * @return
     * @throws QMException
     */
    private Collection chaifengybomOther(QMPartIfc gypart,Collection mtree, String partNumber1000940, 
                        String partNumber10004101, HashMap map, String dwbs, ArrayList arrayList,
                        String id3900G01, String id3100G00) throws QMException//SS25
    {
//        System.out.println("其他车型拆分--------------------------------");
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
//                    System.out.println("父件============="+parentPart.getPartNumber());
//                    System.out.println("子件============="+spart.getPartNumber());
//                }
                
                
                //1、不需拆分、不分车型平台，直接挂到发动机总成下的逻辑总成：
                //1000410、1303G02、1503G01、1600G00、1700G00、3523G01、3724G34直接放到1000940下
                if(partNumber.contains("1000410") || partNumber.contains("1303G02") || 
                        partNumber.contains("1503G01") || partNumber.contains("1600G00") || 
                        partNumber.contains("1700G00") || partNumber.contains("3523G01") || 
                        partNumber.contains("1308G02") || partNumber.contains("1313G02") || //SS23这两个件也是放到1000940下
                        partNumber.contains("3724G34") )
                {
                    mt[0] = partNumber1000940;
                }
                //CCBegin SS23
                /*这两个件也是放到1000940下
                //1308G02、1313G02 放到1000410里
                if(partNumber.contains("1308G02") || partNumber.contains("1313G02") )
                {
                    mt[0] = partNumber10004101;
                }
                */
//                System.out.println("partNumber===="+partNumber);
                //CCEnd SS23
                //CCBegin SS25
              //3725020-A01 8203010-E06C 7925040-92W 7901012EA01 放到3900G01下
                //if(partNumber.equalsIgnoreCase("3725020-A01") || partNumber.equalsIgnoreCase("8203010-E06C") || 
                //        partNumber.equalsIgnoreCase("7925040-92W") || partNumber.equalsIgnoreCase("7901012EA01"))
                //{
                //    mt[0] = id3900G01;
                //}
                
                //以这个开头3103070的3100G00下
                if(partNumber.startsWith("3103070"))
                {
//                    mt[0] = id3100G00;
                    mt[0] = gyID;//SS32
                }
                //CCEnd SS25
                
                //CCBegin SS33
                //2800010和5000990不带结构放置到根下
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
     * 拆分后放到根下零件编号
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
            
            // 清空并关闭sql返回数据
            rs.close();
            // 关闭Statement
            stmt.close();
            // 关闭数据库连接
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
     * 上一个拆分后放940下零件编号
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
            
            // 清空并关闭sql返回数据
            rs.close();
            // 关闭Statement
            stmt.close();
            // 关闭数据库连接
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
     * 上一个拆分后放根下零件编号
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
            
            // 清空并关闭sql返回数据
            rs.close();
            // 关闭Statement
            stmt.close();
            // 关闭数据库连接
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
     * J6P车型拆分
     * @param gypart 根
     * @param mtree 子件
     * @param partNumber1000940 1000940件的BSOID
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
                
                //1、不需拆分、不分车型平台，直接挂到发动机总成下的逻辑总成：
                //1000410、1303G02、1503G01、1600G00、1700G00、3523G01、3724G34直接放到1000940下
                if(partNumber.contains("1000410") || partNumber.contains("1303G02") || 
                        partNumber.contains("1503G01") || partNumber.contains("1600G00") || 
                        partNumber.contains("1700G00") || partNumber.contains("3523G01") || 
                        partNumber.contains("1308G02") || partNumber.contains("1313G02") || //SS23这两个件也是放到1000940下
                        partNumber.contains("3724G34") )
                {
                    mt[0] = partNumber1000940;
                }
                //CCBegin SS23
                /*//这两个件也是放到1000940下
                //1308G02、1313G02 放到1000410里
                if(partNumber.contains("1308G02") || partNumber.contains("1313G02") )
                {
                    mt[0] = partNumber1000410;
                }
                */
                //CCEnd SS23
                //CCBegin SS25
                //3725020-A01 8203010-E06C 7925040-92W 7901012EA01 放到3900G01下
                //if(partNumber.equalsIgnoreCase("3725020-A01") || partNumber.equalsIgnoreCase("8203010-E06C") || 
                //        partNumber.equalsIgnoreCase("7925040-92W") || partNumber.equalsIgnoreCase("7901012EA01"))
                //{
                //    mt[0] = id3900G01;
                //}
                
                //以这个开头3103070的3100G00下
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
//                1104G01 拆分到1000940里1104130 1104250 1104210 1145035
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
//                        	System.out.println("经济纠纷就附件父件");
                            //生成新逻辑总成号  1104G01-382F001 放到940下
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
                
//                1119G01 拆分到1000940里1119046 1119031 1119035 1119053 1119062 1119064 1119125 
//                                        1119127 CQ1461045 CQ34010（1个） T6769006A
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                
//                1203G01 拆分到1000940里1203040 1203065 1203073 1203071 1203075 1203075 CQ1460816 
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                
//                8116G01 拆分到1000940里8116061 8116703
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                
//                1303G01 拆分到1000940里1303011 1303021 CQ67690B（2个）
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                
//                1311G02 拆分到1000940里1311024 1311036
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                
//                3406G01 拆分到1000940里3406010 3406021 3406321
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
//                            //生成新逻辑总成号  1104G01-382F001 放到940下
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
//                1311G01 拆分到1000940里CQ67622B(1件） CQ67640B（2件）
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
//                            //生成新逻辑总成号  1104G01-382F001 放到940下
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

                
//                3506G00 拆分到1000940里3506221 3506230
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
     * J6M车型拆分
     * @param gypart 根
     * @param mtree 子件
     * @param partNumber1000940 1000940件的BSOID
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
                
                //1、不需拆分、不分车型平台，直接挂到发动机总成下的逻辑总成：
                //1000410、1303G02、1503G01、1600G00、1700G00、3523G01、3724G34直接放到1000940下
                if(partNumber.contains("1000410") || partNumber.contains("1303G02") || 
                        partNumber.contains("1503G01") || partNumber.contains("1600G00") || 
                        partNumber.contains("1700G00") || partNumber.contains("3523G01") || 
                        partNumber.contains("1308G02") || partNumber.contains("1313G02") || //SS23这两个件也是放到1000940下
                        partNumber.contains("3724G34") )
                {
                    mt[0] = partNumber1000940;
                }
                //CCBegin SS23
                /*这两个件也是放到1000940下
                //1308G02、1313G02 放到1000410里
                if(partNumber.contains("1308G02") || partNumber.contains("1313G02") )
                {
                    mt[0] = partNumber10004101;
                }
                */
                //CCEnd SS23
                //CCBegin SS25
                //3725020-A01 8203010-E06C 7925040-92W 7901012EA01 放到3900G01下
                //if(partNumber.equalsIgnoreCase("3725020-A01") || partNumber.equalsIgnoreCase("8203010-E06C") || 
                //        partNumber.equalsIgnoreCase("7925040-92W") || partNumber.equalsIgnoreCase("7901012EA01"))
                //{
                //    mt[0] = id3900G01;
                //}
                
                //以这个开头3103070的3100G00下
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
//                1104G01 拆分到1000940里1104130 1104250 1104021 3724015 Q1841225
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                            
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
                
//                1119G01 拆分到1000940里1119031 1119035 1119038 1119049 1119050 1119081 1119105 
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
                            //生成新逻辑总成号  1119G01-382F001 放到940下

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
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
                
//                1203G01 拆分到1000940里1203510 1203030 1203075
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
                            //生成新逻辑总成号  1119G01-382F001 放到940下

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
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
                
//                1303G01 拆分到1000940里1303021 CQ67670B
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
                            //生成新逻辑总成号  1119G01-382F001 放到940下

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
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
                
//                1311G02 拆分到1000940里1311020 1311022 1311027 1311030 1311032 CQ1460820 
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
                            //生成新逻辑总成号  1119G01-382F001 放到940下

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
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
                
//                3406G01 拆分到1000940里3406011 CQ67635B 3406030 3406424
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
                            //生成新逻辑总成号  1119G01-382F001 放到940下

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
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
                
//                3406G02 拆分到1000940里3406011 CQ67635B 3406030 3406424
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
                            //生成新逻辑总成号  1119G01-382F001 放到940下

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
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
     * J6L车型拆分
     * @param gypart 根
     * @param mtree 子件
     * @param partNumber1000940 1000940件的BSOID
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
                
                //1、不需拆分、不分车型平台，直接挂到发动机总成下的逻辑总成：
                //1000410、1303G02、1503G01、1600G00、1700G00、3523G01、3724G34直接放到1000940下
                if(partNumber.contains("1000410") || partNumber.contains("1303G02") || 
                        partNumber.contains("1503G01") || partNumber.contains("1600G00") || 
                        partNumber.contains("1700G00") || partNumber.contains("3523G01") || 
                        partNumber.contains("1308G02") || partNumber.contains("1313G02") || //SS23这两个件也是放到1000940下
                        partNumber.contains("3724G34") )
                {
                    mt[0] = partNumber1000940;
                }
                //CCBegin SS23
                /*这两个件也是放到1000940下
                //1308G02、1313G02 放到1000410里
                if(partNumber.contains("1308G02") || partNumber.contains("1313G02") )
                {
                    mt[0] = partNumber10004101;
                }
                */
                //CCEnd SS23
                //CCBegin SS25
                //3725020-A01 8203010-E06C 7925040-92W 7901012EA01 放到3900G01下
                if(partNumber.equalsIgnoreCase("3725020-A01") || partNumber.equalsIgnoreCase("8203010-E06C") || 
                        partNumber.equalsIgnoreCase("7925040-92W") || partNumber.equalsIgnoreCase("7901012EA01"))
                {
                    mt[0] = id3900G01;
                }
                
                //以这个开头3103070的3100G00下
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
                
//                1104G01 拆分到1000940里1104130 1104250 1104071 CQ72316T5
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
                
//                1119G01 拆分到1000940里1119030 1119035 1119038 1119038 1119050 1119053 1119081 1119105 
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
                
//                1303G01 拆分到1000940里1303034 1303036 1303043 1303045 1303048 CQ1460825 CQ67660B Q1841245
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
                
//                1311G01 拆分到1000940里1311021 1311021 1311022 1311023 1311031 1311046 1311055
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
                
//                3406G01 拆分到1000940里3406011 CQ67635B(1）
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
                
//                3509G01 拆分到1000940里3509284 3509381
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                            //原有逻辑总成号   1104G01-382 放到一级件下
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
	 * 根据规则对符合改名的件进行改名操作（对原件进行另存为）（整车，发动机，车架）
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
//				System.out.println("父件id======="+parentid);
//				System.out.println("子件id======="+childid);
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
//				System.out.println("零件id====="+partid);
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
	 * 根据规则对符合改名的件进行改名操作（对原件进行另存为）（驾驶室）
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
	 * 零部件改名（另存为新件）
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
	 * 另存为一个新件，连带子件关联
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
				// 创建零部件改名历史
				createGyBomReNameHistory(source.getBsoID(),part.getBsoID());
				return part;
			}
			else
			{
				UserInfo user=getCurrentUserInfo();
				String lifeCyID=PublishHelper.getLifeCyID("无流程类零部件生命周期");
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
				part=(QMPartInfo)PublishHelper.assignFolder(part,"\\Root\\工艺合件");
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
				// 创建零部件改名历史
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
	 * 另存为一个新零部件，不带子件
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
				String lifeCyID=PublishHelper.getLifeCyID("无流程类零部件生命周期");
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
				part=(QMPartInfo)PublishHelper.assignFolder(part,"\\Root\\工艺合件");
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
	 * 创建零部件的拆分历史
	 * 
	 * @param sourceID
	 * @param cfID
	 * @param type
	 *            拆分的类型：cf：将一个总成拆分成多个；tq：将零部件从一个总成下提取到别的总成中。
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
			// 关闭Statement
			st.close();
			// 关闭数据库连接
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
	 * 创建零部件的另存为历史
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
			// 关闭Statement
			st.close();
			// 关闭数据库连接
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
	 * 根据零部件ID得到零部件的拆分历史
	 * 
	 * @param sourceID
	 * @param type
	 *            类型
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
			// 关闭Statement
			st.close();
			// 关闭数据库连接
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
	 * 根据零部件的ID得到零部件的改名历史
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
			// 关闭Statement
			st.close();
			// 关闭数据库连接
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
	 * 升版本 将工艺bom上节点版本提升一个版本，例如原来版本是“C”，选择降版本之后变为“D”版本
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
				return "[{未能找到当前子件下一版本！}]";
			}

			// 更新关联，子件id替换成下一版零部件id
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
System.out.println("sql语句===update GYBomStructure set childPart='"+nnpart.getBsoID()+ "',bz1='" + "red"
					+"' where effectCurrent='0' and childPart='"+part.getBsoID()+"' and dwbs='"+dwbs+"'");
				// 关闭Statement
				stmt.close();
				// 关闭数据库连接
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

			// 删除当前版本子件关联
			Collection ids=new ArrayList();
			ids=bianliGYBomid(ids,part.getBsoID(),carModelCode,dwbs);
			deleteGYBom(ids);

			// 新增 下一版本子件关联
			Collection mtree=new ArrayList();
			Vector linkvec=new Vector();
			mtree=getBomLinks(mtree,nnpart,carModelCode,dwbs,linkvec);
			//CCBegin SS15
			saveGYBom(mtree, true, dwbs);//SS21
			//CCEnd SS15

			// 获取当前零部件及其子件最新结构
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
     * 生成整车初始工艺BOM
     */
    public void initGYZCBom(String partID,String gyID,String dwbs) throws QMException
    {
    	
//        System.out.println("dwbs   ============ "+dwbs);
        try
        {
        	long t1 = System.currentTimeMillis();
            // 发动机逻辑总成
            String partNumberRuleFDJ=RemoteProperty.getProperty("gybomlistfdj");
            String partNumberRuleFDJs[] = partNumberRuleFDJ.split(",");
            ArrayList ljzcArr = new ArrayList();//逻辑总成集合
            for(int i=0; i<partNumberRuleFDJs.length; i++)
            {
//                System.out.println("partNumberRuleFDJs[i]  ======  " + partNumberRuleFDJs[i]);
                ljzcArr.add(partNumberRuleFDJs[i]);
            }
            // 整车
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
            ArrayList ohters = new ArrayList();//非逻辑总成集合
            Collection ytree=new ArrayList();//记录书签中变色零件集合
            Vector linkvec = new Vector();//这个集合感觉没什么实质用途
            //先获取第一层子件，主要获取变红标记是哪写件，同时第一层的件的父件都设置为工艺bom的根
            StandardPartService sps=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
            VersionControlService vcservice=(VersionControlService)EJBServiceHelper
                .getService("VersionControlService");
            JFService jfs=(JFService)EJBServiceHelper.getService("JFService");
            PartConfigSpecIfc configSpecIfc=sps.findPartConfigSpecIfc();
            Collection linkcoll=jfs.getUsesPartIfcs(part,configSpecIfc);//整车子件
            //CCBegin SS62
            ArrayList zcArr = new ArrayList();
            zcArr = getZCFirstLevelPart(linkcoll,dwbs);//参数为整车一级件集合
            //CCEnd SS62
            String num410 = "";
            ArrayList childList3900G01 = new ArrayList();
//            System.out.println("使用结构大小======"+linkcoll.size());
            //CCBeign SS60
            //保存整体零件关联的集合
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
                    	//保存单个关联的集合
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
                        objlink[0] = gyID;//父件id
                        objlink[1] = gypart.getPartNumber();//父件编号
                        objlink[2] = subpart.getBsoID();//子件id
                        objlink[3] = subpart.getPartNumber();//子件编号
                        objlink[4] = link.getQuantity();//这个分支的数量 float
                        objlink[5] = link;//关联关系（可以获取数量） PartUsageLinkIfc 
                        objlink[6] = level;//层级关系 int型
                        vecall.add(objlink);
//                        System.out.println(objlink[3]+"======使用数量===="+objlink[4]);
                        //CCEnd SS60
                        boolean flag = false;
                        //判断是否逻辑总成,如果为逻辑总成，那么程序继续进行
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
                        //将逻辑总成和整车都存在集合中
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
                        //获取子件
                        //CCBegin SS60
//                        getBomLinks(gyID, childrenPart, subpart, dwbs, linkvec, flag, ljzcArr, zcArr, ohters, childList3900G01);
                        getBomLinks(gyID, childrenPart, subpart, dwbs, linkvec, flag, ljzcArr, zcArr, ohters, childList3900G01,vecall,link.getQuantity(),level);
                        //CCEnd SS60
                    }
                }
            }
            
            //CCBegin SS56
            QMPartIfc part940=createGYPart(num410, childrenPart, dwbs);//创建1000940
            // 创建整车与工艺合件关联
            String[] mt940=new String[]{gyID,part940.getBsoID(),"1",gypart.getPartNumber(),dwbs,"0","","","个",
            		part940.getPartNumber()};
            childrenPart.add(mt940);
            Collection ntree=chaifengybom(gypart, childrenPart, dwbs, part, ohters);//SS20
//            for(Iterator iit = ntree.iterator();iit.hasNext();){
//            	String[] nn = (String[])iit.next();
//            	if(nn[9].contains("1119G01")){
//            		System.out.println("父件id========"+nn[0]);
//            		System.out.println("子件id========"+nn[1]);
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
//                	System.out.println("333父件！！！！！！！！！！！！！！");
//                	System.out.println("父件编号===="+parentPart.getPartNumber());
//                	System.out.println("子件编号===="+spart.getPartNumber());
//                }
//                if(spart.getPartNumber().contains("1104G01")){
//                	System.out.println("444父件！！！！！！！！！！！！！！");
//                	System.out.println("父件编号===="+parentPart.getPartNumber());
//                	System.out.println("子件编号===="+spart.getPartNumber());
//                }
//    		}
//            System.out.println("过滤前大小==="+childrenPart.size());
//            System.out.println("过滤前集合==="+childrenPart);
            //以下为过滤路线后的零件集合，也与拆分无关
            ArrayList list=new ArrayList();
            ArrayList ljzcArrOhter=new ArrayList();
            ArrayList zcArrOhter=new ArrayList();
            //已经存在的工艺BOM的件的处理
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
//                    System.out.println("已经存在" + mt[9]);
                    ohters.add(mt);
                    childrenPart.remove(mt);
                }
                else
                {
                    list.add(mt);
                }
            }
//            System.out.println("经存在的工艺BOM过滤后大小==="+list.size());
//            childrenPart = list;
            //根据工艺路线过滤
            HashMap map = new HashMap();
            HashMap partMap = new HashMap();
            //存放工艺路线过滤时不展开的子件
            ArrayList gyList = new ArrayList();
//            System.out.println("经存在的工艺BOM过滤后==="+childrenPart);
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
                	//根据工艺路线规则过滤零部件
                    if(!gylxFiltration(dwbs,parentPart,subpart))
                    {
                        gyList.add(mt1);
                        childrenPart.remove(mt1);
                        continue;//SS43
                    }
                }
                
            }
//            System.out.println("工艺不符合大小==="+gyList.size());
            for(int i=0; i<gyList.size(); i++)
            {
                String[] mt = (String[])gyList.get(i);
                removeParts(childrenPart, mt, map, partMap);
            }
            //生命周期过滤（不符合的追溯）
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
//            System.out.println("生命周期过滤后大小==="+childrenPart.size());
            //CCBegin SS56
//            QMPartIfc gyPartIfc=createGYPart(num410, childrenPart, dwbs);//创建1000940
            //CCEnd SS56
            //将配置文件中的配置过的总成添加到1000940下
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
            // 创建整车与工艺合件关联
//            String[] mt940=new String[]{gyID,gyPartIfc.getBsoID(),"1",gypart.getPartNumber(),dwbs,"0","","","个",
//                gyPartIfc.getPartNumber()};
//            childrenPart.add(mt940);
//            System.out.println("-----------------------------拆分-----------------------");
            //拆分工艺BOM，这里应该是需要修改的地方
//            Collection ntree=chaifengybom(gypart, childrenPart, dwbs, part, ohters);//SS20
            //CCEnd SS56
//            System.out.println("拆分后大小==="+ntree.size());
            //把过滤掉的件的被拆分出去的部分加回来
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
//            System.out.println("把过滤掉的件的被拆分出去的部分加回来后大小==="+ntree.size());
            // 根据规则对符合规则的件进行改名（另存为）
            //CCBegin SS55
//            Collection rtree=renameGyBom(ntree);
            Collection rtree=ntree;
            //CCEnd SS55
//            System.out.println("保存工艺BOM大小==="+ntree.size());
//            System.out.println("保存工艺BOM大小rtree==="+rtree.size());
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
 //第二次执行工艺规则过滤是因为前边过滤时不知道什么原因有些不符合过滤规则的件在过滤的集合中没有，但是过滤后又出现
                QMPartIfc parentPart=(QMPartIfc)ps.refreshInfo(mt[0],false);
                QMPartIfc subpart=(QMPartIfc)ps.refreshInfo(mt[1],false);
                if(!parentPart.getBsoID().equals(gyID))
                {
                    if(!gylxFiltration(dwbs,parentPart,subpart))
                    {
                        gyList.add(mt);
                        if(subpart.getPartNumber().contains("1301G04"))
                        {
//                            System.out.println("==========删除===== " + mt[9]+"  " + mt[1]);
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
//                System.out.println("工艺过滤----------------"+mt[9]);
                if(mt[9].contains("1301G04"))
                {
//                    System.out.println("工艺不符合大小    3513G00 mt[9]   ==========  " + mt[9]);
                }
                removeParts(childrenPart, mt, map, partMap);
            }
            //一下处理childList3900G01集合的代码，与拆分无关
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
            //查找代码太费劲，所以此处再做一次循环rtree
            //CCBegin SS50
            //设置一个集合，存放要被删除的零件
            Vector dvec = new Vector();
            for(Iterator iter =rtree.iterator();iter.hasNext();){
            	//第二个元素是零件bosid，第十个元素是零件编号
            	String[] strs = (String[])iter.next();
	            //可以在这里判断是否含有5001G01,5001G02,5002G01,5002G02,5103G01,5103G05,5103G07这几个字符串开头的零件是否包含路线“饰--用”
	        	//设置一个boolean变量，如果上述零件含有“饰--用”路线那么返回true，否则返回false
            	
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
            	//由于数量不对，所以先删除掉
            	if(partNumber.contains("3103070")){
            		dvec.add(strs);
            	}
            	//CCEnd SS60
            }
            //循环存放删除零件的集合
            for(Iterator iter = dvec.iterator();iter.hasNext();){
            	String[] strs = (String[])iter.next();
            	String partNumber = strs[9];
//            	System.out.println("零件编号------------======="+partNumber);
//            	System.out.println("删除前======"+rtree.contains(strs));
            	rtree.remove(strs);
//            	System.out.println("删除后======"+rtree.contains(strs));
            }
        	//CCEnd SS50
            //CCBegin SS39
			//系统自动识别3724015-240、3724015-240/A、T67437676、T67420376，直接替换成所需要的零件
			rtree = replacePart(rtree);
			//CCBegin SS60
			//把删除掉的3103070加进来
            for(Iterator iter = vecall.iterator();iter.hasNext();){
            	Object[] obj = (Object[])iter.next();
            	String partnumber = (String)obj[3];
            	
				if (partnumber.contains("3103070")) {
					String quantity = String.valueOf(obj[4]);
					PartUsageLinkIfc link = (PartUsageLinkIfc) obj[5];
//					System.out.println("数量=====" + quantity);
					String[] mt = new String[10];
					mt[0] = gyID;// 父件id
					mt[1] = (String) obj[2];// 子件id
					mt[2] = quantity;// 数量
					mt[3] = "";
					mt[4] = dwbs;
					mt[5] = "0";
					mt[6] = "";
					mt[7] = "";
					mt[8] = link.getDefaultUnit().getDisplay();
					mt[9] = (String) obj[3];// 子件编号
					rtree.add(mt);
            	}
            }
            //CCEnd SS60
			//CCEnd SS39
            //End SS43
            //保存工艺BOM
            saveGYBom(rtree, true, dwbs);
//            System.out.println("-----------------------------记录设计结构中被工艺结构使用变红的标记-----------------------");
            //记录设计结构中被工艺结构使用变红的标记
            saveBom(ytree);
//            System.out.println("-----------------------------结束-----------------------");
            long t2 = System.currentTimeMillis();
    		System.out.println("初始化整车BOM所用时间======"+(t2-t1)+" 毫秒");
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
//            System.out.println("-----------父件是1000410---------------"+arr);
//        }
        if(arr != null)
        {
            for(int j=0; j<arr.size(); j++)
            {
                String[] str = (String[])arr.get(j);
//                if(mt[9].contains("1000410"))
//                {
//                    System.out.println("-----------父件是1000410---------------"+str[9]);
//                }
//                System.out.println("str[1]  ==== " + str[1]);
                Integer inte = (Integer)map.get(str[1]);
                if((inte != null) && (inte>1))
                {
//                    if(mt[9].contains("1000410"))
//                    {
//                        System.out.println("1111-----------减少---------------"+str[9]);
//                    }
                    inte = inte-1;
                    //map.put(str[1], inte);
                    childrenPart.remove(str);
                }
                else
                {
//                    if(mt[9].contains("1000410"))
//                    {
//                        System.out.println("-----------删除---------------"+str[9]);
//                    }
                    childrenPart.remove(str);
                    map.remove(str[1]);
                    removeParts(childrenPart, str, map, partMap);
                }
            }
        }
    }
    
    /**
     * 获取源零部件结构关联集合
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
                                    System.out.println("当前结构中重复，可以重复添加："+part.getPartNumber()+"=="+subpart.getPartNumber());
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
                            objlink[0] = part.getBsoID();;//父件id
                            objlink[1] = part.getPartNumber();//父件编号
                            objlink[2] = subpart.getBsoID();//子件id
                            objlink[3] = subpart.getPartNumber();//子件编号
                            objlink[4] = link.getQuantity()*quantity;//这个分支的数量 float
                            objlink[5] = link;//关联关系（可以获取数量） PartUsageLinkIfc 
                            objlink[6] = level;//层级关系 int型
                            vecall.add(objlink);
//                            System.out.println(objlink[3]+"=====1使用数量1===="+objlink[4]);
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
	 * 生成整车初始工艺BOM
	 */
	public void initGYZCBom_old(String partID,String gyID,String dwbs) throws QMException//SS41
	{
		try
		{
			// 发动机逻辑总成
			String partNumberRuleFDJ=RemoteProperty.getProperty("gybomlistfdj");
			// 整车
			String partNumberRuleZC=RemoteProperty.getProperty("gybomlistzc");
			boolean gyPartFlag=false;
			Vector fdjparts=new Vector();
			Vector zcparts=new Vector();
			//存放配置文件中没有的件，目的是把这些件子件符合拆分条件的件拆分出来
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
					// 发动机
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
					// 整车
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
//				System.out.println("lyz--开始创建合件！");
				// 发动机
				Iterator fdjiter1=fdjparts.iterator();
				String[] fdjstr=(String[])fdjiter1.next();
				QMPartIfc gyPartIfc=createGYPart(fdjstr[2],fdjparts, dwbs);//SS20//创建1000940
				// 创建整车与工艺合件关联
				String[] mt=new String[]{gyID,gyPartIfc.getBsoID(),"1",gypart.getPartNumber(),dwbs,"0","","","个",
					gyPartIfc.getPartNumber()};
				mtree.add(mt);
				temp = gyPartIfc.getPartNumber();//SS37
				// 创建工艺合件
				Iterator iter=fdjparts.iterator();
				QMPartIfc fdjsp=null;
				while(iter.hasNext())
				{
					String[] str1=(String[])iter.next();
					fdjsp=(QMPartIfc)ps.refreshInfo(str1[0]);
					//CCBegin SS20
					//1000410、1303G02、1308G02（含在1000410里）、1313G02 （含在1000410里）、1503G01、1600G00、1700G00、
					//3523G01、3724G34
					if(fdjsp.getPartNumber().contains("1000410") || fdjsp.getPartNumber().contains("1303G02") || 
					        fdjsp.getPartNumber().contains("1308G02") || fdjsp.getPartNumber().contains("1313G02") || 
					        fdjsp.getPartNumber().contains("1503G01") || fdjsp.getPartNumber().contains("1600G00") || 
					        fdjsp.getPartNumber().contains("1700G00") || fdjsp.getPartNumber().contains("3523G01") || 
					        fdjsp.getPartNumber().contains("3724G34") )
					{
					    String[] mt1=new String[]{gyPartIfc.getBsoID(),fdjsp.getBsoID(),str1[1],gypart.getPartNumber(),
					            dwbs,"0","","","个",str1[2]};
//					    System.out.println("父件777============="+gyPartIfc.getPartNumber());
//	                    System.out.println("子件777============="+fdjsp.getPartNumber());
//	                    System.out.println("gypart777============="+gypart.getPartNumber());
					    mtree.add(mt1);
					}
					else
					{
					    String[] mt1=new String[]{gyID,fdjsp.getBsoID(),str1[1],gypart.getPartNumber(),
		                        dwbs,"0","","","个",str1[2]};
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
				// 整车
				Iterator zciter=zcparts.iterator();
				QMPartIfc zcsp=null;
				while(zciter.hasNext())
				{
//					System.out.println("进这里了么？？？？");
					String[] zcstr1=(String[])zciter.next();
					zcsp=(QMPartIfc)ps.refreshInfo(zcstr1[0]);
					String[] mt1=new String[]{gyID,zcsp.getBsoID(),zcstr1[1],gypart.getPartNumber(),dwbs,"0","","","个",
						zcstr1[2]};
					mtree.add(mt1);
					mtree=getBomLinks(gyID,mtree,zcsp,zcsp.getPartNumber(),dwbs,linkvec,"zc",array,otherParts);//SS20//SS37
				}
			}
			// 根据规则进行结构拆分
			Collection ntree=chaifengybom(gypart,mtree,dwbs, part,array);//SS20
			//把过滤掉的件的被拆分出去的部分加回来
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
			// 根据规则对符合规则的件进行改名（另存为）
			Collection rtree=renameGyBom(ntree);
			//CCBegin SS39
			//系统自动识别3724015-240、3724015-240/A、T67437676、T67420376，直接替换成所需要的零件
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
     * 过滤生命周期状况，不满足的向前追溯
     * 并以试制以及试制以后的状态才添加为过滤条件，如果制造视图最新版本零部件状态不满足条件
     * 需追溯该件制造视图之前满足条件的版本。
     * @param vec 零部件集合 或 结构件集合
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

              // System.out.println("最终版本 = "+part);
                //如果当前件不符合条件，则追溯前一个版本
               
                    part=getPart(part1,State);
                
      
        }
        return part;
    }
    /**
     * 根据生命周期状态追溯当前大版本零部件
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
              //  System.out.println("==================根据生命周期状态追溯当前大版本零部件 state = "+state);                
                if(state.equals(State[j]))
                {
                    return prePart;
                }
            }
           // System.out.println("==================根据生命周期状态追溯当前大版本零部件 prePart = "+prePart); 
            return getPart(prePart,State);
//        }
    }
    
	
	/**
	 * 获取没有被配置的文件中的子件被拆分出去的件的集合
	 * @param othersParts
	 * @return
	 * @throws QMException
	 */
	private ArrayList getOthersCFParts(ArrayList othersParts, QMPartIfc root, String dwbs) throws QMException
	{
	    ArrayList ret = new ArrayList();
	    //获取othersParts内所有件的所有子件
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
	    
        //根据拆分规则拆分，只保留被拆分出去的部分
        
        String partNumber1000940 = "";
        String partNumber1000410 = "";
        String id3900G01 = "";
        String id3100G00 = "";
        
        HashMap map = new HashMap();//存放要拆分件和顶级件的记录
        for(int i=0; i<allParts.size(); i++)
        {
            
            String strs[] = (String[])allParts.get(i);
//            System.out.println("strs[]   ------------    "+strs[9]);
            //CCBegin SS24
            //拆分时遇到JTA开头的就去掉，不管在哪初始化
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
            
            //1、不需拆分、不分车型平台，直接挂到发动机总成下的逻辑总成：
            //1000410、1303G02、1503G01、1600G00、1700G00、3523G01、3724G34直接放到1000940下
            if(partNumber.contains("1000410") || partNumber.contains("1303G02") || 
                    partNumber.contains("1503G01") || partNumber.contains("1600G00") || 
                    partNumber.contains("1700G00") || partNumber.contains("3523G01") || 
                    partNumber.contains("1308G02") || partNumber.contains("1313G02") || //SS23这两个件也是放到1000940下
                    partNumber.contains("3724G34") )
            {
                mt[0] = partNumber1000940;
            }
            
            //3725020-A01 8203010-E06C 7925040-92W 7901012EA01 放到3900G01下
            //Begin SS43
            //if(partNumber.equalsIgnoreCase("3725020-A01") || partNumber.equalsIgnoreCase("8203010-E06C") || 
            //        partNumber.equalsIgnoreCase("7925040-92W") || partNumber.equalsIgnoreCase("7901012EA01"))
            //{
            //    mt[0] = id3900G01;
            //}
            //End SS43
            
            //以这个开头3103070的3100G00下
            if(partNumber.startsWith("3103070"))
            {
//                mt[0] = id3100G00;
                mt[0] = root.getBsoID();
            }
            
            //J6P车型拆分
            if(root.getPartNumber().contains("P66") || root.getPartNumber().contains("p66") || 
                    root.getPartNumber().contains("P67") || root.getPartNumber().contains("p67"))//SS22附件中有新旧规则对比。为用户提出新需求
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下
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
                
//                1119G01 拆分到1000940里1119046 1119031 1119035 1119053 1119062 1119064 1119125 
//                                        1119127 CQ1461045 CQ34010（1个） T6769006A
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下
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
                
//                1203G01 拆分到1000940里1203040 1203065 1203073 1203071 1203075 1203075 CQ1460816 
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                
//                8116G01 拆分到1000940里8116061 8116703
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                
//                1303G01 拆分到1000940里1303011 1303021 CQ67690B（2个）
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                
//                1311G02 拆分到1000940里1311024 1311036
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下

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
                
//                3406G01 拆分到1000940里3406010 3406021 3406321
                
//                3506G00 拆分到1000940里3506221 3506230
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下
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
            //JMP车型拆分
            else if(root.getPartNumber().contains("P63") || root.getPartNumber().contains("p63") || 
                    root.getPartNumber().contains("P64") || root.getPartNumber().contains("p64"))//SS22附件中有新旧规则对比。为用户提出新需求
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下
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
                
//                1119G01 拆分到1000940里1119031 1119035 1119038 1119049 1119050 1119081 1119105 
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
                            //生成新逻辑总成号  1119G01-382F001 放到940下
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
                
//                1203G01 拆分到1000940里1203510 1203030 1203075
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
                            //生成新逻辑总成号  1119G01-382F001 放到940下
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
                
//                1303G01 拆分到1000940里1303021 CQ67670B
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
                            //生成新逻辑总成号  1119G01-382F001 放到940下
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
                
//                1311G02 拆分到1000940里1311020 1311022 1311027 1311030 1311032 CQ1460820 
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
                            //生成新逻辑总成号  1119G01-382F001 放到940下

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
                
//                3406G01 拆分到1000940里3406011 CQ67635B 3406030 3406424
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
                            //生成新逻辑总成号  1119G01-382F001 放到940下
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
                
//                3406G02 拆分到1000940里3406011 CQ67635B 3406030 3406424
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
                            //生成新逻辑总成号  1119G01-382F001 放到940下
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
            //J6L车型拆分
            else if(root.getPartNumber().contains("P62") || root.getPartNumber().contains("p62") || 
                    root.getPartNumber().contains("P61") || root.getPartNumber().contains("p61"))//SS22附件中有新旧规则对比。为用户提出新需求
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下
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
                
//                1119G01 拆分到1000940里1119030 1119035 1119038 1119038 1119050 1119053 1119081 1119105 
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下
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
                
//                1303G01 拆分到1000940里1303034 1303036 1303043 1303045 1303048 CQ1460825 CQ67660B Q1841245
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下
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
                
//                1311G01 拆分到1000940里1311021 1311021 1311022 1311023 1311031 1311046 1311055
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下
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
                
//                3406G01 拆分到1000940里3406011 CQ67635B(1）
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下
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
                
//                3509G01 拆分到1000940里3509284 3509381
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
                            //生成新逻辑总成号  1104G01-382F001 放到940下
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
            //其他车型拆分
            else
            {
                
            }
        }
	    return ret;
	}
	//CCEnd SS37

	/**
	 * 生成车架初始工艺BOM
	 */
	public void initGYCJBom(String partID,String gyID,String dwbs) throws QMException
	{
		try
		{
			// 车架逻辑总成
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
					
					// 车架
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
			// 车架
			Iterator cjiter=cjparts.iterator();
			QMPartIfc cjsp=null;
			while(cjiter.hasNext())
			{
				String[] cjstr1=(String[])cjiter.next();
				cjsp=(QMPartIfc)ps.refreshInfo(cjstr1[0]);
				String[] mt1=new String[]{gyID,cjsp.getBsoID(),cjstr1[1],gypart.getPartNumber(),dwbs,"0","","","个",
					cjstr1[2]};
				mtree.add(mt1);
				mtree=getBomLinks(gyID,mtree,cjsp,cjsp.getPartNumber(),dwbs,linkvec,"cj",array, new ArrayList());//SS20//SS37
			}
			//CCBegin SS73
			// 根据规则进行结构拆分
//			Collection ntree=chaifengybom(gyID,mtree);
			// 根据规则对符合规则的件进行改名（另存为）
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
	/**获得所有关联
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
	 * 生成驾驶室初始工艺BOM
	 */
	public void initGYJSSBom_old(String partID,String gyID,String dwbs,String newnumber) throws QMException
	{
		try
		{
			// 驾驶室逻辑总成
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
					// 驾驶室
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
			// 驾驶室
			Iterator jssiter=jssparts.iterator();
			ArrayList array = new ArrayList();//SS20
			QMPartIfc jsssp=null;
			while(jssiter.hasNext())
			{
				String[] jssstr1=(String[])jssiter.next();
				jsssp=(QMPartIfc)ps.refreshInfo(jssstr1[0]);
				String[] mt1=new String[]{gyID,jsssp.getBsoID(),jssstr1[1],gypart.getPartNumber(),dwbs,"0","","","个",
					jssstr1[2]};
				mtree.add(mt1);
				mtree=getBomLinks(gyID,mtree,jsssp,jsssp.getPartNumber(),dwbs,linkvec,"jss",array, new ArrayList());//SS20//SS37
			}
			// 根据规则进行结构拆分
			Collection ntree=chaifengybom(gyID,mtree);
			// 根据规则对符合规则的件进行改名（另存为）
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
	 * 保存变更记录
	 * 
	 * @param cxh
	 *            车型号
	 * @param gc
	 *            工厂
	 * @param parentid
	 *            父件id
	 * @param subid
	 *            子件id
	 * @param tsubid
	 *            替换件id
	 * @param quantityb
	 *            变更前数量
	 * @param quantitya
	 *            变更后数量
	 * @param sign
	 *            更改标识
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
		// 记录更改标记前要判断是不是有生效的BOM，如果没有，那么不进行记录，如果有才记录
		// searchChangeContent(cxh,gc);
		String flag = getEffect(parentid, "", gc);
//		System.out.println("发布类型====="+flag);
		// 当flag为3的时候（有未生效结构且含有生效结构）这样的时候才去做变更，否则不做
		if (flag.equals("3")) {
//			System.out.println("等于3才进入这里");
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

				// 关闭Statement
				st.close();
				// 关闭数据库连接
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
	/**替换件
	 * @param linkid linkid
	 * @param yid 原节点id
	 * @param xid 替换节点id
	 * @param carModelCode 车型
	 * @param dwbs 工厂
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
				// return "[{未能找到当前子件下一版本！}]";
				// }

				// 更新关联，子件id替换成下一版零部件id
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
					// 关闭Statement
					stmt.close();
					// 关闭数据库连接
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

				// 删除当前子件关联
				Collection ids = new ArrayList();
				ids = bianliGYBomid(ids, part.getBsoID(), carModelCode, dwbs);
				deleteGYBom(ids);

				// 新增子件关联
				Collection mtree = new ArrayList();
				Vector linkvec = new Vector();
				mtree = getBomLinks(mtree, nnpart, carModelCode, dwbs, linkvec);
				// CCBegin SS15
				saveGYBom(mtree, true, dwbs);// SS21
				// CCEnd SS15

				// 获取当前零部件及其子件最新结构
				str = getGYBomAll(linkid, nnpart.getBsoID(), carModelCode, dwbs);
				// CCBegin SS71
			} else if (changeType.equals("only")) {
				PersistService ps = (PersistService) EJBServiceHelper
						.getPersistService();
				StandardPartService spService = (StandardPartService) EJBServiceHelper
						.getService("StandardPartService");
				QMPartIfc part = (QMPartIfc) ps.refreshInfo(yid);
				QMPartIfc nnpart = (QMPartIfc) ps.refreshInfo(xid);

				// 更新关联，子件id替换成下一版零部件id
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
					// 关闭Statement
					stmt.close();
					st1.close();
					// 关闭数据库连接
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
				// 新增子件关联
				Collection mtree = new ArrayList();
				Vector linkvec = new Vector();
				mtree = getBomLinks(mtree, nnpart, carModelCode, dwbs, linkvec);
				saveGYBom(mtree, true, dwbs);
				// 获取当前零部件及其子件最新结构
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
	/**查询关联书签
	 * @param carModelCode 车型码
	 * @return 
	 */
	public String searchLinkBook(String carModelCode) throws QMException{
		JSONArray json=new JSONArray();
		try {
			PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
			QMPartIfc part;
			// 设置根节点
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
					//首先判断子件中不能有这个件，如果有那就不是书签
					stmt1=conn.createStatement();
					String sql1 = "select count(*) childPart from GYBomStructure where childPart='"+partid+"'";
					rs1=stmt1.executeQuery(sql1);
					rs1.next();
					int count1=rs1.getInt(1);
					//然后判断父件，父件中必须得有才能是书签，两个条件需要并存才可以确定
					stmt2=conn.createStatement();
					String sql2 = "select count(*) parentPart from GYBomStructure where parentPart='"+partid+"'";
					rs2=stmt2.executeQuery(sql2);
					rs2.next();
					int count2=rs2.getInt(1);
//					System.out.println("数量count1======="+count1);
//					System.out.println("数量count2======="+count2);
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
				// 清空并关闭sql返回数据
				rs.close();

				// 关闭Statement
				stmt.close();
				// 关闭数据库连接
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
	/**导出BOM
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
//        //零部件发布源版本
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
	 * 导出BOM 指定车型、工厂的BOM集合。 获取子件id和当前用户工厂的生效bom中的父件。 
	 * 返回集合:级别 编号 版本 名称 数量 制造路线 装配路线 父件编号
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
		// 获取子件id和数量
		Vector subvec=findChildPart(id,carModelCode,dwbs,effect);
//		System.out.println("subvec.size()===="+subvec.size());
		QMPartIfc parentpart=(QMPartIfc)ps.refreshInfo(id);

		// 获取子件路线
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
//				//零部件发布源版本
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
				System.out.println("2层"+part.getPartNumber());
			}*/
			if(part==null)
			{
				System.out.println("未找到子件id："+childPart);
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
		// 递归获取子件
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
//		        //零部件发布源版本
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

			//级别 编号 版本 名称 数量 制造路线 装配路线 父件编号
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
		/**创建路线表
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
				reloadTRLIfc.setRouteListLevel("一级路线");
				reloadTRLIfc.setRouteListState("艺准");
				reloadTRLIfc.setDefaultDescreption("初始化艺准");
				reloadTRLIfc.setProductMasterID(productpart.getMasterBsoID());
				reloadTRLIfc.setProductID(productpart.getBsoID());
				//设置setPartIndex
				Vector vec = new Vector();
				String[] restr = new String[6];
				restr[0]=productpart.getMasterBsoID();
				restr[1]=productpart.getPartNumber();
				restr[2]="1";
				restr[3]=productpart.getBsoID();
				restr[4]="0";
				restr[5]="采用";
				vec.add(restr);
				reloadTRLIfc.setPartIndex(vec);

				String folderpath = "\\Root\\工艺准备通知书";
				String lifeCycleTemplateName = "缺省";

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
		 * 创建零部件的路线并保存到数据库中。在该方法中，根据路线处理规则进行路线导入。
		 * @param list 路线表
		 * @param part PDM零部件
		 * @param source 中间表零部件
		 * @return
		 * @throws QMException 
		 */
		public ListRoutePartLinkIfc createListRoutePartLink
			(TechnicsRouteListIfc list ,QMPartIfc part ,QMPartIfc source) 
																throws QMException
		{
			long a = System.currentTimeMillis();
			
			
			//获得持久化服务
			PersistService pservice = (PersistService) EJBServiceHelper
															.getPersistService();
			ListRoutePartLinkIfc listPart = 
				ListRoutePartLinkInfo.newListRoutePartLinkInfo
					(list, part.getMaster().getBsoID(), part.getBsoID());
//			System.out.println("route0000用时*****" +(System.currentTimeMillis() - a));
//			a = System.currentTimeMillis();
			//创建路线对象
			TechnicsRouteIfc routeInfo = new TechnicsRouteInfo();
			routeInfo.setRouteDescription("初始化整车路线");
			routeInfo.setModefyIdenty(getCodingIfc("新增"));
			//这里之前要保存一下TechnicsRouteIfc对象
			routeInfo = (TechnicsRouteIfc) pservice.saveValueInfo(routeInfo);
			if(routeInfo == null)
			{
				throw new QMException("创建路线对象出错");
			}
			listPart.setRouteID(routeInfo.getBsoID());
			listPart.setAdoptStatus("采用");
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
			//关联中保存产品编号名称和id
			listPart.setParentPartID(list.getProductID());
			listPart.setParentPartNum(part.getPartNumber());
			listPart.setParentPartName(part.getPartName());
			//CCEnd SS49
			listPart = (ListRoutePartLinkIfc) pservice.saveValueInfo(listPart);
//			System.out.println("route333333333用时*****" +(System.currentTimeMillis() - a));
//			a = System.currentTimeMillis();
			if(listPart == null)
			{
				throw new QMException("创建零部件"+ part.getPartNumber() + "与路线表关联出错！");
			}
			boolean flag = false;
			flag = saveRouteBranch(routeInfo, source);
//			System.out.println("route333333333用时*****" +(System.currentTimeMillis() - a));
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
		 * 根据标记获得路线标记对象
		 * @param 路线标记
		 * @return 路线标记对象
		 * @author houhf
		 */
		private CodingIfc getCodingIfc(String codestr) throws QMException 
		{
			//路线标记
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
					System.out.println("获取路线标记错误！！！");
					e.printStackTrace();
				}
			}
			
			return codeifc;
		}
		
		/**
		 * 根据单位简称获得单位bsoID
		 * @param departmentName 简称
		 * @return 单位bsoID
		 * @author houhf
		 */
		private String getDepartmentID(String departmentName)throws QMException
		{
			//缓存路线单位ID key单位名称 value单位ID
			HashMap<String, String> departmentMap = null;
			if(departmentMap == null)
			{
				departmentMap = new HashMap<String, String>();
				
				try
				{
					//创建数据库连接
					Connection conn=null;
					Statement stmt=null;
					ResultSet rs=null;
					//搜索CODINGEJB表
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
					
					//搜索CODINGEJB表
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
					//关闭连接
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
					System.out.println("获取路线单位ID出错！！！");
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
		 * 保存路线分支。
		 * @param hashtable 属性表
		 * @param vector 要输出的日志信息
		 * @return 创建过程是否正确
		 * @author houhf
		 */
		
		private boolean saveRouteBranch(TechnicsRouteIfc routeInfo,QMPartIfc part)
		{
			try
			{
				//标记每条路线有几个分支
				int count = 0;
				PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
				
				count = count + 1;
				//制造路线
				String manuRoute = "";
				//装配路线
				String assRoute="";
				HashMap map=new HashMap();
				
				//制造路线
				manuRoute = "总";
				//装配路线
				assRoute="总";
				//整个路线串
				String routeBranchStr;
				if(manuRoute != null && manuRoute.length()>0)
				{
					routeBranchStr = manuRoute.replaceAll("-","→");
				}
				else
				{
					routeBranchStr = "无";
				}
				if(assRoute != null && assRoute.length()>0)
				{
					routeBranchStr = routeBranchStr + "@" + assRoute;
				}
				else
				{
					routeBranchStr = routeBranchStr + "@无";
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
//				System.out.println("进来的制造路线集合是======" + manuRoute);
				//制造路线Node
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
						//当有路线ID不存在时不作处理
						String departmentID = getDepartmentID(manu);
						RouteNodeIfc nodeInfo = new RouteNodeInfo();
						nodeInfo.setRouteType(RouteCategoryType.MANUFACTUREROUTE.getDisplay());
						nodeInfo.setNodeDepartment(departmentID);
						nodeInfo.setRouteInfo(routeInfo);
						x = x + 100;
						nodeInfo.setX(new Long(x).longValue());
						nodeInfo.setY(new Long(y).longValue());
//						System.out.println("node == setRouteType=======" + RouteCategoryType.MANUFACTUREROUTE.getDisplay());
//						System.out.println("node == 当前制造路线串是======" + manu);
//						System.out.println("node == setNodeDepartment==" + getDepartmentID(manu));
//						System.out.println("node == setRouteInfo=======" + routeInfo.getBsoID());
						nodeInfo = (RouteNodeIfc) pservice.saveValueInfo(nodeInfo);
						nodeVec.add(nodeInfo);
					}
				}
				//装配路线
				if(assRoute != null && assRoute.trim().length() > 0)
				{
					//当有路线ID不存在时不作处理
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
//						System.out.println("node == 当前装配路线串是======" + assRoute);
//						System.out.println("node == setNodeDepartment==" + getDepartmentID(assRoute));
//						System.out.println("node == setRouteInfo=======" + routeInfo.getBsoID());
						nodeInfo = (RouteNodeIfc) pservice.saveValueInfo(nodeInfo);
						nodeVec.add(nodeInfo);
					}
				}
				
				//设置节点关联
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
				
				//设置分支与节点关联
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
//				String mes = "创建路线部分出错！" + "\n" + "异常信息是：" + "\n";
//				mes += e.getClientMessage();
//				writeLog(mes);
				return false;
			}
			return true;
		}
		//CCEnd SS38
		//CCBegin SS39
		/**系统自动识别3724015-240、3724015-240/A、T67437676、T67420376，直接替换成所需要的零件
		 * @param co
		 * @return
		 * @throws QMException 
		 */
		public Collection replacePart(Collection co) throws QMException{
			Collection col = new ArrayList();
			for(Iterator iter = co.iterator();iter.hasNext();){
				String[] strs = (String[])iter.next();
				String str9 = strs[9];//子件编号
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
		/**获得零件bsoid
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
	     * 通过零部件的名字和号码查找零部件的主信息。返回的集合中只有一个QMPartMasterIfc对象。
	     * @param partNumber :String 零部件的号码。
	     * @return collection:查找到的QMPartMasterIfc零部件主信息对象的集合，只有一个元素。
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
		 * 通过masterid获取最新版本的零部件。
		 * @param masterID masterid
		 * @return QMPartInfo 最新版本的零部件(QMPart);
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
	 * 结构比较 获取设计BOM结构，工艺BOM结构，比较零部件区别，数量区别。
	 * @param desginID 设计BOM车型id
	 * @param gyID 工艺BOM车型id
	 * @param carModelCode 车型码
	 * @param dwbs 工厂
	 * @param isRelease 是否发布
	 * @return 含有五个元素的集合（编号、设计BOM中数量、设计BOM中版本、工艺BOM中数量、工艺BOM中版本）
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
			// 获取设计BOM结构
			Vector vec = psh.getBOMList(desginID,
					"partNumber-0,partName-0,quantity-0,", "", "");
//			long t2 = System.currentTimeMillis();
//			System.out.println("CompartTreeResult  getBOMList  用时： "
//					+ (t2 - t1) + " 毫秒");
			// System.out.println(vec.size());
			HashMap designMap = new HashMap();
			// vec中第一个元素是表头，第二个元素是车型，所以从第三个元素开始是设计BOM中的结构
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
//			System.out.println("CompartTreeResult  designMap  用时： " + (t3 - t2)
//					+ " 毫秒");

			// 获取工艺BOM
			HashMap gymap = new HashMap();
			//map中key是零件编号，value是id、编号、数量的一个集合
			gymap = bianli(gyID, carModelCode, dwbs, 1L, gymap, isRelease);
//			long t4 = System.currentTimeMillis();
//			System.out.println("CompartTreeResult  bianli  用时： " + (t4 - t3)
//					+ " 毫秒");
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
				// System.out.println("编号:"+str[1]+"    gy数量："+str[2]+"    design数量："+designMap.get(str[1]));
				//工艺BOM中零部件的值对象
				//CCBegin SS42
				QMPartIfc part = (QMPartIfc) ps.refreshInfo(str[0]);
				String gyfbyIbaValue = RationHelper.getIbaValueByName("sourceVersion",str[0]);
				if(gyfbyIbaValue==""){
					gyfbyIbaValue = part.getVersionValue();
				}
				//工艺BOM零件编号
//				String gypartnum = part.getPartNumber();
				//CCEnd SS42
				if (designMap.containsKey(str[1])) {
//					System.out.println("str[1]111111=======" + str[1]);
					//设计BOM中包含工艺BOM零件
					String[] destr = (String[]) designMap.get(str[1]);
					//设计BOM中零件id
					String departid = destr[0];
					//设计BOM中零件值对象
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
					//设计BOM中不包含工艺BOM零件
					i++;
					m++;
					String[] strs = new String[5];
//					System.out.println("str[1]=======" + str[1]);
					strs[0] = str[1];
					strs[1] = "0";
					strs[2] = "无";
					strs[3] = str[2];
					//CCBegin SS42
//					strs[4] = part.getVersionValue();
					strs[4] = gyfbyIbaValue;
					//CCEnd SS42
					vector.add(strs);
				}
			}
//			System.out.println("m=======" + m);
//			System.out.println("差异数量1====" + i);
//			System.out.println("j=======" + j);
			Set idSet = designMap.keySet();
			Iterator iter = idSet.iterator();
			String num = "";
			while (iter.hasNext()) {
				i++;
				num = (String) iter.next();
				String[] str = (String[]) designMap.get(num);
				// System.out.println("design 剩余："+(String)iter.next());
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
				strs[4] = "无";
				vector.add(strs);
			}
			//CCBeing SS66
			sortTongJiVector(vector,0);
			//CCEnd SS66
//			System.out.println("差异数量2====" + i);
//			long t5 = System.currentTimeMillis();
//			System.out.println("CompartTreeResult  json  用时： " + (t5 - t4)
//					+ " 毫秒");
//			System.out.println("整个组织数据用时： " + (t5 - t1) + " 毫秒");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return vector;
	}

	/**
	 * 遍历查找所有子件数量
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

			// 清空并关闭sql返回数据
			rs.close();
			// 关闭Statement
			stmt.close();
			// 关闭数据库连接
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
     * 获得路线信息，整体处理，in的sql方式。每次搜索20个，
     * 这个数是在解放测试机上多次测试结果得出的效率最佳个数。
     * @param routeTempMap HashMap route值的缓存，key为partMasterBsoID+route名称，值就是这个part对应routename的值。
     * @param bomMap HashMap 所有报表中涉及的零部件的集合。key为partBsoID，
     * 值为二维数组，第一个元素是partMasterBsoID，第二个元素是其父件的partMasterBsoID。
     * @return HashMap 返回routeTempMap。
     */
    private HashMap getAllRouteValue(String insql,HashMap routeTempMap)
    {
    	HashMap map = new HashMap();
    	
    	//构造sql，得到全部路线。后续要处理去掉已取消的路线。
    	// 添加TechnicsRouteList不为临准、technicsroute不是取消状态，去掉alterstatus='1'条件。
    	//String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid, route.modefyIdenty from technicsroutebranch branch, listroutepartlink link,technicsroute route,technicsroutelist tcl where branch.routeid=link.routeid and route.bsoid=branch.routeid and link.rightbsoid in (";
    	String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid, route.modefyIdenty,link.colorflag from technicsroutebranch branch, listroutepartlink link,technicsroute route,technicsroutelist tcl where branch.routeid=link.routeid and route.bsoid=branch.routeid and link.rightbsoid in (";
    	// 李萍提出个人资料夹的艺准不取其路线。    	
    	String sql2 = ") and tcl.bsoid=link.leftbsoid and tcl.iterationiflatest=1 and tcl.routeliststate!='临准' and tcl.owner is null order by tcl.modifytime desc";

    	Connection conn = null;
      Statement stmt =null;
      ResultSet rs=null;
  	  try
  	  {
  	  	PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
  	    //搜索表，根据艺准的masterid搜索数量。
    	  conn = PersistUtil.getConnection();
      	stmt = conn.createStatement();
      			rs = stmt.executeQuery(sql1+insql+sql2);
      			while(rs.next())
      			{
      				String keyName = rs.getString(2)+"路线";
    			  			//System.out.println("keyName==="+keyName);
      						if(routeTempMap.containsKey(keyName))
      						{
      							HashMap routemap = (HashMap)routeTempMap.get(keyName);
      							if(routemap.containsKey(rs.getString(3)+rs.getString(4)))
      							{
      								//同一艺准，同一零部件的路线可能有多条，重复的不添加。
      								//比如路线中同时添加了两条路线 研@研 研@研，这样集合routemap的值是“研@研, 研@研”，在用取消的“研@研”过滤时就过滤不掉。
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
      					
      				//得到最新路线保存
      				if(!rs.getString(4).equals("Coding_221664")&&(!routeTempMap.containsKey(rs.getString(2)+"最新路线")||(routeTempMap.containsKey(rs.getString(2)+"最新路线")&&routeTempMap.get(rs.getString(2)+"最新路线").toString().indexOf(rs.getString(3))!=-1)))
      				{
      					if(routeTempMap.containsKey(rs.getString(2)+"最新路线"))
      					{
      						String str1 = routeTempMap.get(rs.getString(2)+"最新路线").toString();
      						routeTempMap.put(rs.getString(2)+"最新路线",str1+","+rs.getString(1));
      					}
      					else
      					{
      						routeTempMap.put(rs.getString(2)+"最新路线",rs.getString(3)+";"+rs.getString(1));
      					}
      				}
      			}
    				//System.out.println("routeTempMap==="+routeTempMap);
    				//将路线串处理。
    			  String temp[] = insql.split(",");
    			  for(int ii = 0;ii<temp.length;ii++)
    			  {
    			  	String id = temp[ii].substring(1,temp[ii].length()-1);
    			  	HashMap routemap = (HashMap)routeTempMap.get(id+"路线");
//    			  	System.out.println(id+"全路线为："+routemap);
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
    			  		if(str.endsWith("Coding_221664"))//取消状态
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
    			  			
    			  			//存在如下情况:
    			  			//routevec======[协@总,协@岛, 柴@柴]
    			  			//canelvec======[协@岛,协@总]
    			  			//协@总,协@岛和协@岛,协@总都是 一个完整的字符串。
    			  			//要进行特殊判断和处理。
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
    			  				//else if(zhizaostr.indexOf(getZhiZaoRoute(str))==-1)//anan 可以判断如果zhizaostr里面包含getZhiZaoRoute(str)但有不一样就添加？想要处理 协--架(漆)/协  到  总 这种情况。
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
    			  			String str1 = routeTempMap.get(id+"最新路线").toString();
    			  			str1 = str1.substring(str1.indexOf(";")+1,str1.length());
    			  			String temproute[] = str1.split(",");
    			  			for(int iiii=0;iiii<temproute.length;iiii++)
    			  			{
    			  				String str = temproute[iiii];
    			  				if(zhizaostr.equals(""))
    			  				{
    			  					zhizaostr = getZhiZaoRoute(str);
    			  				}
    			  				//else if(zhizaostr.indexOf(getZhiZaoRoute(str))==-1)//anan 可以判断如果zhizaostr里面包含getZhiZaoRoute(str)但有不一样就添加？想要处理 协--架(漆)/协  到  总 这种情况。
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
//    			  System.out.println("制造路线======"+zhizaostr);
//    			  System.out.println("装配路线======"+zhuangpeistr);
    			  	routeTempMap.remove(id+"路线");
    			  	routeTempMap.put(id+"制造路线",zhizaostr);
    			  	routeTempMap.put(id+"装配路线",zhuangpeistr);    			  	
    			  }
      	//清空并关闭sql返回数据
  	    rs.close();
  	    //关闭Statement
  	    stmt.close();
  	    //关闭数据库连接  
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
  	  
  	  //转换集合
  	  try
  	  {
        if(routeTempMap!=null)
        {
        	String temp[] = insql.split(",");
        	for(int ii = 0;ii<temp.length;ii++)
        	{
        		String id = temp[ii].substring(1,temp[ii].length()-1);
        		String str1 = routeTempMap.get(id+"制造路线").toString();
        		String str2 = routeTempMap.get(id+"装配路线").toString();
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
     * 根据路线串获得制造路线。路线串格式为 制造路线@装配路线 。
     * @param s String 路线串。
     * @return String 制造路线。
     */
    private String getZhiZaoRoute(String s)
    {
    	String route = s.substring(0,s.indexOf("@"));
    	if(route.equals("无"))
    	{
    		route = "";
    	}
    	return route.replaceAll("→","--");
    }

    /**
     * 根据路线串获得装配路线。路线串格式为 制造路线@装配路线 。
     * @param s String 路线串。
     * @return String 装配路线。
     */
    private String getZhuangPeiRoute(String s)
    {
    	String route = s.substring(s.indexOf("@")+1,s.length());
    	if(route.equals("无"))
    	{
    		//route = "";
    	}
    	return route.replaceAll("→","--");
    }


    /**
     * 判断总的路线字符串中是否包含当前路线字符串。
     * @param s String 总的路线串。
     * @param s1 String 当前路线串。
     * @return boolean 是否包含，是true，否则 false。
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
	 * 查找符合规则的零部件，并创建路线
	 * 
	 * @param partID
	 *            设计车型ID
	 * @throws QMException
	 */
	public void searchAndCreateTechnics(String partID) throws QMException {
		long t1 = System.currentTimeMillis();
		PersistService ps = (PersistService) EJBServiceHelper
				.getPersistService();

		PartServiceHelper psh = new PartServiceHelper();
		// 获取设计BOM结构
		Vector vec = psh.getBOMList(partID,
				"partNumber-0,partName-0,quantity-0,", "", "");
		// 存储要创建路线的零件集合（零件值对象、路线集合）
		Vector saveVec = new Vector();
		// 路线表
		TechnicsRouteListIfc tifc = new TechnicsRouteListInfo();
		for (Iterator iter = vec.iterator(); iter.hasNext();) {
			Object[] str = (Object[]) iter.next();
			// 元素0为ID，元素4为编号
			String partid = (String) str[0];
			String partnumber = (String) str[4];
			
			//编号大于5位，并且倒数第四位是"J"或者"j"，最后三位为纯数字的时候才创建路线，否则不管
			if (partnumber.length() > 5) {
				//倒数第四位的字符
				String final4 = partnumber.substring(partnumber.length()-4, partnumber.length()-3);
				if ((final4.equals("J")||final4.equals("j"))&& partnumber.substring(4, 5).equals("G")) {
					// 添加一个判断，如果-J后面的三位数字不是纯数字，那么不查询
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
							// 截取的零件的编号
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
							// master不为空的时候才去设置路线
							if (pmaster != null) {
								// System.out.println("存在此件！！！！");
								// 查找最新的关联routelistpartlinkid
								String linkid = searchLinkID(pmaster);
								// System.out.println("linkid-----------------==========================="+linkid);
								if (linkid != "") {
									// 所有路线（每个元素是个数组，数组中数据分别为：制造路线、装配路线、是否主要路线）
									Collection routecoll = getRoutes(linkid);
									// 有路线的时候才去存放集合，否则不存（零件值对象、路线集合）
									if (routecoll.size() > 0) {
										Object[] result = new Object[3];
										result[0] = subpart;
										result[1] = routecoll;
										saveVec.add(result);// 存到集合中
									}
								}
							}
						}
					}
				}
			}
		}

		// 当集合大于0才创建路线表
		if (saveVec.size() > 0) {
			// 设计BOM车型值对象
			QMPartIfc desPartIfc = (QMPartIfc) ps.refreshInfo(partID);
			String desMasterID = desPartIfc.getMasterBsoID();
			// 路线表编号
			String technicsNumber = "intGYBOM" + desPartIfc.getPartNumber();
			TechnicsRouteService trs = (TechnicsRouteService) EJBServiceHelper
					.getService("TechnicsRouteService");
			tifc = trs.findRouteListByNum(technicsNumber);
			if (tifc == null) {
				tifc = createRouteList(desPartIfc, desMasterID, saveVec);
			}
			// 调用零部件服务
			StandardPartService sps = (StandardPartService) EJBServiceHelper
					.getService("StandardPartService");

			// 循环创建关联关系
			for (Iterator iter = saveVec.iterator(); iter.hasNext();) {
				Object[] str = (Object[]) iter.next();// （零件值对象、路线集合）
				createListRoutePartLink(tifc, str, desPartIfc.getPartNumber(),
						desPartIfc.getPartName());
				// tifc.getProductID();
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println("搜索并创建所用时间======" + (t2 - t1) + " 毫秒");
	}
    
    /**创建路线表(专为初始化使用)
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
			reloadTRLIfc.setRouteListLevel("一级路线");
			reloadTRLIfc.setRouteListState("艺准");
			reloadTRLIfc.setDefaultDescreption("初始化艺准");
			reloadTRLIfc.setProductMasterID(parentPartMasterID);
			reloadTRLIfc.setProductID(partifc.getBsoID());
			//设置setPartIndex
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
				restr[5]="采用";
//				System.out.println(restr[0]+"===="+restr[1]+"===="+restr[2]+"===="+restr[3]+"===="+restr[4]+"===="+restr[5]);
				vec.add(restr);
			}
			reloadTRLIfc.setPartIndex(vec);
			
			String folderpath = "\\Root\\工艺准备通知书";
			String lifeCycleTemplateName = "缺省";

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
	 * 创建零部件的路线并保存到数据库中。在该方法中，根据路线处理规则进行路线导入。(专为初始化使用)
	 * @param list 路线表
	 * @param part PDM零部件
	 * @param source 中间表零部件
	 * @return
	 * @throws QMException 
	 */
	public ListRoutePartLinkIfc createListRoutePartLink
		(TechnicsRouteListIfc list ,Object[] str,String productnum,String productname) 
															throws QMException
	{
		QMPartIfc part =(QMPartIfc)str[0];
		long a = System.currentTimeMillis();
		//获得持久化服务
		PersistService pservice = (PersistService) EJBServiceHelper
														.getPersistService();
		ListRoutePartLinkIfc listPart = 
			ListRoutePartLinkInfo.newListRoutePartLinkInfo
				(list, part.getMaster().getBsoID(), part.getBsoID());
		//创建路线对象
		TechnicsRouteIfc routeInfo = new TechnicsRouteInfo();
		routeInfo.setRouteDescription("初始化整车路线");
		routeInfo.setModefyIdenty(getCodingIfc("新增"));
		routeInfo.setIsAdopt(false);
		//这里之前要保存一下TechnicsRouteIfc对象
		routeInfo = (TechnicsRouteIfc) pservice.saveValueInfo(routeInfo);
		if(routeInfo == null)
		{
			throw new QMException("创建路线对象出错");
		}
		listPart.setRouteID(routeInfo.getBsoID());
		listPart.setAdoptStatus("采用");
		listPart.setAlterStatus(1);
		listPart.setColorFlag("0");
		//关联中保存产品编号名称和id
		listPart.setParentPartID(list.getProductID());
		listPart.setParentPartNum(productnum);
		listPart.setParentPartName(productname);
		
		listPart = (ListRoutePartLinkIfc) pservice.saveValueInfo(listPart);
		if(listPart == null)
		{
			throw new QMException("创建零部件"+ part.getPartNumber() + "与路线表关联出错！");
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
	 * 保存路线分支。(专为初始化使用)
	 * @param hashtable 属性表
	 * @param vector 要输出的日志信息
	 * @return 创建过程是否正确
	 * @author houhf
	 */
	
	private boolean saveRouteBranch(TechnicsRouteIfc routeInfo,Object[] str)
	{
		try
		{
			//标记每条路线有几个分支
			int count1 = 0;
			PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
			count1 = count1 + 1;
			Collection coll = (Collection)str[1];
			//循环操作，每个元素存一遍
			for(Iterator iter2 = coll.iterator();iter2.hasNext();){
				Object[] resultroutestr = (Object[])iter2.next();
//				for(int kk = 0;kk<resultroutestr.length;kk++){
//					System.out.println("各元素的值======"+kk+"======"+resultroutestr[kk]+"************");
//				}
//				System.out.println("                     ");
				//制造路线
				String manuRoute = (String)resultroutestr[1];
				//装配路线
				String assRoute=(String)resultroutestr[2];
				//是否主要路线
				String ismain = (String)resultroutestr[3];
				//整个路线串
				String routeBranchStr;
				if(manuRoute != null && manuRoute.length()>0)
				{
					routeBranchStr = manuRoute.replaceAll("--","→");
				}
				else
				{
					routeBranchStr = "无";
				}
				if(assRoute != null && assRoute.length()>0)
				{
					routeBranchStr = routeBranchStr + "@" + assRoute;
				}
				else
				{
					routeBranchStr = routeBranchStr + "@无";
				}
				TechnicsRouteBranchIfc routeBranchInfo = new TechnicsRouteBranchInfo();
				if(ismain.equals("是")){
					routeBranchInfo.setMainRoute(true);
				}else{
					routeBranchInfo.setMainRoute(false);
				}
				routeBranchInfo.setRouteStr(routeBranchStr);
				routeBranchInfo.setRouteInfo(routeInfo);
				routeBranchInfo = (TechnicsRouteBranchIfc) pservice.saveValueInfo(routeBranchInfo);
				
				//节点集合
				Vector<RouteNodeIfc> nodeVec = new Vector<RouteNodeIfc>();
				//制造路线Node
				if(manuRoute != null && manuRoute.trim().length() > 0)
				{
					Collection nodeString = new Vector();
					StringTokenizer token = new StringTokenizer(manuRoute, "→");
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
						//当有路线ID不存在时不作处理
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
				
				//装配路线
				if(assRoute != null && assRoute.trim().length() > 0)
				{
					//当有路线ID不存在时不作处理
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
				
				//设置节点关联
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
				
				//设置分支与节点关联
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
	
	/**查找零件的关联id（最新版的）
	 * @return 最新的关联id
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
		// 升序(false是升序，第一个元素时间最早。true是降序，第一个元素时间最晚)
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
	   * 获得指定路线的路线分支信息
	   * @param linkBsoID 路线表与零部件的关联对象ListRoutePartLink的BsoID
	   * @return 返回集合的元素为数组，每个数组的元素依次为序号、制造路线串、装配路线串、是/否主要路线
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
	        String isMainRoute = "是";
	        if(branchinfo.getMainRoute())
	          isMainRoute = "是";
	        else
	          isMainRoute = "否";

	        String makeStr = "";
	        String assemStr = "";
	        Object[] nodes = (Object[])map.get(branchinfo);
	        Vector makeNodes = (Vector)nodes[0];              //制造节点集合
	        RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1];   //装配节点

	        //{{{为生成节点表作准备
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
	              makeStr = makeStr +"→"+node.getNodeDepartmentName();
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
	  
	/**判断字符串是不是纯数字
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
	 * 可以在这里判断是否含有5001G01,5001G02,5002G01,5002G02,5103G01,5103G05,5103
	 * G07这几个字符串开头的零件是否包含路线“饰--用”
	 * 设置一个boolean变量，如果上述零件含有“饰”“用”并且不包含“总”路线那么返回true，否则返回false
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
		if (dwbs.equalsIgnoreCase("W34"))// 成都取二级路线
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
				if (szz.contains("饰") && szz.contains("用") && !szz.contains("总")) {
					flag = true;
				}
			}
//			System.out.println("返回值===="+flag);
//			System.out.println("     ");
		}
		return flag;
	}
	// CCEnd SS50
	//CCBegin SS52
	/**
	 * 保存更新数量变更记录
	 * 
	 * @param carModelCode
	 *            车型码
	 * @param dwbs
	 *            工厂
	 * @param mtree
	 *            集合//mtree中每个数组的元素为linkid、父件id、修改后的数量
	 * @throws QMException 
	 */
	public void saveChangeContentUpdate(String carModelCode, String dwbs,
			Collection mtree) throws QMException {
		//需要查询数量有变化的数据，不是所有的都存
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
						// 看是否比较前后更改的数量，如果比较使用注释掉的代码，不比较使用未注释方法
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
								// 如果不比较，那么就需要创建两条记录，一条删除，一条增加
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
					// 关闭Statement
					st.close();
					// 关闭数据库连接
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
	
	/**保存删除变更记录
	 * @param carModelCode 车型码
	 * @param dwbs 工厂
	 * @param mdtree //mdtree中每个数组的元素为linkid
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
							// 如果不比较，那么就需要创建两条记录，一条删除，一条增加
							saveChangeContent(carModelCode, dwbs, parentPart,
									childPart, "", beforquantity,
									beforquantity, "D");
						} catch (QMException e) {
							e.printStackTrace();
						}
					}
				}
				// 关闭Statement
				st.close();
				// 关闭数据库连接
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
	/** 删除变更记录
	 * @param carid 车型的partid
	 */
	public String deleteBOMContent(String carid,String dwbs){
//		System.out.println("零件id===="+carid);
		try {
			PersistService pservice = (PersistService) EJBServiceHelper
					.getService("PersistService");
			QMPartIfc partifc = (QMPartIfc)pservice.refreshInfo(carid);
			String carModelCode = partifc.getPartNumber();
//			System.out.println("车型编号===="+carModelCode);
			Connection conn = null;
			Statement st = null;
			conn = PersistUtil.getConnection();
			st = conn.createStatement();
			String sql = "delete BOMChangeContent where carModelCode='"+carModelCode+"' and dwbs='"
					+dwbs+"'";
//			System.out.println("sql===="+sql);
			st.executeQuery(sql);
			// 关闭Statement
			st.close();
			// 关闭数据库连接
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
	 * 查找符合规则的一级件集合
	 * 
	 * @param linkcoll
	 *            设计BOM一级件集合
	 * @param dwbs
	 *            工厂
	 * @return zcArr 符合规则的一级件集合
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
					// 保存路线的集合
					HashMap submap = new HashMap();
					// 路线数组
					String[] subroutestr = new String[2];
					// 判断用户所属工厂，然后提取相应路线
					if (dwbs.equals("W34"))// 成都取二级路线
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
     * 是否是实体件
     * 参数，routeAsString 制造路线，assembStr装配路线
     * 第五位不是G，并且装配路线含总
     * @return boolean
     * @throws QMException
     */
     public boolean isEntity(QMPartIfc part,String routeAsString ,String assembStr)throws QMException{
        boolean result=false;
        //第5位为G并且装配路线为空并且制造路线含“用”
        if(part.getPartNumber().length()>5)
        if (!part.getPartNumber().substring(4, 5).equals("G"))
        {
        	if(assembStr.contains("总")){
                result=true;
            }
        }
        return result;
    }
	//CCEnd SS62
     //CCBegin SS63
     /**
 	 * 创建以"-ZC"或"-FC"结尾零件的路线
 	 * 
 	 * @param partID
 	 *            零件ID
 	 * @throws QMException
 	 */
 	public void createZCORFCTechnics(String partID) throws QMException {
 		long t1 = System.currentTimeMillis();
 		PersistService ps = (PersistService) EJBServiceHelper
 				.getPersistService();

 		PartServiceHelper psh = new PartServiceHelper();
 		// 获取设计BOM结构
 		Vector vec = psh.getBOMList(partID,
 				"partNumber-0,partName-0,quantity-0,", "", "");
 		// 存储要创建路线的零件集合（零件值对象、路线集合）
 		Vector saveVec = new Vector();
 		// 路线表
 		TechnicsRouteListIfc tifc = new TechnicsRouteListInfo();
 		for (Iterator iter = vec.iterator(); iter.hasNext();) {
 			Object[] str = (Object[]) iter.next();
 			// 元素0为ID，元素4为编号
 			String partid = (String) str[0];
 			String partnumber = (String) str[4];
 			
 			//编号大于5位，并且倒数第四位是"J"或者"j"，最后三位为纯数字的时候才创建路线，否则不管
 			if (partnumber.length() > 5) {
 				//倒数第四位的字符
 				String final4 = partnumber.substring(partnumber.length()-4, partnumber.length()-3);
 				if ((final4.equals("J")||final4.equals("j"))&& partnumber.substring(4, 5).equals("G")) {
 					// 添加一个判断，如果-J后面的三位数字不是纯数字，那么不查询
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
 							// 截取的零件的编号
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
 							// master不为空的时候才去设置路线
 							if (pmaster != null) {
 								// System.out.println("存在此件！！！！");
 								// 查找最新的关联routelistpartlinkid
 								String linkid = searchLinkID(pmaster);
 								// System.out.println("linkid-----------------==========================="+linkid);
 								if (linkid != "") {
 									// 所有路线（每个元素是个数组，数组中数据分别为：制造路线、装配路线、是否主要路线）
 									Collection routecoll = getRoutes(linkid);
 									// 有路线的时候才去存放集合，否则不存（零件值对象、路线集合）
 									if (routecoll.size() > 0) {
 										Object[] result = new Object[3];
 										result[0] = subpart;
 										result[1] = routecoll;
 										saveVec.add(result);// 存到集合中
 									}
 								}
 							}
 						}
 					}
 				}
 			}
 		}

 		// 当集合大于0才创建路线表
 		if (saveVec.size() > 0) {
 			// 设计BOM车型值对象
 			QMPartIfc desPartIfc = (QMPartIfc) ps.refreshInfo(partID);
 			String desMasterID = desPartIfc.getMasterBsoID();
 			// 路线表编号
 			String technicsNumber = "intGYBOM" + desPartIfc.getPartNumber();
 			TechnicsRouteService trs = (TechnicsRouteService) EJBServiceHelper
 					.getService("TechnicsRouteService");
 			tifc = trs.findRouteListByNum(technicsNumber);
 			if (tifc == null) {
 				tifc = createRouteList(desPartIfc, desMasterID, saveVec);
 			}
 			// 调用零部件服务
 			StandardPartService sps = (StandardPartService) EJBServiceHelper
 					.getService("StandardPartService");

 			// 循环创建关联关系
 			for (Iterator iter = saveVec.iterator(); iter.hasNext();) {
 				Object[] str = (Object[]) iter.next();// （零件值对象、路线集合）
 				createListRoutePartLink(tifc, str, desPartIfc.getPartNumber(),
 						desPartIfc.getPartName());
 				// tifc.getProductID();
 			}
 		}
 		long t2 = System.currentTimeMillis();
 		System.out.println("搜索并创建所用时间======" + (t2 - t1) + " 毫秒");
 	}
 	//CCEnd SS63
 	//CCBegin SS34
 	/**过滤掉不需要版本的零件，返回新生成的零件编号
 	 * @param part 零件值对象
 	 * @param partnumber 带版本的零件编号
 	 * @return
 	 */
 	public String getPartNumber(QMPartIfc part,String partnumber){
 		String partnum = part.getPartNumber();//零件的编号
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
     * 排序方法
     * @param hehe Vector 基本信息处理完成的bom集合。
     * @param numSite int 零部件编号属性所在的位置。
     * @return Vector 排序后bom集合。
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
     * @param yid 原节点id
     * @param xid 替换节点id
     * @param carModelCode 车型
     * @param dwbs 工厂
     * @param parentid 父件id
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

			// 更新关联，子件id替换成下一版零部件id
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

				// 关闭Statement
				stmt.close();
				// 关闭数据库连接
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

			// 新增子件关联
			Collection mtree = new ArrayList();
			Vector linkvec = new Vector();
			mtree = getBomLinks(mtree, nnpart, carModelCode, dwbs, linkvec);
			// CCBegin SS15
			saveGYBom(mtree, true, dwbs);// SS21
			// CCEnd SS15

			// 获取当前零部件及其子件最新结构
			str = getGYBomAll(linkid, nnpart.getBsoID(), carModelCode, dwbs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
  	//CCEnd SS71
	// CCBegin SS70
	/**
	 * 判断数量是否相同
	 * @param dwbs 工厂
	 * @param linkid 关联id
	 * @param parentid 父件id
	 * @param afterquantity 数量
	 * @return
	 * @throws QMException
	 */
	public boolean isSameCount(String dwbs, String linkid, String parentid,
			String afterquantity) throws QMException {
		boolean result = false;
		// 需要查询数量有变化的数据，不是所有的都存
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
				// 关闭Statement
				st.close();
				// 关闭数据库连接
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
	 * 加锁零部件
	 * 
	 * @param partid
	 *            选中节点零件id
	 * @param dwbs
	 *            工厂
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
			// 更新当前节点加锁状态
			String sql = "update GYBomStructure set locker='" + userid
					+ "',bz1='red' where effectCurrent='0' and childPart='"
					+ partid + "' and dwbs = '" + dwbs + "'";
			int i = st.executeUpdate(sql);
			if (i > 0) {
				result = "[{" + "加锁成功" + "}]";
			} else {
				result = "[{" + "加锁失败" + "}]";
			}
			// 关闭Statement
			st.close();
			// 关闭数据库连接
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
	 * 解锁零部件
	 * 
	 * @param partid
	 *            选中节点零件id
	 * @param dwbs
	 *            工厂
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
				// 更新当前节点加锁状态
				String sql = "update GYBomStructure set locker='',bz1='red' where effectCurrent='0' and childPart='"
						+ partid + "' and dwbs = '" + dwbs + "'";
				int i = st.executeUpdate(sql);
				if (i > 0) {
					result = "[{" + "解锁成功" + "}]";
				} else {
					result = "[{" + "解锁失败" + "}]";
				}
				// 关闭Statement
				st.close();
				// 关闭数据库连接
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
	 * 查询加锁用户是否为当前用户
	 * 
	 * @return false 没有当前用户加锁的对象,true 有当前用户加锁的对象
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
			// 更新当前节点加锁状态
			String sql = "select count(*) from GYBomStructure where dwbs='"
					+ dwbs + "' and childPart='" + partid
					+ "' and effectCurrent='0' and locker is null";
			// int i = st.executeUpdate(sql);
			rs = st.executeQuery(sql);
			rs.next();
			int i = rs.getInt(1);
			System.out.println("i===="+i);
			// 如果i小于0，那么s返回0，证明没有加锁对象;
			// 如果i大于0，证明有加锁对象，继续执行程序，如果为当前用户加锁，返回2，否则返回1
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
			
			// 关闭Statement
			st.close();
			
			// 关闭数据库连接
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
	 * 生成驾驶室初始工艺BOM
	 */
	public void initGYJSSBom(String partID,String gyID,String dwbs,String newnumber) throws QMException
	{
		try
		{
			// 驾驶室逻辑总成
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
//					// 驾驶室
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
//			// 驾驶室
//			Iterator jssiter=jssparts.iterator();
//			ArrayList array = new ArrayList();//SS20
//			QMPartIfc jsssp=null;
//			while(jssiter.hasNext())
//			{
//				String[] jssstr1=(String[])jssiter.next();
//				jsssp=(QMPartIfc)ps.refreshInfo(jssstr1[0]);
//				String[] mt1=new String[]{gyID,jsssp.getBsoID(),jssstr1[1],gypart.getPartNumber(),dwbs,"0","","","个",
//					jssstr1[2]};
//				mtree.add(mt1);
//				mtree=getBomLinks(gyID,mtree,jsssp,jsssp.getPartNumber(),dwbs,linkvec,"jss",array, new ArrayList());//SS20//SS37
//			}
//			// 根据规则进行结构拆分
//			Collection ntree=chaifengybom(gyID,mtree);
//			// 根据规则对符合规则的件进行改名（另存为）
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
	/**获得5000010、5000020、5000030编码后缀
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
   	 //是否替换
   	 boolean isupdate = true;
		if (csvData == null || csvData.equals("")) {
			return "导入的文件为空！";
		}
		HashMap exitMap = new HashMap();//是否存在父件、子件、数量、都相同的件
		HashMap exitMapBOM = new HashMap();//是否存在父件 存在的bom
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
					String pnum=partInformation[0];// 父件编号
					String snum=partInformation[1];// 子件编号
					String unit=partInformation[2];// 单位
					String quantity=partInformation[3];// 使用数量
					//String subGroup=partInformation[4];;// 子组
					
//					System.out.println("parentNumber=="+pnum+","+snum+","+unit+","+quantity);
//					System.out.println("childNumber=="+snum);
//					System.out.println("dw=="+unit);
//					System.out.println("quantity=="+quantity);
                    //判断该父件是否存在变更,如果存在则不进行处理
					if(exitMap.containsKey(pnum))
						continue;
					// 检查4个属性，如果都是空，则不做导入也无需记录。
					if((pnum==null||pnum.equals(""))&&(snum==null||snum.equals(""))
						&&(unit==null||unit.equals(""))&&(quantity==null||quantity.equals("")))
					{
						// System.out.println("第"+i+"行没有导入数据，跳过！");
						continue;
					}
					// 检查4个属性是否为空，如果为空则记录日志，不做导入。
					if(pnum==null||pnum.equals("")||snum==null||snum.equals("")||unit==null
						||unit.equals("")||quantity==null||quantity.equals(""))
					{
						logbuffer(buffer,"第"+(i+1)+"行");
						if(pnum==null||pnum.equals(""))
						{
							logbuffer(buffer," 父件编号");
						}
						if(snum==null||snum.equals(""))
						{
							logbuffer(buffer," 子件编号");
						}
						if(unit==null||unit.equals(""))
						{
							logbuffer(buffer," 单位");
						}
						if(quantity==null||quantity.equals(""))
						{
							logbuffer(buffer," 使用数量");
						}
						logbuffer(buffer," 属性没有整理\n");
						continue;
					}
					
					// 根据编号获取父子零部件
					
					
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
								//说明版本不一致
								//查找是否在盆里，如果在盆里不处理
							//	System.out.println("父件为啥会进来呢===="+pnum);
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
									// TODO 自动生成 catch 块
									e.printStackTrace();
								}
								if(cappNumber.equals("")){
									logbuffer(buffer," 第"+(i+1)+"行");
									logbuffer(buffer," \n系统中该零件对应的发布源版本不匹配"+pnum);
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
							//版本不为空的才处理
				
							if(version!=null&&!ver.equals("")&&ver!=null){
								
//								说明版本不一致
								//查找是否在盆里，如果在盆里不处理
								 String sql1 = "select t.beforematerial from erppan t " +
								  "where t.aftermaterial = '"+snum+"'";
								// System.out.println("子件为啥会进来呢===="+sql1);
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
									// TODO 自动生成 catch 块
									e.printStackTrace();
								}
								if(cappNumber.equals("")){
									logbuffer(buffer," 第"+(i+1)+"行");
									logbuffer(buffer," snum+ \n系统中该零件对应的发布源版本不匹配\n");
								}
								//continue;
							}
						}
					}
					
					
					
					
					
					ppart=(QMPartIfc)partmap.get(pnum);
					spart=(QMPartIfc)partmap.get(snum);
					// 检查零部件是否存在，如果不存在则记录日志，不做导入。
					if(ppart==null||spart==null)
					{
						logbuffer(buffer," 第"+(i+1)+"行");
						if(ppart==null)
						{
							logbuffer(buffer," \n 父件"+pnum);
						}
						if(spart==null)
						{
							logbuffer(buffer," \n子件"+snum);
						}
						logbuffer(buffer," 系统无此件\n");
						continue;
					}
				
					ppart=(QMPartIfc)partmap.get(pnum);
					spart=(QMPartIfc)partmap.get(snum);

					if(ppart==null||spart==null)
					{
						logbuffer(buffer," 第"+(i+1)+"行");
						if(ppart==null)
						{
							logbuffer(buffer," \n 父件"+pnum);
						}
						if(spart==null)
						{
							logbuffer(buffer," \n子件"+snum);
						}
						logbuffer(buffer," 系统无此件\n");
						continue;
					}
					
					
				
					

					if(isupdate)
					{
						String exitstr="0";
						// 检查是否存在该工艺BOM，如果存在则记录日志，不做导入。
						if(exitMapBOM.containsKey(ppart.getPartNumber())){
							 exitstr="1";
						}else{
							 exitstr=ghelper.getExitBOM(ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs);
						}
					
						
						 //System.out.println(ppart.getPartNumber()+" eff:"+exitstr);
						// 创建生效结构
						if(exitstr.equals("0"))
						{
							
							String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
								"1","","","个",spart.getPartNumber(),""};
							//System.out.println("mt=="+mt);
							mtree.add(mt);
						}
						else // 删除未原有结构，创建新结构
						{
							// 如果父件的未生效结构已经删除过，则跳过，否则添加。
//							if(!deleteParts.contains(ppart.getBsoID()+",0"))
//							{
//								deleteParts.add(ppart.getBsoID()+",0");
//							}
//							String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
//	 								"1","","","个",spart.getPartNumber(),""};
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
						// 如果父件 已存在生效关联，则不需要进行导入保存。 父件已存在集合
						if(checkParentPartExit(ppart.getBsoID(),dwbs,"1"))
						{
							parentParts.add(pnum);
						}
						else
						{
							String[] mt=new String[]{ppart.getBsoID(),spart.getBsoID(),quantity,"",dwbs,
								"1","","","个",spart.getPartNumber(),""};
							mtree.add(mt);
						}
					}
				
				}
				// 每个导入文件，即一个车型进行一次存储。
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
    	   logbuffer(buffer,"系统中中已存在如下变更结构"+"\r\n");
    	   for(Iterator it=exitMap.keySet().iterator();it.hasNext();)
   		{
   			String number=(String)it.next();
   			
   			String EXITNUMBER=(String)exitMap.get(number);
    		  // String number = exitMap.keySet().toString();
    		   logbuffer(buffer,EXITNUMBER+"\r\n");
    	   }
       }
		logbuffer(buffer,"导入完毕。\n");
	

	return buffer.toString();
	}
    //CCEnd SS82
    /**
	 * 删除指定零部件的一级结构。 初始化工艺BOM前先清空该整车的一级未生效BOM，导入BOM也先清除一级未生效BOM
	 * 
	 * @param parentPart
	 *            父件
	 * @param dwbs
	 *            工厂
	 * @param eff
	 *            1 生效结构， 0未生效结构
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
	 * 校验工艺bom是否编辑路线并发布
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
	 * 校验工艺bom是否编辑路线并发布
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
		// 获取子件id和数量
		Vector subvec=findChildPart(id,carModelCode,dwbs,effect);
		QMPartIfc parentpart=(QMPartIfc)ps.refreshInfo(id);

		// 获取子件路线
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
				System.out.println("未找到子件id："+childPart);
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

		// 递归获取子件
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

			//如果制造路线为空则加入到集合
			if(zhizao.equals("")||(zhizao.length()==0))
			vec.add(new String[]{Integer.toString(level),pnum,part.getPartName(),str[1],zhizao,zhuangpei,parentpart.getPartNumber()});
		
			checkbianliBomList(vec,childPart,carModelCode,dwbs,level+1,zhizao,routemap);

			zhizao="";
			zhuangpei="";
		}
	}
	  //CCEnd SS80
}
