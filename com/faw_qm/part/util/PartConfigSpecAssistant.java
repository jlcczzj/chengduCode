/** ���ɳ��� PartConfigSpecAssistant.java    1.0    2003/02/18
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  * CR1 2009/04/02 л�� ԭ��:��Щ��������ʱ��û�����壬������������жϣ����ε������߼�
  *                     ����:���ε��������������PartDebug.traceǰ���ӣ�if(StructDebug.verbose)
  *                     ��ע:���v3r11-չ���㲿�����ڵ������Ż�
  * CR2 2009/04/02 л�� ԭ��:�������ù淶���˽����ʱ��û��ʹ��ViewCache�����Ǵ����ݿ��ж�ȡ
  *                     ����:��ͼ��ViewCache�ж�ȡ
  *                     ��ע:���v3r11-�����ڲ�Ʒ�����Ż�
  * SS1 A004-2015-3156 �����ڲ�Ʒ���֡�ֵ��ֵ������Ч�� liunan 2015-6-26
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
  �������PartConfigSpecIfc�Ĺ�ϵͬEffConfigSpecAssistant
  ��EffConfigSpecIfc ��ͬ��
  ��Ӧ�Ĺ��캯����װconfigSpecInfo��ͬ�������Ĺ��캯����
  ��Ӧ����ͨ������װconfigSpecInfo�е�ͬ��������
  ���磺
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
 * �㲿�����ù淶�����ࡣ
 * @author ���ȳ�
 * @version 1.0
 */

public class PartConfigSpecAssistant implements ConfigSpec
{
    private PartConfigSpecIfc configSpecIfc;
    private String RESOURCE = "com.faw_qm.part.util.PartResource";
    static final long serialVersionUID = 1L;
//  Map viewobj=null;//Begin CR2
//  private ViewCache viewCache=null;//End CR2
    //�Ƿ�ֻ��ʾ�������ù淶�����°汾��ֵ����
    private boolean isLatest=true;

    /**
     * ���캯����
     */
    public PartConfigSpecAssistant()
    {
        super();
    }

    /**
     * ���캯��������һ������:�㲿����Ч�����ù淶��
     * @param effectivitycs PartEffectivityConfigSpec
     */
    public PartConfigSpecAssistant(PartEffectivityConfigSpec effectivitycs)
    {
        setEffectivity(effectivitycs);
    }

    /**
     * ���캯��������һ������:�㲿����׼���ù淶��
     * @param standard PartStandardConfigSpec
     */
    public PartConfigSpecAssistant(PartStandardConfigSpec standard)
    {
        setStandard(standard);
    }

    /**
     * ���캯��������һ������:�㲿����׼�����ù淶��
     * @param baselinecs PartBaselineConfigSpec
     */
    public PartConfigSpecAssistant(PartBaselineConfigSpec baselinecs)
    {
        setBaseline(baselinecs);
    }

    /**
     * ���캯��������һ������:�㲿�����ù淶ֵ����
     * @param tempIfc PartConfigSpecIfc
     */
    public PartConfigSpecAssistant(PartConfigSpecIfc tempIfc)
    {
        this(tempIfc,true);
    }
    
    /**
     * ���캯��������һ������:�㲿�����ù淶ֵ����
     * @param tempIfc PartConfigSpecIfc ���ù淶
     * @param isLatest boolean �Ƿ�ֻ��ʾ�������ù淶�����°汾��ֵ����
     */
    public PartConfigSpecAssistant(PartConfigSpecIfc tempIfc,boolean isLatest)
    {
        configSpecIfc = tempIfc;
        this.isLatest=isLatest;
    }

    /**
     * ��ñ�׼���ù淶��
     * @return PartStandardConfigSpec
     */
    public PartStandardConfigSpec getStandard()
    {
        return configSpecIfc.getStandard();
    }

    /**
     * ���ñ�׼���ù淶��
     * @param standard PartStandardConfigSpec
     */
    public void setStandard(PartStandardConfigSpec standard)
    {
        configSpecIfc.setStandard(standard);
    }

    /**
     * �����Ч�����ù淶��
     * @return PartEffectivityConfigSpec
     */
    public PartEffectivityConfigSpec getEffectivity()
    {
        return configSpecIfc.getEffectivity();
    }

    /**
     * ������Ч�����ù淶��
     * @param effectivity PartEffectivityConfigSpec
     */
    public void setEffectivity(PartEffectivityConfigSpec effectivity)
    {
        configSpecIfc.setEffectivity(effectivity);
    }

    /**
     * ��û�׼�����ù淶��
     * @return PartBaselineConfigSpec
     */
    public PartBaselineConfigSpec getBaseline()
    {
        return configSpecIfc.getBaseline();
    }

    /**
     * ���û�׼�����ù淶��
     * @param baseline PartBaselineConfigSpec
     */
    public void setBaseline(PartBaselineConfigSpec baseline)
    {
        configSpecIfc.setBaseline(baseline);
    }

    /**
     * ��ȡ��׼�����Ƿ���Ч��
     * @return boolean
     */
    public boolean getStandardActive()
    {
        return configSpecIfc.getStandardActive();
    }

