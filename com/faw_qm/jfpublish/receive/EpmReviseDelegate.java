/**
 * SS1 ��Ψһ��������С�汾�ȴ����㷢��ʧ�ܣ�������������з����� liunan 2014-8-18
 */


package com.faw_qm.jfpublish.receive;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentItemIfc;
import com.faw_qm.content.model.URLDataInfo;
import com.faw_qm.epm.epmdocument.model.EPMDocumentInfo;
import com.faw_qm.epm.epmdocument.model.EPMDocumentMasterInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.iba.definition.litedefinition.BooleanDefView;
import com.faw_qm.iba.definition.litedefinition.FloatDefView;
import com.faw_qm.iba.definition.litedefinition.IntegerDefView;
import com.faw_qm.iba.definition.litedefinition.RatioDefView;
import com.faw_qm.iba.definition.litedefinition.StringDefView;
import com.faw_qm.iba.definition.litedefinition.TimestampDefView;
import com.faw_qm.iba.definition.litedefinition.URLDefView;
import com.faw_qm.iba.definition.litedefinition.UnitDefView;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.BooleanValueDefaultView;
import com.faw_qm.iba.value.litevalue.FloatValueDefaultView;
import com.faw_qm.iba.value.litevalue.IntegerValueDefaultView;
import com.faw_qm.iba.value.litevalue.RatioValueDefaultView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.StringValueDefaultView;
import com.faw_qm.iba.value.litevalue.TimestampValueDefaultView;
import com.faw_qm.iba.value.litevalue.URLValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.VersionedIfc;
//CCBegin by liunan 2008-10-23
import java.util.StringTokenizer;
import com.faw_qm.version.model.IteratedIfc;
//CCEnd by liunan 2008-10-23


public class EpmReviseDelegate extends AbstractStoreDelegate {

	private Hashtable myEpmIBA = null;

	private Group myEpmGrp = null;

