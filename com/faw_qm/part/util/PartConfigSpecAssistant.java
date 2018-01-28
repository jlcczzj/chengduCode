/** 生成程序 PartConfigSpecAssistant.java    1.0    2003/02/18
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  * CR1 2009/04/02 谢斌 原因:这些代码运行时是没有意义，所以增加输出判断，屏蔽掉无用逻辑
  *                     方案:屏蔽调试输出，在所有PartDebug.trace前增加：if(StructDebug.verbose)
  *                     备注:解放v3r11-展开零部件树节点性能优化
  * CR2 2009/04/02 谢斌 原因:根据配置规范过滤结果集时，没有使用ViewCache，而是从数据库中读取
  *                     方案:视图从ViewCache中读取
  *                     备注:解放v3r11-被用于产品性能优化
  * SS1 A004-2015-3156 被用于产品出现“值或值类型无效” liunan 2015-6-26
  */

package com.faw_qm.part.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.baseline.model.BaselineIfc;
import com.faw_qm.config.exception.ConfigException;
import com.faw_qm.config.util.ConfigSpec;
import com.faw_qm.config.util.LatestConfigSpec;
import com.faw_qm.config.util.ViewConfigSpec;
import com.faw_qm.eff.model.EffConfigSpecGroupInfo;
import com.faw_qm.eff.model.StringEffIfc;
import com.faw_qm.eff.util.EffConfigSpecGroupAssistant;
import com.faw_qm.eff.util.EffHelper;
import com.faw_qm.effectivity.util.EffectivityType;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.ownership.model.OwnableIfc;
import com.faw_qm.ownership.util.OwnershipHelper;
import com.faw_qm.part.exception.PartException;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.exception.QueryException;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewManageableIfc;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.viewmanage.model.ViewObjectInfo;
import com.faw_qm.viewmanage.util.ViewCache;
import com.faw_qm.viewmanage.util.ViewHelper;
import java.util.Map;
import java.util.HashMap;

/**
  这个类与PartConfigSpecIfc的关系同EffConfigSpecAssistant
  与EffConfigSpecIfc 相同。
  对应的构造函数封装configSpecInfo中同名参数的构造函数；
  对应的普通方法封装configSpecInfo中的同名方法；
  例如：
  setStandard(PartStandardConfigSpec standard)
  {
     configSpecIfc.setStandard(standard);
  }

  PartConfigSpecAssistant(PartStandardConfigSpec standard)
  {
     configSpecIfc = new PartConfigSpec(standard);
  }
 */

/**
 * 零部件配置规范助手类。
 * @author 吴先超
 * @version 1.0
 */

public class PartConfigSpecAssistant implements ConfigSpec
{
    private PartConfigSpecIfc configSpecIfc;
    private String RESOURCE = "com.faw_qm.part.util.PartResource";
    static final long serialVersionUID = 1L;
//  Map viewobj=null;//Begin CR2
//  private ViewCache viewCache=null;//End CR2
    //是否只显示符合配置规范的最新版本的值对象
    private boolean isLatest=true;

    /**
     * 构造函数。
     */
    public PartConfigSpecAssistant()
    {
        super();
    }

    /**
     * 构造函数。含有一个参数:零部件有效性配置规范。
     * @param effectivitycs PartEffectivityConfigSpec
     */
    public PartConfigSpecAssistant(PartEffectivityConfigSpec effectivitycs)
    {
        setEffectivity(effectivitycs);
    }

    /**
     * 构造函数。含有一个参数:零部件标准配置规范。
     * @param standard PartStandardConfigSpec
     */
    public PartConfigSpecAssistant(PartStandardConfigSpec standard)
    {
        setStandard(standard);
    }

    /**
     * 构造函数。含有一个参数:零部件基准线配置规范。
     * @param baselinecs PartBaselineConfigSpec
     */
    public PartConfigSpecAssistant(PartBaselineConfigSpec baselinecs)
    {
        setBaseline(baselinecs);
    }

    /**
     * 构造函数。含有一个参数:零部件配置规范值对象。
     * @param tempIfc PartConfigSpecIfc
     */
    public PartConfigSpecAssistant(PartConfigSpecIfc tempIfc)
    {
        this(tempIfc,true);
    }
    