    /**
     * ���ñ�׼�����Ƿ���Ч��
     * @param flag boolean
     */
    public void setStandardActive(boolean flag)
    {
        configSpecIfc.setStandardActive(flag);
    }

    /**
     * ��ȡ��Ч�������Ƿ���Ч��
     * @return boolean
     */
    public boolean getEffectivityActive()
    {
        return configSpecIfc.getEffectivityActive();
    }

    /**
     * ������Ч�������Ƿ���Ч��
     * @param flag boolean
     */
    public void setEffectivityActive(boolean flag)
    {
        configSpecIfc.setEffectivityActive(flag);
    }

    /**
     * ��׼�������Ƿ���Ч��
     * @return boolean
     */
    public boolean getBaselineActive()
    {
        return configSpecIfc.getBaselineActive();
    }

    /**
     * ���û�׼�������Ƿ���Ч��
     * @param flag boolean
     */
    public void setBaselineActive(boolean flag)
    {
        configSpecIfc.setBaselineActive(flag);
    }

    /**
     * ��ȡ�ĸ����ù淶����Ч�ġ�
     * @return Serializable
     */
    public Serializable getActive()
    {
        return configSpecIfc.getActive();
    }

    /**
     * �������ù淶Ϊ��ѯ�ռ���Ӳ�ѯ������
     * @exception QMException,QueryException;

     * ��Ӳ�ѯ������
     * 1����������״̬�����û�ѡ���Ĳ�����
     * 2����������ͼ���Ƶ����û�ѡ���Ĳ������ǲ�����������ͼ��
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
     * �������ù淶�а�������Ϣ���˽�����͡�
     * 1������Ԫ�����ͼ��飺ÿ��Ԫ�ر�����IteratedIfc�ӿڵ����ࡣ
     * 2��ѭ���ж�ÿһ��Ԫ�أ����Ԫ������ͼ������Ϊ��ָ����ͼ������master�����iterated�Ӽ���
     * ����hash���У�masterId��keyֵ��������ӵ������������С�
     3�����hash������ȡ��������һ��master��iterated���󼯺ϣ�
        �ж�ÿ��iterated�����������ͼ������ǵ�ǰָ����ͼ��
        ��ӵ���ʱ��������У�ѭ���������ж���ʱ����������Ƿ���Ԫ�أ�
        ���û�У�ȡ��ǰ��ͼ�ĸ���ͼ��Ϊ��ǰ������ͼ�����û�и���ͼ������������
        �и���ͼ���ظ�3���������м������л��ƥ���iterated���󣬲���������
     4������һ����ѯ��iterated������ӵ���ʱ������С�
     5�������������ù淶�������м��������ս�����ء�
     * �������ù淶�а�������Ϣ���˽�����ϡ�
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
        //��������������ϼ��е��㲿�� || ��ǰ�����������߹��� || ��ǰ����������߲��ǵ�ǰ�û�)���������жϣ�
        if(flag0 || !flag1 || !flag2)
          //�����ǰ��������ͼ����&&��Ϊ��ǰ����ָ����ͼ��
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
      //��ͼ��汾�����ͼ���ȣ��������£�
      //1��������ͼ���з����������㲿���汾ʱ���ӱ���ͼ��ȡ��
      //2��ֻ�б���ͼ��û�з����������㲿��ʱ������ǰһ����ͼ�в��ң�
      //3�����ǰһ����ͼ��û�з����������㲿��ʱ�������ǰ�����ͼ�в��ҡ���
      //4�����һ����ͼ���ж���汾��������ʱ��ȡ�����°汾��
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
     * �������ù淶Ϊ��ѯ�ռ���Ӳ�ѯ������
     1������Ч�����ù淶�������ͼ��Ϣ��
     2������û��Ѿ�ָ�������ִ�����²�����������ת����
     3������û�������Чֵ���ж���Ч�����ͣ�
     4�������Ч�����������к��ͣ�������Ч�����ù淶��ӵ���Ч�����ù淶�鵱�У�
        �����Ч�������������ͣ�������Ч�����ù淶��ӵ���Ч�����ù淶�鵱�У�
        �����Ч�������������ͣ�ת����Timestamp���ͣ�
        ������Ч�����ù淶��ӵ���Ч�����ù淶�鵱�С���ת��6��
     5������û�û��ָ����Ч��ֵ��Ĭ��Ϊ������Ч�ԣ�ȡ��ǰʱ��Ϊ��Чֵ��
     6��������Ч�Է���
     * �������ù淶Ϊ��ѯ�ռ���Ӳ�ѯ������
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
        //��ȡ��ǰ������ʲô�ô�???
        //��������˼��:���partEffConfigSpec��effectiveUnit�ǿյĻ����Ͱѵ�ǰ�����ڸ�ֵ��
        //timestamp������Ļ������ǰ�effectiveUnitת��Ϊtimestamp��ʽ���ٸ�ֵ��
        //timestamp��

        Timestamp timestamp = partEffConfigSpec.getCurrentDate();
        EffConfigSpecGroupInfo groupInfo = new EffConfigSpecGroupInfo();
        //����ǰ�����Ч�����ù淶�а�������ͼ��Ϣ���õ���Ч�����ù淶�鵱��
        ViewObjectIfc viewIfc1 = partEffConfigSpec.getViewObjectIfc();
        groupInfo.setViewObjectIfc(partEffConfigSpec.getViewObjectIfc());
        //����ܹ����������������¼���
        if(partEffConfigSpec.getEffectiveConfigItemIfc() != null)
        {
            String effectiveUnit = partEffConfigSpec.getEffectiveUnit();
            //������Ҫ�����жϣ�
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
                //���û��effectiveUnit�Ļ������Ե�ǰ������Ϊ׼???
                groupInfo.add(partEffConfigSpec.buildEffConfigSpecInfo(
                    "QMDatedEffectivity", timestamp));
            }
            else
            {
                //���û��effectiveUnit�Ļ������Ե�ǰ������Ϊ׼???
                groupInfo.add(partEffConfigSpec.buildEffConfigSpecInfo(
                    "QMDatedEffectivity", timestamp));
            }
        }
        groupInfo.setViewObjectIfc(viewIfc1);
        return (new EffConfigSpecGroupAssistant(groupInfo)).appendSearchCriteria(query);
    }

    /**
     * �������ù淶�а�������Ϣ���˽�����͡�
     * ���Ϊ��Ч�����ù淶ָ����ͼ��ʹ����ͼ���ù淶���˽�����ͣ�
     * ʹ��"����"���ù淶���˽�����ͣ�����������
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
        //����Ҫ��collection1�е�Ԫ�ؽ��й��ˣ�
        //���ݵ�ǰ����Ч�����ù淶������һ��EffConfigSpecAssistant����Ȼ�������process������
        Collection result = effProcess(collection1);
        return result;
    }

    /**
     * �Բ�ѯ�����һЩ���ӵĴ���,ֻ������Щ���������Ĳ�ѯ���.
     * �����жϿɵ�����ҵ������Ƿ��ѱ������������߻����ѱ������ȵ�.
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
     * �������ù淶Ϊ��ѯ�ռ���Ӳ�ѯ������
     * baselineable==leftBsoID
     * baseline== rigthBsoID

     * ��Ӳ�ѯ������
     * select * from A a,...,BaselineLink b
     * where a.bsoID = b.baselineable and b.baseline = baselineIfc.bsoID.
     * ����baselineIfc���û�ѡ��ָ����
     * ��:where a.bsoID = b.leftBsoID and b.rightBsoID = baselineIfc.bsoID

     * @param query QMQuery
     * @return QMQuery ������ѯ������query
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
            ////��װ��ʾ��Ϣ��"�޷��������������Ի��ڻ�׼����Ϣѡ�񲿼����ο���׼�߿����ѱ�ɾ����"
            throw new PartException(e, RESOURCE, "9", null);
        }
        QMQuery newQuery = query.duplicate();
        //String firstBsoName = newQuery.getBsoNameAt(0);
        //׷��һ��ҵ�����
        int appendLocation = newQuery.appendBso("BaselineLink",false);
        //BaselineLink��¼Baseline��Baselineable�Ĺ�������ѯ��������ء�
        if(newQuery.getConditionCount()>0)
            newQuery.addAND();
        newQuery.addLeftParentheses();
        QueryCondition condition1 = new QueryCondition("rightBsoID","=", baselineIfc.getBsoID());
        //�����ѯ����������ָ����׼��
        newQuery.addCondition(appendLocation, condition1);
        QueryCondition condition2 = new QueryCondition("bsoID", "leftBsoID");
        //�����ѯ�������ܻ�׼�߹���Ķ����ID��¼��ָ��������
        newQuery.addAND();
        newQuery.addCondition(0, appendLocation, condition2);
        newQuery.addRightParentheses();
        PartDebug.trace(this, "baseline_appendSearchCriteria end....return is QMQuery");
        return newQuery;
    }

    /**
     * �������ù淶�а�������Ϣ���˽�����͡������κδ���ֱ�ӷ��ء�
     * @param collection Collection
     * @return Collection
     */
    private Collection baseline_process(Collection collection)
    {
        return collection;
    }

    /**
     * �������ù淶Ϊ��ѯ�ռ���Ӳ�ѯ������
     * 1�������ǰ��Ч��������Ч��������Ч��ɸѡ�����ӷ�����
     * 2�������ǰ��׼��������Ч�����û�׼��ɸѡ�����ӷ�����
     * 3�������ǰ��׼������Ч�����ñ�׼ɸѡ�����ӷ�����
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
     * �������ù淶�а�������Ϣ���˽�����͡�
     * 1�������ǰ��Ч��������Ч��������Ч�Թ��˽�������ӷ�����
     * 2�������ǰ��׼��������Ч�����û�׼�߹��˽�������ӷ�����
     * 3�������ǰ��׼������Ч�����ñ�׼���˽�������ӷ�����
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
