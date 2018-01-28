/**
 * 生成程序StructurePublisher.java	1.0              2006-11-6
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 比较零件版本大小 刘家坤 2013-09-25
 */
package com.faw_qm.jferp.util;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import com.faw_qm.change.model.QMChangeRequestIfc;
import com.faw_qm.jferp.ejb.service.PromulgateNotifyService;
import com.faw_qm.jferp.exception.QMXMLException;
import com.faw_qm.jferp.model.FilterPartIfc;
import com.faw_qm.jferp.model.FilterPartInfo;
import com.faw_qm.jferp.model.PromulgateNotifyIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.VersionedIfc;

/**
 * <p>Title: 零部件基本信息和结构信息的发布器。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 谢斌
 * @version 1.0
 */
public final class StructurePublisher extends BaseDataPublisher
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(StructurePublisher.class);

    /**
     * 存放标记为修改和重发的零部件对应的filterPart。
     */
    private HashMap updatePartMap = new HashMap();

    /**
     * 缺省构造函数。
     */
    public StructurePublisher()
    {
        super();
    }

    /**
     * 当前只知道通知书，需要根据通知书信息完成数据筛选，并将过滤后零部件结果集合设置给基本发布器，为保存筛选结果做准备。
     * 最后构造数据文件的record元素信息存入到dataList中。
     * @throws QMXMLException 
     */
    protected synchronized final void invoke() throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("invoke(BaseValueIfc) - start"); //$NON-NLS-1$
        }
        filterParts();
        if(logger.isDebugEnabled())
        {
            logger.debug("invoke() - end"); //$NON-NLS-1$
        }
    }

    /**
     * 执行零部件的筛选功能。并将过滤后零部件结果集合设置给基本发布器，为保存筛选结果做准备。
     * 筛选过程中将零部件的信息和结构关联的信息设置到QMXMLPart和QMXMLStructure中，并操作这两个对象完成逻辑。
     * 
     * 筛选结果表记录保存规则：
     * 1.先根据筛选的唯一标识过滤零部件集合，重复的记录被筛选掉，不再发布。
     * 2.零部件号和版本号相同的数据只在筛选结果表中存在一条记录，状态变化，修改其它信息。
     * 
     * 零部件处理规则：查询筛选结果表中数据
     * 1.表中数据不存在，标记为新增N。
     * 2.表中数据存在，检查版本，a版本变化，标记为修改U，对结构进行处理；
     *                         b版本没有变化，1状态变化，标记为重发Z，不处理结构；
     *                                       2状态没有变化，不处理。
     *                                      
     * 结构处理规则，在零部件的版本变化时使用此规则：
     * 1.数据为新增时，结构信息中所有子件发布，结构关系标记为新增N；
     * 2.数据为版本变化时，将数据的子件与表中该数据的版本的子件进行比较：
     *   a新的子零件在原数据中不存在，结构数据标记为新增N；
     *   b新的子零件在原数据中存在，1且使用数量相同，标记为沿用O；
     *                            2且使用数量不同，标记为数量更改U；
     *   c原的子零件在新数据中不存在，标记为取消D。
     * 注：不对结构进行递归处理，只处理第一级结构，递归部分已经在数据提取时完成。
     * @param publishSourseObject 采用通知书。
     * @throws QMXMLException 
     */
    private final void filterParts() throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterParts() - start"); //$NON-NLS-1$
        }
        //通过采用通知书或变更通知书获取关联的零部件。
        List partList = new ArrayList();
        logger
                .debug("publishSourseObject==============="
                        + publishSourseObject);
        try
        {
        	PromulgateNotifyService pnservice=(PromulgateNotifyService)EJBServiceHelper.getService("JFPromulgateNotifyService");
            if(publishSourseObject instanceof PromulgateNotifyIfc)
            {
            	partList=(List)pnservice.getPartsByProId((PromulgateNotifyIfc)publishSourseObject);
            }
            else if(publishSourseObject instanceof QMChangeRequestIfc)
            {
                //返回的结果集是第一个位置为Arraylist变更后的结果,第二个位置变更前的结果。
            	ArrayList[] list = (ArrayList[])pnservice.obtainDataForChange(publishSourseObject);
               logger.debug("list.size()==" + list.length);
                if(list.length > 0)
                    partList = (List) list[0];
            }
            logger.debug("publishSourseObject222222==============="
                    + publishSourseObject);
        }
        catch (QMException e)
        {
            //"通过采用通知书或变更通知书获取关联的零部件失败！"
            logger.error(Messages.getString("Util.22"), e);
            throw new QMXMLException(e);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("partList==" + partList);
        }
        //根据筛选结果表记录保存规则处理零部件。并将筛选后的QMXMLPart集合存入xmlPartList中。
        final List xmlPartList = filterByIdentity(partList);
        if(logger.isDebugEnabled())
        {
            logger.debug("筛选结果表记录保存规则后xmlPartList＝＝" + xmlPartList);
        }
        //根据零部件处理规则处理零部件。并将标记为修改的零部件对应的filterPart放到Map中。
        filterByPartRule(xmlPartList);
        if(logger.isDebugEnabled())
        {
            logger.debug("零部件处理规则后xmlPartList＝＝" + xmlPartList);
            logger.debug("零部件处理规则后updatePartMap＝＝" + updatePartMap.size());
        }
        //根据结构处理规则，处理结构，并将筛选后的QMXMLStructure集合存入xmlStructureList中。
        final List xmlStructureList = filterByStructureRule(xmlPartList);
        if(logger.isDebugEnabled())
        {
            logger.debug("结构处理规则过滤后xmlPartList＝＝" + xmlPartList);
            logger.debug("结构处理规则过滤后xmlStructureList＝＝" + xmlStructureList);
        }
        //将结果数据信息分别存入对应的QMXMLData中，并设置到dataList中。
        setDataRecord(xmlPartList, xmlStructureList);
        //将过滤后零部件结果集合设置给基本发布器，为保存筛选结果做准备。 
        BaseDataPublisher.xmlPartList = xmlPartList;
        if(logger.isDebugEnabled())
        {
            logger.debug("filterParts() - end"); //$NON-NLS-1$
        }
    }

    /**
     * 根据筛选结果表记录保存规则处理零部件。
     * @param partList 待筛选的零部件集合。
     * @return 筛选后的QMXMLPart集合。
     */
    private final List filterByIdentity(final List partList)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByIdentity(List) - start"); //$NON-NLS-1$
        }
        final List xmlPartList = new ArrayList();
        //存放通过唯一标识筛选后的零部件。
        final HashMap tempPartMap = new HashMap();
        for (int i = 0; i < partList.size(); i++)
        {
            final QMPartIfc partIfc = (QMPartIfc) partList.get(i);
            //筛选结果表记录保存规则1。先根据筛选的唯一标识过滤零部件集合，重复的记录被筛选掉，不再发布。
            if(!tempPartMap.containsKey(getPartIdentity(partIfc)))
            {
                tempPartMap.put(getPartIdentity(partIfc), partIfc);
                final QMXMLPart xmlPart = new QMXMLPart(partIfc);
                xmlPartList.add(xmlPart);
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByIdentity(List) - end"); //$NON-NLS-1$
        }
        return xmlPartList;
    }

    /**
     *  获取筛选的唯一标识。
     * @param part 零部件。
     * @return String 筛选的唯一标识。
     */
    private final String getPartIdentity(final QMPartIfc part)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartIdentity(QMPartIfc) - start"); //$NON-NLS-1$
        }
        final String returnString = part.getPartNumber() + part.getVersionID()
                + part.getLifeCycleState().getDisplay();
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartIdentity(QMPartIfc) - end"); //$NON-NLS-1$
        }
        return returnString;
    }

    /**
     * 根据零部件处理规则处理零部件。
     * 
     * 零部件处理规则：查询筛选结果表中数据
     * 1.表中数据不存在，标记为新增N。
     * 2.表中数据存在，检查版本，a版本变化，标记为修改U，对结构进行处理；
     *                         b版本没有变化，1状态变化，标记为重发Z，不处理结构；
     *                                       2状态没有变化，不处理。
     * @param xmlPartList 筛选后的QMXMLPart集合。
     */
    private final void filterByPartRule(final List xmlPartList)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByPartRule(List) - start"); //$NON-NLS-1$
        }
		PartHelper partHelper = new PartHelper();
        final List tempPartList = new ArrayList();
        for (int i = 0; i < xmlPartList.size(); i++)
        {
            FilterPartIfc filterPartIfc = null;
            final QMXMLPart xmlPart = (QMXMLPart) xmlPartList.get(i);
            List filterPartList = new ArrayList();
            try
            {
            	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
                //用编号作为查询条件，查找是否有符合条件的FilterPart。
                final QMQuery query = new QMQuery("FilterPart");
                final QueryCondition condition = new QueryCondition(
                        "partNumber", "=", xmlPart.getPartNumber());
                query.addCondition(condition);
                filterPartList = (List)pservice.findValueInfo(query);
            }
            catch (QMException e)
            {
                //"查找编号为*的FilterPart时出错！"
                logger.error(Messages.getString("Util.15", new Object[]{xmlPart
                        .getPartNumber()})
                        + e);
                throw new QMXMLException(e);
            }
            if(logger.isDebugEnabled())
            {
                logger.debug("filterPartList==" + filterPartList);
            }
            //比较新数据与filterpart的版本，如果新的版本低于旧的则不再发布。
            FilterPartIfc tempFilterPart = null;
            if(filterPartList != null && filterPartList.size() > 0)
            {
                for (int j = 0; j < filterPartList.size(); j++)
                {
                    filterPartIfc = (FilterPartIfc) filterPartList.get(j);
//  modified by tangjinyu 20120830 出现版本AA，先比较版本长度		    
                    //CCBegin SS1
//                    if(xmlPart.getPartVesionID().length()<filterPartIfc.getVersionValue().length()||
//                    	(xmlPart.getPartVesionID().length()==filterPartIfc.getVersionValue().length()&&
//                    				xmlPart.getPartVesionID().compareTo(
//                            filterPartIfc.getVersionValue()) < 0))
                    int compare = partHelper.compareVersion(xmlPart.getPartVesionID(),filterPartIfc.getVersionValue());
  		          if(compare==2)
                    {//CCEnd SS1
                        tempPartList.add(xmlPart);
                        break;
                    }
                    else
                    {
                        if(tempFilterPart == null)
                        {
                            tempFilterPart = filterPartIfc;
                        }
                        else if(tempFilterPart.getVersionValue().length()<filterPartIfc.getVersionValue().length()||
                        		(tempFilterPart.getVersionValue().length()==filterPartIfc.getVersionValue().length()&&
                        				tempFilterPart.getVersionValue().compareTo(
                                filterPartIfc.getVersionValue()) < 0))
                        {
                            tempFilterPart = filterPartIfc;
                        }
                    }
                }
            }
            if(logger.isDebugEnabled())
            {
                logger.debug("xmlPart.getPartNumber()=="
                        + xmlPart.getPartNumber());
            }
            //零部件处理规则1。表中数据不存在，标记为新增N。
            if(tempFilterPart == null)
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("---------零部件处理规则1");
                }
                if(!tempPartList.contains(xmlPart))
                    xmlPart.setPart_publish_type("N");
            }
            //零部件处理规则2。表中数据存在，检查版本。
            else if(xmlPartList.contains(xmlPart))
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("---------零部件处理规则2");
                }
                //零部件处理规则2.b。版本没有变化。
                if(xmlPart.getPartVesionID().equals(
                        tempFilterPart.getVersionValue()))
                {
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("---------零部件处理规则2.b");
                    }
                    //零部件处理规则2.b.2。状态没有变化，除去。
                    if(xmlPart.getPartLifeCycleState().equals(
                            tempFilterPart.getState()))
                    {
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("---------零部件处理规则2.b.2");
                        }
                        //将未改变的零部件放入临时列表中。
                        tempPartList.add(xmlPart);
                    }
                    //零部件处理规则2.b.1。状态变化，标记为重发Z，不处理结构。
                    else
                    {
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("---------零部件处理规则2.b.1");
                        }
                        updatePartMap.put(xmlPart, tempFilterPart);
                        xmlPart.setPart_publish_type("Z");
                    }
                }
                //零部件处理规则2.a。版本变化，标记为修改U，对结构进行处理。
                else
                {
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("---------零部件处理规则2.a");
                    }
                    updatePartMap.put(xmlPart, tempFilterPart);
                    xmlPart.setPart_publish_type("U");
                }
            }
            if(logger.isDebugEnabled())
            {
                logger.debug("零部件处理规则后xmlPart.getPart_publish_type()＝＝"
                        + xmlPart.getPart_publish_type());
            }
        }
        //将未改变的零部件从列表中除去。
        xmlPartList.removeAll(tempPartList);
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByPartRule(List) - end"); //$NON-NLS-1$
        }
    }

    /**
     * 根据结构处理规则，处理结构，并将筛选后的QMXMLStructure集合存入xmlStructureList中。
     * 
     * 注1：由于在使用结构中，同一级可能存在多个不同数量的相同子件，所以在处理结构前，
     * 要先对该问题进行处理。首先将原结构中的子件数量合并，新结构中的子件则保留原样，
     * 然后原结构中的子件再与新结构中的子件进行比较，
     * 头一个子件的数量按下面的第二条规则处理，其后的所有相同子件都作为新增子件处理。
     * 
     * 结构处理规则，在零部件的版本变化时使用此规则：
     * 1.数据为新增时，结构信息中所有子件发布，结构关系标记为新增N；
     * 2.数据为版本变化时，将数据的子件与表中该数据的版本的子件进行比较：
     *   a新的子零件在原数据中不存在，结构数据标记为新增N；
     *   b新的子零件在原数据中存在，1且使用数量相同，标记为沿用O；
     *                           2且使用数量不同，标记为数量更改U；
     *   c原的子零件在新数据中不存在，标记为取消D。
     * 注2：不对结构进行递归处理，只处理第一级结构，递归部分已经在数据提取时完成。
     * @param xmlPartList 筛选后的QMXMLPart集合。
     * @throws QMXMLException
     */
    private final List filterByStructureRule(final List xmlPartList)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByStructureRule(List, HashMap) - start"); //$NON-NLS-1$
        }
        //根据视图和生命周期状态构造过滤零部件结构用到的配置规范。
        //        final PartConfigSpecIfc partConfigSpecIfc = populateConfigSpec();
        final List xmlStructureList = new ArrayList();
        //临时变量。
        QMXMLPart xmlPart;
        PartUsageLinkIfc newUsageLinkIfc;
        PartUsageLinkIfc oldUsageLinkIfc;
        QMXMLStructure xmlStructure;
        for (int i = 0; i < xmlPartList.size(); i++)
        {
            xmlPart = (QMXMLPart) xmlPartList.get(i);
            //结构处理规则1。数据为新增时，结构信息中所有子件发布，结构关系标记为新增N。
            if(xmlPart.getPart_publish_type().equals("N"))
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("---------结构处理规则1");
                }
                //通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
                //结果存入HashMap集合中，键为PartUsageLinkIfc，值为PartUsageLinkIfc。
                final HashMap usesLinkMap = getUsageLinkMap(xmlPart.getPart());
                final Iterator usesLinkIter = usesLinkMap.values().iterator();
                while (usesLinkIter.hasNext())
                {
                    newUsageLinkIfc = (PartUsageLinkIfc) usesLinkIter.next();
                    xmlStructure = new QMXMLStructure(newUsageLinkIfc);
                    xmlStructure.setStructure_publish_type("N");
                    xmlStructureList.add(xmlStructure);
                }
            }
            //结构处理规则2。数据为版本变化时，将数据的子件与表中该数据的版本的子件进行比较。
            else if(xmlPart.getPart_publish_type().equals("U"))
            {
                if(logger.isDebugEnabled())
                {
                    logger.debug("---------结构处理规则2");
                }
                xmlPart = (QMXMLPart) xmlPartList.get(i);
                //通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
                //结果存入HashMap集合中，键为PartUsageLinkIfc，值为PartUsageLinkIfc。
                final HashMap newUsageLinkMap = getUsageLinkMap(xmlPart
                        .getPart());
                HashMap oldUsageLinkMap = new HashMap();
                //获取旧零部件对应的子件结构关联。                
                //根据filterPart记录的零部件名称和版本号获取最新小版本，
                //通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
                //结果PartUsageLink存入HashMap集合中，键为PartUsageLinkIfc，值为PartUsageLinkIfc。
                final FilterPartIfc filterPartIfc = (FilterPartIfc) updatePartMap
                        .get(xmlPart);
                List partMasterList = new ArrayList();
                try
                {
                	PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
                    final QMQuery query = new QMQuery("QMPartMaster");
                    final QueryCondition condition2 = new QueryCondition(
                            "partNumber", "=", filterPartIfc.getPartNumber());
                    query.addCondition(condition2);
                    //暂时不考虑广义部件的情况。
                    query.setChildQuery(false);
                    partMasterList = (List) pservice.findValueInfo(query);
                }
                catch (QueryException e)
                {
                    //"构造查询条件时出错！"
                    logger.error(Messages.getString("Util.19") + e);
                    throw new QMXMLException(e);
                }
                catch (QMException e)
                {
                    //"获取编号为*的零部件的基本信息时出错！"
                    logger.error(Messages.getString("Util.20",
                            new Object[]{filterPartIfc.getPartNumber()})
                            + e);
                    throw new QMXMLException(e);
                }
                if(partMasterList != null && partMasterList.size() > 0)
                {
                    List versionList = new ArrayList();
                    try
                    {
                    	VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
                    	versionList = (List) vcservice.allVersionsOf((MasteredIfc)partMasterList.get(0));
                    }
                    catch (QMException e)
                    {
                        //"获取编号为*的零部件的小版本对象时出错！"
                        logger.error(Messages.getString("Util.21",
                                new Object[]{filterPartIfc.getPartNumber()})
                                + e);
                        throw new QMXMLException(e);
                    }
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("versionList==" + versionList);
                    }
                    final List tempPartList = versionList;
                    for (int j = 0; j < versionList.size(); j++)
                    {
                        //如果新数据版本号与旧数据的大版本号不一致，除去。
                        if(!((QMPartIfc) versionList.get(j)).getVersionID()
                                .equals(filterPartIfc.getVersionValue()))
                        {
                            tempPartList.remove(versionList.get(j));
                        }
                    }
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("tempPartList==" + tempPartList);
                    }
                    //过滤非最新版本的跌代对象，返回最新的迭代版本对象。
                    QMPartIfc latestPartIfc = filterPartByVersion(tempPartList);
                    if(logger.isDebugEnabled())
                    {
                        logger.debug("latestPartIfc ==" + latestPartIfc);
                    }
                    //通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
                    //结果存入HashMap集合中，键为PartUsageLinkIfc，值为PartUsageLinkIfc。
                    if(latestPartIfc != null)
                    {
                        oldUsageLinkMap = getUsageLinkMap(latestPartIfc);
                    }
                }
                //正式执行结构处理规则。
                //新数据中至少有一个子件。
                if(newUsageLinkMap.size() > 0)
                {
                    //新数据的结构关联数组。
                    Object[] newUsageLinks = newUsageLinkMap.keySet().toArray();
                    //旧数据的结构关联数组。
                    final Object[] oldUsageLinks = oldUsageLinkMap.keySet()
                            .toArray();
                    //在新旧数据都有的子件所对应的旧数据中的结构关联。
                    HashMap updateOldUsageLinkMap = new HashMap();
                    //在新旧数据都有的子件所对应的新数据中的结构关联。
                    final HashMap updateNewUsageLinkMap = new HashMap();
                    for (int j = 0; j < newUsageLinks.length; j++)
                    {
                        newUsageLinkIfc = (PartUsageLinkIfc) newUsageLinks[j];
                        for (int k = 0; k < oldUsageLinks.length; k++)
                        {
                            oldUsageLinkIfc = (PartUsageLinkIfc) oldUsageLinks[k];
                            if(oldUsageLinkIfc.getLeftBsoID().equals(
                                    newUsageLinkIfc.getLeftBsoID()))
                            {
                                updateNewUsageLinkMap.put(newUsageLinkIfc,
                                        newUsageLinkIfc);
                                updateOldUsageLinkMap.put(oldUsageLinkIfc,
                                        oldUsageLinkIfc);
                                //在总的集合中除去，剩下的标识为N。
                                newUsageLinkMap.remove(newUsageLinkIfc);
                                //在总的集合中除去，剩下的标识为D。
                                oldUsageLinkMap.remove(oldUsageLinkIfc);
                            }
                        }
                    }
                    //合并结构中使用子件的数量。键为子件主信息BsoID，值为PartUsageLinkIfc。
                    updateOldUsageLinkMap = uniteQuantity(updateOldUsageLinkMap);
                    newUsageLinks = updateNewUsageLinkMap.keySet().toArray();
                    for (int j = 0; j < newUsageLinks.length; j++)
                    {
                        newUsageLinkIfc = (PartUsageLinkIfc) newUsageLinks[j];
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("newUsageLinkIfc.getQuantity()=="
                                    + newUsageLinkIfc.getQuantity());
                        }
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("---------结构处理规则2.b");
                        }
                        oldUsageLinkIfc = (PartUsageLinkIfc) updateOldUsageLinkMap
                                .get(newUsageLinkIfc.getLeftBsoID());
                        //结构处理规则2.b。新的子零件在原数据中存在。
                        if(oldUsageLinkIfc != null)
                        {
                            //结构处理规则2.b.1。且使用数量相同，标记为沿用O。
                            if(logger.isDebugEnabled())
                            {
                                logger.debug("oldUsageLinkIfc.getQuantity()=="
                                        + oldUsageLinkIfc.getQuantity());
                            }
                            if(newUsageLinkIfc.getQuantity() == oldUsageLinkIfc
                                    .getQuantity())
                            {
                                if(logger.isDebugEnabled())
                                {
                                    logger.debug("---------结构处理规则2.b.1");
                                }
                                xmlStructure = new QMXMLStructure(
                                        newUsageLinkIfc);
                                xmlStructure.setStructure_publish_type("O");
                                xmlStructureList.add(xmlStructure);
                            }
                            //结构处理规则2.b.2。且使用数量不同，标记为数量更改U。
                            else
                            {
                                if(logger.isDebugEnabled())
                                {
                                    logger.debug("---------结构处理规则2.b.2");
                                }
                                xmlStructure = new QMXMLStructure(
                                        newUsageLinkIfc);
                                xmlStructure.setStructure_publish_type("U");
                                xmlStructureList.add(xmlStructure);
                            }
                            //去掉原数据中新旧都有的数据，剩下原数据有但新数据没有的数据。不知所谓！！！
                            updateOldUsageLinkMap.remove(oldUsageLinkIfc
                                    .getLeftBsoID());
                        }
                        //结构处理规则2.a。新的子零件在原数据中不存在，结构数据标记为新增N。
                        else
                        {
                            if(logger.isDebugEnabled())
                            {
                                logger.debug("---------结构处理规则2.a");
                            }
                            xmlStructure = new QMXMLStructure(newUsageLinkIfc);
                            xmlStructure.setStructure_publish_type("N");
                            xmlStructureList.add(xmlStructure);
                        }
                    }
                    //结构处理规则2.c。原的子零件在新数据中不存在，标记为取消D。
                    Iterator iter = oldUsageLinkMap.values().iterator();
                    while (iter.hasNext())
                    {
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("---------结构处理规则2.c");
                        }
                        oldUsageLinkIfc = (PartUsageLinkIfc) iter.next();
                        xmlStructure = new QMXMLStructure(oldUsageLinkIfc);
                        xmlStructure.setStructure_publish_type("D");
                        xmlStructureList.add(xmlStructure);
                    }
                    iter = newUsageLinkMap.values().iterator();
                    while (iter.hasNext())
                    {
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("---------结构处理规则2.a");
                        }
                        newUsageLinkIfc = (PartUsageLinkIfc) iter.next();
                        xmlStructure = new QMXMLStructure(newUsageLinkIfc);
                        xmlStructure.setStructure_publish_type("N");
                        xmlStructureList.add(xmlStructure);
                    }
                }
                //新数据中没有子件。
                else
                {
                    final Iterator iter = oldUsageLinkMap.values().iterator();
                    while (iter.hasNext())
                    {
                        if(logger.isDebugEnabled())
                        {
                            logger.debug("---------结构处理规则2.c");
                        }
                        oldUsageLinkIfc = (PartUsageLinkIfc) iter.next();
                        xmlStructure = new QMXMLStructure(oldUsageLinkIfc);
                        xmlStructure.setStructure_publish_type("D");
                        xmlStructureList.add(xmlStructure);
                    }
                }
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("filterByStructureRule(List, HashMap) - end"); //$NON-NLS-1$
        }
        return xmlStructureList;
    }

    /**
     * 合并结构中使用子件的数量。
     * @param usageLinkMap 待合并数量的使用结构Map，值为PartUsageLinkIfc。
     * @return HashMap 合并后的使用结构Map，键为子件主信息BsoID，值为PartUsageLinkIfc。
     */
    private final HashMap uniteQuantity(HashMap usageLinkMap)
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("uniteQuantity(HashMap) - start"); //$NON-NLS-1$
        }
        //合并后的使用结构关联集合。
        List usageLinkList = new ArrayList();
        Iterator iter = usageLinkMap.values().iterator();
        PartUsageLinkIfc usageLinkIfc = null;
        PartUsageLinkIfc usageLinkIfc2 = null;
        float quantity = 0;
        String leftBsoID = "";
        boolean flag = false;
        while (iter.hasNext())
        {
            usageLinkIfc = (PartUsageLinkIfc) iter.next();
            quantity = usageLinkIfc.getQuantity();
            leftBsoID = usageLinkIfc.getLeftBsoID();
            //标识在集合usageLinkList中是否存在当前被循环的这个零部件,初始情况下,认为不存在:
            flag = false;
            //对已有的合并完毕的集合进行循环:
            for (int i = 0; i < usageLinkList.size(); i++)
            {
                usageLinkIfc2 = (PartUsageLinkIfc) usageLinkList.get(i);
                float oldQuantity = usageLinkIfc2.getQuantity();
                String oldLeftBsoID = usageLinkIfc2.getLeftBsoID();
                //如果使用的是同一个零部件的话,合并数量,
                if(leftBsoID.equals(oldLeftBsoID))
                {
                    usageLinkIfc2.setQuantity(quantity + oldQuantity);
                    //找到了这个零部件:
                    flag = true;
                    usageLinkList.set(i, usageLinkIfc2);
                    break;
                }
            }
            if(!flag)
                usageLinkList.add(usageLinkIfc);
        }
        usageLinkMap = new HashMap();
        //将合并后的结果集合加到Map中，键为子件主信息BsoID，值为PartUsageLinkIfc。
        for (int i = 0; i < usageLinkList.size(); i++)
        {
            usageLinkIfc = (PartUsageLinkIfc) usageLinkList.get(i);
            usageLinkMap.put(usageLinkIfc.getLeftBsoID(), usageLinkIfc);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("uniteQuantity(HashMap) - end"); //$NON-NLS-1$
        }
        return usageLinkMap;
    }

    /**
     * 过滤非最新版本的跌代对象，返回最新的迭代版本对象。
     * @param tempPartList 零部件集合。
     * 如果最后版本不止一个，则根据版本号比较返回最大版本号的版本；
     * 如果结果还是不止一个，则根据迭代号返回最后的版本；
     * 如果迭代号相同，则根据时间戳返回最后的版本。
     * @return 最新版本零部件。
     * @throws QMXMLException 
     */
    private final QMPartIfc filterPartByVersion(final List tempPartList)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("filterPartByVersion(List) - start"); //$NON-NLS-1$
        }
        final HashMap hashMap = new HashMap();
        PartHelper partHelper = new PartHelper();
        String partNumber = "";
        for (int i = 0; i < tempPartList.size(); i++)
        {
            final QMPartIfc partIfc = (QMPartIfc) tempPartList.get(i);
            partNumber = partIfc.getPartNumber();
            final IteratedIfc iteratedIfc = (IteratedIfc) hashMap
                    .get(partNumber);
            boolean flag4 = false;
            if(iteratedIfc == null)
            {
                hashMap.put(partNumber, partIfc);
            }//endif 如果partIfc没被使用，或者被当前用户使用，并且iteratedIfc为空。
            //如果以上情况都不成立，并且partIfc是Versioned的实例，
            //比较iteratedIfc与partIfc的版本序列号。
            else
            {
                flag4 = false;
                if(partIfc instanceof VersionedIfc)
                {
                    final String objVerID = ((VersionedIfc) partIfc)
                            .getVersionID();
                    final String iterateVerID = ((VersionedIfc) iteratedIfc)
                            .getVersionID();
//                  modified by tangjinyu 20120830 出现版本AA，先比较版本长度	  
                    //CCBegin SS1
//                    if(objVerID.length()>iterateVerID.length()||
//                    		(objVerID.length()==iterateVerID.length()&&
//                    				objVerID.compareTo(iterateVerID) > 0))
                    int compare = partHelper.compareVersion(objVerID,iterateVerID);
  		          if(compare==1)
                    {//CCEnd SS1
                        hashMap.put(partNumber, partIfc);
                    }//endif partIfc的版本大于iteratedIfc的版本号
                    else if(objVerID.equals(iterateVerID))
                        flag4 = true;
                }
                if(flag4 || (!(partIfc instanceof VersionedIfc)))
                {
                    //如果flag4为真（iteratedIfc与partIfc序列号相等），
                    //或者partIfc不是Versioned的实例，则比较它们的迭代号。
                    flag4 = false;
                    final String objID = ((IteratedIfc) partIfc)
                            .getIterationID();
                    final String iterateID = iteratedIfc.getIterationID();
                    if(Integer.parseInt(objID) > Integer.parseInt(iterateID))
                    {
                        hashMap.put(partNumber, partIfc);
                    }
                    else if(Integer.parseInt(objID) == Integer
                            .parseInt(iterateID))
                        flag4 = true;
                }
                if(flag4)
                {
                    //如果iteratedIfc与partIfc的迭代号相等，则比较它们的时间戳。
                    flag4 = false;
                    final Timestamp objTime = ((BaseValueIfc) partIfc)
                            .getCreateTime();
                    final Timestamp iterateTime = iteratedIfc.getCreateTime();
                    if(objTime.after(iterateTime))
                    {
                        hashMap.put(partNumber, partIfc);
                    }
                    else if(objTime.equals(iterateTime))
                        flag4 = true;
                }
                if(flag4)
                {
                    try
                    {
                    	VersionControlService vcservice=(VersionControlService)EJBServiceHelper.getService("VersionControlService");
                    	final IteratedIfc objIfc = (IteratedIfc)vcservice.predecessorOf((IteratedIfc)partIfc);
                        if(objIfc != null
                                && PersistHelper.isEquivalent(objIfc,
                                        iteratedIfc))
                        {
                            hashMap.put(partNumber, partIfc);
                        }//endif 如果iteratedIfc与partIfc的时间戳相等并且它们的前驱也是同一个对象。
                    }
                    catch (Exception e)
                    {
                        //"获取编号为*的零部件的前一版本出错！"
                        logger.error(Messages.getString("Util.23",
                                new Object[]{partNumber}), e);
                        throw new QMXMLException(e);
                    }
                }
            }
        }
        final QMPartIfc returnQMPartIfc = (QMPartIfc) hashMap.get(partNumber);
        if(logger.isDebugEnabled())
        {
            logger.debug("filterPartByVersion(List) - end"); //$NON-NLS-1$
        }
        return returnQMPartIfc;
    }

    /**
     * 通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
     * 结果存入HashMap集合中，键为PartUsageLinkIfc，值为PartUsageLinkIfc。
     * @param partIfc 零部件。
     * @throws QMXMLException
     */
    private final HashMap getUsageLinkMap(final QMPartIfc partIfc)
            throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger
                    .debug("getUsageLinkMap(PartConfigSpecIfc, QMPartIfc) - start"); //$NON-NLS-1$
        }
        final HashMap usageLinkMap = new HashMap();
        //通过part查询关联的PartUsageLink，返回一个PartUsageLinkIfc的对象集合。
        List usesPartList = new ArrayList();
        try
        {
        	StandardPartService spservice=(StandardPartService)EJBServiceHelper.getService("StandardPartService");
        	usesPartList = (List)spservice.getUsesPartMasters(partIfc);
        }
        catch (QMException e)
        {
            //"获取名为*的零部件结构时出错！"
            logger.error(Messages.getString("Util.17", new Object[]{partIfc
                    .getPartNumber()})
                    + e);
            throw new QMXMLException(e);
        }
        //将要发布的子关联放到HashMap中。
        Iterator iter = usesPartList.iterator();
        while (iter.hasNext())
        {
            Object obj = iter.next();
            usageLinkMap.put(obj, obj);
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getUsageLinkMap(PartConfigSpecIfc, QMPartIfc) - end"); //$NON-NLS-1$
        }
        return usageLinkMap;
    }

    /**
     * 保存筛选结果。
     * @throws QMXMLException 
     */
    protected final void saveFilterPart() throws QMXMLException
    {
    	try
    	{
        if(logger.isDebugEnabled())
        {
            logger.debug("saveFilterPart(List) - start"); //$NON-NLS-1$
        }
        if(logger.isInfoEnabled())
        {
            //"开始保存FilterPart数据！"
            logger.info(Messages.getString("Util.28")); //$NON-NLS-1$
        }
        PersistService pservice=(PersistService)EJBServiceHelper.getService("PersistService");
        //设置好的要保存的筛选结果的集合。
        List filterPartList = new ArrayList();
        for (int i = 0; i < xmlPartList.size(); i++)
        {
            final QMXMLPart xmlPart = (QMXMLPart) xmlPartList.get(i);
            FilterPartIfc filterPartIfc = (FilterPartIfc) updatePartMap
                    .get(xmlPart);
            if(filterPartIfc != null
                    && xmlPart.getPart_publish_type().equals("Z"))
            {
                //更新状态。
                filterPartIfc.setState(xmlPart.getPartLifeCycleState());
                filterPartList.add(filterPartIfc);
                continue;
            }
            filterPartIfc = new FilterPartInfo();
            if(publishSourseObject instanceof PromulgateNotifyIfc)
            {
                filterPartIfc
                        .setNoticeNumber(((PromulgateNotifyIfc) publishSourseObject)
                                .getPromulgateNotifyNumber());
                filterPartIfc.setNoticeType("采用");
            }
            else if(publishSourseObject instanceof QMChangeRequestIfc)
            {
                filterPartIfc
                        .setNoticeNumber(((QMChangeRequestIfc) publishSourseObject)
                                .getNumber());
                filterPartIfc.setNoticeType("变更");
            }
            filterPartIfc.setPartNumber(xmlPart.getPartNumber());
            filterPartIfc.setState(xmlPart.getPartLifeCycleState());
            filterPartIfc.setVersionValue(xmlPart.getPartVesionID());
            filterPartList.add(filterPartIfc);
            if(logger.isDebugEnabled())
            {
                logger.debug(filterPartIfc.getNoticeNumber());
                logger.debug(filterPartIfc.getNoticeType());
                logger.debug(filterPartIfc.getPartNumber());
                logger.debug(filterPartIfc.getState());
                logger.debug(filterPartIfc.getVersionValue());
            }
        }
        for (int i = 0; i < filterPartList.size(); i++)
        {
            try
            {
            	pservice.saveValueInfo((BaseValueIfc)filterPartList.get(i));
            }
            catch (QMException e)
            {
                //"保存对象*时出错！"
                logger.error(Messages.getString("Util.16",
                        new Object[]{((FilterPartIfc) filterPartList.get(i))
                                .getIdentity()})
                        + e);
                throw new QMXMLException(e);
            }
        }
        //"共保存*条FilterPart数据。"
        logger
                .info(Messages
                        .getString(
                                "Util.44", new Object[]{Integer.toString(filterPartList.size())})); //$NON-NLS-1$
        if(logger.isInfoEnabled())
        {
            //"完成保存FilterPart数据！"
            logger.info(Messages.getString("Util.29")); //$NON-NLS-1$
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("saveFilterPart(List) - end"); //$NON-NLS-1$
        }
    	}
    	catch(Exception ex)
    	{
    		ex.printStackTrace();
    		throw new QMXMLException(ex);
    	}
    }

    /**
     * 将结果数据信息分别存入对应的QMXMLData中，并设置到dataList中。
     * @param xmlPartList 过滤后xmlPartList。
     * @param xmlStructureList 过滤后xmlStructureList。
     * @throws QMXMLException 
     */
    private final void setDataRecord(final List xmlPartList,
            final List xmlStructureList) throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("setDataRecord(List, List) - start"); //$NON-NLS-1$
        }
        for (int j = 0; j < dataList.size(); j++)
        {
            final QMXMLData data = (QMXMLData) dataList.get(j);
            if(logger.isDebugEnabled())
            {
                logger.debug("data.getName==" + data.getName());
            }
            if(data.getName().equals("part"))
            {
                final List partRecordList = new ArrayList();
                for (int i = 0; i < xmlPartList.size(); i++)
                {
                    final QMXMLPart xmlPart = (QMXMLPart) xmlPartList.get(i);
                    xmlPart.setPropertyList(data.getPropertyList());
                    partRecordList.add(xmlPart.getRecord());
                }
                data.setRecordList(partRecordList);
                setDescNote(Messages.getString("Util.34",
                        new Object[]{Integer.toString(partRecordList.size()),
                                data.getName()}));
                continue;
            }
            else if(data.getName().equals("structure"))
            {
                final List structureRecordList = new ArrayList();
                for (int i = 0; i < xmlStructureList.size(); i++)
                {
                    final QMXMLStructure xmlStructure = (QMXMLStructure) xmlStructureList
                            .get(i);
                    xmlStructure.setPropertyList(data.getPropertyList());
                    structureRecordList.add(xmlStructure.getRecord());
                }
                data.setRecordList(structureRecordList);
                setDescNote(Messages.getString("Util.34", new Object[]{
                        Integer.toString(structureRecordList.size()),
                        data.getName()}));
                continue;
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("setDataRecord(List, List) - end"); //$NON-NLS-1$
        }
    }

    public static void main(String[] args)
    {
    }
}
