/** ���ɳ���CappLMRB_zh_CN.java	1.1  2003/08/08
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 * CR1 2009/04/28  ��ѧ��   ԭ�򣺹������������ɹ����ŵĹ���
 *                          ����������������������ɹ����ŵ���Դ���ݡ�
 *                          ��ע�������¼��ʶΪCRSS-007
 * CR2 2009/04/29  ��־��   ԭ�򣺹��չ�̱༭��������Ƴ���ť
 *                          ���������չ�̴ӹ������в�ȥ����ʾ����ɾ����
 *                          ��ע�������¼��ʶΪ"CRSS-012"
 * CR3 2009/04/05 ��־��    ԭ���Ż���������߼������ٲ�ѯ���ݿ������
 *                         ������ȥ���ж��Ƿ񱻱�Ĺ������Ż�������������߼���
 *                         ��ע�����ܲ����������ƣ������ճ���������� 
 * CR4 2009/05/22  ������   ԭ�򣺹������޹���ʱӦ�����������ɹ����
 *                         ��������ӵ����Ի�������                        
 *
 */
package com.faw_qm.cappclients.capp.util;


/**
 * <p>Title:���չ��ģ����Դ�ļ������ļ��壩 </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ���� Ѧ��
 *����(1)2008.02.20 ��־���޸� �޸�ԭ��:�߼�����������������ʱ����ǹ涨���ڸ�ʽ�Լ�������
 *                     Ӧ��Ϊ:ֹͣ����,������ʾ��Ϣ,���û�����������ȷ���ڸ�ʽʱ�������.
 * @version 1.0
 */

public class CappLMRB_zh_CN extends java.util.ListResourceBundle
{

    public CappLMRB_zh_CN()
    {
    }

