/**
 *���ɳ��� CappRouteRB_zh_CN.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/22 ����б���������������ݷ�ʽ
 * CR2 2012/01/06 ����  ԭ�������������������ʱʹ��
 * CR3 2012/04/09 ����ԭ��μ�TD5996
 * SS1 ����ͨ�������׼֪ͨ������㲿�� liunan 2013-10-24
 * SS2 ����������Ӧ�� liuyang 2014-8-18
 * SS3 ������������һ��·�� liuyang 2014-8-25
 */
package com.faw_qm.cappclients.conscapproute.util;

import java.util.ListResourceBundle;

/**
 * <p> Title:����·��ģ�����Դ�� </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public class CappRouteRB_zh_CN extends ListResourceBundle
{

    /**
     * ������Դ��
     * @roseuid 4031A67603D9
     */
    public CappRouteRB_zh_CN()
    {

    }

    /**
     * �����Դ����
     * @return Object[][] ����2ά����
     * @roseuid 4031A6760325
     */
    protected Object[][] getContents()
    {
        return contents;
    }

    static final Object contents[][] = {
            // JButtons
            {"OkJButton", "ȷ��"},
            {"CancelJButton", "ȡ��"},
            {"SaveJButton", "����"},
            {"QuitJButton", "�˳�"},
            {"addJButton", "���"},
            {"deleteJButton", "ɾ��"},
            {"searchTreeJButton", "����"},
            {"upJButton", "����"},
            {"downJButton", "����"},
            {"browseJButton", "���"},

            // JLabel
            {"checking in", "���ڼ���"},
            {"describe", "ע��"},
            {"reviseTypeJLabel", "�޶�·�߱�"},
            {"descriJLabel", "˵��"},

            // Titles
            {"exception", "�쳣��Ϣ"},
            {"default_status", "��ӭ���빤��·�߹�����. . ."},
            {"information", "��ʾ"},
            {"error", "����"},
            {"warning", "����"},
            {"notice", "֪ͨ"},
            {"afreshAssignLifeCycle", "����ָ����������"},
            {"afreshAssignProject", "����ָ��������"},
            {"findPartTitle", "�����㲿��"},
            {"reviseTitle", "�޶�"},
            {"checkInTitle", "���빤��·�߱�"},

            {"toolbar.icons",
            // "routeList_create,routeList_update,routeList_delete,Spacer,routeList_checkIn,routeList_checkOut,routeList_repeal,Spacer,routeList_view,public_search,Spacer,route_edit,Spacer,public_help"
                    // },
                    "routeList_create,routeList_update,routeList_delete,Spacer,routeList_checkIn,routeList_checkOut,routeList_repeal,Spacer,routeList_view,public_search,Spacer,route_edit"},

            {"toolbar.text",
            // "����,����,ɾ��,Spacer,����,���,�������,Spacer,�鿴,����,Spacer,�༭·��,Spacer,����"
                    // },
                    "����,����,ɾ��,Spacer,����,���,�������,Spacer,�鿴,����,Spacer,���·��"},
            //begin 20120105 xucy add
            //CCBegin SS2 
        
            //{"routetoolbar.icons", "Spacer,Spacer,Spacer,public_copy,public_paste,route_delete,open,routeM,part_applyAll"},
           // {"routetoolbar.text", "Spacer,Spacer,Spacer,����·��(Ctrl+C),ճ��·��(Ctrl+V),ɾ��·��(Delete),������(Ctrl+Z),����Ϊ����·��(Ctrl+M),Ӧ�õ���·��(Ctrl+Y)"},
            //{"routetoolbar.descripe", "Spacer,Spacer,Spacer,����·��,ճ��·��,ɾ��·��,������,����Ϊ����·��,Ӧ�õ���·��"},
            {"routetoolbar.icons", "Spacer,Spacer,Spacer,public_copy,public_paste,route_delete,open,routeM,part_applyAll,supplier"},
            {"routetoolbar.text", "Spacer,Spacer,Spacer,����·��(Ctrl+C),ճ��·��(Ctrl+V),ɾ��·��(Delete),������(Ctrl+Z),����Ϊ����·��(Ctrl+M),Ӧ�õ���·��(Ctrl+Y),�༭��Ӧ��(Ctrl+G)"},
            {"routetoolbar.descripe", "Spacer,Spacer,Spacer,����·��,ճ��·��,ɾ��·��,������,����Ϊ����·��,Ӧ�õ���·��,�༭��Ӧ��"},
     
            //CCEnd SS2 SS3
            //end 20120105 xucy add
            {"1", "ȱ���������Դ��"}, {"2", "*��û�з����κθ��ġ�"}, {"3", "��׼��Ų���Ϊ�ա�"}, {"4", "��׼���Ʋ���Ϊ�ա�"}, {"5", "ȱ�ٱ�����ֶ�"}, {"6", "���ϼ�λ�ò���Ϊ�ա�"}, {"7", "���ϼ�·����Ч"}, {"8", "��ָ�������ϼв������ĸ������ϼ�"}, {"9", "���ڱ���..."},
            {"10", "�㲿��*�Ѵ��ڣ������ظ���ӡ�"}, {"11", "�����ڲ�Ʒ�����Ϊ�ա�"}, {"12", "�Ƿ񱣴��½�·�߱�"}, {"13", "�Ƿ񱣴����·�߱�"}, {"14", "ָ����·�������ڻ򲻿��á���ȷ�����Ƿ�¼������ȷ���ļ���չ����"}, {"15", "�ļ��Ѵ���,������ָ���ļ�����"}, {"16", "�Ƿ񱣴��½�·�ߣ�"},
            {"17", "�Ƿ񱣴����·�ߣ�"},

            {"20", "ѡ��Ķ������ʹ���"}, {"21", "ɾ���������޷��ָ����Ƿ������"}, {"22", "ϵͳ���ô�������ϵͳ����Ա��ϵ��"},

            {"23", "����ָ�����ϼ�λ�ã�����ִ�м��롣��ѡ�����ϼС�"}, {"24", "����*ʧ�ܣ���Ϊ��Ҫ���ݵ����ػ�洢�����г��ִ���"}, {"25", "*�����ڷ��״μ���״̬�����ȼ��������ִ�м��������"}, {"26", "��û��ѡ����������޷�ִ�е�ǰ������"}, {"27", "*�Ѿ����û�*�����"}, {"28", "*�Ѿ����������"},
            {"29", "�ڳ�ʼ�����ػ���Դʱ����."}, {"30", "��ȷʵҪ�������*������ʧ���б����"}, {"31", "*�Ѿ�����������ϼ�*�У�"}, {"32", "������������ϼ� * ʱ��������."}, {"33", "*�Ѿ������˼��!"}, {"34", "���*ʧ�ܣ�"}, {"35", "��δ����޶�*����ɣ���Ϊ����δ�����롣"},
            {"36", "*�Ѿ���*�������ȷʵҪ��������"}, {"37", "��δָ����ŵ�ǰ����ĸ��������ϼС��������������ϼУ����ܼ����"}, {"38", "�޷��ҵ�����*�Ĺ������������ϼУ���Ϊ*û�б������"}, {"39", "����ɾ��*,��Ϊ���Ѿ������!"}, {"40", "*��Ҫ������������ϼ��в����޸�,����Ҫ�����"},
            {"41", "����δ���*������ǰ����*�����"}, {"42", "��δ��ü��*����ɣ���Ϊ����δ�����롣"}, {"43", "����δ���*��"}, {"44", "*�ѱ�*�������ȷʵҪ����������������б����"},
            {"45", "��ѡ�����༭·�ߵ��㲿����"},
            {"46", "��ǰû�пɹ�ѡ����㲿����"},
            // { "47", "*�ǹ���������ԭ�����������޸ģ�" }, { "48", "��λ����Ϊ�ա�" },
            {"47", "*���û�*������������޸ģ�"}, {"48", "��λ����Ϊ�ա�"}, {"49", "�㲿��*�Ĺ���·���Ѵ���,���ܴ����µĹ���·��"}, {"50", "�㲿��*�Ĺ���·�߲����ڣ�����ִ�е�ǰ������"}, {"51", "�Ƿ��˳�����·�߹�������"}, {"52", "��Ų��ܴ���30���ַ�,����������"},
            {"53", "���Ʋ��ܴ���200���ַ�,����������"}, {"54", "˵�����ܴ���2000���ַ�,����������"},
            {"55", "�㲿��*û�з������ù淶�İ汾��������ӡ�"},
            //CR1 begin
            //CCBegin SS1
            //CCBegin SS3
            //{"addparttoolbar.icons1", "partM_startProductManager,part_copy,changeNotice,part_openIcon"}, {"addparttoolbar.text1", "�������(Ctrl+P),�Ӳ�Ʒ�ṹ�����(Ctrl+B),���������֪ͨ�����(Ctrl+T),���(Ctrl+J)"},
            //{"addparttoolbar.discribe1", "�������,�Ӳ�Ʒ�ṹ�����,���������֪ͨ�����,���"},
//            {"addparttoolbar.icons1", "partM_startProductManager,part_copy,changeNotice,part_openIcon,partM_aptProjectManager"}, {"addparttoolbar.text1", "�������(Ctrl+P),�Ӳ�Ʒ�ṹ�����(Ctrl+B),���������֪ͨ�����(Ctrl+T),���(Ctrl+J),�������׼֪ͨ�����(Ctrl+N)"},
//            {"addparttoolbar.discribe1", "�������,�Ӳ�Ʒ�ṹ�����,���������֪ͨ�����,���,�������׼(��)֪ͨ�����"},
            {"addparttoolbar.icons1", "partM_startProductManager,part_copy,changeNotice,part_openIcon,partM_aptProjectManager,searthLevelOne"}, {"addparttoolbar.text1", "�������(Ctrl+P),�Ӳ�Ʒ�ṹ�����(Ctrl+B),���������֪ͨ�����(Ctrl+T),���(Ctrl+J),�������׼֪ͨ�����(Ctrl+N),������һ��·�����(Ctrl+0)"},
            {"addparttoolbar.discribe1", "�������,�Ӳ�Ʒ�ṹ�����,���������֪ͨ�����,���,�������׼(��)֪ͨ�����,������һ��·�����"},         
            //CCEnd SS1
            //CCEnd SS3
            {"addparttoolbar.icons2", "part_copy"}, {"addparttoolbar.text2", "�Ӳ�Ʒ�ṹ�����(Ctrl+B)"},
            {"addparttoolbar.discribe2", "�Ӳ�Ʒ�ṹ�����"},
            //CR1 end
            //begin 20120105 xucy add
            //CR3 begin
            {"parttoolbar.icons", "Spacer,Spacer,Spacer,part_view,route_editGraph,route_parentPart,routeCode_delete,part_deletestr,public_moveUp,public_moveDown"},
            {"parttoolbar.text", "Spacer,Spacer,Spacer,�鿴���(Ctrl+W),·��ͼ(Ctrl+R),װ��λ��(Ctrl+L),ɾ��װ��λ��,ɾ�����(Ctrl+E),����(Ctrl+��),����(Ctrl+��)"}, {"parttoolbar.descripe", "Spacer,Spacer,Spacer,�鿴���,·��ͼ,װ��λ��,ɾ��װ��λ��,ɾ�����,����,����"},
            //CR3 end
            //end 20120105 xucy add
            //CR2 begin
            {"checkin*", "����*"}, {"checkout*", "���*"}, {"undocheckout*", "�������*"},
            //CR2 end
            //20120111 xucy add
            {"56", "����·�߱�*��δ���룬���ܷ���·�ߡ�"}, {"57", "����·�߱�*�����ɹ���"}, {"58", "��ݼ�*û�ж����Ӧ��·����Ϣ,����Ӧ�á�"}, {"59", "���*��·�ߣ����ܸ���·��"},{"60", "·�ߵ�λ*������"},{"61", "*�༭·��ͼ��Ϊ��ʼ���棬�Ƿ�༭?"}};
}
