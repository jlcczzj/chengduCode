/** ���ɳ���CappAssociationsLogic.java    1.0  2003/02/06
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * SS1 ��ݿ����������������  ������  2014-05-12
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
 * <p>Title:CappAssociationsPanel�Ŀ����߼�</p>
 * <p>Description:CappAssociationsPanel�Ŀ����߼�,��ע��ҵ�����֮��Ĺ����� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author �ܴ�Ԫ
 *��1��20060720Ѧ���޸ģ��޸ķ�����getRelations()
 *      ��������ж�ɾ������ѯ��Ϊ����������һ�߶���ͬʱ���
 *��2��20060731Ѧ���޸ģ��޸ķ�����getIBAAttValue������IBAValueService �����refreshAttributeContainer�������ɵ��� refreshAttributeContainerWithoutConstraints��
 *    ������ٶ�
 * @version 1.0
 */

public class CappAssociationsLogic
{

    private static final boolean DEBUG = false; //debug��ʶ
    private String standardIconResource; //ͼ��·��
    private String otherSideAttributes[]; //�������Լ�
    private BaseValueIfc object; //ҵ�����
    private Class objectClass; //��������
    private String objectBsoName; //����BsoName
    private String role; //��ɫ��
    private String otherSideRole; //��һ�߽�ɫ��
    private Class otherSideClass; //����ҵ���������
    private String otherSideBsoName; //����ҵ�����BsoName
    private String linkClassName; //��������
    private String linkBsoName; //������BsoName
    private CappAssociationsModel model; //����ģ��
    private String secondeTypeValue;
    private static boolean verbose = QM.getVerbose(); //debug��ʶ


    /**
     * Ĭ�Ϲ��췽��
     */
    public CappAssociationsLogic()
    {
        otherSideAttributes = new String[0];
    }


    /**
     * ����ҵ�����
     * @param obj
     */
    public void setObject(BaseValueIfc obj)
    {
        object = obj;
    }


    /**
     * ����ҵ�����
     * @return
     */
    public BaseValueIfc getObject()
    {
        return object;
    }


    /**
     * ���ý�ɫ��
     * @param s
     */
    public void setRole(String s)
    {
        role = s;
    }


    /**
     * ������
     * @return
     */
    public String getRole()
    {
        return role;
    }


    /**
     * ������չ���Զ�������
     * @param value String ��������
     */
    public void setSecondTypeValue(String value)
    {
        this.secondeTypeValue = value;
    }


    /**
     * �õ���չ���Զ�������
     * @return String  ��������
     */
    public String getSecondTypeValue()
    {
        return this.secondeTypeValue;
    }


    /**
     * ������һ��ҵ�����BsoName
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
     * ���ʹ���ҵ������
     * @return
     */
    public Class getOtherSideClass()
    {
        return otherSideClass;
    }


    /**
     * ���ù���ҵ������
     * @param class1
     */
    public void setOtherSideClass(Class class1)
    {
        otherSideClass = class1;
        otherSideBsoName = getBsoName(getOtherSideClass());
    }


    /**
     * ���ʹ������Լ�
     * @param as
     */
    public void setOtherSideAttributes(String as[])
    {
        otherSideAttributes = as;
    }


    /**
     * ���ù������Լ�
     * @return
     */
    public String[] getOtherSideAttributes()
    {
        return otherSideAttributes;
    }


    /**
     * ���ù�������
     * @param s
     */
    public void setLinkClassName(String s)
            throws ClassNotFoundException
    {
        linkClassName = s;
        linkBsoName = getBsoName(getLinkClassName());
    }


    /**
     * ���ʹ�������
     * @return
     * @throws Exception
     */
    public String getLinkClassName()
    {
        return linkClassName;
    }


    /**
     * ���ʹ�����BsoName
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
     * ���ʹ�����
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
     * ���ù�����
     * @param associationsmodel
     */
    public void setModel(CappAssociationsModel associationsmodel)
    {
        model = associationsmodel;
    }


    /**
     * ���ʹ���ģ��
     * @return
     */
    public CappAssociationsModel getModel()
    {
        return model;
    }


    /**
     * ���ù���ģ��
     * @param class1
     */
    public void setObjectClass(Class class1)
    {
        objectClass = class1;
        objectBsoName = getBsoName(class1);
    }


    /**
     * ����ҵ���������
     * @return
     */
    public Class getObjectClass()
    {
        return objectClass;
    }


    /**
     * ����ҵ�����BsoName
     * @return
     */
    protected String getObjectBsoName()
    {
        return objectBsoName;
    }


