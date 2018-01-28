/**
 * SS1 ���װ��·��Ϊ�գ���ȡ��������·�ߡ� liunan 2012-7-10
 * SS2 ��������·�߲�ͬ·�ߵİ�����ϵ�� liunan 2012-11-12
 * SS3 ����ͳ�Ʊ���ʱ�����ѡ��������·�ߺ�װ��·�����ԣ������ liunan 2012-12-3
 * SS4 ��ȡȫ·��ʱ��Ϊÿ���㲿���õ�һ������·�ߣ�������ǰ����ȡȫ·��ʱ���ŵ������У�
 * ������㲿������·�߶���ȡ�����ģ���ô�õ���·�߽��Ϊ�գ���ʱ�ͻ�ȡ����·�ߡ���Ϊ��������Ƕ�ȡ�����������µ��ֲ����ˡ� liunan 2014-1-15
 * SS5 �����׼�ж�һ���㲿�������˶���·�߱༭�������ظ�������������������ȡ��·��ʱ�жϳ��� liunan 2014-1-15
 * SS6 ����ļ���������֧�߼���  jiahx 2013-01-17
 * SS7 �޸�û�ж�Ȩ�޵��㲿��������ʱ����Ȩ�����⡣ ��ȡ�㲿�����Ȩ�޹��ˡ� liunan 2014-4-18
 * SS8 �߼��ܳ����������б�׼��Ӧ�����汾����׼������Q��CQ��T��ͷ������� liunan 2014-9-23
 * SS9 ���ӡ���ɫ����ʶ������ liuyang 2014-6-14
 * SS10 �߼��ܳɱ�����������ݣ���Ϊԭ�����ù淶�ǹ�����ͼ����Ϊ������ͼ����ڻ��棬���Ի����չ�����ͼ������� ������ 2014-11-18
 * SS11 �߼��ܳɱ�����������ݣ���Ϊԭ������ǹ�����ͼֱ��ͨ��sql����ǹ�����ͼ�ģ���Ϊ������������˹�����ͼ
 * ���Խ�ԭ��������ͼ��ѯ����ȥ�� ������ 2014-11-21
 * SS12 ·�߿�˵������ı����쳣�����ӿ��жϡ� liunan 2014-12-16
 * SS13 A004-2015-3182 �㲿���汾��ȡ˳�����1����ȡ�����汾��2����ȡ����Դ�汾��3�����ȡ����汾���еĻ��Ͳ�ȡ��һ���� liunan 2015-8-24
 * SS14 A004-2015-3216 ������׼˵����˵�������ݣ���״̬������� liunan 2015-11-10
 * SS15 A004-2015-3248 �������������µ��������򱨱��е���������汾 ���磺1160011-71U/AL01/A��ӦΪ1160011-71U/AL01��
 * �㲿����ŵ�һ����/����Ϊ��*L01*������0������*0��1��2��3��4����������ZBT������*(L)������AH������*J0*������*J1*������*-SF������BQ���͡�*-ZC���Ĳ��Ӱ汾�� liunan 2015-12-3
 * SS16 �߼��ܳɱ��������к��ж���ʱ��excel��ʾ����У�����ʾΪ���Ķ��š� liunan 2016-1-21
 * SS17 A004-2017-3465 ��ȫSS4���ݣ�����Ƕ�·�ߣ���Ҫȡ���µĶ���·�߷��ء� liunan 2017-2-14
 * SS18 ����������ע��Ϣ���С� liunan 2017-11-20
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
 * <p>Title: �����嵥</p>
 * <p>Description: �㲿��������ת����</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: ����</p>
 * @author liunan 2009-02-11
 * @version 1.0
 * @version 1.1 �����Ż� liunan 2009-04-14
 */

