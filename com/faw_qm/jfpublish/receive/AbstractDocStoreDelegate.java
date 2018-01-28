package com.faw_qm.jfpublish.receive;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import com.faw_qm.affixattr.ejb.service.AffixAttrService;
import com.faw_qm.affixattr.model.AttrContainerInfo;
import com.faw_qm.affixattr.model.AttrDefineInfo;
import com.faw_qm.affixattr.model.AttrRestictInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.util.QMProperties;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;

public abstract class AbstractDocStoreDelegate extends AbstractStoreDelegate {

    protected Group myDocs = null;

    // �ĵ��ü�¼����Դ��Ϣ����չ���塢�������塢Լ������cache ���Զ�����Ϊkey ���Զ���Ϊvalue
    public HashMap affixAttrDefMap = new HashMap();

    public static HashMap affixAttrConMap = new HashMap();

    public static HashMap affixAttrResMap = new HashMap();

    public static String Default_DocAffixAttrConName = "com.faw_qm.doc.ejb.entity.Doc";

    public static String Default_DocAffixAttrResName = "sourceResDef";

    public static String Default_DocAffixAttrDefName_noteNum = "noteNumber";

    public static String Default_DocAffixAttrDefName_dataSource = "dataSource";

    public static String Default_DocAffixAttrDefName_version = "sourceVersion";

    public static String Default_DocAffixAttrDefName_publishDate = "publishDate";

    public static String Default_DocAffixAttrDefName_publishForm = "publishForm";

    public static String Default_DocAffixAttrDefName_creater = "creater";

    public static String Default_DocAffixAttrDefName_modifier = "modifier";

    // ��¼����Դ�����Ϣ:�ĵ���չ����������
    public static String docAffixAttrConName;

    // ��¼����Դ�����Ϣ:�ĵ���չ����Լ����
    public static String docAffixAttrResName;

    // ��¼����Դ�����Ϣ:�ĵ���չ���Զ���(����Դ)
    public static String docAffixAttrDefName_dataSource;

    // ��¼����Դ�����Ϣ:�ĵ���չ���Զ���(����Դ��汾��)
    public static String docAffixAttrDefName_version;

    // ��¼����Դ�����Ϣ:�ĵ���չ���Զ���(����ʱ��)
    public static String docAffixAttrDefName_publishDate;

    // ��¼����Դ�����Ϣ���ĵ���չ���Զ��壨������ʽ֪ͨ����Ǳ������
    public static String docAffixAttrDefName_publishForm;

    // ��¼����Դ�����Ϣ���ĵ���չ���Զ���(�������)
    public static String docAffixAttrDefName_noteNum;

    public static String docAffixAttrDefName_creater;

    public static String docAffixAttrDefName_modifier;

