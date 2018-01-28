/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.service;

import java.util.Collection;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.technics.consroute.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.technics.consroute.util.RouteAdoptedType;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;
import java.util.HashMap;

/**
 * （问题一）2006 07 12  zz 周茁添加 性能优化 ，同时修改了TechnicsRouteServiceEJB

 */
/**
 * 提供版本，版序比较
 * @author 赵立彬
 * @version 1.0
 */
public class CompareHandler
{
    public static final String CHECKOUT = "c/o";
    public static final String WRK = "wrk";

    private CompareHandler()
    {}

    /**
     * 提供版序的比较。
     * @param iteratedVec Collection 路线表值对象。
     * @throws QMException
     * @return Map key: partMasterID, value : Collection:listRoutePartLinkIfc集合。集合顺序，版本号升序排列。
     * @throws QMException 
     */
    protected static Map compareIterate(Collection listVec) throws QMException
    {
        Map hashmap = new TreeMap();
        //对集合进行排序。顺序：版本号由小到大。由于bsoid号是升序，按bsoid确定版序。（可排出同一版本的原本和副本的顺序。）
        TechnicsRouteListIfc[] listInfo = RouteHelper.sortedIterate(listVec);
        if(listInfo.length < 2)
        {
            throw new QMException("给定集合至少包含两个元素。");
        }
        //如果是两个值对象的比较，进行特殊处理，速度较快。
        if(listInfo.length == 2)
        {
            Object[] obj = compareIterate(listInfo[0], listInfo[1]);
            Collection addVec = (Collection)obj[0];
            Map updateMap = (Map)obj[1];
            Collection delVec = (Collection)obj[2];
            for(Iterator iter = addVec.iterator();iter.hasNext();)
            {
                ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
                Collection linkVec = new Vector();
                linkVec.add(null);
                linkVec.add(linkInfo);
                hashmap.put(linkInfo.getPartMasterID() + ";" + linkInfo.getParentPartNum(), linkVec);
            }
            for(Iterator iter = updateMap.keySet().iterator();iter.hasNext();)
            {
                ListRoutePartLinkIfc linkInfo1 = (ListRoutePartLinkIfc)iter.next();
                ListRoutePartLinkIfc linkInfo2 = (ListRoutePartLinkIfc)updateMap.get(linkInfo1);
                Collection linkVec = new Vector();
                linkVec.add(linkInfo1);
                linkVec.add(linkInfo2);
                hashmap.put(linkInfo1.getPartMasterID() + ";" + linkInfo1.getParentPartNum(), linkVec);
            }
            for(Iterator iter = delVec.iterator();iter.hasNext();)
            {
                ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
                Collection linkVec = new Vector();
                linkVec.add(linkInfo);
                linkVec.add(null);
                hashmap.put(linkInfo.getPartMasterID() + ";" + linkInfo.getParentPartNum(), linkVec);
            }
        }
        //多个值对象的比较。
        else
        {
            Vector allPartVec = new Vector();
            for(int i = 0;i < listInfo.length;i++)
            {
                TechnicsRouteListIfc list = listInfo[i];
                for(int j = i + 1;j < listInfo.length;j++)
                {
                    Collection partVec = getPartMaster(list, listInfo[j]);
                    for(Iterator iter = partVec.iterator();iter.hasNext();)
                    {
                        ListRoutePartLinkIfc link = (ListRoutePartLinkIfc)iter.next();
                        if(!isContains(allPartVec, link))
                        {
                            allPartVec.add(link);
                        }
                    }
                }
            }
            handleIterateResult(allPartVec, listInfo, hashmap);
        }
        return hashmap;
    }

