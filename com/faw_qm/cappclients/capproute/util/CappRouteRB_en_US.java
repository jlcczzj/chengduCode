/**
 * ���ɳ��� CappRouteRB_en_US.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.capproute.util;

import java.util.ListResourceBundle;

/**
 * <p>
 * Title:����·��ģ�����Դ��
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @version 1.0
 */
public class CappRouteRB_en_US extends ListResourceBundle {

    /**
     * ������Դ��
     *
     * @roseuid 4031A6770159
     */
    public CappRouteRB_en_US() {

    }

    /**
     * �����Դ����
     *
     * @return Object[][] ����2ά����
     * @roseuid 4031A6760325
     */
    protected Object[][] getContents() {
        return contents;
    }

    static final Object contents[][] = {
            //JButtons
            { "OkJButton", "ȷ��" },
            { "CancelJButton", "ȡ��" },
            { "SaveJButton", "����" },
            { "QuitJButton", "�˳�" },
            { "addJButton", "���" },
            { "deleteJButton", "ɾ��" },
            { "searchTreeJButton", "����" },
            { "upJButton", "����" },
            { "downJButton", "����" },
            { "browseJButton", "���" },

            //JLabel
            { "checking in", "���ڼ���" },
            { "describe", "ע��" },
            { "reviseTypeJLabel", "�޶���׼֪ͨ��" },
            { "descriJLabel", "˵��" },

            //Titles
            { "exception", "�쳣��Ϣ" },
            { "information", "��ʾ" },
            { "error", "����" },
            { "warning", "����" },
            { "notice", "֪ͨ" },
            { "afreshAssignLifeCycle", "����ָ����������" },
            { "afreshAssignProject", "����ָ����Ŀ��" },
            { "findPartTitle", "�����㲿��" },
            { "reviseTitle", "�޶�" },
            { "checkInTitle", "������׼֪ͨ��" },

            {
                    "toolbar.icons",
                   // "routeList_create,routeList_update,routeList_delete,Spacer,routeList_checkIn,routeList_checkOut,routeList_repeal,Spacer,routeList_view,public_search,Spacer,route_edit,Spacer,public_help" },
           "routeList_create,routeList_update,routeList_delete,Spacer,routeList_checkIn,routeList_checkOut,routeList_repeal,Spacer,routeList_view,public_search,Spacer,route_edit" },
                   { "toolbar.text",
                   //   "����,����,ɾ��,Spacer,����,���,�������,Spacer,�鿴,����,Spacer,�༭·��,Spacer,����" },
                "����,����,ɾ��,Spacer,����,���,�������,Spacer,�鿴,����,Spacer,�༭·��" },
            {
                    "routetoolbar.icons",
                   // "route_create,route_update,route_delete,Spacer,public_refresh,public_clear,Spacer,route_view,route_selectPart,Spacer,public_help" },
            "route_create,route_update,route_delete,Spacer,public_refresh,public_clear,Spacer,route_view,route_selectPart" },
                   { "routetoolbar.text",
                    "����,����,ɾ��,Spacer,ˢ��,���,Spacer,�鿴,ѡ��,Spacer,����" },

            { "1", "ȱ���������Դ��" }, { "2", "*��û�з����κθ��ġ�" }, { "3", "��Ų���Ϊ�ա�" },
            { "4", "���Ʋ���Ϊ�ա�" }, { "5", "ȱ�ٱ�����ֶ�" }, { "6", "���ϼ�λ�ò���Ϊ�ա�" },
            { "7", "���ϼ�·����Ч" }, { "8", "��ָ�������ϼв������ĸ������ϼ�" },
            { "9", "���ڱ���..." }, { "10", "�㲿��*�Ѵ��ڣ������ظ���ӡ�" },
            { "11", "�����ڲ�Ʒ�����Ϊ�ա�" }, { "12", "�Ƿ񱣴��½�·�߱�" },
            { "13", "�Ƿ񱣴����·�߱�" },
            { "14", "ָ����·�������ڻ򲻿��á���ȷ�����Ƿ�¼������ȷ���ļ���չ����" },
            { "15", "�ļ��Ѵ���,������ָ���ļ�����" }, { "16", "�Ƿ񱣴��½�·�ߣ�" },
            { "17", "�Ƿ񱣴����·�ߣ�" },

            { "20", "ѡ��Ķ������ʹ���" }, { "21", "ɾ���������޷��ָ����Ƿ������" },
            { "22", "ϵͳ���ô�������ϵͳ����Ա��ϵ��" },

            { "23", "����ָ�����ϼ�λ�ã�����ִ�м��롣��ѡ�����ϼС�" },
            { "24", "����*ʧ�ܣ���Ϊ��Ҫ���ݵ����ػ�洢�����г��ִ���" },
            { "25", "*�����ڷ��״μ���״̬�����ȼ��������ִ�м��������" },
            { "26", "��û��ѡ����������޷�ִ�е�ǰ������" }, { "27", "*�Ѿ����û�*�����" },
            { "28", "*�Ѿ����������" }, { "29", "�ڳ�ʼ�����ػ���Դʱ����." },
            { "30", "��ȷʵҪ�������*������ʧ���б����" }, { "31", "*�Ѿ�����������ϼ�*�У�" },
            { "32", "������������ϼ� * ʱ��������." }, { "33", "*�Ѿ������˼��!" },
            { "34", "���*ʧ�ܣ�" }, { "35", "��δ����޶�*����ɣ���Ϊ����δ�����롣" },
            { "36", "*�Ѿ���*�������ȷʵҪ��������" },
            { "37", "��δָ����ŵ�ǰ����ĸ��������ϼС��������������ϼУ����ܼ����" },
            { "38", "�޷��ҵ�����*�Ĺ������������ϼУ���Ϊ*û�б������" },
            { "39", "����ɾ��*,��Ϊ���Ѿ������!" }, { "40", "*��Ҫ������������ϼ��в����޸�,����Ҫ�����" },
            { "41", "����δ���*������ǰ����*�����" }, { "42", "��δ��ü��*����ɣ���Ϊ����δ�����롣" },
            { "43", "����δ���*��" }, { "44", "*�ѱ�*�������ȷʵҪ����������������б����" },
            { "45", "��ѡ�����༭·�ߵ��㲿����" }, { "46", "��ǰû�пɹ�ѡ����㲿����" },
            { "47", "*�ǹ���������ԭ�����������޸ģ�" }, { "48", "��λ����Ϊ�ա�" },
            { "49", "�㲿��*�Ĺ���·���Ѵ���,���ܴ����µĹ���·��" },
            { "50", "�㲿��*�Ĺ���·�߲����ڣ�����ִ�е�ǰ������" }, { "51", "�Ƿ��˳�����·�߱��������" },
            { "55", "�㲿��*û�з������ù淶�İ汾��������ӡ�" },
        };
}
