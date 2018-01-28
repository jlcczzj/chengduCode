/**
 * 生成程序QMXMLMaterialSplit.java	1.0              2007-9-27
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 * SS1 按照轴齿用户要求,将单位固定为"件" pante 2014-06-25
 * SS2 A004-2015-3148 顶级物料的z_route的路线是装配路线 liunan 2015-6-29
 */
package com.faw_qm.erp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.faw_qm.epm.build.model.EPMBuildRuleIfc;
import com.faw_qm.erp.exception.QMXMLException;
import com.faw_qm.erp.model.FilterPartInfo;
import com.faw_qm.erp.model.MaterialSplitIfc;
import com.faw_qm.erp.model.MaterialStructureIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.util.VersionControlHelper;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;

/**
 * <p>Title: 零部件的物料。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 张强
 * @version 1.0
 */
public class QMXMLMaterialSplit extends QMXMLData
{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger
            .getLogger(QMXMLMaterialSplit.class);

    private MaterialSplitIfc materialSplit;

    private QMPartIfc part;

    private String partPublishType = "";

    private String partVersionID = "";

    /**
     * 缺省构造函数。
     */
    public QMXMLMaterialSplit()
    {
        super();
    }

    /**
     * 构造函数。带一个参数。
     * @param part 零部件。
     */
    public QMXMLMaterialSplit(final MaterialSplitIfc materialSplit)
    {
        this.materialSplit = materialSplit;
        partVersionID = materialSplit.getPartVersion() == null ? ""
                : materialSplit.getPartVersion();
    }
    
    public final String getVirtualFlag()
    {
    	if(String.valueOf(materialSplit.getVirtualFlag()) == null)
            return "0";
        if(materialSplit.getVirtualFlag())
            return "1";
        else
            return "0";
    }
    
    public final String getIsMoreRoute()
    {
    	if(String.valueOf(materialSplit.getIsMoreRoute())==null)
    		return "0";
    	if(materialSplit.getIsMoreRoute())
    		return "1";
    	else
    		return "0";
    }

    /**
     * 获取零件号，被拆分为物料的零件号。
     * @return 零件号。
     */
    public final String getPartNumber()
    {
        return materialSplit.getPartNumber() == null ? "" : materialSplit
                .getPartNumber();
    }

    /**
     * 获取版本，被拆分为物料的零件的版本(不含版序)。
     * @return 版本。
     */
    public final String getPartVersionID()
    {
        return partVersionID == null ? "" : partVersionID;
    }

    /**
     * 设置版本，被拆分为物料的零部件的版本(不含版序)。
     * @param partVersionID 零部件版本。
     */
    public final void setPartVersionID(String partVersionID)
    {
        this.partVersionID = partVersionID;
    }

    /**
     * 获取状态，被拆分为物料的零件拆分时的生命周期状态。
     * @return 状态。
     */
    public final String getState()
    {
        return part.getLifeCycleState().getDisplay() == null ? "" : part
                .getLifeCycleState().getDisplay();
    }

    /**
     * 获取物料号，拆分后的物料号，由零件号+“-”+路线代码组成,回头件第二次出现加尾号"-1"。
     * @return 物料号。
     */
    public final String getMaterialNumber()
    {
        return materialSplit.getMaterialNumber() == null ? "" : materialSplit
                .getMaterialNumber();
    }

    /**
     * 获取转换标识，零件是否转换为物料，0—未转换，1—已转换。
     * @return 转换标识。
     */
    private final String getSplitedStr()
    {
        if(String.valueOf(materialSplit.getSplited()) == null)
        {
            return "0";
        }
        else
        {
            if(materialSplit.getSplited())
            {
                return "1";
            }
            else
            {
                return "0";
            }
        }
    }

    /**
     * 获取层级状态，物料是否经过拆分，0-最底层物料，1-有下级物料，2—此物料下要挂接原零部件的子件。
     * @return 层级状态。
     */
    private final String getStatusStr()
    {
        return Integer.toString(materialSplit.getStatus());
    }

