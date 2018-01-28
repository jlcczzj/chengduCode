/**
 * 生成程序 RouteHelper.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * 工艺路线服务端类 提供了排序算法,通配符的处理还有输入时间的检验等方法
 * @author 赵立彬
 * @version 1.0
 */
public class RouteHelper
{
    private static final String ROUTE_SERVICE = "consTechnicsRouteService";

    /**
     * 构造函数
     */
    private RouteHelper()
    {}

    /**
     * 刷新值对象
     * @param bsoid String 指定的值对象
     * @throws QMException
     * @return BaseValueIfc 刷新后的值对象
     */
    public static BaseValueIfc refreshInfo(String bsoid) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        BaseValueIfc valueInfo = pservice.refreshInfo(bsoid);
        return valueInfo;
    }

    /**
     * 从枚举数组里取出display所存储的值
     * @param type EnumeratedType[] 枚举数组
     * @param display String
     * @return String 枚举数组里取出display所存储的值
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
     * 获得路线服务
     * @throws QMException
     * @return TechnicsRouteService 服务名
     */
    public static TechnicsRouteService getRouteService() throws QMException
    {
        return (TechnicsRouteService)EJBServiceHelper.getService(ROUTE_SERVICE);
    }

    /**
     * 处理带"*"的字符串
     * @param s String 要处理的字符串
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
     * 排序算法。根据bsoid进行排序。升序排列
     * @param valueInfos Object[] 指定排序的bsoid（BaseValueIfc）类型的集合
     * @return Collection Object[]类型集合：valueInfos[i]排序后的bsoid的集合
     * @throws QMException 
     */
    private static Collection sortedInfos(Object[] valueInfos) throws QMException
    {
        //排序
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
                    throw new QMException("此排序只支持BaseValueIfc");
                }
                //由于bsoid号是升序，按bsoid确定版序。（可排出同一版本的原本和副本的顺序。）
                if(((BaseValueIfc)valueInfos[i]).getBsoID().compareTo(((BaseValueIfc)valueInfos[i + 1]).getBsoID()) > 0)
                {
                    //进行替换
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
     * 排序算法。根据给定方法名进行排序。
     * @param valueInfos Object[] 指定的值对象集合（BaseValueIfc类型）
     * @param methodName String 根据某方法的返回值排序 例:getRouteListNumber
     * @param flag boolean boolean true, 降序; false, 升序。
     * @return Collection vector集合：<br> 存放了一个Object[] ：排序后的BaseValueIfc结果集
     * @throws QMException 
     */
    private static Collection sortedInfos(Object[] valueInfos, String methodName, boolean flag) throws QMException
    {
        //排序
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
                    throw new QMException("此排序只支持BaseValueIfc");
                }
                //由于bsoid号是升序，按bsoid确定版序。（可排出同一版本的原本和副本的顺序。）
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
                //降序
                if(flag)
                {
                    if(value1.compareTo(value2) < 0)
                    {
                        //进行替换
                        temp = valueInfos[i];
                        valueInfos[i] = valueInfos[i + 1];
                        valueInfos[i + 1] = temp;
                    }
                }
                //升序
                else
                {
                    if(value1.compareTo(value2) > 0)
                    {
                        //进行替换
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
     * 对属性值排序
     * @param info BaseValueIfc
     * @param methodName String 方法名
     * @return Comparable 将BaseValueIfc造型为Comparable并排序后的属性值
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
            throw new QMException("要排序的属性值为空。");
        }
        if(!(value instanceof Comparable))
        {
            throw new QMException("此排序算法暂时只支持Comparable类型");
        }
        return (Comparable)value;
    }

    /**
     *调用并返回排序算法排序后的信息。根据bsoid进行排序。升序排列
     *@param infos Set 指定排序的bsoid（BaseValueIfc）类型的集合
     *@return Collection Object[]集合：<br> 指定排序的bsoid,存放（BaseValueIfc）类型元素。
     * @throws QMException 
     */
    public static Collection sortedInfos(Set infos) throws QMException
    {
        Object[] valueInfos = infos.toArray();
        return sortedInfos(valueInfos);
    }

    /**
     * 对给定值对象进行排序。按bsoid进行排序。
     * @param infos Collection Object[]：给定的值对象（ BaseValueIfc类型）
     * @return Collection Object[]：排序后的值对象集合（ BaseValueIfc类型）
     * @throws QMException 
     */
    public static Collection sortedInfos(Collection infos) throws QMException
    {
        Object[] valueInfos = infos.toArray();
        return sortedInfos(valueInfos);
    }

    /**
     * @param infos Collection 要排序的值对象集合（ BaseValueIfc类型）
     * @param methodName String 根据某方法的返回值排序 例:getRouteListNumber
     * @param flag boolean
     * @return Collection Object[]：排序后的结果集（ BaseValueIfc类型）
     * @throws QMException 
     */
    public static Collection sortedInfos(Collection infos, String methodName, boolean flag) throws QMException
    {
        Object[] valueInfos = infos.toArray();
        return sortedInfos(valueInfos, methodName, flag);
    }

    /**
     * 排序.版本号升序排列。
     * @param listVec Collection TechnicsRouteListIfc路线表值对象集合
     * @return TechnicsRouteListIfc[] 存放TechnicsRouteListIfc（路线表值对象）
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
     * 处理通配符。
     * @param property String bso的属性名。
     * @param value String 通配符
     * @throws QMException
     * @return QueryCondition 参见handleWildcard(property, value, true);方法
     */

    public static QueryCondition handleWildcard(String property, String value)
    {
        return handleWildcard(property, value, true);
    }

    /**
     * 处理通配符非格式。
     * @param property String bso的属性名
     * @param value String 通配符
     * @param flag boolean boolean true=是，false=非
     * @throws QMException
     * @return QueryCondition
     */

    public static QueryCondition handleWildcard(String property, String value, boolean flag)
    {
        QueryCondition cond = null;
        //不包含'*'和'%';
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
     * 设置时间。支持到小时。
     * @param query QMQuery
     * @param time String String 支持格式（精确到小时，可输入时间范围）："2004", "2003-2004", "2003-2004/4", "2004/04", "2004/4", "2004/4-2004/3" "2004/4/13", "2004/4/13 12", "2004/4 12-2004/4/13 18".
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
        //组合出最后正确的格式。
        //起始时间。
        String begin = getValidTime(beginString.replace('/', '-').trim(), true);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter routeService's handleTimeQuery : begin = " + begin);
            //终止时间。
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
     * 获得有效时间
     * @param time String String 支持格式（精确到小时，可输入时间范围）："2004", "2003-2004", "2003-2004/4", "2004/04", "2004/4", "2004/4-2004/3" "2004/4/13", "2004/4/13 12", "2004/4 12-2004/4/13 18".
     * @param flag boolean flag=true,起始时间；flag = false, 终止时间。
     * @throws QMException
     * @return String 有效的时间
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
                    System.out.println("enter routeService's getValidTime 月份s3 = " + s3);
                }
                if(s3.trim().equals("1") || s3.trim().equals("3") || s3.trim().equals("5") || s3.trim().equals("7") || s3.trim().equals("8") || s3.trim().equals("10") || s3.trim().equals("12"))
                {
                    s2 = "-31";
                }else if(s3.trim().equals("2"))
                {
                    String year = time.substring(0, time.indexOf("-"));
                    int b = Integer.parseInt(year);
                    int m = b % 4;
                    //判断闰年。
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
            throw new QMException("输入时间错误或解析有问题。");
        }
        return convert;
    }
    
    
    /**
     * 比较主要路线和次要路线信息是否变化
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
     * 获取解析后的路线串信息
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
