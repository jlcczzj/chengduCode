/**
 * 生成程序MaterialServiceHelper.java	1.0              2007-10-8
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.faw_qm.clients.common.CommonDataHelper;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.part.client.main.controller.PartRequestServer;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.route.ejb.entity.TechnicsRouteList;
import com.faw_qm.util.EJBServiceHelper;

/**
 * <p>Title: 物料相关服务工具类。</p>
 * <p>Description: 为瘦客户端提供的方法。每个方法均调用服务中的同名方法。</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public class MaterialServiceHelper  extends CommonDataHelper implements Serializable
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(MaterialServiceHelper.class);

    private static final long serialVersionUID = 1L;
    
    /**
     * 从erp.properties文件中读取青汽路线分类简称和路线内码之间的对应关系，并存到hashMap中
     * @return HashMap
     */
    public static HashMap getRouteCodeHashMap()
    {
    	HashMap has=new HashMap();
    	String code= RemoteProperty.getProperty("com.faw_qm.jferp.routecode");
    	StringTokenizer to=new StringTokenizer(code,";");
    	while(to.hasMoreTokens())
    	{
    		String codeStr=to.nextToken();
    		String routeCodeStr= RemoteProperty.getProperty("com.faw_qm.jferp.routecode."+codeStr);
    		has.put(routeCodeStr, codeStr);
    	}
    	return has;
    }

    //20080103 begin
    /**
     * 拆分物料。
     * @param partBsoIDs 以“;”为分隔符的零部件BsoID集合。
     * @param doSplit 为处理“若零件旧版本已拆分为物料，本次拆分是否重新拆分”的情况准备的boolean变量。true：重新拆分；false：不重新拆分。
     * return HashMap 物料Map。键：一组物料共有信息数组；值：物料信息数组集合。
     * @throws QMException
     */
    public static HashMap split(String partBsoIDs, String route, boolean doSplit)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("split(String, boolean) - start"); //$NON-NLS-1$
        }
        HashMap returnHashMap = (HashMap) RequestHelper.request(
                "MaterialSplitService", "split", new Class[]{String.class, String.class,
                        boolean.class}, new Object[]{partBsoIDs,route,
                        new Boolean(doSplit)});
        if(logger.isDebugEnabled())
        {
            logger.debug("split(String, boolean) - end"); //$NON-NLS-1$
        }
        return returnHashMap;
    }
    //20080103 end

    /**
     * 根据零件号获取物料。为瘦客户显示使用。
     * @param partNumberList 零件号集合。
     * @return 物料Map。键：一组物料共有信息数组；值：物料信息数组集合。
     * @throws QMException
     */
    public static HashMap getAllMaterial(List partNumberList)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllMaterial(List) - start"); //$NON-NLS-1$
        }
        HashMap returnHashMap = (HashMap) RequestHelper.request(
                "MaterialSplitService", "getAllMaterial",
                new Class[]{List.class}, new Object[]{partNumberList});
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllMaterial(List) - end"); //$NON-NLS-1$
        }
        return returnHashMap;
    }

    public static void createInterimMaterial(String partNumber)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("createInterimMaterial(String) - start"); //$NON-NLS-1$
        }
        RequestHelper.request("MaterialSplitService", "createInterimMaterial",
                new Class[]{String.class}, new Object[]{partNumber});
        if(logger.isDebugEnabled())
        {
            logger.debug("createInterimMaterial(String) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 根据零件号获取物料。为瘦客户显示使用。
     * @param partNumberList 零件号集合。
     * @return 物料Map。键：一组物料共有信息数组；值：物料信息数组集合。
     * @throws QMException
     */
    public static HashMap getAllInterimMaterial(String partNumber)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllInterimMaterial(String) - start"); //$NON-NLS-1$
        }
        HashMap returnHashMap = (HashMap) RequestHelper.request(
                "MaterialSplitService", "getAllInterimMaterial",
                new Class[]{String.class}, new Object[]{partNumber});
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllInterimMaterial(String) - end"); //$NON-NLS-1$
        }
        return returnHashMap;
    }

    /**
     * 只删结构关系。将更新标识设置为“D”。
     * @param parentPartNumber 父件号。
     * @param parentNumber 父物料号。
     * @param childBsoID 被删除的临时表记录的bsoID。
     * @throws QMException 
     */
    public static void delete(String parentPartNumber, String parentNumber,
            String childBsoID) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("delete(String, String, String) - start"); //$NON-NLS-1$
        }
        RequestHelper.request("MaterialSplitService", "delete", new Class[]{
                String.class, String.class, String.class}, new Object[]{
                parentPartNumber, parentNumber, childBsoID});
        if(logger.isDebugEnabled())
        {
            logger.debug("delete(String, String, String) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 只改结构关系中的子物料号。层级、层级状态都不变。需要将替换的新物料的信息添加到临时表中。需要父件号、父物料号，临时表子物料bsoID，替换物料的bsoID。
     * xieb同级有多个相同子件时如何办？？？？？？
     * @param parentPartNumber 父件号。
     * @param parentNumber 父物料号。
     * @param childBsoID 临时表子物料bsoID。
     * @param replaceBsoID 替换物料的bsoID。
     * @throws QMException 
     */
    public static void replace(String parentPartNumber, String parentNumber,
            String childBsoID, String replaceBsoID) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("replace(String, String, String, String) - start"); //$NON-NLS-1$
        }
        RequestHelper.request("MaterialSplitService", "replace", new Class[]{
                String.class, String.class, String.class, String.class},
                new Object[]{parentPartNumber, parentNumber, childBsoID,
                        replaceBsoID});
        if(logger.isDebugEnabled())
        {
            logger.debug("replace(String, String, String, String) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 正式执行更新。需要校验物料号的唯一性。
     * @param updateMap 键：临时表bsoID。值：修改的物料号。
     * @throws QMException
     */
    public static void updateMaterial(HashMap updateMap) throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("updateMaterial(HashMap) - start"); //$NON-NLS-1$
        }
        RequestHelper.request("MaterialSplitService", "updateMaterial",
                new Class[]{HashMap.class}, new Object[]{updateMap});
        if(logger.isDebugEnabled())
        {
            logger.debug("updateMaterial(HashMap) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 删除所有临时表中的数据。
     * @throws QMException
     */
    public static void deleteAllInterimData() throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteAllInterimData() - start"); //$NON-NLS-1$
        }
        RequestHelper.request("MaterialSplitService", "deleteAllInterimData",
                new Class[]{}, new Object[]{});
        if(logger.isDebugEnabled())
        {
            logger.debug("deleteAllInterimData() - end"); //$NON-NLS-1$
        }
    }

    /**
     * 根据零件号获取物料。
     * @param partNumber 零件号。 
     * @return 物料集合。
     * @throws QMException
     */
    public static List getAllInterimMSplit(String partNumber)
            throws QMException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllInterimMSplit(String) - start"); //$NON-NLS-1$
        }
        List returnList = (List) RequestHelper.request("MaterialSplitService",
                "getAllInterimMSplit", new Class[]{String.class},
                new Object[]{partNumber});
        if(logger.isDebugEnabled())
        {
            logger.debug("getAllInterimMSplit(String) - end"); //$NON-NLS-1$
        }
        return returnList;
    }
    
	/**
	 * 解决科学技术法的问题，保留八位小数。
	 */
	public static String sicenToComm(double value) {
		String retValue = null;
		DecimalFormat df = new DecimalFormat();
		//设置输出数值的格式为XX.XX
		//df = new DecimalFormat("##0.00000000");
		df.setMinimumFractionDigits(8);
		df.setMaximumFractionDigits(8);
		retValue = df.format(value);
		retValue = retValue.replaceAll(",", "");
		return retValue;
	}

	/**
	 * 解决科学技术法的问题，保留八位小数。
	 */
	public static String sicenToComm(float value) {
		String retValue = null;
		DecimalFormat df = new DecimalFormat();
		//设置输出数值的格式为XX.XX
		//df = new DecimalFormat("##0.00000000#########");
		df.setMinimumFractionDigits(8);
		df.setMaximumFractionDigits(8);
		retValue = df.format(value);
		retValue = retValue.replaceAll(",", "");
		return retValue;
	}

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("main(String[]) - start"); //$NON-NLS-1$
        }
        String a = "01-02-03";
        String b = "-01";
        System.out.println("aaaaaaaaa" + a.lastIndexOf(b));
        if(logger.isDebugEnabled())
        {
            logger.debug("main(String[]) - start"); //$NON-NLS-1$
        }
        //      测试数据。
        //            descData.setFileNumber(notifyIfc.getPromulgateNotifyNumber());
        //            descData.setType(notifyIfc.getPromulgateNotifyType());
        //            descData.setSourcetag("无");
        //            descData.setDate("无");
        //            descData.setNotes(notifyIfc.getPromulgateNotifyDescription());
        if(args != null && args.length > 0)
        {
            try
            {
                PartRequestServer partServer = new PartRequestServer(args[0],
                        args[1], args[2]);
                RequestServerFactory.setRequestServer(partServer);
            }
            catch (Exception e)
            {
                logger.error("main(String[])", e); //$NON-NLS-1$
                if(logger.isDebugEnabled())
                {
                    logger.debug("main(String[]) - end"); //$NON-NLS-1$
                }
                return;
            }
        }
        else
        {
            try
            {
                String sid = PartRequestServer.getSessionID("192.168.0.110",
                        "80", "Administrator", "administrator");
                PartRequestServer server = new PartRequestServer(
                        "192.168.0.110", "80", sid);
                RequestServerFactory.setRequestServer(server);
            }
            catch (Exception ex)
            {
                logger.error("main(String[])", ex); //$NON-NLS-1$
                if(logger.isDebugEnabled())
                {
                    logger.debug("main(String[]) - end"); //$NON-NLS-1$
                }
                return;
            }
        }
        try
        {
            //                        String partBsoIDs = "QMPart_52341;QMPart_53803";
            //                        MaterialServiceHelper.split(partBsoIDs, true);
            //                        String part = "QMPart_52341";
            //                        QMPartIfc partIfc = (QMPartIfc) RequestHelper.request(
            //                                "PersistService", "refreshInfo", new Class[]{String.class},
            //                                new Object[]{part});
            //                        String part2 = "QMPart_52843";
            //                        QMPartIfc partIfc2 = (QMPartIfc) RequestHelper.request(
            //                                "PersistService", "refreshInfo", new Class[]{String.class},
            //                                new Object[]{part2});
            //                        List partList = new ArrayList();
            //                        partList.add(partIfc.getPartNumber());
            //                        partList.add(partIfc2.getPartNumber());
            //                        MaterialServiceHelper.getAllMaterial(partList);
            String parentPartNumber = "p01";
            String parentNumber = "p01";
            MaterialServiceHelper.createInterimMaterial(parentPartNumber);
            MaterialServiceHelper.delete(parentPartNumber, parentNumber,
                    "InterimMaterialSplit_81025");
            //            helper.replace(parentPartNumber, parentNumber,
            //                    "InterimMaterialSplit_63665", "MaterialSplit_63613");
        }
        catch (QMException e)
        {
            logger.warn("main(String[]) - exception ignored", e); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("main(String[]) - end"); //$NON-NLS-1$
        }
    }
    
    public Collection searchRoute(String number,String name)throws QMException
    {
    	try
    	{
    		PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
    		QMQuery query=new QMQuery("TechnicsRouteList");
    		int i=query.appendBso("TechnicsRouteListMaster", false);
    		if(number!=null && number.trim().length()>0)
    		{
    		query.addCondition(i,new QueryCondition("routeListNumber",QueryCondition.LIKE,getLikeSearchString(number)));
    		query.addAND();
    		}
    		
    		if(name!=null && name.trim().length()>0)
    		{
    		query.addCondition(i,new QueryCondition("routeListName",QueryCondition.LIKE,getLikeSearchString(name)));
    		query.addAND();
    		}
    		query.addCondition(0,i,new QueryCondition("masterBsoID","bsoID"));
    		query.addAND();
    		query.addCondition(new QueryCondition("iterationIfLatest",true));
    		Collection coll=pservice.findValueInfo(query,false);
    		return coll;
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		throw new QMException(ex);
    	}
    }
}
