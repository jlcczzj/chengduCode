/**
 * SS1 如果装配路线为空，则取父件制造路线。 liunan 2012-7-10
 * SS2 处理制造路线不同路线的包含关系。 liunan 2012-11-12
 * SS3 定制统计报表时，如果选择了制造路线和装配路线属性，则出错。 liunan 2012-12-3
 * SS4 获取全路线时，为每个零部件得到一个最新路线（类似以前不获取全路线时）放到集合中，
 * 如果该零部件所有路线都是取消过的，那么得到的路线结果为空，这时就获取最新路线。因为这种情况是都取消过，但最新的又采用了。 liunan 2014-1-15
 * SS5 如果艺准中对一个零部件进行了多条路线编辑，处理重复的情况，避免后续过滤取消路线时判断出错。 liunan 2014-1-15
 * SS6 添加文件服务器分支逻辑。  jiahx 2013-01-17
 * SS7 修改没有读权限的零部件，报表时出现权限问题。 获取零部件添加权限过滤。 liunan 2014-4-18
 * SS8 逻辑总成数量报表中标准件应不带版本，标准件规则：Q、CQ、T打头的零件。 liunan 2014-9-23
 * SS9 增加“颜色件标识”属性 liuyang 2014-6-14
 * SS10 逻辑总成报表出不来数据，因为原来配置规范是工程视图，改为工艺视图后存在缓存，所以还按照工程视图查找零件 刘家坤 2014-11-18
 * SS11 逻辑总成报表出不来数据，因为原来如果是工程视图直接通过sql零件是工程视图的，因为解放现在增加了工艺视图
 * 所以将原来工程视图查询方法去掉 刘家坤 2014-11-21
 * SS12 路线空说明引起的报表异常，增加空判断。 liunan 2014-12-16
 * SS13 A004-2015-3182 零部件版本获取顺序规则：1、先取生产版本。2、再取发布源版本。3、最后取本身版本。有的话就不取下一个。 liunan 2015-8-24
 * SS14 A004-2015-3216 增加艺准说明（说明后内容）和状态的输出。 liunan 2015-11-10
 * SS15 A004-2015-3248 如果零件满足以下的条件，则报表中的零件不带版本 例如：1160011-71U/AL01/A，应为1160011-71U/AL01。
 * 零部件编号第一个“/”后为“*L01*”、“0”、“*0（1，2，3，4）”、、“ZBT”、“*(L)”、“AH”、“*J0*”、“*J1*”、“*-SF”、“BQ”和“*-ZC”的不加版本。 liunan 2015-12-3
 * SS16 逻辑总成报表，名称中含有逗号时，excel显示会窜行，将显示为中文逗号。 liunan 2016-1-21
 * SS17 A004-2017-3465 补全SS4内容，如果是多路线，则要取最新的多条路线返回。 liunan 2017-2-14
 * SS18 导出报表，备注信息窜行。 liunan 2017-11-20
 */

package com.jf.ejb.service;


import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.cappclients.capproute.web.ReportLevelOneUtil;
import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.config.util.ConfigSpecHelper;
import com.faw_qm.config.util.LatestConfigSpec;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.content.model.StreamDataInfo;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.content.util.StreamUtil;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocDependencyLinkInfo;
import com.faw_qm.epm.build.model.EPMBuildHistoryIfc;
import com.faw_qm.epm.epmdocument.model.EPMDocumentInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.framework.util.QMMessage;
import com.faw_qm.iba.definition.model.StringDefinitionIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartServiceHelper;
import com.faw_qm.part.util.Unit;
import com.faw_qm.part.util.WfUtil;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.PersistUtil;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.users.ejb.entity.Group;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.workflow.worklist.helper.GrantPowerHelper;


/**
 * <p>Title: 物料清单</p>
 * <p>Description: 零部件报表中转服务。</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: 启明</p>
 * @author liunan 2009-02-11
 * @version 1.0
 * @version 1.1 性能优化 liunan 2009-04-14
 */

public class JFServiceEJB
    extends BaseServiceImp
{
  /**序列化ID*/
  static final long serialVersionUID = 1L;

  private String RESOURCE = "com.faw_qm.part.util.PartResource";

  //iba每次查数据库的数量，经测试机测试最优数。
  private static int basenum = 100;

  //route每次查询数据库的数量，经测试机测试最优数。
  private static int routebasenum = 100;//10;

  //记录调用in查询的次数
  private static int subCount=0;

  private static long subTimeGong = 0;

  private static long subTimeOther = 0;

  private static String viewName = "";
  
//CCBegin by leixiao 2010-3-1 解放图纸及文档附件导出  
  private  String ibaid = "";
//CCEnd by leixiao 2010-3-1 解放图纸及文档附件导出
  
  //CCBegin SS6
  private static boolean fileVaultUsed = (RemoteProperty.getProperty(
          "registryFileVaultStoreMode", "true")).equals("true");
  //CCEnd SS6

  public String getServiceName()
  {
    return "JFService";
  }

    /**
     * 此方法在当初专为解放定制的方法基础上进行的优化，主要修改了获得子件的getUsesPartIfcs方法；
     * iba和route的获得方式。由逐个获得改成整理获得。
     * 产品中存在的该方法依然保留，以备使用。
     * 根据指定零部件、定制属性列表和当前用户选择的筛选条件返回零部件的统计表信息。
     * 本方法调用了bianli()方法实现递归。
     * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
     * 1、如果不定制属性：
     * BsoID，号码、名称、是（否）可分（"true","false"）、数量（转化为字符型）、版本和视图；
     * 2、如果定制属性：
     * BsoID，号码、名称、是（否）可分、数量、其他定制属性。
     * @param partIfc :QMPartIfc 指定的零部件的值对象.
     * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版使用。
     * 所有属性都在此数组中，普通属性-0，iba属性-1，route属性-2。
     * @param affixAttrNames : String[] 定制未使用。
     * @param routeNames : String[] 定制未使用。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理；
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理；
     * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的对当前零部件的筛选条件。
     * @throws QMException
     * @return Vector
     * @author liunan 2008-08-01
     */
    public Vector setBOMList(QMPartIfc partIfc, String attrNames[],
                             String affixAttrNames[], String[] routeNames,
                             String source, String type,
                             PartConfigSpecIfc configSpecIfc) throws QMException {
      try
      {
      	System.out.println("\n\n\n"+"--------------------------------------------------");
      	SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
        String userName=sservice.getCurUserInfo().getUsersDesc();
      	System.out.println("用户 "+userName+" 开始生成零部件 "+partIfc.getPartNumber()+" 统计报表");
      	long t1 = System.currentTimeMillis();
        WfUtil wfutil = new WfUtil();
        PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
        Vector vector = new Vector();
        float parentQuantity = 1.0F;

        //记录数量和编号在排序中所在的位置。
        int quantitySite = 0;
        int numberSite = 0;
        String specAttr = "";
        for (int i = 0; i < attrNames.length; i++)
        {
          String attr = attrNames[i];
          attr = attr.trim();
          if (attr.endsWith("-1")||attr.endsWith("-2"))
          {
          	specAttr = specAttr+"=="+attr;
          }
          if (attr != null && attr.length() > 0)
          {
            if (attr.equals("quantity-0"))
            {
              quantitySite = 4 + i;
            }
            if (attr.equals("partNumber-0"))
            {
              numberSite = 4 + i;
            }
          }
        }
        System.out.println("此次统计报表选择的iba或路线有："+specAttr+"==");

        PartUsageLinkInfo partLinkInfo = new PartUsageLinkInfo();
        // iba的缓存，如果报表有iba属性，则第一项的key为ibaName，值为iba属性名集合，
        // 其后各项为具体零部件的iba值，key为partBsoID+ibaName，值为iba值。
        HashMap ibaTempMap = new HashMap();

        // route的缓存，如果报表有route属性，则第一项的key为routeName，值为route属性名集合，
        // 其后各项为具体零部件的route值，key为partMasterBsoID+routeName，值为route值。
        HashMap routeTempMap = new HashMap();
      //CCBegin by leixiao 2010-4-16 逻辑总成装配路线不取父件制造路线
        Vector islogic=new Vector();
        HashMap logicRoute=new HashMap();
      //CCEnd by leixiao 2010-4-16 
        // 保存整个报表中零部件集合，key为partMasterBsoID，值为父件partMasterBsoID。
        HashMap bomMap = new HashMap();
        

        // iba属性集合字符串。
        String ibaName = "";

        // route属性集合字符串。
        String routeName = "";

        //初始化，开始计数。
        subCount =0;

        for (int ibaj = 0; ibaj < attrNames.length; ibaj++)
        {
          String attrfull = attrNames[ibaj];
          if (attrfull.endsWith("-1"))
          {
          	if(ibaName.equals(""))
          	{
          	  ibaName = wfutil.getDisplayName(attrfull.substring(0, attrfull.length() - 2));
          	}
          	else
          	{
          		ibaName = ibaName +","+ wfutil.getDisplayName(attrfull.substring(0, attrfull.length() - 2));
          	}
          }
          if(attrfull.endsWith("-2"))
          {
          	if(routeName.equals(""))
          	{
          	  routeName = attrfull.substring(0, attrfull.length() - 2);
          	}
          	else
          	{
          		routeName = routeName +","+ attrfull.substring(0, attrfull.length() - 2);
          	}
          }
        }

        long t111 = System.currentTimeMillis();
        
      //CCBegin by liunan 2012-05-24 为了得到所有上级件的制造路线，需要记录零件在当前整车下所有父件的集合。
      HashMap allBomMap = new HashMap();
      //vector = bianli(partIfc, partIfc, attrNames, affixAttrNames, routeNames, source, type, configSpecIfc, parentQuantity, partLinkInfo, bomMap,islogic);
      vector = bianli(partIfc, partIfc, attrNames, affixAttrNames, routeNames, source, type, configSpecIfc, parentQuantity, partLinkInfo, bomMap ,allBomMap ,islogic);
      //CCEnd by liunan 2012-05-24
        
        long t222 = System.currentTimeMillis();
        System.out.println("新bianli方法 用时： "+(t222-t111)/1000+" 秒");

        //System.out.println("bomMap'size===="+bomMap.size()+"  and vector'size===="+vector.size());
        if(!ibaName.equals("")&&bomMap.size()>0)
        {
        	ibaTempMap.put("ibaName",ibaName.split(","));
        	long t12 = System.currentTimeMillis();
        	ibaTempMap = getIbaValue(ibaTempMap,bomMap);
        	long t21 = System.currentTimeMillis();
        	System.out.println("新 获取iba 方法 用时： "+(t21-t12)+" 毫秒，每次搜索iba的个数为： "+basenum+" 个");
        }
        if(!routeName.equals("")&&bomMap.size()>0)
        {
        	routeTempMap.put("routeName",routeName.split(","));
        	long t1222 = System.currentTimeMillis();
        	        	
        	//CCBegin by liunan 2012-04-28 获取全部路线。
        	//routeTempMap = getRouteValue(routeTempMap,bomMap);
        	routeTempMap = getAllRouteValue(routeTempMap,bomMap);        	
        	//CCEnd by liunan 2012-04-28
        	
        	long t2221 = System.currentTimeMillis();
        	System.out.println("新 获取route 方法 用时： "+(t2221-t1222)+" 毫秒，每次搜索route的个数为： "+routebasenum+" 个");
        }
        Vector resultVector = new Vector();

        long t11 = System.currentTimeMillis();
        for (int i = 0; i < vector.size(); i++)
        {
          Object[] temp1 = (Object[]) vector.elementAt(i);
          for (int j = 0; j < temp1.length; j++)
          {
            if (temp1[j] == null) {
              temp1[j] = "";
            }
          }
          //CCBegin by liunan 2009-04-05
          //从缓存中得到iba或route放到相应位置。
          if(!ibaName.equals("")||!routeName.equals(""))
          {
          	for (int k = 0; k < attrNames.length; k++)
          	{
          		String attrfull = attrNames[k];
          		if (attrfull.endsWith("-1"))
          		{
          			String curIbaKey = attrfull.substring(0, attrfull.length() - 2);
          			curIbaKey = temp1[0]+wfutil.getDisplayName(curIbaKey);
          			//System.out.println("in getattrfull curIbaKey============"+curIbaKey);
          			if(ibaTempMap.containsKey(curIbaKey))
          			{
          				temp1[4 + k] = ibaTempMap.get(curIbaKey).toString();
          			}
          		}
          		else if (attrfull.endsWith("-2"))
          		{
          			String[] idStr = (String[])bomMap.get(temp1[0]);
          			String curRouteKey = idStr[0]+attrfull.substring(0, attrfull.length() - 2);
          			String curRouteName = attrfull.substring(0, attrfull.length() - 2);
          			//System.out.println("in getattrfull curRouteKey============"+curRouteKey);
          			if(routeTempMap.containsKey(curRouteKey))
          			{
          				String curRouteValue = routeTempMap.get(curRouteKey).toString();          				
          				//如果装配为空，则用父件的制造路线。
          				if(curRouteName.equals("装配路线")&&(curRouteValue==null||curRouteValue.equals("")||curRouteValue.equals("/")))
          				{
          				//CCBegin by leixiao 2010-4-16
          					if(routeTempMap.containsKey(idStr[1]+"制造路线"))
          					{
          						//装配路线为空时取父件制造路线的第一个单位
          						String fuzhizhao=routeTempMap.get(idStr[1]+"制造路线").toString();
          						if(fuzhizhao.indexOf("--")!=-1){
          						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("-"));          					   
          					    }
              					if(islogic.contains(temp1[0])){//如果是逻辑总成不取父件制造         						
              						logicRoute.put(idStr[0].toString(), fuzhizhao);
              					}
              					else if(routeTempMap.get(idStr[0]+"制造路线").toString().indexOf("用")!=-1){
              						//制造路线包含用时，不取父件的的制造作为装配
              					}
              					else{
              						temp1[4 + k]=fuzhizhao;	
              					}          					
          				  }
          					else{
          						if(logicRoute.containsKey(idStr[1].toString())&&!islogic.contains(temp1[0])&&routeTempMap.get(idStr[0]+"制造路线").toString().indexOf("用")==-1){
          							temp1[4 + k]=logicRoute.get(idStr[1].toString());
          						}
          					}
//          					CCEnd by leixiao 2010-4-16
          				}
          				
          				//CCBegin by liunan 2012-04-28 获取全部路线。
          				//CCBegin SS3
          				//else if(curRouteName.equals("装配路线")&&curRouteValue.indexOf("无")!=-1)
          				else if(curRouteName.equals("装配路线")&&curRouteValue.indexOf("无")!=-1&&allBomMap.get(temp1[0])!=null)
          				//CCEnd SS3
          				{
          					String[] idallStr = (String[])allBomMap.get(temp1[0]);
          					String pid = idallStr[1];
          					String temppid[] = pid.split(";");
          					temp1[4 + k]=curRouteValue;
          					for(int ii = 0 ; ii< temppid.length; ii++)
          					{
          						String ppartid = temppid[ii];
          					if(routeTempMap.containsKey(ppartid+"制造路线"))
          					{
          						// 装配路线为空时取父件制造路线的第一个单位
          						String fuzhizhao=routeTempMap.get(ppartid+"制造路线").toString();
          						if(fuzhizhao.indexOf("--")!=-1){
          						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("-"));          					   
          					    }  
          					    
          						if(fuzhizhao.indexOf("/")!=-1){
          						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("/"));          					   
          					    }  
          					    
              					if(islogic.contains(temp1[0])){//如果是逻辑总成不取父件制造  
              						logicRoute.put(idStr[0].toString(), fuzhizhao);//逻辑总成的装配路线
              					}
              					else if(routeTempMap.get(idStr[0]+"制造路线").toString().indexOf("用")!=-1){
              						//制造路线包含用时，不取父件的的制造作为装配
              					}
              					else{
              						
              						if(temp1[4 + k].toString().indexOf(fuzhizhao)<0)
              							{
              								temp1[4 + k]=temp1[4 + k]+"/"+fuzhizhao;
              							}
              						
              					}          					
          				  }
          					else{
          						if(logicRoute.containsKey(idStr[1].toString())&&!islogic.contains(temp1[0])&&routeTempMap.get(idStr[0]+"制造路线").toString().indexOf("用")==-1){//其父件在逻辑总成集成中有内容
          							//System.out.println("logicRoute.get(idStr[1].toString())====="+logicRoute.get(idStr[1].toString()));
          							
          							if(temp1[4 + k].toString().indexOf(logicRoute.get(idStr[1].toString()).toString())<0)
          							{
          								temp1[4 + k]=temp1[4 + k]+"/"+logicRoute.get(idStr[1].toString()).toString();
          							}
          						}
          					}
          					
          				}
          				
          						temp1[4 + k]=temp1[4 + k].toString().replaceAll("/无","").replaceAll("无/","").replaceAll("无","");
          					//System.out.println("temp[4 + k]1==="+temp1[4 + k]);          						
          				}
          				//CCEnd by liunan 2012-04-28
          				else
          				{
          					//CCBegin SS3
          					//temp1[4 + k] = curRouteValue;
          					temp1[4 + k] = curRouteValue.toString().replaceAll("/无","").replaceAll("无/","").replaceAll("无","");
          					//CCEnd SS3
          				}
          			}
          			//如果没有装配路线，也用父件的制造路线。
          			else
          			{
          			//CCBegin by leixiao 2010-4-16       				
          				if(curRouteName.equals("装配路线")&&routeTempMap.containsKey(idStr[1]+"制造路线"))
          				{
      						//CCBegin by leixiao 2010-4-16 装配路线为空时取父件制造路线的第一个单位
      						String fuzhizhao=routeTempMap.get(idStr[1]+"制造路线").toString();
      						if(fuzhizhao.indexOf("--")!=-1){
      						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("-"));          					   
      					    }
          					if(islogic.contains(temp1[0])){//如果是逻辑总成不取父件制造         						
          						logicRoute.put(idStr[0].toString(), fuzhizhao);
          					}
          					else{
          						temp1[4 + k]=fuzhizhao;	
          					}
          				}
      					else if(curRouteName.equals("装配路线")){
      						if(logicRoute.containsKey(idStr[1].toString())&&!islogic.contains(temp1[0])){
      							temp1[4 + k]=logicRoute.get(idStr[1].toString());
      						}      						
      					}
          			//CCEnd by leixiao 2010-4-16
          			}
          			
          			
          			
          		}
          	}
          }
          //CCEnd by liunan 2009-04-05
          String partNumber1 = (String) temp1[numberSite];
          boolean flag = false;
          for (int j = 0; j < resultVector.size(); j++)
          {
            Object[] temp2 = (Object[]) resultVector.elementAt(j);
            String partNumber2 = (String) temp2[numberSite];
            if (partNumber1.equals(partNumber2))
            {
              flag = true;
              if (quantitySite > 0)
              {
                float float1 = (new Float(temp1[quantitySite].toString())).floatValue();
                float float2 = (new Float(temp2[quantitySite].toString())).floatValue();
                Float dikeflinshi = new Float(float1 + float2);
                temp1[quantitySite] = new Integer(dikeflinshi.intValue());
              }
              resultVector.setElementAt(temp1, j);
              break;
            }
          }
          if (!flag)
          {
            resultVector.addElement(temp1);
          }
        }
        //此处修改了排序方法的位置，以前放在结果集合合并之前，导致排序方法用时过长，现改为合并后再排序。
        long t22 = System.currentTimeMillis();
        System.out.println("新获取iba和route方法 用时： "+(t22-t11)+" 毫秒");
        long tsort1 = System.currentTimeMillis();
        resultVector = sortTongJiVectorNew(resultVector, partIfc, numberSite);
        long tsort2 = System.currentTimeMillis();
        System.out.println("排序 方法 用时： "+(tsort2-tsort1)+" 毫秒，排序后集合个数vecotr.size==="+resultVector.size());

        bomMap = null;
        ibaTempMap = null;
        routeTempMap = null;

        boolean flag1 = false;
        boolean flag2 = false;
        String source1 = partIfc.getProducedBy().toString();
        String type1 = partIfc.getPartType().toString();
        if (source != null && source.length() > 0)
        {
          if (source.equals(source1))
          {
            flag1 = true;
          }
        }
        else
        {
          flag1 = true;
        }
        if (type != null && type.length() > 0)
        {
          if (type.equals(type1))
          {
            flag2 = true;
          }
        }
        else
        {
          flag2 = true;
        }
        if (!flag1 || !flag2)
        {
          resultVector.removeElementAt(0);
        }
        else
        {
          Object[] firstObj = (Object[]) resultVector.elementAt(0);
          if (quantitySite > 0)
          {
            firstObj[quantitySite] = "";
          }
          resultVector.setElementAt(firstObj, 0);
        }
        Vector result = new Vector();
        for (int i = 0; i < resultVector.size(); i++)
        {
          Object[] element = (Object[]) resultVector.elementAt(i);
          //如果没选择来源、类型，则不再往下循环处理。
          //CCBegin by liunan 2009-03-10
          if(flag1&&flag2)
          {
          	result.addElement(element);
          	continue;
          }
          //CCEnd by liunan 2009-03-10
          QMPartIfc onePart = (QMPartIfc) pService.refreshInfo( (String) element[
              0]);
          boolean flag11 = false;
          boolean flag22 = false;
          if (source != null && source.length() > 0)
          {
            if (onePart.getProducedBy().toString().equals(source))
            {
              flag11 = true;
            }
          }
          else
          {
            flag11 = true;
          }
          if (type != null && type.length() > 0)
          {
            if (onePart.getPartType().toString().equals(type))
            {
              flag22 = true;
            }
          }
          else
          {
            flag22 = true;
          }
          if (flag11 && flag22)
          {
            result.addElement(element);
          }
        }
        long t1122 = System.currentTimeMillis();
        System.out.println("过滤来源类型 方法 用时： "+(t1122-t22)+" 毫秒");
        Vector firstElement = new Vector();
        firstElement.addElement("");
        firstElement.addElement("");
        firstElement.addElement("");
        String ssss = QMMessage.getLocalizedMessage(RESOURCE, "isHasSubParts", null);
        firstElement.addElement(ssss);

        for (int i = 0; i < attrNames.length; i++)
        {
          String attr = attrNames[i];
          if (attr.endsWith("-0"))
          {
            ssss = QMMessage.getLocalizedMessage(RESOURCE,attr.substring(0,attr.length() - 2), null);
            firstElement.addElement(ssss);
          }
          else if (attr.endsWith("-1"))
          {
            firstElement.addElement(wfutil.getDisplayName(attr.substring(0,attr.length() - 2)));
          }
          else if (attr.endsWith("-2"))
          {
            firstElement.addElement(attr.substring(0, attr.length() - 2));
          }
        }

        String[] tempArray = new String[firstElement.size()];
        for (int i = 0; i < firstElement.size(); i++)
        {
          tempArray[i] = (String) firstElement.elementAt(i);
        }
        result.insertElementAt(tempArray, 0);

        long t2 = System.currentTimeMillis();
        if(subTimeGong>0)
          System.out.println("当前视图为："+viewName+"， 所有查找子件方法subTimeGong用时："+subTimeGong+" 毫秒");
        else if(subTimeOther>0)
          System.out.println("当前视图为："+viewName+"， 所有查找子件方法subTimeOther用时："+subTimeOther+" 毫秒");
        else
          System.out.println("当前视图为："+viewName);
        System.out.println("结束零部件 "+partIfc.getPartNumber()+" 报表，共有 "+(result.size()-1)+ " 个零部件， iba搜索 "+subCount+" 次， 用户 "+userName+" 用时： "+(t2-t1)/1000+" 秒");
        System.out.println("--------------------------------------------------"+"\n\n\n");
