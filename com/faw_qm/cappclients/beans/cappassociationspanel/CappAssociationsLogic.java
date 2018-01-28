/** 生成程序CappAssociationsLogic.java    1.0  2003/02/06
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 * SS1 轴齿开发所见即所得添加  郭晓亮  2014-05-12
 * 
 */

package com.faw_qm.cappclients.beans.cappassociationspanel;

import java.awt.Image;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.faw_qm.affixattr.model.AffixIfc;
import com.faw_qm.affixattr.model.AttrValueInfo;
import com.faw_qm.clients.beans.query.QM;
import com.faw_qm.clients.beans.query.QMBusinessInfo;
import com.faw_qm.clients.util.QMContext;
import com.faw_qm.extend.model.ExtendAttriedIfc;
import com.faw_qm.extend.util.ExtendAttContainer;
import com.faw_qm.extend.util.ExtendAttModel;
import com.faw_qm.extend.util.ExtendAttributeHandler;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.util.JNDIUtil;
import com.faw_qm.util.PropertyDescript;
import com.faw_qm.util.RegistryException;


/**
 * <p>Title:CappAssociationsPanel的控制逻辑</p>
 * <p>Description:CappAssociationsPanel的控制逻辑,关注两业务对象之间的关联类 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 管春元
 *（1）20060720薛静修改，修改方法：getRelations()
 *      将错误的判断删掉，查询改为将关联与另一边对象同时查出
 *（2）20060731薛静修改，修改方法：getIBAAttValue，将用IBAValueService 服务的refreshAttributeContainer方法换成调用 refreshAttributeContainerWithoutConstraints，
 *    以提高速度
 * @version 1.0
 */

public class CappAssociationsLogic
{

    private static final boolean DEBUG = false; //debug标识
    private String standardIconResource; //图标路径
    private String otherSideAttributes[]; //关联属性集
    private BaseValueIfc object; //业务对象
    private Class objectClass; //对象类型
    private String objectBsoName; //对象BsoName
    private String role; //角色名
    private String otherSideRole; //另一边角色名
    private Class otherSideClass; //关联业务对象类型
    private String otherSideBsoName; //关联业务对象BsoName
    private String linkClassName; //关联类名
    private String linkBsoName; //关联类BsoName
    private CappAssociationsModel model; //关联模型
    private String secondeTypeValue;
    private static boolean verbose = QM.getVerbose(); //debug标识


    /**
     * 默认构造方法
     */
    public CappAssociationsLogic()
    {
        otherSideAttributes = new String[0];
    }


    /**
     * 设置业务对象
     * @param obj
     */
    public void setObject(BaseValueIfc obj)
    {
        object = obj;
    }


    /**
     * 访问业务对象
     * @return
     */
    public BaseValueIfc getObject()
    {
        return object;
    }


    /**
     * 设置角色名
     * @param s
     */
    public void setRole(String s)
    {
        role = s;
    }


    /**
     * 访问名
     * @return
     */
    public String getRole()
    {
        return role;
    }


    /**
     * 设置扩展属性二级分类
     * @param value String 二级分类
     */
    public void setSecondTypeValue(String value)
    {
        this.secondeTypeValue = value;
    }


    /**
     * 得到扩展属性二级分类
     * @return String  二级分类
     */
    public String getSecondTypeValue()
    {
        return this.secondeTypeValue;
    }


    /**
     * 访问另一边业务类的BsoName
     * @return
     */
    protected String getOtherSideBsoName()
    {
        if (otherSideBsoName == null)
        {
            return "";
        }
        else
        {
            return otherSideBsoName.trim();
        }
    }


    /**
     * 访问关联业务类型
     * @return
     */
    public Class getOtherSideClass()
    {
        return otherSideClass;
    }


    /**
     * 设置关联业务类型
     * @param class1
     */
    public void setOtherSideClass(Class class1)
    {
        otherSideClass = class1;
        otherSideBsoName = getBsoName(getOtherSideClass());
    }


    /**
     * 访问关联属性集
     * @param as
     */
    public void setOtherSideAttributes(String as[])
    {
        otherSideAttributes = as;
    }


    /**
     * 设置关联属性集
     * @return
     */
    public String[] getOtherSideAttributes()
    {
        return otherSideAttributes;
    }


    /**
     * 设置关联类名
     * @param s
     */
    public void setLinkClassName(String s)
            throws ClassNotFoundException
    {
        linkClassName = s;
        linkBsoName = getBsoName(getLinkClassName());
    }


