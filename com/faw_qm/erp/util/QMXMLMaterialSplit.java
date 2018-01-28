/**
 * ���ɳ���QMXMLMaterialSplit.java	1.0              2007-9-27
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ��������û�Ҫ��,����λ�̶�Ϊ"��" pante 2014-06-25
 * SS2 A004-2015-3148 �������ϵ�z_route��·����װ��·�� liunan 2015-6-29
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
 * <p>Title: �㲿�������ϡ�</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
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
     * ȱʡ���캯����
     */
    public QMXMLMaterialSplit()
    {
        super();
    }

    /**
     * ���캯������һ��������
     * @param part �㲿����
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
     * ��ȡ����ţ������Ϊ���ϵ�����š�
     * @return ����š�
     */
    public final String getPartNumber()
    {
        return materialSplit.getPartNumber() == null ? "" : materialSplit
                .getPartNumber();
    }

    /**
     * ��ȡ�汾�������Ϊ���ϵ�����İ汾(��������)��
     * @return �汾��
     */
    public final String getPartVersionID()
    {
        return partVersionID == null ? "" : partVersionID;
    }

    /**
     * ���ð汾�������Ϊ���ϵ��㲿���İ汾(��������)��
     * @param partVersionID �㲿���汾��
     */
    public final void setPartVersionID(String partVersionID)
    {
        this.partVersionID = partVersionID;
    }

    /**
     * ��ȡ״̬�������Ϊ���ϵ�������ʱ����������״̬��
     * @return ״̬��
     */
    public final String getState()
    {
        return part.getLifeCycleState().getDisplay() == null ? "" : part
                .getLifeCycleState().getDisplay();
    }

    /**
     * ��ȡ���Ϻţ���ֺ�����Ϻţ��������+��-��+·�ߴ������,��ͷ���ڶ��γ��ּ�β��"-1"��
     * @return ���Ϻš�
     */
    public final String getMaterialNumber()
    {
        return materialSplit.getMaterialNumber() == null ? "" : materialSplit
                .getMaterialNumber();
    }

    /**
     * ��ȡת����ʶ������Ƿ�ת��Ϊ���ϣ�0��δת����1����ת����
     * @return ת����ʶ��
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
     * ��ȡ�㼶״̬�������Ƿ񾭹���֣�0-��ײ����ϣ�1-���¼����ϣ�2����������Ҫ�ҽ�ԭ�㲿�����Ӽ���
     * @return �㼶״̬��
     */
    private final String getStatusStr()
    {
        return Integer.toString(materialSplit.getStatus());
    }

    /**
     * ��ȡ·�ߴ�����������йص����ϲ�ֺ��·�ߴ��롣
     * @return ·�ߴ��롣
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
     * ��ȡ���ı�ǣ��������һ���汾�Ƿ񷢲���(N��U��Z)��
     * @return ���ı�ǡ�
     */
    public final String getPublishType()
    {
        return partPublishType;
    }

    /**
     * ���ø��ı�ǣ��������һ���汾�Ƿ񷢲���(N��U��Z)��
     * @param partPublishType ���ı�ǡ�
     */
    public void setPublishType(String partPublishType)
    {
        this.partPublishType = partPublishType;
    }

    /**
     * ��ȡ·�ߣ��������Ա���һ���������������·��,��·��ʱ�÷ֺš�;���ָ���
     * @return ·�ߡ�
     */
    public  final String getRoute()
    {
        return materialSplit.getRoute() == null ? "" : materialSplit.getRoute();
    }

    /**
     * ��ȡ������ƣ������Ϊ���ϵ��������,Ҳ��Ϊ���ϵ����ơ�
     * @return ������ơ�
     */
    private final String getPartName()
    {
        return materialSplit.getPartName() == null ? "" : materialSplit
                .getPartName();
    }
    /**
     * ��ȡ���ϵ���������״̬��
     * @return ������ơ�
     */
    private final String getMaterialSplitState()
    {
        return materialSplit.getState() == null ? "" : materialSplit
                .getState();
    }
    /**
     * ��ȡĬ�ϵ�λ��ö�����ͣ������������������衢ǧ�ˡ��ס���,ɢ����ֻʹ��"��",����Ϊ"��"��
     * @return Ĭ�ϵ�λ��
     */
    private final String getDefaultUnit()
    {
        return materialSplit.getDefaultUnit() == null ? "" : materialSplit
                .getDefaultUnit();
    }