subCount = 0;
subTimeGong = 0;
subTimeOther = 0;
        return result;
      }
      catch (Exception e)
      {
        e.printStackTrace();
        throw new QMException(e.toString());
      }
    }

    /**
     * 第二次优化。
     * 此方法是在当初专为解放定制的方法基础上进行修改的，优化了getUsesPartIfcs方法，
     * 修改了iba特性与路线信息的获得方式，不在bianli中获取，而是bianli完成后统一获得，并赋值。
     * 产品中存在的该方法依然保留，以备使用。
     * 本方法被setBOMList所调用，实现递归调用的功能。
     * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
     * 1、如果不定制属性：
     * BsoID，号码、名称、是（否）可分（"true","false"）、数量（转化为字符型）、版本和视图；
     * 2、如果定制属性：
     * BsoID，号码、名称、是（否）可分、数量、其他定制属性。
     * 其他定制属性中如果存在iba和route则全是""
     * @param partProductIfc :QMPartIfc 父件值对象。
     * @param partIfc :QMPartIfc 指定的零部件的值对象.
     * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
     * 定制开发时所有属性都放在这个数组中，只是后缀不同，普通属性-0，iba属性-1，路线属性-2。
     * @param affixAttrNames : String[] 定制开发未使用。
     * @param routeNames : String[] 定制开发未使用。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理；
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理；
     * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的筛选条件。
     * @param parentQuantity :float 使用了当前部件的部件被最顶级部件所使用的数量；
     * @param partLinkIfc :PartUsageLinkIfc 连接当前部件和其父部件的关联关系值对象；
     * @param bomMap ：HashMap 报表中涉及的所有零部件集合，key为partBsoID，
     * 值为二维数组，第一个元素是masterBsoID，第二个元素是父件masterBsoID。
     * @throws QMException
     * @return Vector
     * @author liunan 2009-04-05
     */     

    private Vector bianli(QMPartIfc partProductIfc, QMPartIfc partIfc,
                          String attrNames[], String affixAttrNames[],
                          String[] routeNames, String source, String type,
                          PartConfigSpecIfc configSpecIfc, float parentQuantity,
                          //CCBegin by liunan 2012-05-24 为了得到所有上级件的制造路线，需要记录零件在当前整车下所有父件的集合。
                          //PartUsageLinkIfc partLinkIfc, HashMap bomMap,Vector islogic) throws
                          PartUsageLinkIfc partLinkIfc, HashMap bomMap, HashMap allBomMap,Vector islogic) throws
                          //CCEnd by liunan 2012-05-24
        QMException {
      //如果bomMap为空则表示第一次进入bianli方法，加入bomMap的值也就是根节点。
      if(bomMap==null||bomMap.size()==0)
      {
      	//随意为父件masterid赋的"root"，用的时候缓存中没有"root"，也就不会得到值。
      	bomMap.put(partIfc.getBsoID(),new String[]{partIfc.getMasterBsoID(),"root"});
      }
      Vector resultVector = new Vector();
      Vector hegeVector = new Vector();
      long t1 = System.currentTimeMillis();
      Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
      long t2 = System.currentTimeMillis();
      //System.out.println("本次getUsesPartIfcs 所用时间："+(t2-t1)+" 毫秒");
      if (collection != null && collection.size() > 0)
      {
        Object[] resultArray = new Object[collection.size()];
        collection.toArray(resultArray);
        for (int i = 0; i < resultArray.length; i++)
        {
          boolean isHasSubParts = true;
          Object obj = resultArray[i];
          if (obj instanceof Object[])
          {
            Object[] obj1 = (Object[]) obj;
            if (obj1[1] instanceof QMPartIfc)
            {
            	//将partIfc的子件按格式保存到缓存bomMap中。
            	if(!bomMap.containsKey(((QMPartIfc)obj1[1]).getBsoID()))
            	{
            	  bomMap.put(((QMPartIfc)obj1[1]).getBsoID(),new String[]{((QMPartIfc)obj1[1]).getMasterBsoID(),partIfc.getMasterBsoID()});
                //CCBegin by liunan 2012-05-24 为了得到所有上级件的制造路线，需要记录零件在当前整车下所有父件的集合。
            	  allBomMap.put(((QMPartIfc)obj1[1]).getBsoID(),new String[]{((QMPartIfc)obj1[1]).getMasterBsoID(),partIfc.getMasterBsoID()});
                //CCEnd by liunan 2012-05-24
              }
              
              //CCBegin by liunan 2012-05-24 为了得到所有上级件的制造路线，需要记录零件在当前整车下所有父件的集合。
              else
              {
              	String idTemp[] = (String[])allBomMap.get(((QMPartIfc)obj1[1]).getBsoID());
              	String parentPartStr = idTemp[1];
              	if(parentPartStr.indexOf(partIfc.getMasterBsoID())<0)
              	{
              		allBomMap.put(((QMPartIfc)obj1[1]).getBsoID(),new String[]{((QMPartIfc)obj1[1]).getMasterBsoID(),parentPartStr+";"+partIfc.getMasterBsoID()});
              	}
              }
              //CCEnd by liunan 2012-05-24
              
              if (isHasSubParts)
              {
                hegeVector.addElement( (Object[]) obj);
              }
            }
          }
        }
      }

      Object[] tempArray = null;

      //记录数量和编号在排序中所在的位置。
      int numberSite = 0;
      for (int i = 0; i < attrNames.length; i++)
      {
        String attr = attrNames[i];
        attr = attr.trim();
        if (attr != null && attr.length() > 0)
        {
          if (attr.equals("partNumber-0"))
          {
            numberSite = 4 + i;
          }
        }
      }
      tempArray = new Object[4 + attrNames.length];
      tempArray[2] = new Integer(numberSite);
      tempArray[0] = partIfc.getBsoID();
      tempArray[1] = new Integer(1);
      String isHasSubParts1 = QMMessage.getLocalizedMessage(RESOURCE, "false", null);
      if (hegeVector != null && hegeVector.size() > 0)
      {
        isHasSubParts1 = QMMessage.getLocalizedMessage(RESOURCE, "true", null);
      }
      tempArray[3] = isHasSubParts1;
      
      //CCBegin by leixiao 2020-4-16    
      if (partIfc.getPartType().toString().equalsIgnoreCase("Logical"))
      {
       islogic.add(partIfc.getBsoID());
      }

      //CCEnd by leixiao 2020-4-16 

      for (int j = 0; j < attrNames.length; j++)
      {
        String attrfull = attrNames[j];
        if (attrfull.endsWith("-0"))
        {
          String attr = attrfull.substring(0, attrfull.length() - 2);
          if (attr != null && attr.length() > 0)
          {
            String temp = tempArray[1].toString();
            if (attr.equals("defaultUnit") && !temp.equals("0"))
            {
              Unit unit = partLinkIfc.getDefaultUnit();
              if (unit != null)
              {
                tempArray[4 + j] = unit.getDisplay();
              }
              else {
                tempArray[4 + j] = "";
              }
            }
            else if (attr.equals("quantity"))
            {
                //需要先判断partLinkIfc是否是持久化的，如果不是，parentQuantity = 1.0
                //如果是:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
            	  //CCBeginby leixiao 2009-2-13 原因：解放升级工艺路线,计算数量不对
                if (partLinkIfc == null ||!PersistHelper.isPersistent(partLinkIfc))
                {
                    parentQuantity = 1.0f;
                }
                else
                {
                    parentQuantity = parentQuantity *
                                     partLinkIfc.getQuantity();
                }
                //CCEndby leixiao 2009-2-13 原因：解放升级工艺路线,计算数量不对
           //   parentQuantity = partLinkIfc.getQuantity(); //parentQuantity*partLinkIfc.getQuantity();
              String tempQuantity = String.valueOf(parentQuantity);
              if (tempQuantity.endsWith(".0"))
              {
                tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
              }
              tempArray[4 + j] = tempQuantity;
            }
            else
            {
              attr = attr.substring(0, 1).toUpperCase() + attr.substring(1, attr.length());
              attr = "get" + attr;
              try
              {
                Class partClass = Class.forName("com.faw_qm.part.model.QMPartInfo");
                Method method1 = partClass.getMethod(attr, null);
                Object obj = method1.invoke(partIfc, null);
                if (obj == null)
                {
                  tempArray[4 + j] = "";
                }
                else
                {
                  if (obj instanceof String)
                  {
                    String tempString = (String) obj;
                    if (tempString != null && tempString.length() > 0)
                    {
                      tempArray[4 + j] = tempString;
                    }
                    else
                    {
                      tempArray[4 + j] = "";
                    }
                  }
                  else
                  {
                    if (obj instanceof EnumeratedType)
                    {
                      EnumeratedType tempType = (EnumeratedType) obj;
                      if (tempType != null)
                      {
                        tempArray[4 + j] = tempType.getDisplay();
                      }
                      else {
                        tempArray[4 + j] = "";
                      }
                    }
                  }
                }
              }
              catch (Exception ex) {
                ex.printStackTrace();
                throw new QMException(ex);
              }
            }
          }
        }
        //iba直接设置为""，稍后整体获取iba后再赋值。
        else if (attrfull.endsWith("-1"))
        {
          	tempArray[4 + j] = "";
        }
        //route直接设置为""，稍后整体获取route后再赋值。
        else if (attrfull.endsWith("-2"))
        {
          	tempArray[4 + j] = "";
        }
      }

      resultVector.addElement(tempArray);
      if (hegeVector != null && hegeVector.size() > 0)
      {
        for (int j = 0; j < hegeVector.size(); j++)
        {
          Object obj = hegeVector.elementAt(j);
          if (obj instanceof Object[])
          {
            Object[] obj2 = (Object[]) obj;
            if (obj2[0] != null && obj2[1] != null)
            {
              Vector tempVector = bianli(partProductIfc, (QMPartIfc) obj2[1],
                                         attrNames, affixAttrNames, routeNames,
                                         source, type, configSpecIfc, parentQuantity,
                                         //CCBegin by liunan 2012-05-24 为了得到所有上级件的制造路线，需要记录零件在当前整车下所有父件的集合。
                                         //(PartUsageLinkIfc) obj2[0], bomMap,islogic);
                                         (PartUsageLinkIfc) obj2[0], bomMap, allBomMap,islogic);
                                         //CCEnd by liunan 2012-05-24
              for (int k = 0; k < tempVector.size(); k++)
              {
                resultVector.addElement(tempVector.elementAt(k));
              }
            }
          }
        }

      }
      return resultVector;
    }


    /**
     * 第一次优化。
     * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法，
     * 产品中存在的该方法依然保留，以备使用。
     * 本方法被setBOMList所调用，实现递归调用的功能。
     * 在返回值vector中，每个元素为一个有多个字符串的数组，分别保存零部件的下列信息：
     * 1、如果不定制属性：
     * BsoID，号码、名称、是（否）可分（"true","false"）、数量（转化为字符型）、版本和视图；
     * 2、如果定制属性：
     * BsoID，号码、名称、是（否）可分、数量、其他定制属性。

     * @param partIfc :QMPartIfc 指定的零部件的值对象.
     * @param attrNames :String[] 定制要输出的普通属性集合；如果为空，则按标准版输出。
     * @param affixAttrNames : String[] 定制的要输出的扩展属性的集合，可以为空。
     * @param routeNames : String[] 定制的要输出的工艺路线名的集合，可以为空。
     * @param source :String 指定的零部件的来源，用来筛选此来源的零部件；如果为空，不用处理；
     * @param type :String 零部件的类型，用来筛选此类型的零部件；如果为空，不用处理；
     * @param configSpecIfc :PartConfigSpecIfc 当前客户端所使用的筛选条件。
     * @param parentQuantity :float 使用了当前部件的部件被最顶级部件所使用的数量；
     * @param partLinkIfc :PartUsageLinkIfc 连接当前部件和其父部件的关联关系值对象；
     * @throws QMException
     * @return Vector
     * @author liunan 2009-03-10
     */
    private Vector bianli(QMPartIfc partProductIfc, QMPartIfc partIfc,
                          String attrNames[], String affixAttrNames[],
                          String[] routeNames, String source, String type,
                          PartConfigSpecIfc configSpecIfc, float parentQuantity,
                          PartUsageLinkIfc partLinkIfc, HashMap ibaTempMap,
                          HashMap routeTempMap) throws
        QMException {

      //    PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
      Vector resultVector = new Vector();
      Vector hegeVector = new Vector();
      Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
      WfUtil wfutil = new WfUtil();
      if (collection != null && collection.size() > 0)
      {
        Object[] resultArray = new Object[collection.size()];
        collection.toArray(resultArray);
        for (int i = 0; i < resultArray.length; i++)
        {
          boolean isHasSubParts = true;
          Object obj = resultArray[i];
          if (obj instanceof Object[])
          {
            Object[] obj1 = (Object[]) obj;
            if (obj1[1] instanceof QMPartIfc)
            {
              if (isHasSubParts)
              {
                hegeVector.addElement( (Object[]) obj);
              }
            }
          }
        }
      }
      if(ibaTempMap!=null&&ibaTempMap.size()==1)
      {
        ibaTempMap = getIbaValue(ibaTempMap,partIfc,hegeVector);
      }
      else if(ibaTempMap!=null&&ibaTempMap.size()>1&&hegeVector.size()>0)
      {
      	ibaTempMap = getIbaValue(ibaTempMap,partIfc,hegeVector);
      }
      long t1 = System.currentTimeMillis();
      if(routeTempMap!=null&&routeTempMap.size()==1)
      {
        routeTempMap = getRouteValue(routeTempMap,partIfc,hegeVector);
      }
      else if(routeTempMap!=null&&routeTempMap.size()>1&&hegeVector.size()>0)
      {
      	routeTempMap = getRouteValue(routeTempMap,partIfc,hegeVector);
      }
      long t2 = System.currentTimeMillis();
      //System.out.println("getRouteValue'time is"+(t2-t1)+"毫秒");

      Object[] tempArray = null;
      boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
      boolean affixAttrNullTrueFlag = affixAttrNames == null ||
          affixAttrNames.length == 0;
      boolean routeNullTrueFlag = routeNames == null || routeNames.length == 0;

      //记录数量和编号在排序中所在的位置。
      int numberSite = 0;
      for (int i = 0; i < attrNames.length; i++)
      {
        String attr = attrNames[i];
        attr = attr.trim();
        if (attr != null && attr.length() > 0)
        {
          if (attr.equals("partNumber-0"))
          {
            numberSite = 4 + i;
          }
        }
      }
      tempArray = new Object[4 + attrNames.length];
      tempArray[2] = new Integer(numberSite);
      tempArray[0] = partIfc.getBsoID();
      tempArray[1] = new Integer(1);
      String isHasSubParts1 = QMMessage.getLocalizedMessage(RESOURCE, "false", null);
      if (hegeVector != null && hegeVector.size() > 0)
      {
        isHasSubParts1 = QMMessage.getLocalizedMessage(RESOURCE, "true", null);
      }
      tempArray[3] = isHasSubParts1;
      //获得零部件的工艺路线。
      TechnicsRouteService trService = (TechnicsRouteService) EJBServiceHelper.
          getService("TechnicsRouteService");

      String parentMakeRouteFirst = null;

      for (int j = 0; j < attrNames.length; j++)
      {
        String attrfull = attrNames[j];
        if (attrfull.endsWith("-0"))
        {
          String attr = attrfull.substring(0, attrfull.length() - 2);
          if (attr != null && attr.length() > 0)
          {
            String temp = tempArray[1].toString();
            if (attr.equals("defaultUnit") && !temp.equals("0"))
            {
              Unit unit = partLinkIfc.getDefaultUnit();
              if (unit != null)
              {
                tempArray[4 + j] = unit.getDisplay();
              }
              else
              {
                tempArray[4 + j] = "";
              }
            }
            else if (attr.equals("quantity"))
            {
                //需要先判断partLinkIfc是否是持久化的，如果不是，parentQuantity = 1.0
                //如果是:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
            	  //CCBeginby leixiao 2009-2-13 原因：解放升级工艺路线,计算数量不对
                if (partLinkIfc == null ||!PersistHelper.isPersistent(partLinkIfc))
                {
                    parentQuantity = 1.0f;
                }
                else
                {
                    parentQuantity = parentQuantity *
                                     partLinkIfc.getQuantity();
                }
                //CCEndby leixiao 2009-2-13 原因：解放升级工艺路线,计算数量不对
           //   parentQuantity = partLinkIfc.getQuantity(); //parentQuantity*partLinkIfc.getQuantity();
              String tempQuantity = String.valueOf(parentQuantity);
              if (tempQuantity.endsWith(".0"))
              {
                tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
              }
              tempArray[4 + j] = tempQuantity;
            }
            else
            {
              attr = attr.substring(0, 1).toUpperCase() + attr.substring(1, attr.length());
              attr = "get" + attr;
              try
              {
                Class partClass = Class.forName("com.faw_qm.part.model.QMPartInfo");
                Method method1 = partClass.getMethod(attr, null);
                Object obj = method1.invoke(partIfc, null);
                if (obj == null)
                {
                  tempArray[4 + j] = "";
                }
                else
                {
                  if (obj instanceof String)
                  {
                    String tempString = (String) obj;
                    if (tempString != null && tempString.length() > 0)
                    {
                      tempArray[4 + j] = tempString;
                    }
                    else
                    {
                      tempArray[4 + j] = "";
                    }
                  }
                  else
                  {
                    if (obj instanceof EnumeratedType)
                    {
                      EnumeratedType tempType = (EnumeratedType) obj;
                      if (tempType != null)
                      {
                        tempArray[4 + j] = tempType.getDisplay();
                      }
                      else
                      {
                        tempArray[4 + j] = "";
                      }
                    }
                  }
                }
              }
              catch (Exception ex)
              {
                ex.printStackTrace();
                throw new QMException(ex);
              }
            }
          }
        }
        else if (attrfull.endsWith("-1"))
        {
          String ibaName = attrfull.substring(0, attrfull.length() - 2);
          ibaName = partIfc.getBsoID().toString()+wfutil.getDisplayName(ibaName);
          //System.out.println("in getattrfull ibaName============"+ibaName);
          if(ibaTempMap.containsKey(ibaName))
          {
          	tempArray[4 + j] = ibaTempMap.get(ibaName).toString();
          }
          else
          {
          	tempArray[4 + j] = "";
          }
        }
        else if (attrfull.endsWith("-2")) {
          String routeName = partIfc.getMasterBsoID().toString()+attrfull.substring(0, attrfull.length() - 2);
          //System.out.println("in getattrfull routeName============"+routeName);
          if(routeTempMap.containsKey(routeName))
          {
          	tempArray[4 + j] = routeTempMap.get(routeName).toString();
          }
          else
          {
          	tempArray[4 + j] = "";
          }
        }
      }

      resultVector.addElement(tempArray);
      if (hegeVector != null && hegeVector.size() > 0)
      {
        for (int j = 0; j < hegeVector.size(); j++)
        {
          Object obj = hegeVector.elementAt(j);
          if (obj instanceof Object[])
          {
            Object[] obj2 = (Object[]) obj;
            if (obj2[0] != null && obj2[1] != null)
            {
              Vector tempVector = bianli(partProductIfc, (QMPartIfc) obj2[1],
                                         attrNames, affixAttrNames, routeNames,
                                         source, type, configSpecIfc, parentQuantity,
                                         (PartUsageLinkIfc) obj2[0], ibaTempMap, routeTempMap);
              for (int k = 0; k < tempVector.size(); k++)
              {
                resultVector.addElement(tempVector.elementAt(k));
              }
            }
          }
        }

      }
      return resultVector;
    }

    /**
     * 第一次优化。liunan 2009-03-10
     * 获取iba值的方法，每次bianli方法进行一次搜索，将本次bianli中的part和得到的子件集合传过来，
     * 对此集合中零部件进行iba搜索，如果ibaTempMap中有的零部件，不再重新搜索。
     * @param ibaTempMap HashMap iba值的缓存，key为partBsoID+iba名称，值就是这个part对应ibaname的值。
     * @param partIfc QMPartIfc 本次bianli的件，也就是集合hegeVector的父件。
     * @param hegeVector Vector 本次bianlipartIfc的子件集合。
     * @return HashMap 同ibaTempMap每个零部件iba的值的集合。
     */
    private HashMap getIbaValue(HashMap ibaTempMap, QMPartIfc partIfc, Vector hegeVector)
    {
    	//System.out.println("选择了iba，才走到这里的！！！");
    	//零部件bsoid集合
    	String partID[] = new String[hegeVector.size()+1];
    	partID[0] = partIfc.getBsoID();
    	for(int i=0;i<hegeVector.size();i++)
    	{
          Object obj = hegeVector.elementAt(i);
          if (obj instanceof Object[])
          {
            Object[] obj2 = (Object[]) obj;
            if (obj2[0] != null && obj2[1] != null)
            {
            	QMPartIfc part = (QMPartIfc)obj2[1];
            	partID[i+1] = part.getBsoID();
            }
          }
    	}
    	String[] ibaNames = (String[])ibaTempMap.get("ibaName");
    	for(int j=0;j<ibaNames.length;j++)
    	{
              //组织好in方法所要的partid数组后进行一次query搜索。
    		Collection coll = getIbaValueBySql(ibaNames[j],partID);
    		Iterator iba=coll.iterator();
        while(iba.hasNext())
        {
        	StringValueIfc s=(StringValueIfc)iba.next();
        	String keyStr = s.getIBAHolderBsoID()+ibaNames[j].toString();
        	//System.out.println("==================================="+keyStr);
                //如果缓存中不存在，则添加。
        	if(!ibaTempMap.containsKey(keyStr))
        	{
        	  ibaTempMap.put(s.getIBAHolderBsoID()+ibaNames[j].toString(),s.getValue());
          }
        }
    	}
    	return ibaTempMap;
    }


    /**
     * 第二次优化。CCBegin by liunan 2009-04-05
     * 获取iba值的方法，整个bianli循环结束后执行的此方法，组装in的query方法，每次搜索100个，
     * 这个数是在解放测试机上多次测试结果得出的效率最佳个数。
     * @param ibaTempMap HashMap iba值的缓存，key为partBsoID+iba名称，值为该零部件ibaname对应的值。
     * @param bomMap HashMap 所有报表中涉及的零部件的集合。key为partBsoID，
     * 值为二维数组，第一个元素是partMasterBsoID，第二个元素是其父件的partMasterBsoID。
     * @return HashMap 返回赋值后的ibaTempMap。
     */
    private HashMap getIbaValue(HashMap ibaTempMap, HashMap bomMap)
    {
    	//System.out.println("选择了iba，才走到这里的！！！");
    	//零部件bsoid集合
    	String[] partID;
    	//记录当前向partid集合中添加的零部件个数
    	int i = 0;
    	//调数据库次数
    	int k = 0;
    	if (bomMap.size() > basenum)
    	{
    		partID = new String[basenum];
    	}
    	else
    	{
    		partID = new String[bomMap.size()];
    	}
    	String[] ibaNames = (String[])ibaTempMap.get("ibaName");
    	Set idSet = bomMap.keySet();
    	Iterator iter = idSet.iterator();
    	while(iter.hasNext())
    	{
    		partID[i] = (String)iter.next();
    		i++;
    		// 以100个零部件为单位进行一次查询，得到该零部件的iba属性
    		if (i == basenum || !iter.hasNext())
    		{
    			for(int j=0;j<ibaNames.length;j++)
    			{
    				long t1 = System.currentTimeMillis();
    				Collection coll = getIbaValueBySql(ibaNames[j],partID);
    				long t2 = System.currentTimeMillis();
    				//System.out.println("搜索一次iba方法，零部件数为 "+i+" 个，用时： "+(t2-t1)/1000+" 秒");
    				Iterator iba=coll.iterator();
    				while(iba.hasNext())
    				{
    					StringValueIfc s=(StringValueIfc)iba.next();
    					String keyStr = s.getIBAHolderBsoID()+ibaNames[j].toString();
    					//System.out.println("==================================="+keyStr);
    					if(!ibaTempMap.containsKey(keyStr))
    					{
    						ibaTempMap.put(s.getIBAHolderBsoID()+ibaNames[j].toString(),s.getValue());
    					}
    				}
    			}
    			// 把i置0，重新开始计数
    			i = 0;
    			// 调数据库次数
    			k++;
    			// bomMap集合中余下零部件数量
    			int next = bomMap.size() - basenum * k;
    			//System.out.println("第"+k+"次-------余下数量＝"+next);
    			if (next > basenum)
    			{
						partID = new String[basenum];
					}
					else if(next>0)
					{
						partID = new String[next];
					}
				}
			}
    	return ibaTempMap;
    }
    //CCEnd by liunan 2009-04-05

    /**
     * liunan 2009-03-10
     * 用in方法构造的query，减少iba搜索时间。
     * @param ibaName String iba特性名称。
     * @param partID String[] in的数组，元素都是partBsoID。
     * @return Collection query得到的结果集合。
     */
    private Collection getIbaValueBySql(String ibaName, String[] partID)
    {
    	//System.out.println("getIbaValueBySql'ibaName ======== "+ibaName);
    	subCount++;
      try
      {
        PersistService pservice = (PersistService) EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery("StringValue");
        int u = query.appendBso("StringDefinition", false);
        QueryCondition qc1 = new QueryCondition("definitionBsoID", "bsoID");
        query.addCondition(0, u, qc1);
        query.addAND();
        QueryCondition qc2 = new QueryCondition("displayName", " = ", ibaName);
        query.addCondition(u, qc2);
        query.addAND();
        QueryCondition qc3 = new QueryCondition("iBAHolderBsoID", QueryCondition.IN, partID);
        query.addCondition(0, qc3);
        return (Collection)pservice.findValueInfo(query, false);
      }
      catch(Exception e)
      {
        e.printStackTrace();
        return null;
      }
    }


    /**
     * CCBegin by liunan 2009-04-05
     * 第二次优化。
     * 获得路线信息，整体处理，in的sql方式。每次搜索20个，
     * 这个数是在解放测试机上多次测试结果得出的效率最佳个数。
     * @param routeTempMap HashMap route值的缓存，key为partMasterBsoID+route名称，值就是这个part对应routename的值。
     * @param bomMap HashMap 所有报表中涉及的零部件的集合。key为partBsoID，
     * 值为二维数组，第一个元素是partMasterBsoID，第二个元素是其父件的partMasterBsoID。
     * @return HashMap 返回routeTempMap。
     */
    private HashMap getRouteValue(HashMap routeTempMap, HashMap bomMap)
    {
    	//零部件masterbsoid集合
    	String[] routeNames = (String[])routeTempMap.get("routeName");
    	int routeNameSize = routeNames.length;
    	String partMasterID = "";
    	boolean isHasRouteList = false;
    	//记录当前向partid集合中添加的零部件个数
    	int i = 0;
    	//调数据库次数
    	int k = 0;
    	for(int a=0;a<routeNameSize;a++)
    	{
    		if(routeNames[a].equals("艺准编号")||routeNames[a].equals("备注"))
    		{
    			isHasRouteList = true;
    		}
    	}
    	// 以20个零部件为单位进行一次查询，得到该零部件的route属性

    	//构造sql，得到最新路线。
    	//CCBegin by liunan 2009-06-10 添加TechnicsRouteList不为临准、technicsroute不是取消状态，去掉alterstatus='1'条件。
    	//String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid from technicsroutebranch branch,(select a.leftbsoid,a.routeid,a.rightbsoid from listroutepartlink a ,(select max(MODIFYTIME) maxtime,rightbsoid from listroutepartlink where rightbsoid in (";
    	//String sql2 = ") and alterstatus='1' and routeid is not null group by rightbsoid) b where a.rightbsoid=b.rightbsoid and a.MODIFYTIME=b.maxtime and a.alterstatus='1') link where branch.routeid=link.routeid";
    //CCBegin SS9
    	//String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid from technicsroutebranch branch,(select a.leftbsoid,a.routeid,a.rightbsoid from listroutepartlink a ,(select max(aa.MODIFYTIME) maxtime,aa.rightbsoid from (select a.MODIFYTIME,a.routeid,a.rightbsoid from listroutepartlink a,technicsroute b, technicsroutelist c where a.rightbsoid in (";
    	String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid,link.colorflag from technicsroutebranch branch,(select a.leftbsoid,a.routeid,a.rightbsoid from listroutepartlink a ,(select max(aa.MODIFYTIME) maxtime,aa.rightbsoid from (select a.MODIFYTIME,a.routeid,a.rightbsoid from listroutepartlink a,technicsroute b, technicsroutelist c where a.rightbsoid in (";
    	//CCEnd SS9
    	//CCBegin by liunan 2012-04-25 李萍提出个人资料夹的艺准不取其路线。
    	//String sql2 = ") and a.routeid=b.bsoid and b.modefyIdenty!='Coding_221664' and a.leftbsoid=c.bsoid and c.routeliststate!='临准') aa where aa.routeid is not null group by aa.rightbsoid) b where a.rightbsoid=b.rightbsoid and a.MODIFYTIME=b.maxtime) link ,technicsroute route where branch.routeid=link.routeid and route.modefyIdenty!='Coding_221664' and route.bsoid=branch.routeid";
    	String sql2 = ") and a.routeid=b.bsoid and b.modefyIdenty!='Coding_221664' and a.leftbsoid=c.bsoid and c.routeliststate!='临准' and c.owner is null) aa where aa.routeid is not null group by aa.rightbsoid) b where a.rightbsoid=b.rightbsoid and a.MODIFYTIME=b.maxtime) link ,technicsroute route where branch.routeid=link.routeid and route.modefyIdenty!='Coding_221664' and route.bsoid=branch.routeid";
    	//CCEnd by liunan 2012-04-25
    	//CCEnd by liunan 2009-06-10

    	Connection conn = null;
      Statement stmt =null;
      ResultSet rs=null;
  	  try
  	  {
  	  	PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
  	    //搜索表，根据艺准的masterid搜索数量。
    	  conn = PersistUtil.getConnection();
      	stmt = conn.createStatement();
      	//System.out.println(sql1+partMasterID+sql2);
      	Set idSet = bomMap.keySet();
      	Iterator iter = idSet.iterator();
      	while(iter.hasNext())
      	{
      		String partKey = (String)iter.next();
      		String masterID[] = (String[])bomMap.get(partKey);
      		if(partMasterID.equals(""))
      		{
      			partMasterID = "'" + masterID[0] + "'";
      		}
      		else
      		{
      			partMasterID = partMasterID + ",'" + masterID[0] + "'";
      		}
      		i++;
      		if (i == routebasenum || !iter.hasNext())
      		{
      			long t1 = System.currentTimeMillis();
      			rs = stmt.executeQuery(sql1+partMasterID+sql2);
    				long t2 = System.currentTimeMillis();
    				//System.out.println("搜索一次route方法，零部件数为 "+i+" 个，用时： "+(t2-t1)+" 毫秒");
      			while(rs.next())
      			{
      				String keyName = "";
      				TechnicsRouteListIfc routelist = null;
      				if(isHasRouteList)
      				{
      					routelist = (TechnicsRouteListIfc) pService.refreshInfo(rs.getString(3), false);
      				}
      				for(int ii=0;ii<routeNameSize;ii++)
      				{
      					if(routeNames[ii].equals("制造路线"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(routeTempMap.containsKey(keyName))
      						{
      							String value = dealRouteStr(routeTempMap.get(keyName).toString(),getZhiZaoRoute(rs.getString(1)));
      							routeTempMap.remove(keyName);
      							routeTempMap.put(keyName,value);
      						}
      						else
      						{
      							routeTempMap.put(keyName,getZhiZaoRoute(rs.getString(1)));
      						}
      					}
      					else if(routeNames[ii].equals("装配路线"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(routeTempMap.containsKey(keyName))
      						{
      							String value = dealRouteStr(routeTempMap.get(keyName).toString(), getZhuangPeiRoute(rs.getString(1)));
      							routeTempMap.remove(keyName);
      							routeTempMap.put(keyName,value);
      						}
      						else
      						{
      							routeTempMap.put(keyName,getZhuangPeiRoute(rs.getString(1)));
      						}
      					}
      					else if(routeNames[ii].equals("艺准编号"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							routeTempMap.put(keyName,routelist.getRouteListNumber());
      						}
      					}
      					//CCBegin SS14
      					else if(routeNames[ii].equals("艺准说明"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							String des = routelist.getRouteListDescription();
      							if(des==null)
      							{
      								des = "";
      							}
      							des = des.trim().replaceAll("\n|\r", "");
      							if(des.indexOf("说明：")!=-1)
      							{
      								des = des.substring(des.indexOf("说明："),des.length());
      							}
      							routeTempMap.put(keyName, des);
      						}
      					}
      					else if(routeNames[ii].equals("艺准状态"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							routeTempMap.put(keyName,routelist.getLifeCycleState().getDisplay());
      						}
      					}
      					//CCEnd SS14
      						//CCBegin SS9
      					else if(routeNames[ii].equals("颜色件标识"))
      					{
      						
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							String color="";
      							if(rs.getString(4)!=null&&rs.getString(4).equals("1")){
      								color="是";
      							}
      							routeTempMap.put(keyName,color);
      						}
      					}
      					//CCEnd SS9
      					else if(routeNames[ii].equals("备注"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							//CCBegin SS18
      							//routeTempMap.put(keyName,routelist.getRouteListDescription());
      							routeTempMap.put(keyName,routelist.getRouteListDescription().trim().replaceAll("\n|\r", ""));
      							//CCEnd SS18
      						}
      					}
      				}
      			}
    				long t3 = System.currentTimeMillis();
    				//System.out.println("处理一次route方法，用时： "+(t3-t2)+" 毫秒");

      			// 把i置0，重新开始计数
      			i = 0;
      			// 调数据库次数
      			k++;
      			// bomMap集合中余下零部件数量
      			//int next = bomMap.size() - routebasenum * k;
      			//System.out.println("第"+k+"次-------余下数量＝"+next);
      			partMasterID = "";
				  }
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
    	return routeTempMap;
    }
    //CCEnd by liunan 2009-04-05

    /**
     * 第一次优化。liunan 2009-03-10
     * 获取route值的方法，每次bianli方法进行一次搜索，将本次bianli中的part和得到的子件集合传过来，
     * 对此集合中零部件进行route搜索，如果routeTempMap中有的零部件，不再重新搜索。
     * @param routeTempMap HashMap route值的缓存，key为partMasterBsoID+route名称，值就是这个part对应routename的值。
     * @param partIfc QMPartIfc 本次bianli的件，也就是集合hegeVector的父件。
     * @param hegeVector Vector 本次bianlipartIfc的子件集合。
     * @return HashMap 同routeTempMap每个零部件iba的值的集合。
     */
    private HashMap getRouteValue(HashMap routeTempMap, QMPartIfc partIfc, Vector hegeVector)
    {
    	//零部件masterbsoid集合
    	String[] routeNames = (String[])routeTempMap.get("routeName");
    	int routeNameSize = routeNames.length;
    	String partMasterID = "";
    	boolean flag1 = true;
    	boolean isHasZhuangPei = false;
    	boolean isHasRouteList = false;
    	for(int a=0;a<routeNameSize;a++)
    	{
    		if(routeNames[a].equals("装配路线"))
    		{
    			isHasZhuangPei = true;
    		}
    		else if(routeNames[a].equals("艺准编号")||routeNames[a].equals("备注"))
    		{
    			isHasRouteList = true;
    		}
    	}
    	for(int jj=0;jj<routeNameSize;jj++)
      {
        if(routeTempMap.containsKey(partIfc.getMasterBsoID()+routeNames[jj]))
        {
          flag1 = false;
          continue;
        }
      }
      if(flag1)
      {
    	  partMasterID = "'"+partIfc.getMasterBsoID()+"'";
      }
    	for(int i=0;i<hegeVector.size();i++)
    	{
          Object obj = hegeVector.elementAt(i);
          if (obj instanceof Object[])
          {
            Object[] obj2 = (Object[]) obj;
            if (obj2[0] != null && obj2[1] != null)
            {
            	QMPartIfc part = (QMPartIfc)obj2[1];
            	boolean flag = false;
            	for(int j=0;j<routeNameSize;j++)
            	{
            	  if(routeTempMap.containsKey(part.getMasterBsoID()+routeNames[j]))
              	{
              		flag = true;
            	  	continue;
            	  }
              }
              if(flag)
              {
              	continue;
              }
              if(partMasterID.equals(""))
              {
              	partMasterID = "'"+part.getMasterBsoID()+"'";
              }
              else
              {
            	  partMasterID = partMasterID+",'"+part.getMasterBsoID()+"'";
            	}
            }
          }
    	}

    	if(partMasterID.equals(""))
    	{
    		return routeTempMap;
    	}

    	//构造sql，得到最新路线。
    	String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid from technicsroutebranch branch,(select a.leftbsoid,a.routeid,a.rightbsoid from listroutepartlink a ,(select max(MODIFYTIME) maxtime,rightbsoid from listroutepartlink where rightbsoid in (";
    	String sql2 = ") and alterstatus='1' and routeid is not null group by rightbsoid) b where a.rightbsoid=b.rightbsoid and a.MODIFYTIME=b.maxtime) link where branch.routeid=link.routeid";

    	Connection conn = null;
      Statement stmt =null;
      ResultSet rs=null;
  	  try
            {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
  	    //搜索表，根据艺准的masterid搜索数量。
    	  conn = PersistUtil.getConnection();
      	stmt = conn.createStatement();
      	//System.out.println(sql1+partMasterID+sql2);
      	rs = stmt.executeQuery(sql1+partMasterID+sql2);
      	while(rs.next())
        {
        	String keyName = "";
        	TechnicsRouteListIfc routelist = null;
        	if(isHasRouteList)
        	{
        		routelist = (TechnicsRouteListIfc) pService.refreshInfo(rs.getString(3));
        	}
        	for(int ii=0;ii<routeNameSize;ii++)
        	{
        		if(routeNames[ii].equals("制造路线"))
        		{
        			keyName = rs.getString(2)+routeNames[ii];
        			if(routeTempMap.containsKey(keyName))
            	{
                  String value = routeTempMap.get(keyName).toString()+"/"+getZhiZaoRoute(rs.getString(1));
        	        routeTempMap.remove(keyName);
                  routeTempMap.put(keyName,value);
        	    }
        	    else
        	    {
        	    	routeTempMap.put(keyName,getZhiZaoRoute(rs.getString(1)));
            	}
        		}
        		else if(routeNames[ii].equals("装配路线"))
        		{
        			keyName = rs.getString(2)+routeNames[ii];
        			if(routeTempMap.containsKey(keyName))
            	{
                  String value = routeTempMap.get(keyName).toString()+"/"+getZhuangPeiRoute(rs.getString(1));
        	        routeTempMap.remove(keyName);
                  routeTempMap.put(keyName,value);
        	    }
        	    else
        	    {
        	    	routeTempMap.put(keyName,getZhuangPeiRoute(rs.getString(1)));
            	}
        		}
        		else if(routeNames[ii].equals("艺准编号"))
        		{
        			keyName = rs.getString(2)+routeNames[ii];
        			if(!routeTempMap.containsKey(keyName))
        	    {
        	    	routeTempMap.put(keyName,routelist.getRouteListNumber());
            	}
        		}
      					//CCBegin SS14
      					else if(routeNames[ii].equals("艺准说明"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							String des = routelist.getRouteListDescription();
      							if(des==null)
      							{
      								des = "";
      							}
      							des = des.trim().replaceAll("\n|\r", "");
      							if(des.indexOf("说明：")!=-1)
      							{
      								des = des.substring(des.indexOf("说明："),des.length());
      							}
      							routeTempMap.put(keyName, des);
      						}
      					}
      					else if(routeNames[ii].equals("艺准状态"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							routeTempMap.put(keyName,routelist.getLifeCycleState().getDisplay());
      						}
      					}
      					//CCEnd SS14
        		else if(routeNames[ii].equals("备注"))
        		{
        			keyName = rs.getString(2)+routeNames[ii];
        			if(!routeTempMap.containsKey(keyName))
        	    {
        	    	//CCBegin SS18
        	    	//routeTempMap.put(keyName,routelist.getRouteListDescription());
        	    	routeTempMap.put(keyName,routelist.getRouteListDescription().trim().replaceAll("\n|\r", ""));
        	    	//CCEnd SS18
            	}
        		}
        	}
        }

        //如果装配为空，则用父件的制造路线。
        if(isHasZhuangPei)
        {
        	for(int i=0;i<hegeVector.size();i++)
        	{
            Object obj = hegeVector.elementAt(i);
            if (obj instanceof Object[])
            {
              Object[] obj2 = (Object[]) obj;
              if (obj2[0] != null && obj2[1] != null)
              {
            	  QMPartIfc part = (QMPartIfc)obj2[1];
            	  if(routeTempMap.containsKey(part.getMasterBsoID()+"装配路线"))
            	  {
            	    String zproute = routeTempMap.get(part.getMasterBsoID()+"装配路线").toString();
            	    if(zproute==null||zproute.equals("")||zproute.equals("/"))
            	    {
        		    	  if(routeTempMap.containsKey(partIfc.getMasterBsoID()+"制造路线"))
        		    	  {
        			        routeTempMap.remove(part.getMasterBsoID()+"装配路线");
                      routeTempMap.put(part.getMasterBsoID()+"装配路线",routeTempMap.get(partIfc.getMasterBsoID()+"制造路线"));
                    }
                  }
                }
            	}
            }
          }
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

    	return routeTempMap;
    }

    /**
     * 路线单位字符串处理，
     * 1、不让"/"开头或结尾，
     * 2、路线重复时只显示一个的处理。
     * @param s1 String 已有路线串。
     * @param s2 String 当前路线。
     * @return String 处理好的路线字符串。
     */
    private String dealRouteStr(String s1, String s2)
    {
    	//System.out.println(s1+"====================="+s2);
    	if(s1.equals(""))
    	{
    		return s2;
    	}
      String temp[] = s1.split("/");
      for(int i = 0 ; i < temp.length ; i++)
      {
        if(temp[i].equals(s2))
        {
          return s1;
        }
      }
      if(s2.equals(""))
      {
        return s1;
      }
      else
      {
      	return s1+"/"+s2;
      }
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
    
    
    /**
     * 优化的排序方法。将集合中partNumber放到object数组中，采用Arrays自带的排序功能进行排序，
     * 得到结果后重新构造集合返回。
     * @param hehe Vector 基本信息处理完成的bom集合。
     * @param partIfc QMPartIfc 出报表的零部件。
     * @param numSite int 零部件编号属性所在的位置。
     * @return Vector 排序后bom集合。
     */
    private Vector sortTongJiVectorNew(Vector hehe, QMPartIfc partIfc, int numSite)
    {
      Vector result = new Vector();
      try
      {
      	String mainPartNum = partIfc.getPartNumber();
      	int heheSize = hehe.size();
      	Object[] aa = null;
      	Object[] bb = new Object[heheSize];
      	HashMap sortMap = new HashMap();
      	String partNum = "";
      	String partNum1 = "";
      	for (int i = 0; i < heheSize; i++)
      	{
      		aa = (Object[]) hehe.elementAt(i);
      		partNum = (String) aa[numSite];
      		bb[i] = partNum;
      		sortMap.put(bb[i],aa);
      	}
      	Arrays.sort(bb);
      	for(int j = 0; j < heheSize; j++)
      	{
      		if(!bb[j].equals(mainPartNum))
      		{
      			result.add(sortMap.get(bb[j]));
      		}
      	}
      	result.add(0, sortMap.get(mainPartNum));
      }
      catch(Exception e)
      {
      	e.printStackTrace();
      }
      return result;
    }

    /**
     * 排序方法
     * @param hehe Vector 基本信息处理完成的bom集合。
     * @param partIfc QMPartIfc 出报表的零部件。
     * @param numSite int 零部件编号属性所在的位置。
     * @return Vector 排序后bom集合。
     */
    private Vector sortTongJiVector(Vector hehe, QMPartIfc partIfc, int numSite) {

      Object[] mainObject = null;
      String mainPartNum = partIfc.getPartNumber();
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
          if (mainObject == null && partNum1.equals(mainPartNum))
          {
            mainObject = bb;
            hehe.remove(j);
            b = b - 1;
          }
          else
          {
            if (partNum.compareTo(partNum1) > 0)
            {
              hehe.setElementAt(bb, i);
              hehe.setElementAt(aa, j);
            }
            j++;
          }
        }
      }
      if (mainObject != null) {
        hehe.add(0, mainObject);
      }

      return hehe;
    }


  /**
   * 此方法在当初专为解放定制的方法基础上进行的优化，主要修改了获得子件的getUsesPartIfcs方法；
   * iba和route的获得方式。由逐个获得改成整理获得。
   * 产品中存在的该方法依然保留，以备使用。
   * 分级物料清单的显示。
   * 返回结果是vector,其中vector中的每个元素都是一个集合：
   * 0：当前part的BsoID；
   * 1：当前part所在的级别，转化为字符型；
   * 2：当前part的编号；
   * 3：当前part的名称；
   * 4：当前part被最顶层部件使用的数量，转化为字符型；
   * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
   *                如果定制了属性：按照所有定制的属性加到结果集合中。
   * 本方法调用了递归方法：
   * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
   * PartUsageLinkIfc partLinkIfc, int parentQuantity);
   * @param partIfc :QMPartIfc 最顶级的部件值对象。
   * @param attrNames :String[] 定制的属性，可以为空。普通属性-0，iba属性-1，route属性-2。
   * @param affixAttrNames : String[] 定制时未使用。
   * @param routeNames : String[] 定制时未使用。
   * @param configSpecIfc :PartConfigSpecIfc 配置规范。
   * @throws QMException
   * @return Vector
   * @author liunan 2009-04-13
   */
  //CCBegin by leixiao 2010-12-22  为生成逻辑总成数量报表修改方法,增加参数islogic,原方法调false
    public Vector setMaterialList(QMPartIfc partIfc, String attrNames[],
            String affixAttrNames[], String[] routeNames,
            PartConfigSpecIfc configSpecIfc) throws
QMException {
    	return setMaterialList(partIfc,attrNames,affixAttrNames,routeNames,configSpecIfc,false);
    }
  //CCEnd by leixiao 2010-12-22  
    /**
     * 此方法在当初专为解放定制的方法基础上进行的优化，主要修改了获得子件的getUsesPartIfcs方法；
     * iba和route的获得方式。由逐个获得改成整理获得。
     * 产品中存在的该方法依然保留，以备使用。
     * 分级物料清单的显示。
     * 返回结果是vector,其中vector中的每个元素都是一个集合：
     * 0：当前part的BsoID；
     * 1：当前part所在的级别，转化为字符型；
     * 2：当前part的编号；
     * 3：当前part的名称；
     * 4：当前part被最顶层部件使用的数量，转化为字符型；
     * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
     *                如果定制了属性：按照所有定制的属性加到结果集合中。
     * 本方法调用了递归方法：
     * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
     * PartUsageLinkIfc partLinkIfc, int parentQuantity);
     * @param partIfc :QMPartIfc 最顶级的部件值对象。
     * @param attrNames :String[] 定制的属性，可以为空。普通属性-0，iba属性-1，route属性-2。
     * @param affixAttrNames : String[] 定制时未使用。
     * @param routeNames : String[] 定制时未使用。
     * @param configSpecIfc :PartConfigSpecIfc 配置规范。
     * @throws QMException
     * @return Vector
     * @author leix 增加参数islogiclist
     */
  public Vector setMaterialList(QMPartIfc partIfc, String attrNames[],
                                String affixAttrNames[], String[] routeNames,
                                PartConfigSpecIfc configSpecIfc,boolean islogiclist) throws
      QMException {
    try {
    	//CCBegin SS10

    	PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
    	      	configSpecIfc=(PartConfigSpecIfc) ps.refreshInfo(configSpecIfc.getBsoID());
    	 //CCEnd SS10
    	  System.out.println("\n\n\n"+"--------------------------------------------------");
      	SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
        String userName=sservice.getCurUserInfo().getUsersDesc();
      	System.out.println("用户 "+userName+" 开始生成零部件 "+partIfc.getPartNumber()+" 分级报表");
      	long t1 = System.currentTimeMillis();
      WfUtil wfutil = new WfUtil();
      int level = 0;
      PartUsageLinkInfo partLinkInfo = new PartUsageLinkInfo();
      float parentQuantity = 1.0F;

      int quantitySite = 0;
      String specAttr = "";
      for (int i = 0; i < attrNames.length; i++)
      {
        String attr = attrNames[i];
        attr = attr.trim();
        if (attr.endsWith("-1")||attr.endsWith("-2"))
        {
        	specAttr = specAttr+"=="+attr;
        }
        if (attr != null && attr.length() > 0)
        {
          if (attr.equals("quantity-0"))
          {
            quantitySite = 4 + i;
          }
        }
      }
      System.out.println("此次分级报表选择的iba或路线有："+specAttr+"==");

      Vector vector = null;

        // iba的缓存，如果报表有iba属性，则第一项的key为ibaName，值为iba属性名集合，
        // 其后各项为具体零部件的iba值，key为partBsoID+ibaName，值为iba值。
        HashMap ibaTempMap = new HashMap();

        // route的缓存，如果报表有route属性，则第一项的key为routeName，值为route属性名集合，
        // 其后各项为具体零部件的route值，key为partMasterBsoID+routeName，值为route值。
        HashMap routeTempMap = new HashMap();
        //CCBegin by leixiao 2010-4-20 
        Vector islogic=new Vector();
        
        HashMap logicRoute=new HashMap();
        //CCEnd by leixiao 2010-4-20 
        // 保存整个报表中零部件集合，key为partMasterBsoID，值为父件partMasterBsoID。
        HashMap bomMap = new HashMap();

        // iba属性集合字符串。
        String ibaName = "";

        // route属性集合字符串。
        String routeName = "";

        subCount =0;

        for (int ibaj = 0; ibaj < attrNames.length; ibaj++)
        {
          String attrfull = attrNames[ibaj];
          if (attrfull.endsWith("-1"))
          {
          	if(ibaName.equals(""))
          	{
          	  ibaName = wfutil.getDisplayName(attrfull.substring(0, attrfull.length() - 2));
          	}
          	else
          	{
          		ibaName = ibaName +","+ wfutil.getDisplayName(attrfull.substring(0, attrfull.length() - 2));
          	}
          }
          if(attrfull.endsWith("-2"))
          {
          	if(routeName.equals(""))
          	{
          	  routeName = attrfull.substring(0, attrfull.length() - 2);
          	}
          	else
          	{
          		routeName = routeName +","+ attrfull.substring(0, attrfull.length() - 2);
          	}
          }
        }

        long t111 = System.currentTimeMillis();
        ////CCBegin by leixiao 2010-12-22 为逻辑总成数量报表,增加参数islogiclist 
      //CCBegin by liunan 2012-05-24 为了得到所有上级件的制造路线，需要记录零件在当前整车下所有父件的集合。
      HashMap allBomMap = new HashMap();
      //vector = fenji(partIfc, partIfc, attrNames, affixAttrNames, routeNames, configSpecIfc, level, partLinkInfo, parentQuantity, "",parentQuantity,bomMap,islogic,islogiclist);
      vector = fenji(partIfc, partIfc, attrNames, affixAttrNames, routeNames, configSpecIfc, level, partLinkInfo, parentQuantity, "",parentQuantity,bomMap,allBomMap,islogic,islogiclist);
      //CCEnd by liunan 2012-05-24
    //CCEnd by leixiao 2010-12-22
      long t222 = System.currentTimeMillis();
        System.out.println("新fenji方法 用时： "+(t222-t111)/1000+" 秒");
        //System.out.println("bomMap'size===="+bomMap.size()+"  and vector'size===="+vector.size());
        System.out.println("bomMap'size===="+bomMap.size()+"  and allBomMap'size===="+allBomMap.size());

        if(!ibaName.equals("")&&bomMap.size()>0)
        {
        	ibaTempMap.put("ibaName",ibaName.split(","));
        	long t12 = System.currentTimeMillis();
        	ibaTempMap = getIbaValue(ibaTempMap,bomMap);
        	long t21 = System.currentTimeMillis();
        	System.out.println("新 获取iba 方法 用时： "+(t21-t12)+" 毫秒，每次搜索iba的个数为： "+basenum+" 个");
        }
        //CCBegin by leixiao 2010-12-22 零件编号后带汽研发布源版本程序 2800G17-48A/A
        if(islogiclist){
        	ibaTempMap.put("ibaName",new String[]{"发布源版本"});
        	ibaTempMap = getIbaValue(ibaTempMap,bomMap);	
        }
        //CCEnd by leixiao 2010-12-22 零件编号后带汽研发布源版本程序 2800G17-48A/A
        if(!routeName.equals("")&&bomMap.size()>0)
        {
        	routeTempMap.put("routeName",routeName.split(","));
        	long t1222 = System.currentTimeMillis();
        	
        	//CCBegin by liunan 2012-04-28 获取全部路线。
        	//routeTempMap = getRouteValue(routeTempMap,bomMap);
        	routeTempMap = getAllRouteValue(routeTempMap,bomMap);        	
        	//CCEnd by liunan 2012-04-28
        	
        	long t2221 = System.currentTimeMillis();
        	System.out.println("新 获取route 方法 用时： "+(t2221-t1222)+" 毫秒，每次搜索route的个数为： "+routebasenum+" 个");
        }

        long ta11 = System.currentTimeMillis();
      if (vector != null && vector.size() > 0)
      {
        Object first[] = (Object[]) vector.elementAt(0);
        if (quantitySite > 0)
        {
          first[quantitySite] = "";
        }
        vector.setElementAt( ( (Object) (first)), 0);
      }
      Vector firstElement = new Vector();
      firstElement.addElement("");
      firstElement.addElement("");
      firstElement.addElement("");
      String ssss = QMMessage.getLocalizedMessage(RESOURCE, "level", null);
      firstElement.addElement(ssss);

      for (int i = 0; i < attrNames.length; i++)
      {
        String attr = attrNames[i];
        if (attr.endsWith("-0"))
        {
          ssss = QMMessage.getLocalizedMessage(RESOURCE,attr.substring(0,attr.length() - 2), null);
          firstElement.addElement(ssss);
        }
        else if (attr.endsWith("-1"))
        {
          firstElement.addElement(wfutil.getDisplayName(attr.substring(0,attr.length() - 2)));
        }
        else if (attr.endsWith("-2"))
        {
          firstElement.addElement(attr.substring(0, attr.length() - 2));
        }
      }
      firstElement.addElement("上一级父件编号");//yanqi-20061222-在分级表上增加父件号

      String[] tempArray = new String[firstElement.size()];
      for (int i = 0; i < firstElement.size(); i++) {
        tempArray[i] = (String) firstElement.elementAt(i);

      }
      vector.insertElementAt( ( (Object[]) (tempArray)), 0);

      long ta22 = System.currentTimeMillis();
      //System.out.println("获取首行属性方法 用时： "+(ta22-ta11)+" 毫秒");

      for (int i = 0; i < vector.size(); i++)
      {
        Object temp[] = (Object[]) vector.elementAt(i);
        for (int j = 0; j < temp.length; j++)
        {
          if (temp[j] == null)
          {
            temp[j] = "";
          }
          //CCBegin SS16
          if(j==5)
          {
          	temp[j] = temp[j].toString().replaceAll(",","，");
          }
          //CCEnd SS16
        }
        //CCBegin by leixiao 2010-12-22 零件编号后带汽研发布源版本程序 2800G17-48A/A
        if(islogiclist){
  			if(ibaTempMap.containsKey(temp[0]+"发布源版本"))
  			{
  				String version = (String)ibaTempMap.get(temp[0]+"发布源版本");
  				//System.out.println("--------number="+temp[4]);
  					String qiyanversion=version.substring(0,version.indexOf("."));
  					if(temp[4].toString().endsWith("$")){//如果是标准件,前面已经加了标识$,判断有$时,不加版本.
  						temp[4]=temp[4].toString().substring(0,temp[4].toString().length()-1);
  					}
  					else{
  						//CCBegin SS8
  						if(!temp[4].toString().startsWith("Q")&&!temp[4].toString().startsWith("CQ")&&!temp[4].toString().startsWith("T"))
  						//CCEnd SS8
  					temp[4]=temp[4]+"/"+qiyanversion;
  					}
  			}
  			//CCBegin SS13
  			else if(!((String)temp[0]).equals(""))
  			{
  				//CCBegin SS15
  				//零部件编号第一个“/”后为“*L01*”、“0”、“*0（1，2，3，4）”、、“ZBT”、“*(L)”、“AH”、“*J0*”、“*J1*”、“*-SF”、“BQ”和“*-ZC”的不加版本
  				boolean hasV = false;
  				//System.out.println("temp[4]==="+temp[4]);
  				if(temp[4].toString().indexOf("/")!=-1)
  				{
  					String str = temp[4].toString().substring(temp[4].toString().indexOf("/")+1);
  					//System.out.println("str==="+str);
  					if(str.indexOf("L01")!=-1||str.endsWith("0")||str.endsWith("1")||str.endsWith("2")||str.endsWith("3")||str.endsWith("4")||str.equals("ZBT")||str.endsWith("(L)")||str.equals("AH")||str.indexOf("J0")!=-1||str.indexOf("J1")!=-1||str.endsWith("-SF")||str.equals("BQ")||str.endsWith("-ZC"))
  					{
  						hasV = true;
  					}
  				}
  				//CCEnd SS15
  				QMPartIfc temppart = (QMPartIfc)ps.refreshInfo((String)temp[0],false);
  				String version = temppart.getVersionValue();
  					version=version.substring(0,version.indexOf("."));
  					if(temp[4].toString().endsWith("$")){//如果是标准件,前面已经加了标识$,判断有$时,不加版本.
  						temp[4]=temp[4].toString().substring(0,temp[4].toString().length()-1);
  					}
  					//CCBegin SS15
  					else if(hasV)
  					{
  						System.out.println("符合特殊要求不带版本："+temp[4]);
  					}
  					//CCEnd SS15
  					else{
  						//CCBegin SS8
  						if(!temp[4].toString().startsWith("Q")&&!temp[4].toString().startsWith("CQ")&&!temp[4].toString().startsWith("T"))
  						//CCEnd SS8
  					temp[4]=temp[4]+"/"+version;
  					}
  			}
  			//CCEnd SS13
        }
        //CCEnd by leixiao 2010-12-22 零件编号后带汽研发布源版本程序 2800G17-48A/A
         
        //CCBegin by liunan 2009-04-05
        //为之前为""的iba和route进行赋值。       
          if((!ibaName.equals("")||!routeName.equals(""))&&i>0)
          {
          	for (int k = 0; k < attrNames.length; k++)
          	{
          		String attrfull = attrNames[k];
          		if (attrfull.endsWith("-1"))
          		{
          			String curIbaKey = attrfull.substring(0, attrfull.length() - 2);
          			curIbaKey = temp[0]+wfutil.getDisplayName(curIbaKey);
          			//System.out.println("in getattrfull curIbaKey============"+curIbaKey);
          			if(ibaTempMap.containsKey(curIbaKey))
          			{
          				temp[4 + k] = ibaTempMap.get(curIbaKey).toString();
          			}
          		}
          		else if (attrfull.endsWith("-2"))
          		{
          			String[] idStr = (String[])bomMap.get(temp[0]);
          			String curRouteKey = idStr[0]+attrfull.substring(0, attrfull.length() - 2);
          			String curRouteName = attrfull.substring(0, attrfull.length() - 2);
          			if(routeTempMap.containsKey(curRouteKey))
          			{
          				String curRouteValue = routeTempMap.get(curRouteKey).toString();
          				//如果装配为空，则用父件的制造路线。
          				//System.out.println("curRouteValue="+curRouteValue);
          				//System.out.println("curRouteName="+curRouteName);

          				if(curRouteName.equals("装配路线")&&(curRouteValue==null||curRouteValue.equals("")||curRouteValue.equals("/")))
          				{
//          				CCBegin by leixiao 2010-4-16 
//                        1、装配路线为空时取父件制造路线的第一个单位 
//                        2、逻辑总成的装配路线不取父件制造路线         	
//                        3、 父件为逻辑总成的装配路线取父件的父件的制造路线       	
//          			  4、当制造路线包含“用”时不从父件取制造路线作为装配路线	
          					if(routeTempMap.containsKey(idStr[1]+"制造路线"))
          					{
          						// 装配路线为空时取父件制造路线的第一个单位
          						String fuzhizhao=routeTempMap.get(idStr[1]+"制造路线").toString();
          						if(fuzhizhao.indexOf("--")!=-1){
          						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("-"));          					   
          					    }
              					if(islogic.contains(temp[0])){//如果是逻辑总成不取父件制造         						
              						logicRoute.put(idStr[0].toString(), fuzhizhao);//逻辑总成的装配路线
              					}
              					else if(routeTempMap.get(idStr[0]+"制造路线").toString().indexOf("用")!=-1){
              						//制造路线包含用时，不取父件的的制造作为装配
              					}
              					else{
              						temp[4 + k]=fuzhizhao;	
              					}          					
          				  }
          					else{
          						if(logicRoute.containsKey(idStr[1].toString())&&!islogic.contains(temp[0])&&routeTempMap.get(idStr[0]+"制造路线").toString().indexOf("用")==-1){//其父件在逻辑总成集成中有内容
          							temp[4 + k]=logicRoute.get(idStr[1].toString());
          						}
          					}
          				//CCEnd by leixiao 2010-4-16 
          				}
          				//CCBegin by liunan 2012-04-28 获取全部路线。
          				else if(curRouteName.equals("装配路线")&&curRouteValue.indexOf("无")!=-1)
          				{
          					String[] idallStr = (String[])allBomMap.get(temp[0]);
          					String pid = idallStr[1];
          					String temppid[] = pid.split(";");
          					temp[4 + k]=curRouteValue;
          					for(int ii = 0 ; ii< temppid.length; ii++)
          					{
          						String ppartid = temppid[ii];
          					if(routeTempMap.containsKey(ppartid+"制造路线"))
          					{
          						// 装配路线为空时取父件制造路线的第一个单位
          						String fuzhizhao=routeTempMap.get(ppartid+"制造路线").toString();
          						if(fuzhizhao.indexOf("--")!=-1){
          						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("-"));          					   
          					    }
          					   
          					   if(fuzhizhao.indexOf("/")!=-1){
          						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("/"));          					   
          					    }
          					    
              					if(islogic.contains(temp[0])){//如果是逻辑总成不取父件制造  
              						logicRoute.put(idStr[0].toString(), fuzhizhao);//逻辑总成的装配路线
              					}
              					else if(routeTempMap.get(idStr[0]+"制造路线").toString().indexOf("用")!=-1){
              						//制造路线包含用时，不取父件的的制造作为装配
              					}
              					else{
              						/*if(curRouteValue.indexOf(fuzhizhao)!=-1)
              						{
              							temp[4 + k]=curRouteValue.replaceAll("/无","").replaceAll("无/","").replaceAll("无","");	
              						}
              						else
              						{
              							temp[4 + k]=curRouteValue.replaceAll("无",fuzhizhao);	
              						}*/
              						
              						if(temp[4 + k].toString().indexOf(fuzhizhao)<0)
              						{
              							temp[4 + k]=temp[4 + k]+"/"+fuzhizhao;
              						}
              					}          					
          				  }
          					else{
          						if(logicRoute.containsKey(idStr[1].toString())&&!islogic.contains(temp[0])&&routeTempMap.get(idStr[0]+"制造路线").toString().indexOf("用")==-1){//其父件在逻辑总成集成中有内容
          							//System.out.println("logicRoute.get(idStr[1].toString())====="+logicRoute.get(idStr[1].toString()));
          							
          							/*if(curRouteValue.indexOf(logicRoute.get(idStr[1].toString()).toString())!=-1)
          							{
          								temp[4 + k]=curRouteValue.replaceAll("/无","").replaceAll("无/","").replaceAll("无","");	
          							}
          							else
          							{
          								temp[4 + k]=curRouteValue.replaceAll("无",logicRoute.get(idStr[1].toString()).toString());
          							}*/
          							if(temp[4 + k].toString().indexOf(logicRoute.get(idStr[1].toString()).toString())<0)
          							{
          								temp[4 + k]=temp[4 + k]+"/"+logicRoute.get(idStr[1].toString()).toString();
          							}
          						}
          					}
          					
          				}
          				
          					//if(temp[4 + k].equals(""))
          					{
          						temp[4 + k]=temp[4 + k].toString().replaceAll("/无","").replaceAll("无/","").replaceAll("无","");	
          					}
          					
          					//System.out.println("temp[4 + k]==="+temp[4 + k]);          						
          				}
          				//CCEnd by liunan 2012-04-28
          				else
          				{
          					temp[4 + k] = curRouteValue;
          				}
          				
          			}
          			else
          			{
//      				CCBegin by leixiao 2010-4-16 
          				if(curRouteName.equals("装配路线")&&routeTempMap.containsKey(idStr[1]+"制造路线"))
          				{
      						//装配路线为空时取父件制造路线的第一个单位
      						String fuzhizhao=routeTempMap.get(idStr[1]+"制造路线").toString();
      						if(fuzhizhao.indexOf("--")!=-1){
      						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("-"));          					   
      					    }
          					if(islogic.contains(temp[0])){//如果是逻辑总成不取父件制造         						
          						logicRoute.put(idStr[0].toString(), fuzhizhao);
          					}
          					else{
          						temp[4 + k]=fuzhizhao;	
          					}
      					//装配路线为空时取父件制造路线的第一个单位
          				}
          				else if(curRouteName.equals("装配路线")){
          					//如果装配路线从父件制造取仍为空，父件是逻辑总成的取父件的父件的制造路线
      						if(logicRoute.containsKey(idStr[1].toString())&&!islogic.contains(temp[0])){
      							temp[4 + k]=logicRoute.get(idStr[1].toString());
      						}
          				}
//      				CCEnd by leixiao 2010-4-16 
      					
          			}

          		}
          	}
          }
          //CCEnd by liunan 2009-04-05
      }

      long t22 = System.currentTimeMillis();
        System.out.println("新获取iba和route方法 用时： "+(t22-ta22)+" 毫秒");

      long t2 = System.currentTimeMillis();
        if(subTimeGong>0)
          System.out.println("当前视图为："+viewName+"， 所有查找子件方法subTimeGong用时："+subTimeGong+" 毫秒");
        else if(subTimeOther>0)
          System.out.println("当前视图为："+viewName+"， 所有查找子件方法subTimeOther用时："+subTimeOther+" 毫秒");
        else
          System.out.println("当前视图为："+viewName);
        System.out.println("结束零部件 "+partIfc.getPartNumber()+" 报表，共有 "+(vector.size()-1)+ " 个零部件， 子件节点共有 "+subCount+" 个， 用户 "+userName+" 用时： "+(t2-t1)/1000+" 秒");
        System.out.println("--------------------------------------------------"+"\n\n\n");
      subCount = 0;
      subTimeGong = 0;
      subTimeOther = 0;
      return vector;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new QMException(e.toString());
    }
  }

/**
   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法，
   * 产品中存在的该方法依然保留，以备使用。
   * liuming add 20070209
   * 无合件装配表的显示。
   * 返回结果是vector,其中vector中的每个元素都是一个集合：
   * 0：当前part的BsoID；
   * 1：当前part所在的级别，转化为字符型；
   * 2：当前part的编号；
   * 3：当前part的名称；
   * 4：当前part被最顶层部件使用的数量，转化为字符型；
   * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
   *                如果定制了属性：按照所有定制的属性加到结果集合中。
   * 本方法调用了递归方法：
   * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
   * PartUsageLinkIfc partLinkIfc, int parentQuantity);
   * @param partIfc :QMPartIfc 最顶级的部件值对象。
   * @param attrNames :String[] 定制的属性，可以为空。
   * @param affixAttrNames : String[] 定制的扩展属性名集合，可以为空。
   * @param routeNames : String[] 定制的工艺路线名集合，可以为空。
   * @param configSpecIfc :PartConfigSpecIfc 配置规范。
   * @throws QMException
   * @return Vector
   * @author liunan 2008-08-01
   */
  public Vector setMaterialList2(QMPartIfc partIfc, String attrNames[],
                                String affixAttrNames[], String[] routeNames,
                                PartConfigSpecIfc configSpecIfc) throws
      QMException {
    try {
    	  System.out.println("\n\n\n"+"--------------------------------------------------");
      	SessionService sservice=(SessionService)EJBServiceHelper.getService("SessionService");
        String userName=sservice.getCurUserInfo().getUsersDesc();
      	System.out.println("用户 "+userName+" 开始生成零部件 "+partIfc.getPartNumber()+" 分级2报表");
      	long t1 = System.currentTimeMillis();
      WfUtil wfutil = new WfUtil();
      int level = 0;
      PartUsageLinkInfo partLinkInfo = new PartUsageLinkInfo();
      float parentQuantity = 1.0F;

      int quantitySite = 0;
      for (int i = 0; i < attrNames.length; i++) {
        String attr = attrNames[i];
        attr = attr.trim();
        if (attr != null && attr.length() > 0) {
          if (attr.equals("quantity-0")) {
            quantitySite = 4 + i;
          }
        }
      }

      Vector vector = null;

      vector = fenji2(partIfc, partIfc, attrNames, affixAttrNames, routeNames,
                     configSpecIfc, level, partLinkInfo, parentQuantity, "",
                     parentQuantity);
      if(vector != null && vector.size() > 0)
      {
        Object first[] = (Object[]) vector.elementAt(0);
        if (quantitySite > 0) {
        	//CCBegin by liunan 2008-10-13 数量的位置应该在下一列。
        	//源码如下
          //first[quantitySite] = "";
          first[quantitySite+1] = "";
          //CCEnd by liunan 2008-10-13
        }
        vector.setElementAt( ( (Object) (first)), 0);
      }
      Vector firstElement = new Vector();
      firstElement.addElement("");
      firstElement.addElement("");
      firstElement.addElement("");
      String ssss = QMMessage.getLocalizedMessage(RESOURCE, "level", null);
      firstElement.addElement(ssss);

      for (int i = 0; i < attrNames.length; i++) {
        String attr = attrNames[i];
        if (attr.endsWith("-0")) {
          ssss = QMMessage.getLocalizedMessage(RESOURCE,
                                               attr.substring(0,
              attr.length() - 2), null);
          firstElement.addElement(ssss);
        }
        else if (attr.endsWith("-1")) {
          firstElement.addElement(wfutil.getDisplayName(attr.substring(0,
              attr.length() - 2)));
        }
        else if (attr.endsWith("-2")) {
          firstElement.addElement(attr.substring(0, attr.length() - 2));
        }
      }
      firstElement.addElement("上一级父件编号");//yanqi-20061222-在分级表上增加父件号

      String[] tempArray = new String[firstElement.size()];
      for (int i = 0; i < firstElement.size(); i++) {
        tempArray[i] = (String) firstElement.elementAt(i);
      }
      vector.insertElementAt( ( (Object[]) (tempArray)), 0);

      for (int i = 0; i < vector.size(); i++) {
        Object temp[] = (Object[]) vector.elementAt(i);
        for (int j = 0; j < temp.length; j++) {
          if (temp[j] == null) {
            temp[j] = "";
          }
        }
      }
      Vector v2 = new Vector() ;
      if(vector != null && vector.size() > 0)
      {
        v2.addElement(vector.firstElement());
        for(int i=1;i<vector.size() ;i++)
        {
          Object[] gg = (Object[])vector.elementAt(i);
          String isLogic = gg[4].toString();
          if(isLogic.equalsIgnoreCase("false"))
          {
            Object[] hh = new Object[gg.length-1];
            hh[0]=gg[0];
            hh[1]=gg[1];
            hh[2]=gg[2];
            hh[3]=gg[3];
            for(int j=4;j<hh.length;j++)
            {
              hh[j]=gg[j+1];
            }
            v2.addElement(hh);
          }
        }
      }
      vector = v2;

      long t2 = System.currentTimeMillis();
        System.out.println("结束零部件 "+partIfc.getPartNumber()+" 报表 用户 "+userName+" 用时： "+(t2-t1)/1000+" 秒");
        System.out.println("--------------------------------------------------"+"\n\n\n");
      return vector;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new QMException(e.toString());
    }
  }


  /**
   * 此方法在当初专为解放定制的方法基础上进行修改，方法中不再处理iba和route，都赋值为""，
   * 等循环fenji完成后统一处理。
   * 产品中存在的该方法依然保留，以备使用。
   * 私有方法。被setMaterialList()方法调用，实现定制分级物料清单的功能。
   * 返回结果是vector,其中vector中的每个元素都是一个集合：
   * 0：当前part的BsoID；
   * 1：当前part所在的级别，转化为字符型；
   * 2：当前part的编号；
   * 3：当前part的名称；
   * 4：当前part被最顶层部件使用的数量，转化为字符型；
   * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
   *                如果定制了属性：按照所有定制的属性加到结果集合中。

   * @param partIfc :QMPartIfc 当前的部件；
   * @param attrNames :String[] 定制的属性集合，可以为空；
   * @param affixAttrNames :String[] 定制的扩展属性名集合，可以为空。
   * @param routeNames :String[] 定制的工艺路线名集合，可以为空。
   * @param configSpecIfc :PartConfigSpecIfc 配置规范；
   * @param level :int 当前part所在的级别；
   * @param partLinkIfc :PartUsageLinkIfc 记录了当前part和起父亲节点的使用关系的值对象；
   * @param parentQuantity :int 当前part的父亲节点被最顶级部件使用的数量。
   * @param bomMap :HashMap 报表中涉及的所有零部件集合，key为partBsoID，
   * 值为二维数组，第一个元素是masterBsoID，第二个元素是父件masterBsoID。
   * @throws QMException
   * @return Vector
   * @author CCBegin by leix	 2010-12-20  增加逻辑总成数量报表  增加参数islogiclist
   */
//  CCBegin by leix	 2010-12-20  增加逻辑总成数量报表  增加参数islogiclist
  private Vector fenji(QMPartIfc partProductIfc, QMPartIfc partIfc,
                       String attrNames[], String affixAttrNames[],
                       String[] routeNames, PartConfigSpecIfc configSpecIfc,
                       int level, PartUsageLinkIfc partLinkIfc,
                       float parentQuantity, String parentFirstMakeRoute,
                       //CCBegin by liunan 2012-05-24 为了得到所有上级件的制造路线，需要记录零件在当前整车下所有父件的集合。
                       //float parentInProductCount, HashMap bomMap,Vector islogic,boolean islogiclist) throws
                       float parentInProductCount, HashMap bomMap, HashMap allBomMap,Vector islogic,boolean islogiclist) throws
                       //CCEnd by liunan 2012-05-24
      QMException
  {
    //如果bomMap为空则表示第一次进入bianli方法，加入bomMap的值也就是根节点。
    if(bomMap==null||bomMap.size()==0)
      {
        //随意为父件masterid赋的"root"，用的时候缓存中没有"root"，也就不会得到值。
      	bomMap.put(partIfc.getBsoID(),new String[]{partIfc.getMasterBsoID(),"root"});
      	allBomMap.put(partIfc.getBsoID(),new String[]{partIfc.getMasterBsoID(),"root"});
      }

    Vector resultVector = new Vector();
    Object[] tempArray = null;

    Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);

    /////////////////////////////////////////排序 屈晓光添加

    Object[] ququ = (Object[]) collection.toArray();
    Vector objectVec = new Vector();
    for (int k = 0; k < ququ.length; k++)
    {
      if (ququ[k] instanceof Object[])
      {
        Object[] obj = (Object[]) ququ[k];
        if ( (obj[1] instanceof QMPartIfc) && (obj[0] instanceof PartUsageLinkIfc))
        {
          for (int j = k; j < ququ.length; j++)
          {
            Object[] obj1 = (Object[]) ququ[j];
            Object[] obj2 = (Object[]) ququ[k];
            if ( (obj1[1] instanceof QMPartIfc) && (obj1[0] instanceof PartUsageLinkIfc))
           {
             //将partIfc的子件按格式保存到缓存bomMap中。
            	if(!bomMap.containsKey(((QMPartIfc)obj1[1]).getBsoID()))
            	{
            	  bomMap.put(((QMPartIfc)obj1[1]).getBsoID(),new String[]{((QMPartIfc)obj1[1]).getMasterBsoID(),partIfc.getMasterBsoID()});
            	  //CCBegin by liunan 2012-05-24 为了得到所有上级件的制造路线，需要记录零件在当前整车下所有父件的集合。
            	  allBomMap.put(((QMPartIfc)obj1[1]).getBsoID(),new String[]{((QMPartIfc)obj1[1]).getMasterBsoID(),partIfc.getMasterBsoID()});
                //CCEnd by liunan 2012-05-24
              }
              //CCBegin by liunan 2012-05-24 为了得到所有上级件的制造路线，需要记录零件在当前整车下所有父件的集合。
              else
              {
              	String idTemp[] = (String[])allBomMap.get(((QMPartIfc)obj1[1]).getBsoID());
              	String parentPartStr = idTemp[1];
              	if(parentPartStr.indexOf(partIfc.getMasterBsoID())<0)
              	{
              		allBomMap.put(((QMPartIfc)obj1[1]).getBsoID(),new String[]{((QMPartIfc)obj1[1]).getMasterBsoID(),parentPartStr+";"+partIfc.getMasterBsoID()});
              	}
              }
              //CCEnd by liunan 2012-05-24

              String partNUM2 = ( (QMPartIfc) obj2[1]).getPartNumber();
              String partNUM1 = ( (QMPartIfc) obj1[1]).getPartNumber();

              if (partNUM2.compareTo(partNUM1) > 0)
              {
                ququ[k] = obj1;
                ququ[j] = obj2;
              }
            }
          }
          objectVec.add(ququ[k]);
        }
      }
      collection = objectVec;
    }

    boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
    //记录数量和编号在排序中所在的位置。
    int numberSite = 0;
    for (int i = 0; i < attrNames.length; i++)
    {
      String attr = attrNames[i];
      attr = attr.trim();
      if (attr != null && attr.length() > 0)
      {
        if (attr.equals("partNumber-0"))
        {
          numberSite = 4 + i;
        }
      }
    }
    if (attrNullTrueFlag)
    {
      tempArray = new Object[5];//yanqi-20061222 增加“父件编号”属性
      tempArray[2] = new Integer(numberSite);//yanqi-20061222
    }
    else
    {
      tempArray = new Object[5 + attrNames.length];//yanqi-20061222增加“父件编号”属性
      tempArray[2] = new Integer(numberSite);
    }

    tempArray[0] = partIfc.getBsoID();
    tempArray[1] = new Integer(1);
    tempArray[3] = new Integer(level); //level的初始值为0。
    //CCBegin by leixiao 2020-4-20    如果是逻辑总成，路线不显示装配路线
    if (partIfc.getPartType().toString().equalsIgnoreCase("Logical"))
    {
      islogic.add(partIfc.getBsoID());
    }
    //CCEnd by leixiao 2020-4-20 
    //CCBegin by leixiao 2010-12-22 父件为 逻辑总成时,子件数量=数量*父件数量 此处获得父件
    QMPartIfc part=null;//父件
    if(partLinkIfc.getBsoID()!=null&&!partLinkIfc.getBsoID().equals(""))
    {
      PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
      part=(QMPartIfc) ps.refreshInfo(partLinkIfc.getRightBsoID());
    }
  //CCEnd by leixiao 2010-12-22 
    String parentMakeRouteFirst = "";
    for (int j = 0; j < attrNames.length; j++)
    {
      String attrfull = attrNames[j];
      if (attrfull.endsWith("-0"))
      {
        String attr = attrfull.substring(0, attrfull.length() - 2);
        if (attr != null && attr.length() > 0)
        {
          String temp = tempArray[1].toString();
          if (attr.equals("defaultUnit") && !temp.equals("0"))
          {
            Unit unit = partLinkIfc.getDefaultUnit();
            if (unit != null)
            {
              tempArray[4 + j] = unit.getDisplay();
            }
            else
            {
              tempArray[4 + j] = "";
            }
          }
          else if (attr.equals("quantity"))
          {
            if (level == 0)
            {
              parentQuantity = 1f;
              String quan = "1";
              tempArray[4 + j] = new String(quan);
            }
            else
            {
            	// parentQuantity = partLinkIfc.getQuantity(); //parentQuantity*partLinkIfc.getQuantity();原码
          	  //CCBegin by leixiao 2010-12-22 父件为 逻辑总成时,子件数量=数量*父件数量 
            	//System.out.println("--------父件-"+part.getPartNumber()+" "+islogic+"  "+part.getPartNumber().substring(4,5));
            	if(islogiclist&&part.getPartNumber().length()>5&&part.getPartNumber().substring(4,5).equals("G")){
            		//System.out.println("--"+partIfc.getPartNumber()+"------父件-"+part.getPartNumber().substring(4,5)+"---"+parentQuantity);
            		parentQuantity=parentQuantity*partLinkIfc.getQuantity();
            	}
            	else{
            	 
              parentQuantity = partLinkIfc.getQuantity(); //parentQuantity*partLinkIfc.getQuantity();
            	}
            	 //CCEnd by leixiao 2010-12-22 父件为 逻辑总成时,子件数量=数量*父件数量 
              String tempQuantity = String.valueOf(parentQuantity);
              if (tempQuantity.endsWith(".0"))
              {
                tempQuantity = tempQuantity.substring(0, tempQuantity.length() - 2);
              }
              tempArray[4 + j] = tempQuantity;
            }
          }
          else
          {
            attr = attr.substring(0, 1).toUpperCase() + attr.substring(1, attr.length());
            attr = "get" + attr;
            try
            {
              Class partClass = Class.forName("com.faw_qm.part.model.QMPartInfo");
              Method method1 = partClass.getMethod(attr, null);
              Object obj = method1.invoke(partIfc, null);
              if (obj == null)
              {
                tempArray[4 + j] = "";
              }
              else
              {
                if (obj instanceof String)
                {
                  String tempString = (String) obj;
                  if (tempString != null && tempString.length() > 0)
                  {
                    tempArray[4 + j] = tempString;
                  }
                  else
                  {
                    tempArray[4 + j] = "";
                  }
                }
                else
                {
                  if (obj instanceof EnumeratedType)
                  {
                    EnumeratedType tempType = (EnumeratedType) obj;
                    if (tempType != null)
                    {
                      tempArray[4 + j] = tempType.getDisplay();
                    }
                    else {
                      tempArray[4 + j] = "";
                    }
                  }
                }
              }
            }
            catch (Exception ex)
            {
              ex.printStackTrace();
              throw new QMException(ex);
            }
          }
        }
      }
      //iba直接设置为""，稍后整体获取iba后再赋值。
      else if (attrfull.endsWith("-1"))
      {
      	tempArray[4 + j] = "";
      }
      //route直接设置为""，稍后整体获取route后再赋值。
      else if (attrfull.endsWith("-2"))
      {
      	tempArray[4 + j] = "";
      }
    }
    //yanqi-20061222-增加父件编号属性
    if(partLinkIfc.getBsoID()!=null&&!partLinkIfc.getBsoID().equals(""))
    {
    	//此处代码上移,以便判断父件是不是逻辑总成
//      PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
//      QMPartIfc part=(QMPartIfc) ps.refreshInfo(partLinkIfc.getRightBsoID());
      if(part!=null)
        tempArray[tempArray.length-1]=part.getPartNumber();
    }//yanqi－20061222
    //CCBegin by leixiao 2010-12-22 零件编号后带汽研发布源版本程序 2800G17-48A/A
    //如果是标准件,加上$符号,供显示版本用.
   // System.out.println(""+islogic+"---"+partIfc.getPartType());
  if(islogiclist&&partIfc.getPartType().toString().equals("Standard")){	
	  tempArray[4]=tempArray[4]+"$"; 
  }
//CCEnd by leixiao 2010-12-22 零件编号后带汽研发布源版本程序 2800G17-48A/A
    resultVector.addElement( ( (Object[]) (tempArray)));

    //////////////////////////////////排序完毕
    if (collection == null || collection.size() == 0)
    {
      return resultVector;
    }
    Object temp[] = collection.toArray();
    level++;
    for (int k = 0; k < temp.length; k++)
    {
      if (temp[k] instanceof Object[])
      {
        Object obj[] = (Object[]) temp[k];
        Vector tempResult = fenji(partProductIfc, (QMPartIfc) obj[1], attrNames,
                             affixAttrNames, routeNames, configSpecIfc, level,
                             (PartUsageLinkIfc) obj[0], parentQuantity,
                             parentMakeRouteFirst == null ? "" :
                             //CCBegin by liunan 2012-05-24 为了得到所有上级件的制造路线，需要记录零件在当前整车下所有父件的集合。
                             //parentMakeRouteFirst, parentInProductCount, bomMap,islogic,islogiclist);
                             parentMakeRouteFirst, parentInProductCount, bomMap, allBomMap,islogic,islogiclist);
                             //CCEnd by liunan 2012-05-24
        for (int m = 0; m < tempResult.size(); m++)
        {
          resultVector.addElement(tempResult.elementAt(m));
        }
      }
    }

    level--;
    return resultVector;
  }
//CCEnd by leix	 2010-12-20  增加逻辑总成数量报表
  
//CCBegin by leix	 2010-12-20    逻辑总成数量报表  报表的零件后带汽研版本
  private String getQiyanVersion(QMPartIfc partIfc) {
	// TODO Auto-generated method stub
		Collection col=null;
		PersistService pservice=null;
		try {
			pservice = (PersistService) EJBServiceHelper
			.getPersistService();
		
			QMQuery query1 = new QMQuery("StringValue");
			query1.addCondition(new QueryCondition("iBAHolderBsoID", "=",
					partIfc.getBsoID()));
			query1.addAND();
			query1.addCondition(new QueryCondition("definitionBsoID", "=",
					ibaid));
//			query1.addOrderBy("modifyTime",true);
			//System.out.println(query1.getDebugSQL());
	    col = pservice.findValueInfo(query1, false);
	  // System.out.println("col="+col);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
}
//CCEnd by leix	 2010-12-20  增加逻辑总成数量报表

  /**
   * 此方法为当初专为解放定制的方法，后期业务的实现也一直通过此方法获得，因此添加这个方法，
   * 产品中存在的该方法依然保留，以备使用。
   * liuming add 20070209
   * 私有方法。被setMaterialList2()方法调用，实现定制无合件装配表的功能。
   * 返回结果是vector,其中vector中的每个元素都是一个集合：
   * 0：当前part的BsoID；
   * 1：当前part所在的级别，转化为字符型；
   * 2：当前part的编号；
   * 3：当前part的名称；
   * 4：当前part被最顶层部件使用的数量，转化为字符型；
   * 5-...：可变的：如果没有定制属性，5：当前part的版本号，6：当前part的视图；
   *                如果定制了属性：按照所有定制的属性加到结果集合中。

   * @param partIfc :QMPartIfc 当前的部件；
   * @param attrNames :String[] 定制的属性集合，可以为空；
   * @param affixAttrNames :String[] 定制的扩展属性名集合，可以为空。
   * @param routeNames :String[] 定制的工艺路线名集合，可以为空。
   * @param configSpecIfc :PartConfigSpecIfc 配置规范；
   * @param level :int 当前part所在的级别；
   * @param partLinkIfc :PartUsageLinkIfc 记录了当前part和起父亲节点的使用关系的值对象；
   * @param parentQuantity :int 当前part的父亲节点被最顶级部件使用的数量。
   * @throws QMException
   * @return Vector
   * @author liunan 2008-08-01
   */
  private Vector fenji2(QMPartIfc partProductIfc, QMPartIfc partIfc,
                       String attrNames[], String affixAttrNames[],
                       String[] routeNames, PartConfigSpecIfc configSpecIfc,
                       int level, PartUsageLinkIfc partLinkIfc,
                       float parentQuantity, String parentFirstMakeRoute,
                       float parentInProductCount) throws
      QMException {
    Vector resultVector = new Vector();
    Object[] tempArray = null;
    WfUtil wfutil = new WfUtil();
    boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
    //记录数量和编号在排序中所在的位置。
    int numberSite = 0;
    for (int i = 0; i < attrNames.length; i++) {
      String attr = attrNames[i];
      attr = attr.trim();
      if (attr != null && attr.length() > 0) {
        if (attr.equals("partNumber-0")) {
          numberSite = 4 + i;
        }
      }
    }
    if (attrNullTrueFlag) {
      //tempArray = new Object[4];
      tempArray = new Object[6];//yanqi-20061222 增加“父件编号”属性
      tempArray[2] = new Integer(numberSite);//yanqi-20061222
    }
    else {
      //tempArray = new Object[4 + attrNames.length];
      tempArray = new Object[6 + attrNames.length];//yanqi-20061222增加“父件编号”属性
      tempArray[2] = new Integer(numberSite);
    }
    boolean affixAttrNullTrueFlag = affixAttrNames == null ||
        affixAttrNames.length == 0;
    boolean routeNullTrueFlag = routeNames == null ||
        routeNames.length == 0;

    tempArray[0] = partIfc.getBsoID();
    tempArray[1] = new Integer(1);
    tempArray[3] = new Integer(level); //level的初始值为0。

    ///////////标记当前零件是否是逻辑总成 liuming 20070303 add
    if (partIfc.getPartType().toString().equalsIgnoreCase("Logical"))
    {
      tempArray[4]="true";
    }
    else
    {
      tempArray[4]="false";
    }
    ///////////////////////////////liuming 20070303 add end

    //获得零部件的工艺路线。
    TechnicsRouteService trService = (TechnicsRouteService) EJBServiceHelper.
        getService("TechnicsRouteService");
    float countInparent = parentInProductCount * parentQuantity; //quxg add for jiefang 此变量并不一定是在直接父亲下的数量,而是其制造路线所取的直接或间接父亲下的使用总数,比如,其直接父亲为逻辑总成,没有路线,那就取逻辑总成父亲下的数量,如此往复,但总称数量要计算在内

    String parentMakeRouteFirst = "";
    Vector routeVec = null;
    for (int j = 0; j < attrNames.length; j++) {
      String attrfull = attrNames[j];
      if (attrfull.endsWith("-0")) {
        String attr = attrfull.substring(0, attrfull.length() - 2);
        if (attr != null && attr.length() > 0) {
          String temp = tempArray[1].toString();
          if (attr.equals("defaultUnit") && !temp.equals("0")) {
            Unit unit = partLinkIfc.getDefaultUnit();
            if (unit != null) {
              tempArray[5 + j] = unit.getDisplay();
            }
            else {
              tempArray[5 + j] = "";
            }
          }
          else if (attr.equals("quantity")) {
            if (level == 0) {
              parentQuantity = 1f;
              String quan = "1";
              tempArray[5 + j] = new String(quan);
            }
            else {
              parentQuantity = partLinkIfc.getQuantity(); //parentQuantity*partLinkIfc.getQuantity();
              String tempQuantity = String.valueOf(parentQuantity);
              if (tempQuantity.endsWith(".0")) {
                tempQuantity = tempQuantity.substring(0,
                    tempQuantity.length() - 2);
              }
              tempArray[5 + j] = tempQuantity;
            }
          }
          else {

            attr = attr.substring(0, 1).toUpperCase() +
                attr.substring(1, attr.length());
            attr = "get" + attr;

            try {
              Class partClass = Class.forName(
                  "com.faw_qm.part.model.QMPartInfo");
              Method method1 = partClass.getMethod(attr, null);
              Object obj = method1.invoke(partIfc, null);
              if (obj == null) {
                tempArray[5 + j] = "";
              }
              else {
                if (obj instanceof String) {
                  String tempString = (String) obj;
                  if (tempString != null && tempString.length() > 0) {
                    tempArray[5 + j] = tempString;
                  }
                  else {
                    tempArray[5 + j] = "";
                  }
                }
                else {
                  if (obj instanceof EnumeratedType) {
                    EnumeratedType tempType = (EnumeratedType) obj;
                    if (tempType != null) {
                      tempArray[5 + j] = tempType.getDisplay();
                    }
                    else {
                      tempArray[5 + j] = "";
                    }
                  }
                }
              }
            }
            catch (Exception ex) {
              ex.printStackTrace();
              throw new QMException(ex);
            }
          }

        }

      }
      else if (attrfull.endsWith("-1")) {
        affixAttrNames = new String[1];
        affixAttrNames[0] = attrfull.substring(0, attrfull.length() - 2);
        tempArray = wfutil.getIBAValue(tempArray, affixAttrNames, partIfc,
                                       j - 3);
        if (tempArray[5 + j] == null) {
          tempArray[5 + j] = "";
        }
      }
      else if (attrfull.endsWith("-2")) {
        routeNames = new String[1];
        routeNames[0] = attrfull.substring(0, attrfull.length() - 2);
        if (routeVec == null) {
          routeVec = trService.getListAndBrances(partProductIfc, partIfc,
                                                 routeNames,
                                                 parentFirstMakeRoute);

        }

        if (routeVec != null && routeVec.size() > 0) {
          HashMap map = (HashMap) routeVec.elementAt(0);
          String makeRoute = (String) map.get("制造路线");
          if (makeRoute == null) {
            makeRoute = "";
          }
          if (makeRoute.indexOf("...") < 0) { //bushi逻辑总成
            parentMakeRouteFirst = "";
            StringTokenizer yy = new StringTokenizer(makeRoute, "/");
            while (yy.hasMoreTokens()) {
              String aa = yy.nextToken();
              StringTokenizer MM = new StringTokenizer(aa, "--");
              parentMakeRouteFirst += MM.nextToken() + "/";
            }
            if (parentMakeRouteFirst.endsWith("/")) {
              parentMakeRouteFirst = parentMakeRouteFirst.substring(0,
                  parentMakeRouteFirst.length() - 1);
            }
            parentMakeRouteFirst = parentMakeRouteFirst + "..." +
                partIfc.getPartNumber();
          }
          else { //是逻辑总成
            parentMakeRouteFirst = makeRoute;
          }

          if (partIfc.getPartType().toString().equalsIgnoreCase("Logical") &&
              (makeRoute != null && makeRoute.length() > 0)) {
            parentInProductCount = countInparent;
          }
          else {
            parentInProductCount = 1.0F;
          }

          String assRoute3 = (String) map.get("装配路线");
          if (assRoute3 == null) {
            assRoute3 = "";
          }
          int o = assRoute3.indexOf("...");
          if (o > 0) {
            map.put("装入合件数量", new Integer( (new Float(countInparent)).intValue()));
            String realassRoute = assRoute3.substring(0, o);
            String realParentNum = assRoute3.substring(o + 3);
            map.put("装配路线", realassRoute);
            map.put("装配路线合件号", realParentNum);

          }

//quxg add finish

          tempArray[1] = new Integer(routeVec.size());
          Object[] tempRoute = new Object[routeVec.size()];
          String[] tempArray1 = new String[routeNames.length];
          for (int ii = 0; ii < routeNames.length; ii++) {
            if (map.get(routeNames[ii]) == null) {
              tempArray1[ii] = "";
            }
            else {
              if (partIfc.getPartType().toString().equalsIgnoreCase("Logical")) {
                tempArray1[ii] = "";
              }
              else {
                tempArray1[ii] = map.get(routeNames[ii].trim()).toString();
              }
            }
          }
          tempRoute[0] = tempArray1;

          tempArray[5 + j] = tempArray1[0];
        }
        else {
          Object[] temp1 = new Object[1];
          Object[] temp2 = new Object[routeNames.length];
          for (int i = 0; i < routeNames.length; i++) {
            temp2[i] = "";
          }
          temp1[0] = temp2;
          tempArray[5 + j] = temp2[0];
        }
      }
    }
    //yanqi-20061222-增加父件编号属性
    if(partLinkIfc.getBsoID()!=null&&!partLinkIfc.getBsoID().equals(""))
    {
      PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
      QMPartIfc part=(QMPartIfc) ps.refreshInfo(partLinkIfc.getRightBsoID());
      if(part!=null)
        tempArray[tempArray.length-1]=part.getPartNumber();
    }//yanqi－20061222
    resultVector.addElement( ( (Object[]) (tempArray)));
    Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);

    /////////////////////////////////////////排序 屈晓光添加

    Object[] ququ = (Object[]) collection.toArray();
    Vector objectVec = new Vector();
    for (int k = 0; k < ququ.length; k++)
    {
        for (int j = k; j < ququ.length; j++)
        {
            Object[] obj1 = (Object[]) ququ[j];
            Object[] obj2 = (Object[]) ququ[k];
              String partNUM2 = ( (QMPartIfc) obj2[1]).getPartNumber();
              String partNUM1 = ( (QMPartIfc) obj1[1]).getPartNumber();
              if (partNUM2.compareTo(partNUM1) > 0) {
                ququ[k] = obj1;
                ququ[j] = obj2;
              }
          }
          objectVec.add(ququ[k]);
      collection = objectVec;
    }
    //////////////////////////////////排序完毕

    if (collection == null || collection.size() == 0) {
      return resultVector;
    }
    Object temp[] = collection.toArray();
    if (partIfc.getPartType().toString().equalsIgnoreCase("Logical"))
    {
    }
    else
    {
      level++;
    }
    for (int k = 0; k < temp.length; k++)
    {
        Object obj[] = (Object[]) temp[k];
        Vector tempResult = fenji2(partProductIfc, (QMPartIfc) obj[1], attrNames,
                             affixAttrNames, routeNames, configSpecIfc, level,
                             (PartUsageLinkIfc) obj[0], parentQuantity,
                             parentMakeRouteFirst == null ? "" :
                             parentMakeRouteFirst, parentInProductCount);

        for (int m = 0; m < tempResult.size(); m++) {
          resultVector.addElement(tempResult.elementAt(m));
        }

    }

    level--;
    return resultVector;
  }


//屈晓光添加 将物料清单输出成本地文件
  /**
  * 将物料清单输出成本地文件，用于分级报表的输出。
  * 在com.faw_qm.part.client.other.controller.MaterialController调用了此方法。
  * @param map HashMap 整理好的报表属性集合。
  * @throws QMException
  * @return String 按规则整理出全部报表的信息，用于进行本地文件写入。
  * @author liunan 2008-08-01
  */
  public String getExportBOMClassfiyString(HashMap map) throws QMException {
    StringBuffer backBuffer = new StringBuffer();
    try
    {
    String PartID = (String) map.get("PartID");
    PersistService persistService = (PersistService) EJBServiceHelper.
        getPersistService();
       // System.out.println("PartID===="+PartID);
    QMPartIfc part = (QMPartIfc) persistService.refreshInfo(PartID);
    String head = "零部件：" + part.getPartNumber() + "(" + part.getPartName() +
        ")" + part.getVersionValue() +
        ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
         ("(" + part.getViewName() + ")")) +
        "的物料清单" +
        "\n";
    backBuffer.append(head);
    //String attributeName = (String) map.get("attributeName");
    String attributeName1 = (String) map.get("attributeName1");
    PartServiceHelper partHelper = new PartServiceHelper();
    Vector vec = partHelper.getMaterialList(PartID, attributeName1);
    String table = "";
    String[] tableHeader = (String[]) vec.elementAt(0);
    for (int i = 0; i < tableHeader.length; i++) {
      String colName = tableHeader[i];
      if (colName != null && colName.length() > 0) {
        table += colName + ",";
      }
    }
    table += "\n";
    backBuffer.append(table);
    for (int i = 1; i < vec.size(); i++) {
      Object[] information = (Object[]) vec.elementAt(i);
      for (int j = 3; j < information.length; j++) {
        Object hh = information[j];
        writeInformation(backBuffer, hh);
      }

      backBuffer.append("\n");
    }
  }
  catch(Exception e)
  {
  	e.printStackTrace();
  }
    return backBuffer.toString();
  }

  //liuming 20070209 add
  /**
   * 将物料清单输出成本地文件，用于无合件装配表的输出。
   * 在com.faw_qm.part.client.other.view.LogicBomFrame调用了此方法。
   * @param map HashMap 整理好的报表属性集合。
   * @throws QMException
   * @return String 按规则整理出全部报表的信息，用于进行本地文件写入。
   * @author liunan 2008-08-01
   */
    public String getExportBOMClassfiyString2(HashMap map) throws QMException {
      StringBuffer backBuffer = new StringBuffer();
      String PartID = (String) map.get("PartID");
      PersistService persistService = (PersistService) EJBServiceHelper.
          getPersistService();
      QMPartIfc part = (QMPartIfc) persistService.refreshInfo(PartID);
      String head = "零部件：" + part.getPartNumber() + "(" + part.getPartName() +
          ")" + part.getVersionValue() +
          ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
           ("(" + part.getViewName() + ")")) +
          "的物料清单" +
          "\n";
      backBuffer.append(head);
      //String attributeName = (String) map.get("attributeName");
      String attributeName1 = (String) map.get("attributeName1");
      PartServiceHelper partHelper = new PartServiceHelper();
      Vector vec = partHelper.getMaterialList2(PartID, attributeName1);
      String table = "";
      String[] tableHeader = (String[]) vec.elementAt(0);
      for (int i = 0; i < tableHeader.length; i++) {
        String colName = tableHeader[i];
        if (colName != null && colName.length() > 0) {
          table += colName + ",";
        }
      }
      table += "\n";
      backBuffer.append(table);
      for (int i = 1; i < vec.size(); i++) {
        Object[] information = (Object[]) vec.elementAt(i);
        for (int j = 3; j < information.length; j++) {
          Object hh = information[j];
          writeInformation(backBuffer, hh);
        }

        backBuffer.append("\n");
      }
      return backBuffer.toString();
  }

//屈晓光添加 将物料清单输出成本地文件
  /**
   * 整理数据，将报表中每行的数据，各值间用“,”分割开，用于生成本地excel文件时，
   * @param backBuffer StringBuffer 报表数据整理成的字符集合。
   * @param object Object
   * @author liunan 2008-08-01
   */
  private void writeInformation(StringBuffer backBuffer, Object object) {
    String hehe = "";
    if ( (object instanceof String) || (object instanceof Integer)) {
      hehe += object + ",";
      int r1 = hehe.indexOf("\n");
      if (r1 > 0) {
        hehe = hehe.substring(0, r1) + "  " +
            hehe.substring(r1 + 1);
      }
      backBuffer.append(hehe);
    }
    else {
      Object[] kk = (Object[]) object;
      for (int i = 0; i < kk.length; i++) {
        Object[] kiki = (Object[]) kk[i];
        String qq = "";
        for (int j = 0; j < kiki.length; j++) {
          String jojo = (String) kiki[j];
          int r1 = jojo.indexOf("\n");
          if (r1 > 0) {
            jojo = jojo.substring(0, r1) + "  " +
                jojo.substring(r1 + 1);
          }
          qq += jojo + " ,";
        }
        backBuffer.append(qq + ",");
      }
    }

  }

//屈晓光添加 将物料清单输出成本地文件
  /**
   * 将物料清单输出成本地文件，用于统计报表的输出。
   * 在com.faw_qm.part.client.other.controller.MaterialController调用了此方法。
   * @param map HashMap 整理好的报表属性集合。
   * @throws QMException
   * @return String 按规则整理出全部报表的信息，用于进行本地文件写入。
   * @author liunan 2008-08-01
   */
  public String getExportBOMStatisticsString(HashMap map) throws QMException {
    String partBsoID = (String) map.get("PartID");
    //  String attributeName = (String) map.get("attributeName");
    String attributeName1 = (String) map.get("attributeName1");
    String source = (String) map.get("source");
    String type = (String) map.get("type");
    PartServiceHelper helper = new PartServiceHelper();
    StringBuffer backBuffer = new StringBuffer();
    QMPartIfc part = (QMPartIfc) helper.getObjectForID(partBsoID);

    String head = "零部件：" + part.getPartNumber() + "(" + part.getPartName() +
        ")" + part.getVersionValue() +
        ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
         ("(" + part.getViewName() + ")")) +
        "的零部件明细表\n";
    backBuffer.append(head);

    Vector vec = helper.getBOMList(partBsoID, attributeName1, source, type);
    String table = "";
    String[] tableHeader = (String[]) vec.elementAt(0);
    for (int i = 0; i < tableHeader.length; i++) {
      String colName = tableHeader[i];
      if (colName != null && colName.length() > 0) {
        table += colName + ",";
      }
    }
    table += "\n";
    backBuffer.append(table);
    for (int i = 1; i < vec.size(); i++) {
      Object[] information = (Object[]) vec.elementAt(i);
      for (int j = 3; j < information.length; j++) {
        Object hh = information[j];
        writeInformation(backBuffer, hh);
      }
      backBuffer.append("\n");
    }
    return backBuffer.toString();
  }

  /**
   * liunan 2009-04-05
   * 优化。改成抵用getUsesPartsFromLinksNew方法。
   * 根据指定配置规范，获得指定部件的使用结构：
   * 返回集合Collection的每个元素是一个数组对象，第0个元素记录关联对象信息，
   * 第1个元素记录关联对象记录的use角色的mastered对象匹配配置规范的iterated对象，
   * 如果没有匹配对象，记录mastered对象。
   * @param partIfc QMPartIfc 零部件值对象。
   * @param configSpecIfc PartConfigSpecIfc 零部件配置规范。
   * @throws QMException
   * @return Collection
   */
  public Collection getUsesPartIfcs(QMPartIfc partIfc, PartConfigSpecIfc configSpecIfc)
    throws QMException
{
    Collection links = null;
    if(configSpecIfc == null)
    {
      StandardPartService spservice = (StandardPartService)EJBServiceHelper.getService("StandardPartService");
              configSpecIfc = spservice.findPartConfigSpecIfc();
    }
    if(configSpecIfc.getStandardActive())
    {
    	if(configSpecIfc.getStandard().getViewObjectIfc()!=null)
    	{
    		viewName = configSpecIfc.getStandard().getViewObjectIfc().getViewName();
    	}
    }
    PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
    if(partIfc.getBsoName().equals("GenericPart"))
      links = pservice.navigateValueInfo(partIfc, "usedBy", "GenericPartUsageLink", false);
    else if(partIfc.getBsoName().equals("QMPart"))
      links = pservice.navigateValueInfo(partIfc, "usedBy", "PartUsageLink", false);
    if(links==null||links.size()==0)
      return new Vector();
    //System.out.println(partIfc.getBsoID()+"======"+links.size());
    Collection coll = new Vector();
    //CCBegin SS11
    //如果当前配置规范是工程视图，则采用新的过滤最新版子件的方法，否则还是使用旧的过滤方法。
//    if(viewName.equals("工程视图"))
//    {
//    	//根据link构造集合，每个元素是一个二维数组，数组第一个是linkifc，第二个是partifc
//    	long t1 = System.currentTimeMillis();
//    	coll = getUsesPartsFromLinksNew(links, configSpecIfc);
//    	long t2 = System.currentTimeMillis();
//    	subTimeGong = subTimeGong+(t2-t1);
//    	//System.out.println(partIfc.getPartNumber()+" 'getUsesPartIfcs方法 用时： "+(t2-t1)+" 毫秒，集合个数为："+coll.size()+" 个");
//    }
//    else
//    {
    	long t1 = System.currentTimeMillis();
    	coll = getUsesPartsFromLinks(links, configSpecIfc);
    	long t2 = System.currentTimeMillis();
    	subTimeOther = subTimeOther+(t2-t1);
    	//System.out.println(partIfc.getPartNumber()+" 'getUsesPartIfcs方法 用时： "+(t2-t1)+" 毫秒，集合个数为："+coll.size()+" 个");
   // }
    //CCEnd SS11
    return coll;
  }

  /**
   * liunan 2009-04-05
   * 用in方法对所有关联的子件masterBsoID进行查询得到其最新版本零部件，
   * 本方法存在一些问题，如不走当前用户配置规范，但可根据配置规范获得的视图为搜索条件得到结果。
   * @param collection Collection
   * @param configSpecIfc PartConfigSpecIfc
   * @throws QMException
   * @return Collection
   */
  private Collection getUsesPartsFromLinksNew(Collection collection,
      PartConfigSpecIfc configSpecIfc) throws QMException
  {
  	Vector resultVector = new Vector();
  	try
  	{
  		ConfigService cservice = (ConfigService)EJBServiceHelper.getService("ConfigService");
  		PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
  		Iterator iterator = collection.iterator();
  		String[] masterId = new String[collection.size()];
  		int j = 0;
  		PartUsageLinkIfc partLinkIfc = null;
  		while (iterator.hasNext())
  		{
  			partLinkIfc = (PartUsageLinkIfc)iterator.next();
  			masterId[j] = partLinkIfc.getLeftBsoID();
  			j++;
      }

      QMQuery query = new QMQuery("QMPart");
      QueryCondition condition1 = new QueryCondition("masterBsoID", QueryCondition.IN, masterId);
      query.addCondition(condition1);
      query.addAND();
      QueryCondition condition2 = new QueryCondition("viewName", "=" , "工程视图");
      query.addCondition(condition2);
      query = (new LatestConfigSpec()).appendSearchCriteria(query);
      //CCBegin SS7
      //Collection coll = (Collection)pService.findValueInfo(query, false);
      Collection coll = (Collection)pService.findValueInfo(query);
      //CCEnd SS7
      Collection collection1 = (new LatestConfigSpec()).process(coll);
      Iterator iter = collection1.iterator();
      Vector vector = (Vector) collection;
      //System.out.println(collection.size()+"========================"+collection1.size());
      while(iter.hasNext())
      {
      	QMPartIfc partIfc = null;
      	Object obj = iter.next();
      	if(obj instanceof QMPartIfc)
      	{
      		partIfc = (QMPartIfc)obj;
      		//System.out.println("part!!!part!!!");
      	}
      	else if(obj instanceof QMPartMasterIfc)
      	{
      		//System.out.println("error!!!error!!!error!!!error!!!error!!!");
      		continue;
      	}
      	else if(obj instanceof Object[])
      	{
      		Object[] objNow = (Object[]) obj;
      		partIfc = (QMPartIfc) objNow[0];
      		//System.out.println("obj!!!obj!!!");
      	}
      //System.out.println(partIfc.getMasterBsoID()+"============="+partIfc.getBsoID()+"============="+partIfc.getPartNumber());
      	for (int i = 0; i < vector.size(); i++)
      	{
      		partLinkIfc = (PartUsageLinkIfc) vector.elementAt(i);
      		if(partLinkIfc.getLeftBsoID().equals(partIfc.getMasterBsoID()))
      		{
      			Object[] resultObj = {partLinkIfc,partIfc};
      			resultVector.addElement(resultObj);
      		}
      	}
      }
  	}
  	catch(QMException e)
  	{
  		e.printStackTrace();
  		return resultVector;
  	}
  	catch(Exception e1)
  	{
  		e1.printStackTrace();
  		return resultVector;
  	}
  		return resultVector;
  }

  /**
   * 目前定制代码未使用，2009-04-14。
   * 通过指定的筛选条件，将集合collection中的PartUsageLinkIfc对象对应的
   * 符合筛选条件的Iterated部件进行筛选，如果不符合筛选条件则返回对应的Mastered主零部件。
   * @param collection :Collection 是PartUsageLinkIfc对象的集合。
   * @param configSpecIfc :PartConfigSpecIfc 零部件的筛选条件。
   * @return collection:Collection 对象，每个元素为一个数组:
   * 数组的第一个元素为PartUsageLinkIfc对象，第二个元素为QMPartIfc对象，
   * 如果没有QMPartIfc对象，为关联的QMPartMasterIfc对象。
   * @throws QMException
   */
  public Collection getUsesPartsFromLinks(Collection collection,
      PartConfigSpecIfc configSpecIfc) throws QMException
  {
      //获得collection中关联对象角色为"uses"的Mastered值对象的集合。
      //返回masterCollection
     // Collection masterCollection = ConfigSpecHelper.mastersOf(collection, "uses");

      long t1 = System.currentTimeMillis();
      Collection masterCollection =mastersOf(collection);
      long t2 = System.currentTimeMillis();
      //System.out.println("mastersOf方法 用时： "+(t2-t1)+" 毫秒");
      ConfigService cservice = (ConfigService)EJBServiceHelper.getService("ConfigService");
      //返回masterCollection中mastered对象管理的符合配置规范configSpec的
      //所有迭代版本的对象的集合，返回值为iteratedCollection
      Collection iteratedCollection = cservice.filteredIterationsOf(masterCollection, new PartConfigSpecAssistant(configSpecIfc));
      long t22 = System.currentTimeMillis();
      //System.out.println("cservice.filteredIterationsOf方法 用时： "+(t22-t2)+" 毫秒");
      //对于给定的master和这些master所管理的iteration对象的集合，
      //返回所有的iteration对象和所有在iteration集合中没有任何一个iteration对象
      //与之相匹配的master对象。返回对象集合为allCollection
      Collection allCollection = ConfigSpecHelper.recoverMissingMasters(masterCollection,iteratedCollection);
      long t222 = System.currentTimeMillis();
      //System.out.println("recoverMissingMasters方法 用时： "+(t222-t22)+" 毫秒");
      //根据指定的关联集合（master与iteration之间）和对应master对象的iteration集合，
      //查找每个关联所连接的iteration对象，重建结果集（在每个结果数组的0位置存放关联对象，
      //在1位置存放iteration对象，如果没有对应的iteration对象，
      //则存放关联对象连接的master对象 。
      Collection coll = ConfigSpecHelper.buildConfigResultFromLinks(collection,"uses",allCollection);
      long t2222 = System.currentTimeMillis();
      //System.out.println("buildConfigResultFromLinks方法 用时： "+(t2222-t222)+" 毫秒");
      return coll;
  }
  /**
   * 目前定制代码未使用，2009-04-14。
   * 返回指定的关联（master与iteration之间）集合中每个关联对象连接的
   * mastered对象（指定角色master）的结果集
   * @param links 关联类值对象集合
   * @param role 角色名
   * @exception com.faw_qm.config.exception.ConfigException
   * @return 对应关联类值对象的Mastered对象集合。
   */
  private Collection mastersOf(Collection links) throws QMException
  {
      Vector vector = (Vector) links;
      Vector resultVector = new Vector();
      for (int i = 0; i < vector.size(); i++)
      {
          PartUsageLinkIfc obj = (PartUsageLinkIfc) vector.elementAt(i);
          String bsoID;
          try
          {
              bsoID = obj.getRoleBsoID("uses");
          }
          catch (QMException e)
          {
              throw new QMException(e, "根据角色名获得其BsoID时出错");
          }
          BaseValueIfc bsoObj;
          PersistService persistService = (PersistService) EJBServiceHelper
                  .getService("PersistService");
          bsoObj = (BaseValueIfc) persistService.refreshInfo(bsoID, false);
          if(!(bsoObj instanceof QMPartMasterIfc))
          {
              throw new QMException();
          }//endif 如果collection中的元素左连接对象object不是MasteredIfc的
          //实例，抛出角色无效例外
          resultVector.addElement(bsoObj);
      }//endfor i
      return removeDuplicates(resultVector);
  }
  /**
 * 将指定的结果集中重复的元素排除。
 * @param collection 结果集
 * @return Collection  排除了重复数据的集合
 * Collection中每一个元素为一Object数组
 * 该Object数组中的第0个元素为一值对象
 */
private Collection removeDuplicates(Collection collection)
        throws QMException
{
	//System.out.println("removeDuplicates11111111111111111111111111111");
    Hashtable hashtable = new Hashtable();
    Vector resultVector = new Vector();
    for (Iterator ite = collection.iterator(); ite.hasNext();)
    {
        BaseValueInfo obj = (BaseValueInfo) ite.next();
        String objBsoID = obj.getBsoID();
        boolean flag = hashtable.containsKey(objBsoID);
        if(flag == true)
            continue;
        hashtable.put(objBsoID, "");//将BsoID做为标志放入Hash表
        resultVector.addElement(obj);
    }//endfor i
    return resultVector;
}



public ArrayList gatherExportData(String routeListID, String IsExpandByProduct)
throws
QMException
{
return ReportLevelOneUtil.getAllInformationColl(routeListID,IsExpandByProduct);
}

//CCBegin by leixiao 2010-3-1 解放图纸及文档附件导出
public HashMap ExportFile(Vector list,String path, String type)
throws QMException{
	setVersionIBA("发布源版本");
	HashMap exportList=new HashMap();
	ContentService contentService = (ContentService) EJBServiceHelper
	.getService("ContentService");
	for(int i=0;i<list.size();i++){
		Vector filecotent=new Vector();
		String [] l=(String[])list.get(i);
		String partnumber=(String)l[0];
		String partversion=(String)l[1];
		Vector data=getDataByNumber(partnumber,partversion,type);
		if(data==null)
			continue;
		for(int j=0;j<data.size();j++){			
		  BaseValueIfc doc=(BaseValueIfc)data.get(j);
		 // System.out.println("doc="+doc);
	      Vector content  = contentService.getContents((ContentHolderIfc)doc);
	    //  System.out.println("doc="+content);
	      if(content==null||content.size()==0)
	    	  continue;
	   //   System.out.println("content="+content);
	      for(int k=0;k<content.size();k++){
	    	  Object o=content.get(k);
	    	  if(o instanceof ApplicationDataInfo){
	    	  ApplicationDataInfo a=(ApplicationDataInfo)content.get(k);	    	  
	    	  //CCBegin SS6
	    	  if(fileVaultUsed)
	    	  {
	    		  ContentClientHelper helper = new ContentClientHelper();
          		  byte[] b = helper.requestDownload(a.getBsoID());
          		  filecotent.add(new Object[]{a.getFileName(),b});
	    	  }
	    	  else
	    	  {
	    		//  System.out.print("ApplicationDataInfo="+a);
		    	  StreamDataInfo  stream=StreamUtil.getInfoHasContent(a.getStreamDataID());
		    	//  System.out.print("stream="+stream);
		    	  filecotent.add(new Object[]{a.getFileName(),stream});
	    	  }
	    	  //CCEnd SS6
	    	  }
	      }
		}
		exportList.put(partnumber+"_"+partversion,filecotent);

	}
	System.out.println("exportList="+exportList);
	return exportList;
	
}

private void setVersionIBA(String name)  throws QMException {
	PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
	QMQuery query = new QMQuery("StringDefinition");
	QueryCondition qc = new QueryCondition("displayName", " = ",name);
	query.addCondition(qc);
	Collection col = pservice.findValueInfo(query, false);
	Iterator iba = col.iterator();
	
	if (iba.hasNext()) {
		StringDefinitionIfc s = (StringDefinitionIfc) iba.next();
		ibaid = s.getBsoID();
	}
	
}


private Vector getEpmDocFromPart(String partid)throws QMException
{
  //System.out.println("---part="+partid);
  
  PersistService pService = (PersistService)EJBServiceHelper.getService("PersistService");
  QMPartIfc partIfc = (QMPartIfc)pService.refreshInfo(partid);
  Vector retVal = new Vector();
  retVal.add(partIfc);
  QMQuery query = new QMQuery("EPMBuildHistory");
  QueryCondition condition = new QueryCondition("rightBsoID","=",partid);
  query.addCondition(condition);
  Collection coll = pService.findValueInfo(query);
  Iterator iter = coll.iterator();
  while(iter.hasNext())
  {
    EPMBuildHistoryIfc link = (EPMBuildHistoryIfc)iter.next();
    EPMDocumentInfo doc = (EPMDocumentInfo)link.getBuiltBy();
    retVal.add(doc);

  }
  
  System.out.println(" 零件id为＝"+partid+" 文档为＝"+retVal);
    return retVal;
}

private Vector getDataByNumber(String number, String version,
		String type) {
	System.out.println("\n----"+number+"----"+version+"------"+type);

	PersistService pservice;
	Collection coll;
	try {
		pservice = (PersistService) EJBServiceHelper
		.getPersistService();
		if(type.equals("零件图纸")){
		QMQuery query = new QMQuery("QMPart");
		int i = query.appendBso("QMPartMaster", false);
		//query.setChildQuery(false);
		query.addCondition(0,i,new QueryCondition("masterBsoID","bsoID"));
		query.addAND();
		query.addCondition(i,new QueryCondition("partNumber","=",number));
		query.addAND();
//		query.addCondition(0,new QueryCondition("versionID","=",version));
//		query.addAND();
		query.addCondition(0,new QueryCondition("iterationIfLatest",true));
		query.addAND();
		query.addCondition(0,new QueryCondition("workableState","<>","wrk"));
//		System.out.println("--"+query.getDebugSQL());
		coll = pservice.findValueInfo(query, false);
		//System.out.println("-----零件"+coll);
		
		if(coll!=null&&coll.size()!=0){
	      String partid=getPartidFromIBA(coll,version);
	      if(partid!=null)			
			return getEpmDocFromPart(partid);
		}
		else
			return null;
	}
	else if (type.equals("文档内容")){
		QMQuery query = new QMQuery("Doc","DocMaster");
		query.setChildQuery(false);
		query.addCondition(0,1,new QueryCondition("masterBsoID","bsoID"));
		query.addAND();
		query.addCondition(1,new QueryCondition("docNum","=",number));
		query.addAND();
		query.addCondition(0,new QueryCondition("versionID","=",version));
		query.addAND();
		query.addCondition(0,new QueryCondition("iterationIfLatest",true));
		query.addAND();
		query.addCondition(0,new QueryCondition("workableState","<>","wrk"));
		query.setVisiableResult(1);
		
		System.out.println("--"+query.getDebugSQL());		
		coll = pservice.findValueInfo(query, false);
		System.out.println("coll="+coll);
		Vector doc=new Vector();
		if(coll!=null&&coll.size()!=0){ 
			Iterator iter=coll.iterator();
			if(iter.hasNext()){
			doc.add((BaseValueIfc)iter.next());
			}
		}
		return doc;
	}
		//CCBegin by leixiao 2010-12-15 解放图纸导出增加一类小版本文档内容导出
	else if (type.equals("小版本文档内容")){
		QMQuery query = new QMQuery("Doc","DocMaster");
		query.setChildQuery(false);
		query.addCondition(0,1,new QueryCondition("masterBsoID","bsoID"));
		query.addAND();
		query.addCondition(1,new QueryCondition("docNum","=",number));
		query.addAND();
		query.addCondition(0,new QueryCondition("versionValue","=",version));
//		query.addAND();
//		query.addCondition(0,new QueryCondition("iterationIfLatest",true));
		query.addAND();
		query.addCondition(0,new QueryCondition("workableState","<>","wrk"));
		query.setVisiableResult(1);
		
		System.out.println("--"+query.getDebugSQL());		
		coll = pservice.findValueInfo(query, false);
		System.out.println("coll="+coll);
		Vector doc=new Vector();
		if(coll!=null&&coll.size()!=0){ 
			Iterator iter=coll.iterator();
			if(iter.hasNext()){
			doc.add((BaseValueIfc)iter.next());
			}
		}
		return doc;
	}
	//CCEnd by leixiao 2010-12-15 解放图纸导出增加一类"小版本文档内容"导出
	else{
		System.out.println("不存在这种分类");
		return null;
	}

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

		
		
	return null;

}

private String getPartidFromIBA(Collection coll,String version) {

	String partid=null;
  
	String [] ids = new String[coll.size()];
	Iterator ii=coll.iterator();
	int n=0;
	while(ii.hasNext()){				
		QMPartIfc part=(QMPartIfc)ii.next();
		ids[n]=part.getBsoID();
		n++;
	}
	//System.out.println(ids);
	PersistService pservice=null;	
	Collection col=null;
	try {
		pservice = (PersistService) EJBServiceHelper
		.getPersistService();
	
		QMQuery query1 = new QMQuery("StringValue");
		query1.addCondition(new QueryCondition("iBAHolderBsoID", "In",
				ids));
		query1.addAND();
		query1.addCondition(new QueryCondition("definitionBsoID", "=",
				ibaid));
		query1.addOrderBy("modifyTime",true);
		//System.out.println(query1.getDebugSQL());
    col = pservice.findValueInfo(query1, false);
  // System.out.println("col="+col);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

    Iterator iba=col.iterator();
    String partversion="";
    while(iba.hasNext()){
    	StringValueIfc s=(StringValueIfc)iba.next();    	
    	partversion=s.getValue();
    	
    	//System.out.println("id="+s.getIBAHolderBsoID()+" v="+partversion +" v==="+version);
    	if(partversion.indexOf(version)!=-1)
    	{
    		//System.out.println("partversion="+partversion+" 需要的="+version );	
    		partid=	s.getIBAHolderBsoID();
    		return partid;
    	}
    }
return null;
}


public String isInGroup(String groupname)
throws
QMException
{
SessionService sservice = (SessionService) EJBServiceHelper.getService(
    "SessionService");
UserInfo userinfo = (UserInfo) sservice.getCurUserInfo();
Group group = getGroup(groupname);
Group adm = getGroup("Administrators");
PersistService persistService = (PersistService) EJBServiceHelper.
                            getPersistService();
//查询关联关系
QMQuery query = new QMQuery("GroupUser");
QueryCondition cond = new QueryCondition("rightBsoID", "=",
                                     userinfo.getBsoID());
QueryCondition cond1 = new QueryCondition("leftBsoID", QueryCondition.IN,
                                      new String[]{group.getBsoID(),adm.getBsoID()});
query.addCondition(cond);
query.addAND();
query.addCondition(cond1);
Collection coll = persistService.findBso(query);
if (coll.size() == 0)
{
return "false";
}
else
{
return "true";
}
}
/**
 * 根据用户组名获得用户组
 * @param name  用户组名
 * @return Group  获得的用户组
 * @throws QMException
 */
private Group getGroup(String name)
        throws QMException
{

    //构造查询语句
    QMQuery query = new QMQuery("Group");
    QueryCondition cond = new QueryCondition("usersName", "=", name);
    query.addCondition(cond);
    //获取持久化服务并进行查询
    PersistService persistService = (PersistService) EJBServiceHelper.
                                    getPersistService();
    Collection coll = persistService.findBso(query);
    //若未找到满足条件的用户则返回空
    if (coll.size() == 0)
    {
        return null;
    }
    Iterator iter = coll.iterator();
    Group group = (Group) iter.next();

    return group;
}
//CCEnd by leixiao 2010-3-1 解放图纸及文档附件导出

public  void handleHistoryCapp()throws QMException{
    QMQuery query = new QMQuery("QMFawTechnics");
    query.addCondition(new QueryCondition("versionID", QueryCondition.NOT_EQUAL, "A"));
    query.addAND();
	query.addCondition(new QueryCondition("iterationIfLatest",Boolean.TRUE));
    query.addAND();
	query.addCondition(new QueryCondition("workableState","=","c/i"));
    query.addAND();
    query.addCondition(new QueryCondition("lifeCycleState", QueryCondition.IN, new String[]{"RELEASED","RECEIVING","QIYENEIKONG"}));
  //  System.out.println(query.getDebugSQL());
    //获取持久化服务并进行查询
    PersistService persistService = (PersistService) EJBServiceHelper.
                                    getPersistService();
    Collection coll = persistService.findValueInfo(query,false);
    //若未找到满足条件的用户则返回空
    System.out.println(query.getDebugSQL());
//    System.out.println("-------"+coll);
    if (coll.size() != 0)
    {
    	Iterator iter = coll.iterator();
    	while(iter.hasNext()){
    		QMFawTechnicsIfc capp=(QMFawTechnicsIfc)iter.next();   
    		System.out.println("------"+capp.getBsoID()+" "+capp.getTechnicsNumber()+"--"+capp.getVersionValue()+" "+capp.getLifeCycleState()+" "+capp.getWorkableState());
    	try{
    		GrantPowerHelper.setHistoryObjectState(capp,"CANCELLED");	
    	}
    	catch(Exception e){
    		e.printStackTrace();
    		continue;
    		
    	}
    	}
    }

}

public  void handleckwd(String issave)throws QMException{
	 QMQuery query = new QMQuery("Doc");
	    //版本不为A.1的
	    query.addCondition(new QueryCondition("versionValue", QueryCondition.NOT_EQUAL, "A.1"));
	    query.addAND();
	    //是最新版本的
		query.addCondition(new QueryCondition("iterationIfLatest",Boolean.TRUE));
	    query.addAND();
	    //管理员更新的
		query.addCondition(new QueryCondition("iterationModifier","=","User_113"));
	    query.addAND();
	    //分类为此三类的 历史类\更改单 历史类\图纸  历史类\明细表
	    query.addCondition(new QueryCondition("docCfBsoID", QueryCondition.IN, new String[]{"DocClassification_779260","DocClassification_779267","DocClassification_779270"}));
	  //  System.out.println(query.getDebugSQL());
	    //获取持久化服务并进行查询
	    query.setChildQuery(false);
	    PersistService persistService = (PersistService) EJBServiceHelper.
	                                    getPersistService();
	    Collection coll = persistService.findValueInfo(query,false);
	    //若未找到满足条件的用户则返回空
	  //  System.out.println(query.getDebugSQL());
	    System.out.println("coll----"+coll.size());
	    if (coll.size() != 0)
	    {
	    	Iterator iter = coll.iterator();
	    	while(iter.hasNext()){
	    		DocIfc doc=(DocIfc)iter.next(); 
	    		createDependencyLink(doc,issave);
	    		//System.out.println("------"+doc.getBsoID()+" "+doc.getDocNum()+"--"+doc.getVersionValue());

	    	}
	    }
}

private void createDependencyLink(DocIfc doc,String issave)throws QMException {
	//System.out.println("------需要处理的文档"+doc);
    QMQuery query1 = new QMQuery("Doc");
    //版本不为A.1的
	query1.addCondition(new QueryCondition("branchID", "=",doc.getBranchID()));
	query1.addAND();
	query1.addCondition(new QueryCondition("iterationIfLatest",Boolean.FALSE));
	query1.addAND();
	query1.addCondition(0,new QueryCondition("workableState","=","c/i"));	
    //获取持久化服务并进行查询
	query1.setChildQuery(false);
    PersistService persistService = (PersistService) EJBServiceHelper.
                                    getPersistService();
   Collection coll1 = persistService.findValueInfo(query1,false);
   
//   System.out.println(query1.getDebugSQL());
//   System.out.println(coll1);
   if(coll1.size()!=0){
   	Iterator iter1 = coll1.iterator();
   	String temp[]=new String[coll1.size()];
   	int i=0;
	while(iter1.hasNext()){ 
		DocIfc doc1=(DocIfc)iter1.next();
		temp[i]=doc1.getBsoID();
		i++;		
	}
	 String leftdocstring=null;
	 QMQuery queryself = new QMQuery("DocDependencyLink");
	 queryself.addCondition(new QueryCondition("rightBsoID", "=", doc.getBsoID()));
	   Collection collself = persistService.findValueInfo(queryself,false);
	 //  System.out.println("------------"+collself);
	   if(collself.size()!=0){
			Iterator iterself = collself.iterator();
			while(iterself.hasNext()){
				   DocDependencyLinkInfo dlink=(DocDependencyLinkInfo)iterself.next();
				   leftdocstring+=dlink.getLeftBsoID()+",";
	   }
	   }
	   
	 //  System.out.println("已存的="+leftdocstring);

		 QMQuery query2 = new QMQuery("Doc");
		 int q=query2.appendBso("DocDependencyLink", false);
		 query2.addCondition(q,new QueryCondition("rightBsoID", QueryCondition.IN, temp));
		 query2.addAND();
		 query2.addCondition(q,0,new QueryCondition("leftBsoID", "bsoID"));
		 query2.addAND();
		 query2.addCondition(0,new QueryCondition("workableState","=","c/i"));	
		 query2.setDisticnt(true);
		 query2.setChildQuery(false); 
	 
	   Collection coll2 = persistService.findValueInfo(query2,false);
	 //  System.out.println("------------"+coll2);

	   if(coll2.size()!=0){		   
		   	Iterator iter2 = coll2.iterator();
			while(iter2.hasNext()){
				DocIfc linkdoc=(DocIfc)iter2.next();
			   System.out.println(""+doc+","+doc.getDocNum()+","+doc.getDocCfName()+","+doc.getVersionValue()+","+linkdoc.getBsoID()+","+linkdoc.getDocNum());
               if(leftdocstring!=null&&leftdocstring.indexOf(linkdoc.getBsoID())!=-1){
            	   System.out.println("-----------已有的,跳出");
	            continue;
               }
               if(issave.equals("save"))
               { 
            	   System.out.println("-----------保存了"+doc.getBsoID()+" "+linkdoc.getBsoID()); 
	            DocDependencyLinkInfo link = new DocDependencyLinkInfo();
	            link.setRightBsoID(doc.getBsoID());
	            link.setLeftBsoID(linkdoc.getBsoID());
	            PersistService pService = (PersistService)EJBServiceHelper.getPersistService();
	            link = (DocDependencyLinkInfo)pService.saveValueInfo(link);
               }
			}   
		   }
	   
		  //  System.out.println(query.getDebugSQL());
   }
	
}




    /**
     * CCBegin by liunan 2012-04-28
     * 获得路线信息，整体处理，in的sql方式。每次搜索20个，
     * 这个数是在解放测试机上多次测试结果得出的效率最佳个数。
     * @param routeTempMap HashMap route值的缓存，key为partMasterBsoID+route名称，值就是这个part对应routename的值。
     * @param bomMap HashMap 所有报表中涉及的零部件的集合。key为partBsoID，
     * 值为二维数组，第一个元素是partMasterBsoID，第二个元素是其父件的partMasterBsoID。
     * @return HashMap 返回routeTempMap。
     */
    private HashMap getAllRouteValue(HashMap routeTempMap, HashMap bomMap)
    {
    	//零部件masterbsoid集合
    	String[] routeNames = (String[])routeTempMap.get("routeName");
    	int routeNameSize = routeNames.length;
    	String partMasterID = "";
    	boolean isHasRouteList = false;
    	//记录当前向partid集合中添加的零部件个数
    	int i = 0;
    	//调数据库次数
    	int k = 0;
    	for(int a=0;a<routeNameSize;a++)
    	{
    		//System.out.println("routeNames[a]==="+routeNames[a]);
    		if(routeNames[a].equals("艺准编号")||routeNames[a].equals("备注"))
    		{
    			isHasRouteList = true;
    		}
    	}
    	//System.out.println("isHasRouteList==="+isHasRouteList);
    	// 以20个零部件为单位进行一次查询，得到该零部件的route属性

    	//select branch.routestr,link.rightbsoid,link.leftbsoid, route.modefyIdenty from technicsroutebranch branch, listroutepartlink link,technicsroute route,technicsroutelist tcl where branch.routeid=link.routeid and route.bsoid=branch.routeid and link.rightbsoid in ('QMPartMaster_54181538') and tcl.bsoid=link.leftbsoid and tcl.iterationiflatest=1 and tcl.routeliststate!='临准' and tcl.owner is null order by tcl.modifytime desc
    	//构造sql，得到全部路线。后续要处理去掉已取消的路线。
    	//CCBegin by liunan 2009-06-10 添加TechnicsRouteList不为临准、technicsroute不是取消状态，去掉alterstatus='1'条件。
       //CCBegin SS9
    	//String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid, route.modefyIdenty from technicsroutebranch branch, listroutepartlink link,technicsroute route,technicsroutelist tcl where branch.routeid=link.routeid and route.bsoid=branch.routeid and link.rightbsoid in (";
    	String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid, route.modefyIdenty,link.colorflag from technicsroutebranch branch, listroutepartlink link,technicsroute route,technicsroutelist tcl where branch.routeid=link.routeid and route.bsoid=branch.routeid and link.rightbsoid in (";
    	//CCEnd SS9 	//CCBegin by liunan 2012-04-25 李萍提出个人资料夹的艺准不取其路线。    	
    	String sql2 = ") and tcl.bsoid=link.leftbsoid and tcl.iterationiflatest=1 and tcl.routeliststate!='临准' and tcl.owner is null order by tcl.modifytime desc";
    	//CCEnd by liunan 2012-04-25
    	//CCEnd by liunan 2009-06-10

    	Connection conn = null;
      Statement stmt =null;
      ResultSet rs=null;
  	  try
  	  {
  	  	PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
  	    //搜索表，根据艺准的masterid搜索数量。
    	  conn = PersistUtil.getConnection();
      	stmt = conn.createStatement();
      	//System.out.println(sql1+partMasterID+sql2);
      	Set idSet = bomMap.keySet();
      	Iterator iter = idSet.iterator();
      	HashMap trlmap = new HashMap();
      	while(iter.hasNext())
      	{
      		String partKey = (String)iter.next();
      		String masterID[] = (String[])bomMap.get(partKey);
      		if(partMasterID.equals(""))
      		{
      			partMasterID = "'" + masterID[0] + "'";
      		}
      		else
      		{
      			partMasterID = partMasterID + ",'" + masterID[0] + "'";
      		}
      		i++;
      		if (i == routebasenum || !iter.hasNext())
      		{      			
      			rs = stmt.executeQuery(sql1+partMasterID+sql2);
      			while(rs.next())
      			{
      				String keyName = "";
      				TechnicsRouteListIfc routelist = null;
      				if(isHasRouteList)
      				{
      					if(trlmap.containsKey(rs.getString(3)))
      					{
      						routelist = (TechnicsRouteListIfc)trlmap.get(rs.getString(3));
      					}
      					else
      					{
      						routelist = (TechnicsRouteListIfc) pService.refreshInfo(rs.getString(3), false);
      						trlmap.put(rs.getString(3),routelist);
      					}      					
      				}
      				//if(rs.getString(2).equals("QMPartMaster_119298465"))
      				//System.out.println(rs.getString(1)+"====="+rs.getString(2)+"====="+rs.getString(3)+"====="+rs.getString(4));
      				for(int ii=0;ii<routeNameSize;ii++)
      				{
      					//由于是路线串，所以只取一个就行。  ||routeNames[ii].equals("装配路线")
      					if(routeNames[ii].equals("制造路线"))
      					{
      						keyName = rs.getString(2)+"路线";
    			  			//System.out.println("keyName==="+keyName);
      						if(routeTempMap.containsKey(keyName))
      						{
      							HashMap routemap = (HashMap)routeTempMap.get(keyName);
      							if(routemap.containsKey(rs.getString(3)+rs.getString(4)))
      							{
      								//CCBegin SS5
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
      								//CCEnd SS5
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
      					}
      					else if(routeNames[ii].equals("艺准编号"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							routeTempMap.put(keyName,routelist.getRouteListNumber());
      						}
      					}
      					//CCBegin SS14
      					else if(routeNames[ii].equals("艺准说明"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							String des = routelist.getRouteListDescription();
      							if(des==null)
      							{
      								des = "";
      							}
      							des = des.trim().replaceAll("\n|\r", "");
      							if(des.indexOf("说明：")!=-1)
      							{
      								des = des.substring(des.indexOf("说明："),des.length());
      							}
      							routeTempMap.put(keyName, des);
      						}
      					}
      					else if(routeNames[ii].equals("艺准状态"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							routeTempMap.put(keyName,routelist.getLifeCycleState().getDisplay());
      						}
      					}
      					//CCEnd SS14
      						//CCBegin SS9
      					else if(routeNames[ii].equals("颜色件标识"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							String color="";
      							if(rs.getString(5)!=null&&rs.getString(5).equals("1")){
      								color="是";
      							}
      							routeTempMap.put(keyName,color);
      						}
      					}
      					//CCEnd SS9
      					else if(routeNames[ii].equals("备注"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							//CCBegin SS12
      							//routeTempMap.put(keyName,routelist.getRouteListDescription());
      							//CCBegin SS18
      							//String des = routelist.getRouteListDescription();
      							String des = routelist.getRouteListDescription().trim().replaceAll("\n|\r", "");
      							//CCEnd SS18
      							if(des==null)
      							{
      								des = "";
      							}
      							routeTempMap.put(keyName,des);
      							//CCEnd SS12
      						}
      					}
      				}
      				
      				//CCBegin SS4 SS17
      				//得到最新路线保存 anan
      				/*if(!rs.getString(4).equals("Coding_221664")&&!routeTempMap.containsKey(rs.getString(2)+"最新路线"))
      				{
      					routeTempMap.put(rs.getString(2)+"最新路线",rs.getString(1));
      				}*/
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
      				//CCEnd SS4 SS17
      				
      			}
    				//System.out.println("routeTempMap==="+routeTempMap);
    				//将路线串处理。
    			  String temp[] = partMasterID.split(",");
    			  for(int ii = 0;ii<temp.length;ii++)
    			  {
    			  	String id = temp[ii].substring(1,temp[ii].length()-1);
    			  	HashMap routemap = (HashMap)routeTempMap.get(id+"路线");
    			  	//if(id.equals("QMPartMaster_119298465"))
    			  	//System.out.println("routemap==="+routemap);
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
    			  	/*if(id.equals("QMPartMaster_119298465"))
    			  	{
    			  		System.out.println("routevec======"+routevec);
    			  		System.out.println("canelvec======"+canelvec);
    			  	}*/
    			  	
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
    			  		//if(id.equals("QMPartMaster_119298465"))
    			  		//System.out.println("after routevec======"+routevec);
    			  		for(int iii=0;iii<routevec.size();iii++)
    			  		{
    			  			String strroute = routevec.elementAt(iii).toString();
    			  			String temproute[] = strroute.split(",");
    			  			for(int iiii=0;iiii<temproute.length;iiii++)
    			  			{
    			  				String str = temproute[iiii];
    			  				/*if(id.equals("QMPartMaster_119298465"))
    			  				{
    			  				  System.out.println("str======"+str);
    			  				  System.out.println("getZhiZaoRoute(str)======"+getZhiZaoRoute(str));
    			  				  System.out.println("begein zhizaostr======"+zhizaostr);
    			  			  }*/
    			  				if(zhizaostr.equals(""))
    			  				{
    			  					zhizaostr = getZhiZaoRoute(str);
    			  				}
    			  				//CCBegin SS2
    			  				//else if(zhizaostr.indexOf(getZhiZaoRoute(str))==-1)//anan 可以判断如果zhizaostr里面包含getZhiZaoRoute(str)但有不一样就添加？想要处理 协--架(漆)/协  到  总 这种情况。
    			  				else if(!hasStr(zhizaostr,getZhiZaoRoute(str)))
    			  				//CCEnd SS2
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
    			  		
    			  		//CCBegin SS1
    			  		if((routevec==null||routevec.size()==0)&&zhizaostr.equals("")&&zhizaostr.equals(""))
    			  		{
    			  			//CCBegin SS4
    			  			/*HashMap routemap1 = (HashMap)routeTempMap.get(id+"路线");
    			  			if(routemap1!=null)
    			  			{
    			  				Set routeSet1 = routemap1.keySet();
    			  				Iterator routeiter1 = routeSet1.iterator();
    			  				if(routeiter1.hasNext())
    			  				{
    			  					String str = (String)routeiter1.next();
    			  					String str1 = routemap1.get(str).toString();
    			  					System.out.println("str1========="+str1);
    			  				  zhizaostr = getZhiZaoRoute(str1);
    			  				  zhuangpeistr = getZhuangPeiRoute(str1);
    			  				}
    			  			}*/
    			  			//CCBegin SS17
    			  			/*String str1 = routeTempMap.get(id+"最新路线").toString();
    			  			System.out.println("str1========="+str1);
    			  			zhizaostr = getZhiZaoRoute(str1);
    			  			zhuangpeistr = getZhuangPeiRoute(str1);*/
    			  			
    			  			String str1 = routeTempMap.get(id+"最新路线").toString();
    			  			//if(id.equals("QMPartMaster_119298465"))
    			  			//System.out.println("最新路线 str1========="+str1);
    			  			str1 = str1.substring(str1.indexOf(";")+1,str1.length());
    			  			String temproute[] = str1.split(",");
    			  			for(int iiii=0;iiii<temproute.length;iiii++)
    			  			{
    			  				String str = temproute[iiii];
    			  				if(zhizaostr.equals(""))
    			  				{
    			  					zhizaostr = getZhiZaoRoute(str);
    			  				}
    			  				//CCBegin SS2
    			  				//else if(zhizaostr.indexOf(getZhiZaoRoute(str))==-1)//anan 可以判断如果zhizaostr里面包含getZhiZaoRoute(str)但有不一样就添加？想要处理 协--架(漆)/协  到  总 这种情况。
    			  				else if(!hasStr(zhizaostr,getZhiZaoRoute(str)))
    			  				//CCEnd SS2
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
    			  			//CCEnd SS4 SS17
    			  		}
    			  		//CCEnd SS1
    			  	}
    			  }
    			  //System.out.println("制造路线======"+zhizaostr);
    			  //System.out.println("装配路线======"+zhuangpeistr);
    			  	routeTempMap.remove(id+"路线");
    			  	routeTempMap.put(id+"制造路线",zhizaostr);
    			  	routeTempMap.put(id+"装配路线",zhuangpeistr);    			  	
    			  }

      			// 把i置0，重新开始计数
      			i = 0;
      			// 调数据库次数
      			k++;
      			// bomMap集合中余下零部件数量
      			//int next = bomMap.size() - routebasenum * k;
      			//System.out.println("第"+k+"次-------余下数量＝"+next);
      			partMasterID = "";
				  }
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
    	return routeTempMap;
    }
    //CCEnd by liunan 2012-04-28
    
}
