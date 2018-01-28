/**
 * 生成程序QMXMLMaterialSplit.java	1.0              2007-9-27
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.faw_qm.epm.build.model.EPMBuildRuleIfc;
import com.faw_qm.jferp.exception.QMXMLException;
import com.faw_qm.jferp.model.FilterPartInfo;
import com.faw_qm.jferp.model.MaterialSplitIfc;
import com.faw_qm.jferp.model.MaterialStructureIfc;
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
            return "false";
        if(materialSplit.getVirtualFlag())
            return "true";
        else
            return "false";
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
       // System.out.println("partVersionID==========="+partVersionID);
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

    /**
     * 获取来源，枚举值为：自制、外购、待定,默认值为：待定。
     * @return 来源。
     */
    private final String getProducedBy()
    {
        if(String.valueOf(materialSplit.getProducedBy()) == null)
            return "false";
        if(materialSplit.getProducedBy().equals("Y"))
            return "true";
        else
            return "false";
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
            else if(property.getType().equals("basic")){
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
            {
                col.setId(property.getId());
                col.setValue(getPublishType());
            }
            else if(property.getName().equals("mat_num"))
            {
                col.setId(property.getId());
                col.setValue(getMaterialNumber());
            }
            else if(property.getName().equals("part_name"))
            {
                col.setId(property.getId());
            	col.setValue(getPartName());
            }
            else if(property.getName().equals("part_unit"))
            {
                col.setId(property.getId());
                col.setValue(getDefaultUnit());
            }
            else if(property.getName().equals("cgbs"))//采购件标识
            {
                col.setId(property.getId());
                col.setValue(getProducedBy());
            }
            else if(property.getName().equals("part_type"))
            {
                col.setId(property.getId());
                col.setValue(getPartType());
            }
            //20071113 zhangq add for srq begin:ERP恢复数据时使用,因为零部件的类型和来源有特定的对应关系.
            else if(property.getName().equals("source_type"))
            {
                col.setId(property.getId());
                col.setValue(getRouteCode()+getPartType());
            }
            //20071113 zhangq add for srq end
            else if(property.getName().equals("part_modifytime"))
            {
                col.setId(property.getId());
                col.setValue(getPartModifyTime());
            }
            else if(property.getName().equals("splited"))
            {
                col.setId(property.getId());
                col.setValue(getSplitedStr());
            }
            else if(property.getName().equals("part_version"))
            {
            	PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
                col.setId(property.getId());
  	        	col.setValue(getPartVersionID());
//  	          }
            }
            else if(property.getName().equals("part_number"))
            {
                col.setId(property.getId());
                col.setValue(getPartNumber());
            }
            else if(property.getName().equals("part_optioncode"))
            {
                col.setId(property.getId());
                col.setValue(getOptionCode());
            }
            else if(property.getName().equals("part_colorflag"))
            {
                col.setId(property.getId());
                col.setValue(getColorFlagStr());
            }
            else if(property.getName().equals("part_lifecycle_state"))
            {
                col.setId(property.getId());
                col.setValue(getState());
            }
            else if(property.getName().equals("status"))
            {
                col.setId(property.getId());
                col.setValue(getStatusStr());
            }
            else if(property.getName().equals("r_code"))
            {
                col.setId(property.getId());
                col.setValue(getRCode());
            }
            
            else if(property.getName().equals("route"))
            {
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
            else if(property.getName().equals("s_route"))//送往部门
            
            	{
                	col.setId(property.getId());
                	String routeStr = getRoute(); //路线串
                	String zpRoute = "";  //装配路线
                	String zzRoute = "";  //制造路线
                	String route_note = getRouteCode(); //当前路线单位
                	String materialNumber=getMaterialNumber();
                	
                	String makeRoute =  RemoteProperty.getProperty("com.faw_qm.erp.routecode." + route_note);
//                	System.out.println("routeStr======="+routeStr);
//                	System.out.println("route_note======="+route_note);
//                	System.out.println("materialNumber======="+materialNumber);   
//                	System.out.println("makeRoute======="+makeRoute);
                	String tempString="";
                	if(makeRoute==null)
                		makeRoute="";    
                	if(materialNumber.lastIndexOf(makeRoute)<0){
                		 tempString=makeRoute;
                	}else{
                		 tempString=materialNumber.substring(materialNumber.lastIndexOf(makeRoute));
                	}
                	
                	//System.out.println("tempString======="+tempString);

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
                	StringTokenizer stringTokena = new StringTokenizer(zzRoute, "-");
                	while (stringTokena.hasMoreTokens()) {
    	        			String code=stringTokena.nextToken();
    	        			temp.add(code);
            			}
//                 	System.out.println("zpRoute======="+zpRoute);
//                	System.out.println("zzRoute======="+zzRoute);
//                	System.out.println("temp======="+temp);
                	
    				for(int kk=0;kk<temp.size();kk++)
    			  	{
    					//System.out.println("jinru======="+getRCode().equalsIgnoreCase((String)specialRouteCodeVec3a.get(kk)));
    			  		if(route_note.equalsIgnoreCase((String)temp.get(kk)))
    			  		{
//    			  			System.out.println("jinru1111111111======="+dataFlag+"============"+mmcount);
//    			  			System.out.println("jinru1111111111route_note======="+route_note);
    			  		
    			  				
    				  			if(kk==temp.size()-1){
    				  				col.setValue(zpRoute);
    				  				break;
    				  			}
    				  			else{
    			  				  col.setValue(temp.get(kk+1).toString());
    				  			  break;
    				  			}
    			  		
    			  		}
    			  	}
                
                }
            
            else if(property.getName().equals("virtualFlag"))
            {
            	col.setId(property.getId());
            	col.setValue(getVirtualFlag());
            }
            else if(property.getName().equals("isMoreRoute"))
            {
            	col.setId(property.getId());
            	col.setValue(getIsMoreRoute());
            }
            else if(property.getName().equals("CAPPLinkCharacterBunch"))
            {
            	col.setId(property.getId());
            	String ss="bsoID="+part.getBsoID();
            	col.setValue(ss);
            }
            else if(property.getName().equals("sizecoL"))
            {
            	col.setId(property.getId());
            	String ss="";
            	col.setValue(ss);
            }
            else if(property.getName().equals("description"))
            {
            	col.setId(property.getId());
            	String ss="";
            	col.setValue(ss);
            }
            else if(property.getName().equals("designPatternNO"))
            {
            	col.setId(property.getId());
            	String ss="";
            	col.setValue(ss);
            }
            else if(property.getName().equals("notePatternNO"))
            {
            	col.setId(property.getId());
            	String ss="";
            	col.setValue(ss);
            }
            colList.add(col);
        }
        for (int i = 0; i < ibaPropList.size(); i++)
        {
            property = (QMXMLProperty) ibaPropList.get(i);
            col = new QMXMLColumn();
            col.setId(property.getId());
 
            if(partIBAMap.containsKey(property.getName()))
            {  	
                col.setValue(partIBAMap.get(property.getName()).toString());
  
            }
            else
            {
                col.setValue("");
            }

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