//    private final String getDefaultUnit()
//  {
//      return materialSplit.getDefaultUnit() == null ? "" :"��";
//  }


    /**
     * ��ȡ��Դ��ö��ֵΪ�����ơ��⹺������,Ĭ��ֵΪ��������
     * @return ��Դ��
     */
    private final String getProducedBy()
    {
        return materialSplit.getProducedBy() == null ? "" : materialSplit
                .getProducedBy();
    }

    /**
     * ��ȡ���ͣ�ö��ֵ�У�������ܳɡ���׼������Ʒ,���ɰ���:��ѹ�����������ܼ���
     * �ǽ���������׼����װ�ü���������߼��ܳɡ����͡����͡����ӡ�װ�䡢��ϴ�����ơ��������
     * @return ���͡�
     */
    private final String getPartType()
    {
        return materialSplit.getPartType() == null ? "" : materialSplit
                .getPartType();
    }

    /**
     * ��ȡѡװ�����룬��PDM������ע������㲿����¼���롣
     * @return ѡװ�����롣
     */
    private final String getOptionCode()
    {
        return materialSplit.getOptionCode() == null ? "" : materialSplit
                .getOptionCode();
    }

    /**
     * ��ȡ��ɫ����ʶ��1λ���֣�0Ϊ��1Ϊ�ǡ�
     * @return ��ɫ����ʶ��
     */
    private final String getColorFlagStr()
    {
        return String.valueOf(materialSplit.getColorFlag()) == null ? ""
                : String.valueOf(materialSplit.getColorFlag());
    }

    /**
     * ��ȡ�㲿���޸�ʱ�䡣
     * @return
     */
    private final String getPartModifyTime()
    {
        return materialSplit.getPartModifyTime().toString() == null ? ""
                : materialSplit.getPartModifyTime().toString();
    }

    /**
     * ���ݸ������������Գ�����,�����iba����ֵ��
     * return HashMap ��Ϊiba����ֵ�����Զ�������ƣ�ֵΪiba���Ե�ֵ��
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
            //"ˢ����������ʱ����"
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
                        //"������λ��ʽ����"
                        logger.error(Messages.getString("Util.8"), e);
                        throw new QMXMLException(e);
                    }
                    catch (IncompatibleUnitsException e)
                    {
                        //"���ֲ����ݵ�λ��"
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
     * ����recordԪ�ء�
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
            //"��ȡ��������ʱ����"
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
            		//||property.getName().equals("��������״̬")
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
            {//���ı��
                col.setId(property.getId());
                col.setValue(getPublishType());
            }
            else if(property.getName().equals("mat_num"))
            {//���Ϻ�
                col.setId(property.getId());
                col.setValue(getMaterialNumber());
            }
            else if(property.getName().equals("part_number"))
            {//������
                col.setId(property.getId());
                col.setValue(getPartNumber());
            }
            else if(property.getName().equals("part_name"))
            {//�������
                    col.setId(property.getId());
                	col.setValue(getPartName());
         
            }
            else if(property.getName().equals("part_unit"))
            {//��λ
                col.setId(property.getId());
                //CCBegin SS1
//                col.setValue(getDefaultUnit());
                col.setValue("��");
              //CCEnd SS1
            }
            else if(property.getName().equals("part_version"))
            {//�㲿���汾
            	PersistService ps = (PersistService)EJBServiceHelper.getService("PersistService");
                 col.setId(property.getId());
  	        	 col.setValue(getPartVersionID());
            }
           
            else if(property.getName().equals("part_type"))
            {//����
                col.setId(property.getId());
                col.setValue(getPartType());
            }
            else if(property.getName().equals("part_source"))
            {//��Դ
                col.setId(property.getId());
                col.setValue(getProducedBy());
            }
            
            else if(property.getName().equals("route"))
            {//����·�ߴ�
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
            	String routeStr = getRoute(); //·�ߴ�
            	String zpRoute = "";  //װ��·��
            	String zzRoute = "";  //����·��
            	String route_note = getRouteCode(); //��ǰ·�ߵ�λ
            	String materialNumber=getMaterialNumber();
            	//String htj = materialNumber.substring(materialNumber.length()-1);//���ϱ�������ǻ�ͷ�����������
            	
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
            {//���칤��·������
            	  col.setId(property.getId());
                  col.setValue(getRouteCode());
            }
            else if(property.getName().equals("z_route_note"))
            {//װ�乤��·������
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
            //CCBegin by chudaming 20100428 BOM�ӿ����ӡ����Ʊ�ʶ
//            System.out.println("11111111111111111============"+partIBAMap);
//            System.out.println("222222222222222222222222222222============"+ibaPropList);
//            System.out.println("33333333333333333============"+property.getName());
            if(!property.getName().equals("��������״̬")){
            	//CCEnd by chudaming 20100428
            if(partIBAMap.containsKey(property.getName()))
            {
            	//CCBegin by chudaming 20100608 ���ɼ���ʶ��ֻ�������ϴ�
            	if(property.getName().equals("�ɼ���ʶ")){
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
            }//CCBegin by chudaming 20100428 BOM�ӿ����ӡ����Ʊ�ʶ��
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
     * ��������ֵ����
     * @return ���� materialSplit��
     */
    public final MaterialSplitIfc getMaterialSplit()
    {
        return materialSplit;
    }

    /**
     * �������ֵ����
     * @param materialSplit Ҫ���õ� materialSplit��
     */
    public final void setMaterialSplit(final MaterialSplitIfc materialSplit)
    {
        this.materialSplit = materialSplit;
    }

    /**
     * �����㲿��ֵ����
     * @return ���� materialSplit��
     */
    public void setPartIfc(QMPartIfc partIfc)
    {
        this.part = partIfc;
    }

    /**
     * ����㲿��ֵ����
     * @return ���� materialSplit��
     */
    public QMPartIfc getPartIfc()
    {
        return part;
    }
}
