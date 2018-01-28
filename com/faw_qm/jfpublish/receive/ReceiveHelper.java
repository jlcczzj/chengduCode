package com.faw_qm.jfpublish.receive;
//SS1 �������ķ��㲿�����������������ͼ ������ 2014-7-8
import java.util.Collection;
import java.util.Iterator;

import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.model.Att;
import com.faw_qm.jfpublish.receive.RelationsDelegate;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.epm.build.model.EPMBuildHistoryInfo;
import com.faw_qm.epm.build.model.EPMBuildLinksRuleInfo;
import com.faw_qm.epm.epmdocument.model.EPMDocumentInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.persist.ejb.service.PersistService;


public class ReceiveHelper 
{
	public String saveEPMDocPartLinks(String partNum, String docNum) throws QMException
	{
		System.out.println("##ReceiveHelper.saveEPMDocPartLinks");

		VersionControlService versiobSer = (VersionControlService) PublishHelper.getEJBService("VersionControlService");
		QMTransaction transaction = null;
		EPMDocumentInfo doc = null;
		String docType = null;
			try {
				transaction = new QMTransaction();
				transaction.begin();

				QMPartMasterIfc partMaster = PublishHelper
						.getPartMasterByNumber(partNum);
				if (partMaster == null) {
					System.out.println("at save �㲿����EPM�ĵ����� �㲿��(partNum=" + partNum + ") is null ");
					transaction.rollback();
					return "at save �㲿����EPM�ĵ����� �㲿��(partNum=" + partNum + ") is null ";
				}
				QMPartInfo part = null;
				Collection coll = (Collection) versiobSer.allVersionsOf(partMaster);
				// ���master�µ�����С�汾�������°������У�
				// ���һ��Ԫ�ؼ�Ϊ���°汾������㲿������
				Iterator iterator = coll.iterator();
				while (iterator.hasNext()) 
				{
					part = (QMPartInfo) iterator.next();
					//CCBegin SS1
					//if (part.getViewName() != null && part.getViewName().trim().equals("������ͼ")) 
					if (part.getViewName() != null && part.getViewName().trim().equals("���������ͼ"))
						//CCEnd SS1
					{
						break;
					}
				}

				if (part == null) {
					System.out.println("at save �㲿����EPM�ĵ����� �㲿��(partNum=" + partNum + ") is null ");
					transaction.rollback();
					return "at save �㲿����EPM�ĵ����� �㲿��(partNum=" + partNum + ") is null ";
				}

				doc = PublishHelper.getEPMDocInfoByNumber(docNum);

				if (doc == null) {
					System.out.println("at save �㲿����EPM�ĵ����� EPM�ĵ�(docNum=" + docNum + ") is null ");
					transaction.rollback();
					return "at save �㲿����EPM�ĵ����� EPM�ĵ�(docNum=" + docNum + ") is null ";
				}
				docType = doc.getAuthoringApplication().getDisplay();
				// ����Ƿ���ڹ�����ϵ
				//CCBegin by liunan 2009-11-09 �ĳ�ͨ��EPMBuildHistory�������ҹ����Ƿ���ڡ�
				//Դ����
				//if (PublishHelper.hasBuildLinkRule(part, doc)) {
				if (PublishHelper.hasBuildHistory(part, doc)) 
				{
					System.out.println("at save �㲿����EPM�ĵ����� (partNum=" + partNum + " docNum=" + docNum + " ) ERROR:�����Ѿ�����,����");
					transaction.rollback();
					return "at save �㲿����EPM�ĵ����� (partNum=" + partNum + " docNum=" + docNum + " ) ERROR:�����Ѿ�����,����";
				}

				// ����EPM�������㲿������֮��Ĺ�����ϵ
				
				PersistService ps = (PersistService) PublishHelper.getEJBService("PersistService");
				if (!PublishHelper.hasBuildLinkRule(part, doc)) 
				{
					EPMBuildLinksRuleInfo tempRule = new EPMBuildLinksRuleInfo(doc, part);
					tempRule.setSourceInstance(doc.getFamilyInstance());
					tempRule.setDescription("Build rule for (generic) instance:" + doc.getDocName());
					ps.saveValueInfo(tempRule);
				}
				//CCEnd by liunan 2009-11-09
				
				EPMBuildHistoryInfo buildHistory = new EPMBuildHistoryInfo();
				buildHistory.setLeftBsoID(doc.getBsoID());
				buildHistory.setRightBsoID(part.getBsoID());
				ps.saveValueInfo(buildHistory);
				transaction.commit();
				System.out.println("����EPM�ĵ���" + docNum + "�����㲿����" + partNum + "����������ϵ�ɹ�");
				return "����EPM�ĵ���" + docNum + "�����㲿����" + partNum + "����������ϵ�ɹ�";

			} catch (QMException ex) {
				System.out.println("at save PartDescribeLink (partNum=" + partNum
						+ " docNum=" + docNum + ") ERROR:"
						+ ex.getLocalizedMessage());
				transaction.rollback();
				return "at save PartDescribeLink (partNum=" + partNum
						+ " docNum=" + docNum + ") ERROR:"
						+ ex.getLocalizedMessage();
			} catch (Exception ex) {
				System.out.println("at save PartDescribeLink (partNum=" + partNum
						+ " docNum=" + docNum + ") ERROR:"
						+ ex.getLocalizedMessage());
				transaction.rollback();
				return "at save PartDescribeLink (partNum=" + partNum
						+ " docNum=" + docNum + ") ERROR:"
						+ ex.getLocalizedMessage();
			}
	}
}
