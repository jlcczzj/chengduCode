/**
 * ���ɳ���MaterialServiceHelper.java	1.0              2007-10-8
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ������ط��񹤾��ࡣ</p>
 * <p>Description: Ϊ�ݿͻ����ṩ�ķ�����ÿ�����������÷����е�ͬ��������</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
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
     * ��erp.properties�ļ��ж�ȡ����·�߷����ƺ�·������֮��Ķ�Ӧ��ϵ�����浽hashMap��
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
     * ������ϡ�
     * @param partBsoIDs �ԡ�;��Ϊ�ָ������㲿��BsoID���ϡ�
     * @param doSplit Ϊ����������ɰ汾�Ѳ��Ϊ���ϣ����β���Ƿ����²�֡������׼����boolean������true�����²�֣�false�������²�֡�
     * return HashMap ����Map������һ�����Ϲ�����Ϣ���飻ֵ��������Ϣ���鼯�ϡ�
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
     * ��������Ż�ȡ���ϡ�Ϊ�ݿͻ���ʾʹ�á�
     * @param partNumberList ����ż��ϡ�
     * @return ����Map������һ�����Ϲ�����Ϣ���飻ֵ��������Ϣ���鼯�ϡ�
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
     * ��������Ż�ȡ���ϡ�Ϊ�ݿͻ���ʾʹ�á�
     * @param partNumberList ����ż��ϡ�
     * @return ����Map������һ�����Ϲ�����Ϣ���飻ֵ��������Ϣ���鼯�ϡ�
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
     * ֻɾ�ṹ��ϵ�������±�ʶ����Ϊ��D����
     * @param parentPartNumber �����š�
     * @param parentNumber �����Ϻš�
     * @param childBsoID ��ɾ������ʱ���¼��bsoID��
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
     * ֻ�Ľṹ��ϵ�е������Ϻš��㼶���㼶״̬�����䡣��Ҫ���滻�������ϵ���Ϣ��ӵ���ʱ���С���Ҫ�����š������Ϻţ���ʱ��������bsoID���滻���ϵ�bsoID��
     * xiebͬ���ж����ͬ�Ӽ�ʱ��ΰ죿����������
     * @param parentPartNumber �����š�
     * @param parentNumber �����Ϻš�
     * @param childBsoID ��ʱ��������bsoID��
     * @param replaceBsoID �滻���ϵ�bsoID��
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
     * ��ʽִ�и��¡���ҪУ�����Ϻŵ�Ψһ�ԡ�
     * @param updateMap ������ʱ��bsoID��ֵ���޸ĵ����Ϻš�
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
     * ɾ��������ʱ���е����ݡ�
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
     * ��������Ż�ȡ���ϡ�
     * @param partNumber ����š� 
     * @return ���ϼ��ϡ�
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
	 * �����ѧ�����������⣬������λС����
	 */
	public static String sicenToComm(double value) {
		String retValue = null;
		DecimalFormat df = new DecimalFormat();
		//���������ֵ�ĸ�ʽΪXX.XX
		//df = new DecimalFormat("##0.00000000");
		df.setMinimumFractionDigits(8);
		df.setMaximumFractionDigits(8);
		retValue = df.format(value);
		retValue = retValue.replaceAll(",", "");
		return retValue;
	}

	/**
	 * �����ѧ�����������⣬������λС����
	 */
	public static String sicenToComm(float value) {
		String retValue = null;
		DecimalFormat df = new DecimalFormat();
		//���������ֵ�ĸ�ʽΪXX.XX
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
        //      �������ݡ�
        //            descData.setFileNumber(notifyIfc.getPromulgateNotifyNumber());
        //            descData.setType(notifyIfc.getPromulgateNotifyType());
        //            descData.setSourcetag("��");
        //            descData.setDate("��");
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