    /**
     * 访问关联类名
     * @return
     * @throws Exception
     */
    public String getLinkClassName()
    {
        return linkClassName;
    }


    /**
     * 访问关联类BsoName
     * @return
     */
    protected String getLinkBsoName()
    {
        if (linkBsoName == null)
        {
            return "";
        }
        else
        {
            return linkBsoName.trim();
        }
    }


    /**
     * 访问关联类
     * @return
     * @throws ClassNotFoundException
     */
    public Class getLinkClass()
            throws ClassNotFoundException
    {
        Class aclass = null;
        if (linkClassName != null && linkClassName.length() != 0)
        {
            aclass = Class.forName(linkClassName);
        }
        return aclass;
    }


    /**
     * 设置关联类
     * @param associationsmodel
     */
    public void setModel(CappAssociationsModel associationsmodel)
    {
        model = associationsmodel;
    }


    /**
     * 访问关联模型
     * @return
     */
    public CappAssociationsModel getModel()
    {
        return model;
    }


    /**
     * 设置关联模型
     * @param class1
     */
    public void setObjectClass(Class class1)
    {
        objectClass = class1;
        objectBsoName = getBsoName(class1);
    }


    /**
     * 访问业务对象类型
     * @return
     */
    public Class getObjectClass()
    {
        return objectClass;
    }


    /**
     * 访问业务对象BsoName
     * @return
     */
    protected String getObjectBsoName()
    {
        return objectBsoName;
    }


    /**
     * 访问具体对象的具体方法对应的属性集
     * @param obj 对象
     * @param method  方法
     * @return 属性值
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object getAttrValue(Object obj, Method method)
            throws
            InvocationTargetException, IllegalAccessException
    {
//    	将材料尺寸改为基本属性后,要在工艺使用材料面板中正确显示所做判断.
//    	if(method.getName().equals("getOfferSizeLength")&&obj instanceof com.faw_qm.resource.support.model.QMMaterialInfo)
//    	{
//    		com.faw_qm.resource.support.model.QMMaterialInfo info=(com.faw_qm.resource.support.model.QMMaterialInfo)obj;
//    		String temp=info.getOfferSizeWidth()+"*"+info.getOfferSizeLength()+"*"+info.getOfferSizeHeight();
//    		return temp;
//    	}
    	Object obj1 = method.invoke(obj, null);

        return obj1;
    }



    /**
     * 根据当前对象、关联类、角色名来查询与当前对象关联的对象与另一边对象的集合。
     * @return Collection 关联的对象与另一边对象的集合
     * @throws QMRemoteException
     * @throws ClassNotFoundException
     */
    public Collection getRelations()
            throws QMRemoteException,
            ClassNotFoundException
    {
        try
        {
            if (getModel() != null)
            {
                return getModel().getAssociations();
            }
        }
        catch (Exception ee)
        {
            ee.printStackTrace();
        }
        boolean flag = false;

       //20060720薛静修改，原因：下面的判断永远不可能为真。此处的查询改为将关联与另一边对象同时查出
        /*if (getOtherSideClass() != null &&
            com.faw_qm.version.model.IteratedIfc.class.isAssignableFrom(this.
                getOtherSideClass()) &&
            com.faw_qm.version.model.MasteredIfc.class.isAssignableFrom(
                getObjectClass()))
        {
            flag = true;
        }
        Collection queryresult = null;
        ServiceRequestInfo info1 = new ServiceRequestInfo();
        info1.setServiceName("PersistService");
        info1.setMethodName("navigateValueInfo");
        System.out.println("flag====="+flag);
        if (flag)
        {
            try
            {
                QMQuery qmquery = new QMQuery(otherSideBsoName, linkBsoName); //add...
                qmquery.addCondition(0, VersionControlHelper.getCondForLatest(true));
                Class[] paraClass =
                        {
                        BaseValueIfc.class, String.class, QMQuery.class,
                        Boolean.TYPE};
                info1.setParaClasses(paraClass);
                Object[] objs =
                        {
                        object, role, qmquery, Boolean.FALSE};

                if (verbose)
                {
                    System.out.println("请求服务" + otherSideBsoName + "*********" +
                                       linkBsoName + "*****" + object +
                                       "***********" + role + "****");

                }
                info1.setParaValues(objs);
            }
            catch (QueryException _e)
            {
                _e.printStackTrace();
            }
        }
        else
        {
            if (verbose)
            {
                System.out.println("请求服务" + otherSideBsoName + "***********" +
                                   linkBsoName);
            }
            Class[] paraClass =
                    {
                    BaseValueIfc.class, String.class, String.class,
                    Boolean.TYPE};
            info1.setParaClasses(paraClass);
            Object[] objs =
                    {
                    object, role, getLinkBsoName(), Boolean.FALSE};
            info1.setParaValues(objs);
            {
                if (verbose)
                {
                    System.out.println("the parameters
         :::" + object.getBsoID() +
                                       ">>>" +
                                       role + ">>>" + getLinkBsoName());
                }
            }
        }*/
    //20060720 薛静add,查询出关联的对象与另一边的对象
       Collection queryresult = null;
        ServiceRequestInfo info1 = new ServiceRequestInfo();
        info1.setServiceName("PersistService");
        info1.setMethodName("findValueInfo");
        try
        {
            QMQuery qmquery = new QMQuery(linkBsoName,otherSideBsoName);

           String oneAttr = JNDIUtil.getBinaryLinkAttr(object.getBsoName(),role,
                linkBsoName);
           QueryCondition cond = new QueryCondition(oneAttr, "=", object.getBsoID());
           qmquery.addCondition(0,cond);
           qmquery.addAND();

           String otherAttr = JNDIUtil.getBinaryLinkAttr(otherSideBsoName,
                   this.getOtherSideRole(),
                   linkBsoName);
           QueryCondition cond1 = new QueryCondition(otherAttr, "bsoID");
           qmquery.addCondition(0, 1, cond1);


            Class[] paraClass =
                   {QMQuery.class};
            info1.setParaClasses(paraClass);
            Object[] objs =
                   {qmquery};
            info1.setParaValues(objs);
            queryresult = (Collection) QMContext.getRequestServer().request(
                    info1);
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
        }

        //20060720 薛静add end
        if (verbose)
        {
            System.out.println("getRelations method end ...the result is" +
                               queryresult);

        }
        return queryresult;
    }