    static {
        QMProperties props = null;
        try {
            props = QMProperties.getLocalProperties();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        docAffixAttrConName = props.getProperty(
                "com.faw_qm.PublishParts.DocAffixAttrConName",
                Default_DocAffixAttrConName);
        docAffixAttrResName = props.getProperty(
                "com.faw_qm.PublishParts.DocAffixAttrResName",
                Default_DocAffixAttrResName);
        docAffixAttrDefName_dataSource = props.getProperty(
                "com.faw_qm.PublishParts.DocAffixAttrDefName_dataSource",
                Default_DocAffixAttrDefName_dataSource);
        docAffixAttrDefName_creater = props.getProperty(
                "com.faw_qm.PublishParts.DocAffixAttrDefName_creater",
                Default_DocAffixAttrDefName_creater);
        docAffixAttrDefName_modifier = props.getProperty(
                "com.faw_qm.PublishParts.DocAffixAttrDefName_modifier",
                Default_DocAffixAttrDefName_modifier);
        docAffixAttrDefName_version = props.getProperty(
                "com.faw_qm.PublishParts.DocAffixAttrDefName_version",
                Default_DocAffixAttrDefName_version);
        docAffixAttrDefName_publishDate = props.getProperty(
                "com.faw_qm.PublishParts.DocAffixAttrDefName_publishDate",
                Default_DocAffixAttrDefName_publishDate);
        docAffixAttrDefName_publishForm = props.getProperty(
                "com.faw_qm.PublishParts.DocAffixAttrDefName_publishForm",
                Default_DocAffixAttrDefName_publishForm);
        docAffixAttrDefName_noteNum = props.getProperty(
                "com.faw_qm.PublishParts.DocAffixAttrDefName_noteNum",
                Default_DocAffixAttrDefName_noteNum);
    }

    /**
     * ��ʼ���ĵ�����Դ����չ���Զ���
     * 
     * @return boolean
     */
    protected boolean initAffixAttrData() {

        try {
            // ���ȼ���ĵ�����չ������������
            AffixAttrService as = (AffixAttrService) PublishHelper
                    .getEJBService("AffixAttrService");
            AttrContainerInfo contInfo = (AttrContainerInfo) affixAttrConMap
                    .get(docAffixAttrConName);
            if (contInfo == null) {
                contInfo = PublishHelper
                        .getAffixAttrContByName(docAffixAttrConName);
            }
            if (contInfo == null) {
                contInfo = new AttrContainerInfo();
                contInfo.setContName(docAffixAttrConName);
                contInfo.setDisplayName("�ĵ���չ��������");
                // ͨ��������ô����ĵ�����
                contInfo = as.saveAttrContainer(contInfo);
                affixAttrConMap.put(docAffixAttrConName, contInfo);
            } else {
                affixAttrConMap.put(docAffixAttrConName, contInfo);
            }
            // �ټ���ĵ��ķ���Դ������Լ������
            AttrRestictInfo resInfo = (AttrRestictInfo) affixAttrResMap
                    .get(docAffixAttrResName);
            if (resInfo == null) {
                resInfo = PublishHelper
                        .getAffixAttrResByName(docAffixAttrResName);
            }
            if (resInfo == null) {
                resInfo = new AttrRestictInfo();
                resInfo.setRestName(docAffixAttrResName);
                resInfo.setAttrType("String");
                resInfo.setMaxValue(100);
                resInfo.setMinValue(1);
                resInfo.setAllowNull(true);
                resInfo = as.saveAttrRestict(resInfo);
                affixAttrResMap.put(docAffixAttrResName, resInfo);
            } else {
                affixAttrResMap.put(docAffixAttrResName, resInfo);
            }
            // �ټ���ĵ��ķ���Դ�����Զ���
            AttrDefineInfo defInfo_source = (AttrDefineInfo) affixAttrDefMap
                    .get(docAffixAttrDefName_dataSource);
            if (defInfo_source == null) {
                defInfo_source = PublishHelper
                        .getAffixAttrDefByName(docAffixAttrDefName_dataSource);
            }
            if (defInfo_source == null) {
                defInfo_source = new AttrDefineInfo();
                defInfo_source.setAttrName(docAffixAttrDefName_dataSource);
                defInfo_source.setDisplayName("������Դ");
                defInfo_source = as.saveAttrDefine(defInfo_source);
                affixAttrDefMap.put(docAffixAttrDefName_dataSource,
                        defInfo_source);
            } else {
                affixAttrDefMap.put(docAffixAttrDefName_dataSource,
                        defInfo_source);
            }
            AttrDefineInfo defInfo_version = (AttrDefineInfo) affixAttrDefMap
                    .get(docAffixAttrDefName_version);
            if (defInfo_version == null) {
                defInfo_version = PublishHelper
                        .getAffixAttrDefByName(docAffixAttrDefName_version);
            }
            if (defInfo_version == null) {
                defInfo_version = new AttrDefineInfo();
                defInfo_version.setAttrName(docAffixAttrDefName_version);
                defInfo_version.setDisplayName("����Դ�汾��");
                defInfo_version = as.saveAttrDefine(defInfo_version);
                affixAttrDefMap.put(docAffixAttrDefName_version,
                        defInfo_version);
            } else {
                affixAttrDefMap.put(docAffixAttrDefName_version,
                        defInfo_version);
            }
            AttrDefineInfo defInfo_creater = (AttrDefineInfo) affixAttrDefMap
                    .get(docAffixAttrDefName_creater);
            if (defInfo_creater == null) {
                defInfo_creater = PublishHelper
                        .getAffixAttrDefByName(docAffixAttrDefName_creater);
            }
            if (defInfo_creater == null) {
                defInfo_creater = new AttrDefineInfo();
                defInfo_creater.setAttrName(docAffixAttrDefName_creater);
                defInfo_creater.setDisplayName("����Դ���ݴ�����");
                defInfo_creater = as.saveAttrDefine(defInfo_creater);
                affixAttrDefMap.put(docAffixAttrDefName_creater,
                        defInfo_creater);
            } else {
                affixAttrDefMap.put(docAffixAttrDefName_creater,
                        defInfo_creater);
            }

            AttrDefineInfo defInfo_modifier = (AttrDefineInfo) affixAttrDefMap
                    .get(docAffixAttrDefName_modifier);
            if (defInfo_modifier == null) {
                defInfo_modifier = PublishHelper
                        .getAffixAttrDefByName(docAffixAttrDefName_modifier);
            }
            if (defInfo_modifier == null) {
                defInfo_modifier = new AttrDefineInfo();
                defInfo_modifier.setAttrName(docAffixAttrDefName_modifier);
                defInfo_modifier.setDisplayName("����Դ���ݸ�����");
                defInfo_modifier = as.saveAttrDefine(defInfo_modifier);
                affixAttrDefMap.put(docAffixAttrDefName_modifier,
                        defInfo_modifier);
            } else {
                affixAttrDefMap.put(docAffixAttrDefName_modifier,
                        defInfo_modifier);
            }

            AttrDefineInfo defInfo_date = (AttrDefineInfo) affixAttrDefMap
                    .get(docAffixAttrDefName_publishDate);
            if (defInfo_date == null) {
                defInfo_date = PublishHelper
                        .getAffixAttrDefByName(docAffixAttrDefName_publishDate);
            }
            if (defInfo_date == null) {
                defInfo_date = new AttrDefineInfo();
                defInfo_date.setAttrName(docAffixAttrDefName_publishDate);
                defInfo_date.setDisplayName("����ʱ��");
                defInfo_date = as.saveAttrDefine(defInfo_date);
                affixAttrDefMap.put(docAffixAttrDefName_publishDate,
                        defInfo_date);
            } else {
                affixAttrDefMap.put(docAffixAttrDefName_publishDate,
                        defInfo_date);
            }
            AttrDefineInfo defInfo_form = (AttrDefineInfo) affixAttrDefMap
                    .get(docAffixAttrDefName_publishForm);
            if (defInfo_form == null) {
                defInfo_form = PublishHelper
                        .getAffixAttrDefByName(docAffixAttrDefName_publishForm);
            }
            if (defInfo_form == null) {
                defInfo_form = new AttrDefineInfo();
                defInfo_form.setAttrName(docAffixAttrDefName_publishForm);
                defInfo_form.setDisplayName("������ʽ");
                defInfo_form = as.saveAttrDefine(defInfo_form);
                affixAttrDefMap.put(docAffixAttrDefName_publishForm,
                        defInfo_form);
            } else {
                affixAttrDefMap.put(docAffixAttrDefName_publishForm,
                        defInfo_form);
            }
            AttrDefineInfo defInfo_num = (AttrDefineInfo) affixAttrDefMap
                    .get(docAffixAttrDefName_noteNum);
            if (defInfo_num == null) {
                defInfo_num = PublishHelper
                        .getAffixAttrDefByName(docAffixAttrDefName_noteNum);
            }
            if (defInfo_num == null) {
                defInfo_num = new AttrDefineInfo();
                defInfo_num.setAttrName(docAffixAttrDefName_noteNum);
                defInfo_num.setDisplayName("����ʱ��");
                defInfo_num = as.saveAttrDefine(defInfo_num);
                affixAttrDefMap.put(docAffixAttrDefName_noteNum, defInfo_num);
            } else {
                affixAttrDefMap.put(docAffixAttrDefName_noteNum, defInfo_num);
            }

            // �ټ���������������Զ��塢����Լ�����Ƿ��Ѵ���������ϵ
            hasAffixAttrLink(contInfo, defInfo_source, resInfo, true);
            hasAffixAttrLink(contInfo, defInfo_version, resInfo, true);
            hasAffixAttrLink(contInfo, defInfo_date, resInfo, true);
            hasAffixAttrLink(contInfo, defInfo_form, resInfo, true);
            hasAffixAttrLink(contInfo, defInfo_num, resInfo, true);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * ����������������Զ��塢����Լ�����Ƿ��Ѵ���������ϵ
     * 
     * @param contInfo
     *            AttrContainerInfo
     * @param defInfo
     *            AttrDefineInfo
     * @param resInfo
     *            AttrRestictInfo
     * @param create
     *            boolean ���������ϵ�����ڣ�����ݲ���ָ����񴴽�
     * @throws QMException
     * @return boolean ���޹���
     */
    protected boolean hasAffixAttrLink(AttrContainerInfo contInfo,
            AttrDefineInfo defInfo, AttrRestictInfo resInfo, boolean create)
            throws QMException {
        AffixAttrService as = (AffixAttrService) PublishHelper
                .getEJBService("AffixAttrService");
        QMQuery query = new QMQuery("ContAndDefLink");
        QueryCondition cond = new QueryCondition("leftBsoID", "=", contInfo
                .getBsoID());
        query.addCondition(cond);
        query.addAND();
        cond = new QueryCondition("rightBsoID", "=", defInfo.getBsoID());
        query.addCondition(cond);
        query.addAND();
        cond = new QueryCondition("attrRestID", "=", resInfo.getBsoID());
        query.addCondition(cond);
        PersistService ps = (PersistService) PublishHelper
                .getEJBService("PersistService");
        Collection coll = ps.findValueInfo(query, false);
        if (coll == null || coll.isEmpty()) {
            // create Link
            if (create) {
                as.addDefineToContainer(contInfo, defInfo, resInfo);
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