    /**
     * ���ʾ������ľ��巽����Ӧ�����Լ�
     * @param obj ����
     * @param method  ����
     * @return ����ֵ
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object getAttrValue(Object obj, Method method)
            throws
            InvocationTargetException, IllegalAccessException
    {
//    	�����ϳߴ��Ϊ�������Ժ�,Ҫ�ڹ���ʹ�ò����������ȷ��ʾ�����ж�.
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
     * ���ݵ�ǰ���󡢹����ࡢ��ɫ������ѯ�뵱ǰ��������Ķ�������һ�߶���ļ��ϡ�
     * @return Collection �����Ķ�������һ�߶���ļ���
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

       //20060720Ѧ���޸ģ�ԭ��������ж���Զ������Ϊ�档�˴��Ĳ�ѯ��Ϊ����������һ�߶���ͬʱ���
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
                    System.out.println("�������" + otherSideBsoName + "*********" +
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
                System.out.println("�������" + otherSideBsoName + "***********" +
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
    //20060720 Ѧ��add,��ѯ�������Ķ�������һ�ߵĶ���
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

        //20060720 Ѧ��add end
        if (verbose)
        {
            System.out.println("getRelations method end ...the result is" +
                               queryresult);

        }
        return queryresult;
    }


    /**
     * �õ��������ľ������������
     * @param prop ����
     * @param obj ����
     * @return  ��������
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
     * ��ø�������ֵ
     * @param persistable AffixIfc ��������ֵ����
     * @param attName String ������
     * @return Object ��������ֵ
     */
    public Object getAffValue(AffixIfc persistable, String attName)
    {
        try
        {
            ServiceRequestInfo info = new ServiceRequestInfo();
            //���־û�����õ�����ֵ�����bsoID
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
            //ͨ�����ø������Է���õ���������ֵ
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
            //��������ֵ,�õ���ǰ���Ե�ֵ
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
     *  ����չ����ֵ����,�������õ�����ֵ
     * @param persistable ExtendAttriedIfc ��չ����ֵ����
     * @param attName String ������
     * @return Object ����ֵ
     */
    public Object getExtendAttValue(ExtendAttriedIfc persistable,
                                    String attName)
    {
        Object obj = null;
        //�����չ��������
        ExtendAttContainer container = persistable.getExtendAttributes();
        if (container == null)
        {
            return null;
        }
        //�õ�����ֵ
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
     * ���IBA����ֵ
     * @param ibaHolderIfc IBAHolderIfc IBA����ֵ����
     * @param attName String IBA������
     * @return Object BA����ֵ
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
        //20060731Ѧ���޸ģ���refreshAttributeContainer�������ɵ��� refreshAttributeContainerWithoutConstraints������ٶ�
        //����IBA����ˢ����������

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
        //�õ�����
        DefaultAttributeContainer defaultAttributeContainer = (
                DefaultAttributeContainer) ibaHolderIfc.getAttributeContainer();

        if (defaultAttributeContainer != null)
        {
            //�õ�IBA����ֵ
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
     * ͨ������Ķ��������ԣ���������������
     * @param prop ������
     * @param obj ����
     * @return Method ����������
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
     * ͨ������Ķ��������ԣ���������������
     * @param prop ������
     * @param obj ����
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
     * ��þ���PropertyDescript�ķ���
     * @param pd PropertyDescript ��������
     * @param classinfo Class ��
     * @return Method ����
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
     * ��þ���PropertyDescript���������Է���
     * @param pd PropertyDescript ��������
     * @param classinfo Class ��
     * @return Method ����
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
     * ��������ɾ���Ĵ���ľ������
     * @param delete_links ��ɾ���Ĺ���
     * @param hashtable �����ӡ��޸ĵĹ���
     * @throws QMRemoteException
     */
    public void save(Enumeration delete_links, Hashtable links)
            throws
            QMRemoteException
    {
        if (verbose)
        {
            System.out.println("ɾ���Ľڵ���=" + delete_links + "=====" + links.size());
        }
        debug("in taskLogic.save");
        while (delete_links.hasMoreElements())
        {
            debug("before isPersistent");
            BinaryLinkIfc binarylinkinfo = (BinaryLinkIfc) delete_links.
                                           nextElement();
            //����־û��������ɾ����
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
                System.out.println("����������" + binarylinkinfo.getBsoID() +
                                   binarylinkinfo.getLeftBsoID());
            }
            obj = getOtherSideBsoID(binarylinkinfo);
            //����־û��������ɾ���棺
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
     * ���ݾ����ֵ���󴴽��¹���
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
            //��ȡ��һ�˶���Ľ�ɫ����
            if (otherSideRole == null)
            {
                otherSideRole = getOtherSideRole();
            }
            setRoleBsoID(binarylinkinfo, persistable.getBsoID(), otherSideRole);
            if (object != null) //��ӵ�
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
     * ���ݾ����ֵ���󴴽��¹���
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
            //��ȡ��һ�˶���Ľ�ɫ����
            if (otherSideRole == null)
            {
                otherSideRole = getOtherSideRole();
            }
            setRoleBsoID(binarylinkinfo, persistable.getBsoID(), otherSideRole);
            if (object != null) //��ӵ�
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
     * ��ȡ��һ�˶���Ľ�ɫ��
     * @return String ��һ�˶���Ľ�ɫ��
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
            //��ȡ��һ�˶���Ľ�ɫ����
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
     * ��þ������͵ļ�����������������·����
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
     * �жϾ����ҵ����ֵ�����Ƿ��ѳ־û���
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
     * ��ȡ����ҵ����ֵ�����ͼ��
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
     * ��þ���className(��ȫ����)���ض�����(attribute)��ʾ��
     * @param attribute   ������
     * @param className  ȫ����
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
     * ��þ���className(��ȫ����)���ض�����(attribute)������
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
     * ��þ���className(��ȫ����)���ض�����(attribute)������
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
     * ��þ������BsoName
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
     * ��þ������BsoName
     * @param cn ��������
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
     * ��ù��������������
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
     * ���ʹ����������
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
     * ���ù����������ֵ
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
     * ����bsoID����ֵ����
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
     * �ɹ���ֵ�����ȡ��һ�߶����bsoID
     * @param bli BinaryLinkIfc ���������
     * @return String ��һ�߶����bsoID
     */
    protected String getOtherSideBsoID(BinaryLinkIfc bli)
    {
        return getRoleBsoID(bli, getOtherSideRole());
    }


    /**
     * ������һ�ߵĽ�ɫ��
     * @param role String ��һ�ߵĽ�ɫ��
     */
    public void setOtherSideRole(String role)
    {
        this.otherSideRole = role;
    }
}