    /**
     * 得到具体对象的具体的属性类型
     * @param prop 对象
     * @param obj 属性
     * @return  属性类型
     * @throws QMRemoteException
     */
    public String getPropertyType(String prop, Object obj)
            throws QMRemoteException
    {
        Class classinfo = obj.getClass();
        String proClass = null;
        if (proClass == null)
        {
            try
            {

                PropertyDescriptor propertydescriptor = new PropertyDescriptor(
                        prop,
                        classinfo);
                if (propertydescriptor != null)
                {
                    return propertydescriptor.getPropertyType().toString();
                }
                PropertyDescript pd = getPropertyDescript(prop,
                        this.getLinkClassName());
                if (pd != null)
                {
                    proClass = pd.getPropertyType();
                }
            }
            catch (ClassNotFoundException _ce)
            {
                _ce.printStackTrace();
            }
            catch (Exception ee)
            {
                return null;
                // ee.printStackTrace();
            }
        }
        return proClass;

    }


    /**
     * 获得附加属性值
     * @param persistable AffixIfc 附加属性值对象
     * @param attName String 属性名
     * @return Object 附加属性值
     */
    public Object getAffValue(AffixIfc persistable, String attName)
    {
        try
        {
            ServiceRequestInfo info = new ServiceRequestInfo();
            //调持久化服务得到属性值对象的bsoID
            info.setServiceName("PersistService");
            info.setMethodName("findValueInfo");
            Class[] theClass =
                    {QMQuery.class};
            QMQuery query = new QMQuery("AttrDefine");
            QueryCondition qc = new QueryCondition("attrName",
                    QueryCondition.EQUAL, attName);
            query.addCondition(qc);
            Object[] theObj =
                    {query};
            info.setParaClasses(theClass);
            info.setParaValues(theObj);
            Collection col = (Collection) QMContext.getRequestServer().request(
                    info);
            String defID = ((BaseValueIfc) col.toArray()[0]).getBsoID();
            //通过调用附加属性服务得到附加属性值
            ServiceRequestInfo info1 = new ServiceRequestInfo();
            info1.setServiceName("AffixAttrService");
            info1.setMethodName("getAffixAttr");
            Class[] theClass1 =
                    {AffixIfc.class};
            Object[] theObj1 =
                    {persistable};
            info1.setParaClasses(theClass1);
            info1.setParaValues(theObj1);
            Vector values = (Vector) QMContext.getRequestServer().request(info1);
            //遍历属性值,得到当前属性的值
            for (int i = 0; i < values.size(); i++)
            {
                AttrValueInfo value = (AttrValueInfo) values.elementAt(i);

                if (value.getAttrDefID().equals(defID))
                {
                    return value.getValue();
                }
            }

        }
        catch (QMException e)
        {
            e.printStackTrace();
            return null;
        }
        return null;

    }