    /**
     * 构造函数。含有一个参数:零部件配置规范值对象。
     * @param tempIfc PartConfigSpecIfc 配置规范
     * @param isLatest boolean 是否只显示符合配置规范的最新版本的值对象
     */
    public PartConfigSpecAssistant(PartConfigSpecIfc tempIfc,boolean isLatest)
    {
        configSpecIfc = tempIfc;
        this.isLatest=isLatest;
    }

    /**
     * 获得标准配置规范。
     * @return PartStandardConfigSpec
     */
    public PartStandardConfigSpec getStandard()
    {
        return configSpecIfc.getStandard();
    }

    /**
     * 设置标准配置规范。
     * @param standard PartStandardConfigSpec
     */
    public void setStandard(PartStandardConfigSpec standard)
    {
        configSpecIfc.setStandard(standard);
    }

    /**
     * 获得有效性配置规范。
     * @return PartEffectivityConfigSpec
     */
    public PartEffectivityConfigSpec getEffectivity()
    {
        return configSpecIfc.getEffectivity();
    }

    /**
     * 设置有效性配置规范。
     * @param effectivity PartEffectivityConfigSpec
     */
    public void setEffectivity(PartEffectivityConfigSpec effectivity)
    {
        configSpecIfc.setEffectivity(effectivity);
    }

    /**
     * 获得基准线配置规范。
     * @return PartBaselineConfigSpec
     */
    public PartBaselineConfigSpec getBaseline()
    {
        return configSpecIfc.getBaseline();
    }

    /**
     * 设置基准线配置规范。
     * @param baseline PartBaselineConfigSpec
     */
    public void setBaseline(PartBaselineConfigSpec baseline)
    {
        configSpecIfc.setBaseline(baseline);
    }

    /**
     * 获取标准配置是否有效。
     * @return boolean
     */
    public boolean getStandardActive()
    {
        return configSpecIfc.getStandardActive();
    }

    /**
     * 设置标准配置是否有效。
     * @param flag boolean
     */
    public void setStandardActive(boolean flag)
    {
        configSpecIfc.setStandardActive(flag);
    }

    /**
     * 获取有效性配置是否有效。
     * @return boolean
     */
    public boolean getEffectivityActive()
    {
        return configSpecIfc.getEffectivityActive();
    }

    /**
     * 设置有效性配置是否有效。
     * @param flag boolean
     */
    public void setEffectivityActive(boolean flag)
    {
        configSpecIfc.setEffectivityActive(flag);
    }

    /**
     * 基准线配置是否有效。
     * @return boolean
     */
    public boolean getBaselineActive()
    {
        return configSpecIfc.getBaselineActive();
    }

    /**
     * 设置基准线配置是否有效。
     * @param flag boolean
     */
    public void setBaselineActive(boolean flag)
    {
        configSpecIfc.setBaselineActive(flag);
    }

    /**
     * 获取哪个配置规范是有效的。
     * @return Serializable
     */
    public Serializable getActive()
    {
        return configSpecIfc.getActive();
    }

    /**
     * 根据配置规范为查询空间添加查询条件。
     * @exception QMException,QueryException;

     * 添加查询条件：
     * 1，生命周期状态等于用户选定的参数；
     * 2，从属的视图名称等于用户选定的参数或是参数的祖先视图。
     * @param query QMQuery
     * @return QMQuery
     * @throws QMException
     * @throws QueryException
     */
    private QMQuery standard_appendSearchCriteria(QMQuery query)
        throws QMException, QueryException
    {
    	if(PartDebug.verbose)
        PartDebug.trace(this, "standard_appendSearchCriteria() begin ....");
        QMQuery newQuery = (new LatestConfigSpec()).appendSearchCriteria(query);
        PartStandardConfigSpec partStandard = getStandard();
        if (newQuery.getConditionCount() > 0)
            newQuery.addAND();
        if(partStandard.getLifeCycleState() != null)
        {
            QueryCondition condition1 = new QueryCondition("lifeCycleState", "=", partStandard.getLifeCycleState().toString());
            newQuery.addCondition(0, condition1);
            newQuery.addAND();
        }
        newQuery.addLeftParentheses();
        if(partStandard.getViewObjectIfc() != null)
        {
            ViewObjectIfc viewIfc = partStandard.getViewObjectIfc();
            ViewService service = (ViewService)EJBServiceHelper.getService("ViewService");
            while(viewIfc != null)
            {
                QueryCondition condition2 = new QueryCondition("viewName", "=", viewIfc.getViewName());
                newQuery.addCondition(0, condition2);
                newQuery.addOR();
                viewIfc = (ViewObjectIfc) ViewHelper.getViewCache().getParent(viewIfc.getBsoID());//CR2
            }
        }

          QueryCondition condition3 = new QueryCondition("viewName", "IS NULL");
          newQuery.addCondition(0, condition3);

        newQuery.addRightParentheses();
        if(PartDebug.verbose)PartDebug.trace(this, "standard_appendSearchCriteria() end....return is QMQuery");
        return newQuery;
    }

