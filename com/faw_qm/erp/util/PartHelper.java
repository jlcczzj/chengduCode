/**
 * ���ɳ��� PartHelper.java    1.0    2013/06/05
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ���Ͻṹ������г�����������״̬Ϊ�����е��Ӽ� ������ 2014-03-05
 * SS2 ƽ̨���⣺A004-2015-3135��״̬��Ϊ�������ʽά��������ӵ���������״̬�� liunan 2015-6-10
 * SS3 ����xml�ļ��� Ҫ������ź����Ϻ��г�Ʒ�ű���һ��
 */
package com.faw_qm.erp.util;


import java.util.*;

import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.erp.ejb.service.MaterialSplitService;
import com.faw_qm.erp.exception.QMXMLException;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.resource.util.ResourceExportImportHandler;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;

import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.ListRoutePartLinkInfo;
import com.faw_qm.technics.route.model.RouteNodeIfc;
import com.faw_qm.technics.route.model.RouteNodeInfo;
import com.faw_qm.technics.route.model.TechnicsRouteBranchInfo;
//CCBegin SS2
import com.faw_qm.codemanage.ejb.service.CodingManageService;
//CCEnd SS2


/**
* <p>��ERP�������ù�����</p>
* <p>Description: </p>
* <p>Copyright: Copyright (c) 2013</p>
* <p>Company: һ������</p>
* @author ������
* @version 1.0
*/
public class PartHelper   
{
	 private static final String RESOURCE = "com.faw_qm.erp.util.ERPResource";
	 PartConfigSpecInfo partConfigSpecIfc = null;
	 //CCBegin SS2
	 //String[] State={"����","����׼��","����"};
	 String[] State=null;
	 //CCEnd SS2
	 private static HashMap routeHashMap;
    public PartHelper()  
    {     
    	routeHashMap = new HashMap();
    	//CCBegin SS2
    	try
    	{
    		CodingManageService service = (CodingManageService)EJBServiceHelper.getService("CodingManageService");
    		Collection coll = service.findDirectClassificationByName("������ķ���״̬","������Ĵ�������");
    		State = new String[coll.size()];
    		Iterator iterator = coll.iterator();
    		int i=0;
    		while(iterator.hasNext())
    		{
    			State[i] = (iterator.next()).toString();
    			i++;
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }
      
   
    /**
     * ������������״�������������ǰ׷��
     * ���������Լ������Ժ��״̬�����Ϊ�������������������ͼ���°汾�㲿��״̬����������
     * ��׷�ݸü�������ͼ֮ǰ���������İ汾��
     * @param vec �㲿������ �� �ṹ������
     * @return
     * 
     * Vector
     * @throws QMException 
     */
    public QMPartIfc filterLifeState(QMPartIfc part1) throws QMException
    {
        QMPartIfc part = null;
        Object[] obj;
        Vector result = new Vector();
        if(part1 instanceof QMPartIfc)
        { 
        	 part = part1;
               
                String state = part.getLifeCycleState().getDisplay();

                for(int j=0;j<State.length;j++)
                {
                	// System.out.println("state = "+state);
                    if(state.contains(State[j]))
                    {                       
                    	 return part;
                    }
                }

              // System.out.println("���հ汾 = "+part);
                //�����ǰ����������������׷��ǰһ���汾
               
                	part=getPart(part1);
                
      
        }
        return part;
    }
    /**
     * ������������״̬׷�ݵ�ǰ��汾�㲿��
     * @param curPart
     * @return
     * 
     * QMPartIfc
     * @throws QMException 
     */
    public QMPartIfc getPart(QMPartIfc curPart) throws QMException
    {
        String preID = curPart.getPredecessorID();
        PersistService persistService = (PersistService)EJBServiceHelper.getService("PersistService");
       
       // System.out.println("curPart.getPredecessorID() = "+curPart.getPredecessorID());
        if(preID==null||preID.length()==0)
        {
        	return null;
        }
        QMPartIfc prePart = (QMPartIfc)persistService.refreshInfo(preID, false);
            String state = prePart.getLifeCycleState().getDisplay();
            for(int j = 0;j < State.length;j++)
            {
              //  System.out.println("==================������������״̬׷�ݵ�ǰ��汾�㲿�� state = "+state);                
                if(state.equals(State[j]))
                {
                    return prePart;
                }
            }
           // System.out.println("==================������������״̬׷�ݵ�ǰ��汾�㲿�� prePart = "+prePart); 
            return getPart(prePart);
//        }
    }
    
    /**
	 * ��������ϸע����ʱ�����һ��
	 * @param part
	 * @param link
	 * @return
	 * @throws QMException
	 */
	public static Vector getRouteBranchs(QMPartIfc part, ListRoutePartLinkInfo link)
	throws QMException
	{
		try
		{
//			System.out.println("linkkkkkkkkkkkkkkkkkkkkkkkkkkkkk======="+link);
			//��������˵�ǰ�����·����Ϣ����ֱ�ӷ���
			Vector objs = new Vector();
			if(routeHashMap.containsKey(part))
			{
				objs = (Vector)routeHashMap.get(part);
				return objs;
			}
			//��õ�ǰ��Ч��·�߹���
			TechnicsRouteService tr = (TechnicsRouteService)EJBServiceHelper.getService("TechnicsRouteService");
			//����������ȥִ������û�б�Ҫquestions
			Collection c = getRoutesAndLinks(part);
			//System.out.println("cssssssssssss=========="+c);
			Iterator i = c.iterator();
			//����ֻ������һ��
			ListRoutePartLinkInfo info = null;

			if(!i.hasNext() && link == null){
				return new Vector();
			}
		
			if(link == null)
			{
				 info = (ListRoutePartLinkInfo)i.next();
			
//				System.out.println("info1info1info1====="+info1);

			} 
			else
			{
				info = link;
			}

			HashMap map = (HashMap)tr.getRouteBranchs(info.getRouteID());
		
			if(map == null || map.size() == 0)
				return new Vector();
			Collection coll = getRouteBranches(map);

//			����·��
			String routeAsString = "";
//			·��ȫ��  
			String routeAllCode = "";
//			װ��·��
			String routeAssemStr = "";
//			����·��
			
			String routeOtherCode="";
//			���·����������������
			String jHRoute="";
//			�Ƿ��·��
			String isMoreRoute="false";
			int ii=coll.size();	
			if(ii>1)
			{
				isMoreRoute="true";
			}
			for(Iterator routeiter = coll.iterator(); routeiter.hasNext();)
			{
				String routeAs[] = (String[])routeiter.next();
				String makeStr = routeAs[1];
//				for(int k=0;k<makeStr.length();k++){
//					
//				}
				String assemStr = routeAs[2];
				String isMainRoute = routeAs[3];
				String otherMake="";
				String otherAss="";
				String otherCode="";
				if(isMainRoute.equals("��"))
				{
						jHRoute=makeStr;
//					}
					routeAsString = makeStr;
					routeAssemStr = assemStr;
					if(routeAssemStr != "")
						routeAllCode = routeAsString + ";" + routeAssemStr;
					else
						routeAllCode = routeAsString;
				}
				//�������Ҫ·���������·��
				else
				{
					//����·���е�����·��
					if(makeStr!=null && makeStr!="")
					{
						otherMake=makeStr;
					}
					else
					{
						otherMake="";
					}
					//����·���е�װ��·��
					if(assemStr!=null && assemStr!="")
					{
						otherAss=assemStr;
					}
					else
					{
						otherAss="";
					}
					//�������·��
					if(otherMake!="" && otherAss!="")
					{
						otherCode=otherMake+";"+otherAss;
					}
					else if(otherMake=="" && otherAss!="")
					{
						otherCode=otherAss;
					}
					else if(otherMake!="" && otherAss=="")
					{
						otherCode=otherMake;
					}
					else
					{
						otherCode="";
					}
				}
				//����ж������·�ߣ����������·����ϣ��á������ָ�
				if(otherCode!="")
				{
					routeOtherCode=routeOtherCode+":"+otherCode;
				}
			}
			objs.add(routeAsString);
			objs.add(routeAllCode);
			objs.add(routeAssemStr);
			objs.add(routeOtherCode);
			objs.add(isMoreRoute);
			//CCBegin by chudaming 20100322
			objs.add(jHRoute);
			//CCEnd by chudaming 20100322
			routeHashMap.put(part, objs);
//			System.out.println("objsobjsobjsobjs=============="+objs);
//			System.out.println("routeAllCoderouteAllCoderouteAllCoderouteAllCode100304=============="+routeAllCode);
			return objs;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new QMException(ex);
		}
	}
	
	private static Collection getRoutesAndLinks(QMPartIfc part)
	throws QMException
	{
		
//		CodingIfc code = ResourceExportImportHandler.importCode("ȡ��","���ı��");
//		String qx = "";
//		if(code!=null)
//			qx = code.getBsoID();
//		System.out.println("qx="+qx);
		 
		PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
		QMQuery query = new QMQuery("ListRoutePartLink");
		int i = query.appendBso("TechnicsRouteList", false);
		QueryCondition qc = new QueryCondition("leftBsoID", "bsoID");
	    query.addCondition(0, i, qc);
		QueryCondition qc1 = new QueryCondition("rightBsoID", "=", part.getMasterBsoID());
		query.addAND();
		query.addCondition(qc1);
		QueryCondition qc2 = new QueryCondition("lifeCycleState" ,"=","RELEASED");
		query.addAND();
		query.addCondition(i,qc2);
		query.addOrderBy("modifyTime",true);
		return pservice.findValueInfo(query, false);
	}
	private static Collection getRouteBranches(HashMap map)
	throws QMException
	{
		Collection v = new Vector();
		Object branchs[] = map.keySet().toArray();
		for(int i = 0; i < branchs.length; i++)
		{
			PersistService pservice = (PersistService)EJBServiceHelper.getService("PersistService");
			TechnicsRouteBranchInfo branchinfo = (TechnicsRouteBranchInfo)branchs[i];
			String isMainRoute = "��";
			if(branchinfo.getMainRoute())
				isMainRoute = "��";
			else
				isMainRoute = "��";
			String makeStr = "";
			String assemStr = "";
			String tempcode = "";
			Object nodes[] = (Object[])map.get(branchinfo);
			Vector makeNodes = (Vector)nodes[0];
//			System.out.println("100224makeNodes========="+makeNodes);
			RouteNodeIfc asseNode = (RouteNodeIfc)nodes[1];
//			System.out.println("100224asseNodeasseNode========="+asseNode.getBsoID());
			if(makeNodes != null && makeNodes.size() > 0)
			{
				for(int m = 0; m < makeNodes.size(); m++)
				{
					RouteNodeInfo node = (RouteNodeInfo)makeNodes.elementAt(m);
					String departid = node.getNodeDepartment();
					BaseValueIfc codeInfo = pservice.refreshInfo(departid);
					String makeCodeStr = "";
					if(codeInfo instanceof CodingIfc)
						//CCBegin by chudaming20100121
						makeCodeStr = ((CodingIfc)codeInfo).getShorten();
					//CCEnd by chudaming20100121
					if(codeInfo instanceof CodingClassificationIfc)
						makeCodeStr = ((CodingClassificationIfc)codeInfo).getClassSort();
					if(makeStr == "")
					{
						makeStr = makeStr + makeCodeStr;
						tempcode = makeCodeStr;
					} else
						if(!tempcode.equals(makeCodeStr))
						{
							makeStr = makeStr + "-" + makeCodeStr;
							tempcode = makeCodeStr;
						}
				}

			}
			if(asseNode != null)
			{
				String departid = asseNode.getNodeDepartment();
				BaseValueIfc codeInfo = pservice.refreshInfo(departid);
				String assemcode = "";
				if(codeInfo instanceof CodingIfc)
					//CCBegin by chudaming20100121
					assemcode = ((CodingIfc)codeInfo).getShorten();
				//CCEnd by chudaming20100121
				if(codeInfo instanceof CodingClassificationIfc)
					assemcode = ((CodingClassificationIfc)codeInfo).getClassSort();
				assemStr = assemcode;
			}
			if(makeStr == null || makeStr.equals(""))
				makeStr = "";
			if(assemStr == null || assemStr.equals(""))
				assemStr = "";
			String array[] = {
					String.valueOf(i + 1), makeStr, assemStr, isMainRoute
			};
			v.add(array);
		}

		return v;
	}
	/**
     * �ж��ַ����Ƿ�ȫ�Ǵ�д
     * @param word �ַ���
     * @return boolean
     */
	public static boolean isUpperCase(String word){
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (!Character.isLowerCase(c)) {
				return false;
			}
		}
		return true;
	}
	 //CCBegin SS1
	/**
	*ͨ��masterBsoID����㲿��ֵ����
	*@param masterid
	*@return QMPartInfo
	*@throws QMException
	*/
	public QMPartInfo getPartInfoByMasterBsoID(String masterid)
			throws QMException {
		try {
			PersistService pService = (PersistService) EJBServiceHelper.getPersistService();
			VersionControlService vcservice = (VersionControlService) EJBServiceHelper.getService("VersionControlService");
			Collection col = vcservice.allVersionsOf((QMPartMasterIfc) pService.refreshInfo(masterid));
			if (col != null && col.iterator().hasNext()) {
				return (QMPartInfo) col.iterator().next();
			}
		} catch (QMException ex) {
			ex.printStackTrace();
			throw ex;
		}
		return null;
	}
	 //CCEnd SS1
	//CCBegin SS3
	 /**
 	 *  ���ϱ�����ɹ���
 	 * @param QMPartIfc partIfc
 	 * @param String partVersion
 	 * @param String makeCodeNameStr
 	 * @param String dashDelimiter
 	 * @return boolean;
	 * @throws QMXMLException 
 	 * @throws QMException
 	 */
	public String getPartNumber(QMPartIfc partIfc,String folder,String lsbb,String jsbb )throws QMException{
		String materialNumber = "";
		if (lsbb == null || lsbb.length() == 0) {
			// ��׼֪ͨ����㲿�������� ����ʷ�汾������ֵΪ�գ�
			if (folder.contains("�������㲿��")
//					
//					|| folder.contains("�����а汾�㲿��")) {
				|| folder.contains("�ų��㲿��")) {
//				CCEnd SS3
				// 1) �㲿���������ϼнڵ���� ���������㲿��������
				// �������а汾�㲿���������ҡ���������Դ�汾����ֵ��
				// �������<������>+��/��+����������Դ�汾��+��-��+·�߼�ƣ�
				// �������������Դ�汾����ֵ���������
				// <������>+��/��+��PDM�����汾��+��-��+·�߼�ƣ�
				if (jsbb != null && jsbb.length() > 0) {
					materialNumber = partIfc.getPartNumber()
							+ "/" + jsbb ;
				} else {
					
					materialNumber = partIfc.getPartNumber() 
							+ "/" + partIfc.getVersionID();
						
				}
//			CCBegin SS3
//			} else if (folder.contains("�������")
//					|| folder.contains("�����ް汾�㲿��")) {
			} else if (folder.contains("�������")
					&& folder.contains("�γ��㲿��")) {
//				CCEnd SS3
				// 2) �㲿���������ϼнڵ����Ϊ��������ġ����������
				// <������>+��/�� +��-��+·�߼�ƣ�
				// 3) �㲿���������ϼа����������ް汾�㲿�����ڵ㣬�������
				// <������>+��/�� +��-��+·�߼�ƣ�
				materialNumber =  partIfc.getPartNumber();
				
			} else if (!folder.contains("�������")
//					CCBegin SS3
//					&& !folder.contains("�����ް汾�㲿��")
					&& !folder.contains("�γ��㲿��")
//					CCEnd SS3
					&& !folder.contains("�������㲿��")
//					CCBegin SS3
//					&& !folder.contains("�����а汾�㲿��")) {
					&& !folder.contains("�ų��㲿��")) {
//				CCEnd SS3
				// 4) �㲿���������ϼнڵ㲻�����������ް汾�㲿������
				// ��������ġ������������㲿�����͡������а汾�㲿������
				// �������<������>+��/��+����������Դ�汾��+��-��+·�߼�ƣ�
				// �������������Դ�汾����ֵ���������
				// <������>+��/��+��PDM�����汾��+��-��+·�߼�ƣ�
				if (jsbb != null && jsbb.length() > 0) {
					materialNumber = partIfc.getPartNumber()
							+ "/" + jsbb;
					
				} else {
				
					materialNumber = 
							partIfc.getPartNumber();
				}
			}

		} else if (lsbb.equals("��ʷ")) {
			// ? ��ʷ�汾����ֵΪ����ʷ�����������<������>+ ��-��+·�߼�ƣ�
			materialNumber =  partIfc.getPartNumber()
					;
			
		} else {
			// ��׼֪ͨ����㲿�������� ����ʷ�汾������ֵΪ��A���ȴ�д��ĸ��
			// �������<������>+��/��+����ʷ�汾��+��-��+·�߼�ƣ�
		
				materialNumber = partIfc.getPartNumber()
				 + "/" + lsbb;
	
		}
		return materialNumber;
	}
	public  String getPartVersion(QMPartIfc partIfc){
		// ��������Դ�汾
		String jsbb;
		String partVersion = "";
		try {
			jsbb = getPartIBA(partIfc, "����Դ�汾","sourceVersion");
	
			//1)	�㲿������������Դ�汾��������ֵ�����㲿���汾����Ϊ��������Դ�汾��
			//2)	�㲿������������Դ�汾������Ϊ�գ����㲿���汾����ΪPDM�����汾��
			if (jsbb != null && jsbb.length() > 0) {
				int index = jsbb.indexOf(".");
				if(index>=0){
					jsbb = jsbb.substring(0,index);
				}
				partVersion = jsbb ;	
			} else {
				partVersion = partIfc.getVersionID();
				
			}
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return partVersion;
	}
	/**
	 * ���ݸ������������Գ�����,����ض�iba����ֵ�� return String Ϊ��iba���Ե�ֵ��
	 * 
	 * @throws QMXMLException
	 */
	public String getPartIBA(QMPartIfc partIfc, String ibaDisplayName,String sName)
	throws QMXMLException {

		String ibaValue = "";
		final HashMap nameAndValue = new HashMap();
		try {
			IBAValueService ibservice=(IBAValueService)EJBServiceHelper.getService("IBAValueService");
			partIfc = (QMPartIfc)ibservice.refreshAttributeContainerWithoutConstraints(partIfc);
		} catch (QMException e) {
			// "ˢ����������ʱ����"
			//logger.error(Messages.getString("Util.7"), e); //$NON-NLS-1$
			throw new QMXMLException(e);
		}
		DefaultAttributeContainer container = (DefaultAttributeContainer) partIfc
		.getAttributeContainer();
		if (container == null) {
			container = new DefaultAttributeContainer();
		}
		final AbstractValueView[] values = container.getAttributeValues();
		for (int i = 0; i < values.length; i++) {
			final AbstractValueView value = values[i];
			final AttributeDefDefaultView definition = value.getDefinition();
			if (definition.getDisplayName().trim()
					.equals(ibaDisplayName.trim())&&definition.getName().trim()
					.equals(sName.trim())) {
				if (value instanceof AbstractContextualValueDefaultView) {
					MeasurementSystemCache.setCurrentMeasurementSystem("SI");
					final String measurementSystem = MeasurementSystemCache
					.getCurrentMeasurementSystem();
					if (value instanceof UnitValueDefaultView) {
						DefaultUnitRenderer defaultunitrenderer = new DefaultUnitRenderer();
						String ss = "";
						try {
							ss = defaultunitrenderer
							.renderValue(
									((UnitValueDefaultView) value)
									.toUnit(),
									((UnitValueDefaultView) value)
									.getUnitDisplayInfo(measurementSystem));
						} catch (UnitFormatException e) {
							// "������λ��ʽ����"
							//logger.error(Messages.getString("Util.8"), e); //$NON-NLS-1$
							throw new QMXMLException(e);
						} catch (IncompatibleUnitsException e) {
							// "���ֲ����ݵ�λ��"
							//logger.error(Messages.getString("Util.9"), e); //$NON-NLS-1$
							throw new QMXMLException(e);
						}
						final String ddd = ((UnitValueDefaultView) value)
						.getUnitDefinition()
						.getDefaultDisplayUnitString(measurementSystem);
						// nameAndValue.put(definition.getDisplayName(), ss +
						// ddd);
						ibaValue = ss + ddd;
					} else {
						// nameAndValue.put(definition.getDisplayName(),
						// ((AbstractContextualValueDefaultView) value)
						// .getValueAsString());
						ibaValue = ((AbstractContextualValueDefaultView) value)
						.getValueAsString();
					}
				} else {
					// nameAndValue.put(definition.getDisplayName(),
					// ((ReferenceValueDefaultView) value)
					// .getLocalizedDisplayString());
					ibaValue = ((ReferenceValueDefaultView) value)
					.getLocalizedDisplayString();
				}
				break;
			}
		}
	
		return ibaValue;
	}
	 /**
     *  ��ȡɸѡ��Ψһ��ʶ��
     * @param part �㲿����
     * @return String ɸѡ��Ψһ��ʶ��
     */
    public  String getPartNumber(final QMPartMasterIfc masterpart)throws QMException
    {
    	QMPartIfc part=getPartInfoByMasterBsoID(masterpart.getBsoID());
        MaterialSplitService msservice = (MaterialSplitService)EJBServiceHelper.getService("MaterialSplitService");
        Vector routevec = RequestHelper.getRouteBranchs(part, null);
        String routeAsString = "";
        String routeAllCode = "";
        String routeAssemStr = "";
        String lsbb = "";
        
        if(routevec.size() != 0)
        {
            routeAsString = (String)routevec.elementAt(0);
            routeAllCode = (String)routevec.elementAt(1);
            lsbb = (String)routevec.elementAt(6);
        }
        String[] aa = new String[2];
//		 ��ȡ��ǰ��������ϼ�
        PartHelper partHelper =  new PartHelper();
		String folder = part.getLocation();
		// ��������Դ�汾
		String jsbb = getPartVersion(part);

       String partnumber = partHelper.getPartNumber(part,folder,lsbb,jsbb);
        return partnumber;
    }
//	CCEnd SS3
    /**
     * ������Ϣ
     */

          private static final boolean VERBOSESYSTEM = (RemoteProperty.getProperty(
	        "com.faw_qm.bomchange.verbose","true")).equals("true");
}