	public EpmReviseDelegate(Group epmsGrp, Hashtable epmIBA,
			PublishLoadHelper helper) {
		this.myEpmGrp = epmsGrp;
		epmsGrp.toXML(new PrintWriter(System.out, true), true);
		this.myEpmIBA = epmIBA;
		this.myHelper = helper;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public ResultReport process() {
		userLog("##ReviseEPM");
		ResultReport result = new ResultReport();
		IBAValueService ibaService = null;
		try {
			ibaService = (IBAValueService) PublishHelper
					.getEJBService("IBAValueService");
		} catch (QMException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		EPMDocumentInfo epmdoc = null;
		Element epmEle = null;
		String epmNum = null;
		String epmName = null;
		String version = null;
		String modifier = null;
		Properties sourceProps = null;
		DefaultAttributeContainer attc = null;
		AbstractValueView[] valueview = null;
		AbstractValueIfc value = null;
		int sernum = 0;
		String fbsourversion = "";
		String prosourceversion = "";
		// ���޶���part�Ĺ�����Ӧ��EPM���޶��򷢲�
		if (myEpmGrp == null || myEpmGrp.getElementCount() == 0) {
			log("û����Ҫ�޶���EPM�ĵ���");
			// userLog("û����Ҫ�޶���EPM�ĵ���");
			return result;
		} else {
			Enumeration eum = myEpmGrp.getElements();
			// userLog("�޶�EPM��Ϣ");
			QMTransaction transaction = null;
			while (eum.hasMoreElements()) {
				sernum++;

				try {
					transaction = new QMTransaction();
                                        transaction.begin();
                                //CCBegin by liunan 2008-10-22
                                //������޶���������С�汾�ı�ʶ��true��ʶ�޶���false��ʾ����С�汾��
                                boolean isReviseOrUp = true;
                                //CCEnd by liunan 2008-10-22

					epmEle = (Element) eum.nextElement();
					epmNum = ((String) epmEle.getValue("num")).trim();
					epmName = ((String) epmEle.getValue("name"));
					version = (String) epmEle.getValue("sourceVersion");
					modifier = (String) epmEle.getValue("modify");

					// �޶�EPM�ĵ�

					epmdoc = PublishHelper.getEPMDocInfoByNumber(epmNum);
					prosourceversion = epmdoc.getVersionValue();
					epmdoc = (EPMDocumentInfo) ibaService
							.refreshAttributeContainerWithoutConstraints(epmdoc);
					attc = (DefaultAttributeContainer) epmdoc
							.getAttributeContainer();
					valueview = attc.getAttributeValues();
					int m = valueview.length;
					for (int i = 0; i < m; i++) {
						if ((valueview[i].getDefinition().getName())
								.toLowerCase().trim().equals("sourceversion")) {
							// flag = true;
							value = IBAValueObjectsFactory.newAttributeValue(
									valueview[i], epmdoc);
							fbsourversion = ((StringValueIfc) value).getValue();
						}
					}
			  	//CCBegin by liunan 2008-10-23
          //�޸���ǰ�İ汾�ȽϷ������޷���ȷ�Ƚϳ�AA��B��C��D�Ȱ汾�Ĵ�С��
          //���ò�Ʒ�а汾������õİ汾�ȽϷ�����
	  			//if (!(version.compareTo(fbsourversion) > 0)) {
          if(!getPublishFlag(version,fbsourversion))
          {
          //CCEnd by liunan 2008-10-23
						try {
							transaction.rollback();
						} catch (QMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						log("epm�ĵ� " + epmNum + "����Ҫ�޶�");
						userLog(sernum + "," + epmdoc.getDocName() + ","
								+ epmNum + "," + fbsourversion + "," + version
								+ "," + prosourceversion + ","
								+ epmdoc.getVersionValue() + ",�ɹ�");
						result.successOne();
						continue;
                                              }
                                              //CCBegin by liunan 2008-10-22
                                              //�ߵ��˴�����ʾ���㲿����Ҫ����������С�Ȼ��Ƚ�������汾�����һ����ʾ�㲿��������С�汾���������޶�������
                                              if(version.substring(0,version.indexOf(".")).equals(fbsourversion.substring(0,fbsourversion.indexOf("."))))
                                              {
                                                      isReviseOrUp = false;
                                              }
                                              //CCEnd by liunan 2008-10-22

					//CCBegin by liunan 2008-10-23
					//�ߵ��˴�����ʾ���㲿����Ҫ����������С�Ȼ��Ƚ�������汾�����һ����ʾ�㲿��������С�汾���������޶�������
					if(isReviseOrUp)
                                        {
                                                epmdoc = (EPMDocumentInfo) ((VersionControlService) PublishHelper
                                                        .getEJBService("VersionControlService"))
                                                        .newVersion((VersionedIfc) epmdoc);
					}
					else
                                        {
                                                EPMDocumentInfo iterationEpmDocTemp = (EPMDocumentInfo) ((VersionControlService) PublishHelper
                                                    .getEJBService("VersionControlService"))
                                                    .newIteration((IteratedIfc) epmdoc);
                                                epmdoc = (EPMDocumentInfo) ((VersionControlService) PublishHelper
                                                    .getEJBService("VersionControlService"))
                                                    .supersede((IteratedIfc) epmdoc,(IteratedIfc) iterationEpmDocTemp);
					}
					//CCEnd by liunan 2008-10-23
					String lifecycle = myHelper.mapPro.getProperty(
							"jfpublish.epm.lifecycle", null);

					if (lifecycle == null || lifecycle.trim().equals("")) {
						lifecycle = PublishLoadHelper.DEFAULT_LIFECYCLE;
					} // else {
					// lifecycle = PublishHelper.strDecoding(lifecycle,
					// "ISO8859-1");
					// }
					String lifeCyID = PublishHelper.getLifeCyID(lifecycle);

					epmdoc.setLifeCycleTemplate(lifeCyID);

					// ����EPM
					epmdoc
							.setAttributeContainer(new DefaultAttributeContainer());
					PersistService ps = (PersistService) PublishHelper
							.getEJBService("PersistService");
					epmdoc = (EPMDocumentInfo) ps.saveValueInfo(epmdoc);
					// ������Ƹı���������
					if (!epmdoc.getDocName().equals(epmName)) {
						EPMDocumentMasterInfo info = (EPMDocumentMasterInfo) epmdoc
								.getMaster();
						info.setDocName(epmName);
						ps.updateValueInfo(info);
					}

					Vector vector = new Vector();
					if (myEpmIBA.containsKey(epmNum)) {
						vector = (Vector) myEpmIBA.get(epmNum);

					}

					sourceProps = new Properties();
					if (myHelper.dataSource != null
							&& myHelper.dataSource.length() > 0) {
						sourceProps.put("dataSource", myHelper.dataSource);
					}
					if (myHelper.publishDate != null
							&& myHelper.publishDate.length() > 0) {
						sourceProps.put("publishDate", myHelper.publishDate);
					}
					if (version != null && version.length() > 0) {
						sourceProps.put("sourceVersion", version);
					}
					if (myHelper.publishForm != null
							&& myHelper.publishForm.length() > 0) {
						sourceProps.put("publishForm", myHelper.publishForm);
					}
					if (modifier != null && modifier.length() > 0) {
						sourceProps.put("modifier", modifier);
					}
					if (myHelper.noteNum != null
							&& myHelper.noteNum.length() > 0) {
						sourceProps.put("noteNum", myHelper.noteNum);
					}
					epmdoc = (EPMDocumentInfo) updateIBA((IBAHolderIfc) epmdoc,
							sourceProps, vector);
					removeContents(epmdoc);
					transaction.commit();
					//CCBegin by liunan 2008-12-11 ������Ϣʱ�����޶�����С�汾������
					log("�޶�epm�ĵ� " + epmNum + " �ɹ�");
                                        if(isReviseOrUp)
					    userLog(sernum + "," + epmdoc.getDocName() + "," + epmNum
							+ "," + fbsourversion + "," + version + ","
							+ prosourceversion + "," + epmdoc.getVersionValue()
							+ ",�ɹ�");
                                        else
                                          userLog(sernum + "," + epmdoc.getDocName() + "," + epmNum
                                                      + "," + fbsourversion + "," + version + ","
                                                      + prosourceversion + "," + epmdoc.getVersionValue()
                                                      + ",С�汾����");
          //CCEnd by liunan 2008-12-11
					result.successOne();

				}

				catch (QMException e) {
					try {
						transaction.rollback();
					} catch (QMException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String msg = "�޶�EPM�ĵ� " + epmNum + " ʧ��";
					errorLog(msg);
					errorLog(e.getLocalMessage());
					e.printStackTrace();
					
					
					//CCBegin SS1
					boolean suflag = false;
					//CCEnd SS1
					
					//CCBegin by liunan 2011-10-27 ���Ρ�������������������������쳣
					//���쳣������ͬʱ�����ļ�������д�����ͬ��Ҫ�����Ľṹ������һ���������ˣ�
					//��һ�����޷��޸�ʱ�������쳣��ʵ���Ѿ������ɹ������޸��á��������ݷ������ͳ��.html��
					//�в�����ʾ���쳣��������ʾ�ɹ���
					//�� ���֡��������쳣�����м�¼�����ɹ���������
					if(msg.indexOf("�����������������������")>0)
					{
						result.successOne();
						//CCBegin SS1
						suflag = true;
						//CCEnd SS1
					}
					//����������Ϣ�������Ѿ��޶����������°汾��
					if(msg.indexOf("�����Ѿ��޶����������°汾")>0)
					{
						result.successOne();
						//CCBegin SS1
						suflag = true;
						//CCEnd SS1
					}
					else
					{
						result.failureOne();
					}
					//CCEnd by liunan 2011-10-27
					
					//CCBegin SS1
					if(msg.indexOf("������С�汾�Ķ���")!=-10)
					{
						result.successOne();
						//CCBegin SS1
						suflag = true;
						//CCEnd SS1
					}
					else
					{
						result.failureOne();
					}
					//CCEnd SS1
					
					userLog(sernum + "," + epmdoc.getDocName() + "," + epmNum
							+ "," + fbsourversion + "," + version + ","
							//CCBegin SS1
							//+ prosourceversion + "," + " " + ",ʧ��" + ","
							+ prosourceversion + "," + " " + (suflag?",�ɹ�":",ʧ��") + ","
							//CCEnd SS1
							+ e.getClientMessage());
				} catch (Exception e) {
					try {
						transaction.rollback();
					} catch (QMException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String msg = "�޶�EPM�ĵ� " + epmNum + " ʧ��";
					errorLog(msg);
					errorLog(e.getLocalizedMessage());
					e.printStackTrace();
					result.failureOne();
					userLog(sernum + "," + epmdoc.getDocName() + "," + epmNum
							+ "," + fbsourversion + "," + version + ","
							+ prosourceversion + "," + " " + ",ʧ��" + ","
							+ e.getLocalizedMessage());

				}
			}
		}
		return result;

	}

	private void removeContents(EPMDocumentInfo epmdoc) throws QMException {
		ContentService cs = (ContentService) PublishHelper
				.getEJBService("ContentService");
		Vector v = cs.getContents(epmdoc);
		ContentItemIfc item;
		for (Iterator iter = v.iterator(); iter.hasNext();) {
			item = (ContentItemIfc) iter.next();
			if (item instanceof ApplicationDataInfo) {
				cs.deleteApplicationData(epmdoc, (ApplicationDataInfo) item);
			} else {
				cs.deleteURLData(epmdoc, (URLDataInfo) item);
			}
		}
	}

	/**
	 * ����IBAֵ,����������
	 *
	 * @param holder
	 * @param url
	 * @param group
	 * @return
	 * @throws java.lang.Exception
	 */
	private IBAHolderIfc updateIBA(IBAHolderIfc holder, Properties props,
			Vector vector) throws QMException {
		if (holder == null) {
			return holder;
		}
		// �������Գ����ߡ�
		holder = ((IBAValueService) PublishHelper
				.getEJBService("IBAValueService")).refreshAttributeContainer(
				holder, null, null, null);
		// ����������
		DefaultAttributeContainer container = (DefaultAttributeContainer) holder
				.getAttributeContainer();
		container.setConstraintGroups(new Vector());
		AbstractValueView aValueView[] = container.getAttributeValues();
		// ɾ��������ԭ�е�����ֵ��
		for (int i = 0; aValueView != null && i < aValueView.length; i++) {
			if (aValueView[i] instanceof ReferenceValueDefaultView) {
				ReferenceValueDefaultView referenceValue = (ReferenceValueDefaultView) aValueView[i];
				if (referenceValue.getLiteIBAReferenceable() != null
						&& referenceValue.getLiteIBAReferenceable()
								.getReferenceID().indexOf("Ranking") == -1) {
					container.deleteAttributeValue(aValueView[i]);
				}
			} else if (aValueView[i] instanceof IntegerValueDefaultView) {
				IntegerValueDefaultView integerValue = (IntegerValueDefaultView) aValueView[i];
				if (integerValue.getReferenceValueDefaultView() == null) {
					container.deleteAttributeValue(aValueView[i]);
				}
			} else {
				container.deleteAttributeValue(aValueView[i]);
			}
		}

		// ��ӷ�����������ֵ��
		if (myHelper.saveSourceInfoByIBA) {
			holder = PublishHelper.setPublishSourceInfoByIBA(holder, props);
			container = (DefaultAttributeContainer) holder
					.getAttributeContainer();

		}
		// ���ԭ������ֵ��
		if (vector != null) {
			for (int i = 0; i < vector.size(); i++) {
				if (vector.get(i) instanceof BooleanValueDefaultView) {
					BooleanValueDefaultView booleanValue = (BooleanValueDefaultView) vector
							.get(i);
					BooleanValueDefaultView booleanValue1 = new BooleanValueDefaultView(
							(BooleanDefView) booleanValue.getDefinition(),
							booleanValue.isValue());
					if (booleanValue.getReferenceValueDefaultView() != null) {
						booleanValue1.setReferenceValueDefaultView(booleanValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(booleanValue1);
				} else if (vector.get(i) instanceof FloatValueDefaultView) {
					FloatValueDefaultView floatValue = (FloatValueDefaultView) vector
							.get(i);
					FloatValueDefaultView floatValue1 = new FloatValueDefaultView(
							(FloatDefView) floatValue.getDefinition(),
							floatValue.getValue(), floatValue.getPrecision());
					if (floatValue.getReferenceValueDefaultView() != null) {
						floatValue1.setReferenceValueDefaultView(floatValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(floatValue1);
				} else if (vector.get(i) instanceof IntegerValueDefaultView) {
					IntegerValueDefaultView integerValue = (IntegerValueDefaultView) vector
							.get(i);
					IntegerValueDefaultView integerValue1 = new IntegerValueDefaultView(
							(IntegerDefView) integerValue.getDefinition(),
							integerValue.getValue());
					if (integerValue.getReferenceValueDefaultView() == null) {
						container.addAttributeValue(integerValue1);
					}
				} else if (vector.get(i) instanceof RatioValueDefaultView) {
					RatioValueDefaultView ratioValue = (RatioValueDefaultView) vector
							.get(i);
					RatioValueDefaultView ratioValue1 = new RatioValueDefaultView(
							(RatioDefView) ratioValue.getDefinition(),
							ratioValue.getValue(), ratioValue.getDenominator());
					if (ratioValue.getReferenceValueDefaultView() != null) {
						ratioValue1.setReferenceValueDefaultView(ratioValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(ratioValue1);
				} else if (vector.get(i) instanceof ReferenceValueDefaultView) {
					ReferenceValueDefaultView referenceValue1 = (ReferenceValueDefaultView) vector
							.get(i);
					if (referenceValue1.getLiteIBAReferenceable() != null
							&& referenceValue1.getLiteIBAReferenceable()
									.getReferenceID().indexOf("Ranking") == -1) {
						container.setConstraintParameter("CSM");
						container.addAttributeValue(referenceValue1);
					}
				} else if (vector.get(i) instanceof StringValueDefaultView) {
					StringValueDefaultView stringValue = (StringValueDefaultView) vector
							.get(i);
					StringValueDefaultView stringValue1 = new StringValueDefaultView(
							(StringDefView) stringValue.getDefinition(),
							stringValue.getValue());
					if (stringValue.getReferenceValueDefaultView() != null) {
						stringValue1.setReferenceValueDefaultView(stringValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(stringValue1);
				} else if (vector.get(i) instanceof TimestampValueDefaultView) {
					TimestampValueDefaultView timestampValue = (TimestampValueDefaultView) vector
							.get(i);
					TimestampValueDefaultView timestampValue1 = new TimestampValueDefaultView(
							(TimestampDefView) timestampValue.getDefinition(),
							timestampValue.getValue());
					if (timestampValue.getReferenceValueDefaultView() != null) {
						timestampValue1
								.setReferenceValueDefaultView(timestampValue
										.getReferenceValueDefaultView());
					}
					container.addAttributeValue(timestampValue1);
				} else if (vector.get(i) instanceof UnitValueDefaultView) {
					UnitValueDefaultView unitValue = (UnitValueDefaultView) vector
							.get(i);
					UnitValueDefaultView unitValue1 = new UnitValueDefaultView(
							(UnitDefView) unitValue.getDefinition(), unitValue
									.getValue(), unitValue.getPrecision());
					if (unitValue.getReferenceValueDefaultView() != null) {
						unitValue1.setReferenceValueDefaultView(unitValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(unitValue1);
				} else if (vector.get(i) instanceof URLValueDefaultView) {
					URLValueDefaultView urlValue = (URLValueDefaultView) vector
							.get(i);
					URLValueDefaultView urlValue1 = new URLValueDefaultView(
							(URLDefView) urlValue.getDefinition(), urlValue
									.getValue(), urlValue.getDescription());
					if (urlValue.getReferenceValueDefaultView() != null) {
						urlValue1.setReferenceValueDefaultView(urlValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(urlValue1);
				}
			}
		}

		holder.setAttributeContainer(container);
		container = (DefaultAttributeContainer) ((IBAValueService) PublishHelper
				.getEJBService("IBAValueService")).updateAttributeContainer(
				holder, container != null ? container.getConstraintParameter()
						: null, null, null);

		holder.setAttributeContainer(container);
		return holder;
	}

        /**
         * �Ƚ������汾�Ĵ�С�������Ƿ񷢲��˶���
         * @param s1 String �������°汾��
         * @param s2 String ���pdm�м�¼�ĸö����Ӧ���еİ汾��
         * @return boolean ��Ҫ����ʱ����true�����򷵻�false��
         * @author liunan 2008-10-23
         */
        private boolean getPublishFlag(String s1, String s2)
        {
          if(s1.indexOf(".")<0)
          {
            s1 = s1 + ".1";
          }
          if (s1.equals(s2)) {
            return false;
          }

          //����StringTokenizer�ָ�st1
          StringTokenizer st1 = new StringTokenizer(s1, ".");
          //����StringTokenizer�ָ�st2
          StringTokenizer st2 = new StringTokenizer(s2, ".");
          int level1 = st1.countTokens();
          int level2 = st2.countTokens();
          //�����汾ֵ���ȴ�Ϊ���µ�
          if (level1 < level2) {
            return false;
          }
          if (level1 > level2) {
            return true;
          }
          String[] sarray1 = new String[level1];
          String[] sarray2 = new String[level2];
          //�ѷָ����ִ��������ַ�������
          for (int i = 0; i < level1; i++) {
            sarray1[i] = st1.nextToken();
            sarray2[i] = st2.nextToken();
          }
          //��ѭ���бȽ������е��ִ�
          for (int k = 0; k < level1; k++) {
            if (sarray1[k].length() > sarray2[k].length()) {
              return true;
            }
            if (sarray1[k].length() < sarray2[k].length()) {
              return false;
            }
            if (sarray1[k].compareTo(sarray2[k]) < 0) {
              return false;
            }
            if (sarray1[k].compareTo(sarray2[k]) > 0) {
              return true;
            }
          }
          return false;
        }

}