    /**
     * 根据配置规范中包含的信息过滤结果集和。
     * 1，集合元素类型检验：每个元素必须是IteratedIfc接口的子类。
     * 2，循环判断每一个元素，如果元素受视图管理，且为其指定视图，根据master分类成iterated子集，
     * 存入hash表中，masterId是key值，否则，添加到处理结果集当中。
     3，获得hash表，依次取出从属于一个master的iterated对象集合，
        判断每个iterated对象从属的视图，如果是当前指定视图，
        添加到临时结果集当中，循环结束后，判断临时结果集当中是否有元素，
        如果没有，取当前视图的父视图作为当前检验视图，如果没有父视图，操作结束，
        有父视图则重复3操作，若中间结果集中获得匹配的iterated对象，操作结束。
     4，将上一步查询的iterated对象添加到临时结果集中。
     5，调用最新配置规范，处理中间结果后，最终结果返回。
     * 根据配置规范中包含的信息过滤结果集合。
     * @param collection Collection
     * @return Collection
     * @throws QMException
     */
    private Collection standard_process(Collection collection)
        throws QMException
    {
      PartDebug.trace(this, "standard_progress() begin ...." + collection);
     
      ViewCache viewCache =ViewHelper.getViewCache();
//      if(viewobj==null)//CR2 Begin
//       viewobj = new HashMap();
//       if(viewobj.isEmpty())
//      viewobj = viewCache.getViewObjects();//CR2 End
      if(collection == null || collection.isEmpty())
        return collection;
      Hashtable hashtable = new Hashtable();
      Vector vector = new Vector();
      Iterator iterator = collection.iterator();
      while(iterator.hasNext())
      {
        Object obj1 = iterator.next();
        BaseValueIfc obj = null;
        if(obj1 instanceof Object[])
        {
          Object[] temp = (Object[])obj1;
          obj = (BaseValueIfc)temp[0];
        }
        else
        {
          obj = (BaseValueIfc)obj1;
        }
        //CCBegin SS1
        if(obj==null)
        {
        	continue;
        }
        //CCEnd SS1
        if(!(obj instanceof IteratedIfc))
        {
          throw new PartException(RESOURCE, "E03013", null);
        }
        boolean flag0 = getStandard().getWorkingIncluded();
        boolean flag1 = obj instanceof OwnableIfc;
        SessionService sService = (SessionService)EJBServiceHelper.getService("SessionService");
        String curUserID = sService.getCurUserID();
        boolean flag2 = OwnershipHelper.isOwnedBy((OwnableIfc)obj, curUserID);
        //如果包括个人资料夹中的零部件 || 当前对象不受所有者管理 || 当前对象的所有者不是当前用户)进行如下判断：
        if(flag0 || !flag1 || !flag2)
          //如果当前对象受视图管理&&已为当前对象指定视图。
        	if((obj instanceof ViewManageableIfc) && ViewHelper.getView((ViewManageableIfc)obj) != null)//CR2
          {
            String masterID = ((IteratedIfc)obj).getMaster().getBsoID();
            Vector iteratedIfcs = (Vector)hashtable.get(masterID);
            if(iteratedIfcs == null)
              iteratedIfcs = new Vector();
            iteratedIfcs.addElement((IteratedIfc)obj);
            hashtable.put(masterID, iteratedIfcs);
          }
          else
            vector.addElement((IteratedIfc)obj);
      }
      Object obj1;
      //视图与版本相比视图优先，规则如下：
      //1．当本视图中有符合条件的零部件版本时，从本视图中取；
      //2．只有本视图中没有符合条件的零部件时，才向前一个视图中查找；
      //3．如果前一个视图中没有符合条件的零部件时，再向更前面的视图中查找…；
      //4．如果一个视图中有多个版本符合条件时，取其最新版本。
      for(; hashtable.size() > 0; hashtable.remove(obj1))
      {
        Enumeration enumeration = hashtable.keys();
        obj1 = enumeration.nextElement();
        Vector vector3 = (Vector)hashtable.get(obj1);
        Vector vector2 = new Vector();
        for(ViewObjectIfc viewIfc = getStandard().getViewObjectIfc(); viewIfc != null; )
        {
        	if(isLatest&&vector2.size()!=0){
          		break;
          }
          for(int i = 0; i < vector3.size(); i++)
          {
            ViewManageableIfc viewmanageableIfc = (ViewManageableIfc)vector3.get(i);
            if(viewIfc.getViewName().equals(viewmanageableIfc.getViewName()))
              vector2.add(viewmanageableIfc);
          }

          viewIfc = ViewHelper.getViewCache().getParent(viewIfc.getBsoID());//CR2

          //viewIfc = ((ViewService)EJBServiceHelper.getService("ViewService")).getParent((ViewObjectInfo)viewIfc);
        }
        for(int i = 0; i < vector2.size(); i++)
        {
          vector.add(vector2.get(i));
        }
      }
        if(isLatest){
            return (new LatestConfigSpec()).process(vector);
        }
        else{
            return (new PartLatestConfigSpec()).process(vector);
        }
  }


