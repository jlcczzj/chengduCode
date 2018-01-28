package com.faw_qm.jfpublish.receive;
// SS1 �������ķ��㲿�����������������ͼ ������ 2014-7-8
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.part.util.Unit;
import com.faw_qm.pcfg.family.model.GenericPartInfo;
import com.faw_qm.pcfg.logic.LogicBase;
import com.faw_qm.util.EJBServiceHelper;

//CCBegin by liunan 2009-11-06
import java.io.IOException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import com.faw_qm.integration.util.QMProperties;
//CCEnd by liunan 2009-11-06

public class PartCreateDelegate extends AbstractStoreDelegate {

	public static final String DEFAULT_PART_LOCATION = "\\Root\\Administrator";

	private Group myParts;

	private Hashtable myPartIBAMap;
	
	//CCBegin by liunan 2009-11-06 ��Ӷ�logicbase�ı���
	private static String LOGICBASE_PATH;

	static {
		try {
			LOGICBASE_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.logicbase.path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static SimpleDateFormat logDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd-HH.mm");
	//CCEnd by liunan 2009-11-06

	public PartCreateDelegate(Group parts, Hashtable partIBAMap,
			PublishLoadHelper helper) {
		this.myParts = parts;
		this.myPartIBAMap = partIBAMap;
		this.myHelper = helper;
	}

	public ResultReport process() {
		userLog("##Part");
		ResultReport result = new ResultReport();
		if (myParts == null || myParts.getElementCount() == 0) {
			log("û����Ҫ�������㲿��");
			return result;
		}
		StandardPartService ser = null;
		try {
			ser = (StandardPartService) EJBServiceHelper
					.getService("StandardPartService");
		} catch (QMException ex) {
			String msg = "ERROR:" + ex.getLocalizedMessage();
			errorLog(msg);
			errorLog(ex);
			return result;
		} catch (Exception ex) {

			String msg = "ERROR:" + ex.getLocalizedMessage();
			errorLog(msg);
			errorLog(ex);
			return result;
		}

		Enumeration enumeration = myParts.getElements();
		Element ele = null;
		String num = null;
		String name = null;
		String location = null;
		String partType = null;
		String source = null;
		String unit = null;
		String lifecycle = null;
		String creater = null;
		String modifier = null;
		String viewName = null;
		String gType = null;
		String sourceVersion = null;
		String maxCompIdUsed = null;
		//CCBegin by liunan 2008-10-28
		//�����������״̬�ķ�����
		String lifecyclestate = "";
		//CCEnd by liunan 2008-10-28
		//CCBegin by liunan 2009-03-18
		boolean flag = false;
		//CCEnd by liunan 2009-03-18
		//CCBegin by liunan 2009-12-07 ����Ƿ��и����ı�ʶ��
		//String isHasCont = ""; 
		//CCEnd by liunan 2009-12-07
		QMTransaction transaction = null;
		QMPartIfc part = null;
		boolean isgpart = false;
		int serNum = 0;
		log("�����㲿����Ϣ��");
		System.out.println("anan �����㲿����");
		Element node = null;
		while (enumeration.hasMoreElements()) {
			try {
				transaction = new QMTransaction();
				transaction.begin();
			} catch (QMException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			ele = (Element) enumeration.nextElement();
			num = ((String) ele.getValue("num")).trim();
			System.out.print("�����"+num);
			//CCBegin by liunan 2011-09-29 ȥ�������пո�
			//name = (String) ele.getValue("name");
			name = ((String) ele.getValue("name")).trim();
			//CCEnd by liunan 2011-09-29
			//CCBegin by liunan 2009-09-24 ��������к���Ӣ�ġ�;������ĳ����ġ�����
			name = name.replaceAll(";","��");
			//CCBegin by liunan 2009-09-24
			creater = (String) ele.getValue("creater");
			modifier = (String) ele.getValue("modify");
			gType = (String) ele.getValue("type");
			sourceVersion = (String) ele.getValue("sourceVersion");
			isgpart = gType.toLowerCase().trim().equals("genericpart");
			
			//CCBegin by liunan 2009-12-07 ����Ƿ��и����ı�ʶ��
			//isHasCont = (String) ele.getValue("isHasCont");
			//CCEnd by liunan 2009-12-07

		  //CCBegin by liunan 2008-10-28
	  	//�����������״̬�ķ�����
			lifecyclestate = (String) ele.getValue("lifecyclestate");
			//CCBegin by liunan 2009-03-18
			if(lifecyclestate.equals("ADVANCEPREPARE"))
			{
				flag = true;
			}
			//CCEnd by liunan 2009-03-18
			//System.out.println("�����㲿����������״̬======="+lifecyclestate);
			//ͨ�������ļ���ȡ��֮��Ӧ�Ľ����������״̬��
			lifecyclestate = myHelper.mapPro.getProperty("part.lifecyclestate." + lifecyclestate);
			//System.out.println("����㲿����������״̬======="+lifecyclestate);
		  //CCEnd by liunan 2008-10-28

			if (name == null || name.trim().length() == 0) {
				result.failureOne();
				String msg = "at save Part (num=" + num + " name=" + name
						+ ") ERROR: �㲿������Ϊ��";
				String s = num + "," + sourceVersion + "," + gType + ",ʧ��";
				result.addErroMsg(s);
				errorLog(msg);
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			if (num == null || num.trim().length() == 0) {
				result.failureOne();
				String msg = "at save Part (num=" + num + " name=" + name
						+ ") ERROR: �㲿�����Ϊ��";
				String s = num + "," + name + "," + sourceVersion + "," + gType
						+ ",ʧ��";
				result.addErroMsg(s);
				errorLog(msg);
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			myHelper.zong++;
			serNum++;
			// �ж�Ҫ�������㲿������岿����ϵͳ���Ƿ��Ѿ�����
			if (isgpart) {
				try {
					part = PublishHelper.getGPartInfoByNumber(num);
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (part != null) {
					try {
						transaction.rollback();
					} catch (QMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					log("���岿�� " + num + " �Ѵ���");
					userLog(serNum + "," + name + "," + num + "," + "�����㲿��"
							+ "," + sourceVersion + ","
							+ part.getVersionValue() + ",�ɹ�");
					this.myHelper.partsCannotStore.add(part.getPartNumber());
					//CCBegin by liunan 2011-04-22 �����Ѵ��ڵģ���ʾ�ɹ���������partVector����ӣ���ֹ�ṹ���ס�
					//myHelper.partVector.addElement(num);
					//CCEnd by liunan 2011-04-22
					result.successOne();
					myHelper.sucnum++;
					if (PublishHelper.VERBOSE)
						System.out.println("======>PartCreateDelegate,part: "
								+ part.getPartNumber()
								+ " exists!put into \"partsCannotStore\"!");
					continue;
				}
			} else {
				try {
					part = PublishHelper.getPartInfoByNumber(num);
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (part != null) {
					try {
						transaction.rollback();
					} catch (QMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					log("�㲿�� " + num + " �Ѵ���");
					userLog(serNum + "," + name + "," + num + "," + "�㲿��" + ","
							+ sourceVersion + "," + part.getVersionValue()
							+ ",�ɹ�");
					this.myHelper.partsCannotStore.add(part.getPartNumber());
					//CCBegin by liunan 2011-04-22 �����Ѵ��ڵģ���ʾ�ɹ���������partVector����ӣ���ֹ�ṹ���ס�
					//myHelper.partVector.addElement(num);
					//CCEnd by liunan 2011-04-22
					result.successOne();
					myHelper.sucnum++;
					if (PublishHelper.VERBOSE)
						System.out.println("======>PartCreateDelegate,part: "
								+ part.getPartNumber()
								+ " exists!put into \"partsCannotStore\"!");
					continue;
				}
			}
			partType = (String) ele.getValue("partType");
			source = (String) ele.getValue("source");
			unit = (String) ele.getValue("unit");
			location = (String) ele.getValue("path");
			if (PublishHelper.VERBOSE)
				System.out.println("=========>location ori: " + location);
			maxCompIdUsed = (String) ele.getValue("maxCompIdUsed");
			if(maxCompIdUsed == null)
			{
				maxCompIdUsed = Long.toString(1L);
			}
			if(location.equals("/Default"))
			{
				location = null;
			}
			if (location == null || location.trim().equals("")) {
				if (PublishHelper.VERBOSE)
					System.out.println("=========>location is null!");
				//location = DEFAULT_PART_LOCATION;
				location = myHelper.mapPro.getProperty("part.location.default");
				// ��ȡ�����ļ�����ȡ�ļ��ж�Ӧֵ
			} else {
				location=PublishHelper.cutPathString(location);
				if (location.startsWith("/")) {
					location = location.substring(1);
				}
				if (location.startsWith("Standard_part")) {
					location = location.substring(0, 13);
					String stand = location.substring(13);
					int l = stand.indexOf("/");
					if (l != -1 && l != 0) {
						location = location + stand.substring(0, l);
					}
				} else {
					int h = location.indexOf("/");
					int m = 0;
					if (h == -1) {
						m = location.indexOf("\\");
						if (m != -1) {
							location = location.substring(0, m);
						}
					} else {
						location = location.substring(0, h);

					}
				}
				if (PublishHelper.VERBOSE)
					System.out.println("=========>location after process: "
							+ location);
				if (isgpart) {
					location = myHelper.mapPro.getProperty("gpart.location."
							+ location, null);
		    	//CCBegin by liunan 2008-09-03
		    	//Ϊ��������ӿ��ء�
		    	if (PublishHelper.VERBOSE)
	    		//CCEnd by liunan 2008-09-03
					System.out.println("dddddddd");

					if (location == null) {
						location = myHelper.mapPro.getProperty(
								"gpart.location.default", "\\Root\\���ݽ�������\\�㲿��");
					}
				} else {
		    	//CCBegin by liunan 2008-09-03
		    	//Ϊ��������ӿ��ء�
		    	if (PublishHelper.VERBOSE)
		    	//CCEnd by liunan 2008-09-03
					System.out.println("gggggggggg");
					location = myHelper.mapPro.getProperty("part.location."
							+ location, null);
					if (location == null) {
						location = myHelper.mapPro.getProperty(
								"part.location.default", "\\Root\\���ݽ�������\\�㲿��");
					}
				}
			}
			if (PublishHelper.VERBOSE)
				System.out.println("=========>location after map: "
						+ PublishHelper.strDecoding(location, "ISO8859-1"));
			// location = PublishHelper.strDecoding(location, "ISO8859-1");
			//System.out.println("location==============="+location);
			location = PublishHelper.normalLocation(location);
			if (PublishHelper.VERBOSE)
				System.out.println("=========>location after normal: "
						+ location);
			lifecycle = (String) ele.getValue("lifecycle");
			lifecycle = myHelper.mapPro.getProperty("jfpublish.part.lifecycle",
					null);
			if (lifecycle == null || lifecycle.trim().equals("")) {
				lifecycle = PublishLoadHelper.DEFAULT_LIFECYCLE;
			} // else {
			// lifecycle = PublishHelper.strDecoding(lifecycle, "ISO8859-1");
			// }
			viewName = (String) ele.getValue("viewName");

			// ��ȡ�����ļ��������ͼ��Ӧֵ
			viewName = myHelper.mapPro.getProperty("part.viewName." + viewName);
			// viewName = PublishHelper.strDecoding(viewName, "ISO8859-1");

			Properties sourceProps = new Properties();
			if (myHelper.dataSource != null) {
				sourceProps.put("dataSource", myHelper.dataSource);
			}
			if (myHelper.publishDate != null) {
				sourceProps.put("publishDate", myHelper.publishDate);
			}
			if (sourceVersion != null) {
				sourceProps.put("sourceVersion", sourceVersion);
			}
			if (myHelper.publishForm != null) {
				sourceProps.put("publishForm", myHelper.publishForm);
			}
			if (myHelper.noteNum != null && myHelper.noteNum.length() > 0) {
				sourceProps.put("noteNum", myHelper.noteNum);
			}
			if (creater != null) {
				sourceProps.put("creater", creater);
			}
			if (modifier != null) {
				sourceProps.put("modifier", modifier);
			}
			System.out.println("anantt create   �㲿�� "+num+" and sourceVersion=="+ (String) ele.getValue("sourceVersion")+"  and  sourceProps.get==="+sourceProps.get("sourceVersion"));//anan
			try {
				if (!isgpart) {
					part = new QMPartInfo();
				} else {
					part = new GenericPartInfo();
					((GenericPartInfo) part).setMaxCompIdUsed(Long.valueOf(
							maxCompIdUsed).longValue());
					String logicBase = (String) ele.getValue("logicBase");
					//CCBegin by liunan 2010-01-20 ��ֹlogicbaseΪnull��
					if(logicBase==null)
					{
						logicBase = "";
					}
					//CCEnd by liunan 2010-01-20
					
					//CCBegin by liunan 2009-11-06 ��Ӷ�logicbase�ı��档
					try 
					{
						String path = LOGICBASE_PATH+num+"-"+logDateFormat.format(new java.util.Date())+".xml";
						FileWriter writer = new FileWriter(path);
						writer.write(logicBase);
						writer.flush();
						writer.close();
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					//CCEnd by liunan 2009-11-06
					
					LogicBase lb = LogicBaseConversion
							.xmlToLogicBase(logicBase);
					if (lb != null) {
						((GenericPartInfo) part).setLogicBase(lb);
					}
				}
				part.setPartName(name);
				part.setPartNumber(num);

		    //CCBegin by liunan 2008-10-28
	    	//���÷����������㲿��״̬�������ǽ����ġ���׼����
	    	//Դ�����£�
				//part.setLifeCycleState(State.toState("PREPARING"));//����״̬����Ҫ�ĳɽ�ŷ�����״̬�ˡ�
				//CCBegin by liunan 2009-12-23 �޸�״̬�ı�ʾ��ʽ��
				part.setLifeCycleState(LifeCycleState.toLifeCycleState(lifecyclestate));
				//CCEnd by liunan 2009-12-23
				//CCEnd by liunan 2008-10-28
				//CCBegin SS1
				viewName = "���������ͼ";
				//CCEnd SS1
				// ��ͼ����
				if (viewName != null && PublishHelper.isHasView(viewName)) {
					part.setViewName(viewName);

				} else {
					part.setViewName(PublishLoadHelper.DEFAULT_VIEW);
				}
				//
				// ����Ĭ�ϵ�λ(ʹ�÷�ʽ)
				if (unit == null || unit.trim().length() == 0) {
					part.setDefaultUnit(Unit.getUnitDefault());
				} else {
					// ��ȡ�����ļ������Ĭ�ϵ�λ��ʹ�÷�ʽ����Ӧֵ

					unit = myHelper.mapPro.getProperty("part.unit." + unit,
							null);

					if (unit == null) {
						unit = myHelper.mapPro.getProperty("part.unit.default",
								null);

					}
					Unit unitT = Unit.toUnit(unit);
					if (unitT == null) {
						part.setDefaultUnit(Unit.getUnitDefault());
					} else {

						part.setDefaultUnit(unitT);
					}
				}
				// ������Դ
				if (source == null || source.trim().length() == 0) {

					// ��ȡ�����ļ��������Դ�Ķ�Ӧֵ
					part.setProducedBy(ProducedBy.getProducedByDefault());
				} else {
					source = myHelper.mapPro.getProperty("part.source."
							+ source);
					ProducedBy produce = ProducedBy.toProducedBy(source);
					if (produce == null) {
						part.setProducedBy(ProducedBy.getProducedByDefault());
					} else {
						part.setProducedBy(produce);
					}
				}

				// ��������
				if (partType == null || partType.trim().length() == 0) {
					part.setPartType(QMPartType.getQMPartTypeDefault());
				} else {
					partType = myHelper.mapPro.getProperty("part.parttype."
							+ partType);
					// partType = PublishHelper.strDecoding(partType,
					// "ISO8859-1");
					QMPartType type = QMPartType.toQMPartType(partType);
					if (type == null) {
						part.setPartType(QMPartType.getQMPartTypeDefault());
					} else {
						part.setPartType(type);
					}
				}
				if (myHelper.user != null) {
					part.setIterationCreator(myHelper.user.getBsoID());
					part.setIterationModifier(myHelper.user.getBsoID());
					//CCBegin by liunan 2009-03-18
					//����ǰ׼״̬��������Ҫ��ǡ�
					/*if(flag)
					{
						 part.setIterationNote("ǰ׼");
					}*/
					//CCEnd by liunan 2011-04-26
					//CCEnd by liunan 2009-03-18
					//CCBegin by liunan 2008-10-08
					//�޸��㲿������ʱ����owner�ֶε����⡣ע�͵����´��롣
					//part.setOwner(myHelper.user.getBsoID());
					part.setOwner("");
					//CCEnd by liunan 2008-10-08
				}
				part = (QMPartInfo) PublishHelper.assignFolder(part, location);
				String lifeCyID = PublishHelper.getLifeCyID(lifecycle);
				part.setLifeCycleTemplate(lifeCyID);
				part = (QMPartInfo) PublishHelper.setPublishSourceInfoByIBA(
						(IBAHolderIfc) part, sourceProps);
				// ����IBAֵ����
				part = (QMPartInfo) PublishHelper.setIBAValues(
						(IBAHolderIfc) part, (Vector) myPartIBAMap.get(part
								.getPartNumber()));

				// �־û�����
				part = (QMPartInfo) ser.savePart(part);
				transaction.commit();
				
				//CCBegin by liunan 2009-12-07 �޸Ľ��ͳ�Ƶ����ݣ����Ӹ�������Ϣ��
				//String temp = isHasCont.trim().equals("true") ? "�и���" : "�ɹ�" ;
				String temp = "�ɹ�";
				if (isgpart) {
					log("�������岿�� " + num + " �ɹ�");
					userLog(serNum + "," + name + "," + num + "," + "�����㲿��"
							+ "," + sourceVersion + ","
							+ part.getVersionValue() + "," + temp);
				} else {
					log("�����㲿�� " + num + " �ɹ�");
					userLog(serNum + "," + name + "," + num + "," + "�㲿��" + ","
							+ sourceVersion + "," + part.getVersionValue()
							+ "," + temp);
				}
				//CCEnd by liunan 2009-12-07
				myHelper.partVector.addElement(num);
				result.addSuccessMsg(num + "," + name + "," + sourceVersion
						+ "," + gType + ",�ɹ�");
				// String s = num + "," + name + "," + sourceVersion + "," +
				// gType
				// + ",�ɹ�";
				result.successOne();
				myHelper.sucnum++;
				// displayPart(part);
			} catch (QMException ex) {
				ex.printStackTrace();
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.myHelper.partsCannotStore.add(num);
				this.myHelper.failedParts.add(num);
				this.myHelper.logErrorPart(num);// ��δ�ܳɹ��������㲿����¼��������־��
				result.failureOne();
				String msg = "at save Part (num=" + num + ") ERROR:"
						+ ex.getClientMessage();
				String s = num + "," + name + "," + sourceVersion + "," + gType
						+ ",ʧ��";
				result.addErroMsg(s);
				errorLog(msg);
				if (isgpart) {
					userLog(serNum + "," + name + "," + num + "," + "�����㲿��"
							+ "," + sourceVersion + "," + " " + ",ʧ��" + ","
							+ ex.getClientMessage());
				} else {
					userLog(serNum + "," + name + "," + num + "," + "�㲿��" + ","
							+ sourceVersion + "," + " " + ",ʧ��" + ","
							+ ex.getClientMessage());
					// cache
					// partMap.put(part.getPartNumber(), part);
				}
				errorLog(ex);
				ex.printStackTrace();
			} catch (Exception ex) {
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.myHelper.partsCannotStore.add(num);
				this.myHelper.failedParts.add(num);
				this.myHelper.logErrorPart(num);// ��δ�ܳɹ��������㲿����¼��������־��
				result.failureOne();
				String msg = "at save Part (num=" + num + ") ERROR:"
						+ ex.getLocalizedMessage();
				String s = num + "," + name + "," + sourceVersion + "," + gType
						+ ",ʧ��";
				result.addErroMsg(s);
				errorLog(msg);
				if (isgpart) {
					userLog(serNum + "," + name + "," + num + "," + "�����㲿��"
							+ "," + sourceVersion + "," + " " + ",ʧ��" + ","
							+ ex.getLocalizedMessage());
				} else {
					userLog(serNum + "," + name + "," + num + "," + "�㲿��" + ","
							+ sourceVersion + "," + " " + ",ʧ��" + ","
							+ ex.getLocalizedMessage());
					// cache
					// partMap.put(part.getPartNumber(), part);
				}

				errorLog(ex);
			}
			System.out.println("  ������");
			// debugInfo("**************************************************************************
			// end");
		} // end while
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
