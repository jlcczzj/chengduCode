/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
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
 * ������һ��2006 07 12  zz ������� �����Ż� ��ͬʱ�޸���TechnicsRouteServiceEJB

 */
/**
 * �ṩ�汾������Ƚ�
 * @author ������
 * @version 1.0
 */
public class CompareHandler
{
    public static final String CHECKOUT = "c/o";
    public static final String WRK = "wrk";

    private CompareHandler()
    {}

    /**
     * �ṩ����ıȽϡ�
     * @param iteratedVec Collection ·�߱�ֵ����
     * @throws QMException
     * @return Map key: partMasterID, value : Collection:listRoutePartLinkIfc���ϡ�����˳�򣬰汾���������С�
     * @throws QMException 
     */
    protected static Map compareIterate(Collection listVec) throws QMException
    {
        Map hashmap = new TreeMap();
        //�Լ��Ͻ�������˳�򣺰汾����С��������bsoid�������򣬰�bsoidȷ�����򡣣����ų�ͬһ�汾��ԭ���͸�����˳�򡣣�
        TechnicsRouteListIfc[] listInfo = RouteHelper.sortedIterate(listVec);
        if(listInfo.length < 2)
        {
            throw new QMException("�����������ٰ�������Ԫ�ء�");
        }
        //���������ֵ����ıȽϣ��������⴦���ٶȽϿ졣
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
        //���ֵ����ıȽϡ�
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
     * ����HashMap key : partMasterID, value : Collection:listRoutePartLinkIfc���ϡ�����˳�򣬰汾���������С�
     * @param allPartVec Collection �����������
     * @param listInfo TechnicsRouteListIfc[] ��������˳��
     * @param hashmap Map key:partMasterID + ";" + partNumber value: vector:ListRoutePartLinkIf �ļ���
     * @throws QMException 
     */
    private static void handleIterateResult(Collection allPartVec, TechnicsRouteListIfc[] listInfo, Map hashmap) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter the class:CompareHandler,method:handleIterateResult:119��");
            System.out.println("����Ҫ�Աȵ����" + allPartVec);
            System.out.println("�汾������" + listInfo.length);
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
            System.out.println("���Ľ��" + hashmap);
            System.out.println("exit the class:CompareHandler");
        }
    }

    /**
     * ����в���Ĺ����е�partMasterID.
     * @param iterated1 TechnicsRouteListIfc �ͼ����� ����A.1
     * @param iterated2 TechnicsRouteListIfc �߼����� ����A.4
     * @throws QMException
     * @return Collection partVec����:addVec:���ӵ��������,updateMap:��������� ·�ߣ�����ɾ����·�ߣ� updateMap: key : A.1��Ӧ�ĸ��Ĺ�����value : A.4��Ӧ�ĸ��Ĺ���; delVec:ɾ�������Collection��
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
     * �ṩ�����汾�ıȽϡ��汾��Ҫ��˳��[A.1, A.4], [A.5 c/o A.5 wrk]
     * @param iterated1 TechnicsRouteListIfc �ͼ����� ����A.1
     * @param iterated2 TechnicsRouteListIfc �߼����� ����A.4
     * @return Object[] iterated2 ���ӣ����ģ�ɾ���Ĺ�����obj[0]=���ӵ��������,Collection;obj[1]=���������·�ߣ�����ɾ����·�ߣ�Map, key = A.1��Ӧ�ĸ��Ĺ�����value = A.4��Ӧ�ĸ��Ĺ���;obj[2]=ɾ�������Collection��
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
                    throw new QMException("�����汾��˳�򲻶ԣ�iterated1�İ��� " + iterateID1 + ", iterated2�İ��� " + iterateID2);
                }
            }else
            {
                throw new QMException("�����汾��˳�򲻶ԣ�iterated1�İ��� " + iterateID1 + ", iterated2�İ��� " + iterateID2);
            }
        }
        Object[] obj = new Object[3];
        //���iterated1�����й�����
        Collection coll1 = RouteHelper.getRouteService().getRouteListLinkParts(iterated1);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "iterated1�����й��� : coll1 =" + coll1);
            //���iterated1��iterated2֮��汾���ӡ����ĺ�ɾ����·�ߡ�
        }
        Collection coll2 = getCreateLink(iterated1, iterated2);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + " iterated1��iterated2֮��汾���ӡ����ĺ�ɾ����·�ߡ�coll2 = " + coll2);
            //������������������δ����·�ߡ���ʱ��õĹ����п����Ǵ���ǰ�汾�̳й����ģ���Ҫ���ˡ�
        }
        Collection coll2_1 = getNewLink(iterated2);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "���������������δ����·�ߡ�coll2_1 = " + coll2_1);
            //�������Ӻ͸��ģ�����ɾ����·�ߵ����顣
        }
        filterCreateAndUpdateLink(coll1, coll2, coll2_1, obj);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "���е��������: coll2 = " + coll2);
            //���ɾ���Ĺ������п������½���������
        }
        Collection coll3 = getDeleteLink(iterated1, iterated2);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "���ɾ���Ĺ������п������½�������: coll3 = " + coll3);
            //�Թ������й��ˡ�
        }
        filterDeleteLink(coll3, coll2);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "ɾ���Ĺ���ȥ�����½����Ĺ���: coll3 = " + coll3);
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
     * ������Ȼ��ɾ���ˣ����п������½����������˹����Ѿ���Ϊ���ӻ���µĹ����������֡��˷�������ɾ�������Ĺ��ˡ�
     * @param coll1 Collection ɾ���Ĺ�����
     * @param coll2 Collection ���ӵĹ�����
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
                    //���һ���������ɾ�����п��ܳ����ظ��������
                    coll1.remove(listLinkInfo1);
                    /////�Ƿ�����ظ���ɾ��������ݲ����С����������ֿ��Բ鿴��ɾ�������Ρ�
                }
            }
        }
    }

    /**
     * ���������·��Map, key = �Ͱ汾(A.1)��Ӧ�ĸ��Ĺ�����value = �߰汾(A.4)��Ӧ�ĸ��Ĺ���;
     * @param coll1 Collection iterated1��Ӧ�����й�����
     * @param coll2 Collection��iterated1��iterated2֮��汾����ĵĹ�����
     * @param coll2 Collection����δ����·�ߵĹ�����
     * @param obj Object[]
     */
    private static void filterCreateAndUpdateLink(Collection coll1, Collection coll2, Collection coll3, Object[] obj)
    {
        ////////////����δ����·�ߵ��������������///////////////////////////////////////
        //coll3��coll2�й��ˡ�
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
                    //ɾ����iterated1��iterated2֮��汾�̳еĹ���������½��Ĺ���������ʱ����û��·�ߣ���
                    coll3.remove(listLinkInfo1);
                    break;
                }
            }
        }
        //������������������δ����·�ߡ���ʱ��õĹ����п����Ǵ���ǰ�汾�̳й����ģ���Ҫ���ˡ�
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "���������������δ����·�ߡ���һ�ι��ˣ�coll3 = " + coll3);
            //coll3��coll1�й��ˡ�����½��Ĺ�������δ����·�ߣ���
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
                    //ɾ����iterated1�̳еĹ���������½��Ĺ���������ʱ����û��·�ߣ���
                    coll3.remove(listLinkInfo1);
                    break;
                }
            }
        }
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "���������������δ����·�ߵĹ������������coll3 = " + coll3);
            /////////���·���б仯����ӻ���ĵĹ�����///////////////////////////////////////////////
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
                    //ɾ�����ĵĹ���������½��Ĺ�������ʱ��������·�ߡ�
                    coll2.remove(listLinkInfo1);
                    break;
                }
            }
        }
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println(TechnicsRouteServiceEJB.VERBOSE + "������������Ѵ���·�ߵĹ�����coll2 = " + coll2);
        }
        coll2.addAll(coll3);
        //���ӵĹ�����
        obj[0] = coll2;
        //������·�ߣ�����ɾ������
        obj[1] = hashmap;
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("exit getCreateAndUpdateLink obj[0]  " + coll2);
        }
    }

    /**
     * ���iterated2���ӡ����ĺ�ɾ����·�ߡ�
     * @param iterated1 TechnicsRouteListIfc �ͼ����� ����A.1
     * @param iterated2 TechnicsRouteListIfc �߼����� ����A.4
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

    //����½���δ����·�ߵĹ�������ʱ��õĹ����п����Ǵ���ǰ�汾�̳й����ģ���Ҫ���ˡ�
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

    //��Ϊ�߰汾����һ��Ϊ���°汾���Ķ����ȡ��״̬Ҳ������Ҫ��
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

    //��������汾��ı仯��
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

    //���iterated2ɾ���������
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

    ///////////////////////////�������ķ�ʽ���а汾�Ƚ�///////
    /**
     * �ṩ����ıȽϡ�
     * @param iteratedVec Collection
     * @throws QMException
     * @return Map key = partMasterID, value = Collection:listRoutePartLinkIfc���ϡ�����˳�򣬰汾���������С�
     * @throws QMException 
     */
    protected static Map commonCompareIterate(Collection listVec) throws QMException
    {
        Map hashmap = new TreeMap();
        //�Լ��Ͻ�������˳�򣺰汾����С��������bsoid�������򣬰�bsoidȷ�����򡣣����ų�ͬһ�汾��ԭ���͸�����˳�򡣣�
        TechnicsRouteListIfc[] listInfo = RouteHelper.sortedIterate(listVec);
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter the class:CompareHandler,method:commonCompareIteratie():�汾��" + listInfo.length);
            for(int i = 0;i < listInfo.length;i++)
            {
                System.out.println("" + listInfo[i]);
            }
        }
        if(listInfo.length < 2)
        {
            throw new QMException("�����������ٰ�������Ԫ�ء�");
        }
        //���������ֵ����ıȽϣ��������⴦���ٶȽϿ졣
        if(listInfo.length == 2)
        {
            //System.out.println("�����汾�Ĺ���·�߱�ĶԱ�");
            compareCommonTwoIterate(listInfo[0], listInfo[1], hashmap);
        }
        //���ֵ����ıȽϡ�
        else
        {
            //System.out.println("���·�߱�ĶԱ�");
            Vector allPartVec = new Vector();
            //�� ����һ�������Ż� �鿴 �汾�Ƚ��㷨������ zz start
            HashMap tempMap = new HashMap();
            //�� ����һ�������Ż� �鿴 �汾�Ƚ��㷨������ zz  end
            for(int i = 0;i < listInfo.length;i++)
            {
                TechnicsRouteListIfc list = listInfo[i];
                for(int j = i + 1;j < listInfo.length;j++)
                {
                    //�� ����һ�������Ż� �鿴 �汾�Ƚ��㷨������ zz start zz start
                    Collection partVec = getCommonPartMaster(list, listInfo[j], tempMap);
                    //zz �� ����һ�������Ż� �鿴 �汾�Ƚ��㷨������ zz start end
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
        // System.out.println(" ����ȽϷ��ص�map " + hashmap.size());
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
     * ����в���Ĺ����е�partMasterID.
     * @param iterated1 TechnicsRouteListIfc
     * @param iterated2 TechnicsRouteListIfc
     * @throws QMException
     * @return Collection
     * @throws QMException 
     */
    private static Collection getCommonPartMaster(TechnicsRouteListIfc iterated1, TechnicsRouteListIfc iterated2, HashMap map) throws QMException
    {
        Collection partVec = new Vector();
        // �� ����һ�������Ż� �鿴 �汾�Ƚ��㷨������ zz start
        compareCommonIterate(iterated1, iterated2, partVec, map);
        // �� ����һ�������Ż� �鿴 �汾�Ƚ��㷨������ zz  end
        return partVec;
    }

    /**
     * ����в���Ĺ����е�partMasterID.
     * @param iterated1 TechnicsRouteListIfc
     * @param iterated2 TechnicsRouteListIfc
     * @param partVec Collection
     * @throws QMException
     * @return Collection ���޸� author:skybird 2005.01.05
     * @throws QMException 
     */
    private static Collection compareCommonIterate(TechnicsRouteListIfc iterated1, TechnicsRouteListIfc iterated2, Collection partVec, HashMap map) throws QMException
    {
        //�� ����һ�������Ż� �鿴 �汾�Ƚ��㷨������ zz start
        Collection vec1 = null;
        if(map.get(iterated1.getBsoID()) == null)
        {
            // ������һ��zz 20060712 �����޸� �޸�ԭ�������Ż�·�߱�汾��ʷ�Ƚ� start
            //  vec1 = RouteHelper.getRouteService().getRouteListLinkParts(
            // iterated1); ԭ����
            vec1 = RouteHelper.getRouteService().getRouteListLinkPartsforVersionCompare(iterated1); // end
            map.put(iterated1.getBsoID(), vec1);
        }else
        {
            vec1 = (Collection)map.get(iterated1.getBsoID());
        }
        Collection vec2 = null;
        if(map.get(iterated2.getBsoID()) == null)
        { // ������һ��zz 20060712 �����޸� �޸�ԭ�������Ż�·�߱�汾��ʷ�Ƚ� start
            // vec2 = RouteHelper.getRouteService().getRouteListLinkParts(
            // iterated2); ԭ����
            vec2 = RouteHelper.getRouteService().getRouteListLinkPartsforVersionCompare(iterated2); //end
            map.put(iterated2.getBsoID(), vec2);
        }else
        {
            vec2 = (Collection)map.get(iterated2.getBsoID());
        }
        // �� ����һ�������Ż� �鿴 �汾�Ƚ��㷨������ zz end
        for(Iterator iter1 = vec1.iterator();iter1.hasNext();)
        {
            ListRoutePartLinkIfc linkInfo1 = (ListRoutePartLinkIfc)iter1.next();
            String number1 = linkInfo1.getParentPartNum();
            //�˱�־�����ж�listInfo2�Ƿ���ɾ���������
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
            //��vec2�в�����linkInfo1��ʱ���������ӵ����������ȥ��
            if(flag)
            {
                partVec.add(linkInfo1);
                /**
                 * //start ListRoutePartLinkIfc linkInfo1 = (ListRoutePartLinkIfc)iter1.next(); //�˱�־�����ж�listInfo2�Ƿ���ɾ��������� boolean flag = true; for(Iterator iter2 = vec2.iterator(); iter2.hasNext();
                 * ) { ListRoutePartLinkIfc linkInfo2 = (ListRoutePartLinkIfc)iter2.next(); if(linkInfo1.getPartMasterID().equals(linkInfo2.getPartMasterID())) { if(linkInfo1.getRouteID() == null) {
                 * if(linkInfo2.getRouteID() != null) partVec.add(linkInfo1.getPartMasterID()); } else if(!(linkInfo1.getRouteID().equals(linkInfo2.getRouteID()))) {
                 * partVec.add(linkInfo1.getPartMasterID()); } vec2.remove(linkInfo2); flag = false; break; } } if(flag) partVec.add(linkInfo1.getPartMasterID());
                 **/
                //end

            }
        }

        //listInfo2���ӵġ�
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
            //�˱�־�����ж�listInfo2�Ƿ���ɾ���������
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
        //listInfo2���ӵġ�
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

    /////////////////////////�汾������ȽϽ�����//////////
}
