/**
 * ����OtherRB.java 1.0 11/2/2003
 * ��Ȩ��һ��������˾����
 * ����������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.part.client.other.util;

import java.util.ListResourceBundle;


/**
 * <p>Title: ��Դ�ļ���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author ���� ����־
 * @version 1.0
 */

public class OtherRB extends ListResourceBundle
{

    public OtherRB()
    {
    }

    public Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] =
            {
            {"doc", "�ĵ�"}
            ,
            {"MaterialTitle", "���������嵥"}
            ,
            {"searchPart", "�����㲿��"}
            ,
            {"Please select output mode", "��ѡ�������ʽ!"}
            ,
            {"At least one attribute", "��������ߵ�������ѡ��һ������!"}
            ,
            {"standar", "��׼"}
            ,
            {"baseLine", "��׼��"}
            ,
            {"effectivity", "��Ч��"}
            ,
            {"value", "*ֵ"}
            ,
            {"search", "����(F). . ."}
            ,
            {"commonSearch", "��ͨ����"}
            ,
            {"searches", "����(S). . ."}
            ,
            {"Not including person folder's part", "�����������ϼ��е��㲿��"}
            ,
            {"Don't save filter condition", "������ɸѡ����"}
            ,
            {"original", "Դ���ϼ�"}
            ,
            {"goal", "Ŀ�����ϼ�"}
            ,
            {"Move operation object", "�������ϼ�"}
            ,
            {"name_check", "��"}
            ,
            {"number_check", "��"}
            ,
            {"unit_check", "�ϲ����"}
            ,
            {"browse", "���"}
            ,
            {"name", "����"}
            ,
            {"name1", "*����"}
            ,
            {"number", "���"}
            ,
            {"view", "��ͼ"}
            ,
            {"state", "״̬"}
            ,
            {"exit", "�˳�"}
            ,
            {"stop", "ֹͣ"}
            ,
            {"StructureTitle", "���ù淶"}
            ,
            {"DeferenceTitle", "�ṹ����Ƚ�"}
            ,
            {"type", "����"}
            ,
            {"standard", "��׼"}
            ,
            {"date", "����"}
            ,
            {"lot_num", "����"}
            ,
            {"serial_num", "���к�"}
            ,
            {"error", "����"}
            ,
            {"warning", "����"}
            ,
            {"1", "����������ַ���û���ҵ���Ӧ�Ļ�׼��!"}
            ,
            {"2", "��׼�����Ʋ�Ψһ��������ѡ��!"}
            ,
            {"3", "����������ַ���û���ҵ���Ӧ����Ч�Է���!"}
            ,
            {"4", "�������Ͳ�ƥ�䣬����Ϊʱ������(yyyy/M/d)!"}
            ,
            {"5", "Դ�㲿��"}
            ,
            {"6", "Ŀ���㲿��"}
            ,
            {"7", "��ѡ��ͬ��Ŀ���㲿�����߲�ͬ�����ù淶!"}
            ,
            {"8", "��ѡ���׼��!"}
            ,
            {"9", "��ѡ����Ч��!"}
            ,
            {"10", "��ѡ����Ч������!"}
            ,
            {"11", "��������Ч��ֵ!"}
            ,
            {"12", "��Ч�����������Ͳ�һ��!"}
            ,
            {"13", "��ѡ����������!"}
            ,
            {"14", "Դ���ϼ�"}
            ,
            {"15", "�������ϼ�"}
            ,
            {"16", "���кź������͵���Ч��ֵ * ����Ϊ����!"}
            ,
            {"17", "��Ч��ֵ�ĳ��ȹ���,���ܴ���100�ַ�!"}
            ,
            {"out", "���"}
            ,
            {"ok", "ȷ��(Y)"}
            ,
            {"cancel", "ȡ��(C)"}
            ,
            {"all", "ȫ��"}
            ,
            {"grade", "�ּ�"}
            ,
            {"statistics", "ͳ�Ʊ�"}
            ,
            {"classify", "����"}
            ,
            {"type", "����"}
            ,
            {"mayOutput", "���������"}
            ,
            {"deleteAll", "<<ȫ��ɾ��"}
            ,
            {"addAttribute", "���>"}
            ,
            {"addAll", "ȫ�����>>"}
            ,
            {"deleteAttri", "<ɾ��"}
            ,
            {"upMove", "����"}
            ,
            {"downMove", "����"}
            ,
            {"outPut", "�������"}
            ,
            {"source", "��Դ"}
            ,
            {"viewName", "��ͼ"}
            ,
            {"projectTeamName", "������"}
            ,
            {"version", "�汾"}
            ,
            {"partType", "����"}
            ,
            {"lifeCycleState", "��������״̬"}
            ,
            //CCBegin by liunan 2008-07-30
            {"unit", "ʹ�÷�ʽ"}
            ,
            //CCEnd by liunan 2008-07-30
            {"partName", "����"}
            ,
            {"partNumber", "���"}
            ,
            {"quantity", "����"}
            ,
            {"attributeName", "  ������"}
            ,
            {"attributeValue", "  ����ֵ"}
            ,
            {"extendSearchTitle", "����չ��������"}
            ,
            {"basicSearchTitle", "��������������"}
            ,
            {"searchJButton", "����"}
            ,
            {"stopJButton", "ֹͣ"}
            ,
            {"uniteJCheckBox", "�ϲ����"}
            ,
            {"okJButton", "ȷ��(Y)"}
            ,
            {"cancelJButton", "ȡ��(C)"}
            ,
            {"clearJButton", "���"}
            ,
            {"viewJButton", "�鿴(V)"}
            ,
            {"extendAttrJPanel", "��������"}
            ,
            {"processing", "���ڴ�������"}
            ,
            {"searching", "�����������ݿ�"}
            ,
            {"23", "�ҵ�"}
            ,
            {"24", "�ҵ�"}
            ,
            {"25", "**��*"}
            ,
            {"26", "**���¼ӵ�*"}
            ,
            {"part", "�㲿��"}
            ,
            {"information", "��ʾ"}
            ,
            {"numberSequence", "���������"}
            ,
            {"nameSequence", "����������"}
            ,
            {"createTime", "��ʱ������"}
            ,
            {"versionSequence", "���汾����"}
            ,
            {"deAscending", "����"}
            ,
            {"ascending", "����"}
            ,
            {"sortFrameTitle", "����"}
            ,
            {"sortOK", "����(O)"}
            ,
            {"sortCancel", "�˳�(Q)"}
            ,
            {"noSelectedNodes", "������û��ѡ���κνڵ㣬\n���ǽ��Ը�����µ��㲿������"}
            ,
            {"noPart", "���ȼ����㲿��"}
            ,
            {"noConfigSpec", "��ǰ�û�û�ж�ȡ���ù淶��Ȩ�ޣ�"}
            ,
            {"norightversion", "����Ŀ��������ù淶�޷���ȡ���ʰ汾��"}
            ,
            {"schema", "C:QMPart; G:��������;A:partNumber;A:partName;A:versionID;A:viewName;A:producedByStr;A:partTypeStr;A:lifeCycleState;A:projectId;A:location;"}
            ,
            {"schema1", "C:QMPart; G:����������;A:partNumber;A:partName;A:versionID;A:viewName;A:producedByStr;A:partTypeStr;A:lifeCycleState;A:projectId;A:location;"}
            ,
            {"state1", "״̬��"}
            ,
            {"attributeJLabel", "<������ ���� �汾>"}
            ,
            {"pathJLabel", "<���ϼ�·��>"}
            ,
            {"objectType", "��������"}
            ,
            {"selectlocation", "ѡ��Ŀ�����ϼ�"}
            ,
            {"routeList", "����·��"}
            ,
            {"alternates","�滻��"}
            ,
            {"substitutes","�ṹ�滻��"}//zz
            ,
            //CCBegin by liunan 2008-07-30
            {"manufactureRoute","����·��"}
            ,
            {"assemblyRoute","װ��·��"}
            ,
            {"remark","��ע"}
            ,
            {"routeListNumber","��׼���"}
            ,
            {"vendor", "��Ӧ��"}
            ,
            {"part", "�㲿��"}
            ,
            {"technicsRoute", "����·��"}
            ,
            {"includeTechnicsRoute", "��������·��"}
            //CCEnd by liunan 2008-07-30
    };
}