    public Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] =
            {
            //JButtons
            {"browsePdfJButton", "���PDF�ļ�(P)"}
            ,
            {"createPdfJButton", "����PDF�ļ�(O)"}
            ,
            {"browseDxfJButton", "���DXF�ļ�(B)"}
            ,
//            {"createDxfJButton", "����DXF�ļ�(D)"}
//            ,
            {"createDxfJButton", "ȷ��(Y)"}
            ,
            {"OkJButton", "ȷ��(Y)"}
            ,
            {"CancelJButton", "ȡ��(C)"}
            ,
            {"SaveJButton", "����(S)"}
            ,
            {"QuitJButton", "�˳�(Q)"}
            ,
            {"ParaJButton", "������Ϣ(E). . ."}
            ,
            {"addJButton", "���(A). . ."}
            ,
            {"editJButton", "�༭(E). . ."}
            ,
            {"deleteJButton", "ɾ��(D)"}
            ,
            {"searchTreeJButton", "����(T)"}
            ,
            {"upJButton", "����(U)"}
            ,
            {"downJButton", "����(DO)"}
            ,
            {"browseJButton", "���(B). . ."}
            ,
            {"storageJButton", "���(ST)"}
            ,
            //JLabels
            {"checking in", "���ڼ���"}
            ,
            {"describe", "ע��"}
            ,
            {"aqlJLabel", "�ϸ�����ˮƽ��ֵ��AQL��"}
            ,
            {"inspectNumberJLabel", "���������n��"}
            ,
            {"inspectTimeJLabel", "���Ƶ��"}
            ,
            {"eligibleNumberJLabel", "�ϸ��ж�����c��"}
            ,
            {"procedureNumberJLabel", "�ӹ������"}
            ,
            {"reviseTechnics", "�޶�����"}
            ,
            {"explain", "˵��"}
            ,
            {"technicsNumberJLabel", "*���ձ��"}
            ,
            //wangh 20070131
            {"stepNumberJLabel2", "������"}
            ,
            {"technicsNameJLabel", "*��������"}
            ,
            {"technicsTypeJLabel", "*��������"}
            ,
            {"remarkJLabel", "��ע"}
            ,
            {"paceNumberJLabel", "*������"}
            ,
            {"drawFormatJLabel", "*��ͼ���"}
            ,
            {"technicsContentJLabel", "��������"}
            ,
            {"mtechContentJLabel", "*��������"}
            ,
            {"drawingJLabel", "���ռ�ͼ"}
            ,
            {"leftJLabel", "����������Դ������"}
            ,
            {"rightJLabel", "��������"}
            ,
            {"stepNumberJLabel", "*�����"}
            ,
            {"relationTechnicsJLabel", "��������"}
            ,
            {"stepNameJLabel", "*��������"}
            ,
            {"stepTypeJLabel", "*��������"}
            ,
            {"stepClassifiJLabel", "*�������"}
            ,
            {"processTypeJLabel", "*�ӹ�����"}
            ,
            {"workshopJLabel", "*����"}
            ,
            {"drawingExportJLabel", "*��ͼ���"}
            ,
            {"drawingJLabel", "���ռ�ͼ"}
            ,
            {"specharTypeJLabel", "�����������"}
            ,
            {"currentSpecharJLabel", "��ǰ����"}
            ,
            //Titles
            {"exception", "�쳣��Ϣ"}
            ,
            {"information", "��ʾ"}
            ,
            {"error", "����"}
            ,
            {"renameTechnicsTitle", "����������"}
            ,
            {"checkInTitle", "����"}
            ,
            {"notice", "֪ͨ"}
            ,
            {"afreshAssignLifeCycle", "����ָ����������"}
            ,
          //2008.03.09 ������ģ�������Ŀ�顱��Ϊ�������顱
            {"afreshAssignProject", "����ָ��������"}
            //end mario
            ,
            {"technicsParam", "���ղ���"}
            ,
            {"technicsInformationTitle", "������Ϣά��"}
            ,
            {"maintainToolEquipmentTitle", "ά����װ���豸����"}
            ,
            {"reviseTitle", "�޶����հ汾"}
            ,
            {"describeDocTitle", "�ĵ�"}
            ,
            {"partTitle", "�㲿��"}
            ,
            {"materialTitle", "����"}
            ,
            {"equipmentTitle", "�豸"}
            ,
            {"toolTitle", "��װ"}
            ,
            {"docTitle", "�ĵ�"}
            ,
            {"technicsMasterTitle", "��������Ϣ"}
            ,
            {"extendAttriTitle", "��չ����"}
            ,
            {"aboutTechnicsTitle", "���ڹ��չ�̹�����"}
            ,
            {"selectTemplateTitle", "ģ��ѡ��"}
            ,
            {"technicsReguMainTitle", "���չ�̹�����"}
            ,
            {"technicsTreeTitle", "������"}
            ,
            {"termTreeTitle", "��������"}
            ,
            {"drawingTreeTitle", "��ͼ"}
            ,
            {"selectObjectTitle", "ѡ�����"}
            ,
            {"editSpecharTitle", "�༭�������"}
            ,
            {"productStructure", "��Ʒ�ṹ"}
            ,
            //Menus
            {"jMenuFile", "�ļ�(F)"}
            ,
            {"jMenuFileExit", "�˳�(X)"}
            ,
            {"jMenuHelp", "����(H)"}
            ,
            {"jMenuHelpAbout", "����(A)"}
            ,
            {"jMenuFileCollect", "�ϲ�(U)"}
            ,
            {"jMenuFileCollect1", "�ϲ�(O)"}
            ,
            {"jMenuFileImport", "����"}
            ,
            {"jMenuFileExport", "����(R)"}
            ,
            {"jMenuExportAll", "����ȫ������(P)"}
            ,
            {"jMenuFileSearch", "����(S)"}
            ,
            {"jMenuFileRefresh", "ˢ��(E)"}
            ,
            {"jMenuFileCreate", "�½�(N)"}
            ,
            {"jMenuCreateTechnics", "��������Ϣ(M)"}
            ,
            {"jMenuCreateStep", "����(S)"}
            ,
            {"jMenuCreatePace", "����(P)..."}
            ,
            {"jMenuSelect", "ѡ��(P)"}
            ,
            {"jMenuSelectView", "�鿴(V)"}
            ,
            {"jMenuSelectUpdate", "����(U)"}
            ,
            {"jMenuSelectFormStepNum", "�������ɹ����(R)"}
            //CR1 begin
            ,
            {"jMenuSelectFormPaceNum", "�������ɹ�����(B)"}
            //CR1 end
            ,
            {"jMenuSelectBrowse", "��ӡԤ��(Y)"}
            ,
            {"jMenuSelectChangeLocation", "�������ϼ�(L)"}
            ,
            {"jMenuSelectRename", "������(M)"}
            ,
            {"jMenuSelectSaveAs", "���Ϊ(G)"}
            ,
            {"jMenuSelectUseTech", "Ӧ�õ��͹���(H)"}
            ,
            {"jMenuSelectUseStep", "Ӧ�õ��͹���(I)"}
            ,
            {"jMenuSelectMadeTech", "���ɵ��͹���(J)"}
            ,
            {"jMenuSelectMadeStep", "���ɵ��͹���(K)"}
            ,
            {"jMenuSelectCopy", "����(C)"}
            ,
            {"jMenuSelectPaste", "ճ��(P)"}
            ,
            {"jMenuVersion", "�汾(V)"}
            ,
            {"jMenuVersionCheckIn", "����(I)"}
            ,
            {"jMenuVersionCheckOut", "���(O)"}
            ,
            {"jMenuVersionCancel", "�������(U)"}
            ,
            {"jMenuVersionRevise", "�޶�(R)"}
            ,
            {"jMenuVersionVersion", "�鿴�汾��ʷ(N)"}
            ,
            {"jMenuVersionIteration", "�鿴������ʷ(T)"}
            ,
            {"jMenuLifeCycle", "��������(L)"}
            ,
            {"jMenuSetLifeCycleState", "����ָ����������״̬(S)"}
            ,
            {"jMenuLifeCycleSelect", "����ָ����������(L)"}
            ,
            {"jMenuLifeCycleView", "�鿴����������ʷ(H)"}
            ,
          //2008.03.09 ������ģ�������Ŀ�顱��Ϊ�������顱
            {"jMenuLifeCycleGroup", "����ָ��������(P)"}
            //end mario
            ,
            {"jMenuIntellect", "���ܹ���"}
            ,
            {"jMenuIntellectPart", "����幤������"}
            ,
            {"jMenuIntellectRoot", "�ӹ�·�߹�������"}
            ,
            {"jMenuSearch", "����(S)"}
            ,
            {"jMenuSearchEquip", "���豸��������(E)"}
            ,
            {"jMenuSearchTool", "����װ��������(T)"}
            ,
            {"jMenuSearchMaterial", "��������������(M)"}
            ,
            {"jMenuSearchTechnics", "�������չ��(S)"}
            ,
            {"jMenuHelpManage", "���չ�̹���"}
            ,
            {"jMenuSelectDelete", "ɾ��(D)"}
            ,
            //Begin CR2
            {"jMenuSelectMoveOut", "�Ƴ�(K)"}
            //End CR2
            ,
            {"jMenuAddObject", "���ѡ�ж���(A)"}
            ,
            {"jMenuConfigCrit", "���ù淶(C)"}
            ,
            {"jMenuHelpItem", "����(H)"}
            ,
            //other
            {"SECOND", "��"}
            ,
            {"MINUTE", "��"}
            ,
            {"HOUR", "Сʱ"}
            ,
            {"partNumber", "�㲿�����"}
            ,
            {"partName", "�㲿������"}
            ,
            {"partVersion", "�汾"}
            ,
            {"mainPart", "��Ҫ���"}
            ,
            {"mainTechnics", "��Ҫ����"}
            ,
            {"docNumber", "�ĵ����"}
            ,
            {"docName", "�ĵ�����"}
            ,
            {"docVersion", "�汾"}
            ,
            {"materialNumber", "���ϱ��"}
            ,
            {"materialName", "��������"}
            ,
            {"roughtType", "ë������"}
            ,
            {"mRation", "�������Ķ��Kg��"}
            ,
            {"mmMark", "��Ҫ"}
            ,
            {"usageCount", "ʹ������"}
            ,
            {"unit", "������λ"}
            ,
            {"extendContent", "��չ����"}
            ,
            {"controlPlan", "���Ƽƻ�"}
            ,
            {"productCharactor", "��Ʒ����"}
            ,
            {"productCriterion", "��Ʒ�淶"}
            ,
            {"processCharactor", "��������"}
            ,
            {"processCriterion", "���̹淶"}
            ,
            {"charactorType", "���Է���"}
            ,
            {"evaluate", "���۲���"}
            ,
            {"samplingCapacity", "ȡ������"}
            ,
            {"samplingFrequency", "ȡ��Ƶ��"}
            ,
            {"controlMethod", "���Ʒ���"}
            ,
            {"reactionPlan", "��Ӧ�ƻ�"}
            ,
            {"equipmentNumber", "�豸���"}
            ,
            {"equipmentName", "�豸����"}
            ,
            {"equipmentType", "�豸�ͺ�"}
            ,
            {"planeNumber", "ƽ��ͼ��"}
            ,
            {"necessary", "��Ҫ"}
            ,
            {"toolNumber", "��װ���"}
            ,
            {"toolName", "��װ����"}
            ,
            {"selectedEquipment", "ѡ���豸"}
            ,
            {"selectedTool", "ѡ�й�װ"}
            ,
            {"mainID", "��Ҫ��ʶ"}
            ,
            {"select", "ѡ��"}
            ,
            {"templateType", "ģ������"}
            ,
            {"technicsNumber", "���ձ��"}
            ,
            {"technicsName", "��������"}
            ,
            {"version", "�汾"}
            ,
            {"technicsMaster", "��������Ϣ"}
            ,
            {"procedureStep", "����"}
            ,
            {"procedurePace", "����"}
            ,
            {"procedureNum1", "������"}
            ,
            {"processFlowNum", "�������̱��"}
            ,
            {"controlPlanNum", "���Ƽƻ����"}
            ,
            {"femaNum", "����FMEA���"}
            ,
            {"taskInstructNum", "��ҵָ�����"}
            ,
            {"procedureNum", "�����"}
            ,
            {"procedureName", "��������"}
            ,
            {"paceNum", "������"}
            ,
            //Begin CR2
            {"toolbar.icons", "public_createObj,public_updateObj,public_deleteObj,public_viewObj,Spacer,public_checkInObj,public_checkOutObj,public_repealObj,Spacer,public_copy,public_paste,Spacer,public_search,public_clear"}
            ,
            {"toolbar.text",
            "����,����,ɾ��,�鿴,Spacer,����,���,�������,Spacer,����,ճ��,Spacer,����,�Ƴ�"}
            //End CR2
            ,
            {"techAction", "��ӹ��չ��"}
            ,
            {"eqAction", "����豸����"}
            ,
            {"toAction", "��ӹ�װ����"}
            ,
            {"maAction", "��Ӳ��Ϲ���"}
            ,
            {"termAction", "ѡ�й�������"}
            ,
            {"drawAction", "��ӹ��ռ�ͼ"}
            ,
            {"partAction", "����㲿��"}
            ,
            //Messages
            {"1", "��Чģʽ"}
            ,
            {"2", "*��û�з����κθ��ġ�"}
            ,
            {"3", "���ձ�Ų���Ϊ�ա�"}
            ,
            {"4", "�������Ʋ���Ϊ�ա�"}
            ,
            {"5", "ȱ�ٱ�����ֶ�"}
            ,
            {"6", "���ϼ�λ�ò���Ϊ�ա�"}
            ,
            {"7", "���ϼ�·����Ч"}
            ,
            {"8", "��ָ�������ϼв������ĸ������ϼ�"}
            ,
            {"9", "���ڱ���..."}
            ,
            {"10", "�Ƿ񱣴��½���������Ϣ��"}
            ,
            {"11", "�Ƿ񱣴���¹�������Ϣ���ݣ�"}
            ,
            {"12", "��ǰ���չ�������ĸ����ļ����У������ύ��"}
            ,
            {"13", "�����Ų���Ϊ�ա�"}
            ,
            {"14", "�������Ʋ���Ϊ�ա�"}
            ,
            {"15", "�Ƿ񱣴��½�����"}
            ,
            {"16", "�Ƿ񱣴���¹������ݣ�"}
            ,
            {"17", "�����Ų���Ϊ�ա�"}
            ,
            {"18", "�Ƿ񱣴��½�������"}
            ,
            {"19", "�Ƿ񱣴���¹������ݣ�"}
            ,
            {"20", "ѡ��Ķ������ʹ���"}
            ,
            {"21", "ɾ���������޷��ָ����Ƿ������"}
            ,
            {"22", "���Ƶ����ͻ����ݲ�ƥ�䣬���ܸ���"}
            ,
            {"23", "����ָ�����յ����ϼ�λ�ã�����ִ�м��롣����ѡ�����ϼС�"}
            ,
            {"24", "����*ʧ�ܣ���Ϊ��Ҫ���ݵ����ػ�洢�����г��ִ���"}
            ,
            {"25", "*�����ڷ��״μ���״̬�����ȼ��������ִ�м��������"}
            ,
            {"26", "��û��ѡ����������޷�ִ�е�ǰ������"}
            ,
            {"27", "*�Ѿ����û�*�����"}
            ,
            {"28", "*�Ѿ����������"}
            ,
            {"29", "�ڳ�ʼ�����ػ���Դʱ,����:"}
            ,
            {"30", "��ȷʵҪ�������*������ʧ���б����"}
            ,
            {"31", "*�Ѿ�����������ϼ�*�У�"}
            ,
            {"32", "������������ϼ� * ʱ��������."}
            ,
            {"33", "*�Ѿ������˼��!"}
            ,
            {"34", "���*ʧ�ܣ�"}
            ,
            {"35", "��δ����޶�*����ɣ���Ϊ����δ�����롣"}
            ,
            {"36", "*�Ѿ���*�������ȷʵҪ��������"}
            ,
            {"37", "��δָ����ŵ�ǰ����ĸ��������ϼС��������������ϼУ����ܼ����"}
            ,
            {"38", "�޷��ҵ�����*�Ĺ������������ϼУ���Ϊ*û�б������"}
            ,
            {"39", "����ɾ��*,��Ϊ���Ѿ������!"}
            ,
            {"40", "*��Ҫ������������ϼ��в����޸�,����Ҫ�����"}
            ,
            {"41", "�豸�б�͹�װ�б���붼��������ݣ�������ѡ����豸�͹�װ��������һ����һ�����ݣ�������⡣"}
            ,
            {"42", "��ǰ�����Ѿ�ѡ���˹������գ�����Ϊ�ù��򴴽�������"}
            ,
            {"43", "��ǰ���չ���Ѿ��ɹ��ύ�����������У�"}
            ,
            {"44", "�Ƿ�ϲ����չ�̣�"}
            ,
            {"45", "��ѡ����*���½����յĹ������಻ƥ�䣡"}
            ,
            {"46", "���湤�򣨲���ʧ��"}
            ,
            {"47", "ϵͳ���ô�������ϵͳ����Ա��ϵ��"}
            ,
            {"48", "����δ���*������ǰ����*�����"}
            ,
            {"49", "*��ǰ�ѱ�*�������ȷʵҪ���������������*���������и�����"}
            ,
            {"50", "��ǰ��������û�п�ά������չ���ݡ�"}
            ,
            {"51", "ȱ���������Դ��"}
            ,
            {"52", "*�����ڼ��״̬�����������Ƚ������룬���ܽ��е�ǰ������"}
            ,
            {"53", "�����������ڹ�������ѡ��һ�����տ����󣬲����½�����"}
            ,
            {"54", "�����������ڹ�������ѡ��һ�����򣨲������󣬲����½�������"}
            ,
            {"55", "*û�б��κ��㲿��ʹ�ã������ύ��"}
            ,
            {"56", "��ʱ��ֵΪʵ��������ȷ���롣"}
            ,
            {"57", "����ָ���Ĺ�װ�����ڣ��Ƿ������½���װ��"}
            ,
            {"58", "���򣨲����ű���Ϊ�������ݣ����ú������֡���ĸ����š�"}
            ,
            {"59", "���򣨲�������Ч"}
            ,
            {"60", "�Ѿ�����*��"}
            ,
            {"61", "*�Ĺ������ݣ�����򹤲�����δ���룡"}
            ,
            {"62", "�������ݲ���Ϊ�ա�"}
            ,
            {"63", "����δ���*�������½��乤��򹤲���"}
            ,
            {"64", "����δ���Ҫճ���Ķ���"}
            ,
            {"65", "��ǰ�����ܼ����"}
            ,
            {"66", "�״μ���ֻ�ܼ��빤������Ϣ"}
            ,
            {"67", "�������ղ���ѡ�񱾹��������Ĺ���"}
            ,
            {"68", "����δ���*��"}
            ,
            {"69", "*��*�Ĺ������಻ƥ�䣡"}
            ,
            {"70", "*���ڼ��״̬�����ܸ������ϼУ�"}
            ,
            {"71", "*���ڹ������ϼ��У����ܸ������ϼУ�"}
            ,
            {"72", "����ָ�����豸�����ڣ��Ƿ������½��豸��"}
            ,
            {"73", "����ָ���Ĳ��ϲ����ڣ��Ƿ������½����ϣ�"}
            ,
            {"74", "*�ǹ���������ԭ�����������޸ģ�"}
            ,
            {"75", "*�Ѿ�����������ܺϲ���"}
            ,
            {"76", "*�ǹ���������ԭ�������ܸ��ƣ�"}
            ,
            {"77", "�����и���Ȩ��"}
            ,
            {"78", "��û��ѡ����Ч��������š�"}
            ,
            {"79", "�������಻��Ϊ�ա�"}
            ,
            {"80", "�������಻��Ϊ�ա�"}
            ,
            {"81", "�ӹ����Ͳ���Ϊ�ա�"}
            ,
            {"82", "���������Ϊ�ա�"}
            ,
            {"83", "���Ų���Ϊ�ա�"}
            ,
            {"84", "��ͼ�����ʽ����Ϊ�ա�"}
            ,
            {"85", "��������"}
            ,
            {"86", "��������"}
            ,
            {"87", "�������"}
            ,
            {"88", "�ӹ�����"}
            ,
            {"89", "����"}
            ,
            {"90", "ͼҳ��ʽ"}
            ,
            {"91", "������Ĺ���Ź���,��ȷ�������С��99999"}
            ,
            {"92", "������Ĺ����Ź�������ȷ��������С��99999"}
            ,
            {"93", "������Ĺ��ձ�Ź�������ȷ������С��20λ"}
            ,
            {"94", "������Ĺ������ƹ�������ȷ������С��40λ"}
            ,
            {"95", "������Ĺ������ƹ�������ȷ������С��40λ"}
            ,
            {"96", "����������Ч"}
            ,
            {"97", "���ձ����Ч"}
            ,
            {"98", "����������Ч"}
            ,
            {"99", "��ע��Ϣ��Ч"}
            ,
            {"100", "������ı�ע��Ϣ��������ȷ������С��40λ"}
            ,
            {"101", "�������Ķ�����Ч"}
            ,
            {"102", "��ȷ���������Ķ�����0~99999.99999֮��"}
            ,
            {"103", "ʹ��������Ч"}
            ,
            {"104", "��ȷ�������ʹ��������1~99֮��"}
            ,
            {"105", "��ȷ�������ʹ��������0~99999.9999֮��"}
            ,
            {"106", "��ʱ��Ч"}
            ,
            {"107", "������Ĺ�ʱ��Ч����ȷ����ʱ��0~9999999.999֮��"}
            ,
            {"108", "������Ƿ�����������"}
            ,
            {"109", "������Ƿ�����������"}
            ,
            {"110", "����ɾ���˹���,�˹����ѱ����տ�*����"}
            ,
            {"111", "*��δʹ�ó�ѹ���Ϲ�����Ϊ��Ҫ����,����ʹ�ó�ѹ����"}
            ,
            //�������,�����ڴ�������в��ҹ�������
            {"112", "��������"}
            ,
            {"113", "���������\"*\"��������δ������ȷ,����ϵͳ����Ա��ϵ"}
            ,
            {"114", "*����ֵӦΪ*����"}
            ,
            {"115", "*�²����½�����"}
            ,
            {"116", "����capp.property�ļ������õĹ���ʹ�ò��Ϲ����������Ƿ���ȷ"}
            ,
            {"117", "���������ļ������õ����ʹ�ù��չ����������Ƿ���ȷ"}
            ,
            {
            "118","���򱻼��"
            }
            ,
            {
             "119","ɾ���ù�������Ϣ,����ʹ���µĹ���͹���Ҳȫ��ɾ���Ҳ��ָܻ�,�Ƿ����"
            }

            ,
            { "120", "��ǰ�û����ǹ��յĴ�����Ҳ���ǹ���Ա�������������ɹ����" }
            //Begin CR3
            ,
            {
             "121","�������ղ���ѡ���ڼ��״̬�Ĺ���"
            }
            //End CR3
            ,
            {"122","�ù�����û�й��򣬲����������ɹ����"}
            ,
            {"workingCopyTitle", "* �Ĺ�������"}
            ,
            {"techCheckedOutBy", "�� * ���"}
            ,
            {"inPesrsonFolder", "�ڸ����ļ���"}
            ,
            {"techCheckedIn", "�Ѽ���"}
            ,
            //Ϊ TechnicsStepJPanel����ʵ�������������������
            {"technicsTypeForTechnicsStepJPanel", "����������Ϣ"}
            ,
            {"ifQuitMessage", "�������˳�ϵͳ,�Ƿ����?"}
            ,
            {"flowdrawing", "�Ƽ�����ͼ"}
            ,

            {"0", "����"}
            ,
            {"Help/Capp/QMTechnics", "help/zh_cn/capp/index.html"}
            ,
            {"Desc/Capp/QMTechnics", ""}
            ,
            //״̬���е�����
            {"default_status", "Ҫ��ð������������������˵�����F1 ����"}
            ,
            {"jMenuFileExit_status", "�˳���ϵͳ��"}
            ,
            {"jMenuHelpAbout_status", "���ڱ�ϵͳ��"}
            ,
            {"jMenuFileCollect_status", "��������ͬ���չ�̺ϲ�Ϊһ����"}
            ,
            {"jMenuFileExport_status", "����һ���򼸸����չ�̵���Ϣ��"}
            ,
            {"jMenuFileRefresh_status", "ˢ�¹�������ѡ�еĽڵ㡣"}
            ,
            {"jMenuFileCreate_status", "����һ����������Ϣ����򹤲���"}
            ,
            {"jMenuCreateTechnics_status", "����һ����������Ϣ��"}
            ,
            {"jMenuCreateStep_status", "����һ������"}
            ,
            {"jMenuCreatePace_status", "����һ��������"}
            ,
            {"jMenuSelectMadeTech_status", "��ѡ�еĹ�������һ�����͹��ա�"}
            ,
            {"jMenuSelectMadeStep_status", "��ѡ�еĹ�������һ�����͹���"}
            ,
            {"jMenuSelectCopy_status", "���ƹ��ջ���򹤲����������С�"}
            ,
            {"jMenuSelectPaste_status", "���������еĶ���ճ����������ѡ��Ľڵ��¡�"}
            ,
            {"jMenuVersionCheckIn_status", "�����ռ��뵽һ���������ϼ��С�"}
            ,
            {"jMenuVersionCheckOut_status", "������յ��������ϼС�"}
            ,
            {"jMenuVersionCancel_status", "������Ĺ��ճ����ؼ��ǰ��״̬��"}
            ,
            {"jMenuVersionRevise_status", "�޶�����,����汾������"}
            ,
            {"jMenuVersionVersion_status", "�鿴������ѡ�нڵ�İ汾��ʷ��"}
            ,
            {"jMenuVersionIteration_status", "�鿴������ѡ�нڵ�İ���汾��"}
            ,
            {"jMenuSetLifeCycleState_status", "����ָ�����յ���������״̬��"}
            ,
            {"jMenuLifeCycleSelect_status", "����ָ�����յ��������ڡ�"}
            ,
            {"jMenuLifeCycleView_status", "�鿴���յ�����������ʷ��"}
            ,
          //2008.03.09 ������ģ�������Ŀ�顱��Ϊ�������顱
            {"jMenuLifeCycleGroup_status", "����ָ�����յĹ����顣"}
            //end mario
            ,
            {"jMenuSearchEquip_status", "��ʹ�õ��豸��Ϣ�������ա�"}
            ,
            {"jMenuSearchTool_status", "��ʹ�õĹ�װ��Ϣ�������ա�"}
            ,
            {"jMenuSearchMaterial_status", "��ʹ�õĲ�����Ϣ�������ա�"}
            ,
            {"jMenuSearchTechnics_status", "�������ա�"}
            ,
            {"jMenuAddObject_status", "����ǰѡ�ж�����빤�����������б��С�"}
            ,
            {"jMenuSelectDelete_status", "ɾ����������ѡ�еĶ���"}
            ,
            //Begin CR2
            {"jMenuMoveOutTechnics_status", "�Ƴ����ա�"}
            //End CR2
            ,
            {"jMenuSelectView_status", "�鿴��������ѡ�еĶ���"}
            ,
            {"jMenuSelectUpdate_status", "���¹�������ѡ�еĶ���"}
            ,
            {"jMenuSelectFormStepNum_status", "��ѡ�й��յĹ���˳���������ɹ���š�"}
            //CR1 begin
            ,
            {"jMenuSelectFormPaceNum_status", "��ѡ�й��յĹ�����˳���������ɹ����š�"}
            //CR1 end
            ,
            {"jMenuSelectBrowse_status", "��ӡԤ�����ջ�����Ϣ��"}
            ,
            {"jMenuSelectChangeLocation_status", "����ָ�����յĴ洢���ϼС�"}
            ,
            {"jMenuSelectRename_status", "Ϊ��������Ϣ����ָ�����ձ�ź͹������ơ�"}
            ,
            {"jMenuSelectSaveAs_status", "�����մ�Ϊ��һ������,�����޸Ĺ�������Ϣ��ĳЩ��Ϣ��"}
            ,
            {"jMenuSelectUseTech_status", "���Ѵ��ڵĵ��͹��ո��Ƶ���ǰѡ��Ĺ����¡�"}
            ,
            {"jMenuSelectUseStep_status", "���Ѵ��ڵĵ��͹����Ƶ���ǰѡ��Ĺ����¡�"}
            ,
            {"jMenuConfigCrit_status_status", "����������������ù淶��"}
            ,
            {"jMenuHelpItem_status", "������"}
            ,
            {"searchTreeJButton_status", "����*��"}
            ,
            {
            "exportSuccessful", "�����ɹ���"
    }
            ,
            {
            "drawingFormatConfigError", "capp.property�����õ�*��ͼ�����ʽ����ȷ,�������"
            }
            ,
            {
             "creatByOther","*�����˴����ġ�"
            }
            ,
            //����(1)2008.02.20 ��־���޸� �޸�ԭ��:�߼�����������������ʱ����ǹ涨���ڸ�ʽ�Լ�������
            //           Ӧ��Ϊ:ֹͣ����,������ʾ��Ϣ,���û�����������ȷ���ڸ�ʽʱ�������.
            {
            "dateCreatedSearch","����ʱ�����,�밴��ʽ���롣"
            }
            ,
            {"processFlow","���Կ���"}
            ,
            {"processFMEA","����FMEA"}
            ,
            {"controlPlan","���Ƽƻ�"}

    };


    public static void main(String[] args)
    {
        CappLMRB_zh_CN cappLMRB_zh_CN1 = new CappLMRB_zh_CN();
    }
}