public class JFServiceEJB
    extends BaseServiceImp
{
  /**���л�ID*/
  static final long serialVersionUID = 1L;

  private String RESOURCE = "com.faw_qm.part.util.PartResource";

  //ibaÿ�β����ݿ�������������Ի�������������
  private static int basenum = 100;

  //routeÿ�β�ѯ���ݿ�������������Ի�������������
  private static int routebasenum = 100;//10;

  //��¼����in��ѯ�Ĵ���
  private static int subCount=0;

  private static long subTimeGong = 0;

  private static long subTimeOther = 0;

  private static String viewName = "";
  
//CCBegin by leixiao 2010-3-1 ���ͼֽ���ĵ���������  
  private  String ibaid = "";
//CCEnd by leixiao 2010-3-1 ���ͼֽ���ĵ���������
  
  //CCBegin SS6
  private static boolean fileVaultUsed = (RemoteProperty.getProperty(
          "registryFileVaultStoreMode", "true")).equals("true");
  //CCEnd SS6

  public String getServiceName()
  {
    return "JFService";
  }

    /**
     * �˷����ڵ���רΪ��Ŷ��Ƶķ��������Ͻ��е��Ż�����Ҫ�޸��˻���Ӽ���getUsesPartIfcs������
     * iba��route�Ļ�÷�ʽ���������øĳ������á�
     * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
     * ����ָ���㲿�������������б�͵�ǰ�û�ѡ���ɸѡ���������㲿����ͳ�Ʊ���Ϣ��
     * ������������bianli()����ʵ�ֵݹ顣
     * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
     * 1��������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true","false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
     * 2������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֡������������������ԡ�
     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����.
     * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼��ʹ�á�
     * �������Զ��ڴ������У���ͨ����-0��iba����-1��route����-2��
     * @param affixAttrNames : String[] ����δʹ�á�
     * @param routeNames : String[] ����δʹ�á�
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õĶԵ�ǰ�㲿����ɸѡ������
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
      	System.out.println("�û� "+userName+" ��ʼ�����㲿�� "+partIfc.getPartNumber()+" ͳ�Ʊ���");
      	long t1 = System.currentTimeMillis();
        WfUtil wfutil = new WfUtil();
        PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
        Vector vector = new Vector();
        float parentQuantity = 1.0F;

        //��¼�����ͱ�������������ڵ�λ�á�
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
        System.out.println("�˴�ͳ�Ʊ���ѡ���iba��·���У�"+specAttr+"==");

        PartUsageLinkInfo partLinkInfo = new PartUsageLinkInfo();
        // iba�Ļ��棬���������iba���ԣ����һ���keyΪibaName��ֵΪiba���������ϣ�
        // ������Ϊ�����㲿����ibaֵ��keyΪpartBsoID+ibaName��ֵΪibaֵ��
        HashMap ibaTempMap = new HashMap();

        // route�Ļ��棬���������route���ԣ����һ���keyΪrouteName��ֵΪroute���������ϣ�
        // ������Ϊ�����㲿����routeֵ��keyΪpartMasterBsoID+routeName��ֵΪrouteֵ��
        HashMap routeTempMap = new HashMap();
      //CCBegin by leixiao 2010-4-16 �߼��ܳ�װ��·�߲�ȡ��������·��
        Vector islogic=new Vector();
        HashMap logicRoute=new HashMap();
      //CCEnd by leixiao 2010-4-16 
        // ���������������㲿�����ϣ�keyΪpartMasterBsoID��ֵΪ����partMasterBsoID��
        HashMap bomMap = new HashMap();
        

        // iba���Լ����ַ�����
        String ibaName = "";

        // route���Լ����ַ�����
        String routeName = "";

        //��ʼ������ʼ������
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
        
      //CCBegin by liunan 2012-05-24 Ϊ�˵õ������ϼ���������·�ߣ���Ҫ��¼����ڵ�ǰ���������и����ļ��ϡ�
      HashMap allBomMap = new HashMap();
      //vector = bianli(partIfc, partIfc, attrNames, affixAttrNames, routeNames, source, type, configSpecIfc, parentQuantity, partLinkInfo, bomMap,islogic);
      vector = bianli(partIfc, partIfc, attrNames, affixAttrNames, routeNames, source, type, configSpecIfc, parentQuantity, partLinkInfo, bomMap ,allBomMap ,islogic);
      //CCEnd by liunan 2012-05-24
        
        long t222 = System.currentTimeMillis();
        System.out.println("��bianli���� ��ʱ�� "+(t222-t111)/1000+" ��");

        //System.out.println("bomMap'size===="+bomMap.size()+"  and vector'size===="+vector.size());
        if(!ibaName.equals("")&&bomMap.size()>0)
        {
        	ibaTempMap.put("ibaName",ibaName.split(","));
        	long t12 = System.currentTimeMillis();
        	ibaTempMap = getIbaValue(ibaTempMap,bomMap);
        	long t21 = System.currentTimeMillis();
        	System.out.println("�� ��ȡiba ���� ��ʱ�� "+(t21-t12)+" ���룬ÿ������iba�ĸ���Ϊ�� "+basenum+" ��");
        }
        if(!routeName.equals("")&&bomMap.size()>0)
        {
        	routeTempMap.put("routeName",routeName.split(","));
        	long t1222 = System.currentTimeMillis();
        	        	
        	//CCBegin by liunan 2012-04-28 ��ȡȫ��·�ߡ�
        	//routeTempMap = getRouteValue(routeTempMap,bomMap);
        	routeTempMap = getAllRouteValue(routeTempMap,bomMap);        	
        	//CCEnd by liunan 2012-04-28
        	
        	long t2221 = System.currentTimeMillis();
        	System.out.println("�� ��ȡroute ���� ��ʱ�� "+(t2221-t1222)+" ���룬ÿ������route�ĸ���Ϊ�� "+routebasenum+" ��");
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
          //�ӻ����еõ�iba��route�ŵ���Ӧλ�á�
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
          				//���װ��Ϊ�գ����ø���������·�ߡ�
          				if(curRouteName.equals("װ��·��")&&(curRouteValue==null||curRouteValue.equals("")||curRouteValue.equals("/")))
          				{
          				//CCBegin by leixiao 2010-4-16
          					if(routeTempMap.containsKey(idStr[1]+"����·��"))
          					{
          						//װ��·��Ϊ��ʱȡ��������·�ߵĵ�һ����λ
          						String fuzhizhao=routeTempMap.get(idStr[1]+"����·��").toString();
          						if(fuzhizhao.indexOf("--")!=-1){
          						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("-"));          					   
          					    }
              					if(islogic.contains(temp1[0])){//������߼��ܳɲ�ȡ��������         						
              						logicRoute.put(idStr[0].toString(), fuzhizhao);
              					}
              					else if(routeTempMap.get(idStr[0]+"����·��").toString().indexOf("��")!=-1){
              						//����·�߰�����ʱ����ȡ�����ĵ�������Ϊװ��
              					}
              					else{
              						temp1[4 + k]=fuzhizhao;	
              					}          					
          				  }
          					else{
          						if(logicRoute.containsKey(idStr[1].toString())&&!islogic.contains(temp1[0])&&routeTempMap.get(idStr[0]+"����·��").toString().indexOf("��")==-1){
          							temp1[4 + k]=logicRoute.get(idStr[1].toString());
          						}
          					}
//          					CCEnd by leixiao 2010-4-16
          				}
          				
          				//CCBegin by liunan 2012-04-28 ��ȡȫ��·�ߡ�
          				//CCBegin SS3
          				//else if(curRouteName.equals("װ��·��")&&curRouteValue.indexOf("��")!=-1)
          				else if(curRouteName.equals("װ��·��")&&curRouteValue.indexOf("��")!=-1&&allBomMap.get(temp1[0])!=null)
          				//CCEnd SS3
          				{
          					String[] idallStr = (String[])allBomMap.get(temp1[0]);
          					String pid = idallStr[1];
          					String temppid[] = pid.split(";");
          					temp1[4 + k]=curRouteValue;
          					for(int ii = 0 ; ii< temppid.length; ii++)
          					{
          						String ppartid = temppid[ii];
          					if(routeTempMap.containsKey(ppartid+"����·��"))
          					{
          						// װ��·��Ϊ��ʱȡ��������·�ߵĵ�һ����λ
          						String fuzhizhao=routeTempMap.get(ppartid+"����·��").toString();
          						if(fuzhizhao.indexOf("--")!=-1){
          						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("-"));          					   
          					    }  
          					    
          						if(fuzhizhao.indexOf("/")!=-1){
          						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("/"));          					   
          					    }  
          					    
              					if(islogic.contains(temp1[0])){//������߼��ܳɲ�ȡ��������  
              						logicRoute.put(idStr[0].toString(), fuzhizhao);//�߼��ܳɵ�װ��·��
              					}
              					else if(routeTempMap.get(idStr[0]+"����·��").toString().indexOf("��")!=-1){
              						//����·�߰�����ʱ����ȡ�����ĵ�������Ϊװ��
              					}
              					else{
              						
              						if(temp1[4 + k].toString().indexOf(fuzhizhao)<0)
              							{
              								temp1[4 + k]=temp1[4 + k]+"/"+fuzhizhao;
              							}
              						
              					}          					
          				  }
          					else{
          						if(logicRoute.containsKey(idStr[1].toString())&&!islogic.contains(temp1[0])&&routeTempMap.get(idStr[0]+"����·��").toString().indexOf("��")==-1){//�丸�����߼��ܳɼ�����������
          							//System.out.println("logicRoute.get(idStr[1].toString())====="+logicRoute.get(idStr[1].toString()));
          							
          							if(temp1[4 + k].toString().indexOf(logicRoute.get(idStr[1].toString()).toString())<0)
          							{
          								temp1[4 + k]=temp1[4 + k]+"/"+logicRoute.get(idStr[1].toString()).toString();
          							}
          						}
          					}
          					
          				}
          				
          						temp1[4 + k]=temp1[4 + k].toString().replaceAll("/��","").replaceAll("��/","").replaceAll("��","");
          					//System.out.println("temp[4 + k]1==="+temp1[4 + k]);          						
          				}
          				//CCEnd by liunan 2012-04-28
          				else
          				{
          					//CCBegin SS3
          					//temp1[4 + k] = curRouteValue;
          					temp1[4 + k] = curRouteValue.toString().replaceAll("/��","").replaceAll("��/","").replaceAll("��","");
          					//CCEnd SS3
          				}
          			}
          			//���û��װ��·�ߣ�Ҳ�ø���������·�ߡ�
          			else
          			{
          			//CCBegin by leixiao 2010-4-16       				
          				if(curRouteName.equals("װ��·��")&&routeTempMap.containsKey(idStr[1]+"����·��"))
          				{
      						//CCBegin by leixiao 2010-4-16 װ��·��Ϊ��ʱȡ��������·�ߵĵ�һ����λ
      						String fuzhizhao=routeTempMap.get(idStr[1]+"����·��").toString();
      						if(fuzhizhao.indexOf("--")!=-1){
      						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("-"));          					   
      					    }
          					if(islogic.contains(temp1[0])){//������߼��ܳɲ�ȡ��������         						
          						logicRoute.put(idStr[0].toString(), fuzhizhao);
          					}
          					else{
          						temp1[4 + k]=fuzhizhao;	
          					}
          				}
      					else if(curRouteName.equals("װ��·��")){
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
        //�˴��޸������򷽷���λ�ã���ǰ���ڽ�����Ϻϲ�֮ǰ���������򷽷���ʱ�������ָ�Ϊ�ϲ���������
        long t22 = System.currentTimeMillis();
        System.out.println("�»�ȡiba��route���� ��ʱ�� "+(t22-t11)+" ����");
        long tsort1 = System.currentTimeMillis();
        resultVector = sortTongJiVectorNew(resultVector, partIfc, numberSite);
        long tsort2 = System.currentTimeMillis();
        System.out.println("���� ���� ��ʱ�� "+(tsort2-tsort1)+" ���룬����󼯺ϸ���vecotr.size==="+resultVector.size());

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
          //���ûѡ����Դ�����ͣ���������ѭ������
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
        System.out.println("������Դ���� ���� ��ʱ�� "+(t1122-t22)+" ����");
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
          System.out.println("��ǰ��ͼΪ��"+viewName+"�� ���в����Ӽ�����subTimeGong��ʱ��"+subTimeGong+" ����");
        else if(subTimeOther>0)
          System.out.println("��ǰ��ͼΪ��"+viewName+"�� ���в����Ӽ�����subTimeOther��ʱ��"+subTimeOther+" ����");
        else
          System.out.println("��ǰ��ͼΪ��"+viewName);
        System.out.println("�����㲿�� "+partIfc.getPartNumber()+" �������� "+(result.size()-1)+ " ���㲿���� iba���� "+subCount+" �Σ� �û� "+userName+" ��ʱ�� "+(t2-t1)/1000+" ��");
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
     * �ڶ����Ż���
     * �˷������ڵ���רΪ��Ŷ��Ƶķ��������Ͻ����޸ĵģ��Ż���getUsesPartIfcs������
     * �޸���iba������·����Ϣ�Ļ�÷�ʽ������bianli�л�ȡ������bianli��ɺ�ͳһ��ã�����ֵ��
     * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
     * ��������setBOMList�����ã�ʵ�ֵݹ���õĹ��ܡ�
     * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
     * 1��������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true","false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
     * 2������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֡������������������ԡ�
     * ���������������������iba��route��ȫ��""
     * @param partProductIfc :QMPartIfc ����ֵ����
     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����.
     * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
     * ���ƿ���ʱ�������Զ�������������У�ֻ�Ǻ�׺��ͬ����ͨ����-0��iba����-1��·������-2��
     * @param affixAttrNames : String[] ���ƿ���δʹ�á�
     * @param routeNames : String[] ���ƿ���δʹ�á�
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õ�ɸѡ������
     * @param parentQuantity :float ʹ���˵�ǰ�����Ĳ��������������ʹ�õ�������
     * @param partLinkIfc :PartUsageLinkIfc ���ӵ�ǰ�������丸�����Ĺ�����ϵֵ����
     * @param bomMap ��HashMap �������漰�������㲿�����ϣ�keyΪpartBsoID��
     * ֵΪ��ά���飬��һ��Ԫ����masterBsoID���ڶ���Ԫ���Ǹ���masterBsoID��
     * @throws QMException
     * @return Vector
     * @author liunan 2009-04-05
     */     

    private Vector bianli(QMPartIfc partProductIfc, QMPartIfc partIfc,
                          String attrNames[], String affixAttrNames[],
                          String[] routeNames, String source, String type,
                          PartConfigSpecIfc configSpecIfc, float parentQuantity,
                          //CCBegin by liunan 2012-05-24 Ϊ�˵õ������ϼ���������·�ߣ���Ҫ��¼����ڵ�ǰ���������и����ļ��ϡ�
                          //PartUsageLinkIfc partLinkIfc, HashMap bomMap,Vector islogic) throws
                          PartUsageLinkIfc partLinkIfc, HashMap bomMap, HashMap allBomMap,Vector islogic) throws
                          //CCEnd by liunan 2012-05-24
        QMException {
      //���bomMapΪ�����ʾ��һ�ν���bianli����������bomMap��ֵҲ���Ǹ��ڵ㡣
      if(bomMap==null||bomMap.size()==0)
      {
      	//����Ϊ����masterid����"root"���õ�ʱ�򻺴���û��"root"��Ҳ�Ͳ���õ�ֵ��
      	bomMap.put(partIfc.getBsoID(),new String[]{partIfc.getMasterBsoID(),"root"});
      }
      Vector resultVector = new Vector();
      Vector hegeVector = new Vector();
      long t1 = System.currentTimeMillis();
      Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);
      long t2 = System.currentTimeMillis();
      //System.out.println("����getUsesPartIfcs ����ʱ�䣺"+(t2-t1)+" ����");
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
            	//��partIfc���Ӽ�����ʽ���浽����bomMap�С�
            	if(!bomMap.containsKey(((QMPartIfc)obj1[1]).getBsoID()))
            	{
            	  bomMap.put(((QMPartIfc)obj1[1]).getBsoID(),new String[]{((QMPartIfc)obj1[1]).getMasterBsoID(),partIfc.getMasterBsoID()});
                //CCBegin by liunan 2012-05-24 Ϊ�˵õ������ϼ���������·�ߣ���Ҫ��¼����ڵ�ǰ���������и����ļ��ϡ�
            	  allBomMap.put(((QMPartIfc)obj1[1]).getBsoID(),new String[]{((QMPartIfc)obj1[1]).getMasterBsoID(),partIfc.getMasterBsoID()});
                //CCEnd by liunan 2012-05-24
              }
              
              //CCBegin by liunan 2012-05-24 Ϊ�˵õ������ϼ���������·�ߣ���Ҫ��¼����ڵ�ǰ���������и����ļ��ϡ�
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

      //��¼�����ͱ�������������ڵ�λ�á�
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
                //��Ҫ���ж�partLinkIfc�Ƿ��ǳ־û��ģ�������ǣ�parentQuantity = 1.0
                //�����:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
            	  //CCBeginby leixiao 2009-2-13 ԭ�򣺽����������·��,������������
                if (partLinkIfc == null ||!PersistHelper.isPersistent(partLinkIfc))
                {
                    parentQuantity = 1.0f;
                }
                else
                {
                    parentQuantity = parentQuantity *
                                     partLinkIfc.getQuantity();
                }
                //CCEndby leixiao 2009-2-13 ԭ�򣺽����������·��,������������
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
        //ibaֱ������Ϊ""���Ժ������ȡiba���ٸ�ֵ��
        else if (attrfull.endsWith("-1"))
        {
          	tempArray[4 + j] = "";
        }
        //routeֱ������Ϊ""���Ժ������ȡroute���ٸ�ֵ��
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
                                         //CCBegin by liunan 2012-05-24 Ϊ�˵õ������ϼ���������·�ߣ���Ҫ��¼����ڵ�ǰ���������и����ļ��ϡ�
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
     * ��һ���Ż���
     * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
     * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
     * ��������setBOMList�����ã�ʵ�ֵݹ���õĹ��ܡ�
     * �ڷ���ֵvector�У�ÿ��Ԫ��Ϊһ���ж���ַ��������飬�ֱ𱣴��㲿����������Ϣ��
     * 1��������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֣�"true","false"����������ת��Ϊ�ַ��ͣ����汾����ͼ��
     * 2������������ԣ�
     * BsoID�����롢���ơ��ǣ��񣩿ɷ֡������������������ԡ�

     * @param partIfc :QMPartIfc ָ�����㲿����ֵ����.
     * @param attrNames :String[] ����Ҫ�������ͨ���Լ��ϣ����Ϊ�գ��򰴱�׼�������
     * @param affixAttrNames : String[] ���Ƶ�Ҫ�������չ���Եļ��ϣ�����Ϊ�ա�
     * @param routeNames : String[] ���Ƶ�Ҫ����Ĺ���·�����ļ��ϣ�����Ϊ�ա�
     * @param source :String ָ�����㲿������Դ������ɸѡ����Դ���㲿�������Ϊ�գ����ô���
     * @param type :String �㲿�������ͣ�����ɸѡ�����͵��㲿�������Ϊ�գ����ô���
     * @param configSpecIfc :PartConfigSpecIfc ��ǰ�ͻ�����ʹ�õ�ɸѡ������
     * @param parentQuantity :float ʹ���˵�ǰ�����Ĳ��������������ʹ�õ�������
     * @param partLinkIfc :PartUsageLinkIfc ���ӵ�ǰ�������丸�����Ĺ�����ϵֵ����
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
      //System.out.println("getRouteValue'time is"+(t2-t1)+"����");

      Object[] tempArray = null;
      boolean attrNullTrueFlag = attrNames == null || attrNames.length == 0;
      boolean affixAttrNullTrueFlag = affixAttrNames == null ||
          affixAttrNames.length == 0;
      boolean routeNullTrueFlag = routeNames == null || routeNames.length == 0;

      //��¼�����ͱ�������������ڵ�λ�á�
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
      //����㲿���Ĺ���·�ߡ�
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
                //��Ҫ���ж�partLinkIfc�Ƿ��ǳ־û��ģ�������ǣ�parentQuantity = 1.0
                //�����:parentQuantity = parentQuantity * partLinkIfc.getQuantity()
            	  //CCBeginby leixiao 2009-2-13 ԭ�򣺽����������·��,������������
                if (partLinkIfc == null ||!PersistHelper.isPersistent(partLinkIfc))
                {
                    parentQuantity = 1.0f;
                }
                else
                {
                    parentQuantity = parentQuantity *
                                     partLinkIfc.getQuantity();
                }
                //CCEndby leixiao 2009-2-13 ԭ�򣺽����������·��,������������
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
     * ��һ���Ż���liunan 2009-03-10
     * ��ȡibaֵ�ķ�����ÿ��bianli��������һ��������������bianli�е�part�͵õ����Ӽ����ϴ�������
     * �Դ˼������㲿������iba���������ibaTempMap���е��㲿������������������
     * @param ibaTempMap HashMap ibaֵ�Ļ��棬keyΪpartBsoID+iba���ƣ�ֵ�������part��Ӧibaname��ֵ��
     * @param partIfc QMPartIfc ����bianli�ļ���Ҳ���Ǽ���hegeVector�ĸ�����
     * @param hegeVector Vector ����bianlipartIfc���Ӽ����ϡ�
     * @return HashMap ͬibaTempMapÿ���㲿��iba��ֵ�ļ��ϡ�
     */
    private HashMap getIbaValue(HashMap ibaTempMap, QMPartIfc partIfc, Vector hegeVector)
    {
    	//System.out.println("ѡ����iba�����ߵ�����ģ�����");
    	//�㲿��bsoid����
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
              //��֯��in������Ҫ��partid��������һ��query������
    		Collection coll = getIbaValueBySql(ibaNames[j],partID);
    		Iterator iba=coll.iterator();
        while(iba.hasNext())
        {
        	StringValueIfc s=(StringValueIfc)iba.next();
        	String keyStr = s.getIBAHolderBsoID()+ibaNames[j].toString();
        	//System.out.println("==================================="+keyStr);
                //��������в����ڣ�����ӡ�
        	if(!ibaTempMap.containsKey(keyStr))
        	{
        	  ibaTempMap.put(s.getIBAHolderBsoID()+ibaNames[j].toString(),s.getValue());
          }
        }
    	}
    	return ibaTempMap;
    }


    /**
     * �ڶ����Ż���CCBegin by liunan 2009-04-05
     * ��ȡibaֵ�ķ���������bianliѭ��������ִ�еĴ˷�������װin��query������ÿ������100����
     * ��������ڽ�Ų��Ի��϶�β��Խ���ó���Ч����Ѹ�����
     * @param ibaTempMap HashMap ibaֵ�Ļ��棬keyΪpartBsoID+iba���ƣ�ֵΪ���㲿��ibaname��Ӧ��ֵ��
     * @param bomMap HashMap ���б������漰���㲿���ļ��ϡ�keyΪpartBsoID��
     * ֵΪ��ά���飬��һ��Ԫ����partMasterBsoID���ڶ���Ԫ�����丸����partMasterBsoID��
     * @return HashMap ���ظ�ֵ���ibaTempMap��
     */
    private HashMap getIbaValue(HashMap ibaTempMap, HashMap bomMap)
    {
    	//System.out.println("ѡ����iba�����ߵ�����ģ�����");
    	//�㲿��bsoid����
    	String[] partID;
    	//��¼��ǰ��partid��������ӵ��㲿������
    	int i = 0;
    	//�����ݿ����
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
    		// ��100���㲿��Ϊ��λ����һ�β�ѯ���õ����㲿����iba����
    		if (i == basenum || !iter.hasNext())
    		{
    			for(int j=0;j<ibaNames.length;j++)
    			{
    				long t1 = System.currentTimeMillis();
    				Collection coll = getIbaValueBySql(ibaNames[j],partID);
    				long t2 = System.currentTimeMillis();
    				//System.out.println("����һ��iba�������㲿����Ϊ "+i+" ������ʱ�� "+(t2-t1)/1000+" ��");
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
    			// ��i��0�����¿�ʼ����
    			i = 0;
    			// �����ݿ����
    			k++;
    			// bomMap�����������㲿������
    			int next = bomMap.size() - basenum * k;
    			//System.out.println("��"+k+"��-------����������"+next);
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
     * ��in���������query������iba����ʱ�䡣
     * @param ibaName String iba�������ơ�
     * @param partID String[] in�����飬Ԫ�ض���partBsoID��
     * @return Collection query�õ��Ľ�����ϡ�
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
     * �ڶ����Ż���
     * ���·����Ϣ�����崦��in��sql��ʽ��ÿ������20����
     * ��������ڽ�Ų��Ի��϶�β��Խ���ó���Ч����Ѹ�����
     * @param routeTempMap HashMap routeֵ�Ļ��棬keyΪpartMasterBsoID+route���ƣ�ֵ�������part��Ӧroutename��ֵ��
     * @param bomMap HashMap ���б������漰���㲿���ļ��ϡ�keyΪpartBsoID��
     * ֵΪ��ά���飬��һ��Ԫ����partMasterBsoID���ڶ���Ԫ�����丸����partMasterBsoID��
     * @return HashMap ����routeTempMap��
     */
    private HashMap getRouteValue(HashMap routeTempMap, HashMap bomMap)
    {
    	//�㲿��masterbsoid����
    	String[] routeNames = (String[])routeTempMap.get("routeName");
    	int routeNameSize = routeNames.length;
    	String partMasterID = "";
    	boolean isHasRouteList = false;
    	//��¼��ǰ��partid��������ӵ��㲿������
    	int i = 0;
    	//�����ݿ����
    	int k = 0;
    	for(int a=0;a<routeNameSize;a++)
    	{
    		if(routeNames[a].equals("��׼���")||routeNames[a].equals("��ע"))
    		{
    			isHasRouteList = true;
    		}
    	}
    	// ��20���㲿��Ϊ��λ����һ�β�ѯ���õ����㲿����route����

    	//����sql���õ�����·�ߡ�
    	//CCBegin by liunan 2009-06-10 ���TechnicsRouteList��Ϊ��׼��technicsroute����ȡ��״̬��ȥ��alterstatus='1'������
    	//String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid from technicsroutebranch branch,(select a.leftbsoid,a.routeid,a.rightbsoid from listroutepartlink a ,(select max(MODIFYTIME) maxtime,rightbsoid from listroutepartlink where rightbsoid in (";
    	//String sql2 = ") and alterstatus='1' and routeid is not null group by rightbsoid) b where a.rightbsoid=b.rightbsoid and a.MODIFYTIME=b.maxtime and a.alterstatus='1') link where branch.routeid=link.routeid";
    //CCBegin SS9
    	//String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid from technicsroutebranch branch,(select a.leftbsoid,a.routeid,a.rightbsoid from listroutepartlink a ,(select max(aa.MODIFYTIME) maxtime,aa.rightbsoid from (select a.MODIFYTIME,a.routeid,a.rightbsoid from listroutepartlink a,technicsroute b, technicsroutelist c where a.rightbsoid in (";
    	String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid,link.colorflag from technicsroutebranch branch,(select a.leftbsoid,a.routeid,a.rightbsoid from listroutepartlink a ,(select max(aa.MODIFYTIME) maxtime,aa.rightbsoid from (select a.MODIFYTIME,a.routeid,a.rightbsoid from listroutepartlink a,technicsroute b, technicsroutelist c where a.rightbsoid in (";
    	//CCEnd SS9
    	//CCBegin by liunan 2012-04-25 ��Ƽ����������ϼе���׼��ȡ��·�ߡ�
    	//String sql2 = ") and a.routeid=b.bsoid and b.modefyIdenty!='Coding_221664' and a.leftbsoid=c.bsoid and c.routeliststate!='��׼') aa where aa.routeid is not null group by aa.rightbsoid) b where a.rightbsoid=b.rightbsoid and a.MODIFYTIME=b.maxtime) link ,technicsroute route where branch.routeid=link.routeid and route.modefyIdenty!='Coding_221664' and route.bsoid=branch.routeid";
    	String sql2 = ") and a.routeid=b.bsoid and b.modefyIdenty!='Coding_221664' and a.leftbsoid=c.bsoid and c.routeliststate!='��׼' and c.owner is null) aa where aa.routeid is not null group by aa.rightbsoid) b where a.rightbsoid=b.rightbsoid and a.MODIFYTIME=b.maxtime) link ,technicsroute route where branch.routeid=link.routeid and route.modefyIdenty!='Coding_221664' and route.bsoid=branch.routeid";
    	//CCEnd by liunan 2012-04-25
    	//CCEnd by liunan 2009-06-10

    	Connection conn = null;
      Statement stmt =null;
      ResultSet rs=null;
  	  try
  	  {
  	  	PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
  	    //������������׼��masterid����������
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
    				//System.out.println("����һ��route�������㲿����Ϊ "+i+" ������ʱ�� "+(t2-t1)+" ����");
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
      					if(routeNames[ii].equals("����·��"))
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
      					else if(routeNames[ii].equals("װ��·��"))
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
      					else if(routeNames[ii].equals("��׼���"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							routeTempMap.put(keyName,routelist.getRouteListNumber());
      						}
      					}
      					//CCBegin SS14
      					else if(routeNames[ii].equals("��׼˵��"))
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
      							if(des.indexOf("˵����")!=-1)
      							{
      								des = des.substring(des.indexOf("˵����"),des.length());
      							}
      							routeTempMap.put(keyName, des);
      						}
      					}
      					else if(routeNames[ii].equals("��׼״̬"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							routeTempMap.put(keyName,routelist.getLifeCycleState().getDisplay());
      						}
      					}
      					//CCEnd SS14
      						//CCBegin SS9
      					else if(routeNames[ii].equals("��ɫ����ʶ"))
      					{
      						
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							String color="";
      							if(rs.getString(4)!=null&&rs.getString(4).equals("1")){
      								color="��";
      							}
      							routeTempMap.put(keyName,color);
      						}
      					}
      					//CCEnd SS9
      					else if(routeNames[ii].equals("��ע"))
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
    				//System.out.println("����һ��route��������ʱ�� "+(t3-t2)+" ����");

      			// ��i��0�����¿�ʼ����
      			i = 0;
      			// �����ݿ����
      			k++;
      			// bomMap�����������㲿������
      			//int next = bomMap.size() - routebasenum * k;
      			//System.out.println("��"+k+"��-------����������"+next);
      			partMasterID = "";
				  }
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
    	return routeTempMap;
    }
    //CCEnd by liunan 2009-04-05

    /**
     * ��һ���Ż���liunan 2009-03-10
     * ��ȡrouteֵ�ķ�����ÿ��bianli��������һ��������������bianli�е�part�͵õ����Ӽ����ϴ�������
     * �Դ˼������㲿������route���������routeTempMap���е��㲿������������������
     * @param routeTempMap HashMap routeֵ�Ļ��棬keyΪpartMasterBsoID+route���ƣ�ֵ�������part��Ӧroutename��ֵ��
     * @param partIfc QMPartIfc ����bianli�ļ���Ҳ���Ǽ���hegeVector�ĸ�����
     * @param hegeVector Vector ����bianlipartIfc���Ӽ����ϡ�
     * @return HashMap ͬrouteTempMapÿ���㲿��iba��ֵ�ļ��ϡ�
     */
    private HashMap getRouteValue(HashMap routeTempMap, QMPartIfc partIfc, Vector hegeVector)
    {
    	//�㲿��masterbsoid����
    	String[] routeNames = (String[])routeTempMap.get("routeName");
    	int routeNameSize = routeNames.length;
    	String partMasterID = "";
    	boolean flag1 = true;
    	boolean isHasZhuangPei = false;
    	boolean isHasRouteList = false;
    	for(int a=0;a<routeNameSize;a++)
    	{
    		if(routeNames[a].equals("װ��·��"))
    		{
    			isHasZhuangPei = true;
    		}
    		else if(routeNames[a].equals("��׼���")||routeNames[a].equals("��ע"))
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

    	//����sql���õ�����·�ߡ�
    	String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid from technicsroutebranch branch,(select a.leftbsoid,a.routeid,a.rightbsoid from listroutepartlink a ,(select max(MODIFYTIME) maxtime,rightbsoid from listroutepartlink where rightbsoid in (";
    	String sql2 = ") and alterstatus='1' and routeid is not null group by rightbsoid) b where a.rightbsoid=b.rightbsoid and a.MODIFYTIME=b.maxtime) link where branch.routeid=link.routeid";

    	Connection conn = null;
      Statement stmt =null;
      ResultSet rs=null;
  	  try
            {
            PersistService pService = (PersistService) EJBServiceHelper
                    .getService("PersistService");
  	    //������������׼��masterid����������
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
        		if(routeNames[ii].equals("����·��"))
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
        		else if(routeNames[ii].equals("װ��·��"))
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
        		else if(routeNames[ii].equals("��׼���"))
        		{
        			keyName = rs.getString(2)+routeNames[ii];
        			if(!routeTempMap.containsKey(keyName))
        	    {
        	    	routeTempMap.put(keyName,routelist.getRouteListNumber());
            	}
        		}
      					//CCBegin SS14
      					else if(routeNames[ii].equals("��׼˵��"))
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
      							if(des.indexOf("˵����")!=-1)
      							{
      								des = des.substring(des.indexOf("˵����"),des.length());
      							}
      							routeTempMap.put(keyName, des);
      						}
      					}
      					else if(routeNames[ii].equals("��׼״̬"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							routeTempMap.put(keyName,routelist.getLifeCycleState().getDisplay());
      						}
      					}
      					//CCEnd SS14
        		else if(routeNames[ii].equals("��ע"))
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

        //���װ��Ϊ�գ����ø���������·�ߡ�
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
            	  if(routeTempMap.containsKey(part.getMasterBsoID()+"װ��·��"))
            	  {
            	    String zproute = routeTempMap.get(part.getMasterBsoID()+"װ��·��").toString();
            	    if(zproute==null||zproute.equals("")||zproute.equals("/"))
            	    {
        		    	  if(routeTempMap.containsKey(partIfc.getMasterBsoID()+"����·��"))
        		    	  {
        			        routeTempMap.remove(part.getMasterBsoID()+"װ��·��");
                      routeTempMap.put(part.getMasterBsoID()+"װ��·��",routeTempMap.get(partIfc.getMasterBsoID()+"����·��"));
                    }
                  }
                }
            	}
            }
          }
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

    	return routeTempMap;
    }

    /**
     * ·�ߵ�λ�ַ�������
     * 1������"/"��ͷ���β��
     * 2��·���ظ�ʱֻ��ʾһ���Ĵ���
     * @param s1 String ����·�ߴ���
     * @param s2 String ��ǰ·�ߡ�
     * @return String ����õ�·���ַ�����
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
    
    
    /**
     * �Ż������򷽷�����������partNumber�ŵ�object�����У�����Arrays�Դ��������ܽ�������
     * �õ���������¹��켯�Ϸ��ء�
     * @param hehe Vector ������Ϣ������ɵ�bom���ϡ�
     * @param partIfc QMPartIfc ��������㲿����
     * @param numSite int �㲿������������ڵ�λ�á�
     * @return Vector �����bom���ϡ�
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
     * ���򷽷�
     * @param hehe Vector ������Ϣ������ɵ�bom���ϡ�
     * @param partIfc QMPartIfc ��������㲿����
     * @param numSite int �㲿������������ڵ�λ�á�
     * @return Vector �����bom���ϡ�
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
   * �˷����ڵ���רΪ��Ŷ��Ƶķ��������Ͻ��е��Ż�����Ҫ�޸��˻���Ӽ���getUsesPartIfcs������
   * iba��route�Ļ�÷�ʽ���������øĳ������á�
   * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
   * �ּ������嵥����ʾ��
   * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
   * 0����ǰpart��BsoID��
   * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
   * 2����ǰpart�ı�ţ�
   * 3����ǰpart�����ƣ�
   * 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
   * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
   *                ������������ԣ��������ж��Ƶ����Լӵ���������С�
   * �����������˵ݹ鷽����
   * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
   * PartUsageLinkIfc partLinkIfc, int parentQuantity);
   * @param partIfc :QMPartIfc ����Ĳ���ֵ����
   * @param attrNames :String[] ���Ƶ����ԣ�����Ϊ�ա���ͨ����-0��iba����-1��route����-2��
   * @param affixAttrNames : String[] ����ʱδʹ�á�
   * @param routeNames : String[] ����ʱδʹ�á�
   * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
   * @throws QMException
   * @return Vector
   * @author liunan 2009-04-13
   */
  //CCBegin by leixiao 2010-12-22  Ϊ�����߼��ܳ����������޸ķ���,���Ӳ���islogic,ԭ������false
    public Vector setMaterialList(QMPartIfc partIfc, String attrNames[],
            String affixAttrNames[], String[] routeNames,
            PartConfigSpecIfc configSpecIfc) throws
QMException {
    	return setMaterialList(partIfc,attrNames,affixAttrNames,routeNames,configSpecIfc,false);
    }
  //CCEnd by leixiao 2010-12-22  
    /**
     * �˷����ڵ���רΪ��Ŷ��Ƶķ��������Ͻ��е��Ż�����Ҫ�޸��˻���Ӽ���getUsesPartIfcs������
     * iba��route�Ļ�÷�ʽ���������øĳ������á�
     * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
     * �ּ������嵥����ʾ��
     * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
     * 0����ǰpart��BsoID��
     * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
     * 2����ǰpart�ı�ţ�
     * 3����ǰpart�����ƣ�
     * 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
     * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
     *                ������������ԣ��������ж��Ƶ����Լӵ���������С�
     * �����������˵ݹ鷽����
     * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
     * PartUsageLinkIfc partLinkIfc, int parentQuantity);
     * @param partIfc :QMPartIfc ����Ĳ���ֵ����
     * @param attrNames :String[] ���Ƶ����ԣ�����Ϊ�ա���ͨ����-0��iba����-1��route����-2��
     * @param affixAttrNames : String[] ����ʱδʹ�á�
     * @param routeNames : String[] ����ʱδʹ�á�
     * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
     * @throws QMException
     * @return Vector
     * @author leix ���Ӳ���islogiclist
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
      	System.out.println("�û� "+userName+" ��ʼ�����㲿�� "+partIfc.getPartNumber()+" �ּ�����");
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
      System.out.println("�˴ηּ�����ѡ���iba��·���У�"+specAttr+"==");

      Vector vector = null;

        // iba�Ļ��棬���������iba���ԣ����һ���keyΪibaName��ֵΪiba���������ϣ�
        // ������Ϊ�����㲿����ibaֵ��keyΪpartBsoID+ibaName��ֵΪibaֵ��
        HashMap ibaTempMap = new HashMap();

        // route�Ļ��棬���������route���ԣ����һ���keyΪrouteName��ֵΪroute���������ϣ�
        // ������Ϊ�����㲿����routeֵ��keyΪpartMasterBsoID+routeName��ֵΪrouteֵ��
        HashMap routeTempMap = new HashMap();
        //CCBegin by leixiao 2010-4-20 
        Vector islogic=new Vector();
        
        HashMap logicRoute=new HashMap();
        //CCEnd by leixiao 2010-4-20 
        // ���������������㲿�����ϣ�keyΪpartMasterBsoID��ֵΪ����partMasterBsoID��
        HashMap bomMap = new HashMap();

        // iba���Լ����ַ�����
        String ibaName = "";

        // route���Լ����ַ�����
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
        ////CCBegin by leixiao 2010-12-22 Ϊ�߼��ܳ���������,���Ӳ���islogiclist 
      //CCBegin by liunan 2012-05-24 Ϊ�˵õ������ϼ���������·�ߣ���Ҫ��¼����ڵ�ǰ���������и����ļ��ϡ�
      HashMap allBomMap = new HashMap();
      //vector = fenji(partIfc, partIfc, attrNames, affixAttrNames, routeNames, configSpecIfc, level, partLinkInfo, parentQuantity, "",parentQuantity,bomMap,islogic,islogiclist);
      vector = fenji(partIfc, partIfc, attrNames, affixAttrNames, routeNames, configSpecIfc, level, partLinkInfo, parentQuantity, "",parentQuantity,bomMap,allBomMap,islogic,islogiclist);
      //CCEnd by liunan 2012-05-24
    //CCEnd by leixiao 2010-12-22
      long t222 = System.currentTimeMillis();
        System.out.println("��fenji���� ��ʱ�� "+(t222-t111)/1000+" ��");
        //System.out.println("bomMap'size===="+bomMap.size()+"  and vector'size===="+vector.size());
        System.out.println("bomMap'size===="+bomMap.size()+"  and allBomMap'size===="+allBomMap.size());

        if(!ibaName.equals("")&&bomMap.size()>0)
        {
        	ibaTempMap.put("ibaName",ibaName.split(","));
        	long t12 = System.currentTimeMillis();
        	ibaTempMap = getIbaValue(ibaTempMap,bomMap);
        	long t21 = System.currentTimeMillis();
        	System.out.println("�� ��ȡiba ���� ��ʱ�� "+(t21-t12)+" ���룬ÿ������iba�ĸ���Ϊ�� "+basenum+" ��");
        }
        //CCBegin by leixiao 2010-12-22 �����ź�����з���Դ�汾���� 2800G17-48A/A
        if(islogiclist){
        	ibaTempMap.put("ibaName",new String[]{"����Դ�汾"});
        	ibaTempMap = getIbaValue(ibaTempMap,bomMap);	
        }
        //CCEnd by leixiao 2010-12-22 �����ź�����з���Դ�汾���� 2800G17-48A/A
        if(!routeName.equals("")&&bomMap.size()>0)
        {
        	routeTempMap.put("routeName",routeName.split(","));
        	long t1222 = System.currentTimeMillis();
        	
        	//CCBegin by liunan 2012-04-28 ��ȡȫ��·�ߡ�
        	//routeTempMap = getRouteValue(routeTempMap,bomMap);
        	routeTempMap = getAllRouteValue(routeTempMap,bomMap);        	
        	//CCEnd by liunan 2012-04-28
        	
        	long t2221 = System.currentTimeMillis();
        	System.out.println("�� ��ȡroute ���� ��ʱ�� "+(t2221-t1222)+" ���룬ÿ������route�ĸ���Ϊ�� "+routebasenum+" ��");
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
      firstElement.addElement("��һ���������");//yanqi-20061222-�ڷּ��������Ӹ�����

      String[] tempArray = new String[firstElement.size()];
      for (int i = 0; i < firstElement.size(); i++) {
        tempArray[i] = (String) firstElement.elementAt(i);

      }
      vector.insertElementAt( ( (Object[]) (tempArray)), 0);

      long ta22 = System.currentTimeMillis();
      //System.out.println("��ȡ�������Է��� ��ʱ�� "+(ta22-ta11)+" ����");

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
          	temp[j] = temp[j].toString().replaceAll(",","��");
          }
          //CCEnd SS16
        }
        //CCBegin by leixiao 2010-12-22 �����ź�����з���Դ�汾���� 2800G17-48A/A
        if(islogiclist){
  			if(ibaTempMap.containsKey(temp[0]+"����Դ�汾"))
  			{
  				String version = (String)ibaTempMap.get(temp[0]+"����Դ�汾");
  				//System.out.println("--------number="+temp[4]);
  					String qiyanversion=version.substring(0,version.indexOf("."));
  					if(temp[4].toString().endsWith("$")){//����Ǳ�׼��,ǰ���Ѿ����˱�ʶ$,�ж���$ʱ,���Ӱ汾.
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
  				//�㲿����ŵ�һ����/����Ϊ��*L01*������0������*0��1��2��3��4����������ZBT������*(L)������AH������*J0*������*J1*������*-SF������BQ���͡�*-ZC���Ĳ��Ӱ汾
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
  					if(temp[4].toString().endsWith("$")){//����Ǳ�׼��,ǰ���Ѿ����˱�ʶ$,�ж���$ʱ,���Ӱ汾.
  						temp[4]=temp[4].toString().substring(0,temp[4].toString().length()-1);
  					}
  					//CCBegin SS15
  					else if(hasV)
  					{
  						System.out.println("��������Ҫ�󲻴��汾��"+temp[4]);
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
        //CCEnd by leixiao 2010-12-22 �����ź�����з���Դ�汾���� 2800G17-48A/A
         
        //CCBegin by liunan 2009-04-05
        //Ϊ֮ǰΪ""��iba��route���и�ֵ��       
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
          				//���װ��Ϊ�գ����ø���������·�ߡ�
          				//System.out.println("curRouteValue="+curRouteValue);
          				//System.out.println("curRouteName="+curRouteName);

          				if(curRouteName.equals("װ��·��")&&(curRouteValue==null||curRouteValue.equals("")||curRouteValue.equals("/")))
          				{
//          				CCBegin by leixiao 2010-4-16 
//                        1��װ��·��Ϊ��ʱȡ��������·�ߵĵ�һ����λ 
//                        2���߼��ܳɵ�װ��·�߲�ȡ��������·��         	
//                        3�� ����Ϊ�߼��ܳɵ�װ��·��ȡ�����ĸ���������·��       	
//          			  4��������·�߰������á�ʱ���Ӹ���ȡ����·����Ϊװ��·��	
          					if(routeTempMap.containsKey(idStr[1]+"����·��"))
          					{
          						// װ��·��Ϊ��ʱȡ��������·�ߵĵ�һ����λ
          						String fuzhizhao=routeTempMap.get(idStr[1]+"����·��").toString();
          						if(fuzhizhao.indexOf("--")!=-1){
          						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("-"));          					   
          					    }
              					if(islogic.contains(temp[0])){//������߼��ܳɲ�ȡ��������         						
              						logicRoute.put(idStr[0].toString(), fuzhizhao);//�߼��ܳɵ�װ��·��
              					}
              					else if(routeTempMap.get(idStr[0]+"����·��").toString().indexOf("��")!=-1){
              						//����·�߰�����ʱ����ȡ�����ĵ�������Ϊװ��
              					}
              					else{
              						temp[4 + k]=fuzhizhao;	
              					}          					
          				  }
          					else{
          						if(logicRoute.containsKey(idStr[1].toString())&&!islogic.contains(temp[0])&&routeTempMap.get(idStr[0]+"����·��").toString().indexOf("��")==-1){//�丸�����߼��ܳɼ�����������
          							temp[4 + k]=logicRoute.get(idStr[1].toString());
          						}
          					}
          				//CCEnd by leixiao 2010-4-16 
          				}
          				//CCBegin by liunan 2012-04-28 ��ȡȫ��·�ߡ�
          				else if(curRouteName.equals("װ��·��")&&curRouteValue.indexOf("��")!=-1)
          				{
          					String[] idallStr = (String[])allBomMap.get(temp[0]);
          					String pid = idallStr[1];
          					String temppid[] = pid.split(";");
          					temp[4 + k]=curRouteValue;
          					for(int ii = 0 ; ii< temppid.length; ii++)
          					{
          						String ppartid = temppid[ii];
          					if(routeTempMap.containsKey(ppartid+"����·��"))
          					{
          						// װ��·��Ϊ��ʱȡ��������·�ߵĵ�һ����λ
          						String fuzhizhao=routeTempMap.get(ppartid+"����·��").toString();
          						if(fuzhizhao.indexOf("--")!=-1){
          						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("-"));          					   
          					    }
          					   
          					   if(fuzhizhao.indexOf("/")!=-1){
          						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("/"));          					   
          					    }
          					    
              					if(islogic.contains(temp[0])){//������߼��ܳɲ�ȡ��������  
              						logicRoute.put(idStr[0].toString(), fuzhizhao);//�߼��ܳɵ�װ��·��
              					}
              					else if(routeTempMap.get(idStr[0]+"����·��").toString().indexOf("��")!=-1){
              						//����·�߰�����ʱ����ȡ�����ĵ�������Ϊװ��
              					}
              					else{
              						/*if(curRouteValue.indexOf(fuzhizhao)!=-1)
              						{
              							temp[4 + k]=curRouteValue.replaceAll("/��","").replaceAll("��/","").replaceAll("��","");	
              						}
              						else
              						{
              							temp[4 + k]=curRouteValue.replaceAll("��",fuzhizhao);	
              						}*/
              						
              						if(temp[4 + k].toString().indexOf(fuzhizhao)<0)
              						{
              							temp[4 + k]=temp[4 + k]+"/"+fuzhizhao;
              						}
              					}          					
          				  }
          					else{
          						if(logicRoute.containsKey(idStr[1].toString())&&!islogic.contains(temp[0])&&routeTempMap.get(idStr[0]+"����·��").toString().indexOf("��")==-1){//�丸�����߼��ܳɼ�����������
          							//System.out.println("logicRoute.get(idStr[1].toString())====="+logicRoute.get(idStr[1].toString()));
          							
          							/*if(curRouteValue.indexOf(logicRoute.get(idStr[1].toString()).toString())!=-1)
          							{
          								temp[4 + k]=curRouteValue.replaceAll("/��","").replaceAll("��/","").replaceAll("��","");	
          							}
          							else
          							{
          								temp[4 + k]=curRouteValue.replaceAll("��",logicRoute.get(idStr[1].toString()).toString());
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
          						temp[4 + k]=temp[4 + k].toString().replaceAll("/��","").replaceAll("��/","").replaceAll("��","");	
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
          				if(curRouteName.equals("װ��·��")&&routeTempMap.containsKey(idStr[1]+"����·��"))
          				{
      						//װ��·��Ϊ��ʱȡ��������·�ߵĵ�һ����λ
      						String fuzhizhao=routeTempMap.get(idStr[1]+"����·��").toString();
      						if(fuzhizhao.indexOf("--")!=-1){
      						fuzhizhao = fuzhizhao.substring(0,fuzhizhao.indexOf("-"));          					   
      					    }
          					if(islogic.contains(temp[0])){//������߼��ܳɲ�ȡ��������         						
          						logicRoute.put(idStr[0].toString(), fuzhizhao);
          					}
          					else{
          						temp[4 + k]=fuzhizhao;	
          					}
      					//װ��·��Ϊ��ʱȡ��������·�ߵĵ�һ����λ
          				}
          				else if(curRouteName.equals("װ��·��")){
          					//���װ��·�ߴӸ�������ȡ��Ϊ�գ��������߼��ܳɵ�ȡ�����ĸ���������·��
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
        System.out.println("�»�ȡiba��route���� ��ʱ�� "+(t22-ta22)+" ����");

      long t2 = System.currentTimeMillis();
        if(subTimeGong>0)
          System.out.println("��ǰ��ͼΪ��"+viewName+"�� ���в����Ӽ�����subTimeGong��ʱ��"+subTimeGong+" ����");
        else if(subTimeOther>0)
          System.out.println("��ǰ��ͼΪ��"+viewName+"�� ���в����Ӽ�����subTimeOther��ʱ��"+subTimeOther+" ����");
        else
          System.out.println("��ǰ��ͼΪ��"+viewName);
        System.out.println("�����㲿�� "+partIfc.getPartNumber()+" �������� "+(vector.size()-1)+ " ���㲿���� �Ӽ��ڵ㹲�� "+subCount+" ���� �û� "+userName+" ��ʱ�� "+(t2-t1)/1000+" ��");
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
   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
   * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
   * liuming add 20070209
   * �޺ϼ�װ������ʾ��
   * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
   * 0����ǰpart��BsoID��
   * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
   * 2����ǰpart�ı�ţ�
   * 3����ǰpart�����ƣ�
   * 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
   * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
   *                ������������ԣ��������ж��Ƶ����Լӵ���������С�
   * �����������˵ݹ鷽����
   * fenji(QMPartIfc partIfc, String[] attrNames, PartConfigSpecIfc configSpecIfc,
   * PartUsageLinkIfc partLinkIfc, int parentQuantity);
   * @param partIfc :QMPartIfc ����Ĳ���ֵ����
   * @param attrNames :String[] ���Ƶ����ԣ�����Ϊ�ա�
   * @param affixAttrNames : String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
   * @param routeNames : String[] ���ƵĹ���·�������ϣ�����Ϊ�ա�
   * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
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
      	System.out.println("�û� "+userName+" ��ʼ�����㲿�� "+partIfc.getPartNumber()+" �ּ�2����");
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
        	//CCBegin by liunan 2008-10-13 ������λ��Ӧ������һ�С�
        	//Դ������
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
      firstElement.addElement("��һ���������");//yanqi-20061222-�ڷּ��������Ӹ�����

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
        System.out.println("�����㲿�� "+partIfc.getPartNumber()+" ���� �û� "+userName+" ��ʱ�� "+(t2-t1)/1000+" ��");
        System.out.println("--------------------------------------------------"+"\n\n\n");
      return vector;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new QMException(e.toString());
    }
  }


  /**
   * �˷����ڵ���רΪ��Ŷ��Ƶķ��������Ͻ����޸ģ������в��ٴ���iba��route������ֵΪ""��
   * ��ѭ��fenji��ɺ�ͳһ����
   * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
   * ˽�з�������setMaterialList()�������ã�ʵ�ֶ��Ʒּ������嵥�Ĺ��ܡ�
   * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
   * 0����ǰpart��BsoID��
   * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
   * 2����ǰpart�ı�ţ�
   * 3����ǰpart�����ƣ�
   * 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
   * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
   *                ������������ԣ��������ж��Ƶ����Լӵ���������С�

   * @param partIfc :QMPartIfc ��ǰ�Ĳ�����
   * @param attrNames :String[] ���Ƶ����Լ��ϣ�����Ϊ�գ�
   * @param affixAttrNames :String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
   * @param routeNames :String[] ���ƵĹ���·�������ϣ�����Ϊ�ա�
   * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
   * @param level :int ��ǰpart���ڵļ���
   * @param partLinkIfc :PartUsageLinkIfc ��¼�˵�ǰpart�����׽ڵ��ʹ�ù�ϵ��ֵ����
   * @param parentQuantity :int ��ǰpart�ĸ��׽ڵ㱻�������ʹ�õ�������
   * @param bomMap :HashMap �������漰�������㲿�����ϣ�keyΪpartBsoID��
   * ֵΪ��ά���飬��һ��Ԫ����masterBsoID���ڶ���Ԫ���Ǹ���masterBsoID��
   * @throws QMException
   * @return Vector
   * @author CCBegin by leix	 2010-12-20  �����߼��ܳ���������  ���Ӳ���islogiclist
   */
//  CCBegin by leix	 2010-12-20  �����߼��ܳ���������  ���Ӳ���islogiclist
  private Vector fenji(QMPartIfc partProductIfc, QMPartIfc partIfc,
                       String attrNames[], String affixAttrNames[],
                       String[] routeNames, PartConfigSpecIfc configSpecIfc,
                       int level, PartUsageLinkIfc partLinkIfc,
                       float parentQuantity, String parentFirstMakeRoute,
                       //CCBegin by liunan 2012-05-24 Ϊ�˵õ������ϼ���������·�ߣ���Ҫ��¼����ڵ�ǰ���������и����ļ��ϡ�
                       //float parentInProductCount, HashMap bomMap,Vector islogic,boolean islogiclist) throws
                       float parentInProductCount, HashMap bomMap, HashMap allBomMap,Vector islogic,boolean islogiclist) throws
                       //CCEnd by liunan 2012-05-24
      QMException
  {
    //���bomMapΪ�����ʾ��һ�ν���bianli����������bomMap��ֵҲ���Ǹ��ڵ㡣
    if(bomMap==null||bomMap.size()==0)
      {
        //����Ϊ����masterid����"root"���õ�ʱ�򻺴���û��"root"��Ҳ�Ͳ���õ�ֵ��
      	bomMap.put(partIfc.getBsoID(),new String[]{partIfc.getMasterBsoID(),"root"});
      	allBomMap.put(partIfc.getBsoID(),new String[]{partIfc.getMasterBsoID(),"root"});
      }

    Vector resultVector = new Vector();
    Object[] tempArray = null;

    Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);

    /////////////////////////////////////////���� ���������

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
             //��partIfc���Ӽ�����ʽ���浽����bomMap�С�
            	if(!bomMap.containsKey(((QMPartIfc)obj1[1]).getBsoID()))
            	{
            	  bomMap.put(((QMPartIfc)obj1[1]).getBsoID(),new String[]{((QMPartIfc)obj1[1]).getMasterBsoID(),partIfc.getMasterBsoID()});
            	  //CCBegin by liunan 2012-05-24 Ϊ�˵õ������ϼ���������·�ߣ���Ҫ��¼����ڵ�ǰ���������и����ļ��ϡ�
            	  allBomMap.put(((QMPartIfc)obj1[1]).getBsoID(),new String[]{((QMPartIfc)obj1[1]).getMasterBsoID(),partIfc.getMasterBsoID()});
                //CCEnd by liunan 2012-05-24
              }
              //CCBegin by liunan 2012-05-24 Ϊ�˵õ������ϼ���������·�ߣ���Ҫ��¼����ڵ�ǰ���������и����ļ��ϡ�
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
    //��¼�����ͱ�������������ڵ�λ�á�
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
      tempArray = new Object[5];//yanqi-20061222 ���ӡ�������š�����
      tempArray[2] = new Integer(numberSite);//yanqi-20061222
    }
    else
    {
      tempArray = new Object[5 + attrNames.length];//yanqi-20061222���ӡ�������š�����
      tempArray[2] = new Integer(numberSite);
    }

    tempArray[0] = partIfc.getBsoID();
    tempArray[1] = new Integer(1);
    tempArray[3] = new Integer(level); //level�ĳ�ʼֵΪ0��
    //CCBegin by leixiao 2020-4-20    ������߼��ܳɣ�·�߲���ʾװ��·��
    if (partIfc.getPartType().toString().equalsIgnoreCase("Logical"))
    {
      islogic.add(partIfc.getBsoID());
    }
    //CCEnd by leixiao 2020-4-20 
    //CCBegin by leixiao 2010-12-22 ����Ϊ �߼��ܳ�ʱ,�Ӽ�����=����*�������� �˴���ø���
    QMPartIfc part=null;//����
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
            	// parentQuantity = partLinkIfc.getQuantity(); //parentQuantity*partLinkIfc.getQuantity();ԭ��
          	  //CCBegin by leixiao 2010-12-22 ����Ϊ �߼��ܳ�ʱ,�Ӽ�����=����*�������� 
            	//System.out.println("--------����-"+part.getPartNumber()+" "+islogic+"  "+part.getPartNumber().substring(4,5));
            	if(islogiclist&&part.getPartNumber().length()>5&&part.getPartNumber().substring(4,5).equals("G")){
            		//System.out.println("--"+partIfc.getPartNumber()+"------����-"+part.getPartNumber().substring(4,5)+"---"+parentQuantity);
            		parentQuantity=parentQuantity*partLinkIfc.getQuantity();
            	}
            	else{
            	 
              parentQuantity = partLinkIfc.getQuantity(); //parentQuantity*partLinkIfc.getQuantity();
            	}
            	 //CCEnd by leixiao 2010-12-22 ����Ϊ �߼��ܳ�ʱ,�Ӽ�����=����*�������� 
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
      //ibaֱ������Ϊ""���Ժ������ȡiba���ٸ�ֵ��
      else if (attrfull.endsWith("-1"))
      {
      	tempArray[4 + j] = "";
      }
      //routeֱ������Ϊ""���Ժ������ȡroute���ٸ�ֵ��
      else if (attrfull.endsWith("-2"))
      {
      	tempArray[4 + j] = "";
      }
    }
    //yanqi-20061222-���Ӹ����������
    if(partLinkIfc.getBsoID()!=null&&!partLinkIfc.getBsoID().equals(""))
    {
    	//�˴���������,�Ա��жϸ����ǲ����߼��ܳ�
//      PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
//      QMPartIfc part=(QMPartIfc) ps.refreshInfo(partLinkIfc.getRightBsoID());
      if(part!=null)
        tempArray[tempArray.length-1]=part.getPartNumber();
    }//yanqi��20061222
    //CCBegin by leixiao 2010-12-22 �����ź�����з���Դ�汾���� 2800G17-48A/A
    //����Ǳ�׼��,����$����,����ʾ�汾��.
   // System.out.println(""+islogic+"---"+partIfc.getPartType());
  if(islogiclist&&partIfc.getPartType().toString().equals("Standard")){	
	  tempArray[4]=tempArray[4]+"$"; 
  }
//CCEnd by leixiao 2010-12-22 �����ź�����з���Դ�汾���� 2800G17-48A/A
    resultVector.addElement( ( (Object[]) (tempArray)));

    //////////////////////////////////�������
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
                             //CCBegin by liunan 2012-05-24 Ϊ�˵õ������ϼ���������·�ߣ���Ҫ��¼����ڵ�ǰ���������и����ļ��ϡ�
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
//CCEnd by leix	 2010-12-20  �����߼��ܳ���������
  
//CCBegin by leix	 2010-12-20    �߼��ܳ���������  ��������������а汾
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
//CCEnd by leix	 2010-12-20  �����߼��ܳ���������

  /**
   * �˷���Ϊ����רΪ��Ŷ��Ƶķ���������ҵ���ʵ��Ҳһֱͨ���˷�����ã����������������
   * ��Ʒ�д��ڵĸ÷�����Ȼ�������Ա�ʹ�á�
   * liuming add 20070209
   * ˽�з�������setMaterialList2()�������ã�ʵ�ֶ����޺ϼ�װ���Ĺ��ܡ�
   * ���ؽ����vector,����vector�е�ÿ��Ԫ�ض���һ�����ϣ�
   * 0����ǰpart��BsoID��
   * 1����ǰpart���ڵļ���ת��Ϊ�ַ��ͣ�
   * 2����ǰpart�ı�ţ�
   * 3����ǰpart�����ƣ�
   * 4����ǰpart����㲿��ʹ�õ�������ת��Ϊ�ַ��ͣ�
   * 5-...���ɱ�ģ����û�ж������ԣ�5����ǰpart�İ汾�ţ�6����ǰpart����ͼ��
   *                ������������ԣ��������ж��Ƶ����Լӵ���������С�

   * @param partIfc :QMPartIfc ��ǰ�Ĳ�����
   * @param attrNames :String[] ���Ƶ����Լ��ϣ�����Ϊ�գ�
   * @param affixAttrNames :String[] ���Ƶ���չ���������ϣ�����Ϊ�ա�
   * @param routeNames :String[] ���ƵĹ���·�������ϣ�����Ϊ�ա�
   * @param configSpecIfc :PartConfigSpecIfc ���ù淶��
   * @param level :int ��ǰpart���ڵļ���
   * @param partLinkIfc :PartUsageLinkIfc ��¼�˵�ǰpart�����׽ڵ��ʹ�ù�ϵ��ֵ����
   * @param parentQuantity :int ��ǰpart�ĸ��׽ڵ㱻�������ʹ�õ�������
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
    //��¼�����ͱ�������������ڵ�λ�á�
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
      tempArray = new Object[6];//yanqi-20061222 ���ӡ�������š�����
      tempArray[2] = new Integer(numberSite);//yanqi-20061222
    }
    else {
      //tempArray = new Object[4 + attrNames.length];
      tempArray = new Object[6 + attrNames.length];//yanqi-20061222���ӡ�������š�����
      tempArray[2] = new Integer(numberSite);
    }
    boolean affixAttrNullTrueFlag = affixAttrNames == null ||
        affixAttrNames.length == 0;
    boolean routeNullTrueFlag = routeNames == null ||
        routeNames.length == 0;

    tempArray[0] = partIfc.getBsoID();
    tempArray[1] = new Integer(1);
    tempArray[3] = new Integer(level); //level�ĳ�ʼֵΪ0��

    ///////////��ǵ�ǰ����Ƿ����߼��ܳ� liuming 20070303 add
    if (partIfc.getPartType().toString().equalsIgnoreCase("Logical"))
    {
      tempArray[4]="true";
    }
    else
    {
      tempArray[4]="false";
    }
    ///////////////////////////////liuming 20070303 add end

    //����㲿���Ĺ���·�ߡ�
    TechnicsRouteService trService = (TechnicsRouteService) EJBServiceHelper.
        getService("TechnicsRouteService");
    float countInparent = parentInProductCount * parentQuantity; //quxg add for jiefang �˱�������һ������ֱ�Ӹ����µ�����,����������·����ȡ��ֱ�ӻ��Ӹ����µ�ʹ������,����,��ֱ�Ӹ���Ϊ�߼��ܳ�,û��·��,�Ǿ�ȡ�߼��ܳɸ����µ�����,�������,���ܳ�����Ҫ��������

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
          String makeRoute = (String) map.get("����·��");
          if (makeRoute == null) {
            makeRoute = "";
          }
          if (makeRoute.indexOf("...") < 0) { //bushi�߼��ܳ�
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
          else { //���߼��ܳ�
            parentMakeRouteFirst = makeRoute;
          }

          if (partIfc.getPartType().toString().equalsIgnoreCase("Logical") &&
              (makeRoute != null && makeRoute.length() > 0)) {
            parentInProductCount = countInparent;
          }
          else {
            parentInProductCount = 1.0F;
          }

          String assRoute3 = (String) map.get("װ��·��");
          if (assRoute3 == null) {
            assRoute3 = "";
          }
          int o = assRoute3.indexOf("...");
          if (o > 0) {
            map.put("װ��ϼ�����", new Integer( (new Float(countInparent)).intValue()));
            String realassRoute = assRoute3.substring(0, o);
            String realParentNum = assRoute3.substring(o + 3);
            map.put("װ��·��", realassRoute);
            map.put("װ��·�ߺϼ���", realParentNum);

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
    //yanqi-20061222-���Ӹ����������
    if(partLinkIfc.getBsoID()!=null&&!partLinkIfc.getBsoID().equals(""))
    {
      PersistService ps=(PersistService)EJBServiceHelper.getPersistService();
      QMPartIfc part=(QMPartIfc) ps.refreshInfo(partLinkIfc.getRightBsoID());
      if(part!=null)
        tempArray[tempArray.length-1]=part.getPartNumber();
    }//yanqi��20061222
    resultVector.addElement( ( (Object[]) (tempArray)));
    Collection collection = getUsesPartIfcs(partIfc, configSpecIfc);

    /////////////////////////////////////////���� ���������

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
    //////////////////////////////////�������

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


//��������� �������嵥����ɱ����ļ�
  /**
  * �������嵥����ɱ����ļ������ڷּ�����������
  * ��com.faw_qm.part.client.other.controller.MaterialController�����˴˷�����
  * @param map HashMap ����õı������Լ��ϡ�
  * @throws QMException
  * @return String �����������ȫ���������Ϣ�����ڽ��б����ļ�д�롣
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
    String head = "�㲿����" + part.getPartNumber() + "(" + part.getPartName() +
        ")" + part.getVersionValue() +
        ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
         ("(" + part.getViewName() + ")")) +
        "�������嵥" +
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
   * �������嵥����ɱ����ļ��������޺ϼ�װ���������
   * ��com.faw_qm.part.client.other.view.LogicBomFrame�����˴˷�����
   * @param map HashMap ����õı������Լ��ϡ�
   * @throws QMException
   * @return String �����������ȫ���������Ϣ�����ڽ��б����ļ�д�롣
   * @author liunan 2008-08-01
   */
    public String getExportBOMClassfiyString2(HashMap map) throws QMException {
      StringBuffer backBuffer = new StringBuffer();
      String PartID = (String) map.get("PartID");
      PersistService persistService = (PersistService) EJBServiceHelper.
          getPersistService();
      QMPartIfc part = (QMPartIfc) persistService.refreshInfo(PartID);
      String head = "�㲿����" + part.getPartNumber() + "(" + part.getPartName() +
          ")" + part.getVersionValue() +
          ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
           ("(" + part.getViewName() + ")")) +
          "�������嵥" +
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

//��������� �������嵥����ɱ����ļ�
  /**
   * �������ݣ���������ÿ�е����ݣ���ֵ���á�,���ָ���������ɱ���excel�ļ�ʱ��
   * @param backBuffer StringBuffer ������������ɵ��ַ����ϡ�
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

//��������� �������嵥����ɱ����ļ�
  /**
   * �������嵥����ɱ����ļ�������ͳ�Ʊ���������
   * ��com.faw_qm.part.client.other.controller.MaterialController�����˴˷�����
   * @param map HashMap ����õı������Լ��ϡ�
   * @throws QMException
   * @return String �����������ȫ���������Ϣ�����ڽ��б����ļ�д�롣
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

    String head = "�㲿����" + part.getPartNumber() + "(" + part.getPartName() +
        ")" + part.getVersionValue() +
        ( (part.getViewName() == null || part.getViewName().length() < 1) ? "" :
         ("(" + part.getViewName() + ")")) +
        "���㲿����ϸ��\n";
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
   * �Ż����ĳɵ���getUsesPartsFromLinksNew������
   * ����ָ�����ù淶�����ָ��������ʹ�ýṹ��
   * ���ؼ���Collection��ÿ��Ԫ����һ��������󣬵�0��Ԫ�ؼ�¼����������Ϣ��
   * ��1��Ԫ�ؼ�¼���������¼��use��ɫ��mastered����ƥ�����ù淶��iterated����
   * ���û��ƥ����󣬼�¼mastered����
   * @param partIfc QMPartIfc �㲿��ֵ����
   * @param configSpecIfc PartConfigSpecIfc �㲿�����ù淶��
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
    //�����ǰ���ù淶�ǹ�����ͼ��������µĹ������°��Ӽ��ķ�����������ʹ�þɵĹ��˷�����
//    if(viewName.equals("������ͼ"))
//    {
//    	//����link���켯�ϣ�ÿ��Ԫ����һ����ά���飬�����һ����linkifc���ڶ�����partifc
//    	long t1 = System.currentTimeMillis();
//    	coll = getUsesPartsFromLinksNew(links, configSpecIfc);
//    	long t2 = System.currentTimeMillis();
//    	subTimeGong = subTimeGong+(t2-t1);
//    	//System.out.println(partIfc.getPartNumber()+" 'getUsesPartIfcs���� ��ʱ�� "+(t2-t1)+" ���룬���ϸ���Ϊ��"+coll.size()+" ��");
//    }
//    else
//    {
    	long t1 = System.currentTimeMillis();
    	coll = getUsesPartsFromLinks(links, configSpecIfc);
    	long t2 = System.currentTimeMillis();
    	subTimeOther = subTimeOther+(t2-t1);
    	//System.out.println(partIfc.getPartNumber()+" 'getUsesPartIfcs���� ��ʱ�� "+(t2-t1)+" ���룬���ϸ���Ϊ��"+coll.size()+" ��");
   // }
    //CCEnd SS11
    return coll;
  }

  /**
   * liunan 2009-04-05
   * ��in���������й������Ӽ�masterBsoID���в�ѯ�õ������°汾�㲿����
   * ����������һЩ���⣬�粻�ߵ�ǰ�û����ù淶�����ɸ������ù淶��õ���ͼΪ���������õ������
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
      QueryCondition condition2 = new QueryCondition("viewName", "=" , "������ͼ");
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
   * Ŀǰ���ƴ���δʹ�ã�2009-04-14��
   * ͨ��ָ����ɸѡ������������collection�е�PartUsageLinkIfc�����Ӧ��
   * ����ɸѡ������Iterated��������ɸѡ�����������ɸѡ�����򷵻ض�Ӧ��Mastered���㲿����
   * @param collection :Collection ��PartUsageLinkIfc����ļ��ϡ�
   * @param configSpecIfc :PartConfigSpecIfc �㲿����ɸѡ������
   * @return collection:Collection ����ÿ��Ԫ��Ϊһ������:
   * ����ĵ�һ��Ԫ��ΪPartUsageLinkIfc���󣬵ڶ���Ԫ��ΪQMPartIfc����
   * ���û��QMPartIfc����Ϊ������QMPartMasterIfc����
   * @throws QMException
   */
  public Collection getUsesPartsFromLinks(Collection collection,
      PartConfigSpecIfc configSpecIfc) throws QMException
  {
      //���collection�й��������ɫΪ"uses"��Masteredֵ����ļ��ϡ�
      //����masterCollection
     // Collection masterCollection = ConfigSpecHelper.mastersOf(collection, "uses");

      long t1 = System.currentTimeMillis();
      Collection masterCollection =mastersOf(collection);
      long t2 = System.currentTimeMillis();
      //System.out.println("mastersOf���� ��ʱ�� "+(t2-t1)+" ����");
      ConfigService cservice = (ConfigService)EJBServiceHelper.getService("ConfigService");
      //����masterCollection��mastered�������ķ������ù淶configSpec��
      //���е����汾�Ķ���ļ��ϣ�����ֵΪiteratedCollection
      Collection iteratedCollection = cservice.filteredIterationsOf(masterCollection, new PartConfigSpecAssistant(configSpecIfc));
      long t22 = System.currentTimeMillis();
      //System.out.println("cservice.filteredIterationsOf���� ��ʱ�� "+(t22-t2)+" ����");
      //���ڸ�����master����Щmaster�������iteration����ļ��ϣ�
      //�������е�iteration�����������iteration������û���κ�һ��iteration����
      //��֮��ƥ���master���󡣷��ض��󼯺�ΪallCollection
      Collection allCollection = ConfigSpecHelper.recoverMissingMasters(masterCollection,iteratedCollection);
      long t222 = System.currentTimeMillis();
      //System.out.println("recoverMissingMasters���� ��ʱ�� "+(t222-t22)+" ����");
      //����ָ���Ĺ������ϣ�master��iteration֮�䣩�Ͷ�Ӧmaster�����iteration���ϣ�
      //����ÿ�����������ӵ�iteration�����ؽ����������ÿ����������0λ�ô�Ź�������
      //��1λ�ô��iteration�������û�ж�Ӧ��iteration����
      //���Ź����������ӵ�master���� ��
      Collection coll = ConfigSpecHelper.buildConfigResultFromLinks(collection,"uses",allCollection);
      long t2222 = System.currentTimeMillis();
      //System.out.println("buildConfigResultFromLinks���� ��ʱ�� "+(t2222-t222)+" ����");
      return coll;
  }
  /**
   * Ŀǰ���ƴ���δʹ�ã�2009-04-14��
   * ����ָ���Ĺ�����master��iteration֮�䣩������ÿ�������������ӵ�
   * mastered����ָ����ɫmaster���Ľ����
   * @param links ������ֵ���󼯺�
   * @param role ��ɫ��
   * @exception com.faw_qm.config.exception.ConfigException
   * @return ��Ӧ������ֵ�����Mastered���󼯺ϡ�
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
              throw new QMException(e, "���ݽ�ɫ�������BsoIDʱ����");
          }
          BaseValueIfc bsoObj;
          PersistService persistService = (PersistService) EJBServiceHelper
                  .getService("PersistService");
          bsoObj = (BaseValueIfc) persistService.refreshInfo(bsoID, false);
          if(!(bsoObj instanceof QMPartMasterIfc))
          {
              throw new QMException();
          }//endif ���collection�е�Ԫ�������Ӷ���object����MasteredIfc��
          //ʵ�����׳���ɫ��Ч����
          resultVector.addElement(bsoObj);
      }//endfor i
      return removeDuplicates(resultVector);
  }
  /**
 * ��ָ���Ľ�������ظ���Ԫ���ų���
 * @param collection �����
 * @return Collection  �ų����ظ����ݵļ���
 * Collection��ÿһ��Ԫ��ΪһObject����
 * ��Object�����еĵ�0��Ԫ��Ϊһֵ����
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
        hashtable.put(objBsoID, "");//��BsoID��Ϊ��־����Hash��
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

//CCBegin by leixiao 2010-3-1 ���ͼֽ���ĵ���������
public HashMap ExportFile(Vector list,String path, String type)
throws QMException{
	setVersionIBA("����Դ�汾");
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
  
  System.out.println(" ���idΪ��"+partid+" �ĵ�Ϊ��"+retVal);
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
		if(type.equals("���ͼֽ")){
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
		//System.out.println("-----���"+coll);
		
		if(coll!=null&&coll.size()!=0){
	      String partid=getPartidFromIBA(coll,version);
	      if(partid!=null)			
			return getEpmDocFromPart(partid);
		}
		else
			return null;
	}
	else if (type.equals("�ĵ�����")){
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
		//CCBegin by leixiao 2010-12-15 ���ͼֽ��������һ��С�汾�ĵ����ݵ���
	else if (type.equals("С�汾�ĵ�����")){
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
	//CCEnd by leixiao 2010-12-15 ���ͼֽ��������һ��"С�汾�ĵ�����"����
	else{
		System.out.println("���������ַ���");
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
    		//System.out.println("partversion="+partversion+" ��Ҫ��="+version );	
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
//��ѯ������ϵ
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
 * �����û���������û���
 * @param name  �û�����
 * @return Group  ��õ��û���
 * @throws QMException
 */
private Group getGroup(String name)
        throws QMException
{

    //�����ѯ���
    QMQuery query = new QMQuery("Group");
    QueryCondition cond = new QueryCondition("usersName", "=", name);
    query.addCondition(cond);
    //��ȡ�־û����񲢽��в�ѯ
    PersistService persistService = (PersistService) EJBServiceHelper.
                                    getPersistService();
    Collection coll = persistService.findBso(query);
    //��δ�ҵ������������û��򷵻ؿ�
    if (coll.size() == 0)
    {
        return null;
    }
    Iterator iter = coll.iterator();
    Group group = (Group) iter.next();

    return group;
}
//CCEnd by leixiao 2010-3-1 ���ͼֽ���ĵ���������

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
    //��ȡ�־û����񲢽��в�ѯ
    PersistService persistService = (PersistService) EJBServiceHelper.
                                    getPersistService();
    Collection coll = persistService.findValueInfo(query,false);
    //��δ�ҵ������������û��򷵻ؿ�
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
	    //�汾��ΪA.1��
	    query.addCondition(new QueryCondition("versionValue", QueryCondition.NOT_EQUAL, "A.1"));
	    query.addAND();
	    //�����°汾��
		query.addCondition(new QueryCondition("iterationIfLatest",Boolean.TRUE));
	    query.addAND();
	    //����Ա���µ�
		query.addCondition(new QueryCondition("iterationModifier","=","User_113"));
	    query.addAND();
	    //����Ϊ������� ��ʷ��\���ĵ� ��ʷ��\ͼֽ  ��ʷ��\��ϸ��
	    query.addCondition(new QueryCondition("docCfBsoID", QueryCondition.IN, new String[]{"DocClassification_779260","DocClassification_779267","DocClassification_779270"}));
	  //  System.out.println(query.getDebugSQL());
	    //��ȡ�־û����񲢽��в�ѯ
	    query.setChildQuery(false);
	    PersistService persistService = (PersistService) EJBServiceHelper.
	                                    getPersistService();
	    Collection coll = persistService.findValueInfo(query,false);
	    //��δ�ҵ������������û��򷵻ؿ�
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
	//System.out.println("------��Ҫ������ĵ�"+doc);
    QMQuery query1 = new QMQuery("Doc");
    //�汾��ΪA.1��
	query1.addCondition(new QueryCondition("branchID", "=",doc.getBranchID()));
	query1.addAND();
	query1.addCondition(new QueryCondition("iterationIfLatest",Boolean.FALSE));
	query1.addAND();
	query1.addCondition(0,new QueryCondition("workableState","=","c/i"));	
    //��ȡ�־û����񲢽��в�ѯ
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
	   
	 //  System.out.println("�Ѵ��="+leftdocstring);

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
            	   System.out.println("-----------���е�,����");
	            continue;
               }
               if(issave.equals("save"))
               { 
            	   System.out.println("-----------������"+doc.getBsoID()+" "+linkdoc.getBsoID()); 
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
     * ���·����Ϣ�����崦��in��sql��ʽ��ÿ������20����
     * ��������ڽ�Ų��Ի��϶�β��Խ���ó���Ч����Ѹ�����
     * @param routeTempMap HashMap routeֵ�Ļ��棬keyΪpartMasterBsoID+route���ƣ�ֵ�������part��Ӧroutename��ֵ��
     * @param bomMap HashMap ���б������漰���㲿���ļ��ϡ�keyΪpartBsoID��
     * ֵΪ��ά���飬��һ��Ԫ����partMasterBsoID���ڶ���Ԫ�����丸����partMasterBsoID��
     * @return HashMap ����routeTempMap��
     */
    private HashMap getAllRouteValue(HashMap routeTempMap, HashMap bomMap)
    {
    	//�㲿��masterbsoid����
    	String[] routeNames = (String[])routeTempMap.get("routeName");
    	int routeNameSize = routeNames.length;
    	String partMasterID = "";
    	boolean isHasRouteList = false;
    	//��¼��ǰ��partid��������ӵ��㲿������
    	int i = 0;
    	//�����ݿ����
    	int k = 0;
    	for(int a=0;a<routeNameSize;a++)
    	{
    		//System.out.println("routeNames[a]==="+routeNames[a]);
    		if(routeNames[a].equals("��׼���")||routeNames[a].equals("��ע"))
    		{
    			isHasRouteList = true;
    		}
    	}
    	//System.out.println("isHasRouteList==="+isHasRouteList);
    	// ��20���㲿��Ϊ��λ����һ�β�ѯ���õ����㲿����route����

    	//select branch.routestr,link.rightbsoid,link.leftbsoid, route.modefyIdenty from technicsroutebranch branch, listroutepartlink link,technicsroute route,technicsroutelist tcl where branch.routeid=link.routeid and route.bsoid=branch.routeid and link.rightbsoid in ('QMPartMaster_54181538') and tcl.bsoid=link.leftbsoid and tcl.iterationiflatest=1 and tcl.routeliststate!='��׼' and tcl.owner is null order by tcl.modifytime desc
    	//����sql���õ�ȫ��·�ߡ�����Ҫ����ȥ����ȡ����·�ߡ�
    	//CCBegin by liunan 2009-06-10 ���TechnicsRouteList��Ϊ��׼��technicsroute����ȡ��״̬��ȥ��alterstatus='1'������
       //CCBegin SS9
    	//String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid, route.modefyIdenty from technicsroutebranch branch, listroutepartlink link,technicsroute route,technicsroutelist tcl where branch.routeid=link.routeid and route.bsoid=branch.routeid and link.rightbsoid in (";
    	String sql1 = "select branch.routestr,link.rightbsoid,link.leftbsoid, route.modefyIdenty,link.colorflag from technicsroutebranch branch, listroutepartlink link,technicsroute route,technicsroutelist tcl where branch.routeid=link.routeid and route.bsoid=branch.routeid and link.rightbsoid in (";
    	//CCEnd SS9 	//CCBegin by liunan 2012-04-25 ��Ƽ����������ϼе���׼��ȡ��·�ߡ�    	
    	String sql2 = ") and tcl.bsoid=link.leftbsoid and tcl.iterationiflatest=1 and tcl.routeliststate!='��׼' and tcl.owner is null order by tcl.modifytime desc";
    	//CCEnd by liunan 2012-04-25
    	//CCEnd by liunan 2009-06-10

    	Connection conn = null;
      Statement stmt =null;
      ResultSet rs=null;
  	  try
  	  {
  	  	PersistService pService = (PersistService) EJBServiceHelper.getService("PersistService");
  	    //������������׼��masterid����������
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
      					//������·�ߴ�������ֻȡһ�����С�  ||routeNames[ii].equals("װ��·��")
      					if(routeNames[ii].equals("����·��"))
      					{
      						keyName = rs.getString(2)+"·��";
    			  			//System.out.println("keyName==="+keyName);
      						if(routeTempMap.containsKey(keyName))
      						{
      							HashMap routemap = (HashMap)routeTempMap.get(keyName);
      							if(routemap.containsKey(rs.getString(3)+rs.getString(4)))
      							{
      								//CCBegin SS5
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
      					else if(routeNames[ii].equals("��׼���"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							routeTempMap.put(keyName,routelist.getRouteListNumber());
      						}
      					}
      					//CCBegin SS14
      					else if(routeNames[ii].equals("��׼˵��"))
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
      							if(des.indexOf("˵����")!=-1)
      							{
      								des = des.substring(des.indexOf("˵����"),des.length());
      							}
      							routeTempMap.put(keyName, des);
      						}
      					}
      					else if(routeNames[ii].equals("��׼״̬"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							routeTempMap.put(keyName,routelist.getLifeCycleState().getDisplay());
      						}
      					}
      					//CCEnd SS14
      						//CCBegin SS9
      					else if(routeNames[ii].equals("��ɫ����ʶ"))
      					{
      						keyName = rs.getString(2)+routeNames[ii];
      						if(!routeTempMap.containsKey(keyName))
      						{
      							String color="";
      							if(rs.getString(5)!=null&&rs.getString(5).equals("1")){
      								color="��";
      							}
      							routeTempMap.put(keyName,color);
      						}
      					}
      					//CCEnd SS9
      					else if(routeNames[ii].equals("��ע"))
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
      				//�õ�����·�߱��� anan
      				/*if(!rs.getString(4).equals("Coding_221664")&&!routeTempMap.containsKey(rs.getString(2)+"����·��"))
      				{
      					routeTempMap.put(rs.getString(2)+"����·��",rs.getString(1));
      				}*/
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
      				//CCEnd SS4 SS17
      				
      			}
    				//System.out.println("routeTempMap==="+routeTempMap);
    				//��·�ߴ�����
    			  String temp[] = partMasterID.split(",");
    			  for(int ii = 0;ii<temp.length;ii++)
    			  {
    			  	String id = temp[ii].substring(1,temp[ii].length()-1);
    			  	HashMap routemap = (HashMap)routeTempMap.get(id+"·��");
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
    			  				//else if(zhizaostr.indexOf(getZhiZaoRoute(str))==-1)//anan �����ж����zhizaostr�������getZhiZaoRoute(str)���в�һ������ӣ���Ҫ���� Э--��(��)/Э  ��  �� ���������
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
    			  			/*HashMap routemap1 = (HashMap)routeTempMap.get(id+"·��");
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
    			  			/*String str1 = routeTempMap.get(id+"����·��").toString();
    			  			System.out.println("str1========="+str1);
    			  			zhizaostr = getZhiZaoRoute(str1);
    			  			zhuangpeistr = getZhuangPeiRoute(str1);*/
    			  			
    			  			String str1 = routeTempMap.get(id+"����·��").toString();
    			  			//if(id.equals("QMPartMaster_119298465"))
    			  			//System.out.println("����·�� str1========="+str1);
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
    			  				//else if(zhizaostr.indexOf(getZhiZaoRoute(str))==-1)//anan �����ж����zhizaostr�������getZhiZaoRoute(str)���в�һ������ӣ���Ҫ���� Э--��(��)/Э  ��  �� ���������
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
    			  //System.out.println("����·��======"+zhizaostr);
    			  //System.out.println("װ��·��======"+zhuangpeistr);
    			  	routeTempMap.remove(id+"·��");
    			  	routeTempMap.put(id+"����·��",zhizaostr);
    			  	routeTempMap.put(id+"װ��·��",zhuangpeistr);    			  	
    			  }

      			// ��i��0�����¿�ʼ����
      			i = 0;
      			// �����ݿ����
      			k++;
      			// bomMap�����������㲿������
      			//int next = bomMap.size() - routebasenum * k;
      			//System.out.println("��"+k+"��-------����������"+next);
      			partMasterID = "";
				  }
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
    	return routeTempMap;
    }
    //CCEnd by liunan 2012-04-28
    
}