    /**
     * 获取路线代码与此物料有关的物料拆分后的路线代码。
     * @return 路线代码。
     */
    public final String getRouteCode()
    {
        return materialSplit.getRouteCode() == null ? "" : materialSplit
                .getRouteCode();
    }
    public final String getRCode()
    {
        return materialSplit.getRCode() == null ? "" : materialSplit
                .getRCode();
    }
    /**
     * 获取更改标记，零件的上一个版本是否发布过(N、U、Z)。
     * @return 更改标记。
     */
    public final String getPublishType()
    {
        return partPublishType;
    }

    /**
     * 设置更改标记，零件的上一个版本是否发布过(N、U、Z)。
     * @param partPublishType 更改标记。
     */
    public void setPublishType(String partPublishType)
    {
        this.partPublishType = partPublishType;
    }

    /**
     * 获取路线，事物特性表中一个零件可以有两条路线,多路线时用分号“;”分隔。
     * @return 路线。
     */
    public  final String getRoute()
    {
        return materialSplit.getRoute() == null ? "" : materialSplit.getRoute();
    }

    /**
     * 获取零件名称，被拆分为物料的零件名称,也作为物料的名称。
     * @return 零件名称。
     */
    private final String getPartName()
    {
        return materialSplit.getPartName() == null ? "" : materialSplit
                .getPartName();
    }
    /**
     * 获取物料的生命周期状态。
     * @return 零件名称。
     */
    private final String getMaterialSplitState()
    {
        return materialSplit.getState() == null ? "" : materialSplit
                .getState();
    }
    /**
     * 获取默认单位，枚举类型，包括：个、按照所需、千克、米、升,散热器只使用"件",处理为"个"。
     * @return 默认单位。
     */
    private final String getDefaultUnit()
    {
        return materialSplit.getDefaultUnit() == null ? "" : materialSplit
                .getDefaultUnit();
    }
//    private final String getDefaultUnit()
//  {
//      return materialSplit.getDefaultUnit() == null ? "" :"件";
//  }


    /**
     * 获取来源，枚举值为：自制、外购、待定,默认值为：待定。
     * @return 来源。
     */
    private final String getProducedBy()
    {
        return materialSplit.getProducedBy() == null ? "" : materialSplit
                .getProducedBy();
    }

    /**
     * 获取类型，枚举值有：零件、总成、标准件、产品,还可包括:冲压件、铸件、管件、
     * 非金属件、标准件、装置件、组件、逻辑总成、车型、机型、焊接、装配、酸洗、试制、油漆件。
     * @return 类型。
     */
    private final String getPartType()
    {
        return materialSplit.getPartType() == null ? "" : materialSplit
                .getPartType();
    }

    /**
     * 获取选装策略码，从PDM配置器注册出的零部件记录此码。
     * @return 选装策略码。
     */
    private final String getOptionCode()
    {
        return materialSplit.getOptionCode() == null ? "" : materialSplit
                .getOptionCode();
    }

    /**
     * 获取颜色件标识，1位数字：0为否、1为是。
     * @return 颜色件标识。
     */
    private final String getColorFlagStr()
    {
        return String.valueOf(materialSplit.getColorFlag()) == null ? ""
                : String.valueOf(materialSplit.getColorFlag());
    }

    /**
     * 获取零部件修改时间。
     * @return
     */
    private final String getPartModifyTime()
    {
        return materialSplit.getPartModifyTime().toString() == null ? ""
                : materialSplit.getPartModifyTime().toString();
    }