    /**
     *  由扩展属性值对象,属性名得到属性值
     * @param persistable ExtendAttriedIfc 扩展属性值对象
     * @param attName String 属性名
     * @return Object 属性值
     */
    public Object getExtendAttValue(ExtendAttriedIfc persistable,
                                    String attName)
    {
        Object obj = null;
        //获得扩展属性容器
        ExtendAttContainer container = persistable.getExtendAttributes();
        if (container == null)
        {
            return null;
        }
        //得到属性值
        for (int i = 0; i < container.getAttCount(); i++)
        {
            ExtendAttModel model = container.getAttributeAt(i);
            if (model.getAttName().equals(attName))
            {
                obj = model.getAttValue();
                return obj;
            }
        }
        return null;
    }


    /**
     * 获得IBA属性值
     * @param ibaHolderIfc IBAHolderIfc IBA属性值对象
     * @param attName String IBA属性名
     * @return Object BA属性值
     */
    public Object getIBAAttValue(IBAHolderIfc ibaHolderIfc, String attName)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.beans.cappAssociationsPanel.CappAssociationsLogic."
                    +
                    "getIBAAttValue(IBAHolderIfc ibaHolderIfc,String attName) begin"
                    + "ibaHolderIfc=" + ibaHolderIfc + " attName=" + attName);
        }
        //20060731薛静修改，将refreshAttributeContainer方法换成调用 refreshAttributeContainerWithoutConstraints，提高速度
        //调用IBA服务刷新属性容器

        Object obj = null;
        Class[] paraclass =
                {IBAHolderIfc.class};
        Object[] paraobj =
                {ibaHolderIfc};
        ServiceRequestInfo info = new ServiceRequestInfo();
        info.setParaClasses(paraclass);
        info.setParaValues(paraobj);
        info.setServiceName("IBAValueService");
        info.setMethodName("refreshAttributeContainerWithoutConstraints");

        RequestServer server = QMContext.getRequestServer();

        try
        {
            ibaHolderIfc = (IBAHolderIfc) server.request(info);
        }
        catch (QMRemoteException ex)
        {
            ex.printStackTrace();
        }
        //得到容器
        DefaultAttributeContainer defaultAttributeContainer = (
                DefaultAttributeContainer) ibaHolderIfc.getAttributeContainer();

        if (defaultAttributeContainer != null)
        {
            //得到IBA属性值
            AbstractValueView[] values = defaultAttributeContainer.
                                         getAttributeValues();
            for (int index = 0; index < values.length; index++)
            {
                AbstractValueView value = values[index];
                AttributeDefDefaultView definition = value.getDefinition();
                if (definition.getName().equals(attName))
                {
                    obj = ((AbstractContextualValueDefaultView) value).
                          getValueAsString();
                    break;
                }
            }

        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.beans.cappAssociationsPanel.CappAssociationsLogic."
                    +
                    "getIBAAttValue(IBAHolderIfc ibaHolderIfc,String attName) end"
                    + "return is " + obj);

        }
        return obj;
    }


    /**
     * 通过具体的对象及其属性，获得其访问器方法
     * @param prop 属性名
     * @param obj 方法
     * @return Method 访问器方法
     * @throws QMRemoteException
     */
    public Method getGetterMethod(String prop, Object obj)
            throws
            QMRemoteException
    {
        Class classinfo = obj.getClass();
        Method method = null;
        try
        {
            PropertyDescriptor propertydescriptor = new PropertyDescriptor(prop,
                    classinfo);
            method = propertydescriptor.getReadMethod();
        }
        catch (IntrospectionException _e)
        {
            if (verbose)
            {
                System.out.println("the attribute \" " + prop +
                                   " \" no exist the getter method ");
                //_e.printStackTrace();
            }
        }
        if (method == null)
        {
            try
            {
                PropertyDescript pd = getPropertyDescript(prop, classinfo);
                if (pd != null)
                {
                    method = getAttributeMethod(pd, classinfo);
                }
            }
            catch (ClassNotFoundException _ce)
            {
                _ce.printStackTrace();
            }
        }
        return method;
    }


    /**
     * 通过具体的对象及其属性，获得其访问器方法
     * @param prop 属性名
     * @param obj 对象
     * @return
     * @throws QMRemoteException
     */
    public Method getSetterMethod(String prop, Object obj)
            throws
            QMRemoteException
    {
        Class classinfo = obj.getClass();
        Method method = null;
        try
        {
            PropertyDescriptor propertydescriptor = new PropertyDescriptor(prop,
                    classinfo);
            method = propertydescriptor.getWriteMethod();
        }
        catch (IntrospectionException _e)
        {
            if (verbose)
            {
                System.out.println("the attribute \" " + prop +
                                   " \" no exist the getter method ");

            }
        }
        if (method == null)
        {
            try
            {
                PropertyDescript pd = getPropertyDescript(prop, classinfo);
                if (pd != null)
                {
                    method = getAttributeSetMethod(pd, classinfo);
                }
            }
            catch (ClassNotFoundException _ce)
            {
                _ce.printStackTrace();
            }
        }
        return method;
    }


    /**
     * 获得具体PropertyDescript的方法
     * @param pd PropertyDescript 属性描述
     * @param classinfo Class 类
     * @return Method 方法
     */
    private Method getAttributeMethod(PropertyDescript pd, Class classinfo)
    {
        Method method = null;
        if (pd != null)
        {
            String methodname = pd.getLimitValue(QMBusinessInfo.Getter_Method);
            if (methodname != null)
            {
                try
                {
                    method = classinfo.getMethod(methodname, null);
                }
                catch (NoSuchMethodException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return method;
    }


    /**
     * 获得具体PropertyDescript的设置属性方法
     * @param pd PropertyDescript 属性描述
     * @param classinfo Class 类
     * @return Method 方法
     */
    private Method getAttributeSetMethod(PropertyDescript pd, Class classinfo)
    {
        Method method = null;
        if (pd != null)
        {
            // String methodname = pd.getLimitValue(QMBusinessInfo.Setter_Method);
            String methodname = pd.getLimitValue("SetterMethod");
            if (methodname != null)
            {
                try
                {
                    method = classinfo.getMethod(methodname, null);
                }
                catch (NoSuchMethodException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return method;
    }


    /**
     * 用于增、删、改传入的具体关联
     * @param delete_links 待删除的关联
     * @param hashtable 待增加、修改的关联
     * @throws QMRemoteException
     */
    public void save(Enumeration delete_links, Hashtable links)
            throws
            QMRemoteException
    {
        if (verbose)
        {
            System.out.println("删除的节点数=" + delete_links + "=====" + links.size());
        }
        debug("in taskLogic.save");
        while (delete_links.hasMoreElements())
        {
            debug("before isPersistent");
            BinaryLinkIfc binarylinkinfo = (BinaryLinkIfc) delete_links.
                                           nextElement();
            //请求持久化服务进行删除：
            if (isPersistent(binarylinkinfo))
            {
                ServiceRequestInfo info1 = new ServiceRequestInfo();
                info1.setServiceName("PersistService");
                info1.setMethodName("deleteValueInfo");
                Class[] paraClass =
                        {
                        BaseValueIfc.class};
                info1.setParaClasses(paraClass);
                Object[] objs =
                        {
                        binarylinkinfo};
                info1.setParaValues(objs);
                QMContext.getRequestServer().request(info1);
            }
        }
        String obj = null;
        BinaryLinkIfc binarylinkinfo = null;
        for (Enumeration enumeration1 = links.elements();
                                        enumeration1.hasMoreElements();
                                        links.put(obj, binarylinkinfo))
        {

            binarylinkinfo = (BinaryLinkIfc) enumeration1.
                             nextElement();
            if (verbose)
            {
                System.out.println("关联的属性" + binarylinkinfo.getBsoID() +
                                   binarylinkinfo.getLeftBsoID());
            }
            obj = getOtherSideBsoID(binarylinkinfo);
            //请求持久化服务进行删保存：
            ServiceRequestInfo info1 = new ServiceRequestInfo();
            info1.setServiceName("PersistService");
            info1.setMethodName("saveValueInfo");
            Class[] paraClass =
                    {
                    BaseValueIfc.class};
            info1.setParaClasses(paraClass);
            Object[] objs =
                    {
                    binarylinkinfo};
            info1.setParaValues(objs);
            binarylinkinfo = (BinaryLinkIfc) QMContext.getRequestServer().
                             request(
                    info1);
            debug("the_key = " + obj);
        }
    }


    /**
     * 根据具体的值对象创建新关联
     * @param persistable
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     */
    protected BinaryLinkIfc createNewLink(BaseValueIfc persistable)
            throws
            IllegalAccessException, NoSuchMethodException,
            InstantiationException,
            ClassNotFoundException, InvocationTargetException
    {
        Constructor constructor = getLinkClass().getConstructor(null);
        BinaryLinkIfc binarylinkinfo = (BinaryLinkIfc) constructor.newInstance(null);
        debug("CappAssociationsLogic.createNewLink() object: " + object);
        debug("CappAssociationsLogic.createNewLink() added_object: " +
              persistable);
        if (getOtherSideBsoName().equalsIgnoreCase("") ||
            getLinkBsoName().equalsIgnoreCase(""))
        {
            return null;
        }
        try
        {
            //获取另一端对象的角色名：
            if (otherSideRole == null)
            {
                otherSideRole = getOtherSideRole();
            }
            setRoleBsoID(binarylinkinfo, persistable.getBsoID(), otherSideRole);
            if (object != null) //后加的
            {
                setRoleBsoID(binarylinkinfo, object.getBsoID(), role);
            }
        }
        catch (Throwable _ex)
        {
            _ex.printStackTrace();
        }
        return binarylinkinfo;
    }
    
    
    //CCBegin SS1
    
    /**
     * 根据具体的值对象创建新关联
     * @param persistable
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     * @throws InvocationTargetException
     */
    public BinaryLinkIfc createNewLinkForBSX(BaseValueIfc persistable)
            throws
            IllegalAccessException, NoSuchMethodException,
            InstantiationException,
            ClassNotFoundException, InvocationTargetException
    {
        Constructor constructor = getLinkClass().getConstructor(null);
        BinaryLinkIfc binarylinkinfo = (BinaryLinkIfc) constructor.newInstance(null);
        debug("CappAssociationsLogic.createNewLink() object: " + object);
        debug("CappAssociationsLogic.createNewLink() added_object: " +
              persistable);
        if (getOtherSideBsoName().equalsIgnoreCase("") ||
            getLinkBsoName().equalsIgnoreCase(""))
        {
            return null;
        }
        try
        {
            //获取另一端对象的角色名：
            if (otherSideRole == null)
            {
                otherSideRole = getOtherSideRole();
            }
            setRoleBsoID(binarylinkinfo, persistable.getBsoID(), otherSideRole);
            if (object != null) //后加的
            {
                setRoleBsoID(binarylinkinfo, object.getBsoID(), role);
            }
        }
        catch (Throwable _ex)
        {
            _ex.printStackTrace();
        }
        return binarylinkinfo;
    }
    
    
    
    //CCEnd SS1
    
    


    /**
     * 获取另一端对象的角色名
     * @return String 另一端对象的角色名
     */
    protected String getOtherSideRole()
    {
        String otherrole = null;
        if (otherSideRole != null)
        {
            return otherrole = otherSideRole;
        }
        try
        {
            //获取另一端对象的角色名：
            otherrole=JNDIUtil.getLinkRole(getOtherSideBsoName(), getLinkBsoName());
            /*StaticMethodRequestInfo info1 = new StaticMethodRequestInfo();
            info1.setClassName("com.faw_qm.util.JNDIUtil");
            info1.setMethodName("getLinkRole");
            Class[] paraClass =
                    {
                    String.class, String.class};
            info1.setParaClasses(paraClass);
            if (verbose)
            {
                System.out.println("the otherSideBsoName is:::" +
                                   getOtherSideBsoName() +
                                   " the size is :::" +
                                   getOtherSideBsoName().length());
                System.out.println("the linkBsoName is:::" + getLinkBsoName() +
                                   " the size is :::" + getLinkBsoName().length());
            }
            Object[] objs =
                    {
                    getOtherSideBsoName(), getLinkBsoName()}; //???
            info1.setParaValues(objs);
            otherrole = (String) QMContext.getRequestServer().request(
                    info1);*/
        }
        catch (RegistryException _e)
        {
            _e.printStackTrace();
        }
        if (verbose)
        {
            System.out.println(
                    "getOtherSideRole method... the othersiderole id:::" +
                    otherrole);
        }
        return otherrole;
    }


    /**
     * 获得具体类型的简单类名，即不包含类路径。
     * @param class1
     * @return
     */
    protected String getSimpleName(Class class1)
    {
        char ac[] = class1.getName().toCharArray();
        String s = null;
        for (int i = ac.length - 1; i > 0; i--)
        {
            if (ac[i] != '.')
            {
                continue;
            }
            s = class1.getName().substring(i + 1);
            break;
        }
        return s;
    }


    /**
     * 判断具体的业务类值对象是否已持久化。
     * @param persistable
     * @return
     */
    public boolean isPersistent(BaseValueIfc persistable)
    {
        return persistable.getBsoID() != null;
    }

    private void debug(String s)
    {
        if (verbose)
        {
            System.out.println(s);
        }
    }


    /**
     * 获取具体业务类值对象的图标
     * @param basevalueinfo
     * @return
     */
    public Image getStandardIcon(BaseValueIfc basevalueinfo)
    {
        Image image = null;
        if (basevalueinfo != null)
        {
            try
            {
                image = QM.getStandardIcon(basevalueinfo.getBsoName());
            }
            catch (Exception _ex)
            {
                image = null;
            }
        }
        return image;
    }


    /**
     * 获得具体className(完全类名)的特定属性(attribute)显示名
     * @param attribute   属性名
     * @param className  全类名
     * @return
     * @throws ClassNotFoundException
     */
    public String getDisplayName(String attribute, String className)
            throws
            ClassNotFoundException
    {

        String displayName = "";
        try
        {
            if (className == null || attribute == null)
            {
                return displayName;
            }
            if (className.equals("") || attribute.equals(""))
            {
                return displayName;
            }
            PropertyDescript pd = getPropertyDescript(attribute, className);
            if (pd != null)
            {
                displayName = pd.getDisplayName();
            }
            else
            {
                String bsoName = getBsoName(className);
                ExtendAttContainer container = ExtendAttributeHandler.
                                               getExtendAtts(bsoName,
                        getSecondTypeValue());
                if (container != null)
                {
                    displayName = container.findExtendAttModel(attribute).
                                  getAttDisplay();
                }
            }
        }
        catch (Exception _e)
        {
            _e.printStackTrace();
        }
        if (displayName == null)
        {
            displayName = "";
        }
        return displayName;
    }


    /**
     * 获得具体className(完全类名)的特定属性(attribute)的描述
     * @param attribute
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public PropertyDescript getPropertyDescript(String attribute,
                                                Class classobj)
            throws
            ClassNotFoundException
    {
        PropertyDescript propertydescript = null;

            if (classobj == null || attribute == null)
            {
                return propertydescript;
            }

            String bsoName = getBsoName(classobj);
           propertydescript= JNDIUtil.getPropertyDescript(bsoName, attribute);
            /*StaticMethodRequestInfo info1 = new StaticMethodRequestInfo();
            info1.setClassName("com.faw_qm.util.JNDIUtil");
            info1.setMethodName("getPropertyDescript");
            Class[] paraClass =
                    {
                    String.class, String.class};
            info1.setParaClasses(paraClass);
            Object[] objs =
                    {
                    bsoName, attribute};
            info1.setParaValues(objs);
            propertydescript = (PropertyDescript) QMContext.getRequestServer().
                               request(info1);*/


        return propertydescript;
    }


    /**
     * 获得具体className(完全类名)的特定属性(attribute)的描述
     * @param attribute
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public PropertyDescript getPropertyDescript(String attribute,
                                                String className)
            throws
            ClassNotFoundException
    {
        PropertyDescript propertydescript = null;

            if (className == null || attribute == null)
            {
                return propertydescript;
            }
            if (className.equals("") || attribute.equals(""))
            {
                return propertydescript;
            }
            String bsoName = getBsoName(className);
            propertydescript=JNDIUtil.getPropertyDescript(bsoName, attribute);
            /*StaticMethodRequestInfo info1 = new StaticMethodRequestInfo();
            info1.setClassName("com.faw_qm.util.JNDIUtil");
            info1.setMethodName("getPropertyDescript");
            Class[] paraClass =
                    {
                    String.class, String.class};
            info1.setParaClasses(paraClass);
            Object[] objs =
                    {
                    bsoName, attribute};
            info1.setParaValues(objs);
            try
            {
                propertydescript = (PropertyDescript) QMContext.
                                   getRequestServer().
                                   request(info1);
            }
            catch (QMRemoteException ex)
            {
                ex.printStackTrace();
            }*/

        return propertydescript;

    }


    /**
     * 获得具体类的BsoName
     * @param aclass
     * @return
     */
    protected String getBsoName(Class aclass)
    {
        String bsoName = null;
        try
        {
            Method method1 = aclass.getMethod("getBsoName", null);
            bsoName = (String) method1.invoke(aclass.newInstance(), null);
        }
        catch (NoSuchMethodException _e)
        {
            _e.printStackTrace();
        }
        catch (InvocationTargetException _e)
        {
            _e.printStackTrace();
        }
        catch (IllegalAccessException _e)
        {
            _e.printStackTrace();
        }
        catch (InstantiationException _e)
        {
            _e.printStackTrace();
        }
        return bsoName;
    }


    /**
     * 获得具体类的BsoName
     * @param cn 类完整名
     * @return
     * @throws ClassNotFoundException
     */
    protected String getBsoName(String cn)
            throws ClassNotFoundException
    {
        String bsoName = null;
        if (cn != null && cn.trim().length() != 0)
        {
            bsoName = getBsoName(Class.forName(cn));
        }
        return bsoName;
    }


    /**
     * 获得关联类的真正属性
     * @param bsoName
     * @param arole
     * @return
     */
    private String getBinaryLinkAttr(String bsoName, String arole)
    {
        String roleID = null;
        try
        {
           roleID= JNDIUtil.getBinaryLinkAttr(bsoName, arole);

           /* StaticMethodRequestInfo info1 = new StaticMethodRequestInfo();
            info1.setClassName("com.faw_qm.util.JNDIUtil");
            info1.setMethodName("getBinaryLinkAttr");
            Class[] paraClass =
                    {
                    String.class, String.class};
            info1.setParaClasses(paraClass);
            Object[] objs =
                    {
                    bsoName, arole};
            info1.setParaValues(objs);
            if (verbose)
            {
                System.out.println(
                        "getBinaryLinkAttr method...the BsoName is:::" +
                        bsoName);
                System.out.println("getBinaryLinkAttr method...the role is:::" +
                                   arole);
            }
            roleID = (String) QMContext.getRequestServer().
                     request(info1);*/
        }
        catch (RegistryException qmre)
        {
            qmre.printStackTrace();
        }
        return roleID;
    }


    /**
     * 访问关联类的属性
     * @param bli
     * @param arole
     */
    protected String getRoleBsoID(BinaryLinkIfc bli, String arole)
    {
        String attrValue = null;
        String linkAttr = getBinaryLinkAttr(bli.getBsoName(), arole);
        if (linkAttr.equalsIgnoreCase("leftBsoID"))
        {
            attrValue = bli.getLeftBsoID();
        }
        else
        {
            attrValue = bli.getRightBsoID();

        }
        return attrValue;
    }


    /**
     * 设置关联类的属性值
     * @param bli
     * @param value
     * @param arole
     */
    protected void setRoleBsoID(BinaryLinkIfc bli, String value, String arole)
    {
        String linkAttr = getBinaryLinkAttr(bli.getBsoName(), arole);
        if (linkAttr.equalsIgnoreCase("leftBsoID"))
        {
            bli.setLeftBsoID(value);
        }
        else
        {
            bli.setRightBsoID(value);
        }
    }


    /**
     * 根据bsoID更新值对象
     * @param bsoID
     * @return
     */
    protected static BaseValueIfc refreshInfo(String bsoID)
    {
        BaseValueIfc persistable = null;
        try
        {
            ServiceRequestInfo info = new ServiceRequestInfo();
            info.setServiceName("PersistService");
            info.setMethodName("refreshInfo");
            Class[] paraClass =
                    {
                    String.class};
            info.setParaClasses(paraClass);
            Object[] objs =
                    {
                    bsoID};
            info.setParaValues(objs);
            persistable = (BaseValueIfc) QMContext.getRequestServer().
                          request(info);
        }
        catch (QMRemoteException _e)
        {
            _e.printStackTrace();
        }
        return persistable;
    }


    /**
     * 由关联值对象获取另一边对象的bsoID
     * @param bli BinaryLinkIfc 关联类对象
     * @return String 另一边对象的bsoID
     */
    protected String getOtherSideBsoID(BinaryLinkIfc bli)
    {
        return getRoleBsoID(bli, getOtherSideRole());
    }


    /**
     * 设置另一边的角色名
     * @param role String 另一边的角色名
     */
    public void setOtherSideRole(String role)
    {
        this.otherSideRole = role;
    }
}