    /**
     * 根据配置规范为查询空间添加查询条件。
     1，向有效性配置规范组添加视图信息；
     2，如果用户已经指定配置项，执行如下操作，否则跳转至；
     3，如果用户输入有效值，判断有效性类型；
     4，如果有效性类型是序列号型，构造有效性配置规范添加到有效性配置规范组当中；
        如果有效性类型是批号型，构造有效性配置规范添加到有效性配置规范组当中；
        如果有效性类型是日期型，转换成Timestamp类型，
        构造有效性配置规范添加到有效性配置规范组当中。跳转至6；
     5，如果用户没有指明有效性值，默认为日期有效性，取当前时间为有效值。
     6，调用有效性服务。
     * 根据配置规范为查询空间添加查询条件。
     * @param query QMQuery
     * @return QMQuery
     * @throws QMException
     * @throws QueryException
     */
    private QMQuery effectivity_appendSearchCriteria(QMQuery query)
        throws QMException, QueryException
    {
    	if(PartDebug.verbose)
        PartDebug.trace(this, "effectivity_appendSearchCriteria() begin ....");
        PartEffectivityConfigSpec partEffConfigSpec = getEffectivity();
        //获取当前日期有什么用处???
        //本来的意思是:如果partEffConfigSpec的effectiveUnit是空的话，就把当前的日期赋值给
        //timestamp，否则的话，还是把effectiveUnit转化为timestamp格式，再赋值给
        //timestamp：

        Timestamp timestamp = partEffConfigSpec.getCurrentDate();
        EffConfigSpecGroupInfo groupInfo = new EffConfigSpecGroupInfo();
        //将当前零件有效性配置规范中包含的视图信息设置到有效性配置规范组当中
        ViewObjectIfc viewIfc1 = partEffConfigSpec.getViewObjectIfc();
        groupInfo.setViewObjectIfc(partEffConfigSpec.getViewObjectIfc());
        //如果能够获得配置项，进行如下检验
        if(partEffConfigSpec.getEffectiveConfigItemIfc() != null)
        {
            String effectiveUnit = partEffConfigSpec.getEffectiveUnit();
            //这里需要增加判断：
            if(partEffConfigSpec.getEffectivityType() != null && effectiveUnit !=null && effectiveUnit.trim() != null && effectiveUnit.trim().length() > 0)
            {
                if(partEffConfigSpec.getEffectivityType().
                    equals(EffectivityType.SERIAL_NUM))
                {
                    groupInfo.add(partEffConfigSpec.buildEffConfigSpecInfo(
                        "QMSerialNumEffectivity", partEffConfigSpec.getEffectiveUnit()));
                }
                if(partEffConfigSpec.getEffectivityType().equals(EffectivityType.LOT_NUM))
                {
                    groupInfo.add(partEffConfigSpec.buildEffConfigSpecInfo(
                        "QMLotEffectivity",partEffConfigSpec.getEffectiveUnit()));
                }
                if(partEffConfigSpec.getEffectivityType().equals(EffectivityType.DATE))
                {
                     timestamp = (Timestamp)EffHelper.valueAsStringToValue(
                         "QMDatedEffectivity",partEffConfigSpec.getEffectiveUnit());
                     groupInfo.add(partEffConfigSpec.buildEffConfigSpecInfo(
                         "QMDatedEffectivity",timestamp));
                }
            }
            //end if(partEffConfigSpec.getEffectiveUnit() !=null)
            else
            {
                if(partEffConfigSpec.getEffectivityType() != null && partEffConfigSpec.getEffectivityType().equals(EffectivityType.DATE))
                {
                     groupInfo.add(partEffConfigSpec.buildEffConfigSpecInfo(
                         "QMDatedEffectivity",timestamp));
                }
            }
        }
        //end if(partEffConfigSpec.getEffectiveConfigItemIfc() != null)
        else
        {
            if(partEffConfigSpec.getEffectiveUnit() != null)
            {
                timestamp = (Timestamp)EffHelper.valueAsStringToValue(
                         "QMDatedEffectivity",partEffConfigSpec.getEffectiveUnit());
                //如果没有effectiveUnit的话，就以当前的日期为准???
                groupInfo.add(partEffConfigSpec.buildEffConfigSpecInfo(
                    "QMDatedEffectivity", timestamp));
            }
            else
            {
                //如果没有effectiveUnit的话，就以当前的日期为准???
                groupInfo.add(partEffConfigSpec.buildEffConfigSpecInfo(
                    "QMDatedEffectivity", timestamp));
            }
        }
        groupInfo.setViewObjectIfc(viewIfc1);
        return (new EffConfigSpecGroupAssistant(groupInfo)).appendSearchCriteria(query);
    }

