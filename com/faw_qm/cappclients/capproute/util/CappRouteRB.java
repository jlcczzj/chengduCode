/**
 * ���ɳ��� CappRouteRB.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.capproute.util;

import java.util.ListResourceBundle;

/**
 * Title:����·��ģ�����Դ��
 * Description:
 * Copyright: Copyright (c) 2004
 * Company: һ������
 * @author ����
 * @version 1.0
 */
public class CappRouteRB extends ListResourceBundle {
    //��������
    public static final String MISSING_RESOURCER = "1";

    public static final String NO_CHANGE_MADE = "2";

    public static final String NO_NUMBER_ENTERED = "3";

    public static final String NO_NAME_ENTERED = "4";

    public static final String REQUIRED_FIELDS_MISSING = "5";

    public static final String NO_LOCATION_ENTERED = "6";

    public static final String LOCATION_NOT_VALID = "7";

    public static final String LOCATION_NOT_PERSONAL_CABINET = "8";

    public static final String SAVING = "9";

    public static final String ADD_RECYCLE = "10";

    public static final String NO_PRODUCT_ENTERED = "11";

    public static final String IS_SAVE_CREATE_ROUTELIST = "12";

    public static final String IS_SAVE_UPDATE_ROUTELIST = "13";

    public static final String PATH_NOT_EXIST = "14";

    public static final String FILE_ALREADY_EXIST = "15";

    public static final String IS_SAVE_CREATE_ROUTE = "16";

    public static final String IS_SAVE_UPDATE_ROUTE = "17";

    public static final String WRONG_TYPE_OBJECT = "20";

    public static final String CONFIRM_DELETE_OBJECT = "21";

    public static final String SYSTEM_ERROR = "22";

    public static final String CONFIRM_APPOINT_FOLDER = "23";

    public static final String CHECK_IN_FAILURE = "24";

    public static final String NOT_FIRST_CHECK_IN = "25";

    public static final String NOT_SELECT_OBJECT = "26";

    public static final String ALREADY_BY_OTHERS_CHECK_OUT = "27";

    public static final String ALREADY_BY_OWN_CHECK_OUT = "28";

    public static final String INIT_RESOURCE_ERROR = "29";

    public static final String VERIFY_UNDO_CHECKOUT = "30";

    public static final String ALREADY_CHECKOUT = "31";

    public static final String RETRIEVE_CHECKOUT_FOLDER_FAILED = "32";

    public static final String ALREADY_CHECKOUT_BY_OTHER = "33";

    public static final String CHECK_OUT_FAILURE = "34";

    public static final String NOT_PERMITED_REVISE = "35";

    public static final String VERIFY_CHECKIN_NOT_OWNER = "36";

    public static final String NULL_CHECK_OUT_FOLDER = "37";

    public static final String NOT_FOUND_FOLDER = "38";

    public static final String CANNOT_DELETE_CHECKED_OUT = "39";

    public static final String PLEASE_CONFIRM_CHECK_OUT = "40";

    public static final String NOT_CHECKOUT_OWNER_DISPLAY = "41";

    public static final String NOT_PERMITED_CHECK_OUT = "42";

    public static final String NOU_CHECK_OUT_BY_YOU = "43";

    public static final String VERIFY_UNDO_CHECKOUT_NOT_OWNER = "44";

    public static final String CAN_NOT_EDIT_ROUTE = "45";

    public static final String NOT_HAVE_PART = "46";

    public static final String CANNOT_MODIFY_ORIGINAL_OBJECT = "47";

    public static final String NO_DEPARTMENT_ENTER = "48";

    public static final String CAN_NOT_COPY_ROUTE = "49";

    public static final String PART_NOT_HAVE_ROUTE = "50";

    public static final String IS_EXIT_SYSTEM = "51";

    /**
     * ������Դ��
     *
     * @roseuid 4031A67602F2
     */
    public CappRouteRB() {
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
            { "default_status", "��ӭ������׼֪ͨ�����. . ." },
            { "information", "��ʾ" },
            { "error", "����" },
            { "warning", "����" },
            { "notice", "֪ͨ" },
            { "afreshAssignLifeCycle", "����ָ����������" },
            { "afreshAssignProject", "����ָ����Ŀ��" },
            { "findPartTitle", "�����㲿��" },
            { "reviseTitle", "�޶�" },
            { "checkInTitle", "������׼֪ͨ��" },
            { "renameRouteListTitle", "��������׼֪ͨ��" },
            {
                    "toolbar.icons",
                    "routeList_create,routeList_update,routeList_delete,Spacer,routeList_checkIn,routeList_checkOut,routeList_repeal,Spacer,routeList_view,public_search,Spacer,route_edit,Spacer,public_help" },
            { "toolbar.text",
                    "����,����,ɾ��,Spacer,����,���,�������,Spacer,�鿴,����,Spacer,�༭·��,Spacer,����" },
            { "toolbar.discribe",
                    "����,����,ɾ��,Spacer,����,���,�������,Spacer,�鿴,����,Spacer,�༭·��,Spacer,����" },
            {
                    "routetoolbar.icons",
                    "route_create,route_update,route_delete,Spacer,public_refresh,public_clear,Spacer,route_view,route_selectPart,Spacer,public_help" },
            { "routetoolbar.text",
                    "����,����,ɾ��,Spacer,ˢ��,���,Spacer,�鿴,ѡ��,Spacer,����" },
            { "routetoolbar.descripe",
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
            //{ "47", "*�ǹ���������ԭ�����������޸ģ�" }, { "48", "��λ����Ϊ�ա�" },
            { "47", "*���û�*������������޸ģ�" }, { "48", "��λ����Ϊ�ա�" },
            { "49", "�㲿��*�Ĺ���·���Ѵ���,���ܴ����µĹ���·�ߡ�" },
            { "50", "�㲿��*�Ĺ���·�߲����ڣ�����ִ�е�ǰ������" }, { "51", "�Ƿ��˳�����·�߱��������" },
            { "52", "��Ų��ܴ���30���ַ�,����������" }, { "53", "���Ʋ��ܴ���200���ַ�,����������" },
            { "54", "˵�����ܴ���2000���ַ�,����������" }, { "0", "����" },
            { "55", "�㲿��*û�з������ù淶�İ汾��������ӡ�" },
            { "Help/Summary/QMSummary", "help/zh_cn/routlist/index.html" },
            { "Desc/Summary/QMSummary", "" },
            //CCBegin by leixiao 2009-9-21 ԭ�򣺽����������·��,����"ת��"  
            { "moveobject", "��׼/�ձ�" },
            {"olderfolder", "Դ���ϼ�"} ,
            {"changefolder", "�������ϼ�"},
            {"goal", "Ŀ�����ϼ�"},
            {"attributeJLabel", "<������ ���� �汾>"}
            ,
            {"pathJLabel", "<���ϼ�·��>"}
            ,
            {"objectType", "��������"}
            ,
            {"selectlocation", "ѡ��Ŀ�����ϼ�"}
            //CCEnd by leixiao 2009-9-21 ԭ�򣺽����������·��,����"ת��"  
            

    };

}
