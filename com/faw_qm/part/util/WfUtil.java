package com.faw_qm.part.util;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.ejb.entity.IBAHolder;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.iba.value.util.IBAValueUtility;
//import com.faw_qm.iba.value.ejb.service.IBAValueHelper;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.definition.*;
import com.faw_qm.iba.definition.litedefinition.*;
//import com.faw_qm.iba.definition.ejb.service.IBADefinitionHelper;
import com.faw_qm.iba.definition.ejb.service.IBADefinitionService;
import com.faw_qm.iba.value.util.AttributeContainer;
import com.faw_qm.iba.value.litevalue.StringValueDefaultView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.units.util.MeasurementSystemDefaultView;
import com.faw_qm.util.EJBServiceHelper;
import java.util.Locale;
import java.util.Vector;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class WfUtil implements Serializable
{
    RequestServer server = null;
    Vector exportIba = null;
    public WfUtil()
    {
    	server = RequestServerFactory.getRequestServer();
    	exportIba = getExportIBAAttributes();
    }
     public StringDefView[] getStringAttributes()
     {
        StringDefView sdv_array[] = null;
        try
        {
        	if(server!=null)//客房端
        	{
	          Class classes[] = {};
	          Object objects[] = {};
	          AttributeOrgNodeView organizers[] = (AttributeOrgNodeView[])invokeServiceMethod("IBADefinitionService","getAttributeOrganizerRoots",classes,objects);
	          Vector sdv_vector = null;
	          if(organizers != null && organizers.length > 0)
	          {
	              sdv_vector = new Vector();
	              int count = organizers.length;
	              int sdv_count = 0;
	              int sdv_index = 0;
	              for(int index = 0; index < count; index++)
	              {
	                  //获得该属性组织器下的所有属性定义
	                  sdv_array = getStringAttributes((AbstractAttributeDefinizerNodeView)(organizers[index]));
	                  if(sdv_array == null || sdv_array.length <= 0)
	                      continue;
	                  sdv_count = sdv_array.length;
	                  //将所有的属性定义放入sdv_vector中
	                  for(sdv_index = 0; sdv_index < sdv_count; sdv_index++)
	                      sdv_vector.addElement(sdv_array[sdv_index]);
	              }
	              //复制到sdv_array中并返回
	              if(sdv_vector.size() > 0)
	              {
	                  sdv_array = new StringDefView[sdv_vector.size()];
	                  sdv_vector.copyInto(sdv_array);
	              }
	          }
	        }
	        else//服务端
	        {
	          IBADefinitionService ibadefservice = (IBADefinitionService)EJBServiceHelper.getService("IBADefinitionService");
	          AttributeOrgNodeView organizers[] = (AttributeOrgNodeView[])ibadefservice.getAttributeOrganizerRoots();
	          Vector sdv_vector = null;
	          if(organizers != null && organizers.length > 0)
	          {
	              sdv_vector = new Vector();
	              int count = organizers.length;
	              int sdv_count = 0;
	              int sdv_index = 0;
	              for(int index = 0; index < count; index++)
	              {
	                  //获得该属性组织器下的所有属性定义
	                  sdv_array = getStringAttributes((AbstractAttributeDefinizerNodeView)(organizers[index]));
	                  if(sdv_array == null || sdv_array.length <= 0)
	                      continue;
	                  sdv_count = sdv_array.length;
	                  //将所有的属性定义放入sdv_vector中
	                  for(sdv_index = 0; sdv_index < sdv_count; sdv_index++)
	                      sdv_vector.addElement(sdv_array[sdv_index]);
	              }
	              //复制到sdv_array中并返回
	              if(sdv_vector.size() > 0)
	              {
	                  sdv_array = new StringDefView[sdv_vector.size()];
	                  sdv_vector.copyInto(sdv_array);
	              }
	          }
	        }
        }
	catch(QMException qme)
        {
        	//System.out.println("例外QMException");
        	qme.printStackTrace();
        }
        return sdv_array;
      }

   public StringDefView[] getStringAttributes( AbstractAttributeDefinizerNodeView node )
   {
     StringDefView sdv_array[] = null;
     try
     {
     	if(server!=null)
     	{
	     //获得接点node下的子接点
	     Class classes[] = {AbstractAttributeDefinizerNodeView.class};
	     Object objects[] = {node};
	     AbstractAttributeDefinizerNodeView child_nodes[] = (AbstractAttributeDefinizerNodeView[])invokeServiceMethod("IBADefinitionService","getAttributeChildren",classes,objects);
	     Vector sdv_vector = null;
	     int count = 0;
	     int index = 0;
	     int child_count = 0;
	     int child_index = 0;
	     if(child_nodes != null && child_nodes.length > 0)
	     {
	         sdv_vector = new Vector();
	         count = child_nodes.length;
	         for(index = 0; index < count; index++)
	         {
	             if(child_nodes[index] instanceof AttributeDefNodeView)
	             {
	             	AttributeDefNodeView attributedefNodeViewl = (AttributeDefNodeView)child_nodes[index];
	             	Class[] classess={AttributeDefNodeView.class};
	             	Object[] objectss={attributedefNodeViewl};
	             	AttributeDefDefaultView default_view = (AttributeDefDefaultView)invokeServiceMethod("IBADefinitionService","getAttributeDefDefaultView",classess,objectss);
	                 if(default_view instanceof StringDefView)
	                     sdv_vector.addElement(default_view);
	             }
	             sdv_array = getStringAttributes(child_nodes[index]);
	             if(sdv_array == null || sdv_array.length <= 0)
	                 continue;
	             child_count = sdv_array.length;
	             for(child_index = 0; child_index < child_count; child_index++)
	                 sdv_vector.addElement(sdv_array[child_index]);
	         }

	         if(sdv_vector.size() > 0)
	         {
	             sdv_array = new StringDefView[sdv_vector.size()];
	             sdv_vector.copyInto(sdv_array);
	         }
	     }
	}
	else
	{
	     IBADefinitionService ibadefservice = (IBADefinitionService)EJBServiceHelper.getService("IBADefinitionService");
	     AbstractAttributeDefinizerNodeView child_nodes[] = (AbstractAttributeDefinizerNodeView[])ibadefservice.getAttributeChildren(node);
	     Vector sdv_vector = null;
	     int count = 0;
	     int index = 0;
	     int child_count = 0;
	     int child_index = 0;
	     if(child_nodes != null && child_nodes.length > 0)
	     {
	         sdv_vector = new Vector();
	         count = child_nodes.length;
	         for(index = 0; index < count; index++)
	         {
	             if(child_nodes[index] instanceof AttributeDefNodeView)
	             {
	             	AttributeDefNodeView attributedefNodeViewl = (AttributeDefNodeView)child_nodes[index];
	             	AttributeDefDefaultView default_view = (AttributeDefDefaultView)ibadefservice.getAttributeDefDefaultView(attributedefNodeViewl);
	                 if(default_view instanceof StringDefView)
	                     sdv_vector.addElement(default_view);
	             }
	             sdv_array = getStringAttributes(child_nodes[index]);
	             if(sdv_array == null || sdv_array.length <= 0)
	                 continue;
	             child_count = sdv_array.length;
	             for(child_index = 0; child_index < child_count; child_index++)
	                 sdv_vector.addElement(sdv_array[child_index]);
	         }

	         if(sdv_vector.size() > 0)
	         {
	             sdv_array = new StringDefView[sdv_vector.size()];
	             sdv_vector.copyInto(sdv_array);
	         }
	     }
	}
      }
      catch(QMException qme)
      {
      	qme.printStackTrace();
      }
      return sdv_array;
   }
   public Object invokeServiceMethod(String serviceName, String methodName, Class[] paraClass, Object[] objs)
      throws QMRemoteException
   {
    	Object object = null;
    	ServiceRequestInfo info1 = new ServiceRequestInfo();
    	info1.setServiceName(serviceName);
    	info1.setMethodName(methodName);
    	info1.setParaClasses(paraClass);
    	info1.setParaValues(objs);
    	object = (Object)server.request(info1);
    	return object;
   }

  //以下方法用来获得给定IBA名称的Iba属性的值
    public String getIBAAttrValue(AbstractValueView[] abview,String IBAName)
    {
            String field_value = "";
            //AbstractValueView abview[] = getAbstractValue(part);
            if(abview == null || abview.length < 1)
                return field_value;
            for(int j = 0; j < abview.length; j++)
            {
                if((abview[j] instanceof ReferenceValueDefaultView))
                    continue;
                String field_name = abview[j].getDefinition().getName();
                if(!field_name.trim().equals(IBAName))
                    continue;
                try
                {
                    if(field_value.length()==0)
                       field_value = IBAValueUtility.getLocalizedIBAValueDisplayString(abview[j], null);
                    else
                       field_value=field_value+";"+IBAValueUtility.getLocalizedIBAValueDisplayString(abview[j], null);
                }
                catch (QMException wte)
                {
                    //System.out.println(wte);
                }

            }
       return field_value;
    }

    public AbstractValueView[] getAbstractValue(QMPartIfc part)
    {
    	//System.out.println("进入WfUtil.getAbstractValue()----------------------------1");
        AbstractValueView aabstractvalueview[] = null;
        try
        {
        	if(server!=null)
        	{
	            //System.out.println("WfUtil.getAbstractValue()--------------s--------------2");
	            IBAHolderIfc ibaholder = (IBAHolderIfc)part;
	            //System.out.println("WfUtil.getAbstractValue()--------------s--------------3");
	            Class[] classes = {IBAHolderIfc.class,Object.class,Locale.class,MeasurementSystemDefaultView.class};
	            Object[] objects = {ibaholder,null,null,null};
	            ibaholder = (IBAHolderIfc)invokeServiceMethod("IBAValueService","refreshAttributeContainer",classes,objects);
	            //System.out.println("WfUtil.getAbstractValue()--------------s--------------4");
	            DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer)ibaholder.getAttributeContainer();
	            //System.out.println("WfUtil.getAbstractValue()---------------s-------------5");
	            if(defaultattributecontainer != null)
	            {
	                aabstractvalueview = defaultattributecontainer.getAttributeValues();
	                //System.out.println("WfUtil.getAbstractValue()-----------s------aabstractvalueview's size "+aabstractvalueview.length);
	                //System.out.println("WfUtil.getAbstractValue()----------s------------------6");
	            }
	        }
	        else
	        {
	            //System.out.println("WfUtil.getAbstractValue()-------------c---------------2");
	            IBAHolderIfc ibaholder = (IBAHolderIfc)part;
	            //System.out.println("WfUtil.getAbstractValue()------------c----------------3");
	            IBAValueService ibavalueservice = (IBAValueService)EJBServiceHelper.getService("IBAValueService");
	            ibaholder = (IBAHolderIfc)ibavalueservice.refreshAttributeContainer(ibaholder,null,null,null);
	            //System.out.println("WfUtil.getAbstractValue()-------------c---------------4");
	            DefaultAttributeContainer defaultattributecontainer = (DefaultAttributeContainer)ibaholder.getAttributeContainer();
	            //System.out.println("WfUtil.getAbstractValue()--------------c--------------5");
	            if(defaultattributecontainer != null)
	            {
	                aabstractvalueview = defaultattributecontainer.getAttributeValues();
	                //System.out.println("WfUtil.getAbstractValue()----------c-------aabstractvalueview's size "+aabstractvalueview.length);
	                //System.out.println("WfUtil.getAbstractValue()------------c----------------6");
	            }
	        }
        }
        catch(QMException wtexception)
        {
            wtexception.printStackTrace();
        }
        return aabstractvalueview;
    }

    public Vector getQMPartIbaValue(QMPartIfc part)
    {
    	Vector ibavector = new Vector();
    	//System.out.println("进入WfUtil.getQMPartIbaValue()----------------------------1");
    	AbstractValueView aabstractvalueview[] = (AbstractValueView[])getAbstractValue(part);
    	//System.out.println("WfUtil.getQMPartIbaValue()----------------------------2");
    	if(aabstractvalueview!=null&&aabstractvalueview.length>0)
    	{
    		for(int j=0;j<aabstractvalueview.length;j++)
    		{
    			//System.out.println("WfUtil.getQMPartIbaValue()----------------------------3");
    			if(aabstractvalueview[j] instanceof ReferenceValueDefaultView)
    				continue;
    			String[] nameValuePair = new String[2];
    			String ibaName = aabstractvalueview[j].getDefinition().getName();
    			String ibaDisplay = aabstractvalueview[j].getDefinition().getDisplayName();
    			//System.out.println("WfUtil.getQMPartIbaValue()-------------------------ibaName = "+ibaName);
    			String ibaValue = "";
    			try
	                {
	                    if(ibaValue.length()==0)
	                       ibaValue = IBAValueUtility.getLocalizedIBAValueDisplayString(aabstractvalueview[j], null);
	                    else
	                       ibaValue=ibaValue+";"+IBAValueUtility.getLocalizedIBAValueDisplayString(aabstractvalueview[j], null);
	                }
	                catch (QMException wte)
	                {
	                    wte.printStackTrace();
	                }
	                nameValuePair[0] = ibaDisplay;
	                nameValuePair[1] = ibaValue;
	                //System.out.println("WfUtil.getQMPartIbaValue()------------------------ibaValue = "+ibaValue);
	                ibavector.addElement(nameValuePair);
	                //System.out.println("WfUtil.getQMPartIbaValue()----------------------------4");
    		}
    	}
    	return ibavector;
    }

  public Object[] getIBAValue(Object[] tempArray,String[] affixAttrNames,QMPartIfc partIfc,int basicl)
  {
  	if(affixAttrNames!=null&&affixAttrNames.length>0)
  	{
  		AbstractValueView[] abstractValueview = getAbstractValue(partIfc);
  		for(int i=0;i<affixAttrNames.length;i++)
  		{
  			String affixName = affixAttrNames[i];
  			String affixValue = getIBAAttrValue(abstractValueview,affixName);
  			tempArray[basicl+7+i] = affixValue;
  		}
  	}
  	return tempArray;
  }
  /*
  public String getDispalyName(String ibaName)
  {
  	String displayName = "";
  	StringDefView[] stringdef = getStringAttributes();
  	if(stringdef!=null&&stringdef.length>0)
  	{
  		for(int i=0;i<stringdef.length;i++)
  		{
  			StringDefView defview = (StringDefView)stringdef[i];
  			//System.out.println("Iba属性名称为："+defview.getName());
  			if((defview.getName()).equalsIgnoreCase(ibaName))
  			{
  				displayName= defview.getDisplayName();
  				break;
  			}
  		}
  	}
  	return displayName;
  }
  */
  public String getDisplayName(String ibaName)
  {
  	String displayName = "";
  	String[] englishName = (String[])exportIba.elementAt(0);
  	String[] chineseName = (String[])exportIba.elementAt(1);
  	for(int i=0;i<englishName.length;i++)
  	{
  		if(ibaName.equalsIgnoreCase(englishName[i]))
  		{
  			displayName = chineseName[i];
  			break;
  		}
  	}
  	return displayName;
 }
  public String getDispalyName(StringDefView[] stringdef,String ibaName)
  {
  	String displayName = "";
  	//StringDefView[] stringdef = getStringAttributes();
  	if(stringdef!=null&&stringdef.length>0)
  	{
  		for(int i=0;i<stringdef.length;i++)
  		{
  			StringDefView defview = (StringDefView)stringdef[i];
  			//System.out.println("Iba属性名称为："+defview.getName());
  			if((defview.getName()).equalsIgnoreCase(ibaName))
  			{
  				displayName= defview.getDisplayName();
  				break;
  			}
  		}
  	}
  	return displayName;
  }
  /**
  *此方法用来从文件中获得要输出的IBA属性的名称
  *返回的Vector中有两个元素，第一个元素存放的是IBA属性的英文名称的数组，第二个元素存放的是IBA属性的中文名称的数组。
  */
  //CCBeginBy leix  2009-11-30 enum->enumeration JDK1.5以上版本enum为关键字
  public Vector getExportIBAAttributes()
  {
  	ResourceBundle rb = ResourceBundle.getBundle("com.faw_qm.part.util.fawjf");
  	Enumeration enumeration = rb.getKeys();
  	Vector tempv = new Vector();
  	String s;
  	int i=0;
  	while(enumeration.hasMoreElements())
  	{
  		s=(String)enumeration.nextElement();
  		String value = getResourceValue(rb,s);
  		tempv.addElement(value);
  	}
  	i=tempv.size();
  	String englishName[] = new String[i];
  	String chineseName[] = new String[i];
  	for(int j=0;j<i;j++)
  	{
  		String iba = (String)tempv.elementAt(j);
  		StringTokenizer stk = new StringTokenizer(iba, "@");
  		if(stk.hasMoreTokens())
  		{
  			englishName[j]=stk.nextToken().toString();
  			chineseName[j]=stk.nextToken().toString();
  		}
  	}
  	Vector exportiba = new Vector();
  	exportiba.addElement(englishName);
  	exportiba.addElement(chineseName);
  	return exportiba;

  }
  //CCEndBy leix  2009-11-30 enum->enumeration JDK1.5以上版本enum为关键字
  
  private static String getResourceValue(ResourceBundle resourcebundle, String s)
    {
        String s1 = resourcebundle.getString(s);
        if(s1 != null)
        {
            String s2 = "ISO8859-1";
            try
            {
                byte abyte0[] = s1.getBytes(s2);
                s1 = new String(abyte0);
            }
            catch(Exception exception) { }
        }
        return s1;
    }
}