    /**
     * 根据配置规范中包含的信息过滤结果集和。
     * 如果为有效性配置规范指定视图，使用视图配置规范过滤结果集和，
     * 使用"最新"配置规范过滤结果集和，操作结束。
     * @param collection Collection
     * @return Collection
     * @throws QMException
     */
    private Collection effectivity_process(Collection collection) throws QMException
    {
        PartDebug.trace(this, "effectivity_process() begin ....");
        PartEffectivityConfigSpec partEffConfigSpec = getEffectivity();
        if (partEffConfigSpec.getViewObjectIfc()!=null)
        {
            ConfigSpec viewcs = new ViewConfigSpec(partEffConfigSpec.getViewObjectIfc(), true , isLatest);
            collection = viewcs.process(collection);
        }
        PartDebug.trace(this, "effectivity_progress() end....return is Collection ");
        Collection collection1=null;
        if(isLatest){
            collection1 = (new LatestConfigSpec()).process(collection);
        }
        else{
            collection1=(new PartLatestConfigSpec()).process(collection);
        }
        //还需要对collection1中的元素进行过滤：
        //根据当前的有效性配置规范对象构造一个EffConfigSpecAssistant对象，然后调用其process方法。
        Collection result = effProcess(collection1);
        return result;
    }

    /**
     * 对查询结果作一些附加的处理,只返回那些符合条件的查询结果.
     * 例如判断可迭代的业务对象是否已被分派了所有者或者已被加锁等等.
     * @param collection Collection
     * @return Collection
     * @throws ConfigException
     */
    private Collection effProcess(Collection collection) throws ConfigException
    {
        if(collection != null){
        try
        {
            String effType = getEffectivity().getEffectivityType().toString();
            if(effType.equals(EffectivityType.LOT_NUM.toString()) || effType.equals(EffectivityType.SERIAL_NUM.toString()))
            {
                Vector result = new Vector();
                Iterator iterator = collection.iterator();
                while(iterator.hasNext())
                {
                    Object obj = iterator.next();
                    if(obj instanceof QMPartIfc)
                    {
                          effProcess((QMPartIfc)obj,result);
                    }
                    else if(obj instanceof QMPartMasterIfc)
                    {
                    	result.addElement(obj);

                    }
                    else if(obj instanceof Object[])
                    {
                    	 Object[] objNow = (Object[]) obj;
                         effProcess((QMPartIfc) objNow[0],result);
                    }
                }
                return result;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new ConfigException(ex);
        }
        }
         return collection;
    }

    private void effProcess(QMPartIfc part,Collection result)
        throws Exception{
        String branchID = part.getBranchID();
        QMQuery query = new QMQuery("StringEff");
        QueryCondition condition1 = new QueryCondition("targetBranchID",
              "=", branchID);
        query.addCondition(condition1);
        PersistService pService = (PersistService)
            EJBServiceHelper.getService("PersistService");
        Collection effCollection = pService.findValueInfo(query);
        //int effUnitLength = getEffectivity().getEffectiveUnit().length();
        String configurationItemID = getEffectivity().getEffectiveConfigItemIfc().getBsoID();
        if(effCollection != null && effCollection.size() > 0)
        {
            String  curvalue = getEffectivity().getEffectiveUnit();
            Iterator iterator1 = effCollection.iterator();
            while(iterator1.hasNext())
            {
                StringEffIfc effIfc = (StringEffIfc)iterator1.next();
                //int lengthNow = effIfc.getStart().length();
                String effContextID = effIfc.getEffContextID();
                if(effContextID.equals(configurationItemID))
                {
                     String startvalue= effIfc.getStart();
                     int diflength = curvalue.length() - startvalue.length();
                     if( diflength == 0 ){
                     	 if(curvalue.compareTo(startvalue) >= 0){
                     	     String endvalue = effIfc.getEnd();
                     	     boolean flag = true;
                     	     if(endvalue != null){
                     	          flag = endvalue.compareTo(curvalue) >= 0;
                     	     }
                     	     if(flag){
                     	          result.add(new Object[]{part});
                     	          break;
                     	     }
                     	 }
                     }
                 }
             }
         }
    }

    /**
     * 根据配置规范为查询空间添加查询条件。
     * baselineable==leftBsoID
     * baseline== rigthBsoID

     * 添加查询条件：
     * select * from A a,...,BaselineLink b
     * where a.bsoID = b.baselineable and b.baseline = baselineIfc.bsoID.
     * 其中baselineIfc由用户选择指定。
     * 即:where a.bsoID = b.leftBsoID and b.rightBsoID = baselineIfc.bsoID

     * @param query QMQuery
     * @return QMQuery 添加完查询条件的query
     * @throws QMException
     */

    private QMQuery baseline_appendSearchCriteria(QMQuery query)
        throws QMException//, QueryException
    {if(PartDebug.verbose)
        PartDebug.trace(this, "baseline_appendSearchCriteria() begin ....");
        PartBaselineConfigSpec baselineConfigSpec = getBaseline();
        if(baselineConfigSpec.getBaselineIfc() == null)
        {if(PartDebug.verbose)
            PartDebug.trace(this, "baseline_appendSearchCriteria end....return is QMQuery");
            return query;
        }
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        BaselineIfc baselineIfc = null;
        try
        {
            if(baselineConfigSpec.getBaselineIfc() != null)
                baselineIfc = (BaselineIfc)pservice.refreshInfo(baselineConfigSpec.getBaselineIfc());
        }
        catch(QMException e)
        {
            e.printStackTrace();
            ////包装提示信息："无法附加搜索条件以基于基准线信息选择部件。参考基准线可能已被删除。"
            throw new PartException(e, RESOURCE, "9", null);
        }
        QMQuery newQuery = query.duplicate();
        //String firstBsoName = newQuery.getBsoNameAt(0);
        //追加一个业务对象。
        int appendLocation = newQuery.appendBso("BaselineLink",false);
        //BaselineLink记录Baseline和Baselineable的关联，查询结果不返回。
        if(newQuery.getConditionCount()>0)
            newQuery.addAND();
        newQuery.addLeftParentheses();
        QueryCondition condition1 = new QueryCondition("rightBsoID","=", baselineIfc.getBsoID());
        //构造查询条件，关联指定基准线
        newQuery.addCondition(appendLocation, condition1);
        QueryCondition condition2 = new QueryCondition("bsoID", "leftBsoID");
        //构造查询条件，受基准线管理的对象的ID记录在指定关联中
        newQuery.addAND();
        newQuery.addCondition(0, appendLocation, condition2);
        newQuery.addRightParentheses();
        PartDebug.trace(this, "baseline_appendSearchCriteria end....return is QMQuery");
        return newQuery;
    }

    /**
     * 根据配置规范中包含的信息过滤结果集和。不做任何处理，直接返回。
     * @param collection Collection
     * @return Collection
     */
    private Collection baseline_process(Collection collection)
    {
        return collection;
    }

    /**
     * 根据配置规范为查询空间添加查询条件：
     * 1，如果当前有效性配置有效，调用有效性筛选条件子方法；
     * 2，如果当前基准线配置有效，调用基准线筛选条件子方法；
     * 3，如果当前标准配置有效，调用标准筛选条件子方法；
     * @param query QMQuery
     * @throws ConfigException
     * @return QMQuery
     */
    public QMQuery appendSearchCriteria(QMQuery query) throws ConfigException
    {if(PartDebug.verbose)
        PartDebug.trace(this, "appendSearchCriteria() begin ....");
        if(getEffectivityActive() && getEffectivity() != null)
        try
        {
            return effectivity_appendSearchCriteria(query);
        }
        catch(QMException ex)
        {
            ex.printStackTrace();
            throw new ConfigException(ex);
        }
        if(getStandardActive() && getStandard() != null)
        try
        {
            return standard_appendSearchCriteria(query);
        }
        catch(QMException ex)
        {
            ex.printStackTrace();
            throw new ConfigException(ex);
        }
        if(getBaselineActive() && getBaseline() != null)
        try
        {
            return baseline_appendSearchCriteria(query);
        }
        catch(QMException ex)
        {
            ex.printStackTrace();
            throw new ConfigException(ex);
        }
        try
        {if(PartDebug.verbose)
            PartDebug.trace(this, "appendSearchCriteria() end....return is QMQuery");
            return (QMQuery)query.duplicate();
        }
        catch(QMException ex)
        {
            ex.printStackTrace();
            throw new ConfigException(ex);
        }
    }
    /**
     * 根据配置规范中包含的信息过滤结果集和。
     * 1，如果当前有效性配置有效，调用有效性过滤结果集和子方法；
     * 2，如果当前基准线配置有效，调用基准线过滤结果集和子方法；
     * 3，如果当前标准配置有效，调用标准过滤结果集和子方法；
     * @param collection Collection
     * @return Collection
     * @throws ConfigException
     */
    public Collection process(Collection collection)
        throws ConfigException
    {if(PartDebug.verbose)
        PartDebug.trace(this, "progress() begin ....");
        if(getEffectivityActive() && getEffectivity() != null)
        try
        {if(PartDebug.verbose)
            PartDebug.trace(this, "progress() end....return is Collection");
            return effectivity_process(collection);
        }
        catch(QMException ex)
        {
            ex.printStackTrace();
            throw new ConfigException(ex);
        }
        else
        {
            if(getStandardActive() && getStandard() != null)
            {
                try
                {if(PartDebug.verbose)
                    PartDebug.trace(this, "progress() end....return is Collection");
                    Collection collection1 =  standard_process(collection);
                    return collection1;
                }
                catch(QMException ex)
                {
                    ex.printStackTrace();
                    throw new ConfigException(ex);
                }
            }
            else
            {
                if(getBaselineActive() && getBaseline() != null)
                {if(PartDebug.verbose)
                    PartDebug.trace(this, "progress() end....return is Collection");
                    return baseline_process(collection);
                }
                else
                {if(PartDebug.verbose)
                    PartDebug.trace(this, "progress() end....return is Collection");
                    return collection;
                }
            }
        }
    }
}