    /**
     * 根据给定的事物特性持有者,获得其iba属性值。
     * return HashMap 键为iba属性值的属性定义的名称，值为iba属性的值。
     * @throws QMXMLException
     */
    private final HashMap getPartIBA() throws QMXMLException
    {
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartIBA() - start");
        }
        final HashMap nameAndValue = new HashMap();
        try
        {
        	IBAValueService ivservice=(IBAValueService)EJBServiceHelper.getService("IBAValueService");
        	part=(QMPartIfc)ivservice.refreshAttributeContainerWithoutConstraints(part);
        }
        catch (QMException e)
        {
            //"刷新属性容器时出错！"
            logger.error(Messages.getString("Util.7"), e);
            throw new QMXMLException(e);
        }
        DefaultAttributeContainer container = (DefaultAttributeContainer) part
                .getAttributeContainer();
        if(container == null)
            container = new DefaultAttributeContainer();
        final AbstractValueView[] values = container.getAttributeValues();
        for (int i = 0; i < values.length; i++)
        {
            final AbstractValueView value = values[i];
            final AttributeDefDefaultView definition = value.getDefinition();
            if(value instanceof AbstractContextualValueDefaultView)
            {
                MeasurementSystemCache.setCurrentMeasurementSystem("SI");
                final String measurementSystem = MeasurementSystemCache
                        .getCurrentMeasurementSystem();
                if(value instanceof UnitValueDefaultView)
                {
                    DefaultUnitRenderer defaultunitrenderer = new DefaultUnitRenderer();
                    String ss = "";
                    try
                    {
                        ss = defaultunitrenderer.renderValue(
                                ((UnitValueDefaultView) value).toUnit(),
                                ((UnitValueDefaultView) value)
                                        .getUnitDisplayInfo(measurementSystem));
                    }
                    catch (UnitFormatException e)
                    {
                        //"计量单位格式出错！"
                        logger.error(Messages.getString("Util.8"), e);
                        throw new QMXMLException(e);
                    }
                    catch (IncompatibleUnitsException e)
                    {
                        //"出现不兼容单位！"
                        logger.error(Messages.getString("Util.9"), e);
                        throw new QMXMLException(e);
                    }
                    final String ddd = ((UnitValueDefaultView) value)
                            .getUnitDefinition().getDefaultDisplayUnitString(
                                    measurementSystem);
                    nameAndValue.put(definition.getDisplayName(), ss + ddd);
                }
                else
                {
                    nameAndValue.put(definition.getDisplayName(),
                            ((AbstractContextualValueDefaultView) value)
                                    .getValueAsString());
                }
            }
            else
            {
                nameAndValue.put(definition.getDisplayName(),
                        ((ReferenceValueDefaultView) value)
                                .getLocalizedDisplayString());
            }
        }
        if(logger.isDebugEnabled())
        {
            logger.debug("getPartIBA() - end" + nameAndValue.size());
        }
        return nameAndValue;
    }

    /**
     * 设置record元素。
     * @return QMXMLRecord
     * @throws QMXMLException
     */
    public final QMXMLRecord getRecord() throws Exception
    {
    	try{
        if(logger.isDebugEnabled())
        {
            logger.debug("getRecord() - start"); //$NON-NLS-1$
        }
        final QMXMLRecord record = new QMXMLRecord();
        final List propertyList = getPropertyList();
        QMXMLProperty property = null;
        QMXMLColumn col = null;
        final List colList = new ArrayList();
        HashMap partIBAMap = new HashMap();
        try
        {
            partIBAMap = getPartIBA();
        }
        catch (QMXMLException e)
        {
            //"获取事物特性时出错！"
            logger.error(Messages.getString("Util.12") + e);
            throw new QMXMLException(e);
        }
        final List extensionPropList = new ArrayList(1);
        final List basicPropList = new ArrayList(19);
        final List ibaPropList = new ArrayList(13);
        for (int i = 0; i < propertyList.size(); i++)
        {
            property = (QMXMLProperty) propertyList.get(i);
            if(property.getType().equals("extension")){
                extensionPropList.add(property);
            }
            else if(property.getType().equals("basic")
            		//||property.getName().equals("生命周期状态")
            		){
            	//System.out.println("propertypropertypropertyproperty========="+property.getName());
                basicPropList.add(property);
            }
            else if(property.getType().equals("iba")){
                ibaPropList.add(property);
            }
        }
     
        for (int i = 0; i < basicPropList.size(); i++)
        {
            property = (QMXMLProperty) basicPropList.get(i);
            col = new QMXMLColumn();
            if(property.getName().equals("part_publish_type"))
            {//更改标记
                col.setId(property.getId());
                col.setValue(getPublishType());
            }
            else if(property.getName().equals("mat_num"))
            {//物料号
                col.setId(property.getId());
                col.setValue(getMaterialNumber());
            }
            else if(property.getName().equals("part_number"))
            {//零件编号
                col.setId(property.getId());
                col.setValue(getPartNumber());
            }
            else if(property.getName().equals("part_name"))
            {//零件名称
                    col.setId(property.getId());
                	col.setValue(getPartName());
         
            }
            else if(property.getName().equals("part_unit"))
            {//单位
                col.setId(property.getId());
                //CCBegin SS1
//                col.setValue(getDefaultUnit());
                col.setValue("件");
              //CCEnd SS1
            }
            else if(property.getName().equals("part_version"))
            {//零部件版本
            	PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
                 col.setId(property.getId());
  	        	 col.setValue(getPartVersionID());
            }
           
            else if(property.getName().equals("part_type"))
            {//类型
                col.setId(property.getId());
                col.setValue(getPartType());
            }
            else if(property.getName().equals("part_source"))
            {//来源
                col.setId(property.getId());
                col.setValue(getProducedBy());
            }
            
            else if(property.getName().equals("route"))
            {//制造路线串
            	col.setId(property.getId());
            	String a=getRoute();
            	if(!a.equals("")&&a!=null){
            		if(a.indexOf(";")>0){
            	String b=a.substring(0,a.indexOf(";"));
                col.setValue(b);
            	}else if(a.indexOf(";")==0){
            		 col.setValue(" ");
            	}
            		else{
            		col.setValue(a);
            	}
            	}
            }
            else if(property.getName().equals("z_route"))
            {
            	col.setId(property.getId());
            	String routeStr = getRoute(); //路线串
            	String zpRoute = "";  //装配路线
            	String zzRoute = "";  //制造路线
            	String route_note = getRouteCode(); //当前路线单位
            	String materialNumber=getMaterialNumber();
            	//String htj = materialNumber.substring(materialNumber.length()-1);//物料编码如果是回头件则会有数字
            	
            	String makeRoute =  RemoteProperty.getProperty("com.faw_qm.erp.routecode." + route_note);
            	//System.out.println("routeStr======="+routeStr);
            	//System.out.println("route_note======="+route_note);
            	//System.out.println("materialNumber======="+materialNumber);   
            	//System.out.println("makeRoute======="+makeRoute);
            	String tempString="";
            	if(makeRoute==null)
            		makeRoute="";    
            	if(materialNumber.lastIndexOf(makeRoute)<0){
            		 tempString=makeRoute;
            	}else{
            		 tempString=materialNumber.substring(materialNumber.lastIndexOf(makeRoute));
            	}
            	
            	String mCount= "";
            	if(tempString.indexOf("-")>=0&&tempString.indexOf("X-1")<0){ 
            		 mCount=tempString.substring(tempString.indexOf("-")+1,tempString.length());
            	}else{
            		mCount = "0";
            	}
            	
            	//System.out.println("mCount======="+mCount);  
            	int mmcount=new Integer(mCount).intValue();
   	
            	if(routeStr.indexOf(";")>=0){    
            		int a = routeStr.indexOf(";"); 
            		zpRoute = routeStr.substring(a+1);
            		zzRoute = routeStr.substring(0,a);
            	}else{
            		zpRoute = "";	
            		zzRoute = routeStr;
            	}     
            	int m = 0;
            	Vector temp = new Vector();
            	//Vector temp1 = new Vector();
            	StringTokenizer stringTokena = new StringTokenizer(zzRoute, "-");
            	while (stringTokena.hasMoreTokens()) {
	        			String code=stringTokena.nextToken();
	        			temp.add(code);
        			}
             	//System.out.println("zpRoute======="+zpRoute);
            	//System.out.println("zzRoute======="+zzRoute);
            	//System.out.println("temp======="+temp);
            	int dataFlag=0;
				for(int kk=0;kk<temp.size();kk++)
			  	{
					//System.out.println("jinru======="+getRCode().equalsIgnoreCase((String)specialRouteCodeVec3a.get(kk)));
			  		if(route_note.equalsIgnoreCase((String)temp.get(kk)))
			  		{
			  			//System.out.println("jinru1111111111======="+dataFlag+"============"+mmcount);
			  			//System.out.println("jinru1111111111route_note======="+route_note);
			  		
			  			if(dataFlag==mmcount) 
			  			{
			  				
				  			if(kk==temp.size()-1){
				  				col.setValue(zpRoute);
				  				break;
				  			}
				  			else{
			  				  col.setValue(temp.get(kk+1).toString());
				  			  break;
				  			}
			  			}
			  			dataFlag++;
			  		}
			  	}
			
          //CCBegin SS2
          if(materialSplit.getRootFlag())
          {
           	col.setValue(zpRoute);
          }
          //CCEnd SS2
            	
            
            
            }
            else if(property.getName().equals("route_note"))
            {//制造工艺路线描述
            	  col.setId(property.getId());
                  col.setValue(getRouteCode());
            }
            else if(property.getName().equals("z_route_note"))
            {//装配工艺路线描述
            	col.setId(property.getId());
            	String a=getRoute();
            	if(!a.equals("")&&a!=null){
            		if(a.indexOf(";")>0){
		            	String b=a.substring(a.indexOf(";")+1,a.length());
		                col.setValue(b);
	            	}else if (a.indexOf(";")==0){
	            		String v=a.substring(a.indexOf(";")+1,a.length());
	            		col.setValue(v);
	            	}
            	}
            }
            colList.add(col);
        }
        for (int i = 0; i < ibaPropList.size(); i++)
        {
            property = (QMXMLProperty) ibaPropList.get(i);
            col = new QMXMLColumn();
            col.setId(property.getId());
            //CCBegin by chudaming 20100428 BOM接口增加‘试制标识
//            System.out.println("11111111111111111============"+partIBAMap);
//            System.out.println("222222222222222222222222222222============"+ibaPropList);
//            System.out.println("33333333333333333============"+property.getName());
            if(!property.getName().equals("生命周期状态")){
            	//CCEnd by chudaming 20100428
            if(partIBAMap.containsKey(property.getName()))
            {
            	//CCBegin by chudaming 20100608 ‘采集标识’只给主物料打
            	if(property.getName().equals("采集标识")){
            		if(materialSplit.getRootFlag()){
//            			System.out.println("dddddddddd0915000============"+partIBAMap);
//            			System.out.println("dddddddddd09151111============"+col);
//            			System.out.println("dddddddddd09152222============"+property.getName().toString());
//            			System.out.println("dddddddddd091533333333333333333============"+partIBAMap.get(property.getName()).toString());
//            			CCBegin by chudaming 20100919 for qingqi 
            			if(partIBAMap.get(property.getName())!=null){
            			col.setValue(partIBAMap.get(property.getName()).toString());
            			}else{
            				col.setValue("");
            			}
            			//CCEnd by chudaming 20100919 for qingqi 
            		}else{
            			col.setValue("");
            		}
            	}else{
//            		System.out.println("partIBAMappartIBAMappartIBAMap=========="+partIBAMap);
//            		System.out.println("propertyproperty=========="+property.getName());
//            		System.out.println("zzzzzhhhhzzz=========="+partIBAMap.get(property.getName()).toString());
                col.setValue(partIBAMap.get(property.getName()).toString());
            	}
                
            }
            else
            {
                col.setValue("");
            }
            }//CCBegin by chudaming 20100428 BOM接口增加‘试制标识’
            else{
            	col.setValue(getMaterialSplitState());
            }////CCEnd by chudaming 20100428
            
            colList.add(col);
//            System.out.println("colListcolList==="+colList);
        }
        record.setColList(colList);
        if(logger.isDebugEnabled())
        {
            logger.debug("getRecord() - end" + record); //$NON-NLS-1$
        }
        return record;
    }catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
    }

    /**
     * 设置物料值对象
     * @return 返回 materialSplit。
     */
    public final MaterialSplitIfc getMaterialSplit()
    {
        return materialSplit;
    }

    /**
     * 获得物料值对象
     * @param materialSplit 要设置的 materialSplit。
     */
    public final void setMaterialSplit(final MaterialSplitIfc materialSplit)
    {
        this.materialSplit = materialSplit;
    }

    /**
     * 设置零部件值对象
     * @return 返回 materialSplit。
     */
    public void setPartIfc(QMPartIfc partIfc)
    {
        this.part = partIfc;
    }

    /**
     * 获得零部件值对象
     * @return 返回 materialSplit。
     */
    public QMPartIfc getPartIfc()
    {
        return part;
    }
}
