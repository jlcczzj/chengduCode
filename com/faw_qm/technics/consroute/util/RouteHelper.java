/**
 * ���ɳ��� RouteHelper.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.util;

import java.lang.reflect.Method;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.cappclients.conscapproute.graph.RouteItem;
import com.faw_qm.cappclients.conscapproute.util.RouteClientUtil;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.EnumeratedType;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.RouteBranchNodeLinkIfc;
import com.faw_qm.technics.consroute.model.RouteBranchNodeLinkInfo;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.RouteNodeLinkIfc;
import com.faw_qm.technics.consroute.model.RouteNodeLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.util.EJBServiceHelper;

/**
 * ����·�߷������ �ṩ�������㷨,ͨ����Ĵ���������ʱ��ļ���ȷ���
 * @author ������
 * @version 1.0
 */
public class RouteHelper
{
    private static final String ROUTE_SERVICE = "consTechnicsRouteService";

    /**
     * ���캯��
     */
    private RouteHelper()
    {}

    /**
     * ˢ��ֵ����
     * @param bsoid String ָ����ֵ����
     * @throws QMException
     * @return BaseValueIfc ˢ�º��ֵ����
     */
    public static BaseValueIfc refreshInfo(String bsoid) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        BaseValueIfc valueInfo = pservice.refreshInfo(bsoid);
        return valueInfo;
    }

    /**
     * ��ö��������ȡ��display���洢��ֵ
     * @param type EnumeratedType[] ö������
     * @param display String
     * @return String ö��������ȡ��display���洢��ֵ
     */
    public static String getValue(EnumeratedType[] type, String display)
    {
        String value = null;
        for(int i = 0;i < type.length;i++)
        {
            if(type[i].getDisplay().equals(display))
            {
                value = type[i].toString();
                break;
            }
        }
        return value;
    }

    /**
     * ���·�߷���
     * @throws QMException
     * @return TechnicsRouteService ������
     */
    public static TechnicsRouteService getRouteService() throws QMException
    {
        return (TechnicsRouteService)EJBServiceHelper.getService(ROUTE_SERVICE);
    }

    /**
     * �����"*"���ַ���
     * @param s String Ҫ������ַ���
     * @param aobj Object[]
     * @return String
     */
    public static String insertKeys(String s, Object aobj[])
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("RouteHelper's insertKeys ->IN, s = " + s + ", aobj[] = " + aobj[1]);
        }
        StringBuffer stringbuffer = new StringBuffer();
        int i = aobj.length;
        for(int j = 0;j < i;j++)
        {
            int k = s.indexOf("*");
            if(k != -1)
            {
                stringbuffer.append(s.substring(0, k));
                if(aobj[j] != null)
                {
                    stringbuffer.append(aobj[j].toString());
                }
                s = s.substring(k + 1);
            }
        }
        if(s.indexOf("*") != -1)
        {
            s = s.replace('*', ' ');
        }
        stringbuffer.append(s);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("RouteHelper's  insertKeys ->OUT " + stringbuffer);
        }
        return stringbuffer.toString();
    }

    /**
     * �����㷨������bsoid����������������
     * @param valueInfos Object[] ָ�������bsoid��BaseValueIfc�����͵ļ���
     * @return Collection Object[]���ͼ��ϣ�valueInfos[i]������bsoid�ļ���
     * @throws QMException 
     */
    private static Collection sortedInfos(Object[] valueInfos) throws QMException
    {
        //����
        for(int j = valueInfos.length;j > 0;j--)
        {
            if(TechnicsRouteServiceEJB.VERBOSE)
            {
                //System.out.println("j= " + j);
            }
            Object temp = null;
            for(int i = 0;i < j - 1;i++)
            {
                if(TechnicsRouteServiceEJB.VERBOSE)
                {
                    //          //System.out.println("valueInfos[" + i + "]=
                    //          // "+((BaseValueIfc)valueInfos[i]).getBsoID());
                    //          //System.out.println("valueInfos[" + (i+1) + "]=
                    //          // "+((BaseValueIfc)valueInfos[i+1]).getBsoID());
                }
                if(!(valueInfos[i] instanceof BaseValueIfc))
                {
                    throw new QMException("������ֻ֧��BaseValueIfc");
                }
                //����bsoid�������򣬰�bsoidȷ�����򡣣����ų�ͬһ�汾��ԭ���͸�����˳�򡣣�
                if(((BaseValueIfc)valueInfos[i]).getBsoID().compareTo(((BaseValueIfc)valueInfos[i + 1]).getBsoID()) > 0)
                {
                    //�����滻
                    temp = valueInfos[i];
                    valueInfos[i] = valueInfos[i + 1];
                    valueInfos[i + 1] = temp;
                }
            }
        }
        Collection sorted = new Vector();
        for(int i = 0;i < valueInfos.length;i++)
        {
            sorted.add(valueInfos[i]);
        }
        return sorted;
    }

    /**
     * �����㷨�����ݸ�����������������
     * @param valueInfos Object[] ָ����ֵ���󼯺ϣ�BaseValueIfc���ͣ�
     * @param methodName String ����ĳ�����ķ���ֵ���� ��:getRouteListNumber
     * @param flag boolean boolean true, ����; false, ����
     * @return Collection vector���ϣ�<br> �����һ��Object[] ��������BaseValueIfc�����
     * @throws QMException 
     */
    private static Collection sortedInfos(Object[] valueInfos, String methodName, boolean flag) throws QMException
    {
        //����
        for(int j = valueInfos.length;j > 0;j--)
        {
            if(TechnicsRouteServiceEJB.VERBOSE)
            {
                System.out.println("j= " + j);
            }
            Object temp = null;
            for(int i = 0;i < j - 1;i++)
            {
                if(TechnicsRouteServiceEJB.VERBOSE)
                {
                    //System.out.println("valueInfos[" + i + "]=
                    // "+((BaseValueIfc)valueInfos[i]).getBsoID());
                    //System.out.println("valueInfos[" + (i+1) + "]=
                    // "+((BaseValueIfc)valueInfos[i+1]).getBsoID());
                }
                if(!(valueInfos[i] instanceof BaseValueIfc))
                {
                    throw new QMException("������ֻ֧��BaseValueIfc");
                }
                //����bsoid�������򣬰�bsoidȷ�����򡣣����ų�ͬһ�汾��ԭ���͸�����˳�򡣣�
                BaseValueIfc info1 = (BaseValueIfc)valueInfos[i];
                BaseValueIfc info2 = (BaseValueIfc)valueInfos[i + 1];
                Comparable value1 = getPropertyValue(info1, methodName);
                if(TechnicsRouteServiceEJB.VERBOSE)
                {
                    //System.out.println("value1================="+value1);
                }
                Comparable value2 = getPropertyValue(info2, methodName);
                if(TechnicsRouteServiceEJB.VERBOSE)
                {
                    //System.out.println("value2================="+value2);
                }
                //����
                if(flag)
                {
                    if(value1.compareTo(value2) < 0)
                    {
                        //�����滻
                        temp = valueInfos[i];
                        valueInfos[i] = valueInfos[i + 1];
                        valueInfos[i + 1] = temp;
                    }
                }
                //����
                else
                {
                    if(value1.compareTo(value2) > 0)
                    {
                        //�����滻
                        temp = valueInfos[i];
                        valueInfos[i] = valueInfos[i + 1];
                        valueInfos[i + 1] = temp;
                    }
                }
            }
        }
        Collection sorted = new Vector();
        for(int i = 0;i < valueInfos.length;i++)
        {
            sorted.add(valueInfos[i]);
        }
        return sorted;
    }

    /**
     * ������ֵ����
     * @param info BaseValueIfc
     * @param methodName String ������
     * @return Comparable ��BaseValueIfc����ΪComparable������������ֵ
     * @throws QMException 
     * @see BaseValueInfo
     */
    private static Comparable getPropertyValue(BaseValueIfc info, String methodName) throws QMException
    {
        Object value = null;
        try
        {
            Method method = info.getClass().getMethod(methodName, null);
            value = method.invoke(info, null);
        }catch(Exception ex)
        {
            throw new QMException(ex);
        }
        if(value == null)
        {
            throw new QMException("Ҫ���������ֵΪ�ա�");
        }
        if(!(value instanceof Comparable))
        {
            throw new QMException("�������㷨��ʱֻ֧��Comparable����");
        }
        return (Comparable)value;
    }

    /**
     *���ò����������㷨��������Ϣ������bsoid����������������
     *@param infos Set ָ�������bsoid��BaseValueIfc�����͵ļ���
     *@return Collection Object[]���ϣ�<br> ָ�������bsoid,��ţ�BaseValueIfc������Ԫ�ء�
     * @throws QMException 
     */
    public static Collection sortedInfos(Set infos) throws QMException
    {
        Object[] valueInfos = infos.toArray();
        return sortedInfos(valueInfos);
    }

    /**
     * �Ը���ֵ����������򡣰�bsoid��������
     * @param infos Collection Object[]��������ֵ���� BaseValueIfc���ͣ�
     * @return Collection Object[]��������ֵ���󼯺ϣ� BaseValueIfc���ͣ�
     * @throws QMException 
     */
    public static Collection sortedInfos(Collection infos) throws QMException
    {
        Object[] valueInfos = infos.toArray();
        return sortedInfos(valueInfos);
    }

    /**
     * @param infos Collection Ҫ�����ֵ���󼯺ϣ� BaseValueIfc���ͣ�
     * @param methodName String ����ĳ�����ķ���ֵ���� ��:getRouteListNumber
     * @param flag boolean
     * @return Collection Object[]�������Ľ������ BaseValueIfc���ͣ�
     * @throws QMException 
     */
    public static Collection sortedInfos(Collection infos, String methodName, boolean flag) throws QMException
    {
        Object[] valueInfos = infos.toArray();
        return sortedInfos(valueInfos, methodName, flag);
    }

    /**
     * ����.�汾���������С�
     * @param listVec Collection TechnicsRouteListIfc·�߱�ֵ���󼯺�
     * @return TechnicsRouteListIfc[] ���TechnicsRouteListIfc��·�߱�ֵ����
     * @throws QMException 
     */
    public static TechnicsRouteListIfc[] sortedIterate(Collection listVec) throws QMException
    {
        Collection listinfos = sortedInfos(listVec);
        TechnicsRouteListIfc[] routeListInfo = new TechnicsRouteListIfc[listinfos.size()];
        int i = 0;
        for(Iterator iter = listVec.iterator();iter.hasNext();)
        {
            routeListInfo[i] = (TechnicsRouteListIfc)iter.next();
            i++;
        }
        return routeListInfo;
    }

    /**
     * ����ͨ�����
     * @param property String bso����������
     * @param value String ͨ���
     * @throws QMException
     * @return QueryCondition �μ�handleWildcard(property, value, true);����
     */

    public static QueryCondition handleWildcard(String property, String value)
    {
        return handleWildcard(property, value, true);
    }

    /**
     * ����ͨ����Ǹ�ʽ��
     * @param property String bso��������
     * @param value String ͨ���
     * @param flag boolean boolean true=�ǣ�false=��
     * @throws QMException
     * @return QueryCondition
     */

    public static QueryCondition handleWildcard(String property, String value, boolean flag)
    {
        QueryCondition cond = null;
        //������'*'��'%';
        if(value.indexOf("*") == -1 && value.indexOf("%") == -1)
        {
            if(flag)
            {
                cond = new QueryCondition(property, QueryCondition.EQUAL, value);
            }else
            {
                cond = new QueryCondition(property, QueryCondition.NOT_EQUAL, value);
            }
        }else
        {
            if(value.indexOf("*") != -1)
            {
                value = value.replace('*', '%');
            }
            if(flag)
            {
                cond = new QueryCondition(property, QueryCondition.LIKE, value);
            }else
            {
                cond = new QueryCondition(property, QueryCondition.NOT_LIKE, value);
            }
        }
        return cond;
    }

    /**
     * ����ʱ�䡣֧�ֵ�Сʱ��
     * @param query QMQuery
     * @param time String String ֧�ָ�ʽ����ȷ��Сʱ��������ʱ�䷶Χ����"2004", "2003-2004", "2003-2004/4", "2004/04", "2004/4", "2004/4-2004/3" "2004/4/13", "2004/4/13 12", "2004/4 12-2004/4/13 18".
     * @param timeType String String "createTime" or "modifyTime".
     * @throws QMException 
     */
    public static void handleTimeQuery(QMQuery query, String time, String timeType) throws QMException
    {
        String beginString = null;
        String endString = null;
        int index = time.indexOf("-");
        if(index > 0)
        {
            beginString = time.substring(0, index);
            endString = time.substring(index + 1).trim();
        }else
        {
            beginString = time;
            endString = time;
        }
        //��ϳ������ȷ�ĸ�ʽ��
        //��ʼʱ�䡣
        String begin = getValidTime(beginString.replace('/', '-').trim(), true);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter routeService's handleTimeQuery : begin = " + begin);
            //��ֹʱ�䡣
        }
        String end = getValidTime(endString.replace('/', '-').trim(), false);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter routeService's handleTimeQuery : end = " + end);
        }
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.TIME + "findRouteLists " + timeType + "'s begin = " + begin + ", end= " + end);
        }
        Timestamp beginTime = Timestamp.valueOf(begin);
        Timestamp endTime = Timestamp.valueOf(end);
        QueryCondition cond1 = new QueryCondition(timeType, QueryCondition.GREATER_THAN_OR_EQUAL, beginTime);
        query.addCondition(cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition(timeType, QueryCondition.LESS_THAN, endTime);
        query.addCondition(cond2);
        query.addAND();
    }

    /**
     * �����Чʱ��
     * @param time String String ֧�ָ�ʽ����ȷ��Сʱ��������ʱ�䷶Χ����"2004", "2003-2004", "2003-2004/4", "2004/04", "2004/4", "2004/4-2004/3" "2004/4/13", "2004/4/13 12", "2004/4 12-2004/4/13 18".
     * @param flag boolean flag=true,��ʼʱ�䣻flag = false, ��ֹʱ�䡣
     * @throws QMException
     * @return String ��Ч��ʱ��
     * @throws QMException 
     */
    private static String getValidTime(String time, boolean flag) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.TIME + "enter routeService's getValidTime : time = " + time + ", flag = " + flag);
        }
        String convert = null;
        String day_begin_time = " 00:00:00.000";
        String day_time = ":00:00.000";
        String day_time_end = ":60:00.000";
        String day_end_time = " 24:00:00.000";
        StringTokenizer toke = new StringTokenizer(time, "-");
        int i = toke.countTokens() - 1;
        if(time.indexOf(" ") != -1)
        {
            if(flag)
            {
                convert = time + day_time;
            }else
            {
                convert = time + day_time_end;
            }
        }else if(i == 2)
        {
            if(flag)
            {
                convert = time + day_begin_time;
            }else
            {
                convert = time + day_end_time;
            }
        }else if(i == 1)
        {
            String s1 = "-1";
            String s2 = null;
            if(flag)
            {
                convert = time + s1 + day_begin_time;
            }else
            {
                String s3 = time.substring(time.indexOf("-") + 1).trim();
                int k = s3.indexOf("0");
                if(k == 0)
                {
                    s3 = s3.substring(1);
                }
                if(TechnicsRouteServiceEJB.VERBOSE)
                {
                    System.out.println("enter routeService's getValidTime �·�s3 = " + s3);
                }
                if(s3.trim().equals("1") || s3.trim().equals("3") || s3.trim().equals("5") || s3.trim().equals("7") || s3.trim().equals("8") || s3.trim().equals("10") || s3.trim().equals("12"))
                {
                    s2 = "-31";
                }else if(s3.trim().equals("2"))
                {
                    String year = time.substring(0, time.indexOf("-"));
                    int b = Integer.parseInt(year);
                    int m = b % 4;
                    //�ж����ꡣ
                    if(m == 0)
                    {
                        s2 = "-29";
                    }else
                    {
                        s2 = "-28";
                    }
                }else
                {
                    s2 = "-30";
                }
                convert = time + s2 + day_end_time;
            }
        }else if(i == 0)
        {
            String s1 = "-1-1";
            String s2 = "-12-31";
            if(flag)
            {
                convert = time + s1 + day_begin_time;
            }else
            {
                convert = time + s2 + day_end_time;
            }
        }else
        {
            throw new QMException("����ʱ��������������⡣");
        }
        return convert;
    }
    
    
    /**
     * �Ƚ���Ҫ·�ߺʹ�Ҫ·����Ϣ�Ƿ�仯
     * 20120120 xucy add
     */
    public static boolean compareRouteStr(String str1, String str2)
    {
    	if(str1 == null && str2 == null)
    	{
    		return true;
    	}
    	else if(str1 == null && str2 != null)
    	{
    		return false;
    	}
    	else if(str1 != null && !"".equals(str1) && str2 == null)
    	{
    		return false;
    	}
    	else if(str1 == null && str2 != null && !"".equals(str2))
        {
            return false;
        }else if("".equals(str1) && str2 == null)
        {
            return true;
        }
    	else 
    	{
    		return str1.equals(str2);
    	}
    }
    
    
    /**
     * ��ȡ�������·�ߴ���Ϣ
     * @param routeBranchStr
     * @return
     */
    public static String getRouteBranchStr(String routeBranchStr)
    {
        if(routeBranchStr != null && !routeBranchStr.equals(""))
        {
            if(routeBranchStr.startsWith("-") || routeBranchStr.startsWith("="))
            {
                routeBranchStr = routeBranchStr.substring(1);
            }else if(routeBranchStr.lastIndexOf("-") == routeBranchStr.length() - 1 || routeBranchStr.lastIndexOf("=") == routeBranchStr.length() - 1)
            {
                routeBranchStr = routeBranchStr.substring(0, routeBranchStr.length() - 1);
            }else if(routeBranchStr.startsWith("-") && routeBranchStr.lastIndexOf("=") == routeBranchStr.length() - 1)
            {
                routeBranchStr = routeBranchStr.substring(1, routeBranchStr.length() - 1);
            }else if(routeBranchStr.startsWith("=") && routeBranchStr.lastIndexOf("-") == routeBranchStr.length() - 1)
            {
                routeBranchStr = routeBranchStr.substring(1, routeBranchStr.length() - 1);
            }
        }
        else
        {
            return null;
        }
        return routeBranchStr;
    }  
    
}