    /**
     * 设置HashMap key : partMasterID, value : Collection:listRoutePartLinkIfc集合。集合顺序，版本号升序排列。
     * @param allPartVec Collection 所有零件集合
     * @param listInfo TechnicsRouteListIfc[] 此数组有顺序。
     * @param hashmap Map key:partMasterID + ";" + partNumber value: vector:ListRoutePartLinkIf 的集合
     * @throws QMException 
     */
    private static void handleIterateResult(Collection allPartVec, TechnicsRouteListIfc[] listInfo, Map hashmap) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter the class:CompareHandler,method:handleIterateResult:119行");
            System.out.println("所有要对比的零件" + allPartVec);
            System.out.println("版本的数量" + listInfo.length);
        }
        for(Iterator iter = allPartVec.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkinfo = (ListRoutePartLinkIfc)iter.next();
            String partMasterID = linkinfo.getPartMasterID();
            String partNumber = linkinfo.getParentPartNum();
            Collection linkVec = new Vector();
            for(int i = 0;i < listInfo.length;i++)
            {
                ListRoutePartLinkIfc linkInfo = RouteHelper.getRouteService().getListRoutePartLink(listInfo[i].getBsoID(), partMasterID, partNumber);
                linkVec.add(linkInfo);
            }
            hashmap.put(partMasterID + ";" + partNumber, linkVec);
        }
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("最后的结果" + hashmap);
            System.out.println("exit the class:CompareHandler");
        }
    }

    /**
     * 获得有差异的关联中的partMasterID.
     * @param iterated1 TechnicsRouteListIfc 低级版序 例：A.1
     * @param iterated2 TechnicsRouteListIfc 高级版序 例：A.4
     * @throws QMException
     * @return Collection partVec包含:addVec:增加的零件集合,updateMap:更改零件的 路线（包括删除的路线） updateMap: key : A.1对应的更改关联，value : A.4对应的更改关联; delVec:删除的零件Collection。
     * @throws QMException 
     */
    private static Collection getPartMaster(TechnicsRouteListIfc iterated1, TechnicsRouteListIfc iterated2) throws QMException
    {
        Collection partVec = new Vector();
        Object[] obj = compareIterate(iterated1, iterated2);
        Collection addVec = (Collection)obj[0];
        Map updateMap = (Map)obj[1];
        Collection delVec = (Collection)obj[2];
        for(Iterator iter = addVec.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            partVec.add(linkInfo);
        }
        for(Iterator iter = updateMap.keySet().iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            partVec.add(linkInfo);
        }
        for(Iterator iter = delVec.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo = (ListRoutePartLinkIfc)iter.next();
            partVec.add(linkInfo);
        }
        return partVec;
    }

    /**
     * 提供两个版本的比较。版本需要有顺序。[A.1, A.4], [A.5 c/o A.5 wrk]
     * @param iterated1 TechnicsRouteListIfc 低级版序 例：A.1
     * @param iterated2 TechnicsRouteListIfc 高级版序 例：A.4
     * @return Object[] iterated2 增加，更改，删除的关联。obj[0]=增加的零件集合,Collection;obj[1]=更改零件的路线（包括删除的路线）Map, key = A.1对应的更改关联，value = A.4对应的更改关联;obj[2]=删除的零件Collection。
     * @throws QMException 
     */
    private static Object[] compareIterate(TechnicsRouteListIfc iterated1, TechnicsRouteListIfc iterated2) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter routeService's comparteIterate iterated1.bsoid = " + iterated1.getBsoID() + " iterated2.bsoid = " + iterated2.getBsoID());
        }
        String iterateID1 = iterated1.getVersionValue();
        String iterateID2 = iterated2.getVersionValue();
        if(iterateID2.compareTo(iterateID1) < 1)
        {
            if(iterateID2.equals(iterateID1))
            {
                if(!(iterated1.getWorkableState().equals(CHECKOUT)))
                {
                    throw new QMException("给定版本的顺序不对：iterated1的版序： " + iterateID1 + ", iterated2的版序： " + iterateID2);
                }
            }else
            {
                throw new QMException("给定版本的顺序不对：iterated1的版序： " + iterateID1 + ", iterated2的版序： " + iterateID2);
            }
        }
        Object[] obj = new Object[3];
        //获得iterated1的所有关联。
        Collection coll1 = RouteHelper.getRouteService().getRouteListLinkParts(iterated1);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "iterated1的所有关联 : coll1 =" + coll1);
            //获得iterated1到iterated2之间版本增加、更改和删除的路线。
        }
        Collection coll2 = getCreateLink(iterated1, iterated2);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + " iterated1到iterated2之间版本增加、更改和删除的路线。coll2 = " + coll2);
            //获得新增的零件，但尚未创建路线。此时获得的关联有可能是从以前版本继承过来的，需要过滤。
        }
        Collection coll2_1 = getNewLink(iterated2);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "新增的零件，但尚未创建路线。coll2_1 = " + coll2_1);
            //设置增加和更改（包括删除）路线的数组。
        }
        filterCreateAndUpdateLink(coll1, coll2, coll2_1, obj);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "所有的新增零件: coll2 = " + coll2);
            //获得删除的关联。有可能重新建立关联。
        }
        Collection coll3 = getDeleteLink(iterated1, iterated2);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "获得删除的关联，有可能重新建立关联: coll3 = " + coll3);
            //对关联进行过滤。
        }
        filterDeleteLink(coll3, coll2);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "删除的关联去掉重新建立的关联: coll3 = " + coll3);
        }
        obj[2] = coll3;
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "exit routeService's comparteIterate obj[0] = " + obj[0] + ", obj[1].key = " + ((Map)obj[1]).keySet() + "obj[1].values = "
                    + ((Map)obj[1]).values() + ", obj[2] = " + obj[2]);
        }
        return obj;
    }

    /**
     * 关联虽然被删除了，但有可能重新建立关联，此关联已经作为增加或更新的关联有所体现。此方法进行删除关联的过滤。
     * @param coll1 Collection 删除的关联。
     * @param coll2 Collection 增加的关联。
     * @return Collection
     */
    private static void filterDeleteLink(Collection coll1, Collection coll2)
    {
        for(Iterator iter = coll2.iterator();iter.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter.next();
            String number1 = listLinkInfo.getParentPartNum();
            for(Iterator iter1 = coll1.iterator();iter1.hasNext();)
            {
                ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkIfc)iter1.next();
                String number2 = listLinkInfo1.getParentPartNum();
                boolean numEql = false;
                if(number1 == null && number2 == null)
                {
                    numEql = true;
                }else if(number1 != null && number2 != null && number1.equals(number2))
                {
                    numEql = true;

                }
                if(numEql && listLinkInfo1.getPartMasterID().trim().equals(listLinkInfo.getPartMasterID().trim()))
                {
                    //如果一个件被多次删除，有可能出现重复的零件。
                    coll1.remove(listLinkInfo1);
                    /////是否过滤重复的删除零件。暂不进行。。。。。现可以查看出删除过几次。
                }
            }
        }
    }

    /**
     * 更改零件的路线Map, key = 低版本(A.1)对应的更改关联，value = 高版本(A.4)对应的更改关联;
     * @param coll1 Collection iterated1对应的所有关联。
     * @param coll2 Collection　iterated1到iterated2之间版本增或改的关联。
     * @param coll2 Collection　尚未创建路线的关联。
     * @param obj Object[]
     */
    private static void filterCreateAndUpdateLink(Collection coll1, Collection coll2, Collection coll3, Object[] obj)
    {
        ////////////对尚未创建路线的情况作单独处理。///////////////////////////////////////
        //coll3在coll2中过滤。
        for(Iterator iter3 = coll2.iterator();iter3.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter3.next();
            String number1 = listLinkInfo.getParentPartNum();
            for(Iterator iter4 = coll3.iterator();iter4.hasNext();)
            {
                ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkIfc)iter4.next();
                String number2 = listLinkInfo1.getParentPartNum();
                boolean numEql = false;
                if(number1 == null && number2 == null)
                {
                    numEql = true;
                }else if(number1 != null && number2 != null && number1.equals(number2))
                {
                    numEql = true;
                }
                if(numEql && listLinkInfo1.getPartMasterID().trim().equals(listLinkInfo.getPartMasterID().trim()))
                {
                    //删除从iterated1到iterated2之间版本继承的关联。获得新建的关联。（此时关联没有路线）。
                    coll3.remove(listLinkInfo1);
                    break;
                }
            }
        }
        //获得新增的零件，但尚未创建路线。此时获得的关联有可能是从以前版本继承过来的，需要过滤。
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "新增的零件，但尚未创建路线。第一次过滤：coll3 = " + coll3);
            //coll3在coll1中过滤。获得新建的关联（且未创建路线）。
        }
        for(Iterator iter5 = coll1.iterator();iter5.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter5.next();
            String number1 = listLinkInfo.getParentPartNum();
            for(Iterator iter6 = coll3.iterator();iter6.hasNext();)
            {
                ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkIfc)iter6.next();
                String number2 = listLinkInfo1.getParentPartNum();
                boolean numEql = false;
                if(number1 == null && number2 == null)
                {
                    numEql = true;
                }else if(number1 != null && number2 != null && number1.equals(number2))
                {
                    numEql = true;

                }
                if(numEql && listLinkInfo1.getPartMasterID().trim().equals(listLinkInfo.getPartMasterID().trim()))
                {
                    //删除从iterated1继承的关联。获得新建的关联。（此时关联没有路线）。
                    coll3.remove(listLinkInfo1);
                    break;
                }
            }
        }
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "新增的零件，但尚未创建路线的关联的最后结果：coll3 = " + coll3);
            /////////获得路线有变化，添加或更改的关联。///////////////////////////////////////////////
        }
        Map hashmap = new TreeMap();
        for(Iterator iter1 = coll1.iterator();iter1.hasNext();)
        {
            ListRoutePartLinkIfc listLinkInfo = (ListRoutePartLinkIfc)iter1.next();
            String number1 = listLinkInfo.getParentPartNum();
            for(Iterator iter2 = coll2.iterator();iter2.hasNext();)
            {
                ListRoutePartLinkIfc listLinkInfo1 = (ListRoutePartLinkIfc)iter2.next();
                String number2 = listLinkInfo1.getParentPartNum();
                boolean numEql = false;
                if(number1 == null && number2 == null)
                {
                    numEql = true;
                }else if(number1 != null && number2 != null && number1.equals(number2))
                {
                    numEql = true;

                }
                if(numEql && listLinkInfo1.getPartMasterID().trim().equals(listLinkInfo.getPartMasterID().trim()))
                {
                    hashmap.put(listLinkInfo, listLinkInfo1);
                    //删除更改的关联，获得新建的关联。此时关联已有路线。
                    coll2.remove(listLinkInfo1);
                    break;
                }
            }
        }
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "新增的零件，已创建路线的关联：coll2 = " + coll2);
        }
        coll2.addAll(coll3);
        //增加的关联。
        obj[0] = coll2;
        //更改了路线（包括删除）。
        obj[1] = hashmap;
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("exit getCreateAndUpdateLink obj[0]  " + coll2);
        }
    }

    /**
     * 获得iterated2增加、更改和删除的路线。
     * @param iterated1 TechnicsRouteListIfc 低级版序 例：A.1
     * @param iterated2 TechnicsRouteListIfc 高级版序 例：A.4
     * @throws QMException
     * @return Collection
     * @throws QMException 
     */
    private static Collection getCreateLink(TechnicsRouteListIfc iterated1, TechnicsRouteListIfc iterated2) throws QMException
    {
        Collection coll1 = getPredecessorAlter(iterated1, iterated2);
        Collection coll2 = getSelfAlter(iterated2);
        coll1.addAll(coll2);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("exit routeService's getCreateLink coll1= " + coll1);
        }
        return coll1;
    }

    //获得新建且未创建路线的关联。此时获得的关联有可能是从以前版本继承过来的，需要过滤。
    private static Collection getNewLink(TechnicsRouteListIfc iterated2) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TechnicsRouteServiceEJB.LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond1 = new QueryCondition("alterStatus", QueryCondition.EQUAL, TechnicsRouteServiceEJB.INHERIT);
        query.addCondition(cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition(TechnicsRouteServiceEJB.LEFTID, QueryCondition.EQUAL, iterated2.getBsoID());
        query.addCondition(cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition("routeID", QueryCondition.IS_NULL);
        query.addCondition(cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.CANCEL.toString());
        query.addCondition(cond4);
        Collection coll = pservice.findValueInfo(query);
        return coll;
    }

    //因为高版本（不一定为最新版本）的对象的取消状态也可能需要。
    private static Collection getSelfAlter(TechnicsRouteListIfc iterated2) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TechnicsRouteServiceEJB.LIST_ROUTE_PART_LINKBSONAME);
        QueryCondition cond = new QueryCondition("alterStatus", QueryCondition.EQUAL, TechnicsRouteServiceEJB.ROUTEALTER);
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond2 = new QueryCondition(TechnicsRouteServiceEJB.LEFTID, QueryCondition.EQUAL, iterated2.getBsoID());
        query.addCondition(cond2);
        Collection coll = pservice.findValueInfo(query);
        return coll;
    }

    //获得两个版本间的变化。
    private static Collection getPredecessorAlter(TechnicsRouteListIfc iterated1, TechnicsRouteListIfc iterated2) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TechnicsRouteServiceEJB.LIST_ROUTE_PART_LINKBSONAME);
        String iterateID1 = iterated1.getVersionValue();
        String iterateID2 = iterated2.getVersionValue();
        String versionID = iterated2.getVersionID();
        String masterID = iterated2.getMaster().getBsoID();
        QueryCondition cond = new QueryCondition("alterStatus", QueryCondition.EQUAL, TechnicsRouteServiceEJB.ROUTEALTER);
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition("routeListIterationID", QueryCondition.GREATER_THAN, iterateID1);
        query.addCondition(cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("routeListIterationID", QueryCondition.LESS_THAN, iterateID2);
        query.addCondition(cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition("initialUsed", QueryCondition.EQUAL, versionID);
        query.addCondition(cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("routeListMasterID", QueryCondition.EQUAL, masterID);
        query.addCondition(cond4);
        query.addAND();
        QueryCondition cond5 = new QueryCondition("adoptStatus", QueryCondition.EQUAL, RouteAdoptedType.ADOPT.toString());
        query.addCondition(cond5);
        Collection coll = pservice.findValueInfo(query);
        return coll;
    }

    //获得iterated2删除的零件。
    private static Collection getDeleteLink(TechnicsRouteListIfc iterated1, TechnicsRouteListIfc iterated2) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        QMQuery query = new QMQuery(TechnicsRouteServiceEJB.LIST_ROUTE_PART_LINKBSONAME);
        String iterateID1 = iterated1.getVersionValue();
        String iterateID2 = iterated2.getVersionValue();
        String versionID = iterated2.getVersionID();
        String masterID = iterated2.getMaster().getBsoID();
        QueryCondition cond = new QueryCondition("alterStatus", QueryCondition.EQUAL, TechnicsRouteServiceEJB.PARTDELETE);
        query.addCondition(cond);
        query.addAND();
        QueryCondition cond1 = new QueryCondition("routeListIterationID", QueryCondition.GREATER_THAN, iterateID1);
        query.addCondition(cond1);
        query.addAND();
        QueryCondition cond2 = new QueryCondition("routeListIterationID", QueryCondition.LESS_THAN_OR_EQUAL, iterateID2);
        query.addCondition(cond2);
        query.addAND();
        QueryCondition cond3 = new QueryCondition("initialUsed", QueryCondition.EQUAL, versionID);
        query.addCondition(cond3);
        query.addAND();
        QueryCondition cond4 = new QueryCondition("routeListMasterID", QueryCondition.EQUAL, masterID);
        query.addCondition(cond4);
        Collection coll = pservice.findValueInfo(query);
        return coll;
    }

    ///////////////////////////用正常的方式进行版本比较///////
    /**
     * 提供版序的比较。
     * @param iteratedVec Collection
     * @throws QMException
     * @return Map key = partMasterID, value = Collection:listRoutePartLinkIfc集合。集合顺序，版本号升序排列。
     * @throws QMException 
     */
    protected static Map commonCompareIterate(Collection listVec) throws QMException
    {
        Map hashmap = new TreeMap();
        //对集合进行排序。顺序：版本号由小到大。由于bsoid号是升序，按bsoid确定版序。（可排出同一版本的原本和副本的顺序。）
        TechnicsRouteListIfc[] listInfo = RouteHelper.sortedIterate(listVec);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter the class:CompareHandler,method:commonCompareIteratie():版本数" + listInfo.length);
            for(int i = 0;i < listInfo.length;i++)
            {
                System.out.println("" + listInfo[i]);
            }
        }
        if(listInfo.length < 2)
        {
            throw new QMException("给定集合至少包含两个元素。");
        }
        //如果是两个值对象的比较，进行特殊处理，速度较快。
        if(listInfo.length == 2)
        {
            //System.out.println("两个版本的工艺路线表的对比");
            compareCommonTwoIterate(listInfo[0], listInfo[1], hashmap);
        }
        //多个值对象的比较。
        else
        {
            //System.out.println("多个路线表的对比");
            Vector allPartVec = new Vector();
            //（ 问题一）性能优化 查看 版本比较算法有冗余 zz start
            HashMap tempMap = new HashMap();
            //（ 问题一）性能优化 查看 版本比较算法有冗余 zz  end
            for(int i = 0;i < listInfo.length;i++)
            {
                TechnicsRouteListIfc list = listInfo[i];
                for(int j = i + 1;j < listInfo.length;j++)
                {
                    //（ 问题一）性能优化 查看 版本比较算法有冗余 zz start zz start
                    Collection partVec = getCommonPartMaster(list, listInfo[j], tempMap);
                    //zz （ 问题一）性能优化 查看 版本比较算法有冗余 zz start end
                    for(Iterator iter = partVec.iterator();iter.hasNext();)
                    {
                        ListRoutePartLinkIfc linkIfc = (ListRoutePartLinkIfc)iter.next();
                        if(!isContains(allPartVec, linkIfc))
                        {
                            allPartVec.add(linkIfc);
                        }
                    }
                }
            }
            handleIterateResult(allPartVec, listInfo, hashmap);
        }
        // System.out.println(" 版序比较返回的map " + hashmap.size());
        return hashmap;
    }

    private static boolean isContains(Vector vec, ListRoutePartLinkIfc info)
    {

        int count = vec.size();
        if(count == 0)
        {
            return false;
        }
        for(int i = 0;i < count;i++)
        {
            ListRoutePartLinkIfc info1 = (ListRoutePartLinkIfc)vec.elementAt(i);
            if(info1.getPartMasterID().equals(info.getPartMasterID()))
            {
                String number = info.getParentPartNum();
                String number1 = info1.getParentPartNum();
                if(number == null && number1 == null)
                {
                    return true;
                }
                if(number != null && number1 != null && number.equals(number1))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获得有差异的关联中的partMasterID.
     * @param iterated1 TechnicsRouteListIfc
     * @param iterated2 TechnicsRouteListIfc
     * @throws QMException
     * @return Collection
     * @throws QMException 
     */
    private static Collection getCommonPartMaster(TechnicsRouteListIfc iterated1, TechnicsRouteListIfc iterated2, HashMap map) throws QMException
    {
        Collection partVec = new Vector();
        // （ 问题一）性能优化 查看 版本比较算法有冗余 zz start
        compareCommonIterate(iterated1, iterated2, partVec, map);
        // （ 问题一）性能优化 查看 版本比较算法有冗余 zz  end
        return partVec;
    }

    /**
     * 获得有差异的关联中的partMasterID.
     * @param iterated1 TechnicsRouteListIfc
     * @param iterated2 TechnicsRouteListIfc
     * @param partVec Collection
     * @throws QMException
     * @return Collection 被修改 author:skybird 2005.01.05
     * @throws QMException 
     */
    private static Collection compareCommonIterate(TechnicsRouteListIfc iterated1, TechnicsRouteListIfc iterated2, Collection partVec, HashMap map) throws QMException
    {
        //（ 问题一）性能优化 查看 版本比较算法有冗余 zz start
        Collection vec1 = null;
        if(map.get(iterated1.getBsoID()) == null)
        {
            // （问题一）zz 20060712 周茁修改 修改原因：性能优化路线表版本历史比较 start
            //  vec1 = RouteHelper.getRouteService().getRouteListLinkParts(
            // iterated1); 原代码
            vec1 = RouteHelper.getRouteService().getRouteListLinkPartsforVersionCompare(iterated1); // end
            map.put(iterated1.getBsoID(), vec1);
        }else
        {
            vec1 = (Collection)map.get(iterated1.getBsoID());
        }
        Collection vec2 = null;
        if(map.get(iterated2.getBsoID()) == null)
        { // （问题一）zz 20060712 周茁修改 修改原因：性能优化路线表版本历史比较 start
            // vec2 = RouteHelper.getRouteService().getRouteListLinkParts(
            // iterated2); 原代码
            vec2 = RouteHelper.getRouteService().getRouteListLinkPartsforVersionCompare(iterated2); //end
            map.put(iterated2.getBsoID(), vec2);
        }else
        {
            vec2 = (Collection)map.get(iterated2.getBsoID());
        }
        // （ 问题一）性能优化 查看 版本比较算法有冗余 zz end
        for(Iterator iter1 = vec1.iterator();iter1.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo1 = (ListRoutePartLinkIfc)iter1.next();
            String number1 = linkInfo1.getParentPartNum();
            //此标志用于判断listInfo2是否有删除的零件。
            boolean flag = true;
            for(Iterator iter2 = vec2.iterator();iter2.hasNext();)
            {
                ListRoutePartLinkIfc linkInfo2 = (ListRoutePartLinkIfc)iter2.next();
                String number2 = linkInfo2.getParentPartNum();
                boolean numEql = false;
                if(number1 == null && number2 == null)
                {
                    numEql = true;
                }else if(number1 != null && number2 != null && number1.equals(number2))
                {
                    numEql = true;
                }
                if(numEql && linkInfo1.getPartMasterID().equals(linkInfo2.getPartMasterID()))
                {
                    if(linkInfo1.getRouteID() == null)
                    {
                        if(linkInfo2.getRouteID() != null)
                        {
                            partVec.add(linkInfo1);
                        }
                    }else
                    {
                        if(linkInfo2.getRouteID() == null)
                        {
                            partVec.add(linkInfo1);
                        }else if(!(linkInfo1.getRouteID().equals(linkInfo2.getRouteID())))
                        {
                            partVec.add(linkInfo1);
                        }
                    }
                    //else if(!(linkInfo1.getRouteID().equals(linkInfo2.getRouteID())))
                    //{
                    //    partVec.add(linkInfo1.getPartMasterID());
                    //}
                    vec2.remove(linkInfo2);
                    flag = false;
                    break;
                }
            }
            //当vec2中不包含linkInfo1的时候，则把其添加到结果集合中去。
            if(flag)
            {
                partVec.add(linkInfo1);
                /**
                 * //start ListRoutePartLinkIfc linkInfo1 = (ListRoutePartLinkIfc)iter1.next(); //此标志用于判断listInfo2是否有删除的零件。 boolean flag = true; for(Iterator iter2 = vec2.iterator(); iter2.hasNext();
                 * ) { ListRoutePartLinkIfc linkInfo2 = (ListRoutePartLinkIfc)iter2.next(); if(linkInfo1.getPartMasterID().equals(linkInfo2.getPartMasterID())) { if(linkInfo1.getRouteID() == null) {
                 * if(linkInfo2.getRouteID() != null) partVec.add(linkInfo1.getPartMasterID()); } else if(!(linkInfo1.getRouteID().equals(linkInfo2.getRouteID()))) {
                 * partVec.add(linkInfo1.getPartMasterID()); } vec2.remove(linkInfo2); flag = false; break; } } if(flag) partVec.add(linkInfo1.getPartMasterID());
                 **/
                //end

            }
        }

        //listInfo2增加的。
        for(Iterator iter3 = vec2.iterator();iter3.hasNext();)
        {
            ListRoutePartLinkIfc partMaster = (ListRoutePartLinkIfc)iter3.next();
            partVec.add(partMaster);
        }
        return partVec;
    }

    private static void compareCommonTwoIterate(TechnicsRouteListIfc iterated1, TechnicsRouteListIfc iterated2, Map hashmap) throws QMException
    {
        Collection vec1 = RouteHelper.getRouteService().getRouteListLinkParts(iterated1);
        Collection vec2 = RouteHelper.getRouteService().getRouteListLinkParts(iterated2);
        for(Iterator iter1 = vec1.iterator();iter1.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo1 = (ListRoutePartLinkIfc)iter1.next();
            String number1 = linkInfo1.getParentPartNum();
            //此标志用于判断listInfo2是否有删除的零件。
            boolean flag = true;
            for(Iterator iter2 = vec2.iterator();iter2.hasNext();)
            {
                ListRoutePartLinkIfc linkInfo2 = (ListRoutePartLinkIfc)iter2.next();
                String number2 = linkInfo2.getParentPartNum();
                boolean numEql = false;
                if(number1 == null && number2 == null)
                {
                    numEql = true;
                }else if(number1 != null && number2 != null && number1.equals(number2))
                {
                    numEql = true;
                }
                if(numEql && linkInfo1.getPartMasterID().equals(linkInfo2.getPartMasterID()))
                {
                    if(linkInfo1.getRouteID() == null)
                    {
                        if(linkInfo2.getRouteID() != null)
                        {
                            Collection linkUpdateVec = new Vector();
                            linkUpdateVec.add(linkInfo1);
                            linkUpdateVec.add(linkInfo2);
                            hashmap.put(linkInfo1.getPartMasterID() + ";" + number1, linkUpdateVec);
                        }
                    }else if(!(linkInfo1.getRouteID().equals(linkInfo2.getRouteID())))
                    {
                        Collection linkUpdateVec = new Vector();
                        linkUpdateVec.add(linkInfo1);
                        linkUpdateVec.add(linkInfo2);
                        hashmap.put(linkInfo1.getPartMasterID() + ";" + number1, linkUpdateVec);
                    }
                    vec2.remove(linkInfo2);
                    flag = false;
                    break;
                }
            }
            if(flag)
            {
                Collection linkDelVec = new Vector();
                linkDelVec.add(linkInfo1);
                linkDelVec.add(null);
                hashmap.put(linkInfo1.getPartMasterID() + ";" + number1, linkDelVec);
            }
        }
        //listInfo2增加的。
        for(Iterator iter3 = vec2.iterator();iter3.hasNext();)
        {
            ListRoutePartLinkIfc linkAddInfo = (ListRoutePartLinkIfc)iter3.next();
            String partMasterID = linkAddInfo.getPartMasterID();
            Collection linkAddVec = new Vector();
            linkAddVec.add(null);
            linkAddVec.add(linkAddInfo);
            hashmap.put(partMasterID + ";" + linkAddInfo.getParentPartNum(), linkAddVec);
        }
    }

    /////////////////////////版本、版序比较结束。//////////
}
